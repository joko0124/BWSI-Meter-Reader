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

public class useraccountsettings extends androidx.appcompat.app.AppCompatActivity implements B4AActivity{
	public static useraccountsettings mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "com.bwsi.MeterReader", "com.bwsi.MeterReader.useraccountsettings");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (useraccountsettings).");
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
		activityBA = new BA(this, layout, processBA, "com.bwsi.MeterReader", "com.bwsi.MeterReader.useraccountsettings");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "com.bwsi.MeterReader.useraccountsettings", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (useraccountsettings) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (useraccountsettings) Resume **");
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
		return useraccountsettings.class;
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
            BA.LogInfo("** Activity (useraccountsettings) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (useraccountsettings) Pause event (activity is not paused). **");
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
            useraccountsettings mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (useraccountsettings) Resume **");
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
public static String _strreadingdata = "";
public static anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public static com.johan.Vibrate.Vibrate _vibration = null;
public static long[] _vibratepattern = null;
public de.amberhome.objects.appcompat.ACActionBar _actionbarbutton = null;
public de.amberhome.objects.appcompat.ACToolbarDarkWrapper _toolbar = null;
public anywheresoftware.b4a.object.XmlLayoutBuilder _xmlicon = null;
public de.amberhome.materialdialogs.MaterialDialogBuilderWrapper _matdialogbuilder = null;
public de.amberhome.objects.SnackbarWrapper _snack = null;
public static int _intflestyle = 0;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnsave = null;
public de.amberhome.objects.FloatlabelEditTextWrapper _txtconfirmpassword = null;
public de.amberhome.objects.FloatlabelEditTextWrapper _txtcurrentusername = null;
public de.amberhome.objects.FloatlabelEditTextWrapper _txtnewpassword = null;
public de.amberhome.objects.FloatlabelEditTextWrapper _txtnewusername = null;
public de.amberhome.objects.FloatlabelEditTextWrapper _txtoldpassword = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public b4a.example.dateutils _dateutils = null;
public com.bwsi.MeterReader.main _main = null;
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
anywheresoftware.b4j.object.JavaObject _javaobjectinstance = null;
 //BA.debugLineNum = 35;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 37;BA.debugLine="Activity.LoadLayout(\"UserSettings\")";
mostCurrent._activity.LoadLayout("UserSettings",mostCurrent.activityBA);
 //BA.debugLineNum = 39;BA.debugLine="GlobalVar.CSTitle.Initialize.Size(13).Append($\"US";
mostCurrent._globalvar._cstitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (13)).Append(BA.ObjectToCharSequence(("USER ACCOUNT SETTING"))).Bold().PopAll();
 //BA.debugLineNum = 40;BA.debugLine="GlobalVar.CSSubTitle.Initialize.Size(11).Append($";
mostCurrent._globalvar._cssubtitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (11)).Append(BA.ObjectToCharSequence(("Allows to Modify Account Info."))).PopAll();
 //BA.debugLineNum = 42;BA.debugLine="ToolBar.InitMenuListener";
mostCurrent._toolbar.InitMenuListener();
 //BA.debugLineNum = 43;BA.debugLine="ToolBar.Title = GlobalVar.CSTitle";
mostCurrent._toolbar.setTitle(BA.ObjectToCharSequence(mostCurrent._globalvar._cstitle /*anywheresoftware.b4a.objects.CSBuilder*/ .getObject()));
 //BA.debugLineNum = 44;BA.debugLine="ToolBar.SubTitle = GlobalVar.CSSubTitle";
mostCurrent._toolbar.setSubTitle(BA.ObjectToCharSequence(mostCurrent._globalvar._cssubtitle /*anywheresoftware.b4a.objects.CSBuilder*/ .getObject()));
 //BA.debugLineNum = 45;BA.debugLine="ToolBar.SetElevationAnimated(5,5dip)";
