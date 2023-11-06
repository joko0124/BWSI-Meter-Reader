B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=6.8
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: True
	#IncludeTitle: False
#End Region
#Extends: android.support.v7.app.AppCompatActivity

Sub Process_Globals
	Private strReadingData As String
	Private xui As XUI
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Dim ActionBarButton As ACActionBar
	Private ToolBar As ACToolBarDark
	Private xmlIcon As XmlLayoutBuilder
	
	'Material Dialog
	Private MatDialogBuilder As MaterialDialogBuilder
	Private snack As DSSnackbar

	Private pnlSettings As Panel
	Private ctrMonth, ctrYear As Int
	Private cboBillMonth As ACSpinner
	Private cboBillYear As ACSpinner
	Private btnOk As ACButton
	Private cdUpdate As ColorDrawable

	Dim lngYear As Long
	Dim intMonth As Int
	Dim strBillPeriod As String
	Private lblBillPeriod As Label
End Sub

Sub Activity_Create(FirstTime As Boolean)
'	Scale.SetRate(0.5)
	Activity.LoadLayout("ReadingSettings")
	
	GlobalVar.CSTitle.Initialize.Size(13).Append($"BILLING PERIOD SETTING"$).Bold.PopAll
	GlobalVar.CSSubTitle.Initialize.Size(11).Append($"Allows to Change Billing Period."$).PopAll
	
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
	
	cdUpdate.Initialize2(GlobalVar.PriColor, 25, 0, Colors.Transparent)
	btnOk.Background = cdUpdate
End Sub

Sub Activity_KeyPress (KeyCode As Int) As Boolean 'Return True to consume the event
	If KeyCode = 4 Then
		Return False
	Else
		Return True
	End If
End Sub

Sub Activity_Resume
	pnlSettings.Visible = True
	FillSpinners
	lblBillPeriod.Text = GlobalVar.BillPeriod
End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub ToolBar_NavigationItemClick
	Activity.Finish
End Sub

Private Sub FillSpinners
	Dim theDate As Long
	Dim lngYear, BillDate As Long
	Dim sBillYear As String
	Dim lMonth As Long
	
	cboBillMonth.Clear
	cboBillYear.Clear
	
	lMonth = DateTime.GetMonth(DateTime.Now)
	LogColor(lMonth, Colors.Yellow)
	
	If lMonth = 1 Then
		lngYear = (DateTime.GetYear(DateTime.Now) - 1)
	Else
		lngYear = (DateTime.GetYear(DateTime.Now))
	End If
	
'	lngYear = (DateTime.GetYear(DateTime.Now) - 1)
	GlobalVar.BillYear = DBaseFunctions.GetBillYear(GlobalVar.BranchID)
	GlobalVar.BillMonth = DBaseFunctions.GetBillMonth(GlobalVar.BranchID)
	DateTime.DateFormat = "MM/dd/yyyy"
	BillDate = DateTime.DateParse(GlobalVar.BillMonth & "/01/" & GlobalVar.BillYear)
	Log(BillDate)
	
	GlobalVar.BillMonthName = DateUtils.GetMonthName(BillDate)
	sBillYear = GlobalVar.BillYear
	
	For ctrMonth = 1 To 12
		DateTime.DateFormat = "MM/dd/yyyy"
		theDate = DateTime.DateParse(ctrMonth & "/01/" & lngYear)
		cboBillMonth.Add(DateUtils.GetMonthName(theDate))
	Next
	cboBillMonth.SelectedIndex = cboBillMonth.IndexOf(GlobalVar.BillMonthName)
	
	For ctrYear = lngYear To 2050
		cboBillYear.Add(ctrYear)
	Next
	cboBillYear.SelectedIndex = cboBillYear.IndexOf(sBillYear)
	LogColor(GlobalVar.BillYear,Colors.Yellow)
	
End Sub
#Region SnackBar Settings
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
#End Region

#Region Password
Private Sub ShowPasswordDialog
	MatDialogBuilder.Initialize("RetPassword")
	MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIGHT)
	MatDialogBuilder.Title($"Password Required"$).TitleColor(GlobalVar.PriColor)
	MatDialogBuilder.Content($"Input your Password."$)
	MatDialogBuilder.InputType(MatDialogBuilder.TYPE_TEXT_VARIATION_PASSWORD)
	MatDialogBuilder.Input("Enter your Password Here...", "")
	MatDialogBuilder.PositiveText($"Ok"$).PositiveColor(GlobalVar.PriColor)
	MatDialogBuilder.NegativeText($"Cancel"$).NegativeColor(Colors.Red)
	MatDialogBuilder.CanceledOnTouchOutside(False)
	MatDialogBuilder.AlwaysCallInputCallback
	MatDialogBuilder.IconRes("ic_security_black_36dp2").LimitIconToDefaultSize
	MatDialogBuilder.Show
End Sub

Private Sub RetPassword_InputChanged (mDialog As MaterialDialog, sPassword As String)
	Dim csBuild As CSBuilder
	If sPassword.Length <= 0 Then
		csBuild.Initialize.Bold.Color(Colors.Red).Append($"Enter your Password to Continue..."$).PopAll
		mDialog.Content = csBuild
		mDialog.EnableActionButton(mDialog.ACTION_POSITIVE, False)
	Else
		If sPassword <> GlobalVar.UserPW Then
			mDialog.EnableActionButton(mDialog.ACTION_POSITIVE, False)
			csBuild.Initialize.Bold.Color(Colors.Red).Append($"Password is Incorrect!"$).PopAll
			mDialog.Content = csBuild
		Else
			mDialog.EnableActionButton(mDialog.ACTION_POSITIVE, True)
			csBuild.Initialize.Bold.Color(Colors.Black).Append($"Password is Ok!"$).PopAll
			mDialog.Content = csBuild
		End If
	End If
