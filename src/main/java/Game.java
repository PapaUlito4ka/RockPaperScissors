import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Game {
    private String[] headers;
    private String[][] tableData;
    private String moveStr;
    private int moveIdx;
    private final HmacGenerator hmacGenerator;
    private final TableGenerator tableGenerator;

    public Game(String[] headers_) throws NoSuchAlgorithmException, InvalidKeyException {
        fillHeaders(headers_);
        fillTableData();
        makeMove();
        hmacGenerator = new HmacGenerator(moveStr);
        tableGenerator = new TableGenerator(headers, tableData);
    }

    public void run() {
        printHmac();
        while (true) {
            printMenu();
            try {
                play(playerMove());
                break;
            } catch (Exception ignored) {}
        }
    }

    private void printMenu() {
        for (int i = 1; i < headers.length; i++) {
            System.out.println(i + " - " + headers[i]);
        }
        System.out.println("0 - exit");
        System.out.println("? - help");
    }

    private void printTable() {
        System.out.println(tableGenerator.generate());
    }

    private void printHmac() {
        System.out.println("HMAC: " + hmacGenerator.getHmac());
    }

    private void printKey() {
        System.out.println("HMAC key: " + hmacGenerator.getKey());
    }

    private void fillTableData() {
        int size = headers.length;
        int halfSize = (size - 1) / 2;
        tableData = new String[size - 1][size];

        for (int i = 0; i < tableData.length; i++) {
            tableData[i][0] = headers[i + 1];
            tableData[i][i + 1] = "DRAW";
            int j = i;
            for (int k = 0; k < halfSize; k++) {
                if (j == 0) j = size - 1;
                tableData[i][j] = "LOSE";
                j--;
            }
            j = i + 2;
            for (int k = 0; k < halfSize; k++) {
                if (j >= size) j = 1;
                tableData[i][j] = "WIN";
                j++;
            }
        }
    }

    private void fillHeaders(String[] headers_) {
        headers = new String[headers_.length + 1];
        int i = 0;
        headers[i++] = "PC \\ USER";
        for (; i < headers.length; i++) {
            headers[i] = headers_[i - 1];
        }
    }

    private void makeMove() {
        int min = 1;
        int max = headers.length - 1;
        moveIdx = (int) ((Math.random() * (max - min)) + min);
        moveStr = headers[moveIdx];
    }

    private String playerMove() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your move: ");
        return scanner.next();
    }

    private void play(String playerMove) throws Exception {
        if (playerMove.equals("?")) {
            printTable();
            throw new Exception("Table print");
        }
        int playerChoice;
        try {
            playerChoice = Integer.parseInt(playerMove);
        } catch (NumberFormatException e) {
            throw new Exception("Invalid player move");
        }

        if (playerChoice == 0) {
            System.out.println("Bye!");
            return;
        }

        System.out.println("Your move: " + headers[playerChoice]);
        System.out.println("Computer move: " + moveStr);
        if (tableData[moveIdx - 1][playerChoice].equals("DRAW")) {
            System.out.println("Draw!");
        } else if (tableData[moveIdx - 1][playerChoice].equals("WIN")) {
            System.out.println("You win!");
        } else {
            System.out.println("You lost!");
        }
        printKey();
    }

}
