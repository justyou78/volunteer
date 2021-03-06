package volunteer.data.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import volunteer.data.vo.ListVO;
import volunteer.data.vo.MemberVO;


@Repository
public class MemberDAOImpl implements MemberDAO {
	
	@Autowired
	SqlSessionTemplate sqlSession;
	
	
	public void insert(MemberVO vo) {
		sqlSession.insert("member.insertVolMember", vo);
	}
	public String selectID(String id) {
		return sqlSession.selectOne("member.selectID",id);
		
	}
	
	public void update(MemberVO vo) {
		sqlSession.update("member.updateVolMember", vo);
	}
	public String getName(String id) {
		return sqlSession.selectOne("member.getName", id);
	}
	public MemberVO selectAll(String id) {
		return sqlSession.selectOne("member.selectAll", id);
	}
	public void updateInfo(MemberVO vo) {
		if(vo.getMember_type().equals("1")) {
			sqlSession.update("member.updateVol",vo);
		}
		else {
			sqlSession.update("member.updateDisabled",vo);
			
		}
	}
	public void updateVolTime(ListVO vo) {
		sqlSession.update("member.updateVolTime",vo);
	}
	public void updatePoint(String give_star,String vol_id) {
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("give_star", give_star);
		hm.put("vol_id", vol_id);
		
		sqlSession.update("member.updatePoint",hm);
	}
	
	public List<String> selectAllID(){
		List<String> id = sqlSession.selectList("member.selectAllID");
		return id;
	}
	public List selectVolFromAddress(HashMap<String, String> hm) {
		List<MemberVO> list = sqlSession.selectList("member.selectVolFromAddress", hm);
		return list;
	}
	public MemberVO selectAllFromEmail(String email) {
		return sqlSession.selectOne("member.selectAllFromEmail",email);
	}
	public List<MemberVO> selectAllDescTime() {
		List<MemberVO> list = new ArrayList<MemberVO>();
		list = sqlSession.selectList("member.selectAllDescTime");
		return list;
	}
	
}
