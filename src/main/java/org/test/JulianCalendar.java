package org.test;

public final class JulianCalendar extends Calendar {

  protected final long SECONDS_TO_1970;
  private final long SECONDS_IN_PERIOD;
  private final long DAYS_IN_PERIOD;

  private final int MONTHS_IN_YEAR = 12;
  private final int DAYS_IN_STD_YEAR = 365;
  private final int DAYS_IN_LEAP_YEAR = 366;
  private final int YEARS_BETWEEN_LEAP_YEARS = 4;
  private final int YEARS_IN_CENTURY = 100;
  private final int YEARS_IN_PERIOD = 400;

  private final int[] PERIOD_YEAR_TO_DAYS = new int[YEARS_IN_PERIOD];
  private final byte[] STD_YEAR_DAY = new byte[DAYS_IN_STD_YEAR];
  private final byte[] LEAP_YEAR_DAY = new byte[DAYS_IN_LEAP_YEAR];
  private final byte[] STD_YEAR_MONTH = new byte[DAYS_IN_STD_YEAR];
  private final byte[] LEAP_YEAR_MONTH = new byte[DAYS_IN_LEAP_YEAR];
  private final byte[] STD_YEAR_MONTH_DAYS = {
    31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
  };
  private final byte[] LEAP_YEAR_MONTH_DAYS = {
    31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
  };

  protected JulianCalendar() {

    int index = 0;
    for (byte month = 0; month < MONTHS_IN_YEAR; month++) {
      for (byte day = 0; day < STD_YEAR_MONTH_DAYS[month]; day++) {
        STD_YEAR_MONTH[index] = month;
        STD_YEAR_DAY[index++] = day;
      }
    }

    index = 0;
    for (byte month = 0; month < MONTHS_IN_YEAR; month++) {
      for (byte day = 0; day < LEAP_YEAR_MONTH_DAYS[month]; day++) {
        LEAP_YEAR_MONTH[index] = month;
        LEAP_YEAR_DAY[index++] = day;
      }
    }

    int dayOfPeriod = 0;
    for (int year = 0; year < YEARS_IN_PERIOD; year++) {
      dayOfPeriod += getDaysInYear(year);
      PERIOD_YEAR_TO_DAYS[year] = dayOfPeriod;
    }

    DAYS_IN_PERIOD = dayOfPeriod;
    SECONDS_IN_PERIOD = DAYS_IN_PERIOD * Time.SECONDS_IN_DAY;

    long days = 0;
    for (int year = 0; year < 1970; year++) {
      days += getDaysInYear(year);
    }

    SECONDS_TO_1970 = days * Time.SECONDS_IN_DAY;
  }

  @Override
  public int getDaysInYear(long year) {
    return isLeapYear(year) ? DAYS_IN_LEAP_YEAR : DAYS_IN_STD_YEAR;
  }

  private boolean isLeapYear(long year) {
    if ((year % YEARS_IN_PERIOD) == 0) {
      return true;
    } else if ((year % YEARS_IN_CENTURY) == 0) {
      return false;
    } else {
      return ((year % YEARS_BETWEEN_LEAP_YEARS) == 0);
    }
  }

  private Date getDateFromSeconds(long seconds) {

    long baseYear = (seconds / SECONDS_IN_PERIOD) * YEARS_IN_PERIOD;
    int dayOfYear = (int) ((seconds % SECONDS_IN_PERIOD) / Time.SECONDS_IN_DAY);
    int periodYear = dayOfYear / DAYS_IN_LEAP_YEAR; // estimate the year then adjust
    while (dayOfYear >= PERIOD_YEAR_TO_DAYS[periodYear]) periodYear++;
    if (dayOfYear > getDaysInYear(periodYear)) {
      dayOfYear = dayOfYear - PERIOD_YEAR_TO_DAYS[periodYear - 1];
    }

    long year = baseYear + periodYear;
    int month = isLeapYear(year) ? LEAP_YEAR_MONTH[dayOfYear] : STD_YEAR_MONTH[dayOfYear];
    int day = isLeapYear(year) ? LEAP_YEAR_DAY[dayOfYear] : STD_YEAR_DAY[dayOfYear];
    return new Date(year, month, day);
  }

  @Override
  public String toUTC(DateTime dateTime) {
    Clock clock = new Clock(dateTime.getSeconds());
    Date date = getDateFromSeconds(dateTime.getSeconds());
    return String.format("%sT%sZ", date.toString(), clock.toString());
  }

  @Override
  public String toUTC(Time time) {
    Clock clock = new Clock(time.getSeconds());
    Date date = getDateFromSeconds(time.getSeconds());
    return String.format("%sT%s.06dZ", date.toString(), clock.toString(), time.getMicroFraction());
  }

  @Override
  public String toUTC(Instant instant) {
    Date date = getDateFromSeconds(instant.getRoundedSeconds());
    Clock clock = new Clock(instant.getRoundedSeconds());
    return String.format("%sT%s.%09dZ", date.toString(), clock.toString(), instant.getNanoFraction());
  }

  class Clock {

    private final int hour;
    private final int minute;
    private final int second;

    public Clock(int hour, int minute, int second) {
      this.hour = hour;
      this.minute = minute;
      this.second = second;
    }

    public Clock(long seconds) {
      int secondsIntoDay = (int) (seconds % Time.SECONDS_IN_DAY);
      this.hour = secondsIntoDay / Time.SECONDS_IN_HOUR;
      this.minute = (secondsIntoDay % Time.SECONDS_IN_HOUR) / Time.SECONDS_IN_MINUTE;
      this.second = (secondsIntoDay % Time.SECONDS_IN_MINUTE);
    }

    @Override
    public String toString() {
      return String.format("%02d:%02d:%02d", hour, minute, second);
    }
  }

  class Date {

    private final long year;
    private final int month;
    private final int day;

    public Date(long year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @Override
    public String toString() {
        return String.format("%04d-%02d-%02d", year, month+1, day+1);
    }
  }

}