package com.grpetr.task.web.view;

import com.grpetr.task.web.result.CommandResult;
import com.grpetr.task.web.result.ForwardResult;
import com.grpetr.task.web.result.RedirectResult;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ResultView {

    public Map<Class<?>, View> getViews() {
        return views;
    }

    public ResultView(){

        views = new HashMap<>();
        View redirectResult =  (result, request, response) -> {
            response.sendRedirect(request.getContextPath() + result.getResource());
        };
        View forwardResult = (result, request, response) -> {
            RequestDispatcher disp = request.getRequestDispatcher(result.getResource());
            disp.forward(request, response);
        };
        views.put(ForwardResult.class, forwardResult);
        views.put(RedirectResult.class, redirectResult);

    }

    Map<Class<?>, View> views;

    public void render(CommandResult result, HttpServletRequest request,
                       HttpServletResponse response) throws IOException, ServletException {
        views.get(result.getClass()).render(result, request, response);

        }

}
