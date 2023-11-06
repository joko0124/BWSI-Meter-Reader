B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=9.3
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: True
	#IncludeTitle: False
#End Region
#Extends: android.support.v7.app.AppCompatActivity
#If Java

public boolean _onCreateOptionsMenu(android.view.Menu menu) {
	if (processBA.subExists("activity_createmenu")) {
		processBA.raiseEvent2(null, true, "activity_createmenu", false, new de.amberhome.objects.appcompat.ACMenuWrapper(menu));
		return true;
	}
	else
		return false;
}
#End If

Sub Activity_CreateMenu(Menu As ACMenu)
	Private Item As ACMenuItem
	Menu.Clear
	Menu.Add2(1, 1, "User Account",xmlIcon.GetDrawable("ic_account_box_white_24dp")).ShowAsAction = Item.SHOW_AS_ACTION_IF_ROOM
	Menu.Add2(2, 2, "Settings",xmlIcon.GetDrawable("ic_settings_white_24dp")).ShowAsAction = Item.SHOW_AS_ACTION_ALWAYS
End Sub

Sub Process_Globals
	Private xui As XUI
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Dim ActionBarButton As ACActionBar
	Private ToolBar As ACToolBarDark
	Private xmlIcon As XmlLayoutBuilder
	
	Type ReadingStatus (sBookNo As String, sBookDesc As String, iTotAccounts As Int, _
					iTotRead As Int, iTotUnread As Int, iTotMisCoded As Int, iTotZeroCons As Int, _
					iTotLowBill As Int, iTotHighBill As Int, iTotAveBill As Int, iTotUnprinted As Int)

	'Material Dialog
	Private MatDialogBuilder As MaterialDialogBuilder
	
	'Main Screen
	Private lblEmpName As Label
	Private lblBranchName As Label
	Private lblMessage As Label
	
	Private lblBillPeriod As Label
	
	Dim rsAssignedBooks As Cursor
'	Private pnlAssignedBooks As Panel
	Private clvBookAssignment As CustomListView

	
	Private snack As DSSnackbar
	Private pnlAssignment As Panel
	
	Private CD As ColorDrawable
	Private vibration As B4Avibrate
	Private vibratePattern() As Long
	Private csAns As CSBuilder


	Private Drawer As B4XDrawer
	Private lvMenus As ListView
	Private pnlMenuHeader As Panel
	Private lblUserBranch As Label
	Private lblUserFullName As Label

	Private lblBookDesc As Label
	Private lblBookNo As Label
	Private lblNoAccts As Label

	Private lblRead As Label
	Private lblUnread As Label
	Private lblMissCode As Label
	Private lblZero As Label
	Private lblHigh As Label
	Private lblLow As Label
	Private lblAve As Label
	Private lblUnprint As Label
	Private btnLoad As ACButton

	Private lblReadTitle As Label
	Private lblUnreadTitle As Label
	Private lblMissCodeTitle As Label
	Private lblZeroTitle As Label
	Private lblLowTitle As Label
	Private lblHighTtle As Label
	Private lblAveTitle As Label
	Private lblUnprintTitle As Label
	
	Private RedColor = 0xFFFF0000 As Double
	Private GrayColor = 0xFFB1B1B1 As Double
	Private BlueColor = 0xFF1976D2 As Double
	Private btnBP As Panel
	Private btnDL As Panel
	Private btnLogOut As Panel
	Private btnUL As Panel
	Private btnValid As Panel
	Private pnlMenu As Panel
	Private pnlStatus As Panel
End Sub

