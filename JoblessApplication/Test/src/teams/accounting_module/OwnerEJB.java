package teams.accounting_module;

import javax.ejb.Stateless;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Owner;



/**
 * EJB Class manipulating Owner JPA Class
 * 
 * @author Kaloyan Tsvetkov
 */
@Stateless
@SessionScoped
public class OwnerEJB {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * Method to get Owner information fom DataBase
	 * @param id
	 * @return Owner Object
	 * @author Kaloyan Tsvetkov
	 */
	public Owner getOwnerInformation(int id){
		Owner owner = entityManager.find(Owner.class, id); //find Owner by primary key(int id)
		return owner;
	}
	

}
