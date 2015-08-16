package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class test {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		List<Place> accList = new ArrayList<>();
		System.out.println("Main?+++++++++++++++++++++++++++++++++++++++++++");
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestingJoblessDB");
		System.out.println("Sled factorito ------------------");
		EntityManager em = emf.createEntityManager();
		System.out.println("Sled managera ------------------");
//		Owner owner = new Owner();
//		owner.setAdress("Adress na ownera");
//		owner.setBic("BIC");
//		owner.setCompanyName("Owner's company");
//		owner.setEik("EIK OWNER");
//		owner.setEmail("owner@owner.bg");
//		owner.setIban("Owners IBAN");
//		owner.setMol("Owners MOL");
//		owner.setPhoneNumber("123456789");
//		Accounter acc = new Accounter();
//		acc.setLoginName("accounterusername");
//		System.out.println("Sled set name ------------------");
//		acc.setLoginPassword("accpassword");
//		acc.setSault("accsalt");
//		acc.setToken("");
		
//		System.out.println("Stiga li do querito +++++++++++++++++++++++++++++++++++++++++++++++");
		Query query = em.createQuery("SELECT pl from Place pl");
		em.getTransaction().begin();
	
		accList = query.getResultList();
//		em.persist(acc);
		em.getTransaction().commit();
		System.out.println(accList.size());
		for (Place place : accList) {
			System.out.println(place.getPlacesName() + ", ");
		}
		em.close();
		
	}

}
