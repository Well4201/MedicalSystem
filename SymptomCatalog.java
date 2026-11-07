import java.util.ArrayList;
import java.util.Scanner;

// Added from Registration-and-priority.
public class SymptomCatalog {

    private Scanner scanner;

    private String[] eyesSymptoms = {
        "Blurry vision", "Red eyes", "Eye pain", "Itchy eyes", "Watery eyes"
    };
    private String[] dentalSymptoms = {
        "Toothache", "Swollen gums", "Bleeding gums", "Sensitive teeth", "Bad breath"
    };
    private String[] physicalSymptoms = {
        "Fever", "Cough", "Headache", "Body pain", "Stomach ache"
    };

    // selected symptoms will be stored here
    private ArrayList<String> selectedSymptoms = new ArrayList<String>();

    public SymptomCatalog(Scanner scanner) {
        this.scanner = scanner;
    }

    // main menu
    public ArrayList<String> selectSymptoms() {
        selectedSymptoms.clear();
        boolean done = false;

        while (!done) {
            System.out.println("\n=== SYMPTOM CATEGORIES ===");
            System.out.println("1. Eyes");
            System.out.println("2. Dental");
            System.out.println("3. Physical");
            System.out.println("0. Finish selecting symptoms");
            System.out.print("Choose: ");
            int choice = readNumber();

            switch (choice) {
                case 1:
                    chooseFromList("EYES", eyesSymptoms);
                    break;
                case 2:
                    chooseFromList("DENTAL", dentalSymptoms);
                    break;
                case 3:
                    chooseFromList("PHYSICAL", physicalSymptoms);
                    break;
                case 0:
                    done = true;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
        return selectedSymptoms;
    }

    // shows one category and lets the patient pick many symptoms
    private void chooseFromList(String title, String[] symptoms) {
        boolean back = false;

        while (!back) {
            System.out.println("\n--- " + title + " SYMPTOMS ---");
            for (int i = 0; i < symptoms.length; i++) {
                System.out.println((i + 1) + ". " + symptoms[i]);
            }
            System.out.println("0. Back to categories");
            System.out.print("Select a symptom: ");
            int choice = readNumber();

            if (choice == 0) {
                back = true;
            } else if (choice >= 1 && choice <= symptoms.length) {
                String symptom = symptoms[choice - 1];
                if (selectedSymptoms.contains(symptom)) {
                    System.out.println(symptom + " is already selected.");
                } else {
                    selectedSymptoms.add(symptom);
                    System.out.println("Added: " + symptom);
                }
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // suggested specialty that matcehs the most selected symptoms
    public String getSuggestedSpecialty() {
        int eyesCount = countMatches(eyesSymptoms);
        int dentalCount = countMatches(dentalSymptoms);
        int physicalCount = countMatches(physicalSymptoms);

        if (selectedSymptoms.isEmpty()) {
            return "Physical"; //default specialty if no symptoms
        }

        if (eyesCount >= dentalCount && eyesCount >= physicalCount) {
            return "Eyes";
        } else if (dentalCount >= physicalCount) {
            return "Dental";
        } else {
            return "Physical";
        }
    }

    // counts how many selected symptoms belong to a category
    private int countMatches(String[] categorySymptoms) {
        int count = 0;
        for (int i = 0; i < categorySymptoms.length; i++) {
            if (selectedSymptoms.contains(categorySymptoms[i])) {
                count++;
            }
        }
        return count;
    }

    // this will turn the selected symptoms into one sentence, e.g. "Fever, Cough"
    public String getSymptomsAsText() {
        if (selectedSymptoms.isEmpty()) {
            return "None";
        }
        String text = "";
        for (int i = 0; i < selectedSymptoms.size(); i++) {
            text += selectedSymptoms.get(i);
            if (i < selectedSymptoms.size() - 1) {
                text += ", ";
            }
        }
        return text;
    }

    // reads a number safely so typing letters does not crash the program
    private int readNumber() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
