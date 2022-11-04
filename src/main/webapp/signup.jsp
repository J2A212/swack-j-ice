<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Swack - 新規登録画面</title>

<link rel="stylesheet" href="css/signup.css">
</head>
<body background="login.jpg">
	<div class="container">
		<h1>Swack</h1>
		<h2>新規登録</h2>
		<div class="card">
			<p class="error">${errorMsg}</p>
			<form action="SignupServlet" method="post">
				 <label for="username">氏名</label>
				 <input type="text" name="username"id="username" required /> 
				 
				 <label for="mailaddress">メールアドレス</label>
				 <input type="text" name="mailaddress" id="mailaddress" required />
				 
				 <label for="password">パスワード</label>
				 <input type="password"name="password" id="password" required />
				 
				 <label for="passwordRepeat">確認用パスワード</label>
				 <input type="password"name="passwordRepeat" id="passwordRepeat" required />
				 
				 <input type="submit" value="登録">
			</form>
		</div>
	</div>
	<a href="login.jsp">ログイン画面へ</a>
</body>
</html>