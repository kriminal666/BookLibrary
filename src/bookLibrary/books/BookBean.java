package bookLibrary.books;


import javax.faces.bean.ManagedBean;

 
@ManagedBean (name="bookBean")
public class BookBean {
	
	private String isbn;
	private String title;
	private String author;
	private short year;
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public short getYear() {
		return year;
	}
	public void setYear(short year) {
		this.year = year;
	}
	
	
	
	

}
