package PreTDDBCHandsOn.wikiengin;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WikiEngineImpl implements WikiEngine {
	// ヘッダーのパターン
	private static final Pattern HEADER_PATTERN = Pattern.compile("^(=+) .* (=+)$");
	
	// イタリックに変換する際のパターン
	private static final String ITALIC_PATTERN = ".*(_).*(_).*";
	
	// ボールドに変換する際のパターン
	private static final String BOLD_PATTERN = ".*(\\*).*(\\*).*";
	
	// 打ち消し線に変換する際のパターン
	private static final String STRIKE_PATTERN = ".*(~~).*(~~).*";
	
	// 上付き文字に変換する際のパターン
	private static final String SUPER_PATTERN = ".*(\\^).*(\\^).*";
	
	// 下付き文字に変換する際のパターン
	private static final String SUBSCRIPT_PATTERN = ".*(,,).*(,,).*";
	
	// ソースコード文字列に変換する際のパターン
	private static final String CODE_PATTERN_1 = ".*(\\{\\{\\{).*(\\}\\}\\}).*";
	private static final String CODE_PATTERN_2 = ".*(`).*(`).*";
	
	// リスト行のコンバータ
	private ListMarkupConverter listConverter = new ListMarkupConverter();
	
	// 引用行のコンバータ
	private QuoteMarkupConverter quoteConverter = new QuoteMarkupConverter();
	
	// テーブル行のコンバータ
	private TableMarkupConverter tableConverter = new TableMarkupConverter();
	
	// ヘッダーレベルの最大値
	private static final int HEADER_MAX_NUMBER = 6;
	
	// 変換パターンを保持するリスト
	private ArrayList<TagPattern> patternList = new ArrayList<TagPattern>();
	
	/**
	 * タグの正規表現と変換するタグをまとめたインナークラス
	 * @author mao
	 *
	 */
	class TagPattern {
		Pattern matcher;
		String tagName;
		
		TagPattern(String patternString, String tagName) {
			this.matcher = Pattern.compile(patternString);
			this.tagName = tagName;
		}
	}
	
	/**
	 * デフォルトコンストラクタ
	 */
	public WikiEngineImpl() {
		patternList.add(new TagPattern(ITALIC_PATTERN, "i"));
		patternList.add(new TagPattern(BOLD_PATTERN, "b"));
		patternList.add(new TagPattern(STRIKE_PATTERN, "strike"));
		patternList.add(new TagPattern(SUPER_PATTERN, "sup"));
		patternList.add(new TagPattern(SUBSCRIPT_PATTERN, "sub"));
		patternList.add(new TagPattern(CODE_PATTERN_1, "tt"));
		patternList.add(new TagPattern(CODE_PATTERN_2, "tt"));
	}

	@Override
	public String toHtml(String text) {
		if(text == null) {
			throw new IllegalArgumentException();
		}
		
		// リストの変換
		String retText = listConverter.convert(text);
		
		// Quoteの変換
		retText = quoteConverter.convert(retText);
		
		// テーブルの変換
		retText = tableConverter.convert(retText);
		
		// その他タグの変換
		// パターンマッチのため、改行コードを変換する
		retText = replaceWikiMarkupString(retText.replaceAll("\n", "<CrLf>"));
		
		return retText.replaceAll("<CrLf>", "\n");
	}
	
	/**
	 * Wikiマークアップを再帰的にリプレースする
	 * @param text 対象文字列
	 * @return 返還後の文字列
	 */
	private String replaceWikiMarkupString(String text) {
		String retText = text;
		
		Matcher m = HEADER_PATTERN.matcher(text);
		if(m.find()) {
			String start = m.group(1);
			String end = m.group(2);
			
			int level = start.length();
			if(level <= HEADER_MAX_NUMBER && level == end.length()) {
				// マッチした=の数が同じなら変換する
				retText = TextMarkupConverter.generateHtmlHeaderString(start, "H" + level, text);
			}
		}
		
		for(TagPattern tagPattern : patternList) {
			m = tagPattern.matcher.matcher(retText);
			if(m.find()) {
				retText = TextMarkupConverter.generateHtmlString(m.group(1), m.group(2), tagPattern.tagName, retText);
			}
		}
		
		if(text.equals(retText)) {
			return retText;
		} else {
			return replaceWikiMarkupString(retText);
		}
	}
}
