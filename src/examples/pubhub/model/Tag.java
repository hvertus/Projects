package examples.pubhub.model;

import java.util.List;

public class Tag {
	private String tagName;
	
	private List<Book> books;
	
	public Tag() {
	}

	public Tag(String tagName) {
		this.tagName = tagName;
	}
	
	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
	public List<Book> getBooks() {
		return this.books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}
}