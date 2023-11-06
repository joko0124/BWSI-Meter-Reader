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

public class main extends androidx.appcompat.app.AppCompatActivity implements B4AActivity{
	public static main mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "com.bwsi.MeterReader", "com.bwsi.MeterReader.main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (main).");
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
		activityBA = new BA(this, layout, processBA, "com.bwsi.MeterReader", "com.bwsi.MeterReader.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "com.bwsi.MeterReader.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (main) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (main) Resume **");
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
		return main.class;
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
            BA.LogInfo("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (main) Pause event (activity is not paused). **");
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
            main mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (main) Resume **");
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
public static anywheresoftware.b4a.objects.RuntimePermissions _permissions = null;
public adr.splashfadelibrary.splashfade _myfade = null;
public b4a.example.liquidprogress _liquidprogress1 = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public b4a.example.dateutils _dateutils = null;
public com.bwsi.MeterReader.datasyncing _datasyncing = null;
public com.bwsi.MeterReader.camera _camera = null;
public com.bwsi.MeterReader.cmrvr _cmrvr = null;
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

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
vis = vis | (datasyncing.mostCurrent != null);
vis = vis | (camera.mostCurrent != null);
vis = vis | (cmrvr.mostCurrent != null);
vis = vis | (customerbill.mostCurrent != null);
vis = vis | (customerlist.mostCurrent != null);
vis = vis | (login.mostCurrent != null);
vis = vis | (mainscreen.mostCurrent != null);
vis = vis | (meterreading.mostCurrent != null);
vis = vis | (newcam.mostCurrent != null);
vis = vis | (readingbooks.mostCurrent != null);
vis = vis | (readingsettings.mostCurrent != null);
vis = vis | (readingvalidation.mostCurrent != null);
vis = vis | (useraccountsettings.mostCurrent != null);
vis = vis | (validationrptgenerator.mostCurrent != null);
return vis;}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 35;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 37;BA.debugLine="MyScale.SetRate(0.5)";
mostCurrent._myscale._setrate /*String*/ (mostCurrent.activityBA,0.5);
 //BA.debugLineNum = 38;BA.debugLine="Activity.LoadLayout(\"Splash\")";
mostCurrent._activity.LoadLayout("Splash",mostCurrent.activityBA);
 //BA.debugLineNum = 39;BA.debugLine="MyFade.Initialize(Activity, Me, \"splash.png\", Gra";
