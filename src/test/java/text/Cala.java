package text;

import java.util.Calendar;

public class Cala {

    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        System.out.println(hour);
    }
}
