package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TelephoneBillCalculatorImplTest {

    @Test
    void calculate() {
        String phoneLog = "420774577453,13-01-2020 18:10:15,13-01-2020 18:12:57," +
                          "420776562353,18-01-2020 08:59:20,18-01-2020 09:10:00";
        TelephoneBillCalculatorImpl telephoneBillCalculator = new TelephoneBillCalculatorImpl();
        BigDecimal expected = new BigDecimal("1.0");
        BigDecimal actual = telephoneBillCalculator.calculate(phoneLog);
        Assertions.assertEquals(expected, actual);
    }
}