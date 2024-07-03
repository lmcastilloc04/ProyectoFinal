import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.SecureRandom;
import java.util.Base64;

public class Cifrador {
    private static final String ALGORITHM = "AES"; // Algoritmo de cifrado AES

    // Método para generar una clave secreta
    public static SecretKey generateSecretKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM); // Crear un generador de claves para AES
        keyGen.init(256, new SecureRandom()); // Inicializar el generador con una longitud de clave de 256 bits y un generador de números aleatorios seguro
        return keyGen.generateKey(); // Generar y retornar la clave secreta
    }

    // Método para guardar la clave secreta en un archivo
    public static void saveSecretKey(SecretKey secretKey, String fileName) throws Exception {
        byte[] keyBytes = secretKey.getEncoded(); // Obtener la clave en formato de bytes
        String keyString = Base64.getEncoder().encodeToString(keyBytes); // Codificar la clave en Base64 para guardarla como una cadena
        try (FileOutputStream fos = new FileOutputStream(fileName)) { // Crear un FileOutputStream para escribir en el archivo
            fos.write(keyString.getBytes()); // Escribir la clave codificada en el archivo
        }
    }

    // Método para cargar una clave secreta desde un archivo
    public static SecretKey loadSecretKey(String fileName) throws Exception {
        byte[] keyBytes;
        try (FileInputStream fis = new FileInputStream(fileName)) { // Crear un FileInputStream para leer desde el archivo
            keyBytes = fis.readAllBytes(); // Leer todos los bytes del archivo
        }
        String keyString = new String(keyBytes); // Convertir los bytes leídos a una cadena
        byte[] decodedKey = Base64.getDecoder().decode(keyString); // Decodificar la cadena desde Base64 a bytes
        return new SecretKeySpec(decodedKey, ALGORITHM); // Crear una clave secreta a partir de los bytes decodificados
    }

    // Método para cifrar un archivo
    public static void encryptFile(SecretKey secretKey, File inputFile, File outputFile) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM); // Obtener una instancia del cifrador para AES
        cipher.init(Cipher.ENCRYPT_MODE, secretKey); // Inicializar el cifrador en modo de encriptación con la clave secreta

        try (FileInputStream fis = new FileInputStream(inputFile);
             FileOutputStream fos = new FileOutputStream(outputFile)) { // Crear streams para leer el archivo de entrada y escribir el archivo de salida
            byte[] inputBytes = fis.readAllBytes(); // Leer todos los bytes del archivo de entrada
            byte[] outputBytes = cipher.doFinal(inputBytes); // Cifrar los bytes leídos
            fos.write(outputBytes); // Escribir los bytes cifrados en el archivo de salida
        }
    }

    // Método para descifrar un archivo
    public static void decryptFile(SecretKey secretKey, File inputFile, File outputFile) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM); // Obtener una instancia del cifrador para AES
        cipher.init(Cipher.DECRYPT_MODE, secretKey); // Inicializar el cifrador en modo de desencriptación con la clave secreta

        try (FileInputStream fis = new FileInputStream(inputFile);
             FileOutputStream fos = new FileOutputStream(outputFile)) { // Crear streams para leer el archivo de entrada y escribir el archivo de salida
            byte[] inputBytes = fis.readAllBytes(); // Leer todos los bytes del archivo de entrada
            byte[] outputBytes = cipher.doFinal(inputBytes); // Desencriptar los bytes leídos
            fos.write(outputBytes); // Escribir los bytes desencriptados en el archivo de salida
        }
    }
}