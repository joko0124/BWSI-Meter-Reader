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
'	Dim iItem As ACMenuItem
	'
'	Menu.Clear
'	Menu.Add2(1, 1, $"Search"$, xmlIcon.GetDrawable("ic_search_white_24dp")).ShowAsAction = iItem.SHOW_AS_ACTION_IF_ROOM
End Sub

Sub Process_Globals
	Private xui As XUI
	Dim PDFContent As String
End Sub

Sub Globals
	Dim ActionBarButton As ACActionBar
	Private ToolBar As ACToolBarDark
	Private xmlIcon As XmlLayoutBuilder
	
	'Material Dialog
	Private MatDialogBuilder As MaterialDialogBuilder
	
	'Expandable ListView
	Private CLV1 As classCustomListView
	
	'Database
	Private iReportFlag As Int
	Private pnlContent As Panel
	Private rsMissCoded As Cursor
	Private rsHighBill As Cursor
	Private rsLowBill As Cursor
	Private rsZeroCons As Cursor
	Private rsAveBill As Cursor
	
	
	Private lblAccountName As Label
	Private lblAccountNo As Label
	Private lblAddress As Label
	Private lblBookNo As Label
	Private lblCum As Label
	Private lblMeterNo As Label
	Private lblPresRdg As Label
	Private lblPrevRdg As Label
	Private lblReason As Label
	Private lblSeqNo As Label
	
	Private pnlCustomers As Panel
	Private lblRecCount As Label
	Private pnlStatus As Panel
	Private btnExport As ACButton
	
	Private PDFWriter1 As PDFWriter
	Private PaperSize As PDFPaperSizes
	Private Fonts As PDFStandardFonts
	Dim snack As DSSnackbar
	
End Sub

Sub Activity_Create(FirstTime As Boolean)
	MyScale.SetRate(0.5)
	Activity.LoadLayout("ReportGenerator")

	Select GlobalVar.iReportLayout
		Case 0
			GlobalVar.CSTitle.Initialize.Size(17).Bold.Append($"Erroneous/Misscoded Readings"$).PopAll
			GlobalVar.CSSubTitle.Initialize.Size(14).Append($"List of Erroneous/Misscoded Readings"$).PopAll
			iReportFlag = 0
			
		Case 1
			GlobalVar.CSTitle.Initialize.Size(17).Bold.Append($"Implosive High Readings"$).PopAll
			GlobalVar.CSSubTitle.Initialize.Size(14).Append($"List of Implosive High Readings"$).PopAll
			iReportFlag = 1
			
		Case 2
			GlobalVar.CSTitle.Initialize.Size(17).Bold.Append($"Implosive Low Readings"$).PopAll
			GlobalVar.CSSubTitle.Initialize.Size(14).Append($"List of Implosive Low Readings"$).PopAll
			iReportFlag = 2

		Case 3
			GlobalVar.CSTitle.Initialize.Size(17).Bold.Append($"Zero Consumption Readings"$).PopAll
			GlobalVar.CSSubTitle.Initialize.Size(14).Append($"List of Zero Consumption Readings"$).PopAll
			iReportFlag = 3

		Case 4
			GlobalVar.CSTitle.Initialize.Size(17).Bold.Append($"Average Bill Readings"$).PopAll
			GlobalVar.CSSubTitle.Initialize.Size(14).Append($"List of Average Bill Readings"$).PopAll
			iReportFlag = 4
			DisplayRecords(GlobalVar.BranchID, GlobalVar.BillYear, GlobalVar.BillMonth, GlobalVar.iReaderID, GlobalVar.iReportLayout)
	End Select
	
	If FirstTime Then
		PDFWriter1.Initialize("PDFWriter1",PaperSize.LETTER_WIDTH,PaperSize.LETTER_HEIGHT)
	End If
	
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
	btnExport.Background = CreateButtonColor(0xFF0D47A1, 0xFF0D47A1,0xFF1E88E5, 0xFF0D47A1)
End Sub

Sub PDFWriter1_ConversionDone (Content As String)
	PDFContent = Content
	ProgressDialogHide
	ToastMessageShow("Conversion has been done.",False)
End Sub


Sub Activity_KeyPress (KeyCode As Int) As Boolean 'Return True to consume the event
	If KeyCode = 4 Then
		Return False
	Else
		Return True
	End If
End Sub

Sub ToolBar_NavigationItemClick
	Activity.Finish
End Sub

