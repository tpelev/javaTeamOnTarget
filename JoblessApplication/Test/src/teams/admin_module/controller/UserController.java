package teams.admin_module.controller;

import java.util.List;

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
	@EJB
	private UserDaoBean userDaoBean;

	
	private PaginationHelper pagination;
	private List<UserProfile> list;
	private UserProfile userProfile;
	
	private int selectedItemIndex;

	private String firstName;
	private String lastName;
	private String email;

	
	@SuppressWarnings("rawtypes")
	private DataModel dtmdl = null;
	
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

                    return new ListDataModel(userDaoBean.findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public DataModel getdtmdl() {
        if (dtmdl == null) {
            dtmdl = getPagination().createPageDataModel();
        }
        return dtmdl;
    }
    private void recreateModel() {
        dtmdl = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    private void updateCurrentItem() {
        int count = userDaoBean.count();
        if (selectedItemIndex >= count) {

            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;

            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {

            	pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
        	userProfile = userDaoBean.findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
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

	public List<UserProfile> getList() {
		list = userDaoBean.showAllUsersProfiles();
		return list;
	}

	public void setList(List<UserProfile> list) {
		this.list = list;
	}

	public String updateUserProfile(UserProfile userProfile) {
		setUserProfile(userProfile);
		
		return "userUpdate.xhtml";
	}

	public String update() {
		userDaoBean.updateUserProfile(userProfile.getId(), getFirstName(), getLastName(), getEmail());
		dtmdl=null;
		getdtmdl();
		return "Users.xhtml";
	}

}
