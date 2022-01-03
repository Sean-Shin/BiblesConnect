package sean.to.readbiblesmart;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import sean.to.readbiblesmart.data.NotesData;
import sean.to.readbiblesmart.data.SettingsData;
import sean.to.readbiblesmart.data.StarData;
import sean.to.readbiblesmart.ui.dashboard.DashboardFragment;
import sean.to.readbiblesmart.ui.home.HomeFragment;
import sean.to.readbiblesmart.ui.notifications.NotificationsFragment;
import sean.to.readbiblesmart.util.BibleUtil;
import sean.to.readbiblesmart.util.Util;


public class MainActivity extends AppCompatActivity {
    public static JSONObject esvBibleObject;
    public static JSONObject kjvBibleObject;
    public static JSONObject nivBibleObject;
    public static JSONObject nltBibleObject;
    public static JSONObject esBibleObject;
    public static JSONObject frBibleObject;

    public static Context context;
    public static FragmentManager fragmentManager;
    public static HomeFragment homeFragment;
    public static DashboardFragment dashboardFragment;
    public static boolean esvloadingdone, kjvloadingdone ,nivloadingdone ,nltloadingdone;
    public static boolean esloadingdone, frloadingdone;
    public static StarData starData;
    public static NotesData notesData;
    public static SettingsData settingsData;
    public static int previousBottomButton;
    public static String previousQuery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        esvloadingdone = false;
        kjvloadingdone = false;
        nivloadingdone = false;
        nltloadingdone = false;
        esloadingdone = false;
        frloadingdone = false;

        previousQuery = "";

        starData = new StarData();
        starData.readStarData(context);

        settingsData = new SettingsData();
        settingsData.readSettingsData(context);

