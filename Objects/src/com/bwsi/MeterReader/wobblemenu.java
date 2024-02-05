package com.bwsi.MeterReader;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class wobblemenu extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "com.bwsi.MeterReader.wobblemenu");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            
        }
        if (BA.isShellModeRuntimeCheck(ba)) 
			   this.getClass().getMethod("_class_globals", com.bwsi.MeterReader.wobblemenu.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public String _meventname = "";
public Object _mcallback = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _mbase = null;
public anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public int _backgroundcolor = 0;
public int _iconcolor = 0;
public int _iconsize = 0;
public int _textcolor = 0;
public int _textsize = 0;
public int _selectediconcolor = 0;
public int _circleradius = 0;
public int _animduration = 0;
public anywheresoftware.b4a.objects.B4XViewWrapper _tabcontainer = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _tabcurve = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _tabcircle = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _tab1 = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _tab2 = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _tab3 = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _tab4 = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _tab5 = null;
public anywheresoftware.b4a.objects.collections.List _tabs = null;
public anywheresoftware.b4a.objects.collections.List _iconlist = null;
public int _menuheight = 0;
public int _absolutewidth = 0;
public int _tabcount = 0;
public int _activetab = 0;
public int _currenttab = 0;
public int _animation_type_elastic_out = 0;
public int _animation_type_elastic_in = 0;
public int _animation_type_ease_out = 0;
public int _animation_type_ease_in = 0;
public int _animation_type_none = 0;
public int _animationtype = 0;
public int _icon_appear_from_center = 0;
public int _icon_appear_from_edge = 0;
public int _icon_appear_fade_in = 0;
public int _icon_appear_no_animation = 0;
public int _iconappearstyle = 0;
public String _shadowcolor = "";
public anywheresoftware.b4a.objects.collections.List _badge = null;
public anywheresoftware.b4a.objects.collections.List _enabled = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _designerlbl = null;
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
public static class _icontype{
public boolean IsInitialized;
public String Text;
public String Icon;
public anywheresoftware.b4a.keywords.constants.TypefaceWrapper iFont;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper IconImg;
public boolean tinted;
public void Initialize() {
IsInitialized = true;
Text = "";
Icon = "";
iFont = new anywheresoftware.b4a.keywords.constants.TypefaceWrapper();
IconImg = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
tinted = false;
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public void  _animateto(anywheresoftware.b4a.objects.B4XViewWrapper _element,int _newpos) throws Exception{
ResumableSub_AnimateTo rsub = new ResumableSub_AnimateTo(this,_element,_newpos);
rsub.resume(ba, null);
}
public static class ResumableSub_AnimateTo extends BA.ResumableSub {
public ResumableSub_AnimateTo(com.bwsi.MeterReader.wobblemenu parent,anywheresoftware.b4a.objects.B4XViewWrapper _element,int _newpos) {
this.parent = parent;
this._element = _element;
this._newpos = _newpos;
}
com.bwsi.MeterReader.wobblemenu parent;
anywheresoftware.b4a.objects.B4XViewWrapper _element;
int _newpos;
long _n = 0L;
int _duration = 0;
int _currentpos = 0;
float _start = 0f;
float _tempvalue = 0f;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 777;BA.debugLine="Dim n As Long = DateTime.Now";
_n = parent.__c.DateTime.getNow();
 //BA.debugLineNum = 778;BA.debugLine="Dim duration As Int = animDuration";
_duration = parent._animduration;
 //BA.debugLineNum = 779;BA.debugLine="Dim currentPos As Int = Element.left";
_currentpos = _element.getLeft();
 //BA.debugLineNum = 780;BA.debugLine="Dim start As Float = currentPos";
_start = (float) (_currentpos);
 //BA.debugLineNum = 781;BA.debugLine="currentPos = NewPos";
_currentpos = _newpos;
 //BA.debugLineNum = 782;BA.debugLine="Dim tempValue As Float";
_tempvalue = 0f;
 //BA.debugLineNum = 783;BA.debugLine="Do While DateTime.Now < n + duration";
if (true) break;

case 1:
//do while
this.state = 10;
while (parent.__c.DateTime.getNow()<_n+_duration) {
this.state = 3;
if (true) break;
}
if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 784;BA.debugLine="tempValue = TimeWisePosition(DateTime.Now - n, s";
_tempvalue = (float) (parent._timewiseposition((float) (parent.__c.DateTime.getNow()-_n),_start,(float) (_newpos-_start),_duration));
 //BA.debugLineNum = 785;BA.debugLine="Element.left=tempValue";
_element.setLeft((int) (_tempvalue));
 //BA.debugLineNum = 786;BA.debugLine="Sleep(1)";
parent.__c.Sleep(ba,this,(int) (1));
this.state = 11;
return;
case 11:
//C
this.state = 4;
;
 //BA.debugLineNum = 787;BA.debugLine="If NewPos <> currentPos Then Return";
if (true) break;

case 4:
//if
this.state = 9;
if (_newpos!=_currentpos) { 
this.state = 6;
;}if (true) break;

case 6:
//C
this.state = 9;
if (true) return ;
if (true) break;

case 9:
//C
this.state = 1;
;
 if (true) break;

case 10:
//C
this.state = -1;
;
 //BA.debugLineNum = 789;BA.debugLine="Element.Left = currentPos";
_element.setLeft(_currentpos);
 //BA.debugLineNum = 790;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public String  _base_resize(double _width,double _height) throws Exception{
 //BA.debugLineNum = 125;BA.debugLine="Private Sub Base_Resize (Width As Double, Height A";
 //BA.debugLineNum = 126;BA.debugLine="mBase.Width = Width";
_mbase.setWidth((int) (_width));
 //BA.debugLineNum = 127;BA.debugLine="AbsoluteWidth= Min(mBase.Parent.Width,mBase.Paren";
_absolutewidth = (int) (__c.Min(_mbase.getParent().getWidth(),_mbase.getParent().getHeight()));
 //BA.debugLineNum = 128;BA.debugLine="MenuHeight = (AbsoluteWidth/7) + ((AbsoluteWidth/";
_menuheight = (int) ((_absolutewidth/(double)7)+((_absolutewidth/(double)7)/(double)4));
 //BA.debugLineNum = 129;BA.debugLine="mBase.Height = MenuHeight";
_mbase.setHeight(_menuheight);
 //BA.debugLineNum = 130;BA.debugLine="mBase.Top = mBase.Parent.Height - mBase.Height";
_mbase.setTop((int) (_mbase.getParent().getHeight()-_mbase.getHeight()));
 //BA.debugLineNum = 131;BA.debugLine="circleRadius = AbsoluteWidth/7";
_circleradius = (int) (_absolutewidth/(double)7);
 //BA.debugLineNum = 133;BA.debugLine="DrawView";
_drawview();
 //BA.debugLineNum = 134;BA.debugLine="End Sub";
return "";
}
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 23;BA.debugLine="Private Sub Class_Globals";
 //BA.debugLineNum = 24;BA.debugLine="Private mEventName As String 'ignore";
_meventname = "";
 //BA.debugLineNum = 25;BA.debugLine="Private mCallBack As Object 'ignore";
_mcallback = new Object();
 //BA.debugLineNum = 26;BA.debugLine="Private mBase As B4XView";
_mbase = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 29;BA.debugLine="Private BackgroundColor,IconColor,IconSize,TextCo";
_backgroundcolor = 0;
_iconcolor = 0;
_iconsize = 0;
_textcolor = 0;
_textsize = 0;
_selectediconcolor = 0;
_circleradius = 0;
_animduration = 0;
 //BA.debugLineNum = 30;BA.debugLine="Private TabContainer,TabCurve,TabCircle,Tab1,Tab2";
_tabcontainer = new anywheresoftware.b4a.objects.B4XViewWrapper();
_tabcurve = new anywheresoftware.b4a.objects.B4XViewWrapper();
_tabcircle = new anywheresoftware.b4a.objects.B4XViewWrapper();
_tab1 = new anywheresoftware.b4a.objects.B4XViewWrapper();
_tab2 = new anywheresoftware.b4a.objects.B4XViewWrapper();
_tab3 = new anywheresoftware.b4a.objects.B4XViewWrapper();
_tab4 = new anywheresoftware.b4a.objects.B4XViewWrapper();
_tab5 = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private Tabs,IconList As List";
_tabs = new anywheresoftware.b4a.objects.collections.List();
_iconlist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 32;BA.debugLine="Private MenuHeight, AbsoluteWidth, TabCount, Acti";
_menuheight = 0;
_absolutewidth = 0;
_tabcount = 0;
_activetab = 0;
 //BA.debugLineNum = 33;BA.debugLine="Private CurrentTab As Int";
_currenttab = 0;
 //BA.debugLineNum = 35;BA.debugLine="Public const ANIMATION_TYPE_ELASTIC_OUT As Int =";
_animation_type_elastic_out = (int) (0);
 //BA.debugLineNum = 36;BA.debugLine="Public const ANIMATION_TYPE_ELASTIC_IN As Int = 1";
_animation_type_elastic_in = (int) (1);
 //BA.debugLineNum = 37;BA.debugLine="Public const ANIMATION_TYPE_EASE_OUT As Int = 2";
_animation_type_ease_out = (int) (2);
 //BA.debugLineNum = 38;BA.debugLine="Public const ANIMATION_TYPE_EASE_IN As Int = 3";
_animation_type_ease_in = (int) (3);
 //BA.debugLineNum = 39;BA.debugLine="Public const ANIMATION_TYPE_NONE As Int = 4";
_animation_type_none = (int) (4);
 //BA.debugLineNum = 40;BA.debugLine="Private AnimationType As Int";
_animationtype = 0;
 //BA.debugLineNum = 42;BA.debugLine="Public const ICON_APPEAR_FROM_CENTER As Int = 0";
_icon_appear_from_center = (int) (0);
 //BA.debugLineNum = 43;BA.debugLine="Public const ICON_APPEAR_FROM_EDGE As Int = 1";
_icon_appear_from_edge = (int) (1);
 //BA.debugLineNum = 44;BA.debugLine="Public const ICON_APPEAR_FADE_IN As Int = 2";
_icon_appear_fade_in = (int) (2);
 //BA.debugLineNum = 45;BA.debugLine="Public const ICON_APPEAR_NO_ANIMATION As Int = 3";
_icon_appear_no_animation = (int) (3);
 //BA.debugLineNum = 46;BA.debugLine="Private IconAppearStyle As Int";
_iconappearstyle = 0;
 //BA.debugLineNum = 47;BA.debugLine="Private ShadowColor As String";
_shadowcolor = "";
 //BA.debugLineNum = 48;BA.debugLine="Private badge,enabled As List";
_badge = new anywheresoftware.b4a.objects.collections.List();
_enabled = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 55;BA.debugLine="Type IconType(Text As String, Icon As String, iFo";
;
 //BA.debugLineNum = 62;BA.debugLine="Private designerLbl As B4XView";
_designerlbl = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 63;BA.debugLine="End Sub";
return "";
}
public String  _createtab() throws Exception{
int _j = 0;
anywheresoftware.b4a.objects.B4XViewWrapper _tabview = null;
int _tab_width = 0;
com.bwsi.MeterReader.wobblemenu._icontype _i = null;
anywheresoftware.b4a.objects.LabelWrapper _icon = null;
anywheresoftware.b4a.objects.B4XViewWrapper _b4xlbl = null;
anywheresoftware.b4a.objects.ImageViewWrapper _iconimg = null;
anywheresoftware.b4a.objects.LabelWrapper _icontext = null;
anywheresoftware.b4j.object.JavaObject _jo = null;
anywheresoftware.b4a.objects.collections.Map _data = null;
 //BA.debugLineNum = 201;BA.debugLine="Private Sub CreateTab";
 //BA.debugLineNum = 202;BA.debugLine="For j=0 To TabCount-1";
{
final int step1 = 1;
final int limit1 = (int) (_tabcount-1);
_j = (int) (0) ;
for (;_j <= limit1 ;_j = _j + step1 ) {
 //BA.debugLineNum = 204;BA.debugLine="Dim tabView As B4XView = Tabs.Get(j)";
_tabview = new anywheresoftware.b4a.objects.B4XViewWrapper();
_tabview = (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_tabs.Get(_j)));
 //BA.debugLineNum = 205;BA.debugLine="tabView.Enabled = enabled.Get(j)";
_tabview.setEnabled(BA.ObjectToBoolean(_enabled.Get(_j)));
 //BA.debugLineNum = 206;BA.debugLine="Dim tab_width As Int = TabContainer.Width/TabCou";
_tab_width = (int) (_tabcontainer.getWidth()/(double)_tabcount);
 //BA.debugLineNum = 207;BA.debugLine="tabView.Color = xui.Color_Transparent";
_tabview.setColor(_xui.Color_Transparent);
 //BA.debugLineNum = 208;BA.debugLine="TabContainer.AddView(tabView,tab_width*j,0,tab_w";
_tabcontainer.AddView((android.view.View)(_tabview.getObject()),(int) (_tab_width*_j),(int) (0),_tab_width,_tabcontainer.getHeight());
 //BA.debugLineNum = 210;BA.debugLine="Dim i As IconType:i.Initialize";
_i = new com.bwsi.MeterReader.wobblemenu._icontype();
 //BA.debugLineNum = 210;BA.debugLine="Dim i As IconType:i.Initialize";
_i.Initialize();
 //BA.debugLineNum = 211;BA.debugLine="If IconList.Size > j Then";
if (_iconlist.getSize()>_j) { 
 //BA.debugLineNum = 212;BA.debugLine="i = IconList.Get(j)";
_i = (com.bwsi.MeterReader.wobblemenu._icontype)(_iconlist.Get(_j));
 }else {
 //BA.debugLineNum = 214;BA.debugLine="i.tinted = False";
_i.tinted /*boolean*/  = __c.False;
 //BA.debugLineNum = 215;BA.debugLine="i.IconImg = Null";
_i.IconImg /*anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper*/  = (anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper(), (android.graphics.Bitmap)(__c.Null));
 //BA.debugLineNum = 216;BA.debugLine="i.icon = Chr(0xF10C)";
_i.Icon /*String*/  = BA.ObjectToString(__c.Chr((int) (0xf10c)));
 //BA.debugLineNum = 222;BA.debugLine="i.ifont = Typeface.FONTAWESOME";
_i.iFont /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/  = (anywheresoftware.b4a.keywords.constants.TypefaceWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.constants.TypefaceWrapper(), (android.graphics.Typeface)(__c.Typeface.getFONTAWESOME()));
 };
 //BA.debugLineNum = 226;BA.debugLine="Dim icon As Label";
_icon = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 227;BA.debugLine="icon.Initialize(\"\")";
_icon.Initialize(ba,"");
 //BA.debugLineNum = 228;BA.debugLine="Dim b4xlbl As B4XView = icon";
_b4xlbl = new anywheresoftware.b4a.objects.B4XViewWrapper();
_b4xlbl = (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_icon.getObject()));
 //BA.debugLineNum = 229;BA.debugLine="b4xlbl.TextColor = IconColor";
_b4xlbl.setTextColor(_iconcolor);
 //BA.debugLineNum = 230;BA.debugLine="b4xlbl.SetTextAlignment(\"CENTER\",\"CENTER\")";
_b4xlbl.SetTextAlignment("CENTER","CENTER");
 //BA.debugLineNum = 231;BA.debugLine="b4xlbl.TextSize = IconSize";
_b4xlbl.setTextSize((float) (_iconsize));
 //BA.debugLineNum = 236;BA.debugLine="icon.Typeface = i.iFont";
_icon.setTypeface((android.graphics.Typeface)(_i.iFont /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/ .getObject()));
 //BA.debugLineNum = 239;BA.debugLine="icon.Text = i.icon";
_icon.setText(BA.ObjectToCharSequence(_i.Icon /*String*/ ));
 //BA.debugLineNum = 240;BA.debugLine="tabView.AddView(icon,0,0,tabView.Width,tabView.H";
_tabview.AddView((android.view.View)(_icon.getObject()),(int) (0),(int) (0),_tabview.getWidth(),_tabview.getHeight());
 //BA.debugLineNum = 242;BA.debugLine="Dim iconImg As ImageView";
_iconimg = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 243;BA.debugLine="iconImg.Initialize(\"\")";
_iconimg.Initialize(ba,"");
 //BA.debugLineNum = 244;BA.debugLine="tabView.AddView(iconImg,0,((tabView.Height/3)-(t";
_tabview.AddView((android.view.View)(_iconimg.getObject()),(int) (0),(int) (((_tabview.getHeight()/(double)3)-(_tabview.getHeight()/(double)4))/(double)2),_tabview.getWidth(),_tabview.getHeight());
 //BA.debugLineNum = 246;BA.debugLine="Dim iconText As Label";
_icontext = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 247;BA.debugLine="iconText.Initialize(\"\")";
_icontext.Initialize(ba,"");
 //BA.debugLineNum = 248;BA.debugLine="b4xlbl=iconText";
_b4xlbl = (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_icontext.getObject()));
 //BA.debugLineNum = 249;BA.debugLine="b4xlbl.TextColor = TextColor";
_b4xlbl.setTextColor(_textcolor);
 //BA.debugLineNum = 250;BA.debugLine="b4xlbl.Font=designerLbl.Font";
_b4xlbl.setFont(_designerlbl.getFont());
 //BA.debugLineNum = 251;BA.debugLine="b4xlbl.TextSize=TextSize";
_b4xlbl.setTextSize((float) (_textsize));
 //BA.debugLineNum = 252;BA.debugLine="b4xlbl.SetTextAlignment(\"CENTER\",\"CENTER\")";
_b4xlbl.SetTextAlignment("CENTER","CENTER");
 //BA.debugLineNum = 253;BA.debugLine="b4xlbl.text=i.text";
_b4xlbl.setText(BA.ObjectToCharSequence(_i.Text /*String*/ ));
 //BA.debugLineNum = 255;BA.debugLine="tabView.AddView(iconText,0,tabView.Height/2,tabV";
_tabview.AddView((android.view.View)(_icontext.getObject()),(int) (0),(int) (_tabview.getHeight()/(double)2),_tabview.getWidth(),(int) (_tabview.getHeight()/(double)2));
 //BA.debugLineNum = 257;BA.debugLine="If i.Text = \"\" Then";
if ((_i.Text /*String*/ ).equals("")) { 
 //BA.debugLineNum = 262;BA.debugLine="icon.Height=tabView.Height";
_icon.setHeight(_tabview.getHeight());
 //BA.debugLineNum = 263;BA.debugLine="iconImg.Height = icon.Height";
_iconimg.setHeight(_icon.getHeight());
 }else {
 //BA.debugLineNum = 270;BA.debugLine="icon.Height=(tabView.Height/3)*2";
_icon.setHeight((int) ((_tabview.getHeight()/(double)3)*2));
 //BA.debugLineNum = 271;BA.debugLine="iconImg.Height = (tabView.Height/2)";
_iconimg.setHeight((int) ((_tabview.getHeight()/(double)2)));
 };
 //BA.debugLineNum = 275;BA.debugLine="If i.IconImg.IsInitialized Then";
if (_i.IconImg /*anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper*/ .IsInitialized()) { 
 //BA.debugLineNum = 277;BA.debugLine="Dim jo As JavaObject=iconImg";
_jo = new anywheresoftware.b4j.object.JavaObject();
_jo = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(_iconimg.getObject()));
 //BA.debugLineNum = 278;BA.debugLine="jo.RunMethod(\"setImageBitmap\",Array(i.IconImg))";
_jo.RunMethod("setImageBitmap",new Object[]{(Object)(_i.IconImg /*anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper*/ .getObject())});
 //BA.debugLineNum = 279;BA.debugLine="jo.RunMethod(\"setScaleType\",Array(\"CENTER_INSID";
_jo.RunMethod("setScaleType",new Object[]{(Object)("CENTER_INSIDE")});
 //BA.debugLineNum = 280;BA.debugLine="jo.RunMethod(\"setColorFilter\",Array(Colors.Tran";
_jo.RunMethod("setColorFilter",new Object[]{(Object)(__c.Colors.Transparent)});
 //BA.debugLineNum = 281;BA.debugLine="If i.tinted Then jo.RunMethod(\"setColorFilter\",";
if (_i.tinted /*boolean*/ ) { 
_jo.RunMethod("setColorFilter",new Object[]{(Object)(__c.Colors.RGB(_getargb(_iconcolor)[(int) (1)],_getargb(_iconcolor)[(int) (2)],_getargb(_iconcolor)[(int) (3)]))});};
 };
 //BA.debugLineNum = 297;BA.debugLine="If badge.Size > j Then";
if (_badge.getSize()>_j) { 
 //BA.debugLineNum = 298;BA.debugLine="If badge.Get(j) <> \"\" Then";
if ((_badge.Get(_j)).equals((Object)("")) == false) { 
 //BA.debugLineNum = 299;BA.debugLine="Dim data As Map = badge.Get(j)";
_data = new anywheresoftware.b4a.objects.collections.Map();
_data = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(_badge.Get(_j)));
 //BA.debugLineNum = 300;BA.debugLine="SetBadge(j+1,data.Get(\"count\"),data.Get(\"backc";
_setbadge((int) (_j+1),(int)(BA.ObjectToNumber(_data.Get((Object)("count")))),(int)(BA.ObjectToNumber(_data.Get((Object)("backcolor")))),(int)(BA.ObjectToNumber(_data.Get((Object)("textcolor")))));
 };
 }else {
 //BA.debugLineNum = 303;BA.debugLine="badge.Add(\"\")";
_badge.Add((Object)(""));
 };
 //BA.debugLineNum = 306;BA.debugLine="If IconList.Size < TabCount Then IconList.add(i)";
