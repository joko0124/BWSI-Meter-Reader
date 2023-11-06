package com.bwsi.MeterReader.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_branchsettings{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("cbobranches").vw.setLeft((int)((views.get("lblbranch").vw.getLeft() + views.get("lblbranch").vw.getWidth())+(2d * scale)));
views.get("cbobranches").vw.setWidth((int)((100d / 100 * width) - ((views.get("lblbranch").vw.getLeft() + views.get("lblbranch").vw.getWidth())+(2d * scale))));

}
}