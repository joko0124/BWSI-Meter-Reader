package com.bwsi.MeterReader;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class customfunctions {
private static customfunctions mostCurrent = new customfunctions();
public static Object getObject() {
    throw new RuntimeException("Code module does not support this method.");
}
 public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public static com.bwsi.MeterReader.keyvaluestore _kvs = null;
public static anywheresoftware.b4a.objects.StringUtils _su = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public b4a.example.dateutils _dateutils = null;
public com.bwsi.MeterReader.main _main = null;
public com.bwsi.MeterReader.datasyncing _datasyncing = null;
public com.bwsi.MeterReader.login _login = null;
public com.bwsi.MeterReader.dbasefunctions _dbasefunctions = null;
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
public static String  _clearuserdata(anywheresoftware.b4a.BA _ba) throws Exception{
 //BA.debugLineNum = 172;BA.debugLine="Public Sub ClearUserData";
 //BA.debugLineNum = 173;BA.debugLine="KVS.Initialize(File.DirInternal, \"mrandroid.dat\")";
_kvs._initialize /*String*/ ((_ba.processBA == null ? _ba : _ba.processBA),anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"mrandroid.dat");
 //BA.debugLineNum = 174;BA.debugLine="KVS.Remove(\"user_data\")";
_kvs._remove /*String*/ ("user_data");
 //BA.debugLineNum = 175;BA.debugLine="End Sub";
return "";
}
public static boolean  _confirmyn(anywheresoftware.b4a.BA _ba,String _smsg,String _stitle,String _spositive,String _scancel,String _snegative) throws Exception{
boolean _blnretval = false;
byte _bytans = (byte)0;
anywheresoftware.b4a.objects.CSBuilder _csmsg = null;
anywheresoftware.b4a.objects.CSBuilder _cstitle = null;
anywheresoftware.b4a.objects.CSBuilder _cspositive = null;
anywheresoftware.b4a.objects.CSBuilder _cscancel = null;
anywheresoftware.b4a.objects.CSBuilder _csnegative = null;
anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper _icon = null;
 //BA.debugLineNum = 7;BA.debugLine="Public Sub ConfirmYN(sMsg As String, sTitle As Str";
 //BA.debugLineNum = 8;BA.debugLine="Dim blnRetVal As Boolean";
_blnretval = false;
 //BA.debugLineNum = 9;BA.debugLine="Dim bytAns As Byte";
_bytans = (byte)0;
 //BA.debugLineNum = 11;BA.debugLine="Dim csMsg, csTitle, csPositive, csCancel, csNegat";
_csmsg = new anywheresoftware.b4a.objects.CSBuilder();
_cstitle = new anywheresoftware.b4a.objects.CSBuilder();
_cspositive = new anywheresoftware.b4a.objects.CSBuilder();
_cscancel = new anywheresoftware.b4a.objects.CSBuilder();
_csnegative = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 13;BA.debugLine="Dim icon As B4XBitmap";
_icon = new anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper();
 //BA.debugLineNum = 14;BA.debugLine="csTitle.Initialize.Color(Colors.Red).Append(sTitl";
_cstitle.Initialize().Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(_stitle)).PopAll();
 //BA.debugLineNum = 16;BA.debugLine="csMsg.Initialize.Bold.Color(Colors.DarkGray).Appe";
_csmsg.Initialize().Bold().Color(anywheresoftware.b4a.keywords.Common.Colors.DarkGray).Append(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.CRLF+_smsg)).PopAll();
 //BA.debugLineNum = 17;BA.debugLine="csPositive.Initialize.Bold.Size(20).Color(GlobalV";
_cspositive.Initialize().Bold().Size((int) (20)).Color((int) (mostCurrent._globalvar._pricolor /*double*/ )).Append(BA.ObjectToCharSequence(_spositive)).PopAll();
 //BA.debugLineNum = 18;BA.debugLine="csNegative.Initialize.Bold.Size(20).Color(Colors.";
_csnegative.Initialize().Bold().Size((int) (20)).Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(_spositive)).PopAll();
 //BA.debugLineNum = 19;BA.debugLine="csCancel.Initialize.Bold.Size(20).Color(Colors.Bl";
