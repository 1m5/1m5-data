package io.onemfive.data.health.mental.memory;

import java.io.Serializable;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
public class MemoryTestPopScores implements Serializable {

    private int successes = 0; // Clicked when should have
    private double successWeight = 0D; // TODO: Weights need to come from population

    private int misses = 0; // Should have clicked but did not
    private double missWeight = 0D; // TODO: Weights need to come from population

    private int negative = 0; // Clickable but should not have clicked
    private double negativeWeight = 0D; // TODO: Weights need to come from population

    private int inappropriate = 0; // Not clickable but clicked
    private double inappropriateWeight = 0D; // TODO: Weights need to come from population

    private long avgResponseTimeOverallMs = 0L;
    private long minReponseTimeOverallMs = 0L;
    private long maxResponseTimeOverallMs = 0L;

    private long avgResponseTimeSuccessMs = 0L;
    private long minResponseTimeSuccessMs = 0L;
    private long maxResponseTimeSuccessMs = 0L;

    private long missTimeMs = 0L;

    private long avgResponseTimeNegativeMs = 0L;
    private long minResponseTimeNegativeMs = 0L;
    private long maxResponseTimeNegativeMs = 0L;

    private long avgResponseTimeInappropriateMs = 0L;
    private long minResponseTimeInappropriateMs = 0L;
    private long maxResponseTimeInappropriateMs = 0L;

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

    public void buildScore() {

    }
}
