<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use App\Http\Traits\CustAdvPaymentTrait;
use App\Http\Traits\BillsTrait;
use App\Http\Traits\Functions;
use App\Http\Traits\SettingsTrait;
use App\Http\Traits\EmployeesTrait;
use App\Http\Traits\TellerTrait;
use App\Http\Traits\CustomerTrait;

use DB;
use Response;
use Carbon\Carbon;

class MeterReadingController extends Controller
{

    use CustAdvPaymentTrait;
    use BillsTrait;
    use Functions;
    use SettingsTrait;
    use EmployeesTrait; 
    use TellerTrait;
    use CustomerTrait;

    private function getRates($branch_id){      
      $query = DB::select("SELECT t.* FROM 
                              (select rh.id,
                                      rh.branch_id,
                                      rh.class,
                                      rh.sub_class,
                                      rh.is_active,
                                      rh.date_from,
                                      rh.date_to,
                                      concat(rh.class,'-',rh.sub_class) as rate_class, 
                                      rh.date_from as rate_cutoff 
                              from rates_header rh 
                              where rh.branch_id = '$branch_id' 
                              order by rate_class asc, rate_cutoff desc 
                              limit 18446744073709551615) t 
                            GROUP BY  t.rate_class
                         ");
      return $query;
    }

    public function getRatesHeader(Request $request){
      $bid    = $request->input('BranchID');
      $header = $this->getRates($bid);
      return Response::json($header);
    }

    public function getRatesDetails(Request $request){
      $bid    = $request->input('BranchID');
      $header = $this->getRates($bid);

      $ratesArray = [];
      foreach($header as $h){
        $details = DB::table('rates_details')
                    ->select(                      
                        'header_id',
                        'from AS rangefrom',
                        'to AS rangeto',
                        'type AS typecust',
                        'amount'
                      )
                    ->where('header_id', $h->id)
                    ->get();

        array_push($ratesArray, $details);
      }

      $ratesDetails = array_collapse($ratesArray);
      return Response::json($ratesDetails);
    }

    public function getUserAccess(Request $request) {

      $uname = $request->input('UserName');
      $upw = $request->input('UserPassword');

      $query  = DB::select("SELECT
                    Users.UserID AS UserID, Users.EmpID AS EmpID,
                    Users.UserName AS UserName, Users.UserPassword AS UserPassword,
                    Employees.FullName AS FullName,
                    Employees.BranchID AS BranchID,
                    Branches.description AS BranchName,
                    Employees.IsMultiPos AS IsMultiPos,
                    UserModules.Module1 AS Mod1,
                    UserModules.Module2 AS Mod2,
                    UserModules.Module3 AS Mod3,
                    UserModules.Module4 AS Mod4,
                    UserModules.Module5 AS Mod5,
                    UserModules.Module6 AS Mod6,
                    UserModules.Module7 AS Mod7
                    FROM tblusers AS Users
                    INNER JOIN tblusermodules AS UserModules ON Users.UserID = UserModules.UserID
                    INNER JOIN tblemployees AS Employees ON Users.EmpID = Employees.EmpID
                    INNER JOIN branches AS Branches ON Employees.BranchID = Branches.id
                    WHERE Users.UserName = '$uname'
                    AND Users.UserPassword = '$upw'");

        return Response::json($query);
    }

    public function getEmployeeInfo(Request $request) {

      $uid = $request->input('UserID');
      $bid = $request->input('BranchID');

      $query  = DB::select("SELECT
                            users.id AS UserID,
                            users.branch_id AS BranchID,
                            users.name AS EmpName,
                            users.username AS UserName,
                            users.androidpassword AS UserPassword,
                            tblusermodules.Module1 AS Module1,
                            tblusermodules.Module2 AS Module2,
                            tblusermodules.Module3 AS Module3,
                            tblusermodules.Module4 AS Module4,
                            tblusermodules.Module5 AS Module5,
                            tblusermodules.Module6 AS Module6,
                            tblusermodules.Module7 AS Module7
                            FROM users
                            INNER JOIN tblusermodules ON users.id = tblusermodules.UserID
                            WHERE users.id = $uid
                            AND users.branch_id = $bid");

        return Response::json($query);
    }    

    public function getUserModules(Request $request) {

      $uid = $request->input('UserID');

      $query  = DB::select("SELECT UserModules.UserID AS UserID,
                            UserModules.Module1 AS Mod1,
                            UserModules.Module2 AS Mod2,
                            UserModules.Module3 AS Mod3,
                            UserModules.Module4 AS Mod4,
                            UserModules.Module5 AS Mod5,
                            UserModules.Module6 AS Mod6,
                            UserModules.Module7 AS Mod7
                            FROM tblusermodules AS UserModules
                            WHERE UserModules.UserID = $uid");

        return Response::json($query);
    }

    public function getHeader(Request $request){
      $branchId = $request->input('branchID');
      $userId   = $request->input('userID');
      $presDate = $request->input('rdgDate');

      $cutOffDay = DB::table('tblsysparam')->where('branch_id', $branchId)->value('billing_cutoff');      

      $header = DB::table('book_assignment AS ba')
                ->select(
                          'ba.branch_id           AS    BranchID',
                          'ba.bill_month          AS    BillMonth',
                          'ba.bill_year           AS    BillYear',
                          'ba.book_id             AS    BookID',
                          'bo.code                AS    BookNo',
                          'bo.code                AS    BatchNumber',
                          'bo.description         AS    BookDescription',
                          DB::raw(" 'RB'          AS    BatchType"),                          
                          DB::raw("DATE_FORMAT(ba.previous_rdg_date, '%m/%d/%Y')   AS    PrevRdgDate"),
                          DB::raw("DATE_FORMAT(ba.rdg_date, '%m/%d/%Y')            AS    PresRdgDate"),
                          'ba.due_date            AS    DueDate',
                          DB::raw(" 'RB'          AS    DataSource"),
                          'ba.reader_id           AS    ReaderId',
                          DB::raw(" 0             AS    IsFinal"),
                          'ba.reader_id           AS    CreatedBy',
                          'ba.no_of_accts         AS    TotalCustomers',
                          DB::raw("DATE_FORMAT(ba.bill_period_from, '%m/%d/%Y')   AS    DateFrom"),
                          DB::raw("DATE_FORMAT(ba.bill_period_to, '%m/%d/%Y')     AS    DateTo"),
                          'ba.start_bill_no       AS    StartBillNo'
                  )
                ->join('books AS bo', 'bo.id', 'ba.book_id')
                ->where([
                          ['ba.branch_id', $branchId],
                          ['ba.reader_id', $userId],
                          ['ba.rdg_date',  $presDate],
                          ['bo.branch_id', $branchId]
                  ])
                ->orderBy('BookNo')
                ->get();

        return Response::json($header);
    }

    public function getDetails(Request $request){
      $branchId = $request->input('branchID');
      $userId   = $request->input('userID');
      $presDate = $request->input('rdgDate');

      $cutOffDay = DB::table('tblsysparam')->where('branch_id', $branchId)->value('billing_cutoff');   
      
      $header = DB::table('book_assignment AS ba')
                ->select(
                          'ba.branch_id           AS    BranchID',
                          'ba.bill_month          AS    BillMonth',
                          'ba.bill_year           AS    BillYear',
                          'ba.book_id             AS    BookID',
                          'ba.due_date            AS    DueDate',
                          'ba.rdg_date            AS    PresentRdgDate',
                          'ba.discon_date         AS    DisconnectionDate',
                          'ba.start_bill_no       AS    StartBillNo'
                  )
                ->where([
                          ['ba.branch_id', $branchId],
                          ['ba.reader_id', $userId],
                          ['ba.rdg_date',  $presDate]
                  ])
                ->orderBy('ba.book_code', 'asc')
                ->get();   

      $detailsArray = [];
      
      foreach($header as $h){

        $seq_no = $h->StartBillNo;

        // $BillingCutoff = date('Y-m-d', strtotime($h->BillYear .'-'. $h->BillMonth .'-'. $cutOffDay));

        // // if($branchId == 14 || $branchId == 62 || $branchId == 19 || $branchId == 48){
        // if( date('d') > date('d', strtotime($BillingCutoff)) ){
        //   $y = date('Y', strtotime($BillingCutoff));
        //   $m = date('m', strtotime($BillingCutoff));
        //   $d = date('d', strtotime($BillingCutoff));
        //   $dt = Carbon::create($y, $m, $d, 0);
        //   // $dt = $dt->addMonth();
        //   if(date("Y-m", strtotime($BillingCutoff)) == date("Y-m", strtotime($dt)) ){
        //     //
        //   }else{
        //     $dt = $dt->addMonth();
        //   }      
                              
        //   $BillingCutoff = $dt;
        // }

        $BillingCutoff = date('Y-m-d', strtotime( $h->BillYear .'-'. $h->BillMonth .'-'. $cutOffDay));

        $rdgDate = DB::table("book_assignment")
                    ->where([
                                ["bill_month", $h->BillMonth],
                                ["bill_year",  $h->BillYear]
                        ])
                    ->orderBy("rdgDay", "asc")                  
                    ->select(
                                DB::raw("DAY(rdg_date) AS rdgDay"),
                                DB::raw("MONTH(rdg_date) AS rdgMonth"),
                                DB::raw("YEAR(rdg_date) AS rdgYear")
                        )
                    ->first();        

        $rdgDay    = $rdgDate ? $rdgDate->rdgDay   : date('d');
        $rdgMonth  = $rdgDate ? $rdgDate->rdgMonth : date('m');
        $rdgYear   = $rdgDate ? $rdgDate->rdgYear  : date('Y');
        $READINGDT = date('Y-m-d', strtotime($rdgYear.'-'.$rdgMonth.'-'.$rdgDay));

        if( $rdgDay > date('d', strtotime($BillingCutoff)) ){
            $y = date('Y', strtotime($BillingCutoff));
            $m = date('m', strtotime($BillingCutoff));
            $d = date('d', strtotime($BillingCutoff));
            $dt = Carbon::create($y, $m, $d, 0);

            if( date("Y-m", strtotime($BillingCutoff)) == date("Y-m", strtotime($READINGDT)) ){
                
            }else{
                $dt = $dt->addMonth();
            }          
            
            $BillingCutoff = $dt;
        }

        $BillingCutoff = date("Y-m-d", strtotime($BillingCutoff));



        
        $settings      = isset($settings) ? $settings : $this->UseSettings($branchId, $h->BillYear, $h->BillMonth);
        $senior        = $settings['senior'];

        $details = DB::table('cust_acc AS ca')
                  ->select(
                            'ca.book_id                                             AS      BookID',
                            'b.code                                                 AS      BookCode',                            
                            'ca.id                                                  AS      AcctID',                            
                            'ca.acct_number                                         AS      AcctNo',
                            DB::raw("IFNULL(ca.acct_number_old, ca.acct_number)     AS      OldAcctNo"),
                            DB::raw("UPPER(IF(LENGTH(TRIM(cu.business_name)) > 0, cu.business_name, concat(cu.lastname,', ',cu.firstname,' ',cu.middlename))) AS AcctName"),
                            DB::raw("IF( LENGTH(TRIM(ca.address)) > 0, CONCAT(ca.address,', ',gb.name,', ',gc.name,', ',gp.name), CONCAT(gb.name,', ',gc.name,', ',gp.name)) AS AcctAddress"),
                            'ca.acct_class                                          AS      AcctClass',
                            'ca.acct_subclass                                       AS      AcctSubClass',                            
                            'ca.account_status                                      AS      AcctStatus',
                            'ca.meter_id                                            AS      MeterID',
                            'wm.meter_no                                            AS      MeterNo',
                            'wm.max_reading                                         AS      MaxReading',
                            // 'ca.sequence                                            AS      SeqNo',
                            DB::raw("IFNULL(ca.sequence, 0)                         AS      SeqNo"),
                            'ca.sc_discount                                         AS      IsSenior',                             
                            DB::raw("DATE_FORMAT(ca.rdg_previous_date, '%m/%d/%Y')  AS      PrevRdgDate"),
                            'ca.rdg_previous                                        AS      PrevRdg',    
                            'ca.rdg_final                                           AS      FinalRdg',
                            DB::raw("DATE_FORMAT(ca.date_dc, '%m/%d/%Y')            AS      DisconDate"),
                            'ca.cum_previous                                        AS      PrevCum',                            
                            'ca.cum_average                                         AS      AveCum', 
                            'ca.addtl_cons                                          AS      AddCum',                              
                            'ca.adv_payment                                         AS      AdvPayment',
                            'ca.gdeposit                                            AS      GDeposit',
                            DB::raw("IFNULL(ca.rdg_sequence, ca.sequence)           AS      RdgSequence"),
                            'ca.with_penalty                                        AS      WithDueDate',
                            'ca.promo_id                                            AS      PromoId',
                            DB::raw("IF(ca.has_meter_charges=1,ca.amt_meter_charges, 0) AS  MeterCharges"),
                            DB::raw("IF(ca.has_franchise_tax=1,ca.pct_franchise_tax, 0) AS  FranchiseTaxPct"),
                            "ca.has_septage_fee                                         AS  HasSeptageFee"
                    )
                  ->join('cust AS cu', 'cu.id', 'ca.cust_id')
                  ->join('geo_brgys AS gb', 'ca.brgy_id', 'gb.id')
                  ->join('geo_cities AS gc', 'ca.city_id', 'gc.id')
                  ->join('geo_provinces AS gp', 'ca.province_id', 'gp.id')
                  ->join('wmeters AS wm', 'ca.meter_id', 'wm.id')
                  ->join('books AS b', 'b.id', 'ca.book_id')
                  ->where([
                            ['ca.book_id', $h->BookID],   
                            ['ca.date_ac', '<=', $BillingCutoff],
                            ['ca.branch_id', $branchId]
                    ])
                  ->whereIn('ca.account_status', ['ac','dc'])
                  ->orderBy('ca.sequence')
                  ->get();      

        foreach($details as $d){
            $atb        = $this->add_to_bill($d->AcctID);
            $billPeriod = date('Y-m-d', strtotime($h->BillYear . '-' . $h->BillMonth . '-01'));
            $billTable  = DB::table('bills_details AS bd')
                          ->select(
                                'bd.is_pn',
                                'bd.balance',
                                'bd.penalty',
                                'bd.total_due',
                                'bd.amount_paid',
                                'bd.advance',
                                'bd.senior',
                                'bd.discount',
                                'bd.tax_2307',
                                'bd.tax_2306',
                                'bd.due_date'
                            )
                          ->join('bills_header AS bh', 'bh.id', 'bd.batch_id')
                          ->where([
                                    ['bd.account_id', $d->AcctID],
                                    ['bd.status', 'UP']
                            ])
                          ->whereRaw("DATE_FORMAT(concat(bh.bill_year,'-',bh.bill_month,'-01'), '%Y-%m-%d') < '$billPeriod' " )
                          ->get();

            $q_arrears   = 0;
            $currentDate = date('Y-m-d');

            foreach($billTable as $bt){
              $curPenalty = 0;
              if($bt->is_pn==1){
                $curPenalty = $bt->penalty;
              }else{                
                if( $currentDate > date('Y-m-d', strtotime($bt->due_date)) && $bt->due_date != null ){
                  $curPenalty = $bt->penalty;
                }
              }
              $curBalance = $bt->total_due + $curPenalty - $bt->amount_paid - $bt->advance - $bt->senior - $bt->discount - $bt->tax_2307 - $bt->tax_2306;
              $curBalance = $curBalance < 0 ? 0 : $curBalance;
              $q_arrears += $curBalance;      
            } 

            // $acctQuery = DB::table("bills_details AS bd")
            //                     ->join("bills_header AS bh", "bh.id", "bd.batch_id")
            //                     ->where([
            //                               ["bh.branch_id",  $branchId],
            //                               ["bd.account_id", $d->AcctID]
            //                       ])
            //                     ->select(
            //                               DB::raw("DATE_FORMAT(bh.present_rdg_date, '%m/%d/%Y') AS PrevRdgDate")
            //                       )
            //                     ->orderBy("bh.present_rdg_date", "desc")
            //                     ->first();

            // $d->PrevRdgDate = is_null($acctQuery) ? $d->PrevRdgDate : $acctQuery->PrevRdgDate;

            $year   = date('Y', strtotime($d->PrevRdgDate));
            $month = date('m', strtotime($d->PrevRdgDate));
            $day   = date('d', strtotime($d->PrevRdgDate));
            $dt = Carbon::create($year, $month, $day, 0);
            $dt = $dt->addDay();

            $billPeriodFrom = $dt->toDateString();    

            // $promoPenalty  = $d->PromoId ? 0.10 : 0;
            $promoPenalty  = $d->PromoId ? $this->GetCustomerPromoPenalty($d->AcctID, $h->PresentRdgDate) : 0;
            $branchPenalty = $d->WithDueDate == 1 ? $settings['penalty'] : 0;
            $totalPenalty  = $promoPenalty + $branchPenalty;

            if($d->PromoId){
              $d->WithDueDate = 1;
            }

            $d->BillNo            = $this->GetBillNo($seq_no);
            $d->BillYear          = $h->BillYear;
            $d->BillMonth         = $h->BillMonth;             
            $d->BranchID          = $h->BranchID;
            $d->SeniorOnBefore    = $senior['before'];
            $d->SeniorAfter       = $senior['after'];
            $d->SeniorMaxCum      = $senior['max_cum'];            
            $d->PresRdgDate       = date('m/d/Y', strtotime($h->PresentRdgDate));
            $d->DateFrom          = date('m/d/Y', strtotime($billPeriodFrom));
            $d->DateTo            = date('m/d/Y', strtotime($h->PresentRdgDate));
            $d->DueDate           = $h->DueDate ? date('m/d/Y', strtotime($h->DueDate)) : null;
            $d->DisconnectionDate = date('m/d/Y', strtotime($h->DisconnectionDate));
            $d->BillType          = 'RB';             
            $d->AddToBill         = $atb['amount'];
            $d->AtbRef            = serialize($atb['reference']);
            $d->Arrears           = $q_arrears ? $q_arrears : 0;
            $d->PenaltyPct        = $totalPenalty;
            $d->AcctAddress       = $d->AcctAddress;
            $d->FranchiseTaxPct   = $d->FranchiseTaxPct / 100;

            $d->MaxSeptageCum  = (strtolower($d->AcctClass) == "com") ? 50 : 30;
            

            $currentBillPeriod = date("Y-m-d", strtotime($h->BillYear . '-' . $h->BillMonth . '-01'));
            $cumHistory = DB::table("bills_details AS bd")
                            ->select(
                                      "bd.total_cum",
                                      DB::raw("DATE_FORMAT(CONCAT(bh.bill_year,'-',bh.bill_month,'-01'), '%Y-%m-%d') AS bill_period"),
                                      "bd.account_id"
                              )      
                            ->join("bills_header AS bh", "bh.id", "bd.batch_id")
                            ->where("bd.account_id", $d->AcctID)
                            ->havingRaw("bill_period < '$currentBillPeriod'")
                            ->orderBy("bill_period", "desc")
                            ->take(3)
                            ->get();

            $cumHistory    = $cumHistory->sortBy("bill_period");
            $periodHistory = $cumHistory->pluck("bill_period");
            $cumHistory    = $cumHistory->pluck("total_cum");

            $d->PrevCum1st = isset($cumHistory[0]) ? $cumHistory[0] : "";
            $d->PrevCum2nd = isset($cumHistory[1]) ? $cumHistory[1] : "";
            $d->PrevCum3rd = isset($cumHistory[2]) ? $cumHistory[2] : "";

            $d->BillPeriod1st = isset($periodHistory[0]) ? date("M Y",strtotime($periodHistory[0])) : "";
            $d->BillPeriod2nd = isset($periodHistory[1]) ? date("M Y",strtotime($periodHistory[1])) : "";
            $d->BillPeriod3rd = isset($periodHistory[2]) ? date("M Y",strtotime($periodHistory[2])) : "";
            
            $seq_no++;
        }   // end foreach:$details           

        array_push($detailsArray, $details->sortBy('RdgSequence'));
      } // end foreach:$header

      $details = collect(array_collapse($detailsArray));
      return Response::json($details);

    }

    public function GetBillNo($seq_no){
        $max     = '0000000';
        $len     = strlen($seq_no);
        $bill_no = substr($max, $len) . $seq_no;
        return $bill_no;      
    }

    public function getBooks(Request $request){
      $bid = $request->input('BranchID');
      
      $query  = DB::select("SELECT
                    Books.branch_id AS BranchID,
                    Books.id AS BookID,
                    Books.code AS BookCode,
                    Books.description AS BookDesc, 
                    Books.no_duedate AS NoDueDate,
                    Books.with_pca AS WithPCA
                    FROM books AS Books
                    WHERE Books.branch_id = $bid");

      // $response->header('charset', 'utf-8');
      // return Response::json($query, JSON_UNESCAPED_UNICODE);
      return Response::json($query);
    }

    public function getBookAssignments(Request $request){
      $bid = $request->input('BranchID');
      $byr = $request->input('BillYear');
      $bmo = $request->input('BillMonth');
      $uid = $request->input('ReaderID');
      $rdgdate = $request->input('rdgDate');

      $query  = DB::select("SELECT
                    BookAssignment.branch_id AS BranchID,
                    BookAssignment.bill_year AS BillYear,
                    BookAssignment.bill_month AS BillMonth,
                    BookAssignment.book_id AS BookID,
                    Book.code AS BookCode,
                    Book.description AS BookDesc,
                    BookAssignment.no_of_accts AS NoOfAccts,
                    BookAssignment.previous_rdg_date AS PrevRdgDate,
                    BookAssignment.last_billno AS LastBillNo,
                    BookAssignment.reader_id AS UserID,
                    BookAssignment.rdg_date AS PresRdgDate,
                    BookAssignment.bill_period_from AS BillPeriodFrom,
                    BookAssignment.bill_period_to AS BillPeriodTo,
                    BookAssignment.due_date AS DueDate
                    FROM book_assignment AS BookAssignment
                    INNER JOIN books AS Book ON Book.id = BookAssignment.book_id
                    WHERE BookAssignment.branch_id = $bid
                    AND BookAssignment.bill_year = $byr
                    AND BookAssignment.bill_month = $bmo
                    AND BookAssignment.reader_id = $uid
                    AND BookAssignment.rdg_date = '$rdgdate'");

      return $query;        
    }

    public function getPCA(Request $request) {
      $bid = $request->input('BranchID');
      $dcutoff = $request->input('CutOff');

      $query  = DB::select("SELECT pca.*
                    FROM(SELECT max(cutoff) AS max_cutoff, book_id
                    FROM pca
                    WHERE branch_id = $bid
                    AND cutoff <= '$dcutoff'
                    GROUP BY book_id
                    ORDER BY book_id ASC, cutoff DESC) t
                    INNER JOIN pca ON t.book_id = pca.book_id
                    AND t.max_cutoff = pca.cutoff
                    WHERE pca.branch_id = $bid
                    ORDER BY pca.book_id ASC");

      return Response::json($query);
    }

    public function getBranches(){
      $query  = DB::select("SELECT
                    Branch.id AS BranchID,
                    Branch.code AS BranchCode,
                    Branch.company_id AS CompanyID,
                    Branch.description AS BranchName,
                    Branch.address AS BranchAddress,
                    Branch.contact_no AS ContactNo,
                    Branch.email AS Email,
                    Branch.tin AS TinNo,
                    Branch.nfee AS NotarialFee
                    FROM
                    branches AS Branch");

      // $response->header('charset', 'utf-8');
      // return Response::json($query, JSON_UNESCAPED_UNICODE);
      return Response::json($query);
    }

    public function getBranchInfo(Request $request){
      $uid = $request->input('UserID');
      $query  = DB::select("SELECT branches.id AS BranchID, branches.code AS BranchCode, branches.company_id AS CompanyID,
                            branches.description AS BranchName, branches.address AS BranchAddress,
                            branches.contact_no AS BranchContact, branches.email AS BranchEmail,
                            branches.tin AS TinNo, branches.nfee AS NotarialFee
                            FROM tblusers
                            INNER JOIN tblemployees ON tblusers.EmpID = tblemployees.EmpID
                            INNER JOIN branches ON tblemployees.BranchID = branches.id
                            WHERE tblusers.UserID = $uid");

      // $response->header('charset', 'utf-8');
      // return Response::json($query, JSON_UNESCAPED_UNICODE);
      return Response::json($query);
    }

    public function updateUserAccount(Request $request){
      // $empid = $request->input('empid');
      // $uid = $request->input('uid');

      // $uname = $request->input('uname');
      // $upassword = $request->input('upassword');

      // DB::table('tblusers')
      //   ->where([['EmpID',$empid], ['UserID',$uid]])
      //   ->update(['UserName'=>$uname, 'UserPassword'=>$upassword]);
      $uid = $request->input('uid');
      $uname = $request->input('username');
      $androidpassword = $request->input('androidpassword');

      DB::table('users')->where('id', $uid)->update(['username'=>$uname, 'androidpassword'=>$androidpassword]);      
    }
 
    public function checkDuplicate(Request $request){
      $uid = $request->input('uid');
      $uname = $request->input('uname');
      $upassword = $request->input('upassword');
      $query= DB::select("SELECT Users.id, Users.username, Users.androidpassword
                          FROM users AS Users
                          WHERE (Users.username = '$uname' OR Users.androidpassword = '$upassword')
                          AND Users.id <> $uid");
      // return $query;
      if ($query){
          return "Duplicate"; }
      else {
            DB::table('users')->where('id', $uid)->update(['username'=>$uname, 'androidpassword'=>$androidpassword]); }
    }

    public function addBook(Request $request){
      $branchId = $request->input('branch_id');
      $bookCode = $request->input('book_code');
      $bookDesc = $request->input('book_desc');

      $id = DB::table('books')->insertGetId([
                'code' => $bookCode,
                'branch_id' => $branchId,
                'description' => $bookDesc
                ]);

      return $id;
    }

    public function postJson(Request $request){
      $data = $request->input('data');
      $decoded=json_decode($data,true);
      DB::table('books')->insert($decoded);
    }

    private function is_date( $str ) {
        try {
            $dt = new DateTime( trim($str) );
        }
        catch( Exception $e ) {
            return false;
        }
        $month = $dt->format('m');
        $day = $dt->format('d');
        $year = $dt->format('Y');
        if( checkdate($month, $day, $year) ) {
            return true;
        }
        else {
            return false;
        }
    }    

    public function uploadReadings(Request $request){
        $content = $request->getContent();
        $data    = json_decode($content, true);

        $header                = $data[0]['Header'][0];
        $header['due_date']    = strtotime($header['due_date']) <= 0 ? null : date('Y-m-d', strtotime($header['due_date']));
        $header['data_source'] = 'RB';
        $header['is_final']    = 1;
        $header['created_by']  = $header['reader_id'];

        $details = $data[0]['Details'];

        $ssm = DB::table("branches")->where("id", $header["branch_id"])->value("septage_fee");
        
        DB::beginTransaction();
        try{

          $headerExists = DB::table('bills_header')
                           ->select('id')
                           ->where([
                                      ['branch_id',  $header['branch_id']],
                                      ['bill_month', $header['bill_month']],
                                      ['bill_year',  $header['bill_year']],
                                      ['book_id',    $header['book_id']],                                      
                                      ['batch_type', 'RB']
                            ])
                           ->first();

          if($headerExists){
            $batchId = $headerExists->id;
          }else{
            $batchId = DB::table('bills_header')->insertGetId($header);
          }          

          foreach($details as $d){            

            $isUploaded = DB::table("bills_details AS bd")
                            ->join("bills_header AS bh", "bh.id", "bd.batch_id")
                            ->where([
                                      ["bd.account_id", $d["account_id"]],
                                      ["bh.bill_month", $header["bill_month"]],
                                      ["bh.bill_year" , $header["bill_year"]]
                              ])
                            ->select("bd.id")
                            ->first();

            if($isUploaded){
              // skip
            }else{    
                              
                $d['batch_id'] = $batchId;
                $accountId     = $d['account_id'];

                $custAcct = DB::table('cust_acc')->where('id', $accountId)->first();

                $custname = DB::table("cust")
                             ->where("id", $custAcct->cust_id)
                             ->select(
                                 DB::raw("IF(LENGTH(business_name) > 0, business_name, CONCAT(lastname,', ',firstname,' ',middlename)) AS custname")
                              )
                             ->value("custname");

                $d['senior']   = round($d['senior'],2);
                $advance       = $custAcct->adv_payment;
                $totalDue      = round($d['total_due'],2) - $d['senior'];
                $d['advance']  = $advance >= $totalDue ? $totalDue : $advance;
                $atb           = unserialize($d['atb_reference']);
                $maxReading    = $d['max_reading'];
                $d['due_date'] = $header['due_date'];
                $d['previous_rdg_date'] = date('Y-m-d', strtotime($d['previous_rdg_date']));

                $d['balance'] = round($totalDue,2) - round($d['advance'],2);
                $billStatus   = round($d['balance'],2) > 0 ? 'UP' : 'P';
                $d['status']  = $billStatus;

                $billsDetails = array_except($d, ['rdg_sequence','max_reading']);

                // INSERT TO BILLS DETAILS
                $detailsId = DB::table('bills_details')->insertGetId($billsDetails);
                
                if($ssm == 1 && $d["status"] == 'P'){
                    $paidSeptage_data = [
                      "bill_id"           => $detailsId,
                      "bill_number"       => $d["bill_number"],
                      "bill_type"         => $d["bill_type"],
                      "bill_month"        => $header["bill_month"],
                      "bill_year"         => $header["bill_year"],
                      "account_id"        => $accountId,
                      "account_no"        => $custAcct->acct_number,
                      "customer_name"     => $custname,
                      "with_septic_tank"  => $custAcct->with_septic_tank,
                      "account_class"     => $d["acct_class"],
                      "total_cum"         => $d["total_cum"],
                      "septage_fee"       => $d["septage_fee"],
                      "collection_date"   => date("Y-m-d"),
                      "collection_id"     => 0,
                      "source"            => "advance payment"
                    ];

                    DB::table("paid_septage")->insert($paidSeptage_data);
                }

                // UPDATE ADD CONS
                DB::table('cust_acc')->where('id', $accountId)->update(['addtl_cons' => 0]);

                // UPDATE READING SEQUENCE
                DB::table('cust_acc')->where('id', $accountId)->update(['rdg_sequence' => $d['rdg_sequence']]);

                // UPDATE ADVANCE PAYMENT
                if($advance > 0){
                  $useAdvPayment = $advance >= $totalDue ? $totalDue : $advance;
                  $this->useAdvPayment($accountId, 'bill', $detailsId, $useAdvPayment, $header['branch_id'], $header['reader_id']);
                }            

                // UPDATE ADD TO BILL RECORD
                if($atb) {
                  foreach($atb as $o){
                      $id     = $o['id'];
                      $amount = $o['atb'] ? $o['atb'] : 0;
                      DB::table('cust_acc_payables')->where('id', $id)->increment('amt_paid', $amount);
                      DB::table('cust_acc_payables')->where('id', $id)->decrement('amt_remaining', $amount);
                      $this->UpdateTermsRemaining($id);                      
                  }         
                }

                // METER LOOP
                if($d['present_rdg'] > $maxReading){
                    DB::table('wmeters')->where('id', $d['meter_id'])->increment('loop_counter');
                    DB::table('wmeters_loop_history')->insert([
                            'meter_id'      => $d['meter_id'],
                            'reference'     => 'bill',
                            'reference_id'  => $detailsId,
                            'reference_no'  => $d['bill_number']
                        ]);
                }
            } // isUploaded
          } // foreach $details

          DB::commit();
          return [
            'status'  => 200,
            'message' => 'Successfully Uploaded',
            'data'    => $batchId
          ];
        }catch(Exception $e){
          echo $e->getMessage();
          DB::rollback();
        }
    }       

    public function index(){
      return view('mreading.index');
    }

    public function extract_data(){
      $query = DB::table('excel_import AS ex')
                // ->leftJoin('books AS bo', 'bo.code', 'ex.book_no')
                ->leftJoin('android_bills_header AS bh', 'bh.batch_number', 'ex.book_no')
                ->select(
                          'ex.book_no AS BookCode',
                          'ex.sequence AS SeqNo',
                          'ex.account_no',
                          'ex.customer_name AS AcctName',
                          'ex.address AS AcctAddress',                       
                          'ex.meter_no AS MeterNo',
                          'ex.class AS AcctClass',
                          'ex.present_date',
                          'ex.previous_date',
                          'ex.present_rdg',
                          'ex.previous_rdg AS PrevRdg',
                          'ex.actual_cum',
                          'ex.add_cons AS AddCum',
                          'ex.previous_cum AS PrevCum',
                          'ex.atb AS AddToBill',
                          'ex.basic',
                          'ex.penalty',
                          'ex.others',
                          'bh.book_id AS BookID',
                          'bh.id AS HeaderID',
                          'ex.id AS AcctID',
                          DB::raw("CONCAT(ex.book_no,'-',ex.sequence,'-',ex.account_no) AS AcctNo"),
                          'bh.due_date AS DueDate',
                          'bh.reader_id AS ReaderID'
                  )
                ->orderBy('ex.book_no')
                ->get();

      $branchId         = 29;
      $billMonth        = 12;
      $billYear         = 2017;
      $previousRdgDate  = '2017-12-11';
      $presentRdgDate   = '2018-01-11';

      // $books = DB::table('excel_import AS ex')
      //           ->leftJoin('books AS bo', 'bo.code', 'ex.book_no')
      //           ->select('bo.id', 'bo.code')
      //           ->groupBy('bo.id')
      //           ->get();

      // DB::beginTransaction();
      // try{
      //   foreach($books as $b){
      //     $headerData = [
      //       'branch_id'         =>  $branchId,
      //       'bill_month'        =>  $billMonth,
      //       'bill_year'         =>  $billYear,
      //       'book_id'           =>  $b->id,
      //       'batch_number'      =>  $b->code,
      //       'batch_type'        =>  'RB',
      //       'previous_rdg_date' =>  $previousRdgDate,
      //       'present_rdg_date'  =>  $presentRdgDate,
      //       'data_source'       =>  'RB'
      //     ];   

      //     DB::table('android_bills_header')->insert($headerData);
      //   }
      //   DB::commit();
      // }catch(Exception $e){
      //   DB::rollback();
      // }


      DB::beginTransaction();
      try{
        foreach($query as $q){      
          $ACCOUNT_CLASS = explode('-', $q->AcctClass);

              $data = [
                'BillHeaderID'      =>    $q->HeaderID,
                'BillYear'          =>    $billYear,
                'BillMonth'         =>    $billMonth,
                'BranchID'          =>    $branchId,
                'BookID'            =>    $q->BookID,
                'BookNo'            =>    $q->BookCode,
                'AcctID'            =>    $q->AcctID,
                'AcctNo'            =>    $q->AcctNo,
                'OldAcctNo'         =>    $q->AcctNo,
                'AcctName'          =>    $q->AcctName,
                'AcctAddress'       =>    $q->AcctAddress,
                'AcctClass'         =>    $ACCOUNT_CLASS[0],                
                'AcctSubClass'      =>    $ACCOUNT_CLASS[1],
                'AcctStatus'        =>    'ac',
                'MeterID'           =>    0,
                'MeterNo'           =>    $q->MeterNo,
                'MaxReading'        =>    99999,
                'SeqNo'             =>    $q->SeqNo,
                'IsSenior'          =>    0,
                'PrevRdgDate'       =>    $previousRdgDate,
                'PrevRdg'           =>    $q->PrevRdg,
                'PrevCum'           =>    $q->PrevCum,
                'PresRdgDate'       =>    $presentRdgDate,
                'DateFrom'          =>    '2017-12-12',
                'DateTo'            =>    $presentRdgDate,
                'DueDate'           =>    $q->DueDate,
                'AveCum'            =>    0,
                'BillType'          =>    'RB',
                'AddCum'            =>    $q->AddCum,
                'AddToBillAmt'      =>    $q->AddToBill,
                'AtbRef'            =>    null,
                'ArrearsAmt'        =>    ($q->basic + $q->penalty + $q->others),
                'AdvPayment'        =>    0,
                'PenaltyPct'        =>    0.1,
                'ReadBy'            =>    $q->ReaderID
              ];

              DB::table('android_readings')->insert($data);

              DB::commit();
              // echo 'success';       
          }
      }catch(Exception $e){
        DB::rollback();
      }         
    }

    public function getUserAccount(Request $request) {

      $bid = $request->input('BranchID');
      $uname = $request->input('UserName');
      $upw = $request->input('UserPassword');

      $query  = DB::select("SELECT
                            Users.id,
                            Users.name,
                            Users.username,
                            Users.androidpassword,
                            Users.branch_id,
                            Branch.code,
                            Branch.company_id,
                            Branch.description,
                            Branch.address,
                            Branch.contact_no,
                            Branch.tin,
                            UserModule.Module1,
                            UserModule.Module2,
                            UserModule.Module3,
                            UserModule.Module4,
                            UserModule.Module5,
                            UserModule.Module6
                            FROM users AS Users
                            INNER JOIN branches AS Branch ON Users.branch_id = Branch.id
                            INNER JOIN tblusermodules AS UserModule ON Users.id = UserModule.UserID
                            WHERE Users.branch_id = $bid
                            AND Users.username = '$uname'
                            AND Users.androidpassword = '$upw'");

        return Response::json($query);
    }    

    public function getMeterReader(Request $request) {

      $branch_id = $request->input('BranchID');

      $query  = DB::select("SELECT
                            Reader.id AS ReaderID,
                            Reader.`name` AS ReaderName
                            FROM users AS Reader
                            INNER JOIN users_positions AS UsersPosition ON Reader.id = UsersPosition.user_id
                            WHERE Reader.branch_id = $branch_id
                            AND UsersPosition.position_desc = 'Meter Reader'");
        return Response::json($query);
    }    
}