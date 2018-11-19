package lukuvinkit;
import java.util.ArrayList;
import java.util.List;

public class StubIO implements IO {

    private List<String> inputs;
    private int i;
    private ArrayList<String> outputs;

    public StubIO(List<String> inputs) {
        this.inputs = inputs;
        outputs = new ArrayList<>();
        i = 0;
    }

    @Override
    public void print(String toPrint) {
        outputs.add(toPrint);
    }


    @Override
    public void printLn(String printLine) {
        outputs.add(printLine);
    }

    @Override
    public String readLine() {
        if (i < this.inputs.size()) {
            return this.inputs.get(i++);
        }
        return "";
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() {
    }
}
