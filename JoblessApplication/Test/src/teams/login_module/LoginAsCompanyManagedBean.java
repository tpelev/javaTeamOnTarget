package teams.login_module;

import java.util.Random;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import model.Accounter;
import model.Company;
import model.CompanyProfile;
import model.Test;
import model.User;
import model.UserProfile;
import teams.user_module.AddAdManagedBean;

@ManagedBean(name = "LoginAsCompany")
@SessionScoped
public class LoginAsCompanyManagedBean {
	@EJB
	private RegisterCompanyEJB company;
//	private CompanyProfile companyProfile;
	@ManagedProperty(value = "#{createAd}")
	private AddAdManagedBean addManagerBean;

	public String companyUserName;
	private String passCompany;
	@Resource(name = "mailSession")
	private Session mailSession;
	
//	private Random rnd;
	public String loginCompany() {

		String userName = this.getCompanyUserName();

		String pass1 = this.getPassCompany();
		System.out.println("entered pass" + pass1);

		try {
			Company test = company.getCompanyUserName(companyUserName);
			String hashedPass = company.hashing(userName, pass1);
			if (hashedPass.equals(test.getLoginPassword())) {
				HttpSession session = SessionBean.getSession();
				session.setAttribute("company", userName);

				addManagerBean.setCompanyUserName(userName);
				return "vip";
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Incorect Company name or password!", "Please enter correct user name and password"));
				return "LoginAsCompany";
			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Incorect Company name or password!", "Please enter correct user name and password"));
			return "LoginAsCompany";
		}
	}
	
	public void validate(ComponentSystemEvent e) {
		UIForm form = (UIForm) e.getComponent();
		UIInput nameInput = (UIInput) form.findComponent("username");
		UIInput pwdInput = (UIInput) form.findComponent("pass1");

		String userName = nameInput.getValue().toString();
		String pass1 = pwdInput.getValue().toString();

		try {
			Company test = company.getCompanyUserName(userName);
			String hashedPass = company.hashing(userName, pass1);
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
//	To be called from new xhtml file for company Password forgotten with input for username and e-mail. 
	
//	public String sendNewPassword() {
//		String companyEmail = companyProfile.getEmail();
//		StringBuilder sb = new StringBuilder();
//		
//		company.updatePassword(this.companyUserName, loginPassword);
//		sb.append("Your new password for joblessdb is: " 
//		+ company.u);

//		
//		String to = companyEmail;// change accordingly
//		String from = "awwwwws@gmail.com";// change accordingly
//		// String host = "localhost";//or IP address
//		try {
//
//			Message message = new MimeMessage(mailSession);
//			message.setFrom(new InternetAddress(from));
//			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//			message.setSubject("New password for your JOBLESS account");
//			message.setText("You have requested new password from Jobless\n" + sb.toString());
//			// Send message
//			Transport.send(message);
//			System.out.println("message sent successfully....");
//
//		} catch (MessagingException mex) {
//			mex.printStackTrace();
//		}
//		return "LoginAsCompany.xhtml";
//	}
	
	public String getCompanyUserName() {
		return companyUserName;
	}

//	public CompanyProfile getCompanyProfile() {
//		return companyProfile;
//	}
//
//	public void setCompanyProfile(CompanyProfile companyProfile) {
//		this.companyProfile = companyProfile;
//	}

	public void setCompanyUserName(String companyUserName) {
		this.companyUserName = companyUserName;
	}

	public String getPassCompany() {
		return passCompany;
	}

	public void setPassCompany(String passCompany) {
		this.passCompany = passCompany;
	}

	public AddAdManagedBean getAddManagerBean() {
		return addManagerBean;
	}

	public void setAddManagerBean(AddAdManagedBean addManagerBean) {
		this.addManagerBean = addManagerBean;
	}

	

}
