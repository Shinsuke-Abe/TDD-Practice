package PreTDDBCHandsOn.wikiengin;

public class TextMarkupConverter {
	/**
	 * HTML�^�O�ɕϊ������������Ԃ��B
	 * @param startMarkupString Wiki�}�[�N�A�b�v�̍ŏ��̕�����
	 * @param endMarkupString Wiki�}�[�N�A�b�v�̍Ō�̕�����
	 * @param tag �ϊ�����^�O��
	 * @param text �ϊ���������
	 * @return HTML�^�O�ɕϊ����ꂽ������
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
	 * �w�b�_�p�̕ϊ����\�b�h(���p�󔒂̃��v���[�X���K�v)
	 * @param markupString Wiki�}�[�N�A�b�v�̕���
	 * @param tag �ϊ�����^�O��
	 * @param text �ϊ���������
	 * @return HTML�^�O�ɕϊ����ꂽ������
	 */
	public static String generateHtmlHeaderString(String markupString, String tag, String text) {
		return generateHtmlString(markupString, markupString, tag, text).replace(" ", "");
	}
}