if (_iconlist.getSize()<_tabcount) { 
_iconlist.Add((Object)(_i));};
 //BA.debugLineNum = 307;BA.debugLine="If j=CurrentTab-1 Then";
if (_j==_currenttab-1) { 
 //BA.debugLineNum = 308;BA.debugLine="icon.Visible = False";
_icon.setVisible(__c.False);
 //BA.debugLineNum = 309;BA.debugLine="iconImg.Visible = False";
_iconimg.setVisible(__c.False);
 //BA.debugLineNum = 310;BA.debugLine="iconText.Visible = False";
_icontext.setVisible(__c.False);
 };
 }
};
 //BA.debugLineNum = 313;BA.debugLine="End Sub";
return "";
}
public String  _curveto(b4a.example.bcpath _path1,float _controlpointx,float _controlpointy,float _targetx,float _targety) throws Exception{
b4a.example.bcpath._internalbcpathpointdata _lastpoint = null;
float _currentx = 0f;
float _currenty = 0f;
int _numberofsteps = 0;
float _dt = 0f;
float _t = 0f;
int _i = 0;
float _tt1 = 0f;
float _tt2 = 0f;
float _tt3 = 0f;
float _x = 0f;
float _y = 0f;
 //BA.debugLineNum = 815;BA.debugLine="Private Sub CurveTo (Path1 As BCPath, ControlPoint";
 //BA.debugLineNum = 816;BA.debugLine="Dim LastPoint As InternalBCPathPointData = Path1.";
_lastpoint = (b4a.example.bcpath._internalbcpathpointdata)(_path1._points.Get((int) (_path1._points.getSize()-1)));
 //BA.debugLineNum = 817;BA.debugLine="Dim CurrentX As Float = LastPoint.X";
_currentx = _lastpoint.X;
 //BA.debugLineNum = 818;BA.debugLine="Dim Currenty As Float = LastPoint.Y";
_currenty = _lastpoint.Y;
 //BA.debugLineNum = 819;BA.debugLine="Dim NumberOfSteps As Int = 100 '<--- change as ne";
_numberofsteps = (int) (100);
 //BA.debugLineNum = 820;BA.debugLine="Dim dt As Float = 1 / NumberOfSteps";
_dt = (float) (1/(double)_numberofsteps);
 //BA.debugLineNum = 821;BA.debugLine="Dim t As Float = dt";
_t = _dt;
 //BA.debugLineNum = 822;BA.debugLine="For i = 1 To NumberOfSteps";
{
final int step7 = 1;
final int limit7 = _numberofsteps;
_i = (int) (1) ;
for (;_i <= limit7 ;_i = _i + step7 ) {
 //BA.debugLineNum = 823;BA.debugLine="Dim tt1 As Float =  (1 - t) * (1 - t)";
_tt1 = (float) ((1-_t)*(1-_t));
 //BA.debugLineNum = 824;BA.debugLine="Dim tt2 As Float = 2 * (1 - t) * t";
_tt2 = (float) (2*(1-_t)*_t);
 //BA.debugLineNum = 825;BA.debugLine="Dim tt3 As Float = t * t";
_tt3 = (float) (_t*_t);
 //BA.debugLineNum = 826;BA.debugLine="Dim x As Float = tt1 * CurrentX + tt2 * ControlP";
_x = (float) (_tt1*_currentx+_tt2*_controlpointx+_tt3*_targetx);
 //BA.debugLineNum = 827;BA.debugLine="Dim y As Float = tt1 * Currenty + tt2 * ControlP";
_y = (float) (_tt1*_currenty+_tt2*_controlpointy+_tt3*_targety);
 //BA.debugLineNum = 828;BA.debugLine="Path1.LineTo(x, y)";
_path1._lineto(_x,_y);
 //BA.debugLineNum = 829;BA.debugLine="t = t + dt";
_t = (float) (_t+_dt);
 }
};
 //BA.debugLineNum = 831;BA.debugLine="End Sub";
return "";
}
public String  _designercreateview(Object _base,anywheresoftware.b4a.objects.LabelWrapper _lbl,anywheresoftware.b4a.objects.collections.Map _props) throws Exception{
 //BA.debugLineNum = 76;BA.debugLine="Public Sub DesignerCreateView (Base As Object, Lbl";
 //BA.debugLineNum = 77;BA.debugLine="designerLbl=Lbl";
_designerlbl = (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_lbl.getObject()));
 //BA.debugLineNum = 78;BA.debugLine="mBase = Base";
