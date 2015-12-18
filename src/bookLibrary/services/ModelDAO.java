package bookLibrary.services;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import bookLibrary.hibernate.HibernateUtil;

/**
 *Hibernate DAO class
 *
 * @author criminal
 */
public class ModelDAO<T> {
   
 
final Class<T> typeParameterClass;
private Session session;
private Transaction tx;
 
/**
 * Constructor
 * 
 * @param typeParameterClass
 */
public ModelDAO(Class<T> typeParameterClass) {
   this.typeParameterClass = typeParameterClass;
}
 
/**
 * Insert one object in database
 * 
 * @param p
 * @return
 */
public long insertObject(T p){
        long id = 0;
        try{
            initOperation();
            id = (Long) session.save(p);
            tx.commit(); 
        } catch (HibernateException he){
            workException(he);
            throw he;
        } finally{
            session.close();
        }
          
     
    return id;
}
 
/**
 * Update one object from database
 * 
 * @param obj
 * @throws HibernateException
 */
public void UpdateObject(T obj) throws HibernateException{
        try{
            initOperation();
            session.update(obj);
            tx.commit();
        } catch (HibernateException he){
            workException(he);
            throw he;
        } finally{
            session.close();
        }
}
 
/**
 * Delete one object from database
 * 
 * @param obj
 * @throws HibernateException
 */
public void deleteObject(T obj) throws HibernateException{
        try{
            initOperation();
            session.delete(obj);
            tx.commit();
        } catch (HibernateException he){
            workException(he);
            throw he;
        } finally{
            session.close();
        }
}
 
/**
 * Get one object by id
 * 
 * @param id
 * @return
 * @throws HibernateException
 */
public T getObject(long id) throws HibernateException{
        T object = null;
        try{
            initOperation();

            object = (T) session.get(typeParameterClass, id);
        } finally{
            session.close();
        }
 
    return object;
}

/**
 * Get user for login
 * 
 * @param email
 * @return
 * @throws HibernateException
 */
@SuppressWarnings("unchecked")
public T getUserLogin(String email) throws HibernateException {
	T object = null;
	
	
	try{
		
		initOperation();
		Criteria crit = session.createCriteria(typeParameterClass);
		crit.add(Restrictions.eqOrIsNull("email", email));
		object = (T) crit.uniqueResult();
		
	}finally{
		session.close();
	}
	
	return object;
}
 
/**
 * Get all objects
 * 
 * @return
 * 
 */
public List<T> getObjects() {
        List<T> list = null;
 
        try{
            initOperation();
            list = session.createQuery("from "+typeParameterClass.getSimpleName()).list();
            
        } catch (HibernateException he){
            workException(he);
             throw he; 
        } finally{
            session.close();
            
        }
 
    return list;
}
 
 
private void initOperation() throws HibernateException{
	
	
	    session = HibernateUtil.getSessionFactory().openSession();
    
        tx = session.beginTransaction();
      
}
 
private void workException(HibernateException he) throws HibernateException{
        tx.rollback();
        //throw he;
}
}
