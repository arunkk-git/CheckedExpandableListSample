package ro.rekaszeru.stackoverflow.activity;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ro.rekaszeru.stackoverflow.R;
import ro.rekaszeru.stackoverflow.data.Child;
import ro.rekaszeru.stackoverflow.data.Parent;

public class CheckableListActivity extends ExpandableListActivity
{
	private static final int CHECK_CHANGED = 1;
    private static final int BUTTON_CLICK =  2;
	private static final String STR_REGISTERED = " has registered!";
	private static final String STR_UNREGISTERED = " has unregistered!";
	
	private ArrayList<Parent> parents;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		getExpandableListView().setGroupIndicator(null);
		getExpandableListView().setDivider(null);
		getExpandableListView().setDividerHeight(0);
		registerForContextMenu(getExpandableListView());
		
		//TODO: serve with correct data instead of this!
		final ArrayList<Parent> dummyList = buildDummyData();
		loadHosts(dummyList);
	}
	
	/**
	 * here should come your data service implementation
	 * @return
	 */
	private ArrayList<Parent> buildDummyData()
	{
		final ArrayList<Parent> list = new ArrayList<Parent>();
		for (int i = 0; i < 20; i++)
		{
			final Parent parent = new Parent();
			parent.setName("Parent " + i);
			parent.setChecked((i % 2) == 0);
			parent.setChildren(new ArrayList<Child>());
			for (int j = 0; j < 25 - i; j++)
			{
				final Child child = new Child();
				child.setName("Child " + i + "/" + j);
				parent.getChildren().add(child);
			}
			list.add(parent);
		}
		return list;
	}
	
	private void loadHosts(final ArrayList<Parent> newParents)
	{
		if (newParents == null)
			return;
		parents = newParents;
		Log.d("StackOverFlow", "parents arrived: " + newParents.size());
		if (this.getExpandableListAdapter() == null)
		{
			final MyExpandableListAdapter mAdapter = new MyExpandableListAdapter();
			this.setListAdapter(mAdapter);
		}
		else
			((MyExpandableListAdapter)getExpandableListAdapter()).notifyDataSetChanged();
	}

	/**
	 * A simple adapter which maintains an ArrayList of photo resource Ids.
	 * Each photo is displayed as an image. This adapter supports clearing
	 * the list of photos and adding a new photo.
	 */
	private class MyExpandableListAdapter extends BaseExpandableListAdapter
	{
		private Handler checkChangeHander = new Handler()
		{
			public void handleMessage(Message msg) 
			{
				final Parent parent = (Parent)msg.obj;
				switch (msg.what)
				{
					case CHECK_CHANGED:
						((MyExpandableListAdapter)getExpandableListAdapter()).notifyDataSetChanged();
						break;
                    case BUTTON_CLICK:
                        Toast.makeText(getApplicationContext(),"Set Time Button Clicked ",Toast.LENGTH_LONG).show();
                        break;
					default:
						break;
				}
				final Boolean checked = parent.isChecked();
				Toast.makeText(getApplicationContext(), parent.getName() + " " + (checked ? STR_REGISTERED : STR_UNREGISTERED), 
						Toast.LENGTH_SHORT).show();
			};
		};
		
		/**
		 * @author rekaszeru
		 *
		 */
		private final class CheckUpdateListener implements OnCheckedChangeListener
		{
			private final Parent parent;
			/**
			 * @param child
			 */
			private CheckUpdateListener(Parent child)
			{
				this.parent = child;
			}
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				parent.setChecked(isChecked);
				final Message msg = new Message();
				msg.what = CHECK_CHANGED;
				msg.obj = parent;
				checkChangeHander.sendMessage(msg);
			}

        }



		private LayoutInflater inflater;

		public MyExpandableListAdapter()
		{
			inflater = LayoutInflater.from(CheckableListActivity.this);
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded, 
				View convertView, ViewGroup parentView)
		{
			final Parent parent = parents.get(groupPosition);
			convertView = inflater.inflate(R.layout.grouprow, parentView, false);
			((TextView) convertView.findViewById(R.id.parentname)).setText(parent.getName());
			CheckBox checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
            Button button = (Button) convertView.findViewById(R.id.timeset);

            checkbox.setChecked(parent.isChecked());
			if (parent.isChecked())
				convertView.setBackgroundResource(R.color.red);
			else
				convertView.setBackgroundResource(R.color.blue);
			checkbox.setOnCheckedChangeListener(new CheckUpdateListener(parent));
			button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(getApplicationContext(),"Button OnCLick done ",Toast.LENGTH_LONG).show();
					Button sel = (Button)v;
					sel.setTextColor(Color.GREEN);
					sel.setTextSize(25);
					sel.setText("12:34 AM");
					Intent intent = new Intent(getApplicationContext(),SetTimeDialog.class);
					startActivity(intent);
				}
			});
			return convertView;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition, boolean isLastChild, 
				View convertView, ViewGroup parentView)
		{
			final Parent parent = parents.get(groupPosition);
			final Child child = parent.getChildren().get(childPosition);
			convertView = inflater.inflate(R.layout.childrow, parentView, false);
			((TextView) convertView.findViewById(R.id.childname)).setText(child.getName());
			return convertView;
		}

		@Override
		public Object getChild(int groupPosition, int childPosition)
		{
			return parents.get(groupPosition).getChildren().get(childPosition);
		}

		@Override
		public long getChildId(int groupPosition, int childPosition)
		{
			return childPosition;
		}

		@Override
		public int getChildrenCount(int groupPosition)
		{
			return parents.get(groupPosition).getChildren().size();
		}

		@Override
		public Object getGroup(int groupPosition)
		{
			return parents.get(groupPosition);
		}

		@Override
		public int getGroupCount()
		{
			return parents.size();
		}

		@Override
		public long getGroupId(int groupPosition)
		{
			return groupPosition;
		}

		@Override
		public void notifyDataSetChanged()
		{
			super.notifyDataSetChanged();
		}

		@Override
		public boolean isEmpty()
		{
			return ((parents == null) || parents.isEmpty());
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition)
		{
			return true;
		}

		@Override
		public boolean hasStableIds()
		{
			return true;
		}

		@Override
		public boolean areAllItemsEnabled()
		{
			return true;
		}
	}
}
