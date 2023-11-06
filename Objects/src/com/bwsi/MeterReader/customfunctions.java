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
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public b4a.example.dateutils _dateutils = null;
public com.bwsi.MeterReader.main _main = null;
public com.bwsi.MeterReader.datasyncing _datasyncing = null;
public com.bwsi.MeterReader.camera _camera = null;
public com.bwsi.MeterReader.cmrvr _cmrvr = null;
public com.bwsi.MeterReader.customerbill _customerbill = null;
public com.bwsi.MeterReader.customerlist _customerlist = null;
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
public static String  _clearuserdata(anywheresoftware.b4a.BA _ba) throws Exception{
 //BA.debugLineNum = 171;BA.debugLine="Public Sub ClearUserData";
 //BA.debugLineNum = 172;BA.debugLine="KVS.Initialize(File.DirInternal, \"mrandroid.dat\")";
_kvs._initialize /*String*/ ((_ba.processBA == null ? _ba : _ba.processBA),anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"mrandroid.dat");
 //BA.debugLineNum = 173;BA.debugLine="KVS.Remove(\"user_data\")";
_kvs._remove /*String*/ ("user_data");
 //BA.debugLineNum = 174;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 6;BA.debugLine="Public Sub ConfirmYN(sMsg As String, sTitle As Str";
 //BA.debugLineNum = 7;BA.debugLine="Dim blnRetVal As Boolean";
_blnretval = false;
 //BA.debugLineNum = 8;BA.debugLine="Dim bytAns As Byte";
_bytans = (byte)0;
 //BA.debugLineNum = 10;BA.debugLine="Dim csMsg, csTitle, csPositive, csCancel, csNegat";
_csmsg = new anywheresoftware.b4a.objects.CSBuilder();
_cstitle = new anywheresoftware.b4a.objects.CSBuilder();
_cspositive = new anywheresoftware.b4a.objects.CSBuilder();
_cscancel = new anywheresoftware.b4a.objects.CSBuilder();
_csnegative = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 12;BA.debugLine="Dim icon As B4XBitmap";
_icon = new anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper();
 //BA.debugLineNum = 13;BA.debugLine="csTitle.Initialize.Color(Colors.Red).Append(sTitl";
_cstitle.Initialize().Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(_stitle)).PopAll();
 //BA.debugLineNum = 15;BA.debugLine="csMsg.Initialize.Bold.Color(Colors.DarkGray).Appe";
_csmsg.Initialize().Bold().Color(anywheresoftware.b4a.keywords.Common.Colors.DarkGray).Append(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.CRLF+_smsg)).PopAll();
 //BA.debugLineNum = 16;BA.debugLine="csPositive.Initialize.Bold.Size(20).Color(GlobalV";
_cspositive.Initialize().Bold().Size((int) (20)).Color((int) (mostCurrent._globalvar._pricolor /*double*/ )).Append(BA.ObjectToCharSequence(_spositive)).PopAll();
 //BA.debugLineNum = 17;BA.debugLine="csNegative.Initialize.Bold.Size(20).Color(Colors.";
_csnegative.Initialize().Bold().Size((int) (20)).Color(anywheresoftware.b4a.keywords.Common.Colors.Red).Append(BA.ObjectToCharSequence(_spositive)).PopAll();
 //BA.debugLineNum = 18;BA.debugLine="csCancel.Initialize.Bold.Size(20).Color(Colors.Bl";
_cscancel.Initialize().Bold().Size((int) (20)).Color(anywheresoftware.b4a.keywords.Common.Colors.Black).Append(BA.ObjectToCharSequence(_spositive)).PopAll();
 //BA.debugLineNum = 20;BA.debugLine="icon = xui.LoadBitmapResize(File.DirAssets, \"ques";
_icon = _xui.LoadBitmapResize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"question_mark.png",anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (24)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (24)),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 22;BA.debugLine="csMsg.Initialize.Bold.Color(Colors.DarkGray).Appe";
_csmsg.Initialize().Bold().Color(anywheresoftware.b4a.keywords.Common.Colors.DarkGray).Append(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.CRLF+_smsg)).PopAll();
 //BA.debugLineNum = 23;BA.debugLine="csTitle.Initialize.Color(GlobalVar.PriColor).Appe";
