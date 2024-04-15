package com.example.murica_for_dummies.Utils;

public class GetValue {

    public static double convert(String value){
        try{
            double res = Double.parseDouble(value);
            if (res < 0){
                return Constants.ERROR_VALUE;
            }
            return res;
        }
        catch (Exception e){
            //default value returned is -1
            return Constants.ERROR_VALUE;
        }
    }
}
