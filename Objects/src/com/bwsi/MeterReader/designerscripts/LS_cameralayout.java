package com.bwsi.MeterReader.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_cameralayout{

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
views.get("toolbar").vw.setHeight((int)((50d * scale)));
;}else{ 
;
views.get("toolbar").vw.setHeight((int)((42d * scale)));
;};
;};
views.get("pnlmain").vw.setLeft((int)(0d));
views.get("pnlmain").vw.setWidth((int)((100d / 100 * width) - (0d)));
views.get("pnlmain").vw.setTop((int)(0d));
views.get("pnlmain").vw.setHeight((int)((100d / 100 * height) - (0d)));
views.get("pnlbuttonanchor").vw.setLeft((int)((0d / 100 * width)));
views.get("pnlbuttonanchor").vw.setWidth((int)((100d / 100 * width) - ((0d / 100 * width))));
views.get("pnlbuttonanchor").vw.setTop((int)((94d / 100 * height)));
views.get("pnlbuttonanchor").vw.setHeight((int)((100d / 100 * height) - ((94d / 100 * height))));
views.get("pnlcapture").vw.setLeft((int)((50d / 100 * width) - (views.get("pnlcapture").vw.getWidth() / 2)));
views.get("pnlcapture").vw.setTop((int)((views.get("pnlbuttonanchor").vw.getTop()) - (views.get("pnlcapture").vw.getHeight() / 2)));
views.get("pnlflash").vw.setLeft((int)((5d / 100 * width)));
views.get("pnlflash").vw.setTop((int)((0.5d / 100 * height)));
views.get("pnlswitch").vw.setLeft((int)((views.get("pnlbuttonanchor").vw.getWidth())-(5d / 100 * width) - (views.get("pnlswitch").vw.getWidth())));
views.get("pnlswitch").vw.setTop((int)((0.5d / 100 * height)));

}
}