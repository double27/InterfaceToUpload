package com.yuqing;

public class TestUpload {
	public static void main(String[] args) {
		// �ϴ��ļ� POST ͬ������  
        try {  
            UpLoadUtils.uploadFileImpl("D:/ѧ����ĩ�ɼ���.xls");
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
	}
}
