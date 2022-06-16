package org.test;

/*
 *  This class can represent moments in time anywhere from 292270 BC through 292271 AD
 *  with microsecond accuracy while taking up 64 bits.
 */

public class Time {

    public static final int SECONDS_IN_MINUTE = 60;
    public static final int SECONDS_IN_HOUR = SECONDS_IN_MINUTE * 60;
    public static final int SECONDS_IN_DAY = SECONDS_IN_HOUR * 24;

    public static final long NANOS_PER_SECOND =  1000000000L;
    public static final long MICROS_PER_SECOND = 1000000;
    public static final long MILLIS_PER_SECOND = 1000;
    public static final long FEMTOS_PER_NANO = 1000L;

    private final long microSeconds;

    private Time(long microSeconds) {
        this.microSeconds = microSeconds;
    }

    public long getMicroSeconds() {
        return microSeconds;
    }

    public long getSeconds() {
        return microSeconds / MICROS_PER_SECOND;
    }

    public long getMicroFraction() {
        return microSeconds % MICROS_PER_SECOND;
    }

    @Override
    public boolean equals(Object o) {
        Time other = (Time) o;
        return (this.microSeconds == other.microSeconds);
    }

    @Override
    public String toString() {
        return Calendar.getSystemCalendar().toUTC(this);
    }

    public static Time now() {
        return Instant.now().toTime();
    }

    public static Time fromSeconds(long seconds) {
        return new Time(seconds * MICROS_PER_SECOND);
    }

    public static Time fromMilliSeconds(long milliSeconds) {
        return new Time(milliSeconds * MILLIS_PER_SECOND);
    }

    public static Time fromMicroSeconds(long microSeconds) {
        return new Time(microSeconds);
    }

    public static Time fromInstant(Instant instant) {
        return instant.toTime();
    }

}
