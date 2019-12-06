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
	
}
