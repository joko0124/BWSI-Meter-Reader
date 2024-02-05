B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=10
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: True
	#IncludeTitle: False
	#Extends : androidx.appcompat.app.AppCompatActivity
#End Region

Sub Process_Globals
	Private tglWifi As ToggleWifiData
	Private WiFiStatus As Boolean
	Private SF As StringFunctions
	
	Public Permissions As RuntimePermissions
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Private btnLogin As ACButton
	Private chkShowPass As CheckBox
'	Private txtUserName As DSFloatLabelEditText
	Private txtPassword As B4XFloatTextField
	
	Dim Users As theUser
	Dim UserBranch As theBranch
	Dim Companies As theCompany
	
	Dim URLName As String
	Dim Job1 As HttpJob

	Private btnSettings As DSFloatingActionButton
	Private MatDialogBuilder As MaterialDialogBuilder
	Private strPassword As String
	Dim snack As DSSnackbar
	Dim NewIP As String
	Dim CD As ColorDrawable
	
	Dim xui As XUI
	Private vibration As B4Avibrate
	Private vibratePattern() As Long

	'Branch Dialog
	Private rsBranches As Cursor
'	Private cboBranches As ACSpinner
	Private lblBranch As Label
	Private btnBranch As DSFloatingActionButton
	
	Private intSettingsFlag As Int
	Private strSelectedBranch As String
	Private intFleStyle As Int
	Private xmlIcon As XmlLayoutBuilder
	Private cboBranches As Spinner
	Private lblSelectedBranch As Label
	Private pnlBranches As Panel
	Private pnlSearchBranch As Panel
	Private SV As SearchView
	Private SearchFor As String
	Private btnCancel As ACButton
	Private CD As ColorDrawable
	Private cdTextBox As ColorDrawable
	
	Private SelectedBranchID As Double
	Private SelectedBranchName As String
	
	Private sURL As String
	Private pnlSearchBranches As Panel
	Private txtUserName As B4XFloatTextField
	Private InpTyp As SLInpTypeConst
	Private blnWifiOn As Boolean
	Private blnUserClosed As Boolean
	
	Dim Alert As AX_CustomAlertDialog
	Dim imeKeyBoard As IME
End Sub
#Region Activity Modules
Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	MyScale.SetRate(0.5)
	
	Activity.LoadLayout("LoginNew")
	CD.Initialize2(0xFF1976D2, 25, 0, 0xFF000000)
	btnLogin.Background = CD
	
	GlobalVar.ServerAddress = DBaseFunctions.GetServerIP
	GlobalVar.BranchID = DBaseFunctions.GetBranchID
	GlobalVar.CompanyID = DBaseFunctions.GetCompanyID(GlobalVar.BranchID)
	GetBGImage(GlobalVar.CompanyID)
	
'	CustomFunctions.ClearUserData
	LogColor(GlobalVar.BranchID, Colors.Blue)
	tglWifi.Initialize
	WiFiStatus = tglWifi.isWIFI_enabled
	txtUserName.Text = ""
	txtPassword.Text = ""
	txtUserName.RequestFocusAndShowKeyboard
	blnWifiOn = False
	pnlSearchBranches.Visible = False
	CD.Initialize2(GlobalVar.PriColor, 5dip, 0dip,Colors.White)
	btnCancel.Background = CD
	
	SF.Initialize
	Dim r As Reflector
	r.Target = r.GetContext
	r.Target = r.RunMethod("getResources")
	r.Target = r.RunMethod("getDisplayMetrics")
	LogColor($"X DPI: "$ & r.GetField("xdpi"),Colors.Yellow)
	LogColor($"Y DPI: "$ & r.GetField("ydpi"),Colors.Yellow)

	LogColor($"WIDTH: "$ & GetDeviceLayoutValues.Width, Colors.Magenta)
	LogColor($"HEIGHT: "$ & GetDeviceLayoutValues.Height,Colors.Magenta)
    
	Log(GetDeviceLayoutValues.Width/r.GetField("xdpi"))
	Log(GetDeviceLayoutValues.Height/r.GetField("ydpi"))
	
	txtUserName.mBase.SetColorAndBorder(Colors.Transparent,0,Colors.Transparent,0)
	txtPassword.mBase.SetColorAndBorder(Colors.Transparent,0,Colors.Transparent,0)
	GlobalVar.ServerAddress = DBaseFunctions.GetServerIP
	GlobalVar.BranchID = DBaseFunctions.GetBranchID
	GlobalVar.CompanyID = DBaseFunctions.GetCompanyID(GlobalVar.BranchID)
	GetBGImage(GlobalVar.CompanyID)
	pnlSearchBranches.Visible = False
	
	CheckPermissions
End Sub

Sub Activity_KeyPress (KeyCode As Int) As Boolean 'Return True to consume the event
	If KeyCode = 4 Then
		ConfirmExitApp($"CONFIRM EXIT"$, $"Do you want to Close "$ & Application.LabelName & $"?"$)
		Return True
	Else
		Return False
	End If
End Sub

Sub Activity_Resume
	If blnUserClosed = False Then
		GlobalVar.ServerAddress = DBaseFunctions.GetServerIP
		GlobalVar.BranchID = DBaseFunctions.GetBranchID
		GlobalVar.CompanyID = DBaseFunctions.GetCompanyID(GlobalVar.BranchID)
		GetBGImage(GlobalVar.CompanyID)
		pnlSearchBranches.Visible = False
	End If
End Sub

Sub Activity_Pause (UserClosed As Boolean)
	UserClosed = blnUserClosed
	If UserClosed = True Then
		ExitApplication
	End If
End Sub

Private Sub CheckPermissions

	'    Use Whatever permissions you require here - This is my set of permissions in one of my Apps
	' Also CheckAndRequest - Checks - If not granted - then requests i.o.w's if it is the second time you are entering the App - it will check - if all is in place the user will not be requested to give permissions again.

	Log("Checking Permissions")
  
	Permissions.CheckAndRequest(Permissions.PERMISSION_READ_EXTERNAL_STORAGE)
	Permissions.CheckAndRequest(Permissions.PERMISSION_WRITE_EXTERNAL_STORAGE)
	Permissions.CheckAndRequest(Permissions.PERMISSION_ACCESS_COARSE_LOCATION)
	Permissions.GetAllSafeDirsExternal("")
  
	Permissions.CheckAndRequest(Permissions.PERMISSION_CAMERA)
	Permissions.CheckAndRequest(Permissions.PERMISSION_BODY_SENSORS)
	Return
  
End Sub

Sub Activity_PermissionResult (Permission As String, Result As Boolean)
	If Permission = Permissions.PERMISSION_CAMERA Then
		Result = True
		LogColor("YOU CAN USE THE CAMERA",Colors.Red)
	Else
		Result = False
	End If
	Log (Permission)
End Sub
#End Region


Sub chkShowPass_CheckedChange(Checked As Boolean)
	If Checked = True Then
		
	Else
'		txtPassword.PasswordMode = True
	End If
End Sub

