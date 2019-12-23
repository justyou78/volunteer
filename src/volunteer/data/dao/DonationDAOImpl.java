package volunteer.data.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import volunteer.data.vo.DonationVO;

@Repository
public class DonationDAOImpl {
	@Autowired
	 SqlSessionTemplate sqlSession;
	
	public List<DonationVO> select() {
	      List<DonationVO> list = sqlSession.selectList("donation.select");
	      return list;
	  }

}
