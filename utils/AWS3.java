package com.manager.utils;




import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

import com.amazonaws.services.s3.model.ObjectMetadata;


public class AWS3 {
	
	
	

	
		//创建对象
	public Map create(HttpServletRequest request) throws IOException, FileUploadException{
		// 建立连接
		
		
		Map map=new HashMap();
		String accessKey = "HW5M5F7ZX8F3BK5MFJTU";
		String secretKey = "z4RW1L61QNFYcMCYa8wtwT97m5pPDhCtYTp5PjOM";
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		AmazonS3 conn = new AmazonS3Client(credentials);
		conn.setEndpoint("http://172.18.23.70");
		Bucket bucket = conn.createBucket("my-new-bucket");
		String dirName = request.getParameter("dir");
		String fileName = "5.jpg";
		int fileSize = 22;
		String fileType = "";
		//数据流
		InputStream input;
		
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		List<FileItem> items = upload.parseRequest(request);
		Iterator<FileItem> itr = items.iterator();
		System.out.println(itr);
		
		while (itr.hasNext()) {
		
			
			FileItem item = itr.next();
			System.out.println(item);
			//文件名
			fileName = item.getName();
			System.out.println(item.getName());
			if(fileName!=null){
			//文件大小
			fileSize = (int) item.getSize();
			System.out.println(fileSize);
			//文件扩展名(类型)
			fileType = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();		
			System.out.println(fileType);
			
			input=item.getInputStream();
			conn.putObject("my-new-bucket", fileName, input, new ObjectMetadata());
			GeneratePresignedUrlRequest Request = new GeneratePresignedUrlRequest("my-new-bucket", fileName);
			System.out.println(conn.generatePresignedUrl(Request));
			String downLoadURL=conn.generatePresignedUrl(Request).toString();
			
			map.put("fileName", fileName);
			map.put("fileSize", fileSize);
			map.put("fileType", fileType);
			map.put("downLoadURL",downLoadURL);
			
			}
			
			}
		 
		//形成对象下载链接
		return map;
	}
	

	
}
