package volunteer.board.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import volunteer.data.dao.BoardDAOImpl;
import volunteer.data.vo.BoardVO;

@Controller
@RequestMapping("/board/")
public class BoardAction {
	
	@Autowired
	BoardDAOImpl dao;
	
	// 리스트
	@RequestMapping("boardList")
	public String boardList(Model model) {
		List<BoardVO> vo = dao.select();
		model.addAttribute("vo", vo);
		return "board/boardList";
	}
	
	// 게시글
	@RequestMapping("boardContent")
	public String boardContent(Model model, int num) {
		BoardVO vo = dao.selectNum(num);		
		model.addAttribute("vo", vo);
		
		// 조회수 증가
		vo.setViews(vo.getViews()+1);
		dao.updateViews(vo);
		
		return "board/boardContent";
	}
	
	// 글쓰기
	@RequestMapping("boardWriteForm")
	public String boardWriteForm() {
		return "board/boardWriteForm";
	}
	@RequestMapping("boardWritePro")
	public String boardWritePro(BoardVO vo) {
		dao.insert(vo);
		return "board/boardWritePro";
	}
	
	// 게시글 수정
	@RequestMapping("boardModifyForm")
	public String boardModifyForm(Model model, int num) {
		BoardVO vo = dao.selectNum(num);
		model.addAttribute("vo", vo);
		return "board/boardModifyForm";
	}
	@RequestMapping("boardModifyPro")
	public String boardModifyPro(BoardVO vo) {
		dao.update(vo);
		return "board/boardModifyPro";
	}
	
	// 게시글 삭제
	@RequestMapping("boardDeleteForm")
	public String boardDeleteForm(Model model, int num) {
		model.addAttribute("num", num);
		return "board/boardDeleteForm";
	}
	@RequestMapping("boardDeletePro")
	public String boardDeletePro(int num) {
		dao.delete(num);
		return "board/boardDeletePro";
	}
}