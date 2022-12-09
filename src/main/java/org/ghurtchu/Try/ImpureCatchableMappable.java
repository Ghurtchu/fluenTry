package org.ghurtchu.Try;

import java.util.function.Consumer;

interface ImpureCatchableMappable<T> {

    T ifThrowsThenRunTaskOrElseGet(
            Consumer<? super Exception> errorConsumer,
            Class<? extends Exception>... clientExceptions
    );
}
