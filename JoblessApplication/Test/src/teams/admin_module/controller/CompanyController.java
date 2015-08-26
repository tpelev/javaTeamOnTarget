package teams.admin_module.controller;

import java.util.List;

import javax.ejb.EJB;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import model.CompanyProfile;
import pagination.PaginationHelper;

@ManagedBean(name = "companies")
@SessionScoped
public class CompanyController 
{
	@EJB
	private CompanyDaoBean companyDaoBean;
	
	
	private CompanyProfile companyProfile;
	
	private List<CompanyProfile> list;
	

	
	private String address;
	private String companyName;
	private String MOL;
	private String EIK;
	private String email;
	private String companyType;
	
	private PaginationHelper pagination;
	private int selectedItemIndex;
	@SuppressWarnings("rawtypes")
	private DataModel dtmdl = null;
	
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

                    return new ListDataModel(companyDaoBean.findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
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
        int count = companyDaoBean.count();
        if (selectedItemIndex >= count) {

            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;

            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {

            	pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
        	companyProfile = companyDaoBean.findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
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
    
	public CompanyProfile getCompanyProfile() {
		return companyProfile;
	}
	public void setCompanyProfile(CompanyProfile companyProfile) {
		this.companyProfile = companyProfile;
	}
	public List<CompanyProfile> getList()
	{
		list = companyDaoBean.showAllCompanies();
		
		return list;
	}
	public void setList(List<CompanyProfile> list) {
		this.list = list;
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
	
	public String sendToUpdate(CompanyProfile companyProfile){
		
		setCompanyProfile(companyProfile);
		return "updateCompany.xhtml";
	}
	public String updateCompanyProfile (){
		companyDaoBean.updateCompany(companyProfile.getId(), getCompanyName(), getAddress(), getMOL(), getEIK(), getEmail(), getCompanyType());
		dtmdl=null;
		getdtmdl();
		return "Companies.xhtml";
	}
	public void updateAproval(CompanyProfile tempCompanyProfile){
		if (tempCompanyProfile.getStatus()!=1) {
			companyDaoBean.updateStatusAproval(tempCompanyProfile.getId());
			dtmdl=null;
			getdtmdl();
		}
	
	}
	
	public void notAproval(CompanyProfile tempCompanyProfile){
		if (tempCompanyProfile.getStatus()!=2 ){
			companyDaoBean.updateStatusNotAproval(tempCompanyProfile.getId());
			dtmdl=null;
			getdtmdl();
		}
	
	}
}
