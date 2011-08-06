package PreTDDBCHandsOn.wikiengin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class LineSeparatedWikiMarkupConverter {
	// �}�[�N�A�b�v�p�̃p�^�[��
	protected Pattern p4Markup;
	
	// �ϊ�������ԋp�p�̃o�b�t�@
	protected StringBuffer textMarkupConverted;
	
	// ���K�\���̃}�b�`���[
	protected Matcher m4Markup;
	
	// �p�^�[���̃u���b�N�����ǂ����𔻒f����t���O
	protected boolean isPatternBlock;
	
	// �ŏI�s�����f���邽�߂̃t���O
	protected boolean isLastLine;
	
	// �u���b�N�̃^�O
	protected String blockTagName;
	
	/**
	 * �s���ƂŔ��ʂ���Wiki�}�[�N�A�b�v��ϊ�����B
	 * TemplateMethod�p�^�[�����g�p���Ă���̂ŁA
	 * �p���N���X�ňȉ��̃��\�b�h�̎������K�v�B
	 *   executeBeforeConvert,
	 *   executePatternMatch,
	 *   executePatternNoMatch,
	 *   executeAfterMatching,
	 *   executeAfterConvert
	 * �}�[�N�A�b�v�̐��K�\����executeBeforeConvert��p4Markup�t�B�[���h�ɃZ�b�g���邱�ƁB
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
	 * �ϊ��̑O�������s��(�����͌p���N���X��)
	 */
	public abstract void executeBeforeConvert();
	
	/**
	 * ���K�\���Ƀ}�b�`�����ꍇ�̏������s��(�����͌p���N���X��)
	 * @param textLine ����������s
	 * @param m4Markup �}�b�`���[
	 * @param isLastLine �ŏI�s�t���O
	 */
	public abstract void executePatternMatch(String textLine);
	
	/**
	 * ���K�\���Ƀ}�b�`���Ȃ������ꍇ�̏������s��(�����͌p���N���X��)
	 * @param textLine ����������s
	 * @param m4Markup �}�b�`���[
	 * @param isLastLine �ŏI�s�t���O
	 */
	public abstract void executePatternNoMatch(String textLine);
	
	/**
	 * ���K�\���̃}�b�`���O���I�������̏���(�����͌p���N���X��)
	 */
	public abstract void executeAfterMatching();
	
	/**
	 * �ϊ��̌㏈�����s��(�����͌p���N���X��)
	 */
	public abstract void executeAfterConvert();
	
	/**
	 * �u���b�N�̊J�n�𔻕ʂ��ĕK�v�ł���ΊJ�n�^�O�𐶐�����
	 */
	protected void checkAndAppendBlockStartTag() {
		if(!isPatternBlock) {
			isPatternBlock = true;
			textMarkupConverted.append("<" + blockTagName + ">");
		}
	}
	
	/**
	 * �u���b�N�̏I���𔻕ʂ��ĕK�v�ł���ΏI���^�O�𐶐�����
	 */
	protected void checkAndAppendBlockEndTag() {
		if(isPatternBlock) {
			isPatternBlock = false;
			textMarkupConverted.append("</" + blockTagName + ">");
		}
	}
}
