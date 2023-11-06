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

public class validationrptgenerator extends androidx.appcompat.app.AppCompatActivity implements B4AActivity{
	public static validationrptgenerator mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "com.bwsi.MeterReader", "com.bwsi.MeterReader.validationrptgenerator");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (validationrptgenerator).");
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
		activityBA = new BA(this, layout, processBA, "com.bwsi.MeterReader", "com.bwsi.MeterReader.validationrptgenerator");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "com.bwsi.MeterReader.validationrptgenerator", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (validationrptgenerator) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (validationrptgenerator) Resume **");
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
		return validationrptgenerator.class;
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
            BA.LogInfo("** Activity (validationrptgenerator) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (validationrptgenerator) Pause event (activity is not paused). **");
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
            validationrptgenerator mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (validationrptgenerator) Resume **");
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
public static String _pdfcontent = "";
public de.amberhome.objects.appcompat.ACActionBar _actionbarbutton = null;
public de.amberhome.objects.appcompat.ACToolbarDarkWrapper _toolbar = null;
public anywheresoftware.b4a.object.XmlLayoutBuilder _xmlicon = null;
public de.amberhome.materialdialogs.MaterialDialogBuilderWrapper _matdialogbuilder = null;
public com.bwsi.MeterReader.classcustomlistview _clv1 = null;
public static int _ireportflag = 0;
public anywheresoftware.b4a.objects.PanelWrapper _pnlcontent = null;
public anywheresoftware.b4a.sql.SQL.CursorWrapper _rsmisscoded = null;
public anywheresoftware.b4a.sql.SQL.CursorWrapper _rshighbill = null;
public anywheresoftware.b4a.sql.SQL.CursorWrapper _rslowbill = null;
public anywheresoftware.b4a.sql.SQL.CursorWrapper _rszerocons = null;
public anywheresoftware.b4a.sql.SQL.CursorWrapper _rsavebill = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblaccountname = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblaccountno = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbladdress = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblbookno = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcum = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblmeterno = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpresrdg = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblprevrdg = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblreason = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblseqno = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlcustomers = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblreccount = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlstatus = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnexport = null;
public com.rootsoft.pdfwriter.myPDFWriter _pdfwriter1 = null;
public com.rootsoft.pdfwriter.PaperSize _papersize = null;
public com.rootsoft.pdfwriter.StandardFonts _fonts = null;
public de.amberhome.objects.SnackbarWrapper _snack = null;
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
public com.bwsi.MeterReader.readingsettings _readingsettings = null;
public com.bwsi.MeterReader.readingvalidation _readingvalidation = null;
public com.bwsi.MeterReader.starter _starter = null;
public com.bwsi.MeterReader.useraccountsettings _useraccountsettings = null;

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
 //BA.debugLineNum = 74;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 75;BA.debugLine="MyScale.SetRate(0.5)";
mostCurrent._myscale._setrate /*String*/ (mostCurrent.activityBA,0.5);
 //BA.debugLineNum = 76;BA.debugLine="Activity.LoadLayout(\"ReportGenerator\")";
mostCurrent._activity.LoadLayout("ReportGenerator",mostCurrent.activityBA);
 //BA.debugLineNum = 78;BA.debugLine="Select GlobalVar.iReportLayout";
switch (BA.switchObjectToInt(mostCurrent._globalvar._ireportlayout /*int*/ ,(int) (0),(int) (1),(int) (2),(int) (3),(int) (4))) {
case 0: {
 //BA.debugLineNum = 80;BA.debugLine="GlobalVar.CSTitle.Initialize.Size(17).Bold.Appe";
mostCurrent._globalvar._cstitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (17)).Bold().Append(BA.ObjectToCharSequence(("Erroneous/Misscoded Readings"))).PopAll();
 //BA.debugLineNum = 81;BA.debugLine="GlobalVar.CSSubTitle.Initialize.Size(14).Append";
mostCurrent._globalvar._cssubtitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (14)).Append(BA.ObjectToCharSequence(("List of Erroneous/Misscoded Readings"))).PopAll();
 //BA.debugLineNum = 82;BA.debugLine="iReportFlag = 0";
_ireportflag = (int) (0);
 break; }
case 1: {
 //BA.debugLineNum = 85;BA.debugLine="GlobalVar.CSTitle.Initialize.Size(17).Bold.Appe";
mostCurrent._globalvar._cstitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (17)).Bold().Append(BA.ObjectToCharSequence(("Implosive High Readings"))).PopAll();
 //BA.debugLineNum = 86;BA.debugLine="GlobalVar.CSSubTitle.Initialize.Size(14).Append";
