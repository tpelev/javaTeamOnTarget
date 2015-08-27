package teams.login_module;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

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
	private String sault;
	private String token;
	private String msg;

	

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
		this.checkUserNameExisting();
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

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
		
	}
	
	public String addUser() {
		if (!registerUser.userExists(this.loginName)) {
			registerUser.addUser(this.firstName, this.lastName, this.email, this.loginName, 
					HashingAlgorithm.hashing(getLoginName(), getLoginPassword()),
					this.sault, this.token);
			return "loginAsUser.xhtml?faces-redirect=true";
		}else{
			return "loginRegisterAsUser.xhtml?faces-redirect=true";
		}

	}
	
	public void isExists(FacesContext f, UIComponent c, Object obj) {
		if (registerUser.userExists(this.loginName)) {		
			throw new ValidatorException(new FacesMessage("Username already exists!"));
		}

	}
	
	public void checkUserNameExisting(){
		if (registerUser.userExists(this.loginName)) {
			this.setMsg("Username already exists");
		}
		else{
			this.setMsg("");
		}
	}
	
}
