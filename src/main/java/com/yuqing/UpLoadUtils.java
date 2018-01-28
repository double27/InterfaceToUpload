package com.yuqing;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class UpLoadUtils {
	/**
	 * 上传文件
	 * 
	 * @param localFilePath 本地文件路径
	 */
	public static String uploadFileImpl(String localFilePath) {
		String respStr = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httppost = new HttpPost("http://localhost:8080/upload");
			FileBody binFileBody = new FileBody(new File(localFilePath));

			MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder
					.create();
			multipartEntityBuilder.setCharset(Charset.forName("utf-8"));  
			multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE); 
			// add the file params
			multipartEntityBuilder.addPart("file", binFileBody);

			HttpEntity reqEntity = multipartEntityBuilder.build();
			httppost.setEntity(reqEntity);

			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				System.out.println(response.getStatusLine());
				HttpEntity resEntity = response.getEntity();
				respStr = getRespString(resEntity);
				EntityUtils.consume(resEntity);
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("resp=" + respStr);
		return respStr;
	}

	/**
	 * 将返回结果转化为String
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	private static String getRespString(HttpEntity entity) throws Exception {
		if (entity == null) {
			return null;
		}
		InputStream is = entity.getContent();
		StringBuffer strBuf = new StringBuffer();
		byte[] buffer = new byte[4096];
		int r = 0;
		while ((r = is.read(buffer)) > 0) {
			strBuf.append(new String(buffer, 0, r, "UTF-8"));
		}
		return strBuf.toString();
	}
}

