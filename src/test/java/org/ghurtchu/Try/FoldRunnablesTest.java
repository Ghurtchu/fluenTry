package org.ghurtchu.Try;

import org.junit.Assert;
import org.junit.Test;

public class FoldRunnablesTest {

    @Test
    public void failAndHealWithZero() {
        int result = Try.of(() -> { throw new RuntimeException(); }).fold(obj -> 1, 0);
        Assert.assertEquals(0, result );
    }

    @Test
    public void succeedWithOne() {
        int result = Try.of(() -> {}).fold(obj -> 1, 0);
        Assert.assertEquals(1, result);
    }

}
