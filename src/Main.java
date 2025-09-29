

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in);
    public static boolean isRunning = true;
    public static Konto konto = new Konto();

    public static void main(String[] args) {

        System.out.println("Hej och välkommen till din personal-finance applikation.");

        while(isRunning){
            visaMeny();
            try {
                int menyVal = scanner.nextInt();
                scanner.nextLine();

                switch (menyVal) {
                    case 1:
                        konto.läggTillTransaktion();
                        break;
                    case 2:
                        konto.raderaTransaktion();
                        break;
                    case 3:
                        konto.visaSaldo();
                        break;
                    case 4:
                        konto.visaSpenderadePengar();
                        break;
                    case 5:
                        konto.visaInkomst();
                        break;
                    case 6:
                        isRunning = false;
                        System.out.println("Välkommen åter!");
                        break;
                    default:
                        System.out.println("Fel typ av input, försök igen!");
                        break;
                }
            } catch (InputMismatchException exception){
                System.out.println("Du måste ange en siffra mellan 1-6. Försök igen!");
                scanner.nextLine();
            }
        }
        scanner.close();


    }

    public static void visaMeny(){
        System.out.println("**********MENY*********");
        System.out.println("1. Lägg in transaktioner");
        System.out.println("2. Radera transaktioner");
        System.out.println("3. Se nuvarande kontosaldo");
        System.out.println("4. Se pengar spenderade årsvis, månadsvis, veckovis, dagsvis");
        System.out.println("5. Se inkomst årsvis, månadsvis, veckovis, dagsvis");
        System.out.println("6. Avsluta");
        System.out.println("************************************************************");
        System.out.println("Tryck in ditt val (1-6): ");
    }

}
