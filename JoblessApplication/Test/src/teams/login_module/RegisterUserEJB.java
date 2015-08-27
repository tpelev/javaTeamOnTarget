package teams.login_module;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.User;
import model.UserProfile;

/**
 * Session Bean implementation class RegisterUserEJB
 */
@Stateless
public class RegisterUserEJB {
	@PersistenceContext
	private EntityManager em;

	public void addUser(String firstName, String lastName, String email, String loginName, String loginPassword,
			String sault, String token) {
		UserProfile userProfile = new UserProfile();
		User user = new User();
		user.setLoginName(loginName);
		user.setLoginPassword(loginPassword);
		user.setSault("");
		user.setToken("");
		em.persist(user);
		userProfile.setFirstName(firstName);
		userProfile.setLastName(lastName);
		userProfile.setEmail(email);
		userProfile.setUser(user);
		em.persist(userProfile);
	}

	@SuppressWarnings("unchecked")
	public boolean userExists(String userName) {
		boolean userExists = false;
		Query query = em.createQuery("Select u from User u where u.loginName= '" + userName + "'");

		List<User> user = new ArrayList<>();
		user = query.getResultList();
		if (!user.isEmpty()) {
			userExists = true;
		}
		return userExists;
	}

	public User getUserUserName(String userName) {
		Query query = em.createQuery("SELECT u FROM User u WHERE u.loginName = '" + userName + "'");
		User user = (User) query.getSingleResult();
		return user;
	}
}
