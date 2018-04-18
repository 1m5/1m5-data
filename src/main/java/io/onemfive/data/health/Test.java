package io.onemfive.data.health;

import java.io.Serializable;
import java.util.List;

/**
 * TODO: Add Description
 *
 * @author ObjectOrange
 */
public interface Test extends Serializable {
    List<Integer> cardsUsed();
    TestResult getTestResult();
}
