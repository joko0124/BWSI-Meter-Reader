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

public class readingsettings extends androidx.appcompat.app.AppCompatActivity implements B4AActivity{
	public static readingsettings mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "com.bwsi.MeterReader", "com.bwsi.MeterReader.readingsettings");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (readingsettings).");
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
		activityBA = new BA(this, layout, processBA, "com.bwsi.MeterReader", "com.bwsi.MeterReader.readingsettings");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "com.bwsi.MeterReader.readingsettings", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (readingsettings) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (readingsettings) Resume **");
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
		return readingsettings.class;
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
            BA.LogInfo("** Activity (readingsettings) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (readingsettings) Pause event (activity is not paused). **");
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
            readingsettings mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (readingsettings) Resume **");
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
public de.amberhome.objects.appcompat.ACActionBar _actionbarbutton = null;
public de.amberhome.objects.appcompat.ACToolbarDarkWrapper _toolbar = null;
public anywheresoftware.b4a.object.XmlLayoutBuilder _xmlicon = null;
public de.amberhome.materialdialogs.MaterialDialogBuilderWrapper _matdialogbuilder = null;
public de.amberhome.objects.SnackbarWrapper _snack = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlsettings = null;
public static int _ctrmonth = 0;
public static int _ctryear = 0;
public de.amberhome.objects.appcompat.ACSpinnerWrapper _cbobillmonth = null;
public de.amberhome.objects.appcompat.ACSpinnerWrapper _cbobillyear = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnok = null;
public anywheresoftware.b4a.objects.drawable.ColorDrawable _cdupdate = null;
public static long _lngyear = 0L;
public static int _intmonth = 0;
public static String _strbillperiod = "";
public anywheresoftware.b4a.objects.LabelWrapper _lblbillperiod = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public b4a.example.dateutils _dateutils = null;
public com.bwsi.MeterReader.main _main = null;
public com.bwsi.MeterReader.login _login = null;
public com.bwsi.MeterReader.mainscreen _mainscreen = null;
public com.bwsi.MeterReader.meterreading _meterreading = null;
public com.bwsi.MeterReader.datasyncing _datasyncing = null;
public com.bwsi.MeterReader.dbasefunctions _dbasefunctions = null;
public com.bwsi.MeterReader.camera _camera = null;
public com.bwsi.MeterReader.cmrvr _cmrvr = null;
public com.bwsi.MeterReader.customerbill _customerbill = null;
public com.bwsi.MeterReader.customerlist _customerlist = null;
public com.bwsi.MeterReader.customfunctions _customfunctions = null;
public com.bwsi.MeterReader.dbutils _dbutils = null;
public com.bwsi.MeterReader.globalvar _globalvar = null;
public com.bwsi.MeterReader.modvariables _modvariables = null;
public com.bwsi.MeterReader.myscale _myscale = null;
public com.bwsi.MeterReader.newcam _newcam = null;
public com.bwsi.MeterReader.readingbooks _readingbooks = null;
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
 //BA.debugLineNum = 36;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 38;BA.debugLine="Activity.LoadLayout(\"ReadingSettings\")";
mostCurrent._activity.LoadLayout("ReadingSettings",mostCurrent.activityBA);
 //BA.debugLineNum = 40;BA.debugLine="GlobalVar.CSTitle.Initialize.Size(13).Append($\"BI";
mostCurrent._globalvar._cstitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (13)).Append(BA.ObjectToCharSequence(("BILLING PERIOD SETTING"))).Bold().PopAll();
 //BA.debugLineNum = 41;BA.debugLine="GlobalVar.CSSubTitle.Initialize.Size(11).Append($";
mostCurrent._globalvar._cssubtitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (11)).Append(BA.ObjectToCharSequence(("Allows to Change Billing Period."))).PopAll();
 //BA.debugLineNum = 43;BA.debugLine="ToolBar.InitMenuListener";
mostCurrent._toolbar.InitMenuListener();
 //BA.debugLineNum = 44;BA.debugLine="ToolBar.Title = GlobalVar.CSTitle";
mostCurrent._toolbar.setTitle(BA.ObjectToCharSequence(mostCurrent._globalvar._cstitle /*anywheresoftware.b4a.objects.CSBuilder*/ .getObject()));
 //BA.debugLineNum = 45;BA.debugLine="ToolBar.SubTitle = GlobalVar.CSSubTitle";
mostCurrent._toolbar.setSubTitle(BA.ObjectToCharSequence(mostCurrent._globalvar._cssubtitle /*anywheresoftware.b4a.objects.CSBuilder*/ .getObject()));
 //BA.debugLineNum = 46;BA.debugLine="ToolBar.SetElevationAnimated(5,5dip)";
