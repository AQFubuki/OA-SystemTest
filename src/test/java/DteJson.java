import com.fubuki.fubukioa.service.LeaveFormService;
import com.fubuki.fubukioa.utils.ChangeLocalDateTime;
import com.fubuki.fubukioa.utils.ResponseUtils;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DteJson {
    @Test
    public void Test1() {
        LeaveFormService leaveFormService = new LeaveFormService();
        List<Map> formList = leaveFormService
                .getLeaveFormList("process", 6l);
        if (formList.isEmpty()) {
            System.out.println("kkkkkkkkk n ");
        }
        for (Map map : formList) {
            System.out.println(map);
            Date start_time = ChangeLocalDateTime
                    .changeToDate((LocalDateTime) map.get("start_time"));
            Date end_time = ChangeLocalDateTime
                    .changeToDate((LocalDateTime) map.get("end_time"));
            Date create_time = ChangeLocalDateTime
                    .changeToDate((LocalDateTime) map.get("create_time"));
            map.replace("create_time", create_time);
            map.replace("start_time", start_time);
            map.replace("end_time", end_time);
            System.out.println(create_time);
            //System.out.println("tttt"+create_time.getTime());
            //Date c2=new Date(Long.parseLong(String.valueOf(create_time.getTime())));
            //System.out.println(c2);
            System.out.println("====================");
        }
        ResponseUtils resp = new ResponseUtils().put("date", new Date())
                .put("list", formList);
        System.out.println(resp.toJosnString());
    }
}
