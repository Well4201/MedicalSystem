import java.util.ArrayList;
import java.util.Scanner;

// Added from Registration-and-priority.
public class PatientMenu {

    private Scanner scanner;

    // makes its own scanner to read what the user types
    public PatientMenu() {
        this.scanner = new Scanner(System.in);
    }

    // uses a scanner that was made somewhere else
    public PatientMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    // asks the questions, creates the patient, and gives it back
    public Patient registerPatient() {
        System.out.println("\n=== PATIENT REGISTRATION ===");

        System.out.print("Enter name: ");
        String name = scanner.nextLine().trim();

        int age = askNumber("Enter age: ", 1, 120);

        System.out.println("\nSeverity level:");
        System.out.println("1 = Lowest");
        System.out.println("2 = Moderate");
        System.out.println("3 = Severe");
        int severity = askNumber("Enter severity (1-3): ", 1, 3);

        boolean senior;
        if (age < 60) {
            System.out.println("Are you a senior citizen? (Y/N): N (age is below 60)");
            senior = false;
        } else {
            System.out.println("Are you a senior citizen? (Y/N): Y (age is 60 or above)");
            senior = true;
        }
        boolean pwd = askYesNo("Are you a person with a disability (PWD)? (Y/N): ");
        boolean pregnant = askYesNo("Are you pregnant? (Y/N): ");

        // let the patient pick symptoms from the menu
        SymptomCatalog catalog = new SymptomCatalog(scanner);
        ArrayList<String> selected = catalog.selectSymptoms();
        if (selected.isEmpty()) {
            System.out.println("No symptoms selected. Defaulting to Physical.");
        }
        String symptoms = catalog.getSymptomsAsText();
        String specialty = catalog.getSuggestedSpecialty();

        // create the patient (id and arrival order are set automatically)
        Patient patient = new Patient(name, age, symptoms, severity, senior, pwd, pregnant, specialty);

        displayPatient(patient);
        return patient;
    }

    // displays the patient information
    public void displayPatient(Patient p) {
        System.out.println("\n=== REGISTRATION DETAILS ===");
        System.out.println("Patient ID: " + p.getPatientId());
        System.out.println("Arrival Order: " + p.getArrivalOrder());
        System.out.println("Name: " + p.getName());
        System.out.println("Age: " + p.getAge());
        System.out.println("Severity: " + p.getSeverity());
        System.out.println("Senior Citizen: " + (p.isSenior() ? "Yes" : "No"));
        System.out.println("PWD: " + (p.isPWD() ? "Yes" : "No"));
        System.out.println("Pregnant: " + (p.isPregnant() ? "Yes" : "No"));
        System.out.println("Symptoms: " + p.getSymptoms());
        System.out.println("Priority Status: " + (p.isPriority() ? "PRIORITY" : "Regular"));
        System.out.println("Suggested Specialty: " + p.getSuggestedSpecialty());
    }

    // asks again and again until the number is between min and max
    private int askNumber(String message, int min, int max) {
        int number = -1;
        while (number < min || number > max) {
            System.out.print(message);
            try {
                number = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                // if the user typed letters, it counts it as a wrong answer
                number = -1;
            }
            if (number < min || number > max) {
                System.out.println("Please enter a number from " + min + " to " + max + ".");
            }
        }
        return number;
    }

    // asks again and again until the user types y or n
    private boolean askYesNo(String message) {
        while (true) {
            System.out.print(message);
            String answer = scanner.nextLine().trim().toUpperCase();
            if (answer.equals("Y")) {
                return true;
            } else if (answer.equals("N")) {
                return false;
            }
            System.out.println("Please enter Y or N.");
        }
    }
}
