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
@WebServlet("/DeleteChatServlet")
public class DeleteChatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteChatServlet() { super(); }

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
		String chatLogId = request.getParameter("deleteChatLogId");
		
		RoomModel roomModel = new RoomModel();
		ChatModel chatModel = new ChatModel();
		System.out.println(userId);
		System.out.println("chatLogId"+chatLogId);
		System.out.println("roomId="+roomId);
		System.out.println(1);
		try {
			//管理者ID取得
			System.out.println(2);
			String createdUserId = roomModel.getCreatedUserId(roomId);
			String chatUserId = chatModel.getChatUserId(chatLogId);
			//もしも、編集しようとしたのが管理者又は本人だった場合実行
			System.out.println(createdUserId+"あ"+chatUserId);
			if(createdUserId.equals(userId) || chatUserId.equals(userId)) {
				chatModel.deleteChatLog(chatLogId);
				session.setAttribute("user", user);
				response.sendRedirect("MainServlet");
				return;
			} else {
				request.setAttribute("errorMsg",ERR_SYSTEM);
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
