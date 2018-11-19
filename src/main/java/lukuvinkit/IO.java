package lukuvinkit;

import java.io.IOException;

public interface IO extends AutoCloseable {
    void print(String toPrint);
    void printLn(String printLine);
    String readLine() throws IOException;
    void flush();
}