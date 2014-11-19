package com.longtech.nettools.shell;

// TODO: Auto-generated Javadoc
/**
 * The Class PortScanResult.
 */
public class PortScanResult {

	/** The port. */
	public int port;

	/** The is open. */
	public boolean isOpen;

	/**
	 * Instantiates a new port scan result.
	 * 
	 * @param port
	 *            the port
	 * @param open
	 *            the open
	 */
	public PortScanResult(int port, boolean open) {
		this.port = port;
		this.isOpen = open;
	}

}
