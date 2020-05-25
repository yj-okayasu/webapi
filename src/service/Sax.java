package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class Sax extends DefaultHandler {

	// XML階層リスト
	private LinkedList<String> pathList = new LinkedList<String>();
    // 読み込みフラグtrue
    private boolean read = false;
    // 情報マップ配列
    public ArrayList<HashMap<String, String>> blockList = new ArrayList<HashMap<String, String>>();
    // 情報マップ
    private HashMap<String, String> crHashMap;
    // 区切りタグ
    private String separateTag;
    
    /**
     * コンストラクタ
     * @param sepTag:
     */
    public Sax(String sepTag) {
    	separateTag = sepTag;
    }
 
    /**
    * ドキュメント開始
    */
    public void startDocument() {
    	System.out.println("Read XMLDocument.");
    }
 
    /**
    * ドキュメント終了
    */
    public void endDocument() {
    	System.out.println("Read XMLDocument is Done.");
    }
 
    /**
    * 要素の開始タグ読み込み時に毎回呼ばれる
    */
    public void startElement(String uri,
                               String localName,
                               String qName,
                               Attributes attributes) {
    	if(qName.equals(separateTag)) {
    		// 情報を格納するHashMapを生成
    		crHashMap = new HashMap<String, String>();
    		// 読み込みフラグtrue
    		read = true;
    	}else if(read){
    		// XML階層パスに現在のタグを追加
    		pathList.add(qName);
    	}
    }
 
    /**
    * テキストデータ読み込み時に毎回呼ばれる
    */
    public void characters(char[] ch,
                            int offset,
                            int length) {
		// 配列をドット区切りにしてパス化
		String path = String.join(".", pathList);
    	// ハッシュマップにタグの値を追加
    	if(read) crHashMap.put(path, new String(ch, offset, length));
    }
 
    /**
    * 要素の終了タグ読み込み時に毎回呼ばれる
    */
    public void endElement(String uri,
                           String localName,
                           String qName) {
		
    	// パスの終端タグ名と終了タグ名が一致する場合
    	if(pathList.size() > 0 && pathList.getLast().equals(qName)) {
			// 終端タグを削除
    		pathList.removeLast();
		}
    	
    	if(qName.equals(separateTag)) {
    		// リストに情報マップを追加
    		blockList.add(crHashMap);
    		read = false;
    	}
    }
}