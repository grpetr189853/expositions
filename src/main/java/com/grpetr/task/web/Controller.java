package com.grpetr.task.web;

import com.grpetr.task.exception.AppException;
import com.grpetr.task.web.command.Command;
import com.grpetr.task.web.command.CommandContainer;
import com.grpetr.task.web.constants.Path;
import com.grpetr.task.web.result.CommandResult;
import com.grpetr.task.web.result.ForwardResult;
import com.grpetr.task.web.view.ResultView;
import com.grpetr.task.web.view.View;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@MultipartConfig
public class Controller extends HttpServlet {
    private static final long serialVersionUID = 2423353715955164816L;
    ResultView views = new ResultView();
    private static final Logger log = Logger.getLogger(Controller.class);

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    /**
     * Main method of this controller.
     */
    private void process(HttpServletRequest request,
                         HttpServletResponse response) throws IOException, ServletException {

        log.debug("Controller starts");
        String commandName;

        // extract command name from the request

        commandName = request.getParameter("command");
        log.trace("Request parameter: command --> " + commandName);

        // obtain command object by its name
        Command command = CommandContainer.get(commandName);
        log.trace("Obtained command --> " + command);
        boolean isError = false;
        // execute command and get forward address
//        String forward = Path.PAGE__ERROR_PAGE;
        CommandResult result = new ForwardResult(Path.PAGE__ERROR_PAGE);
        try {
            result = command.execute(request, response);
        } catch (AppException e) {
            isError = true;
            if (e.getCause() != null) {
                request.setAttribute("errorMessage", e.getMessage() + ": "
                        + e.getCause().getMessage());
            } else {
                request.setAttribute("errorMessage", e.getMessage());
            }
        }

        log.trace("Forward address --> " + result);

        log.debug("Controller finished, now go to forward address --> " + result);

        // if the forward address is not null go to the address
        /*
        boolean sendRedirect = Boolean.valueOf(request.getParameter("sendRedirect"));
        if (isError == true || (forward != null && sendRedirect == false)) {
            RequestDispatcher disp = request.getRequestDispatcher(forward);
            disp.forward(request, response);
        } else {
            Object sendRedirectExpositions = request.getSession().getAttribute("sendRedirectExpositions");
            Object sendRedirectHalls = request.getSession().getAttribute("sendRedirectHalls");
            if (sendRedirectExpositions != null && sendRedirectExpositions.equals(true)) {
                response.sendRedirect("controller?command=listExpositions");
            } else if (sendRedirectHalls != null && sendRedirectHalls.equals(true)) {
                response.sendRedirect("controller?command=listHalls");
            }
        }
        */

        views.render(result, request, response);
    }
}
