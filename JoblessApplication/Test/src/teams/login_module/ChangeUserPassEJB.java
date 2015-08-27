package teams.login_module;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.User;
import model.UserProfile;

/**
 * Session Bean implementation class ChangeUserPassEJB
 */
@Stateless

public class ChangeUserPassEJB {
	@PersistenceContext
	private EntityManager em;
	private String loginName;
	private String email;
	private int id;


	public String getFirstName() {
		return loginName;
	}

	public void setFirstName(String firstName) {
		this.loginName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Finds if the e-mail address of the current object exists in the database
	 * @param loginName holds login name
	 * @param email holds email
	 * @return boolean
	 * @author Tihomir_Pelev
	 * @author Slavka_Peleva
	 */
	public boolean findUserEmail(String loginName, String email) {
		boolean isExist = false;
		UserProfile up = findUserObject(loginName);
		if (up.getUser().getLoginName().equals(loginName) && up.getEmail().equals(email)) {
			setFirstName(up.getFirstName());
			setEmail(up.getEmail());
			setId(up.getUser().getId());
			isExist = true;
		}
		return isExist;
	}

	/**
	 * Getting user profile object corresponding to the given user name
	 * @param loginName holds login name
	 * @return UserProfile object
	 * @author Tihomir_Pelev
	 * @author Slavka_Peleva
	 */
	private UserProfile findUserObject(String loginName) {
		UserProfile up = new UserProfile();
		Query query = em.createQuery("SELECT up FROM UserProfile up where up.user.loginName='" + loginName + "'");
		
		up = (UserProfile) query.getSingleResult();
		return up;
	}
	
	/**
	 * Finds user's id and sets a new password
	 * @param password holds password
	 * @author Tihomir_Pelev
	 * @author Slavka_Peleva
	 */
	public void updatePass(String password) {
		User user = em.find(User.class, this.id);
		user.setLoginPassword(password);	
		em.persist(user);
	
	}

}
