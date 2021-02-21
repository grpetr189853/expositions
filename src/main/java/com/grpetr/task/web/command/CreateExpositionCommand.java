package com.grpetr.task.web.command;

import com.grpetr.task.db.DBManager;
import com.grpetr.task.db.dao.*;
import com.grpetr.task.db.entity.Hall;
import com.grpetr.task.exception.AppException;
import com.grpetr.task.web.constants.Path;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;


public class CreateExpositionCommand extends Command {
    private static final Logger log = Logger.getLogger(CreateExpositionCommand.class);
    public final static String FORMATTER_PATTERN = "yyyy-MM-dd";
    private static final long serialVersionUID = 7749413733058993774L;
    public DAOFactory daoFactory = DAOFactory.getInstance();
    private final String UPLOAD_DIRECTORY = "/src/main/webapp/img/";
    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws AppException, IOException, ServletException {

        log.debug("Create exposition Command starts");

        /*****FILE UPLOAD******/
        Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
        InputStream input = filePart.getInputStream();
        FileOutputStream output = null;
        try {
            // Create folder (if it doesn't already exist)
            String realPath = request.getServletContext().getRealPath("");
            File folder = new File(realPath);
            File folderParent = new File(folder.getParent()).getParentFile();
            File imgDir = new File( folderParent + UPLOAD_DIRECTORY);
            if (!imgDir.exists()) {
                imgDir.mkdirs();
            }
            // Create output file
            output = new FileOutputStream(new File(imgDir, fileName));
            // Write data from input stream to output file.
            int bytesRead = 0;
            byte[] buffer = new byte[4096];
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        } catch (IOException ioex) {
            ioex.printStackTrace();
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException ioex) {
                ioex.printStackTrace();
            }
            // Also close InputStream if no longer needed.
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException ioex) {
                ioex.printStackTrace();
            }
        }
        /*****FILE UPLOAD******/
        request.getSession().removeAttribute("sendRedirectExpositions");
        request.getSession().removeAttribute("sendRedirectHalls");
        String forward = Path.PAGE__ERROR_PAGE;
        int newRowId = 0;
        Connection con = null;

        try {
            con = DBManager.getInstance().getConnection();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMATTER_PATTERN);
            String theme = (String) request.getParameter("theme");
            String errorMessage = null;
            if(theme.isEmpty()){
                errorMessage = "Exposition theme cannot be empty";
                request.setAttribute("errorMessage", errorMessage);
                log.error("errorMessage --> " + errorMessage);
                forward = Path.PAGE__ERROR_PAGE;
                return forward;
            }

            if(request.getParameter("ticket_price").isEmpty()){
                errorMessage = "Ticket price cannot be empty";
                request.setAttribute("errorMessage", errorMessage);
                log.error("errorMessage --> " + errorMessage);
                forward = Path.PAGE__ERROR_PAGE;
                return forward;
            }
            int ticketPrice = Integer.parseInt(request.getParameter("ticket_price"));
            int ticketsCount = Integer.parseInt(request.getParameter("tickets_count"));
            String date_in = request.getParameter("date_in");
            String date_out = request.getParameter("date_out");
            if(date_in.isEmpty() || date_out.isEmpty()){
                errorMessage = "Exposition Dates cannot be empty";
                request.setAttribute("errorMessage", errorMessage);
                log.error("errorMessage --> " + errorMessage);
                forward = Path.PAGE__ERROR_PAGE;
                return forward;
            }
            LocalDate dateIn = LocalDate.parse(date_in, formatter);
            LocalDate dateOut = LocalDate.parse(date_out, formatter);
            String[] halls = request.getParameterValues("halls");
            if(halls == null || halls.length == 0){
                errorMessage = "Exposition Halls cannot be empty";
                request.setAttribute("errorMessage", errorMessage);
                log.error("errorMessage --> " + errorMessage);
                forward = Path.PAGE__ERROR_PAGE;
                return forward;
            }
            int[] hall_ids = new int[halls.length];
            System.out.println(Arrays.toString(halls));
            for (int i = 0; i < halls.length; i++) {
                hall_ids[i] = Integer.parseInt(halls[i]);
            }
            HallDAO hallDAO = daoFactory.getHallDAO();
            ExpositionDAO expositionDAO = daoFactory.getExpositionDAO();
            newRowId = expositionDAO.setNewExposition(con, theme,ticketPrice,ticketsCount, dateIn,dateOut, hall_ids, fileName);
            for (Integer hall_id: hall_ids) {
                if(!(hallDAO.checkWhetherHallIsOccupied(con, hall_id,dateIn) && hallDAO.checkWhetherHallIsOccupied(con, hall_id,dateOut))){
                    expositionDAO.setNewExpositionHall(con, newRowId, hall_id);
                } else {
                    log.trace("The hall is occupied at this time");
                    throw new AppException("The hall is occupied at this time");
                }

            }
            if(newRowId != 0){
                forward = Path.PAGE__ADMIN_EXPOSITIONS;
                request.getSession().setAttribute("sendRedirectExpositions", true);
                log.trace("Set the request attribute: sendRedirectExpositions --> " + true);
            }
            con.commit();
        } catch (Exception ex){
            log.error("Cannot create exposition", ex);
            try {
                con.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(con != null){
                try {
                    con.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            throw new AppException("Cannot cannot create exposition",ex);
        } finally {
            if(con != null){
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        log.debug("Command finished");
        return forward;
    }
}
