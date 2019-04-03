/*******************************************************************************
 * Copyright (c) 2010, 2017 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
grammar InternalXbase;

options {
	superClass=AbstractInternalAntlrParser;
}

@lexer::header {
package org.eclipse.xtext.xbase.parser.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;
}

@parser::header {
package org.eclipse.xtext.xbase.parser.antlr.internal;

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import org.eclipse.xtext.xbase.services.XbaseGrammarAccess;

}

@parser::members {

 	private XbaseGrammarAccess grammarAccess;

    public InternalXbaseParser(TokenStream input, XbaseGrammarAccess grammarAccess) {
        this(input);
        this.grammarAccess = grammarAccess;
        registerRules(grammarAccess.getGrammar());
    }

    @Override
    protected String getFirstRuleName() {
    	return "XExpression";
   	}

   	@Override
   	protected XbaseGrammarAccess getGrammarAccess() {
   		return grammarAccess;
   	}

}

@rulecatch {
    catch (RecognitionException re) {
        recover(input,re);
        appendSkippedTokens();
    }
}

// Entry rule entryRuleXExpression
entryRuleXExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXExpressionRule()); }
	iv_ruleXExpression=ruleXExpression
	{ $current=$iv_ruleXExpression.current; }
	EOF;

// Rule XExpression
ruleXExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	{
		newCompositeNode(grammarAccess.getXExpressionAccess().getXAssignmentParserRuleCall());
	}
	this_XAssignment_0=ruleXAssignment
	{
		$current = $this_XAssignment_0.current;
		afterParserOrEnumRuleCall();
	}
;

// Entry rule entryRuleXAssignment
entryRuleXAssignment returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXAssignmentRule()); }
	iv_ruleXAssignment=ruleXAssignment
	{ $current=$iv_ruleXAssignment.current; }
	EOF;

// Rule XAssignment
ruleXAssignment returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				{
					$current = forceCreateModelElement(
						grammarAccess.getXAssignmentAccess().getXAssignmentAction_0_0(),
						$current);
				}
			)
			(
				(
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getXAssignmentRule());
						}
					}
					{
						newCompositeNode(grammarAccess.getXAssignmentAccess().getFeatureJvmIdentifiableElementCrossReference_0_1_0());
					}
					ruleFeatureCallID
					{
						afterParserOrEnumRuleCall();
					}
				)
			)
			{
				newCompositeNode(grammarAccess.getXAssignmentAccess().getOpSingleAssignParserRuleCall_0_2());
			}
			ruleOpSingleAssign
			{
				afterParserOrEnumRuleCall();
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getXAssignmentAccess().getValueXAssignmentParserRuleCall_0_3_0());
					}
					lv_value_3_0=ruleXAssignment
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getXAssignmentRule());
						}
						set(
							$current,
							"value",
							lv_value_3_0,
							"org.eclipse.xtext.xbase.Xbase.XAssignment");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
		    |
		(
			{
				newCompositeNode(grammarAccess.getXAssignmentAccess().getXOrExpressionParserRuleCall_1_0());
			}
			this_XOrExpression_4=ruleXOrExpression
			{
				$current = $this_XOrExpression_4.current;
				afterParserOrEnumRuleCall();
			}
			(
				(
					((
						(
						)
						(
							(
								ruleOpMultiAssign
							)
						)
					)
					)=>
					(
						(
							{
								$current = forceCreateModelElementAndSet(
									grammarAccess.getXAssignmentAccess().getXBinaryOperationLeftOperandAction_1_1_0_0_0(),
									$current);
							}
						)
						(
							(
								{
									if ($current==null) {
										$current = createModelElement(grammarAccess.getXAssignmentRule());
									}
								}
								{
									newCompositeNode(grammarAccess.getXAssignmentAccess().getFeatureJvmIdentifiableElementCrossReference_1_1_0_0_1_0());
								}
								ruleOpMultiAssign
								{
									afterParserOrEnumRuleCall();
								}
							)
						)
					)
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getXAssignmentAccess().getRightOperandXAssignmentParserRuleCall_1_1_1_0());
						}
						lv_rightOperand_7_0=ruleXAssignment
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getXAssignmentRule());
							}
							set(
								$current,
								"rightOperand",
								lv_rightOperand_7_0,
								"org.eclipse.xtext.xbase.Xbase.XAssignment");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)?
		)
	)
;

// Entry rule entryRuleOpSingleAssign
entryRuleOpSingleAssign returns [String current=null]:
	{ newCompositeNode(grammarAccess.getOpSingleAssignRule()); }
	iv_ruleOpSingleAssign=ruleOpSingleAssign
	{ $current=$iv_ruleOpSingleAssign.current.getText(); }
	EOF;

// Rule OpSingleAssign
ruleOpSingleAssign returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	kw='='
	{
		$current.merge(kw);
		newLeafNode(kw, grammarAccess.getOpSingleAssignAccess().getEqualsSignKeyword());
	}
;

// Entry rule entryRuleOpMultiAssign
entryRuleOpMultiAssign returns [String current=null]:
	{ newCompositeNode(grammarAccess.getOpMultiAssignRule()); }
	iv_ruleOpMultiAssign=ruleOpMultiAssign
	{ $current=$iv_ruleOpMultiAssign.current.getText(); }
	EOF;

// Rule OpMultiAssign
ruleOpMultiAssign returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		kw='+='
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getOpMultiAssignAccess().getPlusSignEqualsSignKeyword_0());
		}
		    |
		kw='-='
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getOpMultiAssignAccess().getHyphenMinusEqualsSignKeyword_1());
		}
		    |
		kw='*='
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getOpMultiAssignAccess().getAsteriskEqualsSignKeyword_2());
		}
		    |
		kw='/='
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getOpMultiAssignAccess().getSolidusEqualsSignKeyword_3());
		}
		    |
		kw='%='
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getOpMultiAssignAccess().getPercentSignEqualsSignKeyword_4());
		}
		    |
		(
			kw='<'
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getOpMultiAssignAccess().getLessThanSignKeyword_5_0());
			}
			kw='<'
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getOpMultiAssignAccess().getLessThanSignKeyword_5_1());
			}
			kw='='
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getOpMultiAssignAccess().getEqualsSignKeyword_5_2());
			}
		)
		    |
		(
			kw='>'
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getOpMultiAssignAccess().getGreaterThanSignKeyword_6_0());
			}
			(
				kw='>'
				{
					$current.merge(kw);
					newLeafNode(kw, grammarAccess.getOpMultiAssignAccess().getGreaterThanSignKeyword_6_1());
				}
			)?
			kw='>='
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getOpMultiAssignAccess().getGreaterThanSignEqualsSignKeyword_6_2());
			}
		)
	)
;

// Entry rule entryRuleXOrExpression
entryRuleXOrExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXOrExpressionRule()); }
	iv_ruleXOrExpression=ruleXOrExpression
	{ $current=$iv_ruleXOrExpression.current; }
	EOF;

// Rule XOrExpression
ruleXOrExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getXOrExpressionAccess().getXAndExpressionParserRuleCall_0());
		}
		this_XAndExpression_0=ruleXAndExpression
		{
			$current = $this_XAndExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				((
					(
					)
					(
						(
							ruleOpOr
						)
					)
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getXOrExpressionAccess().getXBinaryOperationLeftOperandAction_1_0_0_0(),
								$current);
						}
					)
					(
						(
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getXOrExpressionRule());
								}
							}
							{
								newCompositeNode(grammarAccess.getXOrExpressionAccess().getFeatureJvmIdentifiableElementCrossReference_1_0_0_1_0());
							}
							ruleOpOr
							{
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getXOrExpressionAccess().getRightOperandXAndExpressionParserRuleCall_1_1_0());
					}
					lv_rightOperand_3_0=ruleXAndExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getXOrExpressionRule());
						}
						set(
							$current,
							"rightOperand",
							lv_rightOperand_3_0,
							"org.eclipse.xtext.xbase.Xbase.XAndExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;

// Entry rule entryRuleOpOr
entryRuleOpOr returns [String current=null]:
	{ newCompositeNode(grammarAccess.getOpOrRule()); }
	iv_ruleOpOr=ruleOpOr
	{ $current=$iv_ruleOpOr.current.getText(); }
	EOF;

// Rule OpOr
ruleOpOr returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	kw='||'
	{
		$current.merge(kw);
		newLeafNode(kw, grammarAccess.getOpOrAccess().getVerticalLineVerticalLineKeyword());
	}
;

// Entry rule entryRuleXAndExpression
entryRuleXAndExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXAndExpressionRule()); }
	iv_ruleXAndExpression=ruleXAndExpression
	{ $current=$iv_ruleXAndExpression.current; }
	EOF;

// Rule XAndExpression
ruleXAndExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getXAndExpressionAccess().getXEqualityExpressionParserRuleCall_0());
		}
		this_XEqualityExpression_0=ruleXEqualityExpression
		{
			$current = $this_XEqualityExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				((
					(
					)
					(
						(
							ruleOpAnd
						)
					)
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getXAndExpressionAccess().getXBinaryOperationLeftOperandAction_1_0_0_0(),
								$current);
						}
					)
					(
						(
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getXAndExpressionRule());
								}
							}
							{
								newCompositeNode(grammarAccess.getXAndExpressionAccess().getFeatureJvmIdentifiableElementCrossReference_1_0_0_1_0());
							}
							ruleOpAnd
							{
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getXAndExpressionAccess().getRightOperandXEqualityExpressionParserRuleCall_1_1_0());
					}
					lv_rightOperand_3_0=ruleXEqualityExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getXAndExpressionRule());
						}
						set(
							$current,
							"rightOperand",
							lv_rightOperand_3_0,
							"org.eclipse.xtext.xbase.Xbase.XEqualityExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;

// Entry rule entryRuleOpAnd
entryRuleOpAnd returns [String current=null]:
	{ newCompositeNode(grammarAccess.getOpAndRule()); }
	iv_ruleOpAnd=ruleOpAnd
	{ $current=$iv_ruleOpAnd.current.getText(); }
	EOF;

// Rule OpAnd
ruleOpAnd returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	kw='&&'
	{
		$current.merge(kw);
		newLeafNode(kw, grammarAccess.getOpAndAccess().getAmpersandAmpersandKeyword());
	}
;

// Entry rule entryRuleXEqualityExpression
entryRuleXEqualityExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXEqualityExpressionRule()); }
	iv_ruleXEqualityExpression=ruleXEqualityExpression
	{ $current=$iv_ruleXEqualityExpression.current; }
	EOF;

// Rule XEqualityExpression
ruleXEqualityExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getXEqualityExpressionAccess().getXRelationalExpressionParserRuleCall_0());
		}
		this_XRelationalExpression_0=ruleXRelationalExpression
		{
			$current = $this_XRelationalExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				((
					(
					)
					(
						(
							ruleOpEquality
						)
					)
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getXEqualityExpressionAccess().getXBinaryOperationLeftOperandAction_1_0_0_0(),
								$current);
						}
					)
					(
						(
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getXEqualityExpressionRule());
								}
							}
							{
								newCompositeNode(grammarAccess.getXEqualityExpressionAccess().getFeatureJvmIdentifiableElementCrossReference_1_0_0_1_0());
							}
							ruleOpEquality
							{
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getXEqualityExpressionAccess().getRightOperandXRelationalExpressionParserRuleCall_1_1_0());
					}
					lv_rightOperand_3_0=ruleXRelationalExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getXEqualityExpressionRule());
						}
						set(
							$current,
							"rightOperand",
							lv_rightOperand_3_0,
							"org.eclipse.xtext.xbase.Xbase.XRelationalExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;

// Entry rule entryRuleOpEquality
entryRuleOpEquality returns [String current=null]:
	{ newCompositeNode(grammarAccess.getOpEqualityRule()); }
	iv_ruleOpEquality=ruleOpEquality
	{ $current=$iv_ruleOpEquality.current.getText(); }
	EOF;

// Rule OpEquality
ruleOpEquality returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		kw='=='
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getOpEqualityAccess().getEqualsSignEqualsSignKeyword_0());
		}
		    |
		kw='!='
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getOpEqualityAccess().getExclamationMarkEqualsSignKeyword_1());
		}
		    |
		kw='==='
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getOpEqualityAccess().getEqualsSignEqualsSignEqualsSignKeyword_2());
		}
		    |
		kw='!=='
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getOpEqualityAccess().getExclamationMarkEqualsSignEqualsSignKeyword_3());
		}
	)
;

// Entry rule entryRuleXRelationalExpression
entryRuleXRelationalExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXRelationalExpressionRule()); }
	iv_ruleXRelationalExpression=ruleXRelationalExpression
	{ $current=$iv_ruleXRelationalExpression.current; }
	EOF;

// Rule XRelationalExpression
ruleXRelationalExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getXRelationalExpressionAccess().getXOtherOperatorExpressionParserRuleCall_0());
		}
		this_XOtherOperatorExpression_0=ruleXOtherOperatorExpression
		{
			$current = $this_XOtherOperatorExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				(
					((
						(
						)
						'instanceof'
					)
					)=>
					(
						(
							{
								$current = forceCreateModelElementAndSet(
									grammarAccess.getXRelationalExpressionAccess().getXInstanceOfExpressionExpressionAction_1_0_0_0_0(),
									$current);
							}
						)
						otherlv_2='instanceof'
						{
							newLeafNode(otherlv_2, grammarAccess.getXRelationalExpressionAccess().getInstanceofKeyword_1_0_0_0_1());
						}
					)
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getXRelationalExpressionAccess().getTypeJvmTypeReferenceParserRuleCall_1_0_1_0());
						}
						lv_type_3_0=ruleJvmTypeReference
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getXRelationalExpressionRule());
							}
							set(
								$current,
								"type",
								lv_type_3_0,
								"org.eclipse.xtext.xbase.Xtype.JvmTypeReference");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
			    |
			(
				(
					((
						(
						)
						(
							(
								ruleOpCompare
							)
						)
					)
					)=>
					(
						(
							{
								$current = forceCreateModelElementAndSet(
									grammarAccess.getXRelationalExpressionAccess().getXBinaryOperationLeftOperandAction_1_1_0_0_0(),
									$current);
							}
						)
						(
							(
								{
									if ($current==null) {
										$current = createModelElement(grammarAccess.getXRelationalExpressionRule());
									}
								}
								{
									newCompositeNode(grammarAccess.getXRelationalExpressionAccess().getFeatureJvmIdentifiableElementCrossReference_1_1_0_0_1_0());
								}
								ruleOpCompare
								{
									afterParserOrEnumRuleCall();
								}
							)
						)
					)
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getXRelationalExpressionAccess().getRightOperandXOtherOperatorExpressionParserRuleCall_1_1_1_0());
						}
						lv_rightOperand_6_0=ruleXOtherOperatorExpression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getXRelationalExpressionRule());
							}
							set(
								$current,
								"rightOperand",
								lv_rightOperand_6_0,
								"org.eclipse.xtext.xbase.Xbase.XOtherOperatorExpression");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
		)*
	)
;

// Entry rule entryRuleOpCompare
entryRuleOpCompare returns [String current=null]:
	{ newCompositeNode(grammarAccess.getOpCompareRule()); }
	iv_ruleOpCompare=ruleOpCompare
	{ $current=$iv_ruleOpCompare.current.getText(); }
	EOF;

// Rule OpCompare
ruleOpCompare returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		kw='>='
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getOpCompareAccess().getGreaterThanSignEqualsSignKeyword_0());
		}
		    |
		(
			kw='<'
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getOpCompareAccess().getLessThanSignKeyword_1_0());
			}
			kw='='
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getOpCompareAccess().getEqualsSignKeyword_1_1());
			}
		)
		    |
		kw='>'
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getOpCompareAccess().getGreaterThanSignKeyword_2());
		}
		    |
		kw='<'
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getOpCompareAccess().getLessThanSignKeyword_3());
		}
	)
;

// Entry rule entryRuleXOtherOperatorExpression
entryRuleXOtherOperatorExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXOtherOperatorExpressionRule()); }
	iv_ruleXOtherOperatorExpression=ruleXOtherOperatorExpression
	{ $current=$iv_ruleXOtherOperatorExpression.current; }
	EOF;

// Rule XOtherOperatorExpression
ruleXOtherOperatorExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getXOtherOperatorExpressionAccess().getXAdditiveExpressionParserRuleCall_0());
		}
		this_XAdditiveExpression_0=ruleXAdditiveExpression
		{
			$current = $this_XAdditiveExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				((
					(
					)
					(
						(
							ruleOpOther
						)
					)
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getXOtherOperatorExpressionAccess().getXBinaryOperationLeftOperandAction_1_0_0_0(),
								$current);
						}
					)
					(
						(
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getXOtherOperatorExpressionRule());
								}
							}
							{
								newCompositeNode(grammarAccess.getXOtherOperatorExpressionAccess().getFeatureJvmIdentifiableElementCrossReference_1_0_0_1_0());
							}
							ruleOpOther
							{
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getXOtherOperatorExpressionAccess().getRightOperandXAdditiveExpressionParserRuleCall_1_1_0());
					}
					lv_rightOperand_3_0=ruleXAdditiveExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getXOtherOperatorExpressionRule());
						}
						set(
							$current,
							"rightOperand",
							lv_rightOperand_3_0,
							"org.eclipse.xtext.xbase.Xbase.XAdditiveExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;

// Entry rule entryRuleOpOther
entryRuleOpOther returns [String current=null]:
	{ newCompositeNode(grammarAccess.getOpOtherRule()); }
	iv_ruleOpOther=ruleOpOther
	{ $current=$iv_ruleOpOther.current.getText(); }
	EOF;

// Rule OpOther
ruleOpOther returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		kw='->'
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getOpOtherAccess().getHyphenMinusGreaterThanSignKeyword_0());
		}
		    |
		kw='..<'
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getOpOtherAccess().getFullStopFullStopLessThanSignKeyword_1());
		}
		    |
		(
			kw='>'
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getOpOtherAccess().getGreaterThanSignKeyword_2_0());
			}
			kw='..'
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getOpOtherAccess().getFullStopFullStopKeyword_2_1());
			}
		)
		    |
		kw='..'
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getOpOtherAccess().getFullStopFullStopKeyword_3());
		}
		    |
		kw='=>'
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getOpOtherAccess().getEqualsSignGreaterThanSignKeyword_4());
		}
		    |
		(
			kw='>'
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getOpOtherAccess().getGreaterThanSignKeyword_5_0());
			}
			(
				(
					((
						'>'
						'>'
					)
					)=>
					(
						kw='>'
						{
							$current.merge(kw);
							newLeafNode(kw, grammarAccess.getOpOtherAccess().getGreaterThanSignKeyword_5_1_0_0_0());
						}
						kw='>'
						{
							$current.merge(kw);
							newLeafNode(kw, grammarAccess.getOpOtherAccess().getGreaterThanSignKeyword_5_1_0_0_1());
						}
					)
				)
				    |
				kw='>'
				{
					$current.merge(kw);
					newLeafNode(kw, grammarAccess.getOpOtherAccess().getGreaterThanSignKeyword_5_1_1());
				}
			)
		)
		    |
		(
			kw='<'
			{
				$current.merge(kw);
				newLeafNode(kw, grammarAccess.getOpOtherAccess().getLessThanSignKeyword_6_0());
			}
			(
				(
					((
						'<'
						'<'
					)
					)=>
					(
						kw='<'
						{
							$current.merge(kw);
							newLeafNode(kw, grammarAccess.getOpOtherAccess().getLessThanSignKeyword_6_1_0_0_0());
						}
						kw='<'
						{
							$current.merge(kw);
							newLeafNode(kw, grammarAccess.getOpOtherAccess().getLessThanSignKeyword_6_1_0_0_1());
						}
					)
				)
				    |
				kw='<'
				{
					$current.merge(kw);
					newLeafNode(kw, grammarAccess.getOpOtherAccess().getLessThanSignKeyword_6_1_1());
				}
				    |
				kw='=>'
				{
					$current.merge(kw);
					newLeafNode(kw, grammarAccess.getOpOtherAccess().getEqualsSignGreaterThanSignKeyword_6_1_2());
				}
			)
		)
		    |
		kw='<>'
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getOpOtherAccess().getLessThanSignGreaterThanSignKeyword_7());
		}
		    |
		kw='?:'
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getOpOtherAccess().getQuestionMarkColonKeyword_8());
		}
	)
;

// Entry rule entryRuleXAdditiveExpression
entryRuleXAdditiveExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXAdditiveExpressionRule()); }
	iv_ruleXAdditiveExpression=ruleXAdditiveExpression
	{ $current=$iv_ruleXAdditiveExpression.current; }
	EOF;

// Rule XAdditiveExpression
ruleXAdditiveExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getXAdditiveExpressionAccess().getXMultiplicativeExpressionParserRuleCall_0());
		}
		this_XMultiplicativeExpression_0=ruleXMultiplicativeExpression
		{
			$current = $this_XMultiplicativeExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				((
					(
					)
					(
						(
							ruleOpAdd
						)
					)
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getXAdditiveExpressionAccess().getXBinaryOperationLeftOperandAction_1_0_0_0(),
								$current);
						}
					)
					(
						(
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getXAdditiveExpressionRule());
								}
							}
							{
								newCompositeNode(grammarAccess.getXAdditiveExpressionAccess().getFeatureJvmIdentifiableElementCrossReference_1_0_0_1_0());
							}
							ruleOpAdd
							{
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getXAdditiveExpressionAccess().getRightOperandXMultiplicativeExpressionParserRuleCall_1_1_0());
					}
					lv_rightOperand_3_0=ruleXMultiplicativeExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getXAdditiveExpressionRule());
						}
						set(
							$current,
							"rightOperand",
							lv_rightOperand_3_0,
							"org.eclipse.xtext.xbase.Xbase.XMultiplicativeExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;

// Entry rule entryRuleOpAdd
entryRuleOpAdd returns [String current=null]:
	{ newCompositeNode(grammarAccess.getOpAddRule()); }
	iv_ruleOpAdd=ruleOpAdd
	{ $current=$iv_ruleOpAdd.current.getText(); }
	EOF;

// Rule OpAdd
ruleOpAdd returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		kw='+'
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getOpAddAccess().getPlusSignKeyword_0());
		}
		    |
		kw='-'
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getOpAddAccess().getHyphenMinusKeyword_1());
		}
	)
;

// Entry rule entryRuleXMultiplicativeExpression
entryRuleXMultiplicativeExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXMultiplicativeExpressionRule()); }
	iv_ruleXMultiplicativeExpression=ruleXMultiplicativeExpression
	{ $current=$iv_ruleXMultiplicativeExpression.current; }
	EOF;

// Rule XMultiplicativeExpression
ruleXMultiplicativeExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getXMultiplicativeExpressionAccess().getXUnaryOperationParserRuleCall_0());
		}
		this_XUnaryOperation_0=ruleXUnaryOperation
		{
			$current = $this_XUnaryOperation_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				((
					(
					)
					(
						(
							ruleOpMulti
						)
					)
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getXMultiplicativeExpressionAccess().getXBinaryOperationLeftOperandAction_1_0_0_0(),
								$current);
						}
					)
					(
						(
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getXMultiplicativeExpressionRule());
								}
							}
							{
								newCompositeNode(grammarAccess.getXMultiplicativeExpressionAccess().getFeatureJvmIdentifiableElementCrossReference_1_0_0_1_0());
							}
							ruleOpMulti
							{
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getXMultiplicativeExpressionAccess().getRightOperandXUnaryOperationParserRuleCall_1_1_0());
					}
					lv_rightOperand_3_0=ruleXUnaryOperation
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getXMultiplicativeExpressionRule());
						}
						set(
							$current,
							"rightOperand",
							lv_rightOperand_3_0,
							"org.eclipse.xtext.xbase.Xbase.XUnaryOperation");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;

// Entry rule entryRuleOpMulti
entryRuleOpMulti returns [String current=null]:
	{ newCompositeNode(grammarAccess.getOpMultiRule()); }
	iv_ruleOpMulti=ruleOpMulti
	{ $current=$iv_ruleOpMulti.current.getText(); }
	EOF;

// Rule OpMulti
ruleOpMulti returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		kw='*'
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getOpMultiAccess().getAsteriskKeyword_0());
		}
		    |
		kw='**'
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getOpMultiAccess().getAsteriskAsteriskKeyword_1());
		}
		    |
		kw='/'
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getOpMultiAccess().getSolidusKeyword_2());
		}
		    |
		kw='%'
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getOpMultiAccess().getPercentSignKeyword_3());
		}
	)
;

// Entry rule entryRuleXUnaryOperation
entryRuleXUnaryOperation returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXUnaryOperationRule()); }
	iv_ruleXUnaryOperation=ruleXUnaryOperation
	{ $current=$iv_ruleXUnaryOperation.current; }
	EOF;

// Rule XUnaryOperation
ruleXUnaryOperation returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				{
					$current = forceCreateModelElement(
						grammarAccess.getXUnaryOperationAccess().getXUnaryOperationAction_0_0(),
						$current);
				}
			)
			(
				(
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getXUnaryOperationRule());
						}
					}
					{
						newCompositeNode(grammarAccess.getXUnaryOperationAccess().getFeatureJvmIdentifiableElementCrossReference_0_1_0());
					}
					ruleOpUnary
					{
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getXUnaryOperationAccess().getOperandXUnaryOperationParserRuleCall_0_2_0());
					}
					lv_operand_2_0=ruleXUnaryOperation
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getXUnaryOperationRule());
						}
						set(
							$current,
							"operand",
							lv_operand_2_0,
							"org.eclipse.xtext.xbase.Xbase.XUnaryOperation");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
		    |
		{
			newCompositeNode(grammarAccess.getXUnaryOperationAccess().getXCastedExpressionParserRuleCall_1());
		}
		this_XCastedExpression_3=ruleXCastedExpression
		{
			$current = $this_XCastedExpression_3.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleOpUnary
entryRuleOpUnary returns [String current=null]:
	{ newCompositeNode(grammarAccess.getOpUnaryRule()); }
	iv_ruleOpUnary=ruleOpUnary
	{ $current=$iv_ruleOpUnary.current.getText(); }
	EOF;

// Rule OpUnary
ruleOpUnary returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		kw='!'
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getOpUnaryAccess().getExclamationMarkKeyword_0());
		}
		    |
		kw='-'
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getOpUnaryAccess().getHyphenMinusKeyword_1());
		}
		    |
		kw='+'
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getOpUnaryAccess().getPlusSignKeyword_2());
		}
	)
;

// Entry rule entryRuleXCastedExpression
entryRuleXCastedExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXCastedExpressionRule()); }
	iv_ruleXCastedExpression=ruleXCastedExpression
	{ $current=$iv_ruleXCastedExpression.current; }
	EOF;

// Rule XCastedExpression
ruleXCastedExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getXCastedExpressionAccess().getXPostfixOperationParserRuleCall_0());
		}
		this_XPostfixOperation_0=ruleXPostfixOperation
		{
			$current = $this_XPostfixOperation_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				((
					(
					)
					'as'
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getXCastedExpressionAccess().getXCastedExpressionTargetAction_1_0_0_0(),
								$current);
						}
					)
					otherlv_2='as'
					{
						newLeafNode(otherlv_2, grammarAccess.getXCastedExpressionAccess().getAsKeyword_1_0_0_1());
					}
				)
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getXCastedExpressionAccess().getTypeJvmTypeReferenceParserRuleCall_1_1_0());
					}
					lv_type_3_0=ruleJvmTypeReference
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getXCastedExpressionRule());
						}
						set(
							$current,
							"type",
							lv_type_3_0,
							"org.eclipse.xtext.xbase.Xtype.JvmTypeReference");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)*
	)
;

// Entry rule entryRuleXPostfixOperation
entryRuleXPostfixOperation returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXPostfixOperationRule()); }
	iv_ruleXPostfixOperation=ruleXPostfixOperation
	{ $current=$iv_ruleXPostfixOperation.current; }
	EOF;

// Rule XPostfixOperation
ruleXPostfixOperation returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getXPostfixOperationAccess().getXMemberFeatureCallParserRuleCall_0());
		}
		this_XMemberFeatureCall_0=ruleXMemberFeatureCall
		{
			$current = $this_XMemberFeatureCall_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			((
				(
				)
				(
					(
						ruleOpPostfix
					)
				)
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElementAndSet(
							grammarAccess.getXPostfixOperationAccess().getXPostfixOperationOperandAction_1_0_0(),
							$current);
					}
				)
				(
					(
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getXPostfixOperationRule());
							}
						}
						{
							newCompositeNode(grammarAccess.getXPostfixOperationAccess().getFeatureJvmIdentifiableElementCrossReference_1_0_1_0());
						}
						ruleOpPostfix
						{
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
		)?
	)
;

// Entry rule entryRuleOpPostfix
entryRuleOpPostfix returns [String current=null]:
	{ newCompositeNode(grammarAccess.getOpPostfixRule()); }
	iv_ruleOpPostfix=ruleOpPostfix
	{ $current=$iv_ruleOpPostfix.current.getText(); }
	EOF;

// Rule OpPostfix
ruleOpPostfix returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		kw='++'
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getOpPostfixAccess().getPlusSignPlusSignKeyword_0());
		}
		    |
		kw='--'
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getOpPostfixAccess().getHyphenMinusHyphenMinusKeyword_1());
		}
	)
;

// Entry rule entryRuleXMemberFeatureCall
entryRuleXMemberFeatureCall returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXMemberFeatureCallRule()); }
	iv_ruleXMemberFeatureCall=ruleXMemberFeatureCall
	{ $current=$iv_ruleXMemberFeatureCall.current; }
	EOF;

// Rule XMemberFeatureCall
ruleXMemberFeatureCall returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getXMemberFeatureCallAccess().getXPrimaryExpressionParserRuleCall_0());
		}
		this_XPrimaryExpression_0=ruleXPrimaryExpression
		{
			$current = $this_XPrimaryExpression_0.current;
			afterParserOrEnumRuleCall();
		}
		(
			(
				(
					((
						(
						)
						(
							'.'
							    |
							(
								(
									'::'
								)
							)
						)
						(
							(
								ruleFeatureCallID
							)
						)
						ruleOpSingleAssign
					)
					)=>
					(
						(
							{
								$current = forceCreateModelElementAndSet(
									grammarAccess.getXMemberFeatureCallAccess().getXAssignmentAssignableAction_1_0_0_0_0(),
									$current);
							}
						)
						(
							otherlv_2='.'
							{
								newLeafNode(otherlv_2, grammarAccess.getXMemberFeatureCallAccess().getFullStopKeyword_1_0_0_0_1_0());
							}
							    |
							(
								(
									lv_explicitStatic_3_0='::'
									{
										newLeafNode(lv_explicitStatic_3_0, grammarAccess.getXMemberFeatureCallAccess().getExplicitStaticColonColonKeyword_1_0_0_0_1_1_0());
									}
									{
										if ($current==null) {
											$current = createModelElement(grammarAccess.getXMemberFeatureCallRule());
										}
										setWithLastConsumed($current, "explicitStatic", true, "::");
									}
								)
							)
						)
						(
							(
								{
									if ($current==null) {
										$current = createModelElement(grammarAccess.getXMemberFeatureCallRule());
									}
								}
								{
									newCompositeNode(grammarAccess.getXMemberFeatureCallAccess().getFeatureJvmIdentifiableElementCrossReference_1_0_0_0_2_0());
								}
								ruleFeatureCallID
								{
									afterParserOrEnumRuleCall();
								}
							)
						)
						{
							newCompositeNode(grammarAccess.getXMemberFeatureCallAccess().getOpSingleAssignParserRuleCall_1_0_0_0_3());
						}
						ruleOpSingleAssign
						{
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getXMemberFeatureCallAccess().getValueXAssignmentParserRuleCall_1_0_1_0());
						}
						lv_value_6_0=ruleXAssignment
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getXMemberFeatureCallRule());
							}
							set(
								$current,
								"value",
								lv_value_6_0,
								"org.eclipse.xtext.xbase.Xbase.XAssignment");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
			    |
			(
				(
					((
						(
						)
						(
							'.'
							    |
							(
								(
									'?.'
								)
							)
							    |
							(
								(
									'::'
								)
							)
						)
					)
					)=>
					(
						(
							{
								$current = forceCreateModelElementAndSet(
									grammarAccess.getXMemberFeatureCallAccess().getXMemberFeatureCallMemberCallTargetAction_1_1_0_0_0(),
									$current);
							}
						)
						(
							otherlv_8='.'
							{
								newLeafNode(otherlv_8, grammarAccess.getXMemberFeatureCallAccess().getFullStopKeyword_1_1_0_0_1_0());
							}
							    |
							(
								(
									lv_nullSafe_9_0='?.'
									{
										newLeafNode(lv_nullSafe_9_0, grammarAccess.getXMemberFeatureCallAccess().getNullSafeQuestionMarkFullStopKeyword_1_1_0_0_1_1_0());
									}
									{
										if ($current==null) {
											$current = createModelElement(grammarAccess.getXMemberFeatureCallRule());
										}
										setWithLastConsumed($current, "nullSafe", true, "?.");
									}
								)
							)
							    |
							(
								(
									lv_explicitStatic_10_0='::'
									{
										newLeafNode(lv_explicitStatic_10_0, grammarAccess.getXMemberFeatureCallAccess().getExplicitStaticColonColonKeyword_1_1_0_0_1_2_0());
									}
									{
										if ($current==null) {
											$current = createModelElement(grammarAccess.getXMemberFeatureCallRule());
										}
										setWithLastConsumed($current, "explicitStatic", true, "::");
									}
								)
							)
						)
					)
				)
				(
					otherlv_11='<'
					{
						newLeafNode(otherlv_11, grammarAccess.getXMemberFeatureCallAccess().getLessThanSignKeyword_1_1_1_0());
					}
					(
						(
							{
								newCompositeNode(grammarAccess.getXMemberFeatureCallAccess().getTypeArgumentsJvmArgumentTypeReferenceParserRuleCall_1_1_1_1_0());
							}
							lv_typeArguments_12_0=ruleJvmArgumentTypeReference
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getXMemberFeatureCallRule());
								}
								add(
									$current,
									"typeArguments",
									lv_typeArguments_12_0,
									"org.eclipse.xtext.xbase.Xtype.JvmArgumentTypeReference");
								afterParserOrEnumRuleCall();
							}
						)
					)
					(
						otherlv_13=','
						{
							newLeafNode(otherlv_13, grammarAccess.getXMemberFeatureCallAccess().getCommaKeyword_1_1_1_2_0());
						}
						(
							(
								{
									newCompositeNode(grammarAccess.getXMemberFeatureCallAccess().getTypeArgumentsJvmArgumentTypeReferenceParserRuleCall_1_1_1_2_1_0());
								}
								lv_typeArguments_14_0=ruleJvmArgumentTypeReference
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getXMemberFeatureCallRule());
									}
									add(
										$current,
										"typeArguments",
										lv_typeArguments_14_0,
										"org.eclipse.xtext.xbase.Xtype.JvmArgumentTypeReference");
									afterParserOrEnumRuleCall();
								}
							)
						)
					)*
					otherlv_15='>'
					{
						newLeafNode(otherlv_15, grammarAccess.getXMemberFeatureCallAccess().getGreaterThanSignKeyword_1_1_1_3());
					}
				)?
				(
					(
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getXMemberFeatureCallRule());
							}
						}
						{
							newCompositeNode(grammarAccess.getXMemberFeatureCallAccess().getFeatureJvmIdentifiableElementCrossReference_1_1_2_0());
						}
						ruleIdOrSuper
						{
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					(
						((
							'('
						)
						)=>
						(
							lv_explicitOperationCall_17_0='('
							{
								newLeafNode(lv_explicitOperationCall_17_0, grammarAccess.getXMemberFeatureCallAccess().getExplicitOperationCallLeftParenthesisKeyword_1_1_3_0_0());
							}
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getXMemberFeatureCallRule());
								}
								setWithLastConsumed($current, "explicitOperationCall", true, "(");
							}
						)
					)
					(
						(
							((
								(
								)
								(
									(
										(
											ruleJvmFormalParameter
										)
									)
									(
										','
										(
											(
												ruleJvmFormalParameter
											)
										)
									)*
								)?
								(
									(
										'|'
									)
								)
							)
							)=>
							(
								{
									newCompositeNode(grammarAccess.getXMemberFeatureCallAccess().getMemberCallArgumentsXShortClosureParserRuleCall_1_1_3_1_0_0());
								}
								lv_memberCallArguments_18_0=ruleXShortClosure
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getXMemberFeatureCallRule());
									}
									add(
										$current,
										"memberCallArguments",
										lv_memberCallArguments_18_0,
										"org.eclipse.xtext.xbase.Xbase.XShortClosure");
									afterParserOrEnumRuleCall();
								}
							)
						)
						    |
						(
							(
								(
									{
										newCompositeNode(grammarAccess.getXMemberFeatureCallAccess().getMemberCallArgumentsXExpressionParserRuleCall_1_1_3_1_1_0_0());
									}
									lv_memberCallArguments_19_0=ruleXExpression
									{
										if ($current==null) {
											$current = createModelElementForParent(grammarAccess.getXMemberFeatureCallRule());
										}
										add(
											$current,
											"memberCallArguments",
											lv_memberCallArguments_19_0,
											"org.eclipse.xtext.xbase.Xbase.XExpression");
										afterParserOrEnumRuleCall();
									}
								)
							)
							(
								otherlv_20=','
								{
									newLeafNode(otherlv_20, grammarAccess.getXMemberFeatureCallAccess().getCommaKeyword_1_1_3_1_1_1_0());
								}
								(
									(
										{
											newCompositeNode(grammarAccess.getXMemberFeatureCallAccess().getMemberCallArgumentsXExpressionParserRuleCall_1_1_3_1_1_1_1_0());
										}
										lv_memberCallArguments_21_0=ruleXExpression
										{
											if ($current==null) {
												$current = createModelElementForParent(grammarAccess.getXMemberFeatureCallRule());
											}
											add(
												$current,
												"memberCallArguments",
												lv_memberCallArguments_21_0,
												"org.eclipse.xtext.xbase.Xbase.XExpression");
											afterParserOrEnumRuleCall();
										}
									)
								)
							)*
						)
					)?
					otherlv_22=')'
					{
						newLeafNode(otherlv_22, grammarAccess.getXMemberFeatureCallAccess().getRightParenthesisKeyword_1_1_3_2());
					}
				)?
				(
					((
						(
						)
						'['
					)
					)=>
					(
						{
							newCompositeNode(grammarAccess.getXMemberFeatureCallAccess().getMemberCallArgumentsXClosureParserRuleCall_1_1_4_0());
						}
						lv_memberCallArguments_23_0=ruleXClosure
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getXMemberFeatureCallRule());
							}
							add(
								$current,
								"memberCallArguments",
								lv_memberCallArguments_23_0,
								"org.eclipse.xtext.xbase.Xbase.XClosure");
							afterParserOrEnumRuleCall();
						}
					)
				)?
			)
		)*
	)
;

// Entry rule entryRuleXPrimaryExpression
entryRuleXPrimaryExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXPrimaryExpressionRule()); }
	iv_ruleXPrimaryExpression=ruleXPrimaryExpression
	{ $current=$iv_ruleXPrimaryExpression.current; }
	EOF;

// Rule XPrimaryExpression
ruleXPrimaryExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getXPrimaryExpressionAccess().getXConstructorCallParserRuleCall_0());
		}
		this_XConstructorCall_0=ruleXConstructorCall
		{
			$current = $this_XConstructorCall_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getXPrimaryExpressionAccess().getXBlockExpressionParserRuleCall_1());
		}
		this_XBlockExpression_1=ruleXBlockExpression
		{
			$current = $this_XBlockExpression_1.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getXPrimaryExpressionAccess().getXSwitchExpressionParserRuleCall_2());
		}
		this_XSwitchExpression_2=ruleXSwitchExpression
		{
			$current = $this_XSwitchExpression_2.current;
			afterParserOrEnumRuleCall();
		}
		    |
		(
			((
				(
				)
				'synchronized'
				'('
			)
			)=>
			{
				newCompositeNode(grammarAccess.getXPrimaryExpressionAccess().getXSynchronizedExpressionParserRuleCall_3());
			}
			this_XSynchronizedExpression_3=ruleXSynchronizedExpression
			{
				$current = $this_XSynchronizedExpression_3.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		{
			newCompositeNode(grammarAccess.getXPrimaryExpressionAccess().getXFeatureCallParserRuleCall_4());
		}
		this_XFeatureCall_4=ruleXFeatureCall
		{
			$current = $this_XFeatureCall_4.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getXPrimaryExpressionAccess().getXLiteralParserRuleCall_5());
		}
		this_XLiteral_5=ruleXLiteral
		{
			$current = $this_XLiteral_5.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getXPrimaryExpressionAccess().getXIfExpressionParserRuleCall_6());
		}
		this_XIfExpression_6=ruleXIfExpression
		{
			$current = $this_XIfExpression_6.current;
			afterParserOrEnumRuleCall();
		}
		    |
		(
			((
				(
				)
				'for'
				'('
				(
					(
						ruleJvmFormalParameter
					)
				)
				':'
			)
			)=>
			{
				newCompositeNode(grammarAccess.getXPrimaryExpressionAccess().getXForLoopExpressionParserRuleCall_7());
			}
			this_XForLoopExpression_7=ruleXForLoopExpression
			{
				$current = $this_XForLoopExpression_7.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		{
			newCompositeNode(grammarAccess.getXPrimaryExpressionAccess().getXBasicForLoopExpressionParserRuleCall_8());
		}
		this_XBasicForLoopExpression_8=ruleXBasicForLoopExpression
		{
			$current = $this_XBasicForLoopExpression_8.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getXPrimaryExpressionAccess().getXWhileExpressionParserRuleCall_9());
		}
		this_XWhileExpression_9=ruleXWhileExpression
		{
			$current = $this_XWhileExpression_9.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getXPrimaryExpressionAccess().getXDoWhileExpressionParserRuleCall_10());
		}
		this_XDoWhileExpression_10=ruleXDoWhileExpression
		{
			$current = $this_XDoWhileExpression_10.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getXPrimaryExpressionAccess().getXThrowExpressionParserRuleCall_11());
		}
		this_XThrowExpression_11=ruleXThrowExpression
		{
			$current = $this_XThrowExpression_11.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getXPrimaryExpressionAccess().getXReturnExpressionParserRuleCall_12());
		}
		this_XReturnExpression_12=ruleXReturnExpression
		{
			$current = $this_XReturnExpression_12.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getXPrimaryExpressionAccess().getXTryCatchFinallyExpressionParserRuleCall_13());
		}
		this_XTryCatchFinallyExpression_13=ruleXTryCatchFinallyExpression
		{
			$current = $this_XTryCatchFinallyExpression_13.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getXPrimaryExpressionAccess().getXParenthesizedExpressionParserRuleCall_14());
		}
		this_XParenthesizedExpression_14=ruleXParenthesizedExpression
		{
			$current = $this_XParenthesizedExpression_14.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleXLiteral
entryRuleXLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXLiteralRule()); }
	iv_ruleXLiteral=ruleXLiteral
	{ $current=$iv_ruleXLiteral.current; }
	EOF;

// Rule XLiteral
ruleXLiteral returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getXLiteralAccess().getXCollectionLiteralParserRuleCall_0());
		}
		this_XCollectionLiteral_0=ruleXCollectionLiteral
		{
			$current = $this_XCollectionLiteral_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		(
			((
				(
				)
				'['
			)
			)=>
			{
				newCompositeNode(grammarAccess.getXLiteralAccess().getXClosureParserRuleCall_1());
			}
			this_XClosure_1=ruleXClosure
			{
				$current = $this_XClosure_1.current;
				afterParserOrEnumRuleCall();
			}
		)
		    |
		{
			newCompositeNode(grammarAccess.getXLiteralAccess().getXBooleanLiteralParserRuleCall_2());
		}
		this_XBooleanLiteral_2=ruleXBooleanLiteral
		{
			$current = $this_XBooleanLiteral_2.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getXLiteralAccess().getXNumberLiteralParserRuleCall_3());
		}
		this_XNumberLiteral_3=ruleXNumberLiteral
		{
			$current = $this_XNumberLiteral_3.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getXLiteralAccess().getXNullLiteralParserRuleCall_4());
		}
		this_XNullLiteral_4=ruleXNullLiteral
		{
			$current = $this_XNullLiteral_4.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getXLiteralAccess().getXStringLiteralParserRuleCall_5());
		}
		this_XStringLiteral_5=ruleXStringLiteral
		{
			$current = $this_XStringLiteral_5.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getXLiteralAccess().getXTypeLiteralParserRuleCall_6());
		}
		this_XTypeLiteral_6=ruleXTypeLiteral
		{
			$current = $this_XTypeLiteral_6.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleXCollectionLiteral
entryRuleXCollectionLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXCollectionLiteralRule()); }
	iv_ruleXCollectionLiteral=ruleXCollectionLiteral
	{ $current=$iv_ruleXCollectionLiteral.current; }
	EOF;

// Rule XCollectionLiteral
ruleXCollectionLiteral returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getXCollectionLiteralAccess().getXSetLiteralParserRuleCall_0());
		}
		this_XSetLiteral_0=ruleXSetLiteral
		{
			$current = $this_XSetLiteral_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getXCollectionLiteralAccess().getXListLiteralParserRuleCall_1());
		}
		this_XListLiteral_1=ruleXListLiteral
		{
			$current = $this_XListLiteral_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleXSetLiteral
entryRuleXSetLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXSetLiteralRule()); }
	iv_ruleXSetLiteral=ruleXSetLiteral
	{ $current=$iv_ruleXSetLiteral.current; }
	EOF;

// Rule XSetLiteral
ruleXSetLiteral returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getXSetLiteralAccess().getXSetLiteralAction_0(),
					$current);
			}
		)
		otherlv_1='#'
		{
			newLeafNode(otherlv_1, grammarAccess.getXSetLiteralAccess().getNumberSignKeyword_1());
		}
		otherlv_2='{'
		{
			newLeafNode(otherlv_2, grammarAccess.getXSetLiteralAccess().getLeftCurlyBracketKeyword_2());
		}
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getXSetLiteralAccess().getElementsXExpressionParserRuleCall_3_0_0());
					}
					lv_elements_3_0=ruleXExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getXSetLiteralRule());
						}
						add(
							$current,
							"elements",
							lv_elements_3_0,
							"org.eclipse.xtext.xbase.Xbase.XExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_4=','
				{
					newLeafNode(otherlv_4, grammarAccess.getXSetLiteralAccess().getCommaKeyword_3_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getXSetLiteralAccess().getElementsXExpressionParserRuleCall_3_1_1_0());
						}
						lv_elements_5_0=ruleXExpression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getXSetLiteralRule());
							}
							add(
								$current,
								"elements",
								lv_elements_5_0,
								"org.eclipse.xtext.xbase.Xbase.XExpression");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
		)?
		otherlv_6='}'
		{
			newLeafNode(otherlv_6, grammarAccess.getXSetLiteralAccess().getRightCurlyBracketKeyword_4());
		}
	)
;

// Entry rule entryRuleXListLiteral
entryRuleXListLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXListLiteralRule()); }
	iv_ruleXListLiteral=ruleXListLiteral
	{ $current=$iv_ruleXListLiteral.current; }
	EOF;

// Rule XListLiteral
ruleXListLiteral returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getXListLiteralAccess().getXListLiteralAction_0(),
					$current);
			}
		)
		otherlv_1='#'
		{
			newLeafNode(otherlv_1, grammarAccess.getXListLiteralAccess().getNumberSignKeyword_1());
		}
		otherlv_2='['
		{
			newLeafNode(otherlv_2, grammarAccess.getXListLiteralAccess().getLeftSquareBracketKeyword_2());
		}
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getXListLiteralAccess().getElementsXExpressionParserRuleCall_3_0_0());
					}
					lv_elements_3_0=ruleXExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getXListLiteralRule());
						}
						add(
							$current,
							"elements",
							lv_elements_3_0,
							"org.eclipse.xtext.xbase.Xbase.XExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_4=','
				{
					newLeafNode(otherlv_4, grammarAccess.getXListLiteralAccess().getCommaKeyword_3_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getXListLiteralAccess().getElementsXExpressionParserRuleCall_3_1_1_0());
						}
						lv_elements_5_0=ruleXExpression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getXListLiteralRule());
							}
							add(
								$current,
								"elements",
								lv_elements_5_0,
								"org.eclipse.xtext.xbase.Xbase.XExpression");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
		)?
		otherlv_6=']'
		{
			newLeafNode(otherlv_6, grammarAccess.getXListLiteralAccess().getRightSquareBracketKeyword_4());
		}
	)
;

// Entry rule entryRuleXClosure
entryRuleXClosure returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXClosureRule()); }
	iv_ruleXClosure=ruleXClosure
	{ $current=$iv_ruleXClosure.current; }
	EOF;

// Rule XClosure
ruleXClosure returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				'['
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getXClosureAccess().getXClosureAction_0_0_0(),
							$current);
					}
				)
				otherlv_1='['
				{
					newLeafNode(otherlv_1, grammarAccess.getXClosureAccess().getLeftSquareBracketKeyword_0_0_1());
				}
			)
		)
		(
			((
				(
					(
						(
							ruleJvmFormalParameter
						)
					)
					(
						','
						(
							(
								ruleJvmFormalParameter
							)
						)
					)*
				)?
				(
					(
						'|'
					)
				)
			)
			)=>
			(
				(
					(
						(
							{
								newCompositeNode(grammarAccess.getXClosureAccess().getDeclaredFormalParametersJvmFormalParameterParserRuleCall_1_0_0_0_0());
							}
							lv_declaredFormalParameters_2_0=ruleJvmFormalParameter
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getXClosureRule());
								}
								add(
									$current,
									"declaredFormalParameters",
									lv_declaredFormalParameters_2_0,
									"org.eclipse.xtext.xbase.Xbase.JvmFormalParameter");
								afterParserOrEnumRuleCall();
							}
						)
					)
					(
						otherlv_3=','
						{
							newLeafNode(otherlv_3, grammarAccess.getXClosureAccess().getCommaKeyword_1_0_0_1_0());
						}
						(
							(
								{
									newCompositeNode(grammarAccess.getXClosureAccess().getDeclaredFormalParametersJvmFormalParameterParserRuleCall_1_0_0_1_1_0());
								}
								lv_declaredFormalParameters_4_0=ruleJvmFormalParameter
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getXClosureRule());
									}
									add(
										$current,
										"declaredFormalParameters",
										lv_declaredFormalParameters_4_0,
										"org.eclipse.xtext.xbase.Xbase.JvmFormalParameter");
									afterParserOrEnumRuleCall();
								}
							)
						)
					)*
				)?
				(
					(
						lv_explicitSyntax_5_0='|'
						{
							newLeafNode(lv_explicitSyntax_5_0, grammarAccess.getXClosureAccess().getExplicitSyntaxVerticalLineKeyword_1_0_1_0());
						}
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getXClosureRule());
							}
							setWithLastConsumed($current, "explicitSyntax", true, "|");
						}
					)
				)
			)
		)?
		(
			(
				{
					newCompositeNode(grammarAccess.getXClosureAccess().getExpressionXExpressionInClosureParserRuleCall_2_0());
				}
				lv_expression_6_0=ruleXExpressionInClosure
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getXClosureRule());
					}
					set(
						$current,
						"expression",
						lv_expression_6_0,
						"org.eclipse.xtext.xbase.Xbase.XExpressionInClosure");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_7=']'
		{
			newLeafNode(otherlv_7, grammarAccess.getXClosureAccess().getRightSquareBracketKeyword_3());
		}
	)
;

// Entry rule entryRuleXExpressionInClosure
entryRuleXExpressionInClosure returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXExpressionInClosureRule()); }
	iv_ruleXExpressionInClosure=ruleXExpressionInClosure
	{ $current=$iv_ruleXExpressionInClosure.current; }
	EOF;

// Rule XExpressionInClosure
ruleXExpressionInClosure returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getXExpressionInClosureAccess().getXBlockExpressionAction_0(),
					$current);
			}
		)
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getXExpressionInClosureAccess().getExpressionsXExpressionOrVarDeclarationParserRuleCall_1_0_0());
					}
					lv_expressions_1_0=ruleXExpressionOrVarDeclaration
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getXExpressionInClosureRule());
						}
						add(
							$current,
							"expressions",
							lv_expressions_1_0,
							"org.eclipse.xtext.xbase.Xbase.XExpressionOrVarDeclaration");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_2=';'
				{
					newLeafNode(otherlv_2, grammarAccess.getXExpressionInClosureAccess().getSemicolonKeyword_1_1());
				}
			)?
		)*
	)
;

// Entry rule entryRuleXShortClosure
entryRuleXShortClosure returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXShortClosureRule()); }
	iv_ruleXShortClosure=ruleXShortClosure
	{ $current=$iv_ruleXShortClosure.current; }
	EOF;

// Rule XShortClosure
ruleXShortClosure returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				(
					(
						(
							ruleJvmFormalParameter
						)
					)
					(
						','
						(
							(
								ruleJvmFormalParameter
							)
						)
					)*
				)?
				(
					(
						'|'
					)
				)
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getXShortClosureAccess().getXClosureAction_0_0_0(),
							$current);
					}
				)
				(
					(
						(
							{
								newCompositeNode(grammarAccess.getXShortClosureAccess().getDeclaredFormalParametersJvmFormalParameterParserRuleCall_0_0_1_0_0());
							}
							lv_declaredFormalParameters_1_0=ruleJvmFormalParameter
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getXShortClosureRule());
								}
								add(
									$current,
									"declaredFormalParameters",
									lv_declaredFormalParameters_1_0,
									"org.eclipse.xtext.xbase.Xbase.JvmFormalParameter");
								afterParserOrEnumRuleCall();
							}
						)
					)
					(
						otherlv_2=','
						{
							newLeafNode(otherlv_2, grammarAccess.getXShortClosureAccess().getCommaKeyword_0_0_1_1_0());
						}
						(
							(
								{
									newCompositeNode(grammarAccess.getXShortClosureAccess().getDeclaredFormalParametersJvmFormalParameterParserRuleCall_0_0_1_1_1_0());
								}
								lv_declaredFormalParameters_3_0=ruleJvmFormalParameter
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getXShortClosureRule());
									}
									add(
										$current,
										"declaredFormalParameters",
										lv_declaredFormalParameters_3_0,
										"org.eclipse.xtext.xbase.Xbase.JvmFormalParameter");
									afterParserOrEnumRuleCall();
								}
							)
						)
					)*
				)?
				(
					(
						lv_explicitSyntax_4_0='|'
						{
							newLeafNode(lv_explicitSyntax_4_0, grammarAccess.getXShortClosureAccess().getExplicitSyntaxVerticalLineKeyword_0_0_2_0());
						}
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getXShortClosureRule());
							}
							setWithLastConsumed($current, "explicitSyntax", true, "|");
						}
					)
				)
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getXShortClosureAccess().getExpressionXExpressionParserRuleCall_1_0());
				}
				lv_expression_5_0=ruleXExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getXShortClosureRule());
					}
					set(
						$current,
						"expression",
						lv_expression_5_0,
						"org.eclipse.xtext.xbase.Xbase.XExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleXParenthesizedExpression
entryRuleXParenthesizedExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXParenthesizedExpressionRule()); }
	iv_ruleXParenthesizedExpression=ruleXParenthesizedExpression
	{ $current=$iv_ruleXParenthesizedExpression.current; }
	EOF;

// Rule XParenthesizedExpression
ruleXParenthesizedExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0='('
		{
			newLeafNode(otherlv_0, grammarAccess.getXParenthesizedExpressionAccess().getLeftParenthesisKeyword_0());
		}
		{
			newCompositeNode(grammarAccess.getXParenthesizedExpressionAccess().getXExpressionParserRuleCall_1());
		}
		this_XExpression_1=ruleXExpression
		{
			$current = $this_XExpression_1.current;
			afterParserOrEnumRuleCall();
		}
		otherlv_2=')'
		{
			newLeafNode(otherlv_2, grammarAccess.getXParenthesizedExpressionAccess().getRightParenthesisKeyword_2());
		}
	)
;

// Entry rule entryRuleXIfExpression
entryRuleXIfExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXIfExpressionRule()); }
	iv_ruleXIfExpression=ruleXIfExpression
	{ $current=$iv_ruleXIfExpression.current; }
	EOF;

// Rule XIfExpression
ruleXIfExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getXIfExpressionAccess().getXIfExpressionAction_0(),
					$current);
			}
		)
		otherlv_1='if'
		{
			newLeafNode(otherlv_1, grammarAccess.getXIfExpressionAccess().getIfKeyword_1());
		}
		otherlv_2='('
		{
			newLeafNode(otherlv_2, grammarAccess.getXIfExpressionAccess().getLeftParenthesisKeyword_2());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getXIfExpressionAccess().getIfXExpressionParserRuleCall_3_0());
				}
				lv_if_3_0=ruleXExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getXIfExpressionRule());
					}
					set(
						$current,
						"if",
						lv_if_3_0,
						"org.eclipse.xtext.xbase.Xbase.XExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_4=')'
		{
			newLeafNode(otherlv_4, grammarAccess.getXIfExpressionAccess().getRightParenthesisKeyword_4());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getXIfExpressionAccess().getThenXExpressionParserRuleCall_5_0());
				}
				lv_then_5_0=ruleXExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getXIfExpressionRule());
					}
					set(
						$current,
						"then",
						lv_then_5_0,
						"org.eclipse.xtext.xbase.Xbase.XExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			(
				('else')=>
				otherlv_6='else'
				{
					newLeafNode(otherlv_6, grammarAccess.getXIfExpressionAccess().getElseKeyword_6_0());
				}
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getXIfExpressionAccess().getElseXExpressionParserRuleCall_6_1_0());
					}
					lv_else_7_0=ruleXExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getXIfExpressionRule());
						}
						set(
							$current,
							"else",
							lv_else_7_0,
							"org.eclipse.xtext.xbase.Xbase.XExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
	)
;

// Entry rule entryRuleXSwitchExpression
entryRuleXSwitchExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXSwitchExpressionRule()); }
	iv_ruleXSwitchExpression=ruleXSwitchExpression
	{ $current=$iv_ruleXSwitchExpression.current; }
	EOF;

// Rule XSwitchExpression
ruleXSwitchExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getXSwitchExpressionAccess().getXSwitchExpressionAction_0(),
					$current);
			}
		)
		otherlv_1='switch'
		{
			newLeafNode(otherlv_1, grammarAccess.getXSwitchExpressionAccess().getSwitchKeyword_1());
		}
		(
			(
				(
					((
						'('
						(
							(
								ruleJvmFormalParameter
							)
						)
						':'
					)
					)=>
					(
						otherlv_2='('
						{
							newLeafNode(otherlv_2, grammarAccess.getXSwitchExpressionAccess().getLeftParenthesisKeyword_2_0_0_0_0());
						}
						(
							(
								{
									newCompositeNode(grammarAccess.getXSwitchExpressionAccess().getDeclaredParamJvmFormalParameterParserRuleCall_2_0_0_0_1_0());
								}
								lv_declaredParam_3_0=ruleJvmFormalParameter
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getXSwitchExpressionRule());
									}
									set(
										$current,
										"declaredParam",
										lv_declaredParam_3_0,
										"org.eclipse.xtext.xbase.Xbase.JvmFormalParameter");
									afterParserOrEnumRuleCall();
								}
							)
						)
						otherlv_4=':'
						{
							newLeafNode(otherlv_4, grammarAccess.getXSwitchExpressionAccess().getColonKeyword_2_0_0_0_2());
						}
					)
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getXSwitchExpressionAccess().getSwitchXExpressionParserRuleCall_2_0_1_0());
						}
						lv_switch_5_0=ruleXExpression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getXSwitchExpressionRule());
							}
							set(
								$current,
								"switch",
								lv_switch_5_0,
								"org.eclipse.xtext.xbase.Xbase.XExpression");
							afterParserOrEnumRuleCall();
						}
					)
				)
				otherlv_6=')'
				{
					newLeafNode(otherlv_6, grammarAccess.getXSwitchExpressionAccess().getRightParenthesisKeyword_2_0_2());
				}
			)
			    |
			(
				(
					((
						(
							(
								ruleJvmFormalParameter
							)
						)
						':'
					)
					)=>
					(
						(
							(
								{
									newCompositeNode(grammarAccess.getXSwitchExpressionAccess().getDeclaredParamJvmFormalParameterParserRuleCall_2_1_0_0_0_0());
								}
								lv_declaredParam_7_0=ruleJvmFormalParameter
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getXSwitchExpressionRule());
									}
									set(
										$current,
										"declaredParam",
										lv_declaredParam_7_0,
										"org.eclipse.xtext.xbase.Xbase.JvmFormalParameter");
									afterParserOrEnumRuleCall();
								}
							)
						)
						otherlv_8=':'
						{
							newLeafNode(otherlv_8, grammarAccess.getXSwitchExpressionAccess().getColonKeyword_2_1_0_0_1());
						}
					)
				)?
				(
					(
						{
							newCompositeNode(grammarAccess.getXSwitchExpressionAccess().getSwitchXExpressionParserRuleCall_2_1_1_0());
						}
						lv_switch_9_0=ruleXExpression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getXSwitchExpressionRule());
							}
							set(
								$current,
								"switch",
								lv_switch_9_0,
								"org.eclipse.xtext.xbase.Xbase.XExpression");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
		)
		otherlv_10='{'
		{
			newLeafNode(otherlv_10, grammarAccess.getXSwitchExpressionAccess().getLeftCurlyBracketKeyword_3());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getXSwitchExpressionAccess().getCasesXCasePartParserRuleCall_4_0());
				}
				lv_cases_11_0=ruleXCasePart
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getXSwitchExpressionRule());
					}
					add(
						$current,
						"cases",
						lv_cases_11_0,
						"org.eclipse.xtext.xbase.Xbase.XCasePart");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		(
			otherlv_12='default'
			{
				newLeafNode(otherlv_12, grammarAccess.getXSwitchExpressionAccess().getDefaultKeyword_5_0());
			}
			otherlv_13=':'
			{
				newLeafNode(otherlv_13, grammarAccess.getXSwitchExpressionAccess().getColonKeyword_5_1());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getXSwitchExpressionAccess().getDefaultXExpressionParserRuleCall_5_2_0());
					}
					lv_default_14_0=ruleXExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getXSwitchExpressionRule());
						}
						set(
							$current,
							"default",
							lv_default_14_0,
							"org.eclipse.xtext.xbase.Xbase.XExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
		otherlv_15='}'
		{
			newLeafNode(otherlv_15, grammarAccess.getXSwitchExpressionAccess().getRightCurlyBracketKeyword_6());
		}
	)
;

// Entry rule entryRuleXCasePart
entryRuleXCasePart returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXCasePartRule()); }
	iv_ruleXCasePart=ruleXCasePart
	{ $current=$iv_ruleXCasePart.current; }
	EOF;

// Rule XCasePart
ruleXCasePart returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getXCasePartAccess().getXCasePartAction_0(),
					$current);
			}
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getXCasePartAccess().getTypeGuardJvmTypeReferenceParserRuleCall_1_0());
				}
				lv_typeGuard_1_0=ruleJvmTypeReference
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getXCasePartRule());
					}
					set(
						$current,
						"typeGuard",
						lv_typeGuard_1_0,
						"org.eclipse.xtext.xbase.Xtype.JvmTypeReference");
					afterParserOrEnumRuleCall();
				}
			)
		)?
		(
			otherlv_2='case'
			{
				newLeafNode(otherlv_2, grammarAccess.getXCasePartAccess().getCaseKeyword_2_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getXCasePartAccess().getCaseXExpressionParserRuleCall_2_1_0());
					}
					lv_case_3_0=ruleXExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getXCasePartRule());
						}
						set(
							$current,
							"case",
							lv_case_3_0,
							"org.eclipse.xtext.xbase.Xbase.XExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
		(
			(
				otherlv_4=':'
				{
					newLeafNode(otherlv_4, grammarAccess.getXCasePartAccess().getColonKeyword_3_0_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getXCasePartAccess().getThenXExpressionParserRuleCall_3_0_1_0());
						}
						lv_then_5_0=ruleXExpression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getXCasePartRule());
							}
							set(
								$current,
								"then",
								lv_then_5_0,
								"org.eclipse.xtext.xbase.Xbase.XExpression");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
			    |
			(
				(
					lv_fallThrough_6_0=','
					{
						newLeafNode(lv_fallThrough_6_0, grammarAccess.getXCasePartAccess().getFallThroughCommaKeyword_3_1_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getXCasePartRule());
						}
						setWithLastConsumed($current, "fallThrough", true, ",");
					}
				)
			)
		)
	)
;

// Entry rule entryRuleXForLoopExpression
entryRuleXForLoopExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXForLoopExpressionRule()); }
	iv_ruleXForLoopExpression=ruleXForLoopExpression
	{ $current=$iv_ruleXForLoopExpression.current; }
	EOF;

// Rule XForLoopExpression
ruleXForLoopExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				'for'
				'('
				(
					(
						ruleJvmFormalParameter
					)
				)
				':'
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getXForLoopExpressionAccess().getXForLoopExpressionAction_0_0_0(),
							$current);
					}
				)
				otherlv_1='for'
				{
					newLeafNode(otherlv_1, grammarAccess.getXForLoopExpressionAccess().getForKeyword_0_0_1());
				}
				otherlv_2='('
				{
					newLeafNode(otherlv_2, grammarAccess.getXForLoopExpressionAccess().getLeftParenthesisKeyword_0_0_2());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getXForLoopExpressionAccess().getDeclaredParamJvmFormalParameterParserRuleCall_0_0_3_0());
						}
						lv_declaredParam_3_0=ruleJvmFormalParameter
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getXForLoopExpressionRule());
							}
							set(
								$current,
								"declaredParam",
								lv_declaredParam_3_0,
								"org.eclipse.xtext.xbase.Xbase.JvmFormalParameter");
							afterParserOrEnumRuleCall();
						}
					)
				)
				otherlv_4=':'
				{
					newLeafNode(otherlv_4, grammarAccess.getXForLoopExpressionAccess().getColonKeyword_0_0_4());
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getXForLoopExpressionAccess().getForExpressionXExpressionParserRuleCall_1_0());
				}
				lv_forExpression_5_0=ruleXExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getXForLoopExpressionRule());
					}
					set(
						$current,
						"forExpression",
						lv_forExpression_5_0,
						"org.eclipse.xtext.xbase.Xbase.XExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_6=')'
		{
			newLeafNode(otherlv_6, grammarAccess.getXForLoopExpressionAccess().getRightParenthesisKeyword_2());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getXForLoopExpressionAccess().getEachExpressionXExpressionParserRuleCall_3_0());
				}
				lv_eachExpression_7_0=ruleXExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getXForLoopExpressionRule());
					}
					set(
						$current,
						"eachExpression",
						lv_eachExpression_7_0,
						"org.eclipse.xtext.xbase.Xbase.XExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleXBasicForLoopExpression
entryRuleXBasicForLoopExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXBasicForLoopExpressionRule()); }
	iv_ruleXBasicForLoopExpression=ruleXBasicForLoopExpression
	{ $current=$iv_ruleXBasicForLoopExpression.current; }
	EOF;

// Rule XBasicForLoopExpression
ruleXBasicForLoopExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getXBasicForLoopExpressionAccess().getXBasicForLoopExpressionAction_0(),
					$current);
			}
		)
		otherlv_1='for'
		{
			newLeafNode(otherlv_1, grammarAccess.getXBasicForLoopExpressionAccess().getForKeyword_1());
		}
		otherlv_2='('
		{
			newLeafNode(otherlv_2, grammarAccess.getXBasicForLoopExpressionAccess().getLeftParenthesisKeyword_2());
		}
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getXBasicForLoopExpressionAccess().getInitExpressionsXExpressionOrVarDeclarationParserRuleCall_3_0_0());
					}
					lv_initExpressions_3_0=ruleXExpressionOrVarDeclaration
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getXBasicForLoopExpressionRule());
						}
						add(
							$current,
							"initExpressions",
							lv_initExpressions_3_0,
							"org.eclipse.xtext.xbase.Xbase.XExpressionOrVarDeclaration");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_4=','
				{
					newLeafNode(otherlv_4, grammarAccess.getXBasicForLoopExpressionAccess().getCommaKeyword_3_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getXBasicForLoopExpressionAccess().getInitExpressionsXExpressionOrVarDeclarationParserRuleCall_3_1_1_0());
						}
						lv_initExpressions_5_0=ruleXExpressionOrVarDeclaration
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getXBasicForLoopExpressionRule());
							}
							add(
								$current,
								"initExpressions",
								lv_initExpressions_5_0,
								"org.eclipse.xtext.xbase.Xbase.XExpressionOrVarDeclaration");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
		)?
		otherlv_6=';'
		{
			newLeafNode(otherlv_6, grammarAccess.getXBasicForLoopExpressionAccess().getSemicolonKeyword_4());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getXBasicForLoopExpressionAccess().getExpressionXExpressionParserRuleCall_5_0());
				}
				lv_expression_7_0=ruleXExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getXBasicForLoopExpressionRule());
					}
					set(
						$current,
						"expression",
						lv_expression_7_0,
						"org.eclipse.xtext.xbase.Xbase.XExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)?
		otherlv_8=';'
		{
			newLeafNode(otherlv_8, grammarAccess.getXBasicForLoopExpressionAccess().getSemicolonKeyword_6());
		}
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getXBasicForLoopExpressionAccess().getUpdateExpressionsXExpressionParserRuleCall_7_0_0());
					}
					lv_updateExpressions_9_0=ruleXExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getXBasicForLoopExpressionRule());
						}
						add(
							$current,
							"updateExpressions",
							lv_updateExpressions_9_0,
							"org.eclipse.xtext.xbase.Xbase.XExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_10=','
				{
					newLeafNode(otherlv_10, grammarAccess.getXBasicForLoopExpressionAccess().getCommaKeyword_7_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getXBasicForLoopExpressionAccess().getUpdateExpressionsXExpressionParserRuleCall_7_1_1_0());
						}
						lv_updateExpressions_11_0=ruleXExpression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getXBasicForLoopExpressionRule());
							}
							add(
								$current,
								"updateExpressions",
								lv_updateExpressions_11_0,
								"org.eclipse.xtext.xbase.Xbase.XExpression");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
		)?
		otherlv_12=')'
		{
			newLeafNode(otherlv_12, grammarAccess.getXBasicForLoopExpressionAccess().getRightParenthesisKeyword_8());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getXBasicForLoopExpressionAccess().getEachExpressionXExpressionParserRuleCall_9_0());
				}
				lv_eachExpression_13_0=ruleXExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getXBasicForLoopExpressionRule());
					}
					set(
						$current,
						"eachExpression",
						lv_eachExpression_13_0,
						"org.eclipse.xtext.xbase.Xbase.XExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleXWhileExpression
entryRuleXWhileExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXWhileExpressionRule()); }
	iv_ruleXWhileExpression=ruleXWhileExpression
	{ $current=$iv_ruleXWhileExpression.current; }
	EOF;

// Rule XWhileExpression
ruleXWhileExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getXWhileExpressionAccess().getXWhileExpressionAction_0(),
					$current);
			}
		)
		otherlv_1='while'
		{
			newLeafNode(otherlv_1, grammarAccess.getXWhileExpressionAccess().getWhileKeyword_1());
		}
		otherlv_2='('
		{
			newLeafNode(otherlv_2, grammarAccess.getXWhileExpressionAccess().getLeftParenthesisKeyword_2());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getXWhileExpressionAccess().getPredicateXExpressionParserRuleCall_3_0());
				}
				lv_predicate_3_0=ruleXExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getXWhileExpressionRule());
					}
					set(
						$current,
						"predicate",
						lv_predicate_3_0,
						"org.eclipse.xtext.xbase.Xbase.XExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_4=')'
		{
			newLeafNode(otherlv_4, grammarAccess.getXWhileExpressionAccess().getRightParenthesisKeyword_4());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getXWhileExpressionAccess().getBodyXExpressionParserRuleCall_5_0());
				}
				lv_body_5_0=ruleXExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getXWhileExpressionRule());
					}
					set(
						$current,
						"body",
						lv_body_5_0,
						"org.eclipse.xtext.xbase.Xbase.XExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleXDoWhileExpression
entryRuleXDoWhileExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXDoWhileExpressionRule()); }
	iv_ruleXDoWhileExpression=ruleXDoWhileExpression
	{ $current=$iv_ruleXDoWhileExpression.current; }
	EOF;

// Rule XDoWhileExpression
ruleXDoWhileExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getXDoWhileExpressionAccess().getXDoWhileExpressionAction_0(),
					$current);
			}
		)
		otherlv_1='do'
		{
			newLeafNode(otherlv_1, grammarAccess.getXDoWhileExpressionAccess().getDoKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getXDoWhileExpressionAccess().getBodyXExpressionParserRuleCall_2_0());
				}
				lv_body_2_0=ruleXExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getXDoWhileExpressionRule());
					}
					set(
						$current,
						"body",
						lv_body_2_0,
						"org.eclipse.xtext.xbase.Xbase.XExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_3='while'
		{
			newLeafNode(otherlv_3, grammarAccess.getXDoWhileExpressionAccess().getWhileKeyword_3());
		}
		otherlv_4='('
		{
			newLeafNode(otherlv_4, grammarAccess.getXDoWhileExpressionAccess().getLeftParenthesisKeyword_4());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getXDoWhileExpressionAccess().getPredicateXExpressionParserRuleCall_5_0());
				}
				lv_predicate_5_0=ruleXExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getXDoWhileExpressionRule());
					}
					set(
						$current,
						"predicate",
						lv_predicate_5_0,
						"org.eclipse.xtext.xbase.Xbase.XExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_6=')'
		{
			newLeafNode(otherlv_6, grammarAccess.getXDoWhileExpressionAccess().getRightParenthesisKeyword_6());
		}
	)
;

// Entry rule entryRuleXBlockExpression
entryRuleXBlockExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXBlockExpressionRule()); }
	iv_ruleXBlockExpression=ruleXBlockExpression
	{ $current=$iv_ruleXBlockExpression.current; }
	EOF;

// Rule XBlockExpression
ruleXBlockExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getXBlockExpressionAccess().getXBlockExpressionAction_0(),
					$current);
			}
		)
		otherlv_1='{'
		{
			newLeafNode(otherlv_1, grammarAccess.getXBlockExpressionAccess().getLeftCurlyBracketKeyword_1());
		}
		(
			(
				(
					{
						newCompositeNode(grammarAccess.getXBlockExpressionAccess().getExpressionsXExpressionOrVarDeclarationParserRuleCall_2_0_0());
					}
					lv_expressions_2_0=ruleXExpressionOrVarDeclaration
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getXBlockExpressionRule());
						}
						add(
							$current,
							"expressions",
							lv_expressions_2_0,
							"org.eclipse.xtext.xbase.Xbase.XExpressionOrVarDeclaration");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_3=';'
				{
					newLeafNode(otherlv_3, grammarAccess.getXBlockExpressionAccess().getSemicolonKeyword_2_1());
				}
			)?
		)*
		otherlv_4='}'
		{
			newLeafNode(otherlv_4, grammarAccess.getXBlockExpressionAccess().getRightCurlyBracketKeyword_3());
		}
	)
;

// Entry rule entryRuleXExpressionOrVarDeclaration
entryRuleXExpressionOrVarDeclaration returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXExpressionOrVarDeclarationRule()); }
	iv_ruleXExpressionOrVarDeclaration=ruleXExpressionOrVarDeclaration
	{ $current=$iv_ruleXExpressionOrVarDeclaration.current; }
	EOF;

// Rule XExpressionOrVarDeclaration
ruleXExpressionOrVarDeclaration returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getXExpressionOrVarDeclarationAccess().getXVariableDeclarationParserRuleCall_0());
		}
		this_XVariableDeclaration_0=ruleXVariableDeclaration
		{
			$current = $this_XVariableDeclaration_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getXExpressionOrVarDeclarationAccess().getXExpressionParserRuleCall_1());
		}
		this_XExpression_1=ruleXExpression
		{
			$current = $this_XExpression_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleXVariableDeclaration
entryRuleXVariableDeclaration returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXVariableDeclarationRule()); }
	iv_ruleXVariableDeclaration=ruleXVariableDeclaration
	{ $current=$iv_ruleXVariableDeclaration.current; }
	EOF;

// Rule XVariableDeclaration
ruleXVariableDeclaration returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getXVariableDeclarationAccess().getXVariableDeclarationAction_0(),
					$current);
			}
		)
		(
			(
				(
					lv_writeable_1_0='var'
					{
						newLeafNode(lv_writeable_1_0, grammarAccess.getXVariableDeclarationAccess().getWriteableVarKeyword_1_0_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getXVariableDeclarationRule());
						}
						setWithLastConsumed($current, "writeable", true, "var");
					}
				)
			)
			    |
			otherlv_2='val'
			{
				newLeafNode(otherlv_2, grammarAccess.getXVariableDeclarationAccess().getValKeyword_1_1());
			}
		)
		(
			(
				((
					(
						(
							ruleJvmTypeReference
						)
					)
					(
						(
							ruleValidID
						)
					)
				)
				)=>
				(
					(
						(
							{
								newCompositeNode(grammarAccess.getXVariableDeclarationAccess().getTypeJvmTypeReferenceParserRuleCall_2_0_0_0_0());
							}
							lv_type_3_0=ruleJvmTypeReference
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getXVariableDeclarationRule());
								}
								set(
									$current,
									"type",
									lv_type_3_0,
									"org.eclipse.xtext.xbase.Xtype.JvmTypeReference");
								afterParserOrEnumRuleCall();
							}
						)
					)
					(
						(
							{
								newCompositeNode(grammarAccess.getXVariableDeclarationAccess().getNameValidIDParserRuleCall_2_0_0_1_0());
							}
							lv_name_4_0=ruleValidID
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getXVariableDeclarationRule());
								}
								set(
									$current,
									"name",
									lv_name_4_0,
									"org.eclipse.xtext.xbase.Xtype.ValidID");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
			    |
			(
				(
					{
						newCompositeNode(grammarAccess.getXVariableDeclarationAccess().getNameValidIDParserRuleCall_2_1_0());
					}
					lv_name_5_0=ruleValidID
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getXVariableDeclarationRule());
						}
						set(
							$current,
							"name",
							lv_name_5_0,
							"org.eclipse.xtext.xbase.Xtype.ValidID");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
		(
			otherlv_6='='
			{
				newLeafNode(otherlv_6, grammarAccess.getXVariableDeclarationAccess().getEqualsSignKeyword_3_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getXVariableDeclarationAccess().getRightXExpressionParserRuleCall_3_1_0());
					}
					lv_right_7_0=ruleXExpression
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getXVariableDeclarationRule());
						}
						set(
							$current,
							"right",
							lv_right_7_0,
							"org.eclipse.xtext.xbase.Xbase.XExpression");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)?
	)
;

// Entry rule entryRuleJvmFormalParameter
entryRuleJvmFormalParameter returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getJvmFormalParameterRule()); }
	iv_ruleJvmFormalParameter=ruleJvmFormalParameter
	{ $current=$iv_ruleJvmFormalParameter.current; }
	EOF;

// Rule JvmFormalParameter
ruleJvmFormalParameter returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				{
					newCompositeNode(grammarAccess.getJvmFormalParameterAccess().getParameterTypeJvmTypeReferenceParserRuleCall_0_0());
				}
				lv_parameterType_0_0=ruleJvmTypeReference
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getJvmFormalParameterRule());
					}
					set(
						$current,
						"parameterType",
						lv_parameterType_0_0,
						"org.eclipse.xtext.xbase.Xtype.JvmTypeReference");
					afterParserOrEnumRuleCall();
				}
			)
		)?
		(
			(
				{
					newCompositeNode(grammarAccess.getJvmFormalParameterAccess().getNameValidIDParserRuleCall_1_0());
				}
				lv_name_1_0=ruleValidID
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getJvmFormalParameterRule());
					}
					set(
						$current,
						"name",
						lv_name_1_0,
						"org.eclipse.xtext.xbase.Xtype.ValidID");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleFullJvmFormalParameter
entryRuleFullJvmFormalParameter returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getFullJvmFormalParameterRule()); }
	iv_ruleFullJvmFormalParameter=ruleFullJvmFormalParameter
	{ $current=$iv_ruleFullJvmFormalParameter.current; }
	EOF;

// Rule FullJvmFormalParameter
ruleFullJvmFormalParameter returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				{
					newCompositeNode(grammarAccess.getFullJvmFormalParameterAccess().getParameterTypeJvmTypeReferenceParserRuleCall_0_0());
				}
				lv_parameterType_0_0=ruleJvmTypeReference
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getFullJvmFormalParameterRule());
					}
					set(
						$current,
						"parameterType",
						lv_parameterType_0_0,
						"org.eclipse.xtext.xbase.Xtype.JvmTypeReference");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getFullJvmFormalParameterAccess().getNameValidIDParserRuleCall_1_0());
				}
				lv_name_1_0=ruleValidID
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getFullJvmFormalParameterRule());
					}
					set(
						$current,
						"name",
						lv_name_1_0,
						"org.eclipse.xtext.xbase.Xtype.ValidID");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleXFeatureCall
entryRuleXFeatureCall returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXFeatureCallRule()); }
	iv_ruleXFeatureCall=ruleXFeatureCall
	{ $current=$iv_ruleXFeatureCall.current; }
	EOF;

// Rule XFeatureCall
ruleXFeatureCall returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getXFeatureCallAccess().getXFeatureCallAction_0(),
					$current);
			}
		)
		(
			otherlv_1='<'
			{
				newLeafNode(otherlv_1, grammarAccess.getXFeatureCallAccess().getLessThanSignKeyword_1_0());
			}
			(
				(
					{
						newCompositeNode(grammarAccess.getXFeatureCallAccess().getTypeArgumentsJvmArgumentTypeReferenceParserRuleCall_1_1_0());
					}
					lv_typeArguments_2_0=ruleJvmArgumentTypeReference
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getXFeatureCallRule());
						}
						add(
							$current,
							"typeArguments",
							lv_typeArguments_2_0,
							"org.eclipse.xtext.xbase.Xtype.JvmArgumentTypeReference");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_3=','
				{
					newLeafNode(otherlv_3, grammarAccess.getXFeatureCallAccess().getCommaKeyword_1_2_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getXFeatureCallAccess().getTypeArgumentsJvmArgumentTypeReferenceParserRuleCall_1_2_1_0());
						}
						lv_typeArguments_4_0=ruleJvmArgumentTypeReference
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getXFeatureCallRule());
							}
							add(
								$current,
								"typeArguments",
								lv_typeArguments_4_0,
								"org.eclipse.xtext.xbase.Xtype.JvmArgumentTypeReference");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
			otherlv_5='>'
			{
				newLeafNode(otherlv_5, grammarAccess.getXFeatureCallAccess().getGreaterThanSignKeyword_1_3());
			}
		)?
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getXFeatureCallRule());
					}
				}
				{
					newCompositeNode(grammarAccess.getXFeatureCallAccess().getFeatureJvmIdentifiableElementCrossReference_2_0());
				}
				ruleIdOrSuper
				{
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			(
				((
					'('
				)
				)=>
				(
					lv_explicitOperationCall_7_0='('
					{
						newLeafNode(lv_explicitOperationCall_7_0, grammarAccess.getXFeatureCallAccess().getExplicitOperationCallLeftParenthesisKeyword_3_0_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getXFeatureCallRule());
						}
						setWithLastConsumed($current, "explicitOperationCall", true, "(");
					}
				)
			)
			(
				(
					((
						(
						)
						(
							(
								(
									ruleJvmFormalParameter
								)
							)
							(
								','
								(
									(
										ruleJvmFormalParameter
									)
								)
							)*
						)?
						(
							(
								'|'
							)
						)
					)
					)=>
					(
						{
							newCompositeNode(grammarAccess.getXFeatureCallAccess().getFeatureCallArgumentsXShortClosureParserRuleCall_3_1_0_0());
						}
						lv_featureCallArguments_8_0=ruleXShortClosure
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getXFeatureCallRule());
							}
							add(
								$current,
								"featureCallArguments",
								lv_featureCallArguments_8_0,
								"org.eclipse.xtext.xbase.Xbase.XShortClosure");
							afterParserOrEnumRuleCall();
						}
					)
				)
				    |
				(
					(
						(
							{
								newCompositeNode(grammarAccess.getXFeatureCallAccess().getFeatureCallArgumentsXExpressionParserRuleCall_3_1_1_0_0());
							}
							lv_featureCallArguments_9_0=ruleXExpression
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getXFeatureCallRule());
								}
								add(
									$current,
									"featureCallArguments",
									lv_featureCallArguments_9_0,
									"org.eclipse.xtext.xbase.Xbase.XExpression");
								afterParserOrEnumRuleCall();
							}
						)
					)
					(
						otherlv_10=','
						{
							newLeafNode(otherlv_10, grammarAccess.getXFeatureCallAccess().getCommaKeyword_3_1_1_1_0());
						}
						(
							(
								{
									newCompositeNode(grammarAccess.getXFeatureCallAccess().getFeatureCallArgumentsXExpressionParserRuleCall_3_1_1_1_1_0());
								}
								lv_featureCallArguments_11_0=ruleXExpression
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getXFeatureCallRule());
									}
									add(
										$current,
										"featureCallArguments",
										lv_featureCallArguments_11_0,
										"org.eclipse.xtext.xbase.Xbase.XExpression");
									afterParserOrEnumRuleCall();
								}
							)
						)
					)*
				)
			)?
			otherlv_12=')'
			{
				newLeafNode(otherlv_12, grammarAccess.getXFeatureCallAccess().getRightParenthesisKeyword_3_2());
			}
		)?
		(
			((
				(
				)
				'['
			)
			)=>
			(
				{
					newCompositeNode(grammarAccess.getXFeatureCallAccess().getFeatureCallArgumentsXClosureParserRuleCall_4_0());
				}
				lv_featureCallArguments_13_0=ruleXClosure
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getXFeatureCallRule());
					}
					add(
						$current,
						"featureCallArguments",
						lv_featureCallArguments_13_0,
						"org.eclipse.xtext.xbase.Xbase.XClosure");
					afterParserOrEnumRuleCall();
				}
			)
		)?
	)
;

// Entry rule entryRuleFeatureCallID
entryRuleFeatureCallID returns [String current=null]:
	{ newCompositeNode(grammarAccess.getFeatureCallIDRule()); }
	iv_ruleFeatureCallID=ruleFeatureCallID
	{ $current=$iv_ruleFeatureCallID.current.getText(); }
	EOF;

// Rule FeatureCallID
ruleFeatureCallID returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getFeatureCallIDAccess().getValidIDParserRuleCall_0());
		}
		this_ValidID_0=ruleValidID
		{
			$current.merge(this_ValidID_0);
		}
		{
			afterParserOrEnumRuleCall();
		}
		    |
		kw='extends'
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getFeatureCallIDAccess().getExtendsKeyword_1());
		}
		    |
		kw='static'
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getFeatureCallIDAccess().getStaticKeyword_2());
		}
		    |
		kw='import'
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getFeatureCallIDAccess().getImportKeyword_3());
		}
		    |
		kw='extension'
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getFeatureCallIDAccess().getExtensionKeyword_4());
		}
	)
;

// Entry rule entryRuleIdOrSuper
entryRuleIdOrSuper returns [String current=null]:
	{ newCompositeNode(grammarAccess.getIdOrSuperRule()); }
	iv_ruleIdOrSuper=ruleIdOrSuper
	{ $current=$iv_ruleIdOrSuper.current.getText(); }
	EOF;

// Rule IdOrSuper
ruleIdOrSuper returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getIdOrSuperAccess().getFeatureCallIDParserRuleCall_0());
		}
		this_FeatureCallID_0=ruleFeatureCallID
		{
			$current.merge(this_FeatureCallID_0);
		}
		{
			afterParserOrEnumRuleCall();
		}
		    |
		kw='super'
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getIdOrSuperAccess().getSuperKeyword_1());
		}
	)
;

// Entry rule entryRuleXConstructorCall
entryRuleXConstructorCall returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXConstructorCallRule()); }
	iv_ruleXConstructorCall=ruleXConstructorCall
	{ $current=$iv_ruleXConstructorCall.current; }
	EOF;

// Rule XConstructorCall
ruleXConstructorCall returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getXConstructorCallAccess().getXConstructorCallAction_0(),
					$current);
			}
		)
		otherlv_1='new'
		{
			newLeafNode(otherlv_1, grammarAccess.getXConstructorCallAccess().getNewKeyword_1());
		}
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getXConstructorCallRule());
					}
				}
				{
					newCompositeNode(grammarAccess.getXConstructorCallAccess().getConstructorJvmConstructorCrossReference_2_0());
				}
				ruleQualifiedName
				{
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			(
				('<')=>
				otherlv_3='<'
				{
					newLeafNode(otherlv_3, grammarAccess.getXConstructorCallAccess().getLessThanSignKeyword_3_0());
				}
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getXConstructorCallAccess().getTypeArgumentsJvmArgumentTypeReferenceParserRuleCall_3_1_0());
					}
					lv_typeArguments_4_0=ruleJvmArgumentTypeReference
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getXConstructorCallRule());
						}
						add(
							$current,
							"typeArguments",
							lv_typeArguments_4_0,
							"org.eclipse.xtext.xbase.Xtype.JvmArgumentTypeReference");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_5=','
				{
					newLeafNode(otherlv_5, grammarAccess.getXConstructorCallAccess().getCommaKeyword_3_2_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getXConstructorCallAccess().getTypeArgumentsJvmArgumentTypeReferenceParserRuleCall_3_2_1_0());
						}
						lv_typeArguments_6_0=ruleJvmArgumentTypeReference
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getXConstructorCallRule());
							}
							add(
								$current,
								"typeArguments",
								lv_typeArguments_6_0,
								"org.eclipse.xtext.xbase.Xtype.JvmArgumentTypeReference");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
			otherlv_7='>'
			{
				newLeafNode(otherlv_7, grammarAccess.getXConstructorCallAccess().getGreaterThanSignKeyword_3_3());
			}
		)?
		(
			(
				((
					'('
				)
				)=>
				(
					lv_explicitConstructorCall_8_0='('
					{
						newLeafNode(lv_explicitConstructorCall_8_0, grammarAccess.getXConstructorCallAccess().getExplicitConstructorCallLeftParenthesisKeyword_4_0_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getXConstructorCallRule());
						}
						setWithLastConsumed($current, "explicitConstructorCall", true, "(");
					}
				)
			)
			(
				(
					((
						(
						)
						(
							(
								(
									ruleJvmFormalParameter
								)
							)
							(
								','
								(
									(
										ruleJvmFormalParameter
									)
								)
							)*
						)?
						(
							(
								'|'
							)
						)
					)
					)=>
					(
						{
							newCompositeNode(grammarAccess.getXConstructorCallAccess().getArgumentsXShortClosureParserRuleCall_4_1_0_0());
						}
						lv_arguments_9_0=ruleXShortClosure
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getXConstructorCallRule());
							}
							add(
								$current,
								"arguments",
								lv_arguments_9_0,
								"org.eclipse.xtext.xbase.Xbase.XShortClosure");
							afterParserOrEnumRuleCall();
						}
					)
				)
				    |
				(
					(
						(
							{
								newCompositeNode(grammarAccess.getXConstructorCallAccess().getArgumentsXExpressionParserRuleCall_4_1_1_0_0());
							}
							lv_arguments_10_0=ruleXExpression
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getXConstructorCallRule());
								}
								add(
									$current,
									"arguments",
									lv_arguments_10_0,
									"org.eclipse.xtext.xbase.Xbase.XExpression");
								afterParserOrEnumRuleCall();
							}
						)
					)
					(
						otherlv_11=','
						{
							newLeafNode(otherlv_11, grammarAccess.getXConstructorCallAccess().getCommaKeyword_4_1_1_1_0());
						}
						(
							(
								{
									newCompositeNode(grammarAccess.getXConstructorCallAccess().getArgumentsXExpressionParserRuleCall_4_1_1_1_1_0());
								}
								lv_arguments_12_0=ruleXExpression
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getXConstructorCallRule());
									}
									add(
										$current,
										"arguments",
										lv_arguments_12_0,
										"org.eclipse.xtext.xbase.Xbase.XExpression");
									afterParserOrEnumRuleCall();
								}
							)
						)
					)*
				)
			)?
			otherlv_13=')'
			{
				newLeafNode(otherlv_13, grammarAccess.getXConstructorCallAccess().getRightParenthesisKeyword_4_2());
			}
		)?
		(
			((
				(
				)
				'['
			)
			)=>
			(
				{
					newCompositeNode(grammarAccess.getXConstructorCallAccess().getArgumentsXClosureParserRuleCall_5_0());
				}
				lv_arguments_14_0=ruleXClosure
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getXConstructorCallRule());
					}
					add(
						$current,
						"arguments",
						lv_arguments_14_0,
						"org.eclipse.xtext.xbase.Xbase.XClosure");
					afterParserOrEnumRuleCall();
				}
			)
		)?
	)
;

// Entry rule entryRuleXBooleanLiteral
entryRuleXBooleanLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXBooleanLiteralRule()); }
	iv_ruleXBooleanLiteral=ruleXBooleanLiteral
	{ $current=$iv_ruleXBooleanLiteral.current; }
	EOF;

// Rule XBooleanLiteral
ruleXBooleanLiteral returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getXBooleanLiteralAccess().getXBooleanLiteralAction_0(),
					$current);
			}
		)
		(
			otherlv_1='false'
			{
				newLeafNode(otherlv_1, grammarAccess.getXBooleanLiteralAccess().getFalseKeyword_1_0());
			}
			    |
			(
				(
					lv_isTrue_2_0='true'
					{
						newLeafNode(lv_isTrue_2_0, grammarAccess.getXBooleanLiteralAccess().getIsTrueTrueKeyword_1_1_0());
					}
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getXBooleanLiteralRule());
						}
						setWithLastConsumed($current, "isTrue", true, "true");
					}
				)
			)
		)
	)
;

// Entry rule entryRuleXNullLiteral
entryRuleXNullLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXNullLiteralRule()); }
	iv_ruleXNullLiteral=ruleXNullLiteral
	{ $current=$iv_ruleXNullLiteral.current; }
	EOF;

// Rule XNullLiteral
ruleXNullLiteral returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getXNullLiteralAccess().getXNullLiteralAction_0(),
					$current);
			}
		)
		otherlv_1='null'
		{
			newLeafNode(otherlv_1, grammarAccess.getXNullLiteralAccess().getNullKeyword_1());
		}
	)
;

// Entry rule entryRuleXNumberLiteral
entryRuleXNumberLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXNumberLiteralRule()); }
	iv_ruleXNumberLiteral=ruleXNumberLiteral
	{ $current=$iv_ruleXNumberLiteral.current; }
	EOF;

// Rule XNumberLiteral
ruleXNumberLiteral returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getXNumberLiteralAccess().getXNumberLiteralAction_0(),
					$current);
			}
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getXNumberLiteralAccess().getValueNumberParserRuleCall_1_0());
				}
				lv_value_1_0=ruleNumber
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getXNumberLiteralRule());
					}
					set(
						$current,
						"value",
						lv_value_1_0,
						"org.eclipse.xtext.xbase.Xbase.Number");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleXStringLiteral
entryRuleXStringLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXStringLiteralRule()); }
	iv_ruleXStringLiteral=ruleXStringLiteral
	{ $current=$iv_ruleXStringLiteral.current; }
	EOF;

// Rule XStringLiteral
ruleXStringLiteral returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getXStringLiteralAccess().getXStringLiteralAction_0(),
					$current);
			}
		)
		(
			(
				lv_value_1_0=RULE_STRING
				{
					newLeafNode(lv_value_1_0, grammarAccess.getXStringLiteralAccess().getValueSTRINGTerminalRuleCall_1_0());
				}
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getXStringLiteralRule());
					}
					setWithLastConsumed(
						$current,
						"value",
						lv_value_1_0,
						"org.eclipse.xtext.xbase.Xtype.STRING");
				}
			)
		)
	)
;

// Entry rule entryRuleXTypeLiteral
entryRuleXTypeLiteral returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXTypeLiteralRule()); }
	iv_ruleXTypeLiteral=ruleXTypeLiteral
	{ $current=$iv_ruleXTypeLiteral.current; }
	EOF;

// Rule XTypeLiteral
ruleXTypeLiteral returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getXTypeLiteralAccess().getXTypeLiteralAction_0(),
					$current);
			}
		)
		otherlv_1='typeof'
		{
			newLeafNode(otherlv_1, grammarAccess.getXTypeLiteralAccess().getTypeofKeyword_1());
		}
		otherlv_2='('
		{
			newLeafNode(otherlv_2, grammarAccess.getXTypeLiteralAccess().getLeftParenthesisKeyword_2());
		}
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getXTypeLiteralRule());
					}
				}
				{
					newCompositeNode(grammarAccess.getXTypeLiteralAccess().getTypeJvmTypeCrossReference_3_0());
				}
				ruleQualifiedName
				{
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getXTypeLiteralAccess().getArrayDimensionsArrayBracketsParserRuleCall_4_0());
				}
				lv_arrayDimensions_4_0=ruleArrayBrackets
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getXTypeLiteralRule());
					}
					add(
						$current,
						"arrayDimensions",
						lv_arrayDimensions_4_0,
						"org.eclipse.xtext.xbase.Xtype.ArrayBrackets");
					afterParserOrEnumRuleCall();
				}
			)
		)*
		otherlv_5=')'
		{
			newLeafNode(otherlv_5, grammarAccess.getXTypeLiteralAccess().getRightParenthesisKeyword_5());
		}
	)
;

// Entry rule entryRuleXThrowExpression
entryRuleXThrowExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXThrowExpressionRule()); }
	iv_ruleXThrowExpression=ruleXThrowExpression
	{ $current=$iv_ruleXThrowExpression.current; }
	EOF;

// Rule XThrowExpression
ruleXThrowExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getXThrowExpressionAccess().getXThrowExpressionAction_0(),
					$current);
			}
		)
		otherlv_1='throw'
		{
			newLeafNode(otherlv_1, grammarAccess.getXThrowExpressionAccess().getThrowKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getXThrowExpressionAccess().getExpressionXExpressionParserRuleCall_2_0());
				}
				lv_expression_2_0=ruleXExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getXThrowExpressionRule());
					}
					set(
						$current,
						"expression",
						lv_expression_2_0,
						"org.eclipse.xtext.xbase.Xbase.XExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleXReturnExpression
entryRuleXReturnExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXReturnExpressionRule()); }
	iv_ruleXReturnExpression=ruleXReturnExpression
	{ $current=$iv_ruleXReturnExpression.current; }
	EOF;

// Rule XReturnExpression
ruleXReturnExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getXReturnExpressionAccess().getXReturnExpressionAction_0(),
					$current);
			}
		)
		otherlv_1='return'
		{
			newLeafNode(otherlv_1, grammarAccess.getXReturnExpressionAccess().getReturnKeyword_1());
		}
		(
			('extends' | 'static' | 'import' | 'extension' | '!' | '-' | '+' | 'new' | '{' | 'switch' | 'synchronized' | '<' | 'super' | '#' | '[' | 'false' | 'true' | 'null' | 'typeof' | 'if' | 'for' | 'while' | 'do' | 'throw' | 'return' | 'try' | '(' | RULE_ID | RULE_HEX | RULE_INT | RULE_DECIMAL | RULE_STRING)=>
			(
				{
					newCompositeNode(grammarAccess.getXReturnExpressionAccess().getExpressionXExpressionParserRuleCall_2_0());
				}
				lv_expression_2_0=ruleXExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getXReturnExpressionRule());
					}
					set(
						$current,
						"expression",
						lv_expression_2_0,
						"org.eclipse.xtext.xbase.Xbase.XExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)?
	)
;

// Entry rule entryRuleXTryCatchFinallyExpression
entryRuleXTryCatchFinallyExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXTryCatchFinallyExpressionRule()); }
	iv_ruleXTryCatchFinallyExpression=ruleXTryCatchFinallyExpression
	{ $current=$iv_ruleXTryCatchFinallyExpression.current; }
	EOF;

// Rule XTryCatchFinallyExpression
ruleXTryCatchFinallyExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getXTryCatchFinallyExpressionAccess().getXTryCatchFinallyExpressionAction_0(),
					$current);
			}
		)
		otherlv_1='try'
		{
			newLeafNode(otherlv_1, grammarAccess.getXTryCatchFinallyExpressionAccess().getTryKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getXTryCatchFinallyExpressionAccess().getExpressionXExpressionParserRuleCall_2_0());
				}
				lv_expression_2_0=ruleXExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getXTryCatchFinallyExpressionRule());
					}
					set(
						$current,
						"expression",
						lv_expression_2_0,
						"org.eclipse.xtext.xbase.Xbase.XExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			(
				(
					('catch')=>
					(
						{
							newCompositeNode(grammarAccess.getXTryCatchFinallyExpressionAccess().getCatchClausesXCatchClauseParserRuleCall_3_0_0_0());
						}
						lv_catchClauses_3_0=ruleXCatchClause
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getXTryCatchFinallyExpressionRule());
							}
							add(
								$current,
								"catchClauses",
								lv_catchClauses_3_0,
								"org.eclipse.xtext.xbase.Xbase.XCatchClause");
							afterParserOrEnumRuleCall();
						}
					)
				)+
				(
					(
						('finally')=>
						otherlv_4='finally'
						{
							newLeafNode(otherlv_4, grammarAccess.getXTryCatchFinallyExpressionAccess().getFinallyKeyword_3_0_1_0());
						}
					)
					(
						(
							{
								newCompositeNode(grammarAccess.getXTryCatchFinallyExpressionAccess().getFinallyExpressionXExpressionParserRuleCall_3_0_1_1_0());
							}
							lv_finallyExpression_5_0=ruleXExpression
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getXTryCatchFinallyExpressionRule());
								}
								set(
									$current,
									"finallyExpression",
									lv_finallyExpression_5_0,
									"org.eclipse.xtext.xbase.Xbase.XExpression");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)?
			)
			    |
			(
				otherlv_6='finally'
				{
					newLeafNode(otherlv_6, grammarAccess.getXTryCatchFinallyExpressionAccess().getFinallyKeyword_3_1_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getXTryCatchFinallyExpressionAccess().getFinallyExpressionXExpressionParserRuleCall_3_1_1_0());
						}
						lv_finallyExpression_7_0=ruleXExpression
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getXTryCatchFinallyExpressionRule());
							}
							set(
								$current,
								"finallyExpression",
								lv_finallyExpression_7_0,
								"org.eclipse.xtext.xbase.Xbase.XExpression");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)
		)
	)
;

// Entry rule entryRuleXSynchronizedExpression
entryRuleXSynchronizedExpression returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXSynchronizedExpressionRule()); }
	iv_ruleXSynchronizedExpression=ruleXSynchronizedExpression
	{ $current=$iv_ruleXSynchronizedExpression.current; }
	EOF;

// Rule XSynchronizedExpression
ruleXSynchronizedExpression returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			((
				(
				)
				'synchronized'
				'('
			)
			)=>
			(
				(
					{
						$current = forceCreateModelElement(
							grammarAccess.getXSynchronizedExpressionAccess().getXSynchronizedExpressionAction_0_0_0(),
							$current);
					}
				)
				otherlv_1='synchronized'
				{
					newLeafNode(otherlv_1, grammarAccess.getXSynchronizedExpressionAccess().getSynchronizedKeyword_0_0_1());
				}
				otherlv_2='('
				{
					newLeafNode(otherlv_2, grammarAccess.getXSynchronizedExpressionAccess().getLeftParenthesisKeyword_0_0_2());
				}
			)
		)
		(
			(
				{
					newCompositeNode(grammarAccess.getXSynchronizedExpressionAccess().getParamXExpressionParserRuleCall_1_0());
				}
				lv_param_3_0=ruleXExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getXSynchronizedExpressionRule());
					}
					set(
						$current,
						"param",
						lv_param_3_0,
						"org.eclipse.xtext.xbase.Xbase.XExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_4=')'
		{
			newLeafNode(otherlv_4, grammarAccess.getXSynchronizedExpressionAccess().getRightParenthesisKeyword_2());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getXSynchronizedExpressionAccess().getExpressionXExpressionParserRuleCall_3_0());
				}
				lv_expression_5_0=ruleXExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getXSynchronizedExpressionRule());
					}
					set(
						$current,
						"expression",
						lv_expression_5_0,
						"org.eclipse.xtext.xbase.Xbase.XExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleXCatchClause
entryRuleXCatchClause returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXCatchClauseRule()); }
	iv_ruleXCatchClause=ruleXCatchClause
	{ $current=$iv_ruleXCatchClause.current; }
	EOF;

// Rule XCatchClause
ruleXCatchClause returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			('catch')=>
			otherlv_0='catch'
			{
				newLeafNode(otherlv_0, grammarAccess.getXCatchClauseAccess().getCatchKeyword_0());
			}
		)
		otherlv_1='('
		{
			newLeafNode(otherlv_1, grammarAccess.getXCatchClauseAccess().getLeftParenthesisKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getXCatchClauseAccess().getDeclaredParamFullJvmFormalParameterParserRuleCall_2_0());
				}
				lv_declaredParam_2_0=ruleFullJvmFormalParameter
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getXCatchClauseRule());
					}
					set(
						$current,
						"declaredParam",
						lv_declaredParam_2_0,
						"org.eclipse.xtext.xbase.Xbase.FullJvmFormalParameter");
					afterParserOrEnumRuleCall();
				}
			)
		)
		otherlv_3=')'
		{
			newLeafNode(otherlv_3, grammarAccess.getXCatchClauseAccess().getRightParenthesisKeyword_3());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getXCatchClauseAccess().getExpressionXExpressionParserRuleCall_4_0());
				}
				lv_expression_4_0=ruleXExpression
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getXCatchClauseRule());
					}
					set(
						$current,
						"expression",
						lv_expression_4_0,
						"org.eclipse.xtext.xbase.Xbase.XExpression");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleQualifiedName
entryRuleQualifiedName returns [String current=null]:
	{ newCompositeNode(grammarAccess.getQualifiedNameRule()); }
	iv_ruleQualifiedName=ruleQualifiedName
	{ $current=$iv_ruleQualifiedName.current.getText(); }
	EOF;

// Rule QualifiedName
ruleQualifiedName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getQualifiedNameAccess().getValidIDParserRuleCall_0());
		}
		this_ValidID_0=ruleValidID
		{
			$current.merge(this_ValidID_0);
		}
		{
			afterParserOrEnumRuleCall();
		}
		(
			(
				('.')=>
				kw='.'
				{
					$current.merge(kw);
					newLeafNode(kw, grammarAccess.getQualifiedNameAccess().getFullStopKeyword_1_0());
				}
			)
			{
				newCompositeNode(grammarAccess.getQualifiedNameAccess().getValidIDParserRuleCall_1_1());
			}
			this_ValidID_2=ruleValidID
			{
				$current.merge(this_ValidID_2);
			}
			{
				afterParserOrEnumRuleCall();
			}
		)*
	)
;

// Entry rule entryRuleNumber
entryRuleNumber returns [String current=null]@init {
	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens();
}:
	{ newCompositeNode(grammarAccess.getNumberRule()); }
	iv_ruleNumber=ruleNumber
	{ $current=$iv_ruleNumber.current.getText(); }
	EOF;
finally {
	myHiddenTokenState.restore();
}

// Rule Number
ruleNumber returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens();
}
@after {
	leaveRule();
}:
	(
		this_HEX_0=RULE_HEX
		{
			$current.merge(this_HEX_0);
		}
		{
			newLeafNode(this_HEX_0, grammarAccess.getNumberAccess().getHEXTerminalRuleCall_0());
		}
		    |
		(
			(
				this_INT_1=RULE_INT
				{
					$current.merge(this_INT_1);
				}
				{
					newLeafNode(this_INT_1, grammarAccess.getNumberAccess().getINTTerminalRuleCall_1_0_0());
				}
				    |
				this_DECIMAL_2=RULE_DECIMAL
				{
					$current.merge(this_DECIMAL_2);
				}
				{
					newLeafNode(this_DECIMAL_2, grammarAccess.getNumberAccess().getDECIMALTerminalRuleCall_1_0_1());
				}
			)
			(
				kw='.'
				{
					$current.merge(kw);
					newLeafNode(kw, grammarAccess.getNumberAccess().getFullStopKeyword_1_1_0());
				}
				(
					this_INT_4=RULE_INT
					{
						$current.merge(this_INT_4);
					}
					{
						newLeafNode(this_INT_4, grammarAccess.getNumberAccess().getINTTerminalRuleCall_1_1_1_0());
					}
					    |
					this_DECIMAL_5=RULE_DECIMAL
					{
						$current.merge(this_DECIMAL_5);
					}
					{
						newLeafNode(this_DECIMAL_5, grammarAccess.getNumberAccess().getDECIMALTerminalRuleCall_1_1_1_1());
					}
				)
			)?
		)
	)
;
finally {
	myHiddenTokenState.restore();
}

// Entry rule entryRuleJvmTypeReference
entryRuleJvmTypeReference returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getJvmTypeReferenceRule()); }
	iv_ruleJvmTypeReference=ruleJvmTypeReference
	{ $current=$iv_ruleJvmTypeReference.current; }
	EOF;

// Rule JvmTypeReference
ruleJvmTypeReference returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				newCompositeNode(grammarAccess.getJvmTypeReferenceAccess().getJvmParameterizedTypeReferenceParserRuleCall_0_0());
			}
			this_JvmParameterizedTypeReference_0=ruleJvmParameterizedTypeReference
			{
				$current = $this_JvmParameterizedTypeReference_0.current;
				afterParserOrEnumRuleCall();
			}
			(
				((
					(
					)
					ruleArrayBrackets
				)
				)=>
				(
					(
						{
							$current = forceCreateModelElementAndSet(
								grammarAccess.getJvmTypeReferenceAccess().getJvmGenericArrayTypeReferenceComponentTypeAction_0_1_0_0(),
								$current);
						}
					)
					{
						newCompositeNode(grammarAccess.getJvmTypeReferenceAccess().getArrayBracketsParserRuleCall_0_1_0_1());
					}
					ruleArrayBrackets
					{
						afterParserOrEnumRuleCall();
					}
				)
			)*
		)
		    |
		{
			newCompositeNode(grammarAccess.getJvmTypeReferenceAccess().getXFunctionTypeRefParserRuleCall_1());
		}
		this_XFunctionTypeRef_3=ruleXFunctionTypeRef
		{
			$current = $this_XFunctionTypeRef_3.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleArrayBrackets
entryRuleArrayBrackets returns [String current=null]:
	{ newCompositeNode(grammarAccess.getArrayBracketsRule()); }
	iv_ruleArrayBrackets=ruleArrayBrackets
	{ $current=$iv_ruleArrayBrackets.current.getText(); }
	EOF;

// Rule ArrayBrackets
ruleArrayBrackets returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		kw='['
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getArrayBracketsAccess().getLeftSquareBracketKeyword_0());
		}
		kw=']'
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getArrayBracketsAccess().getRightSquareBracketKeyword_1());
		}
	)
;

// Entry rule entryRuleXFunctionTypeRef
entryRuleXFunctionTypeRef returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXFunctionTypeRefRule()); }
	iv_ruleXFunctionTypeRef=ruleXFunctionTypeRef
	{ $current=$iv_ruleXFunctionTypeRef.current; }
	EOF;

// Rule XFunctionTypeRef
ruleXFunctionTypeRef returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			otherlv_0='('
			{
				newLeafNode(otherlv_0, grammarAccess.getXFunctionTypeRefAccess().getLeftParenthesisKeyword_0_0());
			}
			(
				(
					(
						{
							newCompositeNode(grammarAccess.getXFunctionTypeRefAccess().getParamTypesJvmTypeReferenceParserRuleCall_0_1_0_0());
						}
						lv_paramTypes_1_0=ruleJvmTypeReference
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getXFunctionTypeRefRule());
							}
							add(
								$current,
								"paramTypes",
								lv_paramTypes_1_0,
								"org.eclipse.xtext.xbase.Xtype.JvmTypeReference");
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					otherlv_2=','
					{
						newLeafNode(otherlv_2, grammarAccess.getXFunctionTypeRefAccess().getCommaKeyword_0_1_1_0());
					}
					(
						(
							{
								newCompositeNode(grammarAccess.getXFunctionTypeRefAccess().getParamTypesJvmTypeReferenceParserRuleCall_0_1_1_1_0());
							}
							lv_paramTypes_3_0=ruleJvmTypeReference
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getXFunctionTypeRefRule());
								}
								add(
									$current,
									"paramTypes",
									lv_paramTypes_3_0,
									"org.eclipse.xtext.xbase.Xtype.JvmTypeReference");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)*
			)?
			otherlv_4=')'
			{
				newLeafNode(otherlv_4, grammarAccess.getXFunctionTypeRefAccess().getRightParenthesisKeyword_0_2());
			}
		)?
		otherlv_5='=>'
		{
			newLeafNode(otherlv_5, grammarAccess.getXFunctionTypeRefAccess().getEqualsSignGreaterThanSignKeyword_1());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getXFunctionTypeRefAccess().getReturnTypeJvmTypeReferenceParserRuleCall_2_0());
				}
				lv_returnType_6_0=ruleJvmTypeReference
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getXFunctionTypeRefRule());
					}
					set(
						$current,
						"returnType",
						lv_returnType_6_0,
						"org.eclipse.xtext.xbase.Xtype.JvmTypeReference");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleJvmParameterizedTypeReference
entryRuleJvmParameterizedTypeReference returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getJvmParameterizedTypeReferenceRule()); }
	iv_ruleJvmParameterizedTypeReference=ruleJvmParameterizedTypeReference
	{ $current=$iv_ruleJvmParameterizedTypeReference.current; }
	EOF;

// Rule JvmParameterizedTypeReference
ruleJvmParameterizedTypeReference returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			(
				{
					if ($current==null) {
						$current = createModelElement(grammarAccess.getJvmParameterizedTypeReferenceRule());
					}
				}
				{
					newCompositeNode(grammarAccess.getJvmParameterizedTypeReferenceAccess().getTypeJvmTypeCrossReference_0_0());
				}
				ruleQualifiedName
				{
					afterParserOrEnumRuleCall();
				}
			)
		)
		(
			(
				('<')=>
				otherlv_1='<'
				{
					newLeafNode(otherlv_1, grammarAccess.getJvmParameterizedTypeReferenceAccess().getLessThanSignKeyword_1_0());
				}
			)
			(
				(
					{
						newCompositeNode(grammarAccess.getJvmParameterizedTypeReferenceAccess().getArgumentsJvmArgumentTypeReferenceParserRuleCall_1_1_0());
					}
					lv_arguments_2_0=ruleJvmArgumentTypeReference
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getJvmParameterizedTypeReferenceRule());
						}
						add(
							$current,
							"arguments",
							lv_arguments_2_0,
							"org.eclipse.xtext.xbase.Xtype.JvmArgumentTypeReference");
						afterParserOrEnumRuleCall();
					}
				)
			)
			(
				otherlv_3=','
				{
					newLeafNode(otherlv_3, grammarAccess.getJvmParameterizedTypeReferenceAccess().getCommaKeyword_1_2_0());
				}
				(
					(
						{
							newCompositeNode(grammarAccess.getJvmParameterizedTypeReferenceAccess().getArgumentsJvmArgumentTypeReferenceParserRuleCall_1_2_1_0());
						}
						lv_arguments_4_0=ruleJvmArgumentTypeReference
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getJvmParameterizedTypeReferenceRule());
							}
							add(
								$current,
								"arguments",
								lv_arguments_4_0,
								"org.eclipse.xtext.xbase.Xtype.JvmArgumentTypeReference");
							afterParserOrEnumRuleCall();
						}
					)
				)
			)*
			otherlv_5='>'
			{
				newLeafNode(otherlv_5, grammarAccess.getJvmParameterizedTypeReferenceAccess().getGreaterThanSignKeyword_1_3());
			}
			(
				(
					((
						(
						)
						'.'
					)
					)=>
					(
						(
							{
								$current = forceCreateModelElementAndSet(
									grammarAccess.getJvmParameterizedTypeReferenceAccess().getJvmInnerTypeReferenceOuterAction_1_4_0_0_0(),
									$current);
							}
						)
						otherlv_7='.'
						{
							newLeafNode(otherlv_7, grammarAccess.getJvmParameterizedTypeReferenceAccess().getFullStopKeyword_1_4_0_0_1());
						}
					)
				)
				(
					(
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getJvmParameterizedTypeReferenceRule());
							}
						}
						{
							newCompositeNode(grammarAccess.getJvmParameterizedTypeReferenceAccess().getTypeJvmTypeCrossReference_1_4_1_0());
						}
						ruleValidID
						{
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					(
						('<')=>
						otherlv_9='<'
						{
							newLeafNode(otherlv_9, grammarAccess.getJvmParameterizedTypeReferenceAccess().getLessThanSignKeyword_1_4_2_0());
						}
					)
					(
						(
							{
								newCompositeNode(grammarAccess.getJvmParameterizedTypeReferenceAccess().getArgumentsJvmArgumentTypeReferenceParserRuleCall_1_4_2_1_0());
							}
							lv_arguments_10_0=ruleJvmArgumentTypeReference
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getJvmParameterizedTypeReferenceRule());
								}
								add(
									$current,
									"arguments",
									lv_arguments_10_0,
									"org.eclipse.xtext.xbase.Xtype.JvmArgumentTypeReference");
								afterParserOrEnumRuleCall();
							}
						)
					)
					(
						otherlv_11=','
						{
							newLeafNode(otherlv_11, grammarAccess.getJvmParameterizedTypeReferenceAccess().getCommaKeyword_1_4_2_2_0());
						}
						(
							(
								{
									newCompositeNode(grammarAccess.getJvmParameterizedTypeReferenceAccess().getArgumentsJvmArgumentTypeReferenceParserRuleCall_1_4_2_2_1_0());
								}
								lv_arguments_12_0=ruleJvmArgumentTypeReference
								{
									if ($current==null) {
										$current = createModelElementForParent(grammarAccess.getJvmParameterizedTypeReferenceRule());
									}
									add(
										$current,
										"arguments",
										lv_arguments_12_0,
										"org.eclipse.xtext.xbase.Xtype.JvmArgumentTypeReference");
									afterParserOrEnumRuleCall();
								}
							)
						)
					)*
					otherlv_13='>'
					{
						newLeafNode(otherlv_13, grammarAccess.getJvmParameterizedTypeReferenceAccess().getGreaterThanSignKeyword_1_4_2_3());
					}
				)?
			)*
		)?
	)
;

// Entry rule entryRuleJvmArgumentTypeReference
entryRuleJvmArgumentTypeReference returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getJvmArgumentTypeReferenceRule()); }
	iv_ruleJvmArgumentTypeReference=ruleJvmArgumentTypeReference
	{ $current=$iv_ruleJvmArgumentTypeReference.current; }
	EOF;

// Rule JvmArgumentTypeReference
ruleJvmArgumentTypeReference returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getJvmArgumentTypeReferenceAccess().getJvmTypeReferenceParserRuleCall_0());
		}
		this_JvmTypeReference_0=ruleJvmTypeReference
		{
			$current = $this_JvmTypeReference_0.current;
			afterParserOrEnumRuleCall();
		}
		    |
		{
			newCompositeNode(grammarAccess.getJvmArgumentTypeReferenceAccess().getJvmWildcardTypeReferenceParserRuleCall_1());
		}
		this_JvmWildcardTypeReference_1=ruleJvmWildcardTypeReference
		{
			$current = $this_JvmWildcardTypeReference_1.current;
			afterParserOrEnumRuleCall();
		}
	)
;

// Entry rule entryRuleJvmWildcardTypeReference
entryRuleJvmWildcardTypeReference returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getJvmWildcardTypeReferenceRule()); }
	iv_ruleJvmWildcardTypeReference=ruleJvmWildcardTypeReference
	{ $current=$iv_ruleJvmWildcardTypeReference.current; }
	EOF;

// Rule JvmWildcardTypeReference
ruleJvmWildcardTypeReference returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		(
			{
				$current = forceCreateModelElement(
					grammarAccess.getJvmWildcardTypeReferenceAccess().getJvmWildcardTypeReferenceAction_0(),
					$current);
			}
		)
		otherlv_1='?'
		{
			newLeafNode(otherlv_1, grammarAccess.getJvmWildcardTypeReferenceAccess().getQuestionMarkKeyword_1());
		}
		(
			(
				(
					(
						{
							newCompositeNode(grammarAccess.getJvmWildcardTypeReferenceAccess().getConstraintsJvmUpperBoundParserRuleCall_2_0_0_0());
						}
						lv_constraints_2_0=ruleJvmUpperBound
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getJvmWildcardTypeReferenceRule());
							}
							add(
								$current,
								"constraints",
								lv_constraints_2_0,
								"org.eclipse.xtext.xbase.Xtype.JvmUpperBound");
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getJvmWildcardTypeReferenceAccess().getConstraintsJvmUpperBoundAndedParserRuleCall_2_0_1_0());
						}
						lv_constraints_3_0=ruleJvmUpperBoundAnded
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getJvmWildcardTypeReferenceRule());
							}
							add(
								$current,
								"constraints",
								lv_constraints_3_0,
								"org.eclipse.xtext.xbase.Xtype.JvmUpperBoundAnded");
							afterParserOrEnumRuleCall();
						}
					)
				)*
			)
			    |
			(
				(
					(
						{
							newCompositeNode(grammarAccess.getJvmWildcardTypeReferenceAccess().getConstraintsJvmLowerBoundParserRuleCall_2_1_0_0());
						}
						lv_constraints_4_0=ruleJvmLowerBound
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getJvmWildcardTypeReferenceRule());
							}
							add(
								$current,
								"constraints",
								lv_constraints_4_0,
								"org.eclipse.xtext.xbase.Xtype.JvmLowerBound");
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					(
						{
							newCompositeNode(grammarAccess.getJvmWildcardTypeReferenceAccess().getConstraintsJvmLowerBoundAndedParserRuleCall_2_1_1_0());
						}
						lv_constraints_5_0=ruleJvmLowerBoundAnded
						{
							if ($current==null) {
								$current = createModelElementForParent(grammarAccess.getJvmWildcardTypeReferenceRule());
							}
							add(
								$current,
								"constraints",
								lv_constraints_5_0,
								"org.eclipse.xtext.xbase.Xtype.JvmLowerBoundAnded");
							afterParserOrEnumRuleCall();
						}
					)
				)*
			)
		)?
	)
;

// Entry rule entryRuleJvmUpperBound
entryRuleJvmUpperBound returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getJvmUpperBoundRule()); }
	iv_ruleJvmUpperBound=ruleJvmUpperBound
	{ $current=$iv_ruleJvmUpperBound.current; }
	EOF;

// Rule JvmUpperBound
ruleJvmUpperBound returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0='extends'
		{
			newLeafNode(otherlv_0, grammarAccess.getJvmUpperBoundAccess().getExtendsKeyword_0());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getJvmUpperBoundAccess().getTypeReferenceJvmTypeReferenceParserRuleCall_1_0());
				}
				lv_typeReference_1_0=ruleJvmTypeReference
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getJvmUpperBoundRule());
					}
					set(
						$current,
						"typeReference",
						lv_typeReference_1_0,
						"org.eclipse.xtext.xbase.Xtype.JvmTypeReference");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleJvmUpperBoundAnded
entryRuleJvmUpperBoundAnded returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getJvmUpperBoundAndedRule()); }
	iv_ruleJvmUpperBoundAnded=ruleJvmUpperBoundAnded
	{ $current=$iv_ruleJvmUpperBoundAnded.current; }
	EOF;

// Rule JvmUpperBoundAnded
ruleJvmUpperBoundAnded returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0='&'
		{
			newLeafNode(otherlv_0, grammarAccess.getJvmUpperBoundAndedAccess().getAmpersandKeyword_0());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getJvmUpperBoundAndedAccess().getTypeReferenceJvmTypeReferenceParserRuleCall_1_0());
				}
				lv_typeReference_1_0=ruleJvmTypeReference
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getJvmUpperBoundAndedRule());
					}
					set(
						$current,
						"typeReference",
						lv_typeReference_1_0,
						"org.eclipse.xtext.xbase.Xtype.JvmTypeReference");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleJvmLowerBound
entryRuleJvmLowerBound returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getJvmLowerBoundRule()); }
	iv_ruleJvmLowerBound=ruleJvmLowerBound
	{ $current=$iv_ruleJvmLowerBound.current; }
	EOF;

// Rule JvmLowerBound
ruleJvmLowerBound returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0='super'
		{
			newLeafNode(otherlv_0, grammarAccess.getJvmLowerBoundAccess().getSuperKeyword_0());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getJvmLowerBoundAccess().getTypeReferenceJvmTypeReferenceParserRuleCall_1_0());
				}
				lv_typeReference_1_0=ruleJvmTypeReference
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getJvmLowerBoundRule());
					}
					set(
						$current,
						"typeReference",
						lv_typeReference_1_0,
						"org.eclipse.xtext.xbase.Xtype.JvmTypeReference");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleJvmLowerBoundAnded
entryRuleJvmLowerBoundAnded returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getJvmLowerBoundAndedRule()); }
	iv_ruleJvmLowerBoundAnded=ruleJvmLowerBoundAnded
	{ $current=$iv_ruleJvmLowerBoundAnded.current; }
	EOF;

// Rule JvmLowerBoundAnded
ruleJvmLowerBoundAnded returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0='&'
		{
			newLeafNode(otherlv_0, grammarAccess.getJvmLowerBoundAndedAccess().getAmpersandKeyword_0());
		}
		(
			(
				{
					newCompositeNode(grammarAccess.getJvmLowerBoundAndedAccess().getTypeReferenceJvmTypeReferenceParserRuleCall_1_0());
				}
				lv_typeReference_1_0=ruleJvmTypeReference
				{
					if ($current==null) {
						$current = createModelElementForParent(grammarAccess.getJvmLowerBoundAndedRule());
					}
					set(
						$current,
						"typeReference",
						lv_typeReference_1_0,
						"org.eclipse.xtext.xbase.Xtype.JvmTypeReference");
					afterParserOrEnumRuleCall();
				}
			)
		)
	)
;

// Entry rule entryRuleQualifiedNameWithWildcard
entryRuleQualifiedNameWithWildcard returns [String current=null]:
	{ newCompositeNode(grammarAccess.getQualifiedNameWithWildcardRule()); }
	iv_ruleQualifiedNameWithWildcard=ruleQualifiedNameWithWildcard
	{ $current=$iv_ruleQualifiedNameWithWildcard.current.getText(); }
	EOF;

// Rule QualifiedNameWithWildcard
ruleQualifiedNameWithWildcard returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getQualifiedNameWithWildcardAccess().getQualifiedNameParserRuleCall_0());
		}
		this_QualifiedName_0=ruleQualifiedName
		{
			$current.merge(this_QualifiedName_0);
		}
		{
			afterParserOrEnumRuleCall();
		}
		kw='.'
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getQualifiedNameWithWildcardAccess().getFullStopKeyword_1());
		}
		kw='*'
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getQualifiedNameWithWildcardAccess().getAsteriskKeyword_2());
		}
	)
;

// Entry rule entryRuleValidID
entryRuleValidID returns [String current=null]:
	{ newCompositeNode(grammarAccess.getValidIDRule()); }
	iv_ruleValidID=ruleValidID
	{ $current=$iv_ruleValidID.current.getText(); }
	EOF;

// Rule ValidID
ruleValidID returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	this_ID_0=RULE_ID
	{
		$current.merge(this_ID_0);
	}
	{
		newLeafNode(this_ID_0, grammarAccess.getValidIDAccess().getIDTerminalRuleCall());
	}
;

// Entry rule entryRuleXImportDeclaration
entryRuleXImportDeclaration returns [EObject current=null]:
	{ newCompositeNode(grammarAccess.getXImportDeclarationRule()); }
	iv_ruleXImportDeclaration=ruleXImportDeclaration
	{ $current=$iv_ruleXImportDeclaration.current; }
	EOF;

// Rule XImportDeclaration
ruleXImportDeclaration returns [EObject current=null]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		otherlv_0='import'
		{
			newLeafNode(otherlv_0, grammarAccess.getXImportDeclarationAccess().getImportKeyword_0());
		}
		(
			(
				(
					(
						lv_static_1_0='static'
						{
							newLeafNode(lv_static_1_0, grammarAccess.getXImportDeclarationAccess().getStaticStaticKeyword_1_0_0_0());
						}
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getXImportDeclarationRule());
							}
							setWithLastConsumed($current, "static", true, "static");
						}
					)
				)
				(
					(
						lv_extension_2_0='extension'
						{
							newLeafNode(lv_extension_2_0, grammarAccess.getXImportDeclarationAccess().getExtensionExtensionKeyword_1_0_1_0());
						}
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getXImportDeclarationRule());
							}
							setWithLastConsumed($current, "extension", true, "extension");
						}
					)
				)?
				(
					(
						{
							if ($current==null) {
								$current = createModelElement(grammarAccess.getXImportDeclarationRule());
							}
						}
						{
							newCompositeNode(grammarAccess.getXImportDeclarationAccess().getImportedTypeJvmDeclaredTypeCrossReference_1_0_2_0());
						}
						ruleQualifiedNameInStaticImport
						{
							afterParserOrEnumRuleCall();
						}
					)
				)
				(
					(
						(
							lv_wildcard_4_0='*'
							{
								newLeafNode(lv_wildcard_4_0, grammarAccess.getXImportDeclarationAccess().getWildcardAsteriskKeyword_1_0_3_0_0());
							}
							{
								if ($current==null) {
									$current = createModelElement(grammarAccess.getXImportDeclarationRule());
								}
								setWithLastConsumed($current, "wildcard", true, "*");
							}
						)
					)
					    |
					(
						(
							{
								newCompositeNode(grammarAccess.getXImportDeclarationAccess().getMemberNameValidIDParserRuleCall_1_0_3_1_0());
							}
							lv_memberName_5_0=ruleValidID
							{
								if ($current==null) {
									$current = createModelElementForParent(grammarAccess.getXImportDeclarationRule());
								}
								set(
									$current,
									"memberName",
									lv_memberName_5_0,
									"org.eclipse.xtext.xbase.Xtype.ValidID");
								afterParserOrEnumRuleCall();
							}
						)
					)
				)
			)
			    |
			(
				(
					{
						if ($current==null) {
							$current = createModelElement(grammarAccess.getXImportDeclarationRule());
						}
					}
					{
						newCompositeNode(grammarAccess.getXImportDeclarationAccess().getImportedTypeJvmDeclaredTypeCrossReference_1_1_0());
					}
					ruleQualifiedName
					{
						afterParserOrEnumRuleCall();
					}
				)
			)
			    |
			(
				(
					{
						newCompositeNode(grammarAccess.getXImportDeclarationAccess().getImportedNamespaceQualifiedNameWithWildcardParserRuleCall_1_2_0());
					}
					lv_importedNamespace_7_0=ruleQualifiedNameWithWildcard
					{
						if ($current==null) {
							$current = createModelElementForParent(grammarAccess.getXImportDeclarationRule());
						}
						set(
							$current,
							"importedNamespace",
							lv_importedNamespace_7_0,
							"org.eclipse.xtext.xbase.Xtype.QualifiedNameWithWildcard");
						afterParserOrEnumRuleCall();
					}
				)
			)
		)
		(
			otherlv_8=';'
			{
				newLeafNode(otherlv_8, grammarAccess.getXImportDeclarationAccess().getSemicolonKeyword_2());
			}
		)?
	)
;

// Entry rule entryRuleQualifiedNameInStaticImport
entryRuleQualifiedNameInStaticImport returns [String current=null]:
	{ newCompositeNode(grammarAccess.getQualifiedNameInStaticImportRule()); }
	iv_ruleQualifiedNameInStaticImport=ruleQualifiedNameInStaticImport
	{ $current=$iv_ruleQualifiedNameInStaticImport.current.getText(); }
	EOF;

// Rule QualifiedNameInStaticImport
ruleQualifiedNameInStaticImport returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()]
@init {
	enterRule();
}
@after {
	leaveRule();
}:
	(
		{
			newCompositeNode(grammarAccess.getQualifiedNameInStaticImportAccess().getValidIDParserRuleCall_0());
		}
		this_ValidID_0=ruleValidID
		{
			$current.merge(this_ValidID_0);
		}
		{
			afterParserOrEnumRuleCall();
		}
		kw='.'
		{
			$current.merge(kw);
			newLeafNode(kw, grammarAccess.getQualifiedNameInStaticImportAccess().getFullStopKeyword_1());
		}
	)+
;

RULE_HEX : ('0x'|'0X') ('0'..'9'|'a'..'f'|'A'..'F'|'_')+ ('#' (('b'|'B') ('i'|'I')|('l'|'L')))?;

RULE_INT : '0'..'9' ('0'..'9'|'_')*;

RULE_DECIMAL : RULE_INT (('e'|'E') ('+'|'-')? RULE_INT)? (('b'|'B') ('i'|'I'|'d'|'D')|('l'|'L'|'d'|'D'|'f'|'F'))?;

RULE_ID : '^'? ('a'..'z'|'A'..'Z'|'$'|'_') ('a'..'z'|'A'..'Z'|'$'|'_'|'0'..'9')*;

RULE_STRING : ('"' ('\\' .|~(('\\'|'"')))* '"'?|'\'' ('\\' .|~(('\\'|'\'')))* '\''?);

RULE_ML_COMMENT : '/*' ( options {greedy=false;} : . )*'*/';

RULE_SL_COMMENT : '//' ~(('\n'|'\r'))* ('\r'? '\n')?;

RULE_WS : (' '|'\t'|'\r'|'\n')+;

RULE_ANY_OTHER : .;
