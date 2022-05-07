/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cloud.shopfly.b2c.framework.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * xmltool
 * @author kingapex
 *
 */
public   class XMLUtil {
	private XMLUtil(){}
	
	
	/**
	 * Reads the unique child of a node
	 * @param node
	 * @param tagName
	 * @return If there are more than one such nodetagNameChild node, returns the first child node, or if none existsNull
	 * @throws RuntimeException The incomingnodeThe node is notNode.ELEMENT_NODEtype
	 */
	public static Element getChildByTagName(Node node,String tagName){
		
//		//Checking node Correctness
		if(node.getNodeType()!= Node.ELEMENT_NODE){
			throw new RuntimeException(tagName +"The node format is incorrect,nonElementType.");
		}
		Element el = (Element)node;
		// Gets a child of this node named tagName and returns the first
		NodeList nodeList  =el.getElementsByTagName(tagName);
		int length  = nodeList.getLength();
		// If no child exists, Null is returned
		if( nodeList == null || length==0 ){
			return null;
		}
		return (Element)nodeList.item(0);
	}
	

	/**
	 * Reads the unique child of a node
	 * @param doc
	 * @param tagName
	 * @return If there are more than one such nodetagNameChild node, returns the first child node, or if none existsNull
	 * @throws RuntimeException The incomingnodeThe node is notNode.ELEMENT_NODEtype
	 */
	public static Element getChildByTagName(Document doc,String tagName){
		// Gets a child of this node named tagName and returns the first
		NodeList nodeList  =doc.getElementsByTagName(tagName);
		int length  = nodeList.getLength();
		// If no child exists, Null is returned
		if( nodeList == null || length==0 ){
			return null;
		}
		return (Element)nodeList.item(0);
	}
	
	
	/**
	 * Find the children of a node<br>
	 * Attribute name and attribute value of the match child
	 * @param node node
	 * @param attrName The name of the property to match
	 * @param attrValue The value of the property to match
	 * @return Returns the matched node
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
