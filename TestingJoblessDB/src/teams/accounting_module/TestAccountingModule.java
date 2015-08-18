package teams.accounting_module;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.entity.Invoice;

public class TestAccountingModule {

	public static void main(String[] args) {
		
//		List<Invoice> invList = null;
//		
//		EntityManagerFactory entityManager = Persistence.createEntityManagerFactory("JoblessDB");
//		EntityManager manager = entityManager.createEntityManager();
//		manager.getTransaction().begin();
//
//		
//		Query query = manager.createNamedQuery("Invoice.findByInvoiceDate");
//		query.setParameter("invoiceDate", "2015-08-15");
//		
//		invList= query.getResultList();
//
//		manager.close();
//		entityManager.close();
//		for (Invoice invoice : invList) {
//			System.out.println(invoice.getId() + invoice.getOwner().getAdress() + invoice.getCompanyProfile().getCompanyName());
//		}
	}
}
