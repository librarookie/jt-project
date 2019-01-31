package com.jt.manage.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jt.common.vo.PicUploadResult;
import com.jt.manage.service.FileService;

@Controller
public class FileController {
	
	@Autowired
	private FileService fileServce;
	
	//实现文件上传入门案例
	@RequestMapping("/file")
	public String file(MultipartFile fileImage) throws IllegalStateException, IOException{
		
		//1.判断文件夹是否存在
		File fileDir = new File("E:/jt-upload");
		if(!fileDir.exists()) {	//判断文件夹是否存在
			fileDir.mkdirs();//创建文件夹
		}
		//abc.jpg	获取图片名称
		String fileName = fileImage.getOriginalFilename();
		
		//实现文件上传
		fileImage.transferTo(new File("E:/jt-upload/"+fileName));
		
		//使用重定向技术
		return "redirect:/file.jsp";
		//转发
		//return "forword:/file.jsp";
	}
	//实现文件的上传
	@RequestMapping("/pic/upload")
	@ResponseBody
	public PicUploadResult uploadFile(MultipartFile uploadFile) {
		return fileServce.uploadFile(uploadFile);
	}

}
