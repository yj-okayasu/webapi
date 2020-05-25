package service;

import java.sql.SQLException;
import java.util.ArrayList;
import dao.Dao;

public class FavoriteDBAccess {

	public static ArrayList<String> Select(String accountId) throws SQLException {
		Dao dao = null;
		ArrayList<String> result = null;
		
		try {
			dao = new Dao();
			
			result = dao.getFavoriteShopIds(accountId);
		}finally {
			if(dao != null) dao.close();
		}
		
		return result;
	}
	
	public static int Insert(String accountId, String shopId) throws SQLException {
		Dao dao = null;
		int result = 0;
		
		try {
			dao = new Dao();
			
			result = dao.insertFavorite(accountId, shopId);
		}finally {
			if(dao != null) dao.close();
		}
		
		return result;
	}
	
	public static int Delete(String accountId, String shopId) throws SQLException {
		Dao dao = null;
		int result = 0;
		
		try {
			dao = new Dao();
			
			result = dao.deleteFavorite(accountId, shopId);
		}finally {
			if(dao != null) dao.close();
		}
		
		return result;
	}
}
