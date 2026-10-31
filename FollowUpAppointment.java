import java.time.LocalDate;

public class FollowUpAppointment extends Appointment {
    private final Appointment previousVisit;

    public FollowUpAppointment(LocalDate date, Doctor doctor, Appointment previousVisit) {
        super(date, doctor, AppointmentStatus.WAITING);
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Date cannot be in the past.");
        }
        this.previousVisit = previousVisit;
    }

    public Appointment getPreviousVisit() {
        return previousVisit;
    }

    @Override
    public String getType() {
        return "Follow-up";
    }

    @Override
    public void printSummary() {
        super.printSummary();
        if (previousVisit != null) {
            System.out.println("Follow-up to: " + previousVisit.getType()
                    + " visit on " + previousVisit.getDate());
        }
    }
}