_cscancel.Initialize().Bold().Size((int) (20)).Color(anywheresoftware.b4a.keywords.Common.Colors.Black).Append(BA.ObjectToCharSequence(_spositive)).PopAll();
 //BA.debugLineNum = 21;BA.debugLine="icon = xui.LoadBitmapResize(File.DirAssets, \"ques";
_icon = _xui.LoadBitmapResize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"question_mark.png",anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (24)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (24)),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 23;BA.debugLine="csMsg.Initialize.Bold.Color(Colors.DarkGray).Appe";
_csmsg.Initialize().Bold().Color(anywheresoftware.b4a.keywords.Common.Colors.DarkGray).Append(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.CRLF+_smsg)).PopAll();
 //BA.debugLineNum = 24;BA.debugLine="csTitle.Initialize.Color(GlobalVar.PriColor).Appe";
_cstitle.Initialize().Color((int) (mostCurrent._globalvar._pricolor /*double*/ )).Append(BA.ObjectToCharSequence(_stitle)).PopAll();
 //BA.debugLineNum = 26;BA.debugLine="bytAns = Msgbox2(csMsg, csTitle, sPositive, sCanc";
_bytans = (byte) (anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence(_csmsg.getObject()),BA.ObjectToCharSequence(_cstitle.getObject()),_spositive,_scancel,_snegative,(android.graphics.Bitmap)(_icon.getObject()),_ba));
 //BA.debugLineNum = 27;BA.debugLine="If bytAns = DialogResponse.Positive Then";
if (_bytans==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 28;BA.debugLine="blnRetVal = True";
_blnretval = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 30;BA.debugLine="blnRetVal = False";
_blnretval = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 32;BA.debugLine="Return blnRetVal";
if (true) return _blnretval;
 //BA.debugLineNum = 33;BA.debugLine="End Sub";
return false;
}
public static boolean  _isconnectedtoserver(anywheresoftware.b4a.BA _ba,String _surl) throws Exception{
boolean _bretval = false;
int _parts = 0;
String _protocol = "";
String _remainder = "";
 //BA.debugLineNum = 267;BA.debugLine="Public Sub IsConnectedToServer(sURL As String) As";
 //BA.debugLineNum = 268;BA.debugLine="Dim bRetVal As Boolean";
_bretval = false;
 //BA.debugLineNum = 271;BA.debugLine="Dim parts As Int";
_parts = 0;
 //BA.debugLineNum = 273;BA.debugLine="parts = sURL.IndexOf(\"//\") + 2";
_parts = (int) (_surl.indexOf("//")+2);
 //BA.debugLineNum = 276;BA.debugLine="If parts > 1 Then";
if (_parts>1) { 
 //BA.debugLineNum = 277;BA.debugLine="Dim protocol As String";
_protocol = "";
 //BA.debugLineNum = 278;BA.debugLine="Dim remainder As String";
_remainder = "";
 //BA.debugLineNum = 280;BA.debugLine="protocol = sURL.SubString2(0, parts)";
_protocol = _surl.substring((int) (0),_parts);
 //BA.debugLineNum = 281;BA.debugLine="remainder = sURL.SubString(parts)";
_remainder = _surl.substring(_parts);
 //BA.debugLineNum = 283;BA.debugLine="LogColor(\"Protocol: \" & protocol, Colors.Cyan)";
anywheresoftware.b4a.keywords.Common.LogImpl("185655568","Protocol: "+_protocol,anywheresoftware.b4a.keywords.Common.Colors.Cyan);
 //BA.debugLineNum = 284;BA.debugLine="LogColor(\"Remainder: \" & remainder, Colors.Magen";
anywheresoftware.b4a.keywords.Common.LogImpl("185655569","Remainder: "+_remainder,anywheresoftware.b4a.keywords.Common.Colors.Magenta);
 }else {
 //BA.debugLineNum = 286;BA.debugLine="Log(\"Invalid URL format\")";
anywheresoftware.b4a.keywords.Common.LogImpl("185655571","Invalid URL format",0);
 };
 //BA.debugLineNum = 289;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 291;BA.debugLine="Try";
try { //BA.debugLineNum = 292;BA.debugLine="bRetVal = Ping(remainder,\"Report\",1,4)";
_bretval = _ping(_ba,_remainder,"Report",(int) (1),(int) (4));
 } 
       catch (Exception e18) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e18); //BA.debugLineNum = 294;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("185655579",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 };
 //BA.debugLineNum = 296;BA.debugLine="Return bRetVal";
