package org.ghurtchu.Try;

import java.util.function.Function;

interface Foldable<T> {

    <V> V fold(
            Function<? super T, ? extends V> successMapper,
            V defaultValue
    );
}
