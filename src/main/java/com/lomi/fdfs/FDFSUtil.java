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
 * FDSF���ߣ������ڵ�tracker����storage�����
 * @author ZHANGYUKUN
 *
 */
public class FDFSUtil {
	
	private static TrackerClient trackerClient;
	private static TrackerServer trackerServer;
	private static StorageServer storageServer;
	private static StorageClient1 storageClient;
	
	/**
	 * ��ʼ��
	 * @param configPath
	 * @throws IOException
	 * @throws MyException
	 */
	public static void init(String configPath) throws IOException, MyException {
		
		 String conf_filename = new FDFSUtil().getClass().getClassLoader().getResource(configPath).getPath().replaceAll("%20"," ");
         ClientGlobal.init(conf_filename);
         
         trackerClient = new TrackerClient();
         
         //��ȡtrackerServer����ѯ��(Ҳ����˵����Ƕ�ڵ��tracker,���о�����ѯ���ã����Լ̳��޸Ļ�ȡ���ؾ������)
         trackerServer = trackerClient.getConnection();
         
         //�����ָ��������ͨ��cmd��ѯ�����Ĵ洢λ�ã�֪����������֪��storage ��ip�˿ں�storeλ��
         //ֻҪ���ж��storage,��ôstorageServer��Ӧ��ÿ�λ�ȡ
         storageServer = trackerClient.getStoreStorage(trackerServer);
         storageClient = new StorageClient1(trackerServer, storageServer);
	}


	/**
	 * ��ȡstorageClient
	 * @return
	 */
	public static StorageClient1 getClient() {
		if( storageClient == null ) {
			throw new RuntimeException("��Ҫ��ʼ��");
		}
		return storageClient;
	}
	
	/**
	 * �ϴ�
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
	 * �����ļ�
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
