package com.dts.haripertama.Calculator;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dts.haripertama.R;

import java.util.ArrayList;
import java.util.Arrays;

public class Calculator extends AppCompatActivity {

    ImageButton btnbackButtonCalculator;
    TextView showResultInputCalculator;
    String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculator);

        btnbackButtonCalculator = findViewById(R.id.backButton);
        showResultInputCalculator = findViewById(R.id.showResultInputCalculator);
        GridView buttonsGridView = findViewById(R.id.gridViewButtonsCalculator);
        ArrayList<CalculatorButtonModel> buttonModels = new ArrayList<>();

        buttonModels.add(new CalculatorButtonModel("7"));
        buttonModels.add(new CalculatorButtonModel("8"));
        buttonModels.add(new CalculatorButtonModel("9"));
        buttonModels.add(new CalculatorButtonModel("/"));
        buttonModels.add(new CalculatorButtonModel("4"));
        buttonModels.add(new CalculatorButtonModel("5"));
        buttonModels.add(new CalculatorButtonModel("6"));
        buttonModels.add(new CalculatorButtonModel("*"));
        buttonModels.add(new CalculatorButtonModel("1"));
        buttonModels.add(new CalculatorButtonModel("2"));
        buttonModels.add(new CalculatorButtonModel("3"));
        buttonModels.add(new CalculatorButtonModel("-"));
        buttonModels.add(new CalculatorButtonModel("C"));
        buttonModels.add(new CalculatorButtonModel("0"));
        buttonModels.add(new CalculatorButtonModel("+"));
        buttonModels.add(new CalculatorButtonModel("="));

        CalculatorButtonGVAdapter adapter = new CalculatorButtonGVAdapter(this, buttonModels);
        buttonsGridView.setAdapter(adapter);
        buttonsGridView.setOnItemClickListener((adapterView, view, i, l) -> {
            String[] numbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
            String[] operators = {"+", "-", "*", "/", "C", "="};

            CalculatorButtonModel buttonModel = buttonModels.get(i);
            String title = buttonModel.getTitle();

            if (Arrays.asList(numbers).contains(title)) {
                if (result.equals("0")) {
                    result = "";
                }

                result += title;
            } else if (Arrays.asList(operators).contains(title)) {
                if (title.equals("C")) {
                    result = "";
                } else if (title.equals("=")) {
                    // Calculate the result
                    String[] parts = result.split("(?<=\\d)(?=\\D)|(?<=\\D)(?=\\d)");
                    if (parts.length != 3) {
                        return;
                    }

                    Float num1 = Float.parseFloat(parts[0]);
                    Float num2 = Float.parseFloat(parts[2]);
                    String operator = parts[1];

                    switch (operator) {
                        case "+":
                            result = String.valueOf(num1 + num2);
                            break;
                        case "-":
                            result = String.valueOf(num1 - num2);
                            break;
                        case "*":
                            result = String.valueOf(num1 * num2);
                            break;
                        case "/":
                            result = String.valueOf(num1 / num2);
                            break;
                    }
                } else {
                    String[] parts = result.split("(?<=\\d)(?=\\D)|(?<=\\D)(?=\\d)");
                    if (parts.length == 3) {
                        return;
                    }

                    result += title;
                }
            }

            showResultInputCalculator.setText(result);


            Log.d("Calculator", "Result: " + result);
            // Do something with the title
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnbackButtonCalculator.setOnClickListener(view -> finish());
    }
}