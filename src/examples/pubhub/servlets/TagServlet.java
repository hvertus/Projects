package examples.pubhub.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import examples.pubhub.dao.BookDAO;
import examples.pubhub.dao.TagDAO;
import examples.pubhub.model.Book;
import examples.pubhub.model.Tag;
import examples.pubhub.utilities.DAOUtilities;
import examples.pubhub.utilities.Utils;

/*
 * This servlet will take you to the homepage for the Book Publishing module (level 100)
 */
@WebServlet("/Tag")
public class TagServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//get all books, and put them into the session
		String option = request.getParameter("option");
		
		if(Utils.isSame(option, "tag")) {
			request.getSession().setAttribute("tags", DAOUtilities.getTagDAO().getAllTags());
			request.getRequestDispatcher("tag.jsp").forward(request, response);
		} else if(Utils.isSame(option, "tagBook")) {						
			request.getSession().setAttribute("books", DAOUtilities.getBookDAO().getAllBooks());
			request.getSession().setAttribute("tags", DAOUtilities.getTagDAO().getAllTags());
			request.getRequestDispatcher("tagbook.jsp").forward(request, response);
		}				
	}
	
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String option = request.getParameter("option");
		TagDAO tagDAO = DAOUtilities.getTagDAO();
		
		if(Utils.isSame(option, "tag")) {
			String tagName = request.getParameter("tagName");			
			boolean tagAdded = tagDAO.addTag(tagName);
			
			if(tagAdded){
				request.getSession().setAttribute("message", "Tag successfully added");
				request.getSession().setAttribute("messageClass", "alert-success");
	
				// We use a redirect here instead of a forward, because we don't
				// want request data to be saved. Otherwise, when
				// a user clicks "refresh", their browser would send the data
				// again!
				// This would be bad data management, and it
				// could result in duplicate rows in a database.
				response.sendRedirect(request.getContextPath() + "/tag?option=tag");
			} else {
				request.getSession().setAttribute("message", "There was a problem adding the tag");
				request.getSession().setAttribute("messageClass", "alert-danger");
				request.getRequestDispatcher("tag.jsp").forward(request, response);			
			}
		} else if(Utils.isSame(option, "tagBook")) {
			String selectedBook = request.getParameter("book");
			BookDAO bookDAO =  DAOUtilities.getBookDAO();
			Book book = bookDAO.getBookByISBN(selectedBook);
			book.setTags(new ArrayList<>());
			//get tags from the session
			int tagsSize = ((List<Tag>) request.getSession().getAttribute("tags")).size();

			String selectedTag = null;
			Tag tag = null;
			for(int i=0; i < tagsSize; i++) { //step through to get selected tags
				selectedTag = request.getParameter("tagName"+i); //get selected tag of each index
				if(selectedTag!=null) {
					tag = new Tag();
					tag.setTagName(selectedTag);
					book.getTags().add(tag);
				}
			}
			
			if(!book.getTags().isEmpty()) {
				boolean bookTagged = tagDAO.addTagToBook(book);
				if(bookTagged){
					request.getSession().setAttribute("message", "Tags have been successfully added on the book.");
					request.getSession().setAttribute("messageClass", "alert-success");
					request.getRequestDispatcher("tagbook.jsp").forward(request, response);
				} else {
					request.getSession().setAttribute("message", "There was a problem adding tag to a book.");
					request.getSession().setAttribute("messageClass", "alert-danger");
					request.getRequestDispatcher("tagbook.jsp").forward(request, response);			
				}
			} else {
				request.getSession().setAttribute("message", "At leat one tag must be selected.");
				request.getSession().setAttribute("messageClass", "alert-danger");
				request.getRequestDispatcher("tagbook.jsp").forward(request, response);	
				return;				
			}
		} else if(Utils.isSame(option, "remove")) {
			String tagName = request.getParameter("tagName");

			if(Utils.isNotBlank(tagName)) {		
				boolean removed = DAOUtilities.getTagDAO().deleteTagByName(tagName);
				if(removed) {
					request.getSession().setAttribute("message", "Tag successfully removed.");
					request.getSession().setAttribute("messageClass", "alert-success");				
					request.getSession().setAttribute("tags", DAOUtilities.getTagDAO().deleteTagByName(tagName));					
					response.sendRedirect(request.getContextPath() + "/tag?option=tag");
				} else {
					request.getSession().setAttribute("message", "There was a problem removing this tag.");
					request.getSession().setAttribute("messageClass", "alert-danger");
					request.getRequestDispatcher("tag.jsp").forward(request, response);					
				}
			}  			
		}
	}	

}