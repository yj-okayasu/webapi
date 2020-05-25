package dto;

import java.io.Serializable;

/**
 * 
 * 店舗クラス
 * 店舗情報を格納するクラスです
 *
 */
public class Shop implements Serializable{
	private static final long serialVersionUID = 1L;
	
	// 指示4: 以下を参考に店舗情報を2つ追加する。
	
	// 店舗ID
	public String id;
	// 店舗名
	public String name;
	// 店舗ロゴURL
	public String logoImage;
	// 店舗住所
	public String address;
	
	// ジャンル
	public String genre;
	// 店舗キャッチコピー
	public String shopCatch;
	// お気に入り登録済みフラグ
	public boolean favorite = false;
	// 店舗情報URL
	public String shopUrl;
	
	// ゲッター/セッター
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getLogoImage() {
		return logoImage;
	}

	public void setLogoImage(String logoImage) {
		this.logoImage = logoImage;
	}
	
	public String getaddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public String getShopCatch() {
		return shopCatch;
	}

	public void setShopCatch(String shopCatch) {
		this.shopCatch = shopCatch;
	}
	
	public boolean getFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
	
	public String getShopUrl() {
		return shopUrl;
	}

	public void setShopUrl(String shopUrl) {
		this.shopUrl = shopUrl;
	}
}