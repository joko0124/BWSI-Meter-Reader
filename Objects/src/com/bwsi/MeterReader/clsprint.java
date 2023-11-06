package com.bwsi.MeterReader;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class clsprint extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "com.bwsi.MeterReader.clsprint");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            
        }
        if (BA.isShellModeRuntimeCheck(ba)) 
			   this.getClass().getMethod("_class_globals", com.bwsi.MeterReader.clsprint.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
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
public com.bwsi.MeterReader.validationrptgenerator _validationrptgenerator = null;
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 1;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 2;BA.debugLine="End Sub";
return "";
}
public byte[]  _getbytesforbitmap(String _bitmapname) throws Exception{
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _mybitmap = null;
anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _out = null;
int _offset = 0;
int _wl = 0;
int _wh = 0;
int _x = 0;
int _k = 0;
byte _slice = (byte)0;
int _b = 0;
int _y = 0;
int _pixel = 0;
 //BA.debugLineNum = 11;BA.debugLine="Sub getBytesForBitmap( bitmapName As String) As By";
 //BA.debugLineNum = 13;BA.debugLine="Dim myBitmap As Bitmap";
_mybitmap = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 14;BA.debugLine="myBitmap = LoadBitmap(File.DirAssets, bitmapName)";
_mybitmap = __c.LoadBitmap(__c.File.getDirAssets(),_bitmapname);
 //BA.debugLineNum = 16;BA.debugLine="Log(\"rastering bitmap: width=\"&myBitmap.Width&\" h";
__c.LogImpl("738666245","rastering bitmap: width="+BA.NumberToString(_mybitmap.getWidth())+" height="+BA.NumberToString(_mybitmap.getHeight()),0);
 //BA.debugLineNum = 18;BA.debugLine="Dim out As OutputStream";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 19;BA.debugLine="out.InitializeToBytesArray(0)						' define start";
_out.InitializeToBytesArray((int) (0));
 //BA.debugLineNum = 21;BA.debugLine="out.WriteBytes(Array As Byte(0x1B,0x40),0,2)";
_out.WriteBytes(new byte[]{(byte) (0x1b),(byte) (0x40)},(int) (0),(int) (2));
 //BA.debugLineNum = 23;BA.debugLine="out.WriteBytes(Array As Byte(0x1B,0x33,33),0,3)";
_out.WriteBytes(new byte[]{(byte) (0x1b),(byte) (0x33),(byte) (33)},(int) (0),(int) (3));
 //BA.debugLineNum = 29;BA.debugLine="Dim offset As Int=0";
_offset = (int) (0);
 //BA.debugLineNum = 31;BA.debugLine="Do While offset<myBitmap.Height";
while (_offset<_mybitmap.getHeight()) {
 //BA.debugLineNum = 33;BA.debugLine="out.WriteBytes(Array As Byte(0x1B,0x2A,33),0,3)";
_out.WriteBytes(new byte[]{(byte) (0x1b),(byte) (0x2a),(byte) (33)},(int) (0),(int) (3));
 //BA.debugLineNum = 34;BA.debugLine="Dim wl As Int = Bit.And(myBitmap.Width,0xFF)";
_wl = __c.Bit.And(_mybitmap.getWidth(),(int) (0xff));
 //BA.debugLineNum = 35;BA.debugLine="Dim wh As Int = Bit.ShiftRight(myBitmap.Width,8)";
_wh = __c.Bit.ShiftRight(_mybitmap.getWidth(),(int) (8));
 //BA.debugLineNum = 36;BA.debugLine="out.WriteBytes(Array As Byte(wl,wh),0,2)	' Width";
_out.WriteBytes(new byte[]{(byte) (_wl),(byte) (_wh)},(int) (0),(int) (2));
 //BA.debugLineNum = 38;BA.debugLine="For x=0 To myBitmap.Width-1";
{
final int step14 = 1;
final int limit14 = (int) (_mybitmap.getWidth()-1);
_x = (int) (0) ;
for (;_x <= limit14 ;_x = _x + step14 ) {
 //BA.debugLineNum = 40;BA.debugLine="For k=0 To 2";
{
final int step15 = 1;
final int limit15 = (int) (2);
_k = (int) (0) ;
for (;_k <= limit15 ;_k = _k + step15 ) {
 //BA.debugLineNum = 42;BA.debugLine="Dim slice As Byte=0";
_slice = (byte) (0);
 //BA.debugLineNum = 43;BA.debugLine="For b=0 To 7";
{
final int step17 = 1;
final int limit17 = (int) (7);
_b = (int) (0) ;
for (;_b <= limit17 ;_b = _b + step17 ) {
 //BA.debugLineNum = 44;BA.debugLine="Dim y As Int = offset+8*k+b";
_y = (int) (_offset+8*_k+_b);
 //BA.debugLineNum = 45;BA.debugLine="If y<myBitmap.Height Then";
if (_y<_mybitmap.getHeight()) { 
 //BA.debugLineNum = 46;BA.debugLine="Dim pixel As Int = myBitmap.GetPixel(x,y)";
_pixel = _mybitmap.GetPixel(_x,_y);
 //BA.debugLineNum = 47;BA.debugLine="If Bit.And(pixel,0xFFFFFF)<>0xFFFFFF Then";
if (__c.Bit.And(_pixel,(int) (0xffffff))!=0xffffff) { 
 //BA.debugLineNum = 48;BA.debugLine="slice = Bit.Or(slice, Bit.ShiftRight(0x80,b";
_slice = (byte) (__c.Bit.Or((int) (_slice),__c.Bit.ShiftRight((int) (0x80),_b)));
 };
 };
 }
};
 //BA.debugLineNum = 53;BA.debugLine="out.WriteBytes(Array As Byte(slice),0,1)";
_out.WriteBytes(new byte[]{_slice},(int) (0),(int) (1));
 }
};
 }
};
 //BA.debugLineNum = 57;BA.debugLine="offset=offset+24";
_offset = (int) (_offset+24);
 //BA.debugLineNum = 58;BA.debugLine="out.WriteBytes(Array As Byte(0x0A),0,1)			' \\n a";
_out.WriteBytes(new byte[]{(byte) (0x0a)},(int) (0),(int) (1));
 }
;
 //BA.debugLineNum = 61;BA.debugLine="out.WriteBytes(Array As Byte(0x1B,0x33,33),0,3)";
_out.WriteBytes(new byte[]{(byte) (0x1b),(byte) (0x33),(byte) (33)},(int) (0),(int) (3));
 //BA.debugLineNum = 63;BA.debugLine="Return out.ToBytesArray()";
if (true) return _out.ToBytesArray();
 //BA.debugLineNum = 65;BA.debugLine="End Sub";
return null;
}
public byte[]  _getbytesforbitmap2(String _bitmapname,boolean _centered) throws Exception{
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _mybitmap = null;
int _x = 0;
anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _out = null;
int _mbytes = 0;
int _wl = 0;
int _wh = 0;
int _hl = 0;
int _hh = 0;
int _y = 0;
byte _slice = (byte)0;
int _b = 0;
 //BA.debugLineNum = 68;BA.debugLine="Sub getBytesForBitmap2( bitmapName As String , cen";
 //BA.debugLineNum = 71;BA.debugLine="Dim myBitmap As Bitmap, x As Int";
_mybitmap = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
_x = 0;
 //BA.debugLineNum = 72;BA.debugLine="myBitmap = LoadBitmap(File.DirAssets, bitmapName)";
_mybitmap = __c.LoadBitmap(__c.File.getDirAssets(),_bitmapname);
 //BA.debugLineNum = 74;BA.debugLine="Log(\"rastering2 bitmap: width=\"&myBitmap.Width&\"";
__c.LogImpl("738731782","rastering2 bitmap: width="+BA.NumberToString(_mybitmap.getWidth())+" height="+BA.NumberToString(_mybitmap.getHeight()),0);
 //BA.debugLineNum = 76;BA.debugLine="Dim out As OutputStream";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 77;BA.debugLine="out.InitializeToBytesArray(0)						' define start";
_out.InitializeToBytesArray((int) (0));
 //BA.debugLineNum = 79;BA.debugLine="out.WriteBytes(Array As Byte(0x1B,0x40),0,2)";
_out.WriteBytes(new byte[]{(byte) (0x1b),(byte) (0x40)},(int) (0),(int) (2));
 //BA.debugLineNum = 81;BA.debugLine="If centered Then";
if (_centered) { 
 //BA.debugLineNum = 82;BA.debugLine="out.WriteBytes(Array As Byte(0x1B,0x61,1),0,3)";
_out.WriteBytes(new byte[]{(byte) (0x1b),(byte) (0x61),(byte) (1)},(int) (0),(int) (3));
 };
 //BA.debugLineNum = 86;BA.debugLine="out.WriteBytes(Array As Byte(0x1D,0x76,0x30,0x30)";
_out.WriteBytes(new byte[]{(byte) (0x1d),(byte) (0x76),(byte) (0x30),(byte) (0x30)},(int) (0),(int) (4));
 //BA.debugLineNum = 88;BA.debugLine="Dim mbytes As Int = (myBitmap.Width+7)/8";
_mbytes = (int) ((_mybitmap.getWidth()+7)/(double)8);
 //BA.debugLineNum = 89;BA.debugLine="Dim wl As Int = Bit.And(mbytes,0xFF)";
_wl = __c.Bit.And(_mbytes,(int) (0xff));
 //BA.debugLineNum = 90;BA.debugLine="Dim wh As Int = Bit.ShiftRight(mbytes,8)";
_wh = __c.Bit.ShiftRight(_mbytes,(int) (8));
 //BA.debugLineNum = 91;BA.debugLine="Dim hl As Int = Bit.And(myBitmap.height,0xFF)";
_hl = __c.Bit.And(_mybitmap.getHeight(),(int) (0xff));
 //BA.debugLineNum = 92;BA.debugLine="Dim hh As Int = Bit.ShiftRight(myBitmap.height,8)";
_hh = __c.Bit.ShiftRight(_mybitmap.getHeight(),(int) (8));
 //BA.debugLineNum = 94;BA.debugLine="out.WriteBytes(Array As Byte(wl,wh,hl,hh),0,4)	'";
_out.WriteBytes(new byte[]{(byte) (_wl),(byte) (_wh),(byte) (_hl),(byte) (_hh)},(int) (0),(int) (4));
 //BA.debugLineNum = 96;BA.debugLine="For y=0 To myBitmap.Height-1";
{
final int step17 = 1;
final int limit17 = (int) (_mybitmap.getHeight()-1);
_y = (int) (0) ;
for (;_y <= limit17 ;_y = _y + step17 ) {
 //BA.debugLineNum = 98;BA.debugLine="For x=0 To myBitmap.Width-1 Step 8";
{
final int step18 = 8;
final int limit18 = (int) (_mybitmap.getWidth()-1);
_x = (int) (0) ;
for (;_x <= limit18 ;_x = _x + step18 ) {
 //BA.debugLineNum = 99;BA.debugLine="Dim slice As Byte=0";
_slice = (byte) (0);
 //BA.debugLineNum = 100;BA.debugLine="For b=0 To 7";
{
final int step20 = 1;
final int limit20 = (int) (7);
_b = (int) (0) ;
for (;_b <= limit20 ;_b = _b + step20 ) {
 //BA.debugLineNum = 101;BA.debugLine="If x+b < myBitmap.Width Then";
if (_x+_b<_mybitmap.getWidth()) { 
 //BA.debugLineNum = 102;BA.debugLine="If Bit.And( myBitmap.GetPixel(x+b,y),0xFFFFFF";
if (__c.Bit.And(_mybitmap.GetPixel((int) (_x+_b),_y),(int) (0xffffff))!=0xffffff) { 
 //BA.debugLineNum = 103;BA.debugLine="slice=Bit.Or(slice,Bit.ShiftRight(0x80,b))";
_slice = (byte) (__c.Bit.Or((int) (_slice),__c.Bit.ShiftRight((int) (0x80),_b)));
 };
 };
 }
};
 //BA.debugLineNum = 107;BA.debugLine="out.WriteBytes(Array As Byte(slice),0,1)";
_out.WriteBytes(new byte[]{_slice},(int) (0),(int) (1));
 }
};
 }
};
 //BA.debugLineNum = 111;BA.debugLine="Return out.ToBytesArray()";
if (true) return _out.ToBytesArray();
 //BA.debugLineNum = 113;BA.debugLine="End Sub";
return null;
}
public byte[]  _getbytesforbitmap3(String _bitmapname,int _dotsattheleft) throws Exception{
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _mybitmap = null;
int _x = 0;
anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _out = null;
int _mbytes = 0;
int _wl = 0;
int _wh = 0;
int _hl = 0;
int _hh = 0;
int _y = 0;
int _k = 0;
byte _slice = (byte)0;
int _b = 0;
 //BA.debugLineNum = 119;BA.debugLine="Sub getBytesForBitmap3( bitmapName As String , dot";
 //BA.debugLineNum = 122;BA.debugLine="Dim myBitmap As Bitmap, x As Int";
_mybitmap = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
_x = 0;
 //BA.debugLineNum = 123;BA.debugLine="myBitmap = LoadBitmap(File.DirAssets, bitmapName)";
_mybitmap = __c.LoadBitmap(__c.File.getDirAssets(),_bitmapname);
 //BA.debugLineNum = 125;BA.debugLine="Log(\"rastering2 bitmap: width=\"&myBitmap.Width&\"";
__c.LogImpl("738797318","rastering2 bitmap: width="+BA.NumberToString(_mybitmap.getWidth())+" height="+BA.NumberToString(_mybitmap.getHeight()),0);
 //BA.debugLineNum = 127;BA.debugLine="Dim out As OutputStream";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 128;BA.debugLine="out.InitializeToBytesArray(0)						' define start";
_out.InitializeToBytesArray((int) (0));
 //BA.debugLineNum = 130;BA.debugLine="out.WriteBytes(Array As Byte(0x1B,0x40),0,2)";
_out.WriteBytes(new byte[]{(byte) (0x1b),(byte) (0x40)},(int) (0),(int) (2));
 //BA.debugLineNum = 132;BA.debugLine="dotsAtTheLeft = Bit.And(dotsAtTheLeft,0xFFF8)";
_dotsattheleft = __c.Bit.And(_dotsattheleft,(int) (0xfff8));
 //BA.debugLineNum = 134;BA.debugLine="out.WriteBytes(Array As Byte(0x1D,0x76,0x30,0x30)";
_out.WriteBytes(new byte[]{(byte) (0x1d),(byte) (0x76),(byte) (0x30),(byte) (0x30)},(int) (0),(int) (4));
 //BA.debugLineNum = 135;BA.debugLine="Dim mbytes As Int = (myBitmap.Width+7)/8 + dotsAt";
_mbytes = (int) ((_mybitmap.getWidth()+7)/(double)8+_dotsattheleft/(double)8);
 //BA.debugLineNum = 136;BA.debugLine="Dim wl As Int = Bit.And(mbytes,0xFF)";
_wl = __c.Bit.And(_mbytes,(int) (0xff));
 //BA.debugLineNum = 137;BA.debugLine="Dim wh As Int = Bit.ShiftRight(mbytes,8)";
_wh = __c.Bit.ShiftRight(_mbytes,(int) (8));
 //BA.debugLineNum = 138;BA.debugLine="Dim hl As Int = Bit.And(myBitmap.height,0xFF)";
_hl = __c.Bit.And(_mybitmap.getHeight(),(int) (0xff));
 //BA.debugLineNum = 139;BA.debugLine="Dim hh As Int = Bit.ShiftRight(myBitmap.height,8)";
_hh = __c.Bit.ShiftRight(_mybitmap.getHeight(),(int) (8));
 //BA.debugLineNum = 141;BA.debugLine="out.WriteBytes(Array As Byte(wl,wh,hl,hh),0,4)	'";
_out.WriteBytes(new byte[]{(byte) (_wl),(byte) (_wh),(byte) (_hl),(byte) (_hh)},(int) (0),(int) (4));
 //BA.debugLineNum = 143;BA.debugLine="For y=0 To myBitmap.Height-1";
{
final int step15 = 1;
final int limit15 = (int) (_mybitmap.getHeight()-1);
_y = (int) (0) ;
for (;_y <= limit15 ;_y = _y + step15 ) {
 //BA.debugLineNum = 145;BA.debugLine="For k=0 To (dotsAtTheLeft/8)-1";
{
final int step16 = 1;
final int limit16 = (int) ((_dotsattheleft/(double)8)-1);
_k = (int) (0) ;
for (;_k <= limit16 ;_k = _k + step16 ) {
 //BA.debugLineNum = 146;BA.debugLine="out.WriteBytes(Array As Byte(0x00),0,1)";
_out.WriteBytes(new byte[]{(byte) (0x00)},(int) (0),(int) (1));
 }
};
 //BA.debugLineNum = 149;BA.debugLine="For x=0 To myBitmap.Width-1 Step 8";
{
final int step19 = 8;
final int limit19 = (int) (_mybitmap.getWidth()-1);
_x = (int) (0) ;
for (;_x <= limit19 ;_x = _x + step19 ) {
 //BA.debugLineNum = 150;BA.debugLine="Dim slice As Byte=0";
_slice = (byte) (0);
 //BA.debugLineNum = 151;BA.debugLine="For b=0 To 7";
{
final int step21 = 1;
final int limit21 = (int) (7);
_b = (int) (0) ;
for (;_b <= limit21 ;_b = _b + step21 ) {
 //BA.debugLineNum = 152;BA.debugLine="If x+b < myBitmap.Width Then";
if (_x+_b<_mybitmap.getWidth()) { 
 //BA.debugLineNum = 153;BA.debugLine="If Bit.And( myBitmap.GetPixel(x+b,y),0xFFFFFF";
if (__c.Bit.And(_mybitmap.GetPixel((int) (_x+_b),_y),(int) (0xffffff))!=0xffffff) { 
 //BA.debugLineNum = 154;BA.debugLine="slice=Bit.Or(slice,Bit.ShiftRight(0x80,b))";
_slice = (byte) (__c.Bit.Or((int) (_slice),__c.Bit.ShiftRight((int) (0x80),_b)));
 };
 };
 }
};
 //BA.debugLineNum = 158;BA.debugLine="out.WriteBytes(Array As Byte(slice),0,1)";
_out.WriteBytes(new byte[]{_slice},(int) (0),(int) (1));
 }
};
 }
};
 //BA.debugLineNum = 162;BA.debugLine="Return out.ToBytesArray()";
if (true) return _out.ToBytesArray();
 //BA.debugLineNum = 164;BA.debugLine="End Sub";
return null;
}
public String  _initialize(anywheresoftware.b4a.BA _ba) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 5;BA.debugLine="Public Sub Initialize";
 //BA.debugLineNum = 7;BA.debugLine="End Sub";
return "";
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
return BA.SubDelegator.SubNotFound;
}
}
