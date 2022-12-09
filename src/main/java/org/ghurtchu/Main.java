package org.ghurtchu;

import org.ghurtchu.Try.Try;

class Main {

    public static void main(String[] args) {

        Try.of(() -> 5 / 0).ifThrowsThenRunTask(System.out::println, ArithmeticException.class);

    }
}