Sub btnLogin_Click
	WiFiStatus = tglWifi.isWIFI_enabled

	If SF.Len(SF.Trim(txtUserName.Text)) <= 0 Then
		vibration.vibrateOnce(1000)
		UserWarningMsg($"E R R O R"$, $"Oooops! User Name not specified."$)
		txtUserName.RequestFocusAndShowKeyboard
		Return
	End If
	
	If SF.Len(SF.Trim(txtPassword.Text)) <= 0 Then
		vibration.vibrateOnce(1000)
		UserWarningMsg($"E R R O R"$, $"Oooops! Password not specified."$)
		txtPassword.RequestFocusAndShowKeyboard
		Return
	End If

	If DBaseFunctions.IsUserNameExists(txtUserName.Text, GlobalVar.BranchID) = True Then
		If SF.Len(SF.Trim(txtPassword.Text)) <= 0 Then
			vibration.vibrateOnce(1000)
			UserWarningMsg($"E R R O R"$, $"Oooops! User Password not specified."$)
			txtPassword.RequestFocusAndShowKeyboard
			Return
		End If

		If Not(DBaseFunctions.IsPasswordCorrect(txtUserName.Text, txtPassword.Text, GlobalVar.BranchID)) Then
			vibration.vibrateOnce(1000)
			UserWarningMsg($"E R R O R"$, $"Oooops! User Password is incorrect."$)
			txtPassword.Text = ""
			txtPassword.RequestFocusAndShowKeyboard
			Return
		End If
	End If

	ProgressDialogShow2($"Checking User's Credentials."$ & CRLF & $"Please Wait..."$, False)
	
	If DBaseFunctions.IsUserExists(txtUserName.Text, txtPassword.Text, GlobalVar.BranchID) = True Then
		ProgressDialogShow2($"User's found. Fetching User's Info..."$, False)
		
		If DBaseFunctions.IsThereUserInfo(txtUserName.Text, txtPassword.Text) = True Then
			DBaseFunctions.GetBranchInfo(GlobalVar.BranchID)
		Else
			vibration.vibrateOnce(1000)
			UserWarningMsg($"E R R O R"$, $"Unable to fetch User's Information due to incomplete credentials!"$)
'			DispErrorMsg($"Unable to fetch User's Information due to incomplete credentials!"$, $"ERROR"$)
'			txtUserName.RequestFocus
'			txtUserName.SelectAll
			txtPassword.Text = ""
			Return
		End If

		If DBaseFunctions.GetBillSettings(GlobalVar.BranchID) = False Then
			vibration.vibrateOnce(1000)
			UserWarningMsg($"E R R O R"$, $"Billing Settings not initializes!"$ & CRLF & $"Please contact your Android Developer for assistance."$)
'			DispErrorMsg($"$Billing Settings not initializes!"$ & CRLF & $"Please contact your Android Developer for assistance."$, $"ERROR"$)
			Return
		End If
		
		ProgressDialogHide
		CustomFunctions.SaveUserSettings
		Activity.Finish
		StartActivity(MainScreen)
	Else
		If WiFiStatus = False Then
'			Dim csMsg As CSBuilder
'			csMsg.Initialize.Color(GlobalVar.PriColor).Size(16).Append($"Do you want to turn on WiFi Connection?"$).PopAll
'			Dim icon As B4XBitmap = xui.LoadBitmapResize(File.DirAssets, "warning.png", 60dip, 60dip, True)
'			Dim response As Object = xui.Msgbox2Async(csMsg, $"WiFi Connection Needed!"$, "Yes", "Cancel", "", icon)
'			Wait For (response) Msgbox_Result (Result As Int)
			ShowWiFiWarning($"Enable WiFi"$,$"Please Turn On WiFi device"$ & CRLF & $"to Access the Elite Server!"$)
			Return
		Else
			If GlobalVar.ServerAddress = Null Or GlobalVar.ServerAddress = "" Then
				ProgressDialogHide
				vibration.vibrateOnce(1000)
				snack.Initialize("SetServer", Activity,"Server IP not configured...",snack.DURATION_LONG)
				CustomFunctions.SetSnackBarBackground(snack,Colors.Red)
				CustomFunctions.SetSnackBarTextColor(snack,Colors.White, "")
				snack.SetAction("Configure")
				snack.Show
				Return
			End If
			Log($"Is connected to server: "$ & CustomFunctions.IsConnectedToServer(GlobalVar.ServerAddress))
			
			If CustomFunctions.IsConnectedToServer(GlobalVar.ServerAddress) = True Then
				ToastMessageShow($"Connected to Server"$, True)
			Else
				vibration.vibrateOnce(1000)
				snack.Initialize("SetServer", Activity,"Server IP not configured...", snack.DURATION_LONG)
				CustomFunctions.SetSnackBarBackground(snack,Colors.Red)
				CustomFunctions.SetSnackBarTextColor(snack,Colors.White, "SetServer")
				snack.SetAction("Configure")
				snack.Show
				Return
			End If

			
			ProgressDialogShow2($"Checking Database Server Connection."$ & CRLF & $"Please Wait..."$, False)
			
			GetUserAccount(GlobalVar.BranchID, txtUserName.Text, txtPassword.Text)
		End If
	End If
End Sub

