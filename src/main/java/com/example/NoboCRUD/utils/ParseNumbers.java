package com.example.NoboCRUD.utils;

public class ParseNumbers {
    public static boolean parseToInt(String number){
        try {
            Integer.parseInt(number);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }

    public static boolean parseToDouble(String number){
        try{
            Double.parseDouble(number);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }
}
