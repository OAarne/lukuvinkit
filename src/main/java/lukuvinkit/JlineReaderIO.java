package lukuvinkit;

import java.io.IOException;
import org.jline.reader.*;
import org.jline.reader.impl.LineReaderImpl;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class JlineReaderIO implements IO {

    private Terminal terminal;
    private LineReader reader;

    public JlineReaderIO() throws IOException {
        this.terminal = TerminalBuilder.terminal();
        this.reader = new LineReaderImpl(terminal);
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
        try {
            return reader.readLine();
        } catch (EndOfFileException e) {
            return null;
        }
    }

    @Override
    public String readLine(String prompt) throws IOException {
        try {
            return reader.readLine(prompt);
        } catch (EndOfFileException e) {
            return null;
        }
    }

    @Override
    public void flush() {
        System.out.flush();
    }

    @Override
    public void close() throws IOException {
        terminal.close();
    }

}