_mbase = (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_base));
 //BA.debugLineNum = 79;BA.debugLine="mBase.Color = xui.Color_Transparent";
_mbase.setColor(_xui.Color_Transparent);
 //BA.debugLineNum = 80;BA.debugLine="AbsoluteWidth= Min(mBase.Parent.Width,mBase.Paren";
_absolutewidth = (int) (__c.Min(_mbase.getParent().getWidth(),_mbase.getParent().getHeight()));
 //BA.debugLineNum = 81;BA.debugLine="MenuHeight = (AbsoluteWidth/7) + ((AbsoluteWidth/";
_menuheight = (int) ((_absolutewidth/(double)7)+((_absolutewidth/(double)7)/(double)4));
 //BA.debugLineNum = 82;BA.debugLine="mBase.Height = MenuHeight";
_mbase.setHeight(_menuheight);
 //BA.debugLineNum = 83;BA.debugLine="mBase.Top = mBase.Parent.Height - mBase.Height";
_mbase.setTop((int) (_mbase.getParent().getHeight()-_mbase.getHeight()));
 //BA.debugLineNum = 84;BA.debugLine="circleRadius = AbsoluteWidth/7";
_circleradius = (int) (_absolutewidth/(double)7);
 //BA.debugLineNum = 86;BA.debugLine="BackgroundColor = xui.PaintOrColorToColor(Props.G";
_backgroundcolor = _xui.PaintOrColorToColor(_props.Get((Object)("BackgroundColor")));
 //BA.debugLineNum = 87;BA.debugLine="IconColor = xui.PaintOrColorToColor(Props.Get(\"Ic";
_iconcolor = _xui.PaintOrColorToColor(_props.Get((Object)("IconColor")));
 //BA.debugLineNum = 88;BA.debugLine="IconSize = xui.PaintOrColorToColor(Props.Get(\"Ico";
_iconsize = _xui.PaintOrColorToColor(_props.Get((Object)("IconSize")));
 //BA.debugLineNum = 89;BA.debugLine="TextSize = xui.PaintOrColorToColor(Props.Get(\"Tex";
_textsize = _xui.PaintOrColorToColor(_props.Get((Object)("TextSize")));
 //BA.debugLineNum = 90;BA.debugLine="TextColor = xui.PaintOrColorToColor(Props.Get(\"Te";
_textcolor = _xui.PaintOrColorToColor(_props.Get((Object)("TextColor")));
 //BA.debugLineNum = 91;BA.debugLine="SelectedIconColor = xui.PaintOrColorToColor(Props";
_selectediconcolor = _xui.PaintOrColorToColor(_props.Get((Object)("SelectedIconColor")));
 //BA.debugLineNum = 92;BA.debugLine="animDuration = Props.Get(\"AnimationDuration\")";
_animduration = (int)(BA.ObjectToNumber(_props.Get((Object)("AnimationDuration"))));
 //BA.debugLineNum = 93;BA.debugLine="TabCount = Props.Get(\"TabCount\")";
_tabcount = (int)(BA.ObjectToNumber(_props.Get((Object)("TabCount"))));
 //BA.debugLineNum = 94;BA.debugLine="ActiveTab = Props.Get(\"ActiveTab\")";
_activetab = (int)(BA.ObjectToNumber(_props.Get((Object)("ActiveTab"))));
 //BA.debugLineNum = 95;BA.debugLine="ShadowColor = Props.Get(\"ShadowColor\")";
_shadowcolor = BA.ObjectToString(_props.Get((Object)("ShadowColor")));
 //BA.debugLineNum = 96;BA.debugLine="If TabCount = 5 Then";
if (_tabcount==5) { 
 //BA.debugLineNum = 97;BA.debugLine="CurrentTab = ActiveTab";
_currenttab = _activetab;
 }else {
 //BA.debugLineNum = 99;BA.debugLine="If ActiveTab > TabCount Then";
if (_activetab>_tabcount) { 
 //BA.debugLineNum = 100;BA.debugLine="CurrentTab = 2";
_currenttab = (int) (2);
 }else {
 //BA.debugLineNum = 102;BA.debugLine="CurrentTab = ActiveTab";
_currenttab = _activetab;
 };
 };
 //BA.debugLineNum = 106;BA.debugLine="Select Props.Get(\"IconAppear\")";
switch (BA.switchObjectToInt(_props.Get((Object)("IconAppear")),(Object)("FROM EDGE"),(Object)("FROM CENTER"),(Object)("FADE IN"),(Object)("NO ANIMATION"))) {
case 0: {
 //BA.debugLineNum = 107;BA.debugLine="Case \"FROM EDGE\": IconAppearStyle = ICON_APPEAR_";
_iconappearstyle = _icon_appear_from_edge;
 break; }
case 1: {
 //BA.debugLineNum = 108;BA.debugLine="Case \"FROM CENTER\": IconAppearStyle = ICON_APPEA";
_iconappearstyle = _icon_appear_from_center;
 break; }
case 2: {
 //BA.debugLineNum = 109;BA.debugLine="Case \"FADE IN\": IconAppearStyle = ICON_APPEAR_FA";
_iconappearstyle = _icon_appear_fade_in;
 break; }
case 3: {
 //BA.debugLineNum = 110;BA.debugLine="Case \"NO ANIMATION\": IconAppearStyle = ICON_APPE";
_iconappearstyle = _icon_appear_no_animation;
 break; }
}
;
 //BA.debugLineNum = 113;BA.debugLine="Select Props.Get(\"AnimationType\")";
switch (BA.switchObjectToInt(_props.Get((Object)("AnimationType")),(Object)("ELASTIC OUT"),(Object)("ELASTIC IN"),(Object)("EASE OUT"),(Object)("EASE IN"),(Object)("NONE"))) {
case 0: {
 //BA.debugLineNum = 114;BA.debugLine="Case \"ELASTIC OUT\": AnimationType = ANIMATION_TY";
_animationtype = _animation_type_elastic_out;
 break; }
case 1: {
 //BA.debugLineNum = 115;BA.debugLine="Case \"ELASTIC IN\": AnimationType = ANIMATION_TYP";
_animationtype = _animation_type_elastic_in;
 break; }
case 2: {
 //BA.debugLineNum = 116;BA.debugLine="Case \"EASE OUT\": AnimationType = ANIMATION_TYPE_";
_animationtype = _animation_type_ease_out;
 break; }
case 3: {
 //BA.debugLineNum = 117;BA.debugLine="Case \"EASE IN\": AnimationType = ANIMATION_TYPE_E";
_animationtype = _animation_type_ease_in;
 break; }
case 4: {
 //BA.debugLineNum = 118;BA.debugLine="Case \"NONE\": AnimationType = ANIMATION_TYPE_NONE";
_animationtype = _animation_type_none;
 break; }
}
;
 //BA.debugLineNum = 121;BA.debugLine="DrawView";
_drawview();
 //BA.debugLineNum = 123;BA.debugLine="End Sub";
