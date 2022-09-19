package org.ghurtchu.protocols;

import org.ghurtchu.impl.Try;

import java.util.function.Function;

public interface FlatMappable<T> {

    <V> Try<V> flatMap(Function<? super T, ? extends Try<V>> flatMapper);
}
