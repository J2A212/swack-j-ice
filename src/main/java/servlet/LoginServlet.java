package servlet;

import static parameter.Messages.*;

import java.io.IOException;
import java.util.Calendar;
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
import model.UserModel;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// パラメータ取得
		String mailAddress = request.getParameter("mailaddress");
		System.out.println(mailAddress);
		String password = request.getParameter("password");
		System.out.println(password);
		UserModel userModel = new UserModel();

		// パラメータチェック
		StringBuilder errorMsg = new StringBuilder();
		if (mailAddress == null || mailAddress.length() == 0) {
			errorMsg.append("メールアドレスが入っていません<br>");
		}
		if (password == null || password.length() == 0) {
			errorMsg.append("パスワードが入っていません<br>");
		} else {
			try {
				//メールアドレスに紐づけられているパスワード取得
				String checkPassword = userModel.getPassword(mailAddress);
				//メールアドレスに紐づけられている認証失敗回数取得
				int failCount = userModel.getFailCount(mailAddress);
				//パスワードが一致しない場合
				if(!checkPassword.equals(password)) {
					errorMsg.append("パスワードが正しくありません");
					//失敗回数を1増やす
					userModel.passwordFailCountup(mailAddress);
				} else if (failCount >= 5) {
					errorMsg.append("一定回数パスワードの認証に失敗したため\n" + mailAddress + "はロックされています。");
				}
			} catch (SwackException e1) {
				e1.printStackTrace();
			}
		}
		if (errorMsg.length() > 0) {
			// エラー
			request.setAttribute("errorMsg", errorMsg.toString());
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}

		// 処理
		try {
			// ログインチェック
			User user = new LoginModel().checkLogin(mailAddress, password);
			if (user == null) {
				// 認証失敗
				request.setAttribute("errorMsg", ERR_LOGIN_PARAM_MISTAKE);
				request.getRequestDispatcher("login.jsp").forward(request, response);
				return;
			} else {
				Date date = new Date();//比較用の三週間目の日付
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(Calendar.DATE, -21);
				date = calendar.getTime();
				Date lastLogin = userModel.getLastLogin(mailAddress);
				//最終ログインが比較用の日付(date)よりも前の日付だった場合
				if(date.compareTo(lastLogin) == 1) {
					System.out.println(1);
					request.setAttribute("errorMsg", "最終ログインが三週間を超えているため\nアカウントがロックされています");
					request.getRequestDispatcher("login.jsp").forward(request, response);
				}
				
				// 認証成功(ログイン情報をセッションに保持)
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				response.sendRedirect("MainServlet");
				return;
			}

		} catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}

	}

}
