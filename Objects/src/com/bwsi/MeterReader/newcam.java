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

public class newcam extends androidx.appcompat.app.AppCompatActivity implements B4AActivity{
	public static newcam mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "com.bwsi.MeterReader", "com.bwsi.MeterReader.newcam");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (newcam).");
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
		activityBA = new BA(this, layout, processBA, "com.bwsi.MeterReader", "com.bwsi.MeterReader.newcam");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "com.bwsi.MeterReader.newcam", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (newcam) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (newcam) Resume **");
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
		return newcam.class;
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
            BA.LogInfo("** Activity (newcam) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (newcam) Pause event (activity is not paused). **");
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
            newcam mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (newcam) Resume **");
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
public static boolean _frontcamera = false;
public de.amberhome.objects.appcompat.ACToolbarDarkWrapper _toolbar = null;
public de.amberhome.objects.appcompat.ACActionBar _actionbarbutton = null;
public de.amberhome.materialdialogs.MaterialDialogBuilderWrapper _matdialogbuilder = null;
public static String _filename = "";
public static String _dir = "";
public static String _pictfoldername = "";
public static String _picturepath = "";
public com.bwsi.MeterReader.cameraexclass _camex = null;
public static int _ibillmonth = 0;
public static String _sbillmonth = "";
public static String _sbillyear = "";
public de.amberhome.objects.SnackbarWrapper _snack = null;
public anywheresoftware.b4a.objects.PanelWrapper _btncapture = null;
public anywheresoftware.b4a.objects.PanelWrapper _btnflash = null;
public anywheresoftware.b4a.objects.PanelWrapper _btnswitch = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblswitch = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlmain = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlswitch = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlcapture = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcapture = null;
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
public com.bwsi.MeterReader.customerlist _customerlist = null;
public com.bwsi.MeterReader.dbutils _dbutils = null;
public com.bwsi.MeterReader.globalvar _globalvar = null;
public com.bwsi.MeterReader.modvariables _modvariables = null;
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
 //BA.debugLineNum = 41;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 42;BA.debugLine="Activity.LoadLayout(\"CameraLayout\")";
mostCurrent._activity.LoadLayout("CameraLayout",mostCurrent.activityBA);
 //BA.debugLineNum = 44;BA.debugLine="GlobalVar.CSTitle.Initialize.Size(17).Bold.Append";
mostCurrent._globalvar._cstitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (17)).Bold().Append(BA.ObjectToCharSequence(("Acct. No ")+mostCurrent._globalvar._camacctno /*String*/ )).PopAll();
 //BA.debugLineNum = 45;BA.debugLine="GlobalVar.CSSubTitle.Initialize.Size(14).Append($";
mostCurrent._globalvar._cssubtitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (14)).Append(BA.ObjectToCharSequence(("Meter No.: ")+mostCurrent._globalvar._cammeterno /*String*/ )).PopAll();
 //BA.debugLineNum = 47;BA.debugLine="ToolBar.InitMenuListener";
mostCurrent._toolbar.InitMenuListener();
 //BA.debugLineNum = 48;BA.debugLine="ToolBar.Title = GlobalVar.CSTitle";
mostCurrent._toolbar.setTitle(BA.ObjectToCharSequence(mostCurrent._globalvar._cstitle /*anywheresoftware.b4a.objects.CSBuilder*/ .getObject()));
 //BA.debugLineNum = 49;BA.debugLine="ToolBar.SubTitle = GlobalVar.CSSubTitle";
mostCurrent._toolbar.setSubTitle(BA.ObjectToCharSequence(mostCurrent._globalvar._cssubtitle /*anywheresoftware.b4a.objects.CSBuilder*/ .getObject()));
 //BA.debugLineNum = 50;BA.debugLine="ToolBar.SetElevationAnimated(5,5dip)";
