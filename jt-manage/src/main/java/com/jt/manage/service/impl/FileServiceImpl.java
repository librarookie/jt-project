package com.jt.manage.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jt.common.vo.PicUploadResult;
import com.jt.manage.service.FileService;
@Service
public class FileServiceImpl implements FileService {

	private String dirPath = "E:/jt-upload/";
	//定义虚拟路径
	private String urlPath = "http://image.jt.com/";
	/**
	 * 思路：
	 * 1.校验上传的文件是否为图片，jpg/png/gif
	 * 2.判断文件是否为恶意程序 ， exe/rpm
	 * 3.为了提高检索效率，一般采用分文件存储，层级最好不要超过 5 级
	 * 4.为了防止文件名称重复，采用动态的方式获取文件名称
	 */
	@Override
	public PicUploadResult uploadFile(MultipartFile uploadFile) {
		//1.效验文件的名称
		PicUploadResult result = new PicUploadResult();
		String fileName = uploadFile.getOriginalFilename();
		fileName = fileName.toLowerCase();	//将数据转换为小写
		/**
		 * 正则表达式（matches）：
		 * ^开始    $结束
		 * .  是除了/r /n 的任意字符
		 * +  一个或这多个
		 * *  没有或者多个
		 * () 组
		 * |  or或者的意思
		 * 		asd.jpg
		 */
		if(! fileName.matches("^.*\\.(jpg|png|gif)$")) {
			result.setError(1);	//表示不是图片
		}
		//2.判断是否为恶意程序
		try {
			BufferedImage buff = ImageIO.read(uploadFile.getInputStream());
			int width = buff.getWidth();
			int height = buff.getHeight();
			if(width==0 || height==0) {
				result.setError(1);		//表示不是图片
				return result;
			}
			/**
			 * 3.为了提高存储效率，采用分文件存储格式
			 * yyyy/MM/dd */
			String dateDir = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
			//4.保证文件不重名	UUID 2*32
			String uuid = UUID.randomUUID().toString().replace("-", "");
			int random = new Random().nextInt(1000);
			String fileType = fileName.substring(fileName.lastIndexOf("."));
			String uuidFileName = uuid + random + fileType;
			
			//定义文件夹的路径E:/jt-upload/2018/11/3
			String fileDirPath = dirPath + dateDir;
			File fileDir = new File(fileDirPath);
			
			if(! fileDir.exists()) {
				fileDir.mkdirs();
			}
			
			//实现文件的上传
			File realFilePath = new File(fileDirPath+"/"+uuidFileName);
			uploadFile.transferTo(realFilePath);
			//设定宽高
			result.setHeight(height+"");
			result.setWidth(width+"");
			
			/**
			 * 为了实现图片的页面访问，拼接虚拟路径
			 * http://image.jt.com/2018/11/11/asd.jpg
			 */
			String realurlPath = urlPath+dateDir + "/"+uuidFileName;
			result.setUrl(realurlPath);
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(1);
		}
		return result;
	}

}
