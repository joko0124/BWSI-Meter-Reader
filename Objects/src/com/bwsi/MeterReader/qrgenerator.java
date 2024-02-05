package com.bwsi.MeterReader;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class qrgenerator extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "com.bwsi.MeterReader.qrgenerator");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            
        }
        if (BA.isShellModeRuntimeCheck(ba)) 
			   this.getClass().getMethod("_class_globals", com.bwsi.MeterReader.qrgenerator.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public anywheresoftware.b4a.objects.B4XCanvas _cvs = null;
public int _modulesize = 0;
public int _qrversion = 0;
public int _gfsize = 0;
public int[] _exptable = null;
public int[] _logtable = null;
public int[] _polyzero = null;
public int[] _generator4l = null;
public int[] _generator4h = null;
public com.bwsi.MeterReader.bytesbuilder _tempbb = null;
public boolean[][] _matrix = null;
public boolean[][] _reserved = null;
public int _numberofmodules = 0;
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
public anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper  _addbitmap(anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper _qrbmp,anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper _logo,int _alpha) throws Exception{
b4a.example.bitmapcreator _bc = null;
b4a.example.bitmapcreator _logobc = null;
b4a.example.bitmapcreator._argbcolor _argb = null;
b4a.example.bitmapcreator._argbcolor _largb = null;
int _x = 0;
int _y = 0;
 //BA.debugLineNum = 401;BA.debugLine="Public Sub AddBitmap(QRBmp As B4XBitmap, logo As B";
 //BA.debugLineNum = 402;BA.debugLine="Dim bc As BitmapCreator";
_bc = new b4a.example.bitmapcreator();
 //BA.debugLineNum = 403;BA.debugLine="bc.Initialize(QRBmp.Width, QRBmp.Height)";
_bc._initialize(ba,(int) (_qrbmp.getWidth()),(int) (_qrbmp.getHeight()));
 //BA.debugLineNum = 404;BA.debugLine="bc.CopyPixelsFromBitmap(QRBmp)";
_bc._copypixelsfrombitmap(_qrbmp);
 //BA.debugLineNum = 405;BA.debugLine="Dim LogoBC As BitmapCreator";
_logobc = new b4a.example.bitmapcreator();
 //BA.debugLineNum = 406;BA.debugLine="LogoBC.Initialize(QRBmp.Width, QRBmp.Height)";
_logobc._initialize(ba,(int) (_qrbmp.getWidth()),(int) (_qrbmp.getHeight()));
 //BA.debugLineNum = 407;BA.debugLine="LogoBC.CopyPixelsFromBitmap(logo)";
_logobc._copypixelsfrombitmap(_logo);
 //BA.debugLineNum = 408;BA.debugLine="Dim argb, largb As ARGBColor";
_argb = new b4a.example.bitmapcreator._argbcolor();
_largb = new b4a.example.bitmapcreator._argbcolor();
 //BA.debugLineNum = 409;BA.debugLine="For x = 0 To bc.mWidth - 1";
{
final int step8 = 1;
final int limit8 = (int) (_bc._mwidth-1);
_x = (int) (0) ;
for (;_x <= limit8 ;_x = _x + step8 ) {
 //BA.debugLineNum = 410;BA.debugLine="For y = 0 To bc.mHeight - 1";
{
final int step9 = 1;
final int limit9 = (int) (_bc._mheight-1);
_y = (int) (0) ;
for (;_y <= limit9 ;_y = _y + step9 ) {
 //BA.debugLineNum = 411;BA.debugLine="bc.GetARGB(x, y, argb)";
_bc._getargb(_x,_y,_argb);
 //BA.debugLineNum = 412;BA.debugLine="LogoBC.GetARGB(x, y, largb)";
_logobc._getargb(_x,_y,_largb);
 //BA.debugLineNum = 413;BA.debugLine="If largb.a > 0 Then";
if (_largb.a>0) { 
 //BA.debugLineNum = 414;BA.debugLine="largb.a = Alpha";
_largb.a = _alpha;
 //BA.debugLineNum = 415;BA.debugLine="LogoBC.SetARGB(x, y, largb)";
_logobc._setargb(_x,_y,_largb);
 //BA.debugLineNum = 416;BA.debugLine="bc.BlendPixel(LogoBC, x, y, x, y)";
_bc._blendpixel(_logobc,_x,_y,_x,_y);
 };
 }
};
 }
};
 //BA.debugLineNum = 420;BA.debugLine="Return bc.Bitmap";
if (true) return _bc._getbitmap();
 //BA.debugLineNum = 421;BA.debugLine="End Sub";
return null;
}
public String  _adddatatomatrix(byte[] _encoded,boolean _ver4h) throws Exception{
anywheresoftware.b4a.objects.collections.List _order = null;
byte _b = (byte)0;
int[] _xy = null;
byte[] _format = null;
int _i = 0;
 //BA.debugLineNum = 137;BA.debugLine="Private Sub AddDataToMatrix (Encoded() As Byte, Ve";
 //BA.debugLineNum = 138;BA.debugLine="Dim order As List = CreateOrder";
_order = new anywheresoftware.b4a.objects.collections.List();
_order = _createorder();
 //BA.debugLineNum = 140;BA.debugLine="For Each b As Byte In Encoded";
{
final byte[] group2 = _encoded;
final int groupLen2 = group2.length
;int index2 = 0;
;
for (; index2 < groupLen2;index2++){
_b = group2[index2];
 //BA.debugLineNum = 141;BA.debugLine="Dim xy() As Int = GetNextPosition(order)";
_xy = _getnextposition(_order);
 //BA.debugLineNum = 142;BA.debugLine="Matrix(xy(0), xy(1)) = (b = 1)";
_matrix[_xy[(int) (0)]][_xy[(int) (1)]] = (_b==1);
 //BA.debugLineNum = 143;BA.debugLine="If (xy(1) + xy(0)) Mod 2 = 0 Then Matrix(xy(0),";
if ((_xy[(int) (1)]+_xy[(int) (0)])%2==0) { 
_matrix[_xy[(int) (0)]][_xy[(int) (1)]] = __c.Not(_matrix[_xy[(int) (0)]][_xy[(int) (1)]]);};
 }
};
 //BA.debugLineNum = 145;BA.debugLine="If Ver4H Then";
if (_ver4h) { 
 //BA.debugLineNum = 146;BA.debugLine="Dim format() As Byte = Array As Byte(0,0,1,0,1,1";
_format = new byte[]{(byte) (0),(byte) (0),(byte) (1),(byte) (0),(byte) (1),(byte) (1),(byte) (0),(byte) (1),(byte) (0),(byte) (0),(byte) (0),(byte) (1),(byte) (0),(byte) (0),(byte) (1)};
 }else {
 //BA.debugLineNum = 148;BA.debugLine="Dim format() As Byte = Array As Byte(1,1,1,0,1,1";
_format = new byte[]{(byte) (1),(byte) (1),(byte) (1),(byte) (0),(byte) (1),(byte) (1),(byte) (1),(byte) (1),(byte) (1),(byte) (0),(byte) (0),(byte) (0),(byte) (1),(byte) (0),(byte) (0)};
 };
 //BA.debugLineNum = 150;BA.debugLine="For i = 0 To 5";
{
final int step12 = 1;
final int limit12 = (int) (5);
_i = (int) (0) ;
for (;_i <= limit12 ;_i = _i + step12 ) {
 //BA.debugLineNum = 151;BA.debugLine="Matrix(i, 8) = format(i) = 1";
_matrix[_i][(int) (8)] = _format[_i]==1;
 //BA.debugLineNum = 152;BA.debugLine="Matrix(8, NumberOfModules - 1 - i) = format(i) =";
_matrix[(int) (8)][(int) (_numberofmodules-1-_i)] = _format[_i]==1;
 }
};
 //BA.debugLineNum = 154;BA.debugLine="Matrix(7, 8) = format(6) = 1";
_matrix[(int) (7)][(int) (8)] = _format[(int) (6)]==1;
 //BA.debugLineNum = 155;BA.debugLine="Matrix(8, NumberOfModules - 1 - 6) = format(6) =";
_matrix[(int) (8)][(int) (_numberofmodules-1-6)] = _format[(int) (6)]==1;
 //BA.debugLineNum = 156;BA.debugLine="Matrix(8, 8) = format(7) = 1";
_matrix[(int) (8)][(int) (8)] = _format[(int) (7)]==1;
 //BA.debugLineNum = 157;BA.debugLine="Matrix(8, 7) = format(8) = 1";
_matrix[(int) (8)][(int) (7)] = _format[(int) (8)]==1;
 //BA.debugLineNum = 158;BA.debugLine="For i = 0 To 5";
{
final int step20 = 1;
final int limit20 = (int) (5);
_i = (int) (0) ;
for (;_i <= limit20 ;_i = _i + step20 ) {
 //BA.debugLineNum = 159;BA.debugLine="Matrix(8, 5 - i) = format(i + 9) = 1";
_matrix[(int) (8)][(int) (5-_i)] = _format[(int) (_i+9)]==1;
 }
};
 //BA.debugLineNum = 161;BA.debugLine="For i = 0 To 7";
{
final int step23 = 1;
final int limit23 = (int) (7);
_i = (int) (0) ;
for (;_i <= limit23 ;_i = _i + step23 ) {
 //BA.debugLineNum = 162;BA.debugLine="Matrix(NumberOfModules - 1 - 7 + i, 8) = format(";
_matrix[(int) (_numberofmodules-1-7+_i)][(int) (8)] = _format[(int) (7+_i)]==1;
 }
};
 //BA.debugLineNum = 165;BA.debugLine="End Sub";
return "";
}
public String  _addfinder(int _left,int _top,int _sizeminone) throws Exception{
int _y = 0;
int _x = 0;
boolean _value = false;
 //BA.debugLineNum = 273;BA.debugLine="Private Sub AddFinder (Left As Int, Top As Int, Si";
 //BA.debugLineNum = 274;BA.debugLine="For y = 0 To SizeMinOne";
{
final int step1 = 1;
final int limit1 = _sizeminone;
_y = (int) (0) ;
for (;_y <= limit1 ;_y = _y + step1 ) {
 //BA.debugLineNum = 275;BA.debugLine="For x = 0 To SizeMinOne";
{
final int step2 = 1;
final int limit2 = _sizeminone;
_x = (int) (0) ;
for (;_x <= limit2 ;_x = _x + step2 ) {
 //BA.debugLineNum = 276;BA.debugLine="Dim value As Boolean";
_value = false;
 //BA.debugLineNum = 277;BA.debugLine="If x = 0 Or x = SizeMinOne Or y = 0 Or y = Size";
if (_x==0 || _x==_sizeminone || _y==0 || _y==_sizeminone) { 
 //BA.debugLineNum = 278;BA.debugLine="value = True";
_value = __c.True;
 }else if(_x!=1 && _y!=1 && _x!=_sizeminone-1 && _y!=_sizeminone-1) { 
 //BA.debugLineNum = 280;BA.debugLine="value = True";
_value = __c.True;
 };
 //BA.debugLineNum = 282;BA.debugLine="Matrix(Left + x, Top + y) = value";
_matrix[(int) (_left+_x)][(int) (_top+_y)] = _value;
 //BA.debugLineNum = 283;BA.debugLine="Reserved(Left + x, Top + y) = True";
_reserved[(int) (_left+_x)][(int) (_top+_y)] = __c.True;
 }
};
 }
};
 //BA.debugLineNum = 286;BA.debugLine="End Sub";
return "";
}
public String  _addfinders() throws Exception{
int _i = 0;
 //BA.debugLineNum = 242;BA.debugLine="Private Sub AddFinders";
 //BA.debugLineNum = 243;BA.debugLine="AddFinder(0, 0, 6)";
_addfinder((int) (0),(int) (0),(int) (6));
 //BA.debugLineNum = 244;BA.debugLine="AddFinder(NumberOfModules - 7, 0, 6)";
_addfinder((int) (_numberofmodules-7),(int) (0),(int) (6));
 //BA.debugLineNum = 245;BA.debugLine="AddFinder(0, NumberOfModules - 7, 6)";
_addfinder((int) (0),(int) (_numberofmodules-7),(int) (6));
 //BA.debugLineNum = 247;BA.debugLine="AddFinder(26 - 2, 26 - 2, 4)";
_addfinder((int) (26-2),(int) (26-2),(int) (4));
 //BA.debugLineNum = 249;BA.debugLine="For i = 8 To NumberOfModules - 8";
{
final int step5 = 1;
final int limit5 = (int) (_numberofmodules-8);
_i = (int) (8) ;
for (;_i <= limit5 ;_i = _i + step5 ) {
 //BA.debugLineNum = 250;BA.debugLine="Matrix(i, 6) = (i Mod 2 = 0)";
_matrix[_i][(int) (6)] = (_i%2==0);
 //BA.debugLineNum = 251;BA.debugLine="Matrix(6, i) = (i Mod 2 = 0)";
_matrix[(int) (6)][_i] = (_i%2==0);
 //BA.debugLineNum = 252;BA.debugLine="Reserved(i, 6) = True";
_reserved[_i][(int) (6)] = __c.True;
 //BA.debugLineNum = 253;BA.debugLine="Reserved(6, i) = True";
_reserved[(int) (6)][_i] = __c.True;
 }
};
 //BA.debugLineNum = 255;BA.debugLine="Matrix(8, NumberOfModules - 1 - 7) = True";
_matrix[(int) (8)][(int) (_numberofmodules-1-7)] = __c.True;
 //BA.debugLineNum = 256;BA.debugLine="Reserved(8, NumberOfModules - 1 - 7) = True";
_reserved[(int) (8)][(int) (_numberofmodules-1-7)] = __c.True;
 //BA.debugLineNum = 257;BA.debugLine="For i = 0 To 7";
{
final int step13 = 1;
final int limit13 = (int) (7);
_i = (int) (0) ;
for (;_i <= limit13 ;_i = _i + step13 ) {
 //BA.debugLineNum = 258;BA.debugLine="Reserved(7, i) = True";
_reserved[(int) (7)][_i] = __c.True;
 //BA.debugLineNum = 259;BA.debugLine="Reserved(7, NumberOfModules - 1 - i) = True";
_reserved[(int) (7)][(int) (_numberofmodules-1-_i)] = __c.True;
 //BA.debugLineNum = 260;BA.debugLine="Reserved(8, NumberOfModules - 1 - i) = True";
_reserved[(int) (8)][(int) (_numberofmodules-1-_i)] = __c.True;
 //BA.debugLineNum = 261;BA.debugLine="Reserved(NumberOfModules -1 - 7, i) = True";
_reserved[(int) (_numberofmodules-1-7)][_i] = __c.True;
 //BA.debugLineNum = 262;BA.debugLine="Reserved(i, 7) = True";
_reserved[_i][(int) (7)] = __c.True;
 //BA.debugLineNum = 263;BA.debugLine="Reserved(i,NumberOfModules -1 - 7) = True";
_reserved[_i][(int) (_numberofmodules-1-7)] = __c.True;
 //BA.debugLineNum = 264;BA.debugLine="Reserved(NumberOfModules -1 - i, 7) = True";
_reserved[(int) (_numberofmodules-1-_i)][(int) (7)] = __c.True;
 //BA.debugLineNum = 265;BA.debugLine="Reserved(NumberOfModules -1 - i, 8) = True";
_reserved[(int) (_numberofmodules-1-_i)][(int) (8)] = __c.True;
 }
};
 //BA.debugLineNum = 267;BA.debugLine="For i = 0 To 8";
{
final int step23 = 1;
final int limit23 = (int) (8);
_i = (int) (0) ;
for (;_i <= limit23 ;_i = _i + step23 ) {
 //BA.debugLineNum = 268;BA.debugLine="Reserved(8, i) = True";
_reserved[(int) (8)][_i] = __c.True;
 //BA.debugLineNum = 269;BA.debugLine="Reserved(i, 8) = True";
_reserved[_i][(int) (8)] = __c.True;
 }
};
 //BA.debugLineNum = 271;BA.debugLine="End Sub";
return "";
}
public int  _bitstounsignedbyte(byte[] _b,int _offset) throws Exception{
int _res = 0;
int _i = 0;
int _x = 0;
 //BA.debugLineNum = 218;BA.debugLine="Private Sub BitsToUnsignedByte (b() As Byte, Offse";
 //BA.debugLineNum = 219;BA.debugLine="Dim res As Int";
_res = 0;
 //BA.debugLineNum = 220;BA.debugLine="For i = 0 To 7";
{
final int step2 = 1;
final int limit2 = (int) (7);
_i = (int) (0) ;
for (;_i <= limit2 ;_i = _i + step2 ) {
 //BA.debugLineNum = 221;BA.debugLine="Dim x As Int = Bit.ShiftLeft(1, 7 - i)";
_x = __c.Bit.ShiftLeft((int) (1),(int) (7-_i));
 //BA.debugLineNum = 222;BA.debugLine="res = res + b(i + Offset) * x";
_res = (int) (_res+_b[(int) (_i+_offset)]*_x);
 }
};
 //BA.debugLineNum = 224;BA.debugLine="Return res";
if (true) return _res;
 //BA.debugLineNum = 225;BA.debugLine="End Sub";
return 0;
}
public int[]  _calcreedsolomon(int[] _input,int[] _generator) throws Exception{
int _ecbytes = 0;
int[] _infocoefficients = null;
int[] _remainder = null;
 //BA.debugLineNum = 290;BA.debugLine="Private Sub CalcReedSolomon (Input() As Int, Gener";
 //BA.debugLineNum = 291;BA.debugLine="Dim ecBytes As Int = Generator.Length - 1";
_ecbytes = (int) (_generator.length-1);
 //BA.debugLineNum = 292;BA.debugLine="Dim InfoCoefficients(Input.Length) As Int";
_infocoefficients = new int[_input.length];
;
 //BA.debugLineNum = 293;BA.debugLine="IntArrayCopy(Input, 0, InfoCoefficients, 0, Input";
_intarraycopy(_input,(int) (0),_infocoefficients,(int) (0),_input.length);
 //BA.debugLineNum = 294;BA.debugLine="InfoCoefficients = CreateGFPoly(InfoCoefficients)";
_infocoefficients = _creategfpoly(_infocoefficients);
 //BA.debugLineNum = 295;BA.debugLine="InfoCoefficients = PolyMultiplyByMonomial(InfoCoe";
_infocoefficients = _polymultiplybymonomial(_infocoefficients,_ecbytes,(int) (1));
 //BA.debugLineNum = 296;BA.debugLine="Dim remainder() As Int = PolyDivide(InfoCoefficie";
_remainder = _polydivide(_infocoefficients,_generator);
 //BA.debugLineNum = 297;BA.debugLine="Return remainder";
if (true) return _remainder;
 //BA.debugLineNum = 298;BA.debugLine="End Sub";
return null;
}
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 2;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 3;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 4;BA.debugLine="Public cvs As B4XCanvas";
_cvs = new anywheresoftware.b4a.objects.B4XCanvas();
 //BA.debugLineNum = 5;BA.debugLine="Private ModuleSize As Int";
_modulesize = 0;
 //BA.debugLineNum = 6;BA.debugLine="Private const QRVersion As Int = 4";
_qrversion = (int) (4);
 //BA.debugLineNum = 7;BA.debugLine="Private GFSize As Int = 256";
_gfsize = (int) (256);
 //BA.debugLineNum = 8;BA.debugLine="Private ExpTable(GFSize) As Int";
_exptable = new int[_gfsize];
;
 //BA.debugLineNum = 9;BA.debugLine="Private LogTable(GFSize) As Int";
_logtable = new int[_gfsize];
;
 //BA.debugLineNum = 10;BA.debugLine="Private PolyZero() As Int = Array As Int(0)";
_polyzero = new int[]{(int) (0)};
 //BA.debugLineNum = 11;BA.debugLine="Private Generator4L() As Int = Array As Int(1, 15";
_generator4l = new int[]{(int) (1),(int) (152),(int) (185),(int) (240),(int) (5),(int) (111),(int) (99),(int) (6),(int) (220),(int) (112),(int) (150),(int) (69),(int) (36),(int) (187),(int) (22),(int) (228),(int) (198),(int) (121),(int) (121),(int) (165),(int) (174)};
 //BA.debugLineNum = 12;BA.debugLine="Private Generator4H() As Int = Array As Int(1, 59";
_generator4h = new int[]{(int) (1),(int) (59),(int) (13),(int) (104),(int) (189),(int) (68),(int) (209),(int) (30),(int) (8),(int) (163),(int) (65),(int) (41),(int) (229),(int) (98),(int) (50),(int) (36),(int) (59)};
 //BA.debugLineNum = 13;BA.debugLine="Private TempBB As BytesBuilder";
_tempbb = new com.bwsi.MeterReader.bytesbuilder();
 //BA.debugLineNum = 14;BA.debugLine="Private Matrix(0, 0) As Boolean";
_matrix = new boolean[(int) (0)][];
{
int d0 = _matrix.length;
int d1 = (int) (0);
for (int i0 = 0;i0 < d0;i0++) {
_matrix[i0] = new boolean[d1];
}
}
;
 //BA.debugLineNum = 15;BA.debugLine="Private Reserved(0, 0) As Boolean";
_reserved = new boolean[(int) (0)][];
{
int d0 = _reserved.length;
int d1 = (int) (0);
for (int i0 = 0;i0 < d0;i0++) {
_reserved[i0] = new boolean[d1];
}
}
;
 //BA.debugLineNum = 16;BA.debugLine="Private NumberOfModules As Int";
_numberofmodules = 0;
 //BA.debugLineNum = 17;BA.debugLine="End Sub";
return "";
}
public anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper  _create(String _text) throws Exception{
byte[] _bytes = null;
boolean _v_4h = false;
byte[] _mode = null;
byte[] _contentcountindicator = null;
com.bwsi.MeterReader.bytesbuilder _encodeddata = null;
byte _b = (byte)0;
int _maxsize = 0;
int _padsize = 0;
byte[] _pad = null;
 //BA.debugLineNum = 31;BA.debugLine="Public Sub Create(Text As String) As B4XBitmap";
 //BA.debugLineNum = 32;BA.debugLine="Dim Bytes() As Byte = Text.GetBytes(\"utf8\") 'non-";
_bytes = _text.getBytes("utf8");
 //BA.debugLineNum = 33;BA.debugLine="If Bytes.Length > 78 Then";
if (_bytes.length>78) { 
 //BA.debugLineNum = 34;BA.debugLine="Log(\"Too long!\")";
__c.LogImpl("151576835","Too long!",0);
 //BA.debugLineNum = 35;BA.debugLine="Return Null";
if (true) return (anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper(), (android.graphics.Bitmap)(__c.Null));
 };
 //BA.debugLineNum = 37;BA.debugLine="Dim V_4H As Boolean = Bytes.Length <= 36";
_v_4h = _bytes.length<=36;
 //BA.debugLineNum = 38;BA.debugLine="If V_4H Then";
if (_v_4h) { 
 //BA.debugLineNum = 39;BA.debugLine="Log(\"Version 4-H\")";
__c.LogImpl("151576840","Version 4-H",0);
 }else {
 //BA.debugLineNum = 41;BA.debugLine="Log(\"Version 4-L\")";
__c.LogImpl("151576842","Version 4-L",0);
 };
 //BA.debugLineNum = 43;BA.debugLine="Dim Matrix(NumberOfModules, NumberOfModules) As B";
_matrix = new boolean[_numberofmodules][];
{
int d0 = _matrix.length;
int d1 = _numberofmodules;
for (int i0 = 0;i0 < d0;i0++) {
_matrix[i0] = new boolean[d1];
}
}
;
 //BA.debugLineNum = 44;BA.debugLine="Dim Reserved(NumberOfModules, NumberOfModules) As";
_reserved = new boolean[_numberofmodules][];
{
int d0 = _reserved.length;
int d1 = _numberofmodules;
for (int i0 = 0;i0 < d0;i0++) {
_reserved[i0] = new boolean[d1];
}
}
;
 //BA.debugLineNum = 46;BA.debugLine="Dim Mode() As Byte = Array As Byte(0, 1, 0, 0) 'b";
_mode = new byte[]{(byte) (0),(byte) (1),(byte) (0),(byte) (0)};
 //BA.debugLineNum = 47;BA.debugLine="Dim ContentCountIndicator() As Byte = UnsignedByt";
_contentcountindicator = _unsignedbytetobits(_bytes.length);
 //BA.debugLineNum = 48;BA.debugLine="Dim EncodedData As BytesBuilder";
_encodeddata = new com.bwsi.MeterReader.bytesbuilder();
 //BA.debugLineNum = 49;BA.debugLine="EncodedData.Initialize";
_encodeddata._initialize /*String*/ (ba);
 //BA.debugLineNum = 50;BA.debugLine="EncodedData.Append(Mode)";
_encodeddata._append /*com.bwsi.MeterReader.bytesbuilder*/ (_mode);
 //BA.debugLineNum = 51;BA.debugLine="EncodedData.Append(ContentCountIndicator)";
_encodeddata._append /*com.bwsi.MeterReader.bytesbuilder*/ (_contentcountindicator);
 //BA.debugLineNum = 52;BA.debugLine="For Each b As Byte In Bytes";
{
final byte[] group20 = _bytes;
final int groupLen20 = group20.length
;int index20 = 0;
;
for (; index20 < groupLen20;index20++){
_b = group20[index20];
 //BA.debugLineNum = 53;BA.debugLine="EncodedData.Append(UnsignedByteToBits(Bit.And(0x";
_encodeddata._append /*com.bwsi.MeterReader.bytesbuilder*/ (_unsignedbytetobits(__c.Bit.And((int) (0xff),(int) (_b))));
 }
};
 //BA.debugLineNum = 56;BA.debugLine="Dim MaxSize As Int";
_maxsize = 0;
 //BA.debugLineNum = 57;BA.debugLine="If V_4H Then MaxSize = 36 * 8 Else MaxSize = 80 *";
if (_v_4h) { 
_maxsize = (int) (36*8);}
else {
_maxsize = (int) (80*8);};
 //BA.debugLineNum = 58;BA.debugLine="Dim PadSize As Int = Min(4, MaxSize - EncodedData";
_padsize = (int) (__c.Min(4,_maxsize-_encodeddata._getlength /*int*/ ()));
 //BA.debugLineNum = 59;BA.debugLine="Dim pad(PadSize) As Byte";
_pad = new byte[_padsize];
;
 //BA.debugLineNum = 60;BA.debugLine="EncodedData.Append(pad)";
_encodeddata._append /*com.bwsi.MeterReader.bytesbuilder*/ (_pad);
 //BA.debugLineNum = 61;BA.debugLine="Do While EncodedData.Length Mod 8 <> 0";
while (_encodeddata._getlength /*int*/ ()%8!=0) {
 //BA.debugLineNum = 62;BA.debugLine="EncodedData.Append(Array As Byte(0))";
_encodeddata._append /*com.bwsi.MeterReader.bytesbuilder*/ (new byte[]{(byte) (0)});
 }
;
 //BA.debugLineNum = 65;BA.debugLine="Do While EncodedData.Length < MaxSize";
while (_encodeddata._getlength /*int*/ ()<_maxsize) {
 //BA.debugLineNum = 66;BA.debugLine="EncodedData.Append(Array As Byte(1,1,1,0,1,1,0,0";
_encodeddata._append /*com.bwsi.MeterReader.bytesbuilder*/ (new byte[]{(byte) (1),(byte) (1),(byte) (1),(byte) (0),(byte) (1),(byte) (1),(byte) (0),(byte) (0)});
 //BA.debugLineNum = 67;BA.debugLine="If EncodedData.Length < MaxSize Then EncodedData";
if (_encodeddata._getlength /*int*/ ()<_maxsize) { 
_encodeddata._append /*com.bwsi.MeterReader.bytesbuilder*/ (new byte[]{(byte) (0),(byte) (0),(byte) (0),(byte) (1),(byte) (0),(byte) (0),(byte) (0),(byte) (1)});};
 }
;
 //BA.debugLineNum = 69;BA.debugLine="If V_4H Then";
if (_v_4h) { 
 //BA.debugLineNum = 70;BA.debugLine="Version4H(EncodedData)";
_version4h(_encodeddata);
 }else {
 //BA.debugLineNum = 72;BA.debugLine="Version4L(EncodedData)";
_version4l(_encodeddata);
 };
 //BA.debugLineNum = 74;BA.debugLine="AddFinders";
_addfinders();
 //BA.debugLineNum = 75;BA.debugLine="AddDataToMatrix(EncodedData.ToArray, V_4H)";
_adddatatomatrix(_encodeddata._toarray /*byte[]*/ (),_v_4h);
 //BA.debugLineNum = 76;BA.debugLine="DrawMatrix";
_drawmatrix();
 //BA.debugLineNum = 77;BA.debugLine="cvs.Invalidate";
_cvs.Invalidate();
 //BA.debugLineNum = 78;BA.debugLine="Return cvs.CreateBitmap";
if (true) return _cvs.CreateBitmap();
 //BA.debugLineNum = 79;BA.debugLine="End Sub";
return null;
}
public int[]  _creategfpoly(int[] _coefficients) throws Exception{
int _firstnonzero = 0;
int[] _res = null;
 //BA.debugLineNum = 317;BA.debugLine="Private Sub CreateGFPoly(Coefficients() As Int) As";
 //BA.debugLineNum = 318;BA.debugLine="If Coefficients.Length > 1 And Coefficients(0) =";
if (_coefficients.length>1 && _coefficients[(int) (0)]==0) { 
 //BA.debugLineNum = 319;BA.debugLine="Dim FirstNonZero As Int = 1";
_firstnonzero = (int) (1);
 //BA.debugLineNum = 320;BA.debugLine="Do While FirstNonZero < Coefficients.Length And";
while (_firstnonzero<_coefficients.length && _coefficients[_firstnonzero]==0) {
 //BA.debugLineNum = 321;BA.debugLine="FirstNonZero = FirstNonZero + 1";
_firstnonzero = (int) (_firstnonzero+1);
 }
;
 //BA.debugLineNum = 323;BA.debugLine="If FirstNonZero = Coefficients.Length Then";
if (_firstnonzero==_coefficients.length) { 
 //BA.debugLineNum = 324;BA.debugLine="Return Array As Int(0)";
if (true) return new int[]{(int) (0)};
 };
 //BA.debugLineNum = 326;BA.debugLine="Dim res(Coefficients.Length - FirstNonZero) As I";
_res = new int[(int) (_coefficients.length-_firstnonzero)];
;
 //BA.debugLineNum = 327;BA.debugLine="IntArrayCopy(Coefficients, FirstNonZero, res, 0,";
_intarraycopy(_coefficients,_firstnonzero,_res,(int) (0),_res.length);
 //BA.debugLineNum = 328;BA.debugLine="Return res";
if (true) return _res;
 };
 //BA.debugLineNum = 330;BA.debugLine="Return Coefficients";
if (true) return _coefficients;
 //BA.debugLineNum = 331;BA.debugLine="End Sub";
return null;
}
public anywheresoftware.b4a.objects.collections.List  _createorder() throws Exception{
anywheresoftware.b4a.objects.collections.List _order = null;
int _x = 0;
int _y = 0;
int _dy = 0;
 //BA.debugLineNum = 176;BA.debugLine="Private Sub CreateOrder As List";
 //BA.debugLineNum = 177;BA.debugLine="Dim Order As List";
_order = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 178;BA.debugLine="Order.Initialize";
_order.Initialize();
 //BA.debugLineNum = 179;BA.debugLine="Dim x As Int = NumberOfModules - 1";
_x = (int) (_numberofmodules-1);
 //BA.debugLineNum = 180;BA.debugLine="Dim y As Int = NumberOfModules - 1";
_y = (int) (_numberofmodules-1);
 //BA.debugLineNum = 181;BA.debugLine="Dim dy As Int = -1";
_dy = (int) (-1);
 //BA.debugLineNum = 182;BA.debugLine="Do While x >= 0 And y >= 0";
while (_x>=0 && _y>=0) {
 //BA.debugLineNum = 183;BA.debugLine="Order.Add(Array As Int(x, y))";
_order.Add((Object)(new int[]{_x,_y}));
 //BA.debugLineNum = 184;BA.debugLine="Order.Add(Array As Int(x - 1, y))";
_order.Add((Object)(new int[]{(int) (_x-1),_y}));
 //BA.debugLineNum = 185;BA.debugLine="y = y + dy";
_y = (int) (_y+_dy);
 //BA.debugLineNum = 186;BA.debugLine="If y = -1 Then";
if (_y==-1) { 
 //BA.debugLineNum = 187;BA.debugLine="x = x - 2";
_x = (int) (_x-2);
 //BA.debugLineNum = 188;BA.debugLine="y = 0";
_y = (int) (0);
 //BA.debugLineNum = 189;BA.debugLine="dy = 1";
_dy = (int) (1);
 }else if(_y==_numberofmodules) { 
 //BA.debugLineNum = 191;BA.debugLine="x = x - 2";
_x = (int) (_x-2);
 //BA.debugLineNum = 192;BA.debugLine="y = NumberOfModules - 1";
_y = (int) (_numberofmodules-1);
 //BA.debugLineNum = 193;BA.debugLine="dy = -1";
_dy = (int) (-1);
 };
 //BA.debugLineNum = 195;BA.debugLine="If x = 6 Then x = x - 1";
if (_x==6) { 
_x = (int) (_x-1);};
 }
;
 //BA.debugLineNum = 197;BA.debugLine="Return Order";
if (true) return _order;
 //BA.debugLineNum = 198;BA.debugLine="End Sub";
return null;
}
public String  _drawmatrix() throws Exception{
anywheresoftware.b4a.objects.B4XCanvas.B4XRect _r = null;
int _y = 0;
int _x = 0;
int _clr = 0;
 //BA.debugLineNum = 200;BA.debugLine="Private Sub DrawMatrix";
 //BA.debugLineNum = 201;BA.debugLine="cvs.DrawRect(cvs.TargetRect, xui.Color_White, Tru";
_cvs.DrawRect(_cvs.getTargetRect(),_xui.Color_White,__c.True,(float) (0));
 //BA.debugLineNum = 202;BA.debugLine="Dim r As B4XRect";
_r = new anywheresoftware.b4a.objects.B4XCanvas.B4XRect();
 //BA.debugLineNum = 203;BA.debugLine="For y = 0 To NumberOfModules - 1";
{
final int step3 = 1;
final int limit3 = (int) (_numberofmodules-1);
_y = (int) (0) ;
for (;_y <= limit3 ;_y = _y + step3 ) {
 //BA.debugLineNum = 204;BA.debugLine="For x = 0 To NumberOfModules - 1";
{
final int step4 = 1;
final int limit4 = (int) (_numberofmodules-1);
_x = (int) (0) ;
for (;_x <= limit4 ;_x = _x + step4 ) {
 //BA.debugLineNum = 205;BA.debugLine="r.Initialize((x + 4) * ModuleSize, (y + 4) * Mo";
_r.Initialize((float) ((_x+4)*_modulesize),(float) ((_y+4)*_modulesize),(float) (0),(float) (0));
 //BA.debugLineNum = 206;BA.debugLine="r.Width = ModuleSize";
_r.setWidth(_modulesize);
 //BA.debugLineNum = 207;BA.debugLine="r.Height = ModuleSize";
_r.setHeight((float) (_modulesize));
 //BA.debugLineNum = 208;BA.debugLine="Dim clr As Int";
_clr = 0;
 //BA.debugLineNum = 209;BA.debugLine="If Matrix(x, y) Then";
if (_matrix[_x][_y]) { 
 //BA.debugLineNum = 210;BA.debugLine="clr = xui.Color_Black";
_clr = _xui.Color_Black;
 //BA.debugLineNum = 212;BA.debugLine="cvs.DrawRect(r, clr, True, 0)";
_cvs.DrawRect(_r,_clr,__c.True,(float) (0));
 };
 }
};
 }
};
 //BA.debugLineNum = 216;BA.debugLine="End Sub";
return "";
}
public int[]  _getnextposition(anywheresoftware.b4a.objects.collections.List _order) throws Exception{
int[] _xy = null;
 //BA.debugLineNum = 167;BA.debugLine="Private Sub GetNextPosition (order As List) As Int";
 //BA.debugLineNum = 168;BA.debugLine="Do While True";
while (__c.True) {
 //BA.debugLineNum = 169;BA.debugLine="Dim xy() As Int = order.Get(0)";
_xy = (int[])(_order.Get((int) (0)));
 //BA.debugLineNum = 170;BA.debugLine="order.RemoveAt(0)";
_order.RemoveAt((int) (0));
 //BA.debugLineNum = 171;BA.debugLine="If Reserved(xy(0), xy(1)) = False Then Return xy";
if (_reserved[_xy[(int) (0)]][_xy[(int) (1)]]==__c.False) { 
if (true) return _xy;};
 }
;
 //BA.debugLineNum = 173;BA.debugLine="Return Null";
if (true) return (int[])(__c.Null);
 //BA.debugLineNum = 174;BA.debugLine="End Sub";
return null;
}
public int[]  _gfbuildmonomial(int _degree,int _coefficient) throws Exception{
int[] _c = null;
 //BA.debugLineNum = 394;BA.debugLine="Private Sub GFBuildMonomial(Degree As Int, Coeffic";
 //BA.debugLineNum = 395;BA.debugLine="If Coefficient = 0 Then Return PolyZero";
if (_coefficient==0) { 
if (true) return _polyzero;};
 //BA.debugLineNum = 396;BA.debugLine="Dim c(Degree + 1) As Int";
_c = new int[(int) (_degree+1)];
;
 //BA.debugLineNum = 397;BA.debugLine="c(0) = Coefficient";
_c[(int) (0)] = _coefficient;
 //BA.debugLineNum = 398;BA.debugLine="Return c";
if (true) return _c;
 //BA.debugLineNum = 399;BA.debugLine="End Sub";
return null;
}
public int  _gfinverse(int _a) throws Exception{
 //BA.debugLineNum = 383;BA.debugLine="Private Sub GFInverse(a As Int) As Int";
 //BA.debugLineNum = 384;BA.debugLine="Return ExpTable(GFSize - LogTable(a) - 1)";
if (true) return _exptable[(int) (_gfsize-_logtable[_a]-1)];
 //BA.debugLineNum = 385;BA.debugLine="End Sub";
return 0;
}
public int  _gfmultiply(int _a,int _b) throws Exception{
 //BA.debugLineNum = 387;BA.debugLine="Private Sub GFMultiply(a As Int, b As Int) As Int";
 //BA.debugLineNum = 388;BA.debugLine="If a = 0 Or b = 0 Then";
if (_a==0 || _b==0) { 
 //BA.debugLineNum = 389;BA.debugLine="Return 0";
if (true) return (int) (0);
 };
 //BA.debugLineNum = 391;BA.debugLine="Return ExpTable((LogTable(a) + LogTable(b)) Mod (";
if (true) return _exptable[(int) ((_logtable[_a]+_logtable[_b])%(_gfsize-1))];
 //BA.debugLineNum = 392;BA.debugLine="End Sub";
return 0;
}
public String  _initialize(anywheresoftware.b4a.BA _ba,int _bitmapsize) throws Exception{
innerInitialize(_ba);
anywheresoftware.b4a.objects.B4XViewWrapper _p = null;
 //BA.debugLineNum = 19;BA.debugLine="Public Sub Initialize (BitmapSize As Int)";
 //BA.debugLineNum = 20;BA.debugLine="TempBB.Initialize";
_tempbb._initialize /*String*/ (ba);
 //BA.debugLineNum = 21;BA.debugLine="NumberOfModules = 17 + QRVersion * 4";
_numberofmodules = (int) (17+_qrversion*4);
 //BA.debugLineNum = 22;BA.debugLine="ModuleSize = BitmapSize / (NumberOfModules + 8)";
_modulesize = (int) (_bitmapsize/(double)(_numberofmodules+8));
 //BA.debugLineNum = 23;BA.debugLine="PrepareTables";
_preparetables();
 //BA.debugLineNum = 24;BA.debugLine="BitmapSize = ModuleSize * (NumberOfModules + 8)";
_bitmapsize = (int) (_modulesize*(_numberofmodules+8));
 //BA.debugLineNum = 26;BA.debugLine="Dim p As B4XView = xui.CreatePanel(\"\")";
_p = new anywheresoftware.b4a.objects.B4XViewWrapper();
_p = _xui.CreatePanel(ba,"");
 //BA.debugLineNum = 27;BA.debugLine="p.SetLayoutAnimated(0, 0, 0, BitmapSize, BitmapSi";
_p.SetLayoutAnimated((int) (0),(int) (0),(int) (0),_bitmapsize,_bitmapsize);
 //BA.debugLineNum = 28;BA.debugLine="cvs.Initialize(p)";
_cvs.Initialize(_p);
 //BA.debugLineNum = 29;BA.debugLine="End Sub";
return "";
}
public String  _intarraycopy(int[] _src,int _srcoffset,int[] _dest,int _destoffset,int _count) throws Exception{
int _i = 0;
 //BA.debugLineNum = 352;BA.debugLine="Private Sub IntArrayCopy(Src() As Int, SrcOffset A";
 //BA.debugLineNum = 353;BA.debugLine="For i = 0 To Count - 1";
{
final int step1 = 1;
final int limit1 = (int) (_count-1);
_i = (int) (0) ;
for (;_i <= limit1 ;_i = _i + step1 ) {
 //BA.debugLineNum = 354;BA.debugLine="Dest(DestOffset + i) = Src(SrcOffset + i)";
_dest[(int) (_destoffset+_i)] = _src[(int) (_srcoffset+_i)];
 }
};
 //BA.debugLineNum = 356;BA.debugLine="End Sub";
return "";
}
public String  _logarray(anywheresoftware.b4a.objects.collections.List _arr) throws Exception{
 //BA.debugLineNum = 423;BA.debugLine="Private Sub LogArray(arr As List) 'ignore";
 //BA.debugLineNum = 424;BA.debugLine="Log(arr)";
__c.LogImpl("153018625",BA.ObjectToString(_arr),0);
 //BA.debugLineNum = 425;BA.debugLine="End Sub";
return "";
}
public int[]  _polyaddorsubtract(int[] _this,int[] _other) throws Exception{
int[] _small = null;
int[] _large = null;
int[] _temp = null;
int[] _sumdiff = null;
int _lengthdiff = 0;
int _i = 0;
 //BA.debugLineNum = 333;BA.debugLine="Private Sub PolyAddOrSubtract(This() As Int, Other";
 //BA.debugLineNum = 334;BA.debugLine="If This(0) = 0 Then Return Other";
if (_this[(int) (0)]==0) { 
if (true) return _other;};
 //BA.debugLineNum = 335;BA.debugLine="If Other(0) = 0 Then Return This";
if (_other[(int) (0)]==0) { 
if (true) return _this;};
 //BA.debugLineNum = 336;BA.debugLine="Dim Small() As Int = This";
_small = _this;
 //BA.debugLineNum = 337;BA.debugLine="Dim Large() As Int = Other";
_large = _other;
 //BA.debugLineNum = 338;BA.debugLine="If Small.Length > Large.Length Then";
if (_small.length>_large.length) { 
 //BA.debugLineNum = 339;BA.debugLine="Dim temp() As Int = Small";
_temp = _small;
 //BA.debugLineNum = 340;BA.debugLine="Small = Large";
_small = _large;
 //BA.debugLineNum = 341;BA.debugLine="Large = temp";
_large = _temp;
 };
 //BA.debugLineNum = 343;BA.debugLine="Dim SumDiff(Large.Length) As Int";
_sumdiff = new int[_large.length];
;
 //BA.debugLineNum = 344;BA.debugLine="Dim LengthDiff As Int = Large.Length - Small.Leng";
_lengthdiff = (int) (_large.length-_small.length);
 //BA.debugLineNum = 345;BA.debugLine="IntArrayCopy(Large, 0, SumDiff, 0, LengthDiff)";
_intarraycopy(_large,(int) (0),_sumdiff,(int) (0),_lengthdiff);
 //BA.debugLineNum = 346;BA.debugLine="For i = LengthDiff To Large.Length - 1";
{
final int step13 = 1;
final int limit13 = (int) (_large.length-1);
_i = _lengthdiff ;
for (;_i <= limit13 ;_i = _i + step13 ) {
 //BA.debugLineNum = 347;BA.debugLine="SumDiff(i) = Bit.Xor(Small(i - LengthDiff), Larg";
_sumdiff[_i] = __c.Bit.Xor(_small[(int) (_i-_lengthdiff)],_large[_i]);
 }
};
 //BA.debugLineNum = 349;BA.debugLine="Return CreateGFPoly(SumDiff)";
if (true) return _creategfpoly(_sumdiff);
 //BA.debugLineNum = 350;BA.debugLine="End Sub";
return null;
}
public int[]  _polydivide(int[] _this,int[] _other) throws Exception{
int[] _quotient = null;
int[] _remainder = null;
int _denominatorleadingterm = 0;
int _inversedenominatorleadingterm = 0;
int _degreedifference = 0;
int _dscale = 0;
int[] _term = null;
int[] _iterationquotient = null;
 //BA.debugLineNum = 367;BA.debugLine="Private Sub PolyDivide (This() As Int, Other() As";
 //BA.debugLineNum = 368;BA.debugLine="Dim quotient() As Int = PolyZero";
_quotient = _polyzero;
 //BA.debugLineNum = 369;BA.debugLine="Dim remainder() As Int = This";
_remainder = _this;
 //BA.debugLineNum = 370;BA.debugLine="Dim denominatorLeadingTerm As Int = Other(0)";
_denominatorleadingterm = _other[(int) (0)];
 //BA.debugLineNum = 371;BA.debugLine="Dim inverseDenominatorLeadingTerm As Int = GFInve";
_inversedenominatorleadingterm = _gfinverse(_denominatorleadingterm);
 //BA.debugLineNum = 372;BA.debugLine="Do While remainder.Length >= Other.Length And rem";
while (_remainder.length>=_other.length && _remainder[(int) (0)]!=0) {
 //BA.debugLineNum = 373;BA.debugLine="Dim DegreeDifference As Int = remainder.Length -";
_degreedifference = (int) (_remainder.length-_other.length);
 //BA.debugLineNum = 374;BA.debugLine="Dim dscale As Int = GFMultiply(remainder(0), inv";
_dscale = _gfmultiply(_remainder[(int) (0)],_inversedenominatorleadingterm);
 //BA.debugLineNum = 375;BA.debugLine="Dim term() As Int = PolyMultiplyByMonomial(Other";
_term = _polymultiplybymonomial(_other,_degreedifference,_dscale);
 //BA.debugLineNum = 376;BA.debugLine="Dim iterationQuotient() As Int = GFBuildMonomial";
_iterationquotient = _gfbuildmonomial(_degreedifference,_dscale);
 //BA.debugLineNum = 377;BA.debugLine="quotient = PolyAddOrSubtract(quotient, iteration";
_quotient = _polyaddorsubtract(_quotient,_iterationquotient);
 //BA.debugLineNum = 378;BA.debugLine="remainder = PolyAddOrSubtract(remainder, term)";
_remainder = _polyaddorsubtract(_remainder,_term);
 }
;
 //BA.debugLineNum = 380;BA.debugLine="Return remainder";
if (true) return _remainder;
 //BA.debugLineNum = 381;BA.debugLine="End Sub";
return null;
}
public int[]  _polymultiplybymonomial(int[] _this,int _degree,int _coefficient) throws Exception{
int[] _product = null;
int _i = 0;
 //BA.debugLineNum = 358;BA.debugLine="Private Sub PolyMultiplyByMonomial (This() As Int,";
 //BA.debugLineNum = 359;BA.debugLine="If Coefficient = 0 Then Return PolyZero";
if (_coefficient==0) { 
if (true) return _polyzero;};
 //BA.debugLineNum = 360;BA.debugLine="Dim product(This.Length + Degree) As Int";
_product = new int[(int) (_this.length+_degree)];
;
 //BA.debugLineNum = 361;BA.debugLine="For i = 0 To This.Length - 1";
{
final int step3 = 1;
final int limit3 = (int) (_this.length-1);
_i = (int) (0) ;
for (;_i <= limit3 ;_i = _i + step3 ) {
 //BA.debugLineNum = 362;BA.debugLine="product(i) = GFMultiply(This(i), Coefficient)";
_product[_i] = _gfmultiply(_this[_i],_coefficient);
 }
};
 //BA.debugLineNum = 364;BA.debugLine="Return CreateGFPoly(product)";
if (true) return _creategfpoly(_product);
 //BA.debugLineNum = 365;BA.debugLine="End Sub";
return null;
}
public String  _preparetables() throws Exception{
int _x = 0;
int _primitive = 0;
int _i = 0;
 //BA.debugLineNum = 301;BA.debugLine="Private Sub PrepareTables";
 //BA.debugLineNum = 302;BA.debugLine="Dim x = 1 As Int";
_x = (int) (1);
 //BA.debugLineNum = 303;BA.debugLine="Dim Primitive As Int = 285";
_primitive = (int) (285);
 //BA.debugLineNum = 304;BA.debugLine="For i = 0 To GFSize - 1";
{
final int step3 = 1;
final int limit3 = (int) (_gfsize-1);
_i = (int) (0) ;
for (;_i <= limit3 ;_i = _i + step3 ) {
 //BA.debugLineNum = 305;BA.debugLine="ExpTable(i) = x";
_exptable[_i] = _x;
 //BA.debugLineNum = 306;BA.debugLine="x = x * 2";
_x = (int) (_x*2);
 //BA.debugLineNum = 307;BA.debugLine="If x >= GFSize Then";
if (_x>=_gfsize) { 
 //BA.debugLineNum = 308;BA.debugLine="x = Bit.Xor(Primitive, x)";
_x = __c.Bit.Xor(_primitive,_x);
 //BA.debugLineNum = 309;BA.debugLine="x = Bit.And(GFSize - 1, x)";
_x = __c.Bit.And((int) (_gfsize-1),_x);
 };
 }
};
 //BA.debugLineNum = 312;BA.debugLine="For i = 0 To GFSize - 2";
{
final int step11 = 1;
final int limit11 = (int) (_gfsize-2);
_i = (int) (0) ;
for (;_i <= limit11 ;_i = _i + step11 ) {
 //BA.debugLineNum = 313;BA.debugLine="LogTable(ExpTable(i)) = i";
_logtable[_exptable[_i]] = _i;
 }
};
 //BA.debugLineNum = 315;BA.debugLine="End Sub";
return "";
}
public byte[]  _unsignedbytetobits(int _value) throws Exception{
int _i = 0;
int _x = 0;
int _ii = 0;
 //BA.debugLineNum = 227;BA.debugLine="Private Sub UnsignedByteToBits (Value As Int) As B";
 //BA.debugLineNum = 228;BA.debugLine="TempBB.Clear";
_tempbb._clear /*String*/ ();
 //BA.debugLineNum = 229;BA.debugLine="For i = 7 To 0 Step - 1";
{
final int step2 = -1;
final int limit2 = (int) (0);
_i = (int) (7) ;
for (;_i >= limit2 ;_i = _i + step2 ) {
 //BA.debugLineNum = 230;BA.debugLine="Dim x As Int = Bit.ShiftLeft(1, i)";
_x = __c.Bit.ShiftLeft((int) (1),_i);
 //BA.debugLineNum = 231;BA.debugLine="Dim ii As Int = Bit.And(Value, x)";
_ii = __c.Bit.And(_value,_x);
 //BA.debugLineNum = 232;BA.debugLine="If ii <> 0 Then";
if (_ii!=0) { 
 //BA.debugLineNum = 233;BA.debugLine="TempBB.Append(Array As Byte(1))";
_tempbb._append /*com.bwsi.MeterReader.bytesbuilder*/ (new byte[]{(byte) (1)});
 }else {
 //BA.debugLineNum = 235;BA.debugLine="TempBB.Append(Array As Byte(0))";
_tempbb._append /*com.bwsi.MeterReader.bytesbuilder*/ (new byte[]{(byte) (0)});
 };
 }
};
 //BA.debugLineNum = 239;BA.debugLine="Return TempBB.ToArray";
if (true) return _tempbb._toarray /*byte[]*/ ();
 //BA.debugLineNum = 240;BA.debugLine="End Sub";
return null;
}
public String  _version4h(com.bwsi.MeterReader.bytesbuilder _encodeddata) throws Exception{
anywheresoftware.b4a.objects.collections.List _ecs = null;
anywheresoftware.b4a.objects.collections.List _datablocks = null;
int _block1 = 0;
byte[] _data = null;
int[] _dataasints = null;
int _i = 0;
int[] _ec = null;
int[] _ec2 = null;
com.bwsi.MeterReader.bytesbuilder _interleaved = null;
int[] _ii = null;
byte[] _pad = null;
 //BA.debugLineNum = 81;BA.debugLine="Private Sub Version4H (EncodedData As BytesBuilder";
 //BA.debugLineNum = 82;BA.debugLine="Dim ecs As List";
_ecs = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 83;BA.debugLine="ecs.Initialize";
_ecs.Initialize();
 //BA.debugLineNum = 84;BA.debugLine="Dim dataBlocks As List";
_datablocks = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 85;BA.debugLine="dataBlocks.Initialize";
_datablocks.Initialize();
 //BA.debugLineNum = 86;BA.debugLine="For block1 = 0 To 3";
{
final int step5 = 1;
final int limit5 = (int) (3);
_block1 = (int) (0) ;
for (;_block1 <= limit5 ;_block1 = _block1 + step5 ) {
 //BA.debugLineNum = 87;BA.debugLine="Dim Data() As Byte = EncodedData.SubArray2(block";
_data = _encodeddata._subarray2 /*byte[]*/ ((int) (_block1*9*8),(int) ((_block1+1)*9*8));
 //BA.debugLineNum = 88;BA.debugLine="Dim DataAsInts(Data.Length / 8) As Int";
_dataasints = new int[(int) (_data.length/(double)8)];
;
 //BA.debugLineNum = 89;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 90;BA.debugLine="For i = 0 To Data.Length - 1 Step 8";
{
final int step9 = 8;
final int limit9 = (int) (_data.length-1);
_i = (int) (0) ;
for (;_i <= limit9 ;_i = _i + step9 ) {
 //BA.debugLineNum = 91;BA.debugLine="DataAsInts(i / 8) = BitsToUnsignedByte(Data, i)";
_dataasints[(int) (_i/(double)8)] = _bitstounsignedbyte(_data,_i);
 }
};
 //BA.debugLineNum = 93;BA.debugLine="dataBlocks.Add(DataAsInts)";
_datablocks.Add((Object)(_dataasints));
 //BA.debugLineNum = 94;BA.debugLine="Dim ec() As Int = CalcReedSolomon(DataAsInts, Ge";
_ec = _calcreedsolomon(_dataasints,_generator4h);
 //BA.debugLineNum = 95;BA.debugLine="If ec.Length < Generator4H.Length - 1 Then";
if (_ec.length<_generator4h.length-1) { 
 //BA.debugLineNum = 96;BA.debugLine="Dim ec2(Generator4H.Length - 1) As Int";
_ec2 = new int[(int) (_generator4h.length-1)];
;
 //BA.debugLineNum = 97;BA.debugLine="IntArrayCopy(ec, 0, ec2,  Generator4H.Length -";
_intarraycopy(_ec,(int) (0),_ec2,(int) (_generator4h.length-1-_ec.length),_ec.length);
 //BA.debugLineNum = 98;BA.debugLine="ec = ec2";
_ec = _ec2;
 };
 //BA.debugLineNum = 100;BA.debugLine="ecs.Add(ec)";
_ecs.Add((Object)(_ec));
 }
};
 //BA.debugLineNum = 102;BA.debugLine="Dim Interleaved As BytesBuilder";
_interleaved = new com.bwsi.MeterReader.bytesbuilder();
 //BA.debugLineNum = 103;BA.debugLine="Interleaved.Initialize";
_interleaved._initialize /*String*/ (ba);
 //BA.debugLineNum = 104;BA.debugLine="For i = 0 To 8";
{
final int step23 = 1;
final int limit23 = (int) (8);
_i = (int) (0) ;
for (;_i <= limit23 ;_i = _i + step23 ) {
 //BA.debugLineNum = 105;BA.debugLine="For block1 = 0 To 3";
{
final int step24 = 1;
final int limit24 = (int) (3);
_block1 = (int) (0) ;
for (;_block1 <= limit24 ;_block1 = _block1 + step24 ) {
 //BA.debugLineNum = 106;BA.debugLine="Dim ii() As Int = dataBlocks.Get(block1)";
_ii = (int[])(_datablocks.Get(_block1));
 //BA.debugLineNum = 107;BA.debugLine="Interleaved.Append(UnsignedByteToBits(ii(i)))";
_interleaved._append /*com.bwsi.MeterReader.bytesbuilder*/ (_unsignedbytetobits(_ii[_i]));
 }
};
 }
};
 //BA.debugLineNum = 110;BA.debugLine="For i = 0 To 15";
{
final int step29 = 1;
final int limit29 = (int) (15);
_i = (int) (0) ;
for (;_i <= limit29 ;_i = _i + step29 ) {
 //BA.debugLineNum = 111;BA.debugLine="For block1 = 0 To 3";
{
final int step30 = 1;
final int limit30 = (int) (3);
_block1 = (int) (0) ;
for (;_block1 <= limit30 ;_block1 = _block1 + step30 ) {
 //BA.debugLineNum = 112;BA.debugLine="Dim ii() As Int = ecs.Get(block1)";
_ii = (int[])(_ecs.Get(_block1));
 //BA.debugLineNum = 113;BA.debugLine="Interleaved.Append(UnsignedByteToBits(ii(i)))";
_interleaved._append /*com.bwsi.MeterReader.bytesbuilder*/ (_unsignedbytetobits(_ii[_i]));
 }
};
 }
};
 //BA.debugLineNum = 116;BA.debugLine="Dim Pad(7) As Byte";
_pad = new byte[(int) (7)];
;
 //BA.debugLineNum = 117;BA.debugLine="Interleaved.Append(Pad)";
_interleaved._append /*com.bwsi.MeterReader.bytesbuilder*/ (_pad);
 //BA.debugLineNum = 118;BA.debugLine="EncodedData.Clear";
_encodeddata._clear /*String*/ ();
 //BA.debugLineNum = 119;BA.debugLine="EncodedData.Append(Interleaved.ToArray)";
_encodeddata._append /*com.bwsi.MeterReader.bytesbuilder*/ (_interleaved._toarray /*byte[]*/ ());
 //BA.debugLineNum = 120;BA.debugLine="End Sub";
return "";
}
public String  _version4l(com.bwsi.MeterReader.bytesbuilder _encodeddata) throws Exception{
byte[] _data = null;
int[] _dataasints = null;
int _i = 0;
int[] _ec = null;
byte[] _pad = null;
 //BA.debugLineNum = 122;BA.debugLine="Private Sub Version4L (EncodedData As BytesBuilder";
 //BA.debugLineNum = 123;BA.debugLine="Dim Data() As Byte = EncodedData.ToArray";
_data = _encodeddata._toarray /*byte[]*/ ();
 //BA.debugLineNum = 124;BA.debugLine="Dim DataAsInts(Data.Length / 8) As Int";
_dataasints = new int[(int) (_data.length/(double)8)];
;
 //BA.debugLineNum = 125;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 126;BA.debugLine="For i = 0 To Data.Length - 1 Step 8";
{
final int step4 = 8;
final int limit4 = (int) (_data.length-1);
_i = (int) (0) ;
for (;_i <= limit4 ;_i = _i + step4 ) {
 //BA.debugLineNum = 127;BA.debugLine="DataAsInts(i / 8) = BitsToUnsignedByte(Data, i)";
_dataasints[(int) (_i/(double)8)] = _bitstounsignedbyte(_data,_i);
 }
};
 //BA.debugLineNum = 129;BA.debugLine="Dim ec() As Int = CalcReedSolomon(DataAsInts, Gen";
_ec = _calcreedsolomon(_dataasints,_generator4l);
 //BA.debugLineNum = 130;BA.debugLine="Dim pad(8 * (Generator4L.Length - 1 - ec.Length))";
_pad = new byte[(int) (8*(_generator4l.length-1-_ec.length))];
;
 //BA.debugLineNum = 131;BA.debugLine="EncodedData.Append(pad)";
_encodeddata._append /*com.bwsi.MeterReader.bytesbuilder*/ (_pad);
 //BA.debugLineNum = 132;BA.debugLine="For Each i As Int In ec";
{
final int[] group10 = _ec;
final int groupLen10 = group10.length
;int index10 = 0;
;
for (; index10 < groupLen10;index10++){
_i = group10[index10];
 //BA.debugLineNum = 133;BA.debugLine="EncodedData.Append(UnsignedByteToBits(i))";
_encodeddata._append /*com.bwsi.MeterReader.bytesbuilder*/ (_unsignedbytetobits(_i));
 }
};
 //BA.debugLineNum = 135;BA.debugLine="End Sub";
return "";
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
return BA.SubDelegator.SubNotFound;
}
}
