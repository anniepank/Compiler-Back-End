package yapl.test.backend.rm;

import java.io.OutputStream;
import java.io.PrintStream;

public class PrintStub extends PrintStream {
    public PrintStub(OutputStream out) {
        super(out);
    }

    @Override
    public void println(String x) {
    }
}
