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
	Private frontCamera As Boolean = False
End Sub

Sub Globals
	Private Panel1 As Panel
	Private camEx As CameraExClass
	Private iBillMonth As Int
	Private sBillMonth As String
	Private sBillYear As String
	Private snack As DSSnackbar
End Sub

Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("Camera")
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

Private Sub InitializeCamera
	camEx.Initialize(Panel1, frontCamera, Me, "Camera1")
	frontCamera = camEx.Front
End Sub

Sub Activity_Pause (UserClosed As Boolean)
	camEx.Release
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

Sub btnTakePicture_Click
	camEx.TakePicture
End Sub

Sub Camera1_PictureTaken (Data() As Byte)
	Dim filename As String
	Dim dir As String = File.DirRootExternal
	
	filename = sBillYear & "-" & sBillMonth & "-" & GlobalVar.strBookSequence & ".jpg"
	camEx.SavePictureToFile(Data, File.DirRootExternal, filename)
	camEx.StartPreview 'restart preview
	
	'send a broadcast intent to the media scanner to force it to scan the saved file.
	Dim Phone As Phone
	Dim i As Intent
	i.Initialize("android.intent.action.MEDIA_SCANNER_SCAN_FILE", _
		"file://" & File.Combine(dir, filename))
	Phone.SendBroadcastIntent(i)
	snack.Initialize("",Activity,$"Picture saved. Filename : "$ & filename,snack.DURATION_SHORT)
	SetSnackBarBackground(snack, Colors.White)
	SetSnackBarTextColor(snack, GlobalVar.PriColor)
	snack.Show
End Sub

Sub btnChangeCam_Click
	camEx.Release
	frontCamera = Not(frontCamera)
	InitializeCamera
End Sub

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