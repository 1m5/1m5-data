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

//    public enum Impairment {Unimpaired, Borderline, Impaired, Gross}

    @Id
    private Long id;
    private Long did;
    private String name;
    private Date timeStarted;
    private Date timeEnded;
//    private Impairment impairment = Impairment.Unimpaired;

    private int difficulty = 1;

    protected int successes = 0; // Clicked when should have
    protected List<Long> successResponseTimes = new ArrayList<>();
    protected long cumulativeSuccessResponseTimeMs = 0L;
//    protected double successWeight = 0D; // TODO: Weights need to come from population

    protected int misses = 0; // Should have clicked but did not
    protected long cumulativeMissesResponseTimeMs = 0L;
    protected List<Long> missesResponseTimes = new ArrayList<>();
//    protected double missWeight = 0D; // TODO: Weights need to come from population

    protected int negative = 0; // Clickable but should not have clicked
    protected long cumulativeNegativeResponseTimeMs = 0L;
    protected List<Long> negativeResponseTimes = new ArrayList<>();
//    protected double negativeWeight = 0D; // TODO: Weights need to come from population

    protected int inappropriate = 0; // Not clickable but clicked
    protected long cumulativeInappropriateResponseTimeMs = 0L;
    protected List<Long> inappropriateResponseTimes = new ArrayList<>();
