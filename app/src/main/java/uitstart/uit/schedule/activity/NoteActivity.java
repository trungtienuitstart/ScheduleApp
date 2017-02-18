package uitstart.uit.schedule.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import uitstart.uit.schedule.model.DateTimeManager;
import uitstart.uit.schedule.model.Note;
import uitstart.uit.schedule.R;

public class NoteActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    public static final int REQUEST_NEW=0;
    public static  final int REQUEST_UPDATE=1;
    public static final int REQUEST_DELETE=2;


    private BottomNavigationView bnvOptions, bnvAction;
    private TextView tvDate, tvTime, title_activity;
    private EditText edtName, edtDetail;
    private ImageView ic_activity;
    private Note noteReceived=null;

    private Intent calledIntent;
    private Note noteResult=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        addControls();
        addEvents();
        initTheme();
    }


    private void addControls() {
        bnvAction= (BottomNavigationView) findViewById(R.id.bnvAction);
        bnvOptions= (BottomNavigationView) findViewById(R.id.bnvOptions);
        ic_activity= (ImageView) findViewById(R.id.ic_activity);
        title_activity= (TextView) findViewById(R.id.title_activity);
        tvDate= (TextView) findViewById(R.id.tvDate);
        tvTime= (TextView) findViewById(R.id.tvTime);
        edtName= (EditText) findViewById(R.id.edtName);
        edtDetail= (EditText) findViewById(R.id.edtDetail);
    }

    private void addEvents() {
        tvTime.setOnClickListener(this);
        tvDate.setOnClickListener(this);
        bnvAction.setOnNavigationItemSelectedListener(this);
        bnvOptions.setOnNavigationItemSelectedListener(this);
    }

    private void initTheme() {

        calledIntent=getIntent();
        noteReceived= (Note) calledIntent.getSerializableExtra("sentNote");
        if(noteReceived==null)
            initThemeForAdd();
        if(noteReceived!=null)
            initThemeForInFo();

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.doneMenuItem:
                noteResult=new Note(tvDate.getText().toString(),tvTime.getText().toString(),edtName.getText().toString(),edtDetail.getText().toString());
                if(noteReceived==null)
                    sendResultForMainActivity(REQUEST_NEW);
                if(noteReceived!=null) {
                    noteResult.setId(noteReceived.getId());
                    sendResultForMainActivity(REQUEST_UPDATE);
                }
                    break;
            case R.id.cancelMenuItem: returnBack() ; break;
            case R.id.editMenuItem: editNote(); break;
            case R.id.deleteMenuItem:
                noteResult=noteReceived;
                sendResultForMainActivity(REQUEST_DELETE); break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch(id){
            case R.id.tvDate: openChangeDate(); break;
            case R.id.tvTime: openChangeTime(); break;
        }
    }

    //---------------------------------------------------------------------

    private void openChangeTime() {
        TimePickerDialog.OnTimeSetListener onTimeSetListener=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                DateTimeManager.calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                DateTimeManager.calendar.set(Calendar.MINUTE,minute);
                DateTimeManager.calendar.set(Calendar.SECOND,0);
                tvTime.setText(DateTimeManager.FORMAT_TIME.format(DateTimeManager.calendar.getTime()));
            }
        };
        TimePickerDialog timePickerDialog=new TimePickerDialog(this,onTimeSetListener,
                DateTimeManager.calendar.get(Calendar.HOUR_OF_DAY),
                DateTimeManager.calendar.get(Calendar.MINUTE),
                true);
        timePickerDialog.show();

    }

    private void openChangeDate() {
        DatePickerDialog.OnDateSetListener onDateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                DateTimeManager.calendar.set(Calendar.YEAR,year);
                DateTimeManager.calendar.set(Calendar.MONTH,month);
                DateTimeManager.calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                tvDate.setText(DateTimeManager.FORMAT_DATE.format(DateTimeManager.calendar.getTime()));
            }
        };
        DatePickerDialog datePickerDialog=new DatePickerDialog(this,onDateSetListener,
                DateTimeManager.calendar.get(Calendar.YEAR),
                DateTimeManager.calendar.get(Calendar.MONTH),
                DateTimeManager.calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void editNote() {
        initThemeForEdit();
    }

    private void returnBack() {
        if(noteReceived==null)
            finish();
        if(noteReceived!=null)
            initThemeForInFo();
    }

    private void sendResultForMainActivity(int request) {
        calledIntent.putExtra("result",noteResult);
        setResult(request,calledIntent);
        finish();
    }


    //-------------------------------------------------
    private void initThemeForAdd() {
        ic_activity.setImageResource(R.drawable.ic_add_activity);
        title_activity.setText("Thêm ghi chú mới");
        tvDate.setEnabled(true);
        tvTime.setEnabled(true);
        edtName.setEnabled(true);
        edtDetail.setEnabled(true);
        bnvAction.setVisibility(View.VISIBLE);
        bnvOptions.setVisibility(View.GONE);
        bnvAction.getMenu().getItem(0).setChecked(false);
        bnvAction.getMenu().getItem(1).setChecked(false);

        tvDate.setText(DateTimeManager.FORMAT_DATE.format(DateTimeManager.calendar.getTime()));
        tvTime.setText(DateTimeManager.FORMAT_TIME.format(DateTimeManager.calendar.getTime()));
    }

    private void initThemeForEdit() {
        initThemeForAdd();
        ic_activity.setImageResource(R.drawable.ic_edit_activity);
        title_activity.setText("Chỉnh sửa ghi chú");
        tvTime.setText(noteReceived.getTimeOfNote());
        tvDate.setText(noteReceived.getDateOfNote());
    }

    private void initThemeForInFo() {
        ic_activity.setImageResource(R.drawable.ic_info_activity);
        title_activity.setText("Thông tin ghi chú");
        tvDate.setEnabled(false);
        tvTime.setEnabled(false);
        edtDetail.setEnabled(false);
        edtName.setEnabled(false);
        bnvAction.setVisibility(View.GONE);
        bnvOptions.setVisibility(View.VISIBLE);
        bnvOptions.getMenu().getItem(0).setChecked(false);
        bnvOptions.getMenu().getItem(1).setChecked(false);

        tvDate.setText(noteReceived.getDateOfNote());
        tvTime.setText(noteReceived.getTimeOfNote());
        edtName.setText(noteReceived.getNameOfNote());
        edtDetail.setText(noteReceived.getDetailOfNote());
    }

}
