package org.ghurtchu.Try;

import java.util.function.Function;

interface PureCatchableMappable<T> {
    
    <V> V ifThrowsThenGetDefaultOrElseMap(
            Function<? super T, ? extends V> successMapper,
            V defaultValue,
            Class<? extends Exception>... clientExceptions
    );

}
