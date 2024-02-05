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

public class customerbill extends androidx.appcompat.app.AppCompatActivity implements B4AActivity{
	public static customerbill mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "com.bwsi.MeterReader", "com.bwsi.MeterReader.customerbill");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (customerbill).");
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
		activityBA = new BA(this, layout, processBA, "com.bwsi.MeterReader", "com.bwsi.MeterReader.customerbill");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "com.bwsi.MeterReader.customerbill", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (customerbill) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (customerbill) Resume **");
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
		return customerbill.class;
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
            BA.LogInfo("** Activity (customerbill) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (customerbill) Pause event (activity is not paused). **");
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
            customerbill mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (customerbill) Resume **");
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
public static Object _index = null;
public static anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public static com.bwsi.MeterReader.clsprint _epsonprint = null;
public static anywheresoftware.b4a.objects.Serial.BluetoothAdmin _btadmin = null;
public static anywheresoftware.b4a.objects.collections.Map _paireddevices = null;
public static anywheresoftware.b4a.objects.collections.List _founddevices = null;
public static String _devicename = "";
public static String _devicemac = "";
public static anywheresoftware.b4a.objects.Serial _serial1 = null;
public static anywheresoftware.b4a.objects.streams.File.TextWriterWrapper _tmprinter = null;
public static String _printbuffer = "";
public static byte[] _printlogo = null;
public static anywheresoftware.b4a.randomaccessfile.AsyncStreams _ostream = null;
public static int _res = 0;
public static anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _flagbitmap = null;
public static anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _logobmp = null;
public static anywheresoftware.b4j.object.JavaObject _woosimcmd = null;
public static anywheresoftware.b4j.object.JavaObject _woosimimage = null;
public static anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _logo = null;
public static anywheresoftware.b4a.objects.RuntimePermissions _permissions = null;
public de.amberhome.objects.appcompat.ACToolbarDarkWrapper _toolbar = null;
public de.amberhome.objects.appcompat.ACActionBar _actionbarbutton = null;
public anywheresoftware.b4a.object.XmlLayoutBuilder _xmlicon = null;
public de.amberhome.objects.SnackbarWrapper _snack = null;
public anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
public de.amberhome.materialdialogs.MaterialDialogBuilderWrapper _matdialogbuilder = null;
public anywheresoftware.b4a.sql.SQL.CursorWrapper _rsloadedbook = null;
public anywheresoftware.b4a.sql.SQL.CursorWrapper _rsunreadacct = null;
public anywheresoftware.b4a.sql.SQL.CursorWrapper _rsreadacct = null;
public anywheresoftware.b4a.sql.SQL.CursorWrapper _rsallacct = null;
public anywheresoftware.b4a.sql.SQL.CursorWrapper _rsunusualreading = null;
public static String _billno = "";
public static String _acctno = "";
public static String _acctname = "";
public static String _acctaddress = "";
public static String _meterno = "";
public static double _gdeposit = 0;
public static double _prevrdg = 0;
public static String _presrdg = "";
public static String _duedate = "";
public static double _addcons = 0;
public static double _totalcum = 0;
public static double _basicamt = 0;
public static double _pcaamt = 0;
public static double _addtobillamt = 0;
public static double _advpayment = 0;
public static double _penaltyamt = 0;
public static double _totaldueamtaftersc = 0;
public static double _currentamt = 0;
public static double _arrearsamt = 0;
public static int _withduedate = 0;
public static double _totaldueamt = 0;
public static double _senioronbeforeamt = 0;
public static double _seniorafteramt = 0;
public static double _totaldueamtbeforesc = 0;
public static String _bookseq = "";
public static String _custclass = "";
public static String _sbranchname = "";
public static String _sbranchaddress = "";
public static String _sbranchcontactno = "";
public static String _stinno = "";
public static String _sbranchcode = "";
public static String _sbillperiod = "";
public static int _bookid = 0;
public static String _billdate = "";
public static String _datefrom = "";
public static String _dateto = "";
public static String _spresrdg = "";
public static String _sprevrdg = "";
public static String _saddcons = "";
public static String _stotalcum = "";
public static String _sgdeposit = "";
public static String _sbasicamt = "";
public static String _spcaamt = "";
public static String _scurrentamt = "";
public static String _ssenioronbeforeamt = "";
public static String _saddtobillamt = "";
public static String _sarrearsamt = "";
public static String _sadvpayment = "";
public static String _stotaldueamt = "";
public static String _spenaltyamt = "";
public static String _sseniorafteramt = "";
public static String _stotaldueamtaftersc = "";
public com.johan.Vibrate.Vibrate _vibration = null;
public static long[] _vibratepattern = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnprint = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblacctno = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblacctname = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbladdress = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblmeterno = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblbookseq = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblclass = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblgd = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpresrdg = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblprevrdg = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbladdcons = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltotalcons = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblbasic = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpca = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcurrent = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblscdisc10 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblothers = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblarrears = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbladvances = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltotdue = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblduedate = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpenalty = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblscdisc5 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblafterdueamt = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnclose = null;
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
public com.bwsi.MeterReader.customerlist _customerlist = null;
public com.bwsi.MeterReader.dbutils _dbutils = null;
public com.bwsi.MeterReader.globalvar _globalvar = null;
public com.bwsi.MeterReader.modvariables _modvariables = null;
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
 //BA.debugLineNum = 166;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 167;BA.debugLine="MyScale.SetRate(0.5)";
mostCurrent._myscale._setrate /*String*/ (mostCurrent.activityBA,0.5);
 //BA.debugLineNum = 168;BA.debugLine="Activity.LoadLayout(\"BillDetails\")";
mostCurrent._activity.LoadLayout("BillDetails",mostCurrent.activityBA);
 //BA.debugLineNum = 170;BA.debugLine="Log(GlobalVar.BillID)";
anywheresoftware.b4a.keywords.Common.LogImpl("143909124",BA.NumberToString(mostCurrent._globalvar._billid /*double*/ ),0);
 //BA.debugLineNum = 171;BA.debugLine="GetBillDetais(GlobalVar.BillID)";
_getbilldetais(mostCurrent._globalvar._billid /*double*/ );
 //BA.debugLineNum = 172;BA.debugLine="DisplayRecords";
_displayrecords();
 //BA.debugLineNum = 174;BA.debugLine="GlobalVar.CSTitle.Initialize.Size(17).Bold.Append";
mostCurrent._globalvar._cstitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (17)).Bold().Append(BA.ObjectToCharSequence(("BILL NO. ")+mostCurrent._billno)).PopAll();
 //BA.debugLineNum = 175;BA.debugLine="GlobalVar.CSSubTitle.Initialize.Size(14).Append($";
mostCurrent._globalvar._cssubtitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (14)).Append(BA.ObjectToCharSequence(("For the Month of ")+mostCurrent._sbillperiod)).PopAll();
 //BA.debugLineNum = 177;BA.debugLine="ToolBar.InitMenuListener";
mostCurrent._toolbar.InitMenuListener();
 //BA.debugLineNum = 178;BA.debugLine="ToolBar.Title = GlobalVar.CSTitle";
mostCurrent._toolbar.setTitle(BA.ObjectToCharSequence(mostCurrent._globalvar._cstitle /*anywheresoftware.b4a.objects.CSBuilder*/ .getObject()));
 //BA.debugLineNum = 179;BA.debugLine="ToolBar.SubTitle = GlobalVar.CSSubTitle";
mostCurrent._toolbar.setSubTitle(BA.ObjectToCharSequence(mostCurrent._globalvar._cssubtitle /*anywheresoftware.b4a.objects.CSBuilder*/ .getObject()));
 //BA.debugLineNum = 180;BA.debugLine="ToolBar.SetElevationAnimated(5,5dip)";
mostCurrent._toolbar.SetElevationAnimated((int) (5),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 182;BA.debugLine="Dim jo As JavaObject";
_jo = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 183;BA.debugLine="Dim xl As XmlLayoutBuilder";
_xl = new anywheresoftware.b4a.object.XmlLayoutBuilder();
 //BA.debugLineNum = 184;BA.debugLine="jo = ToolBar";
_jo = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(mostCurrent._toolbar.getObject()));
 //BA.debugLineNum = 185;BA.debugLine="jo.RunMethod(\"setPopupTheme\", Array(xl.GetResourc";
_jo.RunMethod("setPopupTheme",new Object[]{(Object)(_xl.GetResourceId("style","ToolbarMenu"))});
 //BA.debugLineNum = 186;BA.debugLine="jo.RunMethod(\"setContentInsetStartWithNavigation\"";
_jo.RunMethod("setContentInsetStartWithNavigation",new Object[]{(Object)(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)))});
 //BA.debugLineNum = 187;BA.debugLine="jo.RunMethod(\"setTitleMarginStart\", Array(0dip))";
_jo.RunMethod("setTitleMarginStart",new Object[]{(Object)(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)))});
 //BA.debugLineNum = 189;BA.debugLine="ActionBarButton.Initialize";
mostCurrent._actionbarbutton.Initialize(mostCurrent.activityBA);
 //BA.debugLineNum = 190;BA.debugLine="ActionBarButton.ShowUpIndicator = True";
mostCurrent._actionbarbutton.setShowUpIndicator(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 191;BA.debugLine="BTAdmin.Initialize(\"Admin\")";
_btadmin.Initialize(processBA,"Admin");
 //BA.debugLineNum = 192;BA.debugLine="Serial1.Initialize(\"Printer\")";
_serial1.Initialize("Printer");
 //BA.debugLineNum = 193;BA.debugLine="GetBillDetais(GlobalVar.BillID)";
_getbilldetais(mostCurrent._globalvar._billid /*double*/ );
 //BA.debugLineNum = 195;BA.debugLine="CD.Initialize2(0xFF1976D2, 25, 0,0xFF000000)";
mostCurrent._cd.Initialize2((int) (0xff1976d2),(int) (25),(int) (0),(int) (0xff000000));
 //BA.debugLineNum = 196;BA.debugLine="btnClose.Background = CD";
mostCurrent._btnclose.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cd.getObject()));
 //BA.debugLineNum = 197;BA.debugLine="btnPrint.Background = CD";
mostCurrent._btnprint.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cd.getObject()));
 //BA.debugLineNum = 198;BA.debugLine="End Sub";
