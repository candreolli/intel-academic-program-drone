package com.intel.academic.program;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
/**
 * State used to read the picture's size.
 * @author Cédric Andreolli - Intel Corporation
 *
 */
public class StateSize implements StateLike{
	private static int round = 0;
	@Override
	public void recordStep(StateMachine stateMachine, byte[] buffer) {
		try {
			OutputStream os = new FileOutputStream(new File("/etc/droneserver/"+"flight_"+StateMachine.getFlightNumber()+"_"+StateMachine.fileName+round+".jpeg"));
			stateMachine.setOutputFileStream(os);
			String str = new String(buffer, "UTF-8").replace("\n", "");
			int size = Integer.parseInt(str);
			stateMachine.setSize(size);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		stateMachine.setCurrentState(new StateWidth());
		round++;
	}

}
