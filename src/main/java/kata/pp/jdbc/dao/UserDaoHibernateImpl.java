package kata.pp.jdbc.dao;

import kata.pp.jdbc.model.User;
import kata.pp.jdbc.util.Util;

import org.hibernate.JDBCException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {

    private static final Logger LOGGER = Logger.getLogger(UserDaoHibernateImpl.class.getName());

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS user "
                + "(ID BIGINT(100) PRIMARY KEY AUTO_INCREMENT, "
                + "name varchar(20) NOT NULL, lastname VARCHAR(40) NOT NULL, age integer(3) NOT NULL, "
                + "UNIQUE INDEX ID (name, lastname, age))";

        try(Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS user";

        try(Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);

        try(Session session = Util.getSessionFactory().openSession()) {
            if (session == null)
                return;
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (JDBCException e) {
            LOGGER.warning("The DB has this user already");
        }
    }

    @Override
    public void removeUserById(long id) {
        String hql = "DELETE User where ID = :param";

        try(Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery(hql).setParameter("param", id).executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        String hql = "FROM User ORDER BY ID";

        try(Session session = Util.getSessionFactory().openSession()) {
            return session.createQuery(hql, User.class).getResultList();
        }
    }

    @Override
    public void cleanUsersTable() {
        String hql = "DELETE FROM User";

        try(Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery(hql).executeUpdate();
            transaction.commit();
        }
    }
}
