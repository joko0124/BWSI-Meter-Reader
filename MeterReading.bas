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

#Region Variable Declarations
Sub Process_Globals
	Dim index As Object
	Private xui As XUI
	Dim EPSONPrint As clsPrint

	Dim BTAdmin As BluetoothAdmin
	Dim PairedDevices As Map

	Dim FoundDevices As List
	Dim DeviceName, DeviceMac As String

	Dim Serial1 As Serial
	Dim TMPrinter As TextWriter

	Dim PrintBuffer As String
	Dim sepBuffer As String
	Dim QRBuffer As String
	Dim GCashRefNo As String
	
	Dim BarCode() As Byte
	Dim byte3 As Byte = 0x4d
	Dim QRCodeNew() As Byte

	Dim PrintLogo() As Byte
	Dim PrintSeptageLogo() As Byte
	Dim oStream As AsyncStreams
	Dim oStream2 As AsyncStreams
	Private Res As Int
	
	Private flagBitmap As Bitmap

	Dim Logobmp As Bitmap
	Dim WoosimCmd As JavaObject
	Dim WoosimImage As JavaObject
	Dim WoosimBarcode As JavaObject
	
	Dim Logo As Bitmap
	Dim SeptageLogo As Bitmap
	Public Permissions As RuntimePermissions
	Dim soundsAlarmChannel As SoundPool
	Private InpTyp As SLInpTypeConst
	
	Private TTS1 As TTS
	Dim TYPE_TEXT_FLAG_NO_SUGGESTIONS  As Int = 0x80000

End Sub

Sub Globals
	Private Panel1 As Panel
	Dim LvEffect As ListView

	Private camEx As CameraExClass
	Private PnlFocus As Panel
	Dim cvsDrawing As Canvas
	Private api As Int
	Dim focuscolor As Double

	Private ToolBar As ACToolBarDark
	Private ActionBarButton As ACActionBar
	Private xmlIcon As XmlLayoutBuilder
	Private PopWarnings As ACPopupMenu
	Private PopSubMenu As ACPopupMenu
	Dim BookCode, BookDesc As String

	Private snack As DSSnackbar
	Dim CD As ColorDrawable

	Private strSF As StringFunctions
	Private IMEkeyboard As IME
	Private WasEdited As Boolean
	Private SV As SearchView

	'Material Dialog
	Private MatDialogBuilder As MaterialDialogBuilder
	
	'Recordset
	Private rsLoadedBook As Cursor

	Private rsUnReadAcct As Cursor
	Private rsReadAcct As Cursor
	Private rsAllAcct As Cursor
	Private rsUnusualReading As Cursor
	
	'////////////////////////////////////////////////////////////
	'UI
	Private pnlMain As Panel
	
	'Account Info
	Private pnlAccountInfo As Panel
	Private lblSeqNo As Label
	Private lblMeter As Label
	Private lblAcctNo As Label
	Private lblAcctName As Label
	Private lblAcctAddress As Label

	'Previous Cons
	Private pnlPrevious As Panel
	Private lblClass As Label
	Private lblPrevCum As Label
	Private lblReadStatus As Label

	'Present Reading & Cons
	Private pnlPresent As Panel
	Private txtPresRdg As EditText
	Private lblPresCum As Label
	Private lblDiscon As Label

	'Findings and Remarks
	Private pnlFindings As Panel
	Private lblFindings As Label
	Private lblMissCode As Label
	Private lblWarning As Label
	Private lblRemarks As Label
	
	'Buttons
	Private pnlButtons As Panel
	Private btnPrevious As ACButton
	Private btnNext As ACButton
	Private btnEdit As ACButton
	Private btnMissCode As ACButton
	Private btnRemarks As ACButton
	Private btnReprint As ACButton

	'Status
	Private pnlStatus As Panel
	Private lblNoAccts As Label
	Private lblUnreadCount As Label
	'////////////////////////////////////////////////////////////
	
	'DataFields
	'Fixed
	Private ReadID As Int
	Private BillNo As String
	Private BookNo As Int
	Private AcctID As Int
	Private AcctNo As String
	Private AcctName As String
	Private AcctAddress As String
	Private AcctClass As String
	Private AcctSubClass As String
	Private AcctClassification As String'Classification
	Private AcctStatus As String
	Private MeterNo As String
	Private MaxReading As Double
	Private SeqNo As String
	Private IsSenior As Int
	Private SeniorOnBefore As Double
	Private SeniorAfter As Double
	Private SeniorMaxCum As Double
	Private GDeposit As Double

	Private PrevRdgDate As String
	Private PrevRdg As Int
	Private PrevCum As String
	
	Private FinalRdg As Int
	Private DisconDate As String

	Private PresRdgDate As String
	Private PresRdg As String
	Private PresCum As String

	Private DateFrom As String
	Private DateTo As String
	Private WithDueDate As Int
	Private DueDate As String
	Private DisconnectionDate As String
	Private AveCum As Double
	Private AddCons As Double
	Private CurrentCuM As Int

	Private TotalCum As Int
	Private BasicAmt As Double
	Private PCA As Double
	Private PCAAmt As Double
	Private AddToBillAmt As Double
	Private AdvPayment As Double
	Private RemainingAdvPayment As Double
	Private PenaltyPct As Double
	Private PenaltyAmt As Double
	Private TotalDueAmtAfterSC As Double

	Private ReadRemarks As String
	Private sAveRem As String
	Private strReadRemarks As String
	Private PaidStatus As Int
	Private PrintStatus As Int
	Private WasMisCoded As Int
	Private MisCodeID As Int
	Private MisCodeDesc As String
	Private WasImplosive As Int
	Private ImplosiveType As String

	Private WasRead As Int
	Private FFindings As String
	Private FWarnings As String
	Private NewSeqNo As Int
	Private NoInput As Int
	Private TimeRead As String
	Private DateRead As String
	Private WasUploaded As Int

	'Variants
	Private BillType As String
	Private CurrentAmt As Double
	Private ArrearsAmt As Double
	Private TotalDueAmt As Double
	Private SeniorOnBeforeAmt As Double
	Private SeniorAfterAmt As Double
	Private TotalDueAmtBeforeSC As Double

	Private intRateHeaderID As Int
	Private DiscAmt, DiscAmt2 As Double
	
	Private MeterCharges As Double
	Private FranchiseTaxPct As Double
	Private FranchiseTaxAmt As Double
	Private SeptageFeeAmt As Double
	Private SeptageFee As Double
	Private SeptageArrears As Double
	Private GTotalSeptage as Double
	Private GTotalDue As Double
	Private GTotalAfterDue As Double
	Private GTotalDueAmt As Double
	Private GTotalAfterDueAmt As Double

	Private NoPrinted As Int
	Private BillPeriod1st As String
	Private PrevCum1st As Int
	Private BillPeriod2nd As String
	Private PrevCum2nd As Int
	Private BillPeriod3rd As String
	Private PrevCum3rd As Int

	Private HasSeptageFee As Int
	Private MinSeptageCum As Int
	Private MaxSeptageCum As Int
	Private SeptageRate As Double


	'Global Variables
	Private blnEdit As Boolean 'Edit button trigger

	Private BookMark = 0 As Int
	Private RecCount = 0 As Int
	Private CountUnReadAcct As Int
	Private intCurrPos As Int
	Private intTempCurrPos As Int

	Private flagBitmap As Bitmap
	Private ZeroConsIcon As Bitmap
	Private MissCodeIcon As Bitmap
	
	'Unusual Readings Status
	Dim badgeMissCode, badgeZero, badgeHigh, badgeLow, badgeTotal As Badger
	Dim intMissCode, intZero, intHigh, intLow, intAve, intUnprinted, intTotal As Int

	'Search Unusual
	Private pnlUnusual As Panel
	Private intSearchFlag As Int
	Private pnlSearchUnusual As Panel
	Private lblUnusualCount As Label
	Private btnCancelUnusual As ACButton

	'Search Options
	Private pnlSearch As Panel
	Private pnlSearchOptions As Panel
	Private pnlSearchBy As Panel
	Private optUnread As RadioButton
	Private optRead As RadioButton
	Private optAll As RadioButton
	Private optSeqNo As RadioButton
	Private optMeterNo As RadioButton
	Private optAcctName As RadioButton
	Private SearchPanel As Panel
	Private SearchFor As String
	Private lblSearchRecCount As Label
	Private btnCancelSearch As ACButton
	
	'Printing
	Private BookID As Int
	Private BookSeq As String
	Private CustClass As String
	Private BillDate As String

	Private sBranchName As String
	Private sBranchAddress As String
	Private sBranchContactNo As String
	Private sTinNo As String
	Private sBranchCode As String
	Private sBillPeriod As String
	Private iHasSeptage As Int
	Private dSeptageRate As Double
		
	'to String
	Private sPresRdg As String
	Private sPrevRdg As String
	Private sAddCons As String
	Private sTotalCum As String

	Private vibration As B4Avibrate
	Private vibratePattern() As Long
	
	Private sBillMonth As String
	Private sBillYear As String
	
	Private PresAveRdg As String
	Private PresAveCum As String
	
	Private sPresentReading As String
	
	Private SoundID As Int

	Private cdTxtBox, cdConfirmRdg As ColorDrawable
	Private cdButtonCancel, cdButtonSaveOnly, cdButtonSaveAndPrint As ColorDrawable

	'Negative Reading Dialog
	Private pnlNegativeMsgBox As Panel
	Private btnNegativeCancelPost As ACButton
	Private btnNegativeSelect As ACButton
	Private opt0 As ACRadioButton
	Private opt1 As ACRadioButton
	Private opt2 As ACRadioButton
	Private opt3 As ACRadioButton
	Private opt4 As ACRadioButton
	Private opt5 As ACRadioButton
	Private opt6 As ACRadioButton
	Private opt7 As ACRadioButton
	Private opt8 As ACRadioButton
	Private txtOthers As EditText

	'High Bill Dialog
	Private pnlHiMsgBox As Panel
	Private btnHiCancelPost As ACButton
	Private btnHiSaveOnly As ACButton
	Private btnHiSaveAndPrint As ACButton

	'High Bill Confirmation Dialog
	Private pnlHighBillConfirmation As Panel
	Private txtPresRdgConfirm As EditText
	Private btnHBConfirmCancel As ACButton
	Private btnHBConfirmSaveAndPrint As ACButton
	Private intSaveOnly As Int

	'Low Bill Dialog
	Private btnLowCancelPost As ACButton
	Private btnLowSaveOnly As ACButton
	Private btnLowSaveAndPrint As ACButton
	Private pnlLowMsgBox As Panel
	
	'Zero Consumption Dialog
	Private pnlZeroMsgBox As Panel
	Private btnZeroCancelPost As ACButton
	Private btnZeroSaveOnly As ACButton
	Private btnZeroSaveAndPrint As ACButton
	Private txtRemarks As EditText
	
	'Normal Reading Dialog
	Private pnlNormalMsgBox As Panel
	Private btnNormalCancelPost As ACButton
	Private btnNormalSaveOnly As ACButton
	Private btnNormalSaveAndPrint As ACButton
	
	'Average Bill Dialog
	Private pnlAveMsgBox As Panel
	Private btnAveTakePicture As ACButton
	Private btnAveCancelPost As ACButton

	'Average Bill - Remarks Dialog
	Private pnlAveRemMsgBox As Panel
	Private btnAveRemSaveAndPrint As ACButton
	Private btnAveRemCancelPost As ACButton
	Private txtReason As EditText
	
	'High Bill Printing Confirmation Dialog
	Private pnlHBConfirmReprint As Panel
	Private txtPresRdgConfirmReprint As EditText
	Private btnHBConfirmReprintCancel As ACButton
	Private btnHBConfirmSaveAndRePrint As ACButton

	'QRCode
	Private QR As QRGenerator
	Private bmpQR As B4XBitmap
	
	Dim T1 As Timer
	Private optReason0 As ACRadioButton
	Private optReason1 As ACRadioButton
	Private optReason2 As ACRadioButton
	Private optReason3 As ACRadioButton
	Private optReason4 As ACRadioButton
	Private optReason5 As ACRadioButton
	Private optReason6 As ACRadioButton
	Private optReason7 As ACRadioButton
	Private optReason8 As ACRadioButton
	
	'Miscode Reading Dialog
	Private pnlNegativeMsgBox As Panel
	Private btnNegativeCancelPost As ACButton
	Private btnNegativeSelect As ACButton
	Private opt0 As ACRadioButton
	Private opt1 As ACRadioButton
	Private opt2 As ACRadioButton
	Private opt3 As ACRadioButton
	Private opt4 As ACRadioButton
	Private opt5 As ACRadioButton
	Private opt6 As ACRadioButton
	Private opt7 As ACRadioButton
	Private opt8 As ACRadioButton
	Private txtOthers As EditText

	Private pnlMisCodeMsgBox As Panel
	Private lblMisCodeMsgTitle As Label
	Private optMisCode0 As ACRadioButton
	Private optMisCode1 As ACRadioButton
	Private optMisCode2 As ACRadioButton
	Private optMisCode3 As ACRadioButton
	Private optMisCode4 As ACRadioButton
	Private optMisCode5 As ACRadioButton
	Private optMisCode6 As ACRadioButton
	Private optMisCode7 As ACRadioButton
	Private optMisCode8 As ACRadioButton
	Private btnMisCodeCancelPost As ACButton
	Private btnMisCodeSave As ACButton
	Private txtMisCode As EditText
	
	Private blnSuperHighBill As Boolean
	Private btnSuperHBCancel As ACButton
	Private btnSuperHBSave As ACButton
	Private pnlSuperHB As Panel
	Private txtSuperHBPresRdg As EditText
	
	Dim initPrinter() As Byte
	Private cKeyboard As CustomKeyboard

End Sub
#End Region

#Region Activity Events
Sub Activity_Create(FirstTime As Boolean)
	MyScale.SetRate(0.5)
	Activity.LoadLayout("mreading2")
	BookCode = DBaseFunctions.GetCodebyID("BookCode", "tblBooks","BookID", GlobalVar.BookID)
	BookDesc = DBaseFunctions.GetCodebyID("BookDesc", "tblBooks","BookID", GlobalVar.BookID)
	
	modVariables.SourceSansProBold.Initialize("sourcesanspro-bold.ttf")
	modVariables.SourceSansProRegular.Initialize("sourcesanspro-regular.ttf")

	GlobalVar.CSTitle.Initialize.Size(18).Bold.Typeface(modVariables.SourceSansProBold.SetCustomFonts).Append($"BOOK - "$ & BookCode).PopAll
	GlobalVar.CSSubTitle.Initialize.Size(13).Append(BookDesc).PopAll
	
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
	
	T1.Initialize("Timer1",100)
	T1.Enabled = False

	If FirstTime Then
		T1.Initialize("Timer1",100)
		T1.Enabled = False
		flagBitmap = LoadBitmap(File.DirAssets, "flag.png")
		QR.Initialize(150)
		intSaveOnly = 0
		blnSuperHighBill = False
	End If

	txtPresRdg.Color = Colors.Black
	txtPresRdg.TextColor = 0xFFFFDB71
	cKeyboard.Initialize("CKB","keyboardview_trans")
	cKeyboard.RegisterEditText(txtPresRdg,"txtPresRdg","num",True)

	SetButtonColors
	ClearDisplay
	If RetrieveRecords(GlobalVar.BookID) = True Then
		rsLoadedBook.Position = 0
		DisplayRecord
		ButtonState
	End If

	
	pnlSearch.Visible = False
	pnlUnusual.Visible = False
	BTAdmin.Initialize("Admin")
	Serial1.Initialize("Printer")
	soundsAlarmChannel.Initialize(2)

	InpTyp.Initialize
	GlobalVar.blnAverage = False
	GlobalVar.isAverage = 0

'	cKeyboard.RegisterEditText(txtPresRdgConfirm,"txtPresRdgConfirm","num",True)
'	cKeyboard.RegisterEditText(txtSuperHBPresRdg,"txtSuperHBPresRdg","num",True)

'	txtPresRdg.InputType = Bit.Or(txtPresRdg.InputType,TYPE_TEXT_FLAG_NO_SUGGESTIONS)
	txtPresRdg.SingleLine = True
	txtPresRdg.ForceDoneButton = True
	txtPresRdg.InputType = txtPresRdg.INPUT_TYPE_NUMBERS

	txtPresRdgConfirm.SingleLine = True
	txtPresRdgConfirm.ForceDoneButton = True
	txtPresRdgConfirm.InputType = txtPresRdgConfirm.INPUT_TYPE_NUMBERS

	txtSuperHBPresRdg.SingleLine = True
	txtSuperHBPresRdg.ForceDoneButton = True
	txtSuperHBPresRdg.InputType = txtSuperHBPresRdg.INPUT_TYPE_NUMBERS
	cKeyboard.SetCustomFilter(txtPresRdg,txtPresRdg.INPUT_TYPE_NUMBERS,"0,1,2,3,4,5,6,7,8,9")

End Sub

Sub Activity_KeyPress (KeyCode As Int) As Boolean 'Return True to consume the event
	If KeyCode = 4 Then
		If cKeyboard.IsSoftKeyboardVisible = True Then
			If GlobalVar.SF.Trim(GlobalVar.SF.Len(txtPresRdg.Text)) > 0 Then
				txtPresRdg.Text = ""
			End If
			cKeyboard.HideKeyboard
			Return True
		End If
		ToolBar_NavigationItemClick
		Return True
	Else
		Return False
	End If
End Sub

Sub Activity_Resume
	SetButtonColors
	pnlSearch.Visible=False
	pnlUnusual.Visible = False
	
	pnlNegativeMsgBox.Visible = False
	pnlHiMsgBox.Visible = False
	pnlHighBillConfirmation.Visible = False
	pnlLowMsgBox.Visible = False
	
	Dim jo As JavaObject
	Dim ph As Phone

	jo.InitializeNewInstance("android.media.SoundPool", Array(4, ph.VOLUME_ALARM, 0)) '4 = max streams
	soundsAlarmChannel = jo
	SoundID = soundsAlarmChannel.Load(File.DirAssets,"beep.wav")
	BTAdmin.Initialize("Admin")
	Serial1.Initialize("Printer")
	vibratePattern = Array As Long(500, 500, 300, 500)
	IMEkeyboard.SetLengthFilter(txtPresRdg, 10)
	txtPresRdg.InputType = txtPresRdg.INPUT_TYPE_NUMBERS

	sBillYear = GlobalVar.BillYear
	If GlobalVar.SF.Len(GlobalVar.BillMonth) = 1 Then
		sBillMonth = "0" & GlobalVar.BillMonth
	Else
		sBillMonth = GlobalVar.BillMonth
	End If
	
	pnlNegativeMsgBox.Visible = False
	InpTyp.SetInputType(txtRemarks,Array As Int(InpTyp.TYPE_CLASS_TEXT, InpTyp.TYPE_TEXT_FLAG_AUTO_CORRECT, InpTyp.TYPE_TEXT_FLAG_CAP_SENTENCES))
	InpTyp.SetInputType(txtOthers,Array As Int(InpTyp.TYPE_CLASS_TEXT, InpTyp.TYPE_TEXT_FLAG_AUTO_CORRECT, InpTyp.TYPE_TEXT_FLAG_CAP_SENTENCES))
	InpTyp.SetInputType(txtReason,Array As Int(InpTyp.TYPE_CLASS_TEXT, InpTyp.TYPE_TEXT_FLAG_AUTO_CORRECT, InpTyp.TYPE_TEXT_FLAG_CAP_SENTENCES))
	
	QR.Initialize(150)
	T1.Initialize("Timer1",100)
	T1.Enabled = False
	LogColor(GlobalVar.isAverage, Colors.Blue)
	LogColor($"Boolean Average: "$ & GlobalVar.blnAverage, Colors.Blue)
	
	If GlobalVar.blnAverage = True And GlobalVar.isAverage = 1 Then ShowAverageBillRemarks
	intSaveOnly = 0
	blnSuperHighBill = False
	
	If TTS1.IsInitialized = False Then
		TTS1.Initialize("TTS1")
	End If
End Sub

Sub Activity_Pause (UserClosed As Boolean)
End Sub

Sub Activity_PermissionResult (Permission As String, Result As Boolean)
	If Permission = Permissions.PERMISSION_CAMERA Then
		LogColor("YOU CAN USE THE CAMERA",Colors.Red)
		snack.Initialize("",Activity, $"Camera is ready to use..."$, snack.DURATION_SHORT)
		SetSnackBarBackground(snack, GlobalVar.PriColor)
		SetSnackBarTextColor(snack, Colors.White)
		snack.Show
	End If
	Log("Permissions OK")

End Sub
#End Region

#Region MainMenu
Sub Activity_CreateMenu(Menu As ACMenu)
	Dim iItem As ACMenuItem

	Menu.Clear
	Menu.Add2(1, 1, $"Search"$, xmlIcon.GetDrawable("ic_find_in_page_white_36dp")).ShowAsAction = iItem.SHOW_AS_ACTION_ALWAYS
	Menu.Add2(2, 2, "Flag", xmlIcon.GetDrawable("ic_flag_white_24dp")).ShowAsAction = iItem.SHOW_AS_ACTION_ALWAYS
	Menu.Add2(3, 3, "SubMenu", xmlIcon.GetDrawable("ic_menu_white_24dp")).ShowAsAction = iItem.SHOW_AS_ACTION_IF_ROOM
	
	flagBitmap = LoadBitmap(File.DirAssets, "flag.png")
	
	If GetUnusualCount(GlobalVar.BookID) = True Then
		UpdateBadge("Flag", AddBadgeToIcon(flagBitmap, intTotal))
	End If
	CreateSubMenus
End Sub

Private Sub CreateSubMenus
	Dim csMissCode, csZero, csHigh, csLow, csAve, csAve2, csPhoto, csCancel, csUnprint As CSBuilder

	PopWarnings.Initialize("PopWarning", ToolBar.GetView(3))
	PopSubMenu.Initialize("PopSubMnu", ToolBar.GetView(3))
			
	
	csMissCode.Initialize.Size(14).Color(Colors.White).Append("Miscoded").PopAll
	csZero.Initialize.Size(14).Color(Colors.White).Append("Zero Cons.").PopAll
	csHigh.Initialize.Size(14).Color(Colors.White).Append("High Bill").PopAll
	csLow.Initialize.Size(14).Color(Colors.White).Append("Low Bill").PopAll
	csAve.Initialize.Size(14).Color(Colors.White).Append("Average Bill").PopAll
	csUnprint.Initialize.Size(14).Color(Colors.White).Append("Unprinted Bills").PopAll
	
	csAve2.Initialize.Size(14).Color(Colors.White).Append("Average Reading").PopAll
	csPhoto.Initialize.Size(14).Color(Colors.White).Append("Take Photo").PopAll
	csCancel.Initialize.Size(14).Color(Colors.White).Append("Cancel Reading").PopAll

	Dim sMenu As Object
	PopWarnings.AddMenuItem(101,csMissCode, xmlIcon.GetDrawable("miss"))
	PopWarnings.AddMenuItem(102, csZero, xmlIcon.GetDrawable("zero"))
	PopWarnings.AddMenuItem(103, csHigh, xmlIcon.GetDrawable("high"))
	PopWarnings.AddMenuItem(104, csLow, xmlIcon.GetDrawable("low"))
	PopWarnings.AddMenuItem(105, csAve, xmlIcon.GetDrawable("ave1"))
	PopWarnings.AddMenuItem(106, csUnprint, xmlIcon.GetDrawable("unprint"))

	PopSubMenu.AddMenuItem(202, csAve2,xmlIcon.GetDrawable("ave2"))
	PopSubMenu.AddMenuItem(201, csPhoto,xmlIcon.GetDrawable("photo"))
	PopSubMenu.AddMenuItem(203, csCancel,xmlIcon.GetDrawable("cancel"))
	
'	sMenu = PopWarnings.GetItem(0)
'	LogColor(sMenu, Colors.Yellow)
End Sub

Sub AddBadgeToIcon(bmp As Bitmap, Number As Int) As Bitmap
	Dim cvs As Canvas
	Dim mbmp As Bitmap
	mbmp.InitializeMutable(32dip, 32dip)
	cvs.Initialize2(mbmp)
	Dim target As Rect
	target.Initialize(0, 0, mbmp.Width, mbmp.Height)
	cvs.DrawBitmap(bmp, Null, target)

	If Number > 0 Then
		cvs.DrawCircle(mbmp.Width - 8dip, 8dip, 8dip, Colors.Red, True, 0)
		cvs.DrawText(Min(Number, 100), mbmp.Width - 8dip, 12dip, Typeface.DEFAULT_BOLD, 12, Colors.White, "CENTER")
	End If
	Return mbmp
End Sub

Sub UpdateBadge(MenuTitle As String, Icon As Bitmap)
	Dim m As ACMenuItem = GetMenuItem(MenuTitle)
	m.Icon = BitmapToBitmapDrawable(Icon)
End Sub

Sub BitmapToBitmapDrawable (bitmap As Bitmap) As BitmapDrawable
	Dim bd As BitmapDrawable
	bd.Initialize(bitmap)
	Return bd
End Sub

Sub GetMenuItem(Title As String) As ACMenuItem
	For i = 0 To ToolBar.Menu.Size - 1
		Dim m As ACMenuItem = ToolBar.Menu.GetItem(i)
		If m.Title = Title Then
			Return m
		End If
	Next
	Return Null
End Sub

Sub ToolBar_NavigationItemClick
cKeyboard.HideKeyboard
'	If cKeyboard.IsSoftKeyboardVisible = True Then
'		cKeyboard.HideKeyboard
	If pnlSearch.Visible = True Then
		btnCancelSearch_Click
		IMEkeyboard.HideKeyboard
	Else If pnlUnusual.Visible = True Then
		btnCancelUnusual_Click
		IMEkeyboard.HideKeyboard
	Else If pnlNegativeMsgBox.Visible = True Then
		btnNegativeCancelPost_Click
	Else If pnlHiMsgBox.Visible = True Then
		btnHiCancelPost_Click
	Else If pnlHighBillConfirmation.Visible = True Then
		btnHBConfirmCancel_Click
	Else If pnlMisCodeMsgBox.Visible = True Then
		btnMisCodeCancelPost_Click
	Else If pnlAveMsgBox.Visible = True Then
		btnAveCancelPost_Click
	Else If blnEdit = True And txtPresRdg.Enabled = True Then
		rsLoadedBook.Position = intCurrPos
		DisplayRecord
		ButtonState
	Else
		IMEkeyboard.HideKeyboard
'		cKeyboard.HideKeyboard
		Activity.Finish
	End If
End Sub

Sub ToolBar_MenuItemClick (Item As ACMenuItem)
	cKeyboard.HideKeyboard
	Select Item.Id
		Case 1
			If pnlSearch.Visible=True Then Return
			intTempCurrPos = rsLoadedBook.Position
			cKeyboard.HideKeyboard
			DisableControls
			SV.Initialize(Me,"SV")
			SV.AddToParent(SearchPanel, 2%x, 0.5%y, SearchPanel.Width - 2%x, SearchPanel.Height - 0.5%y)
			SV.ClearAll
			IMEkeyboard.ShowKeyboard(SV.et)

			pnlSearch.Visible=True
			optUnread.Checked = True
			optSeqNo.Checked=True
			optSeqNo_CheckedChange(True)
		Case 2
			PopWarnings.Show
		Case 3
			PopSubMenu.Show
	End Select
End Sub

Sub PopWarning_ItemClicked (Item As ACMenuItem)
	Log("Popupmenu Item clicked: " & Item.Id & " - " & Item.Title)

	ToolBar.Menu.FindItem(1).Enabled = False
	ToolBar.Menu.FindItem(2).Enabled = False
	ToolBar.Menu.FindItem(3).Enabled = False

	Select Case Item.Id
		Case 101'Misscode
			intSearchFlag = 0
		
		Case 102'Zero
			intSearchFlag = 1
		
		Case 103'High Bill
			intSearchFlag = 2
		
		Case 104 'Low bill
			intSearchFlag = 3

		Case 105 'Average Bill
			intSearchFlag = 4
		
		Case 106 'Unprinted
			intSearchFlag = 5
	End Select

	If pnlUnusual.Visible = True Then Return
	
	cKeyboard.HideKeyboard
	DisableControls
	pnlUnusual.Visible = True
	SV.Initialize(Me,"SV")
	SV.AddToParent(pnlSearchUnusual,2%x, 3.5%y, pnlSearchUnusual.Width - 2%x ,pnlSearchUnusual.Height - 1%y)
	SV.ClearAll
	SearchUnsualReading(intSearchFlag)
End Sub

Sub PopSubMnu_ItemClicked (Item As ACMenuItem)
	Dim sFileName As String
	GlobalVar.strBookSequence = BookNo & "-" & SeqNo
	If cKeyboard.IsSoftKeyboardVisible = True Then cKeyboard.HideKeyboard
	
	Select Case Item.Id
		Case 201'Camera
			GlobalVar.isAverage = 0
			Log(GlobalVar.strBookSequence)
			Permissions.CheckAndRequest(Permissions.PERMISSION_CAMERA)
			StartActivity(NewCam)
			GlobalVar.blnAverage = False
		Case 202'Average
			intTempCurrPos = rsLoadedBook.Position
			If WasRead = 1 Then
				If WasMisCoded = 1 Then
					blnEdit = True
				Else
					vibration.vibratePattern(vibratePattern, 0)
					ShowAveNotAvailableDialog
					Return
				End If
			Else
				If AcctStatus = "dc" Then
					vibration.vibratePattern(vibratePattern, 0)
					ShowAveNotAvailableDialog
					Return					
				End If
			End If

			sFileName = sBillYear & "-" & sBillMonth & "-" & AcctNo & ".jpg"
			LogColor($"File Exist: "$ & File.Exists(File.DirRootExternal, sFileName), Colors.Yellow)
			If File.Exists(File.Combine(File.DirRootExternal,"DCIM/MeterReading"), sFileName) = False Then
				ShowPicRequiredDialog
				Return
			End If
			ShowAverageBillRemarks
			
		Case 203'Cancel
			rsLoadedBook.Position = intCurrPos
			DisplayRecord
			ButtonState
	End Select
	
End Sub

Sub FindViewByTag(Parent As Panel, Name As String) As View
	For Each v As View In Parent.GetAllViewsRecursive
		If Name = v.Tag Then Return v
	Next
	Log("View not found: " & Name)
	Return Null
End Sub


#End Region

#Region UI Display
Sub DisplayRecord()
	'Variables to hold current record
	ReadID = rsLoadedBook.GetInt("ReadID")
	BillNo = rsLoadedBook.GetString("BillNo")
	AcctID = rsLoadedBook.GetInt("AcctID")
	AcctNo = rsLoadedBook.GetString("AcctNo")
	AcctName = rsLoadedBook.GetString("AcctName")
	AcctAddress = rsLoadedBook.GetString("AcctAddress")
	AcctClass = rsLoadedBook.GetString("AcctClass")
	AcctSubClass = rsLoadedBook.GetString("AcctSubClass")
	AcctStatus = rsLoadedBook.GetString("AcctStatus")
	MeterNo = rsLoadedBook.GetString("MeterNo")
	MaxReading = rsLoadedBook.GetDouble("MaxReading")
	BookNo = rsLoadedBook.GetString("BookNo")
	SeqNo = rsLoadedBook.GetString("SeqNo")
	IsSenior = rsLoadedBook.GetInt("IsSenior")
	SeniorOnBefore = rsLoadedBook.GetDouble("SeniorOnBefore")
	SeniorAfter = rsLoadedBook.GetDouble("SeniorAfter")
	SeniorMaxCum = rsLoadedBook.GetDouble("SeniorMaxCum")
	GDeposit = rsLoadedBook.GetDouble("GDeposit")
	PrevRdgDate = rsLoadedBook.GetString("PrevRdgDate")
	PrevRdg = rsLoadedBook.GetInt("PrevRdg")
	PrevCum = rsLoadedBook.GetInt("PrevCum")
	FinalRdg = rsLoadedBook.GetInt("FinalRdg")
	GlobalVar.CamAcctNo = AcctNo
	GlobalVar.CamMeterNo = MeterNo
	Log(PrevRdg & " - " & PrevCum)
	
	PresRdgDate = rsLoadedBook.GetString("PresRdgDate")
	PresRdg = rsLoadedBook.GetString("PresRdg")
	PresCum = rsLoadedBook.GetInt("PresCum")
	DateFrom = rsLoadedBook.GetString("DateFrom")
	DateTo = rsLoadedBook.GetString("DateTo")
	DueDate = rsLoadedBook.GetString("DueDate")
	AveCum = rsLoadedBook.GetLong("AveCum")
	BillType = rsLoadedBook.GetString("BillType")
	AddCons = rsLoadedBook.GetDouble("AddCons")
	TotalCum = rsLoadedBook.GetInt("TotalCum")
	BasicAmt = rsLoadedBook.GetDouble("BasicAmt")
	PCA = rsLoadedBook.GetDouble("PCA")
	AddToBillAmt = rsLoadedBook.GetDouble("AddToBillAmt")
	ArrearsAmt = rsLoadedBook.GetDouble("ArrearsAmt")
	AdvPayment = rsLoadedBook.GetDouble("AdvPayment")
	PenaltyPct = rsLoadedBook.GetDouble("PenaltyPct")
	PenaltyAmt = rsLoadedBook.GetDouble("PenaltyAmt")

	WasRead = rsLoadedBook.GetInt("WasRead")
	WasMisCoded = rsLoadedBook.GetInt("WasMissCoded")
	MisCodeID = rsLoadedBook.GetInt("MissCodeID")
	MisCodeDesc = rsLoadedBook.GetString("MissCode")
	WasImplosive = rsLoadedBook.GetInt("WasImplosive")
	ImplosiveType = rsLoadedBook.GetString("ImplosiveType")

	FFindings = rsLoadedBook.GetString("FFindings")
	FWarnings = rsLoadedBook.GetString("FWarnings")
	ReadRemarks = rsLoadedBook.GetString("ReadRemarks")
	NoInput = rsLoadedBook.GetInt("NoInput")
	WasUploaded = rsLoadedBook.GetInt("WasUploaded")
	PrintStatus = rsLoadedBook.GetInt("PrintStatus")

	MeterCharges = rsLoadedBook.GetDouble("MeterCharges")
	FranchiseTaxPct = rsLoadedBook.GetDouble("FranchiseTaxPct")
