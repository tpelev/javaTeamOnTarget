package teams.admin_module.controller;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import model.UserProfile;
import pagination.PaginationHelper;

@ManagedBean(name = "users")
@SessionScoped
public class UserController {
	// Instance
	@EJB
	private UserDaoBean userDaoBean;
	private PaginationHelper pagination;
	private UserProfile userProfile;

	// Fields
	private String firstName;
	private String lastName;
	private String email;

	// Pagination
	@SuppressWarnings("rawtypes")
	private DataModel dtmdl = null;

	public DataModel getdtmdl() {
		if (dtmdl == null) {
			dtmdl = getPagination().createPageDataModel();
		}
		return dtmdl;
	}

	private void recreateModel() {
		dtmdl = null;
	}

	public PaginationHelper getPagination() {

		if (pagination == null) {

			pagination = new PaginationHelper(3) {
				@Override
				public int getItemsCount() {
					return userDaoBean.count();
				}

				@SuppressWarnings({ "unchecked", "rawtypes" })
				@Override
				public DataModel createPageDataModel() {

					return new ListDataModel(userDaoBean
							.findRange(new int[] { getPageFirstItem(), getPageFirstItem() + getPageSize() }));
				}
			};
		}
		return pagination;
	}

	public String next() {
		getPagination().nextPage();
		recreateModel();
		return "home";
	}

	public String previous() {
		getPagination().previousPage();
		recreateModel();
		return "home";
	}
//	END of Pagination

	// Redirects to new .xhtml with form for update and setting Object from
	// class UserProfile
	public String updateUserProfile(UserProfile userProfile) {
		setUserProfile(userProfile);

		return "AdminUserUpdate.xhtml";
	}


	// Passing parameters to query for update UserProfile and refreshing the
	// dataTable
	public String update() {
		userDaoBean.updateUserProfile(userProfile.getId(), getFirstName(), getLastName(), getEmail());
		dtmdl = null;
		getdtmdl();
		return "AdminUsersPanel.xhtml";
	}
	
	
//	Set and Get methods
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

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}
//END of Set and Get methods

}
