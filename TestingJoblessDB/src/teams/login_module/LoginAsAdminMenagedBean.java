package teams.login_module;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import model.Admin;

@ManagedBean(name = "LoginAsAdminB")
@SessionScoped
public class LoginAsAdminMenagedBean {
	@EJB
	private AdminLoginEJB admin;

	public String adminUserName;
	private String pass;

	public void loginAdmin() {
		
		String userName = this.getAdminUserName();
		
		String pass1 = this.getPass();
		System.out.println("entered pass" + pass1);

		try {
			Admin test = admin.getAdminUserName(adminUserName);
			if (pass1.equals(test.getLoginPassword())) {
				System.out.println("Success!! Welcome admin!");
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public String getAdminUserName() {
		return adminUserName;
		
	}

	public void setAdminUserName(String name) {
		this.adminUserName = name;
		
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

}
