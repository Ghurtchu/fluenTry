package org.ghurtchu.impl;

import org.junit.Assert;
import org.junit.Test;

public class FoldRunnablesTest {

    @Test
    public void failAndHealWithZero() {
        int result = Try.run(() -> { throw new RuntimeException(); }).fold(obj -> 1, 0);
        Assert.assertEquals(0, result );
    }

    @Test
    public void succeedWithOne() {
        int result = Try.run(() -> {}).fold(obj -> 1, 0);
        Assert.assertEquals(1, result);
    }

}
