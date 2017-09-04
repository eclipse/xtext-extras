package org.eclipse.xtext.xbase.tests.typesystem;

import org.eclipse.xtext.xbase.tests.AbstractXbaseTestCase;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.common.types.JvmDeclaredType;
import org.eclipse.xtext.common.types.JvmIdentifiableElement;
import org.eclipse.xtext.common.types.JvmMember;
import org.eclipse.xtext.common.types.JvmOperation;
import org.eclipse.xtext.common.types.JvmType;
import org.eclipse.xtext.common.types.JvmTypeParameter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScopeProvider;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.validation.IssueSeverities;
import org.eclipse.xtext.xbase.XExpression;
import org.eclipse.xtext.xbase.XbaseFactory;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure2;
import org.eclipse.xtext.xbase.scoping.batch.AbstractFeatureScopeSession;
import org.eclipse.xtext.xbase.scoping.batch.ConstructorScopes;
import org.eclipse.xtext.xbase.scoping.batch.FeatureScopes;
import org.eclipse.xtext.xbase.scoping.batch.IFeatureScopeSession;
import org.eclipse.xtext.xbase.scoping.batch.TypeScopes;
import org.eclipse.xtext.xbase.testing.typesystem.PublicReentrantTypeResolver;
import org.eclipse.xtext.xbase.testing.typesystem.PublicResolvedTypes;
import org.eclipse.xtext.xbase.typesystem.IBatchTypeResolver;
import org.eclipse.xtext.xbase.typesystem.computation.ITypeComputationState;
import org.eclipse.xtext.xbase.typesystem.internal.AbstractTypeComputationState;
import org.eclipse.xtext.xbase.typesystem.internal.AbstractTypeExpectation;
import org.eclipse.xtext.xbase.typesystem.internal.ResolvedTypes;
import org.eclipse.xtext.xbase.typesystem.override.BottomResolvedOperation;
import org.eclipse.xtext.xbase.typesystem.override.IOverrideCheckResult;
import org.eclipse.xtext.xbase.typesystem.override.IResolvedFeatures;
import org.eclipse.xtext.xbase.typesystem.override.OverrideTester;
import org.eclipse.xtext.xbase.typesystem.references.LightweightTypeReference;
import org.eclipse.xtext.xbase.typesystem.references.UnboundTypeReference;
import org.eclipse.xtext.xbase.typesystem.util.CommonTypeComputationServices;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * Test if the OverrideTester detects properly the function types' overriding.
 * 
 * Part of the code is copied from <code>ClosureTypeComputerUnitTest.java</code>.
 * 
 * @author Stephane Galland - Initial contribution and API
 */
@SuppressWarnings("all")
public class OverrideTesterFunctionTypeTest extends AbstractXbaseTestCase {

	@SuppressWarnings("all")
	interface MyInterface {
		void myfct(Procedure2<Boolean, Class<? extends Number>> f);
	}
	@SuppressWarnings("all")
	class MyClass implements MyInterface {
		public void myfct(Procedure2<Boolean, Class<? extends Number>> f) {}
	}
	
	@Inject
	private CommonTypeComputationServices services;
	
	@Inject
	private IBatchTypeResolver typeResolver;
	
	@Inject
	private Provider<PublicReentrantTypeResolver> reentrantResolverProvider;
	
	@Inject
	private OverrideTester overrideTester;
	
	@Inject
	private ParseHelper<XExpression> parseHelper;

	private TestableState state;

	private ResourceSet contextResourceSet;
	
	private PublicReentrantTypeResolver reentrantResolver;
	
	@Test
	public void overridingJvmProcedure() throws Exception {
		JvmDeclaredType interfaceType = (JvmDeclaredType) getTypeForName(MyInterface.class, state);
		JvmOperation overriddenOperation = interfaceType.getDeclaredOperations().iterator().next();
		
		JvmDeclaredType classType = (JvmDeclaredType) getTypeForName(MyClass.class, state);
		JvmOperation overridingOperation = classType.getDeclaredOperations().iterator().next();
		
		IOverrideCheckResult result = this.overrideTester.isSubsignature(
			toResolveOperation(overridingOperation, classType),
			overriddenOperation,
			false);
		assertNotNull(result);
		assertFalse(result.hasProblems());
	}
	
	@Test
	public void overridingXbaseFunctionType() throws Exception {
		JvmDeclaredType interfaceType = (JvmDeclaredType) getTypeForName(MyInterface.class, state);
		JvmOperation overriddenOperation = interfaceType.getDeclaredOperations().iterator().next();
		
		JvmDeclaredType classType = (JvmDeclaredType) getTypeForName(MyClass.class, state);
		JvmOperation overridingOperation = classType.getDeclaredOperations().iterator().next();
		
		IOverrideCheckResult result = this.overrideTester.isSubsignature(
			toResolveOperation(overridingOperation, classType),
			overriddenOperation,
			false);
		assertNotNull(result);
		assertFalse(result.hasProblems());
	}

