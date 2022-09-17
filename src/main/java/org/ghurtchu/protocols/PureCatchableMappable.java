package org.ghurtchu.protocols;

import java.util.function.Function;

public interface PureCatchableMappable<T> {

    <V> V ifThrowsGetDefaultOrElseMap(Function<? super T, ? extends V> successMapper, V defaultValue, Class<? extends Exception>... exceptions);

}
