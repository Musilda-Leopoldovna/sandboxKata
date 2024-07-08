package kata.pp.jdbc.service;

import kata.pp.jdbc.dao.UserDao;
import kata.pp.jdbc.dao.UserDaoHibernateImpl;
import kata.pp.jdbc.dao.UserDaoJDBCImpl;
import kata.pp.jdbc.model.User;

import java.util.List;
import java.util.logging.Logger;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = new UserDaoHibernateImpl();
    final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());
    public void createUsersTable() {
        userDao.createUsersTable();
    }

    public void dropUsersTable() {
        userDao.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        userDao.saveUser(name, lastName, age);
        String addedUser = String.format("User name '%s %s' added in DB.", name, lastName);
        LOGGER.info(addedUser);
    }

    public void removeUserById(long id) {
        userDao.removeUserById(id);
    }

    public List<User> getAllUsers() {
        List<User> allUsers = userDao.getAllUsers();
        allUsers.forEach(u -> System.out.println(u.toString()));
        return allUsers;
    }

    public void cleanUsersTable() {
        userDao.cleanUsersTable();
    }
}
