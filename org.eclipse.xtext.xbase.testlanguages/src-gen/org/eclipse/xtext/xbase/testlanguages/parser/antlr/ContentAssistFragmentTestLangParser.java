/*
 * generated by Xtext
 */
package org.eclipse.xtext.xbase.testlanguages.parser.antlr;

import com.google.inject.Inject;
import org.eclipse.xtext.parser.antlr.AbstractAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.xbase.testlanguages.parser.antlr.internal.InternalContentAssistFragmentTestLangParser;
import org.eclipse.xtext.xbase.testlanguages.services.ContentAssistFragmentTestLangGrammarAccess;

public class ContentAssistFragmentTestLangParser extends AbstractAntlrParser {

	@Inject
	private ContentAssistFragmentTestLangGrammarAccess grammarAccess;

	@Override
	protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT");
	}
	

	@Override
	protected InternalContentAssistFragmentTestLangParser createParser(XtextTokenStream stream) {
		return new InternalContentAssistFragmentTestLangParser(stream, getGrammarAccess());
	}

	@Override 
	protected String getDefaultRuleName() {
		return "ContentAssistFragmentTestLanguageRoot";
	}

	public ContentAssistFragmentTestLangGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}

	public void setGrammarAccess(ContentAssistFragmentTestLangGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}
