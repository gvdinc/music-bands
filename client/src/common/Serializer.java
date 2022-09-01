package common;

import java.io.*;

public class Serializer {
    public static final int MAX_SIZE = 32 * 1024;
    public static final int PORT = 8181;


    public static ByteArrayOutputStream serialize(Object request) {
        ObjectOutputStream oos;
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream(MAX_SIZE);
            oos = new ObjectOutputStream(bos);
            oos.writeObject(request);
            oos.close();
            return bos;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bos;
    }

    public static Object deserialize(byte[] request) {
        ObjectInputStream ois;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(request, 0, request.length);
            ois = new ObjectInputStream(bis);
            Object object = ois.readObject();
            ois.close();
            return object;
        } catch (EOFException e) {
            System.out.println("Not enough memory");
            return null;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Serialization error. Class was not found");
            //e.printStackTrace();
            //System.exit(0);
            return null;
        }
    }
}
