package com.bwsi.MeterReader.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_camera{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("panel1").vw.setLeft((int)(0d));
views.get("panel1").vw.setWidth((int)((100d / 100 * width) - (0d)));
views.get("panel1").vw.setTop((int)(0d));
views.get("panel1").vw.setHeight((int)((100d / 100 * height) - (0d)));
views.get("pnlcambutton").vw.setTop((int)((90d / 100 * height)));
views.get("pnlcambutton").vw.setHeight((int)((100d / 100 * height) - ((90d / 100 * height))));
views.get("pnlcambutton").vw.setLeft((int)((50d / 100 * width) - (views.get("pnlcambutton").vw.getWidth() / 2)));
views.get("btnflash").vw.setLeft((int)((25d / 100 * width) - (views.get("btnflash").vw.getWidth() / 2)));
views.get("btntakepicture").vw.setLeft((int)((50d / 100 * width) - (views.get("btntakepicture").vw.getWidth() / 2)));
views.get("btnchangecam").vw.setLeft((int)((75d / 100 * width) - (views.get("btnchangecam").vw.getWidth() / 2)));
views.get("btnflash").vw.setTop((int)((5d / 100 * height) - (views.get("btnflash").vw.getHeight() / 2)));
views.get("btntakepicture").vw.setTop((int)((5d / 100 * height) - (views.get("btntakepicture").vw.getHeight() / 2)));
views.get("btnchangecam").vw.setTop((int)((5d / 100 * height) - (views.get("btnchangecam").vw.getHeight() / 2)));

}
}