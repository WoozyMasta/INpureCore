package info.inpureprojects.core.Utils;

import java.io.PrintStream;
import java.util.ArrayList;

/**
 * Created by den on 11/2/2014.
 */
public class FilterPrintStream extends FakePrintStream {

    private static final String name = "System.out";
    private ArrayList<FilterLogger.Filter> filters = new ArrayList();
    private PrintStream original;
    private int blockedMessageCount = 0;

    public FilterPrintStream(PrintStream original) {
        super();
        this.original = original;
    }

    public void registerFilter(FilterLogger.Filter f) {
        filters.add(f);
        FilterLogger.log.info("Registered filter on channel " + name + " of type " + f.getClass().getName());
    }

    @Override
    public void println(String x) {
        boolean display = true;
        for (FilterLogger.Filter f : filters) {
            if (!f.display(x)) {
                display = false;
                break;
            }
        }
        if (display) {
            original.println(x);
        }
    }

    @Override
    public void print(String s) {
        boolean display = true;
        for (FilterLogger.Filter f : filters) {
            if (!f.display(s)) {
                display = false;
                break;
            }
        }
        if (display) {
            original.print(s);
        }
    }

    public void report() {
        FilterLogger.log.info(String.valueOf(this.blockedMessageCount) + " messages suppressed from logger: " + name);
    }
}
