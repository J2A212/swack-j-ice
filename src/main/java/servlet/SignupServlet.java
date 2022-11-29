package servlet;

import static parameter.Messages.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import exception.SwackException;
import model.LoginModel;
import model.SignUpModel;
import model.UserModel;

/**
 * Servlet implementation class SignUpServlet
 */
@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userName = request.getParameter("username");
		String mailAddress = request.getParameter("mailaddress");
		String password = request.getParameter("password");
		String passwordRepeat = request.getParameter("passwordRepeat");

		// パラメータチェック
		StringBuilder errorMsg = new StringBuilder();
		if (userName == null || userName.length() == 0) {
			errorMsg.append("お名前が入っていません<br>");
		}
		if (mailAddress == null || mailAddress.length() == 0) {
			errorMsg.append("メールアドレスが入っていません<br>");
		}
		if (password == null || password.length() == 0) {
			errorMsg.append("パスワードが入っていません<br>");
		}
		if (passwordRepeat == null || passwordRepeat.length() == 0) {
			errorMsg.append("確認用パスワードが入っていません<br>");
		} else if (password.equals(passwordRepeat) == false) {
			errorMsg.append("確認用パスワードが一致していません<br>");
		}
		if (errorMsg.length() > 0) {
			// エラー
			request.setAttribute("errorMsg", errorMsg.toString());
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}

		try {
//			boolean han = new UserModel().exists(mailAddress);
//			if (han) {
//				// 同じユーザーが存在
//				errorMsg.append("同じメールアドレスが登録されているユーザーが既にいます<br>");
//				request.setAttribute("errorMsg", errorMsg.toString());
//				request.getRequestDispatcher("signup.jsp").forward(request, response);
//				return;
//			} else {
//				// 登録成功
				String maxUserId = new SignUpModel().selectMaxUserId();
				int id = Integer.parseInt(maxUserId.substring(1, 5));
				id++;
				if (id < 10) {
					maxUserId = "U000" + id;
				} else if (id < 100) {
					maxUserId = "U00" + id;
				} else if (id < 1000) {
					maxUserId = "U0" + id;
				} else {
					maxUserId = "U" + id;
				}
				System.out.println(id);
				User user = new User(maxUserId, userName, mailAddress, password);
				new SignUpModel().insert(user);
				
//				new UserModel().join(user);
//				RoomDAO rodao = new RoomDAO();
//				String maxroid = rodao.selectMaxRoomId();
//				List<String> users = rodao.getUser(maxUserId);
//				System.out.println(rodao.insertroom(maxroid, users, maxUserId));
//				System.out.println(rodao.join(maxroid, users, maxUserId));
				
				//最終ログイン更新(util型とsql型のDateを使うため、util型をsql型に変換しています
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				System.out.println(2);
				String formattedDate = simpleDateFormat.format(new Date());
				System.out.println(3);
				java.sql.Date loginDate = java.sql.Date.valueOf(formattedDate);
				System.out.println(4);
				UserModel userModel = new UserModel();
				userModel.loginUpdate(loginDate, mailAddress);
				User userl = new LoginModel().checkLogin(mailAddress, password);
				HttpSession session = request.getSession();
				session.setAttribute("user", userl);
				// ログイン画面を表示
				response.sendRedirect("login.jsp");
				return;
//			}
		} catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.getRequestDispatcher("main.jsp").forward(request, response);
			return;

		}

	}
}
