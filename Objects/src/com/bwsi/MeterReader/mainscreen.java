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

public class mainscreen extends androidx.appcompat.app.AppCompatActivity implements B4AActivity{
	public static mainscreen mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "com.bwsi.MeterReader", "com.bwsi.MeterReader.mainscreen");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (mainscreen).");
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
		activityBA = new BA(this, layout, processBA, "com.bwsi.MeterReader", "com.bwsi.MeterReader.mainscreen");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "com.bwsi.MeterReader.mainscreen", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (mainscreen) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (mainscreen) Resume **");
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
		return mainscreen.class;
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
            BA.LogInfo("** Activity (mainscreen) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (mainscreen) Pause event (activity is not paused). **");
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
            mainscreen mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (mainscreen) Resume **");
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
public anywheresoftware.b4a.objects.LabelWrapper _lblempname = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblbranchname = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblmessage = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblbillperiod = null;
public anywheresoftware.b4a.sql.SQL.CursorWrapper _rsassignedbooks = null;
public b4a.example3.customlistview _clvbookassignment = null;
public de.amberhome.objects.SnackbarWrapper _snack = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlassignment = null;
public anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
public com.johan.Vibrate.Vibrate _vibration = null;
public static long[] _vibratepattern = null;
public anywheresoftware.b4a.objects.CSBuilder _csans = null;
public com.bwsi.MeterReader.b4xdrawer _drawer = null;
public anywheresoftware.b4a.objects.ListViewWrapper _lvmenus = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlmenuheader = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbluserbranch = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbluserfullname = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblbookdesc = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblbookno = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblnoaccts = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblread = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblunread = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblmisscode = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblzero = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblhigh = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllow = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblave = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblunprint = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnload = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblreadtitle = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblunreadtitle = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblmisscodetitle = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblzerotitle = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllowtitle = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblhighttle = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblavetitle = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblunprinttitle = null;
public static double _redcolor = 0;
public static double _graycolor = 0;
public static double _bluecolor = 0;
public anywheresoftware.b4a.objects.PanelWrapper _btnbp = null;
public anywheresoftware.b4a.objects.PanelWrapper _btndl = null;
public anywheresoftware.b4a.objects.PanelWrapper _btnlogout = null;
public anywheresoftware.b4a.objects.PanelWrapper _btnul = null;
public anywheresoftware.b4a.objects.PanelWrapper _btnvalid = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlmenu = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlstatus = null;
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
public static class _readingstatus{
public boolean IsInitialized;
public String sBookNo;
public String sBookDesc;
public int iTotAccounts;
public int iTotRead;
public int iTotUnread;
public int iTotMisCoded;
public int iTotZeroCons;
public int iTotLowBill;
public int iTotHighBill;
public int iTotAveBill;
public int iTotUnprinted;
public void Initialize() {
IsInitialized = true;
sBookNo = "";
sBookDesc = "";
iTotAccounts = 0;
iTotRead = 0;
iTotUnread = 0;
iTotMisCoded = 0;
iTotZeroCons = 0;
iTotLowBill = 0;
iTotHighBill = 0;
iTotAveBill = 0;
iTotUnprinted = 0;
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}

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
 //BA.debugLineNum = 105;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 106;BA.debugLine="MyScale.SetRate(0.5)";
mostCurrent._myscale._setrate /*String*/ (mostCurrent.activityBA,0.5);
 //BA.debugLineNum = 107;BA.debugLine="Drawer.Initialize(Me,\"MainMenu\",Activity, 300dip)";
mostCurrent._drawer._initialize /*String*/ (mostCurrent.activityBA,mainscreen.getObject(),"MainMenu",(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (300)));
 //BA.debugLineNum = 108;BA.debugLine="Drawer.CenterPanel.LoadLayout(\"DashboardOrig\")";
mostCurrent._drawer._getcenterpanel /*anywheresoftware.b4a.objects.B4XViewWrapper*/ ().LoadLayout("DashboardOrig",mostCurrent.activityBA);
 //BA.debugLineNum = 109;BA.debugLine="modVariables.SourceSansProBold.Initialize(\"source";
mostCurrent._modvariables._sourcesansprobold /*njdude.fontawesome.lib.customfonts*/ ._initialize(processBA,"sourcesanspro-bold.ttf");
 //BA.debugLineNum = 110;BA.debugLine="modVariables.SourceSansProRegular.Initialize(\"sou";
mostCurrent._modvariables._sourcesansproregular /*njdude.fontawesome.lib.customfonts*/ ._initialize(processBA,"sourcesanspro-regular.ttf");
 //BA.debugLineNum = 113;BA.debugLine="GlobalVar.CSTitle.Initialize.Size(17).Bold.Typefa";
mostCurrent._globalvar._cstitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (17)).Bold().Typeface((android.graphics.Typeface)(mostCurrent._modvariables._sourcesansprobold /*njdude.fontawesome.lib.customfonts*/ ._setcustomfonts().getObject())).Append(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.Application.getLabelName())).PopAll();
 //BA.debugLineNum = 114;BA.debugLine="GlobalVar.CSSubTitle.Initialize.Size(14).Append(A";
mostCurrent._globalvar._cssubtitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (14)).Append(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.Application.getVersionName())).PopAll();
 //BA.debugLineNum = 116;BA.debugLine="ToolBar.InitMenuListener";
mostCurrent._toolbar.InitMenuListener();
 //BA.debugLineNum = 117;BA.debugLine="ToolBar.Title = GlobalVar.CSTitle";
mostCurrent._toolbar.setTitle(BA.ObjectToCharSequence(mostCurrent._globalvar._cstitle /*anywheresoftware.b4a.objects.CSBuilder*/ .getObject()));
 //BA.debugLineNum = 118;BA.debugLine="ToolBar.SubTitle = GlobalVar.CSSubTitle";
mostCurrent._toolbar.setSubTitle(BA.ObjectToCharSequence(mostCurrent._globalvar._cssubtitle /*anywheresoftware.b4a.objects.CSBuilder*/ .getObject()));
 //BA.debugLineNum = 120;BA.debugLine="Dim jo As JavaObject";
_jo = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 121;BA.debugLine="Dim xl As XmlLayoutBuilder";
_xl = new anywheresoftware.b4a.object.XmlLayoutBuilder();
 //BA.debugLineNum = 122;BA.debugLine="jo = ToolBar";
_jo = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(mostCurrent._toolbar.getObject()));
 //BA.debugLineNum = 123;BA.debugLine="jo.RunMethod(\"setPopupTheme\", Array(xl.GetResourc";
_jo.RunMethod("setPopupTheme",new Object[]{(Object)(_xl.GetResourceId("style","ToolbarMenu"))});
 //BA.debugLineNum = 124;BA.debugLine="jo.RunMethod(\"setContentInsetStartWithNavigation\"";
_jo.RunMethod("setContentInsetStartWithNavigation",new Object[]{(Object)(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)))});
 //BA.debugLineNum = 125;BA.debugLine="jo.RunMethod(\"setTitleMarginStart\", Array(0dip))";
_jo.RunMethod("setTitleMarginStart",new Object[]{(Object)(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)))});
 //BA.debugLineNum = 127;BA.debugLine="ActionBarButton.Initialize";
mostCurrent._actionbarbutton.Initialize(mostCurrent.activityBA);
 //BA.debugLineNum = 128;BA.debugLine="ActionBarButton.ShowUpIndicator = True";
mostCurrent._actionbarbutton.setShowUpIndicator(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 129;BA.debugLine="ActionBarButton.UpIndicatorBitmap = LoadBitmap(Fi";
mostCurrent._actionbarbutton.setUpIndicatorBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"hamburger.png").getObject()));
 //BA.debugLineNum = 131;BA.debugLine="lblEmpName.Text = TitleCase(GlobalVar.EmpName)";
mostCurrent._lblempname.setText(BA.ObjectToCharSequence(_titlecase(mostCurrent._globalvar._empname /*String*/ )));
 //BA.debugLineNum = 132;BA.debugLine="lblBranchName.Text =GlobalVar.SF.Upper(GlobalVar.";
mostCurrent._lblbranchname.setText(BA.ObjectToCharSequence(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv5(mostCurrent._globalvar._branchname /*String*/ )));
 //BA.debugLineNum = 133;BA.debugLine="lblBillPeriod.Text = GlobalVar.BillPeriod";
mostCurrent._lblbillperiod.setText(BA.ObjectToCharSequence(mostCurrent._globalvar._billperiod /*String*/ ));
 //BA.debugLineNum = 135;BA.debugLine="LogColor($\"Is First Time: \"$ & FirstTime, Colors.";
anywheresoftware.b4a.keywords.Common.LogImpl("432047134",("Is First Time: ")+BA.ObjectToString(_firsttime),anywheresoftware.b4a.keywords.Common.Colors.Cyan);
 //BA.debugLineNum = 137;BA.debugLine="If FirstTime = True Then";