mostCurrent._toolbar.SetElevationAnimated((int) (5),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 52;BA.debugLine="Dim jo As JavaObject";
_jo = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 53;BA.debugLine="Dim xl As XmlLayoutBuilder";
_xl = new anywheresoftware.b4a.object.XmlLayoutBuilder();
 //BA.debugLineNum = 54;BA.debugLine="jo = ToolBar";
_jo = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(mostCurrent._toolbar.getObject()));
 //BA.debugLineNum = 55;BA.debugLine="jo.RunMethod(\"setPopupTheme\", Array(xl.GetResourc";
_jo.RunMethod("setPopupTheme",new Object[]{(Object)(_xl.GetResourceId("style","ToolbarMenu"))});
 //BA.debugLineNum = 56;BA.debugLine="jo.RunMethod(\"setContentInsetStartWithNavigation\"";
_jo.RunMethod("setContentInsetStartWithNavigation",new Object[]{(Object)(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)))});
 //BA.debugLineNum = 57;BA.debugLine="jo.RunMethod(\"setTitleMarginStart\", Array(0dip))";
_jo.RunMethod("setTitleMarginStart",new Object[]{(Object)(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)))});
 //BA.debugLineNum = 59;BA.debugLine="ActionBarButton.Initialize";
mostCurrent._actionbarbutton.Initialize(mostCurrent.activityBA);
 //BA.debugLineNum = 60;BA.debugLine="ActionBarButton.ShowUpIndicator = True";
