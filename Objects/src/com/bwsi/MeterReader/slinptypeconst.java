package com.bwsi.MeterReader;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class slinptypeconst extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "com.bwsi.MeterReader.slinptypeconst");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            
        }
        if (BA.isShellModeRuntimeCheck(ba)) 
			   this.getClass().getMethod("_class_globals", com.bwsi.MeterReader.slinptypeconst.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
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
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 2;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 3;BA.debugLine="End Sub";
return "";
}
public int  _getinputtype(anywheresoftware.b4a.objects.EditTextWrapper _et) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
 //BA.debugLineNum = 17;BA.debugLine="Sub GetInputType(ET As EditText) As Int";
 //BA.debugLineNum = 18;BA.debugLine="Dim R As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 19;BA.debugLine="R.Target=ET";
_r.Target = (Object)(_et.getObject());
 //BA.debugLineNum = 20;BA.debugLine="Return R.RunMethod(\"getInputType\")";
if (true) return (int)(BA.ObjectToNumber(_r.RunMethod("getInputType")));
 //BA.debugLineNum = 21;BA.debugLine="End Sub";
return 0;
}
public int  _getinputtypeclass(anywheresoftware.b4a.objects.EditTextWrapper _et) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
 //BA.debugLineNum = 23;BA.debugLine="Sub GetInputTypeClass(ET As EditText) As Int";
 //BA.debugLineNum = 24;BA.debugLine="Dim R As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 25;BA.debugLine="R.Target=ET";
_r.Target = (Object)(_et.getObject());
 //BA.debugLineNum = 26;BA.debugLine="Return Bit.And(R.RunMethod(\"getInputType\"),TYPE_M";
if (true) return __c.Bit.And((int)(BA.ObjectToNumber(_r.RunMethod("getInputType"))),_type_mask_class());
 //BA.debugLineNum = 27;BA.debugLine="End Sub";
return 0;
}
public int  _getinputtypeflags(anywheresoftware.b4a.objects.EditTextWrapper _et) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
 //BA.debugLineNum = 29;BA.debugLine="Sub GetInputTypeFlags(ET As EditText) As Int";
 //BA.debugLineNum = 30;BA.debugLine="Dim R As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 31;BA.debugLine="R.Target=ET";
_r.Target = (Object)(_et.getObject());
 //BA.debugLineNum = 32;BA.debugLine="Return Bit.And(R.RunMethod(\"getInputType\"),TYPE_M";
if (true) return __c.Bit.And((int)(BA.ObjectToNumber(_r.RunMethod("getInputType"))),_type_mask_flags());
 //BA.debugLineNum = 33;BA.debugLine="End Sub";
return 0;
}
public int  _getinputtypevariation(anywheresoftware.b4a.objects.EditTextWrapper _et) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
 //BA.debugLineNum = 35;BA.debugLine="Sub GetInputTypeVariation(ET As EditText) As Int";
 //BA.debugLineNum = 36;BA.debugLine="Dim R As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 37;BA.debugLine="R.Target=ET";
_r.Target = (Object)(_et.getObject());
 //BA.debugLineNum = 38;BA.debugLine="Return Bit.And(R.RunMethod(\"getInputType\"),TYPE_M";
if (true) return __c.Bit.And((int)(BA.ObjectToNumber(_r.RunMethod("getInputType"))),_type_mask_variation());
 //BA.debugLineNum = 39;BA.debugLine="End Sub";
return 0;
}
public String  _initialize(anywheresoftware.b4a.BA _ba) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 5;BA.debugLine="Public Sub Initialize";
 //BA.debugLineNum = 6;BA.debugLine="End Sub";
