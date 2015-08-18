package teams.accounting_module;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import model.entity.Accounter;


@Singleton
public class AccountEJB {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	//get log In result
	public boolean getLogIn(String userName, String pass){
	
		Query query = entityManager.createNamedQuery("Accounter.findByLoginName");
		query.setParameter("loginName", userName);
		Accounter acounter = (Accounter) query.getSingleResult();
		if(acounter!=null){
			boolean flag = getHashingPass(acounter, pass);
			if(flag){
				return true;
			}else{
				return false;
			}
		}else{
			return false;	
		}
		
	}
	
	//Hashing password
	private boolean getHashingPass(Accounter acounter, String pass){
		HashFunction hf = Hashing.sha256();
		HashCode hcPass = hf.newHasher().putString(pass, Charsets.UTF_8).hash();
		
		String finalPass = hcPass.toString() + acounter.getSault();
		HashCode hcFnalPass = hf.newHasher().putString(finalPass, Charsets.UTF_8).hash();
		if(acounter.getLoginPassword().equals(hcFnalPass.toString())){
			return true;
		}else{
			return false;
		}
	}
	

}
