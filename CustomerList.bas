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

Sub Activity_CreateMenu(Menu As ACMenu)
	Dim iItem As ACMenuItem

	Menu.Clear
	Menu.Add2(1, 1, $"Search"$, xmlIcon.GetDrawable("ic_search_white_24dp")).ShowAsAction = iItem.SHOW_AS_ACTION_IF_ROOM
End Sub

Sub Process_Globals
	Private xui As XUI
End Sub

Sub Globals
	Dim ActionBarButton As ACActionBar
	Private ToolBar As ACToolBarDark
	Private xmlIcon As XmlLayoutBuilder
	
	'Material Dialog
	Private MatDialogBuilder As MaterialDialogBuilder
	
	'Expandable ListView
	'Database
	Private rsBooks As Cursor
	Private rsAccts As Cursor
	Private rsCustomers As Cursor
	
	Private CLV1 As classCustomListView
	Private pnlCustomers As Panel
	Private btnDetails As ACButton
	Private lblAccountName As Label
	Private lblAccountNo As Label
	Private lblAddress As Label
	Private lblBookNo As Label
	Private lblMeterNo As Label
	Private lblSeqNo As Label
	Private lblStatus As Label
	
	Private CD As ColorDrawable
	Private pnlContent As Panel
	Private dblReadID As Double
	Private SearchPanel As Panel
	Private SV As SearchView
	Private blnSearching As Boolean
	
	
	Private optAcctName As RadioButton
	Private optBookNo As RadioButton
	Private optMeterNo As RadioButton
	Private optSeqNo As RadioButton
	Private pnlSearchBy As Panel
End Sub

Sub Activity_Create(FirstTime As Boolean)
	MyScale.SetRate(0.5)
	Activity.LoadLayout("BookListing")

	GlobalVar.CSTitle.Initialize.Size(17).Bold.Append($"Bill Printing"$).PopAll
	GlobalVar.CSSubTitle.Initialize.Size(14).Append($"List of Account's Bill per Book"$).PopAll
	
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

	pnlCustomers.Initialize("")
	SearchPanel.Visible = False
	blnSearching = False
	DisplayCustomers(GlobalVar.BranchID, GlobalVar.BillYear, GlobalVar.BillMonth, GlobalVar.UserID)

End Sub

Sub Activity_KeyPress (KeyCode As Int) As Boolean 'Return True to consume the event
	If KeyCode = 4 Then
		Return False
	Else
		Return True
	End If
End Sub

Sub Activity_Resume
End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Sub ToolBar_NavigationItemClick
	If blnSearching = True Then
		blnSearching = False
		SV.ClearSearchBox
		SV.ClearAll
		SearchPanel.Visible = False
	Else
		blnSearching = False
		Activity.Finish
	End If
End Sub

Sub ToolBar_MenuItemClick (Item As ACMenuItem)
	If SearchPanel.Visible = True Then
		Return
	Else
		blnSearching = True
		SearchPanel.Visible = True
		SV.Initialize(Me,"SV")
		SV.AddToParent(SearchPanel,1dip,50dip,SearchPanel.Width,SearchPanel.Height)
		SV.ClearAll
		optBookNo.Checked = True
		optBookNo_CheckedChange(True)
	End If
End Sub

Private Sub DisplayCustomers(iBranchID As Int, iBillYear As Int, iBillMonth As Int, iUserID As Int)
	Try
		Starter.strCriteria = "SELECT ReadID, AcctNo, AcctName, AcctAddress, " & _
							  "BookNo, SeqNo, MeterNo, WasRead " & _
							  "FROM tblReadings " & _
							  "WHERE BranchID = " & iBranchID & " " & _
							  "AND BillYear = " & iBillYear & " " & _
							  "AND BillMonth = " & iBillMonth & " " & _
							  "AND ReadBy = " & iUserID & " " & _
							  "AND WasRead = 1 " & _
							  "ORDER BY BookNo Asc, SeqNo Asc"

		rsAccts = Starter.DBCon.ExecQuery(Starter.strCriteria)
		CLV1.Clear
		If rsAccts.RowCount > 0 Then
			pnlCustomers.Visible = True
			For i = 0 To rsAccts.RowCount - 1
				rsAccts.Position = i
				CLV1.Add(CreateCustList(CLV1.AsView.Width, rsAccts.GetString("AcctNo"), rsAccts.GetString("AcctName"), rsAccts.GetString("AcctAddress"), rsAccts.GetString("BookNo"), rsAccts.GetString("SeqNo"), rsAccts.GetString("MeterNo"), rsAccts.GetInt("WasRead")),rsAccts.GetInt("ReadID"))
			Next
		Else
			Log(rsAccts.RowCount)
		End If
	Catch
		rsAccts.Close
		Log(LastException)
	End Try
	rsAccts.Close
	ProgressDialogHide
End Sub

