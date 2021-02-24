package com.grpetr.task.web.command;

import com.grpetr.task.exception.AppException;
import com.grpetr.task.web.constants.Path;
import com.grpetr.task.web.result.CommandResult;
import com.grpetr.task.web.result.ForwardResult;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowCreateHallCommand extends Command {

    private static final Logger log = Logger.getLogger(ShowCreateHallCommand.class);
    private static final long serialVersionUID = -8477882129987279604L;

    @Override
    public CommandResult execute(HttpServletRequest request,
                                 HttpServletResponse response) throws AppException, IOException, ServletException {

        log.debug("Show Create Hall Command starts");
        // put menu items list to the request
        request.setAttribute("showCreateHall", true);
        log.trace("Set the request attribute: showCreateHall --> " + true);


        log.debug("Command finished");
        return new ForwardResult(Path.PAGE__ADMIN_CREATE_HALL);
    }

}