Sub Activity_Create(FirstTime As Boolean)
	MyScale.SetRate(0.5)
	Drawer.Initialize(Me,"MainMenu",Activity, 300dip)
	Drawer.CenterPanel.LoadLayout("DashboardOrig")
	modVariables.SourceSansProBold.Initialize("sourcesanspro-bold.ttf")
	modVariables.SourceSansProRegular.Initialize("sourcesanspro-regular.ttf")
	
	
	GlobalVar.CSTitle.Initialize.Size(17).Bold.Typeface(modVariables.SourceSansProBold.SetCustomFonts).Append(Application.LabelName).PopAll
	GlobalVar.CSSubTitle.Initialize.Size(14).Append(Application.VersionName).PopAll
	
	ToolBar.InitMenuListener
	ToolBar.Title = GlobalVar.CSTitle
	ToolBar.SubTitle = GlobalVar.CSSubTitle
	
	Dim jo As JavaObject
	Dim xl As XmlLayoutBuilder
	jo = ToolBar
	jo.RunMethod("setPopupTheme", Array(xl.GetResourceId("style", "ToolbarMenu")))
	jo.RunMethod("setContentInsetStartWithNavigation", Array(1dip))
	jo.RunMethod("setTitleMarginStart", Array(0dip))
		
	ActionBarButton.Initialize
	ActionBarButton.ShowUpIndicator = True
	ActionBarButton.UpIndicatorBitmap = LoadBitmap(File.DirAssets, "hamburger.png")

	lblEmpName.Text = TitleCase(GlobalVar.EmpName)
	lblBranchName.Text =GlobalVar.SF.Upper(GlobalVar.BranchName)
	lblBillPeriod.Text = GlobalVar.BillPeriod
	
	LogColor($"Is First Time: "$ & FirstTime, Colors.Cyan)
	
	If FirstTime = True Then
		If GlobalVar.Mod5 = 1 Then
			ShowWelcomeAdminDialog
		Else
			ShowWelcomeDialog
		End If
		
		CreateMainMenu(GlobalVar.Mod1, GlobalVar.Mod2, GlobalVar.Mod3, GlobalVar.Mod4, GlobalVar.Mod5, GlobalVar.Mod6)
		
		snack.Initialize("",Activity, $"Welcome back to Meter Reader on Android!"$,snack.DURATION_LONG)
		SetSnackBarBackground(snack,GlobalVar.PriColor)
		SetSnackBarTextColor(snack,Colors.White)
		snack.Show
	End If
	
'	CustomFunctions.SaveUserSettings
	csAns.Initialize.Color(Colors.White).Bold.Append($"YES"$).PopAll
	Drawer.LeftPanel.LoadLayout("MainMenu")
End Sub


Sub Activity_Resume
	CreateMainMenu(GlobalVar.Mod1, GlobalVar.Mod2, GlobalVar.Mod3, GlobalVar.Mod4, GlobalVar.Mod5, GlobalVar.Mod6)

	If DBaseFunctions.IsThereBookAssignments(GlobalVar.BranchID, GlobalVar.BillYear, GlobalVar.BillMonth, GlobalVar.UserID) = True Then
		pnlMenu.Visible = False
		lblMessage.Visible = False
		pnlStatus.Visible = False
		pnlAssignment.Visible = True
		FillBookAssignments(GlobalVar.UserID, GlobalVar.BranchID, GlobalVar.BillYear, GlobalVar.BillMonth)
		If DBaseFunctions.HasDownloadedData(GlobalVar.UserID) = 0 Then
			ShowWarningDialog
		End If
	Else
		pnlMenu.Visible = True
		pnlAssignment.Visible = False
		pnlStatus.Visible = True
		If GlobalVar.Mod5 = 1 Then
			pnlStatus.Color = GlobalVar.PriColor
			lblMessage.TextColor = Colors.White
			lblMessage.Text = "Biller's Module"
		Else
			pnlStatus.Color = Colors.White
			lblMessage.TextColor = Colors.Red
			lblMessage.Text = "No Assigned Book(s) so far..."
		End If
		lblMessage.Visible = True
	End If
'
	lblEmpName.Text = TitleCase(GlobalVar.EmpName)
	lblBranchName.Text = GlobalVar.BranchName
	lblBillPeriod.Text = GlobalVar.BillPeriod
	Drawer.LeftOpen = False
End Sub

Sub Activity_Pause (UserClosed As Boolean)
	If UserClosed Then ExitApplication
End Sub

Sub Activity_KeyPress (KeyCode As Int) As Boolean 'Return True to consume the event
	If KeyCode = 4 Then
'		Drawer.LeftOpen = False
'		vibration.vibrateOnce(1000)
'		snack.Initialize("LogOFF", Activity, $"Sure to Log Off now?"$, snack.DURATION_SHORT)
'		SetSnackBarBackground(snack, Colors.Red)
'		SetSnackBarTextColor(snack, Colors.White)
'		snack.SetAction(csAns)
'		snack.Show
		ShowLogOffSnackBar
		Return True
	Else
		Return False
	End If
End Sub

Sub ToolBar_NavigationItemClick
'	If MatDrawer.IsInitialized Then
'		MatDrawer.OpenDrawer
'	End If
	Drawer.LeftOpen = Not(Drawer.LeftOpen)
End Sub

Sub ToolBar_MenuItemClick (Item As ACMenuItem)
	Select Case Item.Id
		Case 1 'User Account
			StartActivity(UserAccountSettings)
		Case 2 'Settings
			StartActivity(ReadingSettings)
'			StartActivity(Settings)
	End Select
End Sub

