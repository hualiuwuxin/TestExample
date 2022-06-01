package com.lomi.fdfs;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.junit.Before;
import org.junit.Test;

import com.lomi.threeModule.fdfs.FDFSUtil;

public class FDFSUtilTest {
	
	@Before
	public void init() throws IOException, MyException {
		 FDFSUtil.init("fdfs/fdfs_client.conf");
		// FDFSUtil.init("fdfs/fdfs.properties");
	}
	
	
	/**
	 * 上传例子
	 * @throws IOException
	 * @throws MyException
	 */
	@Test
	public void updload() throws IOException, MyException {
         byte[] fileData =  FileUtils.readFileToByteArray(new File( "C:\\Users\\ZHANGYUKUN\\Desktop\\1.png" ));
         
         //设置元数据
         NameValuePair[] metaList = new NameValuePair[3];
         metaList[0] = new NameValuePair("fileName", "");
         metaList[1] = new NameValuePair("fileExtName", "png");
         metaList[2] = new NameValuePair("fileLength", String.valueOf( fileData.length ));

         
         String fileId = FDFSUtil.uploadFile(fileData, "png", metaList);
         System.out.println( fileId );
	}
	
	
	/**
	 * 下载例子
	 * @throws IOException
	 * @throws MyException
	 */
	@Test
	public void download() throws IOException, MyException {
		
		byte[] fileData = FDFSUtil.downloadFile("group1/M00/00/00/wKgByGI0rieAQZO5ABA07LqR8Rk.tar.gz");
		
		FileUtils.writeByteArrayToFile(new File("C:\\Users\\ZHANGYUKUN\\Desktop\\a\\"+ 1 +".tar.gz"), fileData);
		
		System.out.println("下载了文件");
	}
	
	/**
	 * 分片上传
	 * @throws IOException
	 * @throws MyException
	 */
	@Test
	public void uploadFileSice() throws IOException, MyException {
		byte[] fileData =  FileUtils.readFileToByteArray(new File( "C:\\Users\\ZHANGYUKUN\\Desktop\\info.txt" ));
		
		//设置元数据
        NameValuePair[] metaList = new NameValuePair[3];
        metaList[0] = new NameValuePair("fileName", "");
        metaList[1] = new NameValuePair("fileExtName", "");
        metaList[2] = new NameValuePair("fileLength", String.valueOf( fileData.length ));
         
		String fileId = FDFSUtil.uploadFileSice(fileData, "txt", metaList);
		
		System.out.println( fileId );
	}
	
	/**
	 * 分片下载
	 * @throws IOException
	 * @throws MyException
	 */
	@Test
	public void downloadSice() throws IOException, MyException {
		byte[] fileData = FDFSUtil.downloadSice("group1/M00/00/00/CgoKC2IzbhOAeKleAAG12DC05xo474.png");
		
		FileUtils.writeByteArrayToFile(new File("C:\\Users\\ZHANGYUKUN\\Desktop\\3.png"), fileData);
	}
	
	@Test
	public void delete() throws IOException, MyException {
		String fileId = "group1/M00/00/00/CgoKC2Izc3GAE_YmAAG12DC05xo325.png";
		
		byte[] fileData = null;
		
		fileData = FDFSUtil.downloadFile(fileId);
		System.out.println( fileData.length );
		FDFSUtil.delete(fileId);
		fileData = FDFSUtil.downloadFile(fileId);
		if( fileData == null  ) {
			System.out.println("删除文件完成");
		}else {
			System.out.println("文件还在");
		}
		
	}
	
	
	
	
	

}
