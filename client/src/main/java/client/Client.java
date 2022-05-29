package client;

import correspondency.CommandType;
import correspondency.RequestCo;
import correspondency.ResponseCo;
import exceptions.RecursionException;
import serialization.ObjectSerializer;
import storage.Worker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.*;

public class Client {

    private InetSocketAddress address;

    public Client(String host, int port) {
        this.address = new InetSocketAddress(host, port);
    }

    private ArrayList<Worker> objects_to_add;
    private String workingFile;

    public void setWorkingFile(String name) {
        workingFile = name;
    }

    public void setObjectsFromFile(ArrayList<Worker> list) {
        Collections.sort(list, new Comparator<Worker>() {
            @Override
            public int compare(Worker o1, Worker o2) {
                return (int) (o1.getId() - o2.getId());
            }
        });
        objects_to_add = list;
    }

    public void start() throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        channel.connect(address);
        Selector selector = Selector.open();
        channel.register(
                selector,
                SelectionKey.OP_READ |
                        SelectionKey.OP_WRITE |
                        SelectionKey.OP_CONNECT);
        System.out.println("Connected");
        while (true) {
            selector.select();
            for (SelectionKey key : selector.selectedKeys()) {
                if (!key.isValid())
                    continue;
                if (key.isConnectable()) {
                    if (!connect(key))
                        return;
                    System.out.println("User connected: " + key);
                }
                if (key.isReadable()) {
                    try {
                        getResponse(key);
                        continue;
                    }
                    catch (IOException ex) {
                        System.out.println("Server shut down");
                        return;
                    }
                }
                if (key.isWritable()) {
                    if (objects_to_add != null) {
                        sendObjectsRequest(key);
                        continue;
                    }
                    try {
                        sendRequest(key);
                    }
                    catch (IOException ex) {
                        System.out.println("Server shut down");
                        return;
                    }
                }

                //?????????????????????????????
                sleep();
            }
            selector.selectedKeys().clear();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void getResponse(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        int read = channel.read(buffer);
        if (read == -1) {
            channel.close();
            return;
        }
        byte[] data = new byte[read];
        System.arraycopy(buffer.array(), 0, data, 0, read);
        ResponseCo res = ObjectSerializer.fromByteArray(data);

        if (res == null) {
            System.out.println("Buffer out of bounds");
            return;
        }
        System.out.println(res.getResponseMessage());
        if (res.isExit())
            System.exit(0);
        if (res.isObjectNeeded()) {
            sendObjectRequest(key, res);
        }

        if (res.isExecuteScript()) {
            fileTobeProcessed = res.getExecute_file();
            is_exeRunning = true;
        }

    }

    private void sendObjectsRequest(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        channel.configureBlocking(false);


        RequestCo r = new RequestCo();
        r.setObjArray(this.objects_to_add);
        r.setWorkingFile(this.workingFile);

        byte[] arr = ObjectSerializer.toByteArray(r);
        channel.write(ByteBuffer.wrap(arr));
        sleep();
        this.objects_to_add = null;
    }

    private void sendObjectRequest(SelectionKey key, ResponseCo res) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        channel.configureBlocking(false);

        try {
            update_scanner();
        }
        catch (Exception ex) {
            clear_exec();
        }

        RequestCo r = new RequestCo().getRequestObject(curScanner);
        if (r == null) {
            return;
        }
        r.setCommandType(res.getCommandType());
        r.setId(res.getId());
        byte[] arr = ObjectSerializer.toByteArray(r);
        channel.write(ByteBuffer.wrap(arr));
        sleep();
    }


    private Scanner curScanner = new Scanner(System.in);
    private boolean is_exeRunning = false;
    private HashMap<File, Boolean> file_controller = new HashMap<>();
    private String fileTobeProcessed = null;
    private Stack<Scanner> scannerStack = new Stack<>();

    private void clear_exec() {
        curScanner = new Scanner(System.in);
        fileTobeProcessed = null;
        scannerStack.clear();
        file_controller.clear();
        is_exeRunning = false;
    }

    private void update_scanner() throws FileNotFoundException,
            RecursionException {
        if (is_exeRunning) {
            File f = new File(fileTobeProcessed);
            is_exeRunning = false;

            if (file_controller.containsKey(f))
                throw new RecursionException();
            file_controller.put(f, Boolean.TRUE);

            scannerStack.push(new Scanner(f));
            curScanner = scannerStack.peek();
        }
        else if (!scannerStack.isEmpty()) {
            if (!curScanner.hasNextLine())
                scannerStack.pop();
            if (!scannerStack.isEmpty())
                curScanner = scannerStack.peek();
            else
                clear_exec();
        }
    }

    private void sendRequest(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        channel.configureBlocking(false);

        try {
            update_scanner();
        }
        catch (FileNotFoundException | RecursionException ex) {
            clear_exec();
            System.out.println(ex.getMessage());
        }

        RequestCo r = new RequestCo().getRequestLine(curScanner);
        byte[] arr = ObjectSerializer.toByteArray(r);
        channel.write(ByteBuffer.wrap(arr));
    }

    private boolean connect(SelectionKey key) {
        SocketChannel sc = (SocketChannel) key.channel();
        try {
            while (sc.isConnectionPending()) {
                sc.finishConnect();
            }
        } catch (IOException e) {
            key.cancel();
            System.out.println("Could not connect to server");
            return false;
        }
        return true;
    }

}
