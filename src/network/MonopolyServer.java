package network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.google.gson.Gson;
import control.MonopolyGame;
import control.action.Action;
import control.action.PassAction;
import entity.Player;
import entity.dice.DiceResult;
import entity.trade.OfferStatus;
import entity.trade.TradeOffer;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.*;

// 1. Player clicks at create new game and MonopolyServer gets created.
// 2. MonopolyClient object gets created for the owner and connects to the server
// 3. MonopolyServer class sends "buttons active" command to the client.
// 4. Every time a player joins, MonopolyServer class adds this player and sends the list to the clients
public class MonopolyServer {

    // Singleton
    private static MonopolyServer instance = null;

    Server server;
    private final Set<Connection> clients = new HashSet<>();
    private final Map<Connection, Player> registeredPlayer = new HashMap<>();
    private ArrayList<Player> players = new ArrayList<>();
    private MonopolyGame monopolyGame;
    private Connection activeConnection = null;
    boolean gameStarted = false;
    boolean serverClosed = false;
    boolean[] checkboxes = new boolean[3];
    ChatMessage chatLog;
    int playerCount;
    int maxId;

    private MonopolyServer() throws IOException {
        chatLog = new ChatMessage();

        server = new Server();
        serverClosed = false;
        initServer();
        server.bind(MonopolyNetwork.PORT);
        playerCount = 0;
        maxId = -1;

        MonopolyNetwork.register(server);
        System.out.println("IP Address: " + InetAddress.getLocalHost().getHostAddress());
        MonopolyNetwork.ipAddress = InetAddress.getLocalHost().getHostAddress();
        System.out.println("[SERVER] Server initialized and ready for connections...");

        server.addListener(new Listener() {
            @Override
            public void connected(Connection connection) {
                if (!clients.contains(connection)) {
                    clients.add(connection);
                }
                System.out.println("[SERVER] New client connected --> " + connection.getID());
                playerCount++;
            }

            @Override
            public void disconnected(Connection connection) {
                if (clients.contains(connection)) {
                    clients.remove(connection);
                }
                registeredPlayer.remove(connection);
                clients.remove(connection);
                playerCount--;

                ArrayList<Player> players = new ArrayList<>(registeredPlayer.values());
                server.sendToAllTCP(players);
                server.sendToAllTCP(checkboxes);
                server.sendToAllTCP("update lobby");
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
                        if (/*connection.equals(activeConnection)*/ true) {
                            if (o instanceof Action) {
                                System.out.println("[SERVER] got action");
                                server.sendToAllExceptTCP(connection.getID(), o);
                            }
                            else if (o instanceof String) {
                                String s = (String) o;
                                if (s.contains("next player:")) {
                                    int charPos = s.indexOf(":");
                                    int activePlayerIndex = Integer.parseInt(s.substring(charPos + 1));

                                    registeredPlayer.forEach( (c,player) -> {
                                        if (player.getPlayerId() == activePlayerIndex) {
                                            activeConnection = c;
                                        }
                                    });
                                    activeConnection.sendTCP("activate buttons"); // continue this method, inactive for other connections

                                    for (Connection c : clients) {
                                        if (activeConnection != c) {
                                            c.sendTCP("deactivate buttons");
                                            c.sendTCP("active player:" + activePlayerIndex);
                                        }
                                    }
                                }
                                else if (s.equals("get chat")) {
                                    connection.sendTCP(chatLog);
                                }
                                System.out.println("[SERVER] Message from " + connection.getID() + " --> " + s);
                            }
                            else if (o instanceof DiceResult) {
                                server.sendToAllTCP(o);
                            }
                            else if (o instanceof ChatMessage) {
                                ChatMessage chatMessage = (ChatMessage) o;
                                ArrayList<Player> players = new ArrayList<>(registeredPlayer.values());
                                Player player = registeredPlayer.get(connection);
                                chatLog.setMessage(chatLog.getMessage() + player.getName()
                                        + ": " +  chatMessage.getMessage() + "\n");
                                server.sendToAllTCP(chatLog);
                            }
                            else if (o instanceof TradeOffer) {
                                System.out.println("[SERVER] Got trade request");
                                TradeOffer tradeOffer = (TradeOffer) o;
                                if (tradeOffer.getStatus() == OfferStatus.AWAITING_RESPONSE ) {
                                    server.sendToTCP(tradeOffer.getReceiverID() + 1, o); // connection ids start from 1
                                }
                                else if (tradeOffer.getStatus() == OfferStatus.DECLINED ) {
                                    server.sendToTCP(tradeOffer.getSenderID() + 1, o);
                                }
                                else if (tradeOffer.getStatus() == OfferStatus.ACCEPTED ) {
                                    server.sendToAllTCP(tradeOffer);
                                }
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
                        else if(s.contains("player name")) {
                            String name = s.substring((s.indexOf(":") + 1));
                            System.out.println("name:" + name);
                            Player player = new Player(playerCount - 1, name, Player.Token.NONE, 0);
                            maxId = playerCount;
                            registeredPlayer.put(connection, player);

                            ArrayList<Player> players = new ArrayList<>(registeredPlayer.values());
                            server.sendToAllTCP(players);
                            server.sendToAllTCP(checkboxes);
                            server.sendToAllTCP("update lobby");
                        } else if (s.contains("leave lobby")) {
                            int id = Integer.parseInt(s.substring(s.indexOf(":") + 1));
                            System.out.println("player with id: " + id + " left the lobby");

                            ArrayList<Player> players = new ArrayList<>(registeredPlayer.values());
                            server.sendToAllTCP(players);
                            server.sendToAllTCP(checkboxes);
                            server.sendToAllTCP("update lobby");
                        }
                        else if (s.equals("server closed")) {
                            server.sendToAllTCP("server closed");
                            System.out.println("server closed");
                            server.stop();
                            gameStarted = false;
                            serverClosed = true;
                        }
                    }
                    else if (o instanceof boolean[]) {
                        boolean[] checkboxes = (boolean[]) o;
                        MonopolyServer.this.checkboxes = checkboxes;
                        server.sendToAllExceptTCP(connection.getID(), checkboxes);
                        server.sendToAllExceptTCP(connection.getID(), "update lobby");
                    }
                    else if (o instanceof Player) {
                        Player player = (Player) o;
                        registeredPlayer.put(connection, player);
                        server.sendToAllExceptTCP(connection.getID(), new ArrayList<Player>(registeredPlayer.values()));
                        server.sendToAllTCP("update lobby");
                    }
                }

            }
        });
    }

