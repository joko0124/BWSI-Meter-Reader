package com.bwsi.MeterReader.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_login{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("txtusername").vw.setTop((int)((56d / 100 * height)));
views.get("txtusername").vw.setWidth((int)((95d / 100 * width)));
views.get("txtusername").vw.setLeft((int)((50d / 100 * width) - (views.get("txtusername").vw.getWidth() / 2)));
views.get("txtusername").vw.setHeight((int)((8d / 100 * height)));
views.get("txtpassword").vw.setTop((int)((views.get("txtusername").vw.getTop() + views.get("txtusername").vw.getHeight())+(3d * scale)));
views.get("txtpassword").vw.setWidth((int)((95d / 100 * width)));
views.get("txtpassword").vw.setLeft((int)((50d / 100 * width) - (views.get("txtpassword").vw.getWidth() / 2)));
views.get("txtpassword").vw.setHeight((int)((8d / 100 * height)));
views.get("chkshowpass").vw.setTop((int)((views.get("txtpassword").vw.getTop() + views.get("txtpassword").vw.getHeight())+(3d * scale)));
views.get("chkshowpass").vw.setWidth((int)((95d / 100 * width)));
views.get("chkshowpass").vw.setLeft((int)((50d / 100 * width) - (views.get("chkshowpass").vw.getWidth() / 2)));
views.get("chkshowpass").vw.setHeight((int)((8d / 100 * width)));
views.get("btnlogin").vw.setWidth((int)((95d / 100 * width)));
views.get("btnlogin").vw.setLeft((int)((50d / 100 * width) - (views.get("btnlogin").vw.getWidth() / 2)));
views.get("btnlogin").vw.setTop((int)((80d / 100 * height)));
views.get("btnlogin").vw.setHeight((int)((88d / 100 * height) - ((80d / 100 * height))));
views.get("pnlbranches").vw.setWidth((int)((95d / 100 * width)));
views.get("pnlbranches").vw.setLeft((int)((50d / 100 * width) - (views.get("pnlbranches").vw.getWidth() / 2)));
views.get("pnlbranches").vw.setHeight((int)((75d / 100 * height)));
views.get("pnlbranches").vw.setTop((int)((48d / 100 * height) - (views.get("pnlbranches").vw.getHeight() / 2)));
views.get("lblsearchtitle").vw.setWidth((int)((views.get("pnlbranches").vw.getWidth())));
views.get("lblsearchtitle").vw.setHeight((int)((4d / 100 * height)));
views.get("lblsearchtitle").vw.setTop((int)((0d * scale)));
views.get("lblbranches").vw.setLeft((int)((5d / 100 * width)));
views.get("lblbranches").vw.setWidth((int)((93d / 100 * width) - ((5d / 100 * width))));
views.get("lblbranches").vw.setLeft((int)((46.55d / 100 * width) - (views.get("lblbranches").vw.getWidth() / 2)));
views.get("btncancel").vw.setTop((int)((68d / 100 * height)));
views.get("btncancel").vw.setHeight((int)((74d / 100 * height) - ((68d / 100 * height))));
views.get("btncancel").vw.setLeft((int)((63d / 100 * width)));
views.get("btncancel").vw.setWidth((int)((views.get("pnlbranches").vw.getLeft() + views.get("pnlbranches").vw.getWidth())-(25d * scale) - ((63d / 100 * width))));
views.get("pnlsearchbranch").vw.setTop((int)((views.get("lblsearchtitle").vw.getTop() + views.get("lblsearchtitle").vw.getHeight())+(5d * scale)));
views.get("pnlsearchbranch").vw.setHeight((int)((views.get("btncancel").vw.getTop())-(5d * scale) - ((views.get("lblsearchtitle").vw.getTop() + views.get("lblsearchtitle").vw.getHeight())+(5d * scale))));
views.get("pnlsearchbranch").vw.setWidth((int)((92d / 100 * width)));
views.get("pnlsearchbranch").vw.setLeft((int)((47.5d / 100 * width) - (views.get("pnlsearchbranch").vw.getWidth() / 2)));
views.get("lblselectedbranch").vw.setTop((int)((views.get("btncancel").vw.getTop())));
views.get("lblselectedbranch").vw.setHeight((int)((views.get("btncancel").vw.getTop())+(views.get("btncancel").vw.getHeight()) - ((views.get("btncancel").vw.getTop()))));
views.get("lblselectedbranch").vw.setLeft((int)((15d * scale)));
views.get("lblselectedbranch").vw.setWidth((int)((views.get("btncancel").vw.getLeft())-(5d * scale) - ((15d * scale))));

}
}