return "";
}
public String  _setinputtype(anywheresoftware.b4a.objects.EditTextWrapper _et,int[] _itype) throws Exception{
int _res = 0;
int _i = 0;
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
 //BA.debugLineNum = 7;BA.debugLine="Sub SetInputType(ET As EditText,IType() As Int)";
 //BA.debugLineNum = 8;BA.debugLine="Dim Res As Int = 0";
_res = (int) (0);
 //BA.debugLineNum = 9;BA.debugLine="For Each I As Int In IType";
{
final int[] group2 = _itype;
final int groupLen2 = group2.length
;int index2 = 0;
;
for (; index2 < groupLen2;index2++){
_i = group2[index2];
 //BA.debugLineNum = 10;BA.debugLine="Res=Bit.Or(Res,I)";
_res = __c.Bit.Or(_res,_i);
 }
};
 //BA.debugLineNum = 12;BA.debugLine="Dim R As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 13;BA.debugLine="R.Target=ET";
_r.Target = (Object)(_et.getObject());
 //BA.debugLineNum = 14;BA.debugLine="R.RunMethod2(\"setInputType\",Res,\"java.lang.int\")";
_r.RunMethod2("setInputType",BA.NumberToString(_res),"java.lang.int");
 //BA.debugLineNum = 15;BA.debugLine="End Sub";
return "";
}
public int  _type_class_datetime() throws Exception{
 //BA.debugLineNum = 42;BA.debugLine="Sub TYPE_CLASS_DATETIME As Int";
 //BA.debugLineNum = 43;BA.debugLine="Return 4 '(0x00000004)";
if (true) return (int) (4);
 //BA.debugLineNum = 44;BA.debugLine="End Sub";
return 0;
}
public int  _type_class_number() throws Exception{
 //BA.debugLineNum = 47;BA.debugLine="Sub TYPE_CLASS_NUMBER As Int";
 //BA.debugLineNum = 48;BA.debugLine="Return 2 '(0x00000002)";
if (true) return (int) (2);
 //BA.debugLineNum = 49;BA.debugLine="End Sub";
return 0;
}
public int  _type_class_phone() throws Exception{
 //BA.debugLineNum = 52;BA.debugLine="Sub TYPE_CLASS_PHONE As Int";
 //BA.debugLineNum = 53;BA.debugLine="Return 3 '(0x00000003)";
if (true) return (int) (3);
 //BA.debugLineNum = 54;BA.debugLine="End Sub";
return 0;
}
public int  _type_class_text() throws Exception{
 //BA.debugLineNum = 57;BA.debugLine="Sub TYPE_CLASS_TEXT As Int";
 //BA.debugLineNum = 58;BA.debugLine="Return 1 '(0x00000001)";
if (true) return (int) (1);
 //BA.debugLineNum = 59;BA.debugLine="End Sub";
return 0;
}
public int  _type_datetime_variation_date() throws Exception{
 //BA.debugLineNum = 62;BA.debugLine="Sub TYPE_DATETIME_VARIATION_DATE As Int";
 //BA.debugLineNum = 63;BA.debugLine="Return 16 '(0x00000010)";
if (true) return (int) (16);
 //BA.debugLineNum = 64;BA.debugLine="End Sub";
return 0;
}
public int  _type_datetime_variation_normal() throws Exception{
 //BA.debugLineNum = 67;BA.debugLine="Sub TYPE_DATETIME_VARIATION_NORMAL As Int";
 //BA.debugLineNum = 68;BA.debugLine="Return 0 '(0x00000000)";
if (true) return (int) (0);
 //BA.debugLineNum = 69;BA.debugLine="End Sub";
return 0;
}
public int  _type_datetime_variation_time() throws Exception{
 //BA.debugLineNum = 72;BA.debugLine="Sub TYPE_DATETIME_VARIATION_TIME As Int";
 //BA.debugLineNum = 73;BA.debugLine="Return 32 '(0x00000020)";
if (true) return (int) (32);
 //BA.debugLineNum = 74;BA.debugLine="End Sub";
return 0;
}
public int  _type_mask_class() throws Exception{
 //BA.debugLineNum = 77;BA.debugLine="Sub TYPE_MASK_CLASS As Int";
 //BA.debugLineNum = 78;BA.debugLine="Return 15 '(0x0000000f)";
if (true) return (int) (15);
 //BA.debugLineNum = 79;BA.debugLine="End Sub";
return 0;
}
public int  _type_mask_flags() throws Exception{
 //BA.debugLineNum = 82;BA.debugLine="Sub TYPE_MASK_FLAGS As Int";
 //BA.debugLineNum = 83;BA.debugLine="Return 16773120 '(0x00fff000)";
if (true) return (int) (16773120);
 //BA.debugLineNum = 84;BA.debugLine="End Sub";
return 0;
}
public int  _type_mask_variation() throws Exception{
 //BA.debugLineNum = 87;BA.debugLine="Sub TYPE_MASK_VARIATION As Int";
 //BA.debugLineNum = 88;BA.debugLine="Return 4080 '(0x00000ff0)";
if (true) return (int) (4080);
 //BA.debugLineNum = 89;BA.debugLine="End Sub";
return 0;
}
public int  _type_null() throws Exception{
 //BA.debugLineNum = 92;BA.debugLine="Sub TYPE_NULL As Int";
 //BA.debugLineNum = 93;BA.debugLine="Return 0 '(0x00000000)";
if (true) return (int) (0);
 //BA.debugLineNum = 94;BA.debugLine="End Sub";
return 0;
}
public int  _type_number_flag_decimal() throws Exception{
 //BA.debugLineNum = 97;BA.debugLine="Sub TYPE_NUMBER_FLAG_DECIMAL As Int";
 //BA.debugLineNum = 98;BA.debugLine="Return 8192 '(0x00002000)";
if (true) return (int) (8192);
 //BA.debugLineNum = 99;BA.debugLine="End Sub";
return 0;
}
public int  _type_number_flag_signed() throws Exception{
 //BA.debugLineNum = 102;BA.debugLine="Sub TYPE_NUMBER_FLAG_SIGNED As Int";
 //BA.debugLineNum = 103;BA.debugLine="Return 4096 '(0x00001000)";
if (true) return (int) (4096);
 //BA.debugLineNum = 104;BA.debugLine="End Sub";
return 0;
}
public int  _type_number_variation_normal() throws Exception{
 //BA.debugLineNum = 107;BA.debugLine="Sub TYPE_NUMBER_VARIATION_NORMAL As Int";
 //BA.debugLineNum = 108;BA.debugLine="Return 0 '(0x00000000)";
if (true) return (int) (0);
 //BA.debugLineNum = 109;BA.debugLine="End Sub";
return 0;
}
public int  _type_number_variation_password() throws Exception{
 //BA.debugLineNum = 112;BA.debugLine="Sub TYPE_NUMBER_VARIATION_PASSWORD As Int";
 //BA.debugLineNum = 113;BA.debugLine="Return 16 '(0x00000010)";
if (true) return (int) (16);
 //BA.debugLineNum = 114;BA.debugLine="End Sub";
return 0;
}
public int  _type_text_flag_auto_complete() throws Exception{
 //BA.debugLineNum = 128;BA.debugLine="Sub TYPE_TEXT_FLAG_AUTO_COMPLETE As Int";
 //BA.debugLineNum = 129;BA.debugLine="Return 65536 '(0x00008000)";
if (true) return (int) (65536);
 //BA.debugLineNum = 130;BA.debugLine="End Sub";
return 0;
}
public int  _type_text_flag_auto_correct() throws Exception{
 //BA.debugLineNum = 123;BA.debugLine="Sub TYPE_TEXT_FLAG_AUTO_CORRECT As Int";
 //BA.debugLineNum = 124;BA.debugLine="Return 32768 '(0x00008000)";
if (true) return (int) (32768);
 //BA.debugLineNum = 125;BA.debugLine="End Sub";
return 0;
}
public int  _type_text_flag_cap_characters() throws Exception{
 //BA.debugLineNum = 133;BA.debugLine="Sub TYPE_TEXT_FLAG_CAP_CHARACTERS As Int";
 //BA.debugLineNum = 134;BA.debugLine="Return 4096 '(0x00001000)";
if (true) return (int) (4096);
 //BA.debugLineNum = 135;BA.debugLine="End Sub";
return 0;
}
public int  _type_text_flag_cap_sentences() throws Exception{
 //BA.debugLineNum = 138;BA.debugLine="Sub TYPE_TEXT_FLAG_CAP_SENTENCES As Int";
 //BA.debugLineNum = 139;BA.debugLine="Return 16384 '(0x00004000)";
if (true) return (int) (16384);
 //BA.debugLineNum = 140;BA.debugLine="End Sub";
return 0;
}
public int  _type_text_flag_cap_words() throws Exception{
 //BA.debugLineNum = 143;BA.debugLine="Sub TYPE_TEXT_FLAG_CAP_WORDS As Int";
 //BA.debugLineNum = 144;BA.debugLine="Return 8192 '(0x00002000)";
if (true) return (int) (8192);
 //BA.debugLineNum = 145;BA.debugLine="End Sub";
return 0;
}
public int  _type_text_flag_ime_multi_line() throws Exception{
 //BA.debugLineNum = 148;BA.debugLine="Sub TYPE_TEXT_FLAG_IME_MULTI_LINE As Int";
 //BA.debugLineNum = 149;BA.debugLine="Return 262144 '(0x00040000)";
if (true) return (int) (262144);
 //BA.debugLineNum = 150;BA.debugLine="End Sub";
return 0;
}
public int  _type_text_flag_multi_line() throws Exception{
 //BA.debugLineNum = 153;BA.debugLine="Sub TYPE_TEXT_FLAG_MULTI_LINE As Int";
 //BA.debugLineNum = 154;BA.debugLine="Return 131072 '(0x00020000)";
if (true) return (int) (131072);
 //BA.debugLineNum = 155;BA.debugLine="End Sub";
return 0;
}
public int  _type_text_flag_no_suggestions() throws Exception{
 //BA.debugLineNum = 158;BA.debugLine="Sub TYPE_TEXT_FLAG_NO_SUGGESTIONS As Int";
 //BA.debugLineNum = 159;BA.debugLine="Return 524288 '(0x00080000)";
if (true) return (int) (524288);
 //BA.debugLineNum = 160;BA.debugLine="End Sub";
return 0;
}
public int  _type_text_variation_email_address() throws Exception{
 //BA.debugLineNum = 163;BA.debugLine="Sub TYPE_TEXT_VARIATION_EMAIL_ADDRESS As Int";
 //BA.debugLineNum = 164;BA.debugLine="Return 32 '(0x00000020)";
if (true) return (int) (32);
 //BA.debugLineNum = 165;BA.debugLine="End Sub";
return 0;
}
public int  _type_text_variation_email_subject() throws Exception{
 //BA.debugLineNum = 168;BA.debugLine="Sub TYPE_TEXT_VARIATION_EMAIL_SUBJECT As Int";
 //BA.debugLineNum = 169;BA.debugLine="Return 48 '(0x00000030)";
if (true) return (int) (48);
 //BA.debugLineNum = 170;BA.debugLine="End Sub";
return 0;
}
public int  _type_text_variation_filter() throws Exception{
 //BA.debugLineNum = 173;BA.debugLine="Sub TYPE_TEXT_VARIATION_FILTER As Int";
 //BA.debugLineNum = 174;BA.debugLine="Return 176 '(0x000000b0)";
if (true) return (int) (176);
 //BA.debugLineNum = 175;BA.debugLine="End Sub";
return 0;
}
public int  _type_text_variation_long_message() throws Exception{
 //BA.debugLineNum = 178;BA.debugLine="Sub TYPE_TEXT_VARIATION_LONG_MESSAGE As Int";
 //BA.debugLineNum = 179;BA.debugLine="Return 80 '(0x00000050)";
if (true) return (int) (80);
 //BA.debugLineNum = 180;BA.debugLine="End Sub";
return 0;
}
public int  _type_text_variation_normal() throws Exception{
 //BA.debugLineNum = 183;BA.debugLine="Sub TYPE_TEXT_VARIATION_NORMAL As Int";
 //BA.debugLineNum = 184;BA.debugLine="Return 0 '(0x00000000)";
if (true) return (int) (0);
 //BA.debugLineNum = 185;BA.debugLine="End Sub";
return 0;
}
public int  _type_text_variation_password() throws Exception{
 //BA.debugLineNum = 188;BA.debugLine="Sub TYPE_TEXT_VARIATION_PASSWORD As Int";
 //BA.debugLineNum = 189;BA.debugLine="Return 128 '(0x00000080)";
if (true) return (int) (128);
 //BA.debugLineNum = 190;BA.debugLine="End Sub";
return 0;
}
public int  _type_text_variation_person_name() throws Exception{
 //BA.debugLineNum = 193;BA.debugLine="Sub TYPE_TEXT_VARIATION_PERSON_NAME As Int";
 //BA.debugLineNum = 194;BA.debugLine="Return 96 '(0x00000060)";
if (true) return (int) (96);
 //BA.debugLineNum = 195;BA.debugLine="End Sub";
return 0;
}
public int  _type_text_variation_phonetic() throws Exception{
 //BA.debugLineNum = 198;BA.debugLine="Sub TYPE_TEXT_VARIATION_PHONETIC As Int";
 //BA.debugLineNum = 199;BA.debugLine="Return 192 '(0x000000c0)";
if (true) return (int) (192);
 //BA.debugLineNum = 200;BA.debugLine="End Sub";
return 0;
}
public int  _type_text_variation_postal_address() throws Exception{
 //BA.debugLineNum = 203;BA.debugLine="Sub TYPE_TEXT_VARIATION_POSTAL_ADDRESS As Int";
 //BA.debugLineNum = 204;BA.debugLine="Return 112 '(0x00000070)";
if (true) return (int) (112);
 //BA.debugLineNum = 205;BA.debugLine="End Sub";
return 0;
}
public int  _type_text_variation_short_message() throws Exception{
 //BA.debugLineNum = 208;BA.debugLine="Sub TYPE_TEXT_VARIATION_SHORT_MESSAGE As Int";
 //BA.debugLineNum = 209;BA.debugLine="Return 64 '(0x00000040)";
if (true) return (int) (64);
 //BA.debugLineNum = 210;BA.debugLine="End Sub";
return 0;
}
public int  _type_text_variation_uri() throws Exception{
 //BA.debugLineNum = 213;BA.debugLine="Sub TYPE_TEXT_VARIATION_URI As Int";
 //BA.debugLineNum = 214;BA.debugLine="Return 16 '(0x00000010)";
if (true) return (int) (16);
 //BA.debugLineNum = 215;BA.debugLine="End Sub";
return 0;
}
public int  _type_text_variation_visible_password() throws Exception{
 //BA.debugLineNum = 218;BA.debugLine="Sub TYPE_TEXT_VARIATION_VISIBLE_PASSWORD As Int";
 //BA.debugLineNum = 219;BA.debugLine="Return 144 '(0x00000090)";
if (true) return (int) (144);
 //BA.debugLineNum = 220;BA.debugLine="End Sub";
return 0;
}
public int  _type_text_variation_web_edit_text() throws Exception{
 //BA.debugLineNum = 223;BA.debugLine="Sub TYPE_TEXT_VARIATION_WEB_EDIT_TEXT As Int";
 //BA.debugLineNum = 224;BA.debugLine="Return 160 '(0x000000a0)";
if (true) return (int) (160);
 //BA.debugLineNum = 225;BA.debugLine="End Sub";
return 0;
}
public int  _type_text_variation_web_email_address() throws Exception{
 //BA.debugLineNum = 228;BA.debugLine="Sub TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS As Int";
 //BA.debugLineNum = 229;BA.debugLine="Return 208 '(0x000000d0)";
if (true) return (int) (208);
 //BA.debugLineNum = 230;BA.debugLine="End Sub";
return 0;
}
public int  _type_text_variation_web_password() throws Exception{
 //BA.debugLineNum = 233;BA.debugLine="Sub TYPE_TEXT_VARIATION_WEB_PASSWORD As Int";
 //BA.debugLineNum = 234;BA.debugLine="Return 224 '(0x000000e0)";
if (true) return (int) (224);
 //BA.debugLineNum = 235;BA.debugLine="End Sub";
return 0;
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
return BA.SubDelegator.SubNotFound;
}
}
