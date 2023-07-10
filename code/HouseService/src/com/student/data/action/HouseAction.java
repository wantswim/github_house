package com.student.data.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.student.data.dao.HouseDao;
import com.student.jdbc.Consts;
import com.student.util.PingYinUtil;

public class HouseAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletFileUpload upload;
	private final long MAXSize = 4194304 * 2L;// 4*2MB
	private String filedir = null;
	private HouseDao hobbyDao;

	public HouseAction() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String path = request.getContextPath();
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		String action_flag = request.getParameter("action_flag");
		if (action_flag.equals("queryMRMessage")) {
			queryMRMessage(request, response);
		} else if (action_flag.equals("addMessage")) {
			addMessage(request, response);
		} else if (action_flag.equals("listInterestMessage")) {
			listInterestMessage(request, response);
		} else if (action_flag.equals("listInterestUserMessage")) {
			listInterestUserMessage(request, response);
		} else if (action_flag.equals("deleteInterest")) {
			deleteInterest(request, response);
		} else if (action_flag.equals("deletePcInterest")) {
			deletePcInterest(request, response);
		} else if (action_flag.equals("listHuoDongMessage")) {
			listHuoDongMessage(request, response);
		} else if (action_flag.equals("deleteHobby")) {
			deleteHobby(request, response);
		} else if (action_flag.equals("queryMessage")) {
			queryMessage(request, response);
		} else if (action_flag.equals("addLookHouse")) {
			addLookHouse(request, response);
		} else if (action_flag.equals("LookHousePhone")) {
			LookHousePhone(request, response);
		} else if (action_flag.equals("addReviewMessage")) {
			addReviewMessage(request, response);
		} else if (action_flag.equals("addAply")) {
			addAply(request, response);
		} else if (action_flag.equals("addXieyi")) {
			addXieyi(request, response);
		} else if (action_flag.equals("listHourseMessage")) {
			listHourseMessage(request, response);
		} else if (action_flag.equals("deletehouse")) {
			deletehouse(request, response);
		} else if (action_flag.equals("listFbuUserMessage")) {
			listFbuUserMessage(request, response);
		} else if (action_flag.equals("updateOrderState")) {
			updateOrderState(request, response);
		} else if (action_flag.equals("listOrderPC")) {
			listOrderPC(request, response);
		} else if (action_flag.equals("listZoom")) {
			listZoom(request, response);
		} else if (action_flag.equals("myapplyMessage")) {
			myapplyMessage(request, response);
		} else if (action_flag.equals("listMyUserOrderZeKe")) {
			listMyUserOrderZeKe(request, response);
		} else if (action_flag.equals("addXuQiu")) {
			addXuQiu(request, response);
		} else if (action_flag.equals("listHourseRecommendMessage")) {
			listHourseRecommendMessage(request, response);
		} else if (action_flag.equals("checkXuQiuInfor")) {
			checkXuQiuInfor(request, response);
		} else if (action_flag.equals("updateXuQIU")) {
			updateXuQIU(request, response);
		}else if (action_flag.equals("addHourseMessage")) {
			addHourseMessage(request, response);
		}else if (action_flag.equals("listHouseMessage")) {
			listHouseMessage(request, response);
		}else if (action_flag.equals("listXuQiuMessage")) {
			listXuQiuMessage(request, response);
		}else if (action_flag.equals("listRecommendMessage")) {
			listRecommendMessage(request, response);
		} else if (action_flag.equals("updateHouseInfor")) {
			updateHouseInfor(request, response);
		} else if (action_flag.equals("listRecommendPhoneHouse")) {
			listRecommendPhoneHouse(request, response);
		}

	}

	public void init(ServletConfig config) throws ServletException {
		FileItemFactory factory = new DiskFileItemFactory();// Create a factory
		this.upload = new ServletFileUpload(factory);// Create a new file upload
		this.upload.setSizeMax(this.MAXSize);// Set overall request size
		filedir = config.getServletContext().getRealPath("upload");
		System.out.println("filedir=" + filedir);
		hobbyDao = new HouseDao();
	}
	
	
	private void listRecommendPhoneHouse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();

		String xuqiuUserId = request.getParameter("xuqiuUserId");
		List<Object> params = new ArrayList<Object>();
		params.add(xuqiuUserId);

		List<Map<String, Object>> list = hobbyDao.listRecommendPhoneHouse(params);
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "ok");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);

	}

	
	
	private void updateHouseInfor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String path = request.getContextPath();
		 String[] hourseInfor = request.getParameterValues("hourseInfor");
		String xuqiuId = request.getParameter("xuqiuId");
		List<Object> params = new ArrayList<Object>();


		
		String ids="";
		for(int i=0;i<hourseInfor.length;i++){
			System.out.println(hourseInfor[i]);
			ids = ids+hourseInfor[i]+",";
		}
		
		params.add(ids.substring(0, ids.length()-1));
		params.add(xuqiuId);

		boolean flag = hobbyDao.updateHouseInfor(params);
		JSONObject jsonmsg = new JSONObject();
		if (flag) {
			listXuQiuMessage(request, response);
		} else {
			jsonmsg.put("repMsg", "预约失败");
			jsonmsg.put("repCode", "111");
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
			System.out.println(jsonmsg);
		}

	}
	
	private void listRecommendMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String xuqiuId = request.getParameter("xuqiuId");
		
		Map<String, Object> list_tea = hobbyDao.QueryStopCar(xuqiuId);
		List<Map<String, Object>> list = hobbyDao.listrecommednMessage();
		request.setAttribute("listMessage", list);
		request.setAttribute("map", list_tea);
		request.getRequestDispatcher("../recommendHouse.jsp").forward(request, response);

	}
	private void listXuQiuMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		List<Map<String, Object>> list = hobbyDao.listXuQiuMessage();
		request.setAttribute("listMessage", list);
		request.getRequestDispatcher("../xuqiuMessage.jsp").forward(request, response);

	}
	

	private void listHouseMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		List<Map<String, Object>> list = hobbyDao.listShopMessage();
		request.setAttribute("listMessage", list);
		request.getRequestDispatcher("../houseMessage.jsp").forward(request, response);

	}
	private void addHourseMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final long MAX_SIZE = 2048 * 1024 * 1024;// 设置上传文件最大值为2G，可以改为更大
		// 表单含有文件要提交
		String path = request.getContextPath();
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		// 构建一个文件上传类
		ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
		// servletFileUpload.setFileSizeMax(3 * 1024 * 1024);
		servletFileUpload.setSizeMax(MAX_SIZE);// 上传文件总大小
		List<FileItem> list = null;
		List<Object> params = new ArrayList<Object>();
		try {
			// 解析request的请求
			list = servletFileUpload.parseRequest(request);
			// 取出所有表单的值:判断非文本字段和文本字段
			for (FileItem fileItem : list) {
				if (fileItem.isFormField()) {
					
					
					
					if (fileItem.getFieldName().equals("houseName")) {

						params.add(fileItem.getString("utf-8"));
						System.out.println(fileItem.getString("utf-8"));

					}
					
					
					if (fileItem.getFieldName().equals("houseMoney")) {

						params.add(fileItem.getString("utf-8"));
						System.out.println(fileItem.getString("utf-8"));

					}
					
					
					if (fileItem.getFieldName().equals("housePhone")) {

						params.add(fileItem.getString("utf-8"));
						System.out.println(fileItem.getString("utf-8"));

					}
					
					if (fileItem.getFieldName().equals("houseMessage")) {

						params.add(fileItem.getString("utf-8"));
						System.out.println(fileItem.getString("utf-8"));
						
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
						params.add(df.format(new Date()));
						params.add("admin");
						params.add("admin");
						params.add("1");
					}
					
					
					if (fileItem.getFieldName().equals("houseCategoryName")) {
						params.add("admin");
						params.add(fileItem.getString("utf-8"));
					}

					
					if (fileItem.getFieldName().equals("houseAddress")) {

						params.add(fileItem.getString("utf-8"));
						System.out.println(fileItem.getString("utf-8"));

					}
					
					
					if (fileItem.getFieldName().equals("housePeiTao")) {
						params.add(fileItem.getString("utf-8"));
						System.out.println(fileItem.getString("utf-8"));
						params.add("");
					}
					
					

				} else {
					try {

						String image = fileItem.getName();
						String imageload = PingYinUtil.getPingYin(image);
						System.out.println("image111--->>" + imageload);
						params.add(imageload);
						String upload_path = request.getRealPath("/upload");
						System.out.println("--->>" + upload_path);
						String imgPath = Consts.imgPath;
						File real_path = new File(imgPath + "/" + imageload);
						fileItem.write(real_path);

						// 把数据插入到数据库中
					} catch (Exception e) {
						e.printStackTrace();
					}

					boolean flag = hobbyDao.addMessage(params);

					if (flag) {
						listHouseMessage(request, response);
					} else {
						System.out.println("flag:no");
					}

				}
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void updateXuQIU(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String path = request.getContextPath();
		String xuqiuMoney = request.getParameter("xuqiuMoney");
		String xuqiuMianJi = request.getParameter("xuqiuMianJi");
		String xuqiuAddress = request.getParameter("xuqiuAddress");
		String xuqiuId = request.getParameter("xuqiuId");
		List<Object> params = new ArrayList<Object>();

		params.add(xuqiuMoney);
		params.add(xuqiuMianJi);
		params.add(xuqiuAddress);
		params.add(xuqiuId);

		boolean flag = hobbyDao.updateXuQIU(params);
		JSONObject jsonmsg = new JSONObject();
		if (flag) {
			jsonmsg.put("repMsg", "设置成功");
			jsonmsg.put("repCode", "666");
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
			System.out.println(jsonmsg);
		} else {
			jsonmsg.put("repMsg", "预约失败");
			jsonmsg.put("repCode", "111");
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
			System.out.println(jsonmsg);
		}

	}

	private void checkXuQiuInfor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String userId = request.getParameter("userId");
		List<Object> params = new ArrayList<Object>();
		params.add(userId);

		if (hobbyDao.CheckXuQiu(params)) {
			Map<String, Object> flagMap = hobbyDao.queryXuQiu(params);
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "请求成功");
			jsonmsg.put("repCode", "666");
			jsonmsg.put("data", flagMap);
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
		} else {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "no");
			jsonmsg.put("repCode", "111");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
		}

	}

	private void listHourseRecommendMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String userId = request.getParameter("userId");
		List<Object> params = new ArrayList<Object>();
		params.add(userId);

		Map<String, Object> mapInfor = hobbyDao.queryXuQiu(params);

		String moneyStart = "";
		String moneyEnd = "";
		if (!mapInfor.get("xuqiuMoney").toString().equals("")) {
			moneyStart = mapInfor.get("xuqiuMoney").toString().split("-")[0];
			moneyEnd = mapInfor.get("xuqiuMoney").toString().split("-")[1];
		}

		String mianjiStart = "";
		String mianjiEnd = "";
		if (!mapInfor.get("xuqiuMianJi").toString().equals("")) {
			mianjiStart = mapInfor.get("xuqiuMianJi").toString().split("-")[0];
			mianjiEnd = mapInfor.get("xuqiuMianJi").toString().split("-")[1];
		}

		String zoomInfor = "";
		if (!mapInfor.get("xuqiuAddress").toString().equals("")) {
			zoomInfor = mapInfor.get("xuqiuAddress").toString();
		}

		List<Map<String, Object>> flagFood = hobbyDao.listHourseRecommendMessage(moneyStart, moneyEnd, mianjiStart, mianjiEnd, zoomInfor);
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", flagFood);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void addXuQiu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String path = request.getContextPath();
		String xuqiuMoney = request.getParameter("xuqiuMoney");
		String xuqiuMianJi = request.getParameter("xuqiuMianJi");
		String xuqiuAddress = request.getParameter("xuqiuAddress");
		String xuqiuUserId = request.getParameter("xuqiuUserId");
		List<Object> params = new ArrayList<Object>();

		params.add(xuqiuMoney);
		params.add(xuqiuMianJi);
		params.add(xuqiuAddress);
		params.add(xuqiuUserId);

		boolean flag = hobbyDao.addXuQiu(params);
		JSONObject jsonmsg = new JSONObject();
		if (flag) {
			jsonmsg.put("repMsg", "设置成功");
			jsonmsg.put("repCode", "666");
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
			System.out.println(jsonmsg);
		} else {
			jsonmsg.put("repMsg", "预约失败");
			jsonmsg.put("repCode", "111");
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
			System.out.println(jsonmsg);
		}

	}

	private void listMyUserOrderZeKe(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();

		String applyHouseUserId = request.getParameter("applyHouseUserId");
		List<Object> params = new ArrayList<Object>();
		params.add(applyHouseUserId);

		List<Map<String, Object>> list = hobbyDao.listMyUserOrderZeKe(params);
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "����ɹ�");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);

	}

	private void myapplyMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();

		String applyUserId = request.getParameter("applyUserId");
		List<Object> params = new ArrayList<Object>();
		params.add(applyUserId);

		List<Map<String, Object>> list = hobbyDao.listMyUserOrder(params);
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "ok");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);

	}

	private void listZoom(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();

		String userId = request.getParameter("userId");

		List<Map<String, Object>> list = hobbyDao.listFbuUserMessage(userId);
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);

	}

	private void listOrderPC(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		List<Map<String, Object>> list = hobbyDao.listHuoDongMessage();
		request.setAttribute("listMessage", list);
		request.getRequestDispatcher("../orderMessage.jsp").forward(request, response);

	}

	private void updateOrderState(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String applyState = request.getParameter("applyState");
		String applyId = request.getParameter("applyId");
		String applyHouseUserId = request.getParameter("applyHouseUserId");

		List<Object> params = new ArrayList<Object>();
		params.add(applyState);
		params.add(applyId);
		params.add(applyHouseUserId);
		boolean flag = hobbyDao.updateOrderState(params);
		if (flag) {

			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "成功");
			jsonmsg.put("repCode", "666");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);

		} else {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "失败");
			jsonmsg.put("repCode", "111");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);
		}

	}

	private void listFbuUserMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();

		String userId = request.getParameter("userId");

		List<Map<String, Object>> list = hobbyDao.listFbuUserMessage(userId);
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);

	}

	private void deletehouse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String houseId = request.getParameter("houseId");
		List<Object> params = new ArrayList<Object>();
		params.add(houseId);
		boolean flag = hobbyDao.deletehouse(params);
		JSONObject jsonmsg = new JSONObject();
		if (flag) {
			jsonmsg.put("repMsg", "删除成功");
			jsonmsg.put("repCode", "666");
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
			System.out.println(jsonmsg);
		} else {
			jsonmsg.put("repMsg", "预约失败");
			jsonmsg.put("repCode", "111");
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
			System.out.println(jsonmsg);
		}

	}

	private void listHourseMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String moneyStart = request.getParameter("moneyStart");
		String moneyEnd = request.getParameter("moneyEnd");

		String mianjiStart = request.getParameter("mianjiStart");
		String mianjiEnd = request.getParameter("mianjiEnd");

		String houseType = request.getParameter("houseType");

		String zoomInfor = request.getParameter("zoomInfor");

		List<Map<String, Object>> list = hobbyDao.listHourseMessage(moneyStart, moneyEnd, mianjiStart, mianjiEnd, houseType, zoomInfor);
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);

	}

	private void addXieyi(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String path = request.getContextPath();
		String agreementJiaName = request.getParameter("agreementJiaName");
		String agreementYiName = request.getParameter("agreementYiName");
		String agreementHouseId = request.getParameter("agreementHouseId");

		List<Object> params = new ArrayList<Object>();

		params.add(agreementJiaName);
		params.add(agreementYiName);
		params.add(agreementHouseId);

		boolean flag = hobbyDao.addXieyi(params);
		JSONObject jsonmsg = new JSONObject();
		if (flag) {

			Map<String, Object> map = hobbyDao.queryXieYi();
			jsonmsg.put("repMsg", map.get("agreementId").toString());
			jsonmsg.put("repCode", "666");
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
			System.out.println(jsonmsg);
		} else {
			jsonmsg.put("repMsg", "预约失败");
			jsonmsg.put("repCode", "111");
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
			System.out.println(jsonmsg);
		}

	}

	private void addAply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String path = request.getContextPath();
		String applyHouseId = request.getParameter("applyHouseId");
		String applyHouseName = request.getParameter("applyHouseName");
		String applyHouseMoney = request.getParameter("applyHouseMoney");
		String applyUserId = request.getParameter("applyUserId");
		String applyUserName = request.getParameter("applyUserName");
		String applyHouseUserId = request.getParameter("applyHouseUserId");
		String applyZLRealName = request.getParameter("applyZLRealName");
		String applyZLTime = request.getParameter("applyZLTime");
		String agreementId = request.getParameter("agreementId");

		List<Object> params = new ArrayList<Object>();

		params.add(applyUserId);
		params.add(applyUserName);
		params.add(applyHouseId);
		params.add(applyHouseName);
		params.add(applyHouseMoney);
		params.add(applyHouseUserId);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		params.add(df.format(new Date()));
		params.add("1");
		params.add(applyZLRealName);
		params.add(applyZLTime);
		params.add(agreementId);

		boolean flag = hobbyDao.addOrder(params);
		JSONObject jsonmsg = new JSONObject();
		if (flag) {
			jsonmsg.put("repMsg", "提交成功");
			jsonmsg.put("repCode", "666");
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
			System.out.println(jsonmsg);
		} else {
			jsonmsg.put("repMsg", "预约失败");
			jsonmsg.put("repCode", "111");
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
			System.out.println(jsonmsg);
		}

	}

	private void addReviewMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reviewUserId = request.getParameter("reviewUserId");
		String reviewUserName = request.getParameter("reviewUserName");
		String reviewMessage = request.getParameter("reviewMessage");
		String reviewMessageId = request.getParameter("reviewMessageId");

		List<Object> params_check = new ArrayList<Object>();
		params_check.add(reviewUserId);
		params_check.add(reviewUserName);
		params_check.add(reviewMessage);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式
		params_check.add(df.format(new Date()));
		params_check.add(reviewMessageId);
		boolean flag = hobbyDao.addReviewMessage(params_check);
		JSONObject jsonmsg = new JSONObject();
		if (flag) {
			jsonmsg.put("repMsg", "提交成功");
			jsonmsg.put("repCode", "666");
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
			System.out.println(jsonmsg);
		} else {
			jsonmsg.put("repMsg", "预约失败");
			jsonmsg.put("repCode", "111");
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
			System.out.println(jsonmsg);
		}

	}

	private void LookHousePhone(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 已经进行分页之后的数据集合

		List<Map<String, Object>> list = hobbyDao.listLookhouseMessage();
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端
	}

	private void addLookHouse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String path = request.getContextPath();
		String lookHouseMoney = request.getParameter("lookHouseMoney");
		String lookHouseTypeId = request.getParameter("lookHouseTypeId");
		String lookHouseTypeName = request.getParameter("lookHouseTypeName");
		String lookHousePhone = request.getParameter("lookHousePhone");
		String lookHouseUserId = request.getParameter("lookHouseUserId");
		String lookHouseUserName = request.getParameter("lookHouseUserName");

		List<Object> params = new ArrayList<Object>();

		params.add(lookHouseMoney);
		params.add(lookHouseTypeId);
		params.add(lookHouseTypeName);
		params.add(lookHousePhone);
		params.add(lookHouseUserId);
		params.add(lookHouseUserName);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 设置日期格式
		params.add(df.format(new Date()));

		boolean flag = hobbyDao.addLookHouse(params);
		JSONObject jsonmsg = new JSONObject();
		if (flag) {
			jsonmsg.put("repMsg", "发布成功");
			jsonmsg.put("repCode", "666");
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
			System.out.println(jsonmsg);
		} else {
			jsonmsg.put("repMsg", "预约失败");
			jsonmsg.put("repCode", "111");
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
			System.out.println(jsonmsg);
		}

	}

	private void queryMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 已经进行分页之后的数据集合
		String searchmessage = request.getParameter("searchmessage");

		// List<Object> params = new ArrayList<Object>();
		// params.add(uid);
		List<Map<String, Object>> list = hobbyDao.queryMessage(searchmessage);
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端
	}

	private void deleteHobby(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String hobbyId = request.getParameter("hobbyId");
		List<Object> params = new ArrayList<Object>();
		params.add(hobbyId);
		boolean flag = hobbyDao.deletehobbymsg(params);
		if (flag) {
			listHuoDongMessage(request, response);
		}

	}

	private void listHuoDongMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		List<Map<String, Object>> list = hobbyDao.listHuoDongMessage();
		request.setAttribute("listMessage", list);
		request.getRequestDispatcher("../hobbyMessage.jsp").forward(request, response);

	}

	private void deletePcInterest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String interestId = request.getParameter("interestId");
		List<Object> params = new ArrayList<Object>();
		params.add(interestId);
		boolean flag = hobbyDao.deleteInter(params);
		if (flag) {
			listMessage(request, response);
		}

	}

	private void deleteInterest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String interestId = request.getParameter("interestId");
		List<Object> params = new ArrayList<Object>();
		params.add(interestId);
		boolean flag = hobbyDao.deleteInter(params);
		if (flag) {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "删除成功");
			jsonmsg.put("repCode", "666");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
		} else {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "提交失败");
			jsonmsg.put("repCode", "111");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
		}
	}

	private void listInterestUserMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String interestUserId = request.getParameter("interestUserId");
		List<Object> params = new ArrayList<Object>();
		params.add(interestUserId);

		List<Map<String, Object>> flagFood = hobbyDao.listInterestUserMessage(params);
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", flagFood);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void listInterestMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Map<String, Object>> flagFood = hobbyDao.listInterestMessage();
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", flagFood);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void addMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sCategory = request.getParameter("houseCategory");
		String houseCategoryName = request.getParameter("houseCategoryName");
		String sName = request.getParameter("houseName");
		String sMoney = request.getParameter("houseMoney");
		String sPhone = request.getParameter("housePhone");
		String sMessage = request.getParameter("houseMessage");
		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");
		String image = request.getParameter("image");
		String houseCreatime = request.getParameter("houseCreatime");

		String houseAddress = request.getParameter("houseAddress");
		String housePeiTao = request.getParameter("housePeiTao");
		String houseType = request.getParameter("houseType");

		List<Object> params_check = new ArrayList<Object>();
		params_check.add(sName);
		params_check.add(sCategory);
		params_check.add(userId);
		boolean flagCheck = hobbyDao.checkHouse(params_check);
		if (flagCheck) {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "该房源已经发布过了");
			jsonmsg.put("repCode", "111");
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
		} else {
			String imagePath = null;
			if (sName == null) {
				try {
					List<FileItem> items = this.upload.parseRequest(request);
					if (items != null && !items.isEmpty()) {
						for (FileItem fileItem : items) {
							String filename = fileItem.getName();
							System.out.println("文件保存路径为:" + Consts.imgPath + "/" + filename);

							File real_path = new File(Consts.imgPath + "/" + filename);
							InputStream inputSteam = fileItem.getInputStream();
							BufferedInputStream fis = new BufferedInputStream(inputSteam);
							FileOutputStream fos = new FileOutputStream(real_path);
							int f;
							while ((f = fis.read()) != -1) {
								fos.write(f);
							}
							fos.flush();
							fos.close();
							fis.close();
							inputSteam.close();
							System.out.println("文件：" + filename + "上传成功!");
							imagePath = filename;

						}
					}

				} catch (FileUploadException e) {
					e.printStackTrace();
				}
			} else {

				List<Object> params = new ArrayList<Object>();
				params.add(sName + "");
				params.add(sMoney + "");
				params.add(sPhone + "");
				params.add(sMessage + "");
			
				params.add(houseCreatime);
				params.add(userId + "");
				params.add(userName + "");
				params.add(1 + "");
				params.add(sCategory);
				params.add(houseCategoryName);
				params.add(houseAddress);
				params.add(housePeiTao);
				params.add(houseType);
				params.add(image);
				boolean flag = hobbyDao.addMessage(params);
				System.out.println(flag + "");
				if (flag) {

					List<Object> paramsCheck = new ArrayList<Object>();

					paramsCheck.add(houseCreatime);
					Map<String, Object> map = hobbyDao.queryhourse(paramsCheck);

					JSONObject jsonmsg = new JSONObject();
					jsonmsg.put("repMsg", map.get("houseId"));
					jsonmsg.put("repCode", "666");
					response.getWriter().print(jsonmsg);// 将路径返回给客户端
				} else {
					JSONObject jsonmsg = new JSONObject();
					jsonmsg.put("repMsg", "上传文件失败");
					jsonmsg.put("repCode", "111");
					response.getWriter().print(jsonmsg);// 将路径返回给客户端
				}

			}
		}

	}

	private void queryMRMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String medicalRecordPatientId = request.getParameter("medicalRecordPatientId");
		String appointmentId = request.getParameter("appointmentId");
		List<Object> params = new ArrayList<Object>();
		params.add(medicalRecordPatientId);
		params.add(appointmentId);
		Map<String, Object> flagFood = hobbyDao.queryMRMessage(params);
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", flagFood);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void addMRMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String medicalRecordPatientId = request.getParameter("medicalRecordPatientId");
		String medicalRecordPatientName = request.getParameter("medicalRecordPatientName");
		String medicalRecordPatientPhone = request.getParameter("medicalRecordPatientPhone");
		String medicalRecordPatientTime = request.getParameter("medicalRecordPatientTime");
		String medicalRecordPatientMessage = request.getParameter("medicalRecordPatientMessage");
		String medicalRecordDoctorId = request.getParameter("medicalRecordDoctorId");
		String medicalRecordDoctorName = request.getParameter("medicalRecordDoctorName");
		String appointmentId = request.getParameter("appointmentId");

		List<Object> params = new ArrayList<Object>();
		params.add(medicalRecordPatientId);
		params.add(medicalRecordPatientName);
		params.add(medicalRecordPatientPhone);
		params.add(medicalRecordPatientTime);
		params.add(medicalRecordPatientMessage);
		params.add(medicalRecordDoctorId);
		params.add(medicalRecordDoctorName);
		params.add(appointmentId);

		boolean flag = hobbyDao.addMRMessage(params);
		JSONObject jsonmsg = new JSONObject();
		if (flag) {
			jsonmsg.put("repMsg", "提交成功");
			jsonmsg.put("repCode", "666");
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
			System.out.println(jsonmsg);
		} else {
			jsonmsg.put("repMsg", "提交失败");
			jsonmsg.put("repCode", "111");
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
			System.out.println(jsonmsg);
		}
	}

	private void listDoctorQueryAppointmentMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String appointmentDoctorId = request.getParameter("appointmentDoctorId");
		List<Object> params = new ArrayList<Object>();
		params.add(appointmentDoctorId);
		List<Map<String, Object>> flagFood = hobbyDao.listDoctorAppointmentmsgMessage(params);
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", flagFood);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void listAppointmentMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String appointmentUserId = request.getParameter("appointmentUserId");
		List<Object> params = new ArrayList<Object>();
		params.add(appointmentUserId);
		List<Map<String, Object>> flagFood = hobbyDao.listAppointmentmsgMessage(params);
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", flagFood);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void addAppointment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();

		String appointmentDoctorId = request.getParameter("appointmentDoctorId");
		String appointmentDoctorName = request.getParameter("appointmentDoctorName");
		String appointmentUserId = request.getParameter("appointmentUserId");
		String appointmentUserName = request.getParameter("appointmentUserName");

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式

		List<Object> paramsCheck = new ArrayList<Object>();
		paramsCheck.add(appointmentDoctorId);
		paramsCheck.add(df.format(new Date()));
		int appNumber = hobbyDao.getItemCount(paramsCheck);

		List<Object> params = new ArrayList<Object>();
		params.add(appointmentDoctorId);
		params.add(appointmentDoctorName);
		params.add(appointmentUserId);
		params.add(appointmentUserName);
		params.add(df.format(new Date()));

		if (appNumber == 0) {
			params.add("1");
		} else {
			params.add(appNumber + 1);
		}

		boolean flag = hobbyDao.addAppointment(params);
		JSONObject jsonmsg = new JSONObject();
		if (flag) {
			jsonmsg.put("repMsg", "预约成功");
			jsonmsg.put("repCode", "666");
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
			System.out.println(jsonmsg);
		} else {
			jsonmsg.put("repMsg", "预约失败");
			jsonmsg.put("repCode", "111");
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
			System.out.println(jsonmsg);
		}
	}

	private void listSearchMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String searchMsg = request.getParameter("searchMsg");
		List<Map<String, Object>> list = hobbyDao.listSearchMessage(searchMsg);
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void deleteMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String flowerId = request.getParameter("flowerId");
		List<Object> params = new ArrayList<Object>();
		params.add(flowerId);
		boolean flag = hobbyDao.deleteMessage(params);
		if (flag) {
			listMessage(request, response);
		}

	}

	private void updateReview(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String rId = request.getParameter("rId");
		String replyContent = request.getParameter("replyContent");
		List<Object> params = new ArrayList<Object>();
		params.add(replyContent);
		params.add(rId);
		boolean flag = hobbyDao.updateMessage(params);
		if (flag) {
			List<Map<String, Object>> list = hobbyDao.listReviewMessage();
			request.setAttribute("listMessage", list);
			request.getRequestDispatcher("../reviewListMessage.jsp").forward(request, response);
		}

	}

	private void listPhoneMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<Map<String, Object>> flagFood = hobbyDao.listPhoneMessage();
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "请求成功");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", flagFood);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// 将路径返回给客户端

	}

	private void listMessageChoice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		List<Map<String, Object>> list = hobbyDao.listMessage();
		request.setAttribute("listMessage", list);
		request.getRequestDispatcher("../formJob.jsp").forward(request, response);

	}

	private void listMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		List<Map<String, Object>> list = hobbyDao.listMessage();
		request.setAttribute("listMessage", list);
		request.getRequestDispatcher("../interMessage.jsp").forward(request, response);

	}

	private void addOldMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();

		String naviName = request.getParameter("naviName");
		String naviJD = request.getParameter("naviJD");
		String naviWD = request.getParameter("naviWD");
		List<Object> params = new ArrayList<Object>();
		params.add(naviName);
		params.add(naviJD);
		params.add(naviWD);
		boolean flag = hobbyDao.addMessage(params);
		if (flag) {
			listMessage(request, response);
		} else {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "提交失败");
			jsonmsg.put("repCode", "111");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// 将路径返回给客户端
		}

	}

}
