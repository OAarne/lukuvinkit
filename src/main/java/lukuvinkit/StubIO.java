package lukuvinkit;

import java.util.ArrayList;
import java.util.List;

public class StubIO implements IO {

    private List<String> inputs;
    private int i;
    private List<String> outputs;

    public StubIO(List<String> inputs) {
        this.inputs = inputs;
        outputs = new ArrayList<>();
        i = 0;
    }

    @Override
    public void print(Object obj) {
        outputs.add(obj.toString());
    }

    @Override
    public void println(Object obj) {
        outputs.add(obj.toString());
    }

    @Override
    public void println() {
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
