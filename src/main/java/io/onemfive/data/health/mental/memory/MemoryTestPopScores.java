package io.onemfive.data.health.mental.memory;

import java.io.Serializable;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
public class MemoryTestPopScores implements Serializable {

    private double borderlineImpairedScore = 0.0D;
    private double impairedScore = 0.0D;
    private double grosslyImpairedScore = 0.0D;

    private long population = 0L;

    public MemoryTestPopScores(double borderlineImpairedScore, double impairedScore, double grosslyImpairedScore) {
        this.borderlineImpairedScore = borderlineImpairedScore;
        this.impairedScore = impairedScore;
        this.grosslyImpairedScore = grosslyImpairedScore;
    }

    public double getBorderlineImpairedScore() {
        return borderlineImpairedScore;
    }

    public double getImpairedScore() {
        return impairedScore;
    }

    public double getGrosslyImpairedScore() {
        return grosslyImpairedScore;
    }
}
