import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException {
        if (args.length < 3) {
            System.out.println("Number of arguments must be >= 3");
            System.out.println("Example: ");
            System.out.println("java -jar game.jar rock paper scissors lizard Spock");
            return;
        }
        if (args.length % 2 == 0) {
            System.out.println("Number of arguments must be odd");
            System.out.println("Example: ");
            System.out.println("java -jar game.jar rock paper scissors");
            return;
        }

        for (int i = 0; i < args.length - 1; i++) {
            for (int j = i + 1; j < args.length; j++) {
                if (args[i].equals(args[j])) {
                    System.out.println("Each argument should be unique");
                    System.out.println("Example: ");
                    System.out.println("java -jar game.jar test1 test2 test3");
                    return;
                }
            }
        }

        Game game = new Game(args);
        game.run();
    }
}
