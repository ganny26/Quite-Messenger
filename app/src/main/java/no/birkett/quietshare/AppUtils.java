package no.birkett.quietshare;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Selva on 10/24/2017.
 */

public  class AppUtils {
    public static String getTime(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String strDate = sdf.format(cal.getTime());
        return strDate;
    }
}
