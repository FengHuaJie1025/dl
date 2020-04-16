package com.dl.common.utils.smsutils;

import java.net.URLEncoder;

/**
 * 用于打包短信号码与短信内容，用于作为PostMsg的发送函数Post的参数
 */
public class MessageData 
{
	/**
	 * 将号码与内容打成一个数据包，可用checkMessageData先判断号码与内容是否正确
	 * @param messageNumber	短信的号码，必须为正确的号码，或者为小灵通，或者为手机号码
	 * @param messageText	短信的内容，小灵通的信息不得超过45个字，手机的不超过70个字
     * @exception  IllegalArgumentException
     *             如果号码与内容无效
	 */	
	public MessageData(String messageNumber, String messageText)
	{
		if (!checkMessageData(messageNumber, messageText))
		{
			throw new IllegalArgumentException();
		}
		number = messageNumber;
		text = messageText;
	}
	String number;
	String text;
	
	/**
	 * 返回要发送的短信的字符串格式，包含了短信号码和内容，内容经过URL编码，用于PostMsg的短信发送
     * @return	该对象的字符串表示
	 */
	public String toString()
	{
		StringBuffer result = new StringBuffer();
		result.append("[mobile]" + number + "[mobile!]");
		
        String urlMessageText = "";
        try{
            urlMessageText = URLEncoder.encode(text, "GBK");
        }catch(Exception e){
            urlMessageText = "";
        }       
		result.append("[content]" + urlMessageText + "[content!]");
		
		return result.toString();
	}
	
	/**
	 * 检查短信号码的类型，-1为无效号码；0为小灵通号码；1为手机号码
	 * @param messageNumber	要检查的短信号码的字符串
	 * @return	-1为无效号码；0为小灵通号码；1为手机号码
	 */
	public static int checkNumberType(String messageNumber)
	{
		if (messageNumber == null)
			throw new NullPointerException();

		int MIN_PHONE_SIZE = 10;		
		int MAX_PHONE_SIZE = 12;
		int MOBILE_NUMBER_SIZE = 11;	//手机号码的长度为11位

		if (messageNumber.length() < MIN_PHONE_SIZE || 
				messageNumber.length() > MAX_PHONE_SIZE)
		{
			return -1;
		}

		//检查该字符串的内容是否都为数字
		for (int i = 0; i < messageNumber.length(); i++)
		{
			if ((messageNumber.charAt(i) < '0') || (messageNumber.charAt(i) > '9'))
			{
				return -1;
			}
		}

		if ((messageNumber.charAt(0) == '1') && 
				(messageNumber.length() == MOBILE_NUMBER_SIZE))
			return 1;
		else if ((messageNumber.charAt(0) == '0') && 
				(messageNumber.length() >= MIN_PHONE_SIZE) && 
				(messageNumber.length() <= MAX_PHONE_SIZE))
			return 0;
		else
			return -1;
	}
	
	/**
	 * 根据短信号码的类型，检查短信的内容是否超长
	 * @param numberType	短信的号码类型，由checkNumberType返回来的值
	 * @param messageText	短信的内容，要发送的信息
	 * @return	短信内容不超长，则返回真，否则返回假
	 */
	public static boolean checkMessageText(int numberType, String messageText)
	{
		int maxTextLength = 0; 
		switch (numberType)
		{
			case 1:
				maxTextLength = 70;		//手机信息的长度
				break;
			case 0:
				maxTextLength = 45;		//小灵通信息的长度
				break;
			case -1:
				return false;
		}
		if (messageText.length() > maxTextLength)
		{
			return false;
		}
		return true;
	}

	/**
	 * 检查短信号码与短信内容是否正确
	 * @param messageNumber	要发送的短信号码
	 * @param messageText	要发送的短信内容
	 * @return	如果号码与内容正确，则返回真，否则返回假
	 */
	public static boolean checkMessageData(String messageNumber, String messageText)
	{
		int numberType = checkNumberType(messageNumber);
		return checkMessageText(numberType, messageText);
	}
}
