package service;
import Object.UserOBJ;

public interface UserService {

    void viewInfo();
    void viewTransactionHistory();
    boolean login();
    boolean register();
    boolean deposit();
    boolean withdraw();
    boolean transfer();
    void setUser(UserOBJ user);
    void logout();

}
