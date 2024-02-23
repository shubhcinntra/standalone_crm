package com.cinntra.standalone.fragments;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.cinntra.standalone.R;
import com.cinntra.standalone.databinding.FragmentAddEventBinding;
import com.cinntra.standalone.globals.Globals;
import com.cinntra.standalone.model.EventResponse;
import com.cinntra.standalone.model.EventValue;
import com.cinntra.standalone.model.QuotationResponse;
import com.cinntra.standalone.newapimodel.NewOpportunityRespose;
import com.cinntra.standalone.receivers.NotificationPublisher;
import com.cinntra.standalone.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityAddEventDialogue extends DialogFragment implements View.OnClickListener {

//    @BindView(R.id.head_title)
//    TextView head_title;
//    @BindView(R.id.back_press)
//    RelativeLayout back_press;
//    TextView pass_date;
//    @BindView(R.id.from_value)
//    EditText from_date_value;
//    @BindView(R.id.related_document_value)
//    EditText related_document_value;
//    @BindView(R.id.description_text)
//    EditText description_text;
//    @BindView(R.id.host_text)
//    EditText host_text;
//    @BindView(R.id.title_text)
//    EditText title_text;
//    @BindView(R.id.add_location_text)
//    EditText add_location_text;
//    @BindView(R.id.participant_value)
//    EditText participant_value;
//    @BindView(R.id.colorSpinner_view)
//    LinearLayout colorSpinner_view;
//    @BindView(R.id.spinnerview)
//    LinearLayout spinnerview;
//    @BindView(R.id.to_view)
//    RelativeLayout to_view;
//    @BindView(R.id.from_view)
//    RelativeLayout from_view;
//    @BindView(R.id.to_value)
//    EditText to_date_value;
//    @BindView(R.id.submit_button)
//    Button submit;
//    @BindView(R.id.upload_button)
//    Button upload_button;
//    @BindView(R.id.simple_switch)
//    Switch simple_switch;
//    @BindView(R.id.spinner)
//    Spinner preority_spinner;
//    @BindView(R.id.color_spinner)
//    Spinner colorspin;
//    @BindView(R.id.time_view)
//    RelativeLayout time_view;
//    @BindView(R.id.time_value)
//    TextView time_value;
//    @BindView(R.id.add)
//    ImageView add;
//    @BindView(R.id.loader)
//    ProgressBar loader;
    String priority = "";
    String allday = "";
    String repeated = "";
    int t1hr,t1min;
    EventPrerioritySpinner eventPrerioritySpinner;
    EventTextSpinner eventTextSpinner;

    ArrayList<String> categories   = new ArrayList<>();
    ArrayList<Integer> circleimage = new ArrayList<>();
    final Calendar myCalendar      = Calendar.getInstance();

    private AlarmManager alarmManager;
    private Calendar myTime;
    NewOpportunityRespose opportunityItem;

    FragmentAddEventBinding binding;

    public ActivityAddEventDialogue()
      {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }
    public static ActivityAddEventDialogue newInstance(String title)
     {
        ActivityAddEventDialogue frag = new ActivityAddEventDialogue();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }
      @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
      {
    super.onCreate(savedInstanceState);
          if (getArguments() != null) {
              Bundle b      = getArguments();
              opportunityItem =(NewOpportunityRespose) b.getParcelable(Globals.OpportunityItem);
          }

    setStyle(DialogFragment.STYLE_NORMAL,
    android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
      }
      @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
    Bundle savedInstanceState)
      {
          binding=FragmentAddEventBinding.inflate(inflater,container,false);
    View v = inflater.inflate(R.layout.fragment_add_event, container);
    //ButterKnife.bind(this,v);
   setDefaults();
   //loadData();


          binding.eventContent.simpleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                  if(isChecked){
                      allday = "All day";
                  }else{
                      allday = "One day";
                  }
              }
          });
    return binding.getRoot();
      }
      @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    binding.headerBottomroundEdit.headTitle.setText("New Event");
    binding.headerBottomroundEdit.backPress.setOnClickListener(this);
    binding.headerBottomroundEdit.add.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
           {
    if(v.getId()==R.id.back_press)
      getDialog().dismiss();
     else if(v.getId()==R.id.submit_button)
          {

            String title      = binding.eventContent.title.getText().toString().trim();
            String fromDate   = binding.eventContent.fromValue.getText().toString().trim();
            String toDate     = binding.eventContent.toValue.getText().toString().trim();
            String location   = binding.eventContent.addLocationText.getText().toString().trim();
            String host       = binding.eventContent.hostText.getText().toString().trim();
            String partcipant = binding.eventContent.participantValue.getText().toString().trim();
            String desc       = binding.eventContent.descriptionText.getText().toString().trim();
            String related    = binding.eventContent.relatedDocumentValue.getText().toString().trim();
            String time       = binding.eventContent.timeValue.getText().toString().trim();

            if(validation(title,fromDate,toDate,location,host,partcipant,desc,related,time)){
                EventValue eventValue = new EventValue();
                eventValue.setOppId(Integer.valueOf(opportunityItem.getId()));
                eventValue.setTitle(title);
                eventValue.setDescription(desc);
                eventValue.setFrom(fromDate);
                eventValue.setTo(toDate);
                eventValue.setEmp(Integer.parseInt(Prefs.getString(Globals.EmployeeID,"")));
                eventValue.setCreateTime(Globals.getTCurrentTime());
                eventValue.setCreateDate(Globals.getTodaysDate());
                eventValue.setType("Event");
                eventValue.setSourceType("Opportunity");
                eventValue.setParticipants(partcipant);
                eventValue.setComment("");
                eventValue.setSubject("");
                eventValue.setTime(Globals.getTCurrentTime());
                eventValue.setDocument("");
                eventValue.setRelatedTo(related);
                eventValue.setLocation(location);
                eventValue.setHost(host);
                eventValue.setAllday(allday);
                eventValue.setName(opportunityItem.getCustomerName());
                eventValue.setProgressStatus("WIP");
                eventValue.setPriority(priority);
                eventValue.setRepeated(repeated);




                if(Globals.checkInternet(getContext())) {
                    binding.loader.loader.setVisibility(View.VISIBLE);
                  callApi(eventValue);
                }


            }

             /* NewEvent event = new NewEvent(title,fromDate,toDate,true,"Repeate",location,
             host,"top",partcipant,desc,related,1,time);
              TaskEventList.add(event);*/

             // saveData();


        }
    }

    private void callApi(EventValue eventValue) {

        Call<EventResponse> call = NewApiClient.getInstance().getApiService().createnewevent(eventValue);
        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                if(response.code()==200)
                {
                    binding.loader.loader.setVisibility(View.GONE);
                    Toasty.success(getContext(), "Add Successfully", Toast.LENGTH_LONG).show();
                    getDialog().dismiss();

                }
                else
                {
                   binding.loader. loader.setVisibility(View.GONE);
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
            public void onFailure(Call<EventResponse> call, Throwable t) {
              binding.loader.  loader.setVisibility(View.GONE);
                Toasty.error(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validation(String title, String fromDate, String toDate, String location,
                               String host, String partcipant, String desc, String related, String time) {
    if(title.isEmpty()){
        binding.eventContent.titleText.setError(getResources().getString(R.string.title_error));
        return false;
    }else if (fromDate.isEmpty()){
        binding.eventContent.fromValue.setError(getResources().getString(R.string.fromdate_error));
        return false;
    }else if (toDate.isEmpty()){
        binding.eventContent.toValue.setError(getResources().getString(R.string.todate_error));
        return false;
    }else if (location.isEmpty()){
        binding.eventContent.addLocationText.setError(getResources().getString(R.string.location_error));
        return false;
    }else if (host.isEmpty()){
        binding.eventContent.hostText.setError(getResources().getString(R.string.host_error));
        return false;
    } else if (partcipant.isEmpty()){
        binding.eventContent.participantValue.setError(getResources().getString(R.string.participant_error));
        return false;
    }else if (desc.isEmpty()){
        binding.eventContent.descriptionText.setError(getResources().getString(R.string.description_error));
        return false;
    }else if(time.isEmpty()){
        binding.eventContent.timeValue.setError(getResources().getString(R.string.time_error));
        return false;
    }
    return true;
    }

    private void setDefaults()
      {
          binding.headerBottomroundEdit.add.setVisibility(View.GONE);
        circleimage.add(R.drawable.red_dot);
        circleimage.add(R.drawable.ic_green_dot);
        circleimage.add(R.drawable.yellow_dot);
          categories.add("Daily");
          categories.add("Weekly");
          categories.add("Monthly");



        DatePickerDialog.OnDateSetListener date = (view, year, month, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
         // updateLabel(pass_date, myCalendar);
        };


        eventPrerioritySpinner = new EventPrerioritySpinner(getActivity(), circleimage);
        binding.eventContent.colorSpinner.setAdapter(eventPrerioritySpinner);
          binding.eventContent.colorSpinner.setDropDownVerticalOffset(120);

          binding.eventContent.colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    priority = "high";
                }else if  (position==1) {
                    priority = "medium";
                }else{
                    priority = "low";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                priority = "high";
            }
        });

        eventTextSpinner = new EventTextSpinner(getActivity(), categories);
          binding.eventContent.spinner.setAdapter(eventTextSpinner);
          binding.eventContent.spinner.setDropDownVerticalOffset(120);

          binding.eventContent.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 repeated =    binding.eventContent.spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                repeated = categories.get(0);
            }
        });

