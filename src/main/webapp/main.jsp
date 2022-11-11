<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Swack - メイン画面</title>

<link rel="shortcut icon" href="images/favicon.ico">

<link rel="stylesheet" href="css/modal.css">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/main.css">
<style>
.hover-blue:hover {
	color: blue;
}
</style>


</head>
<body>

	<div class="container">
		<header class="header">
			<div>${nowUser.userName}</div>

			<!-- ルーム作成-->
			<form action="CreateRoomServlet">
				<input type="submit" value="ルーム作成">
			</form>

			<!-- ルーム招待-->
			<form action="joinmember.jsp">
				<input type="submit" value="ルーム招待">
			</form>

			<!-- ルーム参加-->
			<form action="RoomServlet">
				<select name="room">
					<option value="">ルーム</option>
					<c:forEach var="room" items="${roomList}">
						<option># ${room.roomName}</option>
					</c:forEach>
				</select> <input type="submit" value="参加">
			</form>

			<!-- ログアウト機能-->
			<form action="LogoutServlet" id = "logoutForm"method="get">
				<input type="submit" value="ログアウト" onclick="logout();">
			</form>
		</header>
		<section class="main">
			<div class="left">
				<h2>Swack</h2>
				<hr>
				<details open>
					<summary>
						<a class="hover-blue">ルーム</a>
					</summary>
					<c:forEach var="room" items="${roomList}">
						<a class="list-name" href="MainServlet?roomId=${room.roomId}">
							# ${room.roomName} </a>
						<br>
					</c:forEach>
				</details>

				<details open>
					<summary>
						<a class="hover-blue">ダイレクト</a>
					</summary>
					<c:forEach var="room" items="${directList}">
						<a class="list-name" href="MainServlet?roomId=${room.roomId}">
							# ${room.roomName} </a>
						<br>
					</c:forEach>
				</details>


				<div class="modal_wrap">
					<input id="trigger" type="checkbox">
					<div class="modal_overlay">
						<label for="trigger" class="modal_trigger"></label>
						<div class="modal_content">
							<label for="trigger" class="close_button">✖️</label>
							<p class="modal_title2">モーダル の中身を表示</p>
							<p>あああああああああああああああああああああああああああああああああああああああああああああああああああああああああああ</p>
						</div>
					</div>
				</div>
					<label for="trigger" class="open_button">モーダルを表示</label>

			</div>
			<div class="contents">
				<div class="contents-header">
					<h2>${nowRoom.roomName}(${nowRoom.memberCount}人)</h2>
					<hr>
				</div>
				<div id="logArea" class="contents-area">
					<c:forEach var="log" items="${chatLogList}">
						<div class="log-area">
							<div class="log-box">
								<span class="log-name">${log.userName} </span> <span
									class="log-time">[${log.createdAt}]</span><br>
								${log.message}
							</div>
						</div>
					</c:forEach>
				</div>
				<div class="contents-footer">
					<form action="ChatServlet" method="post">
						<input type="hidden" name="roomId" value="${nowRoom.roomId}">
						<div class="form-wrap">
							<input type="text" name="message"> <input type="submit"
								value="送信">
						</div>
					</form>
				</div>
			</div>
		</section>

	</div>
	<script type="text/javascript"
		src="//ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="js/jquery-3.2.0.min.js"></script>
	<script src="js/main.js"></script>

</body>
</html>