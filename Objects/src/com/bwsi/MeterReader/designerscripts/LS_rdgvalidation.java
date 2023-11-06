package com.bwsi.MeterReader.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_rdgvalidation{

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
views.get("pnlcontent").vw.setLeft((int)((0d / 100 * width)));
views.get("pnlcontent").vw.setWidth((int)((100d / 100 * width) - ((0d / 100 * width))));
views.get("pnlcontent").vw.setTop((int)((views.get("toolbar").vw.getTop() + views.get("toolbar").vw.getHeight())));
views.get("pnlcontent").vw.setHeight((int)((100d / 100 * height) - ((views.get("toolbar").vw.getTop() + views.get("toolbar").vw.getHeight()))));
views.get("pnlreaderbox").vw.setLeft((int)((2d / 100 * width)));
views.get("pnlreaderbox").vw.setWidth((int)((views.get("pnlcontent").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
views.get("pnlreaderbox").vw.setTop((int)((1d / 100 * height)));
views.get("pnlreaderbox").vw.setHeight((int)((views.get("pnlcontent").vw.getHeight())-(74d / 100 * height) - ((1d / 100 * height))));
views.get("pnltitle").vw.setTop((int)((0d / 100 * height)));
views.get("pnltitle").vw.setLeft((int)((0d / 100 * width)));
views.get("pnltitle").vw.setWidth((int)((views.get("pnlreaderbox").vw.getWidth()) - ((0d / 100 * width))));
views.get("lbltitleicon").vw.setLeft((int)((2d / 100 * width)));
views.get("lbltitleicon").vw.setTop((int)((1d / 100 * width)));
views.get("lbltitleicon").vw.setHeight((int)((views.get("pnltitle").vw.getHeight())-(1d / 100 * height) - ((1d / 100 * width))));
views.get("lblreadertitle").vw.setTop((int)((views.get("lbltitleicon").vw.getTop() + views.get("lbltitleicon").vw.getHeight()/2) - (views.get("lblreadertitle").vw.getHeight() / 2)));
views.get("lblreadertitle").vw.setLeft((int)((views.get("lbltitleicon").vw.getLeft() + views.get("lbltitleicon").vw.getWidth())+(1d * scale)));
views.get("lblreadertitle").vw.setWidth((int)((views.get("pnltitle").vw.getWidth()) - ((views.get("lbltitleicon").vw.getLeft() + views.get("lbltitleicon").vw.getWidth())+(1d * scale))));
views.get("lblmeterreader").vw.setTop((int)((views.get("pnltitle").vw.getTop() + views.get("pnltitle").vw.getHeight())+(10d * scale)));
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
views.get("pnlshortcuts").vw.setLeft((int)((2d / 100 * width)));
views.get("pnlshortcuts").vw.setWidth((int)((views.get("pnlcontent").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
views.get("pnlshortcuts").vw.setTop((int)((18d / 100 * height)));
views.get("pnlshortcuts").vw.setHeight((int)((views.get("pnlcontent").vw.getHeight())-(1d / 100 * height) - ((18d / 100 * height))));
views.get("pnlshortcutstitle").vw.setTop((int)((0d / 100 * height)));
views.get("pnlshortcutstitle").vw.setLeft((int)((0d / 100 * width)));
views.get("pnlshortcutstitle").vw.setWidth((int)((views.get("pnlshortcuts").vw.getWidth()) - ((0d / 100 * width))));
views.get("lblshicon").vw.setLeft((int)((2d / 100 * width)));
views.get("lblshicon").vw.setTop((int)((1d / 100 * width)));
views.get("lblshicon").vw.setHeight((int)((views.get("pnlshortcutstitle").vw.getHeight())-(1d / 100 * height) - ((1d / 100 * width))));
views.get("lblshtitle").vw.setTop((int)((views.get("lblshicon").vw.getTop() + views.get("lblshicon").vw.getHeight()/2) - (views.get("lblshtitle").vw.getHeight() / 2)));
views.get("lblshtitle").vw.setLeft((int)((views.get("lblshicon").vw.getLeft() + views.get("lblshicon").vw.getWidth())+(1d * scale)));
views.get("lblshtitle").vw.setWidth((int)((views.get("pnlshortcutstitle").vw.getWidth()) - ((views.get("lblshicon").vw.getLeft() + views.get("lblshicon").vw.getWidth())+(1d * scale))));
views.get("btnmiscoded").vw.setLeft((int)((3d / 100 * width)));
views.get("btnmiscoded").vw.setWidth((int)((46d / 100 * width) - ((3d / 100 * width))));
views.get("btnmiscoded").vw.setTop((int)((6d / 100 * height)));
views.get("btnmiscoded").vw.setHeight((int)((26d / 100 * height) - ((6d / 100 * height))));
views.get("miscodedicon").vw.setLeft((int)((views.get("btnmiscoded").vw.getWidth())/2d - (views.get("miscodedicon").vw.getWidth() / 2)));
views.get("miscodedicon").vw.setTop((int)((7d / 100 * height) - (views.get("miscodedicon").vw.getHeight() / 2)));
views.get("label1").vw.setLeft((int)((2d / 100 * width)));
views.get("label1").vw.setWidth((int)((views.get("btnmiscoded").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
views.get("label1").vw.setTop((int)((views.get("miscodedicon").vw.getTop() + views.get("miscodedicon").vw.getHeight())+(5d * scale)));
views.get("btnzerocons").vw.setLeft((int)((50d / 100 * width)));
views.get("btnzerocons").vw.setWidth((int)((views.get("pnlshortcuts").vw.getWidth())-(3d / 100 * width) - ((50d / 100 * width))));
views.get("btnzerocons").vw.setTop((int)((6d / 100 * height)));
views.get("btnzerocons").vw.setHeight((int)((26d / 100 * height) - ((6d / 100 * height))));
views.get("zeroicon").vw.setLeft((int)((views.get("btnzerocons").vw.getWidth())/2d - (views.get("zeroicon").vw.getWidth() / 2)));
views.get("zeroicon").vw.setTop((int)((7d / 100 * height) - (views.get("zeroicon").vw.getHeight() / 2)));
views.get("label2").vw.setLeft((int)((2d / 100 * width)));
views.get("label2").vw.setWidth((int)((views.get("btnzerocons").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
views.get("label2").vw.setTop((int)((views.get("zeroicon").vw.getTop() + views.get("zeroicon").vw.getHeight())+(5d * scale)));
views.get("btnlb").vw.setLeft((int)((3d / 100 * width)));
views.get("btnlb").vw.setWidth((int)((46d / 100 * width) - ((3d / 100 * width))));
views.get("btnlb").vw.setTop((int)((28d / 100 * height)));
views.get("btnlb").vw.setHeight((int)((48d / 100 * height) - ((28d / 100 * height))));
views.get("lbicon").vw.setLeft((int)((views.get("btnlb").vw.getWidth())/2d - (views.get("lbicon").vw.getWidth() / 2)));
views.get("lbicon").vw.setTop((int)((7d / 100 * height) - (views.get("lbicon").vw.getHeight() / 2)));
views.get("label3").vw.setLeft((int)((2d / 100 * width)));
views.get("label3").vw.setWidth((int)((views.get("btnlb").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
views.get("label3").vw.setTop((int)((views.get("zeroicon").vw.getTop() + views.get("zeroicon").vw.getHeight())+(5d * scale)));
views.get("btnhb").vw.setLeft((int)((50d / 100 * width)));
views.get("btnhb").vw.setWidth((int)((views.get("pnlshortcuts").vw.getWidth())-(3d / 100 * width) - ((50d / 100 * width))));
views.get("btnhb").vw.setTop((int)((28d / 100 * height)));
views.get("btnhb").vw.setHeight((int)((48d / 100 * height) - ((28d / 100 * height))));
views.get("hbicon").vw.setLeft((int)((views.get("btnhb").vw.getWidth())/2d - (views.get("hbicon").vw.getWidth() / 2)));
views.get("hbicon").vw.setTop((int)((7d / 100 * height) - (views.get("hbicon").vw.getHeight() / 2)));
views.get("label4").vw.setLeft((int)((2d / 100 * width)));
views.get("label4").vw.setWidth((int)((views.get("btnhb").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
views.get("label4").vw.setTop((int)((views.get("hbicon").vw.getTop() + views.get("hbicon").vw.getHeight())+(5d * scale)));
views.get("btnab").vw.setLeft((int)((3d / 100 * width)));
views.get("btnab").vw.setWidth((int)((46d / 100 * width) - ((3d / 100 * width))));
views.get("btnab").vw.setTop((int)((50d / 100 * height)));
views.get("btnab").vw.setHeight((int)((70.03d / 100 * height) - ((50d / 100 * height))));
views.get("abicon").vw.setLeft((int)((views.get("btnab").vw.getWidth())/2d - (views.get("abicon").vw.getWidth() / 2)));
views.get("abicon").vw.setTop((int)((7d / 100 * height) - (views.get("abicon").vw.getHeight() / 2)));
views.get("label5").vw.setLeft((int)((2d / 100 * width)));
views.get("label5").vw.setWidth((int)((views.get("btnab").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
views.get("label5").vw.setTop((int)((views.get("abicon").vw.getTop() + views.get("abicon").vw.getHeight())+(5d * scale)));
views.get("btnunprinted").vw.setLeft((int)((50d / 100 * width)));
views.get("btnunprinted").vw.setWidth((int)((views.get("pnlshortcuts").vw.getWidth())-(3d / 100 * width) - ((50d / 100 * width))));
views.get("btnunprinted").vw.setTop((int)((50d / 100 * height)));
views.get("btnunprinted").vw.setHeight((int)((70.03d / 100 * height) - ((50d / 100 * height))));
views.get("unprintedicon").vw.setLeft((int)((views.get("btnunprinted").vw.getWidth())/2d - (views.get("unprintedicon").vw.getWidth() / 2)));
views.get("unprintedicon").vw.setTop((int)((7d / 100 * height) - (views.get("unprintedicon").vw.getHeight() / 2)));
views.get("label6").vw.setLeft((int)((2d / 100 * width)));
views.get("label6").vw.setWidth((int)((views.get("btnunprinted").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
views.get("label6").vw.setTop((int)((views.get("unprintedicon").vw.getTop() + views.get("unprintedicon").vw.getHeight())+(5d * scale)));
((TextViewWrapper)views.get("label7").vw).setText((BA.NumberToString(views.get("btnmiscoded").vw.getHeight()))+" / "+(BA.NumberToString(views.get("btnlb").vw.getHeight()))+" / "+(BA.NumberToString(views.get("btnunprinted").vw.getHeight())));

}
}