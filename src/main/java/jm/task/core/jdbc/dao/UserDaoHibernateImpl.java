package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        String myTableName = "CREATE TABLE IF NOT EXISTS User (id INT NOT NULL AUTO_INCREMENT, " +
                "name VARCHAR(45) NOT NULL, lastName VARCHAR(45) NOT NULL, age INT NOT NULL, PRIMARY KEY (id))";
        Session session = Util.getSession();
        session.beginTransaction();
        session.createSQLQuery(myTableName).addEntity(User.class).executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void dropUsersTable() {
        String drop = "DROP TABLE IF EXISTS User ";
        Session session = Util.getSession();
        session.beginTransaction();
        session.createSQLQuery(drop).executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSession();
        session.beginTransaction();
        User user = new User(name,lastName,age);
        session.save(user);
        session.evict(user);
        session.getTransaction().commit();
    }

    @Override
    public void removeUserById(long id) {
        String sqlDeleteString = "delete User where id = :param";
        Session session = Util.getSession();
        session.beginTransaction();
        session.createQuery(sqlDeleteString)
                .setParameter("param", id)
                .executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "from User";
        Session session = Util.getSession();
        session.beginTransaction();
        List<User> list = session.createQuery(sql).list();
        session.getTransaction().commit();

        return list;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSession();
        session.beginTransaction();
        String hql = String.format("delete from User");
        session.createQuery(hql).executeUpdate();
        session.getTransaction().commit();
    }
}
