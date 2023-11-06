<?php

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/
Auth::routes();

Route::get ("/",      	 "HomeController@index")->middleware('auth');
Route::get ("/index", 	 "HomeController@index")->middleware('auth');
Route::get ("/home",  	 "HomeController@index")->middleware('auth');
Route::get ("/print",    "HomeController@print_raw")->middleware('auth');

/////////////////////////////////////////////////////////////////////////////
/// CUSTOMER SERVICE
/////////////////////////////////////////////////////////////////////////////
Route::group([
	'prefix' 	 => 'csr', 
	'middleware' => [
		'auth', 
		'role:CUSTOMER SERVICE REPRESENTATIVE|ADMIN SUPERVISOR'
	]], 
	function() {
		Route::group(['prefix'=>'application'], function() {
			Route::get ("", 								"CsrApplicationController@index");
			Route::get ("dt",								"CsrApplicationController@dt");

			Route::get ("new", 								"CsrApplicationController@application_new");
			Route::post("save", 							"CsrApplicationController@application_save");

			Route::post("validate", 						"CsrApplicationController@validate_application");
			Route::get ("display-illegals",					"CsrApplicationController@display_illegals");
			
			Route::get ("edit", 							"CsrApplicationController@edit_application");
			Route::post("patch", 							"CsrApplicationController@patch_application");

			Route::get ("cancel", 							"CsrApplicationController@cancel_application_view");
			Route::post("cancel", 							"CsrApplicationController@cancel_application");

			Route::get ("details",							"CsrApplicationController@application_details");

			Route::get ("assessment",						"CsrApplicationController@assessment");
			Route::get ("assessment-get-details",			"CsrApplicationController@assessment_get_details");
			Route::get ("assessment-get-downpayment",		"CsrApplicationController@assessment_get_downpayment");
			Route::get ("assessment-ctype-fees",			"CsrApplicationController@assessment_ctype_fees");
			Route::get ("assessment-services-details",		"CsrApplicationController@assessment_services_details");
			Route::get ("assessment-promo-details",			"CsrApplicationController@assessment_promo_details");
			Route::get ("assessment-arrears",				"CsrApplicationController@assessment_arrears");
			Route::get ("assessment-account-arrears",		"CsrApplicationController@assessment_account_arrears");
			Route::post("assessment-save",					"CsrApplicationController@assessment_save");
			Route::get ("assessment-print",					"CsrApplicationController@assessment_print");
			Route::get ("assessment-materials",				"CsrApplicationController@materials_view");
			Route::get ("assessment-services",				"CsrApplicationController@services_view");

			Route::get ("view-application",					"CsrApplicationController@view_application");
			Route::get ("view-survey",						"CsrApplicationController@view_survey");

			Route::get ("additional-services",				"CsrApplicationController@additional_services");
			Route::post("additional-services",				"CsrApplicationController@additional_services_save");
			Route::get ("additional-materials",				"CsrApplicationController@additional_materials");
			Route::post("additional-materials",				"CsrApplicationController@additional_materials_save");

			Route::get ("change-connection-type",			"CsrApplicationController@change_conntype_view");
			Route::post("change-connection-type",			"CsrApplicationController@change_conntype_save");


			Route::get ("get-connection-type-fees",			"CsrApplicationController@get_connection_type_fees");
			
			Route::post("update-contract-number",			"CsrApplicationController@update_contract_number");
			Route::post("create-ncjo",						"CsrApplicationController@create_ncjo");
		});

		Route::group(['prefix'=>'customers'], function() {
			Route::get ("",								"CsrCustomerController@index");
			Route::get ("dt",							"CsrCustomerController@dt");
			Route::get ("customer",						"CsrCustomerController@customer");
			Route::get ("account",						"CsrCustomerController@account");
			Route::get ("account-new",					"CsrCustomerController@account_new");
			Route::post("account-new-save",				"CsrCustomerController@account_new_save");
			
			Route::get ("account-edit-bs",				"CsrCustomerController@account_edit_bs");
			Route::post("account-patch-bs",				"CsrCustomerController@account_patch_bs");

			Route::get ("account-add-gd",				"CsrCustomerController@account_add_gd_view");
			Route::post("account-add-gd",				"CsrCustomerController@account_add_gd");

			Route::get ("account-change-rate",			"CsrCustomerController@account_change_rate_view");
			Route::post("account-change-rate",			"CsrCustomerController@account_change_rate");

			Route::get ("account-edit-name",			"CsrCustomerController@account_edit_name");
			Route::post("account-patch-name",			"CsrCustomerController@account_patch_name");
			Route::get ("account-edit-addr-billing",	"CsrCustomerController@account_edit_addr_billing");
			Route::post("account-patch-addr-billing",	"CsrCustomerController@account_patch_addr_billing");
			Route::get ("account-edit-addr-service",	"CsrCustomerController@account_edit_addr_service");
			Route::post("account-patch-addr-service",	"CsrCustomerController@account_patch_addr_service");
			Route::get ("account-edit-tags",			"CsrCustomerController@account_edit_tags");
			Route::post("account-patch-tags",			"CsrCustomerController@account_patch_tags");
			Route::get ("account-jo-history", 			"CsrCustomerController@account_jo_history");

			Route::get ("account-mother-edit",			"CsrCustomerController@account_mother_edit");
			Route::post("account-mother-patch",			"CsrCustomerController@account_mother_patch");

			Route::get ("account-transfer-view",		"CsrCustomerController@account_transfer_view");
			Route::get ("account-transfer",				"CsrCustomerController@account_transfer");

			Route::get ("customer-illegal",				"CsrCustomerController@customer_illegal");
			Route::post("promissory-attachment",		"CsrCustomerController@promissory_attachment");
		});

		Route::group(['prefix'=>'job-order'], function() {
			Route::get ("",								"CsrJoController@index");
			Route::get ("dt",							"CsrJoController@dt");
			Route::get ("jo-new",						"CsrJoController@jo_new");
			Route::get ("jo-new-materials",				"CsrJoController@jo_materials_new");
			Route::get ("jo-new-services",				"CsrJoController@jo_new_services");
			Route::post("jo-save",						"CsrJoController@jo_save");
			Route::get ("jo-dispatch",					"CsrJoController@jo_dispatch_view");
			Route::post("jo-dispatch",					"CsrJoController@jo_dispatch_save");
			Route::get ("jo-cancel",					"CsrJoController@jo_cancel_view");
			Route::post("jo-cancel",					"CsrJoController@jo_cancel_save");
			Route::get ("jo-print",						"CsrJoController@jo_print");
			
			Route::get ("jo-materials-additional",		"CsrJoController@jo_materials_additional");
			Route::post("jo-materials-additional-save",	"CsrJoController@jo_materials_additional_save");
			Route::get ("jo-services-additional",		"CsrJoController@jo_services_additional");
			Route::post("jo-services-additional-save",	"CsrJoController@jo_services_additional_save");

			Route::get ("view",							"CsrJoController@jo_view");
			Route::get ("view-jo-materials",			"CsrJoController@jo_view_materials");
			Route::get ("view-jo-services",				"CsrJoController@jo_view_services");

			Route::get ("update",						"CsrJoController@update_jo");
			Route::get ("save",							"CsrJoController@save_jo");

			Route::group(['prefix'=>'findings'], function() {
				Route::get ("",					"CsrJoController@jo_findings_view");
				Route::post("save",				"CsrJoController@jo_findings_save");


				Route::get ("nc",				"CsrJoController@nc_findings");
				Route::post("nc/submit",		"CsrJoController@nc_findings_submit");

				Route::get ("dc-da",			"CsrJoController@dcda_findings");
				Route::post("dc-da/submit",		"CsrJoController@dcda_findings_submit");

				Route::get ("dc-cr",			"CsrJoController@dccr_findings");
				Route::post("dc-cr/submit",		"CsrJoController@dccr_findings_submit");

				Route::get ("rc",				"CsrJoController@rc_findings");
				Route::post("rc/submit",		"CsrJoController@rc_findings_submit");

				Route::get ("cm",				"CsrJoController@cm_findings");
				Route::post("cm/submit",		"CsrJoController@cm_findings_submit");

				Route::get ("mc",				"CsrJoController@mc_findings");
				Route::post("mc/submit",		"CsrJoController@mc_findings_submit");

				Route::get ("sl",				"CsrJoController@sl_findings");
				Route::post("sl/submit",		"CsrJoController@sl_findings_submit");

				Route::get ("rm",				"CsrJoController@rm_findings");
				Route::post("rm/submit",		"CsrJoController@rm_findings_submit");

				Route::get ("sas",				"CsrJoController@sas_findings");
				Route::post("sas/submit",		"CsrJoController@sas_findings_submit");

				Route::get ("cac",				"CsrJoController@cac_findings");
				Route::post("cac/submit",		"CsrJoController@cac_findings_submit");

				Route::get ("ic",				"CsrJoController@ic_findings");
				Route::get ("ic/compute",		"CsrJoController@ic_compute");
				Route::get ("ic/compute/basic",	"CsrJoController@ic_compute_basic");
				Route::post("ic/submit",		"CsrJoController@ic_findings_submit");

				Route::get ("ot",				"CsrJoController@ot_findings");
				Route::post("ot/submit",		"CsrJoController@ot_findings_submit");
			});
		});

		Route::group(['prefix'=>'add-to-bill'], function() {
			Route::get ("",	    				"CsrAddToBillController@index");
			Route::get ("dt",	    			"CsrAddToBillController@dt");
			Route::get ("atb-new",	    		"CsrAddToBillController@atb_new");
			Route::post("atb-save",	    		"CsrAddToBillController@atb_save");
			Route::get ("atb-edit",	    		"CsrAddToBillController@atb_edit");
			Route::post("atb-patch",	    	"CsrAddToBillController@atb_patch");
			Route::post("atb-cancel",	    	"CsrAddToBillController@atb_cancel");
		});	

		Route::group(['prefix'=>'bill-promissory'], function() {
			Route::get ("",						"CsrPromissoryController@index");
			Route::get ("new",					"CsrPromissoryController@new_promissory");
			Route::get ("acct-bills",			"CsrPromissoryController@acct_bills");
			Route::post("attachment-upload",	"CsrPromissoryController@attachment_upload");
			Route::post("post-promissory",		"CsrPromissoryController@post_promissory");
			Route::get ("payments-ledger",		"CsrPromissoryController@payments_ledger");
		});

		Route::group(['prefix'=>'illegal-promissory'], function() {
			Route::get ("",						"CsrIllegalPromissoryController@index");
			Route::get ("new",					"CsrIllegalPromissoryController@new_promissory");
			Route::get ("acct-illegals",		"CsrIllegalPromissoryController@acct_illegals");
			Route::post("post-promissory",		"CsrIllegalPromissoryController@post_promissory");
			Route::get ("payments-ledger",		"CsrIllegalPromissoryController@payments_ledger");
		});

		Route::group(['prefix'=>'customer-ledger'], function() {
			Route::get("", 	    		"CustomerLedgerController@index");
			Route::get("get-accounts", 	"CustomerLedgerController@accounts");
			Route::get("get-bills", 	"CustomerLedgerController@bills");
			Route::get("payments",   	"CustomerLedgerController@payments");
			Route::get("get-payments",  "CustomerLedgerController@get_payments");
		});	

		Route::group(['prefix'=>'illegal-connection'], function() {
			Route::get ("",					"CsrIllegalController@index");
			Route::get ("dt",				"CsrIllegalController@dt");
			Route::get ("details",			"CsrIllegalController@details");
			Route::get ("edit-payment",		"CsrIllegalController@edit_payment");
			Route::post("update-payment",	"CsrIllegalController@update_payment");
		});

		Route::group(['prefix'=>'skip-bills'], function() {
			Route::get ("",				"SkipBillsController@index");
			Route::get ("get-bills",    "SkipBillsController@bills");
			Route::post("update",       "SkipBillsController@update");
		});

		Route::group(['prefix'=>'senior-citizens'], function() {
			Route::get ("",					"CsrSeniorCitizensController@index");
			Route::get ("dt",				"CsrSeniorCitizensController@dt");
			Route::get ("accounts",			"CsrSeniorCitizensController@accounts");
			Route::get ("activate",			"CsrSeniorCitizensController@activate");
			Route::get ("deactivate",		"CsrSeniorCitizensController@deactivate");
			Route::get ("deactivate-all",	"CsrSeniorCitizensController@deactivate_all");
		});

		Route::group(['prefix'=>'customers-with-no-duedate'], function(){
			Route::get ("",        "CsrCustNoDuedateController@index");
			Route::get ("get-all", "CsrCustNoDuedateController@getAll");
			Route::post("update",  "CsrCustNoDuedateController@update");
			Route::post("delete",  "CsrCustNoDuedateController@delete");
		});
		
		Route::group(['prefix'=>'customers-with-penalty'], function(){
			Route::get ("",         		  		"CsrCustWithPenaltyController@index");
			Route::get ("load-customer", 	  		"CsrCustWithPenaltyController@load_cust");
			Route::get ("edit-cust-with-penalty", 	"CsrCustWithPenaltyController@edit_cust");
			Route::get ("load-books", 	  			"CsrCustWithPenaltyController@load_books");
			Route::get ("load-class", 	  			"CsrCustWithPenaltyController@load_class");
			Route::get ("filter-accounts", 	  		"CsrCustWithPenaltyController@filter_acct");
			Route::get ("deactivate", 	  			"CsrCustWithPenaltyController@deactivate");
			Route::get ("deactivate-all", 	  		"CsrCustWithPenaltyController@deactivate_all");
			Route::post ("update-customer", 	  	"CsrCustWithPenaltyController@update_cust");
			Route::get ("data-filter", 	  			"CsrCustWithPenaltyController@getCustomFilterData");
		});

		# OTHERS SETTINGS
		Route::get ("other-settings",						"CustomerOtherSettingsController@index");
		Route::get ("other-settings/load-cust",				"CustomerOtherSettingsController@load");
		Route::get ("other-settings/edit-others",			"CustomerOtherSettingsController@edit");
		Route::post ("other-settings/update-others",		"CustomerOtherSettingsController@update");		
});
/////////////////////////////////////////////////////////////////////////////


