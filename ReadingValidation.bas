B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=10
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

Sub Process_Globals
	Private xui As XUI
End Sub

Sub Globals
	Dim ActionBarButton As ACActionBar
	Private ToolBar As ACToolBarDark

	'Database
	Private rsReaders As Cursor
	Private iValidType As Int
	
	'UI
	Private btnAB As Panel
	Private btnHB As Panel
	Private btnLB As Panel
	Private btnMiscoded As Panel
	Private btnUnprinted As Panel
	Private btnZeroCons As Panel
	Private cboReader As ACSpinner
	
	Private MyBadges As Badger
	Private iZeroCount, iMisCodedCount, iLBCount, iHBCount, iAvgCount, iUnprintCount As Int
	Private MiscodedIcon As B4XView
	Private ZeroIcon As B4XView
	Private LBIcon As B4XView
	Private HBIcon As B4XView
	Private ABIcon As B4XView
	Private UnprintedIcon As B4XView
End Sub

Sub Activity_Create(FirstTime As Boolean)
	MyScale.SetRate(0.5)
	Activity.LoadLayout("RdgValidation")

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
	
	MyBadges.Initialize
	FillReaders
	If cboReader.SelectedIndex > -1 Then
		GlobalVar.iReaderID = DBaseFunctions.GetIDbyCode("UserID","tblUsers","EmpName",cboReader.SelectedItem)
		GetValidationCount(GlobalVar.iReaderID, GlobalVar.BranchID, GlobalVar.BillYear, GlobalVar.BillMonth)
	
		MyBadges.SetBadge(MiscodedIcon, iMisCodedCount)
		MyBadges.SetBadge(ZeroIcon, iZeroCount)
		MyBadges.SetBadge(LBIcon, iLBCount)
		MyBadges.SetBadge(HBIcon, iHBCount)
		MyBadges.SetBadge(ABIcon, iAvgCount)
		MyBadges.SetBadge(UnprintedIcon, iUnprintCount)
	End If
'	If IsThereRecords("zero", GlobalVar.iReaderID) = True Then
'		MyBadges.SetBadge(btnZeroCons, iZeroCount)
'	End If
End Sub

Sub Activity_CreateMenu(Menu As ACMenu)
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
'	GlobalVar.GlobalVar.iReaderID = 0
'	InfoMsg($"REMINDER!"$, $"Unprinted bills are not available to validate except miscoded readings!"$)
End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

#Region DataBase
Private Sub FillReaders
	Dim rsReaders As Cursor
	cboReader.Clear
	
	Try
		Starter.strCriteria = "SELECT Readings.ReadBy, Users.EmpName AS ReaderName " & _
						  "FROM tblReadings AS Readings " & _
						  "INNER JOIN tblUsers AS Users ON Readings.ReadBy = Users.UserID " & _
						  "GROUP BY Readings.ReadBy, Users.EmpName " & _
						  "ORDER BY Users.UserID"
		
		LogColor(Starter.strCriteria, Colors.Cyan)
		rsReaders = Starter.DBCon.ExecQuery(Starter.strCriteria)
		
		If rsReaders. RowCount > 0 Then
			For i = 0 To rsReaders.RowCount - 1
				rsReaders.Position = i
				cboReader.Add(rsReaders.GetString("ReaderName"))
			Next
		Else
		End If
	Catch
		Log(LastException)
	End Try
End Sub
Private Sub IsThereMiscodedRecords(ReaderID As Int) As Boolean
	Dim bRetVal As Boolean
	Dim rsValidation As Cursor
	
	Try
		bRetVal = False
		Starter.strCriteria = "SELECT * FROM tblReadings " & _
						  "WHERE ReadBy = " & ReaderID & " " & _
						  "AND BranchID = " & GlobalVar.BranchID & " " & _
						  "AND BillYear = " & GlobalVar.BillYear & " " & _
						  "AND BillMonth = " & GlobalVar.BillMonth & " " & _
						  "AND WasRead = 1 " & _
						  "AND WasMissCoded = 1"
		LogColor(Starter.strCriteria, Colors.Cyan)
		
		rsValidation = Starter.DBCon.ExecQuery(Starter.strCriteria)
				
		If rsValidation.RowCount > 0 Then
			bRetVal = True
		Else
			bRetVal = False
		End If
	Catch
		Log(LastException)
		bRetVal = False
	End Try
	rsValidation.Close
	Return bRetVal
End Sub