Private Sub GetUserAccount(iBranchID As Int, sUserName As String, sUserPassword As String)
	Dim URLName As String
	Dim RetVal As String

	Dim j As HttpJob

	j.Initialize("",Me)
	URLName = GlobalVar.ServerAddress & GlobalVar.ControllerPrefix & "userAccount"
	sURL = URLName & "?BranchID=" & iBranchID & "&UserName=" & sUserName & "&UserPassword=" & sUserPassword
	LogColor(sURL, Colors.Blue)
	
	j.Download2(URLName, Array As String("BranchID", iBranchID, "UserName", sUserName, "UserPassword", sUserPassword))
	
	ProgressDialogShow2($"Downloading User Information..."$, False)
	Wait For (j) JobDone(j As HttpJob)
	If j.Success Then
		RetVal = j.GetString
		Log(RetVal)
		
		If RetVal = "[]" Then
			snack.Initialize("", Activity, $""Either User Name or Password is Incorrect!"$, snack.DURATION_LONG)
			CustomFunctions.SetSnackBarBackground(snack, Colors.Red)
			CustomFunctions.SetSnackBarTextColor(snack, Colors.White, "")
			snack.Show
			j.Release
			txtUserName.RequestFocusAndShowKeyboard
			Return
		End If
		
		SaveUserInfo(RetVal)
	Else
		ProgressDialogHide
		j.Release
		vibration.vibrateOnce(2000)
		snack.Initialize("",Activity,$"Unable to Fetch User Information due to "$ & j.ErrorMessage, snack.DURATION_SHORT)
		CustomFunctions.SetSnackBarBackground(snack, Colors.Red)
		CustomFunctions.SetSnackBarTextColor(snack, Colors.White, "")
		snack.Show
		LogColor(j.ErrorMessage, Colors.Red)
	End If
	j.Release
	ProgressDialogHide
End Sub

Private Sub SaveUserInfo(sReturnVal As String)
	Dim parser As JSONParser
	Dim root As List
	
	parser.Initialize(sReturnVal)
	root = parser.NextArray

	Try
		If DBaseFunctions.IsUserExists(txtUserName.Text, txtPassword.Text, GlobalVar.BranchID) = False Then
			For Each MP As Map In root
				Starter.strCriteria = "INSERT INTO tblUsers VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
				Starter.DBCon.AddNonQueryToBatch(Starter.strCriteria, Array(MP.Get("id"), MP.Get("branch_id"), MP.Get("name"), MP.Get("username"), MP.Get("androidpassword"), MP.Get("Module1"), MP.Get("Module2"), MP.Get("Module3"), MP.Get("Module4"), MP.Get("Module5"), MP.Get("Module6"), $"0"$))
			Next
			
			Dim SenderFilter As Object = Starter.DBCon.ExecNonQueryBatch("SQL")
			Wait For (SenderFilter) SQL_NonQueryComplete (Success As Boolean)

			If Success Then
				snack.Initialize("", Activity, $"User Information has been successfully downloaded."$, snack.DURATION_SHORT)
				CustomFunctions.SetSnackBarBackground(snack,Colors.White)
				CustomFunctions.SetSnackBarTextColor(snack, GlobalVar.PriColor, "")
				snack.Show

				DBaseFunctions.GetUserInfo(txtUserName.Text, txtPassword.Text)
				DBaseFunctions.GetBranchInfo(GlobalVar.BranchID)
				
				If DBaseFunctions.GetBillSettings(GlobalVar.BranchID) = False Then
					snack.Initialize("", Activity, $"Billing Settings not initializes"$ & CRLF & $"Please contact your Android Developer for assistance."$, snack.DURATION_SHORT)
					CustomFunctions.SetSnackBarBackground(snack,Colors.White)
					CustomFunctions.SetSnackBarTextColor(snack, GlobalVar.PriColor, "")
					snack.Show
					Return
				End If

				If GlobalVar.Mod5 = 1 Then
					DBaseFunctions.ZapMeterReader
					DownloadReaders(GlobalVar.BranchID)
					Return
				End If
				
				CustomFunctions.SaveUserSettings
				Activity.Finish
				StartActivity(MainScreen)
			End If
		End If
	Catch
		vibration.vibrateOnce(2000)
		snack.Initialize("", Activity, $"Unable to save User Information due to "$ & LastException.Message, snack.DURATION_SHORT)
		CustomFunctions.SetSnackBarBackground(snack, Colors.Red)
		CustomFunctions.SetSnackBarTextColor(snack, Colors.White, "")
		snack.Show
		Log(LastException)
	End Try
End Sub

Sub txtUserName_FocusChanged (HasFocus As Boolean)
	If HasFocus And txtUserName.Text.Length > 0 Then
'		txtUserName.Focused
	End If
End Sub

Sub txtPassword_FocusChanged (HasFocus As Boolean)
	If HasFocus And txtPassword.Text.Length > 0 Then
		'		txtPassword.SelectAll
	End If
End Sub

Sub btnSettings_Click
	intSettingsFlag = 1
'	ShowIPSettings
	ShowNewIPSettings
End Sub

Private Sub ShowIPSettings()
	Dim csContent As CSBuilder
	GlobalVar.ServerAddress = DBaseFunctions.GetServerIP
	Log(GlobalVar.ServerAddress)

	csContent.Initialize.Size(12).Color(Colors.Red).Append($"WARNING! Be sure to enter the correct server's IP Address."$).Color(Colors.Gray).Append(CRLF & $"This will be used for downloading/uploading reading data."$).PopAll
	
	MatDialogBuilder.Initialize("IPSettings")
	MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIGHT)
	MatDialogBuilder.Title($"SERVER IP ADDRESS SETTINGS"$).TitleColor(Colors.Red).TitleGravity(MatDialogBuilder.GRAVITY_START)
	MatDialogBuilder.IconRes(GlobalVar.WarningIcon).LimitIconToDefaultSize
	MatDialogBuilder.InputType(Bit.Or(Bit.Or(MatDialogBuilder.TYPE_CLASS_TEXT, MatDialogBuilder.TYPE_TEXT_FLAG_CAP_SENTENCES), MatDialogBuilder.TYPE_TEXT_VARIATION_PERSON_NAME))
	MatDialogBuilder.Input2($"Enter your IP Address here..."$, GlobalVar.ServerAddress, False)
	MatDialogBuilder.AlwaysCallInputCallback
	MatDialogBuilder.Content(csContent)
	MatDialogBuilder.ContentColor(Colors.Black)
	MatDialogBuilder.PositiveText($"SAVE SETTINGS"$).PositiveColor(GlobalVar.PriColor)
	MatDialogBuilder.NegativeText($"CANCEL"$).NegativeColor(Colors.Red)
	MatDialogBuilder.CanceledOnTouchOutside(False)
	MatDialogBuilder.Show
End Sub

Private Sub IPSettings_InputChanged (mDialog As MaterialDialog, sIP As String)
	If sIP.Length = 0 Then
		mDialog.EnableActionButton(mDialog.ACTION_POSITIVE, False)
	Else
		mDialog.EnableActionButton(mDialog.ACTION_POSITIVE, True)
	End If
	NewIP = sIP
End Sub

Sub IPSettings_ButtonPressed (Dialog As MaterialDialog, Action As String)
	Select Case Action
		Case Dialog.ACTION_POSITIVE
			ShowAdminPassword
		
		Case Dialog.ACTION_NEGATIVE
			snack.Initialize("RetryButton", Activity, "Setting Server's IP Address Cancelled!", snack.DURATION_LONG)
			CustomFunctions.SetSnackBarBackground(snack,Colors.Red)
			CustomFunctions.SetSnackBarTextColor(snack,Colors.White, True)
			snack.Show
			Return
	End Select
End Sub

Private Sub ShowAdminPassword()
	Dim csContent As CSBuilder

	csContent.Initialize.Size(12).Color(Colors.Gray).Append($"Enter Administrative Password to Continue."$).PopAll
	
	MatDialogBuilder.Initialize("AdminPassword")
	MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIGHT)
	MatDialogBuilder.Title($"ADMINISTRATIVE PASSWORD"$).TitleColor(Colors.Red).TitleGravity(MatDialogBuilder.GRAVITY_START)
	MatDialogBuilder.IconRes(GlobalVar.WarningIcon).LimitIconToDefaultSize
	MatDialogBuilder.InputType(MatDialogBuilder.TYPE_TEXT_VARIATION_PASSWORD)
	MatDialogBuilder.Input2($"Enter Admin Password here..."$, $""$, False)
	MatDialogBuilder.AlwaysCallInputCallback
	MatDialogBuilder.Content(csContent)
	MatDialogBuilder.ContentColor(Colors.Black)
	MatDialogBuilder.PositiveText($"OK"$).PositiveColor(GlobalVar.PriColor)
	MatDialogBuilder.NegativeText($"CANCEL"$).NegativeColor(Colors.Red)
	MatDialogBuilder.CanceledOnTouchOutside(False)
	MatDialogBuilder.Show
End Sub

