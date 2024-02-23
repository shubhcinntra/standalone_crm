package com.cinntra.standalone.fragments;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cinntra.standalone.R;
import com.cinntra.standalone.databinding.FragmentAddTaskBinding;
import com.cinntra.standalone.globals.Globals;
import com.cinntra.standalone.model.EventResponse;
import com.cinntra.standalone.model.EventValue;
import com.cinntra.standalone.model.NewEvent;
import com.cinntra.standalone.model.QuotationResponse;
import com.cinntra.standalone.receivers.NotificationPublisher;
import com.cinntra.standalone.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class UpdateActivityTaskDetailFragment extends Fragment implements  View.OnClickListener {

//    @BindView(R.id.head_title)
//    TextView head_title;
//    @BindView(R.id.back_press)
//    RelativeLayout back_press;
//    @BindView(R.id.date_value)
//    EditText date_value;
//    @BindView(R.id.time_value)
//    TextView settime;
//    @BindView(R.id.title_text)
//    EditText title_text;
//    @BindView(R.id.add_location_text)
//    EditText add_location_text;
//    @BindView(R.id.host_text)
//    EditText host_text;
//    @BindView(R.id.description_text)
//    EditText description_text;
//    @BindView(R.id.simple_switch)
//    Switch simple_switch;
//    @BindView(R.id.date_view)
//    RelativeLayout date_view;
//    @BindView(R.id.color_view)
//    LinearLayout color_view;
//    @BindView(R.id.time_view)
//    RelativeLayout time_view;
//    @BindView(R.id.progress_status_view)
//    RelativeLayout progress_status_view;
//    @BindView(R.id.spinnerview)
//    RelativeLayout spinnerview;
//    @BindView(R.id.spinner)
//    Spinner preority_spinner;
//    @BindView(R.id.color_spinner)
//    Spinner colorspin;
//    @BindView(R.id.progress_spinner)
//    Spinner progress_spinner;
//    @BindView(R.id.submit_button)
//    Button submit_button;
//    @BindView(R.id.add)
//    ImageView add;
//    @BindView(R.id.ok)
//    ImageView ok;
    EventValue newEvent;
    int Position ;
    EventPrerioritySpinner eventPrerioritySpinner;
    EventTextSpinner eventTextSpinner;
    TaskProgressSpinner taskProgressSpinner;
    int t1hr,t1min;
    ArrayList<String> categories      = new ArrayList<>();
    ArrayList<Integer> circleimage    = new ArrayList<>();
    ArrayList<String> progress_status = new ArrayList<>();
    final Calendar myCalendar         = Calendar.getInstance();

    private AlarmManager alarmManager;
    private Calendar myTime;
    private String priority,repeated,progresstatus;

    public UpdateActivityTaskDetailFragment() {

    }

    // TODO: Rename and change types and number of parameters
    public static UpdateTaskDetailFragment newInstance(String param1) {
        UpdateTaskDetailFragment fragment = new UpdateTaskDetailFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle b      = getArguments();
            newEvent =(EventValue) b.getParcelable("View");
            Position = b.getInt("Position");

        }
    }


    FragmentAddTaskBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentAddTaskBinding.inflate(inflater,container,false);
        View v = inflater.inflate(R.layout.fragment_add_task, container, false);
       // ButterKnife.bind(this, v);

        binding.headerBottomroundEdit.headTitle.setText("Update Task");
        binding.headerBottomroundEdit.backPress.setOnClickListener(this);
        binding.taskContent.submitButton.setOnClickListener(this);
        binding.headerBottomroundEdit .ok.setOnClickListener(this);
        binding.headerBottomroundEdit.  add.setOnClickListener(this);
        setDisable();
        setDefaults();
        setData();
