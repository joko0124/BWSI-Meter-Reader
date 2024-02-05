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

public class readingbooks extends androidx.appcompat.app.AppCompatActivity implements B4AActivity{
	public static readingbooks mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "com.bwsi.MeterReader", "com.bwsi.MeterReader.readingbooks");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (readingbooks).");
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
		activityBA = new BA(this, layout, processBA, "com.bwsi.MeterReader", "com.bwsi.MeterReader.readingbooks");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "com.bwsi.MeterReader.readingbooks", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (readingbooks) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (readingbooks) Resume **");
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
		return readingbooks.class;
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
            BA.LogInfo("** Activity (readingbooks) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (readingbooks) Pause event (activity is not paused). **");
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
            readingbooks mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (readingbooks) Resume **");
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
public de.amberhome.objects.appcompat.ACButtonWrapper _btnload = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblbookdesc = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblbookno = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblhigh = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllow = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblmisscode = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblnoaccts = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblunread = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblzero = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlassignedbooks = null;
public anywheresoftware.b4a.sql.SQL.CursorWrapper _rsassignedbooks = null;
public com.bwsi.MeterReader.classcustomlistview _clvassignment = null;
public anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblread = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblave = null;
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
 //BA.debugLineNum = 61;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 62;BA.debugLine="MyScale.SetRate(0.5)";
mostCurrent._myscale._setrate /*String*/ (mostCurrent.activityBA,0.5);
 //BA.debugLineNum = 63;BA.debugLine="Activity.LoadLayout(\"BookSelection\")";
mostCurrent._activity.LoadLayout("BookSelection",mostCurrent.activityBA);
 //BA.debugLineNum = 65;BA.debugLine="GlobalVar.CSTitle.Initialize.Size(13).Append($\"Bo";
mostCurrent._globalvar._cstitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (13)).Append(BA.ObjectToCharSequence(("Book Selection"))).Bold().PopAll();
 //BA.debugLineNum = 66;BA.debugLine="GlobalVar.CSSubTitle.Initialize.Size(11).Append($";
mostCurrent._globalvar._cssubtitle /*anywheresoftware.b4a.objects.CSBuilder*/ .Initialize().Size((int) (11)).Append(BA.ObjectToCharSequence(("Please select the book you want to read..."))).PopAll();
 //BA.debugLineNum = 68;BA.debugLine="ToolBar.InitMenuListener";
mostCurrent._toolbar.InitMenuListener();
 //BA.debugLineNum = 69;BA.debugLine="ToolBar.Title = GlobalVar.CSTitle";
mostCurrent._toolbar.setTitle(BA.ObjectToCharSequence(mostCurrent._globalvar._cstitle /*anywheresoftware.b4a.objects.CSBuilder*/ .getObject()));
 //BA.debugLineNum = 70;BA.debugLine="ToolBar.SubTitle = GlobalVar.CSSubTitle";
mostCurrent._toolbar.setSubTitle(BA.ObjectToCharSequence(mostCurrent._globalvar._cssubtitle /*anywheresoftware.b4a.objects.CSBuilder*/ .getObject()));
 //BA.debugLineNum = 72;BA.debugLine="ActionBarButton.Initialize";
mostCurrent._actionbarbutton.Initialize(mostCurrent.activityBA);
 //BA.debugLineNum = 73;BA.debugLine="ActionBarButton.ShowUpIndicator = True";
mostCurrent._actionbarbutton.setShowUpIndicator(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 75;BA.debugLine="Dim jo As JavaObject";
_jo = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 76;BA.debugLine="Dim xl As XmlLayoutBuilder";
_xl = new anywheresoftware.b4a.object.XmlLayoutBuilder();
 //BA.debugLineNum = 77;BA.debugLine="jo = ToolBar";
_jo = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(mostCurrent._toolbar.getObject()));
 //BA.debugLineNum = 78;BA.debugLine="jo.RunMethod(\"setPopupTheme\", Array(xl.GetResourc";