/////////////////////////////////////////////////////////////////////////////
/// BILLING
/////////////////////////////////////////////////////////////////////////////
Route::group([
	'prefix'     => 'billing', 
	'middleware' => [
		'auth'
	]], 
	function() {
		Route::group([
			'prefix' 	 => 'acct-deactivation', 
			'middleware' => ['role:BILLER|ADMIN SUPERVISOR']
		], function(){			
			Route::get ("", 					"AcctDeactivationController@Index");
			Route::get ("new", 					"AcctDeactivationController@New");
			Route::post("save", 				"AcctDeactivationController@Save");
		});

		Route::group([
			'prefix' 	 => 'bill-inquiry', 
			'middleware' => [
				'role:BILLER|ADMIN SUPERVISOR|TELLER|CUSTOMER SERVICE REPRESENTATIVE'
			]],
			function(){			
				Route::get("", 					"BillInquiryController@index");
				Route::get("get-bills", 		"BillInquiryController@get_bills");
				Route::get("view-collections", 	"BillInquiryController@view_collection_details");
		});	

		Route::group([
			'prefix' 	 => 'bill-alteration', 
			'middleware' => [
				'role:BILLER|ADMIN SUPERVISOR'
			]], 
			function() {
				Route::get("", 	  		         		"BillAlterationController@index");
				Route::get("customer-account-details", 	"BillAlterationController@customer_account_details");
				Route::get("get-bills", 	  	        "BillAlterationController@get_bills");
				Route::post("post-alteration", 	  	    "BillAlterationController@post_bill");
				Route::get("alter-bill", 	  	        "BillAlterationController@alter_bill");
				Route::post("post-temp-alteration",		"BillAlterationController@post_temp_alteration");
				Route::get("pending-alterations", 	    "BillAlterationController@pending_alterations");
				Route::get("view-pending-alterations", 	"BillAlterationController@view_pending_alterations");
				Route::get("view-details", 				"BillAlterationController@view_details");
				Route::post("approve-alterations", 		"BillAlterationController@ApproveAlterations");
				Route::post("remove-pending", 			"BillAlterationController@remove_pending");
				Route::get("passcode", 					"BillAlterationController@validate_passcode");
		});		

		Route::group([
			'prefix' 	 => 'final-bill',
			'middleware' => [
				'role:BILLER|ADMIN SUPERVISOR'
			]], 
			function() {
				Route::get("", 	  		         		"FinalBillController@index");
				Route::get("customer-account-details", 	"FinalBillController@customer_account_details");
				Route::get("previous-bill", 	  	    "FinalBillController@previous_bill");
				Route::post("post-fb", 	  			    "FinalBillController@post_bill");
				Route::get("check-pullout", 	  	    "FinalBillController@checkPullout");
		});

		Route::group([
			'prefix' 	 => 'refund-posting', 
			'middleware' => [
				'role:BILLER|ADMIN SUPERVISOR'
			]], 
			function(){
				Route::get("",     			 "RsController@index");
				Route::get("account",     	 "RsController@account");
				Route::get("balance",     	 "RsController@balance");
				Route::post("post",     	 "RsController@post");
				Route::get("get-rs",     	 "RsController@get_rs");
				Route::get("account-search", "RsController@select2_acct_search");
				Route::post("cancel",     	 "RsController@cancelRs");
		});	

		Route::get("bill-printing", "ReportsController@soa")->middleware('role:BILLER|ADMIN SUPERVISOR');		

		Route::group([
			'prefix' 	 => 'soa', 
			'middleware' => [
				'role:BILLER|ADMIN SUPERVISOR'
			]], 
			function(){
				Route::get("",    "SoaController@index");
				Route::get("get-bill", "SoaController@get_bill");
		});			

		Route::get ("reading-validation", 		"BillPostingController@validation_report")->middleware('role:BILLER|ADMIN SUPERVISOR');
		Route::get ("get-reading-validation", 	"BillPostingController@get_reading_validation")->middleware('role:BILLER|ADMIN SUPERVISOR');

		Route::group([
			'prefix' => 'bill-posting', 
			'middleware'=>[
				'role:BILLER|ADMIN SUPERVISOR'
			]], 
			function() {
				Route::get("",                   "BillPostingController@index");
				Route::get("get-bills-header",   "BillPostingController@bills_header");
				Route::get("get-bills-details",  "BillPostingController@bills_details");
				Route::get("reading-status", 	 "BillPostingController@reading_status");
				Route::post("post-header", 		 "BillPostingController@post_header");
				Route::post("post-bill", 		 "BillPostingController@post_bill");
		});		

		Route::get ("due-dates",         "BillPostingController@due_dates")->middleware('role:ADMINISTRATOR');
		Route::get ("get-due-dates",     "BillPostingController@get_due_dates");
		Route::post("update-due-date",   "BillPostingController@update_due_date");

		Route::get("check-reading-period",  "ReadingPeriodController@checkReadingPeriod");

		Route::group([
			'prefix' => 'book-assignment', 
			'middleware'=>[
				'role:BILLER|ADMIN SUPERVISOR'
			]], 
			function(){
				Route::get("/", 		"BookAssignmentController@index");
				Route::get("get-books", "BookAssignmentController@get_books");
				Route::post("post", 	"BookAssignmentController@post");
				Route::get("reading-sequence", 	"BookAssignmentController@reading_sequence");
		});		

		Route::group([
			'prefix' => 'final-statement', 
			'middleware'=>[
				'role:BILLER|ADMIN SUPERVISOR'
			]], 
			function(){
				Route::get ("/", 			"FsoaController@index");
				Route::get ("get-payables", "FsoaController@getPayables");
				Route::post("post", 		"FsoaController@post");
		});		
});
/////////////////////////////////////////////////////////////////////////////


