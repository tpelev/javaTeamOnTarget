package teams.login_module;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

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
	
	public String hashing(String loginName, String loginPassword){
		HashFunction hash = Hashing.sha256();
		String salt = hash.newHasher().putString(loginName, Charsets.UTF_8).hash().toString();
		String pass = hash.newHasher().putString(loginPassword, Charsets.UTF_8).hash().toString();
		HashCode hs = hash.newHasher().putString(pass, Charsets.UTF_8).putString(salt, Charsets.UTF_8).hash();
		return hs.toString();
	}
	public User getAdminUserName(String userName){
		Query query = em.createQuery("SELECT u FROM User u WHERE u.loginName = '"+userName+"'");
		User user = (User) query.getSingleResult();
		return user;
	}
}
