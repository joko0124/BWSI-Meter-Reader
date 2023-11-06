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

Sub Process_Globals
	Private strReadingData As String
	Private xui As XUI
	Dim AP As ManageExternalStorage
	Dim device As Phone
	Private ion As Object
	Dim soundsAlarmChannel As SoundPool

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Dim ActionBarButton As ACActionBar
	Private ToolBar As ACToolBarDark
	Private xmlIcon As XmlLayoutBuilder
	
	'Material Dialog
	Private MatDialogBuilder As MaterialDialogBuilder
'	Private DTRdgDate As AnotherDatePicker

	Private pnlButtons As Panel
	Private pnlDownload As Panel
	
	Private strRdgDate As String
	Dim bmpDownload As BitmapDrawable
	Dim bmpUpload As BitmapDrawable
	
	Private rsReader As Cursor
	Private rsHeader As Cursor
	Private rsDetails As Cursor
	Private ReaderID As Int
	Private intUploadedBookID As Int
	Private intID As Int
	Private intUploadAcctID As Int

	Private cboReader As ACSpinner
	Private intDownload = 0 As Int
	
	Private snack As DSSnackbar
	Dim DLctr =0 As Int
	Private btnCancelUpload As ACButton
	Private btnOkUpload As ACButton
	Private cboReaderUpload As ACSpinner
	Private pnlUpload As Panel
	Private UploadCtr As Int

	Dim intReaderID As Int
	Private btnCancel As ACButton
	Private btnOK As ACButton
	
	Private sURL As String
	
	Private BackUpPath As String
	Private BackUpName As String
	
	Dim myStyle As ColorDrawable
	Private cdButtonCancel, cdButtonOK As ColorDrawable

	Private pnlDownloadBox As Panel
	Private pnlUploadBox As Panel
	Private btnDownload As ACButton
	Private btnUpload As ACButton
	Private cdDownUpStyle As ColorDrawable
	Private cdReader As ColorDrawable
	
	Private MyDateDialogs As DateDialogs

	Private lblRdgDateIcon As Label
	Private lblReadingDate As Label
	Private lDate As Long
	Private sRdgDate As String
	
	Private csNote As CSBuilder
	Private lblULNote As Label
	Private SoundID As Int
	Private vibration As B4Avibrate
'	Private vibratePattern() As Long

End Sub

Sub Activity_Create(FirstTime As Boolean)
	MyScale.SetRate(0.5)
	Activity.LoadLayout("DataSyncingNew")
	
	GlobalVar.CSTitle.Initialize.Size(13).Append($"DATA SYNCING"$).Bold.PopAll
	GlobalVar.CSSubTitle.Initialize.Size(11).Append($"Allows to Download and Upload Reading Data."$).PopAll
	
	ToolBar.InitMenuListener
	ToolBar.Title = GlobalVar.CSTitle
	ToolBar.SubTitle = GlobalVar.CSSubTitle

	ActionBarButton.Initialize
	ActionBarButton.ShowUpIndicator = True

	pnlButtons.Visible = True
	pnlDownloadBox.Visible = False
	pnlUploadBox.Visible = False
	
'	cdButtonCancel.Initialize2(Colors.White,0,0,Colors.Transparent
	cdDownUpStyle.Initialize2(GlobalVar.PriColor, 25,0,Colors.Transparent)
	btnDownload.Background = cdDownUpStyle
	btnDownload.Text = Chr(0xF019) & $"  DOWNLOAD READING DATA"$
	btnDownload.Typeface = Typeface.DEFAULT_BOLD
	btnDownload.Typeface = Typeface.FONTAWESOME

	btnUpload.Background = cdDownUpStyle
	btnUpload.Text = Chr(0xF093) & $"  UPLOAD READING DATA"$
	btnUpload.Typeface = Typeface.DEFAULT_BOLD
	btnUpload.Typeface = Typeface.FONTAWESOME

	myStyle.Initialize2(Colors.White, 0,0,Colors.Transparent)
'	cboReader.Background = myStyle
	cboReader.TextColor = Colors.Gray
	
	If FirstTime Then
	End If
	
	Dim jo As JavaObject
	Dim ph As Phone

	jo.InitializeNewInstance("android.media.SoundPool", Array(4, ph.VOLUME_ALARM, 0)) '4 = max streams
	soundsAlarmChannel = jo
	SoundID = soundsAlarmChannel.Load(File.DirAssets,"beep.wav")
'	Dim sdkVersion As Int = Phone.SdkVersion
'	
'	If sdkVersion < 30 Then
'		Starter.rp.CheckAndRequest(Starter.rp.PERMISSION_WRITE_EXTERNAL_STORAGE)
'		Wait For Activity_PermissionResult (Permission As String, Result As Boolean)
'	Else
'		If Not(AP.HasPermission) Then
'			MsgboxAsync(" The app is about to request access to all files , Whether to allow ?", " File access ")
'			Wait For Msgbox_Result(Res As Int)
'			AP.GetPermission
'			Wait For MES_StorageAvailable
'		End If
'	End If
'	
'	Starter.rp.CheckAndRequest(Starter.rp.PERMISSION_READ_EXTERNAL_STORAGE)
'	Starter.rp.CheckAndRequest(Starter.rp.PERMISSION_WRITE_EXTERNAL_STORAGE)
'	Starter.rp.GetSafeDirDefaultExternal("")

'	If FirstTime Then
		AP.Initialize(Me, "AP")
'	End If
	
	' get the device SDK version
	Dim SdkVersion As Int = device.SdkVersion
	
	' Choose which permission to request in order to access external storgage
	If SdkVersion < 30 Then
		Log("SDK = " & SdkVersion & " : Requesting WRITE_EXTERNAL_STORAGE permission")
		Dim rp As RuntimePermissions
		rp.CheckAndRequest(rp.PERMISSION_WRITE_EXTERNAL_STORAGE) ' Implicit read capability if granted
		Wait For Activity_PermissionResult (Permission As String, Result As Boolean)
		Log($"PERMISSION_WRITE_EXTERNAL_STORAGE = ${Result}"$)
	Else
		Log("SDK = " & SdkVersion & " : Requesting MANAGE_EXTERNAL_STORAGE permission")
		Log("On Entry MANAGE_EXTERNAL_STORAGE = " & AP.HasPermission)
		If Not(AP.HasPermission) Then
			MsgboxAsync("This app requires access to all files, please enable the option", "Manage All Files")
			Wait For Msgbox_Result(Res As Int)
			Log("Getting permission")
			AP.GetPermission
			Wait For AP_StorageAvailable
		End If
	End If

End Sub

Sub Activity_KeyPress (KeyCode As Int) As Boolean 'Return True to consume the event
	Dim intResponse As Int
	If KeyCode = 4 Then
		Return False
	Else
		Return True
	End If
End Sub

Sub Activity_Resume
	pnlButtons.Visible = True
	pnlDownloadBox.Visible = False
	pnlUploadBox.Visible = False
	myStyle.Initialize2(Colors.White, 0,0,Colors.Transparent)
	SoundID = soundsAlarmChannel.Load(File.DirAssets,"beep.wav")
End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub Activity_PermissionResult (Permission As String, Result As Boolean)
	Log (Permission)
End Sub
Sub ToolBar_NavigationItemClick
	Activity.Finish
End Sub

Sub ToolBar_MenuItemClick (Item As ACMenuItem)
	
End Sub

#Region Download Panel
Public Sub ShowDownloadPanel
'	cboReader.Background = myStyle

	cdButtonCancel.Initialize(0xFFDC143C, 25)
	btnCancel.Background = cdButtonCancel

	cdButtonOK.Initialize(0xFF1976D2, 25)
	btnOK.Background = cdButtonOK

	pnlButtons.Visible = False
	pnlDownloadBox.Visible = True
	LoadReader
	SetReadingDate
End Sub

Private Sub LoadReader

	cboReader.Clear
	Try
		Starter.strCriteria = "SELECT * FROM tblReaders"
		rsReader = Starter.DBCon.ExecQuery(Starter.strCriteria)
		If rsReader.RowCount > 0 Then
			For i = 0 To rsReader.RowCount - 1
				rsReader.Position = i
				cboReader.Add(TitleCase(rsReader.GetString("ReaderName")))
			Next
		End If
	Catch
		ToastMessageShow("Unable to Load Meter Readers due to " & LastException.Message,False)
		Log(LastException)
	End Try
End Sub

Private Sub SetReadingDate
	DateTime.DateFormat = "MM/dd/yyyy"
	lDate = DateTime.DateParse(DateTime.Date(DateTime.Now))
	sRdgDate = DateTime.Date(lDate)
	lblReadingDate.Text = sRdgDate
End Sub
#End Region

#Region DownloadButton
Sub btnDownload_HoverEnter()
	btnDownload.Color = 0xFF1976D2
End Sub

Sub btnDownload_HoverExit()
	btnDownload.Color = GlobalVar.PriColor
End Sub

Sub btnDownload_Click()
	ShowDownloadPanel
End Sub
#End Region

#Region Download

Sub btnOk_Click
	Dim dDate As Long
	Try
'		dDate = lblReadingDate.Text
		dDate = DateTime.DateParse(lblReadingDate.Text)
		DateTime.DateFormat="yyyy-MM-dd"
		strRdgDate = DateTime.Date(dDate)
		Log(strRdgDate)

		If strRdgDate = "" Then
			xui.MsgboxAsync("Please specify the reading date!",Application.LabelName)
			Return
		End If
		
		If cboReader.SelectedItem = "" Then
			xui.MsgboxAsync("Please specify the reader!",Application.LabelName)
			Return
		End If

		ReaderID = DBaseFunctions.GetIDbyCode("ReaderID", "tblReaders", "ReaderName", cboReader.SelectedItem)
		
		Log(cboReader.SelectedItem)
		Log (ReaderID)
'		ConfirmDownload
		ConfirmDL($"DOWNLOAD READING DATA"$,$"REMINDERS: PASSWORD REQUIRED!"$ & CRLF & $"Continuing this process will DELETE ALL existing data on this device and will download new reading data."$ & CRLF & CRLF & $"Do you want to REMOVE all current data and DOWNLOAD new records?"$)
	Catch
		xui.MsgboxAsync(LastException.Message, Application.LabelName)
		Log(LastException)
	End Try
End Sub

Sub btnCancel_Click
	ConfirmCancelDownload
End Sub

Private Sub ConfirmDownload
	Dim csContent As CSBuilder
	csContent.Initialize.Size(14).Color(Colors.Red).Bold.Append($"REMINDERS: PASSWORD REQUIRED!"$).Color(Colors.DarkGray).Append(CRLF & $"Continuing this process will "$).Color(Colors.Red).Append($"DELETE ALL"$).Color(Colors.DarkGray).Append($" existing data on this device and will download new reading data."$ & CRLF & CRLF & $"Do you want to REMOVE all current data and DOWNLOAD new records?"$).PopAll
	
	MatDialogBuilder.Initialize("Downloading")
	MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIGHT)
	MatDialogBuilder.Title($"DOWNLOAD READING DATA"$).TitleColor(GlobalVar.PriColor).TitleGravity(MatDialogBuilder.GRAVITY_START)
	MatDialogBuilder.IconRes(GlobalVar.WarningIcon).LimitIconToDefaultSize
	MatDialogBuilder.Content(csContent)
	MatDialogBuilder.PositiveText($"YES"$).PositiveColor(GlobalVar.PriColor)
	MatDialogBuilder.NegativeText($"NO"$).NegativeColor(Colors.Red)
	MatDialogBuilder.CanceledOnTouchOutside(False)
	MatDialogBuilder.Show
End Sub

Private Sub Downloading_ButtonPressed(Dialog As MaterialDialog, sAction As String)
	Select Case sAction
		Case Dialog.ACTION_POSITIVE
			ShowDLPasswordDialog
		Case Dialog.ACTION_NEGATIVE
			Return
	End Select
End Sub

Private Sub ShowDLPasswordDialog
	MatDialogBuilder.Initialize("RetDLPassword")
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

