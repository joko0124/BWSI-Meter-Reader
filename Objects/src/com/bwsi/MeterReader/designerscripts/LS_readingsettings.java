package com.bwsi.MeterReader.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_readingsettings{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("toolbar").vw.setTop((int)((0d * scale)));
if ((anywheresoftware.b4a.keywords.LayoutBuilder.getScreenSize()>6.5d)) { 
;
views.get("toolbar").vw.setHeight((int)((64d * scale)));
;}else{ 
;
if ((BA.ObjectToBoolean( String.valueOf(anywheresoftware.b4a.keywords.LayoutBuilder.isPortrait())))) { 
;
views.get("toolbar").vw.setHeight((int)((56d * scale)));
;}else{ 
;
views.get("toolbar").vw.setHeight((int)((48d * scale)));
;};
;};
views.get("pnlmain").vw.setWidth((int)((100d / 100 * width)));
views.get("pnlmain").vw.setTop((int)((views.get("toolbar").vw.getTop() + views.get("toolbar").vw.getHeight())));
views.get("pnlmain").vw.setHeight((int)((100d / 100 * height) - ((views.get("toolbar").vw.getTop() + views.get("toolbar").vw.getHeight()))));
views.get("pnlmain").vw.setLeft((int)((0d / 100 * width)));
views.get("pnlmain").vw.setWidth((int)((100d / 100 * width) - ((0d / 100 * width))));
views.get("label1").vw.setLeft((int)((2d / 100 * width)));
views.get("label1").vw.setWidth((int)((views.get("pnlmain").vw.getWidth())-(62d / 100 * width) - ((2d / 100 * width))));
views.get("label1").vw.setTop((int)((2d / 100 * height)));
views.get("lblbillperiod").vw.setLeft((int)((views.get("label1").vw.getLeft() + views.get("label1").vw.getWidth())+(5d * scale)));
views.get("lblbillperiod").vw.setWidth((int)((views.get("pnlmain").vw.getWidth())-(1d / 100 * width) - ((views.get("label1").vw.getLeft() + views.get("label1").vw.getWidth())+(5d * scale))));
views.get("lblbillperiod").vw.setTop((int)((2d / 100 * height)));
views.get("pnlsettings").vw.setLeft((int)((4d / 100 * width)));
views.get("pnlsettings").vw.setWidth((int)((views.get("pnlmain").vw.getWidth())-(4d / 100 * width) - ((4d / 100 * width))));
views.get("pnlsettings").vw.setTop((int)((12d / 100 * height)));
views.get("pnlsettings").vw.setHeight((int)((views.get("pnlmain").vw.getHeight())-(47d / 100 * height) - ((12d / 100 * height))));
views.get("lblbillmonth").vw.setTop((int)((3d / 100 * height)));
views.get("lblbillmonth").vw.setLeft((int)((3d / 100 * width)));
views.get("lblbillyear").vw.setTop((int)((views.get("lblbillmonth").vw.getTop() + views.get("lblbillmonth").vw.getHeight())+(5d * scale)));
views.get("lblbillyear").vw.setLeft((int)((3d / 100 * width)));
views.get("pnlmonth").vw.setLeft((int)((views.get("lblbillmonth").vw.getLeft() + views.get("lblbillmonth").vw.getWidth())+(5d * scale)));
views.get("pnlmonth").vw.setWidth((int)((views.get("pnlsettings").vw.getWidth())-(3d / 100 * width) - ((views.get("lblbillmonth").vw.getLeft() + views.get("lblbillmonth").vw.getWidth())+(5d * scale))));
views.get("pnlmonth").vw.setTop((int)((3d / 100 * height)));
views.get("pnlyear").vw.setLeft((int)((views.get("lblbillyear").vw.getLeft() + views.get("lblbillyear").vw.getWidth())+(5d * scale)));
views.get("pnlyear").vw.setWidth((int)((views.get("pnlsettings").vw.getWidth())-(3d / 100 * width) - ((views.get("lblbillyear").vw.getLeft() + views.get("lblbillyear").vw.getWidth())+(5d * scale))));
views.get("pnlyear").vw.setTop((int)((views.get("pnlmonth").vw.getTop() + views.get("pnlmonth").vw.getHeight())+(5d * scale)));
views.get("cbobillmonth").vw.setLeft((int)((2d / 100 * width)));
views.get("cbobillmonth").vw.setWidth((int)((views.get("pnlmonth").vw.getWidth())-(1d / 100 * width) - ((2d / 100 * width))));
views.get("cbobillmonth").vw.setTop((int)((0.5d / 100 * height)));
views.get("cbobillmonth").vw.setHeight((int)((views.get("pnlmonth").vw.getHeight())-(0.5d / 100 * height) - ((0.5d / 100 * height))));
views.get("cbobillyear").vw.setLeft((int)((2d / 100 * width)));
views.get("cbobillyear").vw.setWidth((int)((views.get("pnlyear").vw.getWidth())-(1d / 100 * width) - ((2d / 100 * width))));
views.get("cbobillyear").vw.setTop((int)((0.5d / 100 * height)));
views.get("cbobillyear").vw.setHeight((int)((views.get("pnlyear").vw.getHeight())-(0.5d / 100 * height) - ((0.5d / 100 * height))));
views.get("btnok").vw.setTop((int)((views.get("pnlyear").vw.getTop() + views.get("pnlyear").vw.getHeight())+(20d * scale)));
views.get("btnok").vw.setHeight((int)((views.get("pnlsettings").vw.getHeight())-(2d / 100 * height) - ((views.get("pnlyear").vw.getTop() + views.get("pnlyear").vw.getHeight())+(20d * scale))));
views.get("btnok").vw.setLeft((int)((3d / 100 * width)));
views.get("btnok").vw.setWidth((int)((views.get("pnlsettings").vw.getWidth())-(3d / 100 * width) - ((3d / 100 * width))));

}
}