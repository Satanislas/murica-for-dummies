package com.example.murica_for_dummies.Utils;

public class VolumeConverters {
    public static double gillToLiters(double gill){
        return gill * 0.142065;
    }

    public static double pintToLiters(double pint){
        return pint * 0.473176;
    }

    public static double gallonToLiters(double gallon){
        return gallon * 3.78541;
    }

    public static double litersToGill(double liters){
        return liters / 0.142065;
    }

    public static double litersToPint(double liters){
        return liters / 0.473176;
    }

    public static double litersToGallon(double liters){
        return liters / 3.78541;
    }
}