        notesData = new NotesData();
        notesData.readNoteData(context);


//        ActionBar actionBar = getSupportActionBar();


//        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                new ActivityResultCallback<Instrumentation.ActivityResult>() {
//                    @Override
//                    public void onActivityResult(Instrumentation.ActivityResult result) {
//                        if (result.getResultCode() == Activity.RESULT_OK) {
//                            // There are no request codes
//                            Intent data = result.getData();
//                            doSomeOperations();
//                        }
//                    }
//                });


//        initBible();
        AsyncInitBible();



        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();



//        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                int id = item.getItemId();
//                switch(id){
//                    //check id
//                }
//                return true;

//        prebutton.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(context, "pre",Toast.LENGTH_LONG).show();
//                    }
//                }
//        );

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


// after setupWithNavController
        navView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                        new Util().printLog("","onNavigationItemSelected");
                        Fragment selectedFragment = null;
                        switch (menuItem.getItemId()) {
                            case R.id.pre_menu:
                                checkWhichTab();
                                sendMessageToFragment("pre");
                                previousBottomButton = R.id.pre_menu;
                                new Util().printLog("****", "pre item");
                                break;
                            case R.id.next_menu:
                                checkWhichTab();
                                sendMessageToFragment("next");
                                previousBottomButton = R.id.next_menu;
                                new Util().printLog("****", "next item");

                                break;
                            case R.id.navigation_home:
                                activateFragment(menuItem.getItemId());
                                previousBottomButton = R.id.navigation_home;
                                break;
                            case R.id.navigation_dashboard:
                                activateFragment(menuItem.getItemId());
                                previousBottomButton = R.id.navigation_dashboard;
                                break;
                            case R.id.navigation_notifications:
                                activateFragment(menuItem.getItemId());
                                previousBottomButton = R.id.navigation_notifications;
                                break;
                        }
//                        FragmentTransaction transaction =
//                                getSupportFragmentManager().beginTransaction();
//                        transaction.replace(R.id.container, selectedFragment);
//                        transaction.commit();
                        return true;
                    }
                }
        );




    }
    public void checkWhichTab(){
        if(previousBottomButton == R.id.navigation_dashboard
                || previousBottomButton == R.id.navigation_notifications ){
            activateFragment(R.id.navigation_home);
        }
    }
    public void activateFragment(int rId){
//        GalleryFragment frag = new GalleryFragment();
//        FragmentManager ft = getFragmentManager();
//        FragmentTransaction fragmentTransaction =ft.beginTransaction();
//        fragmentTransaction.replace(R.id.home, frag);
//        // ft.addToBackStack(null);
//        fragmentTransaction.commit();
//        DashboardFragment frag = new DashboardFragment();
        FragmentManager ft = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction =ft.beginTransaction();
//        HomeFragment frag = (HomeFragment)ft.findFragmentById(R.id.navigation_home);
        switch (rId){
            case R.id.navigation_home:
//                homeFragment = (HomeFragment)ft.findFragmentById(R.id.navigation_home);
                homeFragment = (HomeFragment)ft.findFragmentByTag("home");
                if(homeFragment != null){
                    fragmentTransaction.replace(R.id.container, homeFragment);
                    fragmentTransaction.commit();
                }
                else {
                    homeFragment = new HomeFragment();
                    fragmentTransaction.replace(R.id.container, homeFragment, "home");
                    saveTransaction(fragmentTransaction, "home");
//                    fragmentTransaction.add(R.id.container, homeFragment,"home");
                }
                break;
            case R.id.navigation_dashboard:
                dashboardFragment = (DashboardFragment)ft.findFragmentByTag("dashboard");
//                dashboardFragment = (DashboardFragment)ft.findFragmentById(R.id.navigation_dashboard);
                if(dashboardFragment != null){
                    fragmentTransaction.replace(R.id.container, dashboardFragment);
                    fragmentTransaction.commit();
                }else{
                    dashboardFragment = new DashboardFragment();
                    fragmentTransaction.replace(R.id.container, dashboardFragment, "dashboard");
                    saveTransaction(fragmentTransaction,"dashboard");

//                    fragmentTransaction.add(R.id.container,dashboardFragment,"dashboard");
                }
                break;
            case R.id.navigation_notifications:
                fragmentTransaction.replace(R.id.container, NotificationsFragment.class,null);
                saveTransaction(fragmentTransaction, "noti");
                break;

        }

        updateActionBarTitle();


    }
    public void selectItem(int rId){
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setSelectedItemId(rId);
    }
    public FragmentTransaction saveTransaction(FragmentTransaction fragmentTransaction,String tag){
        //        fragmentTransaction.add(R.id.navigation_dashboard, frag);
//        fragmentTransaction.setReorderingAllowed(true);
//        fragmentTransaction.addToBackStack(tag);

        // ft.addToBackStack(null);
        fragmentTransaction.commit();
//        Toast.makeText(context, "new",Toast.LENGTH_LONG).show();
        return fragmentTransaction;
    }

    public void initBible(){
        try{
            esvBibleObject = new JSONObject(new BibleUtil().loadJSONFromAsset("esv"));
            kjvBibleObject = new JSONObject(new BibleUtil().loadJSONFromAsset("kjv"));
            nivBibleObject = new JSONObject(new BibleUtil().loadJSONFromAsset("niv"));
            nltBibleObject = new JSONObject(new BibleUtil().loadJSONFromAsset("nlt"));
        }catch (JSONException e){
            e.printStackTrace();
        }

    }
    public void AsyncInitBible(){
        AsyncBibleLoading asyncBibleLoading1 = new AsyncBibleLoading();
        asyncBibleLoading1.execute("esv");
        AsyncBibleLoading asyncBibleLoading2 = new AsyncBibleLoading();
        asyncBibleLoading2.execute("kjv");
        AsyncBibleLoading asyncBibleLoading3 = new AsyncBibleLoading();
        asyncBibleLoading3.execute("niv");
        AsyncBibleLoading asyncBibleLoading4 = new AsyncBibleLoading();
        asyncBibleLoading4.execute("nlt");

        AsyncBibleLoading asyncBibleLoading5 = new AsyncBibleLoading();
        asyncBibleLoading5.execute("rvr"); //es
        AsyncBibleLoading asyncBibleLoading6 = new AsyncBibleLoading();
        asyncBibleLoading6.execute("ost"); //fr
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            String action = data.getExtras().getString("action");
            String query = data.getExtras().getString("query");

            if(action.equals("pre")){

            }else if(action.equals("next")){
                Toast.makeText(context, "onActivityResult",Toast.LENGTH_LONG).show();
                sendMessageToFragment(query);
            }
        }
    }
    private void sendMessageToFragment(String action){
        sendMessageToFragment(action,"action");
    }
    private void sendQueryToFragment(String query){
        sendMessageToFragment(query,"query");
    }
    private void sendMessageToFragment(String action,String key){
        FragmentManager manager = this.getSupportFragmentManager();
//
//        HomeFragment myFragment = (HomeFragment) manager.findFragmentById(R.id.navigation_home);
//        List<Fragment> fragments = manager.getFragments();
//        Fragment lastFragment = fragments.get(fragments.size() - 1);
//
//
//        if(myFragment != null)
//            myFragment.changeBible(action);
//        else
//            Toast.makeText(context, "fragment is null",Toast.LENGTH_LONG).show();

        Bundle result = new Bundle();
        result.putString(key, action);

        if(fragmentManager != null)
        fragmentManager.setFragmentResult("requestKey", result);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu, menu);
