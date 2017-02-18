package uitstart.uit.schedule.model;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Khang on 2/15/2017.
 */

public class DateTimeManager {
    public static Calendar calendar=Calendar.getInstance();

    public static final SimpleDateFormat FORMAT_DATE=new SimpleDateFormat("dd/MM/yyyy");
    public static final SimpleDateFormat FORMAT_TIME=new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat FORMAT_CALENDAR=new SimpleDateFormat("dd/MM/yyyy HH:mm");
}
