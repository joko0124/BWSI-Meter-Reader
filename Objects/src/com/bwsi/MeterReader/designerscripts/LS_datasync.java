package com.bwsi.MeterReader.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_datasync{

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
views.get("pnlmain").vw.setWidth((int)((100d / 100 * width)));
views.get("pnlmain").vw.setTop((int)((views.get("toolbar").vw.getTop() + views.get("toolbar").vw.getHeight())+(5d * scale)));
views.get("pnlmain").vw.setHeight((int)((100d / 100 * height) - ((views.get("toolbar").vw.getTop() + views.get("toolbar").vw.getHeight())+(5d * scale))));
views.get("pnlmain").vw.setLeft((int)((50d / 100 * width) - (views.get("pnlmain").vw.getWidth() / 2)));
views.get("pnlbuttons").vw.setWidth((int)((100d / 100 * width)));
views.get("pnlbuttons").vw.setLeft((int)((50d / 100 * width) - (views.get("pnlbuttons").vw.getWidth() / 2)));
views.get("pnlbuttons").vw.setHeight((int)((23d / 100 * height)));
views.get("pnlbuttons").vw.setTop((int)((30d / 100 * height) - (views.get("pnlbuttons").vw.getHeight() / 2)));
views.get("btndownload").vw.setHeight((int)((10d / 100 * height)));
views.get("btndownload").vw.setTop((int)((1d / 100 * height)));
views.get("btndownload").vw.setWidth((int)((90d / 100 * width)));
views.get("btndownload").vw.setLeft((int)((50d / 100 * width) - (views.get("btndownload").vw.getWidth() / 2)));
views.get("btnupload").vw.setHeight((int)((10d / 100 * height)));
views.get("btnupload").vw.setWidth((int)((90d / 100 * width)));
views.get("btnupload").vw.setLeft((int)((50d / 100 * width) - (views.get("btnupload").vw.getWidth() / 2)));
views.get("btnupload").vw.setTop((int)((views.get("btndownload").vw.getTop() + views.get("btndownload").vw.getHeight())+(1d / 100 * height)));
views.get("pnldownload").vw.setWidth((int)((90d / 100 * width)));
views.get("pnldownload").vw.setLeft((int)((50d / 100 * width) - (views.get("pnldownload").vw.getWidth() / 2)));
views.get("pnldownload").vw.setHeight((int)((35d / 100 * height)));
views.get("pnldownload").vw.setTop((int)((30d / 100 * height) - (views.get("pnldownload").vw.getHeight() / 2)));
views.get("lblreader").vw.setTop((int)((3d / 100 * height)));
views.get("lblreader").vw.setHeight((int)((4d / 100 * height)));
views.get("lblreader").vw.setWidth((int)((25d / 100 * width)));
views.get("lblreader").vw.setLeft((int)((15d / 100 * width) - (views.get("lblreader").vw.getWidth() / 2)));
views.get("cboreader").vw.setTop((int)((views.get("lbldatetime").vw.getTop())));
views.get("cboreader").vw.setHeight((int)((7d / 100 * height)));
views.get("cboreader").vw.setWidth((int)((60d / 100 * width)));
views.get("cboreader").vw.setLeft((int)((views.get("lblreader").vw.getLeft() + views.get("lblreader").vw.getWidth())+(5d * scale)));
views.get("lbldatetime").vw.setTop((int)((views.get("cboreader").vw.getTop() + views.get("cboreader").vw.getHeight())+(13d * scale)));
views.get("lbldatetime").vw.setHeight((int)((4d / 100 * height)));
views.get("lbldatetime").vw.setWidth((int)((25d / 100 * width)));
views.get("lbldatetime").vw.setLeft((int)((views.get("lblreader").vw.getLeft() + views.get("lblreader").vw.getWidth()) - (views.get("lbldatetime").vw.getWidth())));
views.get("dtrdgdate").vw.setTop((int)((views.get("cboreader").vw.getTop() + views.get("cboreader").vw.getHeight())+(8d * scale)));
views.get("dtrdgdate").vw.setHeight((int)((6d / 100 * height)));
views.get("dtrdgdate").vw.setWidth((int)((48d / 100 * width)));
views.get("dtrdgdate").vw.setLeft((int)((views.get("cboreader").vw.getLeft())+(5d * scale)));
views.get("btnok").vw.setHeight((int)((8d / 100 * height)));
views.get("btnok").vw.setTop((int)((views.get("dtrdgdate").vw.getTop() + views.get("dtrdgdate").vw.getHeight())+(5d * scale)));
views.get("btnok").vw.setWidth((int)((70d / 100 * width)));
views.get("btnok").vw.setLeft((int)((45d / 100 * width) - (views.get("btnok").vw.getWidth() / 2)));
views.get("btncancel").vw.setHeight((int)((8d / 100 * height)));
views.get("btncancel").vw.setWidth((int)((70d / 100 * width)));
views.get("btncancel").vw.setTop((int)((views.get("btnok").vw.getTop() + views.get("btnok").vw.getHeight())+(5d * scale)));
views.get("btncancel").vw.setLeft((int)((45d / 100 * width) - (views.get("btncancel").vw.getWidth() / 2)));
views.get("pnlupload").vw.setWidth((int)((90d / 100 * width)));
views.get("pnlupload").vw.setLeft((int)((50d / 100 * width) - (views.get("pnlupload").vw.getWidth() / 2)));
views.get("pnlupload").vw.setHeight((int)((28d / 100 * height)));
views.get("pnlupload").vw.setTop((int)((30d / 100 * height) - (views.get("pnlupload").vw.getHeight() / 2)));
views.get("lblmeterreader").vw.setTop((int)((3d / 100 * height)));
views.get("lblmeterreader").vw.setHeight((int)((4d / 100 * height)));
//BA.debugLineNum = 78;BA.debugLine="lblMeterReader.Width = 25%x"[datasync/General script]
views.get("lblmeterreader").vw.setWidth((int)((25d / 100 * width)));
//BA.debugLineNum = 79;BA.debugLine="lblMeterReader.HorizontalCenter = 15%x"[datasync/General script]
views.get("lblmeterreader").vw.setLeft((int)((15d / 100 * width) - (views.get("lblmeterreader").vw.getWidth() / 2)));
//BA.debugLineNum = 81;BA.debugLine="cboReaderUpload.Top =1.5%y"[datasync/General script]
views.get("cboreaderupload").vw.setTop((int)((1.5d / 100 * height)));
//BA.debugLineNum = 82;BA.debugLine="cboReaderUpload.Height = 7%y"[datasync/General script]
views.get("cboreaderupload").vw.setHeight((int)((7d / 100 * height)));
//BA.debugLineNum = 83;BA.debugLine="cboReaderUpload.Width = 60%x"[datasync/General script]
views.get("cboreaderupload").vw.setWidth((int)((60d / 100 * width)));
//BA.debugLineNum = 84;BA.debugLine="cboReaderUpload.Left = lblMeterReader.Right + 5dip"[datasync/General script]
views.get("cboreaderupload").vw.setLeft((int)((views.get("lblmeterreader").vw.getLeft() + views.get("lblmeterreader").vw.getWidth())+(5d * scale)));
//BA.debugLineNum = 86;BA.debugLine="btnOkUpload.Height = 8%y"[datasync/General script]
views.get("btnokupload").vw.setHeight((int)((8d / 100 * height)));
//BA.debugLineNum = 87;BA.debugLine="btnOkUpload.Top = cboReaderUpload.Bottom + 5dip"[datasync/General script]
views.get("btnokupload").vw.setTop((int)((views.get("cboreaderupload").vw.getTop() + views.get("cboreaderupload").vw.getHeight())+(5d * scale)));
//BA.debugLineNum = 88;BA.debugLine="btnOkUpload.Width = 70%x"[datasync/General script]
views.get("btnokupload").vw.setWidth((int)((70d / 100 * width)));
//BA.debugLineNum = 89;BA.debugLine="btnOkUpload.HorizontalCenter = 45%x"[datasync/General script]
views.get("btnokupload").vw.setLeft((int)((45d / 100 * width) - (views.get("btnokupload").vw.getWidth() / 2)));
//BA.debugLineNum = 91;BA.debugLine="btnCancelUpload.Height = 8%y"[datasync/General script]
views.get("btncancelupload").vw.setHeight((int)((8d / 100 * height)));
//BA.debugLineNum = 92;BA.debugLine="btnCancelUpload.Width = 70%x"[datasync/General script]
views.get("btncancelupload").vw.setWidth((int)((70d / 100 * width)));
//BA.debugLineNum = 93;BA.debugLine="btnCancelUpload.Top = btnOkUpload.Bottom + 5dip"[datasync/General script]
views.get("btncancelupload").vw.setTop((int)((views.get("btnokupload").vw.getTop() + views.get("btnokupload").vw.getHeight())+(5d * scale)));
//BA.debugLineNum = 94;BA.debugLine="btnCancelUpload.HorizontalCenter = 45%x"[datasync/General script]
views.get("btncancelupload").vw.setLeft((int)((45d / 100 * width) - (views.get("btncancelupload").vw.getWidth() / 2)));

}
}