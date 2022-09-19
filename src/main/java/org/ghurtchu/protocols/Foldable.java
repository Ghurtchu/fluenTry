package org.ghurtchu.protocols;

import java.util.function.Function;

public interface Foldable<T> {

    <V> V fold(
            Function<? super T, ? extends V> successMapper,
            V defaultValue
    );
}
