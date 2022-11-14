package servlet;

import static parameter.Messages.*;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ChatLog;
import bean.Room;
import bean.User;
import exception.SwackException;
import model.ChatModel;

/**
 * Servlet implementation class JoinRoomServlet
 */
@WebServlet("/JoinRoomServlet")
public class JoinRoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		String roomId = request.getParameter("roomId");
		if (roomId == null) {
			roomId = "R0000";
		}

		try {
			ChatModel chatModel = new ChatModel();
			Room room = chatModel.getRoom(roomId, user.getUserId());
			List<Room> roomList = chatModel.getRoomList(user.getUserId());
			List<Room> directList = chatModel.getDirectList(user.getUserId());
			List<ChatLog> chatLogList = chatModel.getChatlogList(roomId);

			// メイン画面を表示する
			request.setAttribute("nowUser", user);
			request.setAttribute("nowRoom", room);
			request.setAttribute("roomList", roomList);
			request.setAttribute("directList", directList);
			request.setAttribute("chatLogList", chatLogList);

		} catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;

		}

		request.getRequestDispatcher("main.jsp").forward(request, response);
	}

}
