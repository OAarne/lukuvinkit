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
    public void print(Object obj) {
        System.out.print(obj);
    }

    @Override
    public void println(Object obj) {
        System.out.println(obj);
    }

    @Override
    public void println() {
        System.out.println();
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
    public void close() throws IOException {
        reader.close();
    }

}
