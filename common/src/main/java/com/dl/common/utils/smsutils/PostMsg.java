package com.dl.common.utils.smsutils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URLEncoder;


/**
 * 短信包唯一的主要类，提供短信发送和接收、余额查询、帐号信息等功能
 * 如果通过代理上网，可以调用setProxy来设置代理服务器
 */
public class PostMsg {
//	public static void main(String[] args){
//		PostMsg msg = new PostMsg("wuqj", "66217");
		
		//设置网关服务器，该服务器用于发送信息，不同的情况下连接服务器有不同的发送速度，
		//所以强烈推荐代码里面有能够修改服务器的选项，比如做成配置文件的格式
		//String cmIP = "219.136.240.94";
		//int cmPort = 13013;
		//msg.getCmHost().setHost(cmIP, cmPort);
		
		//设置Web Service服务器，该服务器用于接收信息，不同的情况下连接服务器有不同的发送速度，
		//所以强烈推荐代码里面有能够修改服务器的选项，比如做成配置文件的格式
		//String wsIP = "210.21.112.195";
		//int wsPort = 8080;
		//msg.getWsHost().setHost(wsIP, wsPort);
		
//		//如果是通过代理服务器上网，可以用如下的方式告诉接口代理服务器的信息和用户名密码
//		msg.getProxy().setProxy(ProxyServer.PROXY_TYPE_HTTP, "192.168.0.171", 808,
//			"one", "one");

		//显示余额
		//int remainFee = msg.getRemainFee();
		//if (remainFee < 0){
		//	System.out.println("获取余额失败，原因为：" + esmsErrorMessage(remainFee) + "代号为：" + remainFee);	
		//	return;
		//}else{
		//	System.out.println("余额为：" + remainFee + "分");				
		//}
		
		//if (msg.post("13798079314", "短信测试", null) == 0){
		//	System.out.println("短信发送成功，发送后的余额是" + msg.getRemainFee());							
		//}else{
		//	System.out.println("短信发送失败");							
		//}
		
		//MessageData[] msgData = new MessageData[3];
		//msgData[0] = new MessageData("13798079314", "1");
		//msgData[1] = new MessageData("13798079314", "2");
		//msgData[2] = new MessageData("13798079314", "3");
		//System.out.println(msg.post(msgData, null));				
		//System.out.println("余额为：" + remainFee + "分");				
		
//		try {
//			ConfigInfo info = msg.getConfigInfo();
//			if (info == null){
//				System.out.println("获取帐号信息失败，用户名密码错误");				
//			}else{
//				System.out.println(info.toString());
//			}		
//			
//			System.out.println("回复信息为：");							
//			MOMsg[] msgList = msg.getMOMsg();
//			if (msgList == null)
//				System.out.println("获取回复信息失败：用户名密码出错");	
//			else{
//				if (msgList.length == 0)
//					System.out.println("当前无回复信息");	
//				else
//					for (int i = 0; i < msgList.length; i++) {
//						System.out.println(msgList[i]);			
//					}						
//			}
//		} catch (ConnectException e) {
//			System.out.println("操作失败，连接不上服务器");
//			e.printStackTrace();		
//		}
//	}

	private static String esmsErrorMessage(int errorCode)
	{
		switch (errorCode)
		{
			case PostMsg.E_INVALID_USER_PASSWORD:
				return "用户名密码错误";

			case PostMsg.E_FAILED_CONNECTED_TO_SERVER:
				return "连接不上服务器";				

			case PostMsg.E_FAILED_TO_POST_MESSAGE:
				return "短信发送失败，或者余额不足，或者密码错误";				

			case PostMsg.E_INVALID_MESSAGE_DATA:
				return "无效的短信号码或者内容";				

			default:
				return "未知错误";				
		}
	}
	
	
    /**
     * 用户名密码错误
     */
    public final static int E_INVALID_USER_PASSWORD = -6;
    
    /**
     * 连接不上服务器
     */
    public final static int E_FAILED_CONNECTED_TO_SERVER = -3;
    
    /**
     * 短信发送失败，余额不足或者用户名密码错误
     */
    public final static int E_FAILED_TO_POST_MESSAGE = -99;
    
