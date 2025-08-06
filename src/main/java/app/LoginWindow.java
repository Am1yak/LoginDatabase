package app;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginWindow {
    boolean flag = false;

    public boolean login(ObservableList<UserData> users) {
        Stage stage = new Stage();
        stage.setTitle("Login");
        flag = false;

        VBox vbox = new VBox();
        stage.setScene(new Scene(vbox));

        Label info = new Label("Login screen");
        Label username = new Label("Username");
        TextField user = new TextField();
        Label password = new Label("Password");
        TextField pass = new TextField();
        Button login = new Button("Login");
        login.setOnAction(e -> {
            for (UserData userData : users) {
                if (userData.username().equals(user.getText()) && userData.password().equals(pass.getText())) {
                    flag = true;
                    break;
                }
            }
            stage.close();
        });
        vbox.getChildren().addAll(info, username, user, password, pass, login);
        stage.showAndWait();
        return flag;
    }
}
