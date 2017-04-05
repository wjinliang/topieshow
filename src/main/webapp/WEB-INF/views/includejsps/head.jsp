<%@ page language="java" pageEncoding="UTF-8"%>
<style>
.page-header {
	height: 51px;
}

.nav>li>a>img {
	max-width: 30px;
}

.navbar-nav>li>a {
	padding-top: 10px;
	padding-bottom: 10px;
}

.username-hide-mobile {
	color: white;
}

.logo {
	font-size: 20px;
	line-height: 55px;
	float: left;
	color: white;
	margin-left: -40px;
	margin-right: 50px;
}
</style>
<div class="page-header">
	<!-- BEGIN HEADER TOP -->
	<%-- <div class="page-header-top">
		<div class="container">
			<div class="page-logo">
				<span style="font-size: 21px; line-height: 75px;"><b>TOPIE</b>SHOW</span>
				<img src="<%=basePath%>assets/admin/layout3/img/logo-big-white.png" alt="TOPIECMS" style="height: 19px;margin-top:28px;"
			</div>
			<a href="javascript:;" class="menu-toggler"></a>
			<div class="top-menu">
				<ul class="nav navbar-nav pull-right">
					<li class="dropdown dropdown-extended  dropdown-inbox"
						id="header_inbox_bar"><a href="javascript:;"
						class="dropdown-toggle" data-toggle="dropdown"
						data-hover="dropdown" data-close-others="true"> <span
							class="circle">0</span> <span class="corner"></span>
					</a></li>
					<li id="user-info" class="dropdown dropdown-user"><a
						href="javascript:;" class="dropdown-toggle" data-toggle="dropdown"
						data-hover="dropdown" data-close-others="true"> <img alt=""
							class="img-circle" src="<%=basePath%>assets/dmcms/img/user.png">
							<span class="username username-hide-mobile">用户名</span>
					</a></li>
				</ul>
			</div>
		</div>
	</div> --%>
	<!-- END HEADER TOP -->
	<!-- BEGIN HEADER MENU -->
	<div class="page-header-menu">
		<div class="container">
			<%-- <div class="page-logo">
				<span style="font-size: 21px; line-height: 75px;"><b>TOPIE</b>SHOW</span>
				<img src="<%=basePath%>assets/admin/layout3/img/logo-big-white.png" alt="TOPIECMS" style="height: 19px;margin-top:28px;"
			</div> --%>
			<span class="logo"><b>TOPIE</b>SHOW</span>
			<div class="hor-menu"></div>
			<!-- 
			<a href="javascript:;" class="menu-toggler"></a> -->
			<div class="top-menu">
				<ul class="nav navbar-nav pull-right">
					<li id="user-info" class="dropdown dropdown-user"><a
						href="javascript:;" class="dropdown-toggle" data-toggle="dropdown"
						data-hover="dropdown" data-close-others="true"> <img alt=""
							class="img-circle" src="<%=basePath%>assets/dmcms/img/user.png">
							<span class="username username-hide-mobile">用户名</span>
					</a></li>
				</ul>
			</div>
			<%-- <%@include file="./toolbar.jsp"%> --%>
		</div>
	</div>
	<!-- END HEADER MENU -->
</div>

