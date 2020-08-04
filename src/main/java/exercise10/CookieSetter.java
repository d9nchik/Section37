package exercise10;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CookieSetter extends HttpServlet {
    public static final int TWO_DAYS = 2 * 24 * 60 * 60;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        final Cookie color = new Cookie("color", "red");
        color.setMaxAge(TWO_DAYS);
        resp.addCookie(color);
        final Cookie radius = new Cookie("radius", "5.5");
        radius.setMaxAge(TWO_DAYS);
        resp.addCookie(radius);
        final Cookie count = new Cookie("count", "2");
        count.setMaxAge(TWO_DAYS);
        resp.addCookie(count);
        resp.setContentType("text/html");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<head lang=\"en\">");
            out.println("<meta charset=\"UTF-8\">");
            out.println(" <link href=\"/simple.css\" rel=\"stylesheet\">");
            out.println("<title>Cookie giver</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>You are with cookies </h1>");
            out.println("</body>");
            out.println("</html>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
