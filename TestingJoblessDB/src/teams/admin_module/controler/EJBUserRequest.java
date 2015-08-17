package teams.admin_module.controler;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.sun.tools.xjc.reader.xmlschema.bindinfo.BIConversion.User;

import model.UserProfile;

@Stateless
public class EJBUserRequest {

	@PersistenceContext
	private EntityManager entityManager;

	public List<User> showAllUsers() {
		Query query = entityManager.createQuery("Select u from User u");

		@SuppressWarnings("unchecked")
		List<User> list = query.getResultList();

		return list;
	}
	public List<UserProfile> showAllUsersProfiles() {
		Query query = entityManager.createQuery("Select u from UserProfile u");

		@SuppressWarnings("unchecked")
		List<UserProfile> list = query.getResultList();

		return list;
	}
	public void update (int id ,String firstName,String lastName,String email){
		
		UserProfile profile = entityManager.find(UserProfile.class, id);
		profile.setFirstName(firstName);
		profile.setLastName(lastName);
		profile.setEmail(email);
		entityManager.flush();
	}
}
