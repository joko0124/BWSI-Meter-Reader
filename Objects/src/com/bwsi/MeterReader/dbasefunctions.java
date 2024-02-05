package com.bwsi.MeterReader;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class dbasefunctions {
private static dbasefunctions mostCurrent = new dbasefunctions();
public static Object getObject() {
    throw new RuntimeException("Code module does not support this method.");
}
 public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.sql.SQL.CursorWrapper _rstemp = null;
public static anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public b4a.example.dateutils _dateutils = null;
public com.bwsi.MeterReader.main _main = null;
public com.bwsi.MeterReader.datasyncing _datasyncing = null;
public com.bwsi.MeterReader.login _login = null;
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
public static double  _computebasicamt(anywheresoftware.b4a.BA _ba,int _iprescum,int _ibranchid,String _sclass,String _ssubclass) throws Exception{
double _retval = 0;
String _sdate = "";
String _sbilldate = "";
int _i = 0;
String _billtype = "";
double _billamt = 0;
int _rangefrom = 0;
int _rangeto = 0;
int _excesscum = 0;
double _basicamt = 0;
int _prescum = 0;
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsrate = null;
 //BA.debugLineNum = 671;BA.debugLine="Public Sub ComputeBasicAmt(iPresCum As Int, iBranc";
 //BA.debugLineNum = 672;BA.debugLine="Dim RetVal As Double";
_retval = 0;
 //BA.debugLineNum = 673;BA.debugLine="Dim sDate, sBillDate As String";
_sdate = "";
_sbilldate = "";
 //BA.debugLineNum = 674;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 675;BA.debugLine="Dim BillType As String";
_billtype = "";
 //BA.debugLineNum = 676;BA.debugLine="Dim BillAmt As Double";
_billamt = 0;
 //BA.debugLineNum = 677;BA.debugLine="Dim RangeFrom, RangeTo As Int";
_rangefrom = 0;
_rangeto = 0;
 //BA.debugLineNum = 678;BA.debugLine="Dim ExcessCum As Int";
_excesscum = 0;
 //BA.debugLineNum = 679;BA.debugLine="Dim BasicAmt As Double";
_basicamt = 0;
 //BA.debugLineNum = 680;BA.debugLine="Dim PresCum As Int";
_prescum = 0;
 //BA.debugLineNum = 681;BA.debugLine="Dim rsRate As Cursor";
_rsrate = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 686;BA.debugLine="BillType = \"\"";
_billtype = "";
 //BA.debugLineNum = 687;BA.debugLine="BillAmt = 0";
_billamt = 0;
 //BA.debugLineNum = 688;BA.debugLine="PresCum = 0";
_prescum = (int) (0);
 //BA.debugLineNum = 689;BA.debugLine="ExcessCum = 0";
_excesscum = (int) (0);
 //BA.debugLineNum = 690;BA.debugLine="BasicAmt = 0";
_basicamt = 0;
 //BA.debugLineNum = 692;BA.debugLine="Try";
try { //BA.debugLineNum = 694;BA.debugLine="Starter.strCriteria = \"SELECT id FROM rates_head";
mostCurrent._starter._strcriteria /*String*/  = "SELECT id FROM rates_header WHERE branch_id = "+BA.NumberToString(_ibranchid)+" "+"AND class = '"+_sclass+"' "+"AND sub_class = '"+_ssubclass+"' "+"ORDER BY date_from DESC LIMIT 1";
 //BA.debugLineNum = 698;BA.debugLine="Log(Starter.strCriteria)";
anywheresoftware.b4a.keywords.Common.LogImpl("114417947",mostCurrent._starter._strcriteria /*String*/ ,0);
 //BA.debugLineNum = 699;BA.debugLine="RetVal = Starter.DBCon.ExecQuerySingleResult(Sta";
_retval = (double)(Double.parseDouble(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 700;BA.debugLine="Log(RetVal)";
anywheresoftware.b4a.keywords.Common.LogImpl("114417949",BA.NumberToString(_retval),0);
 //BA.debugLineNum = 703;BA.debugLine="Starter.strCriteria = \"SELECT * FROM rates_detai";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM rates_details WHERE header_id = "+BA.NumberToString(_retval)+" "+"ORDER BY rangefrom DESC";
 //BA.debugLineNum = 706;BA.debugLine="rsRate = Starter.DBCon.ExecQuery(Starter.strCrit";
_rsrate = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 707;BA.debugLine="PresCum = iPresCum";
_prescum = _iprescum;
 //BA.debugLineNum = 709;BA.debugLine="If rsRate.RowCount > 0 Then";
if (_rsrate.getRowCount()>0) { 
 //BA.debugLineNum = 710;BA.debugLine="For i = 0 To rsRate.RowCount - 1";
{
final int step25 = 1;
final int limit25 = (int) (_rsrate.getRowCount()-1);
_i = (int) (0) ;
for (;_i <= limit25 ;_i = _i + step25 ) {
 //BA.debugLineNum = 711;BA.debugLine="rsRate.Position	= i";
_rsrate.setPosition(_i);
 //BA.debugLineNum = 712;BA.debugLine="RangeFrom = rsRate.GetString(\"rangefrom\")";
_rangefrom = (int)(Double.parseDouble(_rsrate.GetString("rangefrom")));
 //BA.debugLineNum = 713;BA.debugLine="RangeTo = rsRate.GetString(\"rangeto\")";
_rangeto = (int)(Double.parseDouble(_rsrate.GetString("rangeto")));
 //BA.debugLineNum = 714;BA.debugLine="BillType = rsRate.GetString(\"typecust\")";
_billtype = _rsrate.GetString("typecust");
 //BA.debugLineNum = 715;BA.debugLine="BillAmt = rsRate.GetDouble(\"amount\")";
_billamt = _rsrate.GetDouble("amount");
 //BA.debugLineNum = 717;BA.debugLine="If PresCum >= RangeFrom And PresCum <= RangeTo";
if (_prescum>=_rangefrom && _prescum<=_rangeto) { 
 //BA.debugLineNum = 718;BA.debugLine="If BillType = \"m\" Then";
if ((_billtype).equals("m")) { 
 //BA.debugLineNum = 719;BA.debugLine="BasicAmt = BasicAmt + BillAmt";
_basicamt = _basicamt+_billamt;
 //BA.debugLineNum = 720;BA.debugLine="Exit";
if (true) break;
 };
 //BA.debugLineNum = 723;BA.debugLine="If BillType = \"r\" Or BillType = \"s\" Then";
if ((_billtype).equals("r") || (_billtype).equals("s")) { 
 //BA.debugLineNum = 724;BA.debugLine="ExcessCum = (PresCum - RangeFrom) + 1";
_excesscum = (int) ((_prescum-_rangefrom)+1);
 //BA.debugLineNum = 725;BA.debugLine="BasicAmt = BasicAmt + (ExcessCum * BillAmt)";
_basicamt = _basicamt+(_excesscum*_billamt);
 //BA.debugLineNum = 726;BA.debugLine="PresCum = PresCum - ExcessCum";
_prescum = (int) (_prescum-_excesscum);
 };
 //BA.debugLineNum = 729;BA.debugLine="If PresCum = 0 Then";
if (_prescum==0) { 
 //BA.debugLineNum = 730;BA.debugLine="Exit";
if (true) break;
 };
 }else {
 };
 }
};
 };
 } 
       catch (Exception e49) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e49); //BA.debugLineNum = 738;BA.debugLine="xui.MsgboxAsync(\"Unable to fetch basic amount du";
_xui.MsgboxAsync((_ba.processBA == null ? _ba : _ba.processBA),BA.ObjectToCharSequence("Unable to fetch basic amount due to "+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage()),BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.Application.getLabelName()));
 //BA.debugLineNum = 739;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("114417988",anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage(),0);
 //BA.debugLineNum = 741;BA.debugLine="Return 0";
if (true) return 0;
 };
 //BA.debugLineNum = 744;BA.debugLine="Return BasicAmt";
if (true) return _basicamt;
 //BA.debugLineNum = 745;BA.debugLine="End Sub";
return 0;
}
public static int  _getbillmonth(anywheresoftware.b4a.BA _ba,int _ibranchid) throws Exception{
int _iretval = 0;
 //BA.debugLineNum = 53;BA.debugLine="Public Sub GetBillMonth(iBranchID As Int) As Int";
 //BA.debugLineNum = 54;BA.debugLine="Dim iRetVal As Int";
_iretval = 0;
 //BA.debugLineNum = 55;BA.debugLine="Try";
try { //BA.debugLineNum = 56;BA.debugLine="iRetVal = Starter.DBCon.ExecQuerySingleResult(\"S";
_iretval = (int)(Double.parseDouble(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult("SELECT BillMonth FROM tblSysParam WHERE BranchID = "+BA.NumberToString(_ibranchid))));
 } 
       catch (Exception e5) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e5); //BA.debugLineNum = 58;BA.debugLine="ToastMessageShow($\"Unable to fetch Billing Month";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to fetch Billing Month due to ")+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 59;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("112451846",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 };
 //BA.debugLineNum = 61;BA.debugLine="Return iRetVal";
if (true) return _iretval;
 //BA.debugLineNum = 62;BA.debugLine="End Sub";
return 0;
}
public static String  _getbillperiod(anywheresoftware.b4a.BA _ba,int _ibranchid) throws Exception{
String _sretval = "";
 //BA.debugLineNum = 64;BA.debugLine="Public Sub GetBillPeriod(iBranchID As Int) As Stri";
 //BA.debugLineNum = 65;BA.debugLine="Dim sRetVal As String";
_sretval = "";
 //BA.debugLineNum = 66;BA.debugLine="Try";
try { //BA.debugLineNum = 67;BA.debugLine="sRetVal = Starter.DBCon.ExecQuerySingleResult(\"S";
_sretval = mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult("SELECT BillPeriod FROM tblSysParam WHERE BranchID = "+BA.NumberToString(_ibranchid));
 } 
       catch (Exception e5) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e5); //BA.debugLineNum = 69;BA.debugLine="ToastMessageShow($\"Unable to fetch Billing Perio";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to fetch Billing Period due to ")+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 70;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("112517382",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 };
 //BA.debugLineNum = 72;BA.debugLine="Return sRetVal";
if (true) return _sretval;
 //BA.debugLineNum = 73;BA.debugLine="End Sub";
return "";
}
public static boolean  _getbillsettings(anywheresoftware.b4a.BA _ba,int _ibranchid) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsbillsettings = null;
boolean _blnretval = false;
long _billdate = 0L;
 //BA.debugLineNum = 90;BA.debugLine="Public Sub GetBillSettings(iBranchID As Int) As Bo";
 //BA.debugLineNum = 91;BA.debugLine="Dim rsBillSettings As Cursor";
_rsbillsettings = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 92;BA.debugLine="Dim blnRetval As Boolean";
_blnretval = false;
 //BA.debugLineNum = 93;BA.debugLine="Dim BillDate As Long";
_billdate = 0L;
 //BA.debugLineNum = 94;BA.debugLine="Try";
