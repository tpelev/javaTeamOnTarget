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

	/**
	 * Adds user in the database
	 * 
	 * @param  firstName  user's first name
	 * @param  lastName  user's last name
	 * @param  email  user's email
	 * @param  loginName user's login name
	 * @param  loginPassword - user's login password
	 * @param  sault - salt
	 * @param  token  token
	 * @author Slavka_Peleva
	 * @author Galina_Petrova
	 */
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

	/**
	 * Checks if the user exists in the database
	 * 
	 * @param  userName holds username from database
	 * @return boolean
	 * @author Slavka_Peleva
	 * @author Galina_Petrova
	 */
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

	/**
	 * Gets user's user name
	 * @param  userName holds the username from the database
	 * @return user
	 * @author Slavka_Peleva
	 * @author Galina_Petrova
	 */
	public User getUserUserName(String userName) {
		Query query = em.createQuery("SELECT u FROM User u WHERE u.loginName = '" + userName + "'");
		User user = (User) query.getSingleResult();
		return user;
	}
}