return "";
}
public String  _drawcircle() throws Exception{
anywheresoftware.b4a.objects.B4XCanvas _circle = null;
int _innerradius = 0;
int _i = 0;
 //BA.debugLineNum = 460;BA.debugLine="Private Sub DrawCircle";
 //BA.debugLineNum = 461;BA.debugLine="Dim circle As B4XCanvas";
_circle = new anywheresoftware.b4a.objects.B4XCanvas();
 //BA.debugLineNum = 462;BA.debugLine="circle.Initialize(TabCircle)";
_circle.Initialize(_tabcircle);
 //BA.debugLineNum = 463;BA.debugLine="Dim innerRadius As Int = Min(mBase.Parent.Width,m";
_innerradius = (int) (__c.Min(_mbase.getParent().getWidth(),_mbase.getParent().getHeight())/(double)8);
 //BA.debugLineNum = 464;BA.debugLine="For i=1 To 10";
{
final int step4 = 1;
final int limit4 = (int) (10);
_i = (int) (1) ;
for (;_i <= limit4 ;_i = _i + step4 ) {
 //BA.debugLineNum = 465;BA.debugLine="If ShadowColor=\"Dark\" Then";
if ((_shadowcolor).equals("Dark")) { 
 //BA.debugLineNum = 466;BA.debugLine="circle.DrawCircle(circleRadius/2,(circleRadius/";
_circle.DrawCircle((float) (_circleradius/(double)2),(float) ((_circleradius/(double)2)+2),(float) ((_innerradius/(double)2)+_i),_xui.Color_ARGB((int) (3),(int) (0),(int) (0),(int) (0)),__c.True,(float) (0));
 }else if((_shadowcolor).equals("Light")) { 
 //BA.debugLineNum = 468;BA.debugLine="circle.DrawCircle(circleRadius/2,(circleRadius/";
_circle.DrawCircle((float) (_circleradius/(double)2),(float) ((_circleradius/(double)2)+2),(float) ((_innerradius/(double)2)+_i),_xui.Color_ARGB((int) (3),(int) (255),(int) (255),(int) (255)),__c.True,(float) (0));
 };
 }
};
 //BA.debugLineNum = 472;BA.debugLine="circle.DrawCircle(circleRadius/2,circleRadius/2,i";
_circle.DrawCircle((float) (_circleradius/(double)2),(float) (_circleradius/(double)2),(float) (_innerradius/(double)2),_backgroundcolor,__c.True,(float) (0));
 //BA.debugLineNum = 473;BA.debugLine="circle.Invalidate";
_circle.Invalidate();
 //BA.debugLineNum = 474;BA.debugLine="End Sub";
return "";
}
public String  _drawcurve() throws Exception{
int[] _backargb = null;
anywheresoftware.b4a.objects.B4XCanvas _curve = null;
anywheresoftware.b4a.objects.B4XCanvas.B4XRect _rect = null;
b4a.example.bitmapcreator _bezierview = null;
b4a.example.bcpath _bezierpath = null;
double _swidth = 0;
double _sheight = 0;
double _curveheight = 0;
int _shadow = 0;
 //BA.debugLineNum = 406;BA.debugLine="Private Sub DrawCurve";
 //BA.debugLineNum = 407;BA.debugLine="Dim backARGB() As Int = GetARGB(BackgroundColor)";
_backargb = _getargb(_backgroundcolor);
 //BA.debugLineNum = 408;BA.debugLine="Dim curve As B4XCanvas";
_curve = new anywheresoftware.b4a.objects.B4XCanvas();
 //BA.debugLineNum = 409;BA.debugLine="curve.Initialize(TabCurve)";
_curve.Initialize(_tabcurve);
 //BA.debugLineNum = 410;BA.debugLine="Dim rect As B4XRect";
_rect = new anywheresoftware.b4a.objects.B4XCanvas.B4XRect();
 //BA.debugLineNum = 411;BA.debugLine="Dim BezierView As BitmapCreator";
_bezierview = new b4a.example.bitmapcreator();
 //BA.debugLineNum = 412;BA.debugLine="Dim BezierPath As BCPath";
_bezierpath = new b4a.example.bcpath();
 //BA.debugLineNum = 413;BA.debugLine="Dim sWidth As Double = 1000";
_swidth = 1000;
 //BA.debugLineNum = 414;BA.debugLine="Dim sHeight As Double = 500";
_sheight = 500;
 //BA.debugLineNum = 416;BA.debugLine="Dim curveHeight As Double = sHeight- sHeight/5 -";
_curveheight = _sheight-_sheight/(double)5-__c.DipToCurrent((int) (3));
 //BA.debugLineNum = 422;BA.debugLine="BezierPath.Initialize(0, 0)";
_bezierpath._initialize(ba,(float) (0),(float) (0));
 //BA.debugLineNum = 423;BA.debugLine="CurveTo(BezierPath, sWidth/9, 0, sWidth/7, (sHeig";
_curveto(_bezierpath,(float) (_swidth/(double)9),(float) (0),(float) (_swidth/(double)7),(float) ((_sheight/(double)3)));
 //BA.debugLineNum = 424;BA.debugLine="CurveTo(BezierPath, sWidth/5 + 5dip, curveHeight";
_curveto(_bezierpath,(float) (_swidth/(double)5+__c.DipToCurrent((int) (5))),(float) (_curveheight-__c.DipToCurrent((int) (8))),(float) (_swidth/(double)2),(float) (_curveheight));
 //BA.debugLineNum = 425;BA.debugLine="CurveTo(BezierPath, (sWidth-sWidth/5) - 5dip, cur";
_curveto(_bezierpath,(float) ((_swidth-_swidth/(double)5)-__c.DipToCurrent((int) (5))),(float) (_curveheight-__c.DipToCurrent((int) (8))),(float) (_swidth-(_swidth/(double)7)),(float) ((_sheight/(double)3)));
 //BA.debugLineNum = 426;BA.debugLine="CurveTo(BezierPath, (sWidth-sWidth/9)-2dip, 0, sW";
_curveto(_bezierpath,(float) ((_swidth-_swidth/(double)9)-__c.DipToCurrent((int) (2))),(float) (0),(float) (_swidth),(float) (0));
 //BA.debugLineNum = 427;BA.debugLine="BezierPath.LineTo(sWidth,sHeight)";
_bezierpath._lineto((float) (_swidth),(float) (_sheight));
 //BA.debugLineNum = 428;BA.debugLine="BezierPath.LineTo(0,sHeight)";
_bezierpath._lineto((float) (0),(float) (_sheight));
 //BA.debugLineNum = 429;BA.debugLine="BezierPath.LineTo(0,0)";
_bezierpath._lineto((float) (0),(float) (0));
 //BA.debugLineNum = 431;BA.debugLine="Dim shadow As Int";
_shadow = 0;
 //BA.debugLineNum = 432;BA.debugLine="If ShadowColor=\"Dark\" Then";
if ((_shadowcolor).equals("Dark")) { 
 //BA.debugLineNum = 433;BA.debugLine="shadow = xui.Color_ARGB(backARGB(0),Max(0,backAR";
_shadow = _xui.Color_ARGB(_backargb[(int) (0)],(int) (__c.Max(0,_backargb[(int) (1)]-20)),(int) (__c.Max(0,_backargb[(int) (2)]-20)),(int) (__c.Max(0,_backargb[(int) (3)]-20)));
 }else if((_shadowcolor).equals("Light")) { 
 //BA.debugLineNum = 435;BA.debugLine="shadow = xui.Color_ARGB(backARGB(0),Min(255,back";
_shadow = _xui.Color_ARGB(_backargb[(int) (0)],(int) (__c.Min(255,_backargb[(int) (1)]+20)),(int) (__c.Min(255,_backargb[(int) (2)]+20)),(int) (__c.Min(255,_backargb[(int) (3)]+20)));
 };
 //BA.debugLineNum = 438;BA.debugLine="BezierView.Initialize(sWidth,sHeight)";
_bezierview._initialize(ba,(int) (_swidth),(int) (_sheight));
 //BA.debugLineNum = 439;BA.debugLine="BezierView.DrawPath(BezierPath,BackgroundColor,Tr";
_bezierview._drawpath(_bezierpath,_backgroundcolor,__c.True,(int) (0));
 //BA.debugLineNum = 441;BA.debugLine="BezierView.DrawPath(BezierPath,shadow,False,10)";
_bezierview._drawpath(_bezierpath,_shadow,__c.False,(int) (10));
 //BA.debugLineNum = 446;BA.debugLine="rect.Initialize(TabContainer.Width - (AbsoluteWid";
_rect.Initialize((float) (_tabcontainer.getWidth()-(_absolutewidth/(double)5)/(double)2-4),(float) (0),(float) (_tabcontainer.getWidth()+(_absolutewidth/(double)5)/(double)2+4),(float) (_tabcontainer.getHeight()));
 //BA.debugLineNum = 447;BA.debugLine="curve.DrawBitmap(BezierView.Bitmap,rect)";
_curve.DrawBitmap((android.graphics.Bitmap)(_bezierview._getbitmap().getObject()),_rect);
 //BA.debugLineNum = 449;BA.debugLine="rect.Initialize(0,0,TabContainer.Width -(Absolute";
_rect.Initialize((float) (0),(float) (0),(float) (_tabcontainer.getWidth()-(_absolutewidth/(double)5)/(double)2),(float) (_tabcontainer.getHeight()));
 //BA.debugLineNum = 450;BA.debugLine="curve.DrawRect(rect,BackgroundColor,True,0)";
_curve.DrawRect(_rect,_backgroundcolor,__c.True,(float) (0));
 //BA.debugLineNum = 451;BA.debugLine="rect.Initialize(TabContainer.Width +(AbsoluteWidt";
_rect.Initialize((float) (_tabcontainer.getWidth()+(_absolutewidth/(double)5)/(double)2),(float) (0),(float) (_tabcurve.getWidth()),(float) (_tabcontainer.getHeight()));
 //BA.debugLineNum = 452;BA.debugLine="curve.DrawRect(rect,BackgroundColor,True,0)";
_curve.DrawRect(_rect,_backgroundcolor,__c.True,(float) (0));
 //BA.debugLineNum = 453;BA.debugLine="curve.DrawLine(0,0,TabContainer.Width -(AbsoluteW";
_curve.DrawLine((float) (0),(float) (0),(float) (_tabcontainer.getWidth()-(_absolutewidth/(double)5)/(double)2),(float) (0),_shadow,(float) (4));
 //BA.debugLineNum = 454;BA.debugLine="curve.DrawLine(TabContainer.Width +(AbsoluteWidth";
_curve.DrawLine((float) (_tabcontainer.getWidth()+(_absolutewidth/(double)5)/(double)2),(float) (0),(float) (_tabcontainer.getWidth()*2),(float) (0),_shadow,(float) (4));
 //BA.debugLineNum = 455;BA.debugLine="curve.DrawLine(0,TabContainer.Height,TabContainer";
_curve.DrawLine((float) (0),(float) (_tabcontainer.getHeight()),(float) (_tabcontainer.getWidth()*2),(float) (_tabcontainer.getHeight()),_backgroundcolor,(float) (4));
 //BA.debugLineNum = 457;BA.debugLine="curve.Invalidate";
_curve.Invalidate();
 //BA.debugLineNum = 458;BA.debugLine="End Sub";
return "";
}
public String  _drawview() throws Exception{
anywheresoftware.b4a.objects.LabelWrapper _icon = null;
anywheresoftware.b4a.objects.ImageViewWrapper _iconimg = null;
anywheresoftware.b4a.objects.B4XViewWrapper _lw = null;
anywheresoftware.b4a.objects.B4XViewWrapper _rw = null;
anywheresoftware.b4a.objects.B4XViewWrapper _cw = null;
 //BA.debugLineNum = 136;BA.debugLine="Private Sub DrawView";
 //BA.debugLineNum = 137;BA.debugLine="mBase.RemoveAllViews";
_mbase.RemoveAllViews();
 //BA.debugLineNum = 138;BA.debugLine="Tabs.Clear";
_tabs.Clear();
 //BA.debugLineNum = 139;BA.debugLine="If TabCircle.IsInitialized Then TabCircle.RemoveA";
if (_tabcircle.IsInitialized()) { 
_tabcircle.RemoveAllViews();};
 //BA.debugLineNum = 141;BA.debugLine="TabContainer = xui.CreatePanel(\"\")";
_tabcontainer = _xui.CreatePanel(ba,"");
 //BA.debugLineNum = 142;BA.debugLine="mBase.AddView(TabContainer,0,mBase.Height/4 ,mBas";
_mbase.AddView((android.view.View)(_tabcontainer.getObject()),(int) (0),(int) (_mbase.getHeight()/(double)4),_mbase.getWidth(),(int) (_mbase.getHeight()-(_mbase.getHeight()/(double)4)));
 //BA.debugLineNum = 143;BA.debugLine="TabCurve = xui.CreatePanel(\"\")";
_tabcurve = _xui.CreatePanel(ba,"");
 //BA.debugLineNum = 144;BA.debugLine="TabContainer.AddView(TabCurve,((TabContainer.Widt";
_tabcontainer.AddView((android.view.View)(_tabcurve.getObject()),(int) (((_tabcontainer.getWidth()/(double)_tabcount)*(_currenttab-1))+((_tabcontainer.getWidth()/(double)_tabcount)/(double)2)-((_tabcontainer.getWidth()*2)/(double)2)),(int) (0),(int) (_tabcontainer.getWidth()*2),_tabcontainer.getHeight());
 //BA.debugLineNum = 145;BA.debugLine="DrawCurve";
_drawcurve();
 //BA.debugLineNum = 147;BA.debugLine="TabCircle = xui.CreatePanel(\"\")";
_tabcircle = _xui.CreatePanel(ba,"");
 //BA.debugLineNum = 148;BA.debugLine="mBase.AddView(TabCircle,((TabContainer.Width/TabC";
_mbase.AddView((android.view.View)(_tabcircle.getObject()),(int) (((_tabcontainer.getWidth()/(double)_tabcount)*(_currenttab-1))+((_tabcontainer.getWidth()/(double)_tabcount)/(double)2)-(_circleradius/(double)2)),__c.DipToCurrent((int) (1)),_circleradius,_circleradius);
 //BA.debugLineNum = 149;BA.debugLine="DrawCircle";
_drawcircle();
 //BA.debugLineNum = 151;BA.debugLine="Dim icon As Label";
_icon = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 152;BA.debugLine="icon.Initialize(\"\")";
_icon.Initialize(ba,"");
 //BA.debugLineNum = 159;BA.debugLine="icon.TextColor = SelectedIconColor";
_icon.setTextColor(_selectediconcolor);
 //BA.debugLineNum = 160;BA.debugLine="icon.Typeface = Typeface.FONTAWESOME";
_icon.setTypeface(__c.Typeface.getFONTAWESOME());
 //BA.debugLineNum = 161;BA.debugLine="icon.TextSize = 20";
_icon.setTextSize((float) (20));
 //BA.debugLineNum = 162;BA.debugLine="icon.Gravity = Gravity.CENTER_HORIZONTAL + Gravit";
_icon.setGravity((int) (__c.Gravity.CENTER_HORIZONTAL+__c.Gravity.CENTER_VERTICAL));
 //BA.debugLineNum = 169;BA.debugLine="TabCircle.AddView(icon,0,0,TabCircle.Width,TabCir";
_tabcircle.AddView((android.view.View)(_icon.getObject()),(int) (0),(int) (0),_tabcircle.getWidth(),_tabcircle.getHeight());
 //BA.debugLineNum = 171;BA.debugLine="Dim iconImg As ImageView";
_iconimg = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 172;BA.debugLine="iconImg.Initialize(\"\")";
_iconimg.Initialize(ba,"");
 //BA.debugLineNum = 173;BA.debugLine="TabCircle.AddView(iconImg,(TabCircle.Width/2)-((T";
_tabcircle.AddView((android.view.View)(_iconimg.getObject()),(int) ((_tabcircle.getWidth()/(double)2)-((_tabcircle.getWidth()/(double)2)/(double)2)),(int) ((_tabcircle.getHeight()/(double)2)-((_tabcircle.getHeight()/(double)2)/(double)2)),(int) (_tabcircle.getWidth()/(double)2),(int) (_tabcircle.getHeight()/(double)2));
 //BA.debugLineNum = 175;BA.debugLine="Dim lw,rw,cw As B4XView";
_lw = new anywheresoftware.b4a.objects.B4XViewWrapper();
_rw = new anywheresoftware.b4a.objects.B4XViewWrapper();
_cw = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 176;BA.debugLine="lw = xui.CreatePanel(\"\")";
_lw = _xui.CreatePanel(ba,"");
 //BA.debugLineNum = 177;BA.debugLine="rw = xui.CreatePanel(\"\")";
_rw = _xui.CreatePanel(ba,"");
 //BA.debugLineNum = 178;BA.debugLine="cw = xui.CreatePanel(\"\")";
_cw = _xui.CreatePanel(ba,"");
 //BA.debugLineNum = 179;BA.debugLine="lw.Color = BackgroundColor";
_lw.setColor(_backgroundcolor);
 //BA.debugLineNum = 180;BA.debugLine="rw.Color = BackgroundColor";
_rw.setColor(_backgroundcolor);
 //BA.debugLineNum = 181;BA.debugLine="cw.Color = BackgroundColor";
_cw.setColor(_backgroundcolor);
 //BA.debugLineNum = 182;BA.debugLine="TabCircle.AddView(lw,(TabCircle.Height/5),(TabCir";
_tabcircle.AddView((android.view.View)(_lw.getObject()),(int) ((_tabcircle.getHeight()/(double)5)),(int) ((_tabcircle.getHeight()/(double)2)-((_tabcircle.getHeight()/(double)2)/(double)2)),__c.DipToCurrent((int) (1)),(int) (_tabcircle.getHeight()/(double)2));
 //BA.debugLineNum = 183;BA.debugLine="TabCircle.AddView(rw,TabCircle.Width-(TabCircle.H";
_tabcircle.AddView((android.view.View)(_rw.getObject()),(int) (_tabcircle.getWidth()-(_tabcircle.getHeight()/(double)5)-__c.DipToCurrent((int) (1))),(int) ((_tabcircle.getHeight()/(double)2)-((_tabcircle.getHeight()/(double)2)/(double)2)),__c.DipToCurrent((int) (1)),(int) (_tabcircle.getHeight()/(double)2));
 //BA.debugLineNum = 184;BA.debugLine="TabCircle.AddView(cw,TabCircle.Width/2,(TabCircle";
_tabcircle.AddView((android.view.View)(_cw.getObject()),(int) (_tabcircle.getWidth()/(double)2),(int) ((_tabcircle.getHeight()/(double)2)-((_tabcircle.getHeight()/(double)2)/(double)2)),(int) (0),(int) (_tabcircle.getHeight()/(double)2));
 //BA.debugLineNum = 186;BA.debugLine="Tab1 = xui.CreatePanel(\"IconTab\")";
_tab1 = _xui.CreatePanel(ba,"IconTab");
 //BA.debugLineNum = 187;BA.debugLine="Tabs.Add(Tab1)";
_tabs.Add((Object)(_tab1.getObject()));
 //BA.debugLineNum = 188;BA.debugLine="Tab2 = xui.CreatePanel(\"IconTab\")";
_tab2 = _xui.CreatePanel(ba,"IconTab");
 //BA.debugLineNum = 189;BA.debugLine="Tabs.Add(Tab2)";
_tabs.Add((Object)(_tab2.getObject()));
 //BA.debugLineNum = 190;BA.debugLine="Tab3 = xui.CreatePanel(\"IconTab\")";
_tab3 = _xui.CreatePanel(ba,"IconTab");
 //BA.debugLineNum = 191;BA.debugLine="Tabs.Add(Tab3)";
_tabs.Add((Object)(_tab3.getObject()));
 //BA.debugLineNum = 192;BA.debugLine="Tab4 = xui.CreatePanel(\"IconTab\")";
_tab4 = _xui.CreatePanel(ba,"IconTab");
 //BA.debugLineNum = 193;BA.debugLine="Tabs.Add(Tab4)";
_tabs.Add((Object)(_tab4.getObject()));
 //BA.debugLineNum = 194;BA.debugLine="Tab5 = xui.CreatePanel(\"IconTab\")";
_tab5 = _xui.CreatePanel(ba,"IconTab");
 //BA.debugLineNum = 195;BA.debugLine="Tabs.Add(Tab5)";
_tabs.Add((Object)(_tab5.getObject()));
 //BA.debugLineNum = 197;BA.debugLine="CreateTab";
_createtab();
 //BA.debugLineNum = 198;BA.debugLine="SetCircleIcon";
_setcircleicon();
 //BA.debugLineNum = 199;BA.debugLine="End Sub";
return "";
}
public int[]  _getargb(int _color) throws Exception{
int[] _res = null;
 //BA.debugLineNum = 834;BA.debugLine="Private Sub GetARGB(Color As Int) As Int()";
 //BA.debugLineNum = 835;BA.debugLine="Private res(4) As Int";
_res = new int[(int) (4)];
;
 //BA.debugLineNum = 836;BA.debugLine="res(0) = Bit.UnsignedShiftRight(Bit.And(Color, 0x";
_res[(int) (0)] = __c.Bit.UnsignedShiftRight(__c.Bit.And(_color,(int) (0xff000000)),(int) (24));
 //BA.debugLineNum = 837;BA.debugLine="res(1) = Bit.UnsignedShiftRight(Bit.And(Color, 0x";
_res[(int) (1)] = __c.Bit.UnsignedShiftRight(__c.Bit.And(_color,(int) (0xff0000)),(int) (16));
 //BA.debugLineNum = 838;BA.debugLine="res(2) = Bit.UnsignedShiftRight(Bit.And(Color, 0x";
_res[(int) (2)] = __c.Bit.UnsignedShiftRight(__c.Bit.And(_color,(int) (0xff00)),(int) (8));
 //BA.debugLineNum = 839;BA.debugLine="res(3) = Bit.And(Color, 0xff)";
_res[(int) (3)] = __c.Bit.And(_color,(int) (0xff));
 //BA.debugLineNum = 840;BA.debugLine="Return res";
if (true) return _res;
 //BA.debugLineNum = 841;BA.debugLine="End Sub";
return null;
}
public int  _getcurrenttab() throws Exception{
 //BA.debugLineNum = 607;BA.debugLine="Public Sub GetCurrentTab As Int";
 //BA.debugLineNum = 608;BA.debugLine="Return CurrentTab";
if (true) return _currenttab;
 //BA.debugLineNum = 609;BA.debugLine="End Sub";
return 0;
}
public boolean  _getenabletab(int _tabid) throws Exception{
 //BA.debugLineNum = 711;BA.debugLine="Public Sub GetEnableTab(TabID As Int) As Boolean";
 //BA.debugLineNum = 712;BA.debugLine="If TabID >= 1 And TabID <= TabCount Then";
if (_tabid>=1 && _tabid<=_tabcount) { 
 //BA.debugLineNum = 713;BA.debugLine="Return enabled.get(TabID-1)";
if (true) return BA.ObjectToBoolean(_enabled.Get((int) (_tabid-1)));
 }else {
 //BA.debugLineNum = 715;BA.debugLine="Log(\"Invalid Tab ID\")";
__c.LogImpl("983886084","Invalid Tab ID",0);
 //BA.debugLineNum = 716;BA.debugLine="Return False";
if (true) return __c.False;
 };
 //BA.debugLineNum = 718;BA.debugLine="End Sub";
return false;
}
public int  _getheight() throws Exception{
 //BA.debugLineNum = 695;BA.debugLine="Public Sub getHeight As Int";
 //BA.debugLineNum = 696;BA.debugLine="Return mBase.Height";
if (true) return _mbase.getHeight();
 //BA.debugLineNum = 697;BA.debugLine="End Sub";
return 0;
}
public String  _icontab_click() throws Exception{
 //BA.debugLineNum = 741;BA.debugLine="Private Sub IconTab_Click";
 //BA.debugLineNum = 742;BA.debugLine="TriggerTabClickEvent(Sender,True)";
_triggertabclickevent((anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(__c.Sender(ba))),__c.True);
 //BA.debugLineNum = 743;BA.debugLine="End Sub";
return "";
}
public String  _initialize(anywheresoftware.b4a.BA _ba,Object _callback,String _eventname) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 65;BA.debugLine="Public Sub Initialize (Callback As Object, EventNa";
 //BA.debugLineNum = 66;BA.debugLine="mEventName = EventName";