    /**
     * 无效的短信数据，或者是信息内容超长、或者是号码的格式不对、或者是小灵通号码与手机号码混合发送
     */
    public final static int E_INVALID_MESSAGE_DATA = -5;
    
    /**
     * 用指定的用户名和密码创建一个新的PostMsg实例
     * @param userName	用户名
     * @param password	密码
     * @throws	NullPointerException
     * 			如果userName或password为null
     */
    public PostMsg(String userName, String password) {
        setUser(userName, password);
    }
        
    String userName = "";        //用户名
    String password = "";        //密码
    
    /**
     * 设置用户名和密码，可以切换到其他的短信帐号
     * @param userName	用户名
     * @param password	密码
     * @throws	NullPointerException
     * 			如果userName或password为null
     */
    public void setUser(String userName, String password) {
    	if (userName == null || password == null)
    		throw new NullPointerException();
    	
        this.userName = userName;
        this.password = password;
    }
    
    /**
     * 用StringBuilder作为传引用的方式，返回用户名和密码
     */
    public void getUser(StringBuilder userName, StringBuilder password) {
        userName.replace(0, userName.length(), this.userName);
        password.replace(0, password.length(), this.password);
    }
    
    /**
     * 网关服务器，用于发送信息
     */
    HostInfo cmHost = new HostInfo("211.147.239.62", 9050);
    
    /**
     * 网关服务器（用于发送信息），可以用HostInfo.setHost来设定其他的服务器，
     * 可用的网关服务器有：
     * <ul>
     * <li>电信服务器：211.147.224.154:13013(12013)</li>
     * <li>网通服务器：210.21.112.195:13013(12013)</li>
     * <li>备用服务器：211.147.224.174:13013(12013）</li>
     * <li>防火墙专用：218.204.253.77:80（不推荐使用）</li>
     * </ul>
     * 
     * @return 返回网关服务器信息（即IP地址和端口）。
     */
    public HostInfo getCmHost() {
        return cmHost;
    }
    
    /**
     * Web Service服务器，用于接收信息、获取余额、修改密码.
     */
//    HostInfo wsHost = new HostInfo("218.204.253.79", 8080);   
    HostInfo wsHost = new HostInfo("211.147.239.62", 9088);   
    
    /**
     * Web Service服务器（用于接收信息），可以用HostInfo.setHost来设定其他的网关服务器
     * 可用的服务器有：
     * <ul>
     * <li>电信服务器：211.147.224.154:8080</li>
     * <li>网通服务器：210.21.112.195:8080</li>
     * </ul>
     * @return 返回Web Service服务器信息（即IP地址和端口）。
     */
    public HostInfo getWsHost() {
        return wsHost;
    }
    
    int timeOut = 5000;     //连接服务器最长的时间
    
    ProxyServer proxy = new ProxyServer();
    
    /**
     * 返回代理服务器，通过ProxyServer.setProxy可以设置代理服务器的相关配置信息
     * @return	代理服务器
     */
    ProxyServer getProxy(){
    	return proxy;
    }
       
