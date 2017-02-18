package uitstart.uit.schedule.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import uitstart.uit.schedule.database.NoteDataBase;
import uitstart.uit.schedule.model.DateTimeManager;
import uitstart.uit.schedule.model.Note;
import uitstart.uit.schedule.adapter.NoteAdapter;
import uitstart.uit.schedule.R;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener, AdapterView.OnItemClickListener {

    private BottomNavigationView bnvMainMenu;
    private TextView tvDate;
    private ListView lvNote;

    private ArrayList<Note> arr;
    private NoteAdapter adapter;
    public static NoteDataBase db;

    private ArrayList<Note> selectedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
        initTheme();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initTheme();
        refreshListNode(tvDate.getText().toString());

    }

    private void addControls() {
        bnvMainMenu= (BottomNavigationView) findViewById(R.id.bnvMainMenu);
        tvDate= (TextView) findViewById(R.id.tvDate);
        lvNote= (ListView) findViewById(R.id.lvNote);

    }

    private void addEvents() {
        bnvMainMenu.setOnNavigationItemSelectedListener(this);
        tvDate.setOnClickListener(this);
        lvNote.setOnItemClickListener(this);
        lvNote.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        lvNote.setMultiChoiceModeListener(new ListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                if(checked) {
                    selectedNote.add(arr.get(position));
                }
                else
                    selectedNote.remove(arr.get(position));

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                bnvMainMenu.setVisibility(View.GONE);
                MenuInflater inflater=mode.getMenuInflater();
                inflater.inflate(R.menu.menu_bar,menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                int id=item.getItemId();

                if(id==R.id.delete_item){
                    deleteChoiceNote();
                    onDestroyActionMode(mode);
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                bnvMainMenu.setVisibility(View.VISIBLE);
                mode.finish();
            }
        });
    }

    private void initTheme() {
        bnvMainMenu.getMenu().getItem(0).setChecked(false);
        bnvMainMenu.getMenu().getItem(1).setChecked(false);
        bnvMainMenu.getMenu().getItem(2).setChecked(false);
        tvDate.setText(DateTimeManager.FORMAT_DATE.format(DateTimeManager.calendar.getTime()));
    }

    private void initData() {
        db=new NoteDataBase(this);
        arr=new ArrayList<Note>();
        adapter=new NoteAdapter(this,R.layout.note_item,arr);
        lvNote.setAdapter(adapter);
        refreshListNode(tvDate.getText().toString());

        selectedNote=new ArrayList<>();
    }

    //-------------------------------------------------------------------------------------------------------------

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.tvDate) {
            openChangeDate();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch(id){
            case R.id.addMenuItem: openNoteActivity(null); break;
            case R.id.settingMenuItem: openSetting(); break;
            case R.id.allNoteMenuItem: viewAllNote(); break;
        }
        return false;
    }

    private void deleteChoiceNote() {
        for(Note i:selectedNote){
            db.deleteNote(i);
        }
        selectedNote.clear();
        refreshListNode(tvDate.getText().toString());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        lvNote.clearChoices();
        lvNote.requestLayout();
        openNoteActivity((Note) parent.getItemAtPosition(position));
    }


    //----------------------------------------------------------
    private void openSetting() {

    }

    private void openNoteActivity(@Nullable Note note) {
        Intent noteIntent=new Intent(MainActivity.this,NoteActivity.class);
        if(note!=null){
            noteIntent.putExtra("sentNote",note);
        }
        startActivityForResult(noteIntent,0);
    }

    private void viewAllNote() {
        Intent intentAllNote=new Intent(MainActivity.this,AllNoteActivity.class);
        startActivity(intentAllNote);
    }

    //--------------------------------------------------------------------------------------------
    private void openChangeDate() {
        DatePickerDialog.OnDateSetListener onDateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                DateTimeManager.calendar.set(Calendar.YEAR,year);
                DateTimeManager.calendar.set(Calendar.MONTH,month);
                DateTimeManager.calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                tvDate.setText(DateTimeManager.FORMAT_DATE.format(DateTimeManager.calendar.getTime()));
                refreshListNode(tvDate.getText().toString());
            }
        };
        DatePickerDialog datePickerDialog=new DatePickerDialog(this,onDateSetListener,
                DateTimeManager.calendar.get(Calendar.YEAR),
                DateTimeManager.calendar.get(Calendar.MONTH),
                DateTimeManager.calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }




    //------------------------------------------------------------------------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data!=null){
            Note noteResult= (Note) data.getSerializableExtra("result");
            switch(resultCode){
                case NoteActivity.REQUEST_NEW: addNewNote(noteResult); break;
                case NoteActivity.REQUEST_DELETE: deleteNote(noteResult); break;
                case NoteActivity.REQUEST_UPDATE: updateNote(noteResult);break;
            }
        }
    }

    public void addNewNote(Note n){
        db.insertNote(n);
        refreshListNode(tvDate.getText().toString());
        Toast.makeText(this,"Bạn vừa thêm thàng công 1 ghi chú mới",Toast.LENGTH_SHORT).show();
    }

    public void updateNote(Note n){
        db.updateNote(n);
        refreshListNode(tvDate.getText().toString());
        Toast.makeText(this,"Bạn vừa cập nhật thông tin ghi chú",Toast.LENGTH_SHORT).show();
    }

    public void deleteNote(Note n){
        db.deleteNote(n);
        refreshListNode(tvDate.getText().toString());
        Toast.makeText(this,"Bạn vừa xóa một ghi chú",Toast.LENGTH_SHORT).show();
    }

    //--------------------------------------------------------------------------

    private void refreshListNode(String date) {
        arr.clear();

        ArrayList<Note> noteOfDate=db.getNote(tvDate.getText().toString());
        for(Note i:noteOfDate){
            arr.add(i);
        }

        Collections.sort(arr, new Comparator<Note>() {
            @Override
            public int compare(Note o1, Note o2) {
                return o1.getTimeOfNote().compareTo(o2.getTimeOfNote());
            }
        });

        adapter.notifyDataSetChanged();
    }

}
