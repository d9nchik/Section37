import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class TimeForm extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html";
    private final Locale[] allLocale = Locale.getAvailableLocales();
    private final String[] allTimeZone = TimeZone.getAvailableIDs();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(CONTENT_TYPE);
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<head lang=\"en\">");
        out.println("<meta charset=\"UTF-8\"><title>Time</title></head>");
        out.println("<h1>Choose locale and time zone</h1>");
        out.println("<form method=\"post\" action=" +
                "TimeForm>");
        out.println("Locale <select size=\"1\" name=\"locale\">");
        // Fill in all locales
        for (int i = 0; i < allLocale.length; i++) {
            out.println("<option value=\"" + i + "\">" +
                    allLocale[i].getDisplayName() + "</option>");
        }
        out.println("</select>");
        // Fill in all time zones
        out.println("<p>Time Zone<select size=\"1\" name=\"timezone\">");
        for (String s : allTimeZone) {
            out.println("<option value=\"" + s + "\">" +
                    s + "</option>");
        }
        out.println("</select>");
        out.println("<p><input type=\"submit\" value=\"Submit\" >");
        out.println("<input type=\"reset\" value=\"Reset\"></p>");
        out.println("</form>");
        out.close(); // Close stream
    }

    /**
     * Process the HTTP Post request
     */
    public void doPost(HttpServletRequest request, HttpServletResponse
            response) throws IOException {
        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<head lang=\"en\">");
        out.println("<meta charset=\"UTF-8\">");
        int localeIndex = Integer.parseInt(
                request.getParameter("locale"));
        String timeZoneID = request.getParameter("timezone");
        out.println("<title>Current Time</title></head>");
        out.println("<body>");
        Calendar calendar = new GregorianCalendar(allLocale[localeIndex]);
        TimeZone timeZone = TimeZone.getTimeZone(timeZoneID);
        DateFormat dateFormat = DateFormat.getDateTimeInstance(
                DateFormat.FULL, DateFormat.FULL, allLocale[localeIndex]);
        dateFormat.setTimeZone(timeZone);
        out.println("Current time is " +
                dateFormat.format(calendar.getTime()) + "</p>");
        out.println("</body></html>");
        out.close(); // Close stream
    }
}
