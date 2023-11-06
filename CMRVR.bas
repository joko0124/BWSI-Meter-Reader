B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=7.8
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
'	Dim iItem As ACMenuItem
'
'	Menu.Clear
'	Menu.Add2(1, 1, $"Search"$, xmlIcon.GetDrawable("ic_search_white_24dp")).ShowAsAction = iItem.SHOW_AS_ACTION_IF_ROOM
End Sub

Sub Process_Globals
	Private xui As XUI
End Sub

Sub Globals
	Dim ActionBarButton As ACActionBar
	Private ToolBar As ACToolBarDark
	Private xmlIcon As XmlLayoutBuilder
	
	'Material Dialog
	Private MatDialogBuilder As MaterialDialogBuilder
	
	'Expandable ListView

	'Database
	
	Private btnAveBill As ACButton
	Private btnHighBill As ACButton
	Private btnLowBill As ACButton
	Private btnMissCoded As ACButton
	Private btnZeroCons As ACButton
	Private lblTitle As Label
	
	Private snack As DSSnackbar
	Dim iUnusualRpt As Int
End Sub

Sub Activity_Create(FirstTime As Boolean)
	MyScale.SetRate(0.5)
	Activity.LoadLayout("ReadingValidation")

	GlobalVar.CSTitle.Initialize.Size(17).Bold.Append($"Reading Validation Report"$).PopAll
	GlobalVar.CSSubTitle.Initialize.Size(14).Append($"List of Account's with Unusual Reading"$).PopAll
	
	ToolBar.InitMenuListener
	ToolBar.Title = GlobalVar.CSTitle
	ToolBar.SubTitle = GlobalVar.CSSubTitle
	ToolBar.SetElevationAnimated(5,5dip)
	
	Dim jo As JavaObject
	Dim xl As XmlLayoutBuilder
	jo = ToolBar
	jo.RunMethod("setPopupTheme", Array(xl.GetResourceId("style", "ToolbarMenu")))
	jo.RunMethod("setContentInsetStartWithNavigation", Array(1dip))
	jo.RunMethod("setTitleMarginStart", Array(0dip))
	
	ActionBarButton.Initialize
	ActionBarButton.ShowUpIndicator = True
	SetButtons

End Sub

Sub Activity_KeyPress (KeyCode As Int) As Boolean 'Return True to consume the event
	If KeyCode = 4 Then
		ToolBar_NavigationItemClick
		Return False
	Else
		Return True
	End If
End Sub

Sub ToolBar_NavigationItemClick
		Activity.Finish
End Sub

Sub Activity_Resume
	GlobalVar.iReaderID = 0
End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub btnMissCoded_Click
	iUnusualRpt = 0
'	GlobalVar.ReportLayout = "Miscoded"
	GlobalVar.iReportLayout = 0
	If GlobalVar.Mod1 = 0 Then
		ShowReaders
	Else
		GlobalVar.iReaderID = GlobalVar.UserID

		If IsThereUnusual(0, GlobalVar.iReaderID) = False Then
			DispErrorMsg(0)
			Return
		End If
		StartActivity(ValidationRptGenerator)
	End If
End Sub

Sub btnHighBill_Click
	iUnusualRpt = 1
	GlobalVar.iReportLayout = 1

	If GlobalVar.Mod1 = 0 Then
		ShowReaders
	Else
		GlobalVar.iReaderID = GlobalVar.UserID

		If IsThereUnusual(1, GlobalVar.iReaderID) = False Then
			DispErrorMsg(1)
			Return
		End If	
		StartActivity(ValidationRptGenerator)
	End If 
End Sub

Sub btnLowBill_Click
	iUnusualRpt = 2
	GlobalVar.iReportLayout = 2

	If GlobalVar.Mod1 = 0 Then
		ShowReaders
	Else
		GlobalVar.iReaderID = GlobalVar.UserID

		If IsThereUnusual(2, GlobalVar.iReaderID) = False Then
			DispErrorMsg(2)
			Return
		End If
		StartActivity(ValidationRptGenerator)
	End If
End Sub

Sub btnZeroCons_Click
	iUnusualRpt = 3
	GlobalVar.iReportLayout = 3

	If GlobalVar.Mod1 = 0 Then
		ShowReaders
	Else
		GlobalVar.iReaderID = GlobalVar.UserID

		If IsThereUnusual(3, GlobalVar.iReaderID) = False Then
			DispErrorMsg(3)
			Return
		End If
		StartActivity(ValidationRptGenerator)
	End If
End Sub

Sub btnAveBill_Click
	iUnusualRpt = 4
	GlobalVar.iReportLayout = 4

	If GlobalVar.Mod1 = 0 Then
		ShowReaders
	Else
		GlobalVar.iReaderID = GlobalVar.UserID

		If IsThereUnusual(4, GlobalVar.iReaderID) = False Then
			DispErrorMsg(4)
			Return
		End If
		StartActivity(ValidationRptGenerator)
	End If
