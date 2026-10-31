import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookingMenu {

    
    private static class Booking {
        final Patient patient;
        final Appointment appointment;

        Booking(Patient patient, Appointment appointment) {
            this.patient = patient;
            this.appointment = appointment;
        }
    }

    private final List<Booking> bookings = new ArrayList<>();

    public void show(Scanner in, Patient patient, Doctor doctor, QueueManager queue) {
        boolean open = true;
        while (open) {
            System.out.println("\n--- Appointment Booking ---");
            System.out.println("1.Book scheduled appointment");
            System.out.println("2.Book follow-up appointment");
            System.out.println("3.View my bookings");
            System.out.println("4.Check in (today's bookings)");
            System.out.println("0.Back");
            int choice = readInt(in, "Enter option: ");

            switch (choice) {
                case 1:
                    bookScheduled(in, patient, doctor);
                    break;
                case 2:
                    bookFollowUp(in, patient, doctor);
                    break;
                case 3:
                    viewBookings(patient);
                    break;
                case 4:
                    checkIn(in, patient, queue);
                    break;
                case 0:
                    open = false;
                    break;
                default:
                    System.out.println("Not an option. Try again.");
            }
        }
    }

    private void bookScheduled(Scanner in, Patient patient, Doctor doctor) {
        LocalDate date = readDate(in, "Enter date (yyyy-MM-dd): ");
        LocalTime time = readTime(in, "Enter time (HH:mm, 24-hour): ");
        try {
            Appointment a = new ScheduledAppointment(date, time, doctor);
            bookings.add(new Booking(patient, a));
            System.out.println("Scheduled appointment booked.");
            a.printSummary();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void bookFollowUp(Scanner in, Patient patient, Doctor doctor) {
        Appointment previous = patient.getAppointment();
        if (previous == null) {
            System.out.println("No previous visit found, so a follow-up cannot be booked.");
            return;
        }
        LocalDate date = readDate(in, "Enter follow-up date (yyyy-MM-dd): ");
        try {
            Appointment a = new FollowUpAppointment(date, doctor, previous);
            bookings.add(new Booking(patient, a));
            System.out.println("Follow-up appointment booked.");
            a.printSummary();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void viewBookings(Patient patient) {
        boolean found = false;
        for (Booking b : bookings) {
            if (b.patient == patient) {
                System.out.println();
                b.appointment.printSummary();
                found = true;
            }
        }
        if (!found) {
            System.out.println("You have no bookings.");
        }
    }

    
    private void checkIn(Scanner in, Patient patient, QueueManager queue) {
        List<Booking> todays = new ArrayList<>();
        for (Booking b : bookings) {
            if (b.patient == patient
                    && b.appointment.getDate().equals(LocalDate.now())
                    && b.appointment.getStatus() == AppointmentStatus.WAITING) {
                todays.add(b);
            }
        }
        if (todays.isEmpty()) {
            System.out.println("You have no bookings to check in for today.");
            return;
        }

        System.out.println("\nToday's bookings:");
        for (int i = 0; i < todays.size(); i++) {
            Appointment a = todays.get(i).appointment;
            System.out.println((i + 1) + ". " + a.getType() + " with Dr. " + a.getDoctor().getUsername());
        }
        int pick = readInt(in, "Choose a booking (0 to cancel): ");
        if (pick < 1 || pick > todays.size()) {
            return;
        }

        Booking chosen = todays.get(pick - 1);
        patient.setAppointment(chosen.appointment);
        queue.enqueue(patient);
        System.out.println("Checked in. You have joined the queue.");
    }

    private LocalDate readDate(Scanner in, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return LocalDate.parse(in.nextLine().trim());
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date. Use the format yyyy-MM-dd, e.g. 2026-11-05.");
            }
        }
    }

    private LocalTime readTime(Scanner in, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return LocalTime.parse(in.nextLine().trim());
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time. Use the format HH:mm, e.g. 14:30.");
            }
        }
    }

    private int readInt(Scanner in, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(in.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }
}
