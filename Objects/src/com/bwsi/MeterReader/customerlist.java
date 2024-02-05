package com.bwsi.MeterReader;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class customerlist extends androidx.appcompat.app.AppCompatActivity implements B4AActivity{
	public static customerlist mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = true;
	public static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;
    public static boolean dontPause;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mostCurrent = this;
		if (processBA == null) {
			processBA = new BA(this.getApplicationContext(), null, null, "com.bwsi.MeterReader", "com.bwsi.MeterReader.customerlist");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (customerlist).");
				p.finish();
			}
		}
        processBA.setActivityPaused(true);
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(this, processBA, wl, false))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "com.bwsi.MeterReader", "com.bwsi.MeterReader.customerlist");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "com.bwsi.MeterReader.customerlist", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (customerlist) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (customerlist) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEventFromUI(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return customerlist.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeydown", this, new Object[] {keyCode, event}))
            return true;
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeyup", this, new Object[] {keyCode, event}))
            return true;
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null)
            return;
        if (this != mostCurrent)
			return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        if (!dontPause)
            BA.LogInfo("** Activity (customerlist) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (customerlist) Pause event (activity is not paused). **");
        if (mostCurrent != null)
            processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        if (!dontPause) {
            processBA.setActivityPaused(true);
            mostCurrent = null;
        }

        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
            customerlist mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (customerlist) Resume **");
            if (mc != mostCurrent)
                return;
		    processBA.raiseEvent(mc._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        for (int i = 0;i < permissions.length;i++) {
            Object[] o = new Object[] {permissions[i], grantResults[i] == 0};
            processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
        }
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public de.amberhome.objects.appcompat.ACActionBar _actionbarbutton = null;
public de.amberhome.objects.appcompat.ACToolbarDarkWrapper _toolbar = null;
public anywheresoftware.b4a.object.XmlLayoutBuilder _xmlicon = null;
public de.amberhome.materialdialogs.MaterialDialogBuilderWrapper _matdialogbuilder = null;
public anywheresoftware.b4a.sql.SQL.CursorWrapper _rsbooks = null;
public anywheresoftware.b4a.sql.SQL.CursorWrapper _rsaccts = null;
public anywheresoftware.b4a.sql.SQL.CursorWrapper _rscustomers = null;
public com.bwsi.MeterReader.classcustomlistview _clv1 = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlcustomers = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btndetails = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblaccountname = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblaccountno = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbladdress = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblbookno = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblmeterno = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblseqno = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblstatus = null;
public anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlcontent = null;
public static double _dblreadid = 0;
public anywheresoftware.b4a.objects.PanelWrapper _searchpanel = null;
public com.bwsi.MeterReader.searchview _sv = null;
public static boolean _blnsearching = false;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _optacctname = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _optbookno = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _optmeterno = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _optseqno = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlsearchby = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public b4a.example.dateutils _dateutils = null;
public com.bwsi.MeterReader.main _main = null;
public com.bwsi.MeterReader.datasyncing _datasyncing = null;
public com.bwsi.MeterReader.login _login = null;
public com.bwsi.MeterReader.dbasefunctions _dbasefunctions = null;
public com.bwsi.MeterReader.customfunctions _customfunctions = null;
public com.bwsi.MeterReader.mainscreen _mainscreen = null;
public com.bwsi.MeterReader.myscale _myscale = null;
public com.bwsi.MeterReader.meterreading _meterreading = null;
public com.bwsi.MeterReader.camera _camera = null;
public com.bwsi.MeterReader.cmrvr _cmrvr = null;
public com.bwsi.MeterReader.customerbill _customerbill = null;
public com.bwsi.MeterReader.dbutils _dbutils = null;
public com.bwsi.MeterReader.globalvar _globalvar = null;
public com.bwsi.MeterReader.modvariables _modvariables = null;
public com.bwsi.MeterReader.newcam _newcam = null;
public com.bwsi.MeterReader.readingbooks _readingbooks = null;
public com.bwsi.MeterReader.readingsettings _readingsettings = null;
public com.bwsi.MeterReader.readingvalidation _readingvalidation = null;
public com.bwsi.MeterReader.starter _starter = null;
public com.bwsi.MeterReader.useraccountsettings _useraccountsettings = null;
public com.bwsi.MeterReader.validationrptgenerator _validationrptgenerator = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
anywheresoftware.b4j.object.JavaObject _jo = null;
anywheresoftware.b4a.object.XmlLayoutBuilder _xl = null;
 //BA.debugLineNum = 69;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 70;BA.debugLine="MyScale.SetRate(0.5)";
mostCurrent._myscale._setrate /*String*/ (mostCurrent.activityBA,0.5);
 //BA.debugLineNum = 71;BA.debugLine="Activity.LoadLayout(\"BookListing\")";
mostCurrent._activity.LoadLayout("BookListing",mostCurrent.activityBA);
 //BA.debugLineNum = 73;BA.debugLine="GlobalVar.CSTitle.Initialize.Size(17).Bold.Append";
mostCurrent._globalvar._cstitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (17)).Bold().Append(BA.ObjectToCharSequence(("Bill Printing"))).PopAll();
 //BA.debugLineNum = 74;BA.debugLine="GlobalVar.CSSubTitle.Initialize.Size(14).Append($";
