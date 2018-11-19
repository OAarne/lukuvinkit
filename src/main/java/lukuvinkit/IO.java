package lukuvinkit;

import java.io.IOException;

public interface IO extends AutoCloseable {

    void print(Object toPrint);

    void println(Object printLine);

    void println();

    String readLine() throws IOException;

    void flush();
}