Private Sub RetDLPassword_InputChanged (mDialog As MaterialDialog, sInput As String)
	Dim csBuild As CSBuilder
	If sInput.Length <= 0 Then
		csBuild.Initialize.Bold.Color(Colors.Red).Append($"Enter your Password to Continue..."$).PopAll
		mDialog.Content = csBuild
		mDialog.EnableActionButton(mDialog.ACTION_POSITIVE, False)
	Else
		If sInput <> GlobalVar.UserPW Then
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

Private Sub RetDLPassword_ButtonPressed (mDialog As MaterialDialog, sAction As String)
	Dim lDateTime As Long
	Select Case sAction
		Case mDialog.ACTION_POSITIVE
			If DBaseFunctions.HasBookAssigned(GlobalVar.BranchID, GlobalVar.BillMonth, GlobalVar.BillYear) = True Then
				'copy the database first
				Dim sDateTime As String
				lDateTime = DateTime.Now
				DateTime.DateFormat = "yyyyMMdd HHmmss a"
				sDateTime = DateTime.Date(lDateTime)
				If File.Exists(File.Combine(File.DirRootExternal,"documents"),"MasterDB") = False Then File.MakeDir(File.Combine(File.DirRootExternal,"documents"),"MasterDB")
				BackUpPath = File.Combine(File.DirRootExternal, "documents/MasterDB")
				LogColor(BackUpPath, Colors.Yellow)
				
				BackUpName = GlobalVar.UserName & "" & GlobalVar.BillPeriod & " " & sDateTime & $" Backup"$
				Starter.rp.GetAllSafeDirsExternal(BackUpPath)
				Starter.rp.CheckAndRequest(Starter.rp.PERMISSION_WRITE_EXTERNAL_STORAGE)

				Wait For Activity_PermissionResult (Permission As String, Result As Boolean)
				If Result = False Then
					ToastMessageShow ($"Unable to backup due to permission to write was denied"$,True)
				Else
					File.Copy(Starter.DBPath, Starter.DBName, BackUpPath, BackUpName)
					ToastMessageShow ($"Auto Backup Success!"$,True)
				End If
				
'				Wait For Activity_PermissionResult (Permission As String, Result As Boolean)
'				If Result = False Then
'					ToastMessageShow ($"Unable to backup due to permission to write was denied"$,True)
'				Else
'					File.Copy(Starter.DBPath, Starter.DBName,BackUpPath, BackUpName)
'					ToastMessageShow ($"Auto Backup Success!"$,True)
'				End If
			End If
			GlobalVar.ServerAddress = DBaseFunctions.GetServerIP
			DeleteBooks
			DownloadBooks(GlobalVar.BranchID)
		Case mDialog.ACTION_NEGATIVE
			Return
	End Select
End Sub

Private Sub DownloadBooks(iBranchID As Int)
	Dim URLName As String
	Dim RetVal As String

	Dim j As HttpJob

	j.Initialize("",Me)
	URLName = GlobalVar.ServerAddress & GlobalVar.ControllerPrefix & "bookData"
	Log (URLName & $"BranchID = "$ & iBranchID)
	j.Download2(URLName, Array As String("BranchID", iBranchID))
	
	ProgressDialogShow2($"Downloading Book/Zone Data..."$, False)
	Wait For (j) JobDone(j As HttpJob)
	If j.Success Then
		RetVal = j.GetString
		Log(RetVal)
		If RetVal = "[]" Then
			snack.Initialize("", Activity, $"No Book Information available to Download!"$, snack.DURATION_LONG)
			SetSnackBarBackground(snack, Colors.Red)
			SetSnackBarTextColor(snack, Colors.White)
			snack.Show
			j.Release
			Return
		End If
		SaveBookData(RetVal)
	Else
		snack.Initialize("RetryDownloadBooks", Activity, $"Unable to Download Book Data due to "$ & j.ErrorMessage, snack.DURATION_LONG)
		SetSnackBarBackground(snack, Colors.Red)
		SetSnackBarTextColor(snack, Colors.White)
		snack.SetAction("Retry")
		snack.Show
		Log(j.ErrorMessage)
		ProgressDialogHide
	End If
	j.Release
	
	ProgressDialogHide
End Sub

Private Sub DownloadEmployeeInfo(iReaderID As Int, iBranchID As Int)
	Dim URLName As String
	Dim RetVal As String

	Dim j As HttpJob
	
	Log($"ReaderID = "$ & iReaderID)
	j.Initialize("",Me)
	URLName = GlobalVar.ServerAddress & GlobalVar.ControllerPrefix & "employeeInfo"
	j.Download2(URLName, Array As String("UserID", iReaderID, "BranchID", iBranchID))
	Log(URLName & "?" & "&UserID=" &  iReaderID & "&BranchID=" & iBranchID )
	
	ProgressDialogShow2($"Downloading Reader's Data..."$, False)
	Wait For (j) JobDone(j As HttpJob)
	If j.Success Then
		RetVal = j.GetString
		Log(URLName)
		Log(RetVal)
		
		If RetVal = "[]" Then
			j.Release
			snack.Initialize("", Activity, $"Employee info not available to download!"$, snack.DURATION_LONG)
			SetSnackBarBackground(snack, Colors.Red)
			SetSnackBarTextColor(snack, Colors.White)
			snack.Show
			Return
		End If
		SaveReaderInfo(RetVal)
	Else
		snack.Initialize("RetryDownloadEmployeeInfo", Activity, $"Unable to Selected Reader Information due to "$ & j.ErrorMessage, snack.DURATION_LONG)
		SetSnackBarBackground(snack, Colors.Red)
		SetSnackBarTextColor(snack, Colors.White)
		snack.SetAction("Retry")
		snack.Show
		Log(j.ErrorMessage)
		ProgressDialogHide
	End If
	j.Release
	ProgressDialogHide
End Sub

Private Sub DownloadBookAssignments(iBranchID As Int, iBillYear As Int, iBillMonth As Int, iReaderID As Int, sReadingDate As String)
	Dim URLName As String
	Dim RetVal As String

	Dim j As HttpJob
	j.Initialize("",Me)
	URLName = GlobalVar.ServerAddress & GlobalVar.ControllerPrefix & "bookAssignments"
	j.Download2(URLName, Array As String("BranchID",iBranchID,"BillYear",iBillYear, "BillMonth", iBillMonth, "ReaderID", iReaderID, "rdgDate", sReadingDate))

	sURL = URLName & "?BranchID=" & iBranchID & "&BillYear=" & iBillYear &  "&BillMonth=" &  iBillMonth &  "&ReaderID=" &  iReaderID &  "&rdgDate=" &  sReadingDate
	LogColor(sURL, Colors.Red)
	ProgressDialogShow2($"Downloading Book Assignments..."$, False)
	Wait For (j) JobDone(j As HttpJob)
	If j.Success Then
		RetVal = j.GetString
		Log(URLName)
		Log(RetVal)
		If RetVal = "[]" Then
			snack.Initialize("", Activity, $"No book assignment(s) available to Download!"$, snack.DURATION_LONG)
			SetSnackBarBackground(snack, Colors.Red)
			SetSnackBarTextColor(snack, Colors.White)
			snack.Show
			j.Release
			Return
		End If
		SaveBookAssignments(RetVal)
	Else
		snack.Initialize("RetryDownloadBookAssignments", Activity, $"Unable to Download Book Assignment(s) due to "$ & j.ErrorMessage, snack.DURATION_LONG)
		SetSnackBarBackground(snack, Colors.Red)
		SetSnackBarTextColor(snack, Colors.White)
		snack.SetAction("Retry")
		snack.Show
		Log(j.ErrorMessage)
		ProgressDialogHide
	End If
	j.Release
	ProgressDialogHide
End Sub

Private Sub DownloadCustomers(iBranchID As Int, iReaderID As Int, sReadingDate As String)
	Dim URLName As String
	Dim RetVal As String

	Dim j As HttpJob
	j.Initialize("",Me)
	URLName = GlobalVar.ServerAddress & GlobalVar.ControllerPrefix & "getDetails"
	j.Download2(URLName, Array As String("branchID", iBranchID, "userID", iReaderID, "rdgDate", sReadingDate))
	Log(URLName & "?" & "BranchID=" & iBranchID &  "&ReaderID=" &  iReaderID &  "&rdgDate=" &  sReadingDate)
	
	ProgressDialogShow2($"Downloading Customer Accounts and Data..."$, False)
	Wait For (j) JobDone(j As HttpJob)
	If j.Success Then
		RetVal = j.GetString
		Log(URLName)
		Log(RetVal)
		If RetVal = "[]" Then
			j.Release
			snack.Initialize("", Activity, $"No customer account available to download!"$, snack.DURATION_LONG)
			SetSnackBarBackground(snack, Colors.Red)
			SetSnackBarTextColor(snack, Colors.White)
			snack.Show
			Return
		End If
		SaveCustomerAccounts(RetVal)
	Else
		snack.Initialize("RetryDownloadCustomers", Activity, $"Unable to Download Customer's Account(s) due to "$ & j.ErrorMessage, snack.DURATION_LONG)
		SetSnackBarBackground(snack, Colors.Red)
		SetSnackBarTextColor(snack, Colors.White)
		snack.SetAction("Retry")
		snack.Show
		Log(j.ErrorMessage)
		ProgressDialogHide
	End If
	j.Release
	ProgressDialogHide
End Sub

Private Sub DownloadRateHeader(iBranchID As Int)
	Dim URLName As String
	Dim RetVal As String
	Dim j As HttpJob
	j.Initialize("",Me)
	URLName = GlobalVar.ServerAddress & GlobalVar.ControllerPrefix & "getRatesHeader"
	j.Download2(URLName, Array As String("BranchID", iBranchID))
	ProgressDialogShow2($"Downloading Rates Data..."$, False)
	Wait For (j) JobDone(j As HttpJob)
	If j.Success Then
		RetVal = j.GetString
		Log(URLName)
		Log(RetVal)
		If RetVal = "[]" Then
			snack.Initialize("", Activity, $"No Branch Rate Master available to Download!"$, snack.DURATION_LONG)
			SetSnackBarBackground(snack, Colors.Red)
			SetSnackBarTextColor(snack, Colors.White)
			snack.Show
			j.Release
			Return
		End If
		SaveRateHeader(RetVal)
	Else
		snack.Initialize("RetryDownloadRateHeader", Activity, $"Unable to Download Data due to "$ & j.ErrorMessage, snack.DURATION_LONG)
		SetSnackBarBackground(snack, Colors.Red)
		SetSnackBarTextColor(snack, Colors.White)
		snack.SetAction("Retry")
		snack.Show
		Log(j.ErrorMessage)
		ProgressDialogHide
	End If
	j.Release
	ProgressDialogHide
End Sub

Private Sub DownloadRateDetails(iBranchID As Int)
	Dim URLName As String
	Dim RetVal As String
	
	Dim j As HttpJob
	j.Initialize("",Me)
	URLName = GlobalVar.ServerAddress & GlobalVar.ControllerPrefix & "getRatesDetails"
	j.Download2(URLName, Array As String("BranchID", iBranchID))
	ProgressDialogShow2($"Downloading Rates Data..."$, False)
	
	Wait For (j) JobDone(j As HttpJob)
	If j.Success Then
		RetVal = j.GetString
		Log(URLName)
		Log(RetVal)
		If RetVal = "[]" Then
			snack.Initialize("", Activity, $"No Branch Rate Details available to Download!"$, snack.DURATION_LONG)
			SetSnackBarBackground(snack, Colors.Red)
			SetSnackBarTextColor(snack, Colors.White)
			snack.Show
			j.Release
			Return
		End If
		SaveRateDetails(RetVal)
	Else
		snack.Initialize("RetryDownloadRateDetails", Activity, $"Unable to Download Data due to "$ & j.ErrorMessage, snack.DURATION_LONG)
		SetSnackBarBackground(snack, Colors.Red)
		SetSnackBarTextColor(snack, Colors.White)
		snack.SetAction("Retry")
		snack.Show
		Log(j.ErrorMessage)
		ProgressDialogHide
	End If
	j.Release
	ProgressDialogHide
End Sub

