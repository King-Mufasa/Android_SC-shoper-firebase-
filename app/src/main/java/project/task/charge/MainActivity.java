package project.task.charge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import project.task.charge.member.Member;
import project.task.charge.util.Global;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public String TAG = "Main";
    private LinearLayout all_members, new_task, my_task, about_us;
    public static MainActivity myself;
    private Intent intent;
    private TextView current_user_name, current_user_email;
    private NavigationView navigationView;
    ViewPager viewPager;
    private ArrayList<Member> array_all_members = new ArrayList<Member>();

    private String USER_NAME = "Name";
    private String USER_GENDER = "Gender";
    private String USER_EMAIL = "Email";
    private String TASK_TITLE = "Title";
    private String TASK_DATE_START = "Start date";
    private String TASK_DATE_END = "End date";
    private String TASK_DURATION = "Duration";
    private String TASK_HIRED = "Hired members";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myself = this;
        all_members = (LinearLayout)findViewById(R.id.btn_all_members);
        new_task = (LinearLayout)findViewById(R.id.btn_newtask);
        my_task = (LinearLayout)findViewById(R.id.btn_mytask);
        about_us = (LinearLayout)findViewById(R.id.btn_about_us);
        navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.menu_all_members:
                        intent = new Intent(MainActivity.this, members.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_make_task:
                        intent = new Intent(MainActivity.this, make_new_task.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_mytask:
                        intent = new Intent(MainActivity.this, my_task.class);
                        startActivity(intent);
//                showHelp();
                        return true;
                    case R.id.menu_profile_setting:
//                showHelp();
                        return true;
                    case R.id.menu_exit:
                        finish();
                        return true;
                    default:
                        return true;
                }
            }
        });
        View headerView = navigationView.getHeaderView(0);
        current_user_name = (TextView)headerView.findViewById(R.id.current_user_name);
        current_user_email = (TextView)headerView.findViewById(R.id.current_user_email);

        all_members.setOnClickListener(this);
        new_task.setOnClickListener(this);
        my_task.setOnClickListener(this);
        about_us.setOnClickListener(this);
        FirebaseApp.initializeApp(this);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.logo);
        toolbar.setTitle(R.string.project_id);
        toolbar.setTitleTextColor(Color.BLACK);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Member");
        Toast.makeText(this, myRef.toString(), Toast.LENGTH_LONG).show();
//        myRef.setValue("test@test.com");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String value = dataSnapshot.getValue(String.class);
//                Log.d(TAG, "Value is:" + value);
                if (dataSnapshot.exists()){
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) dataSnapshot.getValue();
//                    Member Member = dataSnapshot.getValue(Member.class);
//                    Log.d(TAG, "User name: " + Member.getName() + ", email " + Member.getEmail());
                    for (String key : dataMap.keySet()){

                        Object data = dataMap.get(key);

                        try{
                            HashMap<String, Object> userData = (HashMap<String, Object>) data;
                            String userName = userData.get("Name").toString();
                            String userEmail = userData.get("Email").toString();
                            String userGender = userData.get("Gender").toString();
                            if(userEmail.equals(Global.current_user_email)) {
                                Global.current_user_name = userName;
                                current_user_name.setText(Global.current_user_name);
                                current_user_email.setText(Global.current_user_email);
                            }
                            array_all_members.add(new Member(userName, userEmail, userGender));
                        }catch (ClassCastException cce){


                            try{

                                String mString = String.valueOf(dataMap.get(key));
//                                Log.e(TAG, mString);

                            }catch (ClassCastException cce2){

                            }
                        }

                    }
                    Global.array_all_members = array_all_members;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG,"Failed to rad value ", databaseError.toException());
            }
        });
        DatabaseReference post_Ref = database.getReference("POST");
        post_Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    HashMap<String, Object> postData = (HashMap<String, Object>) dataSnapshot.getValue();
                        try{
                            String post_title = postData.get("Title").toString();
                            String post_desc = postData.get("Description").toString();
                            String post_created = postData.get("Created date").toString();
                            TextView title = (TextView) findViewById(R.id.post_title);
                            TextView desc = (TextView)findViewById(R.id.post_desc);
                            TextView created = (TextView)findViewById(R.id.post_created);
                            title.setText(post_title.toString());
                            desc.setText(post_desc.toString());
                            created.setText(post_created.toString());



                        }catch (ClassCastException cce){
                        }


                    Global.array_all_members = array_all_members;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG,"Failed to rad value ", databaseError.toException());
            }
        });

        DatabaseReference Project_Ref = database.getReference("Project");
        Project_Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.e("Count " ,""+snapshot.getChildrenCount());
                for(DataSnapshot ds : snapshot.getChildren()) {
                    String name = ds.getKey();
                    Global.list_project.add(name);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("The read failed: " ,databaseError.toString());
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_all_members:
                intent = new Intent(this, members.class);
                startActivity(intent);
                break;
            case R.id.btn_mytask:
                break;
            case R.id.btn_newtask:
                intent = new Intent(this, make_new_task.class);
                startActivity(intent);
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_all_members:
                intent = new Intent(this, members.class);
                startActivity(intent);
                return true;
            case R.id.menu_make_task:
                intent = new Intent(this, make_new_task.class);
                startActivity(intent);
                return true;
            case R.id.menu_mytask:
//                showHelp();
                return true;
            case R.id.menu_profile_setting:
//                showHelp();
                return true;
            case R.id.menu_exit:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public static MainActivity getInstance(){
        return myself;
    }
}