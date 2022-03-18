package com.lomi.fdfs;

import java.io.IOException;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;


/**
 * FDSF工具，适用于单tracker，单storage的情况
 * @author ZHANGYUKUN
 *
 */
public class FDFSUtil {
	
	private static TrackerClient trackerClient;
	private static TrackerServer trackerServer;
	private static StorageServer storageServer;
	private static StorageClient1 storageClient;
	
	/**
	 * 初始化
	 * @param configPath
	 * @throws IOException
	 * @throws MyException
	 */
	public static void init(String configPath) throws IOException, MyException {
		
		 String conf_filename = new FDFSUtil().getClass().getClassLoader().getResource(configPath).getPath().replaceAll("%20"," ");
         ClientGlobal.init(conf_filename);
         
         trackerClient = new TrackerClient();
         
         //获取trackerServer是轮询的(也就是说如果是多节点的tracker,这行觉得轮询不好，可以继承修改获取负载均衡策略)
         trackerServer = trackerClient.getConnection();
         
         //如果不指定组名是通过cmd查询出来的存储位置，知道了组名就知道storage 的ip端口和store位置
         //只要是有多个storage,那么storageServer就应该每次获取
         storageServer = trackerClient.getStoreStorage(trackerServer);
         storageClient = new StorageClient1(trackerServer, storageServer);
	}


	/**
	 * 获取storageClient
	 * @return
	 */
	public static StorageClient1 getClient() {
		if( storageClient == null ) {
			throw new RuntimeException("需要初始化");
		}
		return storageClient;
	}
	
	/**
	 * 上传
	 * @param fileData
	 * @param metaList
	 * @return
	 * @throws IOException
	 * @throws MyException
	 */
	public static String uploadFile( byte[] fileData ,String extName ,NameValuePair[] metaList) throws IOException, MyException {
		  StorageClient1 client = FDFSUtil.getClient();
		  String fileId = client.upload_file1(fileData, extName , metaList);
		  return fileId;
	}
	
	/**
	 * 下载文件
	 * @param fileId
	 * @return
	 * @throws IOException
	 * @throws MyException
	 */
	public static byte[] downloadFile( String fileId) throws IOException, MyException {
		StorageClient1 client = FDFSUtil.getClient();
		byte[] fileData = client.download_file1(fileId);
		return fileData;
	}
	
	
	
	

}
