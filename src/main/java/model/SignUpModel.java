package model;

import bean.User;
import dao.UsersDAO;
import exception.SwackException;

public class SignUpModel {
	public void insert(User user) throws SwackException {
		
		UsersDAO usersDAO = new UsersDAO();
		usersDAO.insert(user);

	}
	public String selectMaxUserId() throws SwackException{
		UsersDAO usersDAO=new UsersDAO();
		
		String id=usersDAO.selectMaxUserId();
			
		return id;
		
	}
}
