import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class Transaktion {
    LocalDate datum;
    double belopp;
    String typ;
    int vecka;


    Transaktion(LocalDate datum, double belopp, String typ, int vecka){
        this.datum = datum;
        this.belopp = belopp;
        this.typ = typ;
        this.vecka = vecka;

    }
    public DayOfWeek getDag(){
        return datum.getDayOfWeek();
    }

    public int getVecka(){
        WeekFields wf = WeekFields.of(Locale.getDefault());
        return datum.get(wf.weekOfWeekBasedYear());
    }

    public Month getMånad(){
        return datum.getMonth();
    }

    public int getÅr(){
        return datum.getYear();
    }

    public String toString(){
        return datum + " V." + vecka + " | " + typ + " | " + belopp + "kr";
    }

}