if (_firsttime==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 138;BA.debugLine="If GlobalVar.Mod5 = 1 Then";
if (mostCurrent._globalvar._mod5 /*int*/ ==1) { 
 //BA.debugLineNum = 139;BA.debugLine="ShowWelcomeAdminDialog";
_showwelcomeadmindialog();
 }else {
 //BA.debugLineNum = 141;BA.debugLine="ShowWelcomeDialog";
_showwelcomedialog();
 };
 //BA.debugLineNum = 144;BA.debugLine="CreateMainMenu(GlobalVar.Mod1, GlobalVar.Mod2, G";
_createmainmenu(mostCurrent._globalvar._mod1 /*int*/ ,mostCurrent._globalvar._mod2 /*int*/ ,mostCurrent._globalvar._mod3 /*int*/ ,mostCurrent._globalvar._mod4 /*int*/ ,mostCurrent._globalvar._mod5 /*int*/ ,mostCurrent._globalvar._mod6 /*int*/ );
 //BA.debugLineNum = 146;BA.debugLine="snack.Initialize(\"\",Activity, $\"Welcome back to";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Welcome back to Meter Reader on Android!"),mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 147;BA.debugLine="SetSnackBarBackground(snack,GlobalVar.PriColor)";
_setsnackbarbackground(mostCurrent._snack,(int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 148;BA.debugLine="SetSnackBarTextColor(snack,Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 149;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 };
 //BA.debugLineNum = 153;BA.debugLine="csAns.Initialize.Color(Colors.White).Bold.Append(";
mostCurrent._csans.Initialize().Color(anywheresoftware.b4a.keywords.Common.Colors.White).Bold().Append(BA.ObjectToCharSequence(("YES"))).PopAll();
 //BA.debugLineNum = 154;BA.debugLine="Drawer.LeftPanel.LoadLayout(\"MainMenu\")";
mostCurrent._drawer._getleftpanel /*anywheresoftware.b4a.objects.B4XViewWrapper*/ ().LoadLayout("MainMenu",mostCurrent.activityBA);
 //BA.debugLineNum = 155;BA.debugLine="End Sub";
return "";
}
public static String  _activity_createmenu(de.amberhome.objects.appcompat.ACMenuWrapper _menu) throws Exception{
de.amberhome.objects.appcompat.ACMenuItemWrapper _item = null;
 //BA.debugLineNum = 18;BA.debugLine="Sub Activity_CreateMenu(Menu As ACMenu)";
 //BA.debugLineNum = 19;BA.debugLine="Private Item As ACMenuItem";
_item = new de.amberhome.objects.appcompat.ACMenuItemWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Menu.Clear";
_menu.Clear();
 //BA.debugLineNum = 21;BA.debugLine="Menu.Add2(1, 1, \"User Account\",xmlIcon.GetDrawabl";
_menu.Add2((int) (1),(int) (1),BA.ObjectToCharSequence("User Account"),mostCurrent._xmlicon.GetDrawable("ic_account_box_white_24dp")).setShowAsAction(_item.SHOW_AS_ACTION_IF_ROOM);
 //BA.debugLineNum = 22;BA.debugLine="Menu.Add2(2, 2, \"Settings\",xmlIcon.GetDrawable(\"i";
_menu.Add2((int) (2),(int) (2),BA.ObjectToCharSequence("Settings"),mostCurrent._xmlicon.GetDrawable("ic_settings_white_24dp")).setShowAsAction(_item.SHOW_AS_ACTION_ALWAYS);
 //BA.debugLineNum = 23;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 196;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 197;BA.debugLine="If KeyCode = 4 Then";
if (_keycode==4) { 
 //BA.debugLineNum = 205;BA.debugLine="ShowLogOffSnackBar";
_showlogoffsnackbar();
 //BA.debugLineNum = 206;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 208;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 210;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 192;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 193;BA.debugLine="If UserClosed Then ExitApplication";
if (_userclosed) { 
anywheresoftware.b4a.keywords.Common.ExitApplication();};
 //BA.debugLineNum = 194;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 158;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 159;BA.debugLine="CreateMainMenu(GlobalVar.Mod1, GlobalVar.Mod2, Gl";
_createmainmenu(mostCurrent._globalvar._mod1 /*int*/ ,mostCurrent._globalvar._mod2 /*int*/ ,mostCurrent._globalvar._mod3 /*int*/ ,mostCurrent._globalvar._mod4 /*int*/ ,mostCurrent._globalvar._mod5 /*int*/ ,mostCurrent._globalvar._mod6 /*int*/ );
 //BA.debugLineNum = 161;BA.debugLine="If DBaseFunctions.IsThereBookAssignments(GlobalVa";
if (mostCurrent._dbasefunctions._istherebookassignments /*boolean*/ (mostCurrent.activityBA,mostCurrent._globalvar._branchid /*int*/ ,(int) (mostCurrent._globalvar._billyear /*double*/ ),mostCurrent._globalvar._billmonth /*int*/ ,mostCurrent._globalvar._userid /*int*/ )==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 162;BA.debugLine="pnlMenu.Visible = False";
mostCurrent._pnlmenu.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 163;BA.debugLine="lblMessage.Visible = False";
mostCurrent._lblmessage.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 164;BA.debugLine="pnlStatus.Visible = False";
mostCurrent._pnlstatus.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 165;BA.debugLine="pnlAssignment.Visible = True";
mostCurrent._pnlassignment.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 166;BA.debugLine="FillBookAssignments(GlobalVar.UserID, GlobalVar.";
_fillbookassignments(mostCurrent._globalvar._userid /*int*/ ,mostCurrent._globalvar._branchid /*int*/ ,(int) (mostCurrent._globalvar._billyear /*double*/ ),mostCurrent._globalvar._billmonth /*int*/ );
 //BA.debugLineNum = 167;BA.debugLine="If DBaseFunctions.HasDownloadedData(GlobalVar.Us";
if (mostCurrent._dbasefunctions._hasdownloadeddata /*int*/ (mostCurrent.activityBA,mostCurrent._globalvar._userid /*int*/ )==0) { 
 //BA.debugLineNum = 168;BA.debugLine="ShowWarningDialog";
_showwarningdialog();
 };
 }else {
 //BA.debugLineNum = 171;BA.debugLine="pnlMenu.Visible = True";
mostCurrent._pnlmenu.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 172;BA.debugLine="pnlAssignment.Visible = False";
mostCurrent._pnlassignment.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 173;BA.debugLine="pnlStatus.Visible = True";
mostCurrent._pnlstatus.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 174;BA.debugLine="If GlobalVar.Mod5 = 1 Then";
if (mostCurrent._globalvar._mod5 /*int*/ ==1) { 
 //BA.debugLineNum = 175;BA.debugLine="pnlStatus.Color = GlobalVar.PriColor";
mostCurrent._pnlstatus.setColor((int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 176;BA.debugLine="lblMessage.TextColor = Colors.White";
mostCurrent._lblmessage.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 177;BA.debugLine="lblMessage.Text = \"Biller's Module\"";
mostCurrent._lblmessage.setText(BA.ObjectToCharSequence("Biller's Module"));
 }else {
 //BA.debugLineNum = 179;BA.debugLine="pnlStatus.Color = Colors.White";
mostCurrent._pnlstatus.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 180;BA.debugLine="lblMessage.TextColor = Colors.Red";
mostCurrent._lblmessage.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 181;BA.debugLine="lblMessage.Text = \"No Assigned Book(s) so far..";
mostCurrent._lblmessage.setText(BA.ObjectToCharSequence("No Assigned Book(s) so far..."));
 };
 //BA.debugLineNum = 183;BA.debugLine="lblMessage.Visible = True";
mostCurrent._lblmessage.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 186;BA.debugLine="lblEmpName.Text = TitleCase(GlobalVar.EmpName)";
mostCurrent._lblempname.setText(BA.ObjectToCharSequence(_titlecase(mostCurrent._globalvar._empname /*String*/ )));
 //BA.debugLineNum = 187;BA.debugLine="lblBranchName.Text = GlobalVar.BranchName";
mostCurrent._lblbranchname.setText(BA.ObjectToCharSequence(mostCurrent._globalvar._branchname /*String*/ ));
 //BA.debugLineNum = 188;BA.debugLine="lblBillPeriod.Text = GlobalVar.BillPeriod";
mostCurrent._lblbillperiod.setText(BA.ObjectToCharSequence(mostCurrent._globalvar._billperiod /*String*/ ));
 //BA.debugLineNum = 189;BA.debugLine="Drawer.LeftOpen = False";
mostCurrent._drawer._setleftopen /*boolean*/ (anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 190;BA.debugLine="End Sub";
return "";
}
public static String  _btnbp_click() throws Exception{
 //BA.debugLineNum = 746;BA.debugLine="Sub btnBP_Click";
 //BA.debugLineNum = 747;BA.debugLine="StartActivity(ReadingSettings)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._readingsettings.getObject()));
 //BA.debugLineNum = 748;BA.debugLine="End Sub";
return "";
}
public static String  _btndl_click() throws Exception{
 //BA.debugLineNum = 750;BA.debugLine="Sub btnDL_Click";
 //BA.debugLineNum = 751;BA.debugLine="CallSubDelayed(DataSyncing,\"btnDownload_Click\")";
anywheresoftware.b4a.keywords.Common.CallSubDelayed(processBA,(Object)(mostCurrent._datasyncing.getObject()),"btnDownload_Click");
 //BA.debugLineNum = 752;BA.debugLine="End Sub";
return "";
}
public static String  _btnload_click() throws Exception{
Object _value = null;
double _intpcaamount = 0;
int _index = 0;
com.bwsi.MeterReader.mainscreen._readingstatus _assignrec = null;
 //BA.debugLineNum = 579;BA.debugLine="Sub btnLoad_Click()";
 //BA.debugLineNum = 581;BA.debugLine="Dim value As Object";
_value = new Object();
 //BA.debugLineNum = 582;BA.debugLine="Dim intPCAAmount As Double";
_intpcaamount = 0;
 //BA.debugLineNum = 583;BA.debugLine="Dim Index As Int = clvBookAssignment.GetItemFromV";
_index = mostCurrent._clvbookassignment._getitemfromview((anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA))));
 //BA.debugLineNum = 584;BA.debugLine="Dim AssignRec As ReadingStatus = clvBookAssignmen";
_assignrec = (com.bwsi.MeterReader.mainscreen._readingstatus)(mostCurrent._clvbookassignment._getvalue(_index));
 //BA.debugLineNum = 586;BA.debugLine="value = AssignRec.sBookNo";
_value = (Object)(_assignrec.sBookNo /*String*/ );
 //BA.debugLineNum = 588;BA.debugLine="Log(value)";
anywheresoftware.b4a.keywords.Common.LogImpl("432833545",BA.ObjectToString(_value),0);
 //BA.debugLineNum = 591;BA.debugLine="LogColor (Index, Colors.Cyan)";
anywheresoftware.b4a.keywords.Common.LogImpl("432833548",BA.NumberToString(_index),anywheresoftware.b4a.keywords.Common.Colors.Cyan);
 //BA.debugLineNum = 593;BA.debugLine="GlobalVar.BookID = DBaseFunctions.GetIDbyCode(\"Bo";
mostCurrent._globalvar._bookid /*int*/  = (int) (mostCurrent._dbasefunctions._getidbycode /*double*/ (mostCurrent.activityBA,"BookID","tblBooks","BookCode",BA.ObjectToString(_value)));
 //BA.debugLineNum = 594;BA.debugLine="Log(GlobalVar.BookID)";
anywheresoftware.b4a.keywords.Common.LogImpl("432833551",BA.NumberToString(mostCurrent._globalvar._bookid /*int*/ ),0);
 //BA.debugLineNum = 596;BA.debugLine="If DBaseFunctions.IsBookWithPCA(GlobalVar.BookID)";
if (mostCurrent._dbasefunctions._isbookwithpca /*boolean*/ (mostCurrent.activityBA,mostCurrent._globalvar._bookid /*int*/ )==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 597;BA.debugLine="intPCAAmount = DBaseFunctions.GetPCAmount(Global";
_intpcaamount = mostCurrent._dbasefunctions._getpcamount /*double*/ (mostCurrent.activityBA,mostCurrent._globalvar._bookid /*int*/ );
 }else {
 //BA.debugLineNum = 599;BA.debugLine="intPCAAmount = 0";
_intpcaamount = 0;
 };
 //BA.debugLineNum = 601;BA.debugLine="If DBaseFunctions.IsPCAUpdate(GlobalVar.BookID) =";
if (mostCurrent._dbasefunctions._ispcaupdate /*boolean*/ (mostCurrent.activityBA,mostCurrent._globalvar._bookid /*int*/ )==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 602;BA.debugLine="DBaseFunctions.UpdatePCA(GlobalVar.BookID, intPC";
mostCurrent._dbasefunctions._updatepca /*String*/ (mostCurrent.activityBA,mostCurrent._globalvar._bookid /*int*/ ,_intpcaamount);
 };
 //BA.debugLineNum = 604;BA.debugLine="StartActivity(MeterReading)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._meterreading.getObject()));
 //BA.debugLineNum = 605;BA.debugLine="End Sub";
return "";
}
public static String  _btnlogout_click() throws Exception{
 //BA.debugLineNum = 763;BA.debugLine="Sub btnLogOut_Click";
 //BA.debugLineNum = 764;BA.debugLine="ShowLogOffSnackBar";
_showlogoffsnackbar();
 //BA.debugLineNum = 765;BA.debugLine="End Sub";
return "";
}
public static String  _btnul_click() throws Exception{
 //BA.debugLineNum = 754;BA.debugLine="Sub btnUL_Click";
 //BA.debugLineNum = 755;BA.debugLine="CallSubDelayed(DataSyncing,\"btnUpload_Click\")";
anywheresoftware.b4a.keywords.Common.CallSubDelayed(processBA,(Object)(mostCurrent._datasyncing.getObject()),"btnUpload_Click");
 //BA.debugLineNum = 756;BA.debugLine="End Sub";
return "";
}
public static String  _btnvalid_click() throws Exception{
 //BA.debugLineNum = 758;BA.debugLine="Sub btnValid_Click";
 //BA.debugLineNum = 759;BA.debugLine="StartActivity(CMRVR)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._cmrvr.getObject()));
 //BA.debugLineNum = 761;BA.debugLine="End Sub";
return "";
}
public static String  _closebutton_click() throws Exception{
 //BA.debugLineNum = 412;BA.debugLine="Private Sub CloseButton_Click()";
 //BA.debugLineNum = 413;BA.debugLine="ExitApplication";
anywheresoftware.b4a.keywords.Common.ExitApplication();
 //BA.debugLineNum = 414;BA.debugLine="End Sub";
return "";
}
public static String  _clv1_itemclick(int _index,Object _value) throws Exception{
 //BA.debugLineNum = 700;BA.debugLine="Sub CLV1_ItemClick (Index As Int, Value As Object)";
 //BA.debugLineNum = 701;BA.debugLine="GlobalVar.BookID = Value";
mostCurrent._globalvar._bookid /*int*/  = (int)(BA.ObjectToNumber(_value));
 //BA.debugLineNum = 702;BA.debugLine="Log(Value)";
anywheresoftware.b4a.keywords.Common.LogImpl("433226754",BA.ObjectToString(_value),0);
 //BA.debugLineNum = 703;BA.debugLine="End Sub";
return "";
}
public static String  _clvbookassignment_visiblerangechanged(int _firstindex,int _lastindex) throws Exception{
int _extrasize = 0;
int _i = 0;
anywheresoftware.b4a.objects.B4XViewWrapper _pnl = null;
com.bwsi.MeterReader.mainscreen._readingstatus _bookrec = null;
 //BA.debugLineNum = 479;BA.debugLine="Sub clvBookAssignment_VisibleRangeChanged (FirstIn";
 //BA.debugLineNum = 480;BA.debugLine="Dim ExtraSize As Int = 15 'List size";
_extrasize = (int) (15);
 //BA.debugLineNum = 481;BA.debugLine="clvBookAssignment.Refresh";
mostCurrent._clvbookassignment._refresh();
 //BA.debugLineNum = 483;BA.debugLine="For i = Max(0, FirstIndex - ExtraSize) To Min(Las";
{
final int step3 = 1;
final int limit3 = (int) (anywheresoftware.b4a.keywords.Common.Min(_lastindex+_extrasize,mostCurrent._clvbookassignment._getsize()-1));
_i = (int) (anywheresoftware.b4a.keywords.Common.Max(0,_firstindex-_extrasize)) ;
for (;_i <= limit3 ;_i = _i + step3 ) {
 //BA.debugLineNum = 484;BA.debugLine="Dim Pnl As B4XView = clvBookAssignment.GetPanel(";
_pnl = new anywheresoftware.b4a.objects.B4XViewWrapper();
_pnl = mostCurrent._clvbookassignment._getpanel(_i);
 //BA.debugLineNum = 485;BA.debugLine="If i > FirstIndex - ExtraSize And i < LastIndex";
if (_i>_firstindex-_extrasize && _i<_lastindex+_extrasize) { 
 //BA.debugLineNum = 486;BA.debugLine="If Pnl.NumberOfViews = 0 Then 'Add each item/la";
if (_pnl.getNumberOfViews()==0) { 
 //BA.debugLineNum = 487;BA.debugLine="Dim BookRec As ReadingStatus = clvBookAssignme";
_bookrec = (com.bwsi.MeterReader.mainscreen._readingstatus)(mostCurrent._clvbookassignment._getvalue(_i));
 //BA.debugLineNum = 488;BA.debugLine="LogColor($\"value: \"$ & clvBookAssignment.GetVa";
anywheresoftware.b4a.keywords.Common.LogImpl("432768009",("value: ")+BA.ObjectToString(mostCurrent._clvbookassignment._getvalue(_i)),anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 //BA.debugLineNum = 489;BA.debugLine="Pnl.LoadLayout(\"BookAssign\")";
_pnl.LoadLayout("BookAssign",mostCurrent.activityBA);
 //BA.debugLineNum = 490;BA.debugLine="lblBookNo.Text = $\"BOOK \"$ & BookRec.sBookNo";
mostCurrent._lblbookno.setText(BA.ObjectToCharSequence(("BOOK ")+_bookrec.sBookNo /*String*/ ));
 //BA.debugLineNum = 491;BA.debugLine="lblBookDesc.Text = BookRec.sBookDesc";
mostCurrent._lblbookdesc.setText(BA.ObjectToCharSequence(_bookrec.sBookDesc /*String*/ ));
 //BA.debugLineNum = 492;BA.debugLine="lblNoAccts.Text = BookRec.iTotAccounts & \" Acc";
mostCurrent._lblnoaccts.setText(BA.ObjectToCharSequence(BA.NumberToString(_bookrec.iTotAccounts /*int*/ )+" Account(s)"));
 //BA.debugLineNum = 493;BA.debugLine="lblRead.Text = BookRec.iTotRead";
mostCurrent._lblread.setText(BA.ObjectToCharSequence(_bookrec.iTotRead /*int*/ ));
 //BA.debugLineNum = 494;BA.debugLine="lblUnread.Text = BookRec.iTotUnread";
mostCurrent._lblunread.setText(BA.ObjectToCharSequence(_bookrec.iTotUnread /*int*/ ));
 //BA.debugLineNum = 495;BA.debugLine="lblMissCode.Text = BookRec.iTotMisCoded";
mostCurrent._lblmisscode.setText(BA.ObjectToCharSequence(_bookrec.iTotMisCoded /*int*/ ));
 //BA.debugLineNum = 496;BA.debugLine="lblZero.Text = BookRec.iTotZeroCons";
mostCurrent._lblzero.setText(BA.ObjectToCharSequence(_bookrec.iTotZeroCons /*int*/ ));
 //BA.debugLineNum = 497;BA.debugLine="lblHigh.Text = BookRec.iTotHighBill";
mostCurrent._lblhigh.setText(BA.ObjectToCharSequence(_bookrec.iTotHighBill /*int*/ ));
 //BA.debugLineNum = 498;BA.debugLine="lblLow.Text = BookRec.iTotLowBill";
mostCurrent._lbllow.setText(BA.ObjectToCharSequence(_bookrec.iTotLowBill /*int*/ ));
 //BA.debugLineNum = 499;BA.debugLine="lblAve.Text = BookRec.iTotAveBill";
mostCurrent._lblave.setText(BA.ObjectToCharSequence(_bookrec.iTotAveBill /*int*/ ));
 //BA.debugLineNum = 500;BA.debugLine="lblUnprint.Text = BookRec.iTotUnprinted";
mostCurrent._lblunprint.setText(BA.ObjectToCharSequence(_bookrec.iTotUnprinted /*int*/ ));
 //BA.debugLineNum = 502;BA.debugLine="If BookRec.iTotRead = 0 Then";
if (_bookrec.iTotRead /*int*/ ==0) { 
 //BA.debugLineNum = 503;BA.debugLine="lblReadTitle.TextColor = RedColor";
mostCurrent._lblreadtitle.setTextColor((int) (_redcolor));
 //BA.debugLineNum = 504;BA.debugLine="lblRead.TextColor = RedColor";
mostCurrent._lblread.setTextColor((int) (_redcolor));
 //BA.debugLineNum = 505;BA.debugLine="lblUnprintTitle.TextColor = GrayColor";
mostCurrent._lblunprinttitle.setTextColor((int) (_graycolor));
 //BA.debugLineNum = 506;BA.debugLine="lblUnprint.TextColor = GrayColor";
mostCurrent._lblunprint.setTextColor((int) (_graycolor));
 }else {
 //BA.debugLineNum = 508;BA.debugLine="If BookRec.iTotUnprinted > 0 Then";
if (_bookrec.iTotUnprinted /*int*/ >0) { 
 //BA.debugLineNum = 509;BA.debugLine="lblUnprintTitle.TextColor = RedColor";
mostCurrent._lblunprinttitle.setTextColor((int) (_redcolor));
 //BA.debugLineNum = 510;BA.debugLine="lblUnprint.TextColor = RedColor";
mostCurrent._lblunprint.setTextColor((int) (_redcolor));
 }else {
 //BA.debugLineNum = 512;BA.debugLine="lblUnprintTitle.TextColor = GrayColor";
mostCurrent._lblunprinttitle.setTextColor((int) (_graycolor));
 //BA.debugLineNum = 513;BA.debugLine="lblUnprint.TextColor = BlueColor";
mostCurrent._lblunprint.setTextColor((int) (_bluecolor));
 };
 //BA.debugLineNum = 515;BA.debugLine="lblReadTitle.TextColor = GrayColor";
mostCurrent._lblreadtitle.setTextColor((int) (_graycolor));
 //BA.debugLineNum = 516;BA.debugLine="lblRead.TextColor = BlueColor";
mostCurrent._lblread.setTextColor((int) (_bluecolor));
 };
 //BA.debugLineNum = 519;BA.debugLine="If BookRec.iTotUnread > 0 Then";
if (_bookrec.iTotUnread /*int*/ >0) { 
 //BA.debugLineNum = 520;BA.debugLine="lblUnreadTitle.TextColor = RedColor";
mostCurrent._lblunreadtitle.setTextColor((int) (_redcolor));
 //BA.debugLineNum = 521;BA.debugLine="lblUnread.TextColor = RedColor";
mostCurrent._lblunread.setTextColor((int) (_redcolor));
 }else {
 //BA.debugLineNum = 523;BA.debugLine="lblUnreadTitle.TextColor = GrayColor";
mostCurrent._lblunreadtitle.setTextColor((int) (_graycolor));
 //BA.debugLineNum = 524;BA.debugLine="lblUnread.TextColor = BlueColor";
mostCurrent._lblunread.setTextColor((int) (_bluecolor));
 };
 //BA.debugLineNum = 527;BA.debugLine="If BookRec.iTotMisCoded > 0 Then";
if (_bookrec.iTotMisCoded /*int*/ >0) { 
 //BA.debugLineNum = 528;BA.debugLine="lblMissCodeTitle.TextColor = RedColor";
mostCurrent._lblmisscodetitle.setTextColor((int) (_redcolor));
 //BA.debugLineNum = 529;BA.debugLine="lblMissCode.TextColor = RedColor";
mostCurrent._lblmisscode.setTextColor((int) (_redcolor));
 }else {
 //BA.debugLineNum = 531;BA.debugLine="lblMissCodeTitle.TextColor = GrayColor";
mostCurrent._lblmisscodetitle.setTextColor((int) (_graycolor));
 //BA.debugLineNum = 532;BA.debugLine="lblMissCode.TextColor = BlueColor";
mostCurrent._lblmisscode.setTextColor((int) (_bluecolor));
 };
 //BA.debugLineNum = 535;BA.debugLine="If BookRec.iTotZeroCons > 0 Then";
if (_bookrec.iTotZeroCons /*int*/ >0) { 
 //BA.debugLineNum = 536;BA.debugLine="lblZeroTitle.TextColor = RedColor";
mostCurrent._lblzerotitle.setTextColor((int) (_redcolor));
 //BA.debugLineNum = 537;BA.debugLine="lblZero.TextColor = RedColor";
mostCurrent._lblzero.setTextColor((int) (_redcolor));
 }else {
 //BA.debugLineNum = 539;BA.debugLine="lblZeroTitle.TextColor = GrayColor";
mostCurrent._lblzerotitle.setTextColor((int) (_graycolor));
 //BA.debugLineNum = 540;BA.debugLine="lblZero.TextColor = BlueColor";
mostCurrent._lblzero.setTextColor((int) (_bluecolor));
 };
 //BA.debugLineNum = 543;BA.debugLine="If BookRec.iTotHighBill > 0 Then";
if (_bookrec.iTotHighBill /*int*/ >0) { 
 //BA.debugLineNum = 544;BA.debugLine="lblHighTtle.TextColor = RedColor";
mostCurrent._lblhighttle.setTextColor((int) (_redcolor));
 //BA.debugLineNum = 545;BA.debugLine="lblHigh.TextColor = RedColor";
mostCurrent._lblhigh.setTextColor((int) (_redcolor));
 }else {
 //BA.debugLineNum = 547;BA.debugLine="lblHighTtle.TextColor = GrayColor";
mostCurrent._lblhighttle.setTextColor((int) (_graycolor));
 //BA.debugLineNum = 548;BA.debugLine="lblHigh.TextColor = BlueColor";
mostCurrent._lblhigh.setTextColor((int) (_bluecolor));
 };
 //BA.debugLineNum = 551;BA.debugLine="If BookRec.iTotLowBill > 0 Then";
if (_bookrec.iTotLowBill /*int*/ >0) { 
 //BA.debugLineNum = 552;BA.debugLine="lblLowTitle.TextColor = RedColor";
mostCurrent._lbllowtitle.setTextColor((int) (_redcolor));
 //BA.debugLineNum = 553;BA.debugLine="lblLow.TextColor = RedColor";
mostCurrent._lbllow.setTextColor((int) (_redcolor));
 }else {
 //BA.debugLineNum = 555;BA.debugLine="lblLowTitle.TextColor = GrayColor";
mostCurrent._lbllowtitle.setTextColor((int) (_graycolor));
 //BA.debugLineNum = 556;BA.debugLine="lblLow.TextColor = BlueColor";
mostCurrent._lbllow.setTextColor((int) (_bluecolor));
 };
 //BA.debugLineNum = 559;BA.debugLine="If BookRec.iTotAveBill> 0 Then";
if (_bookrec.iTotAveBill /*int*/ >0) { 
 //BA.debugLineNum = 560;BA.debugLine="lblAveTitle.TextColor = RedColor";
mostCurrent._lblavetitle.setTextColor((int) (_redcolor));
 //BA.debugLineNum = 561;BA.debugLine="lblAve.TextColor = RedColor";
mostCurrent._lblave.setTextColor((int) (_redcolor));
 }else {
 //BA.debugLineNum = 563;BA.debugLine="lblAveTitle.TextColor = GrayColor";
mostCurrent._lblavetitle.setTextColor((int) (_graycolor));
 //BA.debugLineNum = 564;BA.debugLine="lblAve.TextColor = BlueColor";
mostCurrent._lblave.setTextColor((int) (_bluecolor));
 };
 //BA.debugLineNum = 567;BA.debugLine="CD.Initialize2(0xFF1976D2, 25, 0,0xFF000000)";
mostCurrent._cd.Initialize2((int) (0xff1976d2),(int) (25),(int) (0),(int) (0xff000000));
 //BA.debugLineNum = 568;BA.debugLine="btnLoad.Background = CD";
mostCurrent._btnload.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cd.getObject()));
 };
 }else {
 //BA.debugLineNum = 572;BA.debugLine="If Pnl.NumberOfViews > 0 Then";
if (_pnl.getNumberOfViews()>0) { 
 //BA.debugLineNum = 573;BA.debugLine="Pnl.RemoveAllViews 'Remove none visable item/l";
_pnl.RemoveAllViews();
 };
 };
 }
};
 //BA.debugLineNum = 577;BA.debugLine="End Sub";
return "";
}
public static String  _createmainmenu(int _imod1,int _imod2,int _imod3,int _imod4,int _imod5,int _imod6) throws Exception{
com.maximussoft.circularimageview.CircularImageViewWrapper _civ = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _imgback = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _cdpressed = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _cdnormal = null;
anywheresoftware.b4a.objects.drawable.StateListDrawable _sld = null;
anywheresoftware.b4a.objects.CSBuilder _csmenu1 = null;
anywheresoftware.b4a.objects.CSBuilder _csmenu2 = null;
anywheresoftware.b4a.objects.CSBuilder _csmenu3 = null;
anywheresoftware.b4a.objects.CSBuilder _csmenu4 = null;
anywheresoftware.b4a.objects.CSBuilder _csmenu5 = null;
anywheresoftware.b4a.objects.CSBuilder _csmenu6 = null;
Object _sicon1 = null;
Object _sicon2 = null;
Object _sicon3 = null;
Object _sicon4 = null;
Object _sicon5 = null;
Object _sicon6 = null;
anywheresoftware.b4j.object.JavaObject _lvo = null;
 //BA.debugLineNum = 230;BA.debugLine="Private Sub CreateMainMenu(iMod1 As Int, iMod2 As";
 //BA.debugLineNum = 231;BA.debugLine="Dim civ As CircularImageView";
_civ = new com.maximussoft.circularimageview.CircularImageViewWrapper();
 //BA.debugLineNum = 232;BA.debugLine="Dim imgBack As BitmapDrawable";
_imgback = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 233;BA.debugLine="Dim CDPressed,CDNormal As ColorDrawable";
_cdpressed = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
_cdnormal = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 234;BA.debugLine="Dim SLD As StateListDrawable";
_sld = new anywheresoftware.b4a.objects.drawable.StateListDrawable();
 //BA.debugLineNum = 235;BA.debugLine="Dim csMenu1, csMenu2, csMenu3, csMenu4, csMenu5,";
_csmenu1 = new anywheresoftware.b4a.objects.CSBuilder();
_csmenu2 = new anywheresoftware.b4a.objects.CSBuilder();
_csmenu3 = new anywheresoftware.b4a.objects.CSBuilder();
_csmenu4 = new anywheresoftware.b4a.objects.CSBuilder();
_csmenu5 = new anywheresoftware.b4a.objects.CSBuilder();
_csmenu6 = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 236;BA.debugLine="Dim sIcon1, sIcon2, sIcon3, sIcon4, sIcon5, sIcon";
_sicon1 = new Object();
_sicon2 = new Object();
_sicon3 = new Object();
_sicon4 = new Object();
_sicon5 = new Object();
_sicon6 = new Object();
 //BA.debugLineNum = 239;BA.debugLine="imgBack.Initialize( LoadBitmap(File.DirAssets,\"pr";
_imgback.Initialize((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"profile3.jpg").getObject()));
 //BA.debugLineNum = 241;BA.debugLine="If pnlMenuHeader.IsInitialized = False Then pnlMe";
if (mostCurrent._pnlmenuheader.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
mostCurrent._pnlmenuheader.Initialize(mostCurrent.activityBA,"");};
 //BA.debugLineNum = 242;BA.debugLine="If civ.IsInitialized = False Then civ.Initialize(";
if (_civ.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
_civ.Initialize(processBA,"");};
 //BA.debugLineNum = 244;BA.debugLine="civ.BorderWidth = 2dip";
_civ.setBorderWidth(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2)));
 //BA.debugLineNum = 245;BA.debugLine="civ.BorderColor = GlobalVar.PriColor";
_civ.setBorderColor((int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 246;BA.debugLine="civ.Color = Colors.Transparent";
_civ.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 247;BA.debugLine="civ.SetDrawable ( imgBack )";
_civ.SetDrawable(_imgback);
 //BA.debugLineNum = 248;BA.debugLine="pnlMenuHeader.AddView(civ,20,2%y,90,90)";
mostCurrent._pnlmenuheader.AddView((android.view.View)(_civ.getObject()),(int) (20),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (2),mostCurrent.activityBA),(int) (90),(int) (90));
 //BA.debugLineNum = 251;BA.debugLine="CDPressed.Initialize(Colors.White,0)  ' Normal co";
_cdpressed.Initialize(anywheresoftware.b4a.keywords.Common.Colors.White,(int) (0));
 //BA.debugLineNum = 252;BA.debugLine="CDNormal.Initialize(0xFFB0E0E6,0) 'Selected color";
_cdnormal.Initialize((int) (0xffb0e0e6),(int) (0));
 //BA.debugLineNum = 254;BA.debugLine="SLD.Initialize";
_sld.Initialize();
 //BA.debugLineNum = 255;BA.debugLine="SLD.AddState(SLD.State_Pressed,CDPressed)";
_sld.AddState(_sld.State_Pressed,(android.graphics.drawable.Drawable)(_cdpressed.getObject()));
 //BA.debugLineNum = 256;BA.debugLine="SLD.AddState(-SLD.State_Pressed,CDNormal)";
_sld.AddState((int) (-_sld.State_Pressed),(android.graphics.drawable.Drawable)(_cdnormal.getObject()));
 //BA.debugLineNum = 258;BA.debugLine="If lblUserFullName.IsInitialized = False Then lbl";
if (mostCurrent._lbluserfullname.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
mostCurrent._lbluserfullname.Initialize(mostCurrent.activityBA,"");};
 //BA.debugLineNum = 259;BA.debugLine="If lblUserBranch.IsInitialized = False Then lblUs";
if (mostCurrent._lbluserbranch.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
mostCurrent._lbluserbranch.Initialize(mostCurrent.activityBA,"");};
 //BA.debugLineNum = 260;BA.debugLine="If lvMenus.IsInitialized = False Then lvMenus.Ini";
if (mostCurrent._lvmenus.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
mostCurrent._lvmenus.Initialize(mostCurrent.activityBA,"lvMenus");};
 //BA.debugLineNum = 262;BA.debugLine="Dim LVO As JavaObject = lvMenus";
_lvo = new anywheresoftware.b4j.object.JavaObject();
_lvo = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(mostCurrent._lvmenus.getObject()));
 //BA.debugLineNum = 263;BA.debugLine="LVO.RunMethod(\"setSelector\",Array As Object(SLD))";
_lvo.RunMethod("setSelector",new Object[]{(Object)(_sld.getObject())});
 //BA.debugLineNum = 264;BA.debugLine="lvMenus.FastScrollEnabled=True";
mostCurrent._lvmenus.setFastScrollEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 267;BA.debugLine="lblUserFullName.Text = GlobalVar.SF.Upper(GlobalV";
mostCurrent._lbluserfullname.setText(BA.ObjectToCharSequence(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv5(mostCurrent._globalvar._empname /*String*/ )));
 //BA.debugLineNum = 268;BA.debugLine="lblUserBranch.Text = GlobalVar.BranchName";
mostCurrent._lbluserbranch.setText(BA.ObjectToCharSequence(mostCurrent._globalvar._branchname /*String*/ ));
 //BA.debugLineNum = 271;BA.debugLine="If iMod1 = 1 Then";
if (_imod1==1) { 
 //BA.debugLineNum = 272;BA.debugLine="csMenu1.Initialize.Color(GlobalVar.PriColor).App";
_csmenu1.Initialize().Color((int) (mostCurrent._globalvar._pricolor /*double*/ )).Append(BA.ObjectToCharSequence(("Meter Reading"))).PopAll();
 //BA.debugLineNum = 273;BA.debugLine="sIcon1 = FontBit(Chr(0xE86D),17,GlobalVar.PriCol";
_sicon1 = (Object)(_fontbit(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xe86d))),(float) (17),(int) (mostCurrent._globalvar._pricolor /*double*/ ),anywheresoftware.b4a.keywords.Common.False).getObject());
 }else {
 //BA.debugLineNum = 275;BA.debugLine="csMenu1.Initialize.Color(Colors.LightGray).Appen";
_csmenu1.Initialize().Color(anywheresoftware.b4a.keywords.Common.Colors.LightGray).Append(BA.ObjectToCharSequence(("Meter Reading"))).PopAll();
 //BA.debugLineNum = 276;BA.debugLine="sIcon1 = FontBit(Chr(0xE86D),17,Colors.LightGray";
_sicon1 = (Object)(_fontbit(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xe86d))),(float) (17),anywheresoftware.b4a.keywords.Common.Colors.LightGray,anywheresoftware.b4a.keywords.Common.False).getObject());
 };
 //BA.debugLineNum = 279;BA.debugLine="If iMod2 = 1 Then";