Private Sub AdminPassword_InputChanged (mDialog As MaterialDialog, sPassword As String)
	If sPassword.Length = 0 Then
		mDialog.EnableActionButton(mDialog.ACTION_POSITIVE, False)
	Else
		mDialog.EnableActionButton(mDialog.ACTION_POSITIVE, True)
	End If
	strPassword = sPassword
End Sub

Sub AdminPassword_ButtonPressed (Dialog As MaterialDialog, Action As String)
	Select Case Action
		Case Dialog.ACTION_POSITIVE
			Select intSettingsFlag
				Case 1
					If strPassword <>GlobalVar.AdminPassword Then
						vibration.vibrateOnce(2000)
						snack.Initialize("RetryButton", Activity, "Password is Incorrect!", snack.DURATION_LONG)
						snack.SetAction("RETRY")
						CustomFunctions.SetSnackBarBackground(snack,Colors.White)
						CustomFunctions.SetSnackBarTextColor(snack,Colors.Red, "RETRY")
						snack.Show
						Return
					End If
					UpdateServerIP(NewIP)
				Case 2
					If strPassword <>GlobalVar.AdminPassword Then
						vibration.vibrateOnce(2000)
						snack.Initialize("RetryButton", Activity, "Password is Incorrect!", snack.DURATION_LONG)
						snack.SetAction("RETRY")
						CustomFunctions.SetSnackBarBackground(snack,Colors.White)
						CustomFunctions.SetSnackBarTextColor(snack,Colors.Red, "RETRY")
						snack.Show
						Return
					End If
					GlobalVar.BranchID = SelectedBranchID
					UpdateBranchID(GlobalVar.BranchID)
					GlobalVar.CompanyID = DBaseFunctions.GetCompanyID(GlobalVar.BranchID)
					GetBGImage(GlobalVar.CompanyID)
				Case Else
					Return
			End Select
		Case Dialog.ACTION_NEGATIVE
			Select intSettingsFlag
				Case 1
					snack.Initialize("RetryButton", Activity, "Setting Server's IP Address Cancelled!", snack.DURATION_LONG)
					CustomFunctions.SetSnackBarBackground(snack,Colors.Red)
					CustomFunctions.SetSnackBarTextColor(snack,Colors.White, "")
					snack.Show
					Return
				Case 2
					snack.Initialize("RetryButton", Activity, "Setting Branch was Cancelled!", snack.DURATION_LONG)
					CustomFunctions.SetSnackBarBackground(snack,Colors.Red)
					CustomFunctions.SetSnackBarTextColor(snack,Colors.White, "")
					snack.Show
					Return
				Case Else
					Return
			End Select
	End Select
End Sub

Private Sub RetryButton_Click()
'	ShowAdminPassword
	ShowNewAdminPassword
End Sub

Private Sub UserNameBlank_Click()
	txtUserName.RequestFocusAndShowKeyboard
	txtPassword.Text=""
End Sub

Private Sub UserPasswordBlank_Click()
	txtPassword.RequestFocusAndShowKeyboard
End Sub
Private Sub SetServer_Click()
	ShowIPSettings
End Sub

Sub EncryptText(text As String, password As String) As Byte()
	Dim c As B4XCipher
	Return c.Encrypt(text.GetBytes("utf8"), password)
End Sub

Sub DecryptText(EncryptedData() As Byte, password As String) As String
	Dim c As B4XCipher
	Dim b() As Byte = c.Decrypt(EncryptedData, password)
	Return BytesToString(b, 0, b.Length, "utf8")
End Sub

Private Sub DownloadReaders(iBranchID As Int)
	Dim sRetVal As String
	Dim MeterReaderList As List
	Dim jSonPar As JSONParser
	Dim mpField As Map

	Try
		URLName = GlobalVar.ServerAddress & GlobalVar.ControllerPrefix & "getReaders"
		Job1.Initialize("", Me)
		Job1.Download2(URLName, Array As String("BranchID", iBranchID))

		sURL = URLName & "?BranchID=" & GlobalVar.BranchID
		LogColor(sURL, Colors.Red)
	
		Wait For (Job1) JobDone(j As HttpJob)
		If j.Success Then
			sRetVal = j.GetString
			jSonPar.Initialize(sRetVal)
			Log(sRetVal)
			ProgressDialogHide
			
			If sRetVal = "[]" Then
				j.Release
				xui.MsgboxAsync($"Either User Name or Password is Incorrect!"$ & CRLF & $"Please try again."$, Application.LabelName)
'				txtUserName.RequestFocus
'				txtUserName.SelectAll
				Return
			End If
			
			Starter.DBCon.BeginTransaction
			Try
				MeterReaderList.Initialize
				MeterReaderList = jSonPar.NextArray
				For i = 0 To MeterReaderList.Size - 1
					mpField.Initialize
					mpField = MeterReaderList.Get(i)
					Starter.strCriteria = "INSERT INTO tblReaders VALUES(?, ?, ?)"
					Starter.DBCon.ExecNonQuery2(Starter.strCriteria, Array As String(mpField.Get("ReaderID"), TitleCase(mpField.Get("ReaderName")), $"0"$))
				Next
				Starter.DBCon.TransactionSuccessful
				j.Release
			Catch
				Log(LastException)
				ToastMessageShow($"Unable to connect to Database Server due to "$ & LastException.Message, False)
			End Try
			Starter.DBCon.EndTransaction
			CustomFunctions.SaveUserSettings
			Activity.Finish
			StartActivity(MainScreen)
		Else
			ProgressDialogHide
			ToastMessageShow($"Unable to Fetch Meter Readers due to "$ & LastException.Message, True)
			j.Release
		End If
	Catch
		ToastMessageShow($"Unable to check Download Meter Readers to "$ & LastException.Message, False)
		Log(LastException)
		j.Release
	End Try
End Sub

Private Sub UpdateServerIP(sNewIP As String)
	Starter.DBCon.BeginTransaction
	'http://10.0.11.13/elite-system/public
	Try
		Starter.DBCon.ExecNonQuery("UPDATE android_metadata SET server_ip = '" & sNewIP & "'")
		Starter.DBCon.TransactionSuccessful
		snack.Initialize("",Activity,"Server's IP was succesfully Updated.", snack.DURATION_LONG)
		CustomFunctions.SetSnackBarBackground(snack, Colors.White)
		CustomFunctions.SetSnackBarTextColor(snack,GlobalVar.PriColor, "")
		snack.Show
		GlobalVar.ServerAddress = DBaseFunctions.GetServerIP
		GlobalVar.BranchID = DBaseFunctions.GetBranchID
	Catch
		Log(LastException)
	End Try
	Starter.DBCon.EndTransaction
End Sub

Private Sub DispErrorMsg(sMsg As String, sTitle As String)
	Dim csTitle, csMsg As CSBuilder
	csTitle.Initialize.Color(Colors.Red).Append(sTitle).PopAll
	csMsg.Initialize.Bold.Color(Colors.DarkGray).Append(CRLF & sMsg).PopAll
	
	Msgbox(csMsg, csTitle)
End Sub

Sub cboBranches_ItemClick (Position As Int, Value As Object)
	
End Sub

