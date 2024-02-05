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

public class readingvalidation extends androidx.appcompat.app.AppCompatActivity implements B4AActivity{
	public static readingvalidation mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "com.bwsi.MeterReader", "com.bwsi.MeterReader.readingvalidation");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (readingvalidation).");
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
		activityBA = new BA(this, layout, processBA, "com.bwsi.MeterReader", "com.bwsi.MeterReader.readingvalidation");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "com.bwsi.MeterReader.readingvalidation", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (readingvalidation) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (readingvalidation) Resume **");
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
		return readingvalidation.class;
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
            BA.LogInfo("** Activity (readingvalidation) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (readingvalidation) Pause event (activity is not paused). **");
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
            readingvalidation mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (readingvalidation) Resume **");
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
public anywheresoftware.b4a.sql.SQL.CursorWrapper _rsreaders = null;
public static int _ivalidtype = 0;
public anywheresoftware.b4a.objects.PanelWrapper _btnab = null;
public anywheresoftware.b4a.objects.PanelWrapper _btnhb = null;
public anywheresoftware.b4a.objects.PanelWrapper _btnlb = null;
public anywheresoftware.b4a.objects.PanelWrapper _btnmiscoded = null;
public anywheresoftware.b4a.objects.PanelWrapper _btnunprinted = null;
public anywheresoftware.b4a.objects.PanelWrapper _btnzerocons = null;
public de.amberhome.objects.appcompat.ACSpinnerWrapper _cboreader = null;
public com.bwsi.MeterReader.badger _mybadges = null;
public static int _izerocount = 0;
public static int _imiscodedcount = 0;
public static int _ilbcount = 0;
public static int _ihbcount = 0;
public static int _iavgcount = 0;
public static int _iunprintcount = 0;
public anywheresoftware.b4a.objects.B4XViewWrapper _miscodedicon = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _zeroicon = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _lbicon = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _hbicon = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _abicon = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _unprintedicon = null;
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
public com.bwsi.MeterReader.newcam _newcam = null;
public com.bwsi.MeterReader.readingbooks _readingbooks = null;
public com.bwsi.MeterReader.readingsettings _readingsettings = null;
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
 //BA.debugLineNum = 49;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 50;BA.debugLine="MyScale.SetRate(0.5)";
mostCurrent._myscale._setrate /*String*/ (mostCurrent.activityBA,0.5);
 //BA.debugLineNum = 51;BA.debugLine="Activity.LoadLayout(\"RdgValidation\")";
mostCurrent._activity.LoadLayout("RdgValidation",mostCurrent.activityBA);
 //BA.debugLineNum = 53;BA.debugLine="GlobalVar.CSTitle.Initialize.Size(17).Bold.Append";
mostCurrent._globalvar._cstitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (17)).Bold().Append(BA.ObjectToCharSequence(("Reading Validation Report"))).PopAll();
 //BA.debugLineNum = 54;BA.debugLine="GlobalVar.CSSubTitle.Initialize.Size(14).Append($";
mostCurrent._globalvar._cssubtitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (14)).Append(BA.ObjectToCharSequence(("List of Account's with Unusual Reading"))).PopAll();
 //BA.debugLineNum = 56;BA.debugLine="ToolBar.InitMenuListener";
mostCurrent._toolbar.InitMenuListener();
 //BA.debugLineNum = 57;BA.debugLine="ToolBar.Title = GlobalVar.CSTitle";
mostCurrent._toolbar.setTitle(BA.ObjectToCharSequence(mostCurrent._globalvar._cstitle /*anywheresoftware.b4a.objects.CSBuilder*/ .getObject()));
 //BA.debugLineNum = 58;BA.debugLine="ToolBar.SubTitle = GlobalVar.CSSubTitle";
mostCurrent._toolbar.setSubTitle(BA.ObjectToCharSequence(mostCurrent._globalvar._cssubtitle /*anywheresoftware.b4a.objects.CSBuilder*/ .getObject()));
 //BA.debugLineNum = 59;BA.debugLine="ToolBar.SetElevationAnimated(5,5dip)";
mostCurrent._toolbar.SetElevationAnimated((int) (5),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 61;BA.debugLine="Dim jo As JavaObject";
_jo = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 62;BA.debugLine="Dim xl As XmlLayoutBuilder";
_xl = new anywheresoftware.b4a.object.XmlLayoutBuilder();
 //BA.debugLineNum = 63;BA.debugLine="jo = ToolBar";
_jo = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(mostCurrent._toolbar.getObject()));
 //BA.debugLineNum = 64;BA.debugLine="jo.RunMethod(\"setPopupTheme\", Array(xl.GetResourc";
_jo.RunMethod("setPopupTheme",new Object[]{(Object)(_xl.GetResourceId("style","ToolbarMenu"))});
 //BA.debugLineNum = 65;BA.debugLine="jo.RunMethod(\"setContentInsetStartWithNavigation\"";
_jo.RunMethod("setContentInsetStartWithNavigation",new Object[]{(Object)(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)))});
 //BA.debugLineNum = 66;BA.debugLine="jo.RunMethod(\"setTitleMarginStart\", Array(0dip))";
_jo.RunMethod("setTitleMarginStart",new Object[]{(Object)(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)))});
 //BA.debugLineNum = 68;BA.debugLine="ActionBarButton.Initialize";
mostCurrent._actionbarbutton.Initialize(mostCurrent.activityBA);
 //BA.debugLineNum = 69;BA.debugLine="ActionBarButton.ShowUpIndicator = True";
