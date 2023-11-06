package com.bwsi.MeterReader.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_loginnew{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("pnlmain").vw.setLeft((int)((0d / 100 * width)));
views.get("pnlmain").vw.setWidth((int)((100d / 100 * width) - ((0d / 100 * width))));
views.get("pnlmain").vw.setTop((int)((0d / 100 * height)));
views.get("pnlmain").vw.setHeight((int)((100d / 100 * height) - ((0d / 100 * height))));
views.get("pnlanchor").vw.setLeft((int)((3d / 100 * width)));
views.get("pnlanchor").vw.setWidth((int)((views.get("pnlmain").vw.getWidth())-(3d / 100 * width) - ((3d / 100 * width))));
views.get("pnlanchor").vw.setTop((int)((60d / 100 * height)));
views.get("pnlanchor").vw.setHeight((int)((views.get("pnlmain").vw.getHeight())-(7d / 100 * height) - ((60d / 100 * height))));
views.get("pnlusername").vw.setLeft((int)((2d / 100 * width)));
views.get("pnlusername").vw.setWidth((int)((views.get("pnlanchor").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
views.get("pnlusername").vw.setTop((int)((2d / 100 * height)));
views.get("pnlusername").vw.setHeight((int)((views.get("pnlanchor").vw.getHeight())-(22d / 100 * height) - ((2d / 100 * height))));
views.get("usericon").vw.setLeft((int)((2d / 100 * width)));
views.get("usericon").vw.setTop((int)((1d / 100 * height)));
views.get("usericon").vw.setHeight((int)((views.get("pnlusername").vw.getHeight())-(1d / 100 * height) - ((1d / 100 * height))));
views.get("txtusername").vw.setTop((int)((1d / 100 * height)));
views.get("txtusername").vw.setHeight((int)((views.get("pnlusername").vw.getHeight())-(1d / 100 * height) - ((1d / 100 * height))));
views.get("txtusername").vw.setLeft((int)((views.get("usericon").vw.getLeft() + views.get("usericon").vw.getWidth())+(5d * scale)));
views.get("txtusername").vw.setWidth((int)((views.get("pnlusername").vw.getWidth())-(5d / 100 * width) - ((views.get("usericon").vw.getLeft() + views.get("usericon").vw.getWidth())+(5d * scale))));
views.get("pnlpassword").vw.setLeft((int)((2d / 100 * width)));
views.get("pnlpassword").vw.setWidth((int)((views.get("pnlanchor").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
views.get("pnlpassword").vw.setTop((int)((views.get("pnlusername").vw.getTop() + views.get("pnlusername").vw.getHeight())+(10d * scale)));
views.get("pnlpassword").vw.setHeight((int)((views.get("pnlusername").vw.getHeight())));
views.get("passicon").vw.setLeft((int)((2d / 100 * width)));
views.get("passicon").vw.setTop((int)((1d / 100 * height)));
views.get("passicon").vw.setHeight((int)((views.get("pnlpassword").vw.getHeight())-(1d / 100 * height) - ((1d / 100 * height))));
views.get("txtpassword").vw.setTop((int)((1d / 100 * height)));
views.get("txtpassword").vw.setHeight((int)((views.get("pnlpassword").vw.getHeight())-(1d / 100 * height) - ((1d / 100 * height))));
views.get("txtpassword").vw.setLeft((int)((views.get("passicon").vw.getLeft() + views.get("passicon").vw.getWidth())+(5d * scale)));
views.get("txtpassword").vw.setWidth((int)((views.get("pnlpassword").vw.getWidth())-(5d / 100 * width) - ((views.get("passicon").vw.getLeft() + views.get("passicon").vw.getWidth())+(5d * scale))));
views.get("btnlogin").vw.setLeft((int)((2d / 100 * width)));
views.get("btnlogin").vw.setWidth((int)((views.get("pnlanchor").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
views.get("btnlogin").vw.setTop((int)((views.get("pnlpassword").vw.getTop() + views.get("pnlpassword").vw.getHeight())+(15d * scale)));
views.get("btnlogin").vw.setHeight((int)((views.get("pnlanchor").vw.getHeight())-(2d / 100 * height) - ((views.get("pnlpassword").vw.getTop() + views.get("pnlpassword").vw.getHeight())+(15d * scale))));
views.get("btnbranch").vw.setLeft((int)((views.get("pnlmain").vw.getWidth())-(27d / 100 * width)));
views.get("btnsettings").vw.setLeft((int)((views.get("btnbranch").vw.getLeft() + views.get("btnbranch").vw.getWidth())+(5d * scale)));
views.get("pnlsearchbranches").vw.setTop((int)((0d / 100 * height)));
views.get("pnlsearchbranches").vw.setHeight((int)((100d / 100 * height) - ((0d / 100 * height))));
views.get("pnlsearchbranches").vw.setLeft((int)((0d / 100 * width)));
views.get("pnlsearchbranches").vw.setWidth((int)((100d / 100 * width) - ((0d / 100 * width))));
views.get("pnlbranches").vw.setLeft((int)((1d / 100 * width)));
views.get("pnlbranches").vw.setWidth((int)((views.get("pnlsearchbranches").vw.getWidth())-(1d / 100 * width) - ((1d / 100 * width))));
views.get("pnlbranches").vw.setTop((int)((13d / 100 * height)));
views.get("pnlbranches").vw.setHeight((int)((views.get("pnlsearchbranches").vw.getHeight())-(12d / 100 * height) - ((13d / 100 * height))));
views.get("lblsearchtitle").vw.setLeft((int)((0d / 100 * width)));
views.get("lblsearchtitle").vw.setWidth((int)((views.get("pnlbranches").vw.getWidth()) - ((0d / 100 * width))));
views.get("lblsearchtitle").vw.setTop((int)((0d / 100 * height)));
views.get("lblsearchtitle").vw.setHeight((int)((5d / 100 * height) - ((0d / 100 * height))));
views.get("pnlsearchbranch").vw.setTop((int)((views.get("lblsearchtitle").vw.getTop() + views.get("lblsearchtitle").vw.getHeight())+(5d * scale)));
views.get("pnlsearchbranch").vw.setHeight((int)((views.get("pnlbranches").vw.getHeight())-(8d / 100 * height) - ((views.get("lblsearchtitle").vw.getTop() + views.get("lblsearchtitle").vw.getHeight())+(5d * scale))));
views.get("pnlsearchbranch").vw.setLeft((int)((2d / 100 * width)));
views.get("pnlsearchbranch").vw.setWidth((int)((views.get("pnlbranches").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
views.get("lblbranches").vw.setLeft((int)((1d / 100 * width)));
views.get("lblbranches").vw.setWidth((int)((views.get("pnlsearchbranch").vw.getWidth())-(1d / 100 * width) - ((1d / 100 * width))));
views.get("lblselectedbranch").vw.setTop((int)((views.get("pnlsearchbranch").vw.getTop() + views.get("pnlsearchbranch").vw.getHeight())+(10d * scale)));
views.get("lblselectedbranch").vw.setHeight((int)((views.get("pnlbranches").vw.getHeight())-(2d / 100 * height) - ((views.get("pnlsearchbranch").vw.getTop() + views.get("pnlsearchbranch").vw.getHeight())+(10d * scale))));
views.get("lblselectedbranch").vw.setLeft((int)((3d / 100 * width)));
views.get("lblselectedbranch").vw.setWidth((int)((views.get("pnlbranches").vw.getWidth())-(35d / 100 * width) - ((3d / 100 * width))));
views.get("btncancel").vw.setLeft((int)((63d / 100 * width)));
views.get("btncancel").vw.setWidth((int)((views.get("pnlbranches").vw.getWidth())-(2d / 100 * width) - ((63d / 100 * width))));
views.get("btncancel").vw.setTop((int)((views.get("pnlsearchbranch").vw.getTop() + views.get("pnlsearchbranch").vw.getHeight())+(5d * scale)));
views.get("btncancel").vw.setHeight((int)((views.get("pnlbranches").vw.getHeight())-(1d / 100 * height) - ((views.get("pnlsearchbranch").vw.getTop() + views.get("pnlsearchbranch").vw.getHeight())+(5d * scale))));

}
}