try { //BA.debugLineNum = 95;BA.debugLine="Starter.strCriteria = \"SELECT * FROM tblSysParam";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblSysParam WHERE BranchID = "+BA.NumberToString(_ibranchid);
 //BA.debugLineNum = 96;BA.debugLine="rsBillSettings = Starter.DBCon.ExecQuery(Starter";
_rsbillsettings = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 97;BA.debugLine="Log (Starter.strCriteria)";
anywheresoftware.b4a.keywords.Common.LogImpl("112648455",mostCurrent._starter._strcriteria /*String*/ ,0);
 //BA.debugLineNum = 99;BA.debugLine="If rsBillSettings.RowCount > 0 Then";
if (_rsbillsettings.getRowCount()>0) { 
 //BA.debugLineNum = 100;BA.debugLine="rsBillSettings.Position = 0";
_rsbillsettings.setPosition((int) (0));
 //BA.debugLineNum = 101;BA.debugLine="GlobalVar.BillYear = rsBillSettings.GetInt(\"Bil";
mostCurrent._globalvar._billyear /*double*/  = _rsbillsettings.GetInt("BillYear");
 //BA.debugLineNum = 102;BA.debugLine="GlobalVar.BillMonth = rsBillSettings.GetInt(\"Bi";
mostCurrent._globalvar._billmonth /*int*/  = _rsbillsettings.GetInt("BillMonth");
 //BA.debugLineNum = 103;BA.debugLine="GlobalVar.BillPeriod =rsBillSettings.GetString(";
mostCurrent._globalvar._billperiod /*String*/  = _rsbillsettings.GetString("BillPeriod");
 //BA.debugLineNum = 104;BA.debugLine="DateTime.DateFormat = \"MM/dd/yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("MM/dd/yyyy");
 //BA.debugLineNum = 105;BA.debugLine="BillDate = DateTime.DateParse(rsBillSettings.Ge";
_billdate = anywheresoftware.b4a.keywords.Common.DateTime.DateParse(BA.NumberToString(_rsbillsettings.GetInt("BillMonth"))+"/01/"+BA.NumberToString(_rsbillsettings.GetInt("BillYear")));
 //BA.debugLineNum = 106;BA.debugLine="Log(BillDate)";
anywheresoftware.b4a.keywords.Common.LogImpl("112648464",BA.NumberToString(_billdate),0);
 //BA.debugLineNum = 107;BA.debugLine="GlobalVar.BillMonthName= DateUtils.GetMonthName";
mostCurrent._globalvar._billmonthname /*String*/  = mostCurrent._dateutils._getmonthname(_ba,_billdate);
 //BA.debugLineNum = 108;BA.debugLine="blnRetval = True";
_blnretval = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 110;BA.debugLine="blnRetval = False";
_blnretval = anywheresoftware.b4a.keywords.Common.False;
 };
 } 
       catch (Exception e22) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e22); //BA.debugLineNum = 113;BA.debugLine="blnRetval = False";
_blnretval = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 114;BA.debugLine="ToastMessageShow($\"Unable to fetch Billing Setti";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to fetch Billing Settings due to ")+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 115;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("112648473",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 };
 //BA.debugLineNum = 117;BA.debugLine="rsBillSettings.Close";
_rsbillsettings.Close();
 //BA.debugLineNum = 118;BA.debugLine="Return blnRetval";
if (true) return _blnretval;
 //BA.debugLineNum = 119;BA.debugLine="End Sub";
return false;
}
public static double  _getbillyear(anywheresoftware.b4a.BA _ba,int _ibranchid) throws Exception{
double _dretval = 0;
 //BA.debugLineNum = 42;BA.debugLine="Public Sub GetBillYear(iBranchID As Int) As  Doubl";
 //BA.debugLineNum = 43;BA.debugLine="Dim dRetVal As Double";
_dretval = 0;
 //BA.debugLineNum = 44;BA.debugLine="Try";
try { //BA.debugLineNum = 45;BA.debugLine="dRetVal = Starter.DBCon.ExecQuerySingleResult(\"S";
_dretval = (double)(Double.parseDouble(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult("SELECT BillYear FROM tblSysParam WHERE BranchID = "+BA.NumberToString(_ibranchid))));
 } 
       catch (Exception e5) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e5); //BA.debugLineNum = 47;BA.debugLine="ToastMessageShow($\"Unable to fetch Billing Year";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to fetch Billing Year due to ")+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 48;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("112386310",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 };
 //BA.debugLineNum = 50;BA.debugLine="Return dRetVal";
if (true) return _dretval;
 //BA.debugLineNum = 51;BA.debugLine="End Sub";
return 0;
}
public static String  _getbookid(anywheresoftware.b4a.BA _ba,String _sbookcode) throws Exception{
 //BA.debugLineNum = 382;BA.debugLine="Public Sub GetBookID (sBookCode As String)";
 //BA.debugLineNum = 391;BA.debugLine="End Sub";
return "";
}
public static int  _getbranchid(anywheresoftware.b4a.BA _ba) throws Exception{
int _iretval = 0;
 //BA.debugLineNum = 19;BA.debugLine="Public Sub GetBranchID() As Int";
 //BA.debugLineNum = 20;BA.debugLine="Dim iRetVal As Int";
_iretval = 0;
 //BA.debugLineNum = 21;BA.debugLine="iRetVal = 0";
_iretval = (int) (0);
 //BA.debugLineNum = 22;BA.debugLine="Try";
try { //BA.debugLineNum = 23;BA.debugLine="iRetVal = Starter.DBCon.ExecQuerySingleResult(\"S";
_iretval = (int)(Double.parseDouble(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult("SELECT BranchID FROM tblSysParam")));
 } 
       catch (Exception e6) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e6); //BA.debugLineNum = 25;BA.debugLine="ToastMessageShow($\"Unable to fetch Branch ID due";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to fetch Branch ID due to ")+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 26;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("112255239",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 };
 //BA.debugLineNum = 28;BA.debugLine="Return iRetVal";
if (true) return _iretval;
 //BA.debugLineNum = 29;BA.debugLine="End Sub";
return 0;
}
public static String  _getbranchinfo(anywheresoftware.b4a.BA _ba,int _ibranchid) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsbranch = null;
 //BA.debugLineNum = 294;BA.debugLine="Public Sub GetBranchInfo(iBranchID As Int)";
 //BA.debugLineNum = 295;BA.debugLine="Dim rsBranch As Cursor";
_rsbranch = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 296;BA.debugLine="Try";
try { //BA.debugLineNum = 297;BA.debugLine="Starter.strCriteria = \"SELECT * FROM tblBranches";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblBranches "+"WHERE BranchID = "+BA.NumberToString(_ibranchid);
 //BA.debugLineNum = 299;BA.debugLine="Log(Starter.strCriteria)";
anywheresoftware.b4a.keywords.Common.LogImpl("113172741",mostCurrent._starter._strcriteria /*String*/ ,0);
 //BA.debugLineNum = 300;BA.debugLine="rsBranch = Starter.DBCon.ExecQuery(Starter.strCr";
_rsbranch = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 302;BA.debugLine="If rsBranch.RowCount > 0 Then";
if (_rsbranch.getRowCount()>0) { 
 //BA.debugLineNum = 303;BA.debugLine="rsBranch.Position = 0";
_rsbranch.setPosition((int) (0));
 //BA.debugLineNum = 304;BA.debugLine="GlobalVar.CompanyID = rsBranch.GetInt(\"CompanyI";
mostCurrent._globalvar._companyid /*int*/  = _rsbranch.GetInt("CompanyID");
 //BA.debugLineNum = 305;BA.debugLine="GlobalVar.BranchCode = rsBranch.GetString(\"Bran";
mostCurrent._globalvar._branchcode /*String*/  = _rsbranch.GetString("BranchCode");
 //BA.debugLineNum = 306;BA.debugLine="GlobalVar.BranchName = rsBranch.GetString(\"Bran";
mostCurrent._globalvar._branchname /*String*/  = _rsbranch.GetString("BranchName");
 //BA.debugLineNum = 307;BA.debugLine="GlobalVar.BranchAddress = rsBranch.GetString(\"B";
mostCurrent._globalvar._branchaddress /*String*/  = _rsbranch.GetString("BranchAddress");
 //BA.debugLineNum = 308;BA.debugLine="GlobalVar.BranchContactNo = rsBranch.GetString(";
mostCurrent._globalvar._branchcontactno /*String*/  = _rsbranch.GetString("ContactNo");
 //BA.debugLineNum = 309;BA.debugLine="GlobalVar.BranchTINumber = rsBranch.GetString(\"";
mostCurrent._globalvar._branchtinumber /*String*/  = _rsbranch.GetString("TinNo");
 //BA.debugLineNum = 310;BA.debugLine="GlobalVar.BranchLogo = rsBranch.GetString(\"Logo";
mostCurrent._globalvar._branchlogo /*String*/  = _rsbranch.GetString("LogoName");
 //BA.debugLineNum = 311;BA.debugLine="GlobalVar.WithMeterCharges = rsBranch.GetInt(\"W";
mostCurrent._globalvar._withmetercharges /*int*/  = _rsbranch.GetInt("WithMeterCharges");
 //BA.debugLineNum = 312;BA.debugLine="GlobalVar.WithFranchisedTax = rsBranch.GetInt(\"";
mostCurrent._globalvar._withfranchisedtax /*int*/  = _rsbranch.GetInt("WithFranchisedTax");
 //BA.debugLineNum = 313;BA.debugLine="GlobalVar.WithSeptageFee = rsBranch.GetInt(\"Wit";
mostCurrent._globalvar._withseptagefee /*int*/  = _rsbranch.GetInt("WithSeptageFee");
 //BA.debugLineNum = 314;BA.debugLine="GlobalVar.WithDisconDate = rsBranch.GetInt(\"Wit";
mostCurrent._globalvar._withdiscondate /*int*/  = _rsbranch.GetInt("WithDisconDate");
 //BA.debugLineNum = 315;BA.debugLine="GlobalVar.SeptageLogo = rsBranch.GetString(\"Sep";
mostCurrent._globalvar._septagelogo /*String*/  = _rsbranch.GetString("SeptageLogo");
 //BA.debugLineNum = 316;BA.debugLine="GlobalVar.SeptageProv = rsBranch.GetInt(\"Septag";
mostCurrent._globalvar._septageprov /*int*/  = _rsbranch.GetInt("SeptageProvider");
 };
 } 
       catch (Exception e23) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e23); //BA.debugLineNum = 325;BA.debugLine="ToastMessageShow($\"Unable to fetch Branch Info d";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to fetch Branch Info due to ")+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 326;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("113172768",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 };
 //BA.debugLineNum = 328;BA.debugLine="rsBranch.Close";
_rsbranch.Close();
 //BA.debugLineNum = 329;BA.debugLine="End Sub";
return "";
}
public static String  _getbranchname(anywheresoftware.b4a.BA _ba,int _ibranchid) throws Exception{
String _sretval = "";
 //BA.debugLineNum = 31;BA.debugLine="Public Sub GetBranchName(iBranchID As Int) As Stri";
 //BA.debugLineNum = 32;BA.debugLine="Dim sRetVal As String";
_sretval = "";
 //BA.debugLineNum = 33;BA.debugLine="Try";
try { //BA.debugLineNum = 34;BA.debugLine="sRetVal = Starter.DBCon.ExecQuerySingleResult(\"S";
_sretval = mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult("SELECT BranchName FROM tblBranches WHERE BranchID = "+BA.NumberToString(_ibranchid));
 } 
       catch (Exception e5) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e5); //BA.debugLineNum = 36;BA.debugLine="ToastMessageShow($\"Unable to fetch Branch Name d";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to fetch Branch Name due to ")+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 37;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("112320774",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 };
 //BA.debugLineNum = 39;BA.debugLine="Return sRetVal";
if (true) return _sretval;
 //BA.debugLineNum = 40;BA.debugLine="End Sub";
return "";
}
public static String  _getcodebyid(anywheresoftware.b4a.BA _ba,String _retfieldcode,String _tablename,String _fieldidtocompare,int _iid) throws Exception{
String _sretvalue = "";
 //BA.debugLineNum = 577;BA.debugLine="Public Sub GetCodebyID(RetFieldCode As String, Tab";
 //BA.debugLineNum = 578;BA.debugLine="Dim rsTemp As Cursor";
_rstemp = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 579;BA.debugLine="Dim sRetValue As String";
_sretvalue = "";
 //BA.debugLineNum = 581;BA.debugLine="Try";
try { //BA.debugLineNum = 582;BA.debugLine="Starter.strCriteria = \"SELECT \" & RetFieldCode &";
mostCurrent._starter._strcriteria /*String*/  = "SELECT "+_retfieldcode+" FROM "+_tablename+" "+"WHERE "+_fieldidtocompare+" = "+BA.NumberToString(_iid);
 //BA.debugLineNum = 584;BA.debugLine="rsTemp = Starter.DBCon.ExecQuery(Starter.strCrit";
_rstemp = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 585;BA.debugLine="Log(Starter.strCriteria)";
anywheresoftware.b4a.keywords.Common.LogImpl("114090248",mostCurrent._starter._strcriteria /*String*/ ,0);
 //BA.debugLineNum = 587;BA.debugLine="If rsTemp.RowCount > 0 Then";
if (_rstemp.getRowCount()>0) { 
 //BA.debugLineNum = 588;BA.debugLine="rsTemp.Position = 0";
_rstemp.setPosition((int) (0));
 //BA.debugLineNum = 589;BA.debugLine="sRetValue = rsTemp.GetString(RetFieldCode)";
_sretvalue = _rstemp.GetString(_retfieldcode);
 }else {
 //BA.debugLineNum = 591;BA.debugLine="sRetValue = \"\"";
_sretvalue = "";
 };
 } 
       catch (Exception e14) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e14); //BA.debugLineNum = 594;BA.debugLine="sRetValue = \"\"";
