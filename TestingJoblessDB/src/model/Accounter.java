package model.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the accounters database table.
 * 
 */
@Entity
@Table(name="accounters")

@NamedQueries({
	@NamedQuery(name="Accounter.findAll", query="SELECT a FROM Accounter a"),
    @NamedQuery(name = "Accounter.findById", query = "SELECT a FROM Accounter a WHERE a.id = :id"),
    @NamedQuery(name = "Accounter.findByLoginName", query = "SELECT a FROM Accounter a WHERE a.loginName = :loginName")
})
public class Accounter implements Serializable {
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

	public Accounter() {
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

}