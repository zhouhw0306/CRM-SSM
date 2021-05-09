<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>

	<script type="text/javascript">

		$(function () {

			//给整个窗口添加键盘按下的事件
			$(window).keydown(function (e) {
				if (e.keyCode==13){
					$("#loginBtn").click();
				}
			})

			//页面加载完毕后, 将用户文本框中的内容清空
			//$("#loginAct").val("");

			//页面加载完毕后, 让用户的文本框自动获得焦点
			$("#loginAct").focus();

			//给登入按钮添加单击事件
			$("#loginBtn").click(function () {
				//收集参数
				var loginAct = $.trim($("#loginAct").val());
				var loginPwd = $.trim($("#loginPwd").val());
				var isRemPwd = $("#isRemPwd").prop("checked");
				//表单验证
				if (loginAct==""){
					$("#msg").html("账号不能为空");
					return;
				}
				if (loginPwd==""){
					$("#msg").html("密码不能为空");
					return;
				}
				//发送请求
				$.ajax({

					url:"login.do",
					data:{
						loginAct:loginAct,
						loginPwd:loginPwd,
						isRemPwd:isRemPwd
					},
					type:"post",
					dataType:"json",
					success:function (data) {
						if (data.code=="1"){
							//跳转业务主页面
							window.location.href="workbench/index.do"
						}else{
							$("#msg").html(data.message);
						}

					},
					beforeSend:function () {//在发送ajax请求之前执行本函数, 本函数会返回true,或者false,返回值决定ajax能否真正发送请求
						$("#msg").text("正在验证")
						return true;
					}

				})


			})

		});

	</script>

</head>
<body>
	<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
		<img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
	</div>
	<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
		<div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">&nbsp;&nbsp;CRM &nbsp;<span style="font-size: 12px;">&copy;2021&nbsp;夏天不吃西瓜</span></div>
	</div>
	
	<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
		<div style="position: absolute; top: 0px; right: 60px;">
			<div class="page-header">
				<h1>登录</h1>
			</div>
			<form action="workbench/index.jsp" class="form-horizontal" role="form">
				<div class="form-group form-group-lg">
					<div style="width: 350px;">
						<input class="form-control" value="${cookie.loginAct.value}" type="text" placeholder="用户名" id="loginAct">
					</div>
					<div style="width: 350px; position: relative;top: 20px;">
						<input class="form-control" value="${cookie.loginPwd.value}" type="password" placeholder="密码" id="loginPwd">
					</div>
					<div class="checkbox"  style="position: relative;top: 30px; left: 10px;">

						<label>
							<!--使用jstl-->
							<c:if test="${not empty cookie.loginAct and not empty cookie.loginPwd}">
								<input type="checkbox" id="isRemPwd" checked="true">
							</c:if>
							<c:if test="${empty cookie.loginAct or empty cookie.loginPwd}">
								<input type="checkbox" id="isRemPwd">
							</c:if>
							十天内免登录
						</label>
						&nbsp;&nbsp;
						<span id="msg" style="color: red"></span>
					</div>
					<button type="button" id="loginBtn" class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;">登录</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>