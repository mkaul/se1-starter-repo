package de.hbrs.team89.se1_starter_repo;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * common superclass for all servlets
 * groups all auxiliary common methods used in all servlets
 */
public abstract class ParkhausServlet extends HttpServlet {

    /* abstract methods, to be defined in subclasses */
    abstract String NAME(); // each ParkhausServlet should have a name, e.g. "Level1"
    abstract int MAX(); // maximum number of parking slots of a single parking level
    abstract String config(); // configuration of a single parking level

    /**
     * HTTP GET
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String cmd = request.getParameter("cmd");
        System.out.println( cmd + " requested: " + request.getQueryString());
        switch ( cmd ){
            case "config":
                // Overwrite Parkhaus config parameters
                // Max, open_from, open_to, delay, simulation_speed
                out.println( config() );
                break;
            case "sum":
                out.println( getPersistentSum() );
                break;
            case "cars":
                // TODO: Send list of cars stored on the server to the client.
                // Cars are separated by comma.
                // Values of a single car are separated by slash.
                // Format: Nr, timer begin, duration, price, Ticket, color, space, client category, vehicle type, license (PKW Kennzeichen)
                // For example:
                // out.println("1/1619420863044/_/_/Ticket1/#0d1e0a/2/any/PKW/1,2/1619420863045/_/_/Ticket2/#dd10aa/3/any/PKW/2"); // TODO replace by real list of cars
                break;
            case "chart":
                // TODO send chart infos as JSON object to client
                break;
            default:
                System.out.println("Invalid Command: " + request.getQueryString());
        }

    }

    /**
     * HTTP POST
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String body = getBody( request );
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        System.out.println( body );
        String[] params = body.split(",");
        String event = params[0];
        String[] restParams = Arrays.copyOfRange(params, 1, params.length);

        switch( event ){
            case "enter":
                CarIF newCar = new Car( restParams );
                cars().add( newCar );
                System.out.println( "enter," + newCar );
                // re-direct car to another parking lot
                // out.println( locator( newCar ) );
                break;
            case "leave":
                CarIF oldCar = cars().get(0);
                if ( params.length > 4 ){
                    String priceString = params[4];
                    if ( ! "_".equals( priceString ) ){
                        // for JSON format skip over text and proceed to next integer
                        float price = (float)new Scanner( priceString ).useDelimiter("\\D+").nextInt();
                        price /= 100.0f;  // like Integer.parseInt( priceString ) / 100.0f;
                        // store new sum in ServletContext
                        getContext().setAttribute("sum"+NAME(), getPersistentSum() + price );
                    }
                }
                System.out.println( "leave," + oldCar );
                break;
            case "invalid": case "occupied":
                System.out.println( body );
                break;
            case "tomcat":
                out.println( getServletConfig().getServletContext().getServerInfo()
                        + getServletConfig().getServletContext().getMajorVersion()
                        + getServletConfig().getServletContext().getMinorVersion() );
                break;
            default:
                System.out.println( body );
                // System.out.println( "Invalid Command: " + body );
        }

    }


    // auxiliary methods used in HTTP request processing

    /**
     * @return the servlet context
     */
    ServletContext getContext(){
        return getServletConfig().getServletContext();
    }

    /**
     * TODO: replace this by your own function
     * @return the number of the free parking lot to which the next incoming car will be directed
     */
    int locator( CarIF car ){
        // numbers of parking lots start at 1, not zero
        return 1 + (( cars().size() - 1 ) % this.MAX());
    }

    /**
     * @return the list of all cars stored in the servlet context so far
     */
    @SuppressWarnings("unchecked")
    List<CarIF> cars(){
        if ( getContext().getAttribute( "cars"+NAME() ) == null ){
            getContext().setAttribute( "cars"+NAME(), new ArrayList<Car>() );
        }
        return (List<CarIF>) getContext().getAttribute( "cars"+NAME() );
    }

    /**
     * TODO: replace this by your own function
     * @return the sum of parking fees of all cars stored so far
     */
    Float getPersistentSum(){
        Float sum = (Float)getContext().getAttribute("sum");
        return sum == null ?  0.0f : sum;
    }

    /**
    * @param request the HTTP POST request
    * @return the body of the request
    */
    String getBody( HttpServletRequest request ) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if ( inputStream != null ) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public void destroy() {
        System.out.println("Servlet destroyed.");
    }
}