    public void initServer() throws IOException {
        server.start();
        server.bind(MonopolyNetwork.PORT);
        serverClosed = false;
    }

    public static MonopolyServer getInstance()
    {
        if (instance == null) {
            try {
                instance = new MonopolyServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return instance;
    }

    public void startGame() throws IOException {
        long seed = System.currentTimeMillis();
        ArrayList<Player> players = new ArrayList<Player>(registeredPlayer.values());
        System.out.println("Game starts with players --> " + players);

        // send this game to clients, or send the players and seed (more efficient)
        server.sendToAllTCP(players);
        server.sendToAllTCP(seed);
        // bind the ui to the game
        // start the game and get the first player
        //Player activePlayer = monopolyGame.startGame();
        // go to the game screen in clients
        server.sendToAllTCP("game started");
        //server.sendToAllTCP("Game started with these players --> " + players);
        //server.sendToAllTCP("Game started with this seed --> " + seed);
        gameStarted = true;

        //System.out.println(new Gson().toJson(players));
        //System.out.println(new Gson().toJson(monopolyGame.getBoard().getProperties()));

        int activePlayerIndex = new Random().nextInt(players.size());

        Player activePlayer = players.get(activePlayerIndex);
        registeredPlayer.forEach( (connection,player) -> {
            if (player.equals(activePlayer)) {
                activeConnection = connection;
            }
        });
        activeConnection.sendTCP("activate buttons"); // continue this method, inactive for other connections

        for (Connection c : clients) {
            if (activeConnection != c) {
                c.sendTCP("deactivate buttons");
                c.sendTCP("active player:" + activePlayer.getPlayerId());
            }
        }

    }

    public void stopGame() {
        server.stop(); // too harsh??
    }

    public static void main(String[] args) throws IOException {
        MonopolyServer monopolyServer = new MonopolyServer();
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public boolean isServerClosed() {
        return serverClosed;
    }
}
