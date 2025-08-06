package app;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    ObservableList<UserData> users = FXCollections.observableArrayList();
    ListView<UserData> listView;
    Label info_label;
    JsonSerializer jsonSerializer = new JsonSerializer();
    SQLHandler handler = new SQLHandler();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login Database");
        BorderPane root = new BorderPane();
        root.setTop(config_box_init());

        HBox mainBox = new HBox();
        mainBox.getChildren().addAll(list_box_init(), info_box_init());
        users.addAll(jsonSerializer.deserialize());
        root.setBottom(mainBox);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private HBox config_box_init(){
        HBox config_box = new HBox();
        config_box.setSpacing(10);
        config_box.setPadding(new Insets(10, 10, 10, 10));
        config_box.setStyle("-fx-background-color: #336699");

        Button login_button = new Button("Login");
        login_button.setOnAction(e -> {
            LoginWindow loginWindow = new LoginWindow();
            boolean flag = loginWindow.login(users);
            if (flag) {
                System.out.println("Login Successful");
            } else {
                System.out.println("Login Failed");
            }
        });
        config_box.getChildren().add(login_button);

        Button reg_button = new Button("Register");
        reg_button.setOnAction(e -> {
            UserData user = RegisterWindow.getUser();
            users.add(user);
            jsonSerializer.serialize(users);
            handler.db_insert(users.getLast());
            // handler.db_remove(users);
        });
        config_box.getChildren().add(reg_button);

        Button remove_button = new Button("Remove");
        remove_button.setOnAction(e -> {
            int selectedIndex = listView.getSelectionModel().getSelectedIndex();
            users.remove(selectedIndex);
            jsonSerializer.serialize(users);
        });
        config_box.getChildren().add(remove_button);

        Button refresh_button = new Button("Refresh");
        refresh_button.setOnAction(e -> {
            users = handler.db_update_from();
            System.out.println("Update Successful");
        });
        config_box.getChildren().add(refresh_button);

        return config_box;
    }

    private VBox list_box_init(){
        VBox list_box = new VBox();
        listView = new ListView<>(users);
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            info_label.setText("Info" +
                    "\nEmail: " + newValue.email() +
                    "\nPassword: " + newValue.password());
        });
        list_box.getChildren().add(listView);

        return list_box;
    }

    private VBox info_box_init(){
        VBox info_box = new VBox();
        info_box.setSpacing(10);
        info_box.setPadding(new Insets(10, 10, 10, 10));
        info_box.setStyle("-fx-background-color: #399");

        info_label = new Label("Info" +
                "\nEmail:" +
                "\nPassword:");
        info_box.getChildren().add(info_label);

        return info_box;
    }
}


