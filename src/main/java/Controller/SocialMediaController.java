package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        //Javalin functions
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::exampleHandler);
        app.delete("/messages/{message_id}", this::exampleHandler);
        app.patch("/messages/{message_id}", this::exampleHandler);
        app.patch("/accounts/{account_id}/messages", this::exampleHandler);

        return app;
    }

    /**
     * Registers the user to the database.
     * Status 200 if successful, Status 400 if unsuccessful.
     * @param ctx The Javalin Context object, which provides information about the HTTP request and response.
     */
    private void registerHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Account newAccount = om.readValue(ctx.body(), Account.class);
        Account registeredAccount = this.accountService.registerNewAccount(newAccount);

        //If the registered account is not null, it has been successfully registered.
        if(registeredAccount != null){
            ctx.json(om.writeValueAsString(registeredAccount));
            ctx.status(200);
        }
        else
            ctx.status(400);
    }

    /**
     * Verifies the login of a user in the database.
     * Status 200 if successful, Status 401 if unsuccessful.
     * @param ctx The Javalin Context object, which provides information about the HTTP request and response.
     * @throws JsonProcessingException
     */
    private void loginHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Account loginAccount = om.readValue(ctx.body(), Account.class);
        Account validatedAccount = this.accountService.loginAccount(loginAccount);

        //If the validated account is not null, it has been successfully found.
        if(validatedAccount != null){
            ctx.json(om.writeValueAsString(validatedAccount));
            ctx.status(200);
        }

        //If not, the account information is incorrect.
        else
            ctx.status(401);
    }

    /**
     * Creates a new message in the database.
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     */
    private void createMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Message newMessage = om.readValue(ctx.body(), Message.class);
        Message validatedNewMessage = this.messageService.createMessage(newMessage);

        //If the validated message is not null, it has been successfully registered.
        if(validatedNewMessage != null){
            ctx.json(om.writeValueAsString(validatedNewMessage));
            ctx.status(200);
        }
        else
            ctx.status(400);
    }

    /**
     * Creates a list of all messages in the database.
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getAllMessagesHandler(Context ctx) {
        List<Message> allMessages = this.messageService.getAllMessages();
        ctx.json(allMessages);
        ctx.status(200);
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }


}