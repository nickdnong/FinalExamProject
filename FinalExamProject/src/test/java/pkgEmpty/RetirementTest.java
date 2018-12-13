package pkgEmpty;

import org.junit.Before;
import org.junit.Test;
import pkgCore.Retirement;

import static org.junit.Assert.assertEquals;

public class RetirementTest {
    Retirement retirement;
    final static double DELTA = 0.01;

    @Before
    public void setUp() {
        retirement = new Retirement(40,
                0.07,
                20,
                0.02,
                10000,
                2642);

    }

    @Test
    public void amountToSave() {
        double expect = 554.13;
        double toSave = retirement.AmountToSave();
        assertEquals(toSave, expect, DELTA);
    }

    @Test
    public void totalAmountSaved() {
        double expect = 1454485.55;
        double totalSaved = retirement.TotalAmountSaved();
        assertEquals(totalSaved, expect, DELTA);
    }
}