mostCurrent._globalvar._cssubtitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (14)).Append(BA.ObjectToCharSequence(("List of Account's Bill per Book"))).PopAll();
 //BA.debugLineNum = 76;BA.debugLine="ToolBar.InitMenuListener";
mostCurrent._toolbar.InitMenuListener();
 //BA.debugLineNum = 77;BA.debugLine="ToolBar.Title = GlobalVar.CSTitle";
mostCurrent._toolbar.setTitle(BA.ObjectToCharSequence(mostCurrent._globalvar._cstitle /*anywheresoftware.b4a.objects.CSBuilder*/ .getObject()));
 //BA.debugLineNum = 78;BA.debugLine="ToolBar.SubTitle = GlobalVar.CSSubTitle";
mostCurrent._toolbar.setSubTitle(BA.ObjectToCharSequence(mostCurrent._globalvar._cssubtitle /*anywheresoftware.b4a.objects.CSBuilder*/ .getObject()));
 //BA.debugLineNum = 79;BA.debugLine="ToolBar.SetElevationAnimated(5,5dip)";
mostCurrent._toolbar.SetElevationAnimated((int) (5),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 81;BA.debugLine="Dim jo As JavaObject";
_jo = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 82;BA.debugLine="Dim xl As XmlLayoutBuilder";
_xl = new anywheresoftware.b4a.object.XmlLayoutBuilder();
 //BA.debugLineNum = 83;BA.debugLine="jo = ToolBar";
_jo = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(mostCurrent._toolbar.getObject()));
 //BA.debugLineNum = 84;BA.debugLine="jo.RunMethod(\"setPopupTheme\", Array(xl.GetResourc";
_jo.RunMethod("setPopupTheme",new Object[]{(Object)(_xl.GetResourceId("style","ToolbarMenu"))});
 //BA.debugLineNum = 85;BA.debugLine="jo.RunMethod(\"setContentInsetStartWithNavigation\"";
_jo.RunMethod("setContentInsetStartWithNavigation",new Object[]{(Object)(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)))});
 //BA.debugLineNum = 86;BA.debugLine="jo.RunMethod(\"setTitleMarginStart\", Array(0dip))";
_jo.RunMethod("setTitleMarginStart",new Object[]{(Object)(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)))});
 //BA.debugLineNum = 88;BA.debugLine="ActionBarButton.Initialize";
mostCurrent._actionbarbutton.Initialize(mostCurrent.activityBA);
 //BA.debugLineNum = 89;BA.debugLine="ActionBarButton.ShowUpIndicator = True";
