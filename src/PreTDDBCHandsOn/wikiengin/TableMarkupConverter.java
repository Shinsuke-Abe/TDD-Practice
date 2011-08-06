package PreTDDBCHandsOn.wikiengin;

import java.util.regex.Pattern;

public class TableMarkupConverter extends LineSeparatedWikiMarkupConverter {
	
	// テーブル業のパターン
	private static final String TABLE_PATTERN = "^(\\|\\| ).*( \\|\\|)$";

	@Override
	public void executeBeforeConvert() {
		blockTagName = "table";
		p4Markup = Pattern.compile(TABLE_PATTERN);
		isPatternBlock = false;
	}

	@Override
	public void executePatternMatch(String textLine) {
		checkAndAppendBlockStartTag();
		textMarkupConverted.append("<tr>" + TextMarkupConverter.generateHtmlString(m4Markup.group(1), m4Markup.group(2), "td", textLine) + "</tr>");
	}

	@Override
	public void executePatternNoMatch(String textLine) {
		checkAndAppendBlockEndTag();
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
		checkAndAppendBlockEndTag();
	}

}
