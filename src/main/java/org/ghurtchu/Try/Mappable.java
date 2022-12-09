package org.ghurtchu.Try;

import java.util.function.Function;

interface Mappable<T> {

    <V> Try<V> map(Function<? super T, ? extends V> mapper);
}