mostCurrent._globalvar._cssubtitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (14)).Append(BA.ObjectToCharSequence(("List of Implosive High Readings"))).PopAll();
 //BA.debugLineNum = 87;BA.debugLine="iReportFlag = 1";
_ireportflag = (int) (1);
 break; }
case 2: {
 //BA.debugLineNum = 90;BA.debugLine="GlobalVar.CSTitle.Initialize.Size(17).Bold.Appe";
mostCurrent._globalvar._cstitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (17)).Bold().Append(BA.ObjectToCharSequence(("Implosive Low Readings"))).PopAll();
 //BA.debugLineNum = 91;BA.debugLine="GlobalVar.CSSubTitle.Initialize.Size(14).Append";
mostCurrent._globalvar._cssubtitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (14)).Append(BA.ObjectToCharSequence(("List of Implosive Low Readings"))).PopAll();
 //BA.debugLineNum = 92;BA.debugLine="iReportFlag = 2";
_ireportflag = (int) (2);
 break; }
case 3: {
 //BA.debugLineNum = 95;BA.debugLine="GlobalVar.CSTitle.Initialize.Size(17).Bold.Appe";
mostCurrent._globalvar._cstitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (17)).Bold().Append(BA.ObjectToCharSequence(("Zero Consumption Readings"))).PopAll();
 //BA.debugLineNum = 96;BA.debugLine="GlobalVar.CSSubTitle.Initialize.Size(14).Append";
mostCurrent._globalvar._cssubtitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (14)).Append(BA.ObjectToCharSequence(("List of Zero Consumption Readings"))).PopAll();
 //BA.debugLineNum = 97;BA.debugLine="iReportFlag = 3";
_ireportflag = (int) (3);
 break; }
case 4: {
 //BA.debugLineNum = 100;BA.debugLine="GlobalVar.CSTitle.Initialize.Size(17).Bold.Appe";
mostCurrent._globalvar._cstitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (17)).Bold().Append(BA.ObjectToCharSequence(("Average Bill Readings"))).PopAll();
 //BA.debugLineNum = 101;BA.debugLine="GlobalVar.CSSubTitle.Initialize.Size(14).Append";
mostCurrent._globalvar._cssubtitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (14)).Append(BA.ObjectToCharSequence(("List of Average Bill Readings"))).PopAll();
 //BA.debugLineNum = 102;BA.debugLine="iReportFlag = 4";
_ireportflag = (int) (4);
 //BA.debugLineNum = 103;BA.debugLine="DisplayRecords(GlobalVar.BranchID, GlobalVar.Bi";
_displayrecords(mostCurrent._globalvar._branchid /*int*/ ,(int) (mostCurrent._globalvar._billyear /*double*/ ),mostCurrent._globalvar._billmonth /*int*/ ,mostCurrent._globalvar._ireaderid /*int*/ ,mostCurrent._globalvar._ireportlayout /*int*/ );
 break; }
}
;
 //BA.debugLineNum = 106;BA.debugLine="If FirstTime Then";
if (_firsttime) { 
 //BA.debugLineNum = 107;BA.debugLine="PDFWriter1.Initialize(\"PDFWriter1\",PaperSize.LET";
mostCurrent._pdfwriter1.Initialize(processBA,"PDFWriter1",mostCurrent._papersize.LETTER_WIDTH,mostCurrent._papersize.LETTER_HEIGHT);
 };
 //BA.debugLineNum = 110;BA.debugLine="ToolBar.InitMenuListener";
mostCurrent._toolbar.InitMenuListener();
 //BA.debugLineNum = 111;BA.debugLine="ToolBar.Title = GlobalVar.CSTitle";
mostCurrent._toolbar.setTitle(BA.ObjectToCharSequence(mostCurrent._globalvar._cstitle /*anywheresoftware.b4a.objects.CSBuilder*/ .getObject()));
 //BA.debugLineNum = 112;BA.debugLine="ToolBar.SubTitle = GlobalVar.CSSubTitle";
mostCurrent._toolbar.setSubTitle(BA.ObjectToCharSequence(mostCurrent._globalvar._cssubtitle /*anywheresoftware.b4a.objects.CSBuilder*/ .getObject()));
 //BA.debugLineNum = 113;BA.debugLine="ToolBar.SetElevationAnimated(5,5dip)";
mostCurrent._toolbar.SetElevationAnimated((int) (5),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 115;BA.debugLine="Dim jo As JavaObject";
_jo = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 116;BA.debugLine="Dim xl As XmlLayoutBuilder";
_xl = new anywheresoftware.b4a.object.XmlLayoutBuilder();
 //BA.debugLineNum = 117;BA.debugLine="jo = ToolBar";
_jo = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(mostCurrent._toolbar.getObject()));
 //BA.debugLineNum = 118;BA.debugLine="jo.RunMethod(\"setPopupTheme\", Array(xl.GetResourc";