Sub Activity_Resume
	DisplayRecords(GlobalVar.BranchID, GlobalVar.BillYear, GlobalVar.BillMonth, GlobalVar.iReaderID, GlobalVar.iReportLayout)
End Sub

Sub Activity_Pause (UserClosed As Boolean)

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

Private Sub DisplayRecords(iBranchID As Int, iBillYear As Int, iBillMonth As Int, iUserID As Int, iRpt As Int)
	Dim sReason As String
	Try
		Select Case iRpt
			Case 0
				Starter.strCriteria = "SELECT ReadID, AcctNo, AcctName, AcctAddress, BookNo, SeqNo, MeterNo, OrigRdg as PresRdg, PrevRdg, (OrigRdg - PrevRdg) as PresCum, MissCode As Reason " & _
							  "FROM tblReadings " & _
							  "WHERE MissCoded = 1 " & _
							  "AND BranchID = " & iBranchID & " " & _
							  "AND BillYear = " & iBillYear & " " & _
							  "AND BillMonth = " & iBillMonth & " " & _
							  "AND ReadBy = " & iUserID

			Case 1
				Starter.strCriteria = "SELECT ReadID, AcctNo, AcctName, AcctAddress, BookNo, SeqNo, MeterNo, PresRdg, PrevRdg, PresCum, ImplosiveType As Reason " & _
							  "FROM tblReadings " & _
							  "WHERE ImplosiveType = '" & "implosive-inc" & "' " & _
							  "AND BranchID = " & iBranchID & " " & _
							  "AND BillYear = " & iBillYear & " " & _
							  "AND BillMonth = " & iBillMonth & " " & _
							  "AND ReadBy = " & iUserID

			Case 2
				Starter.strCriteria = "SELECT ReadID, AcctNo, AcctName, AcctAddress, BookNo, SeqNo, MeterNo, PresRdg, PrevRdg, PresCum, ImplosiveType As Reason " & _
							  "FROM tblReadings " & _
							  "WHERE ImplosiveType = '" & "implosive-dec" & "' " & _
							  "AND BranchID = " & iBranchID & " " & _
							  "AND BillYear = " & iBillYear & " " & _
							  "AND BillMonth = " & iBillMonth & " " & _
							  "AND ReadBy = " & iUserID

			Case 3
				Starter.strCriteria = "SELECT ReadID, AcctNo, AcctName, AcctAddress, BookNo, SeqNo, MeterNo, PresRdg, PrevRdg, PresCum, ReadRemarks As Reason " & _
							  "FROM tblReadings " & _
							  "WHERE ImplosiveType = '" & "zero" & "' " & _
							  "AND BranchID = " & iBranchID & " " & _
							  "AND BillYear = " & iBillYear & " " & _
							  "AND BillMonth = " & iBillMonth & " " & _
							  "AND ReadBy = " & iUserID

			Case 4
				Starter.strCriteria = "SELECT ReadID, AcctNo, AcctName, AcctAddress, BookNo, SeqNo, MeterNo, PresRdg, PrevRdg, PresCum, ReadRemarks As Reason " & _
							  "FROM tblReadings " & _
							  "WHERE BillType = '" & "AB" & "' " & _
							  "AND BranchID = " & iBranchID & " " & _
							  "AND BillYear = " & iBillYear & " " & _
							  "AND BillMonth = " & iBillMonth & " " & _
							  "AND ReadBy = " & iUserID
		End Select

		rsMissCoded = Starter.DBCon.ExecQuery(Starter.strCriteria)
		Log(rsMissCoded.RowCount)
		CLV1.Clear
		If pnlCustomers.IsInitialized = False Then pnlCustomers.Initialize("")
		If rsMissCoded.RowCount > 0 Then
			lblRecCount.Text = rsMissCoded.RowCount & $" Record(s) Found"$
			pnlCustomers.Visible = True
			For i = 0 To rsMissCoded.RowCount - 1
				rsMissCoded.Position = i
				If iRpt = 1 Then
					sReason = $"Present consumption is more than 20CuM."$
				Else If iRpt = 2 Then
					sReason = $"Present consumption is less than 20CuM."$
				Else
					sReason = rsMissCoded.GetString("Reason")
				End If
				CLV1.Add(CreateList(CLV1.AsView.Width, rsMissCoded.GetString("AcctNo"), rsMissCoded.GetString("AcctName"), rsMissCoded.GetString("AcctAddress"), rsMissCoded.GetString("BookNo"), rsMissCoded.GetString("SeqNo"), rsMissCoded.GetString("MeterNo"), rsMissCoded.GetInt("PresRdg"), rsMissCoded.GetInt("PrevRdg"), rsMissCoded.GetInt("PresCum"), sReason),rsMissCoded.GetInt("ReadID"))
			Next
		Else
			Log(rsMissCoded.RowCount)
		End If
	Catch
		rsMissCoded.Close
		Log(LastException)
	End Try
	rsMissCoded.Close
	ProgressDialogHide
