import java.time.LocalDate;
import java.time.LocalTime;

public class ScheduledAppointment extends Appointment {
    private final LocalTime timeSlot;

    public ScheduledAppointment(LocalDate date, LocalTime timeSlot, Doctor doctor) {
        super(date, doctor, AppointmentStatus.WAITING);
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Date cannot be in the past.");
        }
        this.timeSlot = timeSlot;
    }

    public LocalTime getTimeSlot() {
        return timeSlot;
    }

    @Override
    public String getType() {
        return "Scheduled";
    }

    @Override
    public void printSummary() {
        super.printSummary();
        System.out.println("Time:   " + timeSlot);
    }
}
