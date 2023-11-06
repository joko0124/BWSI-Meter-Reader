package com.bwsi.MeterReader.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_unusualrdgreport{

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
views.get("pnlmain").vw.setTop((int)((views.get("toolbar").vw.getTop() + views.get("toolbar").vw.getHeight())));
views.get("pnlmain").vw.setHeight((int)((100d / 100 * height) - ((views.get("toolbar").vw.getTop() + views.get("toolbar").vw.getHeight()))));
views.get("pnlmain").vw.setLeft((int)((0d / 100 * width)));
views.get("pnlmain").vw.setWidth((int)((100d / 100 * width) - ((0d / 100 * width))));
views.get("pnlsearch").vw.setLeft((int)((2d / 100 * width)));
views.get("pnlsearch").vw.setWidth((int)((views.get("pnlmain").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
views.get("pnlsearch").vw.setTop((int)((1d / 100 * height)));
views.get("pnlsearch").vw.setHeight((int)((views.get("pnlmain").vw.getHeight())-(85d / 100 * height) - ((1d / 100 * height))));
views.get("lblsearchicon").vw.setLeft((int)((1d / 100 * width)));
views.get("lblsearchicon").vw.setTop((int)((0.5d / 100 * height)));
views.get("lblsearchicon").vw.setHeight((int)((views.get("pnlsearch").vw.getHeight())-(0.5d / 100 * height) - ((0.5d / 100 * height))));
views.get("txtsearch").vw.setLeft((int)((views.get("lblsearchicon").vw.getLeft() + views.get("lblsearchicon").vw.getWidth())));
views.get("txtsearch").vw.setWidth((int)((views.get("pnlsearch").vw.getWidth())-(1d / 100 * width) - ((views.get("lblsearchicon").vw.getLeft() + views.get("lblsearchicon").vw.getWidth()))));
views.get("txtsearch").vw.setTop((int)((0.5d / 100 * height)));
views.get("txtsearch").vw.setHeight((int)((views.get("pnlsearch").vw.getHeight())-(0.5d / 100 * height) - ((0.5d / 100 * height))));
views.get("pnllist").vw.setTop((int)((views.get("pnlsearch").vw.getTop() + views.get("pnlsearch").vw.getHeight())+(10d * scale)));
views.get("pnllist").vw.setHeight((int)((views.get("pnlmain").vw.getHeight())-(8d / 100 * height) - ((views.get("pnlsearch").vw.getTop() + views.get("pnlsearch").vw.getHeight())+(10d * scale))));
views.get("pnllist").vw.setLeft((int)((2d / 100 * width)));
views.get("pnllist").vw.setWidth((int)((views.get("pnlmain").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
views.get("clvvalidationlist").vw.setLeft((int)((1d / 100 * width)));
views.get("clvvalidationlist").vw.setWidth((int)((views.get("pnllist").vw.getWidth())-(1d / 100 * width) - ((1d / 100 * width))));
views.get("clvvalidationlist").vw.setTop((int)((5d * scale)));
views.get("clvvalidationlist").vw.setHeight((int)((views.get("pnllist").vw.getHeight())-(5d * scale) - ((5d * scale))));
views.get("pnlstatus").vw.setLeft((int)((0d / 100 * width)));
views.get("pnlstatus").vw.setWidth((int)((100d / 100 * width) - ((0d / 100 * width))));
views.get("pnlstatus").vw.setTop((int)((views.get("pnllist").vw.getTop() + views.get("pnllist").vw.getHeight())+(12d * scale)));
views.get("pnlstatus").vw.setHeight((int)((views.get("pnlmain").vw.getTop() + views.get("pnlmain").vw.getHeight())-(0.25d / 100 * height) - ((views.get("pnllist").vw.getTop() + views.get("pnllist").vw.getHeight())+(12d * scale))));
views.get("lblcount").vw.setLeft((int)((2d / 100 * width)));
views.get("lblcount").vw.setWidth((int)((98d / 100 * width) - ((2d / 100 * width))));
views.get("lblcount").vw.setLeft((int)((50d / 100 * width) - (views.get("lblcount").vw.getWidth() / 2)));
views.get("lblcount").vw.setTop((int)((0.5d / 100 * height)));
views.get("lblcount").vw.setHeight((int)((views.get("pnlstatus").vw.getHeight())-(8d / 100 * height) - ((0.5d / 100 * height))));

}
}