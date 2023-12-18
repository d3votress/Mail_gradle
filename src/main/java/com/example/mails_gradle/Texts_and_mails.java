package com.example.mails_gradle;

import java.io.File;

/**
 * Класс для заполнения комбобоксов
 */
public class Texts_and_mails {

    //static String path = "";

    /**
     * Пути к файлам с мейлами для комбобокса
     */
    public static String[] mails = {
            ("/src/main/resources/com/example/mails_gradle/MAILS/mails_1"),
            ("/src/main/resources/com/example/mails_gradle/MAILS/mails_2"),
            ("/src/main/resources/com/example/mails_gradle/MAILS/mails_3")
    };
    /**
     * Пути к файлам с текстами для отправки для комбобокса
     */
    public static String[] texts = {
            ("/src/main/resources/com/example/mails_gradle/TEXTS/texts_1"),
            ("/src/main/resources/com/example/mails_gradle/TEXTS/texts_2"),
            ("/src/main/resources/com/example/mails_gradle/TEXTS/texts_3")
    };

}
