package info.inpureprojects.core.API.Utils;

import java.util.concurrent.TimeUnit;

/**
 * Created by den on 6/23/14.
 */
public class Timer {

    private long start;
    private long stop;
    private long diff;

    public void start() {
        start = System.nanoTime();
    }

    public void stop() {
        stop = System.nanoTime();
        diff = stop - start;
    }

    public long getMilli() {
        return TimeUnit.MILLISECONDS.convert(diff, TimeUnit.NANOSECONDS);
    }

    public void announce(String title) {
        System.out.println(title + " took " + TimeUnit.MILLISECONDS.convert(diff, TimeUnit.NANOSECONDS) + " milliseconds!");
    }

}
