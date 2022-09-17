package org.ghurtchu.protocols;

import java.util.function.Consumer;

public interface ImpureCatchableMappable<T> {

    T ifThrowsThenRunTaskOrElseGet(
            Consumer<? super Exception> errorConsumer,
            Class<? extends Exception>... clientExceptions
    );
}
