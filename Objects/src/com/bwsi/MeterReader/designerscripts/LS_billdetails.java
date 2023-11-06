package com.bwsi.MeterReader.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_billdetails{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
//BA.debugLineNum = 2;BA.debugLine="AutoScaleAll"[billdetails/General script]
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
//BA.debugLineNum = 4;BA.debugLine="ToolBar.Top = 0dip"[billdetails/General script]
views.get("toolbar").vw.setTop((int)((0d * scale)));
//BA.debugLineNum = 6;BA.debugLine="If ActivitySize > 6.5 Then"[billdetails/General script]
if ((anywheresoftware.b4a.keywords.LayoutBuilder.getScreenSize()>6.5d)) { 
;
//BA.debugLineNum = 7;BA.debugLine="ToolBar.Height = 64dip"[billdetails/General script]
views.get("toolbar").vw.setHeight((int)((64d * scale)));
//BA.debugLineNum = 8;BA.debugLine="Else"[billdetails/General script]
;}else{ 
;
//BA.debugLineNum = 9;BA.debugLine="If Portrait Then"[billdetails/General script]
if ((BA.ObjectToBoolean( String.valueOf(anywheresoftware.b4a.keywords.LayoutBuilder.isPortrait())))) { 
;
//BA.debugLineNum = 10;BA.debugLine="ToolBar.Height = 56dip"[billdetails/General script]
views.get("toolbar").vw.setHeight((int)((56d * scale)));
//BA.debugLineNum = 11;BA.debugLine="Else"[billdetails/General script]
;}else{ 
;
//BA.debugLineNum = 12;BA.debugLine="ToolBar.Height = 48dip"[billdetails/General script]
views.get("toolbar").vw.setHeight((int)((48d * scale)));
//BA.debugLineNum = 13;BA.debugLine="End If"[billdetails/General script]
;};
//BA.debugLineNum = 14;BA.debugLine="End If"[billdetails/General script]
;};
//BA.debugLineNum = 16;BA.debugLine="pnlContent.Left = 0"[billdetails/General script]
views.get("pnlcontent").vw.setLeft((int)(0d));
//BA.debugLineNum = 17;BA.debugLine="pnlContent.Width = 100%x"[billdetails/General script]
views.get("pnlcontent").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 18;BA.debugLine="pnlContent.SetTopAndBottom(ToolBar.Bottom, 100%y)"[billdetails/General script]
views.get("pnlcontent").vw.setTop((int)((views.get("toolbar").vw.getTop() + views.get("toolbar").vw.getHeight())));
views.get("pnlcontent").vw.setHeight((int)((100d / 100 * height) - ((views.get("toolbar").vw.getTop() + views.get("toolbar").vw.getHeight()))));
//BA.debugLineNum = 19;BA.debugLine="pnlAccountInfo.SetLeftAndRight(1%x, 99%x)"[billdetails/General script]
views.get("pnlaccountinfo").vw.setLeft((int)((1d / 100 * width)));
views.get("pnlaccountinfo").vw.setWidth((int)((99d / 100 * width) - ((1d / 100 * width))));
//BA.debugLineNum = 20;BA.debugLine="Label1.Left = 10dip"[billdetails/General script]
views.get("label1").vw.setLeft((int)((10d * scale)));
//BA.debugLineNum = 21;BA.debugLine="lblAcctNo.SetLeftAndRight(lbl.Right, 95%x)"[billdetails/General script]
views.get("lblacctno").vw.setLeft((int)((views.get("lbl").vw.getLeft() + views.get("lbl").vw.getWidth())));
views.get("lblacctno").vw.setWidth((int)((95d / 100 * width) - ((views.get("lbl").vw.getLeft() + views.get("lbl").vw.getWidth()))));
//BA.debugLineNum = 22;BA.debugLine="lblAcctName.SetLeftAndRight(Label1.Left, 95%x)"[billdetails/General script]
views.get("lblacctname").vw.setLeft((int)((views.get("label1").vw.getLeft())));
views.get("lblacctname").vw.setWidth((int)((95d / 100 * width) - ((views.get("label1").vw.getLeft()))));
//BA.debugLineNum = 23;BA.debugLine="lblAddress.SetLeftAndRight(Label1.Left, 95%x)"[billdetails/General script]
views.get("lbladdress").vw.setLeft((int)((views.get("label1").vw.getLeft())));
views.get("lbladdress").vw.setWidth((int)((95d / 100 * width) - ((views.get("label1").vw.getLeft()))));
//BA.debugLineNum = 24;BA.debugLine="lblMeterNo.SetLeftAndRight(lbl.Right, 95%x)"[billdetails/General script]
views.get("lblmeterno").vw.setLeft((int)((views.get("lbl").vw.getLeft() + views.get("lbl").vw.getWidth())));
views.get("lblmeterno").vw.setWidth((int)((95d / 100 * width) - ((views.get("lbl").vw.getLeft() + views.get("lbl").vw.getWidth()))));
//BA.debugLineNum = 25;BA.debugLine="lblGD.SetLeftAndRight(Label6.Right, 95%x)"[billdetails/General script]
views.get("lblgd").vw.setLeft((int)((views.get("label6").vw.getLeft() + views.get("label6").vw.getWidth())));
views.get("lblgd").vw.setWidth((int)((95d / 100 * width) - ((views.get("label6").vw.getLeft() + views.get("label6").vw.getWidth()))));
//BA.debugLineNum = 26;BA.debugLine="lblBookSeq.SetLeftAndRight(Label3.Right, 95%x)"[billdetails/General script]
views.get("lblbookseq").vw.setLeft((int)((views.get("label3").vw.getLeft() + views.get("label3").vw.getWidth())));
views.get("lblbookseq").vw.setWidth((int)((95d / 100 * width) - ((views.get("label3").vw.getLeft() + views.get("label3").vw.getWidth()))));
//BA.debugLineNum = 27;BA.debugLine="lblMeterNo.SetLeftAndRight(Label7.Right, 95%x)"[billdetails/General script]
views.get("lblmeterno").vw.setLeft((int)((views.get("label7").vw.getLeft() + views.get("label7").vw.getWidth())));
views.get("lblmeterno").vw.setWidth((int)((95d / 100 * width) - ((views.get("label7").vw.getLeft() + views.get("label7").vw.getWidth()))));
//BA.debugLineNum = 28;BA.debugLine="lblClass.SetLeftAndRight(Label10.Right, 95%x)"[billdetails/General script]
views.get("lblclass").vw.setLeft((int)((views.get("label10").vw.getLeft() + views.get("label10").vw.getWidth())));
views.get("lblclass").vw.setWidth((int)((95d / 100 * width) - ((views.get("label10").vw.getLeft() + views.get("label10").vw.getWidth()))));
//BA.debugLineNum = 30;BA.debugLine="pnlConsumption.SetLeftAndRight(1%x, 99%x)"[billdetails/General script]
views.get("pnlconsumption").vw.setLeft((int)((1d / 100 * width)));
views.get("pnlconsumption").vw.setWidth((int)((99d / 100 * width) - ((1d / 100 * width))));
//BA.debugLineNum = 31;BA.debugLine="Label4.Left = 10dip"[billdetails/General script]
views.get("label4").vw.setLeft((int)((10d * scale)));
//BA.debugLineNum = 32;BA.debugLine="lblPresRdg.SetLeftAndRight(Label9.Right, 95%x)"[billdetails/General script]
views.get("lblpresrdg").vw.setLeft((int)((views.get("label9").vw.getLeft() + views.get("label9").vw.getWidth())));
views.get("lblpresrdg").vw.setWidth((int)((95d / 100 * width) - ((views.get("label9").vw.getLeft() + views.get("label9").vw.getWidth()))));
//BA.debugLineNum = 33;BA.debugLine="lblPrevRdg.SetLeftAndRight(Label12.Right, 95%x)"[billdetails/General script]
views.get("lblprevrdg").vw.setLeft((int)((views.get("label12").vw.getLeft() + views.get("label12").vw.getWidth())));
views.get("lblprevrdg").vw.setWidth((int)((95d / 100 * width) - ((views.get("label12").vw.getLeft() + views.get("label12").vw.getWidth()))));
//BA.debugLineNum = 34;BA.debugLine="lblAddCons.SetLeftAndRight(Label15.Right, 95%x)"[billdetails/General script]
views.get("lbladdcons").vw.setLeft((int)((views.get("label15").vw.getLeft() + views.get("label15").vw.getWidth())));
views.get("lbladdcons").vw.setWidth((int)((95d / 100 * width) - ((views.get("label15").vw.getLeft() + views.get("label15").vw.getWidth()))));
//BA.debugLineNum = 35;BA.debugLine="lblTotalCons.SetLeftAndRight(Label16.Right, 95%x)"[billdetails/General script]
views.get("lbltotalcons").vw.setLeft((int)((views.get("label16").vw.getLeft() + views.get("label16").vw.getWidth())));
views.get("lbltotalcons").vw.setWidth((int)((95d / 100 * width) - ((views.get("label16").vw.getLeft() + views.get("label16").vw.getWidth()))));
//BA.debugLineNum = 37;BA.debugLine="pnlBill.SetLeftAndRight(1%x, 99%x)"[billdetails/General script]
views.get("pnlbill").vw.setLeft((int)((1d / 100 * width)));
views.get("pnlbill").vw.setWidth((int)((99d / 100 * width) - ((1d / 100 * width))));
//BA.debugLineNum = 38;BA.debugLine="pnlAfterDue.SetLeftAndRight(1%x, 99%x)"[billdetails/General script]
views.get("pnlafterdue").vw.setLeft((int)((1d / 100 * width)));
views.get("pnlafterdue").vw.setWidth((int)((99d / 100 * width) - ((1d / 100 * width))));
//BA.debugLineNum = 39;BA.debugLine="Label18.Left = 10dip"[billdetails/General script]
views.get("label18").vw.setLeft((int)((10d * scale)));
//BA.debugLineNum = 40;BA.debugLine="lblBasic.SetLeftAndRight(Label19.Right, 95%x)"[billdetails/General script]
views.get("lblbasic").vw.setLeft((int)((views.get("label19").vw.getLeft() + views.get("label19").vw.getWidth())));
views.get("lblbasic").vw.setWidth((int)((95d / 100 * width) - ((views.get("label19").vw.getLeft() + views.get("label19").vw.getWidth()))));
//BA.debugLineNum = 41;BA.debugLine="lblPCA.SetLeftAndRight(Label19.Right, 95%x)"[billdetails/General script]
views.get("lblpca").vw.setLeft((int)((views.get("label19").vw.getLeft() + views.get("label19").vw.getWidth())));
views.get("lblpca").vw.setWidth((int)((95d / 100 * width) - ((views.get("label19").vw.getLeft() + views.get("label19").vw.getWidth()))));
//BA.debugLineNum = 42;BA.debugLine="lblCurrent.SetLeftAndRight(Label19.Right, 95%x)"[billdetails/General script]
views.get("lblcurrent").vw.setLeft((int)((views.get("label19").vw.getLeft() + views.get("label19").vw.getWidth())));
views.get("lblcurrent").vw.setWidth((int)((95d / 100 * width) - ((views.get("label19").vw.getLeft() + views.get("label19").vw.getWidth()))));
//BA.debugLineNum = 43;BA.debugLine="lblSCDisc10.SetLeftAndRight(Label19.Right, 95%x)"[billdetails/General script]
views.get("lblscdisc10").vw.setLeft((int)((views.get("label19").vw.getLeft() + views.get("label19").vw.getWidth())));
views.get("lblscdisc10").vw.setWidth((int)((95d / 100 * width) - ((views.get("label19").vw.getLeft() + views.get("label19").vw.getWidth()))));
//BA.debugLineNum = 44;BA.debugLine="lblOthers.SetLeftAndRight(Label23.Right, 95%x)"[billdetails/General script]
views.get("lblothers").vw.setLeft((int)((views.get("label23").vw.getLeft() + views.get("label23").vw.getWidth())));
views.get("lblothers").vw.setWidth((int)((95d / 100 * width) - ((views.get("label23").vw.getLeft() + views.get("label23").vw.getWidth()))));
//BA.debugLineNum = 45;BA.debugLine="lblArrears.SetLeftAndRight(Label29.Right, 95%x)"[billdetails/General script]
views.get("lblarrears").vw.setLeft((int)((views.get("label29").vw.getLeft() + views.get("label29").vw.getWidth())));
views.get("lblarrears").vw.setWidth((int)((95d / 100 * width) - ((views.get("label29").vw.getLeft() + views.get("label29").vw.getWidth()))));
//BA.debugLineNum = 46;BA.debugLine="lblAdvances.SetLeftAndRight(Label33.Right, 95%x)"[billdetails/General script]
views.get("lbladvances").vw.setLeft((int)((views.get("label33").vw.getLeft() + views.get("label33").vw.getWidth())));
views.get("lbladvances").vw.setWidth((int)((95d / 100 * width) - ((views.get("label33").vw.getLeft() + views.get("label33").vw.getWidth()))));
//BA.debugLineNum = 47;BA.debugLine="lblTotDue.SetLeftAndRight(Label35.Right, 95%x)"[billdetails/General script]
views.get("lbltotdue").vw.setLeft((int)((views.get("label35").vw.getLeft() + views.get("label35").vw.getWidth())));
views.get("lbltotdue").vw.setWidth((int)((95d / 100 * width) - ((views.get("label35").vw.getLeft() + views.get("label35").vw.getWidth()))));
//BA.debugLineNum = 48;BA.debugLine="lblDueDate.SetLeftAndRight(Label31.Right, 95%x)"[billdetails/General script]
views.get("lblduedate").vw.setLeft((int)((views.get("label31").vw.getLeft() + views.get("label31").vw.getWidth())));
views.get("lblduedate").vw.setWidth((int)((95d / 100 * width) - ((views.get("label31").vw.getLeft() + views.get("label31").vw.getWidth()))));
//BA.debugLineNum = 49;BA.debugLine="lblPenalty.SetLeftAndRight(Label37.Right, 95%x)"[billdetails/General script]
views.get("lblpenalty").vw.setLeft((int)((views.get("label37").vw.getLeft() + views.get("label37").vw.getWidth())));
views.get("lblpenalty").vw.setWidth((int)((95d / 100 * width) - ((views.get("label37").vw.getLeft() + views.get("label37").vw.getWidth()))));
//BA.debugLineNum = 50;BA.debugLine="lblSCDisc5.SetLeftAndRight(Label41.Right, 95%x)"[billdetails/General script]
views.get("lblscdisc5").vw.setLeft((int)((views.get("label41").vw.getLeft() + views.get("label41").vw.getWidth())));
views.get("lblscdisc5").vw.setWidth((int)((95d / 100 * width) - ((views.get("label41").vw.getLeft() + views.get("label41").vw.getWidth()))));
//BA.debugLineNum = 51;BA.debugLine="lblAfterDueAmt.SetLeftAndRight(Label43.Right, 95%x)"[billdetails/General script]
views.get("lblafterdueamt").vw.setLeft((int)((views.get("label43").vw.getLeft() + views.get("label43").vw.getWidth())));
views.get("lblafterdueamt").vw.setWidth((int)((95d / 100 * width) - ((views.get("label43").vw.getLeft() + views.get("label43").vw.getWidth()))));
//BA.debugLineNum = 52;BA.debugLine="lblPaymentAfterDue.Width = 100%X"[billdetails/General script]
views.get("lblpaymentafterdue").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 53;BA.debugLine="lblPaymentAfterDue.HorizontalCenter = 50%X"[billdetails/General script]
views.get("lblpaymentafterdue").vw.setLeft((int)((50d / 100 * width) - (views.get("lblpaymentafterdue").vw.getWidth() / 2)));
//BA.debugLineNum = 54;BA.debugLine="btnClose.SetTopAndBottom(pnlAfterDue.Bottom + 20dip, 90%y)"[billdetails/General script]
views.get("btnclose").vw.setTop((int)((views.get("pnlafterdue").vw.getTop() + views.get("pnlafterdue").vw.getHeight())+(20d * scale)));
views.get("btnclose").vw.setHeight((int)((90d / 100 * height) - ((views.get("pnlafterdue").vw.getTop() + views.get("pnlafterdue").vw.getHeight())+(20d * scale))));
//BA.debugLineNum = 55;BA.debugLine="btnPrint.SetTopAndBottom(pnlAfterDue.Bottom + 20dip, 90%y)"[billdetails/General script]
views.get("btnprint").vw.setTop((int)((views.get("pnlafterdue").vw.getTop() + views.get("pnlafterdue").vw.getHeight())+(20d * scale)));
views.get("btnprint").vw.setHeight((int)((90d / 100 * height) - ((views.get("pnlafterdue").vw.getTop() + views.get("pnlafterdue").vw.getHeight())+(20d * scale))));
//BA.debugLineNum = 56;BA.debugLine="btnPrint.Left=33%x"[billdetails/General script]
views.get("btnprint").vw.setLeft((int)((33d / 100 * width)));
//BA.debugLineNum = 57;BA.debugLine="btnClose.Left=btnPrint.Right + 5dip"[billdetails/General script]
views.get("btnclose").vw.setLeft((int)((views.get("btnprint").vw.getLeft() + views.get("btnprint").vw.getWidth())+(5d * scale)));

}
}