_cstitle.Initialize().Color((int) (mostCurrent._globalvar._pricolor /*double*/ )).Append(BA.ObjectToCharSequence(_stitle)).PopAll();
 //BA.debugLineNum = 25;BA.debugLine="bytAns = Msgbox2(csMsg, csTitle, sPositive, sCanc";
_bytans = (byte) (anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence(_csmsg.getObject()),BA.ObjectToCharSequence(_cstitle.getObject()),_spositive,_scancel,_snegative,(android.graphics.Bitmap)(_icon.getObject()),_ba));
 //BA.debugLineNum = 26;BA.debugLine="If bytAns = DialogResponse.Positive Then";
if (_bytans==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 27;BA.debugLine="blnRetVal = True";
_blnretval = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 29;BA.debugLine="blnRetVal = False";
_blnretval = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 31;BA.debugLine="Return blnRetVal";
if (true) return _blnretval;
 //BA.debugLineNum = 32;BA.debugLine="End Sub";
return false;
}
public static boolean  _isthereuserdata(anywheresoftware.b4a.BA _ba) throws Exception{
boolean _bretval = false;
 //BA.debugLineNum = 176;BA.debugLine="Public Sub IsThereUserData As Boolean";
 //BA.debugLineNum = 177;BA.debugLine="Dim bRetVal As Boolean";
_bretval = false;
 //BA.debugLineNum = 179;BA.debugLine="KVS.Initialize(File.DirInternal, \"mrandroid.dat\")";
_kvs._initialize /*String*/ ((_ba.processBA == null ? _ba : _ba.processBA),anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"mrandroid.dat");
 //BA.debugLineNum = 180;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 181;BA.debugLine="Try";
try { //BA.debugLineNum = 182;BA.debugLine="If KVS.ContainsKey(\"user_data\") Then";
if (_kvs._containskey /*boolean*/ ("user_data")) { 
 //BA.debugLineNum = 183;BA.debugLine="bRetVal = True";
_bretval = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 185;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 };
 } 
       catch (Exception e11) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e11); //BA.debugLineNum = 188;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("421889036",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 };
 //BA.debugLineNum = 191;BA.debugLine="Log($\"User Data \"$ & bRetVal)";
anywheresoftware.b4a.keywords.Common.LogImpl("421889039",("User Data ")+BA.ObjectToString(_bretval),0);
 //BA.debugLineNum = 192;BA.debugLine="Return bRetVal";
if (true) return _bretval;
 //BA.debugLineNum = 193;BA.debugLine="End Sub";
return false;
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 1;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 2;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 3;BA.debugLine="Private KVS As KeyValueStore";
_kvs = new com.bwsi.MeterReader.keyvaluestore();
 //BA.debugLineNum = 4;BA.debugLine="End Sub";
return "";
}
public static String  _retrieveusersettings(anywheresoftware.b4a.BA _ba) throws Exception{
anywheresoftware.b4a.objects.collections.Map _retrievedata = null;
 //BA.debugLineNum = 82;BA.debugLine="Public Sub RetrieveUserSettings";
 //BA.debugLineNum = 83;BA.debugLine="KVS.Initialize(File.DirInternal, \"mrandroid.dat\")";
_kvs._initialize /*String*/ ((_ba.processBA == null ? _ba : _ba.processBA),anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"mrandroid.dat");
 //BA.debugLineNum = 85;BA.debugLine="Dim RetrieveData As Map";
_retrievedata = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 86;BA.debugLine="RetrieveData.Initialize";
_retrievedata.Initialize();
 //BA.debugLineNum = 88;BA.debugLine="RetrieveData = KVS.Get(\"user_data\")";
_retrievedata = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(_kvs._get /*Object*/ ("user_data")));
 //BA.debugLineNum = 91;BA.debugLine="GlobalVar.ServerAddress = RetrieveData.Get(\"Serve";
mostCurrent._globalvar._serveraddress /*String*/  = BA.ObjectToString(_retrievedata.Get((Object)("ServerAddress")));
 //BA.debugLineNum = 94;BA.debugLine="GlobalVar.UserID = RetrieveData.Get(\"UserID\")";
