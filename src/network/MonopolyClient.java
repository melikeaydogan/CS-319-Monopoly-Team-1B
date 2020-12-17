package network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import control.ActionLog;
import control.MonopolyGame;
import control.action.Action;
import entity.Player;
import entity.card.Card;
import entity.property.Building;
import gui.GameScreenController;
import gui.LobbyController;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

// create this when player joins to the lobby

// 1. client enters the ip address and clicks join game
// 2. MonopolyClient object gets created and logins into the specified IP Address
// 3. If login successful, MonopolyClient sends a player to the server and gets players from the server
// 4. UI switches to Lobby and LobbyController gets this players array
// 5. If a new player joins, server sends the array and MonopolyClient class updates the
//    LobbyController
public class MonopolyClient {
    // Singleton
    private static MonopolyClient instance = null;

    // Connection members
    boolean isConnected = false;
    Client client;
    Connection connection;

    // Control members
    boolean gameStarted = false;
    GameScreenController gameScreenController;
    LobbyController lobbyController;

    // Lobby members
    String name;
    ArrayList<Player> players;
    boolean alliance ,speedDie, privateLobby;

    // Game members
    long seed;
    MonopolyGame monopolyGame;

    public MonopolyClient(LobbyController lobbyController) {
        name = "";
        client = new Client();
        new Thread(client).start(); // it keeps the client alive
        this.lobbyController = lobbyController;
        this.gameScreenController = null;
        instance = this;

        MonopolyNetwork.register(client);

        client.addListener(new Listener() {
            @Override
            public void connected(Connection c) {
                // TODO: A default token and default team needs to be assigned.
                isConnected = true;
                connection = c;

                //Player player = new Player(name, Player.Token.BATTLESHIP, 1);
                connection.sendTCP("player name: " + name);
            }

            @Override
            public void idle(Connection connection) {

            }

            @Override
            public void disconnected(Connection c) {
                // TODO: How to remove the disconnected player?
                isConnected = false;
                connection = null;
            }

            @Override
            public void received(Connection connection, Object o) {
                if (gameStarted) {
                    if (o instanceof Action) {
                        Action action = (Action) o;
                        if (Objects.isNull(MonopolyGame.getActionLog())) {
                            MonopolyGame.actionLog = ActionLog.getInstance();
                        }
                        action.act(); // how is it connected to the MonopolyGame class?
                        System.out.println(action);
                    }
                    else if (o instanceof ChatMessage) {
                        ChatMessage message = (ChatMessage) o;
                        // gameScreenController.addChatMessage(message);
                    }
                    else if (o instanceof String) {
                        String s = (String) o;
                        System.out.println("[SERVER] " + s);
                    }
                }
                else {
                    if (o instanceof ArrayList) {
                        players = (ArrayList<Player>) o;
                        players.sort(Comparator.comparing(Player::getPlayerId));
                        lobbyController.updateLobbyState(MonopolyClient.this);
                    }
                    else if (o instanceof Player[]) {
                        Player[] playerArray = (Player[]) o;
                        players = new ArrayList<Player>(Arrays.asList(playerArray));
                        players.sort(Comparator.comparing(Player::getPlayerId));
                    }
                    else if (o instanceof Long) {
                        seed = (Long) o;
                    }
                    else if (o instanceof String) {
                        String message = (String) o;
                        if (message.equals("game started")) {
                                lobbyController.startGame();
                        } // Activate and deactivate buttons don't need to be here
                        else if (message.equals("update lobby")) {
                            lobbyController.updateLobbyState(MonopolyClient.this);
                        }
                        else {
                            System.out.println("[SERVER] " + message);
                        }
                    }
                    else if (o instanceof boolean[]) {
                        boolean[] checkboxes = (boolean[]) o;

                        alliance = checkboxes[0];
                        speedDie = checkboxes[1];
                        privateLobby = checkboxes[2];
                    }
                }

            }
        });
    }

    public void connect(String ipAddress, String name) {
        this.name = name;
        System.out.println("[CLIENT] Connecting to the server...");
        MonopolyNetwork.ipAddress = ipAddress;
        //new Thread("Connect") {
        //    public void run () {
                try {
                    client.connect(5000, MonopolyNetwork.ipAddress, MonopolyNetwork.PORT);
                    // Server communication after connection can go here, or in Listener#connected().
                    System.out.println("[CLIENT] Connection successful to " + MonopolyNetwork.ipAddress + "!");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    System.exit(1);
                }
        //    }
        //}.start();

    }

    public static MonopolyClient getInstance() {
        return instance;
    }

    public void sendStartGameCommand() {
        connection.sendTCP("start game");
    }

    public void sendAction(Action action) {
        connection.sendTCP(action);
    }

    public void sendChatMessage(ChatMessage chatMessage) {
        connection.sendTCP(chatMessage);
    }

    public void sendString(String s) { // can be used for commands
        connection.sendTCP(s);
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getId() {
        //for (int i = 0; i < players.size(); i++) {
        //    if (players.get(i).getName().equals(name))
        //        return i;
        //}
        //return -1;

        return connection.getID() - 1;
    }

    public MonopolyGame getMonopolyGame() {
        return monopolyGame;
    }

    public void setupMonopolyGame(GameScreenController gsc, ArrayList<Player> players) {
        try {
            this.gameScreenController = gsc;
            this.monopolyGame = new MonopolyGame(players, gsc);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void updateLobbyControllers() {
        // TODO: his sends a message to all to update their lobbyController object
        //  Do this using lobbyController.updateLobbyState() method
        boolean[] checkboxes = new boolean[]{alliance, speedDie, privateLobby};
        Player player = players.get(getId());
        client.sendTCP(checkboxes);
        client.sendTCP(player);
    }

    public void updateGameScreenControllers() {
        // TODO: his sends a message to all to update their lobbyController object
        //  Do this using gameScreenController.updateBoardState() method

    }

    public boolean getSpeedDie() {
        return speedDie;
    }

    public boolean getAlliance() {
        return alliance;
    }

    public boolean getPrivateLobby() {
        return privateLobby;
    }

    public void setAlliance(boolean alliance) {
        this.alliance = alliance;
    }

    public void setPrivateLobby(boolean privateLobby) {
        this.privateLobby = privateLobby;
    }

    public void setSpeedDie(boolean speedDie) {
        this.speedDie = speedDie;
    }
}
