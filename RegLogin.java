import java.util.Scanner;

public class RegLogin {


    private String[][] credentials = new String[1][2];
    private int doctorCount = 0;
    private Doctor currentDoctor = null;

    public RegLogin() {
        credentials[0][0] = "Admin";
        credentials[0][1] = "Admin123";
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

        if (doctorCount == credentials.length) {
            String[][] temp = new String[credentials.length + 1][2];
            for (int i = 0; i < credentials.length; i++) {
                temp[i][0] = credentials[i][0];
                temp[i][1] = credentials[i][1];
            }
            credentials = temp;
        }

        credentials[doctorCount][0] = newUser;
        credentials[doctorCount][1] = newPass;
        doctorCount++;

        System.out.println("Sign up successful! You can now log in as a doctor.");
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