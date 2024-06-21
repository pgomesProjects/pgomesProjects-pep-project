package Service;

import DAO.AccountDAO;
import Model.Account;
import Model.Message;

public class AccountService {
    
    public AccountDAO accountDAO;
    
    //Constructors
    public AccountService(){
        this.accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO socialMediaDAO){
        this.accountDAO = socialMediaDAO;
    }

    /**
     * Registers an account to a database using the DAO.
     * @param newAccount New account information.
     * @return If account is valid, returns registered account information. If not, return null.
     */
    public Account registerNewAccount(Account newAccount){
        //Check conditions
        if(newAccount.getUsername().isBlank() || newAccount.getPassword().length() < 4 || this.accountDAO.searchAccountByUsername(newAccount.getUsername()) != null)
            return null;
        
        return this.accountDAO.registerAccount(newAccount);
    }

    /**
     * Checks if the account's credentials are valid.
     * @param currentAccount The account information to check.
     * @return If the account is found, returns the account data. If not, return null.
     */
    public Account loginAccount(Account currentAccount){
        return this.accountDAO.checkAccountCredentials(currentAccount.getUsername(), currentAccount.getPassword());
    }

}
