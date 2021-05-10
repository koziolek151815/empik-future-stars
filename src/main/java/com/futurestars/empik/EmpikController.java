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

        if (splittedString.size() >= 3) {
            throw new RuntimeException("Wrong number of parameters");
        }
        if (splittedString.size() == 1) {
            return Integer.parseInt(splittedString.get(0));
        } else {
            int a = Integer.parseInt(splittedString.get(0));
            int b = Integer.parseInt(splittedString.get(1));

            return a + b;
        }
    }
}
