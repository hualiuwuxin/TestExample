package com.lomi.fdfs;

import java.io.IOException;
import java.util.Arrays;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.FileInfo;
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
	
	
	/**
	 * 分段上传(简单的分2片顺序删除)
	 * 
	 * 正常分片上传应该按照大写切片，并且记录每一片上传的状态，对于失败的片段通过offset位置重新上传
	 * @param fileData
	 * @param metaList
	 * @return
	 * @throws IOException
	 * @throws MyException
	 */
	public static String uploadFileSice(byte[] fileData, String extName, NameValuePair[] metaList)
			throws IOException, MyException {
		StorageClient1 client = FDFSUtil.getClient();

		byte[] slice1 = Arrays.copyOfRange(fileData, 0, fileData.length / 2);
		byte[] slice2 = Arrays.copyOfRange(fileData, fileData.length / 2, fileData.length);
		
		String fileId = client.upload_appender_file1(slice1, "txt", metaList);
		System.out.println("上传第一片完成");
		int state = client.append_file1(fileId, slice2);
		if( state != 0 ) {
			throw new RuntimeException("上传失败");
		}
		System.out.println("上传第二片完成");
		
		return fileId;
	}
	
	/**
	 * 分片获取数据（简单的分两片下载）
	 * 和分片上传一样，正常应该按照大小分片，然后记录状态
	 * @param fileId
	 * @return
	 * @throws IOException
	 * @throws MyException
	 */
	public static byte[] downloadSice( String fileId) throws IOException, MyException {
		StorageClient1 client = FDFSUtil.getClient();
		FileInfo  fileInfo = client.get_file_info1(fileId);
		System.out.println("文件总大小：" + fileInfo.getFileSize() );
		
		byte[] slice1 = client.download_file1(fileId, 0, fileInfo.getFileSize()/2);
		byte[] slice2 = client.download_file1(fileId, fileInfo.getFileSize()/2, fileInfo.getFileSize()-fileInfo.getFileSize()/2 );
		
		byte[] data = new byte[ slice1.length + slice2.length   ];
		
		System.arraycopy(slice1, 0, data, 0, slice1.length);
		System.arraycopy(slice2, 0, data, slice1.length, slice2.length);
		
		return data;
	}
	
	
	/**
	 * 删除文件（备注fdfs文件删除了的瞬间还取得到）
	 * @param fileId
	 * @throws IOException
	 * @throws MyException
	 */
	public static void delete( String fileId) throws IOException, MyException {
		StorageClient1 client = FDFSUtil.getClient();
		int state = client.delete_file1(fileId);
		if( state != 0 ) {
			throw new RuntimeException("文件下载失败");
		}
	}
	
	

}