'	FranchiseTaxAmt = rsLoadedBook.GetDouble("FranchiseTaxAmt")
	SeptageFeeAmt = rsLoadedBook.GetDouble("SeptageAmt")
	NoPrinted = rsLoadedBook.GetInt("NoPrinted")
	PrevCum1st = rsLoadedBook.GetInt("PrevCum1st")
	PrevCum2nd = rsLoadedBook.GetInt("PrevCum2nd")
	PrevCum3rd = rsLoadedBook.GetInt("PrevCum3rd")
	HasSeptageFee = rsLoadedBook.GetInt("HasSeptageFee")
	WithDueDate = rsLoadedBook.GetInt("WithDueDate")
	MinSeptageCum = rsLoadedBook.GetInt("MinSeptageCum")
	MaxSeptageCum = rsLoadedBook.GetInt("MaxSeptageCum")
	SeptageRate = rsLoadedBook.GetDouble("SeptageRate")
	SeptageArrears = rsLoadedBook.GetDouble("SeptageArrears")

	AcctClassification = AcctClass & "-" & AcctSubClass
	strReadRemarks = ""

	'UI Display
	If strSF.Len(SeqNo) = 1 Then
		lblSeqNo.Text = $"000"$ & SeqNo
	Else If strSF.Len(SeqNo) = 2 Then
		lblSeqNo.Text = $"00"$ & SeqNo
	Else If strSF.Len(SeqNo) = 3 Then
		lblSeqNo.Text = $"0"$ & SeqNo
	Else If strSF.Len(SeqNo) >= 4 Then
		lblSeqNo.Text = SeqNo
	End If
	
	lblMeter.Text = MeterNo
	lblAcctNo.Text = AcctNo
	lblAcctName.Text = GlobalVar.SF.Upper(AcctName)
	lblAcctAddress.Text = TitleCase(AcctAddress)
	lblClass.Text = AcctClassification
	lblPrevCum.Text = PrevCum

	lblFindings.Text = FFindings
	lblMissCode.Text = MisCodeDesc
	lblWarning.Text = FWarnings

'	Log(PresRdg)

	If WasRead = 0 And (PresRdg = Null Or PresRdg = "") Then
'	If WasRead = 0 Then
		btnEdit.Enabled = False
		btnRemarks.Enabled = False
		btnReprint.Enabled = False
		lblPresCum.Text = ""
		txtPresRdg.Text = ""
		txtPresRdg.Enabled = True
		txtPresRdg.RequestFocus
		cKeyboard.SetCustomFilter(txtPresRdg, txtPresRdg.INPUT_TYPE_NUMBERS,"1234567890")
		cKeyboard.ShowKeyboard(txtPresRdg)
		txtPresRdg.InputType = txtPresRdg.INPUT_TYPE_NUMBERS
		T1.Enabled = False
		lblReadStatus.Visible = True
		lblReadStatus.Text = "UNREAD"
		lblReadStatus.TextColor = Colors.Red
	Else If WasRead = 1 And WasMisCoded = 1 Then
'		T1.Enabled = True
		lblReadStatus.Text = "MISCODED"
		lblReadStatus.TextColor = Colors.Gray
		btnEdit.Enabled = True
		btnReprint.Enabled = False
		btnRemarks.Enabled = True
		txtPresRdg.Text = PresRdg
		txtPresRdg.Enabled = False
		lblPresCum.Text = PresCum
		IMEkeyboard.HideKeyboard
		cKeyboard.HideKeyboard
	Else If BillType = "AB" Then
		lblReadStatus.Visible = True
		lblReadStatus.Text = "AVG. BILL"
		lblReadStatus.TextColor = 0xFFF9A863
		btnEdit.Enabled = True
		btnReprint.Enabled = True
		btnRemarks.Enabled = True
		txtPresRdg.Text = PresRdg
'		txtPresRdg.Enabled = True
		txtPresRdg.Enabled = False
		lblPresCum.Text = PresCum
		IMEkeyboard.HideKeyboard
		cKeyboard.HideKeyboard
	Else
		T1.Enabled = False
		lblReadStatus.Visible = True
		lblReadStatus.Text = "READ"
		lblReadStatus.TextColor = 0xFF14C0DB
		btnEdit.Enabled = True
		btnReprint.Enabled = True
		btnRemarks.Enabled = True
		txtPresRdg.Text = PresRdg
'		txtPresRdg.Enabled = True
		txtPresRdg.Enabled = False
		lblPresCum.Text = PresCum
		IMEkeyboard.HideKeyboard
		cKeyboard.HideKeyboard
	End If
	
	If WasMisCoded = 1 Then
		lblMissCode.Text = MisCodeDesc
		lblMissCode.TextColor = Colors.Red
		lblFindings.TextColor = Colors.Red
	Else
		lblMissCode.Text = "N/A"
		lblMissCode.TextColor = 0xFF3A3A3A
		lblFindings.TextColor = 0xFF3A3A3A
	End If
	
	If FFindings.Length > 0 Then
		lblFindings.Text = FFindings
	Else
		lblFindings.Text = "Actual Reading"
	End If

	If FWarnings.Length > 0  Then
		If FWarnings<>"NONE" Then
			lblWarning.Text = FWarnings
			lblWarning.TextColor = Colors.Red
		Else
			lblWarning.TextColor = 0xFF3A3A3A
		End If
	Else
		lblWarning.Text = "N/A"
		lblWarning.TextColor = 0xFF3A3A3A
	End If
	
	LogColor(ReadRemarks, Colors.Yellow)
	If GlobalVar.SF.Len(GlobalVar.SF.Trim(ReadRemarks)) > 0 Then
		lblRemarks.Text = ReadRemarks
	Else
		lblRemarks.Text = "N/A"
	End If

	'//////////////////////////////////////////////////////////////////////////////////////////////////////
	If AcctStatus = "dc" Then
		txtPresRdg.Text = FinalRdg
		txtPresRdg.RequestFocus
		txtPresRdg.Enabled = True
'		txtPresRdg.SelectionStart=0
		txtPresRdg.SelectAll
		cKeyboard.ShowKeyboard(txtPresRdg)
		cKeyboard.SetCustomFilter(txtPresRdg, txtPresRdg.INPUT_TYPE_NUMBERS,"")
		lblDiscon.Visible = True
	Else
		lblDiscon.Visible = False
	End If
	'//////////////////////////////////////////////////////////////////////////////////////////////////////

	intCurrPos = rsLoadedBook.Position
	BookMark = rsLoadedBook.Position + 1
	CountUnReadAcct = CountUnread(GlobalVar.BookID)
	
	lblNoAccts.Text = $"Record "$ & BookMark & $" of "$ & RecCount
	lblUnreadCount.Text = $"Unread Acct(s). : "$ & CountUnReadAcct
End Sub

Sub ClearDisplay
	lblSeqNo.Text = ""
	lblMeter.Text = ""
	lblAcctNo.Text = ""
	lblAcctName.Text = ""
	lblAcctAddress.Text = ""
	lblClass.Text = ""
	lblPrevCum.Text = ""
	lblFindings.Text = ""
	lblMissCode.Text = ""
	lblWarning.Text = ""

	BookMark = 0
'	pnlSearch.Visible=False
	
	btnPrevious.Enabled=False
	btnNext.Enabled=False
	btnMissCode.Enabled=False
	btnRemarks.Enabled=False
	btnEdit.Enabled=False
	btnReprint.Enabled=False
	btnReprint.Text = "Print"
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

Private Sub SetButtonColors()
'	0xFF1976D2
	btnPrevious.Background = CreateButtonColor(0xFF0D47A1, 0xFF1976D2,0xFF1E88E5, 0xFF0D47A1)
	btnNext.Background = CreateButtonColor(0xFF0D47A1, 0xFF1976D2,0xFF1E88E5, 0xFF0D47A1)
	btnEdit.Background = CreateButtonColor(0xFF0D47A1, 0xFF1976D2,0xFF1E88E5, 0xFF0D47A1)
	btnMissCode.Background = CreateButtonColor(0xFF0D47A1, 0xFF1976D2,0xFF1E88E5, 0xFF0D47A1)
	btnRemarks.Background = CreateButtonColor(0xFF0D47A1, 0xFF1976D2,0xFF1E88E5, 0xFF0D47A1)
	btnReprint.Background = CreateButtonColor(0xFF0D47A1, 0xFF1976D2,0xFF1E88E5, 0xFF0D47A1)
	btnCancelSearch.Background = CreateButtonColor(0xFF0D47A1, 0xFF1976D2,0xFF1E88E5, 0xFF0D47A1)
	btnCancelUnusual.Background = CreateButtonColor(0xFF0D47A1, 0xFF1976D2,0xFF1E88E5, 0xFF0D47A1)
End Sub

Sub DisableControls
	pnlMain.Enabled = False
	ToolBar.Enabled = False
	pnlAccountInfo.Enabled = False
	pnlPrevious.Enabled = False
	pnlPresent.Enabled = False
	pnlFindings.Enabled = False
	pnlButtons.Enabled = False
	pnlStatus.Enabled = False
	
	txtPresRdg.Enabled = False
	btnPrevious.Enabled = False
	btnNext.Enabled = False
	btnEdit.Enabled = False
	btnMissCode.Enabled = False
	btnRemarks.Enabled = False
	btnReprint.Enabled = False

	ToolBar.Menu.FindItem(1).Enabled = False
	ToolBar.Menu.FindItem(2).Enabled = False
	ToolBar.Menu.FindItem(3).Enabled = False
End Sub

Sub EnableControls
	pnlMain.Enabled = True
	ToolBar.Enabled = True
	pnlAccountInfo.Enabled = True
	pnlPrevious.Enabled = True
	pnlPresent.Enabled = True
	pnlFindings.Enabled = True
	pnlButtons.Enabled = True
	pnlStatus.Enabled = True

	ToolBar.Menu.FindItem(1).Enabled = True
	ToolBar.Menu.FindItem(2).Enabled = True
	ToolBar.Menu.FindItem(3).Enabled = True
End Sub

Sub ButtonState
	If RecCount > 0 Then
		If rsLoadedBook.Position <= 0 Then
			btnPrevious.Enabled=False
			btnNext.Enabled=True
		Else if rsLoadedBook.Position = RecCount - 1 Then
			btnPrevious.Enabled=True
			btnNext.Enabled=False
		Else
			btnPrevious.Enabled=True
			btnNext.Enabled=True
		End If
	Else
		ClearDisplay
		Return
	End If
	
	If PrintStatus = 0 Then
		btnReprint.Text = "PRINT"
	Else
		btnReprint.Text = "REPRINT"
	End If
	
	If WasMisCoded = 1 Then
		btnMissCode.Text = $"CHANGE MISCODE"$
	Else
		btnMissCode.Text = "ADD MISCODE"
	End If
	
	If ReadRemarks = Null Or ReadRemarks.Length <= 0 Then
		btnRemarks.Text="ADD REMARKS"
	Else
		btnRemarks.Text="CHANGE REMARKS"
	End If
	
	If WasRead = 0 Then
		If AcctStatus = "dc" Then
			btnMissCode.Enabled = False
			btnRemarks.Enabled = True
		Else
			btnMissCode.Enabled = True
			btnRemarks.Enabled = False
		End If
		btnEdit.Enabled = False
		btnReprint.Enabled = False
	Else
		
		If WasMisCoded = 1 Then
			btnReprint.Enabled = False
			btnEdit.Enabled = True
			btnRemarks.Enabled = True
			btnMissCode.Enabled = True
			Return
		End If
		
		If PrintStatus = 0 Then
			btnEdit.Enabled = True
			btnMissCode.Enabled = False
			btnRemarks.Enabled = True
		Else
			btnEdit.Enabled = False
			btnMissCode.Enabled = False
			btnRemarks.Enabled = False
		End If
		If AcctStatus = "dc" Then
			btnMissCode.Enabled = False
		End If
	End If
		
	If WasUploaded = 1 Then
		btnEdit.Enabled = False
		btnMissCode.Enabled = False
		btnRemarks.Enabled = False
		btnReprint.Enabled = True
	End If
	
End Sub
#End Region

#Region DataBase
Sub RetrieveRecords(iBookID As Int) As Boolean
	Dim blnRet As Boolean
	Try
		Starter.strCriteria="SELECT * FROM tblReadings " & _
						"WHERE BookID = " & iBookID & " " & _
						"AND BillYear = " & GlobalVar.BillYear & " " & _
						"AND BillMonth = " & GlobalVar.BillMonth & " " & _
						"AND ReadBy = " & GlobalVar.UserID & " " & _
						"AND BranchID = " & GlobalVar.BranchID & " " & _
						"ORDER BY (Case WHEN NewSeqNo Is Null Then SeqNo Else NewSeqNo End), SeqNo Asc, TimeRead Asc, DateRead Asc, AcctID"
		Log(Starter.strCriteria)
		
		rsLoadedBook = Starter.DBCon.ExecQuery(Starter.strCriteria)
		
		If rsLoadedBook.RowCount > 0 Then
			RecCount = rsLoadedBook.RowCount
			blnRet = True
		Else
			blnRet = False
		End If
	Catch
		blnRet = False
		Log(LastException)
		ToastMessageShow("Unable to retreive records due to " & LastException.Message & "!",False)
	End Try
	Return blnRet
End Sub

Private Sub CountUnread(iBookID As Int) As Int
	Dim iCount As Int
	Try
		Starter.strCriteria="SELECT SUM(CASE WHEN tblReadings.WasRead = 0 Then 1 Else 0 End) As UnreadAccts FROM tblReadings " & _
							"WHERE BookID=" & iBookID & " AND BillYear= " & GlobalVar.BillYear & " AND BillMonth=" & GlobalVar.BillMonth
		Log(Starter.strCriteria)

		iCount = Starter.DBCon.ExecQuerySingleResult(Starter.strCriteria)
		Log(iCount)
	Catch
		iCount = 0
		ToastMessageShow("Unable to COUNT unread Account(s) due to " & LastException.Message, False)
		Log(LastException.Message)
	End Try

	Return iCount
End Sub

Private Sub GetUnusualCount(iBookID As Int) As Boolean
	Dim blnRetVal As Boolean
	Try
		Starter.strCriteria = "SELECT SUM(CASE WHEN WasMissCoded = 1 THEN 1 ELSE 0 END) as MissCoded, " & _
						  "SUM(CASE WHEN ImplosiveType = 'zero' Then 1 ELSE 0 END) As ZeroCons, " & _
						  "SUM(CASE WHEN ImplosiveType = 'implosive-inc' Then 1 ELSE 0 END) As HighBill, " & _
						  "SUM(CASE WHEN ImplosiveType = 'implosive-dec' Then 1 ELSE 0 END) As LowBill, " & _
						  "SUM(CASE WHEN BillType = 'AB' Then 1 ELSE 0 END) As AveBill, " & _
						  "SUM(CASE WHEN PrintStatus = 0 Then 1 ELSE 0 END) As Unprinted " & _
						  "FROM tblReadings " & _
						  "WHERE BookID = " & iBookID & " " & _
						  "AND BillYear = " & GlobalVar.BillYear & " " & _
						  "AND BillMonth = " & GlobalVar.BillMonth & " " & _
						  "AND WasRead = 1 " & _
						  "AND ReadBy = " & GlobalVar.UserID
		rsUnusualReading = Starter.DBCon.ExecQuery(Starter.strCriteria)
		If rsUnusualReading.RowCount > 0 Then
			rsUnusualReading.Position = 0
			blnRetVal = True
			intMissCode = rsUnusualReading.GetInt("MissCoded")
			intZero = rsUnusualReading.GetInt("ZeroCons")
			intHigh = rsUnusualReading.GetInt("HighBill")
			intLow = rsUnusualReading.GetInt("LowBill")
			intAve = rsUnusualReading.GetInt("AveBill")
			intUnprinted = rsUnusualReading.GetInt("Unprinted")
			intTotal = intMissCode + intZero + intHigh + intLow + intAve + intUnprinted
		Else
			blnRetVal = False
		End If
		rsUnusualReading.Close
		Return blnRetVal
	Catch
		blnRetVal = False
		Log(LastException)
	End Try
End Sub

Private Sub IsPrintableData(iReadID As Int) As Boolean
	Dim blnRetVal As Boolean
	Dim rsValidData As Cursor
	
	Try
		Starter.strCriteria = "SELECT * FROM tblReadings WHERE ReadID = " & iReadID & " " & _
							  "AND TotalCum >= 0 " & _
							  "AND PresCum >= 0 " & _
							  "AND PresRdg <> '" & "" & "' " & _
							  "AND WasRead = 1 " & _
							  "AND WasMissCoded = 0"
		rsValidData = Starter.DBCon.ExecQuery(Starter.strCriteria)
		
		If rsValidData.RowCount > 0 Then
			blnRetVal = True
		Else
			blnRetVal = False
		End If
	Catch
		blnRetVal = False
		rsValidData.Close
		Log(LastException)
	End Try
	
	rsValidData.Close
	Return blnRetVal
End Sub

#End Region

#Region Present Reading
Sub txtPresRdg_TextChanged (Old As String, New As String)
	If strSF.Len(txtPresRdg.Text.Trim) <= 0 Then
		lblPresCum.Text = ""
		Return
	End If
	lblPresCum.Text = ComputeCumUsed

'	If Old = New Then
'		WasEdited = False
'	Else
'		WasEdited = True
'	End If
End Sub

Private Sub txtPresRdg_HandleAction() As Boolean
	Dim iTotalCuM As Int
	Dim dRatesHeaderID As Double
	
	'save the cursor position
	intCurrPos = rsLoadedBook.Position
	
	'Test the length of the reading
	If AcctStatus = "dc" Then
		txtPresRdg.Text = FinalRdg
	End If
	
	LogColor($"Pres Rdg: "$ & txtPresRdg.Text, Colors.Magenta)

	cKeyboard.HideKeyboard

	If strSF.Len(strSF.Trim(txtPresRdg.Text)) <= 0 Then
		Return False
	End If
	
	'Test if the inputted was a valid numerical format
	If IsNumber(txtPresRdg.Text) = False Then
		Return False
	End If
	
	
	'Compute Current CuM
	CurrentCuM = strSF.Val(txtPresRdg.Text) - strSF.Val(PrevRdg)
	LogColor($"Prev Rdg: "$ & PrevRdg, Colors.Magenta)
	LogColor($"Current CuM: "$ & CurrentCuM, Colors.Cyan)
	iTotalCuM = CurrentCuM + AddCons
	LogColor($"Total CuM: "$ & iTotalCuM, Colors.Magenta)
	
	'Test the Rate Header existence
	If Not(DBaseFunctions.HasRateHeader(GlobalVar.BranchID, AcctClass, AcctSubClass)) Then
		vibration.vibratePattern(vibratePattern, 0)
		txtPresRdg.Text = ""
		txtPresRdg.Enabled = False
		WarningMsg($"NO RATES FOUND!"$, $"Customer account classification has no rates found!"$ & CRLF & $"Please ask assistance to the Central Billing & IT Department regarding this."$)
		Return True
	Else
		'Get the Rates Header ID
		dRatesHeaderID = DBaseFunctions.GetRatesHeaderID(AcctClass, AcctSubClass)
				
		'Test the Rates Details from Rates Header ID
		If Not(DBaseFunctions.HasRateDetails(dRatesHeaderID)) Then
			vibration.vibratePattern(vibratePattern, 0)
			WarningMsg($"NO RATES FOUND!"$, $"Customer account classification has no rates found!"$ & CRLF & $"Please ask assistance to the Central Billing & IT Department regarding this."$)
			txtPresRdg.Text = ""
			txtPresRdg.Enabled = False
			Return True
		End If
	End If
	
	If HasSeptageFee = 1 Then
		LogColor ($"Acct Classification: "$ & AcctClassification, Colors.Magenta)
		GlobalVar.SeptageRateID = DBaseFunctions.GetSeptageRateID(AcctClassification, GlobalVar.BranchID)
		LogColor ($"Septage Rate ID: "$ & GlobalVar.SeptageRateID, Colors.Cyan)
		
		If GlobalVar.SeptageRateID = 0 Then
			WarningMsg($"NO SEPTAGE RATES FOUND!"$, $"Customer account classification has no septage rates found!"$ & CRLF & $"Please ask assistance to the Central Billing & IT Department regarding this."$)
			Return True
		End If
	End If

	If CurrentCuM < 0 Or strSF.Val(txtPresRdg.Text) < strSF.Val(PrevRdg) Then 'Negative Reading
		vibration.vibratePattern(vibratePattern, 0)
		WasImplosive = 1
		ShowNegativeReadingDialog
		Return True
	
	Else If CurrentCuM = 0 Or strSF.Val(txtPresRdg.Text) = strSF.Val(PrevRdg) Then 'Zero Consumption
		vibration.vibratePattern(vibratePattern, 0)
		WasImplosive = 1
		ShowSaveZeroReadingDialog
		Return True
	
	Else If (iTotalCuM - PrevCum) >= 20 Then 'High Bill
		If DBaseFunctions.IsWithinRange(CurrentCuM, GlobalVar.BranchID, AcctClass, AcctSubClass) = False Then
			vibration.vibratePattern(vibratePattern, 0)
			WarningMsg($"E R R O R!"$, $"Reading input Out of Range!"$)
			txtPresRdg.SelectAll
'			txtPresRdg.Enabled = False
		Return True
		End If
		
		If (iTotalCuM - PrevCum) >= 100 Then
			blnSuperHighBill = True
		Else
			blnSuperHighBill = False
		End If
		
		vibration.vibratePattern(vibratePattern, 0)
		WasImplosive = 1
		ShowHighBillDialog
		LogColor($"Total CuM: "$ & (iTotalCuM - PrevCum), Colors.Cyan)
		Return True
	
	Else If (PrevCum - iTotalCuM) >= 20 Then 'Low Bill
		vibration.vibratePattern(vibratePattern, 0)
		WasImplosive = 1
		ShowLowBillDialog
		Return True
	Else 'Normal Reading
		WasImplosive = 0
		ShowAddNewReading
		Return True
	End If
End Sub

Sub txtPresRdg_EnterPressed
'	Dim Currcum As Double
'	Dim dHeaderID As Double
'	
'	intCurrPos = rsLoadedBook.Position
'	Currcum = 0
'	Currcum = strSF.Val(txtPresRdg.Text) - PrevRdg
'	
'	cKeyboard.HideKeyboard
'
'	If AcctStatus = "dc" Then
'		Log("Enter Pressed")
'	End If
'	
'	Test for existence of rates
'	If strSF.Len(txtPresRdg.Text.Trim) <= 0 Or IsNumber(txtPresRdg.Text) = False Then Return
'	
'	If Not(DBaseFunctions.HasRateHeader(GlobalVar.BranchID, AcctClass, AcctSubClass, GlobalVar.BillYear & "-" & GlobalVar.BillMonth & "-01")) Then
'	If Not(DBaseFunctions.HasRateHeader(GlobalVar.BranchID, AcctClass, AcctSubClass)) Then
'		vibration.vibratePattern(vibratePattern, 0)
'		If  DispErrorMsg($"Customer account classification has no rates found!"$ & CRLF & $"Please ask assistance to the Central Billing & IT Department regarding this."$, $"NO RATES FOUND!"$) = True Then
'			vibration.vibrateCancel
'			txtPresRdg.Text = ""
'			txtPresRdg.Enabled = False
'			Return
'		End If
'	Else
'		dHeaderID = DBaseFunctions.GetRatesHeaderID(AcctClass, AcctSubClass)
'		If Not(DBaseFunctions.HasRateDetails(dHeaderID)) Then
'			vibration.vibratePattern(vibratePattern, 0)
'			If  DispErrorMsg($"Customer account classification has no rates found!"$ & CRLF & $"Please ask assistance to the Central Billing & IT Department regarding this."$, $"NO RATES FOUND!"$) = True Then
'				vibration.vibrateCancel
'				txtPresRdg.Text = ""
'				txtPresRdg.Enabled = False
'				Return
'			End If
'		End If
'	End If
'	
'	If blnEdit = False Then ' New Readings
'		If strSF.Len(txtPresRdg.Text.Trim) <= 0 Or IsNumber(txtPresRdg.Text) = False Then Return
'		
'		If strSF.Val(lblPresCum.Text) < 0 Or strSF.Val(txtPresRdg.Text) < PrevRdg Then 'Negative Consumption
'			vibration.vibratePattern(vibratePattern, 0)
'			WasImplosive = 1
'			ShowNegativeReadingDialog
'		Else If strSF.Val(lblPresCum.Text) = 0 Or strSF.Val(txtPresRdg.Text) = PrevRdg Then 'Zero Consumption
'			vibration.vibratePattern(vibratePattern, 0)
'			WasImplosive = 1
'			ShowSaveZeroReadingDialog
'		Else If ((strSF.Val(lblPresCum.Text) + AddCons) - PrevCum) >= 20 Then 'High Bill
'			If DBaseFunctions.IsWithinRange(Currcum, GlobalVar.BranchID, AcctClass, AcctSubClass) = False Then
'				vibration.vibratePattern(vibratePattern, 0)
'				If  DispErrorMsg($"Inputted Reading Out of Range!"$, $"ERROR INPUT"$) = True Then
'					vibration.vibrateCancel
'					txtPresRdg.SelectionStart = 1
'					txtPresRdg.SelectAll
'					IMEkeyboard.ShowKeyboard(txtPresRdg)
'				End If
'				Return
'			End If
'			
'			If ((strSF.Val(lblPresCum.Text) + AddCons) - PrevCum) >= 100 Then
'				blnSuperHighBill = True
'			Else
'				blnSuperHighBill = False
'			End If
'			
'			vibration.vibratePattern(vibratePattern, 0)
'			WasImplosive = 1
'			ShowHighBillDialog
'			Log((strSF.Val(lblPresCum.Text) + AddCons) - PrevCum)
'		Else If (PrevCum - (strSF.Val(lblPresCum.Text) + AddCons)) >= 20 Then 'Low Bill
'			vibration.vibratePattern(vibratePattern, 0)
'			WasImplosive = 1
'			ShowLowBillDialog
'		Else 'Normal Reading
'			WasImplosive = 0
'			ShowAddNewReading
'		End If
'		Dim sImplosive As String
'		Dim iImplosive As Int
'		iImplosive = CheckImplosive(PrevCum, strSF.Val(lblPresCum.Text))
'		If iImplosive = 0 Then
'			sImplosive = "Normal Bill"
'		Else If iImplosive = 1 Then
'			sImplosive = "Low Bill"
'		Else If iImplosive = 2 Then
'			sImplosive = "High Bill"
'		End If
'		LogColor(sImplosive,Colors.Yellow)
'	Else 'Edit Reading
'		If strSF.Len(txtPresRdg.Text.Trim) <= 0 Or IsNumber(txtPresRdg.Text) = False Then Return
'		If strSF.Val(lblPresCum.Text) < 0 Or strSF.Val(txtPresRdg.Text) < PrevRdg Then 'Negative Consumption
'			vibration.vibratePattern(vibratePattern, 0)
'			WasImplosive = 1
'			ShowNegativeReadingDialog
'		Else If strSF.Val(lblPresCum.Text) = 0 Or strSF.Val(txtPresRdg.Text) = PrevRdg Then 'Zero Consumption
'			vibration.vibratePattern(vibratePattern, 0)
'			WasImplosive = 1
'			ShowSaveZeroReadingDialog
'		Else If ((strSF.Val(lblPresCum.Text) + AddCons) - PrevCum) >= 20 Then 'High Bill
'			If DBaseFunctions.IsWithinRange(Currcum, GlobalVar.BranchID, AcctClass, AcctSubClass) = False Then
'				vibration.vibratePattern(vibratePattern, 0)
'				If  DispErrorMsg($"Inputted Reading Out of Range!"$, $"ERROR INPUT"$) = True Then
'					vibration.vibrateCancel
'					txtPresRdg.SelectionStart = 1
'					txtPresRdg.SelectAll
'					IMEkeyboard.ShowKeyboard(txtPresRdg)
'				End If
'				Return
'			End If
'			If ((strSF.Val(lblPresCum.Text) + AddCons) - PrevCum) >= 100 Then
'				blnSuperHighBill = True
'			Else
'				blnSuperHighBill = False
'			End If
'			
'			Log((strSF.Val(lblPresCum.Text) + AddCons) - PrevCum)
'			vibration.vibratePattern(vibratePattern, 0)
'			WasImplosive = 1
'			ShowHighBillDialog
'		Else If (PrevCum - (strSF.Val(lblPresCum.Text) + AddCons)) >= 20 Then 'Low Bill
'			vibration.vibratePattern(vibratePattern, 0)
'			WasImplosive = 1
'			ShowLowBillDialog
'		Else 'Normal Reading
'			WasImplosive = 0
'			ShowAddNewReading
'		End If
'	End If
End Sub

Sub txtPresRdg_FocusChanged (HasFocus As Boolean)
	If HasFocus = True Then
'		cKeyboard.ShowKeyboard(txtPresRdg)
		WasEdited = False
'		If AcctStatus = "dc" Then
'			txtPresRdg.Enabled = True
'		End If
	Else
		cKeyboard.HideKeyboard
	End If
	
End Sub

#End Region

#Region Button Actions
Sub btnReprint_Click
	If IsPrintableData(ReadID) = False Then
		snack.Initialize("", Activity,$"No data to Reprint!"$, snack.DURATION_LONG)
		SetSnackBarBackground(snack, Colors.Red)
		SetSnackBarTextColor(snack, Colors.White)
		snack.Show
		Return
	End If

	vibration.vibrateOnce(1000)
	If PrintStatus = 1 Then
		snack.Initialize("BillReprint", Activity, $"Reprint Bill Statement for this Account?"$, snack.DURATION_LONG)
		SetSnackBarTextColor(snack, Colors.Red)
		SetSnackBarBackground(snack, Colors.White)
		snack.SetAction("YES")
		snack.Show
	Else
'		If ImplosiveType = "implosive-inc" Then
'			ShowHighBillDialog
'			Return
'		End If
		snack.Initialize("BillReprint", Activity, $"Print Bill Statement for this Account?"$, snack.DURATION_LONG)
		SetSnackBarTextColor(snack, Colors.Red)
		SetSnackBarBackground(snack, Colors.White)
		snack.SetAction("YES")
		snack.Show
	End If
End Sub

Private Sub BillReprint_Click()
	ProgressDialogShow2($"Bill Statement Printing..."$, False)
	UpdatePrintStatus(ReadID, AcctID)
	PrintBillData(ReadID)
	If RetrieveRecords(GlobalVar.BookID) = True Then
		rsLoadedBook.Position = intCurrPos
		DisplayRecord
		ButtonState
	End If
End Sub

Sub btnRemarks_Click
	Select Case btnRemarks.Text
		Case "ADD REMARKS"
			ShowAddRemarksDialog
		Case "CHANGE REMARKS"
			blnEdit = True
			ShowEditRemarksDialog
	End Select
End Sub

Sub btnPrevious_Click
	If rsLoadedBook.Position<=0 Then Return
	rsLoadedBook.Position = rsLoadedBook.Position - 1
	If BookMark = 0 Then
		btnPrevious.Enabled=False
		Return
	End If
	btnNext.Enabled=True
	DisplayRecord
	ButtonState
	Log($"Record Position: "$ & rsLoadedBook.Position)
End Sub

Sub btnNext_Click
	If rsLoadedBook.Position = (RecCount - 1) Then Return
	
	rsLoadedBook.Position = rsLoadedBook.Position + 1
	If BookMark = RecCount Then
		btnNext.Enabled=False
		Return
	End If

	btnPrevious.Enabled=True
	DisplayRecord
	ButtonState
	Log($"Record Position: "$ & rsLoadedBook.Position)
End Sub

Sub btnMissCode_Click
	intCurrPos = rsLoadedBook.Position
	Log(btnMissCode.Text)
	If AcctStatus = "dc" Then
		DispErrorMsg($"NOT ALLOWED!"$, $"Miscoding not allowed for disconnected account."$)
		Return
	End If
	Select Case btnMissCode.Text
		Case "CHANGE MISCODE"
			blnEdit = True
			ShowEditMissCodeDialog
		Case "ADD MISCODE"
			ShowAddMissCodeDialog
	End Select
End Sub

Sub btnEdit_Click
	If WasRead = 0 Then Return
	If WasMisCoded = 1 Then
		ShowEditDialog
		Return
	End If
	blnEdit = True
	txtPresRdg.Enabled=True
	txtPresRdg.RequestFocus
	txtPresRdg.SelectionStart=0
	txtPresRdg.SelectAll
'	IMEkeyboard.ShowKeyboard(txtPresRdg)
	btnEdit.Enabled = False
	btnReprint.Enabled = False
	btnPrevious.Enabled = False
	btnNext.Enabled = False
End Sub
#End Region

#Region Search Records

Sub optSeqNo_CheckedChange(Checked As Boolean)
	If Checked=True Then
		If optUnread.Checked = True Then
			SearchAccount(0, 0)
		Else If optRead.Checked = True Then
			SearchAccount(0, 1)
		Else
			SearchAccount(0, 2)
		End If
	End If
End Sub

Sub optMeterNo_CheckedChange(Checked As Boolean)
	If Checked=True Then
		If optUnread.Checked = True Then
			SearchAccount(1, 0)
		Else If optRead.Checked = True Then
			SearchAccount(1, 1)
		Else
			SearchAccount(1, 2)
		End If
	End If
End Sub

Sub optAcctName_CheckedChange(Checked As Boolean)
	If Checked=True Then
		If optUnread.Checked = True Then
			SearchAccount(2, 0)
		Else If optRead.Checked = True Then
			SearchAccount(2, 1)
		Else
			SearchAccount(2, 2)
		End If
	End If
End Sub

Sub optUnread_CheckedChange(Checked As Boolean)
	If Checked=True Then
		If optSeqNo.Checked = True Then
			SearchAccount(0, 0)
		Else If optMeterNo.Checked = True Then
			SearchAccount(1, 0)
		Else
			SearchAccount(2, 0)
		End If
	End If
End Sub

Sub optRead_CheckedChange(Checked As Boolean)
	If Checked=True Then
		If optSeqNo.Checked = True Then
			SearchAccount(0, 1)
		Else If optMeterNo.Checked = True Then
			SearchAccount(1, 1)
		Else
			SearchAccount(2, 1)
		End If
	End If
End Sub

Sub optAll_CheckedChange(Checked As Boolean)
	If Checked=True Then
		If optSeqNo.Checked = True Then
			SearchAccount(0, 2)
		Else If optMeterNo.Checked = True Then
			SearchAccount(1, 2)
		Else
			SearchAccount(2, 2)
		End If
	End If

End Sub

Sub SV_ItemClick(Value As String)
	Log(Value)
	SearchFor = Value
	FindSearchRetValue(SearchFor)
	SV.ClearAll
	SearchClosed
End Sub