_sretvalue = "";
 //BA.debugLineNum = 595;BA.debugLine="ToastMessageShow($\"Unable to Get Code by ID due";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to Get Code by ID due to ")+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 596;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("114090259",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 };
 //BA.debugLineNum = 598;BA.debugLine="rsTemp.Close";
_rstemp.Close();
 //BA.debugLineNum = 599;BA.debugLine="Return sRetValue";
if (true) return _sretvalue;
 //BA.debugLineNum = 600;BA.debugLine="End Sub";
return "";
}
public static int  _getcompanyid(anywheresoftware.b4a.BA _ba,double _ibranchid) throws Exception{
int _iretval = 0;
 //BA.debugLineNum = 8;BA.debugLine="Public Sub GetCompanyID(iBranchID As Double) As In";
 //BA.debugLineNum = 9;BA.debugLine="Dim iRetVal As Int";
_iretval = 0;
 //BA.debugLineNum = 10;BA.debugLine="Try";
try { //BA.debugLineNum = 11;BA.debugLine="iRetVal = Starter.DBCon.ExecQuerySingleResult(\"S";
_iretval = (int)(Double.parseDouble(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult("SELECT tblBranches.CompanyID FROM tblBranches WHERE BranchID = "+BA.NumberToString(_ibranchid))));
 } 
       catch (Exception e5) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e5); //BA.debugLineNum = 13;BA.debugLine="ToastMessageShow($\"Unable to fetch Branch ID due";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to fetch Branch ID due to ")+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 14;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("112189702",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 };
 //BA.debugLineNum = 16;BA.debugLine="Return iRetVal";
if (true) return _iretval;
 //BA.debugLineNum = 17;BA.debugLine="End Sub";
return 0;
}
public static String  _getcompanyinfo(anywheresoftware.b4a.BA _ba,int _icompanyid) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _rscompany = null;
 //BA.debugLineNum = 331;BA.debugLine="Public Sub GetCompanyInfo(iCompanyID As Int)";
 //BA.debugLineNum = 332;BA.debugLine="Dim rsCompany As Cursor";
_rscompany = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 333;BA.debugLine="Try";
try { //BA.debugLineNum = 334;BA.debugLine="Starter.strCriteria = \"SELECT * FROM tblCompanie";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblCompanies "+"WHERE CompanyID = "+BA.NumberToString(_icompanyid);
 //BA.debugLineNum = 336;BA.debugLine="Log(Starter.strCriteria)";
anywheresoftware.b4a.keywords.Common.LogImpl("113238277",mostCurrent._starter._strcriteria /*String*/ ,0);
 //BA.debugLineNum = 337;BA.debugLine="rsCompany = Starter.DBCon.ExecQuery(Starter.strC";
_rscompany = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 339;BA.debugLine="If rsCompany.RowCount > 0 Then";
if (_rscompany.getRowCount()>0) { 
 //BA.debugLineNum = 340;BA.debugLine="rsCompany.Position = 0";
_rscompany.setPosition((int) (0));
 //BA.debugLineNum = 341;BA.debugLine="GlobalVar.CompanyCode = rsCompany.GetInt(\"Compa";
mostCurrent._globalvar._companycode /*String*/  = BA.NumberToString(_rscompany.GetInt("CompanyCode"));
 //BA.debugLineNum = 342;BA.debugLine="GlobalVar.CompanyName = rsCompany.GetString(\"Co";
mostCurrent._globalvar._companyname /*String*/  = _rscompany.GetString("CompanyName");
 };
 } 
       catch (Exception e12) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e12); //BA.debugLineNum = 345;BA.debugLine="ToastMessageShow($\"Unable to fetch Company Info";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to fetch Company Info due to ")+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 346;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("113238287",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 };
 //BA.debugLineNum = 348;BA.debugLine="rsCompany.Close";
_rscompany.Close();
 //BA.debugLineNum = 349;BA.debugLine="End Sub";
return "";
}
public static double  _getidbycode(anywheresoftware.b4a.BA _ba,String _retfieldid,String _tablename,String _fieldcodetocompare,String _scode) throws Exception{
double _dretvalue = 0;
 //BA.debugLineNum = 552;BA.debugLine="Public Sub GetIDbyCode(RetFieldID As String, Table";
 //BA.debugLineNum = 553;BA.debugLine="Dim rsTemp As Cursor";
_rstemp = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 554;BA.debugLine="Dim dRetValue As Double";
_dretvalue = 0;
 //BA.debugLineNum = 556;BA.debugLine="Try";
try { //BA.debugLineNum = 557;BA.debugLine="Starter.strCriteria = \"SELECT \" & RetFieldID & \"";
mostCurrent._starter._strcriteria /*String*/  = "SELECT "+_retfieldid+" FROM "+_tablename+" "+"WHERE "+_fieldcodetocompare+" = '"+_scode+"'";
 //BA.debugLineNum = 559;BA.debugLine="rsTemp = Starter.DBCon.ExecQuery(Starter.strCrit";
_rstemp = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 560;BA.debugLine="Log(Starter.strCriteria)";
anywheresoftware.b4a.keywords.Common.LogImpl("114024712",mostCurrent._starter._strcriteria /*String*/ ,0);
 //BA.debugLineNum = 562;BA.debugLine="If rsTemp.RowCount > 0 Then";
if (_rstemp.getRowCount()>0) { 
 //BA.debugLineNum = 563;BA.debugLine="rsTemp.Position = 0";
_rstemp.setPosition((int) (0));
 //BA.debugLineNum = 564;BA.debugLine="dRetValue = rsTemp.GetDouble(RetFieldID)";
_dretvalue = _rstemp.GetDouble(_retfieldid);
 }else {
 //BA.debugLineNum = 566;BA.debugLine="dRetValue = 0";
_dretvalue = 0;
 };
 } 
       catch (Exception e14) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e14); //BA.debugLineNum = 569;BA.debugLine="dRetValue = 0";
_dretvalue = 0;
 //BA.debugLineNum = 570;BA.debugLine="ToastMessageShow($\"Unable to Get ID by Code due";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to Get ID by Code due to ")+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 571;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("114024723",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 };
 //BA.debugLineNum = 573;BA.debugLine="rsTemp.Close";
_rstemp.Close();
 //BA.debugLineNum = 574;BA.debugLine="Return dRetValue";
if (true) return _dretvalue;
 //BA.debugLineNum = 575;BA.debugLine="End Sub";
return 0;
}
public static double  _getminimumrangeto(anywheresoftware.b4a.BA _ba,int _ibranchid,String _sclass,String _ssubclass,String _dfrom) throws Exception{
double _rateheader = 0;
double _retval = 0;
 //BA.debugLineNum = 913;BA.debugLine="Public Sub GetMinimumRangeTo(iBranchID As Int, sCl";
 //BA.debugLineNum = 914;BA.debugLine="Dim RateHeader As Double";
_rateheader = 0;
 //BA.debugLineNum = 915;BA.debugLine="Dim RetVal As Double";
_retval = 0;
 //BA.debugLineNum = 916;BA.debugLine="Try";
try { //BA.debugLineNum = 918;BA.debugLine="Starter.strCriteria = \"SELECT id FROM rates_head";
mostCurrent._starter._strcriteria /*String*/  = "SELECT id FROM rates_header WHERE branch_id = "+BA.NumberToString(_ibranchid)+" "+"AND class = '"+_sclass+"' "+"AND sub_class = '"+_ssubclass+"' "+"AND date_from <= '"+_dfrom+"' "+"ORDER BY date_from DESC LIMIT 1";
 //BA.debugLineNum = 923;BA.debugLine="Log(Starter.strCriteria)";
anywheresoftware.b4a.keywords.Common.LogImpl("114876682",mostCurrent._starter._strcriteria /*String*/ ,0);
 //BA.debugLineNum = 925;BA.debugLine="RateHeader = Starter.DBCon.ExecQuerySingleResult";
_rateheader = (double)(Double.parseDouble(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 926;BA.debugLine="Log(RateHeader)";
anywheresoftware.b4a.keywords.Common.LogImpl("114876685",BA.NumberToString(_rateheader),0);
 //BA.debugLineNum = 929;BA.debugLine="Starter.strCriteria = \"SELECT rangeto FROM rates";
mostCurrent._starter._strcriteria /*String*/  = "SELECT rangeto FROM rates_details "+"WHERE header_id = "+BA.NumberToString(_rateheader)+" "+"AND typecust = '"+"m"+"' "+"ORDER BY RangeTo DESC LIMIT 1";
 //BA.debugLineNum = 934;BA.debugLine="RetVal = Starter.DBCon.ExecQuerySingleResult(Sta";
_retval = (double)(Double.parseDouble(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult(mostCurrent._starter._strcriteria /*String*/ )));
 } 
       catch (Exception e11) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e11); //BA.debugLineNum = 937;BA.debugLine="xui.MsgboxAsync(\"Unable to fetch basic amount du";
_xui.MsgboxAsync((_ba.processBA == null ? _ba : _ba.processBA),BA.ObjectToCharSequence("Unable to fetch basic amount due to "+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage()),BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.Application.getLabelName()));
 //BA.debugLineNum = 938;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("114876697",anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage(),0);
 //BA.debugLineNum = 939;BA.debugLine="Return 0";
if (true) return 0;
 };
 //BA.debugLineNum = 941;BA.debugLine="Return RetVal";
if (true) return _retval;
 //BA.debugLineNum = 942;BA.debugLine="End Sub";
return 0;
}
public static double  _getminimumrate(anywheresoftware.b4a.BA _ba,int _iheaderid) throws Exception{
int _iretval = 0;
 //BA.debugLineNum = 630;BA.debugLine="Public Sub GetMinimumRate(iHeaderID As Int) As Dou";
 //BA.debugLineNum = 631;BA.debugLine="Dim iRetVal As Int";
_iretval = 0;
 //BA.debugLineNum = 632;BA.debugLine="Try";
try { //BA.debugLineNum = 633;BA.debugLine="iRetVal = Starter.DBCon.ExecQuerySingleResult(\"S";
_iretval = (int)(Double.parseDouble(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult("SELECT amount FROM rates_details WHERE header_id = "+BA.NumberToString(_iheaderid)+" "+"AND typecust = '"+"m"+"' "+"ORDER BY rangefrom DESC LIMIT 1")));
 } 
       catch (Exception e5) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e5); //BA.debugLineNum = 637;BA.debugLine="ToastMessageShow($\"Unable to fetch Residential R";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to fetch Residential Rate due to ")+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 638;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("114286856",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 };
 //BA.debugLineNum = 640;BA.debugLine="Return iRetVal";
if (true) return _iretval;
 //BA.debugLineNum = 641;BA.debugLine="End Sub";
return 0;
}
public static double  _getpcamount(anywheresoftware.b4a.BA _ba,int _ibookid) throws Exception{
double _amtret = 0;
 //BA.debugLineNum = 500;BA.debugLine="Public Sub GetPCAmount(iBookID As Int) As Double";
 //BA.debugLineNum = 501;BA.debugLine="Dim AmtRet As Double";
_amtret = 0;
 //BA.debugLineNum = 502;BA.debugLine="Try";
try { //BA.debugLineNum = 503;BA.debugLine="AmtRet = Starter.DBCon.ExecQuerySingleResult(\"SE";
_amtret = (double)(Double.parseDouble(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult("SELECT Amount FROM tblPCA WHERE BranchID = "+BA.NumberToString(mostCurrent._globalvar._branchid /*int*/ )+" AND BookID = "+BA.NumberToString(_ibookid))));
 } 
       catch (Exception e5) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e5); //BA.debugLineNum = 505;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("113828101",anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage(),0);
 //BA.debugLineNum = 506;BA.debugLine="AmtRet = 0";
