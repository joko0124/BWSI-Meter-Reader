package com.bwsi.MeterReader.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_datasyncingnew{

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
views.get("pnlmain").vw.setTop((int)((views.get("toolbar").vw.getTop() + views.get("toolbar").vw.getHeight())));
views.get("pnlmain").vw.setHeight((int)((100d / 100 * height) - ((views.get("toolbar").vw.getTop() + views.get("toolbar").vw.getHeight()))));
views.get("pnlmain").vw.setLeft((int)((50d / 100 * width) - (views.get("pnlmain").vw.getWidth() / 2)));
views.get("pnlbuttons").vw.setWidth((int)((views.get("pnlmain").vw.getWidth())));
views.get("pnlbuttons").vw.setLeft((int)((0d / 100 * width)));
views.get("pnlbuttons").vw.setWidth((int)((views.get("pnlmain").vw.getWidth()) - ((0d / 100 * width))));
views.get("pnlbuttons").vw.setHeight((int)((32d / 100 * height)));
views.get("pnlbuttons").vw.setTop((int)((30d / 100 * height) - (views.get("pnlbuttons").vw.getHeight() / 2)));
views.get("btndownload").vw.setTop((int)((2d / 100 * height)));
views.get("btndownload").vw.setHeight((int)((views.get("pnlbuttons").vw.getHeight())-(17d / 100 * height) - ((2d / 100 * height))));
views.get("btndownload").vw.setLeft((int)((5d / 100 * width)));
views.get("btndownload").vw.setWidth((int)((views.get("pnlbuttons").vw.getWidth())-(5d / 100 * width) - ((5d / 100 * width))));
views.get("btnupload").vw.setTop((int)((17d / 100 * height)));
views.get("btnupload").vw.setHeight((int)((views.get("pnlbuttons").vw.getHeight())-(2d / 100 * height) - ((17d / 100 * height))));
views.get("btnupload").vw.setLeft((int)((5d / 100 * width)));
views.get("btnupload").vw.setWidth((int)((views.get("pnlbuttons").vw.getWidth())-(5d / 100 * width) - ((5d / 100 * width))));
views.get("pnldownloadbox").vw.setLeft((int)(0d));
views.get("pnldownloadbox").vw.setTop((int)(0d));
views.get("pnldownloadbox").vw.setWidth((int)((100d / 100 * width)));
views.get("pnldownloadbox").vw.setHeight((int)((100d / 100 * height)));
views.get("pnldownload").vw.setWidth((int)((100d / 100 * width)));
views.get("pnldownload").vw.setLeft((int)((2d / 100 * width)));
views.get("pnldownload").vw.setWidth((int)((views.get("pnldownloadbox").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
views.get("pnldownload").vw.setHeight((int)((33d / 100 * height)));
views.get("pnldownload").vw.setTop((int)((views.get("pnldownloadbox").vw.getHeight())/2d-(15d / 100 * height) - (views.get("pnldownload").vw.getHeight() / 2)));
views.get("lbldltitle").vw.setWidth((int)((100d / 100 * width)));
views.get("lbldltitle").vw.setTop((int)((0d / 100 * height)));
views.get("lbldltitle").vw.setHeight((int)((views.get("lbldltitle").vw.getHeight()) - ((0d / 100 * height))));
views.get("lbldltitle").vw.setLeft((int)((0d / 100 * width)));
views.get("lbldltitle").vw.setWidth((int)((views.get("pnldownload").vw.getWidth()) - ((0d / 100 * width))));
views.get("lbldlicon").vw.setTop((int)((0d / 100 * height)));
views.get("lbldlicon").vw.setHeight((int)((views.get("lbldltitle").vw.getHeight()) - ((0d / 100 * height))));
views.get("lbldlicon").vw.setLeft((int)((10d * scale)));
views.get("lblreader").vw.setTop((int)((views.get("lbldltitle").vw.getTop() + views.get("lbldltitle").vw.getHeight())+(10d * scale)));
views.get("lblreader").vw.setLeft((int)((3d / 100 * width)));
views.get("pnlreaderdl").vw.setTop((int)((views.get("lbldltitle").vw.getTop() + views.get("lbldltitle").vw.getHeight())+(10d * scale)));
views.get("pnlreaderdl").vw.setLeft((int)((views.get("lblreader").vw.getLeft() + views.get("lblreader").vw.getWidth())));
views.get("pnlreaderdl").vw.setWidth((int)((views.get("pnldownload").vw.getWidth())-(3d / 100 * width) - ((views.get("lblreader").vw.getLeft() + views.get("lblreader").vw.getWidth()))));
views.get("lblreadericon").vw.setLeft((int)((1d / 100 * width)));
views.get("lblreadericon").vw.setTop((int)((0.5d / 100 * height)));
views.get("lblreadericon").vw.setHeight((int)((views.get("pnlreaderdl").vw.getHeight())-(0.5d / 100 * height) - ((0.5d / 100 * height))));
views.get("cboreader").vw.setLeft((int)((views.get("lblreadericon").vw.getLeft() + views.get("lblreadericon").vw.getWidth())+(3d * scale)));
views.get("cboreader").vw.setWidth((int)((views.get("pnlreaderdl").vw.getWidth())-(1d / 100 * width) - ((views.get("lblreadericon").vw.getLeft() + views.get("lblreadericon").vw.getWidth())+(3d * scale))));
views.get("cboreader").vw.setTop((int)((0.5d / 100 * height)));
views.get("cboreader").vw.setHeight((int)((views.get("pnlreaderdl").vw.getHeight())-(0.5d / 100 * height) - ((0.5d / 100 * height))));
views.get("lbldatetime").vw.setTop((int)((views.get("lblreader").vw.getTop() + views.get("lblreader").vw.getHeight())+(10d * scale)));
views.get("lbldatetime").vw.setLeft((int)((3d / 100 * width)));
views.get("pnldateanchor").vw.setTop((int)((views.get("pnlreaderdl").vw.getTop() + views.get("pnlreaderdl").vw.getHeight())+(10d * scale)));
views.get("pnldateanchor").vw.setLeft((int)((views.get("lbldatetime").vw.getLeft() + views.get("lbldatetime").vw.getWidth())));
views.get("pnldateanchor").vw.setWidth((int)((views.get("pnldownload").vw.getWidth())-(3d / 100 * width) - ((views.get("lbldatetime").vw.getLeft() + views.get("lbldatetime").vw.getWidth()))));
views.get("lblrdgdateicon").vw.setLeft((int)((1d / 100 * width)));
views.get("lblrdgdateicon").vw.setTop((int)((0.5d / 100 * height)));
views.get("lblrdgdateicon").vw.setHeight((int)((views.get("pnldateanchor").vw.getHeight())-(0.5d / 100 * height) - ((0.5d / 100 * height))));
views.get("lblreadingdate").vw.setLeft((int)((views.get("lblrdgdateicon").vw.getLeft() + views.get("lblrdgdateicon").vw.getWidth())+(3d * scale)));
views.get("lblreadingdate").vw.setWidth((int)((views.get("pnldateanchor").vw.getWidth())-(1d / 100 * width) - ((views.get("lblrdgdateicon").vw.getLeft() + views.get("lblrdgdateicon").vw.getWidth())+(3d * scale))));
views.get("lblreadingdate").vw.setTop((int)((0.5d / 100 * height)));
views.get("lblreadingdate").vw.setHeight((int)((views.get("pnldateanchor").vw.getHeight())-(0.5d / 100 * height) - ((0.5d / 100 * height))));
views.get("btncancel").vw.setTop((int)((25d / 100 * height)));
views.get("btncancel").vw.setHeight((int)((views.get("pnldownload").vw.getHeight())-(10d * scale) - ((25d / 100 * height))));
views.get("btncancel").vw.setLeft((int)((5d / 100 * width)));
views.get("btncancel").vw.setWidth((int)((views.get("pnldownload").vw.getWidth())-(63d / 100 * width) - ((5d / 100 * width))));
views.get("btnok").vw.setTop((int)((25d / 100 * height)));
views.get("btnok").vw.setHeight((int)((views.get("pnldownload").vw.getHeight())-(10d * scale) - ((25d / 100 * height))));
views.get("btnok").vw.setLeft((int)((views.get("pnldownload").vw.getWidth())-(35d / 100 * width)));
views.get("btnok").vw.setWidth((int)((views.get("pnldownload").vw.getWidth())-(3d / 100 * width) - ((views.get("pnldownload").vw.getWidth())-(35d / 100 * width))));
views.get("pnluploadbox").vw.setLeft((int)(0d));
views.get("pnluploadbox").vw.setTop((int)(0d));
views.get("pnluploadbox").vw.setWidth((int)((100d / 100 * width)));
views.get("pnluploadbox").vw.setHeight((int)((100d / 100 * height)));
views.get("pnlupload").vw.setLeft((int)((2d / 100 * width)));
views.get("pnlupload").vw.setWidth((int)((views.get("pnluploadbox").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
views.get("pnlupload").vw.setTop((int)((25d / 100 * height)));
views.get("pnlupload").vw.setHeight((int)((views.get("pnluploadbox").vw.getHeight())-(43d / 100 * height) - ((25d / 100 * height))));
views.get("lblultitle").vw.setTop((int)((0d / 100 * height)));
views.get("lblultitle").vw.setHeight((int)((views.get("lblultitle").vw.getHeight()) - ((0d / 100 * height))));
views.get("lblultitle").vw.setLeft((int)((0d / 100 * width)));
views.get("lblultitle").vw.setWidth((int)((views.get("pnlupload").vw.getWidth()) - ((0d / 100 * width))));
views.get("lblulicon").vw.setTop((int)((0d / 100 * height)));
views.get("lblulicon").vw.setHeight((int)((views.get("lblultitle").vw.getHeight()) - ((0d / 100 * height))));
views.get("lblulicon").vw.setLeft((int)((10d * scale)));
views.get("lblulnote").vw.setLeft((int)((5d / 100 * width)));
views.get("lblulnote").vw.setWidth((int)((views.get("pnlupload").vw.getWidth())-(2d / 100 * width) - ((5d / 100 * width))));
views.get("lblulnote").vw.setTop((int)((views.get("lblultitle").vw.getTop() + views.get("lblultitle").vw.getHeight())+(10d * scale)));
views.get("lblmeterreader").vw.setTop((int)((views.get("lblulnote").vw.getTop() + views.get("lblulnote").vw.getHeight())+(10d * scale)));
views.get("lblmeterreader").vw.setLeft((int)((1.5d / 100 * width)));
views.get("pnlreaderul").vw.setTop((int)((views.get("lblmeterreader").vw.getTop())));
views.get("pnlreaderul").vw.setLeft((int)((views.get("lblmeterreader").vw.getLeft() + views.get("lblmeterreader").vw.getWidth())+(3d * scale)));
views.get("pnlreaderul").vw.setWidth((int)((views.get("pnldownload").vw.getWidth())-(3d / 100 * width) - ((views.get("lblmeterreader").vw.getLeft() + views.get("lblmeterreader").vw.getWidth())+(3d * scale))));
views.get("lblreaderulicon").vw.setLeft((int)((1d / 100 * width)));
views.get("lblreaderulicon").vw.setTop((int)((0.5d / 100 * height)));
views.get("lblreaderulicon").vw.setHeight((int)((views.get("pnlreaderul").vw.getHeight())-(0.5d / 100 * height) - ((0.5d / 100 * height))));
views.get("cboreaderupload").vw.setLeft((int)((views.get("lblreaderulicon").vw.getLeft() + views.get("lblreaderulicon").vw.getWidth())+(3d * scale)));
views.get("cboreaderupload").vw.setWidth((int)((views.get("pnlreaderul").vw.getWidth())-(1d / 100 * width) - ((views.get("lblreaderulicon").vw.getLeft() + views.get("lblreaderulicon").vw.getWidth())+(3d * scale))));
views.get("cboreaderupload").vw.setTop((int)((0.5d / 100 * height)));
views.get("cboreaderupload").vw.setHeight((int)((views.get("pnlreaderul").vw.getHeight())-(0.5d / 100 * height) - ((0.5d / 100 * height))));
views.get("btncancelupload").vw.setTop((int)((views.get("pnlreaderul").vw.getTop() + views.get("pnlreaderul").vw.getHeight())+(10d * scale)));
views.get("btncancelupload").vw.setHeight((int)((views.get("pnlupload").vw.getHeight())-(10d * scale) - ((views.get("pnlreaderul").vw.getTop() + views.get("pnlreaderul").vw.getHeight())+(10d * scale))));
views.get("btncancelupload").vw.setLeft((int)((5d / 100 * width)));
views.get("btncancelupload").vw.setWidth((int)((views.get("pnlupload").vw.getWidth())-(63d / 100 * width) - ((5d / 100 * width))));
views.get("btnokupload").vw.setTop((int)((views.get("pnlreaderul").vw.getTop() + views.get("pnlreaderul").vw.getHeight())+(10d * scale)));
views.get("btnokupload").vw.setHeight((int)((views.get("pnlupload").vw.getHeight())-(10d * scale) - ((views.get("pnlreaderul").vw.getTop() + views.get("pnlreaderul").vw.getHeight())+(10d * scale))));
//BA.debugLineNum = 115;BA.debugLine="btnOkUpload.SetLeftAndRight(pnlUpload.Width - 35%x, pnlUpload.Width - 3%x)"[DataSyncingNew/General script]
views.get("btnokupload").vw.setLeft((int)((views.get("pnlupload").vw.getWidth())-(35d / 100 * width)));
views.get("btnokupload").vw.setWidth((int)((views.get("pnlupload").vw.getWidth())-(3d / 100 * width) - ((views.get("pnlupload").vw.getWidth())-(35d / 100 * width))));

}
}