Private Sub SearchAccount(iSearchBy As Int, iFilterBy As Int)
	Dim sField1, sField2 As String
	Dim SearchList As List

	SearchList.Initialize
	SearchList.Clear

	Select Case iSearchBy
		Case 0'Meter Number
			sField1="SeqNo"
			sField2="AcctName"
			SV.et.InputType = SV.et.INPUT_TYPE_NUMBERS

		Case 1'Account Number
			sField1="MeterNo"
			sField2="AcctName"
			SV.et.InputType = SV.et.INPUT_TYPE_NUMBERS

		Case 2'Account Name
			sField1="AcctName"
			sField2="SeqNo"
			SV.et.InputType = SV.et.INPUT_TYPE_TEXT
	End Select
	
	If SV.IsInitialized=False Then
		SV.Initialize(Me,"SV")
	End If
	
	SV.ClearAll
	SV.ClearSearchBox
	SV.lv.Clear
	
	Select iFilterBy
		Case 0
			If RetrieveUnread(GlobalVar.BookID, sField1)= False Then Return
			For i = 0 To rsUnReadAcct.RowCount - 1
				rsUnReadAcct.Position=i
				Dim it As Item
				it.Title=rsUnReadAcct.GetString(sField1) & " - " & rsUnReadAcct.GetString(sField2)
				it.Text=rsUnReadAcct.GetString("AcctAddress")
				it.SearchText=rsUnReadAcct.GetString(sField1).ToLowerCase
				it.Value=rsUnReadAcct.GetString("ReadID")
				SearchList.Add(it)
			Next
		Case 1
			If RetrieveRead(GlobalVar.BookID, sField1) = False Then Return
			For i = 0 To rsReadAcct.RowCount - 1
				rsReadAcct.Position=i
				Dim it As Item
				it.Title=rsReadAcct.GetString(sField1) & " - " & rsReadAcct.GetString(sField2)
				it.Text=rsReadAcct.GetString("AcctAddress")
				it.SearchText=rsReadAcct.GetString(sField1).ToLowerCase
				it.Value=rsReadAcct.GetString("ReadID")
				SearchList.Add(it)
			Next
		Case 2
			If RetrieveAll(GlobalVar.BookID, sField1) = False Then Return
			For i = 0 To rsAllAcct.RowCount - 1
				rsAllAcct.Position=i
				Dim it As Item
				it.Title=rsAllAcct.GetString(sField1) & " - " & rsAllAcct.GetString(sField2)
				it.Text=rsAllAcct.GetString("AcctAddress")
				it.SearchText=rsAllAcct.GetString(sField1).ToLowerCase
				it.Value=rsAllAcct.GetString("ReadID")
				SearchList.Add(it)
			Next
		Case Else
			For i = 0 To rsLoadedBook.RowCount - 1
				rsLoadedBook.Position=i
				Dim it As Item
				it.Title=rsLoadedBook.GetString(sField1) & " - " & rsLoadedBook.GetString(sField2)
				it.Text=rsLoadedBook.GetString("AcctAddress")
				it.SearchText=rsLoadedBook.GetString(sField1).ToLowerCase
				it.Value=rsLoadedBook.GetString("ReadID")
				SearchList.Add(it)
			Next
	End Select
	IMEkeyboard.ShowKeyboard(SV.et)
	SV.SetItems(SearchList)
	SearchList.Clear
End Sub

Sub RetrieveUnread(iBookID As Int, sOrderBy As String) As Boolean
	Dim blnRet As Boolean
	Try
		Starter.strCriteria="SELECT * FROM tblReadings " & _
							"WHERE WasRead = 0 AND BookID=" & iBookID & " AND BillYear= " & GlobalVar.BillYear  & " AND BillMonth=" & GlobalVar.BillMonth & "  ORDER BY " & sOrderBy
		Log(Starter.strCriteria)
		rsUnReadAcct=Starter.DBCon.ExecQuery(Starter.strCriteria)
		If rsUnReadAcct.RowCount>0 Then
			lblSearchRecCount.Text = rsUnReadAcct.RowCount & $" Record(s) found..."$
			blnRet= True
		Else
			lblSearchRecCount.Text = $"No Record found..."$
			blnRet=False
		End If
	Catch
		blnRet=False
		Log(LastException)
		ToastMessageShow("Unable to retreive records due to " & LastException.Message & "!",False)
	End Try
	Return blnRet
End Sub

Sub RetrieveAll(iBookID As Int, sOrderBy As String) As Boolean
	Dim blnRet As Boolean
	Try
		Starter.strCriteria="SELECT * FROM tblReadings " & _
							"WHERE BookID=" & iBookID & " AND BillYear= " & GlobalVar.BillYear & " AND BillMonth=" & GlobalVar.BillMonth & "  ORDER BY " & sOrderBy
		Log(Starter.strCriteria)
		rsAllAcct=Starter.DBCon.ExecQuery(Starter.strCriteria)
		If rsAllAcct.RowCount>0 Then
			lblSearchRecCount.Text = rsAllAcct.RowCount & $" Record(s) found..."$
			blnRet= True
		Else
			lblSearchRecCount.Text = $"No Record found..."$
			blnRet=False
		End If
	Catch
		blnRet=False
		Log(LastException)
		ToastMessageShow("Unable to retreive records due to " & LastException.Message & "!",False)
	End Try
	Return blnRet
End Sub

Sub RetrieveRead(iBookID As Int, sOrderBy As String) As Boolean
	Dim blnRet As Boolean
	Try
		Starter.strCriteria="SELECT * FROM tblReadings " & _
							"WHERE WasRead = 1 AND BookID=" & iBookID & " AND BillYear= " & GlobalVar.BillYear & " AND BillMonth=" & GlobalVar.BillMonth & " ORDER BY " & sOrderBy
		Log(Starter.strCriteria)
		rsReadAcct = Starter.DBCon.ExecQuery(Starter.strCriteria)
		If rsReadAcct.RowCount>0 Then
			lblSearchRecCount.Text = rsReadAcct.RowCount & $" Record(s) found..."$
			blnRet= True
		Else
			lblSearchRecCount.Text = $"No Record found..."$
			blnRet=False
		End If
	Catch
		blnRet=False
		Log(LastException)
		ToastMessageShow("Unable to retreive records due to " & LastException.Message & "!",False)
	End Try
	Return blnRet
End Sub

Sub btnCancelSearch_Click
	SearchClosed
	IMEkeyboard.HideKeyboard
	rsLoadedBook.Position = intTempCurrPos
	DisplayRecord
	ButtonState
End Sub


Sub SearchClosed
	SearchFor = ""
	SV.ClearSearchBox
	SV.ClearAll
'	SV.RemoveView
	pnlSearch.Visible=False
	pnlUnusual.Visible = False
'	SetButtonColors
	EnableControls
End Sub

Sub FindSearchRetValue(sSearch As String)
	Try
		Log(rsLoadedBook.Position)
'		rsLoadedBook.Position=0
		For i = 0 To rsLoadedBook.RowCount - 1
			rsLoadedBook.Position = i
			If sSearch = rsLoadedBook.GetString("ReadID") Then
				ReadID = sSearch
				BookMark = rsLoadedBook.Position + 1
				DisplayRecord
				ButtonState
				Exit
			End If
		Next
		Log(rsLoadedBook.Position)
	Catch
		Log(LastException)
	End Try
End Sub

Sub pnlSearch_Touch (Action As Int, X As Float, Y As Float)
	
End Sub
#End Region

#Region Adding MissCodeOnly
Private Sub ShowAddMissCodeDialog
	soundsAlarmChannel.Play(SoundID,1,1,1,0,1)

	cdButtonCancel.Initialize(0xFFDC143C, 25)
	btnMisCodeCancelPost.Background = cdButtonCancel

	cdButtonSaveAndPrint.Initialize(0xFF1976D2, 25)
	btnMisCodeSave.Background = cdButtonSaveAndPrint
	
	pnlMisCodeMsgBox.Visible = True
	lblMisCodeMsgTitle.Text = $"         ADD MISCODE ENTRY"$
	btnMisCodeSave.Text = $"SAVE"$
	txtMisCode.Enabled = False
	blnEdit = True
End Sub

Sub btnMisCodeSave_Click
	If optMisCode0.Checked = True Then
		MisCodeID = 1
		MisCodeDesc = optMisCode0.Text
	Else If optMisCode1.Checked = True Then
		MisCodeID = 2
		MisCodeDesc = optMisCode1.Text
	Else If optMisCode2.Checked = True Then
		MisCodeID = 3
		MisCodeDesc = optMisCode2.Text
	Else If optMisCode3.Checked = True Then
		MisCodeID = 4
		MisCodeDesc = optMisCode3.Text
	Else If optMisCode4.Checked = True Then
		MisCodeID = 5
		MisCodeDesc = optMisCode4.Text
	Else If optMisCode5.Checked = True Then
		MisCodeID = 6
		MisCodeDesc = optMisCode5.Text
	Else If optMisCode6.Checked = True Then
		MisCodeID = 7
		MisCodeDesc = optMisCode6.Text
	Else If optMisCode7.Checked = True Then
		MisCodeID = 8
		MisCodeDesc = optMisCode7.Text
	Else If optMisCode8.Checked = True Then
		If txtMisCode.Text = "" Or GlobalVar.SF.Len(GlobalVar.SF.Trim(txtMisCode.Text)) <= 0 Then
			If DispErrorMsg($"Please enter other reason to post."$, $"E R R O R"$) = True Then
				txtMisCode.Enabled = True
				txtMisCode.RequestFocus
				IMEkeyboard.ShowKeyboard(txtMisCode)
				Return
			End If
		Else
			MisCodeID = 9
			MisCodeDesc = txtMisCode.Text
		End If
	Else
		Return
	End If
	
	vibration.vibrateCancel
	soundsAlarmChannel.Stop(SoundID)
	
	Select Case btnMisCodeSave.Text
		Case "SAVE"
			blnEdit = True
			intCurrPos = rsLoadedBook.Position
			SaveMissCodeOnly(MisCodeID, MisCodeDesc,ReadID, AcctID)
			pnlMisCodeMsgBox.Visible = False
		Case "UPDATE"
			blnEdit = False
			intCurrPos = rsLoadedBook.Position
			SaveMissCodeOnly(MisCodeID, MisCodeDesc,ReadID, AcctID)
			pnlMisCodeMsgBox.Visible = False
	End Select

	If RetrieveRecords(GlobalVar.BookID)=False Then Return
	If intCurrPos < (RecCount - 1) Then
		rsLoadedBook.Position = intCurrPos + 1
	Else
		rsLoadedBook.Position = intCurrPos
	End If
	DisplayRecord
	ButtonState

	If GetUnusualCount(GlobalVar.BookID) = True Then
		UpdateBadge("Flag", AddBadgeToIcon(flagBitmap, intTotal))
	End If
	
End Sub

Sub optMisCode0_CheckedChange(Checked As Boolean)
	LogColor(Checked,Colors.Magenta)
	If Checked = True Then
		txtMisCode.Enabled = False
		txtMisCode.Text = ""
	End If
End Sub

Sub optMisCode1_CheckedChange(Checked As Boolean)
	LogColor(Checked,Colors.Magenta)
	If Checked = True Then
		txtMisCode.Enabled = False
		txtMisCode.Text = ""
	End If
End Sub

Sub optMisCode2_CheckedChange(Checked As Boolean)
	LogColor(Checked,Colors.Magenta)
	If Checked = True Then
		txtMisCode.Enabled = False
		txtMisCode.Text = ""
	End If
End Sub

Sub optMisCode3_CheckedChange(Checked As Boolean)
	LogColor(Checked,Colors.Magenta)
	If Checked = True Then
		txtMisCode.Enabled = False
		txtMisCode.Text = ""
	End If
End Sub

Sub optMisCode4_CheckedChange(Checked As Boolean)
	LogColor(Checked,Colors.Magenta)
	If Checked = True Then
		txtMisCode.Enabled = False
		txtMisCode.Text = ""
	End If
End Sub

Sub optMisCode5_CheckedChange(Checked As Boolean)
	LogColor(Checked,Colors.Magenta)
	If Checked = True Then
		txtMisCode.Enabled = False
		txtMisCode.Text = ""
	End If
End Sub

Sub optMisCode6_CheckedChange(Checked As Boolean)
	LogColor(Checked,Colors.Magenta)
	If Checked = True Then
		txtMisCode.Enabled = False
		txtMisCode.Text = ""
	End If
End Sub

Sub optMisCode7_CheckedChange(Checked As Boolean)
	LogColor(Checked,Colors.Magenta)
	If Checked = True Then
		txtMisCode.Enabled = False
		txtMisCode.Text = ""
	End If
End Sub

Sub optMisCode8_CheckedChange(Checked As Boolean)
	LogColor(Checked,Colors.Magenta)
	If Checked = True Then
		txtMisCode.Enabled = True
		txtMisCode.RequestFocus
		IMEkeyboard.ShowKeyboard(txtOthers)
	Else
		txtMisCode.Enabled = False
		txtMisCode.Text = ""
	End If
End Sub

Sub txtMisCode_TextChanged (Old As String, New As String)
	If optMisCode8.Checked = True Then
		If GlobalVar.SF.Len(GlobalVar.SF.Trim(txtMisCode.Text)) <= 0 Then
			btnMisCodeSave.Enabled = False
		Else
			btnMisCodeSave.Enabled = True
		End If
	Else
		btnMisCodeSave.Enabled = True
	End If
End Sub

Sub txtMisCode_EnterPressed
	If btnMisCodeSave.Enabled = True Then
		btnMisCodeSave_Click
	End If
End Sub

Sub btnMisCodeCancelPost_Click
	soundsAlarmChannel.Stop(SoundID)
	vibration.vibrateCancel
	pnlMisCodeMsgBox.Visible = False

	snack.Initialize("", Activity, $"Posting Miscode Cancelled."$ ,snack.DURATION_SHORT)
	SetSnackBarBackground(snack, Colors.White)
	SetSnackBarTextColor(snack, Colors.Red)
	snack.Show
	blnEdit = False
	IMEkeyboard.HideKeyboard
End Sub
Private Sub ShowEditMissCodeDialog
	soundsAlarmChannel.Play(SoundID,1,1,1,0,1)

	cdButtonCancel.Initialize(0xFFDC143C, 25)
	btnMisCodeCancelPost.Background = cdButtonCancel

	cdButtonSaveAndPrint.Initialize(0xFF1976D2, 25)
	btnMisCodeSave.Background = cdButtonSaveAndPrint
	
	pnlMisCodeMsgBox.Visible = True
	lblMisCodeMsgTitle.Text = $"         EDIT MISCODE ENTRY"$
	btnMisCodeSave.Text = $"UPDATE"$
	txtMisCode.Enabled = False
	blnEdit = False
End Sub

Private Sub SaveMissCodeOnly(iMissCodeID As Int, sMissCodeDesc As String, iReadID As Int, iAcctID As Int)
	Dim lngDateTime As Long
	Dim Findings As String
	Dim sWarning As String
	lngDateTime = DateTime.Now
	DateTime.TimeFormat = "HH:mm:ss"
	TimeRead = DateTime.Time(lngDateTime)
	DateTime.DateFormat = "MM/dd/yyyy"
	DateRead = DateTime.Date(lngDateTime)

	NoInput = NoInput + 1
	If IsNumber(txtPresRdg.Text) Then
		TotalCum = lblPresCum.Text + AddCons
	Else
		TotalCum = 0
	End If
	
	If lblPresCum.Text.Length <=0 Then
		Findings = "Skipped Reading"
	Else
		If strSF.Val(lblPresCum.Text) < 0 Then
			Findings = "Negative Consumption"
		End If
	End If
	
	sWarning = $"Unable to Read Customer's Meter"$
	
	Try
		Starter.DBCon.BeginTransaction
		If blnEdit = True Then
			Starter.strCriteria="UPDATE tblReadings " & _
							"SET WasMissCoded = ?, MissCodeID = ?, MissCode = ?, fFindings = ?, fWarnings = ?, OrigRdg = ?, PresRdg = ?, PresCum = ?, TotalCum = ?, " & _
							"WasRead = ?, NoInput = ?, TimeRead = ?, DateRead = ? " & _
							"WHERE ReadID = " & iReadID & " " & _
							"And AcctID = " & iAcctID

			Starter.DBCon.ExecNonQuery2(Starter.strCriteria, Array As String($"1"$, iMissCodeID, sMissCodeDesc, Findings, sWarning, txtPresRdg.Text, txtPresRdg.Text, lblPresCum.Text, TotalCum, _
							 $"1"$, NoInput, TimeRead, DateRead))

			snack.Initialize("", Activity, $"Miss Code Entry was successfully updated"$, snack.DURATION_SHORT)
			SetSnackBarBackground(snack, GlobalVar.PriColor)
			SetSnackBarTextColor(snack, Colors.White)
			snack.Show
			blnEdit = False
		Else
			NewSeqNo = DBaseFunctions.GetSeqNo(GlobalVar.BookID)
			Starter.strCriteria="UPDATE tblReadings " & _
							"SET WasMissCoded = ?, MissCodeID = ?, MissCode = ?, fFindings = ?, fWarnings = ?, OrigRdg = ?, PresRdg = ?, PresCum = ?, TotalCum = ?, MissCoded = ?, " & _
							"WasRead = ?, NewSeqNo = ?, NoInput = ?, TimeRead = ?, DateRead = ? " & _
							"WHERE ReadID = " & iReadID & " " & _
							"And AcctID = " & iAcctID

			Starter.DBCon.ExecNonQuery2(Starter.strCriteria, Array As String($"1"$, iMissCodeID, sMissCodeDesc, Findings, sWarning, txtPresRdg.Text, txtPresRdg.Text, lblPresCum.Text, TotalCum, $"1"$, _
							$"1"$, NewSeqNo, NoInput, TimeRead, DateRead))
			snack.Initialize("", Activity, $"Miss Code Entry was successfully saved!"$, snack.DURATION_SHORT)
			SetSnackBarBackground(snack, GlobalVar.PriColor)
			SetSnackBarTextColor(snack, Colors.White)
			snack.Show
		End If
		Starter.DBCon.TransactionSuccessful
	Catch
		Log(LastException)
	End Try
	Starter.DBCon.EndTransaction
End Sub
#End Region

#Region Add Remarks
Sub ShowAddRemarksDialog
	Dim csContent As CSBuilder
	
	csContent.Initialize.Size(16).Color(Colors.DarkGray).Append($"Please enter a remarks for this account."$).PopAll
	MatDialogBuilder.Initialize("AddRemarksDialog")
	MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIGHT)
	MatDialogBuilder.Title($"ADD READING REMARKS"$).TitleColor(Colors.Red).TitleGravity(MatDialogBuilder.GRAVITY_START)
	MatDialogBuilder.IconRes(GlobalVar.WarningIcon).LimitIconToDefaultSize
	MatDialogBuilder.InputType(Bit.Or(Bit.Or(MatDialogBuilder.TYPE_CLASS_TEXT, MatDialogBuilder.TYPE_TEXT_FLAG_CAP_SENTENCES), MatDialogBuilder.TYPE_TEXT_VARIATION_PERSON_NAME))
	MatDialogBuilder.Input2($"Type your Remarks here..."$, $""$, False)
	MatDialogBuilder.AlwaysCallInputCallback
	MatDialogBuilder.Content(csContent)
	MatDialogBuilder.PositiveText($"SAVE"$).PositiveColor(GlobalVar.PriColor)
	MatDialogBuilder.NegativeText($"CANCEL"$).NegativeColor(Colors.Red)
	MatDialogBuilder.CanceledOnTouchOutside(False)
	MatDialogBuilder.Show
End Sub

Sub AddRemarksDialog_InputChanged (mDialog As MaterialDialog, sRemarks As String)
	If sRemarks.Length=0 Then
		mDialog.Content="Nothing to save."
		mDialog.EnableActionButton(mDialog.ACTION_POSITIVE, False)
	Else
		mDialog.Content="Please Enter a Remarks for this Account."
		mDialog.EnableActionButton(mDialog.ACTION_POSITIVE, True)
	End If
	ReadRemarks = sRemarks
End Sub

Sub AddRemarksDialog_ButtonPressed (Dialog As MaterialDialog, Action As String)
	Select Case Action
		Case Dialog.ACTION_POSITIVE
			intCurrPos = rsLoadedBook.Position
			SaveNewRemarks(ReadRemarks, ReadID, AcctID)
			If RetrieveRecords(GlobalVar.BookID)=False Then Return
			If intCurrPos < (RecCount - 1) Then
				rsLoadedBook.Position = intCurrPos + 1
			Else
				rsLoadedBook.Position = intCurrPos
			End If
			DisplayRecord
			ButtonState
		Case Dialog.ACTION_NEGATIVE
			snack.Initialize("", Activity, $"Adding Reading Remarks Cancelled."$ ,snack.DURATION_SHORT)
			SetSnackBarBackground(snack, Colors.White)
			SetSnackBarTextColor(snack, Colors.Red)
			snack.Show
			Return
	End Select
End Sub

Sub ShowEditRemarksDialog
	Dim csContent As CSBuilder
	
	csContent.Initialize.Size(16).Color(Colors.DarkGray).Append($"Please enter a remarks for this account."$).PopAll
	MatDialogBuilder.Initialize("EditRemarksDialog")
	MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIGHT)
	MatDialogBuilder.Title($"EDIT READING REMARKS"$).TitleColor(Colors.Red).TitleGravity(MatDialogBuilder.GRAVITY_START)
	MatDialogBuilder.IconRes(GlobalVar.WarningIcon).LimitIconToDefaultSize
	MatDialogBuilder.InputType(Bit.Or(Bit.Or(MatDialogBuilder.TYPE_CLASS_TEXT, MatDialogBuilder.TYPE_TEXT_FLAG_CAP_SENTENCES), MatDialogBuilder.TYPE_TEXT_VARIATION_PERSON_NAME))
	MatDialogBuilder.Input2($"Type your Remarks here..."$, $""$, False)
	MatDialogBuilder.AlwaysCallInputCallback
	MatDialogBuilder.Content(csContent)
	MatDialogBuilder.PositiveText($"UPDATE"$).PositiveColor(GlobalVar.PriColor)
	MatDialogBuilder.NegativeText($"CANCEL"$).NegativeColor(Colors.Red)
	MatDialogBuilder.CanceledOnTouchOutside(False)
	MatDialogBuilder.Show
End Sub

Sub EditRemarksDialog_InputChanged (mDialog As MaterialDialog, sRemarks As String)
	If sRemarks.Length=0 Then
		mDialog.Content="Nothing to save."
		mDialog.EnableActionButton(mDialog.ACTION_POSITIVE, False)
	Else
		mDialog.Content="Please Enter a Remarks for this Account."
		mDialog.EnableActionButton(mDialog.ACTION_POSITIVE, True)
	End If
	ReadRemarks = sRemarks
End Sub

Sub EditRemarksDialog_ButtonPressed (Dialog As MaterialDialog, Action As String)
	Select Case Action
		Case Dialog.ACTION_POSITIVE
			intCurrPos = rsLoadedBook.Position
			SaveNewRemarks(ReadRemarks, ReadID, AcctID)
			If RetrieveRecords(GlobalVar.BookID)=False Then Return
			If intCurrPos < (RecCount - 1) Then
				rsLoadedBook.Position = intCurrPos + 1
			Else
				rsLoadedBook.Position = intCurrPos
			End If
			DisplayRecord
			ButtonState
		Case Dialog.ACTION_NEGATIVE
			snack.Initialize("", Activity, $"Editting Reading Remarks Cancelled."$ ,snack.DURATION_SHORT)
			SetSnackBarBackground(snack, Colors.White)
			SetSnackBarTextColor(snack, Colors.Red)
			snack.Show
			IMEkeyboard.HideKeyboard
			Return
	End Select
End Sub

Private Sub SaveNewRemarks(sRemarks As String, iReadID As Int, iAcctID As Int)
	Dim intWasBlank As Int
	
	Try
		NoInput = NoInput + 1
		Starter.DBCon.BeginTransaction
		Starter.strCriteria="UPDATE tblReadings " & _
							"SET ReadRemarks = ?, NoInput = ? " & _
							"WHERE ReadID = " & iReadID & " " & _
							"AND AcctID = " & iAcctID

		Starter.DBCon.ExecNonQuery2(Starter.strCriteria, Array As String(sRemarks, NoInput))
		Starter.DBCon.TransactionSuccessful
		If blnEdit = True Then
			snack.Initialize("", Activity, $"Remarks Entry was successfully updated!"$, snack.DURATION_LONG)
			SetSnackBarBackground(snack, GlobalVar.PriColor)
			SetSnackBarTextColor(snack, Colors.White)
			snack.Show
		Else
			snack.Initialize("", Activity, $"Remarks Entry was successfully added!"$, snack.DURATION_LONG)
			SetSnackBarBackground(snack, GlobalVar.PriColor)
			SetSnackBarTextColor(snack, Colors.White)
			snack.Show
		End If
	Catch
		Log(LastException)
	End Try
	Starter.DBCon.EndTransaction
End Sub
#End Region

#Region Saving Readings
Private Sub SaveReading(iPrintStatus As Int, iReadID As Int, iAcctID As Int)
	Dim lngDateTime As Long
	Dim sWarning As String
	Dim MinimumRangeTo As Int
	Dim dHeaderID As Int
	
	Dim bBasicAmt As BigDecimal
	Dim bPCAAmt As BigDecimal
	Dim bCurrentAmt As BigDecimal
	Dim bSeniorOnBeforeAmt As BigDecimal
	Dim bTotalDueAmtBeforeSC As BigDecimal
	Dim bDiscAmt As BigDecimal
	Dim bMeterCharges As BigDecimal
	Dim bFranchiseTaxAmt As BigDecimal
	
	Dim bSeptageFee As BigDecimal
	Dim bSeptageFeeAmt As BigDecimal
	
	Dim bTotalDueAmt As BigDecimal
	Dim bNewAmt As BigDecimal
	
	Dim bSeniorAfterAmt As BigDecimal
	Dim bPenaltyAmt As BigDecimal
	Dim bTotalDueAmtAfterSC As BigDecimal

	Dim sBasicAmt As String
	Dim sPCAAmt As String
	Dim sCurrentAmt As String
	Dim sSeniorOnBeforeAmt As String
	Dim sTotalDueAmtBeforeSC As String
	Dim sDiscAmt As String
	Dim sMeterCharges As String
	Dim sFranchiseTaxAmt As String
	Dim sSeptageFee As String
	Dim sSeptageFeeAmt As String
	Dim sTotalDueAmt As String
	Dim sNewAmt As String
	
	Dim sSeniorAfterAmt As String
	Dim sPenaltyAmt As String
	Dim sTotalDueAmtAfterSC As String
	
'	dHeaderID = DBaseFunctions.GetRatesHeaderID(AcctClass, AcctSubClass)
	'***********************************************************

	'Reading Date and Time**************************************
	lngDateTime = DateTime.Now
	DateTime.TimeFormat = "HH:mm:ss"
	TimeRead = DateTime.Time(lngDateTime)
	DateTime.DateFormat = "MM/dd/yyyy"
	DateRead = DateTime.Date(lngDateTime)
	
	LogColor($"Date Read: "$ & DateRead, Colors.Yellow)
	LogColor($"Time Read: "$ & TimeRead, Colors.Magenta)
	'***********************************************************

	'Misc **************************************
	CurrentCuM = strSF.Val(txtPresRdg.Text) - strSF.Val(PrevRdg)

	If CurrentCuM < 0 Then
		WarningMsg($"E R R O R"$, $"Unable to save this reading at this time..."$)
		Return
	End If
	
	ImplosiveType = GetImplosiveType(PrevCum, CurrentCuM)
	sWarning = GetWarning(PrevCum, CurrentCuM)
	
	NoInput = NoInput + 1
	WasRead = 1
	PrintStatus = iPrintStatus
	
	If PrintStatus = 1 Then
		NoPrinted = NoPrinted + 1
	Else
		NoPrinted = NoPrinted
	End If
	'End of Misc ***********************************************************

	'Initialization *******************************************
	TotalCum = 0
	BasicAmt = 0
	PCAAmt = 0
	CurrentAmt = 0
	SeniorOnBeforeAmt = 0
	TotalDueAmtBeforeSC = 0
	DiscAmt = 0
	FranchiseTaxAmt = 0
	
	SeptageFee = 0
	SeptageFeeAmt = 0
	TotalDueAmt = 0
	
	SeniorAfterAmt = 0
	PenaltyAmt = 0
	TotalDueAmtAfterSC = 0
	RemainingAdvPayment = 0
	'End of Initialization*********************************************************
	
	'Computation **********************************************
	TotalCum = strSF.Val(lblPresCum.Text) + AddCons

	BasicAmt = DBaseFunctions.ComputeBasicAmt(strSF.Val(TotalCum), GlobalVar.BranchID, AcctClass, AcctSubClass)
		
	'PCA Computation
	If DBaseFunctions.IsBookWithPCA(GlobalVar.BookID) = True Then
		If AcctClass = "REL" Then
			If TotalCum <= 10 Then
				PCAAmt = 0
			Else
				PCAAmt = (TotalCum - 10) * PCA
			End If
		Else
			MinimumRangeTo = DBaseFunctions.GetMinimumRangeTo(GlobalVar.BranchID, AcctClass, AcctSubClass, GlobalVar.BillYear & "-" & GlobalVar.BillMonth & "-01")
			
			If TotalCum <= MinimumRangeTo Then
				PCAAmt = MinimumRangeTo * PCA
			Else
				PCAAmt = TotalCum * PCA
			End If
		End If
	Else
		PCAAmt = 0
	End If
	
	'Current Amount
	CurrentAmt = BasicAmt + PCAAmt

	'Senior Citizen Computation
	If IsSenior = 1 And AcctClass = "RES" Then
		SeniorOnBeforeAmt = GetSeniorBefore(ReadID, TotalCum)
		SeniorAfterAmt = GetSeniorAfter(ReadID, TotalCum)
	Else
		SeniorAfterAmt = 0
		SeniorOnBeforeAmt = 0
	End If

	TotalDueAmtBeforeSC = CurrentAmt - SeniorOnBeforeAmt
	
	'Franchise Tax Computation
	FranchiseTaxAmt = (CurrentAmt * FranchiseTaxPct)

'	Septage Fee Computation	
	GlobalVar.SeptageRateID = 0
	GlobalVar.SeptageRatePerCuM = 0
	GlobalVar.SeptageMinCuM = 0
	GlobalVar.SeptageMaxCuM = 0
	GlobalVar.SeptageRateType = "amount"
	dSeptageRate = 0
	
	If HasSeptageFee = 1 Then
		LogColor ($"Acct Classification: "$ & AcctClassification, Colors.Magenta)
		GlobalVar.SeptageRateID = DBaseFunctions.GetSeptageRateID(AcctClassification, GlobalVar.BranchID)
		LogColor ($"Septage Rate ID: "$ & GlobalVar.SeptageRateID, Colors.Cyan)
		
		If GlobalVar.SeptageRateID = 0 Then
			WarningMsg($"NO SEPTAGE RATES FOUND!"$, $"Customer account classification has no septage rates found!"$ & CRLF & $"Please ask assistance to the Central Billing & IT Department regarding this."$)
			Return
		Else
			DBaseFunctions.GetSeptageRateDetails(GlobalVar.BranchID, GlobalVar.SeptageRateID, AcctClassification)
			If GlobalVar.SeptageRateType = "amount" Then
				If TotalCum <= GlobalVar.SeptageMinCuM Then
					SeptageFee = GlobalVar.SeptageRatePerCuM * GlobalVar.SeptageMinCuM
					
				Else If TotalCum >= GlobalVar.SeptageMaxCuM Then
					SeptageFee = GlobalVar.SeptageRatePerCuM * GlobalVar.SeptageMaxCuM
					
				Else
					SeptageFee = GlobalVar.SeptageRatePerCuM * TotalCum
				End If
			Else
				SeptageFee = (GlobalVar.SeptageRatePerCuM) * CurrentAmt
			End If
		End If
		
		'Old septage code
'		If TotalCum <= MinSeptageCum Then
'			SeptageFee = SeptageRate * MinSeptageCum
'		Else If TotalCum >= MaxSeptageCum Then
'			SeptageFee = SeptageRate * MaxSeptageCum
'		Else
'			SeptageFee = SeptageRate * TotalCum
'		End If

'		End If
	Else
		SeptageFee = 0
	End If
	
	LogColor($"Septage Rate: "$ & SeptageRate, Colors.Cyan)
	SeptageFeeAmt = SeptageFee
	
	'Round Off **********************************************
	bSeptageFee.Initialize(SeptageFee)
	bSeptageFee = RoundtoCurrency(bSeptageFee, 2)
	sSeptageFee = NumberFormat2(bSeptageFee.ToPlainString, 1, 2, 2, False)
	LogColor($"ORIG SEPTAGE AND SEWERAGE: "$ & sSeptageFee, Colors.Yellow)

	bBasicAmt.Initialize(BasicAmt)
	bBasicAmt = RoundtoCurrency(bBasicAmt, 2)
	sBasicAmt = NumberFormat2(bBasicAmt.ToPlainString, 1, 2, 2, False)
	LogColor($"Basic: "$ & sBasicAmt, Colors.White)

	bPCAAmt.Initialize(PCAAmt)
	bPCAAmt = RoundtoCurrency(bPCAAmt, 2)
	sPCAAmt = NumberFormat2(bPCAAmt.ToPlainString, 1, 2, 2, False)
	LogColor($"PCA: "$ & sPCAAmt, Colors.White)
	
	bCurrentAmt.Initialize(CurrentAmt)
	bCurrentAmt = RoundtoCurrency(bCurrentAmt, 2)
	sCurrentAmt = NumberFormat2(bCurrentAmt.ToPlainString, 1, 2, 2, False)
	LogColor($"CURENT BILL: "$ & sCurrentAmt, Colors.White)
	
	bSeniorOnBeforeAmt.Initialize(SeniorOnBeforeAmt)
	bSeniorOnBeforeAmt = RoundtoCurrency(bSeniorOnBeforeAmt, 2)
	sSeniorOnBeforeAmt = NumberFormat2(bSeniorOnBeforeAmt.ToPlainString, 1, 2, 2, False)
	LogColor($"SC DISC: "$ & sSeniorOnBeforeAmt, Colors.White)
	
	bSeniorAfterAmt.Initialize(SeniorAfterAmt)
	bSeniorAfterAmt = RoundtoCurrency(bSeniorAfterAmt, 2)
	sSeniorAfterAmt = NumberFormat2(bSeniorAfterAmt.ToPlainString, 1, 2, 2, False)
	LogColor($"SENIOR DISC BEFORE: "$ & sSeniorAfterAmt, Colors.Yellow)
	
	bTotalDueAmtBeforeSC.Initialize(TotalDueAmtBeforeSC)
	bTotalDueAmtBeforeSC = RoundtoCurrency(bTotalDueAmtBeforeSC, 2)
	sTotalDueAmtBeforeSC = NumberFormat2(bTotalDueAmtBeforeSC.ToPlainString, 1, 2, 2, False)
	LogColor($"TOTAL DUE BEFORE DUE: "$ & sTotalDueAmtBeforeSC, Colors.YELLOW)
	
	bDiscAmt.Initialize(DiscAmt)
	bDiscAmt = RoundtoCurrency(bDiscAmt, 2)
	sDiscAmt = NumberFormat2(bDiscAmt.ToPlainString, 1, 2, 2, False)
	LogColor($"DISCOUNT: "$ & sDiscAmt, Colors.White)
	
	bMeterCharges.Initialize(MeterCharges)
	bMeterCharges = RoundtoCurrency(bMeterCharges, 2)
	sMeterCharges = NumberFormat2(bMeterCharges.ToPlainString, 1, 2, 2, False)
	LogColor($"METER CHARGE: "$ & sMeterCharges, Colors.Yellow)
	
	bFranchiseTaxAmt.Initialize(FranchiseTaxAmt)
	bFranchiseTaxAmt = RoundtoCurrency(bFranchiseTaxAmt, 2)
	sFranchiseTaxAmt = NumberFormat2(bFranchiseTaxAmt.ToPlainString, 1, 2, 2, False)
	LogColor($"FRANCHISE TAX: "$ & sFranchiseTaxAmt, Colors.Yellow)
	
	'Computation for Total Due Amount**********************************************
