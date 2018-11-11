package name.ggladilin.utils.unwrapper.oracle;

import com.salvis.unwrapper.util.Unwrapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;


/**
 * @author gwg
 */
public class MainClass {

    private static final String[] DEF_ARGS = {"input"};

    public static void main(final String[] args) {
        final String[] argP = (args == null || args.length == 0) ? DEF_ARGS : args;
        for (final String fileName : argP) {
            try {
                final FileReader inputFile = new FileReader(fileName);
                final BufferedReader stringReader = new BufferedReader(inputFile);
                final StringBuilder allStrings = new StringBuilder();
                stringReader.lines().forEach(item -> allStrings.append(item).append("\n"));

                final String result = Unwrapper.unwrap(allStrings.toString());

                final FileWriter outputFile = new FileWriter(fileName, false);
                outputFile.write(result);
                outputFile.close();
                System.out.println(String.format("File %s processed successfully", fileName));
            } catch (final Throwable throwable) {
                System.out.println(String.format("File %s processing failed: %s", fileName, throwable.getMessage()));
            }
        }
    }

}
