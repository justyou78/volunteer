package volunteer.data.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import volunteer.data.vo.ConnectVO;

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
	public void deleteConnect(String vol_id,String disabled_id) {
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("disabled_id",disabled_id );
		hm.put("vol_id",vol_id );
		sqlsession.delete("connect.deleteConnect", hm);
	}
	public void deleteConnect(String disabled_id) {
	
		sqlsession.delete("connect.deleteConnectFromId", disabled_id);
	}
	public List<ConnectVO> selectAll(String id){
		List<ConnectVO> list = sqlsession.selectList("connect.selectAll",id);
		
		return list;
		
	}
	
	public void updateCheck_vol(String vol_id, String disabled_id) {
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("disabled_id",disabled_id );
		hm.put("vol_id",vol_id );
		
		sqlsession.update("connect.insert_vol",hm);
		
	}
}
