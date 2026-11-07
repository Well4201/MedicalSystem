import java.time.LocalDate;

public abstract class Appointment {
    private final LocalDate date;
    private final Doctor doctor;
    private AppointmentStatus status;

    public Appointment(LocalDate date, Doctor doctor, AppointmentStatus status) {
        this.date = date;
        this.doctor = doctor;
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    // Doctor dashboard calls this when a patient is tended, held, etc.
    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    // Each subclass names its own type ("Walk-in", "Scheduled", "Follow-up").
    public abstract String getType();

    public void printSummary() {
        System.out.println("Type:   " + getType());
        System.out.println("Date:   " + date);
        System.out.println("Doctor: Dr. " + doctor.getUsername());
        System.out.println("Status: " + status);
    }
}
