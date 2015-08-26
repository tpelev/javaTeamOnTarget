package teams.admin_module.controller;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import model.CompanyProfile;
import pagination.PaginationHelper;

@ManagedBean(name = "companies")
@SessionScoped
public class CompanyController {
	// Instance
	@EJB
	private CompanyDaoBean companyDaoBean;
	private PaginationHelper pagination;	
	private CompanyProfile companyProfile;

	
	private String address;
	private String companyName;
	private String MOL;
	private String EIK;
	private String email;
	private String companyType;

	// Pagination
	@SuppressWarnings("rawtypes")
	private DataModel dtmdl = null;

	// Dynamically filling dataTable
	@SuppressWarnings("rawtypes")
	public DataModel getdtmdl() {
		if (dtmdl == null) {
			dtmdl = getPagination().createPageDataModel();
		}
		return dtmdl;
	}

	public PaginationHelper getPagination() {

		if (pagination == null) {

			pagination = new PaginationHelper(3) {
				@Override
				public int getItemsCount() {
					return companyDaoBean.count();
				}

				@SuppressWarnings({ "unchecked", "rawtypes" })
				@Override
				public DataModel createPageDataModel() {

					return new ListDataModel(companyDaoBean
							.findRange(new int[] { getPageFirstItem(), getPageFirstItem() + getPageSize() }));
				}
			};
		}
		return pagination;
	}

	private void recreateModel() {
		dtmdl = null;
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
	// END of Pagination

	// Redirects to new .xhtml with form for update and setting Object from
	// class CompanyProfile
	public String sendToUpdate(CompanyProfile companyProfile) {

		setCompanyProfile(companyProfile);
		return "AdminUpdateCompany.xhtml";
	}

	// Passing parameters to query for update CompanyProfile and refreshing the
	// dataTable
	public String updateCompanyProfile() {
		companyDaoBean.updateCompany(companyProfile.getId(), getCompanyName(), getAddress(), getMOL(), getEIK(),
				getEmail(), getCompanyType());
		dtmdl = null;
		getdtmdl();
		return "AdminCompaniesPanel.xhtml";
	}

	// Query for setting cow "status" to "1" ("1" is Approve) in dataBase
	public void updateAproval(CompanyProfile tempCompanyProfile) {
		if (tempCompanyProfile.getStatus() != 1) {
			companyDaoBean.updateStatusAproval(tempCompanyProfile.getId());
			dtmdl = null;
			getdtmdl();
		}

	}

	// Query for setting cow "status" to "2" ("2" is notApprove) in dataBase
	public void notAproval(CompanyProfile tempCompanyProfile) {
		if (tempCompanyProfile.getStatus() != 2) {
			companyDaoBean.updateStatusNotAproval(tempCompanyProfile.getId());
			dtmdl = null;
			getdtmdl();
		}

	}

	// Set and Get methods
	public CompanyProfile getCompanyProfile() {
		return companyProfile;
	}

	public void setCompanyProfile(CompanyProfile companyProfile) {
		this.companyProfile = companyProfile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getMOL() {
		return MOL;
	}

	public void setMOL(String mOL) {
		MOL = mOL;
	}

	public String getEIK() {
		return EIK;
	}

	public void setEIK(String eIK) {
		EIK = eIK;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}
	// End of Set Get methods
}