if (_imod2==1) { 
 //BA.debugLineNum = 280;BA.debugLine="csMenu2.Initialize.Color(GlobalVar.PriColor).App";
_csmenu2.Initialize().Color((int) (mostCurrent._globalvar._pricolor /*double*/ )).Append(BA.ObjectToCharSequence(("Bill Printing"))).PopAll();
 //BA.debugLineNum = 281;BA.debugLine="sIcon2 = FontBit(Chr(0xE555),17,GlobalVar.PriCol";
_sicon2 = (Object)(_fontbit(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xe555))),(float) (17),(int) (mostCurrent._globalvar._pricolor /*double*/ ),anywheresoftware.b4a.keywords.Common.False).getObject());
 }else {
 //BA.debugLineNum = 283;BA.debugLine="csMenu2.Initialize.Color(Colors.LightGray).Appen";
_csmenu2.Initialize().Color(anywheresoftware.b4a.keywords.Common.Colors.LightGray).Append(BA.ObjectToCharSequence(("Bill Printing"))).PopAll();
 //BA.debugLineNum = 284;BA.debugLine="sIcon2 = FontBit(Chr(0xE555),17,Colors.LightGray";
_sicon2 = (Object)(_fontbit(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xe555))),(float) (17),anywheresoftware.b4a.keywords.Common.Colors.LightGray,anywheresoftware.b4a.keywords.Common.False).getObject());
 };
 //BA.debugLineNum = 287;BA.debugLine="If iMod3 = 1 Then";
