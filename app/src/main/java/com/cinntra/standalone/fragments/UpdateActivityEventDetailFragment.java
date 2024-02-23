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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.cinntra.standalone.R;
import com.cinntra.standalone.databinding.FragmentAddEventBinding;
import com.cinntra.standalone.globals.Globals;
import com.cinntra.standalone.model.EventResponse;
import com.cinntra.standalone.model.EventValue;
import com.cinntra.standalone.model.QuotationResponse;
import com.cinntra.standalone.receivers.NotificationPublisher;
import com.cinntra.standalone.webservices.NewApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivityEventDetailFragment extends Fragment implements  View.OnClickListener {

//    @BindView(R.id.head_title)
//    TextView head_title;
//    @BindView(R.id.back_press)
//    RelativeLayout back_press;
    TextView pass_date;
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
//    @BindView(R.id.time_value)
//    TextView time_value;
//    @BindView(R.id.add)
//    ImageView add;
//    @BindView(R.id.ok)
//    ImageView ok;
    EventValue newEvent;

    String priority = "";
    String allday = "";
    String repeated = "";
    private AlarmManager alarmManager;
    private Calendar myTime;
    EventValue setEventData = new EventValue();
    int t1hr,t1min;
    EventPrerioritySpinner eventPrerioritySpinner;
    EventTextSpinner eventTextSpinner;

    ArrayList<String> categories   = new ArrayList<>();
    ArrayList<Integer> circleimage = new ArrayList<>();
    final Calendar myCalendar      = Calendar.getInstance();

    public UpdateActivityEventDetailFragment() {

    }


    @Override
    public void onDetach() {
        super.onDetach();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    // TODO: Rename and change types and number of parameters
    public static UpdateActivityEventDetailFragment newInstance(String param1) {
        UpdateActivityEventDetailFragment fragment = new UpdateActivityEventDetailFragment();
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

        }
    }

    FragmentAddEventBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

     binding=FragmentAddEventBinding.inflate(inflater,container,false);
        View v = inflater.inflate(R.layout.fragment_add_event, container, false);
       // ButterKnife.bind(this, v);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
       binding.headerBottomroundEdit.headTitle.setText("Update Event");
        binding.headerBottomroundEdit.backPress.setOnClickListener(this);
        binding.eventContent.submitButton.setOnClickListener(this);
        binding.headerBottomroundEdit.ok.setOnClickListener(this);
        binding.headerBottomroundEdit.add.setOnClickListener(this);
        setDisable();
        setDefaults();
        if(Globals.checkInternet(getContext()))
        callApi(newEvent.getId());



        return binding.getRoot();
    }

    private void callApi(Integer eventValue) {
        EventValue event = new EventValue();
        event.setId(eventValue);
        Call<EventResponse> call = NewApiClient.getInstance().getApiService().particularevent(event);
        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                if(response.code()==200)
                {

                    setEventData = response.body().getData().get(0);
                    setData(setEventData);
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

    private void setDefaults()
    {

        circleimage.add(R.drawable.red_dot);
        circleimage.add(R.drawable.ic_green_dot);
        circleimage.add(R.drawable.yellow_dot);
        categories.add("Repeat");
        categories.add("Daily");
        categories.add("Monthly");
        categories.add("Weekly");



        DatePickerDialog.OnDateSetListener date = (view, year, month, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(pass_date, myCalendar);
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
                repeated = binding.eventContent.spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                repeated = categories.get(0);
            }
        });


        binding.eventContent.fromValue.setOnClickListener(v -> {
            binding.eventContent.fromValue.getText().toString();
            if (!binding.eventContent.fromValue.getText().toString().isEmpty()) {
                String value = binding.eventContent.fromValue.getText().toString();
                String[] dd = value.split("-");
                int y = Integer.parseInt(dd[2]);
                int m = Integer.parseInt(dd[1])-1;
                int d = Integer.parseInt(dd[0]);
                myCalendar.set(Calendar.DAY_OF_MONTH, y);
                myCalendar.set(Calendar.MONTH, m);
                myCalendar.set(Calendar.YEAR, d);
                new DatePickerDialog(getActivity(), date, d, m, y).show();
            } else {
                new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
            pass_date = binding.eventContent.fromValue;

        });

        binding.eventContent.toValue.setOnClickListener(v -> {
            if (binding.eventContent.toValue.getText().toString() != null && !binding.eventContent.toValue.getText().toString().isEmpty()) {
                String value = binding.eventContent.toValue.getText().toString();
                String[] dd = value.split("-");
                int y = Integer.parseInt(dd[2]);
                int m = Integer.parseInt(dd[1])-1;
                int d = Integer.parseInt(dd[0]);
                myCalendar.set(Calendar.DAY_OF_MONTH, y);
                myCalendar.set(Calendar.MONTH, m);
                myCalendar.set(Calendar.YEAR, d);
                new DatePickerDialog(getActivity(), date, d, m, y).show();

            } else {
                new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
            pass_date = binding.eventContent.toValue;

        });

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

    private void setData(EventValue setEventData) {
        binding.eventContent.titleText.setText(setEventData.getTitle());
        binding.eventContent.fromValue.setText(setEventData.getFrom());
        binding.eventContent.toValue.setText(setEventData.getTo());
        binding.eventContent.timeValue.setText(setEventData.getTime());
        binding.eventContent.addLocationText.setText(setEventData.getLocation());
        binding.eventContent.hostText.setText(setEventData.getHost());
        binding.eventContent.participantValue.setText(setEventData.getParticipants());
        binding.eventContent.descriptionText.setText(setEventData.getDescription());
        binding.eventContent.relatedDocumentValue.setText(setEventData.getRelatedTo());
        if(setEventData.getAllday().equals("All Day"))
            binding.eventContent.simpleSwitch.setChecked(true);
        else
            binding.eventContent.simpleSwitch.setChecked(false);

        binding.eventContent.spinner.setSelection(getposition(categories,setEventData.getRepeated()));
        binding.eventContent.colorSpinner.setSelection(getpos(circleimage,setEventData.getPriority()));

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


    }

    private int getpos(ArrayList<Integer> circleimage, String priority) {
        int pos = -1;
        for (Integer s : circleimage){
            if (priority.equals("high"))
                pos = circleimage.indexOf(s);
            else if (priority.equals("medium"))
                pos = circleimage.indexOf(s);
            else
                pos = circleimage.indexOf(s);
        }
        return pos;
    }

    private int getposition(ArrayList<String> categories, String repeated) {
        int pos = -1;
        for(String s : categories){
            if(s.equals(repeated)){
                return categories.indexOf(s);
            }
        }
        return pos;
    }

    private void updateLabel(TextView pass_date, Calendar myCalendar)
    {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        pass_date.setText(sdf.format(myCalendar.getTime()));
    }
    private void setDisable() {
        binding.eventContent.titleText.setClickable(false);
        binding.eventContent.titleText.setFocusable(false);
        binding.eventContent.titleText.setFocusableInTouchMode(false);

        binding.eventContent.fromValue.setClickable(false);
        binding.eventContent.fromValue.setFocusable(false);
        binding.eventContent.fromValue.setFocusableInTouchMode(false);

        binding.eventContent.toValue.setFocusableInTouchMode(false);
        binding.eventContent.toValue.setFocusable(false);
        binding.eventContent.toValue.setClickable(false);
        binding.eventContent.timeValue.setClickable(false);
        binding.eventContent.timeValue.setFocusable(false);
        binding.eventContent.timeValue.setFocusableInTouchMode(false);
        binding.eventContent.addLocationText.setClickable(false);
        binding.eventContent.addLocationText.setFocusableInTouchMode(false);
        binding.eventContent.addLocationText.setFocusable(false);

        binding.eventContent.hostText.setFocusable(false);
        binding.eventContent.hostText.setClickable(false);
        binding.eventContent.hostText.setFocusableInTouchMode(false);

        binding.eventContent.participantValue.setFocusable(false);
        binding.eventContent.participantValue.setClickable(false);
        binding.eventContent.participantValue.setFocusableInTouchMode(false);

        binding.eventContent.relatedDocumentValue.setFocusable(false);
        binding.eventContent.relatedDocumentValue.setClickable(false);
        binding.eventContent.relatedDocumentValue.setFocusableInTouchMode(false);

        binding.eventContent.descriptionText.setFocusable(false);
        binding.eventContent.descriptionText.setClickable(false);
        binding.eventContent.descriptionText.setFocusableInTouchMode(false);

        binding.eventContent.colorSpinner.setEnabled(false);
        binding.eventContent.spinner.setEnabled(false);
        binding.eventContent.uploadButton.setVisibility(View.GONE);
        binding.eventContent.submitButton.setVisibility(View.GONE);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_press:
                getActivity().onBackPressed();
                break;
            case R.id.add:
                setEnable();
                break;
            case R.id.ok:
            case R.id.submit_button:
                String title = binding.eventContent.titleText.getText().toString().trim();
                String fromDate = binding.eventContent.fromValue.getText().toString().trim();
                String toDate = binding.eventContent.toValue.getText().toString().trim();
                String location = binding.eventContent.addLocationText.getText().toString().trim();
                String host = binding.eventContent.hostText.getText().toString().trim();
                String partcipant = binding.eventContent.participantValue.getText().toString().trim();
                String desc = binding.eventContent.descriptionText.getText().toString().trim();
                String related = binding.eventContent.relatedDocumentValue.getText().toString().trim();
                String time = binding.eventContent.timeValue.getText().toString().trim();


                if (validation(title, fromDate, toDate, location, host, partcipant, desc, related, time)) {
                    EventValue eventValue = new EventValue();
                    eventValue.setOppId(setEventData.getOppId());
                    eventValue.setTitle(title);
                    eventValue.setDescription(desc);
                    eventValue.setFrom(fromDate);
                    eventValue.setTo(toDate);
                    eventValue.setEmp(setEventData.getEmp());
                    eventValue.setCreateTime(Globals.getTCurrentTime());
                    eventValue.setCreateDate(Globals.getTodaysDate());
                    eventValue.setType("Event");
                    eventValue.setSourceType("");
                    eventValue.setParticipants(partcipant);
                    eventValue.setComment("");
                    eventValue.setSubject("");
                    eventValue.setTime(Globals.getTCurrentTime());
                    eventValue.setDocument("");
                    eventValue.setRelatedTo(related);
                    eventValue.setLocation(location);
                    eventValue.setHost(host);
                    eventValue.setAllday(allday);
                    eventValue.setName(setEventData.getName());
                    eventValue.setProgressStatus("WIP");
                    eventValue.setPriority(priority);
                    eventValue.setRepeated(repeated);
                    eventValue.setId(setEventData.getId());


                    if (Globals.checkInternet(getContext())){
                        callupdateApi(eventValue);
                        setDisable();
                    }

                }
        }
        }

    private void callupdateApi(EventValue eventValue) {

        Call<EventResponse> call = NewApiClient.getInstance().getApiService().updateevent(eventValue);
        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                if(response.code()==200)
                {

                    Toast.makeText(getActivity(), "Updated Successfully.", Toast.LENGTH_SHORT).show();
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
        }else if (related.isEmpty()){
            binding.eventContent.relatedDocumentValue.setError(getResources().getString(R.string.related_toerror));
            return false;
        }else if(time.isEmpty()){
            binding.eventContent.timeValue.setError(getResources().getString(R.string.time_error));
            return false;
        }
        return true;
    }


        private void setEnable () {
            binding.headerBottomroundEdit.add.setVisibility(View.GONE);
            binding.headerBottomroundEdit.ok.setVisibility(View.VISIBLE);
            binding.eventContent.titleText.setClickable(true);
            binding.eventContent.titleText.setFocusable(true);
            binding.eventContent.titleText.setFocusableInTouchMode(true);

            binding.eventContent.fromValue.setClickable(true);
            binding.eventContent.fromValue.setFocusable(true);

            binding.eventContent.toValue.setFocusable(true);
            binding.eventContent.toValue.setClickable(true);
            binding.eventContent.timeValue.setClickable(true);
            binding.eventContent.timeValue.setFocusable(true);

            binding.eventContent.addLocationText.setClickable(true);
            binding.eventContent.addLocationText.setFocusableInTouchMode(true);
            binding.eventContent.addLocationText.setFocusable(true);

            binding.eventContent.hostText.setFocusable(true);
            binding.eventContent.hostText.setClickable(true);
            binding.eventContent.hostText.setFocusableInTouchMode(true);

            binding.eventContent.participantValue.setFocusable(true);
            binding.eventContent.participantValue.setClickable(true);
            binding.eventContent.participantValue.setFocusableInTouchMode(true);

            binding.eventContent.relatedDocumentValue.setFocusable(true);
            binding.eventContent.relatedDocumentValue.setClickable(true);
            binding.eventContent.relatedDocumentValue.setFocusableInTouchMode(true);

            binding.eventContent.descriptionText.setFocusable(true);
            binding.eventContent.descriptionText.setClickable(true);
            binding.eventContent.descriptionText.setFocusableInTouchMode(true);

            binding.eventContent.colorSpinner.setEnabled(true);
            binding.eventContent.spinner.setEnabled(true);
            binding.eventContent.uploadButton.setVisibility(View.VISIBLE);
            binding.eventContent.submitButton.setVisibility(View.VISIBLE);
        }


        private void setAlarm () {

            alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
            Intent i = new Intent(getContext(), NotificationPublisher.class);
            i.putExtra("value", getActivity().getResources().getString(R.string.meeting_notification));
            i.putExtra("title", getActivity().getResources().getString(R.string.meeting));
            final int id = (int) System.currentTimeMillis();
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), id, i, PendingIntent.FLAG_ONE_SHOT);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, myTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        }


}
