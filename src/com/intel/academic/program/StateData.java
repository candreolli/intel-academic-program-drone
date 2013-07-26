package com.intel.academic.program;

import java.io.IOException;
import java.io.OutputStream;

/**
 * State used to read the picture's data.
 * @author Cédric Andreolli - Intel Corporation
 *
 */
public class StateData implements StateLike {

	@Override
	public void recordStep(StateMachine stateMachine, byte[] buffer) {
		//The remaining size is used to ensure that the expected amount of data are really
		//read.
		int remainingSize = stateMachine.getSize() - buffer.length;
		stateMachine.setSize(remainingSize);
		OutputStream os = stateMachine.getOutputFileStream();
		try {
			os.write(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//If the remainingSize is 0 (or less), then a new picture can be retrieved.
		//So the next state is "StateSize" (the first state of the state machine).
		if(remainingSize <= 0){
			stateMachine.setCurrentState(new StateSize());
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
