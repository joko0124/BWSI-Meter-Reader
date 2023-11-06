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
	Dim PrintLogo() As Byte
	Dim oStream As AsyncStreams
	Private Res As Int
	
	Private flagBitmap As Bitmap

	Dim Logobmp As Bitmap
	Dim WoosimCmd As JavaObject
	Dim WoosimImage As JavaObject
	Dim Logo As Bitmap
	Public Permissions As RuntimePermissions
End Sub

Sub Globals

	Private ToolBar As ACToolBarDark
	Private ActionBarButton As ACActionBar
	Private xmlIcon As XmlLayoutBuilder

	Private snack As DSSnackbar
	Dim CD As ColorDrawable

	'Material Dialog
	Private MatDialogBuilder As MaterialDialogBuilder
	
	'Recordset
	Private rsLoadedBook As Cursor

	Private rsUnReadAcct As Cursor
	Private rsReadAcct As Cursor
	Private rsAllAcct As Cursor
	Private rsUnusualReading As Cursor
	

	'Fixed
	Private BillNo As String
	Private AcctNo As String
	Private AcctName As String
	Private AcctAddress As String
	Private MeterNo As String
	Private GDeposit As Double

	Private PrevRdg As Double
	Private PresRdg As String

	Private DueDate As String
	Private AddCons As Double
	Private TotalCum As Double
	Private BasicAmt As Double

	Private PCAAmt As Double
	Private AddToBillAmt As Double
	Private AdvPayment As Double
	Private PenaltyAmt As Double
	Private TotalDueAmtAfterSC As Double

	'Variants
	Private CurrentAmt As Double
	Private ArrearsAmt As Double
	Private WithDueDate As Int
	Private TotalDueAmt As Double
	Private SeniorOnBeforeAmt As Double
	Private SeniorAfterAmt As Double
	Private TotalDueAmtBeforeSC As Double


	'Printing
	Private BookSeq As String
	Private CustClass As String
	Private sBranchName As String
	Private sBranchAddress As String
	Private sBranchContactNo As String
	Private sTinNo As String
	Private sBranchCode As String
	Private sBillPeriod As String
		
	'to String
	Private BookID As Int
	Private BillDate As String
	Private DateFrom As String
	Private DateTo As String
	
	Private sPresRdg As String
	Private sPrevRdg As String
	Private sAddCons As String
	Private sTotalCum As String

	Private sGDeposit As String
	Private sBasicAmt As String
	Private sPCAAmt As String
	Private sCurrentAmt As String
	Private sSeniorOnBeforeAmt As String
	Private sAddToBillAmt As String
	Private sArrearsAmt As String
	Private sAdvPayment As String
	Private sTotalDueAmt As String
	Private sPenaltyAmt As String
	Private sSeniorAfterAmt As String
	Private sTotalDueAmtAfterSC As String

	Private vibration As B4Avibrate
	Private vibratePattern() As Long
	Private btnPrint As ACButton

	Private lblAcctNo As Label
	Private lblAcctName As Label
	Private lblAddress As Label
	Private lblMeterNo As Label
	Private lblBookSeq As Label
	Private lblClass As Label
	Private lblGD As Label

	Private lblPresRdg As Label
	Private lblPrevRdg As Label
	Private lblAddCons As Label
	Private lblTotalCons As Label

	Private lblBasic As Label
	Private lblPCA As Label
	Private lblCurrent As Label
	Private lblSCDisc10 As Label
	Private lblOthers As Label
	Private lblArrears As Label
	Private lblAdvances As Label
	Private lblTotDue As Label

	Private lblDueDate As Label
	Private lblPenalty As Label
	Private lblSCDisc5 As Label
	Private lblAfterDueAmt As Label
	Private btnClose As ACButton
End Sub

Sub Activity_Create(FirstTime As Boolean)
	MyScale.SetRate(0.5)
	Activity.LoadLayout("BillDetails")
	
	Log(GlobalVar.BillID)
	GetBillDetais(GlobalVar.BillID)
	DisplayRecords
	
	GlobalVar.CSTitle.Initialize.Size(17).Bold.Append($"BILL NO. "$ & BillNo).PopAll
	GlobalVar.CSSubTitle.Initialize.Size(14).Append($"For the Month of "$ & sBillPeriod).PopAll
	
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
	BTAdmin.Initialize("Admin")
	Serial1.Initialize("Printer")
	GetBillDetais(GlobalVar.BillID)

	CD.Initialize2(0xFF1976D2, 25, 0,0xFF000000)
	btnClose.Background = CD
	btnPrint.Background = CD
