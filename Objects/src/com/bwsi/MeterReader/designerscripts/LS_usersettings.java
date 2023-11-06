package com.bwsi.MeterReader.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_usersettings{

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
views.get("pnlcontent").vw.setWidth((int)((95d / 100 * width)));
views.get("pnlcontent").vw.setLeft((int)((50d / 100 * width) - (views.get("pnlcontent").vw.getWidth() / 2)));
views.get("pnlcontent").vw.setTop((int)((views.get("toolbar").vw.getTop() + views.get("toolbar").vw.getHeight())+(5d * scale)));
views.get("pnlcontent").vw.setHeight((int)((65d / 100 * height) - ((views.get("toolbar").vw.getTop() + views.get("toolbar").vw.getHeight())+(5d * scale))));
views.get("txtcurrentusername").vw.setTop((int)((5d * scale)));
views.get("txtcurrentusername").vw.setWidth((int)((90d / 100 * width)));
views.get("txtcurrentusername").vw.setLeft((int)((47d / 100 * width) - (views.get("txtcurrentusername").vw.getWidth() / 2)));
views.get("txtnewusername").vw.setTop((int)((views.get("txtcurrentusername").vw.getTop() + views.get("txtcurrentusername").vw.getHeight())+(5d * scale)));
views.get("txtnewusername").vw.setWidth((int)((90d / 100 * width)));
views.get("txtnewusername").vw.setLeft((int)((47d / 100 * width) - (views.get("txtnewusername").vw.getWidth() / 2)));
views.get("txtoldpassword").vw.setTop((int)((views.get("txtnewusername").vw.getTop() + views.get("txtnewusername").vw.getHeight())+(5d * scale)));
views.get("txtoldpassword").vw.setWidth((int)((90d / 100 * width)));
views.get("txtoldpassword").vw.setLeft((int)((47d / 100 * width) - (views.get("txtoldpassword").vw.getWidth() / 2)));
views.get("txtnewpassword").vw.setTop((int)((views.get("txtoldpassword").vw.getTop() + views.get("txtoldpassword").vw.getHeight())+(5d * scale)));
views.get("txtnewpassword").vw.setWidth((int)((90d / 100 * width)));
views.get("txtnewpassword").vw.setLeft((int)((47d / 100 * width) - (views.get("txtnewpassword").vw.getWidth() / 2)));
views.get("txtconfirmpassword").vw.setTop((int)((views.get("txtnewpassword").vw.getTop() + views.get("txtnewpassword").vw.getHeight())+(5d * scale)));
views.get("txtconfirmpassword").vw.setWidth((int)((90d / 100 * width)));
views.get("txtconfirmpassword").vw.setLeft((int)((47d / 100 * width) - (views.get("txtconfirmpassword").vw.getWidth() / 2)));
views.get("btnsave").vw.setWidth((int)((90d / 100 * width)));
views.get("btnsave").vw.setLeft((int)((47d / 100 * width) - (views.get("btnsave").vw.getWidth() / 2)));
views.get("btnsave").vw.setTop((int)((views.get("txtconfirmpassword").vw.getTop() + views.get("txtconfirmpassword").vw.getHeight())+(5d * scale)));
views.get("btnsave").vw.setHeight((int)((views.get("pnlcontent").vw.getHeight())-(5d * scale) - ((views.get("txtconfirmpassword").vw.getTop() + views.get("txtconfirmpassword").vw.getHeight())+(5d * scale))));

}
}