package org.ghurtchu.protocols;

import java.util.function.Consumer;

public interface CatchableConsumable {

    void ifThrowsThenRunTask(
            Consumer<? super Exception> errorConsumer,
            Class<? extends Exception>... clientExceptions
    );
}

