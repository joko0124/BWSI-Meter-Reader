package com.bwsi.MeterReader.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_mreading2{

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
views.get("pnlaccountinfo").vw.setTop((int)((0d / 100 * height)));
views.get("pnlaccountinfo").vw.setWidth((int)((98d / 100 * width)));
views.get("pnlaccountinfo").vw.setLeft((int)((50d / 100 * width) - (views.get("pnlaccountinfo").vw.getWidth() / 2)));
views.get("label1").vw.setLeft((int)((2d / 100 * width)));
views.get("lblseqno").vw.setLeft((int)((views.get("label1").vw.getLeft() + views.get("label1").vw.getWidth())+(2d * scale)));
views.get("lblseqno").vw.setWidth((int)((views.get("pnlaccountinfo").vw.getWidth())-(56d / 100 * width) - ((views.get("label1").vw.getLeft() + views.get("label1").vw.getWidth())+(2d * scale))));
views.get("label2").vw.setTop((int)((views.get("label1").vw.getTop())));
views.get("label2").vw.setLeft((int)((views.get("lblseqno").vw.getLeft() + views.get("lblseqno").vw.getWidth())+(2d * scale)));
views.get("lblmeter").vw.setLeft((int)((views.get("label2").vw.getLeft() + views.get("label2").vw.getWidth())+(3d * scale)));
views.get("lblmeter").vw.setWidth((int)((views.get("pnlaccountinfo").vw.getWidth())-(1d / 100 * width) - ((views.get("label2").vw.getLeft() + views.get("label2").vw.getWidth())+(3d * scale))));
views.get("label3").vw.setLeft((int)((views.get("label1").vw.getLeft() + views.get("label1").vw.getWidth()) - (views.get("label3").vw.getWidth())));
views.get("lblacctno").vw.setLeft((int)((views.get("label3").vw.getLeft() + views.get("label3").vw.getWidth())+(5d * scale)));
views.get("lblacctno").vw.setWidth((int)((views.get("pnlaccountinfo").vw.getWidth())-(2d / 100 * width) - ((views.get("label3").vw.getLeft() + views.get("label3").vw.getWidth())+(5d * scale))));
views.get("label4").vw.setLeft((int)((views.get("label3").vw.getLeft() + views.get("label3").vw.getWidth()) - (views.get("label4").vw.getWidth())));
views.get("lblacctname").vw.setLeft((int)((views.get("label4").vw.getLeft() + views.get("label4").vw.getWidth())+(5d * scale)));
views.get("lblacctname").vw.setWidth((int)((views.get("pnlaccountinfo").vw.getWidth())-(2d / 100 * width) - ((views.get("label4").vw.getLeft() + views.get("label4").vw.getWidth())+(5d * scale))));
views.get("label5").vw.setLeft((int)((views.get("label4").vw.getLeft() + views.get("label4").vw.getWidth()) - (views.get("label5").vw.getWidth())));
views.get("lblacctaddress").vw.setLeft((int)((views.get("label5").vw.getLeft() + views.get("label5").vw.getWidth())+(5d * scale)));
views.get("lblacctaddress").vw.setWidth((int)((views.get("pnlaccountinfo").vw.getWidth())-(1.25d / 100 * width) - ((views.get("label5").vw.getLeft() + views.get("label5").vw.getWidth())+(5d * scale))));
views.get("label6").vw.setLeft((int)((views.get("label5").vw.getLeft() + views.get("label5").vw.getWidth()) - (views.get("label6").vw.getWidth())));
views.get("lblclass").vw.setLeft((int)((views.get("label6").vw.getLeft() + views.get("label6").vw.getWidth())+(5d * scale)));
views.get("lblclass").vw.setWidth((int)((views.get("pnlaccountinfo").vw.getWidth())-(2d / 100 * width) - ((views.get("label6").vw.getLeft() + views.get("label6").vw.getWidth())+(5d * scale))));
views.get("pnlprevious").vw.setWidth((int)((98d / 100 * width)));
views.get("pnlprevious").vw.setLeft((int)((50d / 100 * width) - (views.get("pnlprevious").vw.getWidth() / 2)));
views.get("pnlpresent").vw.setWidth((int)((98d / 100 * width)));
views.get("pnlpresent").vw.setLeft((int)((50d / 100 * width) - (views.get("pnlpresent").vw.getWidth() / 2)));
views.get("txtpresrdg").vw.setWidth((int)((95d / 100 * width)));
views.get("txtpresrdg").vw.setLeft((int)((49d / 100 * width) - (views.get("txtpresrdg").vw.getWidth() / 2)));
views.get("lblreadstatus").vw.setLeft((int)((views.get("pnlpresent").vw.getWidth())-(45d / 100 * width)));
views.get("lblreadstatus").vw.setWidth((int)((views.get("txtpresrdg").vw.getLeft() + views.get("txtpresrdg").vw.getWidth()) - ((views.get("pnlpresent").vw.getWidth())-(45d / 100 * width))));
views.get("lbldiscon").vw.setTop((int)((views.get("lblreadstatus").vw.getTop() + views.get("lblreadstatus").vw.getHeight()/2) - (views.get("lbldiscon").vw.getHeight() / 2)));
views.get("lbldiscon").vw.setLeft((int)((views.get("txtpresrdg").vw.getLeft())));
views.get("lbldiscon").vw.setWidth((int)((views.get("lblreadstatus").vw.getLeft())-(10d * scale) - ((views.get("txtpresrdg").vw.getLeft()))));
views.get("lbltitle").vw.setTop((int)((0d / 100 * height)));
views.get("lbltitle").vw.setWidth((int)((97d / 100 * width)));
views.get("lbltitle").vw.setLeft((int)((49d / 100 * width) - (views.get("lbltitle").vw.getWidth() / 2)));
views.get("pnlfindings").vw.setLeft((int)((views.get("pnlmain").vw.getLeft())));
views.get("pnlfindings").vw.setWidth((int)((views.get("pnlmain").vw.getWidth()) - ((views.get("pnlmain").vw.getLeft()))));
views.get("pnlfindings").vw.setTop((int)((49d / 100 * height)));
views.get("pnlfindings").vw.setHeight((int)((68d / 100 * height) - ((49d / 100 * height))));
views.get("pnlbuttons").vw.setTop((int)((68d / 100 * height)));
views.get("pnlbuttons").vw.setHeight((int)((85d / 100 * height) - ((68d / 100 * height))));
views.get("pnlstatus").vw.setLeft((int)((views.get("pnlmain").vw.getLeft())));
views.get("pnlstatus").vw.setWidth((int)((views.get("pnlmain").vw.getWidth()) - ((views.get("pnlmain").vw.getLeft()))));
views.get("pnlstatus").vw.setTop((int)((views.get("pnlbuttons").vw.getTop() + views.get("pnlbuttons").vw.getHeight())+(5d * scale)));
views.get("pnlstatus").vw.setHeight((int)((views.get("pnlmain").vw.getHeight()) - ((views.get("pnlbuttons").vw.getTop() + views.get("pnlbuttons").vw.getHeight())+(5d * scale))));
views.get("lblnoaccts").vw.setTop((int)(0-(1d / 100 * height)));
views.get("lblnoaccts").vw.setLeft((int)((views.get("pnlstatus").vw.getLeft())+(1d / 100 * width)));
views.get("lblnoaccts").vw.setWidth((int)((views.get("pnlstatus").vw.getWidth())-(50d / 100 * width) - ((views.get("pnlstatus").vw.getLeft())+(1d / 100 * width))));
views.get("lblunreadcount").vw.setTop((int)(0-(1d / 100 * height)));
views.get("lblunreadcount").vw.setLeft((int)((views.get("pnlstatus").vw.getWidth())-(50d / 100 * width)));
views.get("lblunreadcount").vw.setWidth((int)((views.get("pnlstatus").vw.getWidth())-(1d / 100 * width) - ((views.get("pnlstatus").vw.getWidth())-(50d / 100 * width))));
views.get("pnlsearch").vw.setWidth((int)((100d / 100 * width)));
views.get("pnlsearch").vw.setLeft((int)((0d / 100 * width)));
views.get("pnlsearch").vw.setWidth((int)((100d / 100 * width) - ((0d / 100 * width))));
views.get("pnlsearch").vw.setHeight((int)((100d / 100 * height)));
views.get("pnlsearch").vw.setTop((int)((0d / 100 * height)));
views.get("pnlsearch").vw.setHeight((int)((100d / 100 * height) - ((0d / 100 * height))));
views.get("pnlsearchanchor").vw.setLeft((int)((2d / 100 * width)));
views.get("pnlsearchanchor").vw.setWidth((int)((views.get("pnlsearch").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
views.get("pnlsearchanchor").vw.setHeight((int)((83d / 100 * height)));
views.get("pnlsearchanchor").vw.setTop((int)((views.get("pnlsearch").vw.getHeight())/2d-(3d / 100 * height) - (views.get("pnlsearchanchor").vw.getHeight() / 2)));
views.get("lblsearchlabel").vw.setLeft((int)((0d / 100 * width)));
views.get("lblsearchlabel").vw.setWidth((int)((views.get("pnlsearchanchor").vw.getWidth()) - ((0d / 100 * width))));
views.get("lblsearchlabel").vw.setHeight((int)((4d / 100 * height)));
views.get("lblsearchlabel").vw.setTop((int)(0d));
views.get("pnlsearchoptions").vw.setTop((int)((views.get("lblsearchlabel").vw.getTop() + views.get("lblsearchlabel").vw.getHeight())+(3d * scale)));
views.get("pnlsearchoptions").vw.setHeight((int)((views.get("pnlsearchanchor").vw.getHeight())-(69d / 100 * height) - ((views.get("lblsearchlabel").vw.getTop() + views.get("lblsearchlabel").vw.getHeight())+(3d * scale))));
views.get("pnlsearchoptions").vw.setLeft((int)((2d / 100 * width)));
views.get("pnlsearchoptions").vw.setWidth((int)((views.get("pnlsearchanchor").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
views.get("label12").vw.setLeft((int)((0d / 100 * width)));
views.get("label12").vw.setWidth((int)((views.get("pnlsearchoptions").vw.getWidth()) - ((0d / 100 * width))));
views.get("optunread").vw.setLeft((int)((2d / 100 * width)));
views.get("optunread").vw.setWidth((int)((30d / 100 * width) - ((2d / 100 * width))));
views.get("optunread").vw.setTop((int)((views.get("label12").vw.getTop() + views.get("label12").vw.getHeight())+(8d * scale)));
views.get("optunread").vw.setHeight((int)((views.get("pnlsearchoptions").vw.getHeight())-(2d / 100 * height) - ((views.get("label12").vw.getTop() + views.get("label12").vw.getHeight())+(8d * scale))));
views.get("optread").vw.setLeft((int)((33d / 100 * width)));
views.get("optread").vw.setWidth((int)((58d / 100 * width) - ((33d / 100 * width))));
views.get("optread").vw.setTop((int)((views.get("label12").vw.getTop() + views.get("label12").vw.getHeight())+(8d * scale)));
views.get("optread").vw.setHeight((int)((views.get("pnlsearchoptions").vw.getHeight())-(2d / 100 * height) - ((views.get("label12").vw.getTop() + views.get("label12").vw.getHeight())+(8d * scale))));
views.get("optall").vw.setLeft((int)((60d / 100 * width)));
views.get("optall").vw.setWidth((int)((views.get("pnlsearchoptions").vw.getWidth())-(2d / 100 * width) - ((60d / 100 * width))));
views.get("optall").vw.setTop((int)((views.get("label12").vw.getTop() + views.get("label12").vw.getHeight())+(8d * scale)));
views.get("optall").vw.setHeight((int)((views.get("pnlsearchoptions").vw.getHeight())-(2d / 100 * height) - ((views.get("label12").vw.getTop() + views.get("label12").vw.getHeight())+(8d * scale))));
views.get("pnlsearchby").vw.setTop((int)((views.get("pnlsearchoptions").vw.getTop() + views.get("pnlsearchoptions").vw.getHeight())+(5d * scale)));
views.get("pnlsearchby").vw.setHeight((int)((views.get("pnlsearchanchor").vw.getHeight())-(57d / 100 * height) - ((views.get("pnlsearchoptions").vw.getTop() + views.get("pnlsearchoptions").vw.getHeight())+(5d * scale))));
views.get("pnlsearchby").vw.setLeft((int)((2d / 100 * width)));
views.get("pnlsearchby").vw.setWidth((int)((views.get("pnlsearchanchor").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
views.get("label16").vw.setLeft((int)((0d / 100 * width)));
views.get("label16").vw.setWidth((int)((views.get("pnlsearchby").vw.getWidth()) - ((0d / 100 * width))));
views.get("optseqno").vw.setLeft((int)((2d / 100 * width)));
views.get("optseqno").vw.setWidth((int)((25d / 100 * width) - ((2d / 100 * width))));
views.get("optseqno").vw.setTop((int)((views.get("label16").vw.getTop() + views.get("label16").vw.getHeight())+(8d * scale)));
views.get("optseqno").vw.setHeight((int)((views.get("pnlsearchby").vw.getHeight())-(2d / 100 * height) - ((views.get("label16").vw.getTop() + views.get("label16").vw.getHeight())+(8d * scale))));
views.get("optmeterno").vw.setLeft((int)((30d / 100 * width)));
views.get("optmeterno").vw.setWidth((int)((58d / 100 * width) - ((30d / 100 * width))));
views.get("optmeterno").vw.setTop((int)((views.get("label16").vw.getTop() + views.get("label16").vw.getHeight())+(8d * scale)));
views.get("optmeterno").vw.setHeight((int)((views.get("pnlsearchby").vw.getHeight())-(2d / 100 * height) - ((views.get("label16").vw.getTop() + views.get("label16").vw.getHeight())+(8d * scale))));
views.get("optacctname").vw.setLeft((int)((60d / 100 * width)));
views.get("optacctname").vw.setWidth((int)((views.get("pnlsearchby").vw.getWidth())-(2d / 100 * width) - ((60d / 100 * width))));
//BA.debugLineNum = 104;BA.debugLine="optAcctName.SetTopAndBottom(Label16.Bottom + 8dip, pnlSearchBy.Height - 2%y)"[mreading2/General script]
views.get("optacctname").vw.setTop((int)((views.get("label16").vw.getTop() + views.get("label16").vw.getHeight())+(8d * scale)));
views.get("optacctname").vw.setHeight((int)((views.get("pnlsearchby").vw.getHeight())-(2d / 100 * height) - ((views.get("label16").vw.getTop() + views.get("label16").vw.getHeight())+(8d * scale))));
//BA.debugLineNum = 106;BA.debugLine="SearchPanel.SetTopAndBottom(pnlSearchBy.Bottom + 5dip, pnlSearchAnchor.Height - 9%y)"[mreading2/General script]
views.get("searchpanel").vw.setTop((int)((views.get("pnlsearchby").vw.getTop() + views.get("pnlsearchby").vw.getHeight())+(5d * scale)));
views.get("searchpanel").vw.setHeight((int)((views.get("pnlsearchanchor").vw.getHeight())-(9d / 100 * height) - ((views.get("pnlsearchby").vw.getTop() + views.get("pnlsearchby").vw.getHeight())+(5d * scale))));
//BA.debugLineNum = 107;BA.debugLine="SearchPanel.SetLeftAndRight(2%x, pnlSearchAnchor.Width - 2%x)"[mreading2/General script]
views.get("searchpanel").vw.setLeft((int)((2d / 100 * width)));
views.get("searchpanel").vw.setWidth((int)((views.get("pnlsearchanchor").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
//BA.debugLineNum = 109;BA.debugLine="pnlSearchStatus.SetTopAndBottom(SearchPanel.Bottom + 10dip, pnlSearchAnchor.Height - 2%y)"[mreading2/General script]
views.get("pnlsearchstatus").vw.setTop((int)((views.get("searchpanel").vw.getTop() + views.get("searchpanel").vw.getHeight())+(10d * scale)));
views.get("pnlsearchstatus").vw.setHeight((int)((views.get("pnlsearchanchor").vw.getHeight())-(2d / 100 * height) - ((views.get("searchpanel").vw.getTop() + views.get("searchpanel").vw.getHeight())+(10d * scale))));
//BA.debugLineNum = 110;BA.debugLine="pnlSearchStatus.SetLeftAndRight(2%x, 55%x)"[mreading2/General script]
views.get("pnlsearchstatus").vw.setLeft((int)((2d / 100 * width)));
views.get("pnlsearchstatus").vw.setWidth((int)((55d / 100 * width) - ((2d / 100 * width))));
//BA.debugLineNum = 112;BA.debugLine="lblSearchRecCount.SetLeftAndRight(2%x, pnlSearchStatus.Width - 2%x)"[mreading2/General script]
views.get("lblsearchreccount").vw.setLeft((int)((2d / 100 * width)));
views.get("lblsearchreccount").vw.setWidth((int)((views.get("pnlsearchstatus").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
//BA.debugLineNum = 113;BA.debugLine="lblSearchRecCount.SetTopAndBottom(1%y, pnlSearchStatus.Height - 1%y)"[mreading2/General script]
views.get("lblsearchreccount").vw.setTop((int)((1d / 100 * height)));
views.get("lblsearchreccount").vw.setHeight((int)((views.get("pnlsearchstatus").vw.getHeight())-(1d / 100 * height) - ((1d / 100 * height))));
//BA.debugLineNum = 115;BA.debugLine="btnCancelSearch.SetTopAndBottom(SearchPanel.Bottom + 10dip, pnlSearchAnchor.Height - 2%y)"[mreading2/General script]
views.get("btncancelsearch").vw.setTop((int)((views.get("searchpanel").vw.getTop() + views.get("searchpanel").vw.getHeight())+(10d * scale)));
views.get("btncancelsearch").vw.setHeight((int)((views.get("pnlsearchanchor").vw.getHeight())-(2d / 100 * height) - ((views.get("searchpanel").vw.getTop() + views.get("searchpanel").vw.getHeight())+(10d * scale))));
//BA.debugLineNum = 116;BA.debugLine="btnCancelSearch.SetLeftAndRight(pnlSearchStatus.Right + 5dip, pnlSearchAnchor.Width - 2%x)"[mreading2/General script]
views.get("btncancelsearch").vw.setLeft((int)((views.get("pnlsearchstatus").vw.getLeft() + views.get("pnlsearchstatus").vw.getWidth())+(5d * scale)));
views.get("btncancelsearch").vw.setWidth((int)((views.get("pnlsearchanchor").vw.getWidth())-(2d / 100 * width) - ((views.get("pnlsearchstatus").vw.getLeft() + views.get("pnlsearchstatus").vw.getWidth())+(5d * scale))));
//BA.debugLineNum = 118;BA.debugLine="pnlUnusual.Width = 100%x"[mreading2/General script]
views.get("pnlunusual").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 119;BA.debugLine="pnlUnusual.Height = 100%y"[mreading2/General script]
views.get("pnlunusual").vw.setHeight((int)((100d / 100 * height)));
//BA.debugLineNum = 120;BA.debugLine="pnlUnusual.SetTopAndBottom(0%y, 100%y)"[mreading2/General script]
views.get("pnlunusual").vw.setTop((int)((0d / 100 * height)));
views.get("pnlunusual").vw.setHeight((int)((100d / 100 * height) - ((0d / 100 * height))));
//BA.debugLineNum = 121;BA.debugLine="pnlUnusual.SetLeftAndRight(0%x, 100%x)"[mreading2/General script]
views.get("pnlunusual").vw.setLeft((int)((0d / 100 * width)));
views.get("pnlunusual").vw.setWidth((int)((100d / 100 * width) - ((0d / 100 * width))));
//BA.debugLineNum = 123;BA.debugLine="pnlUnusualAnchor.SetLeftAndRight(2%x, pnlUnusual.Width - 2%x)"[mreading2/General script]
views.get("pnlunusualanchor").vw.setLeft((int)((2d / 100 * width)));
views.get("pnlunusualanchor").vw.setWidth((int)((views.get("pnlunusual").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
//BA.debugLineNum = 124;BA.debugLine="pnlUnusualAnchor.Height = 83%y"[mreading2/General script]
views.get("pnlunusualanchor").vw.setHeight((int)((83d / 100 * height)));
//BA.debugLineNum = 125;BA.debugLine="pnlUnusualAnchor.VerticalCenter = pnlUnusual.Height / 2 - 3%y"[mreading2/General script]
views.get("pnlunusualanchor").vw.setTop((int)((views.get("pnlunusual").vw.getHeight())/2d-(3d / 100 * height) - (views.get("pnlunusualanchor").vw.getHeight() / 2)));
//BA.debugLineNum = 127;BA.debugLine="lblUnusualTitle.SetLeftAndRight(0%x, pnlUnusualAnchor.Width)"[mreading2/General script]
views.get("lblunusualtitle").vw.setLeft((int)((0d / 100 * width)));
views.get("lblunusualtitle").vw.setWidth((int)((views.get("pnlunusualanchor").vw.getWidth()) - ((0d / 100 * width))));
//BA.debugLineNum = 128;BA.debugLine="lblUnusualTitle.Height = 4%y"[mreading2/General script]
views.get("lblunusualtitle").vw.setHeight((int)((4d / 100 * height)));
//BA.debugLineNum = 129;BA.debugLine="lblUnusualTitle.Top = 0"[mreading2/General script]
views.get("lblunusualtitle").vw.setTop((int)(0d));
//BA.debugLineNum = 131;BA.debugLine="Label17.SetLeftAndRight(2%x, pnlSearchUnusual.Width - 2%x)"[mreading2/General script]
views.get("label17").vw.setLeft((int)((2d / 100 * width)));
views.get("label17").vw.setWidth((int)((views.get("pnlsearchunusual").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
//BA.debugLineNum = 132;BA.debugLine="Label17.Top = 10dip"[mreading2/General script]
views.get("label17").vw.setTop((int)((10d * scale)));
//BA.debugLineNum = 134;BA.debugLine="pnlSearchUnusual.SetTopAndBottom(lblUnusualTitle.Bottom + 10dip, pnlUnusualAnchor.Height - 9%y)"[mreading2/General script]
views.get("pnlsearchunusual").vw.setTop((int)((views.get("lblunusualtitle").vw.getTop() + views.get("lblunusualtitle").vw.getHeight())+(10d * scale)));
views.get("pnlsearchunusual").vw.setHeight((int)((views.get("pnlunusualanchor").vw.getHeight())-(9d / 100 * height) - ((views.get("lblunusualtitle").vw.getTop() + views.get("lblunusualtitle").vw.getHeight())+(10d * scale))));
//BA.debugLineNum = 135;BA.debugLine="pnlSearchUnusual.SetLeftAndRight(2%x, pnlUnusualAnchor.Width - 2%x)"[mreading2/General script]
views.get("pnlsearchunusual").vw.setLeft((int)((2d / 100 * width)));
views.get("pnlsearchunusual").vw.setWidth((int)((views.get("pnlunusualanchor").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
//BA.debugLineNum = 137;BA.debugLine="pnlSearchUnusualStatus.SetTopAndBottom(pnlSearchUnusual.Bottom + 10dip, pnlUnusualAnchor.Height - 1.75%y)"[mreading2/General script]
views.get("pnlsearchunusualstatus").vw.setTop((int)((views.get("pnlsearchunusual").vw.getTop() + views.get("pnlsearchunusual").vw.getHeight())+(10d * scale)));
views.get("pnlsearchunusualstatus").vw.setHeight((int)((views.get("pnlunusualanchor").vw.getHeight())-(1.75d / 100 * height) - ((views.get("pnlsearchunusual").vw.getTop() + views.get("pnlsearchunusual").vw.getHeight())+(10d * scale))));
//BA.debugLineNum = 138;BA.debugLine="pnlSearchUnusualStatus.SetLeftAndRight(2%x, 60%x)"[mreading2/General script]
views.get("pnlsearchunusualstatus").vw.setLeft((int)((2d / 100 * width)));
views.get("pnlsearchunusualstatus").vw.setWidth((int)((60d / 100 * width) - ((2d / 100 * width))));
//BA.debugLineNum = 140;BA.debugLine="lblUnusualCount.SetLeftAndRight(1%x, pnlSearchUnusualStatus.Width - 1%x)"[mreading2/General script]
views.get("lblunusualcount").vw.setLeft((int)((1d / 100 * width)));
views.get("lblunusualcount").vw.setWidth((int)((views.get("pnlsearchunusualstatus").vw.getWidth())-(1d / 100 * width) - ((1d / 100 * width))));
//BA.debugLineNum = 141;BA.debugLine="lblUnusualCount.SetTopAndBottom(1%y, pnlSearchUnusualStatus.Height - 1%y)"[mreading2/General script]
views.get("lblunusualcount").vw.setTop((int)((1d / 100 * height)));
views.get("lblunusualcount").vw.setHeight((int)((views.get("pnlsearchunusualstatus").vw.getHeight())-(1d / 100 * height) - ((1d / 100 * height))));
//BA.debugLineNum = 143;BA.debugLine="btnCancelUnusual.SetTopAndBottom(pnlSearchUnusual.Bottom + 10dip, pnlUnusualAnchor.Height - 1.75%y)"[mreading2/General script]
views.get("btncancelunusual").vw.setTop((int)((views.get("pnlsearchunusual").vw.getTop() + views.get("pnlsearchunusual").vw.getHeight())+(10d * scale)));
views.get("btncancelunusual").vw.setHeight((int)((views.get("pnlunusualanchor").vw.getHeight())-(1.75d / 100 * height) - ((views.get("pnlsearchunusual").vw.getTop() + views.get("pnlsearchunusual").vw.getHeight())+(10d * scale))));
//BA.debugLineNum = 144;BA.debugLine="btnCancelUnusual.SetLeftAndRight(pnlSearchUnusualStatus.Right + 5dip, pnlUnusualAnchor.Width - 2%x)"[mreading2/General script]
views.get("btncancelunusual").vw.setLeft((int)((views.get("pnlsearchunusualstatus").vw.getLeft() + views.get("pnlsearchunusualstatus").vw.getWidth())+(5d * scale)));
views.get("btncancelunusual").vw.setWidth((int)((views.get("pnlunusualanchor").vw.getWidth())-(2d / 100 * width) - ((views.get("pnlsearchunusualstatus").vw.getLeft() + views.get("pnlsearchunusualstatus").vw.getWidth())+(5d * scale))));
//BA.debugLineNum = 151;BA.debugLine="pnlNormalMsgBox.Left = 0"[mreading2/General script]
views.get("pnlnormalmsgbox").vw.setLeft((int)(0d));
//BA.debugLineNum = 152;BA.debugLine="pnlNormalMsgBox.Top = 0"[mreading2/General script]
views.get("pnlnormalmsgbox").vw.setTop((int)(0d));
//BA.debugLineNum = 153;BA.debugLine="pnlNormalMsgBox.Width = 100%x"[mreading2/General script]
views.get("pnlnormalmsgbox").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 154;BA.debugLine="pnlNormalMsgBox.Height = 100%y"[mreading2/General script]
views.get("pnlnormalmsgbox").vw.setHeight((int)((100d / 100 * height)));
//BA.debugLineNum = 156;BA.debugLine="pnlNormalBox.SetLeftAndRight(3%x,pnlNormalMsgBox.Width - 3%x)"[mreading2/General script]
views.get("pnlnormalbox").vw.setLeft((int)((3d / 100 * width)));
views.get("pnlnormalbox").vw.setWidth((int)((views.get("pnlnormalmsgbox").vw.getWidth())-(3d / 100 * width) - ((3d / 100 * width))));
//BA.debugLineNum = 157;BA.debugLine="pnlNormalBox.Height = 29%y"[mreading2/General script]
views.get("pnlnormalbox").vw.setHeight((int)((29d / 100 * height)));
//BA.debugLineNum = 158;BA.debugLine="pnlNormalBox.VerticalCenter = pnlNormalMsgBox.Height / 2 - 7%Y"[mreading2/General script]
views.get("pnlnormalbox").vw.setTop((int)((views.get("pnlnormalmsgbox").vw.getHeight())/2d-(7d / 100 * height) - (views.get("pnlnormalbox").vw.getHeight() / 2)));
//BA.debugLineNum = 160;BA.debugLine="pnlNormalTitle.SetLeftAndRight(0%X, pnlNegativeBox.Width)"[mreading2/General script]
views.get("pnlnormaltitle").vw.setLeft((int)((0d / 100 * width)));
views.get("pnlnormaltitle").vw.setWidth((int)((views.get("pnlnegativebox").vw.getWidth()) - ((0d / 100 * width))));
//BA.debugLineNum = 161;BA.debugLine="pnlNormalTitle.SetTopAndBottom(0%y,40dip)"[mreading2/General script]
views.get("pnlnormaltitle").vw.setTop((int)((0d / 100 * height)));
views.get("pnlnormaltitle").vw.setHeight((int)((40d * scale) - ((0d / 100 * height))));
//BA.debugLineNum = 163;BA.debugLine="lblIconNormal.Left = 2%x"[mreading2/General script]
views.get("lbliconnormal").vw.setLeft((int)((2d / 100 * width)));
//BA.debugLineNum = 164;BA.debugLine="lblIconNormal.SetTopAndBottom(0.5%y, pnlNormalTitle.Height - 0.5%y)"[mreading2/General script]
views.get("lbliconnormal").vw.setTop((int)((0.5d / 100 * height)));
views.get("lbliconnormal").vw.setHeight((int)((views.get("pnlnormaltitle").vw.getHeight())-(0.5d / 100 * height) - ((0.5d / 100 * height))));
//BA.debugLineNum = 166;BA.debugLine="lblNormalMsgTitle.SetLeftAndRight(lblIconNormal.Right + 5dip, pnlNormalTitle.Width)"[mreading2/General script]
views.get("lblnormalmsgtitle").vw.setLeft((int)((views.get("lbliconnormal").vw.getLeft() + views.get("lbliconnormal").vw.getWidth())+(5d * scale)));
views.get("lblnormalmsgtitle").vw.setWidth((int)((views.get("pnlnormaltitle").vw.getWidth()) - ((views.get("lbliconnormal").vw.getLeft() + views.get("lbliconnormal").vw.getWidth())+(5d * scale))));
//BA.debugLineNum = 167;BA.debugLine="lblNormalMsgTitle.VerticalCenter = lblIconNormal.VerticalCenter"[mreading2/General script]
views.get("lblnormalmsgtitle").vw.setTop((int)((views.get("lbliconnormal").vw.getTop() + views.get("lbliconnormal").vw.getHeight()/2) - (views.get("lblnormalmsgtitle").vw.getHeight() / 2)));
//BA.debugLineNum = 169;BA.debugLine="lblNormalMsgContent.SetLeftAndRight(5%x, pnlNormalBox.Width - 2%x)"[mreading2/General script]
views.get("lblnormalmsgcontent").vw.setLeft((int)((5d / 100 * width)));
views.get("lblnormalmsgcontent").vw.setWidth((int)((views.get("pnlnormalbox").vw.getWidth())-(2d / 100 * width) - ((5d / 100 * width))));
//BA.debugLineNum = 170;BA.debugLine="lblNormalMsgContent.Top = 8%y"[mreading2/General script]
views.get("lblnormalmsgcontent").vw.setTop((int)((8d / 100 * height)));
//BA.debugLineNum = 172;BA.debugLine="btnNormalCancelPost.SetTopAndBottom(22%y, pnlNormalBox.Height - 10dip)"[mreading2/General script]
views.get("btnnormalcancelpost").vw.setTop((int)((22d / 100 * height)));
views.get("btnnormalcancelpost").vw.setHeight((int)((views.get("pnlnormalbox").vw.getHeight())-(10d * scale) - ((22d / 100 * height))));
//BA.debugLineNum = 173;BA.debugLine="btnNormalCancelPost.SetLeftAndRight(3%x, pnlNormalBox.Width - 69%x)"[mreading2/General script]
views.get("btnnormalcancelpost").vw.setLeft((int)((3d / 100 * width)));
views.get("btnnormalcancelpost").vw.setWidth((int)((views.get("pnlnormalbox").vw.getWidth())-(69d / 100 * width) - ((3d / 100 * width))));
//BA.debugLineNum = 175;BA.debugLine="btnNormalSaveOnly.SetTopAndBottom(22%y, pnlNormalBox.Height - 10dip)"[mreading2/General script]
views.get("btnnormalsaveonly").vw.setTop((int)((22d / 100 * height)));
views.get("btnnormalsaveonly").vw.setHeight((int)((views.get("pnlnormalbox").vw.getHeight())-(10d * scale) - ((22d / 100 * height))));
//BA.debugLineNum = 176;BA.debugLine="btnNormalSaveOnly.SetLeftAndRight(btnNormalCancelPost.Right + 7dip, pnlNormalBox.Width - 40%x)"[mreading2/General script]
views.get("btnnormalsaveonly").vw.setLeft((int)((views.get("btnnormalcancelpost").vw.getLeft() + views.get("btnnormalcancelpost").vw.getWidth())+(7d * scale)));
views.get("btnnormalsaveonly").vw.setWidth((int)((views.get("pnlnormalbox").vw.getWidth())-(40d / 100 * width) - ((views.get("btnnormalcancelpost").vw.getLeft() + views.get("btnnormalcancelpost").vw.getWidth())+(7d * scale))));
//BA.debugLineNum = 178;BA.debugLine="btnNormalSaveAndPrint.SetTopAndBottom(22%y, pnlNormalBox.Height - 10dip)"[mreading2/General script]
views.get("btnnormalsaveandprint").vw.setTop((int)((22d / 100 * height)));
views.get("btnnormalsaveandprint").vw.setHeight((int)((views.get("pnlnormalbox").vw.getHeight())-(10d * scale) - ((22d / 100 * height))));
//BA.debugLineNum = 179;BA.debugLine="btnNormalSaveAndPrint.SetLeftAndRight(btnNormalSaveOnly.Right + 7dip, pnlNormalBox.Width - 3%x)"[mreading2/General script]
views.get("btnnormalsaveandprint").vw.setLeft((int)((views.get("btnnormalsaveonly").vw.getLeft() + views.get("btnnormalsaveonly").vw.getWidth())+(7d * scale)));
views.get("btnnormalsaveandprint").vw.setWidth((int)((views.get("pnlnormalbox").vw.getWidth())-(3d / 100 * width) - ((views.get("btnnormalsaveonly").vw.getLeft() + views.get("btnnormalsaveonly").vw.getWidth())+(7d * scale))));
//BA.debugLineNum = 182;BA.debugLine="pnlHiMsgBox.Left = 0"[mreading2/General script]
views.get("pnlhimsgbox").vw.setLeft((int)(0d));
//BA.debugLineNum = 183;BA.debugLine="pnlHiMsgBox.Top = 0"[mreading2/General script]
views.get("pnlhimsgbox").vw.setTop((int)(0d));
//BA.debugLineNum = 184;BA.debugLine="pnlHiMsgBox.Width = 100%x"[mreading2/General script]
views.get("pnlhimsgbox").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 185;BA.debugLine="pnlHiMsgBox.Height = 100%y"[mreading2/General script]
views.get("pnlhimsgbox").vw.setHeight((int)((100d / 100 * height)));
//BA.debugLineNum = 187;BA.debugLine="pnlHiBox.SetLeftAndRight(3%x,pnlHiMsgBox.Width - 3%x)"[mreading2/General script]
views.get("pnlhibox").vw.setLeft((int)((3d / 100 * width)));
views.get("pnlhibox").vw.setWidth((int)((views.get("pnlhimsgbox").vw.getWidth())-(3d / 100 * width) - ((3d / 100 * width))));
//BA.debugLineNum = 188;BA.debugLine="pnlHiBox.Height = 37%y"[mreading2/General script]
views.get("pnlhibox").vw.setHeight((int)((37d / 100 * height)));
//BA.debugLineNum = 189;BA.debugLine="pnlHiBox.VerticalCenter = pnlHiMsgBox.Height / 2 - 7%Y"[mreading2/General script]
views.get("pnlhibox").vw.setTop((int)((views.get("pnlhimsgbox").vw.getHeight())/2d-(7d / 100 * height) - (views.get("pnlhibox").vw.getHeight() / 2)));
//BA.debugLineNum = 191;BA.debugLine="pnlHiTitle.SetTopAndBottom(0%y, 40dip)"[mreading2/General script]
views.get("pnlhititle").vw.setTop((int)((0d / 100 * height)));
views.get("pnlhititle").vw.setHeight((int)((40d * scale) - ((0d / 100 * height))));
//BA.debugLineNum = 192;BA.debugLine="pnlHiTitle.SetLeftAndRight(0%x, pnlHiBox.Width)"[mreading2/General script]
views.get("pnlhititle").vw.setLeft((int)((0d / 100 * width)));
views.get("pnlhititle").vw.setWidth((int)((views.get("pnlhibox").vw.getWidth()) - ((0d / 100 * width))));
//BA.debugLineNum = 193;BA.debugLine="lblIconHi.Left = 2%x"[mreading2/General script]
views.get("lbliconhi").vw.setLeft((int)((2d / 100 * width)));
//BA.debugLineNum = 194;BA.debugLine="lblIconHi.SetTopAndBottom(0.5%y, pnlHiTitle.Height - 0.5%y)"[mreading2/General script]
views.get("lbliconhi").vw.setTop((int)((0.5d / 100 * height)));
views.get("lbliconhi").vw.setHeight((int)((views.get("pnlhititle").vw.getHeight())-(0.5d / 100 * height) - ((0.5d / 100 * height))));
//BA.debugLineNum = 196;BA.debugLine="lblHiMsgTitle.SetLeftAndRight(lblIconHi.Right + 5dip, pnlHiTitle.Width)"[mreading2/General script]
views.get("lblhimsgtitle").vw.setLeft((int)((views.get("lbliconhi").vw.getLeft() + views.get("lbliconhi").vw.getWidth())+(5d * scale)));
views.get("lblhimsgtitle").vw.setWidth((int)((views.get("pnlhititle").vw.getWidth()) - ((views.get("lbliconhi").vw.getLeft() + views.get("lbliconhi").vw.getWidth())+(5d * scale))));
//BA.debugLineNum = 197;BA.debugLine="lblHiMsgTitle.VerticalCenter = lblIconHi.VerticalCenter"[mreading2/General script]
views.get("lblhimsgtitle").vw.setTop((int)((views.get("lbliconhi").vw.getTop() + views.get("lbliconhi").vw.getHeight()/2) - (views.get("lblhimsgtitle").vw.getHeight() / 2)));
//BA.debugLineNum = 199;BA.debugLine="lblHiMsgContent.SetLeftAndRight(5%x, pnlHiBox.Width - 2%x)"[mreading2/General script]
views.get("lblhimsgcontent").vw.setLeft((int)((5d / 100 * width)));
views.get("lblhimsgcontent").vw.setWidth((int)((views.get("pnlhibox").vw.getWidth())-(2d / 100 * width) - ((5d / 100 * width))));
//BA.debugLineNum = 200;BA.debugLine="lblHiMsgContent.Top = 8%y"[mreading2/General script]
views.get("lblhimsgcontent").vw.setTop((int)((8d / 100 * height)));
//BA.debugLineNum = 202;BA.debugLine="lblHiMsgContent2.SetLeftAndRight(5%x, pnlHiBox.Width - 3%x)"[mreading2/General script]
views.get("lblhimsgcontent2").vw.setLeft((int)((5d / 100 * width)));
views.get("lblhimsgcontent2").vw.setWidth((int)((views.get("pnlhibox").vw.getWidth())-(3d / 100 * width) - ((5d / 100 * width))));
//BA.debugLineNum = 203;BA.debugLine="lblHiMsgContent2.Top = lblHiMsgContent.Bottom + 5dip"[mreading2/General script]
views.get("lblhimsgcontent2").vw.setTop((int)((views.get("lblhimsgcontent").vw.getTop() + views.get("lblhimsgcontent").vw.getHeight())+(5d * scale)));
//BA.debugLineNum = 205;BA.debugLine="btnHiCancelPost.SetTopAndBottom(30%y, pnlHiBox.Height - 10dip)"[mreading2/General script]
views.get("btnhicancelpost").vw.setTop((int)((30d / 100 * height)));
views.get("btnhicancelpost").vw.setHeight((int)((views.get("pnlhibox").vw.getHeight())-(10d * scale) - ((30d / 100 * height))));
//BA.debugLineNum = 206;BA.debugLine="btnHiCancelPost.SetLeftAndRight(3%x, pnlHiBox.Width - 69%x)"[mreading2/General script]
views.get("btnhicancelpost").vw.setLeft((int)((3d / 100 * width)));
views.get("btnhicancelpost").vw.setWidth((int)((views.get("pnlhibox").vw.getWidth())-(69d / 100 * width) - ((3d / 100 * width))));
//BA.debugLineNum = 208;BA.debugLine="btnHiSaveOnly.SetTopAndBottom(30%y, pnlHiBox.Height - 10dip)"[mreading2/General script]
views.get("btnhisaveonly").vw.setTop((int)((30d / 100 * height)));
views.get("btnhisaveonly").vw.setHeight((int)((views.get("pnlhibox").vw.getHeight())-(10d * scale) - ((30d / 100 * height))));
//BA.debugLineNum = 209;BA.debugLine="btnHiSaveOnly.SetLeftAndRight(btnHiCancelPost.Right + 7dip, pnlHiBox.Width - 40%x)"[mreading2/General script]
views.get("btnhisaveonly").vw.setLeft((int)((views.get("btnhicancelpost").vw.getLeft() + views.get("btnhicancelpost").vw.getWidth())+(7d * scale)));
views.get("btnhisaveonly").vw.setWidth((int)((views.get("pnlhibox").vw.getWidth())-(40d / 100 * width) - ((views.get("btnhicancelpost").vw.getLeft() + views.get("btnhicancelpost").vw.getWidth())+(7d * scale))));
//BA.debugLineNum = 211;BA.debugLine="btnHiSaveAndPrint.SetTopAndBottom(30%y, pnlHiBox.Height - 10dip)"[mreading2/General script]
views.get("btnhisaveandprint").vw.setTop((int)((30d / 100 * height)));
views.get("btnhisaveandprint").vw.setHeight((int)((views.get("pnlhibox").vw.getHeight())-(10d * scale) - ((30d / 100 * height))));
//BA.debugLineNum = 212;BA.debugLine="btnHiSaveAndPrint.SetLeftAndRight(btnHiSaveOnly.Right + 7dip, pnlHiBox.Width - 3%x)"[mreading2/General script]
views.get("btnhisaveandprint").vw.setLeft((int)((views.get("btnhisaveonly").vw.getLeft() + views.get("btnhisaveonly").vw.getWidth())+(7d * scale)));
views.get("btnhisaveandprint").vw.setWidth((int)((views.get("pnlhibox").vw.getWidth())-(3d / 100 * width) - ((views.get("btnhisaveonly").vw.getLeft() + views.get("btnhisaveonly").vw.getWidth())+(7d * scale))));
//BA.debugLineNum = 215;BA.debugLine="pnlLowMsgBox.Left = 0"[mreading2/General script]
views.get("pnllowmsgbox").vw.setLeft((int)(0d));
//BA.debugLineNum = 216;BA.debugLine="pnlLowMsgBox.Top = 0"[mreading2/General script]
views.get("pnllowmsgbox").vw.setTop((int)(0d));
//BA.debugLineNum = 217;BA.debugLine="pnlLowMsgBox.Width = 100%x"[mreading2/General script]
views.get("pnllowmsgbox").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 218;BA.debugLine="pnlLowMsgBox.Height = 100%y"[mreading2/General script]
views.get("pnllowmsgbox").vw.setHeight((int)((100d / 100 * height)));
//BA.debugLineNum = 220;BA.debugLine="pnlLowBox.SetLeftAndRight(3%x,pnlLowMsgBox.Width - 3%x)"[mreading2/General script]
views.get("pnllowbox").vw.setLeft((int)((3d / 100 * width)));
views.get("pnllowbox").vw.setWidth((int)((views.get("pnllowmsgbox").vw.getWidth())-(3d / 100 * width) - ((3d / 100 * width))));
//BA.debugLineNum = 221;BA.debugLine="pnlLowBox.Height = 37%y"[mreading2/General script]
views.get("pnllowbox").vw.setHeight((int)((37d / 100 * height)));
//BA.debugLineNum = 222;BA.debugLine="pnlLowBox.VerticalCenter = pnlLowMsgBox.Height / 2 - 7%Y"[mreading2/General script]
views.get("pnllowbox").vw.setTop((int)((views.get("pnllowmsgbox").vw.getHeight())/2d-(7d / 100 * height) - (views.get("pnllowbox").vw.getHeight() / 2)));
//BA.debugLineNum = 224;BA.debugLine="pnlLowTitle.SetTopAndBottom(0%y, 40dip)"[mreading2/General script]
views.get("pnllowtitle").vw.setTop((int)((0d / 100 * height)));
views.get("pnllowtitle").vw.setHeight((int)((40d * scale) - ((0d / 100 * height))));
//BA.debugLineNum = 225;BA.debugLine="pnlLowTitle.SetLeftAndRight(0%x, pnlLowBox.Width)"[mreading2/General script]
views.get("pnllowtitle").vw.setLeft((int)((0d / 100 * width)));
views.get("pnllowtitle").vw.setWidth((int)((views.get("pnllowbox").vw.getWidth()) - ((0d / 100 * width))));
//BA.debugLineNum = 226;BA.debugLine="lblIconLow.Left = 2%x"[mreading2/General script]
views.get("lbliconlow").vw.setLeft((int)((2d / 100 * width)));
//BA.debugLineNum = 227;BA.debugLine="lblIconLow.SetTopAndBottom(0.5%y, pnlLowTitle.Height - 0.5%y)"[mreading2/General script]
views.get("lbliconlow").vw.setTop((int)((0.5d / 100 * height)));
views.get("lbliconlow").vw.setHeight((int)((views.get("pnllowtitle").vw.getHeight())-(0.5d / 100 * height) - ((0.5d / 100 * height))));
//BA.debugLineNum = 229;BA.debugLine="lblLowMsgTitle.SetLeftAndRight(lblIconLow.Right + 5dip, pnlLowTitle.Width)"[mreading2/General script]
views.get("lbllowmsgtitle").vw.setLeft((int)((views.get("lbliconlow").vw.getLeft() + views.get("lbliconlow").vw.getWidth())+(5d * scale)));
views.get("lbllowmsgtitle").vw.setWidth((int)((views.get("pnllowtitle").vw.getWidth()) - ((views.get("lbliconlow").vw.getLeft() + views.get("lbliconlow").vw.getWidth())+(5d * scale))));
//BA.debugLineNum = 230;BA.debugLine="lblLowMsgTitle.VerticalCenter = lblIconLow.VerticalCenter"[mreading2/General script]
views.get("lbllowmsgtitle").vw.setTop((int)((views.get("lbliconlow").vw.getTop() + views.get("lbliconlow").vw.getHeight()/2) - (views.get("lbllowmsgtitle").vw.getHeight() / 2)));
//BA.debugLineNum = 232;BA.debugLine="lblLowMsgContent.SetLeftAndRight(5%x, pnlLowBox.Width - 2%x)"[mreading2/General script]
views.get("lbllowmsgcontent").vw.setLeft((int)((5d / 100 * width)));
views.get("lbllowmsgcontent").vw.setWidth((int)((views.get("pnllowbox").vw.getWidth())-(2d / 100 * width) - ((5d / 100 * width))));
//BA.debugLineNum = 233;BA.debugLine="lblLowMsgContent.Top = 8%y"[mreading2/General script]
views.get("lbllowmsgcontent").vw.setTop((int)((8d / 100 * height)));
//BA.debugLineNum = 235;BA.debugLine="lblLowMsgContent2.SetLeftAndRight(5%x, pnlLowBox.Width - 4%x)"[mreading2/General script]
views.get("lbllowmsgcontent2").vw.setLeft((int)((5d / 100 * width)));
views.get("lbllowmsgcontent2").vw.setWidth((int)((views.get("pnllowbox").vw.getWidth())-(4d / 100 * width) - ((5d / 100 * width))));
//BA.debugLineNum = 236;BA.debugLine="lblLowMsgContent2.Top = lblLowMsgContent.Bottom + 5dip"[mreading2/General script]
views.get("lbllowmsgcontent2").vw.setTop((int)((views.get("lbllowmsgcontent").vw.getTop() + views.get("lbllowmsgcontent").vw.getHeight())+(5d * scale)));
//BA.debugLineNum = 238;BA.debugLine="btnLowCancelPost.SetTopAndBottom(30%y, pnlLowBox.Height - 10dip)"[mreading2/General script]
views.get("btnlowcancelpost").vw.setTop((int)((30d / 100 * height)));
views.get("btnlowcancelpost").vw.setHeight((int)((views.get("pnllowbox").vw.getHeight())-(10d * scale) - ((30d / 100 * height))));
//BA.debugLineNum = 239;BA.debugLine="btnLowCancelPost.SetLeftAndRight(3%x, pnlLowBox.Width - 69%x)"[mreading2/General script]
views.get("btnlowcancelpost").vw.setLeft((int)((3d / 100 * width)));
views.get("btnlowcancelpost").vw.setWidth((int)((views.get("pnllowbox").vw.getWidth())-(69d / 100 * width) - ((3d / 100 * width))));
//BA.debugLineNum = 241;BA.debugLine="btnLowSaveOnly.SetTopAndBottom(30%y, pnlLowBox.Height - 10dip)"[mreading2/General script]
views.get("btnlowsaveonly").vw.setTop((int)((30d / 100 * height)));
views.get("btnlowsaveonly").vw.setHeight((int)((views.get("pnllowbox").vw.getHeight())-(10d * scale) - ((30d / 100 * height))));
//BA.debugLineNum = 242;BA.debugLine="btnLowSaveOnly.SetLeftAndRight(btnLowCancelPost.Right + 7dip, pnlLowBox.Width - 40%x)"[mreading2/General script]
views.get("btnlowsaveonly").vw.setLeft((int)((views.get("btnlowcancelpost").vw.getLeft() + views.get("btnlowcancelpost").vw.getWidth())+(7d * scale)));
views.get("btnlowsaveonly").vw.setWidth((int)((views.get("pnllowbox").vw.getWidth())-(40d / 100 * width) - ((views.get("btnlowcancelpost").vw.getLeft() + views.get("btnlowcancelpost").vw.getWidth())+(7d * scale))));
//BA.debugLineNum = 244;BA.debugLine="btnLowSaveAndPrint.SetTopAndBottom(30%y, pnlLowBox.Height - 10dip)"[mreading2/General script]
views.get("btnlowsaveandprint").vw.setTop((int)((30d / 100 * height)));
views.get("btnlowsaveandprint").vw.setHeight((int)((views.get("pnllowbox").vw.getHeight())-(10d * scale) - ((30d / 100 * height))));
//BA.debugLineNum = 245;BA.debugLine="btnLowSaveAndPrint.SetLeftAndRight(btnLowSaveOnly.Right + 7dip, pnlLowBox.Width - 3%x)"[mreading2/General script]
views.get("btnlowsaveandprint").vw.setLeft((int)((views.get("btnlowsaveonly").vw.getLeft() + views.get("btnlowsaveonly").vw.getWidth())+(7d * scale)));
views.get("btnlowsaveandprint").vw.setWidth((int)((views.get("pnllowbox").vw.getWidth())-(3d / 100 * width) - ((views.get("btnlowsaveonly").vw.getLeft() + views.get("btnlowsaveonly").vw.getWidth())+(7d * scale))));
//BA.debugLineNum = 248;BA.debugLine="pnlZeroMsgBox.Left = 0"[mreading2/General script]
views.get("pnlzeromsgbox").vw.setLeft((int)(0d));
//BA.debugLineNum = 249;BA.debugLine="pnlZeroMsgBox.Top = 0"[mreading2/General script]
views.get("pnlzeromsgbox").vw.setTop((int)(0d));
//BA.debugLineNum = 250;BA.debugLine="pnlZeroMsgBox.Width = 100%x"[mreading2/General script]
views.get("pnlzeromsgbox").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 251;BA.debugLine="pnlZeroMsgBox.Height = 100%y"[mreading2/General script]
views.get("pnlzeromsgbox").vw.setHeight((int)((100d / 100 * height)));
//BA.debugLineNum = 253;BA.debugLine="pnlZeroBox.SetLeftAndRight(3%x,pnlZeroMsgBox.Width - 3%x)"[mreading2/General script]
views.get("pnlzerobox").vw.setLeft((int)((3d / 100 * width)));
views.get("pnlzerobox").vw.setWidth((int)((views.get("pnlzeromsgbox").vw.getWidth())-(3d / 100 * width) - ((3d / 100 * width))));
//BA.debugLineNum = 254;BA.debugLine="pnlZeroBox.Height = 40%y"[mreading2/General script]
views.get("pnlzerobox").vw.setHeight((int)((40d / 100 * height)));
//BA.debugLineNum = 255;BA.debugLine="pnlZeroBox.VerticalCenter = pnlZeroMsgBox.Height / 2 - 10%y"[mreading2/General script]
views.get("pnlzerobox").vw.setTop((int)((views.get("pnlzeromsgbox").vw.getHeight())/2d-(10d / 100 * height) - (views.get("pnlzerobox").vw.getHeight() / 2)));
//BA.debugLineNum = 257;BA.debugLine="pnlZeroTitle.SetTopAndBottom(0%y,40dip)"[mreading2/General script]
views.get("pnlzerotitle").vw.setTop((int)((0d / 100 * height)));
views.get("pnlzerotitle").vw.setHeight((int)((40d * scale) - ((0d / 100 * height))));
//BA.debugLineNum = 258;BA.debugLine="pnlZeroTitle.SetLeftAndRight(0%x,pnlZeroBox.Width)"[mreading2/General script]
views.get("pnlzerotitle").vw.setLeft((int)((0d / 100 * width)));
views.get("pnlzerotitle").vw.setWidth((int)((views.get("pnlzerobox").vw.getWidth()) - ((0d / 100 * width))));
//BA.debugLineNum = 260;BA.debugLine="lblIconZero.Left = 2%x"[mreading2/General script]
views.get("lbliconzero").vw.setLeft((int)((2d / 100 * width)));
//BA.debugLineNum = 261;BA.debugLine="lblIconZero.SetTopAndBottom(0.5%y, pnlZeroTitle.Height - 0.5%y)"[mreading2/General script]
views.get("lbliconzero").vw.setTop((int)((0.5d / 100 * height)));
views.get("lbliconzero").vw.setHeight((int)((views.get("pnlzerotitle").vw.getHeight())-(0.5d / 100 * height) - ((0.5d / 100 * height))));
//BA.debugLineNum = 263;BA.debugLine="lblZeroMsgTitle.SetLeftAndRight(lblIconZero.Right + 5dip, pnlZeroTitle.Width - 5dip)"[mreading2/General script]
views.get("lblzeromsgtitle").vw.setLeft((int)((views.get("lbliconzero").vw.getLeft() + views.get("lbliconzero").vw.getWidth())+(5d * scale)));
views.get("lblzeromsgtitle").vw.setWidth((int)((views.get("pnlzerotitle").vw.getWidth())-(5d * scale) - ((views.get("lbliconzero").vw.getLeft() + views.get("lbliconzero").vw.getWidth())+(5d * scale))));
//BA.debugLineNum = 264;BA.debugLine="lblZeroMsgTitle.VerticalCenter = lblIconZero.VerticalCenter"[mreading2/General script]
views.get("lblzeromsgtitle").vw.setTop((int)((views.get("lbliconzero").vw.getTop() + views.get("lbliconzero").vw.getHeight()/2) - (views.get("lblzeromsgtitle").vw.getHeight() / 2)));
//BA.debugLineNum = 266;BA.debugLine="lblZeroMsgContent.SetLeftAndRight(5%x, pnlZeroBox.Width - 2%x)"[mreading2/General script]
views.get("lblzeromsgcontent").vw.setLeft((int)((5d / 100 * width)));
views.get("lblzeromsgcontent").vw.setWidth((int)((views.get("pnlzerobox").vw.getWidth())-(2d / 100 * width) - ((5d / 100 * width))));
//BA.debugLineNum = 267;BA.debugLine="lblZeroMsgContent.Top = pnlZeroTitle.Bottom + 15dip"[mreading2/General script]
views.get("lblzeromsgcontent").vw.setTop((int)((views.get("pnlzerotitle").vw.getTop() + views.get("pnlzerotitle").vw.getHeight())+(15d * scale)));
//BA.debugLineNum = 269;BA.debugLine="lblZeroMsgContent2.SetLeftAndRight(5%x, pnlZeroBox.Width - 4%x)"[mreading2/General script]
views.get("lblzeromsgcontent2").vw.setLeft((int)((5d / 100 * width)));
views.get("lblzeromsgcontent2").vw.setWidth((int)((views.get("pnlzerobox").vw.getWidth())-(4d / 100 * width) - ((5d / 100 * width))));
//BA.debugLineNum = 270;BA.debugLine="lblZeroMsgContent2.Top = lblZeroMsgContent.Bottom + 5dip"[mreading2/General script]
views.get("lblzeromsgcontent2").vw.setTop((int)((views.get("lblzeromsgcontent").vw.getTop() + views.get("lblzeromsgcontent").vw.getHeight())+(5d * scale)));
//BA.debugLineNum = 272;BA.debugLine="pnlRemarksAnchor.SetLeftAndRight(3%x, pnlZeroBox.Width - 3%x)"[mreading2/General script]
views.get("pnlremarksanchor").vw.setLeft((int)((3d / 100 * width)));
views.get("pnlremarksanchor").vw.setWidth((int)((views.get("pnlzerobox").vw.getWidth())-(3d / 100 * width) - ((3d / 100 * width))));
//BA.debugLineNum = 273;BA.debugLine="pnlRemarksAnchor.Top = lblZeroMsgContent2.Bottom + 5dip"[mreading2/General script]
views.get("pnlremarksanchor").vw.setTop((int)((views.get("lblzeromsgcontent2").vw.getTop() + views.get("lblzeromsgcontent2").vw.getHeight())+(5d * scale)));
//BA.debugLineNum = 275;BA.debugLine="txtRemarks.SetLeftAndRight(2%x, pnlRemarksAnchor.Width - 2%x)"[mreading2/General script]
views.get("txtremarks").vw.setLeft((int)((2d / 100 * width)));
views.get("txtremarks").vw.setWidth((int)((views.get("pnlremarksanchor").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
//BA.debugLineNum = 276;BA.debugLine="txtRemarks.SetTopAndBottom(0.25%y, pnlRemarksAnchor.Height - 0.25%y)"[mreading2/General script]
views.get("txtremarks").vw.setTop((int)((0.25d / 100 * height)));
views.get("txtremarks").vw.setHeight((int)((views.get("pnlremarksanchor").vw.getHeight())-(0.25d / 100 * height) - ((0.25d / 100 * height))));
//BA.debugLineNum = 278;BA.debugLine="btnZeroCancelPost.SetTopAndBottom(30%y, pnlZeroBox.Height - 10dip)"[mreading2/General script]
views.get("btnzerocancelpost").vw.setTop((int)((30d / 100 * height)));
views.get("btnzerocancelpost").vw.setHeight((int)((views.get("pnlzerobox").vw.getHeight())-(10d * scale) - ((30d / 100 * height))));
//BA.debugLineNum = 279;BA.debugLine="btnZeroCancelPost.SetLeftAndRight(3%x, pnlZeroBox.Width - 69%x)"[mreading2/General script]
views.get("btnzerocancelpost").vw.setLeft((int)((3d / 100 * width)));
views.get("btnzerocancelpost").vw.setWidth((int)((views.get("pnlzerobox").vw.getWidth())-(69d / 100 * width) - ((3d / 100 * width))));
//BA.debugLineNum = 281;BA.debugLine="btnZeroSaveOnly.SetTopAndBottom(30%y, pnlZeroBox.Height - 10dip)"[mreading2/General script]
views.get("btnzerosaveonly").vw.setTop((int)((30d / 100 * height)));
views.get("btnzerosaveonly").vw.setHeight((int)((views.get("pnlzerobox").vw.getHeight())-(10d * scale) - ((30d / 100 * height))));
//BA.debugLineNum = 282;BA.debugLine="btnZeroSaveOnly.SetLeftAndRight(btnZeroCancelPost.Right + 7dip, pnlZeroBox.Width - 40%x)"[mreading2/General script]
views.get("btnzerosaveonly").vw.setLeft((int)((views.get("btnzerocancelpost").vw.getLeft() + views.get("btnzerocancelpost").vw.getWidth())+(7d * scale)));
views.get("btnzerosaveonly").vw.setWidth((int)((views.get("pnlzerobox").vw.getWidth())-(40d / 100 * width) - ((views.get("btnzerocancelpost").vw.getLeft() + views.get("btnzerocancelpost").vw.getWidth())+(7d * scale))));
//BA.debugLineNum = 284;BA.debugLine="btnZeroSaveAndPrint.SetTopAndBottom(30%y, pnlZeroBox.Height - 10dip)"[mreading2/General script]
views.get("btnzerosaveandprint").vw.setTop((int)((30d / 100 * height)));
views.get("btnzerosaveandprint").vw.setHeight((int)((views.get("pnlzerobox").vw.getHeight())-(10d * scale) - ((30d / 100 * height))));
//BA.debugLineNum = 285;BA.debugLine="btnZeroSaveAndPrint.SetLeftAndRight(btnZeroSaveOnly.Right + 7dip, pnlZeroBox.Width - 3%x)"[mreading2/General script]
views.get("btnzerosaveandprint").vw.setLeft((int)((views.get("btnzerosaveonly").vw.getLeft() + views.get("btnzerosaveonly").vw.getWidth())+(7d * scale)));
views.get("btnzerosaveandprint").vw.setWidth((int)((views.get("pnlzerobox").vw.getWidth())-(3d / 100 * width) - ((views.get("btnzerosaveonly").vw.getLeft() + views.get("btnzerosaveonly").vw.getWidth())+(7d * scale))));
//BA.debugLineNum = 288;BA.debugLine="pnlNegativeMsgBox.Left = 0"[mreading2/General script]
views.get("pnlnegativemsgbox").vw.setLeft((int)(0d));
//BA.debugLineNum = 289;BA.debugLine="pnlNegativeMsgBox.Top = 0"[mreading2/General script]
views.get("pnlnegativemsgbox").vw.setTop((int)(0d));
//BA.debugLineNum = 290;BA.debugLine="pnlNegativeMsgBox.Width = 100%x"[mreading2/General script]
views.get("pnlnegativemsgbox").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 291;BA.debugLine="pnlNegativeMsgBox.Height = 100%y"[mreading2/General script]
views.get("pnlnegativemsgbox").vw.setHeight((int)((100d / 100 * height)));
//BA.debugLineNum = 293;BA.debugLine="pnlNegativeBox.SetLeftAndRight(3%x,pnlNegativeMsgBox.Width - 3%x)"[mreading2/General script]
views.get("pnlnegativebox").vw.setLeft((int)((3d / 100 * width)));
views.get("pnlnegativebox").vw.setWidth((int)((views.get("pnlnegativemsgbox").vw.getWidth())-(3d / 100 * width) - ((3d / 100 * width))));
//BA.debugLineNum = 294;BA.debugLine="pnlNegativeBox.Height = 93%y"[mreading2/General script]
views.get("pnlnegativebox").vw.setHeight((int)((93d / 100 * height)));
//BA.debugLineNum = 295;BA.debugLine="pnlNegativeBox.VerticalCenter = pnlNegativeMsgBox.Height / 2"[mreading2/General script]
views.get("pnlnegativebox").vw.setTop((int)((views.get("pnlnegativemsgbox").vw.getHeight())/2d - (views.get("pnlnegativebox").vw.getHeight() / 2)));
//BA.debugLineNum = 297;BA.debugLine="pnlNegativeTitle.SetTopAndBottom(0%y, 40dip)"[mreading2/General script]
views.get("pnlnegativetitle").vw.setTop((int)((0d / 100 * height)));
views.get("pnlnegativetitle").vw.setHeight((int)((40d * scale) - ((0d / 100 * height))));
//BA.debugLineNum = 298;BA.debugLine="pnlNegativeTitle.SetLeftAndRight(0%x, pnlNegativeBox.Width)"[mreading2/General script]
views.get("pnlnegativetitle").vw.setLeft((int)((0d / 100 * width)));
views.get("pnlnegativetitle").vw.setWidth((int)((views.get("pnlnegativebox").vw.getWidth()) - ((0d / 100 * width))));
//BA.debugLineNum = 299;BA.debugLine="lblIconNegative.Left = 2%x"[mreading2/General script]
views.get("lbliconnegative").vw.setLeft((int)((2d / 100 * width)));
//BA.debugLineNum = 300;BA.debugLine="lblIconNegative.SetTopAndBottom(0.5%y, pnlLowTitle.Height - 0.5%y)"[mreading2/General script]
views.get("lbliconnegative").vw.setTop((int)((0.5d / 100 * height)));
views.get("lbliconnegative").vw.setHeight((int)((views.get("pnllowtitle").vw.getHeight())-(0.5d / 100 * height) - ((0.5d / 100 * height))));
//BA.debugLineNum = 302;BA.debugLine="lblNegativeMsgTitle.SetLeftAndRight(lblIconNegative.Right + 5dip, pnlNegativeTitle.Width)"[mreading2/General script]
views.get("lblnegativemsgtitle").vw.setLeft((int)((views.get("lbliconnegative").vw.getLeft() + views.get("lbliconnegative").vw.getWidth())+(5d * scale)));
views.get("lblnegativemsgtitle").vw.setWidth((int)((views.get("pnlnegativetitle").vw.getWidth()) - ((views.get("lbliconnegative").vw.getLeft() + views.get("lbliconnegative").vw.getWidth())+(5d * scale))));
//BA.debugLineNum = 303;BA.debugLine="lblNegativeMsgTitle.VerticalCenter = lblIconNegative.VerticalCenter"[mreading2/General script]
views.get("lblnegativemsgtitle").vw.setTop((int)((views.get("lbliconnegative").vw.getTop() + views.get("lbliconnegative").vw.getHeight()/2) - (views.get("lblnegativemsgtitle").vw.getHeight() / 2)));
//BA.debugLineNum = 305;BA.debugLine="lblNegativeMsgContent.SetLeftAndRight(3%x, pnlNegativeBox.Width - 1%x)"[mreading2/General script]
views.get("lblnegativemsgcontent").vw.setLeft((int)((3d / 100 * width)));
views.get("lblnegativemsgcontent").vw.setWidth((int)((views.get("pnlnegativebox").vw.getWidth())-(1d / 100 * width) - ((3d / 100 * width))));
//BA.debugLineNum = 306;BA.debugLine="lblNegativeMsgContent.Top = 7%y"[mreading2/General script]
views.get("lblnegativemsgcontent").vw.setTop((int)((7d / 100 * height)));
//BA.debugLineNum = 308;BA.debugLine="lblNegativeMsgContent2.SetLeftAndRight(5%x, pnlNegativeBox.Width - 4%x)"[mreading2/General script]
views.get("lblnegativemsgcontent2").vw.setLeft((int)((5d / 100 * width)));
views.get("lblnegativemsgcontent2").vw.setWidth((int)((views.get("pnlnegativebox").vw.getWidth())-(4d / 100 * width) - ((5d / 100 * width))));
//BA.debugLineNum = 309;BA.debugLine="lblNegativeMsgContent2.Top = lblNegativeMsgContent.Bottom + 5dip"[mreading2/General script]
views.get("lblnegativemsgcontent2").vw.setTop((int)((views.get("lblnegativemsgcontent").vw.getTop() + views.get("lblnegativemsgcontent").vw.getHeight())+(5d * scale)));
//BA.debugLineNum = 311;BA.debugLine="opt0.Top = lblNegativeMsgContent2.Bottom + 10dip"[mreading2/General script]
views.get("opt0").vw.setTop((int)((views.get("lblnegativemsgcontent2").vw.getTop() + views.get("lblnegativemsgcontent2").vw.getHeight())+(10d * scale)));
//BA.debugLineNum = 312;BA.debugLine="opt0.SetLeftAndRight(8%x, pnlNegativeBox.Width - 1%x)"[mreading2/General script]
views.get("opt0").vw.setLeft((int)((8d / 100 * width)));
views.get("opt0").vw.setWidth((int)((views.get("pnlnegativebox").vw.getWidth())-(1d / 100 * width) - ((8d / 100 * width))));
//BA.debugLineNum = 314;BA.debugLine="opt1.Top = opt0.Bottom + 4dip"[mreading2/General script]
views.get("opt1").vw.setTop((int)((views.get("opt0").vw.getTop() + views.get("opt0").vw.getHeight())+(4d * scale)));
//BA.debugLineNum = 315;BA.debugLine="opt1.SetLeftAndRight(8%x, pnlNegativeBox.Width - 1%x)"[mreading2/General script]
views.get("opt1").vw.setLeft((int)((8d / 100 * width)));
views.get("opt1").vw.setWidth((int)((views.get("pnlnegativebox").vw.getWidth())-(1d / 100 * width) - ((8d / 100 * width))));
//BA.debugLineNum = 317;BA.debugLine="opt2.Top = opt1.Bottom + 4dip"[mreading2/General script]
views.get("opt2").vw.setTop((int)((views.get("opt1").vw.getTop() + views.get("opt1").vw.getHeight())+(4d * scale)));
//BA.debugLineNum = 318;BA.debugLine="opt2.SetLeftAndRight(8%x, pnlNegativeBox.Width - 1%x)"[mreading2/General script]
views.get("opt2").vw.setLeft((int)((8d / 100 * width)));
views.get("opt2").vw.setWidth((int)((views.get("pnlnegativebox").vw.getWidth())-(1d / 100 * width) - ((8d / 100 * width))));
//BA.debugLineNum = 320;BA.debugLine="opt3.Top = opt2.Bottom + 4dip"[mreading2/General script]
views.get("opt3").vw.setTop((int)((views.get("opt2").vw.getTop() + views.get("opt2").vw.getHeight())+(4d * scale)));
//BA.debugLineNum = 321;BA.debugLine="opt3.SetLeftAndRight(8%x, pnlNegativeBox.Width - 1%x)"[mreading2/General script]
views.get("opt3").vw.setLeft((int)((8d / 100 * width)));
views.get("opt3").vw.setWidth((int)((views.get("pnlnegativebox").vw.getWidth())-(1d / 100 * width) - ((8d / 100 * width))));
//BA.debugLineNum = 323;BA.debugLine="opt4.Top = opt3.Bottom + 4dip"[mreading2/General script]
views.get("opt4").vw.setTop((int)((views.get("opt3").vw.getTop() + views.get("opt3").vw.getHeight())+(4d * scale)));
//BA.debugLineNum = 324;BA.debugLine="opt4.SetLeftAndRight(8%x, pnlNegativeBox.Width - 1%x)"[mreading2/General script]
views.get("opt4").vw.setLeft((int)((8d / 100 * width)));
views.get("opt4").vw.setWidth((int)((views.get("pnlnegativebox").vw.getWidth())-(1d / 100 * width) - ((8d / 100 * width))));
//BA.debugLineNum = 326;BA.debugLine="opt1.Top = opt0.Bottom + 4dip"[mreading2/General script]
views.get("opt1").vw.setTop((int)((views.get("opt0").vw.getTop() + views.get("opt0").vw.getHeight())+(4d * scale)));
//BA.debugLineNum = 327;BA.debugLine="opt1.SetLeftAndRight(8%x, pnlNegativeBox.Width - 1%x)"[mreading2/General script]
views.get("opt1").vw.setLeft((int)((8d / 100 * width)));
views.get("opt1").vw.setWidth((int)((views.get("pnlnegativebox").vw.getWidth())-(1d / 100 * width) - ((8d / 100 * width))));
//BA.debugLineNum = 329;BA.debugLine="opt5.Top = opt4.Bottom + 4dip"[mreading2/General script]
views.get("opt5").vw.setTop((int)((views.get("opt4").vw.getTop() + views.get("opt4").vw.getHeight())+(4d * scale)));
//BA.debugLineNum = 330;BA.debugLine="opt5.SetLeftAndRight(8%x, pnlNegativeBox.Width - 1%x)"[mreading2/General script]
views.get("opt5").vw.setLeft((int)((8d / 100 * width)));
views.get("opt5").vw.setWidth((int)((views.get("pnlnegativebox").vw.getWidth())-(1d / 100 * width) - ((8d / 100 * width))));
//BA.debugLineNum = 332;BA.debugLine="opt6.Top = opt5.Bottom + 4dip"[mreading2/General script]
views.get("opt6").vw.setTop((int)((views.get("opt5").vw.getTop() + views.get("opt5").vw.getHeight())+(4d * scale)));
//BA.debugLineNum = 333;BA.debugLine="opt6.SetLeftAndRight(8%x, pnlNegativeBox.Width - 1%x)"[mreading2/General script]
views.get("opt6").vw.setLeft((int)((8d / 100 * width)));
views.get("opt6").vw.setWidth((int)((views.get("pnlnegativebox").vw.getWidth())-(1d / 100 * width) - ((8d / 100 * width))));
//BA.debugLineNum = 335;BA.debugLine="opt7.Top = opt6.Bottom + 4dip"[mreading2/General script]
views.get("opt7").vw.setTop((int)((views.get("opt6").vw.getTop() + views.get("opt6").vw.getHeight())+(4d * scale)));
//BA.debugLineNum = 336;BA.debugLine="opt7.SetLeftAndRight(8%x, pnlNegativeBox.Width - 1%x)"[mreading2/General script]
views.get("opt7").vw.setLeft((int)((8d / 100 * width)));
views.get("opt7").vw.setWidth((int)((views.get("pnlnegativebox").vw.getWidth())-(1d / 100 * width) - ((8d / 100 * width))));
//BA.debugLineNum = 338;BA.debugLine="opt8.Top = opt7.Bottom + 4dip"[mreading2/General script]
views.get("opt8").vw.setTop((int)((views.get("opt7").vw.getTop() + views.get("opt7").vw.getHeight())+(4d * scale)));
//BA.debugLineNum = 339;BA.debugLine="opt8.SetLeftAndRight(8%x, pnlNegativeBox.Width - 1%x)"[mreading2/General script]
views.get("opt8").vw.setLeft((int)((8d / 100 * width)));
views.get("opt8").vw.setWidth((int)((views.get("pnlnegativebox").vw.getWidth())-(1d / 100 * width) - ((8d / 100 * width))));
//BA.debugLineNum = 341;BA.debugLine="pnlOthersAnchor.Top = opt8.Bottom + 3dip"[mreading2/General script]
views.get("pnlothersanchor").vw.setTop((int)((views.get("opt8").vw.getTop() + views.get("opt8").vw.getHeight())+(3d * scale)));
//BA.debugLineNum = 342;BA.debugLine="pnlOthersAnchor.SetLeftAndRight(8%x, pnlNegativeBox.Width - 3%x)"[mreading2/General script]
views.get("pnlothersanchor").vw.setLeft((int)((8d / 100 * width)));
views.get("pnlothersanchor").vw.setWidth((int)((views.get("pnlnegativebox").vw.getWidth())-(3d / 100 * width) - ((8d / 100 * width))));
//BA.debugLineNum = 344;BA.debugLine="txtOthers.SetLeftAndRight(2%x, pnlOthersAnchor.Width - 1%x)"[mreading2/General script]
views.get("txtothers").vw.setLeft((int)((2d / 100 * width)));
views.get("txtothers").vw.setWidth((int)((views.get("pnlothersanchor").vw.getWidth())-(1d / 100 * width) - ((2d / 100 * width))));
//BA.debugLineNum = 345;BA.debugLine="txtOthers.SetTopAndBottom(0.25%y, pnlOthersAnchor.Height - 0.25%y)"[mreading2/General script]
views.get("txtothers").vw.setTop((int)((0.25d / 100 * height)));
views.get("txtothers").vw.setHeight((int)((views.get("pnlothersanchor").vw.getHeight())-(0.25d / 100 * height) - ((0.25d / 100 * height))));
//BA.debugLineNum = 347;BA.debugLine="btnNegativeCancelPost.SetTopAndBottom(85%y, pnlNegativeBox.Height - 10dip)"[mreading2/General script]
views.get("btnnegativecancelpost").vw.setTop((int)((85d / 100 * height)));
views.get("btnnegativecancelpost").vw.setHeight((int)((views.get("pnlnegativebox").vw.getHeight())-(10d * scale) - ((85d / 100 * height))));
//BA.debugLineNum = 348;BA.debugLine="btnNegativeCancelPost.SetLeftAndRight(3%x, pnlNegativeBox.Width - 69%x)"[mreading2/General script]
views.get("btnnegativecancelpost").vw.setLeft((int)((3d / 100 * width)));
views.get("btnnegativecancelpost").vw.setWidth((int)((views.get("pnlnegativebox").vw.getWidth())-(69d / 100 * width) - ((3d / 100 * width))));
//BA.debugLineNum = 350;BA.debugLine="btnNegativeSaveOnly.SetTopAndBottom(85%y, pnlNegativeBox.Height - 10dip)"[mreading2/General script]
views.get("btnnegativesaveonly").vw.setTop((int)((85d / 100 * height)));
views.get("btnnegativesaveonly").vw.setHeight((int)((views.get("pnlnegativebox").vw.getHeight())-(10d * scale) - ((85d / 100 * height))));
//BA.debugLineNum = 351;BA.debugLine="btnNegativeSaveOnly.SetLeftAndRight(btnNegativeCancelPost.Right + 7dip, pnlNegativeBox.Width - 42%x)"[mreading2/General script]
views.get("btnnegativesaveonly").vw.setLeft((int)((views.get("btnnegativecancelpost").vw.getLeft() + views.get("btnnegativecancelpost").vw.getWidth())+(7d * scale)));
views.get("btnnegativesaveonly").vw.setWidth((int)((views.get("pnlnegativebox").vw.getWidth())-(42d / 100 * width) - ((views.get("btnnegativecancelpost").vw.getLeft() + views.get("btnnegativecancelpost").vw.getWidth())+(7d * scale))));
//BA.debugLineNum = 353;BA.debugLine="btnNegativeSelect.SetTopAndBottom(85%y, pnlNegativeBox.Height - 10dip)"[mreading2/General script]
views.get("btnnegativeselect").vw.setTop((int)((85d / 100 * height)));
views.get("btnnegativeselect").vw.setHeight((int)((views.get("pnlnegativebox").vw.getHeight())-(10d * scale) - ((85d / 100 * height))));
//BA.debugLineNum = 354;BA.debugLine="btnNegativeSelect.SetLeftAndRight(pnlNegativeBox.Width - 30%x, pnlNegativeBox.Width - 3%x)"[mreading2/General script]
views.get("btnnegativeselect").vw.setLeft((int)((views.get("pnlnegativebox").vw.getWidth())-(30d / 100 * width)));
views.get("btnnegativeselect").vw.setWidth((int)((views.get("pnlnegativebox").vw.getWidth())-(3d / 100 * width) - ((views.get("pnlnegativebox").vw.getWidth())-(30d / 100 * width))));
//BA.debugLineNum = 357;BA.debugLine="pnlHighBillConfirmation.Left = 0"[mreading2/General script]
views.get("pnlhighbillconfirmation").vw.setLeft((int)(0d));
//BA.debugLineNum = 358;BA.debugLine="pnlHighBillConfirmation.Top = 0"[mreading2/General script]
views.get("pnlhighbillconfirmation").vw.setTop((int)(0d));
//BA.debugLineNum = 359;BA.debugLine="pnlHighBillConfirmation.Width = 100%x"[mreading2/General script]
views.get("pnlhighbillconfirmation").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 360;BA.debugLine="pnlHighBillConfirmation.Height = 100%y"[mreading2/General script]
views.get("pnlhighbillconfirmation").vw.setHeight((int)((100d / 100 * height)));
//BA.debugLineNum = 362;BA.debugLine="pnlHBConfirm.SetLeftAndRight(3%x,pnlHighBillConfirmation.Width - 3%x)"[mreading2/General script]
views.get("pnlhbconfirm").vw.setLeft((int)((3d / 100 * width)));
views.get("pnlhbconfirm").vw.setWidth((int)((views.get("pnlhighbillconfirmation").vw.getWidth())-(3d / 100 * width) - ((3d / 100 * width))));
//BA.debugLineNum = 363;BA.debugLine="pnlHBConfirm.Height = 40%y"[mreading2/General script]
views.get("pnlhbconfirm").vw.setHeight((int)((40d / 100 * height)));
//BA.debugLineNum = 364;BA.debugLine="pnlHBConfirm.VerticalCenter = pnlHighBillConfirmation.Height / 2 - 13%y"[mreading2/General script]
views.get("pnlhbconfirm").vw.setTop((int)((views.get("pnlhighbillconfirmation").vw.getHeight())/2d-(13d / 100 * height) - (views.get("pnlhbconfirm").vw.getHeight() / 2)));
//BA.debugLineNum = 366;BA.debugLine="pnlHiConfirmationTitle.SetTopAndBottom(0%y, 40dip)"[mreading2/General script]
views.get("pnlhiconfirmationtitle").vw.setTop((int)((0d / 100 * height)));
views.get("pnlhiconfirmationtitle").vw.setHeight((int)((40d * scale) - ((0d / 100 * height))));
//BA.debugLineNum = 367;BA.debugLine="pnlHiConfirmationTitle.SetLeftAndRight(0%x, pnlHBConfirm.Width)"[mreading2/General script]
views.get("pnlhiconfirmationtitle").vw.setLeft((int)((0d / 100 * width)));
views.get("pnlhiconfirmationtitle").vw.setWidth((int)((views.get("pnlhbconfirm").vw.getWidth()) - ((0d / 100 * width))));
//BA.debugLineNum = 369;BA.debugLine="lblHBConfirmIcon.Left = 2%x"[mreading2/General script]
views.get("lblhbconfirmicon").vw.setLeft((int)((2d / 100 * width)));
//BA.debugLineNum = 370;BA.debugLine="lblHBConfirmIcon.SetTopAndBottom(0.5%y, pnlHiConfirmationTitle.Height - 0.5%y)"[mreading2/General script]
views.get("lblhbconfirmicon").vw.setTop((int)((0.5d / 100 * height)));
views.get("lblhbconfirmicon").vw.setHeight((int)((views.get("pnlhiconfirmationtitle").vw.getHeight())-(0.5d / 100 * height) - ((0.5d / 100 * height))));
//BA.debugLineNum = 372;BA.debugLine="lblHBConfirmTitle.SetLeftAndRight(lblHBConfirmIcon.Right + 5dip, pnlHiConfirmationTitle.Width)"[mreading2/General script]
views.get("lblhbconfirmtitle").vw.setLeft((int)((views.get("lblhbconfirmicon").vw.getLeft() + views.get("lblhbconfirmicon").vw.getWidth())+(5d * scale)));
views.get("lblhbconfirmtitle").vw.setWidth((int)((views.get("pnlhiconfirmationtitle").vw.getWidth()) - ((views.get("lblhbconfirmicon").vw.getLeft() + views.get("lblhbconfirmicon").vw.getWidth())+(5d * scale))));
//BA.debugLineNum = 373;BA.debugLine="lblHBConfirmTitle.VerticalCenter = lblHBConfirmIcon.VerticalCenter"[mreading2/General script]
views.get("lblhbconfirmtitle").vw.setTop((int)((views.get("lblhbconfirmicon").vw.getTop() + views.get("lblhbconfirmicon").vw.getHeight()/2) - (views.get("lblhbconfirmtitle").vw.getHeight() / 2)));
//BA.debugLineNum = 375;BA.debugLine="lblHBConfirmMsgContent.SetLeftAndRight(5%x, pnlHBConfirm.Width - 2%x)"[mreading2/General script]
views.get("lblhbconfirmmsgcontent").vw.setLeft((int)((5d / 100 * width)));
views.get("lblhbconfirmmsgcontent").vw.setWidth((int)((views.get("pnlhbconfirm").vw.getWidth())-(2d / 100 * width) - ((5d / 100 * width))));
//BA.debugLineNum = 376;BA.debugLine="lblHBConfirmMsgContent.Top = 8%y"[mreading2/General script]
views.get("lblhbconfirmmsgcontent").vw.setTop((int)((8d / 100 * height)));
//BA.debugLineNum = 378;BA.debugLine="pnlHBConfirmPresRdgAnchor.SetLeftAndRight(3%x, pnlHBConfirm.Width - 3%x)"[mreading2/General script]
views.get("pnlhbconfirmpresrdganchor").vw.setLeft((int)((3d / 100 * width)));
views.get("pnlhbconfirmpresrdganchor").vw.setWidth((int)((views.get("pnlhbconfirm").vw.getWidth())-(3d / 100 * width) - ((3d / 100 * width))));
//BA.debugLineNum = 379;BA.debugLine="pnlHBConfirmPresRdgAnchor.SetTopAndBottom(lblHBConfirmMsgContent.Bottom + 10dip, pnlHBConfirm.Height - 13%y)"[mreading2/General script]
views.get("pnlhbconfirmpresrdganchor").vw.setTop((int)((views.get("lblhbconfirmmsgcontent").vw.getTop() + views.get("lblhbconfirmmsgcontent").vw.getHeight())+(10d * scale)));
views.get("pnlhbconfirmpresrdganchor").vw.setHeight((int)((views.get("pnlhbconfirm").vw.getHeight())-(13d / 100 * height) - ((views.get("lblhbconfirmmsgcontent").vw.getTop() + views.get("lblhbconfirmmsgcontent").vw.getHeight())+(10d * scale))));
//BA.debugLineNum = 381;BA.debugLine="txtPresRdgConfirm.SetLeftAndRight(2%x, pnlHBConfirmPresRdgAnchor.Width - 2%x)"[mreading2/General script]
views.get("txtpresrdgconfirm").vw.setLeft((int)((2d / 100 * width)));
views.get("txtpresrdgconfirm").vw.setWidth((int)((views.get("pnlhbconfirmpresrdganchor").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
//BA.debugLineNum = 382;BA.debugLine="txtPresRdgConfirm.SetTopAndBottom(0.25%y, pnlHBConfirmPresRdgAnchor.Height - 0.25%y)"[mreading2/General script]
views.get("txtpresrdgconfirm").vw.setTop((int)((0.25d / 100 * height)));
views.get("txtpresrdgconfirm").vw.setHeight((int)((views.get("pnlhbconfirmpresrdganchor").vw.getHeight())-(0.25d / 100 * height) - ((0.25d / 100 * height))));
//BA.debugLineNum = 384;BA.debugLine="btnHBConfirmCancel.SetTopAndBottom(32.5%y, pnlHBConfirm.Height - 10dip)"[mreading2/General script]
views.get("btnhbconfirmcancel").vw.setTop((int)((32.5d / 100 * height)));
views.get("btnhbconfirmcancel").vw.setHeight((int)((views.get("pnlhbconfirm").vw.getHeight())-(10d * scale) - ((32.5d / 100 * height))));
//BA.debugLineNum = 385;BA.debugLine="btnHBConfirmCancel.SetLeftAndRight(3%x, pnlHBConfirm.Width - 64%x)"[mreading2/General script]
views.get("btnhbconfirmcancel").vw.setLeft((int)((3d / 100 * width)));
views.get("btnhbconfirmcancel").vw.setWidth((int)((views.get("pnlhbconfirm").vw.getWidth())-(64d / 100 * width) - ((3d / 100 * width))));
//BA.debugLineNum = 387;BA.debugLine="btnHBConfirmSaveAndPrint.SetTopAndBottom(32.5%y, pnlHBConfirm.Height - 10dip)"[mreading2/General script]
views.get("btnhbconfirmsaveandprint").vw.setTop((int)((32.5d / 100 * height)));
views.get("btnhbconfirmsaveandprint").vw.setHeight((int)((views.get("pnlhbconfirm").vw.getHeight())-(10d * scale) - ((32.5d / 100 * height))));
//BA.debugLineNum = 388;BA.debugLine="btnHBConfirmSaveAndPrint.SetLeftAndRight(50%x, pnlHBConfirm.Width - 3%x)"[mreading2/General script]
views.get("btnhbconfirmsaveandprint").vw.setLeft((int)((50d / 100 * width)));
views.get("btnhbconfirmsaveandprint").vw.setWidth((int)((views.get("pnlhbconfirm").vw.getWidth())-(3d / 100 * width) - ((50d / 100 * width))));
//BA.debugLineNum = 391;BA.debugLine="pnlAveRemMsgBox.Left = 0"[mreading2/General script]
views.get("pnlaveremmsgbox").vw.setLeft((int)(0d));
//BA.debugLineNum = 392;BA.debugLine="pnlAveRemMsgBox.Top = 0"[mreading2/General script]
views.get("pnlaveremmsgbox").vw.setTop((int)(0d));
//BA.debugLineNum = 393;BA.debugLine="pnlAveRemMsgBox.Width = 100%x"[mreading2/General script]
views.get("pnlaveremmsgbox").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 394;BA.debugLine="pnlAveRemMsgBox.Height = 100%y"[mreading2/General script]
views.get("pnlaveremmsgbox").vw.setHeight((int)((100d / 100 * height)));
//BA.debugLineNum = 396;BA.debugLine="pnlAveRemBox.SetLeftAndRight(3%x,pnlAveRemMsgBox.Width - 3%x)"[mreading2/General script]
views.get("pnlaverembox").vw.setLeft((int)((3d / 100 * width)));
views.get("pnlaverembox").vw.setWidth((int)((views.get("pnlaveremmsgbox").vw.getWidth())-(3d / 100 * width) - ((3d / 100 * width))));
//BA.debugLineNum = 397;BA.debugLine="pnlAveRemBox.Height = 88%y"[mreading2/General script]
views.get("pnlaverembox").vw.setHeight((int)((88d / 100 * height)));
//BA.debugLineNum = 398;BA.debugLine="pnlAveRemBox.VerticalCenter = pnlAveRemMsgBox.Height / 2"[mreading2/General script]
views.get("pnlaverembox").vw.setTop((int)((views.get("pnlaveremmsgbox").vw.getHeight())/2d - (views.get("pnlaverembox").vw.getHeight() / 2)));
//BA.debugLineNum = 400;BA.debugLine="pnlAveRemTitle.SetTopAndBottom(0%y, 40dip)"[mreading2/General script]
views.get("pnlaveremtitle").vw.setTop((int)((0d / 100 * height)));
views.get("pnlaveremtitle").vw.setHeight((int)((40d * scale) - ((0d / 100 * height))));
//BA.debugLineNum = 401;BA.debugLine="pnlAveRemTitle.SetLeftAndRight(0%x, pnlAveRemBox.Width)"[mreading2/General script]
views.get("pnlaveremtitle").vw.setLeft((int)((0d / 100 * width)));
views.get("pnlaveremtitle").vw.setWidth((int)((views.get("pnlaverembox").vw.getWidth()) - ((0d / 100 * width))));
//BA.debugLineNum = 403;BA.debugLine="lblIconAveRem.Left = 2%x"[mreading2/General script]
views.get("lbliconaverem").vw.setLeft((int)((2d / 100 * width)));
//BA.debugLineNum = 404;BA.debugLine="lblIconAveRem.SetTopAndBottom(0.5%y, pnlAveRemTitle.Height - 0.5%y)"[mreading2/General script]
views.get("lbliconaverem").vw.setTop((int)((0.5d / 100 * height)));
views.get("lbliconaverem").vw.setHeight((int)((views.get("pnlaveremtitle").vw.getHeight())-(0.5d / 100 * height) - ((0.5d / 100 * height))));
//BA.debugLineNum = 406;BA.debugLine="lblAveRemMsgTitle.SetLeftAndRight(lblIconAveRem.Right + 5dip, pnlAveRemTitle.Width)"[mreading2/General script]
views.get("lblaveremmsgtitle").vw.setLeft((int)((views.get("lbliconaverem").vw.getLeft() + views.get("lbliconaverem").vw.getWidth())+(5d * scale)));
views.get("lblaveremmsgtitle").vw.setWidth((int)((views.get("pnlaveremtitle").vw.getWidth()) - ((views.get("lbliconaverem").vw.getLeft() + views.get("lbliconaverem").vw.getWidth())+(5d * scale))));
//BA.debugLineNum = 407;BA.debugLine="lblAveRemMsgTitle.VerticalCenter = lblIconAveRem.VerticalCenter"[mreading2/General script]
views.get("lblaveremmsgtitle").vw.setTop((int)((views.get("lbliconaverem").vw.getTop() + views.get("lbliconaverem").vw.getHeight()/2) - (views.get("lblaveremmsgtitle").vw.getHeight() / 2)));
//BA.debugLineNum = 409;BA.debugLine="lblAveRemMsgContent.SetLeftAndRight(2%x, pnlAveRemBox.Width - 2%x)"[mreading2/General script]
views.get("lblaveremmsgcontent").vw.setLeft((int)((2d / 100 * width)));
views.get("lblaveremmsgcontent").vw.setWidth((int)((views.get("pnlaverembox").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
//BA.debugLineNum = 410;BA.debugLine="lblAveRemMsgContent.Top = pnlAveRemTitle.Bottom + 10dip"[mreading2/General script]
views.get("lblaveremmsgcontent").vw.setTop((int)((views.get("pnlaveremtitle").vw.getTop() + views.get("pnlaveremtitle").vw.getHeight())+(10d * scale)));
//BA.debugLineNum = 412;BA.debugLine="lblAveRemMsgContent2.SetLeftAndRight(3%x, pnlAveRemBox.Width - 3%x)"[mreading2/General script]
views.get("lblaveremmsgcontent2").vw.setLeft((int)((3d / 100 * width)));
views.get("lblaveremmsgcontent2").vw.setWidth((int)((views.get("pnlaverembox").vw.getWidth())-(3d / 100 * width) - ((3d / 100 * width))));
//BA.debugLineNum = 413;BA.debugLine="lblAveRemMsgContent2.Top = lblAveRemMsgContent.Bottom + 5dip"[mreading2/General script]
views.get("lblaveremmsgcontent2").vw.setTop((int)((views.get("lblaveremmsgcontent").vw.getTop() + views.get("lblaveremmsgcontent").vw.getHeight())+(5d * scale)));
//BA.debugLineNum = 415;BA.debugLine="optReason0.Top = lblAveRemMsgContent2.Bottom + 10dip"[mreading2/General script]
views.get("optreason0").vw.setTop((int)((views.get("lblaveremmsgcontent2").vw.getTop() + views.get("lblaveremmsgcontent2").vw.getHeight())+(10d * scale)));
//BA.debugLineNum = 416;BA.debugLine="optReason0.SetLeftAndRight(8%x, pnlAveRemBox.Width - 1%x)"[mreading2/General script]
views.get("optreason0").vw.setLeft((int)((8d / 100 * width)));
views.get("optreason0").vw.setWidth((int)((views.get("pnlaverembox").vw.getWidth())-(1d / 100 * width) - ((8d / 100 * width))));
//BA.debugLineNum = 418;BA.debugLine="optReason1.Top = optReason0.Bottom + 4dip"[mreading2/General script]
views.get("optreason1").vw.setTop((int)((views.get("optreason0").vw.getTop() + views.get("optreason0").vw.getHeight())+(4d * scale)));
//BA.debugLineNum = 419;BA.debugLine="optReason1.SetLeftAndRight(8%x, pnlAveRemBox.Width - 1%x)"[mreading2/General script]
views.get("optreason1").vw.setLeft((int)((8d / 100 * width)));
views.get("optreason1").vw.setWidth((int)((views.get("pnlaverembox").vw.getWidth())-(1d / 100 * width) - ((8d / 100 * width))));
//BA.debugLineNum = 421;BA.debugLine="optReason2.Top = optReason1.Bottom + 4dip"[mreading2/General script]
views.get("optreason2").vw.setTop((int)((views.get("optreason1").vw.getTop() + views.get("optreason1").vw.getHeight())+(4d * scale)));
//BA.debugLineNum = 422;BA.debugLine="optReason2.SetLeftAndRight(8%x, pnlAveRemBox.Width - 1%x)"[mreading2/General script]
views.get("optreason2").vw.setLeft((int)((8d / 100 * width)));
views.get("optreason2").vw.setWidth((int)((views.get("pnlaverembox").vw.getWidth())-(1d / 100 * width) - ((8d / 100 * width))));
//BA.debugLineNum = 424;BA.debugLine="optReason3.Top = optReason2.Bottom + 4dip"[mreading2/General script]
views.get("optreason3").vw.setTop((int)((views.get("optreason2").vw.getTop() + views.get("optreason2").vw.getHeight())+(4d * scale)));
//BA.debugLineNum = 425;BA.debugLine="optReason3.SetLeftAndRight(8%x, pnlAveRemBox.Width - 1%x)"[mreading2/General script]
views.get("optreason3").vw.setLeft((int)((8d / 100 * width)));
views.get("optreason3").vw.setWidth((int)((views.get("pnlaverembox").vw.getWidth())-(1d / 100 * width) - ((8d / 100 * width))));
//BA.debugLineNum = 427;BA.debugLine="optReason4.Top = optReason3.Bottom + 4dip"[mreading2/General script]
views.get("optreason4").vw.setTop((int)((views.get("optreason3").vw.getTop() + views.get("optreason3").vw.getHeight())+(4d * scale)));
//BA.debugLineNum = 428;BA.debugLine="optReason4.SetLeftAndRight(8%x, pnlAveRemBox.Width - 1%x)"[mreading2/General script]
views.get("optreason4").vw.setLeft((int)((8d / 100 * width)));
views.get("optreason4").vw.setWidth((int)((views.get("pnlaverembox").vw.getWidth())-(1d / 100 * width) - ((8d / 100 * width))));
//BA.debugLineNum = 430;BA.debugLine="optReason5.Top = optReason4.Bottom + 4dip"[mreading2/General script]
views.get("optreason5").vw.setTop((int)((views.get("optreason4").vw.getTop() + views.get("optreason4").vw.getHeight())+(4d * scale)));
//BA.debugLineNum = 431;BA.debugLine="optReason5.SetLeftAndRight(8%x, pnlAveRemBox.Width - 1%x)"[mreading2/General script]
views.get("optreason5").vw.setLeft((int)((8d / 100 * width)));
views.get("optreason5").vw.setWidth((int)((views.get("pnlaverembox").vw.getWidth())-(1d / 100 * width) - ((8d / 100 * width))));
//BA.debugLineNum = 433;BA.debugLine="optReason6.Top = optReason5.Bottom + 4dip"[mreading2/General script]
views.get("optreason6").vw.setTop((int)((views.get("optreason5").vw.getTop() + views.get("optreason5").vw.getHeight())+(4d * scale)));
//BA.debugLineNum = 434;BA.debugLine="optReason6.SetLeftAndRight(8%x, pnlAveRemBox.Width - 1%x)"[mreading2/General script]
views.get("optreason6").vw.setLeft((int)((8d / 100 * width)));
views.get("optreason6").vw.setWidth((int)((views.get("pnlaverembox").vw.getWidth())-(1d / 100 * width) - ((8d / 100 * width))));
//BA.debugLineNum = 436;BA.debugLine="optReason7.Top = optReason6.Bottom + 4dip"[mreading2/General script]
views.get("optreason7").vw.setTop((int)((views.get("optreason6").vw.getTop() + views.get("optreason6").vw.getHeight())+(4d * scale)));
//BA.debugLineNum = 437;BA.debugLine="optReason7.SetLeftAndRight(8%x, pnlAveRemBox.Width - 1%x)"[mreading2/General script]
views.get("optreason7").vw.setLeft((int)((8d / 100 * width)));
views.get("optreason7").vw.setWidth((int)((views.get("pnlaverembox").vw.getWidth())-(1d / 100 * width) - ((8d / 100 * width))));
//BA.debugLineNum = 439;BA.debugLine="optReason8.Top = optReason7.Bottom + 4dip"[mreading2/General script]
views.get("optreason8").vw.setTop((int)((views.get("optreason7").vw.getTop() + views.get("optreason7").vw.getHeight())+(4d * scale)));
//BA.debugLineNum = 440;BA.debugLine="optReason8.SetLeftAndRight(8%x, pnlAveRemBox.Width - 1%x)"[mreading2/General script]
views.get("optreason8").vw.setLeft((int)((8d / 100 * width)));
views.get("optreason8").vw.setWidth((int)((views.get("pnlaverembox").vw.getWidth())-(1d / 100 * width) - ((8d / 100 * width))));
//BA.debugLineNum = 442;BA.debugLine="pnlReasonAnchor.SetLeftAndRight(3%x, pnlAveRemBox.Width - 3%x)"[mreading2/General script]
views.get("pnlreasonanchor").vw.setLeft((int)((3d / 100 * width)));
views.get("pnlreasonanchor").vw.setWidth((int)((views.get("pnlaverembox").vw.getWidth())-(3d / 100 * width) - ((3d / 100 * width))));
//BA.debugLineNum = 443;BA.debugLine="pnlReasonAnchor.Top = optReason8.Bottom + 10dip"[mreading2/General script]
views.get("pnlreasonanchor").vw.setTop((int)((views.get("optreason8").vw.getTop() + views.get("optreason8").vw.getHeight())+(10d * scale)));
//BA.debugLineNum = 445;BA.debugLine="txtReason.SetLeftAndRight(2%x, pnlReasonAnchor.Width - 2%x)"[mreading2/General script]
views.get("txtreason").vw.setLeft((int)((2d / 100 * width)));
views.get("txtreason").vw.setWidth((int)((views.get("pnlreasonanchor").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
//BA.debugLineNum = 446;BA.debugLine="txtReason.SetTopAndBottom(0.25%y, pnlReasonAnchor.Height - 0.25%y)"[mreading2/General script]
views.get("txtreason").vw.setTop((int)((0.25d / 100 * height)));
views.get("txtreason").vw.setHeight((int)((views.get("pnlreasonanchor").vw.getHeight())-(0.25d / 100 * height) - ((0.25d / 100 * height))));
//BA.debugLineNum = 448;BA.debugLine="btnAveRemCancelPost.SetTopAndBottom(80%y, pnlAveRemBox.Height - 10dip)"[mreading2/General script]
views.get("btnaveremcancelpost").vw.setTop((int)((80d / 100 * height)));
views.get("btnaveremcancelpost").vw.setHeight((int)((views.get("pnlaverembox").vw.getHeight())-(10d * scale) - ((80d / 100 * height))));
//BA.debugLineNum = 449;BA.debugLine="btnAveRemCancelPost.SetLeftAndRight(3%x, pnlAveRemBox.Width - 67%x)"[mreading2/General script]
views.get("btnaveremcancelpost").vw.setLeft((int)((3d / 100 * width)));
views.get("btnaveremcancelpost").vw.setWidth((int)((views.get("pnlaverembox").vw.getWidth())-(67d / 100 * width) - ((3d / 100 * width))));
//BA.debugLineNum = 451;BA.debugLine="btnAveRemSaveAndPrint.SetTopAndBottom(80%y, pnlAveRemBox.Height - 10dip)"[mreading2/General script]
views.get("btnaveremsaveandprint").vw.setTop((int)((80d / 100 * height)));
views.get("btnaveremsaveandprint").vw.setHeight((int)((views.get("pnlaverembox").vw.getHeight())-(10d * scale) - ((80d / 100 * height))));
//BA.debugLineNum = 452;BA.debugLine="btnAveRemSaveAndPrint.SetLeftAndRight(50%x, pnlAveRemBox.Width - 3%x)"[mreading2/General script]
views.get("btnaveremsaveandprint").vw.setLeft((int)((50d / 100 * width)));
views.get("btnaveremsaveandprint").vw.setWidth((int)((views.get("pnlaverembox").vw.getWidth())-(3d / 100 * width) - ((50d / 100 * width))));
//BA.debugLineNum = 455;BA.debugLine="pnlAveMsgBox.Left = 0"[mreading2/General script]
views.get("pnlavemsgbox").vw.setLeft((int)(0d));
//BA.debugLineNum = 456;BA.debugLine="pnlAveMsgBox.Top = 0"[mreading2/General script]
views.get("pnlavemsgbox").vw.setTop((int)(0d));
//BA.debugLineNum = 457;BA.debugLine="pnlAveMsgBox.Width = 100%x"[mreading2/General script]
views.get("pnlavemsgbox").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 458;BA.debugLine="pnlAveMsgBox.Height = 100%y"[mreading2/General script]
views.get("pnlavemsgbox").vw.setHeight((int)((100d / 100 * height)));
//BA.debugLineNum = 460;BA.debugLine="pnlAveBox.SetLeftAndRight(3%x,pnlAveMsgBox.Width - 3%x)"[mreading2/General script]
views.get("pnlavebox").vw.setLeft((int)((3d / 100 * width)));
views.get("pnlavebox").vw.setWidth((int)((views.get("pnlavemsgbox").vw.getWidth())-(3d / 100 * width) - ((3d / 100 * width))));
//BA.debugLineNum = 461;BA.debugLine="pnlAveBox.Height = 35%y"[mreading2/General script]
views.get("pnlavebox").vw.setHeight((int)((35d / 100 * height)));
//BA.debugLineNum = 462;BA.debugLine="pnlAveBox.VerticalCenter = pnlAveMsgBox.Height / 2 - 10%y"[mreading2/General script]
views.get("pnlavebox").vw.setTop((int)((views.get("pnlavemsgbox").vw.getHeight())/2d-(10d / 100 * height) - (views.get("pnlavebox").vw.getHeight() / 2)));
//BA.debugLineNum = 464;BA.debugLine="pnlAveTitle.SetTopAndBottom(0%y, 40dip)"[mreading2/General script]
views.get("pnlavetitle").vw.setTop((int)((0d / 100 * height)));
views.get("pnlavetitle").vw.setHeight((int)((40d * scale) - ((0d / 100 * height))));
//BA.debugLineNum = 465;BA.debugLine="pnlAveTitle.SetLeftAndRight(0%x, pnlAveBox.Width)"[mreading2/General script]
views.get("pnlavetitle").vw.setLeft((int)((0d / 100 * width)));
views.get("pnlavetitle").vw.setWidth((int)((views.get("pnlavebox").vw.getWidth()) - ((0d / 100 * width))));
//BA.debugLineNum = 467;BA.debugLine="lblIconAve.Left = 2%x"[mreading2/General script]
views.get("lbliconave").vw.setLeft((int)((2d / 100 * width)));
//BA.debugLineNum = 468;BA.debugLine="lblIconAve.SetTopAndBottom(0.5%y, pnlAveTitle.Height - 0.5%y)"[mreading2/General script]
views.get("lbliconave").vw.setTop((int)((0.5d / 100 * height)));
views.get("lbliconave").vw.setHeight((int)((views.get("pnlavetitle").vw.getHeight())-(0.5d / 100 * height) - ((0.5d / 100 * height))));
//BA.debugLineNum = 470;BA.debugLine="lblAveMsgTitle.SetLeftAndRight(lblIconAve.Right + 5dip, pnlAveTitle.Width)"[mreading2/General script]
views.get("lblavemsgtitle").vw.setLeft((int)((views.get("lbliconave").vw.getLeft() + views.get("lbliconave").vw.getWidth())+(5d * scale)));
views.get("lblavemsgtitle").vw.setWidth((int)((views.get("pnlavetitle").vw.getWidth()) - ((views.get("lbliconave").vw.getLeft() + views.get("lbliconave").vw.getWidth())+(5d * scale))));
//BA.debugLineNum = 471;BA.debugLine="lblAveMsgTitle.VerticalCenter = lblIconAve.VerticalCenter"[mreading2/General script]
views.get("lblavemsgtitle").vw.setTop((int)((views.get("lbliconave").vw.getTop() + views.get("lbliconave").vw.getHeight()/2) - (views.get("lblavemsgtitle").vw.getHeight() / 2)));
//BA.debugLineNum = 473;BA.debugLine="lblAveMsgContent.SetLeftAndRight(3%x, pnlAveBox.Width - 3%x)"[mreading2/General script]
views.get("lblavemsgcontent").vw.setLeft((int)((3d / 100 * width)));
views.get("lblavemsgcontent").vw.setWidth((int)((views.get("pnlavebox").vw.getWidth())-(3d / 100 * width) - ((3d / 100 * width))));
//BA.debugLineNum = 474;BA.debugLine="lblAveMsgContent.Top = pnlAveTitle.Bottom + 10dip"[mreading2/General script]
views.get("lblavemsgcontent").vw.setTop((int)((views.get("pnlavetitle").vw.getTop() + views.get("pnlavetitle").vw.getHeight())+(10d * scale)));
//BA.debugLineNum = 476;BA.debugLine="lblAveMsgContent2.SetLeftAndRight(3%x, pnlAveBox.Width - 3%x)"[mreading2/General script]
views.get("lblavemsgcontent2").vw.setLeft((int)((3d / 100 * width)));
views.get("lblavemsgcontent2").vw.setWidth((int)((views.get("pnlavebox").vw.getWidth())-(3d / 100 * width) - ((3d / 100 * width))));
//BA.debugLineNum = 477;BA.debugLine="lblAveMsgContent2.Top = lblAveMsgContent.Bottom + 5dip"[mreading2/General script]
views.get("lblavemsgcontent2").vw.setTop((int)((views.get("lblavemsgcontent").vw.getTop() + views.get("lblavemsgcontent").vw.getHeight())+(5d * scale)));
//BA.debugLineNum = 479;BA.debugLine="btnAveCancelPost.SetTopAndBottom(28%y, pnlAveBox.Height - 10dip)"[mreading2/General script]
views.get("btnavecancelpost").vw.setTop((int)((28d / 100 * height)));
views.get("btnavecancelpost").vw.setHeight((int)((views.get("pnlavebox").vw.getHeight())-(10d * scale) - ((28d / 100 * height))));
//BA.debugLineNum = 480;BA.debugLine="btnAveCancelPost.SetLeftAndRight(3%x, pnlAveBox.Width - 65%x)"[mreading2/General script]
views.get("btnavecancelpost").vw.setLeft((int)((3d / 100 * width)));
views.get("btnavecancelpost").vw.setWidth((int)((views.get("pnlavebox").vw.getWidth())-(65d / 100 * width) - ((3d / 100 * width))));
//BA.debugLineNum = 482;BA.debugLine="btnAveTakePicture.SetTopAndBottom(28%y, pnlAveBox.Height - 10dip)"[mreading2/General script]
views.get("btnavetakepicture").vw.setTop((int)((28d / 100 * height)));
views.get("btnavetakepicture").vw.setHeight((int)((views.get("pnlavebox").vw.getHeight())-(10d * scale) - ((28d / 100 * height))));
//BA.debugLineNum = 483;BA.debugLine="btnAveTakePicture.SetLeftAndRight(52%x, pnlAveBox.Width - 3%x)"[mreading2/General script]
views.get("btnavetakepicture").vw.setLeft((int)((52d / 100 * width)));
views.get("btnavetakepicture").vw.setWidth((int)((views.get("pnlavebox").vw.getWidth())-(3d / 100 * width) - ((52d / 100 * width))));
//BA.debugLineNum = 486;BA.debugLine="pnlSuperHB.Left = 0"[mreading2/General script]
views.get("pnlsuperhb").vw.setLeft((int)(0d));
//BA.debugLineNum = 487;BA.debugLine="pnlSuperHB.Top = 0"[mreading2/General script]
views.get("pnlsuperhb").vw.setTop((int)(0d));
//BA.debugLineNum = 488;BA.debugLine="pnlSuperHB.Width = 100%x"[mreading2/General script]
views.get("pnlsuperhb").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 489;BA.debugLine="pnlSuperHB.Height = 100%y"[mreading2/General script]
views.get("pnlsuperhb").vw.setHeight((int)((100d / 100 * height)));
//BA.debugLineNum = 491;BA.debugLine="pnlSuperHBDialog.SetLeftAndRight(3%x,pnlSuperHB.Width - 3%x)"[mreading2/General script]
views.get("pnlsuperhbdialog").vw.setLeft((int)((3d / 100 * width)));
views.get("pnlsuperhbdialog").vw.setWidth((int)((views.get("pnlsuperhb").vw.getWidth())-(3d / 100 * width) - ((3d / 100 * width))));
//BA.debugLineNum = 492;BA.debugLine="pnlSuperHBDialog.Height = 45%y"[mreading2/General script]
views.get("pnlsuperhbdialog").vw.setHeight((int)((45d / 100 * height)));
//BA.debugLineNum = 493;BA.debugLine="pnlSuperHBDialog.VerticalCenter = pnlSuperHB.Height / 2 - 13%y"[mreading2/General script]
views.get("pnlsuperhbdialog").vw.setTop((int)((views.get("pnlsuperhb").vw.getHeight())/2d-(13d / 100 * height) - (views.get("pnlsuperhbdialog").vw.getHeight() / 2)));
//BA.debugLineNum = 495;BA.debugLine="lblSuperHBIcon.Left = 1%x"[mreading2/General script]
views.get("lblsuperhbicon").vw.setLeft((int)((1d / 100 * width)));
//BA.debugLineNum = 496;BA.debugLine="lblSuperHBIcon.Top = 1%y"[mreading2/General script]
views.get("lblsuperhbicon").vw.setTop((int)((1d / 100 * height)));
//BA.debugLineNum = 498;BA.debugLine="lblSuperHBTitle.SetLeftAndRight(0, pnlSuperHBDialog.Width)"[mreading2/General script]
views.get("lblsuperhbtitle").vw.setLeft((int)(0d));
views.get("lblsuperhbtitle").vw.setWidth((int)((views.get("pnlsuperhbdialog").vw.getWidth()) - (0d)));
//BA.debugLineNum = 499;BA.debugLine="lblSuperHBTitle.Top = 0%y"[mreading2/General script]
views.get("lblsuperhbtitle").vw.setTop((int)((0d / 100 * height)));
//BA.debugLineNum = 501;BA.debugLine="lblSuperHBMsgContent.SetLeftAndRight(5%x, pnlSuperHBDialog.Width - 2%x)"[mreading2/General script]
views.get("lblsuperhbmsgcontent").vw.setLeft((int)((5d / 100 * width)));
views.get("lblsuperhbmsgcontent").vw.setWidth((int)((views.get("pnlsuperhbdialog").vw.getWidth())-(2d / 100 * width) - ((5d / 100 * width))));
//BA.debugLineNum = 502;BA.debugLine="lblSuperHBMsgContent.Top = 8%y"[mreading2/General script]
views.get("lblsuperhbmsgcontent").vw.setTop((int)((8d / 100 * height)));
//BA.debugLineNum = 504;BA.debugLine="pnlSuperHBPresRdgAnchor.SetLeftAndRight(3%x, pnlSuperHBDialog.Width - 3%x)"[mreading2/General script]
views.get("pnlsuperhbpresrdganchor").vw.setLeft((int)((3d / 100 * width)));
views.get("pnlsuperhbpresrdganchor").vw.setWidth((int)((views.get("pnlsuperhbdialog").vw.getWidth())-(3d / 100 * width) - ((3d / 100 * width))));
//BA.debugLineNum = 505;BA.debugLine="pnlSuperHBPresRdgAnchor.SetTopAndBottom(lblSuperHBMsgContent.Bottom + 5dip, pnlSuperHBDialog.Height - 12%y)"[mreading2/General script]
views.get("pnlsuperhbpresrdganchor").vw.setTop((int)((views.get("lblsuperhbmsgcontent").vw.getTop() + views.get("lblsuperhbmsgcontent").vw.getHeight())+(5d * scale)));
views.get("pnlsuperhbpresrdganchor").vw.setHeight((int)((views.get("pnlsuperhbdialog").vw.getHeight())-(12d / 100 * height) - ((views.get("lblsuperhbmsgcontent").vw.getTop() + views.get("lblsuperhbmsgcontent").vw.getHeight())+(5d * scale))));
//BA.debugLineNum = 507;BA.debugLine="txtSuperHBPresRdg.SetLeftAndRight(2%x, pnlSuperHBPresRdgAnchor.Width - 2%x)"[mreading2/General script]
views.get("txtsuperhbpresrdg").vw.setLeft((int)((2d / 100 * width)));
views.get("txtsuperhbpresrdg").vw.setWidth((int)((views.get("pnlsuperhbpresrdganchor").vw.getWidth())-(2d / 100 * width) - ((2d / 100 * width))));
//BA.debugLineNum = 508;BA.debugLine="txtSuperHBPresRdg.SetTopAndBottom(0.25%y, pnlSuperHBPresRdgAnchor.Height - 0.25%y)"[mreading2/General script]
views.get("txtsuperhbpresrdg").vw.setTop((int)((0.25d / 100 * height)));
views.get("txtsuperhbpresrdg").vw.setHeight((int)((views.get("pnlsuperhbpresrdganchor").vw.getHeight())-(0.25d / 100 * height) - ((0.25d / 100 * height))));
//BA.debugLineNum = 510;BA.debugLine="btnSuperHBCancel.SetTopAndBottom(35%y, pnlSuperHBDialog.Height - 10dip)"[mreading2/General script]
views.get("btnsuperhbcancel").vw.setTop((int)((35d / 100 * height)));
views.get("btnsuperhbcancel").vw.setHeight((int)((views.get("pnlsuperhbdialog").vw.getHeight())-(10d * scale) - ((35d / 100 * height))));
//BA.debugLineNum = 511;BA.debugLine="btnSuperHBCancel.SetLeftAndRight(3%x, pnlSuperHBDialog.Width - 68%x)"[mreading2/General script]
views.get("btnsuperhbcancel").vw.setLeft((int)((3d / 100 * width)));
views.get("btnsuperhbcancel").vw.setWidth((int)((views.get("pnlsuperhbdialog").vw.getWidth())-(68d / 100 * width) - ((3d / 100 * width))));
//BA.debugLineNum = 513;BA.debugLine="btnSuperHBSave.SetTopAndBottom(35%y, pnlSuperHBDialog.Height - 10dip)"[mreading2/General script]
views.get("btnsuperhbsave").vw.setTop((int)((35d / 100 * height)));
views.get("btnsuperhbsave").vw.setHeight((int)((views.get("pnlsuperhbdialog").vw.getHeight())-(10d * scale) - ((35d / 100 * height))));
//BA.debugLineNum = 514;BA.debugLine="btnSuperHBSave.SetLeftAndRight(50%x, pnlSuperHBDialog.Width - 3%x)"[mreading2/General script]
views.get("btnsuperhbsave").vw.setLeft((int)((50d / 100 * width)));
views.get("btnsuperhbsave").vw.setWidth((int)((views.get("pnlsuperhbdialog").vw.getWidth())-(3d / 100 * width) - ((50d / 100 * width))));
//BA.debugLineNum = 518;BA.debugLine="pnlMisCodeMsgBox.Left = 0"[mreading2/General script]
views.get("pnlmiscodemsgbox").vw.setLeft((int)(0d));
//BA.debugLineNum = 519;BA.debugLine="pnlMisCodeMsgBox.Top = 0"[mreading2/General script]
views.get("pnlmiscodemsgbox").vw.setTop((int)(0d));
//BA.debugLineNum = 520;BA.debugLine="pnlMisCodeMsgBox.Width = 100%x"[mreading2/General script]
views.get("pnlmiscodemsgbox").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 521;BA.debugLine="pnlMisCodeMsgBox.Height = 100%y"[mreading2/General script]
views.get("pnlmiscodemsgbox").vw.setHeight((int)((100d / 100 * height)));
//BA.debugLineNum = 523;BA.debugLine="pnlMisCodeBox.SetLeftAndRight(3%x,pnlMisCodeMsgBox.Width - 3%x)"[mreading2/General script]
views.get("pnlmiscodebox").vw.setLeft((int)((3d / 100 * width)));
views.get("pnlmiscodebox").vw.setWidth((int)((views.get("pnlmiscodemsgbox").vw.getWidth())-(3d / 100 * width) - ((3d / 100 * width))));
//BA.debugLineNum = 524;BA.debugLine="pnlMisCodeBox.Height = 85%y"[mreading2/General script]
views.get("pnlmiscodebox").vw.setHeight((int)((85d / 100 * height)));
//BA.debugLineNum = 525;BA.debugLine="pnlMisCodeBox.VerticalCenter = pnlMisCodeMsgBox.Height / 2"[mreading2/General script]
views.get("pnlmiscodebox").vw.setTop((int)((views.get("pnlmiscodemsgbox").vw.getHeight())/2d - (views.get("pnlmiscodebox").vw.getHeight() / 2)));
//BA.debugLineNum = 527;BA.debugLine="lblIconMisCode.Left = 1%x"[mreading2/General script]
views.get("lbliconmiscode").vw.setLeft((int)((1d / 100 * width)));
//BA.debugLineNum = 528;BA.debugLine="lblIconMisCode.Top = 0%y"[mreading2/General script]
views.get("lbliconmiscode").vw.setTop((int)((0d / 100 * height)));
//BA.debugLineNum = 530;BA.debugLine="lblMisCodeMsgTitle.SetLeftAndRight(0, pnlMisCodeBox.Width)"[mreading2/General script]
views.get("lblmiscodemsgtitle").vw.setLeft((int)(0d));
views.get("lblmiscodemsgtitle").vw.setWidth((int)((views.get("pnlmiscodebox").vw.getWidth()) - (0d)));
//BA.debugLineNum = 531;BA.debugLine="lblMisCodeMsgTitle.Top = 0%y"[mreading2/General script]
views.get("lblmiscodemsgtitle").vw.setTop((int)((0d / 100 * height)));
//BA.debugLineNum = 533;BA.debugLine="lblMisCodeMsgContent.SetLeftAndRight(3%x, pnlMisCodeBox.Width - 1%x)"[mreading2/General script]
views.get("lblmiscodemsgcontent").vw.setLeft((int)((3d / 100 * width)));
views.get("lblmiscodemsgcontent").vw.setWidth((int)((views.get("pnlmiscodebox").vw.getWidth())-(1d / 100 * width) - ((3d / 100 * width))));
//BA.debugLineNum = 534;BA.debugLine="lblMisCodeMsgContent.Top = 7%y"[mreading2/General script]
views.get("lblmiscodemsgcontent").vw.setTop((int)((7d / 100 * height)));
//BA.debugLineNum = 536;BA.debugLine="lblMisCodeMsgContent2.SetLeftAndRight(5%x, pnlMisCodeBox.Width - 4%x)"[mreading2/General script]
views.get("lblmiscodemsgcontent2").vw.setLeft((int)((5d / 100 * width)));
views.get("lblmiscodemsgcontent2").vw.setWidth((int)((views.get("pnlmiscodebox").vw.getWidth())-(4d / 100 * width) - ((5d / 100 * width))));
//BA.debugLineNum = 537;BA.debugLine="lblMisCodeMsgContent2.Top = lblMisCodeMsgContent.Bottom + 5dip"[mreading2/General script]
views.get("lblmiscodemsgcontent2").vw.setTop((int)((views.get("lblmiscodemsgcontent").vw.getTop() + views.get("lblmiscodemsgcontent").vw.getHeight())+(5d * scale)));
//BA.debugLineNum = 539;BA.debugLine="optMisCode0.Top = lblMisCodeMsgContent2.Bottom + 5dip"[mreading2/General script]
views.get("optmiscode0").vw.setTop((int)((views.get("lblmiscodemsgcontent2").vw.getTop() + views.get("lblmiscodemsgcontent2").vw.getHeight())+(5d * scale)));
//BA.debugLineNum = 540;BA.debugLine="optMisCode0.SetLeftAndRight(8%x, pnlMisCodeBox.Width - 1%x)"[mreading2/General script]
views.get("optmiscode0").vw.setLeft((int)((8d / 100 * width)));
views.get("optmiscode0").vw.setWidth((int)((views.get("pnlmiscodebox").vw.getWidth())-(1d / 100 * width) - ((8d / 100 * width))));
//BA.debugLineNum = 542;BA.debugLine="optMisCode1.Top = optMisCode0.Bottom + 4dip"[mreading2/General script]
views.get("optmiscode1").vw.setTop((int)((views.get("optmiscode0").vw.getTop() + views.get("optmiscode0").vw.getHeight())+(4d * scale)));
//BA.debugLineNum = 543;BA.debugLine="optMisCode1.SetLeftAndRight(8%x, pnlMisCodeBox.Width - 1%x)"[mreading2/General script]
views.get("optmiscode1").vw.setLeft((int)((8d / 100 * width)));
views.get("optmiscode1").vw.setWidth((int)((views.get("pnlmiscodebox").vw.getWidth())-(1d / 100 * width) - ((8d / 100 * width))));
//BA.debugLineNum = 545;BA.debugLine="optMisCode2.Top = optMisCode1.Bottom + 4dip"[mreading2/General script]
views.get("optmiscode2").vw.setTop((int)((views.get("optmiscode1").vw.getTop() + views.get("optmiscode1").vw.getHeight())+(4d * scale)));
//BA.debugLineNum = 546;BA.debugLine="optMisCode2.SetLeftAndRight(8%x, pnlMisCodeBox.Width - 1%x)"[mreading2/General script]
views.get("optmiscode2").vw.setLeft((int)((8d / 100 * width)));
views.get("optmiscode2").vw.setWidth((int)((views.get("pnlmiscodebox").vw.getWidth())-(1d / 100 * width) - ((8d / 100 * width))));
//BA.debugLineNum = 548;BA.debugLine="optMisCode3.Top = optMisCode2.Bottom + 4dip"[mreading2/General script]
views.get("optmiscode3").vw.setTop((int)((views.get("optmiscode2").vw.getTop() + views.get("optmiscode2").vw.getHeight())+(4d * scale)));
//BA.debugLineNum = 549;BA.debugLine="optMisCode3.SetLeftAndRight(8%x, pnlMisCodeBox.Width - 1%x)"[mreading2/General script]
views.get("optmiscode3").vw.setLeft((int)((8d / 100 * width)));
views.get("optmiscode3").vw.setWidth((int)((views.get("pnlmiscodebox").vw.getWidth())-(1d / 100 * width) - ((8d / 100 * width))));
//BA.debugLineNum = 551;BA.debugLine="optMisCode4.Top = optMisCode3.Bottom + 4dip"[mreading2/General script]
views.get("optmiscode4").vw.setTop((int)((views.get("optmiscode3").vw.getTop() + views.get("optmiscode3").vw.getHeight())+(4d * scale)));
//BA.debugLineNum = 552;BA.debugLine="optMisCode4.SetLeftAndRight(8%x, pnlMisCodeBox.Width - 1%x)"[mreading2/General script]
views.get("optmiscode4").vw.setLeft((int)((8d / 100 * width)));
views.get("optmiscode4").vw.setWidth((int)((views.get("pnlmiscodebox").vw.getWidth())-(1d / 100 * width) - ((8d / 100 * width))));
//BA.debugLineNum = 554;BA.debugLine="optMisCode1.Top = optMisCode0.Bottom + 4dip"[mreading2/General script]
views.get("optmiscode1").vw.setTop((int)((views.get("optmiscode0").vw.getTop() + views.get("optmiscode0").vw.getHeight())+(4d * scale)));
//BA.debugLineNum = 555;BA.debugLine="optMisCode1.SetLeftAndRight(8%x, pnlMisCodeBox.Width - 1%x)"[mreading2/General script]
views.get("optmiscode1").vw.setLeft((int)((8d / 100 * width)));
views.get("optmiscode1").vw.setWidth((int)((views.get("pnlmiscodebox").vw.getWidth())-(1d / 100 * width) - ((8d / 100 * width))));
//BA.debugLineNum = 557;BA.debugLine="optMisCode5.Top = optMisCode4.Bottom + 4dip"[mreading2/General script]
views.get("optmiscode5").vw.setTop((int)((views.get("optmiscode4").vw.getTop() + views.get("optmiscode4").vw.getHeight())+(4d * scale)));
//BA.debugLineNum = 558;BA.debugLine="optMisCode5.SetLeftAndRight(8%x, pnlMisCodeBox.Width - 1%x)"[mreading2/General script]
views.get("optmiscode5").vw.setLeft((int)((8d / 100 * width)));
views.get("optmiscode5").vw.setWidth((int)((views.get("pnlmiscodebox").vw.getWidth())-(1d / 100 * width) - ((8d / 100 * width))));
//BA.debugLineNum = 560;BA.debugLine="optMisCode6.Top = optMisCode5.Bottom + 4dip"[mreading2/General script]
views.get("optmiscode6").vw.setTop((int)((views.get("optmiscode5").vw.getTop() + views.get("optmiscode5").vw.getHeight())+(4d * scale)));
//BA.debugLineNum = 561;BA.debugLine="optMisCode6.SetLeftAndRight(8%x, pnlMisCodeBox.Width - 1%x)"[mreading2/General script]
views.get("optmiscode6").vw.setLeft((int)((8d / 100 * width)));
views.get("optmiscode6").vw.setWidth((int)((views.get("pnlmiscodebox").vw.getWidth())-(1d / 100 * width) - ((8d / 100 * width))));
//BA.debugLineNum = 563;BA.debugLine="optMisCode7.Top = optMisCode6.Bottom + 4dip"[mreading2/General script]
views.get("optmiscode7").vw.setTop((int)((views.get("optmiscode6").vw.getTop() + views.get("optmiscode6").vw.getHeight())+(4d * scale)));
//BA.debugLineNum = 564;BA.debugLine="optMisCode7.SetLeftAndRight(8%x, pnlMisCodeBox.Width - 1%x)"[mreading2/General script]
views.get("optmiscode7").vw.setLeft((int)((8d / 100 * width)));
views.get("optmiscode7").vw.setWidth((int)((views.get("pnlmiscodebox").vw.getWidth())-(1d / 100 * width) - ((8d / 100 * width))));
//BA.debugLineNum = 566;BA.debugLine="optMisCode8.Top = optMisCode7.Bottom + 4dip"[mreading2/General script]
views.get("optmiscode8").vw.setTop((int)((views.get("optmiscode7").vw.getTop() + views.get("optmiscode7").vw.getHeight())+(4d * scale)));
//BA.debugLineNum = 567;BA.debugLine="optMisCode8.SetLeftAndRight(8%x, pnlMisCodeBox.Width - 1%x)"[mreading2/General script]
views.get("optmiscode8").vw.setLeft((int)((8d / 100 * width)));
views.get("optmiscode8").vw.setWidth((int)((views.get("pnlmiscodebox").vw.getWidth())-(1d / 100 * width) - ((8d / 100 * width))));
//BA.debugLineNum = 569;BA.debugLine="pnlMisCodeAnchor.Top = optMisCode8.Bottom + 3dip"[mreading2/General script]
views.get("pnlmiscodeanchor").vw.setTop((int)((views.get("optmiscode8").vw.getTop() + views.get("optmiscode8").vw.getHeight())+(3d * scale)));
//BA.debugLineNum = 570;BA.debugLine="pnlMisCodeAnchor.SetLeftAndRight(8%x, pnlMisCodeBox.Width - 3%x)"[mreading2/General script]
views.get("pnlmiscodeanchor").vw.setLeft((int)((8d / 100 * width)));
views.get("pnlmiscodeanchor").vw.setWidth((int)((views.get("pnlmiscodebox").vw.getWidth())-(3d / 100 * width) - ((8d / 100 * width))));
//BA.debugLineNum = 572;BA.debugLine="txtMisCode.SetLeftAndRight(2%x, pnlMisCodeAnchor.Width - 1%x)"[mreading2/General script]
views.get("txtmiscode").vw.setLeft((int)((2d / 100 * width)));
views.get("txtmiscode").vw.setWidth((int)((views.get("pnlmiscodeanchor").vw.getWidth())-(1d / 100 * width) - ((2d / 100 * width))));
//BA.debugLineNum = 573;BA.debugLine="txtMisCode.SetTopAndBottom(0.25%y, pnlMisCodeAnchor.Height - 0.25%y)"[mreading2/General script]
views.get("txtmiscode").vw.setTop((int)((0.25d / 100 * height)));
views.get("txtmiscode").vw.setHeight((int)((views.get("pnlmiscodeanchor").vw.getHeight())-(0.25d / 100 * height) - ((0.25d / 100 * height))));
//BA.debugLineNum = 575;BA.debugLine="btnMisCodeCancelPost.SetTopAndBottom(77%y, pnlMisCodeBox.Height - 10dip)"[mreading2/General script]
views.get("btnmiscodecancelpost").vw.setTop((int)((77d / 100 * height)));
views.get("btnmiscodecancelpost").vw.setHeight((int)((views.get("pnlmiscodebox").vw.getHeight())-(10d * scale) - ((77d / 100 * height))));
//BA.debugLineNum = 576;BA.debugLine="btnMisCodeCancelPost.SetLeftAndRight(3%x, pnlMisCodeBox.Width - 68%x)"[mreading2/General script]
views.get("btnmiscodecancelpost").vw.setLeft((int)((3d / 100 * width)));
views.get("btnmiscodecancelpost").vw.setWidth((int)((views.get("pnlmiscodebox").vw.getWidth())-(68d / 100 * width) - ((3d / 100 * width))));
//BA.debugLineNum = 578;BA.debugLine="btnMisCodeSave.SetTopAndBottom(77%y, pnlMisCodeBox.Height - 10dip)"[mreading2/General script]
views.get("btnmiscodesave").vw.setTop((int)((77d / 100 * height)));
views.get("btnmiscodesave").vw.setHeight((int)((views.get("pnlmiscodebox").vw.getHeight())-(10d * scale) - ((77d / 100 * height))));
//BA.debugLineNum = 579;BA.debugLine="btnMisCodeSave.SetLeftAndRight(pnlMisCodeBox.Width - 30%x, pnlMisCodeBox.Width - 3%x)"[mreading2/General script]
views.get("btnmiscodesave").vw.setLeft((int)((views.get("pnlmiscodebox").vw.getWidth())-(30d / 100 * width)));
views.get("btnmiscodesave").vw.setWidth((int)((views.get("pnlmiscodebox").vw.getWidth())-(3d / 100 * width) - ((views.get("pnlmiscodebox").vw.getWidth())-(30d / 100 * width))));

}
}