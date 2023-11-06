B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=StaticCode
Version=9.3
@EndOfDesignText@
Sub Process_Globals
	Public rsTemp As Cursor
	Dim xui As XUI
End Sub

#Region System Parameters

Public Sub GetCompanyID(iBranchID As Double) As Int
	Dim iRetVal As Int
	Try
		iRetVal = Starter.DBCon.ExecQuerySingleResult("SELECT tblBranches.CompanyID FROM tblBranches WHERE BranchID = " & iBranchID)
	Catch
		ToastMessageShow($"Unable to fetch Branch ID due to "$ & LastException.Message, False)
		Log(LastException)
	End Try
	Return iRetVal
End Sub

Public Sub GetBranchID() As Int
	Dim iRetVal As Int
	iRetVal = 0
	Try
		iRetVal = Starter.DBCon.ExecQuerySingleResult("SELECT BranchID FROM tblSysParam")
	Catch
		ToastMessageShow($"Unable to fetch Branch ID due to "$ & LastException.Message, False)
		Log(LastException)
	End Try
	Return iRetVal
End Sub

Public Sub GetBranchName(iBranchID As Int) As String
	Dim sRetVal As String
	Try
		sRetVal = Starter.DBCon.ExecQuerySingleResult("SELECT BranchName FROM tblBranches WHERE BranchID = " & iBranchID)
	Catch
		ToastMessageShow($"Unable to fetch Branch Name due to "$ & LastException.Message, False)
		Log(LastException)
	End Try
	Return sRetVal
End Sub

Public Sub GetBillYear(iBranchID As Int) As  Double
	Dim dRetVal As Double
	Try
		dRetVal = Starter.DBCon.ExecQuerySingleResult("SELECT BillYear FROM tblSysParam WHERE BranchID = " & iBranchID)
	Catch
		ToastMessageShow($"Unable to fetch Billing Year due to "$ & LastException.Message, False)
		Log(LastException)
	End Try
	Return dRetVal
End Sub

Public Sub GetBillMonth(iBranchID As Int) As Int
	Dim iRetVal As Int
	Try
		iRetVal = Starter.DBCon.ExecQuerySingleResult("SELECT BillMonth FROM tblSysParam WHERE BranchID = " & iBranchID)
	Catch
		ToastMessageShow($"Unable to fetch Billing Month due to "$ & LastException.Message, False)
		Log(LastException)
	End Try
	Return iRetVal
End Sub

Public Sub GetBillPeriod(iBranchID As Int) As String
	Dim sRetVal As String
	Try
		sRetVal = Starter.DBCon.ExecQuerySingleResult("SELECT BillPeriod FROM tblSysParam WHERE BranchID = " & iBranchID)
	Catch
		ToastMessageShow($"Unable to fetch Billing Period due to "$ & LastException.Message, False)
		Log(LastException)
	End Try
	Return sRetVal
End Sub

Public Sub GetServerIP() As String
	Dim sRetVal As String
	Try
		sRetVal = Starter.DBCon.ExecQuerySingleResult("SELECT server_ip FROM android_metadata")
		If sRetVal = "" Then
			sRetVal = "http://192.168.0.2"
		End If
	Catch
		ToastMessageShow($"Unable to fetch Server's IP Address due to "$ & LastException.Message, False)
		Log(LastException)
	End Try
	Log(sRetVal)
	Return sRetVal
End Sub

Public Sub GetBillSettings(iBranchID As Int) As Boolean
	Dim rsBillSettings As Cursor
	Dim blnRetval As Boolean
	Dim BillDate As Long
	Try
		Starter.strCriteria = "SELECT * FROM tblSysParam WHERE BranchID = " & iBranchID
		rsBillSettings = Starter.DBCon.ExecQuery(Starter.strCriteria)
		Log (Starter.strCriteria)
		
		If rsBillSettings.RowCount > 0 Then
			rsBillSettings.Position = 0
			GlobalVar.BillYear = rsBillSettings.GetInt("BillYear")
			GlobalVar.BillMonth = rsBillSettings.GetInt("BillMonth")
			GlobalVar.BillPeriod =rsBillSettings.GetString("BillPeriod")
			DateTime.DateFormat = "MM/dd/yyyy"
			BillDate = DateTime.DateParse(rsBillSettings.GetInt("BillMonth") & "/01/" & rsBillSettings.GetInt("BillYear"))
			Log(BillDate)
			GlobalVar.BillMonthName= DateUtils.GetMonthName(BillDate)
			blnRetval = True
		Else
			blnRetval = False
		End If
	Catch
		blnRetval = False
		ToastMessageShow($"Unable to fetch Billing Settings due to "$ & LastException.Message, False)
		Log(LastException)
	End Try
	rsBillSettings.Close
	Return blnRetval
End Sub
#End Region

