package lukuvinkit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BufferedReaderIO implements IO {

    private BufferedReader reader;

    public BufferedReaderIO() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void print(String toPrint) {
        System.out.print(toPrint);
    }

    @Override
    public void printLn(String printLine) {
        System.out.println(printLine);
    }

    @Override
    public String readLine() throws IOException {
        return reader.readLine();
    }

    @Override
    public void flush() {
        System.out.flush();
    }

    @Override
    public void close() throws Exception {
        reader.close();
    }

}