Private Sub IsThereRecords(sValidationType As String, ReaderID As Int) As Boolean
	Dim bRetVal As Boolean
	Dim rsValidation As Cursor
	
	Try
		bRetVal = False
		Starter.strCriteria = "SELECT * FROM tblReadings " & _
						  "WHERE ImplosiveType = '" & sValidationType & "' " & _
						  "AND ReadBy = " & ReaderID & " " & _
						  "AND BranchID = " & GlobalVar.BranchID & " " & _
						  "AND BillYear = " & GlobalVar.BillYear & " " & _
						  "AND BillMonth = " & GlobalVar.BillMonth & " " & _
						  "AND BillType = 'RB' " & _
						  "AND WasRead = 1 " & _
						  "AND PrintStatus = 1"
		LogColor(Starter.strCriteria, Colors.Cyan)
		
		rsValidation = Starter.DBCon.ExecQuery(Starter.strCriteria)
				
		If rsValidation.RowCount > 0 Then
			bRetVal = True
		Else
			bRetVal = False
		End If
	Catch
		Log(LastException)
		bRetVal = False
	End Try
	rsValidation.Close
	Return bRetVal
End Sub

Private Sub IsThereAvgRecords(ReaderID As Int) As Boolean
	Dim bRetVal As Boolean
	Dim rsValidation As Cursor
	
	Try
		bRetVal = False
		Starter.strCriteria = "SELECT * FROM tblReadings " & _
						  "WHERE ReadBy = " & ReaderID & " " & _
						  "AND BranchID = " & GlobalVar.BranchID & " " & _
						  "AND BillYear = " & GlobalVar.BillYear & " " & _
						  "AND BillMonth = " & GlobalVar.BillMonth & " " & _
						  "AND BillType = 'AB' " & _
						  "AND WasRead = 1 " & _
						  "AND PrintStatus = 1"
		LogColor(Starter.strCriteria, Colors.Cyan)
		
		rsValidation = Starter.DBCon.ExecQuery(Starter.strCriteria)
				
		If rsValidation.RowCount > 0 Then
			bRetVal = True
		Else
			bRetVal = False
		End If
	Catch
		Log(LastException)
		bRetVal = False
	End Try
	rsValidation.Close
	Return bRetVal
End Sub

Private Sub IsThereUnprintBillRecords(ReaderID As Int) As Boolean
	Dim bRetVal As Boolean
	Dim rsValidation As Cursor
	
	Try
		bRetVal = False
		Starter.strCriteria = "SELECT * FROM tblReadings " & _
						  "WHERE ReadBy = " & ReaderID & " " & _
						  "AND BranchID = " & GlobalVar.BranchID & " " & _
						  "AND BillYear = " & GlobalVar.BillYear & " " & _
						  "AND BillMonth = " & GlobalVar.BillMonth & " " & _
						  "AND WasRead = 1 " & _
						  "AND PrintStatus = 0"
		LogColor(Starter.strCriteria, Colors.Cyan)
		
		rsValidation = Starter.DBCon.ExecQuery(Starter.strCriteria)
				
		If rsValidation.RowCount > 0 Then
			bRetVal = True
		Else
			bRetVal = False
		End If
	Catch
		Log(LastException)
		bRetVal = False
	End Try
	rsValidation.Close
	Return bRetVal
End Sub

#End Region

#Region Buttons
Sub btnZeroCons_Click
	If cboReader.SelectedItem = "" Then
		WarningMsg($"NO READER SELECTED"$,$"Select the reader first!"$)
		Return
	End If
	GlobalVar.iReaderID = DBaseFunctions.GetIDbyCode("UserID","tblUsers","EmpName",cboReader.SelectedItem)
	
	If IsThereRecords($"zero"$,GlobalVar.iReaderID) = False Then
		WarningMsg($"NO RECORD FOUND"$,$"No Zero Consumption reading's found!"$)	
		Return
	End If
	iValidType = 0
	StartActivity(ValidationRptGenerator)
End Sub

Sub btnMiscoded_Click
	If cboReader.SelectedItem = "" Then
		WarningMsg($"NO READER SELECTED"$,$"Select the reader first!"$)
		Return
	End If
	GlobalVar.iReaderID = DBaseFunctions.GetIDbyCode("UserID","tblUsers","EmpName",cboReader.SelectedItem)
	
	If IsThereMiscodedRecords(GlobalVar.iReaderID) = False Then
		WarningMsg($"NO RECORD FOUND"$,$"No Miscoded reading's found!"$)
		Return
	End If
	iValidType = 1
	StartActivity(ValidationRptGenerator)
End Sub

Sub btnLB_Click
	If cboReader.SelectedItem = "" Then
		WarningMsg($"NO READER SELECTED"$,$"Select the reader first!"$)
		Return
	End If
	GlobalVar.iReaderID = DBaseFunctions.GetIDbyCode("UserID","tblUsers","EmpName",cboReader.SelectedItem)
	
	If IsThereRecords($"implosive-dec"$,GlobalVar.iReaderID) = False Then
		WarningMsg($"NO RECORD FOUND"$,$"No Implosive-Decrease reading's found!"$)
		Return
	End If
	iValidType = 2
	StartActivity(ValidationRptGenerator)
