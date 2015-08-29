package teams.accounting_module;

import javax.ejb.Stateless;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.entity.Owner;


/**
 * EJB Class manipulating Owner JPA Class
 * 
 * @author Sergei Slavov
 */
@Stateless
@SessionScoped
public class OwnerEJB {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * Method to get Owner information fom DataBase
	 * @param id - Integer Owner id
	 * @return Owner Object
	 * @author Sergei Slavov
	 */
	public Owner getOwnerInformation(int id){
		Owner owner = entityManager.find(Owner.class, id); //find Owner by primary key(int id)
		return owner;
	}
	

}
