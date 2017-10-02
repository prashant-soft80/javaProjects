package example.services.mail;

import example.services.MessageService;

import javax.inject.Singleton;

@Singleton
public class EmailServiceImpl implements MessageService {
    public boolean sendMessage(String msg, String receipient) {
        //some fancy code to send email
        System.out.println("Email Message sent to "+receipient+" with message="+msg);
        return true;
    }
}
