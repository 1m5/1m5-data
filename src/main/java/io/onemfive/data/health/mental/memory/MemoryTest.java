package io.onemfive.data.health.mental.memory;

import org.dizitart.no2.IndexType;
import org.dizitart.no2.objects.Id;
import org.dizitart.no2.objects.Index;
import org.dizitart.no2.objects.Indices;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
@Indices({
        @Index(value = "did", type = IndexType.NonUnique),
        @Index(value = "name", type = IndexType.NonUnique),
        @Index(value = "difficulty", type = IndexType.NonUnique)
})
public class MemoryTest implements Serializable {

    public enum Impairment {Unimpaired, Borderline, Impaired, Gross}

    @Id
    private Long id;
    private Long did;
    private String name;
    private Date timeStarted;
    private Date timeEnded;
    private Impairment impairment = Impairment.Unimpaired;

    private int difficulty = 1;

    private int successes = 0; // Clicked when should have
    private double successWeight = 1.0D;

    private int misses = 0; // Should have clicked but did not
    private double missWeight = -2.5D;

    private int negative = 0; // Clickable but should not have clicked
    private double negativeWeight = -5.0D;

    private int inappropriate = 0; // Not clickable but clicked
    private double inappropriateWeight = -10.0D;

    private long avgResponseTimeMs = 0L;
    private long minReponseTimeMs = 0L;
    private long maxResponseTimeMs = 0L;

    private List<Integer> cardsUsed = new ArrayList<>();

    /**
     * Blood Alcohol Content (BAC) Impairment Model
     *
     * BAC is used for training the model due to the ability to easily measure it.
     *
     * Widely expected levels of impairment based solely on BAC:
     * Male Female BAC% Effects
     *   1    1    .020 Light to moderate drinkers begin to feel some effects
     *             .040 Most people begin to feel relaxed
     *  2-3  1-2   .050 Though, judgment and restraint more lax. Steering errors increase. Vision impaired.
     *             .060 Judgment is somewhat impaired, people are less able to make rational decisions about their capabilities (for example, driving)
     *  3-4  2-4   .080 There is a definite impairment of muscle coordination and driving skills; this is legal level for intoxication in some states
     *  3-5  2-5   .100 There is clear deterioration of reaction time and control; this is legally drunk in most states
     *             .120 Vomiting usually occurs. Unless this level is reached slowly or a person has developed a tolerance to alcohol
     *  4-7  3-7   .150 Balance and movement are impaired. This blood-alcohol level means the equivalent of 1/2 pint of whiskey is circulating in the blood stream
     *             .300 Many people lose consciousness
     *             .400 Most people lose consciousness; some die
     *             .450 Breathing stops; this is a fatal dose for most people
     *
     */
    private double bloodAlcoholContent = 0.0D;
    private double score = 0.0D;

    private MemortyTestPopScores popScores;

    public MemoryTest() {}

    public static MemoryTest newInstance(String name, Long did) {
        return new MemoryTest(new Random(984732498374923L).nextLong(), did, name);
    }

    private MemoryTest(Long id, Long did, String name) {
        this.id = id;
        this.did = did;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDid() {
        return did;
    }

    public void setDid(Long did) {
        this.did = did;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTimeStarted() {
        return timeStarted;
    }

    public void setTimeStarted(Date timeStarted) {
        this.timeStarted = timeStarted;
    }

    public Date getTimeEnded() {
        return timeEnded;
    }

    public void setTimeEnded(Date timeEnded) {
        this.timeEnded = timeEnded;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getSuccesses() {
        return successes;
    }

    public void addSuccess() {
        successes += 1;
        score += successWeight * difficulty;
        determineImpairment();
    }

    public double getSuccessWeight() {
        return successWeight;
    }

    public void setSuccessWeight(double successWeight) {
        this.successWeight = successWeight;
    }

    public int getMisses() {
        return misses;
    }

    public void addMiss() {
        misses += 1;
        score += missWeight * difficulty;
        determineImpairment();
    }

    public double getMissWeight() {
        return missWeight;
    }

    public void setMissWeight(double missWeight) {
        this.missWeight = missWeight;
    }

    public int getNegative() {
        return negative;
    }

    public void addNegative() {
        negative += 1;
        score += negativeWeight * difficulty;
        determineImpairment();
    }

    public double getNegativeWeight() {
        return negativeWeight;
    }

    public void setNegativeWeight(double negativeWeight) {
        this.negativeWeight = negativeWeight;
    }

    public int getInappropriate() {
        return inappropriate;
    }

    public void addInappropriate() {
        inappropriate += 1;
        score += inappropriateWeight * difficulty;
        determineImpairment();
    }

    public double getInappropriateWeight() {
        return inappropriateWeight;
    }

    public void setInappropriateWeight(double inappropriateWeight) {
        this.inappropriateWeight = inappropriateWeight;
    }

    public long getAvgResponseTimeMs() {
        return avgResponseTimeMs;
    }

    public void setAvgResponseTimeMs(long avgResponseTimeMs) {
        this.avgResponseTimeMs = avgResponseTimeMs;
    }

    public long getMinReponseTimeMs() {
        return minReponseTimeMs;
    }

    public void setMinReponseTimeMs(long minReponseTimeMs) {
        this.minReponseTimeMs = minReponseTimeMs;
    }

    public long getMaxResponseTimeMs() {
        return maxResponseTimeMs;
    }

    public void setMaxResponseTimeMs(long maxResponseTimeMs) {
        this.maxResponseTimeMs = maxResponseTimeMs;
    }

    public List<Integer> cardsUsed() {
        return cardsUsed;
    }

    public void setCardsUsed(List<Integer> cardsUsed) {
        this.cardsUsed = cardsUsed;
    }

    public double getBloodAlcoholContent() {
        return bloodAlcoholContent;
    }

    public void setBloodAlcoholContent(double bloodAlcoholContent) {
        this.bloodAlcoholContent = bloodAlcoholContent;
    }

    public double getScore() {
        return score;
    }

    public MemortyTestPopScores getPopScores() {
        return popScores;
    }

    public void setPopScores(MemortyTestPopScores popScores) {
        this.popScores = popScores;
    }

    public Impairment getImpairment() {
        return impairment;
    }

    private void determineImpairment() {
        if((bloodAlcoholContent >= 0.050 && bloodAlcoholContent < 0.080) || (popScores.getBorderlineImpairedScore() > 0 && score >= popScores.getBorderlineImpairedScore() && score < popScores.getImpairedScore())) impairment = Impairment.Borderline;
        if((bloodAlcoholContent >= 0.080 && bloodAlcoholContent <= 0.1) || (popScores.getImpairedScore() > 0 && score >= popScores.getImpairedScore() && score < popScores.getGrosslyImpairedScore())) impairment = Impairment.Impaired;
        if(bloodAlcoholContent > 0.1 || (popScores.getGrosslyImpairedScore() > 0 && score >= popScores.getGrosslyImpairedScore())) impairment = Impairment.Gross;
        else impairment = Impairment.Unimpaired;
    }

}
