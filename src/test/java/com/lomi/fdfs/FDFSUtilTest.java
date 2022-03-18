package com.lomi.fdfs;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.junit.Before;
import org.junit.Test;

public class FDFSUtilTest {
	
	@Before
	public void init() throws IOException, MyException {
		 FDFSUtil.init("fdfs/fdfs_client.conf");
	}
	
	

	@Test
	public void updload() throws IOException, MyException {
         byte[] fileData =  FileUtils.readFileToByteArray(new File( "C:\\Users\\ZHANGYUKUN\\Desktop\\QQ截图20220317164211.png" ));
         
         //上传文件
         //设置元信息
         NameValuePair[] metaList = new NameValuePair[3];
         metaList[0] = new NameValuePair("fileName", "");
         metaList[1] = new NameValuePair("fileExtName", "");
         metaList[2] = new NameValuePair("fileLength", String.valueOf( fileData.length ));

         
         String fileId = FDFSUtil.uploadFile(fileData, "png", metaList);
         System.out.println( fileId );
	}
	
	@Test
	public void download() throws IOException, MyException {
		byte[] fileData = FDFSUtil.downloadFile("group1/M00/00/00/CgoKC2IzRIOAT29CAAG12DC05xo350.png");
		
		FileUtils.writeByteArrayToFile(new File("C:\\Users\\ZHANGYUKUN\\Desktop\\1.png"), fileData);
	}
	
	
	
	
	
	

}
