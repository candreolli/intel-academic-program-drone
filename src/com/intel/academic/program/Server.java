package com.intel.academic.program;
import java.io.IOException;

/**
 * The server that creates a tunnel between the drone and the remote.
 * @author Cédric Andreolli - Intel Corporation
 *
 */
public class Server {
	/**
	 * The thread that manages the commands.
	 */
	private ConnectionThread commandThread = null;
	/**
	 * The port used to send the commands to the drone.
	 */
	private int portCommandDrone;
	/**
	 * The port used to receive the commands from the remote.
	 */
	private int portCommandRemote;
	/**
	 * The port used to receive the video from the drone.
	 */
	private int portVideoDrone;
	/**
	 * The port used to send the video to the remote.
	 */
	private int portVideoRemote;
	/**
	 * The thread that manages the video
	 */
	private ConnectionThread videoThread = null;

	/**
	 * Default constructor to create a server.
	 * @param portCommandDrone The port used to send the commands to the drone.
	 * @param portCommandRemote The port used to receive the commands from the remote.
	 * @param portVideoDrone The port used to receive the video from the drone.
	 * @param portVideoRemote The port used to send the video to the remote.
	 */
	public Server(int portCommandDrone, int portCommandRemote,
			int portVideoDrone, int portVideoRemote){
		this.portVideoDrone = portVideoDrone;
		this.portVideoRemote = portVideoRemote;
		this.portCommandDrone = portCommandDrone;
		this.portCommandRemote = portCommandRemote;

	}

	/**
	 * Connects the server. Once the server is connected, it's waiting for incoming connections from the
	 * remote and the drone.
	 * @throws IOException
	 */
	public void connect() throws IOException{
		System.out.println("Trying to connect");

		this.videoThread = new ConnectionThread(this.portVideoDrone, this.portVideoRemote, "video");
		this.commandThread = new ConnectionThread(this.portCommandDrone, this.portCommandRemote, "command");

		this.videoThread.start();
		this.commandThread.start();
		synchronized (this) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
