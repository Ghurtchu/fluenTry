package org.ghurtchu.protocols;

import java.util.Optional;

public interface ToOptionalConvertable<T> {

    Optional<T> toOption();
}
