package com.bwsi.MeterReader;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class cameraexclass extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "com.bwsi.MeterReader.cameraexclass");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            
        }
        if (BA.isShellModeRuntimeCheck(ba)) 
			   this.getClass().getMethod("_class_globals", com.bwsi.MeterReader.cameraexclass.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public Object _nativecam = null;
public anywheresoftware.b4a.objects.CameraW _cam = null;
public anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
public Object _target = null;
public String _event = "";
public boolean _front = false;
public Object _parameters = null;
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
public static class _camerainfoandid{
public boolean IsInitialized;
public Object CameraInfo;
public int Id;
public void Initialize() {
IsInitialized = true;
CameraInfo = new Object();
Id = 0;
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static class _camerasize{
public boolean IsInitialized;
public int Width;
public int Height;
public void Initialize() {
IsInitialized = true;
Width = 0;
Height = 0;
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public String  _camera_picturetaken(byte[] _data) throws Exception{
 //BA.debugLineNum = 106;BA.debugLine="Private Sub Camera_PictureTaken (Data() As Byte)";
 //BA.debugLineNum = 107;BA.debugLine="CallSub2(target, event & \"_PictureTaken\", Data)";
__c.CallSubNew2(ba,_target,_event+"_PictureTaken",(Object)(_data));
 //BA.debugLineNum = 108;BA.debugLine="End Sub";
return "";
}
public String  _camera_preview(byte[] _data) throws Exception{
 //BA.debugLineNum = 97;BA.debugLine="Sub Camera_Preview (Data() As Byte)";
 //BA.debugLineNum = 98;BA.debugLine="If SubExists(target, event & \"_preview\") Then";
if (__c.SubExists(ba,_target,_event+"_preview")) { 
 //BA.debugLineNum = 99;BA.debugLine="CallSub2(target, event & \"_preview\", Data)";
__c.CallSubNew2(ba,_target,_event+"_preview",(Object)(_data));
 };
 //BA.debugLineNum = 101;BA.debugLine="End Sub";
return "";
}
public String  _camera_ready(boolean _success) throws Exception{
 //BA.debugLineNum = 82;BA.debugLine="Private Sub Camera_Ready (Success As Boolean)";
 //BA.debugLineNum = 83;BA.debugLine="Try";
try { //BA.debugLineNum = 84;BA.debugLine="If Success Then";
if (_success) { 
 //BA.debugLineNum = 85;BA.debugLine="r.target = cam";
_r.Target = (Object)(_cam);
 //BA.debugLineNum = 86;BA.debugLine="nativeCam = r.GetField(\"camera\")";
_nativecam = _r.GetField("camera");
 //BA.debugLineNum = 87;BA.debugLine="r.target = nativeCam";
_r.Target = _nativecam;
 //BA.debugLineNum = 88;BA.debugLine="parameters = r.RunMethod(\"getParameters\")";
_parameters = _r.RunMethod("getParameters");
 //BA.debugLineNum = 89;BA.debugLine="SetDisplayOrientation";
_setdisplayorientation();
 };
 //BA.debugLineNum = 91;BA.debugLine="CallSub2(target, event & \"_ready\", Success)";
__c.CallSubNew2(ba,_target,_event+"_ready",(Object)(_success));
 } 
       catch (Exception e11) {
			ba.setLastException(e11); //BA.debugLineNum = 93;BA.debugLine="ToastMessageShow(\"Camera problem.\", False)";
__c.ToastMessageShow(BA.ObjectToCharSequence("Camera problem."),__c.False);
 };
 //BA.debugLineNum = 95;BA.debugLine="End Sub";
return "";
}
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 7;BA.debugLine="Private nativeCam As Object";
_nativecam = new Object();
 //BA.debugLineNum = 8;BA.debugLine="Private cam As Camera";
_cam = new anywheresoftware.b4a.objects.CameraW();
 //BA.debugLineNum = 9;BA.debugLine="Private r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 10;BA.debugLine="Private target As Object";
_target = new Object();
 //BA.debugLineNum = 11;BA.debugLine="Private event As String";
_event = "";
 //BA.debugLineNum = 12;BA.debugLine="Public Front As Boolean";
_front = false;
 //BA.debugLineNum = 13;BA.debugLine="Type CameraInfoAndId (CameraInfo As Object, Id As";
;
 //BA.debugLineNum = 14;BA.debugLine="Type CameraSize (Width As Int, Height As Int)";
;
 //BA.debugLineNum = 15;BA.debugLine="Private parameters As Object";
_parameters = new Object();
 //BA.debugLineNum = 16;BA.debugLine="End Sub";
return "";
}
public String  _commitparameters() throws Exception{
 //BA.debugLineNum = 139;BA.debugLine="Public Sub CommitParameters";
 //BA.debugLineNum = 140;BA.debugLine="Try";
try { //BA.debugLineNum = 141;BA.debugLine="r.target = nativeCam";
_r.Target = _nativecam;
 //BA.debugLineNum = 142;BA.debugLine="r.RunMethod4(\"setParameters\", Array As Object(pa";
_r.RunMethod4("setParameters",new Object[]{_parameters},new String[]{"android.hardware.Camera$Parameters"});
 } 
       catch (Exception e5) {
			ba.setLastException(e5); //BA.debugLineNum = 144;BA.debugLine="ToastMessageShow(\"Error setting parameters.\", Tr";
__c.ToastMessageShow(BA.ObjectToCharSequence("Error setting parameters."),__c.True);
 //BA.debugLineNum = 145;BA.debugLine="Log(LastException)";
__c.LogImpl("138469638",BA.ObjectToString(__c.LastException(ba)),0);
 };
 //BA.debugLineNum = 147;BA.debugLine="End Sub";
return "";
}
public com.bwsi.MeterReader.cameraexclass._camerainfoandid  _findcamera(boolean _frontcamera) throws Exception{
com.bwsi.MeterReader.cameraexclass._camerainfoandid _ci = null;
Object _camerainfo = null;
int _cameravalue = 0;
int _numberofcameras = 0;
int _i = 0;
 //BA.debugLineNum = 36;BA.debugLine="Private Sub FindCamera (frontCamera As Boolean) As";
 //BA.debugLineNum = 37;BA.debugLine="Dim ci As CameraInfoAndId";
_ci = new com.bwsi.MeterReader.cameraexclass._camerainfoandid();
 //BA.debugLineNum = 38;BA.debugLine="Dim cameraInfo As Object";
_camerainfo = new Object();
 //BA.debugLineNum = 39;BA.debugLine="Dim cameraValue As Int";
_cameravalue = 0;
 //BA.debugLineNum = 40;BA.debugLine="If frontCamera Then cameraValue = 1 Else cameraVa";
if (_frontcamera) { 
_cameravalue = (int) (1);}
else {
_cameravalue = (int) (0);};
 //BA.debugLineNum = 41;BA.debugLine="cameraInfo = r.CreateObject(\"android.hardware.Cam";
_camerainfo = _r.CreateObject("android.hardware.Camera$CameraInfo");
 //BA.debugLineNum = 42;BA.debugLine="Dim numberOfCameras As Int = r.RunStaticMethod(\"a";
_numberofcameras = (int)(BA.ObjectToNumber(_r.RunStaticMethod("android.hardware.Camera","getNumberOfCameras",(Object[])(__c.Null),(String[])(__c.Null))));
 //BA.debugLineNum = 43;BA.debugLine="For i = 0 To numberOfCameras - 1";
{
final int step7 = 1;
final int limit7 = (int) (_numberofcameras-1);
_i = (int) (0) ;
for (;_i <= limit7 ;_i = _i + step7 ) {
 //BA.debugLineNum = 44;BA.debugLine="r.RunStaticMethod(\"android.hardware.Camera\", \"ge";
_r.RunStaticMethod("android.hardware.Camera","getCameraInfo",new Object[]{(Object)(_i),_camerainfo},new String[]{"java.lang.int","android.hardware.Camera$CameraInfo"});
 //BA.debugLineNum = 46;BA.debugLine="r.target = cameraInfo";
_r.Target = _camerainfo;
 //BA.debugLineNum = 47;BA.debugLine="If r.GetField(\"facing\") = cameraValue Then";
if ((_r.GetField("facing")).equals((Object)(_cameravalue))) { 
 //BA.debugLineNum = 48;BA.debugLine="ci.cameraInfo = r.target";
_ci.CameraInfo /*Object*/  = _r.Target;
 //BA.debugLineNum = 49;BA.debugLine="ci.Id = i";
_ci.Id /*int*/  = _i;
 //BA.debugLineNum = 50;BA.debugLine="Return ci";
if (true) return _ci;
 };
 }
};
 //BA.debugLineNum = 53;BA.debugLine="ci.id = -1";
_ci.Id /*int*/  = (int) (-1);
 //BA.debugLineNum = 54;BA.debugLine="Return ci";
if (true) return _ci;
 //BA.debugLineNum = 55;BA.debugLine="End Sub";
return null;
}
public String  _getcoloreffect() throws Exception{
 //BA.debugLineNum = 149;BA.debugLine="Public Sub GetColorEffect As String";
 //BA.debugLineNum = 150;BA.debugLine="Return GetParameter(\"effect\")";
if (true) return _getparameter("effect");
 //BA.debugLineNum = 151;BA.debugLine="End Sub";
return "";
}
public String  _getflashmode() throws Exception{
 //BA.debugLineNum = 184;BA.debugLine="Public Sub GetFlashMode As String";
 //BA.debugLineNum = 185;BA.debugLine="r.target = parameters";
_r.Target = _parameters;
 //BA.debugLineNum = 186;BA.debugLine="Return r.RunMethod(\"getFlashMode\")";
if (true) return BA.ObjectToString(_r.RunMethod("getFlashMode"));
 //BA.debugLineNum = 187;BA.debugLine="End Sub";
return "";
}
public String  _getparameter(String _key) throws Exception{
 //BA.debugLineNum = 134;BA.debugLine="Public Sub GetParameter(Key As String) As String";
 //BA.debugLineNum = 135;BA.debugLine="r.target = parameters";
_r.Target = _parameters;
 //BA.debugLineNum = 136;BA.debugLine="Return r.RunMethod2(\"get\", Key, \"java.lang.String";
if (true) return BA.ObjectToString(_r.RunMethod2("get",_key,"java.lang.String"));
 //BA.debugLineNum = 137;BA.debugLine="End Sub";
return "";
}
public com.bwsi.MeterReader.cameraexclass._camerasize  _getpicturesize() throws Exception{
com.bwsi.MeterReader.cameraexclass._camerasize _cs = null;
 //BA.debugLineNum = 199;BA.debugLine="Public Sub GetPictureSize As CameraSize";
 //BA.debugLineNum = 200;BA.debugLine="r.target = parameters";
_r.Target = _parameters;
 //BA.debugLineNum = 201;BA.debugLine="r.target = r.RunMethod(\"getPictureSize\")";
_r.Target = _r.RunMethod("getPictureSize");
 //BA.debugLineNum = 202;BA.debugLine="Dim cs As CameraSize";
_cs = new com.bwsi.MeterReader.cameraexclass._camerasize();
 //BA.debugLineNum = 203;BA.debugLine="cs.Width = r.GetField(\"width\")";
_cs.Width /*int*/  = (int)(BA.ObjectToNumber(_r.GetField("width")));
 //BA.debugLineNum = 204;BA.debugLine="cs.Height = r.GetField(\"height\")";
_cs.Height /*int*/  = (int)(BA.ObjectToNumber(_r.GetField("height")));
 //BA.debugLineNum = 205;BA.debugLine="Return cs";
if (true) return _cs;
 //BA.debugLineNum = 206;BA.debugLine="End Sub";
return null;
}
public anywheresoftware.b4a.objects.collections.List  _getsupportedcoloreffects() throws Exception{
 //BA.debugLineNum = 194;BA.debugLine="Public Sub GetSupportedColorEffects As List";
 //BA.debugLineNum = 195;BA.debugLine="r.target = parameters";
_r.Target = _parameters;
 //BA.debugLineNum = 196;BA.debugLine="Return r.RunMethod(\"getSupportedColorEffects\")";
if (true) return (anywheresoftware.b4a.objects.collections.List) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.List(), (java.util.List)(_r.RunMethod("getSupportedColorEffects")));
 //BA.debugLineNum = 197;BA.debugLine="End Sub";
return null;
}
public anywheresoftware.b4a.objects.collections.List  _getsupportedflashmodes() throws Exception{
 //BA.debugLineNum = 189;BA.debugLine="Public Sub GetSupportedFlashModes As List";
 //BA.debugLineNum = 190;BA.debugLine="r.target = parameters";
_r.Target = _parameters;
 //BA.debugLineNum = 191;BA.debugLine="Return r.RunMethod(\"getSupportedFlashModes\")";
if (true) return (anywheresoftware.b4a.objects.collections.List) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.List(), (java.util.List)(_r.RunMethod("getSupportedFlashModes")));
 //BA.debugLineNum = 192;BA.debugLine="End Sub";
return null;
}
public com.bwsi.MeterReader.cameraexclass._camerasize[]  _getsupportedpicturessizes() throws Exception{
anywheresoftware.b4a.objects.collections.List _list1 = null;
com.bwsi.MeterReader.cameraexclass._camerasize[] _cs = null;
int _i = 0;
 //BA.debugLineNum = 157;BA.debugLine="Public Sub GetSupportedPicturesSizes As CameraSize";
 //BA.debugLineNum = 158;BA.debugLine="r.target = parameters";
_r.Target = _parameters;
 //BA.debugLineNum = 159;BA.debugLine="Dim list1 As List = r.RunMethod(\"getSupportedPict";
_list1 = new anywheresoftware.b4a.objects.collections.List();
_list1 = (anywheresoftware.b4a.objects.collections.List) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.List(), (java.util.List)(_r.RunMethod("getSupportedPictureSizes")));
 //BA.debugLineNum = 160;BA.debugLine="Dim cs(list1.Size) As CameraSize";
_cs = new com.bwsi.MeterReader.cameraexclass._camerasize[_list1.getSize()];
{
int d0 = _cs.length;
for (int i0 = 0;i0 < d0;i0++) {
_cs[i0] = new com.bwsi.MeterReader.cameraexclass._camerasize();
}
}
;
 //BA.debugLineNum = 161;BA.debugLine="For i = 0 To list1.Size - 1";
{
final int step4 = 1;
final int limit4 = (int) (_list1.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit4 ;_i = _i + step4 ) {
 //BA.debugLineNum = 162;BA.debugLine="r.target = list1.Get(i)";
_r.Target = _list1.Get(_i);
 //BA.debugLineNum = 163;BA.debugLine="cs(i).Width = r.GetField(\"width\")";
_cs[_i].Width /*int*/  = (int)(BA.ObjectToNumber(_r.GetField("width")));
 //BA.debugLineNum = 164;BA.debugLine="cs(i).Height = r.GetField(\"height\")";
_cs[_i].Height /*int*/  = (int)(BA.ObjectToNumber(_r.GetField("height")));
 }
};
 //BA.debugLineNum = 166;BA.debugLine="Return cs";
if (true) return _cs;
 //BA.debugLineNum = 167;BA.debugLine="End Sub";
return null;
}
public String  _initialize(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.PanelWrapper _panel1,boolean _frontcamera,Object _targetmodule,String _eventname) throws Exception{
innerInitialize(_ba);
int _id = 0;
 //BA.debugLineNum = 18;BA.debugLine="Public Sub Initialize (Panel1 As Panel, FrontCamer";
 //BA.debugLineNum = 19;BA.debugLine="target = TargetModule";
_target = _targetmodule;
 //BA.debugLineNum = 20;BA.debugLine="event = EventName";
_event = _eventname;
 //BA.debugLineNum = 21;BA.debugLine="Front = FrontCamera";
_front = _frontcamera;
 //BA.debugLineNum = 22;BA.debugLine="Dim id As Int";
_id = 0;
 //BA.debugLineNum = 23;BA.debugLine="id = FindCamera(Front).id";
_id = _findcamera(_front).Id /*int*/ ;
 //BA.debugLineNum = 24;BA.debugLine="If id = -1 Then";
if (_id==-1) { 
 //BA.debugLineNum = 25;BA.debugLine="Front = Not(Front) 'try different camera";
_front = __c.Not(_front);
 //BA.debugLineNum = 26;BA.debugLine="id = FindCamera(Front).id";
_id = _findcamera(_front).Id /*int*/ ;
 //BA.debugLineNum = 27;BA.debugLine="If id = -1 Then";
if (_id==-1) { 
 //BA.debugLineNum = 28;BA.debugLine="ToastMessageShow(\"No camera found.\", True)";
__c.ToastMessageShow(BA.ObjectToCharSequence("No camera found."),__c.True);
 //BA.debugLineNum = 29;BA.debugLine="Return";
if (true) return "";
 };
 };
 //BA.debugLineNum = 33;BA.debugLine="cam.Initialize2(Panel1, \"camera\", id)";
_cam.Initialize2(ba,(android.view.ViewGroup)(_panel1.getObject()),"camera",_id);
 //BA.debugLineNum = 34;BA.debugLine="End Sub";
return "";
}
public byte[]  _previewimagetojpeg(byte[] _data,int _quality) throws Exception{
Object _size = null;
Object _previewformat = null;
int _width = 0;
int _height = 0;
Object _yuvimage = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper _rect1 = null;
anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _out = null;
 //BA.debugLineNum = 210;BA.debugLine="Sub PreviewImageToJpeg(data() As Byte, quality As";
 //BA.debugLineNum = 211;BA.debugLine="Dim size, previewFormat As Object";
_size = new Object();
_previewformat = new Object();
 //BA.debugLineNum = 212;BA.debugLine="r.target = parameters";
_r.Target = _parameters;
 //BA.debugLineNum = 213;BA.debugLine="size = r.RunMethod(\"getPreviewSize\")";
_size = _r.RunMethod("getPreviewSize");
 //BA.debugLineNum = 214;BA.debugLine="previewFormat = r.RunMethod(\"getPreviewFormat\")";
_previewformat = _r.RunMethod("getPreviewFormat");
 //BA.debugLineNum = 215;BA.debugLine="r.target = size";
_r.Target = _size;
 //BA.debugLineNum = 216;BA.debugLine="Dim width = r.GetField(\"width\"), height = r.GetFi";
_width = (int)(BA.ObjectToNumber(_r.GetField("width")));
_height = (int)(BA.ObjectToNumber(_r.GetField("height")));
 //BA.debugLineNum = 217;BA.debugLine="Dim yuvImage As Object = r.CreateObject2(\"android";
_yuvimage = _r.CreateObject2("android.graphics.YuvImage",new Object[]{(Object)(_data),_previewformat,(Object)(_width),(Object)(_height),__c.Null},new String[]{"[B","java.lang.int","java.lang.int","java.lang.int","[I"});
 //BA.debugLineNum = 220;BA.debugLine="r.target = yuvImage";
_r.Target = _yuvimage;
 //BA.debugLineNum = 221;BA.debugLine="Dim rect1 As Rect";
_rect1 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper();
 //BA.debugLineNum = 222;BA.debugLine="rect1.Initialize(0, 0, r.RunMethod(\"getWidth\"), r";
_rect1.Initialize((int) (0),(int) (0),(int)(BA.ObjectToNumber(_r.RunMethod("getWidth"))),(int)(BA.ObjectToNumber(_r.RunMethod("getHeight"))));
 //BA.debugLineNum = 223;BA.debugLine="Dim out As OutputStream";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 224;BA.debugLine="out.InitializeToBytesArray(100)";
_out.InitializeToBytesArray((int) (100));
 //BA.debugLineNum = 225;BA.debugLine="r.RunMethod4(\"compressToJpeg\", Array As Object(re";
_r.RunMethod4("compressToJpeg",new Object[]{(Object)(_rect1.getObject()),(Object)(_quality),(Object)(_out.getObject())},new String[]{"android.graphics.Rect","java.lang.int","java.io.OutputStream"});
 //BA.debugLineNum = 227;BA.debugLine="Return out.ToBytesArray";
if (true) return _out.ToBytesArray();
 //BA.debugLineNum = 228;BA.debugLine="End Sub";
return null;
}
public String  _release() throws Exception{
 //BA.debugLineNum = 118;BA.debugLine="Public Sub Release";
 //BA.debugLineNum = 119;BA.debugLine="cam.Release";
_cam.Release();
 //BA.debugLineNum = 120;BA.debugLine="End Sub";
return "";
}
public String  _savepicturetofile(byte[] _data,String _dir,String _filename) throws Exception{
anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _out = null;
 //BA.debugLineNum = 123;BA.debugLine="Public Sub SavePictureToFile(Data() As Byte, Dir A";
 //BA.debugLineNum = 124;BA.debugLine="Dim out As OutputStream = File.OpenOutput(Dir, Fi";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
_out = __c.File.OpenOutput(_dir,_filename,__c.False);
 //BA.debugLineNum = 125;BA.debugLine="out.WriteBytes(Data, 0, Data.Length)";
_out.WriteBytes(_data,(int) (0),_data.length);
 //BA.debugLineNum = 126;BA.debugLine="out.Close";
_out.Close();
 //BA.debugLineNum = 127;BA.debugLine="End Sub";
return "";
}
public String  _setcoloreffect(String _effect) throws Exception{
 //BA.debugLineNum = 153;BA.debugLine="Public Sub SetColorEffect(Effect As String)";
 //BA.debugLineNum = 154;BA.debugLine="SetParameter(\"effect\", Effect)";
_setparameter("effect",_effect);
 //BA.debugLineNum = 155;BA.debugLine="End Sub";
return "";
}
public String  _setdisplayorientation() throws Exception{
int _previewresult = 0;
int _result = 0;
int _degrees = 0;
com.bwsi.MeterReader.cameraexclass._camerainfoandid _ci = null;
int _orientation = 0;
 //BA.debugLineNum = 57;BA.debugLine="Private Sub SetDisplayOrientation";
 //BA.debugLineNum = 58;BA.debugLine="r.target = r.GetActivity";
_r.Target = (Object)(_r.GetActivity(ba));
 //BA.debugLineNum = 59;BA.debugLine="r.target = r.RunMethod(\"getWindowManager\")";
_r.Target = _r.RunMethod("getWindowManager");
 //BA.debugLineNum = 60;BA.debugLine="r.target = r.RunMethod(\"getDefaultDisplay\")";
_r.Target = _r.RunMethod("getDefaultDisplay");
 //BA.debugLineNum = 61;BA.debugLine="r.target = r.RunMethod(\"getRotation\")";
_r.Target = _r.RunMethod("getRotation");
 //BA.debugLineNum = 62;BA.debugLine="Dim previewResult, result, degrees As Int = r.tar";
_previewresult = 0;
_result = 0;
_degrees = (int) ((double)(BA.ObjectToNumber(_r.Target))*90);
 //BA.debugLineNum = 63;BA.debugLine="Dim ci As CameraInfoAndId = FindCamera(Front)";
_ci = _findcamera(_front);
 //BA.debugLineNum = 64;BA.debugLine="r.target = ci.CameraInfo";
_r.Target = _ci.CameraInfo /*Object*/ ;
 //BA.debugLineNum = 65;BA.debugLine="Dim orientation As Int = r.GetField(\"orientation\"";
_orientation = (int)(BA.ObjectToNumber(_r.GetField("orientation")));
 //BA.debugLineNum = 66;BA.debugLine="If Front Then";
if (_front) { 
 //BA.debugLineNum = 67;BA.debugLine="previewResult = (orientation + degrees) Mod 360";
_previewresult = (int) ((_orientation+_degrees)%360);
 //BA.debugLineNum = 68;BA.debugLine="result = previewResult";
_result = _previewresult;
 //BA.debugLineNum = 69;BA.debugLine="previewResult = (360 - previewResult) Mod 360";
_previewresult = (int) ((360-_previewresult)%360);
 }else {
 //BA.debugLineNum = 71;BA.debugLine="previewResult = (orientation - degrees + 360) Mo";
_previewresult = (int) ((_orientation-_degrees+360)%360);
 //BA.debugLineNum = 72;BA.debugLine="result = previewResult";
_result = _previewresult;
 //BA.debugLineNum = 73;BA.debugLine="Log(previewResult)";
__c.LogImpl("137748752",BA.NumberToString(_previewresult),0);
 };
 //BA.debugLineNum = 75;BA.debugLine="r.target = nativeCam";
_r.Target = _nativecam;
 //BA.debugLineNum = 76;BA.debugLine="r.RunMethod2(\"setDisplayOrientation\", previewResu";
_r.RunMethod2("setDisplayOrientation",BA.NumberToString(_previewresult),"java.lang.int");
 //BA.debugLineNum = 77;BA.debugLine="r.target = parameters";
_r.Target = _parameters;
 //BA.debugLineNum = 78;BA.debugLine="r.RunMethod2(\"setRotation\", result, \"java.lang.in";
_r.RunMethod2("setRotation",BA.NumberToString(_result),"java.lang.int");
 //BA.debugLineNum = 79;BA.debugLine="CommitParameters";
_commitparameters();
 //BA.debugLineNum = 80;BA.debugLine="End Sub";
return "";
}
public String  _setflashmode(String _mode) throws Exception{
 //BA.debugLineNum = 179;BA.debugLine="Public Sub SetFlashMode(Mode As String)";
 //BA.debugLineNum = 180;BA.debugLine="r.target = parameters";
_r.Target = _parameters;
 //BA.debugLineNum = 181;BA.debugLine="r.RunMethod2(\"setFlashMode\", Mode, \"java.lang.Str";
_r.RunMethod2("setFlashMode",_mode,"java.lang.String");
 //BA.debugLineNum = 182;BA.debugLine="End Sub";
return "";
}
public String  _setjpegquality(int _quality) throws Exception{
 //BA.debugLineNum = 174;BA.debugLine="Public Sub SetJpegQuality(Quality As Int)";
 //BA.debugLineNum = 175;BA.debugLine="r.target = parameters";
_r.Target = _parameters;
 //BA.debugLineNum = 176;BA.debugLine="r.RunMethod2(\"setJpegQuality\", Quality, \"java.lan";
_r.RunMethod2("setJpegQuality",BA.NumberToString(_quality),"java.lang.int");
 //BA.debugLineNum = 177;BA.debugLine="End Sub";
return "";
}
public String  _setparameter(String _key,String _value) throws Exception{
 //BA.debugLineNum = 129;BA.debugLine="Public Sub SetParameter(Key As String, Value As St";
 //BA.debugLineNum = 130;BA.debugLine="r.target = parameters";
_r.Target = _parameters;
 //BA.debugLineNum = 131;BA.debugLine="r.RunMethod3(\"set\", Key, \"java.lang.String\", Valu";
_r.RunMethod3("set",_key,"java.lang.String",_value,"java.lang.String");
 //BA.debugLineNum = 132;BA.debugLine="End Sub";
return "";
}
public String  _setpicturesize(int _width,int _height) throws Exception{
 //BA.debugLineNum = 169;BA.debugLine="Public Sub SetPictureSize(Width As Int, Height As";
 //BA.debugLineNum = 170;BA.debugLine="r.target = parameters";
_r.Target = _parameters;
 //BA.debugLineNum = 171;BA.debugLine="r.RunMethod3(\"setPictureSize\", Width, \"java.lang.";
_r.RunMethod3("setPictureSize",BA.NumberToString(_width),"java.lang.int",BA.NumberToString(_height),"java.lang.int");
 //BA.debugLineNum = 172;BA.debugLine="End Sub";
return "";
}
public String  _startpreview() throws Exception{
 //BA.debugLineNum = 110;BA.debugLine="Public Sub StartPreview";
 //BA.debugLineNum = 111;BA.debugLine="cam.StartPreview";
_cam.StartPreview();
 //BA.debugLineNum = 112;BA.debugLine="End Sub";
return "";
}
public String  _stoppreview() throws Exception{
 //BA.debugLineNum = 114;BA.debugLine="Public Sub StopPreview";
 //BA.debugLineNum = 115;BA.debugLine="cam.StopPreview";
_cam.StopPreview();
 //BA.debugLineNum = 116;BA.debugLine="End Sub";
return "";
}
public String  _takepicture() throws Exception{
 //BA.debugLineNum = 102;BA.debugLine="Public Sub TakePicture";
 //BA.debugLineNum = 103;BA.debugLine="cam.TakePicture";
_cam.TakePicture();
 //BA.debugLineNum = 104;BA.debugLine="End Sub";
return "";
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
return BA.SubDelegator.SubNotFound;
}
}