Private Sub DownloadBookPCA(iBranchID As Int, sReadingDate As String)
	Dim URLName As String
	Dim RetVal As String

	Dim j As HttpJob
	j.Initialize("",Me)
	URLName = GlobalVar.ServerAddress & GlobalVar.ControllerPrefix & "bookPCA"
	j.Download2(URLName, Array As String("BranchID",iBranchID, "CutOff", sReadingDate))
	Log(URLName & "?" & "BranchID=" & iBranchID & "rdgDate=" &  sReadingDate)
	
	ProgressDialogShow2($"Downloading Data..."$, False)
	Wait For (j) JobDone(j As HttpJob)
	If j.Success Then
		RetVal = j.GetString
		Log(URLName)
		Log(RetVal)
		If RetVal = "[]" Then
			snack.Initialize("", Activity, $"No PCA record found on the specified branch!"$, snack.DURATION_LONG)
			SetSnackBarBackground(snack, Colors.Red)
			SetSnackBarTextColor(snack, Colors.White)
			snack.Show
			j.Release
			Return
		End If
		SaveBookPCA(RetVal)
	Else
		snack.Initialize("RetryDownloadBookPCA", Activity, $"Unable to Download Book PCA(s) due to "$ & j.ErrorMessage, snack.DURATION_LONG)
		SetSnackBarBackground(snack, Colors.Red)
		SetSnackBarTextColor(snack, Colors.White)
		snack.SetAction("Retry")
		snack.Show
		Log(j.ErrorMessage)
		ProgressDialogHide
	End If
	j.Release
	ProgressDialogHide
End Sub

Private Sub DownloadSeptageRates(iBranchID As Int, sReadingDate As String)
	Dim URLName As String
	Dim RetVal As String

	Dim j As HttpJob
	j.Initialize("",Me)
	URLName = GlobalVar.ServerAddress & GlobalVar.ControllerPrefix & "getSeptageRates"
	j.Download2(URLName, Array As String("BranchID",iBranchID, "RdgDate", sReadingDate))
	Log(URLName & "?" & "BranchID=" & iBranchID & "RdgDate=" &  sReadingDate)
	
	ProgressDialogShow2($"Downloading Data..."$, False)
	Wait For (j) JobDone(j As HttpJob)
	If j.Success Then
		RetVal = j.GetString
		Log(URLName)
		Log(RetVal)
		If RetVal = "[]" Then
			snack.Initialize("", Activity, $"No Septage Rates found on the specified branch!"$, snack.DURATION_LONG)
			SetSnackBarBackground(snack, Colors.Red)
			SetSnackBarTextColor(snack, Colors.White)
			snack.Show
			j.Release
			Return
		End If
		SaveSSMRates(RetVal)
	Else
		snack.Initialize("RetryDownloadSeptageRates", Activity, $"Unable to Download Septage Rates due to "$ & j.ErrorMessage, snack.DURATION_LONG)
		SetSnackBarBackground(snack, Colors.Red)
		SetSnackBarTextColor(snack, Colors.White)
		snack.SetAction("Retry")
		snack.Show
		Log(j.ErrorMessage)
		ProgressDialogHide
	End If
	j.Release
	ProgressDialogHide
End Sub
#End Region

#Region Saving Downloaded Data
Private Sub SaveBookData(sReturnVal As String)
	Dim parser As JSONParser
	Dim root As List
	
	parser.Initialize(sReturnVal)
	root = parser.NextArray

	Try
		For Each MP As Map In root
			Starter.strCriteria="INSERT INTO tblBooks VALUES (?, ?, ?, ?, ?, ?, ?)"
			Starter.DBCon.AddNonQueryToBatch(Starter.strCriteria ,Array(MP.Get("BookID"), MP.Get("BranchID"), MP.Get("BookCode"), MP.Get("BookDesc"), MP.Get("NoDueDate"), MP.Get("WithPCA"), $"0"$))
		Next
		
		Dim SenderFilter As Object = Starter.DBCon.ExecNonQueryBatch("SQL")
		Wait For (SenderFilter) SQL_NonQueryComplete (Success As Boolean)
		
		If Success Then
			snack.Initialize("", Activity, $"Book/Zone Data were successfully downloaded."$, snack.DURATION_SHORT)
			SetSnackBarBackground(snack,Colors.White)
			SetSnackBarTextColor(snack, GlobalVar.PriColor)
			snack.Show
			DeleteEmployeeInfo
			DownloadEmployeeInfo(ReaderID, GlobalVar.BranchID)
		Else
			snack.Initialize("RetryDownloadBooks", Activity, $"Unable to save book assignment(s) due to "$ & LastException.Message, snack.DURATION_LONG)
			snack.SetAction("Retry")
			SetSnackBarBackground(snack, Colors.Red)
			SetSnackBarTextColor(snack, Colors.White)
			snack.Show
			Log(LastException)
		End If
	Catch
		snack.Initialize("RetryDownloadBooks", Activity, $"Unable to save book assignment(s) due to "$ & LastException.Message, snack.DURATION_LONG)
		snack.SetAction("Retry")
		SetSnackBarBackground(snack, Colors.Red)
		SetSnackBarTextColor(snack, Colors.White)
		snack.Show
		Log(LastException)
	End Try
'
'	Try
'		For Each MP As Map In root
'			Starter.DBCon.ExecNonQuery2(Starter.strCriteria, Array As String(MP.Get("BookID"), MP.Get("BranchID"), MP.Get("BookCode"), MP.Get("BookDesc"), MP.Get("NoDueDate"), MP.Get("WithPCA")))
'		Next
'
'		snack.Initialize("", Activity, $"Branch' Book Data were successfully downloaded."$, snack.DURATION_SHORT)
'		SetSnackBarBackground(snack,Colors.White)
'		SetSnackBarTextColor(snack, GlobalVar.PriColor)
'		snack.Show
'
'	Catch
'		snack.Initialize("", Activity, $"Unable to save book information due to "$ & LastException.Message, snack.DURATION_LONG)
'		SetSnackBarBackground(snack, Colors.Red)
'		SetSnackBarTextColor(snack, Colors.White)
'		snack.Show
'		Log(LastException)
'	End Try
End Sub

Private Sub SaveReaderInfo(sReturnVal As String)
	Dim parser As JSONParser
	Dim root As List
	
	parser.Initialize(sReturnVal)
	root = parser.NextArray

	Try
		For Each MP As Map In root
			Starter.strCriteria="INSERT INTO tblUsers VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
			Starter.DBCon.AddNonQueryToBatch(Starter.strCriteria ,Array(MP.Get("UserID"), MP.Get("BranchID"), MP.Get("EmpName"), MP.Get("UserName"), MP.Get("UserPassword"), MP.Get("Module1"), MP.Get("Module2"), MP.Get("Module3"), MP.Get("Module4"), MP.Get("Module5"), MP.Get("Module6"), $"1"$))
		Next
		Dim SenderFilter As Object = Starter.DBCon.ExecNonQueryBatch("SQL")
		Wait For (SenderFilter) SQL_NonQueryComplete (Success As Boolean)
		If Success Then
			snack.Initialize("", Activity, $"Specified Reader Info were successfully downloaded."$, snack.DURATION_SHORT)
			SetSnackBarBackground(snack,Colors.White)
			SetSnackBarTextColor(snack, GlobalVar.PriColor)
			snack.Show
			DeleteAssignments
			DownloadBookAssignments(GlobalVar.BranchID, GlobalVar.BillYear, GlobalVar.BillMonth, ReaderID, strRdgDate)
		Else
			snack.Initialize("RetryDownloadEmployeeInfo", Activity, $"Unable to Reader Info due to "$ & LastException.Message, snack.DURATION_LONG)
			snack.SetAction("Retry")
			SetSnackBarBackground(snack, Colors.Red)
			SetSnackBarTextColor(snack, Colors.White)
			snack.Show
			Log(LastException)
		End If
	Catch
		snack.Initialize("RetryDownloadEmployeeInfo", Activity, $"Unable to Reader Info due to "$ & LastException.Message, snack.DURATION_LONG)
		snack.SetAction("Retry")
		SetSnackBarBackground(snack, Colors.Red)
		SetSnackBarTextColor(snack, Colors.White)
		snack.Show
		Log(LastException)
	End Try
End Sub

Private Sub SaveBookAssignments(sReturnVal As String)
	Dim parser As JSONParser
	Dim root As List
	
	parser.Initialize(sReturnVal)
	root = parser.NextArray

	Try
		For Each MP As Map In root
			Starter.strCriteria="INSERT INTO tblBookAssignments VALUES (" & Null & ", ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
			Starter.DBCon.AddNonQueryToBatch(Starter.strCriteria ,Array(GlobalVar.BranchID, GlobalVar.BillYear, GlobalVar.BillMonth, MP.Get("BookID"), MP.Get("BookCode"), MP.Get("BookDesc"), _
													MP.Get("NoOfAccts"), MP.Get("PrevRdgDate"), MP.Get("LastBillNo"), ReaderID, strRdgDate, MP.Get("BillPeriodFrom"), MP.Get("BillPeriodTo"), MP.Get("DueDate"), $"0"$, $""$))
		Next
		Dim SenderFilter As Object = Starter.DBCon.ExecNonQueryBatch("SQL")
		Wait For (SenderFilter) SQL_NonQueryComplete (Success As Boolean)
		If Success Then
			snack.Initialize("", Activity, $"Book Assignment(s) were successfully downloaded."$, snack.DURATION_SHORT)
			SetSnackBarBackground(snack,Colors.White)
			SetSnackBarTextColor(snack, GlobalVar.PriColor)
			snack.Show
			DeleteReadings
			DownloadCustomers(GlobalVar.BranchID, ReaderID, strRdgDate)
		Else
			snack.Initialize("RetryDownloadBookAssignments", Activity, $"Unable to save book assignment(s) due to "$ & LastException.Message, snack.DURATION_LONG)
			snack.SetAction("Retry")
			SetSnackBarBackground(snack, Colors.Red)
			SetSnackBarTextColor(snack, Colors.White)
			snack.Show
			Log(LastException)
		End If
	Catch
		snack.Initialize("RetryDownloadBookAssignments", Activity, $"Unable to save book assignment(s) due to "$ & LastException.Message, snack.DURATION_LONG)
		snack.SetAction("Retry")
		SetSnackBarBackground(snack, Colors.Red)
		SetSnackBarTextColor(snack, Colors.White)
		snack.Show
		Log(LastException)
	End Try
End Sub

