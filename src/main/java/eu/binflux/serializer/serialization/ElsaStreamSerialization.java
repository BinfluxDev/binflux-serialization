package eu.binflux.serializer.serialization;

import eu.binflux.serializer.Serialization;
import eu.binflux.serializer.SerializationException;
import org.mapdb.elsa.ElsaObjectInputStream;
import org.mapdb.elsa.ElsaObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public class ElsaStreamSerialization implements Serialization {

    @Override
    public <T> byte[] serialize(T object) {
        try {
            if(!(object instanceof Serializable))
                throw new SerializationException("Object doesn't implement Serializable");
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ElsaObjectOutputStream output = new ElsaObjectOutputStream(outputStream);
            output.writeObject(object);
            output.flush();
            output.close();
            return outputStream.toByteArray();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> T deserialize(byte[] bytes) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            ElsaObjectInputStream input = new ElsaObjectInputStream(inputStream);
            @SuppressWarnings("unchecked")
            T object = (T) input.readObject();
            input.close();
            return object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