_amtret = 0;
 };
 //BA.debugLineNum = 508;BA.debugLine="Return AmtRet";
if (true) return _amtret;
 //BA.debugLineNum = 509;BA.debugLine="End Sub";
return 0;
}
public static int  _getratesheaderid(anywheresoftware.b4a.BA _ba,String _sclass,String _ssubclass) throws Exception{
int _iretval = 0;
 //BA.debugLineNum = 606;BA.debugLine="Public Sub GetRatesHeaderID(sClass As String, sSub";
 //BA.debugLineNum = 607;BA.debugLine="Dim iRetVal As Int";
_iretval = 0;
 //BA.debugLineNum = 608;BA.debugLine="Try";
try { //BA.debugLineNum = 609;BA.debugLine="iRetVal = Starter.DBCon.ExecQuerySingleResult(\"S";
_iretval = (int)(Double.parseDouble(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult("SELECT id FROM rates_header WHERE class = '"+_sclass+"' "+"AND sub_class = '"+_ssubclass+"'")));
 } 
       catch (Exception e5) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e5); //BA.debugLineNum = 612;BA.debugLine="ToastMessageShow($\"Unable to fetch Rate Header I";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to fetch Rate Header ID due to ")+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 613;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("114155783",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 };
 //BA.debugLineNum = 615;BA.debugLine="Return iRetVal";
if (true) return _iretval;
 //BA.debugLineNum = 616;BA.debugLine="End Sub";
return 0;
}
public static boolean  _getratesje(anywheresoftware.b4a.BA _ba,int _ibranchid,String _sclass,String _ssubclass,String _dfrom) throws Exception{
boolean _blnretval = false;
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsheader = null;
 //BA.debugLineNum = 793;BA.debugLine="Public Sub GetRatesJe(iBranchID As Int, sClass As";
 //BA.debugLineNum = 794;BA.debugLine="Dim blnRetVal As Boolean";
_blnretval = false;
 //BA.debugLineNum = 795;BA.debugLine="Dim rsHeader As Cursor";
_rsheader = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 796;BA.debugLine="Try";
try { //BA.debugLineNum = 798;BA.debugLine="Starter.strCriteria = \"SELECT * FROM rates_heade";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM rates_header WHERE branch_id = "+BA.NumberToString(_ibranchid)+" "+"AND class = '"+_sclass+"' "+"AND sub_class = '"+_ssubclass+"' "+"AND date_from <= '"+_dfrom+"' "+"ORDER BY date_from DESC LIMIT 1";
 //BA.debugLineNum = 803;BA.debugLine="Log(Starter.strCriteria)";
anywheresoftware.b4a.keywords.Common.LogImpl("114614538",mostCurrent._starter._strcriteria /*String*/ ,0);
 //BA.debugLineNum = 805;BA.debugLine="rsHeader = Starter.DBCon.ExecQuery(Starter.strCr";
_rsheader = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 806;BA.debugLine="If rsHeader.RowCount > 0 Then";
if (_rsheader.getRowCount()>0) { 
 //BA.debugLineNum = 807;BA.debugLine="blnRetVal = True";
_blnretval = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 809;BA.debugLine="blnRetVal = False";
_blnretval = anywheresoftware.b4a.keywords.Common.False;
 };
 } 
       catch (Exception e13) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e13); //BA.debugLineNum = 812;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("114614547",anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage(),0);
 //BA.debugLineNum = 813;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 815;BA.debugLine="Return blnRetVal";
if (true) return _blnretval;
 //BA.debugLineNum = 816;BA.debugLine="End Sub";
return false;
}
public static int  _getratesresheaderid(anywheresoftware.b4a.BA _ba,String _ssubclass) throws Exception{
int _iretval = 0;
 //BA.debugLineNum = 618;BA.debugLine="Public Sub GetRatesResHeaderID(sSubClass As String";
 //BA.debugLineNum = 619;BA.debugLine="Dim iRetVal As Int";
_iretval = 0;
 //BA.debugLineNum = 620;BA.debugLine="Try";
try { //BA.debugLineNum = 621;BA.debugLine="iRetVal = Starter.DBCon.ExecQuerySingleResult(\"S";
_iretval = (int)(Double.parseDouble(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult("SELECT id FROM rates_header WHERE class = '"+"RES"+"' "+"AND sub_class = '"+_ssubclass+"'")));
 } 
       catch (Exception e5) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e5); //BA.debugLineNum = 624;BA.debugLine="ToastMessageShow($\"Unable to fetch Rate Header I";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to fetch Rate Header ID due to ")+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 625;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("114221319",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 };
 //BA.debugLineNum = 627;BA.debugLine="Return iRetVal";
if (true) return _iretval;
 //BA.debugLineNum = 628;BA.debugLine="End Sub";
return 0;
}
public static int  _getreaderid(anywheresoftware.b4a.BA _ba,String _sname) throws Exception{
int _iretval = 0;
 //BA.debugLineNum = 123;BA.debugLine="Public Sub GetReaderID(sName As String) As Int";
 //BA.debugLineNum = 124;BA.debugLine="Dim iRetVal As Int";
_iretval = 0;
 //BA.debugLineNum = 125;BA.debugLine="Try";
try { //BA.debugLineNum = 126;BA.debugLine="Log(\"SELECT UserID FROM tblUsers WHERE EmpName =";
anywheresoftware.b4a.keywords.Common.LogImpl("112713987","SELECT UserID FROM tblUsers WHERE EmpName = '"+_sname+"'",0);
 //BA.debugLineNum = 127;BA.debugLine="iRetVal = Starter.DBCon.ExecQuerySingleResult(\"S";
_iretval = (int)(Double.parseDouble(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult("SELECT UserID FROM tblUsers WHERE EmpName = '"+_sname+"'")));
 } 
       catch (Exception e6) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e6); //BA.debugLineNum = 129;BA.debugLine="ToastMessageShow($\"Unable to fetch Reader Name d";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to fetch Reader Name due to ")+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 130;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("112713991",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 };
 //BA.debugLineNum = 132;BA.debugLine="Return iRetVal";
if (true) return _iretval;
 //BA.debugLineNum = 133;BA.debugLine="End Sub";
return 0;
}
public static String  _getseptageratedetails(anywheresoftware.b4a.BA _ba,int _ibranchid,int _irateid,String _sacctclass) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsseptage = null;
 //BA.debugLineNum = 961;BA.debugLine="Public Sub GetSeptageRateDetails(iBranchID As Int,";
 //BA.debugLineNum = 962;BA.debugLine="Dim rsSeptage As Cursor";
_rsseptage = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 963;BA.debugLine="Try";
try { //BA.debugLineNum = 964;BA.debugLine="Starter.strCriteria = \"SELECT * FROM SSMRates WH";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM SSMRates WHERE AcctClassification = '"+_sacctclass+"' "+"AND BranchID = "+BA.NumberToString(_ibranchid)+" "+"AND SeptageRatesID = "+BA.NumberToString(_irateid);
 //BA.debugLineNum = 968;BA.debugLine="LogColor(Starter.strCriteria, Colors.Yellow)";
anywheresoftware.b4a.keywords.Common.LogImpl("115007751",mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.Colors.Yellow);
 //BA.debugLineNum = 970;BA.debugLine="rsSeptage = Starter.DBCon.ExecQuery(Starter.strC";
_rsseptage = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 972;BA.debugLine="If rsSeptage.RowCount > 0 Then";
if (_rsseptage.getRowCount()>0) { 
 //BA.debugLineNum = 973;BA.debugLine="rsSeptage.Position = 0";
_rsseptage.setPosition((int) (0));
 //BA.debugLineNum = 974;BA.debugLine="GlobalVar.SeptageRateType = rsSeptage.GetString";
mostCurrent._globalvar._septageratetype /*String*/  = _rsseptage.GetString("RateType");
 //BA.debugLineNum = 975;BA.debugLine="GlobalVar.SeptageMinCuM = rsSeptage.GetInt(\"Min";
mostCurrent._globalvar._septagemincum /*int*/  = _rsseptage.GetInt("MinCum");
 //BA.debugLineNum = 976;BA.debugLine="GlobalVar.SeptageMaxCuM = rsSeptage.GetInt(\"Max";
mostCurrent._globalvar._septagemaxcum /*int*/  = _rsseptage.GetInt("MaxCum");
 //BA.debugLineNum = 977;BA.debugLine="GlobalVar.SeptageRatePerCuM = rsSeptage.GetDoub";
mostCurrent._globalvar._septageratepercum /*double*/  = _rsseptage.GetDouble("RatePerCum");
 }else {
 //BA.debugLineNum = 979;BA.debugLine="ToastMessageShow($\"Unable to fetch Rate Type du";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to fetch Rate Type due to ")+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 };
 } 
       catch (Exception e16) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e16); //BA.debugLineNum = 982;BA.debugLine="ToastMessageShow($\"Unable to fetch Rate Type due";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to fetch Rate Type due to ")+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 983;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("115007766",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 };
 //BA.debugLineNum = 985;BA.debugLine="End Sub";
return "";
}
public static int  _getseptagerateid(anywheresoftware.b4a.BA _ba,String _sacctclass,int _ibranchid) throws Exception{
String _iretvalue = "";
 //BA.debugLineNum = 946;BA.debugLine="Public Sub GetSeptageRateID(sAcctClass As String,";
 //BA.debugLineNum = 947;BA.debugLine="Dim iRetValue As String";
_iretvalue = "";
 //BA.debugLineNum = 948;BA.debugLine="iRetValue = 0";
_iretvalue = BA.NumberToString(0);
 //BA.debugLineNum = 950;BA.debugLine="Try";
try { //BA.debugLineNum = 951;BA.debugLine="iRetValue = Starter.DBCon.ExecQuerySingleResult(";
_iretvalue = mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult("SELECT SeptageRatesID FROM SSMRates WHERE AcctClassification = '"+_sacctclass+"' "+"AND BranchID = "+BA.NumberToString(_ibranchid));
 } 
       catch (Exception e6) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e6); //BA.debugLineNum = 954;BA.debugLine="iRetValue = 0";
_iretvalue = BA.NumberToString(0);
 //BA.debugLineNum = 955;BA.debugLine="ToastMessageShow($\"Unable to fetch Rate Type due";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to fetch Rate Type due to ")+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 956;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("114942218",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 };
 //BA.debugLineNum = 958;BA.debugLine="Return iRetValue";
if (true) return (int)(Double.parseDouble(_iretvalue));
 //BA.debugLineNum = 959;BA.debugLine="End Sub";
