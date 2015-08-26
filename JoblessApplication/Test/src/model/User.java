package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
    @NamedQuery(name = "User.findByLoginName", query = "SELECT u FROM User u WHERE u.loginName = :loginName")
})
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="LOGIN_NAME")
	private String loginName;

	@Column(name="LOGIN_PASSWORD")
	private String loginPassword;

	private String sault;

	private String token;

	//bi-directional many-to-one association to UserProfile
	@OneToMany(mappedBy="user", fetch=FetchType.EAGER)
	private List<UserProfile> userProfiles;

	public User() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPassword() {
		return this.loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getSault() {
		return this.sault;
	}

	public void setSault(String sault) {
		this.sault = sault;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<UserProfile> getUserProfiles() {
		return this.userProfiles;
	}

	public void setUserProfiles(List<UserProfile> userProfiles) {
		this.userProfiles = userProfiles;
	}

	public UserProfile addUserProfile(UserProfile userProfile) {
		getUserProfiles().add(userProfile);
		userProfile.setUser(this);

		return userProfile;
	}

	public UserProfile removeUserProfile(UserProfile userProfile) {
		getUserProfiles().remove(userProfile);
		userProfile.setUser(null);

		return userProfile;
	}

}