End Sub

Sub btnHB_Click
	If cboReader.SelectedItem = "" Then
		WarningMsg($"NO READER SELECTED"$,$"Select the reader first!"$)
		Return
	End If
	GlobalVar.iReaderID = DBaseFunctions.GetIDbyCode("UserID","tblUsers","EmpName",cboReader.SelectedItem)
	
	If IsThereRecords($"implosive-inc"$,GlobalVar.iReaderID) = False Then
		WarningMsg($"NO RECORD FOUND"$,$"No Implosive-Increase reading's found!"$)
		Return
	End If
	iValidType = 3
	StartActivity(ValidationRptGenerator)
End Sub

Sub btnAB_Click
	If cboReader.SelectedItem = "" Then
		WarningMsg($"NO READER SELECTED"$,$"Select the reader first!"$)
		Return
	End If
	GlobalVar.iReaderID = DBaseFunctions.GetIDbyCode("UserID","tblUsers","EmpName",cboReader.SelectedItem)
	
	If IsThereAvgRecords(GlobalVar.iReaderID) = False Then
		WarningMsg($"NO RECORD FOUND"$,$"No Bill Average reading's found!"$)
		Return
	End If
	iValidType = 4
	StartActivity(ValidationRptGenerator)
End Sub

Sub btnUnprinted_Click
	If cboReader.SelectedItem = "" Then
		WarningMsg($"NO READER SELECTED"$,$"Select the reader first!"$)
		Return
	End If
	GlobalVar.iReaderID = DBaseFunctions.GetIDbyCode("UserID","tblUsers","EmpName",cboReader.SelectedItem)
	
	If IsThereUnprintBillRecords(GlobalVar.iReaderID) = False Then
		WarningMsg($"NO RECORD FOUND"$,$"No Unprinted Bill found!"$)
		Return
	End If
	iValidType = 5
	StartActivity(ValidationRptGenerator)
End Sub
#End Region

#Region Styles
Sub TitleCase (s As String) As String
	s = s.ToLowerCase
	Dim m As Matcher = Regex.Matcher("\b(\w)", s)
	Do While m.Find
		Dim i As Int = m.GetStart(1)
		s = s.SubString2(0, i) & s.SubString2(i, i + 1).ToUpperCase & s.SubString(i + 1)
	Loop
	Return s
End Sub

Private Sub WarningMsg(sTitle As String, sMsg As String)
	Dim AlertDialog As AX_CustomAlertDialog

	AlertDialog.Initialize	
	AlertDialog.Initialize.Create _
			.SetDialogStyleName("MyDialogDisableStatus") _	'Manifest style name
			.SetStyle(AlertDialog.STYLE_DIALOGUE) _
			.SetCancelable(False) _
			.SetTitle(sTitle) _
			.SetTitleColor(Colors.Red) _
			.SetTitleTypeface(GlobalVar.FontBold) _
			.SetMessage(sMsg) _
			.SetPositiveText("OK") _
			.SetPositiveColor(Colors.Blue) _
			.SetPositiveTypeface(GlobalVar.FontBold) _
			.SetMessageTypeface(GlobalVar.Font) _
			.SetOnPositiveClicked("WarningMessage") _ 'listeners
			.SetOnViewBinder("WarningDialog") 'listeners
	
	AlertDialog.SetDialogBackground(myCD)
	AlertDialog.Build.Show

End Sub

Private Sub WarningMessage_OnPositiveClicked (View As View, Dialog As Object)
	Dim AlertDialog As AX_CustomAlertDialog
	
	AlertDialog.Initialize.Dismiss(Dialog)
End Sub

Private Sub WarningDialog_OnBindView (View As View, ViewType As Int)
	Dim AlertDialog As AX_CustomAlertDialog
	AlertDialog.Initialize

	If ViewType = AlertDialog.VIEW_TITLE Then ' Title
		Dim lbl As Label = View
		Dim CS As CSBuilder
		
		CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Typeface(Typeface.MATERIALICONS).Size(26).Color(Colors.Red).Append(Chr(0xE002) & "  ")
		CS.Typeface(GlobalVar.Font).Size(22).Append(lbl.Text).Pop

		lbl.Text = CS.PopAll
	End If
	If ViewType = AlertDialog.VIEW_MESSAGE Then
		Dim lbl As Label = View
		lbl.TextSize = 16
		lbl.TextColor = Colors.Gray
	End If
End Sub

Private Sub myCD As ColorDrawable
	Dim mCD As ColorDrawable
	mCD.Initialize(Colors.RGB(240,240,240),0)
	Return mCD
End Sub

