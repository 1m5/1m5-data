package io.onemfive.data.util;

import java.util.Random;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
public class RandomUtil {
    public static long nextRandomLong() {
        return new Random(System.currentTimeMillis()).nextLong();
    }
}
