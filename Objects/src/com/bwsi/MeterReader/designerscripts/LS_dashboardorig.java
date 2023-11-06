package com.bwsi.MeterReader.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_dashboardorig{

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
views.get("pnlheader").vw.setTop((int)((views.get("toolbar").vw.getTop() + views.get("toolbar").vw.getHeight())+(3d * scale)));
views.get("pnlheader").vw.setHeight((int)((32d / 100 * height) - ((views.get("toolbar").vw.getTop() + views.get("toolbar").vw.getHeight())+(3d * scale))));
views.get("pnlheader").vw.setLeft((int)((1d / 100 * width)));
views.get("pnlheader").vw.setWidth((int)((99d / 100 * width) - ((1d / 100 * width))));
views.get("lblwelcome").vw.setLeft((int)((10d * scale)));
views.get("lblwelcome").vw.setWidth((int)((95d / 100 * width)));
views.get("lblwelcome").vw.setLeft((int)((50d / 100 * width) - (views.get("lblwelcome").vw.getWidth() / 2)));
views.get("lblwelcome").vw.setTop((int)((5d * scale)));
views.get("lblwelcome").vw.setLeft((int)((2d / 100 * width)));
views.get("lblwelcome").vw.setWidth((int)((views.get("pnlheader").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
views.get("usericon").vw.setTop((int)((views.get("lblwelcome").vw.getTop() + views.get("lblwelcome").vw.getHeight())+(2d * scale)));
views.get("usericon").vw.setLeft((int)((views.get("lblwelcome").vw.getLeft())+(3d * scale)));
views.get("lblempname").vw.setLeft((int)((views.get("usericon").vw.getLeft() + views.get("usericon").vw.getWidth())+(2d * scale)));
views.get("lblempname").vw.setWidth((int)((views.get("pnlheader").vw.getWidth())-(2d / 100 * width) - ((views.get("usericon").vw.getLeft() + views.get("usericon").vw.getWidth())+(2d * scale))));
views.get("lblempname").vw.setTop((int)((views.get("usericon").vw.getTop() + views.get("usericon").vw.getHeight()/2) - (views.get("lblempname").vw.getHeight() / 2)));
views.get("branchicon").vw.setTop((int)((views.get("usericon").vw.getTop() + views.get("usericon").vw.getHeight())+(2d * scale)));
views.get("branchicon").vw.setLeft((int)((views.get("usericon").vw.getLeft())));
views.get("lblbranchname").vw.setLeft((int)((views.get("branchicon").vw.getLeft() + views.get("branchicon").vw.getWidth())+(2d * scale)));
views.get("lblbranchname").vw.setWidth((int)((views.get("pnlheader").vw.getWidth())-(2d / 100 * width) - ((views.get("branchicon").vw.getLeft() + views.get("branchicon").vw.getWidth())+(2d * scale))));
views.get("lblbranchname").vw.setTop((int)((views.get("branchicon").vw.getTop() + views.get("branchicon").vw.getHeight()/2) - (views.get("lblbranchname").vw.getHeight() / 2)));
views.get("lblperiod").vw.setLeft((int)((views.get("lblwelcome").vw.getLeft())));
views.get("lblperiod").vw.setWidth((int)((views.get("lblwelcome").vw.getLeft() + views.get("lblwelcome").vw.getWidth()) - ((views.get("lblwelcome").vw.getLeft()))));
views.get("lblperiod").vw.setTop((int)((views.get("branchicon").vw.getTop() + views.get("branchicon").vw.getHeight())+(2d * scale)));
views.get("lblbillperiod").vw.setLeft((int)((views.get("lblempname").vw.getLeft())));
views.get("lblbillperiod").vw.setWidth((int)((views.get("lblwelcome").vw.getLeft() + views.get("lblwelcome").vw.getWidth()) - ((views.get("lblempname").vw.getLeft()))));
views.get("lblbillperiod").vw.setWidth((int)((100d / 100 * width)));
views.get("lblbillperiod").vw.setTop((int)((views.get("lblperiod").vw.getTop() + views.get("lblperiod").vw.getHeight())+(1d * scale)));
views.get("pnlassignment").vw.setLeft((int)((0d / 100 * width)));
views.get("pnlassignment").vw.setWidth((int)((100d / 100 * width) - ((0d / 100 * width))));
views.get("pnlassignment").vw.setTop((int)((views.get("pnlheader").vw.getTop() + views.get("pnlheader").vw.getHeight())+(15d * scale)));
views.get("pnlassignment").vw.setHeight((int)((100d / 100 * height) - ((views.get("pnlheader").vw.getTop() + views.get("pnlheader").vw.getHeight())+(15d * scale))));
views.get("clvbookassignment").vw.setLeft((int)((1d / 100 * width)));
views.get("clvbookassignment").vw.setWidth((int)((views.get("pnlassignment").vw.getWidth())-(1d / 100 * width) - ((1d / 100 * width))));
views.get("clvbookassignment").vw.setTop((int)((1d / 100 * height)));
views.get("clvbookassignment").vw.setHeight((int)((views.get("pnlassignment").vw.getHeight())-(1d / 100 * height) - ((1d / 100 * height))));
views.get("pnlmenu").vw.setTop((int)((views.get("pnlheader").vw.getTop() + views.get("pnlheader").vw.getHeight())+(15d * scale)));
views.get("pnlmenu").vw.setHeight((int)((93d / 100 * height) - ((views.get("pnlheader").vw.getTop() + views.get("pnlheader").vw.getHeight())+(15d * scale))));
views.get("pnlmenu").vw.setLeft((int)((1d / 100 * width)));
views.get("pnlmenu").vw.setWidth((int)((99d / 100 * width) - ((1d / 100 * width))));
views.get("btnbp").vw.setTop((int)((5d / 100 * height)));
views.get("btnbp").vw.setHeight((int)((views.get("pnlmenu").vw.getHeight())-(35d / 100 * height) - ((5d / 100 * height))));
views.get("btnbp").vw.setLeft((int)((3d / 100 * width)));
views.get("btnbp").vw.setWidth((int)((29d / 100 * width) - ((3d / 100 * width))));
views.get("label1").vw.setLeft((int)((0d / 100 * width)));
views.get("label1").vw.setWidth((int)((views.get("btnbp").vw.getWidth()) - ((0d / 100 * width))));
views.get("bpicon").vw.setLeft((int)((views.get("label1").vw.getLeft() + views.get("label1").vw.getWidth()/2) - (views.get("bpicon").vw.getWidth() / 2)));
views.get("btndl").vw.setTop((int)((5d / 100 * height)));
views.get("btndl").vw.setHeight((int)((views.get("pnlmenu").vw.getHeight())-(35d / 100 * height) - ((5d / 100 * height))));
views.get("btndl").vw.setLeft((int)((37d / 100 * width)));
views.get("btndl").vw.setWidth((int)((63d / 100 * width) - ((37d / 100 * width))));
views.get("label2").vw.setLeft((int)((0d / 100 * width)));
views.get("label2").vw.setWidth((int)((views.get("btndl").vw.getWidth()) - ((0d / 100 * width))));
views.get("dlicon").vw.setLeft((int)((views.get("label2").vw.getLeft() + views.get("label2").vw.getWidth()/2) - (views.get("dlicon").vw.getWidth() / 2)));
views.get("btnul").vw.setTop((int)((5d / 100 * height)));
views.get("btnul").vw.setHeight((int)((views.get("pnlmenu").vw.getHeight())-(35d / 100 * height) - ((5d / 100 * height))));
views.get("btnul").vw.setLeft((int)((71d / 100 * width)));
views.get("btnul").vw.setWidth((int)((views.get("pnlmenu").vw.getWidth())-(3d / 100 * width) - ((71d / 100 * width))));
views.get("label3").vw.setLeft((int)((0d / 100 * width)));
views.get("label3").vw.setWidth((int)((views.get("btnul").vw.getWidth()) - ((0d / 100 * width))));
views.get("ulicon").vw.setLeft((int)((views.get("label3").vw.getLeft() + views.get("label3").vw.getWidth()/2) - (views.get("ulicon").vw.getWidth() / 2)));
views.get("btnvalid").vw.setTop((int)((views.get("btndl").vw.getTop() + views.get("btndl").vw.getHeight())+(30d * scale)));
views.get("btnvalid").vw.setHeight((int)((views.get("pnlmenu").vw.getHeight())-(11d / 100 * height) - ((views.get("btndl").vw.getTop() + views.get("btndl").vw.getHeight())+(30d * scale))));
//BA.debugLineNum = 67;BA.debugLine="btnValid.SetLeftAndRight(20%x, 46%x)"[dashboardorig/General script]
views.get("btnvalid").vw.setLeft((int)((20d / 100 * width)));
views.get("btnvalid").vw.setWidth((int)((46d / 100 * width) - ((20d / 100 * width))));
//BA.debugLineNum = 68;BA.debugLine="Label4.SetLeftAndRight(0%x, btnValid.Width)"[dashboardorig/General script]
views.get("label4").vw.setLeft((int)((0d / 100 * width)));
views.get("label4").vw.setWidth((int)((views.get("btnvalid").vw.getWidth()) - ((0d / 100 * width))));
//BA.debugLineNum = 69;BA.debugLine="ValidIcon.HorizontalCenter = Label4.HorizontalCenter"[dashboardorig/General script]
views.get("validicon").vw.setLeft((int)((views.get("label4").vw.getLeft() + views.get("label4").vw.getWidth()/2) - (views.get("validicon").vw.getWidth() / 2)));
//BA.debugLineNum = 71;BA.debugLine="btnLogOut.SetTopAndBottom(btnDL.Bottom + 30dip, pnlMenu.Height - 11%y)"[dashboardorig/General script]
views.get("btnlogout").vw.setTop((int)((views.get("btndl").vw.getTop() + views.get("btndl").vw.getHeight())+(30d * scale)));
views.get("btnlogout").vw.setHeight((int)((views.get("pnlmenu").vw.getHeight())-(11d / 100 * height) - ((views.get("btndl").vw.getTop() + views.get("btndl").vw.getHeight())+(30d * scale))));
//BA.debugLineNum = 72;BA.debugLine="btnLogOut.SetLeftAndRight(54%x, 80%x)"[dashboardorig/General script]
views.get("btnlogout").vw.setLeft((int)((54d / 100 * width)));
views.get("btnlogout").vw.setWidth((int)((80d / 100 * width) - ((54d / 100 * width))));
//BA.debugLineNum = 73;BA.debugLine="Label5.SetLeftAndRight(0%x, btnLogOut.Width)"[dashboardorig/General script]
views.get("label5").vw.setLeft((int)((0d / 100 * width)));
views.get("label5").vw.setWidth((int)((views.get("btnlogout").vw.getWidth()) - ((0d / 100 * width))));
//BA.debugLineNum = 74;BA.debugLine="LogOutIcon.HorizontalCenter = Label5.HorizontalCenter"[dashboardorig/General script]
views.get("logouticon").vw.setLeft((int)((views.get("label5").vw.getLeft() + views.get("label5").vw.getWidth()/2) - (views.get("logouticon").vw.getWidth() / 2)));
//BA.debugLineNum = 76;BA.debugLine="pnlStatus.SetLeftAndRight(0%x, 100%x)"[dashboardorig/General script]
views.get("pnlstatus").vw.setLeft((int)((0d / 100 * width)));
views.get("pnlstatus").vw.setWidth((int)((100d / 100 * width) - ((0d / 100 * width))));
//BA.debugLineNum = 77;BA.debugLine="pnlStatus.SetTopAndBottom(pnlMenu.Bottom + 5dip, 100%y)"[dashboardorig/General script]
views.get("pnlstatus").vw.setTop((int)((views.get("pnlmenu").vw.getTop() + views.get("pnlmenu").vw.getHeight())+(5d * scale)));
views.get("pnlstatus").vw.setHeight((int)((100d / 100 * height) - ((views.get("pnlmenu").vw.getTop() + views.get("pnlmenu").vw.getHeight())+(5d * scale))));
//BA.debugLineNum = 79;BA.debugLine="lblMessage.SetTopAndBottom(0%y, pnlStatus.Height)"[dashboardorig/General script]
views.get("lblmessage").vw.setTop((int)((0d / 100 * height)));
views.get("lblmessage").vw.setHeight((int)((views.get("pnlstatus").vw.getHeight()) - ((0d / 100 * height))));
//BA.debugLineNum = 80;BA.debugLine="lblMessage.SetLeftAndRight(3%x, pnlStatus.Width - 3%x)"[dashboardorig/General script]
views.get("lblmessage").vw.setLeft((int)((3d / 100 * width)));
views.get("lblmessage").vw.setWidth((int)((views.get("pnlstatus").vw.getWidth())-(3d / 100 * width) - ((3d / 100 * width))));

}
}