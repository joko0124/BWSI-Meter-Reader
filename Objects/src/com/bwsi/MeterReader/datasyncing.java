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

public class datasyncing extends androidx.appcompat.app.AppCompatActivity implements B4AActivity{
	public static datasyncing mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "com.bwsi.MeterReader", "com.bwsi.MeterReader.datasyncing");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (datasyncing).");
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
		activityBA = new BA(this, layout, processBA, "com.bwsi.MeterReader", "com.bwsi.MeterReader.datasyncing");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "com.bwsi.MeterReader.datasyncing", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (datasyncing) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (datasyncing) Resume **");
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
		return datasyncing.class;
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
            BA.LogInfo("** Activity (datasyncing) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (datasyncing) Pause event (activity is not paused). **");
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
            datasyncing mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (datasyncing) Resume **");
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
public static com.bwsi.MeterReader.manageexternalstorage _ap = null;
public static anywheresoftware.b4a.phone.Phone _device = null;
public static Object _ion = null;
public static anywheresoftware.b4a.audio.SoundPoolWrapper _soundsalarmchannel = null;
public de.amberhome.objects.appcompat.ACActionBar _actionbarbutton = null;
public de.amberhome.objects.appcompat.ACToolbarDarkWrapper _toolbar = null;
public anywheresoftware.b4a.object.XmlLayoutBuilder _xmlicon = null;
public de.amberhome.materialdialogs.MaterialDialogBuilderWrapper _matdialogbuilder = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlbuttons = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnldownload = null;
public static String _strrdgdate = "";
public anywheresoftware.b4a.objects.drawable.BitmapDrawable _bmpdownload = null;
public anywheresoftware.b4a.objects.drawable.BitmapDrawable _bmpupload = null;
public anywheresoftware.b4a.sql.SQL.CursorWrapper _rsreader = null;
public anywheresoftware.b4a.sql.SQL.CursorWrapper _rsheader = null;
public anywheresoftware.b4a.sql.SQL.CursorWrapper _rsdetails = null;
public static int _readerid = 0;
public static int _intuploadedbookid = 0;
public static int _intid = 0;
public static int _intuploadacctid = 0;
public de.amberhome.objects.appcompat.ACSpinnerWrapper _cboreader = null;
public static int _intdownload = 0;
public de.amberhome.objects.SnackbarWrapper _snack = null;
public static int _dlctr = 0;
public de.amberhome.objects.appcompat.ACButtonWrapper _btncancelupload = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnokupload = null;
public de.amberhome.objects.appcompat.ACSpinnerWrapper _cboreaderupload = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlupload = null;
public static int _uploadctr = 0;
public static int _intreaderid = 0;
public de.amberhome.objects.appcompat.ACButtonWrapper _btncancel = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnok = null;
public static String _surl = "";
public static String _backuppath = "";
public static String _backupname = "";
public anywheresoftware.b4a.objects.drawable.ColorDrawable _mystyle = null;
public anywheresoftware.b4a.objects.drawable.ColorDrawable _cdbuttoncancel = null;
public anywheresoftware.b4a.objects.drawable.ColorDrawable _cdbuttonok = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnldownloadbox = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnluploadbox = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btndownload = null;
public de.amberhome.objects.appcompat.ACButtonWrapper _btnupload = null;
public anywheresoftware.b4a.objects.drawable.ColorDrawable _cddownupstyle = null;
public anywheresoftware.b4a.objects.drawable.ColorDrawable _cdreader = null;
public com.bwsi.MeterReader.datedialogs _mydatedialogs = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrdgdateicon = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblreadingdate = null;
public static long _ldate = 0L;
public static String _srdgdate = "";
public anywheresoftware.b4a.objects.CSBuilder _csnote = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblulnote = null;
public static int _soundid = 0;
public com.johan.Vibrate.Vibrate _vibration = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public b4a.example.dateutils _dateutils = null;
public com.bwsi.MeterReader.main _main = null;
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
public static void  _activity_create(boolean _firsttime) throws Exception{
ResumableSub_Activity_Create rsub = new ResumableSub_Activity_Create(null,_firsttime);
rsub.resume(processBA, null);
}
public static class ResumableSub_Activity_Create extends BA.ResumableSub {
public ResumableSub_Activity_Create(com.bwsi.MeterReader.datasyncing parent,boolean _firsttime) {
this.parent = parent;
this._firsttime = _firsttime;
}
com.bwsi.MeterReader.datasyncing parent;
boolean _firsttime;
anywheresoftware.b4j.object.JavaObject _jo = null;
anywheresoftware.b4a.phone.Phone _ph = null;
int _sdkversion = 0;
anywheresoftware.b4a.objects.RuntimePermissions _rp = null;
String _permission = "";
boolean _result = false;
int _res = 0;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 89;BA.debugLine="MyScale.SetRate(0.5)";
parent.mostCurrent._myscale._setrate /*String*/ (mostCurrent.activityBA,0.5);
 //BA.debugLineNum = 90;BA.debugLine="Activity.LoadLayout(\"DataSyncingNew\")";
parent.mostCurrent._activity.LoadLayout("DataSyncingNew",mostCurrent.activityBA);
 //BA.debugLineNum = 92;BA.debugLine="GlobalVar.CSTitle.Initialize.Size(13).Append($\"DA";
parent.mostCurrent._globalvar._cstitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (13)).Append(BA.ObjectToCharSequence(("DATA SYNCING"))).Bold().PopAll();
 //BA.debugLineNum = 93;BA.debugLine="GlobalVar.CSSubTitle.Initialize.Size(11).Append($";
parent.mostCurrent._globalvar._cssubtitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (11)).Append(BA.ObjectToCharSequence(("Allows to Download and Upload Reading Data."))).PopAll();
 //BA.debugLineNum = 95;BA.debugLine="ToolBar.InitMenuListener";
parent.mostCurrent._toolbar.InitMenuListener();
 //BA.debugLineNum = 96;BA.debugLine="ToolBar.Title = GlobalVar.CSTitle";
parent.mostCurrent._toolbar.setTitle(BA.ObjectToCharSequence(parent.mostCurrent._globalvar._cstitle /*anywheresoftware.b4a.objects.CSBuilder*/ .getObject()));
 //BA.debugLineNum = 97;BA.debugLine="ToolBar.SubTitle = GlobalVar.CSSubTitle";
parent.mostCurrent._toolbar.setSubTitle(BA.ObjectToCharSequence(parent.mostCurrent._globalvar._cssubtitle /*anywheresoftware.b4a.objects.CSBuilder*/ .getObject()));
 //BA.debugLineNum = 99;BA.debugLine="ActionBarButton.Initialize";
parent.mostCurrent._actionbarbutton.Initialize(mostCurrent.activityBA);
 //BA.debugLineNum = 100;BA.debugLine="ActionBarButton.ShowUpIndicator = True";
parent.mostCurrent._actionbarbutton.setShowUpIndicator(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 102;BA.debugLine="pnlButtons.Visible = True";
parent.mostCurrent._pnlbuttons.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 103;BA.debugLine="pnlDownloadBox.Visible = False";
parent.mostCurrent._pnldownloadbox.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 104;BA.debugLine="pnlUploadBox.Visible = False";
parent.mostCurrent._pnluploadbox.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 107;BA.debugLine="cdDownUpStyle.Initialize2(GlobalVar.PriColor, 25,";
parent.mostCurrent._cddownupstyle.Initialize2((int) (parent.mostCurrent._globalvar._pricolor /*double*/ ),(int) (25),(int) (0),anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 108;BA.debugLine="btnDownload.Background = cdDownUpStyle";
parent.mostCurrent._btndownload.setBackground((android.graphics.drawable.Drawable)(parent.mostCurrent._cddownupstyle.getObject()));
 //BA.debugLineNum = 109;BA.debugLine="btnDownload.Text = Chr(0xF019) & $\"  DOWNLOAD REA";
parent.mostCurrent._btndownload.setText(BA.ObjectToCharSequence(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xf019)))+("  DOWNLOAD READING DATA")));
 //BA.debugLineNum = 110;BA.debugLine="btnDownload.Typeface = Typeface.DEFAULT_BOLD";
parent.mostCurrent._btndownload.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD);
 //BA.debugLineNum = 111;BA.debugLine="btnDownload.Typeface = Typeface.FONTAWESOME";
parent.mostCurrent._btndownload.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.getFONTAWESOME());
 //BA.debugLineNum = 113;BA.debugLine="btnUpload.Background = cdDownUpStyle";
parent.mostCurrent._btnupload.setBackground((android.graphics.drawable.Drawable)(parent.mostCurrent._cddownupstyle.getObject()));
 //BA.debugLineNum = 114;BA.debugLine="btnUpload.Text = Chr(0xF093) & $\"  UPLOAD READING";
parent.mostCurrent._btnupload.setText(BA.ObjectToCharSequence(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xf093)))+("  UPLOAD READING DATA")));
 //BA.debugLineNum = 115;BA.debugLine="btnUpload.Typeface = Typeface.DEFAULT_BOLD";
parent.mostCurrent._btnupload.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD);
 //BA.debugLineNum = 116;BA.debugLine="btnUpload.Typeface = Typeface.FONTAWESOME";
parent.mostCurrent._btnupload.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.getFONTAWESOME());
 //BA.debugLineNum = 118;BA.debugLine="myStyle.Initialize2(Colors.White, 0,0,Colors.Tran";
parent.mostCurrent._mystyle.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.White,(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 120;BA.debugLine="cboReader.TextColor = Colors.Gray";
parent.mostCurrent._cboreader.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 //BA.debugLineNum = 122;BA.debugLine="If FirstTime Then";
if (true) break;

case 1:
//if
this.state = 4;
if (_firsttime) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 if (true) break;

case 4:
//C
this.state = 5;
;
 //BA.debugLineNum = 125;BA.debugLine="Dim jo As JavaObject";
_jo = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 126;BA.debugLine="Dim ph As Phone";
_ph = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 128;BA.debugLine="jo.InitializeNewInstance(\"android.media.SoundPool";
_jo.InitializeNewInstance("android.media.SoundPool",new Object[]{(Object)(4),(Object)(_ph.VOLUME_ALARM),(Object)(0)});
 //BA.debugLineNum = 129;BA.debugLine="soundsAlarmChannel = jo";
parent._soundsalarmchannel = (anywheresoftware.b4a.audio.SoundPoolWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.audio.SoundPoolWrapper(), (android.media.SoundPool)(_jo.getObject()));
 //BA.debugLineNum = 130;BA.debugLine="SoundID = soundsAlarmChannel.Load(File.DirAssets,";
parent._soundid = parent._soundsalarmchannel.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"beep.wav");
 //BA.debugLineNum = 150;BA.debugLine="AP.Initialize(Me, \"AP\")";
parent._ap._initialize /*String*/ (processBA,datasyncing.getObject(),"AP");
 //BA.debugLineNum = 154;BA.debugLine="Dim SdkVersion As Int = device.SdkVersion";
_sdkversion = parent._device.getSdkVersion();
 //BA.debugLineNum = 157;BA.debugLine="If SdkVersion < 30 Then";
if (true) break;

case 5:
//if
this.state = 14;
if (_sdkversion<30) { 
this.state = 7;
}else {
this.state = 9;
}if (true) break;

case 7:
//C
this.state = 14;
 //BA.debugLineNum = 158;BA.debugLine="Log(\"SDK = \" & SdkVersion & \" : Requesting WRITE";
anywheresoftware.b4a.keywords.Common.LogImpl("1720966","SDK = "+BA.NumberToString(_sdkversion)+" : Requesting WRITE_EXTERNAL_STORAGE permission",0);
 //BA.debugLineNum = 159;BA.debugLine="Dim rp As RuntimePermissions";
_rp = new anywheresoftware.b4a.objects.RuntimePermissions();
 //BA.debugLineNum = 160;BA.debugLine="rp.CheckAndRequest(rp.PERMISSION_WRITE_EXTERNAL_";
_rp.CheckAndRequest(processBA,_rp.PERMISSION_WRITE_EXTERNAL_STORAGE);
 //BA.debugLineNum = 161;BA.debugLine="Wait For Activity_PermissionResult (Permission A";
anywheresoftware.b4a.keywords.Common.WaitFor("activity_permissionresult", processBA, this, null);
this.state = 15;
return;
case 15:
//C
this.state = 14;
_permission = (String) result[0];
_result = (Boolean) result[1];
;
 //BA.debugLineNum = 162;BA.debugLine="Log($\"PERMISSION_WRITE_EXTERNAL_STORAGE = ${Resu";
anywheresoftware.b4a.keywords.Common.LogImpl("1720970",("PERMISSION_WRITE_EXTERNAL_STORAGE = "+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_result))+""),0);
 if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 164;BA.debugLine="Log(\"SDK = \" & SdkVersion & \" : Requesting MANAG";
anywheresoftware.b4a.keywords.Common.LogImpl("1720972","SDK = "+BA.NumberToString(_sdkversion)+" : Requesting MANAGE_EXTERNAL_STORAGE permission",0);
 //BA.debugLineNum = 165;BA.debugLine="Log(\"On Entry MANAGE_EXTERNAL_STORAGE = \" & AP.H";
anywheresoftware.b4a.keywords.Common.LogImpl("1720973","On Entry MANAGE_EXTERNAL_STORAGE = "+BA.ObjectToString(parent._ap._haspermission /*boolean*/ ()),0);
 //BA.debugLineNum = 166;BA.debugLine="If Not(AP.HasPermission) Then";
if (true) break;

case 10:
//if
this.state = 13;
if (anywheresoftware.b4a.keywords.Common.Not(parent._ap._haspermission /*boolean*/ ())) { 
this.state = 12;
}if (true) break;

case 12:
//C
this.state = 13;
 //BA.debugLineNum = 167;BA.debugLine="MsgboxAsync(\"This app requires access to all fi";
anywheresoftware.b4a.keywords.Common.MsgboxAsync(BA.ObjectToCharSequence("This app requires access to all files, please enable the option"),BA.ObjectToCharSequence("Manage All Files"),processBA);
 //BA.debugLineNum = 168;BA.debugLine="Wait For Msgbox_Result(Res As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("msgbox_result", processBA, this, null);
this.state = 16;
return;
case 16:
//C
this.state = 13;
_res = (Integer) result[0];
;
 //BA.debugLineNum = 169;BA.debugLine="Log(\"Getting permission\")";
anywheresoftware.b4a.keywords.Common.LogImpl("1720977","Getting permission",0);
 //BA.debugLineNum = 170;BA.debugLine="AP.GetPermission";
parent._ap._getpermission /*String*/ ();
 //BA.debugLineNum = 171;BA.debugLine="Wait For AP_StorageAvailable";
anywheresoftware.b4a.keywords.Common.WaitFor("ap_storageavailable", processBA, this, null);
this.state = 17;
return;
case 17:
//C
this.state = 13;
;
 if (true) break;

case 13:
//C
this.state = 14;
;
 if (true) break;