Sub BranchSettingsDialog_CustomViewReady (Dialog As MaterialDialog, CustomView As Panel)
	'CustomView.SetLayoutAnimated(0,0,0, 100%x, 100%y)
	CustomView.LoadLayout("BranchSettings")
	FillBranches
End Sub

Sub BranchSettingsDialog_ButtonPressed (Dialog As MaterialDialog, Action As String)
	Select Action
		Case Dialog.ACTION_POSITIVE
			If cboBranches.SelectedItem = "" Then Return
			strSelectedBranch = cboBranches.SelectedItem
			ShowAdminPassword
		Case Dialog.ACTION_NEGATIVE
	End Select
End Sub

Sub btnBranch_Click
'	Dim csContent As CSBuilder
'	
'	MatDialogBuilder.Initialize("BranchSettingsDialog")
'	MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIGHT)
'	MatDialogBuilder.Title($"BRANCH SETTINGS"$).TitleColor(GlobalVar.PriColor).TitleGravity(MatDialogBuilder.GRAVITY_START)
'	MatDialogBuilder.IconRes(GlobalVar.SettingsIcon).LimitIconToDefaultSize
'	MatDialogBuilder.PositiveText($"SAVE SETTINGS"$).PositiveColor(GlobalVar.PriColor)
'	MatDialogBuilder.NegativeText($"CANCEL"$).NegativeColor(Colors.Red)
'	MatDialogBuilder.CustomView(False, 50dip)
'	MatDialogBuilder.CanceledOnTouchOutside(False)
'	MatDialogBuilder.Show
	If pnlSearchBranches.Visible = True Then Return
	intSettingsFlag = 2
	DisableControls
	
	SV.Initialize(Me,"SV")
	SV.AddToParent(pnlSearchBranch,10,30,pnlSearchBranch.Width,pnlSearchBranch.Height)
	SV.ClearAll
	
	lblSelectedBranch.Text = $"Current Branch:"$ & CRLF & DBaseFunctions.GetBranchName(GlobalVar.BranchID)
	pnlSearchBranches.Visible=True
	SearchBranches
End Sub

Private Sub FillBranches
	Try
		cboBranches.Clear
		Starter.strCriteria = "SELECT * FROM tblBranches ORDER BY CompanyID ASC, BranchName"
		rsBranches = Starter.DBCon.ExecQuery(Starter.strCriteria)
		If rsBranches.RowCount > 0 Then
			For i = 0 To rsBranches.RowCount - 1
				rsBranches.Position = i
				cboBranches.Add(rsBranches.GetString("BranchName"))
			Next
		End If
	Catch
		Log(LastException)
	End Try
End Sub

Private Sub UpdateBranchID(iBranchID As String)
	Starter.DBCon.BeginTransaction
	'http://10.0.11.13/elite-system/public
	Try
		Starter.DBCon.ExecNonQuery("UPDATE tblSysParam SET BranchID = " & iBranchID)
		Starter.DBCon.TransactionSuccessful
		snack.Initialize("",Activity,"Branch Settings has been successfully saved.", snack.DURATION_LONG)
		CustomFunctions.SetSnackBarBackground(snack, Colors.White)
		CustomFunctions.SetSnackBarTextColor(snack,GlobalVar.PriColor, "")
		snack.Show
	Catch
		Log(LastException)
	End Try
	Starter.DBCon.EndTransaction
End Sub

Private Sub GetBGImage(iCompanyID As Int)
	Select Case iCompanyID
		Case 0
			Activity.SetBackgroundImage(LoadBitmap(File.DirAssets,"bwsi-login.png"))
		Case 1'BWSI
			Activity.SetBackgroundImage(LoadBitmap(File.DirAssets,"bwsi-login.png"))
		Case 2'CLPI
			Activity.SetBackgroundImage(LoadBitmap(File.DirAssets, "clpi-login.png"))
		Case 3'HWRI
			Activity.SetBackgroundImage(LoadBitmap(File.DirAssets, "hwri-login.png"))
		Case 4'BCBI
			Activity.SetBackgroundImage(LoadBitmap(File.DirAssets, "bcbi-login.png"))
		Case 5'BIRI
			Activity.SetBackgroundImage(LoadBitmap(File.DirAssets, "biri-login.png"))
		Case 6'LBWI
			Activity.SetBackgroundImage(LoadBitmap(File.DirAssets, "lbwi-login.png"))
		Case 7'SBRI
			Activity.SetBackgroundImage(LoadBitmap(File.DirAssets, "sbri-login.png"))
		Case 8'MR3H
			Activity.SetBackgroundImage(LoadBitmap(File.DirAssets, "mr3h-login.png"))
		Case 9'SD3H
			Activity.SetBackgroundImage(LoadBitmap(File.DirAssets, "sd3h-login.png"))
		Case 10'CENTRAL LIQUID
			Activity.SetBackgroundImage(LoadBitmap(File.DirAssets, "clpi2-login.png"))
		Case Else'BWSI
			Activity.SetBackgroundImage(LoadBitmap(File.DirAssets,"login.png"))
	End Select

End Sub

Sub DisableControls
'	txtUserName.Enabled = False
'	txtPassword.Enabled = False
	btnLogin.Enabled = False
	chkShowPass.Enabled = False
	btnSettings.Enabled = False
	btnBranch.Enabled = False
	btnCancel.Enabled = True
End Sub

Sub EnableControls
'	txtUserName.Enabled = True
'	txtPassword.Enabled = True
	btnLogin.Enabled = True
	chkShowPass.Enabled = True
	btnSettings.Enabled = True
	btnBranch.Enabled = True
End Sub

Private Sub SearchBranches
	Dim rsBranches As Cursor
	Dim SearchList As List
	If SV.IsInitialized=False Then
		SV.Initialize(Me,"SV")
	End If
	
	SV.ClearAll
	SV.ClearSearchBox
	SV.lv.Clear

	SearchList.Initialize
	SearchList.Clear

	Try
		Starter.strCriteria="SELECT tblBranches.BranchID, tblBranches.CompanyID, tblBranches.BranchName, tblCompanies.CompanyName " & _
							"FROM tblBranches INNER JOIN tblCompanies ON tblCompanies.CompanyID = tblBranches.CompanyID " & _
							"ORDER BY tblBranches.CompanyID ASC, tblBranches.BranchName ASC"
		rsBranches = Starter.DBCon.ExecQuery(Starter.strCriteria)

		If rsBranches.RowCount > 0 Then
			For i = 0 To rsBranches.RowCount - 1
				rsBranches.Position = i
				Dim it As Item
				it.Title = rsBranches.GetString("BranchName")
				it.Text = TitleCase(rsBranches.GetString("CompanyName"))
				it.SearchText = rsBranches.GetString("BranchName").ToLowerCase
				it.Value = rsBranches.GetString("BranchID")
				SearchList.Add(it)
			Next
		Else
			Return
		End If
		SV.SetItems(SearchList)
		SearchList.Clear
	Catch
		Log(LastException)
	End Try
End Sub

Sub SV_ItemClick(Value As String)
	Log(Value)
	SelectedBranchID = Value
	SV.ClearAll
	SearchClosed
	SelectedBranchName = DBaseFunctions.GetBranchName(Value)