/////////////////////////////////////////////////////////////////////////////
/// TELLER
/////////////////////////////////////////////////////////////////////////////
Route::group([
	'prefix' 	 => 'teller',
	'middleware' => [
		'auth'		
	]], 
	function() {
		Route::group(['prefix' 	 => 'bills-payment'], function(){
			Route::get("",	                        "BillsPaymentController@index")->middleware("role:TELLER");
			Route::post("authorize-skip-bill",		"BillsPaymentController@AuthorizeSkipBill");
			Route::get("customer-account-details", 	"BillsPaymentController@customer_account_details");
			Route::get("get-bills",	                "BillsPaymentController@get_bills");
			Route::post("post-payment",	 			"BillsPaymentController@post_payment");
			Route::get ("collection-details",		"BillsPaymentController@collection_details");
			Route::post("post-advance",	 			"BillsPaymentController@post_advance");	
		});

		Route::group(['prefix' => 'advance-payment'], function(){		
			Route::get ("",	        			"CiapController@index")->middleware("role:TELLER");
			Route::get ("get-bills",			"CiapController@bills");
			Route::post("post-advance",	 		"CiapController@post");
			Route::get ("collection-details",	"CiapController@collection_details");
			Route::get ("ap",	 	        	"CiapController@available_ap");
			Route::get ("get-advances",	    	"CiapController@get_advances");
		});

		Route::group(["prefix" => "application-payment"], function() {
			Route::get ("",						"TellerApplicationPaymentController@index")->middleware("role:TELLER");
			Route::get ("appl-payables",		"TellerApplicationPaymentController@appl_payables");
			Route::post("post-payment",			"TellerApplicationPaymentController@post_payment");
			Route::get ("collection-details",	"TellerApplicationPaymentController@collection_details");
		});

		Route::group(["prefix" => "others-payment"], function() {
			Route::get ("",						"TellerOthersPaymentController@Index")->middleware("role:TELLER");
			Route::get ("acct-payables",		"TellerOthersPaymentController@AcctPayables");
			Route::post("post-payment",			"TellerOthersPaymentController@PostPayment");
			Route::get ("collection-details",	"TellerOthersPaymentController@CollectionDetails");
		});

		Route::group(['prefix' => 'other-transactions'], function(){
			Route::get ('',     	'OtherTransactionsController@index')->middleware("role:TELLER");
			Route::post('post', 	'OtherTransactionsController@post');
			Route::get ('details',	'OtherTransactionsController@details');
		});

		Route::group(['prefix' => 'refund-fund'], function() {
			Route::get ("",			"RefundController@index");
			Route::get ("voucher",	"RefundController@voucher");
			Route::get ("payment",	"RefundController@payment");
			Route::post("save",	    "RefundController@save");
		});		

		Route::group(['prefix' => 'advance-payment'], function(){		
			Route::get("",	        		"CiapController@index")->middleware("role:TELLER");
			Route::get("get-bills",			"CiapController@bills");
			Route::post("post-advance",	 	"CiapController@post");
			Route::get("ap",	 	        "CiapController@available_ap");
			Route::get("get-advances",	    "CiapController@get_advances");
		});

		Route::group(['prefix' => 'cancel-payment'], function(){		
			Route::get("", 				"CancelPaymentController@index")->middleware("role:TELLER|ADMIN SUPERVISOR");
			Route::get("collection", 	"CancelPaymentController@collection");
			Route::post("cancel", 		"CancelPaymentController@cancel");
		});	

		Route::group(['prefix' => 'collection-posting'], function(){		
			Route::get("", 				    "CollectionPostingController@index")->middleware("role:TELLER|BILLER|ADMIN SUPERVISOR");
			Route::get("collector-file", 	"CollectionPostingController@collector_file");
			Route::get("get-collections", 	"CollectionPostingController@get_collections");

			Route::delete("delete-payment", "CollectionPostingController@delete_payment");
		});		

		Route::get("is-or-existing", "BillsPaymentController@is_or_existing");	

		Route::get("payment-history", "PaymentHistoryController@index");
		Route::get("get-collections", "PaymentHistoryController@get_collections");

		Route::get("discount-and-tax", 	"BillsPaymentController@discount_tax");
		Route::get("sd-add", 			"BillsPaymentController@sd_add");
		Route::get("col-back-date", 	"BillsPaymentController@back_date");

		Route::group(['prefix' => 'search-or'], function(){		
			Route::get("",    "SearchOrController@index");
			Route::get("get", "SearchOrController@getOne");
		});	

		Route::group(['prefix' => 'refund-voucher'], function(){		
			Route::get ("", 			"RefundController@index")->middleware("role:TELLER|CUSTOMER SERVICE REPRESENTATIVE|ADMIN SUPERVISOR");
			Route::get ("balance", 		"RefundController@getUnpaidBills");
			Route::get ("gd", 	    	"RefundController@getGdAdvances");
			Route::post("post", 		"RefundController@post");
			Route::get ("is-refunded", 	"RefundController@isGdRefunded");
		});			
});
/////////////////////////////////////////////////////////////////////////////