case 14:
//C
this.state = -1;
;
 //BA.debugLineNum = 175;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _msgbox_result(int _res) throws Exception{
}
public static void  _ap_storageavailable() throws Exception{
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
int _intresponse = 0;
 //BA.debugLineNum = 177;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 178;BA.debugLine="Dim intResponse As Int";
_intresponse = 0;
 //BA.debugLineNum = 179;BA.debugLine="If KeyCode = 4 Then";
if (_keycode==4) { 
 //BA.debugLineNum = 180;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 }else {
 //BA.debugLineNum = 182;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 184;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 194;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 196;BA.debugLine="End Sub";
return "";
}
public static String  _activity_permissionresult(String _permission,boolean _result) throws Exception{
 //BA.debugLineNum = 198;BA.debugLine="Sub Activity_PermissionResult (Permission As Strin";
 //BA.debugLineNum = 199;BA.debugLine="Log (Permission)";
anywheresoftware.b4a.keywords.Common.LogImpl("1983041",_permission,0);
 //BA.debugLineNum = 200;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 186;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 187;BA.debugLine="pnlButtons.Visible = True";
mostCurrent._pnlbuttons.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 188;BA.debugLine="pnlDownloadBox.Visible = False";
mostCurrent._pnldownloadbox.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 189;BA.debugLine="pnlUploadBox.Visible = False";
mostCurrent._pnluploadbox.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 190;BA.debugLine="myStyle.Initialize2(Colors.White, 0,0,Colors.Tran";
mostCurrent._mystyle.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.White,(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 191;BA.debugLine="SoundID = soundsAlarmChannel.Load(File.DirAssets,";
_soundid = _soundsalarmchannel.Load(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"beep.wav");
 //BA.debugLineNum = 192;BA.debugLine="End Sub";
return "";
}
public static boolean  _areallaccountsuploaded(int _ibookid,int _ibillmonth,int _ibillyear) throws Exception{
boolean _blnretvalue = false;
int _icount = 0;
 //BA.debugLineNum = 1793;BA.debugLine="Sub AreAllAccountsUploaded(iBookID As Int, iBillMo";
 //BA.debugLineNum = 1794;BA.debugLine="Dim blnRetValue As Boolean";
_blnretvalue = false;
 //BA.debugLineNum = 1795;BA.debugLine="Dim iCount As Int";
_icount = 0;
 //BA.debugLineNum = 1796;BA.debugLine="iCount = Starter.DBCon.ExecQuerySingleResult(\"SEL";
_icount = (int)(Double.parseDouble(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult("SELECT Count(tblReadings.WasUploaded) As RecCount FROM tblReadings WHERE tblReadings.WasUploaded = 0 AND tblReadings.BillYear = "+BA.NumberToString(_ibillyear)+" AND tblReadings.BillMonth = "+BA.NumberToString(_ibillmonth)+" AND tblReadings.BookID = "+BA.NumberToString(_ibookid))));
 //BA.debugLineNum = 1797;BA.debugLine="If iCount > 0 Then";
if (_icount>0) { 
 //BA.debugLineNum = 1798;BA.debugLine="blnRetValue=False";
_blnretvalue = anywheresoftware.b4a.keywords.Common.False;
 }else {
 //BA.debugLineNum = 1800;BA.debugLine="blnRetValue=True";
_blnretvalue = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 1802;BA.debugLine="Return blnRetValue";
if (true) return _blnretvalue;
 //BA.debugLineNum = 1804;BA.debugLine="End Sub";
return false;
}
public static String  _btncancel_click() throws Exception{
 //BA.debugLineNum = 298;BA.debugLine="Sub btnCancel_Click";
 //BA.debugLineNum = 299;BA.debugLine="ConfirmCancelDownload";
_confirmcanceldownload();
 //BA.debugLineNum = 300;BA.debugLine="End Sub";
return "";
}
public static String  _btncancelupload_click() throws Exception{
 //BA.debugLineNum = 1266;BA.debugLine="Sub btnCancelUpload_Click";
 //BA.debugLineNum = 1267;BA.debugLine="ShowCancelUploading";
_showcanceluploading();
 //BA.debugLineNum = 1268;BA.debugLine="End Sub";
return "";
}
public static String  _btndownload_click() throws Exception{
 //BA.debugLineNum = 260;BA.debugLine="Sub btnDownload_Click()";
 //BA.debugLineNum = 261;BA.debugLine="ShowDownloadPanel";
_showdownloadpanel();
 //BA.debugLineNum = 262;BA.debugLine="End Sub";
return "";
}
public static String  _btndownload_hoverenter() throws Exception{
 //BA.debugLineNum = 252;BA.debugLine="Sub btnDownload_HoverEnter()";
 //BA.debugLineNum = 253;BA.debugLine="btnDownload.Color = 0xFF1976D2";
mostCurrent._btndownload.setColor((int) (0xff1976d2));
 //BA.debugLineNum = 254;BA.debugLine="End Sub";
return "";
}
public static String  _btndownload_hoverexit() throws Exception{
 //BA.debugLineNum = 256;BA.debugLine="Sub btnDownload_HoverExit()";
 //BA.debugLineNum = 257;BA.debugLine="btnDownload.Color = GlobalVar.PriColor";
mostCurrent._btndownload.setColor((int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 258;BA.debugLine="End Sub";
return "";
}
public static String  _btnok_click() throws Exception{
long _ddate = 0L;
 //BA.debugLineNum = 267;BA.debugLine="Sub btnOk_Click";
 //BA.debugLineNum = 268;BA.debugLine="Dim dDate As Long";
_ddate = 0L;
 //BA.debugLineNum = 269;BA.debugLine="Try";
try { //BA.debugLineNum = 271;BA.debugLine="dDate = DateTime.DateParse(lblReadingDate.Text)";
_ddate = anywheresoftware.b4a.keywords.Common.DateTime.DateParse(mostCurrent._lblreadingdate.getText());
 //BA.debugLineNum = 272;BA.debugLine="DateTime.DateFormat=\"yyyy-MM-dd\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("yyyy-MM-dd");
 //BA.debugLineNum = 273;BA.debugLine="strRdgDate = DateTime.Date(dDate)";
mostCurrent._strrdgdate = anywheresoftware.b4a.keywords.Common.DateTime.Date(_ddate);
 //BA.debugLineNum = 274;BA.debugLine="Log(strRdgDate)";
anywheresoftware.b4a.keywords.Common.LogImpl("11572871",mostCurrent._strrdgdate,0);
 //BA.debugLineNum = 276;BA.debugLine="If strRdgDate = \"\" Then";
if ((mostCurrent._strrdgdate).equals("")) { 
 //BA.debugLineNum = 277;BA.debugLine="xui.MsgboxAsync(\"Please specify the reading dat";
_xui.MsgboxAsync(processBA,BA.ObjectToCharSequence("Please specify the reading date!"),BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.Application.getLabelName()));
 //BA.debugLineNum = 278;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 281;BA.debugLine="If cboReader.SelectedItem = \"\" Then";
if ((mostCurrent._cboreader.getSelectedItem()).equals("")) { 
 //BA.debugLineNum = 282;BA.debugLine="xui.MsgboxAsync(\"Please specify the reader!\",Ap";
_xui.MsgboxAsync(processBA,BA.ObjectToCharSequence("Please specify the reader!"),BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.Application.getLabelName()));
 //BA.debugLineNum = 283;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 286;BA.debugLine="ReaderID = DBaseFunctions.GetIDbyCode(\"ReaderID\"";
_readerid = (int) (mostCurrent._dbasefunctions._getidbycode /*double*/ (mostCurrent.activityBA,"ReaderID","tblReaders","ReaderName",mostCurrent._cboreader.getSelectedItem()));
 //BA.debugLineNum = 288;BA.debugLine="Log(cboReader.SelectedItem)";
anywheresoftware.b4a.keywords.Common.LogImpl("11572885",mostCurrent._cboreader.getSelectedItem(),0);
 //BA.debugLineNum = 289;BA.debugLine="Log (ReaderID)";
anywheresoftware.b4a.keywords.Common.LogImpl("11572886",BA.NumberToString(_readerid),0);
 //BA.debugLineNum = 291;BA.debugLine="ConfirmDL($\"DOWNLOAD READING DATA\"$,$\"REMINDERS:";
_confirmdl(("DOWNLOAD READING DATA"),("REMINDERS: PASSWORD REQUIRED!")+anywheresoftware.b4a.keywords.Common.CRLF+("Continuing this process will DELETE ALL existing data on this device and will download new reading data.")+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+("Do you want to REMOVE all current data and DOWNLOAD new records?"));
 } 
       catch (Exception e20) {
			processBA.setLastException(e20); //BA.debugLineNum = 293;BA.debugLine="xui.MsgboxAsync(LastException.Message, Applicati";
_xui.MsgboxAsync(processBA,BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage()),BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.Application.getLabelName()));
 //BA.debugLineNum = 294;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("11572891",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 296;BA.debugLine="End Sub";
return "";
}
public static String  _btnokupload_click() throws Exception{
 //BA.debugLineNum = 1245;BA.debugLine="Sub btnOkUpload_Click";
 //BA.debugLineNum = 1246;BA.debugLine="If cboReaderUpload.SelectedItem.Length <= 0 Then";
if (mostCurrent._cboreaderupload.getSelectedItem().length()<=0) { 
 //BA.debugLineNum = 1247;BA.debugLine="snack.Initialize(\"\", Activity, $\"Please select t";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Please select the reader you wanted to Upload his reading data."),mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 1248;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1249;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1250;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 1251;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 1254;BA.debugLine="intReaderID = DBaseFunctions.GetIDbyCode(\"ReaderI";
_intreaderid = (int) (mostCurrent._dbasefunctions._getidbycode /*double*/ (mostCurrent.activityBA,"ReaderID","tblReaders","ReaderName",mostCurrent._cboreaderupload.getSelectedItem()));
 //BA.debugLineNum = 1255;BA.debugLine="If intReaderID <=0 Then";
if (_intreaderid<=0) { 
 //BA.debugLineNum = 1256;BA.debugLine="snack.Initialize(\"\", Activity, $\"Reader Unknown.";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Reader Unknown..."),mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 1257;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1258;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1259;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 //BA.debugLineNum = 1260;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 1263;BA.debugLine="ConfirmUL($\"UPLOAD READING DATA\"$,$\"This process";
_confirmul(("UPLOAD READING DATA"),("This process will UPLOAD ALL Reading data on the specified BOOK. Uploaded readings will be locked.")+anywheresoftware.b4a.keywords.Common.CRLF+("Continue uploading readings from your specified reader and book?"));
 //BA.debugLineNum = 1264;BA.debugLine="End Sub";
return "";
}
public static String  _btnupload_click() throws Exception{
 //BA.debugLineNum = 1851;BA.debugLine="Sub btnUpload_Click()";
 //BA.debugLineNum = 1852;BA.debugLine="ShowUploadPanel";
_showuploadpanel();
 //BA.debugLineNum = 1853;BA.debugLine="End Sub";
return "";
}
public static String  _canceldl_buttonpressed(de.amberhome.materialdialogs.MaterialDialogWrapper _dialog,String _saction) throws Exception{
 //BA.debugLineNum = 1888;BA.debugLine="Private Sub CancelDL_ButtonPressed(Dialog As Mater";
 //BA.debugLineNum = 1889;BA.debugLine="Select Case sAction";
switch (BA.switchObjectToInt(_saction,_dialog.ACTION_POSITIVE,_dialog.ACTION_NEGATIVE)) {
case 0: {
 //BA.debugLineNum = 1891;BA.debugLine="pnlButtons.Visible = True";
mostCurrent._pnlbuttons.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1892;BA.debugLine="pnlDownloadBox.Visible = False";
mostCurrent._pnldownloadbox.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1893;BA.debugLine="pnlUploadBox.Visible = False";
mostCurrent._pnluploadbox.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1894;BA.debugLine="snack.Initialize(\"\",Activity,$\"Downloading Read";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Downloading Reading data was cancelled"),mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 1895;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1896;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1897;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 break; }
case 1: {
 //BA.debugLineNum = 1899;BA.debugLine="Return";
if (true) return "";
 break; }
}
;
 //BA.debugLineNum = 1901;BA.debugLine="End Sub";
return "";
}
public static String  _cancelul_buttonpressed(de.amberhome.materialdialogs.MaterialDialogWrapper _dialog,String _saction) throws Exception{
 //BA.debugLineNum = 1703;BA.debugLine="Private Sub CancelUL_ButtonPressed(Dialog As Mater";
 //BA.debugLineNum = 1704;BA.debugLine="Select Case sAction";
switch (BA.switchObjectToInt(_saction,_dialog.ACTION_POSITIVE,_dialog.ACTION_NEGATIVE)) {
case 0: {
 //BA.debugLineNum = 1706;BA.debugLine="pnlButtons.Visible = True";
mostCurrent._pnlbuttons.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1707;BA.debugLine="pnlDownloadBox.Visible = False";
mostCurrent._pnldownloadbox.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1708;BA.debugLine="pnlUploadBox.Visible = False";
mostCurrent._pnluploadbox.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1709;BA.debugLine="snack.Initialize(\"\",Activity,$\"Uploading Readin";
mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(mostCurrent._activity.getObject()),("Uploading Reading data was cancelled"),mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 1710;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1711;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1712;BA.debugLine="snack.Show";
mostCurrent._snack.Show();
 break; }
case 1: {
 //BA.debugLineNum = 1714;BA.debugLine="Return";
if (true) return "";
 break; }
}
;
 //BA.debugLineNum = 1716;BA.debugLine="End Sub";
return "";
}
public static String  _confirmcanceldownload() throws Exception{
anywheresoftware.b4a.objects.CSBuilder _cscontent = null;
 //BA.debugLineNum = 1874;BA.debugLine="Private Sub ConfirmCancelDownload";
 //BA.debugLineNum = 1875;BA.debugLine="Dim csContent As CSBuilder";
_cscontent = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 1876;BA.debugLine="csContent.Initialize.Size(14).Color(Colors.DarkGr";
_cscontent.Initialize().Size((int) (14)).Color(anywheresoftware.b4a.keywords.Common.Colors.DarkGray).Bold().Append(BA.ObjectToCharSequence(("Cancel Downloading Reading Data?"))).PopAll();
 //BA.debugLineNum = 1877;BA.debugLine="MatDialogBuilder.Initialize(\"CancelDL\")";
mostCurrent._matdialogbuilder.Initialize(mostCurrent.activityBA,"CancelDL");
 //BA.debugLineNum = 1878;BA.debugLine="MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIG";
mostCurrent._matdialogbuilder.Theme(mostCurrent._matdialogbuilder.THEME_LIGHT);
 //BA.debugLineNum = 1879;BA.debugLine="MatDialogBuilder.Title($\"CANCEL DOWNLOADING\"$).Ti";
mostCurrent._matdialogbuilder.Title(BA.ObjectToCharSequence(("CANCEL DOWNLOADING"))).TitleColor((int) (mostCurrent._globalvar._pricolor /*double*/ )).TitleGravity(mostCurrent._matdialogbuilder.GRAVITY_START);
 //BA.debugLineNum = 1880;BA.debugLine="MatDialogBuilder.IconRes(GlobalVar.QuestionIcon).";
mostCurrent._matdialogbuilder.IconRes(mostCurrent._globalvar._questionicon /*String*/ ).LimitIconToDefaultSize();
 //BA.debugLineNum = 1881;BA.debugLine="MatDialogBuilder.Content(csContent)";
mostCurrent._matdialogbuilder.Content(BA.ObjectToCharSequence(_cscontent.getObject()));
 //BA.debugLineNum = 1882;BA.debugLine="MatDialogBuilder.PositiveText($\"YES\"$).PositiveCo";
mostCurrent._matdialogbuilder.PositiveText(BA.ObjectToCharSequence(("YES"))).PositiveColor((int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 1883;BA.debugLine="MatDialogBuilder.NegativeText($\"NO\"$).NegativeCol";
mostCurrent._matdialogbuilder.NegativeText(BA.ObjectToCharSequence(("NO"))).NegativeColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1884;BA.debugLine="MatDialogBuilder.CanceledOnTouchOutside(False)";
mostCurrent._matdialogbuilder.CanceledOnTouchOutside(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1885;BA.debugLine="MatDialogBuilder.Show";
mostCurrent._matdialogbuilder.Show();
 //BA.debugLineNum = 1886;BA.debugLine="End Sub";
return "";
}
public static String  _confirmdl(String _stitle,String _smsg) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alertdialog = null;
 //BA.debugLineNum = 1987;BA.debugLine="Private Sub ConfirmDL(sTitle As String, sMsg As St";
 //BA.debugLineNum = 1988;BA.debugLine="Dim AlertDialog As AX_CustomAlertDialog";
_alertdialog = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 1989;BA.debugLine="soundsAlarmChannel.Play(SoundID,1,1,1,0,1)";
_soundsalarmchannel.Play(_soundid,(float) (1),(float) (1),(int) (1),(int) (0),(float) (1));
 //BA.debugLineNum = 1990;BA.debugLine="vibration.vibrateOnce(2000)";
mostCurrent._vibration.vibrateOnce(processBA,(long) (2000));
 //BA.debugLineNum = 1992;BA.debugLine="AlertDialog.Initialize";
_alertdialog.Initialize();
 //BA.debugLineNum = 1993;BA.debugLine="AlertDialog.Initialize.Create _ 			.SetDialogStyl";
_alertdialog.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialogDisableStatus").SetStyle(_alertdialog.getSTYLE_DIALOGUE()).SetCancelable(anywheresoftware.b4a.keywords.Common.False).SetTitle(_stitle).SetTitleColor(anywheresoftware.b4a.keywords.Common.Colors.Red).SetTitleTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessage(_smsg).SetMessageTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetPositiveTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetPositiveColor(anywheresoftware.b4a.keywords.Common.Colors.Blue).SetPositiveText("YES").SetOnPositiveClicked(mostCurrent.activityBA,"OnDownload").SetNegativeTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetNegativeColor(anywheresoftware.b4a.keywords.Common.Colors.Red).SetOnNegativeClicked(mostCurrent.activityBA,"OnDownload").SetNegativeText("CANCEL").SetOnViewBinder(mostCurrent.activityBA,"DownloadViewBinder");
 //BA.debugLineNum = 2011;BA.debugLine="AlertDialog.SetDialogBackground(myCD)";
_alertdialog.SetDialogBackground((android.graphics.drawable.Drawable)(_mycd().getObject()));
 //BA.debugLineNum = 2012;BA.debugLine="AlertDialog.Build.Show";
_alertdialog.Build().Show();
 //BA.debugLineNum = 2013;BA.debugLine="End Sub";
return "";
}
public static String  _confirmdownload() throws Exception{
anywheresoftware.b4a.objects.CSBuilder _cscontent = null;
 //BA.debugLineNum = 302;BA.debugLine="Private Sub ConfirmDownload";
 //BA.debugLineNum = 303;BA.debugLine="Dim csContent As CSBuilder";
_cscontent = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 304;BA.debugLine="csContent.Initialize.Size(14).Color(Colors.Red).B";
_cscontent.Initialize().Size((int) (14)).Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Bold().Append(BA.ObjectToCharSequence(("REMINDERS: PASSWORD REQUIRED!"))).Color(anywheresoftware.b4a.keywords.Common.Colors.DarkGray).Append(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.CRLF+("Continuing this process will "))).Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(("DELETE ALL"))).Color(anywheresoftware.b4a.keywords.Common.Colors.DarkGray).Append(BA.ObjectToCharSequence((" existing data on this device and will download new reading data.")+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+("Do you want to REMOVE all current data and DOWNLOAD new records?"))).PopAll();
 //BA.debugLineNum = 306;BA.debugLine="MatDialogBuilder.Initialize(\"Downloading\")";
mostCurrent._matdialogbuilder.Initialize(mostCurrent.activityBA,"Downloading");
 //BA.debugLineNum = 307;BA.debugLine="MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIG";
mostCurrent._matdialogbuilder.Theme(mostCurrent._matdialogbuilder.THEME_LIGHT);
 //BA.debugLineNum = 308;BA.debugLine="MatDialogBuilder.Title($\"DOWNLOAD READING DATA\"$)";
mostCurrent._matdialogbuilder.Title(BA.ObjectToCharSequence(("DOWNLOAD READING DATA"))).TitleColor((int) (mostCurrent._globalvar._pricolor /*double*/ )).TitleGravity(mostCurrent._matdialogbuilder.GRAVITY_START);
 //BA.debugLineNum = 309;BA.debugLine="MatDialogBuilder.IconRes(GlobalVar.WarningIcon).L";
mostCurrent._matdialogbuilder.IconRes(mostCurrent._globalvar._warningicon /*String*/ ).LimitIconToDefaultSize();
 //BA.debugLineNum = 310;BA.debugLine="MatDialogBuilder.Content(csContent)";
mostCurrent._matdialogbuilder.Content(BA.ObjectToCharSequence(_cscontent.getObject()));
 //BA.debugLineNum = 311;BA.debugLine="MatDialogBuilder.PositiveText($\"YES\"$).PositiveCo";
mostCurrent._matdialogbuilder.PositiveText(BA.ObjectToCharSequence(("YES"))).PositiveColor((int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 312;BA.debugLine="MatDialogBuilder.NegativeText($\"NO\"$).NegativeCol";
mostCurrent._matdialogbuilder.NegativeText(BA.ObjectToCharSequence(("NO"))).NegativeColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 313;BA.debugLine="MatDialogBuilder.CanceledOnTouchOutside(False)";
mostCurrent._matdialogbuilder.CanceledOnTouchOutside(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 314;BA.debugLine="MatDialogBuilder.Show";
mostCurrent._matdialogbuilder.Show();
 //BA.debugLineNum = 315;BA.debugLine="End Sub";
return "";
}
public static String  _confirmul(String _stitle,String _smsg) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alertdialog = null;
 //BA.debugLineNum = 2052;BA.debugLine="Private Sub ConfirmUL(sTitle As String, sMsg As St";
 //BA.debugLineNum = 2053;BA.debugLine="Dim AlertDialog As AX_CustomAlertDialog";
_alertdialog = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 2054;BA.debugLine="soundsAlarmChannel.Play(SoundID,1,1,1,0,1)";
_soundsalarmchannel.Play(_soundid,(float) (1),(float) (1),(int) (1),(int) (0),(float) (1));
 //BA.debugLineNum = 2056;BA.debugLine="AlertDialog.Initialize";
_alertdialog.Initialize();
 //BA.debugLineNum = 2057;BA.debugLine="AlertDialog.Initialize.Create _ 			.SetDialogStyl";
_alertdialog.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialogDisableStatus").SetStyle(_alertdialog.getSTYLE_DIALOGUE()).SetCancelable(anywheresoftware.b4a.keywords.Common.False).SetTitle(_stitle).SetTitleColor(anywheresoftware.b4a.keywords.Common.Colors.Red).SetTitleTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessage(_smsg).SetMessageTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetPositiveTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetPositiveColor(anywheresoftware.b4a.keywords.Common.Colors.Blue).SetPositiveText("YES").SetOnPositiveClicked(mostCurrent.activityBA,"OnUpload").SetNegativeTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetNegativeColor(anywheresoftware.b4a.keywords.Common.Colors.Red).SetOnNegativeClicked(mostCurrent.activityBA,"OnUpload").SetNegativeText("CANCEL").SetOnViewBinder(mostCurrent.activityBA,"UploadViewBinder");
 //BA.debugLineNum = 2075;BA.debugLine="AlertDialog.SetDialogBackground(myCD)";
_alertdialog.SetDialogBackground((android.graphics.drawable.Drawable)(_mycd().getObject()));
 //BA.debugLineNum = 2076;BA.debugLine="AlertDialog.Build.Show";
_alertdialog.Build().Show();
 //BA.debugLineNum = 2077;BA.debugLine="End Sub";
return "";
}
public static String  _convertreadingjson(int _ibranchid,int _ibookid) throws Exception{
anywheresoftware.b4a.objects.collections.JSONParser.JSONGenerator _jsongen = null;
String _strjson = "";
int _reccount = 0;
anywheresoftware.b4a.objects.collections.List _titlelist = null;
anywheresoftware.b4a.objects.collections.List _headerlist = null;
anywheresoftware.b4a.objects.collections.List _detaillist = null;
anywheresoftware.b4a.objects.collections.Map _mp1 = null;
anywheresoftware.b4a.objects.collections.Map _mp2 = null;
anywheresoftware.b4a.objects.collections.Map _mp3 = null;
double _advpayment = 0;
double _dbasic = 0;
String _sbasic = "";
double _ddisc = 0;
String _sdisc = "";
double _dpcaamt = 0;
String _spcaamt = "";
double _dbillamt = 0;
String _sbillamt = "";
double _dothers = 0;
String _sothers = "";
double _dtotaldue = 0;
String _stotaldue = "";
double _dpenalty = 0;
String _spenalty = "";
double _dadvpayment = 0;
String _sadvpayment = "";
double _dbalance = 0;
String _sbalance = "";
double _dmetercharges = 0;
String _smetercharges = "";
double _dfranchisetaxamt = 0;
String _sfranchisetaxamt = "";
double _dseptagefee = 0;
String _sseptagefee = "";
String _rdg_datetime = "";
String _rdgdate = "";
String _rdgtime = "";
String _disconnectiondate = "";
int _i = 0;
String _sstatus = "";
String _sremarks = "";
 //BA.debugLineNum = 1427;BA.debugLine="Sub ConvertReadingJSON(iBranchID As Int, iBookID A";
 //BA.debugLineNum = 1428;BA.debugLine="Dim JSONGen As JSONGenerator";
_jsongen = new anywheresoftware.b4a.objects.collections.JSONParser.JSONGenerator();
 //BA.debugLineNum = 1429;BA.debugLine="Dim strJson As String";
_strjson = "";
 //BA.debugLineNum = 1430;BA.debugLine="Dim RecCount As Int";
_reccount = 0;
 //BA.debugLineNum = 1431;BA.debugLine="Dim TitleList, HeaderList, DetailList As List";
_titlelist = new anywheresoftware.b4a.objects.collections.List();
_headerlist = new anywheresoftware.b4a.objects.collections.List();
_detaillist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 1432;BA.debugLine="Dim MP1, MP2, MP3 As Map";
_mp1 = new anywheresoftware.b4a.objects.collections.Map();
_mp2 = new anywheresoftware.b4a.objects.collections.Map();
_mp3 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1433;BA.debugLine="Dim AdvPayment As Double";
_advpayment = 0;
 //BA.debugLineNum = 1435;BA.debugLine="Dim dBasic As Double";
_dbasic = 0;
 //BA.debugLineNum = 1436;BA.debugLine="Dim sBasic As String";
_sbasic = "";
 //BA.debugLineNum = 1438;BA.debugLine="Dim dDisc As Double";
_ddisc = 0;
 //BA.debugLineNum = 1439;BA.debugLine="Dim sDisc As String";
_sdisc = "";
 //BA.debugLineNum = 1441;BA.debugLine="Dim dPCAAmt As Double";
_dpcaamt = 0;
 //BA.debugLineNum = 1442;BA.debugLine="Dim sPCAAmt As String";
_spcaamt = "";
 //BA.debugLineNum = 1444;BA.debugLine="Dim dbillAmt As Double";
_dbillamt = 0;
 //BA.debugLineNum = 1445;BA.debugLine="Dim sBillAmt As String";
_sbillamt = "";
 //BA.debugLineNum = 1447;BA.debugLine="Dim dOthers As Double";
_dothers = 0;
 //BA.debugLineNum = 1448;BA.debugLine="Dim sOthers As String";
_sothers = "";
 //BA.debugLineNum = 1450;BA.debugLine="Dim dTotalDue As Double";
_dtotaldue = 0;
 //BA.debugLineNum = 1451;BA.debugLine="Dim sTotalDue As String";
_stotaldue = "";
 //BA.debugLineNum = 1453;BA.debugLine="Dim dPenalty As Double";
_dpenalty = 0;
 //BA.debugLineNum = 1454;BA.debugLine="Dim sPenalty As String";
_spenalty = "";
 //BA.debugLineNum = 1456;BA.debugLine="Dim dAdvPayment As Double";
_dadvpayment = 0;
 //BA.debugLineNum = 1457;BA.debugLine="Dim sAdvPayment As String";
_sadvpayment = "";
 //BA.debugLineNum = 1459;BA.debugLine="Dim dBalance As Double";
_dbalance = 0;
 //BA.debugLineNum = 1460;BA.debugLine="Dim sBalance As String";
_sbalance = "";
 //BA.debugLineNum = 1462;BA.debugLine="Dim dMeterCharges As Double";
_dmetercharges = 0;
 //BA.debugLineNum = 1463;BA.debugLine="Dim sMeterCharges As String";
_smetercharges = "";
 //BA.debugLineNum = 1465;BA.debugLine="Dim dFranchiseTaxAmt As Double";
_dfranchisetaxamt = 0;
 //BA.debugLineNum = 1466;BA.debugLine="Dim sFranchiseTaxAmt As String";
_sfranchisetaxamt = "";
 //BA.debugLineNum = 1468;BA.debugLine="Dim dSeptageFee As Double";
_dseptagefee = 0;
 //BA.debugLineNum = 1469;BA.debugLine="Dim sSeptageFee As String";
_sseptagefee = "";
 //BA.debugLineNum = 1471;BA.debugLine="Dim rdg_datetime As String";
_rdg_datetime = "";
 //BA.debugLineNum = 1472;BA.debugLine="Dim rdgDate, rdgTime As String";
_rdgdate = "";
_rdgtime = "";
 //BA.debugLineNum = 1473;BA.debugLine="Dim DisconnectionDate As String";
_disconnectiondate = "";
 //BA.debugLineNum = 1475;BA.debugLine="UploadCtr = 0";
_uploadctr = (int) (0);
 //BA.debugLineNum = 1476;BA.debugLine="Try";
try { //BA.debugLineNum = 1477;BA.debugLine="TitleList.Initialize";
_titlelist.Initialize();
 //BA.debugLineNum = 1478;BA.debugLine="HeaderList.Initialize";
_headerlist.Initialize();
 //BA.debugLineNum = 1479;BA.debugLine="DetailList.Initialize";
_detaillist.Initialize();
 //BA.debugLineNum = 1480;BA.debugLine="DisconnectionDate = Starter.DBCon.ExecQuerySingl";
_disconnectiondate = mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult("SELECT DisconnectionDate FROM tblReadings WHERE BookID = "+BA.NumberToString(_ibookid)+" GROUP BY BookID");
 //BA.debugLineNum = 1481;BA.debugLine="LogColor(DisconnectionDate, Colors.Yellow)";
anywheresoftware.b4a.keywords.Common.LogImpl("14849718",_disconnectiondate,anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 //BA.debugLineNum = 1483;BA.debugLine="Starter.strCriteria=\"SELECT * FROM tblBookAssign";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblBookAssignments "+"WHERE BillYear = "+BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ )+" "+"And BillMonth = "+BA.NumberToString(mostCurrent._globalvar._billmonth /*int*/ )+" "+"AND BranchID = "+BA.NumberToString(_ibranchid)+" "+"AND UserID = "+BA.NumberToString(_intreaderid)+" "+"AND BookID = "+BA.NumberToString(_ibookid);
 //BA.debugLineNum = 1490;BA.debugLine="rsHeader = Starter.DBCon.ExecQuery(Starter.strCr";
mostCurrent._rsheader = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 1492;BA.debugLine="If rsHeader.RowCount > 0 Then";
if (mostCurrent._rsheader.getRowCount()>0) { 
 //BA.debugLineNum = 1493;BA.debugLine="rsHeader.Position=0";
mostCurrent._rsheader.setPosition((int) (0));
 //BA.debugLineNum = 1494;BA.debugLine="MP1.Initialize";
_mp1.Initialize();
 //BA.debugLineNum = 1495;BA.debugLine="HeaderList.Clear";
_headerlist.Clear();
 //BA.debugLineNum = 1496;BA.debugLine="MP1.Put(\"Header\", HeaderList)";
_mp1.Put((Object)("Header"),(Object)(_headerlist.getObject()));
 //BA.debugLineNum = 1498;BA.debugLine="MP2.Initialize";
_mp2.Initialize();
 //BA.debugLineNum = 1499;BA.debugLine="MP2.Put(\"branch_id\", iBranchID)";
_mp2.Put((Object)("branch_id"),(Object)(_ibranchid));
 //BA.debugLineNum = 1500;BA.debugLine="MP2.Put(\"bill_month\", GlobalVar.BillMonth)";
_mp2.Put((Object)("bill_month"),(Object)(mostCurrent._globalvar._billmonth /*int*/ ));
 //BA.debugLineNum = 1501;BA.debugLine="MP2.Put(\"bill_year\", GlobalVar.BillYear)";
_mp2.Put((Object)("bill_year"),(Object)(mostCurrent._globalvar._billyear /*double*/ ));
 //BA.debugLineNum = 1502;BA.debugLine="MP2.Put(\"book_id\", iBookID)";
_mp2.Put((Object)("book_id"),(Object)(_ibookid));
 //BA.debugLineNum = 1503;BA.debugLine="MP2.Put(\"batch_number\", rsHeader.GetString(\"Boo";
_mp2.Put((Object)("batch_number"),(Object)(mostCurrent._rsheader.GetString("BookCode")));
 //BA.debugLineNum = 1504;BA.debugLine="MP2.Put(\"batch_type\", $\"RB\"$)";
_mp2.Put((Object)("batch_type"),(Object)(("RB")));
 //BA.debugLineNum = 1505;BA.debugLine="MP2.Put(\"previous_rdg_date\", rsHeader.GetString";
_mp2.Put((Object)("previous_rdg_date"),(Object)(mostCurrent._rsheader.GetString("PrevRdgDate")));
 //BA.debugLineNum = 1506;BA.debugLine="MP2.Put(\"present_rdg_date\", rsHeader.GetString(";
_mp2.Put((Object)("present_rdg_date"),(Object)(mostCurrent._rsheader.GetString("PresRdgDate")));
 //BA.debugLineNum = 1507;BA.debugLine="MP2.Put(\"due_date\", rsHeader.GetString(\"DueDate";
_mp2.Put((Object)("due_date"),(Object)(mostCurrent._rsheader.GetString("DueDate")));
 //BA.debugLineNum = 1508;BA.debugLine="MP2.Put(\"reader_id\", intReaderID)";
_mp2.Put((Object)("reader_id"),(Object)(_intreaderid));
 //BA.debugLineNum = 1509;BA.debugLine="MP2.Put(\"discon_date\", DisconnectionDate)";
_mp2.Put((Object)("discon_date"),(Object)(_disconnectiondate));
 //BA.debugLineNum = 1510;BA.debugLine="HeaderList.Add(MP2)";
_headerlist.Add((Object)(_mp2.getObject()));
 //BA.debugLineNum = 1511;BA.debugLine="MP1.Put(\"Details\", DetailList)";
_mp1.Put((Object)("Details"),(Object)(_detaillist.getObject()));
 //BA.debugLineNum = 1513;BA.debugLine="Starter.strCriteria=\"SELECT * FROM tblReadings";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblReadings WHERE BranchID = "+BA.NumberToString(_ibranchid)+" "+"AND BookID = "+BA.NumberToString(_ibookid)+" "+"AND BillYear = "+BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ )+" "+"AND BillMonth = "+BA.NumberToString(mostCurrent._globalvar._billmonth /*int*/ )+" "+"AND WasRead = 1 "+"AND WasMissCoded = 0 "+"AND PresCum >= 0 "+"AND PresRdg <> '"+""+"' "+"AND WasUploaded = 0 "+"AND PrintStatus = 1 "+"ORDER BY BillNo ASC ";
 //BA.debugLineNum = 1524;BA.debugLine="rsDetails=Starter.DBCon.ExecQuery(Starter.strCr";
mostCurrent._rsdetails = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 1525;BA.debugLine="Log(Starter.strCriteria)";
anywheresoftware.b4a.keywords.Common.LogImpl("14849762",mostCurrent._starter._strcriteria /*String*/ ,0);
 //BA.debugLineNum = 1527;BA.debugLine="UploadCtr = rsDetails.RowCount";
_uploadctr = mostCurrent._rsdetails.getRowCount();
 //BA.debugLineNum = 1529;BA.debugLine="If rsDetails.RowCount > 0 Then";
if (mostCurrent._rsdetails.getRowCount()>0) { 
 //BA.debugLineNum = 1530;BA.debugLine="RecCount = rsDetails.RowCount";
_reccount = mostCurrent._rsdetails.getRowCount();
 //BA.debugLineNum = 1531;BA.debugLine="DetailList.Clear";
_detaillist.Clear();
 //BA.debugLineNum = 1533;BA.debugLine="For i = 0 To RecCount - 1";
{
final int step69 = 1;
final int limit69 = (int) (_reccount-1);
_i = (int) (0) ;
for (;_i <= limit69 ;_i = _i + step69 ) {
 //BA.debugLineNum = 1534;BA.debugLine="rsDetails.Position = i";
mostCurrent._rsdetails.setPosition(_i);
 //BA.debugLineNum = 1535;BA.debugLine="Dim sStatus, sRemarks As String";
_sstatus = "";
_sremarks = "";
 //BA.debugLineNum = 1537;BA.debugLine="sRemarks = rsDetails.GetString(\"ReadRemarks\")";
_sremarks = mostCurrent._rsdetails.GetString("ReadRemarks");
 //BA.debugLineNum = 1538;BA.debugLine="MP3.Initialize";
_mp3.Initialize();
 //BA.debugLineNum = 1539;BA.debugLine="MP3.Put(\"bill_number\", rsDetails.GetString(\"B";
_mp3.Put((Object)("bill_number"),(Object)(mostCurrent._rsdetails.GetString("BillNo")));
 //BA.debugLineNum = 1540;BA.debugLine="MP3.Put(\"bill_type\", rsDetails.GetString(\"Bil";
_mp3.Put((Object)("bill_type"),(Object)(mostCurrent._rsdetails.GetString("BillType")));
 //BA.debugLineNum = 1541;BA.debugLine="MP3.Put(\"account_id\", rsDetails.GetInt(\"AcctI";
_mp3.Put((Object)("account_id"),(Object)(mostCurrent._rsdetails.GetInt("AcctID")));
 //BA.debugLineNum = 1542;BA.debugLine="MP3.Put(\"acct_class\", rsDetails.GetString(\"Ac";
_mp3.Put((Object)("acct_class"),(Object)(mostCurrent._rsdetails.GetString("AcctClass")));
 //BA.debugLineNum = 1543;BA.debugLine="MP3.Put(\"acct_subclass\", rsDetails.GetString(";
_mp3.Put((Object)("acct_subclass"),(Object)(mostCurrent._rsdetails.GetString("AcctSubClass")));
 //BA.debugLineNum = 1544;BA.debugLine="MP3.Put(\"meter_id\", rsDetails.GetInt(\"MeterID";
_mp3.Put((Object)("meter_id"),(Object)(mostCurrent._rsdetails.GetInt("MeterID")));
 //BA.debugLineNum = 1545;BA.debugLine="MP3.Put(\"present_rdg\", rsDetails.GetInt(\"Pres";
_mp3.Put((Object)("present_rdg"),(Object)(mostCurrent._rsdetails.GetInt("PresRdg")));
 //BA.debugLineNum = 1546;BA.debugLine="MP3.Put(\"previous_rdg\", rsDetails.GetInt(\"Pre";
_mp3.Put((Object)("previous_rdg"),(Object)(mostCurrent._rsdetails.GetInt("PrevRdg")));
 //BA.debugLineNum = 1547;BA.debugLine="MP3.Put(\"actual_cum\", rsDetails.GetInt(\"PresC";
_mp3.Put((Object)("actual_cum"),(Object)(mostCurrent._rsdetails.GetInt("PresCum")));
 //BA.debugLineNum = 1548;BA.debugLine="MP3.Put(\"additional_cum\", rsDetails.GetInt(\"A";
_mp3.Put((Object)("additional_cum"),(Object)(mostCurrent._rsdetails.GetInt("AddCons")));
 //BA.debugLineNum = 1549;BA.debugLine="MP3.Put(\"total_cum\", rsDetails.GetInt(\"TotalC";
_mp3.Put((Object)("total_cum"),(Object)(mostCurrent._rsdetails.GetInt("TotalCum")));
 //BA.debugLineNum = 1551;BA.debugLine="dBasic = rsDetails.GetDouble(\"BasicAmt\")";
_dbasic = mostCurrent._rsdetails.GetDouble("BasicAmt");
 //BA.debugLineNum = 1552;BA.debugLine="MP3.Put(\"basic\", dBasic)";
_mp3.Put((Object)("basic"),(Object)(_dbasic));
 //BA.debugLineNum = 1554;BA.debugLine="dPCAAmt = rsDetails.GetDouble(\"PCAAmt\")";
_dpcaamt = mostCurrent._rsdetails.GetDouble("PCAAmt");
 //BA.debugLineNum = 1555;BA.debugLine="MP3.Put(\"pca\", dPCAAmt)";
_mp3.Put((Object)("pca"),(Object)(_dpcaamt));
 //BA.debugLineNum = 1557;BA.debugLine="dDisc = rsDetails.GetDouble(\"DiscAmt\")";
_ddisc = mostCurrent._rsdetails.GetDouble("DiscAmt");
 //BA.debugLineNum = 1558;BA.debugLine="MP3.Put(\"comp_disc\", dDisc)";
_mp3.Put((Object)("comp_disc"),(Object)(_ddisc));
 //BA.debugLineNum = 1560;BA.debugLine="dbillAmt = (rsDetails.GetDouble(\"BasicAmt\") +";
_dbillamt = (mostCurrent._rsdetails.GetDouble("BasicAmt")+mostCurrent._rsdetails.GetDouble("PCAAmt"));
 //BA.debugLineNum = 1561;BA.debugLine="MP3.Put(\"bill_amount\", dbillAmt)";
_mp3.Put((Object)("bill_amount"),(Object)(_dbillamt));
 //BA.debugLineNum = 1563;BA.debugLine="dOthers = rsDetails.GetDouble(\"AddToBillAmt\")";
_dothers = mostCurrent._rsdetails.GetDouble("AddToBillAmt");
 //BA.debugLineNum = 1564;BA.debugLine="MP3.Put(\"others\", dOthers)";
_mp3.Put((Object)("others"),(Object)(_dothers));
 //BA.debugLineNum = 1566;BA.debugLine="dMeterCharges = rsDetails.GetDouble(\"MeterCha";
_dmetercharges = mostCurrent._rsdetails.GetDouble("MeterCharges");
 //BA.debugLineNum = 1567;BA.debugLine="MP3.Put(\"meter_charges\", dMeterCharges)";
_mp3.Put((Object)("meter_charges"),(Object)(_dmetercharges));
 //BA.debugLineNum = 1569;BA.debugLine="dFranchiseTaxAmt = rsDetails.GetDouble(\"Franc";
_dfranchisetaxamt = mostCurrent._rsdetails.GetDouble("FranchiseTaxAmt");
 //BA.debugLineNum = 1570;BA.debugLine="MP3.Put(\"franchise_tax\", dFranchiseTaxAmt)";
_mp3.Put((Object)("franchise_tax"),(Object)(_dfranchisetaxamt));
 //BA.debugLineNum = 1572;BA.debugLine="dSeptageFee = rsDetails.GetDouble(\"SeptageAmt";
_dseptagefee = mostCurrent._rsdetails.GetDouble("SeptageAmt");
 //BA.debugLineNum = 1573;BA.debugLine="dTotalDue = rsDetails.GetDouble(\"BasicAmt\") +";
_dtotaldue = mostCurrent._rsdetails.GetDouble("BasicAmt")+mostCurrent._rsdetails.GetDouble("PCAAmt")+mostCurrent._rsdetails.GetDouble("AddToBillAmt")+mostCurrent._rsdetails.GetDouble("MeterCharges")+mostCurrent._rsdetails.GetDouble("FranchiseTaxAmt")+mostCurrent._rsdetails.GetDouble("SeptageAmt");
 //BA.debugLineNum = 1574;BA.debugLine="dBalance = (rsDetails.GetDouble(\"BasicAmt\") +";
_dbalance = (mostCurrent._rsdetails.GetDouble("BasicAmt")+mostCurrent._rsdetails.GetDouble("AddToBillAmt")+mostCurrent._rsdetails.GetDouble("MeterCharges")+mostCurrent._rsdetails.GetDouble("FranchiseTaxAmt")+mostCurrent._rsdetails.GetDouble("SeptageAmt"))-_dadvpayment;
 //BA.debugLineNum = 1576;BA.debugLine="MP3.Put(\"septage_fee\", dSeptageFee)";
_mp3.Put((Object)("septage_fee"),(Object)(_dseptagefee));
 //BA.debugLineNum = 1577;BA.debugLine="MP3.Put(\"total_due\", dTotalDue)";
_mp3.Put((Object)("total_due"),(Object)(_dtotaldue));
 //BA.debugLineNum = 1579;BA.debugLine="rdgDate = rsDetails.GetString(\"DateRead\")";
_rdgdate = mostCurrent._rsdetails.GetString("DateRead");
 //BA.debugLineNum = 1580;BA.debugLine="rdgTime = rsDetails.GetString(\"TimeRead\")";
_rdgtime = mostCurrent._rsdetails.GetString("TimeRead");
 //BA.debugLineNum = 1582;BA.debugLine="rdg_datetime = rdgDate & \" - \" & rdgTime";
_rdg_datetime = _rdgdate+" - "+_rdgtime;
 //BA.debugLineNum = 1584;BA.debugLine="If rsDetails.GetInt(\"WithDueDate\") = 1 Then";
if (mostCurrent._rsdetails.GetInt("WithDueDate")==1) { 
 //BA.debugLineNum = 1585;BA.debugLine="MP3.Put(\"due_date\", rsDetails.GetString(\"Due";
_mp3.Put((Object)("due_date"),(Object)(mostCurrent._rsdetails.GetString("DueDate")));
 //BA.debugLineNum = 1587;BA.debugLine="dPenalty = (rsDetails.GetDouble(\"BasicAmt\")";
_dpenalty = (mostCurrent._rsdetails.GetDouble("BasicAmt")+mostCurrent._rsdetails.GetDouble("PCAAmt"))*mostCurrent._rsdetails.GetDouble("PenaltyPct");
 //BA.debugLineNum = 1588;BA.debugLine="MP3.Put(\"penalty\", dPenalty)";
_mp3.Put((Object)("penalty"),(Object)(_dpenalty));
 }else {
 //BA.debugLineNum = 1590;BA.debugLine="MP3.Put(\"due_date\", $\"\"$)";
_mp3.Put((Object)("due_date"),(Object)(("")));
 //BA.debugLineNum = 1591;BA.debugLine="MP3.Put(\"penalty\", 0)";
_mp3.Put((Object)("penalty"),(Object)(0));
 };
 //BA.debugLineNum = 1594;BA.debugLine="If rsDetails.GetDouble(\"AdvPayment\") >= (rsDe";
if (mostCurrent._rsdetails.GetDouble("AdvPayment")>=(mostCurrent._rsdetails.GetDouble("BasicAmt")+mostCurrent._rsdetails.GetDouble("PCAAmt")+mostCurrent._rsdetails.GetDouble("AddToBillAmt")+mostCurrent._rsdetails.GetDouble("MeterCharges")+mostCurrent._rsdetails.GetDouble("FranchiseTaxAmt")+mostCurrent._rsdetails.GetDouble("SeptageAmt"))) { 
 //BA.debugLineNum = 1595;BA.debugLine="dAdvPayment = rsDetails.GetDouble(\"BasicAmt\"";
_dadvpayment = mostCurrent._rsdetails.GetDouble("BasicAmt")+mostCurrent._rsdetails.GetDouble("PCAAmt")+mostCurrent._rsdetails.GetDouble("AddToBillAmt")+mostCurrent._rsdetails.GetDouble("MeterCharges")+mostCurrent._rsdetails.GetDouble("FranchiseTaxAmt")+mostCurrent._rsdetails.GetDouble("SeptageAmt");
 }else {
 //BA.debugLineNum = 1597;BA.debugLine="dAdvPayment = rsDetails.GetDouble(\"AdvPaymen";
_dadvpayment = mostCurrent._rsdetails.GetDouble("AdvPayment");
 };
 //BA.debugLineNum = 1600;BA.debugLine="MP3.Put(\"advance\", dAdvPayment)";
_mp3.Put((Object)("advance"),(Object)(_dadvpayment));
 //BA.debugLineNum = 1601;BA.debugLine="MP3.Put(\"amount_paid\", $\"0\"$)";
_mp3.Put((Object)("amount_paid"),(Object)(("0")));
 //BA.debugLineNum = 1602;BA.debugLine="MP3.Put(\"balance\", dBalance)";
_mp3.Put((Object)("balance"),(Object)(_dbalance));
 //BA.debugLineNum = 1604;BA.debugLine="If (rsDetails.GetDouble(\"BasicAmt\") + rsDetai";
if ((mostCurrent._rsdetails.GetDouble("BasicAmt")+mostCurrent._rsdetails.GetDouble("AddToBillAmt")+mostCurrent._rsdetails.GetDouble("MeterCharges")+mostCurrent._rsdetails.GetDouble("FranchiseTaxAmt")+mostCurrent._rsdetails.GetDouble("SeptageAmt"))<=mostCurrent._rsdetails.GetInt("AdvPayment")) { 
 //BA.debugLineNum = 1605;BA.debugLine="sStatus = \"P\"";
_sstatus = "P";
 }else {
 //BA.debugLineNum = 1607;BA.debugLine="sStatus = \"UP\"";
_sstatus = "UP";
 };
 //BA.debugLineNum = 1610;BA.debugLine="MP3.Put(\"status\", sStatus)";
_mp3.Put((Object)("status"),(Object)(_sstatus));
 //BA.debugLineNum = 1611;BA.debugLine="MP3.Put(\"findings\", sRemarks)";
_mp3.Put((Object)("findings"),(Object)(_sremarks));
 //BA.debugLineNum = 1612;BA.debugLine="MP3.Put(\"miss_code\", rsDetails.GetString(\"Mis";
_mp3.Put((Object)("miss_code"),(Object)(mostCurrent._rsdetails.GetString("MissCode")));
 //BA.debugLineNum = 1613;BA.debugLine="MP3.Put(\"validation_type\", rsDetails.GetStrin";
_mp3.Put((Object)("validation_type"),(Object)(mostCurrent._rsdetails.GetString("ImplosiveType")));
 //BA.debugLineNum = 1614;BA.debugLine="MP3.Put(\"previous_rdg_date\", rsDetails.GetStr";
_mp3.Put((Object)("previous_rdg_date"),(Object)(mostCurrent._rsdetails.GetString("PrevRdgDate")));
 //BA.debugLineNum = 1615;BA.debugLine="MP3.Put(\"previous_cum\", rsDetails.GetInt(\"Pre";
_mp3.Put((Object)("previous_cum"),(Object)(mostCurrent._rsdetails.GetInt("PrevCum")));
 //BA.debugLineNum = 1616;BA.debugLine="MP3.Put(\"atb_reference\", rsDetails.GetString(";
_mp3.Put((Object)("atb_reference"),(Object)(mostCurrent._rsdetails.GetString("AtbRef")));
 //BA.debugLineNum = 1617;BA.debugLine="MP3.Put(\"num_input\", rsDetails.GetString(\"NoI";
_mp3.Put((Object)("num_input"),(Object)(mostCurrent._rsdetails.GetString("NoInput")));
 //BA.debugLineNum = 1618;BA.debugLine="MP3.Put(\"rdg_sequence\", rsDetails.GetInt(\"New";
_mp3.Put((Object)("rdg_sequence"),(Object)(mostCurrent._rsdetails.GetInt("NewSeqNo")));
 //BA.debugLineNum = 1619;BA.debugLine="MP3.Put(\"senior\", rsDetails.GetDouble(\"Senior";
_mp3.Put((Object)("senior"),(Object)(mostCurrent._rsdetails.GetDouble("SeniorOnBeforeAmt")));
 //BA.debugLineNum = 1620;BA.debugLine="MP3.Put(\"max_reading\", rsDetails.GetInt(\"MaxR";
_mp3.Put((Object)("max_reading"),(Object)(mostCurrent._rsdetails.GetInt("MaxReading")));
 //BA.debugLineNum = 1621;BA.debugLine="MP3.Put(\"rdg_datetime\", rdg_datetime)";
_mp3.Put((Object)("rdg_datetime"),(Object)(_rdg_datetime));
 //BA.debugLineNum = 1622;BA.debugLine="DetailList.Add(MP3)";
_detaillist.Add((Object)(_mp3.getObject()));
 }
};
 //BA.debugLineNum = 1624;BA.debugLine="Log(rsDetails.RowCount)";
anywheresoftware.b4a.keywords.Common.LogImpl("14849861",BA.NumberToString(mostCurrent._rsdetails.getRowCount()),0);
 };
 //BA.debugLineNum = 1627;BA.debugLine="TitleList.Add(MP1)";
_titlelist.Add((Object)(_mp1.getObject()));
 //BA.debugLineNum = 1628;BA.debugLine="JSONGen.Initialize2(TitleList)";
_jsongen.Initialize2(_titlelist);
 //BA.debugLineNum = 1630;BA.debugLine="Log (JSONGen.ToString)";
anywheresoftware.b4a.keywords.Common.LogImpl("14849867",_jsongen.ToString(),0);
 //BA.debugLineNum = 1632;BA.debugLine="strJson=JSONGen.ToString";
_strjson = _jsongen.ToString();
 //BA.debugLineNum = 1633;BA.debugLine="Log (strJson)";
anywheresoftware.b4a.keywords.Common.LogImpl("14849870",_strjson,0);
 }else {
 //BA.debugLineNum = 1636;BA.debugLine="strJson = \"\"";
_strjson = "";
 };
 } 
       catch (Exception e153) {
			processBA.setLastException(e153); //BA.debugLineNum = 1640;BA.debugLine="strJson = \"\"";
_strjson = "";
 //BA.debugLineNum = 1641;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("14849878",anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),0);
 //BA.debugLineNum = 1642;BA.debugLine="ToastMessageShow($\"Unable to upload readings due";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to upload readings due to failed json conversion.")),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1643;BA.debugLine="Return strJson";
if (true) return _strjson;
 };
 //BA.debugLineNum = 1646;BA.debugLine="Log (strJson)";
anywheresoftware.b4a.keywords.Common.LogImpl("14849883",_strjson,0);
 //BA.debugLineNum = 1647;BA.debugLine="Return strJson";
if (true) return _strjson;
 //BA.debugLineNum = 1648;BA.debugLine="End Sub";
return "";
}
public static String  _deleteassignments() throws Exception{
 //BA.debugLineNum = 1139;BA.debugLine="Private Sub DeleteAssignments()";
 //BA.debugLineNum = 1140;BA.debugLine="Starter.strCriteria=\"DELETE FROM tblBookAssignmen";
mostCurrent._starter._strcriteria /*String*/  = "DELETE FROM tblBookAssignments "+"WHERE BranchID = "+BA.NumberToString(mostCurrent._globalvar._branchid /*int*/ )+" "+"AND BillYear = "+BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ )+" "+"AND BillMonth = "+BA.NumberToString(mostCurrent._globalvar._billmonth /*int*/ )+" "+"AND UserID = "+BA.NumberToString(_readerid)+" "+"AND PresRdgDate = '"+mostCurrent._strrdgdate+"'";
 //BA.debugLineNum = 1146;BA.debugLine="Starter.DBCon.ExecNonQuery(Starter.strCriteria)";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery(mostCurrent._starter._strcriteria /*String*/ );
 //BA.debugLineNum = 1147;BA.debugLine="Starter.strCriteria=\"DELETE FROM NewSequence \"";
mostCurrent._starter._strcriteria /*String*/  = "DELETE FROM NewSequence ";
 //BA.debugLineNum = 1148;BA.debugLine="Starter.DBCon.ExecNonQuery(Starter.strCriteria)";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery(mostCurrent._starter._strcriteria /*String*/ );
 //BA.debugLineNum = 1149;BA.debugLine="End Sub";
return "";
}
public static String  _deletebooks() throws Exception{
 //BA.debugLineNum = 1124;BA.debugLine="Private Sub DeleteBooks()";
 //BA.debugLineNum = 1125;BA.debugLine="Starter.strCriteria=\"DELETE FROM tblBooks WHERE B";
mostCurrent._starter._strcriteria /*String*/  = "DELETE FROM tblBooks WHERE BranchID = "+BA.NumberToString(mostCurrent._globalvar._branchid /*int*/ );
 //BA.debugLineNum = 1126;BA.debugLine="Starter.DBCon.ExecNonQuery(Starter.strCriteria)";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery(mostCurrent._starter._strcriteria /*String*/ );
 //BA.debugLineNum = 1127;BA.debugLine="End Sub";
return "";
}
public static String  _deleteemployeeinfo() throws Exception{
 //BA.debugLineNum = 1176;BA.debugLine="Private Sub DeleteEmployeeInfo()";
 //BA.debugLineNum = 1177;BA.debugLine="Starter.strCriteria=\"DELETE FROM tblUsers \" & _";
mostCurrent._starter._strcriteria /*String*/  = "DELETE FROM tblUsers "+"WHERE UserID = "+BA.NumberToString(_readerid);
 //BA.debugLineNum = 1179;BA.debugLine="Starter.DBCon.ExecNonQuery(Starter.strCriteria)";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery(mostCurrent._starter._strcriteria /*String*/ );
 //BA.debugLineNum = 1180;BA.debugLine="End Sub";
return "";
}
public static String  _deletepca() throws Exception{
 //BA.debugLineNum = 1182;BA.debugLine="Private Sub DeletePCA()";
 //BA.debugLineNum = 1183;BA.debugLine="Starter.strCriteria=\"DELETE FROM tblPCA WHERE Bra";
mostCurrent._starter._strcriteria /*String*/  = "DELETE FROM tblPCA WHERE BranchID = "+BA.NumberToString(mostCurrent._globalvar._branchid /*int*/ );
 //BA.debugLineNum = 1184;BA.debugLine="Starter.DBCon.ExecNonQuery(Starter.strCriteria)";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery(mostCurrent._starter._strcriteria /*String*/ );
 //BA.debugLineNum = 1185;BA.debugLine="End Sub";
return "";
}
public static String  _deleteratedetails() throws Exception{
 //BA.debugLineNum = 1134;BA.debugLine="Private Sub DeleteRateDetails()";
 //BA.debugLineNum = 1135;BA.debugLine="Starter.strCriteria=\"DELETE FROM rates_details\"";
mostCurrent._starter._strcriteria /*String*/  = "DELETE FROM rates_details";
 //BA.debugLineNum = 1136;BA.debugLine="Starter.DBCon.ExecNonQuery(Starter.strCriteria)";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery(mostCurrent._starter._strcriteria /*String*/ );
 //BA.debugLineNum = 1137;BA.debugLine="End Sub";
return "";
}
public static String  _deleterateheader() throws Exception{
 //BA.debugLineNum = 1129;BA.debugLine="Private Sub DeleteRateHeader()";
 //BA.debugLineNum = 1130;BA.debugLine="Starter.strCriteria=\"DELETE FROM rates_header WHE";
mostCurrent._starter._strcriteria /*String*/  = "DELETE FROM rates_header WHERE branch_id = "+BA.NumberToString(mostCurrent._globalvar._branchid /*int*/ );
 //BA.debugLineNum = 1131;BA.debugLine="Starter.DBCon.ExecNonQuery(Starter.strCriteria)";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery(mostCurrent._starter._strcriteria /*String*/ );
 //BA.debugLineNum = 1132;BA.debugLine="End Sub";
return "";
}
public static String  _deletereadings() throws Exception{
long _ddate = 0L;
String _spresrdgdate = "";
String _syear = "";
String _smonth = "";
String _sday = "";
 //BA.debugLineNum = 1151;BA.debugLine="Private Sub DeleteReadings()";
 //BA.debugLineNum = 1152;BA.debugLine="Dim dDate As Long";
_ddate = 0L;
 //BA.debugLineNum = 1153;BA.debugLine="Dim sPresRdgDate As String";
_spresrdgdate = "";
 //BA.debugLineNum = 1154;BA.debugLine="Dim sYear, sMonth, sDay As String";
_syear = "";
_smonth = "";
_sday = "";
 //BA.debugLineNum = 1159;BA.debugLine="DateTime.DateFormat=\"yyyy-MM-dd\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("yyyy-MM-dd");
 //BA.debugLineNum = 1160;BA.debugLine="dDate = DateTime.DateParse(strRdgDate)";
_ddate = anywheresoftware.b4a.keywords.Common.DateTime.DateParse(mostCurrent._strrdgdate);
 //BA.debugLineNum = 1161;BA.debugLine="DateTime.DateFormat=\"MM/dd/yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("MM/dd/yyyy");
 //BA.debugLineNum = 1162;BA.debugLine="sPresRdgDate = DateTime.Date(dDate)";
_spresrdgdate = anywheresoftware.b4a.keywords.Common.DateTime.Date(_ddate);
 //BA.debugLineNum = 1165;BA.debugLine="Log(sPresRdgDate)";
anywheresoftware.b4a.keywords.Common.LogImpl("13997710",_spresrdgdate,0);
 //BA.debugLineNum = 1167;BA.debugLine="Starter.strCriteria=\"DELETE FROM tblReadings \" &";
mostCurrent._starter._strcriteria /*String*/  = "DELETE FROM tblReadings "+"WHERE BillYear = "+BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ )+" "+"AND BillMonth = "+BA.NumberToString(mostCurrent._globalvar._billmonth /*int*/ )+" "+"AND BranchID = "+BA.NumberToString(mostCurrent._globalvar._branchid /*int*/ )+" "+"AND PresRdgDate = '"+_spresrdgdate+"' "+"AND ReadBy = "+BA.NumberToString(_readerid);
 //BA.debugLineNum = 1173;BA.debugLine="Starter.DBCon.ExecNonQuery(Starter.strCriteria)";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery(mostCurrent._starter._strcriteria /*String*/ );
 //BA.debugLineNum = 1174;BA.debugLine="End Sub";
return "";
}
public static String  _deleteseptagerates() throws Exception{
 //BA.debugLineNum = 1187;BA.debugLine="Private Sub DeleteSeptageRates()";
 //BA.debugLineNum = 1188;BA.debugLine="Starter.strCriteria=\"DELETE FROM SSMRates WHERE B";
mostCurrent._starter._strcriteria /*String*/  = "DELETE FROM SSMRates WHERE BranchID = "+BA.NumberToString(mostCurrent._globalvar._branchid /*int*/ );
 //BA.debugLineNum = 1189;BA.debugLine="Starter.DBCon.ExecNonQuery(Starter.strCriteria)";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery(mostCurrent._starter._strcriteria /*String*/ );
 //BA.debugLineNum = 1190;BA.debugLine="End Sub";
return "";
}
public static String  _dispinfomsg(String _smsg,String _stitle) throws Exception{
anywheresoftware.b4a.objects.CSBuilder _cstitle = null;
anywheresoftware.b4a.objects.CSBuilder _csmsg = null;
 //BA.debugLineNum = 1903;BA.debugLine="Private Sub DispInfoMsg(sMsg As String, sTitle As";
 //BA.debugLineNum = 1905;BA.debugLine="Dim csTitle, csMsg As CSBuilder";
_cstitle = new anywheresoftware.b4a.objects.CSBuilder();
_csmsg = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 1906;BA.debugLine="csTitle.Initialize.Color(Colors.Red).Append(sTitl";
_cstitle.Initialize().Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(_stitle)).PopAll();
 //BA.debugLineNum = 1907;BA.debugLine="csMsg.Initialize.Bold.Color(Colors.DarkGray).Appe";
_csmsg.Initialize().Bold().Color(anywheresoftware.b4a.keywords.Common.Colors.DarkGray).Append(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.CRLF+_smsg)).PopAll();
 //BA.debugLineNum = 1909;BA.debugLine="Msgbox(csMsg, csTitle)";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence(_csmsg.getObject()),BA.ObjectToCharSequence(_cstitle.getObject()),mostCurrent.activityBA);
 //BA.debugLineNum = 1910;BA.debugLine="End Sub";
return "";
}
public static boolean  _dispuploadmsg(String _smsg,String _stitle) throws Exception{
anywheresoftware.b4a.objects.CSBuilder _cstitle = null;
anywheresoftware.b4a.objects.CSBuilder _csmsg = null;
anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper _icon = null;
boolean _blnretval = false;
int _iretval = 0;
 //BA.debugLineNum = 1912;BA.debugLine="Private Sub DispUploadMsg(sMsg As String, sTitle A";
 //BA.debugLineNum = 1913;BA.debugLine="Dim csTitle, csMsg As CSBuilder";
_cstitle = new anywheresoftware.b4a.objects.CSBuilder();
_csmsg = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 1914;BA.debugLine="Dim icon As B4XBitmap";
_icon = new anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper();
 //BA.debugLineNum = 1915;BA.debugLine="Dim blnRetVal As Boolean";
_blnretval = false;
 //BA.debugLineNum = 1916;BA.debugLine="Dim iRetVal As Int";
_iretval = 0;
 //BA.debugLineNum = 1917;BA.debugLine="csTitle.Initialize.Color(Colors.Red).Append(sTitl";
_cstitle.Initialize().Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(_stitle)).PopAll();
 //BA.debugLineNum = 1918;BA.debugLine="csMsg.Initialize.Color(GlobalVar.PriColor).Append";
_csmsg.Initialize().Color((int) (mostCurrent._globalvar._pricolor /*double*/ )).Append(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.CRLF+_smsg)).PopAll();
 //BA.debugLineNum = 1919;BA.debugLine="icon = xui.LoadBitmapResize(File.DirAssets, \"uplo";
_icon = _xui.LoadBitmapResize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"upload_ok.png",anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (24)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (24)),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1921;BA.debugLine="iRetVal = Msgbox2(csMsg, csTitle, $\"OK\"$, \"\", $\"C";
_iretval = anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence(_csmsg.getObject()),BA.ObjectToCharSequence(_cstitle.getObject()),("OK"),"",("CANCEL"),(android.graphics.Bitmap)(_icon.getObject()),mostCurrent.activityBA);
 //BA.debugLineNum = 1923;BA.debugLine="If iRetVal = DialogResponse.POSITIVE Then";
if (_iretval==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 1924;BA.debugLine="blnRetVal = True";
_blnretval = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 1926;BA.debugLine="blnRetVal = False";
_blnretval = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 1929;BA.debugLine="Return blnRetVal";
if (true) return _blnretval;
 //BA.debugLineNum = 1930;BA.debugLine="End Sub";
return false;
}
public static String  _dlulviewbinder_onbindview(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,int _viewtype) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alertdialog = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
anywheresoftware.b4a.objects.CSBuilder _cs = null;
 //BA.debugLineNum = 2205;BA.debugLine="Private Sub DLULViewBinder_OnBindView (View As Vie";
 //BA.debugLineNum = 2206;BA.debugLine="Dim AlertDialog As AX_CustomAlertDialog";
_alertdialog = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 2207;BA.debugLine="AlertDialog.Initialize";
_alertdialog.Initialize();
 //BA.debugLineNum = 2209;BA.debugLine="If ViewType = AlertDialog.VIEW_TITLE Then ' Title";
if (_viewtype==_alertdialog.VIEW_TITLE) { 
 //BA.debugLineNum = 2210;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 2211;BA.debugLine="Dim CS As CSBuilder";
_cs = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 2213;BA.debugLine="CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Ty";
_cs.Initialize().Typeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD).Typeface(anywheresoftware.b4a.keywords.Common.Typeface.getMATERIALICONS()).Size((int) (26)).Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xe834)))+"  "));
 //BA.debugLineNum = 2214;BA.debugLine="CS.Typeface(GlobalVar.Font).Size(22).Append(lbl.";