    private String makeSoapBody(String soapAction, String[] soapParams){
    	StringBuilder soapEnv = new StringBuilder();
    	for (int i = 0; i < soapParams.length; i++){
    		String ni = "n" + Integer.toString(i);
    		String beginTag = "<" + ni + " SOAP-ENV:encodingStyle=\"\">";
    		String endTag = "</" + ni + ">";
    		soapEnv.append(beginTag + soapParams[i] + endTag);
    	}
    	
        StringBuilder soapBody = new StringBuilder();
        soapBody.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
        soapBody.append("<SOAP-ENV:Envelope SOAP-ENV:encodingStyle=\"\" ");
        soapBody.append("xmlns:SOAPSDK1=\"http://www.w3.org/2001/XMLSchema\" ");
        soapBody.append("xmlns:SOAPSDK2=\"http://www.w3.org/2001/XMLSchema-instance\" ");
        soapBody.append("xmlns:SOAPSDK3=\"http://schemas.xmlsoap.org/soap/encoding/\" ");
        soapBody.append("xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">");
        soapBody.append("<SOAP-ENV:Body SOAP-ENV:encodingStyle=\"\">");
        soapBody.append("<" + soapAction + " SOAP-ENV:encodingStyle=\"\">");			//soap请求动作
        soapBody.append(soapEnv);       //Soap请求的参数
        soapBody.append("</" + soapAction + ">");				//soap请求动作
        soapBody.append("</SOAP-ENV:Body>");
        soapBody.append("</SOAP-ENV:Envelope>");
    	
        return soapBody.toString();
    }
    
    /**
     * 向Web Service发送SOAP请求，
     * 获取上行、余额、帐号信息和修改密码都是通过这个函数向Web Service服务器发送HTTP请求数据包
     */
    private String sendSoapRequest(String soapAction, String soapResultTag, String[] soapParams)
    throws ConnectException{
        /**
         * 构造SOAP请求体的数据包，也可算HTTP请求体的数据包
         */
    	String soapBody = makeSoapBody(soapAction, soapParams);
        
        String url = "HTTP://" + wsHost.toString() + "/services/EsmsService";
        /**
         * 构造HTTP请求字符串
         */
        String httpRequest = "POST " + url + "?wsdl HTTP/1.1\r\n";
        
        String actionHeader = "SOAPAction:\"" + url + "/" + soapAction + "\"\r\n";
        
        
        String httpResult;
        String result;
        String beginTag = "<" + soapResultTag;
        String endTag = "</" + soapResultTag + ">";
        
        int beginTagPos;
		try {
			httpResult = proxy.sendHttpRequest(wsHost,
					httpRequest, actionHeader, soapBody.toString(), endTag);
			beginTagPos = httpResult.indexOf(beginTag);
			if (beginTagPos == -1)
			    throw new ConnectException("Not Found soap result begin action tag");
		} catch (ConnectException e) {
			throw e;
		} catch (IOException e) {
			throw new ConnectException(e.getMessage());
		}
        
        int endTagPos = httpResult.indexOf(endTag);
		if (endTagPos == -1)
		    throw new ConnectException("Not Found soap result end action tag");
        return httpResult.substring(httpResult.indexOf(">", beginTagPos) + 1, endTagPos);
    }
    
    /**
     * 获取余额，以分为单位
     * @return 如果用户名、密码错误，则返回-6。如果网络问题，则返回-3。
     */
    public int getRemainFee(){
        String remainFee = "";
		try {
			String[] soapParams = new String[]{userName, password};
			remainFee = sendSoapRequest("getRemainFee", "getRemainFeeReturn", soapParams);
		} catch (ConnectException e) {
			return E_FAILED_CONNECTED_TO_SERVER;
		}
        if (remainFee.compareToIgnoreCase("ERROR") == 0)
            return E_INVALID_USER_PASSWORD;
        else
            return Integer.parseInt(remainFee);
    }
    
    /**
     * 获取回复信息（即MOMsg对象)的数组，最多10条信息
     * @return null 如果用户名、密码错误或者当前帐号没有回复信息，返回null。
     * @return 数组长度为0 当前帐号没有回复信息。
     * @exception ConnectException
     * 			如果连接不上服务器或者网络数据收发出错
     */
    public MOMsg[] getMOMsg()throws ConnectException {
		String[] soapParams = new String[]{userName, password};
        String soapResult = sendSoapRequest("getMOMessage", "getMOMessageReturn", soapParams);
        if (soapResult.compareToIgnoreCase("ERROR") == 0)
            return null;
        
        String momsgList = Helper.decodeHtmlEntity(soapResult);
        return MOMsg.parseMOMsgList(momsgList);
    }

    /**
     * 获取帐号信息，即返回ConfigInfo对象
     * @return	如果用户名、密码错误返回null。
     * @exception ConnectException
     * 			如果连接不上服务器或者网络数据收发出错
     */
    public ConfigInfo getConfigInfo()throws ConnectException {
		String[] soapParams = new String[]{userName, password};
        String soapResult = sendSoapRequest("getConfigInfo", "getConfigInfoReturn", soapParams);
        if (soapResult.equalsIgnoreCase("ERROR"))
            return null;
        String info = Helper.decodeHtmlEntity(soapResult);
        return ConfigInfo.parseConfigInfo(info);
    }
    
    /**
     * 修改帐号当前的密码
     * @param newPassword	新密码，不能为null或空字符串
     * @return	返回0则表示修改密码成功。其他值表示由于某种原因导致密码修改失败
     * @throws	NullPointerException
     * 			如果newPassword为null或者""
     */
    public int modifyPassword(String newPassword)
	{
		if (newPassword == null || newPassword.length() == 0)
		{
			throw new NullPointerException();
		}
		try
		{
			String soapResult = sendSoapRequest("modifyPassword", "multiRef", new String[]{userName, password, newPassword});
			if (soapResult == "0")
			{
				password = newPassword;		//密码修改成功，重新设置帐号密码
				return 0;
			}
			else if (soapResult == "-1")
			{
				return E_INVALID_USER_PASSWORD;
			}
			else
			{
				return E_FAILED_CONNECTED_TO_SERVER;
			}
		}
		catch (Exception e)
		{
			return E_FAILED_CONNECTED_TO_SERVER;
		}
	}
	
	private int finalPost(boolean sendSingle, String messageNumber, String messageText, String subId)
	{
		if (subId == null)
		{
			subId = "";
		}
		String httpResult = "";

		//Get Http://{0}/cgi-bin/sendsingle?username={1}&password={2}&to={3}&text=&subid={4}
		//&msgtype=1 HTTP/1.1\r\n
		StringBuilder url = new StringBuilder();
		url.append("Get Http://");
		url.append(cmHost.toString());
//		if (sendSingle) {
//			url.append("/cgi-bin/sendsingle?");
//		} else {
			url.append("/cgi-bin/sendsms?");
//		}
		url.append("username=" + userName);
		url.append("&password=" + password);
		url.append("&to=" + messageNumber);
		url.append("&text=" + messageText);
		url.append("&subid=" + subId);
		url.append("&msgtype=1  HTTP/1.1\r\n");
		
		//发送HTTP请求
		try {
			System.out.println(url);
			httpResult = proxy.sendHttpRequest(cmHost, url.toString(), "", "", "");		
			int result = Integer.parseInt(httpResult);
			if (result > 0)
			{
				return -result;
			}
			return result;
		} catch (Exception e) {
			return E_FAILED_CONNECTED_TO_SERVER;
		}
	}
	
	/**
     * 短信发送，单发。示例：
     * msg.post("13798079314", "你好，短信测试", "");
     *      
	 * @param messageNumber 小灵通或者手机号码，其他字符串均直接返回-5（无效的短信数据）
	 * @param messageText	要发送的信息的内容，注意字数不能越长（小灵通45个字，手机为70，然后再减去后缀的长度）
	 * @param subId	加在特服号后面的附加号码，代理商2的帐号才可使用这个参数，一般情况下可为""或null
	 * @return	0表示短信发送成功，其他值为发送失败，具体数值表示短信发送失败的原因
	 */
	public int post(String messageNumber, String messageText, String subId)
	{
//		if (!MessageData.checkMessageData(messageNumber, messageText))
//			return E_INVALID_MESSAGE_DATA;
        String urlMessageText;
		try {
			urlMessageText = URLEncoder.encode(messageText, "GBK");
		} catch (UnsupportedEncodingException e) {
			return E_INVALID_MESSAGE_DATA;
		}
		return finalPost(false, messageNumber, urlMessageText, subId);
	}
	
	/**
     * 短信发送，群发。该函数的运行流程如下所述：首先检查群发的号码是否正确一致，
     * 即要么所有的号码均为小灵通号码，要么所有的号码均为手机号码，如果包含混合的号码，
     * 则直接返回-2的错误号码，建议使用filterNumber来筛选号码。
     * 然后检查号码的长度是否超过规定的长度（小灵通为45个字，手机为70个字），
     * 如果超过则返回-3。最后才把信息发送出去。如果这时候信息发送不成功，则返回99的错误。
     * 以下是示例
     *      PostMsg msg = new PostMsg("one", "111");
     *      String[] phoneNumbers = {"13798079314", "13277886658"};
     *      msg.post(phoneNumbers, "你好，短信测试", "");
     *      
	 * @param messageNumberList 群发的号码，小灵通与手机号码不得混合发送
	 * @param messageText	要发送的信息的内容，注意字数不能越长（小灵通45个字，手机为70，然后再减去后缀的长度）
	 * @param subId	加在特服号后面的附加号码，代理商2的帐号才可使用这个参数，一般情况下可为""或null
	 * @return	0表示短信发送成功，其他值为发送失败，具体数值表示短信发送失败的原因
	 */
	public int post(String[] messageNumberList, String messageText, String subId)
	{
		int numberType = MessageData.checkNumberType(messageNumberList[0]);

		StringBuilder sNumber = new StringBuilder();
		sNumber.append(messageNumberList[0]);

		for (int i = 1; i < messageNumberList.length; i++)
		{
			int tempType = MessageData.checkNumberType(messageNumberList[i]);
			if (tempType != numberType)
			{
				numberType = -1;
				break;
			}
			sNumber.append("+" + messageNumberList[i]);
		}

		if (!MessageData.checkMessageText(numberType, messageText))
		{
			return E_INVALID_MESSAGE_DATA;
		}

        String urlMessageText;
		try {
			urlMessageText = URLEncoder.encode(messageText, "GBK");
		} catch (UnsupportedEncodingException e) {
			return E_INVALID_MESSAGE_DATA;
		}
		return finalPost(false, sNumber.toString(), urlMessageText, subId);
	}


	/**
	 * 多条发送，一次可以发送10条信息
	 * @param messageDataList	包含号码与内容的数据包数组
	 * @param subId	加在特服号后面的附加号码，代理商2的帐号才可使用这个参数，一般情况下可为""或null
	 * @return 0表示短信发送成功，其他值为发送失败，具体数值表示短信发送失败的原因
	 */
	public int post(MessageData[] messageDataList, String subId)
	{
		if (messageDataList.length > 10)
		{
			return E_INVALID_MESSAGE_DATA;
		}
		StringBuilder data = new StringBuilder();

		for (int i = 0; i < messageDataList.length; i++)
		{
			String singleData = messageDataList[i].toString();
			data.append(singleData);
		}
		return finalPost(true, data.toString(), "", subId);
	}    
}

class Helper
{
	public static int SubStringBetweenTag(String wholeString, int startPos, 
			String beginTag, String endTag, StringBuilder subString)
	{
		int beginPos = wholeString.indexOf(beginTag, startPos);
		if (beginPos == -1)
			return -1;		
		beginPos += beginTag.length();

		int endPos = wholeString.indexOf(endTag, beginPos);
		if (endPos == -1)
			return -1;

		subString.replace(0, subString.length(),
				wholeString.substring(beginPos, endPos));
		return endPos + endTag.length();
	}
	