//    protected double inappropriateWeight = 0D; // TODO: Weights need to come from population

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

    private MemoryTestPopScores popScores = null;

    public MemoryTest() {}

    public static MemoryTest newInstance(String name, Long did) {
        return new MemoryTest(new Random(984732498374923L).nextLong(), did, name);
    }

    public static MemoryTest newInstance(String name, Long did, MemoryTestPopScores popScores) {
        return new MemoryTest(new Random(984732498374923L).nextLong(), did, name, popScores);
    }

    private MemoryTest(Long id, Long did, String name) {
        this.id = id;
        this.did = did;
        this.name = name;
    }

    private MemoryTest(Long id, Long did, String name, MemoryTestPopScores popScores) {
        this.id = id;
        this.did = did;
        this.name = name;
        this.popScores = popScores;
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

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getSuccesses() {
        return successes;
    }

    public void addSuccess(long responseTimeMs) {
        successes += 1;
        successResponseTimes.add(responseTimeMs);
        cumulativeSuccessResponseTimeMs += responseTimeMs;
    }

//    public double getSuccessWeight() {
//        return successWeight;
//    }
//
//    public void setSuccessWeight(double successWeight) {
//        this.successWeight = successWeight;
//    }

    public int getMisses() {
        return misses;
    }

    public void addMiss(long responseTimeMs) {
        misses += 1;
        missesResponseTimes.add(responseTimeMs);
        cumulativeMissesResponseTimeMs += responseTimeMs;
    }

//    public double getMissWeight() {
//        return missWeight;
//    }
//
//    public void setMissWeight(double missWeight) {
//        this.missWeight = missWeight;
//    }

    public int getNegative() {
        return negative;
    }

    public void addNegative(long responseTimeMs) {
        negative += 1;
        negativeResponseTimes.add(responseTimeMs);
        cumulativeNegativeResponseTimeMs += responseTimeMs;
    }

//    public double getNegativeWeight() {
//        return negativeWeight;
//    }
//
//    public void setNegativeWeight(double negativeWeight) {
//        this.negativeWeight = negativeWeight;
//    }

    public int getInappropriate() {
        return inappropriate;
    }

    public void addInappropriate(long responseTimeMs) {
        inappropriate += 1;
        inappropriateResponseTimes.add(responseTimeMs);
        cumulativeInappropriateResponseTimeMs += responseTimeMs;
    }

//    public double getInappropriateWeight() {
//        return inappropriateWeight;
//    }
//
//    public void setInappropriateWeight(double inappropriateWeight) {
//        this.inappropriateWeight = inappropriateWeight;
//    }

    public long getMaxResponseTimeSuccessMs() {
        if(successResponseTimes == null || successResponseTimes.size() == 0)
            return 0L;
        long maxResponseTimeSuccessMs = 0L;
        for(long responseTime : successResponseTimes) {
            if(responseTime > maxResponseTimeSuccessMs)
                maxResponseTimeSuccessMs = responseTime;
        }
        return maxResponseTimeSuccessMs;
    }

    public long getAvgResponseTimeSuccessMs() {
        if(successes == 0) return 0L;
        return cumulativeSuccessResponseTimeMs / successes;
    }

    public long getMinResponseTimeSuccessMs() {
        if(successResponseTimes == null || successResponseTimes.size() == 0)
            return 0L;
        long minResponseTimeSuccessMs = 999999999999999999L;
        for(long responseTime : successResponseTimes) {
            if(responseTime < minResponseTimeSuccessMs)
                minResponseTimeSuccessMs = responseTime;
        }
        return minResponseTimeSuccessMs;
    }

    public long getMaxResponseTimeMissTimeMs() {
        if(missesResponseTimes == null || missesResponseTimes.size() == 0)
            return 0L;
        long maxResponseTimeMissMs = 0L;
        for(long responseTime : missesResponseTimes) {
            if(responseTime > maxResponseTimeMissMs)
                maxResponseTimeMissMs = responseTime;
        }
        return maxResponseTimeMissMs;
    }

    public long getAvgResponseTimeMissMs() {
        if(misses == 0) return 0L;
        return cumulativeMissesResponseTimeMs / misses;
    }

    public long getMinResponseTimeMissMs() {
        if(missesResponseTimes == null || missesResponseTimes.size() == 0)
            return 0L;
        long minResponseTimeMissesMs = 999999999999999999L;
        for(long responseTime : missesResponseTimes) {
            if(responseTime < minResponseTimeMissesMs)
                minResponseTimeMissesMs = responseTime;
        }
        return minResponseTimeMissesMs;
    }

    public long getMaxResponseTimeNegativeMs() {
        if(negativeResponseTimes == null || negativeResponseTimes.size() == 0)
            return 0L;
        long maxResponseTimeNegativeMs = 0L;
        for(long responseTime : negativeResponseTimes) {
            if(responseTime > maxResponseTimeNegativeMs)
                maxResponseTimeNegativeMs = responseTime;
        }
        return maxResponseTimeNegativeMs;
    }

    public long getAvgResponseTimeNegativeMs() {
        if(negative == 0) return 0L;
        return cumulativeNegativeResponseTimeMs / negative;
    }

    public long getMinResponseTimeNegativeMs() {
        if(negativeResponseTimes == null || negativeResponseTimes.size() == 0)
            return 0L;
        long minResponseTimeNegativeMs = 999999999999999999L;
        for(long responseTime : negativeResponseTimes) {
            if(responseTime < minResponseTimeNegativeMs)
                minResponseTimeNegativeMs = responseTime;
        }
        return minResponseTimeNegativeMs;
    }

    public long getMaxResponseTimeInappropriateMs() {
        if(inappropriateResponseTimes == null || inappropriateResponseTimes.size() == 0)
            return 0L;
        long maxResponseTimeInappropritaeMs = 0L;
        for(long responseTime : inappropriateResponseTimes) {
            if(responseTime > maxResponseTimeInappropritaeMs)
                maxResponseTimeInappropritaeMs = responseTime;
        }
        return maxResponseTimeInappropritaeMs;
    }

    public long getAvgResponseTimeInappropriateMs() {
        if(inappropriate == 0) return 0L;
        return cumulativeInappropriateResponseTimeMs / inappropriate;
    }

    public long getMinResponseTimeInappropriateMs() {
        if(inappropriateResponseTimes == null || inappropriateResponseTimes.size() == 0)
            return 0L;
        long maxResponseTimeInappropriateMs = 0L;
        for(long responseTime : inappropriateResponseTimes) {
            if(responseTime > maxResponseTimeInappropriateMs)
                maxResponseTimeInappropriateMs = responseTime;
        }
        return maxResponseTimeInappropriateMs;
    }

//    public double getScore() {
//        return score;
//    }
//
//    public MemoryTestPopScores getPopScores() {
//        return popScores;
//    }
//
//    public void setPopScores(MemoryTestPopScores popScores) {
//        this.popScores = popScores;
//    }
//
//    public Impairment getImpairment() {
//        return impairment;
//    }

}