#Region Users
Public Sub GetReaderID(sName As String) As Int
	Dim iRetVal As Int
	Try
		Log("SELECT UserID FROM tblUsers WHERE EmpName = '" & sName & "'")
		iRetVal = Starter.DBCon.ExecQuerySingleResult("SELECT UserID FROM tblUsers WHERE EmpName = '" & sName & "'")
	Catch
		ToastMessageShow($"Unable to fetch Reader Name due to "$ & LastException.Message, False)
		Log(LastException)
	End Try
	Return iRetVal
End Sub

Public Sub IsUserExists(sUserName As String, sPassword As String, iBranchID As Int) As Boolean
	Dim blnRetVal As Boolean
	Dim rsUser As Cursor

	Try
		Starter.strCriteria = "SELECT * FROM tblUsers " & _
						  "WHERE UserName = '" & sUserName & "' " & _
						  "AND UserPassword = '" & sPassword & "' " & _
						  "AND BranchID = " & iBranchID

		rsUser = Starter.DBCon.ExecQuery(Starter.strCriteria)

		If rsUser.RowCount > 0 Then
			blnRetVal = True
		Else
			blnRetVal = False
		End If
		rsUser.Close

	Catch
		blnRetVal = False
		ToastMessageShow("Unable to check if User Exists due to " & LastException.Message, False)
		Log(LastException)
	End Try
	Return blnRetVal
End Sub

Public Sub IsUserNameExists(sUserName As String, iBranchID As Int) As Boolean
	Dim blnRetVal As Boolean
	Dim rsUser As Cursor
	Try
		Starter.strCriteria = "SELECT * FROM tblUsers " & _
						  "WHERE UserName = '" & sUserName & "' " & _
						  "AND BranchID = " & iBranchID
		rsUser = Starter.DBCon.ExecQuery(Starter.strCriteria)
		If rsUser.RowCount > 0 Then
			blnRetVal = True
		Else
			blnRetVal = False
		End If
		rsUser.Close
	Catch
		blnRetVal = False
		ToastMessageShow("Unable to check if User's Exists due to " & LastException.Message, False)
		Log(LastException)
	End Try
	Return blnRetVal
End Sub

Public Sub IsPasswordCorrect(sUserName As String, sPass As String, iBranchID As Int) As Boolean
	Dim blnRetVal As Boolean
	Dim rsUser As Cursor
	Try
		Starter.strCriteria = "SELECT * FROM tblUsers " & _
						  "WHERE UserName = '" & sUserName & "' " & _
						  "AND BranchID = " & iBranchID & " " & _
						  "LIMIT 1"
		rsUser = Starter.DBCon.ExecQuery(Starter.strCriteria)
		If rsUser.RowCount > 0 Then
			rsUser.Position = 0
			If rsUser.GetString("UserPassword") = sPass Then
				blnRetVal = True
			Else
				blnRetVal = False				
			End If
		Else
			blnRetVal = False
		End If
		rsUser.Close
	Catch
		blnRetVal = False
		ToastMessageShow("Unable to check if User's Exists due to " & LastException.Message, False)
		Log(LastException)
	End Try
	Return blnRetVal
End Sub

Public Sub IsThereUserInfo(sUserName As String, sUserPassword As String) As Boolean
	Dim rsUser As Cursor
	Dim blnRetVal As Boolean
	Try
		Starter.strCriteria = "SELECT * FROM tblUsers " & _
						  "WHERE UserName = '" & sUserName & "' " & _
						  "AND UserPassword = '" & sUserPassword & "'"
		Log(Starter.strCriteria)
		rsUser = Starter.DBCon.ExecQuery(Starter.strCriteria)
		
		If rsUser.RowCount > 0 Then
			rsUser.Position = 0
			GlobalVar.UserID = rsUser.GetInt("UserID")
			GlobalVar.EmpName =rsUser.GetString("EmpName")
			GlobalVar.UserName = rsUser.GetString("UserName")
			GlobalVar.UserPW = rsUser.GetString("UserPassword")
			GlobalVar.Mod1 = rsUser.GetInt("Module1")
			GlobalVar.Mod2 = rsUser.GetInt("Module2")
			GlobalVar.Mod3 = rsUser.GetInt("Module3")
			GlobalVar.Mod4 = rsUser.GetInt("Module4")
			GlobalVar.Mod5 = rsUser.GetInt("Module5")
			GlobalVar.Mod6 = rsUser.GetInt("Module6")
			blnRetVal = True
		Else
			blnRetVal = False
		End If
		rsUser.Close
	Catch
		blnRetVal = False
		ToastMessageShow($"Unable to fetch User's Info due to "$ & LastException.Message, False)
		Log(LastException)
	End Try
	Return blnRetVal
End Sub

