package com.bwsi.MeterReader;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class bytesbuilder extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "com.bwsi.MeterReader.bytesbuilder");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            
        }
        if (BA.isShellModeRuntimeCheck(ba)) 
			   this.getClass().getMethod("_class_globals", com.bwsi.MeterReader.bytesbuilder.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public byte[] _mbuffer = null;
public anywheresoftware.b4a.agraham.byteconverter.ByteConverter _bc = null;
public int _mlength = 0;
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
public com.bwsi.MeterReader.mainscreen _mainscreen = null;
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
public com.bwsi.MeterReader.bytesbuilder  _append(byte[] _data) throws Exception{
int _oldlength = 0;
 //BA.debugLineNum = 26;BA.debugLine="Public Sub Append(Data() As Byte) As BytesBuilder";
 //BA.debugLineNum = 27;BA.debugLine="Dim OldLength As Int = ChangeLength(mLength + Dat";
_oldlength = _changelength((int) (_mlength+_data.length));
 //BA.debugLineNum = 28;BA.debugLine="bc.ArrayCopy(Data, 0, mBuffer, OldLength, Data.Le";
_bc.ArrayCopy((Object)(_data),(int) (0),(Object)(_mbuffer),_oldlength,_data.length);
 //BA.debugLineNum = 29;BA.debugLine="Return Me";
if (true) return (com.bwsi.MeterReader.bytesbuilder)(this);
 //BA.debugLineNum = 30;BA.debugLine="End Sub";
return null;
}
public int  _changelength(int _newlength) throws Exception{
int _oldlength = 0;
byte[] _b = null;
 //BA.debugLineNum = 14;BA.debugLine="Private Sub ChangeLength (NewLength As Int) As Int";
 //BA.debugLineNum = 15;BA.debugLine="Dim OldLength As Int = mLength";
_oldlength = _mlength;
 //BA.debugLineNum = 16;BA.debugLine="mLength = NewLength";
_mlength = _newlength;
 //BA.debugLineNum = 17;BA.debugLine="If mBuffer.Length < mLength Then";
if (_mbuffer.length<_mlength) { 
 //BA.debugLineNum = 18;BA.debugLine="Dim b(Max(mBuffer.Length * 2, NewLength)) As Byt";
_b = new byte[(int) (__c.Max(_mbuffer.length*2,_newlength))];
;
 //BA.debugLineNum = 19;BA.debugLine="bc.ArrayCopy(mBuffer, 0, b, 0, mBuffer.Length)";
_bc.ArrayCopy((Object)(_mbuffer),(int) (0),(Object)(_b),(int) (0),_mbuffer.length);
 //BA.debugLineNum = 20;BA.debugLine="mBuffer = b";
_mbuffer = _b;
 };
 //BA.debugLineNum = 22;BA.debugLine="Return OldLength";
if (true) return _oldlength;
 //BA.debugLineNum = 23;BA.debugLine="End Sub";
return 0;
}
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 2;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 3;BA.debugLine="Private mBuffer() As Byte";
_mbuffer = new byte[(int) (0)];
;
 //BA.debugLineNum = 4;BA.debugLine="Private bc As ByteConverter";
_bc = new anywheresoftware.b4a.agraham.byteconverter.ByteConverter();
 //BA.debugLineNum = 5;BA.debugLine="Private mLength As Int";
_mlength = 0;
 //BA.debugLineNum = 6;BA.debugLine="End Sub";
return "";
}
public String  _clear() throws Exception{
 //BA.debugLineNum = 32;BA.debugLine="Public Sub Clear";
 //BA.debugLineNum = 33;BA.debugLine="ChangeLength(0)";
_changelength((int) (0));
 //BA.debugLineNum = 34;BA.debugLine="End Sub";
return "";
}
public byte[]  _getbuffer() throws Exception{
 //BA.debugLineNum = 98;BA.debugLine="Private Sub getBuffer As Byte() 'ignore";
 //BA.debugLineNum = 99;BA.debugLine="End Sub";
return null;
}
public int  _getlength() throws Exception{
 //BA.debugLineNum = 87;BA.debugLine="Public Sub getLength As Int";
 //BA.debugLineNum = 88;BA.debugLine="Return mLength";
if (true) return _mlength;
 //BA.debugLineNum = 89;BA.debugLine="End Sub";
return 0;
}
public int  _indexof(byte[] _searchfor) throws Exception{
 //BA.debugLineNum = 102;BA.debugLine="Public Sub IndexOf(SearchFor() As Byte) As Int";
 //BA.debugLineNum = 103;BA.debugLine="Return IndexOf2(SearchFor, 0)";
if (true) return _indexof2(_searchfor,(int) (0));
 //BA.debugLineNum = 104;BA.debugLine="End Sub";
return 0;
}
public int  _indexof2(byte[] _searchfor,int _index) throws Exception{
int _i1 = 0;
int _i2 = 0;
 //BA.debugLineNum = 107;BA.debugLine="Public Sub IndexOf2(SearchFor() As Byte, Index As";
 //BA.debugLineNum = 108;BA.debugLine="For i1 = Index To mLength - SearchFor.Length";
{
final int step1 = 1;
final int limit1 = (int) (_mlength-_searchfor.length);
_i1 = _index ;
for (;_i1 <= limit1 ;_i1 = _i1 + step1 ) {
 //BA.debugLineNum = 109;BA.debugLine="For i2 = 0 To SearchFor.Length - 1";
{
final int step2 = 1;
final int limit2 = (int) (_searchfor.length-1);
_i2 = (int) (0) ;
for (;_i2 <= limit2 ;_i2 = _i2 + step2 ) {
 //BA.debugLineNum = 110;BA.debugLine="If SearchFor(i2) <> mBuffer(i1 + i2) Then";
if (_searchfor[_i2]!=_mbuffer[(int) (_i1+_i2)]) { 
 //BA.debugLineNum = 111;BA.debugLine="Exit";
if (true) break;
 };
 }
};
 //BA.debugLineNum = 114;BA.debugLine="If i2 = SearchFor.Length Then";
if (_i2==_searchfor.length) { 
 //BA.debugLineNum = 115;BA.debugLine="Return i1";
if (true) return _i1;
 };
 }
};
 //BA.debugLineNum = 118;BA.debugLine="Return -1";
if (true) return (int) (-1);
 //BA.debugLineNum = 119;BA.debugLine="End Sub";
return 0;
}
public String  _initialize(anywheresoftware.b4a.BA _ba) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 8;BA.debugLine="Public Sub Initialize";
 //BA.debugLineNum = 9;BA.debugLine="Dim mBuffer(100) As Byte";
