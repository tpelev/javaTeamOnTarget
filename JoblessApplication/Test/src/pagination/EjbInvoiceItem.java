package pagination;

import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import model.Invoice;


/**
 * EJB Class needed for JSF Pagination
 * @author Sava Savov
 *
 */
@Singleton
public class EjbInvoiceItem {
	
	@PersistenceContext
    private EntityManager em; //creating Entity Manager
  
	/**
	 * use a NamedQuery from Invoice JPA Class
	 * getting all Invoices from DB
	 * @return  List<Invoice>
	 */
	public List<Invoice> getAllItems(){		
		List<Invoice> invList = null;

		TypedQuery<Invoice> query = em.createQuery("Invoice.findAll",Invoice.class);		
		invList = query.getResultList();
		
		return invList;
	}
	
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public int count() {
    	
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<Invoice> rt = cq.from(Invoice.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
  
    /**
     * @param range
     * @return List<Invoice>
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
   public List<Invoice> findRange(int[] range) {
	   
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Invoice.class));
        Query q = em.createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

}
