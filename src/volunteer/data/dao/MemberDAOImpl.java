package volunteer.data.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import volunteer.data.vo.MemberVO;


@Repository
public class MemberDAOImpl {
	
	@Autowired
	SqlSessionTemplate sqlSession;
	
	
	public void insertMember(MemberVO vo) {
		sqlSession.insert("member.insertVolMember", vo);
		
	}
}
