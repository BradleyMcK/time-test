package org.test;

/*
 *  This class can represent moments in time anywhere from the moment of the big bang to the estimated
 *  end of the universe with femtosecond accuracy while taking up 128 bits.
 */

public class Instant {

    private final long seconds;
    private final long femtoSeconds;

    public Instant(long seconds) {
        this.seconds = seconds;
        femtoSeconds = 0;
    }

    public Instant(long seconds, long femtoSeconds) {
        this.seconds = seconds;
        this.femtoSeconds = femtoSeconds;
    }

    public long getNanoFraction() {
        return femtoSeconds / Time.FEMTOS_PER_NANO;
    }

    public long getRoundedSeconds() {
        return (femtoSeconds > 500000000000L) ? seconds+1 : seconds;
    }

    public Time toTime() {
        long microSeconds = (seconds * 1000000) + (femtoSeconds / 1000000);
        return Time.fromMicroSeconds(microSeconds);
    }

    public DateTime toDateTime() {
        return DateTime.fromSeconds(getRoundedSeconds());
    }

    @Override
    public String toString() {
        return Calendar.getSystemCalendar().toUTC(this);
    }

    public static Instant now() {

        return Calendar.now();
        //long nanoTime = Calendar.nanoSecondsFromYear0();
        //long femotos = (nanoTime % Time.NANOS_PER_SECOND) * FEMTOS_PER_NANO;
        //long seconds = nanoTime / Time.NANOS_PER_SECOND;
       // return new Instant(seconds, femotos);
    }

    /*
    public static Instant now2() {
        long time = System.nanoTime();
        if (time < baseNanoTime) baseNanoTime = time;
        long nanoTime = (time + (baseMilliTime - baseNanoTime));
        long femotos = (nanoTime % NANOS_PER_SECOND) * FEMTOS_PER_NANO;
        long seconds = JulianCalendar.SECONDS_TO_1970 + (nanoTime / NANOS_PER_SECOND);
        return new Instant(seconds, femotos);
    }
    */

}