_cs.Typeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).Size((int) (22)).Append(BA.ObjectToCharSequence(_lbl.getText())).Pop();
 //BA.debugLineNum = 2216;BA.debugLine="lbl.Text = CS.PopAll";
_lbl.setText(BA.ObjectToCharSequence(_cs.PopAll().getObject()));
 };
 //BA.debugLineNum = 2218;BA.debugLine="If ViewType = AlertDialog.VIEW_MESSAGE Then";
if (_viewtype==_alertdialog.VIEW_MESSAGE) { 
 //BA.debugLineNum = 2219;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 2220;BA.debugLine="lbl.TextSize = 16";
_lbl.setTextSize((float) (16));
 //BA.debugLineNum = 2221;BA.debugLine="lbl.TextColor = Colors.Gray";
_lbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 };
 //BA.debugLineNum = 2223;BA.debugLine="End Sub";
return "";
}
public static void  _downloadbookassignments(int _ibranchid,int _ibillyear,int _ibillmonth,int _ireaderid,String _sreadingdate) throws Exception{
ResumableSub_DownloadBookAssignments rsub = new ResumableSub_DownloadBookAssignments(null,_ibranchid,_ibillyear,_ibillmonth,_ireaderid,_sreadingdate);
rsub.resume(processBA, null);
}
public static class ResumableSub_DownloadBookAssignments extends BA.ResumableSub {
public ResumableSub_DownloadBookAssignments(com.bwsi.MeterReader.datasyncing parent,int _ibranchid,int _ibillyear,int _ibillmonth,int _ireaderid,String _sreadingdate) {
this.parent = parent;
this._ibranchid = _ibranchid;
this._ibillyear = _ibillyear;
this._ibillmonth = _ibillmonth;
this._ireaderid = _ireaderid;
this._sreadingdate = _sreadingdate;
}
com.bwsi.MeterReader.datasyncing parent;
int _ibranchid;
int _ibillyear;
int _ibillmonth;
int _ireaderid;
String _sreadingdate;
String _urlname = "";
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
 //BA.debugLineNum = 483;BA.debugLine="Dim URLName As String";
_urlname = "";
 //BA.debugLineNum = 484;BA.debugLine="Dim RetVal As String";
_retval = "";
 //BA.debugLineNum = 486;BA.debugLine="Dim j As HttpJob";
_j = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 487;BA.debugLine="j.Initialize(\"\",Me)";
_j._initialize(processBA,"",datasyncing.getObject());
 //BA.debugLineNum = 488;BA.debugLine="URLName = GlobalVar.ServerAddress & GlobalVar.Con";
_urlname = parent.mostCurrent._globalvar._serveraddress /*String*/ +parent.mostCurrent._globalvar._controllerprefix /*String*/ +"bookAssignments";
 //BA.debugLineNum = 489;BA.debugLine="j.Download2(URLName, Array As String(\"BranchID\",i";
_j._download2(_urlname,new String[]{"BranchID",BA.NumberToString(_ibranchid),"BillYear",BA.NumberToString(_ibillyear),"BillMonth",BA.NumberToString(_ibillmonth),"ReaderID",BA.NumberToString(_ireaderid),"rdgDate",_sreadingdate});
 //BA.debugLineNum = 491;BA.debugLine="sURL = URLName & \"?BranchID=\" & iBranchID & \"&Bil";
parent.mostCurrent._surl = _urlname+"?BranchID="+BA.NumberToString(_ibranchid)+"&BillYear="+BA.NumberToString(_ibillyear)+"&BillMonth="+BA.NumberToString(_ibillmonth)+"&ReaderID="+BA.NumberToString(_ireaderid)+"&rdgDate="+_sreadingdate;
 //BA.debugLineNum = 492;BA.debugLine="LogColor(sURL, Colors.Red)";
anywheresoftware.b4a.keywords.Common.LogImpl("12162698",parent.mostCurrent._surl,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 493;BA.debugLine="ProgressDialogShow2($\"Downloading Book Assignment";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence(("Downloading Book Assignments...")),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 494;BA.debugLine="Wait For (j) JobDone(j As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_j));
this.state = 11;
return;
case 11:
//C
this.state = 1;
_j = (anywheresoftware.b4a.samples.httputils2.httpjob) result[0];
;
 //BA.debugLineNum = 495;BA.debugLine="If j.Success Then";
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
 //BA.debugLineNum = 496;BA.debugLine="RetVal = j.GetString";
_retval = _j._getstring();
 //BA.debugLineNum = 497;BA.debugLine="Log(URLName)";
anywheresoftware.b4a.keywords.Common.LogImpl("12162703",_urlname,0);
 //BA.debugLineNum = 498;BA.debugLine="Log(RetVal)";
anywheresoftware.b4a.keywords.Common.LogImpl("12162704",_retval,0);
 //BA.debugLineNum = 499;BA.debugLine="If RetVal = \"[]\" Then";
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
 //BA.debugLineNum = 500;BA.debugLine="snack.Initialize(\"\", Activity, $\"No book assign";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(parent.mostCurrent._activity.getObject()),("No book assignment(s) available to Download!"),parent.mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 501;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 502;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 503;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 504;BA.debugLine="j.Release";
_j._release();
 //BA.debugLineNum = 505;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 7:
//C
this.state = 10;
;
 //BA.debugLineNum = 507;BA.debugLine="SaveBookAssignments(RetVal)";
_savebookassignments(_retval);
 if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 509;BA.debugLine="snack.Initialize(\"RetryDownloadBookAssignments\",";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"RetryDownloadBookAssignments",(android.view.View)(parent.mostCurrent._activity.getObject()),("Unable to Download Book Assignment(s) due to ")+_j._errormessage,parent.mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 510;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 511;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 512;BA.debugLine="snack.SetAction(\"Retry\")";
parent.mostCurrent._snack.SetAction("Retry");
 //BA.debugLineNum = 513;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 514;BA.debugLine="Log(j.ErrorMessage)";
anywheresoftware.b4a.keywords.Common.LogImpl("12162720",_j._errormessage,0);
 //BA.debugLineNum = 515;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 if (true) break;

case 10:
//C
this.state = -1;
;
 //BA.debugLineNum = 517;BA.debugLine="j.Release";
_j._release();
 //BA.debugLineNum = 518;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 519;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _jobdone(anywheresoftware.b4a.samples.httputils2.httpjob _j) throws Exception{
}
public static void  _downloadbookpca(int _ibranchid,String _sreadingdate) throws Exception{
ResumableSub_DownloadBookPCA rsub = new ResumableSub_DownloadBookPCA(null,_ibranchid,_sreadingdate);
rsub.resume(processBA, null);
}
public static class ResumableSub_DownloadBookPCA extends BA.ResumableSub {
public ResumableSub_DownloadBookPCA(com.bwsi.MeterReader.datasyncing parent,int _ibranchid,String _sreadingdate) {
this.parent = parent;
this._ibranchid = _ibranchid;
this._sreadingdate = _sreadingdate;
}
com.bwsi.MeterReader.datasyncing parent;
int _ibranchid;
String _sreadingdate;
String _urlname = "";
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
 //BA.debugLineNum = 632;BA.debugLine="Dim URLName As String";
_urlname = "";
 //BA.debugLineNum = 633;BA.debugLine="Dim RetVal As String";
_retval = "";
 //BA.debugLineNum = 635;BA.debugLine="Dim j As HttpJob";
_j = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 636;BA.debugLine="j.Initialize(\"\",Me)";
_j._initialize(processBA,"",datasyncing.getObject());
 //BA.debugLineNum = 637;BA.debugLine="URLName = GlobalVar.ServerAddress & GlobalVar.Con";
_urlname = parent.mostCurrent._globalvar._serveraddress /*String*/ +parent.mostCurrent._globalvar._controllerprefix /*String*/ +"bookPCA";
 //BA.debugLineNum = 638;BA.debugLine="j.Download2(URLName, Array As String(\"BranchID\",i";
_j._download2(_urlname,new String[]{"BranchID",BA.NumberToString(_ibranchid),"CutOff",_sreadingdate});
 //BA.debugLineNum = 639;BA.debugLine="Log(URLName & \"?\" & \"BranchID=\" & iBranchID & \"rd";
anywheresoftware.b4a.keywords.Common.LogImpl("12424840",_urlname+"?"+"BranchID="+BA.NumberToString(_ibranchid)+"rdgDate="+_sreadingdate,0);
 //BA.debugLineNum = 641;BA.debugLine="ProgressDialogShow2($\"Downloading Data...\"$, Fals";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence(("Downloading Data...")),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 642;BA.debugLine="Wait For (j) JobDone(j As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_j));
this.state = 11;
return;
case 11:
//C
this.state = 1;
_j = (anywheresoftware.b4a.samples.httputils2.httpjob) result[0];
;
 //BA.debugLineNum = 643;BA.debugLine="If j.Success Then";
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
 //BA.debugLineNum = 644;BA.debugLine="RetVal = j.GetString";
_retval = _j._getstring();
 //BA.debugLineNum = 645;BA.debugLine="Log(URLName)";
anywheresoftware.b4a.keywords.Common.LogImpl("12424846",_urlname,0);
 //BA.debugLineNum = 646;BA.debugLine="Log(RetVal)";
anywheresoftware.b4a.keywords.Common.LogImpl("12424847",_retval,0);
 //BA.debugLineNum = 647;BA.debugLine="If RetVal = \"[]\" Then";
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
 //BA.debugLineNum = 648;BA.debugLine="snack.Initialize(\"\", Activity, $\"No PCA record";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(parent.mostCurrent._activity.getObject()),("No PCA record found on the specified branch!"),parent.mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 649;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 650;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 651;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 652;BA.debugLine="j.Release";
_j._release();
 //BA.debugLineNum = 653;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 7:
//C
this.state = 10;
;
 //BA.debugLineNum = 655;BA.debugLine="SaveBookPCA(RetVal)";
_savebookpca(_retval);
 if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 657;BA.debugLine="snack.Initialize(\"RetryDownloadBookPCA\", Activit";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"RetryDownloadBookPCA",(android.view.View)(parent.mostCurrent._activity.getObject()),("Unable to Download Book PCA(s) due to ")+_j._errormessage,parent.mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 658;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 659;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 660;BA.debugLine="snack.SetAction(\"Retry\")";
parent.mostCurrent._snack.SetAction("Retry");
 //BA.debugLineNum = 661;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 662;BA.debugLine="Log(j.ErrorMessage)";
anywheresoftware.b4a.keywords.Common.LogImpl("12424863",_j._errormessage,0);
 //BA.debugLineNum = 663;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 if (true) break;

case 10:
//C
this.state = -1;
;
 //BA.debugLineNum = 665;BA.debugLine="j.Release";
_j._release();
 //BA.debugLineNum = 666;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 667;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _downloadbooks(int _ibranchid) throws Exception{
ResumableSub_DownloadBooks rsub = new ResumableSub_DownloadBooks(null,_ibranchid);
rsub.resume(processBA, null);
}
public static class ResumableSub_DownloadBooks extends BA.ResumableSub {
public ResumableSub_DownloadBooks(com.bwsi.MeterReader.datasyncing parent,int _ibranchid) {
this.parent = parent;
this._ibranchid = _ibranchid;
}
com.bwsi.MeterReader.datasyncing parent;
int _ibranchid;
String _urlname = "";
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
 //BA.debugLineNum = 403;BA.debugLine="Dim URLName As String";
_urlname = "";
 //BA.debugLineNum = 404;BA.debugLine="Dim RetVal As String";
_retval = "";
 //BA.debugLineNum = 406;BA.debugLine="Dim j As HttpJob";
_j = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 408;BA.debugLine="j.Initialize(\"\",Me)";
_j._initialize(processBA,"",datasyncing.getObject());
 //BA.debugLineNum = 409;BA.debugLine="URLName = GlobalVar.ServerAddress & GlobalVar.Con";
_urlname = parent.mostCurrent._globalvar._serveraddress /*String*/ +parent.mostCurrent._globalvar._controllerprefix /*String*/ +"bookData";
 //BA.debugLineNum = 410;BA.debugLine="Log (URLName & $\"BranchID = \"$ & iBranchID)";
anywheresoftware.b4a.keywords.Common.LogImpl("12031624",_urlname+("BranchID = ")+BA.NumberToString(_ibranchid),0);
 //BA.debugLineNum = 411;BA.debugLine="j.Download2(URLName, Array As String(\"BranchID\",";
_j._download2(_urlname,new String[]{"BranchID",BA.NumberToString(_ibranchid)});
 //BA.debugLineNum = 413;BA.debugLine="ProgressDialogShow2($\"Downloading Book/Zone Data.";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence(("Downloading Book/Zone Data...")),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 414;BA.debugLine="Wait For (j) JobDone(j As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_j));
this.state = 11;
return;
case 11:
//C
this.state = 1;
_j = (anywheresoftware.b4a.samples.httputils2.httpjob) result[0];
;
 //BA.debugLineNum = 415;BA.debugLine="If j.Success Then";
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
 //BA.debugLineNum = 416;BA.debugLine="RetVal = j.GetString";
_retval = _j._getstring();
 //BA.debugLineNum = 417;BA.debugLine="Log(RetVal)";
anywheresoftware.b4a.keywords.Common.LogImpl("12031631",_retval,0);
 //BA.debugLineNum = 418;BA.debugLine="If RetVal = \"[]\" Then";
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
 //BA.debugLineNum = 419;BA.debugLine="snack.Initialize(\"\", Activity, $\"No Book Inform";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(parent.mostCurrent._activity.getObject()),("No Book Information available to Download!"),parent.mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 420;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 421;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 422;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 423;BA.debugLine="j.Release";
_j._release();
 //BA.debugLineNum = 424;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 7:
//C
this.state = 10;
;
 //BA.debugLineNum = 426;BA.debugLine="SaveBookData(RetVal)";
_savebookdata(_retval);
 if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 428;BA.debugLine="snack.Initialize(\"RetryDownloadBooks\", Activity,";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"RetryDownloadBooks",(android.view.View)(parent.mostCurrent._activity.getObject()),("Unable to Download Book Data due to ")+_j._errormessage,parent.mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 429;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 430;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 431;BA.debugLine="snack.SetAction(\"Retry\")";
parent.mostCurrent._snack.SetAction("Retry");
 //BA.debugLineNum = 432;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 433;BA.debugLine="Log(j.ErrorMessage)";
anywheresoftware.b4a.keywords.Common.LogImpl("12031647",_j._errormessage,0);
 //BA.debugLineNum = 434;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 if (true) break;

case 10:
//C
this.state = -1;
;
 //BA.debugLineNum = 436;BA.debugLine="j.Release";
_j._release();
 //BA.debugLineNum = 438;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 439;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _downloadcustomers(int _ibranchid,int _ireaderid,String _sreadingdate) throws Exception{
ResumableSub_DownloadCustomers rsub = new ResumableSub_DownloadCustomers(null,_ibranchid,_ireaderid,_sreadingdate);
rsub.resume(processBA, null);
}
public static class ResumableSub_DownloadCustomers extends BA.ResumableSub {
public ResumableSub_DownloadCustomers(com.bwsi.MeterReader.datasyncing parent,int _ibranchid,int _ireaderid,String _sreadingdate) {
this.parent = parent;
this._ibranchid = _ibranchid;
this._ireaderid = _ireaderid;
this._sreadingdate = _sreadingdate;
}
com.bwsi.MeterReader.datasyncing parent;
int _ibranchid;
int _ireaderid;
String _sreadingdate;
String _urlname = "";
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
 //BA.debugLineNum = 522;BA.debugLine="Dim URLName As String";
_urlname = "";
 //BA.debugLineNum = 523;BA.debugLine="Dim RetVal As String";
_retval = "";
 //BA.debugLineNum = 525;BA.debugLine="Dim j As HttpJob";
_j = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 526;BA.debugLine="j.Initialize(\"\",Me)";
_j._initialize(processBA,"",datasyncing.getObject());
 //BA.debugLineNum = 527;BA.debugLine="URLName = GlobalVar.ServerAddress & GlobalVar.Con";
_urlname = parent.mostCurrent._globalvar._serveraddress /*String*/ +parent.mostCurrent._globalvar._controllerprefix /*String*/ +"getDetails";
 //BA.debugLineNum = 528;BA.debugLine="j.Download2(URLName, Array As String(\"branchID\",";
_j._download2(_urlname,new String[]{"branchID",BA.NumberToString(_ibranchid),"userID",BA.NumberToString(_ireaderid),"rdgDate",_sreadingdate});
 //BA.debugLineNum = 529;BA.debugLine="Log(URLName & \"?\" & \"BranchID=\" & iBranchID &  \"&";
anywheresoftware.b4a.keywords.Common.LogImpl("12228232",_urlname+"?"+"BranchID="+BA.NumberToString(_ibranchid)+"&ReaderID="+BA.NumberToString(_ireaderid)+"&rdgDate="+_sreadingdate,0);
 //BA.debugLineNum = 531;BA.debugLine="ProgressDialogShow2($\"Downloading Customer Accoun";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence(("Downloading Customer Accounts and Data...")),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 532;BA.debugLine="Wait For (j) JobDone(j As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_j));
this.state = 11;
return;
case 11:
//C
this.state = 1;
_j = (anywheresoftware.b4a.samples.httputils2.httpjob) result[0];
;
 //BA.debugLineNum = 533;BA.debugLine="If j.Success Then";
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
 //BA.debugLineNum = 534;BA.debugLine="RetVal = j.GetString";
_retval = _j._getstring();
 //BA.debugLineNum = 535;BA.debugLine="Log(URLName)";
anywheresoftware.b4a.keywords.Common.LogImpl("12228238",_urlname,0);
 //BA.debugLineNum = 536;BA.debugLine="Log(RetVal)";
anywheresoftware.b4a.keywords.Common.LogImpl("12228239",_retval,0);
 //BA.debugLineNum = 537;BA.debugLine="If RetVal = \"[]\" Then";
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
 //BA.debugLineNum = 538;BA.debugLine="j.Release";
_j._release();
 //BA.debugLineNum = 539;BA.debugLine="snack.Initialize(\"\", Activity, $\"No customer ac";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(parent.mostCurrent._activity.getObject()),("No customer account available to download!"),parent.mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 540;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 541;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 542;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 543;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 7:
//C
this.state = 10;
;
 //BA.debugLineNum = 545;BA.debugLine="SaveCustomerAccounts(RetVal)";
_savecustomeraccounts(_retval);
 if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 547;BA.debugLine="snack.Initialize(\"RetryDownloadCustomers\", Activ";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"RetryDownloadCustomers",(android.view.View)(parent.mostCurrent._activity.getObject()),("Unable to Download Customer's Account(s) due to ")+_j._errormessage,parent.mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 548;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 549;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 550;BA.debugLine="snack.SetAction(\"Retry\")";
parent.mostCurrent._snack.SetAction("Retry");
 //BA.debugLineNum = 551;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 552;BA.debugLine="Log(j.ErrorMessage)";
anywheresoftware.b4a.keywords.Common.LogImpl("12228255",_j._errormessage,0);
 //BA.debugLineNum = 553;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 if (true) break;

case 10:
//C
this.state = -1;
;
 //BA.debugLineNum = 555;BA.debugLine="j.Release";
_j._release();
 //BA.debugLineNum = 556;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 557;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _downloademployeeinfo(int _ireaderid,int _ibranchid) throws Exception{
ResumableSub_DownloadEmployeeInfo rsub = new ResumableSub_DownloadEmployeeInfo(null,_ireaderid,_ibranchid);
rsub.resume(processBA, null);
}
public static class ResumableSub_DownloadEmployeeInfo extends BA.ResumableSub {
public ResumableSub_DownloadEmployeeInfo(com.bwsi.MeterReader.datasyncing parent,int _ireaderid,int _ibranchid) {
this.parent = parent;
this._ireaderid = _ireaderid;
this._ibranchid = _ibranchid;
}
com.bwsi.MeterReader.datasyncing parent;
int _ireaderid;
int _ibranchid;
String _urlname = "";
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
 //BA.debugLineNum = 442;BA.debugLine="Dim URLName As String";
_urlname = "";
 //BA.debugLineNum = 443;BA.debugLine="Dim RetVal As String";
_retval = "";
 //BA.debugLineNum = 445;BA.debugLine="Dim j As HttpJob";
_j = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 447;BA.debugLine="Log($\"ReaderID = \"$ & iReaderID)";
anywheresoftware.b4a.keywords.Common.LogImpl("12097158",("ReaderID = ")+BA.NumberToString(_ireaderid),0);
 //BA.debugLineNum = 448;BA.debugLine="j.Initialize(\"\",Me)";
_j._initialize(processBA,"",datasyncing.getObject());
 //BA.debugLineNum = 449;BA.debugLine="URLName = GlobalVar.ServerAddress & GlobalVar.Con";
_urlname = parent.mostCurrent._globalvar._serveraddress /*String*/ +parent.mostCurrent._globalvar._controllerprefix /*String*/ +"employeeInfo";
 //BA.debugLineNum = 450;BA.debugLine="j.Download2(URLName, Array As String(\"UserID\", iR";
_j._download2(_urlname,new String[]{"UserID",BA.NumberToString(_ireaderid),"BranchID",BA.NumberToString(_ibranchid)});
 //BA.debugLineNum = 451;BA.debugLine="Log(URLName & \"?\" & \"&UserID=\" &  iReaderID & \"&B";
anywheresoftware.b4a.keywords.Common.LogImpl("12097162",_urlname+"?"+"&UserID="+BA.NumberToString(_ireaderid)+"&BranchID="+BA.NumberToString(_ibranchid),0);
 //BA.debugLineNum = 453;BA.debugLine="ProgressDialogShow2($\"Downloading Reader's Data..";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence(("Downloading Reader's Data...")),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 454;BA.debugLine="Wait For (j) JobDone(j As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_j));
this.state = 11;
return;
case 11:
//C
this.state = 1;
_j = (anywheresoftware.b4a.samples.httputils2.httpjob) result[0];
;
 //BA.debugLineNum = 455;BA.debugLine="If j.Success Then";
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
 //BA.debugLineNum = 456;BA.debugLine="RetVal = j.GetString";
_retval = _j._getstring();
 //BA.debugLineNum = 457;BA.debugLine="Log(URLName)";
anywheresoftware.b4a.keywords.Common.LogImpl("12097168",_urlname,0);
 //BA.debugLineNum = 458;BA.debugLine="Log(RetVal)";
anywheresoftware.b4a.keywords.Common.LogImpl("12097169",_retval,0);
 //BA.debugLineNum = 460;BA.debugLine="If RetVal = \"[]\" Then";
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
 //BA.debugLineNum = 461;BA.debugLine="j.Release";
_j._release();
 //BA.debugLineNum = 462;BA.debugLine="snack.Initialize(\"\", Activity, $\"Employee info";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(parent.mostCurrent._activity.getObject()),("Employee info not available to download!"),parent.mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 463;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 464;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 465;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 466;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 7:
//C
this.state = 10;
;
 //BA.debugLineNum = 468;BA.debugLine="SaveReaderInfo(RetVal)";
_savereaderinfo(_retval);
 if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 470;BA.debugLine="snack.Initialize(\"RetryDownloadEmployeeInfo\", Ac";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"RetryDownloadEmployeeInfo",(android.view.View)(parent.mostCurrent._activity.getObject()),("Unable to Selected Reader Information due to ")+_j._errormessage,parent.mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 471;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 472;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 473;BA.debugLine="snack.SetAction(\"Retry\")";
parent.mostCurrent._snack.SetAction("Retry");
 //BA.debugLineNum = 474;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 475;BA.debugLine="Log(j.ErrorMessage)";
anywheresoftware.b4a.keywords.Common.LogImpl("12097186",_j._errormessage,0);
 //BA.debugLineNum = 476;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 if (true) break;

case 10:
//C
this.state = -1;
;
 //BA.debugLineNum = 478;BA.debugLine="j.Release";
_j._release();
 //BA.debugLineNum = 479;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 480;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _downloading_buttonpressed(de.amberhome.materialdialogs.MaterialDialogWrapper _dialog,String _saction) throws Exception{
 //BA.debugLineNum = 317;BA.debugLine="Private Sub Downloading_ButtonPressed(Dialog As Ma";
 //BA.debugLineNum = 318;BA.debugLine="Select Case sAction";
switch (BA.switchObjectToInt(_saction,_dialog.ACTION_POSITIVE,_dialog.ACTION_NEGATIVE)) {
case 0: {
 //BA.debugLineNum = 320;BA.debugLine="ShowDLPasswordDialog";
_showdlpassworddialog();
 break; }
case 1: {
 //BA.debugLineNum = 322;BA.debugLine="Return";
if (true) return "";
 break; }
}
;
 //BA.debugLineNum = 324;BA.debugLine="End Sub";
return "";
}
public static void  _downloadratedetails(int _ibranchid) throws Exception{
ResumableSub_DownloadRateDetails rsub = new ResumableSub_DownloadRateDetails(null,_ibranchid);
rsub.resume(processBA, null);
}
public static class ResumableSub_DownloadRateDetails extends BA.ResumableSub {
public ResumableSub_DownloadRateDetails(com.bwsi.MeterReader.datasyncing parent,int _ibranchid) {
this.parent = parent;
this._ibranchid = _ibranchid;
}
com.bwsi.MeterReader.datasyncing parent;
int _ibranchid;
String _urlname = "";
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
 //BA.debugLineNum = 595;BA.debugLine="Dim URLName As String";
_urlname = "";
 //BA.debugLineNum = 596;BA.debugLine="Dim RetVal As String";
_retval = "";
 //BA.debugLineNum = 598;BA.debugLine="Dim j As HttpJob";
_j = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 599;BA.debugLine="j.Initialize(\"\",Me)";
_j._initialize(processBA,"",datasyncing.getObject());
 //BA.debugLineNum = 600;BA.debugLine="URLName = GlobalVar.ServerAddress & GlobalVar.Con";
_urlname = parent.mostCurrent._globalvar._serveraddress /*String*/ +parent.mostCurrent._globalvar._controllerprefix /*String*/ +"getRatesDetails";
 //BA.debugLineNum = 601;BA.debugLine="j.Download2(URLName, Array As String(\"BranchID\",";
_j._download2(_urlname,new String[]{"BranchID",BA.NumberToString(_ibranchid)});
 //BA.debugLineNum = 602;BA.debugLine="ProgressDialogShow2($\"Downloading Rates Data...\"$";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence(("Downloading Rates Data...")),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 604;BA.debugLine="Wait For (j) JobDone(j As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_j));
this.state = 11;
return;
case 11:
//C
this.state = 1;
_j = (anywheresoftware.b4a.samples.httputils2.httpjob) result[0];
;
 //BA.debugLineNum = 605;BA.debugLine="If j.Success Then";
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
 //BA.debugLineNum = 606;BA.debugLine="RetVal = j.GetString";
_retval = _j._getstring();
 //BA.debugLineNum = 607;BA.debugLine="Log(URLName)";
anywheresoftware.b4a.keywords.Common.LogImpl("12359309",_urlname,0);
 //BA.debugLineNum = 608;BA.debugLine="Log(RetVal)";
anywheresoftware.b4a.keywords.Common.LogImpl("12359310",_retval,0);
 //BA.debugLineNum = 609;BA.debugLine="If RetVal = \"[]\" Then";
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
 //BA.debugLineNum = 610;BA.debugLine="snack.Initialize(\"\", Activity, $\"No Branch Rate";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(parent.mostCurrent._activity.getObject()),("No Branch Rate Details available to Download!"),parent.mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 611;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 612;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 613;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 614;BA.debugLine="j.Release";
_j._release();
 //BA.debugLineNum = 615;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 7:
//C
this.state = 10;
;
 //BA.debugLineNum = 617;BA.debugLine="SaveRateDetails(RetVal)";
_saveratedetails(_retval);
 if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 619;BA.debugLine="snack.Initialize(\"RetryDownloadRateDetails\", Act";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"RetryDownloadRateDetails",(android.view.View)(parent.mostCurrent._activity.getObject()),("Unable to Download Data due to ")+_j._errormessage,parent.mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 620;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 621;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 622;BA.debugLine="snack.SetAction(\"Retry\")";
parent.mostCurrent._snack.SetAction("Retry");
 //BA.debugLineNum = 623;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 624;BA.debugLine="Log(j.ErrorMessage)";
anywheresoftware.b4a.keywords.Common.LogImpl("12359326",_j._errormessage,0);
 //BA.debugLineNum = 625;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 if (true) break;

case 10:
//C
this.state = -1;
;
 //BA.debugLineNum = 627;BA.debugLine="j.Release";
_j._release();
 //BA.debugLineNum = 628;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 629;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _downloadrateheader(int _ibranchid) throws Exception{
ResumableSub_DownloadRateHeader rsub = new ResumableSub_DownloadRateHeader(null,_ibranchid);
rsub.resume(processBA, null);
}
public static class ResumableSub_DownloadRateHeader extends BA.ResumableSub {
public ResumableSub_DownloadRateHeader(com.bwsi.MeterReader.datasyncing parent,int _ibranchid) {
this.parent = parent;
this._ibranchid = _ibranchid;
}
com.bwsi.MeterReader.datasyncing parent;
int _ibranchid;
String _urlname = "";
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
 //BA.debugLineNum = 560;BA.debugLine="Dim URLName As String";
_urlname = "";
 //BA.debugLineNum = 561;BA.debugLine="Dim RetVal As String";
_retval = "";
 //BA.debugLineNum = 562;BA.debugLine="Dim j As HttpJob";
_j = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 563;BA.debugLine="j.Initialize(\"\",Me)";
_j._initialize(processBA,"",datasyncing.getObject());
 //BA.debugLineNum = 564;BA.debugLine="URLName = GlobalVar.ServerAddress & GlobalVar.Con";
_urlname = parent.mostCurrent._globalvar._serveraddress /*String*/ +parent.mostCurrent._globalvar._controllerprefix /*String*/ +"getRatesHeader";
 //BA.debugLineNum = 565;BA.debugLine="j.Download2(URLName, Array As String(\"BranchID\",";
_j._download2(_urlname,new String[]{"BranchID",BA.NumberToString(_ibranchid)});
 //BA.debugLineNum = 566;BA.debugLine="ProgressDialogShow2($\"Downloading Rates Data...\"$";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence(("Downloading Rates Data...")),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 567;BA.debugLine="Wait For (j) JobDone(j As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_j));
this.state = 11;
return;
case 11:
//C
this.state = 1;
_j = (anywheresoftware.b4a.samples.httputils2.httpjob) result[0];
;
 //BA.debugLineNum = 568;BA.debugLine="If j.Success Then";
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
 //BA.debugLineNum = 569;BA.debugLine="RetVal = j.GetString";
_retval = _j._getstring();
 //BA.debugLineNum = 570;BA.debugLine="Log(URLName)";
anywheresoftware.b4a.keywords.Common.LogImpl("12293771",_urlname,0);
 //BA.debugLineNum = 571;BA.debugLine="Log(RetVal)";
anywheresoftware.b4a.keywords.Common.LogImpl("12293772",_retval,0);
 //BA.debugLineNum = 572;BA.debugLine="If RetVal = \"[]\" Then";
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
 //BA.debugLineNum = 573;BA.debugLine="snack.Initialize(\"\", Activity, $\"No Branch Rate";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(parent.mostCurrent._activity.getObject()),("No Branch Rate Master available to Download!"),parent.mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 574;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 575;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 576;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 577;BA.debugLine="j.Release";
_j._release();
 //BA.debugLineNum = 578;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 7:
//C
this.state = 10;
;
 //BA.debugLineNum = 580;BA.debugLine="SaveRateHeader(RetVal)";
_saverateheader(_retval);
 if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 582;BA.debugLine="snack.Initialize(\"RetryDownloadRateHeader\", Acti";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"RetryDownloadRateHeader",(android.view.View)(parent.mostCurrent._activity.getObject()),("Unable to Download Data due to ")+_j._errormessage,parent.mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 583;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 584;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 585;BA.debugLine="snack.SetAction(\"Retry\")";
parent.mostCurrent._snack.SetAction("Retry");
 //BA.debugLineNum = 586;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 587;BA.debugLine="Log(j.ErrorMessage)";
anywheresoftware.b4a.keywords.Common.LogImpl("12293788",_j._errormessage,0);
 //BA.debugLineNum = 588;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 if (true) break;

case 10:
//C
this.state = -1;
;
 //BA.debugLineNum = 590;BA.debugLine="j.Release";
_j._release();
 //BA.debugLineNum = 591;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 592;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _downloadseptagerates(int _ibranchid,String _sreadingdate) throws Exception{
ResumableSub_DownloadSeptageRates rsub = new ResumableSub_DownloadSeptageRates(null,_ibranchid,_sreadingdate);
rsub.resume(processBA, null);
}
public static class ResumableSub_DownloadSeptageRates extends BA.ResumableSub {
public ResumableSub_DownloadSeptageRates(com.bwsi.MeterReader.datasyncing parent,int _ibranchid,String _sreadingdate) {
this.parent = parent;
this._ibranchid = _ibranchid;
this._sreadingdate = _sreadingdate;
}
com.bwsi.MeterReader.datasyncing parent;
int _ibranchid;
String _sreadingdate;
String _urlname = "";
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
 //BA.debugLineNum = 670;BA.debugLine="Dim URLName As String";
_urlname = "";
 //BA.debugLineNum = 671;BA.debugLine="Dim RetVal As String";
_retval = "";
 //BA.debugLineNum = 673;BA.debugLine="Dim j As HttpJob";
_j = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 674;BA.debugLine="j.Initialize(\"\",Me)";
_j._initialize(processBA,"",datasyncing.getObject());
 //BA.debugLineNum = 675;BA.debugLine="URLName = GlobalVar.ServerAddress & GlobalVar.Con";
_urlname = parent.mostCurrent._globalvar._serveraddress /*String*/ +parent.mostCurrent._globalvar._controllerprefix /*String*/ +"getSeptageRates";
 //BA.debugLineNum = 676;BA.debugLine="j.Download2(URLName, Array As String(\"BranchID\",i";
_j._download2(_urlname,new String[]{"BranchID",BA.NumberToString(_ibranchid),"RdgDate",_sreadingdate});
 //BA.debugLineNum = 677;BA.debugLine="Log(URLName & \"?\" & \"BranchID=\" & iBranchID & \"Rd";
anywheresoftware.b4a.keywords.Common.LogImpl("12490376",_urlname+"?"+"BranchID="+BA.NumberToString(_ibranchid)+"RdgDate="+_sreadingdate,0);
 //BA.debugLineNum = 679;BA.debugLine="ProgressDialogShow2($\"Downloading Data...\"$, Fals";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence(("Downloading Data...")),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 680;BA.debugLine="Wait For (j) JobDone(j As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_j));
this.state = 11;
return;
case 11:
//C
this.state = 1;
_j = (anywheresoftware.b4a.samples.httputils2.httpjob) result[0];
;
 //BA.debugLineNum = 681;BA.debugLine="If j.Success Then";
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
 //BA.debugLineNum = 682;BA.debugLine="RetVal = j.GetString";
_retval = _j._getstring();
 //BA.debugLineNum = 683;BA.debugLine="Log(URLName)";
anywheresoftware.b4a.keywords.Common.LogImpl("12490382",_urlname,0);
 //BA.debugLineNum = 684;BA.debugLine="Log(RetVal)";
anywheresoftware.b4a.keywords.Common.LogImpl("12490383",_retval,0);
 //BA.debugLineNum = 685;BA.debugLine="If RetVal = \"[]\" Then";
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
 //BA.debugLineNum = 686;BA.debugLine="snack.Initialize(\"\", Activity, $\"No Septage Rat";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(parent.mostCurrent._activity.getObject()),("No Septage Rates found on the specified branch!"),parent.mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 687;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 688;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 689;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 690;BA.debugLine="j.Release";
_j._release();
 //BA.debugLineNum = 691;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 7:
//C
this.state = 10;
;
 //BA.debugLineNum = 693;BA.debugLine="SaveSSMRates(RetVal)";
_savessmrates(_retval);
 if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 695;BA.debugLine="snack.Initialize(\"RetryDownloadSeptageRates\", Ac";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"RetryDownloadSeptageRates",(android.view.View)(parent.mostCurrent._activity.getObject()),("Unable to Download Septage Rates due to ")+_j._errormessage,parent.mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 696;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 697;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 698;BA.debugLine="snack.SetAction(\"Retry\")";
parent.mostCurrent._snack.SetAction("Retry");
 //BA.debugLineNum = 699;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 700;BA.debugLine="Log(j.ErrorMessage)";
anywheresoftware.b4a.keywords.Common.LogImpl("12490399",_j._errormessage,0);
 //BA.debugLineNum = 701;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 if (true) break;

case 10:
//C
this.state = -1;
;
 //BA.debugLineNum = 703;BA.debugLine="j.Release";
_j._release();
 //BA.debugLineNum = 704;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 705;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _downloadviewbinder_onbindview(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,int _viewtype) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alertdialog = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
anywheresoftware.b4a.objects.CSBuilder _cs = null;
 //BA.debugLineNum = 2033;BA.debugLine="Private Sub DownloadViewBinder_OnBindView (View As";
 //BA.debugLineNum = 2034;BA.debugLine="Dim AlertDialog As AX_CustomAlertDialog";
_alertdialog = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 2035;BA.debugLine="AlertDialog.Initialize";
_alertdialog.Initialize();
 //BA.debugLineNum = 2037;BA.debugLine="If ViewType = AlertDialog.VIEW_TITLE Then ' Title";
if (_viewtype==_alertdialog.VIEW_TITLE) { 
 //BA.debugLineNum = 2038;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 2039;BA.debugLine="Dim CS As CSBuilder";
_cs = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 2041;BA.debugLine="CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Ty";
_cs.Initialize().Typeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD).Typeface(anywheresoftware.b4a.keywords.Common.Typeface.getMATERIALICONS()).Size((int) (26)).Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xe2c4)))+"  "));
 //BA.debugLineNum = 2042;BA.debugLine="CS.Typeface(GlobalVar.Font).Size(22).Append(lbl.";
