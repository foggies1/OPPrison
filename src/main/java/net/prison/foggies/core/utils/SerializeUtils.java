package net.prison.foggies.core.utils;

import java.io.*;
import java.util.Base64;

public class SerializeUtils {

    public static Object fromString(String serializedString) throws IOException, ClassNotFoundException {
        if(serializedString == null) return null;
        byte [] data = Base64.getDecoder().decode(serializedString);
        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(data));
        Object object = objectInputStream.readObject();
        objectInputStream.close();
        return object;
    }

    public static String toString(Serializable object) throws IOException {
        if(object == null) return null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(object);
        objectOutputStream.close();
        return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
    }

}
