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

Sub Process_Globals
	Private strReadingData As String
	Private xui As XUI
	Private vibration As B4Avibrate
	Private vibratePattern() As Long

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

	Private intFleStyle As Int
	Private btnSave As ACButton
	Private txtConfirmPassword As DSFloatLabelEditText
	Private txtCurrentUserName As DSFloatLabelEditText
	Private txtNewPassword As DSFloatLabelEditText
	Private txtNewUserName As DSFloatLabelEditText
	Private txtOldPassword As DSFloatLabelEditText
End Sub

Sub Activity_Create(FirstTime As Boolean)
'	Scale.SetRate(0.5)
	Activity.LoadLayout("UserSettings")
	
	GlobalVar.CSTitle.Initialize.Size(13).Append($"USER ACCOUNT SETTING"$).Bold.PopAll
	GlobalVar.CSSubTitle.Initialize.Size(11).Append($"Allows to Modify Account Info."$).PopAll

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
	
	Dim javaobjectInstance As JavaObject
	intFleStyle = xmlIcon.GetResourceId ("style", "floating_hint")

	javaobjectInstance = txtNewUserName
	javaobjectInstance.RunMethod ("setHintTextAppearance", Array (intFleStyle))

	javaobjectInstance = txtNewUserName
	javaobjectInstance.RunMethod ("setHintTextAppearance", Array (intFleStyle))

	javaobjectInstance = txtOldPassword
	javaobjectInstance.RunMethod ("setHintTextAppearance", Array (intFleStyle))

	javaobjectInstance = txtNewPassword
	javaobjectInstance.RunMethod ("setHintTextAppearance", Array (intFleStyle))

	javaobjectInstance = txtConfirmPassword
	javaobjectInstance.RunMethod ("setHintTextAppearance", Array (intFleStyle))
End Sub

Sub Activity_KeyPress (KeyCode As Int) As Boolean 'Return True to consume the event
	If KeyCode = 4 Then
		Return False
	Else
		Return True
	End If
End Sub

Sub Activity_Resume
	txtCurrentUserName.Text = GlobalVar.UserName
	vibratePattern = Array As Long(500, 500, 300, 500)
End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub ToolBar_NavigationItemClick
	If CustomFunctions.ConfirmYN($"Close User's Settings now?"$, $"CONFIRM CLOSE"$, $"YES"$, $""$, $"CANCEL"$) = True Then
		Activity.Finish
	End If
End Sub

Private Sub ShowSuccessDialog
	Dim csTitle, csContent As CSBuilder
	csTitle.Initialize.Color(GlobalVar.PriColor).Bold.Size(16).Append($"Download Complete"$).PopAll
	csContent.Initialize.Size(14).Color(GlobalVar.PriColor).Bold.Append($"Reading Data for the specified Reader were successfully downloaded."$).PopAll
	
	MatDialogBuilder.Initialize("SettingsOK")
	MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIGHT)
	MatDialogBuilder.Title(csTitle).TitleGravity(MatDialogBuilder.GRAVITY_START)
	MatDialogBuilder.IconRes(GlobalVar.InfoIcon).LimitIconToDefaultSize
	MatDialogBuilder.Content(csContent)
	MatDialogBuilder.PositiveText($"OK"$).PositiveColor(GlobalVar.PriColor)
	MatDialogBuilder.NegativeText($"Download Another"$).NegativeColor(Colors.Red)
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

Private Sub UpdateUserLocally(sNewUserName As String, sNewPass As String, iUserID As Int) As Boolean
	Dim blnRet As Boolean
	Starter.DBCon.BeginTransaction
	Try
		Starter.strCriteria = "UPDATE tblUsers SET UserName = ?, UserPassword = ? " & _
							  "WHERE UserID = " & iUserID
		Starter.DBCon.ExecNonQuery2(Starter.strCriteria, Array As String(sNewUserName, sNewPass))
		Starter.DBCon.TransactionSuccessful
		blnRet = True
	Catch
		Log(LastException)
		blnRet = False
	End Try
	Starter.DBCon.EndTransaction
	Return blnRet
End Sub

Sub btnSave_Click
	If GlobalVar.SF.Len(txtNewUserName.Text.Trim) <= 0 Then
		txtNewUserName.RequestFocus
		txtNewUserName.SelectAll
		snack.Initialize("", Activity, $"New User Name field cannot be blank."$, snack.DURATION_LONG)
		SetSnackBarBackground(snack, Colors.Red)
		SetSnackBarTextColor(snack, Colors.White)
		snack.Show
		vibration.vibrateOnce(1500)
		Return
	End If
	
	If GlobalVar.SF.Len(txtOldPassword.Text.Trim) <= 0 Then
		txtOldPassword.RequestFocus
		txtOldPassword.SelectAll
		snack.Initialize("", Activity, $"Please enter your CURRENT password."$, snack.DURATION_LONG)
		SetSnackBarBackground(snack, Colors.Red)
		SetSnackBarTextColor(snack, Colors.White)
		snack.Show
		vibration.vibrateOnce(1500)
		Return
	End If
	
	If txtOldPassword.Text <> GlobalVar.UserPW Then
		txtOldPassword.RequestFocus
		txtOldPassword.SelectAll
		snack.Initialize("", Activity, $"Password not match."$, snack.DURATION_LONG)
		SetSnackBarBackground(snack, Colors.Red)
		SetSnackBarTextColor(snack, Colors.White)
		snack.Show
		vibration.vibrateOnce(1500)
		Return
	End If
	
	If GlobalVar.SF.Len(txtNewPassword.Text.Trim) <= 0 Then
		txtNewPassword.RequestFocus
		snack.Initialize("", Activity, $"Please enter your NEW password."$, snack.DURATION_LONG)
		SetSnackBarBackground(snack, Colors.Red)
		SetSnackBarTextColor(snack, Colors.White)
		snack.Show
		vibration.vibrateOnce(1500)
		Return
	End If
	
	If txtConfirmPassword.Text <> txtNewPassword.Text Then
		txtConfirmPassword.Text = ""
		txtNewPassword.RequestFocus
		txtNewPassword.SelectAll
		snack.Initialize("", Activity, $"Password not match."$, snack.DURATION_LONG)
		SetSnackBarBackground(snack, Colors.Red)
		SetSnackBarTextColor(snack, Colors.White)
		snack.Show
		vibration.vibrateOnce(1500)
		Return
	End If
	
	If UpdateUserLocally(txtNewUserName.Text, txtNewPassword.Text, GlobalVar.UserID) = True Then
		
	End If
	
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