mostCurrent._toolbar.SetElevationAnimated((int) (5),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 48;BA.debugLine="Dim jo As JavaObject";
_jo = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 49;BA.debugLine="Dim xl As XmlLayoutBuilder";
_xl = new anywheresoftware.b4a.object.XmlLayoutBuilder();
 //BA.debugLineNum = 50;BA.debugLine="jo = ToolBar";
_jo = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(mostCurrent._toolbar.getObject()));
 //BA.debugLineNum = 51;BA.debugLine="jo.RunMethod(\"setPopupTheme\", Array(xl.GetResourc";
_jo.RunMethod("setPopupTheme",new Object[]{(Object)(_xl.GetResourceId("style","ToolbarMenu"))});
 //BA.debugLineNum = 52;BA.debugLine="jo.RunMethod(\"setContentInsetStartWithNavigation\"";
_jo.RunMethod("setContentInsetStartWithNavigation",new Object[]{(Object)(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)))});
 //BA.debugLineNum = 53;BA.debugLine="jo.RunMethod(\"setTitleMarginStart\", Array(0dip))";
_jo.RunMethod("setTitleMarginStart",new Object[]{(Object)(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)))});
 //BA.debugLineNum = 55;BA.debugLine="ActionBarButton.Initialize";
mostCurrent._actionbarbutton.Initialize(mostCurrent.activityBA);
 //BA.debugLineNum = 56;BA.debugLine="ActionBarButton.ShowUpIndicator = True";
mostCurrent._actionbarbutton.setShowUpIndicator(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 58;BA.debugLine="cdUpdate.Initialize2(GlobalVar.PriColor, 25, 0, C";
mostCurrent._cdupdate.Initialize2((int) (mostCurrent._globalvar._pricolor /*double*/ ),(int) (25),(int) (0),anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 59;BA.debugLine="btnOk.Background = cdUpdate";
mostCurrent._btnok.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdupdate.getObject()));
 //BA.debugLineNum = 60;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 62;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 63;BA.debugLine="If KeyCode = 4 Then";
