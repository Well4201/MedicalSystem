import java.time.LocalDate;

public class WalkInAppointment extends Appointment {

   
    public WalkInAppointment(Doctor doctor) {
        super(LocalDate.now(), doctor, AppointmentStatus.WAITING);
    }

    @Override
    public String getType() {
        return "Walk-in";
    }
}