mostCurrent._actionbarbutton.setShowUpIndicator(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 91;BA.debugLine="pnlCustomers.Initialize(\"\")";
mostCurrent._pnlcustomers.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 92;BA.debugLine="SearchPanel.Visible = False";
mostCurrent._searchpanel.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 93;BA.debugLine="blnSearching = False";
_blnsearching = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 94;BA.debugLine="DisplayCustomers(GlobalVar.BranchID, GlobalVar.Bi";
_displaycustomers(mostCurrent._globalvar._branchid /*int*/ ,(int) (mostCurrent._globalvar._billyear /*double*/ ),mostCurrent._globalvar._billmonth /*int*/ ,mostCurrent._globalvar._userid /*int*/ );
 //BA.debugLineNum = 96;BA.debugLine="End Sub";
return "";
}
public static String  _activity_createmenu(de.amberhome.objects.appcompat.ACMenuWrapper _menu) throws Exception{
de.amberhome.objects.appcompat.ACMenuItemWrapper _iitem = null;
 //BA.debugLineNum = 18;BA.debugLine="Sub Activity_CreateMenu(Menu As ACMenu)";
 //BA.debugLineNum = 19;BA.debugLine="Dim iItem As ACMenuItem";
_iitem = new de.amberhome.objects.appcompat.ACMenuItemWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Menu.Clear";
_menu.Clear();
 //BA.debugLineNum = 22;BA.debugLine="Menu.Add2(1, 1, $\"Search\"$, xmlIcon.GetDrawable(\"";
_menu.Add2((int) (1),(int) (1),BA.ObjectToCharSequence(("Search")),mostCurrent._xmlicon.GetDrawable("ic_search_white_24dp")).setShowAsAction(_iitem.SHOW_AS_ACTION_IF_ROOM);
 //BA.debugLineNum = 23;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 98;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 99;BA.debugLine="If KeyCode = 4 Then";
if (_keycode==4) { 
 //BA.debugLineNum = 100;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 }else {
 //BA.debugLineNum = 102;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 104;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 109;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 111;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 106;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 107;BA.debugLine="End Sub";
return "";
}
public static String  _btndetails_click() throws Exception{
int _index = 0;
 //BA.debugLineNum = 207;BA.debugLine="Sub btnDetails_Click";
 //BA.debugLineNum = 208;BA.debugLine="Dim index As Int = CLV1.GetItemFromView(Sender)";
_index = mostCurrent._clv1._getitemfromview /*int*/ ((anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA))));
 //BA.debugLineNum = 209;BA.debugLine="GlobalVar.BillID = CLV1.GetValue(index)";
mostCurrent._globalvar._billid /*double*/  = (double)(BA.ObjectToNumber(mostCurrent._clv1._getvalue /*Object*/ (_index)));
 //BA.debugLineNum = 210;BA.debugLine="Log(GlobalVar.BillID)";
anywheresoftware.b4a.keywords.Common.LogImpl("146530563",BA.NumberToString(mostCurrent._globalvar._billid /*double*/ ),0);
 //BA.debugLineNum = 211;BA.debugLine="StartActivity(CustomerBill)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._customerbill.getObject()));
 //BA.debugLineNum = 212;BA.debugLine="End Sub";
return "";
}
public static String  _clvassignment_itemclick(int _index,Object _value) throws Exception{
 //BA.debugLineNum = 202;BA.debugLine="Sub clvAssignment_ItemClick (Index As Int, Value A";
 //BA.debugLineNum = 203;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.PanelWrapper  _createcustlist(int _iwidth,String _sacctno,String _sacctname,String _saddress,String _sbookno,String _sseqno,String _smeterno,int _iwasread) throws Exception{
anywheresoftware.b4a.objects.B4XViewWrapper _p = null;
int _iheight = 0;
anywheresoftware.b4a.objects.CSBuilder _cswasread = null;
 //BA.debugLineNum = 171;BA.debugLine="Sub CreateCustList(iWidth As Int, sAcctNo As Strin";
 //BA.debugLineNum = 172;BA.debugLine="Dim p As B4XView = xui.CreatePanel(\"\")";
_p = new anywheresoftware.b4a.objects.B4XViewWrapper();
_p = _xui.CreatePanel(processBA,"");
 //BA.debugLineNum = 173;BA.debugLine="Dim iHeight As Int = 165dip";
_iheight = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (165));
 //BA.debugLineNum = 175;BA.debugLine="Dim csWasRead As CSBuilder";
_cswasread = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 177;BA.debugLine="If GetDeviceLayoutValues.ApproximateScreenSize <";
if (anywheresoftware.b4a.keywords.Common.GetDeviceLayoutValues(mostCurrent.activityBA).getApproximateScreenSize()<4.5) { 
_iheight = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (310));};
 //BA.debugLineNum = 178;BA.debugLine="p.SetLayoutAnimated(0, 0, 0, iWidth, iHeight)";
_p.SetLayoutAnimated((int) (0),(int) (0),(int) (0),_iwidth,_iheight);
 //BA.debugLineNum = 179;BA.debugLine="p.LoadLayout(\"CustList\")";
_p.LoadLayout("CustList",mostCurrent.activityBA);
 //BA.debugLineNum = 181;BA.debugLine="If iWasRead = 0 Then";