if (_keycode==4) { 
 //BA.debugLineNum = 64;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 }else {
 //BA.debugLineNum = 66;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 68;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 76;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 78;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 70;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 71;BA.debugLine="pnlSettings.Visible = True";
mostCurrent._pnlsettings.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 72;BA.debugLine="FillSpinners";
_fillspinners();
 //BA.debugLineNum = 73;BA.debugLine="lblBillPeriod.Text = GlobalVar.BillPeriod";
mostCurrent._lblbillperiod.setText(BA.ObjectToCharSequence(mostCurrent._globalvar._billperiod /*String*/ ));
 //BA.debugLineNum = 74;BA.debugLine="End Sub";
return "";
}
public static String  _btnok_click() throws Exception{
long _datespecified = 0L;
long _yearspecified = 0L;
String _monthnamespecified = "";
int _monthspecified = 0;
 //BA.debugLineNum = 221;BA.debugLine="Sub btnOk_Click";
 //BA.debugLineNum = 222;BA.debugLine="Dim DateSpecified As Long";
_datespecified = 0L;
 //BA.debugLineNum = 223;BA.debugLine="Dim YearSpecified As Long";
_yearspecified = 0L;
 //BA.debugLineNum = 224;BA.debugLine="Dim MonthNameSpecified As String";
_monthnamespecified = "";
 //BA.debugLineNum = 225;BA.debugLine="Dim MonthSpecified As Int";
_monthspecified = 0;
 //BA.debugLineNum = 227;BA.debugLine="YearSpecified = GlobalVar.SF.Val(cboBillYear.Sele";
_yearspecified = (long) (mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv6(mostCurrent._cbobillyear.getSelectedItem()));
 //BA.debugLineNum = 228;BA.debugLine="MonthNameSpecified = cboBillMonth.SelectedItem";
_monthnamespecified = mostCurrent._cbobillmonth.getSelectedItem();
 //BA.debugLineNum = 230;BA.debugLine="DateTime.DateFormat = \"MMMM/dd/yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("MMMM/dd/yyyy");
 //BA.debugLineNum = 231;BA.debugLine="DateSpecified = DateTime.DateParse(MonthNameSpeci";
_datespecified = anywheresoftware.b4a.keywords.Common.DateTime.DateParse(_monthnamespecified+"/01/"+BA.NumberToString(_yearspecified));
 //BA.debugLineNum = 232;BA.debugLine="Log(DateUtils.GetMonthName(DateSpecified) & \" \" &";
anywheresoftware.b4a.keywords.Common.LogImpl("754198283",mostCurrent._dateutils._getmonthname(mostCurrent.activityBA,_datespecified)+" "+BA.NumberToString(anywheresoftware.b4a.keywords.Common.DateTime.GetYear(_datespecified)),0);
 //BA.debugLineNum = 234;BA.debugLine="strBillPeriod = DateUtils.GetMonthName(DateSpecif";
mostCurrent._strbillperiod = mostCurrent._dateutils._getmonthname(mostCurrent.activityBA,_datespecified)+" "+BA.NumberToString(_yearspecified);
 //BA.debugLineNum = 235;BA.debugLine="intMonth = DateTime.GetMonth(DateSpecified)";
_intmonth = anywheresoftware.b4a.keywords.Common.DateTime.GetMonth(_datespecified);
 //BA.debugLineNum = 236;BA.debugLine="lngYear = YearSpecified";
_lngyear = _yearspecified;
 //BA.debugLineNum = 239;BA.debugLine="If lngYear > DateTime.GetYear(DateTime.Now) Then";
if (_lngyear>anywheresoftware.b4a.keywords.Common.DateTime.GetYear(anywheresoftware.b4a.keywords.Common.DateTime.getNow())) { 
 //BA.debugLineNum = 241;BA.debugLine="snack.Initialize(\"\", Activity, $\"Unable to set B";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Unable to set Bill Year due to specified year is later than current."),mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 242;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 243;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 244;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 245;BA.debugLine="Return";
if (true) return "";
 }else if(anywheresoftware.b4a.keywords.Common.DateTime.GetYear(anywheresoftware.b4a.keywords.Common.DateTime.getNow())-_lngyear==1) { 
 //BA.debugLineNum = 247;BA.debugLine="If intMonth < 12 And DateTime.GetMonth(DateTime.";
if (_intmonth<12 && anywheresoftware.b4a.keywords.Common.DateTime.GetMonth(anywheresoftware.b4a.keywords.Common.DateTime.getNow())==1) { 
 //BA.debugLineNum = 249;BA.debugLine="snack.Initialize(\"\", Activity, $\"Specified year";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Specified year specified is later than the current year."),mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 250;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 251;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 252;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 253;BA.debugLine="Return";
if (true) return "";
 };
 }else if(_lngyear==anywheresoftware.b4a.keywords.Common.DateTime.GetYear(anywheresoftware.b4a.keywords.Common.DateTime.getNow())) { 
 //BA.debugLineNum = 256;BA.debugLine="If intMonth > DateTime.GetMonth(DateTime.Now) Th";
if (_intmonth>anywheresoftware.b4a.keywords.Common.DateTime.GetMonth(anywheresoftware.b4a.keywords.Common.DateTime.getNow())) { 
 //BA.debugLineNum = 258;BA.debugLine="snack.Initialize(\"\", Activity, $\"Unable to set";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Unable to set Bill Month due to specified month is later than current."),mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 259;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 260;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 261;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 262;BA.debugLine="Return";
if (true) return "";
 }else if(anywheresoftware.b4a.keywords.Common.DateTime.GetMonth(anywheresoftware.b4a.keywords.Common.DateTime.getNow())-_intmonth>1) { 
 //BA.debugLineNum = 265;BA.debugLine="snack.Initialize(\"\", Activity, $\"Unable to set";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Unable to set Bill Month due to specified month is earlier than current."),mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 266;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 267;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 268;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 269;BA.debugLine="Return";
if (true) return "";
 };
 }else {
 //BA.debugLineNum = 273;BA.debugLine="snack.Initialize(\"\", Activity, $\"Unable to set B";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Unable to set Bill Year due to specified year is earlier than current."),mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 274;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 275;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 276;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 277;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 279;BA.debugLine="ShowPasswordDialog";
_showpassworddialog();
 //BA.debugLineNum = 280;BA.debugLine="End Sub";
return "";
}
public static String  _fillspinners() throws Exception{
long _thedate = 0L;
long _billdate = 0L;
String _sbillyear = "";
long _lmonth = 0L;
 //BA.debugLineNum = 84;BA.debugLine="Private Sub FillSpinners";
 //BA.debugLineNum = 85;BA.debugLine="Dim theDate As Long";
_thedate = 0L;
 //BA.debugLineNum = 86;BA.debugLine="Dim lngYear, BillDate As Long";
_lngyear = 0L;
_billdate = 0L;
 //BA.debugLineNum = 87;BA.debugLine="Dim sBillYear As String";
_sbillyear = "";
 //BA.debugLineNum = 88;BA.debugLine="Dim lMonth As Long";
_lmonth = 0L;
 //BA.debugLineNum = 90;BA.debugLine="cboBillMonth.Clear";
mostCurrent._cbobillmonth.Clear();
 //BA.debugLineNum = 91;BA.debugLine="cboBillYear.Clear";
mostCurrent._cbobillyear.Clear();
 //BA.debugLineNum = 93;BA.debugLine="lMonth = DateTime.GetMonth(DateTime.Now)";
_lmonth = (long) (anywheresoftware.b4a.keywords.Common.DateTime.GetMonth(anywheresoftware.b4a.keywords.Common.DateTime.getNow()));
 //BA.debugLineNum = 94;BA.debugLine="LogColor(lMonth, Colors.Yellow)";
anywheresoftware.b4a.keywords.Common.LogImpl("753673994",BA.NumberToString(_lmonth),anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 //BA.debugLineNum = 96;BA.debugLine="If lMonth = 1 Then";
if (_lmonth==1) { 
 //BA.debugLineNum = 97;BA.debugLine="lngYear = (DateTime.GetYear(DateTime.Now) - 1)";
_lngyear = (long) ((anywheresoftware.b4a.keywords.Common.DateTime.GetYear(anywheresoftware.b4a.keywords.Common.DateTime.getNow())-1));
 }else {
 //BA.debugLineNum = 99;BA.debugLine="lngYear = (DateTime.GetYear(DateTime.Now))";
_lngyear = (long) ((anywheresoftware.b4a.keywords.Common.DateTime.GetYear(anywheresoftware.b4a.keywords.Common.DateTime.getNow())));
 };
 //BA.debugLineNum = 103;BA.debugLine="GlobalVar.BillYear = DBaseFunctions.GetBillYear(G";
mostCurrent._globalvar._billyear /*double*/  = mostCurrent._dbasefunctions._getbillyear /*double*/ (mostCurrent.activityBA,mostCurrent._globalvar._branchid /*int*/ );
 //BA.debugLineNum = 104;BA.debugLine="GlobalVar.BillMonth = DBaseFunctions.GetBillMonth";
mostCurrent._globalvar._billmonth /*int*/  = mostCurrent._dbasefunctions._getbillmonth /*int*/ (mostCurrent.activityBA,mostCurrent._globalvar._branchid /*int*/ );
 //BA.debugLineNum = 105;BA.debugLine="DateTime.DateFormat = \"MM/dd/yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("MM/dd/yyyy");
 //BA.debugLineNum = 106;BA.debugLine="BillDate = DateTime.DateParse(GlobalVar.BillMonth";
_billdate = anywheresoftware.b4a.keywords.Common.DateTime.DateParse(BA.NumberToString(mostCurrent._globalvar._billmonth /*int*/ )+"/01/"+BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ ));
 //BA.debugLineNum = 107;BA.debugLine="Log(BillDate)";
anywheresoftware.b4a.keywords.Common.LogImpl("753674007",BA.NumberToString(_billdate),0);
 //BA.debugLineNum = 109;BA.debugLine="GlobalVar.BillMonthName = DateUtils.GetMonthName(";
mostCurrent._globalvar._billmonthname /*String*/  = mostCurrent._dateutils._getmonthname(mostCurrent.activityBA,_billdate);
 //BA.debugLineNum = 110;BA.debugLine="sBillYear = GlobalVar.BillYear";
_sbillyear = BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ );
 //BA.debugLineNum = 112;BA.debugLine="For ctrMonth = 1 To 12";
{
final int step21 = 1;
final int limit21 = (int) (12);
_ctrmonth = (int) (1) ;
for (;_ctrmonth <= limit21 ;_ctrmonth = _ctrmonth + step21 ) {
 //BA.debugLineNum = 113;BA.debugLine="DateTime.DateFormat = \"MM/dd/yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("MM/dd/yyyy");
 //BA.debugLineNum = 114;BA.debugLine="theDate = DateTime.DateParse(ctrMonth & \"/01/\" &";
_thedate = anywheresoftware.b4a.keywords.Common.DateTime.DateParse(BA.NumberToString(_ctrmonth)+"/01/"+BA.NumberToString(_lngyear));
 //BA.debugLineNum = 115;BA.debugLine="cboBillMonth.Add(DateUtils.GetMonthName(theDate)";
mostCurrent._cbobillmonth.Add(BA.ObjectToCharSequence(mostCurrent._dateutils._getmonthname(mostCurrent.activityBA,_thedate)));
 }
};
 //BA.debugLineNum = 117;BA.debugLine="cboBillMonth.SelectedIndex = cboBillMonth.IndexOf";
mostCurrent._cbobillmonth.setSelectedIndex(mostCurrent._cbobillmonth.IndexOf(BA.ObjectToCharSequence(mostCurrent._globalvar._billmonthname /*String*/ )));
 //BA.debugLineNum = 119;BA.debugLine="For ctrYear = lngYear To 2050";
{
final int step27 = 1;
final int limit27 = (int) (2050);
_ctryear = (int) (_lngyear) ;
for (;_ctryear <= limit27 ;_ctryear = _ctryear + step27 ) {
 //BA.debugLineNum = 120;BA.debugLine="cboBillYear.Add(ctrYear)";
mostCurrent._cbobillyear.Add(BA.ObjectToCharSequence(_ctryear));
 }
};
 //BA.debugLineNum = 122;BA.debugLine="cboBillYear.SelectedIndex = cboBillYear.IndexOf(s";
mostCurrent._cbobillyear.setSelectedIndex(mostCurrent._cbobillyear.IndexOf(BA.ObjectToCharSequence(_sbillyear)));
 //BA.debugLineNum = 123;BA.debugLine="LogColor(GlobalVar.BillYear,Colors.Yellow)";
anywheresoftware.b4a.keywords.Common.LogImpl("753674023",BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ ),anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 //BA.debugLineNum = 125;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 15;BA.debugLine="Dim ActionBarButton As ACActionBar";
mostCurrent._actionbarbutton = new de.amberhome.objects.appcompat.ACActionBar();
 //BA.debugLineNum = 16;BA.debugLine="Private ToolBar As ACToolBarDark";
mostCurrent._toolbar = new de.amberhome.objects.appcompat.ACToolbarDarkWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private xmlIcon As XmlLayoutBuilder";
mostCurrent._xmlicon = new anywheresoftware.b4a.object.XmlLayoutBuilder();
 //BA.debugLineNum = 20;BA.debugLine="Private MatDialogBuilder As MaterialDialogBuilder";
mostCurrent._matdialogbuilder = new de.amberhome.materialdialogs.MaterialDialogBuilderWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private snack As DSSnackbar";
mostCurrent._snack = new de.amberhome.objects.SnackbarWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private pnlSettings As Panel";
mostCurrent._pnlsettings = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private ctrMonth, ctrYear As Int";
_ctrmonth = 0;
_ctryear = 0;
 //BA.debugLineNum = 25;BA.debugLine="Private cboBillMonth As ACSpinner";
mostCurrent._cbobillmonth = new de.amberhome.objects.appcompat.ACSpinnerWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private cboBillYear As ACSpinner";
mostCurrent._cbobillyear = new de.amberhome.objects.appcompat.ACSpinnerWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private btnOk As ACButton";
mostCurrent._btnok = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private cdUpdate As ColorDrawable";
mostCurrent._cdupdate = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 30;BA.debugLine="Dim lngYear As Long";
_lngyear = 0L;
 //BA.debugLineNum = 31;BA.debugLine="Dim intMonth As Int";
_intmonth = 0;
 //BA.debugLineNum = 32;BA.debugLine="Dim strBillPeriod As String";
mostCurrent._strbillperiod = "";
 //BA.debugLineNum = 33;BA.debugLine="Private lblBillPeriod As Label";
mostCurrent._lblbillperiod = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 34;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 7;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="Private strReadingData As String";
_strreadingdata = "";
 //BA.debugLineNum = 9;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static String  _retpassword_buttonpressed(de.amberhome.materialdialogs.MaterialDialogWrapper _mdialog,String _saction) throws Exception{
 //BA.debugLineNum = 182;BA.debugLine="Private Sub RetPassword_ButtonPressed (mDialog As";
 //BA.debugLineNum = 183;BA.debugLine="Select Case sAction";
switch (BA.switchObjectToInt(_saction,_mdialog.ACTION_POSITIVE,_mdialog.ACTION_NEGATIVE)) {
case 0: {
 //BA.debugLineNum = 185;BA.debugLine="If UpdateReadingSettings(intMonth, lngYear, str";
if (_updatereadingsettings(_intmonth,_lngyear,mostCurrent._strbillperiod)==anywheresoftware.b4a.keywords.Common.False) { 
if (true) return "";};
 //BA.debugLineNum = 186;BA.debugLine="GlobalVar.BillYear = lngYear";
mostCurrent._globalvar._billyear /*double*/  = _lngyear;
 //BA.debugLineNum = 187;BA.debugLine="GlobalVar.BillMonth = intMonth";
mostCurrent._globalvar._billmonth /*int*/  = _intmonth;
 //BA.debugLineNum = 188;BA.debugLine="GlobalVar.BillPeriod = strBillPeriod";
mostCurrent._globalvar._billperiod /*String*/  = mostCurrent._strbillperiod;
 //BA.debugLineNum = 189;BA.debugLine="ShowSuccessDialog";
_showsuccessdialog();
 break; }
case 1: {
 //BA.debugLineNum = 191;BA.debugLine="Return";
if (true) return "";
 break; }
}
;
 //BA.debugLineNum = 193;BA.debugLine="End Sub";
return "";
}
public static String  _retpassword_inputchanged(de.amberhome.materialdialogs.MaterialDialogWrapper _mdialog,String _spassword) throws Exception{
anywheresoftware.b4a.objects.CSBuilder _csbuild = null;
 //BA.debugLineNum = 163;BA.debugLine="Private Sub RetPassword_InputChanged (mDialog As M";
 //BA.debugLineNum = 164;BA.debugLine="Dim csBuild As CSBuilder";
_csbuild = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 165;BA.debugLine="If sPassword.Length <= 0 Then";
if (_spassword.length()<=0) { 
 //BA.debugLineNum = 166;BA.debugLine="csBuild.Initialize.Bold.Color(Colors.Red).Append";
_csbuild.Initialize().Bold().Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(("Enter your Password to Continue..."))).PopAll();
 //BA.debugLineNum = 167;BA.debugLine="mDialog.Content = csBuild";
_mdialog.setContent(BA.ObjectToCharSequence(_csbuild.getObject()));
 //BA.debugLineNum = 168;BA.debugLine="mDialog.EnableActionButton(mDialog.ACTION_POSITI";
_mdialog.EnableActionButton(_mdialog.ACTION_POSITIVE,anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 170;BA.debugLine="If sPassword <> GlobalVar.UserPW Then";
if ((_spassword).equals(mostCurrent._globalvar._userpw /*String*/ ) == false) { 
 //BA.debugLineNum = 171;BA.debugLine="mDialog.EnableActionButton(mDialog.ACTION_POSIT";
_mdialog.EnableActionButton(_mdialog.ACTION_POSITIVE,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 172;BA.debugLine="csBuild.Initialize.Bold.Color(Colors.Red).Appen";
_csbuild.Initialize().Bold().Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(("Password is Incorrect!"))).PopAll();
 //BA.debugLineNum = 173;BA.debugLine="mDialog.Content = csBuild";
_mdialog.setContent(BA.ObjectToCharSequence(_csbuild.getObject()));
 }else {
 //BA.debugLineNum = 175;BA.debugLine="mDialog.EnableActionButton(mDialog.ACTION_POSIT";
_mdialog.EnableActionButton(_mdialog.ACTION_POSITIVE,anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 176;BA.debugLine="csBuild.Initialize.Bold.Color(Colors.Black).App";
_csbuild.Initialize().Bold().Color(anywheresoftware.b4a.keywords.Common.Colors.Black).Append(BA.ObjectToCharSequence(("Password is Ok!"))).PopAll();
 //BA.debugLineNum = 177;BA.debugLine="mDialog.Content = csBuild";
_mdialog.setContent(BA.ObjectToCharSequence(_csbuild.getObject()));
 };
 };
 //BA.debugLineNum = 180;BA.debugLine="End Sub";
return "";
}
public static String  _setsnackbarbackground(de.amberhome.objects.SnackbarWrapper _psnack,int _pcolor) throws Exception{
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
 //BA.debugLineNum = 127;BA.debugLine="Sub SetSnackBarBackground(pSnack As DSSnackbar, pC";
 //BA.debugLineNum = 128;BA.debugLine="Dim v As View";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
 //BA.debugLineNum = 129;BA.debugLine="v = pSnack.View";
_v = (anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(_psnack.getView()));
 //BA.debugLineNum = 130;BA.debugLine="v.Color = pColor";
_v.setColor(_pcolor);
 //BA.debugLineNum = 131;BA.debugLine="End Sub";
return "";
}
public static String  _setsnackbartextcolor(de.amberhome.objects.SnackbarWrapper _psnack,int _pcolor) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _p = null;
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
anywheresoftware.b4a.objects.LabelWrapper _textv = null;
 //BA.debugLineNum = 133;BA.debugLine="Sub SetSnackBarTextColor(pSnack As DSSnackbar, pCo";
 //BA.debugLineNum = 134;BA.debugLine="Dim p As Panel";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 135;BA.debugLine="p = pSnack.View";
_p = (anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(_psnack.getView()));
 //BA.debugLineNum = 136;BA.debugLine="For Each v As View In p.GetAllViewsRecursive";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
{
final anywheresoftware.b4a.BA.IterableList group3 = _p.GetAllViewsRecursive();
final int groupLen3 = group3.getSize()
;int index3 = 0;
;
for (; index3 < groupLen3;index3++){
_v = (anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(group3.Get(index3)));
 //BA.debugLineNum = 137;BA.debugLine="If v Is Label Then";
if (_v.getObjectOrNull() instanceof android.widget.TextView) { 
 //BA.debugLineNum = 138;BA.debugLine="Dim textv As Label";
_textv = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 139;BA.debugLine="textv = v";
_textv = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_v.getObject()));
 //BA.debugLineNum = 140;BA.debugLine="textv.TextColor = pColor";
_textv.setTextColor(_pcolor);
 //BA.debugLineNum = 141;BA.debugLine="Exit";
if (true) break;
 };
 }
};
 //BA.debugLineNum = 144;BA.debugLine="End Sub";
