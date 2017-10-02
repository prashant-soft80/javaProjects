package example.services;

import com.google.inject.ImplementedBy;
import example.services.mail.EmailServiceImpl;

@ImplementedBy(EmailServiceImpl.class)
public interface MessageService {
    boolean sendMessage(String msg, String receipient);
}
