package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the user_profiles database table.
 * 
 */
@Entity
@Table(name="user_profiles")
@NamedQueries({
    @NamedQuery(name = "UserProfile.findAll", query = "SELECT u FROM UserProfile u"),
    @NamedQuery(name = "UserProfile.findById", query = "SELECT u FROM UserProfile u WHERE u.id = :id"),
    @NamedQuery(name = "UserProfile.findByFirstName", query = "SELECT u FROM UserProfile u WHERE u.firstName = :firstName"),
    @NamedQuery(name = "UserProfile.findByLastName", query = "SELECT u FROM UserProfile u WHERE u.lastName = :lastName"),
    @NamedQuery(name = "UserProfile.findByAllNames", query = "SELECT u FROM UserProfile u WHERE u.firstName = :firstName AND u.lastName = :lastName")})
public class UserProfile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String email;

	@Column(name="FIRST_NAME")
	private String firstName;

	@Column(name="LAST_NAME")
	private String lastName;

	//bi-directional many-to-one association to User
	@ManyToOne
	private User user;

	public UserProfile() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}