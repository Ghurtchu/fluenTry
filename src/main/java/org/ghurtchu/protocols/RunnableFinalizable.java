package org.ghurtchu.protocols;

public interface RunnableFinalizable {

    void endWith(
            Runnable onSuccess,
            Runnable onFailure
    );
}
