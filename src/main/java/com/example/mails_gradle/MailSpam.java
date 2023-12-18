package com.example.mails_gradle;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;


/**
 * Главный класс проекта
 */
public class MailSpam {


    /**
     * @param userLogin логин от мейла
     * @param appPass пароль от мейла
     * @return сессия, с помощью которой мы будем отправлять сообщения
     */
    private static Session start_session(String userLogin, String appPass){
        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");//Enable tls session
        props.put("mail.smtp.auth", "true");//Enable authentication
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.host", "smtp.yandex.ru");//Server's host
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.port", "465");//Server's port

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userLogin, appPass);
            }
        });
        return session;
    }



    /**
     * @param file_path путь к файлу от папки проекта до самого файла
     * @return массив с логином и паролем от аккаунта
     */
    private static String[] get_login_and_pass(String file_path, String pass){
        try {

            byte[] decryptedData = Hider.unhide(pass, file_path);
            String decData = new String(decryptedData, StandardCharsets.UTF_8);

            String[] log_pass = decData.split(" ");

            if (log_pass.length == 2){
                return log_pass;
            }
            else {
                System.out.println("Неверный пароль");
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * @param text текст сообщения
     * @param email мейл кому отправится сообщение
     * @param session сессия, с помощью которой мы будем отправлять сообщение
     * @param userLogin логин от кого будет отправлено сообщение
     */
    private static void send_message(String text, String email, Session session, String userLogin) {

        try
        {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(userLogin)); // от кого
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));//кому
            message.setSubject("Тест");//Тема сообщения
            message.setText(text);
            message.setSentDate(new Date());

            Transport.send(message);
            System.out.println("Сообщение отправлено на email: " + email);
        }
        catch (MessagingException m){
            System.out.println("Ошибка в отправке сообщения");
        }

    }

    /**
     * @param name имя, кому придет сообщение
     * @param text текст, который будет в сообщении
     * @return отредактированный текст для отправки на почту
     */
    private static String edit_text_for_send(String name, String text){
        text = "Добрый день, " + name + "!\n" + text;
        return text;
    }

    /**
     * @param path путь к файлу от папки проекта до самого файла
     * @return массив имен, кому придет рассылка
     */
    private static String[] get_names(String path) {
        try {
            int count = 0;
            String[] names_mails = readFile(path).split("\n");
            String[] names = new String[names_mails.length];
            for (String str : names_mails) {
                names[count] = str.split(" ")[0];
                count += 1;
            }
            return names;
        }catch (Exception e){
            System.out.println("error get_names");
        }
        return new String[0];
    }

    /**
     * @param path путь к файлу от папки проекта до самого файла
     * @return массив мейлов, кому придет рассылка
     */
    private static String[] get_mails(String path) {
        try {
            System.out.println("Аккаунты, которым отправятся сообщения:");
            int count = 0;
            String[] names_mails = readFile(path).split("\n");
            String[] mails = new String[names_mails.length];
            for (String str : names_mails) {
                System.out.println(str);
                mails[count] = str.split(" ")[1];
                count += 1;
            }
            return mails;
        }catch (Exception e){
            System.out.println("error get_mails");
        }
        return new String[0];
    }

    /**
     * @param filePath путь к файлу от папки проекта до самого файла
     * @return текст файла
     */
    public static String readFile(String filePath){
        byte[] encoded = new byte[0];
        String globalPath = new File("").getAbsolutePath();
        String path = globalPath + filePath;
        try {
            encoded = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            System.out.println("error read file");
        }
        return new String(encoded, StandardCharsets.UTF_8);
    }


    /**
     * @param mails_path путь до файла с мейлами и именами
     * @param texts_path путь до файла с тестами для отправки
     * @param pass пароль, с помощью которого будет расшифрован файл
     * @return отравлены сообщения или нет (текст отображается в GUI)
     */
    public static String mail(String mails_path, String texts_path, String pass) {
        if ((mails_path != null) & (texts_path != null)) {
            String[] login_and_pass = get_login_and_pass("/src/main/resources/com/example/mails_gradle/login_and_pass", pass);
            if (login_and_pass != null) {
                String userLogin = login_and_pass[0];
                String appPass = login_and_pass[1];
                System.out.println((userLogin));
                System.out.println(appPass);
                Session session = start_session(userLogin, appPass);

                String names_and_mails_path = mails_path;
                String[] names = get_names(names_and_mails_path);
                String[] mails = get_mails(names_and_mails_path);
                String text_for_send = readFile(texts_path);
                System.out.println("Текст для отправки: " + text_for_send);


                if (names.length == mails.length) {
                    for (int i = 0; i < names.length; i++) {
                        send_message(edit_text_for_send(names[i], text_for_send), mails[i], session, userLogin);
                    }
                } else {
                    System.out.println("Что-то не так с файлом mails_1.txt");
                }
                return "Отправлено";
            }else return "Не отправлено";
        }else return "Не отправлено";
    }
}