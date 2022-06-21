package com.my.controller.commands;

import com.my.controller.commands.implemantations.*;
import com.my.model.services.ExhibitionService;
import com.my.model.services.HallService;
import com.my.model.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class CommandContainer {
    private static final Map<String, Command> commands;

    private static final UserService userService = new UserService();
    private static final HallService hallService = new HallService();
    private static final ExhibitionService exhibitionService = new ExhibitionService();

    private CommandContainer() {
    }

    static {
        commands = new HashMap<>();
        commands.put("chooseLanguage", new ChooseLanguage());
        commands.put("getHomePage", new HomePage());
        commands.put("register", new Registration(userService));
        commands.put("logIn", new LogIn(userService));
        commands.put("logOut", new LogOut());
        commands.put("addHall", new AddHall(hallService));
        commands.put("addExhibition", new AddExhibition(exhibitionService));
        commands.put("getExhibitions", new GetExhibitions(exhibitionService));
        commands.put("getHalls", new GetHalls(hallService));
        commands.put("cancelExhibition", new CancelExhibition(exhibitionService));
        commands.put("buyTicket", new BuyTicket(userService));
        commands.put("getStatistics", new GetStats(exhibitionService));
        commands.put("getDetailedStats", new GetDetailedStats(exhibitionService));
    }

    public static Command getCommand(String command) {
        return commands.get(command);
    }

    public static String doCommand(Command command, HttpServletRequest request) {
        return command.execute(request);
    }
}