if (true) return _bretval;
 //BA.debugLineNum = 297;BA.debugLine="End Sub";
return false;
}
public static boolean  _isthereinternetconnection(anywheresoftware.b4a.BA _ba) throws Exception{
boolean _bretval = false;
 //BA.debugLineNum = 254;BA.debugLine="Public Sub IsThereInternetConnection As Boolean";
 //BA.debugLineNum = 255;BA.debugLine="Dim bRetVal As Boolean";
_bretval = false;
 //BA.debugLineNum = 257;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 259;BA.debugLine="Try";
try { //BA.debugLineNum = 260;BA.debugLine="bRetVal = Ping(\"8.8.8.8\",\"Report\",1,4)";
_bretval = _ping(_ba,"8.8.8.8","Report",(int) (1),(int) (4));
 } 
       catch (Exception e6) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e6); //BA.debugLineNum = 262;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("184475912",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 };
 //BA.debugLineNum = 264;BA.debugLine="Return bRetVal";
if (true) return _bretval;
 //BA.debugLineNum = 265;BA.debugLine="End Sub";
return false;
}
public static boolean  _isthereuserdata(anywheresoftware.b4a.BA _ba) throws Exception{
boolean _bretval = false;
 //BA.debugLineNum = 177;BA.debugLine="Public Sub IsThereUserData As Boolean";
 //BA.debugLineNum = 178;BA.debugLine="Dim bRetVal As Boolean";
_bretval = false;
 //BA.debugLineNum = 180;BA.debugLine="KVS.Initialize(File.DirInternal, \"mrandroid.dat\")";
_kvs._initialize /*String*/ ((_ba.processBA == null ? _ba : _ba.processBA),anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"mrandroid.dat");
 //BA.debugLineNum = 181;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 182;BA.debugLine="Try";
try { //BA.debugLineNum = 183;BA.debugLine="If KVS.ContainsKey(\"user_data\") Then";
if (_kvs._containskey /*boolean*/ ("user_data")) { 
 //BA.debugLineNum = 184;BA.debugLine="bRetVal = True";
_bretval = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 186;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 };
 } 
       catch (Exception e11) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e11); //BA.debugLineNum = 189;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("115400972",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 };
 //BA.debugLineNum = 192;BA.debugLine="Log($\"User Data \"$ & bRetVal)";
anywheresoftware.b4a.keywords.Common.LogImpl("115400975",("User Data ")+BA.ObjectToString(_bretval),0);
 //BA.debugLineNum = 193;BA.debugLine="Return bRetVal";
if (true) return _bretval;
 //BA.debugLineNum = 194;BA.debugLine="End Sub";
