package teams.login_module;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "RegisterCompany")
@SessionScoped
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
	private String loginPassword2;
	private String sault;
	private String token;
	
	public void addCompany(){
		if (!company.companyExists(loginName)) {
			if (this.loginPassword.equals(this.loginPassword2)){
				company.addCompany(this.companyName, this.companyType, this.adress, this.email, this.eik,
						this.mol, this.loginName, this.loginPassword, this.sault, this.token);
			}
		}
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
	}
	public String getLoginPassword() {
		return loginPassword;
	}
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
	public String getLoginPassword2() {
		return loginPassword2;
	}
	public void setLoginPassword2(String loginPassword2) {
		this.loginPassword2 = loginPassword2;
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
	
	

}
