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

public class cmrvr extends androidx.appcompat.app.AppCompatActivity implements B4AActivity{
	public static cmrvr mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "com.bwsi.MeterReader", "com.bwsi.MeterReader.cmrvr");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (cmrvr).");
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
		activityBA = new BA(this, layout, processBA, "com.bwsi.MeterReader", "com.bwsi.MeterReader.cmrvr");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "com.bwsi.MeterReader.cmrvr", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (cmrvr) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (cmrvr) Resume **");
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
		return cmrvr.class;
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
            BA.LogInfo("** Activity (cmrvr) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (cmrvr) Pause event (activity is not paused). **");
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
            cmrvr mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (cmrvr) Resume **");
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
public de.amberhome.objects.appcompat.ACButtonWrapper _btnavebill = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnhighbill = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnlowbill = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnmisscoded = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnzerocons = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltitle = null;
public de.amberhome.objects.SnackbarWrapper _snack = null;
public static int _iunusualrpt = 0;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public b4a.example.dateutils _dateutils = null;
public com.bwsi.MeterReader.main _main = null;
public com.bwsi.MeterReader.datasyncing _datasyncing = null;
public com.bwsi.MeterReader.camera _camera = null;
public com.bwsi.MeterReader.customerbill _customerbill = null;
public com.bwsi.MeterReader.customerlist _customerlist = null;
public com.bwsi.MeterReader.customfunctions _customfunctions = null;
public com.bwsi.MeterReader.dbasefunctions _dbasefunctions = null;
public com.bwsi.MeterReader.dbutils _dbutils = null;
public com.bwsi.MeterReader.globalvar _globalvar = null;
public com.bwsi.MeterReader.login _login = null;
public com.bwsi.MeterReader.mainscreen _mainscreen = null;
public com.bwsi.MeterReader.meterreading _meterreading = null;
public com.bwsi.MeterReader.modvariables _modvariables = null;
public com.bwsi.MeterReader.myscale _myscale = null;
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
 //BA.debugLineNum = 52;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 53;BA.debugLine="MyScale.SetRate(0.5)";
mostCurrent._myscale._setrate /*String*/ (mostCurrent.activityBA,0.5);
 //BA.debugLineNum = 54;BA.debugLine="Activity.LoadLayout(\"ReadingValidation\")";
mostCurrent._activity.LoadLayout("ReadingValidation",mostCurrent.activityBA);
 //BA.debugLineNum = 56;BA.debugLine="GlobalVar.CSTitle.Initialize.Size(17).Bold.Append";
mostCurrent._globalvar._cstitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (17)).Bold().Append(BA.ObjectToCharSequence(("Reading Validation Report"))).PopAll();
 //BA.debugLineNum = 57;BA.debugLine="GlobalVar.CSSubTitle.Initialize.Size(14).Append($";
mostCurrent._globalvar._cssubtitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (14)).Append(BA.ObjectToCharSequence(("List of Account's with Unusual Reading"))).PopAll();
 //BA.debugLineNum = 59;BA.debugLine="ToolBar.InitMenuListener";
mostCurrent._toolbar.InitMenuListener();
 //BA.debugLineNum = 60;BA.debugLine="ToolBar.Title = GlobalVar.CSTitle";
mostCurrent._toolbar.setTitle(BA.ObjectToCharSequence(mostCurrent._globalvar._cstitle /*anywheresoftware.b4a.objects.CSBuilder*/ .getObject()));
 //BA.debugLineNum = 61;BA.debugLine="ToolBar.SubTitle = GlobalVar.CSSubTitle";
mostCurrent._toolbar.setSubTitle(BA.ObjectToCharSequence(mostCurrent._globalvar._cssubtitle /*anywheresoftware.b4a.objects.CSBuilder*/ .getObject()));
 //BA.debugLineNum = 62;BA.debugLine="ToolBar.SetElevationAnimated(5,5dip)";
mostCurrent._toolbar.SetElevationAnimated((int) (5),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 64;BA.debugLine="Dim jo As JavaObject";
_jo = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 65;BA.debugLine="Dim xl As XmlLayoutBuilder";
_xl = new anywheresoftware.b4a.object.XmlLayoutBuilder();
 //BA.debugLineNum = 66;BA.debugLine="jo = ToolBar";
_jo = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(mostCurrent._toolbar.getObject()));
 //BA.debugLineNum = 67;BA.debugLine="jo.RunMethod(\"setPopupTheme\", Array(xl.GetResourc";
_jo.RunMethod("setPopupTheme",new Object[]{(Object)(_xl.GetResourceId("style","ToolbarMenu"))});
 //BA.debugLineNum = 68;BA.debugLine="jo.RunMethod(\"setContentInsetStartWithNavigation\"";
_jo.RunMethod("setContentInsetStartWithNavigation",new Object[]{(Object)(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)))});
 //BA.debugLineNum = 69;BA.debugLine="jo.RunMethod(\"setTitleMarginStart\", Array(0dip))";
_jo.RunMethod("setTitleMarginStart",new Object[]{(Object)(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)))});
 //BA.debugLineNum = 71;BA.debugLine="ActionBarButton.Initialize";
