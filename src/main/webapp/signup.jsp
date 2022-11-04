<%@ page language="java" 
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>

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
	<h1>Swack</h1>
	<h1>新規登録</h1>
	<div class="signup__container">
		<div class="container__child">


			<div class="container__child signup__form card">
				<form action="SignupServlet">
				<p class="error">${errorMsg}</p>
					<div class="form-group">
						<label for="username">氏名</label> <input class="form-control"
							type="text" name="username" id="username"
							required />
					</div>
					<div class="form-group">
						<label for="mailaddress">メールアドレス</label> <input
							class="form-control" type="text" name="mailaddress"
							id="mailaddress" required />
					</div>
					<div class="form-group">
						<label for="password">パスワード</label> <input class="form-control"
							type="password" name="password" id="password"
							 required />
					</div>
					<div class="form-group">
						<label for="passwordRepeat">確認用パスワード</label> <input
							class="form-control" type="password" name="passwordRepeat"
							id="passwordRepeat"  required />
					</div>
					<div>
					
						<ul>
							<input class="btn btn--form" type="submit" value="登録" />
						</ul>
						
					</div>
					
				</form>
				
			</div>
			<a href="login.jsp">ログイン画面へ</a>
		</div>
	</div>
</body>
</html>