mostCurrent._globalvar._userid /*int*/  = (int)(BA.ObjectToNumber(_retrievedata.Get((Object)("UserID"))));
 //BA.debugLineNum = 95;BA.debugLine="GlobalVar.EmpName = RetrieveData.Get(\"EmpName\")";
mostCurrent._globalvar._empname /*String*/  = BA.ObjectToString(_retrievedata.Get((Object)("EmpName")));
 //BA.debugLineNum = 96;BA.debugLine="GlobalVar.UserName = RetrieveData.Get(\"UserName\")";
mostCurrent._globalvar._username /*String*/  = BA.ObjectToString(_retrievedata.Get((Object)("UserName")));
 //BA.debugLineNum = 97;BA.debugLine="GlobalVar.UserPW = RetrieveData.Get(\"UserPW\")";
mostCurrent._globalvar._userpw /*String*/  = BA.ObjectToString(_retrievedata.Get((Object)("UserPW")));
 //BA.debugLineNum = 109;BA.debugLine="GlobalVar.BranchID = RetrieveData.Get(\"BranchID\")";
mostCurrent._globalvar._branchid /*int*/  = (int)(BA.ObjectToNumber(_retrievedata.Get((Object)("BranchID"))));
 //BA.debugLineNum = 131;BA.debugLine="Log($\"Server IP: \"$ & GlobalVar.ServerAddress)";
anywheresoftware.b4a.keywords.Common.LogImpl("421758001",("Server IP: ")+mostCurrent._globalvar._serveraddress /*String*/ ,0);
 //BA.debugLineNum = 134;BA.debugLine="Log($\"User ID: \"$ & GlobalVar.UserID)";
anywheresoftware.b4a.keywords.Common.LogImpl("421758004",("User ID: ")+BA.NumberToString(mostCurrent._globalvar._userid /*int*/ ),0);
 //BA.debugLineNum = 135;BA.debugLine="Log($\"Employee Name: \"$ & GlobalVar.EmpName)";
anywheresoftware.b4a.keywords.Common.LogImpl("421758005",("Employee Name: ")+mostCurrent._globalvar._empname /*String*/ ,0);
 //BA.debugLineNum = 136;BA.debugLine="Log($\"User Name: \"$ & GlobalVar.UserName)";
anywheresoftware.b4a.keywords.Common.LogImpl("421758006",("User Name: ")+mostCurrent._globalvar._username /*String*/ ,0);
 //BA.debugLineNum = 137;BA.debugLine="Log($\"User Password: \"$ & GlobalVar.UserPW)";
anywheresoftware.b4a.keywords.Common.LogImpl("421758007",("User Password: ")+mostCurrent._globalvar._userpw /*String*/ ,0);
 //BA.debugLineNum = 140;BA.debugLine="Log($\"Module 1: \"$ & GlobalVar.Mod1)";
anywheresoftware.b4a.keywords.Common.LogImpl("421758010",("Module 1: ")+BA.NumberToString(mostCurrent._globalvar._mod1 /*int*/ ),0);
 //BA.debugLineNum = 141;BA.debugLine="Log($\"Module 2: \"$ & GlobalVar.Mod2)";
anywheresoftware.b4a.keywords.Common.LogImpl("421758011",("Module 2: ")+BA.NumberToString(mostCurrent._globalvar._mod2 /*int*/ ),0);
 //BA.debugLineNum = 142;BA.debugLine="Log($\"Module 3: \"$ & GlobalVar.Mod3)";
anywheresoftware.b4a.keywords.Common.LogImpl("421758012",("Module 3: ")+BA.NumberToString(mostCurrent._globalvar._mod3 /*int*/ ),0);
 //BA.debugLineNum = 143;BA.debugLine="Log($\"Module 4: \"$ & GlobalVar.Mod4)";
anywheresoftware.b4a.keywords.Common.LogImpl("421758013",("Module 4: ")+BA.NumberToString(mostCurrent._globalvar._mod4 /*int*/ ),0);
 //BA.debugLineNum = 144;BA.debugLine="Log($\"Module 5: \"$ & GlobalVar.Mod5)";
anywheresoftware.b4a.keywords.Common.LogImpl("421758014",("Module 5: ")+BA.NumberToString(mostCurrent._globalvar._mod5 /*int*/ ),0);
 //BA.debugLineNum = 145;BA.debugLine="Log($\"Module 6: \"$ & GlobalVar.Mod6)";
