package org.ghurtchu.protocols;

import org.ghurtchu.impl.Try;

import java.util.function.Function;

public interface Mappable<T> {

    <V> Try<V> map(Function<? super T, ? extends V> mapper);
}