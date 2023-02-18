import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;


@RestController
@RequestMapping(path = "/api")
public class Stage1 {
    private static final BigDecimal HANDLING_COSTS_IN_PERCENT = new BigDecimal("2");
    private static final BigDecimal SMOKING_SURCHARGE_IN_PERCENT = new BigDecimal("3");

    @RequestMapping(path = "/payout", method = RequestMethod.GET)
    public int getPayout(
            @RequestParam int monthlyPayment,
            @RequestParam int lifetimeInYears,
            @RequestParam boolean isSmoker
    ) {
        BigDecimal handlingCosts = HANDLING_COSTS_IN_PERCENT.divide(new BigDecimal("100"));
        BigDecimal smokerSurcharge = BigDecimal.ZERO;

        if (isSmoker) {
            smokerSurcharge = SMOKING_SURCHARGE_IN_PERCENT.divide(new BigDecimal("100"));
        }

        BigDecimal payout = BigDecimal.valueOf(monthlyPayment)
                .multiply(BigDecimal.ONE.subtract(smokerSurcharge).subtract(handlingCosts))
                .multiply(BigDecimal.valueOf(lifetimeInYears))
                .multiply(BigDecimal.valueOf(12));

        return payout.intValue();
    }
}