return 0;
}
public static int  _getseqno(anywheresoftware.b4a.BA _ba,int _ibookid) throws Exception{
double _dblno = 0;
int _iretval = 0;
boolean _blnsaved = false;
 //BA.debugLineNum = 392;BA.debugLine="Sub GetSeqNo(iBookID As Int) As Int";
 //BA.debugLineNum = 393;BA.debugLine="Dim dblNo As Double";
_dblno = 0;
 //BA.debugLineNum = 394;BA.debugLine="Dim iRetVal As Int";
_iretval = 0;
 //BA.debugLineNum = 396;BA.debugLine="Dim blnSaved As Boolean";
_blnsaved = false;
 //BA.debugLineNum = 399;BA.debugLine="blnSaved = False";
_blnsaved = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 401;BA.debugLine="Do While Not (blnSaved = True)";
while (anywheresoftware.b4a.keywords.Common.Not(_blnsaved==anywheresoftware.b4a.keywords.Common.True)) {
 //BA.debugLineNum = 402;BA.debugLine="blnSaved = SaveRecNo(iBookID)";
_blnsaved = _saverecno(_ba,_ibookid);
 //BA.debugLineNum = 403;BA.debugLine="If blnSaved Then";
if (_blnsaved) { 
 //BA.debugLineNum = 404;BA.debugLine="dblNo = Starter.DBCon.ExecQuerySingleResult(\"SE";
_dblno = (double)(Double.parseDouble(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult("SELECT LastSeqNo FROM NewSequence WHERE BookID = "+BA.NumberToString(_ibookid))));
 //BA.debugLineNum = 405;BA.debugLine="Exit";
if (true) break;
 }else {
 };
 }
;
 //BA.debugLineNum = 412;BA.debugLine="iRetVal=dblNo";
_iretval = (int) (_dblno);
 //BA.debugLineNum = 413;BA.debugLine="Return iRetVal";
if (true) return _iretval;
 //BA.debugLineNum = 415;BA.debugLine="End Sub";
return 0;
}
public static String  _getserverip(anywheresoftware.b4a.BA _ba) throws Exception{
String _sretval = "";
 //BA.debugLineNum = 75;BA.debugLine="Public Sub GetServerIP() As String";
 //BA.debugLineNum = 76;BA.debugLine="Dim sRetVal As String";
_sretval = "";
 //BA.debugLineNum = 77;BA.debugLine="Try";
try { //BA.debugLineNum = 78;BA.debugLine="sRetVal = Starter.DBCon.ExecQuerySingleResult(\"S";
_sretval = mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult("SELECT server_ip FROM android_metadata");
 //BA.debugLineNum = 79;BA.debugLine="If sRetVal = \"\" Then";
if ((_sretval).equals("")) { 
 //BA.debugLineNum = 80;BA.debugLine="sRetVal = \"http://192.168.0.2\"";
_sretval = "http://192.168.0.2";
 };
 } 
       catch (Exception e8) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e8); //BA.debugLineNum = 83;BA.debugLine="ToastMessageShow($\"Unable to fetch Server's IP A";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to fetch Server's IP Address due to ")+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 84;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("112582921",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 };
 //BA.debugLineNum = 86;BA.debugLine="Log(sRetVal)";
anywheresoftware.b4a.keywords.Common.LogImpl("112582923",_sretval,0);
 //BA.debugLineNum = 87;BA.debugLine="Return sRetVal";
if (true) return _sretval;
 //BA.debugLineNum = 88;BA.debugLine="End Sub";
return "";
}
public static String  _getuserinfo(anywheresoftware.b4a.BA _ba,String _susername,String _suserpassword) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsuser = null;
 //BA.debugLineNum = 247;BA.debugLine="Public Sub GetUserInfo(sUserName As String, sUserP";
 //BA.debugLineNum = 248;BA.debugLine="Dim rsUser As Cursor";
_rsuser = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 249;BA.debugLine="Try";
try { //BA.debugLineNum = 250;BA.debugLine="Starter.strCriteria = \"SELECT * FROM tblUsers \"";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblUsers "+"WHERE UserName = '"+_susername+"' "+"AND UserPassword = '"+_suserpassword+"'";
 //BA.debugLineNum = 253;BA.debugLine="Log(Starter.strCriteria)";
anywheresoftware.b4a.keywords.Common.LogImpl("113041670",mostCurrent._starter._strcriteria /*String*/ ,0);
 //BA.debugLineNum = 254;BA.debugLine="rsUser = Starter.DBCon.ExecQuery(Starter.strCrit";
_rsuser = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 256;BA.debugLine="If rsUser.RowCount > 0 Then";
if (_rsuser.getRowCount()>0) { 
 //BA.debugLineNum = 257;BA.debugLine="rsUser.Position = 0";
_rsuser.setPosition((int) (0));
 //BA.debugLineNum = 258;BA.debugLine="GlobalVar.UserID = rsUser.GetInt(\"UserID\")";
mostCurrent._globalvar._userid /*int*/  = _rsuser.GetInt("UserID");
 //BA.debugLineNum = 259;BA.debugLine="GlobalVar.EmpName =rsUser.GetString(\"EmpName\")";
mostCurrent._globalvar._empname /*String*/  = _rsuser.GetString("EmpName");
 //BA.debugLineNum = 260;BA.debugLine="GlobalVar.UserName = rsUser.GetString(\"UserName";
mostCurrent._globalvar._username /*String*/  = _rsuser.GetString("UserName");
 //BA.debugLineNum = 261;BA.debugLine="GlobalVar.UserPW = rsUser.GetString(\"UserPasswo";
mostCurrent._globalvar._userpw /*String*/  = _rsuser.GetString("UserPassword");
 //BA.debugLineNum = 262;BA.debugLine="GlobalVar.Mod1 = rsUser.GetInt(\"Module1\")";
mostCurrent._globalvar._mod1 /*int*/  = _rsuser.GetInt("Module1");
 //BA.debugLineNum = 263;BA.debugLine="GlobalVar.Mod2 = rsUser.GetInt(\"Module2\")";
mostCurrent._globalvar._mod2 /*int*/  = _rsuser.GetInt("Module2");
 //BA.debugLineNum = 264;BA.debugLine="GlobalVar.Mod3 = rsUser.GetInt(\"Module3\")";
mostCurrent._globalvar._mod3 /*int*/  = _rsuser.GetInt("Module3");
 //BA.debugLineNum = 265;BA.debugLine="GlobalVar.Mod4 = rsUser.GetInt(\"Module4\")";
mostCurrent._globalvar._mod4 /*int*/  = _rsuser.GetInt("Module4");
 //BA.debugLineNum = 266;BA.debugLine="GlobalVar.Mod5 = rsUser.GetInt(\"Module5\")";
mostCurrent._globalvar._mod5 /*int*/  = _rsuser.GetInt("Module5");
 //BA.debugLineNum = 267;BA.debugLine="GlobalVar.Mod6 = rsUser.GetInt(\"Module6\")";
mostCurrent._globalvar._mod6 /*int*/  = _rsuser.GetInt("Module6");
 };
 //BA.debugLineNum = 269;BA.debugLine="rsUser.Close";
_rsuser.Close();
 } 
       catch (Exception e21) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e21); //BA.debugLineNum = 271;BA.debugLine="ToastMessageShow($\"Unable to fetch User's Info d";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to fetch User's Info due to ")+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 272;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("113041689",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 };
 //BA.debugLineNum = 274;BA.debugLine="End Sub";
return "";
}
public static boolean  _hasbookassigned(anywheresoftware.b4a.BA _ba,int _ibranchid,int _ibillmonth,int _ibillyear) throws Exception{
boolean _blnretval = false;
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsbookassignment = null;
 //BA.debugLineNum = 355;BA.debugLine="Public Sub HasBookAssigned (iBranchID As Int, iBil";
 //BA.debugLineNum = 356;BA.debugLine="Dim blnRetVal As Boolean";
_blnretval = false;
 //BA.debugLineNum = 357;BA.debugLine="Dim rsBookAssignment As Cursor";
_rsbookassignment = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 358;BA.debugLine="blnRetVal = False";
_blnretval = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 359;BA.debugLine="Try";
try { //BA.debugLineNum = 360;BA.debugLine="Starter.strCriteria = \"SELECT BranchID, BillYear";
mostCurrent._starter._strcriteria /*String*/  = "SELECT BranchID, BillYear, BillMonth FROM tblBookAssignments "+"WHERE BranchID = "+BA.NumberToString(_ibranchid)+" "+"AND BillMonth = "+BA.NumberToString(_ibillmonth)+" "+"AND BillYear = "+BA.NumberToString(_ibillyear)+" "+"GROUP BY BranchID, BillYear, BillMonth";
 //BA.debugLineNum = 365;BA.debugLine="rsBookAssignment = Starter.DBCon.ExecQuery(Start";
_rsbookassignment = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 366;BA.debugLine="If rsBookAssignment.RowCount > 0 Then";
if (_rsbookassignment.getRowCount()>0) { 
 //BA.debugLineNum = 367;BA.debugLine="blnRetVal = True";
_blnretval = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 369;BA.debugLine="blnRetVal = False";
_blnretval = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 371;BA.debugLine="rsBookAssignment.Close";
_rsbookassignment.Close();
 } 
       catch (Exception e14) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e14); //BA.debugLineNum = 373;BA.debugLine="blnRetVal = False";
_blnretval = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 374;BA.debugLine="ToastMessageShow(\"Unable to get assigned books d";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Unable to get assigned books due to "+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 375;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("113369364",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 };
 //BA.debugLineNum = 377;BA.debugLine="Return blnRetVal";
if (true) return _blnretval;
 //BA.debugLineNum = 378;BA.debugLine="End Sub";
return false;
}
public static int  _hasdownloadeddata(anywheresoftware.b4a.BA _ba,int _ireaderid) throws Exception{
int _iretval = 0;
 //BA.debugLineNum = 276;BA.debugLine="Public Sub HasDownloadedData(iReaderID As Int) As";
 //BA.debugLineNum = 277;BA.debugLine="Dim iRetVal As Int";
_iretval = 0;
 //BA.debugLineNum = 279;BA.debugLine="Try";
try { //BA.debugLineNum = 280;BA.debugLine="Starter.strCriteria = \"SELECT HasData FROM tblRe";
mostCurrent._starter._strcriteria /*String*/  = "SELECT HasData FROM tblReaders WHERE ReaderID = "+BA.NumberToString(_ireaderid);
 //BA.debugLineNum = 281;BA.debugLine="LogColor(Starter.strCriteria, Colors.Blue)";
anywheresoftware.b4a.keywords.Common.LogImpl("113107205",mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.Colors.Blue);
 //BA.debugLineNum = 283;BA.debugLine="iRetVal = Starter.DBCon.ExecQuerySingleResult(St";
_iretval = (int)(Double.parseDouble(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult(mostCurrent._starter._strcriteria /*String*/ )));
 } 
       catch (Exception e7) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e7); //BA.debugLineNum = 285;BA.debugLine="ToastMessageShow($\"Unable to fetch Branch System";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to fetch Branch System Mode due to ")+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 286;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("113107210",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 //BA.debugLineNum = 287;BA.debugLine="iRetVal = 0";
_iretval = (int) (0);
 };
 //BA.debugLineNum = 290;BA.debugLine="Return iRetVal";
if (true) return _iretval;
 //BA.debugLineNum = 291;BA.debugLine="End Sub";
return 0;
}
public static boolean  _hasratedetails(anywheresoftware.b4a.BA _ba,double _dheaderid) throws Exception{
boolean _blnretval = false;
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsratesdetails = null;
 //BA.debugLineNum = 772;BA.debugLine="Public Sub HasRateDetails(dHeaderID As Double) As";
 //BA.debugLineNum = 773;BA.debugLine="Dim blnRetVal As Boolean";
_blnretval = false;
 //BA.debugLineNum = 774;BA.debugLine="Dim rsRatesDetails As Cursor";
_rsratesdetails = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 775;BA.debugLine="Try";
try { //BA.debugLineNum = 777;BA.debugLine="Starter.strCriteria = \"SELECT * FROM rates_detai";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM rates_details WHERE header_id = "+BA.NumberToString(_dheaderid);
 //BA.debugLineNum = 778;BA.debugLine="Log(Starter.strCriteria)";
anywheresoftware.b4a.keywords.Common.LogImpl("114548998",mostCurrent._starter._strcriteria /*String*/ ,0);
 //BA.debugLineNum = 780;BA.debugLine="rsRatesDetails = Starter.DBCon.ExecQuery(Starter";
_rsratesdetails = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 781;BA.debugLine="If rsRatesDetails.RowCount > 0 Then";
if (_rsratesdetails.getRowCount()>0) { 
 //BA.debugLineNum = 782;BA.debugLine="blnRetVal = True";
_blnretval = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 784;BA.debugLine="blnRetVal = False";
_blnretval = anywheresoftware.b4a.keywords.Common.False;
 };
 } 
       catch (Exception e13) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e13); //BA.debugLineNum = 787;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("114549007",anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage(),0);
 //BA.debugLineNum = 788;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 790;BA.debugLine="Return blnRetVal";
if (true) return _blnretval;
 //BA.debugLineNum = 791;BA.debugLine="End Sub";
