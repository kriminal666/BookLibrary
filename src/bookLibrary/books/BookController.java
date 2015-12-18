package bookLibrary.books;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import org.hibernate.HibernateException;

import bookLibrary.constants.Constants;
import bookLibrary.entities.Book;
import bookLibrary.services.ModelDAO;
import bookLibrary.utils.Utils;

@ManagedBean (name="bookController")

public class BookController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Book book;
	
	private static final ModelDAO <Book> bookDAO = new ModelDAO<Book>(Book.class);
	
	private List <Book> bookList = new ArrayList<Book>();
	
	private List <Book> selectedBooks = new ArrayList<Book>();
	
     

	/**
	 * set BookBean to Book
	 * @param fromForm
	 */
	private void setBook(BookBean fromForm){
		
		book = new Book(fromForm.getIsbn(),fromForm.getTitle(),fromForm.getAuthor(),
				fromForm.getYear());	
		
	}
	
	/**
	 * Save new book in database
	 * @param fromForm
	 */
	public void save(BookBean fromForm){
		
		setBook(fromForm);
		boolean what = false;
		
		//Save to data base
		try{
		   bookDAO.insertObject(book);
		   what = true;
		   String [] msg = new String[2];
		   msg[0] = "Book";
		   msg[1] = "New Book Saved";
		   Utils.messageMaker(Constants.INSERT,Constants.SUCCESS_TRUE, msg);
		   
		}catch(HibernateException he){
			
			//send message
			Utils.messageMaker(Constants.INSERT,Constants.SUCCESS_FALSE,he.getCause().getMessage());
					
		}
		
		//Every thing has gone right, so clean form
		if(what){
			Utils.reset(Constants.NEW_BOOK_FORM);
		}
		
	}
	
	/**
	 * Set Book list to BookBean
	 * 
	 * @return
	 */
	private List<BookBean> setBookBean(){
		
		List <BookBean> bookBeanList= new ArrayList<BookBean>();
		
		for(Book book : getBookList()){
			
			BookBean bookBean = new BookBean();
			bookBean.setIsbn(book.getIsbn());
			bookBean.setAuthor(book.getAuthor());
			bookBean.setTitle(book.getTitle());
			bookBean.setYear(book.getYear());
			bookBeanList.add(bookBean);				
		}
		
		return bookBeanList;
	}
	
	
	/**
	 * Get the books list from database
	 * 
	 * @return
	 */
	private void  getAllBooks(){
		
		//Get books from database
		try{
			bookList = bookDAO.getObjects();
			
		}catch(HibernateException he){
			
			//send message
			Utils.messageMaker(Constants.RETRIVE_ALL,Constants.SUCCESS_FALSE,he.getCause().getMessage());	
			
		}
		
		
		
	}

	/**
	 * Get book list of view
	 * 
	 * @return
	 */
	public List <Book> getBookList() {
		
		if (bookList.size() == 0){
			getAllBooks();
		}	
		return bookList;
	}

	public List <Book> getSelectedBooks() {
		return selectedBooks;
	}

	public void setSelectedBooks(List <Book> selectedBooks) {
		this.selectedBooks = selectedBooks;
		
	}

	
	
	
	


}
