public class Payment {

    private String patientName;
    private double total;
    private double paid;

    public Payment(String patientName, double total) {
        this.patientName = patientName;
        this.total = total;
        this.paid = 0;
    }

    // fee depends on severity
    public static double getFee(int severity) {
        if (severity == 3) {
            return 1000;
        } else if (severity == 2) {
            return 600;
        }
        return 300;
    }

    // pay the whole balance
    public void payFull() {
        paid = total;
    }

    // pay part of the balance, returns false if the amount is not ok
    public boolean payInstallment(double amount) {
        if (amount <= 0 || amount > getBalance()) {
            return false;
        }
        paid = paid + amount;
        return true;
    }

    public double getBalance() {
        return Math.round((total - paid) * 100) / 100.0;
    }

    public boolean isSettled() {
        return getBalance() == 0;
    }

    public void printReceipt() {
        System.out.println("\n====== RECEIPT ======");
        System.out.println("Patient : " + patientName);
        System.out.printf("Total   : %.2f%n", total);
        System.out.printf("Paid    : %.2f%n", paid);
        System.out.printf("Balance : %.2f%n", getBalance());
        System.out.println("=====================");
    }
}
