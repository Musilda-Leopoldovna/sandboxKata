package kata.pp.jdbc.service;

import kata.pp.jdbc.dao.UserDao;
import kata.pp.jdbc.dao.UserDaoHibernateImpl;
import kata.pp.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = new UserDaoHibernateImpl();
    public void createUsersTable() {
        userDao.createUsersTable();
    }

    public void dropUsersTable() {
        userDao.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        userDao.saveUser(name, lastName, age);
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
