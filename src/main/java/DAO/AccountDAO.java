package DAO;

import Model.Account;
import java.sql.*;
import Util.ConnectionUtil;

public class AccountDAO {

    /**
     * Registers an account to the database.
     * @param newAccount The account information for the new account.
     * @return Returns the registered account information if successful. Returns null if not successful.
     */
    public Account registerAccount(Account newAccount){
        //Connect to the database
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //Sets the parameters of the account in the SQL statement
            preparedStatement.setString(1, newAccount.getUsername());
            preparedStatement.setString(2, newAccount.getPassword());

            preparedStatement.executeUpdate();

            //Grabs the auto-incremented account id and creates a new Account object
            ResultSet accountResult = preparedStatement.getGeneratedKeys();
            if(accountResult.next()){
                int generated_account_id = (int) accountResult.getLong(1);
                return new Account(generated_account_id, newAccount.getUsername(), newAccount.getPassword());
            }        
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * Searches for an account in the database based on the id given.
     * @param id The id to search for in the database.
     * @return Returns the account information if found. Returns null if no user found.
     */
    public Account searchAccountByID(int id){
        //Connect to the database
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account WHERE account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //Sets the parameters of the account in the SQL statement
            preparedStatement.setInt(1, id);

            ResultSet accountResult = preparedStatement.executeQuery();
            while(accountResult.next()){
                Account account = new Account(accountResult.getInt("account_id"),
                accountResult.getString("username"),
                accountResult.getString("password"));
                return account;
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * Searches for an account in the database based on the username given.
     * @param username The username to search for in the database.
     * @return Returns the account information if found. Returns null if no user found.
     */
    public Account searchAccountByUsername(String username){
        //Connect to the database
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //Sets the parameters of the account in the SQL statement
            preparedStatement.setString(1, username);

            ResultSet accountResult = preparedStatement.executeQuery();
            while(accountResult.next()){
                Account account = new Account(accountResult.getInt("account_id"),
                accountResult.getString("username"),
                accountResult.getString("password"));
                return account;
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * Checks the database to see if the current username and password credentials are in the database.
     * @param username The username of the account.
     * @param password The password of the account.
     * @return Returns the account info if successful. Returns null if not successful.
     */
    public Account checkAccountCredentials(String username, String password){
                //Connect to the database
                Connection connection = ConnectionUtil.getConnection();
                try{
                    String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        
                    //Sets the parameters of the account in the SQL statement
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);
        
                    ResultSet accountResult = preparedStatement.executeQuery();
                    while(accountResult.next()){
                        Account account = new Account(accountResult.getInt("account_id"),
                        accountResult.getString("username"),
                        accountResult.getString("password"));
                        return account;
                    }
                }
                catch(SQLException e){
                    System.out.println(e.getMessage());
                }
        
                return null;
    }

}
