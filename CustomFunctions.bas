﻿B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=StaticCode
Version=7.8
@EndOfDesignText@
Sub Process_Globals
	Private xui As XUI
	Private KVS As KeyValueStore
End Sub

Public Sub ConfirmYN(sMsg As String, sTitle As String, sPositive As String, sCancel As String, sNegative As String) As Boolean
	Dim blnRetVal As Boolean
	Dim bytAns As Byte
	
	Dim csMsg, csTitle, csPositive, csCancel, csNegative As CSBuilder

	Dim icon As B4XBitmap
	csTitle.Initialize.Color(Colors.Red).Append(sTitle).PopAll
	
	csMsg.Initialize.Bold.Color(Colors.DarkGray).Append(CRLF & sMsg).PopAll
	csPositive.Initialize.Bold.Size(20).Color(GlobalVar.PriColor).Append(sPositive).PopAll
	csNegative.Initialize.Bold.Size(20).Color(Colors.Red).Append(sPositive).PopAll
	csCancel.Initialize.Bold.Size(20).Color(Colors.Black).Append(sPositive).PopAll
	
	icon = xui.LoadBitmapResize(File.DirAssets, "question_mark.png", 24dip, 24dip, True)

	csMsg.Initialize.Bold.Color(Colors.DarkGray).Append(CRLF & sMsg).PopAll
	csTitle.Initialize.Color(GlobalVar.PriColor).Append(sTitle).PopAll
	
	bytAns = Msgbox2(csMsg, csTitle, sPositive, sCancel, sNegative, icon)
	If bytAns = DialogResponse.Positive Then
		blnRetVal = True
	Else
		blnRetVal = False
	End If
	Return blnRetVal
End Sub

Public Sub SaveUserSettings
	KVS.Initialize(File.DirInternal, "mrandroid.dat")
	
	Dim UserData As Map
	
	UserData.Initialize
	'Server Address
	UserData.Put("ServerAddress", GlobalVar.ServerAddress)

	'User Info
	UserData.Put("UserID", GlobalVar.UserID)
	UserData.Put("EmpName", GlobalVar.EmpName)
	UserData.Put("UserName", GlobalVar.UserName)
	UserData.Put("UserPW", GlobalVar.UserPW)

'	'User Modules
'	UserData.Put("Mod1", GlobalVar.Mod1)
'	UserData.Put("Mod2", GlobalVar.Mod2)
'	UserData.Put("Mod3", GlobalVar.Mod3)
'	UserData.Put("Mod4", GlobalVar.Mod4)
'	UserData.Put("Mod5", GlobalVar.Mod5)
'	UserData.Put("Mod6", GlobalVar.Mod6)

	'Branch Info
'	UserData.Put("CompanyID", GlobalVar.CompanyID)
	UserData.Put("BranchID", GlobalVar.BranchID)
'	UserData.Put("BranchCode", GlobalVar.BranchCode)
'	UserData.Put("BranchName", GlobalVar.BranchName)
'	UserData.Put("BranchAddress", GlobalVar.BranchAddress)
'	UserData.Put("BranchContactNo", GlobalVar.BranchContactNo)
'	UserData.Put("BranchTINumber", GlobalVar.BranchTINumber)
'	UserData.Put("BranchLogo", GlobalVar.BranchLogo)
'	UserData.Put("WithMeterCharges", GlobalVar.WithMeterCharges)
'	UserData.Put("WithFranchisedTax", GlobalVar.WithFranchisedTax)
'	UserData.Put("WithSeptageFee", GlobalVar.WithSeptageFee)
'	UserData.Put("WithDisconDate", GlobalVar.WithDisconDate)
'	UserData.Put("SeptageLogo", GlobalVar.SeptageLogo)
'	UserData.Put("SeptageProv", GlobalVar.SeptageProv)

'	'Bill Period
'	UserData.Put("BillYear", GlobalVar.BillYear)
'	UserData.Put("BillMonth", GlobalVar.BillMonth)
'	UserData.Put("BillPeriod", GlobalVar.BillPeriod)
'	UserData.Put("BillMonthName", GlobalVar.BillMonthName)
'
	KVS.Put("user_data",UserData)
End Sub

Public Sub RetrieveUserSettings
	KVS.Initialize(File.DirInternal, "mrandroid.dat")

	Dim RetrieveData As Map
	RetrieveData.Initialize

	RetrieveData = KVS.Get("user_data")

	'Server Address
	GlobalVar.ServerAddress = RetrieveData.Get("ServerAddress")

	'User Info
	GlobalVar.UserID = RetrieveData.Get("UserID")
	GlobalVar.EmpName = RetrieveData.Get("EmpName")
	GlobalVar.UserName = RetrieveData.Get("UserName")
	GlobalVar.UserPW = RetrieveData.Get("UserPW")

