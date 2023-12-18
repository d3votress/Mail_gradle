package com.example.mails_gradle;

import java.io.*;
import java.nio.file.Paths;

/**
 * Класс расшифровки файла
 */
public class Hider {

    /**
     * @param keyz ключ расшифровки
     * @param file_path путь до файла, который будет расшифровываться
     * @return массив байтов логина и пароля
     * @throws IOException Вызывается исключение, когда невозможно перевести ключ в байты
     */
    public static byte[] unhide(String keyz, String file_path) throws IOException {
        byte key = 0;
        try {
            key = Byte.parseByte(keyz);
        } catch (Exception e) {
            throw new IOException(e);
        }

        byte x;
        DataInputStream dis = null;
        try {
            dis = new DataInputStream(new FileInputStream(Paths.get(new File("").getAbsolutePath() + file_path).toFile()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while (dis.available() > 0) {
            try {
                x = dis.readByte();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            x ^= key;
            bos.write(x);
        }

        return bos.toByteArray();
    }

}