_cs.Typeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).Size((int) (22)).Append(BA.ObjectToCharSequence(_lbl.getText())).Pop();
 //BA.debugLineNum = 2044;BA.debugLine="lbl.Text = CS.PopAll";
_lbl.setText(BA.ObjectToCharSequence(_cs.PopAll().getObject()));
 };
 //BA.debugLineNum = 2046;BA.debugLine="If ViewType = AlertDialog.VIEW_MESSAGE Then";
if (_viewtype==_alertdialog.VIEW_MESSAGE) { 
 //BA.debugLineNum = 2047;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 2048;BA.debugLine="lbl.TextSize = 16";
_lbl.setTextSize((float) (16));
 //BA.debugLineNum = 2049;BA.debugLine="lbl.TextColor = Colors.Gray";
_lbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 };
 //BA.debugLineNum = 2051;BA.debugLine="End Sub";
return "";
}
public static String  _getreadername(int _ireaderid) throws Exception{
String _sretval = "";
 //BA.debugLineNum = 1416;BA.debugLine="Private Sub GetReaderName(iReaderID As Int) As Str";
 //BA.debugLineNum = 1417;BA.debugLine="Dim sRetVal As String";
_sretval = "";
 //BA.debugLineNum = 1418;BA.debugLine="Try";
try { //BA.debugLineNum = 1419;BA.debugLine="sRetVal = Starter.DBCon.ExecQuerySingleResult2(\"";
_sretval = mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult2("SELECT UserName FROM tblUsers WHERE UserID = ?",new String[]{BA.NumberToString(_ireaderid)});
 } 
       catch (Exception e5) {
			processBA.setLastException(e5); //BA.debugLineNum = 1421;BA.debugLine="sRetVal = \"\"";
_sretval = "";
 //BA.debugLineNum = 1422;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("14784134",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 1424;BA.debugLine="Return sRetVal";
if (true) return _sretval;
 //BA.debugLineNum = 1425;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 17;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 20;BA.debugLine="Dim ActionBarButton As ACActionBar";
mostCurrent._actionbarbutton = new de.amberhome.objects.appcompat.ACActionBar();
 //BA.debugLineNum = 21;BA.debugLine="Private ToolBar As ACToolBarDark";
mostCurrent._toolbar = new de.amberhome.objects.appcompat.ACToolbarDarkWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private xmlIcon As XmlLayoutBuilder";
mostCurrent._xmlicon = new anywheresoftware.b4a.object.XmlLayoutBuilder();
 //BA.debugLineNum = 25;BA.debugLine="Private MatDialogBuilder As MaterialDialogBuilder";
mostCurrent._matdialogbuilder = new de.amberhome.materialdialogs.MaterialDialogBuilderWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private pnlButtons As Panel";
mostCurrent._pnlbuttons = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private pnlDownload As Panel";
mostCurrent._pnldownload = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private strRdgDate As String";
mostCurrent._strrdgdate = "";
 //BA.debugLineNum = 32;BA.debugLine="Dim bmpDownload As BitmapDrawable";
mostCurrent._bmpdownload = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 33;BA.debugLine="Dim bmpUpload As BitmapDrawable";
mostCurrent._bmpupload = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 35;BA.debugLine="Private rsReader As Cursor";
mostCurrent._rsreader = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private rsHeader As Cursor";
mostCurrent._rsheader = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private rsDetails As Cursor";
mostCurrent._rsdetails = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private ReaderID As Int";
_readerid = 0;
 //BA.debugLineNum = 39;BA.debugLine="Private intUploadedBookID As Int";
_intuploadedbookid = 0;
 //BA.debugLineNum = 40;BA.debugLine="Private intID As Int";
_intid = 0;
 //BA.debugLineNum = 41;BA.debugLine="Private intUploadAcctID As Int";
_intuploadacctid = 0;
 //BA.debugLineNum = 43;BA.debugLine="Private cboReader As ACSpinner";
mostCurrent._cboreader = new de.amberhome.objects.appcompat.ACSpinnerWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Private intDownload = 0 As Int";
_intdownload = (int) (0);
 //BA.debugLineNum = 46;BA.debugLine="Private snack As DSSnackbar";
mostCurrent._snack = new de.amberhome.objects.SnackbarWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Dim DLctr =0 As Int";
_dlctr = (int) (0);
 //BA.debugLineNum = 48;BA.debugLine="Private btnCancelUpload As ACButton";
mostCurrent._btncancelupload = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Private btnOkUpload As ACButton";
mostCurrent._btnokupload = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Private cboReaderUpload As ACSpinner";
mostCurrent._cboreaderupload = new de.amberhome.objects.appcompat.ACSpinnerWrapper();
 //BA.debugLineNum = 51;BA.debugLine="Private pnlUpload As Panel";
mostCurrent._pnlupload = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 52;BA.debugLine="Private UploadCtr As Int";
_uploadctr = 0;
 //BA.debugLineNum = 54;BA.debugLine="Dim intReaderID As Int";
_intreaderid = 0;
 //BA.debugLineNum = 55;BA.debugLine="Private btnCancel As ACButton";
mostCurrent._btncancel = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 56;BA.debugLine="Private btnOK As ACButton";
mostCurrent._btnok = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 58;BA.debugLine="Private sURL As String";
mostCurrent._surl = "";
 //BA.debugLineNum = 60;BA.debugLine="Private BackUpPath As String";
mostCurrent._backuppath = "";
 //BA.debugLineNum = 61;BA.debugLine="Private BackUpName As String";
mostCurrent._backupname = "";
 //BA.debugLineNum = 63;BA.debugLine="Dim myStyle As ColorDrawable";
mostCurrent._mystyle = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 64;BA.debugLine="Private cdButtonCancel, cdButtonOK As ColorDrawab";
mostCurrent._cdbuttoncancel = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
mostCurrent._cdbuttonok = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 66;BA.debugLine="Private pnlDownloadBox As Panel";
mostCurrent._pnldownloadbox = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 67;BA.debugLine="Private pnlUploadBox As Panel";
mostCurrent._pnluploadbox = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 68;BA.debugLine="Private btnDownload As ACButton";
mostCurrent._btndownload = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 69;BA.debugLine="Private btnUpload As ACButton";
mostCurrent._btnupload = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 70;BA.debugLine="Private cdDownUpStyle As ColorDrawable";
mostCurrent._cddownupstyle = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 71;BA.debugLine="Private cdReader As ColorDrawable";
mostCurrent._cdreader = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 73;BA.debugLine="Private MyDateDialogs As DateDialogs";
mostCurrent._mydatedialogs = new com.bwsi.MeterReader.datedialogs();
 //BA.debugLineNum = 75;BA.debugLine="Private lblRdgDateIcon As Label";
mostCurrent._lblrdgdateicon = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 76;BA.debugLine="Private lblReadingDate As Label";
mostCurrent._lblreadingdate = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 77;BA.debugLine="Private lDate As Long";
_ldate = 0L;
 //BA.debugLineNum = 78;BA.debugLine="Private sRdgDate As String";
mostCurrent._srdgdate = "";
 //BA.debugLineNum = 80;BA.debugLine="Private csNote As CSBuilder";
mostCurrent._csnote = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 81;BA.debugLine="Private lblULNote As Label";
mostCurrent._lblulnote = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 82;BA.debugLine="Private SoundID As Int";
_soundid = 0;
 //BA.debugLineNum = 83;BA.debugLine="Private vibration As B4Avibrate";
mostCurrent._vibration = new com.johan.Vibrate.Vibrate();
 //BA.debugLineNum = 86;BA.debugLine="End Sub";
return "";
}
public static boolean  _isthereanydata(String _stablename) throws Exception{
boolean _blnreturn = false;
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsreccount = null;
 //BA.debugLineNum = 1192;BA.debugLine="Sub IsThereAnyData(sTableName As String) As Boolea";
 //BA.debugLineNum = 1193;BA.debugLine="Dim blnReturn As Boolean";
_blnreturn = false;
 //BA.debugLineNum = 1194;BA.debugLine="Dim rsRecCount As Cursor";
_rsreccount = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 1195;BA.debugLine="Try";
try { //BA.debugLineNum = 1196;BA.debugLine="Starter.strCriteria = \"SELECT * FROM \" & sTableN";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM "+_stablename;
 //BA.debugLineNum = 1197;BA.debugLine="rsRecCount = Starter.DBCon.ExecQuery(Starter.str";
_rsreccount = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 1199;BA.debugLine="If rsRecCount.RowCount>0 Then";
if (_rsreccount.getRowCount()>0) { 
 //BA.debugLineNum = 1200;BA.debugLine="blnReturn = True";
_blnreturn = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 1202;BA.debugLine="blnReturn = False";
_blnreturn = anywheresoftware.b4a.keywords.Common.False;
 };
 } 
       catch (Exception e12) {
			processBA.setLastException(e12); //BA.debugLineNum = 1205;BA.debugLine="ToastMessageShow(\"Unable to check \" & sTableName";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Unable to check "+_stablename+" due to "+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage()),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1206;BA.debugLine="blnReturn = False";
_blnreturn = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 1208;BA.debugLine="Return blnReturn";
if (true) return _blnreturn;
 //BA.debugLineNum = 1209;BA.debugLine="End Sub";
return false;
}
public static String  _lblrdgdateicon_click() throws Exception{
String _sdate = "";
long _lrdgdate = 0L;
 //BA.debugLineNum = 1965;BA.debugLine="Sub lblRdgDateIcon_Click";
 //BA.debugLineNum = 1966;BA.debugLine="Dim sDate As String";
_sdate = "";
 //BA.debugLineNum = 1967;BA.debugLine="Dim lRdgDate As Long";
_lrdgdate = 0L;
 //BA.debugLineNum = 1969;BA.debugLine="lDate = DateTime.DateParse(DateTime.Date(DateTime";
_ldate = anywheresoftware.b4a.keywords.Common.DateTime.DateParse(anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow()));
 //BA.debugLineNum = 1970;BA.debugLine="MyDateDialogs.Initialize(Activity, lDate)";
mostCurrent._mydatedialogs._initialize /*String*/ (mostCurrent.activityBA,mostCurrent._activity,_ldate);
 //BA.debugLineNum = 1971;BA.debugLine="DateTime.DateFormat = \"MM/dd/yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("MM/dd/yyyy");
 //BA.debugLineNum = 1972;BA.debugLine="lRdgDate  = MyDateDialogs.Show($\"Select New Prese";
_lrdgdate = (long) (mostCurrent._mydatedialogs._show /*int*/ (("Select New Present Reading Date")));
 //BA.debugLineNum = 1974;BA.debugLine="If lRdgDate  = DialogResponse.POSITIVE Then";
if (_lrdgdate==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 1975;BA.debugLine="sDate = DateTime.GetMonth(MyDateDialogs.DateSele";
_sdate = BA.NumberToString(anywheresoftware.b4a.keywords.Common.DateTime.GetMonth(mostCurrent._mydatedialogs._dateselected /*long*/ ))+"/"+BA.NumberToString(anywheresoftware.b4a.keywords.Common.DateTime.GetDayOfMonth(mostCurrent._mydatedialogs._dateselected /*long*/ ))+"/"+BA.NumberToString(anywheresoftware.b4a.keywords.Common.DateTime.GetYear(mostCurrent._mydatedialogs._dateselected /*long*/ ));
 //BA.debugLineNum = 1976;BA.debugLine="lDate = DateTime.DateParse(sDate)";
_ldate = anywheresoftware.b4a.keywords.Common.DateTime.DateParse(_sdate);
 //BA.debugLineNum = 1977;BA.debugLine="sRdgDate = DateTime.Date(lDate)";
mostCurrent._srdgdate = anywheresoftware.b4a.keywords.Common.DateTime.Date(_ldate);
 }else {
 //BA.debugLineNum = 1979;BA.debugLine="lDate = DateTime.DateParse(DateTime.Date(DateTim";
_ldate = anywheresoftware.b4a.keywords.Common.DateTime.DateParse(anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow()));
 //BA.debugLineNum = 1980;BA.debugLine="sRdgDate = DateTime.Date(lDate)";
mostCurrent._srdgdate = anywheresoftware.b4a.keywords.Common.DateTime.Date(_ldate);
 };
 //BA.debugLineNum = 1982;BA.debugLine="lblReadingDate.Text = sRdgDate";
mostCurrent._lblreadingdate.setText(BA.ObjectToCharSequence(mostCurrent._srdgdate));
 //BA.debugLineNum = 1984;BA.debugLine="End Sub";
return "";
}
public static String  _lblreadingdate_click() throws Exception{
String _sdate = "";
long _lrdgdate = 0L;
 //BA.debugLineNum = 1944;BA.debugLine="Sub lblReadingDate_Click";
 //BA.debugLineNum = 1945;BA.debugLine="Dim sDate As String";
_sdate = "";
 //BA.debugLineNum = 1946;BA.debugLine="Dim lRdgDate As Long";
_lrdgdate = 0L;
 //BA.debugLineNum = 1948;BA.debugLine="lDate = DateTime.DateParse(DateTime.Date(DateTime";
_ldate = anywheresoftware.b4a.keywords.Common.DateTime.DateParse(anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow()));
 //BA.debugLineNum = 1949;BA.debugLine="MyDateDialogs.Initialize(Activity, lDate)";
mostCurrent._mydatedialogs._initialize /*String*/ (mostCurrent.activityBA,mostCurrent._activity,_ldate);
 //BA.debugLineNum = 1950;BA.debugLine="DateTime.DateFormat = \"MM/dd/yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("MM/dd/yyyy");
 //BA.debugLineNum = 1951;BA.debugLine="lRdgDate  = MyDateDialogs.Show($\"Select New Prese";
_lrdgdate = (long) (mostCurrent._mydatedialogs._show /*int*/ (("Select New Present Reading Date")));
 //BA.debugLineNum = 1953;BA.debugLine="If lRdgDate  = DialogResponse.POSITIVE Then";
if (_lrdgdate==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 1954;BA.debugLine="sDate = DateTime.GetMonth(MyDateDialogs.DateSele";
_sdate = BA.NumberToString(anywheresoftware.b4a.keywords.Common.DateTime.GetMonth(mostCurrent._mydatedialogs._dateselected /*long*/ ))+"/"+BA.NumberToString(anywheresoftware.b4a.keywords.Common.DateTime.GetDayOfMonth(mostCurrent._mydatedialogs._dateselected /*long*/ ))+"/"+BA.NumberToString(anywheresoftware.b4a.keywords.Common.DateTime.GetYear(mostCurrent._mydatedialogs._dateselected /*long*/ ));
 //BA.debugLineNum = 1955;BA.debugLine="lDate = DateTime.DateParse(sDate)";
_ldate = anywheresoftware.b4a.keywords.Common.DateTime.DateParse(_sdate);
 //BA.debugLineNum = 1956;BA.debugLine="sRdgDate = DateTime.Date(lDate)";
mostCurrent._srdgdate = anywheresoftware.b4a.keywords.Common.DateTime.Date(_ldate);
 }else {
 //BA.debugLineNum = 1958;BA.debugLine="lDate = DateTime.DateParse(DateTime.Date(DateTim";
_ldate = anywheresoftware.b4a.keywords.Common.DateTime.DateParse(anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow()));
 //BA.debugLineNum = 1959;BA.debugLine="sRdgDate = DateTime.Date(lDate)";
mostCurrent._srdgdate = anywheresoftware.b4a.keywords.Common.DateTime.Date(_ldate);
 };
 //BA.debugLineNum = 1961;BA.debugLine="lblReadingDate.Text = sRdgDate";
mostCurrent._lblreadingdate.setText(BA.ObjectToCharSequence(mostCurrent._srdgdate));
 //BA.debugLineNum = 1963;BA.debugLine="End Sub";
return "";
}
public static String  _loadreader() throws Exception{
int _i = 0;
 //BA.debugLineNum = 225;BA.debugLine="Private Sub LoadReader";
 //BA.debugLineNum = 227;BA.debugLine="cboReader.Clear";
mostCurrent._cboreader.Clear();
 //BA.debugLineNum = 228;BA.debugLine="Try";
try { //BA.debugLineNum = 229;BA.debugLine="Starter.strCriteria = \"SELECT * FROM tblReaders\"";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblReaders";
 //BA.debugLineNum = 230;BA.debugLine="rsReader = Starter.DBCon.ExecQuery(Starter.strCr";
mostCurrent._rsreader = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 231;BA.debugLine="If rsReader.RowCount > 0 Then";
if (mostCurrent._rsreader.getRowCount()>0) { 
 //BA.debugLineNum = 232;BA.debugLine="For i = 0 To rsReader.RowCount - 1";
{
final int step6 = 1;
final int limit6 = (int) (mostCurrent._rsreader.getRowCount()-1);
_i = (int) (0) ;
for (;_i <= limit6 ;_i = _i + step6 ) {
 //BA.debugLineNum = 233;BA.debugLine="rsReader.Position = i";
mostCurrent._rsreader.setPosition(_i);
 //BA.debugLineNum = 234;BA.debugLine="cboReader.Add(TitleCase(rsReader.GetString(\"Re";
mostCurrent._cboreader.Add(BA.ObjectToCharSequence(_titlecase(mostCurrent._rsreader.GetString("ReaderName"))));
 }
};
 };
 } 
       catch (Exception e12) {
			processBA.setLastException(e12); //BA.debugLineNum = 238;BA.debugLine="ToastMessageShow(\"Unable to Load Meter Readers d";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Unable to Load Meter Readers due to "+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 239;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("11245198",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 241;BA.debugLine="End Sub";
return "";
}
public static String  _loaduploader() throws Exception{
int _i = 0;
 //BA.debugLineNum = 1855;BA.debugLine="Private Sub LoadUploader";
 //BA.debugLineNum = 1856;BA.debugLine="cboReaderUpload.Clear";
mostCurrent._cboreaderupload.Clear();
 //BA.debugLineNum = 1857;BA.debugLine="Try";
try { //BA.debugLineNum = 1858;BA.debugLine="Starter.strCriteria = \"SELECT DISTINCT tblReader";
mostCurrent._starter._strcriteria /*String*/  = "SELECT DISTINCT tblReaders.ReaderName "+"FROM tblBookAssignments "+"INNER JOIN tblReaders ON tblBookAssignments.UserID = tblReaders.ReaderID "+"WHERE tblBookAssignments.WasUploaded = 0";
 //BA.debugLineNum = 1862;BA.debugLine="rsReader = Starter.DBCon.ExecQuery(Starter.strCr";
mostCurrent._rsreader = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 1863;BA.debugLine="If rsReader.RowCount > 0 Then";
if (mostCurrent._rsreader.getRowCount()>0) { 
 //BA.debugLineNum = 1864;BA.debugLine="For i = 0 To rsReader.RowCount - 1";
{
final int step6 = 1;
final int limit6 = (int) (mostCurrent._rsreader.getRowCount()-1);
_i = (int) (0) ;
for (;_i <= limit6 ;_i = _i + step6 ) {
 //BA.debugLineNum = 1865;BA.debugLine="rsReader.Position = i";
mostCurrent._rsreader.setPosition(_i);
 //BA.debugLineNum = 1866;BA.debugLine="cboReaderUpload.Add(TitleCase(rsReader.GetStri";
mostCurrent._cboreaderupload.Add(BA.ObjectToCharSequence(_titlecase(mostCurrent._rsreader.GetString("ReaderName"))));
 }
};
 };
 } 
       catch (Exception e12) {
			processBA.setLastException(e12); //BA.debugLineNum = 1870;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("15636111",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 1872;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.drawable.ColorDrawable  _mycd() throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _mcd = null;
 //BA.debugLineNum = 2225;BA.debugLine="Private Sub myCD As ColorDrawable";
 //BA.debugLineNum = 2226;BA.debugLine="Dim mCD As ColorDrawable";
_mcd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 2227;BA.debugLine="mCD.Initialize(Colors.RGB(240,240,240),0)";
_mcd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (240),(int) (240),(int) (240)),(int) (0));
 //BA.debugLineNum = 2228;BA.debugLine="Return mCD";
if (true) return _mcd;
 //BA.debugLineNum = 2229;BA.debugLine="End Sub";
return null;
}
public static String  _ondownload_onnegativeclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alertdialog = null;
 //BA.debugLineNum = 2024;BA.debugLine="Private Sub OnDownload_OnNegativeClicked (View As";
 //BA.debugLineNum = 2025;BA.debugLine="Dim AlertDialog As AX_CustomAlertDialog";
