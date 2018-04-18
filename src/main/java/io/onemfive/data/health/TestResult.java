package io.onemfive.data.health;

import java.io.Serializable;

/**
 * An immutable Score from the result of applying a Model to a Test.
 *
 * @author ObjectOrange
 */

public class TestResult implements Serializable {

    private int score = 0;

    public void addToScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
