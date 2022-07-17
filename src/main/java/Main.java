import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        String a="2022-12-11";
        LocalDate ld1=LocalDate.parse(a, DateTimeFormatter.ofPattern("yyy-MM-dd"));
        System.out.println(ld1);

        LocalDate localDate = ld1.plusDays(10);
        System.out.println(localDate);

        if (Period.between(ld1,localDate).getDays()>0){
            System.out.println(true);
        }
    }
}