if (_imod3==1) { 
 //BA.debugLineNum = 288;BA.debugLine="csMenu3.Initialize.Color(GlobalVar.PriColor).App";
_csmenu3.Initialize().Color((int) (mostCurrent._globalvar._pricolor /*double*/ )).Append(BA.ObjectToCharSequence(("Reading Validation Report"))).PopAll();
 //BA.debugLineNum = 289;BA.debugLine="sIcon3 = FontBit(Chr(0xE065),17,GlobalVar.PriCol";
_sicon3 = (Object)(_fontbit(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xe065))),(float) (17),(int) (mostCurrent._globalvar._pricolor /*double*/ ),anywheresoftware.b4a.keywords.Common.False).getObject());
 }else {
 //BA.debugLineNum = 291;BA.debugLine="csMenu3.Initialize.Color(Colors.LightGray).Appen";
_csmenu3.Initialize().Color(anywheresoftware.b4a.keywords.Common.Colors.LightGray).Append(BA.ObjectToCharSequence(("Reading Validation Report"))).PopAll();
 //BA.debugLineNum = 292;BA.debugLine="sIcon3 = FontBit(Chr(0xE065),17,Colors.LightGray";
_sicon3 = (Object)(_fontbit(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xe065))),(float) (17),anywheresoftware.b4a.keywords.Common.Colors.LightGray,anywheresoftware.b4a.keywords.Common.False).getObject());
 };
 //BA.debugLineNum = 295;BA.debugLine="If iMod4 = 1 Then";
if (_imod4==1) { 
 //BA.debugLineNum = 296;BA.debugLine="csMenu4.Initialize.Color(GlobalVar.PriColor).App";
_csmenu4.Initialize().Color((int) (mostCurrent._globalvar._pricolor /*double*/ )).Append(BA.ObjectToCharSequence(("Bill Period Settings"))).PopAll();
 //BA.debugLineNum = 297;BA.debugLine="sIcon4 = FontBit(Chr(0xE8B8),17,GlobalVar.PriCol";
_sicon4 = (Object)(_fontbit(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xe8b8))),(float) (17),(int) (mostCurrent._globalvar._pricolor /*double*/ ),anywheresoftware.b4a.keywords.Common.False).getObject());
 }else {
 //BA.debugLineNum = 299;BA.debugLine="csMenu4.Initialize.Color(Colors.LightGray).Appen";
_csmenu4.Initialize().Color(anywheresoftware.b4a.keywords.Common.Colors.LightGray).Append(BA.ObjectToCharSequence(("Bill Period Settings"))).PopAll();
 //BA.debugLineNum = 300;BA.debugLine="sIcon4 = FontBit(Chr(0xE8B8),17,Colors.LightGray";
_sicon4 = (Object)(_fontbit(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xe8b8))),(float) (17),anywheresoftware.b4a.keywords.Common.Colors.LightGray,anywheresoftware.b4a.keywords.Common.False).getObject());
 };
 //BA.debugLineNum = 303;BA.debugLine="If iMod5 = 1 Then";
if (_imod5==1) { 
 //BA.debugLineNum = 304;BA.debugLine="csMenu5.Initialize.Color(GlobalVar.PriColor).App";
_csmenu5.Initialize().Color((int) (mostCurrent._globalvar._pricolor /*double*/ )).Append(BA.ObjectToCharSequence(("Data Syncing"))).PopAll();
 //BA.debugLineNum = 305;BA.debugLine="sIcon5 = FontBit(Chr(0xE8D5),17,GlobalVar.PriCol";
_sicon5 = (Object)(_fontbit(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xe8d5))),(float) (17),(int) (mostCurrent._globalvar._pricolor /*double*/ ),anywheresoftware.b4a.keywords.Common.False).getObject());
 }else {
 //BA.debugLineNum = 307;BA.debugLine="csMenu5.Initialize.Color(Colors.LightGray).Appen";
_csmenu5.Initialize().Color(anywheresoftware.b4a.keywords.Common.Colors.LightGray).Append(BA.ObjectToCharSequence(("Data Syncing"))).PopAll();
 //BA.debugLineNum = 308;BA.debugLine="sIcon5 = FontBit(Chr(0xE8D5),17,Colors.LightGray";
_sicon5 = (Object)(_fontbit(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xe8d5))),(float) (17),anywheresoftware.b4a.keywords.Common.Colors.LightGray,anywheresoftware.b4a.keywords.Common.False).getObject());
 };
 //BA.debugLineNum = 311;BA.debugLine="If iMod6 = 1 Then";
