package examples.pubhub.dao;

import java.util.List;

import examples.pubhub.model.Book;
import examples.pubhub.model.Tag;

/**
* Interface for our Data Access Object to handle database queries related to Tags.
*/
public interface TagDAO {	
	public boolean addTag(String tagName);
	public boolean deleteTagByName(String tagName);
	public boolean addTagToBook(String tagName, String isbn13);
	public boolean addTagToBook(Book book);
	public boolean removeTagFromBook(String tagName, String isbn13);
	public List<Tag> getAllTags();
	public List<Tag> getAllTagsForABook(String isbn13);
	public List<Book> getAllBooksForAtag(String tagName);
}