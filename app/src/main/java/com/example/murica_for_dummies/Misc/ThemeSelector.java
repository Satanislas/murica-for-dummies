package com.example.murica_for_dummies.Misc;

import android.content.Context;

import com.example.murica_for_dummies.R;

public class ThemeSelector {

    private static int currentSelectedTheme = 0;

    public static int getSelectedTheme(){
        if (currentSelectedTheme != 0) return currentSelectedTheme;
        else return -1;
    }

    public static void setCurrentSelectedTheme(int id){
        currentSelectedTheme = id;
        //DATABASE
    }

    public static void SetTheme(Context context){
        if (currentSelectedTheme != 0) context.setTheme(currentSelectedTheme);
        else context.setTheme(R.style.Base_Theme_Murica_for_dummies);
    }
}
