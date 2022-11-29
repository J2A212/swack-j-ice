<%@ page language="java" 
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Swack - ログイン画面</title>

<link rel="shortcut icon" href="images/favicon.ico">

<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/login.css">

</head>
<body background="login.jpg">
	<div class="container">
		<h1>Swack</h1>
		<h2>ログイン</h2>
		
		<!-- 各種情報入力 -->
		<div class="card">
			<p class="error" id = "errorMsg">${errorMsg}</p>
			<form action="LoginServlet" id = "loginForm"method="post">
			<input type="email" name="mailaddress" id = "mailAddress"
				placeholder="xxxxxx@xxx.xxx"><br>
			<input type="password" name="password" id = "password"
				placeholder="パスワード" autocomplete="password"><br>
			<input type="submit" value="ログイン" onclick = "login();">
			<label><input type = "checkbox" id = "save"><span>ログイン状態を保存する</span></label>
			</form>
		</div>
		
		<a href="signup.jsp">新規登録画面へ</a>
	</div>
	<script src ="js/jquery-3.2.0.min.js"></script>
	<script src ="js/login.js"></script>
</body>
</html>