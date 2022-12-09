package org.ghurtchu.Try;

interface RunnableFinalizable {

    void endWith(
            Runnable onSuccess,
            Runnable onFailure
    );
}
