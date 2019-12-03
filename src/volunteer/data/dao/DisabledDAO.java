package volunteer.data.dao;

public interface DisabledDAO {
	//각 매서드에 매개변수와 리턴타입을 설정해주세요.
	
	public String makeMap(); // 지도를 만들때 사용할 메서드
	public String sendMessage(); //봉사자에게 메세지보낼때 메서드
	public String Filter(); //필터 설정할 때 메서드
	public String vol_info(); // 지도에서 봉사다 클릭시 정보보여주는 메서드
	
	// 메서드를 추가해주세요.
	
}