_meventname = _eventname;
 //BA.debugLineNum = 67;BA.debugLine="mCallBack = Callback";
_mcallback = _callback;
 //BA.debugLineNum = 68;BA.debugLine="IconList.Initialize";
_iconlist.Initialize();
 //BA.debugLineNum = 69;BA.debugLine="Tabs.Initialize";
_tabs.Initialize();
 //BA.debugLineNum = 70;BA.debugLine="badge.Initialize";
_badge.Initialize();
 //BA.debugLineNum = 71;BA.debugLine="enabled.Initialize2(Array As Boolean(True,True,Tr";
_enabled.Initialize2(anywheresoftware.b4a.keywords.Common.ArrayToList(new boolean[]{__c.True,__c.True,__c.True,__c.True,__c.True}));
 //BA.debugLineNum = 72;BA.debugLine="End Sub";
return "";
}
public String  _removebadge(int _tabid) throws Exception{
anywheresoftware.b4a.objects.B4XViewWrapper _t = null;
 //BA.debugLineNum = 682;BA.debugLine="Public Sub RemoveBadge(TabID As Int)";
 //BA.debugLineNum = 683;BA.debugLine="If TabID >= 1 And TabID <= TabCount Then";
if (_tabid>=1 && _tabid<=_tabcount) { 
 //BA.debugLineNum = 684;BA.debugLine="badge.set(TabID-1,\"\")";
_badge.Set((int) (_tabid-1),(Object)(""));
 //BA.debugLineNum = 685;BA.debugLine="Dim t As B4XView = Tabs.Get(TabID-1)";
_t = new anywheresoftware.b4a.objects.B4XViewWrapper();
_t = (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_tabs.Get((int) (_tabid-1))));
 //BA.debugLineNum = 686;BA.debugLine="If t.NumberOfViews = 4 Then";
if (_t.getNumberOfViews()==4) { 
 //BA.debugLineNum = 687;BA.debugLine="t.GetView(3).RemoveViewFromParent";
_t.GetView((int) (3)).RemoveViewFromParent();
 };
 }else {
 //BA.debugLineNum = 690;BA.debugLine="Log(\"Invalid Tab ID\")";
__c.LogImpl("983689480","Invalid Tab ID",0);
 };
 //BA.debugLineNum = 692;BA.debugLine="End Sub";
