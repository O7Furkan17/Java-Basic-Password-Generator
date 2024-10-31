import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

public class pwd_generator {

    private SecretKey secretKey;

    public pwd_generator() throws NoSuchAlgorithmException {
        // Generate a AES secret key
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256); // AES-256
        secretKey = keyGen.generateKey();
    }

    // Encrypt plaintext with AES
    private String encrypt(String plaintext) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Generate a strong password using given inputs
    public String generatePassword(String name, String birthDate, String siteName) throws Exception {
        // Concatenate inputs
        String input = name + birthDate + siteName;

        // Encrypt the input
        String encryptedInput = encrypt(input);

        // Generate special characters
        String specialCharacters = "!@#$%^&*()_+{}[]<>?/";

        // Create a StringBuilder to build the password
        StringBuilder passwordBuilder = new StringBuilder();

        // Append encrypted input
        passwordBuilder.append(encryptedInput);

        // Append random special characters
        Random random = new Random();
        for (int i = 0; i < 5; i++) { // Append 5 special characters
            int randomIndex = random.nextInt(specialCharacters.length());
            passwordBuilder.append(specialCharacters.charAt(randomIndex));
        }

        return passwordBuilder.toString();
    }

    public static void main(String[] args) throws Exception {
        
            if (args.length < 3) {
                System.err.println("Usage: java PasswordGenerator <name> <birthDate> <siteName>");
                System.exit(1);
            }

            String name = args[0];
            String birthDate = args[1];
            String siteName = args[2];

            pwd_generator generator = new pwd_generator();
            String password = generator.generatePassword(name, birthDate, siteName);
            System.out.println("Generated Password: " + password);
     
    }
}
