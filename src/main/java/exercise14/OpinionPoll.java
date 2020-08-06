package exercise14;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OpinionPoll extends HttpServlet {
    private int yesVote;
    private int noVote;
    private PreparedStatement updateStatement;
    private final Lock lock = new ReentrantLock();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try (PrintWriter out = resp.getWriter()) {
            lock.lock();
            if (req.getParameter("vote").equals("yes"))
                yesVote++;
            else
                noVote++;
            lock.unlock();
            new Thread(this::update).start();
            out.println("<!DOCTYPE html>");
            out.println("<head lang=\"en\">");
            out.println("<meta charset=\"UTF-8\">");
            out.println(" <link href=\"/simple.css\" rel=\"stylesheet\">");
            out.println("<title>Result of voting</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<p>The current Yes count is " + yesVote + "</p>");
            out.println("<p>The current No count is " + noVote + "</p>");
            out.println("</body>");
            out.println("</html>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public OpinionPoll() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/javaBook?serverTimezone=UTC",
                    "scott", "&x8R#Bp9");
            ResultSet result = connection.createStatement()
                    .executeQuery("SELECT  yesCount, noCount FROM javaBook.Poll WHERE question='Are you a CS major? '");
            result.next();
            yesVote = result.getInt(1);
            noVote = result.getInt(2);
            updateStatement = connection.prepareStatement("UPDATE javaBook.Poll SET yesCount=?," +
                    " noCount=? WHERE question='Are you a CS major? '");
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    synchronized private void update() {
        try {
            updateStatement.setInt(1, yesVote);
            updateStatement.setInt(2, noVote);
            updateStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