return "";
}
public String  _setanimationtype(int _animation_type) throws Exception{
 //BA.debugLineNum = 626;BA.debugLine="Public Sub SetAnimationType(Animation_Type As Int)";
 //BA.debugLineNum = 627;BA.debugLine="AnimationType = Animation_Type";
_animationtype = _animation_type;
 //BA.debugLineNum = 628;BA.debugLine="End Sub";
return "";
}
public String  _setbadge(int _tabid,int _count,int _backcolor,int _txtcolor) throws Exception{
anywheresoftware.b4a.objects.B4XViewWrapper _t = null;
anywheresoftware.b4a.objects.LabelWrapper _l = null;
anywheresoftware.b4a.objects.B4XViewWrapper _b4xlbl = null;
 //BA.debugLineNum = 655;BA.debugLine="Public Sub SetBadge(TabID As Int, Count As Int, Ba";
 //BA.debugLineNum = 657;BA.debugLine="If TabID >= 1 And TabID <= TabCount Then";
if (_tabid>=1 && _tabid<=_tabcount) { 
 //BA.debugLineNum = 658;BA.debugLine="RemoveBadge(TabID)";
_removebadge(_tabid);
 //BA.debugLineNum = 659;BA.debugLine="If Count>0 Then";
if (_count>0) { 
 //BA.debugLineNum = 660;BA.debugLine="Dim t As B4XView = Tabs.Get(TabID-1)";
_t = new anywheresoftware.b4a.objects.B4XViewWrapper();
_t = (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_tabs.Get((int) (_tabid-1))));
 //BA.debugLineNum = 661;BA.debugLine="Dim l As Label";
_l = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 662;BA.debugLine="l.Initialize(\"\")";
_l.Initialize(ba,"");
 //BA.debugLineNum = 663;BA.debugLine="Dim b4xlbl As B4XView=l";
_b4xlbl = new anywheresoftware.b4a.objects.B4XViewWrapper();
_b4xlbl = (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_l.getObject()));
 //BA.debugLineNum = 664;BA.debugLine="b4xlbl.TextSize=10";
_b4xlbl.setTextSize((float) (10));
 //BA.debugLineNum = 665;BA.debugLine="b4xlbl.TextColor=xui.PaintOrColorToColor(TxtCol";
_b4xlbl.setTextColor(_xui.PaintOrColorToColor((Object)(_txtcolor)));
 //BA.debugLineNum = 666;BA.debugLine="b4xlbl.Text = Count";
_b4xlbl.setText(BA.ObjectToCharSequence(_count));
 //BA.debugLineNum = 667;BA.debugLine="If Count>99 Then b4xlbl.Text = \"99+\"";
if (_count>99) { 
_b4xlbl.setText(BA.ObjectToCharSequence("99+"));};
 //BA.debugLineNum = 668;BA.debugLine="b4xlbl.SetTextAlignment(\"CENTER\",\"CENTER\")";
_b4xlbl.SetTextAlignment("CENTER","CENTER");
 //BA.debugLineNum = 669;BA.debugLine="t.AddView(l,(t.Width/3)*2,5dip,t.Height/3,t.Hei";
_t.AddView((android.view.View)(_l.getObject()),(int) ((_t.getWidth()/(double)3)*2),__c.DipToCurrent((int) (5)),(int) (_t.getHeight()/(double)3),(int) (_t.getHeight()/(double)3));
 //BA.debugLineNum = 671;BA.debugLine="b4xlbl.SetColorAndBorder(xui.PaintOrColorToColo";
_b4xlbl.SetColorAndBorder(_xui.PaintOrColorToColor((Object)(_backcolor)),(int) (0),_xui.Color_Transparent,(int) (_b4xlbl.getHeight()/(double)2));
 //BA.debugLineNum = 673;BA.debugLine="If TabID = CurrentTab Then b4xlbl.Visible = Fal";
if (_tabid==_currenttab) { 
_b4xlbl.setVisible(__c.False);};
 //BA.debugLineNum = 674;BA.debugLine="badge.set(TabID-1,CreateMap(\"count\":Count,\"back";
_badge.Set((int) (_tabid-1),(Object)(__c.createMap(new Object[] {(Object)("count"),(Object)(_count),(Object)("backcolor"),(Object)(_backcolor),(Object)("textcolor"),(Object)(_txtcolor)}).getObject()));
 };
 }else {
 //BA.debugLineNum = 677;BA.debugLine="Log(\"Invalid Tab ID\")";
__c.LogImpl("983623958","Invalid Tab ID",0);
 };
 //BA.debugLineNum = 679;BA.debugLine="End Sub";
return "";
}
public void  _setcircleicon() throws Exception{
ResumableSub_SetCircleIcon rsub = new ResumableSub_SetCircleIcon(this);
rsub.resume(ba, null);
}
public static class ResumableSub_SetCircleIcon extends BA.ResumableSub {
public ResumableSub_SetCircleIcon(com.bwsi.MeterReader.wobblemenu parent) {
this.parent = parent;
}
com.bwsi.MeterReader.wobblemenu parent;
int _id = 0;
anywheresoftware.b4a.objects.LabelWrapper _cl = null;
anywheresoftware.b4a.objects.B4XViewWrapper _mtab = null;
anywheresoftware.b4a.objects.LabelWrapper _tl = null;
anywheresoftware.b4a.objects.ImageViewWrapper _cli = null;
com.bwsi.MeterReader.wobblemenu._icontype _i = null;
anywheresoftware.b4j.object.JavaObject _jo = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 317;BA.debugLine="Dim id As Int = 0";
_id = (int) (0);
 //BA.debugLineNum = 322;BA.debugLine="Select IconAppearStyle";
if (true) break;

case 1:
//select
this.state = 8;
switch (parent._iconappearstyle) {
case 0: {
this.state = 3;
if (true) break;
}
case 1: {
this.state = 5;
if (true) break;
}
case 2: {
this.state = 7;
if (true) break;
}
}
if (true) break;

case 3:
//C
this.state = 8;
 //BA.debugLineNum = 324;BA.debugLine="TabCircle.GetView(id+2).SetLayoutAnimated(0,Tab";
parent._tabcircle.GetView((int) (_id+2)).SetLayoutAnimated((int) (0),parent._tabcircle.GetView((int) (_id+2)).getLeft(),parent._tabcircle.GetView((int) (_id+2)).getTop(),(int) (parent._tabcircle.getWidth()/(double)2),parent._tabcircle.GetView((int) (_id+2)).getHeight());
 //BA.debugLineNum = 325;BA.debugLine="TabCircle.GetView(id+3).SetLayoutAnimated(0,Tab";
parent._tabcircle.GetView((int) (_id+3)).SetLayoutAnimated((int) (0),(int) (parent._tabcircle.getWidth()-(parent._tabcircle.getHeight()/(double)5)-(parent._tabcircle.getWidth()/(double)2)),parent._tabcircle.GetView((int) (3)).getTop(),(int) (parent._tabcircle.getWidth()/(double)2),parent._tabcircle.GetView((int) (3)).getHeight());
 //BA.debugLineNum = 326;BA.debugLine="TabCircle.GetView(id+2).SetVisibleAnimated(0,Tr";
parent._tabcircle.GetView((int) (_id+2)).SetVisibleAnimated((int) (0),parent.__c.True);
 //BA.debugLineNum = 327;BA.debugLine="TabCircle.GetView(id+3).SetVisibleAnimated(0,Tr";
parent._tabcircle.GetView((int) (_id+3)).SetVisibleAnimated((int) (0),parent.__c.True);
 if (true) break;

case 5:
//C
this.state = 8;
 //BA.debugLineNum = 329;BA.debugLine="TabCircle.GetView(id+4).SetLayoutAnimated(0,Tab";
parent._tabcircle.GetView((int) (_id+4)).SetLayoutAnimated((int) (0),parent._tabcircle.GetView((int) (_id+2)).getLeft(),parent._tabcircle.GetView((int) (_id+2)).getTop(),(int) (parent._tabcircle.GetView((int) (_id+3)).getLeft()-parent._tabcircle.GetView((int) (_id+2)).getLeft()),parent._tabcircle.GetView((int) (_id+2)).getHeight());
 //BA.debugLineNum = 330;BA.debugLine="TabCircle.GetView(id+4).SetVisibleAnimated(0,Tr";
parent._tabcircle.GetView((int) (_id+4)).SetVisibleAnimated((int) (0),parent.__c.True);
 if (true) break;

case 7:
//C
this.state = 8;
 //BA.debugLineNum = 332;BA.debugLine="TabCircle.GetView(id).SetVisibleAnimated(0,Fals";
parent._tabcircle.GetView(_id).SetVisibleAnimated((int) (0),parent.__c.False);
 //BA.debugLineNum = 333;BA.debugLine="TabCircle.GetView(id+1).SetVisibleAnimated(0,Fa";
parent._tabcircle.GetView((int) (_id+1)).SetVisibleAnimated((int) (0),parent.__c.False);
 if (true) break;

case 8:
//C
this.state = 9;
;
 //BA.debugLineNum = 335;BA.debugLine="Sleep(1)";
parent.__c.Sleep(ba,this,(int) (1));
this.state = 29;
return;
case 29:
//C
this.state = 9;
;
 //BA.debugLineNum = 337;BA.debugLine="Dim cl As Label = TabCircle.GetView(id)";
_cl = new anywheresoftware.b4a.objects.LabelWrapper();
_cl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(parent._tabcircle.GetView(_id).getObject()));
 //BA.debugLineNum = 338;BA.debugLine="Dim mTab As B4XView = Tabs.Get(CurrentTab-1)";
_mtab = new anywheresoftware.b4a.objects.B4XViewWrapper();
_mtab = (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(parent._tabs.Get((int) (parent._currenttab-1))));
 //BA.debugLineNum = 339;BA.debugLine="Dim tl As Label = mTab.GetView(0)";
_tl = new anywheresoftware.b4a.objects.LabelWrapper();
_tl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_mtab.GetView((int) (0)).getObject()));
 //BA.debugLineNum = 340;BA.debugLine="cl.Text = tl.text";
_cl.setText(BA.ObjectToCharSequence(_tl.getText()));
 //BA.debugLineNum = 342;BA.debugLine="Dim cli As ImageView = TabCircle.GetView(id+1)";
_cli = new anywheresoftware.b4a.objects.ImageViewWrapper();
_cli = (anywheresoftware.b4a.objects.ImageViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ImageViewWrapper(), (android.widget.ImageView)(parent._tabcircle.GetView((int) (_id+1)).getObject()));
 //BA.debugLineNum = 343;BA.debugLine="Dim i As IconType = IconList.Get(CurrentTab-1)";
_i = (com.bwsi.MeterReader.wobblemenu._icontype)(parent._iconlist.Get((int) (parent._currenttab-1)));
 //BA.debugLineNum = 345;BA.debugLine="Dim jo As JavaObject=cli";
_jo = new anywheresoftware.b4j.object.JavaObject();
_jo = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(_cli.getObject()));
 //BA.debugLineNum = 346;BA.debugLine="If i.IconImg.IsInitialized Then";
if (true) break;

case 9:
//if
this.state = 14;
if (_i.IconImg /*anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper*/ .IsInitialized()) { 
this.state = 11;
}else {
this.state = 13;
}if (true) break;

case 11:
//C
this.state = 14;
 //BA.debugLineNum = 347;BA.debugLine="jo.RunMethod(\"setImageBitmap\",Array(i.IconImg))";
_jo.RunMethod("setImageBitmap",new Object[]{(Object)(_i.IconImg /*anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper*/ .getObject())});
 if (true) break;

case 13:
//C
this.state = 14;
 //BA.debugLineNum = 349;BA.debugLine="jo.RunMethod(\"setImageBitmap\",Array(Null))";
_jo.RunMethod("setImageBitmap",new Object[]{parent.__c.Null});
 if (true) break;

case 14:
//C
this.state = 15;
;
 //BA.debugLineNum = 351;BA.debugLine="jo.RunMethod(\"setScaleType\",Array(\"CENTER_INSIDE\"";
_jo.RunMethod("setScaleType",new Object[]{(Object)("CENTER_INSIDE")});
 //BA.debugLineNum = 352;BA.debugLine="jo.RunMethod(\"setColorFilter\",Array(Colors.Transp";
_jo.RunMethod("setColorFilter",new Object[]{(Object)(parent.__c.Colors.Transparent)});
 //BA.debugLineNum = 353;BA.debugLine="If i.tinted Then jo.RunMethod(\"setColorFilter\",Ar";
if (true) break;

case 15:
//if
this.state = 20;
if (_i.tinted /*boolean*/ ) { 
this.state = 17;
;}if (true) break;

case 17:
//C
this.state = 20;
_jo.RunMethod("setColorFilter",new Object[]{(Object)(parent.__c.Colors.RGB(parent._getargb(parent._selectediconcolor)[(int) (1)],parent._getargb(parent._selectediconcolor)[(int) (2)],parent._getargb(parent._selectediconcolor)[(int) (3)]))});
if (true) break;

case 20:
//C
this.state = 21;
;
 //BA.debugLineNum = 381;BA.debugLine="cl.Typeface = tl.Typeface";
_cl.setTypeface(_tl.getTypeface());
 //BA.debugLineNum = 384;BA.debugLine="Select IconAppearStyle";
if (true) break;

case 21:
//select
this.state = 28;
switch (parent._iconappearstyle) {
case 0: {
this.state = 23;
if (true) break;
}
case 1: {
this.state = 25;
if (true) break;
}
case 2: {
this.state = 27;
if (true) break;
}
}
if (true) break;

case 23:
//C
this.state = 28;
 //BA.debugLineNum = 386;BA.debugLine="TabCircle.GetView(id+2).SetLayoutAnimated(800,T";
