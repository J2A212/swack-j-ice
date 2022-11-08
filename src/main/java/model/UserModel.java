package model;

import java.util.List;

import dao.UsersDAO;
import exception.SwackException;

public class UserModel {
	public List<String> getUserNameList() throws SwackException {

		UsersDAO usersDAO = new UsersDAO();

		List<String> userlist = usersDAO.selectAllName();

		return userlist;

	}

}
