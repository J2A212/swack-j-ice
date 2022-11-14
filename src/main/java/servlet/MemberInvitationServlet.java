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

import bean.Room;
import bean.User;
import exception.SwackException;
import model.RoomModel;

/**
 * Servlet implementation class MemberInvitation
 */
@WebServlet("/MemberInvitationServlet")
public class MemberInvitationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			RoomModel roomModel = new RoomModel();
			// 名前のリストを受け取る
			List<Room> roomList = roomModel.getRoomNameList();

			// メイン画面を表示する
			request.setAttribute("roomList", roomList);
			for(int i = 0; i< roomList.size();i++) {
				System.out.println(roomList.get(i));
			}
			request.getRequestDispatcher("joinroom.jsp").forward(request, response);

		} catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			return;

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		String roomname = request.getParameter("roomname");
		String createUserId = user.getUserId();
		// 下二つは仮のデータ
		boolean directed = false;
		boolean privated = false;
		System.out.println("roomname: " + roomname);


		String[] invitemembers = request.getParameterValues("join");

		RoomModel roomModel = new RoomModel();
		
		
		try {
			// 新規ルーム作成
			String newRoomId=roomModel.newRoom(roomname, createUserId, directed, privated);
			
			for (String memberValue: invitemembers) {
				roomModel.joinRoom(newRoomId, createUserId);
				System.out.println("invitemember: " + memberValue);
			}

			session.setAttribute("user", user);
			response.sendRedirect("main.jsp");
			return;

		} catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.getRequestDispatcher("main.jsp").forward(request, response);
			return;

		}
	}

}
