package teams.admin_module.controler;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import model.UserProfile;

@ManagedBean(name = "BeenUsers")
public class ManagerBeanForUsers {
	@EJB
	private EJBUserRequest ejbCompanies;
	
	private List<UserProfile> userList = new ArrayList<>();
	
	private String firstName;
	private String lastName;
	private String email;
	
	
	
	public String getFirstName() {
		return firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public String getLastName() {
		return lastName;
	}



	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public void setUserList(List<UserProfile> userList) {
		this.userList = userList;
	}

	public void update(UserProfile prof){
		ejbCompanies.update(prof.getId(), getFirstName(), getLastName(), getEmail());
		
	}
	
	public List<UserProfile> getUserList() {
		userList = ejbCompanies.showAllUsersProfiles();
		return userList;
	}

	
}