#Region MainMenu
Private Sub CreateMainMenu(iMod1 As Int, iMod2 As Int, iMod3 As Int, iMod4 As Int, iMod5 As Int, iMod6 As Int)
	Dim civ As CircularImageView
	Dim imgBack As BitmapDrawable
	Dim CDPressed,CDNormal As ColorDrawable
	Dim SLD As StateListDrawable
	Dim csMenu1, csMenu2, csMenu3, csMenu4, csMenu5, csMenu6 As CSBuilder
	Dim sIcon1, sIcon2, sIcon3, sIcon4, sIcon5, sIcon6 As Object
	
	'User Icon
	imgBack.Initialize( LoadBitmap(File.DirAssets,"profile3.jpg"))

	If pnlMenuHeader.IsInitialized = False Then pnlMenuHeader.Initialize("")
	If civ.IsInitialized = False Then civ.Initialize("")

	civ.BorderWidth = 2dip
	civ.BorderColor = GlobalVar.PriColor
	civ.Color = Colors.Transparent
	civ.SetDrawable ( imgBack )
	pnlMenuHeader.AddView(civ,20,2%y,90,90)
	
	'Menu State colors
	CDPressed.Initialize(Colors.White,0)  ' Normal color
	CDNormal.Initialize(0xFFB0E0E6,0) 'Selected color

	SLD.Initialize
	SLD.AddState(SLD.State_Pressed,CDPressed)
	SLD.AddState(-SLD.State_Pressed,CDNormal)
	
	If lblUserFullName.IsInitialized = False Then lblUserFullName.Initialize("")
	If lblUserBranch.IsInitialized = False Then lblUserBranch.Initialize("")
	If lvMenus.IsInitialized = False Then lvMenus.Initialize("lvMenus")
	
	Dim LVO As JavaObject = lvMenus
	LVO.RunMethod("setSelector",Array As Object(SLD))
	lvMenus.FastScrollEnabled=True
	
	'Header Panel
	lblUserFullName.Text = GlobalVar.SF.Upper(GlobalVar.EmpName)
	lblUserBranch.Text = GlobalVar.BranchName
	
	'Menu Colors
	If iMod1 = 1 Then
		csMenu1.Initialize.Color(GlobalVar.PriColor).Append($"Meter Reading"$).PopAll
		sIcon1 = FontBit(Chr(0xE86D),17,GlobalVar.PriColor,False)
	Else
		csMenu1.Initialize.Color(Colors.LightGray).Append($"Meter Reading"$).PopAll
		sIcon1 = FontBit(Chr(0xE86D),17,Colors.LightGray,False)
	End If
	
	If iMod2 = 1 Then
		csMenu2.Initialize.Color(GlobalVar.PriColor).Append($"Bill Printing"$).PopAll
		sIcon2 = FontBit(Chr(0xE555),17,GlobalVar.PriColor,False)
	Else
		csMenu2.Initialize.Color(Colors.LightGray).Append($"Bill Printing"$).PopAll
		sIcon2 = FontBit(Chr(0xE555),17,Colors.LightGray,False)
	End If
	
	If iMod3 = 1 Then
		csMenu3.Initialize.Color(GlobalVar.PriColor).Append($"Reading Validation Report"$).PopAll
		sIcon3 = FontBit(Chr(0xE065),17,GlobalVar.PriColor,False)
	Else
		csMenu3.Initialize.Color(Colors.LightGray).Append($"Reading Validation Report"$).PopAll
		sIcon3 = FontBit(Chr(0xE065),17,Colors.LightGray,False)
	End If
	
	If iMod4 = 1 Then
		csMenu4.Initialize.Color(GlobalVar.PriColor).Append($"Bill Period Settings"$).PopAll
		sIcon4 = FontBit(Chr(0xE8B8),17,GlobalVar.PriColor,False)
	Else
		csMenu4.Initialize.Color(Colors.LightGray).Append($"Bill Period Settings"$).PopAll
		sIcon4 = FontBit(Chr(0xE8B8),17,Colors.LightGray,False)
	End If
	
	If iMod5 = 1 Then
		csMenu5.Initialize.Color(GlobalVar.PriColor).Append($"Data Syncing"$).PopAll
		sIcon5 = FontBit(Chr(0xE8D5),17,GlobalVar.PriColor,False)
	Else
		csMenu5.Initialize.Color(Colors.LightGray).Append($"Data Syncing"$).PopAll
		sIcon5 = FontBit(Chr(0xE8D5),17,Colors.LightGray,False)
	End If
	
	If iMod6 = 1 Then
		csMenu6.Initialize.Color(GlobalVar.PriColor).Append($"User's Settings"$).PopAll
		sIcon6 = FontBit(Chr(0xE851),17,GlobalVar.PriColor,False)
	Else
		csMenu6.Initialize.Color(Colors.LightGray).Append($"User's Settings"$).PopAll
		sIcon6 = FontBit(Chr(0xE851),17,Colors.LightGray,False)
	End If
	lvMenus.Clear
	
	'Add Menu to list
	lvMenus.AddTwoLinesAndBitmap2(csMenu1, $"Input Customer's Meter Reading"$, sIcon1, 1)
	lvMenus.AddTwoLinesAndBitmap2(csMenu2, $"Print/Reprint Customer's Billing Statement"$, sIcon2, 2)
	lvMenus.AddTwoLinesAndBitmap2(csMenu3, $"Show List of Unusual Readings"$, sIcon3, 3)
	lvMenus.AddTwoLinesAndBitmap2(csMenu4, $"Set-up Current Billing Period"$,sIcon4, 4)
	lvMenus.AddTwoLinesAndBitmap2(csMenu5, $"Download/Upload Data from/to Elite Server"$,sIcon5, 5)
	lvMenus.AddTwoLinesAndBitmap2(csMenu6, $"Change User Name and/or User Password"$,sIcon6, 6)
	lvMenus.AddTwoLinesAndBitmap2($"Log Out "$ & GlobalVar.SF.Upper(GlobalVar.UserName),$"Log-out Session"$,FontBit(Chr(0xF08B),17,GlobalVar.PriColor,True),7)
	lvMenus.AddTwoLinesAndBitmap2($"Close App"$,$"Close Meter Reading App"$,FontBit(Chr(0xF2D4),17,GlobalVar.PriColor,True),8)

	lvMenus.TwoLinesAndBitmap.Label.TextColor = GlobalVar.PriColor
	lvMenus.TwoLinesAndBitmap.Label.TextSize = 12
	lvMenus.TwoLinesAndBitmap.SecondLabel.TextSize = 9
	lvMenus.TwoLinesAndBitmap.Label.Typeface = Typeface.DEFAULT_BOLD
	lvMenus.TwoLinesAndBitmap.ItemHeight = 52dip	
