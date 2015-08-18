package teams.accounting_module;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.entity.Owner;


@Singleton
public class OwnerEJB {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	public Owner getOwnerInformation(int id){
		Owner owner = entityManager.find(Owner.class, id);
		return owner;
	}
	

}
