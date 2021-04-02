package examples.pubhub.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import examples.pubhub.utilities.DAOUtilities;
import examples.pubhub.utilities.Utils;

/*
 * This servlet will take you to the homepage for the Book Publishing module (level 100)
 */
public class BookTaggedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().setAttribute("books", DAOUtilities.getBookDAO().getAllBooks());
		request.getRequestDispatcher("bookTagged.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		String tag2Remove = request.getParameter("tagName");
		String selectedBook = request.getParameter("book");
		
		if(Utils.isBlank(tag2Remove)) {		
			// Grab the list of Tags of a given book isbn from the Database
			if(Utils.isNotNull(selectedBook)) {
				request.getSession().setAttribute("selectedBook", selectedBook); //set the book into a session so we can use it to remove tags
				request.getSession().setAttribute("tags", DAOUtilities.getBookDAO().getAllTagsForABook(selectedBook));
				response.sendRedirect(request.getContextPath() + "/BookTagged");
			}
		} else { //remove tag from a book
			selectedBook = (String)request.getSession().getAttribute("selectedBook");	//get selected book from the session		
			boolean removed = DAOUtilities.getTagDAO().removeTagFromBook(tag2Remove, selectedBook);
			if(removed) {
				request.getSession().setAttribute("message", "Tag successfully removed for the book.");
				request.getSession().setAttribute("messageClass", "alert-success");				
				request.getSession().setAttribute("tags", DAOUtilities.getBookDAO().getAllTagsForABook(selectedBook));
				response.sendRedirect(request.getContextPath() + "/BookTagged");				
			} else {
				request.getSession().setAttribute("message", "There was a problem removing the tag for the book.");
				request.getSession().setAttribute("messageClass", "alert-danger");
				request.getRequestDispatcher("bookTagged.jsp").forward(request, response);					
			}
		}
	}

}