Public Sub GetUserInfo(sUserName As String, sUserPassword As String)
	Dim rsUser As Cursor
	Try
		Starter.strCriteria = "SELECT * FROM tblUsers " & _
						  "WHERE UserName = '" & sUserName & "' " & _
						  "AND UserPassword = '" & sUserPassword & "'"
		Log(Starter.strCriteria)
		rsUser = Starter.DBCon.ExecQuery(Starter.strCriteria)
		
		If rsUser.RowCount > 0 Then
			rsUser.Position = 0
			GlobalVar.UserID = rsUser.GetInt("UserID")
			GlobalVar.EmpName =rsUser.GetString("EmpName")
			GlobalVar.UserName = rsUser.GetString("UserName")
			GlobalVar.UserPW = rsUser.GetString("UserPassword")
			GlobalVar.Mod1 = rsUser.GetInt("Module1")
			GlobalVar.Mod2 = rsUser.GetInt("Module2")
			GlobalVar.Mod3 = rsUser.GetInt("Module3")
			GlobalVar.Mod4 = rsUser.GetInt("Module4")
			GlobalVar.Mod5 = rsUser.GetInt("Module5")
			GlobalVar.Mod6 = rsUser.GetInt("Module6")
		End If
		rsUser.Close
	Catch
		ToastMessageShow($"Unable to fetch User's Info due to "$ & LastException.Message, False)
		Log(LastException)
	End Try
End Sub

Public Sub HasDownloadedData(iReaderID As Int) As Int
	Dim iRetVal As Int

	Try
		Starter.strCriteria = "SELECT HasData FROM tblReaders WHERE ReaderID = " & iReaderID
		LogColor(Starter.strCriteria, Colors.Blue)
		
		iRetVal = Starter.DBCon.ExecQuerySingleResult(Starter.strCriteria)
	Catch
		ToastMessageShow($"Unable to fetch Branch System Mode due to "$ & LastException.Message, False)
		Log(LastException)
		iRetVal = 0
	End Try

	Return iRetVal
End Sub


Public Sub GetBranchInfo(iBranchID As Int)
	Dim rsBranch As Cursor
	Try
		Starter.strCriteria = "SELECT * FROM tblBranches " & _
							  "WHERE BranchID = " & iBranchID 
		Log(Starter.strCriteria)
		rsBranch = Starter.DBCon.ExecQuery(Starter.strCriteria)
		
		If rsBranch.RowCount > 0 Then
			rsBranch.Position = 0
			GlobalVar.CompanyID = rsBranch.GetInt("CompanyID")
			GlobalVar.BranchCode = rsBranch.GetString("BranchCode")
			GlobalVar.BranchName = rsBranch.GetString("BranchName")
			GlobalVar.BranchAddress = rsBranch.GetString("BranchAddress")
			GlobalVar.BranchContactNo = rsBranch.GetString("ContactNo")
			GlobalVar.BranchTINumber = rsBranch.GetString("TinNo")
			GlobalVar.BranchLogo = rsBranch.GetString("LogoName")
			GlobalVar.WithMeterCharges = rsBranch.GetInt("WithMeterCharges")
			GlobalVar.WithFranchisedTax = rsBranch.GetInt("WithFranchisedTax")
			GlobalVar.WithSeptageFee = rsBranch.GetInt("WithSeptageFee")
			GlobalVar.WithDisconDate = rsBranch.GetInt("WithDisconDate")
			GlobalVar.SeptageLogo = rsBranch.GetString("SeptageLogo")
			GlobalVar.SeptageProv = rsBranch.GetInt("SeptageProvider")


'			GlobalVar.WithSeptageFee = 1
'			GlobalVar.WithDisconDate = rsBranch.GetInt("WithDisconDate")
'			GlobalVar.SeptageLogo = "clp.png"
'			GlobalVar.SeptageProv = 2
		End If
	Catch
		ToastMessageShow($"Unable to fetch Branch Info due to "$ & LastException.Message, False)
		Log(LastException)
	End Try
	rsBranch.Close
End Sub

Public Sub GetCompanyInfo(iCompanyID As Int)
	Dim rsCompany As Cursor
	Try
		Starter.strCriteria = "SELECT * FROM tblCompanies " & _
							  "WHERE CompanyID = " & iCompanyID 
		Log(Starter.strCriteria)
		rsCompany = Starter.DBCon.ExecQuery(Starter.strCriteria)
		
		If rsCompany.RowCount > 0 Then
			rsCompany.Position = 0
			GlobalVar.CompanyCode = rsCompany.GetInt("CompanyCode")
			GlobalVar.CompanyName = rsCompany.GetString("CompanyName")
		End If
	Catch
		ToastMessageShow($"Unable to fetch Company Info due to "$ & LastException.Message, False)
		Log(LastException)
	End Try
	rsCompany.Close
End Sub

Public Sub SaveUserHistory(iUserID As Int)
	
End Sub

