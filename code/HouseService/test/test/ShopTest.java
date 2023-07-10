package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.junit.Test;

import com.student.data.dao.HouseDao;
import com.student.data.dao.ShopDao;

public class ShopTest {

	private HouseDao shopDao;

	@Test
	public void Reg() {
		shopDao = new HouseDao();
		List<Object> params = new ArrayList<Object>();
		params.add("19");
		List<Map<String, Object>> list = shopDao.listRecommendPhoneHouse(params);
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "ok");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		 

	}
}
