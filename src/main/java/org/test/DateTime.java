package org.test;

/*
 *  This class can represent moments in time anywhere from 4082 BC through 4083 AD AD
 *  with one minute accuracy while taking up only 32 bits.
 */

public class DateTime {

    private final int minutes;

    public DateTime(int minutes) {
        this.minutes = minutes;
    }

    public int getMinutes() {
        return minutes;
    }

    public long getSeconds() {
        return (long) minutes * (long) Time.SECONDS_IN_MINUTE;
    }

    @Override
    public String toString() {
        return Calendar.getSystemCalendar().toUTC(this);
    }

    public static DateTime fromSeconds(long seconds) {
        Long minutes = seconds / Time.SECONDS_IN_MINUTE;
        if (minutes > Integer.MAX_VALUE || minutes < Integer.MIN_VALUE) {
          String msg = "The DateTime class cannot represent moments in time after 4083 AD or before 4082 BC";
          throw new IllegalArgumentException(msg);
        } else {
          return new DateTime(minutes.intValue());
        }
    }
}
