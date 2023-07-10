<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	Map<String, Object> map = (Map<String, Object>) request.getAttribute("map");
	List<Map<String, Object>> listMessage = (List<Map<String, Object>>)request.getAttribute("listMessage");
%>



<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>无标题文档</title>
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<link href="../css/select.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript" src="../js/jquery.idTabs.min.js"></script>
<script type="text/javascript" src="../js/select-ui.min.js"></script>
<script type="text/javascript" src="../editor/kindeditor.js"></script>


<script type="text/javascript">
	$(document).ready(function(e) {
		$(".select1").uedSelect({
			width : 345
		});
		$(".select2").uedSelect({
			width : 167
		});
		$(".select3").uedSelect({
			width : 100
		});
	});
</script>
<script type="text/javascript">
 function dosubmit(){
   var th = document.form2;
   th.action="<%=path%>/servlet/HouseAction?action_flag=updateHouseInfor&xuqiuId=<%=map.get("xuqiuId")%>";
		th.submit();
	}
</script>
</head>

<body>
	<form name="form2" action="" method="post"  >
		<div class="place">
			<span>位置：</span>
			<ul class="placeul">
				<li><a href="#">首页</a></li>
				<li><a href="#">表单</a></li>
			</ul>
		</div>

		<div class="formbody">

			<div class="formtitle">
				<span>基本信息</span>
			</div>

			<ul class="forminfo">
				
				<li><label>需求价格</label><input name="address" type="text" class="dfinput" value="<%=map.get("xuqiuMoney")%>元/月"  disabled="disabled" style="background: #c3c3c3"/></li>
				<li><label>需求面积</label><input name="address" type="text" class="dfinput" value="<%=map.get("xuqiuMianJi")%>平米"  disabled="disabled" style="background: #c3c3c3"/></li>
				<li><label>需求位置</label><input name="address" type="text" class="dfinput" value="<%=map.get("xuqiuAddress")%>"  disabled="disabled" style="background: #c3c3c3"/></li>
				<li><label>推荐房源</label>
				
						<%
								if (!listMessage.isEmpty()) {
									/*  for(Map<String,Object> map:list){ */
									for (int i = 0; i < listMessage.size(); i++) {
										Map<String, Object> mapInfor = listMessage.get(i);
							%>
									<input name="hourseInfor" type="checkbox" value="<%=mapInfor.get("houseId")%>"  /><%=mapInfor.get("houseName")%> &nbsp&nbsp<%=mapInfor.get("houseMoney")%>元/月&nbsp&nbsp <%=mapInfor.get("houseMessage")%>平米  &nbsp&nbsp <%=mapInfor.get("houseAddress")%> <br/>

							<%
								}
								}
							%>
							
		
				
				</li>
				<li><label>&nbsp;</label><input name="" type="button" class="btn" onclick="javascript:dosubmit();"  value="确认推荐" /></li>
			</ul>

		</div>
	</form>
</body>
</html>
