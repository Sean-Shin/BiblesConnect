package sean.to.readbiblesmart.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import sean.to.readbiblesmart.MainActivity;
import sean.to.readbiblesmart.R;
import sean.to.readbiblesmart.data.BibleData;
import sean.to.readbiblesmart.ui.home.HomeFragment;
import sean.to.readbiblesmart.ui.notifications.NotificationsFragment;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        dashboardViewModel =
//                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
//        final TextView textView = root.findViewById(R.id.text_dashboard);
//        dashboardViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        TextView textView = (TextView) root.findViewById(R.id.textView);
        textView.setText(R.string.biblenamelist);
        // initiate a ListView
        ListView listView = (ListView) root.findViewById(R.id.lisview);
        // set the adapter to fill the data in ListView
        ListAdapter listAdapter = new ListAdapter(root.getContext(), BibleData.bibleNames);
        listView.setAdapter(listAdapter);

//        CheckedTextView textView = (CheckedTextView)root.findViewById(R.id.checkedTextView);
//        for(int i =0; i< BibleData.bibleNames.length; i++){
//            textView.setText(BibleData.bibleNames[i]);
//        }
//        textView.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);
//        textView.setChecked(true);

        return root;
    }
    public class ListAdapter extends BaseAdapter {
        String[] names;
        Context context;
        LayoutInflater inflter;
        String value;
        String bible;

        public ListAdapter(Context context, String[] names) {
            this.context = context;
            this.names = names;
            inflter = (LayoutInflater.from(context));

        }

        @Override
        public int getCount() {
            return names.length;
        }

        @Override
        public String getItem(int position) {

            return names[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            view = inflter.inflate(R.layout.list_items, null);

            final CheckedTextView checkedTextView = (CheckedTextView) view.findViewById(R.id.checkedTextView);

            checkedTextView.setText(names[position]);


// perform on Click Event Listener on CheckedTextView
            checkedTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkedTextView.isChecked()) {
// set cheek mark drawable and set checked property to false
                        value = "un-Checked";
                        checkedTextView.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);
                        checkedTextView.setChecked(false);



                    } else {
// set cheek mark drawable and set checked property to true
                        value = "Checked";
                        checkedTextView.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);
                        checkedTextView.setChecked(true);
                        postBibleNames(position);
                    }
//                    Toast.makeText(context, "index "+position, Toast.LENGTH_SHORT).show();
                }
            });
            return view;
        }
    }
    public void postBibleNames(int bibleNameIndex){

        FragmentManager ft = getActivity().getSupportFragmentManager();

        FragmentTransaction fragmentTransaction =ft.beginTransaction();
//        HomeFragment frag = (HomeFragment)ft.findFragmentById(R.id.navigation_home);

        if(MainActivity.homeFragment == null){
            MainActivity.homeFragment = new HomeFragment();
            fragmentTransaction.replace(R.id.container, MainActivity.homeFragment,"home");
            //        fragmentTransaction.add(R.id.navigation_dashboard, frag);
            fragmentTransaction.setReorderingAllowed(true);
            fragmentTransaction.addToBackStack("home");
            // ft.addToBackStack(null);
            fragmentTransaction.commit();
        }


        Bundle result = new Bundle();
        result.putString("bible", Integer.toString(bibleNameIndex));
        getParentFragmentManager().setFragmentResult("requestKey", result);

        selectItem(R.id.navigation_home);

    }
    public void selectItem(int rId){
        BottomNavigationView navView = getActivity().findViewById(R.id.nav_view);
        navView.setSelectedItemId(rId);
    }

}