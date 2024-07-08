package kata.pp.jdbc;

import kata.pp.jdbc.service.UserService;
import kata.pp.jdbc.service.UserServiceImpl;
import kata.pp.jdbc.model.User;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        User user1 = new User("Kolya", "Ivanov", (byte) 5);
        User user2 = new User("Sasha", "Ivanov", (byte) 6);
        User user3 = new User("Misha", "Petrov", (byte) 5);
        User user4 = new User("Grischa", "Utochkin", (byte) 7);
        User user5 = new User("Kolya", "Ivanov", (byte) 5);
        User[] users = {user1, user2, user3, user4, user5};

        userService.createUsersTable();

        for (User user : users) {
            userService.saveUser(user.getName(), user.getLastName(), user.getAge());
        }

        userService.getAllUsers();
        userService.removeUserById(3L);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
