package model;

import java.util.List;

import bean.User;
import dao.UsersDAO;
import exception.SwackException;

public class UserModel {
	public List<User> getUserNameList() throws SwackException {

		UsersDAO usersDAO = new UsersDAO();

		List<User> userlist = usersDAO.selectAll();

		return userlist;

	}

}