End Sub

Sub Activity_KeyPress (KeyCode As Int) As Boolean 'Return True to consume the event
	If KeyCode = 4 Then
		ToolBar_NavigationItemClick
		Return True
	Else
		Return False
	End If
End Sub

Sub Activity_Resume
	BTAdmin.Initialize("Admin")
	Serial1.Initialize("Printer")
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

#Region MainMenu
Sub Activity_CreateMenu(Menu As ACMenu)
End Sub

Sub ToolBar_NavigationItemClick
	Activity.Finish
End Sub

Sub ToolBar_MenuItemClick (Item As ACMenuItem)
End Sub
#End Region

#Region Printing
Private Sub PrintBillData(iReadID As Int)
	Dim rsData As Cursor

	ProgressDialogShow2($"Bill Statement Printing..."$, False)

	Try
		Starter.strCriteria = "SELECT * FROM tblReadings WHERE ReadID = " & iReadID
		rsData = Starter.DBCon.ExecQuery(Starter.strCriteria)

		If rsData.RowCount > 0 Then
			rsData.Position = 0
			AcctNo = rsData.GetString("AcctNo")
			AcctName = rsData.GetString("AcctName")
			AcctAddress = rsData.GetString("AcctAddress")
			MeterNo = rsData.GetString("MeterNo")
			BookID = rsData.GetInt("BookID")
			BookSeq = rsData.GetString("BookNo") & "-" & rsData.GetString("SeqNo")
			CustClass = rsData.GetString("AcctClass") & "-" & rsData.GetString("AcctSubClass")
			GDeposit = Round2(rsData.GetDouble("GDeposit"),2)
			BillNo = rsData.GetString("BillNo")
			BillDate = rsData.GetString("DateRead")
			DateFrom = rsData.GetString("DateFrom")
			DateTo = rsData.GetString("DateRead")

			PresRdg = rsData.GetInt("PresRdg")
			PrevRdg = rsData.GetInt("PrevRdg")
			AddCons = rsData.GetInt("AddCons")
			TotalCum = rsData.GetInt("TotalCum")

			BasicAmt =Round2(rsData.GetDouble("BasicAmt"),2)
			PCAAmt = Round2(rsData.GetDouble("PCAAmt"),2)
			CurrentAmt = Round2(rsData.GetDouble("CurrentAmt"),2)
			SeniorOnBeforeAmt = Round2(rsData.GetDouble("SeniorOnBeforeAmt"),2)
			AddToBillAmt = Round2(rsData.GetDouble("AddToBillAmt"),2)'Others
			ArrearsAmt = Round2(rsData.GetDouble("ArrearsAmt"), 2)
			AdvPayment = Round2(rsData.GetDouble("AdvPayment"), 2)
			TotalDueAmt = Round2(rsData.GetDouble("TotalDueAmt"), 2)
			WithDueDate = rsData.GetInt("WithDueDate")
			If WithDueDate = 1 Then
				DueDate = rsData.GetString("DueDate")
			Else
				DueDate = ""
			End If
			PenaltyAmt = Round2(rsData.GetDouble("PenaltyAmt"), 2)
			SeniorAfterAmt = Round2(rsData.GetDouble("SeniorAfterAmt"), 2)
			TotalDueAmtAfterSC = Round2(rsData.GetDouble("TotalDueAmtAfterSC"), 2)
		End If
		
		sBranchName = GlobalVar.SF.Right(GlobalVar.BranchName, GlobalVar.BranchName.Length - 7)
		If GlobalVar.BranchID = 28 Or GlobalVar.BranchID = 29 Or  GlobalVar.BranchID = 30 Then
			sBranchAddress = GlobalVar.BranchAddress & CRLF & Chr(27) & "!" & Chr(1) & "Guiguinto, Bulacan"
		Else
			sBranchAddress = GlobalVar.BranchAddress
		End If
		sBranchContactNo = GlobalVar.BranchContactNo
		sTinNo = GlobalVar.BranchTINumber
		sBranchCode = GlobalVar.BranchCode
		sBillPeriod = GlobalVar.BillPeriod
		
		'to String
		sPresRdg = PresRdg
		sPrevRdg = PrevRdg
		sAddCons = AddCons
		sTotalCum = TotalCum

		sGDeposit = GDeposit
		sBasicAmt = BasicAmt
		sPCAAmt = PCAAmt
		sCurrentAmt = CurrentAmt
		sSeniorOnBeforeAmt = SeniorOnBeforeAmt
		sAddToBillAmt = AddToBillAmt'Others
		sArrearsAmt = ArrearsAmt
		sAdvPayment = AdvPayment
		sTotalDueAmt = TotalDueAmt
		sPenaltyAmt = PenaltyAmt
		sSeniorAfterAmt = SeniorAfterAmt
		sTotalDueAmtAfterSC = TotalDueAmtAfterSC

