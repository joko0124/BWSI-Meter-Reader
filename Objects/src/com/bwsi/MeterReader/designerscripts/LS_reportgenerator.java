package com.bwsi.MeterReader.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_reportgenerator{

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
views.get("clv1").vw.setHeight((int)((78.5d / 100 * height) - ((0d / 100 * height))));
views.get("pnlstatus").vw.setWidth((int)((100d / 100 * width)));
views.get("pnlstatus").vw.setLeft((int)((50d / 100 * width) - (views.get("pnlstatus").vw.getWidth() / 2)));
views.get("pnlstatus").vw.setTop((int)((95d / 100 * height)));
views.get("pnlstatus").vw.setHeight((int)((100d / 100 * height) - ((95d / 100 * height))));
views.get("lblreccount").vw.setLeft((int)((1d / 100 * width)));
views.get("lblreccount").vw.setWidth((int)((100d / 100 * width)));
views.get("lblreccount").vw.setHeight((int)((views.get("pnlstatus").vw.getHeight())));
views.get("btnexport").vw.setWidth((int)((70d / 100 * width)));
views.get("btnexport").vw.setLeft((int)((50d / 100 * width) - (views.get("btnexport").vw.getWidth() / 2)));
views.get("btnexport").vw.setTop((int)((79d / 100 * height)));
views.get("btnexport").vw.setHeight((int)((85.5d / 100 * height) - ((79d / 100 * height))));

}
}