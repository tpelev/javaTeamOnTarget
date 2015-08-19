package teams.login_module;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;


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
		boolean isValid = admin.validateUserNameAndPassword(getAdminUserName(), getPass());
		if (isValid) {
			HttpSession session = SessionBean.getSession();
			session.setAttribute("admin", userName);
			return "admin";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Incorect admin name or password!", "Please enter correct user name and password"));
			return "LoginAsAdmin";
		}
	}
	
	public String logout(){
		HttpSession session = SessionBean.getSession();
		session.invalidate();
		return "LoginAsAdmin";
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
