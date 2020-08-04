package exercise11;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Stream;

public class CookieGetter extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<head lang=\"en\">");
            out.println("<meta charset=\"UTF-8\">");
            out.println(" <link href=\"/simple.css\" rel=\"stylesheet\">");
            out.println("<title>Cookie getter</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Your cookies on our host</h1>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Name</th>");
            out.println("<th>Value</th>");
            out.println("</tr>");
            Stream.of(req.getCookies()).forEach(e -> out.println(
                    "<tr><td>" + e.getName() + "</td><td>" + e.getValue() + "</td></tr>"));
            out.println("</table>");

            out.println("</body>");
            out.println("</html>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