Private Sub SaveCustomerAccounts(sReturnVal As String)
	Dim parser As JSONParser
	Dim root As List
	Dim sSeqNo As String
	
	parser.Initialize(sReturnVal)
	root = parser.NextArray

	Try
		For Each MP As Map In root
			If GlobalVar.SF.Len(MP.Get("SeqNo")) >= 4 Then
				sSeqNo = MP.Get("SeqNo")
			Else If GlobalVar.SF.Len(MP.Get("SeqNo")) = 3 Then
				sSeqNo = "0" & MP.Get("SeqNo")
			Else If GlobalVar.SF.Len(MP.Get("SeqNo")) = 2 Then
				sSeqNo = "00" & MP.Get("SeqNo")
			Else If GlobalVar.SF.Len(MP.Get("SeqNo")) = 1 Then
				sSeqNo = "000" & MP.Get("SeqNo")
			End If
			Starter.strCriteria="INSERT INTO tblReadings VALUES (" & Null & ", ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)"
			Starter.DBCon.AddNonQueryToBatch(Starter.strCriteria ,Array(MP.Get("BillNo"), MP.Get("BillYear"), MP.Get("BillMonth"), MP.Get("BranchID"), MP.Get("BookID"), MP.Get("BookCode"), MP.Get("AcctID"), MP.Get("AcctNo"), MP.Get("OldAcctNo"), MP.Get("AcctName"), MP.Get("AcctAddress"), MP.Get("AcctClass"), MP.Get("AcctSubClass"), MP.Get("AcctStatus"), MP.Get("MeterID"), MP.Get("MeterNo"), MP.Get("MaxReading"), sSeqNo, MP.Get("IsSenior"), MP.Get("SeniorOnBefore"), MP.Get("SeniorAfter"), MP.Get("SeniorMaxCum"), MP.Get("GDeposit"), MP.Get("PrevRdgDate"), MP.Get("PrevRdg"), MP.Get("PrevCum"), MP.Get("BillPeriod1st"), MP.Get("PrevCum1st"), MP.Get("BillPeriod2nd"), MP.Get("PrevCum2nd"), MP.Get("BillPeriod3rd"), MP.Get("PrevCum3rd"), MP.Get("FinalRdg"), MP.Get("DisconDate"), MP.Get("PresRdgDate"), $""$, $""$, $""$, MP.Get("DateFrom"), MP.Get("DateTo"), MP.Get("WithDueDate"), MP.Get("DueDate"), MP.Get("DisconnectionDate"), MP.Get("AveCum"), MP.Get("BillType"), MP.Get("AddCum"), $"0"$, $"0.00"$, $"0.00"$, $"0.00"$, MP.Get("AddToBill"), MP.Get("AtbRef"), MP.Get("MeterCharges"), MP.Get("FranchiseTaxPct"), $"0.00"$, MP.Get("HasSeptageFee"), MP.Get("MinSeptageCum"), MP.Get("MaxSeptageCum"), MP.Get("SeptageRate"), MP.Get("SeptageArrears"), $"0.00"$, $"0.00"$, $"0.00"$, MP.Get("Arrears"), $"0.00"$, $"0.00"$, $"0.00"$, $"0.00"$, MP.Get("AdvPayment"), MP.Get("PenaltyPct"), $"0.00"$, $"0.00"$, $"0.00"$, $""$, $"0"$, $"0"$, $"0"$, $"0"$, $"0"$, $"0"$, $""$, $"0"$, $""$, $"0"$, $"0"$, $"0"$, $""$, $""$, MP.Get("RdgSequence"), $"0"$, $""$, $""$, ReaderID, $"0"$))
		Next
		Dim SenderFilter As Object = Starter.DBCon.ExecNonQueryBatch("SQL")
		Wait For (SenderFilter) SQL_NonQueryComplete (Success As Boolean)
		If Success Then
			snack.Initialize("", Activity, $"Customer Account(s) were successfully downloaded."$, snack.DURATION_SHORT)
			SetSnackBarBackground(snack,Colors.White)
			SetSnackBarTextColor(snack, GlobalVar.PriColor)
			snack.Show
			DeleteRateHeader
			DownloadRateHeader(GlobalVar.BranchID)
		Else
			snack.Initialize("RetryDownloadCustomers", Activity, $"Unable to save Customer Account(s) due to "$ & LastException.Message, snack.DURATION_LONG)
			snack.SetAction("Retry")
			SetSnackBarBackground(snack, Colors.Red)
			SetSnackBarTextColor(snack, Colors.White)
			snack.Show
			Log(LastException)
		End If
	Catch
		snack.Initialize("RetryDownloadCustomers", Activity, $"Unable to save book assignment(s) due to "$ & LastException.Message, snack.DURATION_LONG)
		snack.SetAction("Retry")
		SetSnackBarBackground(snack, Colors.Red)
		SetSnackBarTextColor(snack, Colors.White)
		snack.Show
		Log(LastException)
	End Try
End Sub

Private Sub SaveRateHeader(sReturnVal As String)
	Dim parser As JSONParser
	Dim root As List
	
	parser.Initialize(sReturnVal)
	root = parser.NextArray

	Try
		For Each MP As Map In root
			Starter.strCriteria="INSERT INTO rates_header VALUES (?, ?, ?, ?, ?, ?, ?)"
			Starter.DBCon.AddNonQueryToBatch(Starter.strCriteria ,Array(MP.Get("id"), MP.Get("branch_id"), MP.Get("class"), MP.Get("sub_class"), MP.Get("is_active"), MP.Get("date_from"), MP.Get("date_to")))
		Next
		Dim SenderFilter As Object = Starter.DBCon.ExecNonQueryBatch("SQL")
		Wait For (SenderFilter) SQL_NonQueryComplete (Success As Boolean)
		If Success Then
			snack.Initialize("", Activity, $"Rate Master were successfully downloaded."$, snack.DURATION_SHORT)
			SetSnackBarBackground(snack,Colors.White)
			SetSnackBarTextColor(snack, GlobalVar.PriColor)
			snack.Show
			DeleteRateDetails
			DownloadRateDetails(GlobalVar.BranchID)
		Else
			snack.Initialize("RetryDownloadRateHeader", Activity, $"Unable to save Rate Master due to "$ & LastException.Message, snack.DURATION_LONG)
			snack.SetAction("Retry")
			SetSnackBarBackground(snack, Colors.Red)
			SetSnackBarTextColor(snack, Colors.White)
			snack.Show
			Log(LastException)
		End If
	Catch
		snack.Initialize("RetryDownloadRateHeader", Activity, $"Unable to save Rate Master due to "$ & LastException.Message, snack.DURATION_LONG)
		snack.SetAction("Retry")
		SetSnackBarBackground(snack, Colors.Red)
		SetSnackBarTextColor(snack, Colors.White)
		snack.Show
		Log(LastException)
	End Try
End Sub

Private Sub SaveRateDetails(sReturnVal As String)
	Dim parser As JSONParser
	Dim root As List
	
	parser.Initialize(sReturnVal)
	root = parser.NextArray

	Try
		For Each MP As Map In root
			Starter.strCriteria="INSERT INTO rates_details VALUES (" & Null & ", ?, ?, ?, ?, ?)"
			Starter.DBCon.AddNonQueryToBatch(Starter.strCriteria, Array(MP.Get("header_id"), MP.Get("rangefrom"), MP.Get("rangeto"), MP.Get("typecust"), MP.Get("amount")))
		Next
		Dim SenderFilter As Object = Starter.DBCon.ExecNonQueryBatch("SQL")
		Wait For (SenderFilter) SQL_NonQueryComplete (Success As Boolean)
		If Success Then
			snack.Initialize("", Activity, $"Rate Details were successfully downloaded."$, snack.DURATION_SHORT)
			SetSnackBarBackground(snack,Colors.White)
			SetSnackBarTextColor(snack, GlobalVar.PriColor)
			snack.Show
			DeletePCA
			DownloadBookPCA(GlobalVar.BranchID, strRdgDate)
		Else
			snack.Initialize("RetryDownloadRateDetails", Activity, $"Unable to save Rate Detail(s) due to "$ & LastException.Message, snack.DURATION_LONG)
			snack.SetAction("Retry")
			SetSnackBarBackground(snack, Colors.Red)
			SetSnackBarTextColor(snack, Colors.White)
			snack.Show
			Log(LastException)
		End If
	Catch
		snack.Initialize("RetryDownloadRateDetails", Activity, $"Unable to save Rate Detail(s) due to "$ & LastException.Message, snack.DURATION_LONG)
		snack.SetAction("Retry")
		SetSnackBarBackground(snack, Colors.Red)
		SetSnackBarTextColor(snack, Colors.White)
		snack.Show
		Log(LastException)
	End Try
End Sub

Private Sub SaveBookPCA(sReturnVal As String)
	Dim parser As JSONParser
	Dim root As List
	
	parser.Initialize(sReturnVal)
	root = parser.NextArray

	Try
		For Each MP As Map In root
			Starter.strCriteria="INSERT INTO tblPCA VALUES (" & Null & ", ?, ?, ?, ?)"
			Starter.DBCon.AddNonQueryToBatch(Starter.strCriteria, Array(MP.Get("branch_id"), MP.Get("book_id"), MP.Get("cutoff"), MP.Get("amount")))
		Next
		Dim SenderFilter As Object = Starter.DBCon.ExecNonQueryBatch("SQL")
		Wait For (SenderFilter) SQL_NonQueryComplete (Success As Boolean)
		If Success Then
			snack.Initialize("", Activity, $"PCA Data were successfully downloaded."$, snack.DURATION_SHORT)
			SetSnackBarBackground(snack,Colors.White)
			SetSnackBarTextColor(snack, GlobalVar.PriColor)
			snack.Show
			If GlobalVar.WithSeptageFee = 1 Then
				DeleteSeptageRates
				DownloadSeptageRates(GlobalVar.BranchID, strRdgDate)
			Else
				ShowDLComplete($"Download Complete"$,$"Reading Data for the specified Reader were successfully downloaded."$)
			End If
		Else
			snack.Initialize("RetryDownloadBookPCA", Activity, $"Unable to save PCA Data due to "$ & LastException.Message, snack.DURATION_LONG)
			snack.SetAction("Retry")
			SetSnackBarBackground(snack, Colors.Red)
			SetSnackBarTextColor(snack, Colors.White)
			snack.Show
			Log(LastException)
		End If
	Catch
		snack.Initialize("RetryDownloadBookPCA", Activity, $"Unable to save PCA Data due to "$ & LastException.Message, snack.DURATION_LONG)
		snack.SetAction("Retry")
		SetSnackBarBackground(snack, Colors.Red)
		SetSnackBarTextColor(snack, Colors.White)
		snack.Show
		Log(LastException)
	End Try
End Sub

Private Sub SaveSSMRates(sReturnVal As String)
	Dim parser As JSONParser
	Dim root As List
	
	parser.Initialize(sReturnVal)
	root = parser.NextArray

	Try
		For Each MP As Map In root
			Starter.strCriteria="INSERT INTO SSMRates VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
			Starter.DBCon.AddNonQueryToBatch(Starter.strCriteria, Array(MP.Get("SeptageRatesID"), MP.Get("BranchID"), MP.Get("AcctClassification"), MP.Get("MinCum"), MP.Get("MaxCum"), MP.Get("RateType"), MP.Get("RatePerCum"), MP.Get("CutOff")))
		Next
		Dim SenderFilter As Object = Starter.DBCon.ExecNonQueryBatch("SQL")
		Wait For (SenderFilter) SQL_NonQueryComplete (Success As Boolean)
		If Success Then
			snack.Initialize("", Activity, $"Septage Rates Data were successfully downloaded."$, snack.DURATION_SHORT)
			SetSnackBarBackground(snack,Colors.White)
			SetSnackBarTextColor(snack, GlobalVar.PriColor)
			snack.Show
			ShowDLComplete($"Download Complete"$,$"Reading Data for the specified Reader were successfully downloaded."$)
		Else
			snack.Initialize("RetryDownloadSeptageRates", Activity, $"Unable to save Septage Rates Data due to "$ & LastException.Message, snack.DURATION_LONG)
			snack.SetAction("Retry")
			SetSnackBarBackground(snack, Colors.Red)
			SetSnackBarTextColor(snack, Colors.White)
			snack.Show
			Log(LastException)
		End If
	Catch
		snack.Initialize("RetryDownloadSeptageRates", Activity, $"Unable to save Septage Rates Data due to "$ & LastException.Message, snack.DURATION_LONG)
		snack.SetAction("Retry")
		SetSnackBarBackground(snack, Colors.Red)
		SetSnackBarTextColor(snack, Colors.White)
		snack.Show
		Log(LastException)
	End Try
End Sub
#End Region

#Region Download Retries
Private Sub RetryDownloadBooks_Click()
	DeleteBooks
	DownloadBooks(GlobalVar.BranchID)
End Sub

Private Sub RetryDownloadEmployeeInfo_Click()
	DeleteEmployeeInfo
	DownloadEmployeeInfo(ReaderID, GlobalVar.BranchID)
End Sub

Private Sub RetryDownloadBookAssignments_Click()
	DeleteAssignments
	DownloadBookAssignments(GlobalVar.BranchID, GlobalVar.BillYear, GlobalVar.BillMonth, ReaderID, strRdgDate)
End Sub

Private Sub RetryDownloadCustomers_Click()
	DeleteReadings
	DownloadCustomers(GlobalVar.BranchID, ReaderID, strRdgDate)
End Sub

Private Sub RetryDownloadRateHeader_Click()
	DeleteRateHeader
	DownloadRateHeader(GlobalVar.BranchID)
End Sub

Private Sub RetryDownloadRateDetails_Click()
	DeleteRateDetails
	DownloadRateDetails(GlobalVar.BranchID)
End Sub

Private Sub RetryDownloadBookPCA_Click()
	DeletePCA
	DownloadBookPCA(GlobalVar.BranchID, strRdgDate)
End Sub
Private Sub RetryDownloadSeptageRates_Click()
	DeleteSeptageRates
	DownloadSeptageRates(GlobalVar.BranchID, strRdgDate)
End Sub

#End Region
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
#Region Delete Data
Private Sub DeleteBooks()
	Starter.strCriteria="DELETE FROM tblBooks WHERE BranchID = " & GlobalVar.BranchID
	Starter.DBCon.ExecNonQuery(Starter.strCriteria)
