/*
Navicat SQLite Data Transfer

Source Server         : Branch Data
Source Server Version : 30714
Source Host           : :0

Target Server Type    : SQLite
Target Server Version : 30714
File Encoding         : 65001

Date: 2019-09-25 14:06:58
*/

PRAGMA foreign_keys = OFF;

-- ----------------------------
-- Table structure for tblBranches
-- ----------------------------
DROP TABLE IF EXISTS "main"."tblBranches";
CREATE TABLE "tblBranches" (
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

-- ----------------------------
-- Records of tblBranches
-- ----------------------------
INSERT INTO "main"."tblBranches" VALUES (1, 1, '', 'BWSI - Garden Villas Branch', '', '', '', '', 0.0, 0, 0, 0, 0, 'bwsi.png');
INSERT INTO "main"."tblBranches" VALUES (2, 1, '', 'BWSI - Laoac Branch', '', '', '', '', 0.0, 0, 0, 0, 0, 'bwsi.png');
INSERT INTO "main"."tblBranches" VALUES (3, 1, 1010, 'BWSI - Laurel Branch', 'Public Market, Pob.  2 Laurel, Batangas', 'CP#: 0928 273 1941', '', '000-743-345-011', 0.0, 0, 0, 0, 0, 'bwsi.png');
INSERT INTO "main"."tblBranches" VALUES (4, 1, 1003, 'BWSI - Llanera Branch', 'Corpuz St. Cor. Lagasca Avenue', 'CELL# 0929-363-4993', '', '000-743-345-006', 0.0, 0, 0, 0, 0, 'bwsi.png');
INSERT INTO "main"."tblBranches" VALUES (5, 1, '', 'BWSI - Main/Central Branch', '', '', '', '', 0.0, 0, 0, 0, 0, 'bwsi.png');
INSERT INTO "main"."tblBranches" VALUES (6, 1, '', 'BWSI - Mapalacsiao Branch', '', '', '', '', 0.0, 0, 0, 0, 0, 'bwsi.png');
INSERT INTO "main"."tblBranches" VALUES (7, 1, '', 'BWSI - San Jacinto Branch', '', '', '', '', 0.0, 0, 0, 0, 0, 'bwsi.png');
INSERT INTO "main"."tblBranches" VALUES (8, 1, '', 'BWSI - Sta. Barbara Branch', '', '', '', '', 0.0, 0, 0, 0, 0, 'bwsi.png');
INSERT INTO "main"."tblBranches" VALUES (9, 1, 1004, 'BWSI - Sto. Domingo Branch', 'Malasin, Sto. Domingo, Nueva Ecija', 'CP#: 0998-471-6495', '', '000-743-345-004', 0.0, 0, 0, 0, 0, 'bwsi.png');
INSERT INTO "main"."tblBranches" VALUES (10, 1, 1011, 'BWSI - Talisay Branch', 'Unit 202 Balai Sofia, Com. Arcade Banga', 'CP#: 0921-785-7588', '', '000-743-345-013', 0.0, 0, 0, 0, 0, 'bwsi.png');
INSERT INTO "main"."tblBranches" VALUES (11, 1, '', 'BWSI - Victoria Tarlac Branch', '', '', '', '', 0.0, 0, 0, 0, 0, 'bwsi.png');
INSERT INTO "main"."tblBranches" VALUES (12, 1, 2005, 'BWSI - Zaragoza Branch', 'Prk. 2 Brgy. San Isidro, Zaragoza, Nueva Ecija', 'CP#: 0921-616-0492', '', '000-743-345-009', 0.0, 0, 0, 0, 0, 'bwsi.png');
INSERT INTO "main"."tblBranches" VALUES (13, 1, 1030, 'BWSI - Canyon Woods Branch', 'Canyon Woods Residential Resort', '', '', '000-743-345-000', 0.0, 0, 0, 0, 0, 'bwsi.png');
INSERT INTO "main"."tblBranches" VALUES (14, 2, 3004, 'CLPI - Apalit Branch', 'Sampaloc, Apalit, Pampanga', '0942-780-2830 / 0915-397-7742', '', '205-497-971-003', 0.0, 0, 0, 1, 0, 'clpi.png');
INSERT INTO "main"."tblBranches" VALUES (15, 2, '', 'CLPI - Bamban Branch', '', '', '', '', 0.0, 0, 0, 0, 0, 'clpi.png');
INSERT INTO "main"."tblBranches" VALUES (16, 2, '', 'CLPI - Caba Branch', '', '', '', '', 0.0, 0, 0, 0, 0, 'clpi.png');
INSERT INTO "main"."tblBranches" VALUES (17, 2, '', 'CLPI - Meycauayan Branch', '', '', '', '', 0.0, 0, 0, 0, 0, 'clpi.png');
INSERT INTO "main"."tblBranches" VALUES (18, 2, 3008, 'CLPI - Minalin Branch', 'San Nicolas, Minalin, Pampanga', 'TEL#: 435-0922', '', '205-497-971-005', 0.0, 0, 0, 0, 0, 'clpi.png');
INSERT INTO "main"."tblBranches" VALUES (19, 2, '0007', 'CLPI - Porac Branch', '114 National Rd., Sta Cruz, Porac, Pampanga', '0918-506-2951', '', '205-497-971-007', 0.0, 0, 0, 0, 0, 'clpi.png');
INSERT INTO "main"."tblBranches" VALUES (20, 2, 2001, 'CLPI - Pugo Branch', '2/F Estobayan Apt., Poblacion East', 'CP# 0909-270-6347', '', '205-497-971-010', 0.0, 0, 0, 0, 0, 'clpi.png');
INSERT INTO "main"."tblBranches" VALUES (21, 2, '', 'CLPI - San Simon Branch', '', '', '', '', 0.0, 0, 0, 0, 0, 'clpi.png');
INSERT INTO "main"."tblBranches" VALUES (22, 2, '', 'CLPI - Sta. Maria Branch', '', '', '', '', 0.0, 0, 0, 0, 0, 'clpi.png');
INSERT INTO "main"."tblBranches" VALUES (23, 2, '', 'CLPI - Sta. Rita Branch', '', '', '', '', 0.0, 0, 0, 0, 0, 'clpi.png');
INSERT INTO "main"."tblBranches" VALUES (24, 2, 3006, 'CLPI - Sto. Tomas Branch', 'McArthur Hi-way, San Matias, Sto Tomas (P)
', 'TEL# (045) 887-1170', '', '205-497-971-002', 0.0, 0, 0, 0, 0, 'clpi.png');
INSERT INTO "main"."tblBranches" VALUES (25, 3, '', 'HWRI - Balagtas Branch', '', '', '', '', 0.0, 0, 0, 0, 0, 'hiyas.png');
INSERT INTO "main"."tblBranches" VALUES (26, 3, 4007, 'HWRI - Matungao Branch', '812 Fatima St. Matungao, Bulakan, Bulacan', 'CP# 0923-347-0040', '', '223-772-603-005', 0.0, 0, 0, 0, 0, 'hiyas.png');
INSERT INTO "main"."tblBranches" VALUES (27, 3, '', 'HWRI - Bustos Branch', '', '', '', '', 0.0, 0, 0, 0, 0, 'hiyas.png');
INSERT INTO "main"."tblBranches" VALUES (28, 3, 4003, 'HWRI - Malis Branch', 'North Bel-Air, Malis', 'TEL# 0932-370-4412', '', '223-772-603-002', 0.0, 0, 0, 0, 0, 'gwd.png');
INSERT INTO "main"."tblBranches" VALUES (29, 3, 4001, 'HWRI - Agatha Branch', 'St. Agatha Subd., Sta. Rita', 'TEL# 794-4927 / CEL# 0917-894-4927', '', '223-772-603-000', 0.0, 0, 0, 0, 0, 'gwd.png');
INSERT INTO "main"."tblBranches" VALUES (30, 3, 4002, 'HWRI - Tuktukan Branch', '2nd Floor Cundangan Bldg, Tuktukan', 'TEL# 794-4300', '', '223-772-603-000', 0.0, 0, 0, 0, 0, 'gwd.png');
INSERT INTO "main"."tblBranches" VALUES (31, 3, '', 'HWRI - Pandi I Branch', '', '', '', '', 0.0, 0, 0, 0, 0, 'hiyas.png');
INSERT INTO "main"."tblBranches" VALUES (32, 3, '', 'HWRI - Pandi II Branch', '', '', '', '', 0.0, 0, 0, 0, 0, 'hiyas.png');
INSERT INTO "main"."tblBranches" VALUES (33, 3, '', 'HWRI - Pandi III Branch', '', '', '', '', 0.0, 0, 0, 0, 0, 'hiyas.png');
INSERT INTO "main"."tblBranches" VALUES (34, 3, '', 'HWRI - Pandi IV Branch', '', '', '', '', 0.0, 0, 0, 0, 0, 'hiyas.png');
INSERT INTO "main"."tblBranches" VALUES (35, 3, '', 'HWRI - Pandi V Branch', '', '', '', '', 0.0, 0, 0, 0, 0, 'hiyas.png');
INSERT INTO "main"."tblBranches" VALUES (36, 3, 4008, 'HWRI - Paombong Branch', '467 A Sto Niño, Paombong, Bulacan', 'TEL# 794-9885 / 09560568766 / 09225029411', '', '223-772-603-007', 0.0, 0, 0, 0, 0, 'hiyas.png');
INSERT INTO "main"."tblBranches" VALUES (37, 4, '', 'BCBI - Alfonso Castañeda Branch', '', '', '', '', 0.0, 0, 0, 0, 0, 'bcbi.png');
INSERT INTO "main"."tblBranches" VALUES (38, 4, '', 'BCBI - Arayat Branch', '', '', '', '', 0.0, 0, 0, 0, 0, 'bcbi.png');
INSERT INTO "main"."tblBranches" VALUES (39, 4, '', 'BCBI - Aritao Branch', '', '', '', '', 0.0, 0, 0, 0, 0, 'bcbi.png');
INSERT INTO "main"."tblBranches" VALUES (40, 4, '', 'BCBI - Bagabag Branch', '', '', '', '', 0.0, 0, 0, 0, 0, 'bcbi.png');
INSERT INTO "main"."tblBranches" VALUES (41, 4, '', 'BCBI - Bayombong Branch', '', '', '', '', 0.0, 0, 0, 0, 0, 'bcbi.png');
INSERT INTO "main"."tblBranches" VALUES (42, 4, '', 'BCBI - Capas Branch', '', '', '', '', 0.0, 0, 0, 0, 0, 'bcbi.png');
INSERT INTO "main"."tblBranches" VALUES (43, 4, '', 'BCBI - Dapdap Branch', '', '', '', '', 0.0, 0, 0, 0, 0, 'bcbi.png');
INSERT INTO "main"."tblBranches" VALUES (44, 4, '', 'BCBI - Magalang Branch', '', '', '', '', 0.0, 0, 0, 0, 0, 'bcbi.png');
INSERT INTO "main"."tblBranches" VALUES (45, 4, '', 'BCBI - Maria Aurora Branch', '', '', '', '', 0.0, 0, 0, 0, 0, 'bcbi.png');
INSERT INTO "main"."tblBranches" VALUES (46, 4, '', 'BCBI - San Luis Branch', '', '', '', '', 0.0, 0, 0, 0, 0, 'bcbi.png');
INSERT INTO "main"."tblBranches" VALUES (47, 4, '', 'BCBI - Sta. Ana Branch', '', '', '', '', 0.0, 0, 0, 0, 0, 'bcbi.png');
INSERT INTO "main"."tblBranches" VALUES (48, 4, 2008, 'BCBI - Telabastagan Branch', 'Essel Park Subd., Telabastagan, CSFP', 'TEL# (045) 887-3753', '', '006-588-335-005', 0.0, 0, 0, 0, 0, 'bcbi.png');
INSERT INTO "main"."tblBranches" VALUES (49, 5, '', 'BIRI - Tuba Branch', '', '', '', '', 0.0, 0, 0, 0, 0, 'biri.png');
INSERT INTO "main"."tblBranches" VALUES (50, 6, '', 'LBWI - Victoria Branch', '', '', '', '', 0.0, 0, 0, 0, 0, 'lbwi.png');
INSERT INTO "main"."tblBranches" VALUES (51, 7, '', 'SBRI - Bingawan Branch', '', '', '', '', 0.0, 0, 0, 0, 0, 'sbri.png');
INSERT INTO "main"."tblBranches" VALUES (52, 7, '', 'SBRI - Bohol Branch', '', '', '', '', 0.0, 0, 0, 0, 0, 'sbri.png');
INSERT INTO "main"."tblBranches" VALUES (53, 7, 8001, 'SBRI - Passi Branch', 'Commonwealth Drv., Pob. Ilawod, Passi City', 'TEL# 311-5999 / CEL# 0928-555-0693', '', '007-973-298-000', 0.0, 0, 0, 0, 0, 'sbri.png');
INSERT INTO "main"."tblBranches" VALUES (54, 7, 6003, 'SBRI - Smartec Branch', 'Brgy. Bongco,  Pototan, Iloilo', 'CEL# 0917-155-6151', '', '007-973-298-002', 0.0, 0, 0, 0, 0, 'sbri.png');
INSERT INTO "main"."tblBranches" VALUES (55, 8, '', 'MR3H - Bamban Branch', '', '', '', '', 0.0, 0, 0, 0, 0, 'mr3h.png');
INSERT INTO "main"."tblBranches" VALUES (56, 8, '', 'MR3H - Sto. Domingo Branch', '', '', '', '', 0.0, 0, 0, 0, 0, 'mr3h.png');
INSERT INTO "main"."tblBranches" VALUES (57, 3, 4005, 'HWRI - Malolos Branch', 'Malolos Heights Subd., Tikay', 'CEL# 0923-984-0119', '', '223-772-603-003', 0.0, 0, 0, 0, 0, 'hiyas.png');
INSERT INTO "main"."tblBranches" VALUES (58, 3, 4007, 'HWRI - San Francisco Branch', 'Purok 4, San Francisco, Bulakan, Bulacan', 'TEL# 792-9990 / CEL# 0932-614-7605', null, '223-772-603-005', 0.0, 0, 0, 0, 0, 'hiyas.png');
INSERT INTO "main"."tblBranches" VALUES (59, 3, 4015, 'HWRI - Salintubig  Branch', 'Cacarong Bata, Pandi Bulacan', 'CEL# 0967-607-4270', null, '223-772-603-008', 0.0, 0, 0, 0, 0, 'hiyas.png');
INSERT INTO "main"."tblBranches" VALUES (60, 4, 2025, 'BCBI - Carranglan Branch', 'Brgy. TL Padilla, Carranglan, Nueva Ecija', 'CEL# 0961-369-0001', null, '006-588-335-013', 0.0, 0, 0, 0, 0, 'bcbi.png');
INSERT INTO "main"."tblBranches" VALUES (61, 1, 1014, 'BWSI - Cuyapo Branch', 'Brgy. Bulala, Cuyapo, Nueva Ecija', '0936-2728182 / 0950-3795939', null, '000-743-345-017', 0.0, 1, 1, 0, 0, 'cuyapo.png');
INSERT INTO "main"."tblBranches" VALUES (62, 2, 3005, 'CLPI - Balucuc Branch', 'Sitio Centro, Apalit, Pampanga', '0943-0967759 / 0966-3177531', null, '205-497-971-003', 0.0, 0, 0, 1, 0, 'clpi.png');
INSERT INTO "main"."tblBranches" VALUES (63, 7, 7001, 'SBRI - Buug Branch', 'Brgy. Datu Panas, Buug, Zamboanga', '', '', '007-973-298', 0.0, 0, 0, 0, 0, 'sbri.png');
INSERT INTO "main"."tblBranches" VALUES (64, 2, 3008, 'CLPI - Minalin Branch', 'San Nicolas, Minalin, Pampanga', 'TEL#: 435-0922', '', '205-497-971-005', 0.0, 0, 0, 0, 0, 'clpi.png');
INSERT INTO "main"."tblBranches" VALUES (65, 7, 6003, 'SBRI - Jaro Branch', 'Brgy. Tagbac, Jaro, Iloilo City', 'TEL# (033) 500-5701 / CP# 0933-829-9209', null, '007-973-298-005', 0.0, 0, 0, 0, 0, 'sbri.png');
