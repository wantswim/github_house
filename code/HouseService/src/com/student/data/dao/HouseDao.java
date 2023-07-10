package com.student.data.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.student.data.service.BaseService;
import com.student.jdbc.JdbcUtils;

public class HouseDao implements BaseService {
	private JdbcUtils jdbcUtils;

	public HouseDao() {
		jdbcUtils = new JdbcUtils();
	}
	
	
	
	public List<Map<String, Object>> listRecommendPhoneHouse(List<Object> params) {

		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapResult;

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from xuqiutb  where  xuqiuUserId = ?";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, params);
			for (int i = 0; i < list.size(); i++) {
				mapResult = list.get(i);
				
				
				String[] ids = list.get(i).get("xuqiuHourseIds").toString().split(",");
				
				for(int a=0;a<ids.length;a++){
					
					List<Object> paramsFocus = new ArrayList<Object>();
					paramsFocus.clear();
					paramsFocus.add(ids[a] + "");
					Map<String, Object> houseMessage = queryHouse(paramsFocus);
					listResult.add(houseMessage);
				}
				

			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return listResult;
	}
	
	
	public boolean updateHouseInfor(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "update  xuqiutb set xuqiuHourseIds =? where xuqiuId = ?";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}
	
	
	public Map<String, Object> QueryStopCar(String xuqiuId) {
		Map<String, Object> map = null;
		String sql = "select * from xuqiutb   where  xuqiuId = '"+xuqiuId+"'  ";
		try {
			jdbcUtils.getConnection();
			map = jdbcUtils.findSimpleResult(sql, null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return map;
	}


	public List<Map<String, Object>> listrecommednMessage() {

		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from housemsg where userId = 'admin' ";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Map<String, Object>> listXuQiuMessage() {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from xuqiutb";
		System.out.println(sql);
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Map<String, Object>> listShopMessage() {

		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapResult;

		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from housemsg";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);

			for (int i = 0; i < list.size(); i++) {
				mapResult = list.get(i);
				List<Object> paramsFocus = new ArrayList<Object>();
				paramsFocus.clear();
				paramsFocus.add(list.get(i).get("houseCategory") + "");
				Map<String, Object> foodMsg = queryType(paramsFocus);
				mapResult.put("typeName", foodMsg.get("typeName"));
				listResult.add(mapResult);
			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	
	
	public boolean updateXuQIU(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "update  xuqiutb set xuqiuMoney =?,xuqiuMianJi =?,xuqiuAddress =? where xuqiuId = ?";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public boolean CheckXuQiu(List<Object> params) {
		boolean flag = false;
		String sql = "select * from xuqiutb where xuqiuUserId=?";
		try {
			jdbcUtils.getConnection();
			Map<String, Object> map = jdbcUtils.findSimpleResult(sql, params);
			flag = !map.isEmpty() ? true : false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public Map<String, Object> queryXuQiu(List<Object> params) {
		Map<String, Object> map = null;
		String sql = "select * from xuqiutb where xuqiuUserId=?";
		try {
			jdbcUtils.getConnection();
			map = jdbcUtils.findSimpleResult(sql, params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return map;
	}

	public List<Map<String, Object>> listHourseRecommendMessage(String moneyStart, String moneyEnd, String mianjiStart, String mianjiEnd,
			String zoomInfor) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		String sql = "";

		if (!moneyStart.equals("") && mianjiStart.equals("") && zoomInfor.equals("")) {
			sql = "select * from housemsg where  houseMoney between " + moneyStart + " and " + moneyEnd;
		}

		if (moneyStart.equals("") && !mianjiStart.equals("") && zoomInfor.equals("")) {
			sql = "select * from housemsg where   houseMessage between " + mianjiStart + " and " + mianjiEnd;
		}

		if (moneyStart.equals("") && mianjiStart.equals("") && !zoomInfor.equals("")) {
			sql = "select * from housemsg where  houseAddress='" + zoomInfor + "'";
		}

		if (!moneyStart.equals("") && !mianjiStart.equals("") && zoomInfor.equals("")) {
			sql = "select * from housemsg where  (houseMoney between " + moneyStart + " and " + moneyEnd + ")and (houseMessage between "
					+ mianjiStart + " and " + mianjiEnd + ")";
		}

		if (!moneyStart.equals("") && mianjiStart.equals("") && !zoomInfor.equals("")) {
			sql = "select * from housemsg where  houseAddress='" + zoomInfor + "' and (houseMoney between " + moneyStart + " and " + moneyEnd + ")";
		}

		if (moneyStart.equals("") && !mianjiStart.equals("") && !zoomInfor.equals("")) {
			sql = "select * from housemsg where  houseAddress='" + zoomInfor + "' and (houseMessage between " + mianjiStart + " and " + mianjiEnd
					+ ")";
		}

		if (!moneyStart.equals("") && !mianjiStart.equals("") && zoomInfor.equals("")) {
			sql = "select * from housemsg where  houseAddress='" + zoomInfor + "' and (houseMoney between " + moneyStart + " and " + moneyEnd
					+ ")and (houseMessage between " + mianjiStart + " and " + mianjiEnd + ")";
		}

		System.out.println(sql);
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public boolean addXuQiu(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "insert into xuqiutb (xuqiuMoney,xuqiuMianJi,xuqiuAddress,xuqiuUserId) values  (?,?,?,?)";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public List<Map<String, Object>> listMyUserOrderZeKe(List<Object> params) {

		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapResult;

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from applymsg  where  applyHouseUserId = ?";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, params);
			for (int i = 0; i < list.size(); i++) {
				mapResult = list.get(i);
				List<Object> paramsFocus = new ArrayList<Object>();
				paramsFocus.clear();
				paramsFocus.add(list.get(i).get("applyHouseId") + "");
				Map<String, Object> houseMessage = queryHouse(paramsFocus);
				mapResult.put("houseMessage", houseMessage);

				List<Object> paramsFocusUser = new ArrayList<Object>();
				paramsFocusUser.clear();
				paramsFocusUser.add(list.get(i).get("applyUserId") + "");
				Map<String, Object> queryUser = queryUser(paramsFocusUser);
				mapResult.put("userMessage", queryUser);

				listResult.add(mapResult);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public Map<String, Object> queryUser(List<Object> params) {
		Map<String, Object> map = null;
		String sql = "select * from user where uid=?";
		try {
			jdbcUtils.getConnection();
			map = jdbcUtils.findSimpleResult(sql, params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return map;
	}

	public List<Map<String, Object>> listMyUserOrder(List<Object> params) {

		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapResult;

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from applymsg  where  applyUserId = ?";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, params);
			for (int i = 0; i < list.size(); i++) {
				mapResult = list.get(i);
				List<Object> paramsFocus = new ArrayList<Object>();
				paramsFocus.clear();
				paramsFocus.add(list.get(i).get("applyHouseId") + "");
				Map<String, Object> houseMessage = queryHouse(paramsFocus);
				mapResult.put("houseMessage", houseMessage);
				listResult.add(mapResult);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public Map<String, Object> queryHouse(List<Object> params) {
		Map<String, Object> map = null;
		String sql = "select * from housemsg where houseId=?";
		try {
			jdbcUtils.getConnection();
			map = jdbcUtils.findSimpleResult(sql, params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return map;
	}

	public boolean updateOrderState(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "update  applymsg set applyState =? where applyId = ? and applyHouseUserId = ?";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public List<Map<String, Object>> listFbuUserMessage(String userId) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from housemsg where userId='" + userId + "'";
		System.out.println(sql);
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public boolean deletehouse(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "delete from housemsg where houseId=?";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public List<Map<String, Object>> listHourseMainMessage(String userId) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from housemsg";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Map<String, Object>> listHourseMessage(String moneyStart, String moneyEnd, String mianjiStart, String mianjiEnd, String houseType,
			String zoomInfor) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		String sql = "";

		if (zoomInfor.equals("")) {
			if (!moneyStart.equals("") && mianjiStart.equals("")) {
				sql = "select * from housemsg where houseType='" + houseType + "'  and houseMoney between " + moneyStart + " and " + moneyEnd;
			}

			if (moneyStart.equals("") && !mianjiStart.equals("")) {
				sql = "select * from housemsg where houseType='" + houseType + "'  and houseMessage between " + mianjiStart + " and " + mianjiEnd;
			}

			if (!moneyStart.equals("") && !mianjiStart.equals("")) {
				sql = "select * from housemsg where houseType='" + houseType + "' and (houseMoney between " + moneyStart + " and " + moneyEnd
						+ ")and (houseMessage between " + mianjiStart + " and " + mianjiEnd + ")";
			}

			if (moneyStart.equals("") && mianjiStart.equals("")) {
				sql = "select * from housemsg where houseType='" + houseType + "'";
			}

		} else {
			if (!moneyStart.equals("") && mianjiStart.equals("")) {
				sql = "select * from housemsg where houseType='" + houseType + "' and houseAddress='" + zoomInfor + "' and houseMoney between "
						+ moneyStart + " and " + moneyEnd;
			}

			if (moneyStart.equals("") && !mianjiStart.equals("")) {
				sql = "select * from housemsg where houseType='" + houseType + "' and houseAddress='" + zoomInfor + "' and houseMessage between "
						+ mianjiStart + " and " + mianjiEnd;
			}

			if (!moneyStart.equals("") && !mianjiStart.equals("")) {
				sql = "select * from housemsg where houseType='" + houseType + "' and houseAddress='" + zoomInfor + "' and (houseMoney between "
						+ moneyStart + " and " + moneyEnd + ")and (houseMessage between " + mianjiStart + " and " + mianjiEnd + ")";
			}

			if (moneyStart.equals("") && mianjiStart.equals("")) {
				sql = "select * from housemsg where houseType='" + houseType + "' and houseAddress='" + zoomInfor + "'";
			}

		}

		System.out.println(sql);
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public Map<String, Object> queryXieYi() {
		Map<String, Object> map = null;
		String sql = "select max(agreementId) as agreementId from agreementtb";
		try {
			jdbcUtils.getConnection();
			map = jdbcUtils.findSimpleResult(sql, null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return map;
	}

	public boolean addXieyi(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "insert into agreementtb (agreementJiaName,agreementYiName,agreementHouseId) values  (?,?,?)";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public boolean addOrder(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "insert into applymsg (applyUserId,applyUserName,applyHouseId,applyHouseName,applyHouseMoney,applyHouseUserId,applyTime,applyState,applyZLRealName,applyZLTime,agreementId) values  (?,?,?,?,?,?,?,?,?,?,?)";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public boolean addReviewMessage(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "insert into reviewmsg (reviewUserId,reviewUserName,reviewMessage," + "reviewTime,reviewMessageId) values  (?,?,?,?,?)";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);

		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public List<Map<String, Object>> listLookhouseMessage() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from lookHousemsg";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public boolean addLookHouse(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "insert into lookHousemsg (lookHouseMoney,lookHouseTypeId,lookHouseTypeName,lookHousePhone,"
					+ "lookHouseUserId,lookHouseUserName,lookHouseTime) values  (?,?,?,?,?,?,?)";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public boolean checkHouse(List<Object> params) {
		boolean flag = false;
		String sql = "select * from housemsg where houseName=? and houseCategory=? and userId = ?";
		try {
			jdbcUtils.getConnection();
			Map<String, Object> map = jdbcUtils.findSimpleResult(sql, params);
			flag = !map.isEmpty() ? true : false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public List<Map<String, Object>> queryMessage(String searchMsg) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapResult;

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from housemsg  ";
		// limit ?,?
		StringBuffer buffer = new StringBuffer(sql);
		List<Object> params = new ArrayList<Object>();
		if (searchMsg != null) {
			buffer.append("where  houseName like ?");
			params.add("%" + searchMsg + "%");
		}
		try {
			System.out.println(buffer.toString());
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(buffer.toString(), params);

			for (int i = 0; i < list.size(); i++) {
				mapResult = list.get(i);
				List<Object> paramsFocus = new ArrayList<Object>();
				paramsFocus.clear();
				paramsFocus.add(list.get(i).get("houseCategory") + "");
				Map<String, Object> foodMsg = queryType(paramsFocus);
				mapResult.put("typeName", foodMsg.get("typeName"));
				listResult.add(mapResult);
			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public Map<String, Object> queryType(List<Object> params) {
		Map<String, Object> map = null;
		String sql = "select * from typemsg where typeId=?";
		try {
			jdbcUtils.getConnection();
			map = jdbcUtils.findSimpleResult(sql, params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return map;
	}

	public boolean deletehobbymsg(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "delete from hobbymsg where hobbyId=?";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public List<Map<String, Object>> listHuoDongMessage() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from applymsg";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public boolean deleteInter(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "delete from interestmsg where interestId=?";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public List<Map<String, Object>> listInterestUserMessage(List<Object> params) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from interestmsg where interestUserId = ? order by interestId desc";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, params);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Map<String, Object>> listInterestMessage() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from interestmsg order by interestId desc";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public boolean addInterest(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "insert into interestmsg (interestHobby,interestMessage,interestUserId,interestUserName,interestTime,interestImage) values  (?,?,?,?,?,?)";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);

		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public int getItemCount(List<Object> params) {
		int result = 0;
		Map<String, Object> map = null;
		String sqlString = "select count(*) mycount FROM appointmentmsg where appointmentDoctorId = ? and appointmentTime=?;";
		// String sql = " select count(*) mycount from ordermsg ";
		try {
			jdbcUtils.getConnection();
			map = jdbcUtils.findSimpleResult(sqlString, params);
			result = Integer.parseInt(map.get("mycount").toString());
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		// TODO Auto-generated method stub
		return result;
	}

	public Map<String, Object> queryMRMessage(List<Object> params) {
		Map<String, Object> map = null;
		String sql = "select * from medicalRecordPatientmsg where medicalRecordPatientId=? and appointmentId=?";
		try {
			jdbcUtils.getConnection();
			map = jdbcUtils.findSimpleResult(sql, params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return map;
	}

	public boolean addMRMessage(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "insert into medicalRecordPatientmsg (medicalRecordPatientId,medicalRecordPatientName,medicalRecordPatientPhone,medicalRecordPatientTime,medicalRecordPatientMessage,medicalRecordDoctorId,medicalRecordDoctorName,appointmentId) values  (?,?,?,?,?,?,?,?)";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public List<Map<String, Object>> listDoctorAppointmentmsgMessage(List<Object> params) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from appointmentmsg where appointmentDoctorId = ?";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, params);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Map<String, Object>> listAppointmentmsgMessage(List<Object> params) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from appointmentmsg where appointmentUserId = ?";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, params);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public boolean addAppointment(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "insert into appointmentmsg (appointmentDoctorId,appointmentDoctorName,appointmentUserId,appointmentUserName,appointmentTime,appointmentNumber) values  (?,?,?,?,?,?)";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public List<Map<String, Object>> listSearchMessage(String proname) {

		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from navigation where (1=1) ";
		// limit ?,?
		StringBuffer buffer = new StringBuffer(sql);
		List<Object> params = new ArrayList<Object>();
		if (proname != null) {
			buffer.append(" and nName like ? ");
			params.add("%" + proname + "%");
		}
		try {
			jdbcUtils.getConnection();

			System.out.println(buffer.toString());
			list = jdbcUtils.findMoreResult(buffer.toString(), params);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public boolean updateMessage(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "update  review set rReplyContent =? where rId = ?";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	@Override
	public boolean addMessage(List<Object> params) {

		System.out.println(params.toString());
		boolean flag = false;
		try {
			String sql = "insert into housemsg (houseName,houseMoney,housePhone,houseMessage,houseCreatime,"
					+ "userId,userName,houseState,houseCategory,houseCategoryName,houseAddress,housePeiTao,houseType,houseImage) values  (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);

		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}

	public Map<String, Object> queryhourse(List<Object> params) {
		Map<String, Object> map = null;
		String sql = "select * from housemsg where houseCreatime= ? ";
		try {
			jdbcUtils.getConnection();
			map = jdbcUtils.findSimpleResult(sql, params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		return map;
	}

	public List<Map<String, Object>> listTopFood() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from bookmsg where fTopState = 2";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Map<String, Object>> listPhoneMessage() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from hobbymsg";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Map<String, Object>> listStudentMessage(List<Object> params) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from student where  sId = ?";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, params);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	public List<Map<String, Object>> listReviewMessage() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from review";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> listMessage() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "select * from interestmsg";
		try {
			jdbcUtils.getConnection();
			list = jdbcUtils.findMoreResult(sql, null);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			jdbcUtils.releaseConn();
		}
		return list;
	}

	@Override
	public boolean deleteMessage(List<Object> params) {
		boolean flag = false;
		try {
			String sql = "delete from bookmsg where flowerId=?";
			jdbcUtils.getConnection();
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
		} finally {
			jdbcUtils.releaseConn();
		}
		return flag;
	}
}
