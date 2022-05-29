package server;

import command.*;
import correspondency.CommandType;
import correspondency.RequestCo;
import correspondency.ResponseCo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import serialization.ObjectSerializer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer {

    private Selector selector;
    private InetSocketAddress address;
    private SocketChannel session;
    private String workingFile = null;

    private ExecutorService ctpService = Executors.newCachedThreadPool();

    private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);

    public SocketServer(String host, int port) {
        this.address = new InetSocketAddress(host, port);
    }

    public void start() throws IOException {
        this.selector = Selector.open();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(this.address);
        ssc.configureBlocking(false);
        ssc.register(this.selector, SelectionKey.OP_ACCEPT);
        logger.info("Server started...");

        new Thread(ServerConsole::read).start();

        while (true) {
            this.selector.select();
            for (SelectionKey key : this.selector.selectedKeys()) {
                if (!key.isValid())
                    continue;
                if (key.isAcceptable()) {
                    try {
                        this.accept(key);
                    }
                    catch (IOException ex) {
                        logger.info("Client cannot be accepted");
                        break;
                    }
                }
                else if (key.isReadable()) {
                    try {
                        this.read(key);
                    }
                    catch (IOException ex) {
                        logger.info("Client disconnected");
                        this.session.close();
                        break;
                    }
                }
            }
            this.selector.selectedKeys().clear();
        }
    }

    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
        SocketChannel channel = ssc.accept();
        channel.configureBlocking(false);
        channel.register(this.selector, SelectionKey.OP_READ);
        if (this.session != null)
            this.session.close();
        this.session = channel;
        logger.info("accepted " + this.session);
    }

    class ProcessRequest implements Runnable {

        private RequestCo req;
        private SelectionKey key;

        public ProcessRequest setRequest(RequestCo req) {
            this.req = req;
            return this;
        }

        public ProcessRequest setKey(SelectionKey key) {
            this.key = key;
            return this;
        }

        @Override
        public void run() {
            if (req == null)
                return;

            ResponseCo res = null;
            if (req.hasWorkingFile()) {
                workingFile = req.getWorkingFile();
                CommandSave.setPath(workingFile);
            }
            if (req.hasArray()) {
                req.getObjArray().forEach(CommandAdd::addWorker);
                res = new ResponseCo("Successfully added "
                        + req.getObjArray().size()
                        + " elements");
            } else if (req.hasObject()) {

                if (req.getCommandType().equals(CommandType.ADD)) {
                    CommandAdd.addWorker(req.getObj());
                    res = new ResponseCo("Successfully added");
                } else if (req.getCommandType().equals(CommandType.ADDIFMIN)) {
                    res = new ResponseCo(
                            CommandAddIfMin.addWorker(req.getObj())
                    );
                } else if (req.getCommandType().equals(CommandType.UPDATEID)) {
                    res = new ResponseCo(
                            CommandUpdateId.updateId(req.getObj(), req.getId())
                    );
                }

            } else {
                Command c = Invoker.launch(req);
                if (c != null) {
                    res = c.getResponse();
                } else {
                    res = new ResponseCo("Wrong command");
                }
            }
            sendResponse(key, res);

            logger.info("Got: " + req.getLine());
        }

    }

    private void read(SelectionKey key) throws IOException {

        ctpService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(2048);
                    int read = channel.read(buffer);
                    if (read == -1) {
                        channel.close();
                        return;
                    }
                    byte[] data = new byte[read];
                    System.arraycopy(buffer.array(), 0, data, 0, read);
                    RequestCo req = ObjectSerializer.fromByteArray(data);

                    new Thread(new ProcessRequest().setRequest(req).setKey(key)).start();

                }
                catch (IOException ex) {
                    logger.info("Client disconnected");
                    try {
                        session.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void sendResponse(SelectionKey key,
                              ResponseCo response) {
        ctpService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    SocketChannel channel = (SocketChannel) key.channel();
                    channel.configureBlocking(false);

                    byte[] arr = ObjectSerializer.toByteArray(response);
                    channel.write(ByteBuffer.wrap(arr));
                }
                catch (IOException ex) {
                    logger.info("Client disconnected");
                    try {
                        session.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}