/////////////////////////////////////////////////////////////////////////////
/// COLLECTOR
/////////////////////////////////////////////////////////////////////////////  
Route::group([
	'prefix' 	 => 'collector',
	'middleware' => [
		'auth',
		'role:TELLER|ADMIN SUPERVISOR|BILLER'
	]], 
	function() {
		Route::group(['prefix' => 'collection-posting'], function(){
			Route::get ("", 					"CollectorController@index");
			// By erick -----------------------------------------------------
			Route::get ("bill-checker", 		"CollectorController@getBill");
			Route::get ("get-bill-record", 		"CollectorController@getBillRecord");
			Route::get ("cancel-payment", 		"CollectorController@cancelPayment");
			// Route::get ("cancel-collection", 	"CollectorController@cancel");
			Route::get ("cancel-collection", 	"CollectorController@cancel");
			//----------------------------------------------------------------
			Route::get ("get-all", 				"CollectorController@getAll");
			Route::get ("get-one", 				"CollectorController@getOne");
			Route::post("store", 				"CollectorController@store");
		});
});
/////////////////////////////////////////////////////////////////////////////


/////////////////////////////////////////////////////////////////////////////
/// WAREHOUSE
/////////////////////////////////////////////////////////////////////////////
Route::group([
	'prefix'     => 'warehouse', 
	'middleware' => [
		'auth'
	]], 
	function() {
		Route::group([
			'prefix' 	 => 'water-meters',
			'middleware' => [
				'role:BILLER|ADMIN SUPERVISOR|WAREHOUSEMAN'
			]],
			function() {
				Route::get ("", 		 "WaterMetersController@index");
				Route::get ("dt", 		 "WaterMetersController@wmeter_dt");
				Route::get ("new", 		 "WaterMetersController@wmeter_new");
				Route::post("save", 	 "WaterMetersController@wmeter_save");	
				Route::get ("calibrate", "WaterMetersController@wmeter_calibrate");			
		});		
});
/////////////////////////////////////////////////////////////////////////////


