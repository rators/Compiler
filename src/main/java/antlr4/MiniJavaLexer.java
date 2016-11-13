// Generated from /Users/rtorres/IdeaProjects/Compiler/src/main/java/antlr4/MiniJava.g4 by ANTLR 4.5.3
package antlr4;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MiniJavaLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, ID=35, INT=36, BOOLEAN=37, WS=38, COMMENT=39, 
		LINE_COMMENT=40;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
		"T__17", "T__18", "T__19", "T__20", "T__21", "T__22", "T__23", "T__24", 
		"T__25", "T__26", "T__27", "T__28", "T__29", "T__30", "T__31", "T__32", 
		"T__33", "ID", "INT", "BOOLEAN", "WS", "COMMENT", "LINE_COMMENT"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'class'", "'{'", "'public'", "'static'", "'void'", "'main'", "'('", 
		"'String'", "'['", "']'", "')'", "'}'", "'extends'", "'case'", "','", 
		"';'", "'return'", "'int'", "'boolean'", "'System.out.println'", "'='", 
		"'while'", "'if'", "'else'", "'!'", "'<'", "'&&'", "'+'", "'-'", "'*'", 
		"'.'", "'length'", "'this'", "'new'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, "ID", 
		"INT", "BOOLEAN", "WS", "COMMENT", "LINE_COMMENT"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public MiniJavaLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "MiniJava.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2*\u0126\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\3\2\3\2\3\2\3"+
		"\2\3\2\3\2\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22"+
		"\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\27\3\27\3\27\3\27"+
		"\3\27\3\27\3\30\3\30\3\30\3\31\3\31\3\31\3\31\3\31\3\32\3\32\3\33\3\33"+
		"\3\34\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3!\3!\3!\3!"+
		"\3!\3\"\3\"\3\"\3\"\3\"\3#\3#\3#\3#\3$\3$\7$\u00ed\n$\f$\16$\u00f0\13"+
		"$\3%\6%\u00f3\n%\r%\16%\u00f4\3&\3&\3&\3&\3&\3&\3&\3&\3&\5&\u0100\n&\3"+
		"\'\6\'\u0103\n\'\r\'\16\'\u0104\3\'\3\'\3(\3(\3(\3(\7(\u010d\n(\f(\16"+
		"(\u0110\13(\3(\3(\3(\3(\3(\3)\3)\3)\3)\7)\u011b\n)\f)\16)\u011e\13)\3"+
		")\5)\u0121\n)\3)\3)\3)\3)\4\u010e\u011c\2*\3\3\5\4\7\5\t\6\13\7\r\b\17"+
		"\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+"+
		"\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*\3"+
		"\2\5\5\2C\\aac|\6\2\62;C\\aac|\5\2\13\f\17\17\"\"\u012c\2\3\3\2\2\2\2"+
		"\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2"+
		"\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2"+
		"\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2"+
		"\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2"+
		"\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2"+
		"\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2"+
		"K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\3S\3\2\2\2\5Y\3\2\2\2\7[\3"+
		"\2\2\2\tb\3\2\2\2\13i\3\2\2\2\rn\3\2\2\2\17s\3\2\2\2\21u\3\2\2\2\23|\3"+
		"\2\2\2\25~\3\2\2\2\27\u0080\3\2\2\2\31\u0082\3\2\2\2\33\u0084\3\2\2\2"+
		"\35\u008c\3\2\2\2\37\u0091\3\2\2\2!\u0093\3\2\2\2#\u0095\3\2\2\2%\u009c"+
		"\3\2\2\2\'\u00a0\3\2\2\2)\u00a8\3\2\2\2+\u00bb\3\2\2\2-\u00bd\3\2\2\2"+
		"/\u00c3\3\2\2\2\61\u00c6\3\2\2\2\63\u00cb\3\2\2\2\65\u00cd\3\2\2\2\67"+
		"\u00cf\3\2\2\29\u00d2\3\2\2\2;\u00d4\3\2\2\2=\u00d6\3\2\2\2?\u00d8\3\2"+
		"\2\2A\u00da\3\2\2\2C\u00e1\3\2\2\2E\u00e6\3\2\2\2G\u00ea\3\2\2\2I\u00f2"+
		"\3\2\2\2K\u00ff\3\2\2\2M\u0102\3\2\2\2O\u0108\3\2\2\2Q\u0116\3\2\2\2S"+
		"T\7e\2\2TU\7n\2\2UV\7c\2\2VW\7u\2\2WX\7u\2\2X\4\3\2\2\2YZ\7}\2\2Z\6\3"+
		"\2\2\2[\\\7r\2\2\\]\7w\2\2]^\7d\2\2^_\7n\2\2_`\7k\2\2`a\7e\2\2a\b\3\2"+
		"\2\2bc\7u\2\2cd\7v\2\2de\7c\2\2ef\7v\2\2fg\7k\2\2gh\7e\2\2h\n\3\2\2\2"+
		"ij\7x\2\2jk\7q\2\2kl\7k\2\2lm\7f\2\2m\f\3\2\2\2no\7o\2\2op\7c\2\2pq\7"+
		"k\2\2qr\7p\2\2r\16\3\2\2\2st\7*\2\2t\20\3\2\2\2uv\7U\2\2vw\7v\2\2wx\7"+
		"t\2\2xy\7k\2\2yz\7p\2\2z{\7i\2\2{\22\3\2\2\2|}\7]\2\2}\24\3\2\2\2~\177"+
		"\7_\2\2\177\26\3\2\2\2\u0080\u0081\7+\2\2\u0081\30\3\2\2\2\u0082\u0083"+
		"\7\177\2\2\u0083\32\3\2\2\2\u0084\u0085\7g\2\2\u0085\u0086\7z\2\2\u0086"+
		"\u0087\7v\2\2\u0087\u0088\7g\2\2\u0088\u0089\7p\2\2\u0089\u008a\7f\2\2"+
		"\u008a\u008b\7u\2\2\u008b\34\3\2\2\2\u008c\u008d\7e\2\2\u008d\u008e\7"+
		"c\2\2\u008e\u008f\7u\2\2\u008f\u0090\7g\2\2\u0090\36\3\2\2\2\u0091\u0092"+
		"\7.\2\2\u0092 \3\2\2\2\u0093\u0094\7=\2\2\u0094\"\3\2\2\2\u0095\u0096"+
		"\7t\2\2\u0096\u0097\7g\2\2\u0097\u0098\7v\2\2\u0098\u0099\7w\2\2\u0099"+
		"\u009a\7t\2\2\u009a\u009b\7p\2\2\u009b$\3\2\2\2\u009c\u009d\7k\2\2\u009d"+
		"\u009e\7p\2\2\u009e\u009f\7v\2\2\u009f&\3\2\2\2\u00a0\u00a1\7d\2\2\u00a1"+
		"\u00a2\7q\2\2\u00a2\u00a3\7q\2\2\u00a3\u00a4\7n\2\2\u00a4\u00a5\7g\2\2"+
		"\u00a5\u00a6\7c\2\2\u00a6\u00a7\7p\2\2\u00a7(\3\2\2\2\u00a8\u00a9\7U\2"+
		"\2\u00a9\u00aa\7{\2\2\u00aa\u00ab\7u\2\2\u00ab\u00ac\7v\2\2\u00ac\u00ad"+
		"\7g\2\2\u00ad\u00ae\7o\2\2\u00ae\u00af\7\60\2\2\u00af\u00b0\7q\2\2\u00b0"+
		"\u00b1\7w\2\2\u00b1\u00b2\7v\2\2\u00b2\u00b3\7\60\2\2\u00b3\u00b4\7r\2"+
		"\2\u00b4\u00b5\7t\2\2\u00b5\u00b6\7k\2\2\u00b6\u00b7\7p\2\2\u00b7\u00b8"+
		"\7v\2\2\u00b8\u00b9\7n\2\2\u00b9\u00ba\7p\2\2\u00ba*\3\2\2\2\u00bb\u00bc"+
		"\7?\2\2\u00bc,\3\2\2\2\u00bd\u00be\7y\2\2\u00be\u00bf\7j\2\2\u00bf\u00c0"+
		"\7k\2\2\u00c0\u00c1\7n\2\2\u00c1\u00c2\7g\2\2\u00c2.\3\2\2\2\u00c3\u00c4"+
		"\7k\2\2\u00c4\u00c5\7h\2\2\u00c5\60\3\2\2\2\u00c6\u00c7\7g\2\2\u00c7\u00c8"+
		"\7n\2\2\u00c8\u00c9\7u\2\2\u00c9\u00ca\7g\2\2\u00ca\62\3\2\2\2\u00cb\u00cc"+
		"\7#\2\2\u00cc\64\3\2\2\2\u00cd\u00ce\7>\2\2\u00ce\66\3\2\2\2\u00cf\u00d0"+
		"\7(\2\2\u00d0\u00d1\7(\2\2\u00d18\3\2\2\2\u00d2\u00d3\7-\2\2\u00d3:\3"+
		"\2\2\2\u00d4\u00d5\7/\2\2\u00d5<\3\2\2\2\u00d6\u00d7\7,\2\2\u00d7>\3\2"+
		"\2\2\u00d8\u00d9\7\60\2\2\u00d9@\3\2\2\2\u00da\u00db\7n\2\2\u00db\u00dc"+
		"\7g\2\2\u00dc\u00dd\7p\2\2\u00dd\u00de\7i\2\2\u00de\u00df\7v\2\2\u00df"+
		"\u00e0\7j\2\2\u00e0B\3\2\2\2\u00e1\u00e2\7v\2\2\u00e2\u00e3\7j\2\2\u00e3"+
		"\u00e4\7k\2\2\u00e4\u00e5\7u\2\2\u00e5D\3\2\2\2\u00e6\u00e7\7p\2\2\u00e7"+
		"\u00e8\7g\2\2\u00e8\u00e9\7y\2\2\u00e9F\3\2\2\2\u00ea\u00ee\t\2\2\2\u00eb"+
		"\u00ed\t\3\2\2\u00ec\u00eb\3\2\2\2\u00ed\u00f0\3\2\2\2\u00ee\u00ec\3\2"+
		"\2\2\u00ee\u00ef\3\2\2\2\u00efH\3\2\2\2\u00f0\u00ee\3\2\2\2\u00f1\u00f3"+
		"\4\62;\2\u00f2\u00f1\3\2\2\2\u00f3\u00f4\3\2\2\2\u00f4\u00f2\3\2\2\2\u00f4"+
		"\u00f5\3\2\2\2\u00f5J\3\2\2\2\u00f6\u00f7\7v\2\2\u00f7\u00f8\7t\2\2\u00f8"+
		"\u00f9\7w\2\2\u00f9\u0100\7g\2\2\u00fa\u00fb\7h\2\2\u00fb\u00fc\7c\2\2"+
		"\u00fc\u00fd\7n\2\2\u00fd\u00fe\7u\2\2\u00fe\u0100\7g\2\2\u00ff\u00f6"+
		"\3\2\2\2\u00ff\u00fa\3\2\2\2\u0100L\3\2\2\2\u0101\u0103\t\4\2\2\u0102"+
		"\u0101\3\2\2\2\u0103\u0104\3\2\2\2\u0104\u0102\3\2\2\2\u0104\u0105\3\2"+
		"\2\2\u0105\u0106\3\2\2\2\u0106\u0107\b\'\2\2\u0107N\3\2\2\2\u0108\u0109"+
		"\7\61\2\2\u0109\u010a\7,\2\2\u010a\u010e\3\2\2\2\u010b\u010d\13\2\2\2"+
		"\u010c\u010b\3\2\2\2\u010d\u0110\3\2\2\2\u010e\u010f\3\2\2\2\u010e\u010c"+
		"\3\2\2\2\u010f\u0111\3\2\2\2\u0110\u010e\3\2\2\2\u0111\u0112\7,\2\2\u0112"+
		"\u0113\7\61\2\2\u0113\u0114\3\2\2\2\u0114\u0115\b(\2\2\u0115P\3\2\2\2"+
		"\u0116\u0117\7\61\2\2\u0117\u0118\7\61\2\2\u0118\u011c\3\2\2\2\u0119\u011b"+
		"\13\2\2\2\u011a\u0119\3\2\2\2\u011b\u011e\3\2\2\2\u011c\u011d\3\2\2\2"+
		"\u011c\u011a\3\2\2\2\u011d\u0120\3\2\2\2\u011e\u011c\3\2\2\2\u011f\u0121"+
		"\7\17\2\2\u0120\u011f\3\2\2\2\u0120\u0121\3\2\2\2\u0121\u0122\3\2\2\2"+
		"\u0122\u0123\7\f\2\2\u0123\u0124\3\2\2\2\u0124\u0125\b)\2\2\u0125R\3\2"+
		"\2\2\n\2\u00ee\u00f4\u00ff\u0104\u010e\u011c\u0120\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}