package com.jt.manage.vo;

public class EasyUITree {

	/**
	 * 
	 */
	private Long id;		//商品分类id号
	private String text;	//商品分类名称
	private String state;	//closed
	
	public EasyUITree() {
		//无参构造，如果不加无参，则反射/创建对象时会报错
	}
	public EasyUITree(Long id, String text, String state) {
		super();
		this.id = id;
		this.text = text;
		this.state = state;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

}
