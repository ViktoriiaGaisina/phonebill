package org.example;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class TelephoneBillCalculatorImpl implements TelephoneBillCalculator {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    @Override
    public BigDecimal calculate(String phoneLog) {
        HashMap<String, Integer> callCounts = new HashMap<>();
        BigDecimal totalBill = BigDecimal.ZERO;
        String mostCallandNumber = null;
        int maxCallCount = 0;
        String[] calls = phoneLog.split("\n");
        for(String call:calls){
            String[] callDetails = call.split(",");
            String phoneNumber = callDetails[0];
            LocalDateTime startTime = LocalDateTime.parse(callDetails[1], DATE_TIME_FORMATTER);
            LocalDateTime endTime = LocalDateTime.parse(callDetails[2], DATE_TIME_FORMATTER);
            int callCount = callCounts.getOrDefault(phoneNumber, 0) + 1;
            callCounts.put(phoneNumber, callCount);
            if(callCount>maxCallCount) {
                mostCallandNumber = phoneNumber;
                maxCallCount = callCount;
            }
            BigDecimal callBill = calculateCallBill(startTime, endTime);
            totalBill = totalBill.add(callBill);
            if(mostCallandNumber!=null) {
                callCounts.remove(mostCallandNumber);
            }
            for(Map.Entry<String, Integer> entry: callCounts.entrySet()) {
                int callsCount = entry.getValue();
                BigDecimal callsBill = calculateAdditionalCallBill(callsCount);
                totalBill = totalBill.add(callsBill);
            }
        }
        return totalBill;
    }

    private BigDecimal calculateCallBill(LocalDateTime startTime, LocalDateTime endTime) {
        Duration duration = Duration.between(startTime, endTime);
        Long minutes = duration.toMinutes();
        BigDecimal rate;
        if (startTime.getHour() >= 8 && startTime.getHour() < 16) {
            rate = BigDecimal.valueOf(1.0);
        } else {
            rate = BigDecimal.valueOf(0.5);
        }
        BigDecimal billAmount = rate.multiply(BigDecimal.valueOf(minutes));
        return billAmount;
    }

    private BigDecimal calculateAdditionalCallBill(int callCount) {
        BigDecimal aditionalRate = BigDecimal.valueOf(0.2);
        BigDecimal aditionalMinuts = BigDecimal.valueOf(callCount - 5);
        BigDecimal aditionalBillAmount = aditionalRate.multiply(aditionalMinuts);
        return aditionalBillAmount;
    }
}