'	ConfirmSelectedBranch(SelectedBranchName)
	ConfirmBranchSelection($"Set "$ & SelectedBranchName & $" as your current branch?"$)
End Sub

Sub SearchClosed
	SearchFor = ""
	SV.ClearSearchBox
	SV.ClearAll
'	SV.RemoveView
	pnlSearchBranches.Visible = False
	EnableControls
End Sub



Sub btnCancel_Click
	SearchClosed
	EnableControls
	snack.Initialize("", Activity, "Setting of Branch cancelled!", snack.DURATION_LONG)
	CustomFunctions.SetSnackBarBackground(snack,Colors.Red)
	CustomFunctions.SetSnackBarTextColor(snack,Colors.White, "")
	snack.Show
End Sub

Public Sub CreateButtonColor(FocusedColor As Int, EnabledColor As Int, DisabledColor As Int, PressedColor As Int) As StateListDrawable

	Dim RetColor As StateListDrawable
	RetColor.Initialize
	Dim drwFocusedColor, drwEnabledColor, drwDisabledColor, drwPressedColor As ColorDrawable

	'drwFocusedColor.Initialize2(FocusedColor, 5, 0, Colors.LightGray) 'border roundness, thickness, and color on Android TV
	'drwEnabledColor.Initialize2(EnabledColor, 5, 0, Colors.DarkGray)
	'drwDisabledColor.Initialize2(DisabledColor, 5, 0, Colors.White)
	'drwPressedColor.Initialize2(PressedColor, 5, 0, Colors.Black)
'	CD.Initialize(0xFF1976D2, 25)
	
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

Private Sub ConfirmSelectedBranch(sMsg As String)
	Dim csTitle As CSBuilder
	
	csTitle.Initialize.Color(GlobalVar.PriColor).Bold.Size(16).Append($"Confirm Selection"$).PopAll
	MatDialogBuilder.Initialize("SelectedBranch")
	MatDialogBuilder.Title(csTitle)
	MatDialogBuilder.Content($"Set "$ & sMsg & $" as your branch?"$).ContentColor(GlobalVar.PriColor)
	MatDialogBuilder.IconRes(GlobalVar.QuestionIcon).LimitIconToDefaultSize
	MatDialogBuilder.PositiveText($"Save"$).PositiveColor(GlobalVar.PriColor)
	MatDialogBuilder.NegativeText($"Cancel"$).NegativeColor(Colors.Red)
	MatDialogBuilder.Cancelable(False)
	MatDialogBuilder.CanceledOnTouchOutside(False)
	MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIGHT)
	MatDialogBuilder.Build
	MatDialogBuilder.Show
End Sub

Sub SelectedBranch_ButtonPressed (mDialog As MaterialDialog, sAction As String)
	Select Case sAction
		Case mDialog.ACTION_POSITIVE
			ShowAdminPassword
		Case mDialog.ACTION_NEGATIVE
			snack.Initialize("", Activity,$"Setting Branch cancelled."$,snack.DURATION_SHORT)
			CustomFunctions.SetSnackBarBackground(snack, Colors.Red)
			CustomFunctions.SetSnackBarTextColor(snack, Colors.White, "")
			snack.Show
	End Select
End Sub

Sub GetGatewayIp As String
	Dim ctxt As JavaObject
	Dim sRetIP As String
	ctxt.InitializeContext
	Dim WifiManager As JavaObject = ctxt.RunMethod("getSystemService", Array(ctxt.GetField("WIFI_SERVICE")))
	Dim dhcp As JavaObject = WifiManager.RunMethod("getDhcpInfo", Null)
	Dim formatter As JavaObject
	sRetIP =  formatter.InitializeStatic("android.text.format.Formatter").RunMethod("formatIpAddress", Array(dhcp.GetField("gateway")))
	LogColor($"Returned IP:"$ & sRetIP, Colors.Yellow)
	Return sRetIP
End Sub

Private Sub OpenEliteSystem
	Dim p As PhoneIntents
	StartActivity(p.OpenBrowser(GlobalVar.ServerAddress))
End Sub

Sub pnlSearchBranches_Touch (Action As Int, X As Float, Y As Float)
	
End Sub

#Region New Msgbox
Private Sub UserWarningMsg(sTitle As String, sMsg As String)
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

Private Sub myCD As ColorDrawable
	Dim mCD As ColorDrawable
	mCD.Initialize(Colors.RGB(240,240,240),0)
	Return mCD
End Sub

Private Sub WarningMessage_OnPositiveClicked (View As View, Dialog As Object)
	Dim AlertDialog As AX_CustomAlertDialog
	
	vibration.vibrateCancel
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


Private Sub ShowWiFiWarning(sTitle As String, sMsg As String)
	Dim Alert As AX_CustomAlertDialog

	Alert.Initialize.Create _
			.SetDialogStyleName("MyDialogDisableStatus") _	'Manifest style name
			.SetStyle(Alert.STYLE_DIALOGUE) _
			.SetCancelable(False) _
			.SetTitle(sTitle) _
			.SetMessage(sMsg) _
			.SetPositiveText("Turn On WiFi") _
			.SetPositiveColor(Colors.Blue) _
			.SetPositiveTypeface(GlobalVar.FontBold) _
			.SetNegativeText("Cancel") _
			.SetNegativeColor(Colors.Red) _
			.SetNegativeTypeface(GlobalVar.Font) _
			.SetTitleTypeface(GlobalVar.Font) _
			.SetMessageTypeface(GlobalVar.Font) _
			.SetOnPositiveClicked("AllowWiFi") _	'listeners
			.SetOnNegativeClicked("AllowWiFi") _
			.SetOnViewBinder("WiFiFontSizeBinder") 'listeners
			
	Alert.SetDialogBackground(myCD)
	Alert.Build.Show

End Sub
'Listeners
Private Sub AllowWiFi_OnNegativeClicked (View As View, Dialog As Object)
	Dim Alert As AX_CustomAlertDialog
	Alert.Initialize.Dismiss(Dialog)
	blnWifiOn = False

	vibration.vibrateOnce(2000)
	snack.Initialize("", Activity,$"Unable to log due to NO INTERNET CONNECTION."$,snack.DURATION_LONG)
	CustomFunctions.SetSnackBarBackground(snack,Colors.Red)
	CustomFunctions.SetSnackBarTextColor(snack,Colors.White, "")
	snack.Show
	ProgressDialogHide

End Sub

Private Sub AllowWiFi_OnPositiveClicked (View As View, Dialog As Object)
	Dim Alert As AX_CustomAlertDialog
	Alert.Initialize.Dismiss(Dialog)
	Sleep(1000)
	blnWifiOn = True
	If blnWifiOn = True Then
		tglWifi.toggleWIFI
		ProgressDialogShow2($"Checking Database Server Connection."$ & CRLF & $"Please Wait..."$, False)
		GetUserAccount(GlobalVar.BranchID, txtUserName.Text, txtPassword.Text)
	End If
End Sub

