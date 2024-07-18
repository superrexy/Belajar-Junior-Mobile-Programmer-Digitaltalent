package com.dts.haripertama.Buku;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dts.haripertama.DailyNote.NoteModel;
import com.dts.haripertama.R;

import java.util.ArrayList;

public class ListBukuLVAdapter extends ArrayAdapter<BukuModel> {
    public ListBukuLVAdapter(Context context, ArrayList<BukuModel> bukuModels) {
        super(context, 0, bukuModels);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.daily_note, parent, false);
        }

        BukuModel buku = getItem(position);
        TextView title = listItemView.findViewById(R.id.tvTitleDailyNote);
        TextView description = listItemView.findViewById(R.id.tvDescriptionDailyNote);
        TextView date = listItemView.findViewById(R.id.tvDateDailyNote);

        assert buku != null;
        title.setText(buku.getJudul());
        description.setText(buku.getDeskripsi());
        date.setText(buku.getDate());

        return listItemView;
    }
}