End Sub

Sub CreateList(iWidth As Int, sAcctNo As String, sAcctName As String, sAddress As String, sBookNo As String, sSeqNo As String, sMeterNo As String, iPresRdg As Int, iPrevRdg As Int, iCUM As Int, sReason As String) As Panel
	Dim p As B4XView = xui.CreatePanel("")
	
	Dim iHeight As Int = 164dip
	
	If GetDeviceLayoutValues.ApproximateScreenSize < 4.5 Then iHeight = 310dip
	p.SetLayoutAnimated(0, 0, 0, iWidth, iHeight)
	p.LoadLayout("ValidationReport")
	
	lblAccountNo.Text = sAcctNo
	lblAccountName.Text = sAcctName
	lblAddress.Text = GlobalVar.SF.Upper(sAddress)
	lblBookNo.Text = sBookNo
	lblSeqNo.Text = sSeqNo
	lblMeterNo.Text = sMeterNo
	lblPresRdg.Text = iPresRdg
	lblPrevRdg.Text = iPrevRdg
	lblCum.Text = iCUM
	lblReason.Text = sReason
	
	Return p
End Sub


Sub CLV1_ItemClick (Index As Int, Value As Object)
	
End Sub

Sub btnExport_Click
	snack.Initialize("", Activity, $"Unable to export PDF due to No PDF writer on this device. -joko"$ ,snack.DURATION_LONG)
	SetSnackBarBackground(snack, Colors.Red)
	SetSnackBarTextColor(snack, Colors.White)
	snack.Show
	Return
'	
'	Dim iBranchID As Int
'	Dim iBillYear As Int
'	Dim iBillMonth As Int
'	Dim iUserID As Int
'	Dim ReportTitle As String
'	Dim y As Int
'	
'	ProgressDialogShow("Generating Document.")
'	Try
'		Select Case iReportFlag
'			Case 0
'				Starter.strCriteria = "SELECT ReadID, AcctNo, AcctName, AcctAddress, BookNo, SeqNo, MeterNo, PresRdg, PrevRdg, PresCum, MissCode As Reason " & _
'							  "FROM tblReadings " & _
'							  "WHERE WasMissCoded = '" & 1 & "' " & _
'							  "AND BranchID = " & GlobalVar.BranchID & " " & _
'							  "AND BillYear = " & GlobalVar.BillYear & " " & _
'							  "AND BillMonth = " & GlobalVar.BillMonth & " " & _
'							  "AND ReadBy = " & GlobalVar.UserID
'				ReportTitle = "Misscoded List"
	'
'			Case 1
'				Starter.strCriteria = "SELECT ReadID, AcctNo, AcctName, AcctAddress, BookNo, SeqNo, MeterNo, PresRdg, PrevRdg, PresCum, ImplosiveType As Reason " & _
'							  "FROM tblReadings " & _
'							  "WHERE ImplosiveType = '" & "implosive-inc" & "' " & _
'							  "AND BranchID = " & GlobalVar.BranchID & " " & _
'							  "AND BillYear = " & GlobalVar.BillYear & " " & _
'							  "AND BillMonth = " & GlobalVar.BillMonth & " " & _
'							  "AND ReadBy = " & GlobalVar.UserID
	'
'				ReportTitle = "High Bill List"
	'
'			Case 2
'				Starter.strCriteria = "SELECT ReadID, AcctNo, AcctName, AcctAddress, BookNo, SeqNo, MeterNo, PresRdg, PrevRdg, PresCum, ImplosiveType As Reason " & _
'							  "FROM tblReadings " & _
'							  "WHERE ImplosiveType = '" & "implosive-dec" & "' " & _
'							  "AND BranchID = " & GlobalVar.BranchID & " " & _
'							  "AND BillYear = " & GlobalVar.BillYear & " " & _
'							  "AND BillMonth = " & GlobalVar.BillMonth & " " & _
'							  "AND ReadBy = " & GlobalVar.UserID
	'
'				ReportTitle = "Low Bill List"
	'