'	If GlobalVar.BranchID = 4 Or GlobalVar.BranchID = 12 Then
'		bNewAmt.Initialize((sCurrentAmt + AddToBillAmt + ArrearsAmt + sMeterCharges + sFranchiseTaxAmt) - (sSeniorOnBeforeAmt + sDiscAmt))
'	Else
'		bNewAmt.Initialize((sCurrentAmt + AddToBillAmt + ArrearsAmt + sMeterCharges + sFranchiseTaxAmt + sSeptageFeeAmt) - (sSeniorOnBeforeAmt + sDiscAmt))
'	End If
	
	'EXCLUDE SEPTAGE FEE
	bNewAmt.Initialize((sCurrentAmt + AddToBillAmt + ArrearsAmt + sMeterCharges + sFranchiseTaxAmt) - (sSeniorOnBeforeAmt + sDiscAmt))
	bNewAmt = RoundtoCurrency(bNewAmt,2)
	sNewAmt =  NumberFormat2(bNewAmt.ToPlainString, 1, 2, 2, False)
	
	LogColor(sNewAmt,Colors.Yellow)
	
	If (sNewAmt - AdvPayment) < 0 Then
		TotalDueAmt = 0
	Else
		TotalDueAmt = sNewAmt - AdvPayment
	End If
	
	If (AdvPayment - sNewAmt) <= 0 Then
		RemainingAdvPayment = 0
	Else
		RemainingAdvPayment = AdvPayment - sNewAmt
	End If
	
'	If RemainingAdvPayment <= SeptageFeeAmt Then
'		SeptageFeeAmt = SeptageFeeAmt - RemainingAdvPayment
'	Else
'		SeptageFeeAmt = 0
'	End If
	
	bSeptageFeeAmt.Initialize(SeptageFeeAmt)
	bSeptageFeeAmt = RoundtoCurrency(bSeptageFeeAmt, 2)
	sSeptageFeeAmt = NumberFormat2(bSeptageFeeAmt.ToPlainString, 1, 2, 2, False)
	
	LogColor($"SEPTAGE AND SEWERAGE1: "$ & SeptageFee, Colors.White)
	LogColor($"SEPTAGE AND SEWERAGE: "$ & sSeptageFeeAmt, Colors.White)

	'TOTAL AMOUNT BEFORE DUE WITHOUT SEPTAGE FEE
	bTotalDueAmt.Initialize(TotalDueAmt)
	bTotalDueAmt = RoundtoCurrency(bTotalDueAmt, 2)
	sTotalDueAmt = NumberFormat2(bTotalDueAmt.ToPlainString, 1, 2, 2, False)
	LogColor($"TOTAL DUE: "$ & sTotalDueAmt, Colors.White)
	
	If AdvPayment >= CurrentAmt Then
		PenaltyAmt = 0
	Else
		PenaltyAmt = CurrentAmt * PenaltyPct
	End If

	bPenaltyAmt.Initialize(PenaltyAmt)
	bPenaltyAmt = RoundtoCurrency(bPenaltyAmt, 2)
	sPenaltyAmt = NumberFormat2(bPenaltyAmt.ToPlainString, 1, 2, 2, False)
	LogColor($"PENALTY: "$ & sPenaltyAmt, Colors.White)

	'AFTER DUE COMPUTATION
	If AdvPayment > ((sCurrentAmt + AddToBillAmt + ArrearsAmt + sMeterCharges + sFranchiseTaxAmt) - (sSeniorAfterAmt + sDiscAmt)) Then
		TotalDueAmtAfterSC = 0
	Else
		TotalDueAmtAfterSC = (sCurrentAmt + AddToBillAmt + ArrearsAmt + sMeterCharges + sFranchiseTaxAmt) - (AdvPayment + sSeniorAfterAmt + sDiscAmt) + sPenaltyAmt
	End If


	bTotalDueAmtAfterSC.Initialize(TotalDueAmtAfterSC)
	bTotalDueAmtAfterSC = RoundtoCurrency(bTotalDueAmtAfterSC, 2)
	sTotalDueAmtAfterSC = NumberFormat2(bTotalDueAmtAfterSC.ToPlainString, 1, 2, 2, False)
	LogColor($"TOTAL AMT. AFTER DUE: "$ & sTotalDueAmtAfterSC, Colors.White)
	
	'///////////////////////////////////////////////// End of Computation ******************************************************************************
	
	'Updating tblReadings record
	
	Starter.DBCon.BeginTransaction
	Try
		If blnEdit = True Then
			Starter.strCriteria = "UPDATE tblReadings " & _
							  "SET PresRdg = ?, PresCum = ?, TotalCum = ?, BasicAmt = ?, PCAAmt = ?, SeptageAmt = ?, SeptageFeeAmt = ?, " & _
							  "CurrentAmt = ?, TotalDueAmt = ?, SeniorOnBeforeAmt = ?, SeniorAfterAmt = ?, TotalDueAmtBeforeSC = ?, PenaltyAmt = ?, TotalDueAmtAfterSC = ?, DiscAmt = ?, FranchiseTaxAmt = ?, " & _
							  "PrintStatus = ?, NoPrinted = ?, WasMissCoded = ?, MissCodeID = ?, MissCode = ?, WasImplosive = ?, ImplosiveType = ?, ImpID = ?, FFindings = ?, FWarnings = ?, ReadRemarks = ?, " & _
							  "BillType = ?, WasRead = ?, NoInput = ?, TimeRead = ?, DateRead = ? " & _
							  "WHERE ReadID = " & iReadID & " " & _
							  "AND AcctID = " & iAcctID
		
			Starter.DBCon.ExecNonQuery2(Starter.strCriteria, Array As String(txtPresRdg.Text, lblPresCum.Text, TotalCum, sBasicAmt, sPCAAmt, sSeptageFee, sSeptageFeeAmt, _
							  sCurrentAmt, sTotalDueAmt, sSeniorOnBeforeAmt, sSeniorAfterAmt, sTotalDueAmtBeforeSC, sPenaltyAmt, sTotalDueAmtAfterSC, sDiscAmt, sFranchiseTaxAmt, _
							  PrintStatus, NoPrinted, $"0"$, $"0"$, $""$, $"0"$, ImplosiveType, $"0"$, $"Actual Reading"$, sWarning, strReadRemarks, _
							  $"RB"$, $"1"$, NoInput, TimeRead, DateRead))
			blnEdit = False
		Else
			NewSeqNo = DBaseFunctions.GetSeqNo(GlobalVar.BookID)
			Starter.strCriteria = "UPDATE tblReadings " & _
							  "SET OrigRdg = ?, PresRdg = ?, PresCum = ?, TotalCum = ?, BasicAmt = ?, PCAAmt = ?, SeptageAmt = ?, SeptageFeeAmt = ?, " & _
							  "CurrentAmt = ?, TotalDueAmt = ?, SeniorOnBeforeAmt = ?, SeniorAfterAmt = ?, TotalDueAmtBeforeSC = ?, PenaltyAmt = ?, TotalDueAmtAfterSC = ?, DiscAmt = ?, FranchiseTaxAmt = ?, " & _
							  "PrintStatus = ?, NoPrinted = ?, WasMissCoded = ?, MissCodeID = ?, MissCode = ?, WasImplosive = ?, ImplosiveType = ?, ImpID = ?, FFindings = ?, FWarnings = ?, ReadRemarks = ?, " & _
							  "BillType = ?, WasRead = ?, NewSeqNo = ?, NoInput = ?, TimeRead = ?, DateRead = ? " & _
							  "WHERE ReadID = " & iReadID & " " & _
							  "AND AcctID = " & iAcctID
		
			Starter.DBCon.ExecNonQuery2(Starter.strCriteria, Array As String(txtPresRdg.Text, txtPresRdg.Text, lblPresCum.Text, TotalCum, sBasicAmt, sPCAAmt, sSeptageFee, sSeptageFeeAmt, _
							  sCurrentAmt, sTotalDueAmt, sSeniorOnBeforeAmt, sSeniorAfterAmt, sTotalDueAmtBeforeSC, sPenaltyAmt, sTotalDueAmtAfterSC, sDiscAmt, sFranchiseTaxAmt, _
							  PrintStatus, NoPrinted, $"0"$, $"0"$, $""$, $"0"$, ImplosiveType, $"0"$, $"Actual Reading"$, sWarning, strReadRemarks, _
							  $"RB"$, $"1"$, NewSeqNo, NoInput, TimeRead, DateRead))
		End If
		Starter.DBCon.TransactionSuccessful
	Catch
		Log(LastException.Message)
	End Try
	Starter.DBCon.EndTransaction
End Sub

Private Sub SaveAverageBill(iPrintStatus As Int, iReadID As Int, iAcctID As Int)
	Dim lngDateTime As Long
	Dim sWarning As String
	Dim MinimumRangeTo As Int
	Dim sAveCum As String
	
	Dim bBasicAmt As BigDecimal
	Dim bPCAAmt As BigDecimal
	Dim bCurrentAmt As BigDecimal
	Dim bSeniorOnBeforeAmt As BigDecimal
	Dim bTotalDueAmtBeforeSC As BigDecimal
	Dim bDiscAmt As BigDecimal
	Dim bMeterCharges As BigDecimal
	Dim bFranchiseTaxAmt As BigDecimal
	Dim bSeptageFee As BigDecimal
	Dim bSeptageFeeAmt As BigDecimal
	Dim bTotalDueAmt As BigDecimal
	Dim bNewAmt As BigDecimal

	Dim bSeniorAfterAmt As BigDecimal
	Dim bPenaltyAmt As BigDecimal
	Dim bTotalDueAmtAfterSC As BigDecimal


	Dim sBasicAmt As String
	Dim sPCAAmt As String
	Dim sCurrentAmt As String
	Dim sSeniorOnBeforeAmt As String
	Dim sTotalDueAmtBeforeSC As String
	Dim sDiscAmt As String
	Dim sMeterCharges As String
	Dim sFranchiseTaxAmt As String
	Dim sSeptageFee As String
	Dim sSeptageFeeAmt As String
	Dim sTotalDueAmt As String
	Dim sNewAmt As String

	
	Dim sSeniorAfterAmt As String
	Dim sPenaltyAmt As String
	Dim sTotalDueAmtAfterSC As String
	
	'Initialization *******************************************
	TotalCum = 0
	BasicAmt = 0
	PCAAmt = 0
	CurrentAmt = 0
	SeniorOnBeforeAmt = 0
	TotalDueAmtBeforeSC = 0
	DiscAmt = 0
	FranchiseTaxAmt = 0
	SeptageFee = 0
	SeptageFeeAmt = 0
	TotalDueAmt = 0
	
	SeniorAfterAmt = 0
	PenaltyAmt = 0
	TotalDueAmtAfterSC = 0
	RemainingAdvPayment = 0
	'End of Initialization*********************************************************

	'Reading Date and Time**************************************
	lngDateTime = DateTime.Now
	DateTime.TimeFormat = "HH:mm:ss"
	TimeRead = DateTime.Time(lngDateTime)
	DateTime.DateFormat = "MM/dd/yyyy"
	DateRead = DateTime.Date(lngDateTime)
	
	LogColor($"Date Read: "$ & DateRead, Colors.Yellow)
	LogColor($"Time Read: "$ & TimeRead, Colors.Magenta)
	'***********************************************************

	'Misc **************************************
	
	ImplosiveType = $"posted"$
	sWarning = $"Average Bill"$
	
	NoInput = NoInput + 1
	WasRead = 1
	PrintStatus = iPrintStatus
	
	If PrintStatus = 1 Then
		NoPrinted = NoPrinted + 1
	Else
		NoPrinted = NoPrinted
	End If
	'End of Misc ***********************************************************

	'Computation **********************************************
	sAveCum = AveCum

	If IsNumber(sAveCum) = False Then
		TotalCum = DBaseFunctions.GetMinimumRangeTo(GlobalVar.BranchID, AcctClass, AcctSubClass, GlobalVar.BillYear & "-" & GlobalVar.BillMonth & "-01") + AddCons
		PresAveCum = DBaseFunctions.GetMinimumRangeTo(GlobalVar.BranchID, AcctClass, AcctSubClass, GlobalVar.BillYear & "-" & GlobalVar.BillMonth & "-01") + AddCons
		PresAveRdg = PrevRdg + TotalCum
	Else
		If AddCons > 0 Then
			If AddCons >= AveCum Then
				TotalCum = AddCons
				PresAveCum = 0
				PresAveRdg = PrevRdg
			Else
				PresAveCum = AveCum - AddCons
				TotalCum = PresAveCum + AddCons
				PresAveRdg = PrevRdg + PresAveCum
			End If
		Else
			TotalCum = AveCum
			PresAveCum = AveCum
			PresAveRdg = PrevRdg + AveCum
		End If
	End If

	BasicAmt = DBaseFunctions.ComputeBasicAmt(strSF.Val(TotalCum), GlobalVar.BranchID, AcctClass, AcctSubClass)
		
	'PCA Computation
	If DBaseFunctions.IsBookWithPCA(GlobalVar.BookID) = True Then
		If AcctClass = "REL" Then
			If TotalCum <= 10 Then
				PCAAmt = 0
			Else
				PCAAmt = (TotalCum - 10) * PCA
			End If
		Else
			MinimumRangeTo = DBaseFunctions.GetMinimumRangeTo(GlobalVar.BranchID, AcctClass, AcctSubClass, GlobalVar.BillYear & "-" & GlobalVar.BillMonth & "-01")
			
			If TotalCum <= MinimumRangeTo Then
				PCAAmt = MinimumRangeTo * PCA
			Else
				PCAAmt = TotalCum * PCA
			End If
		End If
	Else
		PCAAmt = 0
	End If

	'Current Amount
	CurrentAmt = BasicAmt + PCAAmt
	
	'Senior Citizen Computation
	If IsSenior = 1 And AcctClass = "RES" Then
		SeniorOnBeforeAmt = GetSeniorBefore(ReadID, TotalCum)
		SeniorAfterAmt = GetSeniorAfter(ReadID, TotalCum)
	Else
		SeniorAfterAmt = 0
		SeniorOnBeforeAmt = 0
	End If

	TotalDueAmtBeforeSC = CurrentAmt - SeniorOnBeforeAmt
	
	'Franchise Tax Computation
	FranchiseTaxAmt = (CurrentAmt * FranchiseTaxPct)

'	Septage Fee Computation
'	Septage Fee Computation	
	GlobalVar.SeptageRateID = 0
	GlobalVar.SeptageRatePerCuM = 0
	GlobalVar.SeptageMinCuM = 0
	GlobalVar.SeptageMaxCuM = 0
	GlobalVar.SeptageRateType = "amount"
	dSeptageRate = 0
	
	If HasSeptageFee = 1 Then
		LogColor ($"Acct Classification: "$ & AcctClassification, Colors.Magenta)
		GlobalVar.SeptageRateID = DBaseFunctions.GetSeptageRateID(AcctClassification, GlobalVar.BranchID)
		LogColor ($"Septage Rate ID: "$ & GlobalVar.SeptageRateID, Colors.Cyan)
		
		If GlobalVar.SeptageRateID = 0 Then
			WarningMsg($"NO SEPTAGE RATES FOUND!"$, $"Customer account classification has no septage rates found!"$ & CRLF & $"Please ask assistance to the Central Billing & IT Department regarding this."$)
			Return
		Else
			DBaseFunctions.GetSeptageRateDetails(GlobalVar.BranchID, GlobalVar.SeptageRateID, AcctClassification)
			If GlobalVar.SeptageRateType = "amount" Then
				If TotalCum <= GlobalVar.SeptageMinCuM Then
					SeptageFee = GlobalVar.SeptageRatePerCuM * GlobalVar.SeptageMinCuM
					
				Else If TotalCum >= GlobalVar.SeptageMaxCuM Then
					SeptageFee = GlobalVar.SeptageRatePerCuM * GlobalVar.SeptageMaxCuM
					
				Else
					SeptageFee = GlobalVar.SeptageRatePerCuM * TotalCum
				End If
			Else
				SeptageFee = (GlobalVar.SeptageRatePerCuM) * CurrentAmt
			End If
		End If
		
		'Old septage code
'		If TotalCum <= MinSeptageCum Then
'			SeptageFee = SeptageRate * MinSeptageCum
'		Else If TotalCum >= MaxSeptageCum Then
'			SeptageFee = SeptageRate * MaxSeptageCum
'		Else
'			SeptageFee = SeptageRate * TotalCum
'		End If

'		End If
	Else
		SeptageFee = 0
	End If

''	Old septage code
''		If TotalCum <= MinSeptageCum Then
''			SeptageFee = SeptageRate * MinSeptageCum
''		Else If TotalCum >= MaxSeptageCum Then
''			SeptageFee = SeptageRate * MaxSeptageCum
''		Else
''			SeptageFee = SeptageRate * TotalCum
''		End If
'
'	GlobalVar.SeptageRatePerCuM = 0
'	GlobalVar.SeptageMinCuM = 0
'	GlobalVar.SeptageMaxCuM = 0
'	GlobalVar.SeptageRateType = ""
'	dSeptageRate = 0
'	
'	If HasSeptageFee = 1 Then
'		LogColor ($"Acct Classification: "$ & AcctClassification, Colors.Magenta)
'		GlobalVar.SeptageRateID = DBaseFunctions.GetSeptageRateID(AcctClassification, GlobalVar.BranchID)
'		LogColor ($"Septage Rate ID: "$ & GlobalVar.SeptageRateID, Colors.Cyan)
'		
'		If GlobalVar.SeptageRateID = 0 Then
'			WarningMsg($"NO SEPTAGE RATES FOUND!"$, $"Customer account classification has no septage rates found!"$ & CRLF & $"Please ask assistance to the Central Billing & IT Department regarding this."$)
'			Return
'		Else
'			DBaseFunctions.GetSeptageRateDetails(GlobalVar.BranchID, GlobalVar.SeptageRateID, AcctClassification)
'			If GlobalVar.SeptageRateType = "amount" Then
'				If TotalCum <= GlobalVar.SeptageMinCuM Then
'					SeptageFee = GlobalVar.SeptageRatePerCuM * GlobalVar.SeptageMinCuM
'					
'				Else If TotalCum >= GlobalVar.SeptageMaxCuM Then
'					SeptageFee = GlobalVar.SeptageRatePerCuM * GlobalVar.SeptageMaxCuM
'					
'				Else
'					SeptageFee = GlobalVar.SeptageRatePerCuM * TotalCum
'				End If
'			Else
'				SeptageFee = (GlobalVar.SeptageRatePerCuM/100) * CurrentAmt
'			End If
'		End If
'	Else
'		SeptageFee = 0
'	End If
	
	LogColor($"Septage Rate: "$ & SeptageRate, Colors.Cyan)
	SeptageFeeAmt = SeptageFee
'
'	'Septage Fee Computation
'	If HasSeptageFee = 1 Then
'		'////////////////// Waiting for confirmation ///////////////////////////////
'		If GlobalVar.BranchID = 15 Or GlobalVar.BranchID = 43 Then 'Bamban and Dapdap only
'			MinSeptageCum = 10
'		End If
'		'//////////////////////////////////////////////////////////////////////////
'		If TotalCum <= MinSeptageCum Then
'			SeptageFee = SeptageRate * MinSeptageCum
'		Else If TotalCum > MaxSeptageCum Then
'			SeptageFee = SeptageRate * MaxSeptageCum
'		Else
'			SeptageFee = SeptageRate * TotalCum
'		End If
'	Else
'		SeptageFee = 0
'	End If
'	
'	SeptageFeeAmt = SeptageFee
	
	'Round Off **********************************************
	bSeptageFee.Initialize(SeptageFee)
	bSeptageFee = RoundtoCurrency(bSeptageFee, 2)
	sSeptageFee = NumberFormat2(bSeptageFee.ToPlainString, 1, 2, 2, False)
	LogColor($"ORIG SEPTAGE AND SEWERAGE: "$ & sSeptageFee, Colors.Yellow)

	bBasicAmt.Initialize(BasicAmt)
	bBasicAmt = RoundtoCurrency(bBasicAmt, 2)
	sBasicAmt = NumberFormat2(bBasicAmt.ToPlainString, 1, 2, 2, False)
	LogColor($"Basic: "$ & sBasicAmt, Colors.White)

	bPCAAmt.Initialize(PCAAmt)
	bPCAAmt = RoundtoCurrency(bPCAAmt, 2)
	sPCAAmt = NumberFormat2(bPCAAmt.ToPlainString, 1, 2, 2, False)
	LogColor($"PCA: "$ & sPCAAmt, Colors.White)
	
	bCurrentAmt.Initialize(CurrentAmt)
	bCurrentAmt = RoundtoCurrency(bCurrentAmt, 2)
	sCurrentAmt = NumberFormat2(bCurrentAmt.ToPlainString, 1, 2, 2, False)
	LogColor($"CURENT BILL: "$ & sCurrentAmt, Colors.White)
	
	bSeniorOnBeforeAmt.Initialize(SeniorOnBeforeAmt)
	bSeniorOnBeforeAmt = RoundtoCurrency(bSeniorOnBeforeAmt, 2)
	sSeniorOnBeforeAmt = NumberFormat2(bSeniorOnBeforeAmt.ToPlainString, 1, 2, 2, False)
	LogColor($"SC DISC: "$ & sSeniorOnBeforeAmt, Colors.White)
	
	bSeniorAfterAmt.Initialize(SeniorAfterAmt)
	bSeniorAfterAmt = RoundtoCurrency(bSeniorAfterAmt, 2)
	sSeniorAfterAmt = NumberFormat2(bSeniorAfterAmt.ToPlainString, 1, 2, 2, False)
	LogColor($"SENIOR DISC BEFORE: "$ & sSeniorAfterAmt, Colors.Yellow)
	
	bTotalDueAmtBeforeSC.Initialize(TotalDueAmtBeforeSC)
	bTotalDueAmtBeforeSC = RoundtoCurrency(bTotalDueAmtBeforeSC, 2)
	sTotalDueAmtBeforeSC = NumberFormat2(bTotalDueAmtBeforeSC.ToPlainString, 1, 2, 2, False)
	LogColor($"TOTAL DUE BEFORE DUE: "$ & sTotalDueAmtBeforeSC, Colors.YELLOW)
	
	bDiscAmt.Initialize(DiscAmt)
	bDiscAmt = RoundtoCurrency(bDiscAmt, 2)
	sDiscAmt = NumberFormat2(bDiscAmt.ToPlainString, 1, 2, 2, False)
	LogColor($"DISCOUNT: "$ & sDiscAmt, Colors.White)
	
	bMeterCharges.Initialize(MeterCharges)
	bMeterCharges = RoundtoCurrency(bMeterCharges, 2)
	sMeterCharges = NumberFormat2(bMeterCharges.ToPlainString, 1, 2, 2, False)
	LogColor($"METER CHARGE: "$ & sMeterCharges, Colors.Yellow)
	
	bFranchiseTaxAmt.Initialize(FranchiseTaxAmt)
	bFranchiseTaxAmt = RoundtoCurrency(bFranchiseTaxAmt, 2)
	sFranchiseTaxAmt = NumberFormat2(bFranchiseTaxAmt.ToPlainString, 1, 2, 2, False)
	LogColor($"FRANCHISE TAX: "$ & sFranchiseTaxAmt, Colors.Yellow)
	
	'Computation for Total Due Amount**********************************************
'	If GlobalVar.BranchID = 4 Or GlobalVar.BranchID = 12 Then
'		bNewAmt.Initialize((sCurrentAmt + AddToBillAmt + ArrearsAmt + sMeterCharges + sFranchiseTaxAmt) - (sSeniorOnBeforeAmt + sDiscAmt))
'	Else
'		bNewAmt.Initialize((sCurrentAmt + AddToBillAmt + ArrearsAmt + sMeterCharges + sFranchiseTaxAmt + sSeptageFeeAmt) - (sSeniorOnBeforeAmt + sDiscAmt))
'	End If
	
	'REMOVED SEPTAGE FEE
	bNewAmt.Initialize((sCurrentAmt + AddToBillAmt + ArrearsAmt + sMeterCharges + sFranchiseTaxAmt) - (sSeniorOnBeforeAmt + sDiscAmt))
	bNewAmt = RoundtoCurrency(bNewAmt,2)
	sNewAmt =  NumberFormat2(bNewAmt.ToPlainString, 1, 2, 2, False)
	
	LogColor(sNewAmt,Colors.Yellow)
	
	If (sNewAmt - AdvPayment) < 0 Then
		TotalDueAmt = 0
	Else
		TotalDueAmt = sNewAmt - AdvPayment
	End If
	
	If (AdvPayment - sNewAmt) <= 0 Then
		RemainingAdvPayment = 0
	Else
		RemainingAdvPayment = AdvPayment - sNewAmt
	End If
	
	If RemainingAdvPayment <= SeptageFeeAmt Then
		SeptageFeeAmt = SeptageFeeAmt - RemainingAdvPayment
	Else
		SeptageFeeAmt = 0
	End If
	
	bSeptageFeeAmt.Initialize(SeptageFeeAmt)
	bSeptageFeeAmt = RoundtoCurrency(bSeptageFeeAmt, 2)
	sSeptageFeeAmt = NumberFormat2(bSeptageFeeAmt.ToPlainString, 1, 2, 2, False)
	LogColor($"SEPTAGE AND SEWERAGE: "$ & sSeptageFeeAmt, Colors.White)

	'TOTAL AMOUNT BEFORE DUE WITHOUT SEPTAGE FEE
	bTotalDueAmt.Initialize(TotalDueAmt)
	bTotalDueAmt = RoundtoCurrency(bTotalDueAmt, 2)
	sTotalDueAmt = NumberFormat2(bTotalDueAmt.ToPlainString, 1, 2, 2, False)
	LogColor($"TOTAL DUE: "$ & sTotalDueAmt, Colors.White)
	
	If AdvPayment >= CurrentAmt Then
		PenaltyAmt = 0
	Else
		PenaltyAmt = CurrentAmt * PenaltyPct
	End If

	bPenaltyAmt.Initialize(PenaltyAmt)
	bPenaltyAmt = RoundtoCurrency(bPenaltyAmt, 2)
	sPenaltyAmt = NumberFormat2(bPenaltyAmt.ToPlainString, 1, 2, 2, False)
	LogColor($"PENALTY: "$ & sPenaltyAmt, Colors.White)

	'AFTER DUE COMPUTATION
	If ((sCurrentAmt + AddToBillAmt + ArrearsAmt + sMeterCharges + sFranchiseTaxAmt) - (sSeniorAfterAmt + sDiscAmt)) < AdvPayment Then
		TotalDueAmtAfterSC = 0
	Else
		TotalDueAmtAfterSC = sCurrentAmt + AddToBillAmt + ArrearsAmt + sMeterCharges + sFranchiseTaxAmt - (AdvPayment + sSeniorAfterAmt + sDiscAmt) + sPenaltyAmt
	End If

	bTotalDueAmtAfterSC.Initialize(TotalDueAmtAfterSC)
	bTotalDueAmtAfterSC = RoundtoCurrency(bTotalDueAmtAfterSC, 2)
	sTotalDueAmtAfterSC = NumberFormat2(bTotalDueAmtAfterSC.ToPlainString, 1, 2, 2, False)
	LogColor($"TOTAL AMT. AFTER DUE: "$ & sTotalDueAmtAfterSC, Colors.White)
	
	'End of Computation *********************************************************

	Starter.DBCon.BeginTransaction
	Try
		If blnEdit = True Then
			Starter.strCriteria = "UPDATE tblReadings " & _
							  "SET PresRdg = ?, PresCum = ?, TotalCum = ?, BillType =?, BasicAmt = ?, PCAAmt = ?, SeptageFeeAmt = ?, SeptageAmt = ?, " & _
							  "CurrentAmt = ?, TotalDueAmt = ?, SeniorOnBeforeAmt = ?, SeniorAfterAmt = ?, TotalDueAmtBeforeSC = ?, PenaltyAmt = ?, TotalDueAmtAfterSC = ?, DiscAmt = ?, FranchiseTaxAmt = ?, " & _
							  "PrintStatus = ?, NoPrinted = ?, WasMissCoded = ?, MissCodeID = ?, MissCode = ?, WasImplosive = ?, ImplosiveType = ?, ImpID = ?, FFindings = ?, FWarnings = ?, ReadRemarks = ?, " & _
							  "ReadRemarks = ?, WasRead = ?, NoInput = ?, TimeRead = ?, DateRead = ? " & _
							  "WHERE ReadID = " & iReadID & " " & _
							  "AND AcctID = " & iAcctID
		
			Starter.DBCon.ExecNonQuery2(Starter.strCriteria, Array As String(PresAveRdg, PresAveCum, TotalCum, $"AB"$, sBasicAmt, sPCAAmt, sSeptageFee, sSeptageFeeAmt,  _
							  sCurrentAmt, sTotalDueAmt, sSeniorOnBeforeAmt, sSeniorAfterAmt, sTotalDueAmtBeforeSC, sPenaltyAmt, sTotalDueAmtAfterSC, sDiscAmt, sFranchiseTaxAmt, _
							  PrintStatus, NoPrinted, $"0"$, $"0"$, $""$, $"0"$, ImplosiveType, $"0"$, $"Customer's Average Consumption"$, sWarning, strReadRemarks, _
							  sAveRem, $"1"$, NoInput, TimeRead, DateRead))

			snack.Initialize("",Activity, $"Reading has been successfully updated."$, snack.DURATION_LONG)
			SetSnackBarBackground(snack, GlobalVar.PriColor)
			SetSnackBarTextColor(snack, Colors.White)
			snack.Show
			blnEdit = False
		Else
			NewSeqNo = DBaseFunctions.GetSeqNo(GlobalVar.BookID)
			Starter.strCriteria = "UPDATE tblReadings " & _
							  "SET OrigRdg = ?, PresRdg = ?, PresCum = ?, TotalCum = ?, BillType =?, BasicAmt = ?, PCAAmt = ?, SeptageAmt = ?, SeptageFeeAmt = ?, " & _
							  "CurrentAmt = ?, TotalDueAmt = ?, SeniorOnBeforeAmt = ?, SeniorAfterAmt = ?, TotalDueAmtBeforeSC = ?, PenaltyAmt = ?, TotalDueAmtAfterSC = ?, DiscAmt = ?, FranchiseTaxAmt = ?, " & _
							  "PrintStatus = ?, NoPrinted = ?, WasMissCoded = ?, MissCodeID = ?, MissCode = ?, WasImplosive = ?, ImplosiveType = ?, ImpID = ?, FFindings = ?, FWarnings = ?, ReadRemarks = ?, " & _
							  "ReadRemarks = ?, WasRead = ?, NewSeqNo = ?, NoInput = ?, TimeRead = ?, DateRead = ? " & _
							  "WHERE ReadID = " & iReadID & " " & _
							  "AND AcctID = " & iAcctID
		
			Starter.DBCon.ExecNonQuery2(Starter.strCriteria, Array As String(PresAveRdg, PresAveRdg, PresAveCum, TotalCum, $"AB"$, sBasicAmt, sPCAAmt, sSeptageFee, sSeptageFeeAmt,  _
							  sCurrentAmt, sTotalDueAmt, sSeniorOnBeforeAmt, sSeniorAfterAmt, sTotalDueAmtBeforeSC, sPenaltyAmt, sTotalDueAmtAfterSC, sDiscAmt, sFranchiseTaxAmt, _
							  PrintStatus, NoPrinted, $"0"$, $"0"$, $""$, $"0"$, ImplosiveType, $"0"$, $"Customer's Average Consumption"$, sWarning, strReadRemarks, _
							  sAveRem, $"1"$, NewSeqNo, NoInput, TimeRead, DateRead))
			
			snack.Initialize("",Activity, $"New Reading has been successfully posted."$, snack.DURATION_LONG)
			SetSnackBarBackground(snack, GlobalVar.PriColor)
			SetSnackBarTextColor(snack, Colors.White)
			snack.Show
		End If
		Starter.DBCon.TransactionSuccessful
	Catch
		Log(LastException.Message)
	End Try
	Starter.DBCon.EndTransaction
	blnEdit = False
End Sub

Private Sub UpdatePrintStatus(iReadID As Int, iAcctID As Int)
	Starter.DBCon.BeginTransaction
	Try
		Starter.strCriteria = "UPDATE tblReadings " & _
							  "SET PrintStatus = ? " & _
							  "WHERE ReadID = " & iReadID & " " & _
							  "AND AcctID = " & iAcctID
		Starter.DBCon.ExecNonQuery2(Starter.strCriteria, Array As String(1))
		Starter.DBCon.TransactionSuccessful
	Catch
		Log(LastException.Message)
	End Try
	Starter.DBCon.EndTransaction
End Sub

Private Sub GetImplosiveType(iPrevCum As Int, iPresCum As Int) As String
	Dim sRetVal As String
	Try
		If iPresCum = 0 Then
			sRetVal = "zero"
		Else If iPresCum < 0 Then
			sRetVal = "negative"
		Else If (iPresCum - iPrevCum) >= 20 Then
			sRetVal = "implosive-inc"
		Else If (iPrevCum - iPresCum) >= 20 Then
			sRetVal = "implosive-dec"
		Else
			sRetVal = "posted"
		End If
	Catch
		ToastMessageShow($"Unable to get Implosive Type due to "$ & LastException.Message, False)
		Log(LastException)
	End Try
	Return sRetVal
End Sub

Private Sub GetWarning(iPrevCum As Int, iPresCum As Int) As String
	Dim sRetVal As String
	Try
		If iPresCum = 0 Then
			sRetVal = "Zero Consumption"
		Else If iPresCum < 0 Then
			sRetVal = "Negative Consumption"
		Else If (iPresCum - iPrevCum) >= 20 Then
			sRetVal = "High Bill"
		Else If (iPrevCum - iPresCum) >= 20 Then
			sRetVal = "Low Bill"
		Else
			sRetVal = "NONE"
		End If
	Catch
		ToastMessageShow($"Unable to get Implosive Type due to "$ & LastException.Message, False)
		Log(LastException)
	End Try
	Return sRetVal
End Sub

#End Region

#Region Computation

Sub ComputeCumUsed() As Long
	Dim RetVal As Long
	If Not(IsNumber(txtPresRdg.Text)) Or txtPresRdg.Text.Length<=0 Then
		RetVal= 0
	Else
		RetVal = strSF.Val(txtPresRdg.Text) - PrevRdg
	End If
	Return RetVal
End Sub

