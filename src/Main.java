import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        // Encryptor encryptor1 = new Encryptor(3, 4);
        // String encrypt = encryptor1.encryptMessage("What are you doing next weekend?");

        // System.out.println(encrypt + "\n");

        // Encryptor encryptor2 = new Encryptor(6, 5);

        // encrypt = encryptor2.encryptMessage("This one time I was trying to do a handstand and fell backwards and landed on my shoe. It hurt! I won't try that again.");
        // System.out.println(encrypt);


        Encryptor encryptor = new Encryptor(3, 2, 2);

        System.out.println(encryptor.superEncryptMessage("aPPLEZ are reALLY yumm!"));
//        System.out.println(encryptor.superDecryptMessage("bQF s fQMabfsBMZzn!AAM vnAAA"));
    }
}