'		& Chr(27) & "!" & Chr(1) & "Guiguinto, Bulacan" & Chr(10) _
		PrintBuffer = Chr(27) & "@" _
					& Chr(27) & Chr(97) & Chr(49) _
					& Chr(27) & "!" & Chr(33) & sBranchName & Chr(10) _
					& Chr(27) & "!" & Chr(1) & Chr(27) & "t" & Chr(14) & sBranchAddress & Chr(10) _
					& Chr(27) & "!" & Chr(1) & sBranchContactNo & Chr(10) _
					& Chr(27) & "!" & Chr(1) & "TIN NO: " & sTinNo & Chr(10) _
					& Chr(27) & "!" & Chr(8) & "Branch Code: " & sBranchCode & CRLF & Chr(10) _
					& Chr(27) & "!" & Chr(33) & "STATEMENT OF ACCOUNT" & Chr(10) _
					& Chr(27) & "!" & Chr(1) & "For the Month of " & sBillPeriod & Chr(10) _
					& Chr(27) & "!" & Chr(1) & "------------------------------------------" & Chr(10) _
					& Chr(27) & Chr(97) & Chr(48) _
					& Chr(27) & "!" & Chr(16) & "Account No: " & AcctNo  & Chr(10) _
					& Chr(27) & "!" & Chr(16) & Chr(27) & "t" & Chr(16) & GlobalVar.SF.Upper(AcctName) & Chr(10) _
					& Chr(27) & "!" & Chr(1) & Chr(27) & "t" & Chr(16) & GlobalVar.SF.Left(TitleCase(AcctAddress),42) & Chr(10) _
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
					& Chr(27) & "!" & Chr(0) & "BASIC              " & AddWhiteSpaces(NumberFormat2(sBasicAmt,1, 2, 2,True)) & NumberFormat2(sBasicAmt,1, 2, 2,True) &  Chr(10) _
					& Chr(27) & "!" & Chr(0) & "PCA                " & AddWhiteSpaces(NumberFormat2(sPCAAmt,1, 2, 2,True)) & NumberFormat2(sPCAAmt,1, 2, 2,True) &  Chr(10) _
					& Chr(27) & "!" & Chr(16) & "CURRENT BILL       " & AddWhiteSpaces(NumberFormat2(sCurrentAmt,1, 2, 2,True)) & NumberFormat2(sCurrentAmt,1, 2, 2,True) &  Chr(10) _
					& Chr(27) & "!" & Chr(0) & "10% SC DISCOUNT    " & AddWhiteSpaces("(" & NumberFormat2(sSeniorOnBeforeAmt,1, 2, 2,True) & ")") & "(" & NumberFormat2(sSeniorOnBeforeAmt,1, 2, 2,True) & ")" &  Chr(10) _
					& Chr(27) & "!" & Chr(0) & "OTHERS             " & AddWhiteSpaces(NumberFormat2(sAddToBillAmt,1, 2, 2,True)) & NumberFormat2(sAddToBillAmt,1, 2, 2,True) &  Chr(10) _
					& Chr(27) & "!" & Chr(0) & "ARREARS            " & AddWhiteSpaces(NumberFormat2(sArrearsAmt,1, 2, 2,True)) & NumberFormat2(sArrearsAmt,1, 2, 2,True) &  Chr(10) _
					& Chr(27) & "!" & Chr(0) & "ADVANCES           " & AddWhiteSpaces("(" & NumberFormat2(sAdvPayment,1, 2, 2,True) & ")") & "(" & NumberFormat2(sAdvPayment,1, 2, 2,True) & ")" &  Chr(10) _
					& Chr(27) & "!" & Chr(16) & "TOTAL DUE          " & AddWhiteSpaces(NumberFormat2(sTotalDueAmt,1, 2, 2,True)) & NumberFormat2(sTotalDueAmt,1, 2, 2,True) & Chr(10)

		If DBaseFunctions.IsWithDueDate(iReadID) = True Then
			PrintBuffer = PrintBuffer & Chr(27) & Chr(97) & Chr(48) _
					& Chr(27) & "!" & Chr(1) & "------------------------------------------" & Chr(10) _
					& Chr(27) & "!" & Chr(8) & "PAYMENT AFTER DUE DATE" & Chr(10) _
					& Chr(27) & "!" & Chr(0) & "DUE DATE           " & AddWhiteSpaces(DueDate) & DueDate &  Chr(10) _
					& Chr(27) & "!" & Chr(0) & "10% PENALTY        " & AddWhiteSpaces(NumberFormat2(sPenaltyAmt,1, 2, 2,True)) & NumberFormat2(sPenaltyAmt,1, 2, 2,True) &  Chr(10) _
					& Chr(27) & "!" & Chr(0) & "5% SC DISCOUNT     " & AddWhiteSpaces("(" & NumberFormat2(sSeniorAfterAmt,1, 2, 2,True) & ")") & "(" & NumberFormat2(sSeniorAfterAmt,1, 2, 2,True) & ")" &  Chr(10) _
					& Chr(27) & "!" & Chr(16) & "TOTAL              " & AddWhiteSpaces(NumberFormat2(sTotalDueAmtAfterSC,1, 2, 2,True)) & NumberFormat2(sTotalDueAmtAfterSC,1, 2, 2,True) & Chr(10) _
					& Chr(27) & "!" & Chr(1) & "==========================================" & Chr(10)
		Else
			PrintBuffer = PrintBuffer & Chr(27) & "!" & Chr(1) & "==========================================" & Chr(10)
		End If
		
					
		If ArrearsAmt > 0 Then
			PrintBuffer = PrintBuffer & Chr(27) & Chr(97) & Chr(49) _
					      & Chr(27) & "!" & Chr(17) & "NOTICE OF DISCONNECTION" & CRLF & Chr(10)
		End If
		
		PrintBuffer = PrintBuffer & Chr(27) & Chr(97) & Chr(48) _
					  & Chr(27) & "!" & Chr(9) & "NOTE:" &  Chr(10) _
					  & Chr(27) & "!" & Chr(1) & "    Disregard Arrears if Payment has been made. Please pay on or before due date to avoid Disconnection." & CRLF & Chr(10) _
					  & Chr(10) & Chr(27) & Chr(97) & Chr(10)
		StartPrinter
	Catch
		snack.Initialize("", Activity, $"Unable to Print Bill Statement due to "$ & LastException.Message ,snack.DURATION_SHORT)
		SetSnackBarBackground(snack, Colors.White)
		SetSnackBarTextColor(snack, Colors.Red)
		snack.Show
		Log(LastException)
	End Try

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

