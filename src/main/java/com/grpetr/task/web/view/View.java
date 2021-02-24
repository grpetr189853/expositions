package com.grpetr.task.web.view;

import com.grpetr.task.web.result.CommandResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface View {

    void render(CommandResult result, HttpServletRequest request,
                HttpServletResponse response) throws IOException, ServletException;
}
