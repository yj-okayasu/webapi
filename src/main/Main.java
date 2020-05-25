package main;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.FavoriteDBAccess;
import service.ShopAPI;

import java.sql.SQLException;
import java.util.ArrayList;
import constants.Constans;
import dto.Shop;

/**
 * メインサーブレット
 */
@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Main() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// セッションを取得
		HttpSession session = request.getSession();
		// アカウントIDを取得
		String accountId = (String)session.getAttribute("account");
		
		if(accountId == null || accountId.isBlank()) {
			// セッションにアカウント情報がない場合は、ログイン画面へリダイレクト
			response.sendRedirect("Login");
		}else {
			// セッションにお気に入りのリストが存在しない場合
			if(session.getAttribute("favoriteList") == null) {
				// DBからお気に入りのリストを取得
				ArrayList<String> favoriteShopList = new ArrayList<String>();
				try {
					// お気に入りのリストを取得
					favoriteShopList = FavoriteDBAccess.Select(accountId);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				// お気に入りのリストをセッションに設定
				session.setAttribute("favoriteList", favoriteShopList);
			}
			
			// 追加指示2: ここに上記を参考にしてエリアのリストを取得する処理を追加する
			
			ServletContext context = getServletContext();
			RequestDispatcher dis = context.getRequestDispatcher("/main.jsp");
			dis.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// セッションを取得
		HttpSession session = request.getSession();
		// 押されたボタンを取得
		String action = request.getParameter("action");
		
		// お気に入りのリスト取得
		ArrayList<String> favoriteShopList = (ArrayList<String>)session.getAttribute("favoriteList");

		if(action.equals("検索")) {
			// 検索ボタンからのPOSTの場合
			
			// 検索キーワード
			String prmKeyword = request.getParameter("keyword");
			// リクエストに検索キーワードを設定
			request.setAttribute("search", prmKeyword);

			// 店舗リストを新規取得
			ArrayList<Shop> shopList = ShopAPI.keywordSearch(prmKeyword);
			if(shopList != null) {
				// 店舗リスト内の各店舗クラスにお気に入りフラグを設定
				shopList.forEach(x -> x.setFavorite(favoriteShopList.contains(x.getId())));
				// 検索した店舗のリストをリクエストへ設定
				request.setAttribute("shopList", shopList);
				
				// ステータスを表示
				String status = String.format(Constans.STATUS_SEARCH, prmKeyword);
				request.setAttribute("status", status);
			}
			else request.setAttribute("error", Constans.MSG_GET_SHOP_ERROR); // エラーメッセージをリクエストに設定

			ServletContext context = getServletContext();
			RequestDispatcher dis = context.getRequestDispatcher("/main.jsp");
			dis.forward(request, response);
		}else if(action.equals("お気に入り")) {
			// お気に入りボタンからのPOSTの場合
			
			// お気に入り店舗の店舗リストを取得
			ArrayList<Shop> shopList = ShopAPI.shopIdSearch(favoriteShopList);
			if(shopList != null) {
				// 店舗リスト内の各店舗クラスにお気に入りフラグを設定
				shopList.forEach(x -> x.setFavorite(favoriteShopList.contains(x.getId())));
				// 検索した店舗のリストをリクエストへ設定
				request.setAttribute("shopList", shopList);
				
				// ステータスを表示
				String status = Constans.STATUS_FAVORITE;
				request.setAttribute("status", status);
			}
			else request.setAttribute("error", Constans.MSG_GET_SHOP_ERROR); // エラーメッセージをリクエストに設定
			
			ServletContext context = getServletContext();
			RequestDispatcher dis = context.getRequestDispatcher("/main.jsp");
			dis.forward(request, response);
		}
	}
}
