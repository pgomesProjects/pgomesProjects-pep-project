package DAO;

import Model.Message;
import java.sql.*;
import Util.ConnectionUtil;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

    /**
     * Creates a new message and stores it in the database.
     * @param newMessage The information for the message.
     * @return Returns the message information if successful. Returns null if unsuccessful.
     */
    public Message createNewMessage(Message newMessage){
        //Connect to the database
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //Sets the parameters of the message in the SQL statement
            preparedStatement.setInt(1, newMessage.getPosted_by());
            preparedStatement.setString(2, newMessage.getMessage_text());
            preparedStatement.setLong(3, newMessage.getTime_posted_epoch());

            preparedStatement.executeUpdate();

            //Grabs the auto-incremented message id and creates a new Message object
            ResultSet messageResult = preparedStatement.getGeneratedKeys();
            if(messageResult.next()){
                int generated_message_id = (int) messageResult.getLong(1);
                return new Message(generated_message_id, newMessage.getPosted_by(), newMessage.getMessage_text(), newMessage.getTime_posted_epoch());
            }        
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * Stores a list of all messages in the database.
     * @return Returns a list of all message information in the database.
     */
    public List<Message> getAllMessages(){
        //Connect to the database
        Connection connection = ConnectionUtil.getConnection();
        List<Message> allMessages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet messageResults = preparedStatement.executeQuery();
            while(messageResults.next()){
                Message currentMessage = new Message(messageResults.getInt("message_id"), messageResults.getInt("posted_by"), messageResults.getString("message_text"), messageResults.getLong("time_posted_epoch"));
                allMessages.add(currentMessage);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return allMessages;
    }

    /**
     * Gets a message based on the message id given.
     * @param id The id of the message.
     * @return If the message is found, the message information is returned. If no message is found, return null.
     */
    public Message getMessageByMessageId(int id){
        //Connect to the database
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //Sets the parameters of the message in the SQL statement
            preparedStatement.setInt(1, id);

            ResultSet messageResult = preparedStatement.executeQuery();
            while(messageResult.next()){
                Message foundMessage = new Message(messageResult.getInt("message_id"), messageResult.getInt("posted_by"), messageResult.getString("message_text"), messageResult.getLong("time_posted_epoch"));
                return foundMessage;
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * Deletes the message information based on the message id given.
     * @param id The id of the message.
     * @return If the message is deleted, the message information is returned. If no message is found, return null.
     */
    public Message deleteMessage(int id){
        //Get the information of the message before deleting it
        Message messageToDelete = getMessageByMessageId(id);
        //Connect to the database
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "DELETE FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //Sets the parameters of the message in the SQL statement
            preparedStatement.setInt(1, id);

            int rowsDeleted = preparedStatement.executeUpdate();
            if(rowsDeleted > 0)
                return messageToDelete;
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * Updates the message information based on the message id given.
     * @param id The id of the message.
     * @param newMessage The new message to update the database with.
     * @return If the message was successfully updated, the message information is returned. If no message is found, return null.
     */
    public Message updateMessage(int id, String newMessage){
        //Connect to the database
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //Sets the parameters of the message in the SQL statement
            preparedStatement.setString(1, newMessage);
            preparedStatement.setInt(2, id);

            int rowsUpdated = preparedStatement.executeUpdate();

            if(rowsUpdated > 0)
                return getMessageByMessageId(id);
 
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    /**
     * Stores a list of all messages in the database posted by the account id given.
     * @param account_id The account id to check the messages for.
     * @return Returns a list of all message information in the database linked to the account id given.
     */
    public List<Message> getAllMessagesByAccountId(int account_id){
        //Connect to the database
        Connection connection = ConnectionUtil.getConnection();
        List<Message> allMessages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //Sets the parameters of the message in the SQL statement
            preparedStatement.setInt(1, account_id);

            ResultSet messageResults = preparedStatement.executeQuery();
            while(messageResults.next()){
                Message currentMessage = new Message(messageResults.getInt("message_id"), messageResults.getInt("posted_by"), messageResults.getString("message_text"), messageResults.getLong("time_posted_epoch"));
                allMessages.add(currentMessage);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return allMessages;
    }
}
