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

	// テストケース1
	//   入力されたテキストがそのまま返されること
	//   Input: Hello World
	//   Output: Hello World
	@Test
	public void testToHtml_HelloWorld() {
		String input = "Hello World";
		String expected = "Hello World";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// テストケース2
	//   入力されたテキストがそのまま返されること
	//   Input: TDDBootCamp
	//   Output: TDDBootCamp
	//   ※Triangulation
	@Test
	public void testToHtml_TDDBootCamp() {
		String input = "TDDBootCamp";
		String expected = "TDDBootCamp";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// テストケース3
	//   WikiEngineImplが正しくWikiEngineインタフェースを実装していること
	//   Input: ?
	//   Output: ?
	//   コンパイル時に確認できることでも調べることは可能
	@Test
	public void testImplements_WikiEngine() {
		assertThat(target, is(instanceOf(WikiEngine.class)));
	}
	
	// テストケース4
	//   nullを入力すると例外が発生すること
	//   Input: null
	//   Output: IllegalArgumentException
	@Test(expected = IllegalArgumentException.class)
	public void testToHtml_Null() {
		target.toHtml(null);
	}
	
	// テストケース5
	//   文字列を「= 」「 =」で囲むと、「<H1>」に変換する
	//   Input: = Heading =
	//   Output: <H1>Heading</H1>
	//
	//   文字列が「 =」で閉じられていない場合は変換されない
	//   Input: = Heading
	//   Output: = Heading
	//
	//   文字列が「= 」で開始されていない場合は変換されない
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
	
	// テストケース6
	//   文字列を「== 」「 ==」で囲むと、「<H2>」に変換する
	//   Input: == Heading2 ==
	//   Output: <H2>Heading2</H2>
	
	//   文字列が「 ==」で閉じられていない場合は変換されない
	//   Input: == Heading2
	//   Output: == Heading2
	//
	//   文字列が「= 」で開始されていない場合は変換されない
	//   Input: Heading2 ==
	//   Output: Heading2 ==
	//
	//   文字列のヘッダレベルがあっていない場合は変換されない
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
	
	// テストケース7
	//   文字列を「=== 」「 ===」で囲むと、「<H3>」に変換する
	//   Input: === Level3 ===
	//   Output: <H3>Level3</H3>
	@Test
	public void testToHtml_Level3() {
		String input = "=== Level3 ===";
		String expected = "<H3>Level3</H3>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// テストケース8
	//   文字列を「==== 」「 ====」で囲むと、「<H4>」に変換する
	//   Input: ==== Level4 ====
	//   Output: <H4>Level4</H4>
	@Test
	public void testToHtml_Level4() {
		String input = "==== Level4 ====";
		String expected = "<H4>Level4</H4>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// テストケース9
	//   文字列を「===== 」「 =====」で囲むと、「<H5>」に変換する
	//   Input: ===== Level5 ======
	//   Output: <H5>Level5</H5>
	@Test
	public void testToHtml_Level5() {
		String input = "===== Level5 =====";
		String expected = "<H5>Level5</H5>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// テストケース10
	//   文字列を「====== 」「 ======」で囲むと、「<H6>」に変換する
	//   Input: ====== Level6 ======
	//   Output: <H6>Level6</H6>
	@Test
	public void testToHtml_Level6() {
		String input = "====== Level6 ======";
		String expected = "<H6>Level6</H6>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// テストケース11
	//   文字列を「======= 」「 =======」で囲むと、そのまま
	//   ※GoogleCodeのWikiEngineの仕様が<H6>までなので
	//   Input: ======= Level7 =======
	//   Output: ======= Level7 =======
	@Test
	public void testToHtml_Level7() {
		String input = "======= Level7 =======";
		String expected = "======= Level7 =======";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// テストケース12
	//   文字列を「_」「_」で囲むと、「<i>」(イタリック)にする
	//   Input: _Italic_
	//   Output: <i>Italic</i>
	//
	//   文字列を「_」で閉じていない場合、変換しない
	//   Input: _Italic
	//   Output: _Italic
	//
	//   文字列が「_」で始まっていない場合、変換しない
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
	
	// テストケース13
	//   文字列を「*」で囲むと、「<b>」(ボールド)にする
	//   Input: *Bold*
	//   Output: <b>Bold</b>
	@Test
	public void testToHtml_Bold() {
		String input = "*Bold*";
		String expected = "<b>Bold</b>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// テストケース14
	//   文字列を「~~」で囲むと、「<strike>」(打ち消し線)にする
	//   Input: ~~strikeout~~
	//   Output: <strike>strikeout</strike>
	@Test
	public void testToHtml_StrikeOut() {
		String input = "~~strikeout~~";
		String expected = "<strike>strikeout</strike>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// テストケース15
	//   HTML要素は入れ子にすることができる(その１)
	//   Input: _*bold* in italics_
	//   Output: <i><b>bold</b> in italics</i>
	@Test
	public void testToHtml_MarkupNest_BoldInItalics() {
		String input = "_*bold* in italics_";
		String expected = "<i><b>bold</b> in italics</i>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// テストケース16
	//   HTML要素は入れ子にすることができる(その２)
	//   Input: _~~strikeout~~ in italics_
	//   Output: <i><strike>strikeout</strikeout> in italics</i>
	@Test
	public void testToHtml_MarkupNest_StrikeOutInItalics() {
		String input = "_~~strikeout~~ in italics_";
		String expected = "<i><strike>strikeout</strike> in italics</i>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// テストケース17
	//   HTML要素は入れ子にすることができる(その3)
	//   Input: *~~strikeout~~ in bolds*
	//   Output: <b><strike>strikeout</strike> in bolds</b>
	@Test
	public void testToHtml_MarkupNest_StrikeOutInBolds() {
		String input = "*~~strikeout~~ in bolds*";
		String expected = "<b><strike>strikeout</strike> in bolds</b>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// テストケース18
	//   文字列を「^」で囲むと上付き文字にする(<sup>)
	//   Input: ^super^
	//   Output: <sup>super</super>
	@Test
	public void testToHtml_Super() {
		String input = "^super^";
		String expected = "<sup>super</sup>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// テストケース19
	//   文字列を「,,」で囲むと下付き文字にする(<sub>)
	//   Input: ,,sub,,script
	//   Output: <sub>sub</sub>script
	//
	//   「,」の時は変換しない
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
	
	// テストケース20
	//   文字列を「{{{」「}}}」もしくは「`」で囲むと、Code文字列に変換する(<tt>)
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
	
	// テストケース21
	//   改行があっても変換することができる。
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
	
	// テストケース22
	//   単項目の箇条書きリストを変換する
	//   Input:  *input
	//   Output:<ul><li>input</ul></ul>
	@Test
	public void testToHtml_SingleUnorderedList() {
		String input = " *input";
		String expected = "<ul><li>input</li></ul>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// テストケース23
	//   箇条書きリストを変換する
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
	
	// テストケース24
	//   箇条書きリストを変換する
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
	
	// テストケース25
	//   箇条書きリスト(ネストあり)を変換する
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
	
	// テストケース26
	//   箇条書きリスト(ネストあり)を変換する
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
	
	// テストケース27
	//   箇条書きリスト(ネスト複数)を変換する
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
	
	// テストケース28
	//   単項目のナンバリングリストを変換する
	//   Input:  #input1
	//   Output: <ol><li>input1</li></ol>
	@Test
	public void testToHtml_SingleOrderedList() {
		String input = " #input1";
		String expected = "<ol><li>input1</li></ol>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// テストケース29
	//   ナンバリングリストを変換する
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
	
	// テストケース30
	//   ナンバリングリストを変換する
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
	
	// テストケース31
	//   連続していないリストのパターンは別のリストに変換する(箇条書きリスト)
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
	
	// テストケース32
	//   連続していないリストのパターンは別のリストに変換する(ナンバリングリスト)
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
	
	// テストケース33
	//   箇条書きリストとナンバリングリストを変換する
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
	
	// テストケース34
	//   ナンバリングリストと箇条書きリストを変換する
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
	
	// テストケース35
	//   箇条書きリストの中にナンバリングリストをネストする
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
	
	// テストケース36
	//   ナンバリングリストの中に箇条書きリストをネストする
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
	
	// テストケース37
	//   １行のQuoting
	//   Input:  inputQuote
	//   Output:<blockquote>inputQuote</blockquote>
	@Test
	public void testToHtml_QuotingALine() {
		String input = " inputQuote";
		String expected = "<blockquote>inputQuote</blockquote>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// テストケース38
	//   2行のQuoting
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
	
	// テストケース39
	//   複数のQuotingブロック
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
	
	// テストケース40
	//   1行1列のテーブルを変換
	//   Input: || input1 ||
	//   Output: <table><tr><td>input1</td></tr></table>
	@Test
	public void testToHtml_TableSingleRowAndSingleCol()
	{
		String input = "|| input1 ||";
		String expected = "<table><tr><td>input1</td></tr></table>";
		assertThat(target.toHtml(input), is(expected));
	}
	
	// テストケース41
	//  2行1列のテーブルを変換
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
