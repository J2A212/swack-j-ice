<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Swack - ルーム参加画面</title>
<link rel="shortcut icon" href="images/favicon.ico">

<link href="css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<link href="css/bootstrap-toggle.min.css" rel="stylesheet">
<link href="css/bootstrap-select.min.css" rel="stylesheet">

<link rel="stylesheet" href="css/createroom.css">
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<div class="container form-container">
		<div class="row">
			<div class="col-md-12 room-form">
			<h3>ルームに参加する</h3>
				<p class="input_note_special medium_bottom_margin">既存のルームに参加する場所です。</p>
				<form action="MemberInvitationServlet" method="post">
					<div class="form-group">
						<label class="control-label">参加するルーム</label> <select
							name="join" class="form-control selectpicker"
							data-live-search="true" data-selected-text-format="count > 1"
							multiple>
							<c:forEach var="join" items="${roomList}">
								<option># ${join.roomName}</option>
							</c:forEach>

						</select> <span class="users-note">参加したいルームを選んでください。</span>
					</div>
					<div class="room-form-btn">
						<a href="main.jsp">メイン画面へ</a>
						<button id="send" class="btn btn-default">ルームに参加</button>
					</div>
				</form>
			</div>
		</div>
	</div>
<!-- container -->

	<script src="js/jquery-3.2.0.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/bootstrap-toggle.min.js"></script>
	<script type="text/javascript" src="js/bootstrap-select.min.js"></script>

	<script src="js/createroom.js"></script>
</body>
</html>