End Sub

Sub lvMenus_ItemClick (Position As Int, Value As Object)
	LogColor(Value, Colors.Red)
	Select Case Value
		Case 1
			If GlobalVar.Mod1 = 1 Then
				If DBaseFunctions.IsThereBookAssignments(GlobalVar.BranchID, GlobalVar.BillYear, GlobalVar.BillMonth, GlobalVar.UserID) = False Then
					snack.Initialize("",Activity,$"No Assigned book(s) for this Reader!"$,snack.DURATION_LONG)
					SetSnackBarBackground(snack,Colors.Red)
					SetSnackBarTextColor(snack,Colors.White)
					snack.Show
					Return
				End If
				StartActivity(ReadingBooks)
			Else
				Return
			End If
		
		Case 2
			If GlobalVar.Mod2 = 1 Then
				StartActivity(CustomerList)
				ProgressDialogShow2($"Loading Customer's Billing Data..."$, True)
			Else
				Return
			End If
		
		Case 3
			If GlobalVar.Mod3 = 1 Then
				StartActivity(CMRVR)
			Else
				Return
			End If

		Case 4
			If GlobalVar.Mod4 = 1 Then
				StartActivity(ReadingSettings)
			Else
				Return
			End If
		
		Case 5
			If GlobalVar.Mod5 = 1 Then
				StartActivity(DataSyncing)
			Else
				Return
			End If
		
		Case 6
			If GlobalVar.Mod6 = 1 Then
				StartActivity(UserAccountSettings)
			Else
				Return
			End If
		
		Case 7
			ShowLogOffSnackBar
		Case 8
			vibration.vibrateOnce(1000)
			snack.Initialize("CloseButton", Activity, $"Close "$ & Application.LabelName & $"?"$,snack.DURATION_LONG)
			snack.SetAction(csAns)
			SetSnackBarBackground(snack, Colors.Red)
			SetSnackBarTextColor(snack, Colors.White)
			snack.Show
	End Select
	Dim CDBack As ColorDrawable
	CDBack.Initialize(Colors.Transparent,0)
	lvMenus.Background = CDBack
	Drawer.LeftOpen = False
End Sub

Private Sub LogOFF_Click()
	CustomFunctions.ClearUserData
	Activity.Finish
	StartActivity(Login)
End Sub

Private Sub CloseButton_Click()
	ExitApplication
End Sub

#End Region

