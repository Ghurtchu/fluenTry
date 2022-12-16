package org.ghurtchu;

import org.ghurtchu.Try.Try;

import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Main {

    public static void main(String[] args) {

        List<Integer> data = IntStream.iterate(1, i -> i + 1).limit(10).boxed().collect(Collectors.toList());
        // try getting out element at index 1000 and set up consumers finalizers for success and failure
        Try.of(() -> List.of(1, 2).get(1000)).endWith(n -> System.out.println("got " + n), err -> System.out.println("failed due to " + err.getMessage()));

    }
}