parent._tabcircle.GetView((int) (_id+2)).SetLayoutAnimated((int) (800),parent._tabcircle.GetView((int) (_id+2)).getLeft(),parent._tabcircle.GetView((int) (_id+2)).getTop(),parent.__c.DipToCurrent((int) (1)),parent._tabcircle.GetView((int) (_id+2)).getHeight());
 //BA.debugLineNum = 387;BA.debugLine="TabCircle.GetView(id+3).SetLayoutAnimated(800,T";
parent._tabcircle.GetView((int) (_id+3)).SetLayoutAnimated((int) (800),(int) (parent._tabcircle.getWidth()-(parent._tabcircle.getHeight()/(double)4)-parent.__c.DipToCurrent((int) (1))),parent._tabcircle.GetView((int) (_id+3)).getTop(),parent.__c.DipToCurrent((int) (1)),parent._tabcircle.GetView((int) (_id+3)).getHeight());
 if (true) break;

case 25:
//C
this.state = 28;
 //BA.debugLineNum = 389;BA.debugLine="TabCircle.GetView(id+4).SetLayoutAnimated(800,T";
parent._tabcircle.GetView((int) (_id+4)).SetLayoutAnimated((int) (800),(int) (parent._tabcircle.getWidth()/(double)2),parent._tabcircle.GetView((int) (_id+2)).getTop(),parent.__c.DipToCurrent((int) (1)),parent._tabcircle.GetView((int) (_id+2)).getHeight());
 if (true) break;

case 27:
//C
this.state = 28;
 //BA.debugLineNum = 391;BA.debugLine="TabCircle.GetView(id).SetVisibleAnimated(800,Tr";
parent._tabcircle.GetView(_id).SetVisibleAnimated((int) (800),parent.__c.True);
 //BA.debugLineNum = 392;BA.debugLine="TabCircle.GetView(id+1).SetVisibleAnimated(800,";
parent._tabcircle.GetView((int) (_id+1)).SetVisibleAnimated((int) (800),parent.__c.True);
 if (true) break;

case 28:
//C
this.state = -1;
;
 //BA.debugLineNum = 396;BA.debugLine="Sleep(400)";
parent.__c.Sleep(ba,this,(int) (400));
this.state = 30;
return;
case 30:
//C
this.state = -1;
;
 //BA.debugLineNum = 400;BA.debugLine="TabCircle.GetView(id+2).SetVisibleAnimated(100,Fa";
parent._tabcircle.GetView((int) (_id+2)).SetVisibleAnimated((int) (100),parent.__c.False);
 //BA.debugLineNum = 401;BA.debugLine="TabCircle.GetView(id+3).SetVisibleAnimated(100,Fa";
parent._tabcircle.GetView((int) (_id+3)).SetVisibleAnimated((int) (100),parent.__c.False);
 //BA.debugLineNum = 402;BA.debugLine="TabCircle.GetView(id+4).SetVisibleAnimated(100,Fa";
parent._tabcircle.GetView((int) (_id+4)).SetVisibleAnimated((int) (100),parent.__c.False);
 //BA.debugLineNum = 404;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public String  _setcurrenttab(int _tabid) throws Exception{
 //BA.debugLineNum = 621;BA.debugLine="Public Sub SetCurrentTab(TabID As Int)";
 //BA.debugLineNum = 622;BA.debugLine="SetCurrentTab2(TabID,True)";
_setcurrenttab2(_tabid,__c.True);
 //BA.debugLineNum = 623;BA.debugLine="End Sub";
return "";
}
public String  _setcurrenttab2(int _tabid,boolean _triggerevent) throws Exception{
 //BA.debugLineNum = 612;BA.debugLine="Public Sub SetCurrentTab2(TabID As Int, triggerEve";
 //BA.debugLineNum = 613;BA.debugLine="If TabID >= 1 And TabID <= TabCount Then";
if (_tabid>=1 && _tabid<=_tabcount) { 
 //BA.debugLineNum = 614;BA.debugLine="TriggerTabClickEvent(Tabs.Get(TabID-1),triggerEv";
_triggertabclickevent((anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_tabs.Get((int) (_tabid-1)))),_triggerevent);
 }else {
 //BA.debugLineNum = 616;BA.debugLine="Log(\"Invalid Tab ID\")";
__c.LogImpl("983296260","Invalid Tab ID",0);
 };
 //BA.debugLineNum = 618;BA.debugLine="End Sub";
return "";
}
public String  _setenabletab(int _tabid,boolean _enable) throws Exception{
anywheresoftware.b4a.objects.B4XViewWrapper _t = null;
 //BA.debugLineNum = 700;BA.debugLine="Public Sub SetEnableTab(TabID As Int, enable As Bo";
 //BA.debugLineNum = 701;BA.debugLine="If TabID >= 1 And TabID <= TabCount Then";
if (_tabid>=1 && _tabid<=_tabcount) { 
 //BA.debugLineNum = 702;BA.debugLine="Dim t As B4XView = Tabs.Get(TabID-1)";
_t = new anywheresoftware.b4a.objects.B4XViewWrapper();
_t = (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_tabs.Get((int) (_tabid-1))));
 //BA.debugLineNum = 703;BA.debugLine="t.Enabled = enable";
_t.setEnabled(_enable);
 //BA.debugLineNum = 704;BA.debugLine="enabled.Set(TabID-1,enable)";
_enabled.Set((int) (_tabid-1),(Object)(_enable));
 }else {
 //BA.debugLineNum = 706;BA.debugLine="Log(\"Invalid Tab ID\")";
__c.LogImpl("983820550","Invalid Tab ID",0);
 };
 //BA.debugLineNum = 708;BA.debugLine="End Sub";
return "";
}
public String  _seticonappearstyle(int _icon_appear_style) throws Exception{
 //BA.debugLineNum = 631;BA.debugLine="Public Sub SetIconAppearStyle(Icon_Appear_Style As";
 //BA.debugLineNum = 632;BA.debugLine="IconAppearStyle = Icon_Appear_Style";
_iconappearstyle = _icon_appear_style;
 //BA.debugLineNum = 633;BA.debugLine="End Sub";
return "";
}
public String  _settabcount(int _count) throws Exception{
 //BA.debugLineNum = 637;BA.debugLine="Public Sub SetTabCount(count As Int)";
 //BA.debugLineNum = 638;BA.debugLine="If count = 3 Or count = 5 Then";
if (_count==3 || _count==5) { 
 //BA.debugLineNum = 639;BA.debugLine="If CurrentTab > count Then";
if (_currenttab>_count) { 
 //BA.debugLineNum = 640;BA.debugLine="Log(\"Current Tab ID: \"&CurrentTab)";
__c.LogImpl("983558403","Current Tab ID: "+BA.NumberToString(_currenttab),0);
 //BA.debugLineNum = 641;BA.debugLine="Log(\"Cannot change tab count.\")";
__c.LogImpl("983558404","Cannot change tab count.",0);
 }else {
 //BA.debugLineNum = 643;BA.debugLine="TabCount = count";
_tabcount = _count;
 //BA.debugLineNum = 644;BA.debugLine="DrawView";
_drawview();
 };
 }else {
 //BA.debugLineNum = 647;BA.debugLine="Log(\"Count must be either 5 or 3.\")";
__c.LogImpl("983558410","Count must be either 5 or 3.",0);
 };
 //BA.debugLineNum = 649;BA.debugLine="End Sub";
return "";
}
public String  _settabtexticon(int _tabid,String _text,String _icon,anywheresoftware.b4a.keywords.constants.TypefaceWrapper _iconfont) throws Exception{
com.bwsi.MeterReader.wobblemenu._icontype _i = null;
anywheresoftware.b4a.objects.B4XViewWrapper _mtab = null;
anywheresoftware.b4a.objects.ImageViewWrapper _iv = null;
anywheresoftware.b4j.object.JavaObject _jo = null;
anywheresoftware.b4a.objects.LabelWrapper _l = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
 //BA.debugLineNum = 483;BA.debugLine="Public Sub SetTabTextIcon(TabID As Int,Text As Str";
 //BA.debugLineNum = 485;BA.debugLine="If TabID >= 1 And TabID <= TabCount Then";
if (_tabid>=1 && _tabid<=_tabcount) { 
 //BA.debugLineNum = 486;BA.debugLine="Dim i As IconType:i.Initialize";
_i = new com.bwsi.MeterReader.wobblemenu._icontype();
 //BA.debugLineNum = 486;BA.debugLine="Dim i As IconType:i.Initialize";
_i.Initialize();
 //BA.debugLineNum = 487;BA.debugLine="i.icon = Icon";
_i.Icon /*String*/  = _icon;
 //BA.debugLineNum = 488;BA.debugLine="i.Text = Text";
_i.Text /*String*/  = _text;
 //BA.debugLineNum = 489;BA.debugLine="i.ifont = IconFont";
_i.iFont /*anywheresoftware.b4a.keywords.constants.TypefaceWrapper*/  = _iconfont;
 //BA.debugLineNum = 490;BA.debugLine="i.tinted = False";
_i.tinted /*boolean*/  = __c.False;
 //BA.debugLineNum = 491;BA.debugLine="IconList.set(TabID-1,i)";
_iconlist.Set((int) (_tabid-1),(Object)(_i));
 //BA.debugLineNum = 493;BA.debugLine="Dim mTab As B4XView = Tabs.Get(TabID-1)";
_mtab = new anywheresoftware.b4a.objects.B4XViewWrapper();
_mtab = (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_tabs.Get((int) (_tabid-1))));
 //BA.debugLineNum = 494;BA.debugLine="Dim iv As ImageView = mTab.GetView(1)";
_iv = new anywheresoftware.b4a.objects.ImageViewWrapper();
_iv = (anywheresoftware.b4a.objects.ImageViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ImageViewWrapper(), (android.widget.ImageView)(_mtab.GetView((int) (1)).getObject()));
 //BA.debugLineNum = 497;BA.debugLine="Dim jo As JavaObject=iv";
_jo = new anywheresoftware.b4j.object.JavaObject();
_jo = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(_iv.getObject()));
 //BA.debugLineNum = 498;BA.debugLine="jo.RunMethod(\"setImageBitmap\",Array(Null))";
_jo.RunMethod("setImageBitmap",new Object[]{__c.Null});
 //BA.debugLineNum = 499;BA.debugLine="jo.RunMethod(\"setScaleType\",Array(\"CENTER_INSIDE";
_jo.RunMethod("setScaleType",new Object[]{(Object)("CENTER_INSIDE")});
 //BA.debugLineNum = 509;BA.debugLine="Dim l As Label = mTab.GetView(0)";
_l = new anywheresoftware.b4a.objects.LabelWrapper();
_l = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_mtab.GetView((int) (0)).getObject()));
 //BA.debugLineNum = 510;BA.debugLine="l.Text = Icon";
_l.setText(BA.ObjectToCharSequence(_icon));
 //BA.debugLineNum = 518;BA.debugLine="l.Typeface = IconFont";
_l.setTypeface((android.graphics.Typeface)(_iconfont.getObject()));
 //BA.debugLineNum = 521;BA.debugLine="If Text = \"\" Then";
if ((_text).equals("")) { 
 //BA.debugLineNum = 525;BA.debugLine="l.Height=mTab.Height";
_l.setHeight(_mtab.getHeight());
 }else {
 //BA.debugLineNum = 531;BA.debugLine="l.Height=(mTab.Height/3)*2";
_l.setHeight((int) ((_mtab.getHeight()/(double)3)*2));
 };
 //BA.debugLineNum = 535;BA.debugLine="Dim lbl As Label = mTab.GetView(2)";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_mtab.GetView((int) (2)).getObject()));
 //BA.debugLineNum = 536;BA.debugLine="lbl.Text = Text";
_lbl.setText(BA.ObjectToCharSequence(_text));
 //BA.debugLineNum = 538;BA.debugLine="SetCircleIcon";
_setcircleicon();
 }else {
 //BA.debugLineNum = 540;BA.debugLine="Log(\"Invalid Tab ID\")";
__c.LogImpl("983099705","Invalid Tab ID",0);
 };
 //BA.debugLineNum = 542;BA.debugLine="End Sub";
