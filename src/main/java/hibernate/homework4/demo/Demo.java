package hibernate.homework4.demo;

import hibernate.homework4.model.Session;
import hibernate.homework4.model.User;

import java.io.Console;

public class Demo {
    public static void main(String[] args) {
        User user = new User();
        Session session = new Session((long)1, "test", user);

    }
}
