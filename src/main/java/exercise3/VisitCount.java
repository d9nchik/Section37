package exercise3;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

public class VisitCount extends HttpServlet {
    private int count;

    @Override
    public void init() {
        try {
            RandomAccessFile accessFile = new RandomAccessFile("countVisitor.dat", "r");
            accessFile.seek(0);
            count = accessFile.readInt();
        } catch (FileNotFoundException e) {
            count = 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    @Override
    public void destroy() {
        try {
            RandomAccessFile accessFile = new RandomAccessFile("countVisitor.dat", "rw");
            accessFile.seek(0);
            accessFile.writeInt(count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
