package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        final Logger LOGGER = Logger.getLogger(Main.class.getName());

        User user1 = new User("Kolya", "Ivanov", (byte) 5);
        User user2 = new User("Misha", "Ivanov", (byte) 6);
        User user3 = new User("Sasha", "Petrov", (byte) 5);
        User user4 = new User("Sasha", "Utochkin", (byte) 7);
        User user5 = new User("Kolya", "Ivanov", (byte) 5);
        User[] users = {user1, user2, user3, user4, user5};

        userService.createUsersTable();

        for (User user : users) {
            userService.saveUser(user.getName(), user.getLastName(), user.getAge());
            String addedUser = String.format("User name '%s %s' added in DB.", user.getName(), user.getLastName());
            LOGGER.info(addedUser);
        }

        userService.getAllUsers();
        userService.removeUserById(3L);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
