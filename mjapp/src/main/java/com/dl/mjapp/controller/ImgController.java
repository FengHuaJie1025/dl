package com.dl.mjapp.controller;

import com.dl.common.pojo.DlResponse;
import com.dl.common.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = "img")
public class ImgController {

	@PostMapping(value = "uploadImg")
	public DlResponse uploadImg(MultipartHttpServletRequest request) throws Exception {
		String imgUrl = uploadImgUrl(request);
		return DlResponse.ok("图片上传成功",imgUrl);
	}
    
    public static String uploadImgUrl(MultipartHttpServletRequest request) throws Exception {
		// 创建一个通用的多部分解析器
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		String path = "";
		String myFileName = "";
		// 判断 request 是否有文件上传,即多部分请求
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// 取得上传文件
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null) {
					// 取得当前上传文件的文件名称
					String contentType = file.getContentType();
					String[] split = contentType.split("/");
					String hzm = "." + split[split.length - 1];
					String dateTimeString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss-"));
					String yearMonthString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss-"));

					Random random = new Random();
					myFileName = dateTimeString+ random.nextInt(1000) + hzm;
					// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
					if (myFileName.trim() != "") {
						// 重命名上传后的文件名
						String RealPath = request.getSession().getServletContext().getRealPath("");
						String domain = request.getServerName()+":"+request.getServerPort();
						String contextPath = request.getContextPath();
						//按照年月分文件夹
						File dir = new File(RealPath + "/img/"+yearMonthString+"/");
						
						// 如果目录不存在那么创建
						if (!dir.exists()) {
							dir.mkdirs();
						}
						// 定义上传路径
						path = dir + File.separator + myFileName;
						file.transferTo(new File(path));
						String finalPath = "http://" + domain + contextPath + "/img/" +yearMonthString+"/"+ myFileName;
						return finalPath;
					}
				}
			}
			
		}
		
		return "";
	}
    
    public static List<String> uploadImgUrlList(MultipartHttpServletRequest request) throws Exception {
    	// 创建一个通用的多部分解析器
    	CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
    			request.getSession().getServletContext());
    	String path = "";
    	String myFileName = "";

    	List<String> returnList = new ArrayList<String>();
    	// 判断 request 是否有文件上传,即多部分请求
    	if (multipartResolver.isMultipart(request)) {
    		// 转换成多部分request
    		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
    		// 取得request中的所有文件名
    		Iterator<String> iter = multiRequest.getFileNames();
    		while (iter.hasNext()) {
    			// 取得上传文件
    			MultipartFile file = multiRequest.getFile(iter.next());
    			if (file != null) {
    				// 取得当前上传文件的文件名称
    				String contentType = file.getContentType();
    				String[] split = contentType.split("/");
    				String hzm = "." + split[split.length - 1];
    				Random random = new Random();
    				myFileName = DateUtils.getCurrentDateStr("yyyyMMddHHmmss-" + random.nextInt(1000)) + hzm;
    				// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
    				if (myFileName.trim() != "") {
    					// 重命名上传后的文件名
    					String RealPath = request.getSession().getServletContext().getRealPath("");
    					String domain = request.getServerName();
    					String contextPath = request.getContextPath();
    					File dir = new File(RealPath + "/img/");

    					// 如果目录不存在那么创建
    					if (!dir.exists()) {
    						dir.mkdirs();
    					}
    					// 定义上传路径
    					path = dir + File.separator + myFileName;
    					file.transferTo(new File(path));
    					String finalPath = "http://" + domain + contextPath + "/img/" + myFileName;
    					returnList.add(finalPath);
    				}
    			}
    		}
    		return returnList;

    	}

    	return new ArrayList<>();
    }
}
