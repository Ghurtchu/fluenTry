package org.ghurtchu.Try;

import java.util.function.Predicate;

interface Filterable<T> {

    Try<T> filter(Predicate<? super T> predicate);
}
