package org.ghurtchu.Try;

import java.util.function.Consumer;

interface ConsumerFinalizable<T> {

    void endWith(
            Consumer<T> successConsumer,
            Consumer<Exception> failureConsumer
    );
}