if (_imod6==1) { 
 //BA.debugLineNum = 312;BA.debugLine="csMenu6.Initialize.Color(GlobalVar.PriColor).App";
_csmenu6.Initialize().Color((int) (mostCurrent._globalvar._pricolor /*double*/ )).Append(BA.ObjectToCharSequence(("User's Settings"))).PopAll();
 //BA.debugLineNum = 313;BA.debugLine="sIcon6 = FontBit(Chr(0xE851),17,GlobalVar.PriCol";
_sicon6 = (Object)(_fontbit(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xe851))),(float) (17),(int) (mostCurrent._globalvar._pricolor /*double*/ ),anywheresoftware.b4a.keywords.Common.False).getObject());
 }else {
 //BA.debugLineNum = 315;BA.debugLine="csMenu6.Initialize.Color(Colors.LightGray).Appen";
_csmenu6.Initialize().Color(anywheresoftware.b4a.keywords.Common.Colors.LightGray).Append(BA.ObjectToCharSequence(("User's Settings"))).PopAll();
 //BA.debugLineNum = 316;BA.debugLine="sIcon6 = FontBit(Chr(0xE851),17,Colors.LightGray";
_sicon6 = (Object)(_fontbit(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xe851))),(float) (17),anywheresoftware.b4a.keywords.Common.Colors.LightGray,anywheresoftware.b4a.keywords.Common.False).getObject());
 };
 //BA.debugLineNum = 318;BA.debugLine="lvMenus.Clear";
mostCurrent._lvmenus.Clear();
 //BA.debugLineNum = 321;BA.debugLine="lvMenus.AddTwoLinesAndBitmap2(csMenu1, $\"Input Cu";
mostCurrent._lvmenus.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence(_csmenu1.getObject()),BA.ObjectToCharSequence(("Input Customer's Meter Reading")),(android.graphics.Bitmap)(_sicon1),(Object)(1));
 //BA.debugLineNum = 322;BA.debugLine="lvMenus.AddTwoLinesAndBitmap2(csMenu2, $\"Print/Re";
mostCurrent._lvmenus.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence(_csmenu2.getObject()),BA.ObjectToCharSequence(("Print/Reprint Customer's Billing Statement")),(android.graphics.Bitmap)(_sicon2),(Object)(2));
 //BA.debugLineNum = 323;BA.debugLine="lvMenus.AddTwoLinesAndBitmap2(csMenu3, $\"Show Lis";
mostCurrent._lvmenus.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence(_csmenu3.getObject()),BA.ObjectToCharSequence(("Show List of Unusual Readings")),(android.graphics.Bitmap)(_sicon3),(Object)(3));
 //BA.debugLineNum = 324;BA.debugLine="lvMenus.AddTwoLinesAndBitmap2(csMenu4, $\"Set-up C";
mostCurrent._lvmenus.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence(_csmenu4.getObject()),BA.ObjectToCharSequence(("Set-up Current Billing Period")),(android.graphics.Bitmap)(_sicon4),(Object)(4));
 //BA.debugLineNum = 325;BA.debugLine="lvMenus.AddTwoLinesAndBitmap2(csMenu5, $\"Download";
mostCurrent._lvmenus.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence(_csmenu5.getObject()),BA.ObjectToCharSequence(("Download/Upload Data from/to Elite Server")),(android.graphics.Bitmap)(_sicon5),(Object)(5));
 //BA.debugLineNum = 326;BA.debugLine="lvMenus.AddTwoLinesAndBitmap2(csMenu6, $\"Change U";
mostCurrent._lvmenus.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence(_csmenu6.getObject()),BA.ObjectToCharSequence(("Change User Name and/or User Password")),(android.graphics.Bitmap)(_sicon6),(Object)(6));
 //BA.debugLineNum = 327;BA.debugLine="lvMenus.AddTwoLinesAndBitmap2($\"Log Out \"$ & Glob";
mostCurrent._lvmenus.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence(("Log Out ")+mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv5(mostCurrent._globalvar._username /*String*/ )),BA.ObjectToCharSequence(("Log-out Session")),(android.graphics.Bitmap)(_fontbit(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xf08b))),(float) (17),(int) (mostCurrent._globalvar._pricolor /*double*/ ),anywheresoftware.b4a.keywords.Common.True).getObject()),(Object)(7));
 //BA.debugLineNum = 328;BA.debugLine="lvMenus.AddTwoLinesAndBitmap2($\"Close App\"$,$\"Clo";
mostCurrent._lvmenus.AddTwoLinesAndBitmap2(BA.ObjectToCharSequence(("Close App")),BA.ObjectToCharSequence(("Close Meter Reading App")),(android.graphics.Bitmap)(_fontbit(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xf2d4))),(float) (17),(int) (mostCurrent._globalvar._pricolor /*double*/ ),anywheresoftware.b4a.keywords.Common.True).getObject()),(Object)(8));
 //BA.debugLineNum = 330;BA.debugLine="lvMenus.TwoLinesAndBitmap.Label.TextColor = Globa";
mostCurrent._lvmenus.getTwoLinesAndBitmap().Label.setTextColor((int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 331;BA.debugLine="lvMenus.TwoLinesAndBitmap.Label.TextSize = 12";
mostCurrent._lvmenus.getTwoLinesAndBitmap().Label.setTextSize((float) (12));
 //BA.debugLineNum = 332;BA.debugLine="lvMenus.TwoLinesAndBitmap.SecondLabel.TextSize =";
mostCurrent._lvmenus.getTwoLinesAndBitmap().SecondLabel.setTextSize((float) (9));
 //BA.debugLineNum = 333;BA.debugLine="lvMenus.TwoLinesAndBitmap.Label.Typeface = Typefa";
mostCurrent._lvmenus.getTwoLinesAndBitmap().Label.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD);
 //BA.debugLineNum = 334;BA.debugLine="lvMenus.TwoLinesAndBitmap.ItemHeight = 52dip";
mostCurrent._lvmenus.getTwoLinesAndBitmap().setItemHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (52)));
 //BA.debugLineNum = 335;BA.debugLine="End Sub";
return "";
}
public static void  _fillbookassignments(int _iuserid,int _ibranchid,int _ibillyear,int _ibillmonth) throws Exception{
ResumableSub_FillBookAssignments rsub = new ResumableSub_FillBookAssignments(null,_iuserid,_ibranchid,_ibillyear,_ibillmonth);
rsub.resume(processBA, null);
}
public static class ResumableSub_FillBookAssignments extends BA.ResumableSub {
public ResumableSub_FillBookAssignments(com.bwsi.MeterReader.mainscreen parent,int _iuserid,int _ibranchid,int _ibillyear,int _ibillmonth) {
this.parent = parent;
this._iuserid = _iuserid;
this._ibranchid = _ibranchid;
this._ibillyear = _ibillyear;
this._ibillmonth = _ibillmonth;
}
com.bwsi.MeterReader.mainscreen parent;
int _iuserid;
int _ibranchid;
int _ibillyear;
int _ibillmonth;
Object _senderfilter = null;
boolean _success = false;
anywheresoftware.b4a.sql.SQL.ResultSetWrapper _rs = null;
long _starttime = 0L;
com.bwsi.MeterReader.mainscreen._readingstatus _rdgcount = null;
anywheresoftware.b4a.objects.B4XViewWrapper _pnl = null;

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
 //BA.debugLineNum = 420;BA.debugLine="Dim SenderFilter As Object";
_senderfilter = new Object();
 //BA.debugLineNum = 422;BA.debugLine="Try";
if (true) break;

case 1:
//try
this.state = 16;
this.catchState = 15;
this.state = 3;
if (true) break;

case 3:
//C
this.state = 4;
this.catchState = 15;
 //BA.debugLineNum = 423;BA.debugLine="Starter.strCriteria = \"SELECT tblReadings.BookID";
parent.mostCurrent._starter._strcriteria /*String*/  = "SELECT tblReadings.BookID AS BookID, tblReadings.BookNo AS BookNo, tblBooks.BookDesc AS BookDesc, "+"Count(tblReadings.AcctID) As NoAccts, "+"SUM(CASE WHEN tblReadings.WasRead = 1 Then 1 Else 0 End) As ReadAccts, "+"SUM(CASE WHEN tblReadings.WasRead = 0 Then 1 Else 0 End) As UnreadAccts, "+"SUM(CASE WHEN tblReadings.WasMissCoded = 1 THEN 1 ELSE 0 END) as Miscoded, "+"SUM(CASE WHEN tblReadings.ImplosiveType = 'zero' Then 1 ELSE 0 END) As ZeroCons, "+"SUM(CASE WHEN tblReadings.ImplosiveType = 'implosive-inc' Then 1 ELSE 0 END) As HighBill, "+"SUM(CASE WHEN tblReadings.ImplosiveType = 'implosive-dec' Then 1 ELSE 0 END) As LowBill, "+"SUM(CASE WHEN tblReadings.BillType = 'AB' Then 1 ELSE 0 END) As AverageBill, "+"SUM(CASE WHEN (tblReadings.PrintStatus = 0 AND tblReadings.WasRead = 1) Then 1 ELSE 0 END) As Unprinted "+"FROM tblReadings INNER JOIN tblBooks ON tblReadings.BookID = tblBooks.BookID "+"WHERE tblReadings.BranchID = "+BA.NumberToString(_ibranchid)+" "+"AND tblReadings.BillYear = "+BA.NumberToString(_ibillyear)+" "+"AND tblReadings.BillMonth = "+BA.NumberToString(_ibillmonth)+" "+"AND tblReadings.ReadBy = "+BA.NumberToString(_iuserid)+" "+"GROUP BY tblReadings.BookID "+"ORDER BY tblReadings.BookNo Asc";
 //BA.debugLineNum = 441;BA.debugLine="LogColor(Starter.strCriteria, Colors.Yellow)";
anywheresoftware.b4a.keywords.Common.LogImpl("432702486",parent.mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 //BA.debugLineNum = 443;BA.debugLine="SenderFilter = Starter.DBCon.ExecQueryAsync(\"SQL";
_senderfilter = parent.mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQueryAsync(processBA,"SQL",parent.mostCurrent._starter._strcriteria /*String*/ ,(anywheresoftware.b4a.objects.collections.List) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.List(), (java.util.List)(anywheresoftware.b4a.keywords.Common.Null)));
 //BA.debugLineNum = 444;BA.debugLine="Wait For (SenderFilter) SQL_QueryComplete (Succe";
anywheresoftware.b4a.keywords.Common.WaitFor("sql_querycomplete", processBA, this, _senderfilter);
this.state = 17;
return;
case 17:
//C
this.state = 4;
_success = (Boolean) result[0];
_rs = (anywheresoftware.b4a.sql.SQL.ResultSetWrapper) result[1];
;
 //BA.debugLineNum = 446;BA.debugLine="If Success Then";
if (true) break;

case 4:
//if
this.state = 13;
if (_success) { 
this.state = 6;
}else {
this.state = 12;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 447;BA.debugLine="Dim StartTime As Long = DateTime.Now";
_starttime = anywheresoftware.b4a.keywords.Common.DateTime.getNow();
 //BA.debugLineNum = 448;BA.debugLine="clvBookAssignment.Clear";
parent.mostCurrent._clvbookassignment._clear();
 //BA.debugLineNum = 449;BA.debugLine="Do While RS.NextRow";
if (true) break;

case 7:
//do while
this.state = 10;
while (_rs.NextRow()) {
this.state = 9;
if (true) break;
}
if (true) break;

case 9:
//C
this.state = 7;
 //BA.debugLineNum = 450;BA.debugLine="Dim RdgCount As ReadingStatus";
_rdgcount = new com.bwsi.MeterReader.mainscreen._readingstatus();
 //BA.debugLineNum = 451;BA.debugLine="RdgCount.Initialize";
_rdgcount.Initialize();
 //BA.debugLineNum = 452;BA.debugLine="RdgCount.sBookNo = RS.GetString(\"BookNo\")";
_rdgcount.sBookNo /*String*/  = _rs.GetString("BookNo");
 //BA.debugLineNum = 453;BA.debugLine="RdgCount.sBookDesc = RS.GetString(\"BookDesc\")";
_rdgcount.sBookDesc /*String*/  = _rs.GetString("BookDesc");
 //BA.debugLineNum = 454;BA.debugLine="RdgCount.iTotAccounts = RS.GetInt(\"NoAccts\")";
_rdgcount.iTotAccounts /*int*/  = _rs.GetInt("NoAccts");
 //BA.debugLineNum = 455;BA.debugLine="RdgCount.iTotRead = RS.GetInt(\"ReadAccts\")";
_rdgcount.iTotRead /*int*/  = _rs.GetInt("ReadAccts");
 //BA.debugLineNum = 456;BA.debugLine="RdgCount.iTotUnread = RS.GetInt(\"UnreadAccts\")";
_rdgcount.iTotUnread /*int*/  = _rs.GetInt("UnreadAccts");
 //BA.debugLineNum = 457;BA.debugLine="RdgCount.iTotMisCoded = RS.GetInt(\"Miscoded\")";
_rdgcount.iTotMisCoded /*int*/  = _rs.GetInt("Miscoded");
 //BA.debugLineNum = 458;BA.debugLine="RdgCount.iTotZeroCons = RS.GetInt(\"ZeroCons\")";
_rdgcount.iTotZeroCons /*int*/  = _rs.GetInt("ZeroCons");
 //BA.debugLineNum = 459;BA.debugLine="RdgCount.iTotLowBill = RS.GetInt(\"LowBill\")";
_rdgcount.iTotLowBill /*int*/  = _rs.GetInt("LowBill");
 //BA.debugLineNum = 460;BA.debugLine="RdgCount.iTotHighBill = RS.GetInt(\"HighBill\")";
_rdgcount.iTotHighBill /*int*/  = _rs.GetInt("HighBill");
 //BA.debugLineNum = 461;BA.debugLine="RdgCount.iTotAveBill = RS.GetInt(\"AverageBill\"";
_rdgcount.iTotAveBill /*int*/  = _rs.GetInt("AverageBill");
 //BA.debugLineNum = 462;BA.debugLine="RdgCount.iTotUnprinted = RS.GetInt(\"Unprinted\"";
_rdgcount.iTotUnprinted /*int*/  = _rs.GetInt("Unprinted");
 //BA.debugLineNum = 464;BA.debugLine="Dim Pnl As B4XView = xui.CreatePanel(\"\")";
_pnl = new anywheresoftware.b4a.objects.B4XViewWrapper();
_pnl = parent._xui.CreatePanel(processBA,"");
 //BA.debugLineNum = 465;BA.debugLine="Pnl.SetLayoutAnimated(0, 2dip, 2dip, clvBookAs";
_pnl.SetLayoutAnimated((int) (0),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2)),(int) (parent.mostCurrent._clvbookassignment._asview().getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (230)));
 //BA.debugLineNum = 466;BA.debugLine="clvBookAssignment.Add(Pnl, RdgCount)";
