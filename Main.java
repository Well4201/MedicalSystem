import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final int EYES = 1;
    private static final int DENTAL = 2;
    private static final int PHYSICAL = 3;

    private static final ArrayList<Patient> eyeQueue = new ArrayList<>();
    private static final ArrayList<Patient> dentalQueue = new ArrayList<>();
    private static final ArrayList<Patient> physicalQueue = new ArrayList<>();
    private static final ArrayList<Patient> patients = new ArrayList<>();
    private static final BookingMenu bookingMenu = new BookingMenu();
    private static int nextPatientTicket = 1;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        RegLogin regLogin = new RegLogin();

        boolean running = true;

        while (running) {
            System.out.println("\n=== Clinic Queue System ===");
            System.out.println("1.Patient\n2.Doctor\n0.Exit");
            int userType = readInt(in, "Enter option: ");

            switch (userType) {
                case 1:
                    handlePatient(in);
                    break;

                case 2:
                    handleDoctor(in, regLogin);
                    break;

                case 0:
                    running = false;
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Not an option. Try again.");
            }
        }

        in.close();
    }

    public static void handlePatient(Scanner in) {
        System.out.println("\n1.New patient\n2.Returning patient");
        int patientChoice = readInt(in, "Enter option: ");
        Patient p;
        if (patientChoice == 1) {
            PatientMenu patientMenu = new PatientMenu(in);
            p = patientMenu.registerPatient();
            patients.add(p);
        } else if (patientChoice == 2) {
            System.out.print("Enter your patient ID: ");
            String patientId = in.nextLine().trim();
            p = findPatient(patientId);
            if (p == null) {
                System.out.println("No patient found with that ID.");
                return;
            }
            System.out.println("Welcome back, " + p.getName() + ".");
        } else {
            System.out.println("Not an option. Try again.");
            return;
        }

        boolean deciding = true;
        while (deciding) {
            System.out.println("\nPlease choose an option:");
            System.out.println("1.Join the queue (walk-in)");
            System.out.println("2.View current queue");
            System.out.println("3.Book or manage appointments");
            System.out.println("0.Cancel");
            int choice = readInt(in, "Enter option: ");

            switch (choice) {
                case 1:
                    p.setAppointment(new WalkInAppointment(null));
                    joinQueue(in, p);
                    deciding = false;
                    break;

                case 2:
                    printQueueOverview();
                    break;

                case 3:
                    bookingMenu.show(in, p, patient -> joinQueue(in, patient));
                    break;

                case 0:
                    System.out.println("Registration cancelled.");
                    deciding = false;
                    break;

                default:
                    System.out.println("Not an option. Try again.");
            }
        }
    }

    private static Patient findPatient(String patientId) {
        for (Patient patient : patients) {
            if (patient.getPatientId().equalsIgnoreCase(patientId)) {
                return patient;
            }
        }
        return null;
    }

    public static void handleDoctor(Scanner in, RegLogin regLogin) {
        boolean loggedIn = false;

        do {
            System.out.println("\n--- Doctor Access ---");
            System.out.println("1.Login\n2.Sign Up");
            int authChoice = readInt(in, "Enter option: ");

            if (authChoice == 1) {
                loggedIn = regLogin.login(in);
                if (!loggedIn) {
                    System.out.println("Incorrect credentials! Try again.");
                }
            } else if (authChoice == 2) {
                regLogin.signUp(in);
            } else {
                System.out.println("Error not an option. Try again.");
            }
        } while (!loggedIn);

        Doctor doctor = regLogin.getCurrentDoctor();

        boolean working = true;
        while (working) {
            System.out.println("\n--- Doctor Dashboard (Dr. " + doctor.getUsername()
                    + " - " + doctor.expertise() + ") ---");
            printQueueForDoctor(doctor);

            if (!getDoctorQueue(doctor).isEmpty()) {
                System.out.println("\n1.Tend to next patient\n2.Put next patient on hold\n0.Logout");
            } else {
                System.out.println("\n0.Logout");
            }
            int choice = readInt(in, "Enter option: ");

            switch (choice) {
                case 1:
                    if (getDoctorQueue(doctor).isEmpty()) {
                        System.out.println("Not an option. Try again.");
                        break;
                    }
                    Patient tended = dequeuePatientForDoctor(doctor);
                    if (tended.getAppointment() != null) {
                        tended.getAppointment().setDoctor(doctor);
                        tended.getAppointment().setStatus(AppointmentStatus.IN_CONSULTATION);
                    }
                    tendPatient(tended);
                    break;

                case 2:
                    if (getDoctorQueue(doctor).isEmpty()) {
                        System.out.println("Not an option. Try again.");
                        break;
                    }
                    Patient held = dequeuePatientForDoctor(doctor);
                    if (held.getAppointment() != null) {
                        held.getAppointment().setStatus(AppointmentStatus.ON_HOLD);
                    }
                    System.out.println(held.getName() + " has been placed on hold and moved to the back of the queue.");
                    enqueuePatientByExpertise(held);
                    if (held.getAppointment() != null) {
                        held.getAppointment().setStatus(AppointmentStatus.WAITING);
                    }
                    break;

                case 0:
                    working = false;
                    System.out.println("Logging out...");
                    break;

                default:
                    System.out.println("Not an option. Try again.");
            }
        }
    }

    private static void enqueuePatientByExpertise(Patient p) {
        switch (getDoctorExpertise(p)) {
            case EYES:
                eyeQueue.add(p);
                break;
            case DENTAL:
                dentalQueue.add(p);
                break;
            case PHYSICAL:
            default:
                physicalQueue.add(p);
                break;
        }
    }

    private static void joinQueue(Scanner in, Patient patient) {
        PaymentMenu.process(in, patient);
        patient.setTicket(nextPatientTicket++);
        enqueuePatientByExpertise(patient);
        System.out.println("\n--- Patient Ticket ---");
        System.out.println("Ticket number: " + patient.getTicket());
        System.out.println("Patient: " + patient.getName());
        System.out.println("Payment plan: " + patient.getPayment().getPaymentPlan());
        System.out.println("You have been added to the "
                + getExpertiseName(getDoctorExpertise(patient)) + " queue.");
    }

    private static Patient dequeuePatientForDoctor(Doctor doctor) {
        ArrayList<Patient> queue = getDoctorQueue(doctor);
        if (queue.isEmpty()) {
            return null;
        }
        return queue.remove(0);
    }

    private static ArrayList<Patient> getDoctorQueue(Doctor doctor) {
        return getDoctorQueue(getDoctorExpertiseFromDoctor(doctor));
    }

    private static ArrayList<Patient> getDoctorQueue(int expertise) {
        switch (expertise) {
            case EYES:
                return eyeQueue;
            case DENTAL:
                return dentalQueue;
            case PHYSICAL:
            default:
                return physicalQueue;
        }
    }

    private static int getDoctorExpertise(Patient p) {
        if (p == null || p.getSuggestedSpecialty() == null) {
            return PHYSICAL;
        }

        String specialty = p.getSuggestedSpecialty().toLowerCase();
        if (specialty.contains("eye")) {
            return EYES;
        }
        if (specialty.contains("dental")) {
            return DENTAL;
        }
        return PHYSICAL;
    }

    private static int getDoctorExpertiseFromDoctor(Doctor doctor) {
        String expertise = doctor.expertise().toLowerCase();
        if (expertise.contains("eye")) {
            return EYES;
        }
        if (expertise.contains("dental")) {
            return DENTAL;
        }
        return PHYSICAL;
    }

    private static void printQueueOverview() {
        System.out.println("Eye queue: " + eyeQueue.size());
        System.out.println("Dental queue: " + dentalQueue.size());
        System.out.println("Physical queue: " + physicalQueue.size());
    }

    private static void printQueueForDoctor(Doctor doctor) {
        ArrayList<Patient> queue = getDoctorQueue(doctor);
        if (queue.isEmpty()) {
            System.out.println("The queue is currently empty.");
            return;
        }

        System.out.println("Current patient queue:");
        for (int i = 0; i < queue.size(); i++) {
            Patient patient = queue.get(i);
            System.out.println((i + 1) + ". Ticket " + patient.getTicket() + " - "
                    + patient.getName() + " (Age: " + patient.getAge() + ") - "
                    + patient.getSymptoms() + " (Severity: " + patient.getSeverity() + ")"
                    + " [Payment plan: " + patient.getPayment().getPaymentPlan() + "]"
                    + " [Senior: " + yesNo(patient.isSenior())
                    + ", PWD: " + yesNo(patient.isPWD())
                    + ", Pregnant: " + yesNo(patient.isPregnant()) + "]");
        }
    }

    private static String yesNo(boolean value) {
        return value ? "Yes" : "No";
    }

    private static String getExpertiseName(int expertise) {
        switch (expertise) {
            case EYES:
                return "Eyes";
            case DENTAL:
                return "Dental";
            case PHYSICAL:
            default:
                return "Physical";
        }
    }

    public static int readInt(Scanner in, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(in.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    public static void tendPatient(Patient p) {
        System.out.println("\n--- Tending Patient ---");
        System.out.println("Name: " + p.getName());
        System.out.println("Age: " + p.getAge());
        System.out.println("Senior Citizen: " + yesNo(p.isSenior()));
        System.out.println("PWD: " + yesNo(p.isPWD()));
        System.out.println("Pregnant: " + yesNo(p.isPregnant()));
        System.out.println("Symptoms: " + p.getSymptoms());
        System.out.println("Severity: " + p.getSeverity());
        System.out.println("\n--- Receipt ---");
        System.out.println("Patient " + p.getName() + " has been seen by the doctor.");
        System.out.println("Diagnosis result and receipt issued. Thank you.");

        if (p.getPayment() == null) {
            p.setPayment(new Payment(p.getName(), Payment.getFee(p.getSeverity())));
        }
        p.getPayment().printReceipt();

        if (p.getAppointment() != null) {
            p.getAppointment().setStatus(AppointmentStatus.COMPLETED);
        }
    }
}
