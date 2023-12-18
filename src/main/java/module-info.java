/**
 * Выдача доступа к библиотекам
 */
module com.example.mails_gradle {
    requires javafx.controls;
    requires javafx.fxml;
    requires mail;
    requires activation;


    opens com.example.mails_gradle to javafx.fxml;
    exports com.example.mails_gradle;
}