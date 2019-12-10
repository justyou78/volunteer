package volunteer.data.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
		System.out.println("updateInfo"+ vo.getMember_type());
		if(vo.getMember_type().equals("1")) {
			System.out.println("TEST");
			sqlSession.update("member.updateVol",vo);
		}
		else {
			sqlSession.update("member.updateDisabled",vo);
			
		}
	}
	
}