/////////////////////////////////////////////////////////////////////////////
/// REPORTS
/////////////////////////////////////////////////////////////////////////////
Route::group([
	'prefix' 	 => 'reports', 
	'middleware' => 
		'auth'
	], 
	function() {
		Route::get("dcr", 						"ReportsController@dcr");
		Route::get("daily-collection",   		"ReportsController@teller_collection")->middleware('role:TELLER|ADMIN SUPERVISOR');
		Route::get("teller-collection-bills", 	"ReportsController@collection_bills");
		Route::get("teller-collection-others", 	"ReportsController@collection_others");
		Route::get("teller-collection-refunds", "ReportsController@collection_refunds");

		Route::get("collection-summary", 		"CollectionSummaryController@index")->middleware('role:BILLER|ADMIN SUPERVISOR');
		Route::get("get-teller-summary", 		"CollectionSummaryController@get_summary");

		Route::get("collection-report",   			"CollectorReportController@daily")->middleware('role:BILLER|ADMIN SUPERVISOR|TELLER');
		Route::get("collector-collection-summary", 	"CollectorReportController@summary")->middleware('role:TELLER|ADMIN SUPERVISOR');
		Route::get("collector-get-all", 			"CollectorReportController@getAll");

		Route::get("collection-extraction", 	"CollectionExtractionController@index")->middleware('role:BILLER|ADMIN SUPERVISOR');
		Route::get("get-col-extraction", 	    "CollectionExtractionController@get_collections");

		Route::get("dcr-deposit", 				"CollectionSummaryController@get_deposits");
		Route::post("save-deposit", 			"CollectionSummaryController@save_deposit");		

		Route::get("aging-summary", 			"ReportsController@aging_view")->middleware('role:BILLER|ADMIN SUPERVISOR');
		Route::get("aging", 					"ReportsController@aging_ar");
		Route::get("past-due-rate", 			"ReportsController@past_due_rate");
		Route::get("aging-detailed",			"ReportsController@aging_detailed")->middleware('role:BILLER|ADMIN SUPERVISOR');
		Route::get("aging-of-ar-customer",		"ReportsController@aging_per_customer");

		Route::get("aging-summary-new",		    "ReportsController@aging_summary_new");
		Route::get("aging-detailed-new",		"ReportsController@aging_detailed_new");

		Route::get("alteration-listing", 		"ReportsController@alteration")->middleware('role:BILLER|ADMIN SUPERVISOR');
		Route::get("al-details", 			 	"ReportsController@al_details");		
		Route::get("alteration-summary",		"BillAlterationSummaryController@index")->middleware('role:BILLER|ADMIN SUPERVISOR');
		Route::get("get-alteration-summary",	"BillAlterationSummaryController@get_alteration");					

		Route::get("refund-listing", 	 	     "ReportsController@refund_listing")->middleware('role:BILLER|ADMIN SUPERVISOR');
		Route::get("get-rs",     	         	"ReportsController@get_rs");
		Route::get("rs-details",   	         	"ReportsController@rs_details");	
		Route::get("refund-summary",			"RefundSummaryController@index")->middleware('role:BILLER|ADMIN SUPERVISOR');
		Route::get("get-refund-summary",		"RefundSummaryController@get_rs");			
		
		Route::get("billing-register", 	  	 	"ReportsController@billing_register")->middleware('role:BILLER|ADMIN SUPERVISOR');

		Route::get("billing-stats-summary", 	"ReportsController@billing_stats")->middleware('role:BILLER|ADMIN SUPERVISOR');
		Route::get("bs-data",             	 	"ReportsController@billing_stats_data");
		Route::get("billing-stats-consolidated","BillstatsController@index")->middleware('role:BILLER|ADMIN SUPERVISOR');
		Route::get("get-bill-stats-conso",      "BillstatsController@consolidated");

		Route::get("cmrvr", 			  	 	"ReportsController@cmrvr")->middleware('role:BILLER|ADMIN SUPERVISOR|CUSTOMER SERVICE REPRESENTATIVE');
		Route::get("customer-master-list",		"ReportsController@cml_index");
		Route::get("get-cml",					"ReportsController@cml_get");

		# ADD TO BILL
		Route::get("add-to-bill",		 				"ReportsController@add_to_bill")->middleware('role:BILLER|ADMIN SUPERVISOR|CUSTOMER SERVICE REPRESENTATIVE');
		Route::get("add-to-bill-report", 				"ReportsController@add_to_bill_report");

		# ADVANCE PAYMENT
		Route::get("advance-payment",		 			"ReportsController@advance_payment");
		Route::get("advance-payment-report", 			"ReportsController@advance_payment_report");

		# ADVANCE PAYMENT SUMMARY
		Route::get("summary-of-advance-payment",		"ReportsAdvanceSummaryController@advance_payment_summary")->middleware('role:BILLER|ADMIN SUPERVISOR|CUSTOMER SERVICE REPRESENTATIVE');
		Route::get("advance-payment-summary-report", 	"ReportsAdvanceSummaryController@advance_payment_summary_report");

		# SUMMARY OF PROMISSORY NOTES
		Route::get("summary-of-promissory-notes",		 		"ReportsPromissorySummaryController@promissory_summary")->middleware('role:BILLER|ADMIN SUPERVISOR|CUSTOMER SERVICE REPRESENTATIVE');
		Route::get("promissory-summary-report", 				"ReportsPromissorySummaryController@promi_summary_report");		

		# HIGH VIEW COLLECTION REPORT
		Route::get("high-view-collection-report",		 			"HighviewCollectionController@highview_index")->middleware('role:BILLER|ADMIN SUPERVISOR');
		Route::get("high-view-collection-report-summary", 			"HighviewCollectionController@highview_report");	

		# SEPTAGE COLLECTION REPORT
		Route::get("septage-collection-report",		 				"SeptageCollectionController@index")->middleware('role:BILLER|ADMIN SUPERVISOR');
		Route::get("septage-collection-report-summary", 			"SeptageCollectionController@load");

		Route::get("paid-septage-fee",  	"SeptageReportsController@paidSeptageFee_index")->middleware("role:BILLER|ADMIN SUPERVISOR");
		Route::get("paid-septage-fee-get",  "SeptageReportsController@paidSeptageFee_get");
		Route::get("sharing-of-fees",  		"SeptageReportsController@sharingFees_index");

		Route::get("aging-of-septage", 		"SeptageReportsController@septageAging_index")->middleware("role:BILLER|ADMIN SUPERVISOR");
		Route::get("septage-aging-get",  	"SeptageReportsController@septageAging_get");

		# CUSTOMERS
		Route::get("new-connected-customers",					"ReportsController@customers_summary")->middleware('role:BILLER|ADMIN SUPERVISOR|CUSTOMER SERVICE REPRESENTATIVE');
		Route::get("customers-application-summary",				"ReportsController@customers_application_summary");
		Route::get("new-billed-customers",						"ReportsController@new_billed_customers");
		Route::get("summary-of-connected-customers",			"ReportsController@summary_of_connected_customers");

		# JOB ORDER
		Route::get ("job-order-summary",						"ReportsController@jo_summary")->middleware('role:ADMIN SUPERVISOR|CUSTOMER SERVICE REPRESENTATIVE');
		Route::get ("jo-fetch-report",							"ReportsController@jo_fetch_report");

		# CUSTOMERS FOR DISCONNECTION
		Route::get ("customers-for-disconnection",				"ReportsController@customers_for_disconnection")->middleware('role:BILLER|ADMIN SUPERVISOR|CUSTOMER SERVICE REPRESENTATIVE');
		Route::get ("customers-for-disconnection-generate",		"ReportsController@customers_for_disconnection_generate");


		// generate bill nos.
		Route::get ("bill-nos", 	  "ReportsController@bill_nos");
		Route::post("update-bill-no", "ReportsController@generate_bill_no");

		// official receipt
		Route::get("official-receipt-bills",	"ReportsController@official_receipt_bills");
		Route::get("official-receipt-others",	"ReportsController@official_receipt_others");	

		# ACCOUNTING REPORTS
		Route::group(['prefix' => 'other-reports'], function() {
			Route::get("",	  "AccountingController@index")->middleware('role:BILLER|ADMIN SUPERVISOR');
			Route::get("ci",  "AccountingController@collections_industrial");
			Route::get("rd",  "AccountingController@rs_deficit");
			Route::get("ad",  "AccountingController@advances");
			Route::get("aub", "AccountingController@aub");
		});	
		
		# GUARANTEE DEPOSITS REPORT
		Route::group(['prefix'=>'guarantee-deposits'], function(){
			Route::get ("",         		  		"CsrGuaranteeDepositsController@index")->middleware('role:BILLER|ADMIN SUPERVISOR|CUSTOMER SERVICE REPRESENTATIVE');
			Route::get ("load-cust",         		"CsrGuaranteeDepositsController@loadCust");
			Route::get ("filter-accounts",         	"CsrGuaranteeDepositsController@filterAccounts");
		});		

		# CANCELLED OR REPORT
		Route::get("cancelled-or",		 				"CancelOrReportController@index")->middleware('role:TELLER|ADMIN SUPERVISOR');
		Route::get("cancelled-or-report", 				"CancelOrReportController@report");

		# FINAL SOA REPORT - 07-10-2019
		Route::get ("final-soa-summary",        	  	"FinalSoaReportController@index")->middleware('role:BILLER|ADMIN SUPERVISOR');
		Route::get ("final-soa-report",        	  		"FinalSoaReportController@getAll");
		Route::get ("final-soa-details",        	  	"FinalSoaReportController@getDetails");
		Route::get ("final-soa-signatories",        	"FinalSoaReportController@getSignatories");
		

		Route::group(['prefix'=>'application'], function(){
			Route::get ("change-connection-type",   "CsrApplicationController@change_conntype_report");
		});

		# LIST OF SENIOR ACCOUNT REPORT

		Route::get ("list-of-senior-account",        	  	"SeniorListController@index")->middleware('role:CUSTOMER SERVICE REPRESENTATIVE|ADMIN SUPERVISOR');
		
});
/////////////////////////////////////////////////////////////////////////////


