package ac.psych.lithoponeADRXML;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class XML 
{	
	private Element getRoot(InputStream is)
	{
		try
		{
			Element retval = null;
			DocumentBuilderFactory doc_fac = DocumentBuilderFactory.newInstance();
			DocumentBuilder doc_builder = doc_fac.newDocumentBuilder();
			Document doc = doc_builder.parse(is);
			retval = doc.getDocumentElement();
			return retval;
		}
		catch(Exception e)
		{}
		
		return null;
		
	}
	
	public Test GetTest(InputStream resFIS)
	{
		Test retval = new Test();
		
		try
		{
			Element root = getRoot(resFIS);
			getHeader(retval, root);
			NodeList items = root.getChildNodes();
			int itemsCount = items.getLength();
			for(int i = 0; i < itemsCount; i++)
			{
				if(items.item(i).getNodeName().equals("item"))
				{
					Item item = getItem((Element)items.item(i));
					retval.Items.add(item);
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("exception in XML");
		}
		
		return retval;
	}
	
	public String GetDescription(InputStream is)
	{
		String retval = "";
		Element root = getRoot(is);
		if(root.getNodeName().equals("test"))
		{
			retval = root.getAttribute("description");
		}
		return retval;
	}
	
	private void getHeader(Test _test, Element root)
	{
		if(root.getNodeName().equals("test"))
		{
			_test.Name = root.getAttribute("name");
			_test.Description = root.getAttribute("description");
			_test.CasualSize = root.getAttribute("casual_size");
			_test.ItemCount = Integer.parseInt(root.getAttribute("item_count"));
			_test.GraphicCasual = 
					Boolean.parseBoolean(root.getAttribute("graphic_casual"));
			_test.GraphicSelection = 
					Boolean.parseBoolean(root.getAttribute("graphic_selection"));
			_test.TextCasual = 
					Boolean.parseBoolean(root.getAttribute("text_casual"));
			_test.TextSelection = 
					Boolean.parseBoolean(root.getAttribute("text_selection"));
			_test.SelectionSize = root.getAttribute("selection_size");
			
			_test.ID = Integer.parseInt(root.getAttribute("id"));
			//_test.ItemCount = Integer.parseInt(root.getAttribute("item_count"));
			
			_test.Version = Integer.parseInt(root.getAttribute("version"));
		}
	}
	
	//need to sort
	
	private Item getItem(Element itemElem)
	{
		Item retval = null;
		
		if(itemElem.getNodeName().equals("item"))
		{
			retval = new Item();
			retval.ID = Integer.parseInt(itemElem.getAttribute("id"));
			retval.ResID = Integer.parseInt(itemElem.getAttribute("res_id"));
			
			NodeList childList = itemElem.getChildNodes();
			int childListLen = itemElem.getChildNodes().getLength();
			
			for(int i = 0; i < childListLen; i++)
			{
				Node childNode = childList.item(i);
				
				if(childNode.getNodeName().equals("item_casual"))
				{
					retval.Casual = childNode.getTextContent();
				}
				else if(childNode.getNodeName().equals("selection"))//selection
				{
					Selection selection = getSelection((Element)childNode);
					retval.Selections.add(selection);
				}
			}
		}
		
		return retval;
	}
	
	//need to sort
	
	private Selection getSelection(Element selElem)
	{
		Selection retval = null;
		if(selElem.getNodeName().equals("selection"))
		{
			retval = new Selection();
			retval.ID = Integer.parseInt(selElem.getAttribute("id"));
			retval.ResID = Integer.parseInt(selElem.getAttribute("res_id"));
			retval.Value = Float.parseFloat(selElem.getAttribute("value"));
			
			NodeList nl = selElem.getChildNodes();
			int len = nl.getLength();
			for(int i = 0; i < len; i++)
			{
				if(nl.item(i).getNodeName().equals("casual"))
				{
					retval.Casual = nl.item(i).getTextContent();
					break;
				}
			}
		}
		
		return retval;
	}
}
