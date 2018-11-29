package lukuvinkit;

import java.io.IOException;

public interface IO extends AutoCloseable {

    void print(Object toPrint);

    void println(Object printLine);

    void println();

    String readLine() throws IOException;

    default String readLine(String prompt) throws IOException {
        print(prompt);
        flush();
        return readLine();
    }

    void flush();

    void close() throws IOException;
}