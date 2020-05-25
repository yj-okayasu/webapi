package service;

import java.sql.SQLException;

import dao.Dao;

public class AccountDBAccess {

	/**
	 * ログイン認証
	 * @param accountId:アカウントID
	 * @param passWord:パスワード
	 * @return IDとパスワードに一致するアカウントが
	 * 	                      存在する場合   : 1
	 * 	                      存在しない場合: 0
	 * @throws SQLException
	 */
	public static int Select(String accountId, String passWord) throws SQLException {
		Dao dao = null;
		int result = 0;
		
		try {
			dao = new Dao();
			
			result = dao.getAccount(accountId, passWord);
		}finally {
			if(dao != null) dao.close();
		}
		
		return result;
	}
}