/////////////////////////////////////////////////////////////////////////////
/// SETTINGS
/////////////////////////////////////////////////////////////////////////////
Route::group([
	'prefix' 	 => 'settings', 
	'middleware' => 
		'auth'
	], 
	function() {
		Route::get ("connection-materials", 					"ConnectionMaterialsController@index")->middleware("role:CUSTOMER SERVICE REPRESENTATIVE|WAREHOUSEMAN");
		Route::get ("connection-materials/dt", 					"ConnectionMaterialsController@dt");
		Route::get ("connection-materials/new", 				"ConnectionMaterialsController@preset_new");
		Route::post("connection-materials/save", 				"ConnectionMaterialsController@preset_save");
		Route::get ("connection-materials/edit", 				"ConnectionMaterialsController@preset_edit");
		Route::post("connection-materials/patch", 				"ConnectionMaterialsController@preset_cancel");	
		Route::get ("connection-materials/delete",				"ConnectionMaterialsController@preset_delete");
		Route::get ("connection-materials/default",				"ConnectionMaterialsController@preset_default");
});
/////////////////////////////////////////////////////////////////////////////


/////////////////////////////////////////////////////////////////////////////
/// SSM Septage Menu
/////////////////////////////////////////////////////////////////////////////
Route::group([
	'prefix' 	 => 'ssm', 
	'middleware' => [
		'auth',
		'role:ADMINISTRATOR|BILLER|ADMIN SUPERVISOR|CUSTOMER SERVICE REPRESENTATIVE|BRANCH MANAGER'
	]], 
	function() {	
		Route::get("", 				"SeptageController@index");
		Route::get("index", 		"SeptageController@index");
		Route::get("customers", 	"SeptageController@index");
		Route::get("joborders", 	"SeptageController@index");
		Route::get("cycle", 		"SeptageController@index");
		Route::get("septagefees", 	"SeptageController@index");
		Route::get("sharefees", 	"SeptageController@index");
		Route::get("reports", 		"SeptageController@index");

		Route::get ("get-customers", 	"SeptageController@getCustomers");

		Route::post("post-jo",			"SeptageController@postJo");
		Route::get ("get-jo",			"SeptageController@getJo");
		Route::post("dispatch-jo",		"SeptageController@dispatchJo");
		Route::post("cancel-jo",		"SeptageController@cancelJo");
		Route::get ("accomplish-form",	"SeptageController@accomplishForm");
		Route::post("accomplish-jo",	"SeptageController@accomplishJo");
		Route::get ("view-jo-details",	"SeptageController@viewJo");

		Route::get ("print-jo",	"SeptageController@printJo");
});
/////////////////////////////////////////////////////////////////////////////


