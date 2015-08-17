package teams.login_module;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import model.Admin;

@ManagedBean(name="LoginAsAdmin")
public class LoginAsAdminMenagedBean {
	@EJB
	private AdminLoginEJB admin;
	
	private String userName;
	private String pass1;
	
	
	public void loginAdmin(){
		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAA");
		String userName = this.getUserName();
		String pass1 = this.getPass1();
		
		try {
			Admin test=admin.getAdminUserName(userName);
			if(pass1.equals(test.getLoginPassword())){
				System.out.println("Success!! Welcome admin!");
			}
			
			} catch (Exception e) {
				// TODO: handle exception
			}
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPass1() {
		return pass1;
	}
	public void setPass1(String pass1) {
		this.pass1 = pass1;
	}

	
	
}
