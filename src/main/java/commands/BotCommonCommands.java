package commands;

public class BotCommonCommands {
    @AppBotCommand(name = "/hello", description = "when request hello", showInHelp = true)
    String hello(){return "Hello, user";}
    @AppBotCommand(name = "/bye", description = "when request bye", showInHelp = true)
    String bye(){
        return "Bye, user";
    }
    @AppBotCommand(name = "/help", description = "when request help", showInHelp = true)
    String help(){
        return "Help";
    }
}
