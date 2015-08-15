package model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class test {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestingJoblessDB");
		EntityManager em = emf.createEntityManager();
		Admin admin = new Admin();
		
		admin.setLoginName("admin");
		admin.setLoginPassword("12345");
		admin.setSault("admin");
		admin.setToken("");
//		Query query = em.createQuery("SELECT a from Admin a");
		em.getTransaction().begin();
		em.persist(admin);
		em.getTransaction().commit();
		em.close();
	}

}
