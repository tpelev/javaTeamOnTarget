package teams.admin_module.controler;

import java.util.List;

import javax.ejb.Stateless;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.sun.tools.xjc.reader.xmlschema.bindinfo.BIConversion.User;

import model.UserProfile;

@Stateless
@SessionScoped
public class EJBUserRequest {

	@PersistenceContext
	private EntityManager entityManager;
	@SuppressWarnings("unchecked")
	public List<User> showAllUsers() {
		Query selectAllUsers = entityManager.createQuery("Select u from User u");
		List<User> listOfUsers = selectAllUsers.getResultList();
		return listOfUsers;
	}
	public List<UserProfile> showAllUsersProfiles() {
		Query selectAllUserProfile = entityManager.createQuery("Select u from UserProfile u");

		@SuppressWarnings("unchecked")
		List<UserProfile> listOfUserProfiles = selectAllUserProfile.getResultList();

		return listOfUserProfiles;
	}
	
	public void updateUserProfile (int id ,String firstName,String lastName,String email){
		
		UserProfile userProfile = entityManager.find(UserProfile.class, id);
		if(userProfile!=null){
		userProfile.setFirstName(firstName);
		userProfile.setLastName(lastName);
		userProfile.setEmail(email);
		entityManager.flush();
		}
	}
}
