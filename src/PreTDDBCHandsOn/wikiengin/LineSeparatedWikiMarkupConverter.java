package PreTDDBCHandsOn.wikiengin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class LineSeparatedWikiMarkupConverter {
	// マークアップ用のパターン
	protected Pattern p4Markup;
	
	// 変換文字列返却用のバッファ
	protected StringBuffer textMarkupConverted;
	
	// 正規表現のマッチャー
	protected Matcher m4Markup;
	
	// パターンのブロック中かどうかを判断するフラグ
	protected boolean isPatternBlock;
	
	// 最終行か判断するためのフラグ
	protected boolean isLastLine;
	
	// ブロックのタグ
	protected String blockTagName;
	
	/**
	 * 行ごとで判別するWikiマークアップを変換する。
	 * TemplateMethodパターンを使用しているので、
	 * 継承クラスで以下のメソッドの実装が必要。
	 *   executeBeforeConvert,
	 *   executePatternMatch,
	 *   executePatternNoMatch,
	 *   executeAfterMatching,
	 *   executeAfterConvert
	 * マークアップの正規表現はexecuteBeforeConvertでp4Markupフィールドにセットすること。
	 * @param text
	 * @return
	 */
	public String convert(String text) {
		String[] textTargetStringArray = text.split("\n");
		textMarkupConverted = new StringBuffer();
		
		executeBeforeConvert();
		
		for(int i = 0; i < textTargetStringArray.length; i++) {
			String textLine = textTargetStringArray[i];
			m4Markup = p4Markup.matcher(textLine);
			isLastLine = (i == textTargetStringArray.length - 1);
			
			if(m4Markup.find()) {
				executePatternMatch(textLine);
			} else {
				executePatternNoMatch(textLine);
			}
			
			executeAfterMatching();
		}
		
		executeAfterConvert();
		
		return textMarkupConverted.toString();
	}
	
	/**
	 * 変換の前処理を行う(実装は継承クラスで)
	 */
	public abstract void executeBeforeConvert();
	
	/**
	 * 正規表現にマッチした場合の処理を行う(実装は継承クラスで)
	 * @param textLine 処理をする行
	 * @param m4Markup マッチャー
	 * @param isLastLine 最終行フラグ
	 */
	public abstract void executePatternMatch(String textLine);
	
	/**
	 * 正規表現にマッチしなかった場合の処理を行う(実装は継承クラスで)
	 * @param textLine 処理をする行
	 * @param m4Markup マッチャー
	 * @param isLastLine 最終行フラグ
	 */
	public abstract void executePatternNoMatch(String textLine);
	
	/**
	 * 正規表現のマッチングを終わった後の処理(実装は継承クラスで)
	 */
	public abstract void executeAfterMatching();
	
	/**
	 * 変換の後処理を行う(実装は継承クラスで)
	 */
	public abstract void executeAfterConvert();
	
	/**
	 * ブロックの開始を判別して必要であれば開始タグを生成する
	 */
	protected void checkAndAppendBlockStartTag() {
		if(!isPatternBlock) {
			isPatternBlock = true;
			textMarkupConverted.append("<" + blockTagName + ">");
		}
	}
	
	/**
	 * ブロックの終了を判別して必要であれば終了タグを生成する
	 */
	protected void checkAndAppendBlockEndTag() {
		if(isPatternBlock) {
			isPatternBlock = false;
			textMarkupConverted.append("</" + blockTagName + ">");
		}
	}
}