anywheresoftware.b4a.keywords.Common.LogImpl("421758015",("Module 6: ")+BA.NumberToString(mostCurrent._globalvar._mod6 /*int*/ ),0);
 //BA.debugLineNum = 148;BA.debugLine="Log($\"Company ID: \"$ & GlobalVar.CompanyID)";
anywheresoftware.b4a.keywords.Common.LogImpl("421758018",("Company ID: ")+BA.NumberToString(mostCurrent._globalvar._companyid /*int*/ ),0);
 //BA.debugLineNum = 149;BA.debugLine="Log($\"Branch ID: \"$ & GlobalVar.BranchID)";
anywheresoftware.b4a.keywords.Common.LogImpl("421758019",("Branch ID: ")+BA.NumberToString(mostCurrent._globalvar._branchid /*int*/ ),0);
 //BA.debugLineNum = 150;BA.debugLine="Log($\"Branch Code: \"$ & GlobalVar.BranchCode)";
anywheresoftware.b4a.keywords.Common.LogImpl("421758020",("Branch Code: ")+mostCurrent._globalvar._branchcode /*String*/ ,0);
 //BA.debugLineNum = 151;BA.debugLine="Log($\"Branch Name: \"$ & GlobalVar.BranchName)";
anywheresoftware.b4a.keywords.Common.LogImpl("421758021",("Branch Name: ")+mostCurrent._globalvar._branchname /*String*/ ,0);
 //BA.debugLineNum = 152;BA.debugLine="Log($\"Branch Address: \"$ & GlobalVar.BranchAddres";
anywheresoftware.b4a.keywords.Common.LogImpl("421758022",("Branch Address: ")+mostCurrent._globalvar._branchaddress /*String*/ ,0);
 //BA.debugLineNum = 153;BA.debugLine="Log($\"Contact No.: \"$ & GlobalVar.BranchContactNo";
anywheresoftware.b4a.keywords.Common.LogImpl("421758023",("Contact No.: ")+mostCurrent._globalvar._branchcontactno /*String*/ ,0);
 //BA.debugLineNum = 154;BA.debugLine="Log($\"Tin No.: \"$ & GlobalVar.BranchTINumber)";
anywheresoftware.b4a.keywords.Common.LogImpl("421758024",("Tin No.: ")+mostCurrent._globalvar._branchtinumber /*String*/ ,0);
 //BA.debugLineNum = 155;BA.debugLine="Log($\"Logo: \"$ & GlobalVar.BranchLogo)";
anywheresoftware.b4a.keywords.Common.LogImpl("421758025",("Logo: ")+mostCurrent._globalvar._branchlogo /*String*/ ,0);
 //BA.debugLineNum = 156;BA.debugLine="Log($\"Meter Charges: \"$ & GlobalVar.WithMeterChar";
anywheresoftware.b4a.keywords.Common.LogImpl("421758026",("Meter Charges: ")+BA.NumberToString(mostCurrent._globalvar._withmetercharges /*int*/ ),0);
 //BA.debugLineNum = 157;BA.debugLine="Log($\"Franchised Tax: \"$ & GlobalVar.WithFranchis";
anywheresoftware.b4a.keywords.Common.LogImpl("421758027",("Franchised Tax: ")+BA.NumberToString(mostCurrent._globalvar._withfranchisedtax /*int*/ ),0);
 //BA.debugLineNum = 158;BA.debugLine="Log($\"Septage Fee: \"$ & GlobalVar.WithSeptageFee)";
anywheresoftware.b4a.keywords.Common.LogImpl("421758028",("Septage Fee: ")+BA.NumberToString(mostCurrent._globalvar._withseptagefee /*int*/ ),0);
 //BA.debugLineNum = 159;BA.debugLine="Log($\"Disconnection Date: \"$ & GlobalVar.WithDisc";
anywheresoftware.b4a.keywords.Common.LogImpl("421758029",("Disconnection Date: ")+BA.NumberToString(mostCurrent._globalvar._withdiscondate /*int*/ ),0);
 //BA.debugLineNum = 160;BA.debugLine="Log($\"Septage Logo: \"$ & GlobalVar.SeptageLogo)";