Private Sub WiFiFontSizeBinder_OnBindView (View As View, ViewType As Int)
	Dim Alert As AX_CustomAlertDialog
	Alert.Initialize
	If ViewType = Alert.VIEW_TITLE Then ' Title
		Dim lbl As Label = View
'		lbl.TextSize = 30
'		lbl.SetTextColorAnimated(2000,Colors.Magenta)
		
		
		Dim CS As CSBuilder
'		CS.Initialize.Typeface(Font).Append(lbl.Text & " ").Pop
'		CS.Typeface(Typeface.MATERIALICONS).Size(36).Color(Colors.Red).Append(Chr(0xE190))

		CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Typeface(Typeface.MATERIALICONS).Size(26).Color(Colors.Red).Append(Chr(0xE1B3) & "  ")
		CS.Typeface(GlobalVar.Font).Size(22).Append(lbl.Text).Pop

		lbl.Text = CS.PopAll
	End If
	If ViewType = Alert.VIEW_MESSAGE Then
		Dim lbl As Label = View
		lbl.TextSize = 16
		lbl.TextColor = Colors.Gray
	End If
End Sub

Private Sub ConfirmExitApp(sTitle As String, sMsg As String)
	vibration.vibrateOnce(1000)

	Alert.Initialize.Create _
			.SetDialogStyleName("MyDialogDisableStatus") _	'Manifest style name
			.SetStyle(Alert.STYLE_DIALOGUE) _
			.SetCancelable(False) _
			.SetTitle(sTitle) _
			.SetTitleTypeface(GlobalVar.Font) _
			.SetMessage(sMsg) _
			.SetPositiveText("Confirm") _
			.SetPositiveColor(Colors.Blue) _
			.SetPositiveTypeface(GlobalVar.FontBold) _
			.SetNegativeText("Cancel") _
			.SetNegativeColor(Colors.Red) _
			.SetNegativeTypeface(GlobalVar.Font) _
			.SetMessageTypeface(GlobalVar.Font) _
			.SetOnPositiveClicked("ConfirmQuit") _	'listeners
			.SetOnNegativeClicked("ConfirmQuit") _
			.SetOnViewBinder("QuitFontSizeBinder") 'listeners
			
	Alert.SetDialogBackground(myCD)
	Alert.Build.Show

End Sub

'Listeners
Private Sub ConfirmQuit_OnNegativeClicked (View As View, Dialog As Object)
	vibration.vibrateCancel
	Alert.Initialize.Dismiss(Dialog)
End Sub

Private Sub ConfirmQuit_OnPositiveClicked (View As View, Dialog As Object)
'	Dim Alert As AX_CustomAlertDialog
	vibration.vibrateCancel
	blnUserClosed = True
''	Activity.Finish
'	ExitApplication
'	Alert.Initialize.Dismiss2
	CustomFunctions.ClearUserData
	ExitApplication
End Sub

Private Sub QuitFontSizeBinder_OnBindView (View As View, ViewType As Int)
	Dim Alert As AX_CustomAlertDialog
	Alert.Initialize
	If ViewType = Alert.VIEW_TITLE Then ' Title
		Dim lbl As Label = View
		
		Dim CS As CSBuilder
		CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Typeface(Typeface.MATERIALICONS).Size(22).Color(Colors.Red).Append(Chr(0xE879) & " ")
		CS.Typeface(GlobalVar.Font).Size(22).Append(lbl.Text).Pop

		lbl.Text = CS.PopAll
	End If
	If ViewType = Alert.VIEW_MESSAGE Then
		Dim lbl As Label = View
		lbl.TextSize = 17
		lbl.TextColor = Colors.Gray
	End If
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

Private Sub ShowNewIPSettings
	Dim Alert As AX_CustomAlertDialog
	
	Dim LineHint As List : LineHint.Initialize
	LineHint.Add("SERVER IP")
	Dim LineText As List : LineText.Initialize
	LineText.Add(GlobalVar.ServerAddress)
	
	Alert.Initialize.Create _
			.SetDialogStyleName("MyDialogDisableStatus") _	'Manifest style name
			.SetStyle(Alert.STYLE_INPUT) _
			.SetTitle("SERVER IP ADDRESS") _
			.SetMessage("Please enter your Elite IP Address here, as shown on your web browser URL.") _
			.SetLineInputHint(LineHint) _
			.SetLineInputText(LineText) _
			.SetPositiveText("Save Settings") _
			.SetPositiveTypeface(GlobalVar.FontBold) _
			.SetPositiveColor(GlobalVar.PriColor) _
			.SetNegativeText("Cancel") _
			.SetNegativeTypeface(GlobalVar.Font) _
			.SetNegativeColor(Colors.Red) _
			.SetTitleTypeface(GlobalVar.Font) _
			.SetMessageTypeface(GlobalVar.Font) _
			.SetOnPositiveClicked("IPSettings") _	'listeners
			.SetOnNegativeClicked("IPSettings") _	'listeners
			.SetOnInputClicked("IPSettings") _	'listeners
			.SetOnViewBinder("IPSettings") _
			.SetCancelable(False) _
			.SetDialogBackground(myCD) 'listeners
			
	Alert.Build.Show
End Sub
Private Sub IPSettings_OnNegativeClicked (View As View, Dialog As Object)
	vibration.vibrateCancel
	Alert.Initialize.Dismiss(Dialog)
	snack.Initialize("", Activity, "Setting of Server IP cancelled!", snack.DURATION_LONG)
	CustomFunctions.SetSnackBarBackground(snack,Colors.Red)
	CustomFunctions.SetSnackBarTextColor(snack,Colors.White, "")
	snack.Show
End Sub

Private Sub IPSettings_OnInputClicked (View As View, Dialog As Object, InputSList As List)
'	Dim Inputs As String = "Inputs : "&CRLF
'	For i = 0 To InputSList.Size - 1
'		Inputs = Inputs & InputSList.Get(i) & CRLF
'	Next
	NewIP = InputSList.Get(0)
	
	ToastMessageShow(NewIP.Trim,False)
	Dim Alert As AX_CustomAlertDialog
	Alert.Initialize.Dismiss(Dialog)
	Sleep(100)
	ShowNewAdminPassword
End Sub


Private Sub IPSettings_OnBindView (View As View, ViewType As Int)
	Dim Alert As AX_CustomAlertDialog
	Alert.Initialize
	If ViewType = Alert.VIEW_TITLE Then ' Title
		Dim lbl As Label = View
		
		Dim CS As CSBuilder
		CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Typeface(Typeface.MATERIALICONS).Size(30).Color(Colors.Red).Append(Chr(0xE1E2) & " ")
		CS.Typeface(GlobalVar.Font).Size(20).Append(lbl.Text).Pop

		lbl.Text = CS.PopAll
	End If
	
	If ViewType = Alert.VIEW_MESSAGE Then
		Dim lbl As Label = View
		lbl.TextSize = 14
		lbl.TextColor = Colors.Gray
	Else If ViewType = Alert.VIEW_LINE_INPUT Then
		Dim txt As EditText = View
		txt.TextSize = 15
		txt.TextColor = Colors.Black
		txt.Typeface = GlobalVar.Font
	End If
End Sub