Private Sub InfoMsg(sTitle As String, sMsg As String)
	Dim AlertDialog As AX_CustomAlertDialog

	AlertDialog.Initialize	
	AlertDialog.Initialize.Create _
			.SetDialogStyleName("MyDialogDisableStatus") _	'Manifest style name
			.SetStyle(AlertDialog.STYLE_DIALOGUE) _
			.SetCancelable(False) _
			.SetTitle(sTitle) _
			.SetTitleColor(Colors.Red) _
			.SetTitleTypeface(GlobalVar.FontBold) _
			.SetMessage(sMsg) _
			.SetPositiveText("OK") _
			.SetPositiveColor(Colors.Blue) _
			.SetPositiveTypeface(GlobalVar.FontBold) _
			.SetMessageTypeface(GlobalVar.Font) _
			.SetOnPositiveClicked("InfoMessage") _ 'listeners
			.SetOnViewBinder("InfoDialog") 'listeners
	
	AlertDialog.SetDialogBackground(myCD)
	AlertDialog.Build.Show

End Sub

Private Sub InfoMessage_OnPositiveClicked (View As View, Dialog As Object)
	Dim AlertDialog As AX_CustomAlertDialog
	
	AlertDialog.Initialize.Dismiss(Dialog)
End Sub

Private Sub InfoDialog_OnBindView (View As View, ViewType As Int)
	Dim AlertDialog As AX_CustomAlertDialog
	AlertDialog.Initialize

	If ViewType = AlertDialog.VIEW_TITLE Then ' Title
		Dim lbl As Label = View
		Dim CS As CSBuilder
		
		CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Typeface(Typeface.MATERIALICONS).Size(26).Color(GlobalVar.PriColor).Append(Chr(0xE88E) & "  ")
		CS.Typeface(GlobalVar.Font).Size(22).Append(lbl.Text).Pop

		lbl.Text = CS.PopAll
	End If
	If ViewType = AlertDialog.VIEW_MESSAGE Then
		Dim lbl As Label = View
		lbl.TextSize = 16
		lbl.TextColor = Colors.Gray
	End If
End Sub

#End Region


Sub cboReader_ItemClick (Position As Int, Value As Object)
	GlobalVar.iReaderID = DBaseFunctions.GetIDbyCode("UserID","tblUsers","EmpName",cboReader.SelectedItem)
	
	GetValidationCount(GlobalVar.iReaderID, GlobalVar.BranchID, GlobalVar.BillYear, GlobalVar.BillMonth)
	
	MyBadges.SetBadge(MiscodedIcon, iMisCodedCount)
	MyBadges.SetBadge(ZeroIcon, iZeroCount)
	MyBadges.SetBadge(LBIcon, iLBCount)
	MyBadges.SetBadge(HBIcon, iHBCount)
	MyBadges.SetBadge(ABIcon, iAvgCount)
	MyBadges.SetBadge(UnprintedIcon, iUnprintCount)
End Sub

Private Sub GetValidationCount (iUserID As Int, iBranchID As Int, iBillYear As Int, iBillMonth As Int)
	Dim rsCursor As Cursor

	Try
		Starter.strCriteria = "SELECT SUM(CASE WHEN WasMissCoded = 1 THEN 1 ELSE 0 END) as Miscoded, " & _
						  "SUM(CASE WHEN ImplosiveType = 'zero' Then 1 ELSE 0 END) As ZeroCons, " & _
						  "SUM(CASE WHEN ImplosiveType = 'implosive-inc' Then 1 ELSE 0 END) As HighBill, " & _
						  "SUM(CASE WHEN ImplosiveType = 'implosive-dec' Then 1 ELSE 0 END) As LowBill, " & _
						  "SUM(CASE WHEN BillType = 'AB' Then 1 ELSE 0 END) As AverageBill, " & _
						  "SUM(CASE WHEN (PrintStatus = 0 AND WasRead = 1) Then 1 ELSE 0 END) As Unprinted " & _
						  "FROM tblReadings " & _
						  "WHERE BranchID = " & iBranchID & " " & _
						  "AND BillYear = " & iBillYear & " " & _
						  "AND BillMonth = " & iBillMonth & " " & _
						  "AND ReadBy = " & iUserID

		LogColor(Starter.strCriteria, Colors.Yellow)
		rsCursor = Starter.DBCon.ExecQuery(Starter.strCriteria)
		If rsCursor.RowCount > 0 Then
			rsCursor.Position = 0
			iMisCodedCount = rsCursor.GetInt("Miscoded")
			iZeroCount = rsCursor.GetInt("ZeroCons")
			iLBCount = rsCursor.GetInt("LowBill")
			iHBCount = rsCursor.GetInt("HighBill")
			iAvgCount = rsCursor.GetInt("AverageBill")
			iUnprintCount = rsCursor.GetInt("Unprinted")
		Else
			rsCursor.Close
			Return
		End If

	Catch
		Log(LastException)
	End Try
	rsCursor.Close
End Sub