_jo.RunMethod("setPopupTheme",new Object[]{(Object)(_xl.GetResourceId("style","ToolbarMenu"))});
 //BA.debugLineNum = 79;BA.debugLine="jo.RunMethod(\"setContentInsetStartWithNavigation\"";
_jo.RunMethod("setContentInsetStartWithNavigation",new Object[]{(Object)(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)))});
 //BA.debugLineNum = 80;BA.debugLine="jo.RunMethod(\"setTitleMarginStart\", Array(0dip))";
_jo.RunMethod("setTitleMarginStart",new Object[]{(Object)(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)))});
 //BA.debugLineNum = 82;BA.debugLine="If DBaseFunctions.IsThereBookAssignments(GlobalVa";
if (mostCurrent._dbasefunctions._istherebookassignments /*boolean*/ (mostCurrent.activityBA,mostCurrent._globalvar._branchid /*int*/ ,(int) (mostCurrent._globalvar._billyear /*double*/ ),mostCurrent._globalvar._billmonth /*int*/ ,mostCurrent._globalvar._userid /*int*/ )==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 83;BA.debugLine="DisplayAssignments(GlobalVar.BranchID, GlobalVar";
_displayassignments(mostCurrent._globalvar._branchid /*int*/ ,(int) (mostCurrent._globalvar._billyear /*double*/ ),mostCurrent._globalvar._billmonth /*int*/ ,mostCurrent._globalvar._userid /*int*/ );
 };
 //BA.debugLineNum = 86;BA.debugLine="End Sub";