Public Sub HasBookAssigned (iBranchID As Int, iBillMonth As Int, iBillYear As Int) As Boolean
	Dim blnRetVal As Boolean
	Dim rsBookAssignment As Cursor
	blnRetVal = False
	Try
		Starter.strCriteria = "SELECT BranchID, BillYear, BillMonth FROM tblBookAssignments " & _
							  "WHERE BranchID = " & iBranchID & " " & _
							  "AND BillMonth = " & iBillMonth & " " & _
							  "AND BillYear = " & iBillYear & " " & _
							  "GROUP BY BranchID, BillYear, BillMonth"
		rsBookAssignment = Starter.DBCon.ExecQuery(Starter.strCriteria)
		If rsBookAssignment.RowCount > 0 Then
			blnRetVal = True
		Else
			blnRetVal = False
		End If
		rsBookAssignment.Close
	Catch
		blnRetVal = False
		ToastMessageShow("Unable to get assigned books due to " & LastException.Message, False)
		Log(LastException)
	End Try
	Return blnRetVal
End Sub
#End Region

#Region BookReading
Public Sub GetBookID (sBookCode As String)
'	Dim iRetVal As Int
'	Try
'		iRetVal = Starter.DBCon.ExecQuerySingleResult("SELECT tblBranches.CompanyID FROM tblBranches WHERE BranchID = " & iBranchID)
'	Catch
'		ToastMessageShow($"Unable to fetch Branch ID due to "$ & LastException.Message, False)
'		Log(LastException)
'	End Try
'	Return iRetVal	
End Sub
Sub GetSeqNo(iBookID As Int) As Int
	Dim dblNo As Double
	Dim iRetVal As Int
	
	Dim blnSaved As Boolean

	' Initialize blnSaved to False
	blnSaved = False
    
	Do While Not (blnSaved = True)
		blnSaved = SaveRecNo(iBookID)
		If blnSaved Then
			dblNo = Starter.DBCon.ExecQuerySingleResult("SELECT LastSeqNo FROM NewSequence WHERE BookID = " & iBookID)
			Exit
		Else
			' Display a message that the procedure failed but
			' will keep retrying...
		End If
	Loop
	
	iRetVal=dblNo
	Return iRetVal
	
End Sub

Sub SaveRecNo(iBookID As Int) As Boolean
	Dim blnRetVal As Boolean
	Dim lngRec As Long
	Try
		Starter.strCriteria = "SELECT * FROM NewSequence WHERE BookID = " & iBookID
		rsTemp = Starter.dbcon.ExecQuery(Starter.strCriteria)
		If rsTemp.RowCount=0 Then
			Starter.dbcon.ExecNonQuery2("INSERT INTO NewSequence VALUES (?,?)", Array As Object(iBookID, 1))
		Else
			rsTemp.Position=0
			lngRec = rsTemp.GetLong("LastSeqNo") + 1
			Starter.strCriteria="UPDATE NewSequence SET LastSeqNo = ? WHERE BookID = " & iBookID
			Starter.dbcon.ExecNonQuery2(Starter.strCriteria, Array As String(lngRec))
		End If
		blnRetVal=True
	Catch
		blnRetVal = False
		Log(LastException)
	End Try
	Return blnRetVal
End Sub
#End Region

Public Sub IsThereBookAssignments(iBranchID As Int, iBillYear As Int, iBillMonth As Int, iUserID As Int) As Boolean
	Dim rsAssignments As Cursor
	Dim blnRetVal As Boolean
	Try
		Starter.strCriteria = "SELECT * FROM tblBookAssignments " & _
							  "WHERE BranchID = " & iBranchID & " " & _
							  "AND BillYear = " & iBillYear & " " & _
							  "AND BillMonth = " & iBillMonth & " " & _
							  "AND UserID = " & iUserID
		rsAssignments = Starter.DBCon.ExecQuery(Starter.strCriteria)
		If rsAssignments.RowCount <= 0 Then
			blnRetVal = False
		Else
			blnRetVal = True
		End If
	Catch
		blnRetVal = False
		ToastMessageShow($"Unable to check assigned book due to "$ & LastException.Message, False)
		Log(LastException)
	End Try
	rsAssignments.Close
	Return blnRetVal
End Sub

Public Sub ZapMeterReader()
	Try
		Starter.strCriteria="DELETE FROM tblReaders"
		Starter.DBCon.ExecNonQuery(Starter.strCriteria)
	Catch
		Log(LastException)
	End Try
End Sub

#End Region

