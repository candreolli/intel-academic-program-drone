package com.intel.academic.program;

import java.io.OutputStream;

public class StateMachine {
	/**
	 * The prefix of the image names.
	 */
	public final static String fileName = "drone_server_";
	/**
	 * Represents the current state.
	 */
	private StateLike currentState = null;
	/**
	 * Height of the image.
	 */
	private int height;
	/**
	 * The output stream used to write data.
	 */
	private OutputStream outputFileStream = null;
	/**
	 * The size of the file.
	 */
	private int size;
	/**
	 * The width of the image.
	 */
	private int width;

	/**
	 * Default constructor.
	 */
	public StateMachine(){
		this.setCurrentState(new StateSize());
	}

	/**
	 * Create a state machine starting from the state.
	 * @param initialState
	 */
	public StateMachine(StateLike initialState){
		this.setCurrentState(initialState);
	}

	/**
	 * @return the currentState
	 */
	public StateLike getCurrentState() {
		return this.currentState;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * @return the outputFileStream
	 */
	public OutputStream getOutputFileStream() {
		return this.outputFileStream;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return this.size;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return this.width;
	}

	public void recordStep(byte[] buffer){
		this.currentState.recordStep(this, buffer);
	}

	/**
	 * @param currentState the currentState to set
	 */
	public void setCurrentState(StateLike currentState) {
		this.currentState = currentState;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @param outputFileStream the outputFileStream to set
	 */
	public void setOutputFileStream(OutputStream outputFileStream) {
		this.outputFileStream = outputFileStream;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}
}
