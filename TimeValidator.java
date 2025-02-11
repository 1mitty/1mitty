package communitygarden;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeValidator {

    public static boolean isValidAppointmentTime(Date date, String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a"); // Format to handle AM/PM
        sdf.setLenient(false);
        try {
            // Parse time with AM/PM
            Date parsedTime = sdf.parse(time);

            // Check if the time is within the garden's operating hours
            return isWithinOperatingHours(date, parsedTime);
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isWithinOperatingHours(Date date, Date time) {
        // Convert time into calendar format to get the hour
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        int hour = calendar.get(Calendar.HOUR_OF_DAY); // Get the hour (24-hour format)
        int minute = calendar.get(Calendar.MINUTE);

        // Check the day of the week
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        // Garden operates from 8 AM - 8 PM (Mon-Fri) and 8 AM - 5 PM (Sat)
        if (dayOfWeek == Calendar.SUNDAY) {
            return false; // Garden is closed on Sundays
        } else if (dayOfWeek == Calendar.SATURDAY) {
            // Saturday: 8:00 AM - 5:00 PM
            return (hour >= 8 && hour <= 17);
        } else {
            // Monday to Friday: 8:00 AM - 8:00 PM
            return (hour >= 8 && hour <= 20);
        }
    }
}