return "";
}
public static String  _activity_createmenu(de.amberhome.objects.appcompat.ACMenuWrapper _menu) throws Exception{
de.amberhome.objects.appcompat.ACMenuItemWrapper _iitem = null;
 //BA.debugLineNum = 18;BA.debugLine="Sub Activity_CreateMenu(Menu As ACMenu)";
 //BA.debugLineNum = 19;BA.debugLine="Dim iItem As ACMenuItem";
_iitem = new de.amberhome.objects.appcompat.ACMenuItemWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Menu.Clear";
_menu.Clear();
 //BA.debugLineNum = 22;BA.debugLine="Menu.Add2(1, 1, $\"Search\"$, xmlIcon.GetDrawable(\"";
_menu.Add2((int) (1),(int) (1),BA.ObjectToCharSequence(("Search")),mostCurrent._xmlicon.GetDrawable("ic_search_white_24dp")).setShowAsAction(_iitem.SHOW_AS_ACTION_IF_ROOM);
 //BA.debugLineNum = 23;BA.debugLine="Menu.Add2(2, 1, $\"Sort Book by Code\"$, Null).Chec";
_menu.Add2((int) (2),(int) (1),BA.ObjectToCharSequence(("Sort Book by Code")),(android.graphics.drawable.Drawable)(anywheresoftware.b4a.keywords.Common.Null)).setCheckable(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 24;BA.debugLine="Menu.Add2(3, 2, $\"Sort Book by Description\"$, Nul";
_menu.Add2((int) (3),(int) (2),BA.ObjectToCharSequence(("Sort Book by Description")),(android.graphics.drawable.Drawable)(anywheresoftware.b4a.keywords.Common.Null)).setCheckable(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 25;BA.debugLine="Menu.FindItem(2).Checked = True";
_menu.FindItem((int) (2)).setChecked(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 26;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 88;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 89;BA.debugLine="If KeyCode = 4 Then";
if (_keycode==4) { 
 //BA.debugLineNum = 90;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 }else {
 //BA.debugLineNum = 92;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 94;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 100;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 102;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 96;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 98;BA.debugLine="End Sub";
return "";
}
public static String  _btnload_click() throws Exception{
double _intpcaamount = 0;
int _index = 0;
 //BA.debugLineNum = 221;BA.debugLine="Sub btnLoad_Click";
 //BA.debugLineNum = 222;BA.debugLine="Dim intPCAAmount As Double";
_intpcaamount = 0;
 //BA.debugLineNum = 223;BA.debugLine="Dim index As Int = clvAssignment.GetItemFromView(";
_index = mostCurrent._clvassignment._getitemfromview /*int*/ ((anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(anywheresoftware.b4a.keywords.Common.Sender(mostCurrent.activityBA))));
 //BA.debugLineNum = 224;BA.debugLine="GlobalVar.BookID = clvAssignment.GetValue(index)";
mostCurrent._globalvar._bookid /*int*/  = (int)(BA.ObjectToNumber(mostCurrent._clvassignment._getvalue /*Object*/ (_index)));
 //BA.debugLineNum = 225;BA.debugLine="Log(GlobalVar.BookID)";
anywheresoftware.b4a.keywords.Common.LogImpl("153870596",BA.NumberToString(mostCurrent._globalvar._bookid /*int*/ ),0);
 //BA.debugLineNum = 227;BA.debugLine="If DBaseFunctions.IsBookWithPCA(GlobalVar.BookID)";
if (mostCurrent._dbasefunctions._isbookwithpca /*boolean*/ (mostCurrent.activityBA,mostCurrent._globalvar._bookid /*int*/ )==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 228;BA.debugLine="intPCAAmount = DBaseFunctions.GetPCAmount(Global";
_intpcaamount = mostCurrent._dbasefunctions._getpcamount /*double*/ (mostCurrent.activityBA,mostCurrent._globalvar._bookid /*int*/ );
 }else {
 //BA.debugLineNum = 230;BA.debugLine="intPCAAmount = 0";
_intpcaamount = 0;
 };
 //BA.debugLineNum = 233;BA.debugLine="DBaseFunctions.UpdatePCA(GlobalVar.BookID, intPCA";
mostCurrent._dbasefunctions._updatepca /*String*/ (mostCurrent.activityBA,mostCurrent._globalvar._bookid /*int*/ ,_intpcaamount);
 //BA.debugLineNum = 234;BA.debugLine="StartActivity(MeterReading)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._meterreading.getObject()));
 //BA.debugLineNum = 235;BA.debugLine="End Sub";
return "";
}
public static String  _clvassignment_itemclick(int _index,Object _value) throws Exception{
 //BA.debugLineNum = 113;BA.debugLine="Sub clvAssignment_ItemClick (Index As Int, Value A";
 //BA.debugLineNum = 115;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.PanelWrapper  _createassignmentcard(int _iwidth,String _sbookno,String _sbookdesc,int _inoaccts,int _iread,int _iunread,int _imisscode,int _izero,int _ihighbill,int _ilowbill,int _iavebill) throws Exception{
anywheresoftware.b4a.objects.B4XViewWrapper _p = null;
int _iheight = 0;
anywheresoftware.b4a.objects.CSBuilder _csread = null;
anywheresoftware.b4a.objects.CSBuilder _csunread = null;
anywheresoftware.b4a.objects.CSBuilder _csmisscode = null;
anywheresoftware.b4a.objects.CSBuilder _cszero = null;
anywheresoftware.b4a.objects.CSBuilder _cshigh = null;
anywheresoftware.b4a.objects.CSBuilder _cslow = null;
anywheresoftware.b4a.objects.CSBuilder _csave = null;
 //BA.debugLineNum = 153;BA.debugLine="Sub CreateAssignmentCard(iWidth As Int, sBookNo As";
 //BA.debugLineNum = 154;BA.debugLine="Dim p As B4XView = xui.CreatePanel(\"\")";
_p = new anywheresoftware.b4a.objects.B4XViewWrapper();
_p = _xui.CreatePanel(processBA,"");
 //BA.debugLineNum = 155;BA.debugLine="Dim iHeight As Int = 190dip";
_iheight = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (190));
 //BA.debugLineNum = 156;BA.debugLine="Dim csRead, csUnread, csMissCode, csZero, csHigh,";
_csread = new anywheresoftware.b4a.objects.CSBuilder();
_csunread = new anywheresoftware.b4a.objects.CSBuilder();
_csmisscode = new anywheresoftware.b4a.objects.CSBuilder();
_cszero = new anywheresoftware.b4a.objects.CSBuilder();
_cshigh = new anywheresoftware.b4a.objects.CSBuilder();
_cslow = new anywheresoftware.b4a.objects.CSBuilder();
_csave = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 158;BA.debugLine="If GetDeviceLayoutValues.ApproximateScreenSize <";
if (anywheresoftware.b4a.keywords.Common.GetDeviceLayoutValues(mostCurrent.activityBA).getApproximateScreenSize()<4.5) { 
_iheight = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (310));};
 //BA.debugLineNum = 159;BA.debugLine="p.SetLayoutAnimated(0, 0, 0, iWidth, iHeight)";
_p.SetLayoutAnimated((int) (0),(int) (0),(int) (0),_iwidth,_iheight);
 //BA.debugLineNum = 160;BA.debugLine="p.LoadLayout(\"BookAssignment\")";
_p.LoadLayout("BookAssignment",mostCurrent.activityBA);
 //BA.debugLineNum = 162;BA.debugLine="If iRead = 0 Then";
if (_iread==0) { 
 //BA.debugLineNum = 163;BA.debugLine="csRead.Initialize.Bold.Color(Colors.Red).Append(";
_csread.Initialize().Bold().Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(_iread)).PopAll();
 }else {
 //BA.debugLineNum = 165;BA.debugLine="csRead.Initialize.Color(0xFF1976D2).Append(iRead";
_csread.Initialize().Color((int) (0xff1976d2)).Append(BA.ObjectToCharSequence(_iread)).PopAll();
 };
 //BA.debugLineNum = 168;BA.debugLine="If iUnread > 0 Then";
if (_iunread>0) { 
 //BA.debugLineNum = 169;BA.debugLine="csUnread.Initialize.Bold.Color(Colors.Red).Appen";
_csunread.Initialize().Bold().Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(_iunread)).PopAll();
 }else {
 //BA.debugLineNum = 171;BA.debugLine="csUnread.Initialize.Color(0xFF1976D2).Append(iUn";
_csunread.Initialize().Color((int) (0xff1976d2)).Append(BA.ObjectToCharSequence(_iunread)).PopAll();
 };
 //BA.debugLineNum = 174;BA.debugLine="If iMissCode > 0 Then";
if (_imisscode>0) { 
 //BA.debugLineNum = 175;BA.debugLine="csMissCode.Initialize.Bold.Color(Colors.Red).App";
_csmisscode.Initialize().Bold().Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(_imisscode)).PopAll();
 }else {
 //BA.debugLineNum = 177;BA.debugLine="csMissCode.Initialize.Color(0xFF1976D2).Append(i";
_csmisscode.Initialize().Color((int) (0xff1976d2)).Append(BA.ObjectToCharSequence(_imisscode)).PopAll();
 };
 //BA.debugLineNum = 180;BA.debugLine="If iZero > 0 Then";
