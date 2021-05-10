package com.futurestars.empik;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class EmpikController {

    @PostMapping
    public int Add(@RequestBody String numbers) {
        if (numbers.length() == 0) {
            return 0;
        }

        List<String> splittedString = Arrays.asList(numbers.split(","));
        int sum = 0;
        for (String number : splittedString) {
            int parsed = Integer.parseInt(number);
            sum += parsed;
        }
        return sum;
    }

}
