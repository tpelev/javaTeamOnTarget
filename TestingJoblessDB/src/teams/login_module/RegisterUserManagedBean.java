package teams.login_module;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "RegisterUser")
@SessionScoped
public class RegisterUserManagedBean {
	@EJB
	private RegisterUserEJB registerUser;
	private String firstName;
	private String lastName;
	private String email;	
	private String loginName;
	private String loginPassword;
	private String loginPassword2;
	private String sault;
	private String token;
	
	public void addUser(){
		if (!registerUser.userExists(this.loginName)) {
			if (this.loginPassword.equals(this.loginPassword2)) {
				registerUser.addUser(this.firstName, this.lastName, this.email, this.loginName, this.loginPassword, this.sault, this.token);
			}
			
		}
		
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getLoginPassword() {
		return loginPassword;
	}
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
	public String getSault() {
		return sault;
	}
	public void setSault(String sault) {
		this.sault = sault;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getLoginPassword2() {
		return loginPassword2;
	}
	public void setLoginPassword2(String loginPassword2) {
		this.loginPassword2 = loginPassword2;
	}
	
	
	
}
