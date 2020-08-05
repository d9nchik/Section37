package exercise14;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OpinionPoll extends HttpServlet {
    private PreparedStatement getUser;
    private PreparedStatement updateUser;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try (PrintWriter out = resp.getWriter()) {
            generateForm(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try (PrintWriter out = resp.getWriter()) {
            if (!req.getParameter("newPass").equals(req.getParameter("retypePass"))) {
                generateForm(out, "New password is invalid in retype field");
            } else if (!userExist(req.getParameter("name"), req.getParameter("oldPass"))) {
                generateForm(out, "User doesn't exist");
            } else if (!update(req.getParameter("name"), req.getParameter("newPass"))) {
                generateForm(out, "Server error(");
            } else {
                out.println("<!DOCTYPE html>");
                out.println("<head lang=\"en\">");
                out.println("<meta charset=\"UTF-8\">");
                out.println(" <link href=\"/simple.css\" rel=\"stylesheet\">");
                out.println("<title>Password Changed</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Password successfully changed!</h1>");
                out.println("</body>");
                out.println("</html>");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public OpinionPoll() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/javaBook?serverTimezone=UTC",
                    "scott", "&x8R#Bp9");
            getUser = connection.prepareStatement("SELECT password FROM userCredentials WHERE login=? and password=?");
            updateUser = connection.prepareStatement("UPDATE userCredentials SET password=? WHERE login=?");
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void generateForm(PrintWriter out, String specialMSG) {
        out.println("<!DOCTYPE html>");
        out.println("<head lang=\"en\">");
        out.println("<meta charset=\"UTF-8\">");
        out.println(" <link href=\"/simple.css\" rel=\"stylesheet\">");
        out.println("<title>Password Change</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Change password</h1>");
        if (!specialMSG.isEmpty()) {
            out.println("<p>" + specialMSG + "</p>");
        }
        out.println("<form method=\"post\" action=\"/exercise8\">\n" +
                "    <label>Username:<input name=\"name\" type=\"text\" required></label><br>\n" +
                "    <label>Old Password:<input name=\"oldPass\" type=\"password\" required></label><br>\n" +
                "    <label>New Password:<input type=\"password\" name=\"newPass\" required></label><br>\n" +
                "    <label>Confirm New Password:<input type=\"password\" name=\"retypePass\" required></label><br>\n" +
                "    <input type=\"Submit\" value=\"Send\"><input type=\"reset\" value=\"Reset\">\n" +
                "</form>");
        out.println("</body>");
        out.println("</html>");
    }

    private static void generateForm(PrintWriter out) {
        generateForm(out, "");
    }

    synchronized private boolean userExist(String login, String password) {
        try {
            getUser.setString(1, login);
            getUser.setString(2, password);
            return getUser.executeQuery().next();
        } catch (SQLException throwables) {
            return false;
        }
    }

    synchronized private boolean update(String login, String password) {
        try {
            updateUser.setString(2, login);
            updateUser.setString(1, password);
            updateUser.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }
}
