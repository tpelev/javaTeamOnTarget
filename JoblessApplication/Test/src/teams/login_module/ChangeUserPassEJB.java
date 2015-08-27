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
	 * @param loginName
	 * @return
	 */
	private UserProfile findUserObject(String loginName) {
		UserProfile up = new UserProfile();
		Query query = em.createQuery("SELECT up FROM UserProfile up where up.user.loginName='" + loginName + "'");
		
		up = (UserProfile) query.getSingleResult();
		return up;
	}
	
	public void updatePass(String password) {
		User user = em.find(User.class, this.id);
		user.setLoginPassword(password);	
		em.persist(user);
	
	}

}
