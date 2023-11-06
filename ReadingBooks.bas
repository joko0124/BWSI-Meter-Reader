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
	Menu.Add2(2, 1, $"Sort Book by Code"$, Null).Checkable = True
	Menu.Add2(3, 2, $"Sort Book by Description"$, Null).Checkable = True
	Menu.FindItem(2).Checked = True
End Sub

Sub Process_Globals
	Private xui As XUI
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Dim ActionBarButton As ACActionBar
	Private ToolBar As ACToolBarDark
	Private xmlIcon As XmlLayoutBuilder
	
	'Material Dialog
	Private MatDialogBuilder As MaterialDialogBuilder
	
	Private btnLoad As ACButton
	Private lblBookDesc As Label
	Private lblBookNo As Label
	Private lblHigh As Label
	Private lblLow As Label
	Private lblMissCode As Label
	Private lblNoAccts As Label
	Private lblUnread As Label
	Private lblZero As Label
	Private pnlAssignedBooks As Panel
	
	Private rsAssignedBooks As Cursor
	
	Private clvAssignment As classCustomListView
	Private CD As ColorDrawable
	Private lblRead As Label
	Private lblAve As Label
End Sub

Sub Activity_Create(FirstTime As Boolean)
	MyScale.SetRate(0.5)
	Activity.LoadLayout("BookSelection")

	GlobalVar.CSTitle.Initialize.Size(13).Append($"Book Selection"$).Bold.PopAll
	GlobalVar.CSSubTitle.Initialize.Size(11).Append($"Please select the book you want to read..."$).PopAll
	
	ToolBar.InitMenuListener
	ToolBar.Title = GlobalVar.CSTitle
	ToolBar.SubTitle = GlobalVar.CSSubTitle

	ActionBarButton.Initialize
	ActionBarButton.ShowUpIndicator = True

	Dim jo As JavaObject
	Dim xl As XmlLayoutBuilder
	jo = ToolBar
	jo.RunMethod("setPopupTheme", Array(xl.GetResourceId("style", "ToolbarMenu")))
	jo.RunMethod("setContentInsetStartWithNavigation", Array(1dip))
	jo.RunMethod("setTitleMarginStart", Array(0dip))
	
	If DBaseFunctions.IsThereBookAssignments(GlobalVar.BranchID, GlobalVar.BillYear, GlobalVar.BillMonth, GlobalVar.UserID) = True Then
		DisplayAssignments(GlobalVar.BranchID, GlobalVar.BillYear, GlobalVar.BillMonth, GlobalVar.UserID)
	End If

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
	Activity.Finish
End Sub

Sub ToolBar_MenuItemClick (Item As ACMenuItem)
	
End Sub

Sub clvAssignment_ItemClick (Index As Int, Value As Object)
	
End Sub

#Region Assignments
Private Sub DisplayAssignments(iBranchID As Int, iBillYear As Int, iBillMonth As Int, iUserID As Int)
	Try
		Starter.strCriteria = "SELECT tblReadings.BookID AS BookID, tblReadings.BookNo AS BookNo, tblBooks.BookDesc AS BookDesc, " & _
							  "Count(tblReadings.AcctID) As NoAccts, " & _
							  "SUM(CASE WHEN tblReadings.WasRead = 1 Then 1 Else 0 End) As ReadAccts, " & _
							  "SUM(CASE WHEN tblReadings.WasRead = 0 Then 1 Else 0 End) As UnreadAccts, " & _
							  "SUM(CASE WHEN tblReadings.WasMissCoded = 1 THEN 1 ELSE 0 END) as Misscoded, " & _
							  "SUM(CASE WHEN tblReadings.ImplosiveType = 'zero' Then 1 ELSE 0 END) As ZeroCons, " & _
							  "SUM(CASE WHEN tblReadings.ImplosiveType = 'implosive-inc' Then 1 ELSE 0 END) As HighBill, " & _
							  "SUM(CASE WHEN tblReadings.ImplosiveType = 'implosive-dec' Then 1 ELSE 0 END) As LowBill, " & _
							  "SUM(CASE WHEN tblReadings.BillType = 'AB' Then 1 ELSE 0 END) As AverageBill " & _
							  "FROM tblReadings INNER JOIN tblBooks ON tblReadings.BookID = tblBooks.BookID " & _
							  "WHERE tblReadings.BranchID = " & iBranchID & " " & _
							  "AND tblReadings.BillYear = " & iBillYear & " " & _
							  "AND tblReadings.BillMonth = " & iBillMonth & " " & _
							  "AND tblReadings.ReadBy = " & iUserID & " " & _
							  "GROUP BY tblReadings.BookID " & _
							  "ORDER BY tblReadings.BookNo Asc"

		rsAssignedBooks = Starter.DBCon.ExecQuery(Starter.strCriteria)
		clvAssignment.Clear
		If rsAssignedBooks.RowCount > 0 Then
