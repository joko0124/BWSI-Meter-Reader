B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=10
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
End Sub

Sub Globals
	Private BookCode, BookDesc As String
	Private ToolBar As ACToolBarDark
	Private ActionBarButton As ACActionBar
	Private xmlIcon As XmlLayoutBuilder
	Private PopWarnings As ACPopupMenu
	Private PopSubMenu As ACPopupMenu
End Sub
#End Region

#Region Activity Events
Sub Activity_Create(FirstTime As Boolean)
	Scale.SetRate(0.5)
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
	


	If RetrieveRecords(GlobalVar.BookID) = True Then
	End If

	If FirstTime Then
	End If
	
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub
#End Region
#Region Data

Private Sub RetrieveRecords(iBookID As Int) As Boolean
	Dim bRetVal As Boolean
	
	Try
		bRetVal = False
	Catch
		bRetVal = False
		Log(LastException)
	End Try
	Return bRetVal
End Sub
#End Region