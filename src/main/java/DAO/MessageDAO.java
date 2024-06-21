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
}