return false;
}
public static boolean  _ping(anywheresoftware.b4a.BA _ba,String _url,String _resultstype,int _attempts,int _timeout) throws Exception{
anywheresoftware.b4a.keywords.StringBuilderWrapper _sb = null;
anywheresoftware.b4a.phone.Phone _p = null;
String _option = "";
 //BA.debugLineNum = 299;BA.debugLine="Private Sub Ping(Url As String, ResultsType As Str";
 //BA.debugLineNum = 301;BA.debugLine="Dim sb As StringBuilder";
_sb = new anywheresoftware.b4a.keywords.StringBuilderWrapper();
 //BA.debugLineNum = 302;BA.debugLine="Dim p As Phone";
_p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 303;BA.debugLine="Dim Option As String";
_option = "";
 //BA.debugLineNum = 305;BA.debugLine="sb.Initialize";
_sb.Initialize();
 //BA.debugLineNum = 307;BA.debugLine="If ResultsType = \"Report\" Then    Option = \" -v \"";
if ((_resultstype).equals("Report")) { 
_option = " -v ";};
 //BA.debugLineNum = 308;BA.debugLine="If ResultsType = \"Summary\" Or ResultsType = \"Stat";
if ((_resultstype).equals("Summary") || (_resultstype).equals("Status")) { 
_option = " -q ";};
 //BA.debugLineNum = 310;BA.debugLine="p.Shell(\"ping -c \" & Attempts & \" -W \" & Timeout";
_p.Shell("ping -c "+BA.NumberToString(_attempts)+" -W "+BA.NumberToString(_timeout)+_option+_url,(String[])(anywheresoftware.b4a.keywords.Common.Null),(java.lang.StringBuilder)(_sb.getObject()),(java.lang.StringBuilder)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 312;BA.debugLine="If sb.Length = 0 Or sb.ToString.Contains(\"Unreach";
if (_sb.getLength()==0 || _sb.ToString().contains("Unreachable")) { 
 //BA.debugLineNum = 313;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 316;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 318;BA.debugLine="End Sub";
return false;
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 1;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 2;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 3;BA.debugLine="Private KVS As KeyValueStore";
_kvs = new com.bwsi.MeterReader.keyvaluestore();
 //BA.debugLineNum = 4;BA.debugLine="Private SU As StringUtils";
_su = new anywheresoftware.b4a.objects.StringUtils();
 //BA.debugLineNum = 5;BA.debugLine="End Sub";
return "";
}
public static String  _retrieveusersettings(anywheresoftware.b4a.BA _ba) throws Exception{
anywheresoftware.b4a.objects.collections.Map _retrievedata = null;
 //BA.debugLineNum = 83;BA.debugLine="Public Sub RetrieveUserSettings";
 //BA.debugLineNum = 84;BA.debugLine="KVS.Initialize(File.DirInternal, \"mrandroid.dat\")";
_kvs._initialize /*String*/ ((_ba.processBA == null ? _ba : _ba.processBA),anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"mrandroid.dat");
 //BA.debugLineNum = 86;BA.debugLine="Dim RetrieveData As Map";
_retrievedata = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 87;BA.debugLine="RetrieveData.Initialize";
_retrievedata.Initialize();
 //BA.debugLineNum = 89;BA.debugLine="RetrieveData = KVS.Get(\"user_data\")";
_retrievedata = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(_kvs._get /*Object*/ ("user_data")));
 //BA.debugLineNum = 92;BA.debugLine="GlobalVar.ServerAddress = RetrieveData.Get(\"Serve";
mostCurrent._globalvar._serveraddress /*String*/  = BA.ObjectToString(_retrievedata.Get((Object)("ServerAddress")));
 //BA.debugLineNum = 95;BA.debugLine="GlobalVar.UserID = RetrieveData.Get(\"UserID\")";
mostCurrent._globalvar._userid /*int*/  = (int)(BA.ObjectToNumber(_retrievedata.Get((Object)("UserID"))));
 //BA.debugLineNum = 96;BA.debugLine="GlobalVar.EmpName = RetrieveData.Get(\"EmpName\")";
mostCurrent._globalvar._empname /*String*/  = BA.ObjectToString(_retrievedata.Get((Object)("EmpName")));
 //BA.debugLineNum = 97;BA.debugLine="GlobalVar.UserName = RetrieveData.Get(\"UserName\")";
mostCurrent._globalvar._username /*String*/  = BA.ObjectToString(_retrievedata.Get((Object)("UserName")));
 //BA.debugLineNum = 98;BA.debugLine="GlobalVar.UserPW = RetrieveData.Get(\"UserPW\")";
mostCurrent._globalvar._userpw /*String*/  = BA.ObjectToString(_retrievedata.Get((Object)("UserPW")));
 //BA.debugLineNum = 110;BA.debugLine="GlobalVar.BranchID = RetrieveData.Get(\"BranchID\")";
mostCurrent._globalvar._branchid /*int*/  = (int)(BA.ObjectToNumber(_retrievedata.Get((Object)("BranchID"))));
 //BA.debugLineNum = 132;BA.debugLine="Log($\"Server IP: \"$ & GlobalVar.ServerAddress)";
anywheresoftware.b4a.keywords.Common.LogImpl("115269937",("Server IP: ")+mostCurrent._globalvar._serveraddress /*String*/ ,0);
 //BA.debugLineNum = 135;BA.debugLine="Log($\"User ID: \"$ & GlobalVar.UserID)";
anywheresoftware.b4a.keywords.Common.LogImpl("115269940",("User ID: ")+BA.NumberToString(mostCurrent._globalvar._userid /*int*/ ),0);
 //BA.debugLineNum = 136;BA.debugLine="Log($\"Employee Name: \"$ & GlobalVar.EmpName)";
anywheresoftware.b4a.keywords.Common.LogImpl("115269941",("Employee Name: ")+mostCurrent._globalvar._empname /*String*/ ,0);
 //BA.debugLineNum = 137;BA.debugLine="Log($\"User Name: \"$ & GlobalVar.UserName)";
anywheresoftware.b4a.keywords.Common.LogImpl("115269942",("User Name: ")+mostCurrent._globalvar._username /*String*/ ,0);
 //BA.debugLineNum = 138;BA.debugLine="Log($\"User Password: \"$ & GlobalVar.UserPW)";
anywheresoftware.b4a.keywords.Common.LogImpl("115269943",("User Password: ")+mostCurrent._globalvar._userpw /*String*/ ,0);
 //BA.debugLineNum = 141;BA.debugLine="Log($\"Module 1: \"$ & GlobalVar.Mod1)";
anywheresoftware.b4a.keywords.Common.LogImpl("115269946",("Module 1: ")+BA.NumberToString(mostCurrent._globalvar._mod1 /*int*/ ),0);
 //BA.debugLineNum = 142;BA.debugLine="Log($\"Module 2: \"$ & GlobalVar.Mod2)";
anywheresoftware.b4a.keywords.Common.LogImpl("115269947",("Module 2: ")+BA.NumberToString(mostCurrent._globalvar._mod2 /*int*/ ),0);
 //BA.debugLineNum = 143;BA.debugLine="Log($\"Module 3: \"$ & GlobalVar.Mod3)";
anywheresoftware.b4a.keywords.Common.LogImpl("115269948",("Module 3: ")+BA.NumberToString(mostCurrent._globalvar._mod3 /*int*/ ),0);
 //BA.debugLineNum = 144;BA.debugLine="Log($\"Module 4: \"$ & GlobalVar.Mod4)";
anywheresoftware.b4a.keywords.Common.LogImpl("115269949",("Module 4: ")+BA.NumberToString(mostCurrent._globalvar._mod4 /*int*/ ),0);
 //BA.debugLineNum = 145;BA.debugLine="Log($\"Module 5: \"$ & GlobalVar.Mod5)";
anywheresoftware.b4a.keywords.Common.LogImpl("115269950",("Module 5: ")+BA.NumberToString(mostCurrent._globalvar._mod5 /*int*/ ),0);
 //BA.debugLineNum = 146;BA.debugLine="Log($\"Module 6: \"$ & GlobalVar.Mod6)";
anywheresoftware.b4a.keywords.Common.LogImpl("115269951",("Module 6: ")+BA.NumberToString(mostCurrent._globalvar._mod6 /*int*/ ),0);
 //BA.debugLineNum = 149;BA.debugLine="Log($\"Company ID: \"$ & GlobalVar.CompanyID)";
anywheresoftware.b4a.keywords.Common.LogImpl("115269954",("Company ID: ")+BA.NumberToString(mostCurrent._globalvar._companyid /*int*/ ),0);
 //BA.debugLineNum = 150;BA.debugLine="Log($\"Branch ID: \"$ & GlobalVar.BranchID)";
anywheresoftware.b4a.keywords.Common.LogImpl("115269955",("Branch ID: ")+BA.NumberToString(mostCurrent._globalvar._branchid /*int*/ ),0);
 //BA.debugLineNum = 151;BA.debugLine="Log($\"Branch Code: \"$ & GlobalVar.BranchCode)";
anywheresoftware.b4a.keywords.Common.LogImpl("115269956",("Branch Code: ")+mostCurrent._globalvar._branchcode /*String*/ ,0);
 //BA.debugLineNum = 152;BA.debugLine="Log($\"Branch Name: \"$ & GlobalVar.BranchName)";
anywheresoftware.b4a.keywords.Common.LogImpl("115269957",("Branch Name: ")+mostCurrent._globalvar._branchname /*String*/ ,0);
 //BA.debugLineNum = 153;BA.debugLine="Log($\"Branch Address: \"$ & GlobalVar.BranchAddres";
anywheresoftware.b4a.keywords.Common.LogImpl("115269958",("Branch Address: ")+mostCurrent._globalvar._branchaddress /*String*/ ,0);
 //BA.debugLineNum = 154;BA.debugLine="Log($\"Contact No.: \"$ & GlobalVar.BranchContactNo";
anywheresoftware.b4a.keywords.Common.LogImpl("115269959",("Contact No.: ")+mostCurrent._globalvar._branchcontactno /*String*/ ,0);
 //BA.debugLineNum = 155;BA.debugLine="Log($\"Tin No.: \"$ & GlobalVar.BranchTINumber)";
anywheresoftware.b4a.keywords.Common.LogImpl("115269960",("Tin No.: ")+mostCurrent._globalvar._branchtinumber /*String*/ ,0);
 //BA.debugLineNum = 156;BA.debugLine="Log($\"Logo: \"$ & GlobalVar.BranchLogo)";
anywheresoftware.b4a.keywords.Common.LogImpl("115269961",("Logo: ")+mostCurrent._globalvar._branchlogo /*String*/ ,0);
 //BA.debugLineNum = 157;BA.debugLine="Log($\"Meter Charges: \"$ & GlobalVar.WithMeterChar";
anywheresoftware.b4a.keywords.Common.LogImpl("115269962",("Meter Charges: ")+BA.NumberToString(mostCurrent._globalvar._withmetercharges /*int*/ ),0);
 //BA.debugLineNum = 158;BA.debugLine="Log($\"Franchised Tax: \"$ & GlobalVar.WithFranchis";
anywheresoftware.b4a.keywords.Common.LogImpl("115269963",("Franchised Tax: ")+BA.NumberToString(mostCurrent._globalvar._withfranchisedtax /*int*/ ),0);
 //BA.debugLineNum = 159;BA.debugLine="Log($\"Septage Fee: \"$ & GlobalVar.WithSeptageFee)";
anywheresoftware.b4a.keywords.Common.LogImpl("115269964",("Septage Fee: ")+BA.NumberToString(mostCurrent._globalvar._withseptagefee /*int*/ ),0);
 //BA.debugLineNum = 160;BA.debugLine="Log($\"Disconnection Date: \"$ & GlobalVar.WithDisc";
anywheresoftware.b4a.keywords.Common.LogImpl("115269965",("Disconnection Date: ")+BA.NumberToString(mostCurrent._globalvar._withdiscondate /*int*/ ),0);
 //BA.debugLineNum = 161;BA.debugLine="Log($\"Septage Logo: \"$ & GlobalVar.SeptageLogo)";
anywheresoftware.b4a.keywords.Common.LogImpl("115269966",("Septage Logo: ")+mostCurrent._globalvar._septagelogo /*String*/ ,0);
 //BA.debugLineNum = 162;BA.debugLine="Log($\"Septage Provider: \"$ & GlobalVar.SeptagePro";
anywheresoftware.b4a.keywords.Common.LogImpl("115269967",("Septage Provider: ")+BA.NumberToString(mostCurrent._globalvar._septageprov /*int*/ ),0);
 //BA.debugLineNum = 165;BA.debugLine="Log($\"Bill Year: \"$ & GlobalVar.BillYear)";
anywheresoftware.b4a.keywords.Common.LogImpl("115269970",("Bill Year: ")+BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ ),0);
 //BA.debugLineNum = 166;BA.debugLine="Log($\"Bill Month: \"$ & GlobalVar.BillMonth)";
anywheresoftware.b4a.keywords.Common.LogImpl("115269971",("Bill Month: ")+BA.NumberToString(mostCurrent._globalvar._billmonth /*int*/ ),0);
 //BA.debugLineNum = 167;BA.debugLine="Log($\"Billing Period: \"$ & GlobalVar.BillPeriod)";
anywheresoftware.b4a.keywords.Common.LogImpl("115269972",("Billing Period: ")+mostCurrent._globalvar._billperiod /*String*/ ,0);
 //BA.debugLineNum = 168;BA.debugLine="Log($\"Bill Month Name: \"$ & GlobalVar.BillMonthNa";
anywheresoftware.b4a.keywords.Common.LogImpl("115269973",("Bill Month Name: ")+mostCurrent._globalvar._billmonthname /*String*/ ,0);
 //BA.debugLineNum = 170;BA.debugLine="End Sub";
return "";
}
public static String  _saveusersettings(anywheresoftware.b4a.BA _ba) throws Exception{
anywheresoftware.b4a.objects.collections.Map _userdata = null;
 //BA.debugLineNum = 35;BA.debugLine="Public Sub SaveUserSettings";
 //BA.debugLineNum = 36;BA.debugLine="KVS.Initialize(File.DirInternal, \"mrandroid.dat\")";
_kvs._initialize /*String*/ ((_ba.processBA == null ? _ba : _ba.processBA),anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"mrandroid.dat");
 //BA.debugLineNum = 38;BA.debugLine="Dim UserData As Map";
_userdata = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 40;BA.debugLine="UserData.Initialize";
_userdata.Initialize();
 //BA.debugLineNum = 42;BA.debugLine="UserData.Put(\"ServerAddress\", GlobalVar.ServerAdd";
_userdata.Put((Object)("ServerAddress"),(Object)(mostCurrent._globalvar._serveraddress /*String*/ ));
 //BA.debugLineNum = 45;BA.debugLine="UserData.Put(\"UserID\", GlobalVar.UserID)";
_userdata.Put((Object)("UserID"),(Object)(mostCurrent._globalvar._userid /*int*/ ));
 //BA.debugLineNum = 46;BA.debugLine="UserData.Put(\"EmpName\", GlobalVar.EmpName)";
_userdata.Put((Object)("EmpName"),(Object)(mostCurrent._globalvar._empname /*String*/ ));
 //BA.debugLineNum = 47;BA.debugLine="UserData.Put(\"UserName\", GlobalVar.UserName)";
_userdata.Put((Object)("UserName"),(Object)(mostCurrent._globalvar._username /*String*/ ));
 //BA.debugLineNum = 48;BA.debugLine="UserData.Put(\"UserPW\", GlobalVar.UserPW)";
_userdata.Put((Object)("UserPW"),(Object)(mostCurrent._globalvar._userpw /*String*/ ));
 //BA.debugLineNum = 60;BA.debugLine="UserData.Put(\"BranchID\", GlobalVar.BranchID)";
_userdata.Put((Object)("BranchID"),(Object)(mostCurrent._globalvar._branchid /*int*/ ));
 //BA.debugLineNum = 80;BA.debugLine="KVS.Put(\"user_data\",UserData)";
_kvs._put /*String*/ ("user_data",(Object)(_userdata.getObject()));
 //BA.debugLineNum = 81;BA.debugLine="End Sub";
return "";
}
public static String  _setsnackbarbackground(anywheresoftware.b4a.BA _ba,de.amberhome.objects.SnackbarWrapper _psnack,int _pcolor) throws Exception{
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
 //BA.debugLineNum = 244;BA.debugLine="Public Sub SetSnackBarBackground(pSnack As DSSnack";
 //BA.debugLineNum = 245;BA.debugLine="Dim v As View";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
 //BA.debugLineNum = 246;BA.debugLine="v = pSnack.View";
_v = (anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(_psnack.getView()));
 //BA.debugLineNum = 247;BA.debugLine="v.Color = pColor";
_v.setColor(_pcolor);
 //BA.debugLineNum = 248;BA.debugLine="End Sub";
return "";
}
public static String  _setsnackbartextcolor(anywheresoftware.b4a.BA _ba,de.amberhome.objects.SnackbarWrapper _psnack,int _pcolor,String _sactionbar) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _p = null;
anywheresoftware.b4a.objects.collections.List _labellist = null;
anywheresoftware.b4a.objects.ConcreteViewWrapper _v = null;
anywheresoftware.b4a.objects.collections.List _labelviews = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
 //BA.debugLineNum = 197;BA.debugLine="Public Sub SetSnackBarTextColor(pSnack As DSSnackb";
 //BA.debugLineNum = 198;BA.debugLine="Dim p As Panel";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 199;BA.debugLine="Dim labelList As List";