parent.mostCurrent._clvbookassignment._add(_pnl,(Object)(_rdgcount));
 if (true) break;

case 10:
//C
this.state = 13;
;
 if (true) break;

case 12:
//C
this.state = 13;
 //BA.debugLineNum = 469;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("432702514",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 if (true) break;

case 13:
//C
this.state = 16;
;
 //BA.debugLineNum = 472;BA.debugLine="Log($\"List of Book Assignment Records = ${Number";
anywheresoftware.b4a.keywords.Common.LogImpl("432702517",("List of Book Assignment Records = "+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(anywheresoftware.b4a.keywords.Common.NumberFormat2((anywheresoftware.b4a.keywords.Common.DateTime.getNow()-_starttime)/(double)1000,(int) (0),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.False)))+" seconds to populate "+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(parent.mostCurrent._clvbookassignment._getsize()))+" Book Assignment Records"),0);
 if (true) break;

case 15:
//C
this.state = 16;
this.catchState = 0;
 //BA.debugLineNum = 474;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("432702519",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 if (true) break;
if (true) break;

case 16:
//C
this.state = -1;
this.catchState = 0;
;
 //BA.debugLineNum = 477;BA.debugLine="End Sub";
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
public static void  _sql_querycomplete(boolean _success,anywheresoftware.b4a.sql.SQL.ResultSetWrapper _rs) throws Exception{
}
public static anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper  _fontbit(String _icon,float _font_size,int _color,boolean _awesome) throws Exception{
anywheresoftware.b4a.keywords.constants.TypefaceWrapper _typ = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bmp = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper _cvs = null;
double _h = 0;
 //BA.debugLineNum = 726;BA.debugLine="Sub FontBit (icon As String, font_size As Float, c";
 //BA.debugLineNum = 727;BA.debugLine="If color = 0 Then color = Colors.White";
if (_color==0) { 
_color = anywheresoftware.b4a.keywords.Common.Colors.White;};
 //BA.debugLineNum = 728;BA.debugLine="Dim typ As Typeface = Typeface.MATERIALICONS";
_typ = new anywheresoftware.b4a.keywords.constants.TypefaceWrapper();
_typ = (anywheresoftware.b4a.keywords.constants.TypefaceWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.constants.TypefaceWrapper(), (android.graphics.Typeface)(anywheresoftware.b4a.keywords.Common.Typeface.getMATERIALICONS()));
 //BA.debugLineNum = 729;BA.debugLine="If awesome Then typ = Typeface.FONTAWESOME";
if (_awesome) { 
_typ = (anywheresoftware.b4a.keywords.constants.TypefaceWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.constants.TypefaceWrapper(), (android.graphics.Typeface)(anywheresoftware.b4a.keywords.Common.Typeface.getFONTAWESOME()));};
 //BA.debugLineNum = 730;BA.debugLine="Dim bmp As Bitmap";
_bmp = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 731;BA.debugLine="bmp.InitializeMutable(32dip, 32dip)";
_bmp.InitializeMutable(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (32)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (32)));
 //BA.debugLineNum = 732;BA.debugLine="Dim cvs As Canvas";
_cvs = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 733;BA.debugLine="cvs.Initialize2(bmp)";
_cvs.Initialize2((android.graphics.Bitmap)(_bmp.getObject()));
 //BA.debugLineNum = 734;BA.debugLine="Dim h As Double";
_h = 0;
 //BA.debugLineNum = 735;BA.debugLine="If Not(awesome) Then";
if (anywheresoftware.b4a.keywords.Common.Not(_awesome)) { 
 //BA.debugLineNum = 736;BA.debugLine="h = cvs.MeasureStringHeight(icon, typ, font_size";
_h = _cvs.MeasureStringHeight(_icon,(android.graphics.Typeface)(_typ.getObject()),_font_size)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10));
 }else {
 //BA.debugLineNum = 738;BA.debugLine="h = cvs.MeasureStringHeight(icon, typ, font_size";
_h = _cvs.MeasureStringHeight(_icon,(android.graphics.Typeface)(_typ.getObject()),_font_size);
 };
 //BA.debugLineNum = 740;BA.debugLine="cvs.DrawText(icon, bmp.Width / 2, bmp.Height / 2";
_cvs.DrawText(mostCurrent.activityBA,_icon,(float) (_bmp.getWidth()/(double)2),(float) (_bmp.getHeight()/(double)2+_h/(double)2),(android.graphics.Typeface)(_typ.getObject()),_font_size,_color,BA.getEnumFromString(android.graphics.Paint.Align.class,"CENTER"));
 //BA.debugLineNum = 741;BA.debugLine="Return bmp";
if (true) return _bmp;
 //BA.debugLineNum = 742;BA.debugLine="End Sub";
return null;
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 29;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 32;BA.debugLine="Dim ActionBarButton As ACActionBar";
mostCurrent._actionbarbutton = new de.amberhome.objects.appcompat.ACActionBar();
 //BA.debugLineNum = 33;BA.debugLine="Private ToolBar As ACToolBarDark";
mostCurrent._toolbar = new de.amberhome.objects.appcompat.ACToolbarDarkWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private xmlIcon As XmlLayoutBuilder";
mostCurrent._xmlicon = new anywheresoftware.b4a.object.XmlLayoutBuilder();
 //BA.debugLineNum = 36;BA.debugLine="Type ReadingStatus (sBookNo As String, sBookDesc";
;
 //BA.debugLineNum = 41;BA.debugLine="Private MatDialogBuilder As MaterialDialogBuilder";
mostCurrent._matdialogbuilder = new de.amberhome.materialdialogs.MaterialDialogBuilderWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Private lblEmpName As Label";
mostCurrent._lblempname = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private lblBranchName As Label";
mostCurrent._lblbranchname = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Private lblMessage As Label";
mostCurrent._lblmessage = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Private lblBillPeriod As Label";
mostCurrent._lblbillperiod = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Dim rsAssignedBooks As Cursor";
mostCurrent._rsassignedbooks = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 52;BA.debugLine="Private clvBookAssignment As CustomListView";
mostCurrent._clvbookassignment = new b4a.example3.customlistview();
 //BA.debugLineNum = 55;BA.debugLine="Private snack As DSSnackbar";
mostCurrent._snack = new de.amberhome.objects.SnackbarWrapper();
 //BA.debugLineNum = 56;BA.debugLine="Private pnlAssignment As Panel";
mostCurrent._pnlassignment = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 58;BA.debugLine="Private CD As ColorDrawable";
mostCurrent._cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 59;BA.debugLine="Private vibration As B4Avibrate";
mostCurrent._vibration = new com.johan.Vibrate.Vibrate();
 //BA.debugLineNum = 60;BA.debugLine="Private vibratePattern() As Long";
_vibratepattern = new long[(int) (0)];
;
 //BA.debugLineNum = 61;BA.debugLine="Private csAns As CSBuilder";
mostCurrent._csans = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 64;BA.debugLine="Private Drawer As B4XDrawer";
mostCurrent._drawer = new com.bwsi.MeterReader.b4xdrawer();
 //BA.debugLineNum = 65;BA.debugLine="Private lvMenus As ListView";
mostCurrent._lvmenus = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 66;BA.debugLine="Private pnlMenuHeader As Panel";
mostCurrent._pnlmenuheader = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 67;BA.debugLine="Private lblUserBranch As Label";
mostCurrent._lbluserbranch = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 68;BA.debugLine="Private lblUserFullName As Label";
mostCurrent._lbluserfullname = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 70;BA.debugLine="Private lblBookDesc As Label";
mostCurrent._lblbookdesc = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 71;BA.debugLine="Private lblBookNo As Label";
mostCurrent._lblbookno = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 72;BA.debugLine="Private lblNoAccts As Label";
mostCurrent._lblnoaccts = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 74;BA.debugLine="Private lblRead As Label";
mostCurrent._lblread = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 75;BA.debugLine="Private lblUnread As Label";
mostCurrent._lblunread = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 76;BA.debugLine="Private lblMissCode As Label";
mostCurrent._lblmisscode = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 77;BA.debugLine="Private lblZero As Label";
mostCurrent._lblzero = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 78;BA.debugLine="Private lblHigh As Label";
mostCurrent._lblhigh = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 79;BA.debugLine="Private lblLow As Label";
mostCurrent._lbllow = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 80;BA.debugLine="Private lblAve As Label";
mostCurrent._lblave = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 81;BA.debugLine="Private lblUnprint As Label";
mostCurrent._lblunprint = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 82;BA.debugLine="Private btnLoad As ACButton";
mostCurrent._btnload = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 84;BA.debugLine="Private lblReadTitle As Label";
mostCurrent._lblreadtitle = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 85;BA.debugLine="Private lblUnreadTitle As Label";
mostCurrent._lblunreadtitle = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 86;BA.debugLine="Private lblMissCodeTitle As Label";
mostCurrent._lblmisscodetitle = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 87;BA.debugLine="Private lblZeroTitle As Label";
mostCurrent._lblzerotitle = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 88;BA.debugLine="Private lblLowTitle As Label";
mostCurrent._lbllowtitle = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 89;BA.debugLine="Private lblHighTtle As Label";
mostCurrent._lblhighttle = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 90;BA.debugLine="Private lblAveTitle As Label";
mostCurrent._lblavetitle = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 91;BA.debugLine="Private lblUnprintTitle As Label";
mostCurrent._lblunprinttitle = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 93;BA.debugLine="Private RedColor = 0xFFFF0000 As Double";
_redcolor = 0xffff0000;
 //BA.debugLineNum = 94;BA.debugLine="Private GrayColor = 0xFFB1B1B1 As Double";
_graycolor = 0xffb1b1b1;
 //BA.debugLineNum = 95;BA.debugLine="Private BlueColor = 0xFF1976D2 As Double";
_bluecolor = 0xff1976d2;
 //BA.debugLineNum = 96;BA.debugLine="Private btnBP As Panel";