if (_izero>0) { 
 //BA.debugLineNum = 181;BA.debugLine="csZero.Initialize.Bold.Color(Colors.Red).Append(";
_cszero.Initialize().Bold().Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(_izero)).PopAll();
 }else {
 //BA.debugLineNum = 183;BA.debugLine="csZero.Initialize.Color(0xFF1976D2).Append(iZero";
_cszero.Initialize().Color((int) (0xff1976d2)).Append(BA.ObjectToCharSequence(_izero)).PopAll();
 };
 //BA.debugLineNum = 186;BA.debugLine="If iHighBill > 0 Then";
if (_ihighbill>0) { 
 //BA.debugLineNum = 187;BA.debugLine="csHigh.Initialize.Bold.Color(Colors.Red).Append(";
_cshigh.Initialize().Bold().Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(_ihighbill)).PopAll();
 }else {
 //BA.debugLineNum = 189;BA.debugLine="csHigh.Initialize.Color(0xFF1976D2).Append(iHigh";
_cshigh.Initialize().Color((int) (0xff1976d2)).Append(BA.ObjectToCharSequence(_ihighbill)).PopAll();
 };
 //BA.debugLineNum = 192;BA.debugLine="If iLowBill > 0 Then";
if (_ilowbill>0) { 
 //BA.debugLineNum = 193;BA.debugLine="csLow.Initialize.Bold.Color(Colors.Red).Append(i";
_cslow.Initialize().Bold().Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(_ilowbill)).PopAll();
 }else {
 //BA.debugLineNum = 195;BA.debugLine="csLow.Initialize.Color(0xFF1976D2).Append(iLowBi";
_cslow.Initialize().Color((int) (0xff1976d2)).Append(BA.ObjectToCharSequence(_ilowbill)).PopAll();
 };
 //BA.debugLineNum = 198;BA.debugLine="If iAveBill > 0 Then";
