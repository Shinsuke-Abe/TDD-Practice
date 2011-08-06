package PreTDDBCHandsOn.wikiengintest;


import org.junit.Before;
import org.junit.Test;

import PreTDDBCHandsOn.wikiengin.WikiEngine;
import PreTDDBCHandsOn.wikiengin.WikiEngineImpl;

import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsInstanceOf.*;
import static org.junit.Assert.*;

public class WikiEngineImplTest {
	
	WikiEngineImpl target;
	
	@Before
	public void setUp() {
		target = new WikiEngineImpl();
	}

	// �e�X�g�P�[�X1
	//   ���͂��ꂽ�e�L�X�g�����̂܂ܕԂ���邱��
	//   Input: Hello World
	//   Output: Hello World
	@Test
	public void testToHtml_HelloWorld() {
		String input = "Hello World";
		String expected = "Hello World";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// �e�X�g�P�[�X2
	//   ���͂��ꂽ�e�L�X�g�����̂܂ܕԂ���邱��
	//   Input: TDDBootCamp
	//   Output: TDDBootCamp
	//   ��Triangulation
	@Test
	public void testToHtml_TDDBootCamp() {
		String input = "TDDBootCamp";
		String expected = "TDDBootCamp";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// �e�X�g�P�[�X3
	//   WikiEngineImpl��������WikiEngine�C���^�t�F�[�X���������Ă��邱��
	//   Input: ?
	//   Output: ?
	//   �R���p�C�����Ɋm�F�ł��邱�Ƃł����ׂ邱�Ƃ͉\
	@Test
	public void testImplements_WikiEngine() {
		assertThat(target, is(instanceOf(WikiEngine.class)));
	}
	
	// �e�X�g�P�[�X4
	//   null����͂���Ɨ�O���������邱��
	//   Input: null
	//   Output: IllegalArgumentException
	@Test(expected = IllegalArgumentException.class)
	public void testToHtml_Null() {
		target.toHtml(null);
	}
	
	// �e�X�g�P�[�X5
	//   ��������u= �v�u =�v�ň͂ނƁA�u<H1>�v�ɕϊ�����
	//   Input: = Heading =
	//   Output: <H1>Heading</H1>
	//
	//   �����񂪁u =�v�ŕ����Ă��Ȃ��ꍇ�͕ϊ�����Ȃ�
	//   Input: = Heading
	//   Output: = Heading
	//
	//   �����񂪁u= �v�ŊJ�n����Ă��Ȃ��ꍇ�͕ϊ�����Ȃ�
	//   Input: Heading =
	//   Output: Heading =
	@Test
	public void testToHtml_Heading() {
		String input = "= Heading =";
		String expected = "<H1>Heading</H1>";
		assertThat(target.toHtml(input), is(expected));
		
		String input2 = "= Heading";
		String expected2 = "= Heading";
		assertThat(target.toHtml(input2), is(expected2));
		
		String input3 = "Heading =";
		String expected3 = "Heading =";
		assertThat(target.toHtml(input3), is(expected3));
	}
	
	// �e�X�g�P�[�X6
	//   ��������u== �v�u ==�v�ň͂ނƁA�u<H2>�v�ɕϊ�����
	//   Input: == Heading2 ==
	//   Output: <H2>Heading2</H2>
	
	//   �����񂪁u ==�v�ŕ����Ă��Ȃ��ꍇ�͕ϊ�����Ȃ�
	//   Input: == Heading2
	//   Output: == Heading2
	//
	//   �����񂪁u= �v�ŊJ�n����Ă��Ȃ��ꍇ�͕ϊ�����Ȃ�
	//   Input: Heading2 ==
	//   Output: Heading2 ==
	//
	//   ������̃w�b�_���x���������Ă��Ȃ��ꍇ�͕ϊ�����Ȃ�
	//   Input: == Heading2 =
	//   Output: == Heading2 =
	@Test
	public void testToHtml_Heading2() {
		String input = "== Heading2 ==";
		String expected = "<H2>Heading2</H2>";
		assertThat(target.toHtml(input), is(expected));
		
		String input2 = "== Heading2";
		String expected2 = "== Heading2";
		assertThat(target.toHtml(input2), is(expected2));
		
		String input3 = "Heading2 ==";
		String expected3 = "Heading2 ==";
		assertThat(target.toHtml(input3), is(expected3));
		
		String input4 = "== Heading2 =";
		String expected4 = "== Heading2 =";
		assertThat(target.toHtml(input4), is(expected4));
	}
	
	// �e�X�g�P�[�X7
	//   ��������u=== �v�u ===�v�ň͂ނƁA�u<H3>�v�ɕϊ�����
	//   Input: === Level3 ===
	//   Output: <H3>Level3</H3>
	@Test
	public void testToHtml_Level3() {
		String input = "=== Level3 ===";
		String expected = "<H3>Level3</H3>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// �e�X�g�P�[�X8
	//   ��������u==== �v�u ====�v�ň͂ނƁA�u<H4>�v�ɕϊ�����
	//   Input: ==== Level4 ====
	//   Output: <H4>Level4</H4>
	@Test
	public void testToHtml_Level4() {
		String input = "==== Level4 ====";
		String expected = "<H4>Level4</H4>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// �e�X�g�P�[�X9
	//   ��������u===== �v�u =====�v�ň͂ނƁA�u<H5>�v�ɕϊ�����
	//   Input: ===== Level5 ======
	//   Output: <H5>Level5</H5>
	@Test
	public void testToHtml_Level5() {
		String input = "===== Level5 =====";
		String expected = "<H5>Level5</H5>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// �e�X�g�P�[�X10
	//   ��������u====== �v�u ======�v�ň͂ނƁA�u<H6>�v�ɕϊ�����
	//   Input: ====== Level6 ======
	//   Output: <H6>Level6</H6>
	@Test
	public void testToHtml_Level6() {
		String input = "====== Level6 ======";
		String expected = "<H6>Level6</H6>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// �e�X�g�P�[�X11
	//   ��������u======= �v�u =======�v�ň͂ނƁA���̂܂�
	//   ��GoogleCode��WikiEngine�̎d�l��<H6>�܂łȂ̂�
	//   Input: ======= Level7 =======
	//   Output: ======= Level7 =======
	@Test
	public void testToHtml_Level7() {
		String input = "======= Level7 =======";
		String expected = "======= Level7 =======";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// �e�X�g�P�[�X12
	//   ��������u_�v�u_�v�ň͂ނƁA�u<i>�v(�C�^���b�N)�ɂ���
	//   Input: _Italic_
	//   Output: <i>Italic</i>
	//
	//   ��������u_�v�ŕ��Ă��Ȃ��ꍇ�A�ϊ����Ȃ�
	//   Input: _Italic
	//   Output: _Italic
	//
	//   �����񂪁u_�v�Ŏn�܂��Ă��Ȃ��ꍇ�A�ϊ����Ȃ�
	//   Input: Italic_
	//   Output: Italic_
	@Test
	public void testToHtml_Italic() {
		String input = "_Italic_";
		String expected = "<i>Italic</i>";
		assertThat(target.toHtml(input), is(expected));
		
		String input2 = "_Italic";
		String expected2 = "_Italic";
		assertThat(target.toHtml(input2), is(expected2));
		
		String input3 = "Italic_";
		String expected3 = "Italic_";
		assertThat(target.toHtml(input3), is(expected3));
	}
	
	// �e�X�g�P�[�X13
	//   ��������u*�v�ň͂ނƁA�u<b>�v(�{�[���h)�ɂ���
	//   Input: *Bold*
	//   Output: <b>Bold</b>
	@Test
	public void testToHtml_Bold() {
		String input = "*Bold*";
		String expected = "<b>Bold</b>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// �e�X�g�P�[�X14
	//   ��������u~~�v�ň͂ނƁA�u<strike>�v(�ł�������)�ɂ���
	//   Input: ~~strikeout~~
	//   Output: <strike>strikeout</strike>
	@Test
	public void testToHtml_StrikeOut() {
		String input = "~~strikeout~~";
		String expected = "<strike>strikeout</strike>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// �e�X�g�P�[�X15
	//   HTML�v�f�͓���q�ɂ��邱�Ƃ��ł���(���̂P)
	//   Input: _*bold* in italics_
	//   Output: <i><b>bold</b> in italics</i>
	@Test
	public void testToHtml_MarkupNest_BoldInItalics() {
		String input = "_*bold* in italics_";
		String expected = "<i><b>bold</b> in italics</i>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// �e�X�g�P�[�X16
	//   HTML�v�f�͓���q�ɂ��邱�Ƃ��ł���(���̂Q)
	//   Input: _~~strikeout~~ in italics_
	//   Output: <i><strike>strikeout</strikeout> in italics</i>
	@Test
	public void testToHtml_MarkupNest_StrikeOutInItalics() {
		String input = "_~~strikeout~~ in italics_";
		String expected = "<i><strike>strikeout</strike> in italics</i>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// �e�X�g�P�[�X17
	//   HTML�v�f�͓���q�ɂ��邱�Ƃ��ł���(����3)
	//   Input: *~~strikeout~~ in bolds*
	//   Output: <b><strike>strikeout</strike> in bolds</b>
	@Test
	public void testToHtml_MarkupNest_StrikeOutInBolds() {
		String input = "*~~strikeout~~ in bolds*";
		String expected = "<b><strike>strikeout</strike> in bolds</b>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// �e�X�g�P�[�X18
	//   ��������u^�v�ň͂ނƏ�t�������ɂ���(<sup>)
	//   Input: ^super^
	//   Output: <sup>super</super>
	@Test
	public void testToHtml_Super() {
		String input = "^super^";
		String expected = "<sup>super</sup>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// �e�X�g�P�[�X19
	//   ��������u,,�v�ň͂ނƉ��t�������ɂ���(<sub>)
	//   Input: ,,sub,,script
	//   Output: <sub>sub</sub>script
	//
	//   �u,�v�̎��͕ϊ����Ȃ�
	//   Input: ,sub,script
	//   Output: ,sub,script
	@Test
	public void testToHtml_SubScript() {
		String input = ",,sub,,script";
		String expected = "<sub>sub</sub>script";
		assertThat(target.toHtml(input), is(expected));
		
		String input2 = ",sub,script";
		String expected2 = ",sub,script";
		assertThat(target.toHtml(input2), is(expected2));
	}
	
	// �e�X�g�P�[�X20
	//   ��������u{{{�v�u}}}�v�������́u`�v�ň͂ނƁACode������ɕϊ�����(<tt>)
	//   Input: {{{Code}}}
	//   Output: <tt>Code</tt>
	//
	//   Input: `Code`
	//   Output: <tt>Code</tt>
	@Test
	public void testToHtml_Code() {
		String input = "{{{Code}}}";
		String expected = "<tt>Code</tt>";
		assertThat(target.toHtml(input), is(expected));
		
		String input2 = "`Code`";
		assertThat(target.toHtml(input2), is(expected));
	}
	
	// �e�X�g�P�[�X21
	//   ���s�������Ă��ϊ����邱�Ƃ��ł���B
	//   Input:
	//     *
	//     bold1
	//     bold2
	//     bold3
	//     *
	//   Output:
	//     <b>
	//     bold1
	//     bold2
	//     bold3
	//     </b>
	@Test
	public void testToHtml_MultiLine() {
		String input = "*\nbold1\nbold2\nbold3\n*";
		String expected = "<b>\nbold1\nbold2\nbold3\n</b>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// �e�X�g�P�[�X22
	//   �P���ڂ̉ӏ��������X�g��ϊ�����
	//   Input:  *input
	//   Output:<ul><li>input</ul></ul>
	@Test
	public void testToHtml_SingleUnorderedList() {
		String input = " *input";
		String expected = "<ul><li>input</li></ul>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// �e�X�g�P�[�X23
	//   �ӏ��������X�g��ϊ�����
	//   Input:
	//      *input1
	//      *input2
	//   Output:<ul><li>input1</li><li>input2</li></ul>
	@Test
	public void testToHtml_MultiUnorderedList() {
		String input = " *input1\n *input2";
		String expected = "<ul><li>input1</li><li>input2</li></ul>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// �e�X�g�P�[�X24
	//   �ӏ��������X�g��ϊ�����
	//   Input:
	//     *input1
	//     *input2
	//     *input3
	//   Output:<ul><li>input1</li><li>input2</li><li>input3</li></ul>
	@Test
	public void testToHtml_MultiUnorderedList_2() {
		String input = " *input1\n *input2\n *input3";
		String expected = "<ul><li>input1</li><li>input2</li><li>input3</li></ul>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// �e�X�g�P�[�X25
	//   �ӏ��������X�g(�l�X�g����)��ϊ�����
	//   Input:
	//     *input1
	//     *input2
	//       *input2-1
	//   Output:<ul><li>input1</li><li>input2</li><ul><li>input2-1</li></ul></ul>
	@Test
	public void testToHtml_MultiUnorderedList_WithNest() {
		String input = " *input1\n *input2\n   *input2-1";
		String expected = "<ul><li>input1</li><li>input2</li><ul><li>input2-1</li></ul></ul>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// �e�X�g�P�[�X26
	//   �ӏ��������X�g(�l�X�g����)��ϊ�����
	//   Input:
	//     *input1
	//       *input1-1
	//       *input1-2
	//     *input2
	//   Output:<ul><li>input1</li><ul><li>input1-1</li><li>input1-2</li></ul><li>input2</li></ul>
	@Test
	public void testToHtml_MultiUnorderedList_WithNest_2() {
		String input = " *input1\n   *input1-1\n   *input1-2\n *input2";
		String expected = "<ul><li>input1</li><ul><li>input1-1</li><li>input1-2</li></ul><li>input2</li></ul>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// �e�X�g�P�[�X27
	//   �ӏ��������X�g(�l�X�g����)��ϊ�����
	//   Input:
	//     *input1
	//       *input1-1
	//       *input1-2
	//        *input1-2-1
	//     *input2
	//      *input2-1
	//   Output:<ul><li>input1</li><ul><li>input1-1</li><li>input1-2</li><ul><li>input1-2-1</li></ul></ul><li>input2</li><ul><li>input2-1</li></ul></ul>
	@Test
	public void testToHtml_MultiUnorderedList_WithMultiNest() {
		String input = " *input1\n   *input1-1\n   *input1-2\n    *input1-2-1\n *input2\n  *input2-1";
		String expected = "<ul><li>input1</li><ul><li>input1-1</li><li>input1-2</li><ul><li>input1-2-1</li></ul></ul><li>input2</li><ul><li>input2-1</li></ul></ul>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// �e�X�g�P�[�X28
	//   �P���ڂ̃i���o�����O���X�g��ϊ�����
	//   Input:  #input1
	//   Output: <ol><li>input1</li></ol>
	@Test
	public void testToHtml_SingleOrderedList() {
		String input = " #input1";
		String expected = "<ol><li>input1</li></ol>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// �e�X�g�P�[�X29
	//   �i���o�����O���X�g��ϊ�����
	//   Input:
	//    #input1
	//    #input2
	//   Output:<ol><li>input1</li><li>input2</li></ol>
	@Test
	public void testToHtml_MultiOrderedList() {
		String input = " #input1\n #input2";
		String expected = "<ol><li>input1</li><li>input2</li></ol>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// �e�X�g�P�[�X30
	//   �i���o�����O���X�g��ϊ�����
	//   Input:
	//     #input1
	//     #input2
	//     #input3
	//   Output:<ol><li>input1</li><li>input2</li><li>input3</li></ol>
	@Test
	public void testToHtml_MultiOrderedList2() {
		String input = " #input1\n #input2\n #input3";
		String expected = "<ol><li>input1</li><li>input2</li><li>input3</li></ol>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// �e�X�g�P�[�X31
	//   �A�����Ă��Ȃ����X�g�̃p�^�[���͕ʂ̃��X�g�ɕϊ�����(�ӏ��������X�g)
	//   Input:
	//     *input1-1
	//     *input1-2
	//
	//     *input2-1
	//     *input2-2
	//   Output:
	//   <ul><li>input1-1</li><li>input1-2</li></ul>
	//   <ul><li>input2-1</li><li>input2-2</li></ul>
	@Test
	public void testToHtml_Unordered2Lists() {
		String input = " *input1-1\n *input1-2\n\n *input2-1\n *input2-2";
		String expected = "<ul><li>input1-1</li><li>input1-2</li></ul>\n<ul><li>input2-1</li><li>input2-2</li></ul>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// �e�X�g�P�[�X32
	//   �A�����Ă��Ȃ����X�g�̃p�^�[���͕ʂ̃��X�g�ɕϊ�����(�i���o�����O���X�g)
	//   Input:
	//     #input1-1
	//     #input1-2
	//
	//     #input2-1
	//     #input2-2
	//   Output:
	//   <ol><li>input1-1</li><li>input1-2</li></ol>
	//   <ol><li>input2-1</li><li>input2-2</li></ol>
	@Test
	public void testToHtml_Ordered2Lists() {
		String input = " #input1-1\n #input1-2\n\n #input2-1\n #input2-2";
		String expected = "<ol><li>input1-1</li><li>input1-2</li></ol>\n<ol><li>input2-1</li><li>input2-2</li></ol>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// �e�X�g�P�[�X33
	//   �ӏ��������X�g�ƃi���o�����O���X�g��ϊ�����
	//   Input:
	//     *input1-1
	//     *input1-2
	//
	//     #input2-1
	//     #input2-2
	//   Output:
	//   <ul><li>input1-1</li><li>input1-2</li></ul>
	//   <ol><li>input2-1</li><li>input2-2</li></ol>
	@Test
	public void testToHtml_UnorderedListAndOrderedList() {
		String input = " *input1-1\n *input1-2\n\n #input2-1\n #input2-2";
		String expected = "<ul><li>input1-1</li><li>input1-2</li></ul>\n<ol><li>input2-1</li><li>input2-2</li></ol>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// �e�X�g�P�[�X34
	//   �i���o�����O���X�g�Ɖӏ��������X�g��ϊ�����
	//   Input:
	//     #input1-1
	//     #input1-2
	//
	//     *input2-1
	//     *input2-2
	//   Output:
	//   <ol><li>input1-1</li><li>input1-2</li></ol>
	//   <ul><li>input2-1</li><li>input2-2</li></ul>
	@Test
	public void testToHtml_OrderedListAndUnorderedList() {
		String input = " #input1-1\n #input1-2\n\n *input2-1\n *input2-2";
		String expected = "<ol><li>input1-1</li><li>input1-2</li></ol>\n<ul><li>input2-1</li><li>input2-2</li></ul>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// �e�X�g�P�[�X35
	//   �ӏ��������X�g�̒��Ƀi���o�����O���X�g���l�X�g����
	//   Input:
	//     *input1
	//     *input2
	//       #input2-1
	//       #input2-2
	//     *input3
	//   Output:<ul><li>input1</li><li>input2</li><ol><li>input2-1</li><li>input2-2</li></ol><li>input3</li></ul>
	@Test
	public void testToHtml_UnorderedListWithNestedOrderedList() {
		String input = " *input1\n *input2\n   #input2-1\n   #input2-2\n *input3";
		String expected = "<ul><li>input1</li><li>input2</li><ol><li>input2-1</li><li>input2-2</li></ol><li>input3</li></ul>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// �e�X�g�P�[�X36
	//   �i���o�����O���X�g�̒��ɉӏ��������X�g���l�X�g����
	//   Input:
	//     #input1
	//     #input2
	//       *input2-1
	//       *input2-2
	//       *input2-3
	//   Output:<ol><li>input1</li><li>input2</li><ul><li>input2-1</li><li>input2-2</li><li>input2-3</li></ul></ol>
	@Test
	public void testToHtml_OrderedListWithNestedUnorderedList() {
		String input = " #input1\n #input2\n   *input2-1\n   *input2-2\n   *input2-3";
		String expected = "<ol><li>input1</li><li>input2</li><ul><li>input2-1</li><li>input2-2</li><li>input2-3</li></ul></ol>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// �e�X�g�P�[�X37
	//   �P�s��Quoting
	//   Input:  inputQuote
	//   Output:<blockquote>inputQuote</blockquote>
	@Test
	public void testToHtml_QuotingALine() {
		String input = " inputQuote";
		String expected = "<blockquote>inputQuote</blockquote>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// �e�X�g�P�[�X38
	//   2�s��Quoting
	//   Input:
	//     inputQuoteLine1
	//     inputQuoteLine2
	//   Output:
	//   <blockquote>inputQuoteLine1
	//   inputQuoteLine2</blockquote>
	@Test
	public void testToHtml_QuotingTwoLines() {
		String input = " inputQuoteLine1\n inputQuoteLine2";
		String expected = "<blockquote>inputQuoteLine1\ninputQuoteLine2</blockquote>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// �e�X�g�P�[�X39
	//   ������Quoting�u���b�N
	//   Input:
	//     inputQuoteLine1
	//     inputQuoteLine2
	//
	//     inputQuoteLine3
	//     inputQuoteLine4
	//   Output:
	//     <blockquote>inputQuoteLine1
	//     inputQuoteLine2
	//     </blockquote>
	//     <blockquote>inputQuoteLine3
	//     inputQuoteLine4</blockquote>
	@Test
	public void testToHtml_QuoteMultiBlock() {
		String input = " inputQuoteLine1\n inputQuoteLine2\n\n inputQuoteLine3\n inputQuoteLine4";
		String expected = "<blockquote>inputQuoteLine1\ninputQuoteLine2\n</blockquote>\n<blockquote>inputQuoteLine3\ninputQuoteLine4</blockquote>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	//	Tables
	//
	//	Tables are created by entering the content of each cell separated by || delimiters.
	//  You can insert other inline wiki syntax in table cells, including typeface formatting and links.
	//
	//	|| *Year* || *Temperature (low)* || *Temperature (high)* ||
	//	|| 1900 || -10 || 25 ||
	//	|| 1910 || -15 || 30 ||
	//	|| 1920 || -10 || 32 ||
	//	|| 1930 || _N/A_ || _N/A_ ||
	//	|| 1940 || -2 || 40 ||
	
	// �e�X�g�P�[�X40
	//   1�s1��̃e�[�u����ϊ�
	//   Input: || input1 ||
	//   Output: <table><tr><td>input1</td></tr></table>
	@Test
	public void testToHtml_TableSingleRowAndSingleCol()
	{
		String input = "|| input1 ||";
		String expected = "<table><tr><td>input1</td></tr></table>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// �e�X�g�P�[�X41
	//  2�s1��̃e�[�u����ϊ�
	//  Input:
	//     || input1 ||
	//     || input2 ||
	//  Output: <table><tr><td>input1</td></tr><tr><td>input2</td></tr></table>
	@Test
	public void testToHtml_Table2RowsAndSingleCol(){
		String input = "|| input1 ||\n|| input2 ||";
		String expected = "<table><tr><td>input1</td></tr><tr><td>input2</td></tr></table>";
		assertThat(target.toHtml(input), is(expected));
	}
}