mostCurrent._btnbp = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 97;BA.debugLine="Private btnDL As Panel";
mostCurrent._btndl = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 98;BA.debugLine="Private btnLogOut As Panel";
mostCurrent._btnlogout = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 99;BA.debugLine="Private btnUL As Panel";
mostCurrent._btnul = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 100;BA.debugLine="Private btnValid As Panel";
mostCurrent._btnvalid = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 101;BA.debugLine="Private pnlMenu As Panel";
mostCurrent._pnlmenu = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 102;BA.debugLine="Private pnlStatus As Panel";
mostCurrent._pnlstatus = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 103;BA.debugLine="End Sub";
return "";
}
public static String  _logoff_click() throws Exception{
 //BA.debugLineNum = 406;BA.debugLine="Private Sub LogOFF_Click()";
 //BA.debugLineNum = 407;BA.debugLine="CustomFunctions.ClearUserData";
mostCurrent._customfunctions._clearuserdata /*String*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 408;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 409;BA.debugLine="StartActivity(Login)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._login.getObject()));
 //BA.debugLineNum = 410;BA.debugLine="End Sub";
return "";
}
public static String  _lvmenus_itemclick(int _position,Object _value) throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _cdback = null;
 //BA.debugLineNum = 337;BA.debugLine="Sub lvMenus_ItemClick (Position As Int, Value As O";
 //BA.debugLineNum = 338;BA.debugLine="LogColor(Value, Colors.Red)";
anywheresoftware.b4a.keywords.Common.LogImpl("432505857",BA.ObjectToString(_value),anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 339;BA.debugLine="Select Case Value";
switch (BA.switchObjectToInt(_value,(Object)(1),(Object)(2),(Object)(3),(Object)(4),(Object)(5),(Object)(6),(Object)(7),(Object)(8))) {
case 0: {
 //BA.debugLineNum = 341;BA.debugLine="If GlobalVar.Mod1 = 1 Then";
if (mostCurrent._globalvar._mod1 /*int*/ ==1) { 
 //BA.debugLineNum = 342;BA.debugLine="If DBaseFunctions.IsThereBookAssignments(Globa";
if (mostCurrent._dbasefunctions._istherebookassignments /*boolean*/ (mostCurrent.activityBA,mostCurrent._globalvar._branchid /*int*/ ,(int) (mostCurrent._globalvar._billyear /*double*/ ),mostCurrent._globalvar._billmonth /*int*/ ,mostCurrent._globalvar._userid /*int*/ )==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 343;BA.debugLine="snack.Initialize(\"\",Activity,$\"No Assigned bo";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("No Assigned book(s) for this Reader!"),mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 344;BA.debugLine="SetSnackBarBackground(snack,Colors.Red)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 345;BA.debugLine="SetSnackBarTextColor(snack,Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 346;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 347;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 349;BA.debugLine="StartActivity(ReadingBooks)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._readingbooks.getObject()));
 }else {
 //BA.debugLineNum = 351;BA.debugLine="Return";
if (true) return "";
 };
 break; }
case 1: {
 //BA.debugLineNum = 355;BA.debugLine="If GlobalVar.Mod2 = 1 Then";
if (mostCurrent._globalvar._mod2 /*int*/ ==1) { 
 //BA.debugLineNum = 356;BA.debugLine="StartActivity(CustomerList)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._customerlist.getObject()));
 //BA.debugLineNum = 357;BA.debugLine="ProgressDialogShow2($\"Loading Customer's Billi";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence(("Loading Customer's Billing Data...")),anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 359;BA.debugLine="Return";
if (true) return "";
 };
 break; }
case 2: {
 //BA.debugLineNum = 363;BA.debugLine="If GlobalVar.Mod3 = 1 Then";
if (mostCurrent._globalvar._mod3 /*int*/ ==1) { 
 //BA.debugLineNum = 364;BA.debugLine="StartActivity(CMRVR)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._cmrvr.getObject()));
 }else {
 //BA.debugLineNum = 366;BA.debugLine="Return";
if (true) return "";
 };
 break; }
case 3: {
 //BA.debugLineNum = 370;BA.debugLine="If GlobalVar.Mod4 = 1 Then";
if (mostCurrent._globalvar._mod4 /*int*/ ==1) { 
 //BA.debugLineNum = 371;BA.debugLine="StartActivity(ReadingSettings)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._readingsettings.getObject()));
 }else {
 //BA.debugLineNum = 373;BA.debugLine="Return";
if (true) return "";
 };
 break; }
case 4: {
 //BA.debugLineNum = 377;BA.debugLine="If GlobalVar.Mod5 = 1 Then";
if (mostCurrent._globalvar._mod5 /*int*/ ==1) { 
 //BA.debugLineNum = 378;BA.debugLine="StartActivity(DataSyncing)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._datasyncing.getObject()));
 }else {
 //BA.debugLineNum = 380;BA.debugLine="Return";
if (true) return "";
 };
 break; }
case 5: {
 //BA.debugLineNum = 384;BA.debugLine="If GlobalVar.Mod6 = 1 Then";
if (mostCurrent._globalvar._mod6 /*int*/ ==1) { 
 //BA.debugLineNum = 385;BA.debugLine="StartActivity(UserAccountSettings)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._useraccountsettings.getObject()));
 }else {
 //BA.debugLineNum = 387;BA.debugLine="Return";
if (true) return "";
 };
 break; }
case 6: {
 //BA.debugLineNum = 391;BA.debugLine="ShowLogOffSnackBar";
_showlogoffsnackbar();
 break; }
case 7: {
 //BA.debugLineNum = 393;BA.debugLine="vibration.vibrateOnce(1000)";
mostCurrent._vibration.vibrateOnce(processBA,(long) (1000));
 //BA.debugLineNum = 394;BA.debugLine="snack.Initialize(\"CloseButton\", Activity, $\"Clo";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"CloseButton",(android.view.View)(mostCurrent._activity.getObject()),("Close ")+anywheresoftware.b4a.keywords.Common.Application.getLabelName()+("?"),mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 395;BA.debugLine="snack.SetAction(csAns)";
mostCurrent._snack.SetAction(BA.ObjectToString(mostCurrent._csans));
 //BA.debugLineNum = 396;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 397;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 398;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 break; }
}
;
 //BA.debugLineNum = 400;BA.debugLine="Dim CDBack As ColorDrawable";
_cdback = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 401;BA.debugLine="CDBack.Initialize(Colors.Transparent,0)";
_cdback.Initialize(anywheresoftware.b4a.keywords.Common.Colors.Transparent,(int) (0));
 //BA.debugLineNum = 402;BA.debugLine="lvMenus.Background = CDBack";
mostCurrent._lvmenus.setBackground((android.graphics.drawable.Drawable)(_cdback.getObject()));
 //BA.debugLineNum = 403;BA.debugLine="Drawer.LeftOpen = False";
mostCurrent._drawer._setleftopen /*boolean*/ (anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 404;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 25;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 26;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 27;BA.debugLine="End Sub";
return "";
}
public static String  _setsnackbarbackground(de.amberhome.objects.SnackbarWrapper _psnack,int _pcolor) throws Exception{
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
 //BA.debugLineNum = 707;BA.debugLine="Sub SetSnackBarBackground(pSnack As DSSnackbar, pC";
 //BA.debugLineNum = 708;BA.debugLine="Dim v As View";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
 //BA.debugLineNum = 709;BA.debugLine="v = pSnack.View";
_v = (anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(_psnack.getView()));
 //BA.debugLineNum = 710;BA.debugLine="v.Color = pColor";
_v.setColor(_pcolor);
 //BA.debugLineNum = 711;BA.debugLine="End Sub";
return "";
}
public static String  _setsnackbartextcolor(de.amberhome.objects.SnackbarWrapper _psnack,int _pcolor) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _p = null;
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
anywheresoftware.b4a.objects.LabelWrapper _textv = null;
 //BA.debugLineNum = 713;BA.debugLine="Sub SetSnackBarTextColor(pSnack As DSSnackbar, pCo";
 //BA.debugLineNum = 714;BA.debugLine="Dim p As Panel";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 715;BA.debugLine="p = pSnack.View";
_p = (anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(_psnack.getView()));
 //BA.debugLineNum = 716;BA.debugLine="For Each v As View In p.GetAllViewsRecursive";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
{
final anywheresoftware.b4a.BA.IterableList group3 = _p.GetAllViewsRecursive();
final int groupLen3 = group3.getSize()
;int index3 = 0;
;
for (; index3 < groupLen3;index3++){
_v = (anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(group3.Get(index3)));
 //BA.debugLineNum = 717;BA.debugLine="If v Is Label Then";
if (_v.getObjectOrNull() instanceof android.widget.TextView) { 
 //BA.debugLineNum = 718;BA.debugLine="Dim textv As Label";
_textv = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 719;BA.debugLine="textv = v";
_textv = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_v.getObject()));
 //BA.debugLineNum = 720;BA.debugLine="textv.TextColor = pColor";
_textv.setTextColor(_pcolor);
 //BA.debugLineNum = 721;BA.debugLine="Exit";
if (true) break;
 };
 }
};
 //BA.debugLineNum = 724;BA.debugLine="End Sub";
