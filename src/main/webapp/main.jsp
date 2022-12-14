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
			<form action="RoomInvitationServlet">
				<input type="submit" value="ルーム招待">
			</form>

			<!-- ルーム参加-->
			<form action="MemberInvitationServlet">
				<input type="submit" value="ルーム参加">
			</form>
			<!-- ログアウト機能-->
			<form action="LogoutServlet" id="logoutForm" method="get">
				<input type="submit" id="destroy" value="ログアウト" onclick="logout()">
			</form>

		</header>
		<section class="main">
			<div class="left">
				<h2>Swack</h2>
				<hr>
				
				<!-- 自分の参加しているルーム表示 -->
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

				<!-- ダイレクトメッセージ -->
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

				<!-- モーダルウィンドウ -->
				<div class="modal_wrap">
					<input id="trigger" type="checkbox">
					<div class="modal_overlay">
						<label for="trigger" class="modal_trigger"></label>
						<div class="modal_content">
							<label for="trigger" class="close_button">✖️</label>
							<p class="modal_title2">モーダル の中身を表示</p>
							<p></p>
						</div>
					</div>
				</div>
				<label for="trigger" class="open_button">モーダルを表示</label>
			</div>
			
			<!-- チャットログ -->
			<div class="contents">
				<div class="contents-header">
					<h2>${nowRoom.roomName}(${nowRoom.memberCount}人)</h2>
					<hr>
				</div>
				<div id="logArea" class="contents-area">
					<c:forEach var="log" items="${chatLogList}">
						<div class="log-area">
							<div class="log-box">
								<div class="dropdown">
									<span class="log-name" id="dd">${log.userName} </span> <span
										class="log-time">[${log.createdAt}]</span><br>
									${log.message}
								</div>
								<div class="ddmenu">
									<div class="edit">
										<!--<input class="fukidashi" type = "submit" value="削除" id = "delete${log.chatLogId}">-->
										<img src="trash.png" class="delete pointer"
											id="delete${log.chatLogId}" />

										<!--<input class="henn" type="submit" value="編集" id = "update${log.chatLogId}">-->
										<img src="update.png" class="update pointer"
											name="updateChatLogId" id="update${log.chatLogId}" />
									</div>
								</div>
							</div>
						</div>
					</c:forEach>
					<p class="error" id="errorMsg">${errorMsg}</p>
				</div>
				
				<!-- メッセージ送信・編集 -->
				<div>
					<div class="contents-footer">
						<input type="hidden" name="roomId" value="${nowRoom.roomId}">
						<div class="form-wrap">
							<form action="ChatServlet" method="post">
								<div class="show_mode">
									<input type="text" name="message"> <input type="submit"
										value="送信"> <input type="hidden" name="roomId"
										value="${nowRoom.roomId}">
								</div>
							</form>
							<form action="UpdateChatServlet" method="post"
								id="updateChatForm">
								<div class="edit_mode">
									<input type="text" name="message" placeholder="編集"> <input
										type="hidden" name="roomId" value="${nowRoom.roomId}">
									<input type="hidden" name="updateChatLogId"
										id="updateChatLogId" value="${log.chatLogId}">
								</div>
							</form>
						</div>
					</div>
				<!-- メッセージ削除のフォーム -->
					<form action="DeleteChatServlet" method="post" id="deleteChatForm">
						<input type="hidden" name="roomId" value="${nowRoom.roomId}">
						<input type="hidden" name="deleteChatLogId" id="deleteChatLogId"
							value="">
					</form>
				</div>
			</div>
		</section>
	</div>
	<script type="text/javascript"
		src="//ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="js/jquery-3.2.0.min.js"></script>
	<script src="js/main.js"></script>
	<script src="js/dropdown.js"></script>
	<script src="js/delete.js"></script>
	<script src="js/update.js"></script>
</body>
</html>