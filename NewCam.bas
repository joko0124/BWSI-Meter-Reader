B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=9.9
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: True
	#IncludeTitle: False
#End Region
#Extends: android.support.v7.app.AppCompatActivity

Sub Process_Globals
	Private frontCamera As Boolean = False
End Sub

Sub Globals
	Private ToolBar As ACToolBarDark
	Private ActionBarButton As ACActionBar
	
	'Material Dialog
	Private MatDialogBuilder As MaterialDialogBuilder

	Dim filename As String
	Dim dir As String = File.DirRootExternal
	
	Dim PictFolderName As String
	Dim PicturePath As String

	Private camEx As CameraExClass
	
	Private iBillMonth As Int
	Private sBillMonth As String
	Private sBillYear As String
	Private snack As DSSnackbar

	Private btnCapture As Panel
	Private btnFlash As Panel
	Private btnSwitch As Panel
	Private lblSwitch As Label
	Private pnlMain As Panel
	Private pnlSwitch As Panel
	Private pnlCapture As Panel
	Private lblCapture As Label
End Sub

Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("CameraLayout")
	
	GlobalVar.CSTitle.Initialize.Size(17).Bold.Append($"Acct. No "$ & GlobalVar.CamAcctNo).PopAll
	GlobalVar.CSSubTitle.Initialize.Size(14).Append($"Meter No.: "$ & GlobalVar.CamMeterNo).PopAll
	
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
		
End Sub

Sub Activity_Resume
	InitializeCamera
	iBillMonth = DBaseFunctions.GetBillMonth(GlobalVar.BranchID)
	sBillYear = DBaseFunctions.GetBillYear(GlobalVar.BranchID)
	If GlobalVar.SF.Len(iBillMonth) = 1 Then
		sBillMonth = "0" & iBillMonth
	Else
		sBillMonth = iBillMonth
	End If
End Sub

Sub Activity_KeyPress (KeyCode As Int) As Boolean 'Return True to consume the event
	If KeyCode = 4 Then
		ToolBar_NavigationItemClick
		Return True
	Else
		Return False
	End If
End Sub

Private Sub InitializeCamera
	camEx.Initialize(pnlMain, frontCamera, Me, "Camera1")
	frontCamera = camEx.Front
End Sub

Sub Activity_Pause (UserClosed As Boolean)
	camEx.Release
End Sub

Sub Activity_PermissionResult (Permission As String, Result As Boolean)
	If Permission = Starter.rp.PERMISSION_WRITE_EXTERNAL_STORAGE Then
		Result = True
		LogColor("YOU CAN Save to External Drive", Colors.Yellow)
	Else
		Result = False
	End If
End Sub

Sub Camera1_Ready (Success As Boolean)
	If Success Then
		camEx.StartPreview
		camEx.SetJpegQuality(90)
		camEx.CommitParameters
	Else
		ToastMessageShow("Cannot open camera.", True)
	End If
End Sub

Sub Camera1_PictureTaken (Data() As Byte)
	Starter.rp.CheckAndRequest(Starter.rp.PERMISSION_WRITE_EXTERNAL_STORAGE)
	Wait For Activity_PermissionResult (Permission As String, Result As Boolean)

	If Result = False Then
		ToastMessageShow ($"Unable to backup due to permission to write was denied"$,True)
	Else
'		'Backup Data before Upload
'		Dim sDateTime As String
'		Dim lDateTime As Long
'	
'		lDateTime = DateTime.Now
'		DateTime.DateFormat = "yyyyMMdd HHmmss a"
'		sDateTime = DateTime.Date(lDateTime)

		If File.Exists(File.Combine(File.DirRootExternal,"DCIM"),"MeterReading") = False Then File.MakeDir(File.Combine(File.DirRootExternal,"DCIM"),"MeterReading")
		PicturePath = File.Combine(File.DirRootExternal, "DCIM/MeterReading/")
		LogColor(PicturePath, Colors.Yellow)
		filename = sBillYear & "-" & sBillMonth & "-" & GlobalVar.CamAcctNo & ".jpg"
		camEx.SavePictureToFile(Data,PicturePath, filename)
		camEx.StartPreview 'restart preview

