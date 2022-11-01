package model;

import bean.User;
import dao.UsersDAO;
import exception.SwackException;

public class LoginModel {

	public User checkLogin(String mailAddress, String password) throws SwackException {
		System.out.println("[checkLogin] " + mailAddress + " " + password);
//		User user 
//		= new User("U0001", "ダミー太郎",
//				mailAddress, "********");

		UsersDAO usersDAO = new UsersDAO();

		User user = usersDAO.select(mailAddress, password);

		return user;

	}

}
