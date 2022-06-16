package org.test;

public abstract class Calendar {

    private static final JulianCalendar julianCalendar = new JulianCalendar();

    private static Calendar systemCalendar = julianCalendar;
    private static long currentTimeNanos;
    private static long nanoTimeAtStart;

    protected Calendar() {
        currentTimeNanos = System.currentTimeMillis() * Time.MICROS_PER_SECOND;
        nanoTimeAtStart = System.nanoTime();
    }

    public static Calendar getSystemCalendar() {
        return systemCalendar;
    }

    public static void setSystemCalendar(Calendar calendar) {
        systemCalendar = calendar;
    }

    // Returns the number of nanoseconds elapsed from midnight, January 1, year 0 of the Gregorian calendar

    protected static Instant now() {

        long time = System.nanoTime();
        if (time < nanoTimeAtStart) nanoTimeAtStart = time;
        long nanoTime = (time + (currentTimeNanos - nanoTimeAtStart));
        long femtoSeconds = (nanoTime % Time.NANOS_PER_SECOND) * Time.FEMTOS_PER_NANO;
        long seconds = (nanoTime / Time.NANOS_PER_SECOND) + julianCalendar.SECONDS_TO_1970;
        return new Instant(seconds, femtoSeconds);
    }

    public abstract String toUTC(DateTime dateTime);

    public abstract String toUTC(Time time);

    public abstract String toUTC(Instant instant);

    public abstract int getDaysInYear(long year);

}
