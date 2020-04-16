package com.dl.common.utils.smsutils;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

/**
 * 设置包里面的代理服务器的相关参数，如代理类型（当代理类型为PROXY_TYPE_DIRECT时，表示直接上网）、代理服务器地址和端口
 * @author Administrator
 *
 */
public class ProxyServer {
    /**
     * 直接上网,不通过代理服务器
     */
    public final static int PROXY_TYPE_DIRECT = 0;
    
    /**
     * 用HTTP代理方式上网
     */
    public final static int PROXY_TYPE_HTTP = 1;
    
    /**
     * 用SOCKS4代理方式上网
     */
    public final static int PROXY_TYPE_SOCKS4 = 2;
        
    /**
     * 用SOCKS5代理方式上网
     */
    public final static int PROXY_TYPE_SOCKS5 = 3;
    
    private int type = PROXY_TYPE_DIRECT;                //代理服务器的类型
    private HostInfo host = new HostInfo("192.168.0.1", 0);  //代理服务器的地址和端口
    private String userName;
    private String password;
    private int timeOut = 10000;	//缺省设为10秒
    
    private String httpAuthorication = "";   //密码认证，(通过牺牲空间来换取时间)

	/**
	 * @return 代理服务器的主机信息（即IP地址和端口）
	 */
	public HostInfo getHost() {
		if (type == PROXY_TYPE_DIRECT) {
			return null;
		}
		return host;
	}

	/**
	 * @return 代理服务器中帐号的密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return 代理类型。如果PROXY_TYPE_DIRECT，即为直接上网，不通过代理服务器
	 */
	public int getType() {
		return type;
	}

	/**
	 * @return 代理服务器中帐号的用户
	 */
	public String getUserName() {
		return userName;
	}    
	
    /**
     * 设置代理服务器的属性。如果proxyType==PROXY_TYPE_DIRECT，则表示直接上网，无须通过代理服务器
     * @param proxyType	必须为PROXY_TYPE_DIRECT, PROXY_TYPE_HTTP, PROXY_TYPE_SOCKS4, PROXY_TYPE_SOCKS5
     * @param proxyIp	代理服务器的IP地址
     * @param proxyPort	代理服务器的端口
     * @param proxyUserName	代理服务器中帐号的用户
     * @param proxyPassword	代理服务器中帐号的密码
     * @throws	NullPointerException
     * 			如果proxyType不为PROXY_TYPE_DIRECT且proxyHost为null，
     * 			或者userName或password为null
     * @throws	IllegalArgumentException
     * 			如果proxyType不为PROXY_TYPE_DIRECT
     * 			且proxyIp地址格式无效，或者proxyPort<0 || proxyPort > 65535(0xff)
     */
    public void setProxy(int proxyType, String proxyIp, int proxyPort, 
            String proxyUserName, String proxyPassword) {
    	
    	//如果直接上网（不通过代理服务器），直接返回
    	if (proxyType == PROXY_TYPE_DIRECT) {
			type = proxyType;
			return;
		}
    	
        switch(proxyType){
            case PROXY_TYPE_HTTP:
            case PROXY_TYPE_SOCKS4:
            case PROXY_TYPE_SOCKS5:
            	break;
           default:
                throw new IllegalArgumentException("Invalid proxy type");
        }
    	if (proxyUserName == null || proxyPassword == null)
    		throw new NullPointerException();
    	    	
        
        type = proxyType;        
        host.setHost(proxyIp, proxyPort);
        
        userName = proxyUserName;
        password = proxyPassword;
        
        //设置HTTP过代理的基本验证方式
        if ((proxyType == PROXY_TYPE_HTTP) && (userName.length() != 0)) {
			StringBuilder authorication = new StringBuilder();
			authorication.append("Proxy-Authorization: Basic ");
			String userNamePassword = userName + ":" + password;
	        byte[] binaryData = userNamePassword.getBytes();
			authorication.append(encodeBase64(binaryData));
			authorication.append("\r\n");			

			httpAuthorication = authorication.toString();
		} else {
			httpAuthorication = "";
		}
    }
        
    /**
     * 设置代理服务器的属性。如果proxyType==PROXY_TYPE_DIRECT，则表示直接上网，无须通过代理服务器
     * @param proxyType	必须为PROXY_TYPE_DIRECT, PROXY_TYPE_HTTP, PROXY_TYPE_SOCKS4, PROXY_TYPE_SOCKS5
     * @param proxyIp	代理服务器的IP地址
     * @param proxyPort	代理服务器的端口
     * @throws	NullPointerException
     * 			如果proxyType不为PROXY_TYPE_DIRECT且proxyHost为null，
     * @throws	IllegalArgumentException
     * 			如果proxyIp地址格式无效，或者proxyPort<0 || proxyPort > 65535(0xff)
     */
    public void setProxy(int proxyType, String proxyIp, int proxyPort) {
    	setProxy(proxyType, proxyIp, proxyPort, "", "");
    }
 
