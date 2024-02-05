package com.bwsi.MeterReader;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class dbutils {
private static dbutils mostCurrent = new dbutils();
public static Object getObject() {
    throw new RuntimeException("Code module does not support this method.");
}
 public anywheresoftware.b4a.keywords.Common __c = null;
public static String _db_real = "";
public static String _db_integer = "";
public static String _db_blob = "";
public static String _db_text = "";
public static String _htmlcss = "";
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
public com.bwsi.MeterReader.globalvar _globalvar = null;
public com.bwsi.MeterReader.modvariables _modvariables = null;
public com.bwsi.MeterReader.newcam _newcam = null;
public com.bwsi.MeterReader.readingbooks _readingbooks = null;
public com.bwsi.MeterReader.readingsettings _readingsettings = null;
public com.bwsi.MeterReader.readingvalidation _readingvalidation = null;
public com.bwsi.MeterReader.starter _starter = null;
public com.bwsi.MeterReader.useraccountsettings _useraccountsettings = null;
public com.bwsi.MeterReader.validationrptgenerator _validationrptgenerator = null;
public static String  _copydbfromassets(anywheresoftware.b4a.BA _ba,String _filename) throws Exception{
String _targetdir = "";
 //BA.debugLineNum = 22;BA.debugLine="Public Sub CopyDBFromAssets (FileName As String) A";
 //BA.debugLineNum = 23;BA.debugLine="Dim TargetDir As String";
_targetdir = "";
 //BA.debugLineNum = 24;BA.debugLine="If File.ExternalWritable Then TargetDir = File.Di";
if (anywheresoftware.b4a.keywords.Common.File.getExternalWritable()) { 
_targetdir = anywheresoftware.b4a.keywords.Common.File.getDirDefaultExternal();}
else {
_targetdir = anywheresoftware.b4a.keywords.Common.File.getDirInternal();};
 //BA.debugLineNum = 26;BA.debugLine="If File.Exists(TargetDir, FileName) = False Then";
if (anywheresoftware.b4a.keywords.Common.File.Exists(_targetdir,_filename)==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 27;BA.debugLine="File.Copy(File.DirAssets, FileName, TargetDir, F";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),_filename,_targetdir,_filename);
 };
 //BA.debugLineNum = 29;BA.debugLine="Return TargetDir";
if (true) return _targetdir;
 //BA.debugLineNum = 30;BA.debugLine="End Sub";
