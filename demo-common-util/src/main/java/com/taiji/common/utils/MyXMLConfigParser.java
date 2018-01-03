package com.taiji.common.utils;

import java.io.InputStream;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MyXMLConfigParser
{
	public static Properties parse(String clspath)
	{
		InputStream in = MyXMLConfigParser.class.getResourceAsStream(clspath) ;
		if (in != null)
		{
			Properties conf = new Properties() ;
			try
			{
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance() ;
				DocumentBuilder builder = factory.newDocumentBuilder() ;
				Document doc = builder.parse(in) ;
				
				//内部自身配置
				NodeList nodes = doc.getElementsByTagName("item") ;
				int len = 0 ;
				if (nodes != null) len = nodes.getLength() ;
				for (int i=0 ;i<len ;i++)
				{
					Node node = nodes.item(i) ;
					NamedNodeMap attrs = node.getAttributes() ;
					if (attrs != null)
					{
						Node _key = attrs.getNamedItem("key") ;
						String key = _key.getNodeValue() ;
						Node _value = attrs.getNamedItem("value") ;
						String value = _value.getNodeValue() ;
						/*Node _comment = attrs.getNamedItem("comment") ;
						String comment = null ;
						if (_comment != null) comment = _comment.getNodeValue() ;
						
						System.out.println("加载配置参数 - "+key +"="+ value+(comment!=null?":"+comment:""));*/
						conf.put(key, value) ;
					}
				}
				
				//外部导入同类型配置
				nodes = doc.getElementsByTagName("import") ;
				len = 0 ;
				if (nodes != null) len = nodes.getLength() ;
				for (int i=0 ;i<len ;i++)
				{
					Node node = nodes.item(i) ;
					NamedNodeMap attrs = node.getAttributes() ;
					if (attrs != null)
					{
						//Node _key = attrs.getNamedItem("key") ;
						//String key = _key.getNodeValue() ;
						Node _value = attrs.getNamedItem("value") ;
						String value = _value.getNodeValue() ;
						/*Node _comment = attrs.getNamedItem("comment") ;
						String comment = null ;
						if (_comment != null) comment = _comment.getNodeValue() ;
						
						System.out.println("加载配置参数 - "+key +"="+ value+(comment!=null?":"+comment:""));*/
						
						System.out.println("...加载配置["+value+"]...");
						conf.putAll(parse(value)) ;
						System.out.println("...成功加载配置["+value+"]");
					}
				}
				
				//外部导入properties配置
				nodes = doc.getElementsByTagName("import-properties") ;
				len = 0 ;
				if (nodes != null) len = nodes.getLength() ;
				for (int i=0 ;i<len ;i++)
				{
					Node node = nodes.item(i) ;
					NamedNodeMap attrs = node.getAttributes() ;
					if (attrs != null)
					{
						//Node _key = attrs.getNamedItem("key") ;
						//String key = _key.getNodeValue() ;
						Node _value = attrs.getNamedItem("value") ;
						String value = _value.getNodeValue() ;
						/*Node _comment = attrs.getNamedItem("comment") ;
						String comment = null ;
						if (_comment != null) comment = _comment.getNodeValue() ;
						
						System.out.println("加载配置参数 - "+key +"="+ value+(comment!=null?":"+comment:""));*/
						
						System.out.println("...加载配置["+value+"]...");
						conf.putAll(CommonsConfig.loadClsFile(value)) ;
						System.out.println("...成功加载配置["+value+"]");
					}
				}
				
				return conf ;
			}
			catch (Exception e)
			{
				throw new RuntimeException("加载"+clspath+"配置失败" ,e) ;
			}
		}
		else
		{
			throw new RuntimeException("没有发现配置["+clspath+"]") ;
		}
	}
}
