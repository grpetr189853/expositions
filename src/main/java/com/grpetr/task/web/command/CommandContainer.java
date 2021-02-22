package com.grpetr.task.web.command;

import org.apache.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {

    private static final Logger log = Logger.getLogger(CommandContainer.class);

    private static Map<String, Command> commands = new TreeMap<String, Command>();

    static {

        // common commands
        commands.put("login", new LoginCommand());
        commands.put("listExpositions", new ListExpositionsCommand());
        commands.put("listHalls", new ListHallsCommand());
        commands.put("listUsers", new ListUsersCommand());
        commands.put("showCreateExposition", new ShowCreateExpositionCommand());
        commands.put("createExposition", new CreateExpositionCommand());
        commands.put("showCreateHall", new ShowCreateHallCommand());
        commands.put("createHall", new CreateHallCommand());
        commands.put("deleteExposition", new DeleteExpositionCommand());
        commands.put("buyTicket", new BuyTicketCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("registration", new RegistrationCommand());
        commands.put("deleteHall", new DeleteHallCommand());
        commands.put("createOrder", new CreateOrderCommand());
        commands.put("showExpositionStatistic", new ShowExpositionStatisticCommand());
        commands.put("changeLanguage", new ChangeLanguageCommand());
        commands.put("showBoughtTickets", new ShowBoughtTicketsCommand());
        commands.put("showEditExposition", new ShowEditExpositionCommand());
        commands.put("editExposition", new EditExpositionCommand());
        commands.put("showExpositionPhoto", new ShowExpositionPhotoCommand());
        commands.put("filterExpositions", new FilterExpositionsCommand());
        log.debug("Command container was successfully initialized");
        log.trace("Number of commands --> " + commands.size());
    }

    /**
     * Returns command object with the given name.
     *
     * @param commandName Name of the command.
     * @return Command object.
     */
    public static Command get(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            log.trace("Command not found, name --> " + commandName);
            return commands.get("noCommand");
        }

        return commands.get(commandName);
    }
}