Private Sub GetSeniorBefore(iReadID As Int, dTotCum As Double) As Double
	Dim rsSCBefore As Cursor

	Dim dBillAmt As Double
	Dim dMaxCum As Double
	Dim fPct As Double
	Dim RetSeniorDiscount As Double
	Dim maxPCAAmount = 0 As Double
	
	Try
		Starter.strCriteria = "SELECT * FROM tblReadings " & _
							  "WHERE ReadID = " & iReadID
		
		rsSCBefore = Starter.DBCon.ExecQuery(Starter.strCriteria)
		If rsSCBefore.RowCount > 0 Then
			rsSCBefore.Position = 0
			fPct = rsSCBefore.GetDouble("SeniorOnBefore")
			dMaxCum = rsSCBefore.GetDouble("SeniorMaxCum")
			
			maxPCAAmount = dMaxCum * PCA
			
			If PCAAmt > maxPCAAmount Then
				dBillAmt = (DBaseFunctions.ComputeBasicAmt(dMaxCum, GlobalVar.BranchID, AcctClass, AcctSubClass)) + maxPCAAmount
			Else If PCAAmt = 0 Then
				If TotalCum > dMaxCum Then
					dBillAmt = (DBaseFunctions.ComputeBasicAmt(dMaxCum, GlobalVar.BranchID, AcctClass, AcctSubClass))
				Else
					dBillAmt = CurrentAmt
				End If
			Else
				dBillAmt = CurrentAmt
			End If
			RetSeniorDiscount = dBillAmt * fPct
		End If
	Catch
		ToastMessageShow($"Unable to get Senior Discount Before Due Date due to "$ & LastException.Message, False)
		Log(LastException.Message)
	End Try
	rsSCBefore.Close
	Return RetSeniorDiscount
End Sub

Private Sub GetSeniorAfter(iReadID As Int, dTotCum As Double) As Double
	Dim rsSCAfter As Cursor
	
	Dim dBillAmt As Double
	Dim dMaxCum As Double
	Dim fPct As Double
	Dim RetSeniorDiscount As Double
	Dim maxPCAAmount = 0 As Double
	Try
		Starter.strCriteria = "SELECT * FROM tblReadings " & _
							  "WHERE ReadID = " & iReadID
		
		rsSCAfter = Starter.DBCon.ExecQuery(Starter.strCriteria)
		If rsSCAfter.RowCount > 0 Then
			rsSCAfter.Position=0
			fPct = rsSCAfter.GetDouble("SeniorAfter")
			dMaxCum = rsSCAfter.GetDouble("SeniorMaxCum")
		
			maxPCAAmount = dMaxCum * PCA
			
			If PCAAmt > maxPCAAmount Then
				dBillAmt = (DBaseFunctions.ComputeBasicAmt(dMaxCum, GlobalVar.BranchID, AcctClass, AcctSubClass)) + maxPCAAmount
			Else If PCAAmt = 0 Then
				If TotalCum > dMaxCum Then
					dBillAmt = (DBaseFunctions.ComputeBasicAmt(dMaxCum, GlobalVar.BranchID, AcctClass, AcctSubClass))
				Else
					dBillAmt = CurrentAmt
				End If
			Else
				dBillAmt = CurrentAmt
			End If
			RetSeniorDiscount = dBillAmt * fPct
		End If
	Catch
		ToastMessageShow($"Unable to get Senior Discount After Due Date due to "$ & LastException.Message, False)
	End Try
	rsSCAfter.Close
	Return RetSeniorDiscount
End Sub

#End Region

#Region Negative Consumption
Sub opt0_CheckedChange(Checked As Boolean)
	LogColor(Checked,Colors.Magenta)
	If Checked = True Then
		txtOthers.Enabled = False
		txtOthers.Text = ""
	End If
End Sub

Sub opt1_CheckedChange(Checked As Boolean)
	LogColor(Checked,Colors.Magenta)
	If Checked = True Then
		txtOthers.Enabled = False
		txtOthers.Text = ""
	End If
End Sub

Sub opt2_CheckedChange(Checked As Boolean)
	LogColor(Checked,Colors.Magenta)
	If Checked = True Then
		txtOthers.Enabled = False
		txtOthers.Text = ""
	End If
End Sub

Sub opt3_CheckedChange(Checked As Boolean)
	LogColor(Checked,Colors.Magenta)
	If Checked = True Then
		txtOthers.Enabled = False
		txtOthers.Text = ""
	End If
End Sub

Sub opt4_CheckedChange(Checked As Boolean)
	LogColor(Checked,Colors.Magenta)
	If Checked = True Then
		txtOthers.Enabled = False
		txtOthers.Text = ""
	End If
End Sub

Sub opt5_CheckedChange(Checked As Boolean)
	LogColor(Checked,Colors.Magenta)
	If Checked = True Then
		txtOthers.Enabled = False
		txtOthers.Text = ""
	End If
End Sub

Sub opt6_CheckedChange(Checked As Boolean)
	LogColor(Checked,Colors.Magenta)
	If Checked = True Then
		txtOthers.Enabled = False
		txtOthers.Text = ""
	End If
End Sub

Sub opt7_CheckedChange(Checked As Boolean)
	LogColor(Checked,Colors.Magenta)
	If Checked = True Then
		txtOthers.Enabled = False
		txtOthers.Text = ""
	End If
End Sub

Sub opt8_CheckedChange(Checked As Boolean)
	LogColor(Checked,Colors.Magenta)
	If Checked = True Then
		txtOthers.Enabled = True
		txtOthers.RequestFocus
		IMEkeyboard.ShowKeyboard(txtOthers)
	End If
End Sub

Sub pnlNegativeMsgBox_Touch (Action As Int, X As Float, Y As Float)
	
End Sub

Private Sub ShowNegativeReadingDialog
	Dim sMsg = $"WARNING! Negative Reading Detected, Please Check the Water Meter"$ As String

	soundsAlarmChannel.Play(SoundID,1,1,1,0,1)

	cdButtonCancel.Initialize(0xFFDC143C, 25)
	btnNegativeCancelPost.Background = cdButtonCancel

	cdButtonSaveAndPrint.Initialize(0xFF1976D2, 25)
	btnNegativeSelect.Background = cdButtonSaveAndPrint

	cdTxtBox.Initialize2(Colors.White, 0, 0, Colors.Transparent)
	txtOthers.Background = cdTxtBox

	pnlNegativeMsgBox.Visible = True
	txtOthers.Text = ""
	opt0.Checked = False
	opt1.Checked = False
	opt2.Checked = False
	opt3.Checked = True
	opt4.Checked = False
	opt5.Checked = False
	opt6.Checked = False
	opt7.Checked = False
	opt8.Checked = False
	
	If  Not(TalkBackIsActive) Then
		TTS1.Speak(sMsg, False)
	End If
End Sub

Private Sub btnNegativeSelect_Click
	If opt0.Checked = True Then
		MisCodeID = 1
		MisCodeDesc = opt0.Text
	Else If opt1.Checked = True Then
		MisCodeID = 2
		MisCodeDesc = opt1.Text
	Else If opt2.Checked = True Then
		MisCodeID = 3
		MisCodeDesc = opt2.Text
	Else If opt3.Checked = True Then
		MisCodeID = 4
		MisCodeDesc = opt3.Text
	Else If opt4.Checked = True Then
		MisCodeID = 5
		MisCodeDesc = opt4.Text
	Else If opt5.Checked = True Then
		MisCodeID = 6
		MisCodeDesc = opt5.Text
	Else If opt6.Checked = True Then
		MisCodeID = 7
		MisCodeDesc = opt6.Text
	Else If opt7.Checked = True Then
		MisCodeID = 8
		MisCodeDesc = opt7.Text
	Else If opt8.Checked = True Then
		If txtOthers.Text = "" Or GlobalVar.SF.Len(GlobalVar.SF.Trim(txtOthers.Text)) <= 0 Then
			If DispErrorMsg($"Please enter other reason to post."$, $"E R R O R"$) = True Then
				txtOthers.Enabled = True
				txtOthers.RequestFocus
				IMEkeyboard.ShowKeyboard(txtOthers)
				Return
			End If
		Else
			MisCodeID = 9
			MisCodeDesc = txtOthers.Text
		End If
	Else
		Return
	End If
	pnlNegativeMsgBox.Visible = False
	vibration.vibrateCancel
	soundsAlarmChannel.Stop(SoundID)
	
	intCurrPos = rsLoadedBook.Position
	TTS1.Stop

	SaveNegativeReading(MisCodeID, MisCodeDesc,ReadID, AcctID)
	vibration.vibrateCancel
	pnlNegativeMsgBox.Visible = False

	If RetrieveRecords(GlobalVar.BookID)=False Then Return
	If intCurrPos < (RecCount - 1) Then
		rsLoadedBook.Position = intCurrPos + 1
	Else
		rsLoadedBook.Position = intCurrPos
	End If
	DisplayRecord
	ButtonState
	If GetUnusualCount(GlobalVar.BookID) = True Then
		UpdateBadge("Flag", AddBadgeToIcon(flagBitmap, intTotal))
	End If
End Sub

Sub btnNegativeCancelPost_Click
	TTS1.Stop
	soundsAlarmChannel.Stop(SoundID)
	vibration.vibrateCancel
	pnlNegativeMsgBox.Visible = False
	txtOthers.Text = ""
	opt0.Checked = False
	opt1.Checked = False
	opt2.Checked = False
	opt3.Checked = True
	opt4.Checked = False
	opt5.Checked = False
	opt6.Checked = False
	opt7.Checked = False
	opt8.Checked = False

	snack.Initialize("", Activity, $"Posting Reading Cancelled."$ ,snack.DURATION_SHORT)
	SetSnackBarBackground(snack, Colors.White)
	SetSnackBarTextColor(snack, Colors.Red)
	snack.Show
	rsLoadedBook.Position= intCurrPos
	DisplayRecord
	ButtonState
End Sub

Private Sub SaveNegativeReading(iMissCodeID As Int, sMissCodeDesc As String, iReadID As Int, iAcctID As Int)
	Dim lngDateTime As Long
	Dim sFindings As String
	Dim sWarning As String

	lngDateTime = DateTime.Now
	DateTime.TimeFormat = "HH:mm:ss"
	TimeRead = DateTime.Time(lngDateTime)
	DateTime.DateFormat = "MM/dd/yyyy"
	DateRead = DateTime.Date(lngDateTime)

	NoInput = NoInput + 1
	sFindings = "Negative Consumption"
	sWarning = "Negative Consumption"
	
	Try
		Starter.DBCon.BeginTransaction
		If blnEdit = True Then
			Starter.strCriteria="UPDATE tblReadings " & _
							"SET WasMissCoded = ?, MissCodeID = ?, MissCode = ?, fFindings = ?, fWarnings = ?, OrigRdg = ?, PresRdg = ?, " & _
							"WasRead = ?, NoInput = ?, TimeRead = ?, DateRead = ? " & _
							"WHERE ReadID = " & iReadID & " " & _
							"And AcctID = " & iAcctID

			Starter.DBCon.ExecNonQuery2(Starter.strCriteria, Array As String($"1"$, iMissCodeID, sMissCodeDesc, sFindings, sWarning, txtPresRdg.Text, txtPresRdg.Text, _
							 $"1"$, NoInput, TimeRead, DateRead))

			snack.Initialize("", Activity, $"Miss Code Entry was successfully updated"$, snack.DURATION_SHORT)
			SetSnackBarBackground(snack, GlobalVar.PriColor)
			SetSnackBarTextColor(snack, Colors.White)
			snack.Show
			blnEdit = False
		Else
			NewSeqNo = DBaseFunctions.GetSeqNo(GlobalVar.BookID)
			Starter.strCriteria="UPDATE tblReadings " & _
							"SET WasMissCoded = ?, MissCodeID = ?, MissCode = ?, fFindings = ?, fWarnings = ?, OrigRdg = ?, PresRdg = ?, MissCoded = ?, " & _
							"WasRead = ?, NewSeqNo = ?, NoInput = ?, TimeRead = ?, DateRead = ? " & _
							"WHERE ReadID = " & iReadID & " " & _
							"And AcctID = " & iAcctID

			Starter.DBCon.ExecNonQuery2(Starter.strCriteria, Array As String($"1"$, iMissCodeID, sMissCodeDesc, sFindings, sWarning, txtPresRdg.Text, txtPresRdg.Text, $"1"$, _
							$"1"$, NewSeqNo, NoInput, TimeRead, DateRead))
			snack.Initialize("", Activity, $"Miss Code Entry was successfully saved!"$, snack.DURATION_SHORT)
			SetSnackBarBackground(snack, GlobalVar.PriColor)
			SetSnackBarTextColor(snack, Colors.White)
			snack.Show
		End If
		Starter.DBCon.TransactionSuccessful
	Catch
		Log(LastException)
	End Try
	Starter.DBCon.EndTransaction
End Sub

#End Region

#Region Zero Consumption
Private Sub ShowSaveZeroReadingDialog
	Dim sMsg = $"WARNING! Zero Consumption Detected, Please Check the Water Meter"$ As String

	soundsAlarmChannel.Play(SoundID,1,1,1,0,1)

	cdButtonCancel.Initialize(0xFFDC143C, 25)
	btnZeroCancelPost.Background = cdButtonCancel

	cdButtonSaveOnly.Initialize(0xFF14C0DB, 25)
	btnZeroSaveOnly.Background = cdButtonSaveOnly

	cdButtonSaveAndPrint.Initialize(0xFF1976D2, 25)
	btnZeroSaveAndPrint.Background = cdButtonSaveAndPrint
	
	cdTxtBox.Initialize2(Colors.White, 0, 0, Colors.Transparent)
	txtRemarks.Background = cdTxtBox
	txtRemarks.Text = ""
	strReadRemarks = ""
	txtRemarks.RequestFocus
	IMEkeyboard.ShowKeyboard(txtRemarks)

	pnlZeroMsgBox.Visible = True
	btnZeroSaveOnly.Enabled = False
	btnZeroSaveAndPrint.Enabled = False
	If  Not(TalkBackIsActive) Then
		TTS1.Speak(sMsg, False)
	End If
End Sub

Sub pnlZeroMsgBox_Touch (Action As Int, X As Float, Y As Float)
	
End Sub

Sub txtRemarks_EnterPressed
	If txtRemarks.Text = "" Or GlobalVar.SF.Len(GlobalVar.SF.Trim(txtRemarks.Text)) <= 0 Then Return
End Sub

Sub txtRemarks_TextChanged (Old As String, New As String)
	If GlobalVar.SF.Len(GlobalVar.SF.Trim(txtRemarks.Text)) <= 0 Then
		btnZeroSaveOnly.Enabled = False
		btnZeroSaveAndPrint.Enabled = False
	Else
		btnZeroSaveOnly.Enabled = True
		btnZeroSaveAndPrint.Enabled = True
	End If
End Sub

Sub btnZeroSaveAndPrint_Click
	If txtRemarks.Text = "" Or GlobalVar.SF.Len(GlobalVar.SF.Trim(txtRemarks.Text)) <= 0 Then
		snack.Initialize("", Activity, $"Remarks cannot be blank!"$, snack.DURATION_LONG)
		SetSnackBarBackground(snack, Colors.Red)
		SetSnackBarTextColor(snack, Colors.White)
		snack.Show
		
		Return
	End If

	pnlZeroMsgBox.Visible = False
	vibration.vibrateCancel
	soundsAlarmChannel.Stop(SoundID)
	IMEkeyboard.HideKeyboard
	strReadRemarks = txtRemarks.Text
	
	intCurrPos = rsLoadedBook.Position
	TTS1.Stop

	SaveReading(1, ReadID, AcctID)
	PrintBillData(ReadID)
	If RetrieveRecords(GlobalVar.BookID)=False Then Return
	If intCurrPos < (RecCount - 1) Then
		rsLoadedBook.Position = intCurrPos + 1
	Else
		rsLoadedBook.Position = intCurrPos
	End If
	DisplayRecord
	ButtonState
	If GetUnusualCount(GlobalVar.BookID) = True Then
		UpdateBadge("Flag", AddBadgeToIcon(flagBitmap, intTotal))
	End If

End Sub

Sub btnZeroSaveOnly_Click
	If txtRemarks.Text = "" Or GlobalVar.SF.Len(GlobalVar.SF.Trim(txtRemarks.Text)) <= 0 Then
		snack.Initialize("", Activity, $"Remarks cannot be blank!"$, snack.DURATION_LONG)
		SetSnackBarBackground(snack, Colors.Red)
		SetSnackBarTextColor(snack, Colors.White)
		snack.Show
		Return
	End If

	pnlZeroMsgBox.Visible = False
	vibration.vibrateCancel
	soundsAlarmChannel.Stop(SoundID)
	IMEkeyboard.HideKeyboard
	strReadRemarks = txtRemarks.Text
	TTS1.Stop

	SaveReading(0, ReadID, AcctID)
	If RetrieveRecords(GlobalVar.BookID)=False Then Return
	If intCurrPos < (RecCount - 1) Then
		rsLoadedBook.Position = intCurrPos + 1
	Else
		rsLoadedBook.Position = intCurrPos
	End If
	DisplayRecord
	ButtonState
	If GetUnusualCount(GlobalVar.BookID) = True Then
		UpdateBadge("Flag", AddBadgeToIcon(flagBitmap, intTotal))
	End If
End Sub

Sub btnZeroCancelPost_Click
	TTS1.Stop
	soundsAlarmChannel.Stop(SoundID)
	vibration.vibrateCancel
	pnlZeroMsgBox.Visible = False
	txtRemarks.Text = ""
	strReadRemarks = ""

	snack.Initialize("", Activity, $"Adding Reading Remarks Cancelled."$ ,snack.DURATION_SHORT)
	SetSnackBarBackground(snack, Colors.White)
	SetSnackBarTextColor(snack, Colors.Red)
	snack.Show
	vibration.vibrateCancel
	rsLoadedBook.Position= intCurrPos
	IMEkeyboard.HideKeyboard
	DisplayRecord
	ButtonState
End Sub


#End Region

#Region Printing
Private Sub PrintBillData(iReadID As Int)
	Dim rsData As Cursor
	Dim sDisconDate As String
	
	Dim dDisconDate As Long
	Dim sGDeposit As BigDecimal
	Dim sAddToBillAmt As BigDecimal
	Dim sArrearsAmt As BigDecimal
	Dim sAdvPayment As BigDecimal
	Dim dSeptageTotal As Double
	Dim sReadDate As String
	Dim sReadTime As String
	Dim lReadTime As Long
	
	
	Dim REVERSE As String = Chr(29) & "B" & Chr(1)
	Dim UNREVERSE As String = Chr(29) & "B" & Chr(0)
	Dim FULLCUT As String = Chr(29) & "V" & Chr(1)
'	DateTime.DateFormat = "MM/dd/yyyy"
	' Hiyas
'	sDisconDate = DateTime.GetMonth(DateTime.Now) & "/20/" & DateTime.GetYear(DateTime.Now)
'	sDisconDate = dDisconDate
	ProgressDialogShow2($"Bill Statement Printing..."$, False)

	Try
		Starter.strCriteria = "SELECT * FROM tblReadings WHERE ReadID = " & iReadID & " " & _
						  "AND BookID = " & GlobalVar.BookID & " " & _
						  "AND BillYear = " & GlobalVar.BillYear & " " & _
						  "AND BillMonth = " & GlobalVar.BillMonth
		rsData = Starter.DBCon.ExecQuery(Starter.strCriteria)

		If rsData.RowCount > 0 Then
			rsData.Position = 0
			PresRdg = rsData.GetInt("PresRdg")
			If strSF.Len(strSF.Trim(PresRdg)) = 0 Then
				rsData.Close
				Return
			End If
			AcctNo = rsData.GetString("AcctNo")
			AcctName = rsData.GetString("AcctName")
			AcctAddress = rsData.GetString("AcctAddress")
			AcctStatus = rsData.GetString("AcctStatus")
			
			MeterNo = rsData.GetString("MeterNo")
			BookID = rsData.GetInt("BookID")
			BookSeq = rsData.GetString("BookNo") & "-" & rsData.GetString("SeqNo")
			CustClass = rsData.GetString("AcctClass") & "-" & rsData.GetString("AcctSubClass")
			GDeposit = Round2(rsData.GetDouble("GDeposit"),2)
			BillNo = rsData.GetString("BillNo")
			BillDate = rsData.GetString("DateRead")
			

			PrevRdg = rsData.GetInt("PrevRdg")
			AddCons = rsData.GetInt("AddCons")
			TotalCum = rsData.GetInt("TotalCum")

			BasicAmt =Round2(rsData.GetDouble("BasicAmt"),2)
			PCAAmt = Round2(rsData.GetDouble("PCAAmt"),2)
			CurrentAmt = Round2(rsData.GetDouble("CurrentAmt"),2)
			SeniorOnBeforeAmt = Round2(rsData.GetDouble("SeniorOnBeforeAmt"),2)
			AddToBillAmt = Round2(rsData.GetDouble("AddToBillAmt"),2)'Others
			MeterCharges = Round2(rsData.GetDouble("MeterCharges"), 2)
			FranchiseTaxAmt = Round2(rsData.GetDouble("FranchiseTaxAmt"), 2)
			SeptageFeeAmt = Round2(rsData.GetDouble("SeptageFeeAmt"), 2)
			SeptageFee = Round2(rsData.GetDouble("SeptageAmt"), 2)
			SeptageArrears = Round2(rsData.GetDouble("SeptageArrears"), 2)
			
			ArrearsAmt = Round2(rsData.GetDouble("ArrearsAmt"), 2)
			AdvPayment = Round2(rsData.GetDouble("AdvPayment"), 2)
			TotalDueAmt = Round2(rsData.GetDouble("TotalDueAmt"), 2)
			TotalDueAmtBeforeSC = Round2(rsData.GetDouble("TotalDueAmtBeforeSC"), 2)
			iHasSeptage = rsData.GetInt("HasSeptageFee")
			dSeptageRate = rsData.GetDouble("SeptageRate")
			
			WithDueDate = rsData.GetInt("WithDueDate")
'			//apalit only
'			If WithDueDate = 1 Then
'				DueDate = rsData.GetString("DueDate")
'			Else
'				DueDate = ""
'			End If
			
			PenaltyAmt = Round2(rsData.GetDouble("PenaltyAmt"), 2)
			SeniorAfterAmt = Round2(rsData.GetDouble("SeniorAfterAmt"), 2)
			TotalDueAmtAfterSC = Round2(rsData.GetDouble("TotalDueAmtAfterSC"), 2)
			DiscAmt2 = Round2(rsData.GetDouble("DiscAmt"), 2)

			'//////////////////////////////////////////////////////////////////////////////////////////////////////
'			If AcctStatus = "dc" Then
'				DateFrom = rsData.GetString("DateFrom")
'				DateTo = rsData.GetString("DisconDate")
'			Else
'				DateFrom = rsData.GetString("DateFrom")
'				DateTo = rsData.GetString("DateRead")
'			End If
			'//////////////////////////////////////////////////////////////////////////////////////////////////////

'			If AcctStatus = "dc" Then
'				DateFrom = rsData.GetString("DateFrom")
'				DateTo = rsData.GetString("DisconDate")
'			Else

			DateFrom = rsData.GetString("DateFrom")
			DateTo = rsData.GetString("DateRead")
			BillPeriod1st = rsData.GetString("BillPeriod1st")
			BillPeriod2nd = rsData.GetString("BillPeriod2nd")
			BillPeriod3rd = rsData.GetString("BillPeriod3rd")
			PrevCum1st = rsData.GetInt("PrevCum1st")
			PrevCum2nd = rsData.GetInt("PrevCum2nd")
			PrevCum3rd = rsData.GetInt("PrevCum3rd")
			DisconnectionDate = rsData.GetString("DisconnectionDate")

			sReadDate = rsData.GetString("DateRead")
			sReadTime=  rsData.GetString("TimeRead")
'			DateTime.TimeFormat = "hh:mm:ss"
'			lReadTime = DateTime.TimeParse(sReadTime)
'			LogColor(lReadTime, Colors.Yellow)
			
'			End If
			
		End If
		
'		If GlobalVar.BranchID = 1  Then 'GARDEN VILLAS
'			sBranchName = $"GARDEN VILLAS"$ & CRLF & $"BRANCH"$
'		Else If GlobalVar.BranchID = 37  Then 'ALFONSO CASTANEDA
'			sBranchName = $"A. CASTANEDA"$ & CRLF & $"BRANCH"$
'		Else If GlobalVar.BranchID = 45  Then 'MARIA AURORA
'			sBranchName = $"MARIA AURORA"$ & CRLF & $"BRANCH"$
'		Else If GlobalVar.BranchID = 69  Then 'GARDEN VILLAS
'			sBranchName = $"DASMARIÑAS"$ & CRLF & $"BRANCH"$
'		Else If GlobalVar.BranchID = 56 Or GlobalVar.BranchID = 9  Then 'SDRH Sto Domingo
'			sBranchName = $"SANTO DOMINGO"$ & CRLF & $"BRANCH"$

		If GlobalVar.BranchID = 42 Or GlobalVar.BranchID = 48 Then 'Capas and Tela
			sBranchName = $"BWSI - CLPI"$ & CRLF & $"BRANCHES INC."$
		Else If GlobalVar.BranchID = 76 Then 'LAPID'S VILLE
			sBranchName = $"LAPIDSVILLE"$
		Else
			sBranchName = strSF.Upper(strSF.Right(GlobalVar.BranchName, GlobalVar.BranchName.Length - 7))
		End If
		
		If GlobalVar.BranchID = 1 Then 'Garden Villas
			sBranchAddress = GlobalVar.BranchAddress & CRLF & Chr(27) & "!" & Chr(1) & "Brgy. Ibaba, Sta. Rosa, Laguna"
		Else If GlobalVar.BranchID = 5 Then 'Main Branch
			sBranchAddress = GlobalVar.BranchAddress & CRLF & Chr(27) & "!" & Chr(1) & "Balibago, Angeles City"
		Else If GlobalVar.BranchID = 19 Then 'Porac
			sBranchAddress = GlobalVar.BranchAddress & CRLF & Chr(27) & "!" & Chr(1) & "Porac, Pampanga"
		Else If GlobalVar.BranchID = 25 Then 'Balagtas
			sBranchAddress = GlobalVar.BranchAddress & CRLF & Chr(27) & "!" & Chr(1) & "Barangay Wawa, Balagtas, Bulacan"
		Else If GlobalVar.BranchID = 28 Or GlobalVar.BranchID = 29 Or  GlobalVar.BranchID = 30 Then 'Guiguinto Branches
			sBranchAddress = GlobalVar.BranchAddress & CRLF & Chr(27) & "!" & Chr(1) & "Guiguinto, Bulacan"
		Else If GlobalVar.BranchID = 32 Or GlobalVar.BranchID = 33 Or GlobalVar.BranchID = 34 Or GlobalVar.BranchID = 35 Then 'Pandi Branches
			sBranchAddress = GlobalVar.BranchAddress & CRLF & Chr(27) & "!" & Chr(1) & "Pandi, Bulacan"
		Else If GlobalVar.BranchID = 36 Then 'Paombong
			sBranchAddress = GlobalVar.BranchAddress & CRLF & Chr(27) & "!" & Chr(1) & "Poblacion, Paombong, Bulacan"
		Else If GlobalVar.BranchID = 37 Then 'Alfonso Castaneda
			sBranchAddress = GlobalVar.BranchAddress & CRLF & Chr(27) & "!" & Chr(1) & "Nueva Vizcaya"
		Else If GlobalVar.BranchID = 40 Then 'Bagabag
			sBranchAddress = GlobalVar.BranchAddress & CRLF & Chr(27) & "!" & Chr(1) & "Bagabag, Nueva Vizcaya"
		Else If GlobalVar.BranchID = 53 Then 'Passi
			sBranchAddress = GlobalVar.BranchAddress & CRLF & Chr(27) & "!" & Chr(1) & "Passi City, Iloilo"
		Else If GlobalVar.BranchID = 66 Then 'Mati
			sBranchAddress = GlobalVar.BranchAddress & CRLF & Chr(27) & "!" & Chr(1) & "Davao Oriental"
		Else If GlobalVar.BranchID = 67 Then 'Villa Lois
			sBranchAddress = GlobalVar.BranchAddress & CRLF & Chr(27) & "!" & Chr(1) & "Brgy. Siling Bata, Pandi, Bulacan"
		Else If GlobalVar.BranchID = 68 Then 'San Miguel Bulacan
			sBranchAddress = GlobalVar.BranchAddress & CRLF & Chr(27) & "!" & Chr(1) & "San Miguel, Bulacan"
		Else If GlobalVar.BranchID = 70 Then 'Gen Trias
			sBranchAddress = GlobalVar.BranchAddress & CRLF & Chr(27) & "!" & Chr(1) & "Richwood Townhomes, Navarro, Gen Trias (C)"
		Else If GlobalVar.BranchID = 72 Then 'Barotac
			sBranchAddress = GlobalVar.BranchAddress & CRLF & Chr(27) & "!" & Chr(1) & "Poblacion, Barotac Viejo, Iloilo"
		Else If GlobalVar.BranchID = 77 Then 'Calinog
			sBranchAddress = GlobalVar.BranchAddress & CRLF & Chr(27) & "!" & Chr(1) & "Calinog, Iloilo"
		Else If GlobalVar.BranchID = 83 Then 'Angeles East
			sBranchAddress = GlobalVar.BranchAddress & CRLF & Chr(27) & "!" & Chr(1) & "Angeles City"
		Else If GlobalVar.BranchID = 84 Then 'Angeles West
			sBranchAddress = GlobalVar.BranchAddress & CRLF & Chr(27) & "!" & Chr(1) & "Cuayan, Angeles City"
		Else If GlobalVar.BranchID = 85 Then 'San Ildefonso
			sBranchAddress = GlobalVar.BranchAddress & CRLF & Chr(27) & "!" & Chr(1) & "San Ildefonso, Bulacan"
		Else If GlobalVar.BranchID = 86 Then 'Palayan City
			sBranchAddress = GlobalVar.BranchAddress & CRLF & Chr(27) & "!" & Chr(1) & "Palayan City, Nueva Ecija"
		Else If GlobalVar.BranchID = 88 Then 'San Pascual
			sBranchAddress = GlobalVar.BranchAddress & CRLF & Chr(27) & "!" & Chr(1) & "San Pascual, Batangas"
		Else If GlobalVar.BranchID = 91 Then 'CDO
			sBranchAddress = GlobalVar.BranchAddress & CRLF & Chr(27) & "!" & Chr(1) & "Angeles City"
		Else
			sBranchAddress = GlobalVar.BranchAddress
		End If
		'Cuayan, Angeles City
		If GlobalVar.BranchID = 76 Then
			sBranchContactNo = ""
		Else
			sBranchContactNo = GlobalVar.BranchContactNo
		End If
		
'		sBranchContactNo = GlobalVar.BranchContactNo
		
		LogColor(strSF.Len(strSF.Trim(GlobalVar.BranchTINumber)),Colors.Cyan)
		
		If strSF.Len(strSF.Trim(GlobalVar.BranchTINumber)) > 0 Then
			sTinNo = $"TIN NO: "$ & GlobalVar.BranchTINumber
		Else
			sTinNo = $""$
		End If
		
		sBranchCode = GlobalVar.BranchCode
		sBillPeriod = GlobalVar.BillPeriod
		
		'to String
		sPresRdg = PresRdg
		sPrevRdg = PrevRdg
		sAddCons = AddCons
		sTotalCum = TotalCum

		
		sGDeposit.Initialize(GDeposit)
		sGDeposit = RoundtoCurrency(sGDeposit,2)
		
'		sBasicAmt = BasicAmt
'		sPCAAmt = PCAAmt
'		sCurrentAmt = CurrentAmt
'		sDiscAmt = DiscAmt2
'		sSeniorOnBeforeAmt = SeniorOnBeforeAmt
		sAddToBillAmt.Initialize(AddToBillAmt)'Others
		sAddToBillAmt = RoundtoCurrency(sAddToBillAmt,2)
		
		sArrearsAmt.Initialize(ArrearsAmt)
		sArrearsAmt = RoundtoCurrency(sArrearsAmt,2)
		
		sAdvPayment.Initialize(AdvPayment)
		sAdvPayment = RoundtoCurrency(sAdvPayment,2)
		
'		sTotalDueAmt = TotalDueAmt
'		sPenaltyAmt = PenaltyAmt
'		sSeniorAfterAmt = SeniorAfterAmt
'		sTotalDueAmtAfterSC = TotalDueAmtAfterSC
'		sMeterCharges = MeterCharges
'		sFranchiseTaxAmt = FranchiseTaxAmt
'		sSeptageFeeAmt = SeptageFeeAmt