#Region Assignments
Private Sub FillBookAssignments (iUserID As Int, iBranchID As Int, iBillYear As Int, iBillMonth As Int)
	Dim SenderFilter As Object

	Try
		Starter.strCriteria = "SELECT tblReadings.BookID AS BookID, tblReadings.BookNo AS BookNo, tblBooks.BookDesc AS BookDesc, " & _
						  "Count(tblReadings.AcctID) As NoAccts, " & _
						  "SUM(CASE WHEN tblReadings.WasRead = 1 Then 1 Else 0 End) As ReadAccts, " & _
						  "SUM(CASE WHEN tblReadings.WasRead = 0 Then 1 Else 0 End) As UnreadAccts, " & _
						  "SUM(CASE WHEN tblReadings.WasMissCoded = 1 THEN 1 ELSE 0 END) as Miscoded, " & _
						  "SUM(CASE WHEN tblReadings.ImplosiveType = 'zero' Then 1 ELSE 0 END) As ZeroCons, " & _
						  "SUM(CASE WHEN tblReadings.ImplosiveType = 'implosive-inc' Then 1 ELSE 0 END) As HighBill, " & _
						  "SUM(CASE WHEN tblReadings.ImplosiveType = 'implosive-dec' Then 1 ELSE 0 END) As LowBill, " & _
						  "SUM(CASE WHEN tblReadings.BillType = 'AB' Then 1 ELSE 0 END) As AverageBill, " & _
						  "SUM(CASE WHEN (tblReadings.PrintStatus = 0 AND tblReadings.WasRead = 1) Then 1 ELSE 0 END) As Unprinted " & _
						  "FROM tblReadings INNER JOIN tblBooks ON tblReadings.BookID = tblBooks.BookID " & _
						  "WHERE tblReadings.BranchID = " & iBranchID & " " & _
						  "AND tblReadings.BillYear = " & iBillYear & " " & _
						  "AND tblReadings.BillMonth = " & iBillMonth & " " & _
						  "AND tblReadings.ReadBy = " & iUserID & " " & _
						  "GROUP BY tblReadings.BookID " & _
						  "ORDER BY tblReadings.BookNo Asc"

		LogColor(Starter.strCriteria, Colors.Yellow)

		SenderFilter = Starter.DBCon.ExecQueryAsync("SQL", Starter.strCriteria, Null)
		Wait For (SenderFilter) SQL_QueryComplete (Success As Boolean, RS As ResultSet)
		
		If Success Then
			Dim StartTime As Long = DateTime.Now
			clvBookAssignment.Clear
			Do While RS.NextRow
				Dim RdgCount As ReadingStatus
				RdgCount.Initialize
				RdgCount.sBookNo = RS.GetString("BookNo")
				RdgCount.sBookDesc = RS.GetString("BookDesc")
				RdgCount.iTotAccounts = RS.GetInt("NoAccts")
				RdgCount.iTotRead = RS.GetInt("ReadAccts")
				RdgCount.iTotUnread = RS.GetInt("UnreadAccts")
				RdgCount.iTotMisCoded = RS.GetInt("Miscoded")
				RdgCount.iTotZeroCons = RS.GetInt("ZeroCons")
				RdgCount.iTotLowBill = RS.GetInt("LowBill")
				RdgCount.iTotHighBill = RS.GetInt("HighBill")
				RdgCount.iTotAveBill = RS.GetInt("AverageBill")
				RdgCount.iTotUnprinted = RS.GetInt("Unprinted")

				Dim Pnl As B4XView = xui.CreatePanel("")
				Pnl.SetLayoutAnimated(0, 2dip, 2dip, clvBookAssignment.AsView.Width - 2dip, 230dip) 'Panel height + 4 for drop shadow
				clvBookAssignment.Add(Pnl, RdgCount)
			Loop
		Else
			Log(LastException)
		End If

		Log($"List of Book Assignment Records = ${NumberFormat2((DateTime.Now - StartTime) / 1000, 0, 2, 2, False)} seconds to populate ${clvBookAssignment.Size} Book Assignment Records"$)
	Catch
		Log(LastException)
	End Try

End Sub

