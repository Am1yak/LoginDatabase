package app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.collections.ObservableList;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class JsonSerializer {
    String filename = "data.json";

    public JsonSerializer() {
        File file = new File(filename);
        if (!(file.exists())) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void serialize(ObservableList<UserData> users) {
        try (Writer writer = new FileWriter(filename)){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(users, writer);
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public List<UserData> deserialize() {
        Gson gson = new Gson();
        Type listType = new TypeToken<ObservableList<UserData>>() {}.getType();

        List<UserData> users;
        try (FileReader reader = new FileReader(filename)){
            users = gson.fromJson(reader, listType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return users;
    }
}
