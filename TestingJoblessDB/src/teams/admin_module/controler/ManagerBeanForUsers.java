package teams.admin_module.controler;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import model.UserProfile;

@ManagedBean(name = "BeenUsers")
@SessionScoped
public class ManagerBeanForUsers {
	@EJB
	private EJBUserRequest ejbCompaniesForUsers;
	
	private List<UserProfile> listOfUsers = new ArrayList<>();
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
		this.listOfUsers = userList;
	}

	public void updateUserProfile(UserProfile userProfile){
		if(userProfile!=null)
		ejbCompaniesForUsers.updateUserProfile(userProfile.getId(), getFirstName(), getLastName(), getEmail());
		
	}
	
	public List<UserProfile> getUserList() {
		listOfUsers = ejbCompaniesForUsers.showAllUsersProfiles();
		return listOfUsers;
	}

	
}
