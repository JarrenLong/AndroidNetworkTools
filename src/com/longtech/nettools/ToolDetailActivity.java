package com.longtech.nettools;

import com.longtech.nettools.shell.NetworkTools;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

// TODO: Auto-generated Javadoc
/**
 * An activity representing a single Tool detail screen. This activity is only
 * used on handset devices. On tablet-size devices, item details are presented
 * side-by-side with a list of items in a {@link ToolListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link ToolDetailFragment}.
 */
public class ToolDetailActivity extends FragmentActivity {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tool_detail);

		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);

		if (savedInstanceState == null) {
			// Create the detail fragment and add it to the activity
			// using a fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(ToolDetailFragment.ARG_ITEM_ID, getIntent()
					.getStringExtra(ToolDetailFragment.ARG_ITEM_ID));
			ToolDetailFragment fragment = new ToolDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.tool_detail_container, fragment).commit();

			this.setTitle(NetworkTools.ITEM_MAP.get(arguments
					.getString(ToolDetailFragment.ARG_ITEM_ID)).content);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// Navigate up one activity
			NavUtils.navigateUpTo(this,
					new Intent(this, ToolListActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
