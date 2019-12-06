package volunteer.data.dao;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.rosuda.REngine.Rserve.RConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import volunteer.data.vo.ListVO;

@Repository
public class VolunteerDAOImpl implements VolunteerDAO{
	public String getGraph(String id,String sysdate) {
		
		
		
		
		String imgName =null;
		System.out.println(sysdate+"가져온날짜.");
		try {
			
			RConnection r = new RConnection();
			String sql = "SELECT * FROM VOL_LIST WHERE VOL_ID="+id +" and  TO_CHAR(time, 'yyyy')="+"'"+sysdate+"'";
			System.out.println(sql);
			imgName=  id+"statistics"+sysdate+".jpg";
			System.out.println(imgName+"이미지이름");
			
			
			System.out.println("test");
			r.eval("library(RJDBC)");
			r.eval("library(dplyr)");
			r.eval("dri <- JDBC('oracle.jdbc.driver.OracleDriver','C:/Spring_An/work/volunteer/WebContent/WEB-INF/lib/ojdbc6.jar')");
			r.eval("conn <- dbConnect(dri, 'jdbc:oracle:thin:@nullmaster.iptime.org:1521:xe','final04','final04')");
			r.eval("dbListTables(conn)");
			System.out.println("vol_list <- dbGetQuery(conn, \""+sql+"\")");
			r.eval("vol_list <- dbGetQuery(conn, \""+sql+"\")");
			r.eval("bind<- cbind(vol_list, year_month=as.character(as.Date(vol_list$TIME),'%y년%m월'))");
			r.eval("group <-bind %>% group_by(year_month) %>%  summarise(sum_time=sum(VOL_TIME))");
			r.eval("png('C:/Spring_An/work/.metadata/.plugins/org.eclipse.wst.server.core/tmp7/wtpwebapps/volunteer/img/graphimg/"+imgName+"')");
			r.eval("barplot(group$sum_time,names.arg = group$year_month, main='월별 봉사활동' ,col=rainbow(12),legend.text =group$year_month)");
			r.eval("dev.off()");
			r.eval("dbDisconnect(conn)");
		
			
			
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return imgName;
	}
	
	
}