Sub clvBookAssignment_VisibleRangeChanged (FirstIndex As Int, LastIndex As Int)
	Dim ExtraSize As Int = 15 'List size
	clvBookAssignment.Refresh
	
	For i = Max(0, FirstIndex - ExtraSize) To Min(LastIndex + ExtraSize, clvBookAssignment.Size - 1)
		Dim Pnl As B4XView = clvBookAssignment.GetPanel(i)
		If i > FirstIndex - ExtraSize And i < LastIndex + ExtraSize Then
			If Pnl.NumberOfViews = 0 Then 'Add each item/layout to the list/main layout
				Dim BookRec As ReadingStatus = clvBookAssignment.GetValue(i)
				LogColor($"value: "$ & clvBookAssignment.GetValue(i),Colors.Yellow)
				Pnl.LoadLayout("BookAssign")
				lblBookNo.Text = $"BOOK "$ & BookRec.sBookNo
				lblBookDesc.Text = BookRec.sBookDesc
				lblNoAccts.Text = BookRec.iTotAccounts & " Account(s)"
				lblRead.Text = BookRec.iTotRead
				lblUnread.Text = BookRec.iTotUnread
				lblMissCode.Text = BookRec.iTotMisCoded
				lblZero.Text = BookRec.iTotZeroCons
				lblHigh.Text = BookRec.iTotHighBill
				lblLow.Text = BookRec.iTotLowBill
				lblAve.Text = BookRec.iTotAveBill
				lblUnprint.Text = BookRec.iTotUnprinted
				
				If BookRec.iTotRead = 0 Then
					lblReadTitle.TextColor = RedColor
					lblRead.TextColor = RedColor
					lblUnprintTitle.TextColor = GrayColor
					lblUnprint.TextColor = GrayColor
				Else
					If BookRec.iTotUnprinted > 0 Then
						lblUnprintTitle.TextColor = RedColor
						lblUnprint.TextColor = RedColor
					Else
						lblUnprintTitle.TextColor = GrayColor
						lblUnprint.TextColor = BlueColor
					End If
					lblReadTitle.TextColor = GrayColor
					lblRead.TextColor = BlueColor
				End If
				
				If BookRec.iTotUnread > 0 Then
					lblUnreadTitle.TextColor = RedColor
					lblUnread.TextColor = RedColor
				Else
					lblUnreadTitle.TextColor = GrayColor
					lblUnread.TextColor = BlueColor
				End If
				
				If BookRec.iTotMisCoded > 0 Then
					lblMissCodeTitle.TextColor = RedColor
					lblMissCode.TextColor = RedColor
				Else
					lblMissCodeTitle.TextColor = GrayColor
					lblMissCode.TextColor = BlueColor
				End If

				If BookRec.iTotZeroCons > 0 Then
					lblZeroTitle.TextColor = RedColor
					lblZero.TextColor = RedColor
				Else
					lblZeroTitle.TextColor = GrayColor
					lblZero.TextColor = BlueColor
				End If
				
				If BookRec.iTotHighBill > 0 Then
					lblHighTtle.TextColor = RedColor
					lblHigh.TextColor = RedColor
				Else
					lblHighTtle.TextColor = GrayColor
					lblHigh.TextColor = BlueColor
				End If

				If BookRec.iTotLowBill > 0 Then
					lblLowTitle.TextColor = RedColor
					lblLow.TextColor = RedColor
				Else
					lblLowTitle.TextColor = GrayColor
					lblLow.TextColor = BlueColor
				End If

				If BookRec.iTotAveBill> 0 Then
					lblAveTitle.TextColor = RedColor
					lblAve.TextColor = RedColor
				Else
					lblAveTitle.TextColor = GrayColor
					lblAve.TextColor = BlueColor
				End If

				CD.Initialize2(0xFF1976D2, 25, 0,0xFF000000)
				btnLoad.Background = CD

			End If
		Else 'Not visible
			If Pnl.NumberOfViews > 0 Then
				Pnl.RemoveAllViews 'Remove none visable item/layouts from the list/main layout
			End If
		End If
	Next
End Sub

Sub btnLoad_Click()
'	Dim Index As Int
	Dim value As Object
	Dim intPCAAmount As Double
	Dim Index As Int = clvBookAssignment.GetItemFromView(Sender)
	Dim AssignRec As ReadingStatus = clvBookAssignment.GetValue(Index)
	
	value = AssignRec.sBookNo
	
	Log(value)
	
	
	LogColor (Index, Colors.Cyan)
'	GlobalVar.BookID = DBaseFunctions.GetIDbyCode("BookID","tblBooks","BookCode",AssignRec.sBookNo)
	GlobalVar.BookID = DBaseFunctions.GetIDbyCode("BookID","tblBooks","BookCode",value)
	Log(GlobalVar.BookID)
	
	If DBaseFunctions.IsBookWithPCA(GlobalVar.BookID) = True Then
		intPCAAmount = DBaseFunctions.GetPCAmount(GlobalVar.BookID)
	Else
		intPCAAmount = 0
	End If
	If DBaseFunctions.IsPCAUpdate(GlobalVar.BookID) = False Then
		DBaseFunctions.UpdatePCA(GlobalVar.BookID, intPCAAmount)
	End If
	StartActivity(MeterReading)
End Sub
#End Region

#Region Welcome

