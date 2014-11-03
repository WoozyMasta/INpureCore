package info.inpureprojects.core.Utils;

import java.io.PrintStream;

/**
 * Created by den on 11/2/2014.
 */
public class FakePrintStream extends PrintStream {

    public FakePrintStream() {
        super(new FakeOutputStream());
    }
}