'		& Chr(27) & "!" & Chr(1) & "Guiguinto, Bulacan" & Chr(10) _

		'/////////// Lapids Ville
		If GlobalVar.BranchID = 91 Then 'CDO - Remove Contact No and TIN
			PrintBuffer = Chr(27) & "@" _
				& Chr(27) & Chr(97) & Chr(49) _
				& Chr(27) & "!" & Chr(33) & sBranchName & Chr(10) _
				& Chr(27) & "!" & Chr(1) & Chr(27) & "t" & Chr(14) & sBranchAddress & Chr(10) _
				& Chr(27) & "!" & Chr(8) & "Branch Code: " & sBranchCode & CRLF & Chr(10) _
				& Chr(27) & "!" & Chr(33) & "STATEMENT OF ACCOUNT" & Chr(10) _
				& Chr(27) & "!" & Chr(1) & "For the Month of " & sBillPeriod & Chr(10) _
				& Chr(27) & "!" & Chr(1) & "------------------------------------------" & Chr(10) _
				& Chr(27) & Chr(97) & Chr(48) _
				& Chr(27) & "!" & Chr(16) & "Account No: " & AcctNo  & Chr(10) _
				& Chr(27) & "!" & Chr(16) & Chr(27) & "t" & Chr(16) & strSF.Upper(AcctName) & Chr(10) _
				& Chr(27) & "!" & Chr(1) & Chr(27) & "t" & Chr(16) & strSF.Left(TitleCase(AcctAddress),42) & Chr(10) _
				& Chr(27) & "!" & Chr(16) & "Meter No: " & MeterNo &  Chr(10) _
				& Chr(27) & "!" & Chr(8) & "Book-Seq: " & BookSeq &  Chr(10) _
				& Chr(27) & "!" & Chr(0) & "Class   : " & CustClass &  Chr(10) _
				& "Guarantee Deposit " & AddWhiteSpaces2(NumberFormat2(sGDeposit,1,2,2,True)) & NumberFormat2(sGDeposit,1,2,2,True) &  Chr(10) _
				& Chr(27) & "!" & Chr(1) & "------------------------------------------" & Chr(10) _
				& Chr(27) & "!" & Chr(8) & "Bill Number       " & AddWhiteSpaces2(BillNo) & BillNo  &  Chr(10) _
				& Chr(27) & "!" & Chr(0) & "Bill Date         " & AddWhiteSpaces2(BillDate) & BillDate  &  Chr(10) _
				& "Period From       " & AddWhiteSpaces2(DateFrom) & DateFrom  &  Chr(10) _
				& "Period To         " & AddWhiteSpaces2(DateTo) & DateTo  &  Chr(10) _
				& Chr(27) & "!" & Chr(1) & "------------------------------------------" & Chr(10) _
				& Chr(27) & "!" & Chr(0) & "Pres. Reading      " & AddWhiteSpaces(sPresRdg) & sPresRdg &  Chr(10) _
				& Chr(27) & "!" & Chr(0) & "Prev. Reading      " & AddWhiteSpaces(sPrevRdg) & sPrevRdg &  Chr(10) _
				& Chr(27) & "!" & Chr(0) & "Add Cons.          " & AddWhiteSpaces(sAddCons) & sAddCons & Chr(10) _
				& Chr(27) & "!" & Chr(0) & "Total Cum          " & AddWhiteSpaces(sTotalCum) & sTotalCum  & Chr(10) _
				& Chr(27) & "!" & Chr(1) & "------------------------------------------" & Chr(10) _
				& Chr(27) & "!" & Chr(0) & "BASIC              " & AddWhiteSpaces(NumberFormat2(BasicAmt,1, 2, 2,True)) & NumberFormat2(BasicAmt,1, 2, 2,True) &  Chr(10) _
				& Chr(27) & "!" & Chr(0) & "PCA                " & AddWhiteSpaces(NumberFormat2(PCAAmt,1, 2, 2,True)) & NumberFormat2(PCAAmt,1, 2, 2,True) &  Chr(10) _
				& Chr(27) & "!" & Chr(16) & "CURRENT BILL       " & AddWhiteSpaces(NumberFormat2(CurrentAmt,1, 2, 2,True)) & NumberFormat2(CurrentAmt,1, 2, 2,True) &  Chr(10) _
				& Chr(27) & "!" & Chr(0) & "SR. CITIZEN DISC.  " & AddWhiteSpaces("(" & NumberFormat2(SeniorOnBeforeAmt,1, 2, 2,True) & ")") & "(" & NumberFormat2(SeniorOnBeforeAmt,1, 2, 2,True) & ")" &  Chr(10)					
		
		Else
		PrintBuffer = Chr(27) & "@" _
				& Chr(27) & Chr(97) & Chr(49) _
				& Chr(27) & "!" & Chr(33) & sBranchName & Chr(10) _
				& Chr(27) & "!" & Chr(1) & Chr(27) & "t" & Chr(14) & sBranchAddress & Chr(10) _
				& Chr(27) & "!" & Chr(1) & sBranchContactNo & Chr(10) _
				& Chr(27) & "!" & Chr(1) & sTinNo & Chr(10) _
				& Chr(27) & "!" & Chr(8) & "Branch Code: " & sBranchCode & CRLF & Chr(10) _
				& Chr(27) & "!" & Chr(33) & "STATEMENT OF ACCOUNT" & Chr(10) _
				& Chr(27) & "!" & Chr(1) & "For the Month of " & sBillPeriod & Chr(10) _
				& Chr(27) & "!" & Chr(1) & "------------------------------------------" & Chr(10) _
				& Chr(27) & Chr(97) & Chr(48) _
				& Chr(27) & "!" & Chr(16) & "Account No: " & AcctNo  & Chr(10) _
				& Chr(27) & "!" & Chr(16) & Chr(27) & "t" & Chr(16) & strSF.Upper(AcctName) & Chr(10) _
				& Chr(27) & "!" & Chr(1) & Chr(27) & "t" & Chr(16) & strSF.Left(TitleCase(AcctAddress),42) & Chr(10) _
				& Chr(27) & "!" & Chr(16) & "Meter No: " & MeterNo &  Chr(10) _
				& Chr(27) & "!" & Chr(8) & "Book-Seq: " & BookSeq &  Chr(10) _
				& Chr(27) & "!" & Chr(0) & "Class   : " & CustClass &  Chr(10) _
				& "Guarantee Deposit " & AddWhiteSpaces2(NumberFormat2(sGDeposit,1,2,2,True)) & NumberFormat2(sGDeposit,1,2,2,True) &  Chr(10) _
				& Chr(27) & "!" & Chr(1) & "------------------------------------------" & Chr(10) _
				& Chr(27) & "!" & Chr(8) & "Bill Number       " & AddWhiteSpaces2(BillNo) & BillNo  &  Chr(10) _
				& Chr(27) & "!" & Chr(0) & "Bill Date         " & AddWhiteSpaces2(BillDate) & BillDate  &  Chr(10) _
				& "Period From       " & AddWhiteSpaces2(DateFrom) & DateFrom  &  Chr(10) _
				& "Period To         " & AddWhiteSpaces2(DateTo) & DateTo  &  Chr(10) _
				& Chr(27) & "!" & Chr(1) & "------------------------------------------" & Chr(10) _
				& Chr(27) & "!" & Chr(0) & "Pres. Reading      " & AddWhiteSpaces(sPresRdg) & sPresRdg &  Chr(10) _
				& Chr(27) & "!" & Chr(0) & "Prev. Reading      " & AddWhiteSpaces(sPrevRdg) & sPrevRdg &  Chr(10) _
				& Chr(27) & "!" & Chr(0) & "Add Cons.          " & AddWhiteSpaces(sAddCons) & sAddCons & Chr(10) _
				& Chr(27) & "!" & Chr(0) & "Total Cum          " & AddWhiteSpaces(sTotalCum) & sTotalCum  & Chr(10) _
				& Chr(27) & "!" & Chr(1) & "------------------------------------------" & Chr(10) _
				& Chr(27) & "!" & Chr(0) & "BASIC              " & AddWhiteSpaces(NumberFormat2(BasicAmt,1, 2, 2,True)) & NumberFormat2(BasicAmt,1, 2, 2,True) &  Chr(10) _
				& Chr(27) & "!" & Chr(0) & "PCA                " & AddWhiteSpaces(NumberFormat2(PCAAmt,1, 2, 2,True)) & NumberFormat2(PCAAmt,1, 2, 2,True) &  Chr(10) _
				& Chr(27) & "!" & Chr(16) & "CURRENT BILL       " & AddWhiteSpaces(NumberFormat2(CurrentAmt,1, 2, 2,True)) & NumberFormat2(CurrentAmt,1, 2, 2,True) &  Chr(10) _
				& Chr(27) & "!" & Chr(0) & "SR. CITIZEN DISC.  " & AddWhiteSpaces("(" & NumberFormat2(SeniorOnBeforeAmt,1, 2, 2,True) & ")") & "(" & NumberFormat2(SeniorOnBeforeAmt,1, 2, 2,True) & ")" &  Chr(10)					
		End If

'	/////// Other details /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		If DiscAmt2 > 0 Then
			PrintBuffer = PrintBuffer & Chr(27) & "!" & Chr(0) & "DISCOUNT           " & AddWhiteSpaces("(" & NumberFormat2(DiscAmt2,1, 2, 2,True) & ")") & "(" & NumberFormat2(DiscAmt2,1, 2, 2,True) & ")" &  Chr(10)
		End If
		
		PrintBuffer = PrintBuffer & Chr(27) & "!" & Chr(0) & "OTHERS             " & AddWhiteSpaces(NumberFormat2(sAddToBillAmt,1, 2, 2,True)) & NumberFormat2(sAddToBillAmt,1, 2, 2,True) &  Chr(10)

		If GlobalVar.WithMeterCharges = 1 Then
			PrintBuffer = PrintBuffer & "METER CHARGES      " & AddWhiteSpaces(NumberFormat2(MeterCharges,1, 2, 2,True)) & NumberFormat2(MeterCharges,1, 2, 2,True) &  Chr(10)
		End If

		If GlobalVar.WithFranchisedTax = 1 Then
			PrintBuffer = PrintBuffer & "FRANCHISE TAX      " & AddWhiteSpaces(NumberFormat2(FranchiseTaxAmt,1, 2, 2,True)) & NumberFormat2(FranchiseTaxAmt,1, 2, 2,True) &  Chr(10)
		End If
		
		' //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
'		Add this line to implement Stopping of Septage Fee:
'		iHasSeptage = 0
		' /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		If iHasSeptage = 0 Or dSeptageRate <=0 Then
			iHasSeptage = 0
		End If
		
		'Remove this when finalized...
'		If GlobalVar.BranchID = 28 Or GlobalVar.BranchID = 29 Or GlobalVar.BranchID = 30 Then
'			iHasSeptage = 1
'		End If
		'up to here
		
		If iHasSeptage = 1 Then
			PrintBuffer = PrintBuffer & Chr(27) & "!" & Chr(0) & "ARREARS            " & AddWhiteSpaces(NumberFormat2(sArrearsAmt,1, 2, 2,True)) & NumberFormat2(sArrearsAmt,1, 2, 2,True) &  Chr(10) _
					& Chr(27) & "!" & Chr(0) & "ADVANCES           " & AddWhiteSpaces("(" & NumberFormat2(sAdvPayment,1, 2, 2,True) & ")") & "(" & NumberFormat2(sAdvPayment,1, 2, 2,True) & ")" &  Chr(10) _
					& Chr(27) & "!" & Chr(8) & "SUB-TOTAL          " & AddWhiteSpaces(NumberFormat2(TotalDueAmt,1, 2, 2,True)) & NumberFormat2(TotalDueAmt,1, 2, 2,True) & Chr(10) _
					& Chr(27) & Chr(97) & Chr(49) _
					& Chr(27) & "!" & Chr(1) & "==========================================" & Chr(10) _
					& Chr(27) & "!" & Chr(8) & "PAYMENT AFTER DUE DATE" & Chr(10) _
					& Chr(27) & Chr(97) & Chr(48) _
					& Chr(27) & "!" & Chr(0) & "DUE DATE           " & AddWhiteSpaces(DueDate) & DueDate &  Chr(10) _
					& Chr(27) & "!" & Chr(0) & "PENALTY            " & AddWhiteSpaces(NumberFormat2(PenaltyAmt,1, 2, 2,True)) & NumberFormat2(PenaltyAmt,1, 2, 2,True) &  Chr(10) _
					& Chr(27) & "!" & Chr(0) & "SR. CITIZEN DISC.  " & AddWhiteSpaces("(" & NumberFormat2(SeniorAfterAmt,1, 2, 2,True) & ")") & "(" & NumberFormat2(SeniorAfterAmt,1, 2, 2,True) & ")" &  Chr(10) _
					& Chr(27) & "!" & Chr(8) & "SUB-TOTAL AFTER DUE" & AddWhiteSpaces(NumberFormat2(TotalDueAmtAfterSC,1, 2, 2,True)) & NumberFormat2(TotalDueAmtAfterSC,1, 2, 2,True) & Chr(10) _
					& Chr(27) & "!" & Chr(1) & "==========================================" & Chr(10)
		Else ' No Septage Fee
			PrintBuffer = PrintBuffer & Chr(27) & "!" & Chr(0) & "ARREARS            " & AddWhiteSpaces(NumberFormat2(sArrearsAmt,1, 2, 2,True)) & NumberFormat2(sArrearsAmt,1, 2, 2,True) &  Chr(10) _
					& Chr(27) & "!" & Chr(0) & "ADVANCES           " & AddWhiteSpaces("(" & NumberFormat2(sAdvPayment,1, 2, 2,True) & ")") & "(" & NumberFormat2(sAdvPayment,1, 2, 2,True) & ")" &  Chr(10) _
					& Chr(27) & "!" & Chr(16) & "TOTAL DUE          " & AddWhiteSpaces(NumberFormat2(TotalDueAmt,1, 2, 2,True)) & NumberFormat2(TotalDueAmt,1, 2, 2,True) & Chr(10) _
					& Chr(27) & Chr(97) & Chr(49) _
					& Chr(27) & "!" & Chr(1) & "==========================================" & Chr(10) _
					& Chr(27) & "!" & Chr(8) & "PAYMENT AFTER DUE DATE" & Chr(10) _
					& Chr(27) & Chr(97) & Chr(48) _
					& Chr(27) & "!" & Chr(0) & "DUE DATE           " & AddWhiteSpaces(DueDate) & DueDate &  Chr(10) _
					& Chr(27) & "!" & Chr(0) & "PENALTY            " & AddWhiteSpaces(NumberFormat2(PenaltyAmt,1, 2, 2,True)) & NumberFormat2(PenaltyAmt,1, 2, 2,True) &  Chr(10) _
					& Chr(27) & "!" & Chr(0) & "SR. CITIZEN DISC.  " & AddWhiteSpaces("(" & NumberFormat2(SeniorAfterAmt,1, 2, 2,True) & ")") & "(" & NumberFormat2(SeniorAfterAmt,1, 2, 2,True) & ")" &  Chr(10) _
					& Chr(27) & "!" & Chr(16) & "TOTAL AMT AFTER DUE" & AddWhiteSpaces(NumberFormat2(TotalDueAmtAfterSC,1, 2, 2,True)) & NumberFormat2(TotalDueAmtAfterSC,1, 2, 2,True) & Chr(10) _
					& Chr(27) & "!" & Chr(1) & "==========================================" & Chr(10)
		End If

'	////////////////////////////////// Consumption History //////////////////////////////////
		If GlobalVar.SF.Len(BillPeriod3rd) > 0 Then
			PrintBuffer = PrintBuffer & Chr(27) & Chr(97) & Chr(49) _
					& Chr(27) & "!" & Chr(8) & "CONSUMPTION HISTORY" & Chr(10) _
					& Chr(27) & Chr(97) & Chr(48) _
					& Chr(27) & "!" & Chr(0) & BillPeriod1st & $"           "$ & AddWhiteSpaces(PrevCum1st & $" CuM."$) & PrevCum1st & $" CuM."$ &  Chr(10) _
					& Chr(27) & "!" & Chr(0) & BillPeriod2nd & $"           "$ & AddWhiteSpaces(PrevCum2nd & $" CuM."$) & PrevCum2nd & $" CuM."$ &  Chr(10) _
					& Chr(27) & "!" & Chr(0) & BillPeriod3rd & $"           "$ & AddWhiteSpaces(PrevCum3rd & $" CuM."$) & PrevCum3rd & $" CuM."$ &  Chr(10) _
					& Chr(27) & "!" & Chr(1) & "==========================================" & Chr(10)
		Else If GlobalVar.SF.Len(BillPeriod2nd) > 0 Then
			PrintBuffer = PrintBuffer & Chr(27) & Chr(97) & Chr(49) _
					& Chr(27) & "!" & Chr(8) & "CONSUMPTION HISTORY" & Chr(10) _
					& Chr(27) & Chr(97) & Chr(48) _
					& Chr(27) & "!" & Chr(0) & BillPeriod1st & $"           "$ & AddWhiteSpaces(PrevCum1st & $" CuM."$) & PrevCum1st & $" CuM."$ &  Chr(10) _
					& Chr(27) & "!" & Chr(0) & BillPeriod2nd & $"           "$ & AddWhiteSpaces(PrevCum2nd & $" CuM."$) & PrevCum2nd & $" CuM."$ &  Chr(10) _
					& Chr(27) & "!" & Chr(1) & "==========================================" & Chr(10)
		Else If GlobalVar.SF.Len(BillPeriod1st) > 0 Then
			PrintBuffer = PrintBuffer & Chr(27) & Chr(97) & Chr(49) _
					& Chr(27) & "!" & Chr(8) & "CONSUMPTION HISTORY" & Chr(10) _
					& Chr(27) & Chr(97) & Chr(48) _
					& Chr(27) & "!" & Chr(0) & BillPeriod1st & $"           "$ & AddWhiteSpaces(PrevCum1st & $" CuM."$) & PrevCum1st & $" CuM."$ &  Chr(10) _
					& Chr(27) & "!" & Chr(1) & "==========================================" & Chr(10)
		End If
'	////////////////////////////////////////////////////////////////////////////////////////
		
			
			If (ArrearsAmt > 0) Or (TotalDueAmt >= GDeposit) Then
				PrintBuffer = PrintBuffer & Chr(27) & Chr(97) & Chr(49) _
					& Chr(27) & "!" & Chr(33) & Chr(2) & Chr(10) _
					& Chr(27) & "!" & Chr(16) & "NOTICE OF DISCONNECTION" & Chr(10) _
					& Chr(27) & Chr(97) & Chr(48) _
					& Chr(27) & "!" & Chr(8) & "DISCONNECTION DATE:" & AddWhiteSpaces(DisconnectionDate) & DisconnectionDate & CRLF & Chr(10) 
			End If

			PrintBuffer = PrintBuffer & Chr(27) & Chr(97) & Chr(48) _
					& Chr(27) & "!" & Chr(33) & Chr(2) _
					& REVERSE & " NOTE: " &  Chr(10) _
					& Chr(27) & "!" & Chr(1) & UNREVERSE & "    Disregard Arrears if Payment has been made. Please pay on or before the due date to avoid any inconveniences." & CRLF & Chr(10) _
					& Chr(27) & "!" & Chr(0) & "Reader: " & GlobalVar.SF.Upper(GlobalVar.EmpName) &  Chr(10) _
					& Chr(27) & "!" & Chr(0) & "Date & Time: " & sReadDate & " " & sReadTime  & CRLF&  CRLF & Chr(10) _
					& Chr(27) & Chr(97) & Chr(10) & FULLCUT

'////////////////////////////////// Announcement for Septage ////////////////////////////////////////////////

'			If (GlobalVar.BranchID = 15 Or GlobalVar.BranchID = 43 Or GlobalVar.BranchID = 55) And AcctClass = "RES" Then
'				If GlobalVar.BranchID = 74 And AcctClass = "RES" Then 'Gabaldon only
'					PrintBuffer = PrintBuffer & Chr(27) & Chr(97) & Chr(48) _
'					& Chr(27) & "!" & Chr(33) & Chr(2) _
'					& REVERSE & " NOTE: " &  Chr(10) _
'					& Chr(27) & "!" & Chr(1) & UNREVERSE & "    Disregard Arrears if Payment has been made. Please pay on or before the due date to avoid any inconveniences." & CRLF & Chr(10) _
'					& Chr(27) & "!" & Chr(0) & "Reader: " & GlobalVar.SF.Upper(GlobalVar.EmpName) &  Chr(10) _
'					& Chr(27) & "!" & Chr(0) & "Date & Time: " & sReadDate & " " & sReadTime  & CRLF & Chr(10) _
'					& Chr(27) & Chr(97) & Chr(49) _
'					& Chr(27) & "!" & Chr(33) & Chr(2) & Chr(10) _
'					& Chr(27) & "!" & Chr(16) & REVERSE & "   A N N O U N C E M E N T   " & Chr(10) _
'					& Chr(27) & Chr(97) & Chr(48) _
'					& Chr(27) & "!" & Chr(1) & UNREVERSE & "     Starting July 2023 Bill, there will" & Chr(10) _
'					& Chr(27) & "!" & Chr(1) & "be an additional charge of " & Chr(27) & "!" & Chr(8) & "P6.00/CuM" & Chr(27) & "!" & Chr(1) & " as" & Chr(10) _
'					& Chr(27) & "!" & Chr(33) & "Environmental Fee" & Chr(27) & "!" & Chr(1) & " for the" & Chr(10) _
'					& Chr(27) & "!" & Chr(1) & "implementation of " & Chr(27) & "!" & Chr(33) & "Clean Water Act of 2004." & CRLF&  CRLF & Chr(10) _
'					& Chr(27) & Chr(97) & Chr(49) &  Chr(10) & FULLCUT
					'Php4.00/cm3 Environmental Fee for the implementation of the Clean Water Act of 2004 shall be charge on your February Bill.
'				Else If GlobalVar.BranchID = 61 Then
'					If AcctClass = "RES" Then
'						PrintBuffer = PrintBuffer & Chr(27) & Chr(97) & Chr(48) _
'					& Chr(27) & "!" & Chr(33) & Chr(2) _
'					& REVERSE & " NOTE: " &  Chr(10) _
'					& Chr(27) & "!" & Chr(1) & UNREVERSE & "    Disregard Arrears if Payment has been made. Please pay on or before the due date to avoid any inconveniences." & CRLF & Chr(10) _
'					& Chr(27) & "!" & Chr(0) & "Reader: " & GlobalVar.SF.Upper(GlobalVar.EmpName) &  Chr(10) _
'					& Chr(27) & "!" & Chr(0) & "Date & Time: " & sReadDate & " " & sReadTime  & CRLF & Chr(10) _
'					& Chr(27) & Chr(97) & Chr(49) _
'					& Chr(27) & "!" & Chr(33) & Chr(2) & Chr(10) _
'					& Chr(27) & "!" & Chr(16) & "A N N O U N C E M E N T" & Chr(10) _
'					& Chr(27) & Chr(97) & Chr(48) _
'					& Chr(27) & "!" & Chr(1) & "     Starting April 2023 Bill, there will" & Chr(10) _
'					& Chr(27) & "!" & Chr(1) & "be an additional charge of " & Chr(27) & "!" & Chr(8) & "P4.50/CuM" & Chr(27) & "!" & Chr(1) & " as" & Chr(10) _
'					& Chr(27) & "!" & Chr(33) & "Environmental Fee" & Chr(27) & "!" & Chr(1) & " for the" & Chr(10) _
'					& Chr(27) & "!" & Chr(1) & "implementation of " & Chr(27) & "!" & Chr(33) & "Clean Water Act of 2004." & CRLF&  CRLF & Chr(10) _
'					& Chr(27) & Chr(97) & Chr(49) &  Chr(10) & FULLCUT				
'					Else If AcctClass = "COM" Then
'						PrintBuffer = PrintBuffer & Chr(27) & Chr(97) & Chr(48) _
'					& Chr(27) & "!" & Chr(33) & Chr(2) _
'					& REVERSE & " NOTE: " &  Chr(10) _
'					& Chr(27) & "!" & Chr(1) & UNREVERSE & "    Disregard Arrears if Payment has been made. Please pay on or before the due date to avoid any inconveniences." & CRLF & Chr(10) _
'					& Chr(27) & "!" & Chr(0) & "Reader: " & GlobalVar.SF.Upper(GlobalVar.EmpName) &  Chr(10) _
'					& Chr(27) & "!" & Chr(0) & "Date & Time: " & sReadDate & " " & sReadTime  & CRLF & Chr(10) _
'					& Chr(27) & Chr(97) & Chr(49) _
'					& Chr(27) & "!" & Chr(33) & Chr(2) & Chr(10) _
'					& Chr(27) & "!" & Chr(16) & "A N N O U N C E M E N T" & Chr(10) _
'					& Chr(27) & Chr(97) & Chr(48) _
'					& Chr(27) & "!" & Chr(1) & "     Starting April 2023 Bill, there will" & Chr(10) _
'					& Chr(27) & "!" & Chr(1) & "be an additional charge of " & Chr(27) & "!" & Chr(8) & "P9.50/CuM" & Chr(27) & "!" & Chr(1) & " as" & Chr(10) _
'					& Chr(27) & "!" & Chr(33) & "Environmental Fee" & Chr(27) & "!" & Chr(1) & " for the" & Chr(10) _
'					& Chr(27) & "!" & Chr(1) & "implementation of " & Chr(27) & "!" & Chr(33) & "Clean Water Act of 2004." & CRLF&  CRLF & Chr(10) _
'					& Chr(27) & Chr(97) & Chr(49) &  Chr(10) & FULLCUT
'					Else
'						PrintBuffer = PrintBuffer & Chr(27) & Chr(97) & Chr(48) _
'					& Chr(27) & "!" & Chr(33) & Chr(2) _
'					& REVERSE & " NOTE: " &  Chr(10) _
'					& Chr(27) & "!" & Chr(1) & UNREVERSE & "    Disregard Arrears if Payment has been made. Please pay on or before the due date to avoid any inconveniences." & CRLF & Chr(10) _
'					& Chr(27) & "!" & Chr(0) & "Reader: " & GlobalVar.SF.Upper(GlobalVar.EmpName) &  Chr(10) _
'					& Chr(27) & "!" & Chr(0) & "Date & Time: " & sReadDate & " " & sReadTime  & CRLF&  CRLF & Chr(10) _
'					& Chr(27) & Chr(97) & Chr(49) &  Chr(10) & FULLCUT
'					End If
'				Else
'				
'					PrintBuffer = PrintBuffer & Chr(27) & Chr(97) & Chr(48) _
'					& Chr(27) & "!" & Chr(33) & Chr(2) _
'					& REVERSE & " NOTE: " &  Chr(10) _
'					& Chr(27) & "!" & Chr(1) & UNREVERSE & "    Disregard Arrears if Payment has been made. Please pay on or before the due date to avoid any inconveniences." & CRLF & Chr(10) _
'					& Chr(27) & "!" & Chr(0) & "Reader: " & GlobalVar.SF.Upper(GlobalVar.EmpName) &  Chr(10) _
'					& Chr(27) & "!" & Chr(0) & "Date & Time: " & sReadDate & " " & sReadTime  & CRLF&  CRLF & Chr(10) _
'					& Chr(27) & Chr(97) & Chr(49) &  Chr(10) & FULLCUT
'				End If

'			PrintBuffer = PrintBuffer & Chr(27) & Chr(97) & Chr(48) _
'					& Chr(27) & "!" & Chr(33) & Chr(2) _
'					& REVERSE & " NOTE: " &  Chr(10) _
'					& Chr(27) & "!" & Chr(1) & UNREVERSE & "    Disregard Arrears if Payment has been made. Please pay on or before the due date to avoid any inconveniences." & CRLF & Chr(10) _
'					& Chr(27) & "!" & Chr(0) & "Reader: " & GlobalVar.SF.Upper(GlobalVar.EmpName) &  Chr(10) _
'					& Chr(27) & "!" & Chr(0) & "Date & Time: " & sReadDate & " " & sReadTime  & CRLF&  CRLF & Chr(10) _
'					& Chr(27) & Chr(97) & Chr(49) &  Chr(10) & FULLCUT

		'///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		If iHasSeptage = 1 Then 'Footer No Septage Fee
'		Else 'With Septage Fee
			
			GTotalDueAmt = 0
			GTotalDue = 0
			GTotalAfterDue = 0

			GTotalDueAmt = (CurrentAmt + AddToBillAmt + ArrearsAmt + MeterCharges + FranchiseTaxAmt) - (SeniorOnBeforeAmt + DiscAmt2)
			GTotalAfterDueAmt = (CurrentAmt + AddToBillAmt + ArrearsAmt + MeterCharges + FranchiseTaxAmt + PenaltyAmt) - (SeniorAfterAmt + DiscAmt2)
	
			LogColor(GTotalDueAmt,Colors.Yellow)
			GTotalSeptage = SeptageArrears + SeptageFee
	
			If ((GTotalDueAmt + GTotalSeptage) - AdvPayment) < 0 Then
				GTotalDue = 0
			Else
				GTotalDue = (GTotalDueAmt + GTotalSeptage) - AdvPayment
			End If
			
'			If AdvPayment > (GTotalDueAmt + SeptageFee + PenaltyAmt - SeniorAfterAmt) Then
			If AdvPayment > GTotalAfterDueAmt + GTotalSeptage Then
				GTotalAfterDue = 0
			Else
				GTotalAfterDue = (GTotalAfterDueAmt + GTotalSeptage) - AdvPayment
			End If
			
			LogColor($"Septage Fee: "$ & GTotalSeptage,Colors.Cyan)

