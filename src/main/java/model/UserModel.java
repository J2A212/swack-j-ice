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
	public String getUserId(String userName)throws SwackException {
		String userId=null;
		
		UsersDAO userDAO=new UsersDAO();
		userId=userDAO.selectId(userName);
		return userId;
	}
	public String getPassword(String mailAddress) throws SwackException {
		String password = "";
		
		UsersDAO userDAO = new UsersDAO();
		password = userDAO.getPassword(mailAddress);
		return password;
	}
}
