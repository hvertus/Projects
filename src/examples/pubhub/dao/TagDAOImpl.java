package examples.pubhub.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import examples.pubhub.model.Tag;
import examples.pubhub.model.Book;
import examples.pubhub.utilities.DAOUtilities;

/**
 * Implementation for the TagDAO, responsible for querying the database for Tag objects.
 */
public class TagDAOImpl implements TagDAO {
	
	Connection connection = null;
	PreparedStatement stmt = null;

	/*------------------------------------------------------------------------------------------------*/
	
	@Override
	public boolean addTag(String tagName) {
		try {	
			connection = DAOUtilities.getConnection();
			String sql = "INSERT INTO tags VALUES (?)"; 
			stmt = connection.prepareStatement(sql);
						
			stmt.setString(1, tagName);
			
			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;			
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("Exception:: "+e);
			return false;
		} finally {
			closeResources();
		}
	}
	/*------------------------------------------------------------------------------------------------*/

	public boolean deleteTagByName(String tagName) {
		try {
			connection = DAOUtilities.getConnection();
			String sql = "DELETE FROM book_tags WHERE tag_name = ?";
			stmt = connection.prepareStatement(sql);			
			stmt.setString(1, tagName);
			stmt.executeUpdate();
			
			boolean deleted = false;
			sql = "DELETE FROM tags WHERE tag_name = ?";
			stmt = connection.prepareStatement(sql);			
			stmt.setString(1, tagName);	
				
			if (stmt.executeUpdate() != 0) {
				deleted = true;
			}
			
			return deleted;			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}
	/*------------------------------------------------------------------------------------------------*/
	
	//method to add tag to abook.@book the book to add
	@Override
	public boolean addTagToBook(Book book) {
		
		try {
			connection = DAOUtilities.getConnection();
			
			String sql = "INSERT INTO book_tags VALUES (?, ?)"; 
			stmt = connection.prepareStatement(sql);

			int counter = 0 ;
			if(!book.getTags().equals(null)) {
				for(Tag tag: book.getTags()) {
					stmt.setString(1, book.getIsbn13());
					stmt.setString(2, tag.getTagName());	
					if (stmt.executeUpdate() != 0)
						counter++;
				}
			}
			
			if (counter==book.getTags().size())
				return true;
			else
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}	
	/*------------------------------------------------------------------------------------------------*/
	//method to add tag to a book. This is used for test actually.
	@Override
	public boolean addTagToBook(String tagName, String isbn13) {
		
		try {
			connection = DAOUtilities.getConnection();
			String sql = "INSERT INTO book_tags VALUES (?, ?)"; 
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, isbn13);
			stmt.setString(2, tagName);
			
			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}
	/*------------------------------------------------------------------------------------------------*/
	//method to remove tag from a book
	public boolean removeTagFromBook(String tagName, String isbn13) {
		try {
			connection = DAOUtilities.getConnection();
			String sql = "DELETE FROM book_tags WHERE isbn_13=? AND tag_name=?";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, isbn13);
			stmt.setString(2, tagName);
			
			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}

	/*------------------------------------------------------------------------------------------------*/
	//method to get all tags from the table
	@Override
	public List<Tag> getAllTags() {
		List<Tag> tags = null;
		
		try {
			connection = DAOUtilities.getConnection();
			String sql ="SELECT * FROM tags ORDER BY tag_name ASC";
			stmt = connection.prepareStatement(sql);
			
			ResultSet rs = stmt.executeQuery();
			tags = new ArrayList<>();
			Tag tag = null;
			
			while (rs.next()) {
				tag = new Tag();				
				tag.setTagName(rs.getString("tag_name"));					
				tags.add(tag);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		
		return tags;
	}
	
	//method to get all tags for given book's isbn
	@Override
	public List<Tag> getAllTagsForABook(String isbn13) {
		List<Tag> tags = null;
		
		try {
			connection = DAOUtilities.getConnection();
			String sql ="SELECT * FROM book_tags WHERE isbn_13 = ?";
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, isbn13);
			
			ResultSet rs = stmt.executeQuery();
			tags = new ArrayList<>();
			Tag tag = null;
			
			while (rs.next()) {
				tag = new Tag();				
				tag.setTagName(rs.getString("tag_name"));					
				tags.add(tag);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		
		return tags;
	}
	
	//method to get all books for given tag's name
	public List<Book> getAllBooksForAtag(String tagName) {
		List<Book> books = null;			

		try {
			connection = DAOUtilities.getConnection();
			String sql ="SELECT * FROM book_tags WHERE tag_name = '"+tagName+"'";
			stmt = connection.prepareStatement(sql);			

			ResultSet rs = stmt.executeQuery();
			ResultSet rsBooks = null;
			
			Book book = null;
			books = new ArrayList<>();
			
			while (rs.next()) {
				sql ="SELECT * FROM books WHERE isbn_13 = '"+rs.getString("isbn_13")+"'";
				stmt = connection.prepareStatement(sql);
				rsBooks = stmt.executeQuery();
								
				while (rsBooks.next()) {
					book = new Book();
					book.setIsbn13(rsBooks.getString("isbn_13"));
					book.setTitle(rsBooks.getString("title"));
					book.setAuthor(rsBooks.getString("author"));
					book.setPublishDate(rsBooks.getDate("publish_date"));
					book.setPrice(rsBooks.getDouble("price"));
					books.add(book);
				}				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}

		return books;
	}

	/*------------------------------------------------------------------------------------------------*/
	private void closeResources() {
		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException e) {
			System.out.println("Could not close statement!");
			e.printStackTrace();
		}
		
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			System.out.println("Could not close connection!");
			e.printStackTrace();
		}
	}

}