return "";
}
public static String  _activity_createmenu(de.amberhome.objects.appcompat.ACMenuWrapper _menu) throws Exception{
 //BA.debugLineNum = 230;BA.debugLine="Sub Activity_CreateMenu(Menu As ACMenu)";
 //BA.debugLineNum = 231;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 200;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 201;BA.debugLine="If KeyCode = 4 Then";
if (_keycode==4) { 
 //BA.debugLineNum = 202;BA.debugLine="ToolBar_NavigationItemClick";
_toolbar_navigationitemclick();
 //BA.debugLineNum = 203;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 205;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 207;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 214;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 215;BA.debugLine="End Sub";
return "";
}
public static String  _activity_permissionresult(String _permission,boolean _result) throws Exception{
 //BA.debugLineNum = 217;BA.debugLine="Sub Activity_PermissionResult (Permission As Strin";
 //BA.debugLineNum = 218;BA.debugLine="If Permission = Permissions.PERMISSION_CAMERA The";
if ((_permission).equals(_permissions.PERMISSION_CAMERA)) { 
 //BA.debugLineNum = 219;BA.debugLine="LogColor(\"YOU CAN USE THE CAMERA\",Colors.Red)";
anywheresoftware.b4a.keywords.Common.LogImpl("144171266","YOU CAN USE THE CAMERA",anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 220;BA.debugLine="snack.Initialize(\"\",Activity, $\"Camera is ready";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Camera is ready to use..."),mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 221;BA.debugLine="SetSnackBarBackground(snack, GlobalVar.PriColor)";
_setsnackbarbackground(mostCurrent._snack,(int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 222;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 223;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 };
 //BA.debugLineNum = 225;BA.debugLine="Log(\"Permissions OK\")";
anywheresoftware.b4a.keywords.Common.LogImpl("144171272","Permissions OK",0);
 //BA.debugLineNum = 227;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 209;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 210;BA.debugLine="BTAdmin.Initialize(\"Admin\")";
_btadmin.Initialize(processBA,"Admin");
 //BA.debugLineNum = 211;BA.debugLine="Serial1.Initialize(\"Printer\")";
_serial1.Initialize("Printer");
 //BA.debugLineNum = 212;BA.debugLine="End Sub";
return "";
}
public static String  _addwhitespaces(String _svalue) throws Exception{
String _sretval = "";
int _ilen = 0;
String _sspace = "";
int _addspace = 0;
 //BA.debugLineNum = 402;BA.debugLine="Private Sub AddWhiteSpaces(sValue As String) As St";
 //BA.debugLineNum = 403;BA.debugLine="Dim sRetVal As String";
_sretval = "";
 //BA.debugLineNum = 404;BA.debugLine="Dim iLen As Int";
_ilen = 0;
 //BA.debugLineNum = 405;BA.debugLine="Dim sSpace As String";
_sspace = "";
 //BA.debugLineNum = 406;BA.debugLine="Dim AddSpace As Int";
_addspace = 0;
 //BA.debugLineNum = 407;BA.debugLine="Try";
try { //BA.debugLineNum = 409;BA.debugLine="iLen = sValue.Length";
_ilen = _svalue.length();
 //BA.debugLineNum = 410;BA.debugLine="AddSpace = 32 - (19 + iLen)";
_addspace = (int) (32-(19+_ilen));
 //BA.debugLineNum = 411;BA.debugLine="sSpace = RepeatChar(\" \", AddSpace)";
_sspace = _repeatchar(" ",_addspace);
 //BA.debugLineNum = 412;BA.debugLine="sRetVal = sSpace";
_sretval = _sspace;
 } 
       catch (Exception e11) {
			processBA.setLastException(e11); //BA.debugLineNum = 414;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("144564492",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 416;BA.debugLine="Return sRetVal";
if (true) return _sretval;
 //BA.debugLineNum = 417;BA.debugLine="End Sub";
return "";
}
public static String  _addwhitespaces2(String _svalue) throws Exception{
String _sretval = "";
int _ilen = 0;
String _sspace = "";
int _addspace = 0;
 //BA.debugLineNum = 419;BA.debugLine="Private Sub AddWhiteSpaces2(sValue As String) As S";
 //BA.debugLineNum = 420;BA.debugLine="Dim sRetVal As String";
_sretval = "";
 //BA.debugLineNum = 421;BA.debugLine="Dim iLen As Int";
_ilen = 0;
 //BA.debugLineNum = 422;BA.debugLine="Dim sSpace As String";
_sspace = "";
 //BA.debugLineNum = 423;BA.debugLine="Dim AddSpace As Int";
_addspace = 0;
 //BA.debugLineNum = 424;BA.debugLine="Try";
try { //BA.debugLineNum = 426;BA.debugLine="iLen = sValue.Length";
_ilen = _svalue.length();
 //BA.debugLineNum = 427;BA.debugLine="AddSpace = 32 - (18 + iLen)";
_addspace = (int) (32-(18+_ilen));
 //BA.debugLineNum = 428;BA.debugLine="sSpace = RepeatChar(\" \", AddSpace)";
_sspace = _repeatchar(" ",_addspace);
 //BA.debugLineNum = 429;BA.debugLine="sRetVal = sSpace";
_sretval = _sspace;
 } 
       catch (Exception e11) {
			processBA.setLastException(e11); //BA.debugLineNum = 431;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("144630028",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 433;BA.debugLine="Return sRetVal";
if (true) return _sretval;
 //BA.debugLineNum = 434;BA.debugLine="End Sub";
return "";
}
public static String  _btnclose_click() throws Exception{
 //BA.debugLineNum = 741;BA.debugLine="Sub btnClose_Click";
 //BA.debugLineNum = 742;BA.debugLine="ToolBar_NavigationItemClick";
_toolbar_navigationitemclick();
 //BA.debugLineNum = 743;BA.debugLine="End Sub";
return "";
}
public static String  _btnprint_click() throws Exception{
 //BA.debugLineNum = 737;BA.debugLine="Sub btnPrint_Click";
 //BA.debugLineNum = 738;BA.debugLine="PrintBillData(GlobalVar.BillID)";
_printbilldata((int) (mostCurrent._globalvar._billid /*double*/ ));
 //BA.debugLineNum = 739;BA.debugLine="End Sub";
return "";
}
public static String  _cleardata() throws Exception{
 //BA.debugLineNum = 685;BA.debugLine="Private Sub ClearData";
 //BA.debugLineNum = 686;BA.debugLine="lblAcctNo.Text = \"\"";
mostCurrent._lblacctno.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 687;BA.debugLine="lblAcctName.Text = \"\"";
mostCurrent._lblacctname.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 688;BA.debugLine="lblAddress.Text = \"\"";
mostCurrent._lbladdress.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 689;BA.debugLine="lblMeterNo.Text = \"\"";
mostCurrent._lblmeterno.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 690;BA.debugLine="lblBookSeq.Text = \"\"";
mostCurrent._lblbookseq.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 691;BA.debugLine="lblClass.Text = \"\"";
mostCurrent._lblclass.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 692;BA.debugLine="lblGD.Text = \"\"";
mostCurrent._lblgd.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 694;BA.debugLine="lblPresRdg.Text = \"\"";
mostCurrent._lblpresrdg.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 695;BA.debugLine="lblPrevRdg.Text = \"\"";
mostCurrent._lblprevrdg.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 696;BA.debugLine="lblAddCons.Text = \"\"";
mostCurrent._lbladdcons.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 697;BA.debugLine="lblTotalCons.Text = \"\"";
mostCurrent._lbltotalcons.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 699;BA.debugLine="lblBasic.Text = \"\"";
mostCurrent._lblbasic.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 700;BA.debugLine="lblPCA.Text = \"\"";
mostCurrent._lblpca.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 701;BA.debugLine="lblCurrent.Text = \"\"";
mostCurrent._lblcurrent.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 702;BA.debugLine="lblSCDisc10.Text = \"\"";
mostCurrent._lblscdisc10.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 703;BA.debugLine="lblOthers.Text = \"\"";
mostCurrent._lblothers.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 704;BA.debugLine="lblArrears.Text = \"\"";
mostCurrent._lblarrears.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 705;BA.debugLine="lblAdvances.Text = \"\"";
mostCurrent._lbladvances.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 706;BA.debugLine="lblTotDue.Text = \"\"";
mostCurrent._lbltotdue.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 708;BA.debugLine="lblDueDate.Text = \"\"";
mostCurrent._lblduedate.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 709;BA.debugLine="lblPenalty.Text = \"\"";
mostCurrent._lblpenalty.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 710;BA.debugLine="lblSCDisc5.Text = \"\"";
mostCurrent._lblscdisc5.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 711;BA.debugLine="lblAfterDueAmt.Text = \"\"";
mostCurrent._lblafterdueamt.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 712;BA.debugLine="End Sub";
return "";
}
public static boolean  _confirmwarning(String _smsg,String _stitle,String _spos,String _sneg,String _sneu) throws Exception{
boolean _bretval = false;
Object _response = null;
anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper _icon = null;
anywheresoftware.b4a.objects.CSBuilder _cstitle = null;
anywheresoftware.b4a.objects.CSBuilder _csmsg = null;
 //BA.debugLineNum = 745;BA.debugLine="Private Sub ConfirmWarning(sMsg As String, sTitle";
 //BA.debugLineNum = 746;BA.debugLine="Dim bRetVal As Boolean";
_bretval = false;
 //BA.debugLineNum = 747;BA.debugLine="Dim Response As Object";
_response = new Object();
 //BA.debugLineNum = 748;BA.debugLine="Dim icon As B4XBitmap";
_icon = new anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper();
 //BA.debugLineNum = 749;BA.debugLine="Dim csTitle, csMsg As CSBuilder";
_cstitle = new anywheresoftware.b4a.objects.CSBuilder();
_csmsg = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 751;BA.debugLine="csTitle.Initialize.Color(Colors.Red).Append(sTitl";
_cstitle.Initialize().Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(_stitle)).PopAll();
 //BA.debugLineNum = 753;BA.debugLine="csMsg.Initialize.Bold.Color(Colors.Red).Append($\"";
_csmsg.Initialize().Bold().Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(("ACCESS DENIED!"))).Pop().Pop();
 //BA.debugLineNum = 754;BA.debugLine="csMsg.Color(Colors.DarkGray).Append(CRLF & sMsg).";
_csmsg.Color(anywheresoftware.b4a.keywords.Common.Colors.DarkGray).Append(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.CRLF+_smsg)).PopAll();
 //BA.debugLineNum = 756;BA.debugLine="icon = xui.LoadBitmapResize(File.DirAssets, \"erro";
_icon = _xui.LoadBitmapResize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"error.png",anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (24)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (24)),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 758;BA.debugLine="Response = xui.Msgbox2Async(csMsg, csTitle, sPos,";
_response = _xui.Msgbox2Async(processBA,BA.ObjectToCharSequence(_csmsg.getObject()),BA.ObjectToCharSequence(_cstitle.getObject()),_spos,_sneu,_sneg,(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(_icon.getObject())));
 //BA.debugLineNum = 759;BA.debugLine="If Response = xui.DialogResponse_Positive Then";
if ((_response).equals((Object)(_xui.DialogResponse_Positive))) { 
 //BA.debugLineNum = 760;BA.debugLine="bRetVal = True";
_bretval = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 762;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 765;BA.debugLine="Return bRetVal";
if (true) return _bretval;
 //BA.debugLineNum = 767;BA.debugLine="End Sub";
return false;
}
public static anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper  _createscaledbitmap(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _original,int _newwidth,int _newheight) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _b = null;
 //BA.debugLineNum = 543;BA.debugLine="Sub CreateScaledBitmap(Original As Bitmap, NewWidt";
 //BA.debugLineNum = 544;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 545;BA.debugLine="Dim b As Bitmap";
_b = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 546;BA.debugLine="b = r.RunStaticMethod(\"android.graphics.Bitmap\",";
_b = (anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(_r.RunStaticMethod("android.graphics.Bitmap","createScaledBitmap",new Object[]{(Object)(_original.getObject()),(Object)(_newwidth),(Object)(_newheight),(Object)(anywheresoftware.b4a.keywords.Common.True)},new String[]{"android.graphics.Bitmap","java.lang.int","java.lang.int","java.lang.boolean"})));
 //BA.debugLineNum = 547;BA.debugLine="Return b";
if (true) return _b;
 //BA.debugLineNum = 548;BA.debugLine="End Sub";
return null;
}
public static String  _dispinfomsg(String _smsg,String _stitle) throws Exception{
anywheresoftware.b4a.objects.CSBuilder _cstitle = null;
anywheresoftware.b4a.objects.CSBuilder _csmsg = null;
 //BA.debugLineNum = 769;BA.debugLine="Private Sub DispInfoMsg(sMsg As String, sTitle As";
 //BA.debugLineNum = 770;BA.debugLine="Dim csTitle, csMsg As CSBuilder";
_cstitle = new anywheresoftware.b4a.objects.CSBuilder();
_csmsg = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 771;BA.debugLine="csTitle.Initialize.Color(Colors.Red).Append(sTitl";
_cstitle.Initialize().Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(_stitle)).PopAll();
 //BA.debugLineNum = 772;BA.debugLine="csMsg.Initialize.Bold.Color(Colors.DarkGray).Appe";
_csmsg.Initialize().Bold().Color(anywheresoftware.b4a.keywords.Common.Colors.DarkGray).Append(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.CRLF+_smsg)).PopAll();
 //BA.debugLineNum = 774;BA.debugLine="Msgbox(csMsg, csTitle)";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence(_csmsg.getObject()),BA.ObjectToCharSequence(_cstitle.getObject()),mostCurrent.activityBA);
 //BA.debugLineNum = 775;BA.debugLine="End Sub";
return "";
}
public static String  _displayrecords() throws Exception{
 //BA.debugLineNum = 651;BA.debugLine="Private Sub DisplayRecords";
 //BA.debugLineNum = 652;BA.debugLine="lblAcctNo.Text = AcctNo";
mostCurrent._lblacctno.setText(BA.ObjectToCharSequence(mostCurrent._acctno));
 //BA.debugLineNum = 653;BA.debugLine="lblAcctName.Text = AcctName";
mostCurrent._lblacctname.setText(BA.ObjectToCharSequence(mostCurrent._acctname));
 //BA.debugLineNum = 654;BA.debugLine="lblAddress.Text = TitleCase(AcctAddress)";
mostCurrent._lbladdress.setText(BA.ObjectToCharSequence(_titlecase(mostCurrent._acctaddress)));
 //BA.debugLineNum = 655;BA.debugLine="lblMeterNo.Text = MeterNo";
mostCurrent._lblmeterno.setText(BA.ObjectToCharSequence(mostCurrent._meterno));
 //BA.debugLineNum = 656;BA.debugLine="lblBookSeq.Text = BookSeq";
mostCurrent._lblbookseq.setText(BA.ObjectToCharSequence(mostCurrent._bookseq));
 //BA.debugLineNum = 657;BA.debugLine="lblClass.Text = CustClass";
mostCurrent._lblclass.setText(BA.ObjectToCharSequence(mostCurrent._custclass));
 //BA.debugLineNum = 658;BA.debugLine="lblGD.Text = NumberFormat2(sGDeposit,1,2,2,True)";
mostCurrent._lblgd.setText(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(mostCurrent._sgdeposit)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)));
 //BA.debugLineNum = 660;BA.debugLine="lblPresRdg.Text = sPresRdg";
