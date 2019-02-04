package com.fc.radiate.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fc.radiate.DataManagement.FetchData;
import com.fc.radiate.Fragments.CountriesFragment;
import com.fc.radiate.Fragments.FavoriteFragment;
import com.fc.radiate.Fragments.PopularFragment;
import com.fc.radiate.NotificationsAndReminders.NotificationUtils;
import com.fc.radiate.*;
import com.fc.radiate.NotificationsAndReminders.ReminderUtilities;
import com.fc.radiate.Settings.SettingsActivity;

import java.io.Serializable;
import java.util.ArrayList;

import static com.fc.radiate.DataManagement.FetchData.getCurrentStation;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
        BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    public static ProgressDialog progressDialog;
    private BottomNavigationView main_nav;
    private FrameLayout main_frame;
    private FavoriteFragment favoriteFragment;
    private PopularFragment popularFragment;
    private CountriesFragment countriesFragment;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private ListView navCategoriesView;
    private Button searchButton;
    private EditText InputradioName;
    private EditText InputradioTags;
    private Spinner CountrySpinner;
    private Spinner LanguageSpinner;

    public static void showToastMethod(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean isOnline = ConnectivityReceiver.isConnected();
        progressDialog = new ProgressDialog(this);
        if (isOnline) {
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        } else {
            try {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Info");
                alertDialog.setMessage("Internet not available, Cross check your internet connectivity and try again");
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();

                    }
                });

                alertDialog.show();
            } catch (Exception e) {
            }
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        main_frame = findViewById(R.id.main_frame);
        main_nav = findViewById(R.id.main_nav);
        drawerLayout = findViewById(R.id.drawerLayout);
        navCategoriesView = findViewById(R.id.categoriesListView);
        navigationView = findViewById(R.id.navigation_view);
        searchButton = findViewById(R.id.SearchButtonNav);
        InputradioName = findViewById(R.id.SearchNameValue);
        InputradioTags = findViewById(R.id.tagValue);
        CountrySpinner = findViewById(R.id.countrySpinner);
        LanguageSpinner = findViewById(R.id.LanguageSpinner);

        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, new ArrayList<String>());

        ArrayAdapter<String> CountryListAdapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_spinner_item, new ArrayList<String>());

        ArrayAdapter<String> LanguageListAdapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_spinner_item, new ArrayList<String>());

        FetchData.fetchLanguages(LanguageListAdapter);
        //FetchData.fetchLanguages(categoriesAdapter);
        FetchData.fetchCountries(CountryListAdapter);

        navCategoriesView.setAdapter(categoriesAdapter);
        navCategoriesView.setOnItemClickListener(this);

        CountryListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CountrySpinner.setAdapter(CountryListAdapter);

        LanguageListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        LanguageSpinner.setAdapter(LanguageListAdapter);


        actionBarDrawerToggle = new ActionBarDrawerToggle
                (this, drawerLayout, R.string.openSideNav, R.string.closeSideNav);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        favoriteFragment = new FavoriteFragment();
        popularFragment = new PopularFragment();
        countriesFragment = new CountriesFragment();

        // String intentFragment = getIntent().getExtras().getString("FragmentToLoad");

        if (getIntent().hasExtra("FragmentToLoad") &&
                getIntent().getExtras().getString("FragmentToLoad").equals("COUNTRY")) {
            main_nav.setSelectedItemId(R.id.nav_countries);
            setNewFragment(countriesFragment);
        } else {
            main_nav.setSelectedItemId(R.id.nav_popular);
            setNewFragment(popularFragment);
        }


        main_nav.setOnNavigationItemSelectedListener(this);
        searchButton.setOnClickListener(this);
        ReminderUtilities.scheduleRadiateReminder(this);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.action_settings) {
                    Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                    startActivity(intent);
                } else if (id == R.id.randomStation) {
                    if (getCurrentStation() != null) {
                        Intent i = new Intent(MainActivity.this, PlayerActivity.class);
                        i.putExtra("st", (Serializable) getCurrentStation());
                        startActivity(i);
                    } else {
                        Toast.makeText(MainActivity.this, "There is no playing/loading radio!",
                                Toast.LENGTH_SHORT).show();
                    }
                } else if (id == R.id.goToPlaying) {
                    if (getCurrentStation() != null) {
                        Intent i = new Intent(MainActivity.this, PlayerActivity.class);
                        i.putExtra("st", (Serializable) getCurrentStation());
                        startActivity(i);
                    } else {
                        Toast.makeText(MainActivity.this, "There is no playing/loading radio!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });
    }

    private void setNewFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.randomStation) {
            if (getCurrentStation() != null) {
                Intent i = new Intent(MainActivity.this, PlayerActivity.class);
                i.putExtra("st", (Serializable) getCurrentStation());
                startActivity(i);
            } else {
                Toast.makeText(MainActivity.this, "There is no playing/loading radio!",
                        Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.goToPlaying) {
            if (getCurrentStation() != null) {
                Intent i = new Intent(MainActivity.this, PlayerActivity.class);
                i.putExtra("st", (Serializable) getCurrentStation());
                startActivity(i);
            } else {
                Toast.makeText(MainActivity.this, "There is no playing/loading radio!",
                        Toast.LENGTH_SHORT).show();
            }
        } else if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else if (id == R.id.not1) {
            NotificationUtils.remindUserListenRadio(this);
        } else if (id == R.id.not2) {
            NotificationUtils.remindUserDiscoverRadio(this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView tv = (TextView) view;
        Toast.makeText(MainActivity.this, tv.getText() + "  " + position, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int selectedItemId = menuItem.getItemId();
        //progressDialog.show();
        if (selectedItemId == R.id.nav_fav) {
            setNewFragment(favoriteFragment);
            return true;
        } else if (selectedItemId == R.id.nav_popular) {
            setNewFragment(popularFragment);
            return true;
        } else if (selectedItemId == R.id.nav_countries) {
            setNewFragment(countriesFragment);
            return true;
        } else return false;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == searchButton.getId()) {
            String searchName = InputradioName.getText().toString();
            String countryName = CountrySpinner.getSelectedItem().toString();
            String languageName = LanguageSpinner.getSelectedItem().toString();
            String tags = InputradioTags.getText().toString();

            boolean isReverse = true;
            int offset = 0;
            SharedPreferences preferences = PreferenceManager
                    .getDefaultSharedPreferences(this.getApplicationContext());
            String limit = preferences.getString("searchLimit", "10");
            String orderBy = preferences.getString("searchComponent", "name");

            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Searching...");
            progressDialog.show();
            FetchData.fetchSearchResult(
                    searchName,
                    countryName,
                    languageName,
                    tags,
                    limit,
                    orderBy,
                    isReverse,
                    offset
            );

            progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (FetchData.getSearchResult() == null
                            || FetchData.getSearchResult().size() == 0) {
                        showToastMethod(getApplicationContext(), "Not Found!");
                    } else {
                        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                        startActivity(intent);
                    }
                }
            });

        }
    }

}
