package com.example.mails_gradle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.mail.MessagingException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Контроллер для GUI
 */
public class MailController implements Initializable {
    /**
     * Комбобокс для текстов сообщений
     */
    public ComboBox<String> texts;
    /**
     * Комбобокс для мейлов
     */
    public ComboBox<String> mails;
    /**
     * Поле для пароля
     */
    public TextField pass_field;
    /**
     * Поле текста для вывода
     */
    @FXML
    private Label Text;

    /**
     * Срабатывает, когда нажата кнопка
     */
    @FXML
    protected void onHelloButtonClick()  {
        if (pass_field.getText() != ""){
            Text.setText(MailSpam.mail(mails.getValue(),texts.getValue(),pass_field.getText()));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mails.getItems().setAll(Texts_and_mails.mails);
        texts.getItems().setAll(Texts_and_mails.texts);
    }
}