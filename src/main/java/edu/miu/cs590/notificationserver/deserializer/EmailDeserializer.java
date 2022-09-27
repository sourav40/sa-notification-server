package edu.miu.cs590.notificationserver.deserializer;

import edu.miu.cs590.notificationserver.dto.EmailDto;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.ByteBuffer;
import java.util.Map;

public class EmailDeserializer implements Deserializer<EmailDto> {
    private String encoding = "UTF8";

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        //Nothing to configure
    }

    @Override
    public EmailDto deserialize(String topic, byte[] data) {

        try {
            if (data == null) {
                System.out.println("Null received at deserialize");
                return null;
            }

            ByteBuffer buf = ByteBuffer.wrap(data);

            int sizeOfFrom = buf.getInt();
            byte[] fromBytes = new byte[sizeOfFrom];
            buf.get(fromBytes);
            String deserializedFrom = new String(fromBytes, encoding);

            int sizeOfTo = buf.getInt();
            byte[] toBytes = new byte[sizeOfTo];
            buf.get(toBytes);
            String deserializedTo = new String(toBytes, encoding);

            int sizeOfSubject = buf.getInt();
            byte[] subjectBytes = new byte[sizeOfSubject];
            buf.get(subjectBytes);
            String deserializedSubject = new String(subjectBytes, encoding);

            int sizeOfMessage = buf.getInt();
            byte[] messageBytes = new byte[sizeOfMessage];
            buf.get(messageBytes);
            String deserializedMessage = new String(messageBytes, encoding);

            return new EmailDto(deserializedFrom, deserializedTo, deserializedSubject, deserializedMessage);

        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to Supplier");
        }
    }

    @Override
    public void close() {
        // nothing to do
    }

}
