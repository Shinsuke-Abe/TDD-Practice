package PreTDDBCHandsOn.wikiengin;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WikiEngineImpl implements WikiEngine {
	// �w�b�_�[�̃p�^�[��
	private static final Pattern HEADER_PATTERN = Pattern.compile("^(=+) .* (=+)$");
	
	// �C�^���b�N�ɕϊ�����ۂ̃p�^�[��
	private static final String ITALIC_PATTERN = ".*(_).*(_).*";
	
	// �{�[���h�ɕϊ�����ۂ̃p�^�[��
	private static final String BOLD_PATTERN = ".*(\\*).*(\\*).*";
	
	// �ł��������ɕϊ�����ۂ̃p�^�[��
	private static final String STRIKE_PATTERN = ".*(~~).*(~~).*";
	
	// ��t�������ɕϊ�����ۂ̃p�^�[��
	private static final String SUPER_PATTERN = ".*(\\^).*(\\^).*";
	
	// ���t�������ɕϊ�����ۂ̃p�^�[��
	private static final String SUBSCRIPT_PATTERN = ".*(,,).*(,,).*";
	
	// �\�[�X�R�[�h������ɕϊ�����ۂ̃p�^�[��
	private static final String CODE_PATTERN_1 = ".*(\\{\\{\\{).*(\\}\\}\\}).*";
	private static final String CODE_PATTERN_2 = ".*(`).*(`).*";
	
	// ���X�g�s�̃R���o�[�^
	private ListMarkupConverter listConverter = new ListMarkupConverter();
	
	// ���p�s�̃R���o�[�^
	private QuoteMarkupConverter quoteConverter = new QuoteMarkupConverter();
	
	// �e�[�u���s�̃R���o�[�^
	private TableMarkupConverter tableConverter = new TableMarkupConverter();
	
	// �w�b�_�[���x���̍ő�l
	private static final int HEADER_MAX_NUMBER = 6;
	
	// �ϊ��p�^�[����ێ����郊�X�g
	private ArrayList<TagPattern> patternList = new ArrayList<TagPattern>();
	
	/**
	 * �^�O�̐��K�\���ƕϊ�����^�O���܂Ƃ߂��C���i�[�N���X
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
	 * �f�t�H���g�R���X�g���N�^
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
		
		// ���X�g�̕ϊ�
		String retText = listConverter.convert(text);
		
		// Quote�̕ϊ�
		retText = quoteConverter.convert(retText);
		
		// �e�[�u���̕ϊ�
		retText = tableConverter.convert(retText);
		
		// ���̑��^�O�̕ϊ�
		// �p�^�[���}�b�`�̂��߁A���s�R�[�h��ϊ�����
		retText = replaceWikiMarkupString(retText.replaceAll("\n", "<CrLf>"));
		
		return retText.replaceAll("<CrLf>", "\n");
	}
	
	/**
	 * Wiki�}�[�N�A�b�v���ċA�I�Ƀ��v���[�X����
	 * @param text �Ώە�����
	 * @return �ԊҌ�̕�����
	 */
	private String replaceWikiMarkupString(String text) {
		String retText = text;
		
		Matcher m = HEADER_PATTERN.matcher(text);
		if(m.find()) {
			String start = m.group(1);
			String end = m.group(2);
			
			int level = start.length();
			if(level <= HEADER_MAX_NUMBER && level == end.length()) {
				// �}�b�`����=�̐��������Ȃ�ϊ�����
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
