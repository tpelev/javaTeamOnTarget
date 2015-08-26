package teams.login_module;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
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
						"Incorrect Administrator name or password!", "Please enter correct user name and password"));
				return "LoginAsAdmin";
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Incorrect Administrator name or password!", "Please enter correct user name and password"));
			return "LoginAsAdmin";
		}
	}
	public String logout(){
		HttpSession session = SessionBean.getSession();
		session.invalidate();
		return "MainPage.xhtml";
	}

	public void validate(ComponentSystemEvent e) {
		UIForm form = (UIForm) e.getComponent();
		UIInput nameInput = (UIInput) form.findComponent("username");
		UIInput pwdInput = (UIInput) form.findComponent("pass1");

		String userName = nameInput.getValue().toString();
		String pass1 = pwdInput.getValue().toString();

		try {
			Admin test = admin.getAdminUserName(userName);
			String hashedPass = admin.hashing(userName, pass1);
			if (!hashedPass.equals(test.getLoginPassword())) {
				FacesContext fc = FacesContext.getCurrentInstance();
				fc.addMessage(form.getClientId(), new FacesMessage("Invalid username or password."));
				fc.renderResponse();
			}
		} catch (Exception ex) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(form.getClientId(), new FacesMessage("Invalid username or password!"));
			fc.renderResponse();
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

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
