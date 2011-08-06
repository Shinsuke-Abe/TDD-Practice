package PreTDDBCHandsOn.wikiengin;

// TDDではメソッドのインタフェースが重要
// メソッドのインプットとアウトプットがそれぞれ妥当かをテストクラスで確認する
// APIの最初のユーザは自分である。ということを意識して書けば良い
public interface WikiEngine {

	/**
	 * Wikiフォーマットのテキストをhtmlに変換する
	 * @param text 入力テキスト
	 * @return 変換されたhtml
	 */
	String toHtml(String text);
}
