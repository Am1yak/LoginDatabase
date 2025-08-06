package app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Properties;

public class SQLHandler {
    private Connection con;

    public SQLHandler() {
        try {
            con = getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        ConnectionData connectionData = new ConnectionData();
        Properties connectionProps = new Properties();
        connectionProps.put("user", connectionData.userName);
        connectionProps.put("password", connectionData.password);

        Connection conn = DriverManager.getConnection(
                "jdbc:" + connectionData.dbms + "://" +
                            connectionData.serverName +
                            ":" + connectionData.portNumber + "/"
                            + connectionData.databaseName,
                    connectionProps);
        System.out.println("Connected to database");

        return conn;
    }

    public void db_asset(ObservableList<UserData> list){
        String query = "select * from logins";

        try (Statement statement = con.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                String userName = rs.getString("username");
                String password = rs.getString("password");
                String email = rs.getString("email");

                UserData user = new UserData(userName, password, email);
                if(list.contains(user)){
                    list.remove(user);
                }
            }

            String sql = "insert into logins (username, password, email) values";
            for (UserData user : list) {
                sql += "(\""+ user.username() + "\", \"" + user.password() + "\", \"" + user.email() + "\"),";
            }
            sql = sql.substring(0, sql.length()-1) + ";";
            statement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void db_insert(UserData user){
        String query = "insert into logins (username, password, email) values (?, ?, ?)";

        try (PreparedStatement st = con.prepareStatement(query)){
            st.setString(1, user.username());
            st.setString(2, user.password());
            st.setString(3, user.email());
            st.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void db_remove(ObservableList<UserData> list){
        String query = "Select * from logins";

        try (Statement st = con.createStatement()){
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                String userName = rs.getString("username");
                String password = rs.getString("password");
                String email = rs.getString("email");
                UserData user = new UserData(userName, password, email);

                if(!list.contains(user)){
                    String sql = "delete from logins where email = ?";
                    try (PreparedStatement st2 = con.prepareStatement(sql)){
                        st2.setString(1, email);
                        st2.executeUpdate();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<UserData> db_update_from(){
        String sql = "select * from logins";
        ObservableList<UserData> list = FXCollections.observableArrayList();
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new UserData(rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email")));
                System.out.println("User added: " + rs.getString("username") + "\n Password: " + rs.getString("password"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}