'			& Chr(27) & "!" & Chr(33) & "ORDINANCE NO.: 343 S. of 2014" & Chr(10) _

			If GlobalVar.SeptageProv = 1 Then
				If GlobalVar.BranchID = 5 Then 'Main Branch Only
					sepBuffer = Chr(27) & "@" _
						& Chr(27) & Chr(97) & Chr(49) _
						& Chr(27) & "!" & Chr(33) & "STATEMENT OF ACCOUNT" & Chr(10) _
						& Chr(27) & "!" & Chr(1) & "For the Month of " & sBillPeriod & Chr(10) _
						& Chr(27) & "!" & Chr(1) & "------------------------------------------" & Chr(10) _
						& Chr(27) & Chr(97) & Chr(48) _
						& Chr(27) & "!" & Chr(16) & "Account No: " & AcctNo  & Chr(10) _
						& Chr(27) & "!" & Chr(16) & Chr(27) & "t" & Chr(16) & strSF.Upper(AcctName) & Chr(10) _
						& Chr(27) & "!" & Chr(0) & "Class   : " & CustClass &  Chr(10) _
						& Chr(27) & "!" & Chr(1) & "------------------------------------------" & Chr(10) _
						& Chr(27) & "!" & Chr(0) & "TOTAL CONSUMPTION  " & AddWhiteSpaces(sTotalCum) & sTotalCum &  Chr(10) _
						& Chr(27) & "!" & Chr(0) & "ENVIRONMENTAL FEE  " & AddWhiteSpaces(NumberFormat2(SeptageFee,1, 2, 2,True)) & NumberFormat2(SeptageFee,1, 2, 2,True) &  Chr(10) _
						& Chr(27) & Chr(97) & Chr(49) _
						& Chr(27) & "!" & Chr(8) & "================================" & Chr(10) _
						& Chr(27) & "!" & Chr(32) & "S U M M A R Y" & Chr(10) _
						& Chr(27) & "!" & Chr(8) & "================================" & Chr(10) _
						& Chr(27) & Chr(97) & Chr(48) _
						& Chr(27) & "!" & Chr(0) & "CURRENT BILL       " & AddWhiteSpaces(NumberFormat2(CurrentAmt,1, 2, 2,True)) & NumberFormat2(CurrentAmt,1, 2, 2,True) &  Chr(10) _
						& Chr(27) & "!" & Chr(0) & "SR. CITIZEN DISC.  " & AddWhiteSpaces("(" & NumberFormat2(SeniorOnBeforeAmt,1, 2, 2,True) & ")") & "(" & NumberFormat2(SeniorOnBeforeAmt,1, 2, 2,True) & ")" &  Chr(10)					

					If DiscAmt2 > 0 Then
						sepBuffer = sepBuffer & Chr(27) & "!" & Chr(0) & "DISCOUNT           " & AddWhiteSpaces("(" & NumberFormat2(DiscAmt2,1, 2, 2,True) & ")") & "(" & NumberFormat2(DiscAmt2,1, 2, 2,True) & ")" &  Chr(10)
					End If
		
					sepBuffer = sepBuffer & Chr(27) & "!" & Chr(0) & "OTHERS             " & AddWhiteSpaces(NumberFormat2(sAddToBillAmt,1, 2, 2,True)) & NumberFormat2(sAddToBillAmt,1, 2, 2,True) &  Chr(10)

					If GlobalVar.WithMeterCharges = 1 Then
						sepBuffer = sepBuffer & "METER CHARGES      " & AddWhiteSpaces(NumberFormat2(MeterCharges,1, 2, 2,True)) & NumberFormat2(MeterCharges,1, 2, 2,True) &  Chr(10)
					End If

					If GlobalVar.WithFranchisedTax = 1 Then
						sepBuffer = sepBuffer & "FRANCHISE TAX      " & AddWhiteSpaces(NumberFormat2(FranchiseTaxAmt,1, 2, 2,True)) & NumberFormat2(FranchiseTaxAmt,1, 2, 2,True) &  Chr(10)
					End If
				
					sepBuffer = sepBuffer & Chr(27) & "!" & Chr(8) & "WATER BILL         " & AddWhiteSpaces(NumberFormat2(GTotalDueAmt,1, 2, 2,True)) & NumberFormat2(GTotalDueAmt,1, 2, 2,True) &  Chr(10) _
						& Chr(27) & "!" & Chr(8) & "ENVIRONMENTAL FEE  " & AddWhiteSpaces(NumberFormat2(SeptageFee,1, 2, 2,True)) & NumberFormat2(SeptageFee,1, 2, 2,True) &  Chr(10) _
						& Chr(27) & "!" & Chr(0) & "ADVANCES           " & AddWhiteSpaces("(" & NumberFormat2(sAdvPayment,1, 2, 2,True) & ")") & "(" & NumberFormat2(sAdvPayment,1, 2, 2,True) & ")" &  Chr(10) _
						& Chr(27) & "!" & Chr(16) & "TOTAL DUE          " & AddWhiteSpaces(NumberFormat2(GTotalDue,1, 2, 2,True)) & NumberFormat2(GTotalDue,1, 2, 2,True) & Chr(10) _
						& Chr(27) & "!" & Chr(8) & "AMT. AFTER DUE DATE" & AddWhiteSpaces(NumberFormat2(GTotalAfterDue,1, 2, 2,True)) & NumberFormat2(GTotalAfterDue,1, 2, 2,True) & Chr(10) _
						& Chr(27) & "!" & Chr(1) & "==========================================" & CRLF & Chr(10)

				Else 'Soliman Septic Except Main

					sepBuffer = Chr(27) & "@" _
						& Chr(27) & Chr(97) & Chr(49) _
						& Chr(27) & "!" & Chr(33) & "STATEMENT OF ACCOUNT" & Chr(10) _
						& Chr(27) & "!" & Chr(1) & "For the Month of " & sBillPeriod & Chr(10) _
						& Chr(27) & "!" & Chr(1) & "------------------------------------------" & Chr(10) _
						& Chr(27) & Chr(97) & Chr(48) _
						& Chr(27) & "!" & Chr(16) & "Account No: " & AcctNo  & Chr(10) _
						& Chr(27) & "!" & Chr(16) & Chr(27) & "t" & Chr(16) & strSF.Upper(AcctName) & Chr(10) _
						& Chr(27) & "!" & Chr(0) & "Class   : " & CustClass &  Chr(10) _
						& Chr(27) & "!" & Chr(1) & "------------------------------------------" & Chr(10) _
						& Chr(27) & "!" & Chr(0) & "TOTAL CONSUMPTION  " & AddWhiteSpaces(sTotalCum) & sTotalCum &  Chr(10) _
						& Chr(27) & "!" & Chr(0) & "SEWERAGE AND       " & Chr(10) _
						& Chr(27) & "!" & Chr(0) & "SEPTAGE FEE        " & AddWhiteSpaces(NumberFormat2(SeptageFee,1, 2, 2,True)) & NumberFormat2(SeptageFee,1, 2, 2,True) &  Chr(10) _
						& Chr(27) & Chr(97) & Chr(49) _
						& Chr(27) & "!" & Chr(8) & "================================" & Chr(10) _
						& Chr(27) & "!" & Chr(32) & "S U M M A R Y" & Chr(10) _
						& Chr(27) & "!" & Chr(8) & "================================" & Chr(10) _
						& Chr(27) & Chr(97) & Chr(48) _
						& Chr(27) & "!" & Chr(0) & "CURRENT BILL       " & AddWhiteSpaces(NumberFormat2(CurrentAmt,1, 2, 2,True)) & NumberFormat2(CurrentAmt,1, 2, 2,True) &  Chr(10) _
						& Chr(27) & "!" & Chr(0) & "SR. CITIZEN DISC.  " & AddWhiteSpaces("(" & NumberFormat2(SeniorOnBeforeAmt,1, 2, 2,True) & ")") & "(" & NumberFormat2(SeniorOnBeforeAmt,1, 2, 2,True) & ")" &  Chr(10)					

					If DiscAmt2 > 0 Then
						sepBuffer = sepBuffer & Chr(27) & "!" & Chr(0) & "DISCOUNT           " & AddWhiteSpaces("(" & NumberFormat2(DiscAmt2,1, 2, 2,True) & ")") & "(" & NumberFormat2(DiscAmt2,1, 2, 2,True) & ")" &  Chr(10)
					End If
		
					sepBuffer = sepBuffer & Chr(27) & "!" & Chr(0) & "OTHERS             " & AddWhiteSpaces(NumberFormat2(sAddToBillAmt,1, 2, 2,True)) & NumberFormat2(sAddToBillAmt,1, 2, 2,True) &  Chr(10)

					If GlobalVar.WithMeterCharges = 1 Then
						sepBuffer = sepBuffer & "METER CHARGES      " & AddWhiteSpaces(NumberFormat2(MeterCharges,1, 2, 2,True)) & NumberFormat2(MeterCharges,1, 2, 2,True) &  Chr(10)
					End If

					If GlobalVar.WithFranchisedTax = 1 Then
						sepBuffer = sepBuffer & "FRANCHISE TAX      " & AddWhiteSpaces(NumberFormat2(FranchiseTaxAmt,1, 2, 2,True)) & NumberFormat2(FranchiseTaxAmt,1, 2, 2,True) &  Chr(10)
					End If
				
					sepBuffer = sepBuffer & Chr(27) & "!" & Chr(8) & "WATER BILL         " & AddWhiteSpaces(NumberFormat2(GTotalDueAmt,1, 2, 2,True)) & NumberFormat2(GTotalDueAmt,1, 2, 2,True) &  Chr(10) _
						& Chr(27) & "!" & Chr(8) & "SEPTAGE AND        " & Chr(10) _
						& Chr(27) & "!" & Chr(8) & "SEWERAGE FEE       " & AddWhiteSpaces(NumberFormat2(SeptageFee,1, 2, 2,True)) & NumberFormat2(SeptageFee,1, 2, 2,True) &  Chr(10) _
						& Chr(27) & "!" & Chr(0) & "ADVANCES           " & AddWhiteSpaces("(" & NumberFormat2(sAdvPayment,1, 2, 2,True) & ")") & "(" & NumberFormat2(sAdvPayment,1, 2, 2,True) & ")" &  Chr(10) _
						& Chr(27) & "!" & Chr(16) & "TOTAL DUE          " & AddWhiteSpaces(NumberFormat2(GTotalDue,1, 2, 2,True)) & NumberFormat2(GTotalDue,1, 2, 2,True) & Chr(10) _
						& Chr(27) & "!" & Chr(8) & "AMT. AFTER DUE DATE" & AddWhiteSpaces(NumberFormat2(GTotalAfterDue,1, 2, 2,True)) & NumberFormat2(GTotalAfterDue,1, 2, 2,True) & Chr(10) _
						& Chr(27) & "!" & Chr(1) & "==========================================" & CRLF & Chr(10)					
				End If
			Else If GlobalVar.SeptageProv = 2 Then
				If GlobalVar.BranchID = 9 Then 'Sto Domingo
					sepBuffer = Chr(27) & "@" _
							& Chr(27) & Chr(97) & Chr(49) _
							& Chr(27) & "!" & Chr(33) & "STATEMENT OF ACCOUNT" & Chr(10) _
							& Chr(27) & "!" & Chr(1) & "For the Month of " & sBillPeriod & Chr(10) _
							& Chr(27) & "!" & Chr(1) & "------------------------------------------" & Chr(10) _
							& Chr(27) & Chr(97) & Chr(48) _
							& Chr(27) & "!" & Chr(16) & "Account No: " & AcctNo  & Chr(10) _
							& Chr(27) & "!" & Chr(16) & Chr(27) & "t" & Chr(16) & strSF.Upper(AcctName) & Chr(10) _
							& Chr(27) & "!" & Chr(0) & "Class   : " & CustClass &  Chr(10) _
							& Chr(27) & "!" & Chr(1) & "------------------------------------------" & Chr(10) _
							& Chr(27) & "!" & Chr(0) & "TOTAL CONSUMPTION  " & AddWhiteSpaces(sTotalCum) & sTotalCum &  Chr(10) _
							& Chr(27) & "!" & Chr(0) & "SEPTAGE FEE        " & AddWhiteSpaces(NumberFormat2(SeptageFee,1, 2, 2,True)) & NumberFormat2(SeptageFee,1, 2, 2,True) &  Chr(10) _
							& Chr(27) & Chr(97) & Chr(49) _
							& Chr(27) & "!" & Chr(8) & "================================" & Chr(10) _
							& Chr(27) & "!" & Chr(32) & "S U M M A R Y" & Chr(10) _
							& Chr(27) & "!" & Chr(8) & "================================" & Chr(10) _
							& Chr(27) & Chr(97) & Chr(48) _
							& Chr(27) & "!" & Chr(0) & "CURRENT BILL       " & AddWhiteSpaces(NumberFormat2(CurrentAmt,1, 2, 2,True)) & NumberFormat2(CurrentAmt,1, 2, 2,True) &  Chr(10) _
							& Chr(27) & "!" & Chr(0) & "SR. CITIZEN DISC.  " & AddWhiteSpaces("(" & NumberFormat2(SeniorOnBeforeAmt,1, 2, 2,True) & ")") & "(" & NumberFormat2(SeniorOnBeforeAmt,1, 2, 2,True) & ")" &  Chr(10)					

					If DiscAmt2 > 0 Then
						sepBuffer = sepBuffer & Chr(27) & "!" & Chr(0) & "DISCOUNT           " & AddWhiteSpaces("(" & NumberFormat2(DiscAmt2,1, 2, 2,True) & ")") & "(" & NumberFormat2(DiscAmt2,1, 2, 2,True) & ")" &  Chr(10)
					End If
				
					sepBuffer = sepBuffer & Chr(27) & "!" & Chr(0) & "OTHERS             " & AddWhiteSpaces(NumberFormat2(sAddToBillAmt,1, 2, 2,True)) & NumberFormat2(sAddToBillAmt,1, 2, 2,True) &  Chr(10)

					If GlobalVar.WithMeterCharges = 1 Then
						sepBuffer = sepBuffer & "METER CHARGES      " & AddWhiteSpaces(NumberFormat2(MeterCharges,1, 2, 2,True)) & NumberFormat2(MeterCharges,1, 2, 2,True) &  Chr(10)
					End If

					If GlobalVar.WithFranchisedTax = 1 Then
						sepBuffer = sepBuffer & "FRANCHISE TAX      " & AddWhiteSpaces(NumberFormat2(FranchiseTaxAmt,1, 2, 2,True)) & NumberFormat2(FranchiseTaxAmt,1, 2, 2,True) &  Chr(10)
					End If
					
					sepBuffer = sepBuffer & Chr(27) & "!" & Chr(8) & "WATER BILL         " & AddWhiteSpaces(NumberFormat2(GTotalDueAmt,1, 2, 2,True)) & NumberFormat2(GTotalDueAmt,1, 2, 2,True) &  Chr(10) _
							& Chr(27) & "!" & Chr(8) & "SEPTAGE FEE        " & AddWhiteSpaces(NumberFormat2(SeptageFee,1, 2, 2,True)) & NumberFormat2(SeptageFee,1, 2, 2,True) &  Chr(10) _
							& Chr(27) & "!" & Chr(0) & "ADVANCES           " & AddWhiteSpaces("(" & NumberFormat2(sAdvPayment,1, 2, 2,True) & ")") & "(" & NumberFormat2(sAdvPayment,1, 2, 2,True) & ")" &  Chr(10) _
							& Chr(27) & "!" & Chr(16) & "TOTAL DUE          " & AddWhiteSpaces(NumberFormat2(GTotalDue,1, 2, 2,True)) & NumberFormat2(GTotalDue,1, 2, 2,True) & Chr(10) _
							& Chr(27) & "!" & Chr(8) & "AMT. AFTER DUE DATE" & AddWhiteSpaces(NumberFormat2(GTotalAfterDue,1, 2, 2,True)) & NumberFormat2(GTotalAfterDue,1, 2, 2,True) & Chr(10) _
							& Chr(27) & "!" & Chr(1) & "==========================================" & CRLF & Chr(10)
				
				Else 'Clean Liquid Except Sto Domingo
						
					sepBuffer = Chr(27) & "@" _
							& Chr(27) & Chr(97) & Chr(49) _
							& Chr(27) & "!" & Chr(33) & "STATEMENT OF ACCOUNT" & Chr(10) _
							& Chr(27) & "!" & Chr(1) & "For the Month of " & sBillPeriod & Chr(10) _
							& Chr(27) & "!" & Chr(1) & "------------------------------------------" & Chr(10) _
							& Chr(27) & Chr(97) & Chr(48) _
							& Chr(27) & "!" & Chr(16) & "Account No: " & AcctNo  & Chr(10) _
							& Chr(27) & "!" & Chr(16) & Chr(27) & "t" & Chr(16) & strSF.Upper(AcctName) & Chr(10) _
							& Chr(27) & "!" & Chr(0) & "Class   : " & CustClass &  Chr(10) _
							& Chr(27) & "!" & Chr(1) & "------------------------------------------" & Chr(10) _
							& Chr(27) & "!" & Chr(0) & "TOTAL CONSUMPTION  " & AddWhiteSpaces(sTotalCum) & sTotalCum &  Chr(10) _
							& Chr(27) & "!" & Chr(0) & "ENVIRONMENTAL FEE  " & AddWhiteSpaces(NumberFormat2(SeptageFee,1, 2, 2,True)) & NumberFormat2(SeptageFee,1, 2, 2,True) &  Chr(10) _
							& Chr(27) & Chr(97) & Chr(49) _
							& Chr(27) & "!" & Chr(8) & "================================" & Chr(10) _
							& Chr(27) & "!" & Chr(32) & "S U M M A R Y" & Chr(10) _
							& Chr(27) & "!" & Chr(8) & "================================" & Chr(10) _
							& Chr(27) & Chr(97) & Chr(48) _
							& Chr(27) & "!" & Chr(0) & "CURRENT BILL       " & AddWhiteSpaces(NumberFormat2(CurrentAmt,1, 2, 2,True)) & NumberFormat2(CurrentAmt,1, 2, 2,True) &  Chr(10) _
							& Chr(27) & "!" & Chr(0) & "SR. CITIZEN DISC.  " & AddWhiteSpaces("(" & NumberFormat2(SeniorOnBeforeAmt,1, 2, 2,True) & ")") & "(" & NumberFormat2(SeniorOnBeforeAmt,1, 2, 2,True) & ")" &  Chr(10)					

					If DiscAmt2 > 0 Then
						sepBuffer = sepBuffer & Chr(27) & "!" & Chr(0) & "DISCOUNT           " & AddWhiteSpaces("(" & NumberFormat2(DiscAmt2,1, 2, 2,True) & ")") & "(" & NumberFormat2(DiscAmt2,1, 2, 2,True) & ")" &  Chr(10)
					End If
			
					sepBuffer = sepBuffer & Chr(27) & "!" & Chr(0) & "OTHERS             " & AddWhiteSpaces(NumberFormat2(sAddToBillAmt,1, 2, 2,True)) & NumberFormat2(sAddToBillAmt,1, 2, 2,True) &  Chr(10)

					If GlobalVar.WithMeterCharges = 1 Then
						sepBuffer = sepBuffer & "METER CHARGES      " & AddWhiteSpaces(NumberFormat2(MeterCharges,1, 2, 2,True)) & NumberFormat2(MeterCharges,1, 2, 2,True) &  Chr(10)
					End If

					If GlobalVar.WithFranchisedTax = 1 Then
						sepBuffer = sepBuffer & "FRANCHISE TAX      " & AddWhiteSpaces(NumberFormat2(FranchiseTaxAmt,1, 2, 2,True)) & NumberFormat2(FranchiseTaxAmt,1, 2, 2,True) &  Chr(10)
					End If
				
					sepBuffer = sepBuffer & Chr(27) & "!" & Chr(8) & "WATER BILL         " & AddWhiteSpaces(NumberFormat2(GTotalDueAmt,1, 2, 2,True)) & NumberFormat2(GTotalDueAmt,1, 2, 2,True) &  Chr(10) _
							& Chr(27) & "!" & Chr(8) & "ENVIRONMENTAL FEE  " & AddWhiteSpaces(NumberFormat2(SeptageFee,1, 2, 2,True)) & NumberFormat2(SeptageFee,1, 2, 2,True) &  Chr(10) _
							& Chr(27) & "!" & Chr(0) & "ADVANCES           " & AddWhiteSpaces("(" & NumberFormat2(sAdvPayment,1, 2, 2,True) & ")") & "(" & NumberFormat2(sAdvPayment,1, 2, 2,True) & ")" &  Chr(10) _
							& Chr(27) & "!" & Chr(16) & "TOTAL DUE          " & AddWhiteSpaces(NumberFormat2(GTotalDue,1, 2, 2,True)) & NumberFormat2(GTotalDue,1, 2, 2,True) & Chr(10) _
							& Chr(27) & "!" & Chr(8) & "AMT. AFTER DUE DATE" & AddWhiteSpaces(NumberFormat2(GTotalAfterDue,1, 2, 2,True)) & NumberFormat2(GTotalAfterDue,1, 2, 2,True) & Chr(10) _
							& Chr(27) & "!" & Chr(1) & "==========================================" & CRLF & Chr(10)
			End If
		Else
			sepBuffer = Chr(27) & "@" _
			& Chr(27) & Chr(97) & Chr(49) _
			& Chr(27) & "!" & Chr(33) & "STATEMENT OF ACCOUNT" & Chr(10) _
			& Chr(27) & "!" & Chr(1) & "For the Month of " & sBillPeriod & Chr(10) _
			& Chr(27) & "!" & Chr(1) & "------------------------------------------" & Chr(10) _
			& Chr(27) & Chr(97) & Chr(48) _
			& Chr(27) & "!" & Chr(16) & "Account No: " & AcctNo  & Chr(10) _
			& Chr(27) & "!" & Chr(16) & Chr(27) & "t" & Chr(16) & strSF.Upper(AcctName) & Chr(10) _
			& Chr(27) & "!" & Chr(0) & "Class   : " & CustClass &  Chr(10) _
			& Chr(27) & "!" & Chr(1) & "------------------------------------------" & Chr(10) _
			& Chr(27) & "!" & Chr(0) & "TOTAL CONSUMPTION  " & AddWhiteSpaces(sTotalCum) & sTotalCum &  Chr(10) _
			& Chr(27) & "!" & Chr(0) & "ENVIRONMENTAL FEE  " & AddWhiteSpaces(NumberFormat2(SeptageFee,1, 2, 2,True)) & NumberFormat2(SeptageFee,1, 2, 2,True) &  Chr(10) _
			& Chr(27) & Chr(97) & Chr(49) _
			& Chr(27) & "!" & Chr(8) & "================================" & Chr(10) _
			& Chr(27) & "!" & Chr(32) & "S U M M A R Y" & Chr(10) _
			& Chr(27) & "!" & Chr(8) & "================================" & Chr(10) _
			& Chr(27) & Chr(97) & Chr(48) _
			& Chr(27) & "!" & Chr(0) & "CURRENT BILL       " & AddWhiteSpaces(NumberFormat2(CurrentAmt,1, 2, 2,True)) & NumberFormat2(CurrentAmt,1, 2, 2,True) &  Chr(10) _
			& Chr(27) & "!" & Chr(0) & "SR. CITIZEN DISC.  " & AddWhiteSpaces("(" & NumberFormat2(SeniorOnBeforeAmt,1, 2, 2,True) & ")") & "(" & NumberFormat2(SeniorOnBeforeAmt,1, 2, 2,True) & ")" &  Chr(10)					

			If DiscAmt2 > 0 Then
				sepBuffer = sepBuffer & Chr(27) & "!" & Chr(0) & "DISCOUNT           " & AddWhiteSpaces("(" & NumberFormat2(DiscAmt2,1, 2, 2,True) & ")") & "(" & NumberFormat2(DiscAmt2,1, 2, 2,True) & ")" &  Chr(10)
			End If
			
			sepBuffer = sepBuffer & Chr(27) & "!" & Chr(0) & "OTHERS             " & AddWhiteSpaces(NumberFormat2(sAddToBillAmt,1, 2, 2,True)) & NumberFormat2(sAddToBillAmt,1, 2, 2,True) &  Chr(10)

			If GlobalVar.WithMeterCharges = 1 Then
				sepBuffer = sepBuffer & "METER CHARGES      " & AddWhiteSpaces(NumberFormat2(MeterCharges,1, 2, 2,True)) & NumberFormat2(MeterCharges,1, 2, 2,True) &  Chr(10)
			End If

			If GlobalVar.WithFranchisedTax = 1 Then
				sepBuffer = sepBuffer & "FRANCHISE TAX      " & AddWhiteSpaces(NumberFormat2(FranchiseTaxAmt,1, 2, 2,True)) & NumberFormat2(FranchiseTaxAmt,1, 2, 2,True) &  Chr(10)
			End If
				
			sepBuffer = sepBuffer & Chr(27) & "!" & Chr(8) & "WATER BILL         " & AddWhiteSpaces(NumberFormat2(GTotalDueAmt,1, 2, 2,True)) & NumberFormat2(GTotalDueAmt,1, 2, 2,True) &  Chr(10) _
							& Chr(27) & "!" & Chr(8) & "ENVIRONMENTAL FEE  " & AddWhiteSpaces(NumberFormat2(SeptageFee,1, 2, 2,True)) & NumberFormat2(SeptageFee,1, 2, 2,True) &  Chr(10) _
							& Chr(27) & "!" & Chr(8) & "ARREARS            " & AddWhiteSpaces(NumberFormat2(SeptageArrears,1, 2, 2,True)) & NumberFormat2(SeptageArrears,1, 2, 2,True) &  Chr(10) _
							& Chr(27) & "!" & Chr(0) & "ADVANCES           " & AddWhiteSpaces("(" & NumberFormat2(sAdvPayment,1, 2, 2,True) & ")") & "(" & NumberFormat2(sAdvPayment,1, 2, 2,True) & ")" &  Chr(10) _
							& Chr(27) & "!" & Chr(16) & "TOTAL ENVIRONMENTAL" & AddWhiteSpaces(NumberFormat2(GTotalSeptage,1, 2, 2,True)) & NumberFormat2(GTotalSeptage,1, 2, 2,True) & Chr(10) _
							& Chr(27) & "!" & Chr(16) & "TOTAL DUE          " & AddWhiteSpaces(NumberFormat2(GTotalDue,1, 2, 2,True)) & NumberFormat2(GTotalDue,1, 2, 2,True) & Chr(10) _
							& Chr(27) & "!" & Chr(8) & "AMT. AFTER DUE DATE" & AddWhiteSpaces(NumberFormat2(GTotalAfterDue,1, 2, 2,True)) & NumberFormat2(GTotalAfterDue,1, 2, 2,True) & Chr(10) _
							& Chr(27) & "!" & Chr(1) & "==========================================" & CRLF & CRLF & Chr(10) & FULLCUT
		End If

'			If (ArrearsAmt > 0) Or (TotalDueAmt >= GDeposit) Then
'				sepBuffer = sepBuffer & Chr(27) & Chr(97) & Chr(49) _
'						& Chr(27) & "!" & Chr(33) & Chr(2) _
'						& Chr(27) & "!" & Chr(16) & "NOTICE OF DISCONNECTION" & Chr(10) _
'						& Chr(27) & Chr(97) & Chr(48) _
'						& Chr(27) & "!" & Chr(8) & "DISCONNECTION DATE:" & AddWhiteSpaces(DisconnectionDate) & DisconnectionDate & CRLF & Chr(10) 
'			End If
'			
'			sepBuffer = sepBuffer & Chr(27) & Chr(97) & Chr(48) _
'						& Chr(27) & "!" & Chr(33) & Chr(2) _
'						& REVERSE & " NOTE: " &  Chr(10) _
'						& Chr(27) & "!" & Chr(1) & UNREVERSE & "    Disregard Arrears if Payment has been made. Please pay on or before the due date to avoid any inconveniences." & CRLF & Chr(10) _
'						& Chr(27) & "!" & Chr(0) & "Reader: " & GlobalVar.SF.Upper(GlobalVar.EmpName) &  Chr(10) _
'						& Chr(27) & "!" & Chr(0) & "Date & Time: " & sReadDate & " " & sReadTime  & CRLF & CRLF & Chr(10)

		End If

'	////////////////////// QR CODE GENERATOR ///////////////////////////////////////////////////////////////////

'		QRBuffer = GlobalVar.BranchCode & "-" & sBillMonth & GlobalVar.BillYear & "-" & BillNo
'		
'		LogColor(QRBuffer, Colors.Yellow)
'		
'		BarCode = QRBuffer.GetBytes("UTF-8")
'		
'		GCashRefNo = Chr(27) & "@" _
'					& Chr(27) & Chr(97) & Chr(49) & Chr(10) _
'					& Chr(27) & "!" & Chr(8) & "REF. NO.: " & QRBuffer & CRLF & CRLF & Chr(10) _
'					& Chr(10) & Chr(27)
					
'	//////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		StartPrinter
	Catch
		ToastMessageShow($"Unable to Print Bill Statement due to "$ & LastException.Message, True)
		Log(LastException)
	End Try

'		If DBaseFunctions.IsWithDueDate(iReadID) = True Then
'			PrintBuffer = PrintBuffer & Chr(27) & Chr(97) & Chr(48) _
'					& Chr(27) & "!" & Chr(1) & "------------------------------------------" & Chr(10) _
'					& Chr(27) & "!" & Chr(8) & "PAYMENT AFTER DUE DATE" & Chr(10) _
'					& Chr(27) & "!" & Chr(0) & "DUE DATE           " & AddWhiteSpaces(DueDate) & DueDate &  Chr(10) _
'					& Chr(27) & "!" & Chr(0) & "PENALTY            " & AddWhiteSpaces(NumberFormat2(sPenaltyAmt,1, 2, 2,True)) & NumberFormat2(sPenaltyAmt,1, 2, 2,True) &  Chr(10) _
'					& Chr(27) & "!" & Chr(0) & "5% SC DISCOUNT     " & AddWhiteSpaces("(" & NumberFormat2(sSeniorAfterAmt,1, 2, 2,True) & ")") & "(" & NumberFormat2(sSeniorAfterAmt,1, 2, 2,True) & ")" &  Chr(10) _
'					& Chr(27) & "!" & Chr(16) & "TOTAL              " & AddWhiteSpaces(NumberFormat2(sTotalDueAmtAfterSC,1, 2, 2,True)) & NumberFormat2(sTotalDueAmtAfterSC,1, 2, 2,True) & Chr(10) _
'					& Chr(27) & "!" & Chr(1) & "==========================================" & Chr(10)
'		Else
'			PrintBuffer = PrintBuffer & Chr(27) & "!" & Chr(1) & "==========================================" & Chr(10)
'		End If
'		PrintQRCode(ReadID)
End Sub

Private Sub RepeatChar(sChar As String, NoOfRepetition As Int) As String
	Dim Totspaces As String

	For i = 1 To NoOfRepetition
		Totspaces = Totspaces & sChar
	Next
	'   Msgbox(totspaces&totspaces.Length,"tot spaces")
	Return Totspaces
End Sub

Private Sub AddWhiteSpaces(sValue As String) As String
	Dim sRetVal As String
	Dim iLen As Int
	Dim sSpace As String
	Dim AddSpace As Int
	Try
		'20 0.00
		iLen = sValue.Length
		AddSpace = 32 - (19 + iLen)
		sSpace = RepeatChar(" ", AddSpace)
		sRetVal = sSpace
	Catch
		Log(LastException)
	End Try
	Return sRetVal
End Sub

Private Sub AddWhiteSpaces2(sValue As String) As String
	Dim sRetVal As String
	Dim iLen As Int
	Dim sSpace As String
	Dim AddSpace As Int
	Try
		'20 0.00
		iLen = sValue.Length
		AddSpace = 32 - (18 + iLen)
		sSpace = RepeatChar(" ", AddSpace)
		sRetVal = sSpace
	Catch
		Log(LastException)
	End Try
	Return sRetVal
End Sub

Private Sub AddWhiteSpaces3(sValue As String) As String
	Dim sRetVal As String
	Dim iLen As Int
	Dim sSpace As String
	Dim AddSpace As Int
	Try
		'20 0.00
		iLen = sValue.Length
		AddSpace = 24 - (iLen)
		sSpace = RepeatChar(" ", AddSpace)
		sRetVal = sSpace
	Catch
		Log(LastException)
	End Try
	Return sRetVal
End Sub

Private Sub PrintQRCode(iReadID As Int)
'	bmpQR = QR.Create(GlobalVar.BranchID & "-" & GlobalVar.BillMonth & "-" & GlobalVar.BillYear & BillNo)
'	QRBuffer = Chr(27) & "@" _
'			   & Chr(29) & Chr(40) & Chr(107) & Chr(4) & Chr(0) & Chr(49) & Chr(65) & Chr(50) & Chr(0) _
'			   & Chr(29) & Chr(40) & Chr(107) & Chr(3) & Chr(0) & Chr(49) & Chr(67) & Chr(32) _
'			   & Chr(29) & Chr(40) & Chr(107) & Chr(3) & Chr(0) & Chr(49) & Chr(69) & Chr(49) _
'			   & Chr(29) & Chr(40) & Chr(107) & Chr(5) & Chr(0) & Chr(49) & Chr(80) & Chr(48) & bmpQR _
'			   & Chr(29) & Chr(40) & Chr(107) & Chr(3) & Chr(0) & Chr(49) & Chr(81) & Chr(48)
'			   
'	Log(QRBuffer)
'	StartPrinter
End Sub

Sub StartPrinter
	
	PairedDevices.Initialize
	
	Try
		PairedDevices = Serial1.GetPairedDevices
	Catch
		
		DispInfoMsg("Getting Paired Devices","Printer Error")
		TMPrinter.Close
		Serial1.Disconnect
	End Try

	If PairedDevices.Size = 0 Then
		DispInfoMsg("ERROR!" & CRLF & $"No paired Bluetooth Printer..."$, $"NO CONNECTED PRINTER"$)
		ProgressDialogHide
		Return
	End If
	
	If PairedDevices.Size = 1 Then

		Try
			DeviceName=PairedDevices.Getkeyat(0)
			DeviceMac=PairedDevices.GetValueAt(0)
			Log(DeviceName & " -> " & DeviceMac)
		
'			If DeviceName.Contains("Thermal") Then 'Insert the BT-Name of the printer or use the MAC address
			Serial1.ConnectInsecure(BTAdmin, DeviceMac,1)
			ProgressDialogHide
'			End If
		Catch
			DispInfoMsg("Connecting","Printer Error")
			TMPrinter.Close
			Serial1.Disconnect
		End Try
	Else
		FoundDevices.Initialize

		For i = 0 To PairedDevices.Size - 1
			FoundDevices.Add(PairedDevices.GetKeyAt(i))
			DeviceName=PairedDevices.Getkeyat(i)
			DeviceMac=PairedDevices.GetValueAt(i)
			Log(DeviceName & " -> " & DeviceMac)
'			If DeviceName.Contains("Thermal") Then 'Insert the BT-Name of the printer or use the MAC address
			Serial1.ConnectInsecure(BTAdmin, DeviceMac,1)
			ProgressDialogHide
			Exit
'			End If
		Next

		Res = InputList(FoundDevices, "Choose Device", -1)

		If Res <> DialogResponse.CANCEL Then
			Serial1.Connect(PairedDevices.Get(FoundDevices.Get(Res)))
		End If
	End If
End Sub

Sub Printer_Connected (Success As Boolean)
	Dim iResponse As Object
	Dim csMsg As CSBuilder
	ProgressDialogHide
	Log("Connected: " & Success)

	If Success = False Then
		ProgressDialogHide
		If Not(ConfirmWarning($"Unable to Connect to Printer!"$, $"Printer Error"$, $"Reprint"$, $"Cancel"$, "")) Then
			Return
		End If
		StartPrinter
	Else
		'Printer here

		ProgressDialogHide
		TMPrinter.Initialize2(Serial1.OutputStream, "windows-1252")
		oStream.Initialize(Serial1.InputStream, Serial1.OutputStream, "LogoPrint")
		Logo.Initialize(File.DirAssets, GlobalVar.BranchLogo)
		Logobmp = CreateScaledBitmap(Logo, Logo.Width, Logo.Height)
		Log(DeviceName)

		If strSF.Upper(DeviceName) = "WOOSIM" Then
			WoosimCmd.InitializeStatic("com.woosim.printer.WoosimCmd")
			WoosimImage.InitializeStatic("com.woosim.printer.WoosimImage")
			
			initPrinter = WoosimCmd.RunMethod("initPrinter",Null)
			If GlobalVar.BranchID = 28 Or GlobalVar.BranchID = 29 Or GlobalVar.BranchID = 30 Then
				PrintLogo = WoosimImage.RunMethod("printBitmap", Array (0, 0, 400, 200, Logobmp))
			Else
				PrintLogo = WoosimImage.RunMethod("printBitmap", Array (0, 0, 430, 150, Logobmp))
			End If
			If GlobalVar.BranchID = 76 Then
			Else
				oStream.Write(initPrinter)
				oStream.Write(WoosimCmd.RunMethod("setPageMode",Null))
				oStream.Write(PrintLogo)
				oStream.Write(WoosimCmd.RunMethod("PM_setStdMode",Null))
			End If
'			oStream.Write(initPrinter)
'			oStream.Write(WoosimCmd.RunMethod("setPageMode",Null))
'			oStream.Write(PrintLogo)
'			oStream.Write(WoosimCmd.RunMethod("PM_setStdMode",Null))
		Else
			EPSONPrint.Initialize
			PrintLogo =  EPSONPrint.getBytesForBitmap2(GlobalVar.BranchLogo,True)
			oStream.Write(PrintLogo)
		End If

		Sleep(500)
		TMPrinter.WriteLine(PrintBuffer)
		Log(PrintBuffer)
		TMPrinter.Flush
		Sleep(300)
		
'		If GlobalVar.BranchID = 28 Or GlobalVar.BranchID = 29 Or GlobalVar.BranchID = 30 Then
'			GlobalVar.SeptageProv = 3
'			iHasSeptage = 1
'		End If
		
		If GlobalVar.SeptageProv > 0 Then
			If iHasSeptage = 1 Then
				Sleep(100)

				oStream2.Initialize(Serial1.InputStream, Serial1.OutputStream, "SeptageLogo")
				SeptageLogo.Initialize(File.DirAssets, GlobalVar.SeptageLogo)
				Logobmp = CreateScaledBitmap(SeptageLogo, SeptageLogo.Width, SeptageLogo.Height)
				WoosimCmd.InitializeStatic("com.woosim.printer.WoosimCmd")
				WoosimImage.InitializeStatic("com.woosim.printer.WoosimImage")

				initPrinter = WoosimCmd.RunMethod("initPrinter",Null)
				PrintSeptageLogo = WoosimImage.RunMethod("printBitmap", Array (0, 0, 0, 170, Logobmp))
				oStream2.Write(initPrinter)
				oStream2.Write(WoosimCmd.RunMethod("setPageMode",Null))
				oStream2.Write(PrintSeptageLogo)
				oStream2.Write(WoosimCmd.RunMethod("PM_setStdMode",Null))
				Sleep(20)
				TMPrinter.WriteLine(sepBuffer)
			End If
		End If

		'////////////////////// QR CODE PRINTING ///////////////////////////////////////////////////////////////////

'		TMPrinter.Flush
'		Sleep(100)
'		
'		initPrinter = WoosimCmd.RunMethod("initPrinter",Null)
'		WoosimBarcode.InitializeStatic("com.woosim.printer.WoosimBarcode")
'		QRCodeNew = WoosimBarcode.RunMethod("create2DBarcodeQRCode", Array(0, byte3, 5, BarCode))
'		
'		Dim cmd_print () As Byte = WoosimCmd.RunMethod("printData",Null)
'		Dim SetPos() As Byte
'		SetPos = WoosimCmd.RunMethod("setAlignment",Array(1))
'		oStream.Write(initPrinter)
'		'		oStream.Write(WoosimCmd.RunMethod("setPageMode",Null))
'		oStream.Write(SetPos)
'		oStream.Write(QRCodeNew)
'		oStream.Write(cmd_print)
'		Sleep(20)
'		TMPrinter.WriteLine(GCashRefNo)

		'//////////////////////////////////////////////////////////////////////////////////////////////////////////

		ProgressDialogHide
		TMPrinter.Flush
		DispInfoMsg($"Bill Statement successfully printed."$ & $"Tap OK to Continue..."$, $"Bill Printing"$)
		TMPrinter.Close
		Serial1.Disconnect
	End If
End Sub


Sub CreateScaledBitmap(Original As Bitmap, NewWidth As Int, NewHeight As Int) As Bitmap
	Dim r As Reflector
	Dim b As Bitmap
	b = r.RunStaticMethod("android.graphics.Bitmap", "createScaledBitmap", Array As Object(Original, NewWidth, NewHeight, True), Array As String("android.graphics.Bitmap", "java.lang.int", "java.lang.int", "java.lang.boolean"))
	Return b
End Sub

Sub LogoPrint_NewData (Buffer() As Byte)
	Dim msg As String
	msg = BytesToString(Buffer, 0, Buffer.Length, "UTF8")
'	ToastMessageShow(msg, False)
	Log(msg)
End Sub

Sub LogoPrint_Error
'	ToastMessageShow(LastException.Message, True)
'	Log("AStreams_Error")
End Sub

Sub SeptageLogo_NewData (Buffer() As Byte)
	Dim msg As String
	msg = BytesToString(Buffer, 0, Buffer.Length, "UTF8")
'	ToastMessageShow(msg, False)
	Log(msg)
End Sub

Sub SeptageLogo_Error
'	ToastMessageShow(LastException.Message, True)
'	Log("AStreams_Error")
End Sub
#End Region

#Region High Bill
Sub pnlHiMsgBox_Touch (Action As Int, X As Float, Y As Float)
	
End Sub

Private Sub ShowHighBillDialog
	Dim sMsg = $"WARNING! High Bill Detected, Please Check the Water Meter"$ As String
	
	soundsAlarmChannel.Play(SoundID,1,1,1,0,1)
	cdButtonCancel.Initialize(0xFFDC143C, 25)
	btnHiCancelPost.Background = cdButtonCancel


	cdButtonSaveOnly.Initialize(0xFF14C0DB, 25)
	btnHiSaveOnly.Background = cdButtonSaveOnly

	cdButtonSaveAndPrint.Initialize(0xFF1976D2, 25)
	btnHiSaveAndPrint.Background = cdButtonSaveAndPrint

	pnlHiMsgBox.Visible = True
	If  Not(TalkBackIsActive) Then
		TTS1.Speak(sMsg, False)
	End If
End Sub

Sub btnHiCancelPost_Click
	TTS1.Stop
	soundsAlarmChannel.Stop(SoundID)
	vibration.vibrateCancel
	pnlHiMsgBox.Visible = False
	snack.Initialize("", Activity, $"Posting Reading Cancelled."$ ,snack.DURATION_SHORT)
	SetSnackBarBackground(snack, Colors.White)
	SetSnackBarTextColor(snack, Colors.Red)
	snack.Show
	vibration.vibrateCancel
	rsLoadedBook.Position= intCurrPos
	DisplayRecord
	ButtonState
	intSaveOnly = 0
	blnSuperHighBill = False

End Sub

Sub btnHiSaveOnly_Click
	intSaveOnly = 0
	pnlHiMsgBox.Visible = False
	sPresentReading = txtPresRdg.Text
	ShowHighBillConfirmation

'	vibration.vibrateCancel
'	soundsAlarmChannel.Stop(SoundID)
	
'	SaveReading(0, ReadID, AcctID)
'	If RetrieveRecords(GlobalVar.BookID)=False Then Return
'	If intCurrPos < (RecCount - 1) Then
'		rsLoadedBook.Position = intCurrPos + 1
'	Else
'		rsLoadedBook.Position = intCurrPos
'	End If
'	DisplayRecord
'	ButtonState
'	If GetUnusualCount(GlobalVar.BookID) = True Then
'		UpdateBadge("Flag", AddBadgeToIcon(flagBitmap, intTotal))
'	End If
End Sub

