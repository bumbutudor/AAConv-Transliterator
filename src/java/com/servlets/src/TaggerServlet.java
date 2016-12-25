//8.12.16 15:37 v0.2
package com.servlets.src;

import com.io.src.DataBaseControl;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Richard Strauss
 */
//@WebServlet(name = "TaggerServlet", urlPatterns = {"/TaggerServlet"})
public class TaggerServlet extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        try {
            PrintWriter out = response.getWriter();
            DataBaseControl dbc = new DataBaseControl();
            
            String querryString = request.getParameter("queryString");
            String ss = dbc.getDatabaseData(querryString);
            out.println(ss);

        } catch(Exception ex){
            ex.printStackTrace(System.err);
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "This servlet returns the lemma of the word from a mysql database.";
    }

}