_alertdialog = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 2027;BA.debugLine="vibration.vibrateCancel";
mostCurrent._vibration.vibrateCancel(processBA);
 //BA.debugLineNum = 2028;BA.debugLine="soundsAlarmChannel.Stop(SoundID)";
_soundsalarmchannel.Stop(_soundid);
 //BA.debugLineNum = 2029;BA.debugLine="AlertDialog.Initialize.Dismiss(Dialog)";
_alertdialog.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 2031;BA.debugLine="End Sub";
return "";
}
public static String  _ondownload_onpositiveclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alertdialog = null;
 //BA.debugLineNum = 2015;BA.debugLine="Private Sub OnDownload_OnPositiveClicked (View As";
 //BA.debugLineNum = 2016;BA.debugLine="Dim AlertDialog As AX_CustomAlertDialog";
_alertdialog = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 2018;BA.debugLine="vibration.vibrateCancel";
mostCurrent._vibration.vibrateCancel(processBA);
 //BA.debugLineNum = 2019;BA.debugLine="soundsAlarmChannel.Stop(SoundID)";
_soundsalarmchannel.Stop(_soundid);
 //BA.debugLineNum = 2020;BA.debugLine="ShowDLPasswordDialog";
_showdlpassworddialog();
 //BA.debugLineNum = 2021;BA.debugLine="AlertDialog.Initialize.Dismiss(Dialog)";
_alertdialog.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 2022;BA.debugLine="End Sub";
return "";
}
public static String  _ondownloadcomplete_onnegativeclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alertdialog = null;
 //BA.debugLineNum = 2155;BA.debugLine="Private Sub OnDownloadComplete_OnNegativeClicked (";
 //BA.debugLineNum = 2156;BA.debugLine="Dim AlertDialog As AX_CustomAlertDialog";
_alertdialog = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 2157;BA.debugLine="vibration.vibrateCancel";
mostCurrent._vibration.vibrateCancel(processBA);
 //BA.debugLineNum = 2158;BA.debugLine="soundsAlarmChannel.Stop(SoundID)";
_soundsalarmchannel.Stop(_soundid);
 //BA.debugLineNum = 2159;BA.debugLine="AlertDialog.Initialize.Dismiss(Dialog)";
_alertdialog.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 2160;BA.debugLine="End Sub";
return "";
}
public static String  _ondownloadcomplete_onpositiveclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alertdialog = null;
 //BA.debugLineNum = 2146;BA.debugLine="Private Sub OnDownloadComplete_OnPositiveClicked (";
 //BA.debugLineNum = 2147;BA.debugLine="Dim AlertDialog As AX_CustomAlertDialog";
_alertdialog = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 2149;BA.debugLine="vibration.vibrateCancel";
mostCurrent._vibration.vibrateCancel(processBA);
 //BA.debugLineNum = 2150;BA.debugLine="soundsAlarmChannel.Stop(SoundID)";
_soundsalarmchannel.Stop(_soundid);
 //BA.debugLineNum = 2151;BA.debugLine="AlertDialog.Initialize.Dismiss(Dialog)";
_alertdialog.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 2152;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 2153;BA.debugLine="End Sub";
return "";
}
public static String  _onupload_onnegativeclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alertdialog = null;
 //BA.debugLineNum = 2088;BA.debugLine="Private Sub OnUpload_OnNegativeClicked (View As Vi";
 //BA.debugLineNum = 2089;BA.debugLine="Dim AlertDialog As AX_CustomAlertDialog";
_alertdialog = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 2091;BA.debugLine="vibration.vibrateCancel";
mostCurrent._vibration.vibrateCancel(processBA);
 //BA.debugLineNum = 2092;BA.debugLine="soundsAlarmChannel.Stop(SoundID)";
_soundsalarmchannel.Stop(_soundid);
 //BA.debugLineNum = 2093;BA.debugLine="AlertDialog.Initialize.Dismiss(Dialog)";
_alertdialog.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 2094;BA.debugLine="End Sub";
return "";
}
public static String  _onupload_onpositiveclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alertdialog = null;
 //BA.debugLineNum = 2079;BA.debugLine="Private Sub OnUpload_OnPositiveClicked (View As Vi";
 //BA.debugLineNum = 2080;BA.debugLine="Dim AlertDialog As AX_CustomAlertDialog";
_alertdialog = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 2082;BA.debugLine="vibration.vibrateCancel";
mostCurrent._vibration.vibrateCancel(processBA);
 //BA.debugLineNum = 2083;BA.debugLine="soundsAlarmChannel.Stop(SoundID)";
_soundsalarmchannel.Stop(_soundid);
 //BA.debugLineNum = 2084;BA.debugLine="ShowUPPasswordDialog";
_showuppassworddialog();
 //BA.debugLineNum = 2085;BA.debugLine="AlertDialog.Initialize.Dismiss(Dialog)";
_alertdialog.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 2086;BA.debugLine="End Sub";
return "";
}
public static String  _onuploadcomplete_onnegativeclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alertdialog = null;
 //BA.debugLineNum = 2200;BA.debugLine="Private Sub OnUploadComplete_OnNegativeClicked (Vi";
 //BA.debugLineNum = 2201;BA.debugLine="Dim AlertDialog As AX_CustomAlertDialog";
_alertdialog = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 2202;BA.debugLine="AlertDialog.Initialize.Dismiss(Dialog)";
_alertdialog.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 2203;BA.debugLine="End Sub";
return "";
}
public static String  _onuploadcomplete_onpositiveclicked(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,Object _dialog) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alertdialog = null;
 //BA.debugLineNum = 2191;BA.debugLine="Private Sub OnUploadComplete_OnPositiveClicked (Vi";
 //BA.debugLineNum = 2192;BA.debugLine="Dim AlertDialog As AX_CustomAlertDialog";
_alertdialog = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 2194;BA.debugLine="vibration.vibrateCancel";
mostCurrent._vibration.vibrateCancel(processBA);
 //BA.debugLineNum = 2195;BA.debugLine="soundsAlarmChannel.Stop(SoundID)";
_soundsalarmchannel.Stop(_soundid);
 //BA.debugLineNum = 2196;BA.debugLine="AlertDialog.Initialize.Dismiss(Dialog)";
_alertdialog.Initialize().Dismiss((android.app.Dialog)(_dialog));
 //BA.debugLineNum = 2197;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 2198;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 7;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="Private strReadingData As String";
_strreadingdata = "";
 //BA.debugLineNum = 9;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 10;BA.debugLine="Dim AP As ManageExternalStorage";
_ap = new com.bwsi.MeterReader.manageexternalstorage();
 //BA.debugLineNum = 11;BA.debugLine="Dim device As Phone";
_device = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 12;BA.debugLine="Private ion As Object";
_ion = new Object();
 //BA.debugLineNum = 13;BA.debugLine="Dim soundsAlarmChannel As SoundPool";
_soundsalarmchannel = new anywheresoftware.b4a.audio.SoundPoolWrapper();
 //BA.debugLineNum = 15;BA.debugLine="End Sub";