//        binding.eventContent.from.setOnClickListener(v -> {
//           /* from_date_value.getText().toString();
//            if (!from_date_value.getText().toString().isEmpty()) {
//                String value = from_date_value.getText().toString();
//                String[] dd = value.split("-");
//                int y = Integer.parseInt(dd[2]);
//                int m = Integer.parseInt(dd[1])-1;
//                int d = Integer.parseInt(dd[0]);
//                myCalendar.set(Calendar.DAY_OF_MONTH, y);
//                myCalendar.set(Calendar.MONTH, m);
//                myCalendar.set(Calendar.YEAR, d);
//                new DatePickerDialog(getActivity(), date, d, m, y).show();
//            } else {
//                new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }*/
//            Globals.selectDate(getContext(),binding.eventContent.fromValue);
//            pass_date = binding.eventContent.fromValue;
//
//        });

//        to_date_value.setOnClickListener(v -> {
//           /* if (to_date_value.getText().toString() != null && !to_date_value.getText().toString().isEmpty()) {
//                String value = to_date_value.getText().toString();
//                String[] dd = value.split("-");
//                int y = Integer.parseInt(dd[2]);
//                int m = Integer.parseInt(dd[1])-1;
//                int d = Integer.parseInt(dd[0]);
//                myCalendar.set(Calendar.DAY_OF_MONTH, y);
//                myCalendar.set(Calendar.MONTH, m);
//                myCalendar.set(Calendar.YEAR, d);
//                new DatePickerDialog(getActivity(), date, d, m, y).show();
//
//            } else {
//                new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//
//            }*/
//            Globals.selectDate(getContext(),to_date_value);
//            pass_date = to_date_value;
//
//        });



          binding.eventContent.timeValue.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                      @Override
                      public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                          t1hr = hourOfDay;
                          t1min = minute;
                          myTime = Calendar.getInstance();
