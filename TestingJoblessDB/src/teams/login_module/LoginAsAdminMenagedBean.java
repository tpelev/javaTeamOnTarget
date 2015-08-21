package teams.login_module;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import model.Accounter;
import model.Admin;


@ManagedBean(name = "LoginAsAdminB")
@SessionScoped
public class LoginAsAdminMenagedBean {
	@EJB
	private AdminLoginEJB admin;

	public String adminUserName;
	private String pass;
	private String msg;

	public String loginAdmin() {

		String userName = this.getAdminUserName();

		String pass1 = this.getPass();
		System.out.println("entered pass" + pass1);
		try {
			Admin test = admin.getAdminUserName(adminUserName);
			String hashedPass = admin.hashing(userName, pass1);
			if (hashedPass.equals(test.getLoginPassword())) {
				HttpSession session = SessionBean.getSession();
				session.setAttribute("admin", userName);
				return "AdminPanel";
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Incorect Administrator name or password!", "Please enter correct user name and password"));
				return "LoginAsAdmin";
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Incorect Administrator name or password!", "Please enter correct user name and password"));
			return "LoginAsAdmin";
		}
	}
	public String logout(){
		HttpSession session = SessionBean.getSession();
		session.invalidate();
		return "MainPage";
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

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
