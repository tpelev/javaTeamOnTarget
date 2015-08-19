package teams.login_module;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import model.User;

@ManagedBean(name = "LoginAsUser")
@SessionScoped
public class LoginAsUserMenagedBean {
	@EJB
	private UserEJB user;

	public String userUserName;
	private String passUser;

	public void loginUser() {

		String userName = this.getUserUserName();

		String pass1 = this.getPassUser();
		System.out.println("entered pass" + pass1);

		try {
			User test = user.getAdminUserName(userUserName);
			if (pass1.equals(test.getLoginPassword())) {
				System.out.println("Success!! Welcome user!");
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public String getUserUserName() {
		return userUserName;
	}

	public void setUserUserName(String userUserName) {
		this.userUserName = userUserName;
	}

	public String getPassUser() {
		return passUser;
	}

	public void setPassUser(String passUser) {
		this.passUser = passUser;
	}

}
