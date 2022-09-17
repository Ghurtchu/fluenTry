package org.ghurtchu.protocols;

import java.util.function.Function;

public interface PureCatchableMappable<T> {
    
    <V> V ifThrowsThenGetDefaultOrElseMap(
            Function<? super T, ? extends V> successMapper,
            V defaultValue,
            Class<? extends Exception>... clientExceptions
    );

}
