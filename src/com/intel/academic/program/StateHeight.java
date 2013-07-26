package com.intel.academic.program;

import java.io.UnsupportedEncodingException;
/**
 * State used to read the picture's height.
 * @author Cédric Andreolli - Intel Corporation
 *
 */
public class StateHeight implements StateLike {

	@Override
	public void recordStep(StateMachine stateMachine, byte[] buffer) {
		String str;
		try {
			str = new String(buffer, "UTF-8").replace("\n", "");
			int height = Integer.parseInt(str);
			stateMachine.setHeight(height);
			stateMachine.setCurrentState(new StateData());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