mostCurrent._actionbarbutton.setShowUpIndicator(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 62;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 75;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 76;BA.debugLine="If KeyCode = 4 Then";
if (_keycode==4) { 
 //BA.debugLineNum = 77;BA.debugLine="ToolBar_NavigationItemClick";
_toolbar_navigationitemclick();
 //BA.debugLineNum = 78;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 80;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 82;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 89;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 90;BA.debugLine="camEx.Release";
mostCurrent._camex._release /*String*/ ();
 //BA.debugLineNum = 91;BA.debugLine="End Sub";
return "";
}
public static String  _activity_permissionresult(String _permission,boolean _result) throws Exception{
 //BA.debugLineNum = 93;BA.debugLine="Sub Activity_PermissionResult (Permission As Strin";
 //BA.debugLineNum = 94;BA.debugLine="If Permission = Starter.rp.PERMISSION_WRITE_EXTER";
if ((_permission).equals(mostCurrent._starter._rp /*anywheresoftware.b4a.objects.RuntimePermissions*/ .PERMISSION_WRITE_EXTERNAL_STORAGE)) { 
 //BA.debugLineNum = 95;BA.debugLine="Result = True";
_result = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 96;BA.debugLine="LogColor(\"YOU CAN Save to External Drive\", Color";
anywheresoftware.b4a.keywords.Common.LogImpl("150724867","YOU CAN Save to External Drive",anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 }else {
 //BA.debugLineNum = 98;BA.debugLine="Result = False";
_result = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 100;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 64;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 65;BA.debugLine="InitializeCamera";
_initializecamera();
 //BA.debugLineNum = 66;BA.debugLine="iBillMonth = DBaseFunctions.GetBillMonth(GlobalVa";
_ibillmonth = mostCurrent._dbasefunctions._getbillmonth /*int*/ (mostCurrent.activityBA,mostCurrent._globalvar._branchid /*int*/ );
 //BA.debugLineNum = 67;BA.debugLine="sBillYear = DBaseFunctions.GetBillYear(GlobalVar.";
mostCurrent._sbillyear = BA.NumberToString(mostCurrent._dbasefunctions._getbillyear /*double*/ (mostCurrent.activityBA,mostCurrent._globalvar._branchid /*int*/ ));
 //BA.debugLineNum = 68;BA.debugLine="If GlobalVar.SF.Len(iBillMonth) = 1 Then";
if (mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(BA.NumberToString(_ibillmonth))==1) { 
 //BA.debugLineNum = 69;BA.debugLine="sBillMonth = \"0\" & iBillMonth";
mostCurrent._sbillmonth = "0"+BA.NumberToString(_ibillmonth);
 }else {
 //BA.debugLineNum = 71;BA.debugLine="sBillMonth = iBillMonth";
mostCurrent._sbillmonth = BA.NumberToString(_ibillmonth);
 };
 //BA.debugLineNum = 73;BA.debugLine="End Sub";
return "";
}
public static String  _btncapture_click() throws Exception{
 //BA.debugLineNum = 246;BA.debugLine="Sub btnCapture_Click";
 //BA.debugLineNum = 247;BA.debugLine="camEx.TakePicture";
mostCurrent._camex._takepicture /*String*/ ();
 //BA.debugLineNum = 248;BA.debugLine="pnlCapture.Enabled = False";
mostCurrent._pnlcapture.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 249;BA.debugLine="btnCapture.Enabled = False";
mostCurrent._btncapture.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 252;BA.debugLine="btnFlash.Enabled = False";
mostCurrent._btnflash.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 253;BA.debugLine="btnSwitch.Enabled = False";
mostCurrent._btnswitch.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 254;BA.debugLine="End Sub";
return "";
}
public static String  _btnflash_click() throws Exception{
anywheresoftware.b4a.objects.collections.List _flashmodes = null;
String _flash = "";
 //BA.debugLineNum = 233;BA.debugLine="Sub btnFlash_Click";
 //BA.debugLineNum = 234;BA.debugLine="Dim flashModes As List = camEx.GetSupportedFlashM";
_flashmodes = new anywheresoftware.b4a.objects.collections.List();
_flashmodes = mostCurrent._camex._getsupportedflashmodes /*anywheresoftware.b4a.objects.collections.List*/ ();
 //BA.debugLineNum = 236;BA.debugLine="If flashModes.IsInitialized = False Then";
if (_flashmodes.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 237;BA.debugLine="ToastMessageShow(\"Flash not supported.\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Flash not supported."),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 238;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 240;BA.debugLine="Dim flash As String = flashModes.Get((flashModes.";
_flash = BA.ObjectToString(_flashmodes.Get((int) ((_flashmodes.IndexOf((Object)(mostCurrent._camex._getflashmode /*String*/ ()))+1)%_flashmodes.getSize())));
 //BA.debugLineNum = 241;BA.debugLine="camEx.SetFlashMode(flash)";
mostCurrent._camex._setflashmode /*String*/ (_flash);
 //BA.debugLineNum = 242;BA.debugLine="ToastMessageShow(flash, False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(_flash),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 243;BA.debugLine="camEx.CommitParameters";
mostCurrent._camex._commitparameters /*String*/ ();
 //BA.debugLineNum = 244;BA.debugLine="End Sub";
return "";
}
public static String  _btnswitch_click() throws Exception{
 //BA.debugLineNum = 256;BA.debugLine="Sub btnSwitch_Click";
 //BA.debugLineNum = 257;BA.debugLine="camEx.Release";
mostCurrent._camex._release /*String*/ ();
 //BA.debugLineNum = 258;BA.debugLine="frontCamera = Not(frontCamera)";
_frontcamera = anywheresoftware.b4a.keywords.Common.Not(_frontcamera);
 //BA.debugLineNum = 259;BA.debugLine="InitializeCamera";
_initializecamera();
 //BA.debugLineNum = 260;BA.debugLine="End Sub";
return "";
}
public static void  _camera1_picturetaken(byte[] _data) throws Exception{
ResumableSub_Camera1_PictureTaken rsub = new ResumableSub_Camera1_PictureTaken(null,_data);
rsub.resume(processBA, null);
}
public static class ResumableSub_Camera1_PictureTaken extends BA.ResumableSub {
public ResumableSub_Camera1_PictureTaken(com.bwsi.MeterReader.newcam parent,byte[] _data) {
this.parent = parent;
this._data = _data;
}
com.bwsi.MeterReader.newcam parent;
byte[] _data;
String _permission = "";
boolean _result = false;
anywheresoftware.b4a.phone.Phone _phone = null;
anywheresoftware.b4a.objects.IntentWrapper _i = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 113;BA.debugLine="Starter.rp.CheckAndRequest(Starter.rp.PERMISSION_";
parent.mostCurrent._starter._rp /*anywheresoftware.b4a.objects.RuntimePermissions*/ .CheckAndRequest(processBA,parent.mostCurrent._starter._rp /*anywheresoftware.b4a.objects.RuntimePermissions*/ .PERMISSION_WRITE_EXTERNAL_STORAGE);
 //BA.debugLineNum = 114;BA.debugLine="Wait For Activity_PermissionResult (Permission As";
anywheresoftware.b4a.keywords.Common.WaitFor("activity_permissionresult", processBA, this, null);
this.state = 13;
return;
case 13:
//C
this.state = 1;
_permission = (String) result[0];
_result = (Boolean) result[1];
;
 //BA.debugLineNum = 116;BA.debugLine="If Result = False Then";
if (true) break;

case 1:
//if
this.state = 12;
if (_result==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 12;
 //BA.debugLineNum = 117;BA.debugLine="ToastMessageShow ($\"Unable to backup due to perm";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to backup due to permission to write was denied")),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 127;BA.debugLine="If File.Exists(File.Combine(File.DirRootExternal";
if (true) break;

case 6:
//if
this.state = 11;
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.Combine(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"DCIM"),"MeterReading")==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 8;
;}if (true) break;

case 8:
//C
this.state = 11;
anywheresoftware.b4a.keywords.Common.File.MakeDir(anywheresoftware.b4a.keywords.Common.File.Combine(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"DCIM"),"MeterReading");
if (true) break;

case 11:
//C
this.state = 12;
;
 //BA.debugLineNum = 128;BA.debugLine="PicturePath = File.Combine(File.DirRootExternal,";
parent.mostCurrent._picturepath = anywheresoftware.b4a.keywords.Common.File.Combine(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"DCIM/MeterReading/");
 //BA.debugLineNum = 129;BA.debugLine="LogColor(PicturePath, Colors.Yellow)";
anywheresoftware.b4a.keywords.Common.LogImpl("150855953",parent.mostCurrent._picturepath,anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 //BA.debugLineNum = 130;BA.debugLine="filename = sBillYear & \"-\" & sBillMonth & \"-\" &";
parent.mostCurrent._filename = parent.mostCurrent._sbillyear+"-"+parent.mostCurrent._sbillmonth+"-"+parent.mostCurrent._globalvar._camacctno /*String*/ +".jpg";
 //BA.debugLineNum = 131;BA.debugLine="camEx.SavePictureToFile(Data,PicturePath, filena";
parent.mostCurrent._camex._savepicturetofile /*String*/ (_data,parent.mostCurrent._picturepath,parent.mostCurrent._filename);
 //BA.debugLineNum = 132;BA.debugLine="camEx.StartPreview 'restart preview";
parent.mostCurrent._camex._startpreview /*String*/ ();
 if (true) break;

case 12:
//C
this.state = -1;
;
 //BA.debugLineNum = 149;BA.debugLine="Dim Phone As Phone";
_phone = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 150;BA.debugLine="Dim i As Intent";
_i = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 151;BA.debugLine="i.Initialize(\"android.intent.action.MEDIA_SCANNER";
_i.Initialize("android.intent.action.MEDIA_SCANNER_SCAN_FILE","file://"+anywheresoftware.b4a.keywords.Common.File.Combine(parent.mostCurrent._pictfoldername,parent.mostCurrent._filename));
 //BA.debugLineNum = 153;BA.debugLine="Phone.SendBroadcastIntent(i)";
_phone.SendBroadcastIntent((android.content.Intent)(_i.getObject()));
 //BA.debugLineNum = 154;BA.debugLine="PictureTaken";
_picturetaken();
 //BA.debugLineNum = 155;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _camera1_ready(boolean _success) throws Exception{
 //BA.debugLineNum = 102;BA.debugLine="Sub Camera1_Ready (Success As Boolean)";
 //BA.debugLineNum = 103;BA.debugLine="If Success Then";
if (_success) { 
 //BA.debugLineNum = 104;BA.debugLine="camEx.StartPreview";
mostCurrent._camex._startpreview /*String*/ ();
 //BA.debugLineNum = 105;BA.debugLine="camEx.SetJpegQuality(90)";
mostCurrent._camex._setjpegquality /*String*/ ((int) (90));
 //BA.debugLineNum = 106;BA.debugLine="camEx.CommitParameters";
mostCurrent._camex._commitparameters /*String*/ ();
 }else {
 //BA.debugLineNum = 108;BA.debugLine="ToastMessageShow(\"Cannot open camera.\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Cannot open camera."),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 110;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 11;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 12;BA.debugLine="Private ToolBar As ACToolBarDark";
mostCurrent._toolbar = new de.amberhome.objects.appcompat.ACToolbarDarkWrapper();
 //BA.debugLineNum = 13;BA.debugLine="Private ActionBarButton As ACActionBar";
mostCurrent._actionbarbutton = new de.amberhome.objects.appcompat.ACActionBar();
 //BA.debugLineNum = 16;BA.debugLine="Private MatDialogBuilder As MaterialDialogBuilder";
mostCurrent._matdialogbuilder = new de.amberhome.materialdialogs.MaterialDialogBuilderWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Dim filename As String";
mostCurrent._filename = "";
 //BA.debugLineNum = 19;BA.debugLine="Dim dir As String = File.DirRootExternal";
mostCurrent._dir = anywheresoftware.b4a.keywords.Common.File.getDirRootExternal();
 //BA.debugLineNum = 21;BA.debugLine="Dim PictFolderName As String";
mostCurrent._pictfoldername = "";
 //BA.debugLineNum = 22;BA.debugLine="Dim PicturePath As String";
mostCurrent._picturepath = "";
 //BA.debugLineNum = 24;BA.debugLine="Private camEx As CameraExClass";
mostCurrent._camex = new com.bwsi.MeterReader.cameraexclass();
 //BA.debugLineNum = 26;BA.debugLine="Private iBillMonth As Int";
_ibillmonth = 0;
 //BA.debugLineNum = 27;BA.debugLine="Private sBillMonth As String";
mostCurrent._sbillmonth = "";
 //BA.debugLineNum = 28;BA.debugLine="Private sBillYear As String";
mostCurrent._sbillyear = "";
 //BA.debugLineNum = 29;BA.debugLine="Private snack As DSSnackbar";
mostCurrent._snack = new de.amberhome.objects.SnackbarWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private btnCapture As Panel";
mostCurrent._btncapture = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private btnFlash As Panel";
mostCurrent._btnflash = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private btnSwitch As Panel";
mostCurrent._btnswitch = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private lblSwitch As Label";
mostCurrent._lblswitch = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private pnlMain As Panel";
mostCurrent._pnlmain = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private pnlSwitch As Panel";
mostCurrent._pnlswitch = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private pnlCapture As Panel";
mostCurrent._pnlcapture = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private lblCapture As Label";
mostCurrent._lblcapture = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 39;BA.debugLine="End Sub";
return "";
}
public static String  _initializecamera() throws Exception{
 //BA.debugLineNum = 84;BA.debugLine="Private Sub InitializeCamera";
 //BA.debugLineNum = 85;BA.debugLine="camEx.Initialize(pnlMain, frontCamera, Me, \"Camer";
mostCurrent._camex._initialize /*String*/ (mostCurrent.activityBA,mostCurrent._pnlmain,_frontcamera,newcam.getObject(),"Camera1");
 //BA.debugLineNum = 86;BA.debugLine="frontCamera = camEx.Front";
_frontcamera = mostCurrent._camex._front /*boolean*/ ;
 //BA.debugLineNum = 87;BA.debugLine="End Sub";
return "";
}
public static String  _picturetaken() throws Exception{
anywheresoftware.b4a.objects.CSBuilder _cstitle = null;
anywheresoftware.b4a.objects.CSBuilder _cscontent = null;
 //BA.debugLineNum = 183;BA.debugLine="Private Sub PictureTaken";
 //BA.debugLineNum = 184;BA.debugLine="Dim csTitle As CSBuilder";
_cstitle = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 185;BA.debugLine="Dim csContent As CSBuilder";
_cscontent = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 187;BA.debugLine="csTitle.Initialize.Typeface(Typeface.FONTAWESOME)";
_cstitle.Initialize().Typeface(anywheresoftware.b4a.keywords.Common.Typeface.getFONTAWESOME()).Color((int) (mostCurrent._globalvar._pricolor /*double*/ )).Size((int) (16)).Bold().Append(BA.ObjectToCharSequence(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xf129)))+(" Picture Taken"))).PopAll();
 //BA.debugLineNum = 188;BA.debugLine="csContent.Initialize.Color(Colors.DarkGray).Appen";
_cscontent.Initialize().Color(anywheresoftware.b4a.keywords.Common.Colors.DarkGray).Append(BA.ObjectToCharSequence(("Picture has been successfully taken."))).PopAll();
 //BA.debugLineNum = 190;BA.debugLine="MatDialogBuilder.Initialize(\"TakeAnother\")";
mostCurrent._matdialogbuilder.Initialize(mostCurrent.activityBA,"TakeAnother");
 //BA.debugLineNum = 191;BA.debugLine="MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIG";
mostCurrent._matdialogbuilder.Theme(mostCurrent._matdialogbuilder.THEME_LIGHT);
 //BA.debugLineNum = 192;BA.debugLine="MatDialogBuilder.Title(csTitle)";
mostCurrent._matdialogbuilder.Title(BA.ObjectToCharSequence(_cstitle.getObject()));
 //BA.debugLineNum = 194;BA.debugLine="MatDialogBuilder.Content(csContent)";
mostCurrent._matdialogbuilder.Content(BA.ObjectToCharSequence(_cscontent.getObject()));
 //BA.debugLineNum = 195;BA.debugLine="MatDialogBuilder.PositiveText($\"OK\"$).PositiveCol";
mostCurrent._matdialogbuilder.PositiveText(BA.ObjectToCharSequence(("OK"))).PositiveColor((int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 196;BA.debugLine="MatDialogBuilder.NegativeText($\"TRY AGAIN\"$).Nega";
mostCurrent._matdialogbuilder.NegativeText(BA.ObjectToCharSequence(("TRY AGAIN"))).NegativeColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 197;BA.debugLine="MatDialogBuilder.NeutralText($\"OPEN\"$).NeutralCol";
mostCurrent._matdialogbuilder.NeutralText(BA.ObjectToCharSequence(("OPEN"))).NeutralColor((int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 198;BA.debugLine="MatDialogBuilder.CanceledOnTouchOutside(False)";
mostCurrent._matdialogbuilder.CanceledOnTouchOutside(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 199;BA.debugLine="MatDialogBuilder.Show";
mostCurrent._matdialogbuilder.Show();
 //BA.debugLineNum = 200;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 7;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="Private frontCamera As Boolean = False";
_frontcamera = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 9;BA.debugLine="End Sub";
return "";
}
public static String  _setsnackbarbackground(de.amberhome.objects.SnackbarWrapper _psnack,int _pcolor) throws Exception{
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
 //BA.debugLineNum = 158;BA.debugLine="Sub SetSnackBarBackground(pSnack As DSSnackbar, pC";
 //BA.debugLineNum = 159;BA.debugLine="Dim v As View";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
 //BA.debugLineNum = 160;BA.debugLine="v = pSnack.View";
_v = (anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(_psnack.getView()));
 //BA.debugLineNum = 161;BA.debugLine="v.Color = pColor";
_v.setColor(_pcolor);
 //BA.debugLineNum = 162;BA.debugLine="End Sub";
return "";
}
public static String  _setsnackbartextcolor(de.amberhome.objects.SnackbarWrapper _psnack,int _pcolor) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _p = null;
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
anywheresoftware.b4a.objects.LabelWrapper _textv = null;
 //BA.debugLineNum = 164;BA.debugLine="Sub SetSnackBarTextColor(pSnack As DSSnackbar, pCo";
 //BA.debugLineNum = 165;BA.debugLine="Dim p As Panel";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 166;BA.debugLine="p = pSnack.View";
_p = (anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(_psnack.getView()));
 //BA.debugLineNum = 167;BA.debugLine="For Each v As View In p.GetAllViewsRecursive";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
{
final anywheresoftware.b4a.BA.IterableList group3 = _p.GetAllViewsRecursive();
final int groupLen3 = group3.getSize()
;int index3 = 0;
;
for (; index3 < groupLen3;index3++){
_v = (anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(group3.Get(index3)));
 //BA.debugLineNum = 168;BA.debugLine="If v Is Label Then";
if (_v.getObjectOrNull() instanceof android.widget.TextView) { 
 //BA.debugLineNum = 169;BA.debugLine="Dim textv As Label";
_textv = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 170;BA.debugLine="textv = v";
_textv = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_v.getObject()));
 //BA.debugLineNum = 171;BA.debugLine="textv.TextColor = pColor";
_textv.setTextColor(_pcolor);
 //BA.debugLineNum = 172;BA.debugLine="Exit";
if (true) break;
 };
 }
};
 //BA.debugLineNum = 175;BA.debugLine="End Sub";
return "";
}
public static String  _takeanother_buttonpressed(de.amberhome.materialdialogs.MaterialDialogWrapper _mdialog,String _saction) throws Exception{
anywheresoftware.b4a.objects.IntentWrapper _i = null;
 //BA.debugLineNum = 202;BA.debugLine="Sub TakeAnother_ButtonPressed (mDialog As Material";
 //BA.debugLineNum = 203;BA.debugLine="Select Case sAction";
switch (BA.switchObjectToInt(_saction,_mdialog.ACTION_POSITIVE,_mdialog.ACTION_NEGATIVE,_mdialog.ACTION_NEUTRAL)) {
case 0: {
 //BA.debugLineNum = 205;BA.debugLine="If GlobalVar.isAverage = 1 Then";
if (mostCurrent._globalvar._isaverage /*int*/ ==1) { 
 //BA.debugLineNum = 206;BA.debugLine="GlobalVar.blnAverage = True";
mostCurrent._globalvar._blnaverage /*boolean*/  = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 208;BA.debugLine="GlobalVar.blnAverage = False";
mostCurrent._globalvar._blnaverage /*boolean*/  = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 210;BA.debugLine="snack.Initialize(\"\",Activity,$\"Picture saved. F";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Picture saved. Filename : ")+mostCurrent._filename,mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 211;BA.debugLine="SetSnackBarBackground(snack, Colors.White)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 212;BA.debugLine="SetSnackBarTextColor(snack, GlobalVar.PriColor)";
_setsnackbartextcolor(mostCurrent._snack,(int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 213;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 215;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 break; }
case 1: {
 //BA.debugLineNum = 217;BA.debugLine="pnlCapture.Enabled = True";
mostCurrent._pnlcapture.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 218;BA.debugLine="btnCapture.Enabled = True";
mostCurrent._btncapture.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 219;BA.debugLine="lblCapture.Enabled = True";
mostCurrent._lblcapture.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 221;BA.debugLine="Return";
if (true) return "";
 break; }
case 2: {
 //BA.debugLineNum = 223;BA.debugLine="Dim i As Intent";
_i = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 224;BA.debugLine="i.Initialize(i.ACTION_VIEW, File.Combine(PictFo";
_i.Initialize(_i.ACTION_VIEW,anywheresoftware.b4a.keywords.Common.File.Combine(mostCurrent._pictfoldername,mostCurrent._filename));
 //BA.debugLineNum = 225;BA.debugLine="i.SetType(\"image/*\")";
_i.SetType("image/*");
 //BA.debugLineNum = 226;BA.debugLine="StartActivity(i)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(_i.getObject()));
 //BA.debugLineNum = 227;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 break; }
}
;
 //BA.debugLineNum = 229;BA.debugLine="End Sub";
return "";
}
public static String  _toolbar_navigationitemclick() throws Exception{
 //BA.debugLineNum = 179;BA.debugLine="Sub ToolBar_NavigationItemClick";
 //BA.debugLineNum = 180;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 181;BA.debugLine="End Sub";
return "";
}
}
