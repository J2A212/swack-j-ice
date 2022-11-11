<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Swack - メンバー招待画面</title>

<link rel="shortcut icon" href="images/favicon.ico">

<link href="css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<link href="css/bootstrap-toggle.min.css" rel="stylesheet">
<link href="css/bootstrap-select.min.css" rel="stylesheet">

<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/joinmember.css">

</head>

<body>
	<div class="container form-container">
		<div class="row">
			<div class="col-md-12 member-form">
				<h3>他のユーザを# ${nowRoom.roomName}に招待する</h3>
				<form action="RoomInvitationServlet" method="post">
					<div class="form-group">
						<label class="control-label">招待の送信先:(任意)</label> <select
							name="userList" class="form-control selectpicker"
							data-live-search="true" data-selected-text-format="count > 1"
							multiple>
							<c:forEach var="userList" items="${userList}">
								<option>${userList.username}</option>
							</c:forEach>
						</select> <span class="users-note">このルームに追加したい人を選んでください。</span>
					</div>
					<div class="member-form-btn">
						<button class="btn btn-default">キャンセル</button>
						<button id="send" class="btn btn-default">招待する</button>
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

	<script src="js/joinmember.js"></script>
</body>
</html>