#Region PCAValue
Public Sub IsBookWithPCA(iBookID As Int) As Boolean
	Dim blnRetValue As Boolean
	Dim rsBooks As Cursor
	Try
		Starter.strCriteria = "SELECT * FROM tblBooks " & _
							  "WHERE BookID = " & iBookID
		rsBooks = Starter.DBCon.ExecQuery(Starter.strCriteria)
		If rsBooks.RowCount > 0 Then
			rsBooks.Position = 0
			If rsBooks.GetInt("WithPCA") = 1 Then
				blnRetValue = True
			Else
				blnRetValue = False
			End If
		Else
			blnRetValue = False
		End If
	Catch
		blnRetValue = False
		Log(LastException.Message)
	End Try
	Return blnRetValue
End Sub

Public Sub GetPCAmount(iBookID As Int) As Double
	Dim AmtRet As Double
	Try
		AmtRet = Starter.DBCon.ExecQuerySingleResult("SELECT Amount FROM tblPCA WHERE BranchID = " & GlobalVar.BranchID & " AND BookID = " & iBookID)
	Catch
		Log(LastException.Message)
		AmtRet = 0
	End Try
	Return AmtRet
End Sub

Public Sub IsPCAUpdate(iBookID As Int) As Boolean
	Dim bRetVal As Boolean
	Dim rsTemp As Cursor
	Try
		Starter.strCriteria = "SELECT * FROM tblBooks " & _
						  "WHERE tblBooks.BookID = " & iBookID
	rsTemp = Starter.DBCon.ExecQuery(Starter.strCriteria)
	Log(Starter.strCriteria)
	
	If rsTemp.RowCount > 0 Then
		rsTemp.Position = 0
		If rsTemp.GetInt("WasPCAUpdated") = 1 Then
			bRetVal = True
		Else
			bRetVal = False
		End If
	Else
		bRetVal = False
	End If
	Catch
		bRetVal = False
		Log(LastException.Message)
	End Try
	Return bRetVal
End Sub
Public Sub UpdatePCA(iBookID As Int, iAmt As Double)
	Starter.DBCon.BeginTransaction
	Try
		Starter.strCriteria="UPDATE tblReadings SET PCA = ? WHERE BookID = " & iBookID
		Starter.DBCon.ExecNonQuery2(Starter.strCriteria, Array As String(iAmt))
		Starter.strCriteria="UPDATE tblBooks SET WasPCAUpdated = ? WHERE BookID = " & iBookID
		Starter.DBCon.ExecNonQuery2(Starter.strCriteria, Array As String($"1"$))
		Starter.DBCon.TransactionSuccessful
	Catch
		Log(LastException.Message)
	End Try
	Starter.DBCon.EndTransaction
End Sub
#End Region
#Region Misc Function

Public Sub GetIDbyCode(RetFieldID As String, TableName As String, FieldCodeToCompare As String, sCode As String) As Double
	Dim rsTemp As Cursor
	Dim dRetValue As Double
	
	Try
		Starter.strCriteria = "SELECT " & RetFieldID & " FROM " & TableName & " " & _
							  "WHERE " & FieldCodeToCompare & " = '" & sCode & "'"
		rsTemp = Starter.DBCon.ExecQuery(Starter.strCriteria)
		Log(Starter.strCriteria)
		
		If rsTemp.RowCount > 0 Then
			rsTemp.Position = 0
			dRetValue = rsTemp.GetDouble(RetFieldID)
		Else
			dRetValue = 0
		End If
	Catch
		dRetValue = 0
		ToastMessageShow($"Unable to Get ID by Code due to "$ & LastException.Message, False)
		Log(LastException)
	End Try
	rsTemp.Close
	Return dRetValue
End Sub

Public Sub GetCodebyID(RetFieldCode As String, TableName As String, FieldIDToCompare As String, iID As Int) As String
	Dim rsTemp As Cursor
	Dim sRetValue As String
	
	Try
		Starter.strCriteria = "SELECT " & RetFieldCode & " FROM " & TableName & " " & _
							  "WHERE " & FieldIDToCompare & " = " & iID
		rsTemp = Starter.DBCon.ExecQuery(Starter.strCriteria)
		Log(Starter.strCriteria)
		
		If rsTemp.RowCount > 0 Then
			rsTemp.Position = 0
			sRetValue = rsTemp.GetString(RetFieldCode)
		Else
			sRetValue = ""
		End If
	Catch
		sRetValue = ""
		ToastMessageShow($"Unable to Get Code by ID due to "$ & LastException.Message, False)
		Log(LastException)
	End Try
	rsTemp.Close
	Return sRetValue
End Sub

#End Region

#Region Basic Computation

Public Sub GetRatesHeaderID(sClass As String, sSubClass As String) As Int
	Dim iRetVal As Int
	Try
		iRetVal = Starter.DBCon.ExecQuerySingleResult("SELECT id FROM rates_header WHERE class = '" & sClass & "' " & _
													  "AND sub_class = '" & sSubClass & "'")
	Catch
		ToastMessageShow($"Unable to fetch Rate Header ID due to "$ & LastException.Message, False)
		Log(LastException)
	End Try
	Return iRetVal
End Sub

