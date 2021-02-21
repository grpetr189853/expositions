package com.grpetr.task.web.command;

import com.grpetr.task.exception.AppException;
import com.grpetr.task.web.constants.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URI;
import java.util.Locale;

public class ChangeLanguageCommand extends Command {

    private static final long serialVersionUID = -2651459596565529143L;

    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws AppException, IOException, ServletException {
        HttpSession session = request.getSession();
        Locale newLocale = new Locale(request.getParameter("locale"));
        session.setAttribute("locale", newLocale);
        return Path.PAGE__INDEX;
    }
}