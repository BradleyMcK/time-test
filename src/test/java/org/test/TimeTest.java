package org.test;

public class TimeTest {

    /*
    public void print(long seconds, int year, int day, int hour, int min, int sec) {

        DateTime date1 = DateTime.getDate(seconds);
        DateTime date2 = DateTime.getDate(year, day);
        Clock clock1 = new Clock(seconds);
        Clock clock2 = new Clock(hour, min, sec);

        if (!date1.equals(date2) || !clock1.equals(clock2)) {
            System.out.println("Bad");
        }
    }

    private void test() {

        Time now = Time.now();
        DateTime dt = DateTime.getDateTime(now.getSeconds());
        System.out.println(dt.toString());

        long totalSeconds = 0;
        for (int year = 0; year < 2100; year++) {
            System.out.println(year);
            for (int day = 0; day < JulianCalendar.getDaysInYear(year); day++) {
                for (int hour = 0; hour < 24; hour++) {
                    for (int min = 0; min < 60; min++) {
                        for (int sec = 0; sec < 60; sec++) {
                            if ((sec == 0 || sec == 59) &&
                                    (min == 59 && hour == 23) &&
                                    (day == 0 || day > 363)) {
                                print(totalSeconds, year, day, hour, min, sec);
                            }
                            totalSeconds++;
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {

        TimeTest main = new TimeTest();
        main.test();
    }

     */
}
