package com.grpetr.task.web.command;

import com.grpetr.task.exception.AppException;
import com.grpetr.task.web.constants.Path;
import com.grpetr.task.web.result.CommandResult;
import com.grpetr.task.web.result.ForwardResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

public class ChangeLanguageCommand extends Command {

    private static final long serialVersionUID = -2651459596565529143L;

    public CommandResult execute(HttpServletRequest request,
                                 HttpServletResponse response) throws AppException, IOException, ServletException {
        HttpSession session = request.getSession();
        Locale newLocale = new Locale(request.getParameter("locale"));
        session.setAttribute("locale", newLocale);
        return new ForwardResult(Path.PAGE__INDEX);
    }
}