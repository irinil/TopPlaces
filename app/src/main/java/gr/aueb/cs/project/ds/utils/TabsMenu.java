package gr.aueb.cs.project.ds.utils;

/**
 * Created by Irini on 27/5/2016.
 */
import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;


public class TabsMenu extends FragmentActivity implements OnTabChangeListener, OnPageChangeListener {

    MyFragmentPagerAdapter pageAdapter;
    private ViewPager mViewPager;
    private TabHost mTabHost;
    ArrayList<TopPlaceInformation> places = new ArrayList<TopPlaceInformation>();
    TopList f1 = new TopList();
    MapsActivity f2 = new MapsActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tabs_menu);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        // Tab Initialization
        initialiseTabHost();
        Intent i= getIntent();
        places = i.getParcelableArrayListExtra("TopPlaceInformation");
        //places = (ArrayList<TopPlaceInformation>) i.getSerializableExtra("TopPlaceInformation");
        f1.setPlaces(places);
        f2.setPlaces(places);

        // Fragments and ViewPager Initialization
        List<Fragment> fragments = getFragments();
        pageAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(pageAdapter);
        mViewPager.addOnPageChangeListener(TabsMenu.this);

    }


    // Method to add a TabHost
    private static void AddTab(TabsMenu activity, TabHost tabHost, TabHost.TabSpec tabSpec) {
        tabSpec.setContent(new MyTabFactory(activity));
        tabHost.addTab(tabSpec);
    }

    // Manages the Tab changes, synchronizing it with Pages
    public void onTabChanged(String tag) {
        int pos = this.mTabHost.getCurrentTab();
        this.mViewPager.setCurrentItem(pos);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    // Manages the Page changes, synchronizing it with Tabs
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        int pos = this.mViewPager.getCurrentItem();
        this.mTabHost.setCurrentTab(pos);
    }

    @Override
    public void onPageSelected(int arg0) {
    }

    private List<Fragment> getFragments() {
        List<Fragment> fList = new ArrayList<Fragment>();

            fList.add(f1);
            fList.add(f2);


            return fList;
        }


    // Tabs Creation
    private void initialiseTabHost() {
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();

        TabsMenu.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("TopList").setIndicator("TopList"));
        TabsMenu.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("Map").setIndicator("Map"));


        mTabHost.setOnTabChangedListener(this);
    }
}

