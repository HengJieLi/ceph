package com.manager.utils;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.util.StringUtils;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;

public class Ceph001 {
	
	public static void test(){
		String accessKey = "HW5M5F7ZX8F3BK5MFJTU";
		String secretKey = "z4RW1L61QNFYcMCYa8wtwT97m5pPDhCtYTp5PjOM";
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		AmazonS3 conn = new AmazonS3Client(credentials);
		conn.setEndpoint("http://172.18.23.70");
		System.out.println("ok");
	
	//����ӵ����buckets
		List<Bucket> buckets = conn.listBuckets();
		for (Bucket bucket : buckets) {
		        System.out.println(bucket.getName() + "\t" +
		                StringUtils.fromDate(bucket.getCreationDate()));
		}
		
		
		//����һ��bucket
		Bucket bucket = conn.createBucket("my-new-bucket");
		
		//����һ��bucket������
		ObjectListing objects = conn.listObjects(bucket.getName());
		do {
		        for (S3ObjectSummary objectSummary : objects.getObjectSummaries()) {
		                System.out.println(objectSummary.getKey() + "\t" +
		                       objectSummary.getSize() + "\t" +
		                        StringUtils.fromDate(objectSummary.getLastModified()));
		        }
		        objects = conn.listNextBatchOfObjects(objects);
		} while (objects.isTruncated());
		
		//ɾ��һ��bucket
		//conn.deleteBucket(bucket.getName());
		
		//����һ������
		
		InputStream in;
		try {
			File f=new File("D:\\swift.jpg");
			in = new BufferedInputStream(new FileInputStream(f));
			conn.putObject(bucket.getName(), "swift.jpg", in, new ObjectMetadata());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		//�ı�һ�������ACL
		conn.setObjectAcl(bucket.getName(), "swift.jpg", CannedAccessControlList.PublicRead);
		
		
		//����һ������
		conn.getObject(
	        new GetObjectRequest(bucket.getName(), "swift.jpg"),
	        new File("d:\\swift.jpg")
		);
		
		//ɾ��һ������
		//conn.deleteObject(bucket.getName(), "goodbye.txt");
		
		//�γɶ�����������
		GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucket.getName(), "swift.jpg");
		System.out.println(conn.generatePresignedUrl(request));
	}
	

	
public static void main(String[] args) {
	Ceph001.test();
}

	
	
	
}