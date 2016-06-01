package de.andre.utils.idgen;

import java.util.concurrent.atomic.AtomicLong;

public class IdSpace {
    private final String name;
    private final long batchSize;

    private long seed;
    private long threshold;

    private final AtomicLong sequence;

    public IdSpace(final String name, final long seed, final long batchSize) {
        this.name = name;
        this.seed = seed;
        this.batchSize = batchSize;
        this.sequence = new AtomicLong(seed);

        threshold = seed + batchSize - 1L;
    }

    public String getName() {
        return name;
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }

    public long getBatchSize() {
        return batchSize;
    }

    public AtomicLong getSequence() {
        return sequence;
    }

    public long getThreshold() {
        return threshold;
    }

    public void setThreshold(long threshold) {
        this.threshold = threshold;
    }

    @Override
    public String toString() {
        return "IdSpace{" +
                "name='" + name + '\'' +
                ", batchSize=" + batchSize +
                ", seed=" + seed +
                ", threshold=" + threshold +
                ", sequence=" + sequence +
                '}';
    }
}
