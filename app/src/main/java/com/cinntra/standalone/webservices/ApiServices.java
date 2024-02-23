package com.cinntra.standalone.webservices;


import com.cinntra.standalone.model.AddBusinessPartnerData;
import com.cinntra.standalone.model.AddCampaignModel;
import com.cinntra.standalone.model.AddOpportunity;
import com.cinntra.standalone.model.AddQuotation;
import com.cinntra.standalone.model.AdressDetail;
import com.cinntra.standalone.model.BPTypeResponse;
import com.cinntra.standalone.model.Branch;
import com.cinntra.standalone.model.BranchResponse;
import com.cinntra.standalone.model.BusinessPartnerData;
import com.cinntra.standalone.model.CampaignListModel;
import com.cinntra.standalone.model.CampaignListResponse;
import com.cinntra.standalone.model.CampaignModel;
import com.cinntra.standalone.model.CampaignResponse;
import com.cinntra.standalone.model.ChatModel;
import com.cinntra.standalone.model.ChatResponse;
import com.cinntra.standalone.model.CompleteStageResponse;
import com.cinntra.standalone.model.ContactExtension;
import com.cinntra.standalone.model.ContactPerson;
import com.cinntra.standalone.model.ContactPersonData;
import com.cinntra.standalone.model.CounterResponse;
import com.cinntra.standalone.model.CountryResponse;
import com.cinntra.standalone.model.CreateContactData;
import com.cinntra.standalone.model.CreateLead;
import com.cinntra.standalone.model.CustomerBusinessRes;
import com.cinntra.standalone.model.DeliveryResponse;
import com.cinntra.standalone.model.DemoResponse;
import com.cinntra.standalone.model.DemoValue;
import com.cinntra.standalone.model.DepartMentDetail;
import com.cinntra.standalone.model.EmpDetails;
import com.cinntra.standalone.model.EmployeeProfile;
import com.cinntra.standalone.model.EmployeeValue;
import com.cinntra.standalone.model.EventResponse;
import com.cinntra.standalone.model.EventValue;
import com.cinntra.standalone.model.ExpenseNewModelResponse;
import com.cinntra.standalone.model.ExpenseResponse;
import com.cinntra.standalone.model.FollowUpData;
import com.cinntra.standalone.model.FollowUpResponse;
import com.cinntra.standalone.model.HeirarchiResponse;
import com.cinntra.standalone.model.IndustryResponse;
import com.cinntra.standalone.model.InventoryResponse;
import com.cinntra.standalone.model.InvoiceResponse;
import com.cinntra.standalone.model.ItemCategoryData;
import com.cinntra.standalone.model.ItemCategoryResponse;
import com.cinntra.standalone.model.ItemResponse;
import com.cinntra.standalone.model.LeadFilter;
import com.cinntra.standalone.model.LeadTypeResponse;
import com.cinntra.standalone.model.LogInDetail;
import com.cinntra.standalone.model.LogInRequest;
import com.cinntra.standalone.model.LogInResponse;
import com.cinntra.standalone.model.MapData;
import com.cinntra.standalone.model.MapResponse;
import com.cinntra.standalone.model.NewEmployeeUser;
import com.cinntra.standalone.model.NewLogINResponse;
import com.cinntra.standalone.model.NewOppResponse;
import com.cinntra.standalone.model.NewQuotation;
import com.cinntra.standalone.model.NotificationData;
import com.cinntra.standalone.model.NotificationResponse;
import com.cinntra.standalone.model.OpportunitiesResponse;
import com.cinntra.standalone.model.OpportunityItem;
import com.cinntra.standalone.model.OpportunityStageResponse;
import com.cinntra.standalone.model.OwnerResponse;
import com.cinntra.standalone.model.PayMentTermsDetail;
import com.cinntra.standalone.model.PaymentRespnse;
import com.cinntra.standalone.model.PostBP;
import com.cinntra.standalone.model.QuotationItem;
import com.cinntra.standalone.model.QuotationResponse;
import com.cinntra.standalone.model.QuotationStringResponse;
import com.cinntra.standalone.model.RoleListDetail;
import com.cinntra.standalone.model.SaleEmployeeResponse;
import com.cinntra.standalone.model.SalesEmployeeItem;
import com.cinntra.standalone.model.SalesTargetResponse;
import com.cinntra.standalone.model.StagesResponse;
import com.cinntra.standalone.model.StagesValue;
import com.cinntra.standalone.model.StateData;
import com.cinntra.standalone.model.StateDetail;
import com.cinntra.standalone.model.StateRespose;
import com.cinntra.standalone.model.TaxItemResponse;
import com.cinntra.standalone.model.Top5CustomerResponse;
import com.cinntra.standalone.model.Top5ItemResponse;
import com.cinntra.standalone.model.UpdateFavourites;
import com.cinntra.standalone.model.UpdateQuotationModel;
import com.cinntra.standalone.model.UserCounterResponse;
import com.cinntra.standalone.model.UserIDResponse;
import com.cinntra.standalone.model.UserLoginCredential;
import com.cinntra.standalone.model.UserProfile;
import com.cinntra.standalone.model.UserResponse;
import com.cinntra.standalone.newapimodel.AddOpportunityModel;
import com.cinntra.standalone.newapimodel.LeadDocumentResponse;
import com.cinntra.standalone.newapimodel.LeadResponse;
import com.cinntra.standalone.newapimodel.LeadValue;
import com.cinntra.standalone.newapimodel.NewOpportunityRespose;
import com.cinntra.standalone.newapimodel.OpportunityValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface ApiServices {
    @POST("b1sev1/Login")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<LogInResponse> LogIn(@Body LogInRequest data);

    @POST("api/register/")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<String> Register(@Body String data);


    @GET("b1s/v1/Quotations?$select=DocNum,CardCode,DocEntry,DocDate&$orderby=DocEntry desc")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<QuotationResponse> QuotationList_Decending();

    @GET("b1s/v1/Quotations?$orderby=DocEntry desc")
    Call<QuotationResponse> quotationList();

    @GET
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<QuotationResponse> QuotationList(@Url String url);
    //@GET("b1s/v1/Quotations?$filter=DocumentStatus eq 'bost_Open'&$orderby=DocEntry desc &$top={top}")


    @POST("quotation/all_filter")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<QuotationResponse> OpenQuotationList(@Body HashMap<String,String> opportunityValue);

 /*   @GET("b1s/v1/SalesOpportunities/")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<OpportunitiesResponse> OpportunitiesList();*/
    @GET
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<OpportunitiesResponse> OpportunitiesList(@Url String url);

    @GET("b1s/v1/SalesOpportunities?$filter=Status eq 'sos_Open'&$orderby=SequentialNo desc")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<OpportunitiesResponse> OpportunitiesOpenList();
    @GET
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<OpportunitiesResponse> OpportunitiesOpenList(@Url String url);

    @GET("b1s/v1/SalesOpportunities?$filter=Status eq 'sos_Sold'&$orderby=SequentialNo desc")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<OpportunitiesResponse> OpportunitiesWonList();
    @GET
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<OpportunitiesResponse> OpportunitiesWonList(@Url String url);

    @GET("b1s/v1/SalesOpportunities?$filter=Status eq 'sos_Missed'&$orderby=SequentialNo desc")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<OpportunitiesResponse> OpportunitiesLostList();

    @GET
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<OpportunitiesResponse> OpportunitiesLostList(@Url String url);

    //@GET("b1s/v1/Invoices?$filter=DocDueDate lt '2021-02-05'")
    @GET
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<InvoiceResponse> InvoicesOverDueList(@Url String url);

    @GET("b1s/v1/Invoices/")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<QuotationResponse> InvoicesList();

    @GET
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<InvoiceResponse> InvoicesList(@Url String url);

    @POST("order/all_filter")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<QuotationResponse> OrdersList(@Body  HashMap<String,String> opportunityValue);
    @GET
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<QuotationResponse> OrdersList(@Url String url);

    @GET("b1s/v1/Orders?$filter=DocumentStatus eq 'bost_Open'&$orderby=DocEntry desc")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<QuotationResponse> OrdersOpenList();
    @GET
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<QuotationResponse> OrdersOpenList(@Url String url);

    @GET("b1s/v1/DeliveryNotes?$filter=DocumentStatus eq 'bost_Open'")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<QuotationResponse> deliveryOpenList();
    @GET("b1s/v1/DeliveryNotes?$filter=DocumentStatus eq 'C' and Cancelled eq 'tNO'")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<QuotationResponse> deliveryCloseList();
   // @GET("b1s/v1/DeliveryNotes?$filter=DocDueDate lt '2021-02-05'")
    @GET
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<QuotationResponse> deliveryOverDueList(@Url String url);
    @GET("b1s/v1/DeliveryNotes/")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<QuotationResponse> deliveryList();

    @GET("b1s/v1/InventoryGenEntries/")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<InvoiceResponse> inventoryList();







    @POST("item/all")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<ItemResponse> ItemsList(@Body ItemCategoryData url);

    @GET("b1s/v1/SalesTaxCodes?$select=Code")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<TaxItemResponse> taxcodes();

    /*******************  Add New APIs  *********************/
    @POST("b1s/v1/Quotations")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<QuotationResponse> NewQuotation(@Body NewQuotation in);

    @POST("order/create")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<QuotationResponse> addOrder(@Body AddQuotation in);
    @POST("quotation/create")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<QuotationResponse> addQuotation(@Body AddQuotation in);
    @POST("b1s/v1/SalesOpportunities")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<QuotationResponse> addOpportunity(@Body AddOpportunity in);

       /*******************  Update APIs  *********************/

    //@PUT("b1s/v1/Quotations({id})")
    @POST("quotation/update")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<QuotationResponse> updateQuotation( @Body UpdateQuotationModel model);


    @PATCH("b1s/v1/SalesOpportunities({id})")

    Call<QuotationResponse> updateOpportunity(@Path("id") String id, @Body AddOpportunity model);

    @PATCH("b1s/v1/SalesOpportunities({id})")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<QuotationResponse> updateFavorite(@Path("id") String id, @Body UpdateFavourites model);

    @POST("opportunity/fav")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<NewOppResponse> updateoppFavorite( @Body UpdateFavourites model);

    @POST("order/update")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<QuotationResponse> updateOrder( @Body UpdateQuotationModel model);

    @PATCH("b1s/v1/BusinessPartners('{id}')")
    Call<QuotationResponse> AddContact(@Path("id") String id, @Body ContactExtension model);

    @POST("b1s/v1/BusinessPartners")
    Call<QuotationResponse> AddBP( @Body PostBP model);

    @POST("opportunity/update")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<NewOppResponse> newUpdateOpportunity( @Body AddOpportunityModel model);

    @PATCH("b1s/v1/BusinessPartners('{id}')")
    Call<QuotationResponse> AddBP(@Path("id") String id, @Body PostBP model);



  /*  @GET("b1s/v1/SalesPersons/")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<SaleEmployeeResponse> getSalesEmplyeeList();*/
   /* @GET("employee/all")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<SaleEmployeeResponse> getSalesEmplyeeList();*/

      @POST("employee/all_filter")
      @Headers({ "Content-Type: application/json;charset=UTF-8"})
      Call<SaleEmployeeResponse> getSalesEmplyeeList(@Body EmployeeValue employeeValue);

    @POST("businesspartner/employee/all")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<ContactPerson> ContactEmployeesList(@Body ContactPersonData businessPartnerData);



    @GET("b1s/v1/Countries/")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<AdressDetail> getCountryName();

    @GET
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<AdressDetail> getCountryName(@Url String url);

    @GET("b1s/v1/States/")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<StateDetail> getStateName();
    @GET
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<StateDetail> getStateName(@Url String url);

    /*@GET("b1s/v1/Departments/")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<DepartMentDetail> getDepartMent();*/

    @GET("businesspartner/department/all")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<DepartMentDetail> getDepartMent();

    /*@GET("b1s/v1/EmployeePosition/")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<RoleListDetail> getRole();*/

    @GET("businesspartner/position/all")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<RoleListDetail> getRole();

    @GET("XSJS/SalesDashBoard.xsjs?User=E02558&DBName=JCSPL&Position=Level1")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<String> getNewData();


        /******************** Counter Apis****************************/

        @GET("b1s/v1/EmployeesInfo?$select=EmployeeID,FirstName,MiddleName,LastName")
        @Headers({ "Content-Type: application/json;charset=UTF-8"})
        Call<OwnerResponse> Employees_Owener_List();
        @GET("b1s/v1/Quotations?$apply=aggregate($count as Count)")
        @Headers({ "Content-Type: application/json;charset=UTF-8"})
        Call<CounterResponse> QuotationCount();
        @GET("b1s/v1/SalesOpportunities?$apply=aggregate($count as Count)")
        @Headers({ "Content-Type: application/json;charset=UTF-8"})
        Call<CounterResponse> OpportunityCount();
        @GET("b1s/v1/Orders?$apply=aggregate($count as Count)")
        @Headers({ "Content-Type: application/json;charset=UTF-8"})
        Call<CounterResponse> OrdersCount();


        @POST("employee/invoice_counter")
        @Headers({ "Content-Type: application/json;charset=UTF-8"})
        Call<CounterResponse> InvoicesCount(@Body SalesEmployeeItem salesEmployeeItem);
        @GET("b1s/v1/BusinessPartners?$apply=aggregate($count as Count)")
        @Headers({ "Content-Type: application/json;charset=UTF-8"})
        Call<CounterResponse> CustomerCount();

        /************************** one Time/Same Apis  *****************************/

        @GET("b1s/v1/SalesStages/")
        @Headers({ "Content-Type: application/json;charset=UTF-8"})
        Call<StagesResponse> getStagesList();

        @GET("industries/all")
        @Headers({ "Content-Type: application/json;charset=UTF-8"})
        Call<IndustryResponse> getIndustryList();

    /************************** Profile  Apis  *****************************/
    @GET
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<UserIDResponse> getUserID(@Url String url);
    @GET
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<UserResponse> getUserProfile(@Url String url);

    @GET
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<UserProfile> getUserProfileDetail(@Url String url);



          /******************************* Hana Apis **********************************/

          @GET("SalesApp_Cinntra_Test/Opportunity/GetOpportunity.xsjs?DBName=TEST")
          @Headers({ "Content-Type: application/json;charset=UTF-8"})
          Call<String> TestApi();

 /*   @GET
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<InventoryResponse> getAllInventories(@Url String url);
*/

    @GET
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<DeliveryResponse> getOpenDeliveries(@Url String url);
    @GET
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<DeliveryResponse> getCloseDeliveries(@Url String url);
    @GET
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<DeliveryResponse> getOverDueDeliveries(@Url String url);

    @GET
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<SalesTargetResponse> getSalesTarget(@Url String url);

    @POST("employee/top5itembyamount")
    Call<Top5ItemResponse> getTop5Items(@Body HashMap<String,String> hs);
    @POST("employee/top5bp")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<Top5CustomerResponse> getTop5Customer(@Body HashMap<String,String> hs);
  /*  @GET
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<HeirarchiResponse> getHeirarchi(@Url String url);*/


    @POST("employee/all_filter")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<HeirarchiResponse> getAllEmployeelist(@Body EmployeeValue employeeValue);

    @POST("businesspartner/branch/all")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<BranchResponse> getBranch(@Body Branch branch);

    @GET
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<UserCounterResponse> getUserCounter(@Url String url);
    @GET
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<UserLoginCredential> getLogIn(@Url String url);

/*    @GET("b1s/v1/PaymentTermsTypes/")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<PayMentTermsDetail> getPaymentTerm();*/
    @GET("paymenttermstypes/all")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<PayMentTermsDetail> getPaymentTerm();






    @POST("quotation/fav")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<QuotationResponse> updateFavQuotation( @Body QuotationItem model);





    @POST("stage/all")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<OpportunityStageResponse> getAllStages(@Body OpportunityItem model);
    @POST("stage/create")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<OpportunityStageResponse> createStages(@Body StagesValue model);


    @POST("demo/create")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<DemoResponse> createDemo(@Body DemoValue model);

    /*@GET("opportunity/all")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<NewOppResponse> allopportinitylist();*/

    @POST("opportunity/all_filter")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<NewOppResponse> allopportinitylist(@Body OpportunityValue opportunityValue);


    @POST("opportunity/one")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<NewOppResponse> getparticularopportunity(@Body OpportunityValue opportunityValue);

    @POST("activity/chatter_all")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<ChatResponse> getAllChat(@Body StagesValue opportunityValue);

    @POST("activity/chatter")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<ChatResponse> createChat(@Body ChatModel opportunityValue);

    @POST("opportunity/create")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<NewOppResponse> createopportunity(@Body AddOpportunityModel opportunityValue);


    @POST("lead/all_filter")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<LeadResponse> getAllLead(@Body LeadFilter leadValue);

    @POST("activity/create")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<EventResponse> createnewevent(@Body EventValue eventValue);

    @POST("activity/followup")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<FollowUpResponse> createfollowUP(@Body FollowUpData eventValue);


    @POST("activity/all")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<EventResponse> getallevent(@Body EventValue eventValue);


    @POST("activity/delete")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<EventResponse> deleteEvent(@Body EventValue eventValue);


    @POST("activity/one")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<EventResponse> particularevent(@Body EventValue eventValue);


    @POST("activity/update")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<EventResponse> updateevent(@Body EventValue eventValue);


    @POST("opportunity/change_stage")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<OpportunityStageResponse> updatestage(@Body StagesValue stval);


    @POST("opportunity/complete")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<OpportunityStageResponse> completestage(@Body CompleteStageResponse stval);


    @POST("employee/login")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<NewLogINResponse> loginEmployee(@Body LogInDetail logInDetail);


    @POST("login/")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<NewLogINResponse> sessionlogin(@Body HashMap<String,String> logInDetail);


    @POST("stage/stage_detail")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<OpportunityStageResponse> getStagesComment(@Body StagesValue oppitem);


    @POST("lead/one")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<LeadResponse> particularlead(@Body LeadValue leadValue);

    @POST("lead/lead_attachments")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<LeadDocumentResponse> particularleadattachment(@Body HashMap<String,Integer> leadValue);

    @GET("businesspartner/all")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<CustomerBusinessRes> getAllBusinessPartner();

    @POST("businesspartner/create")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<CustomerBusinessRes> addnewCustomer(@Body AddBusinessPartnerData in);

    @POST("businesspartner/update")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<CustomerBusinessRes> updatecustomer(@Body AddBusinessPartnerData businessPartnerData);

    @POST("businesspartner/one")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<CustomerBusinessRes> particularcustomerdetails(@Body BusinessPartnerData businessPartnerData);

    @POST("businesspartner/employee/all")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<ContactPerson> contactemplist(@Body ContactPersonData contactPersonData);


    @POST("businesspartner/employee/create")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<QuotationResponse> createcontact(@Body  CreateContactData contactData);


    @POST("businesspartner/branch/create")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<BranchResponse> addBranch(@Body Branch branch);


    @GET("countries/all")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<CountryResponse> getCountryList();


    @POST("states/all")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<StateRespose> getStateList(@Body StateData stateData);


    @POST("businesspartner/branch/update")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<BranchResponse> updateBranch(@Body Branch branch);


    @POST("employee/dashboard")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<CounterResponse> dashboardcounter(@Body SalesEmployeeItem salesEmployeeItem);

    @POST("employee/analytics")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<CounterResponse> projectiondata(@Body SalesEmployeeItem salesEmployeeItem);


    @POST("activity/maps")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<MapResponse> sendMaplatlong(@Body MapData mapData);

    @POST("notification/all")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<NotificationResponse> allnotification(@Body SalesEmployeeItem salesEmployeeItem);

    @POST("activity/all_filter")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<EventResponse> getcalendardata(@Body SalesEmployeeItem eventValue);


    @POST("order/delivery")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<QuotationStringResponse> orderlist(@Body SalesEmployeeItem salesEmployeeItem);


    @POST("activity/status")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<EventResponse> completeEvent(@Body EventValue eventValue);

    @POST("lead/create")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<LeadResponse> createLead(@Body ArrayList<CreateLead> createLeads);
/*
    @POST("lead/chatter_all")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<ChatResponse> getAllLeadChat(@Body LeadChatModel opportunityValue);*/


    @POST("activity/chatter_all")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<ChatResponse> getAllLeadChat(@Body FollowUpData opportunityValue);


    @POST("lead/chatter")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<ChatResponse> createleadChat(@Body ChatModel chatModel);


    @GET("invoice/all")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<InvoiceResponse> getallinvoice();


    @POST("employee/one")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<EmployeeProfile> getProfileDetail(@Body EmpDetails empDetails);


    @GET("lead/type_all")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<LeadTypeResponse> getLeadType();

    @GET("lead/source_all")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<LeadTypeResponse> getsourceType();

    @POST("notification/read")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<NotificationResponse> readnotification(@Body NotificationData nd);


    @POST("lead/update")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<LeadResponse> updateLead(@Body CreateLead lv);


    @GET("item/category_all")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<ItemCategoryResponse> getAllCategory();


    @POST("category/all_filter")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<ItemCategoryResponse> getAllCategory(@Body EmployeeValue opportunityValue);


    @POST("employee/create")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<LeadResponse> createdemoEmployee(@Body NewEmployeeUser newEmployeeUser);



    @GET("campset/all")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<CampaignResponse> getAllCampaign();


    @POST("campset/create")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<CampaignResponse> createCampaign(@Body AddCampaignModel campaignModel);


    @POST("campset/one")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<CampaignResponse> getCampsetDetails(@Body CampaignModel cm);

    @POST("camp/filter_campaign")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<CampaignListModel> getmemberlist(@Body CampaignListResponse cm);

    @GET("businesspartner/alltype")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<BPTypeResponse> getBptypelist();

    @GET("opportunity/alltype")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<BPTypeResponse> getopptypelist();

    @POST("lead/lead_attachment_create")
    Call<LeadResponse> updateLeadattachment(@Body MultipartBody requestBody);


    @POST("employee/movingitems")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<InventoryResponse> getInventorylist(@Body  HashMap<String, String> st);


    @GET("expense/all")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<ExpenseNewModelResponse> getAllExpense();

    @GET("payment/all")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<PaymentRespnse> getAllpaymentDetails();

    @POST("expense/create")
    Call<ExpenseResponse> createexpense(@Body MultipartBody requestBody);


    @POST("payment/create")
    Call<PaymentRespnse> createpaymentdetails(@Body MultipartBody requestBody);

    @POST("expense/update")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<ExpenseResponse> updateexpense(@Body MultipartBody requestBody);

    @POST("payment/update")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<ExpenseResponse> updatepayment(@Body MultipartBody requestBody);

    @POST("expense/delete")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<ExpenseResponse> deleteexpense(@Body HashMap<String,List<String>> hd);

    @POST("payment/delete")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<PaymentRespnse> deletepayment(@Body HashMap<String, List<Integer>> hd);

    @POST("activity/map_filter")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<MapResponse> getmaplocation(@Body  HashMap<String, String> mapData);
}

