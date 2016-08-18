package Core.Utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * Created by vthiruvengadam on 8/17/16.
 * This Code was taken from https://www.mkyong.com/java/how-to-export-data-to-csv-file-java/
 */
public class XmlToCsv {

    // FORMAT
    // fieldnames = ['NpnNumber', 'State', 'LicenseNumber', 'EffectiveDate', 'ExpirationDate', 'ClassName', 'IsResidentLicense', 'IsActive', 'LOAName', 'IsLOAActive']
    private static final char DEFAULT_SEPARATOR = ',';

    public static void writeLine(Writer w, List<String> values) throws IOException {
        writeLine(w, values, DEFAULT_SEPARATOR, ' ');
    }

    public static void writeLine(Writer w, List<String> values, char separators) throws IOException {
        writeLine(w, values, separators, ' ');
    }

    //https://tools.ietf.org/html/rfc4180
    private static String followCVSformat(String value) {

        String result = value;
        if (result.contains("\"")) {
            result = result.replace("\"", "\"\"");
        }
        return result;

    }

    public static void writeLine(Writer w, List<String> values, char separators, char customQuote) throws IOException {

        boolean first = true;

        //default customQuote is empty

        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            if (!first) {
                sb.append(separators);
            }
            if (customQuote == ' ') {
                sb.append(followCVSformat(value));
            } else {
                sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
            }

            first = false;
        }
        sb.append("\n");
        w.append(sb.toString());


    }

    public static boolean writeFile(String aInFilePath, String aInContent) {
        boolean lStatus = false;
        try {
            FileWriter lWriter = new FileWriter(aInFilePath);
            lWriter.append(aInContent);
            lWriter.flush();
            lWriter.close();
            lStatus = true;
        }
        catch (Exception ex) {
            System.out.println("Failed to write file " + aInFilePath + " " + ex.getMessage());
        }
        return lStatus;
    }
}
