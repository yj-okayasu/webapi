package service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import constants.Constans;
import dto.Shop;

public class ShopAPI {
	/**
	 * 店舗情報をShopクラスに格納して、店舗リストを生成
	 * @param urlStr:APIのURL文字列
	 * @return　店舗のリスト
	 * @throws IOException
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	private static ArrayList<Shop> getShopList(String urlStr) throws IOException, ParserConfigurationException, SAXException {
		ArrayList<Shop> shopList = new ArrayList<Shop>();
		ArrayList<HashMap<String, String>> shopMapList = getShopMapList(urlStr);

		// ここで店舗クラスにAPIから取得した情報を設定
		for(HashMap<String, String> map : shopMapList) {
			// 指示4: map.get("ここに取得したい情報のタグ名") で店舗情報が取得できる。 
			//       タグの一覧は課題指示書を参考にする。
			Shop shop = new Shop();
			shop.setId(map.get("id")); // 店舗ID
			shop.setName(map.get("name")); // 店舗名
			shop.setAddress(map.get("address")); // 住所
			shop.setGenre(map.get("genre.name")); // ジャンル
			shop.setShopCatch(map.get("catch")); // キャッチコピー
			shop.setLogoImage(map.get("logo_image")); // ロゴ画像のURL
			shop.setShopUrl(map.get("urls.pc")); // ホットペッパーのURL
			
			// 店舗リストに店舗クラスを追加
			shopList.add(shop);
		}
		
		return shopList;
	}
	
	/**
	 * APIから店舗情報リストを取得
	 * @param urlStr
	 * @return
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	private static ArrayList<HashMap<String, String>> getShopMapList(String urlStr) throws IOException, ParserConfigurationException, SAXException{
		// APIへの接続準備
		URL url = new URL(urlStr);
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
		urlConn.setRequestMethod("GET");
		urlConn.setInstanceFollowRedirects(false);
		urlConn.setRequestProperty("Accept-Language", "ja;q=0.7,en;q=0.3");
		// APIへ接続
		urlConn.connect();
		
		// SAXを生成
		SAXParserFactory spfactory = SAXParserFactory.newInstance();
		// 追加指示2 AreaAPI.java を作成する際はSax("service_area")とする。
		Sax sax = new Sax("shop");
		SAXParser parser = null;
		
		parser = spfactory.newSAXParser();
		parser.parse(urlConn.getInputStream(), sax);

		urlConn.disconnect();
		
		return sax.blockList;
	}
	
	/**
	 * キーワード検索APIのURLを生成
	 * @param str:検索キーワード
	 * @return 検索パラメータを含めたAPIのURL文字列
	 * @throws UnsupportedEncodingException
	 */
	public static String getSearchUrl(String keyword) throws UnsupportedEncodingException {
		// 検索キーワードの全角スペースはAPI側で無視されるので、半角スペースへ置き換え
		String reqKeyword = keyword.replaceAll("　", " ");
		// 検索キーワードをURLエンコード
		String encoded = URLEncoder.encode(reqKeyword, "UTF-8");
		// APIへPOSTするパラメータを生成
		String param = String.format(Constans.SEARCH_PARAM, encoded);
		// APIのURLを返す
		return String.format(Constans.BASE_API_URL, param);
	}
	
	/**
	 * 店舗検索APIのURLを生成
	 * @param shopIdList
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getSearchShopIdUrl(ArrayList<String> shopIdList) throws UnsupportedEncodingException {
		// APIへPOSTするパラメータ
		StringBuilder param = new StringBuilder();
		
		for(String shopId : shopIdList) {
			// 店舗IDをパラメータに追加
			param.append(String.format(Constans.SELECT_SHOP_PARAM, shopId));
		}
		// APIのURLを返す
		return String.format(Constans.BASE_API_URL, param);
	}
	
	/**
	 * キーワード検索
	 * @param keyword:検索キーワード
	 * @return 店舗のリスト
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public static ArrayList<Shop> keywordSearch(String keyword) throws UnsupportedEncodingException, IOException {
		ArrayList<Shop> result = null;
		
		try {
			result = getShopList(getSearchUrl(keyword));
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 店舗ID検索
	 * @param favoriteShopList:
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public static ArrayList<Shop> shopIdSearch(ArrayList<String> shopIdList) throws UnsupportedEncodingException, IOException {
		ArrayList<Shop> result = null;
		
		try {
			result = getShopList(getSearchShopIdUrl(shopIdList));
		} catch (IOException | ParserConfigurationException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
}