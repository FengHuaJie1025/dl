package com.dl.common.utils.smsutils;

import java.util.ArrayList;


/**
 * PostMsg获取上行信息（getMOMsg)后返回来的对象，存贮了上行信息的ID，内容，发送的时间等
 */
public class MOMsg {
    private String id;//MOLOG表里面的记录ID
    private String srcterminalid;//发送者号码
    private String msgcontent;//内容
    private String destid;//上行端口
    private String receivetime;//接收时间
    
    /**
     * 分析通过SOAP请求得来的字符串，返回相应的MOMsg对象
     * @param momsg	上行信息格式的字符串
     * @return	如果格式正确，返回MOMsg，否则返回null
     */
    private static MOMsg parseMOMsg(String momsg){
    	/**
    	 * 解析如下的字符串格式
    	 * [id]509411[id!][destid]6201766321009720[destid!]
    	 * [srcterminalid]13831757543[srcterminalid!]
    	 * [msgcontent]祝你二零零六年一切顺.因为你就[msgcontent!]
    	 * [receivetime]20060426000111[receivetime!]
    	 */
    	StringBuilder subString = new StringBuilder();
    	int pos = Helper.SubStringBetweenTag(momsg, 0,
    			"[id]", "[id!]", subString);
    	if (pos == -1)    		
    		return null;
    	
    	MOMsg result = new MOMsg();
    	result.id = subString.toString();    	
    	pos = Helper.SubStringBetweenTag(momsg, pos,
    			"[destid]", "[destid!]", subString);    	
    	result.destid = subString.toString();    	
    	pos = Helper.SubStringBetweenTag(momsg, pos,
    			"[srcterminalid]", "[srcterminalid!]", subString);
    	result.srcterminalid = subString.toString();    	
    	pos = Helper.SubStringBetweenTag(momsg, pos,
    			"[msgcontent]", "[msgcontent!]", subString);
    	result.msgcontent = subString.toString();    	
    	pos = Helper.SubStringBetweenTag(momsg, pos,
    			"[receivetime]", "[receivetime!]", subString);
    	result.receivetime = subString.toString();
    	
    	return result;
    }
    
    static MOMsg[] parseMOMsgList(String momsgList){
    	//短信与短信之间用"||||"分开
        ArrayList msgList = new ArrayList();
        final String SEPARATOR = "||||";    //信息与信息之间的分隔符
        int index = 0;
        while (index != -1){
            int endIndex = momsgList.indexOf(SEPARATOR, index);
            if (endIndex == -1){
                endIndex = momsgList.length();
            }
            
            MOMsg momsg = MOMsg.parseMOMsg(momsgList.substring(index, endIndex));
            if (momsg != null)
            	msgList.add(momsg);
            
            if (endIndex == momsgList.length())
                break;
            
            index = endIndex + SEPARATOR.length();
        }
        
        MOMsg[] msgArray = new MOMsg[msgList.size()];
        for (int i = 0; i < msgArray.length; i++)
            msgArray[i] = (MOMsg)msgList.get(i);
        return msgArray;
   }
        
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("[id]" + id + "[id!]");
		result.append("[destid]" + destid +  "[destid!]");
		result.append("[srcterminalid]" + srcterminalid + "[srcterminalid!]");
		result.append("[msgcontent]" + msgcontent + "[msgcontent!]");
		result.append("[receivetime]" + receivetime + "[receivetime!]");
		return result.toString();
	}
    /**
     * 返回信息的ID
     */
    public String getId() {
        return id;
    }
    
    /**
     * 发送信息的手机号码
     */
    public String getSrcterminalid() {
        return srcterminalid;
    }
    
    /**
     * 信息的内容
     */
    public String getMsgcontent() {
        return msgcontent;
    }
    
    /**
     * 信息上行的帐号的号码
     */
    public String getDestid() {
        return destid;
    }
    
    /**
     * 服务器接收到信息的时间，一般等于信息发送的时间
     */    
    public String getReceivetime() {
        return receivetime;
    }

}