package com.intel.academic.program;

import java.io.UnsupportedEncodingException;
/**
 * State used to read the picture's width.
 * @author Cédric Andreolli - Intel Corporation
 *
 */
public class StateWidth implements StateLike {

	@Override
	public void recordStep(StateMachine stateMachine, byte[] buffer) {
		String str;
		try {
			str = new String(buffer, "UTF-8").replace("\n", "");
			int width = Integer.parseInt(str);
			stateMachine.setWidth(width);
			stateMachine.setCurrentState(new StateHeight());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
