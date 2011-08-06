package PreTDDBCHandsOn.wikiengin;

import java.util.Stack;
import java.util.regex.Pattern;


public class ListMarkupConverter extends LineSeparatedWikiMarkupConverter {
	
	// リスト行のパターン
	private static final String LIST_ITEM_PATTERN = "^( +[\\*#]).*";
	
	// リスト項目のネストの閉じタグを処理するためのスタック
	private Stack<ListItemIndent> indentStack;
	
	// リストのパターン
	// UNORDERED : 箇条書きリスト
	// ORDERED : ナンバリングリスト
	private enum ListPattern {
		UNORDERED("<ul>", "</ul>"), ORDERED("<ol>", "</ol>");
		private String startTag;
		private String endTag;
		
		ListPattern(String startTag, String endTag) {
			this.startTag = startTag;
			this.endTag = endTag;
		}
		
		public String getStartTag() {
			return startTag;
		}
		
		public String getEndTag() {
			return endTag;
		}
	}
	
	/**
	 * リスト変換のインデント番号とリストの種類をまとめたインナークラス
	 * @author mao
	 *
	 */
	class ListItemIndent {
		ListPattern listPattern;
		int indentNumber;
		
		ListItemIndent(ListPattern listPattern, int indentNumber) {
			this.listPattern = listPattern;
			this.indentNumber = indentNumber;
		}
	}

	@Override
	public void executeBeforeConvert() {
		p4Markup = Pattern.compile(LIST_ITEM_PATTERN);
		indentStack = new Stack<ListItemIndent>();
	}

	@Override
	public void executePatternMatch(String textLine) {
		String item = m4Markup.group(1);
		textMarkupConverted.append(checkAndGenerateListNestedStartTags(item, indentStack));
		textMarkupConverted.append(generateHtmlListItemString(textLine.substring(item.length())));
	}

	@Override
	public void executePatternNoMatch(String textLine) {
		textMarkupConverted.append(generateListNestedEndTags(indentStack));
		textMarkupConverted.append(textLine);
		if(!isLastLine) {
			textMarkupConverted.append("\n");
		}
	}

	@Override
	public void executeAfterMatching() {
		// Do Nothing
	}

	@Override
	public void executeAfterConvert() {
		textMarkupConverted.append(generateListNestedEndTags(indentStack));
	}
	
	/**
	 * ネストをチェックし、必要に応じてスタートタグやネストのエンドタグを出力する。
	 * ネストがかわらない場合は何も出力しない。
	 * @param item リスト行
	 * @param indentStack インデントのスタック
	 * @return スタートタグやネストのエンドタグ
	 */
	private String checkAndGenerateListNestedStartTags(String item, Stack<ListItemIndent> indentStack) {
		int indentNumber = item.length() - 1;
		
		ListPattern listPattern;
		if(item.indexOf("*") > -1) {
			listPattern = ListPattern.UNORDERED;
		} else {
			listPattern = ListPattern.ORDERED;
		}
		
		if(indentStack.empty() || indentStack.peek().indentNumber < indentNumber) {
			// ネストが深くなったとき
			ListItemIndent indent = new ListItemIndent(listPattern, indentNumber);
			indentStack.push(indent);
			return listPattern.getStartTag();
		} else if(indentStack.peek().indentNumber > indentNumber) {
			// ネストが浅くなったとき
			return generateListNestedEndTags(indentStack, indentNumber);
		} else {
			return "";
		}
	}
	
	/**
	 * リスト項目のアイテムをHTMLコードで返す
	 * @param itemString アイテム文字列
	 * @return リスト項目のHTMLコード
	 */
	private String generateHtmlListItemString(String itemString) {
		return "<li>" + itemString + "</li>";
	}
	
	/**
	 * リストタグを閉じていないネストを処理する
	 * @param indentStack インデントのスタック
	 * @param targetIndent ネスト処理をストップするインデント
	 * @return インデント分生成した"</ul>"または"</ol>"の文字列
	 */
	private String generateListNestedEndTags(Stack<ListItemIndent> indentStack, int targetIndent) {
		StringBuffer retString = new StringBuffer();
		while(indentStack.peek().indentNumber > targetIndent) {
			retString.append(indentStack.peek().listPattern.getEndTag());
			indentStack.pop();
		}
		return retString.toString();
	}
	
	/**
	 * ulタグを閉じていないネストを処理する
	 * @param indentStack インデントのスタック
	 * @return インデント分生成した"</ul>"の文字列
	 */
	private String generateListNestedEndTags(Stack<ListItemIndent> indentStack) {
		StringBuffer retString = new StringBuffer();
		while(!indentStack.isEmpty()) {
			retString.append(indentStack.peek().listPattern.getEndTag());
			indentStack.pop();
		}
		return retString.toString();
	}
}
