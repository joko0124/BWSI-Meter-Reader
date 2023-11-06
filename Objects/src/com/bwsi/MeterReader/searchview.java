package com.bwsi.MeterReader;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class searchview extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "com.bwsi.MeterReader.searchview");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            
        }
        if (BA.isShellModeRuntimeCheck(ba)) 
			   this.getClass().getMethod("_class_globals", com.bwsi.MeterReader.searchview.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.objects.collections.Map _prefixlist = null;
public anywheresoftware.b4a.objects.collections.Map _substringlist = null;
public anywheresoftware.b4a.objects.EditTextWrapper _et = null;
public anywheresoftware.b4a.objects.ListViewWrapper _lv = null;
public int _min_limit = 0;
public int _max_limit = 0;
public Object _mcallback = null;
public String _meventname = "";
public anywheresoftware.b4a.objects.collections.List _allitems = null;
public anywheresoftware.b4a.objects.IME _ime = null;
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
public static class _item{
public boolean IsInitialized;
public String SearchText;
public String Title;
public String Text;
public Object Value;
public void Initialize() {
IsInitialized = true;
SearchText = "";
Title = "";
Text = "";
Value = new Object();
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public String  _additemstolist(anywheresoftware.b4a.objects.collections.List _li,String _full) throws Exception{
com.bwsi.MeterReader.searchview._item _it = null;
 //BA.debugLineNum = 82;BA.debugLine="Private Sub AddItemsToList(li As List, full As Str";
 //BA.debugLineNum = 83;BA.debugLine="If li.IsInitialized = False Then Return";
if (_li.IsInitialized()==__c.False) { 
if (true) return "";};
 //BA.debugLineNum = 84;BA.debugLine="For Each it As Item In li";
{
final anywheresoftware.b4a.BA.IterableList group2 = _li;
final int groupLen2 = group2.getSize()
;int index2 = 0;
;
for (; index2 < groupLen2;index2++){
_it = (com.bwsi.MeterReader.searchview._item)(group2.Get(index2));
 //BA.debugLineNum = 85;BA.debugLine="If full.Length > MAX_LIMIT And it.SearchText.Con";
if (_full.length()>_max_limit && _it.SearchText /*String*/ .contains(_full)==__c.False) { 
 //BA.debugLineNum = 86;BA.debugLine="Continue";
if (true) continue;
 };
 //BA.debugLineNum = 88;BA.debugLine="lv.AddTwoLines2(it.Title, it.Text, it)";
_lv.AddTwoLines2(BA.ObjectToCharSequence(_it.Title /*String*/ ),BA.ObjectToCharSequence(_it.Text /*String*/ ),(Object)(_it));
 }
};
 //BA.debugLineNum = 90;BA.debugLine="End Sub";
return "";
}
public String  _addtoparent(anywheresoftware.b4a.objects.PanelWrapper _parent,int _left,int _top,int _width,int _height) throws Exception{
 //BA.debugLineNum = 47;BA.debugLine="Public Sub AddToParent(Parent As Panel, Left As In";
 //BA.debugLineNum = 48;BA.debugLine="Parent.AddView(et, Left, Top, Width, 45dip)";
_parent.AddView((android.view.View)(_et.getObject()),_left,_top,_width,__c.DipToCurrent((int) (45)));
 //BA.debugLineNum = 49;BA.debugLine="Parent.AddView(lv, Left, Top + et.Height, Width,";
_parent.AddView((android.view.View)(_lv.getObject()),_left,(int) (_top+_et.getHeight()),_width,(int) (_height-_et.getHeight()));
 //BA.debugLineNum = 50;BA.debugLine="et_TextChanged(\"\", \"\")";
_et_textchanged("","");
 //BA.debugLineNum = 51;BA.debugLine="End Sub";
return "";
}
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 3;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 4;BA.debugLine="Private prefixList As Map";
_prefixlist = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 5;BA.debugLine="Private substringList As Map";
_substringlist = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 6;BA.debugLine="Public et As EditText";
_et = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 7;BA.debugLine="Public lv As ListView";
_lv = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 8;BA.debugLine="Private MIN_LIMIT, MAX_LIMIT As Int";
_min_limit = 0;
_max_limit = 0;
 //BA.debugLineNum = 9;BA.debugLine="MIN_LIMIT = 1";
_min_limit = (int) (1);
 //BA.debugLineNum = 10;BA.debugLine="MAX_LIMIT = 4 'doesn't limit the words length. On";
_max_limit = (int) (4);
 //BA.debugLineNum = 11;BA.debugLine="Private mCallback As Object";
_mcallback = new Object();
 //BA.debugLineNum = 12;BA.debugLine="Private mEventName As String";
_meventname = "";
 //BA.debugLineNum = 13;BA.debugLine="Private allItems As List";
_allitems = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 14;BA.debugLine="Private IME As IME";
_ime = new anywheresoftware.b4a.objects.IME();
 //BA.debugLineNum = 15;BA.debugLine="Type Item (SearchText As String, Title As String,";
;
 //BA.debugLineNum = 16;BA.debugLine="End Sub";
return "";
}
public String  _clearall() throws Exception{
 //BA.debugLineNum = 148;BA.debugLine="public Sub ClearAll";
 //BA.debugLineNum = 149;BA.debugLine="If allItems.IsInitialized = False Then Return";
if (_allitems.IsInitialized()==__c.False) { 
if (true) return "";};
 //BA.debugLineNum = 150;BA.debugLine="allItems.Clear";
_allitems.Clear();
 //BA.debugLineNum = 151;BA.debugLine="lv.Clear";
_lv.Clear();
 //BA.debugLineNum = 152;BA.debugLine="End Sub";
return "";
}
public String  _clearsearchbox() throws Exception{
 //BA.debugLineNum = 143;BA.debugLine="Public Sub ClearSearchBox";
 //BA.debugLineNum = 144;BA.debugLine="et.Text=\"\"";
_et.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 145;BA.debugLine="lv.Clear";
_lv.Clear();
 //BA.debugLineNum = 146;BA.debugLine="End Sub";
return "";
}
public String  _et_textchanged(String _old,String _new) throws Exception{
String _str1 = "";
String _str2 = "";
 //BA.debugLineNum = 64;BA.debugLine="Private Sub et_TextChanged (Old As String, New As";
 //BA.debugLineNum = 65;BA.debugLine="lv.Clear";
_lv.Clear();
 //BA.debugLineNum = 66;BA.debugLine="If lv.Visible = False Then lv.Visible = True";
if (_lv.getVisible()==__c.False) { 
_lv.setVisible(__c.True);};
 //BA.debugLineNum = 67;BA.debugLine="If New.Length < MIN_LIMIT Then";
if (_new.length()<_min_limit) { 
 //BA.debugLineNum = 68;BA.debugLine="AddItemsToList(allItems, \"\")";
_additemstolist(_allitems,"");
 //BA.debugLineNum = 69;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 71;BA.debugLine="Dim str1, str2 As String";
_str1 = "";
_str2 = "";
 //BA.debugLineNum = 72;BA.debugLine="str1 = New.ToLowerCase";
_str1 = _new.toLowerCase();
 //BA.debugLineNum = 73;BA.debugLine="If str1.Length > MAX_LIMIT Then";
if (_str1.length()>_max_limit) { 
 //BA.debugLineNum = 74;BA.debugLine="str2 = str1.SubString2(0, MAX_LIMIT)";
_str2 = _str1.substring((int) (0),_max_limit);
 }else {
 //BA.debugLineNum = 76;BA.debugLine="str2 = str1";
_str2 = _str1;
 };
 //BA.debugLineNum = 78;BA.debugLine="AddItemsToList(prefixList.Get(str2), str1)";
_additemstolist((anywheresoftware.b4a.objects.collections.List) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.List(), (java.util.List)(_prefixlist.Get((Object)(_str2)))),_str1);
 //BA.debugLineNum = 79;BA.debugLine="AddItemsToList(substringList.Get(str2), str1)";
_additemstolist((anywheresoftware.b4a.objects.collections.List) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.List(), (java.util.List)(_substringlist.Get((Object)(_str2)))),_str1);
 //BA.debugLineNum = 80;BA.debugLine="End Sub";
return "";
}
public String  _initialize(anywheresoftware.b4a.BA _ba,Object _callback,String _eventname) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 19;BA.debugLine="Public Sub Initialize (Callback As Object, EventNa";
 //BA.debugLineNum = 20;BA.debugLine="IME.Initialize(\"\")";
_ime.Initialize("");
 //BA.debugLineNum = 21;BA.debugLine="et.Initialize(\"et\")";
_et.Initialize(ba,"et");
 //BA.debugLineNum = 22;BA.debugLine="lv.Initialize(\"lv\")";
_lv.Initialize(ba,"lv");
 //BA.debugLineNum = 24;BA.debugLine="et.InputType = Bit.Or(et.INPUT_TYPE_TEXT, 0x00080";
_et.setInputType(__c.Bit.Or(_et.INPUT_TYPE_TEXT,(int) (0x00080000)));
 //BA.debugLineNum = 26;BA.debugLine="et.RequestFocus";
_et.RequestFocus();
 //BA.debugLineNum = 27;BA.debugLine="et.Text=\"\"";
_et.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 28;BA.debugLine="et.ForceDoneButton=True";
_et.setForceDoneButton(__c.True);
 //BA.debugLineNum = 29;BA.debugLine="et.TextSize=16";
_et.setTextSize((float) (16));
 //BA.debugLineNum = 30;BA.debugLine="et.TextColor=0xFF1976D2";
_et.setTextColor((int) (0xff1976d2));
 //BA.debugLineNum = 31;BA.debugLine="IME.ShowKeyboard(et)";
_ime.ShowKeyboard((android.view.View)(_et.getObject()));
 //BA.debugLineNum = 35;BA.debugLine="lv.TwoLinesLayout.ItemHeight = 50dip";
_lv.getTwoLinesLayout().setItemHeight(__c.DipToCurrent((int) (50)));
 //BA.debugLineNum = 36;BA.debugLine="lv.TwoLinesLayout.Label.TextColor=0xFF1976D2";
_lv.getTwoLinesLayout().Label.setTextColor((int) (0xff1976d2));
 //BA.debugLineNum = 37;BA.debugLine="lv.TwoLinesLayout.Label.TextSize=14";
_lv.getTwoLinesLayout().Label.setTextSize((float) (14));
 //BA.debugLineNum = 38;BA.debugLine="lv.TwoLinesLayout.SecondLabel.TextSize=11";
_lv.getTwoLinesLayout().SecondLabel.setTextSize((float) (11));
 //BA.debugLineNum = 39;BA.debugLine="lv.Visible = False";
_lv.setVisible(__c.False);
 //BA.debugLineNum = 40;BA.debugLine="prefixList.Initialize";
_prefixlist.Initialize();
 //BA.debugLineNum = 41;BA.debugLine="substringList.Initialize";
_substringlist.Initialize();
 //BA.debugLineNum = 42;BA.debugLine="mCallback = Callback";
_mcallback = _callback;
 //BA.debugLineNum = 43;BA.debugLine="mEventName = EventName";
_meventname = _eventname;
 //BA.debugLineNum = 44;BA.debugLine="End Sub";
return "";
}
public String  _lv_itemclick(int _position,Object _value) throws Exception{
com.bwsi.MeterReader.searchview._item _it = null;
 //BA.debugLineNum = 53;BA.debugLine="Private Sub lv_ItemClick (Position As Int, Value A";
 //BA.debugLineNum = 54;BA.debugLine="Dim it As Item = Value";
_it = (com.bwsi.MeterReader.searchview._item)(_value);
 //BA.debugLineNum = 55;BA.debugLine="et.Text = it.Title";
_et.setText(BA.ObjectToCharSequence(_it.Title /*String*/ ));
 //BA.debugLineNum = 56;BA.debugLine="et.SelectionStart = et.Text.Length";
_et.setSelectionStart(_et.getText().length());
 //BA.debugLineNum = 57;BA.debugLine="IME.HideKeyboard";
_ime.HideKeyboard(ba);
 //BA.debugLineNum = 58;BA.debugLine="lv.Visible = False";
_lv.setVisible(__c.False);
 //BA.debugLineNum = 59;BA.debugLine="If SubExists(mCallback, mEventName & \"_ItemClick\"";
if (__c.SubExists(ba,_mcallback,_meventname+"_ItemClick")) { 
 //BA.debugLineNum = 60;BA.debugLine="CallSub2(mCallback, mEventName & \"_ItemClick\", i";
__c.CallSubNew2(ba,_mcallback,_meventname+"_ItemClick",_it.Value /*Object*/ );
 };
 //BA.debugLineNum = 62;BA.debugLine="End Sub";
return "";
}
public String  _setindex(Object _index,anywheresoftware.b4a.objects.collections.List _items) throws Exception{
Object[] _obj = null;
 //BA.debugLineNum = 134;BA.debugLine="Public Sub SetIndex(Index As Object, Items As List";
 //BA.debugLineNum = 135;BA.debugLine="Dim obj() As Object";
_obj = new Object[(int) (0)];
{
int d0 = _obj.length;
for (int i0 = 0;i0 < d0;i0++) {
_obj[i0] = new Object();
}
}
;
 //BA.debugLineNum = 136;BA.debugLine="obj = Index";
_obj = (Object[])(_index);
 //BA.debugLineNum = 137;BA.debugLine="prefixList = obj(0)";
_prefixlist = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(_obj[(int) (0)]));
 //BA.debugLineNum = 138;BA.debugLine="substringList = obj(1)";
