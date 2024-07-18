package com.dts.haripertama.DailyNote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dts.haripertama.R;

import java.util.ArrayList;

public class DailyNotesLVAdapter extends ArrayAdapter<NoteModel> {
    DailyNotesLVAdapter(Context context, ArrayList<NoteModel> dailyNotes) {
        super(context, 0, dailyNotes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.daily_note, parent, false);
        }

        NoteModel dailyNote = getItem(position);
        TextView title = listItemView.findViewById(R.id.tvTitleDailyNote);
        TextView description = listItemView.findViewById(R.id.tvDescriptionDailyNote);
        TextView date = listItemView.findViewById(R.id.tvDateDailyNote);

        assert dailyNote != null;
        title.setText(dailyNote.getTitle());
        description.setText(dailyNote.getDescription());
        date.setText(dailyNote.getDate().toString());

        return listItemView;
    }
}
