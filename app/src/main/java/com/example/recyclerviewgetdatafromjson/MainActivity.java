package com.example.recyclerviewgetdatafromjson;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // creating variables for our UI components.
    //String url = "https://jsonkeeper.com/b/WO6S";
    private ArrayList<CourseModal> courseArrayList;
    private RecyclerView courseRV;
    private CourseRVAdapter courseRVAdapter;
    private ProgressBar loadingPB;

    private boolean isLoading;
    private boolean isLastPage;
    private int currentPage = 1;
    private int totalPage = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializing our variables.
        courseRV = findViewById(R.id.idRVCourses);
        loadingPB = findViewById(R.id.idPBLoading);

        // on below line we are adding our array list to our adapter class.
        courseRVAdapter = new CourseRVAdapter(MainActivity.this, courseArrayList);

        // on below line we are setting layout manger to our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(this);
        courseRV.setLayoutManager(manager);
        courseRV.setAdapter(courseRVAdapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        courseRV.addItemDecoration(itemDecoration);

        // initializing our array list.
        //courseArrayList = new ArrayList<>();

        // calling a method to add data to our array list.
        getFirstData();




        // on below line we are setting
        // adapter to our recycler view.
        //courseRV.setHasFixedSize(true);

        courseRV.addOnScrollListener(new PaginationScrollListener(manager) {
            @Override
            public void loadMoreItems() {
                isLoading = true;
                loadingPB.setVisibility(View.VISIBLE);
                currentPage++;
                LoadNextPage();
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }
        });
    }

    /***
     * Load data first
     */
    private void getFirstData() {
        courseArrayList = getCourseArrayList();
        courseRVAdapter.setData(courseArrayList);

    }

    private ArrayList<CourseModal> getCourseArrayList() {

        Toast.makeText(this, "Load data page " + currentPage, Toast.LENGTH_SHORT).show();

        ArrayList<CourseModal> arrayList = new ArrayList<>();
        arrayList.add(new CourseModal("Title Name","Online Batch","6 Tracks", "https://media.geeksforgeeks.org/img-practice/banner/fork-cpp-thumbnail.png"));
        arrayList.add(new CourseModal("Title Name","Online Batch","6 Tracks", "https://media.geeksforgeeks.org/img-practice/banner/linux-shell-scripting-thumbnail.png"));
        arrayList.add(new CourseModal("Title Name","Online Batch","6 Tracks", "https://media.geeksforgeeks.org/img-practice/banner/Workshop-DSA-thumbnail.png"));
        arrayList.add(new CourseModal("Title Name","Online Batch","6 Tracks", "https://media.geeksforgeeks.org/img-practice/banner/dsa-self-paced-thumbnail.png"));
        arrayList.add(new CourseModal("Title Name","Online Batch","6 Tracks", "https://media.geeksforgeeks.org/img-practice/banner/fork-cpp-thumbnail.png"));
        arrayList.add(new CourseModal("Title Name","Online Batch","6 Tracks", "https://media.geeksforgeeks.org/img-practice/banner/linux-shell-scripting-thumbnail.png"));

        return  arrayList;
    }

    private void LoadNextPage() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<CourseModal> arrayList = getCourseArrayList();
                courseArrayList.addAll(arrayList);
                courseRVAdapter.notifyDataSetChanged();

                isLoading = false;
                loadingPB.setVisibility(View.GONE);
                if (currentPage == totalPage) {
                    isLastPage = true;
                }
            }
        }, 2000);
    }

}