/////////////////////////////////////////////////////////////////////////////
/// ADMINISTRATOR
/////////////////////////////////////////////////////////////////////////////
Route::group(['prefix' => 'administrator', 'middleware' => ['auth', 'role:ADMINISTRATOR']], function() {
	# SYSTEM USERS
	Route::get ("account-classifications", 				"AdministratorController@acct_class");
	Route::post("account-classifications/add", 			"AdministratorController@acct_class_add");
	Route::get ("account-classifications/details", 		"AdministratorController@acct_class_details");
	Route::post("account-classifications/patch", 		"AdministratorController@acct_class_patch");

	# BOOKS
	Route::get ("books", 								"BooksController@index");
	Route::get ("books/get-books", 						"BooksController@get_books");
	Route::post("books/add-book", 						"BooksController@add_book");
	Route::post("books/update-book", 					"BooksController@update_book");	

	# BRANCH SETTINGS
	Route::get ("branch-settings", 		 				"BranchSettingsController@index");
	Route::get ("branch-settings/settings", 			"BranchSettingsController@settings");
	Route::post("branch-settings/update",   			"BranchSettingsController@update");
	Route::get ("branch-settings/loadbooks",   			"BranchSettingsController@books");
	Route::get ("branch-settings/books",    			"BranchSettingsController@get_books");
	Route::get ("branch-settings/pca",      			"BranchSettingsController@pca");
	Route::get ("branch-settings/load-others",      	"BranchSettingsController@loadOthers");
	Route::post ("branch-settings/others",      		"BranchSettingsController@add_charges");
	Route::post("branch-settings/add-pca",  			"BranchSettingsController@add_pca");

	# OTHERS SETTINGS
	Route::get ("other-settings",						"CustomerOtherSettingsController@index");
	Route::get ("other-settings/load-cust",				"CustomerOtherSettingsController@load");
	Route::get ("other-settings/edit-others",			"CustomerOtherSettingsController@edit");
	Route::post ("other-settings/update-others",		"CustomerOtherSettingsController@update");

	# BRANCHES
	Route::get ("branches", 			  				"BranchesController@index");
	Route::get ("branches/get-branches",  				"BranchesController@get_branches");
	Route::post("branches/add-branch",    				"BranchesController@add_branch");
	Route::post("branches/update-branch", 				"BranchesController@update_branch");	

	# BRANCH WATER RATES
	Route::get ("rates", 								"RatesController@index");
	Route::get ("rates/existing", 						"RatesController@existing");
	Route::post("rates/post", 							"RatesController@post");
	Route::post("rates/update", 						"RatesController@update");
	Route::get ("rates/get-rates",						"RatesController@get_rates");

	# SYSTEM USERS
	Route::get ("system-users", 						"AdministratorController@users");
	Route::get ("system-users/new", 					"AdministratorController@user_new");
	Route::post("system-users/add", 					"AdministratorController@user_add");
	Route::get ("system-users/edit", 					"AdministratorController@user_edit");
	Route::post("system-users/patch", 					"AdministratorController@user_patch");
	Route::post("system-users/activate", 				"AdministratorController@user_activate");
	Route::post("system-users/de-activate", 			"AdministratorController@user_deactivate");

	# APPLICATION PROMOS
	Route::get ("application-promos", 					"ApplicationPromosController@index");
	Route::get ("application-promos/dt", 				"ApplicationPromosController@dt");
	Route::get ("application-promos/new",				"ApplicationPromosController@promo_new");
	Route::post("application-promos/save",				"ApplicationPromosController@promo_save");
	Route::post("application-promos/delete",			"ApplicationPromosController@promo_delete");

	# DEPLOYMENT TOOLS
	Route::get ("admin-tools", 									"AdministratorDeploymentToolsController@index");
	Route::get ("admin-tools/or-search",						"AdministratorDeploymentToolsController@or_search");

	Route::get ("admin-tools/or-change", 						"AdministratorDeploymentToolsController@or_change_view");
	Route::get ("admin-tools/or-change-search", 				"AdministratorDeploymentToolsController@or_change_search");
	Route::post("admin-tools/or-change", 						"AdministratorDeploymentToolsController@or_change_post");

	Route::get ("admin-tools/or-change-date", 					"AdministratorDeploymentToolsController@or_change_date_view");
	Route::post("admin-tools/or-change-date", 					"AdministratorDeploymentToolsController@or_change_date_post");

	Route::get ("admin-tools/delete-bills-payment",				"AdministratorDeploymentToolsController@delete_bills_payment_view");
	Route::post("admin-tools/delete-bills-payment",				"AdministratorDeploymentToolsController@delete_bills_payment_post");

	Route::get ("admin-tools/other-transactions",				"AdministratorDeploymentToolsController@other_transactions_view");
	Route::get ("admin-tools/other-transactions-list",			"AdministratorDeploymentToolsController@other_transactions_list");
	Route::get ("admin-tools/other-transactions-details",		"AdministratorDeploymentToolsController@other_transactions_details");
	Route::post("admin-tools/other-transactions-update",		"AdministratorDeploymentToolsController@other_transactions_update");
	Route::post("admin-tools/other-transactions-delete",		"AdministratorDeploymentToolsController@other_transactions_delete");

	Route::get("admin-tools/view-pn",		 					"AdministratorDeploymentToolsController@view_pn");
	Route::get("admin-tools/load-pn",		 					"AdministratorDeploymentToolsController@show_pn");
	Route::get("admin-tools/update-pn-date",		 			"AdministratorDeploymentToolsController@update_pn_date");

	Route::get("admin-tools/contract-number",		 			"AdministratorDeploymentToolsController@view_contract_num");
	Route::get("admin-tools/get-contract-number",		 		"AdministratorDeploymentToolsController@get_contract_num");
	Route::get("admin-tools/get-acc-contract-number",		 	"AdministratorDeploymentToolsController@get_acc_contract_num");
	Route::get("admin-tools/update-contract-no",				"AdministratorDeploymentToolsController@update_contract_no");

	Route::get("admin-tools/rs-backdate", 						"AdministratorDeploymentToolsController@view");
	Route::get("admin-tools/get-rs", 							"AdministratorDeploymentToolsController@get_rs_details");
	Route::get("admin-tools/update-rs-date", 					"AdministratorDeploymentToolsController@update_rs");

	# ALTER CLASSIFICATION
	Route::get ("alter-classification", 		 					"AdministratorController@alter_classification");
	Route::get ("alter-classification-get-bill", 		 			"AdministratorController@get_bill_details");
	Route::post("alter-class-update", 		 						"AdministratorController@alter_class_update");
});
/////////////////////////////////////////////////////////////////////////////


/////////////////////////////////////////////////////////////////////////////
/// USER
/////////////////////////////////////////////////////////////////////////////
Route::group(['prefix' => 'user', 'middleware' => 'auth'], function() {
	# MY ACCOUNT
	Route::get ("my-account", 					"UserController@my_account");
	Route::post("my-account-changepass", 		"UserController@my_account_changepass");
});
/////////////////////////////////////////////////////////////////////////////


