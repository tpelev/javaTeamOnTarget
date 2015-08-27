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


import model.Admin;


@ManagedBean(name = "LoginAsAdminB")
@SessionScoped
public class LoginAsAdminMenagedBean {
	@EJB
	private AdminLoginEJB admin;

	public String adminUserName;
	private String pass;
	private String msg;

	
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
	
	public String loginAdmin() {

		String userName = this.getAdminUserName();

		String password = this.getPass();
		System.out.println("entered pass" + password);
		try {
			Admin test = admin.getAdminUserName(adminUserName);
			String hashedPass = HashingAlgorithm.hashing(userName, password);
			if (hashedPass.equals(test.getLoginPassword())) {
				HttpSession session = SessionBean.getSession();
				session.setAttribute("admin", userName);
				return "AdminPanel?faces-redirect=true";
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Incorrect Administrator name or password!", "Please enter correct user name and password"));
				return "loginAsAdmin?faces-redirect=true";
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Incorrect Administrator name or password!", "Please enter correct user name and password"));
			return "loginAsAdmin?faces-redirect=true";
		}
	}
	public String logout(){
		HttpSession session = SessionBean.getSession();
		session.invalidate();
		return "MainPage.xhtml?faces-redirect=true";
	}

	public void validate(ComponentSystemEvent e) {
		UIForm form = (UIForm) e.getComponent();
		UIInput nameInput = (UIInput) form.findComponent("username");
		UIInput pwdInput = (UIInput) form.findComponent("password");

		String userName = nameInput.getValue().toString();
		String password = pwdInput.getValue().toString();

		try {
			Admin adminUser = admin.getAdminUserName(userName);
			String hashedPass = HashingAlgorithm.hashing(userName, password);
			if (!hashedPass.equals(adminUser.getLoginPassword())) {
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
	
}
