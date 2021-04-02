package examples.pubhub.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import examples.pubhub.utilities.DAOUtilities;
import examples.pubhub.utilities.Utils;

public class BookTagServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getSession().setAttribute("tags", DAOUtilities.getTagDAO().getAllTags());
		request.getRequestDispatcher("bookTag.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String selectedTag = request.getParameter("tag");

		// Grab the list of Books of a given tag tagName from the Database
		if (Utils.isNotNull(selectedTag)) {
			request.getSession().setAttribute("selectedTag", selectedTag);
			request.getSession().setAttribute("books", DAOUtilities.getTagDAO().getAllBooksForAtag(selectedTag));
			response.sendRedirect(request.getContextPath() + "/bookTag");
		} else {
			request.getSession().setAttribute("message", "There was a problem showing the book for the tag.");
			request.getSession().setAttribute("messageClass", "alert-danger");
			request.getRequestDispatcher("bookTag.jsp").forward(request, response);
		}
	}
}