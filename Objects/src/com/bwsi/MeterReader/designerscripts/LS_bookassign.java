package com.bwsi.MeterReader.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_bookassign{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("pnlassignedbooks").vw.setLeft((int)((1d / 100 * width)));
views.get("pnlassignedbooks").vw.setWidth((int)((99d / 100 * width) - ((1d / 100 * width))));
views.get("pnlassignedbooks").vw.setHeight((int)((230d * scale)));
views.get("imgicon").vw.setLeft((int)((2d / 100 * width)));
views.get("imgicon").vw.setTop((int)((5d * scale)));
views.get("lblbookno").vw.setLeft((int)((views.get("imgicon").vw.getLeft() + views.get("imgicon").vw.getWidth())+(3d * scale)));
views.get("lblbookno").vw.setWidth((int)((views.get("pnlassignedbooks").vw.getWidth())-(2d / 100 * width) - ((views.get("imgicon").vw.getLeft() + views.get("imgicon").vw.getWidth())+(3d * scale))));
views.get("lblbookno").vw.setTop((int)((views.get("imgicon").vw.getTop())));
views.get("lblbookdesc").vw.setTop((int)((views.get("lblbookno").vw.getTop() + views.get("lblbookno").vw.getHeight())));
views.get("lblbookdesc").vw.setLeft((int)((views.get("lblbookno").vw.getLeft())));
views.get("lblbookdesc").vw.setWidth((int)((views.get("pnlassignedbooks").vw.getWidth())-(2d / 100 * width) - ((views.get("lblbookno").vw.getLeft()))));
views.get("lblnoaccts").vw.setTop((int)((views.get("lblbookdesc").vw.getTop() + views.get("lblbookdesc").vw.getHeight())));
views.get("lblnoaccts").vw.setLeft((int)((views.get("lblbookno").vw.getLeft())));
views.get("lblnoaccts").vw.setWidth((int)((views.get("pnlassignedbooks").vw.getWidth())-(2d / 100 * width) - ((views.get("lblbookno").vw.getLeft()))));
views.get("line").vw.setLeft((int)((views.get("imgicon").vw.getLeft())));
views.get("line").vw.setWidth((int)((views.get("lblbookno").vw.getLeft() + views.get("lblbookno").vw.getWidth()) - ((views.get("imgicon").vw.getLeft()))));
views.get("line").vw.setTop((int)((views.get("lblnoaccts").vw.getTop() + views.get("lblnoaccts").vw.getHeight())));
views.get("lblreadtitle").vw.setTop((int)((views.get("lblnoaccts").vw.getTop() + views.get("lblnoaccts").vw.getHeight())+(10d * scale)));
views.get("lblreadtitle").vw.setLeft((int)((2d / 100 * width)));
views.get("lblread").vw.setTop((int)((views.get("lblreadtitle").vw.getTop())));
views.get("lblread").vw.setLeft((int)((views.get("lblreadtitle").vw.getLeft() + views.get("lblreadtitle").vw.getWidth())+(5d * scale)));
views.get("lblunreadtitle").vw.setTop((int)((views.get("lblnoaccts").vw.getTop() + views.get("lblnoaccts").vw.getHeight())+(10d * scale)));
views.get("lblunread").vw.setTop((int)((views.get("lblunreadtitle").vw.getTop())));
views.get("lblunread").vw.setLeft((int)((views.get("pnlassignedbooks").vw.getWidth())-(3d / 100 * width) - (views.get("lblunread").vw.getWidth())));
views.get("lblunreadtitle").vw.setLeft((int)((views.get("lblunread").vw.getLeft()) - (views.get("lblunreadtitle").vw.getWidth())));
views.get("lblmisscodetitle").vw.setTop((int)((views.get("lblreadtitle").vw.getTop() + views.get("lblreadtitle").vw.getHeight())));
views.get("lblmisscodetitle").vw.setLeft((int)((2d / 100 * width)));
views.get("lblmisscode").vw.setTop((int)((views.get("lblmisscodetitle").vw.getTop())));
views.get("lblmisscode").vw.setLeft((int)((views.get("lblmisscodetitle").vw.getLeft() + views.get("lblmisscodetitle").vw.getWidth())+(5d * scale)));
views.get("lblzerotitle").vw.setTop((int)((views.get("lblmisscodetitle").vw.getTop())));
views.get("lblzero").vw.setTop((int)((views.get("lblzerotitle").vw.getTop())));
views.get("lblzero").vw.setLeft((int)((views.get("pnlassignedbooks").vw.getWidth())-(3d / 100 * width) - (views.get("lblzero").vw.getWidth())));
views.get("lblzerotitle").vw.setLeft((int)((views.get("lblzero").vw.getLeft()) - (views.get("lblzerotitle").vw.getWidth())));
views.get("lblhighttle").vw.setTop((int)((views.get("lblmisscodetitle").vw.getTop() + views.get("lblmisscodetitle").vw.getHeight())));
views.get("lblhighttle").vw.setLeft((int)((2d / 100 * width)));
views.get("lblhigh").vw.setTop((int)((views.get("lblhighttle").vw.getTop())));
views.get("lblhigh").vw.setLeft((int)((views.get("lblhighttle").vw.getLeft() + views.get("lblhighttle").vw.getWidth())+(5d * scale)));
views.get("lbllowtitle").vw.setTop((int)((views.get("lblhighttle").vw.getTop())));
views.get("lbllow").vw.setTop((int)((views.get("lbllowtitle").vw.getTop())));
views.get("lbllow").vw.setLeft((int)((views.get("pnlassignedbooks").vw.getWidth())-(3d / 100 * width) - (views.get("lbllow").vw.getWidth())));
views.get("lbllowtitle").vw.setLeft((int)((views.get("lbllow").vw.getLeft()) - (views.get("lbllowtitle").vw.getWidth())));
views.get("lblavetitle").vw.setTop((int)((views.get("lblhighttle").vw.getTop() + views.get("lblhighttle").vw.getHeight())));
views.get("lblavetitle").vw.setLeft((int)((2d / 100 * width)));
views.get("lblave").vw.setTop((int)((views.get("lblavetitle").vw.getTop())));
views.get("lblave").vw.setLeft((int)((views.get("lblavetitle").vw.getLeft() + views.get("lblavetitle").vw.getWidth())+(5d * scale)));
views.get("lblunprinttitle").vw.setTop((int)((views.get("lblavetitle").vw.getTop())));
views.get("lblunprint").vw.setTop((int)((views.get("lblunprinttitle").vw.getTop())));
views.get("lblunprint").vw.setLeft((int)((views.get("pnlassignedbooks").vw.getWidth())-(3d / 100 * width) - (views.get("lblunprint").vw.getWidth())));
views.get("lblunprinttitle").vw.setLeft((int)((views.get("lbllow").vw.getLeft()) - (views.get("lblunprinttitle").vw.getWidth())));
views.get("btnload").vw.setLeft((int)((views.get("imgicon").vw.getLeft())));
views.get("btnload").vw.setWidth((int)((views.get("lblbookno").vw.getLeft() + views.get("lblbookno").vw.getWidth()) - ((views.get("imgicon").vw.getLeft()))));
views.get("btnload").vw.setTop((int)((views.get("lblunprinttitle").vw.getTop() + views.get("lblunprinttitle").vw.getHeight())+(10d * scale)));
views.get("btnload").vw.setHeight((int)((views.get("pnlassignedbooks").vw.getHeight())-(5d * scale) - ((views.get("lblunprinttitle").vw.getTop() + views.get("lblunprinttitle").vw.getHeight())+(10d * scale))));

}
}