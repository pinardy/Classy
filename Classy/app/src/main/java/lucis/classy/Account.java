package lucis.classy;

/**
 * Created by muayanfrost on 13/12/16.
 */

public class Account {
    String username, password;
    boolean permission;

    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", permission=" + permission +
                '}';
    }
}
