import java.util.Scanner;

public class RegLogin {


    private String[][] credentials = new String[1][3];
    private int doctorCount = 0;
    private Doctor currentDoctor = null;

    public RegLogin() {
        credentials[0][0] = "Admin";
        credentials[0][1] = "Admin123";
        credentials[0][2] = "3";
        doctorCount = 1;
    }

    public void signUp(Scanner in) {
        System.out.print("Choose a username: ");
        String newUser = in.nextLine();

        for (int i = 0; i < doctorCount; i++) {
            if (credentials[i][0].equals(newUser)) {
                System.out.println("That username is already taken. Try logging in instead.");
                return;
            }
        }

        System.out.print("Choose a password: ");
        String newPass = in.nextLine();

        int expertiseChoice = readExpertiseChoice(in);

        if (doctorCount == credentials.length) {
            String[][] temp = new String[credentials.length + 1][3];
            for (int i = 0; i < credentials.length; i++) {
                temp[i][0] = credentials[i][0];
                temp[i][1] = credentials[i][1];
                temp[i][2] = credentials[i][2];
            }
            credentials = temp;
        }

        credentials[doctorCount][0] = newUser;
        credentials[doctorCount][1] = newPass;
        credentials[doctorCount][2] = String.valueOf(expertiseChoice);
        doctorCount++;

        System.out.println("Sign up successful as a " + expertiseName(expertiseChoice)
                + "! You can now log in as a doctor.");
    }

    private int readExpertiseChoice(Scanner in) {
        while (true) {
            System.out.println("Choose your expertise:");
            System.out.println("1. Eyes");
            System.out.println("2. Dental");
            System.out.println("3. Physical");
            System.out.print("Enter your expertise choice (1-3): ");
            int choice;
            try {
                choice = Integer.parseInt(in.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
                continue;
            }
            if (choice >= 1 && choice <= 3) {
                return choice;
            }
            System.out.println("Choose 1, 2, or 3.");
        }
    }

    private String expertiseName(int expertiseChoice) {
        switch (expertiseChoice) {
            case 1:
                return "Eyes Specialist";
            case 2:
                return "Dental Specialist";
            case 3:
                return "Physical Examiner";
            default:
                throw new IllegalArgumentException("Expertise choice must be 1, 2, or 3.");
        }
    }

    public boolean login(Scanner in) {
        System.out.print("Enter username: ");
        String userG = in.nextLine();
        System.out.print("Enter password: ");
        String passG = in.nextLine();

        for (int i = 0; i < doctorCount; i++) {
            if (credentials[i][0].equals(userG) && credentials[i][1].equals(passG)) {
                currentDoctor = new Doctor();
                currentDoctor.setUsername(credentials[i][0]);
                currentDoctor.setPassword(credentials[i][1]);
                currentDoctor.setExpertiseChoice(Integer.parseInt(credentials[i][2]));
                System.out.println("Login successful! Welcome, Dr. " + userG + ".");
                return true;
            }
        }

        return false;
    }

    public Doctor getCurrentDoctor() {
        return currentDoctor;
    }
}