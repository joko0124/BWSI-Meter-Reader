package com.bwsi.MeterReader.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_mainmenu{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("pnlmenuheader").vw.setTop((int)((0d / 100 * height)));
views.get("pnlmenuheader").vw.setLeft((int)((0d / 100 * width)));
views.get("pnlmenuheader").vw.setWidth((int)((100d / 100 * width) - ((0d / 100 * width))));
if ((anywheresoftware.b4a.keywords.LayoutBuilder.getScreenSize()>6.5d)) { 
;
views.get("pnlmenuheader").vw.setHeight((int)((64d * scale)));
;}else{ 
;
if ((BA.ObjectToBoolean( String.valueOf(anywheresoftware.b4a.keywords.LayoutBuilder.isPortrait())))) { 
;
views.get("pnlmenuheader").vw.setHeight((int)((56d * scale)));
;}else{ 
;
views.get("pnlmenuheader").vw.setHeight((int)((48d * scale)));
;};
;};
views.get("lbluserfullname").vw.setLeft((int)((15d / 100 * width)));
views.get("lbluserfullname").vw.setWidth((int)((98d / 100 * width) - ((15d / 100 * width))));
views.get("lbluserbranch").vw.setLeft((int)((15d / 100 * width)));
views.get("lbluserbranch").vw.setWidth((int)((98d / 100 * width) - ((15d / 100 * width))));
views.get("lbluserfullname").vw.setTop((int)((1.75d / 100 * height)));
views.get("lbluserbranch").vw.setTop((int)((views.get("lbluserfullname").vw.getTop() + views.get("lbluserfullname").vw.getHeight())+(2d * scale)));
views.get("lvmenus").vw.setTop((int)((views.get("pnlmenuheader").vw.getTop() + views.get("pnlmenuheader").vw.getHeight())+(10d * scale)));
views.get("lvmenus").vw.setHeight((int)((96d / 100 * height) - ((views.get("pnlmenuheader").vw.getTop() + views.get("pnlmenuheader").vw.getHeight())+(10d * scale))));
views.get("pnlfooter").vw.setTop((int)((views.get("lvmenus").vw.getTop() + views.get("lvmenus").vw.getHeight())));
views.get("pnlfooter").vw.setLeft((int)((0d / 100 * width)));
views.get("pnlfooter").vw.setWidth((int)((100d / 100 * width) - ((0d / 100 * width))));
views.get("pnlfooter").vw.setTop((int)((views.get("lvmenus").vw.getTop() + views.get("lvmenus").vw.getHeight())));
views.get("pnlfooter").vw.setHeight((int)((100d / 100 * height) - ((views.get("lvmenus").vw.getTop() + views.get("lvmenus").vw.getHeight()))));
views.get("lblcredits").vw.setTop((int)((5d * scale)));
views.get("lblcredits").vw.setLeft((int)((0d / 100 * width)));
views.get("lblcredits").vw.setWidth((int)((95d / 100 * width) - ((0d / 100 * width))));

}
}