mostCurrent._toolbar.SetElevationAnimated((int) (5),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 47;BA.debugLine="Dim jo As JavaObject";
_jo = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 48;BA.debugLine="Dim xl As XmlLayoutBuilder";
_xl = new anywheresoftware.b4a.object.XmlLayoutBuilder();
 //BA.debugLineNum = 49;BA.debugLine="jo = ToolBar";
_jo = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(mostCurrent._toolbar.getObject()));
 //BA.debugLineNum = 50;BA.debugLine="jo.RunMethod(\"setPopupTheme\", Array(xl.GetResourc";
_jo.RunMethod("setPopupTheme",new Object[]{(Object)(_xl.GetResourceId("style","ToolbarMenu"))});
 //BA.debugLineNum = 51;BA.debugLine="jo.RunMethod(\"setContentInsetStartWithNavigation\"";
_jo.RunMethod("setContentInsetStartWithNavigation",new Object[]{(Object)(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)))});
 //BA.debugLineNum = 52;BA.debugLine="jo.RunMethod(\"setTitleMarginStart\", Array(0dip))";
_jo.RunMethod("setTitleMarginStart",new Object[]{(Object)(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)))});
 //BA.debugLineNum = 54;BA.debugLine="ActionBarButton.Initialize";
mostCurrent._actionbarbutton.Initialize(mostCurrent.activityBA);
 //BA.debugLineNum = 55;BA.debugLine="ActionBarButton.ShowUpIndicator = True";
mostCurrent._actionbarbutton.setShowUpIndicator(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 57;BA.debugLine="Dim javaobjectInstance As JavaObject";
_javaobjectinstance = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 58;BA.debugLine="intFleStyle = xmlIcon.GetResourceId (\"style\", \"fl";
_intflestyle = mostCurrent._xmlicon.GetResourceId("style","floating_hint");
 //BA.debugLineNum = 60;BA.debugLine="javaobjectInstance = txtNewUserName";
_javaobjectinstance = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(mostCurrent._txtnewusername.getObject()));
 //BA.debugLineNum = 61;BA.debugLine="javaobjectInstance.RunMethod (\"setHintTextAppeara";
_javaobjectinstance.RunMethod("setHintTextAppearance",new Object[]{(Object)(_intflestyle)});
 //BA.debugLineNum = 63;BA.debugLine="javaobjectInstance = txtNewUserName";
_javaobjectinstance = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(mostCurrent._txtnewusername.getObject()));
 //BA.debugLineNum = 64;BA.debugLine="javaobjectInstance.RunMethod (\"setHintTextAppeara";
_javaobjectinstance.RunMethod("setHintTextAppearance",new Object[]{(Object)(_intflestyle)});
 //BA.debugLineNum = 66;BA.debugLine="javaobjectInstance = txtOldPassword";
_javaobjectinstance = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(mostCurrent._txtoldpassword.getObject()));
 //BA.debugLineNum = 67;BA.debugLine="javaobjectInstance.RunMethod (\"setHintTextAppeara";
_javaobjectinstance.RunMethod("setHintTextAppearance",new Object[]{(Object)(_intflestyle)});
 //BA.debugLineNum = 69;BA.debugLine="javaobjectInstance = txtNewPassword";
_javaobjectinstance = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(mostCurrent._txtnewpassword.getObject()));
 //BA.debugLineNum = 70;BA.debugLine="javaobjectInstance.RunMethod (\"setHintTextAppeara";
_javaobjectinstance.RunMethod("setHintTextAppearance",new Object[]{(Object)(_intflestyle)});
 //BA.debugLineNum = 72;BA.debugLine="javaobjectInstance = txtConfirmPassword";
_javaobjectinstance = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(mostCurrent._txtconfirmpassword.getObject()));
 //BA.debugLineNum = 73;BA.debugLine="javaobjectInstance.RunMethod (\"setHintTextAppeara";
_javaobjectinstance.RunMethod("setHintTextAppearance",new Object[]{(Object)(_intflestyle)});
 //BA.debugLineNum = 74;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 76;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 77;BA.debugLine="If KeyCode = 4 Then";
