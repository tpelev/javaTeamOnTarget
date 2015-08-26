package teams.accounting_module;

import java.util.List;

import javax.ejb.Stateless;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Advertisement;

@Stateless
@SessionScoped
public class AdvertisementEJB {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	//get All VIP Advertisements that are not Payed
	public List<Advertisement> showAllVipNonPayedAdvertisement(int id){
		List<Advertisement> advList = null;
		Query query = entityManager.createNamedQuery("Advertisement.findAllnVipAndPayedByCompany");
		query.setParameter("isVip", true);
		query.setParameter("isPayed", false);
		query.setParameter("hasInvoice", false);
		query.setParameter("id", id);
		advList = query.getResultList();
		
		return advList;
	}
	
	//update advertisement hasInvoice to true
	public void updateAdvertToHasInvoice(int id){
		Advertisement advTemp = entityManager.find(Advertisement.class, id);
		advTemp.setHasInvoice(true);
		entityManager.flush();
	}
	
	//make Advertisement paid
	public void updateAdvertToIsPayed(int id){
		Advertisement advTemp = entityManager.find(Advertisement.class, id);
		advTemp.setIsPaid(true);
		entityManager.flush();
	}
	
}