return "";
}
public static String  _createtable(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _tablename,anywheresoftware.b4a.objects.collections.Map _fieldsandtypes,String _primarykey) throws Exception{
anywheresoftware.b4a.keywords.StringBuilderWrapper _sb = null;
int _i = 0;
String _field = "";
String _ftype = "";
String _query = "";
 //BA.debugLineNum = 36;BA.debugLine="Public Sub CreateTable(SQL As SQL, TableName As St";
 //BA.debugLineNum = 37;BA.debugLine="Dim sb As StringBuilder";
_sb = new anywheresoftware.b4a.keywords.StringBuilderWrapper();
 //BA.debugLineNum = 38;BA.debugLine="sb.Initialize";
_sb.Initialize();
 //BA.debugLineNum = 39;BA.debugLine="sb.Append(\"(\")";
_sb.Append("(");
 //BA.debugLineNum = 40;BA.debugLine="For i = 0 To FieldsAndTypes.Size - 1";
{
final int step4 = 1;
final int limit4 = (int) (_fieldsandtypes.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit4 ;_i = _i + step4 ) {
 //BA.debugLineNum = 41;BA.debugLine="Dim field, ftype As String";
_field = "";
_ftype = "";
 //BA.debugLineNum = 42;BA.debugLine="field = FieldsAndTypes.GetKeyAt(i)";
_field = BA.ObjectToString(_fieldsandtypes.GetKeyAt(_i));
 //BA.debugLineNum = 43;BA.debugLine="ftype = FieldsAndTypes.GetValueAt(i)";
_ftype = BA.ObjectToString(_fieldsandtypes.GetValueAt(_i));
 //BA.debugLineNum = 44;BA.debugLine="If i > 0 Then sb.Append(\", \")";
if (_i>0) { 
_sb.Append(", ");};
 //BA.debugLineNum = 45;BA.debugLine="sb.Append(\"[\").Append(field).Append(\"] \").Append";
_sb.Append("[").Append(_field).Append("] ").Append(_ftype);
 //BA.debugLineNum = 46;BA.debugLine="If field = PrimaryKey Then sb.Append(\" PRIMARY K";
if ((_field).equals(_primarykey)) { 
_sb.Append(" PRIMARY KEY");};
 }
};
 //BA.debugLineNum = 48;BA.debugLine="sb.Append(\")\")";
_sb.Append(")");
 //BA.debugLineNum = 49;BA.debugLine="Dim query As String";
_query = "";
 //BA.debugLineNum = 50;BA.debugLine="query = \"CREATE TABLE IF NOT EXISTS [\" & TableNam";
_query = "CREATE TABLE IF NOT EXISTS ["+_tablename+"] "+_sb.ToString();
 //BA.debugLineNum = 51;BA.debugLine="Log(\"CreateTable: \" & query)";
anywheresoftware.b4a.keywords.Common.LogImpl("148693263","CreateTable: "+_query,0);
 //BA.debugLineNum = 52;BA.debugLine="SQL.ExecNonQuery(query)";
_sql.ExecNonQuery(_query);
 //BA.debugLineNum = 53;BA.debugLine="End Sub";
return "";
}
public static String  _deleterecord(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _tablename,anywheresoftware.b4a.objects.collections.Map _wherefieldequals) throws Exception{
anywheresoftware.b4a.keywords.StringBuilderWrapper _sb = null;
anywheresoftware.b4a.objects.collections.List _args = null;
int _i = 0;
 //BA.debugLineNum = 383;BA.debugLine="Public Sub DeleteRecord(SQL As SQL, TableName As S";
 //BA.debugLineNum = 384;BA.debugLine="Dim sb As StringBuilder";
_sb = new anywheresoftware.b4a.keywords.StringBuilderWrapper();
 //BA.debugLineNum = 385;BA.debugLine="sb.Initialize";
_sb.Initialize();
 //BA.debugLineNum = 386;BA.debugLine="sb.Append(\"DELETE FROM [\").Append(TableName).Appe";
_sb.Append("DELETE FROM [").Append(_tablename).Append("] WHERE ");
 //BA.debugLineNum = 387;BA.debugLine="If WhereFieldEquals.Size = 0 Then";
if (_wherefieldequals.getSize()==0) { 
 //BA.debugLineNum = 388;BA.debugLine="Log(\"WhereFieldEquals map empty!\")";
anywheresoftware.b4a.keywords.Common.LogImpl("149545221","WhereFieldEquals map empty!",0);
 //BA.debugLineNum = 389;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 391;BA.debugLine="Dim args As List";
_args = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 392;BA.debugLine="args.Initialize";
_args.Initialize();
 //BA.debugLineNum = 393;BA.debugLine="For i = 0 To WhereFieldEquals.Size - 1";
{
final int step10 = 1;
final int limit10 = (int) (_wherefieldequals.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit10 ;_i = _i + step10 ) {
 //BA.debugLineNum = 394;BA.debugLine="If i > 0 Then sb.Append(\" AND \")";
if (_i>0) { 
_sb.Append(" AND ");};
 //BA.debugLineNum = 395;BA.debugLine="sb.Append(\"[\").Append(WhereFieldEquals.GetKeyAt(";
_sb.Append("[").Append(BA.ObjectToString(_wherefieldequals.GetKeyAt(_i))).Append("] = ?");
 //BA.debugLineNum = 396;BA.debugLine="args.Add(WhereFieldEquals.GetValueAt(i))";
_args.Add(_wherefieldequals.GetValueAt(_i));
 }
};
 //BA.debugLineNum = 398;BA.debugLine="Log(\"DeleteRecord: \" & sb.ToString)";
anywheresoftware.b4a.keywords.Common.LogImpl("149545231","DeleteRecord: "+_sb.ToString(),0);
 //BA.debugLineNum = 399;BA.debugLine="SQL.ExecNonQuery2(sb.ToString, args)";
_sql.ExecNonQuery2(_sb.ToString(),_args);
 //BA.debugLineNum = 400;BA.debugLine="End Sub";
return "";
}
public static String  _droptable(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _tablename) throws Exception{
String _query = "";
 //BA.debugLineNum = 56;BA.debugLine="Public Sub DropTable(SQL As SQL, TableName As Stri";
 //BA.debugLineNum = 57;BA.debugLine="Dim query As String";
_query = "";
 //BA.debugLineNum = 58;BA.debugLine="query = \"DROP TABLE IF EXISTS [\" & TableName & \"]";
_query = "DROP TABLE IF EXISTS ["+_tablename+"]";
 //BA.debugLineNum = 59;BA.debugLine="Log(\"DropTable: \" & query)";
anywheresoftware.b4a.keywords.Common.LogImpl("148758787","DropTable: "+_query,0);
 //BA.debugLineNum = 60;BA.debugLine="SQL.ExecNonQuery(query)";
_sql.ExecNonQuery(_query);
 //BA.debugLineNum = 61;BA.debugLine="End Sub";
return "";
}
public static String  _executehtml(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _query,String[] _stringargs,int _limit,boolean _clickable) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _cur = null;
anywheresoftware.b4a.keywords.StringBuilderWrapper _sb = null;
int _i = 0;
int _row = 0;
 //BA.debugLineNum = 303;BA.debugLine="Public Sub ExecuteHtml(SQL As SQL, Query As String";
 //BA.debugLineNum = 304;BA.debugLine="Dim cur As Cursor";
_cur = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 305;BA.debugLine="If StringArgs <> Null Then";
if (_stringargs!= null) { 
 //BA.debugLineNum = 306;BA.debugLine="cur = SQL.ExecQuery2(Query, StringArgs)";
_cur = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(_sql.ExecQuery2(_query,_stringargs)));
 }else {
 //BA.debugLineNum = 308;BA.debugLine="cur = SQL.ExecQuery(Query)";
_cur = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(_sql.ExecQuery(_query)));
 };
 //BA.debugLineNum = 310;BA.debugLine="Log(\"ExecuteHtml: \" & Query)";
anywheresoftware.b4a.keywords.Common.LogImpl("149348615","ExecuteHtml: "+_query,0);
 //BA.debugLineNum = 311;BA.debugLine="If Limit > 0 Then Limit = Min(Limit, cur.RowCount";
if (_limit>0) { 
_limit = (int) (anywheresoftware.b4a.keywords.Common.Min(_limit,_cur.getRowCount()));}
else {
_limit = _cur.getRowCount();};
 //BA.debugLineNum = 312;BA.debugLine="Dim sb As StringBuilder";
_sb = new anywheresoftware.b4a.keywords.StringBuilderWrapper();
 //BA.debugLineNum = 313;BA.debugLine="sb.Initialize";
_sb.Initialize();
 //BA.debugLineNum = 314;BA.debugLine="sb.Append(\"<html><body>\").Append(CRLF)";
_sb.Append("<html><body>").Append(anywheresoftware.b4a.keywords.Common.CRLF);
 //BA.debugLineNum = 315;BA.debugLine="sb.Append(\"<style type='text/css'>\").Append(HtmlC";
_sb.Append("<style type='text/css'>").Append(_htmlcss).Append("</style>").Append(anywheresoftware.b4a.keywords.Common.CRLF);
 //BA.debugLineNum = 316;BA.debugLine="sb.Append(\"<table><tr>\").Append(CRLF)";
