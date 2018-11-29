/*
 * The MIT License
 *
 * Copyright 2018 tutu.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package lukuvinkit;

public class Lukuvinkit {

    public static void main(String[] args) throws InterruptedException {
        try (IO io = new BufferedReaderIO()) {
            Storage storage = FileSave.loadStorage("vinkit.json");
            CommandInterpreter interpreter = new CommandInterpreter(storage, io);
            interpreter.mainLoop();
            FileSave.saveStorage("vinkit.json", storage);
        } catch (Exception e) {
            System.err.println("Virhe poistuttaessa ohjelmasta.");
            e.printStackTrace();
        }
    }
}
