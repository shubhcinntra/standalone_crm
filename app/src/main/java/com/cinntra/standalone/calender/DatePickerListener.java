package com.cinntra.standalone.calender;

import org.joda.time.DateTime;

public interface DatePickerListener {
    void onDateSelected(DateTime dateSelected);
}