_jo.RunMethod("setPopupTheme",new Object[]{(Object)(_xl.GetResourceId("style","ToolbarMenu"))});
 //BA.debugLineNum = 119;BA.debugLine="jo.RunMethod(\"setContentInsetStartWithNavigation\"";
_jo.RunMethod("setContentInsetStartWithNavigation",new Object[]{(Object)(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)))});
 //BA.debugLineNum = 120;BA.debugLine="jo.RunMethod(\"setTitleMarginStart\", Array(0dip))";
_jo.RunMethod("setTitleMarginStart",new Object[]{(Object)(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)))});
 //BA.debugLineNum = 122;BA.debugLine="ActionBarButton.Initialize";
mostCurrent._actionbarbutton.Initialize(mostCurrent.activityBA);
 //BA.debugLineNum = 123;BA.debugLine="ActionBarButton.ShowUpIndicator = True";
mostCurrent._actionbarbutton.setShowUpIndicator(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 124;BA.debugLine="btnExport.Background = CreateButtonColor(0xFF0D47";
mostCurrent._btnexport.setBackground((android.graphics.drawable.Drawable)(_createbuttoncolor((int) (0xff0d47a1),(int) (0xff0d47a1),(int) (0xff1e88e5),(int) (0xff0d47a1)).getObject()));
 //BA.debugLineNum = 125;BA.debugLine="End Sub";
return "";
}
public static String  _activity_createmenu(de.amberhome.objects.appcompat.ACMenuWrapper _menu) throws Exception{
 //BA.debugLineNum = 18;BA.debugLine="Sub Activity_CreateMenu(Menu As ACMenu)";
 //BA.debugLineNum = 23;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 134;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 135;BA.debugLine="If KeyCode = 4 Then";
if (_keycode==4) { 
 //BA.debugLineNum = 136;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 }else {
 //BA.debugLineNum = 138;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 140;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 150;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 152;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 146;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 147;BA.debugLine="DisplayRecords(GlobalVar.BranchID, GlobalVar.Bill";
_displayrecords(mostCurrent._globalvar._branchid /*int*/ ,(int) (mostCurrent._globalvar._billyear /*double*/ ),mostCurrent._globalvar._billmonth /*int*/ ,mostCurrent._globalvar._ireaderid /*int*/ ,mostCurrent._globalvar._ireportlayout /*int*/ );
 //BA.debugLineNum = 148;BA.debugLine="End Sub";
return "";
}
public static String  _btnexport_click() throws Exception{
 //BA.debugLineNum = 284;BA.debugLine="Sub btnExport_Click";
 //BA.debugLineNum = 285;BA.debugLine="snack.Initialize(\"\", Activity, $\"Unable to export";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Unable to export PDF due to No PDF writer on this device. -joko"),mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 286;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 287;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 288;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 289;BA.debugLine="Return";
if (true) return "";
 //BA.debugLineNum = 404;BA.debugLine="End Sub";
return "";
}
public static String  _clv1_itemclick(int _index,Object _value) throws Exception{
 //BA.debugLineNum = 280;BA.debugLine="Sub CLV1_ItemClick (Index As Int, Value As Object)";
 //BA.debugLineNum = 282;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.drawable.StateListDrawable  _createbuttoncolor(int _focusedcolor,int _enabledcolor,int _disabledcolor,int _pressedcolor) throws Exception{
anywheresoftware.b4a.objects.drawable.StateListDrawable _retcolor = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _drwfocusedcolor = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _drwenabledcolor = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _drwdisabledcolor = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _drwpressedcolor = null;
 //BA.debugLineNum = 154;BA.debugLine="Public Sub CreateButtonColor(FocusedColor As Int,";
 //BA.debugLineNum = 156;BA.debugLine="Dim RetColor As StateListDrawable";
_retcolor = new anywheresoftware.b4a.objects.drawable.StateListDrawable();
 //BA.debugLineNum = 157;BA.debugLine="RetColor.Initialize";
_retcolor.Initialize();
 //BA.debugLineNum = 158;BA.debugLine="Dim drwFocusedColor, drwEnabledColor, drwDisabled";
_drwfocusedcolor = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
_drwenabledcolor = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
_drwdisabledcolor = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
_drwpressedcolor = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 160;BA.debugLine="drwFocusedColor.Initialize2(FocusedColor, 25, 0,";
_drwfocusedcolor.Initialize2(_focusedcolor,(int) (25),(int) (0),anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 161;BA.debugLine="drwEnabledColor.Initialize2(EnabledColor, 25, 0,";
_drwenabledcolor.Initialize2(_enabledcolor,(int) (25),(int) (0),anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 162;BA.debugLine="drwDisabledColor.Initialize2(DisabledColor, 25, 2";
_drwdisabledcolor.Initialize2(_disabledcolor,(int) (25),(int) (2),anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 163;BA.debugLine="drwPressedColor.Initialize2(PressedColor, 25, 0,";
_drwpressedcolor.Initialize2(_pressedcolor,(int) (25),(int) (0),anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 165;BA.debugLine="RetColor.AddState(RetColor.State_Focused, drwFocu";
_retcolor.AddState(_retcolor.State_Focused,(android.graphics.drawable.Drawable)(_drwfocusedcolor.getObject()));
 //BA.debugLineNum = 166;BA.debugLine="RetColor.AddState(RetColor.State_Pressed, drwPres";
_retcolor.AddState(_retcolor.State_Pressed,(android.graphics.drawable.Drawable)(_drwpressedcolor.getObject()));
 //BA.debugLineNum = 167;BA.debugLine="RetColor.AddState(RetColor.State_Enabled, drwEnab";
_retcolor.AddState(_retcolor.State_Enabled,(android.graphics.drawable.Drawable)(_drwenabledcolor.getObject()));
 //BA.debugLineNum = 168;BA.debugLine="RetColor.AddState(RetColor.State_Disabled, drwDis";
_retcolor.AddState(_retcolor.State_Disabled,(android.graphics.drawable.Drawable)(_drwdisabledcolor.getObject()));
 //BA.debugLineNum = 169;BA.debugLine="RetColor.AddCatchAllState(drwFocusedColor)";
_retcolor.AddCatchAllState((android.graphics.drawable.Drawable)(_drwfocusedcolor.getObject()));
 //BA.debugLineNum = 170;BA.debugLine="RetColor.AddCatchAllState(drwEnabledColor)";
_retcolor.AddCatchAllState((android.graphics.drawable.Drawable)(_drwenabledcolor.getObject()));
 //BA.debugLineNum = 171;BA.debugLine="RetColor.AddCatchAllState(drwDisabledColor)";
_retcolor.AddCatchAllState((android.graphics.drawable.Drawable)(_drwdisabledcolor.getObject()));
 //BA.debugLineNum = 172;BA.debugLine="RetColor.AddCatchAllState(drwPressedColor)";
_retcolor.AddCatchAllState((android.graphics.drawable.Drawable)(_drwpressedcolor.getObject()));
 //BA.debugLineNum = 173;BA.debugLine="Return RetColor";
if (true) return _retcolor;
 //BA.debugLineNum = 175;BA.debugLine="End Sub";
return null;
}
public static anywheresoftware.b4a.objects.PanelWrapper  _createlist(int _iwidth,String _sacctno,String _sacctname,String _saddress,String _sbookno,String _sseqno,String _smeterno,int _ipresrdg,int _iprevrdg,int _icum,String _sreason) throws Exception{
anywheresoftware.b4a.objects.B4XViewWrapper _p = null;
int _iheight = 0;
 //BA.debugLineNum = 256;BA.debugLine="Sub CreateList(iWidth As Int, sAcctNo As String, s";
 //BA.debugLineNum = 257;BA.debugLine="Dim p As B4XView = xui.CreatePanel(\"\")";
_p = new anywheresoftware.b4a.objects.B4XViewWrapper();
_p = _xui.CreatePanel(processBA,"");
 //BA.debugLineNum = 259;BA.debugLine="Dim iHeight As Int = 164dip";
_iheight = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (164));
 //BA.debugLineNum = 261;BA.debugLine="If GetDeviceLayoutValues.ApproximateScreenSize <";
if (anywheresoftware.b4a.keywords.Common.GetDeviceLayoutValues(mostCurrent.activityBA).getApproximateScreenSize()<4.5) { 
_iheight = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (310));};
 //BA.debugLineNum = 262;BA.debugLine="p.SetLayoutAnimated(0, 0, 0, iWidth, iHeight)";
_p.SetLayoutAnimated((int) (0),(int) (0),(int) (0),_iwidth,_iheight);
 //BA.debugLineNum = 263;BA.debugLine="p.LoadLayout(\"ValidationReport\")";
_p.LoadLayout("ValidationReport",mostCurrent.activityBA);
 //BA.debugLineNum = 265;BA.debugLine="lblAccountNo.Text = sAcctNo";
mostCurrent._lblaccountno.setText(BA.ObjectToCharSequence(_sacctno));
 //BA.debugLineNum = 266;BA.debugLine="lblAccountName.Text = sAcctName";
mostCurrent._lblaccountname.setText(BA.ObjectToCharSequence(_sacctname));
 //BA.debugLineNum = 267;BA.debugLine="lblAddress.Text = GlobalVar.SF.Upper(sAddress)";
mostCurrent._lbladdress.setText(BA.ObjectToCharSequence(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv5(_saddress)));
 //BA.debugLineNum = 268;BA.debugLine="lblBookNo.Text = sBookNo";
mostCurrent._lblbookno.setText(BA.ObjectToCharSequence(_sbookno));
 //BA.debugLineNum = 269;BA.debugLine="lblSeqNo.Text = sSeqNo";
mostCurrent._lblseqno.setText(BA.ObjectToCharSequence(_sseqno));
 //BA.debugLineNum = 270;BA.debugLine="lblMeterNo.Text = sMeterNo";
mostCurrent._lblmeterno.setText(BA.ObjectToCharSequence(_smeterno));
 //BA.debugLineNum = 271;BA.debugLine="lblPresRdg.Text = iPresRdg";
mostCurrent._lblpresrdg.setText(BA.ObjectToCharSequence(_ipresrdg));
 //BA.debugLineNum = 272;BA.debugLine="lblPrevRdg.Text = iPrevRdg";
mostCurrent._lblprevrdg.setText(BA.ObjectToCharSequence(_iprevrdg));
 //BA.debugLineNum = 273;BA.debugLine="lblCum.Text = iCUM";
mostCurrent._lblcum.setText(BA.ObjectToCharSequence(_icum));
 //BA.debugLineNum = 274;BA.debugLine="lblReason.Text = sReason";
mostCurrent._lblreason.setText(BA.ObjectToCharSequence(_sreason));
 //BA.debugLineNum = 276;BA.debugLine="Return p";
if (true) return (anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(_p.getObject()));
 //BA.debugLineNum = 277;BA.debugLine="End Sub";
return null;
}
public static String  _dispinfomsg(String _smsg,String _stitle) throws Exception{
anywheresoftware.b4a.objects.CSBuilder _cstitle = null;
anywheresoftware.b4a.objects.CSBuilder _csmsg = null;
 //BA.debugLineNum = 406;BA.debugLine="Private Sub DispInfoMsg(sMsg As String, sTitle As";
 //BA.debugLineNum = 407;BA.debugLine="Dim csTitle, csMsg As CSBuilder";
_cstitle = new anywheresoftware.b4a.objects.CSBuilder();
_csmsg = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 408;BA.debugLine="csTitle.Initialize.Color(Colors.Red).Append(sTitl";
_cstitle.Initialize().Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(_stitle)).PopAll();
 //BA.debugLineNum = 409;BA.debugLine="csMsg.Initialize.Bold.Color(Colors.DarkGray).Appe";
_csmsg.Initialize().Bold().Color(anywheresoftware.b4a.keywords.Common.Colors.DarkGray).Append(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.CRLF+_smsg)).PopAll();
 //BA.debugLineNum = 411;BA.debugLine="Msgbox(csMsg, csTitle)";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence(_csmsg.getObject()),BA.ObjectToCharSequence(_cstitle.getObject()),mostCurrent.activityBA);
 //BA.debugLineNum = 412;BA.debugLine="End Sub";
return "";
}
public static String  _displayrecords(int _ibranchid,int _ibillyear,int _ibillmonth,int _iuserid,int _irpt) throws Exception{
String _sreason = "";
int _i = 0;
 //BA.debugLineNum = 177;BA.debugLine="Private Sub DisplayRecords(iBranchID As Int, iBill";
 //BA.debugLineNum = 178;BA.debugLine="Dim sReason As String";
_sreason = "";
 //BA.debugLineNum = 179;BA.debugLine="Try";
try { //BA.debugLineNum = 180;BA.debugLine="Select Case iRpt";
switch (_irpt) {
case 0: {
 //BA.debugLineNum = 182;BA.debugLine="Starter.strCriteria = \"SELECT ReadID, AcctNo,";
mostCurrent._starter._strcriteria /*String*/  = "SELECT ReadID, AcctNo, AcctName, AcctAddress, BookNo, SeqNo, MeterNo, OrigRdg as PresRdg, PrevRdg, (OrigRdg - PrevRdg) as PresCum, MissCode As Reason "+"FROM tblReadings "+"WHERE MissCoded = 1 "+"AND BranchID = "+BA.NumberToString(_ibranchid)+" "+"AND BillYear = "+BA.NumberToString(_ibillyear)+" "+"AND BillMonth = "+BA.NumberToString(_ibillmonth)+" "+"AND ReadBy = "+BA.NumberToString(_iuserid);
 break; }
case 1: {
 //BA.debugLineNum = 191;BA.debugLine="Starter.strCriteria = \"SELECT ReadID, AcctNo,";
mostCurrent._starter._strcriteria /*String*/  = "SELECT ReadID, AcctNo, AcctName, AcctAddress, BookNo, SeqNo, MeterNo, PresRdg, PrevRdg, PresCum, ImplosiveType As Reason "+"FROM tblReadings "+"WHERE ImplosiveType = '"+"implosive-inc"+"' "+"AND BranchID = "+BA.NumberToString(_ibranchid)+" "+"AND BillYear = "+BA.NumberToString(_ibillyear)+" "+"AND BillMonth = "+BA.NumberToString(_ibillmonth)+" "+"AND ReadBy = "+BA.NumberToString(_iuserid);
 break; }
case 2: {
 //BA.debugLineNum = 200;BA.debugLine="Starter.strCriteria = \"SELECT ReadID, AcctNo,";
mostCurrent._starter._strcriteria /*String*/  = "SELECT ReadID, AcctNo, AcctName, AcctAddress, BookNo, SeqNo, MeterNo, PresRdg, PrevRdg, PresCum, ImplosiveType As Reason "+"FROM tblReadings "+"WHERE ImplosiveType = '"+"implosive-dec"+"' "+"AND BranchID = "+BA.NumberToString(_ibranchid)+" "+"AND BillYear = "+BA.NumberToString(_ibillyear)+" "+"AND BillMonth = "+BA.NumberToString(_ibillmonth)+" "+"AND ReadBy = "+BA.NumberToString(_iuserid);
 break; }
case 3: {
 //BA.debugLineNum = 209;BA.debugLine="Starter.strCriteria = \"SELECT ReadID, AcctNo,";
mostCurrent._starter._strcriteria /*String*/  = "SELECT ReadID, AcctNo, AcctName, AcctAddress, BookNo, SeqNo, MeterNo, PresRdg, PrevRdg, PresCum, ReadRemarks As Reason "+"FROM tblReadings "+"WHERE ImplosiveType = '"+"zero"+"' "+"AND BranchID = "+BA.NumberToString(_ibranchid)+" "+"AND BillYear = "+BA.NumberToString(_ibillyear)+" "+"AND BillMonth = "+BA.NumberToString(_ibillmonth)+" "+"AND ReadBy = "+BA.NumberToString(_iuserid);
 break; }
case 4: {
 //BA.debugLineNum = 218;BA.debugLine="Starter.strCriteria = \"SELECT ReadID, AcctNo,";
mostCurrent._starter._strcriteria /*String*/  = "SELECT ReadID, AcctNo, AcctName, AcctAddress, BookNo, SeqNo, MeterNo, PresRdg, PrevRdg, PresCum, ReadRemarks As Reason "+"FROM tblReadings "+"WHERE BillType = '"+"AB"+"' "+"AND BranchID = "+BA.NumberToString(_ibranchid)+" "+"AND BillYear = "+BA.NumberToString(_ibillyear)+" "+"AND BillMonth = "+BA.NumberToString(_ibillmonth)+" "+"AND ReadBy = "+BA.NumberToString(_iuserid);
 break; }
}
;
 //BA.debugLineNum = 227;BA.debugLine="rsMissCoded = Starter.DBCon.ExecQuery(Starter.st";
mostCurrent._rsmisscoded = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 228;BA.debugLine="Log(rsMissCoded.RowCount)";
anywheresoftware.b4a.keywords.Common.LogImpl("761734963",BA.NumberToString(mostCurrent._rsmisscoded.getRowCount()),0);
 //BA.debugLineNum = 229;BA.debugLine="CLV1.Clear";
mostCurrent._clv1._clear /*String*/ ();
 //BA.debugLineNum = 230;BA.debugLine="If pnlCustomers.IsInitialized = False Then pnlCu";
if (mostCurrent._pnlcustomers.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
mostCurrent._pnlcustomers.Initialize(mostCurrent.activityBA,"");};
 //BA.debugLineNum = 231;BA.debugLine="If rsMissCoded.RowCount > 0 Then";
if (mostCurrent._rsmisscoded.getRowCount()>0) { 
 //BA.debugLineNum = 232;BA.debugLine="lblRecCount.Text = rsMissCoded.RowCount & $\" Re";
mostCurrent._lblreccount.setText(BA.ObjectToCharSequence(BA.NumberToString(mostCurrent._rsmisscoded.getRowCount())+(" Record(s) Found")));
 //BA.debugLineNum = 233;BA.debugLine="pnlCustomers.Visible = True";
mostCurrent._pnlcustomers.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 234;BA.debugLine="For i = 0 To rsMissCoded.RowCount - 1";
{
final int step22 = 1;
final int limit22 = (int) (mostCurrent._rsmisscoded.getRowCount()-1);
_i = (int) (0) ;
for (;_i <= limit22 ;_i = _i + step22 ) {
 //BA.debugLineNum = 235;BA.debugLine="rsMissCoded.Position = i";
mostCurrent._rsmisscoded.setPosition(_i);
 //BA.debugLineNum = 236;BA.debugLine="If iRpt = 1 Then";
if (_irpt==1) { 
 //BA.debugLineNum = 237;BA.debugLine="sReason = $\"Present consumption is more than";
_sreason = ("Present consumption is more than 20CuM.");
 }else if(_irpt==2) { 
 //BA.debugLineNum = 239;BA.debugLine="sReason = $\"Present consumption is less than";
_sreason = ("Present consumption is less than 20CuM.");
 }else {
 //BA.debugLineNum = 241;BA.debugLine="sReason = rsMissCoded.GetString(\"Reason\")";
_sreason = mostCurrent._rsmisscoded.GetString("Reason");
 };
 //BA.debugLineNum = 243;BA.debugLine="CLV1.Add(CreateList(CLV1.AsView.Width, rsMissC";
mostCurrent._clv1._add /*String*/ ((anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_createlist(mostCurrent._clv1._asview /*anywheresoftware.b4a.objects.B4XViewWrapper*/ ().getWidth(),mostCurrent._rsmisscoded.GetString("AcctNo"),mostCurrent._rsmisscoded.GetString("AcctName"),mostCurrent._rsmisscoded.GetString("AcctAddress"),mostCurrent._rsmisscoded.GetString("BookNo"),mostCurrent._rsmisscoded.GetString("SeqNo"),mostCurrent._rsmisscoded.GetString("MeterNo"),mostCurrent._rsmisscoded.GetInt("PresRdg"),mostCurrent._rsmisscoded.GetInt("PrevRdg"),mostCurrent._rsmisscoded.GetInt("PresCum"),_sreason).getObject())),(Object)(mostCurrent._rsmisscoded.GetInt("ReadID")));
 }
};
 }else {
 //BA.debugLineNum = 246;BA.debugLine="Log(rsMissCoded.RowCount)";
anywheresoftware.b4a.keywords.Common.LogImpl("761734981",BA.NumberToString(mostCurrent._rsmisscoded.getRowCount()),0);
 };
 } 
       catch (Exception e37) {
			processBA.setLastException(e37); //BA.debugLineNum = 249;BA.debugLine="rsMissCoded.Close";
mostCurrent._rsmisscoded.Close();
 //BA.debugLineNum = 250;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("761734985",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 252;BA.debugLine="rsMissCoded.Close";
mostCurrent._rsmisscoded.Close();
 //BA.debugLineNum = 253;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 254;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 30;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 31;BA.debugLine="Dim ActionBarButton As ACActionBar";
mostCurrent._actionbarbutton = new de.amberhome.objects.appcompat.ACActionBar();
 //BA.debugLineNum = 32;BA.debugLine="Private ToolBar As ACToolBarDark";
mostCurrent._toolbar = new de.amberhome.objects.appcompat.ACToolbarDarkWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private xmlIcon As XmlLayoutBuilder";
mostCurrent._xmlicon = new anywheresoftware.b4a.object.XmlLayoutBuilder();
 //BA.debugLineNum = 36;BA.debugLine="Private MatDialogBuilder As MaterialDialogBuilder";
mostCurrent._matdialogbuilder = new de.amberhome.materialdialogs.MaterialDialogBuilderWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private CLV1 As classCustomListView";
mostCurrent._clv1 = new com.bwsi.MeterReader.classcustomlistview();
 //BA.debugLineNum = 42;BA.debugLine="Private iReportFlag As Int";
_ireportflag = 0;
 //BA.debugLineNum = 43;BA.debugLine="Private pnlContent As Panel";
mostCurrent._pnlcontent = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Private rsMissCoded As Cursor";
mostCurrent._rsmisscoded = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private rsHighBill As Cursor";
mostCurrent._rshighbill = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Private rsLowBill As Cursor";
mostCurrent._rslowbill = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private rsZeroCons As Cursor";
mostCurrent._rszerocons = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Private rsAveBill As Cursor";
mostCurrent._rsavebill = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 51;BA.debugLine="Private lblAccountName As Label";
mostCurrent._lblaccountname = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 52;BA.debugLine="Private lblAccountNo As Label";
mostCurrent._lblaccountno = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 53;BA.debugLine="Private lblAddress As Label";
mostCurrent._lbladdress = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 54;BA.debugLine="Private lblBookNo As Label";
mostCurrent._lblbookno = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 55;BA.debugLine="Private lblCum As Label";
mostCurrent._lblcum = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 56;BA.debugLine="Private lblMeterNo As Label";
mostCurrent._lblmeterno = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 57;BA.debugLine="Private lblPresRdg As Label";
mostCurrent._lblpresrdg = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 58;BA.debugLine="Private lblPrevRdg As Label";
mostCurrent._lblprevrdg = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 59;BA.debugLine="Private lblReason As Label";
mostCurrent._lblreason = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 60;BA.debugLine="Private lblSeqNo As Label";
mostCurrent._lblseqno = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 62;BA.debugLine="Private pnlCustomers As Panel";
mostCurrent._pnlcustomers = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 63;BA.debugLine="Private lblRecCount As Label";
mostCurrent._lblreccount = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 64;BA.debugLine="Private pnlStatus As Panel";
mostCurrent._pnlstatus = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 65;BA.debugLine="Private btnExport As ACButton";
mostCurrent._btnexport = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 67;BA.debugLine="Private PDFWriter1 As PDFWriter";
mostCurrent._pdfwriter1 = new com.rootsoft.pdfwriter.myPDFWriter();
 //BA.debugLineNum = 68;BA.debugLine="Private PaperSize As PDFPaperSizes";
mostCurrent._papersize = new com.rootsoft.pdfwriter.PaperSize();
 //BA.debugLineNum = 69;BA.debugLine="Private Fonts As PDFStandardFonts";
mostCurrent._fonts = new com.rootsoft.pdfwriter.StandardFonts();
 //BA.debugLineNum = 70;BA.debugLine="Dim snack As DSSnackbar";
mostCurrent._snack = new de.amberhome.objects.SnackbarWrapper();
 //BA.debugLineNum = 72;BA.debugLine="End Sub";
return "";
}
public static String  _pdfwriter1_conversiondone(String _content) throws Exception{
 //BA.debugLineNum = 127;BA.debugLine="Sub PDFWriter1_ConversionDone (Content As String)";
 //BA.debugLineNum = 128;BA.debugLine="PDFContent = Content";
_pdfcontent = _content;
 //BA.debugLineNum = 129;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 130;BA.debugLine="ToastMessageShow(\"Conversion has been done.\",Fals";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Conversion has been done."),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 131;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 25;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 26;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 27;BA.debugLine="Dim PDFContent As String";
_pdfcontent = "";
 //BA.debugLineNum = 28;BA.debugLine="End Sub";
return "";
}
public static String  _setsnackbarbackground(de.amberhome.objects.SnackbarWrapper _psnack,int _pcolor) throws Exception{
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
 //BA.debugLineNum = 414;BA.debugLine="Sub SetSnackBarBackground(pSnack As DSSnackbar, pC";
 //BA.debugLineNum = 415;BA.debugLine="Dim v As View";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
 //BA.debugLineNum = 416;BA.debugLine="v = pSnack.View";
_v = (anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(_psnack.getView()));
 //BA.debugLineNum = 417;BA.debugLine="v.Color = pColor";
_v.setColor(_pcolor);
 //BA.debugLineNum = 418;BA.debugLine="End Sub";
return "";
}
public static String  _setsnackbartextcolor(de.amberhome.objects.SnackbarWrapper _psnack,int _pcolor) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _p = null;
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
anywheresoftware.b4a.objects.LabelWrapper _textv = null;
 //BA.debugLineNum = 420;BA.debugLine="Sub SetSnackBarTextColor(pSnack As DSSnackbar, pCo";
 //BA.debugLineNum = 421;BA.debugLine="Dim p As Panel";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 422;BA.debugLine="p = pSnack.View";
_p = (anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(_psnack.getView()));
 //BA.debugLineNum = 423;BA.debugLine="For Each v As View In p.GetAllViewsRecursive";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
{
final anywheresoftware.b4a.BA.IterableList group3 = _p.GetAllViewsRecursive();
final int groupLen3 = group3.getSize()
;int index3 = 0;
;
for (; index3 < groupLen3;index3++){
_v = (anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(group3.Get(index3)));
 //BA.debugLineNum = 424;BA.debugLine="If v Is Label Then";
if (_v.getObjectOrNull() instanceof android.widget.TextView) { 
 //BA.debugLineNum = 425;BA.debugLine="Dim textv As Label";
_textv = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 426;BA.debugLine="textv = v";
_textv = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_v.getObject()));
 //BA.debugLineNum = 427;BA.debugLine="textv.TextColor = pColor";
_textv.setTextColor(_pcolor);
 //BA.debugLineNum = 428;BA.debugLine="Exit";
if (true) break;
 };
 }
};
 //BA.debugLineNum = 431;BA.debugLine="End Sub";
return "";
}
public static String  _toolbar_navigationitemclick() throws Exception{
 //BA.debugLineNum = 142;BA.debugLine="Sub ToolBar_NavigationItemClick";
 //BA.debugLineNum = 143;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 144;BA.debugLine="End Sub";
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
