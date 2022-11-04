package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		} else if(password != passwordRepeat) {
			errorMsg.append("確認用パスワードが一致していません<br>");
		}
		if (errorMsg.length() > 0) {
			// エラー
			request.setAttribute("errorMsg", errorMsg.toString());
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}
		
		
	}

}
