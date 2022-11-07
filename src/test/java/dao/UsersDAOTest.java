package dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import bean.User;
import exception.SwackException;

class UsersDAOTest {

	@Test
	void testSelect() throws SwackException {
		UsersDAO usersDAO = new UsersDAO();
		
		User user 
		= usersDAO.select("taro@swack.com", "swack0001");
		
		System.out.println("testSelect()");
		System.out.println(user);
	}

	@Test
	void testExists() {
		fail("まだ実装されていません");
	}

	@Test
	void testSelectMaxUserId() {
		UsersDAO usersDAO = new UsersDAO();
		
		String testResult = usersDAO.selectMaxUserId();
		
		System.out.println(testSelectMaxUserId);
		System.out.println(testResult);
	}

	@Test
	void testInsert() {
		fail("まだ実装されていません");
	}

}
