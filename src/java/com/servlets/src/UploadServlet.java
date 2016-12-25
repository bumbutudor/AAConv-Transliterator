//17.11.16 19:35 v0.5
package com.servlets.src;

import java.io.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.io.src.LoadSaveFiles;

@WebServlet("/upload")
@MultipartConfig
public class UploadServlet extends HttpServlet {

    private boolean isMultipart;
    private String filePath;
    private final int maxFileSize = 1024 * 1024;
    private final int maxMemSize = 128 * 1024;
    private File file;

    @Override
    public void init() {
        this.filePath = getServletContext().getInitParameter("file-upload-folder");
    }

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, java.io.IOException {
        
        this.isMultipart = ServletFileUpload.isMultipartContent(request);
        response.setContentType("text/html;charset=UTF-8");
        java.io.PrintWriter out = response.getWriter();
        if (!isMultipart) {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet upload</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<p>No file uploaded</p>");
            out.println("</body>");
            out.println("</html>");
            return;
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // maximum size that will be stored in memory
        factory.setSizeThreshold(maxMemSize);
        // Location to save data that is larger than maxMemSize.
        factory.setRepository(new File("/tmp/"));

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        // maximum file size to be uploaded.
        upload.setSizeMax(maxFileSize);

        //System.out.println("We got Here_01");
        
        try {
            // Parse the request to get file items.
            List fileItems = upload.parseRequest(request);

            // Process the uploaded file items
            Iterator i = fileItems.iterator();

            while (i.hasNext()) {
                FileItem fi = (FileItem) i.next();
                if (!fi.isFormField()) {
                    // Get the uploaded file parameters
                   // String fieldName = fi.getFieldName();
                    String fileName = fi.getName();
                    //String contentType = fi.getContentType();
                    //boolean isInMemory = fi.isInMemory();
                    //long sizeInBytes = fi.getSize();
                    // Write the file
                    if (fileName.lastIndexOf("\\") >= 0) {
                        file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\")));
                    } else {
                        file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\") + 1));
                    }
                    fi.write(file);
                    //out.println("Uploaded Filename: " + fileName + "\n " +file.getAbsolutePath()+ "<br>");
                    
                    String loadedText;
                    
                    LoadSaveFiles lsf = new LoadSaveFiles();
                    lsf.LoadFile(file);
                    loadedText = lsf.getLoadedText();

                    if(loadedText == null || loadedText.length() < 1){
                        loadedText = "Some error happened";
                    } else {
                        out.println(loadedText);
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, java.io.IOException {
        throw new ServletException("GET method used with " + getClass().getName() + ": POST method required.");
    }
}


/* 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try{
            out.println("We got here!");
            
            String description = request.getParameter("description"); // Retrieves <input type="text" name="description">
            Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
            InputStream fileContent = filePart.getInputStream();
            
            InputStreamReader fisr = new InputStreamReader(fileContent, "Unicode");
            
            String plainText = "";
            
            try (BufferedReader reader = new BufferedReader(fisr)) {
                String line = null;
                while ((line = reader.readLine()) != null) {
                    plainText+=line;
                    plainText+="\r\n"; //append newLine
                }
            } catch(Exception excep){
                excep.printStackTrace(System.err);
            }
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UploadServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("description: '" + description + "'\nfilePart: '" + filePart + "'\nfileName: '" + fileName + "'\nfileContent: '" + 
                    fileContent + "'\n -------------\n " + plainText + "");
            out.println("</body>");
            out.println("</html>");
            
        
        }catch(IOException ex){
            ex.printStackTrace(System.err);
            out.println("Some error happened: " + ex.toString());
        }
}

*/
