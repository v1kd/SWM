package com.swm.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.swm.bean.Cluster;
import com.swm.processor.SWMProcessor;

/**
 * Servlet implementation class SWMController
 */
@WebServlet("/welcome")
public class SWMController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private SWMProcessor processor = new SWMProcessor();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SWMController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		// processor.processData();

		List<Cluster> clusters = processor.getClusters();
		
		Cluster event = null;
		Cluster player = null;
		Cluster team = null;
		
		for (Cluster cluster : clusters) {
			if (cluster.getCategory().equals("Team") && team == null) {
				team = cluster;
			} else if (cluster.getCategory().equals("Player") && player == null) {
				player = cluster;
			} else if (cluster.getCategory().equals("Tournament")
					&& event == null) {
				event = cluster;
			}
		}
		
		request.setAttribute("clusters", clusters);

		request.setAttribute("event", event);
		request.setAttribute("player", player);
		request.setAttribute("team", team);

		request.getRequestDispatcher("/WEB-INF/jsp/welcome.jsp").forward(
				request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
