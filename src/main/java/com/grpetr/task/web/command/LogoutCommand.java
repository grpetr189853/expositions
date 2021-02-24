package com.grpetr.task.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.grpetr.task.web.constants.Path;
import com.grpetr.task.web.result.CommandResult;
import com.grpetr.task.web.result.ForwardResult;
import org.apache.log4j.Logger;

public class LogoutCommand extends Command {

    private static final long serialVersionUID = -2785976616686657267L;

    private static final Logger log = Logger.getLogger(LogoutCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request,
                                 HttpServletResponse response) throws IOException, ServletException {
        log.debug("Logout Command starts");

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        log.debug("Command finished");
        return new ForwardResult(Path.PAGE__LOGIN);
    }

}