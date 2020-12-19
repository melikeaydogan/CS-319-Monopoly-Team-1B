package network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import control.ActionLog;
import control.MonopolyGame;
import control.action.Action;
import entity.Player;
import entity.dice.DiceResult;
import gui.GameScreenController;
import gui.LobbyController;
import javafx.application.Platform;

import java.io.IOException;
import java.util.*;

// create this when player joins to the lobby

// 1. client enters the ip address and clicks join game
// 2. MonopolyClient object gets created and logins into the specified IP Address
// 3. If login successful, MonopolyClient sends a player to the server and gets players from the server
// 4. UI switches to Lobby and LobbyController gets this players array
// 5. If a new player joins, server sends the array and MonopolyClient class updates the
//    LobbyController
public class MonopolyClient {

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

    // Chat
    ChatMessage chatLog;

    public MonopolyClient(LobbyController lobbyController) {
        chatLog = new ChatMessage();
        name = "";
        client = new Client();
        new Thread(client).start(); // it keeps the client alive
        this.lobbyController = lobbyController;
        this.gameScreenController = null;

        MonopolyNetwork.register(client);

        client.addListener(new Listener() {
            @Override
            public void connected(Connection c) {
                isConnected = true;
                connection = c;

                connection.sendTCP("player name: " + name);
            }

            @Override
            public void idle(Connection connection) {

            }

            @Override
            public void disconnected(Connection c) {
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
                        System.out.println("[CLIENT] Got action");
                        action.act(); // how is it connected to the MonopolyGame class?
                        Platform.runLater(() -> {
                            gameScreenController.updateBoardState();
                        });
                    }
                    else if (o instanceof ChatMessage) {
                        ChatMessage message = (ChatMessage) o;
                        Platform.runLater(() -> {
                            gameScreenController.updateChat(message);
                        });
                    }
                    else if (o instanceof ArrayList) {
                        players = (ArrayList<Player>) o;
                        monopolyGame.getPlayerController().setPlayers(players);
                        gameScreenController.updateBoardState();
                    }
                    else if (o instanceof DiceResult) {
                        DiceResult result = (DiceResult) o;
                        gameScreenController.setDiceLabel(result);
                    }
                    else if (o instanceof String) {
                        String s = (String) o;
                        if (s.equals("activate buttons")) {
                            Platform.runLater(() -> {
                                gameScreenController.activateButtons();
                            });
                            System.out.println("[SERVER] Activate buttons");
                        }
                        else if (s.equals("deactivate buttons")) {
                            gameScreenController.deactivateButtons();
                            System.out.println("[SERVER] Deactivate buttons");
                        }
                        else if (s.contains("active player:")) {
                            int charPos = s.indexOf(":");
                            int activePlayerIndex = Integer.parseInt(s.substring(charPos + 1));
                            gameScreenController.getGame().getPlayerController().setActivePlayerIndex(activePlayerIndex);
                            System.out.println("new active player: " + activePlayerIndex);
                            Platform.runLater(() -> gameScreenController.updateBoardState());
                        }
                        else {
                            System.out.println("[SERVER] " + s);
                        }
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
                                gameStarted = true;
                        } // Activate and deactivate buttons don't need to be here
                        else if (message.equals("update lobby")) {
                            lobbyController.updateLobbyState(MonopolyClient.this);
                        }
                        else if (message.contains("active player:")) {
                            int id = Integer.parseInt(message.substring(message.indexOf(":") + 1));
                            gameScreenController.getGame().getPlayerController().setActivePlayerIndex(id);
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

    public void sendStartGameCommand() {
        connection.sendTCP("start game");
    }

    public void sendAction(Action action) {
        connection.sendTCP(action);
    }

    public void sendLeftLobby(int playerId) {
        connection.sendTCP("leave lobby: " + playerId);
    }

    public void sendEndLobby() {
        // TODO: ends the lobby for all and terminates the server
        // lobbyController.leaveLobby() for all players
    }

    public void disconnect() {
        // TODO: ends connection.
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

    public ChatMessage getChatLog() {
        return chatLog;
    }

    public void setChatLog(ChatMessage chatLog) {
        this.chatLog = chatLog;
    }

    public MonopolyGame getMonopolyGame() {
        return monopolyGame;
    }

    public void setupMonopolyGame(GameScreenController gsc) {
        try {
            this.gameScreenController = gsc;
            this.monopolyGame = new MonopolyGame(players, gsc);
            this.seed = monopolyGame.getDice().getGameSeed();
            gsc.setMonopolyClient(this);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void updateLobbyControllers() {
        boolean[] checkboxes = new boolean[]{alliance, speedDie, privateLobby};
        Player player = players.get(getId());
        client.sendTCP(checkboxes);
        client.sendTCP(player);
    }

    public void updateGameScreenControllers() {
        // TODO: This sends a message to all to update their gameScreenController object
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

    public void sendObject(Object o) {
        connection.sendTCP(o);
    }
}