if (_iwasread==0) { 
 //BA.debugLineNum = 182;BA.debugLine="csWasRead.Initialize.Size(20).Bold.Color(Colors.";
_cswasread.Initialize().Size((int) (20)).Bold().Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(("UNREAD"))).PopAll();
 //BA.debugLineNum = 183;BA.debugLine="btnDetails.Enabled = False";
mostCurrent._btndetails.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 185;BA.debugLine="csWasRead.Initialize.Size(20).Color(GlobalVar.Pr";
_cswasread.Initialize().Size((int) (20)).Color((int) (mostCurrent._globalvar._pricolor /*double*/ )).Append(BA.ObjectToCharSequence(("READ"))).PopAll();
 //BA.debugLineNum = 186;BA.debugLine="btnDetails.Enabled = True";
mostCurrent._btndetails.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 189;BA.debugLine="lblAccountNo.Text = sAcctNo";
mostCurrent._lblaccountno.setText(BA.ObjectToCharSequence(_sacctno));
 //BA.debugLineNum = 190;BA.debugLine="lblAccountName.Text = sAcctName";
mostCurrent._lblaccountname.setText(BA.ObjectToCharSequence(_sacctname));
 //BA.debugLineNum = 191;BA.debugLine="lblAddress.Text = GlobalVar.SF.Upper(sAddress)";
mostCurrent._lbladdress.setText(BA.ObjectToCharSequence(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv5(_saddress)));
 //BA.debugLineNum = 192;BA.debugLine="lblBookNo.Text = sBookNo";
mostCurrent._lblbookno.setText(BA.ObjectToCharSequence(_sbookno));
 //BA.debugLineNum = 193;BA.debugLine="lblSeqNo.Text = sSeqNo";
mostCurrent._lblseqno.setText(BA.ObjectToCharSequence(_sseqno));
 //BA.debugLineNum = 194;BA.debugLine="lblMeterNo.Text = sMeterNo";
mostCurrent._lblmeterno.setText(BA.ObjectToCharSequence(_smeterno));
 //BA.debugLineNum = 195;BA.debugLine="lblStatus.Text = csWasRead";
mostCurrent._lblstatus.setText(BA.ObjectToCharSequence(_cswasread.getObject()));
 //BA.debugLineNum = 197;BA.debugLine="CD.Initialize2(0xFF1976D2, 25, 0,0xFF000000)";
mostCurrent._cd.Initialize2((int) (0xff1976d2),(int) (25),(int) (0),(int) (0xff000000));
 //BA.debugLineNum = 198;BA.debugLine="btnDetails.Background = CD";
mostCurrent._btndetails.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cd.getObject()));
 //BA.debugLineNum = 199;BA.debugLine="Return p";
if (true) return (anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(_p.getObject()));
 //BA.debugLineNum = 200;BA.debugLine="End Sub";
