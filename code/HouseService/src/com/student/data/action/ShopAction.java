package com.student.data.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.student.data.dao.ShopDao;

public class ShopAction extends HttpServlet {

	private ShopDao shopDao;

	public ShopAction() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		String action_flag = request.getParameter("action_flag");
		if (action_flag.equals("addMessage")) {
			addMessage(request, response);
		} else if (action_flag.equals("addType")) {
			addType(request, response);
		}else if (action_flag.equals("listTypeMessage")) {
			listTypeMessage(request, response);
		}else if (action_flag.equals("listTypePhoneMessage")) {
			listTypePhoneMessage(request, response);
		}else if (action_flag.equals("listShopMessage")) {
			listShopMessage(request, response);
		}else if (action_flag.equals("listShopUserMessage")) {
			listShopUserMessage(request, response);
		}else if (action_flag.equals("updateShopMessage")) {
			updateShopMessage(request, response);
		}else if (action_flag.equals("listSearchShop")) {
			listSearchShop(request, response);
		}else if (action_flag.equals("listSearchShopDesc")) {
			listSearchShopDesc(request, response);
		}else if (action_flag.equals("listSearchTypeAscMessage")) {
			listSearchTypeAscMessage(request, response);
		}else if (action_flag.equals("listSearchTypeDescMessage")) {
			listSearchTypeDescMessage(request, response);
		} else if (action_flag.equals("deleteType")) {
			deleteType(request, response);
		}else if (action_flag.equals("listShopVipMessage")) {
			listShopVipMessage(request, response);
		}else if (action_flag.equals("listHouseMessage")) {
			listHouseMessage(request, response);
		} else if (action_flag.equals("deleteMessage")) {
			deleteMessage(request, response);
		}else if (action_flag.equals("listHourseMessage")) {
			listHourseMessage(request, response);
		}
		
		
		

	}
	public void init() throws ServletException {
		shopDao = new ShopDao();
	}
	private void listHourseMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String userId = request.getParameter("userId");
		List<Map<String, Object>> list = shopDao.listHourseMessage(userId);
		// ����json�ַ���
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "����ɹ�");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// ��·�����ظ��ͻ���

	}
	private void deleteMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String houseId = request.getParameter("houseId");
		List<Object> params = new ArrayList<Object>();
		params.add(houseId);
		boolean flag = shopDao.deleteMessage(params);
		if (flag) {
			listHouseMessage(request, response);
		} else {
			System.out.println("ʧ����");
		}

	}
	
	
	private void listShopVipMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		
		
		String houseCategory = request.getParameter("houseCategory");
		List<Object> params = new ArrayList<Object>();
		params.add(houseCategory);
		
		List<Map<String, Object>> list = shopDao.listShopVipMessage(params);
		// ����json�ַ���
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "����ɹ�");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// ��·�����ظ��ͻ���

	}
	private void deleteType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		String typeId = request.getParameter("typeId");
		List<Object> params = new ArrayList<Object>();
		params.add(typeId);
		boolean flag = shopDao.deleteType(params);
		if (flag) {
			listTypeMessage(request, response);
		} else {
			System.out.println("ʧ����");
		}

	}
	
	
	private void listSearchTypeDescMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		
		String searchMsg = request.getParameter("searchMsg");
		String typeId = request.getParameter("typeId");
		List<Map<String, Object>> list = shopDao.listSearchTypeDescMessage(searchMsg,typeId);
		// ����json�ַ���
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "����ɹ�");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// ��·�����ظ��ͻ���

	}
	private void listSearchTypeAscMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		
		String searchMsg = request.getParameter("searchMsg");
		String typeId = request.getParameter("typeId");
		List<Map<String, Object>> list = shopDao.listSearchTypeAscMessage(searchMsg,typeId);
		// ����json�ַ���
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "����ɹ�");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// ��·�����ظ��ͻ���

	}
	
	
	private void listSearchShopDesc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		
		
		String searchMsg = request.getParameter("searchMsg");
		
		List<Map<String, Object>> list = shopDao.listSearchDescMessage(searchMsg);
		// ����json�ַ���
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "����ɹ�");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// ��·�����ظ��ͻ���

	}
	private void listSearchShop(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		
		
		String searchMsg = request.getParameter("searchMsg");
		
		List<Map<String, Object>> list = shopDao.listSearchMessage(searchMsg);
		// ����json�ַ���
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "����ɹ�");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// ��·�����ظ��ͻ���

	}
	
	private void updateShopMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String path = request.getContextPath();
		String uid = request.getParameter("uid");
		String oid = request.getParameter("oid");
		List<Object> params = new ArrayList<Object>();
		params.add(oid);
		boolean flag = shopDao.deleteOrder(params);

		if (flag) {
			// ����json�ַ���
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "�޸ĳɹ�");
			jsonmsg.put("repCode", "666");
			jsonmsg.put("data", "");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// ��·�����ظ��ͻ���
		}else {
			// ����json�ַ���
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "�޸�ʧ�� ");
			jsonmsg.put("repCode", "666");
			jsonmsg.put("data", "");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// ��·�����ظ��ͻ���
		}

	}
	
	private void listShopUserMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		
		
		String userId = request.getParameter("userId");
		List<Object> params = new ArrayList<Object>();
		params.add(userId);
		
		List<Map<String, Object>> list = shopDao.listShopUserMessage(params);
		// ����json�ַ���
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "����ɹ�");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// ��·�����ظ��ͻ���

	}
	
	private void listShopMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		
		String userId = request.getParameter("userId");
		List<Object> params = new ArrayList<Object>();
		params.add(userId);
		
		List<Map<String, Object>> list = shopDao.listShopMessage();
		// ����json�ַ���
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "����ɹ�");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// ��·�����ظ��ͻ���

	}
	
	private void listHouseMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		List<Map<String, Object>> list = shopDao.listShopMessage();
		request.setAttribute("listMessage", list);
		request.getRequestDispatcher("../houseMessage.jsp").forward(request, response);

	}
	
	private void listTypePhoneMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		List<Map<String, Object>> list = shopDao.listTypeMessage();
		// ����json�ַ���
		JSONObject jsonmsg = new JSONObject();
		jsonmsg.put("repMsg", "����ɹ�");
		jsonmsg.put("repCode", "666");
		jsonmsg.put("data", list);
		System.out.println(jsonmsg);
		response.getWriter().print(jsonmsg);// ��·�����ظ��ͻ���

	}
	
	private void listTypeMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();
		List<Map<String, Object>> list = shopDao.listTypeMessage();
		request.setAttribute("listMessage", list);
		request.getRequestDispatcher("../typeMessage.jsp").forward(request, response);

	}
	
	
	private void addType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath();

		String typeName = request.getParameter("typeName");

		List<Object> params = new ArrayList<Object>();
		params.add(typeName);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// �������ڸ�ʽ
		params.add(df.format(new Date()));
		boolean flag = shopDao.addType(params);
		if (flag) {
			listTypeMessage(request, response);
		} else {
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "�ύʧ��");
			jsonmsg.put("repCode", "111");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// ��·�����ظ��ͻ���
		}

	}

	

	private void addMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String path = request.getContextPath();
		String uid = request.getParameter("uid");
		String oid = request.getParameter("oid");
		List<Object> params = new ArrayList<Object>();
		params.add(oid);
		boolean flag = shopDao.deleteOrder(params);

		if (flag) {
			boolean flagState = shopDao.deleteState(params);

			if (flagState) {
				// ����json�ַ���
				JSONObject jsonmsg = new JSONObject();
				jsonmsg.put("repMsg", "ɾ���ɹ�");
				jsonmsg.put("repCode", "666");
				jsonmsg.put("data", "");
				System.out.println(jsonmsg);
				response.getWriter().print(jsonmsg);// ��·�����ظ��ͻ���
			}

		} else {
			// ����json�ַ���
			JSONObject jsonmsg = new JSONObject();
			jsonmsg.put("repMsg", "ɾ��ʧ�� ");
			jsonmsg.put("repCode", "666");
			jsonmsg.put("data", "");
			System.out.println(jsonmsg);
			response.getWriter().print(jsonmsg);// ��·�����ظ��ͻ���
		}

	}



}