mostCurrent._actionbarbutton.setShowUpIndicator(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 71;BA.debugLine="MyBadges.Initialize";
mostCurrent._mybadges._initialize /*String*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 72;BA.debugLine="FillReaders";
_fillreaders();
 //BA.debugLineNum = 73;BA.debugLine="If cboReader.SelectedIndex > -1 Then";
if (mostCurrent._cboreader.getSelectedIndex()>-1) { 
 //BA.debugLineNum = 74;BA.debugLine="GlobalVar.iReaderID = DBaseFunctions.GetIDbyCode";
mostCurrent._globalvar._ireaderid /*int*/  = (int) (mostCurrent._dbasefunctions._getidbycode /*double*/ (mostCurrent.activityBA,"UserID","tblUsers","EmpName",mostCurrent._cboreader.getSelectedItem()));
 //BA.debugLineNum = 75;BA.debugLine="GetValidationCount(GlobalVar.iReaderID, GlobalVa";
_getvalidationcount(mostCurrent._globalvar._ireaderid /*int*/ ,mostCurrent._globalvar._branchid /*int*/ ,(int) (mostCurrent._globalvar._billyear /*double*/ ),mostCurrent._globalvar._billmonth /*int*/ );
 //BA.debugLineNum = 77;BA.debugLine="MyBadges.SetBadge(MiscodedIcon, iMisCodedCount)";
mostCurrent._mybadges._setbadge /*String*/ (mostCurrent._miscodedicon,_imiscodedcount);
 //BA.debugLineNum = 78;BA.debugLine="MyBadges.SetBadge(ZeroIcon, iZeroCount)";
mostCurrent._mybadges._setbadge /*String*/ (mostCurrent._zeroicon,_izerocount);
 //BA.debugLineNum = 79;BA.debugLine="MyBadges.SetBadge(LBIcon, iLBCount)";
mostCurrent._mybadges._setbadge /*String*/ (mostCurrent._lbicon,_ilbcount);
 //BA.debugLineNum = 80;BA.debugLine="MyBadges.SetBadge(HBIcon, iHBCount)";
mostCurrent._mybadges._setbadge /*String*/ (mostCurrent._hbicon,_ihbcount);
 //BA.debugLineNum = 81;BA.debugLine="MyBadges.SetBadge(ABIcon, iAvgCount)";
mostCurrent._mybadges._setbadge /*String*/ (mostCurrent._abicon,_iavgcount);
 //BA.debugLineNum = 82;BA.debugLine="MyBadges.SetBadge(UnprintedIcon, iUnprintCount)";
mostCurrent._mybadges._setbadge /*String*/ (mostCurrent._unprintedicon,_iunprintcount);
 };
 //BA.debugLineNum = 87;BA.debugLine="End Sub";
