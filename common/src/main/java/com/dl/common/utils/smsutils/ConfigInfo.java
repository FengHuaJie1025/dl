package com.dl.common.utils.smsutils;

import java.util.ArrayList;


/**
 * PostMsg获取帐号信息（getConfigInfo)后返回来的对象，存贮帐号的有关信息，如后缀、余额等
 */
public class ConfigInfo {    
    private HostInfo[] hostInfo;//IP地址信息组
    private String[] canumber;//特服号字符串组
    private int remainfee;//余额
    private int alertfee;//报警余额
    private String userbrief;//用户简称
    
    private String configinfo;

    static ConfigInfo parseConfigInfo(String config){
    	/**
    	 * 解析如下的字符串格式
    	 * [ipinfo]219.136.240.94,13013,219.136.240.94,8080[ipinfo!]
    	 * [canumber]电信:11870201001,联通:70308888111569,移动:626210168111569,网通:986888111569[canumber!]
    	 * [remainfee]8954[remainfee!][alertfee]0[alertfee!]
    	 * [userbrief][测试][userbrief!]
    	 */
    	StringBuilder subString = new StringBuilder();
    	int pos = Helper.SubStringBetweenTag(config, 0,
    			"[ipinfo]", "[ipinfo!]", subString);
    	if (pos == -1)    		
    		return null;
    	
    	ConfigInfo result = new ConfigInfo();
    	result.setHostInfo(subString.toString());
    	
    	pos = Helper.SubStringBetweenTag(config, pos,
    			"[canumber]", "[canumber!]", subString);
    	result.setCanaumer(subString.toString());
    	
    	pos = Helper.SubStringBetweenTag(config, pos,
    			"[remainfee]", "[remainfee!]", subString);
    	result.remainfee = Integer.parseInt(subString.toString());
    	
    	pos = Helper.SubStringBetweenTag(config, pos,
    			"[alertfee]", "[alertfee!]", subString);
    	result.alertfee = Integer.parseInt(subString.toString());
    	
    	pos = Helper.SubStringBetweenTag(config, pos,
    			"[userbrief]", "[userbrief!]", subString);
    	result.userbrief = subString.toString();
    	
    	result.configinfo = config;
    	
    	return result;    	
    }
    
    private void setHostInfo(String hostNames) {
        ArrayList ipInfoList = new ArrayList();
        int endIndex = 0;
        int beginIndex = 0;
        while (endIndex != -1){
            endIndex = hostNames.indexOf(',', beginIndex);
            if (endIndex == -1){
                break;
            }
            String ip = hostNames.substring(beginIndex, endIndex);    //ip地址
            
            endIndex = hostNames.indexOf(',', beginIndex);
            beginIndex = endIndex + 1;                            //端口的开始位置
            endIndex = hostNames.indexOf(',', beginIndex);       //端口的结束位置
            if (endIndex == -1){
                endIndex = hostNames.length();
            }
            String port = hostNames.substring(beginIndex, endIndex);
            ipInfoList.add(new HostInfo(ip, Integer.parseInt(port)));
            if (endIndex == hostNames.length()){
                break;
            }
            
            beginIndex = endIndex + 1;
        }
        
        if (ipInfoList.isEmpty())
            return;
        
        hostInfo = new HostInfo[ipInfoList.size()];
        for (int i = 0; i < hostInfo.length; i++){
            hostInfo[i] = (HostInfo)ipInfoList.get(i);
        }
        
    }
    
    private void setCanaumer(String number) {
        ArrayList numberList = new ArrayList();
        int endIndex = 0;
        int beginIndex = 0;
        while (endIndex != -1){
            endIndex = number.indexOf(',', beginIndex);
            if (endIndex == -1){
                endIndex = number.length();
            }
            numberList.add(number.substring(beginIndex, endIndex));
            if (endIndex == number.length()){
                break;
            }
            
            beginIndex = endIndex + 1;
        }
        
        if (numberList.isEmpty())
            return;
        
        canumber = new String[numberList.size()];
        for (int i = 0; i < canumber.length; i++){
            canumber[i] = (String)numberList.get(i);
        }
        
    }    
        
    /**
     * 获取余额
     * @return 余额值，以分为单位
     */
    public int getRemainFee() {
        return remainfee;
    }
    
    /**
     * IP地址信息组
     * @return 可用的服务器的IP地址和端口
     */
    public HostInfo[] getIpinfo() {
        return hostInfo;
    }
    /**
     * 获取帐号的特服号码，有移动的、联通的、电信的等。
     */
    public String[] getCanumber() {
        return canumber;
    }
    
    /**
     * 获取报警余额
     * @return 报警余额值
     */
    public int getAlertFee() {
        return alertfee;
    }
    
    /**
     * 获取用户简称，也即后缀
     * @return String 用户简称
     */
    public String getUserBrief() {
        return userbrief;
    }
    
    public String toString() {
    	return configinfo;
    }
    
}
