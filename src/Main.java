import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Stage1 stage1 = new Stage1();

        System.out.println(stage1.getPayout(2000, 40, false));
        System.out.println(stage1.getPayout(2000, 40, true));

        System.out.println("------------------------------------------------------------------------");

        Stage2 stage2 = new Stage2();

        List<Risk> risks = new ArrayList<Risk>() {{
            add(Risk.NOTHING);
        }};

        System.out.println(stage2.getPayout(2000, 40, risks));

        System.out.println("------------------------------------------------------------------------");

        Stage2 stage22 = new Stage2();

        List<Risk> risks2 = new ArrayList<Risk>() {{
            add(Risk.EXTREME_ATHLETE);
        }};

        System.out.println(stage22.getPayout(2000, 40, risks2));

        System.out.println("------------------------------------------------------------------------");

        Stage2 stage23 = new Stage2();

        List<Risk> risks3 = new ArrayList<Risk>() {{
            add(Risk.SMOKER);
            add(Risk.PROFESSIONAL_GROUP);
        }};

        System.out.println(stage23.getPayout(2000, 40, risks3));

        System.out.println("------------------------------------------------------------------------");

        Stage3 stage31 = new Stage3();

        List<Risk> risks31 = new ArrayList<Risk>() {{
            add(Risk.NOTHING);
        }};

        System.out.println(stage31.getPayout(2000, 40,
                LocalDate.of(2019, 12, 12), risks31));

        System.out.println("------------------------------------------------------------------------");

        Stage3 stage32 = new Stage3();

        List<Risk> risks32 = new ArrayList<Risk>() {{
            add(Risk.NOTHING);
        }};

        System.out.println(stage32.getPayout(2000, 40,
                LocalDate.of(2020, 12, 1), risks32));

        System.out.println("------------------------------------------------------------------------");

        Stage3 stage33 = new Stage3();

        List<Risk> risks33 = new ArrayList<Risk>() {{
            add(Risk.PROFESSIONAL_GROUP);
        }};

        System.out.println(stage33.getPayout(2000, 40,
                LocalDate.of(2022, 12, 12), risks33));
    }
}