anywheresoftware.b4a.keywords.Common.LogImpl("421758030",("Septage Logo: ")+mostCurrent._globalvar._septagelogo /*String*/ ,0);
 //BA.debugLineNum = 161;BA.debugLine="Log($\"Septage Provider: \"$ & GlobalVar.SeptagePro";
anywheresoftware.b4a.keywords.Common.LogImpl("421758031",("Septage Provider: ")+BA.NumberToString(mostCurrent._globalvar._septageprov /*int*/ ),0);
 //BA.debugLineNum = 164;BA.debugLine="Log($\"Bill Year: \"$ & GlobalVar.BillYear)";
anywheresoftware.b4a.keywords.Common.LogImpl("421758034",("Bill Year: ")+BA.NumberToString(mostCurrent._globalvar._billyear /*double*/ ),0);
 //BA.debugLineNum = 165;BA.debugLine="Log($\"Bill Month: \"$ & GlobalVar.BillMonth)";
anywheresoftware.b4a.keywords.Common.LogImpl("421758035",("Bill Month: ")+BA.NumberToString(mostCurrent._globalvar._billmonth /*int*/ ),0);
 //BA.debugLineNum = 166;BA.debugLine="Log($\"Billing Period: \"$ & GlobalVar.BillPeriod)";
anywheresoftware.b4a.keywords.Common.LogImpl("421758036",("Billing Period: ")+mostCurrent._globalvar._billperiod /*String*/ ,0);
 //BA.debugLineNum = 167;BA.debugLine="Log($\"Bill Month Name: \"$ & GlobalVar.BillMonthNa";
anywheresoftware.b4a.keywords.Common.LogImpl("421758037",("Bill Month Name: ")+mostCurrent._globalvar._billmonthname /*String*/ ,0);
 //BA.debugLineNum = 169;BA.debugLine="End Sub";
return "";
}
public static String  _saveusersettings(anywheresoftware.b4a.BA _ba) throws Exception{
anywheresoftware.b4a.objects.collections.Map _userdata = null;
 //BA.debugLineNum = 34;BA.debugLine="Public Sub SaveUserSettings";
 //BA.debugLineNum = 35;BA.debugLine="KVS.Initialize(File.DirInternal, \"mrandroid.dat\")";
_kvs._initialize /*String*/ ((_ba.processBA == null ? _ba : _ba.processBA),anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"mrandroid.dat");
 //BA.debugLineNum = 37;BA.debugLine="Dim UserData As Map";
_userdata = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 39;BA.debugLine="UserData.Initialize";
_userdata.Initialize();
 //BA.debugLineNum = 41;BA.debugLine="UserData.Put(\"ServerAddress\", GlobalVar.ServerAdd";
_userdata.Put((Object)("ServerAddress"),(Object)(mostCurrent._globalvar._serveraddress /*String*/ ));
 //BA.debugLineNum = 44;BA.debugLine="UserData.Put(\"UserID\", GlobalVar.UserID)";
_userdata.Put((Object)("UserID"),(Object)(mostCurrent._globalvar._userid /*int*/ ));
 //BA.debugLineNum = 45;BA.debugLine="UserData.Put(\"EmpName\", GlobalVar.EmpName)";
_userdata.Put((Object)("EmpName"),(Object)(mostCurrent._globalvar._empname /*String*/ ));
 //BA.debugLineNum = 46;BA.debugLine="UserData.Put(\"UserName\", GlobalVar.UserName)";
_userdata.Put((Object)("UserName"),(Object)(mostCurrent._globalvar._username /*String*/ ));
 //BA.debugLineNum = 47;BA.debugLine="UserData.Put(\"UserPW\", GlobalVar.UserPW)";
_userdata.Put((Object)("UserPW"),(Object)(mostCurrent._globalvar._userpw /*String*/ ));
 //BA.debugLineNum = 59;BA.debugLine="UserData.Put(\"BranchID\", GlobalVar.BranchID)";
_userdata.Put((Object)("BranchID"),(Object)(mostCurrent._globalvar._branchid /*int*/ ));
 //BA.debugLineNum = 79;BA.debugLine="KVS.Put(\"user_data\",UserData)";
_kvs._put /*String*/ ("user_data",(Object)(_userdata.getObject()));
 //BA.debugLineNum = 80;BA.debugLine="End Sub";
return "";
}
}
