package servlet;

import static parameter.Messages.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import exception.SwackException;
import model.ChatModel;
import model.RoomModel;

/**
 * Servlet implementation class ManagementUserJudgeServlet
 */
@WebServlet("/ManagementUserJudgeServlet")
public class ManagementUserJudgeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManagementUserJudgeServlet() { super(); }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String userId = user.getUserId();
		String roomId = request.getParameter("roomId");
		String chatLogId = request.getParameter("chatLogId");
		String message = request.getParameter("message");
		String which_UD = request.getParameter("which_UD");	//更新か削除かの判定するために仮の名前をつけたので変えていいです
		
		RoomModel roomModel = new RoomModel();
		ChatModel chatModel = new ChatModel();
		
		try {
			String createdUserId = roomModel.getCreatedUserId(roomId);
			//もしも、編集しようとしたのが管理者だった場合
			if(createdUserId.equals(userId)) {
				//更新か削除か
				if(which_UD.equals("update")) {
					chatModel.updateChatLog(chatLogId, message);
				} else {
					chatModel.deleteChatLog(chatLogId);
				}
				session.setAttribute("user", user);
				response.sendRedirect("MainServlet");
				return;
			} else {
				request.setAttribute("errorMsg", "管理者権限がないユーザのため実行できません。");
				request.getRequestDispatcher("main.jsp").forward(request, response);
				return;
			}
		} catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.getRequestDispatcher("main.jsp").forward(request, response);
			return;
		}
	}

}
