package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import org.hibernate.*;

import java.io.IOException;
import java.util.List;
import java.util.logging.*;

public class UserDaoHibernateImpl implements UserDao {
    private final Util util = new Util();
    private static final Logger LOGGER = Logger.getLogger(UserDaoHibernateImpl.class.getName());
    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Session session = util.getHibernateConnect().openSession()){
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery("CREATE TABLE IF NOT EXISTS user");
            transaction.commit();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS user";
        try (Session session = util.getHibernateConnect().openSession()){
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            transaction.commit();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try(Session session = util.getHibernateConnect().openSession()) {
            if (session == null)
                return;
            Transaction transaction = session.beginTransaction();
            session.save(user);
            LOGGER.log(Level.INFO, "User name '" + name + " " + lastName + "' added in DB.");
            transaction.commit();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
    }

    @Override
    public void removeUserById(long id) {
        String hql = "DELETE User where ID = :param";
        try(Session session = util.getHibernateConnect().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery(hql).setParameter("param", id).executeUpdate();
            transaction.commit();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
    }

    @Override
    public List<User> getAllUsers() {
        try(Session session = util.getHibernateConnect().openSession()){
            return session.createQuery("FROM User", User.class).getResultList();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = util.getHibernateConnect().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            transaction.commit();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
    }
}