return false;
}
public static boolean  _hasrateheader(anywheresoftware.b4a.BA _ba,int _ibranchid,String _sclass,String _ssubclass) throws Exception{
boolean _blnretval = false;
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsheader = null;
 //BA.debugLineNum = 747;BA.debugLine="Public Sub HasRateHeader(iBranchID As Int, sClass";
 //BA.debugLineNum = 748;BA.debugLine="Dim blnRetVal As Boolean";
_blnretval = false;
 //BA.debugLineNum = 749;BA.debugLine="Dim rsHeader As Cursor";
_rsheader = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 750;BA.debugLine="Try";
try { //BA.debugLineNum = 752;BA.debugLine="Starter.strCriteria = \"SELECT * FROM rates_heade";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM rates_header WHERE branch_id = "+BA.NumberToString(_ibranchid)+" "+"AND class = '"+_sclass+"' "+"AND sub_class = '"+_ssubclass+"' "+"ORDER BY date_from DESC LIMIT 1";
 //BA.debugLineNum = 756;BA.debugLine="Log(Starter.strCriteria)";
anywheresoftware.b4a.keywords.Common.LogImpl("114483465",mostCurrent._starter._strcriteria /*String*/ ,0);
 //BA.debugLineNum = 759;BA.debugLine="rsHeader = Starter.DBCon.ExecQuery(Starter.strCr";
_rsheader = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 760;BA.debugLine="If rsHeader.RowCount > 0 Then";
if (_rsheader.getRowCount()>0) { 
 //BA.debugLineNum = 761;BA.debugLine="blnRetVal = True";
_blnretval = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 763;BA.debugLine="blnRetVal = False";
_blnretval = anywheresoftware.b4a.keywords.Common.False;
 };
 } 
       catch (Exception e13) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e13); //BA.debugLineNum = 766;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("114483475",anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage(),0);
 //BA.debugLineNum = 767;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 769;BA.debugLine="Return blnRetVal";
if (true) return _blnretval;
 //BA.debugLineNum = 770;BA.debugLine="End Sub";
return false;
}
public static boolean  _isbookwithpca(anywheresoftware.b4a.BA _ba,int _ibookid) throws Exception{
boolean _blnretvalue = false;
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsbooks = null;
 //BA.debugLineNum = 476;BA.debugLine="Public Sub IsBookWithPCA(iBookID As Int) As Boolea";
 //BA.debugLineNum = 477;BA.debugLine="Dim blnRetValue As Boolean";
_blnretvalue = false;
 //BA.debugLineNum = 478;BA.debugLine="Dim rsBooks As Cursor";
_rsbooks = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 479;BA.debugLine="Try";
try { //BA.debugLineNum = 480;BA.debugLine="Starter.strCriteria = \"SELECT * FROM tblBooks \"";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblBooks "+"WHERE BookID = "+BA.NumberToString(_ibookid);
 //BA.debugLineNum = 482;BA.debugLine="rsBooks = Starter.DBCon.ExecQuery(Starter.strCri";
_rsbooks = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 483;BA.debugLine="If rsBooks.RowCount > 0 Then";
if (_rsbooks.getRowCount()>0) { 
 //BA.debugLineNum = 484;BA.debugLine="rsBooks.Position = 0";
_rsbooks.setPosition((int) (0));
 //BA.debugLineNum = 485;BA.debugLine="If rsBooks.GetInt(\"WithPCA\") = 1 Then";
if (_rsbooks.GetInt("WithPCA")==1) { 
 //BA.debugLineNum = 486;BA.debugLine="blnRetValue = True";
_blnretvalue = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 488;BA.debugLine="blnRetValue = False";
_blnretvalue = anywheresoftware.b4a.keywords.Common.False;
 };
 }else {
 //BA.debugLineNum = 491;BA.debugLine="blnRetValue = False";
_blnretvalue = anywheresoftware.b4a.keywords.Common.False;
 };
 } 
       catch (Exception e17) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e17); //BA.debugLineNum = 494;BA.debugLine="blnRetValue = False";
_blnretvalue = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 495;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("113762579",anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage(),0);
 };
 //BA.debugLineNum = 497;BA.debugLine="Return blnRetValue";
if (true) return _blnretvalue;
 //BA.debugLineNum = 498;BA.debugLine="End Sub";
return false;
}
public static boolean  _isminimumcons(anywheresoftware.b4a.BA _ba,int _iheaderid,int _itotcum) throws Exception{
String _sretval = "";
boolean _bretval = false;
 //BA.debugLineNum = 643;BA.debugLine="Public Sub IsMinimumCons(iHeaderID As Int, iTotCum";
 //BA.debugLineNum = 644;BA.debugLine="Dim SRetVal As String";
_sretval = "";
 //BA.debugLineNum = 645;BA.debugLine="Dim bRetVal As Boolean";
_bretval = false;
 //BA.debugLineNum = 646;BA.debugLine="Try";
try { //BA.debugLineNum = 647;BA.debugLine="SRetVal = Starter.DBCon.ExecQuerySingleResult(\"S";
_sretval = mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult("SELECT typecust FROM rates_details WHERE header_id = "+BA.NumberToString(_iheaderid)+" "+"AND ("+BA.NumberToString(_itotcum)+" BETWEEN rangefrom and rangeto)");
 //BA.debugLineNum = 654;BA.debugLine="If SRetVal = \"m\" Then";
if ((_sretval).equals("m")) { 
 //BA.debugLineNum = 655;BA.debugLine="bRetVal = True";
_bretval = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 657;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 };
 } 
       catch (Exception e11) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e11); //BA.debugLineNum = 663;BA.debugLine="ToastMessageShow($\"Unable to fetch Consumption T";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to fetch Consumption Type due to ")+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 664;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("114352405",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 //BA.debugLineNum = 665;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 667;BA.debugLine="Return bRetVal";
if (true) return _bretval;
 //BA.debugLineNum = 668;BA.debugLine="End Sub";
return false;
}
public static boolean  _isnoduedate(anywheresoftware.b4a.BA _ba,int _ibookid) throws Exception{
boolean _blnretval = false;
anywheresoftware.b4a.sql.SQL.CursorWrapper _rspca = null;
 //BA.debugLineNum = 867;BA.debugLine="Public Sub IsNoDueDate(iBookID As Int) As Boolean";
 //BA.debugLineNum = 868;BA.debugLine="Dim blnRetVal As Boolean";
_blnretval = false;
 //BA.debugLineNum = 869;BA.debugLine="Dim rsPCA As Cursor";
_rspca = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 870;BA.debugLine="Try";
try { //BA.debugLineNum = 871;BA.debugLine="Starter.strCriteria = \"SELECT * FROM tblBooks WH";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblBooks WHERE BookID = "+BA.NumberToString(_ibookid);
 //BA.debugLineNum = 872;BA.debugLine="rsPCA = Starter.DBCon.ExecQuery(Starter.strCrite";
_rspca = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 873;BA.debugLine="If rsPCA.RowCount > 0 Then";
if (_rspca.getRowCount()>0) { 
 //BA.debugLineNum = 874;BA.debugLine="rsPCA.Position=0";
_rspca.setPosition((int) (0));
 //BA.debugLineNum = 875;BA.debugLine="If rsPCA.GetInt(\"NoDueDate\") = 1 Then";
if (_rspca.GetInt("NoDueDate")==1) { 
 //BA.debugLineNum = 876;BA.debugLine="blnRetVal = True";
_blnretval = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 878;BA.debugLine="blnRetVal = False";
_blnretval = anywheresoftware.b4a.keywords.Common.False;
 };
 };
 } 
       catch (Exception e15) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e15); //BA.debugLineNum = 882;BA.debugLine="blnRetVal = True";
_blnretval = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 883;BA.debugLine="rsPCA.Close";
_rspca.Close();
 //BA.debugLineNum = 884;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("114745617",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 };
 //BA.debugLineNum = 886;BA.debugLine="rsPCA.Close";
_rspca.Close();
 //BA.debugLineNum = 887;BA.debugLine="Return blnRetVal";
if (true) return _blnretval;
 //BA.debugLineNum = 888;BA.debugLine="End Sub";
return false;
}
public static boolean  _ispasswordcorrect(anywheresoftware.b4a.BA _ba,String _susername,String _spass,int _ibranchid) throws Exception{
boolean _blnretval = false;
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsuser = null;
 //BA.debugLineNum = 184;BA.debugLine="Public Sub IsPasswordCorrect(sUserName As String,";
 //BA.debugLineNum = 185;BA.debugLine="Dim blnRetVal As Boolean";
_blnretval = false;
 //BA.debugLineNum = 186;BA.debugLine="Dim rsUser As Cursor";
_rsuser = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 187;BA.debugLine="Try";
try { //BA.debugLineNum = 188;BA.debugLine="Starter.strCriteria = \"SELECT * FROM tblUsers \"";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblUsers "+"WHERE UserName = '"+_susername+"' "+"AND BranchID = "+BA.NumberToString(_ibranchid)+" "+"LIMIT 1";
 //BA.debugLineNum = 192;BA.debugLine="rsUser = Starter.DBCon.ExecQuery(Starter.strCrit";
_rsuser = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 193;BA.debugLine="If rsUser.RowCount > 0 Then";
if (_rsuser.getRowCount()>0) { 
 //BA.debugLineNum = 194;BA.debugLine="rsUser.Position = 0";
_rsuser.setPosition((int) (0));
 //BA.debugLineNum = 195;BA.debugLine="If rsUser.GetString(\"UserPassword\") = sPass The";
if ((_rsuser.GetString("UserPassword")).equals(_spass)) { 
 //BA.debugLineNum = 196;BA.debugLine="blnRetVal = True";
_blnretval = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 198;BA.debugLine="blnRetVal = False";
_blnretval = anywheresoftware.b4a.keywords.Common.False;
 };
 }else {
 //BA.debugLineNum = 201;BA.debugLine="blnRetVal = False";
_blnretval = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 203;BA.debugLine="rsUser.Close";
_rsuser.Close();
 } 
       catch (Exception e18) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e18); //BA.debugLineNum = 205;BA.debugLine="blnRetVal = False";
_blnretval = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 206;BA.debugLine="ToastMessageShow(\"Unable to check if User's Exis";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Unable to check if User's Exists due to "+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 207;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("112910615",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 };
 //BA.debugLineNum = 209;BA.debugLine="Return blnRetVal";
if (true) return _blnretval;
 //BA.debugLineNum = 210;BA.debugLine="End Sub";
return false;
}
public static boolean  _ispcaupdate(anywheresoftware.b4a.BA _ba,int _ibookid) throws Exception{
boolean _bretval = false;
 //BA.debugLineNum = 511;BA.debugLine="Public Sub IsPCAUpdate(iBookID As Int) As Boolean";
 //BA.debugLineNum = 512;BA.debugLine="Dim bRetVal As Boolean";
_bretval = false;
 //BA.debugLineNum = 513;BA.debugLine="Dim rsTemp As Cursor";
_rstemp = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 514;BA.debugLine="Try";
try { //BA.debugLineNum = 515;BA.debugLine="Starter.strCriteria = \"SELECT * FROM tblBooks \"";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblBooks "+"WHERE tblBooks.BookID = "+BA.NumberToString(_ibookid);
 //BA.debugLineNum = 517;BA.debugLine="rsTemp = Starter.DBCon.ExecQuery(Starter.strCrite";
_rstemp = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 518;BA.debugLine="Log(Starter.strCriteria)";
anywheresoftware.b4a.keywords.Common.LogImpl("113893639",mostCurrent._starter._strcriteria /*String*/ ,0);
 //BA.debugLineNum = 520;BA.debugLine="If rsTemp.RowCount > 0 Then";
if (_rstemp.getRowCount()>0) { 
 //BA.debugLineNum = 521;BA.debugLine="rsTemp.Position = 0";
_rstemp.setPosition((int) (0));
 //BA.debugLineNum = 522;BA.debugLine="If rsTemp.GetInt(\"WasPCAUpdated\") = 1 Then";
if (_rstemp.GetInt("WasPCAUpdated")==1) { 
 //BA.debugLineNum = 523;BA.debugLine="bRetVal = True";
_bretval = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 525;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 };
 }else {
 //BA.debugLineNum = 528;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 };
 } 
       catch (Exception e18) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e18); //BA.debugLineNum = 531;BA.debugLine="bRetVal = False";
_bretval = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 532;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("113893653",anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage(),0);
 };
 //BA.debugLineNum = 534;BA.debugLine="Return bRetVal";