_sb.Append("<table><tr>").Append(anywheresoftware.b4a.keywords.Common.CRLF);
 //BA.debugLineNum = 317;BA.debugLine="For i = 0 To cur.ColumnCount - 1";
{
final int step14 = 1;
final int limit14 = (int) (_cur.getColumnCount()-1);
_i = (int) (0) ;
for (;_i <= limit14 ;_i = _i + step14 ) {
 //BA.debugLineNum = 318;BA.debugLine="sb.Append(\"<th>\").Append(cur.GetColumnName(i)).A";
_sb.Append("<th>").Append(_cur.GetColumnName(_i)).Append("</th>");
 }
};
 //BA.debugLineNum = 329;BA.debugLine="sb.Append(\"</tr>\").Append(CRLF)";
_sb.Append("</tr>").Append(anywheresoftware.b4a.keywords.Common.CRLF);
 //BA.debugLineNum = 330;BA.debugLine="For row = 0 To Limit - 1";
{
final int step18 = 1;
final int limit18 = (int) (_limit-1);
_row = (int) (0) ;
for (;_row <= limit18 ;_row = _row + step18 ) {
 //BA.debugLineNum = 331;BA.debugLine="cur.Position = row";
_cur.setPosition(_row);
 //BA.debugLineNum = 332;BA.debugLine="If row Mod 2 = 0 Then";
if (_row%2==0) { 
 //BA.debugLineNum = 333;BA.debugLine="sb.Append(\"<tr>\")";
_sb.Append("<tr>");
 }else {
 //BA.debugLineNum = 335;BA.debugLine="sb.Append(\"<tr class='odd'>\")";
_sb.Append("<tr class='odd'>");
 };
 //BA.debugLineNum = 337;BA.debugLine="For i = 0 To cur.ColumnCount - 1";
{
final int step25 = 1;
final int limit25 = (int) (_cur.getColumnCount()-1);
_i = (int) (0) ;
for (;_i <= limit25 ;_i = _i + step25 ) {
 //BA.debugLineNum = 338;BA.debugLine="sb.Append(\"<td>\")";
_sb.Append("<td>");
 //BA.debugLineNum = 339;BA.debugLine="If Clickable Then";
if (_clickable) { 
 //BA.debugLineNum = 340;BA.debugLine="sb.Append(\"<a href='http://\").Append(i).Append";
_sb.Append("<a href='http://").Append(BA.NumberToString(_i)).Append(".");
 //BA.debugLineNum = 341;BA.debugLine="sb.Append(row)";
_sb.Append(BA.NumberToString(_row));
 //BA.debugLineNum = 342;BA.debugLine="sb.Append(\".com'>\").Append(cur.GetString2(i)).";
_sb.Append(".com'>").Append(_cur.GetString2(_i)).Append("</a>");
 }else {
 //BA.debugLineNum = 344;BA.debugLine="sb.Append(cur.GetString2(i))";
_sb.Append(_cur.GetString2(_i));
 };
 //BA.debugLineNum = 346;BA.debugLine="sb.Append(\"</td>\")";
_sb.Append("</td>");
 }
};
 //BA.debugLineNum = 348;BA.debugLine="sb.Append(\"</tr>\").Append(CRLF)";
_sb.Append("</tr>").Append(anywheresoftware.b4a.keywords.Common.CRLF);
 }
};
 //BA.debugLineNum = 350;BA.debugLine="cur.Close";
_cur.Close();
 //BA.debugLineNum = 351;BA.debugLine="sb.Append(\"</table></body></html>\")";
_sb.Append("</table></body></html>");
 //BA.debugLineNum = 352;BA.debugLine="Return sb.ToString";
if (true) return _sb.ToString();
 //BA.debugLineNum = 353;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.collections.Map  _executejson(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _query,String[] _stringargs,int _limit,anywheresoftware.b4a.objects.collections.List _dbtypes) throws Exception{
anywheresoftware.b4a.objects.collections.List _table = null;
anywheresoftware.b4a.sql.SQL.CursorWrapper _cur = null;
int _row = 0;
anywheresoftware.b4a.objects.collections.Map _m = null;
int _i = 0;
anywheresoftware.b4a.objects.collections.Map _root = null;
 //BA.debugLineNum = 264;BA.debugLine="Public Sub ExecuteJSON (SQL As SQL, Query As Strin";
 //BA.debugLineNum = 265;BA.debugLine="Dim table As List";
_table = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 266;BA.debugLine="Dim cur As Cursor";
_cur = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 267;BA.debugLine="If StringArgs <> Null Then";
if (_stringargs!= null) { 
 //BA.debugLineNum = 268;BA.debugLine="cur = SQL.ExecQuery2(Query, StringArgs)";
_cur = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(_sql.ExecQuery2(_query,_stringargs)));
 }else {
 //BA.debugLineNum = 270;BA.debugLine="cur = SQL.ExecQuery(Query)";
_cur = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(_sql.ExecQuery(_query)));
 };
 //BA.debugLineNum = 272;BA.debugLine="Log(\"ExecuteJSON: \" & Query)";
anywheresoftware.b4a.keywords.Common.LogImpl("149283080","ExecuteJSON: "+_query,0);
 //BA.debugLineNum = 273;BA.debugLine="Dim table As List";
_table = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 274;BA.debugLine="table.Initialize";
_table.Initialize();
 //BA.debugLineNum = 275;BA.debugLine="If Limit > 0 Then Limit = Min(Limit, cur.RowCount";