Sub StartPrinter
	
	PairedDevices.Initialize
	
	Try
		PairedDevices = Serial1.GetPairedDevices
	Catch
		Msgbox("Getting Paired Devices","Printer Error")
		TMPrinter.Close
		Serial1.Disconnect
	End Try

	If PairedDevices.Size = 0 Then
		Msgbox("Error Connecting to Printer - Either No Paired Bluetooth Printer or Printer Not Found!", Application.LabelName)
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
			Msgbox("Connecting","Printer Error")
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
		Dim initPrinter() As Byte
	
		ProgressDialogHide
		TMPrinter.Initialize2(Serial1.OutputStream, "windows-1252")
		oStream.Initialize(Serial1.InputStream, Serial1.OutputStream, "LogoPrint")
		Logo.Initialize(File.DirAssets, GlobalVar.BranchLogo)
		Logobmp = CreateScaledBitmap(Logo, Logo.Width, Logo.Height)
		Log(DeviceName)
	
		If GlobalVar.SF.Upper(DeviceName) = "WOOSIM" Then
			WoosimCmd.InitializeStatic("com.woosim.printer.WoosimCmd")
			WoosimImage.InitializeStatic("com.woosim.printer.WoosimImage")
			
			initPrinter = WoosimCmd.RunMethod("initPrinter",Null)
			If GlobalVar.BranchID = 28 Or GlobalVar.BranchID = 29 Or GlobalVar.BranchID = 30 Then
				PrintLogo = WoosimImage.RunMethod("printBitmap", Array (0, 0, 400, 200, Logobmp))
			Else
				PrintLogo = WoosimImage.RunMethod("printBitmap", Array (0, 0, 400, 150, Logobmp))
			End If
			oStream.Write(initPrinter)
			oStream.Write(WoosimCmd.RunMethod("setPageMode",Null))
			oStream.Write(PrintLogo)
			oStream.Write(WoosimCmd.RunMethod("PM_setStdMode",Null))
		Else
			EPSONPrint.Initialize
			PrintLogo =  EPSONPrint.getBytesForBitmap2(GlobalVar.BranchLogo,True)
		End If
		oStream.Write(PrintLogo)
		Sleep(500)
		TMPrinter.WriteLine(PrintBuffer)
		Log(PrintBuffer)
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

