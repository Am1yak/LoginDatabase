package app;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegisterWindow {
    public static UserData getUser() {
        String[] userData = new String[3];

        Stage stage = new Stage();
        stage.setTitle("Register Window");
        VBox root = new VBox();
        stage.setScene(new Scene(root));

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        Label nickLabel = new Label("Nickname:");
        TextField nickField = new TextField();
        Label passwordLabel = new Label("Password:");
        TextField passwordField = new TextField();
        Button registerButton = new Button("Register");
        registerButton.setOnAction(e -> {
            userData[0] = nickField.getText();
            userData[1] = passwordField.getText();
            userData[2] = emailField.getText();
            stage.close();
        });
        root.getChildren().addAll(emailLabel,
                emailField,
                nickLabel,
                nickField,
                passwordLabel,
                passwordField,
                registerButton);
        stage.showAndWait();
        return new UserData(userData[0], userData[1], userData[2]);
    }
    
}
