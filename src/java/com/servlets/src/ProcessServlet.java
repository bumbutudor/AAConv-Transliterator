//18.11.16 12:13 v0.6
package com.servlets.src;

import com.translit.src.Translit;
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
public class ProcessServlet extends HttpServlet {  
    private final Translit tt = new Translit();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        String latinText, cyrText;
        int period;
        boolean actualize;

        try {
            cyrText = request.getParameter("cyrillicText");
            period = Integer.parseInt(request.getParameter("period"));
            actualize = Boolean.parseBoolean(request.getParameter("actualize"));

            tt.setTranslitPreferences(actualize, true);
            tt.setCyrillicText(cyrText);
            tt.transliterate(period);
            latinText = tt.getLatinText();
            
            out.println(latinText);
        } catch (NumberFormatException exe) {
            exe.printStackTrace(System.err);
            out.println("Some error happened: " + exe.toString());
        } finally {
            out.close();
           // tt = null;
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
        return "This servlet is processing the Cyrillic text and returning the converted Latin text.";
    }
}