Private Sub ShowWelcomeDialog
	Dim csContent1, csButtonOK, csTitle As CSBuilder
	
	csTitle.Initialize.Color(GlobalVar.PriColor).Bold.Size(16).Append($"Good Day Sir "$).Append(TitleCase(GlobalVar.EmpName) & "!").PopAll
'	csContent1.Initialize.Bold.Size(18).Color(GlobalVar.PriColor).Append($"WELCOME to METER READING ON ANDROID APP!"$ & CRLF).PopAll
'	csContent2.Initialize.Color(GlobalVar.PriColor).Append(CRLF & $"Billing Settings:"$).Pop.Color(Colors.DarkGray).Bold.Append(CRLF & $"Bill Month: "$ & GlobalVar.BillMonthName & CRLF & $"Bill Year: "$ & GlobalVar.BillYear).PopAll

	csContent1.Initialize.Bold.Color(Colors.Gray).Size(11).Append($"WELCOME to METER READING ON ANDROID APP!"$).Pop.Pop
	csContent1.Color(GlobalVar.PriColor).Size(18).Bold.Append(CRLF & CRLF & $"BILLING SETTINGS:"$).Pop.Pop.Size(16).Append(CRLF & $"Bill Month: "$ & GlobalVar.BillMonthName & CRLF & $"Bill Year: "$ & GlobalVar.BillYear).PopAll
	csButtonOK.Initialize.Bold.Color(Colors.Gray).Size(11).Append($"OK"$).Pop
	
	MatDialogBuilder.Initialize("WelcomeDialog")
	MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIGHT)
	MatDialogBuilder.Title(csTitle)
	MatDialogBuilder.Content(csContent1)
	MatDialogBuilder.IconRes("ic_account_box_black_36dp").LimitIconToDefaultSize
	MatDialogBuilder.PositiveText(csButtonOK)
	MatDialogBuilder.Cancelable(True)
	MatDialogBuilder.AutoDismiss(True)
	MatDialogBuilder.CanceledOnTouchOutside(True)
	MatDialogBuilder.ButtonRippleColor(GlobalVar.PriColor)
	MatDialogBuilder.Show
End Sub

Private Sub ShowWelcomeAdminDialog
	Dim csContent1, csContent2, csTitle As CSBuilder
	
	csTitle.Initialize.Color(GlobalVar.PriColor).Size(16).Append($"Good Day Sir/Ma'am "$).Append(TitleCase(GlobalVar.EmpName) & "!").PopAll
	csContent1.Initialize.Size(18).Color(Colors.Red).Bold.Append($"WELCOME to METER READING ON ANDROID APP!"$ & CRLF).PopAll
	csContent2.Initialize.Color(GlobalVar.PriColor).Bold.Append($"Billing Settings"$).Color(Colors.DarkGray).Append(CRLF & $"Bill Month: "$ & GlobalVar.BillMonthName & CRLF & $"Bill Year: "$ & GlobalVar.BillYear).PopAll
	
	MatDialogBuilder.Initialize("WelcomeAdminDialog")
	MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIGHT)
	MatDialogBuilder.Title(csTitle)
	MatDialogBuilder.Content(csContent1 & CRLF & csContent2)
	MatDialogBuilder.IconRes("ic_account_box_black_36dp").LimitIconToDefaultSize
	MatDialogBuilder.PositiveColor(GlobalVar.PriColor).PositiveText("OK")
	MatDialogBuilder.NeutralColor(Colors.Red).NeutralText("Reset Settings?")
	MatDialogBuilder.Cancelable(True)
	MatDialogBuilder.AutoDismiss(True)
	MatDialogBuilder.CanceledOnTouchOutside(True)
	MatDialogBuilder.ButtonRippleColor(GlobalVar.PriColor)
	MatDialogBuilder.Show
End Sub

Private Sub WelcomeAdminDialog_ButtonPressed(mDialog As MaterialDialog, sAction As String)
	Select Case sAction
		Case mDialog.ACTION_POSITIVE
		Case mDialog.ACTION_NEGATIVE
		Case mDialog.ACTION_NEUTRAL
	End Select
End Sub

Private Sub ShowWarningDialog
	Dim csContent1, csButtonOK, csTitle As CSBuilder
	
	csTitle.Initialize.Color(0xFFDC143C).Bold.Size(20).Append($"W A R N I N G! "$).PopAll
