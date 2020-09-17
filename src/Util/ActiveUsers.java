package Util;

import java.util.HashSet;

public class ActiveUsers {
    private HashSet<UserValidator> activeUsers = new HashSet<>();

    private ActiveUsers(){
        activeUsers = new HashSet<UserValidator>();
    }

    public boolean containsUserName(String name) {
        return activeUsers.stream().anyMatch(user -> user.getUsername().equals(name));
    }

    //добавляем пользователя в активные
    public boolean addUser(UserValidator user) {
        if (activeUsers.contains(user)) return false;
        activeUsers.add(user);
        return true;
    }
    //удаление пользователя из активных
    public void removeUser(UserValidator user) {
        activeUsers.remove(user);
    }
}
