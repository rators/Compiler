// Generated from C:/Users/rotorres/IdeaProjects/Compiler/src/main/java/antlr4\MiniJava.g4 by ANTLR 4.5.3
package antlr4;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MiniJavaParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, ID=35, INT_LIT=36, BOOLEAN_LIT=37, WS=38, 
		COMMENT=39, LINE_COMMENT=40;
	public static final int
		RULE_prog = 0, RULE_mainClass = 1, RULE_classDecl = 2, RULE_varDecl = 3, 
		RULE_propertyDecl = 4, RULE_methodDecl = 5, RULE_methodParam = 6, RULE_caseProperty = 7, 
		RULE_type = 8, RULE_statement = 9, RULE_condExpr = 10, RULE_expr = 11, 
		RULE_atom = 12;
	public static final String[] ruleNames = {
		"prog", "mainClass", "classDecl", "varDecl", "propertyDecl", "methodDecl", 
		"methodParam", "caseProperty", "type", "statement", "condExpr", "expr", 
		"atom"
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
		"INT_LIT", "BOOLEAN_LIT", "WS", "COMMENT", "LINE_COMMENT"
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

	@Override
	public String getGrammarFileName() { return "MiniJava.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public MiniJavaParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgContext extends ParserRuleContext {
		public MainClassContext mainClass() {
			return getRuleContext(MainClassContext.class,0);
		}
		public List<ClassDeclContext> classDecl() {
			return getRuleContexts(ClassDeclContext.class);
		}
		public ClassDeclContext classDecl(int i) {
			return getRuleContext(ClassDeclContext.class,i);
		}
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterProg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitProg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJavaVisitor ) return ((MiniJavaVisitor<? extends T>)visitor).visitProg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_prog);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(26);
			mainClass();
			setState(30);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0 || _la==T__13) {
				{
				{
				setState(27);
				classDecl();
				}
				}
				setState(32);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MainClassContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(MiniJavaParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(MiniJavaParser.ID, i);
		}
		public List<VarDeclContext> varDecl() {
			return getRuleContexts(VarDeclContext.class);
		}
		public VarDeclContext varDecl(int i) {
			return getRuleContext(VarDeclContext.class,i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public MainClassContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mainClass; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterMainClass(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitMainClass(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJavaVisitor ) return ((MiniJavaVisitor<? extends T>)visitor).visitMainClass(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MainClassContext mainClass() throws RecognitionException {
		MainClassContext _localctx = new MainClassContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_mainClass);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(33);
			match(T__0);
			setState(34);
			match(ID);
			setState(35);
			match(T__1);
			setState(36);
			match(T__2);
			setState(37);
			match(T__3);
			setState(38);
			match(T__4);
			setState(39);
			match(T__5);
			setState(40);
			match(T__6);
			setState(41);
			match(T__7);
			setState(42);
			match(T__8);
			setState(43);
			match(T__9);
			setState(44);
			match(ID);
			setState(45);
			match(T__10);
			setState(46);
			match(T__1);
			setState(50);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(47);
					varDecl();
					}
					} 
				}
				setState(52);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			}
			setState(56);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__19) | (1L << T__21) | (1L << T__22) | (1L << ID))) != 0)) {
				{
				{
				setState(53);
				statement();
				}
				}
				setState(58);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(59);
			match(T__11);
			setState(60);
			match(T__11);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassDeclContext extends ParserRuleContext {
		public ClassDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDecl; }
	 
		public ClassDeclContext() { }
		public void copyFrom(ClassDeclContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CaseClassDeclContext extends ClassDeclContext {
		public TerminalNode ID() { return getToken(MiniJavaParser.ID, 0); }
		public List<CasePropertyContext> caseProperty() {
			return getRuleContexts(CasePropertyContext.class);
		}
		public CasePropertyContext caseProperty(int i) {
			return getRuleContext(CasePropertyContext.class,i);
		}
		public CaseClassDeclContext(ClassDeclContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterCaseClassDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitCaseClassDecl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJavaVisitor ) return ((MiniJavaVisitor<? extends T>)visitor).visitCaseClassDecl(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BaseClassContext extends ClassDeclContext {
		public TerminalNode ID() { return getToken(MiniJavaParser.ID, 0); }
		public List<PropertyDeclContext> propertyDecl() {
			return getRuleContexts(PropertyDeclContext.class);
		}
		public PropertyDeclContext propertyDecl(int i) {
			return getRuleContext(PropertyDeclContext.class,i);
		}
		public List<MethodDeclContext> methodDecl() {
			return getRuleContexts(MethodDeclContext.class);
		}
		public MethodDeclContext methodDecl(int i) {
			return getRuleContext(MethodDeclContext.class,i);
		}
		public BaseClassContext(ClassDeclContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterBaseClass(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitBaseClass(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJavaVisitor ) return ((MiniJavaVisitor<? extends T>)visitor).visitBaseClass(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ChildClassContext extends ClassDeclContext {
		public List<TerminalNode> ID() { return getTokens(MiniJavaParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(MiniJavaParser.ID, i);
		}
		public List<PropertyDeclContext> propertyDecl() {
			return getRuleContexts(PropertyDeclContext.class);
		}
		public PropertyDeclContext propertyDecl(int i) {
			return getRuleContext(PropertyDeclContext.class,i);
		}
		public List<MethodDeclContext> methodDecl() {
			return getRuleContexts(MethodDeclContext.class);
		}
		public MethodDeclContext methodDecl(int i) {
			return getRuleContext(MethodDeclContext.class,i);
		}
		public ChildClassContext(ClassDeclContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterChildClass(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitChildClass(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJavaVisitor ) return ((MiniJavaVisitor<? extends T>)visitor).visitChildClass(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassDeclContext classDecl() throws RecognitionException {
		ClassDeclContext _localctx = new ClassDeclContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_classDecl);
		int _la;
		try {
			setState(116);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				_localctx = new BaseClassContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(62);
				match(T__0);
				setState(63);
				match(ID);
				setState(64);
				match(T__1);
				setState(68);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__17) | (1L << T__18) | (1L << ID))) != 0)) {
					{
					{
					setState(65);
					propertyDecl();
					}
					}
					setState(70);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(74);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(71);
					methodDecl();
					}
					}
					setState(76);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(77);
				match(T__11);
				}
				break;
			case 2:
				_localctx = new ChildClassContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(78);
				match(T__0);
				setState(79);
				match(ID);
				setState(80);
				match(T__12);
				setState(81);
				match(ID);
				setState(82);
				match(T__1);
				setState(86);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__17) | (1L << T__18) | (1L << ID))) != 0)) {
					{
					{
					setState(83);
					propertyDecl();
					}
					}
					setState(88);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(92);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(89);
					methodDecl();
					}
					}
					setState(94);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(95);
				match(T__11);
				}
				break;
			case 3:
				_localctx = new CaseClassDeclContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(96);
				match(T__13);
				setState(97);
				match(T__0);
				setState(98);
				match(ID);
				setState(99);
				match(T__6);
				setState(112);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__17) | (1L << T__18) | (1L << ID))) != 0)) {
					{
					setState(100);
					caseProperty();
					setState(109);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__14) {
						{
						{
						setState(101);
						match(T__14);
						setState(103); 
						_errHandler.sync(this);
						_la = _input.LA(1);
						do {
							{
							{
							setState(102);
							caseProperty();
							}
							}
							setState(105); 
							_errHandler.sync(this);
							_la = _input.LA(1);
						} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__17) | (1L << T__18) | (1L << ID))) != 0) );
						}
						}
						setState(111);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(114);
				match(T__10);
				setState(115);
				match(T__15);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarDeclContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(MiniJavaParser.ID, 0); }
		public VarDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varDecl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterVarDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitVarDecl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJavaVisitor ) return ((MiniJavaVisitor<? extends T>)visitor).visitVarDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarDeclContext varDecl() throws RecognitionException {
		VarDeclContext _localctx = new VarDeclContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_varDecl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(118);
			type();
			setState(119);
			match(ID);
			setState(120);
			match(T__15);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PropertyDeclContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(MiniJavaParser.ID, 0); }
		public PropertyDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_propertyDecl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterPropertyDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitPropertyDecl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJavaVisitor ) return ((MiniJavaVisitor<? extends T>)visitor).visitPropertyDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PropertyDeclContext propertyDecl() throws RecognitionException {
		PropertyDeclContext _localctx = new PropertyDeclContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_propertyDecl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(122);
			type();
			setState(123);
			match(ID);
			setState(124);
			match(T__15);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodDeclContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(MiniJavaParser.ID, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public List<MethodParamContext> methodParam() {
			return getRuleContexts(MethodParamContext.class);
		}
		public MethodParamContext methodParam(int i) {
			return getRuleContext(MethodParamContext.class,i);
		}
		public List<VarDeclContext> varDecl() {
			return getRuleContexts(VarDeclContext.class);
		}
		public VarDeclContext varDecl(int i) {
			return getRuleContext(VarDeclContext.class,i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public MethodDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodDecl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterMethodDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitMethodDecl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJavaVisitor ) return ((MiniJavaVisitor<? extends T>)visitor).visitMethodDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodDeclContext methodDecl() throws RecognitionException {
		MethodDeclContext _localctx = new MethodDeclContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_methodDecl);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(126);
			match(T__2);
			setState(127);
			type();
			setState(128);
			match(ID);
			setState(129);
			match(T__6);
			setState(142);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__17) | (1L << T__18) | (1L << ID))) != 0)) {
				{
				setState(130);
				methodParam();
				setState(139);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__14) {
					{
					{
					setState(131);
					match(T__14);
					setState(133); 
					_errHandler.sync(this);
					_la = _input.LA(1);
					do {
						{
						{
						setState(132);
						methodParam();
						}
						}
						setState(135); 
						_errHandler.sync(this);
						_la = _input.LA(1);
					} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__17) | (1L << T__18) | (1L << ID))) != 0) );
					}
					}
					setState(141);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(144);
			match(T__10);
			setState(145);
			match(T__1);
			setState(149);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(146);
					varDecl();
					}
					} 
				}
				setState(151);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			}
			setState(155);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__19) | (1L << T__21) | (1L << T__22) | (1L << ID))) != 0)) {
				{
				{
				setState(152);
				statement();
				}
				}
				setState(157);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(158);
			match(T__16);
			setState(159);
			expr();
			setState(160);
			match(T__15);
			setState(161);
			match(T__11);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodParamContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(MiniJavaParser.ID, 0); }
		public MethodParamContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodParam; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterMethodParam(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitMethodParam(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJavaVisitor ) return ((MiniJavaVisitor<? extends T>)visitor).visitMethodParam(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodParamContext methodParam() throws RecognitionException {
		MethodParamContext _localctx = new MethodParamContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_methodParam);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(163);
			type();
			setState(164);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CasePropertyContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(MiniJavaParser.ID, 0); }
		public CasePropertyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_caseProperty; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterCaseProperty(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitCaseProperty(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJavaVisitor ) return ((MiniJavaVisitor<? extends T>)visitor).visitCaseProperty(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CasePropertyContext caseProperty() throws RecognitionException {
		CasePropertyContext _localctx = new CasePropertyContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_caseProperty);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(166);
			type();
			setState(167);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(MiniJavaParser.ID, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJavaVisitor ) return ((MiniJavaVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_type);
		try {
			setState(175);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(169);
				match(T__17);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(170);
				match(T__17);
				setState(171);
				match(T__8);
				setState(172);
				match(T__9);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(173);
				match(T__18);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(174);
				match(ID);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	 
		public StatementContext() { }
		public void copyFrom(StatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class BasicBlockContext extends StatementContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public BasicBlockContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterBasicBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitBasicBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJavaVisitor ) return ((MiniJavaVisitor<? extends T>)visitor).visitBasicBlock(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class WhileLoopHeadContext extends StatementContext {
		public CondExprContext condExpr() {
			return getRuleContext(CondExprContext.class,0);
		}
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public WhileLoopHeadContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterWhileLoopHead(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitWhileLoopHead(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJavaVisitor ) return ((MiniJavaVisitor<? extends T>)visitor).visitWhileLoopHead(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IfStatementContext extends StatementContext {
		public CondExprContext condExpr() {
			return getRuleContext(CondExprContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public IfStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterIfStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitIfStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJavaVisitor ) return ((MiniJavaVisitor<? extends T>)visitor).visitIfStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class VarDefinitionContext extends StatementContext {
		public TerminalNode ID() { return getToken(MiniJavaParser.ID, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public VarDefinitionContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterVarDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitVarDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJavaVisitor ) return ((MiniJavaVisitor<? extends T>)visitor).visitVarDefinition(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PrintToConsoleContext extends StatementContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public PrintToConsoleContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterPrintToConsole(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitPrintToConsole(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJavaVisitor ) return ((MiniJavaVisitor<? extends T>)visitor).visitPrintToConsole(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ArrayDefinitionContext extends StatementContext {
		public List<TerminalNode> ID() { return getTokens(MiniJavaParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(MiniJavaParser.ID, i);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode INT_LIT() { return getToken(MiniJavaParser.INT_LIT, 0); }
		public ArrayDefinitionContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterArrayDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitArrayDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJavaVisitor ) return ((MiniJavaVisitor<? extends T>)visitor).visitArrayDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_statement);
		int _la;
		try {
			setState(218);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				_localctx = new BasicBlockContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(177);
				match(T__1);
				setState(181);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__19) | (1L << T__21) | (1L << T__22) | (1L << ID))) != 0)) {
					{
					{
					setState(178);
					statement();
					}
					}
					setState(183);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(184);
				match(T__11);
				}
				break;
			case 2:
				_localctx = new PrintToConsoleContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(185);
				match(T__19);
				setState(186);
				match(T__6);
				setState(187);
				expr();
				setState(188);
				match(T__10);
				setState(189);
				match(T__15);
				}
				break;
			case 3:
				_localctx = new VarDefinitionContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(191);
				match(ID);
				setState(192);
				match(T__20);
				setState(193);
				expr();
				setState(194);
				match(T__15);
				}
				break;
			case 4:
				_localctx = new ArrayDefinitionContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(196);
				match(ID);
				setState(197);
				match(T__8);
				setState(198);
				_la = _input.LA(1);
				if ( !(_la==ID || _la==INT_LIT) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(199);
				match(T__9);
				setState(200);
				match(T__20);
				setState(201);
				expr();
				setState(202);
				match(T__15);
				}
				break;
			case 5:
				_localctx = new WhileLoopHeadContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(204);
				match(T__21);
				setState(205);
				match(T__6);
				setState(206);
				condExpr(0);
				setState(207);
				match(T__10);
				setState(208);
				statement();
				}
				break;
			case 6:
				_localctx = new IfStatementContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(210);
				match(T__22);
				setState(211);
				match(T__6);
				setState(212);
				condExpr(0);
				setState(213);
				match(T__10);
				setState(214);
				statement();
				setState(215);
				match(T__23);
				setState(216);
				statement();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CondExprContext extends ParserRuleContext {
		public CondExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condExpr; }
	 
		public CondExprContext() { }
		public void copyFrom(CondExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class NotExprContext extends CondExprContext {
		public CondExprContext condExpr() {
			return getRuleContext(CondExprContext.class,0);
		}
		public NotExprContext(CondExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterNotExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitNotExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJavaVisitor ) return ((MiniJavaVisitor<? extends T>)visitor).visitNotExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LessThanExprContext extends CondExprContext {
		public List<CondExprContext> condExpr() {
			return getRuleContexts(CondExprContext.class);
		}
		public CondExprContext condExpr(int i) {
			return getRuleContext(CondExprContext.class,i);
		}
		public LessThanExprContext(CondExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterLessThanExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitLessThanExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJavaVisitor ) return ((MiniJavaVisitor<? extends T>)visitor).visitLessThanExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParenExprContext extends CondExprContext {
		public CondExprContext condExpr() {
			return getRuleContext(CondExprContext.class,0);
		}
		public ParenExprContext(CondExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterParenExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitParenExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJavaVisitor ) return ((MiniJavaVisitor<? extends T>)visitor).visitParenExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NotBoolExprContext extends CondExprContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public NotBoolExprContext(CondExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterNotBoolExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitNotBoolExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJavaVisitor ) return ((MiniJavaVisitor<? extends T>)visitor).visitNotBoolExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AndExprContext extends CondExprContext {
		public List<CondExprContext> condExpr() {
			return getRuleContexts(CondExprContext.class);
		}
		public CondExprContext condExpr(int i) {
			return getRuleContext(CondExprContext.class,i);
		}
		public AndExprContext(CondExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterAndExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitAndExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJavaVisitor ) return ((MiniJavaVisitor<? extends T>)visitor).visitAndExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CondExprContext condExpr() throws RecognitionException {
		return condExpr(0);
	}

	private CondExprContext condExpr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		CondExprContext _localctx = new CondExprContext(_ctx, _parentState);
		CondExprContext _prevctx = _localctx;
		int _startState = 20;
		enterRecursionRule(_localctx, 20, RULE_condExpr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(228);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				{
				_localctx = new ParenExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(221);
				match(T__6);
				setState(222);
				condExpr(0);
				setState(223);
				match(T__10);
				}
				break;
			case 2:
				{
				_localctx = new NotExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(225);
				match(T__24);
				setState(226);
				condExpr(4);
				}
				break;
			case 3:
				{
				_localctx = new NotBoolExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(227);
				expr();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(238);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(236);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
					case 1:
						{
						_localctx = new LessThanExprContext(new CondExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_condExpr);
						setState(230);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(231);
						match(T__25);
						setState(232);
						condExpr(4);
						}
						break;
					case 2:
						{
						_localctx = new AndExprContext(new CondExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_condExpr);
						setState(233);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(234);
						match(T__26);
						setState(235);
						condExpr(3);
						}
						break;
					}
					} 
				}
				setState(240);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public List<AtomContext> atom() {
			return getRuleContexts(AtomContext.class);
		}
		public AtomContext atom(int i) {
			return getRuleContext(AtomContext.class,i);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode ID() { return getToken(MiniJavaParser.ID, 0); }
		public List<CondExprContext> condExpr() {
			return getRuleContexts(CondExprContext.class);
		}
		public CondExprContext condExpr(int i) {
			return getRuleContext(CondExprContext.class,i);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJavaVisitor ) return ((MiniJavaVisitor<? extends T>)visitor).visitExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_expr);
		int _la;
		try {
			int _alt;
			setState(280);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(241);
				atom();
				setState(250);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						setState(248);
						switch (_input.LA(1)) {
						case T__27:
							{
							setState(242);
							match(T__27);
							setState(243);
							atom();
							}
							break;
						case T__28:
							{
							setState(244);
							match(T__28);
							setState(245);
							atom();
							}
							break;
						case T__29:
							{
							setState(246);
							match(T__29);
							setState(247);
							atom();
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						} 
					}
					setState(252);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,23,_ctx);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(253);
				_la = _input.LA(1);
				if ( !(_la==T__27 || _la==T__28) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(254);
				atom();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(255);
				atom();
				setState(256);
				match(T__8);
				setState(257);
				expr();
				setState(258);
				match(T__9);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(260);
				atom();
				setState(261);
				match(T__30);
				setState(262);
				match(T__31);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(264);
				atom();
				setState(265);
				match(T__30);
				setState(266);
				match(ID);
				setState(267);
				match(T__6);
				setState(276);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__6) | (1L << T__24) | (1L << T__27) | (1L << T__28) | (1L << T__32) | (1L << T__33) | (1L << ID) | (1L << INT_LIT) | (1L << BOOLEAN_LIT))) != 0)) {
					{
					setState(268);
					condExpr(0);
					setState(273);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__14) {
						{
						{
						setState(269);
						match(T__14);
						setState(270);
						condExpr(0);
						}
						}
						setState(275);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(278);
				match(T__10);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AtomContext extends ParserRuleContext {
		public AtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atom; }
	 
		public AtomContext() { }
		public void copyFrom(AtomContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class IntLiteralContext extends AtomContext {
		public TerminalNode INT_LIT() { return getToken(MiniJavaParser.INT_LIT, 0); }
		public IntLiteralContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterIntLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitIntLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJavaVisitor ) return ((MiniJavaVisitor<? extends T>)visitor).visitIntLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ConstructorCallContext extends AtomContext {
		public TerminalNode ID() { return getToken(MiniJavaParser.ID, 0); }
		public ConstructorCallContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterConstructorCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitConstructorCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJavaVisitor ) return ((MiniJavaVisitor<? extends T>)visitor).visitConstructorCall(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IdLiteralContext extends AtomContext {
		public TerminalNode ID() { return getToken(MiniJavaParser.ID, 0); }
		public IdLiteralContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterIdLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitIdLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJavaVisitor ) return ((MiniJavaVisitor<? extends T>)visitor).visitIdLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParenExpressionContext extends AtomContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ParenExpressionContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterParenExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitParenExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJavaVisitor ) return ((MiniJavaVisitor<? extends T>)visitor).visitParenExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ThisCallContext extends AtomContext {
		public ThisCallContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterThisCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitThisCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJavaVisitor ) return ((MiniJavaVisitor<? extends T>)visitor).visitThisCall(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MethodCallContext extends AtomContext {
		public List<TerminalNode> ID() { return getTokens(MiniJavaParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(MiniJavaParser.ID, i);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public MethodCallContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterMethodCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitMethodCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJavaVisitor ) return ((MiniJavaVisitor<? extends T>)visitor).visitMethodCall(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IntegerArrContext extends AtomContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public IntegerArrContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterIntegerArr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitIntegerArr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJavaVisitor ) return ((MiniJavaVisitor<? extends T>)visitor).visitIntegerArr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BooleanLitContext extends AtomContext {
		public TerminalNode BOOLEAN_LIT() { return getToken(MiniJavaParser.BOOLEAN_LIT, 0); }
		public BooleanLitContext(AtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).enterBooleanLit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MiniJavaListener ) ((MiniJavaListener)listener).exitBooleanLit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJavaVisitor ) return ((MiniJavaVisitor<? extends T>)visitor).visitBooleanLit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AtomContext atom() throws RecognitionException {
		AtomContext _localctx = new AtomContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_atom);
		int _la;
		try {
			setState(307);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
			case 1:
				_localctx = new IntLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(282);
				match(INT_LIT);
				}
				break;
			case 2:
				_localctx = new IdLiteralContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(283);
				_la = _input.LA(1);
				if ( !(_la==T__32 || _la==ID) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				}
				break;
			case 3:
				_localctx = new MethodCallContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(284);
				_la = _input.LA(1);
				if ( !(_la==T__32 || _la==ID) ) {
				_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(285);
				match(T__30);
				setState(286);
				match(ID);
				setState(287);
				match(T__6);
				setState(288);
				expr();
				setState(289);
				match(T__10);
				}
				break;
			case 4:
				_localctx = new ConstructorCallContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(291);
				match(T__33);
				setState(292);
				match(ID);
				setState(293);
				match(T__6);
				setState(294);
				match(T__10);
				}
				break;
			case 5:
				_localctx = new ThisCallContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(295);
				match(T__32);
				}
				break;
			case 6:
				_localctx = new IntegerArrContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(296);
				match(T__33);
				setState(297);
				match(T__17);
				setState(298);
				match(T__8);
				setState(299);
				expr();
				setState(300);
				match(T__9);
				}
				break;
			case 7:
				_localctx = new ParenExpressionContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(302);
				match(T__6);
				setState(303);
				expr();
				setState(304);
				match(T__10);
				}
				break;
			case 8:
				_localctx = new BooleanLitContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(306);
				match(BOOLEAN_LIT);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 10:
			return condExpr_sempred((CondExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean condExpr_sempred(CondExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 3);
		case 1:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3*\u0138\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\3\2\3\2\7\2\37\n\2\f\2\16\2\"\13\2\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3\63\n\3\f\3"+
		"\16\3\66\13\3\3\3\7\39\n\3\f\3\16\3<\13\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4"+
		"\7\4E\n\4\f\4\16\4H\13\4\3\4\7\4K\n\4\f\4\16\4N\13\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\7\4W\n\4\f\4\16\4Z\13\4\3\4\7\4]\n\4\f\4\16\4`\13\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\6\4j\n\4\r\4\16\4k\7\4n\n\4\f\4\16\4q\13\4\5"+
		"\4s\n\4\3\4\3\4\5\4w\n\4\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3"+
		"\7\3\7\3\7\3\7\6\7\u0088\n\7\r\7\16\7\u0089\7\7\u008c\n\7\f\7\16\7\u008f"+
		"\13\7\5\7\u0091\n\7\3\7\3\7\3\7\7\7\u0096\n\7\f\7\16\7\u0099\13\7\3\7"+
		"\7\7\u009c\n\7\f\7\16\7\u009f\13\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\t"+
		"\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\5\n\u00b2\n\n\3\13\3\13\7\13\u00b6\n"+
		"\13\f\13\16\13\u00b9\13\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13"+
		"\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13"+
		"\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\5\13\u00dd\n\13"+
		"\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\5\f\u00e7\n\f\3\f\3\f\3\f\3\f\3\f\3\f"+
		"\7\f\u00ef\n\f\f\f\16\f\u00f2\13\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\7\r\u00fb"+
		"\n\r\f\r\16\r\u00fe\13\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3"+
		"\r\3\r\3\r\3\r\3\r\3\r\3\r\7\r\u0112\n\r\f\r\16\r\u0115\13\r\5\r\u0117"+
		"\n\r\3\r\3\r\5\r\u011b\n\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\5\16\u0136\n\16\3\16\2\3\26\17\2\4\6\b\n\f\16\20\22\24\26\30"+
		"\32\2\5\3\2%&\3\2\36\37\4\2##%%\u0158\2\34\3\2\2\2\4#\3\2\2\2\6v\3\2\2"+
		"\2\bx\3\2\2\2\n|\3\2\2\2\f\u0080\3\2\2\2\16\u00a5\3\2\2\2\20\u00a8\3\2"+
		"\2\2\22\u00b1\3\2\2\2\24\u00dc\3\2\2\2\26\u00e6\3\2\2\2\30\u011a\3\2\2"+
		"\2\32\u0135\3\2\2\2\34 \5\4\3\2\35\37\5\6\4\2\36\35\3\2\2\2\37\"\3\2\2"+
		"\2 \36\3\2\2\2 !\3\2\2\2!\3\3\2\2\2\" \3\2\2\2#$\7\3\2\2$%\7%\2\2%&\7"+
		"\4\2\2&\'\7\5\2\2\'(\7\6\2\2()\7\7\2\2)*\7\b\2\2*+\7\t\2\2+,\7\n\2\2,"+
		"-\7\13\2\2-.\7\f\2\2./\7%\2\2/\60\7\r\2\2\60\64\7\4\2\2\61\63\5\b\5\2"+
		"\62\61\3\2\2\2\63\66\3\2\2\2\64\62\3\2\2\2\64\65\3\2\2\2\65:\3\2\2\2\66"+
		"\64\3\2\2\2\679\5\24\13\28\67\3\2\2\29<\3\2\2\2:8\3\2\2\2:;\3\2\2\2;="+
		"\3\2\2\2<:\3\2\2\2=>\7\16\2\2>?\7\16\2\2?\5\3\2\2\2@A\7\3\2\2AB\7%\2\2"+
		"BF\7\4\2\2CE\5\n\6\2DC\3\2\2\2EH\3\2\2\2FD\3\2\2\2FG\3\2\2\2GL\3\2\2\2"+
		"HF\3\2\2\2IK\5\f\7\2JI\3\2\2\2KN\3\2\2\2LJ\3\2\2\2LM\3\2\2\2MO\3\2\2\2"+
		"NL\3\2\2\2Ow\7\16\2\2PQ\7\3\2\2QR\7%\2\2RS\7\17\2\2ST\7%\2\2TX\7\4\2\2"+
		"UW\5\n\6\2VU\3\2\2\2WZ\3\2\2\2XV\3\2\2\2XY\3\2\2\2Y^\3\2\2\2ZX\3\2\2\2"+
		"[]\5\f\7\2\\[\3\2\2\2]`\3\2\2\2^\\\3\2\2\2^_\3\2\2\2_a\3\2\2\2`^\3\2\2"+
		"\2aw\7\16\2\2bc\7\20\2\2cd\7\3\2\2de\7%\2\2er\7\t\2\2fo\5\20\t\2gi\7\21"+
		"\2\2hj\5\20\t\2ih\3\2\2\2jk\3\2\2\2ki\3\2\2\2kl\3\2\2\2ln\3\2\2\2mg\3"+
		"\2\2\2nq\3\2\2\2om\3\2\2\2op\3\2\2\2ps\3\2\2\2qo\3\2\2\2rf\3\2\2\2rs\3"+
		"\2\2\2st\3\2\2\2tu\7\r\2\2uw\7\22\2\2v@\3\2\2\2vP\3\2\2\2vb\3\2\2\2w\7"+
		"\3\2\2\2xy\5\22\n\2yz\7%\2\2z{\7\22\2\2{\t\3\2\2\2|}\5\22\n\2}~\7%\2\2"+
		"~\177\7\22\2\2\177\13\3\2\2\2\u0080\u0081\7\5\2\2\u0081\u0082\5\22\n\2"+
		"\u0082\u0083\7%\2\2\u0083\u0090\7\t\2\2\u0084\u008d\5\16\b\2\u0085\u0087"+
		"\7\21\2\2\u0086\u0088\5\16\b\2\u0087\u0086\3\2\2\2\u0088\u0089\3\2\2\2"+
		"\u0089\u0087\3\2\2\2\u0089\u008a\3\2\2\2\u008a\u008c\3\2\2\2\u008b\u0085"+
		"\3\2\2\2\u008c\u008f\3\2\2\2\u008d\u008b\3\2\2\2\u008d\u008e\3\2\2\2\u008e"+
		"\u0091\3\2\2\2\u008f\u008d\3\2\2\2\u0090\u0084\3\2\2\2\u0090\u0091\3\2"+
		"\2\2\u0091\u0092\3\2\2\2\u0092\u0093\7\r\2\2\u0093\u0097\7\4\2\2\u0094"+
		"\u0096\5\b\5\2\u0095\u0094\3\2\2\2\u0096\u0099\3\2\2\2\u0097\u0095\3\2"+
		"\2\2\u0097\u0098\3\2\2\2\u0098\u009d\3\2\2\2\u0099\u0097\3\2\2\2\u009a"+
		"\u009c\5\24\13\2\u009b\u009a\3\2\2\2\u009c\u009f\3\2\2\2\u009d\u009b\3"+
		"\2\2\2\u009d\u009e\3\2\2\2\u009e\u00a0\3\2\2\2\u009f\u009d\3\2\2\2\u00a0"+
		"\u00a1\7\23\2\2\u00a1\u00a2\5\30\r\2\u00a2\u00a3\7\22\2\2\u00a3\u00a4"+
		"\7\16\2\2\u00a4\r\3\2\2\2\u00a5\u00a6\5\22\n\2\u00a6\u00a7\7%\2\2\u00a7"+
		"\17\3\2\2\2\u00a8\u00a9\5\22\n\2\u00a9\u00aa\7%\2\2\u00aa\21\3\2\2\2\u00ab"+
		"\u00b2\7\24\2\2\u00ac\u00ad\7\24\2\2\u00ad\u00ae\7\13\2\2\u00ae\u00b2"+
		"\7\f\2\2\u00af\u00b2\7\25\2\2\u00b0\u00b2\7%\2\2\u00b1\u00ab\3\2\2\2\u00b1"+
		"\u00ac\3\2\2\2\u00b1\u00af\3\2\2\2\u00b1\u00b0\3\2\2\2\u00b2\23\3\2\2"+
		"\2\u00b3\u00b7\7\4\2\2\u00b4\u00b6\5\24\13\2\u00b5\u00b4\3\2\2\2\u00b6"+
		"\u00b9\3\2\2\2\u00b7\u00b5\3\2\2\2\u00b7\u00b8\3\2\2\2\u00b8\u00ba\3\2"+
		"\2\2\u00b9\u00b7\3\2\2\2\u00ba\u00dd\7\16\2\2\u00bb\u00bc\7\26\2\2\u00bc"+
		"\u00bd\7\t\2\2\u00bd\u00be\5\30\r\2\u00be\u00bf\7\r\2\2\u00bf\u00c0\7"+
		"\22\2\2\u00c0\u00dd\3\2\2\2\u00c1\u00c2\7%\2\2\u00c2\u00c3\7\27\2\2\u00c3"+
		"\u00c4\5\30\r\2\u00c4\u00c5\7\22\2\2\u00c5\u00dd\3\2\2\2\u00c6\u00c7\7"+
		"%\2\2\u00c7\u00c8\7\13\2\2\u00c8\u00c9\t\2\2\2\u00c9\u00ca\7\f\2\2\u00ca"+
		"\u00cb\7\27\2\2\u00cb\u00cc\5\30\r\2\u00cc\u00cd\7\22\2\2\u00cd\u00dd"+
		"\3\2\2\2\u00ce\u00cf\7\30\2\2\u00cf\u00d0\7\t\2\2\u00d0\u00d1\5\26\f\2"+
		"\u00d1\u00d2\7\r\2\2\u00d2\u00d3\5\24\13\2\u00d3\u00dd\3\2\2\2\u00d4\u00d5"+
		"\7\31\2\2\u00d5\u00d6\7\t\2\2\u00d6\u00d7\5\26\f\2\u00d7\u00d8\7\r\2\2"+
		"\u00d8\u00d9\5\24\13\2\u00d9\u00da\7\32\2\2\u00da\u00db\5\24\13\2\u00db"+
		"\u00dd\3\2\2\2\u00dc\u00b3\3\2\2\2\u00dc\u00bb\3\2\2\2\u00dc\u00c1\3\2"+
		"\2\2\u00dc\u00c6\3\2\2\2\u00dc\u00ce\3\2\2\2\u00dc\u00d4\3\2\2\2\u00dd"+
		"\25\3\2\2\2\u00de\u00df\b\f\1\2\u00df\u00e0\7\t\2\2\u00e0\u00e1\5\26\f"+
		"\2\u00e1\u00e2\7\r\2\2\u00e2\u00e7\3\2\2\2\u00e3\u00e4\7\33\2\2\u00e4"+
		"\u00e7\5\26\f\6\u00e5\u00e7\5\30\r\2\u00e6\u00de\3\2\2\2\u00e6\u00e3\3"+
		"\2\2\2\u00e6\u00e5\3\2\2\2\u00e7\u00f0\3\2\2\2\u00e8\u00e9\f\5\2\2\u00e9"+
		"\u00ea\7\34\2\2\u00ea\u00ef\5\26\f\6\u00eb\u00ec\f\4\2\2\u00ec\u00ed\7"+
		"\35\2\2\u00ed\u00ef\5\26\f\5\u00ee\u00e8\3\2\2\2\u00ee\u00eb\3\2\2\2\u00ef"+
		"\u00f2\3\2\2\2\u00f0\u00ee\3\2\2\2\u00f0\u00f1\3\2\2\2\u00f1\27\3\2\2"+
		"\2\u00f2\u00f0\3\2\2\2\u00f3\u00fc\5\32\16\2\u00f4\u00f5\7\36\2\2\u00f5"+
		"\u00fb\5\32\16\2\u00f6\u00f7\7\37\2\2\u00f7\u00fb\5\32\16\2\u00f8\u00f9"+
		"\7 \2\2\u00f9\u00fb\5\32\16\2\u00fa\u00f4\3\2\2\2\u00fa\u00f6\3\2\2\2"+
		"\u00fa\u00f8\3\2\2\2\u00fb\u00fe\3\2\2\2\u00fc\u00fa\3\2\2\2\u00fc\u00fd"+
		"\3\2\2\2\u00fd\u011b\3\2\2\2\u00fe\u00fc\3\2\2\2\u00ff\u0100\t\3\2\2\u0100"+
		"\u011b\5\32\16\2\u0101\u0102\5\32\16\2\u0102\u0103\7\13\2\2\u0103\u0104"+
		"\5\30\r\2\u0104\u0105\7\f\2\2\u0105\u011b\3\2\2\2\u0106\u0107\5\32\16"+
		"\2\u0107\u0108\7!\2\2\u0108\u0109\7\"\2\2\u0109\u011b\3\2\2\2\u010a\u010b"+
		"\5\32\16\2\u010b\u010c\7!\2\2\u010c\u010d\7%\2\2\u010d\u0116\7\t\2\2\u010e"+
		"\u0113\5\26\f\2\u010f\u0110\7\21\2\2\u0110\u0112\5\26\f\2\u0111\u010f"+
		"\3\2\2\2\u0112\u0115\3\2\2\2\u0113\u0111\3\2\2\2\u0113\u0114\3\2\2\2\u0114"+
		"\u0117\3\2\2\2\u0115\u0113\3\2\2\2\u0116\u010e\3\2\2\2\u0116\u0117\3\2"+
		"\2\2\u0117\u0118\3\2\2\2\u0118\u0119\7\r\2\2\u0119\u011b\3\2\2\2\u011a"+
		"\u00f3\3\2\2\2\u011a\u00ff\3\2\2\2\u011a\u0101\3\2\2\2\u011a\u0106\3\2"+
		"\2\2\u011a\u010a\3\2\2\2\u011b\31\3\2\2\2\u011c\u0136\7&\2\2\u011d\u0136"+
		"\t\4\2\2\u011e\u011f\t\4\2\2\u011f\u0120\7!\2\2\u0120\u0121\7%\2\2\u0121"+
		"\u0122\7\t\2\2\u0122\u0123\5\30\r\2\u0123\u0124\7\r\2\2\u0124\u0136\3"+
		"\2\2\2\u0125\u0126\7$\2\2\u0126\u0127\7%\2\2\u0127\u0128\7\t\2\2\u0128"+
		"\u0136\7\r\2\2\u0129\u0136\7#\2\2\u012a\u012b\7$\2\2\u012b\u012c\7\24"+
		"\2\2\u012c\u012d\7\13\2\2\u012d\u012e\5\30\r\2\u012e\u012f\7\f\2\2\u012f"+
		"\u0136\3\2\2\2\u0130\u0131\7\t\2\2\u0131\u0132\5\30\r\2\u0132\u0133\7"+
		"\r\2\2\u0133\u0136\3\2\2\2\u0134\u0136\7\'\2\2\u0135\u011c\3\2\2\2\u0135"+
		"\u011d\3\2\2\2\u0135\u011e\3\2\2\2\u0135\u0125\3\2\2\2\u0135\u0129\3\2"+
		"\2\2\u0135\u012a\3\2\2\2\u0135\u0130\3\2\2\2\u0135\u0134\3\2\2\2\u0136"+
		"\33\3\2\2\2\36 \64:FLX^korv\u0089\u008d\u0090\u0097\u009d\u00b1\u00b7"+
		"\u00dc\u00e6\u00ee\u00f0\u00fa\u00fc\u0113\u0116\u011a\u0135";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}