Public Sub GetRatesResHeaderID(sSubClass As String) As Int
	Dim iRetVal As Int
	Try
		iRetVal = Starter.DBCon.ExecQuerySingleResult("SELECT id FROM rates_header WHERE class = '" & "RES" & "' " & _
													  "AND sub_class = '" & sSubClass & "'")
	Catch
		ToastMessageShow($"Unable to fetch Rate Header ID due to "$ & LastException.Message, False)
		Log(LastException)
	End Try
	Return iRetVal
End Sub

Public Sub GetMinimumRate(iHeaderID As Int) As Double
	Dim iRetVal As Int
	Try
		iRetVal = Starter.DBCon.ExecQuerySingleResult("SELECT amount FROM rates_details WHERE header_id = " & iHeaderID & " " & _
													  "AND typecust = '" & "m" & "' " & _
													  "ORDER BY rangefrom DESC LIMIT 1")
	Catch
		ToastMessageShow($"Unable to fetch Residential Rate due to "$ & LastException.Message, False)
		Log(LastException)
	End Try
	Return iRetVal
End Sub

Public Sub IsMinimumCons(iHeaderID As Int, iTotCum As Int) As Boolean
	Dim SRetVal As String
	Dim bRetVal As Boolean
	Try
		SRetVal = Starter.DBCon.ExecQuerySingleResult("SELECT typecust FROM rates_details WHERE header_id = " & iHeaderID & " " & _
							  						  "AND (" & iTotCum & " BETWEEN rangefrom and rangeto)")
'		Starter.strCriteria = "SELECT typecust FROM rates_details WHERE header_id = " & iHeaderID & " " & _
'							  "AND (" & iTotCum & " BETWEEN rangefrom and rangeto)"
'		rsTemp = Starter.DBCon.ExecQuery(Starter.strCriteria)
'		LogColor(Starter.strCriteria, Colors.Blue)
		
		If SRetVal = "m" Then
			bRetVal = True
		Else
			bRetVal = False
		End If
		
'		iRetVal = Starter.DBCon.ExecQuerySingleResult("SELECT id FROM rates_header WHERE class= '" & sClass & "' " & _
'													  "AND sub_class = '" & sSubClass & "'")
	Catch
		ToastMessageShow($"Unable to fetch Consumption Type due to "$ & LastException.Message, False)
		Log(LastException)
		bRetVal = False
	End Try
	Return bRetVal
End Sub


Public Sub ComputeBasicAmt(iPresCum As Int, iBranchID As Int, sClass As String, sSubClass As String) As Double
	Dim RetVal As Double
	Dim sDate, sBillDate As String
	Dim i As Int
	Dim BillType As String
	Dim BillAmt As Double
	Dim RangeFrom, RangeTo As Int
	Dim ExcessCum As Int
	Dim BasicAmt As Double
	Dim PresCum As Int
	Dim rsRate As Cursor
	 
'	sDate = DateUtils.SetDate(Starter.SF.Left(dFrom,4), Starter.SF.Mid(dFrom,6,2), Starter.SF.Right(dFrom, 2))
'	DateTime.DateFormat="yyyy-MM-dd"
'	sBillDate = DateTime.Date(sDate)
	BillType = ""
	BillAmt = 0
	PresCum = 0
	ExcessCum = 0
	BasicAmt = 0

	Try
		'Get Header Rates
		Starter.strCriteria = "SELECT id FROM rates_header WHERE branch_id = " & iBranchID & " " & _
							  "AND class = '" & sClass & "' " & _
							  "AND sub_class = '" & sSubClass & "' "  & _
							  "ORDER BY date_from DESC LIMIT 1"
		Log(Starter.strCriteria)
		RetVal = Starter.DBCon.ExecQuerySingleResult(Starter.strCriteria)
		Log(RetVal)
		
		'Get Details
		Starter.strCriteria = "SELECT * FROM rates_details WHERE header_id = " & RetVal & " " & _
							  "ORDER BY rangefrom DESC"
		
		rsRate = Starter.DBCon.ExecQuery(Starter.strCriteria)
		PresCum = iPresCum

		If rsRate.RowCount > 0 Then
			For i = 0 To rsRate.RowCount - 1
				rsRate.Position	= i
				RangeFrom = rsRate.GetString("rangefrom")
				RangeTo = rsRate.GetString("rangeto")
				BillType = rsRate.GetString("typecust")
				BillAmt = rsRate.GetDouble("amount")
				
				If PresCum >= RangeFrom And PresCum <= RangeTo Then
					If BillType = "m" Then
						BasicAmt = BasicAmt + BillAmt
						Exit
					End If
					
					If BillType = "r" Or BillType = "s" Then
						ExcessCum = (PresCum - RangeFrom) + 1
						BasicAmt = BasicAmt + (ExcessCum * BillAmt)
						PresCum = PresCum - ExcessCum
					End If
					
					If PresCum = 0 Then
						Exit
					End If
				Else
					
				End If
			Next
		End If
	Catch
		xui.MsgboxAsync("Unable to fetch basic amount due to " & LastException.Message,Application.LabelName)
		Log(LastException.Message)
