import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FirstServlet extends HttpServlet {
    /**
     * Handle the HTTP GET method.
     *
     * @param request  servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws java.io.IOException {
        response.setContentType("text/html");
        java.io.PrintWriter out = response.getWriter();

        // output your page here
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("Hello, Java Servlets");
        out.println("</body>");
        out.println("</html>");
        out.close();
        System.out.println( "doGet called" );
    }

    public FirstServlet() {
        System.out.println("Constructor called");
    }

    /**
     * Initialize variables
     */
    @Override
    public void init() {
        System.out.println("init called");
    }

    /** Clean up resources */
    @Override
    public void destroy() {
        System.out.println( "destroy called" );
    }
}
