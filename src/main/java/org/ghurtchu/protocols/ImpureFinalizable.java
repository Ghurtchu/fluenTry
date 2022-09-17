package org.ghurtchu.protocols;

public interface ImpureFinalizable {

    void finalizeWith(Runnable onSuccess, Runnable onFailure);

}
