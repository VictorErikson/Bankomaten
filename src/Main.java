import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int balance = 1000;
        int choice = -1;
        List<Transaction> history = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        showWelcome();

        while(choice != 0) {
            showMenu();
            choice = scanner.nextInt();
            balance = showResult(choice, balance, scanner, history, formatter);
        }
    }

    static class Transaction {
        String type;
        int amount;
        int balance;
        LocalDateTime time;

        public Transaction(String type, int amount, int balance, LocalDateTime time) {
            this.type = type;
            this.amount = amount;
            this.balance = balance;
            this.time = time;
        }
    }

    public static void showWelcome() {
        System.out.println("--- VÄLKOMMEN TILL BANKOMATEN ---");
    }

    public static void showMenu(){
        System.out.println("0. Avsluta | 1. Se saldo | 2. Insättning/Uttag | 3. Räkna ut årlig ränta | 4. Kolla historik");
        System.out.print("Ditt val: ");
    }

    public static int showResult(int choice, int balance, Scanner scanner, List<Transaction> history, DateTimeFormatter formatter){
        if (choice == 1) {
            System.out.println("Ditt saldo är: " + balance + " kr");
        } else if (choice == 2) {
            balance = handleTransaction(balance, scanner, history);
        }else if (choice == 3) {
            int interest = calculateInterest(balance, scanner);
            System.out.println("Din årliga ränta är: " + interest + "kr");
            System.out.println("Totalt värde efter år ett: " + (interest + balance) + "kr");
        }else if (choice == 4) {
            for (Transaction transaction : history) {
                System.out.println(
                        transaction.time.format(formatter) +
                                " | " + transaction.type +
                                " | " + transaction.amount + " kr" +
                                " | Saldo: " + transaction.balance + " kr"
                );
            }
        }else if (choice == 0) {
            System.out.println("Kortet matas ut. Hejdå!");
        }
        return balance;
    }

    public static int calculateInterest(int amount, Scanner scanner){
        System.out.println("Ange ränta i procent: ");
        int rate = scanner.nextInt();
        return amount * rate / 100;
    }

    public static int handleTransaction(int balance, Scanner scanner, List<Transaction> history ){
        while (true) {
            System.out.println("1. Insättning | 2. Uttag | 3. Tillbaka");
            int transactionChoice = scanner.nextInt();

            if (transactionChoice == 1) {
                System.out.println("Välj summa:");
                int inset = scanner.nextInt();
                LocalDateTime now = LocalDateTime.now();
                balance += inset;
                history.add(new Transaction("Insättning", inset, balance, now));
                System.out.println("Insättning lyckades, dit nya saldo är: " + balance + " kr");
                return balance;
            } else if (transactionChoice == 2) {

                while (true) {
                    System.out.println("Välj summa:");
                    int out = scanner.nextInt();

                    if (out > balance) {
                        System.out.println("Uttag Misslyckades, inte tillräckligt med pengar, välj annan summa.");
                    } else {
                        LocalDateTime now = LocalDateTime.now();

                        balance -= out;
                        history.add(new Transaction("Uttag", out, balance, now));
                        System.out.println("Uttag lyckades, ditt nya saldo är: " + balance + " kr");
                        break;
                    }
                }
                return balance;
            } else if (transactionChoice == 3){
                return balance;
            } else {
                System.out.println("Ogiltigt val.");
            }
        }
    }
}