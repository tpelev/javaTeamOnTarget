package teams.login_module;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;


@ManagedBean(name = "RegisterCompany")
@SessionScoped
@FacesValidator("RegisterCompany")
public class RegisterCompanyManagedBean {
	@EJB
	private RegisterCompanyEJB company;
	private String companyName;
	private String companyType;
	private String adress;
	private String email;
	private String eik;
	private String mol;
	private String loginName;
	private String loginPassword;
	private String sault;
	private String token;
	private String messagge;

	public String addCompany() {
		if (!company.companyExists(loginName)) {
				company.addCompany(this.companyName, this.companyType, this.adress, this.email, this.eik, this.mol,
						this.loginName, company.hashing(loginName, loginPassword), this.sault, this.token);
				return "LoginAsCompany.xhtml";
		}else{
			return "RegisterAsCompany.xhtml";
		}
		
	}
	
//	Needs new ejb
	
//	public void updateCompanyPassword(){
//		if (company.companyExists(loginName)) {
//			Random rnd = null;
//			String newPassword = company.generateNewPassword(rnd, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789", 10);
//			company.updatePassword(this.loginName, company.hashing(loginName, newPassword));
//		}
//	}
	
	

	public void isExists(FacesContext f, UIComponent c, Object obj) {
		if (company.companyExists(this.loginName)) {		
			throw new ValidatorException(new FacesMessage("Username already exists!"));
		}

	}

	public void validEmail(FacesContext f, UIComponent c, Object obj) {
		EmailValidator ev = new EmailValidator();
		ev.validate(f, c, obj);
	}
	
	public void validPass(FacesContext f, UIComponent c, Object obj){
		ConfirmPasswordValidator confirmPass = new ConfirmPasswordValidator();
		confirmPass.validate(f, c, obj);
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEik() {
		return eik;
	}

	public void setEik(String eik) {
		this.eik = eik;
	}

	public String getMol() {
		return mol;
	}

	public void setMol(String mol) {
		this.mol = mol;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
		this.test();
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

	public String getMessagge() {
		return messagge;
	}

	public void setMessagge(String messagge) {
		this.messagge = messagge;
	}
	
	public void test(){
		if (company.companyExists(this.loginName)) {
			this.setMessagge("Username already exists");
		}
		else{
			this.setMessagge("");
		}
	}
}
