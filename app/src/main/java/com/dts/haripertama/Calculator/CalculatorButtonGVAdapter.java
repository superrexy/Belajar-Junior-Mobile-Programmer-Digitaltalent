package com.dts.haripertama.Calculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.dts.haripertama.R;

import java.util.ArrayList;

public class CalculatorButtonGVAdapter extends ArrayAdapter<CalculatorButtonModel> {
    public CalculatorButtonGVAdapter(Context context, ArrayList<CalculatorButtonModel> buttonModels) {
        super(context, 0, buttonModels);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull android.view.ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.calculator_buttons, parent, false);
        }

        CalculatorButtonModel calculatorButtonModel = getItem(position);
        TextView button = listitemView.findViewById(R.id.idTextButtonCalculator);

        assert calculatorButtonModel != null;
        button.setText(calculatorButtonModel.getTitle());

        return listitemView;
    }
}