End Sub

Private Sub DeleteRateHeader()
	Starter.strCriteria="DELETE FROM rates_header WHERE branch_id = " & GlobalVar.BranchID
	Starter.DBCon.ExecNonQuery(Starter.strCriteria)
End Sub

Private Sub DeleteRateDetails()
	Starter.strCriteria="DELETE FROM rates_details"
	Starter.DBCon.ExecNonQuery(Starter.strCriteria)
End Sub

Private Sub DeleteAssignments()
	Starter.strCriteria="DELETE FROM tblBookAssignments " & _
						"WHERE BranchID = " & GlobalVar.BranchID & " " & _
						"AND BillYear = " & GlobalVar.BillYear & " " & _
						"AND BillMonth = " & GlobalVar.BillMonth & " " & _
						"AND UserID = " & ReaderID & " " & _
						"AND PresRdgDate = '" & strRdgDate & "'"
	Starter.DBCon.ExecNonQuery(Starter.strCriteria)
	Starter.strCriteria="DELETE FROM NewSequence "
	Starter.DBCon.ExecNonQuery(Starter.strCriteria)
End Sub

Private Sub DeleteReadings()
	Dim dDate As Long
	Dim sPresRdgDate As String
	Dim sYear, sMonth, sDay As String

'	DateTime.DateFormat="MM/dd/yyyy"
''	dDate = DateTime.DateParse(strRdgDate)
	''	sPresRdgDate = DateTime.Date(dDate)
	DateTime.DateFormat="yyyy-MM-dd"
	dDate = DateTime.DateParse(strRdgDate)
	DateTime.DateFormat="MM/dd/yyyy"
	sPresRdgDate = DateTime.Date(dDate)

'	sPresRdgDate = strRdgDate
	Log(sPresRdgDate)

	Starter.strCriteria="DELETE FROM tblReadings " & _
						"WHERE BillYear = " & GlobalVar.BillYear & " " & _
						"AND BillMonth = " & GlobalVar.BillMonth & " " & _
						"AND BranchID = " & GlobalVar.BranchID & " " & _
						"AND PresRdgDate = '" & sPresRdgDate & "' " & _
						"AND ReadBy = " & ReaderID 
	Starter.DBCon.ExecNonQuery(Starter.strCriteria)
End Sub

Private Sub DeleteEmployeeInfo()
	Starter.strCriteria="DELETE FROM tblUsers " & _
						"WHERE UserID = " & ReaderID 
	Starter.DBCon.ExecNonQuery(Starter.strCriteria)
End Sub

Private Sub DeletePCA()
	Starter.strCriteria="DELETE FROM tblPCA WHERE BranchID = " & GlobalVar.BranchID
	Starter.DBCon.ExecNonQuery(Starter.strCriteria)
End Sub

Private Sub DeleteSeptageRates()
	Starter.strCriteria="DELETE FROM SSMRates WHERE BranchID = " & GlobalVar.BranchID
	Starter.DBCon.ExecNonQuery(Starter.strCriteria)
End Sub

Sub IsThereAnyData(sTableName As String) As Boolean
	Dim blnReturn As Boolean
	Dim rsRecCount As Cursor
	Try
		Starter.strCriteria = "SELECT * FROM " & sTableName
		rsRecCount = Starter.DBCon.ExecQuery(Starter.strCriteria)
		
		If rsRecCount.RowCount>0 Then
			blnReturn = True
		Else
			blnReturn = False
		End If
	Catch
		ToastMessageShow("Unable to check " & sTableName & " due to " & LastException.Message,True)
		blnReturn = False
	End Try
	Return blnReturn
End Sub

Sub ResetData(sTableName As String)
	Try
		Starter.strCriteria = "DELETE FROM SQLITE_SEQUENCE WHERE name='" & sTableName & "'"
		Starter.DBCon.ExecNonQuery(Starter.strCriteria)
	Catch
		ToastMessageShow("Unable to reset table due to " & LastException.Message & ".",False)
	End Try
End Sub
#End Region


#Region UploadData
Private Sub ShowUploadPanel
'	cboReaderUpload.Background = myStyle
	cdButtonCancel.Initialize(0xFFDC143C, 25)
	btnCancelUpload.Background = cdButtonCancel

	cdButtonOK.Initialize(0xFF1976D2, 25)
	btnOkUpload.Background = cdButtonOK

	pnlButtons.Visible = False
	pnlUploadBox.Visible = True
	
	csNote.Initialize.Size(15).Color(Colors.Red).Append($"NOTE: "$)
	csNote.Size(13).Color(GlobalVar.PriColor).Append($"This Process cannot be undone...  "$ & CRLF)
	csNote.Append($"This will upload all valid readings. Miscoded reading and/or negative reading will not be Uploaded."$)
	csNote.PopAll
	
	lblULNote.Text = csNote
	
	LoadUploader
End Sub


Sub btnOkUpload_Click
	If cboReaderUpload.SelectedItem.Length <= 0 Then
		snack.Initialize("", Activity, $"Please select the reader you wanted to Upload his reading data."$, snack.DURATION_SHORT)
		SetSnackBarBackground(snack, Colors.Red)
		SetSnackBarTextColor(snack, Colors.White)
		snack.Show
		Return
	End If
	
	intReaderID = DBaseFunctions.GetIDbyCode("ReaderID","tblReaders", "ReaderName", cboReaderUpload.SelectedItem)
	If intReaderID <=0 Then
		snack.Initialize("", Activity, $"Reader Unknown..."$, snack.DURATION_SHORT)
		SetSnackBarBackground(snack, Colors.Red)
		SetSnackBarTextColor(snack, Colors.White)
		snack.Show
		Return
	End If
'	ConfirmUpload
	ConfirmUL($"UPLOAD READING DATA"$,$"This process will UPLOAD ALL Reading data on the specified BOOK. Uploaded readings will be locked."$ & CRLF & $"Continue uploading readings from your specified reader and book?"$)
End Sub

Sub btnCancelUpload_Click
	ShowCancelUploading
End Sub

Private Sub ShowUPPasswordDialog
	MatDialogBuilder.Initialize("RetUPPassword")
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

Private Sub RetUPPassword_InputChanged (mDialog As MaterialDialog, sInput As String)
	Dim csBuild As CSBuilder
	If sInput.Length <= 0 Then
		csBuild.Initialize.Bold.Color(Colors.Red).Append($"Enter your Password to Continue..."$).PopAll
		mDialog.Content = csBuild
		mDialog.EnableActionButton(mDialog.ACTION_POSITIVE, False)
	Else
		If sInput <> GlobalVar.UserPW Then
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

Private Sub RetUPPassword_ButtonPressed (mDialog As MaterialDialog, sAction As String)
	Select Case sAction
		Case mDialog.ACTION_POSITIVE
			Dim lDateTime As Long
			Dim rsBookAssign As Cursor
			Dim BookList As List
			Dim mpBooks As Map
			Dim Res As Int
			
			
			BookList.Initialize
			Starter.strCriteria="SELECT * FROM tblBookAssignments " & _
								"WHERE UserID = " & intReaderID & " " & _
								"AND WasUploaded = 0"
			rsBookAssign=Starter.DBCon.ExecQuery(Starter.strCriteria)

			If rsBookAssign.RowCount = 0 Then
				xui.MsgboxAsync("No assigned book(s)ready for uploading...", Application.LabelName)
				Return
			Else
				For i = 0 To rsBookAssign.RowCount - 1
					rsBookAssign.Position = i
					mpBooks.Initialize
					BookList.Add(rsBookAssign.GetString("BookCode"))
				Next
			End If
					
			Res = InputList(BookList,"Select a Book To Upload", -1)
			Log(Res)
			
			If Res <> DialogResponse.CANCEL Then
				ProgressDialogShow2($"Generating data from Book "$ & BookList.Get(Res) & $" for uploading..."$, False)
				intUploadedBookID = DBaseFunctions.GetIDbyCode("BookID", "tblBooks", "BookCode", BookList.Get(Res))
				Log (intUploadedBookID)
			Else
				snack.Initialize("", Activity, $"Uploading Data Canceled!"$, snack.DURATION_SHORT)
				SetSnackBarBackground(snack, Colors.Red)
				SetSnackBarTextColor(snack,Colors.White)
				snack.Show
				Return
			End If
			strReadingData = ConvertReadingJSON(GlobalVar.BranchID, intUploadedBookID)

			SaveJSON(intUploadedBookID, strReadingData)
			
			If strReadingData="" Or strReadingData.Length=0 Then
				ProgressDialogHide
				snack.Initialize("", Activity, $"No Reading Data found..."$, snack.DURATION_SHORT)
				SetSnackBarBackground(snack, Colors.Red)
				SetSnackBarTextColor(snack,Colors.White)
				snack.Show
				Return
			End If
			
			ProgressDialogHide

			If DispUploadMsg($"Reading data ready for Uploading!"$ & CRLF  & $"You are about to upload "$ & UploadCtr & $" record(s)..."$ & CRLF & CRLF & $"Tap Ok to continue..."$, $"DATA UPLOADING READY"$) = False Then
				snack.Initialize("", Activity, $"Uploading data cancelled."$, snack.DURATION_SHORT)
				SetSnackBarBackground(snack, Colors.Red)
				SetSnackBarTextColor(snack, Colors.White)
				snack.Show
				Return
			Else
				'Backup Data before Upload
				Dim sDateTime As String
				Dim lDateTime As Long
	
				lDateTime = DateTime.Now
				DateTime.DateFormat = "yyyyMMdd HHmmss a"
				sDateTime = DateTime.Date(lDateTime)
	
				If File.Exists(File.Combine(File.DirRootExternal,"documents"),"MasterDB") = False Then File.MakeDir(File.Combine(File.DirRootExternal,"documents"),"MasterDB")
				BackUpPath = File.Combine(File.DirRootExternal, "documents/MasterDB/")
				LogColor(BackUpPath, Colors.Yellow)

				BackUpName = GetReaderName(intReaderID) & " " & sDateTime & " backup.db"
				Try					
					Starter.rp.GetAllSafeDirsExternal(BackUpPath)
					Starter.rp.CheckAndRequest(Starter.rp.PERMISSION_WRITE_EXTERNAL_STORAGE)

					Wait For Activity_PermissionResult (Permission As String, Result As Boolean)
					
					If Result = False Then
						ToastMessageShow ($"Unable to backup due to permission to write was denied"$,True)
					Else
						File.Copy(Starter.DBPath, Starter.DBName, BackUpPath, BackUpName)
						ToastMessageShow ($"Auto Backup Success!"$,True)
					End If

				Catch
					Log(LastException)
				End Try
			End If
			
			Dim iDBase As Intent
			iDBase.Initialize(iDBase.ACTION_VIEW, "file://" & File.Combine(BackUpPath, BackUpName & "/"))
			iDBase.SetType( "resource/folder" )
			
			Log(strReadingData)
			ProgressDialogShow2("Reading Data Uploading..." & CRLF & "Please Wait.", False)
			
			UploadData(strReadingData)

		Case mDialog.ACTION_NEGATIVE
			snack.Initialize("", Activity, $"Uploading reading data was cancelled"$, snack.DURATION_SHORT)
			SetSnackBarBackground(snack, Colors.Red)
			SetSnackBarTextColor(snack,Colors.White)
			snack.Show
			Return
	End Select
End Sub

Private Sub GetReaderName(iReaderID As Int) As String
	Dim sRetVal As String
	Try
		sRetVal = Starter.DBCon.ExecQuerySingleResult2("SELECT UserName FROM tblUsers WHERE UserID = ?", Array As String(iReaderID))
	Catch
		sRetVal = ""
		Log(LastException)
	End Try
	Return sRetVal
End Sub