    /**
     * 设置代理服务器的属性。如果proxyType==PROXY_TYPE_DIRECT，则表示直接上网，无须通过代理服务器
     * @param proxyType	必须为PROXY_TYPE_DIRECT, PROXY_TYPE_HTTP, PROXY_TYPE_SOCKS4, PROXY_TYPE_SOCKS5
     * @param proxyHost	代理服务器的IP地址和端口
     * @param proxyUserName	代理服务器中帐号的用户
     * @param proxyPassword	代理服务器中帐号的密码
     * @throws	NullPointerException
     * 			如果proxyType不为PROXY_TYPE_DIRECT且proxyHost为null，
     * 			或者userName或password为null
     */
    public void setProxy(int proxyType, HostInfo proxyHost,
            String proxyUserName, String proxyPassword) {
    	setProxy(proxyType, proxyHost.getIp(), proxyHost.getPort(),
    			proxyUserName, proxyPassword);
    }

    private static int copyBytes(byte[] sour, byte[] dest, int offset){
    	for (int i = 0; i < sour.length; i++) {
			dest[i + offset] = sour[i];
		}
    	return offset + sour.length;
    }
    
    /**
     * 连接SOCKS4代理服务器，其实就是将目标主机的IP地址和端口打包发送到代理服务器上
     */
    private  void connectToSocks4(Socket httpSocket, HostInfo dest)
    throws ConnectException{
    	//初始化连接SOCKS4代理服务器的字节
        byte[] connectionData = new byte[9];
//        for (int i = 0; i < connectionData.length; i++) {
//            connectionData[i] = 0;
//        }
        connectionData[0] = 4;			//表示SOCKS4的代理
        connectionData[1] = 1;
        //写入IP地址和端口
        byte[] portBytes = portToBytes(dest.getPort());     //封装端口
        connectionData[2] = portBytes[0];
        connectionData[3] = portBytes[1];        
        byte[] ipBytes = ipToBytes(dest.getIp());     //IP地址
        for (int i = 0; i < ipBytes.length; i++) {
            connectionData[i+4] = ipBytes[i];
        }
    	
        try {
			httpSocket.getOutputStream().write(connectionData);   
			byte[] socks4Receive = new byte[100];        //用来接收代理服务器信息的缓冲
			int len = httpSocket.getInputStream().read(socks4Receive);
			if(len < 8)
			    throw new ConnectException("Failed to connect to Socks4 proxy server");
		} catch (IOException e) {
			throw new ConnectException(e.toString());
		}
    }
    
    /**
     * 连接SOCKS4代理服务器，其实就是将目标主机的IP地址和端口打包发送到代理服务器上
     */
    private  void connectToSocks5(Socket httpSocket, HostInfo dest)
    throws ConnectException{
           	
        try {
        	//以下发送的字节，都是根据RFC规范来做的，如有疑问，请参阅相关文档
        	boolean useLogon = userName.length() > 0? true : false;	//是否使用用户名密码
        	
            byte[] connectionData = new byte[100];
			byte[] receiveData = new byte[100];        //用来接收代理服务器信息的缓冲
			int receiveLength = 0, sendLength = 0;
			
            connectionData[0] = 5;		//表示SOCKS5的代理
            
            if (useLogon){
            	connectionData[1] = (byte)2;
            	connectionData[2] = (byte)2;
    			httpSocket.getOutputStream().write(connectionData, 0, 4);    			
    			receiveLength = httpSocket.getInputStream().read(receiveData);
    			if((receiveLength != 2) || (receiveData[1] != 2))
    			    throw new ConnectException("Failed to connect to Socks5 proxy server");
    			
    			//发送用户名和密码
    			byte[] tempBytes = userName.getBytes();
    			connectionData[1] = (byte)tempBytes.length;
    			sendLength = copyBytes(tempBytes, connectionData, 2);	//拷入用户名
    			tempBytes = password.getBytes();
    			connectionData[sendLength] = (byte)tempBytes.length;	
    			sendLength = copyBytes(tempBytes, connectionData, sendLength + 1);	//拷入密码
    			httpSocket.getOutputStream().write(connectionData, 0, sendLength); 
    			receiveLength = httpSocket.getInputStream().read(receiveData);
    			if((receiveLength != 2) || (receiveData[1] != 0))
    			    throw new ConnectException("Failed to connect to Socks5 proxy server");   			
           }
            else{
            	connectionData[1] = (byte)1;
    			httpSocket.getOutputStream().write(connectionData, 0, 3);    			
    			receiveLength = httpSocket.getInputStream().read(receiveData);
    			if((receiveLength != 2) || (receiveData[1] != 2))
    			    throw new ConnectException("Failed to connect to Socks5 proxy server");
           }
                		
    		connectionData[1] = (byte)1;
    		connectionData[2] = (byte)0;
    		connectionData[3] = (byte)1;
    		copyBytes(ipToBytes(dest.getIp()), connectionData, 4);	//拷入IP地址
    		copyBytes(portToBytes(dest.getPort()), connectionData, 8); //拷入Port
			httpSocket.getOutputStream().write(connectionData, 0, 10);  			
			receiveLength = httpSocket.getInputStream().read(receiveData);
			if((receiveLength != 10) || (receiveData[1] != 0))
			    throw new ConnectException("Failed to connect to Socks5 proxy server");						
		} catch (IOException e) {
			throw new ConnectException(e.toString());
		}
    }
    