_mbuffer = new byte[(int) (100)];
;
 //BA.debugLineNum = 10;BA.debugLine="mLength = 0";
_mlength = (int) (0);
 //BA.debugLineNum = 11;BA.debugLine="End Sub";
return "";
}
public String  _insert(int _index,byte[] _data) throws Exception{
byte[] _afterindex = null;
 //BA.debugLineNum = 38;BA.debugLine="Public Sub Insert(Index As Int, Data() As Byte)";
 //BA.debugLineNum = 39;BA.debugLine="If Index >= mLength Then";
if (_index>=_mlength) { 
 //BA.debugLineNum = 40;BA.debugLine="If Index > mLength Then Log(\"Index too large\")";
if (_index>_mlength) { 
__c.LogImpl("410616834","Index too large",0);};
 //BA.debugLineNum = 41;BA.debugLine="Append(Data)";
_append(_data);
 }else {
 //BA.debugLineNum = 43;BA.debugLine="Dim AfterIndex() As Byte = SubArray(Index)";
_afterindex = _subarray(_index);
 //BA.debugLineNum = 44;BA.debugLine="ChangeLength(mLength + Data.Length)";
_changelength((int) (_mlength+_data.length));
 //BA.debugLineNum = 45;BA.debugLine="bc.ArrayCopy(Data, 0, mBuffer, Index, Data.Lengt";
_bc.ArrayCopy((Object)(_data),(int) (0),(Object)(_mbuffer),_index,_data.length);
 //BA.debugLineNum = 46;BA.debugLine="bc.ArrayCopy(AfterIndex, 0, mBuffer, Index + Dat";
_bc.ArrayCopy((Object)(_afterindex),(int) (0),(Object)(_mbuffer),(int) (_index+_data.length),_afterindex.length);
 };
 //BA.debugLineNum = 48;BA.debugLine="End Sub";
return "";
}
public byte[]  _remove(int _beginindex,int _endindex) throws Exception{
byte[] _res = null;
byte[] _afterendindex = null;
 //BA.debugLineNum = 64;BA.debugLine="Public Sub Remove(BeginIndex As Int, EndIndex As I";
 //BA.debugLineNum = 65;BA.debugLine="Dim res() As Byte = SubArray2(BeginIndex, EndInde";
_res = _subarray2(_beginindex,_endindex);
 //BA.debugLineNum = 66;BA.debugLine="If EndIndex <= mLength Then";
if (_endindex<=_mlength) { 
 //BA.debugLineNum = 67;BA.debugLine="Dim AfterEndIndex() As Byte = SubArray(EndIndex)";
_afterendindex = _subarray(_endindex);
 //BA.debugLineNum = 68;BA.debugLine="bc.ArrayCopy(AfterEndIndex, 0, mBuffer, BeginInd";
_bc.ArrayCopy((Object)(_afterendindex),(int) (0),(Object)(_mbuffer),_beginindex,_afterendindex.length);
 };
 //BA.debugLineNum = 70;BA.debugLine="ChangeLength(mLength - (EndIndex - BeginIndex))";
_changelength((int) (_mlength-(_endindex-_beginindex)));
 //BA.debugLineNum = 71;BA.debugLine="Return res";
if (true) return _res;
 //BA.debugLineNum = 72;BA.debugLine="End Sub";
return null;
}
public String  _set(int _index,byte[] _data) throws Exception{
 //BA.debugLineNum = 51;BA.debugLine="Public Sub Set(Index As Int, Data() As Byte)";
 //BA.debugLineNum = 52;BA.debugLine="If Index >= mLength Then";
if (_index>=_mlength) { 
 //BA.debugLineNum = 53;BA.debugLine="If Index > mLength Then Log(\"Index too large\")";
if (_index>_mlength) { 
__c.LogImpl("410682370","Index too large",0);};
 //BA.debugLineNum = 54;BA.debugLine="Append(Data)";
_append(_data);
 }else {
 //BA.debugLineNum = 56;BA.debugLine="If Data.Length + Index > mLength Then";
if (_data.length+_index>_mlength) { 
 //BA.debugLineNum = 57;BA.debugLine="ChangeLength(Data.Length + Index)";
_changelength((int) (_data.length+_index));
 };
 //BA.debugLineNum = 59;BA.debugLine="bc.ArrayCopy(Data, 0, mBuffer, Index, Data.Lengt";
_bc.ArrayCopy((Object)(_data),(int) (0),(Object)(_mbuffer),_index,_data.length);
 };
 //BA.debugLineNum = 61;BA.debugLine="End Sub";
return "";
}
public byte[]  _subarray(int _beginindex) throws Exception{
 //BA.debugLineNum = 75;BA.debugLine="Public Sub SubArray(BeginIndex As Int) As Byte()";
 //BA.debugLineNum = 76;BA.debugLine="Return SubArray2(BeginIndex, mLength)";
if (true) return _subarray2(_beginindex,_mlength);
 //BA.debugLineNum = 77;BA.debugLine="End Sub";
return null;
}
public byte[]  _subarray2(int _beginindex,int _endindex) throws Exception{
byte[] _b = null;
 //BA.debugLineNum = 81;BA.debugLine="Public Sub SubArray2(BeginIndex As Int, EndIndex A";
 //BA.debugLineNum = 82;BA.debugLine="Dim b(EndIndex - BeginIndex) As Byte";
_b = new byte[(int) (_endindex-_beginindex)];
;
 //BA.debugLineNum = 83;BA.debugLine="bc.ArrayCopy(mBuffer, BeginIndex, b, 0, b.Length)";
_bc.ArrayCopy((Object)(_mbuffer),_beginindex,(Object)(_b),(int) (0),_b.length);
 //BA.debugLineNum = 84;BA.debugLine="Return b";
if (true) return _b;
 //BA.debugLineNum = 85;BA.debugLine="End Sub";
return null;
}
public byte[]  _toarray() throws Exception{
 //BA.debugLineNum = 92;BA.debugLine="Public Sub ToArray() As Byte()";
 //BA.debugLineNum = 93;BA.debugLine="Return SubArray(0)";
if (true) return _subarray((int) (0));
 //BA.debugLineNum = 94;BA.debugLine="End Sub";
return null;
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
return BA.SubDelegator.SubNotFound;
}
}