'
'		BackUpName = GetReaderName(intReaderID) & " " & sDateTime & " backup.db"
'
'		
'		PicturePath = File.Combine(dir, "DCIM")
'		PictFolderName = File.Combine(PicturePath, "MeterReading")
'
'		If File.Exists(PictFolderName, "") = False Then
'			File.MakeDir(PicturePath, "MeterReading")
'			PictFolderName = File.Combine(PicturePath, "MeterReading")
'		End If
	End If
	
	
	'send a broadcast intent to the media scanner to force it to scan the saved file.
	Dim Phone As Phone
	Dim i As Intent
	i.Initialize("android.intent.action.MEDIA_SCANNER_SCAN_FILE", _
		"file://" & File.Combine(PictFolderName, filename))
	Phone.SendBroadcastIntent(i)	
	PictureTaken
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
#End Region


Sub ToolBar_NavigationItemClick
	Activity.Finish
End Sub

Private Sub PictureTaken
	Dim csTitle As CSBuilder
	Dim csContent As CSBuilder
	
	csTitle.Initialize.Typeface(Typeface.FONTAWESOME).Color(GlobalVar.PriColor).Size(16).Bold.Append(Chr(0xF129) & $" Picture Taken"$).PopAll
	csContent.Initialize.Color(Colors.DarkGray).Append($"Picture has been successfully taken."$).PopAll
	
	MatDialogBuilder.Initialize("TakeAnother")
	MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIGHT)
	MatDialogBuilder.Title(csTitle)
'	MatDialogBuilder.IconRes(GlobalVar.InfoIcon).LimitIconToDefaultSize
	MatDialogBuilder.Content(csContent)
	MatDialogBuilder.PositiveText($"OK"$).PositiveColor(GlobalVar.PriColor)
	MatDialogBuilder.NegativeText($"TRY AGAIN"$).NegativeColor(Colors.Red)
	MatDialogBuilder.NeutralText($"OPEN"$).NeutralColor(GlobalVar.PriColor)
	MatDialogBuilder.CanceledOnTouchOutside(False)
	MatDialogBuilder.Show
End Sub

Sub TakeAnother_ButtonPressed (mDialog As MaterialDialog, sAction As String)
	Select Case sAction
		Case mDialog.ACTION_POSITIVE
			If GlobalVar.isAverage = 1 Then
				GlobalVar.blnAverage = True
			Else
				GlobalVar.blnAverage = False
			End If
			snack.Initialize("",Activity,$"Picture saved. Filename : "$ & filename,snack.DURATION_SHORT)
			SetSnackBarBackground(snack, Colors.White)
			SetSnackBarTextColor(snack, GlobalVar.PriColor)
			snack.Show

			Activity.Finish
		Case mDialog.ACTION_NEGATIVE
			pnlCapture.Enabled = True
			btnCapture.Enabled = True
			lblCapture.Enabled = True

			Return
		Case mDialog.ACTION_NEUTRAL
			Dim i As Intent
			i.Initialize(i.ACTION_VIEW, File.Combine(PictFolderName, filename))
			i.SetType("image/*")
			StartActivity(i)
			Activity.Finish
	End Select
End Sub


#Region Button
Sub btnFlash_Click
	Dim flashModes As List = camEx.GetSupportedFlashModes

	If flashModes.IsInitialized = False Then
		ToastMessageShow("Flash not supported.", False)
		Return
	End If
	Dim flash As String = flashModes.Get((flashModes.IndexOf(camEx.GetFlashMode) + 1) Mod flashModes.Size)
	camEx.SetFlashMode(flash)
	ToastMessageShow(flash, False)
	camEx.CommitParameters
End Sub

Sub btnCapture_Click
	camEx.TakePicture
	pnlCapture.Enabled = False
	btnCapture.Enabled = False
'	lblCapture.Enabled = False
	
	btnFlash.Enabled = False
	btnSwitch.Enabled = False
End Sub

Sub btnSwitch_Click
	camEx.Release
	frontCamera = Not(frontCamera)
	InitializeCamera
End Sub

#End Region