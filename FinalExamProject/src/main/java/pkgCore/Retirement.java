package pkgCore;

import static java.lang.Math.pow;

public class Retirement {

    private int iYearsToWork;
    private double dAnnualReturnWorking;
    private int iYearsRetired;
    private double dAnnualReturnRetired;
    private double dRequiredIncome;
    private double dMonthlySSI;
    private double wokingMonthRate;
    private double retiredMonthRate;
    private int monthstoWork;
    private int monthsbeforeDead;
    private double differenceRetired;

    //TODO: Build the contructor, getters and setters for the attributes above.
    public Retirement(int iYearsToWork, double dAnnualReturnWorking, int iYearsRetired, double dAnnualReturnRetired, double dRequiredIncome, double dMonthlySSI) {
        this.iYearsToWork = iYearsToWork;
        this.dAnnualReturnWorking = dAnnualReturnWorking;
        this.iYearsRetired = iYearsRetired;
        this.dAnnualReturnRetired = dAnnualReturnRetired;
        this.dRequiredIncome = dRequiredIncome;
        this.dMonthlySSI = dMonthlySSI;

        wokingMonthRate = dAnnualReturnWorking / 12;
        retiredMonthRate = dAnnualReturnRetired / 12;

        monthsbeforeDead = iYearsRetired * 12;
        monthstoWork = iYearsToWork * 12;

        differenceRetired = dRequiredIncome - dMonthlySSI;
    }


    public double AmountToSave() {
        //TODO: Determine the amount to save each month based on TotalAmountSaved, YearsToWork
        //		and Annual return while working
        // pmt = fv (r/(1+r)^n -1)
        return TotalAmountSaved() * (wokingMonthRate / (pow(1 + wokingMonthRate, monthstoWork) - 1));
    }

    public double TotalAmountSaved() {
        //	TODO: Determine amount to be saved based on Monthly SSI, Required Income, Annual return during retirement
        //		and number of years retired.
        //  pv =  pmt (1- (1+i)^(-n))/i
        return differenceRetired * (1 - pow((1 + retiredMonthRate), -monthsbeforeDead)) / retiredMonthRate;
    }

}
