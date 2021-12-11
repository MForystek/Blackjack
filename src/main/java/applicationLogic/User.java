package applicationLogic;

import gameLogic.players.Player;

public class User {
    //TODO Może by usunąć stąd nick i pobierać tą wartość po prostu z klasy Player?
    private String nick;
    private String password;
    private Player player;

    public boolean changePassword(String oldPassword, String newPassword) {
        if (!password.equals(oldPassword)) {
            return false;
        }
        password = newPassword;
        return true;
    }


    // --------------------------


    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
