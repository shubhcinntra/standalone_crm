package com.cinntra.standalone.fragments;


 import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
 import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.cinntra.standalone.R;
 import com.cinntra.standalone.activities.AddOpportunityActivity;
 import com.cinntra.standalone.activities.ItemsList;
 import com.cinntra.standalone.activities.MainActivity;
 import com.cinntra.standalone.activities.OwnerList;
 import com.cinntra.standalone.activities.SelectedItems;
 import com.cinntra.standalone.adapters.ContactPersonAdapter;
import com.cinntra.standalone.adapters.SalesEmployeeAdapter;
import com.cinntra.standalone.adapters.StageSpinnerAdapter;
 import com.cinntra.standalone.databinding.UpdateOpportunityBinding;
 import com.cinntra.standalone.globals.Globals;
 import com.cinntra.standalone.model.ContactPersonData;
 import com.cinntra.standalone.model.NewOppResponse;
 import com.cinntra.standalone.model.OwnerItem;
import com.cinntra.standalone.model.QuotationResponse;
import com.cinntra.standalone.model.SalesEmployeeItem;
import com.cinntra.standalone.model.SalesOpportunitiesLines;
import com.cinntra.standalone.model.StagesItem;
 import com.cinntra.standalone.newapimodel.AddOpportunityModel;
 import com.cinntra.standalone.newapimodel.NewOpportunityRespose;
 import com.cinntra.standalone.viewModel.ItemViewModel;
 import com.cinntra.standalone.webservices.NewApiClient;
 import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class Opportunity_Update_Fragment extends Fragment implements View.OnClickListener {

    FragmentActivity act;
    int salesEmployeeCode = 0;
    String OppID = "";
    public  int ITEMSVIEWCODE   = 10000;

//    @BindView(R.id.head_title)
//    TextView head_title;
//    @BindView(R.id.back_press)
//    RelativeLayout back_press;
//    @BindView(R.id.mainContainer)
//    LinearLayout mainContainer;
//    @BindView(R.id.opportunity_name_value)
//    EditText opportunity_name_value;
//    @BindView(R.id.business_partner_value)
//    TextView business_partner_value;
//    @BindView(R.id.contact_person_spinner)
//    Spinner contact_person_spinner;
//    @BindView(R.id.close_date_value)
//    EditText close_date_value;
//    @BindView(R.id.opportunity_owner_value)
//    EditText opportunity_owner_value;
//    @BindView(R.id.sales_employee_spinner)
//    Spinner sales_employee_spinner;
//    @BindView(R.id.type_value)
//    TextView type_value;
//    @BindView(R.id.probability_value)
//    EditText probability_value;
//    @BindView(R.id.potential_amount_value)
//    EditText potential_amount_value;
//    @BindView(R.id.stage_spinner)
//    Spinner stage_spinner;
//    @BindView(R.id.description_value)
//    EditText description_value;
//    @BindView(R.id.bussinessPartner)
//    RelativeLayout bussinessPartner;
//    @BindView(R.id.owener)
//    RelativeLayout owener;
//    @BindView(R.id.submit_button)
//    Button submit_button;
//    @BindView(R.id.lead_source_value)
//    TextView lead_source_value;
//
//
//    @BindView(R.id.startDate)
//    RelativeLayout startDate;
//    @BindView(R.id.start_date_value)
//    EditText start_date_value;
//    @BindView(R.id.lead_value)
//    EditText lead_value;
//    @BindView(R.id.startcalender)
//    ImageView startcalender;
//    @BindView(R.id.closeDate)
//    RelativeLayout closeDate;
//    @BindView(R.id.closeCalender)
//    ImageView closeCalender;
//    @BindView(R.id.add)
//    ImageView add;
//    @BindView(R.id.ok)
//    ImageView ok;
//    @BindView(R.id.itemCount)
//    TextView itemCount;
//    @BindView(R.id.item_frame)
//    RelativeLayout item_frame;
    public static int OWNERCODE   = 1001;
    private static  boolean ESCAPED = true;

    NewOpportunityRespose opportunityItem;
    String salesEmployeename = "";
    String ContactPersonName     = "";
    String DataOwnershipfield = "";

    UpdateOpportunityBinding binding;

    public Opportunity_Update_Fragment()
      {
    //Required empty public constructor
      }


    // TODO: Rename and change types and number of parameters
    public static Opportunity_Update_Fragment newInstance(String param1, String param2) {
        Opportunity_Update_Fragment fragment = new Opportunity_Update_Fragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
     Bundle b      = getArguments();
     opportunityItem =(NewOpportunityRespose) b.getParcelable(Globals.OpportunityItem);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
           ViewGroup container,
            Bundle savedInstanceState) {

        act = getActivity();
        binding=UpdateOpportunityBinding.inflate(inflater,container,false);
       // View v=inflater.inflate(R.layout.update_opportunity, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        binding.headerBottomroundEdit.headTitle.setText(getString(R.string.update_opportunity));
       // getActivity().getActionBar().hide();
        binding.headerBottomroundEdit.backPress.setOnClickListener(this);
//        setDisable();
        Globals.SelectedItems.clear();
        setEnable();
        setDefaults();
        setData();


        binding.itemFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Globals.SelectedItems.size()==0) {
                    /*Globals.ItemType = "Paid";
                    openCategorydailog();*/
                    Intent intent = new Intent(getContext(), ItemsList.class);
                    intent.putExtra("CategoryID",0);
                    startActivityForResult(intent, ITEMSVIEWCODE);
                }
                else {
                    Intent intent = new Intent(getContext(), SelectedItems.class);
                    intent.putExtra("FromWhere","addQt");
                    startActivityForResult(intent, ITEMSVIEWCODE);
                }
            }
        });

        return binding.getRoot() ;
    }

    private void setDisable() {
        binding.headerBottomroundEdit.add.setOnClickListener(this);

        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.opportunityNameValue.getApplicationWindowToken(), 0);

        binding.opportunityNameValue.setFocusable(false);
        binding.opportunityNameValue.setClickable(false);

        binding.contactPersonSpinner.setEnabled(false);

        binding.opportunityOwnerValue.setClickable(false);
        binding.opportunityOwnerValue.setFocusable(false);

        binding.salesEmployeeSpinner.setEnabled(false);

        binding.potentialAmountValue.setFocusable(false);
        binding.potentialAmountValue.setClickable(false);

        binding.stageSpinner.setEnabled(false);
        binding.probabilityValue.setFocusable(false);
        binding.probabilityValue.setClickable(false);

        binding.descriptionValue.setClickable(false);
        binding.descriptionValue.setFocusable(false);

        binding.startDateValue.setClickable(false);
        binding.startDateValue.setFocusable(false);
        binding.startcalender.setEnabled(false);

        binding.submitButton.setVisibility(View.GONE);
        binding.submitButton.setEnabled(false);
        binding.submitButton.setClickable(false);
        binding.submitButton.setFocusable(false);

        binding.headerBottomroundEdit.add.setVisibility(View.VISIBLE);
        binding.headerBottomroundEdit.ok.setVisibility(View.GONE);
        ESCAPED = true;
    }

    String salesPersonCode = "";
    String ContactPersonCode = "";
    String CurrentStage = "";
    String Ownershipfield = "";
    private void setData()
      {
          binding.leadValue.setText(opportunityItem.getU_LEADNM());
          salesEmployeename = opportunityItem.getSalesPersonName();
          ContactPersonName = opportunityItem.getContactPersonName();
    salesPersonCode   = opportunityItem.getSalesPerson();
    ContactPersonCode = opportunityItem.getContactPerson();
    CurrentStage      = opportunityItem.getCurrentStageNo();
    Ownershipfield    = opportunityItem.getDataOwnershipfield();
    OppID= opportunityItem.getSequentialNo();
    binding.opportunityNameValue.setText(opportunityItem.getOpportunityName());
    binding.businessPartnerValue.setText(opportunityItem.getCardCode());
    binding.leadSourceValue.setText(opportunityItem.getULsource());
    binding.closeDateValue.setText(opportunityItem.getPredictedClosingDate());
    binding.startDateValue.setText(opportunityItem.getStartDate());
    binding.descriptionValue.setText(opportunityItem.getRemarks());
    if(opportunityItem.getUType().size()>0)
    binding.typeValue.setText(opportunityItem.getUType().get(0).getType());
    binding.probabilityValue.setText(opportunityItem.getUProblty());
    binding.potentialAmountValue.setText(opportunityItem.getMaxLocalTotal());
    binding.opportunityOwnerValue.setText(opportunityItem.getDataOwnershipName());
    salesEmployeeCode = Integer.parseInt(opportunityItem.getSalesPerson());
          Globals.SelectedItems.addAll(opportunityItem.getOppItem());
          binding.itemCount.setText("Item ("+Globals.SelectedItems.size()+")");
//    CurrentStage = opportunityItem.getSalesOpportunitiesLines().get( opportunityItem.getSalesOpportunitiesLines().size()-1).getStageKey();
    if(Globals.checkInternet(getActivity()))
        callContactApi(opportunityItem.getCardCode());
    }


  private void setDefaults() {
   
   

   binding.startDateValue.setOnClickListener(this);
   binding.startDate.setOnClickListener(this);
   binding.startcalender.setOnClickListener(this);
   binding.closeCalender.setOnClickListener(this);
   binding.headerBottomroundEdit.ok.setOnClickListener(this);
   binding.closeDate.setOnClickListener(this);
   binding.closeDateValue.setOnClickListener(this);
   binding.submitButton.setOnClickListener(this);
   binding.opportunityOwnerValue.setOnClickListener(this);
    }
    ArrayList<SalesOpportunitiesLines> jsonlist = new ArrayList<>();

    @Override
    public void onClick(View v) {
    switch (v.getId())
        {
    case R.id.back_press:

     ((AppCompatActivity) getActivity()).getSupportActionBar().show();
      InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
      imm.hideSoftInputFromWindow(binding.opportunityNameValue.getApplicationWindowToken(), 0);
      getActivity().finish();

            break;
    case R.id.add:
         if(ESCAPED){
         Globals.openKeyboard(getContext());
         setEnable();
         setDefaults();
           }else {
         setDisable();
           }
         break;
            case R.id.startDate:
            case R.id.startcalender:
            case R.id.start_date_value:
                Globals.selectDate(act,binding.startDateValue);
                break;
            case R.id.close_date:
            case R.id.closeDate:
            case R.id.close_date_value:
                Globals.selectDate(act,binding.closeDateValue);
                break;

            case R.id.opportunity_owner_value:
                Intent ii = new Intent(getActivity(), OwnerList.class);
                startActivityForResult(ii,OWNERCODE);
                break;
    case R.id.submit_button:
    case R.id.ok:
                setDisable();
                String cardValue    = binding.businessPartnerValue.getText().toString().trim();
                String       remark = binding.descriptionValue.getText().toString().trim();
                if(validation(cardValue,salesEmployeeCode,CurrentStage,binding.potentialAmountValue.getText().toString().trim(),remark)) {
                    SalesOpportunitiesLines dc = new SalesOpportunitiesLines();
                    dc.setSalesPerson(salesEmployeeCode);
                    dc.setDocumentType("bodt_MinusOne");
                    dc.setMaxSystemTotal(Float.valueOf(binding.potentialAmountValue.getText().toString().trim()));
                    dc.setMaxLocalTotal(Float.valueOf(binding.potentialAmountValue.getText().toString().trim()));
                    dc.setStageKey(CurrentStage);
                    jsonlist.add(dc);

                    AddOpportunityModel obj = new AddOpportunityModel();
                    obj.setMaxSystemTotal(binding.potentialAmountValue.getText().toString().trim());
                    obj.setOpportunityName(binding.opportunityNameValue.getText().toString().trim());
                    obj.setClosingDate(binding.closeDateValue.getText().toString().trim());
                    obj.setStartDate(opportunityItem.getStartDate());
                    obj.setDataOwnershipfield(opportunityItem.getDataOwnershipfield());
                    obj.setUProblty(binding.probabilityValue.getText().toString().trim());
                    obj.setCardCode(cardValue); //cardcode
                    obj.setSalesPerson(String.valueOf(salesEmployeeCode)); //salesEmployeeCode
                    obj.setContactPerson(ContactPersonCode);
                    obj.setMaxLocalTotal(binding.potentialAmountValue.getText().toString().trim());//Potential Ammount
                    obj.setRemarks(remark);
                    obj.setId(opportunityItem.getId());
                    obj.setSequentialNo(opportunityItem.getSequentialNo());
                    obj.setSource("None");
                    obj.setStatus("sos_Open");
                    obj.setReasonForClosing("None");
                    obj.setTotalAmounSystem(opportunityItem.getTotalAmounSystem());
                    obj.setTotalAmountLocal(opportunityItem.getTotalAmountLocal());
                    obj.setCurrentStageNo(opportunityItem.getCurrentStageNo());
                    obj.setIndustry("None");
                    obj.setLinkedDocumentType("None");
                    obj.setStatusRemarks("None");
                    obj.setProjectCode("None");
                    obj.setCustomerName(opportunityItem.getCustomerName());
                    obj.setClosingType("sos_Days");
                    obj.setOpportunityType("boOpSales");
                    obj.setUpdateDate(Globals.getTodaysDatervrsfrmt());
                    obj.setUpdateTime(Globals.getTCurrentTime());
                    obj.setUType(opportunityItem.getUType().get(0).getId().toString());
                    obj.setULsource(opportunityItem.getULsource());
                    obj.setUFav(opportunityItem.getUFav());
                    obj.setSalesPersonName(salesEmployeename);
                    obj.setContactPersonName(ContactPersonName);
                    obj.setDataOwnershipName(salesEmployeename);
                    obj.setPredictedClosingDate(binding.closeDateValue.getText().toString().trim());
                    obj.setU_LEADID(opportunityItem.getU_LEADID());
                    obj.setU_LEADNM(binding.leadValue.getText().toString());
                    obj.setOppItem(Globals.SelectedItems);
                    obj.setSalesOpportunitiesLines(jsonlist);
                    if(Globals.checkInternet(getActivity()))
                        updateOpportunity(obj);
                }
                break;
        }



    }

    private void setEnable() {


        binding.opportunityNameValue.setFocusable(true);
        binding.opportunityNameValue.setClickable(true);
        binding.opportunityNameValue.setFocusableInTouchMode(true);

        binding.contactPersonSpinner.setEnabled(true);

        binding.opportunityOwnerValue.setClickable(true);
        binding.opportunityOwnerValue.setFocusable(true);

        binding.salesEmployeeSpinner.setEnabled(true);

        binding.potentialAmountValue.setFocusable(true);
        binding.potentialAmountValue.setClickable(true);
        binding.potentialAmountValue.setFocusableInTouchMode(true);

        binding.stageSpinner.setEnabled(true);
        binding.probabilityValue.setFocusable(true);
        binding.probabilityValue.setClickable(true);
        binding.probabilityValue.setFocusableInTouchMode(true);

        binding.descriptionValue.setClickable(true);
        binding.descriptionValue.setFocusable(true);
        binding.descriptionValue.setFocusableInTouchMode(true);

        binding.startDateValue.setClickable(true);
        binding.startDateValue.setFocusable(true);
        binding.startcalender.setEnabled(true);
        binding.submitButton.setVisibility(View.VISIBLE);
        binding.submitButton.setEnabled(true);
        binding.submitButton.setClickable(true);
        binding.submitButton.setFocusable(true);
        binding.headerBottomroundEdit.add.setVisibility(View.GONE);
        binding.headerBottomroundEdit.ok.setVisibility(View.GONE);

        ESCAPED = false;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == OWNERCODE&&resultCode == RESULT_OK)
        {
            OwnerItem ownerItem = (OwnerItem) data.getSerializableExtra(Globals.OwnerItemData);
            binding.opportunityOwnerValue.setText(ownerItem.getFirstName()+" "+ownerItem.getMiddleName()+" "+ownerItem.getLastName());
            DataOwnershipfield = ownerItem.getEmployeeID();
        }

    }

    private void eventManager()
       {
        binding.salesEmployeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
           {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(salesEmployeeItemList.size()>0&&position>0)
                {
                    salesEmployeename = salesEmployeeItemList.get(position).getSalesEmployeeName();
                    salesPersonCode = salesEmployeeItemList.get(position).getSalesEmployeeCode();
                    salesEmployeeCode = Integer.valueOf(salesPersonCode);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                salesEmployeeCode =Integer.valueOf(salesPersonCode);

            }
        });
        binding.stageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
           {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // if(stagesItemList.size()>0&&position>0)
                CurrentStage = stagesItemList.get(position).getStageno();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
               // CurrentStage = stagesItemList.get(0).getStageno();
            }
        });

         binding.contactPersonSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
           {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              //   ContactPersonName = ContactEmployeesList.get(position).getName();
                 ContactPersonCode = ContactEmployeesList.get(position).getInternalCode();
             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });

          /*if(Globals.OwnerList.size()>0&&Ownershipfield!=null)
           {
              int pos = Globals.getOwenerPo(Globals.OwnerList,Ownershipfield);
             opportunity_owner_value.setText(Globals.OwnerList.get(pos).getFirstName()+" "+Globals.OwnerList.get(pos).getLastName());
               DataOwnershipfield = Globals.OwnerList.get(pos).getEmployeeID();
             }
          else {
              if(Globals.checkInternet(getActivity()))
                callOwenerApi();

            }*/
    }



    List<StagesItem> stagesItemList = new ArrayList<>();
    private void callStagesApi()
         {
    ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
    model.getStagesList().observe(this, new Observer<List<StagesItem>>() {
            @Override
            public void onChanged(@Nullable List<StagesItem> itemsList) {
                if(itemsList == null || itemsList.size() == 0){
                    Globals.setmessage(getActivity());
                }else {
                    stagesItemList = itemsList;
                    binding.stageSpinner.setAdapter(new StageSpinnerAdapter(getActivity(),itemsList));
                    if(!itemsList.isEmpty()&&CurrentStage!=null)
                        binding.stageSpinner.setSelection(Globals.getSelectedStage(itemsList,CurrentStage));
                    eventManager();
                }
            }
        });
         }
    SalesEmployeeAdapter salesadapter;
    public List<SalesEmployeeItem> salesEmployeeItemList = new ArrayList<>();
    private void callSalessApi()
       {
    ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
    model.getSalesEmployeeList().observe(this, new Observer<List<SalesEmployeeItem>>() {
    @Override
    public void onChanged(@Nullable List<SalesEmployeeItem> itemsList)
     {
         if(itemsList == null || itemsList.size() == 0){
             Globals.setmessage(getActivity());
         }else {
             salesEmployeeItemList = itemsList;
             salesadapter = new SalesEmployeeAdapter(getActivity(),itemsList);
             binding.salesEmployeeSpinner.setAdapter(salesadapter);
            /* if(Globals.checkInternet(getActivity()))
                 callStagesApi();*/
             if(!itemsList.isEmpty()&&!salesPersonCode.isEmpty())
                 binding.salesEmployeeSpinner.setSelection(Globals.getSelectedSalesP(itemsList,salesPersonCode));
         }
     }
     });
    }

    private List<ContactPersonData>  ContactEmployeesList;
    ContactPersonAdapter contactPersonAdapter;

    private void callContactApi(String cardCode)
       {
    ContactEmployeesList = new ArrayList<>();
    ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
    model.getContactEmployeeList(cardCode).observe(getActivity(), new Observer<List<ContactPersonData>>() {
    @Override
    public void onChanged(@Nullable List<ContactPersonData> itemsList)
         {
             if(itemsList == null || itemsList.size() == 0){
                 Globals.setmessage(getActivity());
             }else {
               ContactEmployeesList = itemsList;
                 contactPersonAdapter =new ContactPersonAdapter(getActivity(),ContactEmployeesList);
                 binding.contactPersonSpinner.setAdapter(contactPersonAdapter);
                 //int index = ContactEmployeesList.get
                 if(Globals.checkInternet(getActivity()))
                     callSalessApi();
                 if(!itemsList.isEmpty()&&ContactPersonCode!=null)
                     binding.contactPersonSpinner.setSelection(Globals.getSelectedContact(itemsList,ContactPersonCode));
             }
        }
        });
    }


    private void callOwenerApi()
       {
//    ItemViewModel model = ViewModelProviders.of(this).get(ItemViewModel.class);
//    model.getEmployeesList(null).observe(this, new Observer<List<OwnerItem>>() {
//            @Override
//     public void onChanged(@Nullable List<OwnerItem> itemsList) {
//                if(itemsList == null || itemsList.size() == 0){
//                    Globals.setmessage(getActivity());
//                }else {
//                    Globals.OwnerList.clear();
//                    Globals.OwnerList.addAll(itemsList);
//
//                if(Ownershipfield!=null)
//                    {
//                 int pos = Globals.getOwenerPo(Globals.OwnerList, Ownershipfield);
//                 opportunity_owner_value.setText(itemsList.get(pos).getFirstName() + " " + itemsList.get(pos).getLastName());
//                 DataOwnershipfield = Globals.OwnerList.get(pos).getEmployeeID();
//                    }
//
//
//                }
//         }
//      });

           if(MainActivity.ownerListFromLocal == null || MainActivity.ownerListFromLocal.size() == 0){
                    Globals.setmessage(getActivity());
                }else {
                    Globals.OwnerList.clear();
                    Globals.OwnerList.addAll(MainActivity.ownerListFromLocal);

                if(Ownershipfield!=null)
                    {
                 int pos = Globals.getOwenerPo(Globals.OwnerList, Ownershipfield);
                 binding.opportunityOwnerValue.setText(MainActivity.ownerListFromLocal.get(pos).getFirstName() + " " + MainActivity.ownerListFromLocal.get(pos).getLastName());
                 DataOwnershipfield = Globals.OwnerList.get(pos).getEmployeeID();
                    }


                }


    }

    @Override
    public void onResume() {
        super.onResume();
        binding.itemCount.setText("Item ("+Globals.SelectedItems.size()+")");

    }

    private void updateOpportunity(AddOpportunityModel in)
       {
    Call<NewOppResponse> call = NewApiClient.getInstance().getApiService().newUpdateOpportunity(in);
    call.enqueue(new Callback<NewOppResponse>() {
    @Override
    public void onResponse(Call<NewOppResponse> call, Response<NewOppResponse> response) {
     if(response.code()==200)
      {
      Globals.SelectedItems.clear();
         Toast.makeText(getActivity(), "Posted Successfully.", Toast.LENGTH_SHORT).show();
      getActivity().onBackPressed();
      }
      else
                {
                    //Globals.ErrorMessage(CreateContact.this,response.errorBody().toString());
                    Gson gson = new GsonBuilder().create();
                    QuotationResponse mError = new QuotationResponse();
                    try {
                        String s =response.errorBody().string();
                        mError= gson.fromJson(s,QuotationResponse.class);
                        Toast.makeText(getActivity(), mError.getError().getMessage().getValue(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        //handle failure to read error
                    }
                    //Toast.makeText(CreateContact.this, msz, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<NewOppResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }






    private boolean validation(
            String cardCode,int
            salesEmployeeCode,String stagesCode,
            String potentialAmount,String remark)
       {
        if(cardCode.isEmpty())
        {

            Globals.showMessage(act,getString(R.string.can_not_empty));
            return false;
        }
        else if(salesEmployeeCode==0){
            Globals.showMessage(act,getString(R.string.can_not_empty));
            return false;
        }

        else if(remark.isEmpty()){
            binding.descriptionValue.requestFocus();
            binding.descriptionValue.setError(getString(R.string.description_error));
            Globals.showMessage(act,getString(R.string.description_error));
            return false;
        }

        return true;
    }


}