package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
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
        try(Session session = Util.getSession()) {
        session.beginTransaction();
        session.createSQLQuery(myTableName).addEntity(User.class).executeUpdate();
        session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String drop = "DROP TABLE IF EXISTS User ";
        try(Session session = Util.getSession()) {
        session.beginTransaction();
        session.createSQLQuery(drop).executeUpdate();
        session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = Util.getSession()) {
        session.beginTransaction();
        User user = new User(name,lastName,age);
        session.save(user);
        session.evict(user);
        session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        String sqlDeleteString = "delete User where id = :param";
        try(Session session = Util.getSession()) {
            session.beginTransaction();
            session.createQuery(sqlDeleteString)
                    .setParameter("param", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {
        String sql = "from User";

        try(Session session = Util.getSession()) {
        session.beginTransaction();
        List<User> list = session.createQuery(sql).list();
        session.getTransaction().commit();

        return list;
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = Util.getSession()) {
        session.beginTransaction();
        String hql = String.format("delete from User");
        session.createQuery(hql).executeUpdate();
        session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
