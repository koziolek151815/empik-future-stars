package com.futurestars.empik;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class EmpikController {
    static Map<Integer, Integer> map;

    static {
        map = new HashMap<>();
    }

    @PostMapping
    public ResponseEntity<?> Add(@RequestBody String numbers) {
        if (numbers.startsWith("//[")) {
            List<String> support = Arrays.asList(numbers.split("\r\n"));
            String supportString = support.get(0);
            String cutted = supportString.substring(2);

            if (!supportString.endsWith("]")) {
                throw new RuntimeException("Wrong format");
            }
            String arr[] = cutted.substring(1, cutted.length() - 1).split("\\]\\[");

            String delimiter = "";
            String delConcat = "";

            for (String s : arr) {
                delimiter += "[" + s + "]+|";
                delConcat += s;

            }
            String stringToSplit = numbers.substring(4 + delConcat.length() + 2 * arr.length);
            if (numbers.endsWith("\r\n")) {
                throw new RuntimeException("Wrong delimiter");
            }
            List<String> splittedString = Arrays.asList(stringToSplit.split(delimiter + "\r\n"));
            return returnSumWithMessage(splittedString);

        } else {
            if (numbers.length() == 0) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(0);
            }
            List<String> splittedString = Arrays.asList(numbers.split(";|\r\n"));
            return returnSumWithMessage(splittedString);
        }
    }

    public ResponseEntity<?> returnSumWithMessage(List<String> splittedString) {
        List<String> negatives = new ArrayList<>();
        int sum = 0;
        for (String number : splittedString) {
            int parsed = Integer.parseInt(number);
            if (parsed > 1000) {
                continue;
            }
            if (parsed < 0) {
                negatives.add(number);
            }
            sum += parsed;
        }
        if (negatives.isEmpty()) {

            map.putIfAbsent(sum, -1);
            map.put(sum, map.get(sum) + 1);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(sum + " was returned " + map.get(sum) + " times so far");

        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("negatives not allowed: " + negatives);
        }
    }
}