return "";
}
public static String  _activity_createmenu(de.amberhome.objects.appcompat.ACMenuWrapper _menu) throws Exception{
 //BA.debugLineNum = 89;BA.debugLine="Sub Activity_CreateMenu(Menu As ACMenu)";
 //BA.debugLineNum = 90;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 92;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 93;BA.debugLine="If KeyCode = 4 Then";
if (_keycode==4) { 
 //BA.debugLineNum = 94;BA.debugLine="ToolBar_NavigationItemClick";
_toolbar_navigationitemclick();
 //BA.debugLineNum = 95;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 }else {
 //BA.debugLineNum = 97;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 99;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 110;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 112;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 105;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 108;BA.debugLine="End Sub";
return "";
}
public static String  _btnab_click() throws Exception{
 //BA.debugLineNum = 326;BA.debugLine="Sub btnAB_Click";
 //BA.debugLineNum = 327;BA.debugLine="If cboReader.SelectedItem = \"\" Then";
if ((mostCurrent._cboreader.getSelectedItem()).equals("")) { 
 //BA.debugLineNum = 328;BA.debugLine="WarningMsg($\"NO READER SELECTED\"$,$\"Select the r";
_warningmsg(("NO READER SELECTED"),("Select the reader first!"));
 //BA.debugLineNum = 329;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 331;BA.debugLine="GlobalVar.iReaderID = DBaseFunctions.GetIDbyCode(";
mostCurrent._globalvar._ireaderid /*int*/  = (int) (mostCurrent._dbasefunctions._getidbycode /*double*/ (mostCurrent.activityBA,"UserID","tblUsers","EmpName",mostCurrent._cboreader.getSelectedItem()));
 //BA.debugLineNum = 333;BA.debugLine="If IsThereAvgRecords(GlobalVar.iReaderID) = False";
if (_isthereavgrecords(mostCurrent._globalvar._ireaderid /*int*/ )==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 334;BA.debugLine="WarningMsg($\"NO RECORD FOUND\"$,$\"No Bill Average";
_warningmsg(("NO RECORD FOUND"),("No Bill Average reading's found!"));
 //BA.debugLineNum = 335;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 337;BA.debugLine="iValidType = 4";
_ivalidtype = (int) (4);
 //BA.debugLineNum = 338;BA.debugLine="StartActivity(ValidationRptGenerator)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._validationrptgenerator.getObject()));
 //BA.debugLineNum = 339;BA.debugLine="End Sub";
return "";
}
public static String  _btnhb_click() throws Exception{
 //BA.debugLineNum = 311;BA.debugLine="Sub btnHB_Click";
 //BA.debugLineNum = 312;BA.debugLine="If cboReader.SelectedItem = \"\" Then";
if ((mostCurrent._cboreader.getSelectedItem()).equals("")) { 
 //BA.debugLineNum = 313;BA.debugLine="WarningMsg($\"NO READER SELECTED\"$,$\"Select the r";
_warningmsg(("NO READER SELECTED"),("Select the reader first!"));
 //BA.debugLineNum = 314;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 316;BA.debugLine="GlobalVar.iReaderID = DBaseFunctions.GetIDbyCode(";
mostCurrent._globalvar._ireaderid /*int*/  = (int) (mostCurrent._dbasefunctions._getidbycode /*double*/ (mostCurrent.activityBA,"UserID","tblUsers","EmpName",mostCurrent._cboreader.getSelectedItem()));
 //BA.debugLineNum = 318;BA.debugLine="If IsThereRecords($\"implosive-inc\"$,GlobalVar.iRe";
if (_isthererecords(("implosive-inc"),mostCurrent._globalvar._ireaderid /*int*/ )==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 319;BA.debugLine="WarningMsg($\"NO RECORD FOUND\"$,$\"No Implosive-In";
_warningmsg(("NO RECORD FOUND"),("No Implosive-Increase reading's found!"));
 //BA.debugLineNum = 320;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 322;BA.debugLine="iValidType = 3";
_ivalidtype = (int) (3);
 //BA.debugLineNum = 323;BA.debugLine="StartActivity(ValidationRptGenerator)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._validationrptgenerator.getObject()));
 //BA.debugLineNum = 324;BA.debugLine="End Sub";
return "";
}
public static String  _btnlb_click() throws Exception{
 //BA.debugLineNum = 296;BA.debugLine="Sub btnLB_Click";
 //BA.debugLineNum = 297;BA.debugLine="If cboReader.SelectedItem = \"\" Then";
if ((mostCurrent._cboreader.getSelectedItem()).equals("")) { 
 //BA.debugLineNum = 298;BA.debugLine="WarningMsg($\"NO READER SELECTED\"$,$\"Select the r";
_warningmsg(("NO READER SELECTED"),("Select the reader first!"));
 //BA.debugLineNum = 299;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 301;BA.debugLine="GlobalVar.iReaderID = DBaseFunctions.GetIDbyCode(";
mostCurrent._globalvar._ireaderid /*int*/  = (int) (mostCurrent._dbasefunctions._getidbycode /*double*/ (mostCurrent.activityBA,"UserID","tblUsers","EmpName",mostCurrent._cboreader.getSelectedItem()));
 //BA.debugLineNum = 303;BA.debugLine="If IsThereRecords($\"implosive-dec\"$,GlobalVar.iRe";
if (_isthererecords(("implosive-dec"),mostCurrent._globalvar._ireaderid /*int*/ )==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 304;BA.debugLine="WarningMsg($\"NO RECORD FOUND\"$,$\"No Implosive-De";
_warningmsg(("NO RECORD FOUND"),("No Implosive-Decrease reading's found!"));
 //BA.debugLineNum = 305;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 307;BA.debugLine="iValidType = 2";
_ivalidtype = (int) (2);
 //BA.debugLineNum = 308;BA.debugLine="StartActivity(ValidationRptGenerator)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._validationrptgenerator.getObject()));
 //BA.debugLineNum = 309;BA.debugLine="End Sub";
return "";
}
public static String  _btnmiscoded_click() throws Exception{
 //BA.debugLineNum = 281;BA.debugLine="Sub btnMiscoded_Click";
 //BA.debugLineNum = 282;BA.debugLine="If cboReader.SelectedItem = \"\" Then";
if ((mostCurrent._cboreader.getSelectedItem()).equals("")) { 
 //BA.debugLineNum = 283;BA.debugLine="WarningMsg($\"NO READER SELECTED\"$,$\"Select the r";
_warningmsg(("NO READER SELECTED"),("Select the reader first!"));
 //BA.debugLineNum = 284;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 286;BA.debugLine="GlobalVar.iReaderID = DBaseFunctions.GetIDbyCode(";
mostCurrent._globalvar._ireaderid /*int*/  = (int) (mostCurrent._dbasefunctions._getidbycode /*double*/ (mostCurrent.activityBA,"UserID","tblUsers","EmpName",mostCurrent._cboreader.getSelectedItem()));
 //BA.debugLineNum = 288;BA.debugLine="If IsThereMiscodedRecords(GlobalVar.iReaderID) =";
if (_istheremiscodedrecords(mostCurrent._globalvar._ireaderid /*int*/ )==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 289;BA.debugLine="WarningMsg($\"NO RECORD FOUND\"$,$\"No Miscoded rea";
_warningmsg(("NO RECORD FOUND"),("No Miscoded reading's found!"));
 //BA.debugLineNum = 290;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 292;BA.debugLine="iValidType = 1";
_ivalidtype = (int) (1);
 //BA.debugLineNum = 293;BA.debugLine="StartActivity(ValidationRptGenerator)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._validationrptgenerator.getObject()));
 //BA.debugLineNum = 294;BA.debugLine="End Sub";
return "";
}
public static String  _btnunprinted_click() throws Exception{
 //BA.debugLineNum = 341;BA.debugLine="Sub btnUnprinted_Click";
 //BA.debugLineNum = 342;BA.debugLine="If cboReader.SelectedItem = \"\" Then";
if ((mostCurrent._cboreader.getSelectedItem()).equals("")) { 
 //BA.debugLineNum = 343;BA.debugLine="WarningMsg($\"NO READER SELECTED\"$,$\"Select the r";
_warningmsg(("NO READER SELECTED"),("Select the reader first!"));
 //BA.debugLineNum = 344;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 346;BA.debugLine="GlobalVar.iReaderID = DBaseFunctions.GetIDbyCode(";
mostCurrent._globalvar._ireaderid /*int*/  = (int) (mostCurrent._dbasefunctions._getidbycode /*double*/ (mostCurrent.activityBA,"UserID","tblUsers","EmpName",mostCurrent._cboreader.getSelectedItem()));
 //BA.debugLineNum = 348;BA.debugLine="If IsThereUnprintBillRecords(GlobalVar.iReaderID)";
if (_isthereunprintbillrecords(mostCurrent._globalvar._ireaderid /*int*/ )==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 349;BA.debugLine="WarningMsg($\"NO RECORD FOUND\"$,$\"No Unprinted Bi";
_warningmsg(("NO RECORD FOUND"),("No Unprinted Bill found!"));
 //BA.debugLineNum = 350;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 352;BA.debugLine="iValidType = 5";
_ivalidtype = (int) (5);
 //BA.debugLineNum = 353;BA.debugLine="StartActivity(ValidationRptGenerator)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._validationrptgenerator.getObject()));
 //BA.debugLineNum = 354;BA.debugLine="End Sub";
return "";
}
public static String  _btnzerocons_click() throws Exception{
 //BA.debugLineNum = 266;BA.debugLine="Sub btnZeroCons_Click";
 //BA.debugLineNum = 267;BA.debugLine="If cboReader.SelectedItem = \"\" Then";
if ((mostCurrent._cboreader.getSelectedItem()).equals("")) { 
 //BA.debugLineNum = 268;BA.debugLine="WarningMsg($\"NO READER SELECTED\"$,$\"Select the r";
_warningmsg(("NO READER SELECTED"),("Select the reader first!"));
 //BA.debugLineNum = 269;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 271;BA.debugLine="GlobalVar.iReaderID = DBaseFunctions.GetIDbyCode(";
mostCurrent._globalvar._ireaderid /*int*/  = (int) (mostCurrent._dbasefunctions._getidbycode /*double*/ (mostCurrent.activityBA,"UserID","tblUsers","EmpName",mostCurrent._cboreader.getSelectedItem()));
 //BA.debugLineNum = 273;BA.debugLine="If IsThereRecords($\"zero\"$,GlobalVar.iReaderID) =";
if (_isthererecords(("zero"),mostCurrent._globalvar._ireaderid /*int*/ )==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 274;BA.debugLine="WarningMsg($\"NO RECORD FOUND\"$,$\"No Zero Consump";
_warningmsg(("NO RECORD FOUND"),("No Zero Consumption reading's found!"));
 //BA.debugLineNum = 275;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 277;BA.debugLine="iValidType = 0";
_ivalidtype = (int) (0);
 //BA.debugLineNum = 278;BA.debugLine="StartActivity(ValidationRptGenerator)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._validationrptgenerator.getObject()));
 //BA.debugLineNum = 279;BA.debugLine="End Sub";
return "";
}
public static String  _cboreader_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 477;BA.debugLine="Sub cboReader_ItemClick (Position As Int, Value As";
 //BA.debugLineNum = 478;BA.debugLine="GlobalVar.iReaderID = DBaseFunctions.GetIDbyCode(";
mostCurrent._globalvar._ireaderid /*int*/  = (int) (mostCurrent._dbasefunctions._getidbycode /*double*/ (mostCurrent.activityBA,"UserID","tblUsers","EmpName",mostCurrent._cboreader.getSelectedItem()));
 //BA.debugLineNum = 480;BA.debugLine="GetValidationCount(GlobalVar.iReaderID, GlobalVar";
_getvalidationcount(mostCurrent._globalvar._ireaderid /*int*/ ,mostCurrent._globalvar._branchid /*int*/ ,(int) (mostCurrent._globalvar._billyear /*double*/ ),mostCurrent._globalvar._billmonth /*int*/ );
 //BA.debugLineNum = 482;BA.debugLine="MyBadges.SetBadge(MiscodedIcon, iMisCodedCount)";
mostCurrent._mybadges._setbadge /*String*/ (mostCurrent._miscodedicon,_imiscodedcount);
 //BA.debugLineNum = 483;BA.debugLine="MyBadges.SetBadge(ZeroIcon, iZeroCount)";
mostCurrent._mybadges._setbadge /*String*/ (mostCurrent._zeroicon,_izerocount);
 //BA.debugLineNum = 484;BA.debugLine="MyBadges.SetBadge(LBIcon, iLBCount)";
mostCurrent._mybadges._setbadge /*String*/ (mostCurrent._lbicon,_ilbcount);
 //BA.debugLineNum = 485;BA.debugLine="MyBadges.SetBadge(HBIcon, iHBCount)";
mostCurrent._mybadges._setbadge /*String*/ (mostCurrent._hbicon,_ihbcount);
 //BA.debugLineNum = 486;BA.debugLine="MyBadges.SetBadge(ABIcon, iAvgCount)";
mostCurrent._mybadges._setbadge /*String*/ (mostCurrent._abicon,_iavgcount);
 //BA.debugLineNum = 487;BA.debugLine="MyBadges.SetBadge(UnprintedIcon, iUnprintCount)";
mostCurrent._mybadges._setbadge /*String*/ (mostCurrent._unprintedicon,_iunprintcount);
 //BA.debugLineNum = 488;BA.debugLine="End Sub";
return "";
}
public static String  _fillreaders() throws Exception{
int _i = 0;
 //BA.debugLineNum = 115;BA.debugLine="Private Sub FillReaders";
 //BA.debugLineNum = 116;BA.debugLine="Dim rsReaders As Cursor";
mostCurrent._rsreaders = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 117;BA.debugLine="cboReader.Clear";
mostCurrent._cboreader.Clear();
 //BA.debugLineNum = 119;BA.debugLine="Try";
try { //BA.debugLineNum = 120;BA.debugLine="Starter.strCriteria = \"SELECT Readings.ReadBy, U";
mostCurrent._starter._strcriteria /*String*/  = "SELECT Readings.ReadBy, Users.EmpName AS ReaderName "+"FROM tblReadings AS Readings "+"INNER JOIN tblUsers AS Users ON Readings.ReadBy = Users.UserID "+"GROUP BY Readings.ReadBy, Users.EmpName "+"ORDER BY Users.UserID";
 //BA.debugLineNum = 126;BA.debugLine="LogColor(Starter.strCriteria, Colors.Cyan)";
anywheresoftware.b4a.keywords.Common.LogImpl("155574539",mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.Colors.Cyan);
 //BA.debugLineNum = 127;BA.debugLine="rsReaders = Starter.DBCon.ExecQuery(Starter.strC";
mostCurrent._rsreaders = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 129;BA.debugLine="If rsReaders. RowCount > 0 Then";
if (mostCurrent._rsreaders.getRowCount()>0) { 
 //BA.debugLineNum = 130;BA.debugLine="For i = 0 To rsReaders.RowCount - 1";
{
final int step8 = 1;
final int limit8 = (int) (mostCurrent._rsreaders.getRowCount()-1);
_i = (int) (0) ;
for (;_i <= limit8 ;_i = _i + step8 ) {
 //BA.debugLineNum = 131;BA.debugLine="rsReaders.Position = i";
mostCurrent._rsreaders.setPosition(_i);
 //BA.debugLineNum = 132;BA.debugLine="cboReader.Add(rsReaders.GetString(\"ReaderName\"";
mostCurrent._cboreader.Add(BA.ObjectToCharSequence(mostCurrent._rsreaders.GetString("ReaderName")));
 }
};
 }else {
 };
 } 
       catch (Exception e15) {
			processBA.setLastException(e15); //BA.debugLineNum = 137;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("155574550",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 139;BA.debugLine="End Sub";
return "";
}
public static String  _getvalidationcount(int _iuserid,int _ibranchid,int _ibillyear,int _ibillmonth) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _rscursor = null;
 //BA.debugLineNum = 490;BA.debugLine="Private Sub GetValidationCount (iUserID As Int, iB";
 //BA.debugLineNum = 491;BA.debugLine="Dim rsCursor As Cursor";
_rscursor = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 493;BA.debugLine="Try";
try { //BA.debugLineNum = 494;BA.debugLine="Starter.strCriteria = \"SELECT SUM(CASE WHEN WasM";
mostCurrent._starter._strcriteria /*String*/  = "SELECT SUM(CASE WHEN WasMissCoded = 1 THEN 1 ELSE 0 END) as Miscoded, "+"SUM(CASE WHEN ImplosiveType = 'zero' Then 1 ELSE 0 END) As ZeroCons, "+"SUM(CASE WHEN ImplosiveType = 'implosive-inc' Then 1 ELSE 0 END) As HighBill, "+"SUM(CASE WHEN ImplosiveType = 'implosive-dec' Then 1 ELSE 0 END) As LowBill, "+"SUM(CASE WHEN BillType = 'AB' Then 1 ELSE 0 END) As AverageBill, "+"SUM(CASE WHEN (PrintStatus = 0 AND WasRead = 1) Then 1 ELSE 0 END) As Unprinted "+"FROM tblReadings "+"WHERE BranchID = "+BA.NumberToString(_ibranchid)+" "+"AND BillYear = "+BA.NumberToString(_ibillyear)+" "+"AND BillMonth = "+BA.NumberToString(_ibillmonth)+" "+"AND ReadBy = "+BA.NumberToString(_iuserid);
 //BA.debugLineNum = 506;BA.debugLine="LogColor(Starter.strCriteria, Colors.Yellow)";
anywheresoftware.b4a.keywords.Common.LogImpl("156885264",mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 //BA.debugLineNum = 507;BA.debugLine="rsCursor = Starter.DBCon.ExecQuery(Starter.strCr";
_rscursor = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 508;BA.debugLine="If rsCursor.RowCount > 0 Then";
if (_rscursor.getRowCount()>0) { 
 //BA.debugLineNum = 509;BA.debugLine="rsCursor.Position = 0";
_rscursor.setPosition((int) (0));
 //BA.debugLineNum = 510;BA.debugLine="iMisCodedCount = rsCursor.GetInt(\"Miscoded\")";
_imiscodedcount = _rscursor.GetInt("Miscoded");
 //BA.debugLineNum = 511;BA.debugLine="iZeroCount = rsCursor.GetInt(\"ZeroCons\")";
_izerocount = _rscursor.GetInt("ZeroCons");
 //BA.debugLineNum = 512;BA.debugLine="iLBCount = rsCursor.GetInt(\"LowBill\")";
_ilbcount = _rscursor.GetInt("LowBill");
 //BA.debugLineNum = 513;BA.debugLine="iHBCount = rsCursor.GetInt(\"HighBill\")";
_ihbcount = _rscursor.GetInt("HighBill");
 //BA.debugLineNum = 514;BA.debugLine="iAvgCount = rsCursor.GetInt(\"AverageBill\")";
_iavgcount = _rscursor.GetInt("AverageBill");
 //BA.debugLineNum = 515;BA.debugLine="iUnprintCount = rsCursor.GetInt(\"Unprinted\")";
_iunprintcount = _rscursor.GetInt("Unprinted");
 }else {
 //BA.debugLineNum = 517;BA.debugLine="rsCursor.Close";
_rscursor.Close();
 //BA.debugLineNum = 518;BA.debugLine="Return";
if (true) return "";
 };
 } 
       catch (Exception e19) {
			processBA.setLastException(e19); //BA.debugLineNum = 522;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("156885280",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 524;BA.debugLine="rsCursor.Close";
_rscursor.Close();
 //BA.debugLineNum = 525;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 22;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 23;BA.debugLine="Dim ActionBarButton As ACActionBar";
mostCurrent._actionbarbutton = new de.amberhome.objects.appcompat.ACActionBar();
 //BA.debugLineNum = 24;BA.debugLine="Private ToolBar As ACToolBarDark";
mostCurrent._toolbar = new de.amberhome.objects.appcompat.ACToolbarDarkWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private rsReaders As Cursor";
mostCurrent._rsreaders = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private iValidType As Int";
_ivalidtype = 0;
 //BA.debugLineNum = 31;BA.debugLine="Private btnAB As Panel";
mostCurrent._btnab = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private btnHB As Panel";
mostCurrent._btnhb = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private btnLB As Panel";
mostCurrent._btnlb = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private btnMiscoded As Panel";
mostCurrent._btnmiscoded = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private btnUnprinted As Panel";
mostCurrent._btnunprinted = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private btnZeroCons As Panel";
mostCurrent._btnzerocons = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private cboReader As ACSpinner";
mostCurrent._cboreader = new de.amberhome.objects.appcompat.ACSpinnerWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private MyBadges As Badger";
mostCurrent._mybadges = new com.bwsi.MeterReader.badger();
 //BA.debugLineNum = 40;BA.debugLine="Private iZeroCount, iMisCodedCount, iLBCount, iHB";
_izerocount = 0;
_imiscodedcount = 0;
_ilbcount = 0;
_ihbcount = 0;
_iavgcount = 0;
_iunprintcount = 0;
 //BA.debugLineNum = 41;BA.debugLine="Private MiscodedIcon As B4XView";
mostCurrent._miscodedicon = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Private ZeroIcon As B4XView";
mostCurrent._zeroicon = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private LBIcon As B4XView";
mostCurrent._lbicon = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Private HBIcon As B4XView";
mostCurrent._hbicon = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private ABIcon As B4XView";
mostCurrent._abicon = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Private UnprintedIcon As B4XView";
mostCurrent._unprintedicon = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 47;BA.debugLine="End Sub";
return "";
}
public static String  _infodialog_onbindview(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,int _viewtype) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alertdialog = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
anywheresoftware.b4a.objects.CSBuilder _cs = null;
 //BA.debugLineNum = 454;BA.debugLine="Private Sub InfoDialog_OnBindView (View As View, V";
 //BA.debugLineNum = 455;BA.debugLine="Dim AlertDialog As AX_CustomAlertDialog";
_alertdialog = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 456;BA.debugLine="AlertDialog.Initialize";
_alertdialog.Initialize();
 //BA.debugLineNum = 458;BA.debugLine="If ViewType = AlertDialog.VIEW_TITLE Then ' Title";
if (_viewtype==_alertdialog.VIEW_TITLE) { 
 //BA.debugLineNum = 459;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 460;BA.debugLine="Dim CS As CSBuilder";
_cs = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 462;BA.debugLine="CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Ty";
_cs.Initialize().Typeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD).Typeface(anywheresoftware.b4a.keywords.Common.Typeface.getMATERIALICONS()).Size((int) (26)).Color((int) (mostCurrent._globalvar._pricolor /*double*/ )).Append(BA.ObjectToCharSequence(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xe88e)))+"  "));
 //BA.debugLineNum = 463;BA.debugLine="CS.Typeface(GlobalVar.Font).Size(22).Append(lbl.";
_cs.Typeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).Size((int) (22)).Append(BA.ObjectToCharSequence(_lbl.getText())).Pop();
 //BA.debugLineNum = 465;BA.debugLine="lbl.Text = CS.PopAll";
_lbl.setText(BA.ObjectToCharSequence(_cs.PopAll().getObject()));
 };
 //BA.debugLineNum = 467;BA.debugLine="If ViewType = AlertDialog.VIEW_MESSAGE Then";
if (_viewtype==_alertdialog.VIEW_MESSAGE) { 
 //BA.debugLineNum = 468;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 469;BA.debugLine="lbl.TextSize = 16";
_lbl.setTextSize((float) (16));
 //BA.debugLineNum = 470;BA.debugLine="lbl.TextColor = Colors.Gray";
_lbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 };
 //BA.debugLineNum = 472;BA.debugLine="End Sub";
return "";
}
public static String  _infomessage_onpositiveclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alertdialog = null;
 //BA.debugLineNum = 448;BA.debugLine="Private Sub InfoMessage_OnPositiveClicked (View As";
 //BA.debugLineNum = 449;BA.debugLine="Dim AlertDialog As AX_CustomAlertDialog";
_alertdialog = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 451;BA.debugLine="AlertDialog.Initialize.Dismiss(Dialog)";
_alertdialog.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 452;BA.debugLine="End Sub";
return "";
}
public static String  _infomsg(String _stitle,String _smsg) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alertdialog = null;
 //BA.debugLineNum = 424;BA.debugLine="Private Sub InfoMsg(sTitle As String, sMsg As Stri";
 //BA.debugLineNum = 425;BA.debugLine="Dim AlertDialog As AX_CustomAlertDialog";
_alertdialog = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 427;BA.debugLine="AlertDialog.Initialize";
_alertdialog.Initialize();
 //BA.debugLineNum = 428;BA.debugLine="AlertDialog.Initialize.Create _ 			.SetDialogStyl";
_alertdialog.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialogDisableStatus").SetStyle(_alertdialog.getSTYLE_DIALOGUE()).SetCancelable(anywheresoftware.b4a.keywords.Common.False).SetTitle(_stitle).SetTitleColor(anywheresoftware.b4a.keywords.Common.Colors.Red).SetTitleTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessage(_smsg).SetPositiveText("OK").SetPositiveColor(anywheresoftware.b4a.keywords.Common.Colors.Blue).SetPositiveTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessageTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOnPositiveClicked(mostCurrent.activityBA,"InfoMessage").SetOnViewBinder(mostCurrent.activityBA,"InfoDialog");
 //BA.debugLineNum = 443;BA.debugLine="AlertDialog.SetDialogBackground(myCD)";
_alertdialog.SetDialogBackground((android.graphics.drawable.Drawable)(_mycd().getObject()));
 //BA.debugLineNum = 444;BA.debugLine="AlertDialog.Build.Show";
_alertdialog.Build().Show();
 //BA.debugLineNum = 446;BA.debugLine="End Sub";
return "";
}
public static boolean  _isthereavgrecords(int _readerid) throws Exception{
boolean _bretval = false;
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsvalidation = null;
 //BA.debugLineNum = 202;BA.debugLine="Private Sub IsThereAvgRecords(ReaderID As Int) As";
 //BA.debugLineNum = 203;BA.debugLine="Dim bRetVal As Boolean";
_bretval = false;
 //BA.debugLineNum = 204;BA.debugLine="Dim rsValidation As Cursor";
_rsvalidation = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 206;BA.debugLine="Try";
try { //BA.debugLineNum = 207;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 208;BA.debugLine="Starter.strCriteria = \"SELECT * FROM tblReadings";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblReadings "+"WHERE ReadBy = "+BA.NumberToString(_readerid)+" "+"AND BranchID = "+BA.NumberToString(mostCurrent._globalvar._branchid /*int*/ )+" "+"AND BillYear = "+BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ )+" "+"AND BillMonth = "+BA.NumberToString(mostCurrent._globalvar._billmonth /*int*/ )+" "+"AND BillType = 'AB' "+"AND WasRead = 1 "+"AND PrintStatus = 1";
 //BA.debugLineNum = 216;BA.debugLine="LogColor(Starter.strCriteria, Colors.Cyan)";
anywheresoftware.b4a.keywords.Common.LogImpl("155771150",mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.Colors.Cyan);
 //BA.debugLineNum = 218;BA.debugLine="rsValidation = Starter.DBCon.ExecQuery(Starter.s";
_rsvalidation = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 220;BA.debugLine="If rsValidation.RowCount > 0 Then";
if (_rsvalidation.getRowCount()>0) { 
 //BA.debugLineNum = 221;BA.debugLine="bRetVal = True";
_bretval = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 223;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 };
 } 
       catch (Exception e14) {
			processBA.setLastException(e14); //BA.debugLineNum = 226;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("155771160",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 //BA.debugLineNum = 227;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 229;BA.debugLine="rsValidation.Close";
_rsvalidation.Close();
 //BA.debugLineNum = 230;BA.debugLine="Return bRetVal";
if (true) return _bretval;
 //BA.debugLineNum = 231;BA.debugLine="End Sub";
return false;
}
public static boolean  _istheremiscodedrecords(int _readerid) throws Exception{
boolean _bretval = false;
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsvalidation = null;
 //BA.debugLineNum = 140;BA.debugLine="Private Sub IsThereMiscodedRecords(ReaderID As Int";
 //BA.debugLineNum = 141;BA.debugLine="Dim bRetVal As Boolean";
_bretval = false;
 //BA.debugLineNum = 142;BA.debugLine="Dim rsValidation As Cursor";
_rsvalidation = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 144;BA.debugLine="Try";
try { //BA.debugLineNum = 145;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 146;BA.debugLine="Starter.strCriteria = \"SELECT * FROM tblReadings";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblReadings "+"WHERE ReadBy = "+BA.NumberToString(_readerid)+" "+"AND BranchID = "+BA.NumberToString(mostCurrent._globalvar._branchid /*int*/ )+" "+"AND BillYear = "+BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ )+" "+"AND BillMonth = "+BA.NumberToString(mostCurrent._globalvar._billmonth /*int*/ )+" "+"AND WasRead = 1 "+"AND WasMissCoded = 1";
 //BA.debugLineNum = 153;BA.debugLine="LogColor(Starter.strCriteria, Colors.Cyan)";
anywheresoftware.b4a.keywords.Common.LogImpl("155640077",mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.Colors.Cyan);
 //BA.debugLineNum = 155;BA.debugLine="rsValidation = Starter.DBCon.ExecQuery(Starter.s";
_rsvalidation = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 157;BA.debugLine="If rsValidation.RowCount > 0 Then";
if (_rsvalidation.getRowCount()>0) { 
 //BA.debugLineNum = 158;BA.debugLine="bRetVal = True";
_bretval = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 160;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 };
 } 
       catch (Exception e14) {
			processBA.setLastException(e14); //BA.debugLineNum = 163;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("155640087",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 //BA.debugLineNum = 164;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 166;BA.debugLine="rsValidation.Close";
_rsvalidation.Close();
 //BA.debugLineNum = 167;BA.debugLine="Return bRetVal";
if (true) return _bretval;
 //BA.debugLineNum = 168;BA.debugLine="End Sub";
return false;
}
public static boolean  _isthererecords(String _svalidationtype,int _readerid) throws Exception{
boolean _bretval = false;
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsvalidation = null;
 //BA.debugLineNum = 170;BA.debugLine="Private Sub IsThereRecords(sValidationType As Stri";
 //BA.debugLineNum = 171;BA.debugLine="Dim bRetVal As Boolean";
_bretval = false;
 //BA.debugLineNum = 172;BA.debugLine="Dim rsValidation As Cursor";
_rsvalidation = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 174;BA.debugLine="Try";
try { //BA.debugLineNum = 175;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 176;BA.debugLine="Starter.strCriteria = \"SELECT * FROM tblReadings";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblReadings "+"WHERE ImplosiveType = '"+_svalidationtype+"' "+"AND ReadBy = "+BA.NumberToString(_readerid)+" "+"AND BranchID = "+BA.NumberToString(mostCurrent._globalvar._branchid /*int*/ )+" "+"AND BillYear = "+BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ )+" "+"AND BillMonth = "+BA.NumberToString(mostCurrent._globalvar._billmonth /*int*/ )+" "+"AND BillType = 'RB' "+"AND WasRead = 1 "+"AND PrintStatus = 1";
 //BA.debugLineNum = 185;BA.debugLine="LogColor(Starter.strCriteria, Colors.Cyan)";
anywheresoftware.b4a.keywords.Common.LogImpl("155705615",mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.Colors.Cyan);
 //BA.debugLineNum = 187;BA.debugLine="rsValidation = Starter.DBCon.ExecQuery(Starter.s";
_rsvalidation = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 189;BA.debugLine="If rsValidation.RowCount > 0 Then";
if (_rsvalidation.getRowCount()>0) { 
 //BA.debugLineNum = 190;BA.debugLine="bRetVal = True";
_bretval = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 192;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 };
 } 
       catch (Exception e14) {
			processBA.setLastException(e14); //BA.debugLineNum = 195;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("155705625",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 //BA.debugLineNum = 196;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 198;BA.debugLine="rsValidation.Close";
_rsvalidation.Close();
 //BA.debugLineNum = 199;BA.debugLine="Return bRetVal";
if (true) return _bretval;
 //BA.debugLineNum = 200;BA.debugLine="End Sub";
return false;
}
public static boolean  _isthereunprintbillrecords(int _readerid) throws Exception{
boolean _bretval = false;
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsvalidation = null;
 //BA.debugLineNum = 233;BA.debugLine="Private Sub IsThereUnprintBillRecords(ReaderID As";
 //BA.debugLineNum = 234;BA.debugLine="Dim bRetVal As Boolean";
_bretval = false;
 //BA.debugLineNum = 235;BA.debugLine="Dim rsValidation As Cursor";
_rsvalidation = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 237;BA.debugLine="Try";
try { //BA.debugLineNum = 238;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 239;BA.debugLine="Starter.strCriteria = \"SELECT * FROM tblReadings";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblReadings "+"WHERE ReadBy = "+BA.NumberToString(_readerid)+" "+"AND BranchID = "+BA.NumberToString(mostCurrent._globalvar._branchid /*int*/ )+" "+"AND BillYear = "+BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ )+" "+"AND BillMonth = "+BA.NumberToString(mostCurrent._globalvar._billmonth /*int*/ )+" "+"AND WasRead = 1 "+"AND PrintStatus = 0";
 //BA.debugLineNum = 246;BA.debugLine="LogColor(Starter.strCriteria, Colors.Cyan)";
anywheresoftware.b4a.keywords.Common.LogImpl("155836685",mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.Colors.Cyan);
 //BA.debugLineNum = 248;BA.debugLine="rsValidation = Starter.DBCon.ExecQuery(Starter.s";
_rsvalidation = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 250;BA.debugLine="If rsValidation.RowCount > 0 Then";
if (_rsvalidation.getRowCount()>0) { 
 //BA.debugLineNum = 251;BA.debugLine="bRetVal = True";
_bretval = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 253;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 };
 } 
       catch (Exception e14) {
			processBA.setLastException(e14); //BA.debugLineNum = 256;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("155836695",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 //BA.debugLineNum = 257;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 259;BA.debugLine="rsValidation.Close";
_rsvalidation.Close();
 //BA.debugLineNum = 260;BA.debugLine="Return bRetVal";
if (true) return _bretval;
 //BA.debugLineNum = 261;BA.debugLine="End Sub";
return false;
}
public static anywheresoftware.b4a.objects.drawable.ColorDrawable  _mycd() throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _mcd = null;
 //BA.debugLineNum = 418;BA.debugLine="Private Sub myCD As ColorDrawable";
 //BA.debugLineNum = 419;BA.debugLine="Dim mCD As ColorDrawable";
_mcd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 420;BA.debugLine="mCD.Initialize(Colors.RGB(240,240,240),0)";
_mcd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (240),(int) (240),(int) (240)),(int) (0));
 //BA.debugLineNum = 421;BA.debugLine="Return mCD";
if (true) return _mcd;
 //BA.debugLineNum = 422;BA.debugLine="End Sub";
return null;
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 18;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 19;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 20;BA.debugLine="End Sub";
return "";
}
public static String  _titlecase(String _s) throws Exception{
anywheresoftware.b4a.keywords.Regex.MatcherWrapper _m = null;
int _i = 0;
 //BA.debugLineNum = 358;BA.debugLine="Sub TitleCase (s As String) As String";
 //BA.debugLineNum = 359;BA.debugLine="s = s.ToLowerCase";
_s = _s.toLowerCase();
 //BA.debugLineNum = 360;BA.debugLine="Dim m As Matcher = Regex.Matcher(\"\\b(\\w)\", s)";
_m = new anywheresoftware.b4a.keywords.Regex.MatcherWrapper();
_m = anywheresoftware.b4a.keywords.Common.Regex.Matcher("\\b(\\w)",_s);
 //BA.debugLineNum = 361;BA.debugLine="Do While m.Find";
while (_m.Find()) {
 //BA.debugLineNum = 362;BA.debugLine="Dim i As Int = m.GetStart(1)";
_i = _m.GetStart((int) (1));
 //BA.debugLineNum = 363;BA.debugLine="s = s.SubString2(0, i) & s.SubString2(i, i + 1).";
_s = _s.substring((int) (0),_i)+_s.substring(_i,(int) (_i+1)).toUpperCase()+_s.substring((int) (_i+1));
 }
;
 //BA.debugLineNum = 365;BA.debugLine="Return s";
if (true) return _s;
 //BA.debugLineNum = 366;BA.debugLine="End Sub";
return "";
}
public static String  _toolbar_navigationitemclick() throws Exception{
 //BA.debugLineNum = 101;BA.debugLine="Sub ToolBar_NavigationItemClick";
 //BA.debugLineNum = 102;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 103;BA.debugLine="End Sub";
return "";
}
public static String  _warningdialog_onbindview(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,int _viewtype) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alertdialog = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
anywheresoftware.b4a.objects.CSBuilder _cs = null;
 //BA.debugLineNum = 398;BA.debugLine="Private Sub WarningDialog_OnBindView (View As View";
 //BA.debugLineNum = 399;BA.debugLine="Dim AlertDialog As AX_CustomAlertDialog";
_alertdialog = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 400;BA.debugLine="AlertDialog.Initialize";
_alertdialog.Initialize();
 //BA.debugLineNum = 402;BA.debugLine="If ViewType = AlertDialog.VIEW_TITLE Then ' Title";
if (_viewtype==_alertdialog.VIEW_TITLE) { 
 //BA.debugLineNum = 403;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 404;BA.debugLine="Dim CS As CSBuilder";
_cs = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 406;BA.debugLine="CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Ty";
_cs.Initialize().Typeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD).Typeface(anywheresoftware.b4a.keywords.Common.Typeface.getMATERIALICONS()).Size((int) (26)).Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xe002)))+"  "));
 //BA.debugLineNum = 407;BA.debugLine="CS.Typeface(GlobalVar.Font).Size(22).Append(lbl.";
_cs.Typeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).Size((int) (22)).Append(BA.ObjectToCharSequence(_lbl.getText())).Pop();
 //BA.debugLineNum = 409;BA.debugLine="lbl.Text = CS.PopAll";
_lbl.setText(BA.ObjectToCharSequence(_cs.PopAll().getObject()));
 };
 //BA.debugLineNum = 411;BA.debugLine="If ViewType = AlertDialog.VIEW_MESSAGE Then";
