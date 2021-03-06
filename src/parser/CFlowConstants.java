/* Generated By:JJTree&JavaCC: Do not edit this line. CFlowConstants.java */
package parser;


/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface CFlowConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int LINESTART = 4;
  /** RegularExpression Id. */
  int LINEEND = 5;
  /** RegularExpression Id. */
  int OR = 6;
  /** RegularExpression Id. */
  int QUANTIFIER = 7;
  /** RegularExpression Id. */
  int OPEN_GROUP = 8;
  /** RegularExpression Id. */
  int CLOSE_GROUP = 9;
  /** RegularExpression Id. */
  int OPEN_SET = 10;
  /** RegularExpression Id. */
  int CLOSE_SET = 11;
  /** RegularExpression Id. */
  int OPEN_REPEAT = 12;
  /** RegularExpression Id. */
  int CLOSE_REPEAT = 13;
  /** RegularExpression Id. */
  int KLEEN_CLOSURE = 14;
  /** RegularExpression Id. */
  int PLUS = 15;
  /** RegularExpression Id. */
  int CHARACTER = 16;
  /** RegularExpression Id. */
  int NUMBER = 17;
  /** RegularExpression Id. */
  int ANY = 18;
  /** RegularExpression Id. */
  int LF = 19;

  /** Lexical state. */
  int DEFAULT = 0;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\t\"",
    "\"\\r\"",
    "\"^\"",
    "\"$\"",
    "\"|\"",
    "\"?\"",
    "\"(\"",
    "\")\"",
    "\"[\"",
    "\"]\"",
    "\"{\"",
    "\"}\"",
    "\"*\"",
    "\"+\"",
    "<CHARACTER>",
    "<NUMBER>",
    "\".\"",
    "\"\\n\"",
    "\"[^\"",
    "\"-\"",
  };

}
