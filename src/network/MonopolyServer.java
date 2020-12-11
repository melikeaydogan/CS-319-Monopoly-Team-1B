package network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import control.MonopolyGame;
import control.action.Action;
import control.action.PassAction;
import entity.Player;

import java.io.IOException;
import java.util.*;

// 1. Player clicks at create new game and MonopolyServer gets created.
// 2. MonopolyClient object gets created for the owner and connects to the server
// 3. MonopolyServer class sends "buttons active" command to the client.
// 4. Every time a player joins, MonopolyServer class adds this player and sends the list to the clients
public class MonopolyServer {

    Server server;
    private final Set<Connection> clients = new HashSet<>();
    private final Map<Player, Connection> registeredPlayer = new HashMap<>();
    private MonopolyGame monopolyGame;
    private Connection activeConnection = null;
    boolean gameStarted = false;
    // LobbyController lobbyController

    public MonopolyServer() throws IOException {

        server = new Server();
        server.start();

        server.bind(MonopolyNetwork.PORT);

        MonopolyNetwork.register(server);
        System.out.println("[SERVER] Server initialized and ready for connections...");

        server.addListener(new Listener() {
            @Override
            public void connected(Connection connection) {
                if (!clients.contains(connection)) {
                    clients.add(connection);
                }
                System.out.println("[SERVER] New client connected --> " + connection.getID());
                connection.sendTCP("Hi connection number " + connection.getID() + "! :)");
                // lobbyController.update()
                // activeConnection = connection;
                ArrayList<Player> players = new ArrayList<>(registeredPlayer.keySet());
                connection.sendTCP(players);
//
//                if (clients.size() >= 2) {
//                    try {
//                        startGame(); // temporary
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
            }

            @Override
            public void disconnected(Connection connection) {
                if (clients.contains(connection)) {
                    clients.remove(connection);
                }
                System.out.println("[SERVER] Client disconnected --> " + connection.getID());
                registeredPlayer.values().remove(connection); // not sure whether it works or not
            }

            @Override
            public void idle(Connection connection) {
            }

            @Override
            public void received(Connection connection, Object o) {
                if (gameStarted) {
                    if (activeConnection == null) {
                        System.out.println("[SERVER] ERROR:Active connection is null!");
                    }
                    else {
                        if (connection.equals(activeConnection)) {
                            if (o instanceof Action) {
                                server.sendToAllExceptTCP(activeConnection.getID(), o);
                            }
                            else if (o instanceof String) {
                                String s = (String) o;
                                System.out.println("[SERVER] Message from " + connection.getID() + " --> " + s);
                            }
                        }
                        else {
                            if (o instanceof ChatMessage) {
                                server.sendToAllExceptTCP(connection.getID(), o);
                            }
                        }
                    }
                }
                else {
                    if (o instanceof String) {
                        String s = (String) o;
                        if (s.equals("start game")) {
                            try {
                                startGame();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    else if (o instanceof Player) {
                        Player player = (Player) o;
                        player.setPlayerId(connection.getID()); // the most convenient way for ids
                        System.out.println("[SERVER] Client sent the player -->" + player);
                        registeredPlayer.put(player, connection);

                        if (registeredPlayer.size() >= 2) {
                            try {
                                startGame(); // temporary, it needs ui confirmation
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

            }
        });
    }

    public void startGame() throws IOException {
        // ToDo get players from clients
        ArrayList<Player> players = new ArrayList<>(registeredPlayer.keySet());
        long seed = System.currentTimeMillis();
        monopolyGame = new MonopolyGame(players, seed);

        // send this game to clients, or send the players and seed (more efficient)
        server.sendToAllTCP(players);
        server.sendToAllTCP(seed);
        // bind the ui to the game
        // start the game, when start game??? create a command class for startgame or message as "start game"
        Player activePlayer = monopolyGame.startGame();
        // go to the game screen in clients
        server.sendToAllTCP("start game");
        server.sendToAllTCP("Game started with these players --> " + players);
        server.sendToAllTCP("Game started with this seed --> " + seed);
        gameStarted = true;
        activeConnection = registeredPlayer.get(activePlayer);
        activeConnection.sendTCP("activate buttons"); // continue this method, inactive for other connections

    }

    public void stopGame() {
        server.stop(); // too harsh??
    }

    public static void main(String[] args) throws IOException {
        MonopolyServer monopolyServer = new MonopolyServer();
    }

}
