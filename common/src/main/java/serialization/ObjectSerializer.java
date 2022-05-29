package serialization;

import java.io.*;

public class ObjectSerializer {

    public static byte[] toByteArray(Object obj) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        oos.flush();
        byte[] result = baos.toByteArray();
        baos.close();
        oos.close();
        return result;
    }

    public static <T> T fromByteArray(byte[] arr) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(arr);
            ObjectInputStream ois = new ObjectInputStream(bais);
            T result = (T) ois.readObject();
            ois.close();
            bais.close();
            return result;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

}
