package bookLibrary.books;



import javax.faces.bean.ManagedBean;

import org.hibernate.HibernateException;
import org.primefaces.context.RequestContext;

import bookLibrary.constants.Constants;
import bookLibrary.entities.Book;
import bookLibrary.services.ModelDAO;
import bookLibrary.utils.Utils;

@ManagedBean (name="bookController")

public class BookController {
	
	private static Book book;
	
	private final ModelDAO <Book> bookDAO = new ModelDAO<Book>(Book.class);
	

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
	
	
	
	


}
