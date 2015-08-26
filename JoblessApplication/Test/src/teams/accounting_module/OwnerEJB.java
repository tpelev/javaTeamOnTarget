package teams.accounting_module;

import javax.ejb.Singleton;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Owner;



@Singleton
@SessionScoped
public class OwnerEJB {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	//get owner information
	public Owner getOwnerInformation(int id){
		Owner owner = entityManager.find(Owner.class, id);
		return owner;
	}
	

}
