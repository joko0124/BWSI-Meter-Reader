package com.bwsi.MeterReader.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_bookassignment{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("pnlassignedbooks").vw.setLeft((int)((1d / 100 * width)));
views.get("pnlassignedbooks").vw.setWidth((int)((99d / 100 * width) - ((1d / 100 * width))));
views.get("btnload").vw.setLeft((int)((views.get("pnlassignedbooks").vw.getLeft() + views.get("pnlassignedbooks").vw.getWidth())-(10d * scale) - (views.get("btnload").vw.getWidth())));
views.get("btnload").vw.setTop((int)((views.get("lblavetitle").vw.getTop() + views.get("lblavetitle").vw.getHeight())-(5d * scale) - (views.get("btnload").vw.getHeight())));
views.get("lblbookno").vw.setLeft((int)((10d * scale)));

}
}