Sub ConvertReadingJSON(iBranchID As Int, iBookID As Int) As String
	Dim JSONGen As JSONGenerator
	Dim strJson As String
	Dim RecCount As Int
	Dim TitleList, HeaderList, DetailList As List
	Dim MP1, MP2, MP3 As Map
	Dim AdvPayment As Double

	Dim dBasic As Double
	Dim sBasic As String
					
	Dim dDisc As Double
	Dim sDisc As String

	Dim dPCAAmt As Double
	Dim sPCAAmt As String

	Dim dbillAmt As Double
	Dim sBillAmt As String

	Dim dOthers As Double
	Dim sOthers As String

	Dim dTotalDue As Double
	Dim sTotalDue As String
					
	Dim dPenalty As Double
	Dim sPenalty As String
					
	Dim dAdvPayment As Double
	Dim sAdvPayment As String
					
	Dim dBalance As Double
	Dim sBalance As String
	
	Dim dMeterCharges As Double
	Dim sMeterCharges As String
	
	Dim dFranchiseTaxAmt As Double
	Dim sFranchiseTaxAmt As String

	Dim dSeptageFee As Double
	Dim sSeptageFee As String
	
	Dim rdg_datetime As String
	Dim rdgDate, rdgTime As String
	Dim DisconnectionDate As String
	
	UploadCtr = 0
	Try
		TitleList.Initialize
		HeaderList.Initialize
		DetailList.Initialize
		DisconnectionDate = Starter.DBCon.ExecQuerySingleResult("SELECT DisconnectionDate FROM tblReadings WHERE BookID = " & iBookID & " GROUP BY BookID")
		LogColor(DisconnectionDate, Colors.Yellow)
		
		Starter.strCriteria="SELECT * FROM tblBookAssignments " & _
						"WHERE BillYear = " & GlobalVar.BillYear & " " & _
						"And BillMonth = " & GlobalVar.BillMonth & " " & _
						"AND BranchID = " & iBranchID & " " & _
						"AND UserID = " & intReaderID & " " & _
						"AND BookID = " & iBookID
		
		rsHeader = Starter.DBCon.ExecQuery(Starter.strCriteria)
		
		If rsHeader.RowCount > 0 Then
			rsHeader.Position=0
			MP1.Initialize
			HeaderList.Clear
			MP1.Put("Header", HeaderList)
			
			MP2.Initialize
			MP2.Put("branch_id", iBranchID)
			MP2.Put("bill_month", GlobalVar.BillMonth)
			MP2.Put("bill_year", GlobalVar.BillYear)
			MP2.Put("book_id", iBookID)
			MP2.Put("batch_number", rsHeader.GetString("BookCode"))
			MP2.Put("batch_type", $"RB"$)
			MP2.Put("previous_rdg_date", rsHeader.GetString("PrevRdgDate"))
			MP2.Put("present_rdg_date", rsHeader.GetString("PresRdgDate"))
			MP2.Put("due_date", rsHeader.GetString("DueDate"))
			MP2.Put("reader_id", intReaderID)
			MP2.Put("discon_date", DisconnectionDate)
			HeaderList.Add(MP2)
			MP1.Put("Details", DetailList)
			
			Starter.strCriteria="SELECT * FROM tblReadings WHERE BranchID = " & iBranchID & " " & _
								"AND BookID = " & iBookID & " " & _
								"AND BillYear = " & GlobalVar.BillYear & " " & _
								"AND BillMonth = " & GlobalVar.BillMonth & " " & _
								"AND WasRead = 1 " & _
								"AND WasMissCoded = 0 " & _
								"AND PresCum >= 0 " & _
								"AND PresRdg <> '" & "" & "' " & _
								"AND WasUploaded = 0 " & _
								"AND PrintStatus = 1 " & _
								"ORDER BY BillNo ASC "'LIMIT 10"
			rsDetails=Starter.DBCon.ExecQuery(Starter.strCriteria)
			Log(Starter.strCriteria)
			
			UploadCtr = rsDetails.RowCount
			
			If rsDetails.RowCount > 0 Then
				RecCount = rsDetails.RowCount
				DetailList.Clear
'				For i = 0 To 3
				For i = 0 To RecCount - 1
					rsDetails.Position = i
					Dim sStatus, sRemarks As String

					sRemarks = rsDetails.GetString("ReadRemarks")
					MP3.Initialize
					MP3.Put("bill_number", rsDetails.GetString("BillNo"))
					MP3.Put("bill_type", rsDetails.GetString("BillType"))
					MP3.Put("account_id", rsDetails.GetInt("AcctID"))
					MP3.Put("acct_class", rsDetails.GetString("AcctClass"))
					MP3.Put("acct_subclass", rsDetails.GetString("AcctSubClass"))
					MP3.Put("meter_id", rsDetails.GetInt("MeterID"))
					MP3.Put("present_rdg", rsDetails.GetInt("PresRdg"))
					MP3.Put("previous_rdg", rsDetails.GetInt("PrevRdg"))
					MP3.Put("actual_cum", rsDetails.GetInt("PresCum"))
					MP3.Put("additional_cum", rsDetails.GetInt("AddCons"))
					MP3.Put("total_cum", rsDetails.GetInt("TotalCum"))

					dBasic = rsDetails.GetDouble("BasicAmt")
					MP3.Put("basic", dBasic)
					
					dPCAAmt = rsDetails.GetDouble("PCAAmt")
					MP3.Put("pca", dPCAAmt)

					dDisc = rsDetails.GetDouble("DiscAmt")
					MP3.Put("comp_disc", dDisc)

					dbillAmt = (rsDetails.GetDouble("BasicAmt") + rsDetails.GetDouble("PCAAmt"))
					MP3.Put("bill_amount", dbillAmt)

					dOthers = rsDetails.GetDouble("AddToBillAmt")
					MP3.Put("others", dOthers)

					dMeterCharges = rsDetails.GetDouble("MeterCharges")
					MP3.Put("meter_charges", dMeterCharges)
					
					dFranchiseTaxAmt = rsDetails.GetDouble("FranchiseTaxAmt")
					MP3.Put("franchise_tax", dFranchiseTaxAmt)
					
					dSeptageFee = rsDetails.GetDouble("SeptageAmt")
					dTotalDue = rsDetails.GetDouble("BasicAmt") + rsDetails.GetDouble("PCAAmt") + rsDetails.GetDouble("AddToBillAmt") + rsDetails.GetDouble("MeterCharges") + rsDetails.GetDouble("FranchiseTaxAmt") + rsDetails.GetDouble("SeptageAmt")
					dBalance = (rsDetails.GetDouble("BasicAmt") + rsDetails.GetDouble("AddToBillAmt") + rsDetails.GetDouble("MeterCharges") + rsDetails.GetDouble("FranchiseTaxAmt") + rsDetails.GetDouble("SeptageAmt")) - dAdvPayment
					
					MP3.Put("septage_fee", dSeptageFee)
					MP3.Put("total_due", dTotalDue)
					
					rdgDate = rsDetails.GetString("DateRead")
					rdgTime = rsDetails.GetString("TimeRead")
					
					rdg_datetime = rdgDate & " - " & rdgTime

					If rsDetails.GetInt("WithDueDate") = 1 Then
						MP3.Put("due_date", rsDetails.GetString("DueDate"))
						
						dPenalty = (rsDetails.GetDouble("BasicAmt") + rsDetails.GetDouble("PCAAmt")) * rsDetails.GetDouble("PenaltyPct")
						MP3.Put("penalty", dPenalty)
					Else
						MP3.Put("due_date", $""$)
						MP3.Put("penalty", 0)
					End If
					
					If rsDetails.GetDouble("AdvPayment") >= (rsDetails.GetDouble("BasicAmt") + rsDetails.GetDouble("PCAAmt") + rsDetails.GetDouble("AddToBillAmt") + rsDetails.GetDouble("MeterCharges") + rsDetails.GetDouble("FranchiseTaxAmt") + rsDetails.GetDouble("SeptageAmt")) Then
						dAdvPayment = rsDetails.GetDouble("BasicAmt") + rsDetails.GetDouble("PCAAmt") + rsDetails.GetDouble("AddToBillAmt") + rsDetails.GetDouble("MeterCharges") + rsDetails.GetDouble("FranchiseTaxAmt") + rsDetails.GetDouble("SeptageAmt")
					Else
						dAdvPayment = rsDetails.GetDouble("AdvPayment")
					End If
					
					MP3.Put("advance", dAdvPayment)
					MP3.Put("amount_paid", $"0"$)
					MP3.Put("balance", dBalance)
					
					If (rsDetails.GetDouble("BasicAmt") + rsDetails.GetDouble("AddToBillAmt") + rsDetails.GetDouble("MeterCharges") + rsDetails.GetDouble("FranchiseTaxAmt") + rsDetails.GetDouble("SeptageAmt")) <= rsDetails.GetInt("AdvPayment") Then
						sStatus = "P"
					Else
						sStatus = "UP"
					End If

					MP3.Put("status", sStatus)
					MP3.Put("findings", sRemarks)
					MP3.Put("miss_code", rsDetails.GetString("MissCode"))
					MP3.Put("validation_type", rsDetails.GetString("ImplosiveType"))
					MP3.Put("previous_rdg_date", rsDetails.GetString("PrevRdgDate"))
					MP3.Put("previous_cum", rsDetails.GetInt("PrevCum"))
					MP3.Put("atb_reference", rsDetails.GetString("AtbRef"))
					MP3.Put("num_input", rsDetails.GetString("NoInput"))
					MP3.Put("rdg_sequence", rsDetails.GetInt("NewSeqNo"))
					MP3.Put("senior", rsDetails.GetDouble("SeniorOnBeforeAmt"))
					MP3.Put("max_reading", rsDetails.GetInt("MaxReading"))
					MP3.Put("rdg_datetime", rdg_datetime)
					DetailList.Add(MP3)
				Next
				Log(rsDetails.RowCount)
			End If

			TitleList.Add(MP1)
			JSONGen.Initialize2(TitleList)

			Log (JSONGen.ToString)

			strJson=JSONGen.ToString
			Log (strJson)
'			MsgboxAsync(JSONGen.ToPrettyString(3),"Generated")
		Else
			strJson = ""
		End If
		
	Catch
		strJson = ""
		Log(LastException.Message)
		ToastMessageShow($"Unable to upload readings due to failed json conversion."$, False)
		Return strJson

	End Try
		Log (strJson)
		Return strJson
	End Sub

Sub UploadData(sData As String)
	Dim retVal As String
	Dim jParser As JSONParser
	
	Dim j As HttpJob
	j.Initialize("", Me)
	
	j.PostString(GlobalVar.ServerAddress & GlobalVar.ControllerPrefix & "upload", sData)
	j.GetRequest.SetHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.92 Safari/537.36")
	j.GetRequest.SetContentType("plain/text")
	Log(sData)
	
	Wait For (j) JobDone(j As HttpJob)
	If j.Success Then
		retVal = j.GetString
		jParser.Initialize(retVal)
		Log(retVal)
		ProgressDialogHide
		j.Release
				
		If UpdateUploadStatus(intUploadedBookID) = True Then
			If AreAllAccountsUploaded(intUploadedBookID,GlobalVar.BillMonth, GlobalVar.BillYear) = True Then
				UpdateBook(intUploadedBookID)
			End If
			ShowUpLComplete($"Data Upload Complete"$,$"Reading Data for the specified Reader and Book were successfully uploaded!"$)
		End If
	Else
		ProgressDialogHide
		Log(j.ErrorMessage)
		jParser.Initialize(retVal)
		Log(retVal)
				
		ToastMessageShow("Unable to Upload Reading Data due to " & j.ErrorMessage,True)
		j.Release
		Log(j.ErrorMessage)
		Return
	End If
End Sub

Private Sub ShowCancelUploading
	Dim csContent As CSBuilder
	csContent.Initialize.Size(14).Color(Colors.DarkGray).Bold.Append($"Cancel Uploading Reading Data?"$).PopAll
	MatDialogBuilder.Initialize("CancelUL")
	MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIGHT)
	MatDialogBuilder.Title($"CANCEL UPLOADING"$).TitleColor(GlobalVar.PriColor).TitleGravity(MatDialogBuilder.GRAVITY_START)
	MatDialogBuilder.IconRes(GlobalVar.QuestionIcon).LimitIconToDefaultSize
	MatDialogBuilder.Content(csContent)
	MatDialogBuilder.PositiveText($"YES"$).PositiveColor(GlobalVar.PriColor)
	MatDialogBuilder.NegativeText($"NO"$).NegativeColor(Colors.Red)
	MatDialogBuilder.CanceledOnTouchOutside(False)
	MatDialogBuilder.Show
End Sub

