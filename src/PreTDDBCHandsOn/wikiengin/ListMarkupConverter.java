package PreTDDBCHandsOn.wikiengin;

import java.util.Stack;
import java.util.regex.Pattern;


public class ListMarkupConverter extends LineSeparatedWikiMarkupConverter {
	
	// ���X�g�s�̃p�^�[��
	private static final String LIST_ITEM_PATTERN = "^( +[\\*#]).*";
	
	// ���X�g���ڂ̃l�X�g�̕��^�O���������邽�߂̃X�^�b�N
	private Stack<ListItemIndent> indentStack;
	
	// ���X�g�̃p�^�[��
	// UNORDERED : �ӏ��������X�g
	// ORDERED : �i���o�����O���X�g
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
	 * ���X�g�ϊ��̃C���f���g�ԍ��ƃ��X�g�̎�ނ��܂Ƃ߂��C���i�[�N���X
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
	 * �l�X�g���`�F�b�N���A�K�v�ɉ����ăX�^�[�g�^�O��l�X�g�̃G���h�^�O���o�͂���B
	 * �l�X�g�������Ȃ��ꍇ�͉����o�͂��Ȃ��B
	 * @param item ���X�g�s
	 * @param indentStack �C���f���g�̃X�^�b�N
	 * @return �X�^�[�g�^�O��l�X�g�̃G���h�^�O
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
			// �l�X�g���[���Ȃ����Ƃ�
			ListItemIndent indent = new ListItemIndent(listPattern, indentNumber);
			indentStack.push(indent);
			return listPattern.getStartTag();
		} else if(indentStack.peek().indentNumber > indentNumber) {
			// �l�X�g���󂭂Ȃ����Ƃ�
			return generateListNestedEndTags(indentStack, indentNumber);
		} else {
			return "";
		}
	}
	
	/**
	 * ���X�g���ڂ̃A�C�e����HTML�R�[�h�ŕԂ�
	 * @param itemString �A�C�e��������
	 * @return ���X�g���ڂ�HTML�R�[�h
	 */
	private String generateHtmlListItemString(String itemString) {
		return "<li>" + itemString + "</li>";
	}
	
	/**
	 * ���X�g�^�O����Ă��Ȃ��l�X�g����������
	 * @param indentStack �C���f���g�̃X�^�b�N
	 * @param targetIndent �l�X�g�������X�g�b�v����C���f���g
	 * @return �C���f���g����������"</ul>"�܂���"</ol>"�̕�����
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
	 * ul�^�O����Ă��Ȃ��l�X�g����������
	 * @param indentStack �C���f���g�̃X�^�b�N
	 * @return �C���f���g����������"</ul>"�̕�����
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