return "";
}
public static String  _resetdata(String _stablename) throws Exception{
 //BA.debugLineNum = 1211;BA.debugLine="Sub ResetData(sTableName As String)";
 //BA.debugLineNum = 1212;BA.debugLine="Try";
try { //BA.debugLineNum = 1213;BA.debugLine="Starter.strCriteria = \"DELETE FROM SQLITE_SEQUEN";
mostCurrent._starter._strcriteria /*String*/  = "DELETE FROM SQLITE_SEQUENCE WHERE name='"+_stablename+"'";
 //BA.debugLineNum = 1214;BA.debugLine="Starter.DBCon.ExecNonQuery(Starter.strCriteria)";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery(mostCurrent._starter._strcriteria /*String*/ );
 } 
       catch (Exception e5) {
			processBA.setLastException(e5); //BA.debugLineNum = 1216;BA.debugLine="ToastMessageShow(\"Unable to reset table due to \"";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Unable to reset table due to "+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage()+"."),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1218;BA.debugLine="End Sub";
return "";
}
public static void  _retdlpassword_buttonpressed(de.amberhome.materialdialogs.MaterialDialogWrapper _mdialog,String _saction) throws Exception{
ResumableSub_RetDLPassword_ButtonPressed rsub = new ResumableSub_RetDLPassword_ButtonPressed(null,_mdialog,_saction);
rsub.resume(processBA, null);
}
public static class ResumableSub_RetDLPassword_ButtonPressed extends BA.ResumableSub {
public ResumableSub_RetDLPassword_ButtonPressed(com.bwsi.MeterReader.datasyncing parent,de.amberhome.materialdialogs.MaterialDialogWrapper _mdialog,String _saction) {
this.parent = parent;
this._mdialog = _mdialog;
this._saction = _saction;
}
com.bwsi.MeterReader.datasyncing parent;
de.amberhome.materialdialogs.MaterialDialogWrapper _mdialog;
String _saction;
long _ldatetime = 0L;
String _sdatetime = "";
String _permission = "";
boolean _result = false;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 361;BA.debugLine="Dim lDateTime As Long";
_ldatetime = 0L;
 //BA.debugLineNum = 362;BA.debugLine="Select Case sAction";
if (true) break;

case 1:
//select
this.state = 22;
switch (BA.switchObjectToInt(_saction,_mdialog.ACTION_POSITIVE,_mdialog.ACTION_NEGATIVE)) {
case 0: {
this.state = 3;
if (true) break;
}
case 1: {
this.state = 21;
if (true) break;
}
}
if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 364;BA.debugLine="If DBaseFunctions.HasBookAssigned(GlobalVar.Bra";
if (true) break;

case 4:
//if
this.state = 19;
if (parent.mostCurrent._dbasefunctions._hasbookassigned /*boolean*/ (mostCurrent.activityBA,parent.mostCurrent._globalvar._branchid /*int*/ ,parent.mostCurrent._globalvar._billmonth /*int*/ ,(int) (parent.mostCurrent._globalvar._billyear /*double*/ ))==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 6;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 366;BA.debugLine="Dim sDateTime As String";
_sdatetime = "";
 //BA.debugLineNum = 367;BA.debugLine="lDateTime = DateTime.Now";
_ldatetime = anywheresoftware.b4a.keywords.Common.DateTime.getNow();
 //BA.debugLineNum = 368;BA.debugLine="DateTime.DateFormat = \"yyyyMMdd HHmmss a\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("yyyyMMdd HHmmss a");
 //BA.debugLineNum = 369;BA.debugLine="sDateTime = DateTime.Date(lDateTime)";
_sdatetime = anywheresoftware.b4a.keywords.Common.DateTime.Date(_ldatetime);
 //BA.debugLineNum = 370;BA.debugLine="If File.Exists(File.Combine(File.DirRootExtern";
if (true) break;

case 7:
//if
this.state = 12;
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.Combine(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"documents"),"MasterDB")==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 9;
;}if (true) break;

case 9:
//C
this.state = 12;
anywheresoftware.b4a.keywords.Common.File.MakeDir(anywheresoftware.b4a.keywords.Common.File.Combine(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"documents"),"MasterDB");
if (true) break;

case 12:
//C
this.state = 13;
;
 //BA.debugLineNum = 371;BA.debugLine="BackUpPath = File.Combine(File.DirRootExternal";
parent.mostCurrent._backuppath = anywheresoftware.b4a.keywords.Common.File.Combine(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"documents/MasterDB");
 //BA.debugLineNum = 372;BA.debugLine="LogColor(BackUpPath, Colors.Yellow)";
anywheresoftware.b4a.keywords.Common.LogImpl("11966092",parent.mostCurrent._backuppath,anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 //BA.debugLineNum = 374;BA.debugLine="BackUpName = GlobalVar.UserName & \"\" & GlobalV";
parent.mostCurrent._backupname = parent.mostCurrent._globalvar._username /*String*/ +""+parent.mostCurrent._globalvar._billperiod /*String*/ +" "+_sdatetime+(" Backup");
 //BA.debugLineNum = 375;BA.debugLine="Starter.rp.GetAllSafeDirsExternal(BackUpPath)";
parent.mostCurrent._starter._rp /*anywheresoftware.b4a.objects.RuntimePermissions*/ .GetAllSafeDirsExternal(parent.mostCurrent._backuppath);
 //BA.debugLineNum = 376;BA.debugLine="Starter.rp.CheckAndRequest(Starter.rp.PERMISSI";
parent.mostCurrent._starter._rp /*anywheresoftware.b4a.objects.RuntimePermissions*/ .CheckAndRequest(processBA,parent.mostCurrent._starter._rp /*anywheresoftware.b4a.objects.RuntimePermissions*/ .PERMISSION_WRITE_EXTERNAL_STORAGE);
 //BA.debugLineNum = 378;BA.debugLine="Wait For Activity_PermissionResult (Permission";
anywheresoftware.b4a.keywords.Common.WaitFor("activity_permissionresult", processBA, this, null);
this.state = 23;
return;
case 23:
//C
this.state = 13;
_permission = (String) result[0];
_result = (Boolean) result[1];
;
 //BA.debugLineNum = 379;BA.debugLine="If Result = False Then";
if (true) break;

case 13:
//if
this.state = 18;
if (_result==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 15;
}else {
this.state = 17;
}if (true) break;

case 15:
//C
this.state = 18;
 //BA.debugLineNum = 380;BA.debugLine="ToastMessageShow ($\"Unable to backup due to p";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to backup due to permission to write was denied")),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 17:
//C
this.state = 18;
 //BA.debugLineNum = 382;BA.debugLine="File.Copy(Starter.DBPath, Starter.DBName, Bac";
anywheresoftware.b4a.keywords.Common.File.Copy(parent.mostCurrent._starter._dbpath /*String*/ ,parent.mostCurrent._starter._dbname /*String*/ ,parent.mostCurrent._backuppath,parent.mostCurrent._backupname);
 //BA.debugLineNum = 383;BA.debugLine="ToastMessageShow ($\"Auto Backup Success!\"$,Tr";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Auto Backup Success!")),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 18:
//C
this.state = 19;
;
 if (true) break;

case 19:
//C
this.state = 22;
;
 //BA.debugLineNum = 394;BA.debugLine="GlobalVar.ServerAddress = DBaseFunctions.GetSer";
parent.mostCurrent._globalvar._serveraddress /*String*/  = parent.mostCurrent._dbasefunctions._getserverip /*String*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 395;BA.debugLine="DeleteBooks";
_deletebooks();
 //BA.debugLineNum = 396;BA.debugLine="DownloadBooks(GlobalVar.BranchID)";
_downloadbooks(parent.mostCurrent._globalvar._branchid /*int*/ );
 if (true) break;

case 21:
//C
this.state = 22;
 //BA.debugLineNum = 398;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 22:
//C
this.state = -1;
;
 //BA.debugLineNum = 400;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _retdlpassword_inputchanged(de.amberhome.materialdialogs.MaterialDialogWrapper _mdialog,String _sinput) throws Exception{
anywheresoftware.b4a.objects.CSBuilder _csbuild = null;
 //BA.debugLineNum = 341;BA.debugLine="Private Sub RetDLPassword_InputChanged (mDialog As";
 //BA.debugLineNum = 342;BA.debugLine="Dim csBuild As CSBuilder";
_csbuild = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 343;BA.debugLine="If sInput.Length <= 0 Then";
if (_sinput.length()<=0) { 
 //BA.debugLineNum = 344;BA.debugLine="csBuild.Initialize.Bold.Color(Colors.Red).Append";
_csbuild.Initialize().Bold().Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(("Enter your Password to Continue..."))).PopAll();
 //BA.debugLineNum = 345;BA.debugLine="mDialog.Content = csBuild";
_mdialog.setContent(BA.ObjectToCharSequence(_csbuild.getObject()));
 //BA.debugLineNum = 346;BA.debugLine="mDialog.EnableActionButton(mDialog.ACTION_POSITI";
_mdialog.EnableActionButton(_mdialog.ACTION_POSITIVE,anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 348;BA.debugLine="If sInput <> GlobalVar.UserPW Then";
if ((_sinput).equals(mostCurrent._globalvar._userpw /*String*/ ) == false) { 
 //BA.debugLineNum = 349;BA.debugLine="mDialog.EnableActionButton(mDialog.ACTION_POSIT";
_mdialog.EnableActionButton(_mdialog.ACTION_POSITIVE,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 350;BA.debugLine="csBuild.Initialize.Bold.Color(Colors.Red).Appen";
_csbuild.Initialize().Bold().Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(("Password is Incorrect!"))).PopAll();
 //BA.debugLineNum = 351;BA.debugLine="mDialog.Content = csBuild";
_mdialog.setContent(BA.ObjectToCharSequence(_csbuild.getObject()));
 }else {
 //BA.debugLineNum = 353;BA.debugLine="mDialog.EnableActionButton(mDialog.ACTION_POSIT";
_mdialog.EnableActionButton(_mdialog.ACTION_POSITIVE,anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 354;BA.debugLine="csBuild.Initialize.Bold.Color(Colors.Black).App";
_csbuild.Initialize().Bold().Color(anywheresoftware.b4a.keywords.Common.Colors.Black).Append(BA.ObjectToCharSequence(("Password is Ok!"))).PopAll();
 //BA.debugLineNum = 355;BA.debugLine="mDialog.Content = csBuild";
_mdialog.setContent(BA.ObjectToCharSequence(_csbuild.getObject()));
 };
 };
 //BA.debugLineNum = 358;BA.debugLine="End Sub";
return "";
}
public static String  _retrydownloadbookassignments_click() throws Exception{
 //BA.debugLineNum = 1075;BA.debugLine="Private Sub RetryDownloadBookAssignments_Click()";
 //BA.debugLineNum = 1076;BA.debugLine="DeleteAssignments";
_deleteassignments();
 //BA.debugLineNum = 1077;BA.debugLine="DownloadBookAssignments(GlobalVar.BranchID, Globa";
_downloadbookassignments(mostCurrent._globalvar._branchid /*int*/ ,(int) (mostCurrent._globalvar._billyear /*double*/ ),mostCurrent._globalvar._billmonth /*int*/ ,_readerid,mostCurrent._strrdgdate);
 //BA.debugLineNum = 1078;BA.debugLine="End Sub";
return "";
}
public static String  _retrydownloadbookpca_click() throws Exception{
 //BA.debugLineNum = 1095;BA.debugLine="Private Sub RetryDownloadBookPCA_Click()";
 //BA.debugLineNum = 1096;BA.debugLine="DeletePCA";
_deletepca();
 //BA.debugLineNum = 1097;BA.debugLine="DownloadBookPCA(GlobalVar.BranchID, strRdgDate)";
_downloadbookpca(mostCurrent._globalvar._branchid /*int*/ ,mostCurrent._strrdgdate);
 //BA.debugLineNum = 1098;BA.debugLine="End Sub";
return "";
}
public static String  _retrydownloadbooks_click() throws Exception{
 //BA.debugLineNum = 1065;BA.debugLine="Private Sub RetryDownloadBooks_Click()";
 //BA.debugLineNum = 1066;BA.debugLine="DeleteBooks";
_deletebooks();
 //BA.debugLineNum = 1067;BA.debugLine="DownloadBooks(GlobalVar.BranchID)";
_downloadbooks(mostCurrent._globalvar._branchid /*int*/ );
 //BA.debugLineNum = 1068;BA.debugLine="End Sub";
return "";
}
public static String  _retrydownloadcustomers_click() throws Exception{
 //BA.debugLineNum = 1080;BA.debugLine="Private Sub RetryDownloadCustomers_Click()";
 //BA.debugLineNum = 1081;BA.debugLine="DeleteReadings";
_deletereadings();
 //BA.debugLineNum = 1082;BA.debugLine="DownloadCustomers(GlobalVar.BranchID, ReaderID, s";
_downloadcustomers(mostCurrent._globalvar._branchid /*int*/ ,_readerid,mostCurrent._strrdgdate);
 //BA.debugLineNum = 1083;BA.debugLine="End Sub";
return "";
}
public static String  _retrydownloademployeeinfo_click() throws Exception{
 //BA.debugLineNum = 1070;BA.debugLine="Private Sub RetryDownloadEmployeeInfo_Click()";
 //BA.debugLineNum = 1071;BA.debugLine="DeleteEmployeeInfo";
_deleteemployeeinfo();
 //BA.debugLineNum = 1072;BA.debugLine="DownloadEmployeeInfo(ReaderID, GlobalVar.BranchID";
_downloademployeeinfo(_readerid,mostCurrent._globalvar._branchid /*int*/ );
 //BA.debugLineNum = 1073;BA.debugLine="End Sub";
return "";
}
public static String  _retrydownloadratedetails_click() throws Exception{
 //BA.debugLineNum = 1090;BA.debugLine="Private Sub RetryDownloadRateDetails_Click()";
 //BA.debugLineNum = 1091;BA.debugLine="DeleteRateDetails";
_deleteratedetails();
 //BA.debugLineNum = 1092;BA.debugLine="DownloadRateDetails(GlobalVar.BranchID)";
_downloadratedetails(mostCurrent._globalvar._branchid /*int*/ );
 //BA.debugLineNum = 1093;BA.debugLine="End Sub";
return "";
}
public static String  _retrydownloadrateheader_click() throws Exception{
 //BA.debugLineNum = 1085;BA.debugLine="Private Sub RetryDownloadRateHeader_Click()";
 //BA.debugLineNum = 1086;BA.debugLine="DeleteRateHeader";
_deleterateheader();
 //BA.debugLineNum = 1087;BA.debugLine="DownloadRateHeader(GlobalVar.BranchID)";
_downloadrateheader(mostCurrent._globalvar._branchid /*int*/ );
 //BA.debugLineNum = 1088;BA.debugLine="End Sub";
return "";
}
public static String  _retrydownloadseptagerates_click() throws Exception{
 //BA.debugLineNum = 1099;BA.debugLine="Private Sub RetryDownloadSeptageRates_Click()";
 //BA.debugLineNum = 1100;BA.debugLine="DeleteSeptageRates";
_deleteseptagerates();
 //BA.debugLineNum = 1101;BA.debugLine="DownloadSeptageRates(GlobalVar.BranchID, strRdgDa";
_downloadseptagerates(mostCurrent._globalvar._branchid /*int*/ ,mostCurrent._strrdgdate);
 //BA.debugLineNum = 1102;BA.debugLine="End Sub";
return "";
}
public static void  _retuppassword_buttonpressed(de.amberhome.materialdialogs.MaterialDialogWrapper _mdialog,String _saction) throws Exception{
ResumableSub_RetUPPassword_ButtonPressed rsub = new ResumableSub_RetUPPassword_ButtonPressed(null,_mdialog,_saction);
rsub.resume(processBA, null);
}
public static class ResumableSub_RetUPPassword_ButtonPressed extends BA.ResumableSub {
public ResumableSub_RetUPPassword_ButtonPressed(com.bwsi.MeterReader.datasyncing parent,de.amberhome.materialdialogs.MaterialDialogWrapper _mdialog,String _saction) {
this.parent = parent;
this._mdialog = _mdialog;
this._saction = _saction;
}
com.bwsi.MeterReader.datasyncing parent;
de.amberhome.materialdialogs.MaterialDialogWrapper _mdialog;
String _saction;
long _ldatetime = 0L;
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsbookassign = null;
anywheresoftware.b4a.objects.collections.List _booklist = null;
anywheresoftware.b4a.objects.collections.Map _mpbooks = null;
int _res = 0;
int _i = 0;
String _sdatetime = "";
String _permission = "";
boolean _result = false;
anywheresoftware.b4a.objects.IntentWrapper _idbase = null;
int step15;
int limit15;

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
 //BA.debugLineNum = 1305;BA.debugLine="Select Case sAction";
if (true) break;

case 1:
//select
this.state = 50;
switch (BA.switchObjectToInt(_saction,_mdialog.ACTION_POSITIVE,_mdialog.ACTION_NEGATIVE)) {
case 0: {
this.state = 3;
if (true) break;
}
case 1: {
this.state = 49;
if (true) break;
}
}
if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 1307;BA.debugLine="Dim lDateTime As Long";
_ldatetime = 0L;
 //BA.debugLineNum = 1308;BA.debugLine="Dim rsBookAssign As Cursor";
_rsbookassign = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 1309;BA.debugLine="Dim BookList As List";
_booklist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 1310;BA.debugLine="Dim mpBooks As Map";
_mpbooks = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1311;BA.debugLine="Dim Res As Int";
_res = 0;
 //BA.debugLineNum = 1314;BA.debugLine="BookList.Initialize";
_booklist.Initialize();
 //BA.debugLineNum = 1315;BA.debugLine="Starter.strCriteria=\"SELECT * FROM tblBookAssig";
parent.mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblBookAssignments "+"WHERE UserID = "+BA.NumberToString(parent._intreaderid)+" "+"AND WasUploaded = 0";
 //BA.debugLineNum = 1318;BA.debugLine="rsBookAssign=Starter.DBCon.ExecQuery(Starter.st";
_rsbookassign = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(parent.mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(parent.mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 1320;BA.debugLine="If rsBookAssign.RowCount = 0 Then";
if (true) break;

case 4:
//if
this.state = 13;
if (_rsbookassign.getRowCount()==0) { 
this.state = 6;
}else {
this.state = 8;
}if (true) break;

case 6:
//C
this.state = 13;
 //BA.debugLineNum = 1321;BA.debugLine="xui.MsgboxAsync(\"No assigned book(s)ready for";
parent._xui.MsgboxAsync(processBA,BA.ObjectToCharSequence("No assigned book(s)ready for uploading..."),BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.Application.getLabelName()));
 //BA.debugLineNum = 1322;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 8:
//C
this.state = 9;
 //BA.debugLineNum = 1324;BA.debugLine="For i = 0 To rsBookAssign.RowCount - 1";
if (true) break;

case 9:
//for
this.state = 12;
step15 = 1;
limit15 = (int) (_rsbookassign.getRowCount()-1);
_i = (int) (0) ;
this.state = 51;
if (true) break;

case 51:
//C
this.state = 12;
if ((step15 > 0 && _i <= limit15) || (step15 < 0 && _i >= limit15)) this.state = 11;
if (true) break;

case 52:
//C
this.state = 51;
_i = ((int)(0 + _i + step15)) ;
if (true) break;

case 11:
//C
this.state = 52;
 //BA.debugLineNum = 1325;BA.debugLine="rsBookAssign.Position = i";
_rsbookassign.setPosition(_i);
 //BA.debugLineNum = 1326;BA.debugLine="mpBooks.Initialize";
_mpbooks.Initialize();
 //BA.debugLineNum = 1327;BA.debugLine="BookList.Add(rsBookAssign.GetString(\"BookCode";
_booklist.Add((Object)(_rsbookassign.GetString("BookCode")));
 if (true) break;
if (true) break;

case 12:
//C
this.state = 13;
;
 if (true) break;

case 13:
//C
this.state = 14;
;
 //BA.debugLineNum = 1331;BA.debugLine="Res = InputList(BookList,\"Select a Book To Uplo";
_res = anywheresoftware.b4a.keywords.Common.InputList(_booklist,BA.ObjectToCharSequence("Select a Book To Upload"),(int) (-1),mostCurrent.activityBA);
 //BA.debugLineNum = 1332;BA.debugLine="Log(Res)";
anywheresoftware.b4a.keywords.Common.LogImpl("14718620",BA.NumberToString(_res),0);
 //BA.debugLineNum = 1334;BA.debugLine="If Res <> DialogResponse.CANCEL Then";
if (true) break;

case 14:
//if
this.state = 19;
if (_res!=anywheresoftware.b4a.keywords.Common.DialogResponse.CANCEL) { 
this.state = 16;
}else {
this.state = 18;
}if (true) break;

case 16:
//C
this.state = 19;
 //BA.debugLineNum = 1335;BA.debugLine="ProgressDialogShow2($\"Generating data from Boo";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence(("Generating data from Book ")+BA.ObjectToString(_booklist.Get(_res))+(" for uploading...")),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1336;BA.debugLine="intUploadedBookID = DBaseFunctions.GetIDbyCode";
parent._intuploadedbookid = (int) (parent.mostCurrent._dbasefunctions._getidbycode /*double*/ (mostCurrent.activityBA,"BookID","tblBooks","BookCode",BA.ObjectToString(_booklist.Get(_res))));
 //BA.debugLineNum = 1337;BA.debugLine="Log (intUploadedBookID)";
anywheresoftware.b4a.keywords.Common.LogImpl("14718625",BA.NumberToString(parent._intuploadedbookid),0);
 if (true) break;

case 18:
//C
this.state = 19;
 //BA.debugLineNum = 1339;BA.debugLine="snack.Initialize(\"\", Activity, $\"Uploading Dat";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(parent.mostCurrent._activity.getObject()),("Uploading Data Canceled!"),parent.mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 1340;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1341;BA.debugLine="SetSnackBarTextColor(snack,Colors.White)";
_setsnackbartextcolor(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1342;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 1343;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 19:
//C
this.state = 20;
;
 //BA.debugLineNum = 1345;BA.debugLine="strReadingData = ConvertReadingJSON(GlobalVar.B";
parent._strreadingdata = _convertreadingjson(parent.mostCurrent._globalvar._branchid /*int*/ ,parent._intuploadedbookid);
 //BA.debugLineNum = 1347;BA.debugLine="SaveJSON(intUploadedBookID, strReadingData)";
_savejson(parent._intuploadedbookid,parent._strreadingdata);
 //BA.debugLineNum = 1349;BA.debugLine="If strReadingData=\"\" Or strReadingData.Length=0";
if (true) break;

case 20:
//if
this.state = 23;
if ((parent._strreadingdata).equals("") || parent._strreadingdata.length()==0) { 
this.state = 22;
}if (true) break;

case 22:
//C
this.state = 23;
 //BA.debugLineNum = 1350;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 1351;BA.debugLine="snack.Initialize(\"\", Activity, $\"No Reading Da";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(parent.mostCurrent._activity.getObject()),("No Reading Data found..."),parent.mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 1352;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1353;BA.debugLine="SetSnackBarTextColor(snack,Colors.White)";
_setsnackbartextcolor(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1354;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 1355;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 23:
//C
this.state = 24;
;
 //BA.debugLineNum = 1358;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 1360;BA.debugLine="If DispUploadMsg($\"Reading data ready for Uploa";
if (true) break;

case 24:
//if
this.state = 47;
if (_dispuploadmsg(("Reading data ready for Uploading!")+anywheresoftware.b4a.keywords.Common.CRLF+("You are about to upload ")+BA.NumberToString(parent._uploadctr)+(" record(s)...")+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+("Tap Ok to continue..."),("DATA UPLOADING READY"))==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 26;
}else {
this.state = 28;
}if (true) break;

case 26:
//C
this.state = 47;
 //BA.debugLineNum = 1361;BA.debugLine="snack.Initialize(\"\", Activity, $\"Uploading dat";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(parent.mostCurrent._activity.getObject()),("Uploading data cancelled."),parent.mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 1362;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1363;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1364;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 1365;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 28:
//C
this.state = 29;
 //BA.debugLineNum = 1368;BA.debugLine="Dim sDateTime As String";
_sdatetime = "";
 //BA.debugLineNum = 1369;BA.debugLine="Dim lDateTime As Long";
_ldatetime = 0L;
 //BA.debugLineNum = 1371;BA.debugLine="lDateTime = DateTime.Now";
_ldatetime = anywheresoftware.b4a.keywords.Common.DateTime.getNow();
 //BA.debugLineNum = 1372;BA.debugLine="DateTime.DateFormat = \"yyyyMMdd HHmmss a\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("yyyyMMdd HHmmss a");
 //BA.debugLineNum = 1373;BA.debugLine="sDateTime = DateTime.Date(lDateTime)";
_sdatetime = anywheresoftware.b4a.keywords.Common.DateTime.Date(_ldatetime);
 //BA.debugLineNum = 1375;BA.debugLine="If File.Exists(File.Combine(File.DirRootExtern";
if (true) break;

case 29:
//if
this.state = 34;
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.Combine(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"documents"),"MasterDB")==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 31;
;}if (true) break;

case 31:
//C
this.state = 34;
anywheresoftware.b4a.keywords.Common.File.MakeDir(anywheresoftware.b4a.keywords.Common.File.Combine(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"documents"),"MasterDB");
if (true) break;

case 34:
//C
this.state = 35;
;
 //BA.debugLineNum = 1376;BA.debugLine="BackUpPath = File.Combine(File.DirRootExternal";
parent.mostCurrent._backuppath = anywheresoftware.b4a.keywords.Common.File.Combine(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"documents/MasterDB/");
 //BA.debugLineNum = 1377;BA.debugLine="LogColor(BackUpPath, Colors.Yellow)";
anywheresoftware.b4a.keywords.Common.LogImpl("14718665",parent.mostCurrent._backuppath,anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 //BA.debugLineNum = 1379;BA.debugLine="BackUpName = GetReaderName(intReaderID) & \" \"";
parent.mostCurrent._backupname = _getreadername(parent._intreaderid)+" "+_sdatetime+" backup.db";
 //BA.debugLineNum = 1380;BA.debugLine="Try";
if (true) break;

case 35:
//try
this.state = 46;
this.catchState = 45;
this.state = 37;
if (true) break;

case 37:
//C
this.state = 38;
this.catchState = 45;
 //BA.debugLineNum = 1381;BA.debugLine="Starter.rp.GetAllSafeDirsExternal(BackUpPath)";
parent.mostCurrent._starter._rp /*anywheresoftware.b4a.objects.RuntimePermissions*/ .GetAllSafeDirsExternal(parent.mostCurrent._backuppath);
 //BA.debugLineNum = 1382;BA.debugLine="Starter.rp.CheckAndRequest(Starter.rp.PERMISS";
parent.mostCurrent._starter._rp /*anywheresoftware.b4a.objects.RuntimePermissions*/ .CheckAndRequest(processBA,parent.mostCurrent._starter._rp /*anywheresoftware.b4a.objects.RuntimePermissions*/ .PERMISSION_WRITE_EXTERNAL_STORAGE);
 //BA.debugLineNum = 1384;BA.debugLine="Wait For Activity_PermissionResult (Permissio";
anywheresoftware.b4a.keywords.Common.WaitFor("activity_permissionresult", processBA, this, null);
this.state = 53;
return;
case 53:
//C
this.state = 38;
_permission = (String) result[0];
_result = (Boolean) result[1];
;
 //BA.debugLineNum = 1386;BA.debugLine="If Result = False Then";
if (true) break;

case 38:
//if
this.state = 43;
if (_result==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 40;
}else {
this.state = 42;
}if (true) break;

case 40:
//C
this.state = 43;
 //BA.debugLineNum = 1387;BA.debugLine="ToastMessageShow ($\"Unable to backup due to";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to backup due to permission to write was denied")),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 42:
//C
this.state = 43;
 //BA.debugLineNum = 1389;BA.debugLine="File.Copy(Starter.DBPath, Starter.DBName, Ba";
anywheresoftware.b4a.keywords.Common.File.Copy(parent.mostCurrent._starter._dbpath /*String*/ ,parent.mostCurrent._starter._dbname /*String*/ ,parent.mostCurrent._backuppath,parent.mostCurrent._backupname);
 //BA.debugLineNum = 1390;BA.debugLine="ToastMessageShow ($\"Auto Backup Success!\"$,T";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Auto Backup Success!")),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 43:
//C
this.state = 46;
;
 if (true) break;

case 45:
//C
this.state = 46;
this.catchState = 0;
 //BA.debugLineNum = 1394;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("14718682",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 if (true) break;
if (true) break;

case 46:
//C
this.state = 47;
this.catchState = 0;
;
 if (true) break;

case 47:
//C
this.state = 50;
;
 //BA.debugLineNum = 1398;BA.debugLine="Dim iDBase As Intent";
_idbase = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 1399;BA.debugLine="iDBase.Initialize(iDBase.ACTION_VIEW, \"file://\"";
_idbase.Initialize(_idbase.ACTION_VIEW,"file://"+anywheresoftware.b4a.keywords.Common.File.Combine(parent.mostCurrent._backuppath,parent.mostCurrent._backupname+"/"));
 //BA.debugLineNum = 1400;BA.debugLine="iDBase.SetType( \"resource/folder\" )";
_idbase.SetType("resource/folder");
 //BA.debugLineNum = 1402;BA.debugLine="Log(strReadingData)";
anywheresoftware.b4a.keywords.Common.LogImpl("14718690",parent._strreadingdata,0);
 //BA.debugLineNum = 1403;BA.debugLine="ProgressDialogShow2(\"Reading Data Uploading...\"";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Reading Data Uploading..."+anywheresoftware.b4a.keywords.Common.CRLF+"Please Wait."),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1405;BA.debugLine="UploadData(strReadingData)";
_uploaddata(parent._strreadingdata);
 if (true) break;

case 49:
//C
this.state = 50;
 //BA.debugLineNum = 1408;BA.debugLine="snack.Initialize(\"\", Activity, $\"Uploading read";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(parent.mostCurrent._activity.getObject()),("Uploading reading data was cancelled"),parent.mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 1409;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1410;BA.debugLine="SetSnackBarTextColor(snack,Colors.White)";
_setsnackbartextcolor(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1411;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 1412;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 50:
//C
this.state = -1;
;
 //BA.debugLineNum = 1414;BA.debugLine="End Sub";
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
public static String  _retuppassword_inputchanged(de.amberhome.materialdialogs.MaterialDialogWrapper _mdialog,String _sinput) throws Exception{
anywheresoftware.b4a.objects.CSBuilder _csbuild = null;
 //BA.debugLineNum = 1285;BA.debugLine="Private Sub RetUPPassword_InputChanged (mDialog As";
 //BA.debugLineNum = 1286;BA.debugLine="Dim csBuild As CSBuilder";
_csbuild = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 1287;BA.debugLine="If sInput.Length <= 0 Then";
if (_sinput.length()<=0) { 
 //BA.debugLineNum = 1288;BA.debugLine="csBuild.Initialize.Bold.Color(Colors.Red).Append";
_csbuild.Initialize().Bold().Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(("Enter your Password to Continue..."))).PopAll();
 //BA.debugLineNum = 1289;BA.debugLine="mDialog.Content = csBuild";
_mdialog.setContent(BA.ObjectToCharSequence(_csbuild.getObject()));
 //BA.debugLineNum = 1290;BA.debugLine="mDialog.EnableActionButton(mDialog.ACTION_POSITI";
_mdialog.EnableActionButton(_mdialog.ACTION_POSITIVE,anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 1292;BA.debugLine="If sInput <> GlobalVar.UserPW Then";
if ((_sinput).equals(mostCurrent._globalvar._userpw /*String*/ ) == false) { 
 //BA.debugLineNum = 1293;BA.debugLine="mDialog.EnableActionButton(mDialog.ACTION_POSIT";
_mdialog.EnableActionButton(_mdialog.ACTION_POSITIVE,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1294;BA.debugLine="csBuild.Initialize.Bold.Color(Colors.Red).Appen";
_csbuild.Initialize().Bold().Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(("Password is Incorrect!"))).PopAll();
 //BA.debugLineNum = 1295;BA.debugLine="mDialog.Content = csBuild";
_mdialog.setContent(BA.ObjectToCharSequence(_csbuild.getObject()));
 }else {
 //BA.debugLineNum = 1297;BA.debugLine="mDialog.EnableActionButton(mDialog.ACTION_POSIT";
_mdialog.EnableActionButton(_mdialog.ACTION_POSITIVE,anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1298;BA.debugLine="csBuild.Initialize.Bold.Color(Colors.Black).App";
_csbuild.Initialize().Bold().Color(anywheresoftware.b4a.keywords.Common.Colors.Black).Append(BA.ObjectToCharSequence(("Password is Ok!"))).PopAll();
 //BA.debugLineNum = 1299;BA.debugLine="mDialog.Content = csBuild";
_mdialog.setContent(BA.ObjectToCharSequence(_csbuild.getObject()));
 };
 };
 //BA.debugLineNum = 1302;BA.debugLine="End Sub";
return "";
}
public static void  _savebookassignments(String _sreturnval) throws Exception{
ResumableSub_SaveBookAssignments rsub = new ResumableSub_SaveBookAssignments(null,_sreturnval);
rsub.resume(processBA, null);
}
public static class ResumableSub_SaveBookAssignments extends BA.ResumableSub {
public ResumableSub_SaveBookAssignments(com.bwsi.MeterReader.datasyncing parent,String _sreturnval) {
this.parent = parent;
this._sreturnval = _sreturnval;
}
com.bwsi.MeterReader.datasyncing parent;
String _sreturnval;
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.List _root = null;
anywheresoftware.b4a.objects.collections.Map _mp = null;
Object _senderfilter = null;
boolean _success = false;
anywheresoftware.b4a.BA.IterableList group6;
int index6;
int groupLen6;

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
 //BA.debugLineNum = 808;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 809;BA.debugLine="Dim root As List";
_root = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 811;BA.debugLine="parser.Initialize(sReturnVal)";
_parser.Initialize(_sreturnval);
 //BA.debugLineNum = 812;BA.debugLine="root = parser.NextArray";
_root = _parser.NextArray();
 //BA.debugLineNum = 814;BA.debugLine="Try";
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
 //BA.debugLineNum = 815;BA.debugLine="For Each MP As Map In root";
if (true) break;

case 4:
//for
this.state = 7;
_mp = new anywheresoftware.b4a.objects.collections.Map();
group6 = _root;
index6 = 0;
groupLen6 = group6.getSize();
this.state = 17;
if (true) break;

case 17:
//C
this.state = 7;
if (index6 < groupLen6) {
this.state = 6;
_mp = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(group6.Get(index6)));}
if (true) break;

case 18:
//C
this.state = 17;
index6++;
if (true) break;

case 6:
//C
this.state = 18;
 //BA.debugLineNum = 816;BA.debugLine="Starter.strCriteria=\"INSERT INTO tblBookAssignm";
parent.mostCurrent._starter._strcriteria /*String*/  = "INSERT INTO tblBookAssignments VALUES ("+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Null)+", ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
 //BA.debugLineNum = 817;BA.debugLine="Starter.DBCon.AddNonQueryToBatch(Starter.strCri";
parent.mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .AddNonQueryToBatch(parent.mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(parent.mostCurrent._globalvar._branchid /*int*/ ),(Object)(parent.mostCurrent._globalvar._billyear /*double*/ ),(Object)(parent.mostCurrent._globalvar._billmonth /*int*/ ),_mp.Get((Object)("BookID")),_mp.Get((Object)("BookCode")),_mp.Get((Object)("BookDesc")),_mp.Get((Object)("NoOfAccts")),_mp.Get((Object)("PrevRdgDate")),_mp.Get((Object)("LastBillNo")),(Object)(parent._readerid),(Object)(parent.mostCurrent._strrdgdate),_mp.Get((Object)("BillPeriodFrom")),_mp.Get((Object)("BillPeriodTo")),_mp.Get((Object)("DueDate")),(Object)(("0")),(Object)((""))}));
 if (true) break;
if (true) break;

case 7:
//C
this.state = 8;
;
 //BA.debugLineNum = 820;BA.debugLine="Dim SenderFilter As Object = Starter.DBCon.ExecN";
_senderfilter = parent.mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQueryBatch(processBA,"SQL");
 //BA.debugLineNum = 821;BA.debugLine="Wait For (SenderFilter) SQL_NonQueryComplete (Su";
anywheresoftware.b4a.keywords.Common.WaitFor("sql_nonquerycomplete", processBA, this, _senderfilter);
this.state = 19;
return;
case 19:
//C
this.state = 8;
_success = (Boolean) result[0];
;
 //BA.debugLineNum = 822;BA.debugLine="If Success Then";
if (true) break;

case 8:
//if
this.state = 13;
if (_success) { 
this.state = 10;
}else {
this.state = 12;
}if (true) break;

case 10:
//C
this.state = 13;
 //BA.debugLineNum = 823;BA.debugLine="snack.Initialize(\"\", Activity, $\"Book Assignmen";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(parent.mostCurrent._activity.getObject()),("Book Assignment(s) were successfully downloaded."),parent.mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 824;BA.debugLine="SetSnackBarBackground(snack,Colors.White)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 825;BA.debugLine="SetSnackBarTextColor(snack, GlobalVar.PriColor)";
_setsnackbartextcolor(parent.mostCurrent._snack,(int) (parent.mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 826;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 827;BA.debugLine="DeleteReadings";
_deletereadings();
 //BA.debugLineNum = 828;BA.debugLine="DownloadCustomers(GlobalVar.BranchID, ReaderID,";
_downloadcustomers(parent.mostCurrent._globalvar._branchid /*int*/ ,parent._readerid,parent.mostCurrent._strrdgdate);
 if (true) break;

case 12:
//C
this.state = 13;
 //BA.debugLineNum = 830;BA.debugLine="snack.Initialize(\"RetryDownloadBookAssignments\"";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"RetryDownloadBookAssignments",(android.view.View)(parent.mostCurrent._activity.getObject()),("Unable to save book assignment(s) due to ")+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),parent.mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 831;BA.debugLine="snack.SetAction(\"Retry\")";
parent.mostCurrent._snack.SetAction("Retry");
 //BA.debugLineNum = 832;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 833;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 834;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 835;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("12687004",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 if (true) break;

case 13:
//C
this.state = 16;
;
 if (true) break;

case 15:
//C
this.state = 16;
this.catchState = 0;
 //BA.debugLineNum = 838;BA.debugLine="snack.Initialize(\"RetryDownloadBookAssignments\",";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"RetryDownloadBookAssignments",(android.view.View)(parent.mostCurrent._activity.getObject()),("Unable to save book assignment(s) due to ")+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),parent.mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 839;BA.debugLine="snack.SetAction(\"Retry\")";
parent.mostCurrent._snack.SetAction("Retry");
 //BA.debugLineNum = 840;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 841;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 842;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 843;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("12687012",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 if (true) break;
if (true) break;

case 16:
//C
this.state = -1;
this.catchState = 0;
;
 //BA.debugLineNum = 845;BA.debugLine="End Sub";
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
public static void  _savebookdata(String _sreturnval) throws Exception{
ResumableSub_SaveBookData rsub = new ResumableSub_SaveBookData(null,_sreturnval);
rsub.resume(processBA, null);
}
public static class ResumableSub_SaveBookData extends BA.ResumableSub {
public ResumableSub_SaveBookData(com.bwsi.MeterReader.datasyncing parent,String _sreturnval) {
this.parent = parent;
this._sreturnval = _sreturnval;
}
com.bwsi.MeterReader.datasyncing parent;
String _sreturnval;
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.List _root = null;
anywheresoftware.b4a.objects.collections.Map _mp = null;
Object _senderfilter = null;
boolean _success = false;
anywheresoftware.b4a.BA.IterableList group6;
int index6;
int groupLen6;

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
 //BA.debugLineNum = 710;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 711;BA.debugLine="Dim root As List";
_root = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 713;BA.debugLine="parser.Initialize(sReturnVal)";
_parser.Initialize(_sreturnval);
 //BA.debugLineNum = 714;BA.debugLine="root = parser.NextArray";
_root = _parser.NextArray();
 //BA.debugLineNum = 716;BA.debugLine="Try";
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
 //BA.debugLineNum = 717;BA.debugLine="For Each MP As Map In root";
if (true) break;

case 4:
//for
this.state = 7;
_mp = new anywheresoftware.b4a.objects.collections.Map();
group6 = _root;
index6 = 0;
groupLen6 = group6.getSize();
this.state = 17;
if (true) break;

case 17:
//C
this.state = 7;
if (index6 < groupLen6) {
this.state = 6;
_mp = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(group6.Get(index6)));}
if (true) break;

case 18:
//C
this.state = 17;
index6++;
if (true) break;

case 6:
//C
this.state = 18;
 //BA.debugLineNum = 718;BA.debugLine="Starter.strCriteria=\"INSERT INTO tblBooks VALUE";
parent.mostCurrent._starter._strcriteria /*String*/  = "INSERT INTO tblBooks VALUES (?, ?, ?, ?, ?, ?, ?)";
 //BA.debugLineNum = 719;BA.debugLine="Starter.DBCon.AddNonQueryToBatch(Starter.strCri";
parent.mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .AddNonQueryToBatch(parent.mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{_mp.Get((Object)("BookID")),_mp.Get((Object)("BranchID")),_mp.Get((Object)("BookCode")),_mp.Get((Object)("BookDesc")),_mp.Get((Object)("NoDueDate")),_mp.Get((Object)("WithPCA")),(Object)(("0"))}));
 if (true) break;
if (true) break;

case 7:
//C
this.state = 8;
;
 //BA.debugLineNum = 722;BA.debugLine="Dim SenderFilter As Object = Starter.DBCon.ExecN";
_senderfilter = parent.mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQueryBatch(processBA,"SQL");
 //BA.debugLineNum = 723;BA.debugLine="Wait For (SenderFilter) SQL_NonQueryComplete (Su";
anywheresoftware.b4a.keywords.Common.WaitFor("sql_nonquerycomplete", processBA, this, _senderfilter);
this.state = 19;
return;
case 19:
//C
this.state = 8;
_success = (Boolean) result[0];
;
 //BA.debugLineNum = 725;BA.debugLine="If Success Then";
if (true) break;

case 8:
//if
this.state = 13;
if (_success) { 
this.state = 10;
}else {
this.state = 12;
}if (true) break;

case 10:
//C
this.state = 13;
 //BA.debugLineNum = 726;BA.debugLine="snack.Initialize(\"\", Activity, $\"Book/Zone Data";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(parent.mostCurrent._activity.getObject()),("Book/Zone Data were successfully downloaded."),parent.mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 727;BA.debugLine="SetSnackBarBackground(snack,Colors.White)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 728;BA.debugLine="SetSnackBarTextColor(snack, GlobalVar.PriColor)";
_setsnackbartextcolor(parent.mostCurrent._snack,(int) (parent.mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 729;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 730;BA.debugLine="DeleteEmployeeInfo";
_deleteemployeeinfo();
 //BA.debugLineNum = 731;BA.debugLine="DownloadEmployeeInfo(ReaderID, GlobalVar.Branch";
_downloademployeeinfo(parent._readerid,parent.mostCurrent._globalvar._branchid /*int*/ );
 if (true) break;

case 12:
//C
this.state = 13;
 //BA.debugLineNum = 733;BA.debugLine="snack.Initialize(\"RetryDownloadBooks\", Activity";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"RetryDownloadBooks",(android.view.View)(parent.mostCurrent._activity.getObject()),("Unable to save book assignment(s) due to ")+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),parent.mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 734;BA.debugLine="snack.SetAction(\"Retry\")";
parent.mostCurrent._snack.SetAction("Retry");
 //BA.debugLineNum = 735;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 736;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 737;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 738;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("12555933",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 if (true) break;

case 13:
//C
this.state = 16;
;
 if (true) break;

case 15:
//C
this.state = 16;
this.catchState = 0;
 //BA.debugLineNum = 741;BA.debugLine="snack.Initialize(\"RetryDownloadBooks\", Activity,";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"RetryDownloadBooks",(android.view.View)(parent.mostCurrent._activity.getObject()),("Unable to save book assignment(s) due to ")+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),parent.mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 742;BA.debugLine="snack.SetAction(\"Retry\")";
parent.mostCurrent._snack.SetAction("Retry");
 //BA.debugLineNum = 743;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 744;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 745;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 746;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("12555941",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 if (true) break;
if (true) break;

case 16:
//C
this.state = -1;
this.catchState = 0;
;
 //BA.debugLineNum = 766;BA.debugLine="End Sub";
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
public static void  _savebookpca(String _sreturnval) throws Exception{
ResumableSub_SaveBookPCA rsub = new ResumableSub_SaveBookPCA(null,_sreturnval);
rsub.resume(processBA, null);
}
public static class ResumableSub_SaveBookPCA extends BA.ResumableSub {
public ResumableSub_SaveBookPCA(com.bwsi.MeterReader.datasyncing parent,String _sreturnval) {
this.parent = parent;
this._sreturnval = _sreturnval;
}
com.bwsi.MeterReader.datasyncing parent;
String _sreturnval;
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.List _root = null;
anywheresoftware.b4a.objects.collections.Map _mp = null;
Object _senderfilter = null;
boolean _success = false;
anywheresoftware.b4a.BA.IterableList group6;
int index6;
int groupLen6;

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
 //BA.debugLineNum = 979;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 980;BA.debugLine="Dim root As List";
_root = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 982;BA.debugLine="parser.Initialize(sReturnVal)";
_parser.Initialize(_sreturnval);
 //BA.debugLineNum = 983;BA.debugLine="root = parser.NextArray";
_root = _parser.NextArray();
 //BA.debugLineNum = 985;BA.debugLine="Try";
if (true) break;

case 1:
//try
this.state = 28;
this.catchState = 27;
this.state = 3;
if (true) break;

case 3:
//C
this.state = 4;
this.catchState = 27;
 //BA.debugLineNum = 986;BA.debugLine="For Each MP As Map In root";
if (true) break;

case 4:
//for
this.state = 7;
_mp = new anywheresoftware.b4a.objects.collections.Map();
group6 = _root;
index6 = 0;
groupLen6 = group6.getSize();
this.state = 29;
if (true) break;

case 29:
//C
this.state = 7;
if (index6 < groupLen6) {
this.state = 6;
_mp = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(group6.Get(index6)));}
if (true) break;

case 30:
//C
this.state = 29;
index6++;
if (true) break;

case 6:
//C
this.state = 30;
 //BA.debugLineNum = 987;BA.debugLine="Starter.strCriteria=\"INSERT INTO tblPCA VALUES";
parent.mostCurrent._starter._strcriteria /*String*/  = "INSERT INTO tblPCA VALUES ("+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Null)+", ?, ?, ?, ?)";
 //BA.debugLineNum = 988;BA.debugLine="Starter.DBCon.AddNonQueryToBatch(Starter.strCri";
parent.mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .AddNonQueryToBatch(parent.mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{_mp.Get((Object)("branch_id")),_mp.Get((Object)("book_id")),_mp.Get((Object)("cutoff")),_mp.Get((Object)("amount"))}));
 if (true) break;
if (true) break;

case 7:
//C
this.state = 8;
;
 //BA.debugLineNum = 990;BA.debugLine="Dim SenderFilter As Object = Starter.DBCon.ExecN";
_senderfilter = parent.mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQueryBatch(processBA,"SQL");
 //BA.debugLineNum = 991;BA.debugLine="Wait For (SenderFilter) SQL_NonQueryComplete (Su";
anywheresoftware.b4a.keywords.Common.WaitFor("sql_nonquerycomplete", processBA, this, _senderfilter);
this.state = 31;
return;
case 31:
//C
this.state = 8;
_success = (Boolean) result[0];
;
 //BA.debugLineNum = 992;BA.debugLine="If Success Then";
if (true) break;

case 8:
//if
this.state = 25;
if (_success) { 
this.state = 10;
}else {
this.state = 24;
}if (true) break;

case 10:
//C
this.state = 11;
 //BA.debugLineNum = 993;BA.debugLine="snack.Initialize(\"\", Activity, $\"PCA Data were";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(parent.mostCurrent._activity.getObject()),("PCA Data were successfully downloaded."),parent.mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 994;BA.debugLine="SetSnackBarBackground(snack,Colors.White)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 995;BA.debugLine="SetSnackBarTextColor(snack, GlobalVar.PriColor)";
_setsnackbartextcolor(parent.mostCurrent._snack,(int) (parent.mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 996;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 997;BA.debugLine="If GlobalVar.WithSeptageFee = 1 Then";
if (true) break;

case 11:
//if
this.state = 22;
if (parent.mostCurrent._globalvar._withseptagefee /*int*/ ==1) { 
this.state = 13;
}else {
this.state = 21;
}if (true) break;

case 13:
//C
this.state = 14;
 //BA.debugLineNum = 998;BA.debugLine="If GlobalVar.BranchID = 14 Or GlobalVar.Branch";
if (true) break;

case 14:
//if
this.state = 19;
if (parent.mostCurrent._globalvar._branchid /*int*/ ==14 || parent.mostCurrent._globalvar._branchid /*int*/ ==62 || parent.mostCurrent._globalvar._branchid /*int*/ ==28 || parent.mostCurrent._globalvar._branchid /*int*/ ==29 || parent.mostCurrent._globalvar._branchid /*int*/ ==30) { 
this.state = 16;
}else {
this.state = 18;
}if (true) break;

case 16:
//C
this.state = 19;
 //BA.debugLineNum = 999;BA.debugLine="DeleteSeptageRates";
_deleteseptagerates();
 //BA.debugLineNum = 1000;BA.debugLine="DownloadSeptageRates(GlobalVar.BranchID, strR";
_downloadseptagerates(parent.mostCurrent._globalvar._branchid /*int*/ ,parent.mostCurrent._strrdgdate);
 if (true) break;

case 18:
//C
this.state = 19;
 //BA.debugLineNum = 1002;BA.debugLine="ShowDLComplete($\"Download Complete\"$,$\"Readin";
_showdlcomplete(("Download Complete"),("Reading Data for the specified Reader were successfully downloaded."));
 if (true) break;

case 19:
//C
this.state = 22;
;
 if (true) break;

case 21:
//C
this.state = 22;
 //BA.debugLineNum = 1005;BA.debugLine="ShowDLComplete($\"Download Complete\"$,$\"Reading";
_showdlcomplete(("Download Complete"),("Reading Data for the specified Reader were successfully downloaded."));
 if (true) break;

case 22:
//C
this.state = 25;
;
 if (true) break;

case 24:
//C
this.state = 25;
 //BA.debugLineNum = 1008;BA.debugLine="snack.Initialize(\"RetryDownloadBookPCA\", Activi";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"RetryDownloadBookPCA",(android.view.View)(parent.mostCurrent._activity.getObject()),("Unable to save PCA Data due to ")+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),parent.mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 1009;BA.debugLine="snack.SetAction(\"Retry\")";
parent.mostCurrent._snack.SetAction("Retry");
 //BA.debugLineNum = 1010;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1011;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1012;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 1013;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("12949155",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 if (true) break;

case 25:
//C
this.state = 28;
;
 if (true) break;

case 27:
//C
this.state = 28;
this.catchState = 0;
 //BA.debugLineNum = 1016;BA.debugLine="snack.Initialize(\"RetryDownloadBookPCA\", Activit";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"RetryDownloadBookPCA",(android.view.View)(parent.mostCurrent._activity.getObject()),("Unable to save PCA Data due to ")+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),parent.mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 1017;BA.debugLine="snack.SetAction(\"Retry\")";
parent.mostCurrent._snack.SetAction("Retry");
 //BA.debugLineNum = 1018;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1019;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1020;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 1021;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("12949163",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 if (true) break;
if (true) break;

case 28:
//C
this.state = -1;
this.catchState = 0;
;
 //BA.debugLineNum = 1023;BA.debugLine="End Sub";
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
public static void  _savecustomeraccounts(String _sreturnval) throws Exception{
ResumableSub_SaveCustomerAccounts rsub = new ResumableSub_SaveCustomerAccounts(null,_sreturnval);
rsub.resume(processBA, null);
}
public static class ResumableSub_SaveCustomerAccounts extends BA.ResumableSub {
public ResumableSub_SaveCustomerAccounts(com.bwsi.MeterReader.datasyncing parent,String _sreturnval) {
this.parent = parent;
this._sreturnval = _sreturnval;
}
com.bwsi.MeterReader.datasyncing parent;
String _sreturnval;
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.List _root = null;
String _sseqno = "";
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
 //BA.debugLineNum = 848;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 849;BA.debugLine="Dim root As List";
_root = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 850;BA.debugLine="Dim sSeqNo As String";
_sseqno = "";
 //BA.debugLineNum = 852;BA.debugLine="parser.Initialize(sReturnVal)";
_parser.Initialize(_sreturnval);
 //BA.debugLineNum = 853;BA.debugLine="root = parser.NextArray";
_root = _parser.NextArray();
 //BA.debugLineNum = 855;BA.debugLine="Try";
if (true) break;

case 1:
//try
this.state = 32;
this.catchState = 31;
this.state = 3;
if (true) break;

case 3:
//C
this.state = 4;
this.catchState = 31;
 //BA.debugLineNum = 856;BA.debugLine="For Each MP As Map In root";
if (true) break;

case 4:
//for
this.state = 23;
_mp = new anywheresoftware.b4a.objects.collections.Map();
group7 = _root;
index7 = 0;
groupLen7 = group7.getSize();
this.state = 33;
if (true) break;

case 33:
//C
this.state = 23;
if (index7 < groupLen7) {
this.state = 6;
_mp = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(group7.Get(index7)));}
if (true) break;

case 34:
//C
this.state = 33;
index7++;
if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 857;BA.debugLine="If GlobalVar.SF.Len(MP.Get(\"SeqNo\")) >= 4 Then";
if (true) break;

case 7:
//if
this.state = 16;
if (parent.mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(BA.ObjectToString(_mp.Get((Object)("SeqNo"))))>=4) { 
this.state = 9;
}else if(parent.mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(BA.ObjectToString(_mp.Get((Object)("SeqNo"))))==3) { 
this.state = 11;
}else if(parent.mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(BA.ObjectToString(_mp.Get((Object)("SeqNo"))))==2) { 
this.state = 13;
}else if(parent.mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvv7(BA.ObjectToString(_mp.Get((Object)("SeqNo"))))==1) { 
this.state = 15;
}if (true) break;

case 9:
//C
this.state = 16;
 //BA.debugLineNum = 858;BA.debugLine="sSeqNo = MP.Get(\"SeqNo\")";
_sseqno = BA.ObjectToString(_mp.Get((Object)("SeqNo")));
 if (true) break;

case 11:
//C
this.state = 16;
 //BA.debugLineNum = 860;BA.debugLine="sSeqNo = \"0\" & MP.Get(\"SeqNo\")";
_sseqno = "0"+BA.ObjectToString(_mp.Get((Object)("SeqNo")));
 if (true) break;

case 13:
//C
this.state = 16;
 //BA.debugLineNum = 862;BA.debugLine="sSeqNo = \"00\" & MP.Get(\"SeqNo\")";
_sseqno = "00"+BA.ObjectToString(_mp.Get((Object)("SeqNo")));
 if (true) break;

case 15:
//C
this.state = 16;
 //BA.debugLineNum = 864;BA.debugLine="sSeqNo = \"000\" & MP.Get(\"SeqNo\")";
_sseqno = "000"+BA.ObjectToString(_mp.Get((Object)("SeqNo")));
 if (true) break;

case 16:
//C
this.state = 17;
;
 //BA.debugLineNum = 866;BA.debugLine="Starter.strCriteria=\"INSERT INTO tblReadings VA";
parent.mostCurrent._starter._strcriteria /*String*/  = "INSERT INTO tblReadings VALUES ("+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Null)+", ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
 //BA.debugLineNum = 867;BA.debugLine="If GlobalVar.BranchID = 28 Or GlobalVar.BranchI";
if (true) break;

case 17:
//if
this.state = 22;
if (parent.mostCurrent._globalvar._branchid /*int*/ ==28 || parent.mostCurrent._globalvar._branchid /*int*/ ==29 || parent.mostCurrent._globalvar._branchid /*int*/ ==30) { 
this.state = 19;
}else {
this.state = 21;
}if (true) break;

case 19:
//C
this.state = 22;
 //BA.debugLineNum = 868;BA.debugLine="Starter.DBCon.AddNonQueryToBatch(Starter.strCr";
parent.mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .AddNonQueryToBatch(parent.mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{_mp.Get((Object)("BillNo")),_mp.Get((Object)("BillYear")),_mp.Get((Object)("BillMonth")),_mp.Get((Object)("BranchID")),_mp.Get((Object)("BookID")),_mp.Get((Object)("BookCode")),_mp.Get((Object)("AcctID")),_mp.Get((Object)("AcctNo")),_mp.Get((Object)("OldAcctNo")),_mp.Get((Object)("AcctName")),_mp.Get((Object)("AcctAddress")),_mp.Get((Object)("AcctClass")),_mp.Get((Object)("AcctSubClass")),_mp.Get((Object)("AcctStatus")),_mp.Get((Object)("MeterID")),_mp.Get((Object)("MeterNo")),_mp.Get((Object)("MaxReading")),(Object)(_sseqno),_mp.Get((Object)("IsSenior")),_mp.Get((Object)("SeniorOnBefore")),_mp.Get((Object)("SeniorAfter")),_mp.Get((Object)("SeniorMaxCum")),_mp.Get((Object)("GDeposit")),_mp.Get((Object)("PrevRdgDate")),_mp.Get((Object)("PrevRdg")),_mp.Get((Object)("PrevCum")),_mp.Get((Object)("BillPeriod1st")),_mp.Get((Object)("PrevCum1st")),_mp.Get((Object)("BillPeriod2nd")),_mp.Get((Object)("PrevCum2nd")),_mp.Get((Object)("BillPeriod3rd")),_mp.Get((Object)("PrevCum3rd")),_mp.Get((Object)("FinalRdg")),_mp.Get((Object)("DisconDate")),_mp.Get((Object)("PresRdgDate")),(Object)(("")),(Object)(("")),(Object)(("")),_mp.Get((Object)("DateFrom")),_mp.Get((Object)("DateTo")),_mp.Get((Object)("WithDueDate")),_mp.Get((Object)("DueDate")),_mp.Get((Object)("DisconnectionDate")),_mp.Get((Object)("AveCum")),_mp.Get((Object)("BillType")),_mp.Get((Object)("AddCum")),(Object)(("0")),(Object)(("0.00")),(Object)(("0.00")),(Object)(("0.00")),_mp.Get((Object)("AddToBill")),_mp.Get((Object)("AtbRef")),_mp.Get((Object)("MeterCharges")),_mp.Get((Object)("FranchiseTaxPct")),(Object)(("0.00")),_mp.Get((Object)("HasSeptageFee")),_mp.Get((Object)("MinSeptageCum")),_mp.Get((Object)("MaxSeptageCum")),_mp.Get((Object)("SeptageRate")),_mp.Get((Object)("SeptageArrears")),(Object)(("0.00")),(Object)(("0.00")),(Object)(("0.00")),_mp.Get((Object)("Arrears")),(Object)(("0.00")),(Object)(("0.00")),(Object)(("0.00")),(Object)(("0.00")),_mp.Get((Object)("AdvPayment")),_mp.Get((Object)("PenaltyPct")),(Object)(("0.00")),(Object)(("0.00")),(Object)(("0.00")),(Object)(("")),(Object)(("0")),(Object)(("0")),(Object)(("0")),(Object)(("0")),(Object)(("0")),(Object)(("0")),(Object)(("")),(Object)(("0")),(Object)(("")),(Object)(("0")),(Object)(("0")),(Object)(("0")),(Object)(("")),(Object)(("")),_mp.Get((Object)("RdgSequence")),(Object)(("0")),(Object)(("")),(Object)(("")),(Object)(parent._readerid),(Object)(("0")),(Object)((""))}));
 if (true) break;

case 21:
//C
this.state = 22;
 //BA.debugLineNum = 870;BA.debugLine="Starter.DBCon.AddNonQueryToBatch(Starter.strCr";
parent.mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .AddNonQueryToBatch(parent.mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{_mp.Get((Object)("BillNo")),_mp.Get((Object)("BillYear")),_mp.Get((Object)("BillMonth")),_mp.Get((Object)("BranchID")),_mp.Get((Object)("BookID")),_mp.Get((Object)("BookCode")),_mp.Get((Object)("AcctID")),_mp.Get((Object)("AcctNo")),_mp.Get((Object)("OldAcctNo")),_mp.Get((Object)("AcctName")),_mp.Get((Object)("AcctAddress")),_mp.Get((Object)("AcctClass")),_mp.Get((Object)("AcctSubClass")),_mp.Get((Object)("AcctStatus")),_mp.Get((Object)("MeterID")),_mp.Get((Object)("MeterNo")),_mp.Get((Object)("MaxReading")),(Object)(_sseqno),_mp.Get((Object)("IsSenior")),_mp.Get((Object)("SeniorOnBefore")),_mp.Get((Object)("SeniorAfter")),_mp.Get((Object)("SeniorMaxCum")),_mp.Get((Object)("GDeposit")),_mp.Get((Object)("PrevRdgDate")),_mp.Get((Object)("PrevRdg")),_mp.Get((Object)("PrevCum")),_mp.Get((Object)("BillPeriod1st")),_mp.Get((Object)("PrevCum1st")),_mp.Get((Object)("BillPeriod2nd")),_mp.Get((Object)("PrevCum2nd")),_mp.Get((Object)("BillPeriod3rd")),_mp.Get((Object)("PrevCum3rd")),_mp.Get((Object)("FinalRdg")),_mp.Get((Object)("DisconDate")),_mp.Get((Object)("PresRdgDate")),(Object)(("")),(Object)(("")),(Object)(("")),_mp.Get((Object)("DateFrom")),_mp.Get((Object)("DateTo")),_mp.Get((Object)("WithDueDate")),_mp.Get((Object)("DueDate")),_mp.Get((Object)("DisconnectionDate")),_mp.Get((Object)("AveCum")),_mp.Get((Object)("BillType")),_mp.Get((Object)("AddCum")),(Object)(("0")),(Object)(("0.00")),(Object)(("0.00")),(Object)(("0.00")),_mp.Get((Object)("AddToBill")),_mp.Get((Object)("AtbRef")),_mp.Get((Object)("MeterCharges")),_mp.Get((Object)("FranchiseTaxPct")),(Object)(("0.00")),_mp.Get((Object)("HasSeptageFee")),_mp.Get((Object)("MinSeptageCum")),_mp.Get((Object)("MaxSeptageCum")),_mp.Get((Object)("SeptageRate")),(Object)(("0.00")),(Object)(("0.00")),(Object)(("0.00")),(Object)(("0.00")),_mp.Get((Object)("Arrears")),(Object)(("0.00")),(Object)(("0.00")),(Object)(("0.00")),(Object)(("0.00")),_mp.Get((Object)("AdvPayment")),_mp.Get((Object)("PenaltyPct")),(Object)(("0.00")),(Object)(("0.00")),(Object)(("0.00")),(Object)(("")),(Object)(("0")),(Object)(("0")),(Object)(("0")),(Object)(("0")),(Object)(("0")),(Object)(("0")),(Object)(("")),(Object)(("0")),(Object)(("")),(Object)(("0")),(Object)(("0")),(Object)(("0")),(Object)(("")),(Object)(("")),_mp.Get((Object)("RdgSequence")),(Object)(("0")),(Object)(("")),(Object)(("")),(Object)(parent._readerid),(Object)(("0"))}));
 if (true) break;

case 22:
//C
this.state = 34;
;
 if (true) break;
if (true) break;

case 23:
//C
this.state = 24;
;
 //BA.debugLineNum = 873;BA.debugLine="Dim SenderFilter As Object = Starter.DBCon.ExecN";
_senderfilter = parent.mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQueryBatch(processBA,"SQL");
 //BA.debugLineNum = 874;BA.debugLine="Wait For (SenderFilter) SQL_NonQueryComplete (Su";
anywheresoftware.b4a.keywords.Common.WaitFor("sql_nonquerycomplete", processBA, this, _senderfilter);
this.state = 35;
return;
case 35:
//C
this.state = 24;
_success = (Boolean) result[0];
;
 //BA.debugLineNum = 875;BA.debugLine="If Success Then";
if (true) break;

case 24:
//if
this.state = 29;
if (_success) { 
this.state = 26;
}else {
this.state = 28;
}if (true) break;

case 26:
//C
this.state = 29;
 //BA.debugLineNum = 876;BA.debugLine="snack.Initialize(\"\", Activity, $\"Customer Accou";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(parent.mostCurrent._activity.getObject()),("Customer Account(s) were successfully downloaded."),parent.mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 877;BA.debugLine="SetSnackBarBackground(snack,Colors.White)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 878;BA.debugLine="SetSnackBarTextColor(snack, GlobalVar.PriColor)";
_setsnackbartextcolor(parent.mostCurrent._snack,(int) (parent.mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 879;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 880;BA.debugLine="DeleteRateHeader";
_deleterateheader();
 //BA.debugLineNum = 881;BA.debugLine="DownloadRateHeader(GlobalVar.BranchID)";
_downloadrateheader(parent.mostCurrent._globalvar._branchid /*int*/ );
 if (true) break;

case 28:
//C
this.state = 29;
 //BA.debugLineNum = 883;BA.debugLine="snack.Initialize(\"RetryDownloadCustomers\", Acti";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"RetryDownloadCustomers",(android.view.View)(parent.mostCurrent._activity.getObject()),("Unable to save Customer Account(s) due to ")+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),parent.mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 884;BA.debugLine="snack.SetAction(\"Retry\")";
parent.mostCurrent._snack.SetAction("Retry");
 //BA.debugLineNum = 885;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 886;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 887;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 888;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("12752553",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 if (true) break;

case 29:
//C
this.state = 32;
;
 if (true) break;

case 31:
//C
this.state = 32;
this.catchState = 0;
 //BA.debugLineNum = 891;BA.debugLine="snack.Initialize(\"RetryDownloadCustomers\", Activ";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"RetryDownloadCustomers",(android.view.View)(parent.mostCurrent._activity.getObject()),("Unable to save book assignment(s) due to ")+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),parent.mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 892;BA.debugLine="snack.SetAction(\"Retry\")";
parent.mostCurrent._snack.SetAction("Retry");
 //BA.debugLineNum = 893;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 894;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 895;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 896;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("12752561",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 if (true) break;
if (true) break;

case 32:
//C
this.state = -1;
this.catchState = 0;
;
 //BA.debugLineNum = 898;BA.debugLine="End Sub";
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
public static String  _savejson(int _ibookid,String _stext) throws Exception{
 //BA.debugLineNum = 1932;BA.debugLine="Private Sub SaveJSON(iBookID As Int, sText As Stri";
 //BA.debugLineNum = 1933;BA.debugLine="Try";
try { //BA.debugLineNum = 1934;BA.debugLine="Starter.DBCon.BeginTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .BeginTransaction();
 //BA.debugLineNum = 1935;BA.debugLine="Starter.strCriteria=\"UPDATE tblBookAssignments S";
mostCurrent._starter._strcriteria /*String*/  = "UPDATE tblBookAssignments SET JSONText = ? WHERE BookID = "+BA.NumberToString(_ibookid)+" AND BillYear = "+BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ )+" AND BillMonth = "+BA.NumberToString(mostCurrent._globalvar._billmonth /*int*/ );
 //BA.debugLineNum = 1936;BA.debugLine="Starter.DBCon.ExecNonQuery2(Starter.strCriteria,";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2(mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{_stext}));
 //BA.debugLineNum = 1937;BA.debugLine="Starter.DBCon.TransactionSuccessful";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .TransactionSuccessful();
 } 
       catch (Exception e7) {
			processBA.setLastException(e7); //BA.debugLineNum = 1939;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("15963783",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 1941;BA.debugLine="Starter.DBCon.EndTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .EndTransaction();
 //BA.debugLineNum = 1942;BA.debugLine="End Sub";
return "";
}
public static void  _saveratedetails(String _sreturnval) throws Exception{
ResumableSub_SaveRateDetails rsub = new ResumableSub_SaveRateDetails(null,_sreturnval);
rsub.resume(processBA, null);
}
public static class ResumableSub_SaveRateDetails extends BA.ResumableSub {
public ResumableSub_SaveRateDetails(com.bwsi.MeterReader.datasyncing parent,String _sreturnval) {
this.parent = parent;
this._sreturnval = _sreturnval;
}
com.bwsi.MeterReader.datasyncing parent;
String _sreturnval;
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.List _root = null;
anywheresoftware.b4a.objects.collections.Map _mp = null;
Object _senderfilter = null;
boolean _success = false;
anywheresoftware.b4a.BA.IterableList group6;
int index6;
int groupLen6;

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
 //BA.debugLineNum = 940;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 941;BA.debugLine="Dim root As List";
_root = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 943;BA.debugLine="parser.Initialize(sReturnVal)";
_parser.Initialize(_sreturnval);
 //BA.debugLineNum = 944;BA.debugLine="root = parser.NextArray";
_root = _parser.NextArray();
 //BA.debugLineNum = 946;BA.debugLine="Try";
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
 //BA.debugLineNum = 947;BA.debugLine="For Each MP As Map In root";
if (true) break;

case 4:
//for
this.state = 7;
_mp = new anywheresoftware.b4a.objects.collections.Map();
group6 = _root;
index6 = 0;
groupLen6 = group6.getSize();
this.state = 17;
if (true) break;

case 17:
//C
this.state = 7;
if (index6 < groupLen6) {
this.state = 6;
_mp = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(group6.Get(index6)));}
if (true) break;

case 18:
//C
this.state = 17;
index6++;
if (true) break;

case 6:
//C
this.state = 18;
 //BA.debugLineNum = 948;BA.debugLine="Starter.strCriteria=\"INSERT INTO rates_details";
parent.mostCurrent._starter._strcriteria /*String*/  = "INSERT INTO rates_details VALUES ("+BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Null)+", ?, ?, ?, ?, ?)";
 //BA.debugLineNum = 949;BA.debugLine="Starter.DBCon.AddNonQueryToBatch(Starter.strCri";
parent.mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .AddNonQueryToBatch(parent.mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{_mp.Get((Object)("header_id")),_mp.Get((Object)("rangefrom")),_mp.Get((Object)("rangeto")),_mp.Get((Object)("typecust")),_mp.Get((Object)("amount"))}));
 if (true) break;
if (true) break;

case 7:
//C
this.state = 8;
;
 //BA.debugLineNum = 951;BA.debugLine="Dim SenderFilter As Object = Starter.DBCon.ExecN";
_senderfilter = parent.mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQueryBatch(processBA,"SQL");
 //BA.debugLineNum = 952;BA.debugLine="Wait For (SenderFilter) SQL_NonQueryComplete (Su";
anywheresoftware.b4a.keywords.Common.WaitFor("sql_nonquerycomplete", processBA, this, _senderfilter);
this.state = 19;
return;
case 19:
//C
this.state = 8;
_success = (Boolean) result[0];
;
 //BA.debugLineNum = 953;BA.debugLine="If Success Then";
if (true) break;

case 8:
//if
this.state = 13;
if (_success) { 
this.state = 10;
}else {
this.state = 12;
}if (true) break;

case 10:
//C
this.state = 13;
 //BA.debugLineNum = 954;BA.debugLine="snack.Initialize(\"\", Activity, $\"Rate Details w";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(parent.mostCurrent._activity.getObject()),("Rate Details were successfully downloaded."),parent.mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 955;BA.debugLine="SetSnackBarBackground(snack,Colors.White)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 956;BA.debugLine="SetSnackBarTextColor(snack, GlobalVar.PriColor)";
_setsnackbartextcolor(parent.mostCurrent._snack,(int) (parent.mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 957;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 958;BA.debugLine="DeletePCA";
_deletepca();
 //BA.debugLineNum = 959;BA.debugLine="DownloadBookPCA(GlobalVar.BranchID, strRdgDate)";
_downloadbookpca(parent.mostCurrent._globalvar._branchid /*int*/ ,parent.mostCurrent._strrdgdate);
 if (true) break;

case 12:
//C
this.state = 13;
 //BA.debugLineNum = 961;BA.debugLine="snack.Initialize(\"RetryDownloadRateDetails\", Ac";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"RetryDownloadRateDetails",(android.view.View)(parent.mostCurrent._activity.getObject()),("Unable to save Rate Detail(s) due to ")+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),parent.mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 962;BA.debugLine="snack.SetAction(\"Retry\")";
parent.mostCurrent._snack.SetAction("Retry");
 //BA.debugLineNum = 963;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 964;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 965;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 966;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("12883611",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 if (true) break;

case 13:
//C
this.state = 16;
;
 if (true) break;

case 15:
//C
this.state = 16;
this.catchState = 0;
 //BA.debugLineNum = 969;BA.debugLine="snack.Initialize(\"RetryDownloadRateDetails\", Act";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"RetryDownloadRateDetails",(android.view.View)(parent.mostCurrent._activity.getObject()),("Unable to save Rate Detail(s) due to ")+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),parent.mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 970;BA.debugLine="snack.SetAction(\"Retry\")";
parent.mostCurrent._snack.SetAction("Retry");
 //BA.debugLineNum = 971;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 972;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 973;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 974;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("12883619",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 if (true) break;
if (true) break;

case 16:
//C
this.state = -1;
this.catchState = 0;
;
 //BA.debugLineNum = 976;BA.debugLine="End Sub";
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
public static void  _saverateheader(String _sreturnval) throws Exception{
ResumableSub_SaveRateHeader rsub = new ResumableSub_SaveRateHeader(null,_sreturnval);
rsub.resume(processBA, null);
}
public static class ResumableSub_SaveRateHeader extends BA.ResumableSub {
public ResumableSub_SaveRateHeader(com.bwsi.MeterReader.datasyncing parent,String _sreturnval) {
this.parent = parent;
this._sreturnval = _sreturnval;
}
com.bwsi.MeterReader.datasyncing parent;
String _sreturnval;
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.List _root = null;
anywheresoftware.b4a.objects.collections.Map _mp = null;
Object _senderfilter = null;
boolean _success = false;
anywheresoftware.b4a.BA.IterableList group6;
int index6;
int groupLen6;

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
 //BA.debugLineNum = 901;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 902;BA.debugLine="Dim root As List";
_root = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 904;BA.debugLine="parser.Initialize(sReturnVal)";
_parser.Initialize(_sreturnval);
 //BA.debugLineNum = 905;BA.debugLine="root = parser.NextArray";
_root = _parser.NextArray();
 //BA.debugLineNum = 907;BA.debugLine="Try";
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
 //BA.debugLineNum = 908;BA.debugLine="For Each MP As Map In root";
if (true) break;

case 4:
//for
this.state = 7;
_mp = new anywheresoftware.b4a.objects.collections.Map();
group6 = _root;
index6 = 0;
groupLen6 = group6.getSize();
this.state = 17;
if (true) break;

case 17:
//C
this.state = 7;
if (index6 < groupLen6) {
this.state = 6;
_mp = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(group6.Get(index6)));}
if (true) break;

case 18:
//C
this.state = 17;
index6++;
if (true) break;

case 6:
//C
this.state = 18;
 //BA.debugLineNum = 909;BA.debugLine="Starter.strCriteria=\"INSERT INTO rates_header V";
parent.mostCurrent._starter._strcriteria /*String*/  = "INSERT INTO rates_header VALUES (?, ?, ?, ?, ?, ?, ?)";
 //BA.debugLineNum = 910;BA.debugLine="Starter.DBCon.AddNonQueryToBatch(Starter.strCri";
parent.mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .AddNonQueryToBatch(parent.mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{_mp.Get((Object)("id")),_mp.Get((Object)("branch_id")),_mp.Get((Object)("class")),_mp.Get((Object)("sub_class")),_mp.Get((Object)("is_active")),_mp.Get((Object)("date_from")),_mp.Get((Object)("date_to"))}));
 if (true) break;
if (true) break;

case 7:
//C
this.state = 8;
;
 //BA.debugLineNum = 912;BA.debugLine="Dim SenderFilter As Object = Starter.DBCon.ExecN";
_senderfilter = parent.mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQueryBatch(processBA,"SQL");
 //BA.debugLineNum = 913;BA.debugLine="Wait For (SenderFilter) SQL_NonQueryComplete (Su";
anywheresoftware.b4a.keywords.Common.WaitFor("sql_nonquerycomplete", processBA, this, _senderfilter);
this.state = 19;
return;
case 19:
//C
this.state = 8;
_success = (Boolean) result[0];
;
 //BA.debugLineNum = 914;BA.debugLine="If Success Then";
if (true) break;

case 8:
//if
this.state = 13;
if (_success) { 
this.state = 10;
}else {
this.state = 12;
}if (true) break;

case 10:
//C
this.state = 13;
 //BA.debugLineNum = 915;BA.debugLine="snack.Initialize(\"\", Activity, $\"Rate Master we";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(parent.mostCurrent._activity.getObject()),("Rate Master were successfully downloaded."),parent.mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 916;BA.debugLine="SetSnackBarBackground(snack,Colors.White)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 917;BA.debugLine="SetSnackBarTextColor(snack, GlobalVar.PriColor)";
_setsnackbartextcolor(parent.mostCurrent._snack,(int) (parent.mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 918;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 919;BA.debugLine="DeleteRateDetails";
_deleteratedetails();
 //BA.debugLineNum = 920;BA.debugLine="DownloadRateDetails(GlobalVar.BranchID)";
_downloadratedetails(parent.mostCurrent._globalvar._branchid /*int*/ );
 if (true) break;

case 12:
//C
this.state = 13;
 //BA.debugLineNum = 922;BA.debugLine="snack.Initialize(\"RetryDownloadRateHeader\", Act";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"RetryDownloadRateHeader",(android.view.View)(parent.mostCurrent._activity.getObject()),("Unable to save Rate Master due to ")+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),parent.mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 923;BA.debugLine="snack.SetAction(\"Retry\")";
parent.mostCurrent._snack.SetAction("Retry");
 //BA.debugLineNum = 924;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 925;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 926;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 927;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("12818075",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 if (true) break;

case 13:
//C
this.state = 16;
;
 if (true) break;

case 15:
//C
this.state = 16;
this.catchState = 0;
 //BA.debugLineNum = 930;BA.debugLine="snack.Initialize(\"RetryDownloadRateHeader\", Acti";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"RetryDownloadRateHeader",(android.view.View)(parent.mostCurrent._activity.getObject()),("Unable to save Rate Master due to ")+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),parent.mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 931;BA.debugLine="snack.SetAction(\"Retry\")";
parent.mostCurrent._snack.SetAction("Retry");
 //BA.debugLineNum = 932;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 933;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 934;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 935;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("12818083",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 if (true) break;
if (true) break;

case 16:
//C
this.state = -1;
this.catchState = 0;
;
 //BA.debugLineNum = 937;BA.debugLine="End Sub";
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
public static void  _savereaderinfo(String _sreturnval) throws Exception{
ResumableSub_SaveReaderInfo rsub = new ResumableSub_SaveReaderInfo(null,_sreturnval);
rsub.resume(processBA, null);
}
public static class ResumableSub_SaveReaderInfo extends BA.ResumableSub {
public ResumableSub_SaveReaderInfo(com.bwsi.MeterReader.datasyncing parent,String _sreturnval) {
this.parent = parent;
this._sreturnval = _sreturnval;
}
com.bwsi.MeterReader.datasyncing parent;
String _sreturnval;
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.List _root = null;
anywheresoftware.b4a.objects.collections.Map _mp = null;
Object _senderfilter = null;
boolean _success = false;
anywheresoftware.b4a.BA.IterableList group6;
int index6;
int groupLen6;

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
 //BA.debugLineNum = 769;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 770;BA.debugLine="Dim root As List";
_root = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 772;BA.debugLine="parser.Initialize(sReturnVal)";
_parser.Initialize(_sreturnval);
 //BA.debugLineNum = 773;BA.debugLine="root = parser.NextArray";
_root = _parser.NextArray();
 //BA.debugLineNum = 775;BA.debugLine="Try";
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
 //BA.debugLineNum = 776;BA.debugLine="For Each MP As Map In root";
if (true) break;

case 4:
//for
this.state = 7;
_mp = new anywheresoftware.b4a.objects.collections.Map();
group6 = _root;
index6 = 0;
groupLen6 = group6.getSize();
this.state = 17;
if (true) break;

case 17:
//C
this.state = 7;
if (index6 < groupLen6) {
this.state = 6;
_mp = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(group6.Get(index6)));}
if (true) break;

case 18:
//C
this.state = 17;
index6++;
if (true) break;

case 6:
//C
this.state = 18;
 //BA.debugLineNum = 777;BA.debugLine="Starter.strCriteria=\"INSERT INTO tblUsers VALUE";
parent.mostCurrent._starter._strcriteria /*String*/  = "INSERT INTO tblUsers VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
 //BA.debugLineNum = 778;BA.debugLine="Starter.DBCon.AddNonQueryToBatch(Starter.strCri";
parent.mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .AddNonQueryToBatch(parent.mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{_mp.Get((Object)("UserID")),_mp.Get((Object)("BranchID")),_mp.Get((Object)("EmpName")),_mp.Get((Object)("UserName")),_mp.Get((Object)("UserPassword")),_mp.Get((Object)("Module1")),_mp.Get((Object)("Module2")),_mp.Get((Object)("Module3")),_mp.Get((Object)("Module4")),_mp.Get((Object)("Module5")),_mp.Get((Object)("Module6")),(Object)(("1"))}));
 if (true) break;
if (true) break;

case 7:
//C
this.state = 8;
;
 //BA.debugLineNum = 780;BA.debugLine="Dim SenderFilter As Object = Starter.DBCon.ExecN";
_senderfilter = parent.mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQueryBatch(processBA,"SQL");
 //BA.debugLineNum = 781;BA.debugLine="Wait For (SenderFilter) SQL_NonQueryComplete (Su";
anywheresoftware.b4a.keywords.Common.WaitFor("sql_nonquerycomplete", processBA, this, _senderfilter);
this.state = 19;
return;
case 19:
//C
this.state = 8;
_success = (Boolean) result[0];
;
 //BA.debugLineNum = 782;BA.debugLine="If Success Then";
if (true) break;

case 8:
//if
this.state = 13;
if (_success) { 
this.state = 10;
}else {
this.state = 12;
}if (true) break;

case 10:
//C
this.state = 13;
 //BA.debugLineNum = 783;BA.debugLine="snack.Initialize(\"\", Activity, $\"Specified Read";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(parent.mostCurrent._activity.getObject()),("Specified Reader Info were successfully downloaded."),parent.mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 784;BA.debugLine="SetSnackBarBackground(snack,Colors.White)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 785;BA.debugLine="SetSnackBarTextColor(snack, GlobalVar.PriColor)";
_setsnackbartextcolor(parent.mostCurrent._snack,(int) (parent.mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 786;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 787;BA.debugLine="DeleteAssignments";
_deleteassignments();
 //BA.debugLineNum = 788;BA.debugLine="DownloadBookAssignments(GlobalVar.BranchID, Glo";
_downloadbookassignments(parent.mostCurrent._globalvar._branchid /*int*/ ,(int) (parent.mostCurrent._globalvar._billyear /*double*/ ),parent.mostCurrent._globalvar._billmonth /*int*/ ,parent._readerid,parent.mostCurrent._strrdgdate);
 if (true) break;

case 12:
//C
this.state = 13;
 //BA.debugLineNum = 790;BA.debugLine="snack.Initialize(\"RetryDownloadEmployeeInfo\", A";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"RetryDownloadEmployeeInfo",(android.view.View)(parent.mostCurrent._activity.getObject()),("Unable to Reader Info due to ")+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),parent.mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 791;BA.debugLine="snack.SetAction(\"Retry\")";
parent.mostCurrent._snack.SetAction("Retry");
 //BA.debugLineNum = 792;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 793;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 794;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 795;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("12621467",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 if (true) break;

case 13:
//C
this.state = 16;
;
 if (true) break;

case 15:
//C
this.state = 16;
this.catchState = 0;
 //BA.debugLineNum = 798;BA.debugLine="snack.Initialize(\"RetryDownloadEmployeeInfo\", Ac";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"RetryDownloadEmployeeInfo",(android.view.View)(parent.mostCurrent._activity.getObject()),("Unable to Reader Info due to ")+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),parent.mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 799;BA.debugLine="snack.SetAction(\"Retry\")";
parent.mostCurrent._snack.SetAction("Retry");
 //BA.debugLineNum = 800;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 801;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 802;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 803;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("12621475",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 if (true) break;
if (true) break;

case 16:
//C
this.state = -1;
this.catchState = 0;
;
 //BA.debugLineNum = 805;BA.debugLine="End Sub";
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
public static void  _savessmrates(String _sreturnval) throws Exception{
ResumableSub_SaveSSMRates rsub = new ResumableSub_SaveSSMRates(null,_sreturnval);
rsub.resume(processBA, null);
}
public static class ResumableSub_SaveSSMRates extends BA.ResumableSub {
public ResumableSub_SaveSSMRates(com.bwsi.MeterReader.datasyncing parent,String _sreturnval) {
this.parent = parent;
this._sreturnval = _sreturnval;
}
com.bwsi.MeterReader.datasyncing parent;
String _sreturnval;
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.List _root = null;
anywheresoftware.b4a.objects.collections.Map _mp = null;
Object _senderfilter = null;
boolean _success = false;
anywheresoftware.b4a.BA.IterableList group6;
int index6;
int groupLen6;

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
 //BA.debugLineNum = 1026;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 1027;BA.debugLine="Dim root As List";
_root = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 1029;BA.debugLine="parser.Initialize(sReturnVal)";
_parser.Initialize(_sreturnval);
 //BA.debugLineNum = 1030;BA.debugLine="root = parser.NextArray";
_root = _parser.NextArray();
 //BA.debugLineNum = 1032;BA.debugLine="Try";
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
 //BA.debugLineNum = 1033;BA.debugLine="For Each MP As Map In root";
if (true) break;

case 4:
//for
this.state = 7;
_mp = new anywheresoftware.b4a.objects.collections.Map();
group6 = _root;
index6 = 0;
groupLen6 = group6.getSize();
this.state = 17;
if (true) break;

case 17:
//C
this.state = 7;
if (index6 < groupLen6) {
this.state = 6;
_mp = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(group6.Get(index6)));}
if (true) break;

case 18:
//C
this.state = 17;
index6++;
if (true) break;

case 6:
//C
this.state = 18;
 //BA.debugLineNum = 1034;BA.debugLine="Starter.strCriteria=\"INSERT INTO SSMRates VALUE";
parent.mostCurrent._starter._strcriteria /*String*/  = "INSERT INTO SSMRates VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
 //BA.debugLineNum = 1035;BA.debugLine="Starter.DBCon.AddNonQueryToBatch(Starter.strCri";
parent.mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .AddNonQueryToBatch(parent.mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{_mp.Get((Object)("SeptageRatesID")),_mp.Get((Object)("BranchID")),_mp.Get((Object)("AcctClassification")),_mp.Get((Object)("MinCum")),_mp.Get((Object)("MaxCum")),_mp.Get((Object)("RateType")),_mp.Get((Object)("RatePerCum")),_mp.Get((Object)("CutOff"))}));
 if (true) break;
if (true) break;

case 7:
//C
this.state = 8;
;
 //BA.debugLineNum = 1037;BA.debugLine="Dim SenderFilter As Object = Starter.DBCon.ExecN";
_senderfilter = parent.mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQueryBatch(processBA,"SQL");
 //BA.debugLineNum = 1038;BA.debugLine="Wait For (SenderFilter) SQL_NonQueryComplete (Su";
anywheresoftware.b4a.keywords.Common.WaitFor("sql_nonquerycomplete", processBA, this, _senderfilter);
this.state = 19;
return;
case 19:
//C
this.state = 8;
_success = (Boolean) result[0];
;
 //BA.debugLineNum = 1039;BA.debugLine="If Success Then";
if (true) break;

case 8:
//if
this.state = 13;
if (_success) { 
this.state = 10;
}else {
this.state = 12;
}if (true) break;

case 10:
//C
this.state = 13;
 //BA.debugLineNum = 1040;BA.debugLine="snack.Initialize(\"\", Activity, $\"Septage Rates";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"",(android.view.View)(parent.mostCurrent._activity.getObject()),("Septage Rates Data were successfully downloaded."),parent.mostCurrent._snack.DURATION_SHORT);
 //BA.debugLineNum = 1041;BA.debugLine="SetSnackBarBackground(snack,Colors.White)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1042;BA.debugLine="SetSnackBarTextColor(snack, GlobalVar.PriColor)";
_setsnackbartextcolor(parent.mostCurrent._snack,(int) (parent.mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 1043;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 1044;BA.debugLine="ShowDLComplete($\"Download Complete\"$,$\"Reading";
_showdlcomplete(("Download Complete"),("Reading Data for the specified Reader were successfully downloaded."));
 if (true) break;

case 12:
//C
this.state = 13;
 //BA.debugLineNum = 1046;BA.debugLine="snack.Initialize(\"RetryDownloadSeptageRates\", A";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"RetryDownloadSeptageRates",(android.view.View)(parent.mostCurrent._activity.getObject()),("Unable to save Septage Rates Data due to ")+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),parent.mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 1047;BA.debugLine="snack.SetAction(\"Retry\")";
parent.mostCurrent._snack.SetAction("Retry");
 //BA.debugLineNum = 1048;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1049;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1050;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 1051;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("13014682",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 if (true) break;

case 13:
//C
this.state = 16;
;
 if (true) break;

case 15:
//C
this.state = 16;
this.catchState = 0;
 //BA.debugLineNum = 1054;BA.debugLine="snack.Initialize(\"RetryDownloadSeptageRates\", Ac";
parent.mostCurrent._snack.Initialize(mostCurrent.activityBA,"RetryDownloadSeptageRates",(android.view.View)(parent.mostCurrent._activity.getObject()),("Unable to save Septage Rates Data due to ")+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),parent.mostCurrent._snack.DURATION_LONG);
 //BA.debugLineNum = 1055;BA.debugLine="snack.SetAction(\"Retry\")";
parent.mostCurrent._snack.SetAction("Retry");
 //BA.debugLineNum = 1056;BA.debugLine="SetSnackBarBackground(snack, Colors.Red)";
_setsnackbarbackground(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1057;BA.debugLine="SetSnackBarTextColor(snack, Colors.White)";
_setsnackbartextcolor(parent.mostCurrent._snack,anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1058;BA.debugLine="snack.Show";
parent.mostCurrent._snack.Show();
 //BA.debugLineNum = 1059;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("13014690",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 if (true) break;
if (true) break;

case 16:
//C
this.state = -1;
this.catchState = 0;
;
 //BA.debugLineNum = 1061;BA.debugLine="End Sub";
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
public static String  _setreadingdate() throws Exception{
 //BA.debugLineNum = 243;BA.debugLine="Private Sub SetReadingDate";
 //BA.debugLineNum = 244;BA.debugLine="DateTime.DateFormat = \"MM/dd/yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("MM/dd/yyyy");
 //BA.debugLineNum = 245;BA.debugLine="lDate = DateTime.DateParse(DateTime.Date(DateTime";
_ldate = anywheresoftware.b4a.keywords.Common.DateTime.DateParse(anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow()));
 //BA.debugLineNum = 246;BA.debugLine="sRdgDate = DateTime.Date(lDate)";
mostCurrent._srdgdate = anywheresoftware.b4a.keywords.Common.DateTime.Date(_ldate);
 //BA.debugLineNum = 247;BA.debugLine="lblReadingDate.Text = sRdgDate";
mostCurrent._lblreadingdate.setText(BA.ObjectToCharSequence(mostCurrent._srdgdate));
 //BA.debugLineNum = 248;BA.debugLine="End Sub";
return "";
}
public static String  _setsnackbarbackground(de.amberhome.objects.SnackbarWrapper _psnack,int _pcolor) throws Exception{
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
 //BA.debugLineNum = 1105;BA.debugLine="Sub SetSnackBarBackground(pSnack As DSSnackbar, pC";
 //BA.debugLineNum = 1106;BA.debugLine="Dim v As View";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
 //BA.debugLineNum = 1107;BA.debugLine="v = pSnack.View";
_v = (anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(_psnack.getView()));
 //BA.debugLineNum = 1108;BA.debugLine="v.Color = pColor";
_v.setColor(_pcolor);
 //BA.debugLineNum = 1109;BA.debugLine="End Sub";
return "";
}
public static String  _setsnackbartextcolor(de.amberhome.objects.SnackbarWrapper _psnack,int _pcolor) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _p = null;
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
anywheresoftware.b4a.objects.LabelWrapper _textv = null;
 //BA.debugLineNum = 1111;BA.debugLine="Sub SetSnackBarTextColor(pSnack As DSSnackbar, pCo";
 //BA.debugLineNum = 1112;BA.debugLine="Dim p As Panel";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 1113;BA.debugLine="p = pSnack.View";
_p = (anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(_psnack.getView()));
 //BA.debugLineNum = 1114;BA.debugLine="For Each v As View In p.GetAllViewsRecursive";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
{
final anywheresoftware.b4a.BA.IterableList group3 = _p.GetAllViewsRecursive();
final int groupLen3 = group3.getSize()
;int index3 = 0;
;
for (; index3 < groupLen3;index3++){
_v = (anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(group3.Get(index3)));
 //BA.debugLineNum = 1115;BA.debugLine="If v Is Label Then";
if (_v.getObjectOrNull() instanceof android.widget.TextView) { 
 //BA.debugLineNum = 1116;BA.debugLine="Dim textv As Label";
_textv = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 1117;BA.debugLine="textv = v";
_textv = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_v.getObject()));
 //BA.debugLineNum = 1118;BA.debugLine="textv.TextColor = pColor";
_textv.setTextColor(_pcolor);
 //BA.debugLineNum = 1119;BA.debugLine="Exit";
if (true) break;
 };
 }
};
 //BA.debugLineNum = 1122;BA.debugLine="End Sub";
return "";
}
public static String  _showbackupdbasedialog() throws Exception{
anywheresoftware.b4a.objects.CSBuilder _cstitle = null;
anywheresoftware.b4a.objects.CSBuilder _cscontent = null;
 //BA.debugLineNum = 1832;BA.debugLine="Private Sub ShowBackUpDbaseDialog";
 //BA.debugLineNum = 1833;BA.debugLine="Dim csTitle, csContent As CSBuilder";
_cstitle = new anywheresoftware.b4a.objects.CSBuilder();
_cscontent = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 1834;BA.debugLine="csTitle.Initialize.Color(GlobalVar.PriColor).Bold";
_cstitle.Initialize().Color((int) (mostCurrent._globalvar._pricolor /*double*/ )).Bold().Size((int) (16)).Append(BA.ObjectToCharSequence(("CONFIRM BACK-UP"))).PopAll();
 //BA.debugLineNum = 1835;BA.debugLine="csContent.Initialize.Size(14).Color(GlobalVar.Pri";
_cscontent.Initialize().Size((int) (14)).Color((int) (mostCurrent._globalvar._pricolor /*double*/ )).Bold().Append(BA.ObjectToCharSequence(("Back-up Data before uploading?"))).PopAll();
 //BA.debugLineNum = 1837;BA.debugLine="MatDialogBuilder.Initialize(\"BackupDBase\")";
mostCurrent._matdialogbuilder.Initialize(mostCurrent.activityBA,"BackupDBase");
 //BA.debugLineNum = 1838;BA.debugLine="MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIG";
mostCurrent._matdialogbuilder.Theme(mostCurrent._matdialogbuilder.THEME_LIGHT);
 //BA.debugLineNum = 1839;BA.debugLine="MatDialogBuilder.Title(csTitle).TitleGravity(MatD";
mostCurrent._matdialogbuilder.Title(BA.ObjectToCharSequence(_cstitle.getObject())).TitleGravity(mostCurrent._matdialogbuilder.GRAVITY_START);
 //BA.debugLineNum = 1840;BA.debugLine="MatDialogBuilder.IconRes(GlobalVar.InfoIcon).Limi";
mostCurrent._matdialogbuilder.IconRes(mostCurrent._globalvar._infoicon /*String*/ ).LimitIconToDefaultSize();
 //BA.debugLineNum = 1841;BA.debugLine="MatDialogBuilder.Content(csContent)";
mostCurrent._matdialogbuilder.Content(BA.ObjectToCharSequence(_cscontent.getObject()));
 //BA.debugLineNum = 1842;BA.debugLine="MatDialogBuilder.PositiveText($\"YES\"$).PositiveCo";
mostCurrent._matdialogbuilder.PositiveText(BA.ObjectToCharSequence(("YES"))).PositiveColor((int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 1843;BA.debugLine="MatDialogBuilder.NegativeText($\"NO\"$).NegativeCol";
mostCurrent._matdialogbuilder.NegativeText(BA.ObjectToCharSequence(("NO"))).NegativeColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1844;BA.debugLine="MatDialogBuilder.CanceledOnTouchOutside(False)";
mostCurrent._matdialogbuilder.CanceledOnTouchOutside(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1845;BA.debugLine="MatDialogBuilder.Show";
mostCurrent._matdialogbuilder.Show();
 //BA.debugLineNum = 1846;BA.debugLine="End Sub";
return "";
}
public static String  _showcanceluploading() throws Exception{
anywheresoftware.b4a.objects.CSBuilder _cscontent = null;
 //BA.debugLineNum = 1689;BA.debugLine="Private Sub ShowCancelUploading";
 //BA.debugLineNum = 1690;BA.debugLine="Dim csContent As CSBuilder";
_cscontent = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 1691;BA.debugLine="csContent.Initialize.Size(14).Color(Colors.DarkGr";
_cscontent.Initialize().Size((int) (14)).Color(anywheresoftware.b4a.keywords.Common.Colors.DarkGray).Bold().Append(BA.ObjectToCharSequence(("Cancel Uploading Reading Data?"))).PopAll();
 //BA.debugLineNum = 1692;BA.debugLine="MatDialogBuilder.Initialize(\"CancelUL\")";
mostCurrent._matdialogbuilder.Initialize(mostCurrent.activityBA,"CancelUL");
 //BA.debugLineNum = 1693;BA.debugLine="MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIG";
mostCurrent._matdialogbuilder.Theme(mostCurrent._matdialogbuilder.THEME_LIGHT);
 //BA.debugLineNum = 1694;BA.debugLine="MatDialogBuilder.Title($\"CANCEL UPLOADING\"$).Titl";
mostCurrent._matdialogbuilder.Title(BA.ObjectToCharSequence(("CANCEL UPLOADING"))).TitleColor((int) (mostCurrent._globalvar._pricolor /*double*/ )).TitleGravity(mostCurrent._matdialogbuilder.GRAVITY_START);
 //BA.debugLineNum = 1695;BA.debugLine="MatDialogBuilder.IconRes(GlobalVar.QuestionIcon).";
mostCurrent._matdialogbuilder.IconRes(mostCurrent._globalvar._questionicon /*String*/ ).LimitIconToDefaultSize();
 //BA.debugLineNum = 1696;BA.debugLine="MatDialogBuilder.Content(csContent)";
mostCurrent._matdialogbuilder.Content(BA.ObjectToCharSequence(_cscontent.getObject()));
 //BA.debugLineNum = 1697;BA.debugLine="MatDialogBuilder.PositiveText($\"YES\"$).PositiveCo";
mostCurrent._matdialogbuilder.PositiveText(BA.ObjectToCharSequence(("YES"))).PositiveColor((int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 1698;BA.debugLine="MatDialogBuilder.NegativeText($\"NO\"$).NegativeCol";
mostCurrent._matdialogbuilder.NegativeText(BA.ObjectToCharSequence(("NO"))).NegativeColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1699;BA.debugLine="MatDialogBuilder.CanceledOnTouchOutside(False)";
mostCurrent._matdialogbuilder.CanceledOnTouchOutside(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1700;BA.debugLine="MatDialogBuilder.Show";
mostCurrent._matdialogbuilder.Show();
 //BA.debugLineNum = 1701;BA.debugLine="End Sub";
return "";
}
public static String  _showdlcomplete(String _stitle,String _smsg) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alertdialog = null;
 //BA.debugLineNum = 2117;BA.debugLine="Private Sub ShowDLComplete(sTitle As String, sMsg";
 //BA.debugLineNum = 2118;BA.debugLine="Dim AlertDialog As AX_CustomAlertDialog";
_alertdialog = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 2119;BA.debugLine="soundsAlarmChannel.Play(SoundID,1,1,1,0,1)";
_soundsalarmchannel.Play(_soundid,(float) (1),(float) (1),(int) (1),(int) (0),(float) (1));
 //BA.debugLineNum = 2120;BA.debugLine="vibration.vibrateOnce(2000)";
mostCurrent._vibration.vibrateOnce(processBA,(long) (2000));
 //BA.debugLineNum = 2121;BA.debugLine="UpdateHasData(ReaderID)";
_updatehasdata(_readerid);
 //BA.debugLineNum = 2123;BA.debugLine="AlertDialog.Initialize";
_alertdialog.Initialize();
 //BA.debugLineNum = 2124;BA.debugLine="AlertDialog.Initialize.Create _ 			.SetDialogStyl";
_alertdialog.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialogDisableStatus").SetStyle(_alertdialog.getSTYLE_DIALOGUE()).SetCancelable(anywheresoftware.b4a.keywords.Common.False).SetTitle(_stitle).SetTitleColor(anywheresoftware.b4a.keywords.Common.Colors.Red).SetTitleTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessage(_smsg).SetMessageTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetPositiveTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetPositiveColor(anywheresoftware.b4a.keywords.Common.Colors.Blue).SetPositiveText("OK").SetOnPositiveClicked(mostCurrent.activityBA,"OnDownloadComplete").SetNegativeTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetNegativeColor(anywheresoftware.b4a.keywords.Common.Colors.Red).SetNegativeText("DOWNLOAD ANOTHER?").SetOnNegativeClicked(mostCurrent.activityBA,"OnDownloadComplete").SetOnViewBinder(mostCurrent.activityBA,"DLULViewBinder");
 //BA.debugLineNum = 2142;BA.debugLine="AlertDialog.SetDialogBackground(myCD)";
_alertdialog.SetDialogBackground((android.graphics.drawable.Drawable)(_mycd().getObject()));
 //BA.debugLineNum = 2143;BA.debugLine="AlertDialog.Build.Show";
_alertdialog.Build().Show();
 //BA.debugLineNum = 2144;BA.debugLine="End Sub";
return "";
}
public static String  _showdlpassworddialog() throws Exception{
 //BA.debugLineNum = 326;BA.debugLine="Private Sub ShowDLPasswordDialog";
 //BA.debugLineNum = 327;BA.debugLine="MatDialogBuilder.Initialize(\"RetDLPassword\")";
mostCurrent._matdialogbuilder.Initialize(mostCurrent.activityBA,"RetDLPassword");
 //BA.debugLineNum = 328;BA.debugLine="MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIG";
mostCurrent._matdialogbuilder.Theme(mostCurrent._matdialogbuilder.THEME_LIGHT);
 //BA.debugLineNum = 329;BA.debugLine="MatDialogBuilder.Title($\"Password Required\"$).Tit";
mostCurrent._matdialogbuilder.Title(BA.ObjectToCharSequence(("Password Required"))).TitleColor((int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 330;BA.debugLine="MatDialogBuilder.Content($\"Input your Password.\"$";
mostCurrent._matdialogbuilder.Content(BA.ObjectToCharSequence(("Input your Password.")));
 //BA.debugLineNum = 331;BA.debugLine="MatDialogBuilder.InputType(MatDialogBuilder.TYPE_";
mostCurrent._matdialogbuilder.InputType(mostCurrent._matdialogbuilder.TYPE_TEXT_VARIATION_PASSWORD);
 //BA.debugLineNum = 332;BA.debugLine="MatDialogBuilder.Input(\"Enter your Password Here.";
mostCurrent._matdialogbuilder.Input(BA.ObjectToCharSequence("Enter your Password Here..."),BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 333;BA.debugLine="MatDialogBuilder.PositiveText($\"Ok\"$).PositiveCol";
mostCurrent._matdialogbuilder.PositiveText(BA.ObjectToCharSequence(("Ok"))).PositiveColor((int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 334;BA.debugLine="MatDialogBuilder.NegativeText($\"Cancel\"$).Negativ";
mostCurrent._matdialogbuilder.NegativeText(BA.ObjectToCharSequence(("Cancel"))).NegativeColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 335;BA.debugLine="MatDialogBuilder.CanceledOnTouchOutside(False)";
mostCurrent._matdialogbuilder.CanceledOnTouchOutside(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 336;BA.debugLine="MatDialogBuilder.AlwaysCallInputCallback";
mostCurrent._matdialogbuilder.AlwaysCallInputCallback();
 //BA.debugLineNum = 337;BA.debugLine="MatDialogBuilder.IconRes(\"ic_security_black_36dp2";
mostCurrent._matdialogbuilder.IconRes("ic_security_black_36dp2").LimitIconToDefaultSize();
 //BA.debugLineNum = 338;BA.debugLine="MatDialogBuilder.Show";
mostCurrent._matdialogbuilder.Show();
 //BA.debugLineNum = 339;BA.debugLine="End Sub";
return "";
}
public static String  _showdownloadpanel() throws Exception{
 //BA.debugLineNum = 210;BA.debugLine="Public Sub ShowDownloadPanel";
 //BA.debugLineNum = 213;BA.debugLine="cdButtonCancel.Initialize(0xFFDC143C, 25)";
mostCurrent._cdbuttoncancel.Initialize((int) (0xffdc143c),(int) (25));
 //BA.debugLineNum = 214;BA.debugLine="btnCancel.Background = cdButtonCancel";
mostCurrent._btncancel.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdbuttoncancel.getObject()));
 //BA.debugLineNum = 216;BA.debugLine="cdButtonOK.Initialize(0xFF1976D2, 25)";
mostCurrent._cdbuttonok.Initialize((int) (0xff1976d2),(int) (25));
 //BA.debugLineNum = 217;BA.debugLine="btnOK.Background = cdButtonOK";
mostCurrent._btnok.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdbuttonok.getObject()));
 //BA.debugLineNum = 219;BA.debugLine="pnlButtons.Visible = False";
mostCurrent._pnlbuttons.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 220;BA.debugLine="pnlDownloadBox.Visible = True";
mostCurrent._pnldownloadbox.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 221;BA.debugLine="LoadReader";
_loadreader();
 //BA.debugLineNum = 222;BA.debugLine="SetReadingDate";
_setreadingdate();
 //BA.debugLineNum = 223;BA.debugLine="End Sub";
return "";
}
public static String  _showuplcomplete(String _stitle,String _smsg) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alertdialog = null;
 //BA.debugLineNum = 2162;BA.debugLine="Private Sub ShowUpLComplete(sTitle As String, sMsg";
 //BA.debugLineNum = 2163;BA.debugLine="Dim AlertDialog As AX_CustomAlertDialog";
_alertdialog = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 2164;BA.debugLine="soundsAlarmChannel.Play(SoundID,1,1,1,0,1)";
_soundsalarmchannel.Play(_soundid,(float) (1),(float) (1),(int) (1),(int) (0),(float) (1));
 //BA.debugLineNum = 2165;BA.debugLine="vibration.vibrateOnce(2000)";
mostCurrent._vibration.vibrateOnce(processBA,(long) (2000));
 //BA.debugLineNum = 2167;BA.debugLine="AlertDialog.Initialize";
_alertdialog.Initialize();
 //BA.debugLineNum = 2168;BA.debugLine="AlertDialog.Initialize.Create _ 			.SetDialogStyl";
_alertdialog.Initialize().Create(mostCurrent.activityBA).SetDialogStyleName("MyDialogDisableStatus").SetStyle(_alertdialog.getSTYLE_DIALOGUE()).SetCancelable(anywheresoftware.b4a.keywords.Common.False).SetTitle(_stitle).SetTitleColor(anywheresoftware.b4a.keywords.Common.Colors.Red).SetTitleTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetMessage(_smsg).SetMessageTypeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetPositiveTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetPositiveColor(anywheresoftware.b4a.keywords.Common.Colors.Blue).SetPositiveText("OK").SetOnPositiveClicked(mostCurrent.activityBA,"OnUploadComplete").SetNegativeTypeface((android.graphics.Typeface)(mostCurrent._globalvar._fontbold /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).SetNegativeColor(anywheresoftware.b4a.keywords.Common.Colors.Red).SetNegativeText("UPLOAD ANOTHER?").SetOnNegativeClicked(mostCurrent.activityBA,"OnUploadComplete").SetOnViewBinder(mostCurrent.activityBA,"DLULViewBinder");
 //BA.debugLineNum = 2186;BA.debugLine="AlertDialog.SetDialogBackground(myCD)";
_alertdialog.SetDialogBackground((android.graphics.drawable.Drawable)(_mycd().getObject()));
 //BA.debugLineNum = 2187;BA.debugLine="AlertDialog.Build.Show";
_alertdialog.Build().Show();
 //BA.debugLineNum = 2188;BA.debugLine="End Sub";
return "";
}
public static String  _showuploadcomplete() throws Exception{
anywheresoftware.b4a.objects.CSBuilder _cstitle = null;
anywheresoftware.b4a.objects.CSBuilder _cscontent = null;
 //BA.debugLineNum = 1806;BA.debugLine="Private Sub ShowUploadComplete";
 //BA.debugLineNum = 1807;BA.debugLine="Dim csTitle, csContent As CSBuilder";
_cstitle = new anywheresoftware.b4a.objects.CSBuilder();
_cscontent = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 1808;BA.debugLine="csTitle.Initialize.Color(GlobalVar.PriColor).Bold";
_cstitle.Initialize().Color((int) (mostCurrent._globalvar._pricolor /*double*/ )).Bold().Size((int) (16)).Append(BA.ObjectToCharSequence(("Data Upload Complete"))).PopAll();
 //BA.debugLineNum = 1809;BA.debugLine="csContent.Initialize.Size(14).Color(GlobalVar.Pri";
_cscontent.Initialize().Size((int) (14)).Color((int) (mostCurrent._globalvar._pricolor /*double*/ )).Bold().Append(BA.ObjectToCharSequence(("Reading Data for the specified Reader and Book were successfully uploaded."))).PopAll();
 //BA.debugLineNum = 1811;BA.debugLine="MatDialogBuilder.Initialize(\"ULComplete\")";
mostCurrent._matdialogbuilder.Initialize(mostCurrent.activityBA,"ULComplete");
 //BA.debugLineNum = 1812;BA.debugLine="MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIG";
mostCurrent._matdialogbuilder.Theme(mostCurrent._matdialogbuilder.THEME_LIGHT);
 //BA.debugLineNum = 1813;BA.debugLine="MatDialogBuilder.Title(csTitle).TitleGravity(MatD";
mostCurrent._matdialogbuilder.Title(BA.ObjectToCharSequence(_cstitle.getObject())).TitleGravity(mostCurrent._matdialogbuilder.GRAVITY_START);
 //BA.debugLineNum = 1814;BA.debugLine="MatDialogBuilder.IconRes(GlobalVar.InfoIcon).Limi";
mostCurrent._matdialogbuilder.IconRes(mostCurrent._globalvar._infoicon /*String*/ ).LimitIconToDefaultSize();
 //BA.debugLineNum = 1815;BA.debugLine="MatDialogBuilder.Content(csContent)";
mostCurrent._matdialogbuilder.Content(BA.ObjectToCharSequence(_cscontent.getObject()));
 //BA.debugLineNum = 1816;BA.debugLine="MatDialogBuilder.PositiveText($\"OK\"$).PositiveCol";
mostCurrent._matdialogbuilder.PositiveText(BA.ObjectToCharSequence(("OK"))).PositiveColor((int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 1817;BA.debugLine="MatDialogBuilder.NegativeText($\"Upload Another\"$)";
mostCurrent._matdialogbuilder.NegativeText(BA.ObjectToCharSequence(("Upload Another"))).NegativeColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1818;BA.debugLine="MatDialogBuilder.CanceledOnTouchOutside(False)";
mostCurrent._matdialogbuilder.CanceledOnTouchOutside(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1819;BA.debugLine="MatDialogBuilder.Show";
mostCurrent._matdialogbuilder.Show();
 //BA.debugLineNum = 1820;BA.debugLine="End Sub";
return "";
}
public static String  _showuploadpanel() throws Exception{
 //BA.debugLineNum = 1223;BA.debugLine="Private Sub ShowUploadPanel";
 //BA.debugLineNum = 1225;BA.debugLine="cdButtonCancel.Initialize(0xFFDC143C, 25)";
mostCurrent._cdbuttoncancel.Initialize((int) (0xffdc143c),(int) (25));
 //BA.debugLineNum = 1226;BA.debugLine="btnCancelUpload.Background = cdButtonCancel";
mostCurrent._btncancelupload.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdbuttoncancel.getObject()));
 //BA.debugLineNum = 1228;BA.debugLine="cdButtonOK.Initialize(0xFF1976D2, 25)";
mostCurrent._cdbuttonok.Initialize((int) (0xff1976d2),(int) (25));
 //BA.debugLineNum = 1229;BA.debugLine="btnOkUpload.Background = cdButtonOK";
mostCurrent._btnokupload.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cdbuttonok.getObject()));
 //BA.debugLineNum = 1231;BA.debugLine="pnlButtons.Visible = False";
mostCurrent._pnlbuttons.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1232;BA.debugLine="pnlUploadBox.Visible = True";
mostCurrent._pnluploadbox.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1234;BA.debugLine="csNote.Initialize.Size(15).Color(Colors.Red).Appe";
mostCurrent._csnote.Initialize().Size((int) (15)).Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(("NOTE: ")));
 //BA.debugLineNum = 1235;BA.debugLine="csNote.Size(13).Color(GlobalVar.PriColor).Append(";
mostCurrent._csnote.Size((int) (13)).Color((int) (mostCurrent._globalvar._pricolor /*double*/ )).Append(BA.ObjectToCharSequence(("This Process cannot be undone...  ")+anywheresoftware.b4a.keywords.Common.CRLF));
 //BA.debugLineNum = 1236;BA.debugLine="csNote.Append($\"This will upload all valid readin";
mostCurrent._csnote.Append(BA.ObjectToCharSequence(("This will upload all valid readings. Miscoded reading and/or negative reading will not be Uploaded.")));
 //BA.debugLineNum = 1237;BA.debugLine="csNote.PopAll";
mostCurrent._csnote.PopAll();
 //BA.debugLineNum = 1239;BA.debugLine="lblULNote.Text = csNote";
mostCurrent._lblulnote.setText(BA.ObjectToCharSequence(mostCurrent._csnote.getObject()));
 //BA.debugLineNum = 1241;BA.debugLine="LoadUploader";
_loaduploader();
 //BA.debugLineNum = 1242;BA.debugLine="End Sub";
return "";
}
public static String  _showuppassworddialog() throws Exception{
 //BA.debugLineNum = 1270;BA.debugLine="Private Sub ShowUPPasswordDialog";
 //BA.debugLineNum = 1271;BA.debugLine="MatDialogBuilder.Initialize(\"RetUPPassword\")";
mostCurrent._matdialogbuilder.Initialize(mostCurrent.activityBA,"RetUPPassword");
 //BA.debugLineNum = 1272;BA.debugLine="MatDialogBuilder.Theme(MatDialogBuilder.THEME_LIG";
mostCurrent._matdialogbuilder.Theme(mostCurrent._matdialogbuilder.THEME_LIGHT);
 //BA.debugLineNum = 1273;BA.debugLine="MatDialogBuilder.Title($\"Password Required\"$).Tit";
mostCurrent._matdialogbuilder.Title(BA.ObjectToCharSequence(("Password Required"))).TitleColor((int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 1274;BA.debugLine="MatDialogBuilder.Content($\"Input your Password.\"$";
mostCurrent._matdialogbuilder.Content(BA.ObjectToCharSequence(("Input your Password.")));
 //BA.debugLineNum = 1275;BA.debugLine="MatDialogBuilder.InputType(MatDialogBuilder.TYPE_";
mostCurrent._matdialogbuilder.InputType(mostCurrent._matdialogbuilder.TYPE_TEXT_VARIATION_PASSWORD);
 //BA.debugLineNum = 1276;BA.debugLine="MatDialogBuilder.Input(\"Enter your Password Here.";
mostCurrent._matdialogbuilder.Input(BA.ObjectToCharSequence("Enter your Password Here..."),BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 1277;BA.debugLine="MatDialogBuilder.PositiveText($\"Ok\"$).PositiveCol";
mostCurrent._matdialogbuilder.PositiveText(BA.ObjectToCharSequence(("Ok"))).PositiveColor((int) (mostCurrent._globalvar._pricolor /*double*/ ));
 //BA.debugLineNum = 1278;BA.debugLine="MatDialogBuilder.NegativeText($\"Cancel\"$).Negativ";
mostCurrent._matdialogbuilder.NegativeText(BA.ObjectToCharSequence(("Cancel"))).NegativeColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1279;BA.debugLine="MatDialogBuilder.CanceledOnTouchOutside(False)";
mostCurrent._matdialogbuilder.CanceledOnTouchOutside(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1280;BA.debugLine="MatDialogBuilder.AlwaysCallInputCallback";
mostCurrent._matdialogbuilder.AlwaysCallInputCallback();
 //BA.debugLineNum = 1281;BA.debugLine="MatDialogBuilder.IconRes(\"ic_security_black_36dp2";
mostCurrent._matdialogbuilder.IconRes("ic_security_black_36dp2").LimitIconToDefaultSize();
 //BA.debugLineNum = 1282;BA.debugLine="MatDialogBuilder.Show";
mostCurrent._matdialogbuilder.Show();
 //BA.debugLineNum = 1283;BA.debugLine="End Sub";
return "";
}
public static String  _titlecase(String _s) throws Exception{
anywheresoftware.b4a.keywords.Regex.MatcherWrapper _m = null;
int _i = 0;
 //BA.debugLineNum = 2231;BA.debugLine="Sub TitleCase (s As String) As String";
 //BA.debugLineNum = 2232;BA.debugLine="s = s.ToLowerCase";
_s = _s.toLowerCase();
 //BA.debugLineNum = 2233;BA.debugLine="Dim m As Matcher = Regex.Matcher(\"\\b(\\w)\", s)";
_m = new anywheresoftware.b4a.keywords.Regex.MatcherWrapper();
_m = anywheresoftware.b4a.keywords.Common.Regex.Matcher("\\b(\\w)",_s);
 //BA.debugLineNum = 2234;BA.debugLine="Do While m.Find";
while (_m.Find()) {
 //BA.debugLineNum = 2235;BA.debugLine="Dim i As Int = m.GetStart(1)";
_i = _m.GetStart((int) (1));
 //BA.debugLineNum = 2236;BA.debugLine="s = s.SubString2(0, i) & s.SubString2(i, i + 1).";
_s = _s.substring((int) (0),_i)+_s.substring(_i,(int) (_i+1)).toUpperCase()+_s.substring((int) (_i+1));
 }
;
 //BA.debugLineNum = 2238;BA.debugLine="Return s";
if (true) return _s;
 //BA.debugLineNum = 2239;BA.debugLine="End Sub";
return "";
}
public static String  _toolbar_menuitemclick(de.amberhome.objects.appcompat.ACMenuItemWrapper _item) throws Exception{
 //BA.debugLineNum = 205;BA.debugLine="Sub ToolBar_MenuItemClick (Item As ACMenuItem)";
 //BA.debugLineNum = 207;BA.debugLine="End Sub";
return "";
}
public static String  _toolbar_navigationitemclick() throws Exception{
 //BA.debugLineNum = 201;BA.debugLine="Sub ToolBar_NavigationItemClick";
 //BA.debugLineNum = 202;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 203;BA.debugLine="End Sub";
return "";
}
public static String  _ulcomplete_buttonpressed(de.amberhome.materialdialogs.MaterialDialogWrapper _mdialog,String _saction) throws Exception{
 //BA.debugLineNum = 1822;BA.debugLine="Private Sub ULComplete_ButtonPressed (mDialog As M";
 //BA.debugLineNum = 1823;BA.debugLine="Select Case sAction";
switch (BA.switchObjectToInt(_saction,_mdialog.ACTION_POSITIVE,_mdialog.ACTION_NEGATIVE)) {
case 0: {
 //BA.debugLineNum = 1825;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 break; }
case 1: {
 //BA.debugLineNum = 1828;BA.debugLine="Return";
if (true) return "";
 break; }
}
;
 //BA.debugLineNum = 1830;BA.debugLine="End Sub";
return "";
}
public static String  _updatebook(int _ibookid) throws Exception{
 //BA.debugLineNum = 1741;BA.debugLine="Sub UpdateBook(iBookID As Int)";
 //BA.debugLineNum = 1742;BA.debugLine="Try";
try { //BA.debugLineNum = 1743;BA.debugLine="Starter.strCriteria=\"UPDATE tblBookAssignments S";
mostCurrent._starter._strcriteria /*String*/  = "UPDATE tblBookAssignments SET WasUploaded = ?, UploadedBy = ?, DateUploaded = ? WHERE BookID = "+BA.NumberToString(_ibookid)+" AND BillYear = "+BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ )+" AND BillMonth = "+BA.NumberToString(mostCurrent._globalvar._billmonth /*int*/ );
 //BA.debugLineNum = 1744;BA.debugLine="Starter.DBCon.ExecNonQuery2(Starter.strCriteria,";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2(mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{("1"),BA.NumberToString(mostCurrent._globalvar._userid /*int*/ ),anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow())}));
 //BA.debugLineNum = 1745;BA.debugLine="Starter.DBCon.TransactionSuccessful";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .TransactionSuccessful();
 //BA.debugLineNum = 1746;BA.debugLine="ToastMessageShow(\"Selected book was successfully";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Selected book was successfully uploaded!"),anywheresoftware.b4a.keywords.Common.False);
 } 
       catch (Exception e7) {
			processBA.setLastException(e7); //BA.debugLineNum = 1748;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("15111815",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 1750;BA.debugLine="End Sub";
return "";
}
public static String  _updatehasdata(int _ireaderid) throws Exception{
 //BA.debugLineNum = 1752;BA.debugLine="Sub UpdateHasData(iReaderID As Int)";
 //BA.debugLineNum = 1753;BA.debugLine="Try";
try { //BA.debugLineNum = 1754;BA.debugLine="Starter.strCriteria=\"UPDATE tblReaders SET HasDa";
mostCurrent._starter._strcriteria /*String*/  = "UPDATE tblReaders SET HasData = ? WHERE ReaderID = "+BA.NumberToString(_ireaderid);
 //BA.debugLineNum = 1755;BA.debugLine="Starter.DBCon.ExecNonQuery2(Starter.strCriteria,";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2(mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{("1")}));
 //BA.debugLineNum = 1756;BA.debugLine="Starter.DBCon.TransactionSuccessful";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .TransactionSuccessful();
 //BA.debugLineNum = 1757;BA.debugLine="ToastMessageShow(\"Selected book was successfully";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Selected book was successfully uploaded!"),anywheresoftware.b4a.keywords.Common.False);
 } 
       catch (Exception e7) {
			processBA.setLastException(e7); //BA.debugLineNum = 1759;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("15177351",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 1761;BA.debugLine="End Sub";
return "";
}
public static boolean  _updateuploadstatus(int _ibookid) throws Exception{
boolean _blnretval = false;
 //BA.debugLineNum = 1763;BA.debugLine="Sub UpdateUploadStatus(iBookID As Int) As Boolean";
 //BA.debugLineNum = 1764;BA.debugLine="Dim blnRetVal As Boolean";
_blnretval = false;
 //BA.debugLineNum = 1766;BA.debugLine="blnRetVal = False";
_blnretval = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 1767;BA.debugLine="Try";
try { //BA.debugLineNum = 1768;BA.debugLine="Starter.DBCon.BeginTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .BeginTransaction();
 //BA.debugLineNum = 1769;BA.debugLine="Starter.strCriteria=\"UPDATE tblReadings SET WasU";
mostCurrent._starter._strcriteria /*String*/  = "UPDATE tblReadings SET WasUploaded = ?  "+"WHERE BranchID = "+BA.NumberToString(mostCurrent._globalvar._branchid /*int*/ )+" "+"AND BookID = "+BA.NumberToString(_ibookid)+" "+"And BillYear = "+BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ )+" "+"And BillMonth = "+BA.NumberToString(mostCurrent._globalvar._billmonth /*int*/ )+" "+"And WasRead = 1 "+"AND PresCum >= 0 "+"AND PresRdg <> '"+""+"' "+"AND WasUploaded = 0 "+"AND PrintStatus > 0 "+"And WasMissCoded = 0 ";
 //BA.debugLineNum = 1781;BA.debugLine="Starter.DBCon.ExecNonQuery2(Starter.strCriteria,";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2(mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{("1")}));
 //BA.debugLineNum = 1782;BA.debugLine="Starter.DBCon.TransactionSuccessful";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .TransactionSuccessful();
 //BA.debugLineNum = 1783;BA.debugLine="Starter.DBCon.EndTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .EndTransaction();
 //BA.debugLineNum = 1784;BA.debugLine="blnRetVal = True";
_blnretval = anywheresoftware.b4a.keywords.Common.True;
 } 
       catch (Exception e11) {
			processBA.setLastException(e11); //BA.debugLineNum = 1786;BA.debugLine="blnRetVal = False";
_blnretval = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 1787;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("15242904",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 1790;BA.debugLine="Return blnRetVal";
if (true) return _blnretval;
 //BA.debugLineNum = 1791;BA.debugLine="End Sub";
return false;
}
public static void  _uploaddata(String _sdata) throws Exception{
ResumableSub_UploadData rsub = new ResumableSub_UploadData(null,_sdata);
rsub.resume(processBA, null);
}
public static class ResumableSub_UploadData extends BA.ResumableSub {
public ResumableSub_UploadData(com.bwsi.MeterReader.datasyncing parent,String _sdata) {
this.parent = parent;
this._sdata = _sdata;
}
com.bwsi.MeterReader.datasyncing parent;
String _sdata;
String _retval = "";
anywheresoftware.b4a.objects.collections.JSONParser _jparser = null;
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
 //BA.debugLineNum = 1651;BA.debugLine="Dim retVal As String";
_retval = "";
 //BA.debugLineNum = 1652;BA.debugLine="Dim jParser As JSONParser";
_jparser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 1654;BA.debugLine="Dim j As HttpJob";
_j = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 1655;BA.debugLine="j.Initialize(\"\", Me)";
_j._initialize(processBA,"",datasyncing.getObject());
 //BA.debugLineNum = 1657;BA.debugLine="j.PostString(GlobalVar.ServerAddress & GlobalVar.";
_j._poststring(parent.mostCurrent._globalvar._serveraddress /*String*/ +parent.mostCurrent._globalvar._controllerprefix /*String*/ +"upload",_sdata);
 //BA.debugLineNum = 1658;BA.debugLine="j.GetRequest.SetHeader(\"User-Agent\", \"Mozilla/5.0";
_j._getrequest().SetHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.92 Safari/537.36");
 //BA.debugLineNum = 1659;BA.debugLine="j.GetRequest.SetContentType(\"plain/text\")";
_j._getrequest().SetContentType("plain/text");
 //BA.debugLineNum = 1660;BA.debugLine="Log(sData)";
anywheresoftware.b4a.keywords.Common.LogImpl("14915210",_sdata,0);
 //BA.debugLineNum = 1662;BA.debugLine="Wait For (j) JobDone(j As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_j));
this.state = 15;
return;
case 15:
//C
this.state = 1;
_j = (anywheresoftware.b4a.samples.httputils2.httpjob) result[0];
;
 //BA.debugLineNum = 1663;BA.debugLine="If j.Success Then";
if (true) break;

case 1:
//if
this.state = 14;
if (_j._success) { 
this.state = 3;
}else {
this.state = 13;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 1664;BA.debugLine="retVal = j.GetString";
_retval = _j._getstring();
 //BA.debugLineNum = 1665;BA.debugLine="jParser.Initialize(retVal)";
_jparser.Initialize(_retval);
 //BA.debugLineNum = 1666;BA.debugLine="Log(retVal)";
anywheresoftware.b4a.keywords.Common.LogImpl("14915216",_retval,0);
 //BA.debugLineNum = 1667;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 1668;BA.debugLine="j.Release";
_j._release();
 //BA.debugLineNum = 1670;BA.debugLine="If UpdateUploadStatus(intUploadedBookID) = True";
if (true) break;

case 4:
//if
this.state = 11;
if (_updateuploadstatus(parent._intuploadedbookid)==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 6;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 1671;BA.debugLine="If AreAllAccountsUploaded(intUploadedBookID,Glo";
if (true) break;

case 7:
//if
this.state = 10;
if (_areallaccountsuploaded(parent._intuploadedbookid,parent.mostCurrent._globalvar._billmonth /*int*/ ,(int) (parent.mostCurrent._globalvar._billyear /*double*/ ))==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 9;
}if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 1672;BA.debugLine="UpdateBook(intUploadedBookID)";
_updatebook(parent._intuploadedbookid);
 if (true) break;

case 10:
//C
this.state = 11;
;
 //BA.debugLineNum = 1674;BA.debugLine="ShowUpLComplete($\"Data Upload Complete\"$,$\"Read";
_showuplcomplete(("Data Upload Complete"),("Reading Data for the specified Reader and Book were successfully uploaded!"));
 if (true) break;

case 11:
//C
this.state = 14;
;
 if (true) break;

case 13:
//C
this.state = 14;
 //BA.debugLineNum = 1677;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 1678;BA.debugLine="Log(j.ErrorMessage)";
anywheresoftware.b4a.keywords.Common.LogImpl("14915228",_j._errormessage,0);
 //BA.debugLineNum = 1679;BA.debugLine="jParser.Initialize(retVal)";
_jparser.Initialize(_retval);
 //BA.debugLineNum = 1680;BA.debugLine="Log(retVal)";
anywheresoftware.b4a.keywords.Common.LogImpl("14915230",_retval,0);
 //BA.debugLineNum = 1682;BA.debugLine="ToastMessageShow(\"Unable to Upload Reading Data";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Unable to Upload Reading Data due to "+_j._errormessage),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1683;BA.debugLine="j.Release";
_j._release();
 //BA.debugLineNum = 1684;BA.debugLine="Log(j.ErrorMessage)";
anywheresoftware.b4a.keywords.Common.LogImpl("14915234",_j._errormessage,0);
 //BA.debugLineNum = 1685;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 14:
//C
this.state = -1;
;
 //BA.debugLineNum = 1687;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _uploadviewbinder_onbindview(anywheresoftware.b4a.objects.ConcreteViewWrapper _view,int _viewtype) throws Exception{
com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder _alertdialog = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
anywheresoftware.b4a.objects.CSBuilder _cs = null;
 //BA.debugLineNum = 2096;BA.debugLine="Private Sub UploadViewBinder_OnBindView (View As V";
 //BA.debugLineNum = 2097;BA.debugLine="Dim AlertDialog As AX_CustomAlertDialog";
_alertdialog = new com.aghajari.ax_customalertviewdialog.AX_CustomAlertDialogBuilder();
 //BA.debugLineNum = 2098;BA.debugLine="AlertDialog.Initialize";
_alertdialog.Initialize();
 //BA.debugLineNum = 2100;BA.debugLine="If ViewType = AlertDialog.VIEW_TITLE Then ' Title";
if (_viewtype==_alertdialog.VIEW_TITLE) { 
 //BA.debugLineNum = 2101;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 2102;BA.debugLine="Dim CS As CSBuilder";
_cs = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 2104;BA.debugLine="CS.Initialize.Typeface(Typeface.DEFAULT_BOLD).Ty";
_cs.Initialize().Typeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD).Typeface(anywheresoftware.b4a.keywords.Common.Typeface.getMATERIALICONS()).Size((int) (26)).Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (0xe2c6)))+"  "));
 //BA.debugLineNum = 2105;BA.debugLine="CS.Typeface(GlobalVar.Font).Size(16).Append(lbl.";
_cs.Typeface((android.graphics.Typeface)(mostCurrent._globalvar._font /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject())).Size((int) (16)).Append(BA.ObjectToCharSequence(_lbl.getText())).Pop();
 //BA.debugLineNum = 2107;BA.debugLine="lbl.Text = CS.PopAll";
_lbl.setText(BA.ObjectToCharSequence(_cs.PopAll().getObject()));
 };
 //BA.debugLineNum = 2109;BA.debugLine="If ViewType = AlertDialog.VIEW_MESSAGE Then";
if (_viewtype==_alertdialog.VIEW_MESSAGE) { 
 //BA.debugLineNum = 2110;BA.debugLine="Dim lbl As Label = View";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_view.getObject()));
 //BA.debugLineNum = 2111;BA.debugLine="lbl.TextSize = 16";
_lbl.setTextSize((float) (16));
 //BA.debugLineNum = 2112;BA.debugLine="lbl.TextColor = Colors.Gray";
_lbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 };
 //BA.debugLineNum = 2114;BA.debugLine="End Sub";
return "";
}
}
