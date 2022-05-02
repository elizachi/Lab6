package ru.itmo.server;

import ru.itmo.common.commands.CommandType;
import ru.itmo.common.exceptions.*;
import ru.itmo.common.requests.Request;
import ru.itmo.common.responses.Response;
import ru.itmo.server.utility.HandleCommands;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Server {
    private final HandleCommands commandManager = new HandleCommands();
    private Response response;

    private Selector selector;
    private final InetSocketAddress address;
    private final Set<SocketChannel> session;

    public Server(String host, int port) {
        this.address = new InetSocketAddress(host, port);
        this.session = new HashSet<SocketChannel>();
    }

    public void start() {
        try {
            selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(address);
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            ServerLauncher.log.info("Сервер запущен");

            //получение реквеста от клиента
            while(true) {
                selector.select();
                Iterator keys = selector.selectedKeys().iterator();
                Request request;
                while(keys.hasNext()) {
                    SelectionKey key = (SelectionKey) keys.next();
                    keys.remove();
                    if(!key.isValid()) {
                        continue;
                    } if(key.isAcceptable()) {
                        accept(key);
                    } else if(key.isReadable()) {
                        request = read(key);
                        //обработка реквеста
                        if (!request.getCommand().equals(CommandType.EXIT)) {
                            response = commandManager.handleRequest(request);
                        } else {
                            serverSocketChannel.close();
                            response = new Response(Response.Status.SERVER_EXIT, "Сервер завершает свою работу.");
                            commandManager.exit();
                        }
                        //отправка респонза клиенту
                        write(key);
                    }
                }
            }
        } catch (IOException | WrongArgumentException e) {
            System.err.println("Сервер завершает свою работу... :(");
            System.exit(0);
        }
    }

    private void accept(SelectionKey key) {
        try {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
            SocketChannel channel = serverSocketChannel.accept();
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ);
            session.add(channel);
            ServerLauncher.log.info("Клиент "+channel.socket().getRemoteSocketAddress()+ " зашёл на сервер");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Request read(SelectionKey key) throws WrongArgumentException {
        try {
            SocketChannel channel = (SocketChannel) key.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
            int amount = channel.read(byteBuffer);

            if(amount == -1) {
                session.remove(channel);
                ServerLauncher.log.info("Клиент "+channel.socket().getRemoteSocketAddress()+ " съебался");
                channel.close();
                key.cancel();
                return null;
            }

            byte[] data = new byte[amount];
            System.arraycopy(byteBuffer.array(), 0, data, 0, amount);
            String json = new String(data, StandardCharsets.UTF_8);
            return Request.fromJson(json);
        } catch (IOException e) {
            throw new WrongArgumentException(TypeOfError.CONNECTED_REFUSE);
        }
    }

    private void write(SelectionKey key) {
        SocketChannel channel = (SocketChannel) key.channel();
        try {
            //отправка респонза клиенту
            channel.write(ByteBuffer.wrap(response.toJson().getBytes(StandardCharsets.UTF_8)));
        } catch (IOException e) {
            System.err.println("Чота не получилосб...");
            System.exit(0);
        }
    }
}
