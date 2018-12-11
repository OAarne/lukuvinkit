package lukuvinkit.ui;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import static java.util.stream.Collectors.toList;

import org.jline.reader.*;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import lukuvinkit.ReadingTipField;
import lukuvinkit.TipType;

public class JlineReaderIO implements IO {

    private static List<Candidate> commands = Arrays.asList(Command.values())
        .stream()
        .map(c -> new Candidate(c.getCommandString(), c.getCommandString(), c.getGroup(), c.getHelpText(), null, null, true))
        .collect(toList());
    
    private static List<Candidate> tipTypes = Arrays.asList(TipType.values())
        .stream()
        .map(t -> new Candidate(t.getName(), t.getName(), "Vinkkityypit", null, null, null, true))
        .collect(toList());
    
    private static List<Candidate> tipFields = ReadingTipField.VALUES
        .stream()
        .map(t -> new Candidate(
        t.getName() + "=",
        t.getName(),
        "Kentät",
        "oletus on \"" + t.getDefaultValueString() + "\"",
        null, null, false
    ))
        .collect(toList());

    private Terminal terminal;
    private LineReader reader;

    public JlineReaderIO() throws IOException {
        
        this.terminal = TerminalBuilder.terminal();
        this.reader = LineReaderBuilder
            .builder()
            .completer(JlineReaderIO::completer)
            .build();
    }

    private static void completer(LineReader _r, ParsedLine parsedLine, List<Candidate> candidates) {
        if (parsedLine.words().size() > 1) {
            if (parsedLine.words().size() == 2) {
                candidates.addAll(tipTypes);
            }
            candidates.addAll(tipFields);
        } else {
            candidates.addAll(commands);
        }
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

    @Override
    public int getTerminalWidth() {
        return terminal.getWidth();
    }
}