mostCurrent._lblpresrdg.setText(BA.ObjectToCharSequence(mostCurrent._spresrdg));
 //BA.debugLineNum = 661;BA.debugLine="lblPrevRdg.Text = sPrevRdg";
mostCurrent._lblprevrdg.setText(BA.ObjectToCharSequence(mostCurrent._sprevrdg));
 //BA.debugLineNum = 662;BA.debugLine="lblAddCons.Text = sAddCons";
mostCurrent._lbladdcons.setText(BA.ObjectToCharSequence(mostCurrent._saddcons));
 //BA.debugLineNum = 663;BA.debugLine="lblTotalCons.Text = sTotalCum";
mostCurrent._lbltotalcons.setText(BA.ObjectToCharSequence(mostCurrent._stotalcum));
 //BA.debugLineNum = 665;BA.debugLine="lblBasic.Text = NumberFormat2(sBasicAmt,1, 2, 2,T";
mostCurrent._lblbasic.setText(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(mostCurrent._sbasicamt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)));
 //BA.debugLineNum = 666;BA.debugLine="lblPCA.Text = NumberFormat2(sPCAAmt,1, 2, 2,True)";
mostCurrent._lblpca.setText(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(mostCurrent._spcaamt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)));
 //BA.debugLineNum = 667;BA.debugLine="lblCurrent.Text = NumberFormat2(sCurrentAmt,1, 2,";
mostCurrent._lblcurrent.setText(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(mostCurrent._scurrentamt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)));
 //BA.debugLineNum = 668;BA.debugLine="lblSCDisc10.Text = \"(\" & NumberFormat2(sSeniorOnB";
mostCurrent._lblscdisc10.setText(BA.ObjectToCharSequence("("+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(mostCurrent._ssenioronbeforeamt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")"));
 //BA.debugLineNum = 669;BA.debugLine="lblOthers.Text = NumberFormat2(sAddToBillAmt,1, 2";
mostCurrent._lblothers.setText(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(mostCurrent._saddtobillamt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)));
 //BA.debugLineNum = 670;BA.debugLine="lblArrears.Text = NumberFormat2(sArrearsAmt,1, 2,";
mostCurrent._lblarrears.setText(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(mostCurrent._sarrearsamt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)));
 //BA.debugLineNum = 671;BA.debugLine="lblAdvances.Text = \"(\" & NumberFormat2(sAdvPaymen";
mostCurrent._lbladvances.setText(BA.ObjectToCharSequence("("+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(mostCurrent._sadvpayment)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")"));
 //BA.debugLineNum = 672;BA.debugLine="lblTotDue.Text = NumberFormat2(sTotalDueAmt,1, 2,";
mostCurrent._lbltotdue.setText(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(mostCurrent._stotaldueamt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)));
 //BA.debugLineNum = 674;BA.debugLine="lblDueDate.Text = DueDate";
mostCurrent._lblduedate.setText(BA.ObjectToCharSequence(mostCurrent._duedate));
 //BA.debugLineNum = 675;BA.debugLine="If DueDate = \"N/A\" Then";
if ((mostCurrent._duedate).equals("N/A")) { 
 //BA.debugLineNum = 676;BA.debugLine="lblDueDate.TextColor = Colors.Red";
mostCurrent._lblduedate.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 }else {
 //BA.debugLineNum = 678;BA.debugLine="lblDueDate.TextColor = GlobalVar.PriColor";
mostCurrent._lblduedate.setTextColor((int) (mostCurrent._globalvar._pricolor /*double*/ ));
 };
 //BA.debugLineNum = 680;BA.debugLine="lblPenalty.Text = NumberFormat2(sPenaltyAmt,1, 2,";
mostCurrent._lblpenalty.setText(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(mostCurrent._spenaltyamt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)));
 //BA.debugLineNum = 681;BA.debugLine="lblSCDisc5.Text = \"(\" & NumberFormat2(sSeniorAfte";
mostCurrent._lblscdisc5.setText(BA.ObjectToCharSequence("("+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(mostCurrent._sseniorafteramt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")"));
 //BA.debugLineNum = 682;BA.debugLine="lblAfterDueAmt.Text = NumberFormat2(sTotalDueAmtA";
mostCurrent._lblafterdueamt.setText(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(mostCurrent._stotaldueamtaftersc)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)));
 //BA.debugLineNum = 683;BA.debugLine="End Sub";
return "";
}
public static String  _getbilldetais(double _dbillid) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsbilldetails = null;
 //BA.debugLineNum = 586;BA.debugLine="Private Sub GetBillDetais(dBillID As Double)";
 //BA.debugLineNum = 587;BA.debugLine="Dim rsBillDetails As Cursor";