if (_keycode==4) { 
 //BA.debugLineNum = 78;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 }else {
 //BA.debugLineNum = 80;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 82;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 89;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 91;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 84;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 85;BA.debugLine="txtCurrentUserName.Text = GlobalVar.UserName";
mostCurrent._txtcurrentusername.setText(mostCurrent._globalvar._username /*String*/ );
 //BA.debugLineNum = 86;BA.debugLine="vibratePattern = Array As Long(500, 500, 300, 500";
_vibratepattern = new long[]{(long) (500),(long) (500),(long) (300),(long) (500)};
 //BA.debugLineNum = 87;BA.debugLine="End Sub";
return "";
}
public static String  _btnsave_click() throws Exception{
 //BA.debugLineNum = 141;BA.debugLine="Sub btnSave_Click";
 //BA.debugLineNum = 142;BA.debugLine="If GlobalVar.SF.Len(txtNewUserName.Text.Trim) <=";
if (mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(mostCurrent._txtnewusername.getText().trim())<=0) { 
 //BA.debugLineNum = 143;BA.debugLine="txtNewUserName.RequestFocus";
mostCurrent._txtnewusername.RequestFocus();
 //BA.debugLineNum = 144;BA.debugLine="txtNewUserName.SelectAll";
mostCurrent._txtnewusername.SelectAll();
 //BA.debugLineNum = 145;BA.debugLine="snack.Initialize(\"\", Activity, $\"New User Name f";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("New User Name field cannot be blank."),mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 146;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 147;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 148;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 149;BA.debugLine="vibration.vibrateOnce(1500)";
_vibration.vibrateOnce(processBA,(long) (1500));
 //BA.debugLineNum = 150;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 153;BA.debugLine="If GlobalVar.SF.Len(txtOldPassword.Text.Trim) <=";
if (mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(mostCurrent._txtoldpassword.getText().trim())<=0) { 
 //BA.debugLineNum = 154;BA.debugLine="txtOldPassword.RequestFocus";
mostCurrent._txtoldpassword.RequestFocus();
 //BA.debugLineNum = 155;BA.debugLine="txtOldPassword.SelectAll";
mostCurrent._txtoldpassword.SelectAll();
 //BA.debugLineNum = 156;BA.debugLine="snack.Initialize(\"\", Activity, $\"Please enter yo";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Please enter your CURRENT password."),mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 157;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 158;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 159;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 160;BA.debugLine="vibration.vibrateOnce(1500)";
_vibration.vibrateOnce(processBA,(long) (1500));
 //BA.debugLineNum = 161;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 164;BA.debugLine="If txtOldPassword.Text <> GlobalVar.UserPW Then";
if ((mostCurrent._txtoldpassword.getText()).equals(mostCurrent._globalvar._userpw /*String*/ ) == false) { 
 //BA.debugLineNum = 165;BA.debugLine="txtOldPassword.RequestFocus";
mostCurrent._txtoldpassword.RequestFocus();
 //BA.debugLineNum = 166;BA.debugLine="txtOldPassword.SelectAll";
mostCurrent._txtoldpassword.SelectAll();
 //BA.debugLineNum = 167;BA.debugLine="snack.Initialize(\"\", Activity, $\"Password not ma";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Password not match."),mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 168;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 169;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 170;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 171;BA.debugLine="vibration.vibrateOnce(1500)";
_vibration.vibrateOnce(processBA,(long) (1500));
 //BA.debugLineNum = 172;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 175;BA.debugLine="If GlobalVar.SF.Len(txtNewPassword.Text.Trim) <=";
if (mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(mostCurrent._txtnewpassword.getText().trim())<=0) { 
 //BA.debugLineNum = 176;BA.debugLine="txtNewPassword.RequestFocus";
mostCurrent._txtnewpassword.RequestFocus();
 //BA.debugLineNum = 177;BA.debugLine="snack.Initialize(\"\", Activity, $\"Please enter yo";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Please enter your NEW password."),mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 178;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 179;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 180;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 181;BA.debugLine="vibration.vibrateOnce(1500)";
_vibration.vibrateOnce(processBA,(long) (1500));
 //BA.debugLineNum = 182;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 185;BA.debugLine="If txtConfirmPassword.Text <> txtNewPassword.Text";
if ((mostCurrent._txtconfirmpassword.getText()).equals(mostCurrent._txtnewpassword.getText()) == false) { 
 //BA.debugLineNum = 186;BA.debugLine="txtConfirmPassword.Text = \"\"";
mostCurrent._txtconfirmpassword.setText("");
 //BA.debugLineNum = 187;BA.debugLine="txtNewPassword.RequestFocus";
mostCurrent._txtnewpassword.RequestFocus();
 //BA.debugLineNum = 188;BA.debugLine="txtNewPassword.SelectAll";
mostCurrent._txtnewpassword.SelectAll();
 //BA.debugLineNum = 189;BA.debugLine="snack.Initialize(\"\", Activity, $\"Password not ma";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Password not match."),mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 190;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 191;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 192;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 193;BA.debugLine="vibration.vibrateOnce(1500)";
_vibration.vibrateOnce(processBA,(long) (1500));
 //BA.debugLineNum = 194;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 197;BA.debugLine="If UpdateUserLocally(txtNewUserName.Text, txtNewP";
if (_updateuserlocally(mostCurrent._txtnewusername.getText(),mostCurrent._txtnewpassword.getText(),mostCurrent._globalvar._userid /*int*/ )==anywheresoftware.b4a.keywords.Common.True) { 
 };
 //BA.debugLineNum = 201;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 15;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 18;BA.debugLine="Dim ActionBarButton As ACActionBar";
mostCurrent._actionbarbutton = new de.amberhome.objects.appcompat.ACActionBar();
 //BA.debugLineNum = 19;BA.debugLine="Private ToolBar As ACToolBarDark";
mostCurrent._toolbar = new de.amberhome.objects.appcompat.ACToolbarDarkWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private xmlIcon As XmlLayoutBuilder";
mostCurrent._xmlicon = new anywheresoftware.b4a.object.XmlLayoutBuilder();
 //BA.debugLineNum = 23;BA.debugLine="Private MatDialogBuilder As MaterialDialogBuilder";
mostCurrent._matdialogbuilder = new de.amberhome.materialdialogs.MaterialDialogBuilderWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private snack As DSSnackbar";
mostCurrent._snack = new de.amberhome.objects.SnackbarWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private intFleStyle As Int";
_intflestyle = 0;
 //BA.debugLineNum = 27;BA.debugLine="Private btnSave As ACButton";
mostCurrent._btnsave = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private txtConfirmPassword As DSFloatLabelEditTex";
mostCurrent._txtconfirmpassword = new de.amberhome.objects.FloatlabelEditTextWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private txtCurrentUserName As DSFloatLabelEditTex";
mostCurrent._txtcurrentusername = new de.amberhome.objects.FloatlabelEditTextWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private txtNewPassword As DSFloatLabelEditText";
mostCurrent._txtnewpassword = new de.amberhome.objects.FloatlabelEditTextWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private txtNewUserName As DSFloatLabelEditText";
mostCurrent._txtnewusername = new de.amberhome.objects.FloatlabelEditTextWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private txtOldPassword As DSFloatLabelEditText";
mostCurrent._txtoldpassword = new de.amberhome.objects.FloatlabelEditTextWrapper();
 //BA.debugLineNum = 33;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 7;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="Private strReadingData As String";
_strreadingdata = "";
 //BA.debugLineNum = 9;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 10;BA.debugLine="Private vibration As B4Avibrate";
_vibration = new com.johan.Vibrate.Vibrate();
 //BA.debugLineNum = 11;BA.debugLine="Private vibratePattern() As Long";
_vibratepattern = new long[(int) (0)];
;
 //BA.debugLineNum = 13;BA.debugLine="End Sub";
return "";
}
public static String  _setsnackbarbackground(de.amberhome.objects.SnackbarWrapper _psnack,int _pcolor) throws Exception{
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
 //BA.debugLineNum = 204;BA.debugLine="Sub SetSnackBarBackground(pSnack As DSSnackbar, pC";
 //BA.debugLineNum = 205;BA.debugLine="Dim v As View";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
 //BA.debugLineNum = 206;BA.debugLine="v = pSnack.View";
_v = (anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(_psnack.getView()));
 //BA.debugLineNum = 207;BA.debugLine="v.Color = pColor";
_v.setColor(_pcolor);
 //BA.debugLineNum = 208;BA.debugLine="End Sub";
return "";
}
public static String  _setsnackbartextcolor(de.amberhome.objects.SnackbarWrapper _psnack,int _pcolor) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _p = null;
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
anywheresoftware.b4a.objects.LabelWrapper _textv = null;
 //BA.debugLineNum = 210;BA.debugLine="Sub SetSnackBarTextColor(pSnack As DSSnackbar, pCo";
 //BA.debugLineNum = 211;BA.debugLine="Dim p As Panel";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 212;BA.debugLine="p = pSnack.View";
_p = (anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(_psnack.getView()));
 //BA.debugLineNum = 213;BA.debugLine="For Each v As View In p.GetAllViewsRecursive";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
{
final anywheresoftware.b4a.BA.IterableList group3 = _p.GetAllViewsRecursive();
final int groupLen3 = group3.getSize()
;int index3 = 0;
;
for (; index3 < groupLen3;index3++){
_v = (anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(group3.Get(index3)));
 //BA.debugLineNum = 214;BA.debugLine="If v Is Label Then";
if (_v.getObjectOrNull() instanceof android.widget.TextView) { 
 //BA.debugLineNum = 215;BA.debugLine="Dim textv As Label";
_textv = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 216;BA.debugLine="textv = v";
_textv = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_v.getObject()));
 //BA.debugLineNum = 217;BA.debugLine="textv.TextColor = pColor";
_textv.setTextColor(_pcolor);
 //BA.debugLineNum = 218;BA.debugLine="Exit";
if (true) break;
 };
 }
};
 //BA.debugLineNum = 221;BA.debugLine="End Sub";
return "";
}
public static String  _settingsok_buttonpressed(de.amberhome.materialdialogs.MaterialDialogWrapper _mdialog,String _saction) throws Exception{
 //BA.debugLineNum = 115;BA.debugLine="Private Sub SettingsOK_ButtonPressed (mDialog As M";
 //BA.debugLineNum = 116;BA.debugLine="Select Case sAction";
switch (BA.switchObjectToInt(_saction,_mdialog.ACTION_POSITIVE,_mdialog.ACTION_NEGATIVE)) {
case 0: {
 //BA.debugLineNum = 118;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 break; }
case 1: {
 //BA.debugLineNum = 120;BA.debugLine="Return";
if (true) return "";
 break; }
}
;
 //BA.debugLineNum = 122;BA.debugLine="End Sub";
return "";
}
public static String  _showsuccessdialog() throws Exception{
anywheresoftware.b4a.objects.CSBuilder _cstitle = null;
anywheresoftware.b4a.objects.CSBuilder _cscontent = null;
 //BA.debugLineNum = 99;BA.debugLine="Private Sub ShowSuccessDialog";
 //BA.debugLineNum = 100;BA.debugLine="Dim csTitle, csContent As CSBuilder";
_cstitle = new anywheresoftware.b4a.objects.CSBuilder();
_cscontent = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 101;BA.debugLine="csTitle.Initialize.Color(GlobalVar.PriColor).Bold";
_cstitle.Initialize().Color((int) (mostCurrent._globalvar._pricolor /*double*/ )).Bold().Size((int) (16)).Append(BA.ObjectToCharSequence(("Download Complete"))).PopAll();
 //BA.debugLineNum = 102;BA.debugLine="csContent.Initialize.Size(14).Color(GlobalVar.Pri";
_cscontent.Initialize().Size((int) (14)).Color((int) (mostCurrent._globalvar._pricolor /*double*/ )).Bold().Append(BA.ObjectToCharSequence(("Reading Data for the specified Reader were successfully downloaded."))).PopAll();
 //BA.debugLineNum = 104;BA.debugLine="MatDialogBuilder.Initialize(\"SettingsOK\")";
mostCurrent._matdialogbuilder.Initialize(mostCurrent.activityBA,"SettingsOK");
 //BA.debugLineNum = 105;BA.debugLine="MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIG";
mostCurrent._matdialogbuilder.Theme(mostCurrent._matdialogbuilder.THEME_LIGHT);
 //BA.debugLineNum = 106;BA.debugLine="MatDialogBuilder.Title(csTitle).TitleGravity(MatD";
mostCurrent._matdialogbuilder.Title(BA.ObjectToCharSequence(_cstitle.getObject())).TitleGravity(mostCurrent._matdialogbuilder.GRAVITY_START);
 //BA.debugLineNum = 107;BA.debugLine="MatDialogBuilder.IconRes(GlobalVar.InfoIcon).Limi";
mostCurrent._matdialogbuilder.IconRes(mostCurrent._globalvar._infoicon /*String*/ ).LimitIconToDefaultSize();
 //BA.debugLineNum = 108;BA.debugLine="MatDialogBuilder.Content(csContent)";
mostCurrent._matdialogbuilder.Content(BA.ObjectToCharSequence(_cscontent.getObject()));
 //BA.debugLineNum = 109;BA.debugLine="MatDialogBuilder.PositiveText($\"OK\"$).PositiveCol";
mostCurrent._matdialogbuilder.PositiveText(BA.ObjectToCharSequence(("OK"))).PositiveColor((int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 110;BA.debugLine="MatDialogBuilder.NegativeText($\"Download Another\"";
mostCurrent._matdialogbuilder.NegativeText(BA.ObjectToCharSequence(("Download Another"))).NegativeColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 111;BA.debugLine="MatDialogBuilder.CanceledOnTouchOutside(False)";
mostCurrent._matdialogbuilder.CanceledOnTouchOutside(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 112;BA.debugLine="MatDialogBuilder.Show";
mostCurrent._matdialogbuilder.Show();
 //BA.debugLineNum = 113;BA.debugLine="End Sub";
return "";
}
public static String  _toolbar_navigationitemclick() throws Exception{
 //BA.debugLineNum = 93;BA.debugLine="Sub ToolBar_NavigationItemClick";
 //BA.debugLineNum = 94;BA.debugLine="If CustomFunctions.ConfirmYN($\"Close User's Setti";
if (mostCurrent._customfunctions._confirmyn /*boolean*/ (mostCurrent.activityBA,("Close User's Settings now?"),("CONFIRM CLOSE"),("YES"),(""),("CANCEL"))==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 95;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 97;BA.debugLine="End Sub";
return "";
}
public static boolean  _updateuserlocally(String _snewusername,String _snewpass,int _iuserid) throws Exception{
boolean _blnret = false;
 //BA.debugLineNum = 124;BA.debugLine="Private Sub UpdateUserLocally(sNewUserName As Stri";
 //BA.debugLineNum = 125;BA.debugLine="Dim blnRet As Boolean";
_blnret = false;
 //BA.debugLineNum = 126;BA.debugLine="Starter.DBCon.BeginTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .BeginTransaction();
 //BA.debugLineNum = 127;BA.debugLine="Try";
try { //BA.debugLineNum = 128;BA.debugLine="Starter.strCriteria = \"UPDATE tblUsers SET UserN";
mostCurrent._starter._strcriteria /*String*/  = "UPDATE tblUsers SET UserName = ?, UserPassword = ? "+"WHERE UserID = "+BA.NumberToString(_iuserid);
 //BA.debugLineNum = 130;BA.debugLine="Starter.DBCon.ExecNonQuery2(Starter.strCriteria,";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2(mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{_snewusername,_snewpass}));
 //BA.debugLineNum = 131;BA.debugLine="Starter.DBCon.TransactionSuccessful";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .TransactionSuccessful();
 //BA.debugLineNum = 132;BA.debugLine="blnRet = True";
_blnret = anywheresoftware.b4a.keywords.Common.True;
 } 
       catch (Exception e9) {
			processBA.setLastException(e9); //BA.debugLineNum = 134;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("460817418",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 //BA.debugLineNum = 135;BA.debugLine="blnRet = False";
_blnret = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 137;BA.debugLine="Starter.DBCon.EndTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .EndTransaction();
 //BA.debugLineNum = 138;BA.debugLine="Return blnRet";
if (true) return _blnret;
 //BA.debugLineNum = 139;BA.debugLine="End Sub";
return false;
}
}