//        loadData();

        return v;
    }

    private void setData() {
        binding.taskContent.titleText.setText(newEvent.getTitle());
        binding.taskContent.dateValue.setText(newEvent.getFrom());
        binding.taskContent.timeValue.setText(newEvent.getTime());
        binding.taskContent.addLocationText.setText(newEvent.getLocation());
        binding.taskContent.hostText.setText(newEvent.getHost());
        binding.taskContent.descriptionText.setText(newEvent.getDescription());


    }

    private void setDisable() {
        binding.taskContent.titleText.setClickable(false);
        binding.taskContent.titleText.setFocusable(false);
        binding.taskContent.titleText.setFocusableInTouchMode(false);

        binding.taskContent.dateValue.setClickable(false);
        binding.taskContent.dateValue.setFocusable(false);
        binding.taskContent.dateValue.setFocusableInTouchMode(false);

        binding.taskContent.timeValue.setFocusableInTouchMode(false);
        binding.taskContent.timeValue.setFocusable(false);
        binding.taskContent.timeValue.setClickable(false);

        binding.taskContent.addLocationText.setClickable(false);
        binding.taskContent.addLocationText.setFocusableInTouchMode(false);
        binding.taskContent.addLocationText.setFocusable(false);

        binding.taskContent.hostText.setFocusable(false);
        binding.taskContent.hostText.setClickable(false);
        binding.taskContent.hostText.setFocusableInTouchMode(false);


        binding.taskContent.descriptionText.setFocusable(false);
        binding.taskContent.descriptionText.setClickable(false);
        binding.taskContent.descriptionText.setFocusableInTouchMode(false);

        binding.taskContent.colorSpinner.setEnabled(false);
        binding.taskContent.spinner.setEnabled(false);

        binding.taskContent.submitButton.setVisibility(View.GONE);
        binding.headerBottomroundEdit.ok.setVisibility(View.GONE);
        binding.headerBottomroundEdit.add.setVisibility(View.VISIBLE);
    }

    private void setDefaults()
    {
        circleimage.add(R.drawable.red_dot);
        circleimage.add(R.drawable.ic_green_dot);
        circleimage.add(R.drawable.yellow_dot);

        categories.add("Daily");
        categories.add("Weekly");
        categories.add("Monthly");
        progress_status.add("Completed");
        progress_status.add("In Progress");
        progress_status.add("Not Started");
        progress_status.add("Waiting for input");



        DatePickerDialog.OnDateSetListener date = (view, year, month, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            binding.taskContent.dateValue.setText(sdf.format(myCalendar.getTime()));
        };



        eventPrerioritySpinner = new EventPrerioritySpinner(getActivity(), circleimage);
        binding.taskContent.colorSpinner.setAdapter(eventPrerioritySpinner);
        binding.taskContent.colorSpinner.setDropDownVerticalOffset(120);

        if(newEvent.getPriority().equalsIgnoreCase("high")) {
            binding.taskContent.colorSpinner.setSelection(0);
        }
        else if (newEvent.getPriority().equalsIgnoreCase("medium")) {
            binding.taskContent.colorSpinner.setSelection(1);
        }
        else{
            binding.taskContent.colorSpinner.setSelection(2);
        }

        binding.taskContent.colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        binding.taskContent.spinner.setAdapter(eventTextSpinner);
        binding.taskContent.spinner.setDropDownVerticalOffset(120);

        binding.taskContent.spinner.setSelection(categories.indexOf(newEvent.getRepeated()));

        binding.taskContent.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                repeated = binding.taskContent.spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                repeated = categories.get(0);
            }
        });

        taskProgressSpinner = new TaskProgressSpinner(getActivity(),progress_status);
        binding.taskContent.progressSpinner.setAdapter(taskProgressSpinner);
        binding.taskContent.progressSpinner.setDropDownVerticalOffset(120);

        binding.taskContent.progressSpinner.setSelection(progress_status.indexOf(newEvent.getProgressStatus()));

        binding.taskContent.progressSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                progresstatus = binding.taskContent.spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                progresstatus = progress_status.get(0);
            }
        });


        binding.taskContent.dateValue.setOnClickListener(v -> {
            Globals.selectDate(getContext(),binding.taskContent.dateValue);

        });

        binding.taskContent.timeValue.setOnClickListener(new View.OnClickListener() {
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
                        binding.taskContent.timeValue.setText(DateFormat.format("hh:mm aa",myTime));
                        //setAlarm();
                    }
                },12,0,false
                );
                timePickerDialog.updateTime(t1hr,t1min);
                timePickerDialog.show();

            }

        });



    }

    private ArrayList<EventValue> TaskEventList;
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
    }
    private void saveData(ArrayList<EventValue> taskEventList)
    {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(taskEventList);
        editor.putString(Globals.TaskEventList, json);
        editor.apply();
        // Toast.makeText(getActivity(), "Saved Array List to Shared preferences. ", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_press:
                getActivity().onBackPressed();
                break;
            case R.id.submit_button:
            case R.id.ok:
                String title      = binding.taskContent.titleText.getText().toString().trim();
                String date       = binding.taskContent.dateValue.getText().toString().trim();
                String location   = binding.taskContent.addLocationText.getText().toString().trim();
                String host       = binding.taskContent.hostText.getText().toString().trim();
                String desc       = binding.taskContent.descriptionText.getText().toString().trim();
                String time       = binding.taskContent.timeValue.getText().toString().trim();

//                NewEvent task = new NewEvent(title,date,null,true,"Repeate",location,
//                        host,"top",null,desc,null,2,time);



                newEvent.setTitle(title);
                newEvent.setFrom(date);
                newEvent.setLocation(location);
                newEvent.setHost(host);
                newEvent.setDescription(desc);
                newEvent.setTime(time);
//                TaskEventList.set(Position,newEvent);
//                saveData(TaskEventList);

                if(validation(title,location,host,desc,time,date)){
                    setDisable();
                    EventValue eventValue = new EventValue();
                    eventValue.setOppId(newEvent.getOppId());
                    eventValue.setId(newEvent.getId());
                    eventValue.setTitle(title);
                    eventValue.setDescription(desc);
                    eventValue.setFrom(date);
                    eventValue.setTo(date);
                    eventValue.setEmp(Integer.parseInt(Prefs.getString(Globals.EmployeeID,"")));
                    eventValue.setCreateTime(Globals.getTCurrentTime());
                    eventValue.setCreateDate(Globals.getTodaysDate());
                    eventValue.setType("Task");
                    eventValue.setParticipants("");
                    eventValue.setComment("");
                    eventValue.setSubject("");
                    eventValue.setTime(Globals.getTCurrentTime());
                    eventValue.setDocument("");
                    eventValue.setRelatedTo("");
                    eventValue.setLocation(location);
                    eventValue.setHost(host);
                    eventValue.setAllday("");
                    eventValue.setName(newEvent.getName());
                    eventValue.setProgressStatus(progresstatus);
                    eventValue.setPriority(priority);
                    eventValue.setRepeated("");




                    if(Globals.checkInternet(getContext()))
                        callApi(eventValue);




             /* NewEvent event = new NewEvent(title,fromDate,toDate,true,"Repeate",location,
             host,"top",partcipant,desc,related,1,time);
              TaskEventList.add(event);*/

                    // saveData();


                }


                break;
            case R.id.add:
                setEnable();
                break;
        }
    }

    private void callApi(EventValue eventValue) {

        Call<EventResponse> call = NewApiClient.getInstance().getApiService().updateevent(eventValue);
        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                if(response.code()==200)
                {

                    Toast.makeText(getContext(), "Posted Successfully.", Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<EventResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean validation(String title, String location, String host, String desc, String time, String date) {
        if(title.isEmpty()){
            binding.taskContent.titleText.setError(getResources().getString(R.string.title_error));
            return false;
        }else if (date.isEmpty()){
            binding.taskContent.dateValue.setError(getResources().getString(R.string.todate_error));
            return false;
        }else if (location.isEmpty()){
            binding.taskContent.addLocationText.setError(getResources().getString(R.string.location_error));
            return false;
        }else if (host.isEmpty()){
            binding.taskContent.hostText.setError(getResources().getString(R.string.host_error));
            return false;
        } else if (desc.isEmpty()){
            binding.taskContent.descriptionText.setError(getResources().getString(R.string.description_error));
            return false;
        }
        return true;

    }



    private void setEnable() {
        binding.headerBottomroundEdit.add.setVisibility(View.GONE);
        binding.headerBottomroundEdit.ok.setVisibility(View.VISIBLE);
        binding.taskContent.titleText.setClickable(true);
        binding.taskContent.titleText.setFocusable(true);
        binding.taskContent.titleText.setFocusableInTouchMode(true);

        binding.taskContent.dateValue.setClickable(true);
        binding.taskContent.dateValue.setFocusable(true);

        binding.taskContent.timeValue.setFocusable(true);
        binding.taskContent.timeValue.setClickable(true);

        binding.taskContent.addLocationText.setClickable(true);
        binding.taskContent.addLocationText.setFocusableInTouchMode(true);
        binding.taskContent.addLocationText.setFocusable(true);

        binding.taskContent.hostText.setFocusable(true);
        binding.taskContent.hostText.setClickable(true);
        binding.taskContent.hostText.setFocusableInTouchMode(true);

        binding.taskContent.descriptionText.setFocusable(true);
        binding.taskContent.descriptionText.setClickable(true);
        binding.taskContent.descriptionText.setFocusableInTouchMode(true);

        binding.taskContent.colorSpinner.setEnabled(true);
        binding.taskContent.spinner.setEnabled(true);

        binding.taskContent.submitButton.setVisibility(View.VISIBLE);
    }

    private void setAlarm()
    {
        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(getContext(), NotificationPublisher.class);
        i.putExtra("value",getActivity().getResources().getString(R.string.task_notification));
        i.putExtra("title",getActivity().getResources().getString(R.string.ur_task));
        final int id = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(),id,i,PendingIntent.FLAG_ONE_SHOT);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,myTime.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

    }


}
