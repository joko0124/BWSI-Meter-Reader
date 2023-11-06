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

public class camera extends androidx.appcompat.app.AppCompatActivity implements B4AActivity{
	public static camera mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "com.bwsi.MeterReader", "com.bwsi.MeterReader.camera");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (camera).");
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
		activityBA = new BA(this, layout, processBA, "com.bwsi.MeterReader", "com.bwsi.MeterReader.camera");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "com.bwsi.MeterReader.camera", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (camera) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (camera) Resume **");
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
		return camera.class;
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
            BA.LogInfo("** Activity (camera) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (camera) Pause event (activity is not paused). **");
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
            camera mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (camera) Resume **");
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
public anywheresoftware.b4a.objects.PanelWrapper _panel1 = null;
public com.bwsi.MeterReader.cameraexclass _camex = null;
public static int _ibillmonth = 0;
public static String _sbillmonth = "";
public static String _sbillyear = "";
public de.amberhome.objects.SnackbarWrapper _snack = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public b4a.example.dateutils _dateutils = null;
public com.bwsi.MeterReader.main _main = null;
public com.bwsi.MeterReader.datasyncing _datasyncing = null;
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

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 20;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 21;BA.debugLine="Activity.LoadLayout(\"Camera\")";
mostCurrent._activity.LoadLayout("Camera",mostCurrent.activityBA);
 //BA.debugLineNum = 22;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 40;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 41;BA.debugLine="camEx.Release";
mostCurrent._camex._release /*String*/ ();
 //BA.debugLineNum = 42;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 24;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 25;BA.debugLine="InitializeCamera";
_initializecamera();
 //BA.debugLineNum = 26;BA.debugLine="iBillMonth = DBaseFunctions.GetBillMonth(GlobalVa";
_ibillmonth = mostCurrent._dbasefunctions._getbillmonth /*int*/ (mostCurrent.activityBA,mostCurrent._globalvar._branchid /*int*/ );
 //BA.debugLineNum = 27;BA.debugLine="sBillYear = DBaseFunctions.GetBillYear(GlobalVar.";
mostCurrent._sbillyear = BA.NumberToString(mostCurrent._dbasefunctions._getbillyear /*double*/ (mostCurrent.activityBA,mostCurrent._globalvar._branchid /*int*/ ));
 //BA.debugLineNum = 28;BA.debugLine="If GlobalVar.SF.Len(iBillMonth) = 1 Then";
if (mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(BA.NumberToString(_ibillmonth))==1) { 
 //BA.debugLineNum = 29;BA.debugLine="sBillMonth = \"0\" & iBillMonth";
mostCurrent._sbillmonth = "0"+BA.NumberToString(_ibillmonth);
 }else {
 //BA.debugLineNum = 31;BA.debugLine="sBillMonth = iBillMonth";
mostCurrent._sbillmonth = BA.NumberToString(_ibillmonth);
 };
 //BA.debugLineNum = 33;BA.debugLine="End Sub";
