package com.longtech.nettools.shell;

// TODO: Auto-generated Javadoc
/**
 * The Class IPHostLookup.
 */
public class IPTools extends ShellProcess {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.longtech.nettools.ConsoleRunnable#checkArgs(java.lang.String[])
	 */
	public boolean checkArgs(String[] args) {
		// Make sure the first argument is a valid host name or IPv4 address
		// return NetworkTools.isHostName(args[0])
		// || NetworkTools.isIPv4Adress(args[0]);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.longtech.nettools.ConsoleRunnable#getSystemCommand()
	 */
	protected String getSystemCommand() {
		String arg = getArgs()[0];
		String cmd = "";

		if (NetworkTools.isHostName(arg) || NetworkTools.isIPv4Adress(arg)) {
			cmd = "nslookup " + arg;
		} else if (arg.contains("label")) {
			cmd = "ip addrlabel";
		} else if (arg.contains("addr")) {
			cmd = "ip addr";
		} else if (arg.contains("link")) {
			cmd = "ip link";
		} else if (arg.contains("route")) {
			cmd = "ip route";
		} else if (arg.contains("rule")) {
			cmd = "ip rule";
		} else if (arg.contains("neigh")) {
			cmd = "ip neigh";
		} else if (arg.contains("ntable")) {
			cmd = "ip ntable";
		} else if (arg.contains("tunnel")) {
			cmd = "ip tunnel";
		} else if (arg.contains("tuntap")) {
			cmd = "ip tuntap";
		} else if (arg.contains("maddr")) {
			cmd = "ip maddr";
		} else if (arg.contains("mroute")) {
			cmd = "ip mroute";
		} else if (arg.contains("mrule")) {
			cmd = "ip mrule";
		} else {
			cmd = "ip";
		}

		return cmd;
	}
}
