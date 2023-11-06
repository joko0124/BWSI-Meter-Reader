package com.bwsi.MeterReader.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_validationreport{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("pnlcustomers").vw.setLeft((int)((0d / 100 * width)));
views.get("pnlcustomers").vw.setWidth((int)((100d / 100 * width) - ((0d / 100 * width))));
views.get("label1").vw.setLeft((int)((1d / 100 * width)));
views.get("lblaccountno").vw.setLeft((int)((views.get("label1").vw.getLeft() + views.get("label1").vw.getWidth())+(5d * scale)));
views.get("label1").vw.setTop((int)((1d / 100 * height)));
views.get("lblaccountno").vw.setTop((int)((views.get("label1").vw.getTop())));
views.get("lblaccountno").vw.setWidth((int)((100d / 100 * width)-(views.get("label1").vw.getWidth())-(12d * scale)));
views.get("label2").vw.setLeft((int)((1d / 100 * width)));
views.get("lblaccountname").vw.setLeft((int)((views.get("label2").vw.getLeft() + views.get("label2").vw.getWidth())+(5d * scale)));
views.get("label2").vw.setTop((int)((views.get("label1").vw.getTop() + views.get("label1").vw.getHeight())+(1d * scale)));
views.get("lblaccountname").vw.setTop((int)((views.get("label2").vw.getTop())));
views.get("lblaccountname").vw.setWidth((int)((100d / 100 * width)-(views.get("label2").vw.getWidth())-(12d * scale)));
views.get("label3").vw.setLeft((int)((1d / 100 * width)));
views.get("lbladdress").vw.setLeft((int)((views.get("label3").vw.getLeft() + views.get("label3").vw.getWidth())+(5d * scale)));
views.get("label3").vw.setTop((int)((views.get("label2").vw.getTop() + views.get("label2").vw.getHeight())+(1d * scale)));
views.get("lbladdress").vw.setTop((int)((views.get("label3").vw.getTop())));
views.get("lbladdress").vw.setWidth((int)((100d / 100 * width)-(views.get("label3").vw.getWidth())-(12d * scale)));
views.get("label4").vw.setLeft((int)((1d / 100 * width)));
views.get("lblbookno").vw.setLeft((int)((views.get("label4").vw.getLeft() + views.get("label4").vw.getWidth())+(5d * scale)));
views.get("label4").vw.setTop((int)((views.get("lbladdress").vw.getTop() + views.get("lbladdress").vw.getHeight())+(10d * scale)));
views.get("lblbookno").vw.setTop((int)((views.get("label4").vw.getTop())));
views.get("label5").vw.setLeft((int)((1d / 100 * width)));
views.get("lblseqno").vw.setLeft((int)((views.get("label5").vw.getLeft() + views.get("label5").vw.getWidth())+(5d * scale)));
views.get("label5").vw.setTop((int)((views.get("label4").vw.getTop() + views.get("label4").vw.getHeight())+(1d * scale)));
views.get("lblseqno").vw.setTop((int)((views.get("label5").vw.getTop())));
views.get("label6").vw.setLeft((int)((1d / 100 * width)));
views.get("lblmeterno").vw.setLeft((int)((views.get("label6").vw.getLeft() + views.get("label6").vw.getWidth())+(5d * scale)));
views.get("label6").vw.setTop((int)((views.get("label5").vw.getTop() + views.get("label5").vw.getHeight())+(1d * scale)));
views.get("lblmeterno").vw.setTop((int)((views.get("label6").vw.getTop())));
views.get("label7").vw.setLeft((int)((views.get("lblbookno").vw.getLeft() + views.get("lblbookno").vw.getWidth())+(15d * scale)));
views.get("lblpresrdg").vw.setLeft((int)((views.get("label7").vw.getLeft() + views.get("label7").vw.getWidth())));
views.get("label7").vw.setTop((int)((views.get("label4").vw.getTop())));
views.get("lblpresrdg").vw.setTop((int)((views.get("label7").vw.getTop())));
views.get("label8").vw.setLeft((int)((views.get("lblseqno").vw.getLeft() + views.get("lblseqno").vw.getWidth())+(15d * scale)));
views.get("lblprevrdg").vw.setLeft((int)((views.get("label8").vw.getLeft() + views.get("label8").vw.getWidth())));
views.get("label8").vw.setTop((int)((views.get("label7").vw.getTop() + views.get("label7").vw.getHeight())+(1d * scale)));
views.get("lblprevrdg").vw.setTop((int)((views.get("label8").vw.getTop())));
views.get("label9").vw.setLeft((int)((views.get("lblmeterno").vw.getLeft() + views.get("lblmeterno").vw.getWidth())+(15d * scale)));
views.get("lblcum").vw.setLeft((int)((views.get("label9").vw.getLeft() + views.get("label9").vw.getWidth())));
views.get("label9").vw.setTop((int)((views.get("label8").vw.getTop() + views.get("label8").vw.getHeight())));
views.get("lblcum").vw.setTop((int)((views.get("label9").vw.getTop())));
views.get("label10").vw.setLeft((int)((1d / 100 * width)));
views.get("lblreason").vw.setLeft((int)((views.get("label10").vw.getLeft() + views.get("label10").vw.getWidth())+(5d * scale)));
views.get("label10").vw.setTop((int)((views.get("label9").vw.getTop() + views.get("label9").vw.getHeight())+(10d * scale)));
views.get("lblreason").vw.setTop((int)((views.get("label10").vw.getTop())));
views.get("lblreason").vw.setWidth((int)((100d / 100 * width)-(views.get("label10").vw.getWidth())-(12d * scale)));

}
}