import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public class Stage3 {
    private static final BigDecimal NO_RISK = new BigDecimal(0);
    @RequestMapping(path = "/payout", method = RequestMethod.GET)
    public int getPayout(
            @RequestParam int monthlyPayment,
            @RequestParam int lifetimeInYears,
            @RequestParam LocalDate policyStartDate,
            @RequestParam List<Risk> risks
    ) {
        Costs costs = getCostsByYear(policyStartDate);

        int totalMonths = lifetimeInYears * 12;
        BigDecimal basePayout = new BigDecimal(monthlyPayment * totalMonths);

        BigDecimal payout = basePayout
                .subtract(getSmokingSurcharge(costs, risks).multiply(basePayout))
                .subtract(getProfessionalGroupSurcharge(costs, risks).multiply(basePayout))
                .subtract(getAthleteSurcharge(costs, risks).multiply(basePayout))
                .subtract(getHandlingCosts(costs).multiply(basePayout));

        return payout.intValue();
    }

    private Costs getCostsByYear(LocalDate date) {
        Map<Integer, Costs> costsByYear = new HashMap<>();
        Costs currentCosts = new Costs();
        currentCosts.handlingCostsInPercent = new BigDecimal("2");
        currentCosts.smokingSurchargeInPercent = new BigDecimal("3");
        currentCosts.riskyProfessionalGroupSurchargeInPercent = new BigDecimal("5");
        currentCosts.extremeAthleteSurchargeInPercent = new BigDecimal("8");

        costsByYear.put(2019, currentCosts);

        if(date.isAfter(LocalDate.of(2020, 5, 31))) {
            currentCosts.handlingCostsInPercent = costsByYear.get(2019).handlingCostsInPercent
                    .add(new BigDecimal("0.5"));
            currentCosts.smokingSurchargeInPercent = costsByYear.get(2019).smokingSurchargeInPercent
                    .add(new BigDecimal("1"));
            currentCosts.extremeAthleteSurchargeInPercent = costsByYear.get(2019)
                    .extremeAthleteSurchargeInPercent.subtract(new BigDecimal("0.5"));

            costsByYear.put(2020, currentCosts);
        } else return currentCosts;

        if(date.isAfter(LocalDate.of(2022, 5, 31))) {
            currentCosts.handlingCostsInPercent = costsByYear.get(2020).handlingCostsInPercent
                    .add(new BigDecimal("0.5"));
            currentCosts.riskyProfessionalGroupSurchargeInPercent = costsByYear.get(2020)
                    .riskyProfessionalGroupSurchargeInPercent.add(new BigDecimal("1.5"));

            costsByYear.put(2022, currentCosts);
        } else return currentCosts;

        return currentCosts;
    }

    private BigDecimal getSmokingSurcharge(Costs costs, List<Risk> risks) {

        if (risks.contains(Risk.SMOKER)) {
            return costs.smokingSurchargeInPercent.divide(new BigDecimal("100"));
        } else {
            return NO_RISK;
        }
    }

    private BigDecimal getProfessionalGroupSurcharge(Costs costs, List<Risk> risks) {

        if (risks.contains(Risk.PROFESSIONAL_GROUP)) {
            return costs.riskyProfessionalGroupSurchargeInPercent.divide(new BigDecimal("100"));
        } else {
            return NO_RISK;
        }
    }

    private BigDecimal getAthleteSurcharge(Costs costs, List<Risk> risks) {

        if (risks.contains(Risk.EXTREME_ATHLETE)) {
            return costs.extremeAthleteSurchargeInPercent.divide(new BigDecimal("100"));
        } else {
            return NO_RISK;
        }
    }

    private BigDecimal getHandlingCosts(Costs costs) {

        return costs.handlingCostsInPercent.divide(new BigDecimal("100"));
    }
}