if (_iavebill>0) { 
 //BA.debugLineNum = 199;BA.debugLine="csAve.Initialize.Bold.Color(Colors.Red).Append(i";
_csave.Initialize().Bold().Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(_iavebill)).PopAll();
 }else {
 //BA.debugLineNum = 201;BA.debugLine="csAve.Initialize.Color(0xFF1976D2).Append(iAveBi";
_csave.Initialize().Color((int) (0xff1976d2)).Append(BA.ObjectToCharSequence(_iavebill)).PopAll();
 };
 //BA.debugLineNum = 204;BA.debugLine="lblBookNo.Text = \"BOOK \" & sBookNo";
mostCurrent._lblbookno.setText(BA.ObjectToCharSequence("BOOK "+_sbookno));
 //BA.debugLineNum = 205;BA.debugLine="lblBookDesc.Text =GlobalVar.SF.Upper(sBookDesc)";
mostCurrent._lblbookdesc.setText(BA.ObjectToCharSequence(mostCurrent._globalvar._sf /*adr.stringfunctions.stringfunctions*/ ._vvvvvvv5(_sbookdesc)));
 //BA.debugLineNum = 206;BA.debugLine="lblNoAccts.Text = iNoAccts  & \" Account(s)\"";
mostCurrent._lblnoaccts.setText(BA.ObjectToCharSequence(BA.NumberToString(_inoaccts)+" Account(s)"));
 //BA.debugLineNum = 207;BA.debugLine="lblRead.Text = csRead";
mostCurrent._lblread.setText(BA.ObjectToCharSequence(_csread.getObject()));
 //BA.debugLineNum = 208;BA.debugLine="lblUnread.Text = csUnread";
mostCurrent._lblunread.setText(BA.ObjectToCharSequence(_csunread.getObject()));
 //BA.debugLineNum = 209;BA.debugLine="lblMissCode.Text = csMissCode";
mostCurrent._lblmisscode.setText(BA.ObjectToCharSequence(_csmisscode.getObject()));
 //BA.debugLineNum = 210;BA.debugLine="lblZero.Text = csZero";
mostCurrent._lblzero.setText(BA.ObjectToCharSequence(_cszero.getObject()));
 //BA.debugLineNum = 211;BA.debugLine="lblHigh.Text = csHigh";
mostCurrent._lblhigh.setText(BA.ObjectToCharSequence(_cshigh.getObject()));
 //BA.debugLineNum = 212;BA.debugLine="lblLow.Text = csLow";
mostCurrent._lbllow.setText(BA.ObjectToCharSequence(_cslow.getObject()));
 //BA.debugLineNum = 213;BA.debugLine="lblAve.Text = csAve";
mostCurrent._lblave.setText(BA.ObjectToCharSequence(_csave.getObject()));
 //BA.debugLineNum = 214;BA.debugLine="CD.Initialize2(0xFF1976D2, 25, 0,0xFF000000)";
mostCurrent._cd.Initialize2((int) (0xff1976d2),(int) (25),(int) (0),(int) (0xff000000));
 //BA.debugLineNum = 215;BA.debugLine="btnLoad.Background = CD";