    /**
     *   返回与HTML引用实体（即以&开头的数据)相对应的字符
     */
    private static char htmlEntity(String specialHtmlChar) {
        if (specialHtmlChar.indexOf("&#x") == 0)
            return (char)Integer.parseInt(specialHtmlChar.substring(3), 16);
        if (specialHtmlChar.indexOf("&lt") == 0)
            return '<';
        if (specialHtmlChar.indexOf("&gt") == 0)
            return '>';
        if (specialHtmlChar.indexOf("&quot") == 0)
            return '"';
        if (specialHtmlChar.indexOf("&amp") == 0)
            return '&';
        if (specialHtmlChar.indexOf("&apos") == 0)
            return '\'';
        return specialHtmlChar.charAt(0);
    }
    
    /**
     *   将一组HTML实体转换成与之相应的字符串
     *   即Convert all HTML entities to their applicable characters
     */
    public static String decodeHtmlEntity(String html) {
        char[] buffer = new char[html.length()];
        int i = 0, buflen = 0;
        while (i < html.length()){
            if (html.charAt(i) != '&'){
                buffer[buflen] = html.charAt(i);
                i++;
            } else{
                int endIndex = html.indexOf(';', i);
                buffer[buflen] = htmlEntity(html.substring(i, endIndex));
                i = endIndex + 1;
            }
            ++buflen;
        }
        return new String(buffer, 0, buflen);
    }    
}