Sub CreateCustList(iWidth As Int, sAcctNo As String, sAcctName As String, sAddress As String, sBookNo As String, sSeqNo As String, sMeterNo As String, iWasRead As Int) As Panel
	Dim p As B4XView = xui.CreatePanel("")
	Dim iHeight As Int = 165dip
	
	Dim csWasRead As CSBuilder

	If GetDeviceLayoutValues.ApproximateScreenSize < 4.5 Then iHeight = 310dip
	p.SetLayoutAnimated(0, 0, 0, iWidth, iHeight)
	p.LoadLayout("CustList")
	
	If iWasRead = 0 Then
		csWasRead.Initialize.Size(20).Bold.Color(Colors.Red).Append($"UNREAD"$).PopAll
		btnDetails.Enabled = False
	Else
		csWasRead.Initialize.Size(20).Color(GlobalVar.PriColor).Append($"READ"$).PopAll
		btnDetails.Enabled = True
	End If

	lblAccountNo.Text = sAcctNo
	lblAccountName.Text = sAcctName
	lblAddress.Text = GlobalVar.SF.Upper(sAddress)
	lblBookNo.Text = sBookNo
	lblSeqNo.Text = sSeqNo
	lblMeterNo.Text = sMeterNo
	lblStatus.Text = csWasRead
	
	CD.Initialize2(0xFF1976D2, 25, 0,0xFF000000)
	btnDetails.Background = CD
	Return p
End Sub

Sub clvAssignment_ItemClick (Index As Int, Value As Object)
End Sub
#End Region


Sub btnDetails_Click
	Dim index As Int = CLV1.GetItemFromView(Sender)
	GlobalVar.BillID = CLV1.GetValue(index)
	Log(GlobalVar.BillID)
	StartActivity(CustomerBill)
End Sub

Private Sub SearchCustomers(sSearchBy As String)
	Try
		Starter.strCriteria = "SELECT ReadID, AcctNo, AcctName, AcctAddress, " & _
							  "BookNo, SeqNo, MeterNo, WasRead " & _
							  "FROM tblReadings " & _
							  "WHERE BranchID = " & GlobalVar.BranchID & " " & _
							  "AND BillYear = " & GlobalVar.BillYear & " " & _
							  "AND BillMonth = " & GlobalVar.BillMonth & " " & _
							  "AND ReadBy = " & GlobalVar.UserID & " " & _
							  "ORDER BY " & sSearchBy

		rsCustomers = Starter.DBCon.ExecQuery(Starter.strCriteria)
	Catch
		Log(LastException)
	End Try
End Sub

Sub optBookNo_CheckedChange(Checked As Boolean)
	If Checked = True Then
		SearchAccount(0)
	End If
End Sub

Sub optSeqNo_CheckedChange(Checked As Boolean)
	If Checked = True Then
		SearchAccount(1)
	End If
End Sub

Sub optMeterNo_CheckedChange(Checked As Boolean)
	If Checked = True Then
		SearchAccount(2)
	End If
End Sub

Sub optAcctName_CheckedChange(Checked As Boolean)
	If Checked = True Then
		SearchAccount(3)
	End If
End Sub

Private Sub SearchAccount(iSearchBy As Int)
	Dim sField1, sField2, sField3 As String
	Dim SearchList As List

	SearchList.Initialize
	SearchList.Clear

	Select iSearchBy
		Case 0 'Book No
			sField1 = "BookNo"
			sField2 = "AcctName"
			SV.et.InputType = SV.et.INPUT_TYPE_NUMBERS
		Case 1 'Seq. No
			sField1 = "SeqNo"
			sField2 = "AcctName"
			SV.et.InputType = SV.et.INPUT_TYPE_NUMBERS
		Case 2 'Meter No
			sField1 = "MeterNo"
			sField2 = "AcctName"
			SV.et.InputType = SV.et.INPUT_TYPE_NUMBERS
		Case 3 'Acct Name
			sField1 = "AcctName"
			sField2 = "BookNo"
			SV.et.InputType = SV.et.INPUT_TYPE_TEXT
	End Select

	If SV.IsInitialized=False Then
		SV.Initialize(Me,"SV")
	End If
	
	SV.ClearAll
	SV.ClearSearchBox
	SV.lv.Clear

	SearchCustomers(sField1)
	If rsCustomers.RowCount > 0 Then
		For i = 0 To rsCustomers.RowCount - 1
			Log(rsCustomers.RowCount)
			rsCustomers.Position = i
			Dim IT As Item
			IT.Title = rsCustomers.GetString(sField1) & " - " & rsCustomers.GetString(sField2)
			IT.Text = rsCustomers.GetString("AcctAddress")
			IT.SearchText = rsCustomers.GetString(sField1).ToLowerCase
			IT.Value = rsCustomers.GetInt("ReadID")
			SearchList.Add(IT)
		Next
	End If
	SV.SetItems(SearchList)
	SearchList.Clear
End Sub