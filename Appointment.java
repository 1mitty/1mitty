package communitygarden;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Appointment {
    private User user;
    private Date appointmentDate;
    private String appointmentTime;

    public Appointment(User user, Date appointmentDate, String appointmentTime) {
        this.user = user;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
    }

    public User getUser() {
        return user;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }
//override toString to display the appointment information
    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return "Appointment for " + user.getName() + " on " + sdf.format(appointmentDate) + " at " + appointmentTime;
    }
}

