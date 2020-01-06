package volunteer.data.dao;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import volunteer.data.vo.ListVO;
import volunteer.data.vo.MemberVO;

@Repository
public class VolListDaoImpl {
	@Autowired
	SqlSessionTemplate sqlSession;
	
	
	public void insert_vol_list(ListVO vo) {
		sqlSession.insert("list.insert",vo);
	}
	
	
}
