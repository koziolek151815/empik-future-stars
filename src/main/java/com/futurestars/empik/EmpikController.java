package com.futurestars.empik;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class EmpikController {
    @PostMapping
    public int Add(@RequestBody String numbers) {
        if (numbers.startsWith("//")) {
            String delimiter = numbers.substring(2, 3);
            String stringToSplit = numbers.substring(5);
            if (numbers.endsWith("\r\n")) {
                throw new RuntimeException("Wrong delimiter");
            }
            List<String> splittedString = Arrays.asList(stringToSplit.split(delimiter + "|\r\n"));
            return sumNumbersInList(splittedString);

        } else {
            if (numbers.length() == 0) {
                return 0;
            }
            List<String> splittedString = Arrays.asList(numbers.split(";|\r\n"));
            return sumNumbersInList(splittedString);
        }
    }

    public int sumNumbersInList(List<String> splittedString) {
        int sum = 0;
        for (String number : splittedString) {
            int parsed = Integer.parseInt(number);
            sum += parsed;
        }
        return sum;
    }

}
