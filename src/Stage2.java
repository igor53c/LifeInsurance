import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public class Stage2 {
    private static final BigDecimal HANDLING_COSTS_IN_PERCENT = new BigDecimal(2);
    private static final BigDecimal NO_RISK = new BigDecimal(0);
    private static final BigDecimal SMOKING_SURCHARGE_IN_PERCENT = new BigDecimal(3);
    private static final BigDecimal RISKY_PROFESSIONAL_GROUP_SURCHARGE_IN_PERCENT = new BigDecimal(5);
    private static final BigDecimal EXTREME_ATHLETE_SURCHARGE_IN_PERCENT = new BigDecimal(8);

    @RequestMapping(path = "/payout", method = RequestMethod.GET)
    public int getPayout(
            @RequestParam int monthlyPayment,
            @RequestParam int lifetimeInYears,
            @RequestParam List<Risk> risks
    ) {
        int totalMonths = lifetimeInYears * 12;
        BigDecimal totalPayout = new BigDecimal(monthlyPayment).multiply(new BigDecimal(totalMonths));

        BigDecimal handlingCosts = totalPayout.multiply(HANDLING_COSTS_IN_PERCENT.divide(new BigDecimal(100)));

        BigDecimal surcharge = NO_RISK;

        for (Risk risk : risks) {
            switch (risk) {
                case SMOKER -> surcharge = surcharge.add(SMOKING_SURCHARGE_IN_PERCENT);
                case EXTREME_ATHLETE -> surcharge = surcharge.add(EXTREME_ATHLETE_SURCHARGE_IN_PERCENT);
                case PROFESSIONAL_GROUP -> surcharge = surcharge.add(RISKY_PROFESSIONAL_GROUP_SURCHARGE_IN_PERCENT);
                default -> {
                }
            }
        }

        BigDecimal riskSurcharge = totalPayout.multiply(surcharge.divide(new BigDecimal(100)));
        totalPayout = totalPayout.subtract(riskSurcharge).subtract(handlingCosts);

        return totalPayout.intValue();
    }
}