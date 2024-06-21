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

    /**
     * Returns message information based on the message id given.
     * @param id The message id of the message.
     * @return If found, returns the message information. If not, return null.
     */
    public Message getMessageByMessageId(int id){
        return this.messageDAO.getMessageByMessageId(id);
    }

    /**
     * Deletes a message from the database.
     * @param id The message id of the message.
     * @return If deleted, returns the message deleted. If no message deleted, return null.
     */
    public Message deleteMessage(int id){
        return this.messageDAO.deleteMessage(id);
    }

    /**
     * Updates a message in the database.
     * @param id The message id of the message.
     * @param message The updated message.
     * @return If the message id and the message text are valid, returns the updated message. If invalid, return null.
     */
    public Message updateMessage(int id, String message){
        //Check conditions
        if(message.isBlank() || message.length() >= 255 || this.messageDAO.getMessageByMessageId(id) == null)
            return null;

        return this.messageDAO.updateMessage(id, message);
    }

    /**
     * Returns a list of all messages in the database posted by the account id given.
     * @param account_id The account id to check the messages for.
     * @return A list of all messages posted by the account id given.
     */
    public List<Message> getAllMessagesByAccountId(int account_id){
        return this.messageDAO.getAllMessagesByAccountId(account_id);
    }
}