End Sub

Private Sub RetPassword_ButtonPressed (mDialog As MaterialDialog, sAction As String)
	Select Case sAction
		Case mDialog.ACTION_POSITIVE
			If UpdateReadingSettings(intMonth, lngYear, strBillPeriod) = False Then Return
			GlobalVar.BillYear = lngYear
			GlobalVar.BillMonth = intMonth
			GlobalVar.BillPeriod = strBillPeriod
			ShowSuccessDialog
		Case mDialog.ACTION_NEGATIVE
			Return
	End Select
End Sub

Private Sub ShowSuccessDialog
	Dim csTitle, csContent As CSBuilder
	csTitle.Initialize.Color(GlobalVar.PriColor).Bold.Size(16).Append($"SUCCESS"$).PopAll
	csContent.Initialize.Size(14).Color(GlobalVar.PriColor).Bold.Append($"Billing Period was successfully updated."$).PopAll
	
	MatDialogBuilder.Initialize("SettingsOK")
	MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIGHT)
	MatDialogBuilder.Title(csTitle).TitleGravity(MatDialogBuilder.GRAVITY_START)
	MatDialogBuilder.IconRes(GlobalVar.InfoIcon).LimitIconToDefaultSize
	MatDialogBuilder.Content(csContent)
	MatDialogBuilder.PositiveText($"OK"$).PositiveColor(GlobalVar.PriColor)
'	MatDialogBuilder.NegativeText($"Download Another"$).NegativeColor(Colors.Red)
	MatDialogBuilder.CanceledOnTouchOutside(False)
	MatDialogBuilder.Show
End Sub

Private Sub SettingsOK_ButtonPressed (mDialog As MaterialDialog, sAction As String)
	Select Case sAction
		Case mDialog.ACTION_POSITIVE
			Activity.Finish
		Case mDialog.ACTION_NEGATIVE
			Return
	End Select
End Sub
#End Region

Sub btnOk_Click
	Dim DateSpecified As Long
	Dim YearSpecified As Long
	Dim MonthNameSpecified As String
	Dim MonthSpecified As Int
	
	YearSpecified = GlobalVar.SF.Val(cboBillYear.SelectedItem)
	MonthNameSpecified = cboBillMonth.SelectedItem
	
	DateTime.DateFormat = "MMMM/dd/yyyy"
	DateSpecified = DateTime.DateParse(MonthNameSpecified & "/01/" & YearSpecified)
	Log(DateUtils.GetMonthName(DateSpecified) & " " & DateTime.GetYear(DateSpecified))
	
	strBillPeriod = DateUtils.GetMonthName(DateSpecified) & " " & YearSpecified
	intMonth = DateTime.GetMonth(DateSpecified)
	lngYear = YearSpecified
	
	
	If lngYear > DateTime.GetYear(DateTime.Now) Then
		'Cannot set year ahead
		snack.Initialize("", Activity, $"Unable to set Bill Year due to specified year is later than current."$, snack.DURATION_SHORT)
		SetSnackBarBackground(snack, Colors.Red)
		SetSnackBarTextColor(snack, Colors.White)
		snack.Show
		Return
	Else If DateTime.GetYear(DateTime.Now) - lngYear = 1 Then
		If intMonth < 12 And DateTime.GetMonth(DateTime.Now) = 1 Then
			'Year ahead 1 and Month should be December only
			snack.Initialize("", Activity, $"Specified year specified is later than the current year."$, snack.DURATION_SHORT)
			SetSnackBarBackground(snack, Colors.Red)
			SetSnackBarTextColor(snack, Colors.White)
			snack.Show
			Return
		End If
	Else If lngYear = DateTime.GetYear(DateTime.Now) Then
		If intMonth > DateTime.GetMonth(DateTime.Now) Then
			'Current month and later
			snack.Initialize("", Activity, $"Unable to set Bill Month due to specified month is later than current."$, snack.DURATION_SHORT)
			SetSnackBarBackground(snack, Colors.Red)
			SetSnackBarTextColor(snack, Colors.White)
			snack.Show
			Return
		Else If DateTime.GetMonth(DateTime.Now) - intMonth > 1 Then
			'2 months later
			snack.Initialize("", Activity, $"Unable to set Bill Month due to specified month is earlier than current."$, snack.DURATION_SHORT)
			SetSnackBarBackground(snack, Colors.Red)
			SetSnackBarTextColor(snack, Colors.White)
			snack.Show
			Return
		End If
	Else
		'Specified year is later than current year and current month
		snack.Initialize("", Activity, $"Unable to set Bill Year due to specified year is earlier than current."$, snack.DURATION_SHORT)
		SetSnackBarBackground(snack, Colors.Red)
		SetSnackBarTextColor(snack, Colors.White)
		snack.Show
		Return
	End If
	ShowPasswordDialog
End Sub

Private Sub UpdateReadingSettings(iMonth As Int, lYear As Long, sBillPeriod As String) As Boolean
	Dim blnRet As Boolean
	Starter.DBCon.BeginTransaction
	Try
		Starter.strCriteria = "UPDATE tblSysParam SET BillMonth = ?, BillYear = ?, BillPeriod = ? " & _
						  "WHERE BranchID = " & GlobalVar.BranchID
		Starter.DBCon.ExecNonQuery2(Starter.strCriteria, Array As String(iMonth, lYear, sBillPeriod))
		Starter.DBCon.TransactionSuccessful
		blnRet = True
	Catch
		Log(LastException)
		blnRet = False
	End Try
	Starter.DBCon.EndTransaction
	Return blnRet
End Sub