if (true) return _bretval;
 //BA.debugLineNum = 535;BA.debugLine="End Sub";
return false;
}
public static boolean  _istherebookassignments(anywheresoftware.b4a.BA _ba,int _ibranchid,int _ibillyear,int _ibillmonth,int _iuserid) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsassignments = null;
boolean _blnretval = false;
 //BA.debugLineNum = 440;BA.debugLine="Public Sub IsThereBookAssignments(iBranchID As Int";
 //BA.debugLineNum = 441;BA.debugLine="Dim rsAssignments As Cursor";
_rsassignments = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 442;BA.debugLine="Dim blnRetVal As Boolean";
_blnretval = false;
 //BA.debugLineNum = 443;BA.debugLine="Try";
try { //BA.debugLineNum = 444;BA.debugLine="Starter.strCriteria = \"SELECT * FROM tblBookAssi";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblBookAssignments "+"WHERE BranchID = "+BA.NumberToString(_ibranchid)+" "+"AND BillYear = "+BA.NumberToString(_ibillyear)+" "+"AND BillMonth = "+BA.NumberToString(_ibillmonth)+" "+"AND UserID = "+BA.NumberToString(_iuserid);
 //BA.debugLineNum = 449;BA.debugLine="rsAssignments = Starter.DBCon.ExecQuery(Starter.";
_rsassignments = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 450;BA.debugLine="If rsAssignments.RowCount <= 0 Then";
if (_rsassignments.getRowCount()<=0) { 
 //BA.debugLineNum = 451;BA.debugLine="blnRetVal = False";
_blnretval = anywheresoftware.b4a.keywords.Common.False;
 }else {
 //BA.debugLineNum = 453;BA.debugLine="blnRetVal = True";
_blnretval = anywheresoftware.b4a.keywords.Common.True;
 };
 } 
       catch (Exception e12) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e12); //BA.debugLineNum = 456;BA.debugLine="blnRetVal = False";
_blnretval = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 457;BA.debugLine="ToastMessageShow($\"Unable to check assigned book";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to check assigned book due to ")+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 458;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("113631506",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 };
 //BA.debugLineNum = 460;BA.debugLine="rsAssignments.Close";
_rsassignments.Close();
 //BA.debugLineNum = 461;BA.debugLine="Return blnRetVal";
if (true) return _blnretval;
 //BA.debugLineNum = 462;BA.debugLine="End Sub";
return false;
}
public static boolean  _isthereuserinfo(anywheresoftware.b4a.BA _ba,String _susername,String _suserpassword) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsuser = null;
boolean _blnretval = false;
 //BA.debugLineNum = 212;BA.debugLine="Public Sub IsThereUserInfo(sUserName As String, sU";
 //BA.debugLineNum = 213;BA.debugLine="Dim rsUser As Cursor";
_rsuser = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 214;BA.debugLine="Dim blnRetVal As Boolean";
_blnretval = false;
 //BA.debugLineNum = 215;BA.debugLine="Try";
try { //BA.debugLineNum = 216;BA.debugLine="Starter.strCriteria = \"SELECT * FROM tblUsers \"";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblUsers "+"WHERE UserName = '"+_susername+"' "+"AND UserPassword = '"+_suserpassword+"'";
 //BA.debugLineNum = 219;BA.debugLine="Log(Starter.strCriteria)";
anywheresoftware.b4a.keywords.Common.LogImpl("112976135",mostCurrent._starter._strcriteria /*String*/ ,0);
 //BA.debugLineNum = 220;BA.debugLine="rsUser = Starter.DBCon.ExecQuery(Starter.strCrit";
_rsuser = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 222;BA.debugLine="If rsUser.RowCount > 0 Then";
if (_rsuser.getRowCount()>0) { 
 //BA.debugLineNum = 223;BA.debugLine="rsUser.Position = 0";
_rsuser.setPosition((int) (0));
 //BA.debugLineNum = 224;BA.debugLine="GlobalVar.UserID = rsUser.GetInt(\"UserID\")";
mostCurrent._globalvar._userid /*int*/  = _rsuser.GetInt("UserID");
 //BA.debugLineNum = 225;BA.debugLine="GlobalVar.EmpName =rsUser.GetString(\"EmpName\")";
mostCurrent._globalvar._empname /*String*/  = _rsuser.GetString("EmpName");
 //BA.debugLineNum = 226;BA.debugLine="GlobalVar.UserName = rsUser.GetString(\"UserName";
mostCurrent._globalvar._username /*String*/  = _rsuser.GetString("UserName");
 //BA.debugLineNum = 227;BA.debugLine="GlobalVar.UserPW = rsUser.GetString(\"UserPasswo";
mostCurrent._globalvar._userpw /*String*/  = _rsuser.GetString("UserPassword");
 //BA.debugLineNum = 228;BA.debugLine="GlobalVar.Mod1 = rsUser.GetInt(\"Module1\")";
mostCurrent._globalvar._mod1 /*int*/  = _rsuser.GetInt("Module1");
 //BA.debugLineNum = 229;BA.debugLine="GlobalVar.Mod2 = rsUser.GetInt(\"Module2\")";
mostCurrent._globalvar._mod2 /*int*/  = _rsuser.GetInt("Module2");
 //BA.debugLineNum = 230;BA.debugLine="GlobalVar.Mod3 = rsUser.GetInt(\"Module3\")";
mostCurrent._globalvar._mod3 /*int*/  = _rsuser.GetInt("Module3");
 //BA.debugLineNum = 231;BA.debugLine="GlobalVar.Mod4 = rsUser.GetInt(\"Module4\")";
mostCurrent._globalvar._mod4 /*int*/  = _rsuser.GetInt("Module4");
 //BA.debugLineNum = 232;BA.debugLine="GlobalVar.Mod5 = rsUser.GetInt(\"Module5\")";
mostCurrent._globalvar._mod5 /*int*/  = _rsuser.GetInt("Module5");
 //BA.debugLineNum = 233;BA.debugLine="GlobalVar.Mod6 = rsUser.GetInt(\"Module6\")";
mostCurrent._globalvar._mod6 /*int*/  = _rsuser.GetInt("Module6");
 //BA.debugLineNum = 234;BA.debugLine="blnRetVal = True";
_blnretval = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 236;BA.debugLine="blnRetVal = False";
_blnretval = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 238;BA.debugLine="rsUser.Close";
_rsuser.Close();
 } 
       catch (Exception e25) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e25); //BA.debugLineNum = 240;BA.debugLine="blnRetVal = False";
_blnretval = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 241;BA.debugLine="ToastMessageShow($\"Unable to fetch User's Info d";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(("Unable to fetch User's Info due to ")+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 242;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("112976158",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 };
 //BA.debugLineNum = 244;BA.debugLine="Return blnRetVal";
if (true) return _blnretval;
 //BA.debugLineNum = 245;BA.debugLine="End Sub";
return false;
}
public static boolean  _isuserexists(anywheresoftware.b4a.BA _ba,String _susername,String _spassword,int _ibranchid) throws Exception{
boolean _blnretval = false;
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsuser = null;
 //BA.debugLineNum = 135;BA.debugLine="Public Sub IsUserExists(sUserName As String, sPass";
 //BA.debugLineNum = 136;BA.debugLine="Dim blnRetVal As Boolean";
_blnretval = false;
 //BA.debugLineNum = 137;BA.debugLine="Dim rsUser As Cursor";
_rsuser = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 139;BA.debugLine="Try";
try { //BA.debugLineNum = 140;BA.debugLine="Starter.strCriteria = \"SELECT * FROM tblUsers \"";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblUsers "+"WHERE UserName = '"+_susername+"' "+"AND UserPassword = '"+_spassword+"' "+"AND BranchID = "+BA.NumberToString(_ibranchid);
 //BA.debugLineNum = 145;BA.debugLine="rsUser = Starter.DBCon.ExecQuery(Starter.strCrit";
_rsuser = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 147;BA.debugLine="If rsUser.RowCount > 0 Then";
if (_rsuser.getRowCount()>0) { 
 //BA.debugLineNum = 148;BA.debugLine="blnRetVal = True";
_blnretval = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 150;BA.debugLine="blnRetVal = False";
_blnretval = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 152;BA.debugLine="rsUser.Close";
_rsuser.Close();
 } 
       catch (Exception e13) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e13); //BA.debugLineNum = 155;BA.debugLine="blnRetVal = False";
_blnretval = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 156;BA.debugLine="ToastMessageShow(\"Unable to check if User Exists";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Unable to check if User Exists due to "+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 157;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("112779542",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 };
 //BA.debugLineNum = 159;BA.debugLine="Return blnRetVal";
if (true) return _blnretval;
 //BA.debugLineNum = 160;BA.debugLine="End Sub";
return false;
}
public static boolean  _isusernameexists(anywheresoftware.b4a.BA _ba,String _susername,int _ibranchid) throws Exception{
boolean _blnretval = false;
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsuser = null;
 //BA.debugLineNum = 162;BA.debugLine="Public Sub IsUserNameExists(sUserName As String, i";
 //BA.debugLineNum = 163;BA.debugLine="Dim blnRetVal As Boolean";
_blnretval = false;
 //BA.debugLineNum = 164;BA.debugLine="Dim rsUser As Cursor";
_rsuser = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 165;BA.debugLine="Try";
try { //BA.debugLineNum = 166;BA.debugLine="Starter.strCriteria = \"SELECT * FROM tblUsers \"";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblUsers "+"WHERE UserName = '"+_susername+"' "+"AND BranchID = "+BA.NumberToString(_ibranchid);
 //BA.debugLineNum = 169;BA.debugLine="rsUser = Starter.DBCon.ExecQuery(Starter.strCrit";
_rsuser = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 170;BA.debugLine="If rsUser.RowCount > 0 Then";
if (_rsuser.getRowCount()>0) { 
 //BA.debugLineNum = 171;BA.debugLine="blnRetVal = True";
_blnretval = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 173;BA.debugLine="blnRetVal = False";
_blnretval = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 175;BA.debugLine="rsUser.Close";
_rsuser.Close();
 } 
       catch (Exception e13) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e13); //BA.debugLineNum = 177;BA.debugLine="blnRetVal = False";
_blnretval = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 178;BA.debugLine="ToastMessageShow(\"Unable to check if User's Exis";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Unable to check if User's Exists due to "+anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage()),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 179;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("112845073",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 };
 //BA.debugLineNum = 181;BA.debugLine="Return blnRetVal";
if (true) return _blnretval;
 //BA.debugLineNum = 182;BA.debugLine="End Sub";
return false;
}
public static boolean  _iswithduedate(anywheresoftware.b4a.BA _ba,int _ireadid) throws Exception{
boolean _blnretval = false;
anywheresoftware.b4a.sql.SQL.CursorWrapper _rswithduedate = null;
 //BA.debugLineNum = 890;BA.debugLine="Public Sub IsWithDueDate(iReadID As Int) As Boolea";
 //BA.debugLineNum = 891;BA.debugLine="Dim blnRetVal As Boolean";
_blnretval = false;
 //BA.debugLineNum = 892;BA.debugLine="Dim rsWithDueDate As Cursor";
_rswithduedate = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 893;BA.debugLine="Try";
try { //BA.debugLineNum = 894;BA.debugLine="Starter.strCriteria = \"SELECT * FROM tblReadings";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM tblReadings WHERE ReadID = "+BA.NumberToString(_ireadid);
 //BA.debugLineNum = 895;BA.debugLine="rsWithDueDate = Starter.DBCon.ExecQuery(Starter.";
_rswithduedate = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 896;BA.debugLine="If rsWithDueDate.RowCount > 0 Then";
if (_rswithduedate.getRowCount()>0) { 
 //BA.debugLineNum = 897;BA.debugLine="rsWithDueDate.Position=0";
_rswithduedate.setPosition((int) (0));
 //BA.debugLineNum = 898;BA.debugLine="If rsWithDueDate.GetInt(\"WithDueDate\") = 1 Then";
if (_rswithduedate.GetInt("WithDueDate")==1) { 
 //BA.debugLineNum = 899;BA.debugLine="blnRetVal = True";
_blnretval = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 901;BA.debugLine="blnRetVal = False";
_blnretval = anywheresoftware.b4a.keywords.Common.False;
 };
 };
 } 
       catch (Exception e15) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e15); //BA.debugLineNum = 905;BA.debugLine="blnRetVal = True";