return "";
}
public static String  _showlogoffsnackbar() throws Exception{
 //BA.debugLineNum = 777;BA.debugLine="Private Sub ShowLogOffSnackBar";
 //BA.debugLineNum = 778;BA.debugLine="Drawer.LeftOpen = False";
mostCurrent._drawer._setleftopen /*boolean*/ (anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 779;BA.debugLine="vibration.vibrateOnce(1000)";
mostCurrent._vibration.vibrateOnce(processBA,(long) (1000));
 //BA.debugLineNum = 781;BA.debugLine="snack.Initialize(\"LogOFF\", Activity, $\"Sure to Lo";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"LogOFF",(android.view.View)(mostCurrent._activity.getObject()),("Sure to Log Off now?"),mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 782;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 783;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 785;BA.debugLine="snack.SetAction($\"YES\"$)";
mostCurrent._snack.SetAction(("YES"));
 //BA.debugLineNum = 786;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 787;BA.debugLine="End Sub";
return "";
}
public static String  _showwarningdialog() throws Exception{
anywheresoftware.b4a.objects.CSBuilder _cscontent1 = null;
anywheresoftware.b4a.objects.CSBuilder _csbuttonok = null;
anywheresoftware.b4a.objects.CSBuilder _cstitle = null;
 //BA.debugLineNum = 663;BA.debugLine="Private Sub ShowWarningDialog";
 //BA.debugLineNum = 664;BA.debugLine="Dim csContent1, csButtonOK, csTitle As CSBuilder";
_cscontent1 = new anywheresoftware.b4a.objects.CSBuilder();
_csbuttonok = new anywheresoftware.b4a.objects.CSBuilder();
_cstitle = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 666;BA.debugLine="csTitle.Initialize.Color(0xFFDC143C).Bold.Size(20";
_cstitle.Initialize().Color((int) (0xffdc143c)).Bold().Size((int) (20)).Append(BA.ObjectToCharSequence(("W A R N I N G! "))).PopAll();
 //BA.debugLineNum = 670;BA.debugLine="csContent1.Initialize.Bold.Color(Colors.Red).Size";
_cscontent1.Initialize().Bold().Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Size((int) (14)).Append(BA.ObjectToCharSequence(("It seems that the BILLER Didn't Complete the Reading Data Downloading Process"))).Pop().Pop();
 //BA.debugLineNum = 671;BA.debugLine="csContent1.Color(Colors.Gray).Size(14).Bold.Appen";
_cscontent1.Color(anywheresoftware.b4a.keywords.Common.Colors.Gray).Size((int) (14)).Bold().Append(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+("Please ask the Biller to Re-download your reading data.\n"+"	\n"+"	--- CBIT"))).Pop().Pop().Size((int) (16)).PopAll();
 //BA.debugLineNum = 674;BA.debugLine="csButtonOK.Initialize.Bold.Color(Colors.Gray).Siz";
_csbuttonok.Initialize().Bold().Color(anywheresoftware.b4a.keywords.Common.Colors.Gray).Size((int) (11)).Append(BA.ObjectToCharSequence(("OK"))).Pop();
 //BA.debugLineNum = 676;BA.debugLine="MatDialogBuilder.Initialize(\"WarningDialog\")";
mostCurrent._matdialogbuilder.Initialize(mostCurrent.activityBA,"WarningDialog");
 //BA.debugLineNum = 677;BA.debugLine="MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIG";
mostCurrent._matdialogbuilder.Theme(mostCurrent._matdialogbuilder.THEME_LIGHT);
 //BA.debugLineNum = 678;BA.debugLine="MatDialogBuilder.Title(csTitle)";
mostCurrent._matdialogbuilder.Title(BA.ObjectToCharSequence(_cstitle.getObject()));
 //BA.debugLineNum = 679;BA.debugLine="MatDialogBuilder.Content(csContent1)";
mostCurrent._matdialogbuilder.Content(BA.ObjectToCharSequence(_cscontent1.getObject()));
 //BA.debugLineNum = 680;BA.debugLine="MatDialogBuilder.IconRes(\"ic_warning_black_36dp2\"";
mostCurrent._matdialogbuilder.IconRes("ic_warning_black_36dp2").LimitIconToDefaultSize();
 //BA.debugLineNum = 681;BA.debugLine="MatDialogBuilder.PositiveText(csButtonOK)";
mostCurrent._matdialogbuilder.PositiveText(BA.ObjectToCharSequence(_csbuttonok.getObject()));
 //BA.debugLineNum = 682;BA.debugLine="MatDialogBuilder.Cancelable(True)";
mostCurrent._matdialogbuilder.Cancelable(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 683;BA.debugLine="MatDialogBuilder.AutoDismiss(True)";
mostCurrent._matdialogbuilder.AutoDismiss(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 684;BA.debugLine="MatDialogBuilder.CanceledOnTouchOutside(True)";
mostCurrent._matdialogbuilder.CanceledOnTouchOutside(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 685;BA.debugLine="MatDialogBuilder.ButtonRippleColor(GlobalVar.PriC";
mostCurrent._matdialogbuilder.ButtonRippleColor((int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 686;BA.debugLine="MatDialogBuilder.Show";
mostCurrent._matdialogbuilder.Show();
 //BA.debugLineNum = 687;BA.debugLine="End Sub";
return "";
}
public static String  _showwelcomeadmindialog() throws Exception{
anywheresoftware.b4a.objects.CSBuilder _cscontent1 = null;
anywheresoftware.b4a.objects.CSBuilder _cscontent2 = null;
anywheresoftware.b4a.objects.CSBuilder _cstitle = null;
 //BA.debugLineNum = 634;BA.debugLine="Private Sub ShowWelcomeAdminDialog";
 //BA.debugLineNum = 635;BA.debugLine="Dim csContent1, csContent2, csTitle As CSBuilder";
_cscontent1 = new anywheresoftware.b4a.objects.CSBuilder();
_cscontent2 = new anywheresoftware.b4a.objects.CSBuilder();
_cstitle = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 637;BA.debugLine="csTitle.Initialize.Color(GlobalVar.PriColor).Size";
_cstitle.Initialize().Color((int) (mostCurrent._globalvar._pricolor /*double*/ )).Size((int) (16)).Append(BA.ObjectToCharSequence(("Good Day Sir/Ma'am "))).Append(BA.ObjectToCharSequence(_titlecase(mostCurrent._globalvar._empname /*String*/ )+"!")).PopAll();
 //BA.debugLineNum = 638;BA.debugLine="csContent1.Initialize.Size(18).Color(Colors.Red).";
_cscontent1.Initialize().Size((int) (18)).Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Bold().Append(BA.ObjectToCharSequence(("WELCOME to METER READING ON ANDROID APP!")+anywheresoftware.b4a.keywords.Common.CRLF)).PopAll();
 //BA.debugLineNum = 639;BA.debugLine="csContent2.Initialize.Color(GlobalVar.PriColor).B";
_cscontent2.Initialize().Color((int) (mostCurrent._globalvar._pricolor /*double*/ )).Bold().Append(BA.ObjectToCharSequence(("Billing Settings"))).Color(anywheresoftware.b4a.keywords.Common.Colors.DarkGray).Append(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.CRLF+("Bill Month: ")+mostCurrent._globalvar._billmonthname /*String*/ +anywheresoftware.b4a.keywords.Common.CRLF+("Bill Year: ")+BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ ))).PopAll();
 //BA.debugLineNum = 641;BA.debugLine="MatDialogBuilder.Initialize(\"WelcomeAdminDialog\")";
mostCurrent._matdialogbuilder.Initialize(mostCurrent.activityBA,"WelcomeAdminDialog");
 //BA.debugLineNum = 642;BA.debugLine="MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIG";
mostCurrent._matdialogbuilder.Theme(mostCurrent._matdialogbuilder.THEME_LIGHT);
 //BA.debugLineNum = 643;BA.debugLine="MatDialogBuilder.Title(csTitle)";
mostCurrent._matdialogbuilder.Title(BA.ObjectToCharSequence(_cstitle.getObject()));
 //BA.debugLineNum = 644;BA.debugLine="MatDialogBuilder.Content(csContent1 & CRLF & csCo";
mostCurrent._matdialogbuilder.Content(BA.ObjectToCharSequence(BA.ObjectToString(_cscontent1)+anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(_cscontent2)));
 //BA.debugLineNum = 645;BA.debugLine="MatDialogBuilder.IconRes(\"ic_account_box_black_36";
mostCurrent._matdialogbuilder.IconRes("ic_account_box_black_36dp").LimitIconToDefaultSize();
 //BA.debugLineNum = 646;BA.debugLine="MatDialogBuilder.PositiveColor(GlobalVar.PriColor";
mostCurrent._matdialogbuilder.PositiveColor((int) (mostCurrent._globalvar._pricolor /*double*/ )).PositiveText(BA.ObjectToCharSequence("OK"));
 //BA.debugLineNum = 647;BA.debugLine="MatDialogBuilder.NeutralColor(Colors.Red).Neutral";
mostCurrent._matdialogbuilder.NeutralColor(anywheresoftware.b4a.keywords.Common.Colors.Red).NeutralText(BA.ObjectToCharSequence("Reset Settings?"));
 //BA.debugLineNum = 648;BA.debugLine="MatDialogBuilder.Cancelable(True)";
mostCurrent._matdialogbuilder.Cancelable(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 649;BA.debugLine="MatDialogBuilder.AutoDismiss(True)";
mostCurrent._matdialogbuilder.AutoDismiss(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 650;BA.debugLine="MatDialogBuilder.CanceledOnTouchOutside(True)";
mostCurrent._matdialogbuilder.CanceledOnTouchOutside(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 651;BA.debugLine="MatDialogBuilder.ButtonRippleColor(GlobalVar.PriC";
mostCurrent._matdialogbuilder.ButtonRippleColor((int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 652;BA.debugLine="MatDialogBuilder.Show";
mostCurrent._matdialogbuilder.Show();
 //BA.debugLineNum = 653;BA.debugLine="End Sub";
return "";
}
public static String  _showwelcomedialog() throws Exception{
anywheresoftware.b4a.objects.CSBuilder _cscontent1 = null;
anywheresoftware.b4a.objects.CSBuilder _csbuttonok = null;
anywheresoftware.b4a.objects.CSBuilder _cstitle = null;
 //BA.debugLineNum = 610;BA.debugLine="Private Sub ShowWelcomeDialog";
 //BA.debugLineNum = 611;BA.debugLine="Dim csContent1, csButtonOK, csTitle As CSBuilder";
_cscontent1 = new anywheresoftware.b4a.objects.CSBuilder();
_csbuttonok = new anywheresoftware.b4a.objects.CSBuilder();
_cstitle = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 613;BA.debugLine="csTitle.Initialize.Color(GlobalVar.PriColor).Bold";
_cstitle.Initialize().Color((int) (mostCurrent._globalvar._pricolor /*double*/ )).Bold().Size((int) (16)).Append(BA.ObjectToCharSequence(("Good Day Sir "))).Append(BA.ObjectToCharSequence(_titlecase(mostCurrent._globalvar._empname /*String*/ )+"!")).PopAll();
 //BA.debugLineNum = 617;BA.debugLine="csContent1.Initialize.Bold.Color(Colors.Gray).Siz";
_cscontent1.Initialize().Bold().Color(anywheresoftware.b4a.keywords.Common.Colors.Gray).Size((int) (11)).Append(BA.ObjectToCharSequence(("WELCOME to METER READING ON ANDROID APP!"))).Pop().Pop();
 //BA.debugLineNum = 618;BA.debugLine="csContent1.Color(GlobalVar.PriColor).Size(18).Bol";
_cscontent1.Color((int) (mostCurrent._globalvar._pricolor /*double*/ )).Size((int) (18)).Bold().Append(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+("BILLING SETTINGS:"))).Pop().Pop().Size((int) (16)).Append(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.CRLF+("Bill Month: ")+mostCurrent._globalvar._billmonthname /*String*/ +anywheresoftware.b4a.keywords.Common.CRLF+("Bill Year: ")+BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ ))).PopAll();
 //BA.debugLineNum = 619;BA.debugLine="csButtonOK.Initialize.Bold.Color(Colors.Gray).Siz";
_csbuttonok.Initialize().Bold().Color(anywheresoftware.b4a.keywords.Common.Colors.Gray).Size((int) (11)).Append(BA.ObjectToCharSequence(("OK"))).Pop();
 //BA.debugLineNum = 621;BA.debugLine="MatDialogBuilder.Initialize(\"WelcomeDialog\")";
mostCurrent._matdialogbuilder.Initialize(mostCurrent.activityBA,"WelcomeDialog");
 //BA.debugLineNum = 622;BA.debugLine="MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIG";
mostCurrent._matdialogbuilder.Theme(mostCurrent._matdialogbuilder.THEME_LIGHT);
 //BA.debugLineNum = 623;BA.debugLine="MatDialogBuilder.Title(csTitle)";
mostCurrent._matdialogbuilder.Title(BA.ObjectToCharSequence(_cstitle.getObject()));
 //BA.debugLineNum = 624;BA.debugLine="MatDialogBuilder.Content(csContent1)";
mostCurrent._matdialogbuilder.Content(BA.ObjectToCharSequence(_cscontent1.getObject()));
 //BA.debugLineNum = 625;BA.debugLine="MatDialogBuilder.IconRes(\"ic_account_box_black_36";
mostCurrent._matdialogbuilder.IconRes("ic_account_box_black_36dp").LimitIconToDefaultSize();
 //BA.debugLineNum = 626;BA.debugLine="MatDialogBuilder.PositiveText(csButtonOK)";
mostCurrent._matdialogbuilder.PositiveText(BA.ObjectToCharSequence(_csbuttonok.getObject()));
 //BA.debugLineNum = 627;BA.debugLine="MatDialogBuilder.Cancelable(True)";
mostCurrent._matdialogbuilder.Cancelable(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 628;BA.debugLine="MatDialogBuilder.AutoDismiss(True)";
mostCurrent._matdialogbuilder.AutoDismiss(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 629;BA.debugLine="MatDialogBuilder.CanceledOnTouchOutside(True)";
mostCurrent._matdialogbuilder.CanceledOnTouchOutside(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 630;BA.debugLine="MatDialogBuilder.ButtonRippleColor(GlobalVar.PriC";
mostCurrent._matdialogbuilder.ButtonRippleColor((int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 631;BA.debugLine="MatDialogBuilder.Show";
mostCurrent._matdialogbuilder.Show();
 //BA.debugLineNum = 632;BA.debugLine="End Sub";
return "";
}
public static String  _titlecase(String _s) throws Exception{
anywheresoftware.b4a.keywords.Regex.MatcherWrapper _m = null;
int _i = 0;
 //BA.debugLineNum = 767;BA.debugLine="Sub TitleCase (s As String) As String";
 //BA.debugLineNum = 768;BA.debugLine="s = s.ToLowerCase";
_s = _s.toLowerCase();
 //BA.debugLineNum = 769;BA.debugLine="Dim m As Matcher = Regex.Matcher(\"\\b(\\w)\", s)";
_m = new anywheresoftware.b4a.keywords.Regex.MatcherWrapper();
_m = anywheresoftware.b4a.keywords.Common.Regex.Matcher("\\b(\\w)",_s);
 //BA.debugLineNum = 770;BA.debugLine="Do While m.Find";
while (_m.Find()) {
 //BA.debugLineNum = 771;BA.debugLine="Dim i As Int = m.GetStart(1)";
_i = _m.GetStart((int) (1));
 //BA.debugLineNum = 772;BA.debugLine="s = s.SubString2(0, i) & s.SubString2(i, i + 1).";
_s = _s.substring((int) (0),_i)+_s.substring(_i,(int) (_i+1)).toUpperCase()+_s.substring((int) (_i+1));
 }
;
 //BA.debugLineNum = 774;BA.debugLine="Return s";
if (true) return _s;
 //BA.debugLineNum = 775;BA.debugLine="End Sub";
return "";
}
public static String  _toolbar_menuitemclick(de.amberhome.objects.appcompat.ACMenuItemWrapper _item) throws Exception{
 //BA.debugLineNum = 219;BA.debugLine="Sub ToolBar_MenuItemClick (Item As ACMenuItem)";
 //BA.debugLineNum = 220;BA.debugLine="Select Case Item.Id";
switch (BA.switchObjectToInt(_item.getId(),(int) (1),(int) (2))) {
case 0: {
 //BA.debugLineNum = 222;BA.debugLine="StartActivity(UserAccountSettings)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._useraccountsettings.getObject()));
 break; }
case 1: {
 //BA.debugLineNum = 224;BA.debugLine="StartActivity(ReadingSettings)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._readingsettings.getObject()));
 break; }
}
;
 //BA.debugLineNum = 227;BA.debugLine="End Sub";
return "";
}
public static String  _toolbar_navigationitemclick() throws Exception{
 //BA.debugLineNum = 212;BA.debugLine="Sub ToolBar_NavigationItemClick";
 //BA.debugLineNum = 216;BA.debugLine="Drawer.LeftOpen = Not(Drawer.LeftOpen)";
mostCurrent._drawer._setleftopen /*boolean*/ (anywheresoftware.b4a.keywords.Common.Not(mostCurrent._drawer._getleftopen /*boolean*/ ()));
 //BA.debugLineNum = 217;BA.debugLine="End Sub";
return "";
}
public static String  _warningdialog_buttonpressed(de.amberhome.materialdialogs.MaterialDialogWrapper _mdialog,String _saction) throws Exception{
 //BA.debugLineNum = 689;BA.debugLine="Private Sub WarningDialog_ButtonPressed(mDialog As";
 //BA.debugLineNum = 690;BA.debugLine="Select Case sAction";
switch (BA.switchObjectToInt(_saction,_mdialog.ACTION_POSITIVE,_mdialog.ACTION_NEGATIVE,_mdialog.ACTION_NEUTRAL)) {
case 0: {
 //BA.debugLineNum = 692;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 693;BA.debugLine="ExitApplication";
anywheresoftware.b4a.keywords.Common.ExitApplication();
 break; }
case 1: {
 break; }
case 2: {
 break; }
}
;
 //BA.debugLineNum = 697;BA.debugLine="End Sub";
return "";
}
public static String  _welcomeadmindialog_buttonpressed(de.amberhome.materialdialogs.MaterialDialogWrapper _mdialog,String _saction) throws Exception{
 //BA.debugLineNum = 655;BA.debugLine="Private Sub WelcomeAdminDialog_ButtonPressed(mDial";
 //BA.debugLineNum = 656;BA.debugLine="Select Case sAction";
switch (BA.switchObjectToInt(_saction,_mdialog.ACTION_POSITIVE,_mdialog.ACTION_NEGATIVE,_mdialog.ACTION_NEUTRAL)) {
case 0: {
 break; }
case 1: {
 break; }
case 2: {
 break; }
}
;
 //BA.debugLineNum = 661;BA.debugLine="End Sub";
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
