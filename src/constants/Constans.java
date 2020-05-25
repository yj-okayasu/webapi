package constants;

/**
 * 
 * 定数定義クラス
 *
 */
public final class Constans {
	// APIアクセスURL
	public static final String BASE_API_URL = "http://webservice.recruit.co.jp/hotpepper/gourmet/v1/?key=402e1668b6cbca99&count=20&%s";
	// APIキーワード検索パラメータ
	public static final String SEARCH_PARAM = "&keyword=%s";
	// API店舗指定パラメータ
	public static final String SELECT_SHOP_PARAM = "&id=%s";
	
	// 文字列・フォーマット定数
	public static final String STATUS_FAVORITE = "お気に入り一覧";    
	public static final String STATUS_SEARCH = "\"%s\" の検索結果";
	
	public static final String MSG_LOGIN_VALIDATION = "IDとパスワードは必須項目です。";
	public static final String MSG_LOGIN_ERROR = "IDまたはパスワードが間違っています。";
	public static final String MSG_GET_SHOP_ERROR = "店舗情報の取得に失敗しました。";
	
	public static final String SEARCH_BUTTON = "検索";
	public static final String FAVORITE_BUTTON = "お気に入り";
}
