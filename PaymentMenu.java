import java.util.Scanner;

// Added from Payment-and-testing.
public class PaymentMenu {

    public static void process(Scanner in, Patient p) {
        Payment payment = new Payment(p.getName(), Payment.getFee(p.getSeverity()));
        System.out.println("\n--- Payment ---");
        System.out.println("Consultation fee: " + payment.getBalance());

        boolean paying = true;
        while (paying && !payment.isSettled()) {
            System.out.println("\n1.Pay in full\n2.Pay in installment\n3.View balance\n0.Pay later");
            int choice = Main.readInt(in, "Enter option: ");

            switch (choice) {
                case 1:
                    payment.payFull();
                    System.out.println("Paid in full.");
                    break;

                case 2:
                    double amount = readAmount(in, "Enter amount: ");
                    if (payment.payInstallment(amount)) {
                        System.out.println("Payment received. Balance: " + payment.getBalance());
                    } else {
                        System.out.println("Invalid amount. Max is " + payment.getBalance());
                    }
                    break;

                case 3:
                    System.out.println("Balance: " + payment.getBalance());
                    break;

                case 0:
                    paying = false;
                    break;

                default:
                    System.out.println("Not an option. Try again.");
            }
        }

        payment.printReceipt();
    }

    public static double readAmount(Scanner in, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(in.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }
}