Sub btnHiSaveAndPrint_Click
	intSaveOnly = 1
	pnlHiMsgBox.Visible = False
	sPresentReading = txtPresRdg.Text
	ShowHighBillConfirmation
End Sub

#End Region

#Region High Bill Confirmation
Sub pnlHighBillConfirmation_Touch (Action As Int, X As Float, Y As Float)
	
End Sub

Private Sub ShowHighBillConfirmation
	Dim sMsg = $"Please retype your reading!"$ As String
	pnlHighBillConfirmation.Visible = True

	cdConfirmRdg.Initialize2(Colors.Black, 0,0, Colors.Transparent)
	txtPresRdgConfirm.Background = cdConfirmRdg

	cdButtonCancel.Initialize(0xFFDC143C, 15)
	btnHBConfirmCancel.Background = cdButtonCancel

	cdButtonSaveAndPrint.Initialize(0xFF1976D2, 25)
	btnHBConfirmSaveAndPrint.Background = cdButtonSaveAndPrint

	txtPresRdgConfirm.Text = ""
	btnHBConfirmSaveAndPrint.Enabled = False
	txtPresRdgConfirm.RequestFocus
	IMEkeyboard.ShowKeyboard(txtPresRdgConfirm)
	cKeyboard.HideKeyboard
	If  Not(TalkBackIsActive) Then
		TTS1.Speak(sMsg, False)
	End If
	Select Case intSaveOnly
		Case 0
			btnHBConfirmSaveAndPrint.Text = "SAVE"
		Case 1
			btnHBConfirmSaveAndPrint.Text = "SAVE AND PRINT"
	End Select
	
End Sub

Sub txtPresRdgConfirm_TextChanged (Old As String, New As String)
	If txtPresRdg.Text = New Or sPresentReading = New Then
		btnHBConfirmSaveAndPrint.Enabled = True
	Else
		btnHBConfirmSaveAndPrint.Enabled = False
	End If
End Sub

Sub txtPresRdgConfirm_EnterPressed
	If btnHBConfirmSaveAndPrint.Enabled = False Then Return
	btnHBConfirmSaveAndPrint_Click
End Sub

Sub btnHBConfirmSaveAndPrint_Click
	pnlHighBillConfirmation.Visible = False
	TTS1.Stop
	vibration.vibrateCancel
	soundsAlarmChannel.Stop(SoundID)
	
	intCurrPos = rsLoadedBook.Position
	Select Case intSaveOnly
		Case 0
			If blnSuperHighBill = True Then
				ShowSuperHBConfirmation
				Return
			End If
			SaveReading(0, ReadID, AcctID)
		Case 1
			If blnSuperHighBill = True Then
				ShowSuperHBConfirmation
				Return
			End If
			SaveReading(1, ReadID, AcctID)
			PrintBillData(ReadID)
	End Select

	If RetrieveRecords(GlobalVar.BookID)=False Then Return
	If intCurrPos < (RecCount - 1) Then
		rsLoadedBook.Position = intCurrPos + 1
	Else
		rsLoadedBook.Position = intCurrPos
	End If
	DisplayRecord
	ButtonState
	If GetUnusualCount(GlobalVar.BookID) = True Then
		UpdateBadge("Flag", AddBadgeToIcon(flagBitmap, intTotal))
	End If
	intSaveOnly = 0
	blnSuperHighBill = False
End Sub

Sub btnHBConfirmCancel_Click
	TTS1.Stop
	soundsAlarmChannel.Stop(SoundID)
	vibration.vibrateCancel
	pnlHighBillConfirmation.Visible = False
	txtPresRdg.RequestFocus
	txtPresRdg.SelectAll
	IMEkeyboard.HideKeyboard

'	IMEkeyboard.ShowKeyboard(txtPresRdg)
	intSaveOnly = 0
	blnSuperHighBill = False
End Sub
#End Region
#Region Low Bill
Sub pnlLowMsgBox_Touch (Action As Int, X As Float, Y As Float)
	
End Sub

Private Sub ShowLowBillDialog
	Dim sMsg = $"WARNING! Low Bill Detected, Please Check the Water Meter"$ As String

	soundsAlarmChannel.Play(SoundID,1,1,1,0,1)

	cdButtonCancel.Initialize(0xFFDC143C, 25)
	btnLowCancelPost.Background = cdButtonCancel

	cdButtonSaveOnly.Initialize(0xFF14C0DB, 25)
	btnLowSaveOnly.Background = cdButtonSaveOnly

	cdButtonSaveAndPrint.Initialize(0xFF1976D2, 25)
	btnLowSaveAndPrint.Background = cdButtonSaveAndPrint
	cKeyboard.HideKeyboard
	pnlLowMsgBox.Visible = True
	If  Not(TalkBackIsActive) Then
		TTS1.Speak(sMsg, False)
	End If
End Sub

Sub btnLowSaveAndPrint_Click
	TTS1.Stop
	pnlLowMsgBox.Visible = False
	vibration.vibrateCancel
	soundsAlarmChannel.Stop(SoundID)
	
	intCurrPos = rsLoadedBook.Position
	SaveReading(1, ReadID, AcctID)
	PrintBillData(ReadID)
	If RetrieveRecords(GlobalVar.BookID)=False Then Return
	If intCurrPos < (RecCount - 1) Then
		rsLoadedBook.Position = intCurrPos + 1
	Else
		rsLoadedBook.Position = intCurrPos
	End If
	DisplayRecord
	ButtonState
	If GetUnusualCount(GlobalVar.BookID) = True Then
		UpdateBadge("Flag", AddBadgeToIcon(flagBitmap, intTotal))
	End If
End Sub

Sub btnLowSaveOnly_Click
	pnlLowMsgBox.Visible = False
	vibration.vibrateCancel
	soundsAlarmChannel.Stop(SoundID)
	
	SaveReading(0, ReadID, AcctID)
	If RetrieveRecords(GlobalVar.BookID)=False Then Return
	If intCurrPos < (RecCount - 1) Then
		rsLoadedBook.Position = intCurrPos + 1
	Else
		rsLoadedBook.Position = intCurrPos
	End If
	DisplayRecord
	ButtonState
	If GetUnusualCount(GlobalVar.BookID) = True Then
		UpdateBadge("Flag", AddBadgeToIcon(flagBitmap, intTotal))
	End If
End Sub

Sub btnLowCancelPost_Click
	soundsAlarmChannel.Stop(SoundID)
	vibration.vibrateCancel
	pnlLowMsgBox.Visible = False

	snack.Initialize("", Activity, $"Posting Reading Cancelled."$ ,snack.DURATION_SHORT)
	SetSnackBarBackground(snack, Colors.White)
	SetSnackBarTextColor(snack, Colors.Red)
	snack.Show
	rsLoadedBook.Position= intCurrPos
	DisplayRecord
	ButtonState
End Sub
#End Region

#Region Normal Reading

Sub pnlNormalMsgBox_Touch (Action As Int, X As Float, Y As Float)
	
End Sub
Private Sub ShowAddNewReading
	cdButtonCancel.Initialize(0xFFDC143C, 25)
	btnNormalCancelPost.Background = cdButtonCancel

	cdButtonSaveOnly.Initialize(0xFF14C0DB, 25)
	btnNormalSaveOnly.Background = cdButtonSaveOnly

	cdButtonSaveAndPrint.Initialize(0xFF1976D2, 25)
	btnNormalSaveAndPrint.Background = cdButtonSaveAndPrint
	
	pnlNormalMsgBox.Visible = True
End Sub

Sub btnNormalSaveAndPrint_Click
	pnlNormalMsgBox.Visible = False
	intCurrPos = rsLoadedBook.Position
	SaveReading(1, ReadID, AcctID)
	ProgressDialogShow2($"Bill Statement Printing..."$, False)
	PrintBillData(ReadID)
	If RetrieveRecords(GlobalVar.BookID)=False Then Return
	If intCurrPos < (RecCount - 1) Then
		rsLoadedBook.Position = intCurrPos + 1
	Else
		rsLoadedBook.Position = intCurrPos
	End If
	DisplayRecord
	ButtonState
	If GetUnusualCount(GlobalVar.BookID) = True Then
		UpdateBadge("Flag", AddBadgeToIcon(flagBitmap, intTotal))
	End If
End Sub

Sub btnNormalSaveOnly_Click
	pnlNormalMsgBox.Visible = False
	SaveReading(0, ReadID, AcctID)
	If RetrieveRecords(GlobalVar.BookID)=False Then Return
	If intCurrPos < (RecCount - 1) Then
		rsLoadedBook.Position = intCurrPos + 1
	Else
		rsLoadedBook.Position = intCurrPos
	End If
	DisplayRecord
	ButtonState
	If GetUnusualCount(GlobalVar.BookID) = True Then
		UpdateBadge("Flag", AddBadgeToIcon(flagBitmap, intTotal))
	End If
End Sub

Sub btnNormalCancelPost_Click
	pnlNormalMsgBox.Visible = False
	snack.Initialize("", Activity, $"Posting Reading Cancelled."$ ,snack.DURATION_SHORT)
	SetSnackBarBackground(snack, Colors.White)
	SetSnackBarTextColor(snack, Colors.Red)
	snack.Show
	rsLoadedBook.Position = intCurrPos
	DisplayRecord
	ButtonState
End Sub

#End Region

#Region Edit Dialog
Private Sub ShowEditDialog
	
	Dim csContent As CSBuilder
	csContent.Initialize.Color(Colors.Red).Append($"WARNING!"$).Color(Colors.DarkGray).Append(CRLF & $"You already Miscoded this Account."$ & CRLF & $"Continuing this will "$).Color(Colors.Red).Append($"REMOVE "$).Color(Colors.DarkGray).Append($"any Miscoded data on this Account."$).Color(GlobalVar.PriColor).Append(CRLF & CRLF & $"Do you want to Continue anyway?"$).PopAll
	
	MatDialogBuilder.Initialize("EditDialog")
	MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIGHT)
	MatDialogBuilder.Title($"EDIT CURRENT READING"$).TitleColor(Colors.Red).TitleGravity(MatDialogBuilder.GRAVITY_START)
	MatDialogBuilder.IconRes(GlobalVar.WarningIcon).LimitIconToDefaultSize
	MatDialogBuilder.Content(csContent)
	MatDialogBuilder.PositiveText($"YES"$).PositiveColor(GlobalVar.PriColor)
	MatDialogBuilder.NegativeText($"NO"$).NegativeColor(Colors.Red)
	MatDialogBuilder.CanceledOnTouchOutside(False)
	MatDialogBuilder.Show
End Sub

Sub EditDialog_ButtonPressed (mDialog As MaterialDialog, sAction As String)
	Select Case sAction
		Case mDialog.ACTION_POSITIVE
			blnEdit = True
			txtPresRdg.Enabled=True
			txtPresRdg.RequestFocus
			txtPresRdg.SelectionStart=0
			txtPresRdg.SelectAll
'			IMEkeyboard.ShowKeyboard(txtPresRdg)
		Case mDialog.ACTION_NEGATIVE
			snack.Initialize("",Activity,$"Editing Reading Cancelled!"$,snack.DURATION_SHORT)
			SetSnackBarBackground(snack, Colors.Red)
			SetSnackBarTextColor(snack, Colors.White)
			snack.Show
	End Select
			
End Sub

#End Region

#Region Search Unusual Reading

Sub btnCancelUnusual_Click
	SearchClosed
	rsLoadedBook.Position = intTempCurrPos
	DisplayRecord
	ButtonState
End Sub

Sub SearchUnsual_ItemClick(Value As String)
	Log(Value)
	SearchFor = Value
	FindSearchRetValue(SearchFor)
	SV.ClearAll
	SearchClosed
End Sub

Private Sub SearchUnsualReading(iFlag As Int)
	Dim SearchList As List

	intTempCurrPos = rsLoadedBook.Position

	If SV.IsInitialized=False Then
		SV.Initialize(Me,"SV")
	End If
	
	SV.ClearAll
	SV.ClearSearchBox
	SV.lv.Clear

	SearchList.Initialize
	SearchList.Clear

	Try
		Select Case iFlag
			Case 0
				Starter.strCriteria="SELECT * FROM tblReadings " & _
									"WHERE WasRead = 1 " & _
									"AND BookID=" & GlobalVar.BookID & " " & _
									"AND BillYear= " & GlobalVar.BillYear & " " & _
									"AND BillMonth=" & GlobalVar.BillMonth & " " & _
									"AND WasMissCoded = 1 " & _
									"ORDER BY SeqNo ASC"

			Case 1
				Starter.strCriteria="SELECT * FROM tblReadings " & _
									"WHERE WasRead = 1 " & _
									"AND BookID=" & GlobalVar.BookID & " " & _
									"AND BillYear= " & GlobalVar.BillYear & " " & _
									"AND BillMonth=" & GlobalVar.BillMonth & " " & _
									"AND ImplosiveType = 'zero' " & _
									"ORDER BY SeqNo ASC"
			Case 2
				Starter.strCriteria="SELECT * FROM tblReadings " & _
									"WHERE WasRead = 1 " & _
									"AND BookID=" & GlobalVar.BookID & " " & _
									"AND BillYear= " & GlobalVar.BillYear & " " & _
									"AND BillMonth=" & GlobalVar.BillMonth & " " & _
									"AND ImplosiveType = 'implosive-inc' " & _
									"ORDER BY SeqNo ASC"

			Case 3
				Starter.strCriteria="SELECT * FROM tblReadings " & _
									"WHERE WasRead = 1 " & _
									"AND BookID=" & GlobalVar.BookID & " " & _
									"AND BillYear= " & GlobalVar.BillYear & " " & _
									"AND BillMonth=" & GlobalVar.BillMonth & " " & _
									"AND ImplosiveType = 'implosive-dec' " & _
									"ORDER BY SeqNo ASC"
		
			Case 4
				Starter.strCriteria="SELECT * FROM tblReadings " & _
									"WHERE WasRead = 1 " & _
									"AND BookID=" & GlobalVar.BookID & " " & _
									"AND BillYear= " & GlobalVar.BillYear & " " & _
									"AND BillMonth=" & GlobalVar.BillMonth & " " & _
									"AND BillType = 'AB' " & _
									"ORDER BY SeqNo ASC"
			Case 5
				Starter.strCriteria="SELECT * FROM tblReadings " & _
									"WHERE WasRead = 1 " & _
									"AND BookID = " & GlobalVar.BookID & " " & _
									"AND BillYear = " & GlobalVar.BillYear & " " & _
									"AND BillMonth = " & GlobalVar.BillMonth & " " & _
									"AND WasRead = 1 " & _
									"AND PrintStatus = 0 " & _
									"ORDER BY SeqNo ASC"

		End Select
		rsUnusualReading = Starter.DBCon.ExecQuery(Starter.strCriteria)

		If rsUnusualReading.RowCount > 0 Then
			For i = 0 To rsUnusualReading.RowCount - 1
				rsUnusualReading.Position = i
				Dim it As Item
				it.Title=rsUnusualReading.GetString("SeqNo") & " - " & rsUnusualReading.GetString("AcctName")
				it.Text=rsUnusualReading.GetString("AcctAddress")
				it.SearchText = rsUnusualReading.GetString("SeqNo").ToLowerCase
				it.Value=rsUnusualReading.GetString("ReadID")
				SearchList.Add(it)
			Next
			lblUnusualCount.Text = rsUnusualReading.RowCount & $" Record(s) found..."$
		Else
			lblUnusualCount.Text = $"No Record found..."$
			Return
		End If
		SV.SetItems(SearchList)
		SearchList.Clear
	Catch
		Log(LastException)
	End Try
End Sub

Sub pnlUnusual_Touch (Action As Int, X As Float, Y As Float)
	
End Sub
#End Region



#Region Averaging
Sub pnlAveMsgBox_Touch (Action As Int, X As Float, Y As Float)
	
End Sub

Private Sub ComputeAverageBill
	PresAveRdg = PrevRdg + AveCum
	If Not(IsNumber(PresAveRdg)) Or PresAveRdg.Length <= 0 Then
		PresAveCum = 0
	Else
		PresAveCum = AveCum
	End If
End Sub

Private Sub ShowPicRequiredDialog
	soundsAlarmChannel.Play(SoundID,1,1,1,0,1)

	cdButtonCancel.Initialize(0xFFDC143C, 25)
	btnAveCancelPost.Background = cdButtonCancel

	cdButtonSaveAndPrint.Initialize(0xFF1976D2, 25)
	btnAveTakePicture.Background = cdButtonSaveAndPrint
	
	pnlAveMsgBox.Visible = True
End Sub

Sub btnAveTakePicture_Click
	pnlAveMsgBox.Visible = False
	soundsAlarmChannel.Stop(SoundID)
	GlobalVar.isAverage = 1
	Permissions.CheckAndRequest(Permissions.PERMISSION_CAMERA)
	StartActivity(NewCam)
End Sub

Sub btnAveCancelPost_Click
	soundsAlarmChannel.Stop(SoundID)
	vibration.vibrateCancel
	pnlAveMsgBox.Visible = False

	snack.Initialize("", Activity, $"Posting Average Reading Cancelled."$ ,snack.DURATION_SHORT)
	SetSnackBarBackground(snack, Colors.White)
	SetSnackBarTextColor(snack, Colors.Red)
	snack.Show
	GlobalVar.isAverage = 0
	GlobalVar.blnAverage = False
End Sub

Private Sub ShowAveNotAvailableDialog
	Dim csContent As CSBuilder
	Dim csTitle As CSBuilder
	Dim sMsg = $"ERROR! Unable to generate average bill"$ As String

	soundsAlarmChannel.Play(SoundID,1,1,1,0,1)
	
	If AcctStatus = "dc" Then
		csContent.Initialize.Size(14).Color(Colors.Red).Bold.Append($"AVERAGING NOT ALLOWED!"$).Pop
		csContent.Color(Colors.DarkGray).Append(CRLF & $"Unable to generate average bill due to this account was disconnected."$).PopAll
		csTitle.Initialize.Bold.Size(16).Color(Colors.Red).Append($"E R R O R"$).PopAll
	Else
		csContent.Initialize.Size(14).Color(Colors.Red).Bold.Append($"ALREADY READ!"$).Pop
		csContent.Color(Colors.DarkGray).Append(CRLF & $"Unable to generate average bill due to this account was already read."$).PopAll
		csTitle.Initialize.Bold.Size(16).Color(Colors.Red).Append($"E R R O R"$).PopAll
	End If
	

	MatDialogBuilder.Initialize("AveDialogUnavailable")
	MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIGHT)
	MatDialogBuilder.Title(csTitle).TitleGravity(MatDialogBuilder.GRAVITY_START)
	MatDialogBuilder.IconRes(GlobalVar.InfoIcon).LimitIconToDefaultSize
	MatDialogBuilder.Content(csContent)
	MatDialogBuilder.PositiveText($"OK"$).PositiveColor(GlobalVar.PriColor)
	MatDialogBuilder.CanceledOnTouchOutside(False)
	MatDialogBuilder.Show
	
	If  Not(TalkBackIsActive) Then
		TTS1.Speak(sMsg, False)
	End If

End Sub

Sub AveDialogUnavailable_ButtonPressed (mDialog As MaterialDialog, sAction As String)
	Select Case sAction
		Case mDialog.ACTION_POSITIVE
			vibration.vibrateCancel
			soundsAlarmChannel.Stop(SoundID)	
			TTS1.Stop
			Return
		Case mDialog.ACTION_NEGATIVE
			vibration.vibrateCancel
			soundsAlarmChannel.Stop(SoundID)
			TTS1.Stop
			Return
	End Select
End Sub

Private Sub ShowAverageBillRemarks
	IMEkeyboard.HideKeyboard
	cdButtonCancel.Initialize(0xFFDC143C, 25)
	btnAveRemCancelPost.Background = cdButtonCancel

	cdButtonSaveAndPrint.Initialize(0xFF1976D2, 25)
	btnAveRemSaveAndPrint.Background = cdButtonSaveAndPrint
	
	cdTxtBox.Initialize2(Colors.White, 0, 0, Colors.Transparent)
	txtReason.Background = cdTxtBox

	pnlAveRemMsgBox.Visible = True
	txtReason.Text = ""
	sAveRem = ""

	optReason0.Checked = False
	optReason1.Checked = False
	optReason2.Checked = False
	optReason3.Checked = True
	optReason4.Checked = False
	optReason5.Checked = False
	optReason6.Checked = False
	optReason7.Checked = False
	optReason8.Checked = False

End Sub

Sub txtReason_TextChanged (Old As String, New As String)
	If optReason8.Checked = True Then
		If txtReason.Text = "" Or GlobalVar.SF.Len(GlobalVar.SF.Trim(txtReason.Text)) <= 0 Then
			btnAveRemSaveAndPrint.Enabled = False
		Else
			btnAveRemSaveAndPrint.Enabled = True
		End If
	End If
End Sub

Sub btnAveRemSaveAndPrint_Click
	
	If optReason0.Checked = True Then
		sAveRem = optReason0.Text
	Else If optReason1.Checked = True Then
		sAveRem = optReason1.Text
	Else If optReason2.Checked = True Then
		sAveRem = optReason2.Text
	Else If optReason3.Checked = True Then
		sAveRem = optReason3.Text
	Else If optReason4.Checked = True Then
		sAveRem = optReason4.Text
	Else If optReason5.Checked = True Then
		sAveRem = optReason5.Text
	Else If optReason6.Checked = True Then
		sAveRem = optReason6.Text
	Else If optReason7.Checked = True Then
		sAveRem = optReason7.Text
	Else If optReason8.Checked = True Then
		If txtReason.Text = "" Or GlobalVar.SF.Len(GlobalVar.SF.Trim(txtReason.Text)) <= 0 Then Return
		sAveRem = txtReason.Text
	End If

	pnlAveRemMsgBox.Visible = False

	intCurrPos = rsLoadedBook.Position
	ComputeAverageBill
	SaveAverageBill(1, ReadID, AcctID)
	ProgressDialogShow2($"Bill Statement Printing..."$, False)
	IMEkeyboard.HideKeyboard
	PrintBillData(ReadID)
	If RetrieveRecords(GlobalVar.BookID)=False Then Return
	If intCurrPos < (RecCount - 1) Then
		rsLoadedBook.Position = intCurrPos + 1
	Else
		rsLoadedBook.Position = intCurrPos
	End If
	DisplayRecord
	ButtonState
	If GetUnusualCount(GlobalVar.BookID) = True Then
		UpdateBadge("Flag", AddBadgeToIcon(flagBitmap, intTotal))
	End If
End Sub

Sub btnAveRemCancelPost_Click
	pnlAveRemMsgBox.Visible = False
	snack.Initialize("", Activity, $"Average Bill Posting Cancelled."$ ,snack.DURATION_SHORT)
	SetSnackBarBackground(snack, Colors.White)
	SetSnackBarTextColor(snack, Colors.Red)
	IMEkeyboard.HideKeyboard
	snack.Show
	GlobalVar.isAverage = 0
	GlobalVar.blnAverage = False
	rsLoadedBook.Position = intCurrPos
	DisplayRecord
	ButtonState
End Sub

Sub pnlAveRemMsgBox_Touch (Action As Int, X As Float, Y As Float)
	
End Sub

Sub optReason0_CheckedChange(Checked As Boolean)
	LogColor(Checked,Colors.Magenta)
	If Checked = True Then
		txtReason.Enabled = False
		txtReason.Text = ""
	End If
End Sub

Sub optReason1_CheckedChange(Checked As Boolean)
	LogColor(Checked,Colors.Magenta)
	If Checked = True Then
		txtReason.Enabled = False
		txtReason.Text = ""
	End If
End Sub

Sub optReason2_CheckedChange(Checked As Boolean)
	LogColor(Checked,Colors.Magenta)
	If Checked = True Then
		txtReason.Enabled = False
		txtReason.Text = ""
	End If
End Sub

Sub optReason3_CheckedChange(Checked As Boolean)
	LogColor(Checked,Colors.Magenta)
	If Checked = True Then
		txtReason.Enabled = False
		txtReason.Text = ""
	End If
End Sub

Sub optReason4_CheckedChange(Checked As Boolean)
	LogColor(Checked,Colors.Magenta)
	If Checked = True Then
		txtReason.Enabled = False
		txtReason.Text = ""
	End If
End Sub

Sub optReason5_CheckedChange(Checked As Boolean)
	LogColor(Checked,Colors.Magenta)
	If Checked = True Then
		txtReason.Enabled = False
		txtReason.Text = ""
	End If
End Sub

Sub optReason6_CheckedChange(Checked As Boolean)
	LogColor(Checked,Colors.Magenta)
	If Checked = True Then
		txtReason.Enabled = False
		txtReason.Text = ""
	End If
End Sub

Sub optReason7_CheckedChange(Checked As Boolean)
	LogColor(Checked,Colors.Magenta)
	If Checked = True Then
		txtReason.Enabled = False
		txtReason.Text = ""
	End If
End Sub

Sub optReason8_CheckedChange(Checked As Boolean)
	LogColor(Checked,Colors.Magenta)
	If Checked = True Then
		txtReason.Enabled = True
		txtReason.RequestFocus
		IMEkeyboard.ShowKeyboard(txtReason)
	End If
End Sub

#End Region

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

Sub RoundtoCurrency(BD As BigDecimal, DP As Int) As BigDecimal
	BD.Round(BD.Precision - BD.Scale + DP, BD.ROUND_HALF_UP )
	Return BD
End Sub

Private Sub DispInfoMsg(sMsg As String, sTitle As String)
	Dim csTitle, csMsg As CSBuilder
	csTitle.Initialize.Color(Colors.Red).Append(sTitle).PopAll
	csMsg.Initialize.Bold.Color(Colors.DarkGray).Append(CRLF & sMsg).PopAll
	
	Msgbox(csMsg, csTitle)
End Sub

Private Sub DispErrorMsg(sMsg As String, sTitle As String) As Boolean
	Dim csTitle, csMsg As CSBuilder
	Dim blnRetVal As Boolean
	Dim iResp As Int
	Dim icon As B4XBitmap
	csTitle.Initialize.Color(Colors.Red).Append(sTitle).PopAll
	csMsg.Initialize.Bold.Color(Colors.DarkGray).Append(CRLF & sMsg).PopAll
	icon = xui.LoadBitmapResize(File.DirAssets, "error.png", 24dip, 24dip, True)
'	Msgbox(csMsg, csTitle)
	iResp =  Msgbox2(csMsg, csTitle, $"OK"$, "","",icon)
	If iResp = DialogResponse.POSITIVE Then
		blnRetVal = True
	Else
		blnRetVal = False
	End If
	Return blnRetVal
End Sub

Private Sub DispErrorMsg2(sMsg As String, sTitle As String)
	Dim csTitle, csMsg As CSBuilder
	Dim blnRetVal As Boolean
	Dim iResp As Int
	Dim icon As B4XBitmap
	csTitle.Initialize.Color(Colors.Red).Append(sTitle).PopAll
	csMsg.Initialize.Bold.Color(Colors.DarkGray).Append(CRLF & sMsg).PopAll
	icon = xui.LoadBitmapResize(File.DirAssets, "error.png", 24dip, 24dip, True)

	Msgbox2Async(csMsg, csTitle, $"OK"$, "","", icon, False)
End Sub

Private Sub ConfirmWarning(sMsg As String, sTitle As String, sPos As String, sNeg As String, sNeu As String) As Boolean
	Dim bRetVal As Boolean
	Dim Response As Object
	Dim icon As B4XBitmap
	Dim csTitle, csMsg As CSBuilder
	
	csTitle.Initialize.Color(Colors.Red).Append(sTitle).PopAll
	
	csMsg.Initialize.Bold.Color(Colors.Red).Append($"ACCESS DENIED!"$).Pop.Pop
	csMsg.Color(Colors.DarkGray).Append(CRLF & sMsg).PopAll
	
	icon = xui.LoadBitmapResize(File.DirAssets, "error.png", 24dip, 24dip, True)
	
	Response = xui.Msgbox2Async(csMsg, csTitle, sPos, sNeu, sNeg, icon)
	If Response = xui.DialogResponse_Positive Then
		bRetVal = True
	Else
		bRetVal = False
	End If
	
	Return bRetVal
End Sub
#End Region

Private Sub CheckImplosive(iPrevCum As Int, iPresCum As Int) As String
	Dim iRetVal As Int
	Dim iPercentageRate As Int
	Dim RS As Cursor
	Try
		iRetVal = 0
		iPercentageRate = 0
		Starter.strCriteria = "SELECT * FROM ImplosivesRate " & _
							  "WHERE " & iPrevCum & " BETWEEN StartCum AND EndCum"
		LogColor (Starter.strCriteria, Colors.Yellow)
		RS = Starter.DBCon.ExecQuery(Starter.strCriteria)
		
		LogColor(RS.RowCount, Colors.Cyan)
		
		If RS.RowCount <= 0 Then
			iRetVal = 0
		Else
			RS.Position = 0
			If iPrevCum = 0 Then
				If iPresCum >= 100 Then
					iRetVal = 2 'High
				End If
			Else If iPresCum > iPrevCum Then 'Is it too high?
				iPercentageRate = ((iPresCum - iPrevCum) / iPrevCum)
				If iPercentageRate >= RS.GetInt("IncRate") Then
					iRetVal = 2 'High
				Else
					iRetVal = 0 'Normal
				End If
			Else If iPresCum < iPrevCum Then 'Is it too low?
				iPercentageRate = ((iPrevCum - iPresCum) / iPrevCum)
				If iPercentageRate >= RS.GetInt("DecRate") Then
					iRetVal = 1 'Low
				Else
					iRetVal = 0 'Normal
				End If
			Else
				iRetVal = 0 'Normal
			End If
		End If
		
	Catch
		Log(LastException)
		iRetVal = 0
	End Try
	RS.Close
	Return iRetVal
End Sub

Private Sub Timer1_Tick
'	lblReadStatus.Visible = Not(lblReadStatus.Visible)
End Sub

#Region Reprint High Bill
Sub txtPresRdgConfirmReprint_TextChanged (Old As String, New As String)
	If txtPresRdg.Text = New Or sPresentReading = New Then
		btnHBConfirmSaveAndRePrint.Enabled = True
	Else
		btnHBConfirmSaveAndRePrint.Enabled = False
	End If
End Sub

Sub txtPresRdgConfirmReprint_EnterPressed
	
End Sub

Sub btnHBConfirmSaveAndRePrint_Click
	
End Sub

Sub btnHBConfirmReprintCancel_Click
	
End Sub

#End Region

#Region SuperHighBill
Sub pnlSuperHB_Touch (Action As Int, X As Float, Y As Float)
	
End Sub

Private Sub ShowSuperHBConfirmation
	Dim sMsg = $"FINAL WARNING! You are about to bill a Customer with more than 100 Cubic Meter!"$ As String
	soundsAlarmChannel.Play(SoundID,1,1,1,0,1)
	vibration.vibratePattern(vibratePattern,0)
	pnlSuperHB.Visible = True
	If  Not(TalkBackIsActive) Then
		TTS1.Speak(sMsg, False)
	End If
	
	cdConfirmRdg.Initialize2(Colors.Black, 0,0, Colors.Transparent)
	txtSuperHBPresRdg.Background = cdConfirmRdg

	cdButtonCancel.Initialize(0xFFDC143C, 15)
	btnSuperHBCancel.Background = cdButtonCancel
	
	cdButtonSaveAndPrint.Initialize(0xFF1976D2, 25)
	btnSuperHBSave.Background = cdButtonSaveAndPrint

	txtSuperHBPresRdg.Text = ""
	btnSuperHBSave.Enabled = False
	txtSuperHBPresRdg.RequestFocus
	cKeyboard.HideKeyboard
	IMEkeyboard.ShowKeyboard(txtSuperHBPresRdg)
	
	Select Case intSaveOnly
		Case 0
			btnSuperHBSave.Text = "SAVE"
		Case 1
			btnSuperHBSave.Text = "SAVE AND PRINT"
	End Select

End Sub

Sub txtSuperHBPresRdg_TextChanged (Old As String, New As String)
	If txtPresRdg.Text = New Or sPresentReading = New Then
		btnSuperHBSave.Enabled = True
	Else
		btnSuperHBSave.Enabled = False
	End If
	
End Sub

Sub txtSuperHBPresRdg_EnterPressed
	If btnSuperHBSave.Enabled = False Then Return
	btnSuperHBSave_Click
End Sub

Sub btnSuperHBSave_Click
	pnlSuperHB.Visible = False
	TTS1.Stop

	vibration.vibrateCancel
	soundsAlarmChannel.Stop(SoundID)
	
	intCurrPos = rsLoadedBook.Position
	
	Select Case intSaveOnly
		Case 0
			SaveReading(0, ReadID, AcctID)
		Case 1
			SaveReading(1, ReadID, AcctID)
			PrintBillData(ReadID)
	End Select

	If RetrieveRecords(GlobalVar.BookID)=False Then Return
	If intCurrPos < (RecCount - 1) Then
		rsLoadedBook.Position = intCurrPos + 1
	Else
		rsLoadedBook.Position = intCurrPos
	End If
	DisplayRecord
	ButtonState
	If GetUnusualCount(GlobalVar.BookID) = True Then
		UpdateBadge("Flag", AddBadgeToIcon(flagBitmap, intTotal))
	End If
	intSaveOnly = 0
	blnSuperHighBill = False
	
End Sub

Sub btnSuperHBCancel_Click
	TTS1.Stop
	soundsAlarmChannel.Stop(SoundID)
	vibration.vibrateCancel
	pnlSuperHB.Visible = False
	txtPresRdg.RequestFocus
	txtPresRdg.SelectAll
	IMEkeyboard.HideKeyboard

'	IMEkeyboard.ShowKeyboard(txtPresRdg)
	intSaveOnly = 0
	blnSuperHighBill = False
End Sub

Sub TTS1_Ready (Success As Boolean)
	If Success Then
		Dim R As Reflector
		R.Target = TTS1
		R.RunMethod2("setEngineByPackageName", "com.google.android.tts", "java.lang.String")
	Else
		Msgbox("Error initializing TTS engine.", "")
	End If
End Sub


Sub TalkBackIsActive As Boolean
    
	Dim blnReturnValue As Boolean = False
	Dim context As JavaObject
	Dim AccessibilityManager As JavaObject = context.InitializeContext.RunMethod("getSystemService", _
        Array("accessibility"))

	If AccessibilityManager.IsInitialized Then
		Dim services As List = AccessibilityManager.RunMethod("getEnabledAccessibilityServiceList", _
            Array(1)) 'FEEDBACK_SPOKEN

		If services.Size > 0 Then
			blnReturnValue = True
		End If
	End If
	LogColor (blnReturnValue, Colors.Yellow)
	Return blnReturnValue
End Sub
#End Region

#Region New Msgbox
Private Sub WarningMsg(sTitle As String, sMsg As String)
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
	soundsAlarmChannel.Stop(SoundID)
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

#End Region

Private Sub cKeyboard_onKeyDown(ViewTag As Object, PrimaryKeyCode As Int)
	Log(PrimaryKeyCode)
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
