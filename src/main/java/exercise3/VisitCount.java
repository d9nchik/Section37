package exercise3;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class VisitCount extends HttpServlet {
    private int count = 0;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html");
        try (
                PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<head lang=\"en\">");
            out.println("<meta charset=\"UTF-8\">");
            out.println(" <link href=\"/simple.css\" rel=\"stylesheet\">");
            out.println("<title>Visitor Counter</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<p>You are visitor number: " + ++count + "</p>");
            out.println("<p>Host name: " + req.getRemoteHost() + "</p>");
            out.println("<p>IP address: " + req.getRemoteAddr() + "</p>");
            out.println("</body>");
            out.println("</html>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