mostCurrent._actionbarbutton.Initialize(mostCurrent.activityBA);
 //BA.debugLineNum = 72;BA.debugLine="ActionBarButton.ShowUpIndicator = True";
mostCurrent._actionbarbutton.setShowUpIndicator(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 73;BA.debugLine="SetButtons";
_setbuttons();
 //BA.debugLineNum = 75;BA.debugLine="End Sub";
return "";
}
public static String  _activity_createmenu(de.amberhome.objects.appcompat.ACMenuWrapper _menu) throws Exception{
 //BA.debugLineNum = 18;BA.debugLine="Sub Activity_CreateMenu(Menu As ACMenu)";
 //BA.debugLineNum = 23;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 77;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 78;BA.debugLine="If KeyCode = 4 Then";
if (_keycode==4) { 
 //BA.debugLineNum = 79;BA.debugLine="ToolBar_NavigationItemClick";
_toolbar_navigationitemclick();
 //BA.debugLineNum = 80;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 }else {
 //BA.debugLineNum = 82;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 84;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 94;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 96;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 90;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 91;BA.debugLine="GlobalVar.iReaderID = 0";
mostCurrent._globalvar._ireaderid /*int*/  = (int) (0);
 //BA.debugLineNum = 92;BA.debugLine="End Sub";
return "";
}
public static String  _btnavebill_click() throws Exception{
 //BA.debugLineNum = 166;BA.debugLine="Sub btnAveBill_Click";
 //BA.debugLineNum = 167;BA.debugLine="iUnusualRpt = 4";
_iunusualrpt = (int) (4);
 //BA.debugLineNum = 168;BA.debugLine="GlobalVar.iReportLayout = 4";
mostCurrent._globalvar._ireportlayout /*int*/  = (int) (4);
 //BA.debugLineNum = 170;BA.debugLine="If GlobalVar.Mod1 = 0 Then";
if (mostCurrent._globalvar._mod1 /*int*/ ==0) { 
 //BA.debugLineNum = 171;BA.debugLine="ShowReaders";
_showreaders();
 }else {
 //BA.debugLineNum = 173;BA.debugLine="GlobalVar.iReaderID = GlobalVar.UserID";
mostCurrent._globalvar._ireaderid /*int*/  = mostCurrent._globalvar._userid /*int*/ ;
 //BA.debugLineNum = 175;BA.debugLine="If IsThereUnusual(4, GlobalVar.iReaderID) = Fals";
if (_isthereunusual((int) (4),mostCurrent._globalvar._ireaderid /*int*/ )==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 176;BA.debugLine="DispErrorMsg(4)";
_disperrormsg((int) (4));
 //BA.debugLineNum = 177;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 179;BA.debugLine="StartActivity(ValidationRptGenerator)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._validationrptgenerator.getObject()));
 };
 //BA.debugLineNum = 181;BA.debugLine="End Sub";
return "";
}
public static String  _btnhighbill_click() throws Exception{
 //BA.debugLineNum = 115;BA.debugLine="Sub btnHighBill_Click";
 //BA.debugLineNum = 116;BA.debugLine="iUnusualRpt = 1";
_iunusualrpt = (int) (1);
 //BA.debugLineNum = 117;BA.debugLine="GlobalVar.iReportLayout = 1";
mostCurrent._globalvar._ireportlayout /*int*/  = (int) (1);
 //BA.debugLineNum = 119;BA.debugLine="If GlobalVar.Mod1 = 0 Then";
if (mostCurrent._globalvar._mod1 /*int*/ ==0) { 
 //BA.debugLineNum = 120;BA.debugLine="ShowReaders";
_showreaders();
 }else {
 //BA.debugLineNum = 122;BA.debugLine="GlobalVar.iReaderID = GlobalVar.UserID";
mostCurrent._globalvar._ireaderid /*int*/  = mostCurrent._globalvar._userid /*int*/ ;
 //BA.debugLineNum = 124;BA.debugLine="If IsThereUnusual(1, GlobalVar.iReaderID) = Fals";
if (_isthereunusual((int) (1),mostCurrent._globalvar._ireaderid /*int*/ )==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 125;BA.debugLine="DispErrorMsg(1)";
_disperrormsg((int) (1));
 //BA.debugLineNum = 126;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 128;BA.debugLine="StartActivity(ValidationRptGenerator)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._validationrptgenerator.getObject()));
 };
 //BA.debugLineNum = 130;BA.debugLine="End Sub";
return "";
}
public static String  _btnlowbill_click() throws Exception{
 //BA.debugLineNum = 132;BA.debugLine="Sub btnLowBill_Click";
 //BA.debugLineNum = 133;BA.debugLine="iUnusualRpt = 2";
_iunusualrpt = (int) (2);
 //BA.debugLineNum = 134;BA.debugLine="GlobalVar.iReportLayout = 2";
mostCurrent._globalvar._ireportlayout /*int*/  = (int) (2);
 //BA.debugLineNum = 136;BA.debugLine="If GlobalVar.Mod1 = 0 Then";
if (mostCurrent._globalvar._mod1 /*int*/ ==0) { 
 //BA.debugLineNum = 137;BA.debugLine="ShowReaders";
_showreaders();
 }else {
 //BA.debugLineNum = 139;BA.debugLine="GlobalVar.iReaderID = GlobalVar.UserID";
mostCurrent._globalvar._ireaderid /*int*/  = mostCurrent._globalvar._userid /*int*/ ;
 //BA.debugLineNum = 141;BA.debugLine="If IsThereUnusual(2, GlobalVar.iReaderID) = Fals";
if (_isthereunusual((int) (2),mostCurrent._globalvar._ireaderid /*int*/ )==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 142;BA.debugLine="DispErrorMsg(2)";
_disperrormsg((int) (2));
 //BA.debugLineNum = 143;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 145;BA.debugLine="StartActivity(ValidationRptGenerator)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._validationrptgenerator.getObject()));
 };
 //BA.debugLineNum = 147;BA.debugLine="End Sub";
return "";
}
public static String  _btnmisscoded_click() throws Exception{
 //BA.debugLineNum = 98;BA.debugLine="Sub btnMissCoded_Click";
 //BA.debugLineNum = 99;BA.debugLine="iUnusualRpt = 0";
_iunusualrpt = (int) (0);
 //BA.debugLineNum = 101;BA.debugLine="GlobalVar.iReportLayout = 0";
mostCurrent._globalvar._ireportlayout /*int*/  = (int) (0);
 //BA.debugLineNum = 102;BA.debugLine="If GlobalVar.Mod1 = 0 Then";
if (mostCurrent._globalvar._mod1 /*int*/ ==0) { 
 //BA.debugLineNum = 103;BA.debugLine="ShowReaders";
_showreaders();
 }else {
 //BA.debugLineNum = 105;BA.debugLine="GlobalVar.iReaderID = GlobalVar.UserID";
mostCurrent._globalvar._ireaderid /*int*/  = mostCurrent._globalvar._userid /*int*/ ;
 //BA.debugLineNum = 107;BA.debugLine="If IsThereUnusual(0, GlobalVar.iReaderID) = Fals";
if (_isthereunusual((int) (0),mostCurrent._globalvar._ireaderid /*int*/ )==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 108;BA.debugLine="DispErrorMsg(0)";
_disperrormsg((int) (0));
 //BA.debugLineNum = 109;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 111;BA.debugLine="StartActivity(ValidationRptGenerator)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._validationrptgenerator.getObject()));
 };
 //BA.debugLineNum = 113;BA.debugLine="End Sub";
return "";
}
public static String  _btnzerocons_click() throws Exception{
 //BA.debugLineNum = 149;BA.debugLine="Sub btnZeroCons_Click";
 //BA.debugLineNum = 150;BA.debugLine="iUnusualRpt = 3";
_iunusualrpt = (int) (3);
 //BA.debugLineNum = 151;BA.debugLine="GlobalVar.iReportLayout = 3";
mostCurrent._globalvar._ireportlayout /*int*/  = (int) (3);
 //BA.debugLineNum = 153;BA.debugLine="If GlobalVar.Mod1 = 0 Then";
if (mostCurrent._globalvar._mod1 /*int*/ ==0) { 
 //BA.debugLineNum = 154;BA.debugLine="ShowReaders";
_showreaders();
 }else {
 //BA.debugLineNum = 156;BA.debugLine="GlobalVar.iReaderID = GlobalVar.UserID";
mostCurrent._globalvar._ireaderid /*int*/  = mostCurrent._globalvar._userid /*int*/ ;
 //BA.debugLineNum = 158;BA.debugLine="If IsThereUnusual(3, GlobalVar.iReaderID) = Fals";
if (_isthereunusual((int) (3),mostCurrent._globalvar._ireaderid /*int*/ )==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 159;BA.debugLine="DispErrorMsg(3)";
_disperrormsg((int) (3));
 //BA.debugLineNum = 160;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 162;BA.debugLine="StartActivity(ValidationRptGenerator)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._validationrptgenerator.getObject()));
 };
 //BA.debugLineNum = 164;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.drawable.StateListDrawable  _createbuttoncolor(int _focusedcolor,int _enabledcolor,int _disabledcolor,int _pressedcolor) throws Exception{
anywheresoftware.b4a.objects.drawable.StateListDrawable _retcolor = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _drwfocusedcolor = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _drwenabledcolor = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _drwdisabledcolor = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _drwpressedcolor = null;
 //BA.debugLineNum = 191;BA.debugLine="Public Sub CreateButtonColor(FocusedColor As Int,";
 //BA.debugLineNum = 193;BA.debugLine="Dim RetColor As StateListDrawable";
_retcolor = new anywheresoftware.b4a.objects.drawable.StateListDrawable();
 //BA.debugLineNum = 194;BA.debugLine="RetColor.Initialize";
_retcolor.Initialize();
 //BA.debugLineNum = 195;BA.debugLine="Dim drwFocusedColor, drwEnabledColor, drwDisabled";
_drwfocusedcolor = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
_drwenabledcolor = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
_drwdisabledcolor = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
_drwpressedcolor = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 197;BA.debugLine="drwFocusedColor.Initialize2(FocusedColor, 25, 0,";
_drwfocusedcolor.Initialize2(_focusedcolor,(int) (25),(int) (0),anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 198;BA.debugLine="drwEnabledColor.Initialize2(EnabledColor, 25, 0,";
_drwenabledcolor.Initialize2(_enabledcolor,(int) (25),(int) (0),anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 199;BA.debugLine="drwDisabledColor.Initialize2(DisabledColor, 25, 2";
_drwdisabledcolor.Initialize2(_disabledcolor,(int) (25),(int) (2),anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 200;BA.debugLine="drwPressedColor.Initialize2(PressedColor, 25, 0,";
_drwpressedcolor.Initialize2(_pressedcolor,(int) (25),(int) (0),anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 202;BA.debugLine="RetColor.AddState(RetColor.State_Focused, drwFocu";
_retcolor.AddState(_retcolor.State_Focused,(android.graphics.drawable.Drawable)(_drwfocusedcolor.getObject()));
 //BA.debugLineNum = 203;BA.debugLine="RetColor.AddState(RetColor.State_Pressed, drwPres";
_retcolor.AddState(_retcolor.State_Pressed,(android.graphics.drawable.Drawable)(_drwpressedcolor.getObject()));
 //BA.debugLineNum = 204;BA.debugLine="RetColor.AddState(RetColor.State_Enabled, drwEnab";
_retcolor.AddState(_retcolor.State_Enabled,(android.graphics.drawable.Drawable)(_drwenabledcolor.getObject()));
 //BA.debugLineNum = 205;BA.debugLine="RetColor.AddState(RetColor.State_Disabled, drwDis";
_retcolor.AddState(_retcolor.State_Disabled,(android.graphics.drawable.Drawable)(_drwdisabledcolor.getObject()));
 //BA.debugLineNum = 206;BA.debugLine="RetColor.AddCatchAllState(drwFocusedColor)";
_retcolor.AddCatchAllState((android.graphics.drawable.Drawable)(_drwfocusedcolor.getObject()));
 //BA.debugLineNum = 207;BA.debugLine="RetColor.AddCatchAllState(drwEnabledColor)";
_retcolor.AddCatchAllState((android.graphics.drawable.Drawable)(_drwenabledcolor.getObject()));
 //BA.debugLineNum = 208;BA.debugLine="RetColor.AddCatchAllState(drwDisabledColor)";
_retcolor.AddCatchAllState((android.graphics.drawable.Drawable)(_drwdisabledcolor.getObject()));
 //BA.debugLineNum = 209;BA.debugLine="RetColor.AddCatchAllState(drwPressedColor)";
_retcolor.AddCatchAllState((android.graphics.drawable.Drawable)(_drwpressedcolor.getObject()));
 //BA.debugLineNum = 210;BA.debugLine="Return RetColor";
if (true) return _retcolor;
 //BA.debugLineNum = 212;BA.debugLine="End Sub";
return null;
}
public static String  _disperrormsg(int _iunusual) throws Exception{
anywheresoftware.b4a.objects.CSBuilder _cstitle = null;
anywheresoftware.b4a.objects.CSBuilder _csmsg = null;
anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper _icon = null;
String _smsg = "";
 //BA.debugLineNum = 277;BA.debugLine="Private Sub DispErrorMsg(iUnusual As Int)";
 //BA.debugLineNum = 278;BA.debugLine="Dim csTitle, csMsg As CSBuilder";
_cstitle = new anywheresoftware.b4a.objects.CSBuilder();
_csmsg = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 279;BA.debugLine="Dim icon As B4XBitmap";
_icon = new anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper();
 //BA.debugLineNum = 280;BA.debugLine="Dim sMsg As String";
_smsg = "";
 //BA.debugLineNum = 283;BA.debugLine="If iUnusual = 0 Then";
if (_iunusual==0) { 
 //BA.debugLineNum = 284;BA.debugLine="sMsg = $\"No Miss Coded/Erroneous Reading found.\"";
_smsg = ("No Miss Coded/Erroneous Reading found.");
 }else if(_iunusual==1) { 
 //BA.debugLineNum = 286;BA.debugLine="sMsg = $\"No High Bill Reading found.\"$";
_smsg = ("No High Bill Reading found.");
 }else if(_iunusual==2) { 
 //BA.debugLineNum = 288;BA.debugLine="sMsg = $\"No Low Bill Reading found.\"$";
_smsg = ("No Low Bill Reading found.");
 }else if(_iunusual==3) { 
 //BA.debugLineNum = 290;BA.debugLine="sMsg = $\"No Zero Consumption Reading found.\"$";
_smsg = ("No Zero Consumption Reading found.");
 }else if(_iunusual==4) { 
 //BA.debugLineNum = 292;BA.debugLine="sMsg = $\"No Average Bill Reading found.\"$";
_smsg = ("No Average Bill Reading found.");
 };
 //BA.debugLineNum = 295;BA.debugLine="csTitle.Initialize.Color(Colors.Red).Append($\"NO";
_cstitle.Initialize().Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(("NO RECORDS"))).PopAll();
 //BA.debugLineNum = 296;BA.debugLine="csMsg.Initialize.Bold.Color(Colors.DarkGray).Appe";
_csmsg.Initialize().Bold().Color(anywheresoftware.b4a.keywords.Common.Colors.DarkGray).Append(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.CRLF+_smsg)).PopAll();
 //BA.debugLineNum = 297;BA.debugLine="icon = xui.LoadBitmapResize(File.DirAssets, \"erro";
_icon = _xui.LoadBitmapResize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"error.png",anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (24)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (24)),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 298;BA.debugLine="Msgbox2(csMsg, csTitle, $\"OK\"$, \"\",\"\",icon)";
anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence(_csmsg.getObject()),BA.ObjectToCharSequence(_cstitle.getObject()),("OK"),"","",(android.graphics.Bitmap)(_icon.getObject()),mostCurrent.activityBA);
 //BA.debugLineNum = 299;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 41;BA.debugLine="Private btnAveBill As ACButton";
mostCurrent._btnavebill = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Private btnHighBill As ACButton";
mostCurrent._btnhighbill = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private btnLowBill As ACButton";
mostCurrent._btnlowbill = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Private btnMissCoded As ACButton";
mostCurrent._btnmisscoded = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private btnZeroCons As ACButton";
mostCurrent._btnzerocons = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Private lblTitle As Label";
mostCurrent._lbltitle = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Private snack As DSSnackbar";
mostCurrent._snack = new de.amberhome.objects.SnackbarWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Dim iUnusualRpt As Int";
_iunusualrpt = 0;
 //BA.debugLineNum = 50;BA.debugLine="End Sub";
return "";
}
public static boolean  _isthereunusual(int _iflag,int _iuserid) throws Exception{
boolean _blnretval = false;
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsunusual = null;
 //BA.debugLineNum = 214;BA.debugLine="Private Sub IsThereUnusual (iFlag As Int, iUserID";
 //BA.debugLineNum = 215;BA.debugLine="Dim blnRetVal As Boolean";
_blnretval = false;
 //BA.debugLineNum = 216;BA.debugLine="Dim rsUnusual As Cursor";
_rsunusual = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 217;BA.debugLine="Try";
try { //BA.debugLineNum = 218;BA.debugLine="Select Case iFlag";
switch (_iflag) {
case 0: {
 //BA.debugLineNum = 220;BA.debugLine="Starter.strCriteria = \"SELECT * FROM tblReadin";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblReadings "+"WHERE MissCoded = '"+BA.NumberToString(1)+"' "+"AND BranchID = "+BA.NumberToString(mostCurrent._globalvar._branchid /*int*/ )+" "+"AND BillYear = "+BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ )+" "+"AND BillMonth = "+BA.NumberToString(mostCurrent._globalvar._billmonth /*int*/ )+" "+"AND ReadBy = "+BA.NumberToString(_iuserid);
 break; }
case 1: {
 //BA.debugLineNum = 228;BA.debugLine="Starter.strCriteria = \"SELECT * FROM tblReadin";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblReadings "+"WHERE ImplosiveType = '"+"implosive-inc"+"' "+"AND BranchID = "+BA.NumberToString(mostCurrent._globalvar._branchid /*int*/ )+" "+"AND BillYear = "+BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ )+" "+"AND BillMonth = "+BA.NumberToString(mostCurrent._globalvar._billmonth /*int*/ )+" "+"AND ReadBy = "+BA.NumberToString(_iuserid);
 break; }
case 2: {
 //BA.debugLineNum = 236;BA.debugLine="Starter.strCriteria = \"SELECT * FROM tblReadin";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblReadings "+"WHERE ImplosiveType = '"+"implosive-dec"+"' "+"AND BranchID = "+BA.NumberToString(mostCurrent._globalvar._branchid /*int*/ )+" "+"AND BillYear = "+BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ )+" "+"AND BillMonth = "+BA.NumberToString(mostCurrent._globalvar._billmonth /*int*/ )+" "+"AND ReadBy = "+BA.NumberToString(_iuserid);
 break; }
case 3: {
 //BA.debugLineNum = 244;BA.debugLine="Starter.strCriteria = \"SELECT * FROM tblReadin";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblReadings "+"WHERE ImplosiveType = '"+"zero"+"' "+"AND BranchID = "+BA.NumberToString(mostCurrent._globalvar._branchid /*int*/ )+" "+"AND BillYear = "+BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ )+" "+"AND BillMonth = "+BA.NumberToString(mostCurrent._globalvar._billmonth /*int*/ )+" "+"AND ReadBy = "+BA.NumberToString(_iuserid);
 break; }
case 4: {
 //BA.debugLineNum = 252;BA.debugLine="Starter.strCriteria = \"SELECT * FROM tblReadin";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblReadings "+"WHERE BillType = '"+"AB"+"' "+"AND BranchID = "+BA.NumberToString(mostCurrent._globalvar._branchid /*int*/ )+" "+"AND BillYear = "+BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ )+" "+"AND BillMonth = "+BA.NumberToString(mostCurrent._globalvar._billmonth /*int*/ )+" "+"AND ReadBy = "+BA.NumberToString(_iuserid);
 break; }
}
;
 //BA.debugLineNum = 260;BA.debugLine="rsUnusual = Starter.DBCon.ExecQuery(Starter.strC";
_rsunusual = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 261;BA.debugLine="Log(rsUnusual.RowCount)";
anywheresoftware.b4a.keywords.Common.LogImpl("417760303",BA.NumberToString(_rsunusual.getRowCount()),0);
 //BA.debugLineNum = 264;BA.debugLine="If rsUnusual.RowCount > 0 Then";
if (_rsunusual.getRowCount()>0) { 
 //BA.debugLineNum = 265;BA.debugLine="blnRetVal = True";
_blnretval = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 267;BA.debugLine="blnRetVal = False";
_blnretval = anywheresoftware.b4a.keywords.Common.False;
 };
 } 
       catch (Exception e24) {
			processBA.setLastException(e24); //BA.debugLineNum = 270;BA.debugLine="rsUnusual.Close";
_rsunusual.Close();
 //BA.debugLineNum = 271;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("417760313",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 273;BA.debugLine="rsUnusual.Close";
_rsunusual.Close();
 //BA.debugLineNum = 274;BA.debugLine="Return blnRetVal";
if (true) return _blnretval;
 //BA.debugLineNum = 275;BA.debugLine="End Sub";
return false;
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 25;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 26;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 27;BA.debugLine="End Sub";
return "";
}
public static String  _selectedreader_buttonpressed(de.amberhome.materialdialogs.MaterialDialogWrapper _mdialog,String _saction) throws Exception{
 //BA.debugLineNum = 363;BA.debugLine="Private Sub SelectedReader_ButtonPressed (mDialog";
 //BA.debugLineNum = 365;BA.debugLine="Select Case sAction";
switch (BA.switchObjectToInt(_saction,_mdialog.ACTION_POSITIVE,_mdialog.ACTION_NEGATIVE)) {
case 0: {
 //BA.debugLineNum = 367;BA.debugLine="LogColor(GlobalVar.iReaderID, Colors.Blue)";
anywheresoftware.b4a.keywords.Common.LogImpl("418087940",BA.NumberToString(mostCurrent._globalvar._ireaderid /*int*/ ),anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 368;BA.debugLine="If IsThereUnusual(iUnusualRpt, GlobalVar.iReade";
if (_isthereunusual(_iunusualrpt,mostCurrent._globalvar._ireaderid /*int*/ )==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 369;BA.debugLine="DispErrorMsg(iUnusualRpt)";
_disperrormsg(_iunusualrpt);
 //BA.debugLineNum = 370;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 372;BA.debugLine="StartActivity(ValidationRptGenerator)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._validationrptgenerator.getObject()));
 break; }
case 1: {
 break; }
}
;
 //BA.debugLineNum = 376;BA.debugLine="End Sub";
return "";
}
public static String  _selectedreader_ondismiss(de.amberhome.materialdialogs.MaterialDialogWrapper _dialog) throws Exception{
 //BA.debugLineNum = 355;BA.debugLine="Private Sub SelectedReader_OnDismiss (Dialog As Ma";
 //BA.debugLineNum = 356;BA.debugLine="Log(\"Dialog dismissed\")";
anywheresoftware.b4a.keywords.Common.LogImpl("417956865","Dialog dismissed",0);
 //BA.debugLineNum = 357;BA.debugLine="End Sub";
return "";
}
public static String  _selectedreader_singlechoiceitemselected(de.amberhome.materialdialogs.MaterialDialogWrapper _dialog,int _position,String _text) throws Exception{
 //BA.debugLineNum = 359;BA.debugLine="Private Sub SelectedReader_SingleChoiceItemSelecte";
 //BA.debugLineNum = 360;BA.debugLine="GlobalVar.iReaderID =DBaseFunctions.GetReaderID(G";
mostCurrent._globalvar._ireaderid /*int*/  = mostCurrent._dbasefunctions._getreaderid /*int*/ (mostCurrent.activityBA,mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvv2(_text));
 //BA.debugLineNum = 361;BA.debugLine="End Sub";
return "";
}
public static String  _setbuttons() throws Exception{
 //BA.debugLineNum = 183;BA.debugLine="Private Sub SetButtons";
 //BA.debugLineNum = 184;BA.debugLine="btnMissCoded.Background = CreateButtonColor(0xFF0";
mostCurrent._btnmisscoded.setBackground((android.graphics.drawable.Drawable)(_createbuttoncolor((int) (0xff0d47a1),(int) (0xff0d47a1),(int) (0xff1e88e5),(int) (0xff0d47a1)).getObject()));
 //BA.debugLineNum = 185;BA.debugLine="btnHighBill.Background = CreateButtonColor(0xFF0D";
mostCurrent._btnhighbill.setBackground((android.graphics.drawable.Drawable)(_createbuttoncolor((int) (0xff0d47a1),(int) (0xff0d47a1),(int) (0xff1e88e5),(int) (0xff0d47a1)).getObject()));
 //BA.debugLineNum = 186;BA.debugLine="btnLowBill.Background = CreateButtonColor(0xFF0D4";
mostCurrent._btnlowbill.setBackground((android.graphics.drawable.Drawable)(_createbuttoncolor((int) (0xff0d47a1),(int) (0xff0d47a1),(int) (0xff1e88e5),(int) (0xff0d47a1)).getObject()));
 //BA.debugLineNum = 187;BA.debugLine="btnZeroCons.Background = CreateButtonColor(0xFF0D";
mostCurrent._btnzerocons.setBackground((android.graphics.drawable.Drawable)(_createbuttoncolor((int) (0xff0d47a1),(int) (0xff0d47a1),(int) (0xff1e88e5),(int) (0xff0d47a1)).getObject()));
 //BA.debugLineNum = 188;BA.debugLine="btnAveBill.Background = CreateButtonColor(0xFF0D4";
mostCurrent._btnavebill.setBackground((android.graphics.drawable.Drawable)(_createbuttoncolor((int) (0xff0d47a1),(int) (0xff0d47a1),(int) (0xff1e88e5),(int) (0xff0d47a1)).getObject()));
 //BA.debugLineNum = 189;BA.debugLine="End Sub";
return "";
}
public static String  _setsnackbarbackground(de.amberhome.objects.SnackbarWrapper _psnack,int _pcolor) throws Exception{
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
 //BA.debugLineNum = 378;BA.debugLine="Sub SetSnackBarBackground(pSnack As DSSnackbar, pC";
 //BA.debugLineNum = 379;BA.debugLine="Dim v As View";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
 //BA.debugLineNum = 380;BA.debugLine="v = pSnack.View";
_v = (anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(_psnack.getView()));
 //BA.debugLineNum = 381;BA.debugLine="v.Color = pColor";
_v.setColor(_pcolor);
 //BA.debugLineNum = 382;BA.debugLine="End Sub";
return "";
}
public static String  _setsnackbartextcolor(de.amberhome.objects.SnackbarWrapper _psnack,int _pcolor) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _p = null;
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
anywheresoftware.b4a.objects.LabelWrapper _textv = null;
 //BA.debugLineNum = 384;BA.debugLine="Sub SetSnackBarTextColor(pSnack As DSSnackbar, pCo";
 //BA.debugLineNum = 385;BA.debugLine="Dim p As Panel";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 386;BA.debugLine="p = pSnack.View";
_p = (anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(_psnack.getView()));
 //BA.debugLineNum = 387;BA.debugLine="For Each v As View In p.GetAllViewsRecursive";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
{
final anywheresoftware.b4a.BA.IterableList group3 = _p.GetAllViewsRecursive();
final int groupLen3 = group3.getSize()
;int index3 = 0;
;
for (; index3 < groupLen3;index3++){
_v = (anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(group3.Get(index3)));
 //BA.debugLineNum = 388;BA.debugLine="If v Is Label Then";
if (_v.getObjectOrNull() instanceof android.widget.TextView) { 
 //BA.debugLineNum = 389;BA.debugLine="Dim textv As Label";
_textv = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 390;BA.debugLine="textv = v";
_textv = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_v.getObject()));
 //BA.debugLineNum = 391;BA.debugLine="textv.TextColor = pColor";
_textv.setTextColor(_pcolor);
 //BA.debugLineNum = 392;BA.debugLine="Exit";
if (true) break;
 };
 }
};
 //BA.debugLineNum = 395;BA.debugLine="End Sub";
return "";
}
public static String  _showreaders() throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsreaders = null;
anywheresoftware.b4a.objects.CSBuilder _cstitle = null;
String _readers = "";
String[] _readerlist = null;
int _pcount = 0;
int _i = 0;
 //BA.debugLineNum = 301;BA.debugLine="Private Sub ShowReaders";
 //BA.debugLineNum = 302;BA.debugLine="Dim rsReaders As Cursor";
_rsreaders = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 303;BA.debugLine="Dim csTitle As CSBuilder";
_cstitle = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 304;BA.debugLine="Dim Readers As String";
_readers = "";
 //BA.debugLineNum = 305;BA.debugLine="Dim ReaderList() As String";
_readerlist = new String[(int) (0)];
java.util.Arrays.fill(_readerlist,"");
 //BA.debugLineNum = 306;BA.debugLine="Dim pCount As Int";
_pcount = 0;
 //BA.debugLineNum = 308;BA.debugLine="Try";
try { //BA.debugLineNum = 309;BA.debugLine="Starter.strCriteria = \"SELECT tblReadings.ReadBy";
mostCurrent._starter._strcriteria /*String*/  = "SELECT tblReadings.ReadBy, tblUsers.EmpName AS ReaderName "+"FROM tblReadings "+"INNER JOIN tblUsers ON tblReadings.ReadBy = tblUsers.UserID "+"GROUP BY tblReadings.ReadBy, tblUsers.EmpName "+"ORDER BY tblUsers.UserID";
 //BA.debugLineNum = 315;BA.debugLine="LogColor(Starter.strCriteria, Colors.Blue)";
anywheresoftware.b4a.keywords.Common.LogImpl("417891342",mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 317;BA.debugLine="rsReaders =  Starter.DBCon.ExecQuery (Starter.st";
_rsreaders = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 318;BA.debugLine="If rsReaders.RowCount > 0 Then";
if (_rsreaders.getRowCount()>0) { 
 //BA.debugLineNum = 319;BA.debugLine="pCount = rsReaders.RowCount";
_pcount = _rsreaders.getRowCount();
 //BA.debugLineNum = 320;BA.debugLine="Dim ReaderList(pCount) As String";
_readerlist = new String[_pcount];
java.util.Arrays.fill(_readerlist,"");
 //BA.debugLineNum = 322;BA.debugLine="For i = 0 To rsReaders.RowCount - 1";
{
final int step13 = 1;
final int limit13 = (int) (_rsreaders.getRowCount()-1);
_i = (int) (0) ;
for (;_i <= limit13 ;_i = _i + step13 ) {
 //BA.debugLineNum = 323;BA.debugLine="rsReaders.Position = i";
_rsreaders.setPosition(_i);
 //BA.debugLineNum = 324;BA.debugLine="Readers = TitleCase(rsReaders.GetString(\"Reade";
_readers = _titlecase(_rsreaders.GetString("ReaderName"));
 //BA.debugLineNum = 325;BA.debugLine="ReaderList(i) = Readers";
_readerlist[_i] = _readers;
 }
};
 }else {
 //BA.debugLineNum = 328;BA.debugLine="snack.Initialize(\"\", Activity, \"No Assigned Pum";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),"No Assigned Pump(s) found!",mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 329;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 330;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 331;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 332;BA.debugLine="Return";
if (true) return "";
 };
 } 
       catch (Exception e26) {
			processBA.setLastException(e26); //BA.debugLineNum = 335;BA.debugLine="snack.Initialize(\"\", Activity, LastException,sna";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 336;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 337;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 338;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 339;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 342;BA.debugLine="csTitle.Initialize.Color(GlobalVar.PriColor).Bold";
_cstitle.Initialize().Color((int) (mostCurrent._globalvar._pricolor /*double*/ )).Bold().Size((int) (16)).Append(BA.ObjectToCharSequence(("PLEASE SELECT THE READER"))).PopAll();
 //BA.debugLineNum = 343;BA.debugLine="MatDialogBuilder.Initialize(\"SelectedReader\")";
mostCurrent._matdialogbuilder.Initialize(mostCurrent.activityBA,"SelectedReader");
 //BA.debugLineNum = 344;BA.debugLine="MatDialogBuilder.Title(csTitle)";
mostCurrent._matdialogbuilder.Title(BA.ObjectToCharSequence(_cstitle.getObject()));
 //BA.debugLineNum = 345;BA.debugLine="MatDialogBuilder.Items(ReaderList)";
mostCurrent._matdialogbuilder.Items((java.lang.CharSequence[])(_readerlist));
 //BA.debugLineNum = 346;BA.debugLine="MatDialogBuilder.PositiveText($\"SELECT\"$).Positiv";
mostCurrent._matdialogbuilder.PositiveText(BA.ObjectToCharSequence(("SELECT"))).PositiveColor((int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 347;BA.debugLine="MatDialogBuilder.NegativeText($\"CANCEL\"$).Negativ";
mostCurrent._matdialogbuilder.NegativeText(BA.ObjectToCharSequence(("CANCEL"))).NegativeColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 348;BA.debugLine="MatDialogBuilder.Cancelable(False)";
mostCurrent._matdialogbuilder.Cancelable(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 349;BA.debugLine="MatDialogBuilder.CanceledOnTouchOutside(False)";
mostCurrent._matdialogbuilder.CanceledOnTouchOutside(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 350;BA.debugLine="MatDialogBuilder.itemsCallbackSingleChoice(0)";
mostCurrent._matdialogbuilder.ItemsCallbackSingleChoice((int) (0));
 //BA.debugLineNum = 351;BA.debugLine="MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIG";
mostCurrent._matdialogbuilder.Theme(mostCurrent._matdialogbuilder.THEME_LIGHT);
 //BA.debugLineNum = 352;BA.debugLine="MatDialogBuilder.Show";
mostCurrent._matdialogbuilder.Show();
 //BA.debugLineNum = 353;BA.debugLine="End Sub";
return "";
}
public static String  _titlecase(String _s) throws Exception{
anywheresoftware.b4a.keywords.Regex.MatcherWrapper _m = null;
int _i = 0;
 //BA.debugLineNum = 397;BA.debugLine="Sub TitleCase (s As String) As String";
 //BA.debugLineNum = 398;BA.debugLine="s = s.ToLowerCase";
_s = _s.toLowerCase();
 //BA.debugLineNum = 399;BA.debugLine="Dim m As Matcher = Regex.Matcher(\"\\b(\\w)\", s)";
_m = new anywheresoftware.b4a.keywords.Regex.MatcherWrapper();
_m = anywheresoftware.b4a.keywords.Common.Regex.Matcher("\\b(\\w)",_s);
 //BA.debugLineNum = 400;BA.debugLine="Do While m.Find";
while (_m.Find()) {
 //BA.debugLineNum = 401;BA.debugLine="Dim i As Int = m.GetStart(1)";
_i = _m.GetStart((int) (1));
 //BA.debugLineNum = 402;BA.debugLine="s = s.SubString2(0, i) & s.SubString2(i, i + 1).";
_s = _s.substring((int) (0),_i)+_s.substring(_i,(int) (_i+1)).toUpperCase()+_s.substring((int) (_i+1));
 }
;
 //BA.debugLineNum = 404;BA.debugLine="Return s";
if (true) return _s;
 //BA.debugLineNum = 405;BA.debugLine="End Sub";
return "";
}
public static String  _toolbar_navigationitemclick() throws Exception{
 //BA.debugLineNum = 86;BA.debugLine="Sub ToolBar_NavigationItemClick";
 //BA.debugLineNum = 87;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 88;BA.debugLine="End Sub";
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
