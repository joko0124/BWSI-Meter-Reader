package com.bwsi.MeterReader.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_booklisting{

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
views.get("pnlcontent").vw.setLeft((int)(0d));
views.get("pnlcontent").vw.setWidth((int)((100d / 100 * width)));
views.get("pnlcontent").vw.setTop((int)((views.get("toolbar").vw.getTop() + views.get("toolbar").vw.getHeight())));
views.get("pnlcontent").vw.setHeight((int)((100d / 100 * height) - ((views.get("toolbar").vw.getTop() + views.get("toolbar").vw.getHeight()))));
views.get("clv1").vw.setLeft((int)((0d * scale)));
views.get("clv1").vw.setWidth((int)((100d / 100 * width)));
views.get("clv1").vw.setTop((int)((0d / 100 * height)));
views.get("clv1").vw.setHeight((int)((95d / 100 * height) - ((0d / 100 * height))));
views.get("searchpanel").vw.setLeft((int)((0d * scale)));
views.get("searchpanel").vw.setWidth((int)((100d / 100 * width)));
views.get("searchpanel").vw.setTop((int)((0d / 100 * height)));
views.get("searchpanel").vw.setHeight((int)((95d / 100 * height) - ((0d / 100 * height))));
views.get("pnlsearchby").vw.setTop((int)((0d / 100 * width)));
views.get("pnlsearchby").vw.setWidth((int)((100d / 100 * width)));
views.get("pnlsearchby").vw.setLeft((int)((50d / 100 * width) - (views.get("pnlsearchby").vw.getWidth() / 2)));

}
}