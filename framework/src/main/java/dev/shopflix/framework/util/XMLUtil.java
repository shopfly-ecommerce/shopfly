/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.framework.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * xml工具
 * @author kingapex
 *
 */
public   class XMLUtil {
	private XMLUtil(){}
	
	
	/**
	 * 读取某个节点的唯一子节点
	 * @param node
	 * @param tagName
	 * @return 如果此节点存在多个tagName子节点，返回第一个子节点，如果不存在返回Null
	 * @throws RuntimeException 传入的node节点非Node.ELEMENT_NODE类型
	 */
	public static Element getChildByTagName(Node node,String tagName){
		
//		//检查节点正确性
		if(node.getNodeType()!= Node.ELEMENT_NODE){
			throw new RuntimeException(tagName +"节点格式不正确,非Element类型。");
		}
		Element el = (Element)node;
		//获取此节点的名为tagName的子，并返回第一个
		NodeList nodeList  =el.getElementsByTagName(tagName);
		int length  = nodeList.getLength();
		//如果不存在子，则返回Null
		if( nodeList == null || length==0 ){
			return null;
		}
		return (Element)nodeList.item(0);
	}
	

	/**
	 * 读取某个节点的唯一子节点
	 * @param doc
	 * @param tagName
	 * @return 如果此节点存在多个tagName子节点，返回第一个子节点，如果不存在返回Null
	 * @throws RuntimeException 传入的node节点非Node.ELEMENT_NODE类型
	 */
	public static Element getChildByTagName(Document doc,String tagName){
		//获取此节点的名为tagName的子，并返回第一个
		NodeList nodeList  =doc.getElementsByTagName(tagName);
		int length  = nodeList.getLength();
		//如果不存在子，则返回Null
		if( nodeList == null || length==0 ){
			return null;
		}
		return (Element)nodeList.item(0);
	}
	
	
	/**
	 * 寻找某个节点的子节点<br>
	 * 匹配子的属性名称和属性值
	 * @param node 节点 
	 * @param attrName 要匹配的属性名称
	 * @param attrValue 要匹配的属性值
	 * @return 返回匹配的节点
	 */
	public static Element getChildByAttrName (Node node,String attrName,String attrValue){
		
		NodeList nodeList  = node.getChildNodes();
		for(int i=0,len=nodeList.getLength();i<len;i++){
			 Node n = nodeList.item(i);
			 if(n.getNodeType()== Node.ELEMENT_NODE){
				 Element el =(Element)n;
				 if (attrValue.equals( el.getAttribute(attrName) )){
					return el;
				 }
			 }
		}
		
		return null;
	}
	
	
}