return "";
}
public String  _settabtexticon2(int _tabid,String _text,anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _icon,boolean _tinted) throws Exception{
com.bwsi.MeterReader.wobblemenu._icontype _i = null;
anywheresoftware.b4a.objects.B4XViewWrapper _mtab = null;
anywheresoftware.b4a.objects.LabelWrapper _l = null;
anywheresoftware.b4a.objects.ImageViewWrapper _iv = null;
anywheresoftware.b4j.object.JavaObject _jo = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl = null;
 //BA.debugLineNum = 548;BA.debugLine="Public Sub SetTabTextIcon2(TabID As Int,Text As St";
 //BA.debugLineNum = 550;BA.debugLine="If TabID >= 1 And TabID <= TabCount Then";
if (_tabid>=1 && _tabid<=_tabcount) { 
 //BA.debugLineNum = 551;BA.debugLine="Dim i As IconType:i.Initialize";
_i = new com.bwsi.MeterReader.wobblemenu._icontype();
 //BA.debugLineNum = 551;BA.debugLine="Dim i As IconType:i.Initialize";
_i.Initialize();
 //BA.debugLineNum = 552;BA.debugLine="i.iconImg = Icon";
_i.IconImg /*anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper*/  = _icon;
 //BA.debugLineNum = 553;BA.debugLine="i.Text = Text";
_i.Text /*String*/  = _text;
 //BA.debugLineNum = 554;BA.debugLine="i.tinted = tinted";
_i.tinted /*boolean*/  = _tinted;
 //BA.debugLineNum = 555;BA.debugLine="IconList.set(TabID-1,i)";
_iconlist.Set((int) (_tabid-1),(Object)(_i));
 //BA.debugLineNum = 557;BA.debugLine="Dim mTab As B4XView = Tabs.Get(TabID-1)";
_mtab = new anywheresoftware.b4a.objects.B4XViewWrapper();
_mtab = (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_tabs.Get((int) (_tabid-1))));
 //BA.debugLineNum = 558;BA.debugLine="Dim l As Label = mTab.GetView(0)";
_l = new anywheresoftware.b4a.objects.LabelWrapper();
_l = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_mtab.GetView((int) (0)).getObject()));
 //BA.debugLineNum = 559;BA.debugLine="l.Text = i.Icon";
_l.setText(BA.ObjectToCharSequence(_i.Icon /*String*/ ));
 //BA.debugLineNum = 560;BA.debugLine="Dim iv As ImageView = mTab.GetView(1)";
_iv = new anywheresoftware.b4a.objects.ImageViewWrapper();
_iv = (anywheresoftware.b4a.objects.ImageViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ImageViewWrapper(), (android.widget.ImageView)(_mtab.GetView((int) (1)).getObject()));
 //BA.debugLineNum = 562;BA.debugLine="If Text = \"\" Then";
if ((_text).equals("")) { 
 //BA.debugLineNum = 566;BA.debugLine="iv.Height=mTab.Height";
_iv.setHeight(_mtab.getHeight());
 }else {
 //BA.debugLineNum = 572;BA.debugLine="iv.Height=(mTab.Height/2)";
_iv.setHeight((int) ((_mtab.getHeight()/(double)2)));
 };
 //BA.debugLineNum = 577;BA.debugLine="Dim jo As JavaObject=iv";
_jo = new anywheresoftware.b4j.object.JavaObject();
_jo = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(_iv.getObject()));
 //BA.debugLineNum = 578;BA.debugLine="jo.RunMethod(\"setImageBitmap\",Array(i.IconImg))";
_jo.RunMethod("setImageBitmap",new Object[]{(Object)(_i.IconImg /*anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper*/ .getObject())});
 //BA.debugLineNum = 579;BA.debugLine="jo.RunMethod(\"setScaleType\",Array(\"CENTER_INSIDE";
_jo.RunMethod("setScaleType",new Object[]{(Object)("CENTER_INSIDE")});
 //BA.debugLineNum = 580;BA.debugLine="jo.RunMethod(\"setColorFilter\",Array(Colors.Trans";
_jo.RunMethod("setColorFilter",new Object[]{(Object)(__c.Colors.Transparent)});
 //BA.debugLineNum = 581;BA.debugLine="If i.tinted Then jo.RunMethod(\"setColorFilter\",A";
if (_i.tinted /*boolean*/ ) { 
_jo.RunMethod("setColorFilter",new Object[]{(Object)(__c.Colors.RGB(_getargb(_iconcolor)[(int) (1)],_getargb(_iconcolor)[(int) (2)],_getargb(_iconcolor)[(int) (3)]))});};
 //BA.debugLineNum = 597;BA.debugLine="Dim lbl As Label = mTab.GetView(2)";
_lbl = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl = (anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(_mtab.GetView((int) (2)).getObject()));
 //BA.debugLineNum = 598;BA.debugLine="lbl.Text = Text";
_lbl.setText(BA.ObjectToCharSequence(_text));
 //BA.debugLineNum = 600;BA.debugLine="SetCircleIcon";
_setcircleicon();
 }else {
 //BA.debugLineNum = 602;BA.debugLine="Log(\"Invalid Tab ID\")";
__c.LogImpl("983165238","Invalid Tab ID",0);
 };
 //BA.debugLineNum = 604;BA.debugLine="End Sub";
return "";
}
public String  _setvisible(boolean _show,boolean _animate) throws Exception{
 //BA.debugLineNum = 721;BA.debugLine="Public Sub SetVisible(show As Boolean,animate As B";
 //BA.debugLineNum = 722;BA.debugLine="If animate Then";
if (_animate) { 
 //BA.debugLineNum = 723;BA.debugLine="mBase.SetVisibleAnimated(300,show)";
_mbase.SetVisibleAnimated((int) (300),_show);
 }else {
 //BA.debugLineNum = 725;BA.debugLine="mBase.Visible = show";
_mbase.setVisible(_show);
 };
 //BA.debugLineNum = 727;BA.debugLine="End Sub";
return "";
}
public int  _timewiseposition(float _ctime,float _frompos,float _topos,int _duration) throws Exception{
float _ts = 0f;
float _tc = 0f;
 //BA.debugLineNum = 793;BA.debugLine="Private Sub TimeWisePosition(ctime As Float, fromP";
 //BA.debugLineNum = 794;BA.debugLine="Dim ts,tc As Float";
_ts = 0f;
_tc = 0f;
 //BA.debugLineNum = 795;BA.debugLine="ctime = ctime/duration";
_ctime = (float) (_ctime/(double)_duration);
 //BA.debugLineNum = 796;BA.debugLine="ts=ctime*ctime";
_ts = (float) (_ctime*_ctime);
 //BA.debugLineNum = 797;BA.debugLine="tc=ts*ctime";
_tc = (float) (_ts*_ctime);
 //BA.debugLineNum = 799;BA.debugLine="Select AnimationType";
switch (_animationtype) {
case 0: {
 //BA.debugLineNum = 801;BA.debugLine="Return fromPos + toPos * (23.645*tc*ts + -73.73";
if (true) return (int) (_frompos+_topos*(23.645*_tc*_ts+-73.7325*_ts*_ts+86.38*_tc+-46.79*_ts+11.4975*_ctime));
 break; }
case 1: {
 //BA.debugLineNum = 803;BA.debugLine="Return fromPos + toPos * (34.445*tc*ts + -69.39";
if (true) return (int) (_frompos+_topos*(34.445*_tc*_ts+-69.39*_ts*_ts+47.395*_tc+-12.4*_ts+0.95*_ctime));
 break; }
case 2: {
 //BA.debugLineNum = 805;BA.debugLine="Return fromPos + toPos * (tc*ts + -5*ts*ts + 10";
if (true) return (int) (_frompos+_topos*(_tc*_ts+-5*_ts*_ts+10*_tc+-10*_ts+5*_ctime));
 break; }
case 3: {
 //BA.debugLineNum = 807;BA.debugLine="Return fromPos + toPos * (tc*ts)";
if (true) return (int) (_frompos+_topos*(_tc*_ts));
 break; }
default: {
 //BA.debugLineNum = 809;BA.debugLine="Return fromPos + toPos*(ctime)";
if (true) return (int) (_frompos+_topos*(_ctime));
 break; }
}
;
 //BA.debugLineNum = 812;BA.debugLine="End Sub";
return 0;
}
public String  _triggertabclickevent(anywheresoftware.b4a.objects.B4XViewWrapper _t,boolean _trigger) throws Exception{
int _i = 0;
anywheresoftware.b4a.objects.B4XViewWrapper _tb = null;
 //BA.debugLineNum = 747;BA.debugLine="Private Sub TriggerTabClickEvent(t As B4XView, tri";
 //BA.debugLineNum = 748;BA.debugLine="If CurrentTab <> Tabs.IndexOf(t)+1 Then CurrentTa";
if (_currenttab!=_tabs.IndexOf((Object)(_t.getObject()))+1) { 
_currenttab = (int) (_tabs.IndexOf((Object)(_t.getObject()))+1);};
 //BA.debugLineNum = 749;BA.debugLine="AnimateTo(TabCurve,t.Left+(t.Width/2)-(TabCurve.W";
_animateto(_tabcurve,(int) (_t.getLeft()+(_t.getWidth()/(double)2)-(_tabcurve.getWidth()/(double)2)-1));
 //BA.debugLineNum = 750;BA.debugLine="AnimateTo(TabCircle,t.Left+(t.Width/2)-(TabCircle";
_animateto(_tabcircle,(int) (_t.getLeft()+(_t.getWidth()/(double)2)-(_tabcircle.getWidth()/(double)2)));
 //BA.debugLineNum = 752;BA.debugLine="For i=0 To TabCount-1";
{
final int step4 = 1;
final int limit4 = (int) (_tabcount-1);
_i = (int) (0) ;
for (;_i <= limit4 ;_i = _i + step4 ) {
 //BA.debugLineNum = 753;BA.debugLine="Dim tb As B4XView = Tabs.Get(i)";
_tb = new anywheresoftware.b4a.objects.B4XViewWrapper();
_tb = (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_tabs.Get(_i)));
 //BA.debugLineNum = 754;BA.debugLine="If tb<>t Then";
if ((_tb).equals(_t) == false) { 
 //BA.debugLineNum = 755;BA.debugLine="tb.GetView(0).Visible = True";
_tb.GetView((int) (0)).setVisible(__c.True);
 //BA.debugLineNum = 756;BA.debugLine="tb.GetView(1).Visible = True";
_tb.GetView((int) (1)).setVisible(__c.True);
 //BA.debugLineNum = 757;BA.debugLine="tb.GetView(2).Visible = True";
_tb.GetView((int) (2)).setVisible(__c.True);
 //BA.debugLineNum = 758;BA.debugLine="If tb.NumberOfViews = 4 Then tb.GetView(3).Visi";
if (_tb.getNumberOfViews()==4) { 
_tb.GetView((int) (3)).setVisible(__c.True);};
 }else {
 //BA.debugLineNum = 760;BA.debugLine="tb.GetView(0).Visible = False";
_tb.GetView((int) (0)).setVisible(__c.False);
 //BA.debugLineNum = 761;BA.debugLine="tb.GetView(1).Visible = False";
_tb.GetView((int) (1)).setVisible(__c.False);
 //BA.debugLineNum = 762;BA.debugLine="tb.GetView(2).Visible = False";
_tb.GetView((int) (2)).setVisible(__c.False);
 //BA.debugLineNum = 763;BA.debugLine="If tb.NumberOfViews = 4 Then tb.GetView(3).Visi";
if (_tb.getNumberOfViews()==4) { 
_tb.GetView((int) (3)).setVisible(__c.False);};
 };
 }
};
 //BA.debugLineNum = 766;BA.debugLine="SetCircleIcon";
_setcircleicon();
 //BA.debugLineNum = 768;BA.debugLine="If trigger And xui.SubExists(mCallBack, mEventNam";
if (_trigger && _xui.SubExists(ba,_mcallback,_meventname+"_Tab"+BA.NumberToString(_currenttab)+"Click",(int) (0))) { 
 //BA.debugLineNum = 769;BA.debugLine="CallSub(mCallBack, mEventName & \"_Tab\"&CurrentTa";
__c.CallSubNew(ba,_mcallback,_meventname+"_Tab"+BA.NumberToString(_currenttab)+"Click");
 };
 //BA.debugLineNum = 771;BA.debugLine="End Sub";
return "";
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
return BA.SubDelegator.SubNotFound;
}
}
