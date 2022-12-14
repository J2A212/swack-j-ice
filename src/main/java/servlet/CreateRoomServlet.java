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

import bean.User;
import exception.SwackException;
import model.RoomModel;
import model.UserModel;

/**
 * Servlet implementation class CreateRoomServlet
 */
@WebServlet("/CreateRoomServlet")
public class CreateRoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateRoomServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		String roomname = request.getParameter("roomname");
		String createUserId = user.getUserId();
		// 下二つは仮のデータ
		boolean directed = false;
		String roomkbn = request.getParameter("roomkbn");
		boolean privated = false;
		if(roomkbn==null) {
			privated = true;
		}
		System.out.println("roomname: " + roomname);
		System.out.println("roomkbn"+privated);
		
		String[] invitemembers = request.getParameterValues("invitemembers");

		RoomModel roomModel = new RoomModel();
		UserModel userModel = new UserModel();
		String Uid;
		try {
			// 新規ルーム作成
			String newRoomId=roomModel.newRoom(roomname, createUserId, directed, privated);
			int i=0;
			for (String memberValue: invitemembers) {
				System.out.println(invitemembers[i]);
				Uid=userModel.getUserId(invitemembers[i]);
				roomModel.joinRoom(newRoomId, Uid);
				i++;
				System.out.println("invitemember: " + memberValue);
			}
			
			session.setAttribute("user", user);
			response.sendRedirect("MainServlet");
			return;

		} catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.getRequestDispatcher("main.jsp").forward(request, response);
			return;

		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		System.out.println("あ");

		try {
			UserModel userModel = new UserModel();
			// 名前のリストを受け取る
			List<User> userList = userModel.getUserNameList();

			// メイン画面を表示する
			request.setAttribute("nowUser", user);
			for(int i = 0; i< userList.size();i++) {
				System.out.println(userList.get(i));
			}
			request.setAttribute("userList", userList);
			request.getRequestDispatcher("createroom.jsp").forward(request, response);

		} catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			return;

		}

	}

}