'			Case 3
'				Starter.strCriteria = "SELECT ReadID, AcctNo, AcctName, AcctAddress, BookNo, SeqNo, MeterNo, PresRdg, PrevRdg, PresCum, ImplosiveType As Reason " & _
'							  "FROM tblReadings " & _
'							  "WHERE ImplosiveType = '" & "zero" & "' " & _
'							  "AND BranchID = " & GlobalVar.BranchID & " " & _
'							  "AND BillYear = " & GlobalVar.BillYear & " " & _
'							  "AND BillMonth = " & GlobalVar.BillMonth & " " & _
'							  "AND ReadBy = " & GlobalVar.UserID
	'
'				ReportTitle = "Zero Consumption List"
	'
'			Case 4
'				Starter.strCriteria = "SELECT ReadID, AcctNo, AcctName, AcctAddress, BookNo, SeqNo, MeterNo, PresRdg, PrevRdg, PresCum, ReadRemarks As Reason " & _
'							  "FROM tblReadings " & _
'							  "WHERE BillType = '" & "AB" & "' " & _
'							  "AND BranchID = " & GlobalVar.BranchID & " " & _
'							  "AND BillYear = " & GlobalVar.BillYear & " " & _
'							  "AND BillMonth = " & GlobalVar.BillMonth & " " & _
'							  "AND ReadBy = " & GlobalVar.UserID
	'
'				ReportTitle = "Average Bill List"
'		End Select
	'
'		rsMissCoded = Starter.DBCon.ExecQuery(Starter.strCriteria)
'		Log(rsMissCoded.RowCount)
	'
'		
'		If rsMissCoded.RowCount > 0 Then
'			y = 0
'			PDFWriter1.setFont(Fonts.SUBTYPE, Fonts.COURIER)
'			PDFWriter1.addText(5,5,14,ReportTitle)
'			For i = 0 To rsMissCoded.RowCount - 1
'				rsMissCoded.Position = i
'				
'				y = y + 50
'				PDFWriter1.setFont(Fonts.SUBTYPE, Fonts.COURIER)
'				PDFWriter1.addText(10, y , 12, $"ACCT. NO: "$ & rsMissCoded.GetString("AcctNo"))
'			Next
'		Else
'			Log(rsMissCoded.RowCount)
'		End If
'	Catch
'		rsMissCoded.Close
'		Log(LastException)
'	End Try
'	rsMissCoded.Close
'	ProgressDialogHide
'	
	''	PDFWriter1.setFont(Fonts.SUBTYPE, Fonts.TIMES_ROMAN)
	''	PDFWriter1.addRawContent("1 0 0 rg\n")
	''	PDFWriter1.addText(70, 50, 12, "This is an example of PDFWriter.")
	'''	PDFWriter1.addText(
	''	PDFWriter1.setFont2(Fonts.SUBTYPE, Fonts.COURIER, Fonts.WIN_ANSI_ENCODING)
	''	PDFWriter1.addRawContent("0 0 0 rg\n")
	'''	PDFWriter1.addText2(30, 90, 10, "© CRL", Fonts.DEGREES_270_ROTATION)
	''	PDFWriter1.newPage()
	''	PDFWriter1.addRawContent("[] 0 d\n")
	''	PDFWriter1.addRawContent("1 w\n")
	''	PDFWriter1.addRawContent("0 0 1 RG\n")
	''	PDFWriter1.addRawContent("0 1 0 rg\n")
	''	PDFWriter1.addRectangle(40, 50, 280, 50)
	''	PDFWriter1.addText(85, 75, 18, "Code Research Laboratories")
	''	PDFWriter1.newPage()
	''	PDFWriter1.setFont(Fonts.SUBTYPE, Fonts.COURIER_BOLD)
	''	PDFWriter1.addText(150, 150, 14, "Ported by: RootSoft LLC.")
	''	PDFWriter1.addLine(150, 140, 270, 140)
'	PDFWriter1.ConverseDocument
'	
'	PDFWriter1.outputToFile(File.DirDefaultExternal,"myNewPDF.pdf",PDFContent,"ISO-8859-1")
'	ToastMessageShow("PDF Saved.",False)
	'
End Sub

Private Sub DispInfoMsg(sMsg As String, sTitle As String)
	Dim csTitle, csMsg As CSBuilder
	csTitle.Initialize.Color(Colors.Red).Append(sTitle).PopAll
	csMsg.Initialize.Bold.Color(Colors.DarkGray).Append(CRLF & sMsg).PopAll
	
	Msgbox(csMsg, csTitle)
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