package ro.rekaszeru.stackoverflow.data;

import java.util.ArrayList;

public class Parent
{
	private String name;
	private boolean checked;
	private ArrayList<Child> children;
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public boolean isChecked()
	{
		return checked;
	}
	
	public void setChecked(boolean checked)
	{
		this.checked = checked;
	}
	
	public ArrayList<Child> getChildren()
	{
		return children;
	}
	
	public void setChildren(ArrayList<Child> children)
	{
		this.children = children;
	}
}
