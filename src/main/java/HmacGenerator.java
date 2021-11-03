import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HmacGenerator {

    private static final String HMAC = "HmacSHA256";
    private static final int KEY_LENGTH = 16;
    private String message;
    private String key;
    private String hmac;

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length*2);
        for(byte b: bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private byte[] generateKeyBytes() {
        SecureRandom secureRandom = new SecureRandom();
        return secureRandom.generateSeed(KEY_LENGTH);
    }

    public HmacGenerator(String message_) throws NoSuchAlgorithmException, InvalidKeyException {
        message = message_;
        generate();
    }

    private void generate() throws NoSuchAlgorithmException, InvalidKeyException, IllegalStateException {
        Mac signer = Mac.getInstance(HMAC);
        byte[] bytes = generateKeyBytes();
        SecretKeySpec keySpec = new SecretKeySpec(bytes, HMAC);
        signer.init(keySpec);
        byte[] digest = signer.doFinal(message.getBytes(StandardCharsets.UTF_8));

        hmac = bytesToHex(digest);
        key = bytesToHex(bytes);
    }

    public String getKey() {
        return key;
    }
    public String getHmac() {
        return hmac;
    }
}
