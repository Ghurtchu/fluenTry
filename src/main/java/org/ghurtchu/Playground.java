package org.ghurtchu;

import org.ghurtchu.impl.Try;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Function;

public class Playground {

    public static void main(String[] args) {

        Try<Double> tryDouble = Try.of(() -> Math.random() * 2 * 5)
                .flatMap(n -> Try.of(() -> n + 1))
                .flatMap(n -> Try.of(() -> 55 / 0)) // This is where the Success turns into Failure
                .map(Math::sqrt);


        tryDouble.fold(Function.identity(), 0.0); // return success if success, else 0.0

    }




    public static String getAndThenDouble(List<Integer> list, int index) {
        return Try.of(list, index, List::get)
                .map(n -> n * 2)
                .fold(Objects::toString, "0");
    }

}