    String sendHttpRequest(HostInfo destHost, String httpRequest,
    		String httpAdditionalHeader, String httpBody, String endFlag)
    throws ConnectException, IOException{
    	
        String httpHeader = makeHttpHeader(destHost, httpBody.length(), httpAdditionalHeader);
        String httpData = httpRequest + httpHeader + httpBody;
        Socket httpSocket = null;
        try {
			if (type == PROXY_TYPE_DIRECT) {
				httpSocket = new Socket(destHost.getIp(), destHost.getPort());
			} else {
				httpSocket = new Socket(host.getIp(), host.getPort());
			}

			httpSocket.setSoTimeout(timeOut);

			if (type == PROXY_TYPE_SOCKS4) {
				connectToSocks4(httpSocket, destHost);
			}
			if (type == PROXY_TYPE_SOCKS5) {
				connectToSocks5(httpSocket, destHost);
			}

			httpSocket.getOutputStream().write(httpData.getBytes());

			byte[] buffer = new byte[8192];
	        String httpResult = "";
	        StringBuilder resultBuffer = new StringBuilder();
			int buflen = httpSocket.getInputStream().read(buffer);
			httpResult = new String(buffer, 0, buflen);
			while ((httpResult.indexOf(endFlag) == -1) && (buflen > 512)){
				resultBuffer.append(httpResult);
				buflen = httpSocket.getInputStream().read(buffer);
				httpResult = new String(buffer, 0, buflen);
			}
			resultBuffer.append(httpResult);
			
            if (!isHttpHeaderOk(resultBuffer.toString()))
                throw new ConnectException("Http result from Dest host Not 200 OK");
            
			String httpHeaderEndTag = "\r\n\r\n";	//HTTP HEADER 最后面为"\r\n\r\n"
			int endTagPos = resultBuffer.indexOf(httpHeaderEndTag);	
			if (endTagPos == -1)
				throw new ConnectException("Failed to receive Http data");
			
			endTagPos += httpHeaderEndTag.length();
			
//			System.out.println(httpResult);	
			
			return resultBuffer.substring(endTagPos);
		} finally {
			if (httpSocket != null)
				httpSocket.close();
		}
    }
    
    
    /**
	 * 构造HTTP的请求头数据
	 */
    private String makeHttpHeader(HostInfo serverHost, int contentLength, String additionalHeader) {
        StringBuilder httpHeader = new StringBuilder();
        httpHeader.append("Content-Length: " + (new Integer(contentLength)).toString() + "\r\n");
        httpHeader.append("Host: " + serverHost.toString() + "\r\n");
        httpHeader.append("User-Agent: PostMsg\r\n");
        httpHeader.append("Connection: Keep-Alive\r\n");
        httpHeader.append("Cache-Control: no-cache\r\n");
        httpHeader.append("Pragma: no-cache\r\n");
        httpHeader.append(httpAuthorication);          //增加用户名密码的请求头
        httpHeader.append(additionalHeader + "\r\n");
        return httpHeader.toString();
    }
    
    /**
     * 判断HTTP请求返回的数据包的请求头是否OK
     */
    private boolean isHttpHeaderOk(String httpHeader) {
        int OkPosition = httpHeader.indexOf("200 OK");
        if ((OkPosition == -1) || (OkPosition > httpHeader.indexOf("\r\n")))
            return false;
        else
            return true;
    }
        