return "";
}
public static String  _btnchangecam_click() throws Exception{
 //BA.debugLineNum = 78;BA.debugLine="Sub btnChangeCam_Click";
 //BA.debugLineNum = 79;BA.debugLine="camEx.Release";
mostCurrent._camex._release /*String*/ ();
 //BA.debugLineNum = 80;BA.debugLine="frontCamera = Not(frontCamera)";
_frontcamera = anywheresoftware.b4a.keywords.Common.Not(_frontcamera);
 //BA.debugLineNum = 81;BA.debugLine="InitializeCamera";
_initializecamera();
 //BA.debugLineNum = 82;BA.debugLine="End Sub";
return "";
}
public static String  _btnflash_click() throws Exception{
anywheresoftware.b4a.objects.collections.List _flashmodes = null;
String _flash = "";
 //BA.debugLineNum = 84;BA.debugLine="Sub btnFlash_Click";
 //BA.debugLineNum = 85;BA.debugLine="Dim flashModes As List = camEx.GetSupportedFlashM";
_flashmodes = new anywheresoftware.b4a.objects.collections.List();
_flashmodes = mostCurrent._camex._getsupportedflashmodes /*anywheresoftware.b4a.objects.collections.List*/ ();
 //BA.debugLineNum = 86;BA.debugLine="If flashModes.IsInitialized = False Then";
if (_flashmodes.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 87;BA.debugLine="ToastMessageShow(\"Flash not supported.\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Flash not supported."),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 88;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 90;BA.debugLine="Dim flash As String = flashModes.Get((flashModes.";
_flash = BA.ObjectToString(_flashmodes.Get((int) ((_flashmodes.IndexOf((Object)(mostCurrent._camex._getflashmode /*String*/ ()))+1)%_flashmodes.getSize())));
 //BA.debugLineNum = 91;BA.debugLine="camEx.SetFlashMode(flash)";
mostCurrent._camex._setflashmode /*String*/ (_flash);
 //BA.debugLineNum = 92;BA.debugLine="ToastMessageShow(flash, False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(_flash),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 93;BA.debugLine="camEx.CommitParameters";
mostCurrent._camex._commitparameters /*String*/ ();
 //BA.debugLineNum = 94;BA.debugLine="End Sub";
return "";
}
public static String  _btntakepicture_click() throws Exception{
 //BA.debugLineNum = 54;BA.debugLine="Sub btnTakePicture_Click";
 //BA.debugLineNum = 55;BA.debugLine="camEx.TakePicture";
mostCurrent._camex._takepicture /*String*/ ();
 //BA.debugLineNum = 56;BA.debugLine="End Sub";
return "";
}
public static String  _camera1_picturetaken(byte[] _data) throws Exception{
String _filename = "";
String _dir = "";
anywheresoftware.b4a.phone.Phone _phone = null;
anywheresoftware.b4a.objects.IntentWrapper _i = null;
 //BA.debugLineNum = 58;BA.debugLine="Sub Camera1_PictureTaken (Data() As Byte)";
 //BA.debugLineNum = 59;BA.debugLine="Dim filename As String";
_filename = "";
 //BA.debugLineNum = 60;BA.debugLine="Dim dir As String = File.DirRootExternal";
_dir = anywheresoftware.b4a.keywords.Common.File.getDirRootExternal();
 //BA.debugLineNum = 62;BA.debugLine="filename = sBillYear & \"-\" & sBillMonth & \"-\" & G";
_filename = mostCurrent._sbillyear+"-"+mostCurrent._sbillmonth+"-"+mostCurrent._globalvar._strbooksequence /*String*/ +".jpg";
 //BA.debugLineNum = 63;BA.debugLine="camEx.SavePictureToFile(Data, File.DirRootExterna";
mostCurrent._camex._savepicturetofile /*String*/ (_data,anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),_filename);
 //BA.debugLineNum = 64;BA.debugLine="camEx.StartPreview 'restart preview";
mostCurrent._camex._startpreview /*String*/ ();
 //BA.debugLineNum = 67;BA.debugLine="Dim Phone As Phone";
_phone = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 68;BA.debugLine="Dim i As Intent";
_i = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 69;BA.debugLine="i.Initialize(\"android.intent.action.MEDIA_SCANNER";
_i.Initialize("android.intent.action.MEDIA_SCANNER_SCAN_FILE","file://"+anywheresoftware.b4a.keywords.Common.File.Combine(_dir,_filename));
 //BA.debugLineNum = 71;BA.debugLine="Phone.SendBroadcastIntent(i)";
_phone.SendBroadcastIntent((android.content.Intent)(_i.getObject()));
 //BA.debugLineNum = 72;BA.debugLine="snack.Initialize(\"\",Activity,$\"Picture saved. Fil";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Picture saved. Filename : ")+_filename,mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 73;BA.debugLine="SetSnackBarBackground(snack, Colors.White)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 74;BA.debugLine="SetSnackBarTextColor(snack, GlobalVar.PriColor)";
