/*
Navicat SQLite Data Transfer

Source Server         : New Dbase Test
Source Server Version : 30714
Source Host           : :0

Target Server Type    : SQLite
Target Server Version : 30714
File Encoding         : 65001

Date: 2020-07-23 11:48:00
*/

PRAGMA foreign_keys = OFF;

-- ----------------------------
-- Table structure for tblReadings
-- ----------------------------
DROP TABLE IF EXISTS "main"."tblReadings";
CREATE TABLE "tblReadings" (
"ReadID"  INTEGER PRIMARY KEY AUTOINCREMENT,
"BillNo"  TEXT,
"BillYear"  INTEGER,
"BillMonth"  INTEGER,
"BranchID"  INTEGER,
"BookID"  INTEGER,
"BookNo"  TEXT,
"AcctID"  INTEGER,
"AcctNo"  TEXT,
"OldAcctNo"  TEXT,
"AcctName"  TEXT,
"AcctAddress"  TEXT,
"AcctClass"  TEXT,
"AcctSubClass"  TEXT,
"AcctStatus"  TEXT,
"MeterID"  INTEGER,
"MeterNo"  TEXT,
"MaxReading"  INTEGER,
"SeqNo"  TEXT DEFAULT 0,
"IsSenior"  INTEGER,
"SeniorOnBefore"  REAL,
"SeniorAfter"  REAL,
"SeniorMaxCum"  INTEGER,
"GDeposit"  REAL,
"PrevRdgDate"  TEXT,
"PrevRdg"  INTEGER,
"PrevCum"  INTEGER,
"BillPeriod1st"  TEXT,
"PrevCum1st"  INTEGER,
"BillPeriod2nd"  TEXT,
"PrevCum2nd"  INTEGER,
"BillPeriod3rd"  TEXT,
"PrevCum3rd"  INTEGER,
"FinalRdg"  TEXT,
"DisconDate"  TEXT,
"PresRdgDate"  TEXT,
"OrigRdg"  INTEGER,
"PresRdg"  INTEGER,
"PresCum"  INTEGER,
"DateFrom"  TEXT,
"DateTo"  TEXT,
"WithDueDate"  INTEGER DEFAULT 1,
"DueDate"  TEXT,
"DisconnectionDate"  TEXT,
"AveCum"  INTEGER,
"BillType"  TEXT,
"AddCons"  INTEGER,
"TotalCum"  INTEGER,
"BasicAmt"  REAL,
"PCA"  REAL,
"PCAAmt"  REAL,
"AddToBillAmt"  REAL,
"AtbRef"  BLOB,
"MeterCharges"  REAL DEFAULT 0,
"FranchiseTaxPct"  REAL,
"FranchiseTaxAmt"  REAL DEFAULT 0,
"HasSeptageFee"  INTEGER DEFAULT 0,
"MinSeptageCum"  INTEGER,
"MaxSeptageCum"  INTEGER DEFAULT 0,
"SeptageRate"  REAL,
"SeptageAmt"  REAL DEFAULT 0,
"CurrentAmt"  REAL,
"ArrearsAmt"  REAL,
"TotalDueAmt"  REAL,
"SeniorOnBeforeAmt"  REAL,
"SeniorAfterAmt"  REAL,
"TotalDueAmtBeforeSC"  REAL,
"AdvPayment"  REAL,
"PenaltyPct"  REAL,
"PenaltyAmt"  REAL,
"TotalDueAmtAfterSC"  REAL,
"DiscAmt"  REAL,
"ReadRemarks"  INTEGER,
"PaidStatus"  INTEGER,
"PrintStatus"  INTEGER,
"NoPrinted"  INTEGER DEFAULT 0,
"WasMissCoded"  INTEGER,
"MissCoded"  INTEGER DEFAULT 0,
"MissCodeID"  INTEGER,
"MissCode"  TEXT,
"WasImplosive"  INTEGER,
"ImplosiveType"  TEXT,
"ImpID"  INTEGER,
"IncDecRate"  REAL,
"WasRead"  INTEGER,
"FFindings"  TEXT,
"FWarnings"  TEXT,
"NewSeqNo"  INTEGER,
"NoInput"  INTEGER,
"TimeRead"  TEXT,
"DateRead"  TEXT,
"ReadBy"  INTEGER,
"WasUploaded"  INTEGER
);