Private Sub ShowNewAdminPassword
	Dim Alert As AX_CustomAlertDialog
	
	Dim LineHint As List : LineHint.Initialize
	LineHint.Add("ADMINISTRATIVE PASSWORD")
	
	Dim LineText As List : LineText.Initialize
	LineText.Add("")
	
	Alert.Initialize.Create _
			.SetDialogStyleName("MyDialogDisableStatus") _	'Manifest style name
			.SetStyle(Alert.STYLE_INPUT) _
			.SetTitle("ADMIN PASSWORD") _
			.SetMessage("Please enter the Administrative Password to Continue...") _
			.SetLineInputHint(LineHint) _
			.SetLineInputText(LineText) _
			.SetPositiveText("OK") _
			.SetPositiveTypeface(GlobalVar.FontBold) _
			.SetPositiveColor(GlobalVar.PriColor) _
			.SetNegativeText("CANCEL") _
			.SetNegativeTypeface(GlobalVar.Font) _
			.SetNegativeColor(Colors.Red) _
			.SetTitleTypeface(GlobalVar.Font) _
			.SetMessageTypeface(GlobalVar.Font) _
			.SetOnPositiveClicked("AdminPassword") _	'listeners
			.SetOnNegativeClicked("AdminPassword") _	'listeners
			.SetOnInputClicked("AdminPassword") _	'listeners
			.SetOnViewBinder("AdminPassword") _
			.SetCancelable(False) _
			.SetDialogBackground(myCD) 'listeners
			
	Alert.Build.Show
End Sub

Private Sub AdminPassword_OnNegativeClicked (View As View, Dialog As Object)
	vibration.vibrateCancel
	Alert.Initialize.Dismiss(Dialog)
	snack.Initialize("", Activity, "Authentication cancelled!", snack.DURATION_LONG)
	CustomFunctions.SetSnackBarBackground(snack,Colors.Red)
	CustomFunctions.SetSnackBarTextColor(snack,Colors.White, "")
	snack.Show
End Sub

Private Sub AdminPassword_OnInputClicked (View As View, Dialog As Object, InputSList As List)
	Dim Alert As AX_CustomAlertDialog

	Alert.Initialize.Dismiss(Dialog)
	
	If InputSList.Get(0) <> GlobalVar.AdminPassword Then
		imeKeyBoard.HideKeyboard
		vibration.vibrateOnce(2000)
		snack.Initialize("RetryButton", Activity, "Password is Incorrect!", snack.DURATION_LONG)
		snack.SetAction("RETRY")
		CustomFunctions.SetSnackBarBackground(snack,Colors.White)
		CustomFunctions.SetSnackBarTextColor(snack,Colors.Red, "RETRY")
		snack.Show
		Return
	End If
	
	Select intSettingsFlag
		Case 1
			UpdateServerIP(NewIP)
			
		Case 2
			GlobalVar.BranchID = SelectedBranchID
			UpdateBranchID(GlobalVar.BranchID)
			GlobalVar.CompanyID = DBaseFunctions.GetCompanyID(GlobalVar.BranchID)
			GetBGImage(GlobalVar.CompanyID)
		Case Else
			Return
	End Select

End Sub

Private Sub AdminPassword_OnBindView (View As View, ViewType As Int)
	Dim Alert As AX_CustomAlertDialog
	Alert.Initialize
	If ViewType = Alert.VIEW_TITLE Then ' Title
		Dim lbl As Label = View
		
		Dim CS As CSBuilder
		CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Typeface(Typeface.MATERIALICONS).Size(30).Color(Colors.Red).Append(Chr(0xE32A) & " ")
		CS.Typeface(GlobalVar.Font).Size(18).Append(lbl.Text).Pop

		lbl.Text = CS.PopAll
	else If ViewType = Alert.VIEW_MESSAGE Then
		Dim lbl As Label = View
		lbl.TextSize = 14
		lbl.TextColor = Colors.Gray
	Else If ViewType = Alert.VIEW_LINE_INPUT Then
		Dim txt As EditText = View
		InpTyp.Initialize
		InpTyp.SetInputType(txt,Array As Int(InpTyp.TYPE_CLASS_TEXT, InpTyp.TYPE_TEXT_FLAG_AUTO_CORRECT, InpTyp.TYPE_TEXT_VARIATION_PASSWORD))
		txt.TextSize = 17
		txt.TextColor = Colors.Black
'		txt.Typeface = GlobalVar.Font
		txt.ForceDoneButton = False
		txt.SingleLine = True
		txt.PasswordMode = True
	End If
End Sub

Private Sub ConfirmBranchSelection(sMsg As String)
	Alert.Initialize.Create _
			.SetDialogStyleName("MyDialogDisableStatus") _	'Manifest style name
			.SetStyle(Alert.STYLE_DIALOGUE) _
			.SetCancelable(False) _
			.SetTitle($"Confirm Selection"$) _
			.SetTitleTypeface(GlobalVar.Font) _
			.SetMessage(sMsg) _
			.SetPositiveText("Confirm") _
			.SetPositiveColor(Colors.Blue) _
			.SetPositiveTypeface(GlobalVar.FontBold) _
			.SetNegativeText("Cancel") _
			.SetNegativeColor(Colors.Red) _
			.SetNegativeTypeface(GlobalVar.Font) _
			.SetMessageTypeface(GlobalVar.Font) _
			.SetOnPositiveClicked("BranchSelection") _	'listeners
			.SetOnNegativeClicked("BranchSelection") _
			.SetOnViewBinder("BranchSelection") 'listeners
			
	Alert.SetDialogBackground(myCD)
	Alert.Build.Show
End Sub

'Listeners
Private Sub BranchSelection_OnNegativeClicked (View As View, Dialog As Object)
	vibration.vibrateCancel
	Alert.Initialize.Dismiss(Dialog)
	snack.Initialize("", Activity,$"Setting of Branch cancelled."$,snack.DURATION_SHORT)
	CustomFunctions.SetSnackBarBackground(snack, Colors.Red)
	CustomFunctions.SetSnackBarTextColor(snack, Colors.White, "")
	snack.Show
End Sub

Private Sub BranchSelection_OnPositiveClicked (View As View, Dialog As Object)
	vibration.vibrateCancel
	Dim Alert As AX_CustomAlertDialog
	Alert.Initialize.Dismiss(Dialog)
	Sleep(100)
	ShowNewAdminPassword
End Sub

Private Sub BranchSelection_OnBindView (View As View, ViewType As Int)
	Dim Alert As AX_CustomAlertDialog
	Alert.Initialize
	If ViewType = Alert.VIEW_TITLE Then ' Title
		Dim lbl As Label = View
		
		Dim CS As CSBuilder
		CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Typeface(Typeface.FONTAWESOME).Size(22).Color(Colors.Red).Append(Chr(0xF29C) & " ")
		CS.Typeface(GlobalVar.Font).Size(22).Append(lbl.Text).Pop

		lbl.Text = CS.PopAll
	End If
	If ViewType = Alert.VIEW_MESSAGE Then
		Dim lbl As Label = View
		lbl.TextSize = 17
		lbl.TextColor = Colors.Gray
	End If
End Sub

#End Region
