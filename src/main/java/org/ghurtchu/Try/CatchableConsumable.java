package org.ghurtchu.Try;

import java.util.function.Consumer;

interface CatchableConsumable {

    void ifThrowsThenRunTask(
            Consumer<? super Exception> errorConsumer,
            Class<? extends Exception>... clientExceptions
    );
}

