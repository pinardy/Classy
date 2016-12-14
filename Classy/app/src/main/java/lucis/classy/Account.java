package lucis.classy;

/**
 * Created by muayanfrost on 13/12/16.
 */

public class Account {
    private String username, password;
    private boolean accessRights;

    public Account(String username, String password, boolean accessRights) {
        this.username = username;
        this.password = password;
        this.accessRights = accessRights;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAccessRights() {
        return accessRights;
    }

    public void setAccessRights(boolean accessRights) {
        this.accessRights = accessRights;
    }

    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", accessRights=" + accessRights +
                '}';
    }
}
