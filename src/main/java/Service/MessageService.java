package Service;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;

import java.util.List;

public class MessageService {
    public AccountDAO accountDAO;
    public MessageDAO messageDAO;
    
    //Constructors
    public MessageService(){
        this.accountDAO = new AccountDAO();
        this.messageDAO = new MessageDAO();
    }

    public MessageService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
        this.messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.accountDAO = new AccountDAO();
        this.messageDAO = messageDAO;
    }

    public MessageService(AccountDAO accountDAO, MessageDAO messageDAO){
        this.accountDAO = accountDAO;
        this.messageDAO = messageDAO;
    }

    /**
     * Registers a new message to the database using the DAO.
     * @param newMessage New message information.
     * @return If account is valid, returns registered message information. If not, return null.
     */
    public Message createMessage(Message newMessage){
        //Check conditions
        if(newMessage.getMessage_text().isBlank() || newMessage.getMessage_text().length() >= 255 || this.accountDAO.searchAccountByID(newMessage.getPosted_by()) == null)
            return null;
    

        return this.messageDAO.createNewMessage(newMessage);
    }

    /**
     * Returns a list of all messages in the database.
     * @return A list of all message information stored.
     */
    public List<Message> getAllMessages(){
        return this.messageDAO.getAllMessages();
    }
}
