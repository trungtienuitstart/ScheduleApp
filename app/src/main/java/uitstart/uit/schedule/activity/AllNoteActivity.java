package uitstart.uit.schedule.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import uitstart.uit.schedule.R;
import uitstart.uit.schedule.adapter.NoteAdapter;
import uitstart.uit.schedule.model.Note;

public class AllNoteActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lvAllNote;
    private NoteAdapter adapter;
    private ArrayList<Note> arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_note);

        addControls();
        addEvents();
        initTheme();
        initData();
    }

    private void addEvents() {
        lvAllNote.setOnItemClickListener(this);
    }

    private void initData() {
        arr = new ArrayList<>();
        adapter = new NoteAdapter(this, R.layout.note_item, arr);
        lvAllNote.setAdapter(adapter);
        refreshDataListView();
    }

    private void addControls() {
        lvAllNote = (ListView) findViewById(R.id.lvAllNote);
    }

    private void initTheme() {

    }

    private void refreshDataListView(){
        arr.clear();

        ArrayList<Note> arrAllNote=MainActivity.db.getAllNote();
        for(Note i:arrAllNote)
            arr.add(i);

        Collections.sort(arr, new Comparator<Note>() {
            @Override
            public int compare(Note o1, Note o2) {
                if(o1.getDateOfNote().equals(o2.getDateOfNote()))
                    return o1.getTimeOfNote().compareTo(o2.getTimeOfNote());
                return o1.getDateOfNote().compareTo(o2.getDateOfNote());
            }
        });

        adapter.notifyDataSetChanged();
    }

    //------------------------------------
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        openNoteActivity((Note) parent.getItemAtPosition(position));
    }

    private void openNoteActivity(@Nullable Note note) {
        Intent noteIntent=new Intent(AllNoteActivity.this,NoteActivity.class);
        if(note!=null){
            noteIntent.putExtra("sentNote",note);
        }
        startActivityForResult(noteIntent,0);
    }


    //----------------------------------------------------------------
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
        MainActivity.db.insertNote(n);
        refreshDataListView();
        Toast.makeText(this,"Bạn vừa thêm thàng công 1 ghi chú mới",Toast.LENGTH_SHORT).show();
    }

    public void updateNote(Note n){
        MainActivity.db.updateNote(n);
        refreshDataListView();
        Toast.makeText(this,"Bạn vừa cập nhật thông tin ghi chú",Toast.LENGTH_SHORT).show();
    }

    public void deleteNote(Note n){
        MainActivity.db.deleteNote(n);
        refreshDataListView();
        Toast.makeText(this,"Bạn vừa xóa một ghi chú",Toast.LENGTH_SHORT).show();
    }

}
