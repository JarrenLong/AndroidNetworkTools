package com.longtech.nettools.shell;

// TODO: Auto-generated Javadoc
/**
 * The Class Ping.
 */
public class Ping extends ShellProcess {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.longtech.nettools.ConsoleRunnable#checkArgs(java.lang.String[])
	 */
	public boolean checkArgs(String[] args) {
		// Make sure the first argument is a valid host name or IPv4 address
		return NetworkTools.isHostName(args[0])
				|| NetworkTools.isIPv4Adress(args[0]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.longtech.nettools.ConsoleRunnable#getSystemCommand()
	 */
	protected String getSystemCommand() {
		String[] args = getArgs();
		String cmd = "";

		if (checkArgs(args)) {
			if (System.getProperty("os.name").startsWith("Windows")) {
				// For Windows
				cmd = "ping -n 4 " + args[0];
			} else {
				// For Unix/OSX
				cmd = "ping -c 4 " + args[0];
			}
		}

		return cmd;
	}
}
