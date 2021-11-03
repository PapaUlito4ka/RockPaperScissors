import com.github.freva.asciitable.AsciiTable;

public class TableGenerator {
    private final String[] headers;
    private final String[][] data;

    public TableGenerator(String[] headers_, String[][] data_) {
        headers = headers_.clone();
        data = data_.clone();
    }

    public String generate() {
        return AsciiTable.getTable(headers, data);
    }
}
