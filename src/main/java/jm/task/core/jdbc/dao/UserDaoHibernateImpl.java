package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import jm.task.core.jdbc.util.Util;
import org.hibernate.JDBCException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {

    private static final Logger LOGGER = Logger.getLogger(UserDaoHibernateImpl.class.getName());

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS user "
                + "(ID BIGINT(100) NOT NULL PRIMARY KEY AUTO_INCREMENT, "
                + "name varchar(20) NOT NULL, lastname VARCHAR(40) NOT NULL, age integer(3) NOT NULL, "
                + "UNIQUE INDEX ID (name, lastname, age))";

        try(Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            transaction.commit();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS user";

        try(Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            transaction.commit();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        String addedUser = String.format("User name '%s %s' added in DB.", name, lastName);

        try(Session session = Util.getSessionFactory().openSession()) {
            if (session == null)
                return;
            Transaction transaction = session.beginTransaction();
            session.save(user);
            LOGGER.info(addedUser);
            transaction.commit();
        } catch (JDBCException e) {
            System.err.println("The DB has this user already");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
    }

    @Override
    public void removeUserById(long id) {
        String hql = "DELETE User where ID = :param";

        try(Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery(hql).setParameter("param", id).executeUpdate();
            transaction.commit();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
    }

    @Override
    public List<User> getAllUsers() {
        String hql = "FROM User";

        try(Session session = Util.getSessionFactory().openSession()) {
            return session.createQuery(hql, User.class).getResultList();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
    }

    @Override
    public void cleanUsersTable() {
        String hql = "DELETE FROM User";

        try(Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery(hql).executeUpdate();
            transaction.commit();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
    }
}