'	csContent1.Initialize.Bold.Size(18).Color(GlobalVar.PriColor).Append($"WELCOME to METER READING ON ANDROID APP!"$ & CRLF).PopAll
'	csContent2.Initialize.Color(GlobalVar.PriColor).Append(CRLF & $"Billing Settings:"$).Pop.Color(Colors.DarkGray).Bold.Append(CRLF & $"Bill Month: "$ & GlobalVar.BillMonthName & CRLF & $"Bill Year: "$ & GlobalVar.BillYear).PopAll

	csContent1.Initialize.Bold.Color(Colors.Red).Size(14).Append($"It seems that the BILLER Didn't Complete the Reading Data Downloading Process"$).Pop.Pop
	csContent1.Color(Colors.Gray).Size(14).Bold.Append(CRLF & CRLF & $"Please ask the Biller to Re-download your reading data.
	
	--- CBIT"$).Pop.Pop.Size(16).PopAll
	csButtonOK.Initialize.Bold.Color(Colors.Gray).Size(11).Append($"OK"$).Pop
	
	MatDialogBuilder.Initialize("WarningDialog")
	MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIGHT)
	MatDialogBuilder.Title(csTitle)
	MatDialogBuilder.Content(csContent1)
	MatDialogBuilder.IconRes("ic_warning_black_36dp2").LimitIconToDefaultSize
	MatDialogBuilder.PositiveText(csButtonOK)
	MatDialogBuilder.Cancelable(True)
	MatDialogBuilder.AutoDismiss(True)
	MatDialogBuilder.CanceledOnTouchOutside(True)
	MatDialogBuilder.ButtonRippleColor(GlobalVar.PriColor)
	MatDialogBuilder.Show
End Sub

Private Sub WarningDialog_ButtonPressed(mDialog As MaterialDialog, sAction As String)
	Select Case sAction
		Case mDialog.ACTION_POSITIVE
			Activity.Finish
			ExitApplication
		Case mDialog.ACTION_NEGATIVE
		Case mDialog.ACTION_NEUTRAL
	End Select
End Sub
#End Region

Sub CLV1_ItemClick (Index As Int, Value As Object)
	GlobalVar.BookID = Value
	Log(Value)
End Sub


#Region Styles
Sub SetSnackBarBackground(pSnack As DSSnackbar, pColor As Int)
	Dim v As View
	v = pSnack.View
	v.Color = pColor
End Sub

Sub SetSnackBarTextColor(pSnack As DSSnackbar, pColor As Int)
	Dim p As Panel
	p = pSnack.View
	For Each v As View In p.GetAllViewsRecursive
		If v Is Label Then
			Dim textv As Label
			textv = v
			textv.TextColor = pColor
			Exit
		End If
	Next
End Sub

Sub FontBit (icon As String, font_size As Float, color As Int, awesome As Boolean) As Bitmap
	If color = 0 Then color = Colors.White
	Dim typ As Typeface = Typeface.MATERIALICONS
	If awesome Then typ = Typeface.FONTAWESOME
	Dim bmp As Bitmap
	bmp.InitializeMutable(32dip, 32dip)
	Dim cvs As Canvas
	cvs.Initialize2(bmp)
	Dim h As Double
	If Not(awesome) Then
		h = cvs.MeasureStringHeight(icon, typ, font_size) + 10dip
	Else
		h = cvs.MeasureStringHeight(icon, typ, font_size)
	End If
	cvs.DrawText(icon, bmp.Width / 2, bmp.Height / 2 + h / 2, typ, font_size, color, "CENTER")
	Return bmp
End Sub
#End Region

#Region Shortcuts
Sub btnBP_Click
	StartActivity(ReadingSettings)
End Sub

Sub btnDL_Click
	CallSubDelayed(DataSyncing,"btnDownload_Click")
End Sub

Sub btnUL_Click
	CallSubDelayed(DataSyncing,"btnUpload_Click")
End Sub

Sub btnValid_Click
	StartActivity(CMRVR)
'	StartActivity(ReadingValidation)
End Sub

Sub btnLogOut_Click
	ShowLogOffSnackBar
End Sub

Sub TitleCase (s As String) As String
	s = s.ToLowerCase
	Dim m As Matcher = Regex.Matcher("\b(\w)", s)
	Do While m.Find
		Dim i As Int = m.GetStart(1)
		s = s.SubString2(0, i) & s.SubString2(i, i + 1).ToUpperCase & s.SubString(i + 1)
	Loop
	Return s
End Sub

Private Sub ShowLogOffSnackBar
	Drawer.LeftOpen = False
	vibration.vibrateOnce(1000)

	snack.Initialize("LogOFF", Activity, $"Sure to Log Off now?"$, snack.DURATION_SHORT)
	SetSnackBarBackground(snack, Colors.Red)
	SetSnackBarTextColor(snack, Colors.White)
	
	snack.SetAction($"YES"$)
	snack.Show
End Sub
#End Region