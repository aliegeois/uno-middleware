package fr.univnantes.client.state;

public class StateException extends Exception {
	private static final long serialVersionUID = 5963988078799833080L;

	StateException() {
		super();
	}

	StateException(State currentState, Class<? extends State> expectedState, String methodName) {
		super(methodName + " must be called from " + expectedState.getName() + ", not from " + currentState.getClass().getName());
	}
}