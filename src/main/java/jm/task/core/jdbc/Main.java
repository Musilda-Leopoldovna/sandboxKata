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
        //System.out.println("User с именем <" + user.getName() + " " + user.getLastName() + "> добавлен в базу данных");
        userService.saveUser("Misha", "Ivanov", (byte) 6);
        //System.out.println("User с именем <" + user.getName() + " " + user.getLastName() + "> добавлен в базу данных");
        userService.saveUser("Sasha", "Petrov", (byte) 5);
        //System.out.println("User с именем <" + user.getName() + " " + user.getLastName() + "> добавлен в базу данных");
        userService.saveUser("Sasha", "Utochkin", (byte) 7);
        //System.out.println("User с именем <" + user.getName() + " " + user.getLastName() + "> добавлен в базу данных");
        userService.saveUser("Kolya", "Ivanov", (byte) 5);
        //System.out.println("User с именем <" + user.getName() + " " + user.getLastName() + "> добавлен в базу данных");

        List<User> allUsers = userService.getAllUsers();
        allUsers.forEach(u -> System.out.println(u.toString()));

        userService.removeUserById(3L);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