	protected XExpression expression(String string) throws Exception {
		XExpression result = super.expression(string);
		contextResourceSet = result.eResource().getResourceSet();
		return result;
	}
	
	protected XExpression expression(String string, ResourceSet resourceSet) throws Exception {
		return parseHelper.parse(string, resourceSet);
	}
	
	protected TestableState createTypeComputationState() {
		return new TestableState(new PublicResolvedTypes(reentrantResolver) {
		}, new NullFeatureScopeSession());
	}

	private BottomResolvedOperation toResolveOperation(JvmOperation operation, JvmType container) throws Exception {
		LightweightTypeReference reference = state.getReferenceOwner().newParameterizedTypeReference(container);
		return new BottomResolvedOperation(operation, reference, this.overrideTester);
	}
	
	protected JvmType getTypeForName(Class<?> clazz, ITypeComputationState state) {
		ResourceSet resourceSet = state.getReferenceOwner().getContextResourceSet();
		return services.getTypeReferences().findDeclaredType(clazz, resourceSet);
	}

	@Before
	public void setUp() throws Exception {
		XExpression expression = expression("null");
		assertNotNull(contextResourceSet);
		reentrantResolver = reentrantResolverProvider.get();
		reentrantResolver.initializeFrom(EcoreUtil.getRootContainer(expression));
		state = createTypeComputationState();
	}
	
	@After
	public void tearDown() {
		contextResourceSet = null;
		reentrantResolver = null;
		this.state = null;
	}
	
	class TestableState extends AbstractTypeComputationState {

		protected TestableState(ResolvedTypes resolvedTypes, IFeatureScopeSession featureScopeSession) {
			super(resolvedTypes, featureScopeSession);
		}

		@Override
		protected LightweightTypeReference acceptType(ResolvedTypes types, AbstractTypeExpectation expectation,
				LightweightTypeReference type, boolean returnType, int conformanceHint) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		protected LightweightTypeReference acceptType(XExpression alreadyHandled, ResolvedTypes types,
				AbstractTypeExpectation expectation, LightweightTypeReference type, boolean returnType,
				int conformanceHint) {
			throw new UnsupportedOperationException();
		}

		@Override
		protected List<AbstractTypeExpectation> getExpectations(AbstractTypeComputationState actualState) {
			throw new UnsupportedOperationException();
		}

		@Override
		protected List<AbstractTypeExpectation> getReturnExpectations(AbstractTypeComputationState actualState, boolean asActualExpectation) {
			throw new UnsupportedOperationException();
		}
		
		protected UnboundTypeReference createUnboundTypeReference(JvmTypeParameter type) {
			PublicResolvedTypes resolvedTypes = getResolvedTypes();
			return resolvedTypes.createUnboundTypeReference(XbaseFactory.eINSTANCE.createXNullLiteral(), type);
		}
		
		@Override
		public PublicResolvedTypes getResolvedTypes() {
			return (PublicResolvedTypes) super.getResolvedTypes();
		}

		@Override
		public List<LightweightTypeReference> getExpectedExceptions() {
			throw new UnsupportedOperationException();
		}

		@Override
		protected IssueSeverities getSeverities() {
			throw new UnsupportedOperationException();
		}
		
	}

	class NullFeatureScopeSession extends AbstractFeatureScopeSession {

		@Override
		public boolean isVisible(JvmMember member) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean isVisible(JvmMember member, /* @Nullable */ LightweightTypeReference receiverType, /* @Nullable */ JvmIdentifiableElement receiverFeature) {
			throw new UnsupportedOperationException();
		}

		/* @Nullable */
		@Override
		public IEObjectDescription getLocalElement(QualifiedName name) {
			throw new UnsupportedOperationException();
		}
		
		@Override
		protected FeatureScopes getFeatureScopes() {
			throw new UnsupportedOperationException();
		}
		
		@Override
		protected IResolvedFeatures.Provider getResolvedFeaturesProvider() {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean isInstanceContext() {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public boolean isConstructorContext() {
			throw new UnsupportedOperationException();
		}
		
		@Override
		protected ConstructorScopes getConstructorScopes() {
			throw new UnsupportedOperationException();
		}
		
		@Override
		protected TypeScopes getTypeScopes() {
			throw new UnsupportedOperationException();
		}

		@Override
		protected IScopeProvider getDefaultScopeProvider() {
			throw new UnsupportedOperationException();
		}

		@Override
		protected int getId() {
			throw new UnsupportedOperationException();
		}
		
	}

}