if (_limit>0) { 
_limit = (int) (anywheresoftware.b4a.keywords.Common.Min(_limit,_cur.getRowCount()));}
else {
_limit = _cur.getRowCount();};
 //BA.debugLineNum = 276;BA.debugLine="For row = 0 To Limit - 1";
{
final int step12 = 1;
final int limit12 = (int) (_limit-1);
_row = (int) (0) ;
for (;_row <= limit12 ;_row = _row + step12 ) {
 //BA.debugLineNum = 277;BA.debugLine="cur.Position = row";
_cur.setPosition(_row);
 //BA.debugLineNum = 278;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 279;BA.debugLine="m.Initialize";
_m.Initialize();
 //BA.debugLineNum = 280;BA.debugLine="For i = 0 To cur.ColumnCount - 1";
{
final int step16 = 1;
final int limit16 = (int) (_cur.getColumnCount()-1);
_i = (int) (0) ;
for (;_i <= limit16 ;_i = _i + step16 ) {
 //BA.debugLineNum = 281;BA.debugLine="Select DBTypes.Get(i)";
switch (BA.switchObjectToInt(_dbtypes.Get(_i),(Object)(_db_text),(Object)(_db_integer),(Object)(_db_real))) {
case 0: {
 //BA.debugLineNum = 283;BA.debugLine="m.Put(cur.GetColumnName(i), cur.GetString2(i)";
_m.Put((Object)(_cur.GetColumnName(_i)),(Object)(_cur.GetString2(_i)));
 break; }
case 1: {
 //BA.debugLineNum = 285;BA.debugLine="m.Put(cur.GetColumnName(i), cur.GetLong2(i))";
_m.Put((Object)(_cur.GetColumnName(_i)),(Object)(_cur.GetLong2(_i)));
 break; }
case 2: {
 //BA.debugLineNum = 287;BA.debugLine="m.Put(cur.GetColumnName(i), cur.GetDouble2(i)";
_m.Put((Object)(_cur.GetColumnName(_i)),(Object)(_cur.GetDouble2(_i)));
 break; }
default: {
 //BA.debugLineNum = 289;BA.debugLine="Log(\"Invalid type: \" & DBTypes.Get(i))";
anywheresoftware.b4a.keywords.Common.LogImpl("149283097","Invalid type: "+BA.ObjectToString(_dbtypes.Get(_i)),0);
 break; }
}
;
 }
};
 //BA.debugLineNum = 292;BA.debugLine="table.Add(m)";
_table.Add((Object)(_m.getObject()));
 }
};
 //BA.debugLineNum = 294;BA.debugLine="cur.Close";
_cur.Close();
 //BA.debugLineNum = 295;BA.debugLine="Dim root As Map";
_root = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 296;BA.debugLine="root.Initialize";
_root.Initialize();
 //BA.debugLineNum = 297;BA.debugLine="root.Put(\"root\", table)";
_root.Put((Object)("root"),(Object)(_table.getObject()));
 //BA.debugLineNum = 298;BA.debugLine="Return root";
if (true) return _root;
 //BA.debugLineNum = 299;BA.debugLine="End Sub";
return null;
}
public static String  _executelistview(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _query,String[] _stringargs,int _limit,anywheresoftware.b4a.objects.ListViewWrapper _listview1,boolean _twolines) throws Exception{
anywheresoftware.b4a.objects.collections.List _table = null;
String[] _cols = null;
int _i = 0;
 //BA.debugLineNum = 239;BA.debugLine="Public Sub ExecuteListView(SQL As SQL, Query As St";
 //BA.debugLineNum = 241;BA.debugLine="ListView1.Clear";
_listview1.Clear();
 //BA.debugLineNum = 242;BA.debugLine="Dim Table As List";
_table = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 243;BA.debugLine="Table = ExecuteMemoryTable(SQL, Query, StringArgs";
_table = _executememorytable(_ba,_sql,_query,_stringargs,_limit);
 //BA.debugLineNum = 244;BA.debugLine="Dim Cols() As String";
_cols = new String[(int) (0)];
java.util.Arrays.fill(_cols,"");
 //BA.debugLineNum = 245;BA.debugLine="For i = 0 To Table.Size - 1";
{
final int step5 = 1;
final int limit5 = (int) (_table.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit5 ;_i = _i + step5 ) {
 //BA.debugLineNum = 246;BA.debugLine="Cols = Table.Get(i)";
_cols = (String[])(_table.Get(_i));
 //BA.debugLineNum = 247;BA.debugLine="If TwoLines Then";
if (_twolines) { 
 //BA.debugLineNum = 248;BA.debugLine="ListView1.AddTwoLines2(Cols(0), Cols(1), Cols)";
_listview1.AddTwoLines2(BA.ObjectToCharSequence(_cols[(int) (0)]),BA.ObjectToCharSequence(_cols[(int) (1)]),(Object)(_cols));
 }else {
 //BA.debugLineNum = 250;BA.debugLine="ListView1.AddSingleLine2(Cols(0), Cols)";
_listview1.AddSingleLine2(BA.ObjectToCharSequence(_cols[(int) (0)]),(Object)(_cols));
 };
 }
};
 //BA.debugLineNum = 253;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.collections.Map  _executemap(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _query,String[] _stringargs) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _cur = null;
anywheresoftware.b4a.objects.collections.Map _res = null;
int _i = 0;
 //BA.debugLineNum = 201;BA.debugLine="Public Sub ExecuteMap(SQL As SQL, Query As String,";
 //BA.debugLineNum = 202;BA.debugLine="Dim cur As Cursor";
_cur = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 203;BA.debugLine="If StringArgs <> Null Then";
if (_stringargs!= null) { 
 //BA.debugLineNum = 204;BA.debugLine="cur = SQL.ExecQuery2(Query, StringArgs)";
_cur = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(_sql.ExecQuery2(_query,_stringargs)));
 }else {
 //BA.debugLineNum = 206;BA.debugLine="cur = SQL.ExecQuery(Query)";
_cur = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(_sql.ExecQuery(_query)));
 };
 //BA.debugLineNum = 208;BA.debugLine="Log(\"ExecuteMap: \" & Query)";
anywheresoftware.b4a.keywords.Common.LogImpl("149086471","ExecuteMap: "+_query,0);
 //BA.debugLineNum = 209;BA.debugLine="If cur.RowCount = 0 Then";
if (_cur.getRowCount()==0) { 
 //BA.debugLineNum = 210;BA.debugLine="Log(\"No records found.\")";
anywheresoftware.b4a.keywords.Common.LogImpl("149086473","No records found.",0);
 //BA.debugLineNum = 211;BA.debugLine="Return Null";
if (true) return (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(anywheresoftware.b4a.keywords.Common.Null));
 };
 //BA.debugLineNum = 213;BA.debugLine="Dim res As Map";
