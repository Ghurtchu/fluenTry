package org.ghurtchu.Try;

import java.util.function.Function;

interface FlatMappable<T> {

    <V> Try<V> flatMap(Function<? super T, ? extends Try<V>> flatMapper);
}
