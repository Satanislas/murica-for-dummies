package com.example.murica_for_dummies.Utils;

public class DistanceConverters {
    public static double inchToMeter(double inches){
        return inches * 0.0254;
    }

    public static double feetToMeter(double feet){
        return feet * 0.3048;
    }

    public static double milesToMeter(double miles){
        return miles * 1609.34;
    }

    public static double meterToInch(double meters){
        return meters / 0.0254;
    }

    public static double meterToFeet(double meters){
        return meters / 0.3048;
    }

    public static double meterToMiles(double meters){
        return meters / 1609.34;
    }
}
