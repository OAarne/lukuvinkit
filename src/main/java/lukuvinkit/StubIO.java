package lukuvinkit;

import java.util.ArrayList;
import java.util.List;

public class StubIO implements IO {

    private List<String> inputs;
    private int i;
    private List<String> outputs;

    public List<String> getOutputs() {
        return outputs;
    }

    public StubIO(List<String> inputs) {
        this.inputs = inputs;
        i = 0;
        outputs = new ArrayList<>();
        outputs.add("");
    }

    @Override
    public void print(Object obj) {
        int lastIndex = outputs.size() - 1;
        outputs.set(lastIndex, outputs.get(lastIndex) + obj.toString());
    }

    @Override
    public void println() {
        outputs.add("");
    }

    @Override
    public void println(Object obj) {
        print(obj);
        println();
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
