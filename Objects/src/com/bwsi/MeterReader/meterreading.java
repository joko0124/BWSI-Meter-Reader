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

public class meterreading extends androidx.appcompat.app.AppCompatActivity implements B4AActivity{
	public static meterreading mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "com.bwsi.MeterReader", "com.bwsi.MeterReader.meterreading");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (meterreading).");
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
		activityBA = new BA(this, layout, processBA, "com.bwsi.MeterReader", "com.bwsi.MeterReader.meterreading");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "com.bwsi.MeterReader.meterreading", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (meterreading) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (meterreading) Resume **");
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
		return meterreading.class;
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
            BA.LogInfo("** Activity (meterreading) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (meterreading) Pause event (activity is not paused). **");
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
            meterreading mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (meterreading) Resume **");
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
public static String _sepbuffer = "";
public static String _qrbuffer = "";
public static String _gcashrefno = "";
public static byte[] _barcode = null;
public static byte _byte3 = (byte)0;
public static byte[] _qrcodenew = null;
public static byte[] _printlogo = null;
public static byte[] _printseptagelogo = null;
public static anywheresoftware.b4a.randomaccessfile.AsyncStreams _ostream = null;
public static anywheresoftware.b4a.randomaccessfile.AsyncStreams _ostream2 = null;
public static int _res = 0;
public static anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _flagbitmap = null;
public static anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _logobmp = null;
public static anywheresoftware.b4j.object.JavaObject _woosimcmd = null;
public static anywheresoftware.b4j.object.JavaObject _woosimimage = null;
public static anywheresoftware.b4j.object.JavaObject _woosimbarcode = null;
public static anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _logo = null;
public static anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _septagelogo = null;
public static anywheresoftware.b4a.objects.RuntimePermissions _permissions = null;
public static anywheresoftware.b4a.audio.SoundPoolWrapper _soundsalarmchannel = null;
public static com.bwsi.MeterReader.slinptypeconst _inptyp = null;
public static anywheresoftware.b4a.obejcts.TTS _tts1 = null;
public static int _type_text_flag_no_suggestions = 0;
public anywheresoftware.b4a.objects.PanelWrapper _panel1 = null;
public anywheresoftware.b4a.objects.ListViewWrapper _lveffect = null;
public com.bwsi.MeterReader.cameraexclass _camex = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlfocus = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper _cvsdrawing = null;
public static int _api = 0;
public static double _focuscolor = 0;
public de.amberhome.objects.appcompat.ACToolbarDarkWrapper _toolbar = null;
public de.amberhome.objects.appcompat.ACActionBar _actionbarbutton = null;
public anywheresoftware.b4a.object.XmlLayoutBuilder _xmlicon = null;
public de.amberhome.objects.appcompat.ACPopupMenuWrapper _popwarnings = null;
public de.amberhome.objects.appcompat.ACPopupMenuWrapper _popsubmenu = null;
public static String _bookcode = "";
public static String _bookdesc = "";
public de.amberhome.objects.SnackbarWrapper _snack = null;
public anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
public adr.stringfunctions.stringfunctions _strsf = null;
public anywheresoftware.b4a.objects.IME _imekeyboard = null;
public static boolean _wasedited = false;
public com.bwsi.MeterReader.searchview _sv = null;
public de.amberhome.materialdialogs.MaterialDialogBuilderWrapper _matdialogbuilder = null;
public anywheresoftware.b4a.sql.SQL.CursorWrapper _rsloadedbook = null;
public anywheresoftware.b4a.sql.SQL.CursorWrapper _rsunreadacct = null;
public anywheresoftware.b4a.sql.SQL.CursorWrapper _rsreadacct = null;
public anywheresoftware.b4a.sql.SQL.CursorWrapper _rsallacct = null;
public anywheresoftware.b4a.sql.SQL.CursorWrapper _rsunusualreading = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlmain = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlaccountinfo = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblseqno = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblmeter = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblacctno = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblacctname = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblacctaddress = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlprevious = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblclass = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblprevcum = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblreadstatus = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlpresent = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtpresrdg = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblprescum = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbldiscon = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlfindings = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblfindings = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblmisscode = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblwarning = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblremarks = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlbuttons = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnprevious = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnnext = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnedit = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnmisscode = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnremarks = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnreprint = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlstatus = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblnoaccts = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblunreadcount = null;
public static int _readid = 0;
public static String _billno = "";
public static int _bookno = 0;
public static int _acctid = 0;
public static String _acctno = "";
public static String _acctname = "";
public static String _acctaddress = "";
public static String _acctclass = "";
public static String _acctsubclass = "";
public static String _acctclassification = "";
public static String _acctstatus = "";
public static String _meterno = "";
public static double _maxreading = 0;
public static String _seqno = "";
public static int _issenior = 0;
public static double _senioronbefore = 0;
public static double _seniorafter = 0;
public static double _seniormaxcum = 0;
public static double _gdeposit = 0;
public static String _prevrdgdate = "";
public static int _prevrdg = 0;
public static String _prevcum = "";
public static int _finalrdg = 0;
public static String _discondate = "";
public static String _presrdgdate = "";
public static String _presrdg = "";
public static String _prescum = "";
public static String _datefrom = "";
public static String _dateto = "";
public static int _withduedate = 0;
public static String _duedate = "";
public static String _disconnectiondate = "";
public static double _avecum = 0;
public static double _addcons = 0;
public static int _currentcum = 0;
public static int _totalcum = 0;
public static double _basicamt = 0;
public static double _pca = 0;
public static double _pcaamt = 0;
public static double _addtobillamt = 0;
public static double _advpayment = 0;
public static double _remainingadvpayment = 0;
public static double _penaltypct = 0;
public static double _penaltyamt = 0;
public static double _totaldueamtaftersc = 0;
public static String _readremarks = "";
public static String _saverem = "";
public static String _strreadremarks = "";
public static int _paidstatus = 0;
public static int _printstatus = 0;
public static int _wasmiscoded = 0;
public static int _miscodeid = 0;
public static String _miscodedesc = "";
public static int _wasimplosive = 0;
public static String _implosivetype = "";
public static int _wasread = 0;
public static String _ffindings = "";
public static String _fwarnings = "";
public static int _newseqno = 0;
public static int _noinput = 0;
public static String _timeread = "";
public static String _dateread = "";
public static String _printtime = "";
public static int _wasuploaded = 0;
public static String _billtype = "";
public static double _currentamt = 0;
public static double _arrearsamt = 0;
public static double _totaldueamt = 0;
public static double _senioronbeforeamt = 0;
public static double _seniorafteramt = 0;
public static double _totaldueamtbeforesc = 0;
public static int _intrateheaderid = 0;
public static double _discamt = 0;
public static double _discamt2 = 0;
public static double _metercharges = 0;
public static double _franchisetaxpct = 0;
public static double _franchisetaxamt = 0;
public static double _septagefeeamt = 0;
public static double _septagefee = 0;
public static double _septagearrears = 0;
public static double _gtotalseptage = 0;
public static double _gtotaldue = 0;
public static double _gtotalafterdue = 0;
public static double _gtotaldueamt = 0;
public static double _gtotalafterdueamt = 0;
public static int _noprinted = 0;
public static String _billperiod1st = "";
public static int _prevcum1st = 0;
public static String _billperiod2nd = "";
public static int _prevcum2nd = 0;
public static String _billperiod3rd = "";
public static int _prevcum3rd = 0;
public static int _hasseptagefee = 0;
public static int _minseptagecum = 0;
public static int _maxseptagecum = 0;
public static double _septagerate = 0;
public static boolean _blnedit = false;
public static int _bookmark = 0;
public static int _reccount = 0;
public static int _countunreadacct = 0;
public static int _intcurrpos = 0;
public static int _inttempcurrpos = 0;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _zeroconsicon = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _misscodeicon = null;
public com.bwsi.MeterReader.badger _badgemisscode = null;
public com.bwsi.MeterReader.badger _badgezero = null;
public com.bwsi.MeterReader.badger _badgehigh = null;
public com.bwsi.MeterReader.badger _badgelow = null;
public com.bwsi.MeterReader.badger _badgetotal = null;
public static int _intmisscode = 0;
public static int _intzero = 0;
public static int _inthigh = 0;
public static int _intlow = 0;
public static int _intave = 0;
public static int _intunprinted = 0;
public static int _inttotal = 0;
public anywheresoftware.b4a.objects.PanelWrapper _pnlunusual = null;
public static int _intsearchflag = 0;
public anywheresoftware.b4a.objects.PanelWrapper _pnlsearchunusual = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblunusualcount = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btncancelunusual = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlsearch = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlsearchoptions = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlsearchby = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _optunread = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _optread = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _optall = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _optseqno = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _optmeterno = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _optacctname = null;
public anywheresoftware.b4a.objects.PanelWrapper _searchpanel = null;
public static String _searchfor = "";
public anywheresoftware.b4a.objects.LabelWrapper _lblsearchreccount = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btncancelsearch = null;
public static int _bookid = 0;
public static String _bookseq = "";
public static String _custclass = "";
public static String _billdate = "";
public static String _sbranchname = "";
public static String _sbranchaddress = "";
public static String _sbranchcontactno = "";
public static String _stinno = "";
public static String _sbranchcode = "";
public static String _sbillperiod = "";
public static int _ihasseptage = 0;
public static double _dseptagerate = 0;
public static String _spresrdg = "";
public static String _sprevrdg = "";
public static String _saddcons = "";
public static String _stotalcum = "";
public com.johan.Vibrate.Vibrate _vibration = null;
public static long[] _vibratepattern = null;
public static String _sbillmonth = "";
public static String _sbillyear = "";
public static String _presaverdg = "";
public static String _presavecum = "";
public static String _spresentreading = "";
public static int _soundid = 0;
public anywheresoftware.b4a.objects.drawable.ColorDrawable _cdtxtbox = null;
public anywheresoftware.b4a.objects.drawable.ColorDrawable _cdconfirmrdg = null;
public anywheresoftware.b4a.objects.drawable.ColorDrawable _cdbuttoncancel = null;
public anywheresoftware.b4a.objects.drawable.ColorDrawable _cdbuttonsaveonly = null;
public anywheresoftware.b4a.objects.drawable.ColorDrawable _cdbuttonsaveandprint = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlnegativemsgbox = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnnegativecancelpost = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnnegativeselect = null;
public de.amberhome.objects.appcompat.ACRadioButtonWrapper _opt0 = null;
public de.amberhome.objects.appcompat.ACRadioButtonWrapper _opt1 = null;
public de.amberhome.objects.appcompat.ACRadioButtonWrapper _opt2 = null;
public de.amberhome.objects.appcompat.ACRadioButtonWrapper _opt3 = null;
public de.amberhome.objects.appcompat.ACRadioButtonWrapper _opt4 = null;
public de.amberhome.objects.appcompat.ACRadioButtonWrapper _opt5 = null;
public de.amberhome.objects.appcompat.ACRadioButtonWrapper _opt6 = null;
public de.amberhome.objects.appcompat.ACRadioButtonWrapper _opt7 = null;
public de.amberhome.objects.appcompat.ACRadioButtonWrapper _opt8 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtothers = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlhimsgbox = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnhicancelpost = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnhisaveonly = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnhisaveandprint = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlhighbillconfirmation = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtpresrdgconfirm = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnhbconfirmcancel = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnhbconfirmsaveandprint = null;
public static int _intsaveonly = 0;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnlowcancelpost = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnlowsaveonly = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnlowsaveandprint = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnllowmsgbox = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlzeromsgbox = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnzerocancelpost = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnzerosaveonly = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnzerosaveandprint = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtremarks = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlnormalmsgbox = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnnormalcancelpost = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnnormalsaveonly = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnnormalsaveandprint = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlavemsgbox = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnavetakepicture = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnavecancelpost = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlaveremmsgbox = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnaveremsaveandprint = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnaveremcancelpost = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtreason = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlhbconfirmreprint = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtpresrdgconfirmreprint = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnhbconfirmreprintcancel = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnhbconfirmsaveandreprint = null;
public com.bwsi.MeterReader.qrgenerator _qr = null;
public anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper _bmpqr = null;
public anywheresoftware.b4a.objects.Timer _t1 = null;
public de.amberhome.objects.appcompat.ACRadioButtonWrapper _optreason0 = null;
public de.amberhome.objects.appcompat.ACRadioButtonWrapper _optreason1 = null;
public de.amberhome.objects.appcompat.ACRadioButtonWrapper _optreason2 = null;
public de.amberhome.objects.appcompat.ACRadioButtonWrapper _optreason3 = null;
public de.amberhome.objects.appcompat.ACRadioButtonWrapper _optreason4 = null;
public de.amberhome.objects.appcompat.ACRadioButtonWrapper _optreason5 = null;
public de.amberhome.objects.appcompat.ACRadioButtonWrapper _optreason6 = null;
public de.amberhome.objects.appcompat.ACRadioButtonWrapper _optreason7 = null;
public de.amberhome.objects.appcompat.ACRadioButtonWrapper _optreason8 = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlmiscodemsgbox = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblmiscodemsgtitle = null;
public de.amberhome.objects.appcompat.ACRadioButtonWrapper _optmiscode0 = null;
public de.amberhome.objects.appcompat.ACRadioButtonWrapper _optmiscode1 = null;
public de.amberhome.objects.appcompat.ACRadioButtonWrapper _optmiscode2 = null;
public de.amberhome.objects.appcompat.ACRadioButtonWrapper _optmiscode3 = null;
public de.amberhome.objects.appcompat.ACRadioButtonWrapper _optmiscode4 = null;
public de.amberhome.objects.appcompat.ACRadioButtonWrapper _optmiscode5 = null;
public de.amberhome.objects.appcompat.ACRadioButtonWrapper _optmiscode6 = null;
public de.amberhome.objects.appcompat.ACRadioButtonWrapper _optmiscode7 = null;
public de.amberhome.objects.appcompat.ACRadioButtonWrapper _optmiscode8 = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnmiscodecancelpost = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnmiscodesave = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtmiscode = null;
public static boolean _blnsuperhighbill = false;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnsuperhbcancel = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnsuperhbsave = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlsuperhb = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtsuperhbpresrdg = null;
public static byte[] _initprinter = null;
public bm.watscho.keyboard.CustomKeyboard _ckeyboard = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public b4a.example.dateutils _dateutils = null;
public com.bwsi.MeterReader.main _main = null;
public com.bwsi.MeterReader.datasyncing _datasyncing = null;
public com.bwsi.MeterReader.login _login = null;
public com.bwsi.MeterReader.dbasefunctions _dbasefunctions = null;
public com.bwsi.MeterReader.customfunctions _customfunctions = null;
public com.bwsi.MeterReader.mainscreen _mainscreen = null;
public com.bwsi.MeterReader.myscale _myscale = null;
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
 //BA.debugLineNum = 459;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 460;BA.debugLine="MyScale.SetRate(0.5)";
mostCurrent._myscale._setrate /*String*/ (mostCurrent.activityBA,0.5);
 //BA.debugLineNum = 461;BA.debugLine="Activity.LoadLayout(\"mreading2\")";
mostCurrent._activity.LoadLayout("mreading2",mostCurrent.activityBA);
 //BA.debugLineNum = 462;BA.debugLine="BookCode = DBaseFunctions.GetCodebyID(\"BookCode\",";
mostCurrent._bookcode = mostCurrent._dbasefunctions._getcodebyid /*String*/ (mostCurrent.activityBA,"BookCode","tblBooks","BookID",mostCurrent._globalvar._bookid /*int*/ );
 //BA.debugLineNum = 463;BA.debugLine="BookDesc = DBaseFunctions.GetCodebyID(\"BookDesc\",";
mostCurrent._bookdesc = mostCurrent._dbasefunctions._getcodebyid /*String*/ (mostCurrent.activityBA,"BookDesc","tblBooks","BookID",mostCurrent._globalvar._bookid /*int*/ );
 //BA.debugLineNum = 465;BA.debugLine="modVariables.SourceSansProBold.Initialize(\"source";
mostCurrent._modvariables._sourcesansprobold /*njdude.fontawesome.lib.customfonts*/ ._initialize(processBA,"sourcesanspro-bold.ttf");
 //BA.debugLineNum = 466;BA.debugLine="modVariables.SourceSansProRegular.Initialize(\"sou";
mostCurrent._modvariables._sourcesansproregular /*njdude.fontawesome.lib.customfonts*/ ._initialize(processBA,"sourcesanspro-regular.ttf");
 //BA.debugLineNum = 468;BA.debugLine="GlobalVar.CSTitle.Initialize.Size(18).Bold.Typefa";
mostCurrent._globalvar._cstitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (18)).Bold().Typeface((android.graphics.Typeface)(mostCurrent._modvariables._sourcesansprobold /*njdude.fontawesome.lib.customfonts*/ ._setcustomfonts().getObject())).Append(BA.ObjectToCharSequence(("BOOK - ")+mostCurrent._bookcode)).PopAll();
 //BA.debugLineNum = 469;BA.debugLine="GlobalVar.CSSubTitle.Initialize.Size(13).Append(B";
mostCurrent._globalvar._cssubtitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (13)).Append(BA.ObjectToCharSequence(mostCurrent._bookdesc)).PopAll();
 //BA.debugLineNum = 471;BA.debugLine="ToolBar.InitMenuListener";
mostCurrent._toolbar.InitMenuListener();
 //BA.debugLineNum = 472;BA.debugLine="ToolBar.Title = GlobalVar.CSTitle";
mostCurrent._toolbar.setTitle(BA.ObjectToCharSequence(mostCurrent._globalvar._cstitle /*anywheresoftware.b4a.objects.CSBuilder*/ .getObject()));
 //BA.debugLineNum = 473;BA.debugLine="ToolBar.SubTitle = GlobalVar.CSSubTitle";
mostCurrent._toolbar.setSubTitle(BA.ObjectToCharSequence(mostCurrent._globalvar._cssubtitle /*anywheresoftware.b4a.objects.CSBuilder*/ .getObject()));
 //BA.debugLineNum = 474;BA.debugLine="ToolBar.SetElevationAnimated(5,5dip)";
mostCurrent._toolbar.SetElevationAnimated((int) (5),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 476;BA.debugLine="Dim jo As JavaObject";
_jo = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 477;BA.debugLine="Dim xl As XmlLayoutBuilder";
_xl = new anywheresoftware.b4a.object.XmlLayoutBuilder();
 //BA.debugLineNum = 478;BA.debugLine="jo = ToolBar";
_jo = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(mostCurrent._toolbar.getObject()));
 //BA.debugLineNum = 479;BA.debugLine="jo.RunMethod(\"setPopupTheme\", Array(xl.GetResourc";
_jo.RunMethod("setPopupTheme",new Object[]{(Object)(_xl.GetResourceId("style","ToolbarMenu"))});
 //BA.debugLineNum = 480;BA.debugLine="jo.RunMethod(\"setContentInsetStartWithNavigation\"";
_jo.RunMethod("setContentInsetStartWithNavigation",new Object[]{(Object)(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)))});
 //BA.debugLineNum = 481;BA.debugLine="jo.RunMethod(\"setTitleMarginStart\", Array(0dip))";
_jo.RunMethod("setTitleMarginStart",new Object[]{(Object)(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)))});
 //BA.debugLineNum = 483;BA.debugLine="ActionBarButton.Initialize";
mostCurrent._actionbarbutton.Initialize(mostCurrent.activityBA);
 //BA.debugLineNum = 484;BA.debugLine="ActionBarButton.ShowUpIndicator = True";
mostCurrent._actionbarbutton.setShowUpIndicator(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 486;BA.debugLine="T1.Initialize(\"Timer1\",100)";
mostCurrent._t1.Initialize(processBA,"Timer1",(long) (100));
 //BA.debugLineNum = 487;BA.debugLine="T1.Enabled = False";
mostCurrent._t1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 489;BA.debugLine="If FirstTime Then";
if (_firsttime) { 
 //BA.debugLineNum = 490;BA.debugLine="T1.Initialize(\"Timer1\",100)";
mostCurrent._t1.Initialize(processBA,"Timer1",(long) (100));
 //BA.debugLineNum = 491;BA.debugLine="T1.Enabled = False";
mostCurrent._t1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 492;BA.debugLine="flagBitmap = LoadBitmap(File.DirAssets, \"flag.pn";
_flagbitmap = anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"flag.png");
 //BA.debugLineNum = 493;BA.debugLine="QR.Initialize(150)";
mostCurrent._qr._initialize /*String*/ (processBA,(int) (150));
 //BA.debugLineNum = 494;BA.debugLine="intSaveOnly = 0";
_intsaveonly = (int) (0);
 //BA.debugLineNum = 495;BA.debugLine="blnSuperHighBill = False";
_blnsuperhighbill = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 498;BA.debugLine="txtPresRdg.Color = Colors.Black";
mostCurrent._txtpresrdg.setColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 499;BA.debugLine="txtPresRdg.TextColor = 0xFFFFDB71";
mostCurrent._txtpresrdg.setTextColor((int) (0xffffdb71));
 //BA.debugLineNum = 500;BA.debugLine="cKeyboard.Initialize(\"CKB\",\"keyboardview_trans\")";
mostCurrent._ckeyboard.Initialize(mostCurrent.activityBA,"CKB","keyboardview_trans");
 //BA.debugLineNum = 501;BA.debugLine="cKeyboard.RegisterEditText(txtPresRdg,\"txtPresRdg";
mostCurrent._ckeyboard.RegisterEditText((android.widget.EditText)(mostCurrent._txtpresrdg.getObject()),"txtPresRdg","num",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 503;BA.debugLine="SetButtonColors";
_setbuttoncolors();
 //BA.debugLineNum = 504;BA.debugLine="ClearDisplay";
_cleardisplay();
 //BA.debugLineNum = 505;BA.debugLine="If RetrieveRecords(GlobalVar.BookID) = True Then";
if (_retrieverecords(mostCurrent._globalvar._bookid /*int*/ )==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 506;BA.debugLine="rsLoadedBook.Position = 0";
mostCurrent._rsloadedbook.setPosition((int) (0));
 //BA.debugLineNum = 507;BA.debugLine="DisplayRecord";
_displayrecord();
 //BA.debugLineNum = 508;BA.debugLine="ButtonState";
_buttonstate();
 };
 //BA.debugLineNum = 512;BA.debugLine="pnlSearch.Visible = False";
mostCurrent._pnlsearch.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 513;BA.debugLine="pnlUnusual.Visible = False";
mostCurrent._pnlunusual.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 514;BA.debugLine="BTAdmin.Initialize(\"Admin\")";
_btadmin.Initialize(processBA,"Admin");
 //BA.debugLineNum = 515;BA.debugLine="Serial1.Initialize(\"Printer\")";
_serial1.Initialize("Printer");
 //BA.debugLineNum = 516;BA.debugLine="soundsAlarmChannel.Initialize(2)";
_soundsalarmchannel.Initialize((int) (2));
 //BA.debugLineNum = 518;BA.debugLine="InpTyp.Initialize";
_inptyp._initialize /*String*/ (processBA);
 //BA.debugLineNum = 519;BA.debugLine="GlobalVar.blnAverage = False";
mostCurrent._globalvar._blnaverage /*boolean*/  = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 520;BA.debugLine="GlobalVar.isAverage = 0";
mostCurrent._globalvar._isaverage /*int*/  = (int) (0);
 //BA.debugLineNum = 526;BA.debugLine="txtPresRdg.SingleLine = True";
mostCurrent._txtpresrdg.setSingleLine(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 527;BA.debugLine="txtPresRdg.ForceDoneButton = True";
mostCurrent._txtpresrdg.setForceDoneButton(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 528;BA.debugLine="txtPresRdg.InputType = txtPresRdg.INPUT_TYPE_NUMB";
mostCurrent._txtpresrdg.setInputType(mostCurrent._txtpresrdg.INPUT_TYPE_NUMBERS);
 //BA.debugLineNum = 530;BA.debugLine="txtPresRdgConfirm.SingleLine = True";
mostCurrent._txtpresrdgconfirm.setSingleLine(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 531;BA.debugLine="txtPresRdgConfirm.ForceDoneButton = True";
mostCurrent._txtpresrdgconfirm.setForceDoneButton(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 532;BA.debugLine="txtPresRdgConfirm.InputType = txtPresRdgConfirm.I";
mostCurrent._txtpresrdgconfirm.setInputType(mostCurrent._txtpresrdgconfirm.INPUT_TYPE_NUMBERS);
 //BA.debugLineNum = 534;BA.debugLine="txtSuperHBPresRdg.SingleLine = True";
mostCurrent._txtsuperhbpresrdg.setSingleLine(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 535;BA.debugLine="txtSuperHBPresRdg.ForceDoneButton = True";
mostCurrent._txtsuperhbpresrdg.setForceDoneButton(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 536;BA.debugLine="txtSuperHBPresRdg.InputType = txtSuperHBPresRdg.I";
mostCurrent._txtsuperhbpresrdg.setInputType(mostCurrent._txtsuperhbpresrdg.INPUT_TYPE_NUMBERS);
 //BA.debugLineNum = 537;BA.debugLine="cKeyboard.SetCustomFilter(txtPresRdg,txtPresRdg.I";
mostCurrent._ckeyboard.SetCustomFilter((android.widget.EditText)(mostCurrent._txtpresrdg.getObject()),mostCurrent._txtpresrdg.INPUT_TYPE_NUMBERS,"0,1,2,3,4,5,6,7,8,9");
 //BA.debugLineNum = 539;BA.debugLine="End Sub";
return "";
}
public static String  _activity_createmenu(de.amberhome.objects.appcompat.ACMenuWrapper _menu) throws Exception{
de.amberhome.objects.appcompat.ACMenuItemWrapper _iitem = null;
 //BA.debugLineNum = 623;BA.debugLine="Sub Activity_CreateMenu(Menu As ACMenu)";
 //BA.debugLineNum = 624;BA.debugLine="Dim iItem As ACMenuItem";
_iitem = new de.amberhome.objects.appcompat.ACMenuItemWrapper();
 //BA.debugLineNum = 626;BA.debugLine="Menu.Clear";
_menu.Clear();
 //BA.debugLineNum = 627;BA.debugLine="Menu.Add2(1, 1, $\"Search\"$, xmlIcon.GetDrawable(\"";
_menu.Add2((int) (1),(int) (1),BA.ObjectToCharSequence(("Search")),mostCurrent._xmlicon.GetDrawable("ic_find_in_page_white_36dp")).setShowAsAction(_iitem.SHOW_AS_ACTION_ALWAYS);
 //BA.debugLineNum = 628;BA.debugLine="Menu.Add2(2, 2, \"Flag\", xmlIcon.GetDrawable(\"ic_f";
_menu.Add2((int) (2),(int) (2),BA.ObjectToCharSequence("Flag"),mostCurrent._xmlicon.GetDrawable("ic_flag_white_24dp")).setShowAsAction(_iitem.SHOW_AS_ACTION_ALWAYS);
 //BA.debugLineNum = 629;BA.debugLine="Menu.Add2(3, 3, \"SubMenu\", xmlIcon.GetDrawable(\"i";
_menu.Add2((int) (3),(int) (3),BA.ObjectToCharSequence("SubMenu"),mostCurrent._xmlicon.GetDrawable("ic_menu_white_24dp")).setShowAsAction(_iitem.SHOW_AS_ACTION_IF_ROOM);
 //BA.debugLineNum = 631;BA.debugLine="flagBitmap = LoadBitmap(File.DirAssets, \"flag.png";
_flagbitmap = anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"flag.png");
 //BA.debugLineNum = 633;BA.debugLine="If GetUnusualCount(GlobalVar.BookID) = True Then";
if (_getunusualcount(mostCurrent._globalvar._bookid /*int*/ )==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 634;BA.debugLine="UpdateBadge(\"Flag\", AddBadgeToIcon(flagBitmap, i";
_updatebadge("Flag",_addbadgetoicon(_flagbitmap,_inttotal));
 };
 //BA.debugLineNum = 636;BA.debugLine="CreateSubMenus";
_createsubmenus();
 //BA.debugLineNum = 637;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 541;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 542;BA.debugLine="If KeyCode = 4 Then";
if (_keycode==4) { 
 //BA.debugLineNum = 543;BA.debugLine="If cKeyboard.IsSoftKeyboardVisible = True Then";
if (mostCurrent._ckeyboard.IsSoftKeyboardVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 544;BA.debugLine="If GlobalVar.SF.Trim(GlobalVar.SF.Len(txtPresRd";
if ((double)(Double.parseDouble(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv4(BA.NumberToString(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(mostCurrent._txtpresrdg.getText())))))>0) { 
 //BA.debugLineNum = 545;BA.debugLine="txtPresRdg.Text = \"\"";
mostCurrent._txtpresrdg.setText(BA.ObjectToCharSequence(""));
 };
 //BA.debugLineNum = 547;BA.debugLine="cKeyboard.HideKeyboard";
mostCurrent._ckeyboard.HideKeyboard();
 //BA.debugLineNum = 548;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 550;BA.debugLine="ToolBar_NavigationItemClick";
_toolbar_navigationitemclick();
 //BA.debugLineNum = 551;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 553;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 555;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 606;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 607;BA.debugLine="End Sub";
return "";
}
public static String  _activity_permissionresult(String _permission,boolean _result) throws Exception{
 //BA.debugLineNum = 609;BA.debugLine="Sub Activity_PermissionResult (Permission As Strin";
 //BA.debugLineNum = 610;BA.debugLine="If Permission = Permissions.PERMISSION_CAMERA The";
if ((_permission).equals(_permissions.PERMISSION_CAMERA)) { 
 //BA.debugLineNum = 611;BA.debugLine="LogColor(\"YOU CAN USE THE CAMERA\",Colors.Red)";
anywheresoftware.b4a.keywords.Common.LogImpl("121233666","YOU CAN USE THE CAMERA",anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 612;BA.debugLine="snack.Initialize(\"\",Activity, $\"Camera is ready";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Camera is ready to use..."),mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 613;BA.debugLine="SetSnackBarBackground(snack, GlobalVar.PriColor)";
_setsnackbarbackground(mostCurrent._snack,(int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 614;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 615;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 };
 //BA.debugLineNum = 617;BA.debugLine="Log(\"Permissions OK\")";
anywheresoftware.b4a.keywords.Common.LogImpl("121233672","Permissions OK",0);
 //BA.debugLineNum = 619;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
anywheresoftware.b4j.object.JavaObject _jo = null;
anywheresoftware.b4a.phone.Phone _ph = null;
 //BA.debugLineNum = 557;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 558;BA.debugLine="SetButtonColors";
_setbuttoncolors();
 //BA.debugLineNum = 559;BA.debugLine="pnlSearch.Visible=False";
mostCurrent._pnlsearch.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 560;BA.debugLine="pnlUnusual.Visible = False";
mostCurrent._pnlunusual.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 562;BA.debugLine="pnlNegativeMsgBox.Visible = False";
mostCurrent._pnlnegativemsgbox.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 563;BA.debugLine="pnlHiMsgBox.Visible = False";
mostCurrent._pnlhimsgbox.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 564;BA.debugLine="pnlHighBillConfirmation.Visible = False";
mostCurrent._pnlhighbillconfirmation.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 565;BA.debugLine="pnlLowMsgBox.Visible = False";
mostCurrent._pnllowmsgbox.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 567;BA.debugLine="Dim jo As JavaObject";
_jo = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 568;BA.debugLine="Dim ph As Phone";
_ph = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 570;BA.debugLine="jo.InitializeNewInstance(\"android.media.SoundPool";
_jo.InitializeNewInstance("android.media.SoundPool",new Object[]{(Object)(4),(Object)(_ph.VOLUME_ALARM),(Object)(0)});
 //BA.debugLineNum = 571;BA.debugLine="soundsAlarmChannel = jo";
_soundsalarmchannel = (anywheresoftware.b4a.audio.SoundPoolWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.audio.SoundPoolWrapper(), (android.media.SoundPool)(_jo.getObject()));
 //BA.debugLineNum = 572;BA.debugLine="SoundID = soundsAlarmChannel.Load(File.DirAssets,";
_soundid = _soundsalarmchannel.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"beep.wav");
 //BA.debugLineNum = 573;BA.debugLine="BTAdmin.Initialize(\"Admin\")";
_btadmin.Initialize(processBA,"Admin");
 //BA.debugLineNum = 574;BA.debugLine="Serial1.Initialize(\"Printer\")";
_serial1.Initialize("Printer");
 //BA.debugLineNum = 575;BA.debugLine="vibratePattern = Array As Long(500, 500, 300, 500";
_vibratepattern = new long[]{(long) (500),(long) (500),(long) (300),(long) (500)};
 //BA.debugLineNum = 576;BA.debugLine="IMEkeyboard.SetLengthFilter(txtPresRdg, 10)";
mostCurrent._imekeyboard.SetLengthFilter((android.widget.EditText)(mostCurrent._txtpresrdg.getObject()),(int) (10));
 //BA.debugLineNum = 577;BA.debugLine="txtPresRdg.InputType = txtPresRdg.INPUT_TYPE_NUMB";
mostCurrent._txtpresrdg.setInputType(mostCurrent._txtpresrdg.INPUT_TYPE_NUMBERS);
 //BA.debugLineNum = 579;BA.debugLine="sBillYear = GlobalVar.BillYear";
mostCurrent._sbillyear = BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ );
 //BA.debugLineNum = 580;BA.debugLine="If GlobalVar.SF.Len(GlobalVar.BillMonth) = 1 Then";
if (mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(BA.NumberToString(mostCurrent._globalvar._billmonth /*int*/ ))==1) { 
 //BA.debugLineNum = 581;BA.debugLine="sBillMonth = \"0\" & GlobalVar.BillMonth";
mostCurrent._sbillmonth = "0"+BA.NumberToString(mostCurrent._globalvar._billmonth /*int*/ );
 }else {
 //BA.debugLineNum = 583;BA.debugLine="sBillMonth = GlobalVar.BillMonth";
mostCurrent._sbillmonth = BA.NumberToString(mostCurrent._globalvar._billmonth /*int*/ );
 };
 //BA.debugLineNum = 586;BA.debugLine="pnlNegativeMsgBox.Visible = False";
mostCurrent._pnlnegativemsgbox.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 587;BA.debugLine="InpTyp.SetInputType(txtRemarks,Array As Int(InpTy";
_inptyp._setinputtype /*String*/ (mostCurrent._txtremarks,new int[]{_inptyp._type_class_text /*int*/ (),_inptyp._type_text_flag_auto_correct /*int*/ (),_inptyp._type_text_flag_cap_sentences /*int*/ ()});
 //BA.debugLineNum = 588;BA.debugLine="InpTyp.SetInputType(txtOthers,Array As Int(InpTyp";
_inptyp._setinputtype /*String*/ (mostCurrent._txtothers,new int[]{_inptyp._type_class_text /*int*/ (),_inptyp._type_text_flag_auto_correct /*int*/ (),_inptyp._type_text_flag_cap_sentences /*int*/ ()});
 //BA.debugLineNum = 589;BA.debugLine="InpTyp.SetInputType(txtReason,Array As Int(InpTyp";
_inptyp._setinputtype /*String*/ (mostCurrent._txtreason,new int[]{_inptyp._type_class_text /*int*/ (),_inptyp._type_text_flag_auto_correct /*int*/ (),_inptyp._type_text_flag_cap_sentences /*int*/ ()});
 //BA.debugLineNum = 591;BA.debugLine="QR.Initialize(150)";
mostCurrent._qr._initialize /*String*/ (processBA,(int) (150));
 //BA.debugLineNum = 592;BA.debugLine="T1.Initialize(\"Timer1\",100)";
mostCurrent._t1.Initialize(processBA,"Timer1",(long) (100));
 //BA.debugLineNum = 593;BA.debugLine="T1.Enabled = False";
mostCurrent._t1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 594;BA.debugLine="LogColor(GlobalVar.isAverage, Colors.Blue)";
anywheresoftware.b4a.keywords.Common.LogImpl("121102629",BA.NumberToString(mostCurrent._globalvar._isaverage /*int*/ ),anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 595;BA.debugLine="LogColor($\"Boolean Average: \"$ & GlobalVar.blnAve";
anywheresoftware.b4a.keywords.Common.LogImpl("121102630",("Boolean Average: ")+BA.ObjectToString(mostCurrent._globalvar._blnaverage /*boolean*/ ),anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 597;BA.debugLine="If GlobalVar.blnAverage = True And GlobalVar.isAv";
if (mostCurrent._globalvar._blnaverage /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True && mostCurrent._globalvar._isaverage /*int*/ ==1) { 
_showaveragebillremarks();};
 //BA.debugLineNum = 598;BA.debugLine="intSaveOnly = 0";
_intsaveonly = (int) (0);
 //BA.debugLineNum = 599;BA.debugLine="blnSuperHighBill = False";
_blnsuperhighbill = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 601;BA.debugLine="If TTS1.IsInitialized = False Then";
if (_tts1.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 602;BA.debugLine="TTS1.Initialize(\"TTS1\")";
_tts1.Initialize(processBA,"TTS1");
 };
 //BA.debugLineNum = 604;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper  _addbadgetoicon(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bmp,int _number) throws Exception{
anywheresoftware.b4a.objects.drawable.CanvasWrapper _cvs = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _mbmp = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper _target = null;
 //BA.debugLineNum = 673;BA.debugLine="Sub AddBadgeToIcon(bmp As Bitmap, Number As Int) A";
 //BA.debugLineNum = 674;BA.debugLine="Dim cvs As Canvas";
_cvs = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 675;BA.debugLine="Dim mbmp As Bitmap";
_mbmp = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 676;BA.debugLine="mbmp.InitializeMutable(32dip, 32dip)";
_mbmp.InitializeMutable(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (32)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (32)));
 //BA.debugLineNum = 677;BA.debugLine="cvs.Initialize2(mbmp)";
_cvs.Initialize2((android.graphics.Bitmap)(_mbmp.getObject()));
 //BA.debugLineNum = 678;BA.debugLine="Dim target As Rect";
_target = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper();
 //BA.debugLineNum = 679;BA.debugLine="target.Initialize(0, 0, mbmp.Width, mbmp.Height)";
_target.Initialize((int) (0),(int) (0),_mbmp.getWidth(),_mbmp.getHeight());
 //BA.debugLineNum = 680;BA.debugLine="cvs.DrawBitmap(bmp, Null, target)";
_cvs.DrawBitmap((android.graphics.Bitmap)(_bmp.getObject()),(android.graphics.Rect)(anywheresoftware.b4a.keywords.Common.Null),(android.graphics.Rect)(_target.getObject()));
 //BA.debugLineNum = 682;BA.debugLine="If Number > 0 Then";
if (_number>0) { 
 //BA.debugLineNum = 683;BA.debugLine="cvs.DrawCircle(mbmp.Width - 8dip, 8dip, 8dip, Co";
_cvs.DrawCircle((float) (_mbmp.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (8))),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (8))),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (8))),anywheresoftware.b4a.keywords.Common.Colors.Red,anywheresoftware.b4a.keywords.Common.True,(float) (0));
 //BA.debugLineNum = 684;BA.debugLine="cvs.DrawText(Min(Number, 100), mbmp.Width - 8dip";
_cvs.DrawText(mostCurrent.activityBA,BA.NumberToString(anywheresoftware.b4a.keywords.Common.Min(_number,100)),(float) (_mbmp.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (8))),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (12))),anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD,(float) (12),anywheresoftware.b4a.keywords.Common.Colors.White,BA.getEnumFromString(android.graphics.Paint.Align.class,"CENTER"));
 };
 //BA.debugLineNum = 686;BA.debugLine="Return mbmp";
if (true) return _mbmp;
 //BA.debugLineNum = 687;BA.debugLine="End Sub";
return null;
}
public static String  _addremarksdialog_buttonpressed(de.amberhome.materialdialogs.MaterialDialogWrapper _dialog,String _action) throws Exception{
 //BA.debugLineNum = 2351;BA.debugLine="Sub AddRemarksDialog_ButtonPressed (Dialog As Mate";
 //BA.debugLineNum = 2352;BA.debugLine="Select Case Action";
switch (BA.switchObjectToInt(_action,_dialog.ACTION_POSITIVE,_dialog.ACTION_NEGATIVE)) {
case 0: {
 //BA.debugLineNum = 2354;BA.debugLine="intCurrPos = rsLoadedBook.Position";
_intcurrpos = mostCurrent._rsloadedbook.getPosition();
 //BA.debugLineNum = 2355;BA.debugLine="SaveNewRemarks(ReadRemarks, ReadID, AcctID)";
_savenewremarks(mostCurrent._readremarks,_readid,_acctid);
 //BA.debugLineNum = 2356;BA.debugLine="If RetrieveRecords(GlobalVar.BookID)=False Then";
if (_retrieverecords(mostCurrent._globalvar._bookid /*int*/ )==anywheresoftware.b4a.keywords.Common.False) { 
if (true) return "";};
 //BA.debugLineNum = 2357;BA.debugLine="If intCurrPos < (RecCount - 1) Then";
if (_intcurrpos<(_reccount-1)) { 
 //BA.debugLineNum = 2358;BA.debugLine="rsLoadedBook.Position = intCurrPos + 1";
mostCurrent._rsloadedbook.setPosition((int) (_intcurrpos+1));
 }else {
 //BA.debugLineNum = 2360;BA.debugLine="rsLoadedBook.Position = intCurrPos";
mostCurrent._rsloadedbook.setPosition(_intcurrpos);
 };
 //BA.debugLineNum = 2362;BA.debugLine="DisplayRecord";
_displayrecord();
 //BA.debugLineNum = 2363;BA.debugLine="ButtonState";
_buttonstate();
 break; }
case 1: {
 //BA.debugLineNum = 2365;BA.debugLine="snack.Initialize(\"\", Activity, $\"Adding Reading";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Adding Reading Remarks Cancelled."),mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 2366;BA.debugLine="SetSnackBarBackground(snack, Colors.White)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 2367;BA.debugLine="SetSnackBarTextColor(snack, Colors.Red)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 2368;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 2369;BA.debugLine="Return";
if (true) return "";
 break; }
}
;
 //BA.debugLineNum = 2371;BA.debugLine="End Sub";
return "";
}
public static String  _addremarksdialog_inputchanged(de.amberhome.materialdialogs.MaterialDialogWrapper _mdialog,String _sremarks) throws Exception{
 //BA.debugLineNum = 2340;BA.debugLine="Sub AddRemarksDialog_InputChanged (mDialog As Mate";
 //BA.debugLineNum = 2341;BA.debugLine="If sRemarks.Length=0 Then";
if (_sremarks.length()==0) { 
 //BA.debugLineNum = 2342;BA.debugLine="mDialog.Content=\"Nothing to save.\"";
_mdialog.setContent(BA.ObjectToCharSequence("Nothing to save."));
 //BA.debugLineNum = 2343;BA.debugLine="mDialog.EnableActionButton(mDialog.ACTION_POSITI";
_mdialog.EnableActionButton(_mdialog.ACTION_POSITIVE,anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 2345;BA.debugLine="mDialog.Content=\"Please Enter a Remarks for this";
_mdialog.setContent(BA.ObjectToCharSequence("Please Enter a Remarks for this Account."));
 //BA.debugLineNum = 2346;BA.debugLine="mDialog.EnableActionButton(mDialog.ACTION_POSITI";
_mdialog.EnableActionButton(_mdialog.ACTION_POSITIVE,anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 2348;BA.debugLine="ReadRemarks = sRemarks";
mostCurrent._readremarks = _sremarks;
 //BA.debugLineNum = 2349;BA.debugLine="End Sub";
return "";
}
public static String  _addwhitespaces(String _svalue) throws Exception{
String _sretval = "";
int _ilen = 0;
String _sspace = "";
int _addspace = 0;
 //BA.debugLineNum = 4682;BA.debugLine="Private Sub AddWhiteSpaces(sValue As String) As St";
 //BA.debugLineNum = 4683;BA.debugLine="Dim sRetVal As String";
_sretval = "";
 //BA.debugLineNum = 4684;BA.debugLine="Dim iLen As Int";
_ilen = 0;
 //BA.debugLineNum = 4685;BA.debugLine="Dim sSpace As String";
_sspace = "";
 //BA.debugLineNum = 4686;BA.debugLine="Dim AddSpace As Int";
_addspace = 0;
 //BA.debugLineNum = 4687;BA.debugLine="Try";
try { //BA.debugLineNum = 4689;BA.debugLine="iLen = sValue.Length";
_ilen = _svalue.length();
 //BA.debugLineNum = 4690;BA.debugLine="AddSpace = 32 - (19 + iLen)";
_addspace = (int) (32-(19+_ilen));
 //BA.debugLineNum = 4691;BA.debugLine="sSpace = RepeatChar(\" \", AddSpace)";
_sspace = _repeatchar(" ",_addspace);
 //BA.debugLineNum = 4692;BA.debugLine="sRetVal = sSpace";
_sretval = _sspace;
 } 
       catch (Exception e11) {
			processBA.setLastException(e11); //BA.debugLineNum = 4694;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("127983884",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 4696;BA.debugLine="Return sRetVal";
if (true) return _sretval;
 //BA.debugLineNum = 4697;BA.debugLine="End Sub";
return "";
}
public static String  _addwhitespaces2(String _svalue) throws Exception{
String _sretval = "";
int _ilen = 0;
String _sspace = "";
int _addspace = 0;
 //BA.debugLineNum = 4699;BA.debugLine="Private Sub AddWhiteSpaces2(sValue As String) As S";
 //BA.debugLineNum = 4700;BA.debugLine="Dim sRetVal As String";
_sretval = "";
 //BA.debugLineNum = 4701;BA.debugLine="Dim iLen As Int";
_ilen = 0;
 //BA.debugLineNum = 4702;BA.debugLine="Dim sSpace As String";
_sspace = "";
 //BA.debugLineNum = 4703;BA.debugLine="Dim AddSpace As Int";
_addspace = 0;
 //BA.debugLineNum = 4704;BA.debugLine="Try";
try { //BA.debugLineNum = 4706;BA.debugLine="iLen = sValue.Length";
_ilen = _svalue.length();
 //BA.debugLineNum = 4707;BA.debugLine="AddSpace = 32 - (18 + iLen)";
_addspace = (int) (32-(18+_ilen));
 //BA.debugLineNum = 4708;BA.debugLine="sSpace = RepeatChar(\" \", AddSpace)";
_sspace = _repeatchar(" ",_addspace);
 //BA.debugLineNum = 4709;BA.debugLine="sRetVal = sSpace";
_sretval = _sspace;
 } 
       catch (Exception e11) {
			processBA.setLastException(e11); //BA.debugLineNum = 4711;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("128049420",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 4713;BA.debugLine="Return sRetVal";
if (true) return _sretval;
 //BA.debugLineNum = 4714;BA.debugLine="End Sub";
return "";
}
public static String  _addwhitespaces3(String _svalue) throws Exception{
String _sretval = "";
int _ilen = 0;
String _sspace = "";
int _addspace = 0;
 //BA.debugLineNum = 4716;BA.debugLine="Private Sub AddWhiteSpaces3(sValue As String) As S";
 //BA.debugLineNum = 4717;BA.debugLine="Dim sRetVal As String";
_sretval = "";
 //BA.debugLineNum = 4718;BA.debugLine="Dim iLen As Int";
_ilen = 0;
 //BA.debugLineNum = 4719;BA.debugLine="Dim sSpace As String";
_sspace = "";
 //BA.debugLineNum = 4720;BA.debugLine="Dim AddSpace As Int";
_addspace = 0;
 //BA.debugLineNum = 4721;BA.debugLine="Try";
try { //BA.debugLineNum = 4723;BA.debugLine="iLen = sValue.Length";
_ilen = _svalue.length();
 //BA.debugLineNum = 4724;BA.debugLine="AddSpace = 24 - (iLen)";
_addspace = (int) (24-(_ilen));
 //BA.debugLineNum = 4725;BA.debugLine="sSpace = RepeatChar(\" \", AddSpace)";
_sspace = _repeatchar(" ",_addspace);
 //BA.debugLineNum = 4726;BA.debugLine="sRetVal = sSpace";
_sretval = _sspace;
 } 
       catch (Exception e11) {
			processBA.setLastException(e11); //BA.debugLineNum = 4728;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("128114956",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 4730;BA.debugLine="Return sRetVal";
if (true) return _sretval;
 //BA.debugLineNum = 4731;BA.debugLine="End Sub";
return "";
}
public static String  _avedialogunavailable_buttonpressed(de.amberhome.materialdialogs.MaterialDialogWrapper _mdialog,String _saction) throws Exception{
 //BA.debugLineNum = 5507;BA.debugLine="Sub AveDialogUnavailable_ButtonPressed (mDialog As";
 //BA.debugLineNum = 5508;BA.debugLine="Select Case sAction";
switch (BA.switchObjectToInt(_saction,_mdialog.ACTION_POSITIVE,_mdialog.ACTION_NEGATIVE)) {
case 0: {
 //BA.debugLineNum = 5510;BA.debugLine="vibration.vibrateCancel";
mostCurrent._vibration.vibrateCancel(processBA);
 //BA.debugLineNum = 5511;BA.debugLine="soundsAlarmChannel.Stop(SoundID)";
_soundsalarmchannel.Stop(_soundid);
 //BA.debugLineNum = 5512;BA.debugLine="TTS1.Stop";
_tts1.Stop();
 //BA.debugLineNum = 5513;BA.debugLine="Return";
if (true) return "";
 break; }
case 1: {
 //BA.debugLineNum = 5515;BA.debugLine="vibration.vibrateCancel";
mostCurrent._vibration.vibrateCancel(processBA);
 //BA.debugLineNum = 5516;BA.debugLine="soundsAlarmChannel.Stop(SoundID)";
_soundsalarmchannel.Stop(_soundid);
 //BA.debugLineNum = 5517;BA.debugLine="TTS1.Stop";
_tts1.Stop();
 //BA.debugLineNum = 5518;BA.debugLine="Return";
if (true) return "";
 break; }
}
;
 //BA.debugLineNum = 5520;BA.debugLine="End Sub";
return "";
}
public static String  _billreprint_click() throws Exception{
 //BA.debugLineNum = 1683;BA.debugLine="Private Sub BillReprint_Click()";
 //BA.debugLineNum = 1684;BA.debugLine="ProgressDialogShow2($\"Bill Statement Printing...\"";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence(("Bill Statement Printing...")),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1685;BA.debugLine="UpdatePrintStatus(ReadID, AcctID)";
_updateprintstatus(_readid,_acctid);
 //BA.debugLineNum = 1686;BA.debugLine="PrintBillData(ReadID)";
_printbilldata(_readid);
 //BA.debugLineNum = 1687;BA.debugLine="If RetrieveRecords(GlobalVar.BookID) = True Then";
if (_retrieverecords(mostCurrent._globalvar._bookid /*int*/ )==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1688;BA.debugLine="rsLoadedBook.Position = intCurrPos";
mostCurrent._rsloadedbook.setPosition(_intcurrpos);
 //BA.debugLineNum = 1689;BA.debugLine="DisplayRecord";
_displayrecord();
 //BA.debugLineNum = 1690;BA.debugLine="ButtonState";
_buttonstate();
 };
 //BA.debugLineNum = 1692;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.drawable.BitmapDrawable  _bitmaptobitmapdrawable(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bitmap) throws Exception{
anywheresoftware.b4a.objects.drawable.BitmapDrawable _bd = null;
 //BA.debugLineNum = 694;BA.debugLine="Sub BitmapToBitmapDrawable (bitmap As Bitmap) As B";
 //BA.debugLineNum = 695;BA.debugLine="Dim bd As BitmapDrawable";
_bd = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 696;BA.debugLine="bd.Initialize(bitmap)";
_bd.Initialize((android.graphics.Bitmap)(_bitmap.getObject()));
 //BA.debugLineNum = 697;BA.debugLine="Return bd";
if (true) return _bd;
 //BA.debugLineNum = 698;BA.debugLine="End Sub";
return null;
}
public static String  _btnavecancelpost_click() throws Exception{
 //BA.debugLineNum = 5461;BA.debugLine="Sub btnAveCancelPost_Click";
 //BA.debugLineNum = 5462;BA.debugLine="soundsAlarmChannel.Stop(SoundID)";
_soundsalarmchannel.Stop(_soundid);
 //BA.debugLineNum = 5463;BA.debugLine="vibration.vibrateCancel";
mostCurrent._vibration.vibrateCancel(processBA);
 //BA.debugLineNum = 5464;BA.debugLine="pnlAveMsgBox.Visible = False";
mostCurrent._pnlavemsgbox.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 5466;BA.debugLine="snack.Initialize(\"\", Activity, $\"Posting Average";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Posting Average Reading Cancelled."),mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 5467;BA.debugLine="SetSnackBarBackground(snack, Colors.White)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 5468;BA.debugLine="SetSnackBarTextColor(snack, Colors.Red)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 5469;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 5470;BA.debugLine="GlobalVar.isAverage = 0";
mostCurrent._globalvar._isaverage /*int*/  = (int) (0);
 //BA.debugLineNum = 5471;BA.debugLine="GlobalVar.blnAverage = False";
mostCurrent._globalvar._blnaverage /*boolean*/  = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 5472;BA.debugLine="End Sub";
return "";
}
public static String  _btnaveremcancelpost_click() throws Exception{
 //BA.debugLineNum = 5603;BA.debugLine="Sub btnAveRemCancelPost_Click";
 //BA.debugLineNum = 5604;BA.debugLine="pnlAveRemMsgBox.Visible = False";
mostCurrent._pnlaveremmsgbox.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 5605;BA.debugLine="snack.Initialize(\"\", Activity, $\"Average Bill Pos";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Average Bill Posting Cancelled."),mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 5606;BA.debugLine="SetSnackBarBackground(snack, Colors.White)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 5607;BA.debugLine="SetSnackBarTextColor(snack, Colors.Red)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 5608;BA.debugLine="IMEkeyboard.HideKeyboard";
mostCurrent._imekeyboard.HideKeyboard(mostCurrent.activityBA);
 //BA.debugLineNum = 5609;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 5610;BA.debugLine="GlobalVar.isAverage = 0";
mostCurrent._globalvar._isaverage /*int*/  = (int) (0);
 //BA.debugLineNum = 5611;BA.debugLine="GlobalVar.blnAverage = False";
mostCurrent._globalvar._blnaverage /*boolean*/  = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 5612;BA.debugLine="rsLoadedBook.Position = intCurrPos";
mostCurrent._rsloadedbook.setPosition(_intcurrpos);
 //BA.debugLineNum = 5613;BA.debugLine="DisplayRecord";
_displayrecord();
 //BA.debugLineNum = 5614;BA.debugLine="ButtonState";
_buttonstate();
 //BA.debugLineNum = 5615;BA.debugLine="End Sub";
return "";
}
public static String  _btnaveremsaveandprint_click() throws Exception{
 //BA.debugLineNum = 5559;BA.debugLine="Sub btnAveRemSaveAndPrint_Click";
 //BA.debugLineNum = 5561;BA.debugLine="If optReason0.Checked = True Then";
if (mostCurrent._optreason0.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 5562;BA.debugLine="sAveRem = optReason0.Text";
mostCurrent._saverem = mostCurrent._optreason0.getText();
 }else if(mostCurrent._optreason1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 5564;BA.debugLine="sAveRem = optReason1.Text";
mostCurrent._saverem = mostCurrent._optreason1.getText();
 }else if(mostCurrent._optreason2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 5566;BA.debugLine="sAveRem = optReason2.Text";
mostCurrent._saverem = mostCurrent._optreason2.getText();
 }else if(mostCurrent._optreason3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 5568;BA.debugLine="sAveRem = optReason3.Text";
mostCurrent._saverem = mostCurrent._optreason3.getText();
 }else if(mostCurrent._optreason4.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 5570;BA.debugLine="sAveRem = optReason4.Text";
mostCurrent._saverem = mostCurrent._optreason4.getText();
 }else if(mostCurrent._optreason5.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 5572;BA.debugLine="sAveRem = optReason5.Text";
mostCurrent._saverem = mostCurrent._optreason5.getText();
 }else if(mostCurrent._optreason6.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 5574;BA.debugLine="sAveRem = optReason6.Text";
mostCurrent._saverem = mostCurrent._optreason6.getText();
 }else if(mostCurrent._optreason7.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 5576;BA.debugLine="sAveRem = optReason7.Text";
mostCurrent._saverem = mostCurrent._optreason7.getText();
 }else if(mostCurrent._optreason8.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 5578;BA.debugLine="If txtReason.Text = \"\" Or GlobalVar.SF.Len(Globa";
if ((mostCurrent._txtreason.getText()).equals("") || mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv4(mostCurrent._txtreason.getText()))<=0) { 
if (true) return "";};
 //BA.debugLineNum = 5579;BA.debugLine="sAveRem = txtReason.Text";
mostCurrent._saverem = mostCurrent._txtreason.getText();
 };
 //BA.debugLineNum = 5582;BA.debugLine="pnlAveRemMsgBox.Visible = False";
mostCurrent._pnlaveremmsgbox.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 5584;BA.debugLine="intCurrPos = rsLoadedBook.Position";
_intcurrpos = mostCurrent._rsloadedbook.getPosition();
 //BA.debugLineNum = 5585;BA.debugLine="ComputeAverageBill";
_computeaveragebill();
 //BA.debugLineNum = 5586;BA.debugLine="SaveAverageBill(1, ReadID, AcctID)";
_saveaveragebill((int) (1),_readid,_acctid);
 //BA.debugLineNum = 5587;BA.debugLine="ProgressDialogShow2($\"Bill Statement Printing...\"";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence(("Bill Statement Printing...")),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 5588;BA.debugLine="IMEkeyboard.HideKeyboard";
mostCurrent._imekeyboard.HideKeyboard(mostCurrent.activityBA);
 //BA.debugLineNum = 5589;BA.debugLine="PrintBillData(ReadID)";
_printbilldata(_readid);
 //BA.debugLineNum = 5590;BA.debugLine="If RetrieveRecords(GlobalVar.BookID)=False Then R";
if (_retrieverecords(mostCurrent._globalvar._bookid /*int*/ )==anywheresoftware.b4a.keywords.Common.False) { 
if (true) return "";};
 //BA.debugLineNum = 5591;BA.debugLine="If intCurrPos < (RecCount - 1) Then";
if (_intcurrpos<(_reccount-1)) { 
 //BA.debugLineNum = 5592;BA.debugLine="rsLoadedBook.Position = intCurrPos + 1";
mostCurrent._rsloadedbook.setPosition((int) (_intcurrpos+1));
 }else {
 //BA.debugLineNum = 5594;BA.debugLine="rsLoadedBook.Position = intCurrPos";
mostCurrent._rsloadedbook.setPosition(_intcurrpos);
 };
 //BA.debugLineNum = 5596;BA.debugLine="DisplayRecord";
_displayrecord();
 //BA.debugLineNum = 5597;BA.debugLine="ButtonState";
_buttonstate();
 //BA.debugLineNum = 5598;BA.debugLine="If GetUnusualCount(GlobalVar.BookID) = True Then";
if (_getunusualcount(mostCurrent._globalvar._bookid /*int*/ )==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 5599;BA.debugLine="UpdateBadge(\"Flag\", AddBadgeToIcon(flagBitmap, i";
_updatebadge("Flag",_addbadgetoicon(_flagbitmap,_inttotal));
 };
 //BA.debugLineNum = 5601;BA.debugLine="End Sub";
return "";
}
public static String  _btnavetakepicture_click() throws Exception{
 //BA.debugLineNum = 5453;BA.debugLine="Sub btnAveTakePicture_Click";
 //BA.debugLineNum = 5454;BA.debugLine="pnlAveMsgBox.Visible = False";
mostCurrent._pnlavemsgbox.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 5455;BA.debugLine="soundsAlarmChannel.Stop(SoundID)";
_soundsalarmchannel.Stop(_soundid);
 //BA.debugLineNum = 5456;BA.debugLine="GlobalVar.isAverage = 1";
mostCurrent._globalvar._isaverage /*int*/  = (int) (1);
 //BA.debugLineNum = 5457;BA.debugLine="Permissions.CheckAndRequest(Permissions.PERMISSIO";
_permissions.CheckAndRequest(processBA,_permissions.PERMISSION_CAMERA);
 //BA.debugLineNum = 5458;BA.debugLine="StartActivity(NewCam)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._newcam.getObject()));
 //BA.debugLineNum = 5459;BA.debugLine="End Sub";
return "";
}
public static String  _btncancelsearch_click() throws Exception{
 //BA.debugLineNum = 1998;BA.debugLine="Sub btnCancelSearch_Click";
 //BA.debugLineNum = 1999;BA.debugLine="SearchClosed";
_searchclosed();
 //BA.debugLineNum = 2000;BA.debugLine="IMEkeyboard.HideKeyboard";
mostCurrent._imekeyboard.HideKeyboard(mostCurrent.activityBA);
 //BA.debugLineNum = 2001;BA.debugLine="rsLoadedBook.Position = intTempCurrPos";
mostCurrent._rsloadedbook.setPosition(_inttempcurrpos);
 //BA.debugLineNum = 2002;BA.debugLine="DisplayRecord";
_displayrecord();
 //BA.debugLineNum = 2003;BA.debugLine="ButtonState";
_buttonstate();
 //BA.debugLineNum = 2004;BA.debugLine="End Sub";
return "";
}
public static String  _btncancelunusual_click() throws Exception{
 //BA.debugLineNum = 5309;BA.debugLine="Sub btnCancelUnusual_Click";
 //BA.debugLineNum = 5310;BA.debugLine="SearchClosed";
_searchclosed();
 //BA.debugLineNum = 5311;BA.debugLine="rsLoadedBook.Position = intTempCurrPos";
mostCurrent._rsloadedbook.setPosition(_inttempcurrpos);
 //BA.debugLineNum = 5312;BA.debugLine="DisplayRecord";
_displayrecord();
 //BA.debugLineNum = 5313;BA.debugLine="ButtonState";
_buttonstate();
 //BA.debugLineNum = 5314;BA.debugLine="End Sub";
return "";
}
public static String  _btnedit_click() throws Exception{
 //BA.debugLineNum = 1748;BA.debugLine="Sub btnEdit_Click";
 //BA.debugLineNum = 1749;BA.debugLine="If WasRead = 0 Then Return";
if (_wasread==0) { 
if (true) return "";};
 //BA.debugLineNum = 1750;BA.debugLine="If WasMisCoded = 1 Then";
if (_wasmiscoded==1) { 
 //BA.debugLineNum = 1751;BA.debugLine="ShowEditDialog";
_showeditdialog();
 //BA.debugLineNum = 1752;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 1754;BA.debugLine="blnEdit = True";
_blnedit = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 1755;BA.debugLine="txtPresRdg.Enabled=True";
mostCurrent._txtpresrdg.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1756;BA.debugLine="txtPresRdg.RequestFocus";
mostCurrent._txtpresrdg.RequestFocus();
 //BA.debugLineNum = 1757;BA.debugLine="txtPresRdg.SelectionStart=0";
mostCurrent._txtpresrdg.setSelectionStart((int) (0));
 //BA.debugLineNum = 1758;BA.debugLine="txtPresRdg.SelectAll";
mostCurrent._txtpresrdg.SelectAll();
 //BA.debugLineNum = 1760;BA.debugLine="btnEdit.Enabled = False";
mostCurrent._btnedit.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1761;BA.debugLine="btnReprint.Enabled = False";
mostCurrent._btnreprint.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1762;BA.debugLine="btnPrevious.Enabled = False";
mostCurrent._btnprevious.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1763;BA.debugLine="btnNext.Enabled = False";
mostCurrent._btnnext.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1764;BA.debugLine="End Sub";
return "";
}
public static String  _btnhbconfirmcancel_click() throws Exception{
 //BA.debugLineNum = 5109;BA.debugLine="Sub btnHBConfirmCancel_Click";
 //BA.debugLineNum = 5110;BA.debugLine="TTS1.Stop";
_tts1.Stop();
 //BA.debugLineNum = 5111;BA.debugLine="soundsAlarmChannel.Stop(SoundID)";
_soundsalarmchannel.Stop(_soundid);
 //BA.debugLineNum = 5112;BA.debugLine="vibration.vibrateCancel";
mostCurrent._vibration.vibrateCancel(processBA);
 //BA.debugLineNum = 5113;BA.debugLine="pnlHighBillConfirmation.Visible = False";
mostCurrent._pnlhighbillconfirmation.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 5114;BA.debugLine="txtPresRdg.RequestFocus";
mostCurrent._txtpresrdg.RequestFocus();
 //BA.debugLineNum = 5115;BA.debugLine="txtPresRdg.SelectAll";
mostCurrent._txtpresrdg.SelectAll();
 //BA.debugLineNum = 5116;BA.debugLine="IMEkeyboard.HideKeyboard";
mostCurrent._imekeyboard.HideKeyboard(mostCurrent.activityBA);
 //BA.debugLineNum = 5119;BA.debugLine="intSaveOnly = 0";
_intsaveonly = (int) (0);
 //BA.debugLineNum = 5120;BA.debugLine="blnSuperHighBill = False";
_blnsuperhighbill = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 5121;BA.debugLine="End Sub";
return "";
}
public static String  _btnhbconfirmreprintcancel_click() throws Exception{
 //BA.debugLineNum = 5853;BA.debugLine="Sub btnHBConfirmReprintCancel_Click";
 //BA.debugLineNum = 5855;BA.debugLine="End Sub";
return "";
}
public static String  _btnhbconfirmsaveandprint_click() throws Exception{
 //BA.debugLineNum = 5071;BA.debugLine="Sub btnHBConfirmSaveAndPrint_Click";
 //BA.debugLineNum = 5072;BA.debugLine="pnlHighBillConfirmation.Visible = False";
mostCurrent._pnlhighbillconfirmation.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 5073;BA.debugLine="TTS1.Stop";
_tts1.Stop();
 //BA.debugLineNum = 5074;BA.debugLine="vibration.vibrateCancel";
mostCurrent._vibration.vibrateCancel(processBA);
 //BA.debugLineNum = 5075;BA.debugLine="soundsAlarmChannel.Stop(SoundID)";
_soundsalarmchannel.Stop(_soundid);
 //BA.debugLineNum = 5077;BA.debugLine="intCurrPos = rsLoadedBook.Position";
_intcurrpos = mostCurrent._rsloadedbook.getPosition();
 //BA.debugLineNum = 5078;BA.debugLine="Select Case intSaveOnly";
switch (_intsaveonly) {
case 0: {
 //BA.debugLineNum = 5080;BA.debugLine="If blnSuperHighBill = True Then";
if (_blnsuperhighbill==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 5081;BA.debugLine="ShowSuperHBConfirmation";
_showsuperhbconfirmation();
 //BA.debugLineNum = 5082;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 5084;BA.debugLine="SaveReading(0, ReadID, AcctID)";
_savereading((int) (0),_readid,_acctid);
 break; }
case 1: {
 //BA.debugLineNum = 5086;BA.debugLine="If blnSuperHighBill = True Then";
if (_blnsuperhighbill==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 5087;BA.debugLine="ShowSuperHBConfirmation";
_showsuperhbconfirmation();
 //BA.debugLineNum = 5088;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 5090;BA.debugLine="SaveReading(1, ReadID, AcctID)";
_savereading((int) (1),_readid,_acctid);
 //BA.debugLineNum = 5091;BA.debugLine="PrintBillData(ReadID)";
_printbilldata(_readid);
 break; }
}
;
 //BA.debugLineNum = 5094;BA.debugLine="If RetrieveRecords(GlobalVar.BookID)=False Then R";
if (_retrieverecords(mostCurrent._globalvar._bookid /*int*/ )==anywheresoftware.b4a.keywords.Common.False) { 
if (true) return "";};
 //BA.debugLineNum = 5095;BA.debugLine="If intCurrPos < (RecCount - 1) Then";
if (_intcurrpos<(_reccount-1)) { 
 //BA.debugLineNum = 5096;BA.debugLine="rsLoadedBook.Position = intCurrPos + 1";
mostCurrent._rsloadedbook.setPosition((int) (_intcurrpos+1));
 }else {
 //BA.debugLineNum = 5098;BA.debugLine="rsLoadedBook.Position = intCurrPos";
mostCurrent._rsloadedbook.setPosition(_intcurrpos);
 };
 //BA.debugLineNum = 5100;BA.debugLine="DisplayRecord";
_displayrecord();
 //BA.debugLineNum = 5101;BA.debugLine="ButtonState";
_buttonstate();
 //BA.debugLineNum = 5102;BA.debugLine="If GetUnusualCount(GlobalVar.BookID) = True Then";
if (_getunusualcount(mostCurrent._globalvar._bookid /*int*/ )==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 5103;BA.debugLine="UpdateBadge(\"Flag\", AddBadgeToIcon(flagBitmap, i";
_updatebadge("Flag",_addbadgetoicon(_flagbitmap,_inttotal));
 };
 //BA.debugLineNum = 5105;BA.debugLine="intSaveOnly = 0";
_intsaveonly = (int) (0);
 //BA.debugLineNum = 5106;BA.debugLine="blnSuperHighBill = False";
_blnsuperhighbill = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 5107;BA.debugLine="End Sub";
return "";
}
public static String  _btnhbconfirmsaveandreprint_click() throws Exception{
 //BA.debugLineNum = 5849;BA.debugLine="Sub btnHBConfirmSaveAndRePrint_Click";
 //BA.debugLineNum = 5851;BA.debugLine="End Sub";
return "";
}
public static String  _btnhicancelpost_click() throws Exception{
 //BA.debugLineNum = 4973;BA.debugLine="Sub btnHiCancelPost_Click";
 //BA.debugLineNum = 4974;BA.debugLine="TTS1.Stop";
_tts1.Stop();
 //BA.debugLineNum = 4975;BA.debugLine="soundsAlarmChannel.Stop(SoundID)";
_soundsalarmchannel.Stop(_soundid);
 //BA.debugLineNum = 4976;BA.debugLine="vibration.vibrateCancel";
mostCurrent._vibration.vibrateCancel(processBA);
 //BA.debugLineNum = 4977;BA.debugLine="pnlHiMsgBox.Visible = False";
mostCurrent._pnlhimsgbox.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 4978;BA.debugLine="snack.Initialize(\"\", Activity, $\"Posting Reading";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Posting Reading Cancelled."),mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 4979;BA.debugLine="SetSnackBarBackground(snack, Colors.White)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 4980;BA.debugLine="SetSnackBarTextColor(snack, Colors.Red)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 4981;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 4982;BA.debugLine="vibration.vibrateCancel";
mostCurrent._vibration.vibrateCancel(processBA);
 //BA.debugLineNum = 4983;BA.debugLine="rsLoadedBook.Position= intCurrPos";
mostCurrent._rsloadedbook.setPosition(_intcurrpos);
 //BA.debugLineNum = 4984;BA.debugLine="DisplayRecord";
_displayrecord();
 //BA.debugLineNum = 4985;BA.debugLine="ButtonState";
_buttonstate();
 //BA.debugLineNum = 4986;BA.debugLine="intSaveOnly = 0";
_intsaveonly = (int) (0);
 //BA.debugLineNum = 4987;BA.debugLine="blnSuperHighBill = False";
_blnsuperhighbill = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 4989;BA.debugLine="End Sub";
return "";
}
public static String  _btnhisaveandprint_click() throws Exception{
 //BA.debugLineNum = 5014;BA.debugLine="Sub btnHiSaveAndPrint_Click";
 //BA.debugLineNum = 5015;BA.debugLine="intSaveOnly = 1";
_intsaveonly = (int) (1);
 //BA.debugLineNum = 5016;BA.debugLine="pnlHiMsgBox.Visible = False";
mostCurrent._pnlhimsgbox.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 5017;BA.debugLine="sPresentReading = txtPresRdg.Text";
mostCurrent._spresentreading = mostCurrent._txtpresrdg.getText();
 //BA.debugLineNum = 5018;BA.debugLine="ShowHighBillConfirmation";
_showhighbillconfirmation();
 //BA.debugLineNum = 5019;BA.debugLine="End Sub";
return "";
}
public static String  _btnhisaveonly_click() throws Exception{
 //BA.debugLineNum = 4991;BA.debugLine="Sub btnHiSaveOnly_Click";
 //BA.debugLineNum = 4992;BA.debugLine="intSaveOnly = 0";
_intsaveonly = (int) (0);
 //BA.debugLineNum = 4993;BA.debugLine="pnlHiMsgBox.Visible = False";
mostCurrent._pnlhimsgbox.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 4994;BA.debugLine="sPresentReading = txtPresRdg.Text";
mostCurrent._spresentreading = mostCurrent._txtpresrdg.getText();
 //BA.debugLineNum = 4995;BA.debugLine="ShowHighBillConfirmation";
_showhighbillconfirmation();
 //BA.debugLineNum = 5012;BA.debugLine="End Sub";
return "";
}
public static String  _btnlowcancelpost_click() throws Exception{
 //BA.debugLineNum = 5189;BA.debugLine="Sub btnLowCancelPost_Click";
 //BA.debugLineNum = 5190;BA.debugLine="soundsAlarmChannel.Stop(SoundID)";
_soundsalarmchannel.Stop(_soundid);
 //BA.debugLineNum = 5191;BA.debugLine="vibration.vibrateCancel";
mostCurrent._vibration.vibrateCancel(processBA);
 //BA.debugLineNum = 5192;BA.debugLine="pnlLowMsgBox.Visible = False";
mostCurrent._pnllowmsgbox.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 5194;BA.debugLine="snack.Initialize(\"\", Activity, $\"Posting Reading";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Posting Reading Cancelled."),mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 5195;BA.debugLine="SetSnackBarBackground(snack, Colors.White)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 5196;BA.debugLine="SetSnackBarTextColor(snack, Colors.Red)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 5197;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 5198;BA.debugLine="rsLoadedBook.Position= intCurrPos";
mostCurrent._rsloadedbook.setPosition(_intcurrpos);
 //BA.debugLineNum = 5199;BA.debugLine="DisplayRecord";
_displayrecord();
 //BA.debugLineNum = 5200;BA.debugLine="ButtonState";
_buttonstate();
 //BA.debugLineNum = 5201;BA.debugLine="End Sub";
return "";
}
public static String  _btnlowsaveandprint_click() throws Exception{
 //BA.debugLineNum = 5148;BA.debugLine="Sub btnLowSaveAndPrint_Click";
 //BA.debugLineNum = 5149;BA.debugLine="TTS1.Stop";
_tts1.Stop();
 //BA.debugLineNum = 5150;BA.debugLine="pnlLowMsgBox.Visible = False";
mostCurrent._pnllowmsgbox.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 5151;BA.debugLine="vibration.vibrateCancel";
mostCurrent._vibration.vibrateCancel(processBA);
 //BA.debugLineNum = 5152;BA.debugLine="soundsAlarmChannel.Stop(SoundID)";
_soundsalarmchannel.Stop(_soundid);
 //BA.debugLineNum = 5154;BA.debugLine="intCurrPos = rsLoadedBook.Position";
_intcurrpos = mostCurrent._rsloadedbook.getPosition();
 //BA.debugLineNum = 5155;BA.debugLine="SaveReading(1, ReadID, AcctID)";
_savereading((int) (1),_readid,_acctid);
 //BA.debugLineNum = 5156;BA.debugLine="PrintBillData(ReadID)";
_printbilldata(_readid);
 //BA.debugLineNum = 5157;BA.debugLine="If RetrieveRecords(GlobalVar.BookID)=False Then R";
if (_retrieverecords(mostCurrent._globalvar._bookid /*int*/ )==anywheresoftware.b4a.keywords.Common.False) { 
if (true) return "";};
 //BA.debugLineNum = 5158;BA.debugLine="If intCurrPos < (RecCount - 1) Then";
if (_intcurrpos<(_reccount-1)) { 
 //BA.debugLineNum = 5159;BA.debugLine="rsLoadedBook.Position = intCurrPos + 1";
mostCurrent._rsloadedbook.setPosition((int) (_intcurrpos+1));
 }else {
 //BA.debugLineNum = 5161;BA.debugLine="rsLoadedBook.Position = intCurrPos";
mostCurrent._rsloadedbook.setPosition(_intcurrpos);
 };
 //BA.debugLineNum = 5163;BA.debugLine="DisplayRecord";
_displayrecord();
 //BA.debugLineNum = 5164;BA.debugLine="ButtonState";
_buttonstate();
 //BA.debugLineNum = 5165;BA.debugLine="If GetUnusualCount(GlobalVar.BookID) = True Then";
if (_getunusualcount(mostCurrent._globalvar._bookid /*int*/ )==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 5166;BA.debugLine="UpdateBadge(\"Flag\", AddBadgeToIcon(flagBitmap, i";
_updatebadge("Flag",_addbadgetoicon(_flagbitmap,_inttotal));
 };
 //BA.debugLineNum = 5168;BA.debugLine="End Sub";
return "";
}
public static String  _btnlowsaveonly_click() throws Exception{
 //BA.debugLineNum = 5170;BA.debugLine="Sub btnLowSaveOnly_Click";
 //BA.debugLineNum = 5171;BA.debugLine="pnlLowMsgBox.Visible = False";
mostCurrent._pnllowmsgbox.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 5172;BA.debugLine="vibration.vibrateCancel";
mostCurrent._vibration.vibrateCancel(processBA);
 //BA.debugLineNum = 5173;BA.debugLine="soundsAlarmChannel.Stop(SoundID)";
_soundsalarmchannel.Stop(_soundid);
 //BA.debugLineNum = 5175;BA.debugLine="SaveReading(0, ReadID, AcctID)";
_savereading((int) (0),_readid,_acctid);
 //BA.debugLineNum = 5176;BA.debugLine="If RetrieveRecords(GlobalVar.BookID)=False Then R";
if (_retrieverecords(mostCurrent._globalvar._bookid /*int*/ )==anywheresoftware.b4a.keywords.Common.False) { 
if (true) return "";};
 //BA.debugLineNum = 5177;BA.debugLine="If intCurrPos < (RecCount - 1) Then";
if (_intcurrpos<(_reccount-1)) { 
 //BA.debugLineNum = 5178;BA.debugLine="rsLoadedBook.Position = intCurrPos + 1";
mostCurrent._rsloadedbook.setPosition((int) (_intcurrpos+1));
 }else {
 //BA.debugLineNum = 5180;BA.debugLine="rsLoadedBook.Position = intCurrPos";
mostCurrent._rsloadedbook.setPosition(_intcurrpos);
 };
 //BA.debugLineNum = 5182;BA.debugLine="DisplayRecord";
_displayrecord();
 //BA.debugLineNum = 5183;BA.debugLine="ButtonState";
_buttonstate();
 //BA.debugLineNum = 5184;BA.debugLine="If GetUnusualCount(GlobalVar.BookID) = True Then";
if (_getunusualcount(mostCurrent._globalvar._bookid /*int*/ )==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 5185;BA.debugLine="UpdateBadge(\"Flag\", AddBadgeToIcon(flagBitmap, i";
_updatebadge("Flag",_addbadgetoicon(_flagbitmap,_inttotal));
 };
 //BA.debugLineNum = 5187;BA.debugLine="End Sub";
return "";
}
public static String  _btnmiscodecancelpost_click() throws Exception{
 //BA.debugLineNum = 2226;BA.debugLine="Sub btnMisCodeCancelPost_Click";
 //BA.debugLineNum = 2227;BA.debugLine="soundsAlarmChannel.Stop(SoundID)";
_soundsalarmchannel.Stop(_soundid);
 //BA.debugLineNum = 2228;BA.debugLine="vibration.vibrateCancel";
mostCurrent._vibration.vibrateCancel(processBA);
 //BA.debugLineNum = 2229;BA.debugLine="pnlMisCodeMsgBox.Visible = False";
mostCurrent._pnlmiscodemsgbox.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2231;BA.debugLine="snack.Initialize(\"\", Activity, $\"Posting Miscode";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Posting Miscode Cancelled."),mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 2232;BA.debugLine="SetSnackBarBackground(snack, Colors.White)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 2233;BA.debugLine="SetSnackBarTextColor(snack, Colors.Red)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 2234;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 2235;BA.debugLine="blnEdit = False";
_blnedit = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 2236;BA.debugLine="IMEkeyboard.HideKeyboard";
mostCurrent._imekeyboard.HideKeyboard(mostCurrent.activityBA);
 //BA.debugLineNum = 2237;BA.debugLine="End Sub";
return "";
}
public static String  _btnmiscodesave_click() throws Exception{
 //BA.debugLineNum = 2060;BA.debugLine="Sub btnMisCodeSave_Click";
 //BA.debugLineNum = 2061;BA.debugLine="If optMisCode0.Checked = True Then";
if (mostCurrent._optmiscode0.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2062;BA.debugLine="MisCodeID = 1";
_miscodeid = (int) (1);
 //BA.debugLineNum = 2063;BA.debugLine="MisCodeDesc = optMisCode0.Text";
mostCurrent._miscodedesc = mostCurrent._optmiscode0.getText();
 }else if(mostCurrent._optmiscode1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2065;BA.debugLine="MisCodeID = 2";
_miscodeid = (int) (2);
 //BA.debugLineNum = 2066;BA.debugLine="MisCodeDesc = optMisCode1.Text";
mostCurrent._miscodedesc = mostCurrent._optmiscode1.getText();
 }else if(mostCurrent._optmiscode2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2068;BA.debugLine="MisCodeID = 3";
_miscodeid = (int) (3);
 //BA.debugLineNum = 2069;BA.debugLine="MisCodeDesc = optMisCode2.Text";
mostCurrent._miscodedesc = mostCurrent._optmiscode2.getText();
 }else if(mostCurrent._optmiscode3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2071;BA.debugLine="MisCodeID = 4";
_miscodeid = (int) (4);
 //BA.debugLineNum = 2072;BA.debugLine="MisCodeDesc = optMisCode3.Text";
mostCurrent._miscodedesc = mostCurrent._optmiscode3.getText();
 }else if(mostCurrent._optmiscode4.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2074;BA.debugLine="MisCodeID = 5";
_miscodeid = (int) (5);
 //BA.debugLineNum = 2075;BA.debugLine="MisCodeDesc = optMisCode4.Text";
mostCurrent._miscodedesc = mostCurrent._optmiscode4.getText();
 }else if(mostCurrent._optmiscode5.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2077;BA.debugLine="MisCodeID = 6";
_miscodeid = (int) (6);
 //BA.debugLineNum = 2078;BA.debugLine="MisCodeDesc = optMisCode5.Text";
mostCurrent._miscodedesc = mostCurrent._optmiscode5.getText();
 }else if(mostCurrent._optmiscode6.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2080;BA.debugLine="MisCodeID = 7";
_miscodeid = (int) (7);
 //BA.debugLineNum = 2081;BA.debugLine="MisCodeDesc = optMisCode6.Text";
mostCurrent._miscodedesc = mostCurrent._optmiscode6.getText();
 }else if(mostCurrent._optmiscode7.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2083;BA.debugLine="MisCodeID = 8";
_miscodeid = (int) (8);
 //BA.debugLineNum = 2084;BA.debugLine="MisCodeDesc = optMisCode7.Text";
mostCurrent._miscodedesc = mostCurrent._optmiscode7.getText();
 }else if(mostCurrent._optmiscode8.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2086;BA.debugLine="If txtMisCode.Text = \"\" Or GlobalVar.SF.Len(Glob";
if ((mostCurrent._txtmiscode.getText()).equals("") || mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv4(mostCurrent._txtmiscode.getText()))<=0) { 
 //BA.debugLineNum = 2087;BA.debugLine="If DispErrorMsg($\"Please enter other reason to";
if (_disperrormsg(("Please enter other reason to post."),("E R R O R"))==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2088;BA.debugLine="txtMisCode.Enabled = True";
mostCurrent._txtmiscode.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2089;BA.debugLine="txtMisCode.RequestFocus";
mostCurrent._txtmiscode.RequestFocus();
 //BA.debugLineNum = 2090;BA.debugLine="IMEkeyboard.ShowKeyboard(txtMisCode)";
mostCurrent._imekeyboard.ShowKeyboard((android.view.View)(mostCurrent._txtmiscode.getObject()));
 //BA.debugLineNum = 2091;BA.debugLine="Return";
if (true) return "";
 };
 }else {
 //BA.debugLineNum = 2094;BA.debugLine="MisCodeID = 9";
_miscodeid = (int) (9);
 //BA.debugLineNum = 2095;BA.debugLine="MisCodeDesc = txtMisCode.Text";
mostCurrent._miscodedesc = mostCurrent._txtmiscode.getText();
 };
 }else {
 //BA.debugLineNum = 2098;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 2101;BA.debugLine="vibration.vibrateCancel";
mostCurrent._vibration.vibrateCancel(processBA);
 //BA.debugLineNum = 2102;BA.debugLine="soundsAlarmChannel.Stop(SoundID)";
_soundsalarmchannel.Stop(_soundid);
 //BA.debugLineNum = 2104;BA.debugLine="Select Case btnMisCodeSave.Text";
switch (BA.switchObjectToInt(mostCurrent._btnmiscodesave.getText(),"SAVE","UPDATE")) {
case 0: {
 //BA.debugLineNum = 2106;BA.debugLine="blnEdit = True";
_blnedit = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 2107;BA.debugLine="intCurrPos = rsLoadedBook.Position";
_intcurrpos = mostCurrent._rsloadedbook.getPosition();
 //BA.debugLineNum = 2108;BA.debugLine="SaveMissCodeOnly(MisCodeID, MisCodeDesc,ReadID,";
_savemisscodeonly(_miscodeid,mostCurrent._miscodedesc,_readid,_acctid);
 //BA.debugLineNum = 2109;BA.debugLine="pnlMisCodeMsgBox.Visible = False";
mostCurrent._pnlmiscodemsgbox.setVisible(anywheresoftware.b4a.keywords.Common.False);
 break; }
case 1: {
 //BA.debugLineNum = 2111;BA.debugLine="blnEdit = False";
_blnedit = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 2112;BA.debugLine="intCurrPos = rsLoadedBook.Position";
_intcurrpos = mostCurrent._rsloadedbook.getPosition();
 //BA.debugLineNum = 2113;BA.debugLine="SaveMissCodeOnly(MisCodeID, MisCodeDesc,ReadID,";
_savemisscodeonly(_miscodeid,mostCurrent._miscodedesc,_readid,_acctid);
 //BA.debugLineNum = 2114;BA.debugLine="pnlMisCodeMsgBox.Visible = False";
mostCurrent._pnlmiscodemsgbox.setVisible(anywheresoftware.b4a.keywords.Common.False);
 break; }
}
;
 //BA.debugLineNum = 2117;BA.debugLine="If RetrieveRecords(GlobalVar.BookID)=False Then R";
if (_retrieverecords(mostCurrent._globalvar._bookid /*int*/ )==anywheresoftware.b4a.keywords.Common.False) { 
if (true) return "";};
 //BA.debugLineNum = 2118;BA.debugLine="If intCurrPos < (RecCount - 1) Then";
if (_intcurrpos<(_reccount-1)) { 
 //BA.debugLineNum = 2119;BA.debugLine="rsLoadedBook.Position = intCurrPos + 1";
mostCurrent._rsloadedbook.setPosition((int) (_intcurrpos+1));
 }else {
 //BA.debugLineNum = 2121;BA.debugLine="rsLoadedBook.Position = intCurrPos";
mostCurrent._rsloadedbook.setPosition(_intcurrpos);
 };
 //BA.debugLineNum = 2123;BA.debugLine="DisplayRecord";
_displayrecord();
 //BA.debugLineNum = 2124;BA.debugLine="ButtonState";
_buttonstate();
 //BA.debugLineNum = 2126;BA.debugLine="If GetUnusualCount(GlobalVar.BookID) = True Then";
if (_getunusualcount(mostCurrent._globalvar._bookid /*int*/ )==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2127;BA.debugLine="UpdateBadge(\"Flag\", AddBadgeToIcon(flagBitmap, i";
_updatebadge("Flag",_addbadgetoicon(_flagbitmap,_inttotal));
 };
 //BA.debugLineNum = 2130;BA.debugLine="End Sub";
return "";
}
public static String  _btnmisscode_click() throws Exception{
 //BA.debugLineNum = 1732;BA.debugLine="Sub btnMissCode_Click";
 //BA.debugLineNum = 1733;BA.debugLine="intCurrPos = rsLoadedBook.Position";
_intcurrpos = mostCurrent._rsloadedbook.getPosition();
 //BA.debugLineNum = 1734;BA.debugLine="Log(btnMissCode.Text)";
anywheresoftware.b4a.keywords.Common.LogImpl("123330818",mostCurrent._btnmisscode.getText(),0);
 //BA.debugLineNum = 1735;BA.debugLine="If AcctStatus = \"dc\" Then";
if ((mostCurrent._acctstatus).equals("dc")) { 
 //BA.debugLineNum = 1736;BA.debugLine="DispErrorMsg($\"NOT ALLOWED!\"$, $\"Miscoding not a";
_disperrormsg(("NOT ALLOWED!"),("Miscoding not allowed for disconnected account."));
 //BA.debugLineNum = 1737;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 1739;BA.debugLine="Select Case btnMissCode.Text";
switch (BA.switchObjectToInt(mostCurrent._btnmisscode.getText(),"CHANGE MISCODE","ADD MISCODE")) {
case 0: {
 //BA.debugLineNum = 1741;BA.debugLine="blnEdit = True";
_blnedit = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 1742;BA.debugLine="ShowEditMissCodeDialog";
_showeditmisscodedialog();
 break; }
case 1: {
 //BA.debugLineNum = 1744;BA.debugLine="ShowAddMissCodeDialog";
_showaddmisscodedialog();
 break; }
}
;
 //BA.debugLineNum = 1746;BA.debugLine="End Sub";
return "";
}
public static String  _btnnegativecancelpost_click() throws Exception{
 //BA.debugLineNum = 3591;BA.debugLine="Sub btnNegativeCancelPost_Click";
 //BA.debugLineNum = 3592;BA.debugLine="TTS1.Stop";
_tts1.Stop();
 //BA.debugLineNum = 3593;BA.debugLine="soundsAlarmChannel.Stop(SoundID)";
_soundsalarmchannel.Stop(_soundid);
 //BA.debugLineNum = 3594;BA.debugLine="vibration.vibrateCancel";
mostCurrent._vibration.vibrateCancel(processBA);
 //BA.debugLineNum = 3595;BA.debugLine="pnlNegativeMsgBox.Visible = False";
mostCurrent._pnlnegativemsgbox.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3596;BA.debugLine="txtOthers.Text = \"\"";
mostCurrent._txtothers.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 3597;BA.debugLine="opt0.Checked = False";
mostCurrent._opt0.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3598;BA.debugLine="opt1.Checked = False";
mostCurrent._opt1.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3599;BA.debugLine="opt2.Checked = False";
mostCurrent._opt2.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3600;BA.debugLine="opt3.Checked = True";
mostCurrent._opt3.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 3601;BA.debugLine="opt4.Checked = False";
mostCurrent._opt4.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3602;BA.debugLine="opt5.Checked = False";
mostCurrent._opt5.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3603;BA.debugLine="opt6.Checked = False";
mostCurrent._opt6.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3604;BA.debugLine="opt7.Checked = False";
mostCurrent._opt7.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3605;BA.debugLine="opt8.Checked = False";
mostCurrent._opt8.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3607;BA.debugLine="snack.Initialize(\"\", Activity, $\"Posting Reading";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Posting Reading Cancelled."),mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 3608;BA.debugLine="SetSnackBarBackground(snack, Colors.White)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 3609;BA.debugLine="SetSnackBarTextColor(snack, Colors.Red)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 3610;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 3611;BA.debugLine="rsLoadedBook.Position= intCurrPos";
mostCurrent._rsloadedbook.setPosition(_intcurrpos);
 //BA.debugLineNum = 3612;BA.debugLine="DisplayRecord";
_displayrecord();
 //BA.debugLineNum = 3613;BA.debugLine="ButtonState";
_buttonstate();
 //BA.debugLineNum = 3614;BA.debugLine="End Sub";
return "";
}
public static String  _btnnegativeselect_click() throws Exception{
 //BA.debugLineNum = 3527;BA.debugLine="Private Sub btnNegativeSelect_Click";
 //BA.debugLineNum = 3528;BA.debugLine="If opt0.Checked = True Then";
if (mostCurrent._opt0.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 3529;BA.debugLine="MisCodeID = 1";
_miscodeid = (int) (1);
 //BA.debugLineNum = 3530;BA.debugLine="MisCodeDesc = opt0.Text";
mostCurrent._miscodedesc = mostCurrent._opt0.getText();
 }else if(mostCurrent._opt1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 3532;BA.debugLine="MisCodeID = 2";
_miscodeid = (int) (2);
 //BA.debugLineNum = 3533;BA.debugLine="MisCodeDesc = opt1.Text";
mostCurrent._miscodedesc = mostCurrent._opt1.getText();
 }else if(mostCurrent._opt2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 3535;BA.debugLine="MisCodeID = 3";
_miscodeid = (int) (3);
 //BA.debugLineNum = 3536;BA.debugLine="MisCodeDesc = opt2.Text";
mostCurrent._miscodedesc = mostCurrent._opt2.getText();
 }else if(mostCurrent._opt3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 3538;BA.debugLine="MisCodeID = 4";
_miscodeid = (int) (4);
 //BA.debugLineNum = 3539;BA.debugLine="MisCodeDesc = opt3.Text";
mostCurrent._miscodedesc = mostCurrent._opt3.getText();
 }else if(mostCurrent._opt4.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 3541;BA.debugLine="MisCodeID = 5";
_miscodeid = (int) (5);
 //BA.debugLineNum = 3542;BA.debugLine="MisCodeDesc = opt4.Text";
mostCurrent._miscodedesc = mostCurrent._opt4.getText();
 }else if(mostCurrent._opt5.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 3544;BA.debugLine="MisCodeID = 6";
_miscodeid = (int) (6);
 //BA.debugLineNum = 3545;BA.debugLine="MisCodeDesc = opt5.Text";
mostCurrent._miscodedesc = mostCurrent._opt5.getText();
 }else if(mostCurrent._opt6.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 3547;BA.debugLine="MisCodeID = 7";
_miscodeid = (int) (7);
 //BA.debugLineNum = 3548;BA.debugLine="MisCodeDesc = opt6.Text";
mostCurrent._miscodedesc = mostCurrent._opt6.getText();
 }else if(mostCurrent._opt7.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 3550;BA.debugLine="MisCodeID = 8";
_miscodeid = (int) (8);
 //BA.debugLineNum = 3551;BA.debugLine="MisCodeDesc = opt7.Text";
mostCurrent._miscodedesc = mostCurrent._opt7.getText();
 }else if(mostCurrent._opt8.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 3553;BA.debugLine="If txtOthers.Text = \"\" Or GlobalVar.SF.Len(Globa";
if ((mostCurrent._txtothers.getText()).equals("") || mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv4(mostCurrent._txtothers.getText()))<=0) { 
 //BA.debugLineNum = 3554;BA.debugLine="If DispErrorMsg($\"Please enter other reason to";
if (_disperrormsg(("Please enter other reason to post."),("E R R O R"))==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 3555;BA.debugLine="txtOthers.Enabled = True";
mostCurrent._txtothers.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 3556;BA.debugLine="txtOthers.RequestFocus";
mostCurrent._txtothers.RequestFocus();
 //BA.debugLineNum = 3557;BA.debugLine="IMEkeyboard.ShowKeyboard(txtOthers)";
mostCurrent._imekeyboard.ShowKeyboard((android.view.View)(mostCurrent._txtothers.getObject()));
 //BA.debugLineNum = 3558;BA.debugLine="Return";
if (true) return "";
 };
 }else {
 //BA.debugLineNum = 3561;BA.debugLine="MisCodeID = 9";
_miscodeid = (int) (9);
 //BA.debugLineNum = 3562;BA.debugLine="MisCodeDesc = txtOthers.Text";
mostCurrent._miscodedesc = mostCurrent._txtothers.getText();
 };
 }else {
 //BA.debugLineNum = 3565;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 3567;BA.debugLine="pnlNegativeMsgBox.Visible = False";
mostCurrent._pnlnegativemsgbox.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3568;BA.debugLine="vibration.vibrateCancel";
mostCurrent._vibration.vibrateCancel(processBA);
 //BA.debugLineNum = 3569;BA.debugLine="soundsAlarmChannel.Stop(SoundID)";
_soundsalarmchannel.Stop(_soundid);
 //BA.debugLineNum = 3571;BA.debugLine="intCurrPos = rsLoadedBook.Position";
_intcurrpos = mostCurrent._rsloadedbook.getPosition();
 //BA.debugLineNum = 3572;BA.debugLine="TTS1.Stop";
_tts1.Stop();
 //BA.debugLineNum = 3574;BA.debugLine="SaveNegativeReading(MisCodeID, MisCodeDesc,ReadID";
_savenegativereading(_miscodeid,mostCurrent._miscodedesc,_readid,_acctid);
 //BA.debugLineNum = 3575;BA.debugLine="vibration.vibrateCancel";
mostCurrent._vibration.vibrateCancel(processBA);
 //BA.debugLineNum = 3576;BA.debugLine="pnlNegativeMsgBox.Visible = False";
mostCurrent._pnlnegativemsgbox.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3578;BA.debugLine="If RetrieveRecords(GlobalVar.BookID)=False Then R";
if (_retrieverecords(mostCurrent._globalvar._bookid /*int*/ )==anywheresoftware.b4a.keywords.Common.False) { 
if (true) return "";};
 //BA.debugLineNum = 3579;BA.debugLine="If intCurrPos < (RecCount - 1) Then";
if (_intcurrpos<(_reccount-1)) { 
 //BA.debugLineNum = 3580;BA.debugLine="rsLoadedBook.Position = intCurrPos + 1";
mostCurrent._rsloadedbook.setPosition((int) (_intcurrpos+1));
 }else {
 //BA.debugLineNum = 3582;BA.debugLine="rsLoadedBook.Position = intCurrPos";
mostCurrent._rsloadedbook.setPosition(_intcurrpos);
 };
 //BA.debugLineNum = 3584;BA.debugLine="DisplayRecord";
_displayrecord();
 //BA.debugLineNum = 3585;BA.debugLine="ButtonState";
_buttonstate();
 //BA.debugLineNum = 3586;BA.debugLine="If GetUnusualCount(GlobalVar.BookID) = True Then";
if (_getunusualcount(mostCurrent._globalvar._bookid /*int*/ )==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 3587;BA.debugLine="UpdateBadge(\"Flag\", AddBadgeToIcon(flagBitmap, i";
_updatebadge("Flag",_addbadgetoicon(_flagbitmap,_inttotal));
 };
 //BA.debugLineNum = 3589;BA.debugLine="End Sub";
return "";
}
public static String  _btnnext_click() throws Exception{
 //BA.debugLineNum = 1717;BA.debugLine="Sub btnNext_Click";
 //BA.debugLineNum = 1718;BA.debugLine="If rsLoadedBook.Position = (RecCount - 1) Then Re";
if (mostCurrent._rsloadedbook.getPosition()==(_reccount-1)) { 
if (true) return "";};
 //BA.debugLineNum = 1720;BA.debugLine="rsLoadedBook.Position = rsLoadedBook.Position + 1";
mostCurrent._rsloadedbook.setPosition((int) (mostCurrent._rsloadedbook.getPosition()+1));
 //BA.debugLineNum = 1721;BA.debugLine="If BookMark = RecCount Then";
if (_bookmark==_reccount) { 
 //BA.debugLineNum = 1722;BA.debugLine="btnNext.Enabled=False";
mostCurrent._btnnext.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1723;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 1726;BA.debugLine="btnPrevious.Enabled=True";
mostCurrent._btnprevious.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1727;BA.debugLine="DisplayRecord";
_displayrecord();
 //BA.debugLineNum = 1728;BA.debugLine="ButtonState";
_buttonstate();
 //BA.debugLineNum = 1729;BA.debugLine="Log($\"Record Position: \"$ & rsLoadedBook.Position";
anywheresoftware.b4a.keywords.Common.LogImpl("123265292",("Record Position: ")+BA.NumberToString(mostCurrent._rsloadedbook.getPosition()),0);
 //BA.debugLineNum = 1730;BA.debugLine="End Sub";
return "";
}
public static String  _btnnormalcancelpost_click() throws Exception{
 //BA.debugLineNum = 5257;BA.debugLine="Sub btnNormalCancelPost_Click";
 //BA.debugLineNum = 5258;BA.debugLine="pnlNormalMsgBox.Visible = False";
mostCurrent._pnlnormalmsgbox.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 5259;BA.debugLine="snack.Initialize(\"\", Activity, $\"Posting Reading";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Posting Reading Cancelled."),mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 5260;BA.debugLine="SetSnackBarBackground(snack, Colors.White)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 5261;BA.debugLine="SetSnackBarTextColor(snack, Colors.Red)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 5262;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 5263;BA.debugLine="rsLoadedBook.Position = intCurrPos";
mostCurrent._rsloadedbook.setPosition(_intcurrpos);
 //BA.debugLineNum = 5264;BA.debugLine="DisplayRecord";
_displayrecord();
 //BA.debugLineNum = 5265;BA.debugLine="ButtonState";
_buttonstate();
 //BA.debugLineNum = 5266;BA.debugLine="End Sub";
return "";
}
public static String  _btnnormalsaveandprint_click() throws Exception{
 //BA.debugLineNum = 5222;BA.debugLine="Sub btnNormalSaveAndPrint_Click";
 //BA.debugLineNum = 5223;BA.debugLine="pnlNormalMsgBox.Visible = False";
mostCurrent._pnlnormalmsgbox.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 5224;BA.debugLine="intCurrPos = rsLoadedBook.Position";
_intcurrpos = mostCurrent._rsloadedbook.getPosition();
 //BA.debugLineNum = 5225;BA.debugLine="SaveReading(1, ReadID, AcctID)";
_savereading((int) (1),_readid,_acctid);
 //BA.debugLineNum = 5226;BA.debugLine="ProgressDialogShow2($\"Bill Statement Printing...\"";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence(("Bill Statement Printing...")),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 5227;BA.debugLine="PrintBillData(ReadID)";
_printbilldata(_readid);
 //BA.debugLineNum = 5228;BA.debugLine="If RetrieveRecords(GlobalVar.BookID)=False Then R";
if (_retrieverecords(mostCurrent._globalvar._bookid /*int*/ )==anywheresoftware.b4a.keywords.Common.False) { 
if (true) return "";};
 //BA.debugLineNum = 5229;BA.debugLine="If intCurrPos < (RecCount - 1) Then";
if (_intcurrpos<(_reccount-1)) { 
 //BA.debugLineNum = 5230;BA.debugLine="rsLoadedBook.Position = intCurrPos + 1";
mostCurrent._rsloadedbook.setPosition((int) (_intcurrpos+1));
 }else {
 //BA.debugLineNum = 5232;BA.debugLine="rsLoadedBook.Position = intCurrPos";
mostCurrent._rsloadedbook.setPosition(_intcurrpos);
 };
 //BA.debugLineNum = 5234;BA.debugLine="DisplayRecord";
_displayrecord();
 //BA.debugLineNum = 5235;BA.debugLine="ButtonState";
_buttonstate();
 //BA.debugLineNum = 5236;BA.debugLine="If GetUnusualCount(GlobalVar.BookID) = True Then";
if (_getunusualcount(mostCurrent._globalvar._bookid /*int*/ )==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 5237;BA.debugLine="UpdateBadge(\"Flag\", AddBadgeToIcon(flagBitmap, i";
_updatebadge("Flag",_addbadgetoicon(_flagbitmap,_inttotal));
 };
 //BA.debugLineNum = 5239;BA.debugLine="End Sub";
return "";
}
public static String  _btnnormalsaveonly_click() throws Exception{
 //BA.debugLineNum = 5241;BA.debugLine="Sub btnNormalSaveOnly_Click";
 //BA.debugLineNum = 5242;BA.debugLine="pnlNormalMsgBox.Visible = False";
mostCurrent._pnlnormalmsgbox.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 5243;BA.debugLine="SaveReading(0, ReadID, AcctID)";
_savereading((int) (0),_readid,_acctid);
 //BA.debugLineNum = 5244;BA.debugLine="If RetrieveRecords(GlobalVar.BookID)=False Then R";
if (_retrieverecords(mostCurrent._globalvar._bookid /*int*/ )==anywheresoftware.b4a.keywords.Common.False) { 
if (true) return "";};
 //BA.debugLineNum = 5245;BA.debugLine="If intCurrPos < (RecCount - 1) Then";
if (_intcurrpos<(_reccount-1)) { 
 //BA.debugLineNum = 5246;BA.debugLine="rsLoadedBook.Position = intCurrPos + 1";
mostCurrent._rsloadedbook.setPosition((int) (_intcurrpos+1));
 }else {
 //BA.debugLineNum = 5248;BA.debugLine="rsLoadedBook.Position = intCurrPos";
mostCurrent._rsloadedbook.setPosition(_intcurrpos);
 };
 //BA.debugLineNum = 5250;BA.debugLine="DisplayRecord";
_displayrecord();
 //BA.debugLineNum = 5251;BA.debugLine="ButtonState";
_buttonstate();
 //BA.debugLineNum = 5252;BA.debugLine="If GetUnusualCount(GlobalVar.BookID) = True Then";
if (_getunusualcount(mostCurrent._globalvar._bookid /*int*/ )==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 5253;BA.debugLine="UpdateBadge(\"Flag\", AddBadgeToIcon(flagBitmap, i";
_updatebadge("Flag",_addbadgetoicon(_flagbitmap,_inttotal));
 };
 //BA.debugLineNum = 5255;BA.debugLine="End Sub";
return "";
}
public static String  _btnprevious_click() throws Exception{
 //BA.debugLineNum = 1704;BA.debugLine="Sub btnPrevious_Click";
 //BA.debugLineNum = 1705;BA.debugLine="If rsLoadedBook.Position<=0 Then Return";
if (mostCurrent._rsloadedbook.getPosition()<=0) { 
if (true) return "";};
 //BA.debugLineNum = 1706;BA.debugLine="rsLoadedBook.Position = rsLoadedBook.Position - 1";
mostCurrent._rsloadedbook.setPosition((int) (mostCurrent._rsloadedbook.getPosition()-1));
 //BA.debugLineNum = 1707;BA.debugLine="If BookMark = 0 Then";
if (_bookmark==0) { 
 //BA.debugLineNum = 1708;BA.debugLine="btnPrevious.Enabled=False";
mostCurrent._btnprevious.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1709;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 1711;BA.debugLine="btnNext.Enabled=True";
mostCurrent._btnnext.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1712;BA.debugLine="DisplayRecord";
_displayrecord();
 //BA.debugLineNum = 1713;BA.debugLine="ButtonState";
_buttonstate();
 //BA.debugLineNum = 1714;BA.debugLine="Log($\"Record Position: \"$ & rsLoadedBook.Position";
anywheresoftware.b4a.keywords.Common.LogImpl("123199754",("Record Position: ")+BA.NumberToString(mostCurrent._rsloadedbook.getPosition()),0);
 //BA.debugLineNum = 1715;BA.debugLine="End Sub";
return "";
}
public static String  _btnremarks_click() throws Exception{
 //BA.debugLineNum = 1694;BA.debugLine="Sub btnRemarks_Click";
 //BA.debugLineNum = 1695;BA.debugLine="Select Case btnRemarks.Text";
switch (BA.switchObjectToInt(mostCurrent._btnremarks.getText(),"ADD REMARKS","CHANGE REMARKS")) {
case 0: {
 //BA.debugLineNum = 1697;BA.debugLine="ShowAddRemarksDialog";
_showaddremarksdialog();
 break; }
case 1: {
 //BA.debugLineNum = 1699;BA.debugLine="blnEdit = True";
_blnedit = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 1700;BA.debugLine="ShowEditRemarksDialog";
_showeditremarksdialog();
 break; }
}
;
 //BA.debugLineNum = 1702;BA.debugLine="End Sub";
return "";
}
public static String  _btnreprint_click() throws Exception{
 //BA.debugLineNum = 1654;BA.debugLine="Sub btnReprint_Click";
 //BA.debugLineNum = 1655;BA.debugLine="If IsPrintableData(ReadID) = False Then";
if (_isprintabledata(_readid)==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 1656;BA.debugLine="snack.Initialize(\"\", Activity,$\"No data to Repri";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("No data to Reprint!"),mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 1657;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1658;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1659;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 1660;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 1663;BA.debugLine="vibration.vibrateOnce(1000)";
mostCurrent._vibration.vibrateOnce(processBA,(long) (1000));
 //BA.debugLineNum = 1664;BA.debugLine="If PrintStatus = 1 Then";
if (_printstatus==1) { 
 //BA.debugLineNum = 1665;BA.debugLine="snack.Initialize(\"BillReprint\", Activity, $\"Repr";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"BillReprint",(android.view.View)(mostCurrent._activity.getObject()),("Reprint Bill Statement for this Account?"),mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 1666;BA.debugLine="SetSnackBarTextColor(snack, Colors.Red)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1667;BA.debugLine="SetSnackBarBackground(snack, Colors.White)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1668;BA.debugLine="snack.SetAction(\"YES\")";
mostCurrent._snack.SetAction("YES");
 //BA.debugLineNum = 1669;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 }else {
 //BA.debugLineNum = 1675;BA.debugLine="snack.Initialize(\"BillReprint\", Activity, $\"Prin";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"BillReprint",(android.view.View)(mostCurrent._activity.getObject()),("Print Bill Statement for this Account?"),mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 1676;BA.debugLine="SetSnackBarTextColor(snack, Colors.Red)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1677;BA.debugLine="SetSnackBarBackground(snack, Colors.White)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1678;BA.debugLine="snack.SetAction(\"YES\")";
mostCurrent._snack.SetAction("YES");
 //BA.debugLineNum = 1679;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 };
 //BA.debugLineNum = 1681;BA.debugLine="End Sub";
return "";
}
public static String  _btnsuperhbcancel_click() throws Exception{
 //BA.debugLineNum = 5944;BA.debugLine="Sub btnSuperHBCancel_Click";
 //BA.debugLineNum = 5945;BA.debugLine="TTS1.Stop";
_tts1.Stop();
 //BA.debugLineNum = 5946;BA.debugLine="soundsAlarmChannel.Stop(SoundID)";
_soundsalarmchannel.Stop(_soundid);
 //BA.debugLineNum = 5947;BA.debugLine="vibration.vibrateCancel";
mostCurrent._vibration.vibrateCancel(processBA);
 //BA.debugLineNum = 5948;BA.debugLine="pnlSuperHB.Visible = False";
mostCurrent._pnlsuperhb.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 5949;BA.debugLine="txtPresRdg.RequestFocus";
mostCurrent._txtpresrdg.RequestFocus();
 //BA.debugLineNum = 5950;BA.debugLine="txtPresRdg.SelectAll";
mostCurrent._txtpresrdg.SelectAll();
 //BA.debugLineNum = 5951;BA.debugLine="IMEkeyboard.HideKeyboard";
mostCurrent._imekeyboard.HideKeyboard(mostCurrent.activityBA);
 //BA.debugLineNum = 5954;BA.debugLine="intSaveOnly = 0";
_intsaveonly = (int) (0);
 //BA.debugLineNum = 5955;BA.debugLine="blnSuperHighBill = False";
_blnsuperhighbill = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 5956;BA.debugLine="End Sub";
return "";
}
public static String  _btnsuperhbsave_click() throws Exception{
 //BA.debugLineNum = 5911;BA.debugLine="Sub btnSuperHBSave_Click";
 //BA.debugLineNum = 5912;BA.debugLine="pnlSuperHB.Visible = False";
mostCurrent._pnlsuperhb.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 5913;BA.debugLine="TTS1.Stop";
_tts1.Stop();
 //BA.debugLineNum = 5915;BA.debugLine="vibration.vibrateCancel";
mostCurrent._vibration.vibrateCancel(processBA);
 //BA.debugLineNum = 5916;BA.debugLine="soundsAlarmChannel.Stop(SoundID)";
_soundsalarmchannel.Stop(_soundid);
 //BA.debugLineNum = 5918;BA.debugLine="intCurrPos = rsLoadedBook.Position";
_intcurrpos = mostCurrent._rsloadedbook.getPosition();
 //BA.debugLineNum = 5920;BA.debugLine="Select Case intSaveOnly";
switch (_intsaveonly) {
case 0: {
 //BA.debugLineNum = 5922;BA.debugLine="SaveReading(0, ReadID, AcctID)";
_savereading((int) (0),_readid,_acctid);
 break; }
case 1: {
 //BA.debugLineNum = 5924;BA.debugLine="SaveReading(1, ReadID, AcctID)";
_savereading((int) (1),_readid,_acctid);
 //BA.debugLineNum = 5925;BA.debugLine="PrintBillData(ReadID)";
_printbilldata(_readid);
 break; }
}
;
 //BA.debugLineNum = 5928;BA.debugLine="If RetrieveRecords(GlobalVar.BookID)=False Then R";
if (_retrieverecords(mostCurrent._globalvar._bookid /*int*/ )==anywheresoftware.b4a.keywords.Common.False) { 
if (true) return "";};
 //BA.debugLineNum = 5929;BA.debugLine="If intCurrPos < (RecCount - 1) Then";
if (_intcurrpos<(_reccount-1)) { 
 //BA.debugLineNum = 5930;BA.debugLine="rsLoadedBook.Position = intCurrPos + 1";
mostCurrent._rsloadedbook.setPosition((int) (_intcurrpos+1));
 }else {
 //BA.debugLineNum = 5932;BA.debugLine="rsLoadedBook.Position = intCurrPos";
mostCurrent._rsloadedbook.setPosition(_intcurrpos);
 };
 //BA.debugLineNum = 5934;BA.debugLine="DisplayRecord";
_displayrecord();
 //BA.debugLineNum = 5935;BA.debugLine="ButtonState";
_buttonstate();
 //BA.debugLineNum = 5936;BA.debugLine="If GetUnusualCount(GlobalVar.BookID) = True Then";
if (_getunusualcount(mostCurrent._globalvar._bookid /*int*/ )==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 5937;BA.debugLine="UpdateBadge(\"Flag\", AddBadgeToIcon(flagBitmap, i";
_updatebadge("Flag",_addbadgetoicon(_flagbitmap,_inttotal));
 };
 //BA.debugLineNum = 5939;BA.debugLine="intSaveOnly = 0";
_intsaveonly = (int) (0);
 //BA.debugLineNum = 5940;BA.debugLine="blnSuperHighBill = False";
_blnsuperhighbill = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 5942;BA.debugLine="End Sub";
return "";
}
public static String  _btnzerocancelpost_click() throws Exception{
 //BA.debugLineNum = 3785;BA.debugLine="Sub btnZeroCancelPost_Click";
 //BA.debugLineNum = 3786;BA.debugLine="TTS1.Stop";
_tts1.Stop();
 //BA.debugLineNum = 3787;BA.debugLine="soundsAlarmChannel.Stop(SoundID)";
_soundsalarmchannel.Stop(_soundid);
 //BA.debugLineNum = 3788;BA.debugLine="vibration.vibrateCancel";
mostCurrent._vibration.vibrateCancel(processBA);
 //BA.debugLineNum = 3789;BA.debugLine="pnlZeroMsgBox.Visible = False";
mostCurrent._pnlzeromsgbox.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3790;BA.debugLine="txtRemarks.Text = \"\"";
mostCurrent._txtremarks.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 3791;BA.debugLine="strReadRemarks = \"\"";
mostCurrent._strreadremarks = "";
 //BA.debugLineNum = 3793;BA.debugLine="snack.Initialize(\"\", Activity, $\"Adding Reading R";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Adding Reading Remarks Cancelled."),mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 3794;BA.debugLine="SetSnackBarBackground(snack, Colors.White)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 3795;BA.debugLine="SetSnackBarTextColor(snack, Colors.Red)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 3796;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 3797;BA.debugLine="vibration.vibrateCancel";
mostCurrent._vibration.vibrateCancel(processBA);
 //BA.debugLineNum = 3798;BA.debugLine="rsLoadedBook.Position= intCurrPos";
mostCurrent._rsloadedbook.setPosition(_intcurrpos);
 //BA.debugLineNum = 3799;BA.debugLine="IMEkeyboard.HideKeyboard";
mostCurrent._imekeyboard.HideKeyboard(mostCurrent.activityBA);
 //BA.debugLineNum = 3800;BA.debugLine="DisplayRecord";
_displayrecord();
 //BA.debugLineNum = 3801;BA.debugLine="ButtonState";
_buttonstate();
 //BA.debugLineNum = 3802;BA.debugLine="End Sub";
return "";
}
public static String  _btnzerosaveandprint_click() throws Exception{
 //BA.debugLineNum = 3720;BA.debugLine="Sub btnZeroSaveAndPrint_Click";
 //BA.debugLineNum = 3721;BA.debugLine="If txtRemarks.Text = \"\" Or GlobalVar.SF.Len(Globa";
if ((mostCurrent._txtremarks.getText()).equals("") || mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv4(mostCurrent._txtremarks.getText()))<=0) { 
 //BA.debugLineNum = 3722;BA.debugLine="snack.Initialize(\"\", Activity, $\"Remarks cannot";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Remarks cannot be blank!"),mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 3723;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 3724;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 3725;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 3727;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 3730;BA.debugLine="pnlZeroMsgBox.Visible = False";
mostCurrent._pnlzeromsgbox.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3731;BA.debugLine="vibration.vibrateCancel";
mostCurrent._vibration.vibrateCancel(processBA);
 //BA.debugLineNum = 3732;BA.debugLine="soundsAlarmChannel.Stop(SoundID)";
_soundsalarmchannel.Stop(_soundid);
 //BA.debugLineNum = 3733;BA.debugLine="IMEkeyboard.HideKeyboard";
mostCurrent._imekeyboard.HideKeyboard(mostCurrent.activityBA);
 //BA.debugLineNum = 3734;BA.debugLine="strReadRemarks = txtRemarks.Text";
mostCurrent._strreadremarks = mostCurrent._txtremarks.getText();
 //BA.debugLineNum = 3736;BA.debugLine="intCurrPos = rsLoadedBook.Position";
_intcurrpos = mostCurrent._rsloadedbook.getPosition();
 //BA.debugLineNum = 3737;BA.debugLine="TTS1.Stop";
_tts1.Stop();
 //BA.debugLineNum = 3739;BA.debugLine="SaveReading(1, ReadID, AcctID)";
_savereading((int) (1),_readid,_acctid);
 //BA.debugLineNum = 3740;BA.debugLine="PrintBillData(ReadID)";
_printbilldata(_readid);
 //BA.debugLineNum = 3741;BA.debugLine="If RetrieveRecords(GlobalVar.BookID)=False Then R";
if (_retrieverecords(mostCurrent._globalvar._bookid /*int*/ )==anywheresoftware.b4a.keywords.Common.False) { 
if (true) return "";};
 //BA.debugLineNum = 3742;BA.debugLine="If intCurrPos < (RecCount - 1) Then";
if (_intcurrpos<(_reccount-1)) { 
 //BA.debugLineNum = 3743;BA.debugLine="rsLoadedBook.Position = intCurrPos + 1";
mostCurrent._rsloadedbook.setPosition((int) (_intcurrpos+1));
 }else {
 //BA.debugLineNum = 3745;BA.debugLine="rsLoadedBook.Position = intCurrPos";
mostCurrent._rsloadedbook.setPosition(_intcurrpos);
 };
 //BA.debugLineNum = 3747;BA.debugLine="DisplayRecord";
_displayrecord();
 //BA.debugLineNum = 3748;BA.debugLine="ButtonState";
_buttonstate();
 //BA.debugLineNum = 3749;BA.debugLine="If GetUnusualCount(GlobalVar.BookID) = True Then";
if (_getunusualcount(mostCurrent._globalvar._bookid /*int*/ )==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 3750;BA.debugLine="UpdateBadge(\"Flag\", AddBadgeToIcon(flagBitmap, i";
_updatebadge("Flag",_addbadgetoicon(_flagbitmap,_inttotal));
 };
 //BA.debugLineNum = 3753;BA.debugLine="End Sub";
return "";
}
public static String  _btnzerosaveonly_click() throws Exception{
 //BA.debugLineNum = 3755;BA.debugLine="Sub btnZeroSaveOnly_Click";
 //BA.debugLineNum = 3756;BA.debugLine="If txtRemarks.Text = \"\" Or GlobalVar.SF.Len(Globa";
if ((mostCurrent._txtremarks.getText()).equals("") || mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv4(mostCurrent._txtremarks.getText()))<=0) { 
 //BA.debugLineNum = 3757;BA.debugLine="snack.Initialize(\"\", Activity, $\"Remarks cannot";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Remarks cannot be blank!"),mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 3758;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 3759;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 3760;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 3761;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 3764;BA.debugLine="pnlZeroMsgBox.Visible = False";
mostCurrent._pnlzeromsgbox.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3765;BA.debugLine="vibration.vibrateCancel";
mostCurrent._vibration.vibrateCancel(processBA);
 //BA.debugLineNum = 3766;BA.debugLine="soundsAlarmChannel.Stop(SoundID)";
_soundsalarmchannel.Stop(_soundid);
 //BA.debugLineNum = 3767;BA.debugLine="IMEkeyboard.HideKeyboard";
mostCurrent._imekeyboard.HideKeyboard(mostCurrent.activityBA);
 //BA.debugLineNum = 3768;BA.debugLine="strReadRemarks = txtRemarks.Text";
mostCurrent._strreadremarks = mostCurrent._txtremarks.getText();
 //BA.debugLineNum = 3769;BA.debugLine="TTS1.Stop";
_tts1.Stop();
 //BA.debugLineNum = 3771;BA.debugLine="SaveReading(0, ReadID, AcctID)";
_savereading((int) (0),_readid,_acctid);
 //BA.debugLineNum = 3772;BA.debugLine="If RetrieveRecords(GlobalVar.BookID)=False Then R";
if (_retrieverecords(mostCurrent._globalvar._bookid /*int*/ )==anywheresoftware.b4a.keywords.Common.False) { 
if (true) return "";};
 //BA.debugLineNum = 3773;BA.debugLine="If intCurrPos < (RecCount - 1) Then";
if (_intcurrpos<(_reccount-1)) { 
 //BA.debugLineNum = 3774;BA.debugLine="rsLoadedBook.Position = intCurrPos + 1";
mostCurrent._rsloadedbook.setPosition((int) (_intcurrpos+1));
 }else {
 //BA.debugLineNum = 3776;BA.debugLine="rsLoadedBook.Position = intCurrPos";
mostCurrent._rsloadedbook.setPosition(_intcurrpos);
 };
 //BA.debugLineNum = 3778;BA.debugLine="DisplayRecord";
_displayrecord();
 //BA.debugLineNum = 3779;BA.debugLine="ButtonState";
_buttonstate();
 //BA.debugLineNum = 3780;BA.debugLine="If GetUnusualCount(GlobalVar.BookID) = True Then";
if (_getunusualcount(mostCurrent._globalvar._bookid /*int*/ )==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 3781;BA.debugLine="UpdateBadge(\"Flag\", AddBadgeToIcon(flagBitmap, i";
_updatebadge("Flag",_addbadgetoicon(_flagbitmap,_inttotal));
 };
 //BA.debugLineNum = 3783;BA.debugLine="End Sub";
return "";
}
public static String  _buttonstate() throws Exception{
 //BA.debugLineNum = 1183;BA.debugLine="Sub ButtonState";
 //BA.debugLineNum = 1184;BA.debugLine="If RecCount > 0 Then";
if (_reccount>0) { 
 //BA.debugLineNum = 1185;BA.debugLine="If rsLoadedBook.Position <= 0 Then";
if (mostCurrent._rsloadedbook.getPosition()<=0) { 
 //BA.debugLineNum = 1186;BA.debugLine="btnPrevious.Enabled=False";
mostCurrent._btnprevious.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1187;BA.debugLine="btnNext.Enabled=True";
mostCurrent._btnnext.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 }else if(mostCurrent._rsloadedbook.getPosition()==_reccount-1) { 
 //BA.debugLineNum = 1189;BA.debugLine="btnPrevious.Enabled=True";
mostCurrent._btnprevious.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1190;BA.debugLine="btnNext.Enabled=False";
mostCurrent._btnnext.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 1192;BA.debugLine="btnPrevious.Enabled=True";
mostCurrent._btnprevious.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1193;BA.debugLine="btnNext.Enabled=True";
mostCurrent._btnnext.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 };
 }else {
 //BA.debugLineNum = 1196;BA.debugLine="ClearDisplay";
_cleardisplay();
 //BA.debugLineNum = 1197;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 1200;BA.debugLine="If PrintStatus = 0 Then";
if (_printstatus==0) { 
 //BA.debugLineNum = 1201;BA.debugLine="btnReprint.Text = \"PRINT\"";
mostCurrent._btnreprint.setText(BA.ObjectToCharSequence("PRINT"));
 }else {
 //BA.debugLineNum = 1203;BA.debugLine="btnReprint.Text = \"REPRINT\"";
mostCurrent._btnreprint.setText(BA.ObjectToCharSequence("REPRINT"));
 };
 //BA.debugLineNum = 1206;BA.debugLine="If WasMisCoded = 1 Then";
if (_wasmiscoded==1) { 
 //BA.debugLineNum = 1207;BA.debugLine="btnMissCode.Text = $\"CHANGE MISCODE\"$";
mostCurrent._btnmisscode.setText(BA.ObjectToCharSequence(("CHANGE MISCODE")));
 }else {
 //BA.debugLineNum = 1209;BA.debugLine="btnMissCode.Text = \"ADD MISCODE\"";
mostCurrent._btnmisscode.setText(BA.ObjectToCharSequence("ADD MISCODE"));
 };
 //BA.debugLineNum = 1212;BA.debugLine="If ReadRemarks = Null Or ReadRemarks.Length <= 0";
if (mostCurrent._readremarks== null || mostCurrent._readremarks.length()<=0) { 
 //BA.debugLineNum = 1213;BA.debugLine="btnRemarks.Text=\"ADD REMARKS\"";
mostCurrent._btnremarks.setText(BA.ObjectToCharSequence("ADD REMARKS"));
 }else {
 //BA.debugLineNum = 1215;BA.debugLine="btnRemarks.Text=\"CHANGE REMARKS\"";
mostCurrent._btnremarks.setText(BA.ObjectToCharSequence("CHANGE REMARKS"));
 };
 //BA.debugLineNum = 1218;BA.debugLine="If WasRead = 0 Then";
if (_wasread==0) { 
 //BA.debugLineNum = 1219;BA.debugLine="If AcctStatus = \"dc\" Then";
if ((mostCurrent._acctstatus).equals("dc")) { 
 //BA.debugLineNum = 1220;BA.debugLine="btnMissCode.Enabled = False";
mostCurrent._btnmisscode.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1221;BA.debugLine="btnRemarks.Enabled = True";
mostCurrent._btnremarks.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 1223;BA.debugLine="btnMissCode.Enabled = True";
mostCurrent._btnmisscode.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1224;BA.debugLine="btnRemarks.Enabled = False";
mostCurrent._btnremarks.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1226;BA.debugLine="btnEdit.Enabled = False";
mostCurrent._btnedit.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1227;BA.debugLine="btnReprint.Enabled = False";
mostCurrent._btnreprint.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 1230;BA.debugLine="If WasMisCoded = 1 Then";
if (_wasmiscoded==1) { 
 //BA.debugLineNum = 1231;BA.debugLine="btnReprint.Enabled = False";
mostCurrent._btnreprint.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1232;BA.debugLine="btnEdit.Enabled = True";
mostCurrent._btnedit.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1233;BA.debugLine="btnRemarks.Enabled = True";
mostCurrent._btnremarks.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1234;BA.debugLine="btnMissCode.Enabled = True";
mostCurrent._btnmisscode.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1235;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 1238;BA.debugLine="If PrintStatus = 0 Then";
if (_printstatus==0) { 
 //BA.debugLineNum = 1239;BA.debugLine="btnEdit.Enabled = True";
mostCurrent._btnedit.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1240;BA.debugLine="btnMissCode.Enabled = False";
mostCurrent._btnmisscode.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1241;BA.debugLine="btnRemarks.Enabled = True";
mostCurrent._btnremarks.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 1243;BA.debugLine="btnEdit.Enabled = False";
mostCurrent._btnedit.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1244;BA.debugLine="btnMissCode.Enabled = False";
mostCurrent._btnmisscode.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1245;BA.debugLine="btnRemarks.Enabled = False";
mostCurrent._btnremarks.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1247;BA.debugLine="If AcctStatus = \"dc\" Then";
if ((mostCurrent._acctstatus).equals("dc")) { 
 //BA.debugLineNum = 1248;BA.debugLine="btnMissCode.Enabled = False";
mostCurrent._btnmisscode.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 };
 };
 //BA.debugLineNum = 1252;BA.debugLine="If WasUploaded = 1 Then";
if (_wasuploaded==1) { 
 //BA.debugLineNum = 1253;BA.debugLine="btnEdit.Enabled = False";
mostCurrent._btnedit.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1254;BA.debugLine="btnMissCode.Enabled = False";
mostCurrent._btnmisscode.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1255;BA.debugLine="btnRemarks.Enabled = False";
mostCurrent._btnremarks.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1256;BA.debugLine="btnReprint.Enabled = True";
mostCurrent._btnreprint.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 1259;BA.debugLine="End Sub";
return "";
}
public static String  _checkimplosive(int _iprevcum,int _iprescum) throws Exception{
int _iretval = 0;
int _ipercentagerate = 0;
anywheresoftware.b4a.sql.SQL.CursorWrapper _rs = null;
 //BA.debugLineNum = 5783;BA.debugLine="Private Sub CheckImplosive(iPrevCum As Int, iPresC";
 //BA.debugLineNum = 5784;BA.debugLine="Dim iRetVal As Int";
_iretval = 0;
 //BA.debugLineNum = 5785;BA.debugLine="Dim iPercentageRate As Int";
_ipercentagerate = 0;
 //BA.debugLineNum = 5786;BA.debugLine="Dim RS As Cursor";
_rs = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 5787;BA.debugLine="Try";
try { //BA.debugLineNum = 5788;BA.debugLine="iRetVal = 0";
_iretval = (int) (0);
 //BA.debugLineNum = 5789;BA.debugLine="iPercentageRate = 0";
_ipercentagerate = (int) (0);
 //BA.debugLineNum = 5790;BA.debugLine="Starter.strCriteria = \"SELECT * FROM ImplosivesR";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM ImplosivesRate "+"WHERE "+BA.NumberToString(_iprevcum)+" BETWEEN StartCum AND EndCum";
 //BA.debugLineNum = 5792;BA.debugLine="LogColor (Starter.strCriteria, Colors.Yellow)";
anywheresoftware.b4a.keywords.Common.LogImpl("132309257",mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 //BA.debugLineNum = 5793;BA.debugLine="RS = Starter.DBCon.ExecQuery(Starter.strCriteria";
_rs = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 5795;BA.debugLine="LogColor(RS.RowCount, Colors.Cyan)";
anywheresoftware.b4a.keywords.Common.LogImpl("132309260",BA.NumberToString(_rs.getRowCount()),anywheresoftware.b4a.keywords.Common.Colors.Cyan);
 //BA.debugLineNum = 5797;BA.debugLine="If RS.RowCount <= 0 Then";
if (_rs.getRowCount()<=0) { 
 //BA.debugLineNum = 5798;BA.debugLine="iRetVal = 0";
_iretval = (int) (0);
 }else {
 //BA.debugLineNum = 5800;BA.debugLine="RS.Position = 0";
_rs.setPosition((int) (0));
 //BA.debugLineNum = 5801;BA.debugLine="If iPrevCum = 0 Then";
if (_iprevcum==0) { 
 //BA.debugLineNum = 5802;BA.debugLine="If iPresCum >= 100 Then";
if (_iprescum>=100) { 
 //BA.debugLineNum = 5803;BA.debugLine="iRetVal = 2 'High";
_iretval = (int) (2);
 };
 }else if(_iprescum>_iprevcum) { 
 //BA.debugLineNum = 5806;BA.debugLine="iPercentageRate = ((iPresCum - iPrevCum) / iPr";
_ipercentagerate = (int) (((_iprescum-_iprevcum)/(double)_iprevcum));
 //BA.debugLineNum = 5807;BA.debugLine="If iPercentageRate >= RS.GetInt(\"IncRate\") The";
if (_ipercentagerate>=_rs.GetInt("IncRate")) { 
 //BA.debugLineNum = 5808;BA.debugLine="iRetVal = 2 'High";
_iretval = (int) (2);
 }else {
 //BA.debugLineNum = 5810;BA.debugLine="iRetVal = 0 'Normal";
_iretval = (int) (0);
 };
 }else if(_iprescum<_iprevcum) { 
 //BA.debugLineNum = 5813;BA.debugLine="iPercentageRate = ((iPrevCum - iPresCum) / iPr";
_ipercentagerate = (int) (((_iprevcum-_iprescum)/(double)_iprevcum));
 //BA.debugLineNum = 5814;BA.debugLine="If iPercentageRate >= RS.GetInt(\"DecRate\") The";
if (_ipercentagerate>=_rs.GetInt("DecRate")) { 
 //BA.debugLineNum = 5815;BA.debugLine="iRetVal = 1 'Low";
_iretval = (int) (1);
 }else {
 //BA.debugLineNum = 5817;BA.debugLine="iRetVal = 0 'Normal";
_iretval = (int) (0);
 };
 }else {
 //BA.debugLineNum = 5820;BA.debugLine="iRetVal = 0 'Normal";
_iretval = (int) (0);
 };
 };
 } 
       catch (Exception e38) {
			processBA.setLastException(e38); //BA.debugLineNum = 5825;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("132309290",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 //BA.debugLineNum = 5826;BA.debugLine="iRetVal = 0";
_iretval = (int) (0);
 };
 //BA.debugLineNum = 5828;BA.debugLine="RS.Close";
_rs.Close();
 //BA.debugLineNum = 5829;BA.debugLine="Return iRetVal";
if (true) return BA.NumberToString(_iretval);
 //BA.debugLineNum = 5830;BA.debugLine="End Sub";
return "";
}
public static String  _ckeyboard_onkeydown(Object _viewtag,int _primarykeycode) throws Exception{
 //BA.debugLineNum = 6053;BA.debugLine="Private Sub cKeyboard_onKeyDown(ViewTag As Object,";
 //BA.debugLineNum = 6054;BA.debugLine="Log(PrimaryKeyCode)";
anywheresoftware.b4a.keywords.Common.LogImpl("133488897",BA.NumberToString(_primarykeycode),0);
 //BA.debugLineNum = 6055;BA.debugLine="End Sub";
return "";
}
public static String  _cleardisplay() throws Exception{
 //BA.debugLineNum = 1081;BA.debugLine="Sub ClearDisplay";
 //BA.debugLineNum = 1082;BA.debugLine="lblSeqNo.Text = \"\"";
mostCurrent._lblseqno.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 1083;BA.debugLine="lblMeter.Text = \"\"";
mostCurrent._lblmeter.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 1084;BA.debugLine="lblAcctNo.Text = \"\"";
mostCurrent._lblacctno.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 1085;BA.debugLine="lblAcctName.Text = \"\"";
mostCurrent._lblacctname.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 1086;BA.debugLine="lblAcctAddress.Text = \"\"";
mostCurrent._lblacctaddress.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 1087;BA.debugLine="lblClass.Text = \"\"";
mostCurrent._lblclass.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 1088;BA.debugLine="lblPrevCum.Text = \"\"";
mostCurrent._lblprevcum.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 1089;BA.debugLine="lblFindings.Text = \"\"";
mostCurrent._lblfindings.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 1090;BA.debugLine="lblMissCode.Text = \"\"";
mostCurrent._lblmisscode.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 1091;BA.debugLine="lblWarning.Text = \"\"";
mostCurrent._lblwarning.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 1093;BA.debugLine="BookMark = 0";
_bookmark = (int) (0);
 //BA.debugLineNum = 1096;BA.debugLine="btnPrevious.Enabled=False";
mostCurrent._btnprevious.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1097;BA.debugLine="btnNext.Enabled=False";
mostCurrent._btnnext.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1098;BA.debugLine="btnMissCode.Enabled=False";
mostCurrent._btnmisscode.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1099;BA.debugLine="btnRemarks.Enabled=False";
mostCurrent._btnremarks.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1100;BA.debugLine="btnEdit.Enabled=False";
mostCurrent._btnedit.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1101;BA.debugLine="btnReprint.Enabled=False";
mostCurrent._btnreprint.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1102;BA.debugLine="btnReprint.Text = \"Print\"";
mostCurrent._btnreprint.setText(BA.ObjectToCharSequence("Print"));
 //BA.debugLineNum = 1103;BA.debugLine="End Sub";
return "";
}
public static String  _computeaveragebill() throws Exception{
 //BA.debugLineNum = 5432;BA.debugLine="Private Sub ComputeAverageBill";
 //BA.debugLineNum = 5433;BA.debugLine="PresAveRdg = PrevRdg + AveCum";
mostCurrent._presaverdg = BA.NumberToString(_prevrdg+_avecum);
 //BA.debugLineNum = 5434;BA.debugLine="If Not(IsNumber(PresAveRdg)) Or PresAveRdg.Length";
if (anywheresoftware.b4a.keywords.Common.Not(anywheresoftware.b4a.keywords.Common.IsNumber(mostCurrent._presaverdg)) || mostCurrent._presaverdg.length()<=0) { 
 //BA.debugLineNum = 5435;BA.debugLine="PresAveCum = 0";
mostCurrent._presavecum = BA.NumberToString(0);
 }else {
 //BA.debugLineNum = 5437;BA.debugLine="PresAveCum = AveCum";
mostCurrent._presavecum = BA.NumberToString(_avecum);
 };
 //BA.debugLineNum = 5439;BA.debugLine="End Sub";
return "";
}
public static long  _computecumused() throws Exception{
long _retval = 0L;
 //BA.debugLineNum = 3324;BA.debugLine="Sub ComputeCumUsed() As Long";
 //BA.debugLineNum = 3325;BA.debugLine="Dim RetVal As Long";
_retval = 0L;
 //BA.debugLineNum = 3326;BA.debugLine="If Not(IsNumber(txtPresRdg.Text)) Or txtPresRdg.T";
if (anywheresoftware.b4a.keywords.Common.Not(anywheresoftware.b4a.keywords.Common.IsNumber(mostCurrent._txtpresrdg.getText())) || mostCurrent._txtpresrdg.getText().length()<=0) { 
 //BA.debugLineNum = 3327;BA.debugLine="RetVal= 0";
_retval = (long) (0);
 }else {
 //BA.debugLineNum = 3329;BA.debugLine="RetVal = strSF.Val(txtPresRdg.Text) - PrevRdg";
_retval = (long) (mostCurrent._strsf._vvvvvvv6(mostCurrent._txtpresrdg.getText())-_prevrdg);
 };
 //BA.debugLineNum = 3331;BA.debugLine="Return RetVal";
if (true) return _retval;
 //BA.debugLineNum = 3332;BA.debugLine="End Sub";
return 0L;
}
public static boolean  _confirmwarning(String _smsg,String _stitle,String _spos,String _sneg,String _sneu) throws Exception{
boolean _bretval = false;
Object _response = null;
anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper _icon = null;
anywheresoftware.b4a.objects.CSBuilder _cstitle = null;
anywheresoftware.b4a.objects.CSBuilder _csmsg = null;
 //BA.debugLineNum = 5759;BA.debugLine="Private Sub ConfirmWarning(sMsg As String, sTitle";
 //BA.debugLineNum = 5760;BA.debugLine="Dim bRetVal As Boolean";
_bretval = false;
 //BA.debugLineNum = 5761;BA.debugLine="Dim Response As Object";
_response = new Object();
 //BA.debugLineNum = 5762;BA.debugLine="Dim icon As B4XBitmap";
_icon = new anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper();
 //BA.debugLineNum = 5763;BA.debugLine="Dim csTitle, csMsg As CSBuilder";
_cstitle = new anywheresoftware.b4a.objects.CSBuilder();
_csmsg = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 5765;BA.debugLine="csTitle.Initialize.Color(Colors.Red).Append(sTitl";
_cstitle.Initialize().Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(_stitle)).PopAll();
 //BA.debugLineNum = 5767;BA.debugLine="csMsg.Initialize.Bold.Color(Colors.Red).Append($\"";
_csmsg.Initialize().Bold().Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(("ACCESS DENIED!"))).Pop().Pop();
 //BA.debugLineNum = 5768;BA.debugLine="csMsg.Color(Colors.DarkGray).Append(CRLF & sMsg).";
_csmsg.Color(anywheresoftware.b4a.keywords.Common.Colors.DarkGray).Append(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.CRLF+_smsg)).PopAll();
 //BA.debugLineNum = 5770;BA.debugLine="icon = xui.LoadBitmapResize(File.DirAssets, \"erro";
_icon = _xui.LoadBitmapResize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"error.png",anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (24)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (24)),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 5772;BA.debugLine="Response = xui.Msgbox2Async(csMsg, csTitle, sPos,";
_response = _xui.Msgbox2Async(processBA,BA.ObjectToCharSequence(_csmsg.getObject()),BA.ObjectToCharSequence(_cstitle.getObject()),_spos,_sneu,_sneg,(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(_icon.getObject())));
 //BA.debugLineNum = 5773;BA.debugLine="If Response = xui.DialogResponse_Positive Then";
if ((_response).equals((Object)(_xui.DialogResponse_Positive))) { 
 //BA.debugLineNum = 5774;BA.debugLine="bRetVal = True";
_bretval = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 5776;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 5779;BA.debugLine="Return bRetVal";
if (true) return _bretval;
 //BA.debugLineNum = 5780;BA.debugLine="End Sub";
return false;
}
public static int  _countunread(int _ibookid) throws Exception{
int _icount = 0;
 //BA.debugLineNum = 1291;BA.debugLine="Private Sub CountUnread(iBookID As Int) As Int";
 //BA.debugLineNum = 1292;BA.debugLine="Dim iCount As Int";
_icount = 0;
 //BA.debugLineNum = 1293;BA.debugLine="Try";
try { //BA.debugLineNum = 1294;BA.debugLine="Starter.strCriteria=\"SELECT SUM(CASE WHEN tblRea";
mostCurrent._starter._strcriteria /*String*/  = "SELECT SUM(CASE WHEN tblReadings.WasRead = 0 Then 1 Else 0 End) As UnreadAccts FROM tblReadings "+"WHERE BookID="+BA.NumberToString(_ibookid)+" AND BillYear= "+BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ )+" AND BillMonth="+BA.NumberToString(mostCurrent._globalvar._billmonth /*int*/ );
 //BA.debugLineNum = 1296;BA.debugLine="Log(Starter.strCriteria)";
anywheresoftware.b4a.keywords.Common.LogImpl("122544389",mostCurrent._starter._strcriteria /*String*/ ,0);
 //BA.debugLineNum = 1298;BA.debugLine="iCount = Starter.DBCon.ExecQuerySingleResult(Sta";
_icount = (int)(Double.parseDouble(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 1299;BA.debugLine="Log(iCount)";
anywheresoftware.b4a.keywords.Common.LogImpl("122544392",BA.NumberToString(_icount),0);
 } 
       catch (Exception e8) {
			processBA.setLastException(e8); //BA.debugLineNum = 1301;BA.debugLine="iCount = 0";
_icount = (int) (0);
 //BA.debugLineNum = 1302;BA.debugLine="ToastMessageShow(\"Unable to COUNT unread Account";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Unable to COUNT unread Account(s) due to "+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1303;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("122544396",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 1306;BA.debugLine="Return iCount";
if (true) return _icount;
 //BA.debugLineNum = 1307;BA.debugLine="End Sub";
return 0;
}
public static anywheresoftware.b4a.objects.drawable.StateListDrawable  _createbuttoncolor(int _focusedcolor,int _enabledcolor,int _disabledcolor,int _pressedcolor) throws Exception{
anywheresoftware.b4a.objects.drawable.StateListDrawable _retcolor = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _drwfocusedcolor = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _drwenabledcolor = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _drwdisabledcolor = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _drwpressedcolor = null;
 //BA.debugLineNum = 1104;BA.debugLine="Public Sub CreateButtonColor(FocusedColor As Int,";
 //BA.debugLineNum = 1106;BA.debugLine="Dim RetColor As StateListDrawable";
_retcolor = new anywheresoftware.b4a.objects.drawable.StateListDrawable();
 //BA.debugLineNum = 1107;BA.debugLine="RetColor.Initialize";
_retcolor.Initialize();
 //BA.debugLineNum = 1108;BA.debugLine="Dim drwFocusedColor, drwEnabledColor, drwDisabled";
_drwfocusedcolor = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
_drwenabledcolor = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
_drwdisabledcolor = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
_drwpressedcolor = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 1116;BA.debugLine="drwFocusedColor.Initialize2(FocusedColor, 25, 0,";
_drwfocusedcolor.Initialize2(_focusedcolor,(int) (25),(int) (0),anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 1117;BA.debugLine="drwEnabledColor.Initialize2(EnabledColor, 25, 0,";
_drwenabledcolor.Initialize2(_enabledcolor,(int) (25),(int) (0),anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 1118;BA.debugLine="drwDisabledColor.Initialize2(DisabledColor, 25, 2";
_drwdisabledcolor.Initialize2(_disabledcolor,(int) (25),(int) (2),anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 1119;BA.debugLine="drwPressedColor.Initialize2(PressedColor, 25, 0,";
_drwpressedcolor.Initialize2(_pressedcolor,(int) (25),(int) (0),anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 1121;BA.debugLine="RetColor.AddState(RetColor.State_Focused, drwFocu";
_retcolor.AddState(_retcolor.State_Focused,(android.graphics.drawable.Drawable)(_drwfocusedcolor.getObject()));
 //BA.debugLineNum = 1122;BA.debugLine="RetColor.AddState(RetColor.State_Pressed, drwPres";
_retcolor.AddState(_retcolor.State_Pressed,(android.graphics.drawable.Drawable)(_drwpressedcolor.getObject()));
 //BA.debugLineNum = 1123;BA.debugLine="RetColor.AddState(RetColor.State_Enabled, drwEnab";
_retcolor.AddState(_retcolor.State_Enabled,(android.graphics.drawable.Drawable)(_drwenabledcolor.getObject()));
 //BA.debugLineNum = 1124;BA.debugLine="RetColor.AddState(RetColor.State_Disabled, drwDis";
_retcolor.AddState(_retcolor.State_Disabled,(android.graphics.drawable.Drawable)(_drwdisabledcolor.getObject()));
 //BA.debugLineNum = 1125;BA.debugLine="RetColor.AddCatchAllState(drwFocusedColor)";
_retcolor.AddCatchAllState((android.graphics.drawable.Drawable)(_drwfocusedcolor.getObject()));
 //BA.debugLineNum = 1126;BA.debugLine="RetColor.AddCatchAllState(drwEnabledColor)";
_retcolor.AddCatchAllState((android.graphics.drawable.Drawable)(_drwenabledcolor.getObject()));
 //BA.debugLineNum = 1127;BA.debugLine="RetColor.AddCatchAllState(drwDisabledColor)";
_retcolor.AddCatchAllState((android.graphics.drawable.Drawable)(_drwdisabledcolor.getObject()));
 //BA.debugLineNum = 1128;BA.debugLine="RetColor.AddCatchAllState(drwPressedColor)";
_retcolor.AddCatchAllState((android.graphics.drawable.Drawable)(_drwpressedcolor.getObject()));
 //BA.debugLineNum = 1129;BA.debugLine="Return RetColor";
if (true) return _retcolor;
 //BA.debugLineNum = 1131;BA.debugLine="End Sub";
return null;
}
public static anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper  _createscaledbitmap(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _original,int _newwidth,int _newheight) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _b = null;
 //BA.debugLineNum = 4916;BA.debugLine="Sub CreateScaledBitmap(Original As Bitmap, NewWidt";
 //BA.debugLineNum = 4917;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 4918;BA.debugLine="Dim b As Bitmap";
_b = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 4919;BA.debugLine="b = r.RunStaticMethod(\"android.graphics.Bitmap\",";
_b = (anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(_r.RunStaticMethod("android.graphics.Bitmap","createScaledBitmap",new Object[]{(Object)(_original.getObject()),(Object)(_newwidth),(Object)(_newheight),(Object)(anywheresoftware.b4a.keywords.Common.True)},new String[]{"android.graphics.Bitmap","java.lang.int","java.lang.int","java.lang.boolean"})));
 //BA.debugLineNum = 4920;BA.debugLine="Return b";
if (true) return _b;
 //BA.debugLineNum = 4921;BA.debugLine="End Sub";
return null;
}
public static String  _createsubmenus() throws Exception{
anywheresoftware.b4a.objects.CSBuilder _csmisscode = null;
anywheresoftware.b4a.objects.CSBuilder _cszero = null;
anywheresoftware.b4a.objects.CSBuilder _cshigh = null;
anywheresoftware.b4a.objects.CSBuilder _cslow = null;
anywheresoftware.b4a.objects.CSBuilder _csave = null;
anywheresoftware.b4a.objects.CSBuilder _csave2 = null;
anywheresoftware.b4a.objects.CSBuilder _csphoto = null;
anywheresoftware.b4a.objects.CSBuilder _cscancel = null;
anywheresoftware.b4a.objects.CSBuilder _csunprint = null;
Object _smenu = null;
 //BA.debugLineNum = 639;BA.debugLine="Private Sub CreateSubMenus";
 //BA.debugLineNum = 640;BA.debugLine="Dim csMissCode, csZero, csHigh, csLow, csAve, csA";
_csmisscode = new anywheresoftware.b4a.objects.CSBuilder();
_cszero = new anywheresoftware.b4a.objects.CSBuilder();
_cshigh = new anywheresoftware.b4a.objects.CSBuilder();
_cslow = new anywheresoftware.b4a.objects.CSBuilder();
_csave = new anywheresoftware.b4a.objects.CSBuilder();
_csave2 = new anywheresoftware.b4a.objects.CSBuilder();
_csphoto = new anywheresoftware.b4a.objects.CSBuilder();
_cscancel = new anywheresoftware.b4a.objects.CSBuilder();
_csunprint = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 642;BA.debugLine="PopWarnings.Initialize(\"PopWarning\", ToolBar.GetV";
mostCurrent._popwarnings.Initialize(mostCurrent.activityBA,"PopWarning",(android.view.View)(mostCurrent._toolbar.GetView((int) (3)).getObject()));
 //BA.debugLineNum = 643;BA.debugLine="PopSubMenu.Initialize(\"PopSubMnu\", ToolBar.GetVie";
mostCurrent._popsubmenu.Initialize(mostCurrent.activityBA,"PopSubMnu",(android.view.View)(mostCurrent._toolbar.GetView((int) (3)).getObject()));
 //BA.debugLineNum = 646;BA.debugLine="csMissCode.Initialize.Size(14).Color(Colors.White";
_csmisscode.Initialize().Size((int) (14)).Color(anywheresoftware.b4a.keywords.Common.Colors.White).Append(BA.ObjectToCharSequence("Miscoded")).PopAll();
 //BA.debugLineNum = 647;BA.debugLine="csZero.Initialize.Size(14).Color(Colors.White).Ap";
_cszero.Initialize().Size((int) (14)).Color(anywheresoftware.b4a.keywords.Common.Colors.White).Append(BA.ObjectToCharSequence("Zero Cons.")).PopAll();
 //BA.debugLineNum = 648;BA.debugLine="csHigh.Initialize.Size(14).Color(Colors.White).Ap";
_cshigh.Initialize().Size((int) (14)).Color(anywheresoftware.b4a.keywords.Common.Colors.White).Append(BA.ObjectToCharSequence("High Bill")).PopAll();
 //BA.debugLineNum = 649;BA.debugLine="csLow.Initialize.Size(14).Color(Colors.White).App";
_cslow.Initialize().Size((int) (14)).Color(anywheresoftware.b4a.keywords.Common.Colors.White).Append(BA.ObjectToCharSequence("Low Bill")).PopAll();
 //BA.debugLineNum = 650;BA.debugLine="csAve.Initialize.Size(14).Color(Colors.White).App";
_csave.Initialize().Size((int) (14)).Color(anywheresoftware.b4a.keywords.Common.Colors.White).Append(BA.ObjectToCharSequence("Average Bill")).PopAll();
 //BA.debugLineNum = 651;BA.debugLine="csUnprint.Initialize.Size(14).Color(Colors.White)";
_csunprint.Initialize().Size((int) (14)).Color(anywheresoftware.b4a.keywords.Common.Colors.White).Append(BA.ObjectToCharSequence("Unprinted Bills")).PopAll();
 //BA.debugLineNum = 653;BA.debugLine="csAve2.Initialize.Size(14).Color(Colors.White).Ap";
_csave2.Initialize().Size((int) (14)).Color(anywheresoftware.b4a.keywords.Common.Colors.White).Append(BA.ObjectToCharSequence("Average Reading")).PopAll();
 //BA.debugLineNum = 654;BA.debugLine="csPhoto.Initialize.Size(14).Color(Colors.White).A";
_csphoto.Initialize().Size((int) (14)).Color(anywheresoftware.b4a.keywords.Common.Colors.White).Append(BA.ObjectToCharSequence("Take Photo")).PopAll();
 //BA.debugLineNum = 655;BA.debugLine="csCancel.Initialize.Size(14).Color(Colors.White).";
_cscancel.Initialize().Size((int) (14)).Color(anywheresoftware.b4a.keywords.Common.Colors.White).Append(BA.ObjectToCharSequence("Cancel Reading")).PopAll();
 //BA.debugLineNum = 657;BA.debugLine="Dim sMenu As Object";
_smenu = new Object();
 //BA.debugLineNum = 658;BA.debugLine="PopWarnings.AddMenuItem(101,csMissCode, xmlIcon.G";
mostCurrent._popwarnings.AddMenuItem((int) (101),BA.ObjectToCharSequence(_csmisscode.getObject()),mostCurrent._xmlicon.GetDrawable("miss"));
 //BA.debugLineNum = 659;BA.debugLine="PopWarnings.AddMenuItem(102, csZero, xmlIcon.GetD";
mostCurrent._popwarnings.AddMenuItem((int) (102),BA.ObjectToCharSequence(_cszero.getObject()),mostCurrent._xmlicon.GetDrawable("zero"));
 //BA.debugLineNum = 660;BA.debugLine="PopWarnings.AddMenuItem(103, csHigh, xmlIcon.GetD";
mostCurrent._popwarnings.AddMenuItem((int) (103),BA.ObjectToCharSequence(_cshigh.getObject()),mostCurrent._xmlicon.GetDrawable("high"));
 //BA.debugLineNum = 661;BA.debugLine="PopWarnings.AddMenuItem(104, csLow, xmlIcon.GetDr";
mostCurrent._popwarnings.AddMenuItem((int) (104),BA.ObjectToCharSequence(_cslow.getObject()),mostCurrent._xmlicon.GetDrawable("low"));
 //BA.debugLineNum = 662;BA.debugLine="PopWarnings.AddMenuItem(105, csAve, xmlIcon.GetDr";
mostCurrent._popwarnings.AddMenuItem((int) (105),BA.ObjectToCharSequence(_csave.getObject()),mostCurrent._xmlicon.GetDrawable("ave1"));
 //BA.debugLineNum = 663;BA.debugLine="PopWarnings.AddMenuItem(106, csUnprint, xmlIcon.G";
mostCurrent._popwarnings.AddMenuItem((int) (106),BA.ObjectToCharSequence(_csunprint.getObject()),mostCurrent._xmlicon.GetDrawable("unprint"));
 //BA.debugLineNum = 665;BA.debugLine="PopSubMenu.AddMenuItem(202, csAve2,xmlIcon.GetDra";
mostCurrent._popsubmenu.AddMenuItem((int) (202),BA.ObjectToCharSequence(_csave2.getObject()),mostCurrent._xmlicon.GetDrawable("ave2"));
 //BA.debugLineNum = 666;BA.debugLine="PopSubMenu.AddMenuItem(201, csPhoto,xmlIcon.GetDr";
mostCurrent._popsubmenu.AddMenuItem((int) (201),BA.ObjectToCharSequence(_csphoto.getObject()),mostCurrent._xmlicon.GetDrawable("photo"));
 //BA.debugLineNum = 667;BA.debugLine="PopSubMenu.AddMenuItem(203, csCancel,xmlIcon.GetD";
mostCurrent._popsubmenu.AddMenuItem((int) (203),BA.ObjectToCharSequence(_cscancel.getObject()),mostCurrent._xmlicon.GetDrawable("cancel"));
 //BA.debugLineNum = 671;BA.debugLine="End Sub";
return "";
}
public static String  _disablecontrols() throws Exception{
 //BA.debugLineNum = 1145;BA.debugLine="Sub DisableControls";
 //BA.debugLineNum = 1146;BA.debugLine="pnlMain.Enabled = False";
mostCurrent._pnlmain.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1147;BA.debugLine="ToolBar.Enabled = False";
mostCurrent._toolbar.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1148;BA.debugLine="pnlAccountInfo.Enabled = False";
mostCurrent._pnlaccountinfo.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1149;BA.debugLine="pnlPrevious.Enabled = False";
mostCurrent._pnlprevious.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1150;BA.debugLine="pnlPresent.Enabled = False";
mostCurrent._pnlpresent.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1151;BA.debugLine="pnlFindings.Enabled = False";
mostCurrent._pnlfindings.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1152;BA.debugLine="pnlButtons.Enabled = False";
mostCurrent._pnlbuttons.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1153;BA.debugLine="pnlStatus.Enabled = False";
mostCurrent._pnlstatus.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1155;BA.debugLine="txtPresRdg.Enabled = False";
mostCurrent._txtpresrdg.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1156;BA.debugLine="btnPrevious.Enabled = False";
mostCurrent._btnprevious.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1157;BA.debugLine="btnNext.Enabled = False";
mostCurrent._btnnext.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1158;BA.debugLine="btnEdit.Enabled = False";
mostCurrent._btnedit.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1159;BA.debugLine="btnMissCode.Enabled = False";
mostCurrent._btnmisscode.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1160;BA.debugLine="btnRemarks.Enabled = False";
mostCurrent._btnremarks.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1161;BA.debugLine="btnReprint.Enabled = False";
mostCurrent._btnreprint.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1163;BA.debugLine="ToolBar.Menu.FindItem(1).Enabled = False";
mostCurrent._toolbar.getMenu().FindItem((int) (1)).setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1164;BA.debugLine="ToolBar.Menu.FindItem(2).Enabled = False";
mostCurrent._toolbar.getMenu().FindItem((int) (2)).setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1165;BA.debugLine="ToolBar.Menu.FindItem(3).Enabled = False";
mostCurrent._toolbar.getMenu().FindItem((int) (3)).setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1166;BA.debugLine="End Sub";
return "";
}
public static boolean  _disperrormsg(String _smsg,String _stitle) throws Exception{
anywheresoftware.b4a.objects.CSBuilder _cstitle = null;
anywheresoftware.b4a.objects.CSBuilder _csmsg = null;
boolean _blnretval = false;
int _iresp = 0;
anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper _icon = null;
 //BA.debugLineNum = 5729;BA.debugLine="Private Sub DispErrorMsg(sMsg As String, sTitle As";
 //BA.debugLineNum = 5730;BA.debugLine="Dim csTitle, csMsg As CSBuilder";
_cstitle = new anywheresoftware.b4a.objects.CSBuilder();
_csmsg = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 5731;BA.debugLine="Dim blnRetVal As Boolean";
_blnretval = false;
 //BA.debugLineNum = 5732;BA.debugLine="Dim iResp As Int";
_iresp = 0;
 //BA.debugLineNum = 5733;BA.debugLine="Dim icon As B4XBitmap";
_icon = new anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper();
 //BA.debugLineNum = 5734;BA.debugLine="csTitle.Initialize.Color(Colors.Red).Append(sTitl";
_cstitle.Initialize().Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(_stitle)).PopAll();
 //BA.debugLineNum = 5735;BA.debugLine="csMsg.Initialize.Bold.Color(Colors.DarkGray).Appe";
_csmsg.Initialize().Bold().Color(anywheresoftware.b4a.keywords.Common.Colors.DarkGray).Append(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.CRLF+_smsg)).PopAll();
 //BA.debugLineNum = 5736;BA.debugLine="icon = xui.LoadBitmapResize(File.DirAssets, \"erro";
_icon = _xui.LoadBitmapResize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"error.png",anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (24)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (24)),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 5738;BA.debugLine="iResp =  Msgbox2(csMsg, csTitle, $\"OK\"$, \"\",\"\",ic";
_iresp = anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence(_csmsg.getObject()),BA.ObjectToCharSequence(_cstitle.getObject()),("OK"),"","",(android.graphics.Bitmap)(_icon.getObject()),mostCurrent.activityBA);
 //BA.debugLineNum = 5739;BA.debugLine="If iResp = DialogResponse.POSITIVE Then";
if (_iresp==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 5740;BA.debugLine="blnRetVal = True";
_blnretval = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 5742;BA.debugLine="blnRetVal = False";
_blnretval = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 5744;BA.debugLine="Return blnRetVal";
if (true) return _blnretval;
 //BA.debugLineNum = 5745;BA.debugLine="End Sub";
return false;
}
public static String  _disperrormsg2(String _smsg,String _stitle) throws Exception{
anywheresoftware.b4a.objects.CSBuilder _cstitle = null;
anywheresoftware.b4a.objects.CSBuilder _csmsg = null;
boolean _blnretval = false;
int _iresp = 0;
anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper _icon = null;
 //BA.debugLineNum = 5747;BA.debugLine="Private Sub DispErrorMsg2(sMsg As String, sTitle A";
 //BA.debugLineNum = 5748;BA.debugLine="Dim csTitle, csMsg As CSBuilder";
_cstitle = new anywheresoftware.b4a.objects.CSBuilder();
_csmsg = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 5749;BA.debugLine="Dim blnRetVal As Boolean";
_blnretval = false;
 //BA.debugLineNum = 5750;BA.debugLine="Dim iResp As Int";
_iresp = 0;
 //BA.debugLineNum = 5751;BA.debugLine="Dim icon As B4XBitmap";
_icon = new anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper();
 //BA.debugLineNum = 5752;BA.debugLine="csTitle.Initialize.Color(Colors.Red).Append(sTitl";
_cstitle.Initialize().Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(_stitle)).PopAll();
 //BA.debugLineNum = 5753;BA.debugLine="csMsg.Initialize.Bold.Color(Colors.DarkGray).Appe";
_csmsg.Initialize().Bold().Color(anywheresoftware.b4a.keywords.Common.Colors.DarkGray).Append(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.CRLF+_smsg)).PopAll();
 //BA.debugLineNum = 5754;BA.debugLine="icon = xui.LoadBitmapResize(File.DirAssets, \"erro";
_icon = _xui.LoadBitmapResize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"error.png",anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (24)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (24)),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 5756;BA.debugLine="Msgbox2Async(csMsg, csTitle, $\"OK\"$, \"\",\"\", icon,";
anywheresoftware.b4a.keywords.Common.Msgbox2Async(BA.ObjectToCharSequence(_csmsg.getObject()),BA.ObjectToCharSequence(_cstitle.getObject()),("OK"),"","",(anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(_icon.getObject())),processBA,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 5757;BA.debugLine="End Sub";
return "";
}
public static String  _dispinfomsg(String _smsg,String _stitle) throws Exception{
anywheresoftware.b4a.objects.CSBuilder _cstitle = null;
anywheresoftware.b4a.objects.CSBuilder _csmsg = null;
 //BA.debugLineNum = 5721;BA.debugLine="Private Sub DispInfoMsg(sMsg As String, sTitle As";
 //BA.debugLineNum = 5722;BA.debugLine="Dim csTitle, csMsg As CSBuilder";
_cstitle = new anywheresoftware.b4a.objects.CSBuilder();
_csmsg = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 5723;BA.debugLine="csTitle.Initialize.Color(Colors.Red).Append(sTitl";
_cstitle.Initialize().Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(_stitle)).PopAll();
 //BA.debugLineNum = 5724;BA.debugLine="csMsg.Initialize.Bold.Color(Colors.DarkGray).Appe";
_csmsg.Initialize().Bold().Color(anywheresoftware.b4a.keywords.Common.Colors.DarkGray).Append(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.CRLF+_smsg)).PopAll();
 //BA.debugLineNum = 5726;BA.debugLine="Msgbox(csMsg, csTitle)";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence(_csmsg.getObject()),BA.ObjectToCharSequence(_cstitle.getObject()),mostCurrent.activityBA);
 //BA.debugLineNum = 5727;BA.debugLine="End Sub";
return "";
}
public static String  _displayrecord() throws Exception{
 //BA.debugLineNum = 861;BA.debugLine="Sub DisplayRecord()";
 //BA.debugLineNum = 863;BA.debugLine="ReadID = rsLoadedBook.GetInt(\"ReadID\")";
_readid = mostCurrent._rsloadedbook.GetInt("ReadID");
 //BA.debugLineNum = 864;BA.debugLine="BillNo = rsLoadedBook.GetString(\"BillNo\")";
mostCurrent._billno = mostCurrent._rsloadedbook.GetString("BillNo");
 //BA.debugLineNum = 865;BA.debugLine="AcctID = rsLoadedBook.GetInt(\"AcctID\")";
_acctid = mostCurrent._rsloadedbook.GetInt("AcctID");
 //BA.debugLineNum = 866;BA.debugLine="AcctNo = rsLoadedBook.GetString(\"AcctNo\")";
mostCurrent._acctno = mostCurrent._rsloadedbook.GetString("AcctNo");
 //BA.debugLineNum = 867;BA.debugLine="AcctName = rsLoadedBook.GetString(\"AcctName\")";
mostCurrent._acctname = mostCurrent._rsloadedbook.GetString("AcctName");
 //BA.debugLineNum = 868;BA.debugLine="AcctAddress = rsLoadedBook.GetString(\"AcctAddress";
mostCurrent._acctaddress = mostCurrent._rsloadedbook.GetString("AcctAddress");
 //BA.debugLineNum = 869;BA.debugLine="AcctClass = rsLoadedBook.GetString(\"AcctClass\")";
mostCurrent._acctclass = mostCurrent._rsloadedbook.GetString("AcctClass");
 //BA.debugLineNum = 870;BA.debugLine="AcctSubClass = rsLoadedBook.GetString(\"AcctSubCla";
mostCurrent._acctsubclass = mostCurrent._rsloadedbook.GetString("AcctSubClass");
 //BA.debugLineNum = 871;BA.debugLine="AcctStatus = rsLoadedBook.GetString(\"AcctStatus\")";
mostCurrent._acctstatus = mostCurrent._rsloadedbook.GetString("AcctStatus");
 //BA.debugLineNum = 872;BA.debugLine="MeterNo = rsLoadedBook.GetString(\"MeterNo\")";
mostCurrent._meterno = mostCurrent._rsloadedbook.GetString("MeterNo");
 //BA.debugLineNum = 873;BA.debugLine="MaxReading = rsLoadedBook.GetDouble(\"MaxReading\")";
_maxreading = mostCurrent._rsloadedbook.GetDouble("MaxReading");
 //BA.debugLineNum = 874;BA.debugLine="BookNo = rsLoadedBook.GetString(\"BookNo\")";
_bookno = (int)(Double.parseDouble(mostCurrent._rsloadedbook.GetString("BookNo")));
 //BA.debugLineNum = 875;BA.debugLine="SeqNo = rsLoadedBook.GetString(\"SeqNo\")";
mostCurrent._seqno = mostCurrent._rsloadedbook.GetString("SeqNo");
 //BA.debugLineNum = 876;BA.debugLine="IsSenior = rsLoadedBook.GetInt(\"IsSenior\")";
_issenior = mostCurrent._rsloadedbook.GetInt("IsSenior");
 //BA.debugLineNum = 877;BA.debugLine="SeniorOnBefore = rsLoadedBook.GetDouble(\"SeniorOn";
_senioronbefore = mostCurrent._rsloadedbook.GetDouble("SeniorOnBefore");
 //BA.debugLineNum = 878;BA.debugLine="SeniorAfter = rsLoadedBook.GetDouble(\"SeniorAfter";
_seniorafter = mostCurrent._rsloadedbook.GetDouble("SeniorAfter");
 //BA.debugLineNum = 879;BA.debugLine="SeniorMaxCum = rsLoadedBook.GetDouble(\"SeniorMaxC";
_seniormaxcum = mostCurrent._rsloadedbook.GetDouble("SeniorMaxCum");
 //BA.debugLineNum = 880;BA.debugLine="GDeposit = rsLoadedBook.GetDouble(\"GDeposit\")";
_gdeposit = mostCurrent._rsloadedbook.GetDouble("GDeposit");
 //BA.debugLineNum = 881;BA.debugLine="PrevRdgDate = rsLoadedBook.GetString(\"PrevRdgDate";
mostCurrent._prevrdgdate = mostCurrent._rsloadedbook.GetString("PrevRdgDate");
 //BA.debugLineNum = 882;BA.debugLine="PrevRdg = rsLoadedBook.GetInt(\"PrevRdg\")";
_prevrdg = mostCurrent._rsloadedbook.GetInt("PrevRdg");
 //BA.debugLineNum = 883;BA.debugLine="PrevCum = rsLoadedBook.GetInt(\"PrevCum\")";
mostCurrent._prevcum = BA.NumberToString(mostCurrent._rsloadedbook.GetInt("PrevCum"));
 //BA.debugLineNum = 884;BA.debugLine="FinalRdg = rsLoadedBook.GetInt(\"FinalRdg\")";
_finalrdg = mostCurrent._rsloadedbook.GetInt("FinalRdg");
 //BA.debugLineNum = 885;BA.debugLine="GlobalVar.CamAcctNo = AcctNo";
mostCurrent._globalvar._camacctno /*String*/  = mostCurrent._acctno;
 //BA.debugLineNum = 886;BA.debugLine="GlobalVar.CamMeterNo = MeterNo";
mostCurrent._globalvar._cammeterno /*String*/  = mostCurrent._meterno;
 //BA.debugLineNum = 887;BA.debugLine="Log(PrevRdg & \" - \" & PrevCum)";
anywheresoftware.b4a.keywords.Common.LogImpl("122020122",BA.NumberToString(_prevrdg)+" - "+mostCurrent._prevcum,0);
 //BA.debugLineNum = 889;BA.debugLine="PresRdgDate = rsLoadedBook.GetString(\"PresRdgDate";
mostCurrent._presrdgdate = mostCurrent._rsloadedbook.GetString("PresRdgDate");
 //BA.debugLineNum = 890;BA.debugLine="PresRdg = rsLoadedBook.GetString(\"PresRdg\")";
mostCurrent._presrdg = mostCurrent._rsloadedbook.GetString("PresRdg");
 //BA.debugLineNum = 891;BA.debugLine="PresCum = rsLoadedBook.GetInt(\"PresCum\")";
mostCurrent._prescum = BA.NumberToString(mostCurrent._rsloadedbook.GetInt("PresCum"));
 //BA.debugLineNum = 892;BA.debugLine="DateFrom = rsLoadedBook.GetString(\"DateFrom\")";
mostCurrent._datefrom = mostCurrent._rsloadedbook.GetString("DateFrom");
 //BA.debugLineNum = 893;BA.debugLine="DateTo = rsLoadedBook.GetString(\"DateTo\")";
mostCurrent._dateto = mostCurrent._rsloadedbook.GetString("DateTo");
 //BA.debugLineNum = 894;BA.debugLine="DueDate = rsLoadedBook.GetString(\"DueDate\")";
mostCurrent._duedate = mostCurrent._rsloadedbook.GetString("DueDate");
 //BA.debugLineNum = 895;BA.debugLine="AveCum = rsLoadedBook.GetLong(\"AveCum\")";
_avecum = mostCurrent._rsloadedbook.GetLong("AveCum");
 //BA.debugLineNum = 896;BA.debugLine="BillType = rsLoadedBook.GetString(\"BillType\")";
mostCurrent._billtype = mostCurrent._rsloadedbook.GetString("BillType");
 //BA.debugLineNum = 897;BA.debugLine="AddCons = rsLoadedBook.GetDouble(\"AddCons\")";
_addcons = mostCurrent._rsloadedbook.GetDouble("AddCons");
 //BA.debugLineNum = 898;BA.debugLine="TotalCum = rsLoadedBook.GetInt(\"TotalCum\")";
_totalcum = mostCurrent._rsloadedbook.GetInt("TotalCum");
 //BA.debugLineNum = 899;BA.debugLine="BasicAmt = rsLoadedBook.GetDouble(\"BasicAmt\")";
_basicamt = mostCurrent._rsloadedbook.GetDouble("BasicAmt");
 //BA.debugLineNum = 900;BA.debugLine="PCA = rsLoadedBook.GetDouble(\"PCA\")";
_pca = mostCurrent._rsloadedbook.GetDouble("PCA");
 //BA.debugLineNum = 901;BA.debugLine="AddToBillAmt = rsLoadedBook.GetDouble(\"AddToBillA";
_addtobillamt = mostCurrent._rsloadedbook.GetDouble("AddToBillAmt");
 //BA.debugLineNum = 902;BA.debugLine="ArrearsAmt = rsLoadedBook.GetDouble(\"ArrearsAmt\")";
_arrearsamt = mostCurrent._rsloadedbook.GetDouble("ArrearsAmt");
 //BA.debugLineNum = 903;BA.debugLine="AdvPayment = rsLoadedBook.GetDouble(\"AdvPayment\")";
_advpayment = mostCurrent._rsloadedbook.GetDouble("AdvPayment");
 //BA.debugLineNum = 904;BA.debugLine="PenaltyPct = rsLoadedBook.GetDouble(\"PenaltyPct\")";
_penaltypct = mostCurrent._rsloadedbook.GetDouble("PenaltyPct");
 //BA.debugLineNum = 905;BA.debugLine="PenaltyAmt = rsLoadedBook.GetDouble(\"PenaltyAmt\")";
_penaltyamt = mostCurrent._rsloadedbook.GetDouble("PenaltyAmt");
 //BA.debugLineNum = 907;BA.debugLine="WasRead = rsLoadedBook.GetInt(\"WasRead\")";
_wasread = mostCurrent._rsloadedbook.GetInt("WasRead");
 //BA.debugLineNum = 908;BA.debugLine="WasMisCoded = rsLoadedBook.GetInt(\"WasMissCoded\")";
_wasmiscoded = mostCurrent._rsloadedbook.GetInt("WasMissCoded");
 //BA.debugLineNum = 909;BA.debugLine="MisCodeID = rsLoadedBook.GetInt(\"MissCodeID\")";
_miscodeid = mostCurrent._rsloadedbook.GetInt("MissCodeID");
 //BA.debugLineNum = 910;BA.debugLine="MisCodeDesc = rsLoadedBook.GetString(\"MissCode\")";
mostCurrent._miscodedesc = mostCurrent._rsloadedbook.GetString("MissCode");
 //BA.debugLineNum = 911;BA.debugLine="WasImplosive = rsLoadedBook.GetInt(\"WasImplosive\"";
_wasimplosive = mostCurrent._rsloadedbook.GetInt("WasImplosive");
 //BA.debugLineNum = 912;BA.debugLine="ImplosiveType = rsLoadedBook.GetString(\"Implosive";
mostCurrent._implosivetype = mostCurrent._rsloadedbook.GetString("ImplosiveType");
 //BA.debugLineNum = 914;BA.debugLine="FFindings = rsLoadedBook.GetString(\"FFindings\")";
mostCurrent._ffindings = mostCurrent._rsloadedbook.GetString("FFindings");
 //BA.debugLineNum = 915;BA.debugLine="FWarnings = rsLoadedBook.GetString(\"FWarnings\")";
mostCurrent._fwarnings = mostCurrent._rsloadedbook.GetString("FWarnings");
 //BA.debugLineNum = 916;BA.debugLine="ReadRemarks = rsLoadedBook.GetString(\"ReadRemarks";
mostCurrent._readremarks = mostCurrent._rsloadedbook.GetString("ReadRemarks");
 //BA.debugLineNum = 917;BA.debugLine="NoInput = rsLoadedBook.GetInt(\"NoInput\")";
_noinput = mostCurrent._rsloadedbook.GetInt("NoInput");
 //BA.debugLineNum = 918;BA.debugLine="WasUploaded = rsLoadedBook.GetInt(\"WasUploaded\")";
_wasuploaded = mostCurrent._rsloadedbook.GetInt("WasUploaded");
 //BA.debugLineNum = 919;BA.debugLine="PrintStatus = rsLoadedBook.GetInt(\"PrintStatus\")";
_printstatus = mostCurrent._rsloadedbook.GetInt("PrintStatus");
 //BA.debugLineNum = 921;BA.debugLine="MeterCharges = rsLoadedBook.GetDouble(\"MeterCharg";
_metercharges = mostCurrent._rsloadedbook.GetDouble("MeterCharges");
 //BA.debugLineNum = 922;BA.debugLine="FranchiseTaxPct = rsLoadedBook.GetDouble(\"Franchi";
_franchisetaxpct = mostCurrent._rsloadedbook.GetDouble("FranchiseTaxPct");
 //BA.debugLineNum = 924;BA.debugLine="SeptageFeeAmt = rsLoadedBook.GetDouble(\"SeptageAm";
_septagefeeamt = mostCurrent._rsloadedbook.GetDouble("SeptageAmt");
 //BA.debugLineNum = 925;BA.debugLine="NoPrinted = rsLoadedBook.GetInt(\"NoPrinted\")";
_noprinted = mostCurrent._rsloadedbook.GetInt("NoPrinted");
 //BA.debugLineNum = 926;BA.debugLine="PrevCum1st = rsLoadedBook.GetInt(\"PrevCum1st\")";
_prevcum1st = mostCurrent._rsloadedbook.GetInt("PrevCum1st");
 //BA.debugLineNum = 927;BA.debugLine="PrevCum2nd = rsLoadedBook.GetInt(\"PrevCum2nd\")";
_prevcum2nd = mostCurrent._rsloadedbook.GetInt("PrevCum2nd");
 //BA.debugLineNum = 928;BA.debugLine="PrevCum3rd = rsLoadedBook.GetInt(\"PrevCum3rd\")";
_prevcum3rd = mostCurrent._rsloadedbook.GetInt("PrevCum3rd");
 //BA.debugLineNum = 929;BA.debugLine="HasSeptageFee = rsLoadedBook.GetInt(\"HasSeptageFe";
_hasseptagefee = mostCurrent._rsloadedbook.GetInt("HasSeptageFee");
 //BA.debugLineNum = 930;BA.debugLine="WithDueDate = rsLoadedBook.GetInt(\"WithDueDate\")";
_withduedate = mostCurrent._rsloadedbook.GetInt("WithDueDate");
 //BA.debugLineNum = 931;BA.debugLine="MinSeptageCum = rsLoadedBook.GetInt(\"MinSeptageCu";
_minseptagecum = mostCurrent._rsloadedbook.GetInt("MinSeptageCum");
 //BA.debugLineNum = 932;BA.debugLine="MaxSeptageCum = rsLoadedBook.GetInt(\"MaxSeptageCu";
_maxseptagecum = mostCurrent._rsloadedbook.GetInt("MaxSeptageCum");
 //BA.debugLineNum = 933;BA.debugLine="SeptageRate = rsLoadedBook.GetDouble(\"SeptageRate";
_septagerate = mostCurrent._rsloadedbook.GetDouble("SeptageRate");
 //BA.debugLineNum = 935;BA.debugLine="If GlobalVar.BranchID = 28 Or GlobalVar.BranchID";
if (mostCurrent._globalvar._branchid /*int*/ ==28 || mostCurrent._globalvar._branchid /*int*/ ==29 || mostCurrent._globalvar._branchid /*int*/ ==30) { 
 //BA.debugLineNum = 936;BA.debugLine="SeptageArrears = rsLoadedBook.GetDouble(\"Septage";
_septagearrears = mostCurrent._rsloadedbook.GetDouble("SeptageArrears");
 };
 //BA.debugLineNum = 939;BA.debugLine="AcctClassification = AcctClass & \"-\" & AcctSubCla";
mostCurrent._acctclassification = mostCurrent._acctclass+"-"+mostCurrent._acctsubclass;
 //BA.debugLineNum = 940;BA.debugLine="strReadRemarks = \"\"";
mostCurrent._strreadremarks = "";
 //BA.debugLineNum = 943;BA.debugLine="If strSF.Len(SeqNo) = 1 Then";
if (mostCurrent._strsf._vvv7(mostCurrent._seqno)==1) { 
 //BA.debugLineNum = 944;BA.debugLine="lblSeqNo.Text = $\"000\"$ & SeqNo";
mostCurrent._lblseqno.setText(BA.ObjectToCharSequence(("000")+mostCurrent._seqno));
 }else if(mostCurrent._strsf._vvv7(mostCurrent._seqno)==2) { 
 //BA.debugLineNum = 946;BA.debugLine="lblSeqNo.Text = $\"00\"$ & SeqNo";
mostCurrent._lblseqno.setText(BA.ObjectToCharSequence(("00")+mostCurrent._seqno));
 }else if(mostCurrent._strsf._vvv7(mostCurrent._seqno)==3) { 
 //BA.debugLineNum = 948;BA.debugLine="lblSeqNo.Text = $\"0\"$ & SeqNo";
mostCurrent._lblseqno.setText(BA.ObjectToCharSequence(("0")+mostCurrent._seqno));
 }else if(mostCurrent._strsf._vvv7(mostCurrent._seqno)>=4) { 
 //BA.debugLineNum = 950;BA.debugLine="lblSeqNo.Text = SeqNo";
mostCurrent._lblseqno.setText(BA.ObjectToCharSequence(mostCurrent._seqno));
 };
 //BA.debugLineNum = 953;BA.debugLine="lblMeter.Text = MeterNo";
mostCurrent._lblmeter.setText(BA.ObjectToCharSequence(mostCurrent._meterno));
 //BA.debugLineNum = 954;BA.debugLine="lblAcctNo.Text = AcctNo";
mostCurrent._lblacctno.setText(BA.ObjectToCharSequence(mostCurrent._acctno));
 //BA.debugLineNum = 955;BA.debugLine="lblAcctName.Text = GlobalVar.SF.Upper(AcctName)";
mostCurrent._lblacctname.setText(BA.ObjectToCharSequence(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv5(mostCurrent._acctname)));
 //BA.debugLineNum = 956;BA.debugLine="lblAcctAddress.Text = TitleCase(AcctAddress)";
mostCurrent._lblacctaddress.setText(BA.ObjectToCharSequence(_titlecase(mostCurrent._acctaddress)));
 //BA.debugLineNum = 957;BA.debugLine="lblClass.Text = AcctClassification";
mostCurrent._lblclass.setText(BA.ObjectToCharSequence(mostCurrent._acctclassification));
 //BA.debugLineNum = 958;BA.debugLine="lblPrevCum.Text = PrevCum";
mostCurrent._lblprevcum.setText(BA.ObjectToCharSequence(mostCurrent._prevcum));
 //BA.debugLineNum = 960;BA.debugLine="lblFindings.Text = FFindings";
mostCurrent._lblfindings.setText(BA.ObjectToCharSequence(mostCurrent._ffindings));
 //BA.debugLineNum = 961;BA.debugLine="lblMissCode.Text = MisCodeDesc";
mostCurrent._lblmisscode.setText(BA.ObjectToCharSequence(mostCurrent._miscodedesc));
 //BA.debugLineNum = 962;BA.debugLine="lblWarning.Text = FWarnings";
mostCurrent._lblwarning.setText(BA.ObjectToCharSequence(mostCurrent._fwarnings));
 //BA.debugLineNum = 966;BA.debugLine="If WasRead = 0 And (PresRdg = Null Or PresRdg = \"";
if (_wasread==0 && (mostCurrent._presrdg== null || (mostCurrent._presrdg).equals(""))) { 
 //BA.debugLineNum = 968;BA.debugLine="btnEdit.Enabled = False";
mostCurrent._btnedit.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 969;BA.debugLine="btnRemarks.Enabled = False";
mostCurrent._btnremarks.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 970;BA.debugLine="btnReprint.Enabled = False";
mostCurrent._btnreprint.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 971;BA.debugLine="lblPresCum.Text = \"\"";
mostCurrent._lblprescum.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 972;BA.debugLine="txtPresRdg.Text = \"\"";
mostCurrent._txtpresrdg.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 973;BA.debugLine="txtPresRdg.Enabled = True";
mostCurrent._txtpresrdg.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 974;BA.debugLine="txtPresRdg.RequestFocus";
mostCurrent._txtpresrdg.RequestFocus();
 //BA.debugLineNum = 975;BA.debugLine="cKeyboard.SetCustomFilter(txtPresRdg, txtPresRdg";
mostCurrent._ckeyboard.SetCustomFilter((android.widget.EditText)(mostCurrent._txtpresrdg.getObject()),mostCurrent._txtpresrdg.INPUT_TYPE_NUMBERS,"1234567890");
 //BA.debugLineNum = 976;BA.debugLine="cKeyboard.ShowKeyboard(txtPresRdg)";
mostCurrent._ckeyboard.ShowKeyboard((android.widget.EditText)(mostCurrent._txtpresrdg.getObject()));
 //BA.debugLineNum = 977;BA.debugLine="txtPresRdg.InputType = txtPresRdg.INPUT_TYPE_NUM";
mostCurrent._txtpresrdg.setInputType(mostCurrent._txtpresrdg.INPUT_TYPE_NUMBERS);
 //BA.debugLineNum = 978;BA.debugLine="T1.Enabled = False";
mostCurrent._t1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 979;BA.debugLine="lblReadStatus.Visible = True";
mostCurrent._lblreadstatus.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 980;BA.debugLine="lblReadStatus.Text = \"UNREAD\"";
mostCurrent._lblreadstatus.setText(BA.ObjectToCharSequence("UNREAD"));
 //BA.debugLineNum = 981;BA.debugLine="lblReadStatus.TextColor = Colors.Red";
mostCurrent._lblreadstatus.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 }else if(_wasread==1 && _wasmiscoded==1) { 
 //BA.debugLineNum = 984;BA.debugLine="lblReadStatus.Text = \"MISCODED\"";
mostCurrent._lblreadstatus.setText(BA.ObjectToCharSequence("MISCODED"));
 //BA.debugLineNum = 985;BA.debugLine="lblReadStatus.TextColor = Colors.Gray";
mostCurrent._lblreadstatus.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 //BA.debugLineNum = 986;BA.debugLine="btnEdit.Enabled = True";
mostCurrent._btnedit.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 987;BA.debugLine="btnReprint.Enabled = False";
mostCurrent._btnreprint.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 988;BA.debugLine="btnRemarks.Enabled = True";
mostCurrent._btnremarks.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 989;BA.debugLine="txtPresRdg.Text = PresRdg";
mostCurrent._txtpresrdg.setText(BA.ObjectToCharSequence(mostCurrent._presrdg));
 //BA.debugLineNum = 990;BA.debugLine="txtPresRdg.Enabled = False";
mostCurrent._txtpresrdg.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 991;BA.debugLine="lblPresCum.Text = PresCum";
mostCurrent._lblprescum.setText(BA.ObjectToCharSequence(mostCurrent._prescum));
 //BA.debugLineNum = 992;BA.debugLine="IMEkeyboard.HideKeyboard";
mostCurrent._imekeyboard.HideKeyboard(mostCurrent.activityBA);
 //BA.debugLineNum = 993;BA.debugLine="cKeyboard.HideKeyboard";
mostCurrent._ckeyboard.HideKeyboard();
 }else if((mostCurrent._billtype).equals("AB")) { 
 //BA.debugLineNum = 995;BA.debugLine="lblReadStatus.Visible = True";
mostCurrent._lblreadstatus.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 996;BA.debugLine="lblReadStatus.Text = \"AVG. BILL\"";
mostCurrent._lblreadstatus.setText(BA.ObjectToCharSequence("AVG. BILL"));
 //BA.debugLineNum = 997;BA.debugLine="lblReadStatus.TextColor = 0xFFF9A863";
mostCurrent._lblreadstatus.setTextColor((int) (0xfff9a863));
 //BA.debugLineNum = 998;BA.debugLine="btnEdit.Enabled = True";
mostCurrent._btnedit.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 999;BA.debugLine="btnReprint.Enabled = True";
mostCurrent._btnreprint.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1000;BA.debugLine="btnRemarks.Enabled = True";
mostCurrent._btnremarks.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1001;BA.debugLine="txtPresRdg.Text = PresRdg";
mostCurrent._txtpresrdg.setText(BA.ObjectToCharSequence(mostCurrent._presrdg));
 //BA.debugLineNum = 1003;BA.debugLine="txtPresRdg.Enabled = False";
mostCurrent._txtpresrdg.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1004;BA.debugLine="lblPresCum.Text = PresCum";
mostCurrent._lblprescum.setText(BA.ObjectToCharSequence(mostCurrent._prescum));
 //BA.debugLineNum = 1005;BA.debugLine="IMEkeyboard.HideKeyboard";
mostCurrent._imekeyboard.HideKeyboard(mostCurrent.activityBA);
 //BA.debugLineNum = 1006;BA.debugLine="cKeyboard.HideKeyboard";
mostCurrent._ckeyboard.HideKeyboard();
 }else {
 //BA.debugLineNum = 1008;BA.debugLine="T1.Enabled = False";
mostCurrent._t1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1009;BA.debugLine="lblReadStatus.Visible = True";
mostCurrent._lblreadstatus.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1010;BA.debugLine="lblReadStatus.Text = \"READ\"";
mostCurrent._lblreadstatus.setText(BA.ObjectToCharSequence("READ"));
 //BA.debugLineNum = 1011;BA.debugLine="lblReadStatus.TextColor = 0xFF14C0DB";
mostCurrent._lblreadstatus.setTextColor((int) (0xff14c0db));
 //BA.debugLineNum = 1012;BA.debugLine="btnEdit.Enabled = True";
mostCurrent._btnedit.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1013;BA.debugLine="btnReprint.Enabled = True";
mostCurrent._btnreprint.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1014;BA.debugLine="btnRemarks.Enabled = True";
mostCurrent._btnremarks.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1015;BA.debugLine="txtPresRdg.Text = PresRdg";
mostCurrent._txtpresrdg.setText(BA.ObjectToCharSequence(mostCurrent._presrdg));
 //BA.debugLineNum = 1017;BA.debugLine="txtPresRdg.Enabled = False";
mostCurrent._txtpresrdg.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1018;BA.debugLine="lblPresCum.Text = PresCum";
mostCurrent._lblprescum.setText(BA.ObjectToCharSequence(mostCurrent._prescum));
 //BA.debugLineNum = 1019;BA.debugLine="IMEkeyboard.HideKeyboard";
mostCurrent._imekeyboard.HideKeyboard(mostCurrent.activityBA);
 //BA.debugLineNum = 1020;BA.debugLine="cKeyboard.HideKeyboard";
mostCurrent._ckeyboard.HideKeyboard();
 };
 //BA.debugLineNum = 1023;BA.debugLine="If WasMisCoded = 1 Then";
if (_wasmiscoded==1) { 
 //BA.debugLineNum = 1024;BA.debugLine="lblMissCode.Text = MisCodeDesc";
mostCurrent._lblmisscode.setText(BA.ObjectToCharSequence(mostCurrent._miscodedesc));
 //BA.debugLineNum = 1025;BA.debugLine="lblMissCode.TextColor = Colors.Red";
mostCurrent._lblmisscode.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1026;BA.debugLine="lblFindings.TextColor = Colors.Red";
mostCurrent._lblfindings.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 }else {
 //BA.debugLineNum = 1028;BA.debugLine="lblMissCode.Text = \"N/A\"";
mostCurrent._lblmisscode.setText(BA.ObjectToCharSequence("N/A"));
 //BA.debugLineNum = 1029;BA.debugLine="lblMissCode.TextColor = 0xFF3A3A3A";
mostCurrent._lblmisscode.setTextColor((int) (0xff3a3a3a));
 //BA.debugLineNum = 1030;BA.debugLine="lblFindings.TextColor = 0xFF3A3A3A";
mostCurrent._lblfindings.setTextColor((int) (0xff3a3a3a));
 };
 //BA.debugLineNum = 1033;BA.debugLine="If FFindings.Length > 0 Then";
if (mostCurrent._ffindings.length()>0) { 
 //BA.debugLineNum = 1034;BA.debugLine="lblFindings.Text = FFindings";
mostCurrent._lblfindings.setText(BA.ObjectToCharSequence(mostCurrent._ffindings));
 }else {
 //BA.debugLineNum = 1036;BA.debugLine="lblFindings.Text = \"Actual Reading\"";
mostCurrent._lblfindings.setText(BA.ObjectToCharSequence("Actual Reading"));
 };
 //BA.debugLineNum = 1039;BA.debugLine="If FWarnings.Length > 0  Then";
if (mostCurrent._fwarnings.length()>0) { 
 //BA.debugLineNum = 1040;BA.debugLine="If FWarnings<>\"NONE\" Then";
if ((mostCurrent._fwarnings).equals("NONE") == false) { 
 //BA.debugLineNum = 1041;BA.debugLine="lblWarning.Text = FWarnings";
mostCurrent._lblwarning.setText(BA.ObjectToCharSequence(mostCurrent._fwarnings));
 //BA.debugLineNum = 1042;BA.debugLine="lblWarning.TextColor = Colors.Red";
mostCurrent._lblwarning.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 }else {
 //BA.debugLineNum = 1044;BA.debugLine="lblWarning.TextColor = 0xFF3A3A3A";
mostCurrent._lblwarning.setTextColor((int) (0xff3a3a3a));
 };
 }else {
 //BA.debugLineNum = 1047;BA.debugLine="lblWarning.Text = \"N/A\"";
mostCurrent._lblwarning.setText(BA.ObjectToCharSequence("N/A"));
 //BA.debugLineNum = 1048;BA.debugLine="lblWarning.TextColor = 0xFF3A3A3A";
mostCurrent._lblwarning.setTextColor((int) (0xff3a3a3a));
 };
 //BA.debugLineNum = 1051;BA.debugLine="LogColor(ReadRemarks, Colors.Yellow)";
anywheresoftware.b4a.keywords.Common.LogImpl("122020286",mostCurrent._readremarks,anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 //BA.debugLineNum = 1052;BA.debugLine="If GlobalVar.SF.Len(GlobalVar.SF.Trim(ReadRemarks";
if (mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv4(mostCurrent._readremarks))>0) { 
 //BA.debugLineNum = 1053;BA.debugLine="lblRemarks.Text = ReadRemarks";
mostCurrent._lblremarks.setText(BA.ObjectToCharSequence(mostCurrent._readremarks));
 }else {
 //BA.debugLineNum = 1055;BA.debugLine="lblRemarks.Text = \"N/A\"";
mostCurrent._lblremarks.setText(BA.ObjectToCharSequence("N/A"));
 };
 //BA.debugLineNum = 1059;BA.debugLine="If AcctStatus = \"dc\" Then";
if ((mostCurrent._acctstatus).equals("dc")) { 
 //BA.debugLineNum = 1060;BA.debugLine="txtPresRdg.Text = FinalRdg";
mostCurrent._txtpresrdg.setText(BA.ObjectToCharSequence(_finalrdg));
 //BA.debugLineNum = 1061;BA.debugLine="txtPresRdg.RequestFocus";
mostCurrent._txtpresrdg.RequestFocus();
 //BA.debugLineNum = 1062;BA.debugLine="txtPresRdg.Enabled = True";
mostCurrent._txtpresrdg.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1064;BA.debugLine="txtPresRdg.SelectAll";
mostCurrent._txtpresrdg.SelectAll();
 //BA.debugLineNum = 1065;BA.debugLine="cKeyboard.ShowKeyboard(txtPresRdg)";
mostCurrent._ckeyboard.ShowKeyboard((android.widget.EditText)(mostCurrent._txtpresrdg.getObject()));
 //BA.debugLineNum = 1066;BA.debugLine="cKeyboard.SetCustomFilter(txtPresRdg, txtPresRdg";
mostCurrent._ckeyboard.SetCustomFilter((android.widget.EditText)(mostCurrent._txtpresrdg.getObject()),mostCurrent._txtpresrdg.INPUT_TYPE_NUMBERS,"");
 //BA.debugLineNum = 1067;BA.debugLine="lblDiscon.Visible = True";
mostCurrent._lbldiscon.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 1069;BA.debugLine="lblDiscon.Visible = False";
mostCurrent._lbldiscon.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1073;BA.debugLine="intCurrPos = rsLoadedBook.Position";
_intcurrpos = mostCurrent._rsloadedbook.getPosition();
 //BA.debugLineNum = 1074;BA.debugLine="BookMark = rsLoadedBook.Position + 1";
_bookmark = (int) (mostCurrent._rsloadedbook.getPosition()+1);
 //BA.debugLineNum = 1075;BA.debugLine="CountUnReadAcct = CountUnread(GlobalVar.BookID)";
_countunreadacct = _countunread(mostCurrent._globalvar._bookid /*int*/ );
 //BA.debugLineNum = 1077;BA.debugLine="lblNoAccts.Text = $\"Record \"$ & BookMark & $\" of";
mostCurrent._lblnoaccts.setText(BA.ObjectToCharSequence(("Record ")+BA.NumberToString(_bookmark)+(" of ")+BA.NumberToString(_reccount)));
 //BA.debugLineNum = 1078;BA.debugLine="lblUnreadCount.Text = $\"Unread Acct(s). : \"$ & Co";
mostCurrent._lblunreadcount.setText(BA.ObjectToCharSequence(("Unread Acct(s). : ")+BA.NumberToString(_countunreadacct)));
 //BA.debugLineNum = 1079;BA.debugLine="End Sub";
return "";
}
public static String  _editdialog_buttonpressed(de.amberhome.materialdialogs.MaterialDialogWrapper _mdialog,String _saction) throws Exception{
 //BA.debugLineNum = 5287;BA.debugLine="Sub EditDialog_ButtonPressed (mDialog As MaterialD";
 //BA.debugLineNum = 5288;BA.debugLine="Select Case sAction";
switch (BA.switchObjectToInt(_saction,_mdialog.ACTION_POSITIVE,_mdialog.ACTION_NEGATIVE)) {
case 0: {
 //BA.debugLineNum = 5290;BA.debugLine="blnEdit = True";
_blnedit = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 5291;BA.debugLine="txtPresRdg.Enabled=True";
mostCurrent._txtpresrdg.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 5292;BA.debugLine="txtPresRdg.RequestFocus";
mostCurrent._txtpresrdg.RequestFocus();
 //BA.debugLineNum = 5293;BA.debugLine="txtPresRdg.SelectionStart=0";
mostCurrent._txtpresrdg.setSelectionStart((int) (0));
 //BA.debugLineNum = 5294;BA.debugLine="txtPresRdg.SelectAll";
mostCurrent._txtpresrdg.SelectAll();
 break; }
case 1: {
 //BA.debugLineNum = 5297;BA.debugLine="snack.Initialize(\"\",Activity,$\"Editing Reading";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Editing Reading Cancelled!"),mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 5298;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 5299;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 5300;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 break; }
}
;
 //BA.debugLineNum = 5303;BA.debugLine="End Sub";
return "";
}
public static String  _editremarksdialog_buttonpressed(de.amberhome.materialdialogs.MaterialDialogWrapper _dialog,String _action) throws Exception{
 //BA.debugLineNum = 2402;BA.debugLine="Sub EditRemarksDialog_ButtonPressed (Dialog As Mat";
 //BA.debugLineNum = 2403;BA.debugLine="Select Case Action";
switch (BA.switchObjectToInt(_action,_dialog.ACTION_POSITIVE,_dialog.ACTION_NEGATIVE)) {
case 0: {
 //BA.debugLineNum = 2405;BA.debugLine="intCurrPos = rsLoadedBook.Position";
_intcurrpos = mostCurrent._rsloadedbook.getPosition();
 //BA.debugLineNum = 2406;BA.debugLine="SaveNewRemarks(ReadRemarks, ReadID, AcctID)";
_savenewremarks(mostCurrent._readremarks,_readid,_acctid);
 //BA.debugLineNum = 2407;BA.debugLine="If RetrieveRecords(GlobalVar.BookID)=False Then";
if (_retrieverecords(mostCurrent._globalvar._bookid /*int*/ )==anywheresoftware.b4a.keywords.Common.False) { 
if (true) return "";};
 //BA.debugLineNum = 2408;BA.debugLine="If intCurrPos < (RecCount - 1) Then";
if (_intcurrpos<(_reccount-1)) { 
 //BA.debugLineNum = 2409;BA.debugLine="rsLoadedBook.Position = intCurrPos + 1";
mostCurrent._rsloadedbook.setPosition((int) (_intcurrpos+1));
 }else {
 //BA.debugLineNum = 2411;BA.debugLine="rsLoadedBook.Position = intCurrPos";
mostCurrent._rsloadedbook.setPosition(_intcurrpos);
 };
 //BA.debugLineNum = 2413;BA.debugLine="DisplayRecord";
_displayrecord();
 //BA.debugLineNum = 2414;BA.debugLine="ButtonState";
_buttonstate();
 break; }
case 1: {
 //BA.debugLineNum = 2416;BA.debugLine="snack.Initialize(\"\", Activity, $\"Editting Readi";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Editting Reading Remarks Cancelled."),mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 2417;BA.debugLine="SetSnackBarBackground(snack, Colors.White)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 2418;BA.debugLine="SetSnackBarTextColor(snack, Colors.Red)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 2419;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 2420;BA.debugLine="IMEkeyboard.HideKeyboard";
mostCurrent._imekeyboard.HideKeyboard(mostCurrent.activityBA);
 //BA.debugLineNum = 2421;BA.debugLine="Return";
if (true) return "";
 break; }
}
;
 //BA.debugLineNum = 2423;BA.debugLine="End Sub";
return "";
}
public static String  _editremarksdialog_inputchanged(de.amberhome.materialdialogs.MaterialDialogWrapper _mdialog,String _sremarks) throws Exception{
 //BA.debugLineNum = 2391;BA.debugLine="Sub EditRemarksDialog_InputChanged (mDialog As Mat";
 //BA.debugLineNum = 2392;BA.debugLine="If sRemarks.Length=0 Then";
if (_sremarks.length()==0) { 
 //BA.debugLineNum = 2393;BA.debugLine="mDialog.Content=\"Nothing to save.\"";
_mdialog.setContent(BA.ObjectToCharSequence("Nothing to save."));
 //BA.debugLineNum = 2394;BA.debugLine="mDialog.EnableActionButton(mDialog.ACTION_POSITI";
_mdialog.EnableActionButton(_mdialog.ACTION_POSITIVE,anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 2396;BA.debugLine="mDialog.Content=\"Please Enter a Remarks for this";
_mdialog.setContent(BA.ObjectToCharSequence("Please Enter a Remarks for this Account."));
 //BA.debugLineNum = 2397;BA.debugLine="mDialog.EnableActionButton(mDialog.ACTION_POSITI";
_mdialog.EnableActionButton(_mdialog.ACTION_POSITIVE,anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 2399;BA.debugLine="ReadRemarks = sRemarks";
mostCurrent._readremarks = _sremarks;
 //BA.debugLineNum = 2400;BA.debugLine="End Sub";
return "";
}
public static String  _enablecontrols() throws Exception{
 //BA.debugLineNum = 1168;BA.debugLine="Sub EnableControls";
 //BA.debugLineNum = 1169;BA.debugLine="pnlMain.Enabled = True";
mostCurrent._pnlmain.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1170;BA.debugLine="ToolBar.Enabled = True";
mostCurrent._toolbar.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1171;BA.debugLine="pnlAccountInfo.Enabled = True";
mostCurrent._pnlaccountinfo.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1172;BA.debugLine="pnlPrevious.Enabled = True";
mostCurrent._pnlprevious.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1173;BA.debugLine="pnlPresent.Enabled = True";
mostCurrent._pnlpresent.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1174;BA.debugLine="pnlFindings.Enabled = True";
mostCurrent._pnlfindings.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1175;BA.debugLine="pnlButtons.Enabled = True";
mostCurrent._pnlbuttons.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1176;BA.debugLine="pnlStatus.Enabled = True";
mostCurrent._pnlstatus.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1178;BA.debugLine="ToolBar.Menu.FindItem(1).Enabled = True";
mostCurrent._toolbar.getMenu().FindItem((int) (1)).setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1179;BA.debugLine="ToolBar.Menu.FindItem(2).Enabled = True";
mostCurrent._toolbar.getMenu().FindItem((int) (2)).setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1180;BA.debugLine="ToolBar.Menu.FindItem(3).Enabled = True";
mostCurrent._toolbar.getMenu().FindItem((int) (3)).setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1181;BA.debugLine="End Sub";
return "";
}
public static String  _findsearchretvalue(String _ssearch) throws Exception{
int _i = 0;
 //BA.debugLineNum = 2018;BA.debugLine="Sub FindSearchRetValue(sSearch As String)";
 //BA.debugLineNum = 2019;BA.debugLine="Try";
try { //BA.debugLineNum = 2020;BA.debugLine="Log(rsLoadedBook.Position)";
anywheresoftware.b4a.keywords.Common.LogImpl("124313858",BA.NumberToString(mostCurrent._rsloadedbook.getPosition()),0);
 //BA.debugLineNum = 2022;BA.debugLine="For i = 0 To rsLoadedBook.RowCount - 1";
{
final int step3 = 1;
final int limit3 = (int) (mostCurrent._rsloadedbook.getRowCount()-1);
_i = (int) (0) ;
for (;_i <= limit3 ;_i = _i + step3 ) {
 //BA.debugLineNum = 2023;BA.debugLine="rsLoadedBook.Position = i";
mostCurrent._rsloadedbook.setPosition(_i);
 //BA.debugLineNum = 2024;BA.debugLine="If sSearch = rsLoadedBook.GetString(\"ReadID\") T";
if ((_ssearch).equals(mostCurrent._rsloadedbook.GetString("ReadID"))) { 
 //BA.debugLineNum = 2025;BA.debugLine="ReadID = sSearch";
_readid = (int)(Double.parseDouble(_ssearch));
 //BA.debugLineNum = 2026;BA.debugLine="BookMark = rsLoadedBook.Position + 1";
_bookmark = (int) (mostCurrent._rsloadedbook.getPosition()+1);
 //BA.debugLineNum = 2027;BA.debugLine="DisplayRecord";
_displayrecord();
 //BA.debugLineNum = 2028;BA.debugLine="ButtonState";
_buttonstate();
 //BA.debugLineNum = 2029;BA.debugLine="Exit";
if (true) break;
 };
 }
};
 //BA.debugLineNum = 2032;BA.debugLine="Log(rsLoadedBook.Position)";
anywheresoftware.b4a.keywords.Common.LogImpl("124313870",BA.NumberToString(mostCurrent._rsloadedbook.getPosition()),0);
 } 
       catch (Exception e15) {
			processBA.setLastException(e15); //BA.debugLineNum = 2034;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("124313872",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 2036;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.ConcreteViewWrapper  _findviewbytag(anywheresoftware.b4a.objects.PanelWrapper _parent,String _name) throws Exception{
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
 //BA.debugLineNum = 849;BA.debugLine="Sub FindViewByTag(Parent As Panel, Name As String)";
 //BA.debugLineNum = 850;BA.debugLine="For Each v As View In Parent.GetAllViewsRecursive";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
{
final anywheresoftware.b4a.BA.IterableList group1 = _parent.GetAllViewsRecursive();
final int groupLen1 = group1.getSize()
;int index1 = 0;
;
for (; index1 < groupLen1;index1++){
_v = (anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(group1.Get(index1)));
 //BA.debugLineNum = 851;BA.debugLine="If Name = v.Tag Then Return v";
if ((_name).equals(BA.ObjectToString(_v.getTag()))) { 
if (true) return _v;};
 }
};
 //BA.debugLineNum = 853;BA.debugLine="Log(\"View not found: \" & Name)";
anywheresoftware.b4a.keywords.Common.LogImpl("121954564","View not found: "+_name,0);
 //BA.debugLineNum = 854;BA.debugLine="Return Null";
if (true) return (anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 855;BA.debugLine="End Sub";
return null;
}
public static String  _getimplosivetype(int _iprevcum,int _iprescum) throws Exception{
String _sretval = "";
 //BA.debugLineNum = 3278;BA.debugLine="Private Sub GetImplosiveType(iPrevCum As Int, iPre";
 //BA.debugLineNum = 3279;BA.debugLine="Dim sRetVal As String";
_sretval = "";
 //BA.debugLineNum = 3280;BA.debugLine="Try";
try { //BA.debugLineNum = 3281;BA.debugLine="If iPresCum = 0 Then";
if (_iprescum==0) { 
 //BA.debugLineNum = 3282;BA.debugLine="sRetVal = \"zero\"";
_sretval = "zero";
 }else if(_iprescum<0) { 
 //BA.debugLineNum = 3284;BA.debugLine="sRetVal = \"negative\"";
_sretval = "negative";
 }else if((_iprescum-_iprevcum)>=20) { 
 //BA.debugLineNum = 3286;BA.debugLine="sRetVal = \"implosive-inc\"";
_sretval = "implosive-inc";
 }else if((_iprevcum-_iprescum)>=20) { 
 //BA.debugLineNum = 3288;BA.debugLine="sRetVal = \"implosive-dec\"";
_sretval = "implosive-dec";
 }else {
 //BA.debugLineNum = 3290;BA.debugLine="sRetVal = \"posted\"";
_sretval = "posted";
 };
 } 
       catch (Exception e15) {
			processBA.setLastException(e15); //BA.debugLineNum = 3293;BA.debugLine="ToastMessageShow($\"Unable to get Implosive Type";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to get Implosive Type due to ")+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3294;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("126148880",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 3296;BA.debugLine="Return sRetVal";
if (true) return _sretval;
 //BA.debugLineNum = 3297;BA.debugLine="End Sub";
return "";
}
public static de.amberhome.objects.appcompat.ACMenuItemWrapper  _getmenuitem(String _title) throws Exception{
int _i = 0;
de.amberhome.objects.appcompat.ACMenuItemWrapper _m = null;
 //BA.debugLineNum = 700;BA.debugLine="Sub GetMenuItem(Title As String) As ACMenuItem";
 //BA.debugLineNum = 701;BA.debugLine="For i = 0 To ToolBar.Menu.Size - 1";
{
final int step1 = 1;
final int limit1 = (int) (mostCurrent._toolbar.getMenu().Size()-1);
_i = (int) (0) ;
for (;_i <= limit1 ;_i = _i + step1 ) {
 //BA.debugLineNum = 702;BA.debugLine="Dim m As ACMenuItem = ToolBar.Menu.GetItem(i)";
_m = new de.amberhome.objects.appcompat.ACMenuItemWrapper();
_m = mostCurrent._toolbar.getMenu().GetItem(_i);
 //BA.debugLineNum = 703;BA.debugLine="If m.Title = Title Then";
if ((_m.getTitle()).equals(_title)) { 
 //BA.debugLineNum = 704;BA.debugLine="Return m";
if (true) return _m;
 };
 }
};
 //BA.debugLineNum = 707;BA.debugLine="Return Null";
if (true) return (de.amberhome.objects.appcompat.ACMenuItemWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new de.amberhome.objects.appcompat.ACMenuItemWrapper(), (android.view.MenuItem)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 708;BA.debugLine="End Sub";
return null;
}
public static double  _getseniorafter(int _ireadid,double _dtotcum) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsscafter = null;
double _dbillamt = 0;
double _dmaxcum = 0;
double _fpct = 0;
double _retseniordiscount = 0;
double _maxpcaamount = 0;
 //BA.debugLineNum = 3376;BA.debugLine="Private Sub GetSeniorAfter(iReadID As Int, dTotCum";
 //BA.debugLineNum = 3377;BA.debugLine="Dim rsSCAfter As Cursor";
_rsscafter = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 3379;BA.debugLine="Dim dBillAmt As Double";
_dbillamt = 0;
 //BA.debugLineNum = 3380;BA.debugLine="Dim dMaxCum As Double";
_dmaxcum = 0;
 //BA.debugLineNum = 3381;BA.debugLine="Dim fPct As Double";
_fpct = 0;
 //BA.debugLineNum = 3382;BA.debugLine="Dim RetSeniorDiscount As Double";
_retseniordiscount = 0;
 //BA.debugLineNum = 3383;BA.debugLine="Dim maxPCAAmount = 0 As Double";
_maxpcaamount = 0;
 //BA.debugLineNum = 3384;BA.debugLine="Try";
try { //BA.debugLineNum = 3385;BA.debugLine="Starter.strCriteria = \"SELECT * FROM tblReadings";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblReadings "+"WHERE ReadID = "+BA.NumberToString(_ireadid);
 //BA.debugLineNum = 3388;BA.debugLine="rsSCAfter = Starter.DBCon.ExecQuery(Starter.strC";
_rsscafter = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 3389;BA.debugLine="If rsSCAfter.RowCount > 0 Then";
if (_rsscafter.getRowCount()>0) { 
 //BA.debugLineNum = 3390;BA.debugLine="rsSCAfter.Position=0";
_rsscafter.setPosition((int) (0));
 //BA.debugLineNum = 3391;BA.debugLine="fPct = rsSCAfter.GetDouble(\"SeniorAfter\")";
_fpct = _rsscafter.GetDouble("SeniorAfter");
 //BA.debugLineNum = 3392;BA.debugLine="dMaxCum = rsSCAfter.GetDouble(\"SeniorMaxCum\")";
_dmaxcum = _rsscafter.GetDouble("SeniorMaxCum");
 //BA.debugLineNum = 3394;BA.debugLine="maxPCAAmount = dMaxCum * PCA";
_maxpcaamount = _dmaxcum*_pca;
 //BA.debugLineNum = 3396;BA.debugLine="If PCAAmt > maxPCAAmount Then";
if (_pcaamt>_maxpcaamount) { 
 //BA.debugLineNum = 3397;BA.debugLine="dBillAmt = (DBaseFunctions.ComputeBasicAmt(dMa";
_dbillamt = (mostCurrent._dbasefunctions._computebasicamt /*double*/ (mostCurrent.activityBA,(int) (_dmaxcum),mostCurrent._globalvar._branchid /*int*/ ,mostCurrent._acctclass,mostCurrent._acctsubclass))+_maxpcaamount;
 }else if(_pcaamt==0) { 
 //BA.debugLineNum = 3399;BA.debugLine="If TotalCum > dMaxCum Then";
if (_totalcum>_dmaxcum) { 
 //BA.debugLineNum = 3400;BA.debugLine="dBillAmt = (DBaseFunctions.ComputeBasicAmt(dM";
_dbillamt = (mostCurrent._dbasefunctions._computebasicamt /*double*/ (mostCurrent.activityBA,(int) (_dmaxcum),mostCurrent._globalvar._branchid /*int*/ ,mostCurrent._acctclass,mostCurrent._acctsubclass));
 }else {
 //BA.debugLineNum = 3402;BA.debugLine="dBillAmt = CurrentAmt";
_dbillamt = _currentamt;
 };
 }else {
 //BA.debugLineNum = 3405;BA.debugLine="dBillAmt = CurrentAmt";
_dbillamt = _currentamt;
 };
 //BA.debugLineNum = 3407;BA.debugLine="RetSeniorDiscount = dBillAmt * fPct";
_retseniordiscount = _dbillamt*_fpct;
 };
 } 
       catch (Exception e29) {
			processBA.setLastException(e29); //BA.debugLineNum = 3410;BA.debugLine="ToastMessageShow($\"Unable to get Senior Discount";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to get Senior Discount After Due Date due to ")+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 3412;BA.debugLine="rsSCAfter.Close";
_rsscafter.Close();
 //BA.debugLineNum = 3413;BA.debugLine="Return RetSeniorDiscount";
if (true) return _retseniordiscount;
 //BA.debugLineNum = 3414;BA.debugLine="End Sub";
return 0;
}
public static double  _getseniorbefore(int _ireadid,double _dtotcum) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsscbefore = null;
double _dbillamt = 0;
double _dmaxcum = 0;
double _fpct = 0;
double _retseniordiscount = 0;
double _maxpcaamount = 0;
 //BA.debugLineNum = 3334;BA.debugLine="Private Sub GetSeniorBefore(iReadID As Int, dTotCu";
 //BA.debugLineNum = 3335;BA.debugLine="Dim rsSCBefore As Cursor";
_rsscbefore = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 3337;BA.debugLine="Dim dBillAmt As Double";
_dbillamt = 0;
 //BA.debugLineNum = 3338;BA.debugLine="Dim dMaxCum As Double";
_dmaxcum = 0;
 //BA.debugLineNum = 3339;BA.debugLine="Dim fPct As Double";
_fpct = 0;
 //BA.debugLineNum = 3340;BA.debugLine="Dim RetSeniorDiscount As Double";
_retseniordiscount = 0;
 //BA.debugLineNum = 3341;BA.debugLine="Dim maxPCAAmount = 0 As Double";
_maxpcaamount = 0;
 //BA.debugLineNum = 3343;BA.debugLine="Try";
try { //BA.debugLineNum = 3344;BA.debugLine="Starter.strCriteria = \"SELECT * FROM tblReadings";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblReadings "+"WHERE ReadID = "+BA.NumberToString(_ireadid);
 //BA.debugLineNum = 3347;BA.debugLine="rsSCBefore = Starter.DBCon.ExecQuery(Starter.str";
_rsscbefore = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 3348;BA.debugLine="If rsSCBefore.RowCount > 0 Then";
if (_rsscbefore.getRowCount()>0) { 
 //BA.debugLineNum = 3349;BA.debugLine="rsSCBefore.Position = 0";
_rsscbefore.setPosition((int) (0));
 //BA.debugLineNum = 3350;BA.debugLine="fPct = rsSCBefore.GetDouble(\"SeniorOnBefore\")";
_fpct = _rsscbefore.GetDouble("SeniorOnBefore");
 //BA.debugLineNum = 3351;BA.debugLine="dMaxCum = rsSCBefore.GetDouble(\"SeniorMaxCum\")";
_dmaxcum = _rsscbefore.GetDouble("SeniorMaxCum");
 //BA.debugLineNum = 3353;BA.debugLine="maxPCAAmount = dMaxCum * PCA";
_maxpcaamount = _dmaxcum*_pca;
 //BA.debugLineNum = 3355;BA.debugLine="If PCAAmt > maxPCAAmount Then";
if (_pcaamt>_maxpcaamount) { 
 //BA.debugLineNum = 3356;BA.debugLine="dBillAmt = (DBaseFunctions.ComputeBasicAmt(dMa";
_dbillamt = (mostCurrent._dbasefunctions._computebasicamt /*double*/ (mostCurrent.activityBA,(int) (_dmaxcum),mostCurrent._globalvar._branchid /*int*/ ,mostCurrent._acctclass,mostCurrent._acctsubclass))+_maxpcaamount;
 }else if(_pcaamt==0) { 
 //BA.debugLineNum = 3358;BA.debugLine="If TotalCum > dMaxCum Then";
if (_totalcum>_dmaxcum) { 
 //BA.debugLineNum = 3359;BA.debugLine="dBillAmt = (DBaseFunctions.ComputeBasicAmt(dM";
_dbillamt = (mostCurrent._dbasefunctions._computebasicamt /*double*/ (mostCurrent.activityBA,(int) (_dmaxcum),mostCurrent._globalvar._branchid /*int*/ ,mostCurrent._acctclass,mostCurrent._acctsubclass));
 }else {
 //BA.debugLineNum = 3361;BA.debugLine="dBillAmt = CurrentAmt";
_dbillamt = _currentamt;
 };
 }else {
 //BA.debugLineNum = 3364;BA.debugLine="dBillAmt = CurrentAmt";
_dbillamt = _currentamt;
 };
 //BA.debugLineNum = 3366;BA.debugLine="RetSeniorDiscount = dBillAmt * fPct";
_retseniordiscount = _dbillamt*_fpct;
 };
 } 
       catch (Exception e29) {
			processBA.setLastException(e29); //BA.debugLineNum = 3369;BA.debugLine="ToastMessageShow($\"Unable to get Senior Discount";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to get Senior Discount Before Due Date due to ")+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3370;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("126345508",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 3372;BA.debugLine="rsSCBefore.Close";
_rsscbefore.Close();
 //BA.debugLineNum = 3373;BA.debugLine="Return RetSeniorDiscount";
if (true) return _retseniordiscount;
 //BA.debugLineNum = 3374;BA.debugLine="End Sub";
return 0;
}
public static boolean  _getunusualcount(int _ibookid) throws Exception{
boolean _blnretval = false;
 //BA.debugLineNum = 1309;BA.debugLine="Private Sub GetUnusualCount(iBookID As Int) As Boo";
 //BA.debugLineNum = 1310;BA.debugLine="Dim blnRetVal As Boolean";
_blnretval = false;
 //BA.debugLineNum = 1311;BA.debugLine="Try";
try { //BA.debugLineNum = 1312;BA.debugLine="Starter.strCriteria = \"SELECT SUM(CASE WHEN WasM";
mostCurrent._starter._strcriteria /*String*/  = "SELECT SUM(CASE WHEN WasMissCoded = 1 THEN 1 ELSE 0 END) as MissCoded, "+"SUM(CASE WHEN ImplosiveType = 'zero' Then 1 ELSE 0 END) As ZeroCons, "+"SUM(CASE WHEN ImplosiveType = 'implosive-inc' Then 1 ELSE 0 END) As HighBill, "+"SUM(CASE WHEN ImplosiveType = 'implosive-dec' Then 1 ELSE 0 END) As LowBill, "+"SUM(CASE WHEN BillType = 'AB' Then 1 ELSE 0 END) As AveBill, "+"SUM(CASE WHEN PrintStatus = 0 Then 1 ELSE 0 END) As Unprinted "+"FROM tblReadings "+"WHERE BookID = "+BA.NumberToString(_ibookid)+" "+"AND BillYear = "+BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ )+" "+"AND BillMonth = "+BA.NumberToString(mostCurrent._globalvar._billmonth /*int*/ )+" "+"AND WasRead = 1 "+"AND ReadBy = "+BA.NumberToString(mostCurrent._globalvar._userid /*int*/ );
 //BA.debugLineNum = 1324;BA.debugLine="rsUnusualReading = Starter.DBCon.ExecQuery(Start";
mostCurrent._rsunusualreading = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 1325;BA.debugLine="If rsUnusualReading.RowCount > 0 Then";
if (mostCurrent._rsunusualreading.getRowCount()>0) { 
 //BA.debugLineNum = 1326;BA.debugLine="rsUnusualReading.Position = 0";
mostCurrent._rsunusualreading.setPosition((int) (0));
 //BA.debugLineNum = 1327;BA.debugLine="blnRetVal = True";
_blnretval = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 1328;BA.debugLine="intMissCode = rsUnusualReading.GetInt(\"MissCode";
_intmisscode = mostCurrent._rsunusualreading.GetInt("MissCoded");
 //BA.debugLineNum = 1329;BA.debugLine="intZero = rsUnusualReading.GetInt(\"ZeroCons\")";
_intzero = mostCurrent._rsunusualreading.GetInt("ZeroCons");
 //BA.debugLineNum = 1330;BA.debugLine="intHigh = rsUnusualReading.GetInt(\"HighBill\")";
_inthigh = mostCurrent._rsunusualreading.GetInt("HighBill");
 //BA.debugLineNum = 1331;BA.debugLine="intLow = rsUnusualReading.GetInt(\"LowBill\")";
_intlow = mostCurrent._rsunusualreading.GetInt("LowBill");
 //BA.debugLineNum = 1332;BA.debugLine="intAve = rsUnusualReading.GetInt(\"AveBill\")";
_intave = mostCurrent._rsunusualreading.GetInt("AveBill");
 //BA.debugLineNum = 1333;BA.debugLine="intUnprinted = rsUnusualReading.GetInt(\"Unprint";
_intunprinted = mostCurrent._rsunusualreading.GetInt("Unprinted");
 //BA.debugLineNum = 1334;BA.debugLine="intTotal = intMissCode + intZero + intHigh + in";
_inttotal = (int) (_intmisscode+_intzero+_inthigh+_intlow+_intave+_intunprinted);
 }else {
 //BA.debugLineNum = 1336;BA.debugLine="blnRetVal = False";
_blnretval = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 1338;BA.debugLine="rsUnusualReading.Close";
mostCurrent._rsunusualreading.Close();
 //BA.debugLineNum = 1339;BA.debugLine="Return blnRetVal";
if (true) return _blnretval;
 } 
       catch (Exception e21) {
			processBA.setLastException(e21); //BA.debugLineNum = 1341;BA.debugLine="blnRetVal = False";
_blnretval = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 1342;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("122609953",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 1344;BA.debugLine="End Sub";
return false;
}
public static String  _getwarning(int _iprevcum,int _iprescum) throws Exception{
String _sretval = "";
 //BA.debugLineNum = 3299;BA.debugLine="Private Sub GetWarning(iPrevCum As Int, iPresCum A";
 //BA.debugLineNum = 3300;BA.debugLine="Dim sRetVal As String";
_sretval = "";
 //BA.debugLineNum = 3301;BA.debugLine="Try";
try { //BA.debugLineNum = 3302;BA.debugLine="If iPresCum = 0 Then";
if (_iprescum==0) { 
 //BA.debugLineNum = 3303;BA.debugLine="sRetVal = \"Zero Consumption\"";
_sretval = "Zero Consumption";
 }else if(_iprescum<0) { 
 //BA.debugLineNum = 3305;BA.debugLine="sRetVal = \"Negative Consumption\"";
_sretval = "Negative Consumption";
 }else if((_iprescum-_iprevcum)>=20) { 
 //BA.debugLineNum = 3307;BA.debugLine="sRetVal = \"High Bill\"";
_sretval = "High Bill";
 }else if((_iprevcum-_iprescum)>=20) { 
 //BA.debugLineNum = 3309;BA.debugLine="sRetVal = \"Low Bill\"";
_sretval = "Low Bill";
 }else {
 //BA.debugLineNum = 3311;BA.debugLine="sRetVal = \"NONE\"";
_sretval = "NONE";
 };
 } 
       catch (Exception e15) {
			processBA.setLastException(e15); //BA.debugLineNum = 3314;BA.debugLine="ToastMessageShow($\"Unable to get Implosive Type";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to get Implosive Type due to ")+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3315;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("126214416",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 3317;BA.debugLine="Return sRetVal";
if (true) return _sretval;
 //BA.debugLineNum = 3318;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 67;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 68;BA.debugLine="Private Panel1 As Panel";
mostCurrent._panel1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 69;BA.debugLine="Dim LvEffect As ListView";
mostCurrent._lveffect = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 71;BA.debugLine="Private camEx As CameraExClass";
mostCurrent._camex = new com.bwsi.MeterReader.cameraexclass();
 //BA.debugLineNum = 72;BA.debugLine="Private PnlFocus As Panel";
mostCurrent._pnlfocus = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 73;BA.debugLine="Dim cvsDrawing As Canvas";
mostCurrent._cvsdrawing = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 74;BA.debugLine="Private api As Int";
_api = 0;
 //BA.debugLineNum = 75;BA.debugLine="Dim focuscolor As Double";
_focuscolor = 0;
 //BA.debugLineNum = 77;BA.debugLine="Private ToolBar As ACToolBarDark";
mostCurrent._toolbar = new de.amberhome.objects.appcompat.ACToolbarDarkWrapper();
 //BA.debugLineNum = 78;BA.debugLine="Private ActionBarButton As ACActionBar";
mostCurrent._actionbarbutton = new de.amberhome.objects.appcompat.ACActionBar();
 //BA.debugLineNum = 79;BA.debugLine="Private xmlIcon As XmlLayoutBuilder";
mostCurrent._xmlicon = new anywheresoftware.b4a.object.XmlLayoutBuilder();
 //BA.debugLineNum = 80;BA.debugLine="Private PopWarnings As ACPopupMenu";
mostCurrent._popwarnings = new de.amberhome.objects.appcompat.ACPopupMenuWrapper();
 //BA.debugLineNum = 81;BA.debugLine="Private PopSubMenu As ACPopupMenu";
mostCurrent._popsubmenu = new de.amberhome.objects.appcompat.ACPopupMenuWrapper();
 //BA.debugLineNum = 82;BA.debugLine="Dim BookCode, BookDesc As String";
mostCurrent._bookcode = "";
mostCurrent._bookdesc = "";
 //BA.debugLineNum = 84;BA.debugLine="Private snack As DSSnackbar";
mostCurrent._snack = new de.amberhome.objects.SnackbarWrapper();
 //BA.debugLineNum = 85;BA.debugLine="Dim CD As ColorDrawable";
mostCurrent._cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 87;BA.debugLine="Private strSF As StringFunctions";
mostCurrent._strsf = new adr.stringfunctions.stringfunctions();
 //BA.debugLineNum = 88;BA.debugLine="Private IMEkeyboard As IME";
mostCurrent._imekeyboard = new anywheresoftware.b4a.objects.IME();
 //BA.debugLineNum = 89;BA.debugLine="Private WasEdited As Boolean";
_wasedited = false;
 //BA.debugLineNum = 90;BA.debugLine="Private SV As SearchView";
mostCurrent._sv = new com.bwsi.MeterReader.searchview();
 //BA.debugLineNum = 93;BA.debugLine="Private MatDialogBuilder As MaterialDialogBuilder";
mostCurrent._matdialogbuilder = new de.amberhome.materialdialogs.MaterialDialogBuilderWrapper();
 //BA.debugLineNum = 96;BA.debugLine="Private rsLoadedBook As Cursor";
mostCurrent._rsloadedbook = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 98;BA.debugLine="Private rsUnReadAcct As Cursor";
mostCurrent._rsunreadacct = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 99;BA.debugLine="Private rsReadAcct As Cursor";
mostCurrent._rsreadacct = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 100;BA.debugLine="Private rsAllAcct As Cursor";
mostCurrent._rsallacct = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 101;BA.debugLine="Private rsUnusualReading As Cursor";
mostCurrent._rsunusualreading = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 105;BA.debugLine="Private pnlMain As Panel";
mostCurrent._pnlmain = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 108;BA.debugLine="Private pnlAccountInfo As Panel";
mostCurrent._pnlaccountinfo = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 109;BA.debugLine="Private lblSeqNo As Label";
mostCurrent._lblseqno = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 110;BA.debugLine="Private lblMeter As Label";
mostCurrent._lblmeter = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 111;BA.debugLine="Private lblAcctNo As Label";
mostCurrent._lblacctno = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 112;BA.debugLine="Private lblAcctName As Label";
mostCurrent._lblacctname = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 113;BA.debugLine="Private lblAcctAddress As Label";
mostCurrent._lblacctaddress = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 116;BA.debugLine="Private pnlPrevious As Panel";
mostCurrent._pnlprevious = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 117;BA.debugLine="Private lblClass As Label";
mostCurrent._lblclass = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 118;BA.debugLine="Private lblPrevCum As Label";
mostCurrent._lblprevcum = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 119;BA.debugLine="Private lblReadStatus As Label";
mostCurrent._lblreadstatus = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 122;BA.debugLine="Private pnlPresent As Panel";
mostCurrent._pnlpresent = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 123;BA.debugLine="Private txtPresRdg As EditText";
mostCurrent._txtpresrdg = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 124;BA.debugLine="Private lblPresCum As Label";
mostCurrent._lblprescum = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 125;BA.debugLine="Private lblDiscon As Label";
mostCurrent._lbldiscon = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 128;BA.debugLine="Private pnlFindings As Panel";
mostCurrent._pnlfindings = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 129;BA.debugLine="Private lblFindings As Label";
mostCurrent._lblfindings = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 130;BA.debugLine="Private lblMissCode As Label";
mostCurrent._lblmisscode = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 131;BA.debugLine="Private lblWarning As Label";
mostCurrent._lblwarning = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 132;BA.debugLine="Private lblRemarks As Label";
mostCurrent._lblremarks = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 135;BA.debugLine="Private pnlButtons As Panel";
mostCurrent._pnlbuttons = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 136;BA.debugLine="Private btnPrevious As ACButton";
mostCurrent._btnprevious = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 137;BA.debugLine="Private btnNext As ACButton";
mostCurrent._btnnext = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 138;BA.debugLine="Private btnEdit As ACButton";
mostCurrent._btnedit = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 139;BA.debugLine="Private btnMissCode As ACButton";
mostCurrent._btnmisscode = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 140;BA.debugLine="Private btnRemarks As ACButton";
mostCurrent._btnremarks = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 141;BA.debugLine="Private btnReprint As ACButton";
mostCurrent._btnreprint = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 144;BA.debugLine="Private pnlStatus As Panel";
mostCurrent._pnlstatus = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 145;BA.debugLine="Private lblNoAccts As Label";
mostCurrent._lblnoaccts = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 146;BA.debugLine="Private lblUnreadCount As Label";
mostCurrent._lblunreadcount = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 151;BA.debugLine="Private ReadID As Int";
_readid = 0;
 //BA.debugLineNum = 152;BA.debugLine="Private BillNo As String";
mostCurrent._billno = "";
 //BA.debugLineNum = 153;BA.debugLine="Private BookNo As Int";
_bookno = 0;
 //BA.debugLineNum = 154;BA.debugLine="Private AcctID As Int";
_acctid = 0;
 //BA.debugLineNum = 155;BA.debugLine="Private AcctNo As String";
mostCurrent._acctno = "";
 //BA.debugLineNum = 156;BA.debugLine="Private AcctName As String";
mostCurrent._acctname = "";
 //BA.debugLineNum = 157;BA.debugLine="Private AcctAddress As String";
mostCurrent._acctaddress = "";
 //BA.debugLineNum = 158;BA.debugLine="Private AcctClass As String";
mostCurrent._acctclass = "";
 //BA.debugLineNum = 159;BA.debugLine="Private AcctSubClass As String";
mostCurrent._acctsubclass = "";
 //BA.debugLineNum = 160;BA.debugLine="Private AcctClassification As String'Classificati";
mostCurrent._acctclassification = "";
 //BA.debugLineNum = 161;BA.debugLine="Private AcctStatus As String";
mostCurrent._acctstatus = "";
 //BA.debugLineNum = 162;BA.debugLine="Private MeterNo As String";
mostCurrent._meterno = "";
 //BA.debugLineNum = 163;BA.debugLine="Private MaxReading As Double";
_maxreading = 0;
 //BA.debugLineNum = 164;BA.debugLine="Private SeqNo As String";
mostCurrent._seqno = "";
 //BA.debugLineNum = 165;BA.debugLine="Private IsSenior As Int";
_issenior = 0;
 //BA.debugLineNum = 166;BA.debugLine="Private SeniorOnBefore As Double";
_senioronbefore = 0;
 //BA.debugLineNum = 167;BA.debugLine="Private SeniorAfter As Double";
_seniorafter = 0;
 //BA.debugLineNum = 168;BA.debugLine="Private SeniorMaxCum As Double";
_seniormaxcum = 0;
 //BA.debugLineNum = 169;BA.debugLine="Private GDeposit As Double";
_gdeposit = 0;
 //BA.debugLineNum = 171;BA.debugLine="Private PrevRdgDate As String";
mostCurrent._prevrdgdate = "";
 //BA.debugLineNum = 172;BA.debugLine="Private PrevRdg As Int";
_prevrdg = 0;
 //BA.debugLineNum = 173;BA.debugLine="Private PrevCum As String";
mostCurrent._prevcum = "";
 //BA.debugLineNum = 175;BA.debugLine="Private FinalRdg As Int";
_finalrdg = 0;
 //BA.debugLineNum = 176;BA.debugLine="Private DisconDate As String";
mostCurrent._discondate = "";
 //BA.debugLineNum = 178;BA.debugLine="Private PresRdgDate As String";
mostCurrent._presrdgdate = "";
 //BA.debugLineNum = 179;BA.debugLine="Private PresRdg As String";
mostCurrent._presrdg = "";
 //BA.debugLineNum = 180;BA.debugLine="Private PresCum As String";
mostCurrent._prescum = "";
 //BA.debugLineNum = 182;BA.debugLine="Private DateFrom As String";
mostCurrent._datefrom = "";
 //BA.debugLineNum = 183;BA.debugLine="Private DateTo As String";
mostCurrent._dateto = "";
 //BA.debugLineNum = 184;BA.debugLine="Private WithDueDate As Int";
_withduedate = 0;
 //BA.debugLineNum = 185;BA.debugLine="Private DueDate As String";
mostCurrent._duedate = "";
 //BA.debugLineNum = 186;BA.debugLine="Private DisconnectionDate As String";
mostCurrent._disconnectiondate = "";
 //BA.debugLineNum = 187;BA.debugLine="Private AveCum As Double";
_avecum = 0;
 //BA.debugLineNum = 188;BA.debugLine="Private AddCons As Double";
_addcons = 0;
 //BA.debugLineNum = 189;BA.debugLine="Private CurrentCuM As Int";
_currentcum = 0;
 //BA.debugLineNum = 191;BA.debugLine="Private TotalCum As Int";
_totalcum = 0;
 //BA.debugLineNum = 192;BA.debugLine="Private BasicAmt As Double";
_basicamt = 0;
 //BA.debugLineNum = 193;BA.debugLine="Private PCA As Double";
_pca = 0;
 //BA.debugLineNum = 194;BA.debugLine="Private PCAAmt As Double";
_pcaamt = 0;
 //BA.debugLineNum = 195;BA.debugLine="Private AddToBillAmt As Double";
_addtobillamt = 0;
 //BA.debugLineNum = 196;BA.debugLine="Private AdvPayment As Double";
_advpayment = 0;
 //BA.debugLineNum = 197;BA.debugLine="Private RemainingAdvPayment As Double";
_remainingadvpayment = 0;
 //BA.debugLineNum = 198;BA.debugLine="Private PenaltyPct As Double";
_penaltypct = 0;
 //BA.debugLineNum = 199;BA.debugLine="Private PenaltyAmt As Double";
_penaltyamt = 0;
 //BA.debugLineNum = 200;BA.debugLine="Private TotalDueAmtAfterSC As Double";
_totaldueamtaftersc = 0;
 //BA.debugLineNum = 202;BA.debugLine="Private ReadRemarks As String";
mostCurrent._readremarks = "";
 //BA.debugLineNum = 203;BA.debugLine="Private sAveRem As String";
mostCurrent._saverem = "";
 //BA.debugLineNum = 204;BA.debugLine="Private strReadRemarks As String";
mostCurrent._strreadremarks = "";
 //BA.debugLineNum = 205;BA.debugLine="Private PaidStatus As Int";
_paidstatus = 0;
 //BA.debugLineNum = 206;BA.debugLine="Private PrintStatus As Int";
_printstatus = 0;
 //BA.debugLineNum = 207;BA.debugLine="Private WasMisCoded As Int";
_wasmiscoded = 0;
 //BA.debugLineNum = 208;BA.debugLine="Private MisCodeID As Int";
_miscodeid = 0;
 //BA.debugLineNum = 209;BA.debugLine="Private MisCodeDesc As String";
mostCurrent._miscodedesc = "";
 //BA.debugLineNum = 210;BA.debugLine="Private WasImplosive As Int";
_wasimplosive = 0;
 //BA.debugLineNum = 211;BA.debugLine="Private ImplosiveType As String";
mostCurrent._implosivetype = "";
 //BA.debugLineNum = 213;BA.debugLine="Private WasRead As Int";
_wasread = 0;
 //BA.debugLineNum = 214;BA.debugLine="Private FFindings As String";
mostCurrent._ffindings = "";
 //BA.debugLineNum = 215;BA.debugLine="Private FWarnings As String";
mostCurrent._fwarnings = "";
 //BA.debugLineNum = 216;BA.debugLine="Private NewSeqNo As Int";
_newseqno = 0;
 //BA.debugLineNum = 217;BA.debugLine="Private NoInput As Int";
_noinput = 0;
 //BA.debugLineNum = 218;BA.debugLine="Private TimeRead As String";
mostCurrent._timeread = "";
 //BA.debugLineNum = 219;BA.debugLine="Private DateRead As String";
mostCurrent._dateread = "";
 //BA.debugLineNum = 220;BA.debugLine="Private PrintTime as String";
mostCurrent._printtime = "";
 //BA.debugLineNum = 221;BA.debugLine="Private WasUploaded As Int";
_wasuploaded = 0;
 //BA.debugLineNum = 224;BA.debugLine="Private BillType As String";
mostCurrent._billtype = "";
 //BA.debugLineNum = 225;BA.debugLine="Private CurrentAmt As Double";
_currentamt = 0;
 //BA.debugLineNum = 226;BA.debugLine="Private ArrearsAmt As Double";
_arrearsamt = 0;
 //BA.debugLineNum = 227;BA.debugLine="Private TotalDueAmt As Double";
_totaldueamt = 0;
 //BA.debugLineNum = 228;BA.debugLine="Private SeniorOnBeforeAmt As Double";
_senioronbeforeamt = 0;
 //BA.debugLineNum = 229;BA.debugLine="Private SeniorAfterAmt As Double";
_seniorafteramt = 0;
 //BA.debugLineNum = 230;BA.debugLine="Private TotalDueAmtBeforeSC As Double";
_totaldueamtbeforesc = 0;
 //BA.debugLineNum = 232;BA.debugLine="Private intRateHeaderID As Int";
_intrateheaderid = 0;
 //BA.debugLineNum = 233;BA.debugLine="Private DiscAmt, DiscAmt2 As Double";
_discamt = 0;
_discamt2 = 0;
 //BA.debugLineNum = 235;BA.debugLine="Private MeterCharges As Double";
_metercharges = 0;
 //BA.debugLineNum = 236;BA.debugLine="Private FranchiseTaxPct As Double";
_franchisetaxpct = 0;
 //BA.debugLineNum = 237;BA.debugLine="Private FranchiseTaxAmt As Double";
_franchisetaxamt = 0;
 //BA.debugLineNum = 238;BA.debugLine="Private SeptageFeeAmt As Double";
_septagefeeamt = 0;
 //BA.debugLineNum = 239;BA.debugLine="Private SeptageFee As Double";
_septagefee = 0;
 //BA.debugLineNum = 240;BA.debugLine="Private SeptageArrears As Double";
_septagearrears = 0;
 //BA.debugLineNum = 241;BA.debugLine="Private GTotalSeptage As Double";
_gtotalseptage = 0;
 //BA.debugLineNum = 242;BA.debugLine="Private GTotalDue As Double";
_gtotaldue = 0;
 //BA.debugLineNum = 243;BA.debugLine="Private GTotalAfterDue As Double";
_gtotalafterdue = 0;
 //BA.debugLineNum = 244;BA.debugLine="Private GTotalDueAmt As Double";
_gtotaldueamt = 0;
 //BA.debugLineNum = 245;BA.debugLine="Private GTotalAfterDueAmt As Double";
_gtotalafterdueamt = 0;
 //BA.debugLineNum = 247;BA.debugLine="Private NoPrinted As Int";
_noprinted = 0;
 //BA.debugLineNum = 248;BA.debugLine="Private BillPeriod1st As String";
mostCurrent._billperiod1st = "";
 //BA.debugLineNum = 249;BA.debugLine="Private PrevCum1st As Int";
_prevcum1st = 0;
 //BA.debugLineNum = 250;BA.debugLine="Private BillPeriod2nd As String";
mostCurrent._billperiod2nd = "";
 //BA.debugLineNum = 251;BA.debugLine="Private PrevCum2nd As Int";
_prevcum2nd = 0;
 //BA.debugLineNum = 252;BA.debugLine="Private BillPeriod3rd As String";
mostCurrent._billperiod3rd = "";
 //BA.debugLineNum = 253;BA.debugLine="Private PrevCum3rd As Int";
_prevcum3rd = 0;
 //BA.debugLineNum = 255;BA.debugLine="Private HasSeptageFee As Int";
_hasseptagefee = 0;
 //BA.debugLineNum = 256;BA.debugLine="Private MinSeptageCum As Int";
_minseptagecum = 0;
 //BA.debugLineNum = 257;BA.debugLine="Private MaxSeptageCum As Int";
_maxseptagecum = 0;
 //BA.debugLineNum = 258;BA.debugLine="Private SeptageRate As Double";
_septagerate = 0;
 //BA.debugLineNum = 262;BA.debugLine="Private blnEdit As Boolean 'Edit button trigger";
_blnedit = false;
 //BA.debugLineNum = 264;BA.debugLine="Private BookMark = 0 As Int";
_bookmark = (int) (0);
 //BA.debugLineNum = 265;BA.debugLine="Private RecCount = 0 As Int";
_reccount = (int) (0);
 //BA.debugLineNum = 266;BA.debugLine="Private CountUnReadAcct As Int";
_countunreadacct = 0;
 //BA.debugLineNum = 267;BA.debugLine="Private intCurrPos As Int";
_intcurrpos = 0;
 //BA.debugLineNum = 268;BA.debugLine="Private intTempCurrPos As Int";
_inttempcurrpos = 0;
 //BA.debugLineNum = 270;BA.debugLine="Private flagBitmap As Bitmap";
_flagbitmap = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 271;BA.debugLine="Private ZeroConsIcon As Bitmap";
mostCurrent._zeroconsicon = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 272;BA.debugLine="Private MissCodeIcon As Bitmap";
mostCurrent._misscodeicon = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 275;BA.debugLine="Dim badgeMissCode, badgeZero, badgeHigh, badgeLow";
mostCurrent._badgemisscode = new com.bwsi.MeterReader.badger();
mostCurrent._badgezero = new com.bwsi.MeterReader.badger();
mostCurrent._badgehigh = new com.bwsi.MeterReader.badger();
mostCurrent._badgelow = new com.bwsi.MeterReader.badger();
mostCurrent._badgetotal = new com.bwsi.MeterReader.badger();
 //BA.debugLineNum = 276;BA.debugLine="Dim intMissCode, intZero, intHigh, intLow, intAve";
_intmisscode = 0;
_intzero = 0;
_inthigh = 0;
_intlow = 0;
_intave = 0;
_intunprinted = 0;
_inttotal = 0;
 //BA.debugLineNum = 279;BA.debugLine="Private pnlUnusual As Panel";
mostCurrent._pnlunusual = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 280;BA.debugLine="Private intSearchFlag As Int";
_intsearchflag = 0;
 //BA.debugLineNum = 281;BA.debugLine="Private pnlSearchUnusual As Panel";
mostCurrent._pnlsearchunusual = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 282;BA.debugLine="Private lblUnusualCount As Label";
mostCurrent._lblunusualcount = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 283;BA.debugLine="Private btnCancelUnusual As ACButton";
mostCurrent._btncancelunusual = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 286;BA.debugLine="Private pnlSearch As Panel";
mostCurrent._pnlsearch = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 287;BA.debugLine="Private pnlSearchOptions As Panel";
mostCurrent._pnlsearchoptions = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 288;BA.debugLine="Private pnlSearchBy As Panel";
mostCurrent._pnlsearchby = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 289;BA.debugLine="Private optUnread As RadioButton";
mostCurrent._optunread = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 290;BA.debugLine="Private optRead As RadioButton";
mostCurrent._optread = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 291;BA.debugLine="Private optAll As RadioButton";
mostCurrent._optall = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 292;BA.debugLine="Private optSeqNo As RadioButton";
mostCurrent._optseqno = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 293;BA.debugLine="Private optMeterNo As RadioButton";
mostCurrent._optmeterno = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 294;BA.debugLine="Private optAcctName As RadioButton";
mostCurrent._optacctname = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 295;BA.debugLine="Private SearchPanel As Panel";
mostCurrent._searchpanel = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 296;BA.debugLine="Private SearchFor As String";
mostCurrent._searchfor = "";
 //BA.debugLineNum = 297;BA.debugLine="Private lblSearchRecCount As Label";
mostCurrent._lblsearchreccount = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 298;BA.debugLine="Private btnCancelSearch As ACButton";
mostCurrent._btncancelsearch = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 301;BA.debugLine="Private BookID As Int";
_bookid = 0;
 //BA.debugLineNum = 302;BA.debugLine="Private BookSeq As String";
mostCurrent._bookseq = "";
 //BA.debugLineNum = 303;BA.debugLine="Private CustClass As String";
mostCurrent._custclass = "";
 //BA.debugLineNum = 304;BA.debugLine="Private BillDate As String";
mostCurrent._billdate = "";
 //BA.debugLineNum = 306;BA.debugLine="Private sBranchName As String";
mostCurrent._sbranchname = "";
 //BA.debugLineNum = 307;BA.debugLine="Private sBranchAddress As String";
mostCurrent._sbranchaddress = "";
 //BA.debugLineNum = 308;BA.debugLine="Private sBranchContactNo As String";
mostCurrent._sbranchcontactno = "";
 //BA.debugLineNum = 309;BA.debugLine="Private sTinNo As String";
mostCurrent._stinno = "";
 //BA.debugLineNum = 310;BA.debugLine="Private sBranchCode As String";
mostCurrent._sbranchcode = "";
 //BA.debugLineNum = 311;BA.debugLine="Private sBillPeriod As String";
mostCurrent._sbillperiod = "";
 //BA.debugLineNum = 312;BA.debugLine="Private iHasSeptage As Int";
_ihasseptage = 0;
 //BA.debugLineNum = 313;BA.debugLine="Private dSeptageRate As Double";
_dseptagerate = 0;
 //BA.debugLineNum = 316;BA.debugLine="Private sPresRdg As String";
mostCurrent._spresrdg = "";
 //BA.debugLineNum = 317;BA.debugLine="Private sPrevRdg As String";
mostCurrent._sprevrdg = "";
 //BA.debugLineNum = 318;BA.debugLine="Private sAddCons As String";
mostCurrent._saddcons = "";
 //BA.debugLineNum = 319;BA.debugLine="Private sTotalCum As String";
mostCurrent._stotalcum = "";
 //BA.debugLineNum = 321;BA.debugLine="Private vibration As B4Avibrate";
mostCurrent._vibration = new com.johan.Vibrate.Vibrate();
 //BA.debugLineNum = 322;BA.debugLine="Private vibratePattern() As Long";
_vibratepattern = new long[(int) (0)];
;
 //BA.debugLineNum = 324;BA.debugLine="Private sBillMonth As String";
mostCurrent._sbillmonth = "";
 //BA.debugLineNum = 325;BA.debugLine="Private sBillYear As String";
mostCurrent._sbillyear = "";
 //BA.debugLineNum = 327;BA.debugLine="Private PresAveRdg As String";
mostCurrent._presaverdg = "";
 //BA.debugLineNum = 328;BA.debugLine="Private PresAveCum As String";
mostCurrent._presavecum = "";
 //BA.debugLineNum = 330;BA.debugLine="Private sPresentReading As String";
mostCurrent._spresentreading = "";
 //BA.debugLineNum = 332;BA.debugLine="Private SoundID As Int";
_soundid = 0;
 //BA.debugLineNum = 334;BA.debugLine="Private cdTxtBox, cdConfirmRdg As ColorDrawable";
mostCurrent._cdtxtbox = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
mostCurrent._cdconfirmrdg = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 335;BA.debugLine="Private cdButtonCancel, cdButtonSaveOnly, cdButto";
mostCurrent._cdbuttoncancel = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
mostCurrent._cdbuttonsaveonly = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
mostCurrent._cdbuttonsaveandprint = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 338;BA.debugLine="Private pnlNegativeMsgBox As Panel";
mostCurrent._pnlnegativemsgbox = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 339;BA.debugLine="Private btnNegativeCancelPost As ACButton";
mostCurrent._btnnegativecancelpost = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 340;BA.debugLine="Private btnNegativeSelect As ACButton";
mostCurrent._btnnegativeselect = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 341;BA.debugLine="Private opt0 As ACRadioButton";
mostCurrent._opt0 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 342;BA.debugLine="Private opt1 As ACRadioButton";
mostCurrent._opt1 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 343;BA.debugLine="Private opt2 As ACRadioButton";
mostCurrent._opt2 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 344;BA.debugLine="Private opt3 As ACRadioButton";
mostCurrent._opt3 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 345;BA.debugLine="Private opt4 As ACRadioButton";
mostCurrent._opt4 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 346;BA.debugLine="Private opt5 As ACRadioButton";
mostCurrent._opt5 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 347;BA.debugLine="Private opt6 As ACRadioButton";
mostCurrent._opt6 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 348;BA.debugLine="Private opt7 As ACRadioButton";
mostCurrent._opt7 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 349;BA.debugLine="Private opt8 As ACRadioButton";
mostCurrent._opt8 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 350;BA.debugLine="Private txtOthers As EditText";
mostCurrent._txtothers = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 353;BA.debugLine="Private pnlHiMsgBox As Panel";
mostCurrent._pnlhimsgbox = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 354;BA.debugLine="Private btnHiCancelPost As ACButton";
mostCurrent._btnhicancelpost = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 355;BA.debugLine="Private btnHiSaveOnly As ACButton";
mostCurrent._btnhisaveonly = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 356;BA.debugLine="Private btnHiSaveAndPrint As ACButton";
mostCurrent._btnhisaveandprint = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 359;BA.debugLine="Private pnlHighBillConfirmation As Panel";
mostCurrent._pnlhighbillconfirmation = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 360;BA.debugLine="Private txtPresRdgConfirm As EditText";
mostCurrent._txtpresrdgconfirm = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 361;BA.debugLine="Private btnHBConfirmCancel As ACButton";
mostCurrent._btnhbconfirmcancel = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 362;BA.debugLine="Private btnHBConfirmSaveAndPrint As ACButton";
mostCurrent._btnhbconfirmsaveandprint = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 363;BA.debugLine="Private intSaveOnly As Int";
_intsaveonly = 0;
 //BA.debugLineNum = 366;BA.debugLine="Private btnLowCancelPost As ACButton";
mostCurrent._btnlowcancelpost = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 367;BA.debugLine="Private btnLowSaveOnly As ACButton";
mostCurrent._btnlowsaveonly = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 368;BA.debugLine="Private btnLowSaveAndPrint As ACButton";
mostCurrent._btnlowsaveandprint = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 369;BA.debugLine="Private pnlLowMsgBox As Panel";
mostCurrent._pnllowmsgbox = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 372;BA.debugLine="Private pnlZeroMsgBox As Panel";
mostCurrent._pnlzeromsgbox = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 373;BA.debugLine="Private btnZeroCancelPost As ACButton";
mostCurrent._btnzerocancelpost = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 374;BA.debugLine="Private btnZeroSaveOnly As ACButton";
mostCurrent._btnzerosaveonly = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 375;BA.debugLine="Private btnZeroSaveAndPrint As ACButton";
mostCurrent._btnzerosaveandprint = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 376;BA.debugLine="Private txtRemarks As EditText";
mostCurrent._txtremarks = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 379;BA.debugLine="Private pnlNormalMsgBox As Panel";
mostCurrent._pnlnormalmsgbox = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 380;BA.debugLine="Private btnNormalCancelPost As ACButton";
mostCurrent._btnnormalcancelpost = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 381;BA.debugLine="Private btnNormalSaveOnly As ACButton";
mostCurrent._btnnormalsaveonly = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 382;BA.debugLine="Private btnNormalSaveAndPrint As ACButton";
mostCurrent._btnnormalsaveandprint = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 385;BA.debugLine="Private pnlAveMsgBox As Panel";
mostCurrent._pnlavemsgbox = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 386;BA.debugLine="Private btnAveTakePicture As ACButton";
mostCurrent._btnavetakepicture = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 387;BA.debugLine="Private btnAveCancelPost As ACButton";
mostCurrent._btnavecancelpost = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 390;BA.debugLine="Private pnlAveRemMsgBox As Panel";
mostCurrent._pnlaveremmsgbox = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 391;BA.debugLine="Private btnAveRemSaveAndPrint As ACButton";
mostCurrent._btnaveremsaveandprint = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 392;BA.debugLine="Private btnAveRemCancelPost As ACButton";
mostCurrent._btnaveremcancelpost = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 393;BA.debugLine="Private txtReason As EditText";
mostCurrent._txtreason = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 396;BA.debugLine="Private pnlHBConfirmReprint As Panel";
mostCurrent._pnlhbconfirmreprint = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 397;BA.debugLine="Private txtPresRdgConfirmReprint As EditText";
mostCurrent._txtpresrdgconfirmreprint = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 398;BA.debugLine="Private btnHBConfirmReprintCancel As ACButton";
mostCurrent._btnhbconfirmreprintcancel = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 399;BA.debugLine="Private btnHBConfirmSaveAndRePrint As ACButton";
mostCurrent._btnhbconfirmsaveandreprint = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 402;BA.debugLine="Private QR As QRGenerator";
mostCurrent._qr = new com.bwsi.MeterReader.qrgenerator();
 //BA.debugLineNum = 403;BA.debugLine="Private bmpQR As B4XBitmap";
mostCurrent._bmpqr = new anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper();
 //BA.debugLineNum = 405;BA.debugLine="Dim T1 As Timer";
mostCurrent._t1 = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 406;BA.debugLine="Private optReason0 As ACRadioButton";
mostCurrent._optreason0 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 407;BA.debugLine="Private optReason1 As ACRadioButton";
mostCurrent._optreason1 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 408;BA.debugLine="Private optReason2 As ACRadioButton";
mostCurrent._optreason2 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 409;BA.debugLine="Private optReason3 As ACRadioButton";
mostCurrent._optreason3 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 410;BA.debugLine="Private optReason4 As ACRadioButton";
mostCurrent._optreason4 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 411;BA.debugLine="Private optReason5 As ACRadioButton";
mostCurrent._optreason5 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 412;BA.debugLine="Private optReason6 As ACRadioButton";
mostCurrent._optreason6 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 413;BA.debugLine="Private optReason7 As ACRadioButton";
mostCurrent._optreason7 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 414;BA.debugLine="Private optReason8 As ACRadioButton";
mostCurrent._optreason8 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 417;BA.debugLine="Private pnlNegativeMsgBox As Panel";
mostCurrent._pnlnegativemsgbox = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 418;BA.debugLine="Private btnNegativeCancelPost As ACButton";
mostCurrent._btnnegativecancelpost = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 419;BA.debugLine="Private btnNegativeSelect As ACButton";
mostCurrent._btnnegativeselect = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 420;BA.debugLine="Private opt0 As ACRadioButton";
mostCurrent._opt0 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 421;BA.debugLine="Private opt1 As ACRadioButton";
mostCurrent._opt1 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 422;BA.debugLine="Private opt2 As ACRadioButton";
mostCurrent._opt2 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 423;BA.debugLine="Private opt3 As ACRadioButton";
mostCurrent._opt3 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 424;BA.debugLine="Private opt4 As ACRadioButton";
mostCurrent._opt4 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 425;BA.debugLine="Private opt5 As ACRadioButton";
mostCurrent._opt5 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 426;BA.debugLine="Private opt6 As ACRadioButton";
mostCurrent._opt6 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 427;BA.debugLine="Private opt7 As ACRadioButton";
mostCurrent._opt7 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 428;BA.debugLine="Private opt8 As ACRadioButton";
mostCurrent._opt8 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 429;BA.debugLine="Private txtOthers As EditText";
mostCurrent._txtothers = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 431;BA.debugLine="Private pnlMisCodeMsgBox As Panel";
mostCurrent._pnlmiscodemsgbox = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 432;BA.debugLine="Private lblMisCodeMsgTitle As Label";
mostCurrent._lblmiscodemsgtitle = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 433;BA.debugLine="Private optMisCode0 As ACRadioButton";
mostCurrent._optmiscode0 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 434;BA.debugLine="Private optMisCode1 As ACRadioButton";
mostCurrent._optmiscode1 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 435;BA.debugLine="Private optMisCode2 As ACRadioButton";
mostCurrent._optmiscode2 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 436;BA.debugLine="Private optMisCode3 As ACRadioButton";
mostCurrent._optmiscode3 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 437;BA.debugLine="Private optMisCode4 As ACRadioButton";
mostCurrent._optmiscode4 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 438;BA.debugLine="Private optMisCode5 As ACRadioButton";
mostCurrent._optmiscode5 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 439;BA.debugLine="Private optMisCode6 As ACRadioButton";
mostCurrent._optmiscode6 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 440;BA.debugLine="Private optMisCode7 As ACRadioButton";
mostCurrent._optmiscode7 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 441;BA.debugLine="Private optMisCode8 As ACRadioButton";
mostCurrent._optmiscode8 = new de.amberhome.objects.appcompat.ACRadioButtonWrapper();
 //BA.debugLineNum = 442;BA.debugLine="Private btnMisCodeCancelPost As ACButton";
mostCurrent._btnmiscodecancelpost = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 443;BA.debugLine="Private btnMisCodeSave As ACButton";
mostCurrent._btnmiscodesave = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 444;BA.debugLine="Private txtMisCode As EditText";
mostCurrent._txtmiscode = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 446;BA.debugLine="Private blnSuperHighBill As Boolean";
_blnsuperhighbill = false;
 //BA.debugLineNum = 447;BA.debugLine="Private btnSuperHBCancel As ACButton";
mostCurrent._btnsuperhbcancel = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 448;BA.debugLine="Private btnSuperHBSave As ACButton";
mostCurrent._btnsuperhbsave = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 449;BA.debugLine="Private pnlSuperHB As Panel";
mostCurrent._pnlsuperhb = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 450;BA.debugLine="Private txtSuperHBPresRdg As EditText";
mostCurrent._txtsuperhbpresrdg = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 452;BA.debugLine="Dim initPrinter() As Byte";
_initprinter = new byte[(int) (0)];
;
 //BA.debugLineNum = 453;BA.debugLine="Private cKeyboard As CustomKeyboard";
mostCurrent._ckeyboard = new bm.watscho.keyboard.CustomKeyboard();
 //BA.debugLineNum = 455;BA.debugLine="End Sub";
return "";
}
public static boolean  _isprintabledata(int _ireadid) throws Exception{
boolean _blnretval = false;
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsvaliddata = null;
 //BA.debugLineNum = 1346;BA.debugLine="Private Sub IsPrintableData(iReadID As Int) As Boo";
 //BA.debugLineNum = 1347;BA.debugLine="Dim blnRetVal As Boolean";
_blnretval = false;
 //BA.debugLineNum = 1348;BA.debugLine="Dim rsValidData As Cursor";
_rsvaliddata = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 1350;BA.debugLine="Try";
try { //BA.debugLineNum = 1351;BA.debugLine="Starter.strCriteria = \"SELECT * FROM tblReadings";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblReadings WHERE ReadID = "+BA.NumberToString(_ireadid)+" "+"AND TotalCum >= 0 "+"AND PresCum >= 0 "+"AND PresRdg <> '"+""+"' "+"AND WasRead = 1 "+"AND WasMissCoded = 0";
 //BA.debugLineNum = 1357;BA.debugLine="rsValidData = Starter.DBCon.ExecQuery(Starter.st";
_rsvaliddata = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 1359;BA.debugLine="If rsValidData.RowCount > 0 Then";
if (_rsvaliddata.getRowCount()>0) { 
 //BA.debugLineNum = 1360;BA.debugLine="blnRetVal = True";
_blnretval = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 1362;BA.debugLine="blnRetVal = False";
_blnretval = anywheresoftware.b4a.keywords.Common.False;
 };
 } 
       catch (Exception e12) {
			processBA.setLastException(e12); //BA.debugLineNum = 1365;BA.debugLine="blnRetVal = False";
_blnretval = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 1366;BA.debugLine="rsValidData.Close";
_rsvaliddata.Close();
 //BA.debugLineNum = 1367;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("122675477",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 1370;BA.debugLine="rsValidData.Close";
_rsvaliddata.Close();
 //BA.debugLineNum = 1371;BA.debugLine="Return blnRetVal";
if (true) return _blnretval;
 //BA.debugLineNum = 1372;BA.debugLine="End Sub";
return false;
}
public static String  _logoprint_error() throws Exception{
 //BA.debugLineNum = 4930;BA.debugLine="Sub LogoPrint_Error";
 //BA.debugLineNum = 4933;BA.debugLine="End Sub";
return "";
}
public static String  _logoprint_newdata(byte[] _buffer) throws Exception{
String _msg = "";
 //BA.debugLineNum = 4923;BA.debugLine="Sub LogoPrint_NewData (Buffer() As Byte)";
 //BA.debugLineNum = 4924;BA.debugLine="Dim msg As String";
_msg = "";
 //BA.debugLineNum = 4925;BA.debugLine="msg = BytesToString(Buffer, 0, Buffer.Length, \"UT";
_msg = anywheresoftware.b4a.keywords.Common.BytesToString(_buffer,(int) (0),_buffer.length,"UTF8");
 //BA.debugLineNum = 4927;BA.debugLine="Log(msg)";
anywheresoftware.b4a.keywords.Common.LogImpl("128442628",_msg,0);
 //BA.debugLineNum = 4928;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.drawable.ColorDrawable  _mycd() throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _mcd = null;
 //BA.debugLineNum = 6016;BA.debugLine="Private Sub myCD As ColorDrawable";
 //BA.debugLineNum = 6017;BA.debugLine="Dim mCD As ColorDrawable";
_mcd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 6018;BA.debugLine="mCD.Initialize(Colors.RGB(240,240,240),0)";
_mcd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (240),(int) (240),(int) (240)),(int) (0));
 //BA.debugLineNum = 6019;BA.debugLine="Return mCD";
if (true) return _mcd;
 //BA.debugLineNum = 6020;BA.debugLine="End Sub";
return null;
}
public static String  _opt0_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 3419;BA.debugLine="Sub opt0_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 3420;BA.debugLine="LogColor(Checked,Colors.Magenta)";
anywheresoftware.b4a.keywords.Common.LogImpl("126476545",BA.ObjectToString(_checked),anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 3421;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 3422;BA.debugLine="txtOthers.Enabled = False";
mostCurrent._txtothers.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3423;BA.debugLine="txtOthers.Text = \"\"";
mostCurrent._txtothers.setText(BA.ObjectToCharSequence(""));
 };
 //BA.debugLineNum = 3425;BA.debugLine="End Sub";
return "";
}
public static String  _opt1_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 3427;BA.debugLine="Sub opt1_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 3428;BA.debugLine="LogColor(Checked,Colors.Magenta)";
anywheresoftware.b4a.keywords.Common.LogImpl("126542081",BA.ObjectToString(_checked),anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 3429;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 3430;BA.debugLine="txtOthers.Enabled = False";
mostCurrent._txtothers.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3431;BA.debugLine="txtOthers.Text = \"\"";
mostCurrent._txtothers.setText(BA.ObjectToCharSequence(""));
 };
 //BA.debugLineNum = 3433;BA.debugLine="End Sub";
return "";
}
public static String  _opt2_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 3435;BA.debugLine="Sub opt2_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 3436;BA.debugLine="LogColor(Checked,Colors.Magenta)";
anywheresoftware.b4a.keywords.Common.LogImpl("126607617",BA.ObjectToString(_checked),anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 3437;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 3438;BA.debugLine="txtOthers.Enabled = False";
mostCurrent._txtothers.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3439;BA.debugLine="txtOthers.Text = \"\"";
mostCurrent._txtothers.setText(BA.ObjectToCharSequence(""));
 };
 //BA.debugLineNum = 3441;BA.debugLine="End Sub";
return "";
}
public static String  _opt3_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 3443;BA.debugLine="Sub opt3_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 3444;BA.debugLine="LogColor(Checked,Colors.Magenta)";
anywheresoftware.b4a.keywords.Common.LogImpl("126673153",BA.ObjectToString(_checked),anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 3445;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 3446;BA.debugLine="txtOthers.Enabled = False";
mostCurrent._txtothers.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3447;BA.debugLine="txtOthers.Text = \"\"";
mostCurrent._txtothers.setText(BA.ObjectToCharSequence(""));
 };
 //BA.debugLineNum = 3449;BA.debugLine="End Sub";
return "";
}
public static String  _opt4_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 3451;BA.debugLine="Sub opt4_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 3452;BA.debugLine="LogColor(Checked,Colors.Magenta)";
anywheresoftware.b4a.keywords.Common.LogImpl("126738689",BA.ObjectToString(_checked),anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 3453;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 3454;BA.debugLine="txtOthers.Enabled = False";
mostCurrent._txtothers.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3455;BA.debugLine="txtOthers.Text = \"\"";
mostCurrent._txtothers.setText(BA.ObjectToCharSequence(""));
 };
 //BA.debugLineNum = 3457;BA.debugLine="End Sub";
return "";
}
public static String  _opt5_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 3459;BA.debugLine="Sub opt5_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 3460;BA.debugLine="LogColor(Checked,Colors.Magenta)";
anywheresoftware.b4a.keywords.Common.LogImpl("126804225",BA.ObjectToString(_checked),anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 3461;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 3462;BA.debugLine="txtOthers.Enabled = False";
mostCurrent._txtothers.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3463;BA.debugLine="txtOthers.Text = \"\"";
mostCurrent._txtothers.setText(BA.ObjectToCharSequence(""));
 };
 //BA.debugLineNum = 3465;BA.debugLine="End Sub";
return "";
}
public static String  _opt6_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 3467;BA.debugLine="Sub opt6_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 3468;BA.debugLine="LogColor(Checked,Colors.Magenta)";
anywheresoftware.b4a.keywords.Common.LogImpl("126869761",BA.ObjectToString(_checked),anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 3469;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 3470;BA.debugLine="txtOthers.Enabled = False";
mostCurrent._txtothers.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3471;BA.debugLine="txtOthers.Text = \"\"";
mostCurrent._txtothers.setText(BA.ObjectToCharSequence(""));
 };
 //BA.debugLineNum = 3473;BA.debugLine="End Sub";
return "";
}
public static String  _opt7_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 3475;BA.debugLine="Sub opt7_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 3476;BA.debugLine="LogColor(Checked,Colors.Magenta)";
anywheresoftware.b4a.keywords.Common.LogImpl("126935297",BA.ObjectToString(_checked),anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 3477;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 3478;BA.debugLine="txtOthers.Enabled = False";
mostCurrent._txtothers.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3479;BA.debugLine="txtOthers.Text = \"\"";
mostCurrent._txtothers.setText(BA.ObjectToCharSequence(""));
 };
 //BA.debugLineNum = 3481;BA.debugLine="End Sub";
return "";
}
public static String  _opt8_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 3483;BA.debugLine="Sub opt8_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 3484;BA.debugLine="LogColor(Checked,Colors.Magenta)";
anywheresoftware.b4a.keywords.Common.LogImpl("127000833",BA.ObjectToString(_checked),anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 3485;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 3486;BA.debugLine="txtOthers.Enabled = True";
mostCurrent._txtothers.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 3487;BA.debugLine="txtOthers.RequestFocus";
mostCurrent._txtothers.RequestFocus();
 //BA.debugLineNum = 3488;BA.debugLine="IMEkeyboard.ShowKeyboard(txtOthers)";
mostCurrent._imekeyboard.ShowKeyboard((android.view.View)(mostCurrent._txtothers.getObject()));
 };
 //BA.debugLineNum = 3490;BA.debugLine="End Sub";
return "";
}
public static String  _optacctname_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 1793;BA.debugLine="Sub optAcctName_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 1794;BA.debugLine="If Checked=True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1795;BA.debugLine="If optUnread.Checked = True Then";
if (mostCurrent._optunread.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1796;BA.debugLine="SearchAccount(2, 0)";
_searchaccount((int) (2),(int) (0));
 }else if(mostCurrent._optread.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1798;BA.debugLine="SearchAccount(2, 1)";
_searchaccount((int) (2),(int) (1));
 }else {
 //BA.debugLineNum = 1800;BA.debugLine="SearchAccount(2, 2)";
_searchaccount((int) (2),(int) (2));
 };
 };
 //BA.debugLineNum = 1803;BA.debugLine="End Sub";
return "";
}
public static String  _optall_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 1829;BA.debugLine="Sub optAll_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 1830;BA.debugLine="If Checked=True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1831;BA.debugLine="If optSeqNo.Checked = True Then";
if (mostCurrent._optseqno.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1832;BA.debugLine="SearchAccount(0, 2)";
_searchaccount((int) (0),(int) (2));
 }else if(mostCurrent._optmeterno.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1834;BA.debugLine="SearchAccount(1, 2)";
_searchaccount((int) (1),(int) (2));
 }else {
 //BA.debugLineNum = 1836;BA.debugLine="SearchAccount(2, 2)";
_searchaccount((int) (2),(int) (2));
 };
 };
 //BA.debugLineNum = 1840;BA.debugLine="End Sub";
return "";
}
public static String  _optmeterno_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 1781;BA.debugLine="Sub optMeterNo_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 1782;BA.debugLine="If Checked=True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1783;BA.debugLine="If optUnread.Checked = True Then";
if (mostCurrent._optunread.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1784;BA.debugLine="SearchAccount(1, 0)";
_searchaccount((int) (1),(int) (0));
 }else if(mostCurrent._optread.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1786;BA.debugLine="SearchAccount(1, 1)";
_searchaccount((int) (1),(int) (1));
 }else {
 //BA.debugLineNum = 1788;BA.debugLine="SearchAccount(1, 2)";
_searchaccount((int) (1),(int) (2));
 };
 };
 //BA.debugLineNum = 1791;BA.debugLine="End Sub";
return "";
}
public static String  _optmiscode0_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 2132;BA.debugLine="Sub optMisCode0_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 2133;BA.debugLine="LogColor(Checked,Colors.Magenta)";
anywheresoftware.b4a.keywords.Common.LogImpl("124576001",BA.ObjectToString(_checked),anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 2134;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2135;BA.debugLine="txtMisCode.Enabled = False";
mostCurrent._txtmiscode.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2136;BA.debugLine="txtMisCode.Text = \"\"";
mostCurrent._txtmiscode.setText(BA.ObjectToCharSequence(""));
 };
 //BA.debugLineNum = 2138;BA.debugLine="End Sub";
return "";
}
public static String  _optmiscode1_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 2140;BA.debugLine="Sub optMisCode1_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 2141;BA.debugLine="LogColor(Checked,Colors.Magenta)";
anywheresoftware.b4a.keywords.Common.LogImpl("124641537",BA.ObjectToString(_checked),anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 2142;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2143;BA.debugLine="txtMisCode.Enabled = False";
mostCurrent._txtmiscode.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2144;BA.debugLine="txtMisCode.Text = \"\"";
mostCurrent._txtmiscode.setText(BA.ObjectToCharSequence(""));
 };
 //BA.debugLineNum = 2146;BA.debugLine="End Sub";
return "";
}
public static String  _optmiscode2_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 2148;BA.debugLine="Sub optMisCode2_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 2149;BA.debugLine="LogColor(Checked,Colors.Magenta)";
anywheresoftware.b4a.keywords.Common.LogImpl("124707073",BA.ObjectToString(_checked),anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 2150;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2151;BA.debugLine="txtMisCode.Enabled = False";
mostCurrent._txtmiscode.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2152;BA.debugLine="txtMisCode.Text = \"\"";
mostCurrent._txtmiscode.setText(BA.ObjectToCharSequence(""));
 };
 //BA.debugLineNum = 2154;BA.debugLine="End Sub";
return "";
}
public static String  _optmiscode3_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 2156;BA.debugLine="Sub optMisCode3_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 2157;BA.debugLine="LogColor(Checked,Colors.Magenta)";
anywheresoftware.b4a.keywords.Common.LogImpl("124772609",BA.ObjectToString(_checked),anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 2158;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2159;BA.debugLine="txtMisCode.Enabled = False";
mostCurrent._txtmiscode.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2160;BA.debugLine="txtMisCode.Text = \"\"";
mostCurrent._txtmiscode.setText(BA.ObjectToCharSequence(""));
 };
 //BA.debugLineNum = 2162;BA.debugLine="End Sub";
return "";
}
public static String  _optmiscode4_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 2164;BA.debugLine="Sub optMisCode4_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 2165;BA.debugLine="LogColor(Checked,Colors.Magenta)";
anywheresoftware.b4a.keywords.Common.LogImpl("124838145",BA.ObjectToString(_checked),anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 2166;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2167;BA.debugLine="txtMisCode.Enabled = False";
mostCurrent._txtmiscode.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2168;BA.debugLine="txtMisCode.Text = \"\"";
mostCurrent._txtmiscode.setText(BA.ObjectToCharSequence(""));
 };
 //BA.debugLineNum = 2170;BA.debugLine="End Sub";
return "";
}
public static String  _optmiscode5_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 2172;BA.debugLine="Sub optMisCode5_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 2173;BA.debugLine="LogColor(Checked,Colors.Magenta)";
anywheresoftware.b4a.keywords.Common.LogImpl("124903681",BA.ObjectToString(_checked),anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 2174;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2175;BA.debugLine="txtMisCode.Enabled = False";
mostCurrent._txtmiscode.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2176;BA.debugLine="txtMisCode.Text = \"\"";
mostCurrent._txtmiscode.setText(BA.ObjectToCharSequence(""));
 };
 //BA.debugLineNum = 2178;BA.debugLine="End Sub";
return "";
}
public static String  _optmiscode6_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 2180;BA.debugLine="Sub optMisCode6_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 2181;BA.debugLine="LogColor(Checked,Colors.Magenta)";
anywheresoftware.b4a.keywords.Common.LogImpl("124969217",BA.ObjectToString(_checked),anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 2182;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2183;BA.debugLine="txtMisCode.Enabled = False";
mostCurrent._txtmiscode.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2184;BA.debugLine="txtMisCode.Text = \"\"";
mostCurrent._txtmiscode.setText(BA.ObjectToCharSequence(""));
 };
 //BA.debugLineNum = 2186;BA.debugLine="End Sub";
return "";
}
public static String  _optmiscode7_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 2188;BA.debugLine="Sub optMisCode7_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 2189;BA.debugLine="LogColor(Checked,Colors.Magenta)";
anywheresoftware.b4a.keywords.Common.LogImpl("125034753",BA.ObjectToString(_checked),anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 2190;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2191;BA.debugLine="txtMisCode.Enabled = False";
mostCurrent._txtmiscode.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2192;BA.debugLine="txtMisCode.Text = \"\"";
mostCurrent._txtmiscode.setText(BA.ObjectToCharSequence(""));
 };
 //BA.debugLineNum = 2194;BA.debugLine="End Sub";
return "";
}
public static String  _optmiscode8_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 2196;BA.debugLine="Sub optMisCode8_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 2197;BA.debugLine="LogColor(Checked,Colors.Magenta)";
anywheresoftware.b4a.keywords.Common.LogImpl("125100289",BA.ObjectToString(_checked),anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 2198;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2199;BA.debugLine="txtMisCode.Enabled = True";
mostCurrent._txtmiscode.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2200;BA.debugLine="txtMisCode.RequestFocus";
mostCurrent._txtmiscode.RequestFocus();
 //BA.debugLineNum = 2201;BA.debugLine="IMEkeyboard.ShowKeyboard(txtOthers)";
mostCurrent._imekeyboard.ShowKeyboard((android.view.View)(mostCurrent._txtothers.getObject()));
 }else {
 //BA.debugLineNum = 2203;BA.debugLine="txtMisCode.Enabled = False";
mostCurrent._txtmiscode.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2204;BA.debugLine="txtMisCode.Text = \"\"";
mostCurrent._txtmiscode.setText(BA.ObjectToCharSequence(""));
 };
 //BA.debugLineNum = 2206;BA.debugLine="End Sub";
return "";
}
public static String  _optread_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 1817;BA.debugLine="Sub optRead_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 1818;BA.debugLine="If Checked=True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1819;BA.debugLine="If optSeqNo.Checked = True Then";
if (mostCurrent._optseqno.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1820;BA.debugLine="SearchAccount(0, 1)";
_searchaccount((int) (0),(int) (1));
 }else if(mostCurrent._optmeterno.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1822;BA.debugLine="SearchAccount(1, 1)";
_searchaccount((int) (1),(int) (1));
 }else {
 //BA.debugLineNum = 1824;BA.debugLine="SearchAccount(2, 1)";
_searchaccount((int) (2),(int) (1));
 };
 };
 //BA.debugLineNum = 1827;BA.debugLine="End Sub";
return "";
}
public static String  _optreason0_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 5621;BA.debugLine="Sub optReason0_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 5622;BA.debugLine="LogColor(Checked,Colors.Magenta)";
anywheresoftware.b4a.keywords.Common.LogImpl("131260673",BA.ObjectToString(_checked),anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 5623;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 5624;BA.debugLine="txtReason.Enabled = False";
mostCurrent._txtreason.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 5625;BA.debugLine="txtReason.Text = \"\"";
mostCurrent._txtreason.setText(BA.ObjectToCharSequence(""));
 };
 //BA.debugLineNum = 5627;BA.debugLine="End Sub";
return "";
}
public static String  _optreason1_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 5629;BA.debugLine="Sub optReason1_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 5630;BA.debugLine="LogColor(Checked,Colors.Magenta)";
anywheresoftware.b4a.keywords.Common.LogImpl("131326209",BA.ObjectToString(_checked),anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 5631;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 5632;BA.debugLine="txtReason.Enabled = False";
mostCurrent._txtreason.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 5633;BA.debugLine="txtReason.Text = \"\"";
mostCurrent._txtreason.setText(BA.ObjectToCharSequence(""));
 };
 //BA.debugLineNum = 5635;BA.debugLine="End Sub";
return "";
}
public static String  _optreason2_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 5637;BA.debugLine="Sub optReason2_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 5638;BA.debugLine="LogColor(Checked,Colors.Magenta)";
anywheresoftware.b4a.keywords.Common.LogImpl("131391745",BA.ObjectToString(_checked),anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 5639;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 5640;BA.debugLine="txtReason.Enabled = False";
mostCurrent._txtreason.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 5641;BA.debugLine="txtReason.Text = \"\"";
mostCurrent._txtreason.setText(BA.ObjectToCharSequence(""));
 };
 //BA.debugLineNum = 5643;BA.debugLine="End Sub";
return "";
}
public static String  _optreason3_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 5645;BA.debugLine="Sub optReason3_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 5646;BA.debugLine="LogColor(Checked,Colors.Magenta)";
anywheresoftware.b4a.keywords.Common.LogImpl("131457281",BA.ObjectToString(_checked),anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 5647;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 5648;BA.debugLine="txtReason.Enabled = False";
mostCurrent._txtreason.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 5649;BA.debugLine="txtReason.Text = \"\"";
mostCurrent._txtreason.setText(BA.ObjectToCharSequence(""));
 };
 //BA.debugLineNum = 5651;BA.debugLine="End Sub";
return "";
}
public static String  _optreason4_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 5653;BA.debugLine="Sub optReason4_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 5654;BA.debugLine="LogColor(Checked,Colors.Magenta)";
anywheresoftware.b4a.keywords.Common.LogImpl("131522817",BA.ObjectToString(_checked),anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 5655;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 5656;BA.debugLine="txtReason.Enabled = False";
mostCurrent._txtreason.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 5657;BA.debugLine="txtReason.Text = \"\"";
mostCurrent._txtreason.setText(BA.ObjectToCharSequence(""));
 };
 //BA.debugLineNum = 5659;BA.debugLine="End Sub";
return "";
}
public static String  _optreason5_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 5661;BA.debugLine="Sub optReason5_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 5662;BA.debugLine="LogColor(Checked,Colors.Magenta)";
anywheresoftware.b4a.keywords.Common.LogImpl("131588353",BA.ObjectToString(_checked),anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 5663;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 5664;BA.debugLine="txtReason.Enabled = False";
mostCurrent._txtreason.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 5665;BA.debugLine="txtReason.Text = \"\"";
mostCurrent._txtreason.setText(BA.ObjectToCharSequence(""));
 };
 //BA.debugLineNum = 5667;BA.debugLine="End Sub";
return "";
}
public static String  _optreason6_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 5669;BA.debugLine="Sub optReason6_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 5670;BA.debugLine="LogColor(Checked,Colors.Magenta)";
anywheresoftware.b4a.keywords.Common.LogImpl("131653889",BA.ObjectToString(_checked),anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 5671;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 5672;BA.debugLine="txtReason.Enabled = False";
mostCurrent._txtreason.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 5673;BA.debugLine="txtReason.Text = \"\"";
mostCurrent._txtreason.setText(BA.ObjectToCharSequence(""));
 };
 //BA.debugLineNum = 5675;BA.debugLine="End Sub";
return "";
}
public static String  _optreason7_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 5677;BA.debugLine="Sub optReason7_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 5678;BA.debugLine="LogColor(Checked,Colors.Magenta)";
anywheresoftware.b4a.keywords.Common.LogImpl("131719425",BA.ObjectToString(_checked),anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 5679;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 5680;BA.debugLine="txtReason.Enabled = False";
mostCurrent._txtreason.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 5681;BA.debugLine="txtReason.Text = \"\"";
mostCurrent._txtreason.setText(BA.ObjectToCharSequence(""));
 };
 //BA.debugLineNum = 5683;BA.debugLine="End Sub";
return "";
}
public static String  _optreason8_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 5685;BA.debugLine="Sub optReason8_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 5686;BA.debugLine="LogColor(Checked,Colors.Magenta)";
anywheresoftware.b4a.keywords.Common.LogImpl("131784961",BA.ObjectToString(_checked),anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 5687;BA.debugLine="If Checked = True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 5688;BA.debugLine="txtReason.Enabled = True";
mostCurrent._txtreason.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 5689;BA.debugLine="txtReason.RequestFocus";
mostCurrent._txtreason.RequestFocus();
 //BA.debugLineNum = 5690;BA.debugLine="IMEkeyboard.ShowKeyboard(txtReason)";
mostCurrent._imekeyboard.ShowKeyboard((android.view.View)(mostCurrent._txtreason.getObject()));
 };
 //BA.debugLineNum = 5692;BA.debugLine="End Sub";
return "";
}
public static String  _optseqno_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 1769;BA.debugLine="Sub optSeqNo_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 1770;BA.debugLine="If Checked=True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1771;BA.debugLine="If optUnread.Checked = True Then";
if (mostCurrent._optunread.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1772;BA.debugLine="SearchAccount(0, 0)";
_searchaccount((int) (0),(int) (0));
 }else if(mostCurrent._optread.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1774;BA.debugLine="SearchAccount(0, 1)";
_searchaccount((int) (0),(int) (1));
 }else {
 //BA.debugLineNum = 1776;BA.debugLine="SearchAccount(0, 2)";
_searchaccount((int) (0),(int) (2));
 };
 };
 //BA.debugLineNum = 1779;BA.debugLine="End Sub";
return "";
}
public static String  _optunread_checkedchange(boolean _checked) throws Exception{
 //BA.debugLineNum = 1805;BA.debugLine="Sub optUnread_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 1806;BA.debugLine="If Checked=True Then";
if (_checked==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1807;BA.debugLine="If optSeqNo.Checked = True Then";
if (mostCurrent._optseqno.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1808;BA.debugLine="SearchAccount(0, 0)";
_searchaccount((int) (0),(int) (0));
 }else if(mostCurrent._optmeterno.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1810;BA.debugLine="SearchAccount(1, 0)";
_searchaccount((int) (1),(int) (0));
 }else {
 //BA.debugLineNum = 1812;BA.debugLine="SearchAccount(2, 0)";
_searchaccount((int) (2),(int) (0));
 };
 };
 //BA.debugLineNum = 1815;BA.debugLine="End Sub";
return "";
}
public static String  _pnlavemsgbox_touch(int _action,float _x,float _y) throws Exception{
 //BA.debugLineNum = 5428;BA.debugLine="Sub pnlAveMsgBox_Touch (Action As Int, X As Float,";
 //BA.debugLineNum = 5430;BA.debugLine="End Sub";
return "";
}
public static String  _pnlaveremmsgbox_touch(int _action,float _x,float _y) throws Exception{
 //BA.debugLineNum = 5617;BA.debugLine="Sub pnlAveRemMsgBox_Touch (Action As Int, X As Flo";
 //BA.debugLineNum = 5619;BA.debugLine="End Sub";
return "";
}
public static String  _pnlhighbillconfirmation_touch(int _action,float _x,float _y) throws Exception{
 //BA.debugLineNum = 5024;BA.debugLine="Sub pnlHighBillConfirmation_Touch (Action As Int,";
 //BA.debugLineNum = 5026;BA.debugLine="End Sub";
return "";
}
public static String  _pnlhimsgbox_touch(int _action,float _x,float _y) throws Exception{
 //BA.debugLineNum = 4949;BA.debugLine="Sub pnlHiMsgBox_Touch (Action As Int, X As Float,";
 //BA.debugLineNum = 4951;BA.debugLine="End Sub";
return "";
}
public static String  _pnllowmsgbox_touch(int _action,float _x,float _y) throws Exception{
 //BA.debugLineNum = 5124;BA.debugLine="Sub pnlLowMsgBox_Touch (Action As Int, X As Float,";
 //BA.debugLineNum = 5126;BA.debugLine="End Sub";
return "";
}
public static String  _pnlnegativemsgbox_touch(int _action,float _x,float _y) throws Exception{
 //BA.debugLineNum = 3492;BA.debugLine="Sub pnlNegativeMsgBox_Touch (Action As Int, X As F";
 //BA.debugLineNum = 3494;BA.debugLine="End Sub";
return "";
}
public static String  _pnlnormalmsgbox_touch(int _action,float _x,float _y) throws Exception{
 //BA.debugLineNum = 5206;BA.debugLine="Sub pnlNormalMsgBox_Touch (Action As Int, X As Flo";
 //BA.debugLineNum = 5208;BA.debugLine="End Sub";
return "";
}
public static String  _pnlsearch_touch(int _action,float _x,float _y) throws Exception{
 //BA.debugLineNum = 2038;BA.debugLine="Sub pnlSearch_Touch (Action As Int, X As Float, Y";
 //BA.debugLineNum = 2040;BA.debugLine="End Sub";
return "";
}
public static String  _pnlsuperhb_touch(int _action,float _x,float _y) throws Exception{
 //BA.debugLineNum = 5860;BA.debugLine="Sub pnlSuperHB_Touch (Action As Int, X As Float, Y";
 //BA.debugLineNum = 5862;BA.debugLine="End Sub";
return "";
}
public static String  _pnlunusual_touch(int _action,float _x,float _y) throws Exception{
 //BA.debugLineNum = 5420;BA.debugLine="Sub pnlUnusual_Touch (Action As Int, X As Float, Y";
 //BA.debugLineNum = 5422;BA.debugLine="End Sub";
return "";
}
public static String  _pnlzeromsgbox_touch(int _action,float _x,float _y) throws Exception{
 //BA.debugLineNum = 3702;BA.debugLine="Sub pnlZeroMsgBox_Touch (Action As Int, X As Float";
 //BA.debugLineNum = 3704;BA.debugLine="End Sub";
return "";
}
public static String  _popsubmnu_itemclicked(de.amberhome.objects.appcompat.ACMenuItemWrapper _item) throws Exception{
String _sfilename = "";
 //BA.debugLineNum = 803;BA.debugLine="Sub PopSubMnu_ItemClicked (Item As ACMenuItem)";
 //BA.debugLineNum = 804;BA.debugLine="Dim sFileName As String";
_sfilename = "";
 //BA.debugLineNum = 805;BA.debugLine="GlobalVar.strBookSequence = BookNo & \"-\" & SeqNo";
mostCurrent._globalvar._strbooksequence /*String*/  = BA.NumberToString(_bookno)+"-"+mostCurrent._seqno;
 //BA.debugLineNum = 806;BA.debugLine="If cKeyboard.IsSoftKeyboardVisible = True Then cK";
if (mostCurrent._ckeyboard.IsSoftKeyboardVisible()==anywheresoftware.b4a.keywords.Common.True) { 
mostCurrent._ckeyboard.HideKeyboard();};
 //BA.debugLineNum = 808;BA.debugLine="Select Case Item.Id";
switch (BA.switchObjectToInt(_item.getId(),(int) (201),(int) (202),(int) (203))) {
case 0: {
 //BA.debugLineNum = 810;BA.debugLine="GlobalVar.isAverage = 0";
mostCurrent._globalvar._isaverage /*int*/  = (int) (0);
 //BA.debugLineNum = 811;BA.debugLine="Log(GlobalVar.strBookSequence)";
anywheresoftware.b4a.keywords.Common.LogImpl("121889032",mostCurrent._globalvar._strbooksequence /*String*/ ,0);
 //BA.debugLineNum = 812;BA.debugLine="Permissions.CheckAndRequest(Permissions.PERMISS";
_permissions.CheckAndRequest(processBA,_permissions.PERMISSION_CAMERA);
 //BA.debugLineNum = 813;BA.debugLine="StartActivity(NewCam)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._newcam.getObject()));
 //BA.debugLineNum = 814;BA.debugLine="GlobalVar.blnAverage = False";
mostCurrent._globalvar._blnaverage /*boolean*/  = anywheresoftware.b4a.keywords.Common.False;
 break; }
case 1: {
 //BA.debugLineNum = 816;BA.debugLine="intTempCurrPos = rsLoadedBook.Position";
_inttempcurrpos = mostCurrent._rsloadedbook.getPosition();
 //BA.debugLineNum = 817;BA.debugLine="If WasRead = 1 Then";
if (_wasread==1) { 
 //BA.debugLineNum = 818;BA.debugLine="If WasMisCoded = 1 Then";
if (_wasmiscoded==1) { 
 //BA.debugLineNum = 819;BA.debugLine="blnEdit = True";
_blnedit = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 821;BA.debugLine="vibration.vibratePattern(vibratePattern, 0)";
mostCurrent._vibration.vibratePattern(processBA,_vibratepattern,(int) (0));
 //BA.debugLineNum = 822;BA.debugLine="ShowAveNotAvailableDialog";
_showavenotavailabledialog();
 //BA.debugLineNum = 823;BA.debugLine="Return";
if (true) return "";
 };
 }else {
 //BA.debugLineNum = 826;BA.debugLine="If AcctStatus = \"dc\" Then";
if ((mostCurrent._acctstatus).equals("dc")) { 
 //BA.debugLineNum = 827;BA.debugLine="vibration.vibratePattern(vibratePattern, 0)";
mostCurrent._vibration.vibratePattern(processBA,_vibratepattern,(int) (0));
 //BA.debugLineNum = 828;BA.debugLine="ShowAveNotAvailableDialog";
_showavenotavailabledialog();
 //BA.debugLineNum = 829;BA.debugLine="Return";
if (true) return "";
 };
 };
 //BA.debugLineNum = 833;BA.debugLine="sFileName = sBillYear & \"-\" & sBillMonth & \"-\"";
_sfilename = mostCurrent._sbillyear+"-"+mostCurrent._sbillmonth+"-"+mostCurrent._acctno+".jpg";
 //BA.debugLineNum = 834;BA.debugLine="LogColor($\"File Exist: \"$ & File.Exists(File.Di";
anywheresoftware.b4a.keywords.Common.LogImpl("121889055",("File Exist: ")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),_sfilename)),anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 //BA.debugLineNum = 835;BA.debugLine="If File.Exists(File.Combine(File.DirRootExterna";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.Combine(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"DCIM/MeterReading"),_sfilename)==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 836;BA.debugLine="ShowPicRequiredDialog";
_showpicrequireddialog();
 //BA.debugLineNum = 837;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 839;BA.debugLine="ShowAverageBillRemarks";
_showaveragebillremarks();
 break; }
case 2: {
 //BA.debugLineNum = 842;BA.debugLine="rsLoadedBook.Position = intCurrPos";
mostCurrent._rsloadedbook.setPosition(_intcurrpos);
 //BA.debugLineNum = 843;BA.debugLine="DisplayRecord";
_displayrecord();
 //BA.debugLineNum = 844;BA.debugLine="ButtonState";
_buttonstate();
 break; }
}
;
 //BA.debugLineNum = 847;BA.debugLine="End Sub";
return "";
}
public static String  _popwarning_itemclicked(de.amberhome.objects.appcompat.ACMenuItemWrapper _item) throws Exception{
 //BA.debugLineNum = 765;BA.debugLine="Sub PopWarning_ItemClicked (Item As ACMenuItem)";
 //BA.debugLineNum = 766;BA.debugLine="Log(\"Popupmenu Item clicked: \" & Item.Id & \" - \"";
anywheresoftware.b4a.keywords.Common.LogImpl("121823489","Popupmenu Item clicked: "+BA.NumberToString(_item.getId())+" - "+_item.getTitle(),0);
 //BA.debugLineNum = 768;BA.debugLine="ToolBar.Menu.FindItem(1).Enabled = False";
mostCurrent._toolbar.getMenu().FindItem((int) (1)).setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 769;BA.debugLine="ToolBar.Menu.FindItem(2).Enabled = False";
mostCurrent._toolbar.getMenu().FindItem((int) (2)).setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 770;BA.debugLine="ToolBar.Menu.FindItem(3).Enabled = False";
mostCurrent._toolbar.getMenu().FindItem((int) (3)).setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 772;BA.debugLine="Select Case Item.Id";
switch (BA.switchObjectToInt(_item.getId(),(int) (101),(int) (102),(int) (103),(int) (104),(int) (105),(int) (106))) {
case 0: {
 //BA.debugLineNum = 774;BA.debugLine="intSearchFlag = 0";
_intsearchflag = (int) (0);
 break; }
case 1: {
 //BA.debugLineNum = 777;BA.debugLine="intSearchFlag = 1";
_intsearchflag = (int) (1);
 break; }
case 2: {
 //BA.debugLineNum = 780;BA.debugLine="intSearchFlag = 2";
_intsearchflag = (int) (2);
 break; }
case 3: {
 //BA.debugLineNum = 783;BA.debugLine="intSearchFlag = 3";
_intsearchflag = (int) (3);
 break; }
case 4: {
 //BA.debugLineNum = 786;BA.debugLine="intSearchFlag = 4";
_intsearchflag = (int) (4);
 break; }
case 5: {
 //BA.debugLineNum = 789;BA.debugLine="intSearchFlag = 5";
_intsearchflag = (int) (5);
 break; }
}
;
 //BA.debugLineNum = 792;BA.debugLine="If pnlUnusual.Visible = True Then Return";
if (mostCurrent._pnlunusual.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
if (true) return "";};
 //BA.debugLineNum = 794;BA.debugLine="cKeyboard.HideKeyboard";
mostCurrent._ckeyboard.HideKeyboard();
 //BA.debugLineNum = 795;BA.debugLine="DisableControls";
_disablecontrols();
 //BA.debugLineNum = 796;BA.debugLine="pnlUnusual.Visible = True";
mostCurrent._pnlunusual.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 797;BA.debugLine="SV.Initialize(Me,\"SV\")";
mostCurrent._sv._initialize /*String*/ (mostCurrent.activityBA,meterreading.getObject(),"SV");
 //BA.debugLineNum = 798;BA.debugLine="SV.AddToParent(pnlSearchUnusual,2%x, 3.5%y, pnlSe";
mostCurrent._sv._addtoparent /*String*/ (mostCurrent._pnlsearchunusual,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (2),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (3.5),mostCurrent.activityBA),(int) (mostCurrent._pnlsearchunusual.getWidth()-anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (2),mostCurrent.activityBA)),(int) (mostCurrent._pnlsearchunusual.getHeight()-anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (1),mostCurrent.activityBA)));
 //BA.debugLineNum = 799;BA.debugLine="SV.ClearAll";
mostCurrent._sv._clearall /*String*/ ();
 //BA.debugLineNum = 800;BA.debugLine="SearchUnsualReading(intSearchFlag)";
_searchunsualreading(_intsearchflag);
 //BA.debugLineNum = 801;BA.debugLine="End Sub";
return "";
}
public static String  _printbilldata(int _ireadid) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsdata = null;
String _sdiscondate = "";
long _ddiscondate = 0L;
anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper _sgdeposit = null;
anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper _saddtobillamt = null;
anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper _sarrearsamt = null;
anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper _sadvpayment = null;
double _dseptagetotal = 0;
String _sreaddate = "";
String _sreadtime = "";
long _lreadtime = 0L;
String _reverse = "";
String _unreverse = "";
String _fullcut = "";
 //BA.debugLineNum = 3808;BA.debugLine="Private Sub PrintBillData(iReadID As Int)";
 //BA.debugLineNum = 3809;BA.debugLine="Dim rsData As Cursor";
_rsdata = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 3810;BA.debugLine="Dim sDisconDate As String";
_sdiscondate = "";
 //BA.debugLineNum = 3812;BA.debugLine="Dim dDisconDate As Long";
_ddiscondate = 0L;
 //BA.debugLineNum = 3813;BA.debugLine="Dim sGDeposit As BigDecimal";
_sgdeposit = new anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper();
 //BA.debugLineNum = 3814;BA.debugLine="Dim sAddToBillAmt As BigDecimal";
_saddtobillamt = new anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper();
 //BA.debugLineNum = 3815;BA.debugLine="Dim sArrearsAmt As BigDecimal";
_sarrearsamt = new anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper();
 //BA.debugLineNum = 3816;BA.debugLine="Dim sAdvPayment As BigDecimal";
_sadvpayment = new anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper();
 //BA.debugLineNum = 3817;BA.debugLine="Dim dSeptageTotal As Double";
_dseptagetotal = 0;
 //BA.debugLineNum = 3818;BA.debugLine="Dim sReadDate As String";
_sreaddate = "";
 //BA.debugLineNum = 3819;BA.debugLine="Dim sReadTime As String";
_sreadtime = "";
 //BA.debugLineNum = 3820;BA.debugLine="Dim lReadTime As Long";
_lreadtime = 0L;
 //BA.debugLineNum = 3823;BA.debugLine="Dim REVERSE As String = Chr(29) & \"B\" & Chr(1)";
_reverse = BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (29)))+"B"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)));
 //BA.debugLineNum = 3824;BA.debugLine="Dim UNREVERSE As String = Chr(29) & \"B\" & Chr(0)";
_unreverse = BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (29)))+"B"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)));
 //BA.debugLineNum = 3825;BA.debugLine="Dim FULLCUT As String = Chr(29) & \"V\" & Chr(1)";
_fullcut = BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (29)))+"V"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)));
 //BA.debugLineNum = 3830;BA.debugLine="ProgressDialogShow2($\"Bill Statement Printing...\"";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence(("Bill Statement Printing...")),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3832;BA.debugLine="Try";
try { //BA.debugLineNum = 3833;BA.debugLine="Starter.strCriteria = \"SELECT * FROM tblReadings";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblReadings WHERE ReadID = "+BA.NumberToString(_ireadid)+" "+"AND BookID = "+BA.NumberToString(mostCurrent._globalvar._bookid /*int*/ )+" "+"AND BillYear = "+BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ )+" "+"AND BillMonth = "+BA.NumberToString(mostCurrent._globalvar._billmonth /*int*/ );
 //BA.debugLineNum = 3837;BA.debugLine="rsData = Starter.DBCon.ExecQuery(Starter.strCrit";
_rsdata = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 3839;BA.debugLine="If rsData.RowCount > 0 Then";
if (_rsdata.getRowCount()>0) { 
 //BA.debugLineNum = 3840;BA.debugLine="rsData.Position = 0";
_rsdata.setPosition((int) (0));
 //BA.debugLineNum = 3841;BA.debugLine="PresRdg = rsData.GetInt(\"PresRdg\")";
mostCurrent._presrdg = BA.NumberToString(_rsdata.GetInt("PresRdg"));
 //BA.debugLineNum = 3842;BA.debugLine="If strSF.Len(strSF.Trim(PresRdg)) = 0 Then";
if (mostCurrent._strsf._vvv7(mostCurrent._strsf._vvvvvvv4(mostCurrent._presrdg))==0) { 
 //BA.debugLineNum = 3843;BA.debugLine="rsData.Close";
_rsdata.Close();
 //BA.debugLineNum = 3844;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 3846;BA.debugLine="AcctNo = rsData.GetString(\"AcctNo\")";
mostCurrent._acctno = _rsdata.GetString("AcctNo");
 //BA.debugLineNum = 3847;BA.debugLine="AcctName = rsData.GetString(\"AcctName\")";
mostCurrent._acctname = _rsdata.GetString("AcctName");
 //BA.debugLineNum = 3848;BA.debugLine="AcctAddress = rsData.GetString(\"AcctAddress\")";
mostCurrent._acctaddress = _rsdata.GetString("AcctAddress");
 //BA.debugLineNum = 3849;BA.debugLine="AcctStatus = rsData.GetString(\"AcctStatus\")";
mostCurrent._acctstatus = _rsdata.GetString("AcctStatus");
 //BA.debugLineNum = 3851;BA.debugLine="MeterNo = rsData.GetString(\"MeterNo\")";
mostCurrent._meterno = _rsdata.GetString("MeterNo");
 //BA.debugLineNum = 3852;BA.debugLine="BookID = rsData.GetInt(\"BookID\")";
_bookid = _rsdata.GetInt("BookID");
 //BA.debugLineNum = 3853;BA.debugLine="BookSeq = rsData.GetString(\"BookNo\") & \"-\" & rs";
mostCurrent._bookseq = _rsdata.GetString("BookNo")+"-"+_rsdata.GetString("SeqNo");
 //BA.debugLineNum = 3854;BA.debugLine="CustClass = rsData.GetString(\"AcctClass\") & \"-\"";
mostCurrent._custclass = _rsdata.GetString("AcctClass")+"-"+_rsdata.GetString("AcctSubClass");
 //BA.debugLineNum = 3855;BA.debugLine="GDeposit = Round2(rsData.GetDouble(\"GDeposit\"),";
_gdeposit = anywheresoftware.b4a.keywords.Common.Round2(_rsdata.GetDouble("GDeposit"),(int) (2));
 //BA.debugLineNum = 3856;BA.debugLine="BillNo = rsData.GetString(\"BillNo\")";
mostCurrent._billno = _rsdata.GetString("BillNo");
 //BA.debugLineNum = 3857;BA.debugLine="BillDate = rsData.GetString(\"DateRead\")";
mostCurrent._billdate = _rsdata.GetString("DateRead");
 //BA.debugLineNum = 3860;BA.debugLine="PrevRdg = rsData.GetInt(\"PrevRdg\")";
_prevrdg = _rsdata.GetInt("PrevRdg");
 //BA.debugLineNum = 3861;BA.debugLine="AddCons = rsData.GetInt(\"AddCons\")";
_addcons = _rsdata.GetInt("AddCons");
 //BA.debugLineNum = 3862;BA.debugLine="TotalCum = rsData.GetInt(\"TotalCum\")";
_totalcum = _rsdata.GetInt("TotalCum");
 //BA.debugLineNum = 3864;BA.debugLine="BasicAmt =Round2(rsData.GetDouble(\"BasicAmt\"),2";
_basicamt = anywheresoftware.b4a.keywords.Common.Round2(_rsdata.GetDouble("BasicAmt"),(int) (2));
 //BA.debugLineNum = 3865;BA.debugLine="PCAAmt = Round2(rsData.GetDouble(\"PCAAmt\"),2)";
_pcaamt = anywheresoftware.b4a.keywords.Common.Round2(_rsdata.GetDouble("PCAAmt"),(int) (2));
 //BA.debugLineNum = 3866;BA.debugLine="CurrentAmt = Round2(rsData.GetDouble(\"CurrentAm";
_currentamt = anywheresoftware.b4a.keywords.Common.Round2(_rsdata.GetDouble("CurrentAmt"),(int) (2));
 //BA.debugLineNum = 3867;BA.debugLine="SeniorOnBeforeAmt = Round2(rsData.GetDouble(\"Se";
_senioronbeforeamt = anywheresoftware.b4a.keywords.Common.Round2(_rsdata.GetDouble("SeniorOnBeforeAmt"),(int) (2));
 //BA.debugLineNum = 3868;BA.debugLine="AddToBillAmt = Round2(rsData.GetDouble(\"AddToBi";
_addtobillamt = anywheresoftware.b4a.keywords.Common.Round2(_rsdata.GetDouble("AddToBillAmt"),(int) (2));
 //BA.debugLineNum = 3869;BA.debugLine="MeterCharges = Round2(rsData.GetDouble(\"MeterCh";
_metercharges = anywheresoftware.b4a.keywords.Common.Round2(_rsdata.GetDouble("MeterCharges"),(int) (2));
 //BA.debugLineNum = 3870;BA.debugLine="FranchiseTaxAmt = Round2(rsData.GetDouble(\"Fran";
_franchisetaxamt = anywheresoftware.b4a.keywords.Common.Round2(_rsdata.GetDouble("FranchiseTaxAmt"),(int) (2));
 //BA.debugLineNum = 3871;BA.debugLine="SeptageFeeAmt = Round2(rsData.GetDouble(\"Septag";
_septagefeeamt = anywheresoftware.b4a.keywords.Common.Round2(_rsdata.GetDouble("SeptageFeeAmt"),(int) (2));
 //BA.debugLineNum = 3872;BA.debugLine="SeptageFee = Round2(rsData.GetDouble(\"SeptageAm";
_septagefee = anywheresoftware.b4a.keywords.Common.Round2(_rsdata.GetDouble("SeptageAmt"),(int) (2));
 //BA.debugLineNum = 3874;BA.debugLine="If GlobalVar.BranchID = 28 Or GlobalVar.BranchI";
if (mostCurrent._globalvar._branchid /*int*/ ==28 || mostCurrent._globalvar._branchid /*int*/ ==29 || mostCurrent._globalvar._branchid /*int*/ ==30) { 
 //BA.debugLineNum = 3875;BA.debugLine="SeptageArrears = Round2(rsData.GetDouble(\"Sept";
_septagearrears = anywheresoftware.b4a.keywords.Common.Round2(_rsdata.GetDouble("SeptageArrears"),(int) (2));
 };
 //BA.debugLineNum = 3878;BA.debugLine="ArrearsAmt = Round2(rsData.GetDouble(\"ArrearsAm";
_arrearsamt = anywheresoftware.b4a.keywords.Common.Round2(_rsdata.GetDouble("ArrearsAmt"),(int) (2));
 //BA.debugLineNum = 3879;BA.debugLine="AdvPayment = Round2(rsData.GetDouble(\"AdvPaymen";
_advpayment = anywheresoftware.b4a.keywords.Common.Round2(_rsdata.GetDouble("AdvPayment"),(int) (2));
 //BA.debugLineNum = 3880;BA.debugLine="TotalDueAmt = Round2(rsData.GetDouble(\"TotalDue";
_totaldueamt = anywheresoftware.b4a.keywords.Common.Round2(_rsdata.GetDouble("TotalDueAmt"),(int) (2));
 //BA.debugLineNum = 3881;BA.debugLine="TotalDueAmtBeforeSC = Round2(rsData.GetDouble(\"";
_totaldueamtbeforesc = anywheresoftware.b4a.keywords.Common.Round2(_rsdata.GetDouble("TotalDueAmtBeforeSC"),(int) (2));
 //BA.debugLineNum = 3882;BA.debugLine="iHasSeptage = rsData.GetInt(\"HasSeptageFee\")";
_ihasseptage = _rsdata.GetInt("HasSeptageFee");
 //BA.debugLineNum = 3883;BA.debugLine="dSeptageRate = rsData.GetDouble(\"SeptageRate\")";
_dseptagerate = _rsdata.GetDouble("SeptageRate");
 //BA.debugLineNum = 3885;BA.debugLine="WithDueDate = rsData.GetInt(\"WithDueDate\")";
_withduedate = _rsdata.GetInt("WithDueDate");
 //BA.debugLineNum = 3893;BA.debugLine="PenaltyAmt = Round2(rsData.GetDouble(\"PenaltyAm";
_penaltyamt = anywheresoftware.b4a.keywords.Common.Round2(_rsdata.GetDouble("PenaltyAmt"),(int) (2));
 //BA.debugLineNum = 3894;BA.debugLine="SeniorAfterAmt = Round2(rsData.GetDouble(\"Senio";
_seniorafteramt = anywheresoftware.b4a.keywords.Common.Round2(_rsdata.GetDouble("SeniorAfterAmt"),(int) (2));
 //BA.debugLineNum = 3895;BA.debugLine="TotalDueAmtAfterSC = Round2(rsData.GetDouble(\"T";
_totaldueamtaftersc = anywheresoftware.b4a.keywords.Common.Round2(_rsdata.GetDouble("TotalDueAmtAfterSC"),(int) (2));
 //BA.debugLineNum = 3896;BA.debugLine="DiscAmt2 = Round2(rsData.GetDouble(\"DiscAmt\"),";
_discamt2 = anywheresoftware.b4a.keywords.Common.Round2(_rsdata.GetDouble("DiscAmt"),(int) (2));
 //BA.debugLineNum = 3913;BA.debugLine="DateFrom = rsData.GetString(\"DateFrom\")";
mostCurrent._datefrom = _rsdata.GetString("DateFrom");
 //BA.debugLineNum = 3914;BA.debugLine="DateTo = rsData.GetString(\"DateRead\")";
mostCurrent._dateto = _rsdata.GetString("DateRead");
 //BA.debugLineNum = 3915;BA.debugLine="BillPeriod1st = rsData.GetString(\"BillPeriod1st";
mostCurrent._billperiod1st = _rsdata.GetString("BillPeriod1st");
 //BA.debugLineNum = 3916;BA.debugLine="BillPeriod2nd = rsData.GetString(\"BillPeriod2nd";
mostCurrent._billperiod2nd = _rsdata.GetString("BillPeriod2nd");
 //BA.debugLineNum = 3917;BA.debugLine="BillPeriod3rd = rsData.GetString(\"BillPeriod3rd";
mostCurrent._billperiod3rd = _rsdata.GetString("BillPeriod3rd");
 //BA.debugLineNum = 3918;BA.debugLine="PrevCum1st = rsData.GetInt(\"PrevCum1st\")";
_prevcum1st = _rsdata.GetInt("PrevCum1st");
 //BA.debugLineNum = 3919;BA.debugLine="PrevCum2nd = rsData.GetInt(\"PrevCum2nd\")";
_prevcum2nd = _rsdata.GetInt("PrevCum2nd");
 //BA.debugLineNum = 3920;BA.debugLine="PrevCum3rd = rsData.GetInt(\"PrevCum3rd\")";
_prevcum3rd = _rsdata.GetInt("PrevCum3rd");
 //BA.debugLineNum = 3921;BA.debugLine="DisconnectionDate = rsData.GetString(\"Disconnec";
mostCurrent._disconnectiondate = _rsdata.GetString("DisconnectionDate");
 //BA.debugLineNum = 3923;BA.debugLine="sReadDate = rsData.GetString(\"DateRead\")";
_sreaddate = _rsdata.GetString("DateRead");
 //BA.debugLineNum = 3924;BA.debugLine="sReadTime=  rsData.GetString(\"TimeRead\")";
_sreadtime = _rsdata.GetString("TimeRead");
 };
 } 
       catch (Exception e76) {
			processBA.setLastException(e76); //BA.debugLineNum = 3933;BA.debugLine="ToastMessageShow($\"Unable to Print Bill Statemen";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to Print Bill Statement due to ")+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage()),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 3934;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("127852926",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 3948;BA.debugLine="If GlobalVar.BranchID = 42 Or GlobalVar.BranchID";
if (mostCurrent._globalvar._branchid /*int*/ ==42 || mostCurrent._globalvar._branchid /*int*/ ==48) { 
 //BA.debugLineNum = 3949;BA.debugLine="sBranchName = $\"BWSI - CLPI\"$ & CRLF & $\"BRANCH";
mostCurrent._sbranchname = ("BWSI - CLPI")+anywheresoftware.b4a.keywords.Common.CRLF+("BRANCHES INC.");
 }else if(mostCurrent._globalvar._branchid /*int*/ ==76) { 
 //BA.debugLineNum = 3951;BA.debugLine="sBranchName = $\"LAPIDSVILLE\"$";
mostCurrent._sbranchname = ("LAPIDSVILLE");
 }else if(mostCurrent._globalvar._branchid /*int*/ ==56) { 
 //BA.debugLineNum = 3953;BA.debugLine="sBranchName = $\"STO. DOMINGO 3H\"$ & CRLF & $\"WA";
mostCurrent._sbranchname = ("STO. DOMINGO 3H")+anywheresoftware.b4a.keywords.Common.CRLF+("WATERWORKS INC.");
 }else {
 //BA.debugLineNum = 3955;BA.debugLine="sBranchName = strSF.Upper(strSF.Right(GlobalVar";
mostCurrent._sbranchname = mostCurrent._strsf._vvvvvvv5(mostCurrent._strsf._vvvvv7(mostCurrent._globalvar._branchname /*String*/ ,(long) (mostCurrent._globalvar._branchname /*String*/ .length()-7)));
 };
 //BA.debugLineNum = 3958;BA.debugLine="If GlobalVar.BranchID = 1 Then 'Garden Villas";
if (mostCurrent._globalvar._branchid /*int*/ ==1) { 
 //BA.debugLineNum = 3959;BA.debugLine="sBranchAddress = GlobalVar.BranchAddress & CRLF";
mostCurrent._sbranchaddress = mostCurrent._globalvar._branchaddress /*String*/ +anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"Brgy. Ibaba, Sta. Rosa, Laguna";
 }else if(mostCurrent._globalvar._branchid /*int*/ ==5) { 
 //BA.debugLineNum = 3961;BA.debugLine="sBranchAddress = GlobalVar.BranchAddress & CRLF";
mostCurrent._sbranchaddress = mostCurrent._globalvar._branchaddress /*String*/ +anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"Balibago, Angeles City";
 }else if(mostCurrent._globalvar._branchid /*int*/ ==8) { 
 //BA.debugLineNum = 3963;BA.debugLine="sBranchAddress = GlobalVar.BranchAddress & CRLF";
mostCurrent._sbranchaddress = mostCurrent._globalvar._branchaddress /*String*/ +anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"Sta. Barbara, Pangasinan";
 }else if(mostCurrent._globalvar._branchid /*int*/ ==19) { 
 //BA.debugLineNum = 3965;BA.debugLine="sBranchAddress = GlobalVar.BranchAddress & CRLF";
mostCurrent._sbranchaddress = mostCurrent._globalvar._branchaddress /*String*/ +anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"Porac, Pampanga";
 }else if(mostCurrent._globalvar._branchid /*int*/ ==25) { 
 //BA.debugLineNum = 3967;BA.debugLine="sBranchAddress = GlobalVar.BranchAddress & CRLF";
mostCurrent._sbranchaddress = mostCurrent._globalvar._branchaddress /*String*/ +anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"Barangay Wawa, Balagtas, Bulacan";
 }else if(mostCurrent._globalvar._branchid /*int*/ ==28 || mostCurrent._globalvar._branchid /*int*/ ==29 || mostCurrent._globalvar._branchid /*int*/ ==30) { 
 //BA.debugLineNum = 3969;BA.debugLine="sBranchAddress = GlobalVar.BranchAddress & CRLF";
mostCurrent._sbranchaddress = mostCurrent._globalvar._branchaddress /*String*/ +anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"Guiguinto, Bulacan";
 }else if(mostCurrent._globalvar._branchid /*int*/ ==32 || mostCurrent._globalvar._branchid /*int*/ ==33 || mostCurrent._globalvar._branchid /*int*/ ==34 || mostCurrent._globalvar._branchid /*int*/ ==35) { 
 //BA.debugLineNum = 3971;BA.debugLine="sBranchAddress = GlobalVar.BranchAddress & CRLF";
mostCurrent._sbranchaddress = mostCurrent._globalvar._branchaddress /*String*/ +anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"Pandi, Bulacan";
 }else if(mostCurrent._globalvar._branchid /*int*/ ==36) { 
 //BA.debugLineNum = 3973;BA.debugLine="sBranchAddress = GlobalVar.BranchAddress & CRLF";
mostCurrent._sbranchaddress = mostCurrent._globalvar._branchaddress /*String*/ +anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"Poblacion, Paombong, Bulacan";
 }else if(mostCurrent._globalvar._branchid /*int*/ ==37) { 
 //BA.debugLineNum = 3975;BA.debugLine="sBranchAddress = GlobalVar.BranchAddress & CRLF";
mostCurrent._sbranchaddress = mostCurrent._globalvar._branchaddress /*String*/ +anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"Nueva Vizcaya";
 }else if(mostCurrent._globalvar._branchid /*int*/ ==40) { 
 //BA.debugLineNum = 3977;BA.debugLine="sBranchAddress = GlobalVar.BranchAddress & CRLF";
mostCurrent._sbranchaddress = mostCurrent._globalvar._branchaddress /*String*/ +anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"Bagabag, Nueva Vizcaya";
 }else if(mostCurrent._globalvar._branchid /*int*/ ==53) { 
 //BA.debugLineNum = 3979;BA.debugLine="sBranchAddress = GlobalVar.BranchAddress & CRLF";
mostCurrent._sbranchaddress = mostCurrent._globalvar._branchaddress /*String*/ +anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"Passi City, Iloilo";
 }else if(mostCurrent._globalvar._branchid /*int*/ ==66) { 
 //BA.debugLineNum = 3981;BA.debugLine="sBranchAddress = GlobalVar.BranchAddress & CRLF";
mostCurrent._sbranchaddress = mostCurrent._globalvar._branchaddress /*String*/ +anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"Davao Oriental";
 }else if(mostCurrent._globalvar._branchid /*int*/ ==67) { 
 //BA.debugLineNum = 3983;BA.debugLine="sBranchAddress = GlobalVar.BranchAddress & CRLF";
mostCurrent._sbranchaddress = mostCurrent._globalvar._branchaddress /*String*/ +anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"Brgy. Siling Bata, Pandi, Bulacan";
 }else if(mostCurrent._globalvar._branchid /*int*/ ==68) { 
 //BA.debugLineNum = 3985;BA.debugLine="sBranchAddress = GlobalVar.BranchAddress & CRLF";
mostCurrent._sbranchaddress = mostCurrent._globalvar._branchaddress /*String*/ +anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"San Miguel, Bulacan";
 }else if(mostCurrent._globalvar._branchid /*int*/ ==70) { 
 //BA.debugLineNum = 3987;BA.debugLine="sBranchAddress = GlobalVar.BranchAddress & CRLF";
mostCurrent._sbranchaddress = mostCurrent._globalvar._branchaddress /*String*/ +anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"Richwood Townhomes, Navarro, Gen Trias (C)";
 }else if(mostCurrent._globalvar._branchid /*int*/ ==72) { 
 //BA.debugLineNum = 3989;BA.debugLine="sBranchAddress = GlobalVar.BranchAddress & CRLF";
mostCurrent._sbranchaddress = mostCurrent._globalvar._branchaddress /*String*/ +anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"Poblacion, Barotac Viejo, Iloilo";
 }else if(mostCurrent._globalvar._branchid /*int*/ ==77) { 
 //BA.debugLineNum = 3991;BA.debugLine="sBranchAddress = GlobalVar.BranchAddress & CRLF";
mostCurrent._sbranchaddress = mostCurrent._globalvar._branchaddress /*String*/ +anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"Calinog, Iloilo";
 }else if(mostCurrent._globalvar._branchid /*int*/ ==83) { 
 //BA.debugLineNum = 3993;BA.debugLine="sBranchAddress = GlobalVar.BranchAddress & CRLF";
mostCurrent._sbranchaddress = mostCurrent._globalvar._branchaddress /*String*/ +anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"Angeles City";
 }else if(mostCurrent._globalvar._branchid /*int*/ ==84) { 
 //BA.debugLineNum = 3995;BA.debugLine="sBranchAddress = GlobalVar.BranchAddress & CRLF";
mostCurrent._sbranchaddress = mostCurrent._globalvar._branchaddress /*String*/ +anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"Cuayan, Angeles City";
 }else if(mostCurrent._globalvar._branchid /*int*/ ==85) { 
 //BA.debugLineNum = 3997;BA.debugLine="sBranchAddress = GlobalVar.BranchAddress & CRLF";
mostCurrent._sbranchaddress = mostCurrent._globalvar._branchaddress /*String*/ +anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"San Ildefonso, Bulacan";
 }else if(mostCurrent._globalvar._branchid /*int*/ ==86) { 
 //BA.debugLineNum = 3999;BA.debugLine="sBranchAddress = GlobalVar.BranchAddress & CRLF";
mostCurrent._sbranchaddress = mostCurrent._globalvar._branchaddress /*String*/ +anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"Palayan City, Nueva Ecija";
 }else if(mostCurrent._globalvar._branchid /*int*/ ==88) { 
 //BA.debugLineNum = 4001;BA.debugLine="sBranchAddress = GlobalVar.BranchAddress & CRLF";
mostCurrent._sbranchaddress = mostCurrent._globalvar._branchaddress /*String*/ +anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"San Pascual, Batangas";
 }else if(mostCurrent._globalvar._branchid /*int*/ ==91) { 
 //BA.debugLineNum = 4003;BA.debugLine="sBranchAddress = GlobalVar.BranchAddress & CRLF";
mostCurrent._sbranchaddress = mostCurrent._globalvar._branchaddress /*String*/ +anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"Angeles City";
 }else if(mostCurrent._globalvar._branchid /*int*/ ==93) { 
 //BA.debugLineNum = 4005;BA.debugLine="sBranchAddress = GlobalVar.BranchAddress & CRLF";
mostCurrent._sbranchaddress = mostCurrent._globalvar._branchaddress /*String*/ +anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"Pangasinan";
 }else {
 //BA.debugLineNum = 4007;BA.debugLine="sBranchAddress = GlobalVar.BranchAddress";
mostCurrent._sbranchaddress = mostCurrent._globalvar._branchaddress /*String*/ ;
 };
 //BA.debugLineNum = 4010;BA.debugLine="If GlobalVar.BranchID = 76 Then";
if (mostCurrent._globalvar._branchid /*int*/ ==76) { 
 //BA.debugLineNum = 4011;BA.debugLine="sBranchContactNo = \"\"";
mostCurrent._sbranchcontactno = "";
 }else {
 //BA.debugLineNum = 4013;BA.debugLine="sBranchContactNo = GlobalVar.BranchContactNo";
mostCurrent._sbranchcontactno = mostCurrent._globalvar._branchcontactno /*String*/ ;
 };
 //BA.debugLineNum = 4018;BA.debugLine="LogColor(strSF.Len(strSF.Trim(GlobalVar.BranchTI";
anywheresoftware.b4a.keywords.Common.LogImpl("127853010",BA.NumberToString(mostCurrent._strsf._vvv7(mostCurrent._strsf._vvvvvvv4(mostCurrent._globalvar._branchtinumber /*String*/ ))),anywheresoftware.b4a.keywords.Common.Colors.Cyan);
 //BA.debugLineNum = 4020;BA.debugLine="If strSF.Len(strSF.Trim(GlobalVar.BranchTINumber";
if (mostCurrent._strsf._vvv7(mostCurrent._strsf._vvvvvvv4(mostCurrent._globalvar._branchtinumber /*String*/ ))>0) { 
 //BA.debugLineNum = 4021;BA.debugLine="sTinNo = $\"TIN NO: \"$ & GlobalVar.BranchTINumbe";
mostCurrent._stinno = ("TIN NO: ")+mostCurrent._globalvar._branchtinumber /*String*/ ;
 }else {
 //BA.debugLineNum = 4023;BA.debugLine="sTinNo = $\"\"$";
mostCurrent._stinno = ("");
 };
 //BA.debugLineNum = 4026;BA.debugLine="sBranchCode = GlobalVar.BranchCode";
mostCurrent._sbranchcode = mostCurrent._globalvar._branchcode /*String*/ ;
 //BA.debugLineNum = 4027;BA.debugLine="sBillPeriod = GlobalVar.BillPeriod";
mostCurrent._sbillperiod = mostCurrent._globalvar._billperiod /*String*/ ;
 //BA.debugLineNum = 4030;BA.debugLine="sPresRdg = PresRdg";
mostCurrent._spresrdg = mostCurrent._presrdg;
 //BA.debugLineNum = 4031;BA.debugLine="sPrevRdg = PrevRdg";
mostCurrent._sprevrdg = BA.NumberToString(_prevrdg);
 //BA.debugLineNum = 4032;BA.debugLine="sAddCons = AddCons";
mostCurrent._saddcons = BA.NumberToString(_addcons);
 //BA.debugLineNum = 4033;BA.debugLine="sTotalCum = TotalCum";
mostCurrent._stotalcum = BA.NumberToString(_totalcum);
 //BA.debugLineNum = 4036;BA.debugLine="sGDeposit.Initialize(GDeposit)";
_sgdeposit.Initialize(BA.NumberToString(_gdeposit));
 //BA.debugLineNum = 4037;BA.debugLine="sGDeposit = RoundtoCurrency(sGDeposit,2)";
_sgdeposit = _roundtocurrency(_sgdeposit,(int) (2));
 //BA.debugLineNum = 4044;BA.debugLine="sAddToBillAmt.Initialize(AddToBillAmt)'Others";
_saddtobillamt.Initialize(BA.NumberToString(_addtobillamt));
 //BA.debugLineNum = 4045;BA.debugLine="sAddToBillAmt = RoundtoCurrency(sAddToBillAmt,2)";
_saddtobillamt = _roundtocurrency(_saddtobillamt,(int) (2));
 //BA.debugLineNum = 4047;BA.debugLine="sArrearsAmt.Initialize(ArrearsAmt)";
_sarrearsamt.Initialize(BA.NumberToString(_arrearsamt));
 //BA.debugLineNum = 4048;BA.debugLine="sArrearsAmt = RoundtoCurrency(sArrearsAmt,2)";
_sarrearsamt = _roundtocurrency(_sarrearsamt,(int) (2));
 //BA.debugLineNum = 4050;BA.debugLine="sAdvPayment.Initialize(AdvPayment)";
_sadvpayment.Initialize(BA.NumberToString(_advpayment));
 //BA.debugLineNum = 4051;BA.debugLine="sAdvPayment = RoundtoCurrency(sAdvPayment,2)";
_sadvpayment = _roundtocurrency(_sadvpayment,(int) (2));
 //BA.debugLineNum = 4064;BA.debugLine="If GlobalVar.BranchID = 91 Then 'CDO - Remove Co";
if (mostCurrent._globalvar._branchid /*int*/ ==91) { 
 //BA.debugLineNum = 4065;BA.debugLine="PrintBuffer = Chr(27) & \"@\" _ 				& Chr(27) & C";
_printbuffer = BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"@"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+mostCurrent._sbranchname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+mostCurrent._sbranchaddress+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"Branch Code: "+mostCurrent._sbranchcode+anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+"STATEMENT OF ACCOUNT"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"For the Month of "+mostCurrent._sbillperiod+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+"Account No: "+mostCurrent._acctno+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+mostCurrent._strsf._vvvvvvv5(mostCurrent._acctname)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+mostCurrent._strsf._vvv6(_titlecase(mostCurrent._acctaddress),(long) (42))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+"Meter No: "+mostCurrent._meterno+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"Book-Seq: "+mostCurrent._bookseq+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"Class   : "+mostCurrent._custclass+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+"Guarantee Deposit "+_addwhitespaces2(anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(BA.ObjectToNumber(_sgdeposit)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(BA.ObjectToNumber(_sgdeposit)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"Bill Number       "+_addwhitespaces2(mostCurrent._billno)+mostCurrent._billno+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"Bill Date         "+_addwhitespaces2(mostCurrent._billdate)+mostCurrent._billdate+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+"Period From       "+_addwhitespaces2(mostCurrent._datefrom)+mostCurrent._datefrom+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+"Period To         "+_addwhitespaces2(mostCurrent._dateto)+mostCurrent._dateto+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"Pres. Reading      "+_addwhitespaces(mostCurrent._spresrdg)+mostCurrent._spresrdg+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"Prev. Reading      "+_addwhitespaces(mostCurrent._sprevrdg)+mostCurrent._sprevrdg+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"Add Cons.          "+_addwhitespaces(mostCurrent._saddcons)+mostCurrent._saddcons+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"Total Cum          "+_addwhitespaces(mostCurrent._stotalcum)+mostCurrent._stotalcum+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"BASIC              "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_basicamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_basicamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"PCA                "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_pcaamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_pcaamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+"CURRENT BILL       "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_currentamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_currentamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"SR. CITIZEN DISC.  "+_addwhitespaces("("+anywheresoftware.b4a.keywords.Common.NumberFormat2(_senioronbeforeamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")")+"("+anywheresoftware.b4a.keywords.Common.NumberFormat2(_senioronbeforeamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 }else {
 //BA.debugLineNum = 4098;BA.debugLine="PrintBuffer = Chr(27) & \"@\" _ 				& Chr(27) & Ch";
_printbuffer = BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"@"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+mostCurrent._sbranchname+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (14)))+mostCurrent._sbranchaddress+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+mostCurrent._sbranchcontactno+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+mostCurrent._stinno+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"Branch Code: "+mostCurrent._sbranchcode+anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+"STATEMENT OF ACCOUNT"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"For the Month of "+mostCurrent._sbillperiod+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+"Account No: "+mostCurrent._acctno+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+mostCurrent._strsf._vvvvvvv5(mostCurrent._acctname)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+mostCurrent._strsf._vvv6(_titlecase(mostCurrent._acctaddress),(long) (42))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+"Meter No: "+mostCurrent._meterno+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"Book-Seq: "+mostCurrent._bookseq+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"Class   : "+mostCurrent._custclass+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+"Guarantee Deposit "+_addwhitespaces2(anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(BA.ObjectToNumber(_sgdeposit)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(BA.ObjectToNumber(_sgdeposit)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"Bill Number       "+_addwhitespaces2(mostCurrent._billno)+mostCurrent._billno+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"Bill Date         "+_addwhitespaces2(mostCurrent._billdate)+mostCurrent._billdate+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+"Period From       "+_addwhitespaces2(mostCurrent._datefrom)+mostCurrent._datefrom+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+"Period To         "+_addwhitespaces2(mostCurrent._dateto)+mostCurrent._dateto+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"Pres. Reading      "+_addwhitespaces(mostCurrent._spresrdg)+mostCurrent._spresrdg+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"Prev. Reading      "+_addwhitespaces(mostCurrent._sprevrdg)+mostCurrent._sprevrdg+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"Add Cons.          "+_addwhitespaces(mostCurrent._saddcons)+mostCurrent._saddcons+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"Total Cum          "+_addwhitespaces(mostCurrent._stotalcum)+mostCurrent._stotalcum+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"BASIC              "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_basicamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_basicamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"PCA                "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_pcaamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_pcaamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+"CURRENT BILL       "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_currentamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_currentamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"SR. CITIZEN DISC.  "+_addwhitespaces("("+anywheresoftware.b4a.keywords.Common.NumberFormat2(_senioronbeforeamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")")+"("+anywheresoftware.b4a.keywords.Common.NumberFormat2(_senioronbeforeamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 };
 //BA.debugLineNum = 4134;BA.debugLine="If DiscAmt2 > 0 Then";
if (_discamt2>0) { 
 //BA.debugLineNum = 4135;BA.debugLine="PrintBuffer = PrintBuffer & Chr(27) & \"!\" & Chr";
_printbuffer = _printbuffer+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"DISCOUNT           "+_addwhitespaces("("+anywheresoftware.b4a.keywords.Common.NumberFormat2(_discamt2,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")")+"("+anywheresoftware.b4a.keywords.Common.NumberFormat2(_discamt2,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 };
 //BA.debugLineNum = 4138;BA.debugLine="PrintBuffer = PrintBuffer & Chr(27) & \"!\" & Chr(";
_printbuffer = _printbuffer+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"OTHERS             "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(BA.ObjectToNumber(_saddtobillamt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(BA.ObjectToNumber(_saddtobillamt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 //BA.debugLineNum = 4140;BA.debugLine="If GlobalVar.WithMeterCharges = 1 Then";
if (mostCurrent._globalvar._withmetercharges /*int*/ ==1) { 
 //BA.debugLineNum = 4141;BA.debugLine="PrintBuffer = PrintBuffer & \"METER CHARGES";
_printbuffer = _printbuffer+"METER CHARGES      "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_metercharges,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_metercharges,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 };
 //BA.debugLineNum = 4144;BA.debugLine="If GlobalVar.WithFranchisedTax = 1 Then";
if (mostCurrent._globalvar._withfranchisedtax /*int*/ ==1) { 
 //BA.debugLineNum = 4145;BA.debugLine="PrintBuffer = PrintBuffer & \"FRANCHISE TAX";
_printbuffer = _printbuffer+"FRANCHISE TAX      "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_franchisetaxamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_franchisetaxamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 };
 //BA.debugLineNum = 4162;BA.debugLine="If iHasSeptage = 1 Then";
if (_ihasseptage==1) { 
 //BA.debugLineNum = 4163;BA.debugLine="PrintBuffer = PrintBuffer & Chr(27) & \"!\" & Chr";
_printbuffer = _printbuffer+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"ARREARS            "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(BA.ObjectToNumber(_sarrearsamt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(BA.ObjectToNumber(_sarrearsamt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"ADVANCES           "+_addwhitespaces("("+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(BA.ObjectToNumber(_sadvpayment)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")")+"("+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(BA.ObjectToNumber(_sadvpayment)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"SUB-TOTAL          "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_totaldueamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_totaldueamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"=========================================="+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"PAYMENT AFTER DUE DATE"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"DUE DATE           "+_addwhitespaces(mostCurrent._duedate)+mostCurrent._duedate+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"PENALTY            "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_penaltyamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_penaltyamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"SR. CITIZEN DISC.  "+_addwhitespaces("("+anywheresoftware.b4a.keywords.Common.NumberFormat2(_seniorafteramt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")")+"("+anywheresoftware.b4a.keywords.Common.NumberFormat2(_seniorafteramt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"SUB-TOTAL AFTER DUE"+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_totaldueamtaftersc,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_totaldueamtaftersc,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"=========================================="+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 }else {
 //BA.debugLineNum = 4176;BA.debugLine="PrintBuffer = PrintBuffer & Chr(27) & \"!\" & Chr";
_printbuffer = _printbuffer+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"ARREARS            "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(BA.ObjectToNumber(_sarrearsamt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(BA.ObjectToNumber(_sarrearsamt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"ADVANCES           "+_addwhitespaces("("+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(BA.ObjectToNumber(_sadvpayment)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")")+"("+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(BA.ObjectToNumber(_sadvpayment)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+"TOTAL DUE          "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_totaldueamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_totaldueamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"=========================================="+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"PAYMENT AFTER DUE DATE"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"DUE DATE           "+_addwhitespaces(mostCurrent._duedate)+mostCurrent._duedate+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"PENALTY            "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_penaltyamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_penaltyamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"SR. CITIZEN DISC.  "+_addwhitespaces("("+anywheresoftware.b4a.keywords.Common.NumberFormat2(_seniorafteramt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")")+"("+anywheresoftware.b4a.keywords.Common.NumberFormat2(_seniorafteramt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+"TOTAL AMT AFTER DUE"+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_totaldueamtaftersc,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_totaldueamtaftersc,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"=========================================="+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 };
 //BA.debugLineNum = 4191;BA.debugLine="If GlobalVar.SF.Len(BillPeriod3rd) > 0 Then";
if (mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(mostCurrent._billperiod3rd)>0) { 
 //BA.debugLineNum = 4192;BA.debugLine="PrintBuffer = PrintBuffer & Chr(27) & Chr(97) &";
_printbuffer = _printbuffer+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"CONSUMPTION HISTORY"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+mostCurrent._billperiod1st+("           ")+_addwhitespaces(BA.NumberToString(_prevcum1st)+(" CuM."))+BA.NumberToString(_prevcum1st)+(" CuM.")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+mostCurrent._billperiod2nd+("           ")+_addwhitespaces(BA.NumberToString(_prevcum2nd)+(" CuM."))+BA.NumberToString(_prevcum2nd)+(" CuM.")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+mostCurrent._billperiod3rd+("           ")+_addwhitespaces(BA.NumberToString(_prevcum3rd)+(" CuM."))+BA.NumberToString(_prevcum3rd)+(" CuM.")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"=========================================="+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 }else if(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(mostCurrent._billperiod2nd)>0) { 
 //BA.debugLineNum = 4200;BA.debugLine="PrintBuffer = PrintBuffer & Chr(27) & Chr(97) &";
_printbuffer = _printbuffer+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"CONSUMPTION HISTORY"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+mostCurrent._billperiod1st+("           ")+_addwhitespaces(BA.NumberToString(_prevcum1st)+(" CuM."))+BA.NumberToString(_prevcum1st)+(" CuM.")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+mostCurrent._billperiod2nd+("           ")+_addwhitespaces(BA.NumberToString(_prevcum2nd)+(" CuM."))+BA.NumberToString(_prevcum2nd)+(" CuM.")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"=========================================="+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 }else if(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(mostCurrent._billperiod1st)>0) { 
 //BA.debugLineNum = 4207;BA.debugLine="PrintBuffer = PrintBuffer & Chr(27) & Chr(97) &";
_printbuffer = _printbuffer+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"CONSUMPTION HISTORY"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+mostCurrent._billperiod1st+("           ")+_addwhitespaces(BA.NumberToString(_prevcum1st)+(" CuM."))+BA.NumberToString(_prevcum1st)+(" CuM.")+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"=========================================="+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 };
 //BA.debugLineNum = 4216;BA.debugLine="If (ArrearsAmt > 0) Or (TotalDueAmt >= GDeposit";
if ((_arrearsamt>0) || (_totaldueamt>=_gdeposit)) { 
 //BA.debugLineNum = 4217;BA.debugLine="PrintBuffer = PrintBuffer & Chr(27) & Chr(97)";
_printbuffer = _printbuffer+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (2)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+"NOTICE OF DISCONNECTION"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"DISCONNECTION DATE:"+_addwhitespaces(mostCurrent._disconnectiondate)+mostCurrent._disconnectiondate+anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 };
 //BA.debugLineNum = 4224;BA.debugLine="PrintBuffer = PrintBuffer & Chr(27) & Chr(97) & C";
_printbuffer = _printbuffer+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (2)))+_reverse+" NOTE: "+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+_unreverse+"    Disregard Arrears if Payment has been made. Please pay on or before the due date to avoid any inconveniences."+anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"Reader: "+mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv5(mostCurrent._globalvar._empname /*String*/ )+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"Date & Time: "+_sreaddate+" "+_sreadtime+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+_fullcut;
 //BA.debugLineNum = 4356;BA.debugLine="If iHasSeptage = 1 Then 'Footer No Septage Fee";
if (_ihasseptage==1) { 
 //BA.debugLineNum = 4359;BA.debugLine="GTotalDueAmt = 0";
_gtotaldueamt = 0;
 //BA.debugLineNum = 4360;BA.debugLine="GTotalDue = 0";
_gtotaldue = 0;
 //BA.debugLineNum = 4361;BA.debugLine="GTotalAfterDue = 0";
_gtotalafterdue = 0;
 //BA.debugLineNum = 4363;BA.debugLine="GTotalDueAmt = (CurrentAmt + AddToBillAmt + Arr";
_gtotaldueamt = (_currentamt+_addtobillamt+_arrearsamt+_metercharges+_franchisetaxamt)-(_senioronbeforeamt+_discamt2);
 //BA.debugLineNum = 4364;BA.debugLine="GTotalAfterDueAmt = (CurrentAmt + AddToBillAmt";
_gtotalafterdueamt = (_currentamt+_addtobillamt+_arrearsamt+_metercharges+_franchisetaxamt+_penaltyamt)-(_seniorafteramt+_discamt2);
 //BA.debugLineNum = 4366;BA.debugLine="LogColor(GTotalDueAmt,Colors.Yellow)";
anywheresoftware.b4a.keywords.Common.LogImpl("127853358",BA.NumberToString(_gtotaldueamt),anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 //BA.debugLineNum = 4368;BA.debugLine="If GlobalVar.BranchID = 28 Or GlobalVar.BranchI";
if (mostCurrent._globalvar._branchid /*int*/ ==28 || mostCurrent._globalvar._branchid /*int*/ ==29 || mostCurrent._globalvar._branchid /*int*/ ==30) { 
 //BA.debugLineNum = 4369;BA.debugLine="GTotalSeptage = SeptageArrears + SeptageFee";
_gtotalseptage = _septagearrears+_septagefee;
 //BA.debugLineNum = 4371;BA.debugLine="If ((GTotalDueAmt + GTotalSeptage) - AdvPaymen";
if (((_gtotaldueamt+_gtotalseptage)-_advpayment)<0) { 
 //BA.debugLineNum = 4372;BA.debugLine="GTotalDue = 0";
_gtotaldue = 0;
 }else {
 //BA.debugLineNum = 4374;BA.debugLine="GTotalDue = (GTotalDueAmt + GTotalSeptage) -";
_gtotaldue = (_gtotaldueamt+_gtotalseptage)-_advpayment;
 };
 //BA.debugLineNum = 4378;BA.debugLine="If AdvPayment > GTotalAfterDueAmt + GTotalSept";
if (_advpayment>_gtotalafterdueamt+_gtotalseptage) { 
 //BA.debugLineNum = 4379;BA.debugLine="GTotalAfterDue = 0";
_gtotalafterdue = 0;
 }else {
 //BA.debugLineNum = 4381;BA.debugLine="GTotalAfterDue = (GTotalAfterDueAmt + GTotalS";
_gtotalafterdue = (_gtotalafterdueamt+_gtotalseptage)-_advpayment;
 };
 }else {
 //BA.debugLineNum = 4384;BA.debugLine="GTotalSeptage = SeptageFee";
_gtotalseptage = _septagefee;
 //BA.debugLineNum = 4386;BA.debugLine="If ((GTotalDueAmt + GTotalSeptage) - AdvPaymen";
if (((_gtotaldueamt+_gtotalseptage)-_advpayment)<0) { 
 //BA.debugLineNum = 4387;BA.debugLine="GTotalDue = 0";
_gtotaldue = 0;
 }else {
 //BA.debugLineNum = 4389;BA.debugLine="GTotalDue = (GTotalDueAmt + GTotalSeptage) -";
_gtotaldue = (_gtotaldueamt+_gtotalseptage)-_advpayment;
 };
 //BA.debugLineNum = 4393;BA.debugLine="If AdvPayment > GTotalAfterDueAmt + GTotalSept";
if (_advpayment>_gtotalafterdueamt+_gtotalseptage) { 
 //BA.debugLineNum = 4394;BA.debugLine="GTotalAfterDue = 0";
_gtotalafterdue = 0;
 }else {
 //BA.debugLineNum = 4396;BA.debugLine="GTotalAfterDue = (GTotalAfterDueAmt + GTotalS";
_gtotalafterdue = (_gtotalafterdueamt+_gtotalseptage)-_advpayment;
 };
 };
 //BA.debugLineNum = 4400;BA.debugLine="LogColor($\"Septage Fee: \"$ & GTotalSeptage,Colo";
anywheresoftware.b4a.keywords.Common.LogImpl("127853392",("Septage Fee: ")+BA.NumberToString(_gtotalseptage),anywheresoftware.b4a.keywords.Common.Colors.Cyan);
 //BA.debugLineNum = 4404;BA.debugLine="If GlobalVar.SeptageProv = 1 Then";
if (mostCurrent._globalvar._septageprov /*int*/ ==1) { 
 //BA.debugLineNum = 4405;BA.debugLine="If GlobalVar.BranchID = 5 Then 'Main Branch On";
if (mostCurrent._globalvar._branchid /*int*/ ==5) { 
 //BA.debugLineNum = 4406;BA.debugLine="sepBuffer = Chr(27) & \"@\" _ 						& Chr(27) &";
_sepbuffer = BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"@"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+"STATEMENT OF ACCOUNT"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"For the Month of "+mostCurrent._sbillperiod+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+"Account No: "+mostCurrent._acctno+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+mostCurrent._strsf._vvvvvvv5(mostCurrent._acctname)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"Class   : "+mostCurrent._custclass+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"TOTAL CONSUMPTION  "+_addwhitespaces(mostCurrent._stotalcum)+mostCurrent._stotalcum+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"ENVIRONMENTAL FEE  "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_septagefee,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_septagefee,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"================================"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (32)))+"S U M M A R Y"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"================================"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"CURRENT BILL       "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_currentamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_currentamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"SR. CITIZEN DISC.  "+_addwhitespaces("("+anywheresoftware.b4a.keywords.Common.NumberFormat2(_senioronbeforeamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")")+"("+anywheresoftware.b4a.keywords.Common.NumberFormat2(_senioronbeforeamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 //BA.debugLineNum = 4426;BA.debugLine="If DiscAmt2 > 0 Then";
if (_discamt2>0) { 
 //BA.debugLineNum = 4427;BA.debugLine="sepBuffer = sepBuffer & Chr(27) & \"!\" & Chr(";
_sepbuffer = _sepbuffer+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"DISCOUNT           "+_addwhitespaces("("+anywheresoftware.b4a.keywords.Common.NumberFormat2(_discamt2,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")")+"("+anywheresoftware.b4a.keywords.Common.NumberFormat2(_discamt2,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 };
 //BA.debugLineNum = 4430;BA.debugLine="sepBuffer = sepBuffer & Chr(27) & \"!\" & Chr(0";
_sepbuffer = _sepbuffer+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"OTHERS             "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(BA.ObjectToNumber(_saddtobillamt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(BA.ObjectToNumber(_saddtobillamt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 //BA.debugLineNum = 4432;BA.debugLine="If GlobalVar.WithMeterCharges = 1 Then";
if (mostCurrent._globalvar._withmetercharges /*int*/ ==1) { 
 //BA.debugLineNum = 4433;BA.debugLine="sepBuffer = sepBuffer & \"METER CHARGES";
_sepbuffer = _sepbuffer+"METER CHARGES      "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_metercharges,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_metercharges,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 };
 //BA.debugLineNum = 4436;BA.debugLine="If GlobalVar.WithFranchisedTax = 1 Then";
if (mostCurrent._globalvar._withfranchisedtax /*int*/ ==1) { 
 //BA.debugLineNum = 4437;BA.debugLine="sepBuffer = sepBuffer & \"FRANCHISE TAX";
_sepbuffer = _sepbuffer+"FRANCHISE TAX      "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_franchisetaxamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_franchisetaxamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 };
 //BA.debugLineNum = 4440;BA.debugLine="sepBuffer = sepBuffer & Chr(27) & \"!\" & Chr(8";
_sepbuffer = _sepbuffer+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"WATER BILL         "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_gtotaldueamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_gtotaldueamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"ENVIRONMENTAL FEE  "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_septagefee,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_septagefee,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"ADVANCES           "+_addwhitespaces("("+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(BA.ObjectToNumber(_sadvpayment)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")")+"("+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(BA.ObjectToNumber(_sadvpayment)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+"TOTAL DUE          "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_gtotaldue,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_gtotaldue,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"AMT. AFTER DUE DATE"+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_gtotalafterdue,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_gtotalafterdue,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"=========================================="+anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 }else {
 //BA.debugLineNum = 4449;BA.debugLine="sepBuffer = Chr(27) & \"@\" _ 						& Chr(27) &";
_sepbuffer = BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"@"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+"STATEMENT OF ACCOUNT"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"For the Month of "+mostCurrent._sbillperiod+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+"Account No: "+mostCurrent._acctno+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+mostCurrent._strsf._vvvvvvv5(mostCurrent._acctname)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"Class   : "+mostCurrent._custclass+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"TOTAL CONSUMPTION  "+_addwhitespaces(mostCurrent._stotalcum)+mostCurrent._stotalcum+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"SEWERAGE AND       "+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"SEPTAGE FEE        "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_septagefee,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_septagefee,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"================================"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (32)))+"S U M M A R Y"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"================================"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"CURRENT BILL       "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_currentamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_currentamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"SR. CITIZEN DISC.  "+_addwhitespaces("("+anywheresoftware.b4a.keywords.Common.NumberFormat2(_senioronbeforeamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")")+"("+anywheresoftware.b4a.keywords.Common.NumberFormat2(_senioronbeforeamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 //BA.debugLineNum = 4470;BA.debugLine="If DiscAmt2 > 0 Then";
if (_discamt2>0) { 
 //BA.debugLineNum = 4471;BA.debugLine="sepBuffer = sepBuffer & Chr(27) & \"!\" & Chr(";
_sepbuffer = _sepbuffer+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"DISCOUNT           "+_addwhitespaces("("+anywheresoftware.b4a.keywords.Common.NumberFormat2(_discamt2,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")")+"("+anywheresoftware.b4a.keywords.Common.NumberFormat2(_discamt2,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 };
 //BA.debugLineNum = 4474;BA.debugLine="sepBuffer = sepBuffer & Chr(27) & \"!\" & Chr(0";
_sepbuffer = _sepbuffer+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"OTHERS             "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(BA.ObjectToNumber(_saddtobillamt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(BA.ObjectToNumber(_saddtobillamt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 //BA.debugLineNum = 4476;BA.debugLine="If GlobalVar.WithMeterCharges = 1 Then";
if (mostCurrent._globalvar._withmetercharges /*int*/ ==1) { 
 //BA.debugLineNum = 4477;BA.debugLine="sepBuffer = sepBuffer & \"METER CHARGES";
_sepbuffer = _sepbuffer+"METER CHARGES      "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_metercharges,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_metercharges,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 };
 //BA.debugLineNum = 4480;BA.debugLine="If GlobalVar.WithFranchisedTax = 1 Then";
if (mostCurrent._globalvar._withfranchisedtax /*int*/ ==1) { 
 //BA.debugLineNum = 4481;BA.debugLine="sepBuffer = sepBuffer & \"FRANCHISE TAX";
_sepbuffer = _sepbuffer+"FRANCHISE TAX      "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_franchisetaxamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_franchisetaxamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 };
 //BA.debugLineNum = 4484;BA.debugLine="sepBuffer = sepBuffer & Chr(27) & \"!\" & Chr(8";
_sepbuffer = _sepbuffer+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"WATER BILL         "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_gtotaldueamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_gtotaldueamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"SEPTAGE AND        "+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"SEWERAGE FEE       "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_septagefee,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_septagefee,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"ADVANCES           "+_addwhitespaces("("+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(BA.ObjectToNumber(_sadvpayment)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")")+"("+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(BA.ObjectToNumber(_sadvpayment)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+"TOTAL DUE          "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_gtotaldue,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_gtotaldue,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"AMT. AFTER DUE DATE"+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_gtotalafterdue,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_gtotalafterdue,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"=========================================="+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 };
 }else if(mostCurrent._globalvar._septageprov /*int*/ ==2) { 
 //BA.debugLineNum = 4493;BA.debugLine="If GlobalVar.BranchID = 9 Then 'Sto Domingo";
if (mostCurrent._globalvar._branchid /*int*/ ==9) { 
 //BA.debugLineNum = 4494;BA.debugLine="sepBuffer = Chr(27) & \"@\" _ 							& Chr(27)";
_sepbuffer = BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"@"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+"STATEMENT OF ACCOUNT"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"For the Month of "+mostCurrent._sbillperiod+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+"Account No: "+mostCurrent._acctno+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+mostCurrent._strsf._vvvvvvv5(mostCurrent._acctname)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"Class   : "+mostCurrent._custclass+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"TOTAL CONSUMPTION  "+_addwhitespaces(mostCurrent._stotalcum)+mostCurrent._stotalcum+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"SEPTAGE FEE        "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_septagefee,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_septagefee,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"================================"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (32)))+"S U M M A R Y"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"================================"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"CURRENT BILL       "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_currentamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_currentamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"SR. CITIZEN DISC.  "+_addwhitespaces("("+anywheresoftware.b4a.keywords.Common.NumberFormat2(_senioronbeforeamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")")+"("+anywheresoftware.b4a.keywords.Common.NumberFormat2(_senioronbeforeamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 //BA.debugLineNum = 4514;BA.debugLine="If DiscAmt2 > 0 Then";
if (_discamt2>0) { 
 //BA.debugLineNum = 4515;BA.debugLine="sepBuffer = sepBuffer & Chr(27) & \"!\" & Chr(";
_sepbuffer = _sepbuffer+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"DISCOUNT           "+_addwhitespaces("("+anywheresoftware.b4a.keywords.Common.NumberFormat2(_discamt2,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")")+"("+anywheresoftware.b4a.keywords.Common.NumberFormat2(_discamt2,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 };
 //BA.debugLineNum = 4518;BA.debugLine="sepBuffer = sepBuffer & Chr(27) & \"!\" & Chr(0";
_sepbuffer = _sepbuffer+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"OTHERS             "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(BA.ObjectToNumber(_saddtobillamt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(BA.ObjectToNumber(_saddtobillamt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 //BA.debugLineNum = 4520;BA.debugLine="If GlobalVar.WithMeterCharges = 1 Then";
if (mostCurrent._globalvar._withmetercharges /*int*/ ==1) { 
 //BA.debugLineNum = 4521;BA.debugLine="sepBuffer = sepBuffer & \"METER CHARGES";
_sepbuffer = _sepbuffer+"METER CHARGES      "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_metercharges,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_metercharges,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 };
 //BA.debugLineNum = 4524;BA.debugLine="If GlobalVar.WithFranchisedTax = 1 Then";
if (mostCurrent._globalvar._withfranchisedtax /*int*/ ==1) { 
 //BA.debugLineNum = 4525;BA.debugLine="sepBuffer = sepBuffer & \"FRANCHISE TAX";
_sepbuffer = _sepbuffer+"FRANCHISE TAX      "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_franchisetaxamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_franchisetaxamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 };
 //BA.debugLineNum = 4528;BA.debugLine="sepBuffer = sepBuffer & Chr(27) & \"!\" & Chr(8";
_sepbuffer = _sepbuffer+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"WATER BILL         "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_gtotaldueamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_gtotaldueamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"SEPTAGE FEE        "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_septagefee,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_septagefee,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"ADVANCES           "+_addwhitespaces("("+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(BA.ObjectToNumber(_sadvpayment)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")")+"("+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(BA.ObjectToNumber(_sadvpayment)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+"TOTAL DUE          "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_gtotaldue,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_gtotaldue,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"AMT. AFTER DUE DATE"+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_gtotalafterdue,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_gtotalafterdue,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"=========================================="+anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 }else {
 //BA.debugLineNum = 4537;BA.debugLine="sepBuffer = Chr(27) & \"@\" _ 							& Chr(27)";
_sepbuffer = BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"@"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+"STATEMENT OF ACCOUNT"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"For the Month of "+mostCurrent._sbillperiod+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+"Account No: "+mostCurrent._acctno+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+mostCurrent._strsf._vvvvvvv5(mostCurrent._acctname)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"Class   : "+mostCurrent._custclass+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"TOTAL CONSUMPTION  "+_addwhitespaces(mostCurrent._stotalcum)+mostCurrent._stotalcum+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"ENVIRONMENTAL FEE  "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_septagefee,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_septagefee,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"================================"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (32)))+"S U M M A R Y"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"================================"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"CURRENT BILL       "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_currentamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_currentamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"SR. CITIZEN DISC.  "+_addwhitespaces("("+anywheresoftware.b4a.keywords.Common.NumberFormat2(_senioronbeforeamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")")+"("+anywheresoftware.b4a.keywords.Common.NumberFormat2(_senioronbeforeamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 //BA.debugLineNum = 4557;BA.debugLine="If DiscAmt2 > 0 Then";
if (_discamt2>0) { 
 //BA.debugLineNum = 4558;BA.debugLine="sepBuffer = sepBuffer & Chr(27) & \"!\" & Chr(";
_sepbuffer = _sepbuffer+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"DISCOUNT           "+_addwhitespaces("("+anywheresoftware.b4a.keywords.Common.NumberFormat2(_discamt2,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")")+"("+anywheresoftware.b4a.keywords.Common.NumberFormat2(_discamt2,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 };
 //BA.debugLineNum = 4561;BA.debugLine="sepBuffer = sepBuffer & Chr(27) & \"!\" & Chr(0";
_sepbuffer = _sepbuffer+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"OTHERS             "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(BA.ObjectToNumber(_saddtobillamt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(BA.ObjectToNumber(_saddtobillamt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 //BA.debugLineNum = 4563;BA.debugLine="If GlobalVar.WithMeterCharges = 1 Then";
if (mostCurrent._globalvar._withmetercharges /*int*/ ==1) { 
 //BA.debugLineNum = 4564;BA.debugLine="sepBuffer = sepBuffer & \"METER CHARGES";
_sepbuffer = _sepbuffer+"METER CHARGES      "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_metercharges,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_metercharges,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 };
 //BA.debugLineNum = 4567;BA.debugLine="If GlobalVar.WithFranchisedTax = 1 Then";
if (mostCurrent._globalvar._withfranchisedtax /*int*/ ==1) { 
 //BA.debugLineNum = 4568;BA.debugLine="sepBuffer = sepBuffer & \"FRANCHISE TAX";
_sepbuffer = _sepbuffer+"FRANCHISE TAX      "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_franchisetaxamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_franchisetaxamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 };
 //BA.debugLineNum = 4571;BA.debugLine="sepBuffer = sepBuffer & Chr(27) & \"!\" & Chr(8";
_sepbuffer = _sepbuffer+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"WATER BILL         "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_gtotaldueamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_gtotaldueamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"ENVIRONMENTAL FEE  "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_septagefee,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_septagefee,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"ADVANCES           "+_addwhitespaces("("+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(BA.ObjectToNumber(_sadvpayment)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")")+"("+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(BA.ObjectToNumber(_sadvpayment)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+"TOTAL DUE          "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_gtotaldue,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_gtotaldue,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"AMT. AFTER DUE DATE"+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_gtotalafterdue,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_gtotalafterdue,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"=========================================="+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 };
 }else {
 //BA.debugLineNum = 4579;BA.debugLine="sepBuffer = Chr(27) & \"@\" _ 			& Chr(27) & Chr(";
_sepbuffer = BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"@"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (33)))+"STATEMENT OF ACCOUNT"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"For the Month of "+mostCurrent._sbillperiod+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+"Account No: "+mostCurrent._acctno+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"t"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+mostCurrent._strsf._vvvvvvv5(mostCurrent._acctname)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"Class   : "+mostCurrent._custclass+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"------------------------------------------"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"TOTAL CONSUMPTION  "+_addwhitespaces(mostCurrent._stotalcum)+mostCurrent._stotalcum+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"ENVIRONMENTAL FEE  "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_septagefee,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_septagefee,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (49)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"================================"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (32)))+"S U M M A R Y"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"================================"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (97)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (48)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"CURRENT BILL       "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_currentamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_currentamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"SR. CITIZEN DISC.  "+_addwhitespaces("("+anywheresoftware.b4a.keywords.Common.NumberFormat2(_senioronbeforeamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")")+"("+anywheresoftware.b4a.keywords.Common.NumberFormat2(_senioronbeforeamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 //BA.debugLineNum = 4599;BA.debugLine="If DiscAmt2 > 0 Then";
if (_discamt2>0) { 
 //BA.debugLineNum = 4600;BA.debugLine="sepBuffer = sepBuffer & Chr(27) & \"!\" & Chr(0)";
_sepbuffer = _sepbuffer+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"DISCOUNT           "+_addwhitespaces("("+anywheresoftware.b4a.keywords.Common.NumberFormat2(_discamt2,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")")+"("+anywheresoftware.b4a.keywords.Common.NumberFormat2(_discamt2,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 };
 //BA.debugLineNum = 4603;BA.debugLine="sepBuffer = sepBuffer & Chr(27) & \"!\" & Chr(0)";
_sepbuffer = _sepbuffer+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"OTHERS             "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(BA.ObjectToNumber(_saddtobillamt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(BA.ObjectToNumber(_saddtobillamt)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 //BA.debugLineNum = 4605;BA.debugLine="If GlobalVar.WithMeterCharges = 1 Then";
if (mostCurrent._globalvar._withmetercharges /*int*/ ==1) { 
 //BA.debugLineNum = 4606;BA.debugLine="sepBuffer = sepBuffer & \"METER CHARGES      \"";
_sepbuffer = _sepbuffer+"METER CHARGES      "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_metercharges,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_metercharges,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 };
 //BA.debugLineNum = 4609;BA.debugLine="If GlobalVar.WithFranchisedTax = 1 Then";
if (mostCurrent._globalvar._withfranchisedtax /*int*/ ==1) { 
 //BA.debugLineNum = 4610;BA.debugLine="sepBuffer = sepBuffer & \"FRANCHISE TAX      \"";
_sepbuffer = _sepbuffer+"FRANCHISE TAX      "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_franchisetaxamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_franchisetaxamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)));
 };
 //BA.debugLineNum = 4613;BA.debugLine="sepBuffer = sepBuffer & Chr(27) & \"!\" & Chr(8)";
_sepbuffer = _sepbuffer+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"WATER BILL         "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_gtotaldueamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_gtotaldueamt,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"ENVIRONMENTAL FEE  "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_septagefee,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_septagefee,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"ARREARS            "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_septagearrears,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_septagearrears,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0)))+"ADVANCES           "+_addwhitespaces("("+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(BA.ObjectToNumber(_sadvpayment)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")")+"("+anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(BA.ObjectToNumber(_sadvpayment)),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+")"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+"TOTAL ENVIRONMENTAL"+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_gtotalseptage,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_gtotalseptage,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (16)))+"TOTAL DUE          "+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_gtotaldue,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_gtotaldue,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (8)))+"AMT. AFTER DUE DATE"+_addwhitespaces(anywheresoftware.b4a.keywords.Common.NumberFormat2(_gtotalafterdue,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True))+anywheresoftware.b4a.keywords.Common.NumberFormat2(_gtotalafterdue,(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.True)+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (27)))+"!"+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (1)))+"=========================================="+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))+_fullcut;
 };
 };
 //BA.debugLineNum = 4655;BA.debugLine="StartPrinter";
_startprinter();
 //BA.debugLineNum = 4670;BA.debugLine="End Sub";
return "";
}
public static void  _printer_connected(boolean _success) throws Exception{
ResumableSub_Printer_Connected rsub = new ResumableSub_Printer_Connected(null,_success);
rsub.resume(processBA, null);
}
public static class ResumableSub_Printer_Connected extends BA.ResumableSub {
public ResumableSub_Printer_Connected(com.bwsi.MeterReader.meterreading parent,boolean _success) {
this.parent = parent;
this._success = _success;
}
com.bwsi.MeterReader.meterreading parent;
boolean _success;
Object _iresponse = null;
anywheresoftware.b4a.objects.CSBuilder _csmsg = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 4805;BA.debugLine="Dim iResponse As Object";
_iresponse = new Object();
 //BA.debugLineNum = 4806;BA.debugLine="Dim csMsg As CSBuilder";
_csmsg = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 4807;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 4808;BA.debugLine="Log(\"Connected: \" & Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("128311556","Connected: "+BA.ObjectToString(_success),0);
 //BA.debugLineNum = 4810;BA.debugLine="If Success = False Then";
if (true) break;

case 1:
//if
this.state = 35;
if (_success==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 3;
}else {
this.state = 9;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 4811;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 4812;BA.debugLine="If Not(ConfirmWarning($\"Unable to Connect to Pri";
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
 //BA.debugLineNum = 4813;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 7:
//C
this.state = 35;
;
 //BA.debugLineNum = 4815;BA.debugLine="StartPrinter";
_startprinter();
 if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 4819;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 4820;BA.debugLine="TMPrinter.Initialize2(Serial1.OutputStream, \"win";
parent._tmprinter.Initialize2(parent._serial1.getOutputStream(),"windows-1252");
 //BA.debugLineNum = 4821;BA.debugLine="oStream.Initialize(Serial1.InputStream, Serial1.";
parent._ostream.Initialize(processBA,parent._serial1.getInputStream(),parent._serial1.getOutputStream(),"LogoPrint");
 //BA.debugLineNum = 4822;BA.debugLine="Logo.Initialize(File.DirAssets, GlobalVar.Branch";
parent._logo.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),parent.mostCurrent._globalvar._branchlogo /*String*/ );
 //BA.debugLineNum = 4823;BA.debugLine="Logobmp = CreateScaledBitmap(Logo, Logo.Width, L";
parent._logobmp = _createscaledbitmap(parent._logo,parent._logo.getWidth(),parent._logo.getHeight());
 //BA.debugLineNum = 4824;BA.debugLine="Log(DeviceName)";
anywheresoftware.b4a.keywords.Common.LogImpl("128311572",parent._devicename,0);
 //BA.debugLineNum = 4826;BA.debugLine="If strSF.Upper(DeviceName) = \"WOOSIM\" Then";
if (true) break;

case 10:
//if
this.state = 26;
if ((parent.mostCurrent._strsf._vvvvvvv5(parent._devicename)).equals("WOOSIM")) { 
this.state = 12;
}else {
this.state = 25;
}if (true) break;

case 12:
//C
this.state = 13;
 //BA.debugLineNum = 4827;BA.debugLine="WoosimCmd.InitializeStatic(\"com.woosim.printer.";
parent._woosimcmd.InitializeStatic("com.woosim.printer.WoosimCmd");
 //BA.debugLineNum = 4828;BA.debugLine="WoosimImage.InitializeStatic(\"com.woosim.printe";
parent._woosimimage.InitializeStatic("com.woosim.printer.WoosimImage");
 //BA.debugLineNum = 4830;BA.debugLine="initPrinter = WoosimCmd.RunMethod(\"initPrinter\"";
parent._initprinter = (byte[])(parent._woosimcmd.RunMethod("initPrinter",(Object[])(anywheresoftware.b4a.keywords.Common.Null)));
 //BA.debugLineNum = 4831;BA.debugLine="If GlobalVar.BranchID = 28 Or GlobalVar.BranchI";
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
 //BA.debugLineNum = 4832;BA.debugLine="PrintLogo = WoosimImage.RunMethod(\"printBitmap";
parent._printlogo = (byte[])(parent._woosimimage.RunMethod("printBitmap",new Object[]{(Object)(0),(Object)(0),(Object)(400),(Object)(200),(Object)(parent._logobmp.getObject())}));
 if (true) break;

case 17:
//C
this.state = 18;
 //BA.debugLineNum = 4834;BA.debugLine="PrintLogo = WoosimImage.RunMethod(\"printBitmap";
parent._printlogo = (byte[])(parent._woosimimage.RunMethod("printBitmap",new Object[]{(Object)(0),(Object)(0),(Object)(430),(Object)(150),(Object)(parent._logobmp.getObject())}));
 if (true) break;
;
 //BA.debugLineNum = 4836;BA.debugLine="If GlobalVar.BranchID = 76 Then";

case 18:
//if
this.state = 23;
if (parent.mostCurrent._globalvar._branchid /*int*/ ==76) { 
this.state = 20;
}else {
this.state = 22;
}if (true) break;

case 20:
//C
this.state = 23;
 if (true) break;

case 22:
//C
this.state = 23;
 //BA.debugLineNum = 4838;BA.debugLine="oStream.Write(initPrinter)";
parent._ostream.Write(parent._initprinter);
 //BA.debugLineNum = 4839;BA.debugLine="oStream.Write(WoosimCmd.RunMethod(\"setPageMode";
parent._ostream.Write((byte[])(parent._woosimcmd.RunMethod("setPageMode",(Object[])(anywheresoftware.b4a.keywords.Common.Null))));
 //BA.debugLineNum = 4840;BA.debugLine="oStream.Write(PrintLogo)";
parent._ostream.Write(parent._printlogo);
 //BA.debugLineNum = 4841;BA.debugLine="oStream.Write(WoosimCmd.RunMethod(\"PM_setStdMo";
parent._ostream.Write((byte[])(parent._woosimcmd.RunMethod("PM_setStdMode",(Object[])(anywheresoftware.b4a.keywords.Common.Null))));
 if (true) break;

case 23:
//C
this.state = 26;
;
 if (true) break;

case 25:
//C
this.state = 26;
 //BA.debugLineNum = 4848;BA.debugLine="EPSONPrint.Initialize";
parent._epsonprint._initialize /*String*/ (processBA);
 //BA.debugLineNum = 4849;BA.debugLine="PrintLogo =  EPSONPrint.getBytesForBitmap2(Glob";
parent._printlogo = parent._epsonprint._getbytesforbitmap2 /*byte[]*/ (parent.mostCurrent._globalvar._branchlogo /*String*/ ,anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 4850;BA.debugLine="oStream.Write(PrintLogo)";
parent._ostream.Write(parent._printlogo);
 if (true) break;

case 26:
//C
this.state = 27;
;
 //BA.debugLineNum = 4853;BA.debugLine="Sleep(500)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (500));
this.state = 36;
return;
case 36:
//C
this.state = 27;
;
 //BA.debugLineNum = 4854;BA.debugLine="TMPrinter.WriteLine(PrintBuffer)";
parent._tmprinter.WriteLine(parent._printbuffer);
 //BA.debugLineNum = 4855;BA.debugLine="Log(PrintBuffer)";
anywheresoftware.b4a.keywords.Common.LogImpl("128311603",parent._printbuffer,0);
 //BA.debugLineNum = 4856;BA.debugLine="TMPrinter.Flush";
parent._tmprinter.Flush();
 //BA.debugLineNum = 4857;BA.debugLine="Sleep(300)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (300));
this.state = 37;
return;
case 37:
//C
this.state = 27;
;
 //BA.debugLineNum = 4864;BA.debugLine="If GlobalVar.SeptageProv > 0 Then";
if (true) break;

case 27:
//if
this.state = 34;
if (parent.mostCurrent._globalvar._septageprov /*int*/ >0) { 
this.state = 29;
}if (true) break;

case 29:
//C
this.state = 30;
 //BA.debugLineNum = 4865;BA.debugLine="If iHasSeptage = 1 Then";
if (true) break;

case 30:
//if
this.state = 33;
if (parent._ihasseptage==1) { 
this.state = 32;
}if (true) break;

case 32:
//C
this.state = 33;
 //BA.debugLineNum = 4866;BA.debugLine="Sleep(100)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (100));
this.state = 38;
return;
case 38:
//C
this.state = 33;
;
 //BA.debugLineNum = 4868;BA.debugLine="oStream2.Initialize(Serial1.InputStream, Seria";
parent._ostream2.Initialize(processBA,parent._serial1.getInputStream(),parent._serial1.getOutputStream(),"SeptageLogo");
 //BA.debugLineNum = 4869;BA.debugLine="SeptageLogo.Initialize(File.DirAssets, GlobalV";
parent._septagelogo.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),parent.mostCurrent._globalvar._septagelogo /*String*/ );
 //BA.debugLineNum = 4870;BA.debugLine="Logobmp = CreateScaledBitmap(SeptageLogo, Sept";
parent._logobmp = _createscaledbitmap(parent._septagelogo,parent._septagelogo.getWidth(),parent._septagelogo.getHeight());
 //BA.debugLineNum = 4871;BA.debugLine="WoosimCmd.InitializeStatic(\"com.woosim.printer";
parent._woosimcmd.InitializeStatic("com.woosim.printer.WoosimCmd");
 //BA.debugLineNum = 4872;BA.debugLine="WoosimImage.InitializeStatic(\"com.woosim.print";
parent._woosimimage.InitializeStatic("com.woosim.printer.WoosimImage");
 //BA.debugLineNum = 4874;BA.debugLine="initPrinter = WoosimCmd.RunMethod(\"initPrinter";
parent._initprinter = (byte[])(parent._woosimcmd.RunMethod("initPrinter",(Object[])(anywheresoftware.b4a.keywords.Common.Null)));
 //BA.debugLineNum = 4875;BA.debugLine="PrintSeptageLogo = WoosimImage.RunMethod(\"prin";
parent._printseptagelogo = (byte[])(parent._woosimimage.RunMethod("printBitmap",new Object[]{(Object)(0),(Object)(0),(Object)(0),(Object)(170),(Object)(parent._logobmp.getObject())}));
 //BA.debugLineNum = 4876;BA.debugLine="oStream2.Write(initPrinter)";
parent._ostream2.Write(parent._initprinter);
 //BA.debugLineNum = 4877;BA.debugLine="oStream2.Write(WoosimCmd.RunMethod(\"setPageMod";
parent._ostream2.Write((byte[])(parent._woosimcmd.RunMethod("setPageMode",(Object[])(anywheresoftware.b4a.keywords.Common.Null))));
 //BA.debugLineNum = 4878;BA.debugLine="oStream2.Write(PrintSeptageLogo)";
parent._ostream2.Write(parent._printseptagelogo);
 //BA.debugLineNum = 4879;BA.debugLine="oStream2.Write(WoosimCmd.RunMethod(\"PM_setStdM";
parent._ostream2.Write((byte[])(parent._woosimcmd.RunMethod("PM_setStdMode",(Object[])(anywheresoftware.b4a.keywords.Common.Null))));
 //BA.debugLineNum = 4880;BA.debugLine="Sleep(20)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (20));
this.state = 39;
return;
case 39:
//C
this.state = 33;
;
 //BA.debugLineNum = 4881;BA.debugLine="TMPrinter.WriteLine(sepBuffer)";
parent._tmprinter.WriteLine(parent._sepbuffer);
 if (true) break;

case 33:
//C
this.state = 34;
;
 if (true) break;

case 34:
//C
this.state = 35;
;
 //BA.debugLineNum = 4907;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 4908;BA.debugLine="TMPrinter.Flush";
parent._tmprinter.Flush();
 //BA.debugLineNum = 4909;BA.debugLine="DispInfoMsg($\"Bill Statement successfully printe";
_dispinfomsg(("Bill Statement successfully printed.")+("Tap OK to Continue..."),("Bill Printing"));
 //BA.debugLineNum = 4910;BA.debugLine="TMPrinter.Close";
parent._tmprinter.Close();
 //BA.debugLineNum = 4911;BA.debugLine="Serial1.Disconnect";
parent._serial1.Disconnect();
 if (true) break;

case 35:
//C
this.state = -1;
;
 //BA.debugLineNum = 4913;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _printqrcode(int _ireadid) throws Exception{
 //BA.debugLineNum = 4733;BA.debugLine="Private Sub PrintQRCode(iReadID As Int)";
 //BA.debugLineNum = 4744;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 20;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 21;BA.debugLine="Dim index As Object";
_index = new Object();
 //BA.debugLineNum = 22;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 23;BA.debugLine="Dim EPSONPrint As clsPrint";
_epsonprint = new com.bwsi.MeterReader.clsprint();
 //BA.debugLineNum = 25;BA.debugLine="Dim BTAdmin As BluetoothAdmin";
_btadmin = new anywheresoftware.b4a.objects.Serial.BluetoothAdmin();
 //BA.debugLineNum = 26;BA.debugLine="Dim PairedDevices As Map";
_paireddevices = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 28;BA.debugLine="Dim FoundDevices As List";
_founddevices = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 29;BA.debugLine="Dim DeviceName, DeviceMac As String";
_devicename = "";
_devicemac = "";
 //BA.debugLineNum = 31;BA.debugLine="Dim Serial1 As Serial";
_serial1 = new anywheresoftware.b4a.objects.Serial();
 //BA.debugLineNum = 32;BA.debugLine="Dim TMPrinter As TextWriter";
_tmprinter = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Dim PrintBuffer As String";
_printbuffer = "";
 //BA.debugLineNum = 35;BA.debugLine="Dim sepBuffer As String";
_sepbuffer = "";
 //BA.debugLineNum = 36;BA.debugLine="Dim QRBuffer As String";
_qrbuffer = "";
 //BA.debugLineNum = 37;BA.debugLine="Dim GCashRefNo As String";
_gcashrefno = "";
 //BA.debugLineNum = 39;BA.debugLine="Dim BarCode() As Byte";
_barcode = new byte[(int) (0)];
;
 //BA.debugLineNum = 40;BA.debugLine="Dim byte3 As Byte = 0x4d";
_byte3 = (byte) (0x4d);
 //BA.debugLineNum = 41;BA.debugLine="Dim QRCodeNew() As Byte";
_qrcodenew = new byte[(int) (0)];
;
 //BA.debugLineNum = 43;BA.debugLine="Dim PrintLogo() As Byte";
_printlogo = new byte[(int) (0)];
;
 //BA.debugLineNum = 44;BA.debugLine="Dim PrintSeptageLogo() As Byte";
_printseptagelogo = new byte[(int) (0)];
;
 //BA.debugLineNum = 45;BA.debugLine="Dim oStream As AsyncStreams";
_ostream = new anywheresoftware.b4a.randomaccessfile.AsyncStreams();
 //BA.debugLineNum = 46;BA.debugLine="Dim oStream2 As AsyncStreams";
_ostream2 = new anywheresoftware.b4a.randomaccessfile.AsyncStreams();
 //BA.debugLineNum = 47;BA.debugLine="Private Res As Int";
_res = 0;
 //BA.debugLineNum = 49;BA.debugLine="Private flagBitmap As Bitmap";
_flagbitmap = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 51;BA.debugLine="Dim Logobmp As Bitmap";
_logobmp = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 52;BA.debugLine="Dim WoosimCmd As JavaObject";
_woosimcmd = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 53;BA.debugLine="Dim WoosimImage As JavaObject";
_woosimimage = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 54;BA.debugLine="Dim WoosimBarcode As JavaObject";
_woosimbarcode = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 56;BA.debugLine="Dim Logo As Bitmap";
_logo = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 57;BA.debugLine="Dim SeptageLogo As Bitmap";
_septagelogo = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 58;BA.debugLine="Public Permissions As RuntimePermissions";
_permissions = new anywheresoftware.b4a.objects.RuntimePermissions();
 //BA.debugLineNum = 59;BA.debugLine="Dim soundsAlarmChannel As SoundPool";
_soundsalarmchannel = new anywheresoftware.b4a.audio.SoundPoolWrapper();
 //BA.debugLineNum = 60;BA.debugLine="Private InpTyp As SLInpTypeConst";
_inptyp = new com.bwsi.MeterReader.slinptypeconst();
 //BA.debugLineNum = 62;BA.debugLine="Private TTS1 As TTS";
_tts1 = new anywheresoftware.b4a.obejcts.TTS();
 //BA.debugLineNum = 63;BA.debugLine="Dim TYPE_TEXT_FLAG_NO_SUGGESTIONS  As Int = 0x800";
_type_text_flag_no_suggestions = (int) (0x80000);
 //BA.debugLineNum = 65;BA.debugLine="End Sub";
return "";
}
public static String  _repeatchar(String _schar,int _noofrepetition) throws Exception{
String _totspaces = "";
int _i = 0;
 //BA.debugLineNum = 4672;BA.debugLine="Private Sub RepeatChar(sChar As String, NoOfRepeti";
 //BA.debugLineNum = 4673;BA.debugLine="Dim Totspaces As String";
_totspaces = "";
 //BA.debugLineNum = 4675;BA.debugLine="For i = 1 To NoOfRepetition";
{
final int step2 = 1;
final int limit2 = _noofrepetition;
_i = (int) (1) ;
for (;_i <= limit2 ;_i = _i + step2 ) {
 //BA.debugLineNum = 4676;BA.debugLine="Totspaces = Totspaces & sChar";
_totspaces = _totspaces+_schar;
 }
};
 //BA.debugLineNum = 4679;BA.debugLine="Return Totspaces";
if (true) return _totspaces;
 //BA.debugLineNum = 4680;BA.debugLine="End Sub";
return "";
}
public static boolean  _retrieveall(int _ibookid,String _sorderby) throws Exception{
boolean _blnret = false;
 //BA.debugLineNum = 1954;BA.debugLine="Sub RetrieveAll(iBookID As Int, sOrderBy As String";
 //BA.debugLineNum = 1955;BA.debugLine="Dim blnRet As Boolean";
_blnret = false;
 //BA.debugLineNum = 1956;BA.debugLine="Try";
try { //BA.debugLineNum = 1957;BA.debugLine="Starter.strCriteria=\"SELECT * FROM tblReadings \"";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblReadings "+"WHERE BookID="+BA.NumberToString(_ibookid)+" AND BillYear= "+BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ )+" AND BillMonth="+BA.NumberToString(mostCurrent._globalvar._billmonth /*int*/ )+"  ORDER BY "+_sorderby;
 //BA.debugLineNum = 1959;BA.debugLine="Log(Starter.strCriteria)";
anywheresoftware.b4a.keywords.Common.LogImpl("124051717",mostCurrent._starter._strcriteria /*String*/ ,0);
 //BA.debugLineNum = 1960;BA.debugLine="rsAllAcct=Starter.DBCon.ExecQuery(Starter.strCri";
mostCurrent._rsallacct = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 1961;BA.debugLine="If rsAllAcct.RowCount>0 Then";
if (mostCurrent._rsallacct.getRowCount()>0) { 
 //BA.debugLineNum = 1962;BA.debugLine="lblSearchRecCount.Text = rsAllAcct.RowCount & $";
mostCurrent._lblsearchreccount.setText(BA.ObjectToCharSequence(BA.NumberToString(mostCurrent._rsallacct.getRowCount())+(" Record(s) found...")));
 //BA.debugLineNum = 1963;BA.debugLine="blnRet= True";
_blnret = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 1965;BA.debugLine="lblSearchRecCount.Text = $\"No Record found...\"$";
mostCurrent._lblsearchreccount.setText(BA.ObjectToCharSequence(("No Record found...")));
 //BA.debugLineNum = 1966;BA.debugLine="blnRet=False";
_blnret = anywheresoftware.b4a.keywords.Common.False;
 };
 } 
       catch (Exception e14) {
			processBA.setLastException(e14); //BA.debugLineNum = 1969;BA.debugLine="blnRet=False";
_blnret = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 1970;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("124051728",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 //BA.debugLineNum = 1971;BA.debugLine="ToastMessageShow(\"Unable to retreive records due";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Unable to retreive records due to "+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage()+"!"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1973;BA.debugLine="Return blnRet";
if (true) return _blnret;
 //BA.debugLineNum = 1974;BA.debugLine="End Sub";
return false;
}
public static boolean  _retrieveread(int _ibookid,String _sorderby) throws Exception{
boolean _blnret = false;
 //BA.debugLineNum = 1976;BA.debugLine="Sub RetrieveRead(iBookID As Int, sOrderBy As Strin";
 //BA.debugLineNum = 1977;BA.debugLine="Dim blnRet As Boolean";
_blnret = false;
 //BA.debugLineNum = 1978;BA.debugLine="Try";
try { //BA.debugLineNum = 1979;BA.debugLine="Starter.strCriteria=\"SELECT * FROM tblReadings \"";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblReadings "+"WHERE WasRead = 1 AND BookID="+BA.NumberToString(_ibookid)+" AND BillYear= "+BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ )+" AND BillMonth="+BA.NumberToString(mostCurrent._globalvar._billmonth /*int*/ )+" ORDER BY "+_sorderby;
 //BA.debugLineNum = 1981;BA.debugLine="Log(Starter.strCriteria)";
anywheresoftware.b4a.keywords.Common.LogImpl("124117253",mostCurrent._starter._strcriteria /*String*/ ,0);
 //BA.debugLineNum = 1982;BA.debugLine="rsReadAcct = Starter.DBCon.ExecQuery(Starter.str";
mostCurrent._rsreadacct = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 1983;BA.debugLine="If rsReadAcct.RowCount>0 Then";
if (mostCurrent._rsreadacct.getRowCount()>0) { 
 //BA.debugLineNum = 1984;BA.debugLine="lblSearchRecCount.Text = rsReadAcct.RowCount &";
mostCurrent._lblsearchreccount.setText(BA.ObjectToCharSequence(BA.NumberToString(mostCurrent._rsreadacct.getRowCount())+(" Record(s) found...")));
 //BA.debugLineNum = 1985;BA.debugLine="blnRet= True";
_blnret = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 1987;BA.debugLine="lblSearchRecCount.Text = $\"No Record found...\"$";
mostCurrent._lblsearchreccount.setText(BA.ObjectToCharSequence(("No Record found...")));
 //BA.debugLineNum = 1988;BA.debugLine="blnRet=False";
_blnret = anywheresoftware.b4a.keywords.Common.False;
 };
 } 
       catch (Exception e14) {
			processBA.setLastException(e14); //BA.debugLineNum = 1991;BA.debugLine="blnRet=False";
_blnret = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 1992;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("124117264",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 //BA.debugLineNum = 1993;BA.debugLine="ToastMessageShow(\"Unable to retreive records due";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Unable to retreive records due to "+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage()+"!"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1995;BA.debugLine="Return blnRet";
if (true) return _blnret;
 //BA.debugLineNum = 1996;BA.debugLine="End Sub";
return false;
}
public static boolean  _retrieverecords(int _ibookid) throws Exception{
boolean _blnret = false;
 //BA.debugLineNum = 1263;BA.debugLine="Sub RetrieveRecords(iBookID As Int) As Boolean";
 //BA.debugLineNum = 1264;BA.debugLine="Dim blnRet As Boolean";
_blnret = false;
 //BA.debugLineNum = 1265;BA.debugLine="Try";
try { //BA.debugLineNum = 1266;BA.debugLine="Starter.strCriteria=\"SELECT * FROM tblReadings \"";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblReadings "+"WHERE BookID = "+BA.NumberToString(_ibookid)+" "+"AND BillYear = "+BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ )+" "+"AND BillMonth = "+BA.NumberToString(mostCurrent._globalvar._billmonth /*int*/ )+" "+"AND ReadBy = "+BA.NumberToString(mostCurrent._globalvar._userid /*int*/ )+" "+"AND BranchID = "+BA.NumberToString(mostCurrent._globalvar._branchid /*int*/ )+" "+"ORDER BY (Case WHEN NewSeqNo Is Null Then SeqNo Else NewSeqNo End), SeqNo Asc, TimeRead Asc, DateRead Asc, AcctID";
 //BA.debugLineNum = 1273;BA.debugLine="Log(Starter.strCriteria)";
anywheresoftware.b4a.keywords.Common.LogImpl("122478858",mostCurrent._starter._strcriteria /*String*/ ,0);
 //BA.debugLineNum = 1275;BA.debugLine="rsLoadedBook = Starter.DBCon.ExecQuery(Starter.s";
mostCurrent._rsloadedbook = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 1277;BA.debugLine="If rsLoadedBook.RowCount > 0 Then";
if (mostCurrent._rsloadedbook.getRowCount()>0) { 
 //BA.debugLineNum = 1278;BA.debugLine="RecCount = rsLoadedBook.RowCount";
_reccount = mostCurrent._rsloadedbook.getRowCount();
 //BA.debugLineNum = 1279;BA.debugLine="blnRet = True";
_blnret = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 1281;BA.debugLine="blnRet = False";
_blnret = anywheresoftware.b4a.keywords.Common.False;
 };
 } 
       catch (Exception e13) {
			processBA.setLastException(e13); //BA.debugLineNum = 1284;BA.debugLine="blnRet = False";
_blnret = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 1285;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("122478870",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 //BA.debugLineNum = 1286;BA.debugLine="ToastMessageShow(\"Unable to retreive records due";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Unable to retreive records due to "+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage()+"!"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1288;BA.debugLine="Return blnRet";
if (true) return _blnret;
 //BA.debugLineNum = 1289;BA.debugLine="End Sub";
return false;
}
public static boolean  _retrieveunread(int _ibookid,String _sorderby) throws Exception{
boolean _blnret = false;
 //BA.debugLineNum = 1932;BA.debugLine="Sub RetrieveUnread(iBookID As Int, sOrderBy As Str";
 //BA.debugLineNum = 1933;BA.debugLine="Dim blnRet As Boolean";
_blnret = false;
 //BA.debugLineNum = 1934;BA.debugLine="Try";
try { //BA.debugLineNum = 1935;BA.debugLine="Starter.strCriteria=\"SELECT * FROM tblReadings \"";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblReadings "+"WHERE WasRead = 0 AND BookID="+BA.NumberToString(_ibookid)+" AND BillYear= "+BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ )+" AND BillMonth="+BA.NumberToString(mostCurrent._globalvar._billmonth /*int*/ )+"  ORDER BY "+_sorderby;
 //BA.debugLineNum = 1937;BA.debugLine="Log(Starter.strCriteria)";
anywheresoftware.b4a.keywords.Common.LogImpl("123986181",mostCurrent._starter._strcriteria /*String*/ ,0);
 //BA.debugLineNum = 1938;BA.debugLine="rsUnReadAcct=Starter.DBCon.ExecQuery(Starter.str";
mostCurrent._rsunreadacct = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 1939;BA.debugLine="If rsUnReadAcct.RowCount>0 Then";
if (mostCurrent._rsunreadacct.getRowCount()>0) { 
 //BA.debugLineNum = 1940;BA.debugLine="lblSearchRecCount.Text = rsUnReadAcct.RowCount";
mostCurrent._lblsearchreccount.setText(BA.ObjectToCharSequence(BA.NumberToString(mostCurrent._rsunreadacct.getRowCount())+(" Record(s) found...")));
 //BA.debugLineNum = 1941;BA.debugLine="blnRet= True";
_blnret = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 1943;BA.debugLine="lblSearchRecCount.Text = $\"No Record found...\"$";
mostCurrent._lblsearchreccount.setText(BA.ObjectToCharSequence(("No Record found...")));
 //BA.debugLineNum = 1944;BA.debugLine="blnRet=False";
_blnret = anywheresoftware.b4a.keywords.Common.False;
 };
 } 
       catch (Exception e14) {
			processBA.setLastException(e14); //BA.debugLineNum = 1947;BA.debugLine="blnRet=False";
_blnret = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 1948;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("123986192",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 //BA.debugLineNum = 1949;BA.debugLine="ToastMessageShow(\"Unable to retreive records due";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Unable to retreive records due to "+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage()+"!"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1951;BA.debugLine="Return blnRet";
if (true) return _blnret;
 //BA.debugLineNum = 1952;BA.debugLine="End Sub";
return false;
}
public static anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper  _roundtocurrency(anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper _bd,int _dp) throws Exception{
 //BA.debugLineNum = 5716;BA.debugLine="Sub RoundtoCurrency(BD As BigDecimal, DP As Int) A";
 //BA.debugLineNum = 5717;BA.debugLine="BD.Round(BD.Precision - BD.Scale + DP, BD.ROUND_H";
_bd.Round((int) (_bd.Precision()-_bd.Scale()+_dp),_bd.ROUND_HALF_UP);
 //BA.debugLineNum = 5718;BA.debugLine="Return BD";
if (true) return _bd;
 //BA.debugLineNum = 5719;BA.debugLine="End Sub";
return null;
}
public static String  _saveaveragebill(int _iprintstatus,int _ireadid,int _iacctid) throws Exception{
long _lngdatetime = 0L;
String _swarning = "";
int _minimumrangeto = 0;
String _savecum = "";
anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper _bbasicamt = null;
anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper _bpcaamt = null;
anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper _bcurrentamt = null;
anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper _bsenioronbeforeamt = null;
anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper _btotaldueamtbeforesc = null;
anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper _bdiscamt = null;
anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper _bmetercharges = null;
anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper _bfranchisetaxamt = null;
anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper _bseptagefee = null;
anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper _bseptagefeeamt = null;
anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper _btotaldueamt = null;
anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper _bnewamt = null;
anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper _bseniorafteramt = null;
anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper _bpenaltyamt = null;
anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper _btotaldueamtaftersc = null;
String _sbasicamt = "";
String _spcaamt = "";
String _scurrentamt = "";
String _ssenioronbeforeamt = "";
String _stotaldueamtbeforesc = "";
String _sdiscamt = "";
String _smetercharges = "";
String _sfranchisetaxamt = "";
String _sseptagefee = "";
String _sseptagefeeamt = "";
String _stotaldueamt = "";
String _snewamt = "";
String _sseniorafteramt = "";
String _spenaltyamt = "";
String _stotaldueamtaftersc = "";
 //BA.debugLineNum = 2826;BA.debugLine="Private Sub SaveAverageBill(iPrintStatus As Int, i";
 //BA.debugLineNum = 2827;BA.debugLine="Dim lngDateTime As Long";
_lngdatetime = 0L;
 //BA.debugLineNum = 2828;BA.debugLine="Dim sWarning As String";
_swarning = "";
 //BA.debugLineNum = 2829;BA.debugLine="Dim MinimumRangeTo As Int";
_minimumrangeto = 0;
 //BA.debugLineNum = 2830;BA.debugLine="Dim sAveCum As String";
_savecum = "";
 //BA.debugLineNum = 2832;BA.debugLine="Dim bBasicAmt As BigDecimal";
_bbasicamt = new anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper();
 //BA.debugLineNum = 2833;BA.debugLine="Dim bPCAAmt As BigDecimal";
_bpcaamt = new anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper();
 //BA.debugLineNum = 2834;BA.debugLine="Dim bCurrentAmt As BigDecimal";
_bcurrentamt = new anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper();
 //BA.debugLineNum = 2835;BA.debugLine="Dim bSeniorOnBeforeAmt As BigDecimal";
_bsenioronbeforeamt = new anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper();
 //BA.debugLineNum = 2836;BA.debugLine="Dim bTotalDueAmtBeforeSC As BigDecimal";
_btotaldueamtbeforesc = new anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper();
 //BA.debugLineNum = 2837;BA.debugLine="Dim bDiscAmt As BigDecimal";
_bdiscamt = new anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper();
 //BA.debugLineNum = 2838;BA.debugLine="Dim bMeterCharges As BigDecimal";
_bmetercharges = new anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper();
 //BA.debugLineNum = 2839;BA.debugLine="Dim bFranchiseTaxAmt As BigDecimal";
_bfranchisetaxamt = new anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper();
 //BA.debugLineNum = 2840;BA.debugLine="Dim bSeptageFee As BigDecimal";
_bseptagefee = new anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper();
 //BA.debugLineNum = 2841;BA.debugLine="Dim bSeptageFeeAmt As BigDecimal";
_bseptagefeeamt = new anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper();
 //BA.debugLineNum = 2842;BA.debugLine="Dim bTotalDueAmt As BigDecimal";
_btotaldueamt = new anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper();
 //BA.debugLineNum = 2843;BA.debugLine="Dim bNewAmt As BigDecimal";
_bnewamt = new anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper();
 //BA.debugLineNum = 2845;BA.debugLine="Dim bSeniorAfterAmt As BigDecimal";
_bseniorafteramt = new anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper();
 //BA.debugLineNum = 2846;BA.debugLine="Dim bPenaltyAmt As BigDecimal";
_bpenaltyamt = new anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper();
 //BA.debugLineNum = 2847;BA.debugLine="Dim bTotalDueAmtAfterSC As BigDecimal";
_btotaldueamtaftersc = new anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper();
 //BA.debugLineNum = 2850;BA.debugLine="Dim sBasicAmt As String";
_sbasicamt = "";
 //BA.debugLineNum = 2851;BA.debugLine="Dim sPCAAmt As String";
_spcaamt = "";
 //BA.debugLineNum = 2852;BA.debugLine="Dim sCurrentAmt As String";
_scurrentamt = "";
 //BA.debugLineNum = 2853;BA.debugLine="Dim sSeniorOnBeforeAmt As String";
_ssenioronbeforeamt = "";
 //BA.debugLineNum = 2854;BA.debugLine="Dim sTotalDueAmtBeforeSC As String";
_stotaldueamtbeforesc = "";
 //BA.debugLineNum = 2855;BA.debugLine="Dim sDiscAmt As String";
_sdiscamt = "";
 //BA.debugLineNum = 2856;BA.debugLine="Dim sMeterCharges As String";
_smetercharges = "";
 //BA.debugLineNum = 2857;BA.debugLine="Dim sFranchiseTaxAmt As String";
_sfranchisetaxamt = "";
 //BA.debugLineNum = 2858;BA.debugLine="Dim sSeptageFee As String";
_sseptagefee = "";
 //BA.debugLineNum = 2859;BA.debugLine="Dim sSeptageFeeAmt As String";
_sseptagefeeamt = "";
 //BA.debugLineNum = 2860;BA.debugLine="Dim sTotalDueAmt As String";
_stotaldueamt = "";
 //BA.debugLineNum = 2861;BA.debugLine="Dim sNewAmt As String";
_snewamt = "";
 //BA.debugLineNum = 2864;BA.debugLine="Dim sSeniorAfterAmt As String";
_sseniorafteramt = "";
 //BA.debugLineNum = 2865;BA.debugLine="Dim sPenaltyAmt As String";
_spenaltyamt = "";
 //BA.debugLineNum = 2866;BA.debugLine="Dim sTotalDueAmtAfterSC As String";
_stotaldueamtaftersc = "";
 //BA.debugLineNum = 2869;BA.debugLine="TotalCum = 0";
_totalcum = (int) (0);
 //BA.debugLineNum = 2870;BA.debugLine="BasicAmt = 0";
_basicamt = 0;
 //BA.debugLineNum = 2871;BA.debugLine="PCAAmt = 0";
_pcaamt = 0;
 //BA.debugLineNum = 2872;BA.debugLine="CurrentAmt = 0";
_currentamt = 0;
 //BA.debugLineNum = 2873;BA.debugLine="SeniorOnBeforeAmt = 0";
_senioronbeforeamt = 0;
 //BA.debugLineNum = 2874;BA.debugLine="TotalDueAmtBeforeSC = 0";
_totaldueamtbeforesc = 0;
 //BA.debugLineNum = 2875;BA.debugLine="DiscAmt = 0";
_discamt = 0;
 //BA.debugLineNum = 2876;BA.debugLine="FranchiseTaxAmt = 0";
_franchisetaxamt = 0;
 //BA.debugLineNum = 2877;BA.debugLine="SeptageFee = 0";
_septagefee = 0;
 //BA.debugLineNum = 2878;BA.debugLine="SeptageFeeAmt = 0";
_septagefeeamt = 0;
 //BA.debugLineNum = 2879;BA.debugLine="TotalDueAmt = 0";
_totaldueamt = 0;
 //BA.debugLineNum = 2881;BA.debugLine="SeniorAfterAmt = 0";
_seniorafteramt = 0;
 //BA.debugLineNum = 2882;BA.debugLine="PenaltyAmt = 0";
_penaltyamt = 0;
 //BA.debugLineNum = 2883;BA.debugLine="TotalDueAmtAfterSC = 0";
_totaldueamtaftersc = 0;
 //BA.debugLineNum = 2884;BA.debugLine="RemainingAdvPayment = 0";
_remainingadvpayment = 0;
 //BA.debugLineNum = 2888;BA.debugLine="lngDateTime = DateTime.Now";
_lngdatetime = anywheresoftware.b4a.keywords.Common.DateTime.getNow();
 //BA.debugLineNum = 2889;BA.debugLine="DateTime.TimeFormat = \"HH:mm:ss\"";
anywheresoftware.b4a.keywords.Common.DateTime.setTimeFormat("HH:mm:ss");
 //BA.debugLineNum = 2890;BA.debugLine="TimeRead = DateTime.Time(lngDateTime)";
mostCurrent._timeread = anywheresoftware.b4a.keywords.Common.DateTime.Time(_lngdatetime);
 //BA.debugLineNum = 2891;BA.debugLine="DateTime.DateFormat = \"MM/dd/yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("MM/dd/yyyy");
 //BA.debugLineNum = 2892;BA.debugLine="DateRead = DateTime.Date(lngDateTime)";
mostCurrent._dateread = anywheresoftware.b4a.keywords.Common.DateTime.Date(_lngdatetime);
 //BA.debugLineNum = 2894;BA.debugLine="LogColor($\"Date Read: \"$ & DateRead, Colors.Yello";
anywheresoftware.b4a.keywords.Common.LogImpl("126017860",("Date Read: ")+mostCurrent._dateread,anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 //BA.debugLineNum = 2895;BA.debugLine="LogColor($\"Time Read: \"$ & TimeRead, Colors.Magen";
anywheresoftware.b4a.keywords.Common.LogImpl("126017861",("Time Read: ")+mostCurrent._timeread,anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 2900;BA.debugLine="ImplosiveType = $\"posted\"$";
mostCurrent._implosivetype = ("posted");
 //BA.debugLineNum = 2901;BA.debugLine="sWarning = $\"Average Bill\"$";
_swarning = ("Average Bill");
 //BA.debugLineNum = 2903;BA.debugLine="NoInput = NoInput + 1";
_noinput = (int) (_noinput+1);
 //BA.debugLineNum = 2904;BA.debugLine="WasRead = 1";
_wasread = (int) (1);
 //BA.debugLineNum = 2905;BA.debugLine="PrintStatus = iPrintStatus";
_printstatus = _iprintstatus;
 //BA.debugLineNum = 2907;BA.debugLine="If PrintStatus = 1 Then";
if (_printstatus==1) { 
 //BA.debugLineNum = 2908;BA.debugLine="NoPrinted = NoPrinted + 1";
_noprinted = (int) (_noprinted+1);
 }else {
 //BA.debugLineNum = 2910;BA.debugLine="NoPrinted = NoPrinted";
_noprinted = _noprinted;
 };
 //BA.debugLineNum = 2915;BA.debugLine="sAveCum = AveCum";
_savecum = BA.NumberToString(_avecum);
 //BA.debugLineNum = 2917;BA.debugLine="If IsNumber(sAveCum) = False Then";
if (anywheresoftware.b4a.keywords.Common.IsNumber(_savecum)==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 2918;BA.debugLine="TotalCum = DBaseFunctions.GetMinimumRangeTo(Glob";
_totalcum = (int) (mostCurrent._dbasefunctions._getminimumrangeto /*double*/ (mostCurrent.activityBA,mostCurrent._globalvar._branchid /*int*/ ,mostCurrent._acctclass,mostCurrent._acctsubclass,BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ )+"-"+BA.NumberToString(mostCurrent._globalvar._billmonth /*int*/ )+"-01")+_addcons);
 //BA.debugLineNum = 2919;BA.debugLine="PresAveCum = DBaseFunctions.GetMinimumRangeTo(Gl";
mostCurrent._presavecum = BA.NumberToString(mostCurrent._dbasefunctions._getminimumrangeto /*double*/ (mostCurrent.activityBA,mostCurrent._globalvar._branchid /*int*/ ,mostCurrent._acctclass,mostCurrent._acctsubclass,BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ )+"-"+BA.NumberToString(mostCurrent._globalvar._billmonth /*int*/ )+"-01")+_addcons);
 //BA.debugLineNum = 2920;BA.debugLine="PresAveRdg = PrevRdg + TotalCum";
mostCurrent._presaverdg = BA.NumberToString(_prevrdg+_totalcum);
 }else {
 //BA.debugLineNum = 2922;BA.debugLine="If AddCons > 0 Then";
if (_addcons>0) { 
 //BA.debugLineNum = 2923;BA.debugLine="If AddCons >= AveCum Then";
if (_addcons>=_avecum) { 
 //BA.debugLineNum = 2924;BA.debugLine="TotalCum = AddCons";
_totalcum = (int) (_addcons);
 //BA.debugLineNum = 2925;BA.debugLine="PresAveCum = 0";
mostCurrent._presavecum = BA.NumberToString(0);
 //BA.debugLineNum = 2926;BA.debugLine="PresAveRdg = PrevRdg";
mostCurrent._presaverdg = BA.NumberToString(_prevrdg);
 }else {
 //BA.debugLineNum = 2928;BA.debugLine="PresAveCum = AveCum - AddCons";
mostCurrent._presavecum = BA.NumberToString(_avecum-_addcons);
 //BA.debugLineNum = 2929;BA.debugLine="TotalCum = PresAveCum + AddCons";
_totalcum = (int) ((double)(Double.parseDouble(mostCurrent._presavecum))+_addcons);
 //BA.debugLineNum = 2930;BA.debugLine="PresAveRdg = PrevRdg + PresAveCum";
mostCurrent._presaverdg = BA.NumberToString(_prevrdg+(double)(Double.parseDouble(mostCurrent._presavecum)));
 };
 }else {
 //BA.debugLineNum = 2933;BA.debugLine="TotalCum = AveCum";
_totalcum = (int) (_avecum);
 //BA.debugLineNum = 2934;BA.debugLine="PresAveCum = AveCum";
mostCurrent._presavecum = BA.NumberToString(_avecum);
 //BA.debugLineNum = 2935;BA.debugLine="PresAveRdg = PrevRdg + AveCum";
mostCurrent._presaverdg = BA.NumberToString(_prevrdg+_avecum);
 };
 };
 //BA.debugLineNum = 2939;BA.debugLine="BasicAmt = DBaseFunctions.ComputeBasicAmt(strSF.V";
_basicamt = mostCurrent._dbasefunctions._computebasicamt /*double*/ (mostCurrent.activityBA,(int) (mostCurrent._strsf._vvvvvvv6(BA.NumberToString(_totalcum))),mostCurrent._globalvar._branchid /*int*/ ,mostCurrent._acctclass,mostCurrent._acctsubclass);
 //BA.debugLineNum = 2942;BA.debugLine="If DBaseFunctions.IsBookWithPCA(GlobalVar.BookID)";
if (mostCurrent._dbasefunctions._isbookwithpca /*boolean*/ (mostCurrent.activityBA,mostCurrent._globalvar._bookid /*int*/ )==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2943;BA.debugLine="If AcctClass = \"REL\" Then";
if ((mostCurrent._acctclass).equals("REL")) { 
 //BA.debugLineNum = 2944;BA.debugLine="If TotalCum <= 10 Then";
if (_totalcum<=10) { 
 //BA.debugLineNum = 2945;BA.debugLine="PCAAmt = 0";
_pcaamt = 0;
 }else {
 //BA.debugLineNum = 2947;BA.debugLine="PCAAmt = (TotalCum - 10) * PCA";
_pcaamt = (_totalcum-10)*_pca;
 };
 }else {
 //BA.debugLineNum = 2950;BA.debugLine="MinimumRangeTo = DBaseFunctions.GetMinimumRange";
_minimumrangeto = (int) (mostCurrent._dbasefunctions._getminimumrangeto /*double*/ (mostCurrent.activityBA,mostCurrent._globalvar._branchid /*int*/ ,mostCurrent._acctclass,mostCurrent._acctsubclass,BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ )+"-"+BA.NumberToString(mostCurrent._globalvar._billmonth /*int*/ )+"-01"));
 //BA.debugLineNum = 2952;BA.debugLine="If TotalCum <= MinimumRangeTo Then";
if (_totalcum<=_minimumrangeto) { 
 //BA.debugLineNum = 2953;BA.debugLine="PCAAmt = MinimumRangeTo * PCA";
_pcaamt = _minimumrangeto*_pca;
 }else {
 //BA.debugLineNum = 2955;BA.debugLine="PCAAmt = TotalCum * PCA";
_pcaamt = _totalcum*_pca;
 };
 };
 }else {
 //BA.debugLineNum = 2959;BA.debugLine="PCAAmt = 0";
_pcaamt = 0;
 };
 //BA.debugLineNum = 2963;BA.debugLine="CurrentAmt = BasicAmt + PCAAmt";
_currentamt = _basicamt+_pcaamt;
 //BA.debugLineNum = 2966;BA.debugLine="If IsSenior = 1 And AcctClass = \"RES\" Then";
if (_issenior==1 && (mostCurrent._acctclass).equals("RES")) { 
 //BA.debugLineNum = 2967;BA.debugLine="SeniorOnBeforeAmt = GetSeniorBefore(ReadID, Tota";
_senioronbeforeamt = _getseniorbefore(_readid,_totalcum);
 //BA.debugLineNum = 2968;BA.debugLine="SeniorAfterAmt = GetSeniorAfter(ReadID, TotalCum";
_seniorafteramt = _getseniorafter(_readid,_totalcum);
 }else {
 //BA.debugLineNum = 2970;BA.debugLine="SeniorAfterAmt = 0";
_seniorafteramt = 0;
 //BA.debugLineNum = 2971;BA.debugLine="SeniorOnBeforeAmt = 0";
_senioronbeforeamt = 0;
 };
 //BA.debugLineNum = 2974;BA.debugLine="TotalDueAmtBeforeSC = CurrentAmt - SeniorOnBefore";
_totaldueamtbeforesc = _currentamt-_senioronbeforeamt;
 //BA.debugLineNum = 2977;BA.debugLine="FranchiseTaxAmt = (CurrentAmt * FranchiseTaxPct)";
_franchisetaxamt = (_currentamt*_franchisetaxpct);
 //BA.debugLineNum = 2981;BA.debugLine="GlobalVar.SeptageRateID = 0";
mostCurrent._globalvar._septagerateid /*int*/  = (int) (0);
 //BA.debugLineNum = 2982;BA.debugLine="GlobalVar.SeptageRatePerCuM = 0";
mostCurrent._globalvar._septageratepercum /*double*/  = 0;
 //BA.debugLineNum = 2983;BA.debugLine="GlobalVar.SeptageMinCuM = 0";
mostCurrent._globalvar._septagemincum /*int*/  = (int) (0);
 //BA.debugLineNum = 2984;BA.debugLine="GlobalVar.SeptageMaxCuM = 0";
mostCurrent._globalvar._septagemaxcum /*int*/  = (int) (0);
 //BA.debugLineNum = 2985;BA.debugLine="GlobalVar.SeptageRateType = \"amount\"";
mostCurrent._globalvar._septageratetype /*String*/  = "amount";
 //BA.debugLineNum = 2986;BA.debugLine="dSeptageRate = 0";
_dseptagerate = 0;
 //BA.debugLineNum = 2988;BA.debugLine="If HasSeptageFee = 1 Then";
if (_hasseptagefee==1) { 
 //BA.debugLineNum = 2989;BA.debugLine="If GlobalVar.BranchID = 14 Or GlobalVar.BranchID";
if (mostCurrent._globalvar._branchid /*int*/ ==14 || mostCurrent._globalvar._branchid /*int*/ ==62 || mostCurrent._globalvar._branchid /*int*/ ==28 || mostCurrent._globalvar._branchid /*int*/ ==29 || mostCurrent._globalvar._branchid /*int*/ ==30) { 
 //BA.debugLineNum = 2990;BA.debugLine="LogColor ($\"Acct Classification: \"$ & AcctClassi";
anywheresoftware.b4a.keywords.Common.LogImpl("126017956",("Acct Classification: ")+mostCurrent._acctclassification,anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 2991;BA.debugLine="GlobalVar.SeptageRateID = DBaseFunctions.GetSept";
mostCurrent._globalvar._septagerateid /*int*/  = mostCurrent._dbasefunctions._getseptagerateid /*int*/ (mostCurrent.activityBA,mostCurrent._acctclassification,mostCurrent._globalvar._branchid /*int*/ );
 //BA.debugLineNum = 2992;BA.debugLine="LogColor ($\"Septage Rate ID: \"$ & GlobalVar.Sep";
anywheresoftware.b4a.keywords.Common.LogImpl("126017958",("Septage Rate ID: ")+BA.NumberToString(mostCurrent._globalvar._septagerateid /*int*/ ),anywheresoftware.b4a.keywords.Common.Colors.Cyan);
 //BA.debugLineNum = 2994;BA.debugLine="If GlobalVar.SeptageRateID = 0 Then";
if (mostCurrent._globalvar._septagerateid /*int*/ ==0) { 
 //BA.debugLineNum = 2995;BA.debugLine="WarningMsg($\"NO SEPTAGE RATES FOUND!\"$, $\"Custo";
_warningmsg(("NO SEPTAGE RATES FOUND!"),("Customer account classification has no septage rates found!")+anywheresoftware.b4a.keywords.Common.CRLF+("Please ask assistance to the Central Billing & IT Department regarding this."));
 //BA.debugLineNum = 2996;BA.debugLine="Return";
if (true) return "";
 }else {
 //BA.debugLineNum = 2998;BA.debugLine="DBaseFunctions.GetSeptageRateDetails(GlobalVar.";
mostCurrent._dbasefunctions._getseptageratedetails /*String*/ (mostCurrent.activityBA,mostCurrent._globalvar._branchid /*int*/ ,mostCurrent._globalvar._septagerateid /*int*/ ,mostCurrent._acctclassification);
 //BA.debugLineNum = 2999;BA.debugLine="If GlobalVar.SeptageRateType = \"amount\" Then";
if ((mostCurrent._globalvar._septageratetype /*String*/ ).equals("amount")) { 
 //BA.debugLineNum = 3000;BA.debugLine="If TotalCum <= GlobalVar.SeptageMinCuM Then";
if (_totalcum<=mostCurrent._globalvar._septagemincum /*int*/ ) { 
 //BA.debugLineNum = 3001;BA.debugLine="SeptageFee = GlobalVar.SeptageRatePerCuM * G";
_septagefee = mostCurrent._globalvar._septageratepercum /*double*/ *mostCurrent._globalvar._septagemincum /*int*/ ;
 }else if(_totalcum>=mostCurrent._globalvar._septagemaxcum /*int*/ ) { 
 //BA.debugLineNum = 3004;BA.debugLine="SeptageFee = GlobalVar.SeptageRatePerCuM * G";
_septagefee = mostCurrent._globalvar._septageratepercum /*double*/ *mostCurrent._globalvar._septagemaxcum /*int*/ ;
 }else {
 //BA.debugLineNum = 3007;BA.debugLine="SeptageFee = GlobalVar.SeptageRatePerCuM * Tot";
_septagefee = mostCurrent._globalvar._septageratepercum /*double*/ *_totalcum;
 };
 }else {
 //BA.debugLineNum = 3010;BA.debugLine="SeptageFee = (GlobalVar.SeptageRatePerCuM) * Cu";
_septagefee = (mostCurrent._globalvar._septageratepercum /*double*/ )*_currentamt;
 };
 };
 }else {
 //BA.debugLineNum = 3016;BA.debugLine="If TotalCum <= MinSeptageCum Then";
if (_totalcum<=_minseptagecum) { 
 //BA.debugLineNum = 3017;BA.debugLine="SeptageFee = SeptageRate * MinSeptageCum";
_septagefee = _septagerate*_minseptagecum;
 }else if(_totalcum>=_maxseptagecum) { 
 //BA.debugLineNum = 3019;BA.debugLine="SeptageFee = SeptageRate * MaxSeptageCum";
_septagefee = _septagerate*_maxseptagecum;
 }else {
 //BA.debugLineNum = 3021;BA.debugLine="SeptageFee = SeptageRate * TotalCum";
_septagefee = _septagerate*_totalcum;
 };
 };
 }else {
 //BA.debugLineNum = 3025;BA.debugLine="SeptageFee = 0";
_septagefee = 0;
 };
 //BA.debugLineNum = 3072;BA.debugLine="LogColor($\"Septage Rate: \"$ & SeptageRate, Colors";
anywheresoftware.b4a.keywords.Common.LogImpl("126018038",("Septage Rate: ")+BA.NumberToString(_septagerate),anywheresoftware.b4a.keywords.Common.Colors.Cyan);
 //BA.debugLineNum = 3073;BA.debugLine="SeptageFeeAmt = SeptageFee";
_septagefeeamt = _septagefee;
 //BA.debugLineNum = 3096;BA.debugLine="bSeptageFee.Initialize(SeptageFee)";
_bseptagefee.Initialize(BA.NumberToString(_septagefee));
 //BA.debugLineNum = 3097;BA.debugLine="bSeptageFee = RoundtoCurrency(bSeptageFee, 2)";
_bseptagefee = _roundtocurrency(_bseptagefee,(int) (2));
 //BA.debugLineNum = 3098;BA.debugLine="sSeptageFee = NumberFormat2(bSeptageFee.ToPlainSt";
_sseptagefee = anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(_bseptagefee.ToPlainString())),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3099;BA.debugLine="LogColor($\"ORIG SEPTAGE AND SEWERAGE: \"$ & sSepta";
anywheresoftware.b4a.keywords.Common.LogImpl("126018065",("ORIG SEPTAGE AND SEWERAGE: ")+_sseptagefee,anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 //BA.debugLineNum = 3101;BA.debugLine="bBasicAmt.Initialize(BasicAmt)";
_bbasicamt.Initialize(BA.NumberToString(_basicamt));
 //BA.debugLineNum = 3102;BA.debugLine="bBasicAmt = RoundtoCurrency(bBasicAmt, 2)";
_bbasicamt = _roundtocurrency(_bbasicamt,(int) (2));
 //BA.debugLineNum = 3103;BA.debugLine="sBasicAmt = NumberFormat2(bBasicAmt.ToPlainString";
_sbasicamt = anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(_bbasicamt.ToPlainString())),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3104;BA.debugLine="LogColor($\"Basic: \"$ & sBasicAmt, Colors.White)";
anywheresoftware.b4a.keywords.Common.LogImpl("126018070",("Basic: ")+_sbasicamt,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 3106;BA.debugLine="bPCAAmt.Initialize(PCAAmt)";
_bpcaamt.Initialize(BA.NumberToString(_pcaamt));
 //BA.debugLineNum = 3107;BA.debugLine="bPCAAmt = RoundtoCurrency(bPCAAmt, 2)";
_bpcaamt = _roundtocurrency(_bpcaamt,(int) (2));
 //BA.debugLineNum = 3108;BA.debugLine="sPCAAmt = NumberFormat2(bPCAAmt.ToPlainString, 1,";
_spcaamt = anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(_bpcaamt.ToPlainString())),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3109;BA.debugLine="LogColor($\"PCA: \"$ & sPCAAmt, Colors.White)";
anywheresoftware.b4a.keywords.Common.LogImpl("126018075",("PCA: ")+_spcaamt,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 3111;BA.debugLine="bCurrentAmt.Initialize(CurrentAmt)";
_bcurrentamt.Initialize(BA.NumberToString(_currentamt));
 //BA.debugLineNum = 3112;BA.debugLine="bCurrentAmt = RoundtoCurrency(bCurrentAmt, 2)";
_bcurrentamt = _roundtocurrency(_bcurrentamt,(int) (2));
 //BA.debugLineNum = 3113;BA.debugLine="sCurrentAmt = NumberFormat2(bCurrentAmt.ToPlainSt";
_scurrentamt = anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(_bcurrentamt.ToPlainString())),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3114;BA.debugLine="LogColor($\"CURENT BILL: \"$ & sCurrentAmt, Colors.";
anywheresoftware.b4a.keywords.Common.LogImpl("126018080",("CURENT BILL: ")+_scurrentamt,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 3116;BA.debugLine="bSeniorOnBeforeAmt.Initialize(SeniorOnBeforeAmt)";
_bsenioronbeforeamt.Initialize(BA.NumberToString(_senioronbeforeamt));
 //BA.debugLineNum = 3117;BA.debugLine="bSeniorOnBeforeAmt = RoundtoCurrency(bSeniorOnBef";
_bsenioronbeforeamt = _roundtocurrency(_bsenioronbeforeamt,(int) (2));
 //BA.debugLineNum = 3118;BA.debugLine="sSeniorOnBeforeAmt = NumberFormat2(bSeniorOnBefor";
_ssenioronbeforeamt = anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(_bsenioronbeforeamt.ToPlainString())),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3119;BA.debugLine="LogColor($\"SC DISC: \"$ & sSeniorOnBeforeAmt, Colo";
anywheresoftware.b4a.keywords.Common.LogImpl("126018085",("SC DISC: ")+_ssenioronbeforeamt,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 3121;BA.debugLine="bSeniorAfterAmt.Initialize(SeniorAfterAmt)";
_bseniorafteramt.Initialize(BA.NumberToString(_seniorafteramt));
 //BA.debugLineNum = 3122;BA.debugLine="bSeniorAfterAmt = RoundtoCurrency(bSeniorAfterAmt";
_bseniorafteramt = _roundtocurrency(_bseniorafteramt,(int) (2));
 //BA.debugLineNum = 3123;BA.debugLine="sSeniorAfterAmt = NumberFormat2(bSeniorAfterAmt.T";
_sseniorafteramt = anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(_bseniorafteramt.ToPlainString())),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3124;BA.debugLine="LogColor($\"SENIOR DISC BEFORE: \"$ & sSeniorAfterA";
anywheresoftware.b4a.keywords.Common.LogImpl("126018090",("SENIOR DISC BEFORE: ")+_sseniorafteramt,anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 //BA.debugLineNum = 3126;BA.debugLine="bTotalDueAmtBeforeSC.Initialize(TotalDueAmtBefore";
_btotaldueamtbeforesc.Initialize(BA.NumberToString(_totaldueamtbeforesc));
 //BA.debugLineNum = 3127;BA.debugLine="bTotalDueAmtBeforeSC = RoundtoCurrency(bTotalDueA";
_btotaldueamtbeforesc = _roundtocurrency(_btotaldueamtbeforesc,(int) (2));
 //BA.debugLineNum = 3128;BA.debugLine="sTotalDueAmtBeforeSC = NumberFormat2(bTotalDueAmt";
_stotaldueamtbeforesc = anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(_btotaldueamtbeforesc.ToPlainString())),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3129;BA.debugLine="LogColor($\"TOTAL DUE BEFORE DUE: \"$ & sTotalDueAm";
anywheresoftware.b4a.keywords.Common.LogImpl("126018095",("TOTAL DUE BEFORE DUE: ")+_stotaldueamtbeforesc,anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 //BA.debugLineNum = 3131;BA.debugLine="bDiscAmt.Initialize(DiscAmt)";
_bdiscamt.Initialize(BA.NumberToString(_discamt));
 //BA.debugLineNum = 3132;BA.debugLine="bDiscAmt = RoundtoCurrency(bDiscAmt, 2)";
_bdiscamt = _roundtocurrency(_bdiscamt,(int) (2));
 //BA.debugLineNum = 3133;BA.debugLine="sDiscAmt = NumberFormat2(bDiscAmt.ToPlainString,";
_sdiscamt = anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(_bdiscamt.ToPlainString())),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3134;BA.debugLine="LogColor($\"DISCOUNT: \"$ & sDiscAmt, Colors.White)";
anywheresoftware.b4a.keywords.Common.LogImpl("126018100",("DISCOUNT: ")+_sdiscamt,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 3136;BA.debugLine="bMeterCharges.Initialize(MeterCharges)";
_bmetercharges.Initialize(BA.NumberToString(_metercharges));
 //BA.debugLineNum = 3137;BA.debugLine="bMeterCharges = RoundtoCurrency(bMeterCharges, 2)";
_bmetercharges = _roundtocurrency(_bmetercharges,(int) (2));
 //BA.debugLineNum = 3138;BA.debugLine="sMeterCharges = NumberFormat2(bMeterCharges.ToPla";
_smetercharges = anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(_bmetercharges.ToPlainString())),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3139;BA.debugLine="LogColor($\"METER CHARGE: \"$ & sMeterCharges, Colo";
anywheresoftware.b4a.keywords.Common.LogImpl("126018105",("METER CHARGE: ")+_smetercharges,anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 //BA.debugLineNum = 3141;BA.debugLine="bFranchiseTaxAmt.Initialize(FranchiseTaxAmt)";
_bfranchisetaxamt.Initialize(BA.NumberToString(_franchisetaxamt));
 //BA.debugLineNum = 3142;BA.debugLine="bFranchiseTaxAmt = RoundtoCurrency(bFranchiseTaxA";
_bfranchisetaxamt = _roundtocurrency(_bfranchisetaxamt,(int) (2));
 //BA.debugLineNum = 3143;BA.debugLine="sFranchiseTaxAmt = NumberFormat2(bFranchiseTaxAmt";
_sfranchisetaxamt = anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(_bfranchisetaxamt.ToPlainString())),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3144;BA.debugLine="LogColor($\"FRANCHISE TAX: \"$ & sFranchiseTaxAmt,";
anywheresoftware.b4a.keywords.Common.LogImpl("126018110",("FRANCHISE TAX: ")+_sfranchisetaxamt,anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 //BA.debugLineNum = 3154;BA.debugLine="bNewAmt.Initialize((sCurrentAmt + AddToBillAmt +";
_bnewamt.Initialize(BA.NumberToString(((double)(Double.parseDouble(_scurrentamt))+_addtobillamt+_arrearsamt+(double)(Double.parseDouble(_smetercharges))+(double)(Double.parseDouble(_sfranchisetaxamt)))-((double)(Double.parseDouble(_ssenioronbeforeamt))+(double)(Double.parseDouble(_sdiscamt)))));
 //BA.debugLineNum = 3155;BA.debugLine="bNewAmt = RoundtoCurrency(bNewAmt,2)";
_bnewamt = _roundtocurrency(_bnewamt,(int) (2));
 //BA.debugLineNum = 3156;BA.debugLine="sNewAmt =  NumberFormat2(bNewAmt.ToPlainString, 1";
_snewamt = anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(_bnewamt.ToPlainString())),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3158;BA.debugLine="LogColor(sNewAmt,Colors.Yellow)";
anywheresoftware.b4a.keywords.Common.LogImpl("126018124",_snewamt,anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 //BA.debugLineNum = 3160;BA.debugLine="If (sNewAmt - AdvPayment) < 0 Then";
if (((double)(Double.parseDouble(_snewamt))-_advpayment)<0) { 
 //BA.debugLineNum = 3161;BA.debugLine="TotalDueAmt = 0";
_totaldueamt = 0;
 }else {
 //BA.debugLineNum = 3163;BA.debugLine="TotalDueAmt = sNewAmt - AdvPayment";
_totaldueamt = (double)(Double.parseDouble(_snewamt))-_advpayment;
 };
 //BA.debugLineNum = 3166;BA.debugLine="If (AdvPayment - sNewAmt) <= 0 Then";
if ((_advpayment-(double)(Double.parseDouble(_snewamt)))<=0) { 
 //BA.debugLineNum = 3167;BA.debugLine="RemainingAdvPayment = 0";
_remainingadvpayment = 0;
 }else {
 //BA.debugLineNum = 3169;BA.debugLine="RemainingAdvPayment = AdvPayment - sNewAmt";
_remainingadvpayment = _advpayment-(double)(Double.parseDouble(_snewamt));
 };
 //BA.debugLineNum = 3172;BA.debugLine="If RemainingAdvPayment <= SeptageFeeAmt Then";
if (_remainingadvpayment<=_septagefeeamt) { 
 //BA.debugLineNum = 3173;BA.debugLine="SeptageFeeAmt = SeptageFeeAmt - RemainingAdvPaym";
_septagefeeamt = _septagefeeamt-_remainingadvpayment;
 }else {
 //BA.debugLineNum = 3175;BA.debugLine="SeptageFeeAmt = 0";
_septagefeeamt = 0;
 };
 //BA.debugLineNum = 3178;BA.debugLine="bSeptageFeeAmt.Initialize(SeptageFeeAmt)";
_bseptagefeeamt.Initialize(BA.NumberToString(_septagefeeamt));
 //BA.debugLineNum = 3179;BA.debugLine="bSeptageFeeAmt = RoundtoCurrency(bSeptageFeeAmt,";
_bseptagefeeamt = _roundtocurrency(_bseptagefeeamt,(int) (2));
 //BA.debugLineNum = 3180;BA.debugLine="sSeptageFeeAmt = NumberFormat2(bSeptageFeeAmt.ToP";
_sseptagefeeamt = anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(_bseptagefeeamt.ToPlainString())),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3181;BA.debugLine="LogColor($\"SEPTAGE AND SEWERAGE: \"$ & sSeptageFee";
anywheresoftware.b4a.keywords.Common.LogImpl("126018147",("SEPTAGE AND SEWERAGE: ")+_sseptagefeeamt,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 3184;BA.debugLine="bTotalDueAmt.Initialize(TotalDueAmt)";
_btotaldueamt.Initialize(BA.NumberToString(_totaldueamt));
 //BA.debugLineNum = 3185;BA.debugLine="bTotalDueAmt = RoundtoCurrency(bTotalDueAmt, 2)";
_btotaldueamt = _roundtocurrency(_btotaldueamt,(int) (2));
 //BA.debugLineNum = 3186;BA.debugLine="sTotalDueAmt = NumberFormat2(bTotalDueAmt.ToPlain";
_stotaldueamt = anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(_btotaldueamt.ToPlainString())),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3187;BA.debugLine="LogColor($\"TOTAL DUE: \"$ & sTotalDueAmt, Colors.W";
anywheresoftware.b4a.keywords.Common.LogImpl("126018153",("TOTAL DUE: ")+_stotaldueamt,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 3189;BA.debugLine="If AdvPayment >= CurrentAmt Then";
if (_advpayment>=_currentamt) { 
 //BA.debugLineNum = 3190;BA.debugLine="PenaltyAmt = 0";
_penaltyamt = 0;
 }else {
 //BA.debugLineNum = 3192;BA.debugLine="PenaltyAmt = CurrentAmt * PenaltyPct";
_penaltyamt = _currentamt*_penaltypct;
 };
 //BA.debugLineNum = 3195;BA.debugLine="bPenaltyAmt.Initialize(PenaltyAmt)";
_bpenaltyamt.Initialize(BA.NumberToString(_penaltyamt));
 //BA.debugLineNum = 3196;BA.debugLine="bPenaltyAmt = RoundtoCurrency(bPenaltyAmt, 2)";
_bpenaltyamt = _roundtocurrency(_bpenaltyamt,(int) (2));
 //BA.debugLineNum = 3197;BA.debugLine="sPenaltyAmt = NumberFormat2(bPenaltyAmt.ToPlainSt";
_spenaltyamt = anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(_bpenaltyamt.ToPlainString())),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3198;BA.debugLine="LogColor($\"PENALTY: \"$ & sPenaltyAmt, Colors.Whit";
anywheresoftware.b4a.keywords.Common.LogImpl("126018164",("PENALTY: ")+_spenaltyamt,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 3201;BA.debugLine="If ((sCurrentAmt + AddToBillAmt + ArrearsAmt + sM";
if ((((double)(Double.parseDouble(_scurrentamt))+_addtobillamt+_arrearsamt+(double)(Double.parseDouble(_smetercharges))+(double)(Double.parseDouble(_sfranchisetaxamt)))-((double)(Double.parseDouble(_sseniorafteramt))+(double)(Double.parseDouble(_sdiscamt))))<_advpayment) { 
 //BA.debugLineNum = 3202;BA.debugLine="TotalDueAmtAfterSC = 0";
_totaldueamtaftersc = 0;
 }else {
 //BA.debugLineNum = 3204;BA.debugLine="TotalDueAmtAfterSC = sCurrentAmt + AddToBillAmt";
_totaldueamtaftersc = (double)(Double.parseDouble(_scurrentamt))+_addtobillamt+_arrearsamt+(double)(Double.parseDouble(_smetercharges))+(double)(Double.parseDouble(_sfranchisetaxamt))-(_advpayment+(double)(Double.parseDouble(_sseniorafteramt))+(double)(Double.parseDouble(_sdiscamt)))+(double)(Double.parseDouble(_spenaltyamt));
 };
 //BA.debugLineNum = 3207;BA.debugLine="bTotalDueAmtAfterSC.Initialize(TotalDueAmtAfterSC";
_btotaldueamtaftersc.Initialize(BA.NumberToString(_totaldueamtaftersc));
 //BA.debugLineNum = 3208;BA.debugLine="bTotalDueAmtAfterSC = RoundtoCurrency(bTotalDueAm";
_btotaldueamtaftersc = _roundtocurrency(_btotaldueamtaftersc,(int) (2));
 //BA.debugLineNum = 3209;BA.debugLine="sTotalDueAmtAfterSC = NumberFormat2(bTotalDueAmtA";
_stotaldueamtaftersc = anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(_btotaldueamtaftersc.ToPlainString())),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3210;BA.debugLine="LogColor($\"TOTAL AMT. AFTER DUE: \"$ & sTotalDueAm";
anywheresoftware.b4a.keywords.Common.LogImpl("126018176",("TOTAL AMT. AFTER DUE: ")+_stotaldueamtaftersc,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 3214;BA.debugLine="Starter.DBCon.BeginTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .BeginTransaction();
 //BA.debugLineNum = 3215;BA.debugLine="Try";
try { //BA.debugLineNum = 3216;BA.debugLine="If blnEdit = True Then";
if (_blnedit==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 3217;BA.debugLine="Starter.strCriteria = \"UPDATE tblReadings \" & _";
mostCurrent._starter._strcriteria /*String*/  = "UPDATE tblReadings "+"SET PresRdg = ?, PresCum = ?, TotalCum = ?, BillType =?, BasicAmt = ?, PCAAmt = ?, SeptageFeeAmt = ?, SeptageAmt = ?, "+"CurrentAmt = ?, TotalDueAmt = ?, SeniorOnBeforeAmt = ?, SeniorAfterAmt = ?, TotalDueAmtBeforeSC = ?, PenaltyAmt = ?, TotalDueAmtAfterSC = ?, DiscAmt = ?, FranchiseTaxAmt = ?, "+"PrintStatus = ?, NoPrinted = ?, WasMissCoded = ?, MissCodeID = ?, MissCode = ?, WasImplosive = ?, ImplosiveType = ?, ImpID = ?, FFindings = ?, FWarnings = ?, ReadRemarks = ?, "+"ReadRemarks = ?, WasRead = ?, NoInput = ?, TimeRead = ?, DateRead = ? "+"WHERE ReadID = "+BA.NumberToString(_ireadid)+" "+"AND AcctID = "+BA.NumberToString(_iacctid);
 //BA.debugLineNum = 3225;BA.debugLine="Starter.DBCon.ExecNonQuery2(Starter.strCriteria";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2(mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{mostCurrent._presaverdg,mostCurrent._presavecum,BA.NumberToString(_totalcum),("AB"),_sbasicamt,_spcaamt,_sseptagefee,_sseptagefeeamt,_scurrentamt,_stotaldueamt,_ssenioronbeforeamt,_sseniorafteramt,_stotaldueamtbeforesc,_spenaltyamt,_stotaldueamtaftersc,_sdiscamt,_sfranchisetaxamt,BA.NumberToString(_printstatus),BA.NumberToString(_noprinted),("0"),("0"),(""),("0"),mostCurrent._implosivetype,("0"),("Customer's Average Consumption"),_swarning,mostCurrent._strreadremarks,mostCurrent._saverem,("1"),BA.NumberToString(_noinput),mostCurrent._timeread,mostCurrent._dateread}));
 //BA.debugLineNum = 3230;BA.debugLine="snack.Initialize(\"\",Activity, $\"Reading has bee";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Reading has been successfully updated."),mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 3231;BA.debugLine="SetSnackBarBackground(snack, GlobalVar.PriColor";
_setsnackbarbackground(mostCurrent._snack,(int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 3232;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 3233;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 3234;BA.debugLine="blnEdit = False";
_blnedit = anywheresoftware.b4a.keywords.Common.False;
 }else {
 //BA.debugLineNum = 3236;BA.debugLine="NewSeqNo = DBaseFunctions.GetSeqNo(GlobalVar.Bo";
_newseqno = mostCurrent._dbasefunctions._getseqno /*int*/ (mostCurrent.activityBA,mostCurrent._globalvar._bookid /*int*/ );
 //BA.debugLineNum = 3237;BA.debugLine="Starter.strCriteria = \"UPDATE tblReadings \" & _";
mostCurrent._starter._strcriteria /*String*/  = "UPDATE tblReadings "+"SET OrigRdg = ?, PresRdg = ?, PresCum = ?, TotalCum = ?, BillType =?, BasicAmt = ?, PCAAmt = ?, SeptageAmt = ?, SeptageFeeAmt = ?, "+"CurrentAmt = ?, TotalDueAmt = ?, SeniorOnBeforeAmt = ?, SeniorAfterAmt = ?, TotalDueAmtBeforeSC = ?, PenaltyAmt = ?, TotalDueAmtAfterSC = ?, DiscAmt = ?, FranchiseTaxAmt = ?, "+"PrintStatus = ?, NoPrinted = ?, WasMissCoded = ?, MissCodeID = ?, MissCode = ?, WasImplosive = ?, ImplosiveType = ?, ImpID = ?, FFindings = ?, FWarnings = ?, ReadRemarks = ?, "+"ReadRemarks = ?, WasRead = ?, NewSeqNo = ?, NoInput = ?, TimeRead = ?, DateRead = ? "+"WHERE ReadID = "+BA.NumberToString(_ireadid)+" "+"AND AcctID = "+BA.NumberToString(_iacctid);
 //BA.debugLineNum = 3245;BA.debugLine="Starter.DBCon.ExecNonQuery2(Starter.strCriteria";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2(mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{mostCurrent._presaverdg,mostCurrent._presaverdg,mostCurrent._presavecum,BA.NumberToString(_totalcum),("AB"),_sbasicamt,_spcaamt,_sseptagefee,_sseptagefeeamt,_scurrentamt,_stotaldueamt,_ssenioronbeforeamt,_sseniorafteramt,_stotaldueamtbeforesc,_spenaltyamt,_stotaldueamtaftersc,_sdiscamt,_sfranchisetaxamt,BA.NumberToString(_printstatus),BA.NumberToString(_noprinted),("0"),("0"),(""),("0"),mostCurrent._implosivetype,("0"),("Customer's Average Consumption"),_swarning,mostCurrent._strreadremarks,mostCurrent._saverem,("1"),BA.NumberToString(_newseqno),BA.NumberToString(_noinput),mostCurrent._timeread,mostCurrent._dateread}));
 //BA.debugLineNum = 3250;BA.debugLine="snack.Initialize(\"\",Activity, $\"New Reading has";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("New Reading has been successfully posted."),mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 3251;BA.debugLine="SetSnackBarBackground(snack, GlobalVar.PriColor";
_setsnackbarbackground(mostCurrent._snack,(int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 3252;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 3253;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 };
 //BA.debugLineNum = 3255;BA.debugLine="Starter.DBCon.TransactionSuccessful";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .TransactionSuccessful();
 } 
       catch (Exception e266) {
			processBA.setLastException(e266); //BA.debugLineNum = 3257;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("126018223",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 3259;BA.debugLine="Starter.DBCon.EndTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .EndTransaction();
 //BA.debugLineNum = 3260;BA.debugLine="blnEdit = False";
_blnedit = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 3261;BA.debugLine="End Sub";
return "";
}
public static String  _savemisscodeonly(int _imisscodeid,String _smisscodedesc,int _ireadid,int _iacctid) throws Exception{
long _lngdatetime = 0L;
String _findings = "";
String _swarning = "";
 //BA.debugLineNum = 2254;BA.debugLine="Private Sub SaveMissCodeOnly(iMissCodeID As Int, s";
 //BA.debugLineNum = 2255;BA.debugLine="Dim lngDateTime As Long";
_lngdatetime = 0L;
 //BA.debugLineNum = 2256;BA.debugLine="Dim Findings As String";
_findings = "";
 //BA.debugLineNum = 2257;BA.debugLine="Dim sWarning As String";
_swarning = "";
 //BA.debugLineNum = 2258;BA.debugLine="lngDateTime = DateTime.Now";
_lngdatetime = anywheresoftware.b4a.keywords.Common.DateTime.getNow();
 //BA.debugLineNum = 2259;BA.debugLine="DateTime.TimeFormat = \"HH:mm:ss\"";
anywheresoftware.b4a.keywords.Common.DateTime.setTimeFormat("HH:mm:ss");
 //BA.debugLineNum = 2260;BA.debugLine="TimeRead = DateTime.Time(lngDateTime)";
mostCurrent._timeread = anywheresoftware.b4a.keywords.Common.DateTime.Time(_lngdatetime);
 //BA.debugLineNum = 2261;BA.debugLine="DateTime.DateFormat = \"MM/dd/yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("MM/dd/yyyy");
 //BA.debugLineNum = 2262;BA.debugLine="DateRead = DateTime.Date(lngDateTime)";
mostCurrent._dateread = anywheresoftware.b4a.keywords.Common.DateTime.Date(_lngdatetime);
 //BA.debugLineNum = 2264;BA.debugLine="NoInput = NoInput + 1";
_noinput = (int) (_noinput+1);
 //BA.debugLineNum = 2265;BA.debugLine="If IsNumber(txtPresRdg.Text) Then";
if (anywheresoftware.b4a.keywords.Common.IsNumber(mostCurrent._txtpresrdg.getText())) { 
 //BA.debugLineNum = 2266;BA.debugLine="TotalCum = lblPresCum.Text + AddCons";
_totalcum = (int) ((double)(Double.parseDouble(mostCurrent._lblprescum.getText()))+_addcons);
 }else {
 //BA.debugLineNum = 2268;BA.debugLine="TotalCum = 0";
_totalcum = (int) (0);
 };
 //BA.debugLineNum = 2271;BA.debugLine="If lblPresCum.Text.Length <=0 Then";
if (mostCurrent._lblprescum.getText().length()<=0) { 
 //BA.debugLineNum = 2272;BA.debugLine="Findings = \"Skipped Reading\"";
_findings = "Skipped Reading";
 }else {
 //BA.debugLineNum = 2274;BA.debugLine="If strSF.Val(lblPresCum.Text) < 0 Then";
if (mostCurrent._strsf._vvvvvvv6(mostCurrent._lblprescum.getText())<0) { 
 //BA.debugLineNum = 2275;BA.debugLine="Findings = \"Negative Consumption\"";
_findings = "Negative Consumption";
 };
 };
 //BA.debugLineNum = 2279;BA.debugLine="sWarning = $\"Unable to Read Customer's Meter\"$";
_swarning = ("Unable to Read Customer's Meter");
 //BA.debugLineNum = 2281;BA.debugLine="Try";
try { //BA.debugLineNum = 2282;BA.debugLine="Starter.DBCon.BeginTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .BeginTransaction();
 //BA.debugLineNum = 2283;BA.debugLine="If blnEdit = True Then";
if (_blnedit==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2284;BA.debugLine="Starter.strCriteria=\"UPDATE tblReadings \" & _";
mostCurrent._starter._strcriteria /*String*/  = "UPDATE tblReadings "+"SET WasMissCoded = ?, MissCodeID = ?, MissCode = ?, fFindings = ?, fWarnings = ?, OrigRdg = ?, PresRdg = ?, PresCum = ?, TotalCum = ?, "+"WasRead = ?, NoInput = ?, TimeRead = ?, DateRead = ? "+"WHERE ReadID = "+BA.NumberToString(_ireadid)+" "+"And AcctID = "+BA.NumberToString(_iacctid);
 //BA.debugLineNum = 2290;BA.debugLine="Starter.DBCon.ExecNonQuery2(Starter.strCriteria";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2(mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{("1"),BA.NumberToString(_imisscodeid),_smisscodedesc,_findings,_swarning,mostCurrent._txtpresrdg.getText(),mostCurrent._txtpresrdg.getText(),mostCurrent._lblprescum.getText(),BA.NumberToString(_totalcum),("1"),BA.NumberToString(_noinput),mostCurrent._timeread,mostCurrent._dateread}));
 //BA.debugLineNum = 2293;BA.debugLine="snack.Initialize(\"\", Activity, $\"Miss Code Entr";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Miss Code Entry was successfully updated"),mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 2294;BA.debugLine="SetSnackBarBackground(snack, GlobalVar.PriColor";
_setsnackbarbackground(mostCurrent._snack,(int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 2295;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 2296;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 2297;BA.debugLine="blnEdit = False";
_blnedit = anywheresoftware.b4a.keywords.Common.False;
 }else {
 //BA.debugLineNum = 2299;BA.debugLine="NewSeqNo = DBaseFunctions.GetSeqNo(GlobalVar.Bo";
_newseqno = mostCurrent._dbasefunctions._getseqno /*int*/ (mostCurrent.activityBA,mostCurrent._globalvar._bookid /*int*/ );
 //BA.debugLineNum = 2300;BA.debugLine="Starter.strCriteria=\"UPDATE tblReadings \" & _";
mostCurrent._starter._strcriteria /*String*/  = "UPDATE tblReadings "+"SET WasMissCoded = ?, MissCodeID = ?, MissCode = ?, fFindings = ?, fWarnings = ?, OrigRdg = ?, PresRdg = ?, PresCum = ?, TotalCum = ?, MissCoded = ?, "+"WasRead = ?, NewSeqNo = ?, NoInput = ?, TimeRead = ?, DateRead = ? "+"WHERE ReadID = "+BA.NumberToString(_ireadid)+" "+"And AcctID = "+BA.NumberToString(_iacctid);
 //BA.debugLineNum = 2306;BA.debugLine="Starter.DBCon.ExecNonQuery2(Starter.strCriteria";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2(mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{("1"),BA.NumberToString(_imisscodeid),_smisscodedesc,_findings,_swarning,mostCurrent._txtpresrdg.getText(),mostCurrent._txtpresrdg.getText(),mostCurrent._lblprescum.getText(),BA.NumberToString(_totalcum),("1"),("1"),BA.NumberToString(_newseqno),BA.NumberToString(_noinput),mostCurrent._timeread,mostCurrent._dateread}));
 //BA.debugLineNum = 2308;BA.debugLine="snack.Initialize(\"\", Activity, $\"Miss Code Entr";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Miss Code Entry was successfully saved!"),mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 2309;BA.debugLine="SetSnackBarBackground(snack, GlobalVar.PriColor";
_setsnackbarbackground(mostCurrent._snack,(int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 2310;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 2311;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 };
 //BA.debugLineNum = 2313;BA.debugLine="Starter.DBCon.TransactionSuccessful";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .TransactionSuccessful();
 } 
       catch (Exception e44) {
			processBA.setLastException(e44); //BA.debugLineNum = 2315;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("125428029",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 2317;BA.debugLine="Starter.DBCon.EndTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .EndTransaction();
 //BA.debugLineNum = 2318;BA.debugLine="End Sub";
return "";
}
public static String  _savenegativereading(int _imisscodeid,String _smisscodedesc,int _ireadid,int _iacctid) throws Exception{
long _lngdatetime = 0L;
String _sfindings = "";
String _swarning = "";
 //BA.debugLineNum = 3616;BA.debugLine="Private Sub SaveNegativeReading(iMissCodeID As Int";
 //BA.debugLineNum = 3617;BA.debugLine="Dim lngDateTime As Long";
_lngdatetime = 0L;
 //BA.debugLineNum = 3618;BA.debugLine="Dim sFindings As String";
_sfindings = "";
 //BA.debugLineNum = 3619;BA.debugLine="Dim sWarning As String";
_swarning = "";
 //BA.debugLineNum = 3621;BA.debugLine="lngDateTime = DateTime.Now";
_lngdatetime = anywheresoftware.b4a.keywords.Common.DateTime.getNow();
 //BA.debugLineNum = 3622;BA.debugLine="DateTime.TimeFormat = \"HH:mm:ss\"";
anywheresoftware.b4a.keywords.Common.DateTime.setTimeFormat("HH:mm:ss");
 //BA.debugLineNum = 3623;BA.debugLine="TimeRead = DateTime.Time(lngDateTime)";
mostCurrent._timeread = anywheresoftware.b4a.keywords.Common.DateTime.Time(_lngdatetime);
 //BA.debugLineNum = 3624;BA.debugLine="DateTime.DateFormat = \"MM/dd/yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("MM/dd/yyyy");
 //BA.debugLineNum = 3625;BA.debugLine="DateRead = DateTime.Date(lngDateTime)";
mostCurrent._dateread = anywheresoftware.b4a.keywords.Common.DateTime.Date(_lngdatetime);
 //BA.debugLineNum = 3627;BA.debugLine="NoInput = NoInput + 1";
_noinput = (int) (_noinput+1);
 //BA.debugLineNum = 3628;BA.debugLine="sFindings = \"Negative Consumption\"";
_sfindings = "Negative Consumption";
 //BA.debugLineNum = 3629;BA.debugLine="sWarning = \"Negative Consumption\"";
_swarning = "Negative Consumption";
 //BA.debugLineNum = 3631;BA.debugLine="Try";
try { //BA.debugLineNum = 3632;BA.debugLine="Starter.DBCon.BeginTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .BeginTransaction();
 //BA.debugLineNum = 3633;BA.debugLine="If blnEdit = True Then";
if (_blnedit==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 3634;BA.debugLine="Starter.strCriteria=\"UPDATE tblReadings \" & _";
mostCurrent._starter._strcriteria /*String*/  = "UPDATE tblReadings "+"SET WasMissCoded = ?, MissCodeID = ?, MissCode = ?, fFindings = ?, fWarnings = ?, OrigRdg = ?, PresRdg = ?, "+"WasRead = ?, NoInput = ?, TimeRead = ?, DateRead = ? "+"WHERE ReadID = "+BA.NumberToString(_ireadid)+" "+"And AcctID = "+BA.NumberToString(_iacctid);
 //BA.debugLineNum = 3640;BA.debugLine="Starter.DBCon.ExecNonQuery2(Starter.strCriteria";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2(mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{("1"),BA.NumberToString(_imisscodeid),_smisscodedesc,_sfindings,_swarning,mostCurrent._txtpresrdg.getText(),mostCurrent._txtpresrdg.getText(),("1"),BA.NumberToString(_noinput),mostCurrent._timeread,mostCurrent._dateread}));
 //BA.debugLineNum = 3643;BA.debugLine="snack.Initialize(\"\", Activity, $\"Miss Code Entr";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Miss Code Entry was successfully updated"),mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 3644;BA.debugLine="SetSnackBarBackground(snack, GlobalVar.PriColor";
_setsnackbarbackground(mostCurrent._snack,(int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 3645;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 3646;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 3647;BA.debugLine="blnEdit = False";
_blnedit = anywheresoftware.b4a.keywords.Common.False;
 }else {
 //BA.debugLineNum = 3649;BA.debugLine="NewSeqNo = DBaseFunctions.GetSeqNo(GlobalVar.Bo";
_newseqno = mostCurrent._dbasefunctions._getseqno /*int*/ (mostCurrent.activityBA,mostCurrent._globalvar._bookid /*int*/ );
 //BA.debugLineNum = 3650;BA.debugLine="Starter.strCriteria=\"UPDATE tblReadings \" & _";
mostCurrent._starter._strcriteria /*String*/  = "UPDATE tblReadings "+"SET WasMissCoded = ?, MissCodeID = ?, MissCode = ?, fFindings = ?, fWarnings = ?, OrigRdg = ?, PresRdg = ?, MissCoded = ?, "+"WasRead = ?, NewSeqNo = ?, NoInput = ?, TimeRead = ?, DateRead = ? "+"WHERE ReadID = "+BA.NumberToString(_ireadid)+" "+"And AcctID = "+BA.NumberToString(_iacctid);
 //BA.debugLineNum = 3656;BA.debugLine="Starter.DBCon.ExecNonQuery2(Starter.strCriteria";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2(mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{("1"),BA.NumberToString(_imisscodeid),_smisscodedesc,_sfindings,_swarning,mostCurrent._txtpresrdg.getText(),mostCurrent._txtpresrdg.getText(),("1"),("1"),BA.NumberToString(_newseqno),BA.NumberToString(_noinput),mostCurrent._timeread,mostCurrent._dateread}));
 //BA.debugLineNum = 3658;BA.debugLine="snack.Initialize(\"\", Activity, $\"Miss Code Entr";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Miss Code Entry was successfully saved!"),mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 3659;BA.debugLine="SetSnackBarBackground(snack, GlobalVar.PriColor";
_setsnackbarbackground(mostCurrent._snack,(int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 3660;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 3661;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 };
 //BA.debugLineNum = 3663;BA.debugLine="Starter.DBCon.TransactionSuccessful";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .TransactionSuccessful();
 } 
       catch (Exception e33) {
			processBA.setLastException(e33); //BA.debugLineNum = 3665;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("127328561",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 3667;BA.debugLine="Starter.DBCon.EndTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .EndTransaction();
 //BA.debugLineNum = 3668;BA.debugLine="End Sub";
return "";
}
public static String  _savenewremarks(String _sremarks,int _ireadid,int _iacctid) throws Exception{
int _intwasblank = 0;
 //BA.debugLineNum = 2425;BA.debugLine="Private Sub SaveNewRemarks(sRemarks As String, iRe";
 //BA.debugLineNum = 2426;BA.debugLine="Dim intWasBlank As Int";
_intwasblank = 0;
 //BA.debugLineNum = 2428;BA.debugLine="Try";
try { //BA.debugLineNum = 2429;BA.debugLine="NoInput = NoInput + 1";
_noinput = (int) (_noinput+1);
 //BA.debugLineNum = 2430;BA.debugLine="Starter.DBCon.BeginTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .BeginTransaction();
 //BA.debugLineNum = 2431;BA.debugLine="Starter.strCriteria=\"UPDATE tblReadings \" & _";
mostCurrent._starter._strcriteria /*String*/  = "UPDATE tblReadings "+"SET ReadRemarks = ?, NoInput = ? "+"WHERE ReadID = "+BA.NumberToString(_ireadid)+" "+"AND AcctID = "+BA.NumberToString(_iacctid);
 //BA.debugLineNum = 2436;BA.debugLine="Starter.DBCon.ExecNonQuery2(Starter.strCriteria,";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2(mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{_sremarks,BA.NumberToString(_noinput)}));
 //BA.debugLineNum = 2437;BA.debugLine="Starter.DBCon.TransactionSuccessful";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .TransactionSuccessful();
 //BA.debugLineNum = 2438;BA.debugLine="If blnEdit = True Then";
if (_blnedit==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2439;BA.debugLine="snack.Initialize(\"\", Activity, $\"Remarks Entry";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Remarks Entry was successfully updated!"),mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 2440;BA.debugLine="SetSnackBarBackground(snack, GlobalVar.PriColor";
_setsnackbarbackground(mostCurrent._snack,(int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 2441;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 2442;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 }else {
 //BA.debugLineNum = 2444;BA.debugLine="snack.Initialize(\"\", Activity, $\"Remarks Entry";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Remarks Entry was successfully added!"),mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 2445;BA.debugLine="SetSnackBarBackground(snack, GlobalVar.PriColor";
_setsnackbarbackground(mostCurrent._snack,(int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 2446;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 2447;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 };
 } 
       catch (Exception e20) {
			processBA.setLastException(e20); //BA.debugLineNum = 2450;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("125886745",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 2452;BA.debugLine="Starter.DBCon.EndTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .EndTransaction();
 //BA.debugLineNum = 2453;BA.debugLine="End Sub";
return "";
}
public static String  _savereading(int _iprintstatus,int _ireadid,int _iacctid) throws Exception{
long _lngdatetime = 0L;
String _swarning = "";
int _minimumrangeto = 0;
int _dheaderid = 0;
anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper _bbasicamt = null;
anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper _bpcaamt = null;
anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper _bcurrentamt = null;
anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper _bsenioronbeforeamt = null;
anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper _btotaldueamtbeforesc = null;
anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper _bdiscamt = null;
anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper _bmetercharges = null;
anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper _bfranchisetaxamt = null;
anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper _bseptagefee = null;
anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper _bseptagefeeamt = null;
anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper _btotaldueamt = null;
anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper _bnewamt = null;
anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper _bseniorafteramt = null;
anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper _bpenaltyamt = null;
anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper _btotaldueamtaftersc = null;
String _sbasicamt = "";
String _spcaamt = "";
String _scurrentamt = "";
String _ssenioronbeforeamt = "";
String _stotaldueamtbeforesc = "";
String _sdiscamt = "";
String _smetercharges = "";
String _sfranchisetaxamt = "";
String _sseptagefee = "";
String _sseptagefeeamt = "";
String _stotaldueamt = "";
String _snewamt = "";
String _sseniorafteramt = "";
String _spenaltyamt = "";
String _stotaldueamtaftersc = "";
 //BA.debugLineNum = 2457;BA.debugLine="Private Sub SaveReading(iPrintStatus As Int, iRead";
 //BA.debugLineNum = 2458;BA.debugLine="Dim lngDateTime As Long";
_lngdatetime = 0L;
 //BA.debugLineNum = 2459;BA.debugLine="Dim sWarning As String";
_swarning = "";
 //BA.debugLineNum = 2460;BA.debugLine="Dim MinimumRangeTo As Int";
_minimumrangeto = 0;
 //BA.debugLineNum = 2461;BA.debugLine="Dim dHeaderID As Int";
_dheaderid = 0;
 //BA.debugLineNum = 2463;BA.debugLine="Dim bBasicAmt As BigDecimal";
_bbasicamt = new anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper();
 //BA.debugLineNum = 2464;BA.debugLine="Dim bPCAAmt As BigDecimal";
_bpcaamt = new anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper();
 //BA.debugLineNum = 2465;BA.debugLine="Dim bCurrentAmt As BigDecimal";
_bcurrentamt = new anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper();
 //BA.debugLineNum = 2466;BA.debugLine="Dim bSeniorOnBeforeAmt As BigDecimal";
_bsenioronbeforeamt = new anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper();
 //BA.debugLineNum = 2467;BA.debugLine="Dim bTotalDueAmtBeforeSC As BigDecimal";
_btotaldueamtbeforesc = new anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper();
 //BA.debugLineNum = 2468;BA.debugLine="Dim bDiscAmt As BigDecimal";
_bdiscamt = new anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper();
 //BA.debugLineNum = 2469;BA.debugLine="Dim bMeterCharges As BigDecimal";
_bmetercharges = new anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper();
 //BA.debugLineNum = 2470;BA.debugLine="Dim bFranchiseTaxAmt As BigDecimal";
_bfranchisetaxamt = new anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper();
 //BA.debugLineNum = 2472;BA.debugLine="Dim bSeptageFee As BigDecimal";
_bseptagefee = new anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper();
 //BA.debugLineNum = 2473;BA.debugLine="Dim bSeptageFeeAmt As BigDecimal";
_bseptagefeeamt = new anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper();
 //BA.debugLineNum = 2475;BA.debugLine="Dim bTotalDueAmt As BigDecimal";
_btotaldueamt = new anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper();
 //BA.debugLineNum = 2476;BA.debugLine="Dim bNewAmt As BigDecimal";
_bnewamt = new anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper();
 //BA.debugLineNum = 2478;BA.debugLine="Dim bSeniorAfterAmt As BigDecimal";
_bseniorafteramt = new anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper();
 //BA.debugLineNum = 2479;BA.debugLine="Dim bPenaltyAmt As BigDecimal";
_bpenaltyamt = new anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper();
 //BA.debugLineNum = 2480;BA.debugLine="Dim bTotalDueAmtAfterSC As BigDecimal";
_btotaldueamtaftersc = new anywheresoftware.b4a.agraham.bignumbers.BigDecimalWrapper();
 //BA.debugLineNum = 2482;BA.debugLine="Dim sBasicAmt As String";
_sbasicamt = "";
 //BA.debugLineNum = 2483;BA.debugLine="Dim sPCAAmt As String";
_spcaamt = "";
 //BA.debugLineNum = 2484;BA.debugLine="Dim sCurrentAmt As String";
_scurrentamt = "";
 //BA.debugLineNum = 2485;BA.debugLine="Dim sSeniorOnBeforeAmt As String";
_ssenioronbeforeamt = "";
 //BA.debugLineNum = 2486;BA.debugLine="Dim sTotalDueAmtBeforeSC As String";
_stotaldueamtbeforesc = "";
 //BA.debugLineNum = 2487;BA.debugLine="Dim sDiscAmt As String";
_sdiscamt = "";
 //BA.debugLineNum = 2488;BA.debugLine="Dim sMeterCharges As String";
_smetercharges = "";
 //BA.debugLineNum = 2489;BA.debugLine="Dim sFranchiseTaxAmt As String";
_sfranchisetaxamt = "";
 //BA.debugLineNum = 2490;BA.debugLine="Dim sSeptageFee As String";
_sseptagefee = "";
 //BA.debugLineNum = 2491;BA.debugLine="Dim sSeptageFeeAmt As String";
_sseptagefeeamt = "";
 //BA.debugLineNum = 2492;BA.debugLine="Dim sTotalDueAmt As String";
_stotaldueamt = "";
 //BA.debugLineNum = 2493;BA.debugLine="Dim sNewAmt As String";
_snewamt = "";
 //BA.debugLineNum = 2495;BA.debugLine="Dim sSeniorAfterAmt As String";
_sseniorafteramt = "";
 //BA.debugLineNum = 2496;BA.debugLine="Dim sPenaltyAmt As String";
_spenaltyamt = "";
 //BA.debugLineNum = 2497;BA.debugLine="Dim sTotalDueAmtAfterSC As String";
_stotaldueamtaftersc = "";
 //BA.debugLineNum = 2503;BA.debugLine="lngDateTime = DateTime.Now";
_lngdatetime = anywheresoftware.b4a.keywords.Common.DateTime.getNow();
 //BA.debugLineNum = 2504;BA.debugLine="DateTime.TimeFormat = \"HH:mm:ss\"";
anywheresoftware.b4a.keywords.Common.DateTime.setTimeFormat("HH:mm:ss");
 //BA.debugLineNum = 2505;BA.debugLine="TimeRead = DateTime.Time(lngDateTime)";
mostCurrent._timeread = anywheresoftware.b4a.keywords.Common.DateTime.Time(_lngdatetime);
 //BA.debugLineNum = 2506;BA.debugLine="DateTime.DateFormat = \"MM/dd/yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("MM/dd/yyyy");
 //BA.debugLineNum = 2507;BA.debugLine="DateRead = DateTime.Date(lngDateTime)";
mostCurrent._dateread = anywheresoftware.b4a.keywords.Common.DateTime.Date(_lngdatetime);
 //BA.debugLineNum = 2509;BA.debugLine="LogColor($\"Date Read: \"$ & DateRead, Colors.Yello";
anywheresoftware.b4a.keywords.Common.LogImpl("125952308",("Date Read: ")+mostCurrent._dateread,anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 //BA.debugLineNum = 2510;BA.debugLine="LogColor($\"Time Read: \"$ & TimeRead, Colors.Magen";
anywheresoftware.b4a.keywords.Common.LogImpl("125952309",("Time Read: ")+mostCurrent._timeread,anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 2514;BA.debugLine="CurrentCuM = strSF.Val(txtPresRdg.Text) - strSF.V";
_currentcum = (int) (mostCurrent._strsf._vvvvvvv6(mostCurrent._txtpresrdg.getText())-mostCurrent._strsf._vvvvvvv6(BA.NumberToString(_prevrdg)));
 //BA.debugLineNum = 2516;BA.debugLine="If CurrentCuM < 0 Then";
if (_currentcum<0) { 
 //BA.debugLineNum = 2517;BA.debugLine="WarningMsg($\"E R R O R\"$, $\"Unable to save this";
_warningmsg(("E R R O R"),("Unable to save this reading at this time..."));
 //BA.debugLineNum = 2518;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 2521;BA.debugLine="ImplosiveType = GetImplosiveType(PrevCum, Current";
mostCurrent._implosivetype = _getimplosivetype((int)(Double.parseDouble(mostCurrent._prevcum)),_currentcum);
 //BA.debugLineNum = 2522;BA.debugLine="sWarning = GetWarning(PrevCum, CurrentCuM)";
_swarning = _getwarning((int)(Double.parseDouble(mostCurrent._prevcum)),_currentcum);
 //BA.debugLineNum = 2524;BA.debugLine="NoInput = NoInput + 1";
_noinput = (int) (_noinput+1);
 //BA.debugLineNum = 2525;BA.debugLine="WasRead = 1";
_wasread = (int) (1);
 //BA.debugLineNum = 2526;BA.debugLine="PrintStatus = iPrintStatus";
_printstatus = _iprintstatus;
 //BA.debugLineNum = 2528;BA.debugLine="If iPrintStatus = 1 Then";
if (_iprintstatus==1) { 
 //BA.debugLineNum = 2529;BA.debugLine="NoPrinted = NoPrinted + 1";
_noprinted = (int) (_noprinted+1);
 //BA.debugLineNum = 2530;BA.debugLine="lngDateTime = DateTime.Now";
_lngdatetime = anywheresoftware.b4a.keywords.Common.DateTime.getNow();
 //BA.debugLineNum = 2531;BA.debugLine="DateTime.DateFormat = \"yyyy-MM-dd hh:mm:ss a\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("yyyy-MM-dd hh:mm:ss a");
 //BA.debugLineNum = 2532;BA.debugLine="PrintTime = DateTime.Date(lngDateTime)";
mostCurrent._printtime = anywheresoftware.b4a.keywords.Common.DateTime.Date(_lngdatetime);
 }else {
 //BA.debugLineNum = 2534;BA.debugLine="NoPrinted = NoPrinted";
_noprinted = _noprinted;
 };
 //BA.debugLineNum = 2539;BA.debugLine="TotalCum = 0";
_totalcum = (int) (0);
 //BA.debugLineNum = 2540;BA.debugLine="BasicAmt = 0";
_basicamt = 0;
 //BA.debugLineNum = 2541;BA.debugLine="PCAAmt = 0";
_pcaamt = 0;
 //BA.debugLineNum = 2542;BA.debugLine="CurrentAmt = 0";
_currentamt = 0;
 //BA.debugLineNum = 2543;BA.debugLine="SeniorOnBeforeAmt = 0";
_senioronbeforeamt = 0;
 //BA.debugLineNum = 2544;BA.debugLine="TotalDueAmtBeforeSC = 0";
_totaldueamtbeforesc = 0;
 //BA.debugLineNum = 2545;BA.debugLine="DiscAmt = 0";
_discamt = 0;
 //BA.debugLineNum = 2546;BA.debugLine="FranchiseTaxAmt = 0";
_franchisetaxamt = 0;
 //BA.debugLineNum = 2548;BA.debugLine="SeptageFee = 0";
_septagefee = 0;
 //BA.debugLineNum = 2549;BA.debugLine="SeptageFeeAmt = 0";
_septagefeeamt = 0;
 //BA.debugLineNum = 2550;BA.debugLine="TotalDueAmt = 0";
_totaldueamt = 0;
 //BA.debugLineNum = 2552;BA.debugLine="SeniorAfterAmt = 0";
_seniorafteramt = 0;
 //BA.debugLineNum = 2553;BA.debugLine="PenaltyAmt = 0";
_penaltyamt = 0;
 //BA.debugLineNum = 2554;BA.debugLine="TotalDueAmtAfterSC = 0";
_totaldueamtaftersc = 0;
 //BA.debugLineNum = 2555;BA.debugLine="RemainingAdvPayment = 0";
_remainingadvpayment = 0;
 //BA.debugLineNum = 2559;BA.debugLine="TotalCum = strSF.Val(lblPresCum.Text) + AddCons";
_totalcum = (int) (mostCurrent._strsf._vvvvvvv6(mostCurrent._lblprescum.getText())+_addcons);
 //BA.debugLineNum = 2561;BA.debugLine="BasicAmt = DBaseFunctions.ComputeBasicAmt(strSF.V";
_basicamt = mostCurrent._dbasefunctions._computebasicamt /*double*/ (mostCurrent.activityBA,(int) (mostCurrent._strsf._vvvvvvv6(BA.NumberToString(_totalcum))),mostCurrent._globalvar._branchid /*int*/ ,mostCurrent._acctclass,mostCurrent._acctsubclass);
 //BA.debugLineNum = 2564;BA.debugLine="If DBaseFunctions.IsBookWithPCA(GlobalVar.BookID)";
if (mostCurrent._dbasefunctions._isbookwithpca /*boolean*/ (mostCurrent.activityBA,mostCurrent._globalvar._bookid /*int*/ )==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2565;BA.debugLine="If AcctClass = \"REL\" Then";
if ((mostCurrent._acctclass).equals("REL")) { 
 //BA.debugLineNum = 2566;BA.debugLine="If TotalCum <= 10 Then";
if (_totalcum<=10) { 
 //BA.debugLineNum = 2567;BA.debugLine="PCAAmt = 0";
_pcaamt = 0;
 }else {
 //BA.debugLineNum = 2569;BA.debugLine="PCAAmt = (TotalCum - 10) * PCA";
_pcaamt = (_totalcum-10)*_pca;
 };
 }else {
 //BA.debugLineNum = 2572;BA.debugLine="MinimumRangeTo = DBaseFunctions.GetMinimumRange";
_minimumrangeto = (int) (mostCurrent._dbasefunctions._getminimumrangeto /*double*/ (mostCurrent.activityBA,mostCurrent._globalvar._branchid /*int*/ ,mostCurrent._acctclass,mostCurrent._acctsubclass,BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ )+"-"+BA.NumberToString(mostCurrent._globalvar._billmonth /*int*/ )+"-01"));
 //BA.debugLineNum = 2574;BA.debugLine="If TotalCum <= MinimumRangeTo Then";
if (_totalcum<=_minimumrangeto) { 
 //BA.debugLineNum = 2575;BA.debugLine="PCAAmt = MinimumRangeTo * PCA";
_pcaamt = _minimumrangeto*_pca;
 }else {
 //BA.debugLineNum = 2577;BA.debugLine="PCAAmt = TotalCum * PCA";
_pcaamt = _totalcum*_pca;
 };
 };
 }else {
 //BA.debugLineNum = 2581;BA.debugLine="PCAAmt = 0";
_pcaamt = 0;
 };
 //BA.debugLineNum = 2585;BA.debugLine="CurrentAmt = BasicAmt + PCAAmt";
_currentamt = _basicamt+_pcaamt;
 //BA.debugLineNum = 2588;BA.debugLine="If IsSenior = 1 And AcctClass = \"RES\" Then";
if (_issenior==1 && (mostCurrent._acctclass).equals("RES")) { 
 //BA.debugLineNum = 2589;BA.debugLine="SeniorOnBeforeAmt = GetSeniorBefore(ReadID, Tota";
_senioronbeforeamt = _getseniorbefore(_readid,_totalcum);
 //BA.debugLineNum = 2590;BA.debugLine="SeniorAfterAmt = GetSeniorAfter(ReadID, TotalCum";
_seniorafteramt = _getseniorafter(_readid,_totalcum);
 }else {
 //BA.debugLineNum = 2592;BA.debugLine="SeniorAfterAmt = 0";
_seniorafteramt = 0;
 //BA.debugLineNum = 2593;BA.debugLine="SeniorOnBeforeAmt = 0";
_senioronbeforeamt = 0;
 };
 //BA.debugLineNum = 2596;BA.debugLine="TotalDueAmtBeforeSC = CurrentAmt - SeniorOnBefore";
_totaldueamtbeforesc = _currentamt-_senioronbeforeamt;
 //BA.debugLineNum = 2599;BA.debugLine="FranchiseTaxAmt = (CurrentAmt * FranchiseTaxPct)";
_franchisetaxamt = (_currentamt*_franchisetaxpct);
 //BA.debugLineNum = 2602;BA.debugLine="GlobalVar.SeptageRateID = 0";
mostCurrent._globalvar._septagerateid /*int*/  = (int) (0);
 //BA.debugLineNum = 2603;BA.debugLine="GlobalVar.SeptageRatePerCuM = 0";
mostCurrent._globalvar._septageratepercum /*double*/  = 0;
 //BA.debugLineNum = 2604;BA.debugLine="GlobalVar.SeptageMinCuM = 0";
mostCurrent._globalvar._septagemincum /*int*/  = (int) (0);
 //BA.debugLineNum = 2605;BA.debugLine="GlobalVar.SeptageMaxCuM = 0";
mostCurrent._globalvar._septagemaxcum /*int*/  = (int) (0);
 //BA.debugLineNum = 2606;BA.debugLine="GlobalVar.SeptageRateType = \"amount\"";
mostCurrent._globalvar._septageratetype /*String*/  = "amount";
 //BA.debugLineNum = 2607;BA.debugLine="dSeptageRate = 0";
_dseptagerate = 0;
 //BA.debugLineNum = 2609;BA.debugLine="If HasSeptageFee = 1 Then";
if (_hasseptagefee==1) { 
 //BA.debugLineNum = 2610;BA.debugLine="If GlobalVar.BranchID = 14 Or GlobalVar.BranchID";
if (mostCurrent._globalvar._branchid /*int*/ ==14 || mostCurrent._globalvar._branchid /*int*/ ==62 || mostCurrent._globalvar._branchid /*int*/ ==28 || mostCurrent._globalvar._branchid /*int*/ ==29 || mostCurrent._globalvar._branchid /*int*/ ==30) { 
 //BA.debugLineNum = 2611;BA.debugLine="LogColor ($\"Acct Classification: \"$ & AcctClass";
anywheresoftware.b4a.keywords.Common.LogImpl("125952410",("Acct Classification: ")+mostCurrent._acctclassification,anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 2612;BA.debugLine="GlobalVar.SeptageRateID = DBaseFunctions.GetSep";
mostCurrent._globalvar._septagerateid /*int*/  = mostCurrent._dbasefunctions._getseptagerateid /*int*/ (mostCurrent.activityBA,mostCurrent._acctclassification,mostCurrent._globalvar._branchid /*int*/ );
 //BA.debugLineNum = 2613;BA.debugLine="LogColor ($\"Septage Rate ID: \"$ & GlobalVar.Sep";
anywheresoftware.b4a.keywords.Common.LogImpl("125952412",("Septage Rate ID: ")+BA.NumberToString(mostCurrent._globalvar._septagerateid /*int*/ ),anywheresoftware.b4a.keywords.Common.Colors.Cyan);
 //BA.debugLineNum = 2615;BA.debugLine="If GlobalVar.SeptageRateID = 0 Then";
if (mostCurrent._globalvar._septagerateid /*int*/ ==0) { 
 //BA.debugLineNum = 2616;BA.debugLine="WarningMsg($\"NO SEPTAGE RATES FOUND!\"$, $\"Cust";
_warningmsg(("NO SEPTAGE RATES FOUND!"),("Customer account classification has no septage rates found!")+anywheresoftware.b4a.keywords.Common.CRLF+("Please ask assistance to the Central Billing & IT Department regarding this."));
 //BA.debugLineNum = 2617;BA.debugLine="Return";
if (true) return "";
 }else {
 //BA.debugLineNum = 2619;BA.debugLine="DBaseFunctions.GetSeptageRateDetails(GlobalVar";
mostCurrent._dbasefunctions._getseptageratedetails /*String*/ (mostCurrent.activityBA,mostCurrent._globalvar._branchid /*int*/ ,mostCurrent._globalvar._septagerateid /*int*/ ,mostCurrent._acctclassification);
 //BA.debugLineNum = 2620;BA.debugLine="If GlobalVar.SeptageRateType = \"amount\" Then";
if ((mostCurrent._globalvar._septageratetype /*String*/ ).equals("amount")) { 
 //BA.debugLineNum = 2621;BA.debugLine="If TotalCum <= GlobalVar.SeptageMinCuM Then";
if (_totalcum<=mostCurrent._globalvar._septagemincum /*int*/ ) { 
 //BA.debugLineNum = 2622;BA.debugLine="SeptageFee = GlobalVar.SeptageRatePerCuM * G";
_septagefee = mostCurrent._globalvar._septageratepercum /*double*/ *mostCurrent._globalvar._septagemincum /*int*/ ;
 }else if(_totalcum>=mostCurrent._globalvar._septagemaxcum /*int*/ ) { 
 //BA.debugLineNum = 2625;BA.debugLine="SeptageFee = GlobalVar.SeptageRatePerCuM * G";
_septagefee = mostCurrent._globalvar._septageratepercum /*double*/ *mostCurrent._globalvar._septagemaxcum /*int*/ ;
 }else {
 //BA.debugLineNum = 2628;BA.debugLine="SeptageFee = GlobalVar.SeptageRatePerCuM * T";
_septagefee = mostCurrent._globalvar._septageratepercum /*double*/ *_totalcum;
 };
 }else {
 //BA.debugLineNum = 2631;BA.debugLine="SeptageFee = (GlobalVar.SeptageRatePerCuM) *";
_septagefee = (mostCurrent._globalvar._septageratepercum /*double*/ )*_currentamt;
 };
 };
 }else {
 //BA.debugLineNum = 2637;BA.debugLine="If TotalCum <= MinSeptageCum Then";
if (_totalcum<=_minseptagecum) { 
 //BA.debugLineNum = 2638;BA.debugLine="SeptageFee = SeptageRate * MinSeptageCum";
_septagefee = _septagerate*_minseptagecum;
 }else if(_totalcum>=_maxseptagecum) { 
 //BA.debugLineNum = 2640;BA.debugLine="SeptageFee = SeptageRate * MaxSeptageCum";
_septagefee = _septagerate*_maxseptagecum;
 }else {
 //BA.debugLineNum = 2642;BA.debugLine="SeptageFee = SeptageRate * TotalCum";
_septagefee = _septagerate*_totalcum;
 };
 };
 }else {
 //BA.debugLineNum = 2646;BA.debugLine="SeptageFee = 0";
_septagefee = 0;
 };
 //BA.debugLineNum = 2660;BA.debugLine="LogColor($\"Septage Rate: \"$ & SeptageRate, Colors";
anywheresoftware.b4a.keywords.Common.LogImpl("125952459",("Septage Rate: ")+BA.NumberToString(_septagerate),anywheresoftware.b4a.keywords.Common.Colors.Cyan);
 //BA.debugLineNum = 2661;BA.debugLine="SeptageFeeAmt = SeptageFee";
_septagefeeamt = _septagefee;
 //BA.debugLineNum = 2664;BA.debugLine="bSeptageFee.Initialize(SeptageFee)";
_bseptagefee.Initialize(BA.NumberToString(_septagefee));
 //BA.debugLineNum = 2665;BA.debugLine="bSeptageFee = RoundtoCurrency(bSeptageFee, 2)";
_bseptagefee = _roundtocurrency(_bseptagefee,(int) (2));
 //BA.debugLineNum = 2666;BA.debugLine="sSeptageFee = NumberFormat2(bSeptageFee.ToPlainSt";
_sseptagefee = anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(_bseptagefee.ToPlainString())),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2667;BA.debugLine="LogColor($\"ORIG SEPTAGE AND SEWERAGE: \"$ & sSepta";
anywheresoftware.b4a.keywords.Common.LogImpl("125952466",("ORIG SEPTAGE AND SEWERAGE: ")+_sseptagefee,anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 //BA.debugLineNum = 2669;BA.debugLine="bBasicAmt.Initialize(BasicAmt)";
_bbasicamt.Initialize(BA.NumberToString(_basicamt));
 //BA.debugLineNum = 2670;BA.debugLine="bBasicAmt = RoundtoCurrency(bBasicAmt, 2)";
_bbasicamt = _roundtocurrency(_bbasicamt,(int) (2));
 //BA.debugLineNum = 2671;BA.debugLine="sBasicAmt = NumberFormat2(bBasicAmt.ToPlainString";
_sbasicamt = anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(_bbasicamt.ToPlainString())),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2672;BA.debugLine="LogColor($\"Basic: \"$ & sBasicAmt, Colors.White)";
anywheresoftware.b4a.keywords.Common.LogImpl("125952471",("Basic: ")+_sbasicamt,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 2674;BA.debugLine="bPCAAmt.Initialize(PCAAmt)";
_bpcaamt.Initialize(BA.NumberToString(_pcaamt));
 //BA.debugLineNum = 2675;BA.debugLine="bPCAAmt = RoundtoCurrency(bPCAAmt, 2)";
_bpcaamt = _roundtocurrency(_bpcaamt,(int) (2));
 //BA.debugLineNum = 2676;BA.debugLine="sPCAAmt = NumberFormat2(bPCAAmt.ToPlainString, 1,";
_spcaamt = anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(_bpcaamt.ToPlainString())),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2677;BA.debugLine="LogColor($\"PCA: \"$ & sPCAAmt, Colors.White)";
anywheresoftware.b4a.keywords.Common.LogImpl("125952476",("PCA: ")+_spcaamt,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 2679;BA.debugLine="bCurrentAmt.Initialize(CurrentAmt)";
_bcurrentamt.Initialize(BA.NumberToString(_currentamt));
 //BA.debugLineNum = 2680;BA.debugLine="bCurrentAmt = RoundtoCurrency(bCurrentAmt, 2)";
_bcurrentamt = _roundtocurrency(_bcurrentamt,(int) (2));
 //BA.debugLineNum = 2681;BA.debugLine="sCurrentAmt = NumberFormat2(bCurrentAmt.ToPlainSt";
_scurrentamt = anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(_bcurrentamt.ToPlainString())),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2682;BA.debugLine="LogColor($\"CURENT BILL: \"$ & sCurrentAmt, Colors.";
anywheresoftware.b4a.keywords.Common.LogImpl("125952481",("CURENT BILL: ")+_scurrentamt,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 2684;BA.debugLine="bSeniorOnBeforeAmt.Initialize(SeniorOnBeforeAmt)";
_bsenioronbeforeamt.Initialize(BA.NumberToString(_senioronbeforeamt));
 //BA.debugLineNum = 2685;BA.debugLine="bSeniorOnBeforeAmt = RoundtoCurrency(bSeniorOnBef";
_bsenioronbeforeamt = _roundtocurrency(_bsenioronbeforeamt,(int) (2));
 //BA.debugLineNum = 2686;BA.debugLine="sSeniorOnBeforeAmt = NumberFormat2(bSeniorOnBefor";
_ssenioronbeforeamt = anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(_bsenioronbeforeamt.ToPlainString())),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2687;BA.debugLine="LogColor($\"SC DISC: \"$ & sSeniorOnBeforeAmt, Colo";
anywheresoftware.b4a.keywords.Common.LogImpl("125952486",("SC DISC: ")+_ssenioronbeforeamt,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 2689;BA.debugLine="bSeniorAfterAmt.Initialize(SeniorAfterAmt)";
_bseniorafteramt.Initialize(BA.NumberToString(_seniorafteramt));
 //BA.debugLineNum = 2690;BA.debugLine="bSeniorAfterAmt = RoundtoCurrency(bSeniorAfterAmt";
_bseniorafteramt = _roundtocurrency(_bseniorafteramt,(int) (2));
 //BA.debugLineNum = 2691;BA.debugLine="sSeniorAfterAmt = NumberFormat2(bSeniorAfterAmt.T";
_sseniorafteramt = anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(_bseniorafteramt.ToPlainString())),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2692;BA.debugLine="LogColor($\"SENIOR DISC BEFORE: \"$ & sSeniorAfterA";
anywheresoftware.b4a.keywords.Common.LogImpl("125952491",("SENIOR DISC BEFORE: ")+_sseniorafteramt,anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 //BA.debugLineNum = 2694;BA.debugLine="bTotalDueAmtBeforeSC.Initialize(TotalDueAmtBefore";
_btotaldueamtbeforesc.Initialize(BA.NumberToString(_totaldueamtbeforesc));
 //BA.debugLineNum = 2695;BA.debugLine="bTotalDueAmtBeforeSC = RoundtoCurrency(bTotalDueA";
_btotaldueamtbeforesc = _roundtocurrency(_btotaldueamtbeforesc,(int) (2));
 //BA.debugLineNum = 2696;BA.debugLine="sTotalDueAmtBeforeSC = NumberFormat2(bTotalDueAmt";
_stotaldueamtbeforesc = anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(_btotaldueamtbeforesc.ToPlainString())),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2697;BA.debugLine="LogColor($\"TOTAL DUE BEFORE DUE: \"$ & sTotalDueAm";
anywheresoftware.b4a.keywords.Common.LogImpl("125952496",("TOTAL DUE BEFORE DUE: ")+_stotaldueamtbeforesc,anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 //BA.debugLineNum = 2699;BA.debugLine="bDiscAmt.Initialize(DiscAmt)";
_bdiscamt.Initialize(BA.NumberToString(_discamt));
 //BA.debugLineNum = 2700;BA.debugLine="bDiscAmt = RoundtoCurrency(bDiscAmt, 2)";
_bdiscamt = _roundtocurrency(_bdiscamt,(int) (2));
 //BA.debugLineNum = 2701;BA.debugLine="sDiscAmt = NumberFormat2(bDiscAmt.ToPlainString,";
_sdiscamt = anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(_bdiscamt.ToPlainString())),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2702;BA.debugLine="LogColor($\"DISCOUNT: \"$ & sDiscAmt, Colors.White)";
anywheresoftware.b4a.keywords.Common.LogImpl("125952501",("DISCOUNT: ")+_sdiscamt,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 2704;BA.debugLine="bMeterCharges.Initialize(MeterCharges)";
_bmetercharges.Initialize(BA.NumberToString(_metercharges));
 //BA.debugLineNum = 2705;BA.debugLine="bMeterCharges = RoundtoCurrency(bMeterCharges, 2)";
_bmetercharges = _roundtocurrency(_bmetercharges,(int) (2));
 //BA.debugLineNum = 2706;BA.debugLine="sMeterCharges = NumberFormat2(bMeterCharges.ToPla";
_smetercharges = anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(_bmetercharges.ToPlainString())),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2707;BA.debugLine="LogColor($\"METER CHARGE: \"$ & sMeterCharges, Colo";
anywheresoftware.b4a.keywords.Common.LogImpl("125952506",("METER CHARGE: ")+_smetercharges,anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 //BA.debugLineNum = 2709;BA.debugLine="bFranchiseTaxAmt.Initialize(FranchiseTaxAmt)";
_bfranchisetaxamt.Initialize(BA.NumberToString(_franchisetaxamt));
 //BA.debugLineNum = 2710;BA.debugLine="bFranchiseTaxAmt = RoundtoCurrency(bFranchiseTaxA";
_bfranchisetaxamt = _roundtocurrency(_bfranchisetaxamt,(int) (2));
 //BA.debugLineNum = 2711;BA.debugLine="sFranchiseTaxAmt = NumberFormat2(bFranchiseTaxAmt";
_sfranchisetaxamt = anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(_bfranchisetaxamt.ToPlainString())),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2712;BA.debugLine="LogColor($\"FRANCHISE TAX: \"$ & sFranchiseTaxAmt,";
anywheresoftware.b4a.keywords.Common.LogImpl("125952511",("FRANCHISE TAX: ")+_sfranchisetaxamt,anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 //BA.debugLineNum = 2722;BA.debugLine="bNewAmt.Initialize((sCurrentAmt + AddToBillAmt +";
_bnewamt.Initialize(BA.NumberToString(((double)(Double.parseDouble(_scurrentamt))+_addtobillamt+_arrearsamt+(double)(Double.parseDouble(_smetercharges))+(double)(Double.parseDouble(_sfranchisetaxamt)))-((double)(Double.parseDouble(_ssenioronbeforeamt))+(double)(Double.parseDouble(_sdiscamt)))));
 //BA.debugLineNum = 2723;BA.debugLine="bNewAmt = RoundtoCurrency(bNewAmt,2)";
_bnewamt = _roundtocurrency(_bnewamt,(int) (2));
 //BA.debugLineNum = 2724;BA.debugLine="sNewAmt =  NumberFormat2(bNewAmt.ToPlainString, 1";
_snewamt = anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(_bnewamt.ToPlainString())),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2726;BA.debugLine="LogColor(sNewAmt,Colors.Yellow)";
anywheresoftware.b4a.keywords.Common.LogImpl("125952525",_snewamt,anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 //BA.debugLineNum = 2728;BA.debugLine="If (sNewAmt - AdvPayment) < 0 Then";
if (((double)(Double.parseDouble(_snewamt))-_advpayment)<0) { 
 //BA.debugLineNum = 2729;BA.debugLine="TotalDueAmt = 0";
_totaldueamt = 0;
 }else {
 //BA.debugLineNum = 2731;BA.debugLine="TotalDueAmt = sNewAmt - AdvPayment";
_totaldueamt = (double)(Double.parseDouble(_snewamt))-_advpayment;
 };
 //BA.debugLineNum = 2734;BA.debugLine="If (AdvPayment - sNewAmt) <= 0 Then";
if ((_advpayment-(double)(Double.parseDouble(_snewamt)))<=0) { 
 //BA.debugLineNum = 2735;BA.debugLine="RemainingAdvPayment = 0";
_remainingadvpayment = 0;
 }else {
 //BA.debugLineNum = 2737;BA.debugLine="RemainingAdvPayment = AdvPayment - sNewAmt";
_remainingadvpayment = _advpayment-(double)(Double.parseDouble(_snewamt));
 };
 //BA.debugLineNum = 2746;BA.debugLine="bSeptageFeeAmt.Initialize(SeptageFeeAmt)";
_bseptagefeeamt.Initialize(BA.NumberToString(_septagefeeamt));
 //BA.debugLineNum = 2747;BA.debugLine="bSeptageFeeAmt = RoundtoCurrency(bSeptageFeeAmt,";
_bseptagefeeamt = _roundtocurrency(_bseptagefeeamt,(int) (2));
 //BA.debugLineNum = 2748;BA.debugLine="sSeptageFeeAmt = NumberFormat2(bSeptageFeeAmt.ToP";
_sseptagefeeamt = anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(_bseptagefeeamt.ToPlainString())),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2750;BA.debugLine="LogColor($\"SEPTAGE AND SEWERAGE1: \"$ & SeptageFee";
anywheresoftware.b4a.keywords.Common.LogImpl("125952549",("SEPTAGE AND SEWERAGE1: ")+BA.NumberToString(_septagefee),anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 2751;BA.debugLine="LogColor($\"SEPTAGE AND SEWERAGE: \"$ & sSeptageFee";
anywheresoftware.b4a.keywords.Common.LogImpl("125952550",("SEPTAGE AND SEWERAGE: ")+_sseptagefeeamt,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 2754;BA.debugLine="bTotalDueAmt.Initialize(TotalDueAmt)";
_btotaldueamt.Initialize(BA.NumberToString(_totaldueamt));
 //BA.debugLineNum = 2755;BA.debugLine="bTotalDueAmt = RoundtoCurrency(bTotalDueAmt, 2)";
_btotaldueamt = _roundtocurrency(_btotaldueamt,(int) (2));
 //BA.debugLineNum = 2756;BA.debugLine="sTotalDueAmt = NumberFormat2(bTotalDueAmt.ToPlain";
_stotaldueamt = anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(_btotaldueamt.ToPlainString())),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2757;BA.debugLine="LogColor($\"TOTAL DUE: \"$ & sTotalDueAmt, Colors.W";
anywheresoftware.b4a.keywords.Common.LogImpl("125952556",("TOTAL DUE: ")+_stotaldueamt,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 2759;BA.debugLine="If AdvPayment >= CurrentAmt Then";
if (_advpayment>=_currentamt) { 
 //BA.debugLineNum = 2760;BA.debugLine="PenaltyAmt = 0";
_penaltyamt = 0;
 }else {
 //BA.debugLineNum = 2762;BA.debugLine="PenaltyAmt = CurrentAmt * PenaltyPct";
_penaltyamt = _currentamt*_penaltypct;
 };
 //BA.debugLineNum = 2765;BA.debugLine="bPenaltyAmt.Initialize(PenaltyAmt)";
_bpenaltyamt.Initialize(BA.NumberToString(_penaltyamt));
 //BA.debugLineNum = 2766;BA.debugLine="bPenaltyAmt = RoundtoCurrency(bPenaltyAmt, 2)";
_bpenaltyamt = _roundtocurrency(_bpenaltyamt,(int) (2));
 //BA.debugLineNum = 2767;BA.debugLine="sPenaltyAmt = NumberFormat2(bPenaltyAmt.ToPlainSt";
_spenaltyamt = anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(_bpenaltyamt.ToPlainString())),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2768;BA.debugLine="LogColor($\"PENALTY: \"$ & sPenaltyAmt, Colors.Whit";
anywheresoftware.b4a.keywords.Common.LogImpl("125952567",("PENALTY: ")+_spenaltyamt,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 2771;BA.debugLine="If AdvPayment > ((sCurrentAmt + AddToBillAmt + Ar";
if (_advpayment>(((double)(Double.parseDouble(_scurrentamt))+_addtobillamt+_arrearsamt+(double)(Double.parseDouble(_smetercharges))+(double)(Double.parseDouble(_sfranchisetaxamt)))-((double)(Double.parseDouble(_sseniorafteramt))+(double)(Double.parseDouble(_sdiscamt))))) { 
 //BA.debugLineNum = 2772;BA.debugLine="TotalDueAmtAfterSC = 0";
_totaldueamtaftersc = 0;
 }else {
 //BA.debugLineNum = 2774;BA.debugLine="TotalDueAmtAfterSC = (sCurrentAmt + AddToBillAmt";
_totaldueamtaftersc = ((double)(Double.parseDouble(_scurrentamt))+_addtobillamt+_arrearsamt+(double)(Double.parseDouble(_smetercharges))+(double)(Double.parseDouble(_sfranchisetaxamt)))-(_advpayment+(double)(Double.parseDouble(_sseniorafteramt))+(double)(Double.parseDouble(_sdiscamt)))+(double)(Double.parseDouble(_spenaltyamt));
 };
 //BA.debugLineNum = 2778;BA.debugLine="bTotalDueAmtAfterSC.Initialize(TotalDueAmtAfterSC";
_btotaldueamtaftersc.Initialize(BA.NumberToString(_totaldueamtaftersc));
 //BA.debugLineNum = 2779;BA.debugLine="bTotalDueAmtAfterSC = RoundtoCurrency(bTotalDueAm";
_btotaldueamtaftersc = _roundtocurrency(_btotaldueamtaftersc,(int) (2));
 //BA.debugLineNum = 2780;BA.debugLine="sTotalDueAmtAfterSC = NumberFormat2(bTotalDueAmtA";
_stotaldueamtaftersc = anywheresoftware.b4a.keywords.Common.NumberFormat2((double)(Double.parseDouble(_btotaldueamtaftersc.ToPlainString())),(int) (1),(int) (2),(int) (2),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2781;BA.debugLine="LogColor($\"TOTAL AMT. AFTER DUE: \"$ & sTotalDueAm";
anywheresoftware.b4a.keywords.Common.LogImpl("125952580",("TOTAL AMT. AFTER DUE: ")+_stotaldueamtaftersc,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 2787;BA.debugLine="Starter.DBCon.BeginTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .BeginTransaction();
 //BA.debugLineNum = 2788;BA.debugLine="Try";
try { //BA.debugLineNum = 2789;BA.debugLine="If blnEdit = True Then";
if (_blnedit==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2790;BA.debugLine="Starter.strCriteria = \"UPDATE tblReadings \" & _";
mostCurrent._starter._strcriteria /*String*/  = "UPDATE tblReadings "+"SET PresRdg = ?, PresCum = ?, TotalCum = ?, BasicAmt = ?, PCAAmt = ?, SeptageAmt = ?, SeptageFeeAmt = ?, "+"CurrentAmt = ?, TotalDueAmt = ?, SeniorOnBeforeAmt = ?, SeniorAfterAmt = ?, TotalDueAmtBeforeSC = ?, PenaltyAmt = ?, TotalDueAmtAfterSC = ?, DiscAmt = ?, FranchiseTaxAmt = ?, "+"PrintStatus = ?, NoPrinted = ?, WasMissCoded = ?, MissCodeID = ?, MissCode = ?, WasImplosive = ?, ImplosiveType = ?, ImpID = ?, FFindings = ?, FWarnings = ?, ReadRemarks = ?, "+"BillType = ?, WasRead = ?, NoInput = ?, TimeRead = ?, DateRead = ? "+"WHERE ReadID = "+BA.NumberToString(_ireadid)+" "+"AND AcctID = "+BA.NumberToString(_iacctid);
 //BA.debugLineNum = 2798;BA.debugLine="Starter.DBCon.ExecNonQuery2(Starter.strCriteria";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2(mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{mostCurrent._txtpresrdg.getText(),mostCurrent._lblprescum.getText(),BA.NumberToString(_totalcum),_sbasicamt,_spcaamt,_sseptagefee,_sseptagefeeamt,_scurrentamt,_stotaldueamt,_ssenioronbeforeamt,_sseniorafteramt,_stotaldueamtbeforesc,_spenaltyamt,_stotaldueamtaftersc,_sdiscamt,_sfranchisetaxamt,BA.NumberToString(_printstatus),BA.NumberToString(_noprinted),("0"),("0"),(""),("0"),mostCurrent._implosivetype,("0"),("Actual Reading"),_swarning,mostCurrent._strreadremarks,("RB"),("1"),BA.NumberToString(_noinput),mostCurrent._timeread,mostCurrent._dateread}));
 //BA.debugLineNum = 2802;BA.debugLine="blnEdit = False";
_blnedit = anywheresoftware.b4a.keywords.Common.False;
 }else {
 //BA.debugLineNum = 2804;BA.debugLine="NewSeqNo = DBaseFunctions.GetSeqNo(GlobalVar.Bo";
_newseqno = mostCurrent._dbasefunctions._getseqno /*int*/ (mostCurrent.activityBA,mostCurrent._globalvar._bookid /*int*/ );
 //BA.debugLineNum = 2805;BA.debugLine="Starter.strCriteria = \"UPDATE tblReadings \" & _";
mostCurrent._starter._strcriteria /*String*/  = "UPDATE tblReadings "+"SET OrigRdg = ?, PresRdg = ?, PresCum = ?, TotalCum = ?, BasicAmt = ?, PCAAmt = ?, SeptageAmt = ?, SeptageFeeAmt = ?, "+"CurrentAmt = ?, TotalDueAmt = ?, SeniorOnBeforeAmt = ?, SeniorAfterAmt = ?, TotalDueAmtBeforeSC = ?, PenaltyAmt = ?, TotalDueAmtAfterSC = ?, DiscAmt = ?, FranchiseTaxAmt = ?, "+"PrintStatus = ?, NoPrinted = ?, WasMissCoded = ?, MissCodeID = ?, MissCode = ?, WasImplosive = ?, ImplosiveType = ?, ImpID = ?, FFindings = ?, FWarnings = ?, ReadRemarks = ?, "+"BillType = ?, WasRead = ?, NewSeqNo = ?, NoInput = ?, TimeRead = ?, DateRead = ?, PrintTime "+"WHERE ReadID = "+BA.NumberToString(_ireadid)+" "+"AND AcctID = "+BA.NumberToString(_iacctid);
 //BA.debugLineNum = 2813;BA.debugLine="Starter.DBCon.ExecNonQuery2(Starter.strCriteria";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2(mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{mostCurrent._txtpresrdg.getText(),mostCurrent._txtpresrdg.getText(),mostCurrent._lblprescum.getText(),BA.NumberToString(_totalcum),_sbasicamt,_spcaamt,_sseptagefee,_sseptagefeeamt,_scurrentamt,_stotaldueamt,_ssenioronbeforeamt,_sseniorafteramt,_stotaldueamtbeforesc,_spenaltyamt,_stotaldueamtaftersc,_sdiscamt,_sfranchisetaxamt,BA.NumberToString(_printstatus),BA.NumberToString(_noprinted),("0"),("0"),(""),("0"),mostCurrent._implosivetype,("0"),("Actual Reading"),_swarning,mostCurrent._strreadremarks,("RB"),("1"),BA.NumberToString(_newseqno),BA.NumberToString(_noinput),mostCurrent._timeread,mostCurrent._dateread,mostCurrent._printtime}));
 };
 //BA.debugLineNum = 2818;BA.debugLine="Starter.DBCon.TransactionSuccessful";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .TransactionSuccessful();
 } 
       catch (Exception e241) {
			processBA.setLastException(e241); //BA.debugLineNum = 2821;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("125952620",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 2823;BA.debugLine="Starter.DBCon.EndTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .EndTransaction();
 //BA.debugLineNum = 2824;BA.debugLine="End Sub";
return "";
}
public static String  _searchaccount(int _isearchby,int _ifilterby) throws Exception{
String _sfield1 = "";
String _sfield2 = "";
anywheresoftware.b4a.objects.collections.List _searchlist = null;
int _i = 0;
com.bwsi.MeterReader.searchview._item _it = null;
 //BA.debugLineNum = 1850;BA.debugLine="Private Sub SearchAccount(iSearchBy As Int, iFilte";
 //BA.debugLineNum = 1851;BA.debugLine="Dim sField1, sField2 As String";
_sfield1 = "";
_sfield2 = "";
 //BA.debugLineNum = 1852;BA.debugLine="Dim SearchList As List";
_searchlist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 1854;BA.debugLine="SearchList.Initialize";
_searchlist.Initialize();
 //BA.debugLineNum = 1855;BA.debugLine="SearchList.Clear";
_searchlist.Clear();
 //BA.debugLineNum = 1857;BA.debugLine="Select Case iSearchBy";
switch (_isearchby) {
case 0: {
 //BA.debugLineNum = 1859;BA.debugLine="sField1=\"SeqNo\"";
_sfield1 = "SeqNo";
 //BA.debugLineNum = 1860;BA.debugLine="sField2=\"AcctName\"";
_sfield2 = "AcctName";
 //BA.debugLineNum = 1861;BA.debugLine="SV.et.InputType = SV.et.INPUT_TYPE_NUMBERS";
mostCurrent._sv._et /*anywheresoftware.b4a.objects.EditTextWrapper*/ .setInputType(mostCurrent._sv._et /*anywheresoftware.b4a.objects.EditTextWrapper*/ .INPUT_TYPE_NUMBERS);
 break; }
case 1: {
 //BA.debugLineNum = 1864;BA.debugLine="sField1=\"MeterNo\"";
_sfield1 = "MeterNo";
 //BA.debugLineNum = 1865;BA.debugLine="sField2=\"AcctName\"";
_sfield2 = "AcctName";
 //BA.debugLineNum = 1866;BA.debugLine="SV.et.InputType = SV.et.INPUT_TYPE_NUMBERS";
mostCurrent._sv._et /*anywheresoftware.b4a.objects.EditTextWrapper*/ .setInputType(mostCurrent._sv._et /*anywheresoftware.b4a.objects.EditTextWrapper*/ .INPUT_TYPE_NUMBERS);
 break; }
case 2: {
 //BA.debugLineNum = 1869;BA.debugLine="sField1=\"AcctName\"";
_sfield1 = "AcctName";
 //BA.debugLineNum = 1870;BA.debugLine="sField2=\"SeqNo\"";
_sfield2 = "SeqNo";
 //BA.debugLineNum = 1871;BA.debugLine="SV.et.InputType = SV.et.INPUT_TYPE_TEXT";
mostCurrent._sv._et /*anywheresoftware.b4a.objects.EditTextWrapper*/ .setInputType(mostCurrent._sv._et /*anywheresoftware.b4a.objects.EditTextWrapper*/ .INPUT_TYPE_TEXT);
 break; }
}
;
 //BA.debugLineNum = 1874;BA.debugLine="If SV.IsInitialized=False Then";
if (mostCurrent._sv.IsInitialized /*boolean*/ ()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 1875;BA.debugLine="SV.Initialize(Me,\"SV\")";
mostCurrent._sv._initialize /*String*/ (mostCurrent.activityBA,meterreading.getObject(),"SV");
 };
 //BA.debugLineNum = 1878;BA.debugLine="SV.ClearAll";
mostCurrent._sv._clearall /*String*/ ();
 //BA.debugLineNum = 1879;BA.debugLine="SV.ClearSearchBox";
mostCurrent._sv._clearsearchbox /*String*/ ();
 //BA.debugLineNum = 1880;BA.debugLine="SV.lv.Clear";
mostCurrent._sv._lv /*anywheresoftware.b4a.objects.ListViewWrapper*/ .Clear();
 //BA.debugLineNum = 1882;BA.debugLine="Select iFilterBy";
switch (_ifilterby) {
case 0: {
 //BA.debugLineNum = 1884;BA.debugLine="If RetrieveUnread(GlobalVar.BookID, sField1)= F";
if (_retrieveunread(mostCurrent._globalvar._bookid /*int*/ ,_sfield1)==anywheresoftware.b4a.keywords.Common.False) { 
if (true) return "";};
 //BA.debugLineNum = 1885;BA.debugLine="For i = 0 To rsUnReadAcct.RowCount - 1";
{
final int step28 = 1;
final int limit28 = (int) (mostCurrent._rsunreadacct.getRowCount()-1);
_i = (int) (0) ;
for (;_i <= limit28 ;_i = _i + step28 ) {
 //BA.debugLineNum = 1886;BA.debugLine="rsUnReadAcct.Position=i";
mostCurrent._rsunreadacct.setPosition(_i);
 //BA.debugLineNum = 1887;BA.debugLine="Dim it As Item";
_it = new com.bwsi.MeterReader.searchview._item();
 //BA.debugLineNum = 1888;BA.debugLine="it.Title=rsUnReadAcct.GetString(sField1) & \" -";
_it.Title /*String*/  = mostCurrent._rsunreadacct.GetString(_sfield1)+" - "+mostCurrent._rsunreadacct.GetString(_sfield2);
 //BA.debugLineNum = 1889;BA.debugLine="it.Text=rsUnReadAcct.GetString(\"AcctAddress\")";
_it.Text /*String*/  = mostCurrent._rsunreadacct.GetString("AcctAddress");
 //BA.debugLineNum = 1890;BA.debugLine="it.SearchText=rsUnReadAcct.GetString(sField1).";
_it.SearchText /*String*/  = mostCurrent._rsunreadacct.GetString(_sfield1).toLowerCase();
 //BA.debugLineNum = 1891;BA.debugLine="it.Value=rsUnReadAcct.GetString(\"ReadID\")";
_it.Value /*Object*/  = (Object)(mostCurrent._rsunreadacct.GetString("ReadID"));
 //BA.debugLineNum = 1892;BA.debugLine="SearchList.Add(it)";
_searchlist.Add((Object)(_it));
 }
};
 break; }
case 1: {
 //BA.debugLineNum = 1895;BA.debugLine="If RetrieveRead(GlobalVar.BookID, sField1) = Fa";
if (_retrieveread(mostCurrent._globalvar._bookid /*int*/ ,_sfield1)==anywheresoftware.b4a.keywords.Common.False) { 
if (true) return "";};
 //BA.debugLineNum = 1896;BA.debugLine="For i = 0 To rsReadAcct.RowCount - 1";
{
final int step39 = 1;
final int limit39 = (int) (mostCurrent._rsreadacct.getRowCount()-1);
_i = (int) (0) ;
for (;_i <= limit39 ;_i = _i + step39 ) {
 //BA.debugLineNum = 1897;BA.debugLine="rsReadAcct.Position=i";
mostCurrent._rsreadacct.setPosition(_i);
 //BA.debugLineNum = 1898;BA.debugLine="Dim it As Item";
_it = new com.bwsi.MeterReader.searchview._item();
 //BA.debugLineNum = 1899;BA.debugLine="it.Title=rsReadAcct.GetString(sField1) & \" - \"";
_it.Title /*String*/  = mostCurrent._rsreadacct.GetString(_sfield1)+" - "+mostCurrent._rsreadacct.GetString(_sfield2);
 //BA.debugLineNum = 1900;BA.debugLine="it.Text=rsReadAcct.GetString(\"AcctAddress\")";
_it.Text /*String*/  = mostCurrent._rsreadacct.GetString("AcctAddress");
 //BA.debugLineNum = 1901;BA.debugLine="it.SearchText=rsReadAcct.GetString(sField1).To";
_it.SearchText /*String*/  = mostCurrent._rsreadacct.GetString(_sfield1).toLowerCase();
 //BA.debugLineNum = 1902;BA.debugLine="it.Value=rsReadAcct.GetString(\"ReadID\")";
_it.Value /*Object*/  = (Object)(mostCurrent._rsreadacct.GetString("ReadID"));
 //BA.debugLineNum = 1903;BA.debugLine="SearchList.Add(it)";
_searchlist.Add((Object)(_it));
 }
};
 break; }
case 2: {
 //BA.debugLineNum = 1906;BA.debugLine="If RetrieveAll(GlobalVar.BookID, sField1) = Fal";
if (_retrieveall(mostCurrent._globalvar._bookid /*int*/ ,_sfield1)==anywheresoftware.b4a.keywords.Common.False) { 
if (true) return "";};
 //BA.debugLineNum = 1907;BA.debugLine="For i = 0 To rsAllAcct.RowCount - 1";
{
final int step50 = 1;
final int limit50 = (int) (mostCurrent._rsallacct.getRowCount()-1);
_i = (int) (0) ;
for (;_i <= limit50 ;_i = _i + step50 ) {
 //BA.debugLineNum = 1908;BA.debugLine="rsAllAcct.Position=i";
mostCurrent._rsallacct.setPosition(_i);
 //BA.debugLineNum = 1909;BA.debugLine="Dim it As Item";
_it = new com.bwsi.MeterReader.searchview._item();
 //BA.debugLineNum = 1910;BA.debugLine="it.Title=rsAllAcct.GetString(sField1) & \" - \"";
_it.Title /*String*/  = mostCurrent._rsallacct.GetString(_sfield1)+" - "+mostCurrent._rsallacct.GetString(_sfield2);
 //BA.debugLineNum = 1911;BA.debugLine="it.Text=rsAllAcct.GetString(\"AcctAddress\")";
_it.Text /*String*/  = mostCurrent._rsallacct.GetString("AcctAddress");
 //BA.debugLineNum = 1912;BA.debugLine="it.SearchText=rsAllAcct.GetString(sField1).ToL";
_it.SearchText /*String*/  = mostCurrent._rsallacct.GetString(_sfield1).toLowerCase();
 //BA.debugLineNum = 1913;BA.debugLine="it.Value=rsAllAcct.GetString(\"ReadID\")";
_it.Value /*Object*/  = (Object)(mostCurrent._rsallacct.GetString("ReadID"));
 //BA.debugLineNum = 1914;BA.debugLine="SearchList.Add(it)";
_searchlist.Add((Object)(_it));
 }
};
 break; }
default: {
 //BA.debugLineNum = 1917;BA.debugLine="For i = 0 To rsLoadedBook.RowCount - 1";
{
final int step60 = 1;
final int limit60 = (int) (mostCurrent._rsloadedbook.getRowCount()-1);
_i = (int) (0) ;
for (;_i <= limit60 ;_i = _i + step60 ) {
 //BA.debugLineNum = 1918;BA.debugLine="rsLoadedBook.Position=i";
mostCurrent._rsloadedbook.setPosition(_i);
 //BA.debugLineNum = 1919;BA.debugLine="Dim it As Item";
_it = new com.bwsi.MeterReader.searchview._item();
 //BA.debugLineNum = 1920;BA.debugLine="it.Title=rsLoadedBook.GetString(sField1) & \" -";
_it.Title /*String*/  = mostCurrent._rsloadedbook.GetString(_sfield1)+" - "+mostCurrent._rsloadedbook.GetString(_sfield2);
 //BA.debugLineNum = 1921;BA.debugLine="it.Text=rsLoadedBook.GetString(\"AcctAddress\")";
_it.Text /*String*/  = mostCurrent._rsloadedbook.GetString("AcctAddress");
 //BA.debugLineNum = 1922;BA.debugLine="it.SearchText=rsLoadedBook.GetString(sField1).";
_it.SearchText /*String*/  = mostCurrent._rsloadedbook.GetString(_sfield1).toLowerCase();
 //BA.debugLineNum = 1923;BA.debugLine="it.Value=rsLoadedBook.GetString(\"ReadID\")";
_it.Value /*Object*/  = (Object)(mostCurrent._rsloadedbook.GetString("ReadID"));
 //BA.debugLineNum = 1924;BA.debugLine="SearchList.Add(it)";
_searchlist.Add((Object)(_it));
 }
};
 break; }
}
;
 //BA.debugLineNum = 1927;BA.debugLine="IMEkeyboard.ShowKeyboard(SV.et)";
mostCurrent._imekeyboard.ShowKeyboard((android.view.View)(mostCurrent._sv._et /*anywheresoftware.b4a.objects.EditTextWrapper*/ .getObject()));
 //BA.debugLineNum = 1928;BA.debugLine="SV.SetItems(SearchList)";
mostCurrent._sv._setitems /*Object*/ (_searchlist);
 //BA.debugLineNum = 1929;BA.debugLine="SearchList.Clear";
_searchlist.Clear();
 //BA.debugLineNum = 1930;BA.debugLine="End Sub";
return "";
}
public static String  _searchclosed() throws Exception{
 //BA.debugLineNum = 2007;BA.debugLine="Sub SearchClosed";
 //BA.debugLineNum = 2008;BA.debugLine="SearchFor = \"\"";
mostCurrent._searchfor = "";
 //BA.debugLineNum = 2009;BA.debugLine="SV.ClearSearchBox";
mostCurrent._sv._clearsearchbox /*String*/ ();
 //BA.debugLineNum = 2010;BA.debugLine="SV.ClearAll";
mostCurrent._sv._clearall /*String*/ ();
 //BA.debugLineNum = 2012;BA.debugLine="pnlSearch.Visible=False";
mostCurrent._pnlsearch.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2013;BA.debugLine="pnlUnusual.Visible = False";
mostCurrent._pnlunusual.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2015;BA.debugLine="EnableControls";
_enablecontrols();
 //BA.debugLineNum = 2016;BA.debugLine="End Sub";
return "";
}
public static String  _searchunsual_itemclick(String _value) throws Exception{
 //BA.debugLineNum = 5316;BA.debugLine="Sub SearchUnsual_ItemClick(Value As String)";
 //BA.debugLineNum = 5317;BA.debugLine="Log(Value)";
anywheresoftware.b4a.keywords.Common.LogImpl("130277633",_value,0);
 //BA.debugLineNum = 5318;BA.debugLine="SearchFor = Value";
mostCurrent._searchfor = _value;
 //BA.debugLineNum = 5319;BA.debugLine="FindSearchRetValue(SearchFor)";
_findsearchretvalue(mostCurrent._searchfor);
 //BA.debugLineNum = 5320;BA.debugLine="SV.ClearAll";
mostCurrent._sv._clearall /*String*/ ();
 //BA.debugLineNum = 5321;BA.debugLine="SearchClosed";
_searchclosed();
 //BA.debugLineNum = 5322;BA.debugLine="End Sub";
return "";
}
public static String  _searchunsualreading(int _iflag) throws Exception{
anywheresoftware.b4a.objects.collections.List _searchlist = null;
int _i = 0;
com.bwsi.MeterReader.searchview._item _it = null;
 //BA.debugLineNum = 5324;BA.debugLine="Private Sub SearchUnsualReading(iFlag As Int)";
 //BA.debugLineNum = 5325;BA.debugLine="Dim SearchList As List";
_searchlist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 5327;BA.debugLine="intTempCurrPos = rsLoadedBook.Position";
_inttempcurrpos = mostCurrent._rsloadedbook.getPosition();
 //BA.debugLineNum = 5329;BA.debugLine="If SV.IsInitialized=False Then";
if (mostCurrent._sv.IsInitialized /*boolean*/ ()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 5330;BA.debugLine="SV.Initialize(Me,\"SV\")";
mostCurrent._sv._initialize /*String*/ (mostCurrent.activityBA,meterreading.getObject(),"SV");
 };
 //BA.debugLineNum = 5333;BA.debugLine="SV.ClearAll";
mostCurrent._sv._clearall /*String*/ ();
 //BA.debugLineNum = 5334;BA.debugLine="SV.ClearSearchBox";
mostCurrent._sv._clearsearchbox /*String*/ ();
 //BA.debugLineNum = 5335;BA.debugLine="SV.lv.Clear";
mostCurrent._sv._lv /*anywheresoftware.b4a.objects.ListViewWrapper*/ .Clear();
 //BA.debugLineNum = 5337;BA.debugLine="SearchList.Initialize";
_searchlist.Initialize();
 //BA.debugLineNum = 5338;BA.debugLine="SearchList.Clear";
_searchlist.Clear();
 //BA.debugLineNum = 5340;BA.debugLine="Try";
try { //BA.debugLineNum = 5341;BA.debugLine="Select Case iFlag";
switch (_iflag) {
case 0: {
 //BA.debugLineNum = 5343;BA.debugLine="Starter.strCriteria=\"SELECT * FROM tblReadings";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblReadings "+"WHERE WasRead = 1 "+"AND BookID="+BA.NumberToString(mostCurrent._globalvar._bookid /*int*/ )+" "+"AND BillYear= "+BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ )+" "+"AND BillMonth="+BA.NumberToString(mostCurrent._globalvar._billmonth /*int*/ )+" "+"AND WasMissCoded = 1 "+"ORDER BY SeqNo ASC";
 break; }
case 1: {
 //BA.debugLineNum = 5352;BA.debugLine="Starter.strCriteria=\"SELECT * FROM tblReadings";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblReadings "+"WHERE WasRead = 1 "+"AND BookID="+BA.NumberToString(mostCurrent._globalvar._bookid /*int*/ )+" "+"AND BillYear= "+BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ )+" "+"AND BillMonth="+BA.NumberToString(mostCurrent._globalvar._billmonth /*int*/ )+" "+"AND ImplosiveType = 'zero' "+"ORDER BY SeqNo ASC";
 break; }
case 2: {
 //BA.debugLineNum = 5360;BA.debugLine="Starter.strCriteria=\"SELECT * FROM tblReadings";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblReadings "+"WHERE WasRead = 1 "+"AND BookID="+BA.NumberToString(mostCurrent._globalvar._bookid /*int*/ )+" "+"AND BillYear= "+BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ )+" "+"AND BillMonth="+BA.NumberToString(mostCurrent._globalvar._billmonth /*int*/ )+" "+"AND ImplosiveType = 'implosive-inc' "+"ORDER BY SeqNo ASC";
 break; }
case 3: {
 //BA.debugLineNum = 5369;BA.debugLine="Starter.strCriteria=\"SELECT * FROM tblReadings";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblReadings "+"WHERE WasRead = 1 "+"AND BookID="+BA.NumberToString(mostCurrent._globalvar._bookid /*int*/ )+" "+"AND BillYear= "+BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ )+" "+"AND BillMonth="+BA.NumberToString(mostCurrent._globalvar._billmonth /*int*/ )+" "+"AND ImplosiveType = 'implosive-dec' "+"ORDER BY SeqNo ASC";
 break; }
case 4: {
 //BA.debugLineNum = 5378;BA.debugLine="Starter.strCriteria=\"SELECT * FROM tblReadings";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblReadings "+"WHERE WasRead = 1 "+"AND BookID="+BA.NumberToString(mostCurrent._globalvar._bookid /*int*/ )+" "+"AND BillYear= "+BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ )+" "+"AND BillMonth="+BA.NumberToString(mostCurrent._globalvar._billmonth /*int*/ )+" "+"AND BillType = 'AB' "+"ORDER BY SeqNo ASC";
 break; }
case 5: {
 //BA.debugLineNum = 5386;BA.debugLine="Starter.strCriteria=\"SELECT * FROM tblReadings";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblReadings "+"WHERE WasRead = 1 "+"AND BookID = "+BA.NumberToString(mostCurrent._globalvar._bookid /*int*/ )+" "+"AND BillYear = "+BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ )+" "+"AND BillMonth = "+BA.NumberToString(mostCurrent._globalvar._billmonth /*int*/ )+" "+"AND WasRead = 1 "+"AND PrintStatus = 0 "+"ORDER BY SeqNo ASC";
 break; }
}
;
 //BA.debugLineNum = 5396;BA.debugLine="rsUnusualReading = Starter.DBCon.ExecQuery(Start";
mostCurrent._rsunusualreading = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 5398;BA.debugLine="If rsUnusualReading.RowCount > 0 Then";
if (mostCurrent._rsunusualreading.getRowCount()>0) { 
 //BA.debugLineNum = 5399;BA.debugLine="For i = 0 To rsUnusualReading.RowCount - 1";
{
final int step28 = 1;
final int limit28 = (int) (mostCurrent._rsunusualreading.getRowCount()-1);
_i = (int) (0) ;
for (;_i <= limit28 ;_i = _i + step28 ) {
 //BA.debugLineNum = 5400;BA.debugLine="rsUnusualReading.Position = i";
mostCurrent._rsunusualreading.setPosition(_i);
 //BA.debugLineNum = 5401;BA.debugLine="Dim it As Item";
_it = new com.bwsi.MeterReader.searchview._item();
 //BA.debugLineNum = 5402;BA.debugLine="it.Title=rsUnusualReading.GetString(\"SeqNo\") &";
_it.Title /*String*/  = mostCurrent._rsunusualreading.GetString("SeqNo")+" - "+mostCurrent._rsunusualreading.GetString("AcctName");
 //BA.debugLineNum = 5403;BA.debugLine="it.Text=rsUnusualReading.GetString(\"AcctAddres";
_it.Text /*String*/  = mostCurrent._rsunusualreading.GetString("AcctAddress");
 //BA.debugLineNum = 5404;BA.debugLine="it.SearchText = rsUnusualReading.GetString(\"Se";
_it.SearchText /*String*/  = mostCurrent._rsunusualreading.GetString("SeqNo").toLowerCase();
 //BA.debugLineNum = 5405;BA.debugLine="it.Value=rsUnusualReading.GetString(\"ReadID\")";
_it.Value /*Object*/  = (Object)(mostCurrent._rsunusualreading.GetString("ReadID"));
 //BA.debugLineNum = 5406;BA.debugLine="SearchList.Add(it)";
_searchlist.Add((Object)(_it));
 }
};
 //BA.debugLineNum = 5408;BA.debugLine="lblUnusualCount.Text = rsUnusualReading.RowCoun";
mostCurrent._lblunusualcount.setText(BA.ObjectToCharSequence(BA.NumberToString(mostCurrent._rsunusualreading.getRowCount())+(" Record(s) found...")));
 }else {
 //BA.debugLineNum = 5410;BA.debugLine="lblUnusualCount.Text = $\"No Record found...\"$";
mostCurrent._lblunusualcount.setText(BA.ObjectToCharSequence(("No Record found...")));
 //BA.debugLineNum = 5411;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 5413;BA.debugLine="SV.SetItems(SearchList)";
mostCurrent._sv._setitems /*Object*/ (_searchlist);
 //BA.debugLineNum = 5414;BA.debugLine="SearchList.Clear";
_searchlist.Clear();
 } 
       catch (Exception e45) {
			processBA.setLastException(e45); //BA.debugLineNum = 5416;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("130343260",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 5418;BA.debugLine="End Sub";
return "";
}
public static String  _septagelogo_error() throws Exception{
 //BA.debugLineNum = 4942;BA.debugLine="Sub SeptageLogo_Error";
 //BA.debugLineNum = 4945;BA.debugLine="End Sub";
return "";
}
public static String  _septagelogo_newdata(byte[] _buffer) throws Exception{
String _msg = "";
 //BA.debugLineNum = 4935;BA.debugLine="Sub SeptageLogo_NewData (Buffer() As Byte)";
 //BA.debugLineNum = 4936;BA.debugLine="Dim msg As String";
_msg = "";
 //BA.debugLineNum = 4937;BA.debugLine="msg = BytesToString(Buffer, 0, Buffer.Length, \"UT";
_msg = anywheresoftware.b4a.keywords.Common.BytesToString(_buffer,(int) (0),_buffer.length,"UTF8");
 //BA.debugLineNum = 4939;BA.debugLine="Log(msg)";
anywheresoftware.b4a.keywords.Common.LogImpl("128573700",_msg,0);
 //BA.debugLineNum = 4940;BA.debugLine="End Sub";
return "";
}
public static String  _setbuttoncolors() throws Exception{
 //BA.debugLineNum = 1133;BA.debugLine="Private Sub SetButtonColors()";
 //BA.debugLineNum = 1135;BA.debugLine="btnPrevious.Background = CreateButtonColor(0xFF0D";
mostCurrent._btnprevious.setBackground((android.graphics.drawable.Drawable)(_createbuttoncolor((int) (0xff0d47a1),(int) (0xff1976d2),(int) (0xff1e88e5),(int) (0xff0d47a1)).getObject()));
 //BA.debugLineNum = 1136;BA.debugLine="btnNext.Background = CreateButtonColor(0xFF0D47A1";
mostCurrent._btnnext.setBackground((android.graphics.drawable.Drawable)(_createbuttoncolor((int) (0xff0d47a1),(int) (0xff1976d2),(int) (0xff1e88e5),(int) (0xff0d47a1)).getObject()));
 //BA.debugLineNum = 1137;BA.debugLine="btnEdit.Background = CreateButtonColor(0xFF0D47A1";
mostCurrent._btnedit.setBackground((android.graphics.drawable.Drawable)(_createbuttoncolor((int) (0xff0d47a1),(int) (0xff1976d2),(int) (0xff1e88e5),(int) (0xff0d47a1)).getObject()));
 //BA.debugLineNum = 1138;BA.debugLine="btnMissCode.Background = CreateButtonColor(0xFF0D";
mostCurrent._btnmisscode.setBackground((android.graphics.drawable.Drawable)(_createbuttoncolor((int) (0xff0d47a1),(int) (0xff1976d2),(int) (0xff1e88e5),(int) (0xff0d47a1)).getObject()));
 //BA.debugLineNum = 1139;BA.debugLine="btnRemarks.Background = CreateButtonColor(0xFF0D4";
mostCurrent._btnremarks.setBackground((android.graphics.drawable.Drawable)(_createbuttoncolor((int) (0xff0d47a1),(int) (0xff1976d2),(int) (0xff1e88e5),(int) (0xff0d47a1)).getObject()));
 //BA.debugLineNum = 1140;BA.debugLine="btnReprint.Background = CreateButtonColor(0xFF0D4";
mostCurrent._btnreprint.setBackground((android.graphics.drawable.Drawable)(_createbuttoncolor((int) (0xff0d47a1),(int) (0xff1976d2),(int) (0xff1e88e5),(int) (0xff0d47a1)).getObject()));
 //BA.debugLineNum = 1141;BA.debugLine="btnCancelSearch.Background = CreateButtonColor(0x";
mostCurrent._btncancelsearch.setBackground((android.graphics.drawable.Drawable)(_createbuttoncolor((int) (0xff0d47a1),(int) (0xff1976d2),(int) (0xff1e88e5),(int) (0xff0d47a1)).getObject()));
 //BA.debugLineNum = 1142;BA.debugLine="btnCancelUnusual.Background = CreateButtonColor(0";
mostCurrent._btncancelunusual.setBackground((android.graphics.drawable.Drawable)(_createbuttoncolor((int) (0xff0d47a1),(int) (0xff1976d2),(int) (0xff1e88e5),(int) (0xff0d47a1)).getObject()));
 //BA.debugLineNum = 1143;BA.debugLine="End Sub";
return "";
}
public static String  _setsnackbarbackground(de.amberhome.objects.SnackbarWrapper _psnack,int _pcolor) throws Exception{
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
 //BA.debugLineNum = 5697;BA.debugLine="Sub SetSnackBarBackground(pSnack As DSSnackbar, pC";
 //BA.debugLineNum = 5698;BA.debugLine="Dim v As View";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
 //BA.debugLineNum = 5699;BA.debugLine="v = pSnack.View";
_v = (anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(_psnack.getView()));
 //BA.debugLineNum = 5700;BA.debugLine="v.Color = pColor";
_v.setColor(_pcolor);
 //BA.debugLineNum = 5701;BA.debugLine="End Sub";
return "";
}
public static String  _setsnackbartextcolor(de.amberhome.objects.SnackbarWrapper _psnack,int _pcolor) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _p = null;
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
anywheresoftware.b4a.objects.LabelWrapper _textv = null;
 //BA.debugLineNum = 5703;BA.debugLine="Sub SetSnackBarTextColor(pSnack As DSSnackbar, pCo";
 //BA.debugLineNum = 5704;BA.debugLine="Dim p As Panel";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 5705;BA.debugLine="p = pSnack.View";
_p = (anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(_psnack.getView()));
 //BA.debugLineNum = 5706;BA.debugLine="For Each v As View In p.GetAllViewsRecursive";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
{
final anywheresoftware.b4a.BA.IterableList group3 = _p.GetAllViewsRecursive();
final int groupLen3 = group3.getSize()
;int index3 = 0;
;
for (; index3 < groupLen3;index3++){
_v = (anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(group3.Get(index3)));
 //BA.debugLineNum = 5707;BA.debugLine="If v Is Label Then";
if (_v.getObjectOrNull() instanceof android.widget.TextView) { 
 //BA.debugLineNum = 5708;BA.debugLine="Dim textv As Label";
_textv = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 5709;BA.debugLine="textv = v";
_textv = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_v.getObject()));
 //BA.debugLineNum = 5710;BA.debugLine="textv.TextColor = pColor";
_textv.setTextColor(_pcolor);
 //BA.debugLineNum = 5711;BA.debugLine="Exit";
if (true) break;
 };
 }
};
 //BA.debugLineNum = 5714;BA.debugLine="End Sub";
return "";
}
public static String  _showaddmisscodedialog() throws Exception{
 //BA.debugLineNum = 2044;BA.debugLine="Private Sub ShowAddMissCodeDialog";
 //BA.debugLineNum = 2045;BA.debugLine="soundsAlarmChannel.Play(SoundID,1,1,1,0,1)";
_soundsalarmchannel.Play(_soundid,(float) (1),(float) (1),(int) (1),(int) (0),(float) (1));
 //BA.debugLineNum = 2047;BA.debugLine="cdButtonCancel.Initialize(0xFFDC143C, 25)";
mostCurrent._cdbuttoncancel.Initialize((int) (0xffdc143c),(int) (25));
 //BA.debugLineNum = 2048;BA.debugLine="btnMisCodeCancelPost.Background = cdButtonCancel";
mostCurrent._btnmiscodecancelpost.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdbuttoncancel.getObject()));
 //BA.debugLineNum = 2050;BA.debugLine="cdButtonSaveAndPrint.Initialize(0xFF1976D2, 25)";
mostCurrent._cdbuttonsaveandprint.Initialize((int) (0xff1976d2),(int) (25));
 //BA.debugLineNum = 2051;BA.debugLine="btnMisCodeSave.Background = cdButtonSaveAndPrint";
mostCurrent._btnmiscodesave.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdbuttonsaveandprint.getObject()));
 //BA.debugLineNum = 2053;BA.debugLine="pnlMisCodeMsgBox.Visible = True";
mostCurrent._pnlmiscodemsgbox.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2054;BA.debugLine="lblMisCodeMsgTitle.Text = $\"         ADD MISCODE";
mostCurrent._lblmiscodemsgtitle.setText(BA.ObjectToCharSequence(("         ADD MISCODE ENTRY")));
 //BA.debugLineNum = 2055;BA.debugLine="btnMisCodeSave.Text = $\"SAVE\"$";
mostCurrent._btnmiscodesave.setText(BA.ObjectToCharSequence(("SAVE")));
 //BA.debugLineNum = 2056;BA.debugLine="txtMisCode.Enabled = False";
mostCurrent._txtmiscode.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2057;BA.debugLine="blnEdit = True";
_blnedit = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 2058;BA.debugLine="End Sub";
return "";
}
public static String  _showaddnewreading() throws Exception{
 //BA.debugLineNum = 5209;BA.debugLine="Private Sub ShowAddNewReading";
 //BA.debugLineNum = 5210;BA.debugLine="cdButtonCancel.Initialize(0xFFDC143C, 25)";
mostCurrent._cdbuttoncancel.Initialize((int) (0xffdc143c),(int) (25));
 //BA.debugLineNum = 5211;BA.debugLine="btnNormalCancelPost.Background = cdButtonCancel";
mostCurrent._btnnormalcancelpost.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdbuttoncancel.getObject()));
 //BA.debugLineNum = 5213;BA.debugLine="cdButtonSaveOnly.Initialize(0xFF14C0DB, 25)";
mostCurrent._cdbuttonsaveonly.Initialize((int) (0xff14c0db),(int) (25));
 //BA.debugLineNum = 5214;BA.debugLine="btnNormalSaveOnly.Background = cdButtonSaveOnly";
mostCurrent._btnnormalsaveonly.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdbuttonsaveonly.getObject()));
 //BA.debugLineNum = 5216;BA.debugLine="cdButtonSaveAndPrint.Initialize(0xFF1976D2, 25)";
mostCurrent._cdbuttonsaveandprint.Initialize((int) (0xff1976d2),(int) (25));
 //BA.debugLineNum = 5217;BA.debugLine="btnNormalSaveAndPrint.Background = cdButtonSaveAn";
mostCurrent._btnnormalsaveandprint.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdbuttonsaveandprint.getObject()));
 //BA.debugLineNum = 5219;BA.debugLine="pnlNormalMsgBox.Visible = True";
mostCurrent._pnlnormalmsgbox.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 5220;BA.debugLine="End Sub";
return "";
}
public static String  _showaddremarksdialog() throws Exception{
anywheresoftware.b4a.objects.CSBuilder _cscontent = null;
 //BA.debugLineNum = 2322;BA.debugLine="Sub ShowAddRemarksDialog";
 //BA.debugLineNum = 2323;BA.debugLine="Dim csContent As CSBuilder";
_cscontent = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 2325;BA.debugLine="csContent.Initialize.Size(16).Color(Colors.DarkGr";
_cscontent.Initialize().Size((int) (16)).Color(anywheresoftware.b4a.keywords.Common.Colors.DarkGray).Append(BA.ObjectToCharSequence(("Please enter a remarks for this account."))).PopAll();
 //BA.debugLineNum = 2326;BA.debugLine="MatDialogBuilder.Initialize(\"AddRemarksDialog\")";
mostCurrent._matdialogbuilder.Initialize(mostCurrent.activityBA,"AddRemarksDialog");
 //BA.debugLineNum = 2327;BA.debugLine="MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIG";
mostCurrent._matdialogbuilder.Theme(mostCurrent._matdialogbuilder.THEME_LIGHT);
 //BA.debugLineNum = 2328;BA.debugLine="MatDialogBuilder.Title($\"ADD READING REMARKS\"$).T";
mostCurrent._matdialogbuilder.Title(BA.ObjectToCharSequence(("ADD READING REMARKS"))).TitleColor(anywheresoftware.b4a.keywords.Common.Colors.Red).TitleGravity(mostCurrent._matdialogbuilder.GRAVITY_START);
 //BA.debugLineNum = 2329;BA.debugLine="MatDialogBuilder.IconRes(GlobalVar.WarningIcon).L";
mostCurrent._matdialogbuilder.IconRes(mostCurrent._globalvar._warningicon /*String*/ ).LimitIconToDefaultSize();
 //BA.debugLineNum = 2330;BA.debugLine="MatDialogBuilder.InputType(Bit.Or(Bit.Or(MatDialo";
mostCurrent._matdialogbuilder.InputType(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Bit.Or(mostCurrent._matdialogbuilder.TYPE_CLASS_TEXT,mostCurrent._matdialogbuilder.TYPE_TEXT_FLAG_CAP_SENTENCES),mostCurrent._matdialogbuilder.TYPE_TEXT_VARIATION_PERSON_NAME));
 //BA.debugLineNum = 2331;BA.debugLine="MatDialogBuilder.Input2($\"Type your Remarks here.";
mostCurrent._matdialogbuilder.Input2(BA.ObjectToCharSequence(("Type your Remarks here...")),BA.ObjectToCharSequence(("")),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2332;BA.debugLine="MatDialogBuilder.AlwaysCallInputCallback";
mostCurrent._matdialogbuilder.AlwaysCallInputCallback();
 //BA.debugLineNum = 2333;BA.debugLine="MatDialogBuilder.Content(csContent)";
mostCurrent._matdialogbuilder.Content(BA.ObjectToCharSequence(_cscontent.getObject()));
 //BA.debugLineNum = 2334;BA.debugLine="MatDialogBuilder.PositiveText($\"SAVE\"$).PositiveC";
mostCurrent._matdialogbuilder.PositiveText(BA.ObjectToCharSequence(("SAVE"))).PositiveColor((int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 2335;BA.debugLine="MatDialogBuilder.NegativeText($\"CANCEL\"$).Negativ";
mostCurrent._matdialogbuilder.NegativeText(BA.ObjectToCharSequence(("CANCEL"))).NegativeColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 2336;BA.debugLine="MatDialogBuilder.CanceledOnTouchOutside(False)";
mostCurrent._matdialogbuilder.CanceledOnTouchOutside(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2337;BA.debugLine="MatDialogBuilder.Show";
mostCurrent._matdialogbuilder.Show();
 //BA.debugLineNum = 2338;BA.debugLine="End Sub";
return "";
}
public static String  _showavenotavailabledialog() throws Exception{
anywheresoftware.b4a.objects.CSBuilder _cscontent = null;
anywheresoftware.b4a.objects.CSBuilder _cstitle = null;
String _smsg = "";
 //BA.debugLineNum = 5474;BA.debugLine="Private Sub ShowAveNotAvailableDialog";
 //BA.debugLineNum = 5475;BA.debugLine="Dim csContent As CSBuilder";
_cscontent = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 5476;BA.debugLine="Dim csTitle As CSBuilder";
_cstitle = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 5477;BA.debugLine="Dim sMsg = $\"ERROR! Unable to generate average bi";
_smsg = ("ERROR! Unable to generate average bill");
 //BA.debugLineNum = 5479;BA.debugLine="soundsAlarmChannel.Play(SoundID,1,1,1,0,1)";
_soundsalarmchannel.Play(_soundid,(float) (1),(float) (1),(int) (1),(int) (0),(float) (1));
 //BA.debugLineNum = 5481;BA.debugLine="If AcctStatus = \"dc\" Then";
if ((mostCurrent._acctstatus).equals("dc")) { 
 //BA.debugLineNum = 5482;BA.debugLine="csContent.Initialize.Size(14).Color(Colors.Red).";
_cscontent.Initialize().Size((int) (14)).Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Bold().Append(BA.ObjectToCharSequence(("AVERAGING NOT ALLOWED!"))).Pop();
 //BA.debugLineNum = 5483;BA.debugLine="csContent.Color(Colors.DarkGray).Append(CRLF & $";
_cscontent.Color(anywheresoftware.b4a.keywords.Common.Colors.DarkGray).Append(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.CRLF+("Unable to generate average bill due to this account was disconnected."))).PopAll();
 //BA.debugLineNum = 5484;BA.debugLine="csTitle.Initialize.Bold.Size(16).Color(Colors.Re";
_cstitle.Initialize().Bold().Size((int) (16)).Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(("E R R O R"))).PopAll();
 }else {
 //BA.debugLineNum = 5486;BA.debugLine="csContent.Initialize.Size(14).Color(Colors.Red).";
_cscontent.Initialize().Size((int) (14)).Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Bold().Append(BA.ObjectToCharSequence(("ALREADY READ!"))).Pop();
 //BA.debugLineNum = 5487;BA.debugLine="csContent.Color(Colors.DarkGray).Append(CRLF & $";
_cscontent.Color(anywheresoftware.b4a.keywords.Common.Colors.DarkGray).Append(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.CRLF+("Unable to generate average bill due to this account was already read."))).PopAll();
 //BA.debugLineNum = 5488;BA.debugLine="csTitle.Initialize.Bold.Size(16).Color(Colors.Re";
_cstitle.Initialize().Bold().Size((int) (16)).Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(("E R R O R"))).PopAll();
 };
 //BA.debugLineNum = 5492;BA.debugLine="MatDialogBuilder.Initialize(\"AveDialogUnavailable";
mostCurrent._matdialogbuilder.Initialize(mostCurrent.activityBA,"AveDialogUnavailable");
 //BA.debugLineNum = 5493;BA.debugLine="MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIG";
mostCurrent._matdialogbuilder.Theme(mostCurrent._matdialogbuilder.THEME_LIGHT);
 //BA.debugLineNum = 5494;BA.debugLine="MatDialogBuilder.Title(csTitle).TitleGravity(MatD";
mostCurrent._matdialogbuilder.Title(BA.ObjectToCharSequence(_cstitle.getObject())).TitleGravity(mostCurrent._matdialogbuilder.GRAVITY_START);
 //BA.debugLineNum = 5495;BA.debugLine="MatDialogBuilder.IconRes(GlobalVar.InfoIcon).Limi";
mostCurrent._matdialogbuilder.IconRes(mostCurrent._globalvar._infoicon /*String*/ ).LimitIconToDefaultSize();
 //BA.debugLineNum = 5496;BA.debugLine="MatDialogBuilder.Content(csContent)";
mostCurrent._matdialogbuilder.Content(BA.ObjectToCharSequence(_cscontent.getObject()));
 //BA.debugLineNum = 5497;BA.debugLine="MatDialogBuilder.PositiveText($\"OK\"$).PositiveCol";
mostCurrent._matdialogbuilder.PositiveText(BA.ObjectToCharSequence(("OK"))).PositiveColor((int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 5498;BA.debugLine="MatDialogBuilder.CanceledOnTouchOutside(False)";
mostCurrent._matdialogbuilder.CanceledOnTouchOutside(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 5499;BA.debugLine="MatDialogBuilder.Show";
mostCurrent._matdialogbuilder.Show();
 //BA.debugLineNum = 5501;BA.debugLine="If  Not(TalkBackIsActive) Then";
if (anywheresoftware.b4a.keywords.Common.Not(_talkbackisactive())) { 
 //BA.debugLineNum = 5502;BA.debugLine="TTS1.Speak(sMsg, False)";
_tts1.Speak(_smsg,anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 5505;BA.debugLine="End Sub";
return "";
}
public static String  _showaveragebillremarks() throws Exception{
 //BA.debugLineNum = 5522;BA.debugLine="Private Sub ShowAverageBillRemarks";
 //BA.debugLineNum = 5523;BA.debugLine="IMEkeyboard.HideKeyboard";
mostCurrent._imekeyboard.HideKeyboard(mostCurrent.activityBA);
 //BA.debugLineNum = 5524;BA.debugLine="cdButtonCancel.Initialize(0xFFDC143C, 25)";
mostCurrent._cdbuttoncancel.Initialize((int) (0xffdc143c),(int) (25));
 //BA.debugLineNum = 5525;BA.debugLine="btnAveRemCancelPost.Background = cdButtonCancel";
mostCurrent._btnaveremcancelpost.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdbuttoncancel.getObject()));
 //BA.debugLineNum = 5527;BA.debugLine="cdButtonSaveAndPrint.Initialize(0xFF1976D2, 25)";
mostCurrent._cdbuttonsaveandprint.Initialize((int) (0xff1976d2),(int) (25));
 //BA.debugLineNum = 5528;BA.debugLine="btnAveRemSaveAndPrint.Background = cdButtonSaveAn";
mostCurrent._btnaveremsaveandprint.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdbuttonsaveandprint.getObject()));
 //BA.debugLineNum = 5530;BA.debugLine="cdTxtBox.Initialize2(Colors.White, 0, 0, Colors.T";
mostCurrent._cdtxtbox.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.White,(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 5531;BA.debugLine="txtReason.Background = cdTxtBox";
mostCurrent._txtreason.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdtxtbox.getObject()));
 //BA.debugLineNum = 5533;BA.debugLine="pnlAveRemMsgBox.Visible = True";
mostCurrent._pnlaveremmsgbox.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 5534;BA.debugLine="txtReason.Text = \"\"";
mostCurrent._txtreason.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 5535;BA.debugLine="sAveRem = \"\"";
mostCurrent._saverem = "";
 //BA.debugLineNum = 5537;BA.debugLine="optReason0.Checked = False";
mostCurrent._optreason0.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 5538;BA.debugLine="optReason1.Checked = False";
mostCurrent._optreason1.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 5539;BA.debugLine="optReason2.Checked = False";
mostCurrent._optreason2.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 5540;BA.debugLine="optReason3.Checked = True";
mostCurrent._optreason3.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 5541;BA.debugLine="optReason4.Checked = False";
mostCurrent._optreason4.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 5542;BA.debugLine="optReason5.Checked = False";
mostCurrent._optreason5.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 5543;BA.debugLine="optReason6.Checked = False";
mostCurrent._optreason6.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 5544;BA.debugLine="optReason7.Checked = False";
mostCurrent._optreason7.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 5545;BA.debugLine="optReason8.Checked = False";
mostCurrent._optreason8.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 5547;BA.debugLine="End Sub";
return "";
}
public static String  _showeditdialog() throws Exception{
anywheresoftware.b4a.objects.CSBuilder _cscontent = null;
 //BA.debugLineNum = 5271;BA.debugLine="Private Sub ShowEditDialog";
 //BA.debugLineNum = 5273;BA.debugLine="Dim csContent As CSBuilder";
_cscontent = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 5274;BA.debugLine="csContent.Initialize.Color(Colors.Red).Append($\"W";
_cscontent.Initialize().Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(("WARNING!"))).Color(anywheresoftware.b4a.keywords.Common.Colors.DarkGray).Append(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.CRLF+("You already Miscoded this Account.")+anywheresoftware.b4a.keywords.Common.CRLF+("Continuing this will "))).Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(("REMOVE "))).Color(anywheresoftware.b4a.keywords.Common.Colors.DarkGray).Append(BA.ObjectToCharSequence(("any Miscoded data on this Account."))).Color((int) (mostCurrent._globalvar._pricolor /*double*/ )).Append(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+("Do you want to Continue anyway?"))).PopAll();
 //BA.debugLineNum = 5276;BA.debugLine="MatDialogBuilder.Initialize(\"EditDialog\")";
mostCurrent._matdialogbuilder.Initialize(mostCurrent.activityBA,"EditDialog");
 //BA.debugLineNum = 5277;BA.debugLine="MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIG";
mostCurrent._matdialogbuilder.Theme(mostCurrent._matdialogbuilder.THEME_LIGHT);
 //BA.debugLineNum = 5278;BA.debugLine="MatDialogBuilder.Title($\"EDIT CURRENT READING\"$).";
mostCurrent._matdialogbuilder.Title(BA.ObjectToCharSequence(("EDIT CURRENT READING"))).TitleColor(anywheresoftware.b4a.keywords.Common.Colors.Red).TitleGravity(mostCurrent._matdialogbuilder.GRAVITY_START);
 //BA.debugLineNum = 5279;BA.debugLine="MatDialogBuilder.IconRes(GlobalVar.WarningIcon).L";
mostCurrent._matdialogbuilder.IconRes(mostCurrent._globalvar._warningicon /*String*/ ).LimitIconToDefaultSize();
 //BA.debugLineNum = 5280;BA.debugLine="MatDialogBuilder.Content(csContent)";
mostCurrent._matdialogbuilder.Content(BA.ObjectToCharSequence(_cscontent.getObject()));
 //BA.debugLineNum = 5281;BA.debugLine="MatDialogBuilder.PositiveText($\"YES\"$).PositiveCo";
mostCurrent._matdialogbuilder.PositiveText(BA.ObjectToCharSequence(("YES"))).PositiveColor((int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 5282;BA.debugLine="MatDialogBuilder.NegativeText($\"NO\"$).NegativeCol";
mostCurrent._matdialogbuilder.NegativeText(BA.ObjectToCharSequence(("NO"))).NegativeColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 5283;BA.debugLine="MatDialogBuilder.CanceledOnTouchOutside(False)";
mostCurrent._matdialogbuilder.CanceledOnTouchOutside(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 5284;BA.debugLine="MatDialogBuilder.Show";
mostCurrent._matdialogbuilder.Show();
 //BA.debugLineNum = 5285;BA.debugLine="End Sub";
return "";
}
public static String  _showeditmisscodedialog() throws Exception{
 //BA.debugLineNum = 2238;BA.debugLine="Private Sub ShowEditMissCodeDialog";
 //BA.debugLineNum = 2239;BA.debugLine="soundsAlarmChannel.Play(SoundID,1,1,1,0,1)";
_soundsalarmchannel.Play(_soundid,(float) (1),(float) (1),(int) (1),(int) (0),(float) (1));
 //BA.debugLineNum = 2241;BA.debugLine="cdButtonCancel.Initialize(0xFFDC143C, 25)";
mostCurrent._cdbuttoncancel.Initialize((int) (0xffdc143c),(int) (25));
 //BA.debugLineNum = 2242;BA.debugLine="btnMisCodeCancelPost.Background = cdButtonCancel";
mostCurrent._btnmiscodecancelpost.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdbuttoncancel.getObject()));
 //BA.debugLineNum = 2244;BA.debugLine="cdButtonSaveAndPrint.Initialize(0xFF1976D2, 25)";
mostCurrent._cdbuttonsaveandprint.Initialize((int) (0xff1976d2),(int) (25));
 //BA.debugLineNum = 2245;BA.debugLine="btnMisCodeSave.Background = cdButtonSaveAndPrint";
mostCurrent._btnmiscodesave.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdbuttonsaveandprint.getObject()));
 //BA.debugLineNum = 2247;BA.debugLine="pnlMisCodeMsgBox.Visible = True";
mostCurrent._pnlmiscodemsgbox.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 2248;BA.debugLine="lblMisCodeMsgTitle.Text = $\"         EDIT MISCODE";
mostCurrent._lblmiscodemsgtitle.setText(BA.ObjectToCharSequence(("         EDIT MISCODE ENTRY")));
 //BA.debugLineNum = 2249;BA.debugLine="btnMisCodeSave.Text = $\"UPDATE\"$";
mostCurrent._btnmiscodesave.setText(BA.ObjectToCharSequence(("UPDATE")));
 //BA.debugLineNum = 2250;BA.debugLine="txtMisCode.Enabled = False";
mostCurrent._txtmiscode.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2251;BA.debugLine="blnEdit = False";
_blnedit = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 2252;BA.debugLine="End Sub";
return "";
}
public static String  _showeditremarksdialog() throws Exception{
anywheresoftware.b4a.objects.CSBuilder _cscontent = null;
 //BA.debugLineNum = 2373;BA.debugLine="Sub ShowEditRemarksDialog";
 //BA.debugLineNum = 2374;BA.debugLine="Dim csContent As CSBuilder";
_cscontent = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 2376;BA.debugLine="csContent.Initialize.Size(16).Color(Colors.DarkGr";
_cscontent.Initialize().Size((int) (16)).Color(anywheresoftware.b4a.keywords.Common.Colors.DarkGray).Append(BA.ObjectToCharSequence(("Please enter a remarks for this account."))).PopAll();
 //BA.debugLineNum = 2377;BA.debugLine="MatDialogBuilder.Initialize(\"EditRemarksDialog\")";
mostCurrent._matdialogbuilder.Initialize(mostCurrent.activityBA,"EditRemarksDialog");
 //BA.debugLineNum = 2378;BA.debugLine="MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIG";
mostCurrent._matdialogbuilder.Theme(mostCurrent._matdialogbuilder.THEME_LIGHT);
 //BA.debugLineNum = 2379;BA.debugLine="MatDialogBuilder.Title($\"EDIT READING REMARKS\"$).";
mostCurrent._matdialogbuilder.Title(BA.ObjectToCharSequence(("EDIT READING REMARKS"))).TitleColor(anywheresoftware.b4a.keywords.Common.Colors.Red).TitleGravity(mostCurrent._matdialogbuilder.GRAVITY_START);
 //BA.debugLineNum = 2380;BA.debugLine="MatDialogBuilder.IconRes(GlobalVar.WarningIcon).L";
mostCurrent._matdialogbuilder.IconRes(mostCurrent._globalvar._warningicon /*String*/ ).LimitIconToDefaultSize();
 //BA.debugLineNum = 2381;BA.debugLine="MatDialogBuilder.InputType(Bit.Or(Bit.Or(MatDialo";
mostCurrent._matdialogbuilder.InputType(anywheresoftware.b4a.keywords.Common.Bit.Or(anywheresoftware.b4a.keywords.Common.Bit.Or(mostCurrent._matdialogbuilder.TYPE_CLASS_TEXT,mostCurrent._matdialogbuilder.TYPE_TEXT_FLAG_CAP_SENTENCES),mostCurrent._matdialogbuilder.TYPE_TEXT_VARIATION_PERSON_NAME));
 //BA.debugLineNum = 2382;BA.debugLine="MatDialogBuilder.Input2($\"Type your Remarks here.";
mostCurrent._matdialogbuilder.Input2(BA.ObjectToCharSequence(("Type your Remarks here...")),BA.ObjectToCharSequence(("")),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2383;BA.debugLine="MatDialogBuilder.AlwaysCallInputCallback";
mostCurrent._matdialogbuilder.AlwaysCallInputCallback();
 //BA.debugLineNum = 2384;BA.debugLine="MatDialogBuilder.Content(csContent)";
mostCurrent._matdialogbuilder.Content(BA.ObjectToCharSequence(_cscontent.getObject()));
 //BA.debugLineNum = 2385;BA.debugLine="MatDialogBuilder.PositiveText($\"UPDATE\"$).Positiv";
mostCurrent._matdialogbuilder.PositiveText(BA.ObjectToCharSequence(("UPDATE"))).PositiveColor((int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 2386;BA.debugLine="MatDialogBuilder.NegativeText($\"CANCEL\"$).Negativ";
mostCurrent._matdialogbuilder.NegativeText(BA.ObjectToCharSequence(("CANCEL"))).NegativeColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 2387;BA.debugLine="MatDialogBuilder.CanceledOnTouchOutside(False)";
mostCurrent._matdialogbuilder.CanceledOnTouchOutside(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 2388;BA.debugLine="MatDialogBuilder.Show";
mostCurrent._matdialogbuilder.Show();
 //BA.debugLineNum = 2389;BA.debugLine="End Sub";
return "";
}
public static String  _showhighbillconfirmation() throws Exception{
String _smsg = "";
 //BA.debugLineNum = 5028;BA.debugLine="Private Sub ShowHighBillConfirmation";
 //BA.debugLineNum = 5029;BA.debugLine="Dim sMsg = $\"Please retype your reading!\"$ As Str";
_smsg = ("Please retype your reading!");
 //BA.debugLineNum = 5030;BA.debugLine="pnlHighBillConfirmation.Visible = True";
mostCurrent._pnlhighbillconfirmation.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 5032;BA.debugLine="cdConfirmRdg.Initialize2(Colors.Black, 0,0, Color";
mostCurrent._cdconfirmrdg.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.Black,(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 5033;BA.debugLine="txtPresRdgConfirm.Background = cdConfirmRdg";
mostCurrent._txtpresrdgconfirm.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdconfirmrdg.getObject()));
 //BA.debugLineNum = 5035;BA.debugLine="cdButtonCancel.Initialize(0xFFDC143C, 15)";
mostCurrent._cdbuttoncancel.Initialize((int) (0xffdc143c),(int) (15));
 //BA.debugLineNum = 5036;BA.debugLine="btnHBConfirmCancel.Background = cdButtonCancel";
mostCurrent._btnhbconfirmcancel.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdbuttoncancel.getObject()));
 //BA.debugLineNum = 5038;BA.debugLine="cdButtonSaveAndPrint.Initialize(0xFF1976D2, 25)";
mostCurrent._cdbuttonsaveandprint.Initialize((int) (0xff1976d2),(int) (25));
 //BA.debugLineNum = 5039;BA.debugLine="btnHBConfirmSaveAndPrint.Background = cdButtonSav";
mostCurrent._btnhbconfirmsaveandprint.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdbuttonsaveandprint.getObject()));
 //BA.debugLineNum = 5041;BA.debugLine="txtPresRdgConfirm.Text = \"\"";
mostCurrent._txtpresrdgconfirm.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 5042;BA.debugLine="btnHBConfirmSaveAndPrint.Enabled = False";
mostCurrent._btnhbconfirmsaveandprint.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 5043;BA.debugLine="txtPresRdgConfirm.RequestFocus";
mostCurrent._txtpresrdgconfirm.RequestFocus();
 //BA.debugLineNum = 5044;BA.debugLine="IMEkeyboard.ShowKeyboard(txtPresRdgConfirm)";
mostCurrent._imekeyboard.ShowKeyboard((android.view.View)(mostCurrent._txtpresrdgconfirm.getObject()));
 //BA.debugLineNum = 5045;BA.debugLine="cKeyboard.HideKeyboard";
mostCurrent._ckeyboard.HideKeyboard();
 //BA.debugLineNum = 5046;BA.debugLine="If  Not(TalkBackIsActive) Then";
if (anywheresoftware.b4a.keywords.Common.Not(_talkbackisactive())) { 
 //BA.debugLineNum = 5047;BA.debugLine="TTS1.Speak(sMsg, False)";
_tts1.Speak(_smsg,anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 5049;BA.debugLine="Select Case intSaveOnly";
switch (_intsaveonly) {
case 0: {
 //BA.debugLineNum = 5051;BA.debugLine="btnHBConfirmSaveAndPrint.Text = \"SAVE\"";
mostCurrent._btnhbconfirmsaveandprint.setText(BA.ObjectToCharSequence("SAVE"));
 break; }
case 1: {
 //BA.debugLineNum = 5053;BA.debugLine="btnHBConfirmSaveAndPrint.Text = \"SAVE AND PRINT";
mostCurrent._btnhbconfirmsaveandprint.setText(BA.ObjectToCharSequence("SAVE AND PRINT"));
 break; }
}
;
 //BA.debugLineNum = 5056;BA.debugLine="End Sub";
return "";
}
public static String  _showhighbilldialog() throws Exception{
String _smsg = "";
 //BA.debugLineNum = 4953;BA.debugLine="Private Sub ShowHighBillDialog";
 //BA.debugLineNum = 4954;BA.debugLine="Dim sMsg = $\"WARNING! High Bill Detected, Please";
_smsg = ("WARNING! High Bill Detected, Please Check the Water Meter");
 //BA.debugLineNum = 4956;BA.debugLine="soundsAlarmChannel.Play(SoundID,1,1,1,0,1)";
_soundsalarmchannel.Play(_soundid,(float) (1),(float) (1),(int) (1),(int) (0),(float) (1));
 //BA.debugLineNum = 4957;BA.debugLine="cdButtonCancel.Initialize(0xFFDC143C, 25)";
mostCurrent._cdbuttoncancel.Initialize((int) (0xffdc143c),(int) (25));
 //BA.debugLineNum = 4958;BA.debugLine="btnHiCancelPost.Background = cdButtonCancel";
mostCurrent._btnhicancelpost.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdbuttoncancel.getObject()));
 //BA.debugLineNum = 4961;BA.debugLine="cdButtonSaveOnly.Initialize(0xFF14C0DB, 25)";
mostCurrent._cdbuttonsaveonly.Initialize((int) (0xff14c0db),(int) (25));
 //BA.debugLineNum = 4962;BA.debugLine="btnHiSaveOnly.Background = cdButtonSaveOnly";
mostCurrent._btnhisaveonly.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdbuttonsaveonly.getObject()));
 //BA.debugLineNum = 4964;BA.debugLine="cdButtonSaveAndPrint.Initialize(0xFF1976D2, 25)";
mostCurrent._cdbuttonsaveandprint.Initialize((int) (0xff1976d2),(int) (25));
 //BA.debugLineNum = 4965;BA.debugLine="btnHiSaveAndPrint.Background = cdButtonSaveAndPri";
mostCurrent._btnhisaveandprint.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdbuttonsaveandprint.getObject()));
 //BA.debugLineNum = 4967;BA.debugLine="pnlHiMsgBox.Visible = True";
mostCurrent._pnlhimsgbox.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 4968;BA.debugLine="If  Not(TalkBackIsActive) Then";
if (anywheresoftware.b4a.keywords.Common.Not(_talkbackisactive())) { 
 //BA.debugLineNum = 4969;BA.debugLine="TTS1.Speak(sMsg, False)";
_tts1.Speak(_smsg,anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 4971;BA.debugLine="End Sub";
return "";
}
public static String  _showlowbilldialog() throws Exception{
String _smsg = "";
 //BA.debugLineNum = 5128;BA.debugLine="Private Sub ShowLowBillDialog";
 //BA.debugLineNum = 5129;BA.debugLine="Dim sMsg = $\"WARNING! Low Bill Detected, Please C";
_smsg = ("WARNING! Low Bill Detected, Please Check the Water Meter");
 //BA.debugLineNum = 5131;BA.debugLine="soundsAlarmChannel.Play(SoundID,1,1,1,0,1)";
_soundsalarmchannel.Play(_soundid,(float) (1),(float) (1),(int) (1),(int) (0),(float) (1));
 //BA.debugLineNum = 5133;BA.debugLine="cdButtonCancel.Initialize(0xFFDC143C, 25)";
mostCurrent._cdbuttoncancel.Initialize((int) (0xffdc143c),(int) (25));
 //BA.debugLineNum = 5134;BA.debugLine="btnLowCancelPost.Background = cdButtonCancel";
mostCurrent._btnlowcancelpost.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdbuttoncancel.getObject()));
 //BA.debugLineNum = 5136;BA.debugLine="cdButtonSaveOnly.Initialize(0xFF14C0DB, 25)";
mostCurrent._cdbuttonsaveonly.Initialize((int) (0xff14c0db),(int) (25));
 //BA.debugLineNum = 5137;BA.debugLine="btnLowSaveOnly.Background = cdButtonSaveOnly";
mostCurrent._btnlowsaveonly.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdbuttonsaveonly.getObject()));
 //BA.debugLineNum = 5139;BA.debugLine="cdButtonSaveAndPrint.Initialize(0xFF1976D2, 25)";
mostCurrent._cdbuttonsaveandprint.Initialize((int) (0xff1976d2),(int) (25));
 //BA.debugLineNum = 5140;BA.debugLine="btnLowSaveAndPrint.Background = cdButtonSaveAndPr";
mostCurrent._btnlowsaveandprint.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdbuttonsaveandprint.getObject()));
 //BA.debugLineNum = 5141;BA.debugLine="cKeyboard.HideKeyboard";
mostCurrent._ckeyboard.HideKeyboard();
 //BA.debugLineNum = 5142;BA.debugLine="pnlLowMsgBox.Visible = True";
mostCurrent._pnllowmsgbox.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 5143;BA.debugLine="If  Not(TalkBackIsActive) Then";
if (anywheresoftware.b4a.keywords.Common.Not(_talkbackisactive())) { 
 //BA.debugLineNum = 5144;BA.debugLine="TTS1.Speak(sMsg, False)";
_tts1.Speak(_smsg,anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 5146;BA.debugLine="End Sub";
return "";
}
public static String  _shownegativereadingdialog() throws Exception{
String _smsg = "";
 //BA.debugLineNum = 3496;BA.debugLine="Private Sub ShowNegativeReadingDialog";
 //BA.debugLineNum = 3497;BA.debugLine="Dim sMsg = $\"WARNING! Negative Reading Detected,";
_smsg = ("WARNING! Negative Reading Detected, Please Check the Water Meter");
 //BA.debugLineNum = 3499;BA.debugLine="soundsAlarmChannel.Play(SoundID,1,1,1,0,1)";
_soundsalarmchannel.Play(_soundid,(float) (1),(float) (1),(int) (1),(int) (0),(float) (1));
 //BA.debugLineNum = 3501;BA.debugLine="cdButtonCancel.Initialize(0xFFDC143C, 25)";
mostCurrent._cdbuttoncancel.Initialize((int) (0xffdc143c),(int) (25));
 //BA.debugLineNum = 3502;BA.debugLine="btnNegativeCancelPost.Background = cdButtonCancel";
mostCurrent._btnnegativecancelpost.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdbuttoncancel.getObject()));
 //BA.debugLineNum = 3504;BA.debugLine="cdButtonSaveAndPrint.Initialize(0xFF1976D2, 25)";
mostCurrent._cdbuttonsaveandprint.Initialize((int) (0xff1976d2),(int) (25));
 //BA.debugLineNum = 3505;BA.debugLine="btnNegativeSelect.Background = cdButtonSaveAndPri";
mostCurrent._btnnegativeselect.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdbuttonsaveandprint.getObject()));
 //BA.debugLineNum = 3507;BA.debugLine="cdTxtBox.Initialize2(Colors.White, 0, 0, Colors.T";
mostCurrent._cdtxtbox.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.White,(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 3508;BA.debugLine="txtOthers.Background = cdTxtBox";
mostCurrent._txtothers.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdtxtbox.getObject()));
 //BA.debugLineNum = 3510;BA.debugLine="pnlNegativeMsgBox.Visible = True";
mostCurrent._pnlnegativemsgbox.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 3511;BA.debugLine="txtOthers.Text = \"\"";
mostCurrent._txtothers.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 3512;BA.debugLine="opt0.Checked = False";
mostCurrent._opt0.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3513;BA.debugLine="opt1.Checked = False";
mostCurrent._opt1.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3514;BA.debugLine="opt2.Checked = False";
mostCurrent._opt2.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3515;BA.debugLine="opt3.Checked = True";
mostCurrent._opt3.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 3516;BA.debugLine="opt4.Checked = False";
mostCurrent._opt4.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3517;BA.debugLine="opt5.Checked = False";
mostCurrent._opt5.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3518;BA.debugLine="opt6.Checked = False";
mostCurrent._opt6.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3519;BA.debugLine="opt7.Checked = False";
mostCurrent._opt7.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3520;BA.debugLine="opt8.Checked = False";
mostCurrent._opt8.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3522;BA.debugLine="If  Not(TalkBackIsActive) Then";
if (anywheresoftware.b4a.keywords.Common.Not(_talkbackisactive())) { 
 //BA.debugLineNum = 3523;BA.debugLine="TTS1.Speak(sMsg, False)";
_tts1.Speak(_smsg,anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 3525;BA.debugLine="End Sub";
return "";
}
public static String  _showpicrequireddialog() throws Exception{
 //BA.debugLineNum = 5441;BA.debugLine="Private Sub ShowPicRequiredDialog";
 //BA.debugLineNum = 5442;BA.debugLine="soundsAlarmChannel.Play(SoundID,1,1,1,0,1)";
_soundsalarmchannel.Play(_soundid,(float) (1),(float) (1),(int) (1),(int) (0),(float) (1));
 //BA.debugLineNum = 5444;BA.debugLine="cdButtonCancel.Initialize(0xFFDC143C, 25)";
mostCurrent._cdbuttoncancel.Initialize((int) (0xffdc143c),(int) (25));
 //BA.debugLineNum = 5445;BA.debugLine="btnAveCancelPost.Background = cdButtonCancel";
mostCurrent._btnavecancelpost.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdbuttoncancel.getObject()));
 //BA.debugLineNum = 5447;BA.debugLine="cdButtonSaveAndPrint.Initialize(0xFF1976D2, 25)";
mostCurrent._cdbuttonsaveandprint.Initialize((int) (0xff1976d2),(int) (25));
 //BA.debugLineNum = 5448;BA.debugLine="btnAveTakePicture.Background = cdButtonSaveAndPri";
mostCurrent._btnavetakepicture.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdbuttonsaveandprint.getObject()));
 //BA.debugLineNum = 5450;BA.debugLine="pnlAveMsgBox.Visible = True";
mostCurrent._pnlavemsgbox.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 5451;BA.debugLine="End Sub";
return "";
}
public static String  _showsavezeroreadingdialog() throws Exception{
String _smsg = "";
 //BA.debugLineNum = 3673;BA.debugLine="Private Sub ShowSaveZeroReadingDialog";
 //BA.debugLineNum = 3674;BA.debugLine="Dim sMsg = $\"WARNING! Zero Consumption Detected,";
_smsg = ("WARNING! Zero Consumption Detected, Please Check the Water Meter");
 //BA.debugLineNum = 3676;BA.debugLine="soundsAlarmChannel.Play(SoundID,1,1,1,0,1)";
_soundsalarmchannel.Play(_soundid,(float) (1),(float) (1),(int) (1),(int) (0),(float) (1));
 //BA.debugLineNum = 3678;BA.debugLine="cdButtonCancel.Initialize(0xFFDC143C, 25)";
mostCurrent._cdbuttoncancel.Initialize((int) (0xffdc143c),(int) (25));
 //BA.debugLineNum = 3679;BA.debugLine="btnZeroCancelPost.Background = cdButtonCancel";
mostCurrent._btnzerocancelpost.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdbuttoncancel.getObject()));
 //BA.debugLineNum = 3681;BA.debugLine="cdButtonSaveOnly.Initialize(0xFF14C0DB, 25)";
mostCurrent._cdbuttonsaveonly.Initialize((int) (0xff14c0db),(int) (25));
 //BA.debugLineNum = 3682;BA.debugLine="btnZeroSaveOnly.Background = cdButtonSaveOnly";
mostCurrent._btnzerosaveonly.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdbuttonsaveonly.getObject()));
 //BA.debugLineNum = 3684;BA.debugLine="cdButtonSaveAndPrint.Initialize(0xFF1976D2, 25)";
mostCurrent._cdbuttonsaveandprint.Initialize((int) (0xff1976d2),(int) (25));
 //BA.debugLineNum = 3685;BA.debugLine="btnZeroSaveAndPrint.Background = cdButtonSaveAndP";
mostCurrent._btnzerosaveandprint.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdbuttonsaveandprint.getObject()));
 //BA.debugLineNum = 3687;BA.debugLine="cdTxtBox.Initialize2(Colors.White, 0, 0, Colors.T";
mostCurrent._cdtxtbox.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.White,(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 3688;BA.debugLine="txtRemarks.Background = cdTxtBox";
mostCurrent._txtremarks.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdtxtbox.getObject()));
 //BA.debugLineNum = 3689;BA.debugLine="txtRemarks.Text = \"\"";
mostCurrent._txtremarks.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 3690;BA.debugLine="strReadRemarks = \"\"";
mostCurrent._strreadremarks = "";
 //BA.debugLineNum = 3691;BA.debugLine="txtRemarks.RequestFocus";
mostCurrent._txtremarks.RequestFocus();
 //BA.debugLineNum = 3692;BA.debugLine="IMEkeyboard.ShowKeyboard(txtRemarks)";
mostCurrent._imekeyboard.ShowKeyboard((android.view.View)(mostCurrent._txtremarks.getObject()));
 //BA.debugLineNum = 3694;BA.debugLine="pnlZeroMsgBox.Visible = True";
mostCurrent._pnlzeromsgbox.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 3695;BA.debugLine="btnZeroSaveOnly.Enabled = False";
mostCurrent._btnzerosaveonly.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3696;BA.debugLine="btnZeroSaveAndPrint.Enabled = False";
mostCurrent._btnzerosaveandprint.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3697;BA.debugLine="If  Not(TalkBackIsActive) Then";
if (anywheresoftware.b4a.keywords.Common.Not(_talkbackisactive())) { 
 //BA.debugLineNum = 3698;BA.debugLine="TTS1.Speak(sMsg, False)";
_tts1.Speak(_smsg,anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 3700;BA.debugLine="End Sub";
return "";
}
public static String  _showsuperhbconfirmation() throws Exception{
String _smsg = "";
 //BA.debugLineNum = 5864;BA.debugLine="Private Sub ShowSuperHBConfirmation";
 //BA.debugLineNum = 5865;BA.debugLine="Dim sMsg = $\"FINAL WARNING! You are about to bill";
_smsg = ("FINAL WARNING! You are about to bill a Customer with more than 100 Cubic Meter!");
 //BA.debugLineNum = 5866;BA.debugLine="soundsAlarmChannel.Play(SoundID,1,1,1,0,1)";
_soundsalarmchannel.Play(_soundid,(float) (1),(float) (1),(int) (1),(int) (0),(float) (1));
 //BA.debugLineNum = 5867;BA.debugLine="vibration.vibratePattern(vibratePattern,0)";
mostCurrent._vibration.vibratePattern(processBA,_vibratepattern,(int) (0));
 //BA.debugLineNum = 5868;BA.debugLine="pnlSuperHB.Visible = True";
mostCurrent._pnlsuperhb.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 5869;BA.debugLine="If  Not(TalkBackIsActive) Then";
if (anywheresoftware.b4a.keywords.Common.Not(_talkbackisactive())) { 
 //BA.debugLineNum = 5870;BA.debugLine="TTS1.Speak(sMsg, False)";
_tts1.Speak(_smsg,anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 5873;BA.debugLine="cdConfirmRdg.Initialize2(Colors.Black, 0,0, Color";
mostCurrent._cdconfirmrdg.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.Black,(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 5874;BA.debugLine="txtSuperHBPresRdg.Background = cdConfirmRdg";
mostCurrent._txtsuperhbpresrdg.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdconfirmrdg.getObject()));
 //BA.debugLineNum = 5876;BA.debugLine="cdButtonCancel.Initialize(0xFFDC143C, 15)";
mostCurrent._cdbuttoncancel.Initialize((int) (0xffdc143c),(int) (15));
 //BA.debugLineNum = 5877;BA.debugLine="btnSuperHBCancel.Background = cdButtonCancel";
mostCurrent._btnsuperhbcancel.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdbuttoncancel.getObject()));
 //BA.debugLineNum = 5879;BA.debugLine="cdButtonSaveAndPrint.Initialize(0xFF1976D2, 25)";
mostCurrent._cdbuttonsaveandprint.Initialize((int) (0xff1976d2),(int) (25));
 //BA.debugLineNum = 5880;BA.debugLine="btnSuperHBSave.Background = cdButtonSaveAndPrint";
mostCurrent._btnsuperhbsave.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdbuttonsaveandprint.getObject()));
 //BA.debugLineNum = 5882;BA.debugLine="txtSuperHBPresRdg.Text = \"\"";
mostCurrent._txtsuperhbpresrdg.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 5883;BA.debugLine="btnSuperHBSave.Enabled = False";
mostCurrent._btnsuperhbsave.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 5884;BA.debugLine="txtSuperHBPresRdg.RequestFocus";
mostCurrent._txtsuperhbpresrdg.RequestFocus();
 //BA.debugLineNum = 5885;BA.debugLine="cKeyboard.HideKeyboard";
mostCurrent._ckeyboard.HideKeyboard();
 //BA.debugLineNum = 5886;BA.debugLine="IMEkeyboard.ShowKeyboard(txtSuperHBPresRdg)";
mostCurrent._imekeyboard.ShowKeyboard((android.view.View)(mostCurrent._txtsuperhbpresrdg.getObject()));
 //BA.debugLineNum = 5888;BA.debugLine="Select Case intSaveOnly";
switch (_intsaveonly) {
case 0: {
 //BA.debugLineNum = 5890;BA.debugLine="btnSuperHBSave.Text = \"SAVE\"";
mostCurrent._btnsuperhbsave.setText(BA.ObjectToCharSequence("SAVE"));
 break; }
case 1: {
 //BA.debugLineNum = 5892;BA.debugLine="btnSuperHBSave.Text = \"SAVE AND PRINT\"";
mostCurrent._btnsuperhbsave.setText(BA.ObjectToCharSequence("SAVE AND PRINT"));
 break; }
}
;
 //BA.debugLineNum = 5895;BA.debugLine="End Sub";
return "";
}
public static String  _startprinter() throws Exception{
int _i = 0;
 //BA.debugLineNum = 4746;BA.debugLine="Sub StartPrinter";
 //BA.debugLineNum = 4748;BA.debugLine="PairedDevices.Initialize";
_paireddevices.Initialize();
 //BA.debugLineNum = 4750;BA.debugLine="Try";
try { //BA.debugLineNum = 4751;BA.debugLine="PairedDevices = Serial1.GetPairedDevices";
_paireddevices = _serial1.GetPairedDevices();
 } 
       catch (Exception e5) {
			processBA.setLastException(e5); //BA.debugLineNum = 4754;BA.debugLine="DispInfoMsg(\"Getting Paired Devices\",\"Printer Er";
_dispinfomsg("Getting Paired Devices","Printer Error");
 //BA.debugLineNum = 4755;BA.debugLine="TMPrinter.Close";
_tmprinter.Close();
 //BA.debugLineNum = 4756;BA.debugLine="Serial1.Disconnect";
_serial1.Disconnect();
 };
 //BA.debugLineNum = 4759;BA.debugLine="If PairedDevices.Size = 0 Then";
if (_paireddevices.getSize()==0) { 
 //BA.debugLineNum = 4760;BA.debugLine="DispInfoMsg(\"ERROR!\" & CRLF & $\"No paired Blueto";
_dispinfomsg("ERROR!"+anywheresoftware.b4a.keywords.Common.CRLF+("No paired Bluetooth Printer..."),("NO CONNECTED PRINTER"));
 //BA.debugLineNum = 4761;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 4762;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 4765;BA.debugLine="If PairedDevices.Size = 1 Then";
if (_paireddevices.getSize()==1) { 
 //BA.debugLineNum = 4767;BA.debugLine="Try";
try { //BA.debugLineNum = 4768;BA.debugLine="DeviceName=PairedDevices.Getkeyat(0)";
_devicename = BA.ObjectToString(_paireddevices.GetKeyAt((int) (0)));
 //BA.debugLineNum = 4769;BA.debugLine="DeviceMac=PairedDevices.GetValueAt(0)";
_devicemac = BA.ObjectToString(_paireddevices.GetValueAt((int) (0)));
 //BA.debugLineNum = 4770;BA.debugLine="Log(DeviceName & \" -> \" & DeviceMac)";
anywheresoftware.b4a.keywords.Common.LogImpl("128246040",_devicename+" -> "+_devicemac,0);
 //BA.debugLineNum = 4773;BA.debugLine="Serial1.ConnectInsecure(BTAdmin, DeviceMac,1)";
_serial1.ConnectInsecure(processBA,_btadmin,_devicemac,(int) (1));
 //BA.debugLineNum = 4774;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 } 
       catch (Exception e22) {
			processBA.setLastException(e22); //BA.debugLineNum = 4777;BA.debugLine="DispInfoMsg(\"Connecting\",\"Printer Error\")";
_dispinfomsg("Connecting","Printer Error");
 //BA.debugLineNum = 4778;BA.debugLine="TMPrinter.Close";
_tmprinter.Close();
 //BA.debugLineNum = 4779;BA.debugLine="Serial1.Disconnect";
_serial1.Disconnect();
 };
 }else {
 //BA.debugLineNum = 4782;BA.debugLine="FoundDevices.Initialize";
_founddevices.Initialize();
 //BA.debugLineNum = 4784;BA.debugLine="For i = 0 To PairedDevices.Size - 1";
{
final int step28 = 1;
final int limit28 = (int) (_paireddevices.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit28 ;_i = _i + step28 ) {
 //BA.debugLineNum = 4785;BA.debugLine="FoundDevices.Add(PairedDevices.GetKeyAt(i))";
_founddevices.Add(_paireddevices.GetKeyAt(_i));
 //BA.debugLineNum = 4786;BA.debugLine="DeviceName=PairedDevices.Getkeyat(i)";
_devicename = BA.ObjectToString(_paireddevices.GetKeyAt(_i));
 //BA.debugLineNum = 4787;BA.debugLine="DeviceMac=PairedDevices.GetValueAt(i)";
_devicemac = BA.ObjectToString(_paireddevices.GetValueAt(_i));
 //BA.debugLineNum = 4788;BA.debugLine="Log(DeviceName & \" -> \" & DeviceMac)";
anywheresoftware.b4a.keywords.Common.LogImpl("128246058",_devicename+" -> "+_devicemac,0);
 //BA.debugLineNum = 4790;BA.debugLine="Serial1.ConnectInsecure(BTAdmin, DeviceMac,1)";
_serial1.ConnectInsecure(processBA,_btadmin,_devicemac,(int) (1));
 //BA.debugLineNum = 4791;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 4792;BA.debugLine="Exit";
if (true) break;
 }
};
 //BA.debugLineNum = 4796;BA.debugLine="Res = InputList(FoundDevices, \"Choose Device\", -";
_res = anywheresoftware.b4a.keywords.Common.InputList(_founddevices,BA.ObjectToCharSequence("Choose Device"),(int) (-1),mostCurrent.activityBA);
 //BA.debugLineNum = 4798;BA.debugLine="If Res <> DialogResponse.CANCEL Then";
if (_res!=anywheresoftware.b4a.keywords.Common.DialogResponse.CANCEL) { 
 //BA.debugLineNum = 4799;BA.debugLine="Serial1.Connect(PairedDevices.Get(FoundDevices.";
_serial1.Connect(processBA,BA.ObjectToString(_paireddevices.Get(_founddevices.Get(_res))));
 };
 };
 //BA.debugLineNum = 4802;BA.debugLine="End Sub";
return "";
}
public static String  _sv_itemclick(String _value) throws Exception{
 //BA.debugLineNum = 1842;BA.debugLine="Sub SV_ItemClick(Value As String)";
 //BA.debugLineNum = 1843;BA.debugLine="Log(Value)";
anywheresoftware.b4a.keywords.Common.LogImpl("123855105",_value,0);
 //BA.debugLineNum = 1844;BA.debugLine="SearchFor = Value";
mostCurrent._searchfor = _value;
 //BA.debugLineNum = 1845;BA.debugLine="FindSearchRetValue(SearchFor)";
_findsearchretvalue(mostCurrent._searchfor);
 //BA.debugLineNum = 1846;BA.debugLine="SV.ClearAll";
mostCurrent._sv._clearall /*String*/ ();
 //BA.debugLineNum = 1847;BA.debugLine="SearchClosed";
_searchclosed();
 //BA.debugLineNum = 1848;BA.debugLine="End Sub";
return "";
}
public static boolean  _talkbackisactive() throws Exception{
boolean _blnreturnvalue = false;
anywheresoftware.b4j.object.JavaObject _context = null;
anywheresoftware.b4j.object.JavaObject _accessibilitymanager = null;
anywheresoftware.b4a.objects.collections.List _services = null;
 //BA.debugLineNum = 5969;BA.debugLine="Sub TalkBackIsActive As Boolean";
 //BA.debugLineNum = 5971;BA.debugLine="Dim blnReturnValue As Boolean = False";
_blnreturnvalue = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 5972;BA.debugLine="Dim context As JavaObject";
_context = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 5973;BA.debugLine="Dim AccessibilityManager As JavaObject = context.";
_accessibilitymanager = new anywheresoftware.b4j.object.JavaObject();
_accessibilitymanager = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(_context.InitializeContext(processBA).RunMethod("getSystemService",new Object[]{(Object)("accessibility")})));
 //BA.debugLineNum = 5976;BA.debugLine="If AccessibilityManager.IsInitialized Then";
if (_accessibilitymanager.IsInitialized()) { 
 //BA.debugLineNum = 5977;BA.debugLine="Dim services As List = AccessibilityManager.RunM";
_services = new anywheresoftware.b4a.objects.collections.List();
_services = (anywheresoftware.b4a.objects.collections.List) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.List(), (java.util.List)(_accessibilitymanager.RunMethod("getEnabledAccessibilityServiceList",new Object[]{(Object)(1)})));
 //BA.debugLineNum = 5980;BA.debugLine="If services.Size > 0 Then";
if (_services.getSize()>0) { 
 //BA.debugLineNum = 5981;BA.debugLine="blnReturnValue = True";
_blnreturnvalue = anywheresoftware.b4a.keywords.Common.True;
 };
 };
 //BA.debugLineNum = 5984;BA.debugLine="LogColor (blnReturnValue, Colors.Yellow)";
anywheresoftware.b4a.keywords.Common.LogImpl("133161231",BA.ObjectToString(_blnreturnvalue),anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 //BA.debugLineNum = 5985;BA.debugLine="Return blnReturnValue";
if (true) return _blnreturnvalue;
 //BA.debugLineNum = 5986;BA.debugLine="End Sub";
return false;
}
public static String  _timer1_tick() throws Exception{
 //BA.debugLineNum = 5832;BA.debugLine="Private Sub Timer1_Tick";
 //BA.debugLineNum = 5834;BA.debugLine="End Sub";
return "";
}
public static String  _titlecase(String _s) throws Exception{
anywheresoftware.b4a.keywords.Regex.MatcherWrapper _m = null;
int _i = 0;
 //BA.debugLineNum = 6057;BA.debugLine="Sub TitleCase (s As String) As String";
 //BA.debugLineNum = 6058;BA.debugLine="s = s.ToLowerCase";
_s = _s.toLowerCase();
 //BA.debugLineNum = 6059;BA.debugLine="Dim m As Matcher = Regex.Matcher(\"\\b(\\w)\", s)";
_m = new anywheresoftware.b4a.keywords.Regex.MatcherWrapper();
_m = anywheresoftware.b4a.keywords.Common.Regex.Matcher("\\b(\\w)",_s);
 //BA.debugLineNum = 6060;BA.debugLine="Do While m.Find";
while (_m.Find()) {
 //BA.debugLineNum = 6061;BA.debugLine="Dim i As Int = m.GetStart(1)";
_i = _m.GetStart((int) (1));
 //BA.debugLineNum = 6062;BA.debugLine="s = s.SubString2(0, i) & s.SubString2(i, i + 1).";
_s = _s.substring((int) (0),_i)+_s.substring(_i,(int) (_i+1)).toUpperCase()+_s.substring((int) (_i+1));
 }
;
 //BA.debugLineNum = 6064;BA.debugLine="Return s";
if (true) return _s;
 //BA.debugLineNum = 6065;BA.debugLine="End Sub";
return "";
}
public static String  _toolbar_menuitemclick(de.amberhome.objects.appcompat.ACMenuItemWrapper _item) throws Exception{
 //BA.debugLineNum = 741;BA.debugLine="Sub ToolBar_MenuItemClick (Item As ACMenuItem)";
 //BA.debugLineNum = 742;BA.debugLine="cKeyboard.HideKeyboard";
mostCurrent._ckeyboard.HideKeyboard();
 //BA.debugLineNum = 743;BA.debugLine="Select Item.Id";
switch (BA.switchObjectToInt(_item.getId(),(int) (1),(int) (2),(int) (3))) {
case 0: {
 //BA.debugLineNum = 745;BA.debugLine="If pnlSearch.Visible=True Then Return";
if (mostCurrent._pnlsearch.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
if (true) return "";};
 //BA.debugLineNum = 746;BA.debugLine="intTempCurrPos = rsLoadedBook.Position";
_inttempcurrpos = mostCurrent._rsloadedbook.getPosition();
 //BA.debugLineNum = 747;BA.debugLine="cKeyboard.HideKeyboard";
mostCurrent._ckeyboard.HideKeyboard();
 //BA.debugLineNum = 748;BA.debugLine="DisableControls";
_disablecontrols();
 //BA.debugLineNum = 749;BA.debugLine="SV.Initialize(Me,\"SV\")";
mostCurrent._sv._initialize /*String*/ (mostCurrent.activityBA,meterreading.getObject(),"SV");
 //BA.debugLineNum = 750;BA.debugLine="SV.AddToParent(SearchPanel, 2%x, 0.5%y, SearchP";
mostCurrent._sv._addtoparent /*String*/ (mostCurrent._searchpanel,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (2),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (0.5),mostCurrent.activityBA),(int) (mostCurrent._searchpanel.getWidth()-anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (2),mostCurrent.activityBA)),(int) (mostCurrent._searchpanel.getHeight()-anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (0.5),mostCurrent.activityBA)));
 //BA.debugLineNum = 751;BA.debugLine="SV.ClearAll";
mostCurrent._sv._clearall /*String*/ ();
 //BA.debugLineNum = 752;BA.debugLine="IMEkeyboard.ShowKeyboard(SV.et)";
mostCurrent._imekeyboard.ShowKeyboard((android.view.View)(mostCurrent._sv._et /*anywheresoftware.b4a.objects.EditTextWrapper*/ .getObject()));
 //BA.debugLineNum = 754;BA.debugLine="pnlSearch.Visible=True";
mostCurrent._pnlsearch.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 755;BA.debugLine="optUnread.Checked = True";
mostCurrent._optunread.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 756;BA.debugLine="optSeqNo.Checked=True";
mostCurrent._optseqno.setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 757;BA.debugLine="optSeqNo_CheckedChange(True)";
_optseqno_checkedchange(anywheresoftware.b4a.keywords.Common.True);
 break; }
case 1: {
 //BA.debugLineNum = 759;BA.debugLine="PopWarnings.Show";
mostCurrent._popwarnings.Show();
 break; }
case 2: {
 //BA.debugLineNum = 761;BA.debugLine="PopSubMenu.Show";
mostCurrent._popsubmenu.Show();
 break; }
}
;
 //BA.debugLineNum = 763;BA.debugLine="End Sub";
return "";
}
public static String  _toolbar_navigationitemclick() throws Exception{
 //BA.debugLineNum = 710;BA.debugLine="Sub ToolBar_NavigationItemClick";
 //BA.debugLineNum = 711;BA.debugLine="cKeyboard.HideKeyboard";
mostCurrent._ckeyboard.HideKeyboard();
 //BA.debugLineNum = 714;BA.debugLine="If pnlSearch.Visible = True Then";
if (mostCurrent._pnlsearch.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 715;BA.debugLine="btnCancelSearch_Click";
_btncancelsearch_click();
 //BA.debugLineNum = 716;BA.debugLine="IMEkeyboard.HideKeyboard";
mostCurrent._imekeyboard.HideKeyboard(mostCurrent.activityBA);
 }else if(mostCurrent._pnlunusual.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 718;BA.debugLine="btnCancelUnusual_Click";
_btncancelunusual_click();
 //BA.debugLineNum = 719;BA.debugLine="IMEkeyboard.HideKeyboard";
mostCurrent._imekeyboard.HideKeyboard(mostCurrent.activityBA);
 }else if(mostCurrent._pnlnegativemsgbox.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 721;BA.debugLine="btnNegativeCancelPost_Click";
_btnnegativecancelpost_click();
 }else if(mostCurrent._pnlhimsgbox.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 723;BA.debugLine="btnHiCancelPost_Click";
_btnhicancelpost_click();
 }else if(mostCurrent._pnlhighbillconfirmation.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 725;BA.debugLine="btnHBConfirmCancel_Click";
_btnhbconfirmcancel_click();
 }else if(mostCurrent._pnlmiscodemsgbox.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 727;BA.debugLine="btnMisCodeCancelPost_Click";
_btnmiscodecancelpost_click();
 }else if(mostCurrent._pnlavemsgbox.getVisible()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 729;BA.debugLine="btnAveCancelPost_Click";
_btnavecancelpost_click();
 }else if(_blnedit==anywheresoftware.b4a.keywords.Common.True && mostCurrent._txtpresrdg.getEnabled()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 731;BA.debugLine="rsLoadedBook.Position = intCurrPos";
mostCurrent._rsloadedbook.setPosition(_intcurrpos);
 //BA.debugLineNum = 732;BA.debugLine="DisplayRecord";
_displayrecord();
 //BA.debugLineNum = 733;BA.debugLine="ButtonState";
_buttonstate();
 }else {
 //BA.debugLineNum = 735;BA.debugLine="IMEkeyboard.HideKeyboard";
mostCurrent._imekeyboard.HideKeyboard(mostCurrent.activityBA);
 //BA.debugLineNum = 737;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 739;BA.debugLine="End Sub";
return "";
}
public static String  _tts1_ready(boolean _success) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
 //BA.debugLineNum = 5958;BA.debugLine="Sub TTS1_Ready (Success As Boolean)";
 //BA.debugLineNum = 5959;BA.debugLine="If Success Then";
if (_success) { 
 //BA.debugLineNum = 5960;BA.debugLine="Dim R As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 5961;BA.debugLine="R.Target = TTS1";
_r.Target = (Object)(_tts1.getObject());
 //BA.debugLineNum = 5962;BA.debugLine="R.RunMethod2(\"setEngineByPackageName\", \"com.goog";
_r.RunMethod2("setEngineByPackageName","com.google.android.tts","java.lang.String");
 }else {
 //BA.debugLineNum = 5964;BA.debugLine="Msgbox(\"Error initializing TTS engine.\", \"\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Error initializing TTS engine."),BA.ObjectToCharSequence(""),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 5966;BA.debugLine="End Sub";
return "";
}
public static String  _txtmiscode_enterpressed() throws Exception{
 //BA.debugLineNum = 2220;BA.debugLine="Sub txtMisCode_EnterPressed";
 //BA.debugLineNum = 2221;BA.debugLine="If btnMisCodeSave.Enabled = True Then";
if (mostCurrent._btnmiscodesave.getEnabled()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2222;BA.debugLine="btnMisCodeSave_Click";
_btnmiscodesave_click();
 };
 //BA.debugLineNum = 2224;BA.debugLine="End Sub";
return "";
}
public static String  _txtmiscode_textchanged(String _old,String _new) throws Exception{
 //BA.debugLineNum = 2208;BA.debugLine="Sub txtMisCode_TextChanged (Old As String, New As";
 //BA.debugLineNum = 2209;BA.debugLine="If optMisCode8.Checked = True Then";
if (mostCurrent._optmiscode8.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 2210;BA.debugLine="If GlobalVar.SF.Len(GlobalVar.SF.Trim(txtMisCode";
if (mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv4(mostCurrent._txtmiscode.getText()))<=0) { 
 //BA.debugLineNum = 2211;BA.debugLine="btnMisCodeSave.Enabled = False";
mostCurrent._btnmiscodesave.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 2213;BA.debugLine="btnMisCodeSave.Enabled = True";
mostCurrent._btnmiscodesave.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 };
 }else {
 //BA.debugLineNum = 2216;BA.debugLine="btnMisCodeSave.Enabled = True";
mostCurrent._btnmiscodesave.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 2218;BA.debugLine="End Sub";
return "";
}
public static String  _txtpresrdg_enterpressed() throws Exception{
 //BA.debugLineNum = 1505;BA.debugLine="Sub txtPresRdg_EnterPressed";
 //BA.debugLineNum = 1636;BA.debugLine="End Sub";
return "";
}
public static String  _txtpresrdg_focuschanged(boolean _hasfocus) throws Exception{
 //BA.debugLineNum = 1638;BA.debugLine="Sub txtPresRdg_FocusChanged (HasFocus As Boolean)";
 //BA.debugLineNum = 1639;BA.debugLine="If HasFocus = True Then";
if (_hasfocus==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1641;BA.debugLine="WasEdited = False";
_wasedited = anywheresoftware.b4a.keywords.Common.False;
 }else {
 //BA.debugLineNum = 1646;BA.debugLine="cKeyboard.HideKeyboard";
mostCurrent._ckeyboard.HideKeyboard();
 };
 //BA.debugLineNum = 1649;BA.debugLine="End Sub";
return "";
}
public static boolean  _txtpresrdg_handleaction() throws Exception{
int _itotalcum = 0;
double _dratesheaderid = 0;
 //BA.debugLineNum = 1391;BA.debugLine="Private Sub txtPresRdg_HandleAction() As Boolean";
 //BA.debugLineNum = 1392;BA.debugLine="Dim iTotalCuM As Int";
_itotalcum = 0;
 //BA.debugLineNum = 1393;BA.debugLine="Dim dRatesHeaderID As Double";
_dratesheaderid = 0;
 //BA.debugLineNum = 1396;BA.debugLine="intCurrPos = rsLoadedBook.Position";
_intcurrpos = mostCurrent._rsloadedbook.getPosition();
 //BA.debugLineNum = 1399;BA.debugLine="If AcctStatus = \"dc\" Then";
if ((mostCurrent._acctstatus).equals("dc")) { 
 //BA.debugLineNum = 1400;BA.debugLine="txtPresRdg.Text = FinalRdg";
mostCurrent._txtpresrdg.setText(BA.ObjectToCharSequence(_finalrdg));
 };
 //BA.debugLineNum = 1403;BA.debugLine="LogColor($\"Pres Rdg: \"$ & txtPresRdg.Text, Colors";
anywheresoftware.b4a.keywords.Common.LogImpl("122806540",("Pres Rdg: ")+mostCurrent._txtpresrdg.getText(),anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 1405;BA.debugLine="cKeyboard.HideKeyboard";
mostCurrent._ckeyboard.HideKeyboard();
 //BA.debugLineNum = 1407;BA.debugLine="If strSF.Len(strSF.Trim(txtPresRdg.Text)) <= 0 Th";
if (mostCurrent._strsf._vvv7(mostCurrent._strsf._vvvvvvv4(mostCurrent._txtpresrdg.getText()))<=0) { 
 //BA.debugLineNum = 1408;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 1412;BA.debugLine="If IsNumber(txtPresRdg.Text) = False Then";
if (anywheresoftware.b4a.keywords.Common.IsNumber(mostCurrent._txtpresrdg.getText())==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 1413;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 1418;BA.debugLine="CurrentCuM = strSF.Val(txtPresRdg.Text) - strSF.V";
_currentcum = (int) (mostCurrent._strsf._vvvvvvv6(mostCurrent._txtpresrdg.getText())-mostCurrent._strsf._vvvvvvv6(BA.NumberToString(_prevrdg)));
 //BA.debugLineNum = 1419;BA.debugLine="LogColor($\"Prev Rdg: \"$ & PrevRdg, Colors.Magenta";
anywheresoftware.b4a.keywords.Common.LogImpl("122806556",("Prev Rdg: ")+BA.NumberToString(_prevrdg),anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 1420;BA.debugLine="LogColor($\"Current CuM: \"$ & CurrentCuM, Colors.C";
anywheresoftware.b4a.keywords.Common.LogImpl("122806557",("Current CuM: ")+BA.NumberToString(_currentcum),anywheresoftware.b4a.keywords.Common.Colors.Cyan);
 //BA.debugLineNum = 1421;BA.debugLine="iTotalCuM = CurrentCuM + AddCons";
_itotalcum = (int) (_currentcum+_addcons);
 //BA.debugLineNum = 1422;BA.debugLine="LogColor($\"Total CuM: \"$ & iTotalCuM, Colors.Mage";
anywheresoftware.b4a.keywords.Common.LogImpl("122806559",("Total CuM: ")+BA.NumberToString(_itotalcum),anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 1425;BA.debugLine="If Not(DBaseFunctions.HasRateHeader(GlobalVar.Bra";
if (anywheresoftware.b4a.keywords.Common.Not(mostCurrent._dbasefunctions._hasrateheader /*boolean*/ (mostCurrent.activityBA,mostCurrent._globalvar._branchid /*int*/ ,mostCurrent._acctclass,mostCurrent._acctsubclass))) { 
 //BA.debugLineNum = 1426;BA.debugLine="vibration.vibratePattern(vibratePattern, 0)";
mostCurrent._vibration.vibratePattern(processBA,_vibratepattern,(int) (0));
 //BA.debugLineNum = 1427;BA.debugLine="txtPresRdg.Text = \"\"";
mostCurrent._txtpresrdg.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 1428;BA.debugLine="txtPresRdg.Enabled = False";
mostCurrent._txtpresrdg.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1429;BA.debugLine="WarningMsg($\"NO RATES FOUND!\"$, $\"Customer accou";
_warningmsg(("NO RATES FOUND!"),("Customer account classification has no rates found!")+anywheresoftware.b4a.keywords.Common.CRLF+("Please ask assistance to the Central Billing & IT Department regarding this."));
 //BA.debugLineNum = 1430;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 1433;BA.debugLine="dRatesHeaderID = DBaseFunctions.GetRatesHeaderID";
_dratesheaderid = mostCurrent._dbasefunctions._getratesheaderid /*int*/ (mostCurrent.activityBA,mostCurrent._acctclass,mostCurrent._acctsubclass);
 //BA.debugLineNum = 1436;BA.debugLine="If Not(DBaseFunctions.HasRateDetails(dRatesHeade";
if (anywheresoftware.b4a.keywords.Common.Not(mostCurrent._dbasefunctions._hasratedetails /*boolean*/ (mostCurrent.activityBA,_dratesheaderid))) { 
 //BA.debugLineNum = 1437;BA.debugLine="vibration.vibratePattern(vibratePattern, 0)";
mostCurrent._vibration.vibratePattern(processBA,_vibratepattern,(int) (0));
 //BA.debugLineNum = 1438;BA.debugLine="WarningMsg($\"NO RATES FOUND!\"$, $\"Customer acco";
_warningmsg(("NO RATES FOUND!"),("Customer account classification has no rates found!")+anywheresoftware.b4a.keywords.Common.CRLF+("Please ask assistance to the Central Billing & IT Department regarding this."));
 //BA.debugLineNum = 1439;BA.debugLine="txtPresRdg.Text = \"\"";
mostCurrent._txtpresrdg.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 1440;BA.debugLine="txtPresRdg.Enabled = False";
mostCurrent._txtpresrdg.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1441;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 };
 //BA.debugLineNum = 1445;BA.debugLine="If HasSeptageFee = 1 Then";
if (_hasseptagefee==1) { 
 //BA.debugLineNum = 1446;BA.debugLine="LogColor ($\"Acct Classification: \"$ & AcctClassi";
anywheresoftware.b4a.keywords.Common.LogImpl("122806583",("Acct Classification: ")+mostCurrent._acctclassification,anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 //BA.debugLineNum = 1448;BA.debugLine="If GlobalVar.BranchID = 15 Then";
if (mostCurrent._globalvar._branchid /*int*/ ==15) { 
 }else {
 //BA.debugLineNum = 1450;BA.debugLine="GlobalVar.SeptageRateID = DBaseFunctions.GetSept";
mostCurrent._globalvar._septagerateid /*int*/  = mostCurrent._dbasefunctions._getseptagerateid /*int*/ (mostCurrent.activityBA,mostCurrent._acctclassification,mostCurrent._globalvar._branchid /*int*/ );
 //BA.debugLineNum = 1451;BA.debugLine="LogColor ($\"Septage Rate ID: \"$ & GlobalVar.Sept";
anywheresoftware.b4a.keywords.Common.LogImpl("122806588",("Septage Rate ID: ")+BA.NumberToString(mostCurrent._globalvar._septagerateid /*int*/ ),anywheresoftware.b4a.keywords.Common.Colors.Cyan);
 //BA.debugLineNum = 1453;BA.debugLine="If GlobalVar.SeptageRateID = 0 Then";
if (mostCurrent._globalvar._septagerateid /*int*/ ==0) { 
 //BA.debugLineNum = 1454;BA.debugLine="WarningMsg($\"NO SEPTAGE RATES FOUND!\"$, $\"Custo";
_warningmsg(("NO SEPTAGE RATES FOUND!"),("Customer account classification has no septage rates found!")+anywheresoftware.b4a.keywords.Common.CRLF+("Please ask assistance to the Central Billing & IT Department regarding this."));
 //BA.debugLineNum = 1455;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 };
 };
 //BA.debugLineNum = 1460;BA.debugLine="If CurrentCuM < 0 Or strSF.Val(txtPresRdg.Text) <";
if (_currentcum<0 || mostCurrent._strsf._vvvvvvv6(mostCurrent._txtpresrdg.getText())<mostCurrent._strsf._vvvvvvv6(BA.NumberToString(_prevrdg))) { 
 //BA.debugLineNum = 1461;BA.debugLine="vibration.vibratePattern(vibratePattern, 0)";
mostCurrent._vibration.vibratePattern(processBA,_vibratepattern,(int) (0));
 //BA.debugLineNum = 1462;BA.debugLine="WasImplosive = 1";
_wasimplosive = (int) (1);
 //BA.debugLineNum = 1463;BA.debugLine="ShowNegativeReadingDialog";
_shownegativereadingdialog();
 //BA.debugLineNum = 1464;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else if(_currentcum==0 || mostCurrent._strsf._vvvvvvv6(mostCurrent._txtpresrdg.getText())==mostCurrent._strsf._vvvvvvv6(BA.NumberToString(_prevrdg))) { 
 //BA.debugLineNum = 1467;BA.debugLine="vibration.vibratePattern(vibratePattern, 0)";
mostCurrent._vibration.vibratePattern(processBA,_vibratepattern,(int) (0));
 //BA.debugLineNum = 1468;BA.debugLine="WasImplosive = 1";
_wasimplosive = (int) (1);
 //BA.debugLineNum = 1469;BA.debugLine="ShowSaveZeroReadingDialog";
_showsavezeroreadingdialog();
 //BA.debugLineNum = 1470;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else if((_itotalcum-(double)(Double.parseDouble(mostCurrent._prevcum)))>=20) { 
 //BA.debugLineNum = 1473;BA.debugLine="If DBaseFunctions.IsWithinRange(CurrentCuM, Glob";
if (mostCurrent._dbasefunctions._iswithinrange /*boolean*/ (mostCurrent.activityBA,_currentcum,mostCurrent._globalvar._branchid /*int*/ ,mostCurrent._acctclass,mostCurrent._acctsubclass)==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 1474;BA.debugLine="vibration.vibratePattern(vibratePattern, 0)";
mostCurrent._vibration.vibratePattern(processBA,_vibratepattern,(int) (0));
 //BA.debugLineNum = 1475;BA.debugLine="WarningMsg($\"E R R O R!\"$, $\"Reading input Out";
_warningmsg(("E R R O R!"),("Reading input Out of Range!"));
 //BA.debugLineNum = 1476;BA.debugLine="txtPresRdg.SelectAll";
mostCurrent._txtpresrdg.SelectAll();
 //BA.debugLineNum = 1478;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 1481;BA.debugLine="If (iTotalCuM - PrevCum) >= 100 Then";
if ((_itotalcum-(double)(Double.parseDouble(mostCurrent._prevcum)))>=100) { 
 //BA.debugLineNum = 1482;BA.debugLine="blnSuperHighBill = True";
_blnsuperhighbill = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 1484;BA.debugLine="blnSuperHighBill = False";
_blnsuperhighbill = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 1487;BA.debugLine="vibration.vibratePattern(vibratePattern, 0)";
mostCurrent._vibration.vibratePattern(processBA,_vibratepattern,(int) (0));
 //BA.debugLineNum = 1488;BA.debugLine="WasImplosive = 1";
_wasimplosive = (int) (1);
 //BA.debugLineNum = 1489;BA.debugLine="ShowHighBillDialog";
_showhighbilldialog();
 //BA.debugLineNum = 1490;BA.debugLine="LogColor($\"Total CuM: \"$ & (iTotalCuM - PrevCum)";
anywheresoftware.b4a.keywords.Common.LogImpl("122806627",("Total CuM: ")+BA.NumberToString((_itotalcum-(double)(Double.parseDouble(mostCurrent._prevcum)))),anywheresoftware.b4a.keywords.Common.Colors.Cyan);
 //BA.debugLineNum = 1491;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else if(((double)(Double.parseDouble(mostCurrent._prevcum))-_itotalcum)>=20) { 
 //BA.debugLineNum = 1494;BA.debugLine="vibration.vibratePattern(vibratePattern, 0)";
mostCurrent._vibration.vibratePattern(processBA,_vibratepattern,(int) (0));
 //BA.debugLineNum = 1495;BA.debugLine="WasImplosive = 1";
_wasimplosive = (int) (1);
 //BA.debugLineNum = 1496;BA.debugLine="ShowLowBillDialog";
_showlowbilldialog();
 //BA.debugLineNum = 1497;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 1499;BA.debugLine="WasImplosive = 0";
_wasimplosive = (int) (0);
 //BA.debugLineNum = 1500;BA.debugLine="ShowAddNewReading";
_showaddnewreading();
 //BA.debugLineNum = 1501;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 1503;BA.debugLine="End Sub";
return false;
}
public static String  _txtpresrdg_textchanged(String _old,String _new) throws Exception{
 //BA.debugLineNum = 1377;BA.debugLine="Sub txtPresRdg_TextChanged (Old As String, New As";
 //BA.debugLineNum = 1378;BA.debugLine="If strSF.Len(txtPresRdg.Text.Trim) <= 0 Then";
if (mostCurrent._strsf._vvv7(mostCurrent._txtpresrdg.getText().trim())<=0) { 
 //BA.debugLineNum = 1379;BA.debugLine="lblPresCum.Text = \"\"";
mostCurrent._lblprescum.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 1380;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 1382;BA.debugLine="lblPresCum.Text = ComputeCumUsed";
mostCurrent._lblprescum.setText(BA.ObjectToCharSequence(_computecumused()));
 //BA.debugLineNum = 1389;BA.debugLine="End Sub";
return "";
}
public static String  _txtpresrdgconfirm_enterpressed() throws Exception{
 //BA.debugLineNum = 5066;BA.debugLine="Sub txtPresRdgConfirm_EnterPressed";
 //BA.debugLineNum = 5067;BA.debugLine="If btnHBConfirmSaveAndPrint.Enabled = False Then";
if (mostCurrent._btnhbconfirmsaveandprint.getEnabled()==anywheresoftware.b4a.keywords.Common.False) { 
if (true) return "";};
 //BA.debugLineNum = 5068;BA.debugLine="btnHBConfirmSaveAndPrint_Click";
_btnhbconfirmsaveandprint_click();
 //BA.debugLineNum = 5069;BA.debugLine="End Sub";
return "";
}
public static String  _txtpresrdgconfirm_textchanged(String _old,String _new) throws Exception{
 //BA.debugLineNum = 5058;BA.debugLine="Sub txtPresRdgConfirm_TextChanged (Old As String,";
 //BA.debugLineNum = 5059;BA.debugLine="If txtPresRdg.Text = New Or sPresentReading = New";
if ((mostCurrent._txtpresrdg.getText()).equals(_new) || (mostCurrent._spresentreading).equals(_new)) { 
 //BA.debugLineNum = 5060;BA.debugLine="btnHBConfirmSaveAndPrint.Enabled = True";
mostCurrent._btnhbconfirmsaveandprint.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 5062;BA.debugLine="btnHBConfirmSaveAndPrint.Enabled = False";
mostCurrent._btnhbconfirmsaveandprint.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 5064;BA.debugLine="End Sub";
return "";
}
public static String  _txtpresrdgconfirmreprint_enterpressed() throws Exception{
 //BA.debugLineNum = 5845;BA.debugLine="Sub txtPresRdgConfirmReprint_EnterPressed";
 //BA.debugLineNum = 5847;BA.debugLine="End Sub";
return "";
}
public static String  _txtpresrdgconfirmreprint_textchanged(String _old,String _new) throws Exception{
 //BA.debugLineNum = 5837;BA.debugLine="Sub txtPresRdgConfirmReprint_TextChanged (Old As S";
 //BA.debugLineNum = 5838;BA.debugLine="If txtPresRdg.Text = New Or sPresentReading = New";
if ((mostCurrent._txtpresrdg.getText()).equals(_new) || (mostCurrent._spresentreading).equals(_new)) { 
 //BA.debugLineNum = 5839;BA.debugLine="btnHBConfirmSaveAndRePrint.Enabled = True";
mostCurrent._btnhbconfirmsaveandreprint.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 5841;BA.debugLine="btnHBConfirmSaveAndRePrint.Enabled = False";
mostCurrent._btnhbconfirmsaveandreprint.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 5843;BA.debugLine="End Sub";
return "";
}
public static String  _txtreason_textchanged(String _old,String _new) throws Exception{
 //BA.debugLineNum = 5549;BA.debugLine="Sub txtReason_TextChanged (Old As String, New As S";
 //BA.debugLineNum = 5550;BA.debugLine="If optReason8.Checked = True Then";
if (mostCurrent._optreason8.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 5551;BA.debugLine="If txtReason.Text = \"\" Or GlobalVar.SF.Len(Globa";
if ((mostCurrent._txtreason.getText()).equals("") || mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv4(mostCurrent._txtreason.getText()))<=0) { 
 //BA.debugLineNum = 5552;BA.debugLine="btnAveRemSaveAndPrint.Enabled = False";
mostCurrent._btnaveremsaveandprint.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 5554;BA.debugLine="btnAveRemSaveAndPrint.Enabled = True";
mostCurrent._btnaveremsaveandprint.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 };
 };
 //BA.debugLineNum = 5557;BA.debugLine="End Sub";
return "";
}
public static String  _txtremarks_enterpressed() throws Exception{
 //BA.debugLineNum = 3706;BA.debugLine="Sub txtRemarks_EnterPressed";
 //BA.debugLineNum = 3707;BA.debugLine="If txtRemarks.Text = \"\" Or GlobalVar.SF.Len(Globa";
if ((mostCurrent._txtremarks.getText()).equals("") || mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv4(mostCurrent._txtremarks.getText()))<=0) { 
if (true) return "";};
 //BA.debugLineNum = 3708;BA.debugLine="End Sub";
return "";
}
public static String  _txtremarks_textchanged(String _old,String _new) throws Exception{
 //BA.debugLineNum = 3710;BA.debugLine="Sub txtRemarks_TextChanged (Old As String, New As";
 //BA.debugLineNum = 3711;BA.debugLine="If GlobalVar.SF.Len(GlobalVar.SF.Trim(txtRemarks.";
if (mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv4(mostCurrent._txtremarks.getText()))<=0) { 
 //BA.debugLineNum = 3712;BA.debugLine="btnZeroSaveOnly.Enabled = False";
mostCurrent._btnzerosaveonly.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 3713;BA.debugLine="btnZeroSaveAndPrint.Enabled = False";
mostCurrent._btnzerosaveandprint.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 3715;BA.debugLine="btnZeroSaveOnly.Enabled = True";
mostCurrent._btnzerosaveonly.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 3716;BA.debugLine="btnZeroSaveAndPrint.Enabled = True";
mostCurrent._btnzerosaveandprint.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 3718;BA.debugLine="End Sub";
return "";
}
public static String  _txtsuperhbpresrdg_enterpressed() throws Exception{
 //BA.debugLineNum = 5906;BA.debugLine="Sub txtSuperHBPresRdg_EnterPressed";
 //BA.debugLineNum = 5907;BA.debugLine="If btnSuperHBSave.Enabled = False Then Return";
if (mostCurrent._btnsuperhbsave.getEnabled()==anywheresoftware.b4a.keywords.Common.False) { 
if (true) return "";};
 //BA.debugLineNum = 5908;BA.debugLine="btnSuperHBSave_Click";
_btnsuperhbsave_click();
 //BA.debugLineNum = 5909;BA.debugLine="End Sub";
return "";
}
public static String  _txtsuperhbpresrdg_textchanged(String _old,String _new) throws Exception{
 //BA.debugLineNum = 5897;BA.debugLine="Sub txtSuperHBPresRdg_TextChanged (Old As String,";
 //BA.debugLineNum = 5898;BA.debugLine="If txtPresRdg.Text = New Or sPresentReading = New";
if ((mostCurrent._txtpresrdg.getText()).equals(_new) || (mostCurrent._spresentreading).equals(_new)) { 
 //BA.debugLineNum = 5899;BA.debugLine="btnSuperHBSave.Enabled = True";
mostCurrent._btnsuperhbsave.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 5901;BA.debugLine="btnSuperHBSave.Enabled = False";
mostCurrent._btnsuperhbsave.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 5904;BA.debugLine="End Sub";
return "";
}
public static String  _updatebadge(String _menutitle,anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _icon) throws Exception{
de.amberhome.objects.appcompat.ACMenuItemWrapper _m = null;
 //BA.debugLineNum = 689;BA.debugLine="Sub UpdateBadge(MenuTitle As String, Icon As Bitma";
 //BA.debugLineNum = 690;BA.debugLine="Dim m As ACMenuItem = GetMenuItem(MenuTitle)";
_m = new de.amberhome.objects.appcompat.ACMenuItemWrapper();
_m = _getmenuitem(_menutitle);
 //BA.debugLineNum = 691;BA.debugLine="m.Icon = BitmapToBitmapDrawable(Icon)";
_m.setIcon((android.graphics.drawable.Drawable)(_bitmaptobitmapdrawable(_icon).getObject()));
 //BA.debugLineNum = 692;BA.debugLine="End Sub";
return "";
}
public static String  _updateprintstatus(int _ireadid,int _iacctid) throws Exception{
 //BA.debugLineNum = 3263;BA.debugLine="Private Sub UpdatePrintStatus(iReadID As Int, iAcc";
 //BA.debugLineNum = 3264;BA.debugLine="Starter.DBCon.BeginTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .BeginTransaction();
 //BA.debugLineNum = 3265;BA.debugLine="Try";
try { //BA.debugLineNum = 3266;BA.debugLine="Starter.strCriteria = \"UPDATE tblReadings \" & _";
mostCurrent._starter._strcriteria /*String*/  = "UPDATE tblReadings "+"SET PrintStatus = ? "+"WHERE ReadID = "+BA.NumberToString(_ireadid)+" "+"AND AcctID = "+BA.NumberToString(_iacctid);
 //BA.debugLineNum = 3270;BA.debugLine="Starter.DBCon.ExecNonQuery2(Starter.strCriteria,";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2(mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{BA.NumberToString(1)}));
 //BA.debugLineNum = 3271;BA.debugLine="Starter.DBCon.TransactionSuccessful";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .TransactionSuccessful();
 } 
       catch (Exception e7) {
			processBA.setLastException(e7); //BA.debugLineNum = 3273;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("126083338",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 };
 //BA.debugLineNum = 3275;BA.debugLine="Starter.DBCon.EndTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .EndTransaction();
 //BA.debugLineNum = 3276;BA.debugLine="End Sub";
return "";
}
public static String  _warningdialog_onbindview(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,int _viewtype) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alertdialog = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
anywheresoftware.b4a.objects.CSBuilder _cs = null;
 //BA.debugLineNum = 6031;BA.debugLine="Private Sub WarningDialog_OnBindView (View As View";
 //BA.debugLineNum = 6032;BA.debugLine="Dim AlertDialog As AX_CustomAlertDialog";
_alertdialog = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 6033;BA.debugLine="AlertDialog.Initialize";
_alertdialog.Initialize();
 //BA.debugLineNum = 6035;BA.debugLine="If ViewType = AlertDialog.VIEW_TITLE Then ' Title";
if (_viewtype==_alertdialog.VIEW_TITLE) { 
 //BA.debugLineNum = 6036;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 6037;BA.debugLine="Dim CS As CSBuilder";
_cs = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 6039;BA.debugLine="CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Ty";
_cs.Initialize().Typeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD).Typeface(anywheresoftware.b4a.keywords.Common.Typeface.getMATERIALICONS()).Size((int) (26)).Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xe002)))+"  "));
 //BA.debugLineNum = 6040;BA.debugLine="CS.Typeface(GlobalVar.Font).Size(22).Append(lbl.";
_cs.Typeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).Size((int) (22)).Append(BA.ObjectToCharSequence(_lbl.getText())).Pop();
 //BA.debugLineNum = 6042;BA.debugLine="lbl.Text = CS.PopAll";
_lbl.setText(BA.ObjectToCharSequence(_cs.PopAll().getObject()));
 };
 //BA.debugLineNum = 6044;BA.debugLine="If ViewType = AlertDialog.VIEW_MESSAGE Then";
if (_viewtype==_alertdialog.VIEW_MESSAGE) { 
 //BA.debugLineNum = 6045;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 6046;BA.debugLine="lbl.TextSize = 16";
_lbl.setTextSize((float) (16));
 //BA.debugLineNum = 6047;BA.debugLine="lbl.TextColor = Colors.Gray";
_lbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 };
 //BA.debugLineNum = 6049;BA.debugLine="End Sub";
return "";
}
public static String  _warningmessage_onpositiveclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alertdialog = null;
 //BA.debugLineNum = 6022;BA.debugLine="Private Sub WarningMessage_OnPositiveClicked (View";
 //BA.debugLineNum = 6023;BA.debugLine="Dim AlertDialog As AX_CustomAlertDialog";
_alertdialog = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 6025;BA.debugLine="vibration.vibrateCancel";
mostCurrent._vibration.vibrateCancel(processBA);
 //BA.debugLineNum = 6026;BA.debugLine="soundsAlarmChannel.Stop(SoundID)";
_soundsalarmchannel.Stop(_soundid);
 //BA.debugLineNum = 6027;BA.debugLine="AlertDialog.Initialize.Dismiss(Dialog)";
_alertdialog.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 6029;BA.debugLine="End Sub";
return "";
}
public static String  _warningmsg(String _stitle,String _smsg) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alertdialog = null;
 //BA.debugLineNum = 5990;BA.debugLine="Private Sub WarningMsg(sTitle As String, sMsg As S";
 //BA.debugLineNum = 5991;BA.debugLine="Dim AlertDialog As AX_CustomAlertDialog";
_alertdialog = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 5992;BA.debugLine="soundsAlarmChannel.Play(SoundID,1,1,1,0,1)";
_soundsalarmchannel.Play(_soundid,(float) (1),(float) (1),(int) (1),(int) (0),(float) (1));
 //BA.debugLineNum = 5994;BA.debugLine="AlertDialog.Initialize";
_alertdialog.Initialize();
 //BA.debugLineNum = 5996;BA.debugLine="AlertDialog.Initialize.Create _ 			.SetDialogStyl";
_alertdialog.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialogDisableStatus").SetStyle(_alertdialog.getSTYLE_DIALOGUE()).SetCancelable(anywheresoftware.b4a.keywords.Common.False).SetTitle(_stitle).SetTitleColor(anywheresoftware.b4a.keywords.Common.Colors.Red).SetTitleTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessage(_smsg).SetPositiveText("OK").SetPositiveColor(anywheresoftware.b4a.keywords.Common.Colors.Blue).SetPositiveTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessageTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetOnPositiveClicked(mostCurrent.activityBA,"WarningMessage").SetOnViewBinder(mostCurrent.activityBA,"WarningDialog");
 //BA.debugLineNum = 6011;BA.debugLine="AlertDialog.SetDialogBackground(myCD)";
_alertdialog.SetDialogBackground((android.graphics.drawable.Drawable)(_mycd().getObject()));
 //BA.debugLineNum = 6012;BA.debugLine="AlertDialog.Build.Show";
_alertdialog.Build().Show();
 //BA.debugLineNum = 6014;BA.debugLine="End Sub";
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