'	'User Modules
'	GlobalVar.Mod1 = RetrieveData.Get("Mod1")
'	GlobalVar.Mod2 = RetrieveData.Get("Mod2")
'	GlobalVar.Mod3 = RetrieveData.Get("Mod3")
'	GlobalVar.Mod4 = RetrieveData.Get("Mod4")
'	GlobalVar.Mod5 = RetrieveData.Get("Mod5")
'	GlobalVar.Mod6 = RetrieveData.Get("Mod6")

	'Branch Info
'	GlobalVar.CompanyID = RetrieveData.Get("CompanyID")
	GlobalVar.BranchID = RetrieveData.Get("BranchID")
'	GlobalVar.BranchCode = RetrieveData.Get("BranchCode")
'	GlobalVar.BranchName = RetrieveData.Get("BranchName")
'	GlobalVar.BranchAddress = RetrieveData.Get("BranchAddress")
'	GlobalVar.BranchContactNo = RetrieveData.Get("BranchContactNo")
'	GlobalVar.BranchTINumber = RetrieveData.Get("BranchTINumber")
'	GlobalVar.BranchLogo = RetrieveData.Get("BranchLogo")
'	GlobalVar.WithMeterCharges = RetrieveData.Get("WithMeterCharges")
'	GlobalVar.WithFranchisedTax = RetrieveData.Get("WithFranchisedTax")
'	GlobalVar.WithSeptageFee = RetrieveData.Get("WithSeptageFee")
'	GlobalVar.WithDisconDate = RetrieveData.Get("WithDisconDate")
'	GlobalVar.SeptageLogo = RetrieveData.Get("SeptageLogo")
'	GlobalVar.SeptageProv = RetrieveData.Get("SeptageProv")
'
'	'Bill Period
'	GlobalVar.BillYear = RetrieveData.Get("BillYear")
'	GlobalVar.BillMonth = RetrieveData.Get("BillMonth")
'	GlobalVar.BillPeriod = RetrieveData.Get("BillPeriod")
'	GlobalVar.BillMonthName = RetrieveData.Get("BillMonthName")

	'//////////////////////////////////////////////////////////////////////////////////////
	'Server Address
	Log($"Server IP: "$ & GlobalVar.ServerAddress)

	'User Info
	Log($"User ID: "$ & GlobalVar.UserID)
	Log($"Employee Name: "$ & GlobalVar.EmpName)
	Log($"User Name: "$ & GlobalVar.UserName)
	Log($"User Password: "$ & GlobalVar.UserPW)

	'User Modules
	Log($"Module 1: "$ & GlobalVar.Mod1)
	Log($"Module 2: "$ & GlobalVar.Mod2)
	Log($"Module 3: "$ & GlobalVar.Mod3)
	Log($"Module 4: "$ & GlobalVar.Mod4)
	Log($"Module 5: "$ & GlobalVar.Mod5)
	Log($"Module 6: "$ & GlobalVar.Mod6)

	'Branch Info
	Log($"Company ID: "$ & GlobalVar.CompanyID)
	Log($"Branch ID: "$ & GlobalVar.BranchID)
	Log($"Branch Code: "$ & GlobalVar.BranchCode)
	Log($"Branch Name: "$ & GlobalVar.BranchName)
	Log($"Branch Address: "$ & GlobalVar.BranchAddress)
	Log($"Contact No.: "$ & GlobalVar.BranchContactNo)
	Log($"Tin No.: "$ & GlobalVar.BranchTINumber)
	Log($"Logo: "$ & GlobalVar.BranchLogo)
	Log($"Meter Charges: "$ & GlobalVar.WithMeterCharges)
	Log($"Franchised Tax: "$ & GlobalVar.WithFranchisedTax)
	Log($"Septage Fee: "$ & GlobalVar.WithSeptageFee)
	Log($"Disconnection Date: "$ & GlobalVar.WithDisconDate)
	Log($"Septage Logo: "$ & GlobalVar.SeptageLogo)
	Log($"Septage Provider: "$ & GlobalVar.SeptageProv)

	'Bill Period
	Log($"Bill Year: "$ & GlobalVar.BillYear)
	Log($"Bill Month: "$ & GlobalVar.BillMonth)
	Log($"Billing Period: "$ & GlobalVar.BillPeriod)
	Log($"Bill Month Name: "$ & GlobalVar.BillMonthName)
'//////////////////////////////////////////////////////////////////////////////////////
End Sub

Public Sub ClearUserData
	KVS.Initialize(File.DirInternal, "mrandroid.dat")
	KVS.Remove("user_data")
End Sub

Public Sub IsThereUserData As Boolean
	Dim bRetVal As Boolean

	KVS.Initialize(File.DirInternal, "mrandroid.dat")
	bRetVal = False
	Try
		If KVS.ContainsKey("user_data") Then
			bRetVal = True
		Else
			bRetVal = False
		End If
	Catch
		Log(LastException)
	End Try
	
	Log($"User Data "$ & bRetVal)
	Return bRetVal
End Sub
