package com.futurestars.empik;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class EmpikController {
    @PostMapping
    public ResponseEntity<?> Add(@RequestBody String numbers) {
        if (numbers.startsWith("//[")){
            List<String> support = Arrays.asList(numbers.split("\r\n"));
            String supportString = support.get(0);
            if(!supportString.endsWith("]")){
                throw new RuntimeException("Wrong format");
            }
            String delimiter = supportString.substring(3,supportString.length()-1);
            String stringToSplit = numbers.substring(6+delimiter.length());
            if (numbers.endsWith("\r\n")){
                throw new RuntimeException("Wrong delimiter");
            }
            List<String> splittedString = Arrays.asList(stringToSplit.split( "["+delimiter+ "]+"+"|\r\n"));
            return returnSumOrMessage(splittedString);

        } else {
            if (numbers.length() == 0) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(0);
            }
            List<String> splittedString = Arrays.asList(numbers.split(";|\r\n"));
            return returnSumOrMessage(splittedString);
        }
    }

    public ResponseEntity<?> returnSumOrMessage(List<String> splittedString) {
        List<String> negatives = new ArrayList<>();
        int sum = 0;
        for (String number : splittedString) {
            int parsed = Integer.parseInt(number);
            if (parsed >1000){continue;}
            if (parsed < 0) {
                negatives.add(number);
            }
            sum += parsed;
        }
        if (negatives.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(sum);

        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("negatives not allowed: " + negatives);
        }
    }
}
