package com.bwsi.MeterReader;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class manageexternalstorage extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "com.bwsi.MeterReader.manageexternalstorage");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            
        }
        if (BA.isShellModeRuntimeCheck(ba)) 
			   this.getClass().getMethod("_class_globals", com.bwsi.MeterReader.manageexternalstorage.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public Object _ion = null;
public Object _mcallback = null;
public String _meventname = "";
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
public com.bwsi.MeterReader.readingvalidation _readingvalidation = null;
public com.bwsi.MeterReader.starter _starter = null;
public com.bwsi.MeterReader.useraccountsettings _useraccountsettings = null;
public com.bwsi.MeterReader.validationrptgenerator _validationrptgenerator = null;
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 3;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 4;BA.debugLine="Private ion As Object";
_ion = new Object();
 //BA.debugLineNum = 5;BA.debugLine="Private mCallback As Object";
_mcallback = new Object();
 //BA.debugLineNum = 6;BA.debugLine="Private mEventName As String";
_meventname = "";
 //BA.debugLineNum = 8;BA.debugLine="End Sub";
return "";
}
public Object  _getba() throws Exception{
anywheresoftware.b4j.object.JavaObject _jo = null;
 //BA.debugLineNum = 71;BA.debugLine="Private Sub GetBA As Object";
 //BA.debugLineNum = 77;BA.debugLine="Dim jo As JavaObject = Me";
_jo = new anywheresoftware.b4j.object.JavaObject();
_jo = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(this));
 //BA.debugLineNum = 78;BA.debugLine="Return jo.RunMethod(\"getBA\", Null)";
if (true) return _jo.RunMethod("getBA",(Object[])(__c.Null));
 //BA.debugLineNum = 79;BA.debugLine="End Sub";
return null;
}
public String  _getpermission() throws Exception{
anywheresoftware.b4a.objects.IntentWrapper _in = null;
 //BA.debugLineNum = 41;BA.debugLine="Public Sub GetPermission";
 //BA.debugLineNum = 42;BA.debugLine="If HasPermission Then";
if (_haspermission()) { 
 //BA.debugLineNum = 43;BA.debugLine="RaiseEvent";
_raiseevent();
 //BA.debugLineNum = 44;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 46;BA.debugLine="Dim in As Intent";
_in = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 48;BA.debugLine="in.Initialize(\"android.settings.MANAGE_APP_ALL_FI";
_in.Initialize("android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION","package:com.bwsi.MeterReader");
 //BA.debugLineNum = 49;BA.debugLine="StartActivityForResult(in)";
_startactivityforresult(_in);
 //BA.debugLineNum = 50;BA.debugLine="End Sub";
return "";
}
public boolean  _haspermission() throws Exception{
boolean _has = false;
anywheresoftware.b4j.object.JavaObject _jo = null;
 //BA.debugLineNum = 30;BA.debugLine="Public Sub HasPermission As Boolean";
 //BA.debugLineNum = 31;BA.debugLine="Dim has As Boolean";
_has = false;
 //BA.debugLineNum = 32;BA.debugLine="Dim jo As JavaObject";
_jo = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 33;BA.debugLine="jo.InitializeStatic(\"android.os.Environment\")";
_jo.InitializeStatic("android.os.Environment");
 //BA.debugLineNum = 34;BA.debugLine="has = jo.RunMethod(\"isExternalStorageManager\", Nu";
_has = BA.ObjectToBoolean(_jo.RunMethod("isExternalStorageManager",(Object[])(__c.Null)));
 //BA.debugLineNum = 35;BA.debugLine="Return has";
if (true) return _has;
 //BA.debugLineNum = 36;BA.debugLine="End Sub";
return false;
}
public String  _initialize(anywheresoftware.b4a.BA _ba,Object _callback,String _eventname) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 22;BA.debugLine="Public Sub Initialize (Callback As Object, EventNa";
 //BA.debugLineNum = 23;BA.debugLine="mCallback = Callback";
_mcallback = _callback;
 //BA.debugLineNum = 24;BA.debugLine="mEventName = EventName";
_meventname = _eventname;
 //BA.debugLineNum = 25;BA.debugLine="End Sub";
return "";
}
public Object  _ion_event(String _methodname,Object[] _args) throws Exception{
 //BA.debugLineNum = 59;BA.debugLine="Private Sub ion_Event (MethodName As String, Args(";
 //BA.debugLineNum = 60;BA.debugLine="RaiseEvent";
_raiseevent();
 //BA.debugLineNum = 61;BA.debugLine="Return Null";
if (true) return __c.Null;
 //BA.debugLineNum = 62;BA.debugLine="End Sub";
return null;
}
public String  _raiseevent() throws Exception{
 //BA.debugLineNum = 53;BA.debugLine="Private Sub RaiseEvent";
 //BA.debugLineNum = 54;BA.debugLine="Log(\"Calling : \" &  mEventName & \"_StorageAvailab";
__c.LogImpl("149938433","Calling : "+_meventname+"_StorageAvailable",0);
 //BA.debugLineNum = 55;BA.debugLine="CallSubDelayed(mCallback, mEventName & \"_StorageA";
__c.CallSubDelayed(ba,_mcallback,_meventname+"_StorageAvailable");
 //BA.debugLineNum = 56;BA.debugLine="End Sub";
return "";
}
public String  _startactivityforresult(anywheresoftware.b4a.objects.IntentWrapper _i) throws Exception{
anywheresoftware.b4j.object.JavaObject _jo = null;
 //BA.debugLineNum = 65;BA.debugLine="Private Sub StartActivityForResult(i As Intent)";
 //BA.debugLineNum = 66;BA.debugLine="Dim jo As JavaObject = GetBA";
_jo = new anywheresoftware.b4j.object.JavaObject();
_jo = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(_getba()));
 //BA.debugLineNum = 67;BA.debugLine="ion = jo.CreateEvent(\"anywheresoftware.b4a.IOnAct";
_ion = _jo.CreateEvent(ba,"anywheresoftware.b4a.IOnActivityResult","ion",__c.Null);
 //BA.debugLineNum = 68;BA.debugLine="jo.RunMethod(\"startActivityForResult\", Array As O";
_jo.RunMethod("startActivityForResult",new Object[]{_ion,(Object)(_i.getObject())});
 //BA.debugLineNum = 69;BA.debugLine="End Sub";
return "";
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
return BA.SubDelegator.SubNotFound;
}
}