#End Region

Private Sub IsPrintableData(iReadID As Int) As Boolean
	Dim blnRetVal As Boolean
	Dim rsValidData As Cursor
	Try
		Starter.strCriteria = "SELECT * FROM tblReadings WHERE ReadID = " & iReadID & " " & _
							  "AND WasRead = 1 " & _
							  "AND WasMissCoded = 0"
		rsValidData = Starter.DBCon.ExecQuery(Starter.strCriteria)
		If rsValidData.RowCount > 0 Then
			blnRetVal = True
		Else
			blnRetVal = False
		End If
	Catch
		blnRetVal = True
		rsValidData.Close
		Log(LastException)
	End Try
	rsValidData.Close
	Return blnRetVal
End Sub

Private Sub GetBillDetais(dBillID As Double)
	Dim rsBillDetails As Cursor
	Try
		sBillPeriod = GlobalVar.BillPeriod

		Starter.strCriteria = "SELECT * FROM tblReadings WHERE ReadID = " & dBillID
		rsBillDetails = Starter.DBCon.ExecQuery(Starter.strCriteria)
		If rsBillDetails.RowCount > 0 Then
			rsBillDetails.Position = 0
			AcctNo = rsBillDetails.GetString("AcctNo")
			AcctName = rsBillDetails.GetString("AcctName")
			AcctAddress = rsBillDetails.GetString("AcctAddress")
			MeterNo = rsBillDetails.GetString("MeterNo")
			BookSeq = rsBillDetails.GetString("BookNo") & "-" & rsBillDetails.GetString("SeqNo")
			CustClass = rsBillDetails.GetString("AcctClass") & "-" & rsBillDetails.GetString("AcctSubClass")
			GDeposit = Round2(rsBillDetails.GetDouble("GDeposit"),2)
			BillNo = rsBillDetails.GetString("BillNo")

			PresRdg = rsBillDetails.GetInt("PresRdg")
			PrevRdg = rsBillDetails.GetInt("PrevRdg")
			AddCons = rsBillDetails.GetInt("AddCons")
			TotalCum = rsBillDetails.GetInt("TotalCum")

			BasicAmt =Round2(rsBillDetails.GetDouble("BasicAmt"),2)
			PCAAmt = Round2(rsBillDetails.GetDouble("PCAAmt"),2)
			CurrentAmt = Round2(rsBillDetails.GetDouble("CurrentAmt"),2)
			SeniorOnBeforeAmt = Round2(rsBillDetails.GetDouble("SeniorOnBeforeAmt"),2)
			AddToBillAmt = Round2(rsBillDetails.GetDouble("AddToBillAmt"),2)'Others
			ArrearsAmt = Round2(rsBillDetails.GetDouble("ArrearsAmt"), 2)
			AdvPayment = Round2(rsBillDetails.GetDouble("AdvPayment"), 2)
			TotalDueAmt = Round2(rsBillDetails.GetDouble("TotalDueAmt"), 2)
			WithDueDate = rsBillDetails.GetInt("WithDueDate")
			If WithDueDate = 1 Then
				DueDate = rsBillDetails.GetString("DueDate")
			Else
				DueDate = $"N/A"$
			End If
			PenaltyAmt = Round2(rsBillDetails.GetDouble("PenaltyAmt"), 2)
			SeniorAfterAmt = Round2(rsBillDetails.GetDouble("SeniorAfterAmt"), 2)
			TotalDueAmtAfterSC = Round2(rsBillDetails.GetDouble("TotalDueAmtAfterSC"), 2)
		End If
		'to String
		sPresRdg = PresRdg
		sPrevRdg = PrevRdg
		sAddCons = AddCons
		sTotalCum = TotalCum

		sGDeposit = GDeposit
		sBasicAmt = BasicAmt
		sPCAAmt = PCAAmt
		sCurrentAmt = CurrentAmt
		sSeniorOnBeforeAmt = SeniorOnBeforeAmt
		sAddToBillAmt = AddToBillAmt'Others
		sArrearsAmt = ArrearsAmt
		sAdvPayment = AdvPayment
		sTotalDueAmt = TotalDueAmt
		sPenaltyAmt = PenaltyAmt
		sSeniorAfterAmt = SeniorAfterAmt
		sTotalDueAmtAfterSC = TotalDueAmtAfterSC
	Catch
		Log(LastException)
	End Try
	rsBillDetails.Close
