package org.ghurtchu.protocols;

public interface RunnableFinalizable {

    void endWithTasks(
            Runnable onSuccess,
            Runnable onFailure
    );
}
