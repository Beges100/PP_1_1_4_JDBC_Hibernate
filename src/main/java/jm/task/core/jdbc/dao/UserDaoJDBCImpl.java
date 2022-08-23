package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String myTableName = "CREATE TABLE IF NOT EXISTS User (id INT NOT NULL AUTO_INCREMENT, " +
                "name VARCHAR(45) NOT NULL, lastName VARCHAR(45) NOT NULL, age INT NOT NULL, PRIMARY KEY (id))";
        try {
            Statement statement = Util.getConnection().createStatement();
            statement.executeUpdate(myTableName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void dropUsersTable() {
        String drop = "DROP TABLE IF EXISTS User ";
        try {
            Statement statement = Util.getConnection().createStatement();
            statement.executeUpdate(drop);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String addUser = "INSERT INTO User (name, lastName, age) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = Util.getConnection().prepareStatement(addUser);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String del = "DELETE from User where id=" + id;
        try {
            Statement statement = Util.getConnection().createStatement();
            statement.executeUpdate(del);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String view = "SELECT * FROM User";
        try {
            Statement statement = Util.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(view);

            while (resultSet.next()) {
                User user = new User();
                user.setId((long) resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                list.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void cleanUsersTable() {
        String delUser = "DELETE from User";
        try {
            Statement statement = Util.getConnection().createStatement();
            statement.executeUpdate(delUser);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
