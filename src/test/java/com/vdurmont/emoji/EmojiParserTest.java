package com.vdurmont.emoji;

import com.vdurmont.emoji.EmojiParser.AliasCandidate;
import com.vdurmont.emoji.EmojiParser.FitzpatrickAction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class EmojiParserTest {
  @Test
  public void parseToAliases_replaces_the_emojis_by_one_of_their_aliases() {
    // GIVEN
    String str = "An 😀awesome 😃string with a few 😉emojis!";

    // WHEN
    String result = EmojiParser.parseToAliases(str);

    // THEN
    assertEquals(
      "An :grinning:awesome :smiley:string with a few :wink:emojis!",
      result
    );
  }

  @Test
  public void replaceAllEmojis_replace_the_emojis_by_string() throws Exception {
    // GIVEN
    String str = "An 😀awesome 😃string with a few 😉emojis!";

    // WHEN
    String result = EmojiParser.replaceAllEmojis(str, ":)");

    // THEN
    assertEquals(
      "An :)awesome :)string with a few :)emojis!",
      result
    );
  }


  @Test
  public void parseToAliases_REPLACE_with_a_fitzpatrick_modifier() {
    // GIVEN
    String str = "\uD83D\uDC66\uD83C\uDFFF";

    // WHEN
    String result = EmojiParser.parseToAliases(str);

    // THEN
    assertEquals(":boy|type_6:", result);
  }

  @Test
  public void parseToAliases_REMOVE_with_a_fitzpatrick_modifier() {
    // GIVEN
    String str = "\uD83D\uDC66\uD83C\uDFFF";

    // WHEN
    String result = EmojiParser.parseToAliases(str, FitzpatrickAction.REMOVE);

    // THEN
    assertEquals(":boy:", result);
  }

  @Test
  public void parseToAliases_REMOVE_without_a_fitzpatrick_modifier() {
    // GIVEN
    String str = "\uD83D\uDC66";

    // WHEN
    String result = EmojiParser.parseToAliases(str, FitzpatrickAction.REMOVE);

    // THEN
    assertEquals(":boy:", result);
  }

  @Test
  public void parseToAliases_IGNORE_with_a_fitzpatrick_modifier() {
    // GIVEN
    String str = "\uD83D\uDC66\uD83C\uDFFF";

    // WHEN
    String result = EmojiParser.parseToAliases(str, FitzpatrickAction.IGNORE);

    // THEN
    assertEquals(":boy:\uD83C\uDFFF", result);
  }

  @Test
  public void parseToAliases_IGNORE_without_a_fitzpatrick_modifier() {
    // GIVEN
    String str = "\uD83D\uDC66";

    // WHEN
    String result = EmojiParser.parseToAliases(str, FitzpatrickAction.IGNORE);

    // THEN
    assertEquals(":boy:", result);
  }

  @Test
  public void parseToAliases_with_long_overlapping_emoji() {
    // GIVEN
    String str = "\uD83D\uDC68\u200D\uD83D\uDC69\u200D\uD83D\uDC66";

    // WHEN
    String result = EmojiParser.parseToAliases(str);

    //With greedy parsing, this will give :man::woman::boy:
    //THEN
    assertEquals(":family_man_woman_boy:", result);
  }

  @Test
  public void parseToAliases_continuous_non_overlapping_emojis() {
    // GIVEN
    String str = "\uD83D\uDC69\uD83D\uDC68\uD83D\uDC66";

    // WHEN
    String result = EmojiParser.parseToAliases(str);

    //THEN
    assertEquals(":woman::man::boy:", result);
  }

  @Test
  public void parseToHtmlDecimal_replaces_the_emojis_by_their_html_decimal_representation() {
    // GIVEN
    String str = "An 😀awesome 😃string with a few 😉emojis!";

    // WHEN
    String result = EmojiParser.parseToHtmlDecimal(str);

    // THEN
    assertEquals(
      "An &#128512;awesome &#128515;string with a few &#128521;emojis!",
      result
    );
  }

  @Test
  public void parseToHtmlDecimal_PARSE_with_a_fitzpatrick_modifier() {
    // GIVEN
    String str = "\uD83D\uDC66\uD83C\uDFFF";

    // WHEN
    String result = EmojiParser.parseToHtmlDecimal(
      str,
      FitzpatrickAction.PARSE
    );

    // THEN
    assertEquals("&#128102;", result);
  }

  @Test
  public void parseToHtmlDecimal_REMOVE_with_a_fitzpatrick_modifier() {
    // GIVEN
    String str = "\uD83D\uDC66\uD83C\uDFFF";

    // WHEN
    String result = EmojiParser.parseToHtmlDecimal(
      str,
      FitzpatrickAction.REMOVE
    );

    // THEN
    assertEquals("&#128102;", result);
  }

  @Test
  public void parseToHtmlDecimal_IGNORE_with_a_fitzpatrick_modifier() {
    // GIVEN
    String str = "\uD83D\uDC66\uD83C\uDFFF";

    // WHEN
    String result = EmojiParser.parseToHtmlDecimal(
      str,
      FitzpatrickAction.IGNORE
    );

    // THEN
    assertEquals("&#128102;\uD83C\uDFFF", result);
  }

  @Test
  public void parseToHtmlHexadecimal_replaces_the_emojis_by_their_htm_hex_representation() {
    // GIVEN
    String str = "An 😀awesome 😃string with a few 😉emojis!";

    // WHEN
    String result = EmojiParser.parseToHtmlHexadecimal(str);

    // THEN
    assertEquals(
      "An &#x1f600;awesome &#x1f603;string with a few &#x1f609;emojis!",
      result
    );
  }

  @Test
  public void parseToHtmlHexadecimal_PARSE_with_a_fitzpatrick_modifier() {
    // GIVEN
    String str = "\uD83D\uDC66\uD83C\uDFFF";

    // WHEN
    String result = EmojiParser.parseToHtmlHexadecimal(
      str,
      FitzpatrickAction.PARSE
    );

    // THEN
    assertEquals("&#x1f466;", result);
  }

  @Test
  public void parseToHtmlHexadecimal_REMOVE_with_a_fitzpatrick_modifier() {
    // GIVEN
    String str = "\uD83D\uDC66\uD83C\uDFFF";

    // WHEN
    String result = EmojiParser.parseToHtmlHexadecimal(
      str,
      FitzpatrickAction.REMOVE
    );

    // THEN
    assertEquals("&#x1f466;", result);
  }

  @Test
  public void parseToHtmlHexadecimal_IGNORE_with_a_fitzpatrick_modifier() {
    // GIVEN
    String str = "\uD83D\uDC66\uD83C\uDFFF";

    // WHEN
    String result = EmojiParser.parseToHtmlHexadecimal(
      str,
      FitzpatrickAction.IGNORE
    );

    // THEN
    assertEquals("&#x1f466;\uD83C\uDFFF", result);
  }

  @Test
  public void parseToUnicode_replaces_the_aliases_and_the_html_by_their_emoji() {
    // GIVEN
    String str = "An :grinning:awesome :smiley:string " +
      "&#128516;with a few &#x1f609;emojis!";

    // WHEN
    String result = EmojiParser.parseToUnicode(str);

    // THEN
    assertEquals("An 😀awesome 😃string 😄with a few 😉emojis!", result);
  }

  @Test
  public void parseToUnicode_with_the_thumbsup_emoji_replaces_the_alias_by_the_emoji() {
    // GIVEN
    String str = "An :+1:awesome :smiley:string " +
      "&#128516;with a few :wink:emojis!";

    // WHEN
    String result = EmojiParser.parseToUnicode(str);

    // THEN
    assertEquals(
      "An \uD83D\uDC4Dawesome 😃string 😄with a few 😉emojis!",
      result
    );
  }

  @Test
  public void parseToUnicode_with_the_thumbsdown_emoji_replaces_the_alias_by_the_emoji() {
    // GIVEN
    String str = "An :-1:awesome :smiley:string &#128516;" +
      "with a few :wink:emojis!";

    // WHEN
    String result = EmojiParser.parseToUnicode(str);

    // THEN
    assertEquals(
      "An \uD83D\uDC4Eawesome 😃string 😄with a few 😉emojis!",
      result
    );
  }

  @Test
  public void parseToUnicode_with_the_thumbsup_emoji_in_hex_replaces_the_alias_by_the_emoji() {
    // GIVEN
    String str = "An :+1:awesome :smiley:string &#x1f604;" +
      "with a few :wink:emojis!";

    // WHEN
    String result = EmojiParser.parseToUnicode(str);

    // THEN
    assertEquals(
      "An \uD83D\uDC4Dawesome 😃string 😄with a few 😉emojis!",
      result
    );
  }

  @Test
  public void parseToUnicode_with_the_multiple_emojis() {
    // GIVEN
    String str = "emoji here ::woman: :man: :boy: &#x1f604;" +
            "and there:thumbsup::skin-tone-1::boy::man::boy::skin-tone-6:" +
            "and there:thumbsup::skin-tone-1::::boy::man::boy:::::bicyclist:" +
            "and more::boy::skin-tone-6::man:lorem: :boy::skin-tone-6::girl:" +
            "and more::boy::skin-tone-6::thumbsup::skin-tone-5::boy::skin-tone-6::girl:" +
            "and only 1 char emoji :::::v::skin-tone-3:";

    // WHEN
    String result = EmojiParser.parseToUnicode(str);

    //THEN
//    assertEquals("\uD83D\uDC69 \uD83D\uDC68 \uD83D\uDC66", result);
    assertEquals("emoji here :\uD83D\uDC69 \uD83D\uDC68 \uD83D\uDC66 \uD83D\uDE04and there\uD83D\uDC4D\uD83C\uDFFB\uD83D\uDC66\uD83D\uDC68\uD83D\uDC66\uD83C\uDFFFand there\uD83D\uDC4D\uD83C\uDFFB::\uD83D\uDC66\uD83D\uDC68\uD83D\uDC66:::\uD83D\uDEB4and more:\uD83D\uDC66\uD83C\uDFFF\uD83D\uDC68lorem: \uD83D\uDC66\uD83C\uDFFF\uD83D\uDC67and more:\uD83D\uDC66\uD83C\uDFFF\uD83D\uDC4D\uD83C\uDFFE\uD83D\uDC66\uD83C\uDFFF\uD83D\uDC67and only 1 char emoji ::::✌\uD83C\uDFFC", result);
  }

  @Test
  public void parseToUnicode_with_a_fitzpatrick_modifier() {
    // GIVEN
    String str = ":boy|type_6:";

    // WHEN
    String result = EmojiParser.parseToUnicode(str);

    // THEN
    assertEquals("\uD83D\uDC66\uD83C\uDFFF", result);
  }

  @Test
  public void parseToUnicode_with_a_fitzpatrick_modifier_suffix_format() {
    // GIVEN
    String str = "Short code skin tone :boy::skin-tone-1: :boy::skin-tone-3: :boy::skin-tone-4: :boy::skin-tone-5: :boy::skin-tone-6:";

    // WHEN
    String result = EmojiParser.parseToUnicode(str);

    // THEN
    assertEquals("Short code skin tone \uD83D\uDC66\uD83C\uDFFB \uD83D\uDC66\uD83C\uDFFC \uD83D\uDC66\uD83C\uDFFD \uD83D\uDC66\uD83C\uDFFE \uD83D\uDC66\uD83C\uDFFF", result);
  }

  @Test
  public void parseToUnicode_with_an_unsupported_fitzpatrick_modifier_doesnt_replace() {
    // GIVEN
    String str = ":grinning|type_6:";
    // WHEN
    String result = EmojiParser.parseToUnicode(str);

    // THEN
    assertEquals(str, result);
  }

  @Test
  public void getAliasAt_with_one_alias() {
    // GIVEN
    String str = "test :boy: test";

    // WHEN
    AliasCandidate candidate = EmojiParser.getAliasAt(str, 5);

    // THEN
    assertTrue(candidate.emoji.getAliases().contains("boy"));
    assertNull(candidate.fitzpatrick);
  }

  @Test
  public void getAliasAt_with_one_alias_an_another_colon_after() {
    // GIVEN
    String str = "test :boy: test:";

    // WHEN
    AliasCandidate candidate = EmojiParser.getAliasAt(str, 5);

    // THEN
    assertTrue(candidate.emoji.getAliases().contains("boy"));
    assertNull(candidate.fitzpatrick);
  }

  @Test
  public void getAliasAt_with_one_alias_an_another_colon_right_after() {
    // GIVEN
    String str = "test :boy::test";

    // WHEN
    AliasCandidate candidate = EmojiParser.getAliasAt(str, 5);

    // THEN
    assertTrue(candidate.emoji.getAliases().contains("boy"));
    assertNull(candidate.fitzpatrick);
  }

  @Test
  public void getAliasAt_with_one_alias_an_another_colon_before_after() {
    // GIVEN
    String str = "test ::boy: test";

    // WHEN
    AliasCandidate candidate = EmojiParser.getAliasAt(str, 5);
    assertNull(candidate);

    candidate = EmojiParser.getAliasAt(str, 6);

    // THEN
    assertTrue(candidate.emoji.getAliases().contains("boy"));
    assertNull(candidate.fitzpatrick);
  }

  @Test
  public void getAliasAt_with_a_fitzpatrick_modifier() {
    // GIVEN
    String str = "test :boy|type_3: test";

    // WHEN
    AliasCandidate candidate = EmojiParser.getAliasAt(str, 5);

    // THEN
    assertTrue(candidate.emoji.getAliases().contains("boy"));
    assertEquals(Fitzpatrick.TYPE_3, candidate.fitzpatrick);
  }

  @Test
  public void test_with_a_new_flag() {
    String input = "Cuba has a new flag! :cu:";
    String expected = "Cuba has a new flag! \uD83C\uDDE8\uD83C\uDDFA";

    assertEquals(expected, EmojiParser.parseToUnicode(input));
    assertEquals(input, EmojiParser.parseToAliases(expected));
  }

  @Test
  public void removeAllEmojis_removes_all_the_emojis_from_the_string() {
    // GIVEN
    String input = "An 😀awesome 😃string 😄with " +
      "a \uD83D\uDC66\uD83C\uDFFFfew 😉emojis!";

    // WHEN
    String result = EmojiParser.removeAllEmojis(input);

    // THEN
    String expected = "An awesome string with a few emojis!";
    assertEquals(expected, result);
  }

  @Test
  public void removeEmojis_only_removes_the_emojis_in_the_iterable_from_the_string() {
    // GIVEN
    String input = "An\uD83D\uDE03 awesome\uD83D\uDE04 string" +
      "\uD83D\uDC4D\uD83C\uDFFF with\uD83D\uDCAA\uD83C\uDFFD a few emojis!";

    List<Emoji> emojis = new ArrayList<Emoji>();
    emojis.add(EmojiManager.getForAlias("smile"));
    emojis.add(EmojiManager.getForAlias("+1"));

    // WHEN
    String result = EmojiParser.removeEmojis(input, emojis);

    // THEN
    String expected = "An\uD83D\uDE03 awesome string with" +
      "\uD83D\uDCAA\uD83C\uDFFD a few emojis!";
    assertEquals(expected, result);
  }

  @Test
  public void removeAllEmojisExcept_removes_all_the_emojis_from_the_string_except_those_in_the_iterable() {
    // GIVEN
    String input = "An\uD83D\uDE03 awesome\uD83D\uDE04 string" +
      "\uD83D\uDC4D\uD83C\uDFFF with\uD83D\uDCAA\uD83C\uDFFD a few emojis!";

    List<Emoji> emojis = new ArrayList<Emoji>();
    emojis.add(EmojiManager.getForAlias("smile"));
    emojis.add(EmojiManager.getForAlias("+1"));

    // WHEN
    String result = EmojiParser.removeAllEmojisExcept(input, emojis);

    // THEN
    String expected = "An awesome\uD83D\uDE04 string\uD83D\uDC4D\uD83C\uDFFF " +
      "with a few emojis!";
    assertEquals(expected, result);
  }

  @Test
  public void parseToUnicode_with_the_keycap_asterisk_emoji_replaces_the_alias_by_the_emoji() {
    // GIVEN
    String str = "Let's test the :keycap_asterisk: emoji and " +
      "its other alias :star_keycap:";

    // WHEN
    String result = EmojiParser.parseToUnicode(str);

    // THEN
    assertEquals("Let's test the *⃣ emoji and its other alias *⃣", result);
  }

  @Test
  public void parseToAliases_NG_and_nigeria() {
    // GIVEN
    String str = "Nigeria is 🇳🇬, NG is 🆖";

    // WHEN
    String result = EmojiParser.parseToAliases(str);

    // THEN
    assertEquals("Nigeria is :ng:, NG is :squared_ng:", result);
  }

  @Test
  public void parseToAliases_couplekiss_woman_woman() {
    // GIVEN
    String str = "👩‍❤️‍💋‍👩";

    // WHEN
    String result = EmojiParser.parseToAliases(str);

    // THEN
    assertEquals(":couplekiss_woman_woman:", result);
  }

  @Test
  public void extractEmojis() {
    // GIVEN
    String str = "An 😀awesome 😃string with a few 😉emojis!";

    // WHEN
    List<String> result = EmojiParser.extractEmojis(str);

    // THEN
    assertEquals("😀", result.get(0));
    assertEquals("😃", result.get(1));
    assertEquals("😉", result.get(2));

  }

  @Test
  public void extractEmojis_withFitzpatrickModifiers() {
    // GIVEN
    final String surfer = EmojiManager.getForAlias("surfer").getUnicode();
    final String surfer3 = EmojiManager.getForAlias("surfer").getUnicode(Fitzpatrick.TYPE_3);
    final String surfer4 = EmojiManager.getForAlias("surfer").getUnicode(Fitzpatrick.TYPE_4);
    final String surfer5 = EmojiManager.getForAlias("surfer").getUnicode(Fitzpatrick.TYPE_5);
    final String surfer6 = EmojiManager.getForAlias("surfer").getUnicode(Fitzpatrick.TYPE_6);
    final String surfers = surfer + " " + surfer3 + " " + surfer4 + " " + surfer5 + " " + surfer6;

    // WHEN
    List<String> result = EmojiParser.extractEmojis(surfers);

    // THEN
    assertEquals(5, result.size());
    assertEquals(surfer, result.get(0));
    assertEquals(surfer3, result.get(1));
    assertEquals(surfer4, result.get(2));
    assertEquals(surfer5, result.get(3));
    assertEquals(surfer6, result.get(4));

  }

  @Test
  public void parseToAliases_with_first_medal() {
    // GIVEN
    String str = "🥇";

    // WHEN
    String result = EmojiParser.parseToAliases(str);

    // THEN
    assertEquals(":first_place_medal:", result);
  }

  // ---- parseToUnicode skin-color (Fitzpatrick) tests ----

  @Test
  public void parseToUnicode_skinTone_pipeNotation_allFitzpatrickTypes() {
    // GIVEN: one alias per Fitzpatrick type using the pipe (|) separator
    String str = ":boy|type_1_2: :boy|type_3: :boy|type_4: :boy|type_5: :boy|type_6:";

    // WHEN
    String result = EmojiParser.parseToUnicode(str);

    // THEN: each type maps to its respective skin tone modifier
    assertEquals(
      "\uD83D\uDC66\uD83C\uDFFB \uD83D\uDC66\uD83C\uDFFC \uD83D\uDC66\uD83C\uDFFD \uD83D\uDC66\uD83C\uDFFE \uD83D\uDC66\uD83C\uDFFF",
      result
    );
  }

  @Test
  public void parseToUnicode_skinTone2_sameUnicodeAsSkinTone1() {
    // GIVEN: skin-tone-1 and skin-tone-2 both map to PALE_WHITE (\uD83C\uDFFB)
    String strTone1 = ":boy::skin-tone-1:";
    String strTone2 = ":boy::skin-tone-2:";

    // WHEN
    String resultTone1 = EmojiParser.parseToUnicode(strTone1);
    String resultTone2 = EmojiParser.parseToUnicode(strTone2);

    // THEN: both produce identical unicode output
    assertEquals("\uD83D\uDC66\uD83C\uDFFB", resultTone1);
    assertEquals(resultTone1, resultTone2);
  }

  @Test
  public void parseToUnicode_skinTone_consecutiveDoubleColonEmojis() {
    // GIVEN: two skin-toned emojis in a row separated only by "::"
    // The closing ":" of the first skin-tone and the opening ":" of the next
    // alias share the "::" boundary between them.
    String str = ":boy::skin-tone-3::girl::skin-tone-4:";

    // WHEN
    String result = EmojiParser.parseToUnicode(str);

    // THEN: boy+cream-white followed immediately by girl+moderate-brown
    assertEquals(
      "\uD83D\uDC66\uD83C\uDFFC\uD83D\uDC67\uD83C\uDFFD",
      result
    );
  }

  @Test
  public void parseToUnicode_skinTone_thumbsupDoubleColonAllTypes() {
    // GIVEN: :+1: with every skin-tone via the "::" double-colon separator
    String str = ":+1::skin-tone-1: :+1::skin-tone-3: :+1::skin-tone-4: :+1::skin-tone-5: :+1::skin-tone-6:";

    // WHEN
    String result = EmojiParser.parseToUnicode(str);

    // THEN
    assertEquals(
      "\uD83D\uDC4D\uD83C\uDFFB \uD83D\uDC4D\uD83C\uDFFC \uD83D\uDC4D\uD83C\uDFFD \uD83D\uDC4D\uD83C\uDFFE \uD83D\uDC4D\uD83C\uDFFF",
      result
    );
  }

  @Test
  public void parseToUnicode_skinTone_doubleColonPrefix_producesLeadingLiteralColon() {
    // GIVEN: "::boy::skin-tone-6:" — the very first ":" cannot start a valid
    // alias ("::boy:…" fails the trimAlias lookup), so it stays as a literal
    // colon; parsing resumes at the second ":" which begins ":boy::skin-tone-6:".
    String str = "::boy::skin-tone-6:";

    // WHEN
    String result = EmojiParser.parseToUnicode(str);

    // THEN: one literal ":" followed by the skin-toned boy emoji
    assertEquals(":\uD83D\uDC66\uD83C\uDFFF", result);
  }

  @Test
  public void parseToUnicode_skinTone_tripleColonPrefix_producesTwoLeadingLiteralColons() {
    // GIVEN: ":::boy::skin-tone-6:" — two opening colons that cannot form valid
    // aliases are left as literal characters; the third ":" starts the real alias.
    String str = ":::boy::skin-tone-6:";

    // WHEN
    String result = EmojiParser.parseToUnicode(str);

    // THEN: two literal "::" characters followed by the skin-toned boy emoji
    assertEquals("::\uD83D\uDC66\uD83C\uDFFF", result);
  }

  @Test
  public void parseToUnicode_skinTone_mixedPipeAndDoubleColonFormats() {
    // GIVEN: a string that contains both pipe (|) and double-colon (::) skin-tone formats
    String str = ":woman|type_4: :man::skin-tone-3:";

    // WHEN
    String result = EmojiParser.parseToUnicode(str);

    // THEN: woman+moderate-brown, man+cream-white
    assertEquals("\uD83D\uDC69\uD83C\uDFFD \uD83D\uDC68\uD83C\uDFFC", result);
  }

  @Test
  public void parseToUnicode_skinTone_inMiddleOfSentence() {
    // GIVEN: skin-toned emoji aliases surrounded by plain text
    String str = "Hello :girl::skin-tone-5: world :boy|type_3: friend";

    // WHEN
    String result = EmojiParser.parseToUnicode(str);

    // THEN: surrounding text preserved, emojis replaced with correct skin tones
    assertEquals(
      "Hello \uD83D\uDC67\uD83C\uDFFE world \uD83D\uDC66\uD83C\uDFFC friend",
      result
    );
  }

/*  @Test
  public void removeAllEmojiParser_with_continuous_emojis() {
    // GIVEN
    String str = "♣︎❤︎♣︎❤️Love♣︎❤︎♣";

    // WHEN
    String result = EmojiParser.removeAllEmojis(str);

    // THEN
    assertEquals("Love", result);
  }*/

  @Test
  public void removeAllEmojiParser_with_continuous_emojis2() {
    // GIVEN
    String str = "Love♣︎❤︎♣︎❤♣︎❤︎♣";

    // WHEN
    String result = EmojiParser.removeAllEmojis(str);

    // THEN
    assertEquals("Love", result);
  }
}