End Sub

Private Sub DisplayRecords
	lblAcctNo.Text = AcctNo
	lblAcctName.Text = AcctName
	lblAddress.Text = TitleCase(AcctAddress)
	lblMeterNo.Text = MeterNo
	lblBookSeq.Text = BookSeq
	lblClass.Text = CustClass
	lblGD.Text = NumberFormat2(sGDeposit,1,2,2,True)

	lblPresRdg.Text = sPresRdg
	lblPrevRdg.Text = sPrevRdg
	lblAddCons.Text = sAddCons
	lblTotalCons.Text = sTotalCum

	lblBasic.Text = NumberFormat2(sBasicAmt,1, 2, 2,True)
	lblPCA.Text = NumberFormat2(sPCAAmt,1, 2, 2,True)
	lblCurrent.Text = NumberFormat2(sCurrentAmt,1, 2, 2,True)
	lblSCDisc10.Text = "(" & NumberFormat2(sSeniorOnBeforeAmt,1, 2, 2,True) & ")"
	lblOthers.Text = NumberFormat2(sAddToBillAmt,1, 2, 2,True)
	lblArrears.Text = NumberFormat2(sArrearsAmt,1, 2, 2,True)
	lblAdvances.Text = "(" & NumberFormat2(sAdvPayment,1, 2, 2,True) & ")"
	lblTotDue.Text = NumberFormat2(sTotalDueAmt,1, 2, 2,True)

	lblDueDate.Text = DueDate
	If DueDate = "N/A" Then
		lblDueDate.TextColor = Colors.Red
	Else
		lblDueDate.TextColor = GlobalVar.PriColor
	End If
	lblPenalty.Text = NumberFormat2(sPenaltyAmt,1, 2, 2,True)
	lblSCDisc5.Text = "(" & NumberFormat2(sSeniorAfterAmt,1, 2, 2,True) & ")"
	lblAfterDueAmt.Text = NumberFormat2(sTotalDueAmtAfterSC,1, 2, 2,True)
End Sub

Private Sub ClearData
	lblAcctNo.Text = ""
	lblAcctName.Text = ""
	lblAddress.Text = ""
	lblMeterNo.Text = ""
	lblBookSeq.Text = ""
	lblClass.Text = ""
	lblGD.Text = ""

	lblPresRdg.Text = ""
	lblPrevRdg.Text = ""
	lblAddCons.Text = ""
	lblTotalCons.Text = ""

	lblBasic.Text = ""
	lblPCA.Text = ""
	lblCurrent.Text = ""
	lblSCDisc10.Text = ""
	lblOthers.Text = ""
	lblArrears.Text = ""
	lblAdvances.Text = ""
	lblTotDue.Text = ""

	lblDueDate.Text = ""
	lblPenalty.Text = ""
	lblSCDisc5.Text = ""
	lblAfterDueAmt.Text = ""
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



Sub btnPrint_Click
	PrintBillData(GlobalVar.BillID)
End Sub

Sub btnClose_Click
	ToolBar_NavigationItemClick
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

Private Sub DispInfoMsg(sMsg As String, sTitle As String)
	Dim csTitle, csMsg As CSBuilder
	csTitle.Initialize.Color(Colors.Red).Append(sTitle).PopAll
	csMsg.Initialize.Bold.Color(Colors.DarkGray).Append(CRLF & sMsg).PopAll
	
	Msgbox(csMsg, csTitle)
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