//        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);
//        final SearchView searchView = (SearchView) searchViewItem.getActionView();
//        searchView.setQuery("Genesis 1 1", false);
////                (SearchView) MenuItemCompat.getActionView(searchViewItem);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                searchView.clearFocus();
//
//                activateFragment(R.id.navigation_home);
//                sendQueryToFragment(query);
////                homeFragment.updateBible(query);
////                selectItem(R.id.navigation_home);
//
//
//             /*   if(list.contains(query)){
//                    adapter.getFilter().filter(query);
//                }else{
//                    Toast.makeText(MainActivity.this, "No Match found",Toast.LENGTH_LONG).show();
//                }*/
//                return false;
//
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
////                adapter.getFilter().filter(newText);
//                return false;
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//    }
    //
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        // REQUEST_CODE is defined above
//        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
//            // Extract name value from result extras
//            String name = data.getExtras().getString("name");
//            int code = data.getExtras().getInt("code", 0);
//            // Toast the name to display temporarily on screen
//            Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
//        }
//    }

    @Override
    protected void onStart() {
        super.onStart();
//        if(previousBottomButton != R.id.navigation_home)
        activateFragment(R.id.navigation_home);
    }
    private void updateActionBarTitle(){
        getSupportActionBar().setTitle(R.string.app_nickname);
        getSupportActionBar().setSubtitle("Bibles at a once");
    }

    @Override
    protected void onStop() {
        starData.saveStarData(context);
        settingsData.saveSettingsData(context);
        notesData.saveNoteData(context);
        super.onStop();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settingsButton:
                startSettings();
                break;
            case R.id.aboutBtn:
                startAbout();
//                startNotes();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void startSettings(){
        Intent intent = new Intent(this, SettingsActivity.class);
//        EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
    private void startAbout(){
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }


//    public void startNotes(){
//        Intent intent = new Intent(this, NotesActivity.class);
//        startActivity(intent);
//    }
    private class AsyncBibleLoading extends AsyncTask<String,String, JSONObject>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            String bType = strings[0].toLowerCase();
//            JSONObject result = esvBibleObject;
            boolean done = false;

            try{
                switch (bType){
                    case "esv" :
                        esvBibleObject = new JSONObject(new BibleUtil().loadJSONFromAsset("esv"));
//                        result = esvBibleObject;
                        done = true;
                        break;
                    case "kjv" :
                        kjvBibleObject = new JSONObject(new BibleUtil().loadJSONFromAsset("kjv"));
//                        result = kjvBibleObject;
                        done = true;
                        break;
                    case "niv" :
                        nivBibleObject = new JSONObject(new BibleUtil().loadJSONFromAsset("niv"));
//                        result = nivBibleObject;
                        done = true;
                        break;
                    case "nlt" :
                        nltBibleObject = new JSONObject(new BibleUtil().loadJSONFromAsset("nlt"));
//                        result = nltBibleObject;
                        done = true;
                        break;
                    case "rvr" : //es
                        esBibleObject = new JSONObject(new BibleUtil().loadJSONFromAsset("rvr"));
//                        result = nltBibleObject;
                        done = true;
                        break;
                    case "ost" : //fr
                        frBibleObject = new JSONObject(new BibleUtil().loadJSONFromAsset("ost"));
//                        result = nltBibleObject;
                        done = true;
                        break;
                }

            }catch (JSONException e){
                e.printStackTrace();
            }
            return done ? postResult(bType) : null;
        }



        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            if(jsonObject != null){
                try{
                   String bType = jsonObject.getString("async");
                   switch (bType){
                       case "esv":
                           esvloadingdone = true;
                           break;
                       case "kjv":
                           kjvloadingdone = true;
                           break;
                       case "niv":
                           nivloadingdone = true;
                           break;
                       case "nlt":
                           nltloadingdone = true;
                           break;
                       case "rvr":
                           esloadingdone = true;
                           break;
                       case "ost":
                           frloadingdone = true;
                           break;
                   }
                   if(isLoadingDone())
                        sendMessageToFragment("loadingdone");
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }
        private JSONObject postResult(String bType){
            JSONObject result = new JSONObject();
            try{
                result.put("async",bType);
                return result;
            }catch(JSONException e){
                e.printStackTrace();
            }
            return null;
        }
        public boolean isLoadingDone(){
            if(esvloadingdone && kjvloadingdone && nivloadingdone && nltloadingdone
             && esloadingdone && frloadingdone)
                return true;
            return false;
        }
    }


}
