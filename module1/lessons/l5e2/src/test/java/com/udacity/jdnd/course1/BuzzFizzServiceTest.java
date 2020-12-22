package com.udacity.jdnd.course1;

import com.udacity.jdnd.course1.service.FizzBuzzService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class BuzzFizzServiceTest {

	@Test
	void testFizzBuzz(){
		FizzBuzzService fbs = new FizzBuzzService();

		// check non-divisible numbers return themselves
		assertEquals(3, fbs.buzzFizz("Fizz", 1));
		assertEquals(9, fbs.buzzFizz("Fizz", 3));
		assertEquals(12, fbs.buzzFizz("Fizz", 4));
		assertEquals(-3, fbs.buzzFizz("Fizz", -1));
		assertEquals(-9, fbs.buzzFizz("Fizz", -3));
		assertEquals(-12, fbs.buzzFizz("Fizz", -4));
		assertEquals(0, fbs.buzzFizz("Fizz", 0));

		assertEquals(5, fbs.buzzFizz("Buzz", 1));
		assertEquals(15, fbs.buzzFizz("Buzz", 3));
		assertEquals(20, fbs.buzzFizz("Buzz", 4));
		assertEquals(-5, fbs.buzzFizz("Buzz", -1));
		assertEquals(-15, fbs.buzzFizz("Buzz", -3));
		assertEquals(-20, fbs.buzzFizz("Buzz", -4));
		assertEquals(0, fbs.buzzFizz("Buzz", 0));

		assertEquals(15, fbs.buzzFizz("FizzBuzz", 1));
		assertEquals(45, fbs.buzzFizz("FizzBuzz", 3));
		assertEquals(60, fbs.buzzFizz("FizzBuzz", 4));
		assertEquals(-15, fbs.buzzFizz("FizzBuzz", -1));
		assertEquals(-45, fbs.buzzFizz("FizzBuzz", -3));
		assertEquals(-60, fbs.buzzFizz("FizzBuzz", -4));
		assertEquals(0, fbs.buzzFizz("FizzBuzz", 0));

		assertEquals(1, fbs.buzzFizz("NotFizzBuzz", 1));
		assertEquals(3, fbs.buzzFizz("BuzzFizz", 3));
	}
}
