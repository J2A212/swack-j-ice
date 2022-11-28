package model;

import java.sql.Date;
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
	
	public void passwordFailCountup(String mailAddress) throws SwackException {
		UsersDAO userDAO = new UsersDAO();
		userDAO.passwordFailCountup(mailAddress);
	}
	public int getFailCount(String mailAddress) throws SwackException {
		int failCount = 0;
		UsersDAO usersDAO = new UsersDAO();
		failCount = usersDAO.getFailCount(mailAddress);
		return failCount;
	}
	public void loginUpdate(String loginDate,String mailAddress) throws SwackException {
		UsersDAO userDAO = new UsersDAO();
		userDAO.loginUpdate(loginDate, mailAddress);
	}
	
	public Date getLastLogin(String mailAddress) throws SwackException {
		Date loginDate = null;
		UsersDAO userDAO = new UsersDAO();
		loginDate = userDAO.getLastLogin(mailAddress);
		return loginDate;
	}
	public void setLastLogin(Date loginDate) throws SwackException {
		UsersDAO userDAO = new UsersDAO();
		userDAO.setLastLogin(loginDate);
	}
}