'			pnlAssignment.Visible = True
			For i = 0 To rsAssignedBooks.RowCount - 1
				rsAssignedBooks.Position = i
				clvAssignment.Add(CreateAssignmentCard(clvAssignment.AsView.Width, rsAssignedBooks.GetString("BookNo"), rsAssignedBooks.GetString("BookDesc"), rsAssignedBooks.GetInt("NoAccts"), rsAssignedBooks.GetInt("ReadAccts"), rsAssignedBooks.GetInt("UnreadAccts"), rsAssignedBooks.GetInt("Misscoded"), rsAssignedBooks.GetInt("ZeroCons"), rsAssignedBooks.GetInt("HighBill"), rsAssignedBooks.GetInt("LowBill"), rsAssignedBooks.GetInt("AverageBill")),rsAssignedBooks.GetInt("BookID"))
			Next
		Else
			Log(rsAssignedBooks.RowCount)
		End If
	Catch
		Log(LastException)
	End Try
End Sub

Sub CreateAssignmentCard(iWidth As Int, sBookNo As String, sBookDesc As String, iNoAccts As Int, iRead As Int, iUnread As Int, iMissCode As Int, iZero As Int, iHighBill As Int, iLowBill As Int, iAveBill As Int) As Panel
	Dim p As B4XView = xui.CreatePanel("")
	Dim iHeight As Int = 190dip
	Dim csRead, csUnread, csMissCode, csZero, csHigh, csLow, csAve As CSBuilder

	If GetDeviceLayoutValues.ApproximateScreenSize < 4.5 Then iHeight = 310dip
	p.SetLayoutAnimated(0, 0, 0, iWidth, iHeight)
	p.LoadLayout("BookAssignment")
	
	If iRead = 0 Then
		csRead.Initialize.Bold.Color(Colors.Red).Append(iRead).PopAll
	Else
		csRead.Initialize.Color(0xFF1976D2).Append(iRead).PopAll
	End If

	If iUnread > 0 Then
		csUnread.Initialize.Bold.Color(Colors.Red).Append(iUnread).PopAll
	Else
		csUnread.Initialize.Color(0xFF1976D2).Append(iUnread).PopAll
	End If
	
	If iMissCode > 0 Then
		csMissCode.Initialize.Bold.Color(Colors.Red).Append(iMissCode).PopAll
	Else
		csMissCode.Initialize.Color(0xFF1976D2).Append(iMissCode).PopAll
	End If
	
	If iZero > 0 Then
		csZero.Initialize.Bold.Color(Colors.Red).Append(iZero).PopAll
	Else
		csZero.Initialize.Color(0xFF1976D2).Append(iZero).PopAll
	End If
	
	If iHighBill > 0 Then
		csHigh.Initialize.Bold.Color(Colors.Red).Append(iHighBill).PopAll
	Else
		csHigh.Initialize.Color(0xFF1976D2).Append(iHighBill).PopAll
	End If
	
	If iLowBill > 0 Then
		csLow.Initialize.Bold.Color(Colors.Red).Append(iLowBill).PopAll
	Else
		csLow.Initialize.Color(0xFF1976D2).Append(iLowBill).PopAll
	End If
	
	If iAveBill > 0 Then
		csAve.Initialize.Bold.Color(Colors.Red).Append(iAveBill).PopAll
	Else
		csAve.Initialize.Color(0xFF1976D2).Append(iAveBill).PopAll
	End If

	lblBookNo.Text = "BOOK " & sBookNo
	lblBookDesc.Text =GlobalVar.SF.Upper(sBookDesc)
	lblNoAccts.Text = iNoAccts  & " Account(s)"
	lblRead.Text = csRead
	lblUnread.Text = csUnread
	lblMissCode.Text = csMissCode
	lblZero.Text = csZero
	lblHigh.Text = csHigh
	lblLow.Text = csLow
	lblAve.Text = csAve
	CD.Initialize2(0xFF1976D2, 25, 0,0xFF000000)
	btnLoad.Background = CD
	Return p
End Sub
#End Region


Sub btnLoad_Click
	Dim intPCAAmount As Double
	Dim index As Int = clvAssignment.GetItemFromView(Sender)
	GlobalVar.BookID = clvAssignment.GetValue(index)
	Log(GlobalVar.BookID)
	
	If DBaseFunctions.IsBookWithPCA(GlobalVar.BookID) = True Then
		intPCAAmount = DBaseFunctions.GetPCAmount(GlobalVar.BookID)
	Else
		intPCAAmount = 0
	End If
	
	DBaseFunctions.UpdatePCA(GlobalVar.BookID, intPCAAmount)
	StartActivity(MeterReading)
End Sub