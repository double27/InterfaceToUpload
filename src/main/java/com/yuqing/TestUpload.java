package com.yuqing;

public class TestUpload {
	public static void main(String[] args) {
		// 上传文件 POST 同步方法  
        try {  
            UpLoadUtils.uploadFileImpl("D:/学生期末成绩单.xls");
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
	}
}
