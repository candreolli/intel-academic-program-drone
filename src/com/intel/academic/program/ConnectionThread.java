package com.intel.academic.program;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Represents a tunnel connection between a remote and the drone server.
 * @author Cédric Andreolli - Intel Corporation
 *
 */
public class ConnectionThread extends Thread{
	/**
	 * An identifier that allows to identify this specific instance of the class.
	 */
	private String identifier;
	/**
	 * The communication port used by the drone
	 */
	private int portDrone;
	/**
	 * The communication port used by the remote
	 */
	private int portRemote;
	/**
	 * The server socket used for creating the drone socket connection.
	 */
	private ServerSocket serverSocketDrone = null;
	/**
	 * The server socket used for creating the remote socket connection.
	 */
	private ServerSocket serverSocketRemote = null;

	/**
	 * The default constructor.
	 * @param portDrone The communication port used by the drone.
	 * @param portRemote The communication port used by the remote.
	 * @param server A reference on the server.
	 * @param ident A string used to identify this specific instance of the class. You can use what ever you want.
	 */
	public ConnectionThread(int portDrone, int portRemote, String ident) {
		this.portDrone = portDrone;
		this.portRemote = portRemote;
		this.identifier = ident;
	}

	/**
	 * Create the tunnel between the remote and the drone in one direction. This method launch a new thread that will try to
	 * accept new connections on the server socket.
	 * @param readerC The channel that will be used to read data from the socket.
	 * @param writerC The channel that will be used to write data to the socket.
	 * @param serverSocket The server socket.
	 * @param port The port used for this connection.
	 */
	private void connectSocket(final Channel readerC, final Channel writerC,
			final ServerSocket serverSocket, int port) {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println("Connect Socket called... : "+ConnectionThread.this.identifier);
				Socket socket;
				Socket oldSocket = null;
				while(true){
					try {
						System.out.println("ServerSocket.accept() : "+ConnectionThread.this.identifier);
						/*
						 * When we accept a new connection, we must set to null the streams used by the channel
						 * to communicate. If a socket already exist, it's important to close it. This allows the channels
						 * to detect that something went wrong.
						 */
						socket = serverSocket.accept();
						System.out.println("Incoming connection from IP : "+socket.getRemoteSocketAddress());
						readerC.setReader(null);
						writerC.setWriter(null);
						if(oldSocket != null)
							oldSocket.close();
						System.out.println("Setting up the streams :  "+ConnectionThread.this.identifier);
						/**
						 * Setting up the reader or the writer will call notifyAll on the concerned channel.
						 */
						readerC.setReader(socket.getInputStream());
						writerC.setWriter(socket.getOutputStream());
						//We need to remember the reference of the current socket to close it
						//if a new connection occurs.
						oldSocket = socket;
						System.out.println("ConnectSocket is OK... :  "+ConnectionThread.this.identifier);
					} catch (Exception e) {
						System.err.println(e.getMessage()+" :  "+ConnectionThread.this.identifier);
					}
				}
			}
		});
		t.start();

	}

	@Override
	public void run() {
		//First connect the drone
		try {
			/*
			 * We need to create 2 channels to be able to communicate in 2 directions through the tunnel.
			 */
			final Channel dToR = new Channel();
			final Channel rToD = new Channel();

			this.serverSocketDrone = new ServerSocket(this.portDrone);
			this.serverSocketDrone.setReuseAddress(true);
			this.serverSocketRemote = new ServerSocket(this.portRemote);
			this.serverSocketRemote.setReuseAddress(true);;
			/**
			 * Then we can start the channel and set them up later.
			 */
			dToR.start();
			rToD.start();

			/**
			 * The following calls will try to accepts the connections on the server sockets.
			 */
			ConnectionThread.this.connectSocket(dToR, rToD, ConnectionThread.this.serverSocketDrone, ConnectionThread.this.portDrone);
			ConnectionThread.this.connectSocket(rToD, dToR, ConnectionThread.this.serverSocketRemote, ConnectionThread.this.portRemote);

		} catch (Exception e) {
			System.out.println("Exception "+e.getMessage()+" : "+this.identifier);
		}
	}

}
