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

public class login extends androidx.appcompat.app.AppCompatActivity implements B4AActivity{
	public static login mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "com.bwsi.MeterReader", "com.bwsi.MeterReader.login");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (login).");
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
		activityBA = new BA(this, layout, processBA, "com.bwsi.MeterReader", "com.bwsi.MeterReader.login");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "com.bwsi.MeterReader.login", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (login) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (login) Resume **");
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
		return login.class;
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
            BA.LogInfo("** Activity (login) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (login) Pause event (activity is not paused). **");
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
            login mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (login) Resume **");
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
public static tillekesoft.b4a.ToggleWifiData.ToggleWifiData _tglwifi = null;
public static boolean _wifistatus = false;
public static adr.stringfunctions.stringfunctions _sf = null;
public static anywheresoftware.b4a.objects.RuntimePermissions _permissions = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnlogin = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkshowpass = null;
public com.bwsi.MeterReader.b4xfloattextfield _txtpassword = null;
public com.bwsi.MeterReader.globalvar._theuser _users = null;
public com.bwsi.MeterReader.globalvar._thebranch _userbranch = null;
public com.bwsi.MeterReader.globalvar._thecompany _companies = null;
public static String _urlname = "";
public anywheresoftware.b4a.samples.httputils2.httpjob _job1 = null;
public de.amberhome.objects.FloatingActionButtonWrapper _btnsettings = null;
public de.amberhome.materialdialogs.MaterialDialogBuilderWrapper _matdialogbuilder = null;
public static String _strpassword = "";
public de.amberhome.objects.SnackbarWrapper _snack = null;
public static String _newip = "";
public anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
public anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public com.johan.Vibrate.Vibrate _vibration = null;
public static long[] _vibratepattern = null;
public anywheresoftware.b4a.sql.SQL.CursorWrapper _rsbranches = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblbranch = null;
public de.amberhome.objects.FloatingActionButtonWrapper _btnbranch = null;
public static int _intsettingsflag = 0;
public static String _strselectedbranch = "";
public static int _intflestyle = 0;
public anywheresoftware.b4a.object.XmlLayoutBuilder _xmlicon = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _cbobranches = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblselectedbranch = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlbranches = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlsearchbranch = null;
public com.bwsi.MeterReader.searchview _sv = null;
public static String _searchfor = "";
public de.amberhome.objects.appcompat.ACButtonWrapper _btncancel = null;
public anywheresoftware.b4a.objects.drawable.ColorDrawable _cdtextbox = null;
public static double _selectedbranchid = 0;
public static String _selectedbranchname = "";
public static String _surl = "";
public anywheresoftware.b4a.objects.PanelWrapper _pnlsearchbranches = null;
public com.bwsi.MeterReader.b4xfloattextfield _txtusername = null;
public com.bwsi.MeterReader.slinptypeconst _inptyp = null;
public static boolean _blnwifion = false;
public static boolean _blnuserclosed = false;
public com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alert = null;
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
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
 //BA.debugLineNum = 78;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 80;BA.debugLine="MyScale.SetRate(0.5)";
mostCurrent._myscale._setrate /*String*/ (mostCurrent.activityBA,0.5);
 //BA.debugLineNum = 82;BA.debugLine="Activity.LoadLayout(\"LoginNew\")";
mostCurrent._activity.LoadLayout("LoginNew",mostCurrent.activityBA);
 //BA.debugLineNum = 83;BA.debugLine="CD.Initialize2(0xFF1976D2, 25, 0, 0xFF000000)";
mostCurrent._cd.Initialize2((int) (0xff1976d2),(int) (25),(int) (0),(int) (0xff000000));
 //BA.debugLineNum = 84;BA.debugLine="btnLogin.Background = CD";
mostCurrent._btnlogin.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cd.getObject()));
 //BA.debugLineNum = 86;BA.debugLine="GlobalVar.ServerAddress = DBaseFunctions.GetServe";
mostCurrent._globalvar._serveraddress /*String*/  = mostCurrent._dbasefunctions._getserverip /*String*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 87;BA.debugLine="GlobalVar.BranchID = DBaseFunctions.GetBranchID";
mostCurrent._globalvar._branchid /*int*/  = mostCurrent._dbasefunctions._getbranchid /*int*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 88;BA.debugLine="GlobalVar.CompanyID = DBaseFunctions.GetCompanyID";
mostCurrent._globalvar._companyid /*int*/  = mostCurrent._dbasefunctions._getcompanyid /*int*/ (mostCurrent.activityBA,mostCurrent._globalvar._branchid /*int*/ );
 //BA.debugLineNum = 89;BA.debugLine="GetBGImage(GlobalVar.CompanyID)";
_getbgimage(mostCurrent._globalvar._companyid /*int*/ );
 //BA.debugLineNum = 92;BA.debugLine="LogColor(GlobalVar.BranchID, Colors.Blue)";
anywheresoftware.b4a.keywords.Common.LogImpl("427721742",BA.NumberToString(mostCurrent._globalvar._branchid /*int*/ ),anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 93;BA.debugLine="tglWifi.Initialize";
_tglwifi.Initialize(processBA);
 //BA.debugLineNum = 94;BA.debugLine="WiFiStatus = tglWifi.isWIFI_enabled";
_wifistatus = _tglwifi.isWIFI_enabled();
 //BA.debugLineNum = 95;BA.debugLine="txtUserName.Text = \"\"";
mostCurrent._txtusername._settext /*String*/ ("");
 //BA.debugLineNum = 96;BA.debugLine="txtPassword.Text = \"\"";
mostCurrent._txtpassword._settext /*String*/ ("");
 //BA.debugLineNum = 97;BA.debugLine="txtUserName.RequestFocusAndShowKeyboard";
mostCurrent._txtusername._requestfocusandshowkeyboard /*String*/ ();
 //BA.debugLineNum = 98;BA.debugLine="blnWifiOn = False";
_blnwifion = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 99;BA.debugLine="pnlSearchBranches.Visible = False";
mostCurrent._pnlsearchbranches.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 100;BA.debugLine="CD.Initialize2(GlobalVar.PriColor, 5dip, 0dip,Col";
mostCurrent._cd.Initialize2((int) (mostCurrent._globalvar._pricolor /*double*/ ),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 101;BA.debugLine="btnCancel.Background = CD";
mostCurrent._btncancel.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cd.getObject()));
 //BA.debugLineNum = 103;BA.debugLine="SF.Initialize";
_sf._initialize(processBA);
 //BA.debugLineNum = 104;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 105;BA.debugLine="r.Target = r.GetContext";
_r.Target = (Object)(_r.GetContext(processBA));
 //BA.debugLineNum = 106;BA.debugLine="r.Target = r.RunMethod(\"getResources\")";
_r.Target = _r.RunMethod("getResources");
 //BA.debugLineNum = 107;BA.debugLine="r.Target = r.RunMethod(\"getDisplayMetrics\")";
_r.Target = _r.RunMethod("getDisplayMetrics");
 //BA.debugLineNum = 108;BA.debugLine="LogColor($\"X DPI: \"$ & r.GetField(\"xdpi\"),Colors.";