'		rsRate.Close
		Return 0
	End Try
'	rsRate.Close
	Return BasicAmt
End Sub

Public Sub HasRateHeader(iBranchID As Int, sClass As String, sSubClass As String) As Boolean
	Dim blnRetVal As Boolean
	Dim rsHeader As Cursor
	Try
		'Get Header Rates
		Starter.strCriteria = "SELECT * FROM rates_header WHERE branch_id = " & iBranchID & " " & _
							  "AND class = '" & sClass & "' " & _
							  "AND sub_class = '" & sSubClass & "' " & _
							  "ORDER BY date_from DESC LIMIT 1"
		Log(Starter.strCriteria)
		
		
		rsHeader = Starter.DBCon.ExecQuery(Starter.strCriteria)
		If rsHeader.RowCount > 0 Then
			blnRetVal = True
		Else
			blnRetVal = False
		End If
	Catch
		Log(LastException.Message)
		Return False
	End Try
	Return blnRetVal
End Sub

Public Sub HasRateDetails(dHeaderID As Double) As Boolean
	Dim blnRetVal As Boolean
	Dim rsRatesDetails As Cursor
	Try
		'Get Header Rates
		Starter.strCriteria = "SELECT * FROM rates_details WHERE header_id = " & dHeaderID
		Log(Starter.strCriteria)
		
		rsRatesDetails = Starter.DBCon.ExecQuery(Starter.strCriteria)
		If rsRatesDetails.RowCount > 0 Then
			blnRetVal = True
		Else
			blnRetVal = False
		End If
	Catch
		Log(LastException.Message)
		Return False
	End Try
	Return blnRetVal
End Sub

Public Sub GetRatesJe(iBranchID As Int, sClass As String, sSubClass As String, dFrom As String) As Boolean
	Dim blnRetVal As Boolean
	Dim rsHeader As Cursor
	Try
		'Get Header Rates
		Starter.strCriteria = "SELECT * FROM rates_header WHERE branch_id = " & iBranchID & " " & _
							  "AND class = '" & sClass & "' " & _
							  "AND sub_class = '" & sSubClass & "' " & _
							  "AND date_from <= '" & dFrom & "' " & _
							  "ORDER BY date_from DESC LIMIT 1"
		Log(Starter.strCriteria)
		
		rsHeader = Starter.DBCon.ExecQuery(Starter.strCriteria)
		If rsHeader.RowCount > 0 Then
			blnRetVal = True
		Else
			blnRetVal = False
		End If
	Catch
		Log(LastException.Message)
		Return False
	End Try
	Return blnRetVal
End Sub

Public Sub IsWithinRange(iPresCum As Int, iBranchID As Int, sClass As String, sSubClass As String) As Boolean
	Dim blnRetVal As Boolean
	Dim sDate, sBillDate As String
	Dim i As Int
	Dim BillType As String
	Dim BillAmt As Double
	Dim RangeFrom, RangeTo As Int
	Dim ExcessCum As Int
	Dim BasicAmt As Double
	Dim PresCum As Int
	Dim rsRate As Cursor
	Dim RetVal As Double
	 
'	sDate = DateUtils.SetDate(Starter.SF.Left(dFrom,4), Starter.SF.Mid(dFrom,6,2), Starter.SF.Right(dFrom, 2))
'	DateTime.DateFormat="yyyy-MM-dd"
'	sBillDate = DateTime.Date(sDate)
	BillType = ""
	BillAmt = 0
	PresCum = 0
	ExcessCum = 0
	BasicAmt = 0

	Try
		'Get Header Rates
		Starter.strCriteria = "SELECT id FROM rates_header WHERE branch_id = " & iBranchID & " " & _
							  "AND class = '" & sClass & "' " & _
							  "AND sub_class = '" & sSubClass & "' " & _
							  "ORDER BY date_from DESC LIMIT 1"
		Log(Starter.strCriteria)
		RetVal = Starter.DBCon.ExecQuerySingleResult(Starter.strCriteria)
		Log(RetVal)
		
		'Get Details
		Starter.strCriteria = "SELECT * from rates_details WHERE header_id = " & RetVal & " " & _
						  "AND " & iPresCum & " BETWEEN rangefrom And rangeto"
		
		rsRate = Starter.DBCon.ExecQuery(Starter.strCriteria)
		If rsRate.RowCount > 0 Then
			blnRetVal = True
		Else
			blnRetVal = False
		End If
	Catch
		Log(LastException.Message)
		Return False
	End Try
	Return blnRetVal
End Sub

