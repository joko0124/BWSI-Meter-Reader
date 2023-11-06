package com.bwsi.MeterReader.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_custlist{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("pnlcustomers").vw.setLeft((int)((0d / 100 * width)));
views.get("pnlcustomers").vw.setWidth((int)((100d / 100 * width) - ((0d / 100 * width))));
views.get("label1").vw.setLeft((int)((1d / 100 * width)));
views.get("label2").vw.setLeft((int)((1d / 100 * width)));
views.get("label3").vw.setLeft((int)((1d / 100 * width)));
views.get("label4").vw.setLeft((int)((1d / 100 * width)));
views.get("label5").vw.setLeft((int)((1d / 100 * width)));
views.get("label6").vw.setLeft((int)((1d / 100 * width)));
views.get("label7").vw.setLeft((int)((1d / 100 * width)));
views.get("btndetails").vw.setLeft((int)((views.get("pnlcustomers").vw.getLeft() + views.get("pnlcustomers").vw.getWidth())-(10d * scale) - (views.get("btndetails").vw.getWidth())));
views.get("btndetails").vw.setTop((int)((views.get("lblstatus").vw.getTop() + views.get("lblstatus").vw.getHeight()) - (views.get("btndetails").vw.getHeight())));

}
}