Private Sub CancelUL_ButtonPressed(Dialog As MaterialDialog, sAction As String)
	Select Case sAction
		Case Dialog.ACTION_POSITIVE
			pnlButtons.Visible = True
			pnlDownloadBox.Visible = False
			pnlUploadBox.Visible = False
			snack.Initialize("",Activity,$"Uploading Reading data was cancelled"$,snack.DURATION_SHORT)
			SetSnackBarBackground(snack, Colors.Red)
			SetSnackBarTextColor(snack, Colors.White)
			snack.Show
		Case Dialog.ACTION_NEGATIVE
			Return
	End Select
End Sub

'Private Sub ConfirmUpload
'	Dim csContent As CSBuilder
'	csContent.Initialize.Size(14).Color(Colors.Red).Bold.Append($"REMINDERS: THIS PROCESS REQUIRES YOUR PASSWORD!"$).Color(Colors.DarkGray).Append(CRLF & $"This process will "$).Color(GlobalVar.PriColor).Append($"UPLOAD ALL"$).Color(Colors.DarkGray).Append($" Reading data on the specified BOOK. Uploaded readings will be locked."$ & CRLF & CRLF & $"Continue uploading readings from your specified reader and book?"$).PopAll
'	MatDialogBuilder.Initialize("Uploading")
'	MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIGHT)
'	MatDialogBuilder.Title($"UPLOAD READING DATA"$).TitleColor(GlobalVar.PriColor).TitleGravity(MatDialogBuilder.GRAVITY_START)
'	MatDialogBuilder.IconRes(GlobalVar.WarningIcon).LimitIconToDefaultSize
'	MatDialogBuilder.Content(csContent)
'	MatDialogBuilder.PositiveText($"YES"$).PositiveColor(GlobalVar.PriColor)
'	MatDialogBuilder.NegativeText($"NO"$).NegativeColor(Colors.Red)
'	MatDialogBuilder.CanceledOnTouchOutside(False)
'	MatDialogBuilder.Show
'End Sub
'
'Private Sub Uploading_ButtonPressed(Dialog As MaterialDialog, sAction As String)
'	Select Case sAction
'		Case Dialog.ACTION_POSITIVE
'			ShowUPPasswordDialog
'		Case Dialog.ACTION_NEGATIVE
'			Return
'	End Select
'End Sub

Sub UpdateBook(iBookID As Int)
	Try
		Starter.strCriteria="UPDATE tblBookAssignments SET WasUploaded = ?, UploadedBy = ?, DateUploaded = ? WHERE BookID = " & iBookID & " AND BillYear = " & GlobalVar.BillYear & " AND BillMonth = " & GlobalVar.BillMonth
		Starter.DBCon.ExecNonQuery2(Starter.strCriteria, Array As String($"1"$, GlobalVar.UserID, DateTime.Date(DateTime.Now)))
		Starter.DBCon.TransactionSuccessful
		ToastMessageShow("Selected book was successfully uploaded!", False)
	Catch
		Log(LastException)
	End Try
End Sub

Sub UpdateHasData(iReaderID As Int)
	Try
		Starter.strCriteria="UPDATE tblReaders SET HasData = ? WHERE ReaderID = " & iReaderID
		Starter.DBCon.ExecNonQuery2(Starter.strCriteria, Array As String($"1"$))
		Starter.DBCon.TransactionSuccessful
		ToastMessageShow("Selected book was successfully uploaded!", False)
	Catch
		Log(LastException)
	End Try
End Sub

Sub UpdateUploadStatus(iBookID As Int) As Boolean
	Dim blnRetVal As Boolean

	blnRetVal = False
	Try
		Starter.DBCon.BeginTransaction
		Starter.strCriteria="UPDATE tblReadings SET WasUploaded = ?  " & _
							"WHERE BranchID = " & GlobalVar.BranchID & " " & _
							"AND BookID = " & iBookID & " " & _
							"And BillYear = " & GlobalVar.BillYear & " " & _
							"And BillMonth = " & GlobalVar.BillMonth & " " & _
							"And WasRead = 1 " & _
							"AND PresCum >= 0 " & _
							"AND PresRdg <> '" & "" & "' " & _
							"AND WasUploaded = 0 " & _
							"AND PrintStatus > 0 " & _
							"And WasMissCoded = 0 "
		
		Starter.DBCon.ExecNonQuery2(Starter.strCriteria, Array As String($"1"$))
		Starter.DBCon.TransactionSuccessful
		Starter.DBCon.EndTransaction
		blnRetVal = True
	Catch
		blnRetVal = False
		Log(LastException)
	End Try

	Return blnRetVal
End Sub

Sub AreAllAccountsUploaded(iBookID As Int, iBillMonth As Int, iBillYear As Int) As Boolean
	Dim blnRetValue As Boolean
	Dim iCount As Int
	iCount = Starter.DBCon.ExecQuerySingleResult("SELECT Count(tblReadings.WasUploaded) As RecCount FROM tblReadings WHERE tblReadings.WasUploaded = 0 AND tblReadings.BillYear = " & iBillYear & " AND tblReadings.BillMonth = " & iBillMonth & " AND tblReadings.BookID = " & iBookID)
	If iCount > 0 Then
		blnRetValue=False
	Else
		blnRetValue=True
	End If
	Return blnRetValue

End Sub

Private Sub ShowUploadComplete
	Dim csTitle, csContent As CSBuilder
	csTitle.Initialize.Color(GlobalVar.PriColor).Bold.Size(16).Append($"Data Upload Complete"$).PopAll
	csContent.Initialize.Size(14).Color(GlobalVar.PriColor).Bold.Append($"Reading Data for the specified Reader and Book were successfully uploaded."$).PopAll
	
	MatDialogBuilder.Initialize("ULComplete")
	MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIGHT)
	MatDialogBuilder.Title(csTitle).TitleGravity(MatDialogBuilder.GRAVITY_START)
	MatDialogBuilder.IconRes(GlobalVar.InfoIcon).LimitIconToDefaultSize
	MatDialogBuilder.Content(csContent)
	MatDialogBuilder.PositiveText($"OK"$).PositiveColor(GlobalVar.PriColor)
	MatDialogBuilder.NegativeText($"Upload Another"$).NegativeColor(Colors.Red)
	MatDialogBuilder.CanceledOnTouchOutside(False)
	MatDialogBuilder.Show
End Sub

Private Sub ULComplete_ButtonPressed (mDialog As MaterialDialog, sAction As String)
	Select Case sAction
		Case mDialog.ACTION_POSITIVE
			Activity.Finish
		Case mDialog.ACTION_NEGATIVE
'			cboReader.RequestFocus
			Return
	End Select
End Sub

Private Sub ShowBackUpDbaseDialog
	Dim csTitle, csContent As CSBuilder
	csTitle.Initialize.Color(GlobalVar.PriColor).Bold.Size(16).Append($"CONFIRM BACK-UP"$).PopAll
	csContent.Initialize.Size(14).Color(GlobalVar.PriColor).Bold.Append($"Back-up Data before uploading?"$).PopAll
	
	MatDialogBuilder.Initialize("BackupDBase")
	MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIGHT)
	MatDialogBuilder.Title(csTitle).TitleGravity(MatDialogBuilder.GRAVITY_START)
	MatDialogBuilder.IconRes(GlobalVar.InfoIcon).LimitIconToDefaultSize
	MatDialogBuilder.Content(csContent)
	MatDialogBuilder.PositiveText($"YES"$).PositiveColor(GlobalVar.PriColor)
	MatDialogBuilder.NegativeText($"NO"$).NegativeColor(Colors.Red)
	MatDialogBuilder.CanceledOnTouchOutside(False)
	MatDialogBuilder.Show	
End Sub
#End Region



Sub btnUpload_Click()
	ShowUploadPanel
End Sub

Private Sub LoadUploader
	cboReaderUpload.Clear
	Try
		Starter.strCriteria = "SELECT DISTINCT tblReaders.ReaderName " & _
							  "FROM tblBookAssignments " & _
							  "INNER JOIN tblReaders ON tblBookAssignments.UserID = tblReaders.ReaderID " & _
							  "WHERE tblBookAssignments.WasUploaded = 0"
		rsReader = Starter.DBCon.ExecQuery(Starter.strCriteria)
		If rsReader.RowCount > 0 Then
			For i = 0 To rsReader.RowCount - 1
				rsReader.Position = i
				cboReaderUpload.Add(TitleCase(rsReader.GetString("ReaderName")))
			Next
		End If
	Catch
		Log(LastException)
	End Try
End Sub

Private Sub ConfirmCancelDownload
	Dim csContent As CSBuilder
	csContent.Initialize.Size(14).Color(Colors.DarkGray).Bold.Append($"Cancel Downloading Reading Data?"$).PopAll
	MatDialogBuilder.Initialize("CancelDL")
	MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIGHT)
	MatDialogBuilder.Title($"CANCEL DOWNLOADING"$).TitleColor(GlobalVar.PriColor).TitleGravity(MatDialogBuilder.GRAVITY_START)
	MatDialogBuilder.IconRes(GlobalVar.QuestionIcon).LimitIconToDefaultSize
	MatDialogBuilder.Content(csContent)
	MatDialogBuilder.PositiveText($"YES"$).PositiveColor(GlobalVar.PriColor)
	MatDialogBuilder.NegativeText($"NO"$).NegativeColor(Colors.Red)
	MatDialogBuilder.CanceledOnTouchOutside(False)
	MatDialogBuilder.Show
End Sub

Private Sub CancelDL_ButtonPressed(Dialog As MaterialDialog, sAction As String)
	Select Case sAction
		Case Dialog.ACTION_POSITIVE
			pnlButtons.Visible = True
			pnlDownloadBox.Visible = False
			pnlUploadBox.Visible = False
			snack.Initialize("",Activity,$"Downloading Reading data was cancelled"$,snack.DURATION_SHORT)
			SetSnackBarBackground(snack, Colors.Red)
			SetSnackBarTextColor(snack, Colors.White)
			snack.Show
		Case Dialog.ACTION_NEGATIVE
			Return
	End Select
End Sub

Private Sub DispInfoMsg(sMsg As String, sTitle As String)

	Dim csTitle, csMsg As CSBuilder
	csTitle.Initialize.Color(Colors.Red).Append(sTitle).PopAll
	csMsg.Initialize.Bold.Color(Colors.DarkGray).Append(CRLF & sMsg).PopAll
	
	Msgbox(csMsg, csTitle)
End Sub

Private Sub DispUploadMsg(sMsg As String, sTitle As String) As Boolean
	Dim csTitle, csMsg As CSBuilder
	Dim icon As B4XBitmap
	Dim blnRetVal As Boolean
	Dim iRetVal As Int
	csTitle.Initialize.Color(Colors.Red).Append(sTitle).PopAll
	csMsg.Initialize.Color(GlobalVar.PriColor).Append(CRLF & sMsg).PopAll
	icon = xui.LoadBitmapResize(File.DirAssets, "upload_ok.png", 24dip, 24dip, True)
'	Msgbox(csMsg, csTitle)
	iRetVal = Msgbox2(csMsg, csTitle, $"OK"$, "", $"CANCEL"$, icon)
	
	If iRetVal = DialogResponse.POSITIVE Then
		blnRetVal = True
	Else
		blnRetVal = False
	End If
	
	Return blnRetVal
End Sub

Private Sub SaveJSON(iBookID As Int, sText As String)
	Try
		Starter.DBCon.BeginTransaction
		Starter.strCriteria="UPDATE tblBookAssignments SET JSONText = ? WHERE BookID = " & iBookID & " AND BillYear = " & GlobalVar.BillYear & " AND BillMonth = " & GlobalVar.BillMonth
		Starter.DBCon.ExecNonQuery2(Starter.strCriteria, Array As String(sText))
		Starter.DBCon.TransactionSuccessful
	Catch
		Log(LastException)
	End Try
	Starter.DBCon.EndTransaction
End Sub