anywheresoftware.b4a.keywords.Common.LogImpl("427721758",("X DPI: ")+BA.ObjectToString(_r.GetField("xdpi")),anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 //BA.debugLineNum = 109;BA.debugLine="LogColor($\"Y DPI: \"$ & r.GetField(\"ydpi\"),Colors.";
anywheresoftware.b4a.keywords.Common.LogImpl("427721759",("Y DPI: ")+BA.ObjectToString(_r.GetField("ydpi")),anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 //BA.debugLineNum = 111;BA.debugLine="LogColor($\"WIDTH: \"$ & GetDeviceLayoutValues.Widt";
anywheresoftware.b4a.keywords.Common.LogImpl("427721761",("WIDTH: ")+BA.NumberToString(anywheresoftware.b4a.keywords.Common.GetDeviceLayoutValues(mostCurrent.activityBA).Width),anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 112;BA.debugLine="LogColor($\"HEIGHT: \"$ & GetDeviceLayoutValues.Hei";
anywheresoftware.b4a.keywords.Common.LogImpl("427721762",("HEIGHT: ")+BA.NumberToString(anywheresoftware.b4a.keywords.Common.GetDeviceLayoutValues(mostCurrent.activityBA).Height),anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 114;BA.debugLine="Log(GetDeviceLayoutValues.Width/r.GetField(\"xdpi\"";
anywheresoftware.b4a.keywords.Common.LogImpl("427721764",BA.NumberToString(anywheresoftware.b4a.keywords.Common.GetDeviceLayoutValues(mostCurrent.activityBA).Width/(double)(double)(BA.ObjectToNumber(_r.GetField("xdpi")))),0);
 //BA.debugLineNum = 115;BA.debugLine="Log(GetDeviceLayoutValues.Height/r.GetField(\"ydpi";
anywheresoftware.b4a.keywords.Common.LogImpl("427721765",BA.NumberToString(anywheresoftware.b4a.keywords.Common.GetDeviceLayoutValues(mostCurrent.activityBA).Height/(double)(double)(BA.ObjectToNumber(_r.GetField("ydpi")))),0);
 //BA.debugLineNum = 117;BA.debugLine="txtUserName.mBase.SetColorAndBorder(Colors.Transp";
mostCurrent._txtusername._mbase /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .SetColorAndBorder(anywheresoftware.b4a.keywords.Common.Colors.Transparent,(int) (0),anywheresoftware.b4a.keywords.Common.Colors.Transparent,(int) (0));
 //BA.debugLineNum = 118;BA.debugLine="txtPassword.mBase.SetColorAndBorder(Colors.Transp";
mostCurrent._txtpassword._mbase /*anywheresoftware.b4a.objects.B4XViewWrapper*/ .SetColorAndBorder(anywheresoftware.b4a.keywords.Common.Colors.Transparent,(int) (0),anywheresoftware.b4a.keywords.Common.Colors.Transparent,(int) (0));
 //BA.debugLineNum = 119;BA.debugLine="GlobalVar.ServerAddress = DBaseFunctions.GetServe";
mostCurrent._globalvar._serveraddress /*String*/  = mostCurrent._dbasefunctions._getserverip /*String*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 120;BA.debugLine="GlobalVar.BranchID = DBaseFunctions.GetBranchID";
mostCurrent._globalvar._branchid /*int*/  = mostCurrent._dbasefunctions._getbranchid /*int*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 121;BA.debugLine="GlobalVar.CompanyID = DBaseFunctions.GetCompanyID";
mostCurrent._globalvar._companyid /*int*/  = mostCurrent._dbasefunctions._getcompanyid /*int*/ (mostCurrent.activityBA,mostCurrent._globalvar._branchid /*int*/ );
 //BA.debugLineNum = 122;BA.debugLine="GetBGImage(GlobalVar.CompanyID)";
_getbgimage(mostCurrent._globalvar._companyid /*int*/ );
 //BA.debugLineNum = 123;BA.debugLine="pnlSearchBranches.Visible = False";
mostCurrent._pnlsearchbranches.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 125;BA.debugLine="CheckPermissions";
_checkpermissions();
 //BA.debugLineNum = 126;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 128;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 129;BA.debugLine="If KeyCode = 4 Then";
if (_keycode==4) { 
 //BA.debugLineNum = 130;BA.debugLine="ConfirmExitApp($\"CONFIRM EXIT\"$, $\"Do you want t";
_confirmexitapp(("CONFIRM EXIT"),("Do you want to Close ")+anywheresoftware.b4a.keywords.Common.Application.getLabelName()+("?"));
 //BA.debugLineNum = 131;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 133;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 135;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 147;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 148;BA.debugLine="UserClosed = blnUserClosed";
_userclosed = _blnuserclosed;
 //BA.debugLineNum = 149;BA.debugLine="If UserClosed = True Then";
if (_userclosed==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 150;BA.debugLine="ExitApplication";
anywheresoftware.b4a.keywords.Common.ExitApplication();
 };
 //BA.debugLineNum = 152;BA.debugLine="End Sub";
return "";
}
public static String  _activity_permissionresult(String _permission,boolean _result) throws Exception{
 //BA.debugLineNum = 172;BA.debugLine="Sub Activity_PermissionResult (Permission As Strin";
 //BA.debugLineNum = 173;BA.debugLine="If Permission = Permissions.PERMISSION_CAMERA The";
if ((_permission).equals(_permissions.PERMISSION_CAMERA)) { 
 //BA.debugLineNum = 174;BA.debugLine="Result = True";
_result = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 175;BA.debugLine="LogColor(\"YOU CAN USE THE CAMERA\",Colors.Red)";
anywheresoftware.b4a.keywords.Common.LogImpl("428049411","YOU CAN USE THE CAMERA",anywheresoftware.b4a.keywords.Common.Colors.Red);
 }else {
 //BA.debugLineNum = 177;BA.debugLine="Result = False";
_result = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 179;BA.debugLine="Log (Permission)";
anywheresoftware.b4a.keywords.Common.LogImpl("428049415",_permission,0);
 //BA.debugLineNum = 180;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 137;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 138;BA.debugLine="If blnUserClosed = False Then";
if (_blnuserclosed==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 139;BA.debugLine="GlobalVar.ServerAddress = DBaseFunctions.GetServ";
mostCurrent._globalvar._serveraddress /*String*/  = mostCurrent._dbasefunctions._getserverip /*String*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 140;BA.debugLine="GlobalVar.BranchID = DBaseFunctions.GetBranchID";
mostCurrent._globalvar._branchid /*int*/  = mostCurrent._dbasefunctions._getbranchid /*int*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 141;BA.debugLine="GlobalVar.CompanyID = DBaseFunctions.GetCompanyI";
mostCurrent._globalvar._companyid /*int*/  = mostCurrent._dbasefunctions._getcompanyid /*int*/ (mostCurrent.activityBA,mostCurrent._globalvar._branchid /*int*/ );
 //BA.debugLineNum = 142;BA.debugLine="GetBGImage(GlobalVar.CompanyID)";
_getbgimage(mostCurrent._globalvar._companyid /*int*/ );
 //BA.debugLineNum = 143;BA.debugLine="pnlSearchBranches.Visible = False";
mostCurrent._pnlsearchbranches.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 145;BA.debugLine="End Sub";
return "";
}
public static String  _adminpassword_buttonpressed(de.amberhome.materialdialogs.MaterialDialogWrapper _dialog,String _action) throws Exception{
 //BA.debugLineNum = 461;BA.debugLine="Sub AdminPassword_ButtonPressed (Dialog As Materia";
 //BA.debugLineNum = 462;BA.debugLine="Select Case Action";
switch (BA.switchObjectToInt(_action,_dialog.ACTION_POSITIVE,_dialog.ACTION_NEGATIVE)) {
case 0: {
 //BA.debugLineNum = 464;BA.debugLine="Select intSettingsFlag";
switch (_intsettingsflag) {
case 1: {
 //BA.debugLineNum = 466;BA.debugLine="If strPassword <>GlobalVar.AdminPassword Then";
if ((mostCurrent._strpassword).equals(mostCurrent._globalvar._adminpassword /*String*/ ) == false) { 
 //BA.debugLineNum = 467;BA.debugLine="vibration.vibrateOnce(2000)";
mostCurrent._vibration.vibrateOnce(processBA,(long) (2000));
 //BA.debugLineNum = 468;BA.debugLine="snack.Initialize(\"RetryButton\", Activity, \"P";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"RetryButton",(android.view.View)(mostCurrent._activity.getObject()),"Password is Incorrect!",mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 469;BA.debugLine="snack.SetAction(\"RETRY\")";
mostCurrent._snack.SetAction("RETRY");
 //BA.debugLineNum = 470;BA.debugLine="SetSnackBarBackground(snack,Colors.White)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 471;BA.debugLine="SetSnackBarTextColor(snack,Colors.Red)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 472;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 473;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 475;BA.debugLine="UpdateServerIP(NewIP)";
_updateserverip(mostCurrent._newip);
 break; }
case 2: {
 //BA.debugLineNum = 477;BA.debugLine="If strPassword <>GlobalVar.AdminPassword Then";
if ((mostCurrent._strpassword).equals(mostCurrent._globalvar._adminpassword /*String*/ ) == false) { 
 //BA.debugLineNum = 478;BA.debugLine="vibration.vibrateOnce(2000)";
mostCurrent._vibration.vibrateOnce(processBA,(long) (2000));
 //BA.debugLineNum = 479;BA.debugLine="snack.Initialize(\"RetryButton\", Activity, \"P";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"RetryButton",(android.view.View)(mostCurrent._activity.getObject()),"Password is Incorrect!",mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 480;BA.debugLine="snack.SetAction(\"RETRY\")";
mostCurrent._snack.SetAction("RETRY");
 //BA.debugLineNum = 481;BA.debugLine="SetSnackBarBackground(snack,Colors.White)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 482;BA.debugLine="SetSnackBarTextColor(snack,Colors.Red)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 483;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 484;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 486;BA.debugLine="GlobalVar.BranchID = SelectedBranchID";
mostCurrent._globalvar._branchid /*int*/  = (int) (_selectedbranchid);
 //BA.debugLineNum = 487;BA.debugLine="UpdateBranchID(GlobalVar.BranchID)";
_updatebranchid(BA.NumberToString(mostCurrent._globalvar._branchid /*int*/ ));
 //BA.debugLineNum = 488;BA.debugLine="GlobalVar.CompanyID = DBaseFunctions.GetCompa";
mostCurrent._globalvar._companyid /*int*/  = mostCurrent._dbasefunctions._getcompanyid /*int*/ (mostCurrent.activityBA,mostCurrent._globalvar._branchid /*int*/ );
 //BA.debugLineNum = 489;BA.debugLine="GetBGImage(GlobalVar.CompanyID)";
_getbgimage(mostCurrent._globalvar._companyid /*int*/ );
 break; }
default: {
 //BA.debugLineNum = 491;BA.debugLine="Return";
if (true) return "";
 break; }
}
;
 break; }
case 1: {
 //BA.debugLineNum = 494;BA.debugLine="Select intSettingsFlag";
switch (_intsettingsflag) {
case 1: {
 //BA.debugLineNum = 496;BA.debugLine="snack.Initialize(\"RetryButton\", Activity, \"Se";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"RetryButton",(android.view.View)(mostCurrent._activity.getObject()),"Setting Server's IP Address Cancelled!",mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 497;BA.debugLine="SetSnackBarBackground(snack,Colors.Red)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 498;BA.debugLine="SetSnackBarTextColor(snack,Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 499;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 500;BA.debugLine="Return";
if (true) return "";
 break; }
case 2: {
 //BA.debugLineNum = 502;BA.debugLine="snack.Initialize(\"RetryButton\", Activity, \"Se";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"RetryButton",(android.view.View)(mostCurrent._activity.getObject()),"Setting Branch was Cancelled!",mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 503;BA.debugLine="SetSnackBarBackground(snack,Colors.Red)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 504;BA.debugLine="SetSnackBarTextColor(snack,Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 505;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 506;BA.debugLine="Return";
if (true) return "";
 break; }
default: {
 //BA.debugLineNum = 508;BA.debugLine="Return";
if (true) return "";
 break; }
}
;
 break; }
}
;
 //BA.debugLineNum = 511;BA.debugLine="End Sub";
return "";
}
public static String  _adminpassword_inputchanged(de.amberhome.materialdialogs.MaterialDialogWrapper _mdialog,String _spassword) throws Exception{
 //BA.debugLineNum = 452;BA.debugLine="Private Sub AdminPassword_InputChanged (mDialog As";
 //BA.debugLineNum = 453;BA.debugLine="If sPassword.Length = 0 Then";
if (_spassword.length()==0) { 
 //BA.debugLineNum = 454;BA.debugLine="mDialog.EnableActionButton(mDialog.ACTION_POSITI";
_mdialog.EnableActionButton(_mdialog.ACTION_POSITIVE,anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 456;BA.debugLine="mDialog.EnableActionButton(mDialog.ACTION_POSITI";
_mdialog.EnableActionButton(_mdialog.ACTION_POSITIVE,anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 458;BA.debugLine="strPassword = sPassword";
mostCurrent._strpassword = _spassword;
 //BA.debugLineNum = 459;BA.debugLine="End Sub";
return "";
}
public static String  _allowwifi_onnegativeclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
 //BA.debugLineNum = 1021;BA.debugLine="Private Sub AllowWiFi_OnNegativeClicked (View As V";
 //BA.debugLineNum = 1022;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
mostCurrent._alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 1023;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
mostCurrent._alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 1024;BA.debugLine="blnWifiOn = False";
_blnwifion = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 1026;BA.debugLine="vibration.vibrateOnce(2000)";
mostCurrent._vibration.vibrateOnce(processBA,(long) (2000));
 //BA.debugLineNum = 1027;BA.debugLine="snack.Initialize(\"\", Activity,$\"Unable to log due";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Unable to log due to NO INTERNET CONNECTION."),mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 1028;BA.debugLine="SetSnackBarBackground(snack,Colors.Red)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1029;BA.debugLine="SetSnackBarTextColor(snack,Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1030;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 1031;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 1033;BA.debugLine="End Sub";
return "";
}
public static void  _allowwifi_onpositiveclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
ResumableSub_AllowWiFi_OnPositiveClicked rsub = new ResumableSub_AllowWiFi_OnPositiveClicked(null,_view,_dialog);
rsub.resume(processBA, null);
}
public static class ResumableSub_AllowWiFi_OnPositiveClicked extends BA.ResumableSub {
public ResumableSub_AllowWiFi_OnPositiveClicked(com.bwsi.MeterReader.login parent,anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) {
this.parent = parent;
this._view = _view;
this._dialog = _dialog;
}
com.bwsi.MeterReader.login parent;
anywheresoftware.b4a.objects.ConcreteViewWrapper _view;
Object _dialog;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 1036;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
parent.mostCurrent._alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 1037;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
parent.mostCurrent._alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 1038;BA.debugLine="Sleep(1000)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (1000));
this.state = 5;
return;
case 5:
//C
this.state = 1;
;
 //BA.debugLineNum = 1039;BA.debugLine="blnWifiOn = True";
parent._blnwifion = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 1040;BA.debugLine="If blnWifiOn = True Then";
if (true) break;

case 1:
//if
this.state = 4;
if (parent._blnwifion==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 1041;BA.debugLine="tglWifi.toggleWIFI";
parent._tglwifi.toggleWIFI();
 //BA.debugLineNum = 1042;BA.debugLine="ProgressDialogShow2($\"Checking Database Server C";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence(("Checking Database Server Connection.")+anywheresoftware.b4a.keywords.Common.CRLF+("Please Wait...")),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1043;BA.debugLine="GetUserAccount(GlobalVar.BranchID, txtUserName.T";
_getuseraccount(parent.mostCurrent._globalvar._branchid /*int*/ ,parent.mostCurrent._txtusername._gettext /*String*/ (),parent.mostCurrent._txtpassword._gettext /*String*/ ());
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 1045;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _branchsettingsdialog_buttonpressed(de.amberhome.materialdialogs.MaterialDialogWrapper _dialog,String _action) throws Exception{
 //BA.debugLineNum = 656;BA.debugLine="Sub BranchSettingsDialog_ButtonPressed (Dialog As";
 //BA.debugLineNum = 657;BA.debugLine="Select Action";
switch (BA.switchObjectToInt(_action,_dialog.ACTION_POSITIVE,_dialog.ACTION_NEGATIVE)) {
case 0: {
 //BA.debugLineNum = 659;BA.debugLine="If cboBranches.SelectedItem = \"\" Then Return";
if ((mostCurrent._cbobranches.getSelectedItem()).equals("")) { 
if (true) return "";};
 //BA.debugLineNum = 660;BA.debugLine="strSelectedBranch = cboBranches.SelectedItem";
mostCurrent._strselectedbranch = mostCurrent._cbobranches.getSelectedItem();
 //BA.debugLineNum = 661;BA.debugLine="ShowAdminPassword";
_showadminpassword();
 break; }
case 1: {
 break; }
}
;
 //BA.debugLineNum = 664;BA.debugLine="End Sub";
return "";
}
public static String  _branchsettingsdialog_customviewready(de.amberhome.materialdialogs.MaterialDialogWrapper _dialog,anywheresoftware.b4a.objects.PanelWrapper _customview) throws Exception{
 //BA.debugLineNum = 650;BA.debugLine="Sub BranchSettingsDialog_CustomViewReady (Dialog A";
 //BA.debugLineNum = 652;BA.debugLine="CustomView.LoadLayout(\"BranchSettings\")";
_customview.LoadLayout("BranchSettings",mostCurrent.activityBA);
 //BA.debugLineNum = 653;BA.debugLine="FillBranches";
_fillbranches();
 //BA.debugLineNum = 654;BA.debugLine="End Sub";
return "";
}
public static String  _btnbranch_click() throws Exception{
 //BA.debugLineNum = 666;BA.debugLine="Sub btnBranch_Click";
 //BA.debugLineNum = 678;BA.debugLine="If pnlSearchBranches.Visible = True Then Return";
if (mostCurrent._pnlsearchbranches.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
if (true) return "";};
 //BA.debugLineNum = 679;BA.debugLine="intSettingsFlag = 2";
_intsettingsflag = (int) (2);
 //BA.debugLineNum = 680;BA.debugLine="DisableControls";
_disablecontrols();
 //BA.debugLineNum = 681;BA.debugLine="SV.Initialize(Me,\"SV\")";
mostCurrent._sv._initialize /*String*/ (mostCurrent.activityBA,login.getObject(),"SV");
 //BA.debugLineNum = 682;BA.debugLine="SV.AddToParent(pnlSearchBranch,10,30,pnlSearchBra";
mostCurrent._sv._addtoparent /*String*/ (mostCurrent._pnlsearchbranch,(int) (10),(int) (30),mostCurrent._pnlsearchbranch.getWidth(),mostCurrent._pnlsearchbranch.getHeight());
 //BA.debugLineNum = 683;BA.debugLine="SV.ClearAll";
mostCurrent._sv._clearall /*String*/ ();
 //BA.debugLineNum = 684;BA.debugLine="lblSelectedBranch.Text = $\"Current Branch:\"$ & CR";
mostCurrent._lblselectedbranch.setText(BA.ObjectToCharSequence(("Current Branch:")+anywheresoftware.b4a.keywords.Common.CRLF+mostCurrent._dbasefunctions._getbranchname /*String*/ (mostCurrent.activityBA,mostCurrent._globalvar._branchid /*int*/ )));
 //BA.debugLineNum = 685;BA.debugLine="pnlSearchBranches.Visible=True";
mostCurrent._pnlsearchbranches.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 687;BA.debugLine="SearchBranches";
_searchbranches();
 //BA.debugLineNum = 688;BA.debugLine="End Sub";
return "";
}
public static String  _btncancel_click() throws Exception{
 //BA.debugLineNum = 831;BA.debugLine="Sub btnCancel_Click";
 //BA.debugLineNum = 832;BA.debugLine="SearchClosed";
_searchclosed();
 //BA.debugLineNum = 833;BA.debugLine="EnableControls";
_enablecontrols();
 //BA.debugLineNum = 834;BA.debugLine="snack.Initialize(\"\", Activity, \"Setting Branch wa";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),"Setting Branch was Cancelled!",mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 835;BA.debugLine="SetSnackBarBackground(snack,Colors.Red)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 836;BA.debugLine="SetSnackBarTextColor(snack,Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 837;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 838;BA.debugLine="End Sub";
return "";
}
public static String  _btnlogin_click() throws Exception{
 //BA.debugLineNum = 190;BA.debugLine="Sub btnLogin_Click";
 //BA.debugLineNum = 191;BA.debugLine="WiFiStatus = tglWifi.isWIFI_enabled";
_wifistatus = _tglwifi.isWIFI_enabled();
 //BA.debugLineNum = 193;BA.debugLine="If SF.Len(SF.Trim(txtUserName.Text)) <= 0 Then";
if (_sf._vvv7(_sf._vvvvvvv4(mostCurrent._txtusername._gettext /*String*/ ()))<=0) { 
 //BA.debugLineNum = 194;BA.debugLine="vibration.vibrateOnce(1000)";
mostCurrent._vibration.vibrateOnce(processBA,(long) (1000));
 //BA.debugLineNum = 195;BA.debugLine="UserWarningMsg($\"E R R O R\"$, $\"Oooops! User Nam";
_userwarningmsg(("E R R O R"),("Oooops! User Name not specified."));
 //BA.debugLineNum = 196;BA.debugLine="txtUserName.RequestFocusAndShowKeyboard";
mostCurrent._txtusername._requestfocusandshowkeyboard /*String*/ ();
 //BA.debugLineNum = 197;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 200;BA.debugLine="If DBaseFunctions.IsUserNameExists(txtUserName.Te";
if (mostCurrent._dbasefunctions._isusernameexists /*boolean*/ (mostCurrent.activityBA,mostCurrent._txtusername._gettext /*String*/ (),mostCurrent._globalvar._branchid /*int*/ )==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 201;BA.debugLine="If SF.Len(SF.Trim(txtPassword.Text)) <= 0 Then";
if (_sf._vvv7(_sf._vvvvvvv4(mostCurrent._txtpassword._gettext /*String*/ ()))<=0) { 
 //BA.debugLineNum = 202;BA.debugLine="vibration.vibrateOnce(1000)";
mostCurrent._vibration.vibrateOnce(processBA,(long) (1000));
 //BA.debugLineNum = 203;BA.debugLine="UserWarningMsg($\"E R R O R\"$, $\"Oooops! User Pa";
_userwarningmsg(("E R R O R"),("Oooops! User Password not specified."));
 //BA.debugLineNum = 204;BA.debugLine="txtPassword.RequestFocusAndShowKeyboard";
mostCurrent._txtpassword._requestfocusandshowkeyboard /*String*/ ();
 //BA.debugLineNum = 205;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 208;BA.debugLine="If Not(DBaseFunctions.IsPasswordCorrect(txtUserN";
if (anywheresoftware.b4a.keywords.Common.Not(mostCurrent._dbasefunctions._ispasswordcorrect /*boolean*/ (mostCurrent.activityBA,mostCurrent._txtusername._gettext /*String*/ (),mostCurrent._txtpassword._gettext /*String*/ (),mostCurrent._globalvar._branchid /*int*/ ))) { 
 //BA.debugLineNum = 209;BA.debugLine="vibration.vibrateOnce(1000)";
mostCurrent._vibration.vibrateOnce(processBA,(long) (1000));
 //BA.debugLineNum = 210;BA.debugLine="UserWarningMsg($\"E R R O R\"$, $\"Oooops! User Pa";
_userwarningmsg(("E R R O R"),("Oooops! User Password is incorrect."));
 //BA.debugLineNum = 211;BA.debugLine="txtPassword.Text = \"\"";
mostCurrent._txtpassword._settext /*String*/ ("");
 //BA.debugLineNum = 212;BA.debugLine="txtPassword.RequestFocusAndShowKeyboard";
mostCurrent._txtpassword._requestfocusandshowkeyboard /*String*/ ();
 //BA.debugLineNum = 213;BA.debugLine="Return";
if (true) return "";
 };
 };
 //BA.debugLineNum = 217;BA.debugLine="ProgressDialogShow2($\"Checking User's Credentials";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence(("Checking User's Credentials.")+anywheresoftware.b4a.keywords.Common.CRLF+("Please Wait...")),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 219;BA.debugLine="If DBaseFunctions.IsUserExists(txtUserName.Text,";
if (mostCurrent._dbasefunctions._isuserexists /*boolean*/ (mostCurrent.activityBA,mostCurrent._txtusername._gettext /*String*/ (),mostCurrent._txtpassword._gettext /*String*/ (),mostCurrent._globalvar._branchid /*int*/ )==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 220;BA.debugLine="ProgressDialogShow2($\"User's found. Fetching Use";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence(("User's found. Fetching User's Info...")),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 222;BA.debugLine="If DBaseFunctions.IsThereUserInfo(txtUserName.Te";
if (mostCurrent._dbasefunctions._isthereuserinfo /*boolean*/ (mostCurrent.activityBA,mostCurrent._txtusername._gettext /*String*/ (),mostCurrent._txtpassword._gettext /*String*/ ())==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 223;BA.debugLine="DBaseFunctions.GetBranchInfo(GlobalVar.BranchID";
mostCurrent._dbasefunctions._getbranchinfo /*String*/ (mostCurrent.activityBA,mostCurrent._globalvar._branchid /*int*/ );
 }else {
 //BA.debugLineNum = 225;BA.debugLine="vibration.vibrateOnce(1000)";
mostCurrent._vibration.vibrateOnce(processBA,(long) (1000));
 //BA.debugLineNum = 226;BA.debugLine="UserWarningMsg($\"E R R O R\"$, $\"Unable to fetch";
_userwarningmsg(("E R R O R"),("Unable to fetch User's Information due to incomplete credentials!"));
 //BA.debugLineNum = 230;BA.debugLine="txtPassword.Text = \"\"";
mostCurrent._txtpassword._settext /*String*/ ("");
 //BA.debugLineNum = 231;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 234;BA.debugLine="If DBaseFunctions.GetBillSettings(GlobalVar.Bran";
if (mostCurrent._dbasefunctions._getbillsettings /*boolean*/ (mostCurrent.activityBA,mostCurrent._globalvar._branchid /*int*/ )==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 235;BA.debugLine="vibration.vibrateOnce(1000)";
mostCurrent._vibration.vibrateOnce(processBA,(long) (1000));
 //BA.debugLineNum = 236;BA.debugLine="UserWarningMsg($\"E R R O R\"$, $\"Billing Setting";
_userwarningmsg(("E R R O R"),("Billing Settings not initializes!")+anywheresoftware.b4a.keywords.Common.CRLF+("Please contact your Android Developer for assistance."));
 //BA.debugLineNum = 238;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 241;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 242;BA.debugLine="CustomFunctions.SaveUserSettings";
mostCurrent._customfunctions._saveusersettings /*String*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 243;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 244;BA.debugLine="StartActivity(MainScreen)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._mainscreen.getObject()));
 }else {
 //BA.debugLineNum = 246;BA.debugLine="If WiFiStatus = False Then";
if (_wifistatus==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 252;BA.debugLine="ShowWiFiWarning($\"Enable WiFi\"$,$\"Please Turn O";
_showwifiwarning(("Enable WiFi"),("Please Turn On WiFi device")+anywheresoftware.b4a.keywords.Common.CRLF+("to Access the Elite Server!"));
 //BA.debugLineNum = 253;BA.debugLine="Return";
if (true) return "";
 }else {
 //BA.debugLineNum = 255;BA.debugLine="If GlobalVar.ServerAddress = Null Or GlobalVar.";
if (mostCurrent._globalvar._serveraddress /*String*/ == null || (mostCurrent._globalvar._serveraddress /*String*/ ).equals("")) { 
 //BA.debugLineNum = 256;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 257;BA.debugLine="vibration.vibrateOnce(1000)";
mostCurrent._vibration.vibrateOnce(processBA,(long) (1000));
 //BA.debugLineNum = 258;BA.debugLine="snack.Initialize(\"SetServer\", Activity,\"Server";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"SetServer",(android.view.View)(mostCurrent._activity.getObject()),"Server IP not configured...",mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 259;BA.debugLine="SetSnackBarBackground(snack,Colors.Red)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 260;BA.debugLine="SetSnackBarTextColor(snack,Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 261;BA.debugLine="snack.SetAction(\"Configure\")";
mostCurrent._snack.SetAction("Configure");
 //BA.debugLineNum = 262;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 263;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 266;BA.debugLine="ProgressDialogShow2($\"Checking Database Server";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence(("Checking Database Server Connection.")+anywheresoftware.b4a.keywords.Common.CRLF+("Please Wait...")),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 268;BA.debugLine="GetUserAccount(GlobalVar.BranchID, txtUserName.";
_getuseraccount(mostCurrent._globalvar._branchid /*int*/ ,mostCurrent._txtusername._gettext /*String*/ (),mostCurrent._txtpassword._gettext /*String*/ ());
 };
 };
 //BA.debugLineNum = 271;BA.debugLine="End Sub";
return "";
}
public static String  _btnsettings_click() throws Exception{
 //BA.debugLineNum = 384;BA.debugLine="Sub btnSettings_Click";
 //BA.debugLineNum = 385;BA.debugLine="intSettingsFlag = 1";
_intsettingsflag = (int) (1);
 //BA.debugLineNum = 386;BA.debugLine="ShowIPSettings";
_showipsettings();
 //BA.debugLineNum = 387;BA.debugLine="End Sub";
return "";
}
public static String  _cbobranches_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 646;BA.debugLine="Sub cboBranches_ItemClick (Position As Int, Value";
 //BA.debugLineNum = 648;BA.debugLine="End Sub";
return "";
}
public static String  _checkpermissions() throws Exception{
 //BA.debugLineNum = 154;BA.debugLine="Private Sub CheckPermissions";
 //BA.debugLineNum = 159;BA.debugLine="Log(\"Checking Permissions\")";
anywheresoftware.b4a.keywords.Common.LogImpl("427983877","Checking Permissions",0);
 //BA.debugLineNum = 161;BA.debugLine="Permissions.CheckAndRequest(Permissions.PERMISSIO";
_permissions.CheckAndRequest(processBA,_permissions.PERMISSION_READ_EXTERNAL_STORAGE);
 //BA.debugLineNum = 162;BA.debugLine="Permissions.CheckAndRequest(Permissions.PERMISSIO";
_permissions.CheckAndRequest(processBA,_permissions.PERMISSION_WRITE_EXTERNAL_STORAGE);
 //BA.debugLineNum = 163;BA.debugLine="Permissions.CheckAndRequest(Permissions.PERMISSIO";
_permissions.CheckAndRequest(processBA,_permissions.PERMISSION_ACCESS_COARSE_LOCATION);
 //BA.debugLineNum = 164;BA.debugLine="Permissions.GetAllSafeDirsExternal(\"\")";
_permissions.GetAllSafeDirsExternal("");
 //BA.debugLineNum = 166;BA.debugLine="Permissions.CheckAndRequest(Permissions.PERMISSIO";
_permissions.CheckAndRequest(processBA,_permissions.PERMISSION_CAMERA);
 //BA.debugLineNum = 167;BA.debugLine="Permissions.CheckAndRequest(Permissions.PERMISSIO";
_permissions.CheckAndRequest(processBA,_permissions.PERMISSION_BODY_SENSORS);
 //BA.debugLineNum = 168;BA.debugLine="Return";
if (true) return "";
 //BA.debugLineNum = 170;BA.debugLine="End Sub";
return "";
}
public static String  _chkshowpass_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 182;BA.debugLine="Sub chkShowPass_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 183;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 }else {
 };
 //BA.debugLineNum = 188;BA.debugLine="End Sub";
return "";
}
public static String  _confirmexitapp(String _stitle,String _smsg) throws Exception{
 //BA.debugLineNum = 1072;BA.debugLine="Private Sub ConfirmExitApp(sTitle As String, sMsg";
 //BA.debugLineNum = 1073;BA.debugLine="vibration.vibrateOnce(1000)";
mostCurrent._vibration.vibrateOnce(processBA,(long) (1000));
 //BA.debugLineNum = 1075;BA.debugLine="Alert.Initialize.Create _ 			.SetDialogStyleName(";
mostCurrent._alert.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialogDisableStatus").SetStyle(mostCurrent._alert.getSTYLE_DIALOGUE()).SetCancelable(anywheresoftware.b4a.keywords.Common.False).SetTitle(_stitle).SetTitleTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessage(_smsg).SetPositiveText("Confirm").SetPositiveColor(anywheresoftware.b4a.keywords.Common.Colors.Blue).SetPositiveTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetNegativeText("Cancel").SetNegativeColor(anywheresoftware.b4a.keywords.Common.Colors.Red).SetNegativeTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessageTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOnPositiveClicked(mostCurrent.activityBA,"ConfirmQuit").SetOnNegativeClicked(mostCurrent.activityBA,"ConfirmQuit").SetOnViewBinder(mostCurrent.activityBA,"QuitFontSizeBinder");
 //BA.debugLineNum = 1093;BA.debugLine="Alert.SetDialogBackground(myCD)";
mostCurrent._alert.SetDialogBackground((android.graphics.drawable.Drawable)(_mycd().getObject()));
 //BA.debugLineNum = 1094;BA.debugLine="Alert.Build.Show";
mostCurrent._alert.Build().Show();
 //BA.debugLineNum = 1096;BA.debugLine="End Sub";
return "";
}
public static String  _confirmquit_onnegativeclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
 //BA.debugLineNum = 1099;BA.debugLine="Private Sub ConfirmQuit_OnNegativeClicked (View As";
 //BA.debugLineNum = 1100;BA.debugLine="vibration.vibrateCancel";
mostCurrent._vibration.vibrateCancel(processBA);
 //BA.debugLineNum = 1101;BA.debugLine="Alert.Initialize.Dismiss(Dialog)";
mostCurrent._alert.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 1102;BA.debugLine="End Sub";
return "";
}
public static String  _confirmquit_onpositiveclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
 //BA.debugLineNum = 1104;BA.debugLine="Private Sub ConfirmQuit_OnPositiveClicked (View As";
 //BA.debugLineNum = 1106;BA.debugLine="vibration.vibrateCancel";
mostCurrent._vibration.vibrateCancel(processBA);
 //BA.debugLineNum = 1107;BA.debugLine="blnUserClosed = True";
_blnuserclosed = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 1111;BA.debugLine="CustomFunctions.ClearUserData";
mostCurrent._customfunctions._clearuserdata /*String*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 1112;BA.debugLine="ExitApplication";
anywheresoftware.b4a.keywords.Common.ExitApplication();
 //BA.debugLineNum = 1113;BA.debugLine="End Sub";
return "";
}
public static String  _confirmselectedbranch(String _smsg) throws Exception{
anywheresoftware.b4a.objects.CSBuilder _cstitle = null;
 //BA.debugLineNum = 869;BA.debugLine="Private Sub ConfirmSelectedBranch(sMsg As String)";
 //BA.debugLineNum = 870;BA.debugLine="Dim csTitle As CSBuilder";
_cstitle = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 872;BA.debugLine="csTitle.Initialize.Color(GlobalVar.PriColor).Bold";
_cstitle.Initialize().Color((int) (mostCurrent._globalvar._pricolor /*double*/ )).Bold().Size((int) (16)).Append(BA.ObjectToCharSequence(("Confirm Selection"))).PopAll();
 //BA.debugLineNum = 873;BA.debugLine="MatDialogBuilder.Initialize(\"SelectedBranch\")";
mostCurrent._matdialogbuilder.Initialize(mostCurrent.activityBA,"SelectedBranch");
 //BA.debugLineNum = 874;BA.debugLine="MatDialogBuilder.Title(csTitle)";
mostCurrent._matdialogbuilder.Title(BA.ObjectToCharSequence(_cstitle.getObject()));
 //BA.debugLineNum = 875;BA.debugLine="MatDialogBuilder.Content($\"Set \"$ & sMsg & $\" as";
mostCurrent._matdialogbuilder.Content(BA.ObjectToCharSequence(("Set ")+_smsg+(" as your branch?"))).ContentColor((int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 876;BA.debugLine="MatDialogBuilder.IconRes(GlobalVar.QuestionIcon).";
mostCurrent._matdialogbuilder.IconRes(mostCurrent._globalvar._questionicon /*String*/ ).LimitIconToDefaultSize();
 //BA.debugLineNum = 877;BA.debugLine="MatDialogBuilder.PositiveText($\"Save\"$).PositiveC";
mostCurrent._matdialogbuilder.PositiveText(BA.ObjectToCharSequence(("Save"))).PositiveColor((int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 878;BA.debugLine="MatDialogBuilder.NegativeText($\"Cancel\"$).Negativ";
mostCurrent._matdialogbuilder.NegativeText(BA.ObjectToCharSequence(("Cancel"))).NegativeColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 879;BA.debugLine="MatDialogBuilder.Cancelable(False)";
mostCurrent._matdialogbuilder.Cancelable(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 880;BA.debugLine="MatDialogBuilder.CanceledOnTouchOutside(False)";
mostCurrent._matdialogbuilder.CanceledOnTouchOutside(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 881;BA.debugLine="MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIG";
mostCurrent._matdialogbuilder.Theme(mostCurrent._matdialogbuilder.THEME_LIGHT);
 //BA.debugLineNum = 882;BA.debugLine="MatDialogBuilder.Build";
mostCurrent._matdialogbuilder.Build();
 //BA.debugLineNum = 883;BA.debugLine="MatDialogBuilder.Show";
mostCurrent._matdialogbuilder.Show();
 //BA.debugLineNum = 884;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.drawable.StateListDrawable  _createbuttoncolor(int _focusedcolor,int _enabledcolor,int _disabledcolor,int _pressedcolor) throws Exception{
anywheresoftware.b4a.objects.drawable.StateListDrawable _retcolor = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _drwfocusedcolor = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _drwenabledcolor = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _drwdisabledcolor = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _drwpressedcolor = null;
 //BA.debugLineNum = 840;BA.debugLine="Public Sub CreateButtonColor(FocusedColor As Int,";
 //BA.debugLineNum = 842;BA.debugLine="Dim RetColor As StateListDrawable";
_retcolor = new anywheresoftware.b4a.objects.drawable.StateListDrawable();
 //BA.debugLineNum = 843;BA.debugLine="RetColor.Initialize";
_retcolor.Initialize();
 //BA.debugLineNum = 844;BA.debugLine="Dim drwFocusedColor, drwEnabledColor, drwDisabled";
_drwfocusedcolor = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
_drwenabledcolor = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
_drwdisabledcolor = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
_drwpressedcolor = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 852;BA.debugLine="drwFocusedColor.Initialize2(FocusedColor, 25, 0,";
_drwfocusedcolor.Initialize2(_focusedcolor,(int) (25),(int) (0),anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 853;BA.debugLine="drwEnabledColor.Initialize2(EnabledColor, 25, 0,";
_drwenabledcolor.Initialize2(_enabledcolor,(int) (25),(int) (0),anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 854;BA.debugLine="drwDisabledColor.Initialize2(DisabledColor, 25, 2";
_drwdisabledcolor.Initialize2(_disabledcolor,(int) (25),(int) (2),anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 855;BA.debugLine="drwPressedColor.Initialize2(PressedColor, 25, 0,";
_drwpressedcolor.Initialize2(_pressedcolor,(int) (25),(int) (0),anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 857;BA.debugLine="RetColor.AddState(RetColor.State_Focused, drwFocu";
_retcolor.AddState(_retcolor.State_Focused,(android.graphics.drawable.Drawable)(_drwfocusedcolor.getObject()));
 //BA.debugLineNum = 858;BA.debugLine="RetColor.AddState(RetColor.State_Pressed, drwPres";
_retcolor.AddState(_retcolor.State_Pressed,(android.graphics.drawable.Drawable)(_drwpressedcolor.getObject()));
 //BA.debugLineNum = 859;BA.debugLine="RetColor.AddState(RetColor.State_Enabled, drwEnab";
_retcolor.AddState(_retcolor.State_Enabled,(android.graphics.drawable.Drawable)(_drwenabledcolor.getObject()));
 //BA.debugLineNum = 860;BA.debugLine="RetColor.AddState(RetColor.State_Disabled, drwDis";
_retcolor.AddState(_retcolor.State_Disabled,(android.graphics.drawable.Drawable)(_drwdisabledcolor.getObject()));
 //BA.debugLineNum = 861;BA.debugLine="RetColor.AddCatchAllState(drwFocusedColor)";
_retcolor.AddCatchAllState((android.graphics.drawable.Drawable)(_drwfocusedcolor.getObject()));
 //BA.debugLineNum = 862;BA.debugLine="RetColor.AddCatchAllState(drwEnabledColor)";
_retcolor.AddCatchAllState((android.graphics.drawable.Drawable)(_drwenabledcolor.getObject()));
 //BA.debugLineNum = 863;BA.debugLine="RetColor.AddCatchAllState(drwDisabledColor)";
_retcolor.AddCatchAllState((android.graphics.drawable.Drawable)(_drwdisabledcolor.getObject()));
 //BA.debugLineNum = 864;BA.debugLine="RetColor.AddCatchAllState(drwPressedColor)";
_retcolor.AddCatchAllState((android.graphics.drawable.Drawable)(_drwpressedcolor.getObject()));
 //BA.debugLineNum = 865;BA.debugLine="Return RetColor";
if (true) return _retcolor;
 //BA.debugLineNum = 867;BA.debugLine="End Sub";
return null;
}
public static String  _decrypttext(byte[] _encrypteddata,String _password) throws Exception{
anywheresoftware.b4a.object.B4XEncryption _c = null;
byte[] _b = null;
 //BA.debugLineNum = 553;BA.debugLine="Sub DecryptText(EncryptedData() As Byte, password";
 //BA.debugLineNum = 554;BA.debugLine="Dim c As B4XCipher";
_c = new anywheresoftware.b4a.object.B4XEncryption();
 //BA.debugLineNum = 555;BA.debugLine="Dim b() As Byte = c.Decrypt(EncryptedData, passwo";
_b = _c.Decrypt(_encrypteddata,_password);
 //BA.debugLineNum = 556;BA.debugLine="Return BytesToString(b, 0, b.Length, \"utf8\")";
if (true) return anywheresoftware.b4a.keywords.Common.BytesToString(_b,(int) (0),_b.length,"utf8");
 //BA.debugLineNum = 557;BA.debugLine="End Sub";
return "";
}
public static String  _disablecontrols() throws Exception{
 //BA.debugLineNum = 752;BA.debugLine="Sub DisableControls";
 //BA.debugLineNum = 755;BA.debugLine="btnLogin.Enabled = False";
mostCurrent._btnlogin.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 756;BA.debugLine="chkShowPass.Enabled = False";
mostCurrent._chkshowpass.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 757;BA.debugLine="btnSettings.Enabled = False";
mostCurrent._btnsettings.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 758;BA.debugLine="btnBranch.Enabled = False";
mostCurrent._btnbranch.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 759;BA.debugLine="btnCancel.Enabled = True";
mostCurrent._btncancel.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 760;BA.debugLine="End Sub";
return "";
}
public static String  _disperrormsg(String _smsg,String _stitle) throws Exception{
anywheresoftware.b4a.objects.CSBuilder _cstitle = null;
anywheresoftware.b4a.objects.CSBuilder _csmsg = null;
 //BA.debugLineNum = 638;BA.debugLine="Private Sub DispErrorMsg(sMsg As String, sTitle As";
 //BA.debugLineNum = 639;BA.debugLine="Dim csTitle, csMsg As CSBuilder";
_cstitle = new anywheresoftware.b4a.objects.CSBuilder();
_csmsg = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 640;BA.debugLine="csTitle.Initialize.Color(Colors.Red).Append(sTitl";
_cstitle.Initialize().Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(_stitle)).PopAll();
 //BA.debugLineNum = 641;BA.debugLine="csMsg.Initialize.Bold.Color(Colors.DarkGray).Appe";
_csmsg.Initialize().Bold().Color(anywheresoftware.b4a.keywords.Common.Colors.DarkGray).Append(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.CRLF+_smsg)).PopAll();
 //BA.debugLineNum = 643;BA.debugLine="Msgbox(csMsg, csTitle)";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence(_csmsg.getObject()),BA.ObjectToCharSequence(_cstitle.getObject()),mostCurrent.activityBA);
 //BA.debugLineNum = 644;BA.debugLine="End Sub";
return "";
}
public static void  _downloadreaders(int _ibranchid) throws Exception{
ResumableSub_DownloadReaders rsub = new ResumableSub_DownloadReaders(null,_ibranchid);
rsub.resume(processBA, null);
}
public static class ResumableSub_DownloadReaders extends BA.ResumableSub {
public ResumableSub_DownloadReaders(com.bwsi.MeterReader.login parent,int _ibranchid) {
this.parent = parent;
this._ibranchid = _ibranchid;
}
com.bwsi.MeterReader.login parent;
int _ibranchid;
String _sretval = "";
anywheresoftware.b4a.objects.collections.List _meterreaderlist = null;
anywheresoftware.b4a.objects.collections.JSONParser _jsonpar = null;
anywheresoftware.b4a.objects.collections.Map _mpfield = null;
anywheresoftware.b4a.samples.httputils2.httpjob _j = null;
int _i = 0;
int step26;
int limit26;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
try {

        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 560;BA.debugLine="Dim sRetVal As String";
_sretval = "";
 //BA.debugLineNum = 561;BA.debugLine="Dim MeterReaderList As List";
_meterreaderlist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 562;BA.debugLine="Dim jSonPar As JSONParser";
_jsonpar = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 563;BA.debugLine="Dim mpField As Map";
_mpfield = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 565;BA.debugLine="Try";
if (true) break;

case 1:
//try
this.state = 26;
this.catchState = 25;
this.state = 3;
if (true) break;

case 3:
//C
this.state = 4;
this.catchState = 25;
 //BA.debugLineNum = 566;BA.debugLine="URLName = GlobalVar.ServerAddress & GlobalVar.Co";
parent.mostCurrent._urlname = parent.mostCurrent._globalvar._serveraddress /*String*/ +parent.mostCurrent._globalvar._controllerprefix /*String*/ +"getReaders";
 //BA.debugLineNum = 567;BA.debugLine="Job1.Initialize(\"\", Me)";
parent.mostCurrent._job1._initialize(processBA,"",login.getObject());
 //BA.debugLineNum = 568;BA.debugLine="Job1.Download2(URLName, Array As String(\"BranchI";
parent.mostCurrent._job1._download2(parent.mostCurrent._urlname,new String[]{"BranchID",BA.NumberToString(_ibranchid)});
 //BA.debugLineNum = 570;BA.debugLine="sURL = URLName & \"?BranchID=\" & GlobalVar.Branch";
parent.mostCurrent._surl = parent.mostCurrent._urlname+"?BranchID="+BA.NumberToString(parent.mostCurrent._globalvar._branchid /*int*/ );
 //BA.debugLineNum = 571;BA.debugLine="LogColor(sURL, Colors.Red)";
anywheresoftware.b4a.keywords.Common.LogImpl("429491212",parent.mostCurrent._surl,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 573;BA.debugLine="Wait For (Job1) JobDone(j As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(parent.mostCurrent._job1));
this.state = 27;
return;
case 27:
//C
this.state = 4;
_j = (anywheresoftware.b4a.samples.httputils2.httpjob) result[0];
;
 //BA.debugLineNum = 574;BA.debugLine="If j.Success Then";
if (true) break;

case 4:
//if
this.state = 23;
if (_j._success) { 
this.state = 6;
}else {
this.state = 22;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 575;BA.debugLine="sRetVal = j.GetString";
_sretval = _j._getstring();
 //BA.debugLineNum = 576;BA.debugLine="jSonPar.Initialize(sRetVal)";
_jsonpar.Initialize(_sretval);
 //BA.debugLineNum = 577;BA.debugLine="Log(sRetVal)";
anywheresoftware.b4a.keywords.Common.LogImpl("429491218",_sretval,0);
 //BA.debugLineNum = 578;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 580;BA.debugLine="If sRetVal = \"[]\" Then";
if (true) break;

case 7:
//if
this.state = 10;
if ((_sretval).equals("[]")) { 
this.state = 9;
}if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 581;BA.debugLine="j.Release";
_j._release();
 //BA.debugLineNum = 582;BA.debugLine="xui.MsgboxAsync($\"Either User Name or Password";
parent.mostCurrent._xui.MsgboxAsync(processBA,BA.ObjectToCharSequence(("Either User Name or Password is Incorrect!")+anywheresoftware.b4a.keywords.Common.CRLF+("Please try again.")),BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.Application.getLabelName()));
 //BA.debugLineNum = 585;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 10:
//C
this.state = 11;
;
 //BA.debugLineNum = 588;BA.debugLine="Starter.DBCon.BeginTransaction";
parent.mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .BeginTransaction();
 //BA.debugLineNum = 589;BA.debugLine="Try";
if (true) break;

case 11:
//try
this.state = 20;
this.catchState = 19;
this.state = 13;
if (true) break;

case 13:
//C
this.state = 14;
this.catchState = 19;
 //BA.debugLineNum = 590;BA.debugLine="MeterReaderList.Initialize";
_meterreaderlist.Initialize();
 //BA.debugLineNum = 591;BA.debugLine="MeterReaderList = jSonPar.NextArray";
_meterreaderlist = _jsonpar.NextArray();
 //BA.debugLineNum = 592;BA.debugLine="For i = 0 To MeterReaderList.Size - 1";
if (true) break;

case 14:
//for
this.state = 17;
step26 = 1;
limit26 = (int) (_meterreaderlist.getSize()-1);
_i = (int) (0) ;
this.state = 28;
if (true) break;

case 28:
//C
this.state = 17;
if ((step26 > 0 && _i <= limit26) || (step26 < 0 && _i >= limit26)) this.state = 16;
if (true) break;

case 29:
//C
this.state = 28;
_i = ((int)(0 + _i + step26)) ;
if (true) break;

case 16:
//C
this.state = 29;
 //BA.debugLineNum = 593;BA.debugLine="mpField.Initialize";
_mpfield.Initialize();
 //BA.debugLineNum = 594;BA.debugLine="mpField = MeterReaderList.Get(i)";
_mpfield = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(_meterreaderlist.Get(_i)));
 //BA.debugLineNum = 595;BA.debugLine="Starter.strCriteria = \"INSERT INTO tblReaders";
parent.mostCurrent._starter._strcriteria /*String*/  = "INSERT INTO tblReaders VALUES(?, ?, ?)";
 //BA.debugLineNum = 596;BA.debugLine="Starter.DBCon.ExecNonQuery2(Starter.strCriter";
parent.mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2(parent.mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{BA.ObjectToString(_mpfield.Get((Object)("ReaderID"))),_titlecase(BA.ObjectToString(_mpfield.Get((Object)("ReaderName")))),("0")}));
 if (true) break;
if (true) break;

case 17:
//C
this.state = 20;
;
 //BA.debugLineNum = 598;BA.debugLine="Starter.DBCon.TransactionSuccessful";
parent.mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .TransactionSuccessful();
 //BA.debugLineNum = 599;BA.debugLine="j.Release";
_j._release();
 if (true) break;

case 19:
//C
this.state = 20;
this.catchState = 25;
 //BA.debugLineNum = 601;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("429491242",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 //BA.debugLineNum = 602;BA.debugLine="ToastMessageShow($\"Unable to connect to Databa";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to connect to Database Server due to ")+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 if (true) break;
if (true) break;

case 20:
//C
this.state = 23;
this.catchState = 25;
;
 //BA.debugLineNum = 604;BA.debugLine="Starter.DBCon.EndTransaction";
parent.mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .EndTransaction();
 //BA.debugLineNum = 605;BA.debugLine="CustomFunctions.SaveUserSettings";
parent.mostCurrent._customfunctions._saveusersettings /*String*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 606;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 607;BA.debugLine="StartActivity(MainScreen)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(parent.mostCurrent._mainscreen.getObject()));
 if (true) break;

case 22:
//C
this.state = 23;
 //BA.debugLineNum = 609;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 610;BA.debugLine="ToastMessageShow($\"Unable to Fetch Meter Reader";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to Fetch Meter Readers due to ")+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage()),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 611;BA.debugLine="j.Release";
_j._release();
 if (true) break;

case 23:
//C
this.state = 26;
;
 if (true) break;

case 25:
//C
this.state = 26;
this.catchState = 0;
 //BA.debugLineNum = 614;BA.debugLine="ToastMessageShow($\"Unable to check Download Mete";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to check Download Meter Readers to ")+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 615;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("429491256",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 //BA.debugLineNum = 616;BA.debugLine="j.Release";
_j._release();
 if (true) break;
if (true) break;

case 26:
//C
this.state = -1;
this.catchState = 0;
;
 //BA.debugLineNum = 618;BA.debugLine="End Sub";
if (true) break;
}} 
       catch (Exception e0) {
			
if (catchState == 0)
    throw e0;
else {
    state = catchState;
processBA.setLastException(e0);}
            }
        }
    }
}
public static void  _jobdone(anywheresoftware.b4a.samples.httputils2.httpjob _j) throws Exception{
}
public static String  _enablecontrols() throws Exception{
 //BA.debugLineNum = 762;BA.debugLine="Sub EnableControls";
 //BA.debugLineNum = 765;BA.debugLine="btnLogin.Enabled = True";
mostCurrent._btnlogin.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 766;BA.debugLine="chkShowPass.Enabled = True";
mostCurrent._chkshowpass.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 767;BA.debugLine="btnSettings.Enabled = True";
mostCurrent._btnsettings.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 768;BA.debugLine="btnBranch.Enabled = True";
mostCurrent._btnbranch.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 769;BA.debugLine="End Sub";
return "";
}
public static byte[]  _encrypttext(String _text,String _password) throws Exception{
anywheresoftware.b4a.object.B4XEncryption _c = null;
 //BA.debugLineNum = 548;BA.debugLine="Sub EncryptText(text As String, password As String";
 //BA.debugLineNum = 549;BA.debugLine="Dim c As B4XCipher";
_c = new anywheresoftware.b4a.object.B4XEncryption();
 //BA.debugLineNum = 550;BA.debugLine="Return c.Encrypt(text.GetBytes(\"utf8\"), password)";
if (true) return _c.Encrypt(_text.getBytes("utf8"),_password);
 //BA.debugLineNum = 551;BA.debugLine="End Sub";
return null;
}
public static String  _fillbranches() throws Exception{
int _i = 0;
 //BA.debugLineNum = 690;BA.debugLine="Private Sub FillBranches";
 //BA.debugLineNum = 691;BA.debugLine="Try";
try { //BA.debugLineNum = 692;BA.debugLine="cboBranches.Clear";
mostCurrent._cbobranches.Clear();
 //BA.debugLineNum = 693;BA.debugLine="Starter.strCriteria = \"SELECT * FROM tblBranches";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblBranches ORDER BY CompanyID ASC, BranchName";
 //BA.debugLineNum = 694;BA.debugLine="rsBranches = Starter.DBCon.ExecQuery(Starter.str";
mostCurrent._rsbranches = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 695;BA.debugLine="If rsBranches.RowCount > 0 Then";
if (mostCurrent._rsbranches.getRowCount()>0) { 
 //BA.debugLineNum = 696;BA.debugLine="For i = 0 To rsBranches.RowCount - 1";
{
final int step6 = 1;
final int limit6 = (int) (mostCurrent._rsbranches.getRowCount()-1);
_i = (int) (0) ;
for (;_i <= limit6 ;_i = _i + step6 ) {
 //BA.debugLineNum = 697;BA.debugLine="rsBranches.Position = i";
mostCurrent._rsbranches.setPosition(_i);
 //BA.debugLineNum = 698;BA.debugLine="cboBranches.Add(rsBranches.GetString(\"BranchNa";
mostCurrent._cbobranches.Add(mostCurrent._rsbranches.GetString("BranchName"));
 }
};
 };
 } 
       catch (Exception e12) {
			processBA.setLastException(e12); //BA.debugLineNum = 702;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("429949964",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 704;BA.debugLine="End Sub";
return "";
}
public static String  _getbgimage(int _icompanyid) throws Exception{
 //BA.debugLineNum = 722;BA.debugLine="Private Sub GetBGImage(iCompanyID As Int)";
 //BA.debugLineNum = 723;BA.debugLine="Select Case iCompanyID";
switch (_icompanyid) {
case 0: {
 //BA.debugLineNum = 725;BA.debugLine="Activity.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._activity.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"bwsi-login.png").getObject()));
 break; }
case 1: {
 //BA.debugLineNum = 727;BA.debugLine="Activity.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._activity.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"bwsi-login.png").getObject()));
 break; }
case 2: {
 //BA.debugLineNum = 729;BA.debugLine="Activity.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._activity.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"clpi-login.png").getObject()));
 break; }
case 3: {
 //BA.debugLineNum = 731;BA.debugLine="Activity.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._activity.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"hwri-login.png").getObject()));
 break; }
case 4: {
 //BA.debugLineNum = 733;BA.debugLine="Activity.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._activity.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"bcbi-login.png").getObject()));
 break; }
case 5: {
 //BA.debugLineNum = 735;BA.debugLine="Activity.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._activity.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"biri-login.png").getObject()));
 break; }
case 6: {
 //BA.debugLineNum = 737;BA.debugLine="Activity.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._activity.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"lbwi-login.png").getObject()));
 break; }
case 7: {
 //BA.debugLineNum = 739;BA.debugLine="Activity.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._activity.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"sbri-login.png").getObject()));
 break; }
case 8: {
 //BA.debugLineNum = 741;BA.debugLine="Activity.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._activity.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mr3h-login.png").getObject()));
 break; }
case 9: {
 //BA.debugLineNum = 743;BA.debugLine="Activity.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._activity.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"sd3h-login.png").getObject()));
 break; }
case 10: {
 //BA.debugLineNum = 745;BA.debugLine="Activity.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._activity.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"clpi2-login.png").getObject()));
 break; }
default: {
 //BA.debugLineNum = 747;BA.debugLine="Activity.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._activity.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"login.png").getObject()));
 break; }
}
;
 //BA.debugLineNum = 750;BA.debugLine="End Sub";
return "";
}
public static String  _getgatewayip() throws Exception{
anywheresoftware.b4j.object.JavaObject _ctxt = null;
String _sretip = "";
anywheresoftware.b4j.object.JavaObject _wifimanager = null;
anywheresoftware.b4j.object.JavaObject _dhcp = null;
anywheresoftware.b4j.object.JavaObject _formatter = null;
 //BA.debugLineNum = 898;BA.debugLine="Sub GetGatewayIp As String";
 //BA.debugLineNum = 899;BA.debugLine="Dim ctxt As JavaObject";
_ctxt = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 900;BA.debugLine="Dim sRetIP As String";
_sretip = "";
 //BA.debugLineNum = 901;BA.debugLine="ctxt.InitializeContext";
_ctxt.InitializeContext(processBA);
 //BA.debugLineNum = 902;BA.debugLine="Dim WifiManager As JavaObject = ctxt.RunMethod(\"g";
_wifimanager = new anywheresoftware.b4j.object.JavaObject();
_wifimanager = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(_ctxt.RunMethod("getSystemService",new Object[]{_ctxt.GetField("WIFI_SERVICE")})));
 //BA.debugLineNum = 903;BA.debugLine="Dim dhcp As JavaObject = WifiManager.RunMethod(\"g";
_dhcp = new anywheresoftware.b4j.object.JavaObject();
_dhcp = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(_wifimanager.RunMethod("getDhcpInfo",(Object[])(anywheresoftware.b4a.keywords.Common.Null))));
 //BA.debugLineNum = 904;BA.debugLine="Dim formatter As JavaObject";
_formatter = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 905;BA.debugLine="sRetIP =  formatter.InitializeStatic(\"android.tex";
_sretip = BA.ObjectToString(_formatter.InitializeStatic("android.text.format.Formatter").RunMethod("formatIpAddress",new Object[]{_dhcp.GetField("gateway")}));
 //BA.debugLineNum = 906;BA.debugLine="LogColor($\"Returned IP:\"$ & sRetIP, Colors.Yellow";
anywheresoftware.b4a.keywords.Common.LogImpl("430736392",("Returned IP:")+_sretip,anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 //BA.debugLineNum = 907;BA.debugLine="Return sRetIP";
if (true) return _sretip;
 //BA.debugLineNum = 908;BA.debugLine="End Sub";
return "";
}
public static void  _getuseraccount(int _ibranchid,String _susername,String _suserpassword) throws Exception{
ResumableSub_GetUserAccount rsub = new ResumableSub_GetUserAccount(null,_ibranchid,_susername,_suserpassword);
rsub.resume(processBA, null);
}
public static class ResumableSub_GetUserAccount extends BA.ResumableSub {
public ResumableSub_GetUserAccount(com.bwsi.MeterReader.login parent,int _ibranchid,String _susername,String _suserpassword) {
this.parent = parent;
this._ibranchid = _ibranchid;
this._susername = _susername;
this._suserpassword = _suserpassword;
}
com.bwsi.MeterReader.login parent;
int _ibranchid;
String _susername;
String _suserpassword;
String _retval = "";
anywheresoftware.b4a.samples.httputils2.httpjob _j = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 274;BA.debugLine="Dim URLName As String";
parent.mostCurrent._urlname = "";
 //BA.debugLineNum = 275;BA.debugLine="Dim RetVal As String";
_retval = "";
 //BA.debugLineNum = 277;BA.debugLine="Dim j As HttpJob";
_j = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 279;BA.debugLine="j.Initialize(\"\",Me)";
_j._initialize(processBA,"",login.getObject());
 //BA.debugLineNum = 280;BA.debugLine="URLName = GlobalVar.ServerAddress & GlobalVar.Con";
parent.mostCurrent._urlname = parent.mostCurrent._globalvar._serveraddress /*String*/ +parent.mostCurrent._globalvar._controllerprefix /*String*/ +"userAccount";
 //BA.debugLineNum = 281;BA.debugLine="sURL = URLName & \"?BranchID=\" & iBranchID & \"&Use";
parent.mostCurrent._surl = parent.mostCurrent._urlname+"?BranchID="+BA.NumberToString(_ibranchid)+"&UserName="+_susername+"&UserPassword="+_suserpassword;
 //BA.debugLineNum = 282;BA.debugLine="LogColor(sURL, Colors.Blue)";
anywheresoftware.b4a.keywords.Common.LogImpl("428246025",parent.mostCurrent._surl,anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 284;BA.debugLine="j.Download2(URLName, Array As String(\"BranchID\",";
_j._download2(parent.mostCurrent._urlname,new String[]{"BranchID",BA.NumberToString(_ibranchid),"UserName",_susername,"UserPassword",_suserpassword});
 //BA.debugLineNum = 286;BA.debugLine="ProgressDialogShow2($\"Downloading User Informatio";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence(("Downloading User Information...")),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 287;BA.debugLine="Wait For (j) JobDone(j As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_j));
this.state = 11;
return;
case 11:
//C
this.state = 1;
_j = (anywheresoftware.b4a.samples.httputils2.httpjob) result[0];
;
 //BA.debugLineNum = 288;BA.debugLine="If j.Success Then";
if (true) break;

case 1:
//if
this.state = 10;
if (_j._success) { 
this.state = 3;
}else {
this.state = 9;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 289;BA.debugLine="RetVal = j.GetString";
_retval = _j._getstring();
 //BA.debugLineNum = 290;BA.debugLine="Log(RetVal)";
anywheresoftware.b4a.keywords.Common.LogImpl("428246033",_retval,0);
 //BA.debugLineNum = 292;BA.debugLine="If RetVal = \"[]\" Then";
if (true) break;

case 4:
//if
this.state = 7;
if ((_retval).equals("[]")) { 
this.state = 6;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 293;BA.debugLine="snack.Initialize(\"\", Activity, $\"\"Either User N";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(parent.mostCurrent._activity.getObject()),("\"Either User Name or Password is Incorrect!"),parent.mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 294;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 295;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 296;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 297;BA.debugLine="j.Release";
_j._release();
 //BA.debugLineNum = 298;BA.debugLine="txtUserName.RequestFocusAndShowKeyboard";
parent.mostCurrent._txtusername._requestfocusandshowkeyboard /*String*/ ();
 //BA.debugLineNum = 299;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 7:
//C
this.state = 10;
;
 //BA.debugLineNum = 302;BA.debugLine="SaveUserInfo(RetVal)";
_saveuserinfo(_retval);
 if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 304;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 305;BA.debugLine="j.Release";
_j._release();
 //BA.debugLineNum = 306;BA.debugLine="vibration.vibrateOnce(2000)";
parent.mostCurrent._vibration.vibrateOnce(processBA,(long) (2000));
 //BA.debugLineNum = 307;BA.debugLine="snack.Initialize(\"\",Activity,$\"Unable to Fetch U";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(parent.mostCurrent._activity.getObject()),("Unable to Fetch User Information due to ")+_j._errormessage,parent.mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 308;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 309;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 310;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 311;BA.debugLine="LogColor(j.ErrorMessage, Colors.Red)";
anywheresoftware.b4a.keywords.Common.LogImpl("428246054",_j._errormessage,anywheresoftware.b4a.keywords.Common.Colors.Red);
 if (true) break;

case 10:
//C
this.state = -1;
;
 //BA.debugLineNum = 313;BA.debugLine="j.Release";
_j._release();
 //BA.debugLineNum = 314;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 315;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 17;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 21;BA.debugLine="Private btnLogin As ACButton";
mostCurrent._btnlogin = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private chkShowPass As CheckBox";
mostCurrent._chkshowpass = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private txtPassword As B4XFloatTextField";
mostCurrent._txtpassword = new com.bwsi.MeterReader.b4xfloattextfield();
 //BA.debugLineNum = 26;BA.debugLine="Dim Users As theUser";
mostCurrent._users = new com.bwsi.MeterReader.globalvar._theuser();
 //BA.debugLineNum = 27;BA.debugLine="Dim UserBranch As theBranch";
mostCurrent._userbranch = new com.bwsi.MeterReader.globalvar._thebranch();
 //BA.debugLineNum = 28;BA.debugLine="Dim Companies As theCompany";
mostCurrent._companies = new com.bwsi.MeterReader.globalvar._thecompany();
 //BA.debugLineNum = 30;BA.debugLine="Dim URLName As String";
mostCurrent._urlname = "";
 //BA.debugLineNum = 31;BA.debugLine="Dim Job1 As HttpJob";
mostCurrent._job1 = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 33;BA.debugLine="Private btnSettings As DSFloatingActionButton";
mostCurrent._btnsettings = new de.amberhome.objects.FloatingActionButtonWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private MatDialogBuilder As MaterialDialogBuilder";
mostCurrent._matdialogbuilder = new de.amberhome.materialdialogs.MaterialDialogBuilderWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private strPassword As String";
mostCurrent._strpassword = "";
 //BA.debugLineNum = 36;BA.debugLine="Dim snack As DSSnackbar";
mostCurrent._snack = new de.amberhome.objects.SnackbarWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Dim NewIP As String";
mostCurrent._newip = "";
 //BA.debugLineNum = 38;BA.debugLine="Dim CD As ColorDrawable";
mostCurrent._cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 40;BA.debugLine="Dim xui As XUI";
mostCurrent._xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 41;BA.debugLine="Private vibration As B4Avibrate";
mostCurrent._vibration = new com.johan.Vibrate.Vibrate();
 //BA.debugLineNum = 42;BA.debugLine="Private vibratePattern() As Long";
_vibratepattern = new long[(int) (0)];
;
 //BA.debugLineNum = 45;BA.debugLine="Private rsBranches As Cursor";
mostCurrent._rsbranches = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private lblBranch As Label";
mostCurrent._lblbranch = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Private btnBranch As DSFloatingActionButton";
mostCurrent._btnbranch = new de.amberhome.objects.FloatingActionButtonWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Private intSettingsFlag As Int";
_intsettingsflag = 0;
 //BA.debugLineNum = 51;BA.debugLine="Private strSelectedBranch As String";
mostCurrent._strselectedbranch = "";
 //BA.debugLineNum = 52;BA.debugLine="Private intFleStyle As Int";
_intflestyle = 0;
 //BA.debugLineNum = 53;BA.debugLine="Private xmlIcon As XmlLayoutBuilder";
mostCurrent._xmlicon = new anywheresoftware.b4a.object.XmlLayoutBuilder();
 //BA.debugLineNum = 54;BA.debugLine="Private cboBranches As Spinner";
mostCurrent._cbobranches = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 55;BA.debugLine="Private lblSelectedBranch As Label";
mostCurrent._lblselectedbranch = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 56;BA.debugLine="Private pnlBranches As Panel";
mostCurrent._pnlbranches = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 57;BA.debugLine="Private pnlSearchBranch As Panel";
mostCurrent._pnlsearchbranch = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 58;BA.debugLine="Private SV As SearchView";
mostCurrent._sv = new com.bwsi.MeterReader.searchview();
 //BA.debugLineNum = 59;BA.debugLine="Private SearchFor As String";
mostCurrent._searchfor = "";
 //BA.debugLineNum = 60;BA.debugLine="Private btnCancel As ACButton";
mostCurrent._btncancel = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 61;BA.debugLine="Private CD As ColorDrawable";
mostCurrent._cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 62;BA.debugLine="Private cdTextBox As ColorDrawable";
mostCurrent._cdtextbox = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 64;BA.debugLine="Private SelectedBranchID As Double";
_selectedbranchid = 0;
 //BA.debugLineNum = 65;BA.debugLine="Private SelectedBranchName As String";
mostCurrent._selectedbranchname = "";
 //BA.debugLineNum = 67;BA.debugLine="Private sURL As String";
mostCurrent._surl = "";
 //BA.debugLineNum = 68;BA.debugLine="Private pnlSearchBranches As Panel";
mostCurrent._pnlsearchbranches = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 69;BA.debugLine="Private txtUserName As B4XFloatTextField";
mostCurrent._txtusername = new com.bwsi.MeterReader.b4xfloattextfield();
 //BA.debugLineNum = 70;BA.debugLine="Private InpTyp As SLInpTypeConst";
mostCurrent._inptyp = new com.bwsi.MeterReader.slinptypeconst();
 //BA.debugLineNum = 71;BA.debugLine="Private blnWifiOn As Boolean";
_blnwifion = false;
 //BA.debugLineNum = 72;BA.debugLine="Private blnUserClosed As Boolean";
_blnuserclosed = false;
 //BA.debugLineNum = 74;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
mostCurrent._alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 76;BA.debugLine="End Sub";
return "";
}
public static String  _ipsettings_buttonpressed(de.amberhome.materialdialogs.MaterialDialogWrapper _dialog,String _action) throws Exception{
 //BA.debugLineNum = 419;BA.debugLine="Sub IPSettings_ButtonPressed (Dialog As MaterialDi";
 //BA.debugLineNum = 420;BA.debugLine="Select Case Action";
switch (BA.switchObjectToInt(_action,_dialog.ACTION_POSITIVE,_dialog.ACTION_NEGATIVE)) {
case 0: {
 //BA.debugLineNum = 422;BA.debugLine="ShowAdminPassword";
_showadminpassword();
 break; }
case 1: {
 //BA.debugLineNum = 424;BA.debugLine="snack.Initialize(\"RetryButton\", Activity, \"Sett";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"RetryButton",(android.view.View)(mostCurrent._activity.getObject()),"Setting Server's IP Address Cancelled!",mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 425;BA.debugLine="SetSnackBarBackground(snack,Colors.Red)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 426;BA.debugLine="SetSnackBarTextColor(snack,Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 427;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 428;BA.debugLine="Return";
if (true) return "";
 break; }
}
;
 //BA.debugLineNum = 430;BA.debugLine="End Sub";
return "";
}
public static String  _ipsettings_inputchanged(de.amberhome.materialdialogs.MaterialDialogWrapper _mdialog,String _sip) throws Exception{
 //BA.debugLineNum = 410;BA.debugLine="Private Sub IPSettings_InputChanged (mDialog As Ma";
 //BA.debugLineNum = 411;BA.debugLine="If sIP.Length = 0 Then";
if (_sip.length()==0) { 
 //BA.debugLineNum = 412;BA.debugLine="mDialog.EnableActionButton(mDialog.ACTION_POSITI";
_mdialog.EnableActionButton(_mdialog.ACTION_POSITIVE,anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 414;BA.debugLine="mDialog.EnableActionButton(mDialog.ACTION_POSITI";
_mdialog.EnableActionButton(_mdialog.ACTION_POSITIVE,anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 416;BA.debugLine="NewIP = sIP";
mostCurrent._newip = _sip;
 //BA.debugLineNum = 417;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.drawable.ColorDrawable  _mycd() throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _mcd = null;
 //BA.debugLineNum = 960;BA.debugLine="Private Sub myCD As ColorDrawable";
 //BA.debugLineNum = 961;BA.debugLine="Dim mCD As ColorDrawable";
_mcd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 962;BA.debugLine="mCD.Initialize(Colors.RGB(240,240,240),0)";
_mcd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (240),(int) (240),(int) (240)),(int) (0));
 //BA.debugLineNum = 963;BA.debugLine="Return mCD";
if (true) return _mcd;
 //BA.debugLineNum = 964;BA.debugLine="End Sub";
return null;
}
public static String  _openelitesystem() throws Exception{
anywheresoftware.b4a.phone.Phone.PhoneIntents _p = null;
 //BA.debugLineNum = 910;BA.debugLine="Private Sub OpenEliteSystem";
 //BA.debugLineNum = 911;BA.debugLine="Dim p As PhoneIntents";
_p = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 912;BA.debugLine="StartActivity(p.OpenBrowser(GlobalVar.ServerAddre";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(_p.OpenBrowser(mostCurrent._globalvar._serveraddress /*String*/ )));
 //BA.debugLineNum = 913;BA.debugLine="End Sub";
return "";
}
public static String  _pnlsearchbranches_touch(int _action,float _x,float _y) throws Exception{
 //BA.debugLineNum = 930;BA.debugLine="Sub pnlSearchBranches_Touch (Action As Int, X As F";
 //BA.debugLineNum = 932;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 8;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Private tglWifi As ToggleWifiData";
_tglwifi = new tillekesoft.b4a.ToggleWifiData.ToggleWifiData();
 //BA.debugLineNum = 10;BA.debugLine="Private WiFiStatus As Boolean";
_wifistatus = false;
 //BA.debugLineNum = 11;BA.debugLine="Private SF As StringFunctions";
_sf = new adr.stringfunctions.stringfunctions();
 //BA.debugLineNum = 13;BA.debugLine="Public Permissions As RuntimePermissions";
_permissions = new anywheresoftware.b4a.objects.RuntimePermissions();
 //BA.debugLineNum = 15;BA.debugLine="End Sub";
return "";
}
public static String  _quitfontsizebinder_onbindview(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,int _viewtype) throws Exception{
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
anywheresoftware.b4a.objects.CSBuilder _cs = null;
 //BA.debugLineNum = 1115;BA.debugLine="Private Sub QuitFontSizeBinder_OnBindView (View As";
 //BA.debugLineNum = 1116;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
mostCurrent._alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 1117;BA.debugLine="Alert.Initialize";
mostCurrent._alert.Initialize();
 //BA.debugLineNum = 1118;BA.debugLine="If ViewType = Alert.VIEW_TITLE Then ' Title";
if (_viewtype==mostCurrent._alert.VIEW_TITLE) { 
 //BA.debugLineNum = 1119;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 1121;BA.debugLine="Dim CS As CSBuilder";
_cs = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 1122;BA.debugLine="CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Ty";
_cs.Initialize().Typeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD).Typeface(anywheresoftware.b4a.keywords.Common.Typeface.getMATERIALICONS()).Size((int) (22)).Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xe879)))+" "));
 //BA.debugLineNum = 1123;BA.debugLine="CS.Typeface(GlobalVar.Font).Size(22).Append(lbl.";
_cs.Typeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).Size((int) (22)).Append(BA.ObjectToCharSequence(_lbl.getText())).Pop();
 //BA.debugLineNum = 1125;BA.debugLine="lbl.Text = CS.PopAll";
_lbl.setText(BA.ObjectToCharSequence(_cs.PopAll().getObject()));
 };
 //BA.debugLineNum = 1127;BA.debugLine="If ViewType = Alert.VIEW_MESSAGE Then";
if (_viewtype==mostCurrent._alert.VIEW_MESSAGE) { 
 //BA.debugLineNum = 1128;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 1129;BA.debugLine="lbl.TextSize = 17";
_lbl.setTextSize((float) (17));
 //BA.debugLineNum = 1130;BA.debugLine="lbl.TextColor = Colors.Gray";
_lbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 };
 //BA.debugLineNum = 1132;BA.debugLine="End Sub";
return "";
}
public static String  _retrybutton_click() throws Exception{
 //BA.debugLineNum = 513;BA.debugLine="Private Sub RetryButton_Click()";
 //BA.debugLineNum = 514;BA.debugLine="ShowAdminPassword";
_showadminpassword();
 //BA.debugLineNum = 515;BA.debugLine="End Sub";
return "";
}
public static void  _saveuserinfo(String _sreturnval) throws Exception{
ResumableSub_SaveUserInfo rsub = new ResumableSub_SaveUserInfo(null,_sreturnval);
rsub.resume(processBA, null);
}
public static class ResumableSub_SaveUserInfo extends BA.ResumableSub {
public ResumableSub_SaveUserInfo(com.bwsi.MeterReader.login parent,String _sreturnval) {
this.parent = parent;
this._sreturnval = _sreturnval;
}
com.bwsi.MeterReader.login parent;
String _sreturnval;
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.List _root = null;
anywheresoftware.b4a.objects.collections.Map _mp = null;
Object _senderfilter = null;
boolean _success = false;
anywheresoftware.b4a.BA.IterableList group7;
int index7;
int groupLen7;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
try {

        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 318;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 319;BA.debugLine="Dim root As List";
_root = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 321;BA.debugLine="parser.Initialize(sReturnVal)";
_parser.Initialize(_sreturnval);
 //BA.debugLineNum = 322;BA.debugLine="root = parser.NextArray";
_root = _parser.NextArray();
 //BA.debugLineNum = 324;BA.debugLine="Try";
if (true) break;

case 1:
//try
this.state = 25;
this.catchState = 24;
this.state = 3;
if (true) break;

case 3:
//C
this.state = 4;
this.catchState = 24;
 //BA.debugLineNum = 325;BA.debugLine="If DBaseFunctions.IsUserExists(txtUserName.Text,";
if (true) break;

case 4:
//if
this.state = 22;
if (parent.mostCurrent._dbasefunctions._isuserexists /*boolean*/ (mostCurrent.activityBA,parent.mostCurrent._txtusername._gettext /*String*/ (),parent.mostCurrent._txtpassword._gettext /*String*/ (),parent.mostCurrent._globalvar._branchid /*int*/ )==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 6;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 326;BA.debugLine="For Each MP As Map In root";
if (true) break;

case 7:
//for
this.state = 10;
_mp = new anywheresoftware.b4a.objects.collections.Map();
group7 = _root;
index7 = 0;
groupLen7 = group7.getSize();
this.state = 26;
if (true) break;

case 26:
//C
this.state = 10;
if (index7 < groupLen7) {
this.state = 9;
_mp = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(group7.Get(index7)));}
if (true) break;

case 27:
//C
this.state = 26;
index7++;
if (true) break;

case 9:
//C
this.state = 27;
 //BA.debugLineNum = 327;BA.debugLine="Starter.strCriteria = \"INSERT INTO tblUsers VA";
parent.mostCurrent._starter._strcriteria /*String*/  = "INSERT INTO tblUsers VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
 //BA.debugLineNum = 328;BA.debugLine="Starter.DBCon.AddNonQueryToBatch(Starter.strCr";
parent.mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .AddNonQueryToBatch(parent.mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{_mp.Get((Object)("id")),_mp.Get((Object)("branch_id")),_mp.Get((Object)("name")),_mp.Get((Object)("username")),_mp.Get((Object)("androidpassword")),_mp.Get((Object)("Module1")),_mp.Get((Object)("Module2")),_mp.Get((Object)("Module3")),_mp.Get((Object)("Module4")),_mp.Get((Object)("Module5")),_mp.Get((Object)("Module6")),(Object)(("0"))}));
 if (true) break;
if (true) break;

case 10:
//C
this.state = 11;
;
 //BA.debugLineNum = 331;BA.debugLine="Dim SenderFilter As Object = Starter.DBCon.Exec";
_senderfilter = parent.mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQueryBatch(processBA,"SQL");
 //BA.debugLineNum = 332;BA.debugLine="Wait For (SenderFilter) SQL_NonQueryComplete (S";
anywheresoftware.b4a.keywords.Common.WaitFor("sql_nonquerycomplete", processBA, this, _senderfilter);
this.state = 28;
return;
case 28:
//C
this.state = 11;
_success = (Boolean) result[0];
;
 //BA.debugLineNum = 334;BA.debugLine="If Success Then";
if (true) break;

case 11:
//if
this.state = 21;
if (_success) { 
this.state = 13;
}if (true) break;

case 13:
//C
this.state = 14;
 //BA.debugLineNum = 335;BA.debugLine="snack.Initialize(\"\", Activity, $\"User Informat";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(parent.mostCurrent._activity.getObject()),("User Information has been successfully downloaded."),parent.mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 336;BA.debugLine="SetSnackBarBackground(snack,Colors.White)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 337;BA.debugLine="SetSnackBarTextColor(snack, GlobalVar.PriColor";
_setsnackbartextcolor(parent.mostCurrent._snack,(int) (parent.mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 338;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 340;BA.debugLine="DBaseFunctions.GetUserInfo(txtUserName.Text, t";
parent.mostCurrent._dbasefunctions._getuserinfo /*String*/ (mostCurrent.activityBA,parent.mostCurrent._txtusername._gettext /*String*/ (),parent.mostCurrent._txtpassword._gettext /*String*/ ());
 //BA.debugLineNum = 341;BA.debugLine="DBaseFunctions.GetBranchInfo(GlobalVar.BranchI";
parent.mostCurrent._dbasefunctions._getbranchinfo /*String*/ (mostCurrent.activityBA,parent.mostCurrent._globalvar._branchid /*int*/ );
 //BA.debugLineNum = 343;BA.debugLine="If DBaseFunctions.GetBillSettings(GlobalVar.Br";
if (true) break;

case 14:
//if
this.state = 17;
if (parent.mostCurrent._dbasefunctions._getbillsettings /*boolean*/ (mostCurrent.activityBA,parent.mostCurrent._globalvar._branchid /*int*/ )==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 16;
}if (true) break;

case 16:
//C
this.state = 17;
 //BA.debugLineNum = 344;BA.debugLine="snack.Initialize(\"\", Activity, $\"Billing Sett";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(parent.mostCurrent._activity.getObject()),("Billing Settings not initializes")+anywheresoftware.b4a.keywords.Common.CRLF+("Please contact your Android Developer for assistance."),parent.mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 345;BA.debugLine="SetSnackBarBackground(snack,Colors.White)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 346;BA.debugLine="SetSnackBarTextColor(snack, GlobalVar.PriColo";
_setsnackbartextcolor(parent.mostCurrent._snack,(int) (parent.mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 347;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 348;BA.debugLine="Return";
if (true) return ;
 if (true) break;
;
 //BA.debugLineNum = 351;BA.debugLine="If GlobalVar.Mod5 = 1 Then";

case 17:
//if
this.state = 20;
if (parent.mostCurrent._globalvar._mod5 /*int*/ ==1) { 
this.state = 19;
}if (true) break;

case 19:
//C
this.state = 20;
 //BA.debugLineNum = 352;BA.debugLine="DBaseFunctions.ZapMeterReader";
parent.mostCurrent._dbasefunctions._zapmeterreader /*String*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 353;BA.debugLine="DownloadReaders(GlobalVar.BranchID)";
_downloadreaders(parent.mostCurrent._globalvar._branchid /*int*/ );
 //BA.debugLineNum = 354;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 20:
//C
this.state = 21;
;
 //BA.debugLineNum = 357;BA.debugLine="CustomFunctions.SaveUserSettings";
parent.mostCurrent._customfunctions._saveusersettings /*String*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 358;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 359;BA.debugLine="StartActivity(MainScreen)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(parent.mostCurrent._mainscreen.getObject()));
 if (true) break;

case 21:
//C
this.state = 22;
;
 if (true) break;

case 22:
//C
this.state = 25;
;
 if (true) break;

case 24:
//C
this.state = 25;
this.catchState = 0;
 //BA.debugLineNum = 363;BA.debugLine="vibration.vibrateOnce(2000)";
parent.mostCurrent._vibration.vibrateOnce(processBA,(long) (2000));
 //BA.debugLineNum = 364;BA.debugLine="snack.Initialize(\"\", Activity, $\"Unable to save";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(parent.mostCurrent._activity.getObject()),("Unable to save User Information due to ")+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),parent.mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 365;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 366;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 367;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 368;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("428311603",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 if (true) break;
if (true) break;

case 25:
//C
this.state = -1;
this.catchState = 0;
;
 //BA.debugLineNum = 370;BA.debugLine="End Sub";
if (true) break;
}} 
       catch (Exception e0) {
			
if (catchState == 0)
    throw e0;
else {
    state = catchState;
processBA.setLastException(e0);}
            }
        }
    }
}
public static void  _sql_nonquerycomplete(boolean _success) throws Exception{
}
public static String  _searchbranches() throws Exception{
anywheresoftware.b4a.objects.collections.List _searchlist = null;
int _i = 0;
com.bwsi.MeterReader.searchview._item _it = null;
 //BA.debugLineNum = 771;BA.debugLine="Private Sub SearchBranches";
 //BA.debugLineNum = 772;BA.debugLine="Dim rsBranches As Cursor";
mostCurrent._rsbranches = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 773;BA.debugLine="Dim SearchList As List";
_searchlist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 774;BA.debugLine="If SV.IsInitialized=False Then";
if (mostCurrent._sv.IsInitialized /*boolean*/ ()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 775;BA.debugLine="SV.Initialize(Me,\"SV\")";
mostCurrent._sv._initialize /*String*/ (mostCurrent.activityBA,login.getObject(),"SV");
 };
 //BA.debugLineNum = 778;BA.debugLine="SV.ClearAll";
mostCurrent._sv._clearall /*String*/ ();
 //BA.debugLineNum = 779;BA.debugLine="SV.ClearSearchBox";
mostCurrent._sv._clearsearchbox /*String*/ ();
 //BA.debugLineNum = 780;BA.debugLine="SV.lv.Clear";
mostCurrent._sv._lv /*anywheresoftware.b4a.objects.ListViewWrapper*/ .Clear();
 //BA.debugLineNum = 782;BA.debugLine="SearchList.Initialize";
_searchlist.Initialize();
 //BA.debugLineNum = 783;BA.debugLine="SearchList.Clear";
_searchlist.Clear();
 //BA.debugLineNum = 785;BA.debugLine="Try";
try { //BA.debugLineNum = 786;BA.debugLine="Starter.strCriteria=\"SELECT tblBranches.BranchID";
mostCurrent._starter._strcriteria /*String*/  = "SELECT tblBranches.BranchID, tblBranches.CompanyID, tblBranches.BranchName, tblCompanies.CompanyName "+"FROM tblBranches INNER JOIN tblCompanies ON tblCompanies.CompanyID = tblBranches.CompanyID "+"ORDER BY tblBranches.CompanyID ASC, tblBranches.BranchName ASC";
 //BA.debugLineNum = 789;BA.debugLine="rsBranches = Starter.DBCon.ExecQuery(Starter.str";
mostCurrent._rsbranches = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 791;BA.debugLine="If rsBranches.RowCount > 0 Then";
if (mostCurrent._rsbranches.getRowCount()>0) { 
 //BA.debugLineNum = 792;BA.debugLine="For i = 0 To rsBranches.RowCount - 1";
{
final int step15 = 1;
final int limit15 = (int) (mostCurrent._rsbranches.getRowCount()-1);
_i = (int) (0) ;
for (;_i <= limit15 ;_i = _i + step15 ) {
 //BA.debugLineNum = 793;BA.debugLine="rsBranches.Position = i";
mostCurrent._rsbranches.setPosition(_i);
 //BA.debugLineNum = 794;BA.debugLine="Dim it As Item";
_it = new com.bwsi.MeterReader.searchview._item();
 //BA.debugLineNum = 795;BA.debugLine="it.Title = rsBranches.GetString(\"BranchName\")";
_it.Title /*String*/  = mostCurrent._rsbranches.GetString("BranchName");
 //BA.debugLineNum = 796;BA.debugLine="it.Text = TitleCase(rsBranches.GetString(\"Comp";
_it.Text /*String*/  = _titlecase(mostCurrent._rsbranches.GetString("CompanyName"));
 //BA.debugLineNum = 797;BA.debugLine="it.SearchText = rsBranches.GetString(\"BranchNa";
_it.SearchText /*String*/  = mostCurrent._rsbranches.GetString("BranchName").toLowerCase();
 //BA.debugLineNum = 798;BA.debugLine="it.Value = rsBranches.GetString(\"BranchID\")";
_it.Value /*Object*/  = (Object)(mostCurrent._rsbranches.GetString("BranchID"));
 //BA.debugLineNum = 799;BA.debugLine="SearchList.Add(it)";
_searchlist.Add((Object)(_it));
 }
};
 }else {
 //BA.debugLineNum = 802;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 804;BA.debugLine="SV.SetItems(SearchList)";
mostCurrent._sv._setitems /*Object*/ (_searchlist);
 //BA.debugLineNum = 805;BA.debugLine="SearchList.Clear";
_searchlist.Clear();
 } 
       catch (Exception e30) {
			processBA.setLastException(e30); //BA.debugLineNum = 807;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("430277668",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 809;BA.debugLine="End Sub";
return "";
}
public static String  _searchclosed() throws Exception{
 //BA.debugLineNum = 820;BA.debugLine="Sub SearchClosed";
 //BA.debugLineNum = 821;BA.debugLine="SearchFor = \"\"";
mostCurrent._searchfor = "";
 //BA.debugLineNum = 822;BA.debugLine="SV.ClearSearchBox";
mostCurrent._sv._clearsearchbox /*String*/ ();
 //BA.debugLineNum = 823;BA.debugLine="SV.ClearAll";
mostCurrent._sv._clearall /*String*/ ();
 //BA.debugLineNum = 825;BA.debugLine="pnlSearchBranches.Visible=False";
mostCurrent._pnlsearchbranches.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 826;BA.debugLine="EnableControls";
_enablecontrols();
 //BA.debugLineNum = 827;BA.debugLine="End Sub";
return "";
}
public static String  _selectedbranch_buttonpressed(de.amberhome.materialdialogs.MaterialDialogWrapper _mdialog,String _saction) throws Exception{
 //BA.debugLineNum = 886;BA.debugLine="Sub SelectedBranch_ButtonPressed (mDialog As Mater";
 //BA.debugLineNum = 887;BA.debugLine="Select Case sAction";
switch (BA.switchObjectToInt(_saction,_mdialog.ACTION_POSITIVE,_mdialog.ACTION_NEGATIVE)) {
case 0: {
 //BA.debugLineNum = 889;BA.debugLine="ShowAdminPassword";
_showadminpassword();
 break; }
case 1: {
 //BA.debugLineNum = 891;BA.debugLine="snack.Initialize(\"\", Activity,$\"Setting Branch";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Setting Branch cancelled."),mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 892;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 893;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 894;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 break; }
}
;
 //BA.debugLineNum = 896;BA.debugLine="End Sub";
return "";
}
public static String  _setserver_click() throws Exception{
 //BA.debugLineNum = 525;BA.debugLine="Private Sub SetServer_Click()";
 //BA.debugLineNum = 526;BA.debugLine="ShowIPSettings";
_showipsettings();
 //BA.debugLineNum = 527;BA.debugLine="End Sub";
return "";
}
public static String  _setsnackbarbackground(de.amberhome.objects.SnackbarWrapper _psnack,int _pcolor) throws Exception{
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
 //BA.debugLineNum = 529;BA.debugLine="Sub SetSnackBarBackground(pSnack As DSSnackbar, pC";
 //BA.debugLineNum = 530;BA.debugLine="Dim v As View";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
 //BA.debugLineNum = 531;BA.debugLine="v = pSnack.View";
_v = (anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(_psnack.getView()));
 //BA.debugLineNum = 532;BA.debugLine="v.Color = pColor";
_v.setColor(_pcolor);
 //BA.debugLineNum = 533;BA.debugLine="End Sub";
return "";
}
public static String  _setsnackbartextcolor(de.amberhome.objects.SnackbarWrapper _psnack,int _pcolor) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _p = null;
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
anywheresoftware.b4a.objects.LabelWrapper _textv = null;
 //BA.debugLineNum = 535;BA.debugLine="Sub SetSnackBarTextColor(pSnack As DSSnackbar, pCo";
 //BA.debugLineNum = 536;BA.debugLine="Dim p As Panel";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 537;BA.debugLine="p = pSnack.View";
_p = (anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(_psnack.getView()));
 //BA.debugLineNum = 538;BA.debugLine="For Each v As View In p.GetAllViewsRecursive";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
{
final anywheresoftware.b4a.BA.IterableList group3 = _p.GetAllViewsRecursive();
final int groupLen3 = group3.getSize()
;int index3 = 0;
;
for (; index3 < groupLen3;index3++){
_v = (anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(group3.Get(index3)));
 //BA.debugLineNum = 539;BA.debugLine="If v Is Label Then";
if (_v.getObjectOrNull() instanceof android.widget.TextView) { 
 //BA.debugLineNum = 540;BA.debugLine="Dim textv As Label";
_textv = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 541;BA.debugLine="textv = v";
_textv = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_v.getObject()));
 //BA.debugLineNum = 542;BA.debugLine="textv.TextColor = pColor";
_textv.setTextColor(_pcolor);
 //BA.debugLineNum = 543;BA.debugLine="Exit";
if (true) break;
 };
 }
};
 //BA.debugLineNum = 546;BA.debugLine="End Sub";
return "";
}
public static String  _showadminpassword() throws Exception{
anywheresoftware.b4a.objects.CSBuilder _cscontent = null;
 //BA.debugLineNum = 432;BA.debugLine="Private Sub ShowAdminPassword()";
 //BA.debugLineNum = 433;BA.debugLine="Dim csContent As CSBuilder";
_cscontent = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 435;BA.debugLine="csContent.Initialize.Size(12).Color(Colors.Gray).";
_cscontent.Initialize().Size((int) (12)).Color(anywheresoftware.b4a.keywords.Common.Colors.Gray).Append(BA.ObjectToCharSequence(("Enter Administrative Password to Continue."))).PopAll();
 //BA.debugLineNum = 437;BA.debugLine="MatDialogBuilder.Initialize(\"AdminPassword\")";
mostCurrent._matdialogbuilder.Initialize(mostCurrent.activityBA,"AdminPassword");
 //BA.debugLineNum = 438;BA.debugLine="MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIG";
mostCurrent._matdialogbuilder.Theme(mostCurrent._matdialogbuilder.THEME_LIGHT);
 //BA.debugLineNum = 439;BA.debugLine="MatDialogBuilder.Title($\"ADMINISTRATIVE PASSWORD\"";
mostCurrent._matdialogbuilder.Title(BA.ObjectToCharSequence(("ADMINISTRATIVE PASSWORD"))).TitleColor(anywheresoftware.b4a.keywords.Common.Colors.Red).TitleGravity(mostCurrent._matdialogbuilder.GRAVITY_START);
 //BA.debugLineNum = 440;BA.debugLine="MatDialogBuilder.IconRes(GlobalVar.WarningIcon).L";
mostCurrent._matdialogbuilder.IconRes(mostCurrent._globalvar._warningicon /*String*/ ).LimitIconToDefaultSize();
 //BA.debugLineNum = 441;BA.debugLine="MatDialogBuilder.InputType(MatDialogBuilder.TYPE_";
mostCurrent._matdialogbuilder.InputType(mostCurrent._matdialogbuilder.TYPE_TEXT_VARIATION_PASSWORD);
 //BA.debugLineNum = 442;BA.debugLine="MatDialogBuilder.Input2($\"Enter Admin Password he";
mostCurrent._matdialogbuilder.Input2(BA.ObjectToCharSequence(("Enter Admin Password here...")),BA.ObjectToCharSequence(("")),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 443;BA.debugLine="MatDialogBuilder.AlwaysCallInputCallback";
mostCurrent._matdialogbuilder.AlwaysCallInputCallback();
 //BA.debugLineNum = 444;BA.debugLine="MatDialogBuilder.Content(csContent)";
mostCurrent._matdialogbuilder.Content(BA.ObjectToCharSequence(_cscontent.getObject()));
 //BA.debugLineNum = 445;BA.debugLine="MatDialogBuilder.ContentColor(Colors.Black)";
mostCurrent._matdialogbuilder.ContentColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 446;BA.debugLine="MatDialogBuilder.PositiveText($\"OK\"$).PositiveCol";
mostCurrent._matdialogbuilder.PositiveText(BA.ObjectToCharSequence(("OK"))).PositiveColor((int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 447;BA.debugLine="MatDialogBuilder.NegativeText($\"CANCEL\"$).Negativ";
mostCurrent._matdialogbuilder.NegativeText(BA.ObjectToCharSequence(("CANCEL"))).NegativeColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 448;BA.debugLine="MatDialogBuilder.CanceledOnTouchOutside(False)";
mostCurrent._matdialogbuilder.CanceledOnTouchOutside(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 449;BA.debugLine="MatDialogBuilder.Show";
mostCurrent._matdialogbuilder.Show();
 //BA.debugLineNum = 450;BA.debugLine="End Sub";
return "";
}
public static String  _showipsettings() throws Exception{
anywheresoftware.b4a.objects.CSBuilder _cscontent = null;
 //BA.debugLineNum = 388;BA.debugLine="Private Sub ShowIPSettings()";
 //BA.debugLineNum = 389;BA.debugLine="Dim csContent As CSBuilder";
_cscontent = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 390;BA.debugLine="GlobalVar.ServerAddress = DBaseFunctions.GetServe";
mostCurrent._globalvar._serveraddress /*String*/  = mostCurrent._dbasefunctions._getserverip /*String*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 391;BA.debugLine="Log(GlobalVar.ServerAddress)";
anywheresoftware.b4a.keywords.Common.LogImpl("428573699",mostCurrent._globalvar._serveraddress /*String*/ ,0);
 //BA.debugLineNum = 393;BA.debugLine="csContent.Initialize.Size(12).Color(Colors.Red).A";
_cscontent.Initialize().Size((int) (12)).Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(("WARNING! Be sure to enter the correct server's IP Address."))).Color(anywheresoftware.b4a.keywords.Common.Colors.Gray).Append(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.CRLF+("This will be used for downloading/uploading reading data."))).PopAll();
 //BA.debugLineNum = 395;BA.debugLine="MatDialogBuilder.Initialize(\"IPSettings\")";
mostCurrent._matdialogbuilder.Initialize(mostCurrent.activityBA,"IPSettings");
 //BA.debugLineNum = 396;BA.debugLine="MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIG";
mostCurrent._matdialogbuilder.Theme(mostCurrent._matdialogbuilder.THEME_LIGHT);
 //BA.debugLineNum = 397;BA.debugLine="MatDialogBuilder.Title($\"SERVER IP ADDRESS SETTIN";
mostCurrent._matdialogbuilder.Title(BA.ObjectToCharSequence(("SERVER IP ADDRESS SETTINGS"))).TitleColor(anywheresoftware.b4a.keywords.Common.Colors.Red).TitleGravity(mostCurrent._matdialogbuilder.GRAVITY_START);
 //BA.debugLineNum = 398;BA.debugLine="MatDialogBuilder.IconRes(GlobalVar.WarningIcon).L";
mostCurrent._matdialogbuilder.IconRes(mostCurrent._globalvar._warningicon /*String*/ ).LimitIconToDefaultSize();
 //BA.debugLineNum = 399;BA.debugLine="MatDialogBuilder.InputType(Bit.Or(Bit.Or(MatDialo";
mostCurrent._matdialogbuilder.InputType(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Bit.Or(mostCurrent._matdialogbuilder.TYPE_CLASS_TEXT,mostCurrent._matdialogbuilder.TYPE_TEXT_FLAG_CAP_SENTENCES),mostCurrent._matdialogbuilder.TYPE_TEXT_VARIATION_PERSON_NAME));
 //BA.debugLineNum = 400;BA.debugLine="MatDialogBuilder.Input2($\"Enter your IP Address h";
mostCurrent._matdialogbuilder.Input2(BA.ObjectToCharSequence(("Enter your IP Address here...")),BA.ObjectToCharSequence(mostCurrent._globalvar._serveraddress /*String*/ ),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 401;BA.debugLine="MatDialogBuilder.AlwaysCallInputCallback";
mostCurrent._matdialogbuilder.AlwaysCallInputCallback();
 //BA.debugLineNum = 402;BA.debugLine="MatDialogBuilder.Content(csContent)";
mostCurrent._matdialogbuilder.Content(BA.ObjectToCharSequence(_cscontent.getObject()));
 //BA.debugLineNum = 403;BA.debugLine="MatDialogBuilder.ContentColor(Colors.Black)";
mostCurrent._matdialogbuilder.ContentColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 404;BA.debugLine="MatDialogBuilder.PositiveText($\"SAVE SETTINGS\"$).";
mostCurrent._matdialogbuilder.PositiveText(BA.ObjectToCharSequence(("SAVE SETTINGS"))).PositiveColor((int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 405;BA.debugLine="MatDialogBuilder.NegativeText($\"CANCEL\"$).Negativ";
mostCurrent._matdialogbuilder.NegativeText(BA.ObjectToCharSequence(("CANCEL"))).NegativeColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 406;BA.debugLine="MatDialogBuilder.CanceledOnTouchOutside(False)";
mostCurrent._matdialogbuilder.CanceledOnTouchOutside(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 407;BA.debugLine="MatDialogBuilder.Show";
mostCurrent._matdialogbuilder.Show();
 //BA.debugLineNum = 408;BA.debugLine="End Sub";
return "";
}
public static String  _showwifiwarning(String _stitle,String _smsg) throws Exception{
 //BA.debugLineNum = 995;BA.debugLine="Private Sub ShowWiFiWarning(sTitle As String, sMsg";
 //BA.debugLineNum = 996;BA.debugLine="Dim Alert As AX_CustomAlertDialog";
mostCurrent._alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 998;BA.debugLine="Alert.Initialize.Create _ 			.SetDialogStyleName(";
mostCurrent._alert.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialogDisableStatus").SetStyle(mostCurrent._alert.getSTYLE_DIALOGUE()).SetCancelable(anywheresoftware.b4a.keywords.Common.False).SetTitle(_stitle).SetMessage(_smsg).SetPositiveText("Turn On WiFi").SetPositiveColor(anywheresoftware.b4a.keywords.Common.Colors.Blue).SetPositiveTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetNegativeText("Cancel").SetNegativeColor(anywheresoftware.b4a.keywords.Common.Colors.Red).SetNegativeTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetTitleTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessageTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOnPositiveClicked(mostCurrent.activityBA,"AllowWiFi").SetOnNegativeClicked(mostCurrent.activityBA,"AllowWiFi").SetOnViewBinder(mostCurrent.activityBA,"WiFiFontSizeBinder");
 //BA.debugLineNum = 1016;BA.debugLine="Alert.SetDialogBackground(myCD)";
mostCurrent._alert.SetDialogBackground((android.graphics.drawable.Drawable)(_mycd().getObject()));
 //BA.debugLineNum = 1017;BA.debugLine="Alert.Build.Show";
mostCurrent._alert.Build().Show();
 //BA.debugLineNum = 1019;BA.debugLine="End Sub";
return "";
}
public static String  _sv_itemclick(String _value) throws Exception{
 //BA.debugLineNum = 811;BA.debugLine="Sub SV_ItemClick(Value As String)";
 //BA.debugLineNum = 812;BA.debugLine="Log(Value)";
anywheresoftware.b4a.keywords.Common.LogImpl("430343169",_value,0);
 //BA.debugLineNum = 813;BA.debugLine="SelectedBranchID = Value";
_selectedbranchid = (double)(Double.parseDouble(_value));
 //BA.debugLineNum = 814;BA.debugLine="SV.ClearAll";
mostCurrent._sv._clearall /*String*/ ();
 //BA.debugLineNum = 815;BA.debugLine="SearchClosed";
_searchclosed();
 //BA.debugLineNum = 816;BA.debugLine="SelectedBranchName = DBaseFunctions.GetBranchName";
mostCurrent._selectedbranchname = mostCurrent._dbasefunctions._getbranchname /*String*/ (mostCurrent.activityBA,(int)(Double.parseDouble(_value)));
 //BA.debugLineNum = 817;BA.debugLine="ConfirmSelectedBranch(SelectedBranchName)";
_confirmselectedbranch(mostCurrent._selectedbranchname);
 //BA.debugLineNum = 818;BA.debugLine="End Sub";
return "";
}
public static String  _titlecase(String _s) throws Exception{
anywheresoftware.b4a.keywords.Regex.MatcherWrapper _m = null;
int _i = 0;
 //BA.debugLineNum = 1134;BA.debugLine="Sub TitleCase (s As String) As String";
 //BA.debugLineNum = 1135;BA.debugLine="s = s.ToLowerCase";
_s = _s.toLowerCase();
 //BA.debugLineNum = 1136;BA.debugLine="Dim m As Matcher = Regex.Matcher(\"\\b(\\w)\", s)";
_m = new anywheresoftware.b4a.keywords.Regex.MatcherWrapper();
_m = anywheresoftware.b4a.keywords.Common.Regex.Matcher("\\b(\\w)",_s);
 //BA.debugLineNum = 1137;BA.debugLine="Do While m.Find";
while (_m.Find()) {
 //BA.debugLineNum = 1138;BA.debugLine="Dim i As Int = m.GetStart(1)";
_i = _m.GetStart((int) (1));
 //BA.debugLineNum = 1139;BA.debugLine="s = s.SubString2(0, i) & s.SubString2(i, i + 1).";
_s = _s.substring((int) (0),_i)+_s.substring(_i,(int) (_i+1)).toUpperCase()+_s.substring((int) (_i+1));
 }
;
 //BA.debugLineNum = 1141;BA.debugLine="Return s";
if (true) return _s;
 //BA.debugLineNum = 1142;BA.debugLine="End Sub";
return "";
}
public static String  _txtpassword_focuschanged(boolean _hasfocus) throws Exception{
 //BA.debugLineNum = 378;BA.debugLine="Sub txtPassword_FocusChanged (HasFocus As Boolean)";
 //BA.debugLineNum = 379;BA.debugLine="If HasFocus And txtPassword.Text.Length > 0 Then";
if (_hasfocus && mostCurrent._txtpassword._gettext /*String*/ ().length()>0) { 
 };
 //BA.debugLineNum = 382;BA.debugLine="End Sub";
return "";
}
public static String  _txtusername_focuschanged(boolean _hasfocus) throws Exception{
 //BA.debugLineNum = 372;BA.debugLine="Sub txtUserName_FocusChanged (HasFocus As Boolean)";
 //BA.debugLineNum = 373;BA.debugLine="If HasFocus And txtUserName.Text.Length > 0 Then";
if (_hasfocus && mostCurrent._txtusername._gettext /*String*/ ().length()>0) { 
 };
 //BA.debugLineNum = 376;BA.debugLine="End Sub";
return "";
}
public static String  _updatebranchid(String _ibranchid) throws Exception{
 //BA.debugLineNum = 706;BA.debugLine="Private Sub UpdateBranchID(iBranchID As String)";
 //BA.debugLineNum = 707;BA.debugLine="Starter.DBCon.BeginTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .BeginTransaction();
 //BA.debugLineNum = 709;BA.debugLine="Try";
try { //BA.debugLineNum = 710;BA.debugLine="Starter.DBCon.ExecNonQuery(\"UPDATE tblSysParam S";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery("UPDATE tblSysParam SET BranchID = "+_ibranchid);
 //BA.debugLineNum = 711;BA.debugLine="Starter.DBCon.TransactionSuccessful";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .TransactionSuccessful();
 //BA.debugLineNum = 712;BA.debugLine="snack.Initialize(\"\",Activity,\"Branch Settings ha";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),"Branch Settings has been successfully saved.",mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 713;BA.debugLine="SetSnackBarBackground(snack, Colors.White)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 714;BA.debugLine="SetSnackBarTextColor(snack,GlobalVar.PriColor)";
_setsnackbartextcolor(mostCurrent._snack,(int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 715;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 } 
       catch (Exception e10) {
			processBA.setLastException(e10); //BA.debugLineNum = 717;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("430015499",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 719;BA.debugLine="Starter.DBCon.EndTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .EndTransaction();
 //BA.debugLineNum = 720;BA.debugLine="End Sub";
return "";
}
public static String  _updateseptage(int _ireadid,int _iacctid) throws Exception{
 //BA.debugLineNum = 915;BA.debugLine="Private Sub UpdateSeptage(iReadID As Int, iAcctID";
 //BA.debugLineNum = 916;BA.debugLine="Starter.DBCon.BeginTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .BeginTransaction();
 //BA.debugLineNum = 917;BA.debugLine="Try";
try { //BA.debugLineNum = 918;BA.debugLine="Starter.strCriteria = \"UPDATE tblBranch \" & _";
mostCurrent._starter._strcriteria /*String*/  = "UPDATE tblBranch "+"SET PrintStatus = ? "+"WHERE ReadID = "+BA.NumberToString(_ireadid)+" "+"AND AcctID = "+BA.NumberToString(_iacctid);
 //BA.debugLineNum = 922;BA.debugLine="Starter.DBCon.ExecNonQuery2(Starter.strCriteria,";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2(mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{BA.NumberToString(1)}));
 //BA.debugLineNum = 923;BA.debugLine="Starter.DBCon.TransactionSuccessful";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .TransactionSuccessful();
 } 
       catch (Exception e7) {
			processBA.setLastException(e7); //BA.debugLineNum = 925;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("430867466",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 927;BA.debugLine="Starter.DBCon.EndTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .EndTransaction();
 //BA.debugLineNum = 928;BA.debugLine="End Sub";
return "";
}
public static String  _updateserverip(String _snewip) throws Exception{
 //BA.debugLineNum = 620;BA.debugLine="Private Sub UpdateServerIP(sNewIP As String)";
 //BA.debugLineNum = 621;BA.debugLine="Starter.DBCon.BeginTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .BeginTransaction();
 //BA.debugLineNum = 623;BA.debugLine="Try";
try { //BA.debugLineNum = 624;BA.debugLine="Starter.DBCon.ExecNonQuery(\"UPDATE android_metad";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery("UPDATE android_metadata SET server_ip = '"+_snewip+"'");
 //BA.debugLineNum = 625;BA.debugLine="Starter.DBCon.TransactionSuccessful";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .TransactionSuccessful();
 //BA.debugLineNum = 626;BA.debugLine="snack.Initialize(\"\",Activity,\"Server's IP was su";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),"Server's IP was succesfully Updated.",mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 627;BA.debugLine="SetSnackBarBackground(snack, Colors.White)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 628;BA.debugLine="SetSnackBarTextColor(snack,GlobalVar.PriColor)";
_setsnackbartextcolor(mostCurrent._snack,(int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 629;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 630;BA.debugLine="GlobalVar.ServerAddress = DBaseFunctions.GetServ";
mostCurrent._globalvar._serveraddress /*String*/  = mostCurrent._dbasefunctions._getserverip /*String*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 631;BA.debugLine="GlobalVar.BranchID = DBaseFunctions.GetBranchID";
mostCurrent._globalvar._branchid /*int*/  = mostCurrent._dbasefunctions._getbranchid /*int*/ (mostCurrent.activityBA);
 } 
       catch (Exception e12) {
			processBA.setLastException(e12); //BA.debugLineNum = 633;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("429556749",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 635;BA.debugLine="Starter.DBCon.EndTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .EndTransaction();
 //BA.debugLineNum = 636;BA.debugLine="End Sub";
return "";
}
public static String  _usernameblank_click() throws Exception{
 //BA.debugLineNum = 517;BA.debugLine="Private Sub UserNameBlank_Click()";
 //BA.debugLineNum = 518;BA.debugLine="txtUserName.RequestFocusAndShowKeyboard";
mostCurrent._txtusername._requestfocusandshowkeyboard /*String*/ ();
 //BA.debugLineNum = 519;BA.debugLine="txtPassword.Text=\"\"";
mostCurrent._txtpassword._settext /*String*/ ("");
 //BA.debugLineNum = 520;BA.debugLine="End Sub";
return "";
}
public static String  _userpasswordblank_click() throws Exception{
 //BA.debugLineNum = 522;BA.debugLine="Private Sub UserPasswordBlank_Click()";
 //BA.debugLineNum = 523;BA.debugLine="txtPassword.RequestFocusAndShowKeyboard";
mostCurrent._txtpassword._requestfocusandshowkeyboard /*String*/ ();
 //BA.debugLineNum = 524;BA.debugLine="End Sub";
return "";
}
public static String  _userwarningmsg(String _stitle,String _smsg) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alertdialog = null;
 //BA.debugLineNum = 935;BA.debugLine="Private Sub UserWarningMsg(sTitle As String, sMsg";
 //BA.debugLineNum = 936;BA.debugLine="Dim AlertDialog As AX_CustomAlertDialog";
_alertdialog = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 938;BA.debugLine="AlertDialog.Initialize";
_alertdialog.Initialize();
 //BA.debugLineNum = 940;BA.debugLine="AlertDialog.Initialize.Create _ 			.SetDialogStyl";
_alertdialog.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialogDisableStatus").SetStyle(_alertdialog.getSTYLE_DIALOGUE()).SetCancelable(anywheresoftware.b4a.keywords.Common.False).SetTitle(_stitle).SetTitleColor(anywheresoftware.b4a.keywords.Common.Colors.Red).SetTitleTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessage(_smsg).SetPositiveText("OK").SetPositiveColor(anywheresoftware.b4a.keywords.Common.Colors.Blue).SetPositiveTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessageTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOnPositiveClicked(mostCurrent.activityBA,"WarningMessage").SetOnViewBinder(mostCurrent.activityBA,"WarningDialog");
 //BA.debugLineNum = 955;BA.debugLine="AlertDialog.SetDialogBackground(myCD)";
_alertdialog.SetDialogBackground((android.graphics.drawable.Drawable)(_mycd().getObject()));
 //BA.debugLineNum = 956;BA.debugLine="AlertDialog.Build.Show";
_alertdialog.Build().Show();
 //BA.debugLineNum = 958;BA.debugLine="End Sub";
return "";
}
public static String  _warningdialog_onbindview(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,int _viewtype) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alertdialog = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
anywheresoftware.b4a.objects.CSBuilder _cs = null;
 //BA.debugLineNum = 974;BA.debugLine="Private Sub WarningDialog_OnBindView (View As View";
 //BA.debugLineNum = 975;BA.debugLine="Dim AlertDialog As AX_CustomAlertDialog";
_alertdialog = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 976;BA.debugLine="AlertDialog.Initialize";
_alertdialog.Initialize();
 //BA.debugLineNum = 978;BA.debugLine="If ViewType = AlertDialog.VIEW_TITLE Then ' Title";
if (_viewtype==_alertdialog.VIEW_TITLE) { 
 //BA.debugLineNum = 979;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 980;BA.debugLine="Dim CS As CSBuilder";
_cs = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 982;BA.debugLine="CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Ty";
_cs.Initialize().Typeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD).Typeface(anywheresoftware.b4a.keywords.Common.Typeface.getMATERIALICONS()).Size((int) (26)).Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xe002)))+"  "));
 //BA.debugLineNum = 983;BA.debugLine="CS.Typeface(GlobalVar.Font).Size(22).Append(lbl.";
_cs.Typeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).Size((int) (22)).Append(BA.ObjectToCharSequence(_lbl.getText())).Pop();
 //BA.debugLineNum = 985;BA.debugLine="lbl.Text = CS.PopAll";
_lbl.setText(BA.ObjectToCharSequence(_cs.PopAll().getObject()));
 };
 //BA.debugLineNum = 987;BA.debugLine="If ViewType = AlertDialog.VIEW_MESSAGE Then";
if (_viewtype==_alertdialog.VIEW_MESSAGE) { 
 //BA.debugLineNum = 988;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 989;BA.debugLine="lbl.TextSize = 16";
_lbl.setTextSize((float) (16));
 //BA.debugLineNum = 990;BA.debugLine="lbl.TextColor = Colors.Gray";
_lbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 };
 //BA.debugLineNum = 992;BA.debugLine="End Sub";
return "";
}
public static String  _warningmessage_onpositiveclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alertdialog = null;
 //BA.debugLineNum = 966;BA.debugLine="Private Sub WarningMessage_OnPositiveClicked (View";
 //BA.debugLineNum = 967;BA.debugLine="Dim AlertDialog As AX_CustomAlertDialog";
_alertdialog = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 969;BA.debugLine="vibration.vibrateCancel";
mostCurrent._vibration.vibrateCancel(processBA);
 //BA.debugLineNum = 970;BA.debugLine="AlertDialog.Initialize.Dismiss(Dialog)";
_alertdialog.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 972;BA.debugLine="End Sub";
return "";
}
public static String  _wififontsizebinder_onbindview(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,int _viewtype) throws Exception{
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
anywheresoftware.b4a.objects.CSBuilder _cs = null;
 //BA.debugLineNum = 1047;BA.debugLine="Private Sub WiFiFontSizeBinder_OnBindView (View As";
 //BA.debugLineNum = 1048;BA.debugLine="Dim alert As AX_CustomAlertDialog";
mostCurrent._alert = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 1049;BA.debugLine="alert.Initialize";
mostCurrent._alert.Initialize();
 //BA.debugLineNum = 1050;BA.debugLine="If ViewType = Alert.VIEW_TITLE Then ' Title";
if (_viewtype==mostCurrent._alert.VIEW_TITLE) { 
 //BA.debugLineNum = 1051;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 1056;BA.debugLine="Dim CS As CSBuilder";
_cs = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 1060;BA.debugLine="CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Ty";
_cs.Initialize().Typeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD).Typeface(anywheresoftware.b4a.keywords.Common.Typeface.getMATERIALICONS()).Size((int) (26)).Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xe1b3)))+"  "));
 //BA.debugLineNum = 1061;BA.debugLine="CS.Typeface(GlobalVar.Font).Size(22).Append(lbl.";
_cs.Typeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).Size((int) (22)).Append(BA.ObjectToCharSequence(_lbl.getText())).Pop();
 //BA.debugLineNum = 1063;BA.debugLine="lbl.Text = CS.PopAll";
_lbl.setText(BA.ObjectToCharSequence(_cs.PopAll().getObject()));
 };
 //BA.debugLineNum = 1065;BA.debugLine="If ViewType = Alert.VIEW_MESSAGE Then";
if (_viewtype==mostCurrent._alert.VIEW_MESSAGE) { 
 //BA.debugLineNum = 1066;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 1067;BA.debugLine="lbl.TextSize = 16";
_lbl.setTextSize((float) (16));
 //BA.debugLineNum = 1068;BA.debugLine="lbl.TextColor = Colors.Gray";
_lbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 };
 //BA.debugLineNum = 1070;BA.debugLine="End Sub";
return "";
}
}