if (_viewtype==_alertdialog.VIEW_MESSAGE) { 
 //BA.debugLineNum = 412;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 413;BA.debugLine="lbl.TextSize = 16";
_lbl.setTextSize((float) (16));
 //BA.debugLineNum = 414;BA.debugLine="lbl.TextColor = Colors.Gray";
_lbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 };
 //BA.debugLineNum = 416;BA.debugLine="End Sub";
return "";
}
public static String  _warningmessage_onpositiveclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alertdialog = null;
 //BA.debugLineNum = 392;BA.debugLine="Private Sub WarningMessage_OnPositiveClicked (View";
 //BA.debugLineNum = 393;BA.debugLine="Dim AlertDialog As AX_CustomAlertDialog";
_alertdialog = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 395;BA.debugLine="AlertDialog.Initialize.Dismiss(Dialog)";
_alertdialog.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 396;BA.debugLine="End Sub";
return "";
}
public static String  _warningmsg(String _stitle,String _smsg) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alertdialog = null;
 //BA.debugLineNum = 368;BA.debugLine="Private Sub WarningMsg(sTitle As String, sMsg As S";
 //BA.debugLineNum = 369;BA.debugLine="Dim AlertDialog As AX_CustomAlertDialog";
_alertdialog = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 371;BA.debugLine="AlertDialog.Initialize";
_alertdialog.Initialize();
 //BA.debugLineNum = 372;BA.debugLine="AlertDialog.Initialize.Create _ 			.SetDialogStyl";
_alertdialog.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialogDisableStatus").SetStyle(_alertdialog.getSTYLE_DIALOGUE()).SetCancelable(anywheresoftware.b4a.keywords.Common.False).SetTitle(_stitle).SetTitleColor(anywheresoftware.b4a.keywords.Common.Colors.Red).SetTitleTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessage(_smsg).SetPositiveText("OK").SetPositiveColor(anywheresoftware.b4a.keywords.Common.Colors.Blue).SetPositiveTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessageTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOnPositiveClicked(mostCurrent.activityBA,"WarningMessage").SetOnViewBinder(mostCurrent.activityBA,"WarningDialog");
 //BA.debugLineNum = 387;BA.debugLine="AlertDialog.SetDialogBackground(myCD)";
_alertdialog.SetDialogBackground((android.graphics.drawable.Drawable)(_mycd().getObject()));
 //BA.debugLineNum = 388;BA.debugLine="AlertDialog.Build.Show";
_alertdialog.Build().Show();
 //BA.debugLineNum = 390;BA.debugLine="End Sub";
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
