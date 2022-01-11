package applicationLogic;

import gameLogic.players.Player;

public class User extends Player {
    private String password;

    public User(String nick, String password) {
        super(nick);
        this.password = password;
    }

    public boolean changePassword(String oldPassword, String newPassword) {
        if (!password.equals(oldPassword)) {
            return false;
        }
        password = newPassword;
        return true;
    }


    // --------------------------


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