_res = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 214;BA.debugLine="res.Initialize";
_res.Initialize();
 //BA.debugLineNum = 215;BA.debugLine="cur.Position = 0";
_cur.setPosition((int) (0));
 //BA.debugLineNum = 216;BA.debugLine="For i = 0 To cur.ColumnCount - 1";
{
final int step15 = 1;
final int limit15 = (int) (_cur.getColumnCount()-1);
_i = (int) (0) ;
for (;_i <= limit15 ;_i = _i + step15 ) {
 //BA.debugLineNum = 217;BA.debugLine="res.Put(cur.GetColumnName(i).ToLowerCase, cur.Ge";
_res.Put((Object)(_cur.GetColumnName(_i).toLowerCase()),(Object)(_cur.GetString2(_i)));
 }
};
 //BA.debugLineNum = 219;BA.debugLine="cur.Close";
_cur.Close();
 //BA.debugLineNum = 220;BA.debugLine="Return res";
if (true) return _res;
 //BA.debugLineNum = 221;BA.debugLine="End Sub";
return null;
}
public static anywheresoftware.b4a.objects.collections.List  _executememorytable(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _query,String[] _stringargs,int _limit) throws Exception{
anywheresoftware.b4a.sql.SQL.CursorWrapper _cur = null;
anywheresoftware.b4a.objects.collections.List _table = null;
int _row = 0;
String[] _values = null;
int _col = 0;
 //BA.debugLineNum = 174;BA.debugLine="Public Sub ExecuteMemoryTable(SQL As SQL, Query As";
 //BA.debugLineNum = 175;BA.debugLine="Dim cur As Cursor";
_cur = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 176;BA.debugLine="If StringArgs <> Null Then";
if (_stringargs!= null) { 
 //BA.debugLineNum = 177;BA.debugLine="cur = SQL.ExecQuery2(Query, StringArgs)";
_cur = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(_sql.ExecQuery2(_query,_stringargs)));
 }else {
 //BA.debugLineNum = 179;BA.debugLine="cur = SQL.ExecQuery(Query)";
_cur = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(_sql.ExecQuery(_query)));
 };
 //BA.debugLineNum = 181;BA.debugLine="Log(\"ExecuteMemoryTable: \" & Query)";
anywheresoftware.b4a.keywords.Common.LogImpl("149020935","ExecuteMemoryTable: "+_query,0);
 //BA.debugLineNum = 182;BA.debugLine="Dim table As List";
_table = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 183;BA.debugLine="table.Initialize";
_table.Initialize();
 //BA.debugLineNum = 184;BA.debugLine="If Limit > 0 Then Limit = Min(Limit, cur.RowCount";
if (_limit>0) { 
_limit = (int) (anywheresoftware.b4a.keywords.Common.Min(_limit,_cur.getRowCount()));}
else {
_limit = _cur.getRowCount();};
 //BA.debugLineNum = 185;BA.debugLine="For row = 0 To Limit - 1";
{
final int step11 = 1;
final int limit11 = (int) (_limit-1);
_row = (int) (0) ;
for (;_row <= limit11 ;_row = _row + step11 ) {
 //BA.debugLineNum = 186;BA.debugLine="cur.Position = row";
_cur.setPosition(_row);
 //BA.debugLineNum = 187;BA.debugLine="Dim values(cur.ColumnCount) As String";
_values = new String[_cur.getColumnCount()];
java.util.Arrays.fill(_values,"");
 //BA.debugLineNum = 188;BA.debugLine="For col = 0 To cur.ColumnCount - 1";
{
final int step14 = 1;
final int limit14 = (int) (_cur.getColumnCount()-1);
_col = (int) (0) ;
for (;_col <= limit14 ;_col = _col + step14 ) {
 //BA.debugLineNum = 189;BA.debugLine="values(col) = cur.GetString2(col)";
_values[_col] = _cur.GetString2(_col);
 }
};
 //BA.debugLineNum = 191;BA.debugLine="table.Add(values)";
_table.Add((Object)(_values));
 }
};
 //BA.debugLineNum = 193;BA.debugLine="cur.Close";
_cur.Close();
 //BA.debugLineNum = 194;BA.debugLine="Return table";
if (true) return _table;
 //BA.debugLineNum = 195;BA.debugLine="End Sub";
