package com.jt.common.po;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Table(name="tb_item")
@JsonIgnoreProperties(ignoreUnknown=true)	// 表示JSON转化时忽略未知的属性
public class Item extends BasePojo {
	@Id			//定义主键
	@GeneratedValue(strategy=GenerationType.IDENTITY)	//主键自增
	private Long id;			//商品id
	private String title;		//商品标题
	
	//@Column(name="sell_point")	//指定属性和字段一一映射（.xml开启了驼峰映射这里可以省略）
	private String sellPoint;	//商品买点信息
	private Long price;			//商品价格Long > double
	private Integer num;		//商品数量
	private String barcode;		//条形码
	private String image;		//商品图片信息
	private Long cid;			//商品的分类id
	private Integer status;	//1正常，2下架，3删除
	
	// 为了满足页面调用需求，添加 Get方法
	public String[] getImages() {
		return image.split(",");
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSellPoint() {
		return sellPoint;
	}
	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

}
