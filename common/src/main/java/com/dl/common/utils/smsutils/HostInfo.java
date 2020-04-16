package com.dl.common.utils.smsutils;

/**
 * 封装了IP地址和端口的类，可用于获取或设置PostMsg的网关服务器、WEB SERVERCE服务器，代理服务器
 */
public class HostInfo{
    private String hostIp;
    private int hostPort;

    /**
     * 用IP地址和端口创建一个新的主机实例
     * @param ip	IP地址，以点分十进制的格式，如 "211.147.224.154"
     * @param port	端口，必须于0-65535（即0xFFFF)之间
     * @throws	IllegalArgumentException
     * 			如果ip地址格式无效，或者port<0 || port > 65535(0xff)
     */
    public HostInfo(String ip, int port)
    {
    	setHost(ip, port);
    }

    /**
     * 设置主机信息，即IP地址和端口
     * @param ip	IP地址，以点分十进制的格式，如 "211.147.224.154"
     * @param port	端口，必须于0-65535（即0xFFFF)之间
     * @throws	IllegalArgumentException
     * 			如果ip地址格式无效，或者port<0 || port > 65535(0xff)
     */
    public void setHost(String ip, int port){   	
        if (!isValidIpAddress(ip))
            throw new IllegalArgumentException("Invalid IP Address");
        if (port < 0 || port > 0xFFFF) {
        	throw new IllegalArgumentException("port out of range:" + port);
        }
        this.hostIp = ip;
        this.hostPort = port;    	
    }
    
    /**
     * 检查IP地址格式（十分点进制的形式）是否正确
     * @param ipAddress	要判断的IP地址
     * @return	IP地址格式正确返回真,否则返回假
     */
    public static boolean isValidIpAddress(String ipAddress) {
        final int MAX_IP = 255;
        final int MIN_IP = 0;
        
        int dotBeginPos = 0;
        int dotEndPos = 0;
        for (int i = 0; i < 3; i++){
        	//分析被点分开的是否为数字且数字是否大于0小于255
            dotEndPos = ipAddress.indexOf('.', dotBeginPos);
            if (dotEndPos == -1)
                return false;
            try{
                int segment = Integer.parseInt(ipAddress.substring(dotBeginPos, dotEndPos));
                if ((segment < MIN_IP) || (segment > MAX_IP))
                    return false;
            }catch(NumberFormatException e){
                return false;
            }
            dotBeginPos = dotEndPos + 1;
        }
        try{
        	//非数字格式将抛出 NumberFormatException
            int segment = Integer.parseInt(ipAddress.substring(dotBeginPos));
            if ((segment < MIN_IP) || (segment > MAX_IP))
                return false;
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }
    
    /**
     *  返回IP地址，以点分十进制的形式
     */
    public String getIp()
    {
        return hostIp;
    }
    
    /**
     *  返回端口
     */
    public int getPort()
    {
        return hostPort;
    }
    
    /**
     * 构造此 HostInfo 的字符串表示形式。其格式为：IP地址 + ":" + 端口号
     *
     * @return  此对象的字符串表示形式。
     */
    public String toString() 
    {
        return hostIp + ":" + hostPort;
    }
    
    /**
     * 将此对象与指定对象比较。当且仅当参数不为 null，并且它表示与此对象相同的地址时，结果才为 true。
     * 如果 HostInfo 的两个实例的 hostName 和端口号都相等，则它们表示同一个地址。
     *
     * @param   obj   要与之比较的对象。 
     * @return  <code>true</code> if the objects are the same;
     *          <code>false</code> otherwise.
     */
    public final boolean equals(Object obj) {
	if (obj == null || !(obj instanceof HostInfo))
	    return false;
	HostInfo sockAddr = (HostInfo) obj;
	boolean sameIP = false;
	if (this.hostIp != null)
	    sameIP = this.hostIp.equals(sockAddr.hostIp);
	else
	    sameIP = (this.hostIp == null) && (sockAddr.hostIp == null);
	return sameIP && (this.hostPort == sockAddr.hostPort);
    }

    /**
     * 返回此HostInfo地址的哈希码。 
     */
    public final int hashCode() {
	if (hostIp != null)
	    return hostIp.hashCode() + hostPort;
	return hostPort;
    }    
}
