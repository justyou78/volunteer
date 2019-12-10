package volunteer.data.dao;

import java.util.List;

import volunteer.data.vo.BoardVO;

public interface BoardDAO {
	public List<BoardVO> select();
	public void insert(BoardVO vo);
	public void update(BoardVO vo);
	public void delete(int num);
	
}
