import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;


public class Konto {
    Scanner scanner = new Scanner(System.in);
    ArrayList<Transaktion> lista = new ArrayList<>();
    int saldo;

    Konto() {
        lista = new ArrayList<>();
        this.saldo = 0;
    }

    void läggTillTransaktion() {

        while (true) {

            LocalDate datum = null;
            int vecka = 0;
            double belopp;

            System.out.print("Ange belopp: ");
            belopp = scanner.nextDouble();
            scanner.nextLine();
            if(belopp < 0 && belopp + saldo < 0) {
                System.out.println("Du har inte tillräckligt med pengar, försök igen.");
            }else{
                saldo += belopp;
                System.out.println("Ange datum (YYYY-MM-DD): ");
                String inputDatum = scanner.nextLine();

                try {
                    datum = LocalDate.parse(inputDatum, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    vecka = datum.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
                } catch (DateTimeParseException exception) {
                    System.out.println("Felaktig input, ange YYYY-MM-DD");
                    continue;
                }
                System.out.println("Ange typ (t.ex Lön/Mat/Hyra/Nöje)");
                String typ = scanner.nextLine();
                Transaktion transaktion = new Transaktion(datum, belopp, typ, vecka);
                läggTillLista(transaktion);

                System.out.println("Transaktion skapad: " + transaktion);
                System.out.println("Vill du göra en till transaktion? (Y/N): ");
                String val = scanner.nextLine();
                if (!val.equalsIgnoreCase("Y")) {
                    break;
                }
            }
        }
    }

    void läggTillLista(Transaktion transaktion) {

        lista.add(transaktion);
    }

    void visaSpenderadePengar() {

        if(lista.isEmpty()){
            System.out.println("\n-------------------------------");
            System.out.println("Du har inte gjort några utlägg.");
            System.out.println("-------------------------------\n");
            return;
        }
        boolean utlägg = false;
        for(Transaktion t : lista){
            if(t.belopp < 0){
                utlägg = true;
                break;
            }
        }
        if(!utlägg){
            System.out.println("\n-------------------------------");
            System.out.println("Du har inte gjort några utlägg.");
            System.out.println("-------------------------------\n");
            return;
        }

        while (true) {

            System.out.println("1.Årsvis\n2.Månadsvis\n3.Veckovis\n4.Dagvis\n5.Återgå till menyn");
            double sumSpenderat = 0;
            lista.sort((t1, t2) -> t1.datum.compareTo(t2.datum));

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                for (Transaktion t : lista) {
                    if (t.belopp < 0) {
                        sumSpenderat -= t.belopp;
                        switch (choice) {
                            case 1:
                                System.out.println(t.getÅr() + " | " + t.typ + " | " + t.belopp);
                                break;
                            case 2:
                                System.out.println(t.getMånad() + " | " + t.typ + " | " + t.belopp);
                                break;
                            case 3:
                                System.out.println("V." + t.getVecka() + " | " + t.typ + " | " + t.belopp);
                                break;
                            case 4:
                                System.out.println(t.getDag() + " | " + t.typ + " | " + t.belopp);
                                break;
                            case 5:
                                return;
                            default:
                                System.out.println("Fel typ av input");
                                break;
                        }
                    }
                }
                System.out.println("Du har totalt spenderat: " + sumSpenderat + "kr");

                System.out.println("Vill du se en annan tidsenhet? (Y/N)");
                String input = scanner.nextLine();
                if (!input.equalsIgnoreCase("Y")) {
                    return;
                }



            }catch (InputMismatchException e){
                System.out.println("\n");
                System.out.println("Fel typ av input, försök igen.");
                System.out.println("\n");
                scanner.nextLine();

            }
        }
    }



    void visaInkomst(){

        if(lista.isEmpty()){
            System.out.println("\n-----------------------");
            System.out.println("Du har inga inkomster.");
            System.out.println("-----------------------\n");
            return;
        }

        while(true){

            System.out.println("1.Årsvis\n2.Månadsvis\n3.Veckovis\n4.Dagvis\n5.Återgå till menyn");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                double sumInkomst = 0;
                lista.sort((t1, t2) -> t1.datum.compareTo(t2.datum));

                for (Transaktion t : lista) {
                    if (t.belopp > 0) {
                        sumInkomst += t.belopp;

                        switch (choice) {
                            case 1:
                                System.out.println(t.getÅr() + " | " + t.typ + " | " + t.belopp);
                                break;
                            case 2:
                                System.out.println(t.getMånad() + " | " + t.typ + " | " + t.belopp);
                                break;
                            case 3:
                                System.out.println("V." + t.getVecka() + " | " + t.typ + " | " + t.belopp);
                                break;
                            case 4:
                                System.out.println(t.getDag() + " | " + t.typ + " | " + t.belopp);
                                break;
                            case 5:
                                return;
                            default:
                                System.out.println("Fel typ av input");
                                break;
                        }
                    }
                }
                System.out.println("Total inkomst: " + sumInkomst + "kr");


                System.out.println("Vill du se en annan tidsenhet? (Y/N)");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("Y")) {
                } else {
                    return;
                }

            }catch (InputMismatchException exception){
                System.out.println("\n");
                System.out.println("Fel typ av input, försök igen!");
                System.out.println("\n");
                scanner.nextLine();
            }

        }


    }
    void visaSaldo(){
        System.out.println("Totala saldo på ditt konto: " + saldo + "kr");


        while (true){
            System.out.println("Ange Y för att återgå till menyn: ");
            String input = scanner.nextLine();
            if(!input.equalsIgnoreCase("Y")){
                System.out.println("Fel typ av input, vänligen försök igen!");
            }
            else {
                break;
            }
        }
    }

    void raderaTransaktion() {

        int valTaBort = 0;
        while(true) {
            if (lista.isEmpty()) {
                System.out.println("\n");
                System.out.println("Du har inte gjort några transaktion. Återgår till menyn.");
                System.out.println("\n");
                return;
            }
            lista.sort((t1, t2) -> t1.datum.compareTo(t2.datum));
            for (int i = 0; i < lista.size(); i++) {
                System.out.println((i + 1) + ". " + lista.get(i));
            }
            System.out.println("Ange vilket nummer du vill radera: ");
            try {
                valTaBort = scanner.nextInt();
                scanner.nextLine();

                if (valTaBort >= 1 && valTaBort <= lista.size()) {

                    Transaktion transaktion = lista.remove(valTaBort - 1);
                    saldo -= transaktion.belopp;
                    System.out.println("Du har raderat: " + transaktion + "\n");
                } else {
                    System.out.println("Fel input, vänligen försök igen!");
                    continue;
                }

                if (lista.isEmpty()) {
                    System.out.println("\n");
                    System.out.println("Du har inga mer transaktioner att radera. Du skickas nu tillbaka till menyn.");
                    System.out.println("\n");
                    break;
                }

                System.out.println("Vill du radera en till transaktion (Y/N)");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("Y")) {
                } else if (input.equalsIgnoreCase("N")) {
                    break;
                } else {
                    System.out.println("Fel typ av input, vänligen försök igen!");
                }
            }catch (InputMismatchException e){
                System.out.println("Måste ange en siffra/siffror");
                scanner.nextLine();
            }
        }
    }
}
