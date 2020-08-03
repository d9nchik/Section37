package exercise2;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MultiplicationTable extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html");
        try (
                PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<head lang=\"en\">");
            out.println("<meta charset=\"UTF-8\">");
            out.println(" <link href=\"/simple.css\" rel=\"stylesheet\">");
            out.println("<title>Multiplication Table</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<table><thead>");
            out.println("<tr>");
            out.println("<th colspan=\"10\">Multiplication Table</th>");
            out.println("</tr></thead>");
            out.println("<tr>");
            out.println("<td></td>");
            for (int i = 1; i < 10; i++) {
                out.println("<td>" + i + "</td>");
            }
            out.println("</tr>");
            for (int i = 1; i < 10; i++) {
                out.println("<tr>");
                out.println("<td>" + i + "</td>");
                for (int j = 1; j < 10; j++) {
                    out.println("<td>" + i * j + "</td>");
                }
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}