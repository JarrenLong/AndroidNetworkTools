package com.longtech.nettools.shell;

// TODO: Auto-generated Javadoc
/**
 * The Class Ping.
 */
public class NetStat extends ShellProcess {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.longtech.nettools.ConsoleRunnable#checkArgs(java.lang.String[])
	 */
	public boolean checkArgs(String[] args) {
		// No arguments required, just listing info for now
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.longtech.nettools.ConsoleRunnable#getSystemCommand()
	 */
	protected String getSystemCommand() {
		return "netstat";
	}
}
