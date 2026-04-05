package com.vdurmont.emoji;

import static com.vdurmont.emoji.Fitzpatrick.Colors.*;

/**
 * Enum that represents the Fitzpatrick modifiers supported by the emojis.
 */
public enum Fitzpatrick {
  /**
   * Fitzpatrick modifier of type 1/2 (pale white/white)
   */
  TYPE_1_2("\uD83C\uDFFB"),
  SKIN_TONE_1(PALE_WHITE),
  SKIN_TONE_2(PALE_WHITE),

  /**
   * Fitzpatrick modifier of type 3 (cream white)
   */
  TYPE_3(CREAM_WHITE),
  SKIN_TONE_3(CREAM_WHITE),

  /**
   * Fitzpatrick modifier of type 4 (moderate brown)
   */
  TYPE_4(MODERATE_BROWN),
  SKIN_TONE_4(MODERATE_BROWN),

  /**
   * Fitzpatrick modifier of type 5 (dark brown)
   */
  TYPE_5(DARK_BROWN),
  SKIN_TONE_5(DARK_BROWN),

  /**
   * Fitzpatrick modifier of type 6 (black)
   */
  TYPE_6(BLACK),
  SKIN_TONE_6(BLACK);

  /**
   * TONE COLORS VALUE IN UNICODE
   */
  static class Colors {
    static final String PALE_WHITE     = "\uD83C\uDFFB";
    static final String CREAM_WHITE    = "\uD83C\uDFFC";
    static final String MODERATE_BROWN = "\uD83C\uDFFD";
    static final String DARK_BROWN     = "\uD83C\uDFFE";
    static final String BLACK          = "\uD83C\uDFFF";
  }

  /**
   * The unicode representation of the Fitzpatrick modifier
   */
  public final String unicode;

  Fitzpatrick(String unicode) {
    this.unicode = unicode;
  }


  public static Fitzpatrick fitzpatrickFromUnicode(String unicode) {
    for (Fitzpatrick v : values()) {
      if (v.unicode.equals(unicode)) {
        return v;
      }
    }
    return null;
  }

  public static Fitzpatrick fitzpatrickFromType(String type) {
    try {
      final String baseSuffix = "skin-tone-";
      if (type.contains(baseSuffix)) {
        return Fitzpatrick.valueOf(type.replace('-', '_').toUpperCase());
      }

      return Fitzpatrick.valueOf(type.toUpperCase());
    } catch (IllegalArgumentException e) {
      return null;
    }
  }
}