return null;
}
public static String  _executespinner(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _query,String[] _stringargs,int _limit,anywheresoftware.b4a.objects.SpinnerWrapper _spinner1) throws Exception{
anywheresoftware.b4a.objects.collections.List _table = null;
String[] _cols = null;
int _i = 0;
 //BA.debugLineNum = 224;BA.debugLine="Sub ExecuteSpinner(SQL As SQL, Query As String, St";
 //BA.debugLineNum = 225;BA.debugLine="Spinner1.Clear";
_spinner1.Clear();
 //BA.debugLineNum = 226;BA.debugLine="Dim Table As List";
_table = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 227;BA.debugLine="Table = ExecuteMemoryTable(SQL, Query, StringArgs";
_table = _executememorytable(_ba,_sql,_query,_stringargs,_limit);
 //BA.debugLineNum = 228;BA.debugLine="Dim Cols() As String";
_cols = new String[(int) (0)];
java.util.Arrays.fill(_cols,"");
 //BA.debugLineNum = 229;BA.debugLine="For i = 0 To Table.Size - 1";
{
final int step5 = 1;
final int limit5 = (int) (_table.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit5 ;_i = _i + step5 ) {
 //BA.debugLineNum = 230;BA.debugLine="Cols = Table.Get(i)";
_cols = (String[])(_table.Get(_i));
 //BA.debugLineNum = 231;BA.debugLine="Spinner1.Add(Cols(0))";
_spinner1.Add(_cols[(int) (0)]);
 }
};
 //BA.debugLineNum = 233;BA.debugLine="End Sub";
return "";
}
public static int  _getdbversion(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql) throws Exception{
int _count = 0;
int _version = 0;
anywheresoftware.b4a.objects.collections.Map _m = null;
 //BA.debugLineNum = 357;BA.debugLine="Public Sub GetDBVersion (SQL As SQL) As Int";
 //BA.debugLineNum = 358;BA.debugLine="Dim count, version As Int";
_count = 0;
_version = 0;
 //BA.debugLineNum = 359;BA.debugLine="count = SQL.ExecQuerySingleResult(\"SELECT count(*";
_count = (int)(Double.parseDouble(_sql.ExecQuerySingleResult("SELECT count(*) FROM sqlite_master WHERE Type='table' AND name='DBVersion'")));
 //BA.debugLineNum = 360;BA.debugLine="If count > 0 Then";
if (_count>0) { 
 //BA.debugLineNum = 361;BA.debugLine="version = SQL.ExecQuerySingleResult(\"SELECT vers";
_version = (int)(Double.parseDouble(_sql.ExecQuerySingleResult("SELECT version FROM DBVersion")));
 }else {
 //BA.debugLineNum = 364;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 365;BA.debugLine="m.Initialize";
_m.Initialize();
 //BA.debugLineNum = 366;BA.debugLine="m.Put(\"version\", DB_INTEGER)";
_m.Put((Object)("version"),(Object)(_db_integer));
 //BA.debugLineNum = 367;BA.debugLine="CreateTable(SQL, \"DBVersion\", m, \"version\")";
_createtable(_ba,_sql,"DBVersion",_m,"version");
 //BA.debugLineNum = 369;BA.debugLine="SQL.ExecNonQuery(\"INSERT INTO DBVersion VALUES (";
_sql.ExecNonQuery("INSERT INTO DBVersion VALUES (1)");
 //BA.debugLineNum = 371;BA.debugLine="version = 1";
_version = (int) (1);
 };
 //BA.debugLineNum = 374;BA.debugLine="Return version";
if (true) return _version;
 //BA.debugLineNum = 375;BA.debugLine="End Sub";
return 0;
}
public static String  _insertmaps(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _tablename,anywheresoftware.b4a.objects.collections.List _listofmaps) throws Exception{
anywheresoftware.b4a.keywords.StringBuilderWrapper _sb = null;
anywheresoftware.b4a.keywords.StringBuilderWrapper _columns = null;
anywheresoftware.b4a.keywords.StringBuilderWrapper _values = null;
int _i1 = 0;
anywheresoftware.b4a.objects.collections.List _listofvalues = null;
anywheresoftware.b4a.objects.collections.Map _m = null;
int _i2 = 0;
String _col = "";
Object _value = null;
 //BA.debugLineNum = 67;BA.debugLine="Public Sub InsertMaps(SQL As SQL, TableName As Str";
 //BA.debugLineNum = 68;BA.debugLine="Dim sb, columns, values As StringBuilder";
_sb = new anywheresoftware.b4a.keywords.StringBuilderWrapper();
_columns = new anywheresoftware.b4a.keywords.StringBuilderWrapper();
_values = new anywheresoftware.b4a.keywords.StringBuilderWrapper();
 //BA.debugLineNum = 70;BA.debugLine="If ListOfMaps.Size > 1 And ListOfMaps.Get(0) = Li";
if (_listofmaps.getSize()>1 && (_listofmaps.Get((int) (0))).equals(_listofmaps.Get((int) (1)))) { 
 //BA.debugLineNum = 71;BA.debugLine="Log(\"Same Map found twice in list. Each item in";
anywheresoftware.b4a.keywords.Common.LogImpl("148824324","Same Map found twice in list. Each item in the list should include a different map object.",0);
 //BA.debugLineNum = 72;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 74;BA.debugLine="SQL.BeginTransaction";
_sql.BeginTransaction();
 //BA.debugLineNum = 75;BA.debugLine="Try";
try { //BA.debugLineNum = 76;BA.debugLine="For i1 = 0 To ListOfMaps.Size - 1";
{
final int step8 = 1;
final int limit8 = (int) (_listofmaps.getSize()-1);
_i1 = (int) (0) ;
for (;_i1 <= limit8 ;_i1 = _i1 + step8 ) {
 //BA.debugLineNum = 77;BA.debugLine="sb.Initialize";
_sb.Initialize();
 //BA.debugLineNum = 78;BA.debugLine="columns.Initialize";
_columns.Initialize();
 //BA.debugLineNum = 79;BA.debugLine="values.Initialize";
_values.Initialize();
 //BA.debugLineNum = 80;BA.debugLine="Dim listOfValues As List";
_listofvalues = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 81;BA.debugLine="listOfValues.Initialize";
_listofvalues.Initialize();
 //BA.debugLineNum = 82;BA.debugLine="sb.Append(\"INSERT INTO [\" & TableName & \"] (\")";
_sb.Append("INSERT INTO ["+_tablename+"] (");
 //BA.debugLineNum = 83;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 84;BA.debugLine="m = ListOfMaps.Get(i1)";
_m = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(_listofmaps.Get(_i1)));
 //BA.debugLineNum = 85;BA.debugLine="For i2 = 0 To m.Size - 1";
{
final int step17 = 1;
final int limit17 = (int) (_m.getSize()-1);
_i2 = (int) (0) ;
for (;_i2 <= limit17 ;_i2 = _i2 + step17 ) {
 //BA.debugLineNum = 86;BA.debugLine="Dim col As String";
_col = "";
 //BA.debugLineNum = 87;BA.debugLine="Dim value As Object";
_value = new Object();
 //BA.debugLineNum = 88;BA.debugLine="col = m.GetKeyAt(i2)";
_col = BA.ObjectToString(_m.GetKeyAt(_i2));
 //BA.debugLineNum = 89;BA.debugLine="value = m.GetValueAt(i2)";
_value = _m.GetValueAt(_i2);
 //BA.debugLineNum = 90;BA.debugLine="If i2 > 0 Then";
if (_i2>0) { 
 //BA.debugLineNum = 91;BA.debugLine="columns.Append(\", \")";
_columns.Append(", ");
 //BA.debugLineNum = 92;BA.debugLine="values.Append(\", \")";
_values.Append(", ");
 };
 //BA.debugLineNum = 94;BA.debugLine="columns.Append(\"[\").Append(col).Append(\"]\")";
_columns.Append("[").Append(_col).Append("]");
 //BA.debugLineNum = 95;BA.debugLine="values.Append(\"?\")";
_values.Append("?");
 //BA.debugLineNum = 96;BA.debugLine="listOfValues.Add(value)";
_listofvalues.Add(_value);
 }
};
 //BA.debugLineNum = 98;BA.debugLine="sb.Append(columns.ToString).Append(\") VALUES (\"";
_sb.Append(_columns.ToString()).Append(") VALUES (").Append(_values.ToString()).Append(")");
 //BA.debugLineNum = 99;BA.debugLine="If i1 = 0 Then Log(\"InsertMaps (first query out";
if (_i1==0) { 
anywheresoftware.b4a.keywords.Common.LogImpl("148824352","InsertMaps (first query out of "+BA.NumberToString(_listofmaps.getSize())+"): "+_sb.ToString(),0);};
 //BA.debugLineNum = 100;BA.debugLine="SQL.ExecNonQuery2(sb.ToString, listOfValues)";
_sql.ExecNonQuery2(_sb.ToString(),_listofvalues);
 }
};
 //BA.debugLineNum = 102;BA.debugLine="SQL.TransactionSuccessful";
_sql.TransactionSuccessful();
 } 
       catch (Exception e36) {
			(_ba.processBA == null ? _ba : _ba.processBA).setLastException(e36); //BA.debugLineNum = 104;BA.debugLine="ToastMessageShow(LastException.Message, True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.LastException(_ba).getMessage()),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 105;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("148824358",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(_ba)),0);
 };
 //BA.debugLineNum = 107;BA.debugLine="SQL.EndTransaction";
_sql.EndTransaction();
 //BA.debugLineNum = 108;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 1;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 2;BA.debugLine="Public DB_REAL, DB_INTEGER, DB_BLOB, DB_TEXT As S";
_db_real = "";
_db_integer = "";
_db_blob = "";
_db_text = "";
 //BA.debugLineNum = 3;BA.debugLine="DB_REAL = \"REAL\"";
_db_real = "REAL";
 //BA.debugLineNum = 4;BA.debugLine="DB_INTEGER = \"INTEGER\"";
_db_integer = "INTEGER";
 //BA.debugLineNum = 5;BA.debugLine="DB_BLOB = \"BLOB\"";
_db_blob = "BLOB";
 //BA.debugLineNum = 6;BA.debugLine="DB_TEXT = \"TEXT\"";
_db_text = "TEXT";
 //BA.debugLineNum = 7;BA.debugLine="Dim HtmlCSS As String";
_htmlcss = "";
 //BA.debugLineNum = 8;BA.debugLine="HtmlCSS = $\" 		table {width: 100%;border: 1px sol";
_htmlcss = ("\n"+"		table {width: 100%;border: 1px solid #cef;text-align: left; }\n"+"		th { font-weight: bold;	background-color: #acf;	border-bottom: 1px solid #cef; }\n"+"		td,th {	padding: 4px 5px; }\n"+"		.odd {background-color: #def; } \n"+"		.odd td {border-bottom: 1px solid #cef; }\n"+"		a { text-decoration:none; color: #000;}");
 //BA.debugLineNum = 15;BA.debugLine="End Sub";
return "";
}
public static String  _setdbversion(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,int _version) throws Exception{
 //BA.debugLineNum = 378;BA.debugLine="Public Sub SetDBVersion (SQL As SQL, Version As In";
 //BA.debugLineNum = 379;BA.debugLine="SQL.ExecNonQuery2(\"UPDATE DBVersion set version =";
_sql.ExecNonQuery2("UPDATE DBVersion set version = ?",anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(_version)}));
 //BA.debugLineNum = 380;BA.debugLine="End Sub";