//                        myTime.set(0,0,0,t1hr,t1min);
                          myTime.set(Calendar.HOUR_OF_DAY,t1hr);
                          myTime.set(Calendar.MINUTE,t1min);
                          myTime.set(Calendar.SECOND,0);
                          myTime.set(Calendar.MILLISECOND,0);
                          binding.eventContent.timeValue.setText(DateFormat.format("hh:mm aa",myTime));
                          setAlarm();
                      }
                  },12,0,false
                  );
                  timePickerDialog.updateTime(t1hr,t1min);
                  timePickerDialog.show();

              }

          });



      }
    private void updateLabel(TextView pass_date, Calendar myCalendar)
      {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            pass_date.setText(sdf.format(myCalendar.getTime()));
          }

   /* private void saveData() {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
         SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(TaskEventList);
        editor.putString(Globals.TaskEventList, json);
        editor.apply();
       // Toast.makeText(getActivity(), "Saved Array List to Shared preferences. ", Toast.LENGTH_SHORT).show();
    }

    private ArrayList<NewEvent> TaskEventList;
    private void loadData()
      {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(Globals.TaskEventList, null);
        Type type = new TypeToken<ArrayList<NewEvent>>() {}.getType();
        TaskEventList = gson.fromJson(json, type);
        if (TaskEventList == null) {
            TaskEventList = new ArrayList<>();
        }
    }*/

    private void setAlarm() {

        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(getContext(), NotificationPublisher.class);
        i.putExtra("value",getActivity().getResources().getString(R.string.meeting_notification));
        i.putExtra("title",getActivity().getResources().getString(R.string.meeting));
        final int id = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(),id,i,PendingIntent.FLAG_ONE_SHOT);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,myTime.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

    }




}
