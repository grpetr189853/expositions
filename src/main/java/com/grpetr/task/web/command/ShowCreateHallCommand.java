package com.grpetr.task.web.command;

import com.grpetr.task.db.DBManager;
import com.grpetr.task.db.entity.Hall;
import com.grpetr.task.exception.AppException;
import com.grpetr.task.web.constants.Path;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ShowCreateHallCommand extends Command {

    private static final Logger log = Logger.getLogger(ShowCreateHallCommand.class);
    private static final long serialVersionUID = -8477882129987279604L;

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws AppException, IOException, ServletException {

        log.debug("Show Create Hall Command starts");
        // put menu items list to the request
        request.setAttribute("showCreateHall", true);
        log.trace("Set the request attribute: showCreateHall --> " + true);


        log.debug("Command finished");
        return Path.PAGE__ADMIN_CREATE_HALL;
    }

}
