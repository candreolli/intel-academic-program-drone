package com.intel.academic.program;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Represents a one direction communication through a tunnel. Basically, this class handle an input stream from a socket and
 * an output stream from an other socket. Then it connects the input stream to the output stream by copying data from one to
 * the other. As long as the streams are not well configured, instances of this class will be in the waiting state.
 * @author Cédric Andreolli - Intel Corporation
 *
 */
public class Channel extends Thread{
	/**
	 * The input stream used to read the data.
	 */
	private InputStream reader = null;
	/**
	 * Indicates wheter or not this channel must register a JPEG.
	 */
	private boolean registerJPEG = false;

	/**
	 * The output stream used to write the data.
	 */
	private OutputStream writer = null;

	/**
	 * Default constructor
	 * To use this objet, you must set the reader and the writer by calling :
	 * setReader(InputStream reader)
	 * setWriter(OutputStream writer)
	 * @param registerJPEG
	 */
	public Channel(boolean registerJPEG) {
		this.setRegisterJPEG(registerJPEG);
	}

	/**
	 * 
	 * @return The input stream.
	 */
	public synchronized InputStream getReader() {
		return this.reader;
	}

	/**
	 * 
	 * @return The output stream.
	 */
	public synchronized OutputStream getWriter() {
		return this.writer;
	}



	/**
	 * @return the registerJPEG
	 */
	public boolean isRegisterJPEG() {
		return this.registerJPEG;
	}

	@Override
	public void run() {
		super.run();
		StateMachine stateMachine = new StateMachine();
		while(true){
			/*
			 * If the reader or the writer are not correctly set, the tunnel can be
			 * achieved. In this case, the current thread is put in the waiting state.
			 */
			if(this.reader == null || this.writer == null){
				synchronized (this) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						System.err.println(e.getMessage());
					}
				}
			}else{
				/*
				 * Otherwise, we try to transfer data from the input to the output.
				 */
				try {
					/*
					 * To gain reactivity, we are not using buffered streams. We first get the amount of data available on the
					 * input stream. Then we read this amount of data and write it to the output stream.
					 */
					int numberToRead = this.reader.available();
					if(numberToRead>0){//data on the input stream ?
						byte[] buffer = new byte[numberToRead];
						while(numberToRead > 0){
							int totalRead = this.reader.read(buffer, buffer.length - numberToRead, numberToRead);
							numberToRead -= totalRead;
						}
						if(this.registerJPEG)
							stateMachine.recordStep(buffer);
						this.writer.write(buffer);
						this.writer.flush();

					}
				} catch (Exception e) {
					stateMachine = new StateMachine();
					System.out.println("Exception in the channel : "+e.getMessage());
				}
			}
		}
	}

	/**
	 * Set the input stream.
	 * @param reader The input stream.
	 */
	public synchronized void setReader(InputStream reader) {
		this.reader = reader;
		this.notifyAll();
	}

	/**
	 * @param registerJPEG the registerJPEG to set
	 */
	public void setRegisterJPEG(boolean registerJPEG) {
		this.registerJPEG = registerJPEG;
	}

	/**
	 * Set the output stream.
	 * @param reader The output stream.
	 */
	public synchronized void setWriter(OutputStream writer) {
		this.writer = writer;
		this.notifyAll();
	}

}