_labellist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 200;BA.debugLine="labelList.Initialize";
_labellist.Initialize();
 //BA.debugLineNum = 201;BA.debugLine="p = pSnack.View";
_p = (anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(_psnack.getView()));
 //BA.debugLineNum = 203;BA.debugLine="For Each v As View In p.GetAllViewsRecursive";
_v = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
{
final anywheresoftware.b4a.BA.IterableList group5 = _p.GetAllViewsRecursive();
final int groupLen5 = group5.getSize()
;int index5 = 0;
;
for (; index5 < groupLen5;index5++){
_v = (anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(group5.Get(index5)));
 //BA.debugLineNum = 204;BA.debugLine="If v Is Label Then";
if (_v.getObjectOrNull() instanceof android.widget.TextView) { 
 //BA.debugLineNum = 205;BA.debugLine="labelList.Add(v)";
_labellist.Add((Object)(_v.getObject()));
 };
 }
};
 //BA.debugLineNum = 210;BA.debugLine="Dim labelViews As List";
_labelviews = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 211;BA.debugLine="labelViews = labelList";
_labelviews = _labellist;
 //BA.debugLineNum = 215;BA.debugLine="LogColor(labelViews.Size,Colors.Yellow)";
anywheresoftware.b4a.keywords.Common.LogImpl("115466514",BA.NumberToString(_labelviews.getSize()),anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 //BA.debugLineNum = 217;BA.debugLine="For Each lbl As Label In labelViews";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
{
final anywheresoftware.b4a.BA.IterableList group13 = _labelviews;
final int groupLen13 = group13.getSize()
;int index13 = 0;
;
for (; index13 < groupLen13;index13++){
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(group13.Get(index13)));
 //BA.debugLineNum = 218;BA.debugLine="If sActionBar = \"\" Then";
if ((_sactionbar).equals("")) { 
 //BA.debugLineNum = 219;BA.debugLine="Log(lbl.Tag)";
anywheresoftware.b4a.keywords.Common.LogImpl("115466518",BA.ObjectToString(_lbl.getTag()),0);
 //BA.debugLineNum = 220;BA.debugLine="LogColor(lbl.Text, Colors.Cyan)";
anywheresoftware.b4a.keywords.Common.LogImpl("115466519",_lbl.getText(),anywheresoftware.b4a.keywords.Common.Colors.Cyan);
 //BA.debugLineNum = 221;BA.debugLine="lbl.TextColor = pColor";
_lbl.setTextColor(_pcolor);
 //BA.debugLineNum = 222;BA.debugLine="lbl.TextSize = 18";
_lbl.setTextSize((float) (18));
 }else {
 //BA.debugLineNum = 225;BA.debugLine="Log(lbl.Tag)";
anywheresoftware.b4a.keywords.Common.LogImpl("115466524",BA.ObjectToString(_lbl.getTag()),0);
 //BA.debugLineNum = 226;BA.debugLine="LogColor(lbl.Text, Colors.Cyan)";
anywheresoftware.b4a.keywords.Common.LogImpl("115466525",_lbl.getText(),anywheresoftware.b4a.keywords.Common.Colors.Cyan);
 //BA.debugLineNum = 228;BA.debugLine="If lbl.Text = sActionBar Then";
if ((_lbl.getText()).equals(_sactionbar)) { 
 //BA.debugLineNum = 229;BA.debugLine="Log(\"Action Button\")";
anywheresoftware.b4a.keywords.Common.LogImpl("115466528","Action Button",0);
 //BA.debugLineNum = 230;BA.debugLine="lbl.TextColor = Colors.Gray";
_lbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 //BA.debugLineNum = 231;BA.debugLine="lbl.Typeface = Typeface.DEFAULT_BOLD";
_lbl.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD);
 //BA.debugLineNum = 232;BA.debugLine="lbl.TextSize = 20";
_lbl.setTextSize((float) (20));
 }else {
 //BA.debugLineNum = 234;BA.debugLine="lbl.TextColor = pColor";
_lbl.setTextColor(_pcolor);
 //BA.debugLineNum = 235;BA.debugLine="lbl.TextSize = 18";
_lbl.setTextSize((float) (18));
 };
 };
 }
};
 //BA.debugLineNum = 242;BA.debugLine="End Sub";
return "";
}
}
