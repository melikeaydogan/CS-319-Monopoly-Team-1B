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

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

public class MonopolyClient {

    Client client;
    Connection connection;
    String name;
    ArrayList<Player> players;
    long seed;
    MonopolyGame monopolyGame;

    boolean isConnected = false;
    boolean gameStarted = false;

    public MonopolyClient(String name) {
        this.name = name;
        client = new Client();
        new Thread(client).start(); // it keeps the client alive

        MonopolyNetwork.register(client);

        client.addListener(new Listener() {
            @Override
            public void connected(Connection c) {
                isConnected = true;
                connection = c;

                Player player = new Player(name, Player.Token.BATTLESHIP, 1);
                connection.sendTCP(player);
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
                            MonopolyGame.actionLog = new ActionLog();
                        }
                        action.act(); // how is it connected to the MonopolyGame class?
                        System.out.println(action);
                    }
                    else if (o instanceof String) {
                        String s = (String) o;
                        System.out.println("[SERVER] " + s);
                    }
                    else if (o instanceof Player) {
                        Player p = (Player) o;
                        System.out.println("[SERVER] sent the player --> " + p);
                        System.out.println("Player position " + p.getPosition());
                    }
                    else if (o instanceof Building) {
                        Building b = (Building) o;
                        System.out.println("[SERVER] sent the building --> " + b);
                    }
                    else if (o instanceof Card) {
                        Card c = (Card) o;
                        System.out.println("[SERVER] sent the card --> " + c);
                    }
                }
                else {
                    if (o instanceof ArrayList) {
                        players = (ArrayList<Player>) o;
                        System.out.println(players);
                    }
                    else if (o instanceof Long) {
                        Long longSeed = (Long) o;
                        seed = longSeed;
                    }
                    else if (o instanceof String) {
                        String message = (String) o;
                        if (message.equals("game started")) {
                            try {
                                monopolyGame = new MonopolyGame(players, seed); // what about ui?
                                gameStarted = true;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        else if (message.equals("buttons active")) {
                            // make the buttons active
                        }
                        else if (message.equals("buttons inactive")) {
                            // make the buttons inactive
                        }
                        else {
                            System.out.println("[SERVER] " + message);
                        }
                    }
                }

            }
        });
    }

    public void connect() {
        System.out.println("[CLIENT] Connecting to the server...");
        new Thread("Connect") {
            public void run () {
                try {
                    client.connect(5000, MonopolyNetwork.ipAddress, MonopolyNetwork.PORT);
                    // Server communication after connection can go here, or in Listener#connected().
                    System.out.println("[CLIENT] Connection successful to " + MonopolyNetwork.ipAddress + "!");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    System.exit(1);
                }
            }
        }.start();
    }

    public void sendAction(Action action) {

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

    public static void main(String[] args) {
        MonopolyClient client = new MonopolyClient("Mehmet");
        client.connect();
    }
}