return null;
}
public static String  _displaycustomers(int _ibranchid,int _ibillyear,int _ibillmonth,int _iuserid) throws Exception{
int _i = 0;
 //BA.debugLineNum = 140;BA.debugLine="Private Sub DisplayCustomers(iBranchID As Int, iBi";
 //BA.debugLineNum = 141;BA.debugLine="Try";
try { //BA.debugLineNum = 142;BA.debugLine="Starter.strCriteria = \"SELECT ReadID, AcctNo, Ac";
mostCurrent._starter._strcriteria /*String*/  = "SELECT ReadID, AcctNo, AcctName, AcctAddress, "+"BookNo, SeqNo, MeterNo, WasRead "+"FROM tblReadings "+"WHERE BranchID = "+BA.NumberToString(_ibranchid)+" "+"AND BillYear = "+BA.NumberToString(_ibillyear)+" "+"AND BillMonth = "+BA.NumberToString(_ibillmonth)+" "+"AND ReadBy = "+BA.NumberToString(_iuserid)+" "+"AND WasRead = 1 "+"ORDER BY BookNo Asc, SeqNo Asc";
 //BA.debugLineNum = 152;BA.debugLine="rsAccts = Starter.DBCon.ExecQuery(Starter.strCri";
mostCurrent._rsaccts = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 153;BA.debugLine="CLV1.Clear";
mostCurrent._clv1._clear /*String*/ ();
 //BA.debugLineNum = 154;BA.debugLine="If rsAccts.RowCount > 0 Then";
if (mostCurrent._rsaccts.getRowCount()>0) { 
 //BA.debugLineNum = 155;BA.debugLine="pnlCustomers.Visible = True";
mostCurrent._pnlcustomers.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 156;BA.debugLine="For i = 0 To rsAccts.RowCount - 1";
{
final int step7 = 1;
final int limit7 = (int) (mostCurrent._rsaccts.getRowCount()-1);
_i = (int) (0) ;
for (;_i <= limit7 ;_i = _i + step7 ) {
 //BA.debugLineNum = 157;BA.debugLine="rsAccts.Position = i";
mostCurrent._rsaccts.setPosition(_i);
 //BA.debugLineNum = 158;BA.debugLine="CLV1.Add(CreateCustList(CLV1.AsView.Width, rsA";
mostCurrent._clv1._add /*String*/ ((anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_createcustlist(mostCurrent._clv1._asview /*anywheresoftware.b4a.objects.B4XViewWrapper*/ ().getWidth(),mostCurrent._rsaccts.GetString("AcctNo"),mostCurrent._rsaccts.GetString("AcctName"),mostCurrent._rsaccts.GetString("AcctAddress"),mostCurrent._rsaccts.GetString("BookNo"),mostCurrent._rsaccts.GetString("SeqNo"),mostCurrent._rsaccts.GetString("MeterNo"),mostCurrent._rsaccts.GetInt("WasRead")).getObject())),(Object)(mostCurrent._rsaccts.GetInt("ReadID")));
 }
};
 }else {
 //BA.debugLineNum = 161;BA.debugLine="Log(rsAccts.RowCount)";
anywheresoftware.b4a.keywords.Common.LogImpl("146333973",BA.NumberToString(mostCurrent._rsaccts.getRowCount()),0);
 };
 } 
       catch (Exception e15) {
			processBA.setLastException(e15); //BA.debugLineNum = 164;BA.debugLine="rsAccts.Close";
mostCurrent._rsaccts.Close();
 //BA.debugLineNum = 165;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("146333977",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 167;BA.debugLine="rsAccts.Close";
mostCurrent._rsaccts.Close();
 //BA.debugLineNum = 168;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 169;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 29;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 30;BA.debugLine="Dim ActionBarButton As ACActionBar";
mostCurrent._actionbarbutton = new de.amberhome.objects.appcompat.ACActionBar();
 //BA.debugLineNum = 31;BA.debugLine="Private ToolBar As ACToolBarDark";
mostCurrent._toolbar = new de.amberhome.objects.appcompat.ACToolbarDarkWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private xmlIcon As XmlLayoutBuilder";
mostCurrent._xmlicon = new anywheresoftware.b4a.object.XmlLayoutBuilder();
 //BA.debugLineNum = 35;BA.debugLine="Private MatDialogBuilder As MaterialDialogBuilder";
mostCurrent._matdialogbuilder = new de.amberhome.materialdialogs.MaterialDialogBuilderWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private rsBooks As Cursor";
mostCurrent._rsbooks = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private rsAccts As Cursor";
mostCurrent._rsaccts = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private rsCustomers As Cursor";
mostCurrent._rscustomers = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private CLV1 As classCustomListView";
mostCurrent._clv1 = new com.bwsi.MeterReader.classcustomlistview();
 //BA.debugLineNum = 44;BA.debugLine="Private pnlCustomers As Panel";
mostCurrent._pnlcustomers = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private btnDetails As ACButton";
mostCurrent._btndetails = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Private lblAccountName As Label";
mostCurrent._lblaccountname = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private lblAccountNo As Label";
mostCurrent._lblaccountno = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Private lblAddress As Label";
mostCurrent._lbladdress = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Private lblBookNo As Label";
mostCurrent._lblbookno = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Private lblMeterNo As Label";
mostCurrent._lblmeterno = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 51;BA.debugLine="Private lblSeqNo As Label";
mostCurrent._lblseqno = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 52;BA.debugLine="Private lblStatus As Label";
mostCurrent._lblstatus = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 54;BA.debugLine="Private CD As ColorDrawable";
mostCurrent._cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 55;BA.debugLine="Private pnlContent As Panel";
mostCurrent._pnlcontent = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 56;BA.debugLine="Private dblReadID As Double";
_dblreadid = 0;
 //BA.debugLineNum = 57;BA.debugLine="Private SearchPanel As Panel";
mostCurrent._searchpanel = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 58;BA.debugLine="Private SV As SearchView";
mostCurrent._sv = new com.bwsi.MeterReader.searchview();
 //BA.debugLineNum = 59;BA.debugLine="Private blnSearching As Boolean";
_blnsearching = false;
 //BA.debugLineNum = 62;BA.debugLine="Private optAcctName As RadioButton";
mostCurrent._optacctname = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 63;BA.debugLine="Private optBookNo As RadioButton";
mostCurrent._optbookno = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 64;BA.debugLine="Private optMeterNo As RadioButton";
mostCurrent._optmeterno = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 65;BA.debugLine="Private optSeqNo As RadioButton";
mostCurrent._optseqno = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 66;BA.debugLine="Private pnlSearchBy As Panel";
mostCurrent._pnlsearchby = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 67;BA.debugLine="End Sub";
return "";
}
public static String  _optacctname_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 249;BA.debugLine="Sub optAcctName_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 250;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 251;BA.debugLine="SearchAccount(3)";
_searchaccount((int) (3));
 };
 //BA.debugLineNum = 253;BA.debugLine="End Sub";