_substringlist = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(_obj[(int) (1)]));
 //BA.debugLineNum = 139;BA.debugLine="allItems = Items";
_allitems = _items;
 //BA.debugLineNum = 140;BA.debugLine="AddItemsToList(allItems, \"\")";
_additemstolist(_allitems,"");
 //BA.debugLineNum = 141;BA.debugLine="End Sub";
return "";
}
public Object  _setitems(anywheresoftware.b4a.objects.collections.List _items) throws Exception{
long _starttime = 0L;
anywheresoftware.b4a.objects.collections.Map _noduplicates = null;
anywheresoftware.b4a.objects.collections.Map _m = null;
anywheresoftware.b4a.objects.collections.List _li = null;
com.bwsi.MeterReader.searchview._item _it = null;
int _start = 0;
int _count = 0;
String _str = "";
 //BA.debugLineNum = 94;BA.debugLine="Public Sub SetItems(Items As List) As Object";
 //BA.debugLineNum = 95;BA.debugLine="allItems = Items";
_allitems = _items;
 //BA.debugLineNum = 96;BA.debugLine="AddItemsToList(allItems, \"\")";
_additemstolist(_allitems,"");
 //BA.debugLineNum = 97;BA.debugLine="Dim startTime As Long";
_starttime = 0L;
 //BA.debugLineNum = 98;BA.debugLine="startTime = DateTime.Now";
_starttime = __c.DateTime.getNow();
 //BA.debugLineNum = 100;BA.debugLine="Dim noDuplicates As Map";
_noduplicates = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 101;BA.debugLine="noDuplicates.Initialize";
_noduplicates.Initialize();
 //BA.debugLineNum = 102;BA.debugLine="prefixList.Clear";
_prefixlist.Clear();
 //BA.debugLineNum = 103;BA.debugLine="substringList.Clear";
_substringlist.Clear();
 //BA.debugLineNum = 104;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 105;BA.debugLine="Dim li As List";
_li = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 106;BA.debugLine="For Each it As Item In Items";
{
final anywheresoftware.b4a.BA.IterableList group11 = _items;
final int groupLen11 = group11.getSize()
;int index11 = 0;
;
for (; index11 < groupLen11;index11++){
_it = (com.bwsi.MeterReader.searchview._item)(group11.Get(index11));
 //BA.debugLineNum = 107;BA.debugLine="noDuplicates.Clear";
_noduplicates.Clear();
 //BA.debugLineNum = 108;BA.debugLine="For start = 0 To it.SearchText.Length";
{
final int step13 = 1;
final int limit13 = _it.SearchText /*String*/ .length();
_start = (int) (0) ;
for (;_start <= limit13 ;_start = _start + step13 ) {
 //BA.debugLineNum = 109;BA.debugLine="Dim count As Int";
_count = 0;
 //BA.debugLineNum = 110;BA.debugLine="count = MIN_LIMIT";
_count = _min_limit;
 //BA.debugLineNum = 111;BA.debugLine="Do While count <= MAX_LIMIT And start + count <";
while (_count<=_max_limit && _start+_count<=_it.SearchText /*String*/ .length()) {
 //BA.debugLineNum = 112;BA.debugLine="Dim str As String";
_str = "";
 //BA.debugLineNum = 113;BA.debugLine="str = it.SearchText.SubString2(start, start +";
_str = _it.SearchText /*String*/ .substring(_start,(int) (_start+_count));
 //BA.debugLineNum = 114;BA.debugLine="If noDuplicates.ContainsKey(str) = False Then";
if (_noduplicates.ContainsKey((Object)(_str))==__c.False) { 
 //BA.debugLineNum = 115;BA.debugLine="noDuplicates.Put(str, \"\")";
_noduplicates.Put((Object)(_str),(Object)(""));
 //BA.debugLineNum = 116;BA.debugLine="If start = 0 Then m = prefixList Else m = sub";
if (_start==0) { 
_m = _prefixlist;}
else {
_m = _substringlist;};
 //BA.debugLineNum = 117;BA.debugLine="li = m.Get(str)";
_li = (anywheresoftware.b4a.objects.collections.List) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.List(), (java.util.List)(_m.Get((Object)(_str))));
 //BA.debugLineNum = 118;BA.debugLine="If li.IsInitialized = False Then";
if (_li.IsInitialized()==__c.False) { 
 //BA.debugLineNum = 119;BA.debugLine="li.Initialize";
_li.Initialize();
 //BA.debugLineNum = 120;BA.debugLine="m.Put(str, li)";
_m.Put((Object)(_str),(Object)(_li.getObject()));
 };
 //BA.debugLineNum = 122;BA.debugLine="li.Add(it) 'Preserve the original case";
_li.Add((Object)(_it));
 };
 //BA.debugLineNum = 124;BA.debugLine="count = count + 1";
_count = (int) (_count+1);
 }
;
 }
};
 }
};
 //BA.debugLineNum = 128;BA.debugLine="ProgressDialogHide";
__c.ProgressDialogHide();
 //BA.debugLineNum = 129;BA.debugLine="Log(\"Index time: \" & (DateTime.Now - startTime) &";
__c.LogImpl("456623139","Index time: "+BA.NumberToString((__c.DateTime.getNow()-_starttime))+" ms ("+BA.NumberToString(_items.getSize())+" Items)",0);
 //BA.debugLineNum = 130;BA.debugLine="Return Array As Object(prefixList, substringList)";
if (true) return (Object)(new Object[]{(Object)(_prefixlist.getObject()),(Object)(_substringlist.getObject())});
 //BA.debugLineNum = 131;BA.debugLine="End Sub";
return null;
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
return BA.SubDelegator.SubNotFound;
}
}
