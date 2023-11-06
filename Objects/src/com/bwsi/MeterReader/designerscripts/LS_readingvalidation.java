package com.bwsi.MeterReader.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_readingvalidation{

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
views.get("lbltitle").vw.setWidth((int)((95d / 100 * width)));
views.get("lbltitle").vw.setLeft((int)((50d / 100 * width) - (views.get("lbltitle").vw.getWidth() / 2)));
views.get("lbltitle").vw.setTop((int)((8d / 100 * height)));
views.get("btnmisscoded").vw.setWidth((int)((95d / 100 * width)));
views.get("btnmisscoded").vw.setLeft((int)((50d / 100 * width) - (views.get("btnmisscoded").vw.getWidth() / 2)));
views.get("btnmisscoded").vw.setTop((int)((15d / 100 * height)));
views.get("btnmisscoded").vw.setHeight((int)((25d / 100 * height) - ((15d / 100 * height))));
views.get("btnhighbill").vw.setWidth((int)((95d / 100 * width)));
views.get("btnhighbill").vw.setLeft((int)((50d / 100 * width) - (views.get("btnhighbill").vw.getWidth() / 2)));
views.get("btnhighbill").vw.setTop((int)((27d / 100 * height)));
views.get("btnhighbill").vw.setHeight((int)((37d / 100 * height) - ((27d / 100 * height))));
views.get("btnlowbill").vw.setWidth((int)((95d / 100 * width)));
views.get("btnlowbill").vw.setLeft((int)((50d / 100 * width) - (views.get("btnlowbill").vw.getWidth() / 2)));
views.get("btnlowbill").vw.setTop((int)((39d / 100 * height)));
views.get("btnlowbill").vw.setHeight((int)((49d / 100 * height) - ((39d / 100 * height))));
views.get("btnzerocons").vw.setWidth((int)((95d / 100 * width)));
views.get("btnzerocons").vw.setLeft((int)((50d / 100 * width) - (views.get("btnzerocons").vw.getWidth() / 2)));
views.get("btnzerocons").vw.setTop((int)((51d / 100 * height)));
views.get("btnzerocons").vw.setHeight((int)((61d / 100 * height) - ((51d / 100 * height))));
views.get("btnavebill").vw.setWidth((int)((95d / 100 * width)));
views.get("btnavebill").vw.setLeft((int)((50d / 100 * width) - (views.get("btnavebill").vw.getWidth() / 2)));
views.get("btnavebill").vw.setTop((int)((63d / 100 * height)));
views.get("btnavebill").vw.setHeight((int)((73d / 100 * height) - ((63d / 100 * height))));
views.get("pnlreader").vw.setLeft((int)(0d));
views.get("pnlreader").vw.setTop((int)(0d));
views.get("pnlreader").vw.setWidth((int)((100d / 100 * width)));
views.get("pnlreader").vw.setHeight((int)((100d / 100 * height)));
views.get("pnlreaderbox").vw.setLeft((int)((2d / 100 * width)));
views.get("pnlreaderbox").vw.setWidth((int)((views.get("pnlreader").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
views.get("pnlreaderbox").vw.setTop((int)((30d / 100 * height)));
views.get("pnlreaderbox").vw.setHeight((int)((views.get("pnlreader").vw.getHeight())-(45d / 100 * height) - ((30d / 100 * height))));
views.get("lbltitleicon").vw.setLeft((int)((2d / 100 * width)));
views.get("lblreadertitle").vw.setTop((int)((views.get("lbltitleicon").vw.getTop() + views.get("lbltitleicon").vw.getHeight()/2) - (views.get("lblreadertitle").vw.getHeight() / 2)));
views.get("lblreadertitle").vw.setLeft((int)((views.get("lbltitleicon").vw.getLeft() + views.get("lbltitleicon").vw.getWidth())+(1d * scale)));
views.get("lblreadertitle").vw.setWidth((int)((views.get("pnlreaderbox").vw.getWidth()) - ((views.get("lbltitleicon").vw.getLeft() + views.get("lbltitleicon").vw.getWidth())+(1d * scale))));
views.get("lblmeterreader").vw.setTop((int)((views.get("pnltitle").vw.getTop() + views.get("pnltitle").vw.getHeight())+(20d * scale)));
views.get("lblmeterreader").vw.setLeft((int)((1d / 100 * width)));
views.get("pnlreaderanchor").vw.setTop((int)((views.get("lblmeterreader").vw.getTop())));
views.get("pnlreaderanchor").vw.setLeft((int)((views.get("lblmeterreader").vw.getLeft() + views.get("lblmeterreader").vw.getWidth())+(5d * scale)));
views.get("pnlreaderanchor").vw.setWidth((int)((views.get("pnlreaderbox").vw.getWidth())-(2d / 100 * width) - ((views.get("lblmeterreader").vw.getLeft() + views.get("lblmeterreader").vw.getWidth())+(5d * scale))));
views.get("lblreadericon").vw.setLeft((int)((1d / 100 * width)));
views.get("cboreader").vw.setLeft((int)((views.get("lblreadericon").vw.getLeft() + views.get("lblreadericon").vw.getWidth())+(1d * scale)));
views.get("cboreader").vw.setWidth((int)((views.get("pnlreaderanchor").vw.getWidth())-(1d / 100 * width) - ((views.get("lblreadericon").vw.getLeft() + views.get("lblreadericon").vw.getWidth())+(1d * scale))));
views.get("cboreader").vw.setTop((int)((0.5d / 100 * height)));
views.get("cboreader").vw.setHeight((int)((views.get("pnlreaderanchor").vw.getHeight())-(0.5d / 100 * height) - ((0.5d / 100 * height))));
views.get("lblreadericon").vw.setTop((int)((views.get("cboreader").vw.getTop() + views.get("cboreader").vw.getHeight()/2) - (views.get("lblreadericon").vw.getHeight() / 2)));
views.get("btncancel").vw.setLeft((int)((3d / 100 * width)));
views.get("btnok").vw.setLeft((int)((views.get("pnlreaderbox").vw.getWidth())-(3d / 100 * width) - (views.get("btnok").vw.getWidth())));
views.get("btncancel").vw.setTop((int)((views.get("pnlreaderanchor").vw.getTop() + views.get("pnlreaderanchor").vw.getHeight())+(15d * scale)));
views.get("btncancel").vw.setHeight((int)((views.get("pnlreaderbox").vw.getHeight())-(10d * scale) - ((views.get("pnlreaderanchor").vw.getTop() + views.get("pnlreaderanchor").vw.getHeight())+(15d * scale))));
views.get("btnok").vw.setTop((int)((views.get("pnlreaderanchor").vw.getTop() + views.get("pnlreaderanchor").vw.getHeight())+(15d * scale)));
views.get("btnok").vw.setHeight((int)((views.get("pnlreaderbox").vw.getHeight())-(10d * scale) - ((views.get("pnlreaderanchor").vw.getTop() + views.get("pnlreaderanchor").vw.getHeight())+(15d * scale))));

}
}