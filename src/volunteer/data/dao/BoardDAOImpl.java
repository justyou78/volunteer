package volunteer.data.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import volunteer.data.vo.BoardVO;

@Repository
public class BoardDAOImpl implements BoardDAO{

	@Autowired
	SqlSessionTemplate sqlSession;

	// 모든 게시글 찾기
	@Override
	public List<BoardVO> select() {
		List<BoardVO> vo = sqlSession.selectList("board.select");
		return vo;
	}
	
	// 번호로 게시글 찾기
	public BoardVO selectNum(int num) {
		BoardVO vo = sqlSession.selectOne("board.selectNum", num);
		return vo;
	}
	public List<BoardVO> selectAddName() {
	      List<BoardVO> vo = sqlSession.selectList("board.selectAddName");
	      return vo;
	   }
	
	// 글쓰기
	@Override
	public void insert(BoardVO vo) {
		sqlSession.insert("board.insert", vo);
	}
	
	// 글수정
	@Override
	public void update(BoardVO vo) {
		sqlSession.update("board.update", vo);
	}
	
	// 글삭제
	@Override
	public void delete(int num) {
		sqlSession.delete("board.delete", num);
	}
	
	// 조회수 증가
	public void updateViews(BoardVO vo) {
		sqlSession.update("board.updateViews", vo);
	}
}