_blnretval = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 906;BA.debugLine="rsWithDueDate.Close";
_rswithduedate.Close();
 //BA.debugLineNum = 907;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("114811153",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 };
 //BA.debugLineNum = 909;BA.debugLine="rsWithDueDate.Close";
_rswithduedate.Close();
 //BA.debugLineNum = 910;BA.debugLine="Return blnRetVal";
if (true) return _blnretval;
 //BA.debugLineNum = 911;BA.debugLine="End Sub";
return false;
}
public static boolean  _iswithinrange(anywheresoftware.b4a.BA _ba,int _iprescum,int _ibranchid,String _sclass,String _ssubclass) throws Exception{
boolean _blnretval = false;
String _sdate = "";
String _sbilldate = "";
int _i = 0;
String _billtype = "";
double _billamt = 0;
int _rangefrom = 0;
int _rangeto = 0;
int _excesscum = 0;
double _basicamt = 0;
int _prescum = 0;
anywheresoftware.b4a.sql.SQL.CursorWrapper _rsrate = null;
double _retval = 0;
 //BA.debugLineNum = 818;BA.debugLine="Public Sub IsWithinRange(iPresCum As Int, iBranchI";
 //BA.debugLineNum = 819;BA.debugLine="Dim blnRetVal As Boolean";
_blnretval = false;
 //BA.debugLineNum = 820;BA.debugLine="Dim sDate, sBillDate As String";
_sdate = "";
_sbilldate = "";
 //BA.debugLineNum = 821;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 822;BA.debugLine="Dim BillType As String";
_billtype = "";
 //BA.debugLineNum = 823;BA.debugLine="Dim BillAmt As Double";
_billamt = 0;
 //BA.debugLineNum = 824;BA.debugLine="Dim RangeFrom, RangeTo As Int";
_rangefrom = 0;
_rangeto = 0;
 //BA.debugLineNum = 825;BA.debugLine="Dim ExcessCum As Int";
_excesscum = 0;
 //BA.debugLineNum = 826;BA.debugLine="Dim BasicAmt As Double";
_basicamt = 0;
 //BA.debugLineNum = 827;BA.debugLine="Dim PresCum As Int";
_prescum = 0;
 //BA.debugLineNum = 828;BA.debugLine="Dim rsRate As Cursor";
_rsrate = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 829;BA.debugLine="Dim RetVal As Double";
_retval = 0;
 //BA.debugLineNum = 834;BA.debugLine="BillType = \"\"";
_billtype = "";
 //BA.debugLineNum = 835;BA.debugLine="BillAmt = 0";
_billamt = 0;
 //BA.debugLineNum = 836;BA.debugLine="PresCum = 0";
_prescum = (int) (0);
 //BA.debugLineNum = 837;BA.debugLine="ExcessCum = 0";
_excesscum = (int) (0);
 //BA.debugLineNum = 838;BA.debugLine="BasicAmt = 0";
_basicamt = 0;
 //BA.debugLineNum = 840;BA.debugLine="Try";
try { //BA.debugLineNum = 842;BA.debugLine="Starter.strCriteria = \"SELECT id FROM rates_head";
mostCurrent._starter._strcriteria /*String*/  = "SELECT id FROM rates_header WHERE branch_id = "+BA.NumberToString(_ibranchid)+" "+"AND class = '"+_sclass+"' "+"AND sub_class = '"+_ssubclass+"' "+"ORDER BY date_from DESC LIMIT 1";
 //BA.debugLineNum = 846;BA.debugLine="Log(Starter.strCriteria)";
anywheresoftware.b4a.keywords.Common.LogImpl("114680092",mostCurrent._starter._strcriteria /*String*/ ,0);
 //BA.debugLineNum = 847;BA.debugLine="RetVal = Starter.DBCon.ExecQuerySingleResult(Sta";
_retval = (double)(Double.parseDouble(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuerySingleResult(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 848;BA.debugLine="Log(RetVal)";
anywheresoftware.b4a.keywords.Common.LogImpl("114680094",BA.NumberToString(_retval),0);
 //BA.debugLineNum = 851;BA.debugLine="Starter.strCriteria = \"SELECT * from rates_detai";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * from rates_details WHERE header_id = "+BA.NumberToString(_retval)+" "+"AND "+BA.NumberToString(_iprescum)+" BETWEEN rangefrom And rangeto";
 //BA.debugLineNum = 854;BA.debugLine="rsRate = Starter.DBCon.ExecQuery(Starter.strCrit";
_rsrate = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 855;BA.debugLine="If rsRate.RowCount > 0 Then";
if (_rsrate.getRowCount()>0) { 
 //BA.debugLineNum = 856;BA.debugLine="blnRetVal = True";
_blnretval = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 858;BA.debugLine="blnRetVal = False";
_blnretval = anywheresoftware.b4a.keywords.Common.False;
 };
 } 
       catch (Exception e30) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e30); //BA.debugLineNum = 861;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("114680107",anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage(),0);
 //BA.debugLineNum = 862;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 864;BA.debugLine="Return blnRetVal";
if (true) return _blnretval;
 //BA.debugLineNum = 865;BA.debugLine="End Sub";
return false;
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 1;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 2;BA.debugLine="Public rsTemp As Cursor";
_rstemp = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 3;BA.debugLine="Dim xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 4;BA.debugLine="End Sub";
return "";
}
public static boolean  _saverecno(anywheresoftware.b4a.BA _ba,int _ibookid) throws Exception{
boolean _blnretval = false;
long _lngrec = 0L;
 //BA.debugLineNum = 417;BA.debugLine="Sub SaveRecNo(iBookID As Int) As Boolean";
 //BA.debugLineNum = 418;BA.debugLine="Dim blnRetVal As Boolean";
_blnretval = false;
 //BA.debugLineNum = 419;BA.debugLine="Dim lngRec As Long";
_lngrec = 0L;
 //BA.debugLineNum = 420;BA.debugLine="Try";
try { //BA.debugLineNum = 421;BA.debugLine="Starter.strCriteria = \"SELECT * FROM NewSequence";
mostCurrent._starter._strcriteria /*String*/  = "SELECT * FROM NewSequence WHERE BookID = "+BA.NumberToString(_ibookid);
 //BA.debugLineNum = 422;BA.debugLine="rsTemp = Starter.dbcon.ExecQuery(Starter.strCrit";
_rstemp = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(mostCurrent._starter._strcriteria /*String*/ )));
 //BA.debugLineNum = 423;BA.debugLine="If rsTemp.RowCount=0 Then";
if (_rstemp.getRowCount()==0) { 
 //BA.debugLineNum = 424;BA.debugLine="Starter.dbcon.ExecNonQuery2(\"INSERT INTO NewSeq";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2("INSERT INTO NewSequence VALUES (?,?)",anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(_ibookid),(Object)(1)}));
 }else {
 //BA.debugLineNum = 426;BA.debugLine="rsTemp.Position=0";
_rstemp.setPosition((int) (0));
 //BA.debugLineNum = 427;BA.debugLine="lngRec = rsTemp.GetLong(\"LastSeqNo\") + 1";
_lngrec = (long) (_rstemp.GetLong("LastSeqNo")+1);
 //BA.debugLineNum = 428;BA.debugLine="Starter.strCriteria=\"UPDATE NewSequence SET Las";
mostCurrent._starter._strcriteria /*String*/  = "UPDATE NewSequence SET LastSeqNo = ? WHERE BookID = "+BA.NumberToString(_ibookid);
 //BA.debugLineNum = 429;BA.debugLine="Starter.dbcon.ExecNonQuery2(Starter.strCriteria";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2(mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{BA.NumberToString(_lngrec)}));
 };
 //BA.debugLineNum = 431;BA.debugLine="blnRetVal=True";
_blnretval = anywheresoftware.b4a.keywords.Common.True;
 } 
       catch (Exception e16) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e16); //BA.debugLineNum = 433;BA.debugLine="blnRetVal = False";
_blnretval = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 434;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("113565969",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 };
 //BA.debugLineNum = 436;BA.debugLine="Return blnRetVal";
if (true) return _blnretval;
 //BA.debugLineNum = 437;BA.debugLine="End Sub";
return false;
}
public static String  _saveuserhistory(anywheresoftware.b4a.BA _ba,int _iuserid) throws Exception{
 //BA.debugLineNum = 351;BA.debugLine="Public Sub SaveUserHistory(iUserID As Int)";
 //BA.debugLineNum = 353;BA.debugLine="End Sub";
return "";
}
public static String  _updatepca(anywheresoftware.b4a.BA _ba,int _ibookid,double _iamt) throws Exception{
 //BA.debugLineNum = 536;BA.debugLine="Public Sub UpdatePCA(iBookID As Int, iAmt As Doubl";
 //BA.debugLineNum = 537;BA.debugLine="Starter.DBCon.BeginTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .BeginTransaction();
 //BA.debugLineNum = 538;BA.debugLine="Try";
try { //BA.debugLineNum = 539;BA.debugLine="Starter.strCriteria=\"UPDATE tblReadings SET PCA";
mostCurrent._starter._strcriteria /*String*/  = "UPDATE tblReadings SET PCA = ? WHERE BookID = "+BA.NumberToString(_ibookid);
 //BA.debugLineNum = 540;BA.debugLine="Starter.DBCon.ExecNonQuery2(Starter.strCriteria,";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2(mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{BA.NumberToString(_iamt)}));
 //BA.debugLineNum = 541;BA.debugLine="Starter.strCriteria=\"UPDATE tblBooks SET WasPCAU";
mostCurrent._starter._strcriteria /*String*/  = "UPDATE tblBooks SET WasPCAUpdated = ? WHERE BookID = "+BA.NumberToString(_ibookid);
 //BA.debugLineNum = 542;BA.debugLine="Starter.DBCon.ExecNonQuery2(Starter.strCriteria,";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery2(mostCurrent._starter._strcriteria /*String*/ ,anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{("1")}));
 //BA.debugLineNum = 543;BA.debugLine="Starter.DBCon.TransactionSuccessful";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .TransactionSuccessful();
 } 
       catch (Exception e9) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e9); //BA.debugLineNum = 545;BA.debugLine="Log(LastException.Message)";
anywheresoftware.b4a.keywords.Common.LogImpl("113959177",anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage(),0);
 };
 //BA.debugLineNum = 547;BA.debugLine="Starter.DBCon.EndTransaction";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .EndTransaction();
 //BA.debugLineNum = 548;BA.debugLine="End Sub";
return "";
}
public static String  _zapmeterreader(anywheresoftware.b4a.BA _ba) throws Exception{
 //BA.debugLineNum = 464;BA.debugLine="Public Sub ZapMeterReader()";
 //BA.debugLineNum = 465;BA.debugLine="Try";
try { //BA.debugLineNum = 466;BA.debugLine="Starter.strCriteria=\"DELETE FROM tblReaders\"";
mostCurrent._starter._strcriteria /*String*/  = "DELETE FROM tblReaders";
 //BA.debugLineNum = 467;BA.debugLine="Starter.DBCon.ExecNonQuery(Starter.strCriteria)";
mostCurrent._starter._dbcon /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery(mostCurrent._starter._strcriteria /*String*/ );
 } 
       catch (Exception e5) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e5); //BA.debugLineNum = 469;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("113697029",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 };
 //BA.debugLineNum = 471;BA.debugLine="End Sub";
return "";
}
}
