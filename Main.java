import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        QueueSystem queueSystem = new QueueSystem();
        RegLogin regLogin = new RegLogin();

        boolean running = true;

        while (running) {
            System.out.println("\n=== Clinic Queue System ===");
            System.out.println("1.Patient\n2.Doctor\n0.Exit");
            int userType = readInt(in, "Enter option: ");

            switch (userType) {
                case 1:
                    handlePatient(in, queueSystem);
                    break;

                case 2:
                    handleDoctor(in, queueSystem, regLogin);
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


    public static void handlePatient(Scanner in, QueueSystem queueSystem) {
        System.out.println("\n--- Patient Registration ---");

        System.out.print("Enter your name: ");
        String name = in.nextLine();

        int age;
        do {
            age = readInt(in, "Enter your age: ");
            if (age < 0) {
                System.out.println("Age cannot be negative.");
            }
        } while (age < 0);

        System.out.print("Enter your symptoms: ");
        String symptoms = in.nextLine();

        int severity;
        do {
            severity = readInt(in, "Enter severity (1 = lowest, 2 = moderate, 3 = severe): ");
            if (severity < 1 || severity > 3) {
                System.out.println("Severity must be 1, 2, or 3.");
            }
        } while (severity < 1 || severity > 3);

        Patient p = new Patient(name, age, symptoms, severity);


        boolean deciding = true;
        while (deciding) {
            System.out.println("\nPlease choose an option:");
            System.out.println("1.Join the queue");
            System.out.println("2.View current queue");
            System.out.println("0.Cancel");
            int choice = readInt(in, "Enter option: ");

            switch (choice) {
                case 1:
                    queueSystem.enqueue(p);
                    System.out.println("You have been added to the queue. There are "
                            + queueSystem.getSize() + " patient(s) waiting.");
                    deciding = false;
                    break;

                case 2:
                    queueSystem.printQueueCount();
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


    public static void handleDoctor(Scanner in, QueueSystem queueSystem, RegLogin regLogin) {
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
            queueSystem.printQueue();

            if (!queueSystem.isEmpty()) {
                System.out.println("\n1.Tend to next patient\n2.Put next patient on hold\n0.Logout");
            } else {
                System.out.println("\n0.Logout");
            }
            int choice = readInt(in, "Enter option: ");

            switch (choice) {
                case 1:
                    if (queueSystem.isEmpty()) {
                        System.out.println("Not an option. Try again.");
                        break;
                    }
                    Patient tended = queueSystem.dequeue();
                    tendPatient(tended);
                    break;

                case 2:
                    if (queueSystem.isEmpty()) {
                        System.out.println("Not an option. Try again.");
                        break;
                    }
                    Patient held = queueSystem.dequeue();
                    System.out.println(held.Name + " has been placed on hold and moved to the back of the queue.");
                    queueSystem.enqueue(held);
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
        System.out.println("Name: " + p.Name);
        System.out.println("Age: " + p.Age);
        System.out.println("Symptoms: " + p.Symptoms);
        System.out.println("Severity: " + p.Severity);
        System.out.println("\n--- Receipt ---");
        System.out.println("Patient " + p.Name + " has been seen by the doctor.");
        System.out.println("Diagnosis result and receipt issued. Thank you.");
    }
}