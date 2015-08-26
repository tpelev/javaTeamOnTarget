package teams.login_module;

import java.util.Random;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
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

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import model.Company;
import model.User;

@ManagedBean(name = "ChangeUserPass")
@SessionScoped
public class ChangeUserPassManagedBean {
	@EJB
	private ChangeUserPassEJB changePass;
	@EJB
	private RegisterUserEJB user;
	private String loginName;
	private String email;
	private String loginPassword;
	@Resource(name = "mailSession")
	private Session mailSession;

	public String generateNewPassword(Random rnd, String characters, int length) {
		char[] text = new char[length];
		for (int i = 0; i < length; i++) {
			text[i] = characters.charAt(rnd.nextInt(characters.length()));
		}
		setLoginPassword(new String(text));
		return this.loginPassword;
	}

	public static String hashing(String loginName, String loginPassword) {
		HashFunction hash = Hashing.sha256();
		String salt = hash.newHasher().putString(loginName, Charsets.UTF_8).hash().toString();
		String pass = hash.newHasher().putString(loginPassword, Charsets.UTF_8).hash().toString();
		HashCode hs = hash.newHasher().putString(pass, Charsets.UTF_8).putString(salt, Charsets.UTF_8).hash();
		return hs.toString();
	}

	public void updateUserPassword() {
		Random rnd = new Random();
		this.loginPassword = generateNewPassword(rnd, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789",
				10);
		String pass = hashing(this.loginName, this.loginPassword);
		changePass.updatePass(pass);
	}

	public String sendNewPassword() {
		if (changePass.findUserEmail(loginName, email)) {
			updateUserPassword();
			String userEmail = changePass.getEmail();
			StringBuilder sb = new StringBuilder();

			// company.updatePassword(this.companyUserName, loginPassword);
			sb.append("Your new password for joblessdb is: " + getLoginPassword());

			String to = userEmail;// change accordingly
			String from = "awwwwws@gmail.com";// change accordingly
			// String host = "localhost";//or IP address
			try {

				Message message = new MimeMessage(mailSession);
				message.setFrom(new InternetAddress(from));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
				message.setSubject("New password for your JOBLESS account");
				message.setText("You have requested new password from Jobless\n" + sb.toString());
				// Send message
				// 192.168.0.105:8080/Test/faces/emailconfirmation.xhtml
				Transport.send(message);
				System.out.println("message sent successfully....");

			} catch (MessagingException mex) {
				mex.printStackTrace();
			}
			return "LoginAsUser";
		}

		return "LoginAsUser";
	}

	public void validate(ComponentSystemEvent e) {
		UIForm form = (UIForm) e.getComponent();
		UIInput nameInput = (UIInput) form.findComponent("username");
		UIInput pwdInput = (UIInput) form.findComponent("email");
		try {
			String userName = nameInput.getValue().toString();
			String email = pwdInput.getValue().toString();

			try {
				User test = user.getAdminUserName(userName);
				if (!changePass.findUserEmail(userName, email)) {
					FacesContext fc = FacesContext.getCurrentInstance();
					fc.addMessage(form.getClientId(), new FacesMessage("Invalid username or email!"));
					fc.renderResponse();
				}

			} catch (Exception ex) {
				FacesContext fc = FacesContext.getCurrentInstance();
				fc.addMessage(form.getClientId(), new FacesMessage("Invalid username or email!"));
				fc.renderResponse();
			}
		} catch (Exception ex) {
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(form.getClientId(), new FacesMessage("Invalid username or email!"));
			fc.renderResponse();
		}

	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

}