return "";
}
public static String  _settingsok_buttonpressed(de.amberhome.materialdialogs.MaterialDialogWrapper _mdialog,String _saction) throws Exception{
 //BA.debugLineNum = 211;BA.debugLine="Private Sub SettingsOK_ButtonPressed (mDialog As M";
 //BA.debugLineNum = 212;BA.debugLine="Select Case sAction";
switch (BA.switchObjectToInt(_saction,_mdialog.ACTION_POSITIVE,_mdialog.ACTION_NEGATIVE)) {
case 0: {
 //BA.debugLineNum = 214;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 break; }
case 1: {
 //BA.debugLineNum = 216;BA.debugLine="Return";
if (true) return "";
 break; }
}
;
 //BA.debugLineNum = 218;BA.debugLine="End Sub";
return "";
}
public static String  _showpassworddialog() throws Exception{
 //BA.debugLineNum = 148;BA.debugLine="Private Sub ShowPasswordDialog";
 //BA.debugLineNum = 149;BA.debugLine="MatDialogBuilder.Initialize(\"RetPassword\")";
mostCurrent._matdialogbuilder.Initialize(mostCurrent.activityBA,"RetPassword");
 //BA.debugLineNum = 150;BA.debugLine="MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIG";
mostCurrent._matdialogbuilder.Theme(mostCurrent._matdialogbuilder.THEME_LIGHT);
 //BA.debugLineNum = 151;BA.debugLine="MatDialogBuilder.Title($\"Password Required\"$).Tit";
mostCurrent._matdialogbuilder.Title(BA.ObjectToCharSequence(("Password Required"))).TitleColor((int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 152;BA.debugLine="MatDialogBuilder.Content($\"Input your Password.\"$";
mostCurrent._matdialogbuilder.Content(BA.ObjectToCharSequence(("Input your Password.")));
 //BA.debugLineNum = 153;BA.debugLine="MatDialogBuilder.InputType(MatDialogBuilder.TYPE_";
mostCurrent._matdialogbuilder.InputType(mostCurrent._matdialogbuilder.TYPE_TEXT_VARIATION_PASSWORD);
 //BA.debugLineNum = 154;BA.debugLine="MatDialogBuilder.Input(\"Enter your Password Here.";
mostCurrent._matdialogbuilder.Input(BA.ObjectToCharSequence("Enter your Password Here..."),BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 155;BA.debugLine="MatDialogBuilder.PositiveText($\"Ok\"$).PositiveCol";
mostCurrent._matdialogbuilder.PositiveText(BA.ObjectToCharSequence(("Ok"))).PositiveColor((int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 156;BA.debugLine="MatDialogBuilder.NegativeText($\"Cancel\"$).Negativ";
mostCurrent._matdialogbuilder.NegativeText(BA.ObjectToCharSequence(("Cancel"))).NegativeColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 157;BA.debugLine="MatDialogBuilder.CanceledOnTouchOutside(False)";
mostCurrent._matdialogbuilder.CanceledOnTouchOutside(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 158;BA.debugLine="MatDialogBuilder.AlwaysCallInputCallback";
mostCurrent._matdialogbuilder.AlwaysCallInputCallback();
 //BA.debugLineNum = 159;BA.debugLine="MatDialogBuilder.IconRes(\"ic_security_black_36dp2";
mostCurrent._matdialogbuilder.IconRes("ic_security_black_36dp2").LimitIconToDefaultSize();
 //BA.debugLineNum = 160;BA.debugLine="MatDialogBuilder.Show";
mostCurrent._matdialogbuilder.Show();
 //BA.debugLineNum = 161;BA.debugLine="End Sub";
return "";
}
public static String  _showsuccessdialog() throws Exception{
anywheresoftware.b4a.objects.CSBuilder _cstitle = null;
anywheresoftware.b4a.objects.CSBuilder _cscontent = null;
 //BA.debugLineNum = 195;BA.debugLine="Private Sub ShowSuccessDialog";
 //BA.debugLineNum = 196;BA.debugLine="Dim csTitle, csContent As CSBuilder";
_cstitle = new anywheresoftware.b4a.objects.CSBuilder();
_cscontent = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 197;BA.debugLine="csTitle.Initialize.Color(GlobalVar.PriColor).Bold";
_cstitle.Initialize().Color((int) (mostCurrent._globalvar._pricolor /*double*/ )).Bold().Size((int) (16)).Append(BA.ObjectToCharSequence(("SUCCESS"))).PopAll();
 //BA.debugLineNum = 198;BA.debugLine="csContent.Initialize.Size(14).Color(GlobalVar.Pri";
_cscontent.Initialize().Size((int) (14)).Color((int) (mostCurrent._globalvar._pricolor /*double*/ )).Bold().Append(BA.ObjectToCharSequence(("Billing Period was successfully updated."))).PopAll();
 //BA.debugLineNum = 200;BA.debugLine="MatDialogBuilder.Initialize(\"SettingsOK\")";
mostCurrent._matdialogbuilder.Initialize(mostCurrent.activityBA,"SettingsOK");
 //BA.debugLineNum = 201;BA.debugLine="MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIG";
mostCurrent._matdialogbuilder.Theme(mostCurrent._matdialogbuilder.THEME_LIGHT);
 //BA.debugLineNum = 202;BA.debugLine="MatDialogBuilder.Title(csTitle).TitleGravity(MatD";
mostCurrent._matdialogbuilder.Title(BA.ObjectToCharSequence(_cstitle.getObject())).TitleGravity(mostCurrent._matdialogbuilder.GRAVITY_START);
 //BA.debugLineNum = 203;BA.debugLine="MatDialogBuilder.IconRes(GlobalVar.InfoIcon).Limi";
mostCurrent._matdialogbuilder.IconRes(mostCurrent._globalvar._infoicon /*String*/ ).LimitIconToDefaultSize();
 //BA.debugLineNum = 204;BA.debugLine="MatDialogBuilder.Content(csContent)";
mostCurrent._matdialogbuilder.Content(BA.ObjectToCharSequence(_cscontent.getObject()));
 //BA.debugLineNum = 205;BA.debugLine="MatDialogBuilder.PositiveText($\"OK\"$).PositiveCol";
mostCurrent._matdialogbuilder.PositiveText(BA.ObjectToCharSequence(("OK"))).PositiveColor((int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 207;BA.debugLine="MatDialogBuilder.CanceledOnTouchOutside(False)";
mostCurrent._matdialogbuilder.CanceledOnTouchOutside(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 208;BA.debugLine="MatDialogBuilder.Show";
mostCurrent._matdialogbuilder.Show();
 //BA.debugLineNum = 209;BA.debugLine="End Sub";
return "";
}
public static String  _toolbar_navigationitemclick() throws Exception{
 //BA.debugLineNum = 80;BA.debugLine="Sub ToolBar_NavigationItemClick";
 //BA.debugLineNum = 81;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 82;BA.debugLine="End Sub";
return "";
}
public static boolean  _updatereadingsettings(int _imonth,long _lyear,String _sbillperiod) throws Exception{
boolean _blnret = false;
 //BA.debugLineNum = 282;BA.debugLine="Private Sub UpdateReadingSettings(iMonth As Int, l";
 //BA.debugLineNum = 283;BA.debugLine="Dim blnRet As Boolean";
_blnret = false;
 //BA.debugLineNum = 284;BA.debugLine="Starter.DBCon.BeginTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .BeginTransaction();
 //BA.debugLineNum = 285;BA.debugLine="Try";
try { //BA.debugLineNum = 286;BA.debugLine="Starter.strCriteria = \"UPDATE tblSysParam SET Bi";
mostCurrent._starter._strcriteria /*String*/  = "UPDATE tblSysParam SET BillMonth = ?, BillYear = ?, BillPeriod = ? "+"WHERE BranchID = "+BA.NumberToString(mostCurrent._globalvar._branchid /*int*/ );
 //BA.debugLineNum = 288;BA.debugLine="Starter.DBCon.ExecNonQuery2(Starter.strCriteria,";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2(mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{BA.NumberToString(_imonth),BA.NumberToString(_lyear),_sbillperiod}));
 //BA.debugLineNum = 289;BA.debugLine="Starter.DBCon.TransactionSuccessful";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .TransactionSuccessful();
 //BA.debugLineNum = 290;BA.debugLine="blnRet = True";
_blnret = anywheresoftware.b4a.keywords.Common.True;
 } 
       catch (Exception e9) {
			processBA.setLastException(e9); //BA.debugLineNum = 292;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("754263818",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 //BA.debugLineNum = 293;BA.debugLine="blnRet = False";
_blnret = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 295;BA.debugLine="Starter.DBCon.EndTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .EndTransaction();
 //BA.debugLineNum = 296;BA.debugLine="Return blnRet";
if (true) return _blnret;
 //BA.debugLineNum = 297;BA.debugLine="End Sub";
return false;
}
}
