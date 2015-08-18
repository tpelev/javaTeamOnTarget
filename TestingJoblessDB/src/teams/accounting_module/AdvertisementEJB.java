package teams.accounting_module;

import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.entity.Advertisement;


@Singleton
public class AdvertisementEJB {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	//get All VIP Advertisements that are not Payed
	public List<Advertisement> showAllVipNonPayedAdvertisement(int id){
		List<Advertisement> advList = null;
		Query query = entityManager.createNamedQuery("Advertisement.findAllnVipAndPayed");
		query.setParameter("isVip", true);
		query.setParameter("isPayed", false);
		query.setParameter("id", id);
		advList = query.getResultList();
		
		return advList;
	}
	

}
