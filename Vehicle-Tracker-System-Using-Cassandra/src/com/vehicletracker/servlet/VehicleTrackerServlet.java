/*
 * Created by Aastikta Sharma on June 22, 2015.
 * */

package com.vehicletracker.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

/**
 * Servlet implementation class VehicleTrackerServlet
 */
@WebServlet("/track")
public class VehicleTrackerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VehicleTrackerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	 // Creates connection to our Cassandra cluster 
		Cluster cluster = Cluster.builder().addContactPoint("localhost").build();  // Creating Cluster object.
		
		Session session = cluster.connect();  // Creating Session object; You can also limit a session to a particular keyspace by Session session = cluster.connect("vehicle_tracker");
		
		String vehicleId = request.getParameter("veh_id");
		String trackDate = request.getParameter("date_val");
		String queryString = "SELECT time, latitude, longitude FROM vehicle_tracker.location WHERE vehicle_id = '" + vehicleId + "' AND date = '" + trackDate + "' LIMIT 1";
		
		ResultSet result = session.execute(queryString);
		ResultSet result_for_map = session.execute(queryString);

		Double lat_for_map = 0.0;
		Double long_for_map = 0.0;
		
		for (Row row : result_for_map)
		{
		   lat_for_map = row.getDouble("latitude");
		   long_for_map = row.getDouble("longitude");
		}
		
		PrintWriter out = response.getWriter();
		
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\"><html>");
		out.println("<head>");
		out.println("<title>Track a Vehicle</title>");
		out.println("<meta name=\"viewport\" content=\"initial-scale=1.0, user-scalable=no\"/>");
		out.println("<script type=\"text/javascript\" src=\"http://maps.google.com/maps/api/js?sensor=false\"></script>");
		out.println("<script type=\"text/javascript\"> function initialize() { ");
		out.println("var latlng = new google.maps.LatLng(" + lat_for_map + ", " + long_for_map + "); ");
		out.println("var settings = { zoom: 10, center: latlng, mapTypeControl: true, mapTypeControlOptions: {style: google.maps.MapTypeControlStyle.DROPDOWN_MENU}, navigationControl: true, navigationControlOptions: {style: google.maps.NavigationControlStyle.SMALL}, mapTypeId: google.maps.MapTypeId.ROADMAP}; ");
		out.println("var map = new google.maps.Map(document.getElementById(\"map_canvas\"), settings); ");
		out.println("var companyPos = new google.maps.LatLng(" + lat_for_map + ", " + long_for_map + "); ");
		out.println("var companyMarker = new google.maps.Marker({ position: companyPos, map: map, title:\"Vehicle\" }); ");
  		out.println("} </script>");
		out.println("</head>");
		out.println("<body onload=\"initialize()\">");
		out.println("<h1>Track a Vehicle</h1>");
		out.println("Enter the track date and id of the vehicle you want to track");	
		out.println("<p>&nbsp;</p>");
		out.println("<form id=\"form1\" name=\"form1\" method=\"get\" action=\"\">");
		out.println("<table>");
		out.println("<tr><td>Date (e.g. 2014-05-19):</td>");
		out.println("<td><input type=\"text\" name=\"date_val\" id=\"date_val\"/></td></tr>");
		out.println("<tr><td>Vehicle id (e.g. FLN78197):</td>");
		out.println("<td><input type=\"text\" name=\"veh_id\" id=\"veh_id\" /></td></tr>");
		out.println("<tr><td></td><td><input type=\"submit\" name=\"submit\" id=\"submit\" value=\"Submit\"/></td></tr>");
		out.println("</table>");
		out.println("</form>");
		out.println("<p>&nbsp;</p>");
		
		if(request.getParameter("veh_id") == null)
		{
			// blank
		}
		else if(result.isExhausted())
		{
			out.println("<hr/>");
			out.println("<p>&nbsp;</p>");
			out.println("Sorry, no results for vehicle id " + request.getParameter("veh_id") + " for " + request.getParameter("date_val"));
		}		
		else
		{
			out.println("<hr/>");
			out.println("<table cellpadding=\"4\">");
			out.println("<tr><td colspan=\"3\"><h2>" + request.getParameter("veh_id") + "</h2></td></tr>");
			out.println("<tr><td><b>Date and Time</b></td><td><b>Latitude</b></td><td><b>Longitude</b></td></tr>");
			for (Row row : result)
			{
			   out.println("<tr>");
			   out.println("<td>" + row.getDate("time") + "</td>");
			   out.println("<td>" + row.getDouble("latitude") + "</td>");
			   out.println("<td>" + row.getDouble("longitude") + "</td>");   
			   out.println("</tr>");
			}
			out.println("</table>");
			out.println("<div id=\"map_canvas\" style=\"width:500px; height:500px\"></div>");
		}

		out.println("</body></html>");
	}
}