_setsnackbartextcolor(mostCurrent._snack,(int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 75;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 76;BA.debugLine="End Sub";
return "";
}
public static String  _camera1_ready(boolean _success) throws Exception{
 //BA.debugLineNum = 44;BA.debugLine="Sub Camera1_Ready (Success As Boolean)";
 //BA.debugLineNum = 45;BA.debugLine="If Success Then";
if (_success) { 
 //BA.debugLineNum = 46;BA.debugLine="camEx.StartPreview";
mostCurrent._camex._startpreview /*String*/ ();
 //BA.debugLineNum = 47;BA.debugLine="camEx.SetJpegQuality(90)";
mostCurrent._camex._setjpegquality /*String*/ ((int) (90));
 //BA.debugLineNum = 48;BA.debugLine="camEx.CommitParameters";
mostCurrent._camex._commitparameters /*String*/ ();
 }else {
 //BA.debugLineNum = 50;BA.debugLine="ToastMessageShow(\"Cannot open camera.\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Cannot open camera."),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 52;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 11;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 12;BA.debugLine="Private Panel1 As Panel";
mostCurrent._panel1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 13;BA.debugLine="Private camEx As CameraExClass";
mostCurrent._camex = new com.bwsi.MeterReader.cameraexclass();
 //BA.debugLineNum = 14;BA.debugLine="Private iBillMonth As Int";
_ibillmonth = 0;
 //BA.debugLineNum = 15;BA.debugLine="Private sBillMonth As String";
mostCurrent._sbillmonth = "";
 //BA.debugLineNum = 16;BA.debugLine="Private sBillYear As String";
mostCurrent._sbillyear = "";
 //BA.debugLineNum = 17;BA.debugLine="Private snack As DSSnackbar";
mostCurrent._snack = new de.amberhome.objects.SnackbarWrapper();
 //BA.debugLineNum = 18;BA.debugLine="End Sub";
return "";
}
public static String  _initializecamera() throws Exception{
 //BA.debugLineNum = 35;BA.debugLine="Private Sub InitializeCamera";
 //BA.debugLineNum = 36;BA.debugLine="camEx.Initialize(Panel1, frontCamera, Me, \"Camera";
mostCurrent._camex._initialize /*String*/ (mostCurrent.activityBA,mostCurrent._panel1,_frontcamera,camera.getObject(),"Camera1");
 //BA.debugLineNum = 37;BA.debugLine="frontCamera = camEx.Front";
_frontcamera = mostCurrent._camex._front /*boolean*/ ;
 //BA.debugLineNum = 38;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 97;BA.debugLine="Sub SetSnackBarBackground(pSnack As DSSnackbar, pC";
 //BA.debugLineNum = 98;BA.debugLine="Dim v As View";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
 //BA.debugLineNum = 99;BA.debugLine="v = pSnack.View";
_v = (anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(_psnack.getView()));
 //BA.debugLineNum = 100;BA.debugLine="v.Color = pColor";
_v.setColor(_pcolor);
 //BA.debugLineNum = 101;BA.debugLine="End Sub";
return "";
}
public static String  _setsnackbartextcolor(de.amberhome.objects.SnackbarWrapper _psnack,int _pcolor) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _p = null;
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
anywheresoftware.b4a.objects.LabelWrapper _textv = null;
 //BA.debugLineNum = 103;BA.debugLine="Sub SetSnackBarTextColor(pSnack As DSSnackbar, pCo";
 //BA.debugLineNum = 104;BA.debugLine="Dim p As Panel";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 105;BA.debugLine="p = pSnack.View";
_p = (anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(_psnack.getView()));
 //BA.debugLineNum = 106;BA.debugLine="For Each v As View In p.GetAllViewsRecursive";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
{
final anywheresoftware.b4a.BA.IterableList group3 = _p.GetAllViewsRecursive();
final int groupLen3 = group3.getSize()
;int index3 = 0;
;
for (; index3 < groupLen3;index3++){
_v = (anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(group3.Get(index3)));
 //BA.debugLineNum = 107;BA.debugLine="If v Is Label Then";
if (_v.getObjectOrNull() instanceof android.widget.TextView) { 
 //BA.debugLineNum = 108;BA.debugLine="Dim textv As Label";
_textv = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 109;BA.debugLine="textv = v";
_textv = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_v.getObject()));
 //BA.debugLineNum = 110;BA.debugLine="textv.TextColor = pColor";
_textv.setTextColor(_pcolor);
 //BA.debugLineNum = 111;BA.debugLine="Exit";
if (true) break;
 };
 }
};
 //BA.debugLineNum = 114;BA.debugLine="End Sub";
return "";
}
}
