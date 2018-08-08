package com.intkhabahmed.bakenshake.utils;

public class DisplayUtils {
    public static String getFormattedMeasure(String measure, float quantity) {
        return String.valueOf(quantity) + " " + getFormattedMeasureString(measure) + " of";
    }

    private static String getFormattedMeasureString(String measure) {
        switch (measure) {
            case AppConstants.CUP:
                return "cup(s)";
            case AppConstants.G:
                return "gm(s)";
            case AppConstants.K:
                return "kg(s)";
            case AppConstants.OZ:
                return "oz(s)";
            case AppConstants.TBLSP:
                return "tablespoon(s)";
            case AppConstants.TSP:
                return "teaspoon(s)";
            case AppConstants.UNIT:
                return "unit(s)";
            default:
                return measure;
        }
    }
}
