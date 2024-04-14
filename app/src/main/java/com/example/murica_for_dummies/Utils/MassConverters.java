package com.example.murica_for_dummies.Utils;

public class MassConverters {
    public static double ounceToKilograms(double ounce){
        return ounce * 0.0283495;
    }

    public static double poundToKilograms(double pound){
        return pound * 0.453592;
    }

    public static double tonToKilograms(double ton){
        return ton * 907.185;
    }

    public static double kilogramToPounds(double kilogram){
        return kilogram / 0.453592;
    }

    public static double kilogramToTon(double kilogram){
        return kilogram / 907.185;
    }

    public static double kilogramToOunce(double kilogram){
        return kilogram / 0.0283495;
    }
}
