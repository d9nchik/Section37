package exercise9;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ShowTable extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try (PrintWriter out = resp.getWriter()) {
            final String tableName = req.getParameter("table");
            out.println("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>Table " + tableName + "</title>\n" +
                    "    <link href=\"/simple.css\" rel=\"stylesheet\">\n" +
                    "</head>\n" +
                    "<body>");
            out.println("<h1>" + tableName + "</h1>");
            out.println("<table>");

            Class.forName("com.mysql.cj.jdbc.Driver");
            CachedRowSet rowSet = RowSetProvider.newFactory().createCachedRowSet();
            String DB_URL = "jdbc:mysql://localhost/javaBook?serverTimezone=UTC";
            rowSet.setUrl(DB_URL);
            rowSet.setUsername(req.getParameter("username"));
            rowSet.setPassword(req.getParameter("password"));
            rowSet.setCommand("SELECT * FROM " + tableName);
            rowSet.execute();

            ResultSetMetaData metaData = rowSet.getMetaData();
            out.println("<tr>");
            for (int i = 1; i <= metaData.getColumnCount(); i++)
                out.println("<th>" + metaData.getColumnName(i) + "</th>");
            out.println("</tr>");

            while (rowSet.next()) {
                out.println("<tr>");
                for (int i = 1; i <= metaData.getColumnCount(); i++)
                    out.println("<td>" + rowSet.getString(i) + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("</body>\n" +
                    "</html>");
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }

    }
}