return "";
}
public static String  _updaterecord(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _tablename,String _field,Object _newvalue,anywheresoftware.b4a.objects.collections.Map _wherefieldequals) throws Exception{
anywheresoftware.b4a.keywords.StringBuilderWrapper _sb = null;
anywheresoftware.b4a.objects.collections.List _args = null;
int _i = 0;
 //BA.debugLineNum = 112;BA.debugLine="Public Sub UpdateRecord(SQL As SQL, TableName As S";
 //BA.debugLineNum = 114;BA.debugLine="Dim sb As StringBuilder";
_sb = new anywheresoftware.b4a.keywords.StringBuilderWrapper();
 //BA.debugLineNum = 115;BA.debugLine="sb.Initialize";
_sb.Initialize();
 //BA.debugLineNum = 116;BA.debugLine="sb.Append(\"UPDATE [\").Append(TableName).Append(\"]";
_sb.Append("UPDATE [").Append(_tablename).Append("] SET [").Append(_field).Append("] = ? WHERE ");
 //BA.debugLineNum = 117;BA.debugLine="If WhereFieldEquals.Size = 0 Then";
if (_wherefieldequals.getSize()==0) { 
 //BA.debugLineNum = 118;BA.debugLine="Log(\"WhereFieldEquals map empty!\")";
anywheresoftware.b4a.keywords.Common.LogImpl("148889862","WhereFieldEquals map empty!",0);
 //BA.debugLineNum = 119;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 121;BA.debugLine="Dim args As List";
_args = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 122;BA.debugLine="args.Initialize";
_args.Initialize();
 //BA.debugLineNum = 123;BA.debugLine="args.Add(NewValue)";
_args.Add(_newvalue);
 //BA.debugLineNum = 124;BA.debugLine="For i = 0 To WhereFieldEquals.Size - 1";
{
final int step11 = 1;
final int limit11 = (int) (_wherefieldequals.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit11 ;_i = _i + step11 ) {
 //BA.debugLineNum = 125;BA.debugLine="If i > 0 Then sb.Append(\" AND \")";
if (_i>0) { 
_sb.Append(" AND ");};
 //BA.debugLineNum = 126;BA.debugLine="sb.Append(\"[\").Append(WhereFieldEquals.GetKeyAt(";
_sb.Append("[").Append(BA.ObjectToString(_wherefieldequals.GetKeyAt(_i))).Append("] = ?");
 //BA.debugLineNum = 127;BA.debugLine="args.Add(WhereFieldEquals.GetValueAt(i))";
_args.Add(_wherefieldequals.GetValueAt(_i));
 }
};
 //BA.debugLineNum = 129;BA.debugLine="Log(\"UpdateRecord: \" & sb.ToString)";
anywheresoftware.b4a.keywords.Common.LogImpl("148889873","UpdateRecord: "+_sb.ToString(),0);
 //BA.debugLineNum = 130;BA.debugLine="SQL.ExecNonQuery2(sb.ToString, args)";
_sql.ExecNonQuery2(_sb.ToString(),_args);
 //BA.debugLineNum = 131;BA.debugLine="End Sub";
return "";
}
public static String  _updaterecord2(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.sql.SQL _sql,String _tablename,anywheresoftware.b4a.objects.collections.Map _fields,anywheresoftware.b4a.objects.collections.Map _wherefieldequals) throws Exception{
anywheresoftware.b4a.keywords.StringBuilderWrapper _sb = null;
anywheresoftware.b4a.objects.collections.List _args = null;
int _i = 0;
 //BA.debugLineNum = 135;BA.debugLine="Public Sub UpdateRecord2(SQL As SQL, TableName As";
 //BA.debugLineNum = 136;BA.debugLine="If WhereFieldEquals.Size = 0 Then";
if (_wherefieldequals.getSize()==0) { 
 //BA.debugLineNum = 137;BA.debugLine="Log(\"WhereFieldEquals map empty!\")";
anywheresoftware.b4a.keywords.Common.LogImpl("148955394","WhereFieldEquals map empty!",0);
 //BA.debugLineNum = 138;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 140;BA.debugLine="If Fields.Size = 0 Then";
if (_fields.getSize()==0) { 
 //BA.debugLineNum = 141;BA.debugLine="Log(\"Fields empty\")";
anywheresoftware.b4a.keywords.Common.LogImpl("148955398","Fields empty",0);
 //BA.debugLineNum = 142;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 144;BA.debugLine="Dim sb As StringBuilder";
_sb = new anywheresoftware.b4a.keywords.StringBuilderWrapper();
 //BA.debugLineNum = 145;BA.debugLine="sb.Initialize";
_sb.Initialize();
 //BA.debugLineNum = 146;BA.debugLine="sb.Append(\"UPDATE [\").Append(TableName).Append(\"]";
_sb.Append("UPDATE [").Append(_tablename).Append("] SET ");
 //BA.debugLineNum = 147;BA.debugLine="Dim args As List";
_args = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 148;BA.debugLine="args.Initialize";
_args.Initialize();
 //BA.debugLineNum = 149;BA.debugLine="For i=0 To Fields.Size-1";
{
final int step14 = 1;
final int limit14 = (int) (_fields.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit14 ;_i = _i + step14 ) {
 //BA.debugLineNum = 150;BA.debugLine="If i<>Fields.Size-1 Then";
if (_i!=_fields.getSize()-1) { 
 //BA.debugLineNum = 151;BA.debugLine="sb.Append(\"[\").Append(Fields.GetKeyAt(i)).Appen";
_sb.Append("[").Append(BA.ObjectToString(_fields.GetKeyAt(_i))).Append("]=?,");
 }else {
 //BA.debugLineNum = 153;BA.debugLine="sb.Append(\"[\").Append(Fields.GetKeyAt(i)).Appen";
_sb.Append("[").Append(BA.ObjectToString(_fields.GetKeyAt(_i))).Append("]=?");
 };
 //BA.debugLineNum = 155;BA.debugLine="args.Add(Fields.GetValueAt(i))";
_args.Add(_fields.GetValueAt(_i));
 }
};
 //BA.debugLineNum = 158;BA.debugLine="sb.Append(\" WHERE \")";
_sb.Append(" WHERE ");
 //BA.debugLineNum = 159;BA.debugLine="For i = 0 To WhereFieldEquals.Size - 1";
{
final int step23 = 1;
final int limit23 = (int) (_wherefieldequals.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit23 ;_i = _i + step23 ) {
 //BA.debugLineNum = 160;BA.debugLine="If i > 0 Then";
if (_i>0) { 
 //BA.debugLineNum = 161;BA.debugLine="sb.Append(\" AND \")";
_sb.Append(" AND ");
 };
 //BA.debugLineNum = 163;BA.debugLine="sb.Append(\"[\").Append(WhereFieldEquals.GetKeyAt(";
_sb.Append("[").Append(BA.ObjectToString(_wherefieldequals.GetKeyAt(_i))).Append("] = ?");
 //BA.debugLineNum = 164;BA.debugLine="args.Add(WhereFieldEquals.GetValueAt(i))";
_args.Add(_wherefieldequals.GetValueAt(_i));
 }
};
 //BA.debugLineNum = 166;BA.debugLine="Log(\"UpdateRecord: \" & sb.ToString)";
anywheresoftware.b4a.keywords.Common.LogImpl("148955423","UpdateRecord: "+_sb.ToString(),0);
 //BA.debugLineNum = 167;BA.debugLine="SQL.ExecNonQuery2(sb.ToString, args)";
_sql.ExecNonQuery2(_sb.ToString(),_args);
 //BA.debugLineNum = 168;BA.debugLine="End Sub";
return "";
}
}
