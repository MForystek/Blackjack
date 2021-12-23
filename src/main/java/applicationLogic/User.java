package applicationLogic;

import gameLogic.players.Player;

public class User {
    private String password;
    private Player player;

    public User(String nick, String password){
        this.player = new Player(nick);
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


    public String getNick() {
        return this.player.getNick();
    }

    public void setNick(String nick) {
        this.player.setNick(nick);
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
