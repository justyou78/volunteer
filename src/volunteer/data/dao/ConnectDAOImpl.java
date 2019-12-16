package volunteer.data.dao;

import java.util.HashMap;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ConnectDAOImpl implements ConnectDAO {
	@Autowired
	SqlSessionTemplate sqlsession;
	public void insert(String disabled_id,String vol_id) {
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("disabled_id",disabled_id );
		hm.put("vol_id",vol_id );
		
		sqlsession.insert("connect.insert",hm );
		
		
	
	}
	
	public void updateCheck_vol(String vol_id, String disabled_id) {
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("disabled_id",disabled_id );
		hm.put("vol_id",vol_id );
		
		sqlsession.update("connect.insert_vol",hm);
		
	}
}