End Sub

Private Sub SetButtons
	btnMissCoded.Background = CreateButtonColor(0xFF0D47A1, 0xFF0D47A1,0xFF1E88E5, 0xFF0D47A1)
	btnHighBill.Background = CreateButtonColor(0xFF0D47A1, 0xFF0D47A1,0xFF1E88E5, 0xFF0D47A1)
	btnLowBill.Background = CreateButtonColor(0xFF0D47A1, 0xFF0D47A1,0xFF1E88E5, 0xFF0D47A1)
	btnZeroCons.Background = CreateButtonColor(0xFF0D47A1, 0xFF0D47A1,0xFF1E88E5, 0xFF0D47A1)
	btnAveBill.Background = CreateButtonColor(0xFF0D47A1, 0xFF0D47A1,0xFF1E88E5, 0xFF0D47A1)
End Sub

Public Sub CreateButtonColor(FocusedColor As Int, EnabledColor As Int, DisabledColor As Int, PressedColor As Int) As StateListDrawable

	Dim RetColor As StateListDrawable
	RetColor.Initialize
	Dim drwFocusedColor, drwEnabledColor, drwDisabledColor, drwPressedColor As ColorDrawable

	drwFocusedColor.Initialize2(FocusedColor, 25, 0, Colors.Black)
	drwEnabledColor.Initialize2(EnabledColor, 25, 0, Colors.Black)
	drwDisabledColor.Initialize2(DisabledColor, 25, 2, Colors.Black)
	drwPressedColor.Initialize2(PressedColor, 25, 0, Colors.Black)

	RetColor.AddState(RetColor.State_Focused, drwFocusedColor)
	RetColor.AddState(RetColor.State_Pressed, drwPressedColor)
	RetColor.AddState(RetColor.State_Enabled, drwEnabledColor)
	RetColor.AddState(RetColor.State_Disabled, drwDisabledColor)
	RetColor.AddCatchAllState(drwFocusedColor)
	RetColor.AddCatchAllState(drwEnabledColor)
	RetColor.AddCatchAllState(drwDisabledColor)
	RetColor.AddCatchAllState(drwPressedColor)
	Return RetColor

End Sub

Private Sub IsThereUnusual (iFlag As Int, iUserID As Int) As Boolean
	Dim blnRetVal As Boolean
	Dim rsUnusual As Cursor
	Try
		Select Case iFlag
			Case 0
				Starter.strCriteria = "SELECT * FROM tblReadings " & _
							  "WHERE MissCoded = '" & 1 & "' " & _
							  "AND BranchID = " & GlobalVar.BranchID & " " & _
							  "AND BillYear = " & GlobalVar.BillYear & " " & _
							  "AND BillMonth = " & GlobalVar.BillMonth & " " & _
							  "AND ReadBy = " & iUserID

			Case 1
				Starter.strCriteria = "SELECT * FROM tblReadings " & _
							  "WHERE ImplosiveType = '" & "implosive-inc" & "' " & _
							  "AND BranchID = " & GlobalVar.BranchID & " " & _
							  "AND BillYear = " & GlobalVar.BillYear & " " & _
							  "AND BillMonth = " & GlobalVar.BillMonth & " " & _
							  "AND ReadBy = " & iUserID

			Case 2
				Starter.strCriteria = "SELECT * FROM tblReadings " & _
							  "WHERE ImplosiveType = '" & "implosive-dec" & "' " & _
							  "AND BranchID = " & GlobalVar.BranchID & " " & _
							  "AND BillYear = " & GlobalVar.BillYear & " " & _
							  "AND BillMonth = " & GlobalVar.BillMonth & " " & _
							  "AND ReadBy = " & iUserID

			Case 3
				Starter.strCriteria = "SELECT * FROM tblReadings " & _
							  "WHERE ImplosiveType = '" & "zero" & "' " & _
							  "AND BranchID = " & GlobalVar.BranchID & " " & _
							  "AND BillYear = " & GlobalVar.BillYear & " " & _
							  "AND BillMonth = " & GlobalVar.BillMonth & " " & _
							  "AND ReadBy = " & iUserID

			Case 4
				Starter.strCriteria = "SELECT * FROM tblReadings " & _
							  "WHERE BillType = '" & "AB" & "' " & _
							  "AND BranchID = " & GlobalVar.BranchID & " " & _
							  "AND BillYear = " & GlobalVar.BillYear & " " & _
							  "AND BillMonth = " & GlobalVar.BillMonth & " " & _
							  "AND ReadBy = " & iUserID
		End Select

		rsUnusual = Starter.DBCon.ExecQuery(Starter.strCriteria)
		Log(rsUnusual.RowCount)

		
		If rsUnusual.RowCount > 0 Then
			blnRetVal = True
		Else
			blnRetVal = False
		End If
	Catch
		rsUnusual.Close
		Log(LastException)
	End Try
	rsUnusual.Close
	Return blnRetVal
