package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;

import java.util.List;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Kolya", "Ivanov", (byte) 5);
        userService.saveUser("Misha", "Ivanov", (byte) 6);
        userService.saveUser("Sasha", "Petrov", (byte) 5);
        userService.saveUser("Sasha", "Utochkin", (byte) 7);
        userService.saveUser("Kolya", "Ivanov", (byte) 5);

        List<User> allUsers = userService.getAllUsers();
        allUsers.forEach(u -> System.out.println(u.toString()));

        userService.removeUserById(3L);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