    /**
     *  返回与端口相对应的网络字节(即高位字节在低字节)
     */
    private static byte[] portToBytes(int port){
        byte[] result = new byte[2];
        byte low = 0, high = 0;
        
        high = (byte)(port / 256);
        low = (byte)(port % 256);
//        String hexString = Integer.toHexString(port);
//        if (hexString.length() <= 2)
//        {
//            low = Byte.parseByte(hexString, 16);
//        }
//        else
//        {
//            high = (byte)Integer.parseInt(hexString.substring(0, 2), 16);
//            low = (byte)Integer.parseInt(hexString.substring(2), 16);            
//        }
        
        result[0] = (byte)high;
        result[1] = (byte)low;
        return result;
    }
    
    /**
     *  返回与IP地址相对应的网络字节(即高位字节在低字节)
     */
   private static byte[] ipToBytes(String ip){
        char dot = '.';
        int b0, b1, b2, b3;
        int p1, p2, p3;
        
        p1 = ip.indexOf(dot);
        b0 = Integer.parseInt(ip.substring(0, p1));
        
        p1++;
        p2 = ip.indexOf(dot, p1);
        b1 = Integer.parseInt(ip.substring(p1, p2));
        
        p2++;
        p3 = ip.indexOf(dot, p2);
        b2 = Integer.parseInt(ip.substring(p2, p3));
        
        p3++;
        b3 = Integer.parseInt(ip.substring(p3));
        
        byte[] result = new byte[4];
        result[0] = (byte)b0;
        result[1] = (byte)b1;
        result[2] = (byte)b2;
        result[3] = (byte)b3;
        
        return result;
    }
   
    /**
     *  与BASE64码相对应的字符
     */
    private static char  base64Char(byte base64) {
        base64 &= 0x3f;
        if (base64 < 26)
            return (char)('A' + base64);
        if (base64 < 52)
            return (char)('a' + base64 - 26);
        if (base64 < 62)
            return (char)('0' + base64 - 52);
        if (base64 == 62)
            return '+';
        else
            return '/';
    }
    
    /**
     *  将一字符串加密成BASE64码
     */
    private static String encodeBase64(byte[] binaryData) {
        final int SIGN = -128;
        
        int numberTriplets = (int)(binaryData.length / 3);
        int bufferLength = (numberTriplets * 3 == binaryData.length)?
            numberTriplets * 4 : (numberTriplets * 4 + 4);
        char[] buffer = new char[bufferLength];
        int dataIndex = 0, bufferIndex = 0;
        byte b1, b2, b3;
        byte val1, val2, val3;
        byte l, k;
        for (int i = 0; i < numberTriplets; i++){
            b1 = binaryData[dataIndex++];
            b2 = binaryData[dataIndex++];
            b3 = binaryData[dataIndex++];
            
            l  = (byte)(b2 & 0x0f);
            k  = (byte)(b1 & 0x03);
            
            val1 = ((b1 & SIGN)==0)?(byte)(b1>>2):(byte)((b1)>>2^0xc0);
            val2 = ((b2 & SIGN)==0)?(byte)(b2>>4):(byte)((b2)>>4^0xf0);
            val3 = ((b3 & SIGN)==0)?(byte)(b3>>6):(byte)((b3)>>6^0xfc);
            
            buffer[bufferIndex++] = base64Char(val1);
            buffer[bufferIndex++] = base64Char((byte)(val2 | ( k<<4 )));
            buffer[bufferIndex++] = base64Char((byte)((l <<2 ) | val3));
            buffer[bufferIndex++] = base64Char(b3);
        }
        if (dataIndex < binaryData.length){
            b1 = binaryData[dataIndex++];
            b2 = (dataIndex < binaryData.length)? binaryData[dataIndex] : 0;
            
            val1 = ((b1 & SIGN)==0)?(byte)(b1>>2):(byte)((b1)>>2^0xc0);
            val2 = ((b2 & SIGN)==0)?(byte)(b2>>4):(byte)((b2)>>4^0xf0);
            val3 = 0;
            
            l  = (byte)(b2 & 0x0f);
            k  = (byte)(b1 & 0x03);
            
            buffer[bufferIndex++] = base64Char(val1);
            buffer[bufferIndex++] = base64Char((byte)(val2 | ( k<<4 )));
            buffer[bufferIndex] = base64Char((byte)((l <<2 ) | val3));
            if (b2 == 0)
                buffer[bufferIndex] = '=';
            buffer[++bufferIndex] = '=';
        }
        return new String(buffer);
    }        
}
