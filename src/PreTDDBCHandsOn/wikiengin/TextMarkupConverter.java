package PreTDDBCHandsOn.wikiengin;

public class TextMarkupConverter {
	/**
	 * HTMLタグに変換した文字列を返す。
	 * @param startMarkupString Wikiマークアップの最初の文字列
	 * @param endMarkupString Wikiマークアップの最後の文字列
	 * @param tag 変換するタグ名
	 * @param text 変換元文字列
	 * @return HTMLタグに変換された文字列
	 */
	public static String generateHtmlString(String startMarkupString, String endMarkupString, String tag, String text) {
		int startIndex = text.indexOf(startMarkupString) + startMarkupString.length();
		int endIndex = text.indexOf(endMarkupString, startIndex);
		String body = text.substring(startIndex, endIndex);
		
		String beforeString = startMarkupString + body + endMarkupString;
		String afterString = "<" + tag + ">" + body + "</" + tag + ">";
		return text.replace(beforeString, afterString);
	}
	
	/**
	 * ヘッダ用の変換メソッド(半角空白のリプレースが必要)
	 * @param markupString Wikiマークアップの文字
	 * @param tag 変換するタグ名
	 * @param text 変換元文字列
	 * @return HTMLタグに変換された文字列
	 */
	public static String generateHtmlHeaderString(String markupString, String tag, String text) {
		return generateHtmlString(markupString, markupString, tag, text).replace(" ", "");
	}
}
