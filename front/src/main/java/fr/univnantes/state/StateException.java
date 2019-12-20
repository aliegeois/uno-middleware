package fr.univnantes.state;

import java.util.stream.Stream;

public class StateException extends Exception {
	private static final long serialVersionUID = 5963988078799833080L;

	StateException() {
		super();
	}

	/*StateException(State currentState, Class<? extends State> expectedState, String methodName) {
		super(methodName + " must be called from " + expectedState.getName() + ", not from " + currentState.getClass().getName());
	}*/

	@SafeVarargs
	StateException(State currentState, String methodName, Class<? extends State>... expectedStates) {
		/*String errorMessage = methodName + " must be called from ";
		for(int i = 0; i < expectedStates.length; i++) {
			errorMessage += expectedStates[i].getName();
			if(i != expectedStates.length - 1)
				errorMessage += " or ";
		}
		errorMessage += ", not from " + currentState.getClass().getName();*/
		super(methodName + " must be called from " + Stream.of(expectedStates).map(state -> state.getName()).reduce((acc, stateName) -> acc + stateName).get() + ", not from " + currentState.getClass().getName());
	}
}