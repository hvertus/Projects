package examples.pubhub.dao;

import java.util.List;

import examples.pubhub.model.Tag;

public class TestTagDAO {	
	
	public static void main(String[] args){
		TagDAO dao = new TagDAOImpl();
		addTag(dao, "Tag_1");
		//addTagToBook(dao);		
		//displayAllTagsForABook(dao, "1010");
	}

	static void addTag(TagDAO dao, String tagName) {
		//dao = new TagDAOImpl();
		dao.addTag("dummy_tagname_2022");		
	}
	
	static void addTagToBook(TagDAO dao) {
		//dao = new TagDAOImpl();
		dao.addTagToBook("dummy_tagname_2021", "1010");		
	}	
	
	static void displayAllTagsForABook(TagDAO dao, String isbn_13) {
		//dao = new TagDAOImpl();
		List<Tag> tags = dao.getAllTagsForABook(isbn_13);
		for (int i = 0; i < tags.size(); i++){
			Tag t = tags.get(i);
			System.out.println(t.getTagName());
		}
	}

}