return "";
}
public static String  _optbookno_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 231;BA.debugLine="Sub optBookNo_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 232;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 233;BA.debugLine="SearchAccount(0)";
_searchaccount((int) (0));
 };
 //BA.debugLineNum = 235;BA.debugLine="End Sub";
return "";
}
public static String  _optmeterno_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 243;BA.debugLine="Sub optMeterNo_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 244;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 245;BA.debugLine="SearchAccount(2)";
_searchaccount((int) (2));
 };
 //BA.debugLineNum = 247;BA.debugLine="End Sub";
return "";
}
public static String  _optseqno_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 237;BA.debugLine="Sub optSeqNo_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 238;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 239;BA.debugLine="SearchAccount(1)";
_searchaccount((int) (1));
 };
 //BA.debugLineNum = 241;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 25;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 26;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 27;BA.debugLine="End Sub";
return "";
}
public static String  _searchaccount(int _isearchby) throws Exception{
String _sfield1 = "";
String _sfield2 = "";
String _sfield3 = "";
anywheresoftware.b4a.objects.collections.List _searchlist = null;
int _i = 0;
com.bwsi.MeterReader.searchview._item _it = null;
 //BA.debugLineNum = 255;BA.debugLine="Private Sub SearchAccount(iSearchBy As Int)";
 //BA.debugLineNum = 256;BA.debugLine="Dim sField1, sField2, sField3 As String";
_sfield1 = "";
_sfield2 = "";
_sfield3 = "";
 //BA.debugLineNum = 257;BA.debugLine="Dim SearchList As List";
_searchlist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 259;BA.debugLine="SearchList.Initialize";
_searchlist.Initialize();
 //BA.debugLineNum = 260;BA.debugLine="SearchList.Clear";
_searchlist.Clear();
 //BA.debugLineNum = 262;BA.debugLine="Select iSearchBy";
switch (_isearchby) {
case 0: {
 //BA.debugLineNum = 264;BA.debugLine="sField1 = \"BookNo\"";
_sfield1 = "BookNo";
 //BA.debugLineNum = 265;BA.debugLine="sField2 = \"AcctName\"";
_sfield2 = "AcctName";
 //BA.debugLineNum = 266;BA.debugLine="SV.et.InputType = SV.et.INPUT_TYPE_NUMBERS";
mostCurrent._sv._et /*anywheresoftware.b4a.objects.EditTextWrapper*/ .setInputType(mostCurrent._sv._et /*anywheresoftware.b4a.objects.EditTextWrapper*/ .INPUT_TYPE_NUMBERS);
 break; }
case 1: {
 //BA.debugLineNum = 268;BA.debugLine="sField1 = \"SeqNo\"";
_sfield1 = "SeqNo";
 //BA.debugLineNum = 269;BA.debugLine="sField2 = \"AcctName\"";
_sfield2 = "AcctName";
 //BA.debugLineNum = 270;BA.debugLine="SV.et.InputType = SV.et.INPUT_TYPE_NUMBERS";
mostCurrent._sv._et /*anywheresoftware.b4a.objects.EditTextWrapper*/ .setInputType(mostCurrent._sv._et /*anywheresoftware.b4a.objects.EditTextWrapper*/ .INPUT_TYPE_NUMBERS);
 break; }
case 2: {
 //BA.debugLineNum = 272;BA.debugLine="sField1 = \"MeterNo\"";
_sfield1 = "MeterNo";
 //BA.debugLineNum = 273;BA.debugLine="sField2 = \"AcctName\"";
_sfield2 = "AcctName";
 //BA.debugLineNum = 274;BA.debugLine="SV.et.InputType = SV.et.INPUT_TYPE_NUMBERS";
mostCurrent._sv._et /*anywheresoftware.b4a.objects.EditTextWrapper*/ .setInputType(mostCurrent._sv._et /*anywheresoftware.b4a.objects.EditTextWrapper*/ .INPUT_TYPE_NUMBERS);
 break; }
case 3: {
 //BA.debugLineNum = 276;BA.debugLine="sField1 = \"AcctName\"";
_sfield1 = "AcctName";
 //BA.debugLineNum = 277;BA.debugLine="sField2 = \"BookNo\"";
_sfield2 = "BookNo";
 //BA.debugLineNum = 278;BA.debugLine="SV.et.InputType = SV.et.INPUT_TYPE_TEXT";
mostCurrent._sv._et /*anywheresoftware.b4a.objects.EditTextWrapper*/ .setInputType(mostCurrent._sv._et /*anywheresoftware.b4a.objects.EditTextWrapper*/ .INPUT_TYPE_TEXT);
 break; }
}
;
 //BA.debugLineNum = 281;BA.debugLine="If SV.IsInitialized=False Then";
if (mostCurrent._sv.IsInitialized /*boolean*/ ()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 282;BA.debugLine="SV.Initialize(Me,\"SV\")";
mostCurrent._sv._initialize /*String*/ (mostCurrent.activityBA,customerlist.getObject(),"SV");
 };
 //BA.debugLineNum = 285;BA.debugLine="SV.ClearAll";
mostCurrent._sv._clearall /*String*/ ();
 //BA.debugLineNum = 286;BA.debugLine="SV.ClearSearchBox";
mostCurrent._sv._clearsearchbox /*String*/ ();
 //BA.debugLineNum = 287;BA.debugLine="SV.lv.Clear";
mostCurrent._sv._lv /*anywheresoftware.b4a.objects.ListViewWrapper*/ .Clear();
 //BA.debugLineNum = 289;BA.debugLine="SearchCustomers(sField1)";
_searchcustomers(_sfield1);
 //BA.debugLineNum = 290;BA.debugLine="If rsCustomers.RowCount > 0 Then";
if (mostCurrent._rscustomers.getRowCount()>0) { 
 //BA.debugLineNum = 291;BA.debugLine="For i = 0 To rsCustomers.RowCount - 1";
{
final int step31 = 1;
final int limit31 = (int) (mostCurrent._rscustomers.getRowCount()-1);
_i = (int) (0) ;
for (;_i <= limit31 ;_i = _i + step31 ) {
 //BA.debugLineNum = 292;BA.debugLine="Log(rsCustomers.RowCount)";
anywheresoftware.b4a.keywords.Common.LogImpl("146923813",BA.NumberToString(mostCurrent._rscustomers.getRowCount()),0);
 //BA.debugLineNum = 293;BA.debugLine="rsCustomers.Position = i";
mostCurrent._rscustomers.setPosition(_i);
 //BA.debugLineNum = 294;BA.debugLine="Dim IT As Item";
_it = new com.bwsi.MeterReader.searchview._item();
 //BA.debugLineNum = 295;BA.debugLine="IT.Title = rsCustomers.GetString(sField1) & \" -";
_it.Title /*String*/  = mostCurrent._rscustomers.GetString(_sfield1)+" - "+mostCurrent._rscustomers.GetString(_sfield2);
 //BA.debugLineNum = 296;BA.debugLine="IT.Text = rsCustomers.GetString(\"AcctAddress\")";
_it.Text /*String*/  = mostCurrent._rscustomers.GetString("AcctAddress");
 //BA.debugLineNum = 297;BA.debugLine="IT.SearchText = rsCustomers.GetString(sField1).";
_it.SearchText /*String*/  = mostCurrent._rscustomers.GetString(_sfield1).toLowerCase();
 //BA.debugLineNum = 298;BA.debugLine="IT.Value = rsCustomers.GetInt(\"ReadID\")";
_it.Value /*Object*/  = (Object)(mostCurrent._rscustomers.GetInt("ReadID"));
 //BA.debugLineNum = 299;BA.debugLine="SearchList.Add(IT)";
_searchlist.Add((Object)(_it));
 }
};
 };
 //BA.debugLineNum = 302;BA.debugLine="SV.SetItems(SearchList)";
mostCurrent._sv._setitems /*Object*/ (_searchlist);
 //BA.debugLineNum = 303;BA.debugLine="SearchList.Clear";
_searchlist.Clear();
 //BA.debugLineNum = 304;BA.debugLine="End Sub";
return "";
}
public static String  _searchcustomers(String _ssearchby) throws Exception{
 //BA.debugLineNum = 214;BA.debugLine="Private Sub SearchCustomers(sSearchBy As String)";
 //BA.debugLineNum = 215;BA.debugLine="Try";
try { //BA.debugLineNum = 216;BA.debugLine="Starter.strCriteria = \"SELECT ReadID, AcctNo, Ac";
mostCurrent._starter._strcriteria /*String*/  = "SELECT ReadID, AcctNo, AcctName, AcctAddress, "+"BookNo, SeqNo, MeterNo, WasRead "+"FROM tblReadings "+"WHERE BranchID = "+BA.NumberToString(mostCurrent._globalvar._branchid /*int*/ )+" "+"AND BillYear = "+BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ )+" "+"AND BillMonth = "+BA.NumberToString(mostCurrent._globalvar._billmonth /*int*/ )+" "+"AND ReadBy = "+BA.NumberToString(mostCurrent._globalvar._userid /*int*/ )+" "+"ORDER BY "+_ssearchby;
 //BA.debugLineNum = 225;BA.debugLine="rsCustomers = Starter.DBCon.ExecQuery(Starter.st";
mostCurrent._rscustomers = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 } 
       catch (Exception e5) {
			processBA.setLastException(e5); //BA.debugLineNum = 227;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("146596109",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 229;BA.debugLine="End Sub";
return "";
}
public static String  _toolbar_menuitemclick(de.amberhome.objects.appcompat.ACMenuItemWrapper _item) throws Exception{
 //BA.debugLineNum = 126;BA.debugLine="Sub ToolBar_MenuItemClick (Item As ACMenuItem)";
 //BA.debugLineNum = 127;BA.debugLine="If SearchPanel.Visible = True Then";
if (mostCurrent._searchpanel.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 128;BA.debugLine="Return";
if (true) return "";
 }else {
 //BA.debugLineNum = 130;BA.debugLine="blnSearching = True";
_blnsearching = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 131;BA.debugLine="SearchPanel.Visible = True";
mostCurrent._searchpanel.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 132;BA.debugLine="SV.Initialize(Me,\"SV\")";
mostCurrent._sv._initialize /*String*/ (mostCurrent.activityBA,customerlist.getObject(),"SV");
 //BA.debugLineNum = 133;BA.debugLine="SV.AddToParent(SearchPanel,1dip,50dip,SearchPane";
mostCurrent._sv._addtoparent /*String*/ (mostCurrent._searchpanel,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),mostCurrent._searchpanel.getWidth(),mostCurrent._searchpanel.getHeight());
 //BA.debugLineNum = 134;BA.debugLine="SV.ClearAll";
mostCurrent._sv._clearall /*String*/ ();
 //BA.debugLineNum = 135;BA.debugLine="optBookNo.Checked = True";
mostCurrent._optbookno.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 136;BA.debugLine="optBookNo_CheckedChange(True)";
_optbookno_checkedchange(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 138;BA.debugLine="End Sub";
return "";
}
public static String  _toolbar_navigationitemclick() throws Exception{
 //BA.debugLineNum = 114;BA.debugLine="Sub ToolBar_NavigationItemClick";
 //BA.debugLineNum = 115;BA.debugLine="If blnSearching = True Then";
if (_blnsearching==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 116;BA.debugLine="blnSearching = False";
_blnsearching = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 117;BA.debugLine="SV.ClearSearchBox";
mostCurrent._sv._clearsearchbox /*String*/ ();
 //BA.debugLineNum = 118;BA.debugLine="SV.ClearAll";
mostCurrent._sv._clearall /*String*/ ();
 //BA.debugLineNum = 119;BA.debugLine="SearchPanel.Visible = False";
mostCurrent._searchpanel.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 121;BA.debugLine="blnSearching = False";
_blnsearching = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 122;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 124;BA.debugLine="End Sub";
return "";
}

public boolean _onCreateOptionsMenu(android.view.Menu menu) {
	if (processBA.subExists("activity_createmenu")) {
		processBA.raiseEvent2(null, true, "activity_createmenu", false, new de.amberhome.objects.appcompat.ACMenuWrapper(menu));
		return true;
	}
	else
		return false;
}
}
