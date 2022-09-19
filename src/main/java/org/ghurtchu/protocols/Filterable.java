package org.ghurtchu.protocols;

import org.ghurtchu.impl.Try;

import java.util.function.Predicate;

public interface Filterable<T> {

    Try<T> filter(Predicate<? super T> predicate);
}
