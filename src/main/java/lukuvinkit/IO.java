package lukuvinkit;

public interface IO extends AutoCloseable {
    void print(String toPrint);
    void printLn(String printLine);
    String readLine();
    void flush();
}