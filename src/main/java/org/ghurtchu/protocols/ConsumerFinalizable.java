package org.ghurtchu.protocols;

import java.util.function.Consumer;

public interface ConsumerFinalizable<T> {

    void endWith(
            Consumer<T> onSuccess,
            Consumer<Exception> onFailure
    );
}