mostCurrent._btnload.setBackground((android.graphics.drawable.Drawable)(mostCurrent._cd.getObject()));
 //BA.debugLineNum = 216;BA.debugLine="Return p";
if (true) return (anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(_p.getObject()));
 //BA.debugLineNum = 217;BA.debugLine="End Sub";
return null;
}
public static String  _displayassignments(int _ibranchid,int _ibillyear,int _ibillmonth,int _iuserid) throws Exception{
int _i = 0;
 //BA.debugLineNum = 118;BA.debugLine="Private Sub DisplayAssignments(iBranchID As Int, i";
 //BA.debugLineNum = 119;BA.debugLine="Try";
try { //BA.debugLineNum = 120;BA.debugLine="Starter.strCriteria = \"SELECT tblReadings.BookID";
mostCurrent._starter._strcriteria /*String*/  = "SELECT tblReadings.BookID AS BookID, tblReadings.BookNo AS BookNo, tblBooks.BookDesc AS BookDesc, "+"Count(tblReadings.AcctID) As NoAccts, "+"SUM(CASE WHEN tblReadings.WasRead = 1 Then 1 Else 0 End) As ReadAccts, "+"SUM(CASE WHEN tblReadings.WasRead = 0 Then 1 Else 0 End) As UnreadAccts, "+"SUM(CASE WHEN tblReadings.WasMissCoded = 1 THEN 1 ELSE 0 END) as Misscoded, "+"SUM(CASE WHEN tblReadings.ImplosiveType = 'zero' Then 1 ELSE 0 END) As ZeroCons, "+"SUM(CASE WHEN tblReadings.ImplosiveType = 'implosive-inc' Then 1 ELSE 0 END) As HighBill, "+"SUM(CASE WHEN tblReadings.ImplosiveType = 'implosive-dec' Then 1 ELSE 0 END) As LowBill, "+"SUM(CASE WHEN tblReadings.BillType = 'AB' Then 1 ELSE 0 END) As AverageBill "+"FROM tblReadings INNER JOIN tblBooks ON tblReadings.BookID = tblBooks.BookID "+"WHERE tblReadings.BranchID = "+BA.NumberToString(_ibranchid)+" "+"AND tblReadings.BillYear = "+BA.NumberToString(_ibillyear)+" "+"AND tblReadings.BillMonth = "+BA.NumberToString(_ibillmonth)+" "+"AND tblReadings.ReadBy = "+BA.NumberToString(_iuserid)+" "+"GROUP BY tblReadings.BookID "+"ORDER BY tblReadings.BookNo Asc";
 //BA.debugLineNum = 137;BA.debugLine="rsAssignedBooks = Starter.DBCon.ExecQuery(Starte";
mostCurrent._rsassignedbooks = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 138;BA.debugLine="clvAssignment.Clear";
mostCurrent._clvassignment._clear /*String*/ ();
 //BA.debugLineNum = 139;BA.debugLine="If rsAssignedBooks.RowCount > 0 Then";
if (mostCurrent._rsassignedbooks.getRowCount()>0) { 
 //BA.debugLineNum = 141;BA.debugLine="For i = 0 To rsAssignedBooks.RowCount - 1";
{
final int step6 = 1;
final int limit6 = (int) (mostCurrent._rsassignedbooks.getRowCount()-1);
_i = (int) (0) ;
for (;_i <= limit6 ;_i = _i + step6 ) {
 //BA.debugLineNum = 142;BA.debugLine="rsAssignedBooks.Position = i";
mostCurrent._rsassignedbooks.setPosition(_i);
 //BA.debugLineNum = 143;BA.debugLine="clvAssignment.Add(CreateAssignmentCard(clvAssi";
mostCurrent._clvassignment._add /*String*/ ((anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_createassignmentcard(mostCurrent._clvassignment._asview /*anywheresoftware.b4a.objects.B4XViewWrapper*/ ().getWidth(),mostCurrent._rsassignedbooks.GetString("BookNo"),mostCurrent._rsassignedbooks.GetString("BookDesc"),mostCurrent._rsassignedbooks.GetInt("NoAccts"),mostCurrent._rsassignedbooks.GetInt("ReadAccts"),mostCurrent._rsassignedbooks.GetInt("UnreadAccts"),mostCurrent._rsassignedbooks.GetInt("Misscoded"),mostCurrent._rsassignedbooks.GetInt("ZeroCons"),mostCurrent._rsassignedbooks.GetInt("HighBill"),mostCurrent._rsassignedbooks.GetInt("LowBill"),mostCurrent._rsassignedbooks.GetInt("AverageBill")).getObject())),(Object)(mostCurrent._rsassignedbooks.GetInt("BookID")));
 }
};
 }else {
 //BA.debugLineNum = 146;BA.debugLine="Log(rsAssignedBooks.RowCount)";
anywheresoftware.b4a.keywords.Common.LogImpl("153739548",BA.NumberToString(mostCurrent._rsassignedbooks.getRowCount()),0);
 };
 } 
       catch (Exception e14) {
			processBA.setLastException(e14); //BA.debugLineNum = 149;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("153739551",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 };
 //BA.debugLineNum = 151;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 32;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 35;BA.debugLine="Dim ActionBarButton As ACActionBar";
mostCurrent._actionbarbutton = new de.amberhome.objects.appcompat.ACActionBar();
 //BA.debugLineNum = 36;BA.debugLine="Private ToolBar As ACToolBarDark";
mostCurrent._toolbar = new de.amberhome.objects.appcompat.ACToolbarDarkWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private xmlIcon As XmlLayoutBuilder";
mostCurrent._xmlicon = new anywheresoftware.b4a.object.XmlLayoutBuilder();
 //BA.debugLineNum = 40;BA.debugLine="Private MatDialogBuilder As MaterialDialogBuilder";
mostCurrent._matdialogbuilder = new de.amberhome.materialdialogs.MaterialDialogBuilderWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Private btnLoad As ACButton";
mostCurrent._btnload = new de.amberhome.objects.appcompat.ACButtonWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private lblBookDesc As Label";
mostCurrent._lblbookdesc = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Private lblBookNo As Label";
mostCurrent._lblbookno = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private lblHigh As Label";
mostCurrent._lblhigh = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Private lblLow As Label";
mostCurrent._lbllow = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private lblMissCode As Label";
mostCurrent._lblmisscode = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Private lblNoAccts As Label";
mostCurrent._lblnoaccts = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Private lblUnread As Label";
mostCurrent._lblunread = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Private lblZero As Label";
mostCurrent._lblzero = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 51;BA.debugLine="Private pnlAssignedBooks As Panel";
mostCurrent._pnlassignedbooks = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 53;BA.debugLine="Private rsAssignedBooks As Cursor";
mostCurrent._rsassignedbooks = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 55;BA.debugLine="Private clvAssignment As classCustomListView";
mostCurrent._clvassignment = new com.bwsi.MeterReader.classcustomlistview();
 //BA.debugLineNum = 56;BA.debugLine="Private CD As ColorDrawable";
mostCurrent._cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 57;BA.debugLine="Private lblRead As Label";
mostCurrent._lblread = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 58;BA.debugLine="Private lblAve As Label";
mostCurrent._lblave = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 59;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 28;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 29;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 30;BA.debugLine="End Sub";
return "";
}
public static String  _toolbar_menuitemclick(de.amberhome.objects.appcompat.ACMenuItemWrapper _item) throws Exception{
 //BA.debugLineNum = 109;BA.debugLine="Sub ToolBar_MenuItemClick (Item As ACMenuItem)";
 //BA.debugLineNum = 111;BA.debugLine="End Sub";
return "";
}
public static String  _toolbar_navigationitemclick() throws Exception{
 //BA.debugLineNum = 105;BA.debugLine="Sub ToolBar_NavigationItemClick";
 //BA.debugLineNum = 106;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 107;BA.debugLine="End Sub";
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
