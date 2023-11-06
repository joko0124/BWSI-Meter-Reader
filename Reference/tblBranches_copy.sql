/*
Navicat SQLite Data Transfer

Source Server         : Meter Reading DBase
Source Server Version : 30714
Source Host           : :0

Target Server Type    : SQLite
Target Server Version : 30714
File Encoding         : 65001

Date: 2019-09-25 14:07:44
*/

PRAGMA foreign_keys = OFF;

-- ----------------------------
-- Table structure for tblBranches_copy
-- ----------------------------
DROP TABLE IF EXISTS "main"."tblBranches_copy";
CREATE TABLE "tblBranches_copy" (
"BranchID"  INTEGER,
"CompanyID"  INTEGER,
"BranchCode"  TEXT,
"BranchName"  TEXT,
"BranchAddress"  TEXT,
"ContactNo"  TEXT,
"Email"  TEXT,
"TinNo"  TEXT,
"NotarialFee"  REAL(9,2),
"WithMeterCharges"  INTEGER DEFAULT 0,
"WithFranchisedTax"  INTEGER DEFAULT 0,
"WithSeptageFee"  INTEGER DEFAULT 0,
"WithDisconDate"  INTEGER DEFAULT 0,
"LogoName"  TEXT
);
