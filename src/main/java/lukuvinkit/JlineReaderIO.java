package lukuvinkit;

import java.io.IOException;
import java.io.InputStreamReader;
import org.jline.reader.*;
import org.jline.reader.impl.LineReaderImpl;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.terminal.impl.AbstractTerminal;

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
        return reader.readLine();
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
