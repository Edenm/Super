package view;

import ViewLogic.slidingmenu.R;
import model.Helper;
import model.ModelLogic;
import model.SuperMarket;
import model.dropbox.manager.NetworkListener;
import model.dropbox.manager.NetworkManager;
import view.adapter.NavDrawerListAdapter;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

/**
 * This class is manage all tabs and fragment in the app
 */
public class MainActivity extends TabActivity {
	/** The DrawerLayout */
	private DrawerLayout mDrawerLayout;
	/** The list view of drawer */
	private ListView mDrawerList;
	/** The action bar drawer toggle */
	private ActionBarDrawerToggle mDrawerToggle;

	public final static int MAIN = 1001;

	/** nav drawer title */
	private CharSequence mDrawerTitle;

	/** used to store app title */
	private CharSequence mTitle;

	/** slide menu items */
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;

	/** The tab host */
	TabHost mTabHost;

	Context ctx;

	/**
	 * On create
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)  {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setTabs();

		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Update Profile
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
		// About Us
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(0, -1)));
		// Log-of
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(0, -1)));


		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			//displayView(0);
		}

		onDownloadFinished();
	}

	/**
	 * Create and sets all tabs in tabhost
	 */
	private void setTabs()
	{

		mTabHost = getTabHost();

		Intent intent;

		TabHost.TabSpec ts3 = mTabHost.newTabSpec("3");
		intent=new Intent(this, ComparisonTabActivity.class);
		ts3.setContent(intent);
		CharSequence tab3Indicator = getString(R.string.tab3);
		ts3.setIndicator(tab3Indicator);
		mTabHost.addTab(ts3);

		TabHost.TabSpec ts2 = mTabHost.newTabSpec("2");
		intent=new Intent(this, SuperTabActivity.class);
		ts2.setContent(intent);
		CharSequence tab2Indicator = getString(R.string.tab2);
		ts2.setIndicator(tab2Indicator);
		mTabHost.addTab(ts2);

		TabHost.TabSpec ts1 = mTabHost.newTabSpec("1");
		intent=new Intent(this, SearchTabActivity.class);
		ts1.setContent(intent);
		CharSequence tab1Indicator = getString(R.string.tab1);
		ts1.setIndicator(tab1Indicator);
		mTabHost.addTab(ts1);

		mTabHost.setCurrentTab(2);
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(MainActivity.this, LoginActivity.class);
		startActivity(intent);
	}


	/**
	 * The method read all updated xml
	 */
	public void onDownloadFinished() {

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				ModelLogic ml = ModelLogic.getInstance();
				//Helper.readAllXml("Ramilevi", "דרך השלום 13 נשר");
				//Helper.readAllXml("Shopersal", "שלמה המלך 55 חיפה");
				//ml.writeSer();
			}
		});
		thread.start();
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			Intent updateIntent = new Intent(this,UpdateProfileFragmentActivity.class);
			updateIntent.putExtra("calling-activity",MAIN);
			startActivity(updateIntent);
			return true;
		default:
			NavUtils.navigateUpFromSameTask(this);
			return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		case 0:
			Intent updateIntent = new Intent(this,UpdateProfileFragmentActivity.class);
			updateIntent.putExtra("calling-activity",MAIN);
			startActivity(updateIntent);
			break;
		case 1:
			startActivity(new Intent(this, AboutFragmentActivity.class));
			break;
		case 2:
			startActivity(new Intent(this,LoginActivity.class));
			break;
		default:
			break;
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

}