Sub lblReadingDate_Click
	Dim sDate As String
	Dim lRdgDate As Long
			
	lDate = DateTime.DateParse(DateTime.Date(DateTime.Now))
	MyDateDialogs.Initialize(Activity, lDate)
	DateTime.DateFormat = "MM/dd/yyyy"
	lRdgDate  = MyDateDialogs.Show($"Select New Present Reading Date"$)
			
	If lRdgDate  = DialogResponse.POSITIVE Then
		sDate = DateTime.GetMonth(MyDateDialogs.DateSelected) & "/" & DateTime.GetDayOfMonth(MyDateDialogs.DateSelected) & "/" & DateTime.GetYear(MyDateDialogs.DateSelected)
		lDate = DateTime.DateParse(sDate)
		sRdgDate = DateTime.Date(lDate)
	Else
		lDate = DateTime.DateParse(DateTime.Date(DateTime.Now))
		sRdgDate = DateTime.Date(lDate)
	End If
	lblReadingDate.Text = sRdgDate

End Sub

Sub lblRdgDateIcon_Click
	Dim sDate As String
	Dim lRdgDate As Long
			
	lDate = DateTime.DateParse(DateTime.Date(DateTime.Now))
	MyDateDialogs.Initialize(Activity, lDate)
	DateTime.DateFormat = "MM/dd/yyyy"
	lRdgDate  = MyDateDialogs.Show($"Select New Present Reading Date"$)
			
	If lRdgDate  = DialogResponse.POSITIVE Then
		sDate = DateTime.GetMonth(MyDateDialogs.DateSelected) & "/" & DateTime.GetDayOfMonth(MyDateDialogs.DateSelected) & "/" & DateTime.GetYear(MyDateDialogs.DateSelected)
		lDate = DateTime.DateParse(sDate)
		sRdgDate = DateTime.Date(lDate)
	Else
		lDate = DateTime.DateParse(DateTime.Date(DateTime.Now))
		sRdgDate = DateTime.Date(lDate)
	End If
	lblReadingDate.Text = sRdgDate
	
End Sub

#Region New Msgbox
Private Sub ConfirmDL(sTitle As String, sMsg As String)
	Dim AlertDialog As AX_CustomAlertDialog
	soundsAlarmChannel.Play(SoundID,1,1,1,0,1)
	vibration.vibrateOnce(2000)

	AlertDialog.Initialize
	AlertDialog.Initialize.Create _
			.SetDialogStyleName("MyDialogDisableStatus") _	'Manifest style name
			.SetStyle(AlertDialog.STYLE_DIALOGUE) _
			.SetCancelable(False) _
			.SetTitle(sTitle) _
			.SetTitleColor(Colors.Red) _
			.SetTitleTypeface(GlobalVar.FontBold) _
			.SetMessage(sMsg) _
			.SetMessageTypeface(GlobalVar.Font) _
			.SetPositiveTypeface(GlobalVar.FontBold) _
			.SetPositiveColor(Colors.Blue) _
			.SetPositiveText("YES") _
			.SetOnPositiveClicked("OnDownload") _ 'listeners
			.SetNegativeTypeface(GlobalVar.FontBold) _
			.SetNegativeColor(Colors.Red) _
			.SetOnNegativeClicked("OnDownload") _ 'listeners
			.SetNegativeText("CANCEL") _
			.SetOnViewBinder("DownloadViewBinder") 'listeners
	AlertDialog.SetDialogBackground(myCD)
	AlertDialog.Build.Show
End Sub

Private Sub OnDownload_OnPositiveClicked (View As View, Dialog As Object)
	Dim AlertDialog As AX_CustomAlertDialog
	
	vibration.vibrateCancel
	soundsAlarmChannel.Stop(SoundID)
	ShowDLPasswordDialog
	AlertDialog.Initialize.Dismiss(Dialog)
End Sub

Private Sub OnDownload_OnNegativeClicked (View As View, Dialog As Object)
	Dim AlertDialog As AX_CustomAlertDialog
	
	vibration.vibrateCancel
	soundsAlarmChannel.Stop(SoundID)
	AlertDialog.Initialize.Dismiss(Dialog)

End Sub

Private Sub DownloadViewBinder_OnBindView (View As View, ViewType As Int)
	Dim AlertDialog As AX_CustomAlertDialog
	AlertDialog.Initialize

	If ViewType = AlertDialog.VIEW_TITLE Then ' Title
		Dim lbl As Label = View
		Dim CS As CSBuilder
		
		CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Typeface(Typeface.MATERIALICONS).Size(26).Color(Colors.Red).Append(Chr(0xE2C4) & "  ")
		CS.Typeface(GlobalVar.Font).Size(22).Append(lbl.Text).Pop

		lbl.Text = CS.PopAll
	End If
	If ViewType = AlertDialog.VIEW_MESSAGE Then
		Dim lbl As Label = View
		lbl.TextSize = 16
		lbl.TextColor = Colors.Gray
	End If
End Sub
Private Sub ConfirmUL(sTitle As String, sMsg As String)
	Dim AlertDialog As AX_CustomAlertDialog
	soundsAlarmChannel.Play(SoundID,1,1,1,0,1)

	AlertDialog.Initialize
	AlertDialog.Initialize.Create _
			.SetDialogStyleName("MyDialogDisableStatus") _	'Manifest style name
			.SetStyle(AlertDialog.STYLE_DIALOGUE) _
			.SetCancelable(False) _
			.SetTitle(sTitle) _
			.SetTitleColor(Colors.Red) _
			.SetTitleTypeface(GlobalVar.FontBold) _
			.SetMessage(sMsg) _
			.SetMessageTypeface(GlobalVar.Font) _
			.SetPositiveTypeface(GlobalVar.FontBold) _
			.SetPositiveColor(Colors.Blue) _
			.SetPositiveText("YES") _
			.SetOnPositiveClicked("OnUpload") _ 'listeners
			.SetNegativeTypeface(GlobalVar.FontBold) _
			.SetNegativeColor(Colors.Red) _
			.SetOnNegativeClicked("OnUpload") _ 'listeners
			.SetNegativeText("CANCEL") _
			.SetOnViewBinder("UploadViewBinder") 'listeners
	AlertDialog.SetDialogBackground(myCD)
	AlertDialog.Build.Show
End Sub

Private Sub OnUpload_OnPositiveClicked (View As View, Dialog As Object)
	Dim AlertDialog As AX_CustomAlertDialog
	
	vibration.vibrateCancel
	soundsAlarmChannel.Stop(SoundID)
	ShowUPPasswordDialog
	AlertDialog.Initialize.Dismiss(Dialog)
End Sub

Private Sub OnUpload_OnNegativeClicked (View As View, Dialog As Object)
	Dim AlertDialog As AX_CustomAlertDialog
	
	vibration.vibrateCancel
	soundsAlarmChannel.Stop(SoundID)
	AlertDialog.Initialize.Dismiss(Dialog)
End Sub

Private Sub UploadViewBinder_OnBindView (View As View, ViewType As Int)
	Dim AlertDialog As AX_CustomAlertDialog
	AlertDialog.Initialize

	If ViewType = AlertDialog.VIEW_TITLE Then ' Title
		Dim lbl As Label = View
		Dim CS As CSBuilder
		
		CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Typeface(Typeface.MATERIALICONS).Size(26).Color(Colors.Red).Append(Chr(0xE2C6) & "  ")
		CS.Typeface(GlobalVar.Font).Size(16).Append(lbl.Text).Pop

		lbl.Text = CS.PopAll
	End If
	If ViewType = AlertDialog.VIEW_MESSAGE Then
		Dim lbl As Label = View
		lbl.TextSize = 16
		lbl.TextColor = Colors.Gray
	End If
End Sub


Private Sub ShowDLComplete(sTitle As String, sMsg As String)
	Dim AlertDialog As AX_CustomAlertDialog
	soundsAlarmChannel.Play(SoundID,1,1,1,0,1)
	vibration.vibrateOnce(2000)
	UpdateHasData(ReaderID)

	AlertDialog.Initialize
	AlertDialog.Initialize.Create _
			.SetDialogStyleName("MyDialogDisableStatus") _	'Manifest style name
			.SetStyle(AlertDialog.STYLE_DIALOGUE) _
			.SetCancelable(False) _
			.SetTitle(sTitle) _
			.SetTitleColor(Colors.Red) _
			.SetTitleTypeface(GlobalVar.FontBold) _
			.SetMessage(sMsg) _
			.SetMessageTypeface(GlobalVar.Font) _
			.SetPositiveTypeface(GlobalVar.FontBold) _
			.SetPositiveColor(Colors.Blue) _
			.SetPositiveText("OK") _
			.SetOnPositiveClicked("OnDownloadComplete") _ 'listeners
			.SetNegativeTypeface(GlobalVar.FontBold) _
			.SetNegativeColor(Colors.Red) _
			.SetNegativeText("DOWNLOAD ANOTHER?") _
			.SetOnNegativeClicked("OnDownloadComplete") _ 'listeners
			.SetOnViewBinder("DLULViewBinder") 'listeners
	AlertDialog.SetDialogBackground(myCD)
	AlertDialog.Build.Show
End Sub

Private Sub OnDownloadComplete_OnPositiveClicked (View As View, Dialog As Object)
	Dim AlertDialog As AX_CustomAlertDialog
	
	vibration.vibrateCancel
	soundsAlarmChannel.Stop(SoundID)
	AlertDialog.Initialize.Dismiss(Dialog)
	Activity.Finish
End Sub

Private Sub OnDownloadComplete_OnNegativeClicked (View As View, Dialog As Object)
	Dim AlertDialog As AX_CustomAlertDialog
	vibration.vibrateCancel
	soundsAlarmChannel.Stop(SoundID)
	AlertDialog.Initialize.Dismiss(Dialog)
End Sub

Private Sub ShowUpLComplete(sTitle As String, sMsg As String)
	Dim AlertDialog As AX_CustomAlertDialog
	soundsAlarmChannel.Play(SoundID,1,1,1,0,1)
	vibration.vibrateOnce(2000)
	
	AlertDialog.Initialize
	AlertDialog.Initialize.Create _
			.SetDialogStyleName("MyDialogDisableStatus") _	'Manifest style name
			.SetStyle(AlertDialog.STYLE_DIALOGUE) _
			.SetCancelable(False) _
			.SetTitle(sTitle) _
			.SetTitleColor(Colors.Red) _
			.SetTitleTypeface(GlobalVar.FontBold) _
			.SetMessage(sMsg) _
			.SetMessageTypeface(GlobalVar.Font) _
			.SetPositiveTypeface(GlobalVar.FontBold) _
			.SetPositiveColor(Colors.Blue) _
			.SetPositiveText("OK") _
			.SetOnPositiveClicked("OnUploadComplete") _ 'listeners
			.SetNegativeTypeface(GlobalVar.FontBold) _
			.SetNegativeColor(Colors.Red) _
			.SetNegativeText("UPLOAD ANOTHER?") _
			.SetOnNegativeClicked("OnUploadComplete") _ 'listeners
			.SetOnViewBinder("DLULViewBinder") 'listeners
	AlertDialog.SetDialogBackground(myCD)
	AlertDialog.Build.Show
End Sub

Private Sub OnUploadComplete_OnPositiveClicked (View As View, Dialog As Object)
	Dim AlertDialog As AX_CustomAlertDialog
	
	vibration.vibrateCancel
	soundsAlarmChannel.Stop(SoundID)
	AlertDialog.Initialize.Dismiss(Dialog)
	Activity.Finish
End Sub

Private Sub OnUploadComplete_OnNegativeClicked (View As View, Dialog As Object)
	Dim AlertDialog As AX_CustomAlertDialog	
	AlertDialog.Initialize.Dismiss(Dialog)
End Sub

Private Sub DLULViewBinder_OnBindView (View As View, ViewType As Int)
	Dim AlertDialog As AX_CustomAlertDialog
	AlertDialog.Initialize

	If ViewType = AlertDialog.VIEW_TITLE Then ' Title
		Dim lbl As Label = View
		Dim CS As CSBuilder
		
		CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Typeface(Typeface.MATERIALICONS).Size(26).Color(Colors.Red).Append(Chr(0xE834) & "  ")
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

Sub TitleCase (s As String) As String
	s = s.ToLowerCase
	Dim m As Matcher = Regex.Matcher("\b(\w)", s)
	Do While m.Find
		Dim i As Int = m.GetStart(1)
		s = s.SubString2(0, i) & s.SubString2(i, i + 1).ToUpperCase & s.SubString(i + 1)
	Loop
	Return s
End Sub

#End Region



