package bookLibrary.entities;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

 
@Entity
@Table(name="books")
public class Book implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(unique = true)
	private String isbn;
	
	private String title;
	
	private String author;
	
	private short year;
	
	//Constructor
	
	public Book(){
		
		
	}
	
	
	public Book(String isbn, String title, String author, short year) {
		super();
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.year = year;
	}
	
	
	
	public Long getId() {
		return id;
	}


	private void setId(Long id) {
		this.id = id;
	}


	//Getters & Setters
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