#GLOBAL
Route::group(['middleware' => 'auth'], function() {
	Route::get("books",									"MainController@books");
	Route::get("meter-readers",							"MainController@meter_readers");
	Route::get("tellers",								"MainController@tellers");
	Route::get("collectors",							"MainController@collectors");
	Route::get("users",			        				"MainController@get_users");
	Route::get("branches",			    				"MainController@branches");
	Route::get("companies",			    				"MainController@companies");
	Route::get("classifications",	    				"MainController@classifications");
	Route::get("set-or",	    						"MainController@set_current_or");
	Route::get("set-printer",							"MainController@set_current_printer");
	Route::get("emp-has-position",	    				"MainController@employee_has_position");

	Route::get('acct-class', 							"MainController@acct_class");
	Route::get('acct-subclass', 						"MainController@acct_subclass");

	Route::get('provinces', 							"MainController@provinces");
	Route::get('cities', 								"MainController@cities");
	Route::get('brgys',  								"MainController@brgys");

	Route::get('jo-categories',  						"MainController@jo_categories");
	Route::get('jo-reasons',  							"MainController@jo_reasons");
	Route::get('jo-services',  							"MainController@jo_services");

	Route::get('wmeters',  								"MainController@wmeters");
	Route::get('class-only',  		    				"MainController@class_only");

	Route::get("select2-cust-search",					"MainController@select2_cust_search");
	Route::get("select2-cust-details",					"MainController@select2_cust_details");

	Route::get("select2-acct-search",					"MainController@select2_acct_search");
	Route::get("select2-acct-details",					"MainController@select2_acct_details");

	Route::get("select2-appl-search",					"MainController@select2_appl_search");
	Route::get("select2-appl-details",					"MainController@select2_appl_details");

	Route::get("select2-bill-search",					"MainController@select2_bill_search");
	Route::get("select2-bill-details",  				"MainController@select2_bill_details");
	
	Route::get("select2-materials-search",				"MainController@select2_materials_search");
	Route::get("select2-materials-details",  			"MainController@select2_materials_details");

	Route::get("select2-materials-preset-search",		"MainController@select2_materials_preset_search");
	Route::get("select2-materials-preset-details",  	"MainController@select2_materials_preset_details");

	Route::get("select2-services-search",				"MainController@select2_services_search");
	Route::get("select2-services-details",  			"MainController@select2_services_details");

	Route::get("select2-application-services-search",	"MainController@select2_application_services_search");
	Route::get("select2-application-services-details",  "MainController@select2_application_services_details");

	Route::get("select2-system-user-search",  			"MainController@select2_system_user_search");
	Route::get("select2-system-user-details",  			"MainController@select2_system_user_details");

	Route::post("close-billing",  						"CloseBillingController@index")->middleware("role:BILLER|ADMIN SUPERVISOR");
	Route::get("validate-readings", 					"CloseBillingController@validate_readings");
	
	Route::post("passcode-validator",   				"PasscodeController@validate_passcode");
	Route::post("passcode-rs",   						"PasscodeController@cancel_rs");
	Route::post("passcode-al",   						"PasscodeController@alter_class");

	Route::get("notification-for-rs", 					"NotificationsController@for_rs");
	Route::get("notification-pending-al", 				"NotificationsController@pending_al");
	Route::get("notification-critical-jo", 				"NotificationsController@critical_jo");
});

# PDFs
Route::group(['prefix'=>'pdf', 'middleware'=>'auth'], function() {
	Route::get("cmrvr",   			"PdfController@cmrvr");
	Route::get("billing-register",  "PdfController@billing_register");
	Route::get("bill-printing",  	"PdfController@bill_printing");
	Route::get("soa",  				"PdfController@soa");
	Route::get("alteration",  		"PdfController@alteration");
	Route::get("rs",  				"PdfController@print_rs");
	Route::get("billing-stats",  	"PdfController@billing_stats");
	Route::post("dcr-prepare",      "PdfController@dcr_prepare");
	Route::get ("dcr",      		"PdfController@dcr_print");
	Route::post("ar-prepare",       "PdfController@ar_prepare");
	Route::get ("ar",      			"PdfController@ar_print");	
});

# METER READING - ANDROID
Route::group(['prefix' => 'mreading'], function() {

	Route::get('/userAccess', 'MeterReadingController@getUserAccess');

	Route::get('/userAccount', 'MeterReadingController@getUserAccount');

	Route::get('/bookAssignments', 'MeterReadingController@getBookAssignments');

	Route::get('/custAccounts', 'MeterReadingController@getCustomers');

	Route::get('/bookPCA', 'MeterReadingController@getPCA');

	Route::get('/bookData', 'MeterReadingController@getBooks');

	Route::get('/getAllBranches', 'MeterReadingController@getBranches');

	Route::get('/getBranch', 'MeterReadingController@getBranchInfo');

	Route::get('/employeeInfo', 'MeterReadingController@getEmployeeInfo');

	Route::post('/upload', 'MeterReadingController@uploadReadings');

	Route::get('/updateUser', 'MeterReadingController@updateUserAccount');

	Route::get('/checkUser', 'MeterReadingController@checkDuplicate');

	Route::get('/getModules', 'MeterReadingController@getUserModules');

	Route::get('/getHeader', 'MeterReadingController@getHeader');
	Route::get('/getDetails', 'MeterReadingController@getDetails');


	Route::get('extract-data', 'MeterReadingController@extract_data');
	Route::get('/userAccount', 'MeterReadingController@getUserAccount');

	Route::get('/getRatesHeader',  'MeterReadingController@getRatesHeader');
	Route::get('/getRatesDetails', 'MeterReadingController@getRatesDetails');
	Route::get('/getReaders', 'MeterReadingController@getMeterReader');
});

# OR PRINTING
Route::group(['prefix'=>'or-printing', 'middleware'=>'auth'], function() {
	Route::get('water-bill', 	 'OrPrintingController@print_water_bill');
	Route::get('get-or-details', 'OrPrintingController@get_or_details');
});

# NEW SOA
Route::group(['prefix'=>'soa', 'middleware'=>'auth'], function() {
	Route::get('', 	 'SoaController@print_soa');
});

# MIGRATION
Route::group(['prefix'=>'migration', 'middleware'=>['auth','role:ADMINISTRATOR']], function() {
	Route::get('', 						'MigrationController@index');
	Route::get('migrate', 				'MigrationController@migrate_data');

	Route::get('collections', 			'MigrationController@migrate_collections');
	Route::get('collections-migrate', 	'MigrationController@migrate_collections_action');

	Route::get('duplicate-bill',		'MigrationController@duplicate_bill');
	Route::get('process-old-data', 		'MigrationController@process_old_data');
});

Route::group(["prefix"=>"fixbills"], function(){
	Route::get ("", 				"FixBillsController@index");
	Route::get ("authenticate", 	"FixBillsController@authenticate");
	Route::get ("check-session", 	"FixBillsController@checkSession");
	Route::post("execute", 			"FixBillsController@execute");
});


# OFFSITE BACKUPS
Route::group(["prefix"=>"binary-logs-backup"], function() {
	Route::get ("", 					"BinaryLogsBackupController@Index");
	Route::get ("dt", 					"BinaryLogsBackupController@DataTable");
	Route::post("post", 				"BinaryLogsBackupController@UploadFile");
});

# CLOUD UPDATES
Route::group(["prefix"=>"api", "middleware"=>"auth"], function() {
	Route::get("system-updates-get",	"ApiController@GetSystemUpdates");
});