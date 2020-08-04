package exercise5;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class WebLoanCalculator extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html");
        try (PrintWriter out = resp.getWriter()) {
            final double rate = Double.parseDouble(req.getParameter("rate"));
            final int years = Integer.parseInt(req.getParameter("years"));
            final double loanAmount = Double.parseDouble(req.getParameter("loan"));
            Loan loan = new Loan(rate, years, loanAmount);
            out.println("<!DOCTYPE html>");
            out.println("<head lang=\"en\">");
            out.println("<meta charset=\"UTF-8\">");
            out.println(" <link href=\"/simple.css\" rel=\"stylesheet\">");
            out.println("<title>Result of your request</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<p>Loan amount: " + loanAmount + "</p>");
            out.println("<p>Annual interest rate: " + rate + "</p>");
            out.println("<p>Number of years: " + years + "</p>");
            out.println("<p>Monthly payment: " + loan.getMonthlyPayment() + "</p>");
            out.println("<p>Total payment: " + loan.getTotalPayment() + "</p>");
            out.println("</body>");
            out.println("</html>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