End Sub

Private Sub DispErrorMsg(iUnusual As Int)
	Dim csTitle, csMsg As CSBuilder
	Dim icon As B4XBitmap
	Dim sMsg As String
	
'	Msgbox(csMsg, csTitle)
	If iUnusual = 0 Then
		sMsg = $"No Miss Coded/Erroneous Reading found."$
	Else If iUnusual = 1 Then
		sMsg = $"No High Bill Reading found."$
	Else If iUnusual = 2 Then
		sMsg = $"No Low Bill Reading found."$
	Else If iUnusual = 3 Then
		sMsg = $"No Zero Consumption Reading found."$
	Else If iUnusual = 4 Then
		sMsg = $"No Average Bill Reading found."$
	End If

	csTitle.Initialize.Color(Colors.Red).Append($"NO RECORDS"$).PopAll
	csMsg.Initialize.Bold.Color(Colors.DarkGray).Append(CRLF & sMsg).PopAll
	icon = xui.LoadBitmapResize(File.DirAssets, "error.png", 24dip, 24dip, True)
	Msgbox2(csMsg, csTitle, $"OK"$, "","",icon)
End Sub

Private Sub ShowReaders
	Dim rsReaders As Cursor
	Dim csTitle As CSBuilder
	Dim Readers As String
	Dim ReaderList() As String
	Dim pCount As Int
	
	Try
		Starter.strCriteria = "SELECT tblReadings.ReadBy, tblUsers.EmpName AS ReaderName " & _
							  "FROM tblReadings " & _
							  "INNER JOIN tblUsers ON tblReadings.ReadBy = tblUsers.UserID " & _
							  "GROUP BY tblReadings.ReadBy, tblUsers.EmpName " & _
							  "ORDER BY tblUsers.UserID"
							  
		LogColor(Starter.strCriteria, Colors.Blue)
		
		rsReaders =  Starter.DBCon.ExecQuery (Starter.strCriteria)
		If rsReaders.RowCount > 0 Then
			pCount = rsReaders.RowCount
			Dim ReaderList(pCount) As String
			
			For i = 0 To rsReaders.RowCount - 1
				rsReaders.Position = i
				Readers = TitleCase(rsReaders.GetString("ReaderName"))
				ReaderList(i) = Readers
			Next
		Else
			snack.Initialize("", Activity, "No Assigned Pump(s) found!",snack.DURATION_SHORT)
			SetSnackBarBackground(snack, Colors.Red)
			SetSnackBarTextColor(snack, Colors.White)
			snack.Show
			Return
		End If
	Catch
		snack.Initialize("", Activity, LastException,snack.DURATION_SHORT)
		SetSnackBarBackground(snack, Colors.Red)
		SetSnackBarTextColor(snack, Colors.White)
		snack.Show
		Return
	End Try
	
	csTitle.Initialize.Color(GlobalVar.PriColor).Bold.Size(16).Append($"PLEASE SELECT THE READER"$).PopAll
	MatDialogBuilder.Initialize("SelectedReader")
	MatDialogBuilder.Title(csTitle)
	MatDialogBuilder.Items(ReaderList)
	MatDialogBuilder.PositiveText($"SELECT"$).PositiveColor(GlobalVar.PriColor)
	MatDialogBuilder.NegativeText($"CANCEL"$).NegativeColor(Colors.Red)
	MatDialogBuilder.Cancelable(False)
	MatDialogBuilder.CanceledOnTouchOutside(False)
	MatDialogBuilder.itemsCallbackSingleChoice(0)
	MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIGHT)
	MatDialogBuilder.Show
End Sub

Private Sub SelectedReader_OnDismiss (Dialog As MaterialDialog)
	Log("Dialog dismissed")
End Sub

Private Sub SelectedReader_SingleChoiceItemSelected (Dialog As MaterialDialog, Position As Int, Text As String)
	GlobalVar.iReaderID =DBaseFunctions.GetReaderID(GlobalVar.SF.Lower(Text))
End Sub

Private Sub SelectedReader_ButtonPressed (mDialog As MaterialDialog, sAction As String)
	
	Select Case sAction
		Case mDialog.ACTION_POSITIVE
			LogColor(GlobalVar.iReaderID, Colors.Blue)
			If IsThereUnusual(iUnusualRpt, GlobalVar.iReaderID) = False Then
				DispErrorMsg(iUnusualRpt)
				Return
			End If
			StartActivity(ValidationRptGenerator)
			
		Case mDialog.ACTION_NEGATIVE
	End Select
End Sub

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

Sub TitleCase (s As String) As String
	s = s.ToLowerCase
	Dim m As Matcher = Regex.Matcher("\b(\w)", s)
	Do While m.Find
		Dim i As Int = m.GetStart(1)
		s = s.SubString2(0, i) & s.SubString2(i, i + 1).ToUpperCase & s.SubString(i + 1)
	Loop
	Return s
End Sub