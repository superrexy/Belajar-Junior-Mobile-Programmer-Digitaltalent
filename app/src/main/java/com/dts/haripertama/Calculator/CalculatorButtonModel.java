package com.dts.haripertama.Calculator;

import androidx.annotation.NonNull;

public class CalculatorButtonModel {
    private String title;


    public CalculatorButtonModel(@NonNull String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
