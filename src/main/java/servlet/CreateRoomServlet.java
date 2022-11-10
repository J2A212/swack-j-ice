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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		String roomName = request.getParameter("name");
		String createUserId = user.getUserId();
		//下二つは仮のデータ
		String directed = "false";
		String privated = "false";
		
		RoomModel roomModel= new RoomModel();
		
		try {
			//新規ルーム作成
			roomModel.newRoom(roomName, createUserId, directed, privated);
			
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		try {
			UserModel userModel = new UserModel();
			//名前のリストを受け取る
			List<User> userList = userModel.getUserNameList();
			

			// メイン画面を表示する
			request.setAttribute("nowUser", user);
			request.setAttribute("userList", userList);

		} catch (SwackException e) {
			e.printStackTrace();
			request.setAttribute("errorMsg", ERR_SYSTEM);
			request.getRequestDispatcher("createroom.jsp").forward(request, response);
			return;

		}
		
	}

}