_rsbilldetails = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 588;BA.debugLine="Try";
try { //BA.debugLineNum = 589;BA.debugLine="sBillPeriod = GlobalVar.BillPeriod";
mostCurrent._sbillperiod = mostCurrent._globalvar._billperiod /*String*/ ;
 //BA.debugLineNum = 591;BA.debugLine="Starter.strCriteria = \"SELECT * FROM tblReadings";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblReadings WHERE ReadID = "+BA.NumberToString(_dbillid);
 //BA.debugLineNum = 592;BA.debugLine="rsBillDetails = Starter.DBCon.ExecQuery(Starter.";
_rsbilldetails = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 593;BA.debugLine="If rsBillDetails.RowCount > 0 Then";
if (_rsbilldetails.getRowCount()>0) { 
 //BA.debugLineNum = 594;BA.debugLine="rsBillDetails.Position = 0";
_rsbilldetails.setPosition((int) (0));
 //BA.debugLineNum = 595;BA.debugLine="AcctNo = rsBillDetails.GetString(\"AcctNo\")";
mostCurrent._acctno = _rsbilldetails.GetString("AcctNo");
 //BA.debugLineNum = 596;BA.debugLine="AcctName = rsBillDetails.GetString(\"AcctName\")";
mostCurrent._acctname = _rsbilldetails.GetString("AcctName");
 //BA.debugLineNum = 597;BA.debugLine="AcctAddress = rsBillDetails.GetString(\"AcctAddr";
mostCurrent._acctaddress = _rsbilldetails.GetString("AcctAddress");
 //BA.debugLineNum = 598;BA.debugLine="MeterNo = rsBillDetails.GetString(\"MeterNo\")";
mostCurrent._meterno = _rsbilldetails.GetString("MeterNo");
 //BA.debugLineNum = 599;BA.debugLine="BookSeq = rsBillDetails.GetString(\"BookNo\") & \"";
mostCurrent._bookseq = _rsbilldetails.GetString("BookNo")+"-"+_rsbilldetails.GetString("SeqNo");
 //BA.debugLineNum = 600;BA.debugLine="CustClass = rsBillDetails.GetString(\"AcctClass\"";
mostCurrent._custclass = _rsbilldetails.GetString("AcctClass")+"-"+_rsbilldetails.GetString("AcctSubClass");
 //BA.debugLineNum = 601;BA.debugLine="GDeposit = Round2(rsBillDetails.GetDouble(\"GDep";
_gdeposit = anywheresoftware.b4a.keywords.Common.Round2(_rsbilldetails.GetDouble("GDeposit"),(int) (2));
 //BA.debugLineNum = 602;BA.debugLine="BillNo = rsBillDetails.GetString(\"BillNo\")";
mostCurrent._billno = _rsbilldetails.GetString("BillNo");
 //BA.debugLineNum = 604;BA.debugLine="PresRdg = rsBillDetails.GetInt(\"PresRdg\")";
mostCurrent._presrdg = BA.NumberToString(_rsbilldetails.GetInt("PresRdg"));
 //BA.debugLineNum = 605;BA.debugLine="PrevRdg = rsBillDetails.GetInt(\"PrevRdg\")";
_prevrdg = _rsbilldetails.GetInt("PrevRdg");
 //BA.debugLineNum = 606;BA.debugLine="AddCons = rsBillDetails.GetInt(\"AddCons\")";
_addcons = _rsbilldetails.GetInt("AddCons");
 //BA.debugLineNum = 607;BA.debugLine="TotalCum = rsBillDetails.GetInt(\"TotalCum\")";
_totalcum = _rsbilldetails.GetInt("TotalCum");
 //BA.debugLineNum = 609;BA.debugLine="BasicAmt =Round2(rsBillDetails.GetDouble(\"Basic";
_basicamt = anywheresoftware.b4a.keywords.Common.Round2(_rsbilldetails.GetDouble("BasicAmt"),(int) (2));
 //BA.debugLineNum = 610;BA.debugLine="PCAAmt = Round2(rsBillDetails.GetDouble(\"PCAAmt";
_pcaamt = anywheresoftware.b4a.keywords.Common.Round2(_rsbilldetails.GetDouble("PCAAmt"),(int) (2));
 //BA.debugLineNum = 611;BA.debugLine="CurrentAmt = Round2(rsBillDetails.GetDouble(\"Cu";
_currentamt = anywheresoftware.b4a.keywords.Common.Round2(_rsbilldetails.GetDouble("CurrentAmt"),(int) (2));
 //BA.debugLineNum = 612;BA.debugLine="SeniorOnBeforeAmt = Round2(rsBillDetails.GetDou";
_senioronbeforeamt = anywheresoftware.b4a.keywords.Common.Round2(_rsbilldetails.GetDouble("SeniorOnBeforeAmt"),(int) (2));
 //BA.debugLineNum = 613;BA.debugLine="AddToBillAmt = Round2(rsBillDetails.GetDouble(\"";
_addtobillamt = anywheresoftware.b4a.keywords.Common.Round2(_rsbilldetails.GetDouble("AddToBillAmt"),(int) (2));
 //BA.debugLineNum = 614;BA.debugLine="ArrearsAmt = Round2(rsBillDetails.GetDouble(\"Ar";
_arrearsamt = anywheresoftware.b4a.keywords.Common.Round2(_rsbilldetails.GetDouble("ArrearsAmt"),(int) (2));
 //BA.debugLineNum = 615;BA.debugLine="AdvPayment = Round2(rsBillDetails.GetDouble(\"Ad";
_advpayment = anywheresoftware.b4a.keywords.Common.Round2(_rsbilldetails.GetDouble("AdvPayment"),(int) (2));
 //BA.debugLineNum = 616;BA.debugLine="TotalDueAmt = Round2(rsBillDetails.GetDouble(\"T";
_totaldueamt = anywheresoftware.b4a.keywords.Common.Round2(_rsbilldetails.GetDouble("TotalDueAmt"),(int) (2));
 //BA.debugLineNum = 617;BA.debugLine="WithDueDate = rsBillDetails.GetInt(\"WithDueDate";
_withduedate = _rsbilldetails.GetInt("WithDueDate");
 //BA.debugLineNum = 618;BA.debugLine="If WithDueDate = 1 Then";
if (_withduedate==1) { 
 //BA.debugLineNum = 619;BA.debugLine="DueDate = rsBillDetails.GetString(\"DueDate\")";
mostCurrent._duedate = _rsbilldetails.GetString("DueDate");
 }else {
 //BA.debugLineNum = 621;BA.debugLine="DueDate = $\"N/A\"$";
mostCurrent._duedate = ("N/A");
 };
 //BA.debugLineNum = 623;BA.debugLine="PenaltyAmt = Round2(rsBillDetails.GetDouble(\"Pe";
_penaltyamt = anywheresoftware.b4a.keywords.Common.Round2(_rsbilldetails.GetDouble("PenaltyAmt"),(int) (2));
 //BA.debugLineNum = 624;BA.debugLine="SeniorAfterAmt = Round2(rsBillDetails.GetDouble";
_seniorafteramt = anywheresoftware.b4a.keywords.Common.Round2(_rsbilldetails.GetDouble("SeniorAfterAmt"),(int) (2));
 //BA.debugLineNum = 625;BA.debugLine="TotalDueAmtAfterSC = Round2(rsBillDetails.GetDo";
_totaldueamtaftersc = anywheresoftware.b4a.keywords.Common.Round2(_rsbilldetails.GetDouble("TotalDueAmtAfterSC"),(int) (2));
 };
 //BA.debugLineNum = 628;BA.debugLine="sPresRdg = PresRdg";
mostCurrent._spresrdg = mostCurrent._presrdg;
 //BA.debugLineNum = 629;BA.debugLine="sPrevRdg = PrevRdg";
mostCurrent._sprevrdg = BA.NumberToString(_prevrdg);
 //BA.debugLineNum = 630;BA.debugLine="sAddCons = AddCons";
mostCurrent._saddcons = BA.NumberToString(_addcons);
 //BA.debugLineNum = 631;BA.debugLine="sTotalCum = TotalCum";
mostCurrent._stotalcum = BA.NumberToString(_totalcum);
 //BA.debugLineNum = 633;BA.debugLine="sGDeposit = GDeposit";
mostCurrent._sgdeposit = BA.NumberToString(_gdeposit);
 //BA.debugLineNum = 634;BA.debugLine="sBasicAmt = BasicAmt";
mostCurrent._sbasicamt = BA.NumberToString(_basicamt);
 //BA.debugLineNum = 635;BA.debugLine="sPCAAmt = PCAAmt";
mostCurrent._spcaamt = BA.NumberToString(_pcaamt);
 //BA.debugLineNum = 636;BA.debugLine="sCurrentAmt = CurrentAmt";
mostCurrent._scurrentamt = BA.NumberToString(_currentamt);
 //BA.debugLineNum = 637;BA.debugLine="sSeniorOnBeforeAmt = SeniorOnBeforeAmt";
mostCurrent._ssenioronbeforeamt = BA.NumberToString(_senioronbeforeamt);
 //BA.debugLineNum = 638;BA.debugLine="sAddToBillAmt = AddToBillAmt'Others";
mostCurrent._saddtobillamt = BA.NumberToString(_addtobillamt);
 //BA.debugLineNum = 639;BA.debugLine="sArrearsAmt = ArrearsAmt";
mostCurrent._sarrearsamt = BA.NumberToString(_arrearsamt);
 //BA.debugLineNum = 640;BA.debugLine="sAdvPayment = AdvPayment";
mostCurrent._sadvpayment = BA.NumberToString(_advpayment);
 //BA.debugLineNum = 641;BA.debugLine="sTotalDueAmt = TotalDueAmt";
mostCurrent._stotaldueamt = BA.NumberToString(_totaldueamt);
 //BA.debugLineNum = 642;BA.debugLine="sPenaltyAmt = PenaltyAmt";
mostCurrent._spenaltyamt = BA.NumberToString(_penaltyamt);
 //BA.debugLineNum = 643;BA.debugLine="sSeniorAfterAmt = SeniorAfterAmt";
mostCurrent._sseniorafteramt = BA.NumberToString(_seniorafteramt);
 //BA.debugLineNum = 644;BA.debugLine="sTotalDueAmtAfterSC = TotalDueAmtAfterSC";
mostCurrent._stotaldueamtaftersc = BA.NumberToString(_totaldueamtaftersc);
 } 
       catch (Exception e55) {
			processBA.setLastException(e55); //BA.debugLineNum = 646;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("145088828",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 648;BA.debugLine="rsBillDetails.Close";
_rsbilldetails.Close();
 //BA.debugLineNum = 649;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 46;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 48;BA.debugLine="Private ToolBar As ACToolBarDark";
mostCurrent._toolbar = new de.amberhome.objects.appcompat.ACToolbarDarkWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Private ActionBarButton As ACActionBar";
mostCurrent._actionbarbutton = new de.amberhome.objects.appcompat.ACActionBar();
 //BA.debugLineNum = 50;BA.debugLine="Private xmlIcon As XmlLayoutBuilder";
mostCurrent._xmlicon = new anywheresoftware.b4a.object.XmlLayoutBuilder();
 //BA.debugLineNum = 52;BA.debugLine="Private snack As DSSnackbar";
mostCurrent._snack = new de.amberhome.objects.SnackbarWrapper();
 //BA.debugLineNum = 53;BA.debugLine="Dim CD As ColorDrawable";
mostCurrent._cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 56;BA.debugLine="Private MatDialogBuilder As MaterialDialogBuilder";
mostCurrent._matdialogbuilder = new de.amberhome.materialdialogs.MaterialDialogBuilderWrapper();
 //BA.debugLineNum = 59;BA.debugLine="Private rsLoadedBook As Cursor";
mostCurrent._rsloadedbook = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 61;BA.debugLine="Private rsUnReadAcct As Cursor";
mostCurrent._rsunreadacct = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 62;BA.debugLine="Private rsReadAcct As Cursor";
mostCurrent._rsreadacct = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 63;BA.debugLine="Private rsAllAcct As Cursor";
mostCurrent._rsallacct = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 64;BA.debugLine="Private rsUnusualReading As Cursor";
mostCurrent._rsunusualreading = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 68;BA.debugLine="Private BillNo As String";
mostCurrent._billno = "";
 //BA.debugLineNum = 69;BA.debugLine="Private AcctNo As String";
mostCurrent._acctno = "";
 //BA.debugLineNum = 70;BA.debugLine="Private AcctName As String";
mostCurrent._acctname = "";
 //BA.debugLineNum = 71;BA.debugLine="Private AcctAddress As String";
mostCurrent._acctaddress = "";
 //BA.debugLineNum = 72;BA.debugLine="Private MeterNo As String";
mostCurrent._meterno = "";
 //BA.debugLineNum = 73;BA.debugLine="Private GDeposit As Double";
_gdeposit = 0;
 //BA.debugLineNum = 75;BA.debugLine="Private PrevRdg As Double";
_prevrdg = 0;
 //BA.debugLineNum = 76;BA.debugLine="Private PresRdg As String";
mostCurrent._presrdg = "";
 //BA.debugLineNum = 78;BA.debugLine="Private DueDate As String";
mostCurrent._duedate = "";
 //BA.debugLineNum = 79;BA.debugLine="Private AddCons As Double";
_addcons = 0;
 //BA.debugLineNum = 80;BA.debugLine="Private TotalCum As Double";
_totalcum = 0;
 //BA.debugLineNum = 81;BA.debugLine="Private BasicAmt As Double";
_basicamt = 0;
 //BA.debugLineNum = 83;BA.debugLine="Private PCAAmt As Double";
_pcaamt = 0;
 //BA.debugLineNum = 84;BA.debugLine="Private AddToBillAmt As Double";
_addtobillamt = 0;
 //BA.debugLineNum = 85;BA.debugLine="Private AdvPayment As Double";
_advpayment = 0;
 //BA.debugLineNum = 86;BA.debugLine="Private PenaltyAmt As Double";
_penaltyamt = 0;
 //BA.debugLineNum = 87;BA.debugLine="Private TotalDueAmtAfterSC As Double";
_totaldueamtaftersc = 0;
 //BA.debugLineNum = 90;BA.debugLine="Private CurrentAmt As Double";
_currentamt = 0;
 //BA.debugLineNum = 91;BA.debugLine="Private ArrearsAmt As Double";
_arrearsamt = 0;
 //BA.debugLineNum = 92;BA.debugLine="Private WithDueDate As Int";
_withduedate = 0;
 //BA.debugLineNum = 93;BA.debugLine="Private TotalDueAmt As Double";
_totaldueamt = 0;
 //BA.debugLineNum = 94;BA.debugLine="Private SeniorOnBeforeAmt As Double";
_senioronbeforeamt = 0;
 //BA.debugLineNum = 95;BA.debugLine="Private SeniorAfterAmt As Double";
_seniorafteramt = 0;
 //BA.debugLineNum = 96;BA.debugLine="Private TotalDueAmtBeforeSC As Double";
_totaldueamtbeforesc = 0;
 //BA.debugLineNum = 100;BA.debugLine="Private BookSeq As String";
mostCurrent._bookseq = "";
 //BA.debugLineNum = 101;BA.debugLine="Private CustClass As String";
mostCurrent._custclass = "";
 //BA.debugLineNum = 102;BA.debugLine="Private sBranchName As String";
mostCurrent._sbranchname = "";
 //BA.debugLineNum = 103;BA.debugLine="Private sBranchAddress As String";
mostCurrent._sbranchaddress = "";
 //BA.debugLineNum = 104;BA.debugLine="Private sBranchContactNo As String";
mostCurrent._sbranchcontactno = "";
 //BA.debugLineNum = 105;BA.debugLine="Private sTinNo As String";
mostCurrent._stinno = "";
 //BA.debugLineNum = 106;BA.debugLine="Private sBranchCode As String";
mostCurrent._sbranchcode = "";
 //BA.debugLineNum = 107;BA.debugLine="Private sBillPeriod As String";
mostCurrent._sbillperiod = "";
 //BA.debugLineNum = 110;BA.debugLine="Private BookID As Int";
_bookid = 0;
 //BA.debugLineNum = 111;BA.debugLine="Private BillDate As String";
mostCurrent._billdate = "";
 //BA.debugLineNum = 112;BA.debugLine="Private DateFrom As String";
mostCurrent._datefrom = "";
 //BA.debugLineNum = 113;BA.debugLine="Private DateTo As String";
mostCurrent._dateto = "";
 //BA.debugLineNum = 115;BA.debugLine="Private sPresRdg As String";
mostCurrent._spresrdg = "";
 //BA.debugLineNum = 116;BA.debugLine="Private sPrevRdg As String";
mostCurrent._sprevrdg = "";
 //BA.debugLineNum = 117;BA.debugLine="Private sAddCons As String";
mostCurrent._saddcons = "";
 //BA.debugLineNum = 118;BA.debugLine="Private sTotalCum As String";
mostCurrent._stotalcum = "";
 //BA.debugLineNum = 120;BA.debugLine="Private sGDeposit As String";
mostCurrent._sgdeposit = "";
 //BA.debugLineNum = 121;BA.debugLine="Private sBasicAmt As String";
mostCurrent._sbasicamt = "";
 //BA.debugLineNum = 122;BA.debugLine="Private sPCAAmt As String";
mostCurrent._spcaamt = "";
 //BA.debugLineNum = 123;BA.debugLine="Private sCurrentAmt As String";
mostCurrent._scurrentamt = "";
 //BA.debugLineNum = 124;BA.debugLine="Private sSeniorOnBeforeAmt As String";
mostCurrent._ssenioronbeforeamt = "";
 //BA.debugLineNum = 125;BA.debugLine="Private sAddToBillAmt As String";
mostCurrent._saddtobillamt = "";
 //BA.debugLineNum = 126;BA.debugLine="Private sArrearsAmt As String";
mostCurrent._sarrearsamt = "";
 //BA.debugLineNum = 127;BA.debugLine="Private sAdvPayment As String";
mostCurrent._sadvpayment = "";
 //BA.debugLineNum = 128;BA.debugLine="Private sTotalDueAmt As String";
mostCurrent._stotaldueamt = "";
 //BA.debugLineNum = 129;BA.debugLine="Private sPenaltyAmt As String";
mostCurrent._spenaltyamt = "";
 //BA.debugLineNum = 130;BA.debugLine="Private sSeniorAfterAmt As String";
mostCurrent._sseniorafteramt = "";
 //BA.debugLineNum = 131;BA.debugLine="Private sTotalDueAmtAfterSC As String";
mostCurrent._stotaldueamtaftersc = "";
 //BA.debugLineNum = 133;BA.debugLine="Private vibration As B4Avibrate";
mostCurrent._vibration = new com.johan.Vibrate.Vibrate();
 //BA.debugLineNum = 134;BA.debugLine="Private vibratePattern() As Long";
_vibratepattern = new long[(int) (0)];
;
 //BA.debugLineNum = 135;BA.debugLine="Private btnPrint As ACButton";
mostCurrent._btnprint = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 137;BA.debugLine="Private lblAcctNo As Label";
mostCurrent._lblacctno = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 138;BA.debugLine="Private lblAcctName As Label";
mostCurrent._lblacctname = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 139;BA.debugLine="Private lblAddress As Label";
mostCurrent._lbladdress = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 140;BA.debugLine="Private lblMeterNo As Label";
mostCurrent._lblmeterno = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 141;BA.debugLine="Private lblBookSeq As Label";
mostCurrent._lblbookseq = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 142;BA.debugLine="Private lblClass As Label";
mostCurrent._lblclass = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 143;BA.debugLine="Private lblGD As Label";
mostCurrent._lblgd = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 145;BA.debugLine="Private lblPresRdg As Label";
mostCurrent._lblpresrdg = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 146;BA.debugLine="Private lblPrevRdg As Label";
mostCurrent._lblprevrdg = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 147;BA.debugLine="Private lblAddCons As Label";
mostCurrent._lbladdcons = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 148;BA.debugLine="Private lblTotalCons As Label";
mostCurrent._lbltotalcons = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 150;BA.debugLine="Private lblBasic As Label";
mostCurrent._lblbasic = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 151;BA.debugLine="Private lblPCA As Label";
mostCurrent._lblpca = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 152;BA.debugLine="Private lblCurrent As Label";
mostCurrent._lblcurrent = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 153;BA.debugLine="Private lblSCDisc10 As Label";
mostCurrent._lblscdisc10 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 154;BA.debugLine="Private lblOthers As Label";
mostCurrent._lblothers = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 155;BA.debugLine="Private lblArrears As Label";
mostCurrent._lblarrears = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 156;BA.debugLine="Private lblAdvances As Label";
mostCurrent._lbladvances = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 157;BA.debugLine="Private lblTotDue As Label";
mostCurrent._lbltotdue = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 159;BA.debugLine="Private lblDueDate As Label";
mostCurrent._lblduedate = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 160;BA.debugLine="Private lblPenalty As Label";
mostCurrent._lblpenalty = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 161;BA.debugLine="Private lblSCDisc5 As Label";
mostCurrent._lblscdisc5 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 162;BA.debugLine="Private lblAfterDueAmt As Label";
mostCurrent._lblafterdueamt = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 163;BA.debugLine="Private btnClose As ACButton";
mostCurrent._btnclose = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 164;BA.debugLine="End Sub";
return "";
}
public static boolean  _isprintabledata(int _ireadid) throws Exception{
boolean _blnretval = false;
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsvaliddata = null;
 //BA.debugLineNum = 564;BA.debugLine="Private Sub IsPrintableData(iReadID As Int) As Boo";
 //BA.debugLineNum = 565;BA.debugLine="Dim blnRetVal As Boolean";
_blnretval = false;
 //BA.debugLineNum = 566;BA.debugLine="Dim rsValidData As Cursor";
_rsvaliddata = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 567;BA.debugLine="Try";
try { //BA.debugLineNum = 568;BA.debugLine="Starter.strCriteria = \"SELECT * FROM tblReadings";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblReadings WHERE ReadID = "+BA.NumberToString(_ireadid)+" "+"AND WasRead = 1 "+"AND WasMissCoded = 0";
 //BA.debugLineNum = 571;BA.debugLine="rsValidData = Starter.DBCon.ExecQuery(Starter.st";
_rsvaliddata = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 572;BA.debugLine="If rsValidData.RowCount > 0 Then";
if (_rsvaliddata.getRowCount()>0) { 
 //BA.debugLineNum = 573;BA.debugLine="blnRetVal = True";
_blnretval = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 575;BA.debugLine="blnRetVal = False";
_blnretval = anywheresoftware.b4a.keywords.Common.False;
 };
 } 
       catch (Exception e12) {
			processBA.setLastException(e12); //BA.debugLineNum = 578;BA.debugLine="blnRetVal = True";
_blnretval = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 579;BA.debugLine="rsValidData.Close";
_rsvaliddata.Close();
 //BA.debugLineNum = 580;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("145023248",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 582;BA.debugLine="rsValidData.Close";
_rsvaliddata.Close();
 //BA.debugLineNum = 583;BA.debugLine="Return blnRetVal";
if (true) return _blnretval;
 //BA.debugLineNum = 584;BA.debugLine="End Sub";
return false;
}
public static String  _logoprint_error() throws Exception{
 //BA.debugLineNum = 557;BA.debugLine="Sub LogoPrint_Error";
 //BA.debugLineNum = 560;BA.debugLine="End Sub";
return "";
}
public static String  _logoprint_newdata(byte[] _buffer) throws Exception{
String _msg = "";
 //BA.debugLineNum = 550;BA.debugLine="Sub LogoPrint_NewData (Buffer() As Byte)";
 //BA.debugLineNum = 551;BA.debugLine="Dim msg As String";
_msg = "";
 //BA.debugLineNum = 552;BA.debugLine="msg = BytesToString(Buffer, 0, Buffer.Length, \"UT";
_msg = anywheresoftware.b4a.keywords.Common.BytesToString(_buffer,(int) (0),_buffer.length,"UTF8");
 //BA.debugLineNum = 554;BA.debugLine="Log(msg)";
anywheresoftware.b4a.keywords.Common.LogImpl("144892164",_msg,0);
 //BA.debugLineNum = 555;BA.debugLine="End Sub";
return "";
}
public static String  _printbilldata(int _ireadid) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsdata = null;
 //BA.debugLineNum = 242;BA.debugLine="Private Sub PrintBillData(iReadID As Int)";
 //BA.debugLineNum = 243;BA.debugLine="Dim rsData As Cursor";
_rsdata = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 245;BA.debugLine="ProgressDialogShow2($\"Bill Statement Printing...\"";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence(("Bill Statement Printing...")),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 247;BA.debugLine="Try";
try { //BA.debugLineNum = 248;BA.debugLine="Starter.strCriteria = \"SELECT * FROM tblReadings";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblReadings WHERE ReadID = "+BA.NumberToString(_ireadid);
 //BA.debugLineNum = 249;BA.debugLine="rsData = Starter.DBCon.ExecQuery(Starter.strCrit";
_rsdata = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 251;BA.debugLine="If rsData.RowCount > 0 Then";
if (_rsdata.getRowCount()>0) { 
 //BA.debugLineNum = 252;BA.debugLine="rsData.Position = 0";
_rsdata.setPosition((int) (0));
 //BA.debugLineNum = 253;BA.debugLine="AcctNo = rsData.GetString(\"AcctNo\")";
mostCurrent._acctno = _rsdata.GetString("AcctNo");
 //BA.debugLineNum = 254;BA.debugLine="AcctName = rsData.GetString(\"AcctName\")";
mostCurrent._acctname = _rsdata.GetString("AcctName");
 //BA.debugLineNum = 255;BA.debugLine="AcctAddress = rsData.GetString(\"AcctAddress\")";
mostCurrent._acctaddress = _rsdata.GetString("AcctAddress");
 //BA.debugLineNum = 256;BA.debugLine="MeterNo = rsData.GetString(\"MeterNo\")";
mostCurrent._meterno = _rsdata.GetString("MeterNo");
 //BA.debugLineNum = 257;BA.debugLine="BookID = rsData.GetInt(\"BookID\")";
_bookid = _rsdata.GetInt("BookID");
 //BA.debugLineNum = 258;BA.debugLine="BookSeq = rsData.GetString(\"BookNo\") & \"-\" & rs";
mostCurrent._bookseq = _rsdata.GetString("BookNo")+"-"+_rsdata.GetString("SeqNo");
 //BA.debugLineNum = 259;BA.debugLine="CustClass = rsData.GetString(\"AcctClass\") & \"-\"";
mostCurrent._custclass = _rsdata.GetString("AcctClass")+"-"+_rsdata.GetString("AcctSubClass");
 //BA.debugLineNum = 260;BA.debugLine="GDeposit = Round2(rsData.GetDouble(\"GDeposit\"),";
_gdeposit = anywheresoftware.b4a.keywords.Common.Round2(_rsdata.GetDouble("GDeposit"),(int) (2));
 //BA.debugLineNum = 261;BA.debugLine="BillNo = rsData.GetString(\"BillNo\")";
mostCurrent._billno = _rsdata.GetString("BillNo");
 //BA.debugLineNum = 262;BA.debugLine="BillDate = rsData.GetString(\"DateRead\")";
mostCurrent._billdate = _rsdata.GetString("DateRead");
 //BA.debugLineNum = 263;BA.debugLine="DateFrom = rsData.GetString(\"DateFrom\")";
mostCurrent._datefrom = _rsdata.GetString("DateFrom");
 //BA.debugLineNum = 264;BA.debugLine="DateTo = rsData.GetString(\"DateRead\")";
mostCurrent._dateto = _rsdata.GetString("DateRead");
 //BA.debugLineNum = 266;BA.debugLine="PresRdg = rsData.GetInt(\"PresRdg\")";
mostCurrent._presrdg = BA.NumberToString(_rsdata.GetInt("PresRdg"));
 //BA.debugLineNum = 267;BA.debugLine="PrevRdg = rsData.GetInt(\"PrevRdg\")";
_prevrdg = _rsdata.GetInt("PrevRdg");
 //BA.debugLineNum = 268;BA.debugLine="AddCons = rsData.GetInt(\"AddCons\")";
_addcons = _rsdata.GetInt("AddCons");
 //BA.debugLineNum = 269;BA.debugLine="TotalCum = rsData.GetInt(\"TotalCum\")";
_totalcum = _rsdata.GetInt("TotalCum");
 //BA.debugLineNum = 271;BA.debugLine="BasicAmt =Round2(rsData.GetDouble(\"BasicAmt\"),2";
_basicamt = anywheresoftware.b4a.keywords.Common.Round2(_rsdata.GetDouble("BasicAmt"),(int) (2));
 //BA.debugLineNum = 272;BA.debugLine="PCAAmt = Round2(rsData.GetDouble(\"PCAAmt\"),2)";
_pcaamt = anywheresoftware.b4a.keywords.Common.Round2(_rsdata.GetDouble("PCAAmt"),(int) (2));
 //BA.debugLineNum = 273;BA.debugLine="CurrentAmt = Round2(rsData.GetDouble(\"CurrentAm";
_currentamt = anywheresoftware.b4a.keywords.Common.Round2(_rsdata.GetDouble("CurrentAmt"),(int) (2));
 //BA.debugLineNum = 274;BA.debugLine="SeniorOnBeforeAmt = Round2(rsData.GetDouble(\"Se";
_senioronbeforeamt = anywheresoftware.b4a.keywords.Common.Round2(_rsdata.GetDouble("SeniorOnBeforeAmt"),(int) (2));
 //BA.debugLineNum = 275;BA.debugLine="AddToBillAmt = Round2(rsData.GetDouble(\"AddToBi";
_addtobillamt = anywheresoftware.b4a.keywords.Common.Round2(_rsdata.GetDouble("AddToBillAmt"),(int) (2));
 //BA.debugLineNum = 276;BA.debugLine="ArrearsAmt = Round2(rsData.GetDouble(\"ArrearsAm";
_arrearsamt = anywheresoftware.b4a.keywords.Common.Round2(_rsdata.GetDouble("ArrearsAmt"),(int) (2));
 //BA.debugLineNum = 277;BA.debugLine="AdvPayment = Round2(rsData.GetDouble(\"AdvPaymen";
_advpayment = anywheresoftware.b4a.keywords.Common.Round2(_rsdata.GetDouble("AdvPayment"),(int) (2));
 //BA.debugLineNum = 278;BA.debugLine="TotalDueAmt = Round2(rsData.GetDouble(\"TotalDue";
_totaldueamt = anywheresoftware.b4a.keywords.Common.Round2(_rsdata.GetDouble("TotalDueAmt"),(int) (2));
 //BA.debugLineNum = 279;BA.debugLine="WithDueDate = rsData.GetInt(\"WithDueDate\")";
_withduedate = _rsdata.GetInt("WithDueDate");
 //BA.debugLineNum = 280;BA.debugLine="If WithDueDate = 1 Then";
if (_withduedate==1) { 
 //BA.debugLineNum = 281;BA.debugLine="DueDate = rsData.GetString(\"DueDate\")";
mostCurrent._duedate = _rsdata.GetString("DueDate");
 }else {
 //BA.debugLineNum = 283;BA.debugLine="DueDate = \"\"";
mostCurrent._duedate = "";
 };
 //BA.debugLineNum = 285;BA.debugLine="PenaltyAmt = Round2(rsData.GetDouble(\"PenaltyAm";
_penaltyamt = anywheresoftware.b4a.keywords.Common.Round2(_rsdata.GetDouble("PenaltyAmt"),(int) (2));
 //BA.debugLineNum = 286;BA.debugLine="SeniorAfterAmt = Round2(rsData.GetDouble(\"Senio";
_seniorafteramt = anywheresoftware.b4a.keywords.Common.Round2(_rsdata.GetDouble("SeniorAfterAmt"),(int) (2));
 //BA.debugLineNum = 287;BA.debugLine="TotalDueAmtAfterSC = Round2(rsData.GetDouble(\"T";
_totaldueamtaftersc = anywheresoftware.b4a.keywords.Common.Round2(_rsdata.GetDouble("TotalDueAmtAfterSC"),(int) (2));
 };
 //BA.debugLineNum = 290;BA.debugLine="sBranchName = GlobalVar.SF.Right(GlobalVar.Branc";
mostCurrent._sbranchname = mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvv7(mostCurrent._globalvar._branchname /*String*/ ,(long) (mostCurrent._globalvar._branchname /*String*/ .length()-7));
 //BA.debugLineNum = 291;BA.debugLine="If GlobalVar.BranchID = 28 Or GlobalVar.BranchID";
if (mostCurrent._globalvar._branchid /*int*/ ==28 || mostCurrent._globalvar._branchid /*int*/ ==29 || mostCurrent._globalvar._branchid /*int*/ ==30) { 
 //BA.debugLineNum = 292;BA.debugLine="sBranchAddress = GlobalVar.BranchAddress & CRLF";
mostCurrent._sbranchaddress = mostCurrent._globalvar._branchaddress /*String*/ +anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"Guiguinto, Bulacan";
 }else {
 //BA.debugLineNum = 294;BA.debugLine="sBranchAddress = GlobalVar.BranchAddress";
mostCurrent._sbranchaddress = mostCurrent._globalvar._branchaddress /*String*/ ;
 };
 //BA.debugLineNum = 296;BA.debugLine="sBranchContactNo = GlobalVar.BranchContactNo";
mostCurrent._sbranchcontactno = mostCurrent._globalvar._branchcontactno /*String*/ ;
 //BA.debugLineNum = 297;BA.debugLine="sTinNo = GlobalVar.BranchTINumber";
mostCurrent._stinno = mostCurrent._globalvar._branchtinumber /*String*/ ;
 //BA.debugLineNum = 298;BA.debugLine="sBranchCode = GlobalVar.BranchCode";
mostCurrent._sbranchcode = mostCurrent._globalvar._branchcode /*String*/ ;
 //BA.debugLineNum = 299;BA.debugLine="sBillPeriod = GlobalVar.BillPeriod";
mostCurrent._sbillperiod = mostCurrent._globalvar._billperiod /*String*/ ;
 //BA.debugLineNum = 302;BA.debugLine="sPresRdg = PresRdg";
mostCurrent._spresrdg = mostCurrent._presrdg;
 //BA.debugLineNum = 303;BA.debugLine="sPrevRdg = PrevRdg";
mostCurrent._sprevrdg = BA.NumberToString(_prevrdg);
 //BA.debugLineNum = 304;BA.debugLine="sAddCons = AddCons";
mostCurrent._saddcons = BA.NumberToString(_addcons);
 //BA.debugLineNum = 305;BA.debugLine="sTotalCum = TotalCum";
mostCurrent._stotalcum = BA.NumberToString(_totalcum);
 //BA.debugLineNum = 307;BA.debugLine="sGDeposit = GDeposit";
mostCurrent._sgdeposit = BA.NumberToString(_gdeposit);
 //BA.debugLineNum = 308;BA.debugLine="sBasicAmt = BasicAmt";
mostCurrent._sbasicamt = BA.NumberToString(_basicamt);
 //BA.debugLineNum = 309;BA.debugLine="sPCAAmt = PCAAmt";
mostCurrent._spcaamt = BA.NumberToString(_pcaamt);
 //BA.debugLineNum = 310;BA.debugLine="sCurrentAmt = CurrentAmt";
mostCurrent._scurrentamt = BA.NumberToString(_currentamt);
 //BA.debugLineNum = 311;BA.debugLine="sSeniorOnBeforeAmt = SeniorOnBeforeAmt";
mostCurrent._ssenioronbeforeamt = BA.NumberToString(_senioronbeforeamt);
 //BA.debugLineNum = 312;BA.debugLine="sAddToBillAmt = AddToBillAmt'Others";
mostCurrent._saddtobillamt = BA.NumberToString(_addtobillamt);
 //BA.debugLineNum = 313;BA.debugLine="sArrearsAmt = ArrearsAmt";
mostCurrent._sarrearsamt = BA.NumberToString(_arrearsamt);
 //BA.debugLineNum = 314;BA.debugLine="sAdvPayment = AdvPayment";
mostCurrent._sadvpayment = BA.NumberToString(_advpayment);
 //BA.debugLineNum = 315;BA.debugLine="sTotalDueAmt = TotalDueAmt";
mostCurrent._stotaldueamt = BA.NumberToString(_totaldueamt);
 //BA.debugLineNum = 316;BA.debugLine="sPenaltyAmt = PenaltyAmt";
mostCurrent._spenaltyamt = BA.NumberToString(_penaltyamt);
 //BA.debugLineNum = 317;BA.debugLine="sSeniorAfterAmt = SeniorAfterAmt";
mostCurrent._sseniorafteramt = BA.NumberToString(_seniorafteramt);
 //BA.debugLineNum = 318;BA.debugLine="sTotalDueAmtAfterSC = TotalDueAmtAfterSC";
mostCurrent._stotaldueamtaftersc = BA.NumberToString(_totaldueamtaftersc);
 //BA.debugLineNum = 321;BA.debugLine="PrintBuffer = Chr(27) & \"@\" _ 					& Chr(27) & C";
_printbuffer = BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"@"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+mostCurrent._sbranchname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+mostCurrent._sbranchaddress+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+mostCurrent._sbranchcontactno+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"TIN NO: "+mostCurrent._stinno+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"Branch Code: "+mostCurrent._sbranchcode+anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+"STATEMENT OF ACCOUNT"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"For the Month of "+mostCurrent._sbillperiod+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+"Account No: "+mostCurrent._acctno+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv5(mostCurrent._acctname)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv6(_titlecase(mostCurrent._acctaddress),(long) (42))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+"Meter No: "+mostCurrent._meterno+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"Book-Seq: "+mostCurrent._bookseq+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"Class   : "+mostCurrent._custclass+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+"Guarantee Deposit "+_addwhitespaces2(anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(mostCurrent._sgdeposit)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(mostCurrent._sgdeposit)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"Bill Number       "+_addwhitespaces2(mostCurrent._billno)+mostCurrent._billno+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"Bill Date         "+_addwhitespaces2(mostCurrent._billdate)+mostCurrent._billdate+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+"Period From       "+_addwhitespaces2(mostCurrent._datefrom)+mostCurrent._datefrom+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+"Period To         "+_addwhitespaces2(mostCurrent._dateto)+mostCurrent._dateto+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"Pres. Reading      "+_addwhitespaces(mostCurrent._spresrdg)+mostCurrent._spresrdg+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"Prev. Reading      "+_addwhitespaces(mostCurrent._sprevrdg)+mostCurrent._sprevrdg+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"Add Cons.          "+_addwhitespaces(mostCurrent._saddcons)+mostCurrent._saddcons+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"Total Cum          "+_addwhitespaces(mostCurrent._stotalcum)+mostCurrent._stotalcum+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"BASIC              "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(mostCurrent._sbasicamt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(mostCurrent._sbasicamt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"PCA                "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(mostCurrent._spcaamt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(mostCurrent._spcaamt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+"CURRENT BILL       "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(mostCurrent._scurrentamt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(mostCurrent._scurrentamt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"10% SC DISCOUNT    "+_addwhitespaces("("+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(mostCurrent._ssenioronbeforeamt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")")+"("+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(mostCurrent._ssenioronbeforeamt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"OTHERS             "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(mostCurrent._saddtobillamt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(mostCurrent._saddtobillamt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"ARREARS            "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(mostCurrent._sarrearsamt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(mostCurrent._sarrearsamt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"ADVANCES           "+_addwhitespaces("("+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(mostCurrent._sadvpayment)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")")+"("+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(mostCurrent._sadvpayment)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+"TOTAL DUE          "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(mostCurrent._stotaldueamt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(mostCurrent._stotaldueamt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 //BA.debugLineNum = 359;BA.debugLine="If DBaseFunctions.IsWithDueDate(iReadID) = True";
if (mostCurrent._dbasefunctions._iswithduedate /*boolean*/ (mostCurrent.activityBA,_ireadid)==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 360;BA.debugLine="PrintBuffer = PrintBuffer & Chr(27) & Chr(97) &";
_printbuffer = _printbuffer+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"PAYMENT AFTER DUE DATE"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"DUE DATE           "+_addwhitespaces(mostCurrent._duedate)+mostCurrent._duedate+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"10% PENALTY        "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(mostCurrent._spenaltyamt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(mostCurrent._spenaltyamt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"5% SC DISCOUNT     "+_addwhitespaces("("+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(mostCurrent._sseniorafteramt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")")+"("+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(mostCurrent._sseniorafteramt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+"TOTAL              "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(mostCurrent._stotaldueamtaftersc)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(mostCurrent._stotaldueamtaftersc)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"=========================================="+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 }else {
 //BA.debugLineNum = 369;BA.debugLine="PrintBuffer = PrintBuffer & Chr(27) & \"!\" & Chr";
_printbuffer = _printbuffer+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"=========================================="+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 };
 //BA.debugLineNum = 373;BA.debugLine="If ArrearsAmt > 0 Then";
if (_arrearsamt>0) { 
 //BA.debugLineNum = 374;BA.debugLine="PrintBuffer = PrintBuffer & Chr(27) & Chr(97) &";
_printbuffer = _printbuffer+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (17)))+"NOTICE OF DISCONNECTION"+anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 };
 //BA.debugLineNum = 378;BA.debugLine="PrintBuffer = PrintBuffer & Chr(27) & Chr(97) &";
_printbuffer = _printbuffer+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (9)))+"NOTE:"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"    Disregard Arrears if Payment has been made. Please pay on or before due date to avoid Disconnection."+anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 //BA.debugLineNum = 382;BA.debugLine="StartPrinter";
_startprinter();
 } 
       catch (Exception e80) {
			processBA.setLastException(e80); //BA.debugLineNum = 384;BA.debugLine="snack.Initialize(\"\", Activity, $\"Unable to Print";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Unable to Print Bill Statement due to ")+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 385;BA.debugLine="SetSnackBarBackground(snack, Colors.White)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 386;BA.debugLine="SetSnackBarTextColor(snack, Colors.Red)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 387;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 388;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("144433554",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 391;BA.debugLine="End Sub";
return "";
}
public static void  _printer_connected(boolean _success) throws Exception{
ResumableSub_Printer_Connected rsub = new ResumableSub_Printer_Connected(null,_success);
rsub.resume(processBA, null);
}
public static class ResumableSub_Printer_Connected extends BA.ResumableSub {
public ResumableSub_Printer_Connected(com.bwsi.MeterReader.customerbill parent,boolean _success) {
this.parent = parent;
this._success = _success;
}
com.bwsi.MeterReader.customerbill parent;
boolean _success;
Object _iresponse = null;
anywheresoftware.b4a.objects.CSBuilder _csmsg = null;
byte[] _initprinter = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 492;BA.debugLine="Dim iResponse As Object";
_iresponse = new Object();
 //BA.debugLineNum = 493;BA.debugLine="Dim csMsg As CSBuilder";
_csmsg = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 494;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 495;BA.debugLine="Log(\"Connected: \" & Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("144761092","Connected: "+BA.ObjectToString(_success),0);
 //BA.debugLineNum = 497;BA.debugLine="If Success = False Then";
if (true) break;

case 1:
//if
this.state = 22;
if (_success==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 3;
}else {
this.state = 9;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 498;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 499;BA.debugLine="If Not(ConfirmWarning($\"Unable to Connect to Pri";
if (true) break;

case 4:
//if
this.state = 7;
if (anywheresoftware.b4a.keywords.Common.Not(_confirmwarning(("Unable to Connect to Printer!"),("Printer Error"),("Reprint"),("Cancel"),""))) { 
this.state = 6;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 500;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 7:
//C
this.state = 22;
;
 //BA.debugLineNum = 502;BA.debugLine="StartPrinter";
_startprinter();
 if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 505;BA.debugLine="Dim initPrinter() As Byte";
_initprinter = new byte[(int) (0)];
;
 //BA.debugLineNum = 507;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 508;BA.debugLine="TMPrinter.Initialize2(Serial1.OutputStream, \"win";
parent._tmprinter.Initialize2(parent._serial1.getOutputStream(),"windows-1252");
 //BA.debugLineNum = 509;BA.debugLine="oStream.Initialize(Serial1.InputStream, Serial1.";
parent._ostream.Initialize(processBA,parent._serial1.getInputStream(),parent._serial1.getOutputStream(),"LogoPrint");
 //BA.debugLineNum = 510;BA.debugLine="Logo.Initialize(File.DirAssets, GlobalVar.Branch";
parent._logo.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),parent.mostCurrent._globalvar._branchlogo /*String*/ );
 //BA.debugLineNum = 511;BA.debugLine="Logobmp = CreateScaledBitmap(Logo, Logo.Width, L";
parent._logobmp = _createscaledbitmap(parent._logo,parent._logo.getWidth(),parent._logo.getHeight());
 //BA.debugLineNum = 512;BA.debugLine="Log(DeviceName)";
anywheresoftware.b4a.keywords.Common.LogImpl("144761109",parent._devicename,0);
 //BA.debugLineNum = 514;BA.debugLine="If GlobalVar.SF.Upper(DeviceName) = \"WOOSIM\" The";
if (true) break;

case 10:
//if
this.state = 21;
if ((parent.mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv5(parent._devicename)).equals("WOOSIM")) { 
this.state = 12;
}else {
this.state = 20;
}if (true) break;

case 12:
//C
this.state = 13;
 //BA.debugLineNum = 515;BA.debugLine="WoosimCmd.InitializeStatic(\"com.woosim.printer.";
parent._woosimcmd.InitializeStatic("com.woosim.printer.WoosimCmd");
 //BA.debugLineNum = 516;BA.debugLine="WoosimImage.InitializeStatic(\"com.woosim.printe";
parent._woosimimage.InitializeStatic("com.woosim.printer.WoosimImage");
 //BA.debugLineNum = 518;BA.debugLine="initPrinter = WoosimCmd.RunMethod(\"initPrinter\"";
_initprinter = (byte[])(parent._woosimcmd.RunMethod("initPrinter",(Object[])(anywheresoftware.b4a.keywords.Common.Null)));
 //BA.debugLineNum = 519;BA.debugLine="If GlobalVar.BranchID = 28 Or GlobalVar.BranchI";
if (true) break;

case 13:
//if
this.state = 18;
if (parent.mostCurrent._globalvar._branchid /*int*/ ==28 || parent.mostCurrent._globalvar._branchid /*int*/ ==29 || parent.mostCurrent._globalvar._branchid /*int*/ ==30) { 
this.state = 15;
}else {
this.state = 17;
}if (true) break;

case 15:
//C
this.state = 18;
 //BA.debugLineNum = 520;BA.debugLine="PrintLogo = WoosimImage.RunMethod(\"printBitmap";
parent._printlogo = (byte[])(parent._woosimimage.RunMethod("printBitmap",new Object[]{(Object)(0),(Object)(0),(Object)(400),(Object)(200),(Object)(parent._logobmp.getObject())}));
 if (true) break;

case 17:
//C
this.state = 18;
 //BA.debugLineNum = 522;BA.debugLine="PrintLogo = WoosimImage.RunMethod(\"printBitmap";
parent._printlogo = (byte[])(parent._woosimimage.RunMethod("printBitmap",new Object[]{(Object)(0),(Object)(0),(Object)(400),(Object)(150),(Object)(parent._logobmp.getObject())}));
 if (true) break;

case 18:
//C
this.state = 21;
;
 //BA.debugLineNum = 524;BA.debugLine="oStream.Write(initPrinter)";
parent._ostream.Write(_initprinter);
 //BA.debugLineNum = 525;BA.debugLine="oStream.Write(WoosimCmd.RunMethod(\"setPageMode\"";
parent._ostream.Write((byte[])(parent._woosimcmd.RunMethod("setPageMode",(Object[])(anywheresoftware.b4a.keywords.Common.Null))));
 //BA.debugLineNum = 526;BA.debugLine="oStream.Write(PrintLogo)";
parent._ostream.Write(parent._printlogo);
 //BA.debugLineNum = 527;BA.debugLine="oStream.Write(WoosimCmd.RunMethod(\"PM_setStdMod";
parent._ostream.Write((byte[])(parent._woosimcmd.RunMethod("PM_setStdMode",(Object[])(anywheresoftware.b4a.keywords.Common.Null))));
 if (true) break;

case 20:
//C
this.state = 21;
 //BA.debugLineNum = 529;BA.debugLine="EPSONPrint.Initialize";
parent._epsonprint._initialize /*String*/ (processBA);
 //BA.debugLineNum = 530;BA.debugLine="PrintLogo =  EPSONPrint.getBytesForBitmap2(Glob";
parent._printlogo = parent._epsonprint._getbytesforbitmap2 /*byte[]*/ (parent.mostCurrent._globalvar._branchlogo /*String*/ ,anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 21:
//C
this.state = 22;
;
 //BA.debugLineNum = 532;BA.debugLine="oStream.Write(PrintLogo)";
parent._ostream.Write(parent._printlogo);
 //BA.debugLineNum = 533;BA.debugLine="Sleep(500)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (500));
this.state = 23;
return;
case 23:
//C
this.state = 22;
;
 //BA.debugLineNum = 534;BA.debugLine="TMPrinter.WriteLine(PrintBuffer)";
parent._tmprinter.WriteLine(parent._printbuffer);
 //BA.debugLineNum = 535;BA.debugLine="Log(PrintBuffer)";
anywheresoftware.b4a.keywords.Common.LogImpl("144761132",parent._printbuffer,0);
 //BA.debugLineNum = 536;BA.debugLine="TMPrinter.Flush";
parent._tmprinter.Flush();
 //BA.debugLineNum = 537;BA.debugLine="DispInfoMsg($\"Bill Statement successfully printe";
_dispinfomsg(("Bill Statement successfully printed.")+("Tap OK to Continue..."),("Bill Printing"));
 //BA.debugLineNum = 538;BA.debugLine="TMPrinter.Close";
parent._tmprinter.Close();
 //BA.debugLineNum = 539;BA.debugLine="Serial1.Disconnect";
parent._serial1.Disconnect();
 if (true) break;

case 22:
//C
this.state = -1;
;
 //BA.debugLineNum = 541;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 18;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 19;BA.debugLine="Dim index As Object";
_index = new Object();
 //BA.debugLineNum = 20;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 21;BA.debugLine="Dim EPSONPrint As clsPrint";
_epsonprint = new com.bwsi.MeterReader.clsprint();
 //BA.debugLineNum = 23;BA.debugLine="Dim BTAdmin As BluetoothAdmin";
_btadmin = new anywheresoftware.b4a.objects.Serial.BluetoothAdmin();
 //BA.debugLineNum = 24;BA.debugLine="Dim PairedDevices As Map";
_paireddevices = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 26;BA.debugLine="Dim FoundDevices As List";
_founddevices = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 27;BA.debugLine="Dim DeviceName, DeviceMac As String";
_devicename = "";
_devicemac = "";
 //BA.debugLineNum = 29;BA.debugLine="Dim Serial1 As Serial";
_serial1 = new anywheresoftware.b4a.objects.Serial();
 //BA.debugLineNum = 30;BA.debugLine="Dim TMPrinter As TextWriter";
_tmprinter = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Dim PrintBuffer As String";
_printbuffer = "";
 //BA.debugLineNum = 33;BA.debugLine="Dim PrintLogo() As Byte";
_printlogo = new byte[(int) (0)];
;
 //BA.debugLineNum = 34;BA.debugLine="Dim oStream As AsyncStreams";
_ostream = new anywheresoftware.b4a.randomaccessfile.AsyncStreams();
 //BA.debugLineNum = 35;BA.debugLine="Private Res As Int";
_res = 0;
 //BA.debugLineNum = 37;BA.debugLine="Private flagBitmap As Bitmap";
_flagbitmap = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Dim Logobmp As Bitmap";
_logobmp = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Dim WoosimCmd As JavaObject";
_woosimcmd = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 41;BA.debugLine="Dim WoosimImage As JavaObject";
_woosimimage = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 42;BA.debugLine="Dim Logo As Bitmap";
_logo = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Public Permissions As RuntimePermissions";
_permissions = new anywheresoftware.b4a.objects.RuntimePermissions();
 //BA.debugLineNum = 44;BA.debugLine="End Sub";
return "";
}
public static String  _repeatchar(String _schar,int _noofrepetition) throws Exception{
String _totspaces = "";
int _i = 0;
 //BA.debugLineNum = 392;BA.debugLine="Private Sub RepeatChar(sChar As String, NoOfRepeti";
 //BA.debugLineNum = 393;BA.debugLine="Dim Totspaces As String";
_totspaces = "";
 //BA.debugLineNum = 395;BA.debugLine="For i = 1 To NoOfRepetition";
{
final int step2 = 1;
final int limit2 = _noofrepetition;
_i = (int) (1) ;
for (;_i <= limit2 ;_i = _i + step2 ) {
 //BA.debugLineNum = 396;BA.debugLine="Totspaces = Totspaces & sChar";
_totspaces = _totspaces+_schar;
 }
};
 //BA.debugLineNum = 399;BA.debugLine="Return Totspaces";
if (true) return _totspaces;
 //BA.debugLineNum = 400;BA.debugLine="End Sub";
return "";
}
public static String  _setsnackbarbackground(de.amberhome.objects.SnackbarWrapper _psnack,int _pcolor) throws Exception{
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
 //BA.debugLineNum = 715;BA.debugLine="Sub SetSnackBarBackground(pSnack As DSSnackbar, pC";
 //BA.debugLineNum = 716;BA.debugLine="Dim v As View";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
 //BA.debugLineNum = 717;BA.debugLine="v = pSnack.View";
_v = (anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(_psnack.getView()));
 //BA.debugLineNum = 718;BA.debugLine="v.Color = pColor";
_v.setColor(_pcolor);
 //BA.debugLineNum = 719;BA.debugLine="End Sub";
return "";
}
public static String  _setsnackbartextcolor(de.amberhome.objects.SnackbarWrapper _psnack,int _pcolor) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _p = null;
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
anywheresoftware.b4a.objects.LabelWrapper _textv = null;
 //BA.debugLineNum = 721;BA.debugLine="Sub SetSnackBarTextColor(pSnack As DSSnackbar, pCo";
 //BA.debugLineNum = 722;BA.debugLine="Dim p As Panel";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 723;BA.debugLine="p = pSnack.View";
_p = (anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(_psnack.getView()));
 //BA.debugLineNum = 724;BA.debugLine="For Each v As View In p.GetAllViewsRecursive";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
{
final anywheresoftware.b4a.BA.IterableList group3 = _p.GetAllViewsRecursive();
final int groupLen3 = group3.getSize()
;int index3 = 0;
;
for (; index3 < groupLen3;index3++){
_v = (anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(group3.Get(index3)));
 //BA.debugLineNum = 725;BA.debugLine="If v Is Label Then";
if (_v.getObjectOrNull() instanceof android.widget.TextView) { 
 //BA.debugLineNum = 726;BA.debugLine="Dim textv As Label";
_textv = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 727;BA.debugLine="textv = v";
_textv = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_v.getObject()));
 //BA.debugLineNum = 728;BA.debugLine="textv.TextColor = pColor";
_textv.setTextColor(_pcolor);
 //BA.debugLineNum = 729;BA.debugLine="Exit";
if (true) break;
 };
 }
};
 //BA.debugLineNum = 732;BA.debugLine="End Sub";
return "";
}
public static String  _startprinter() throws Exception{
int _i = 0;
 //BA.debugLineNum = 436;BA.debugLine="Sub StartPrinter";
 //BA.debugLineNum = 438;BA.debugLine="PairedDevices.Initialize";
_paireddevices.Initialize();
 //BA.debugLineNum = 440;BA.debugLine="Try";
try { //BA.debugLineNum = 441;BA.debugLine="PairedDevices = Serial1.GetPairedDevices";
_paireddevices = _serial1.GetPairedDevices();
 } 
       catch (Exception e5) {
			processBA.setLastException(e5); //BA.debugLineNum = 443;BA.debugLine="Msgbox(\"Getting Paired Devices\",\"Printer Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Getting Paired Devices"),BA.ObjectToCharSequence("Printer Error"),mostCurrent.activityBA);
 //BA.debugLineNum = 444;BA.debugLine="TMPrinter.Close";
_tmprinter.Close();
 //BA.debugLineNum = 445;BA.debugLine="Serial1.Disconnect";
_serial1.Disconnect();
 };
 //BA.debugLineNum = 448;BA.debugLine="If PairedDevices.Size = 0 Then";
if (_paireddevices.getSize()==0) { 
 //BA.debugLineNum = 449;BA.debugLine="Msgbox(\"Error Connecting to Printer - Either No";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Error Connecting to Printer - Either No Paired Bluetooth Printer or Printer Not Found!"),BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.Application.getLabelName()),mostCurrent.activityBA);
 //BA.debugLineNum = 450;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 453;BA.debugLine="If PairedDevices.Size = 1 Then";
if (_paireddevices.getSize()==1) { 
 //BA.debugLineNum = 454;BA.debugLine="Try";
try { //BA.debugLineNum = 455;BA.debugLine="DeviceName=PairedDevices.Getkeyat(0)";
_devicename = BA.ObjectToString(_paireddevices.GetKeyAt((int) (0)));
 //BA.debugLineNum = 456;BA.debugLine="DeviceMac=PairedDevices.GetValueAt(0)";
_devicemac = BA.ObjectToString(_paireddevices.GetValueAt((int) (0)));
 //BA.debugLineNum = 457;BA.debugLine="Log(DeviceName & \" -> \" & DeviceMac)";
anywheresoftware.b4a.keywords.Common.LogImpl("144695573",_devicename+" -> "+_devicemac,0);
 //BA.debugLineNum = 460;BA.debugLine="Serial1.ConnectInsecure(BTAdmin, DeviceMac,1)";
_serial1.ConnectInsecure(processBA,_btadmin,_devicemac,(int) (1));
 //BA.debugLineNum = 461;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 } 
       catch (Exception e21) {
			processBA.setLastException(e21); //BA.debugLineNum = 464;BA.debugLine="Msgbox(\"Connecting\",\"Printer Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Connecting"),BA.ObjectToCharSequence("Printer Error"),mostCurrent.activityBA);
 //BA.debugLineNum = 465;BA.debugLine="TMPrinter.Close";
_tmprinter.Close();
 //BA.debugLineNum = 466;BA.debugLine="Serial1.Disconnect";
_serial1.Disconnect();
 };
 }else {
 //BA.debugLineNum = 469;BA.debugLine="FoundDevices.Initialize";
_founddevices.Initialize();
 //BA.debugLineNum = 471;BA.debugLine="For i = 0 To PairedDevices.Size - 1";
{
final int step27 = 1;
final int limit27 = (int) (_paireddevices.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit27 ;_i = _i + step27 ) {
 //BA.debugLineNum = 472;BA.debugLine="FoundDevices.Add(PairedDevices.GetKeyAt(i))";
_founddevices.Add(_paireddevices.GetKeyAt(_i));
 //BA.debugLineNum = 473;BA.debugLine="DeviceName=PairedDevices.Getkeyat(i)";
_devicename = BA.ObjectToString(_paireddevices.GetKeyAt(_i));
 //BA.debugLineNum = 474;BA.debugLine="DeviceMac=PairedDevices.GetValueAt(i)";
_devicemac = BA.ObjectToString(_paireddevices.GetValueAt(_i));
 //BA.debugLineNum = 475;BA.debugLine="Log(DeviceName & \" -> \" & DeviceMac)";
anywheresoftware.b4a.keywords.Common.LogImpl("144695591",_devicename+" -> "+_devicemac,0);
 //BA.debugLineNum = 477;BA.debugLine="Serial1.ConnectInsecure(BTAdmin, DeviceMac,1)";
_serial1.ConnectInsecure(processBA,_btadmin,_devicemac,(int) (1));
 //BA.debugLineNum = 478;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 479;BA.debugLine="Exit";
if (true) break;
 }
};
 //BA.debugLineNum = 483;BA.debugLine="Res = InputList(FoundDevices, \"Choose Device\", -";
_res = anywheresoftware.b4a.keywords.Common.InputList(_founddevices,BA.ObjectToCharSequence("Choose Device"),(int) (-1),mostCurrent.activityBA);
 //BA.debugLineNum = 485;BA.debugLine="If Res <> DialogResponse.CANCEL Then";
if (_res!=anywheresoftware.b4a.keywords.Common.DialogResponse.CANCEL) { 
 //BA.debugLineNum = 486;BA.debugLine="Serial1.Connect(PairedDevices.Get(FoundDevices.";
_serial1.Connect(processBA,BA.ObjectToString(_paireddevices.Get(_founddevices.Get(_res))));
 };
 };
 //BA.debugLineNum = 489;BA.debugLine="End Sub";
return "";
}
public static String  _titlecase(String _s) throws Exception{
anywheresoftware.b4a.keywords.Regex.MatcherWrapper _m = null;
int _i = 0;
 //BA.debugLineNum = 777;BA.debugLine="Sub TitleCase (s As String) As String";
 //BA.debugLineNum = 778;BA.debugLine="s = s.ToLowerCase";
_s = _s.toLowerCase();
 //BA.debugLineNum = 779;BA.debugLine="Dim m As Matcher = Regex.Matcher(\"\\b(\\w)\", s)";
_m = new anywheresoftware.b4a.keywords.Regex.MatcherWrapper();
_m = anywheresoftware.b4a.keywords.Common.Regex.Matcher("\\b(\\w)",_s);
 //BA.debugLineNum = 780;BA.debugLine="Do While m.Find";
while (_m.Find()) {
 //BA.debugLineNum = 781;BA.debugLine="Dim i As Int = m.GetStart(1)";
_i = _m.GetStart((int) (1));
 //BA.debugLineNum = 782;BA.debugLine="s = s.SubString2(0, i) & s.SubString2(i, i + 1).";
_s = _s.substring((int) (0),_i)+_s.substring(_i,(int) (_i+1)).toUpperCase()+_s.substring((int) (_i+1));
 }
;
 //BA.debugLineNum = 784;BA.debugLine="Return s";
if (true) return _s;
 //BA.debugLineNum = 785;BA.debugLine="End Sub";
return "";
}
public static String  _toolbar_menuitemclick(de.amberhome.objects.appcompat.ACMenuItemWrapper _item) throws Exception{
 //BA.debugLineNum = 237;BA.debugLine="Sub ToolBar_MenuItemClick (Item As ACMenuItem)";
 //BA.debugLineNum = 238;BA.debugLine="End Sub";
return "";
}
public static String  _toolbar_navigationitemclick() throws Exception{
 //BA.debugLineNum = 233;BA.debugLine="Sub ToolBar_NavigationItemClick";
 //BA.debugLineNum = 234;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 235;BA.debugLine="End Sub";
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
