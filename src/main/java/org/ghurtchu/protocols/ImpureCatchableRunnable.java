package org.ghurtchu.protocols;

import java.util.function.Consumer;

public interface ImpureCatchableRunnable {

    void ifThrowsCatchAndThenRun(Consumer<? super Exception> consumer, Class<? extends Exception>... exceptions);

}
