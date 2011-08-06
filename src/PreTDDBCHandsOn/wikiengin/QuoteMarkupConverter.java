package PreTDDBCHandsOn.wikiengin;

import java.util.regex.Pattern;

public class QuoteMarkupConverter extends LineSeparatedWikiMarkupConverter {
	
	// 引用行のパターン
	private static final String QUOTE_PATTERN = "^( +).*";

	@Override
	public void executeBeforeConvert() {
		blockTagName = "blockquote";
		p4Markup = Pattern.compile(QUOTE_PATTERN);
		isPatternBlock = false;
	}

	@Override
	public void executePatternMatch(String textLine) {
		String quote = m4Markup.group(1);
		checkAndAppendBlockStartTag();
		textMarkupConverted.append(textLine.substring(quote.length()));
	}

	@Override
	public void executePatternNoMatch(String textLine) {
		checkAndAppendBlockEndTag();
		textMarkupConverted.append(textLine);
	}
	
	@Override
	public void executeAfterMatching() {
		if(!isLastLine) {
			textMarkupConverted.append("\n");
		}
	}

	@Override
	public void executeAfterConvert() {
		checkAndAppendBlockEndTag();
	}

}