Public Sub IsNoDueDate(iBookID As Int) As Boolean
	Dim blnRetVal As Boolean
	Dim rsPCA As Cursor
	Try
		Starter.strCriteria = "SELECT * FROM tblBooks WHERE BookID = " & iBookID
		rsPCA = Starter.DBCon.ExecQuery(Starter.strCriteria)
		If rsPCA.RowCount > 0 Then
			rsPCA.Position=0
			If rsPCA.GetInt("NoDueDate") = 1 Then
				blnRetVal = True
			Else
				blnRetVal = False
			End If
		End If
	Catch
		blnRetVal = True
		rsPCA.Close
		Log(LastException)
	End Try
	rsPCA.Close
	Return blnRetVal
End Sub

Public Sub IsWithDueDate(iReadID As Int) As Boolean
	Dim blnRetVal As Boolean
	Dim rsWithDueDate As Cursor
	Try
		Starter.strCriteria = "SELECT * FROM tblReadings WHERE ReadID = " & iReadID
		rsWithDueDate = Starter.DBCon.ExecQuery(Starter.strCriteria)
		If rsWithDueDate.RowCount > 0 Then
			rsWithDueDate.Position=0
			If rsWithDueDate.GetInt("WithDueDate") = 1 Then
				blnRetVal = True
			Else
				blnRetVal = False
			End If
		End If
	Catch
		blnRetVal = True
		rsWithDueDate.Close
		Log(LastException)
	End Try
	rsWithDueDate.Close
	Return blnRetVal
End Sub

Public Sub GetMinimumRangeTo(iBranchID As Int, sClass As String, sSubClass As String, dFrom As String) As Double
	Dim RateHeader As Double
	Dim RetVal As Double
	Try
		'Get Header Rates
		Starter.strCriteria = "SELECT id FROM rates_header WHERE branch_id = " & iBranchID & " " & _
							  "AND class = '" & sClass & "' " & _
							  "AND sub_class = '" & sSubClass & "' " & _
							  "AND date_from <= '" & dFrom & "' " & _
							  "ORDER BY date_from DESC LIMIT 1"
		Log(Starter.strCriteria)

		RateHeader = Starter.DBCon.ExecQuerySingleResult(Starter.strCriteria)
		Log(RateHeader)
		
		'Get Mininmum
		Starter.strCriteria = "SELECT rangeto FROM rates_details " & _
							  "WHERE header_id = " & RateHeader & " " & _
							  "AND typecust = '" & "m" & "' " & _
							  "ORDER BY RangeTo DESC LIMIT 1"

		RetVal = Starter.DBCon.ExecQuerySingleResult(Starter.strCriteria)
		
	Catch
		xui.MsgboxAsync("Unable to fetch basic amount due to " & LastException.Message,Application.LabelName)
		Log(LastException.Message)
		Return 0
	End Try
	Return RetVal
End Sub
#End Region

#Region Septage Fees
Public Sub GetSeptageRateID(sAcctClass As String, iBranchID As Int) As Int
	Dim iRetValue As String
	iRetValue = 0
	
	Try
		iRetValue = Starter.DBCon.ExecQuerySingleResult("SELECT SeptageRatesID FROM SSMRates WHERE AcctClassification = '" & sAcctClass & "' " & _
						  					   "AND BranchID = " & iBranchID)
	Catch
		iRetValue = 0
		ToastMessageShow($"Unable to fetch Rate Type due to "$ & LastException.Message, False)
		Log(LastException)
	End Try
	Return iRetValue
End Sub

Public Sub GetSeptageRateDetails(iBranchID As Int, iRateID As Int, sAcctClass As String)
	Dim rsSeptage As Cursor
	Try
		Starter.strCriteria = "SELECT * FROM SSMRates WHERE AcctClassification = '" & sAcctClass & "' " & _
						  "AND BranchID = " & iBranchID & " " & _
						  "AND SeptageRatesID = " & iRateID
						  
		LogColor(Starter.strCriteria, Colors.Yellow)
		
		rsSeptage = Starter.DBCon.ExecQuery(Starter.strCriteria)
		
		If rsSeptage.RowCount > 0 Then
			rsSeptage.Position = 0
			GlobalVar.SeptageRateType = rsSeptage.GetString("RateType")
			GlobalVar.SeptageMinCuM = rsSeptage.GetInt("MinCum")
			GlobalVar.SeptageMaxCuM = rsSeptage.GetInt("MaxCum")
			GlobalVar.SeptageRatePerCuM = rsSeptage.GetDouble("RatePerCum")
		Else
			ToastMessageShow($"Unable to fetch Rate Type due to "$ & LastException.Message, False)
		End If
	Catch
		ToastMessageShow($"Unable to fetch Rate Type due to "$ & LastException.Message, False)
		Log(LastException)
	End Try
End Sub
#End Region