mostCurrent._myfade._initialize(mostCurrent.activityBA,mostCurrent._activity,main.getObject(),"splash.png",anywheresoftware.b4a.keywords.Common.Gravity.FILL,(int) (1500),(int) (0),"Right",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 41;BA.debugLine="CheckPermissions";
_checkpermissions();
 //BA.debugLineNum = 42;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 44;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 45;BA.debugLine="If KeyCode = 4 Then";
if (_keycode==4) { 
 //BA.debugLineNum = 46;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 48;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 53;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 55;BA.debugLine="End Sub";
return "";
}
public static String  _activity_permissionresult(String _permission,boolean _result) throws Exception{
 //BA.debugLineNum = 75;BA.debugLine="Sub Activity_PermissionResult (Permission As Strin";
 //BA.debugLineNum = 76;BA.debugLine="If Permission = Permissions.PERMISSION_CAMERA The";
if ((_permission).equals(_permissions.PERMISSION_CAMERA)) { 
 //BA.debugLineNum = 77;BA.debugLine="Result = True";
_result = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 78;BA.debugLine="LogColor(\"YOU CAN USE THE CAMERA\",Colors.Red)";
anywheresoftware.b4a.keywords.Common.LogImpl("4458755","YOU CAN USE THE CAMERA",anywheresoftware.b4a.keywords.Common.Colors.Red);
 }else {
 //BA.debugLineNum = 80;BA.debugLine="Result = False";
_result = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 82;BA.debugLine="Log (Permission)";
anywheresoftware.b4a.keywords.Common.LogImpl("4458759",_permission,0);
 //BA.debugLineNum = 83;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 50;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 51;BA.debugLine="End Sub";
return "";
}
public static String  _checkpermissions() throws Exception{
 //BA.debugLineNum = 57;BA.debugLine="Private Sub CheckPermissions";
 //BA.debugLineNum = 62;BA.debugLine="Log(\"Checking Permissions\")";
anywheresoftware.b4a.keywords.Common.LogImpl("4393221","Checking Permissions",0);
 //BA.debugLineNum = 64;BA.debugLine="Permissions.CheckAndRequest(Permissions.PERMISSIO";
_permissions.CheckAndRequest(processBA,_permissions.PERMISSION_READ_EXTERNAL_STORAGE);
 //BA.debugLineNum = 65;BA.debugLine="Permissions.CheckAndRequest(Permissions.PERMISSIO";
_permissions.CheckAndRequest(processBA,_permissions.PERMISSION_WRITE_EXTERNAL_STORAGE);
 //BA.debugLineNum = 66;BA.debugLine="Permissions.CheckAndRequest(Permissions.PERMISSIO";
_permissions.CheckAndRequest(processBA,_permissions.PERMISSION_ACCESS_COARSE_LOCATION);
 //BA.debugLineNum = 67;BA.debugLine="Permissions.GetAllSafeDirsExternal(\"\")";
_permissions.GetAllSafeDirsExternal("");
 //BA.debugLineNum = 69;BA.debugLine="Permissions.CheckAndRequest(Permissions.PERMISSIO";
_permissions.CheckAndRequest(processBA,_permissions.PERMISSION_CAMERA);
 //BA.debugLineNum = 70;BA.debugLine="Permissions.CheckAndRequest(Permissions.PERMISSIO";
_permissions.CheckAndRequest(processBA,_permissions.PERMISSION_BODY_SENSORS);
 //BA.debugLineNum = 71;BA.debugLine="Return";
if (true) return "";
 //BA.debugLineNum = 73;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 28;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 31;BA.debugLine="Dim MyFade As SplashFade";
mostCurrent._myfade = new adr.splashfadelibrary.splashfade();
 //BA.debugLineNum = 32;BA.debugLine="Private LiquidProgress1 As LiquidProgress";
mostCurrent._liquidprogress1 = new b4a.example.liquidprogress();
 //BA.debugLineNum = 33;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        anywheresoftware.b4a.samples.httputils2.httputils2service._process_globals();
b4a.example.dateutils._process_globals();
main._process_globals();
datasyncing._process_globals();
camera._process_globals();
cmrvr._process_globals();
customerbill._process_globals();
customerlist._process_globals();
customfunctions._process_globals();
dbasefunctions._process_globals();
dbutils._process_globals();
globalvar._process_globals();
login._process_globals();
mainscreen._process_globals();
meterreading._process_globals();
modvariables._process_globals();
myscale._process_globals();
newcam._process_globals();
readingbooks._process_globals();
readingsettings._process_globals();
readingvalidation._process_globals();
starter._process_globals();
useraccountsettings._process_globals();
validationrptgenerator._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 24;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 25;BA.debugLine="Public Permissions As RuntimePermissions";
_permissions = new anywheresoftware.b4a.objects.RuntimePermissions();
 //BA.debugLineNum = 26;BA.debugLine="End Sub";
return "";
}
public static String  _splash_complete() throws Exception{
 //BA.debugLineNum = 85;BA.debugLine="Sub Splash_Complete";
 //BA.debugLineNum = 86;BA.debugLine="If CustomFunctions.IsThereUserData = True Then";
if (mostCurrent._customfunctions._isthereuserdata /*boolean*/ (mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 87;BA.debugLine="CustomFunctions.RetrieveUserSettings";
mostCurrent._customfunctions._retrieveusersettings /*String*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 88;BA.debugLine="DBaseFunctions.GetUserInfo(GlobalVar.UserName, G";
mostCurrent._dbasefunctions._getuserinfo /*String*/ (mostCurrent.activityBA,mostCurrent._globalvar._username /*String*/ ,mostCurrent._globalvar._userpw /*String*/ );
 //BA.debugLineNum = 89;BA.debugLine="DBaseFunctions.GetBranchInfo(GlobalVar.BranchID)";
mostCurrent._dbasefunctions._getbranchinfo /*String*/ (mostCurrent.activityBA,mostCurrent._globalvar._branchid /*int*/ );
 //BA.debugLineNum = 90;BA.debugLine="If DBaseFunctions.GetBillSettings(GlobalVar.Bran";
if (mostCurrent._dbasefunctions._getbillsettings /*boolean*/ (mostCurrent.activityBA,mostCurrent._globalvar._branchid /*int*/ )==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 91;BA.debugLine="ExitApplication";
anywheresoftware.b4a.keywords.Common.ExitApplication();
 };
 //BA.debugLineNum = 93;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 94;BA.debugLine="StartActivity(MainScreen)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._mainscreen.getObject()));
 }else {
 //BA.debugLineNum = 96;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 97;BA.debugLine="StartActivity(Login)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._login.getObject()));
 };
 //BA.debugLineNum = 99;BA.debugLine="End Sub";
return "";
}
}
