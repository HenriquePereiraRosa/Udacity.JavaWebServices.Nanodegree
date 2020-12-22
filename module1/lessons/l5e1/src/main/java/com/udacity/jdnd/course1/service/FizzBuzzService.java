package com.udacity.jdnd.course1.service;

public class FizzBuzzService {

    /**
     * If number is divisible by 3, return "Fizz". If divisible by 5,
     * return "Buzz", and if divisible by both, return "FizzBuzz". Otherwise,
     * return the number itself.
     *
     * @Throws IllegalArgumentException for values < 1
     */
    public String fizzBuzz(int number) {
        Boolean dividedByThree = false;
        String fizzBuzz;

        if(number <= 0)
            throw new IllegalArgumentException();

        if((number % 3) == 0)
            dividedByThree = true;


        if((number % 5) == 0)
            if(dividedByThree) return "FizzBuzz";
            else return "Buzz";

        if(dividedByThree) return "Fizz";

        return Integer.toString(number);
    }
}
