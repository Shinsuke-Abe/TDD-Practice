package PreTDDBCHandsOn.wikiengin;

// TDD�ł̓��\�b�h�̃C���^�t�F�[�X���d�v
// ���\�b�h�̃C���v�b�g�ƃA�E�g�v�b�g�����ꂼ��Ó������e�X�g�N���X�Ŋm�F����
// API�̍ŏ��̃��[�U�͎����ł���B�Ƃ������Ƃ��ӎ����ď����Ηǂ�
public interface WikiEngine {

	/**
	 * Wiki�t�H�[�}�b�g�̃e�L�X�g��html�ɕϊ�����
	 * @param text ���̓e�L�X�g
	 * @return �ϊ����ꂽhtml
	 */
	String toHtml(String text);
}
