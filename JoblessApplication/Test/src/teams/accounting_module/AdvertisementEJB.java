package teams.accounting_module;

import java.util.List;

import javax.ejb.Stateless;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.Advertisement;



/**
 * EJB Class manipulating Advertisement JPA Class
 * 
 * @author Kaloyan Tsvetkov
 */
@Stateless
@SessionScoped
public class AdvertisementEJB {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * Method create a NamedQuery from Advertisement JPA Class "Advertisement.findAllnVipAndPayedByCompany"
	 * getting  All VIP Advertisements that are not Payed and don't have a invoice for them, by CompanyProfile id
	 * NamedQuery parameters are: isVip set to true, isPayed set to false, hasInvoice set to false and id set to metod param.
	 *  {@link Advertisement}
	 * @param id
	 * @return List<Advertisement>
	 * @author Kaloyan Tsvetkov
	 */
	public List<Advertisement> showAllVipNonPayedAdvertisement(int id){
		List<Advertisement> advList = null;
		//Create a NamedQuery that get All VIP Advertisements that are not Payed and dont have a invoice, by CopmanyProfile id
		Query query = entityManager.createNamedQuery("Advertisement.findAllnVipAndPayedByCompany"); 
		query.setParameter("isVip", true);
		query.setParameter("isPayed", false);
		query.setParameter("hasInvoice", false);
		query.setParameter("id", id);
		advList = query.getResultList();
		
		return advList;
	}
	
	/**
	 * Method update current Advertisement Object in DataBase
	 * set Column hasInvoice to true
	 * for writing in DB is used flush() method
	 * @param id
	 * @return void
	 * @author Kaloyan Tsvetkov
	 */
	public void updateAdvertToHasInvoice(int id){
		Advertisement advTemp = entityManager.find(Advertisement.class, id); //find Advertisement by primary key(int id)
		advTemp.setHasInvoice(true); //set Advertisement hasInvoice to true
		entityManager.flush(); //updating the Object in DataBase
	}
	
	/**
	 * Method update current Advertisement Object in DataBase
	 * set Column IsPaid to true
	 * for writing in DB is used flush() method
	 * @param id
	 * @return void
	 * @author Kaloyan Tsvetkov
	 */
	public void updateAdvertToIsPayed(int id){
		Advertisement advTemp = entityManager.find(Advertisement.class, id); //find Advertisement by primary key(int id)
		advTemp.setIsPaid(true); //set advertisement isPaid to true
		entityManager.flush(); //updating the Object in DataBase
	}
	
}
