package uitstart.uit.schedule.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import uitstart.uit.schedule.R;
import uitstart.uit.schedule.model.Note;

/**
 * Created by Khang on 2/15/2017.
 */

public class NoteAdapter extends ArrayAdapter<Note> {
    private Activity context;
    private int resource;
    private List<Note> objects;

    public NoteAdapter(Activity context, int resource, List<Note> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View v=inflater.inflate(resource,null);

        TextView tvName= (TextView) v.findViewById(R.id.tvName);
        TextView tvDetail= (TextView) v.findViewById(R.id.tvDetail);
        TextView tvTime= (TextView) v.findViewById(R.id.tvTime);
        TextView tvDate= (TextView) v.findViewById(R.id.tvDate);

        Note noteSelected=objects.get(position);

        tvName.setText(noteSelected.getNameOfNote());
        tvDetail.setText(noteSelected.getDetailOfNote());
        tvTime.setText(noteSelected.getTimeOfNote());
        tvDate.setText(noteSelected.getDateOfNote());

        return v;
    }
}
