<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.css">
<script src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
<script src="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.js"></script>
<link rel="stylesheet" type="text/css" href="resources/css/damiwan_all.css"/>
</head>
<body>
	<div data-role="page" id="pageone">
	    <div data-role="header">
 		    <h1>欢迎来到我的主页</h1>
 		</div>
		<div data-role="content">
			<p>这是第一个页面</p>
			<a href="#pagetwo" style="color:red;" data-rel="dialog">Go to Page Two</a>
			<p>
				<a href="externalfile.html">访问外部文件</a>
			</p>
			<img src="../resources/images/2-index_login.gif" />
			<a href="http://www.damiwan.com" data-icon="arrow-r" data-iconpos="right">www.damiwan.com</a>
			<br/>
			<ol data-role="listview" data-inset="true" >
				<li data-role="list-divider"><a href="#">2列表项ol_1</a></li>
				<li><a href="#">1列表项ol_2</a></li>
				<li><a href="#">1列表项ol_3</a></li>
			</ol>
			<ul data-role="listview" data-autodividers="true" data-filter="true" data-filter-placeholder="搜索姓名 ..." data-inset="true" >
				<li>
					<a href="#">
						 <img style="padding:15px 0 0 15px;" src="../resources/images/1_30.gif">
        				 <h2>Google Chrome</h2>
                         <p>Google Chrome 是免费的开源 web 浏览器。发布于 2008 年。</p>
					</a>
					<a href="#download" data-rel="dialog">下载</a>
				</li>
				<li><a href="#">b列表项ul_2</a></li>
				<li><a href="#">b列表项ul_3</a></li>
		   </ul>
		</div>

		<div data-role="footer">
			<h1>页面底部内容</h1>
		</div>
	</div>

	<div data-role="page" id="pagetwo">
	  <div data-role="content">
	 	 <p>这是第二个页面</p>
	     <a href="#pageone">Go to Page One</a>
	  </div>
	</div>
	
	<div data-role="page" id="download">
	    <div data-role="content">
		    <h3>拆分按钮实例</h3>
		    <p>该按钮仅供演示。</p>
		    <a href="#" data-role="button" data-rel="back" data-icon="check" data-inline="true" data-mini="true">下载</a>
		    <a href="#" data-role="button" data-rel="back" data-inline="true" data-mini="true">取消</a>
  		</div>
	</div> 
	
</body>
</html>