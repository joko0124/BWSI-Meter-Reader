package com.bwsi.MeterReader;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class globalvar {
private static globalvar mostCurrent = new globalvar();
public static Object getObject() {
    throw new RuntimeException("Code module does not support this method.");
}
 public anywheresoftware.b4a.keywords.Common __c = null;
public static String _serveraddress = "";
public static String _controllerprefix = "";
public static int _companyid = 0;
public static String _companycode = "";
public static String _companyname = "";
public static int _branchid = 0;
public static String _branchname = "";
public static String _branchcode = "";
public static String _branchaddress = "";
public static String _branchcontactno = "";
public static String _branchtinumber = "";
public static String _branchlogo = "";
public static int _withmetercharges = 0;
public static int _withfranchisedtax = 0;
public static int _withseptagefee = 0;
public static int _withdiscondate = 0;
public static int _userid = 0;
public static String _empname = "";
public static String _username = "";
public static String _userpw = "";
public static int _mod1 = 0;
public static int _mod2 = 0;
public static int _mod3 = 0;
public static int _mod4 = 0;
public static int _mod5 = 0;
public static int _mod6 = 0;
public static boolean _hasdata = false;
public static double _billyear = 0;
public static int _billmonth = 0;
public static String _billmonthname = "";
public static String _billperiod = "";
public static int _bookid = 0;
public static double _bookpca = 0;
public static int _septagerateid = 0;
public static int _septagemincum = 0;
public static int _septagemaxcum = 0;
public static String _septageratetype = "";
public static double _septageratepercum = 0;
public static String _questionicon = "";
public static String _infoicon = "";
public static String _warningicon = "";
public static String _settingsicon = "";
public static double _pricolor = 0;
public static anywheresoftware.b4a.objects.CSBuilder _cstitle = null;
public static anywheresoftware.b4a.objects.CSBuilder _cssubtitle = null;
public static adr.stringfunctions.stringfunctions _sf = null;
public static String _adminpassword = "";
public static double _billid = 0;
public static String _strbooksequence = "";
public static int _isaverage = 0;
public static boolean _blnaverage = false;
public static int _ireportlayout = 0;
public static String _reportlayout = "";
public static int _ireaderid = 0;
public static String _camacctno = "";
public static String _cammeterno = "";
public static int _septageprov = 0;
public static String _septagelogo = "";
public static anywheresoftware.b4a.keywords.constants.TypefaceWrapper _font = null;
public static anywheresoftware.b4a.keywords.constants.TypefaceWrapper _fontbold = null;
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
public static class _thecompany{
public boolean IsInitialized;
public int ID;
public String Name;
public void Initialize() {
IsInitialized = true;
ID = 0;
Name = "";
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static class _thebranch{
public boolean IsInitialized;
public int ID;
public String Name;
public String Code;
public String Address;
public String ContactNo;
public String TIN;
public String Logo;
public void Initialize() {
IsInitialized = true;
ID = 0;
Name = "";
Code = "";
Address = "";
ContactNo = "";
TIN = "";
Logo = "";
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static class _theuser{
public boolean IsInitialized;
public int ID;
public int BranchID;
public String EmployeeName;
public String UserName;
public String Password;
public int Mod1;
public int Mod2;
public int Mod3;
public int Mod4;
public int Mod5;
public int Mod6;
public void Initialize() {
IsInitialized = true;
ID = 0;
BranchID = 0;
EmployeeName = "";
UserName = "";
Password = "";
Mod1 = 0;
Mod2 = 0;
Mod3 = 0;
Mod4 = 0;
Mod5 = 0;
Mod6 = 0;
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static class _thebillperiod{
public boolean IsInitialized;
public double Year;
public int Month;
public String MonthName;
public String Period;
public void Initialize() {
IsInitialized = true;
Year = 0;
Month = 0;
MonthName = "";
Period = "";
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 1;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 5;BA.debugLine="Public ServerAddress As String";
_serveraddress = "";
 //BA.debugLineNum = 6;BA.debugLine="Public ControllerPrefix As String = \"/mreading/\"";
_controllerprefix = "/mreading/";
 //BA.debugLineNum = 8;BA.debugLine="Type theCompany (ID As Int, Name As String)";
;
 //BA.debugLineNum = 9;BA.debugLine="Public CompanyID As Int";
_companyid = 0;
 //BA.debugLineNum = 10;BA.debugLine="Public CompanyCode As String";
_companycode = "";
 //BA.debugLineNum = 11;BA.debugLine="Public CompanyName As String";
_companyname = "";
 //BA.debugLineNum = 13;BA.debugLine="Type theBranch (ID As Int, Name As String, Code A";
;
 //BA.debugLineNum = 14;BA.debugLine="Public BranchID As Int";
_branchid = 0;
 //BA.debugLineNum = 15;BA.debugLine="Public BranchName As String";
_branchname = "";
 //BA.debugLineNum = 16;BA.debugLine="Public BranchCode As String";
_branchcode = "";
 //BA.debugLineNum = 17;BA.debugLine="Public BranchAddress As String";
_branchaddress = "";
 //BA.debugLineNum = 18;BA.debugLine="Public BranchContactNo As String";
_branchcontactno = "";
 //BA.debugLineNum = 19;BA.debugLine="Public BranchTINumber As String";
_branchtinumber = "";
 //BA.debugLineNum = 20;BA.debugLine="Public BranchLogo As String";
_branchlogo = "";
 //BA.debugLineNum = 21;BA.debugLine="Public WithMeterCharges As Int";
_withmetercharges = 0;
 //BA.debugLineNum = 22;BA.debugLine="Public WithFranchisedTax As Int";
_withfranchisedtax = 0;
 //BA.debugLineNum = 23;BA.debugLine="Public WithSeptageFee As Int";
_withseptagefee = 0;
 //BA.debugLineNum = 24;BA.debugLine="Public WithDisconDate As Int";
_withdiscondate = 0;
 //BA.debugLineNum = 26;BA.debugLine="Type theUser (ID As Int, BranchID As Int, Employe";
;
 //BA.debugLineNum = 27;BA.debugLine="Public UserID As Int";
_userid = 0;
 //BA.debugLineNum = 28;BA.debugLine="Public EmpName As String";
_empname = "";
 //BA.debugLineNum = 29;BA.debugLine="Public UserName As String";
_username = "";
 //BA.debugLineNum = 30;BA.debugLine="Public UserPW As String";
_userpw = "";
 //BA.debugLineNum = 31;BA.debugLine="Public Mod1, Mod2, Mod3, Mod4, Mod5, Mod6 As Int";
_mod1 = 0;
_mod2 = 0;
_mod3 = 0;
_mod4 = 0;
_mod5 = 0;
_mod6 = 0;
 //BA.debugLineNum = 32;BA.debugLine="Public HasData As Boolean";
_hasdata = false;
 //BA.debugLineNum = 34;BA.debugLine="Type theBillPeriod (Year As Double, Month As Int,";
;
 //BA.debugLineNum = 35;BA.debugLine="Public BillYear As Double";
_billyear = 0;
 //BA.debugLineNum = 36;BA.debugLine="Public BillMonth As Int";
_billmonth = 0;
 //BA.debugLineNum = 37;BA.debugLine="Public BillMonthName As String";
_billmonthname = "";
 //BA.debugLineNum = 38;BA.debugLine="Public BillPeriod As String";
_billperiod = "";
 //BA.debugLineNum = 40;BA.debugLine="Public BookID As Int";
_bookid = 0;
 //BA.debugLineNum = 41;BA.debugLine="Public BookPCA As Double";
_bookpca = 0;
 //BA.debugLineNum = 43;BA.debugLine="Public SeptageRateID, SeptageMinCuM, SeptageMaxCu";
_septagerateid = 0;
_septagemincum = 0;
_septagemaxcum = 0;
 //BA.debugLineNum = 44;BA.debugLine="Public SeptageRateType As String";
_septageratetype = "";
 //BA.debugLineNum = 45;BA.debugLine="Public SeptageRatePerCuM As Double";
_septageratepercum = 0;
 //BA.debugLineNum = 47;BA.debugLine="Public QuestionIcon = $\"ic_help_outline_black_36d";
_questionicon = ("ic_help_outline_black_36dp");
 //BA.debugLineNum = 48;BA.debugLine="Public InfoIcon = $\"ic_info_black_36dp\"$ As Strin";
_infoicon = ("ic_info_black_36dp");
 //BA.debugLineNum = 49;BA.debugLine="Public WarningIcon = $\"ic_warning_black_36dp2\"$ A";
_warningicon = ("ic_warning_black_36dp2");
 //BA.debugLineNum = 50;BA.debugLine="Public SettingsIcon = $\"ic_settings_black_36dp\"$";
_settingsicon = ("ic_settings_black_36dp");
 //BA.debugLineNum = 51;BA.debugLine="Public PriColor = 0xFF088AEB As Double";
_pricolor = 0xff088aeb;
 //BA.debugLineNum = 53;BA.debugLine="Public CSTitle As CSBuilder";
_cstitle = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 54;BA.debugLine="Public CSSubTitle As CSBuilder";
_cssubtitle = new anywheresoftware.b4a.objects.CSBuilder();
 //BA.debugLineNum = 56;BA.debugLine="Public SF As StringFunctions";
_sf = new adr.stringfunctions.stringfunctions();
 //BA.debugLineNum = 57;BA.debugLine="Public AdminPassword As String = \"MRAndroid\"";
_adminpassword = "MRAndroid";
 //BA.debugLineNum = 59;BA.debugLine="Public BillID As Double";
_billid = 0;
 //BA.debugLineNum = 60;BA.debugLine="Public strBookSequence As String";
_strbooksequence = "";
 //BA.debugLineNum = 61;BA.debugLine="Public isAverage As Int";
_isaverage = 0;
 //BA.debugLineNum = 62;BA.debugLine="Public blnAverage As Boolean";
_blnaverage = false;
 //BA.debugLineNum = 63;BA.debugLine="Public iReportLayout As Int";
_ireportlayout = 0;
 //BA.debugLineNum = 64;BA.debugLine="Public ReportLayout As String";
_reportlayout = "";
 //BA.debugLineNum = 66;BA.debugLine="Public iReaderID As Int";
_ireaderid = 0;
 //BA.debugLineNum = 67;BA.debugLine="Public CamAcctNo As String";
_camacctno = "";
 //BA.debugLineNum = 68;BA.debugLine="Public CamMeterNo As String";
_cammeterno = "";
 //BA.debugLineNum = 70;BA.debugLine="Public SeptageProv As Int";
_septageprov = 0;
 //BA.debugLineNum = 71;BA.debugLine="Public SeptageLogo As String";
_septagelogo = "";
 //BA.debugLineNum = 73;BA.debugLine="Public Font As Typeface = Typeface.LoadFromAssets";
_font = new anywheresoftware.b4a.keywords.constants.TypefaceWrapper();
_font = (anywheresoftware.b4a.keywords.constants.TypefaceWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.constants.TypefaceWrapper(), (android.graphics.Typeface)(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("myfont.ttf")));
 //BA.debugLineNum = 74;BA.debugLine="Public FontBold As Typeface = Typeface.LoadFromAs";
_fontbold = new anywheresoftware.b4a.keywords.constants.TypefaceWrapper();
_fontbold = (anywheresoftware.b4a.keywords.constants.TypefaceWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.constants.TypefaceWrapper(), (android.graphics.Typeface)(anywheresoftware.b4a.keywords.Common.Typeface.LoadFromAssets("myfont_bold.ttf")));
 //BA.debugLineNum = 76;BA.debugLine="End Sub";
return "";
}
}
