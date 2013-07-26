package com.intel.academic.program;

/**
 * Interface that represents a state of the state machine.
 * @author Cédric Andreolli - Intel Corporation
 *
 */
public interface StateLike {
	/**
	 * Records the data from the buffer if needed and change the state of the state machine.
	 * @param stateMachine A reference to the state machine.
	 * @param buffer The byte buffer from the socket stream.
	 */
	public void recordStep(StateMachine stateMachine, byte[] buffer);
}
