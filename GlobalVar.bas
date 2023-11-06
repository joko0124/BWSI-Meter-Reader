B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=StaticCode
Version=9.3
@EndOfDesignText@
Sub Process_Globals
	'Server IP Address
'	Public ServerAddress = "http://10.0.11.14/elite-system/public" As String
'	Public ServerAddress = "http://192.168.1.16/elite-system/public/mreading/" As String
	Public ServerAddress As String
	Public ControllerPrefix As String = "/mreading/"
	
	Type theCompany (ID As Int, Name As String)
	Public CompanyID As Int
	Public CompanyCode As String
	Public CompanyName As String
	
	Type theBranch (ID As Int, Name As String, Code As String, Address As String, ContactNo As String, TIN As String, Logo As String)
	Public BranchID As Int
	Public BranchName As String
	Public BranchCode As String
	Public BranchAddress As String
	Public BranchContactNo As String
	Public BranchTINumber As String
	Public BranchLogo As String
	Public WithMeterCharges As Int
	Public WithFranchisedTax As Int
	Public WithSeptageFee As Int
	Public WithDisconDate As Int
	
	Type theUser (ID As Int, BranchID As Int, EmployeeName As String, UserName As String, Password As String, Mod1 As Int,  Mod2 As Int,  Mod3 As Int,  Mod4 As Int,  Mod5 As Int,  Mod6 As Int)
	Public UserID As Int
	Public EmpName As String
	Public UserName As String
	Public UserPW As String
	Public Mod1, Mod2, Mod3, Mod4, Mod5, Mod6 As Int
	Public HasData As Boolean
	
	Type theBillPeriod (Year As Double, Month As Int, MonthName As String, Period As String)
	Public BillYear As Double
	Public BillMonth As Int
	Public BillMonthName As String
	Public BillPeriod As String
	
	Public BookID As Int
	Public BookPCA As Double
	
	Public SeptageRateID, SeptageMinCuM, SeptageMaxCuM As Int
	Public SeptageRateType As String
	Public SeptageRatePerCuM As Double
	
	Public QuestionIcon = $"ic_help_outline_black_36dp"$ As String
	Public InfoIcon = $"ic_info_black_36dp"$ As String
	Public WarningIcon = $"ic_warning_black_36dp2"$ As String
	Public SettingsIcon = $"ic_settings_black_36dp"$ As String
	Public PriColor = 0xFF088AEB As Double
	
	Public CSTitle As CSBuilder
	Public CSSubTitle As CSBuilder
	
	Public SF As StringFunctions
	Public AdminPassword As String = "MRAndroid"
	
	Public BillID As Double
	Public strBookSequence As String
	Public isAverage As Int
	Public blnAverage As Boolean
	Public iReportLayout As Int
	Public ReportLayout As String
	
	Public iReaderID As Int
	Public CamAcctNo As String
	Public CamMeterNo As String
	
	Public SeptageProv As Int
	Public SeptageLogo As String
	
	Public Font As Typeface = Typeface.LoadFromAssets("myfont.ttf")
	Public FontBold As Typeface = Typeface.LoadFromAssets("myfont_bold.ttf")

End Sub