package temnogrudova.com.retrofittest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class MainActivity extends AppCompatActivity {
    public static final String API_URL = "http://jsonplaceholder.typicode.com";
    RecyclerView twitterMessages;
    /*Asynchronous in Retrofit
    Описание запросов для retrofit-а

    Для того, что бы retroit понял структуру необходимых нам запросов,
     ее нужно выразить в виде интерфейса с методами, покрытыми аннотациями,
     описывающими тип http запросов и параметры для формирования полного url-а:
   */
    public interface RetrofitService {
        @GET("/posts")
        Call<List<Post>> getPosts();

        /*
        @POST("/posts")
        Call<Post> createPost(@Body Post post);
        */
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        twitterMessages = (RecyclerView)findViewById(R.id.myList);
        twitterMessages.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Запросы выполняются путем создания объекта,
                // реализующего интерфейс нашего bakcend-а,
                // и вызова конкретных запросов через методы этого объекта.
                        Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(API_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();


                // Create an instance of our Retrofit API interface.
                RetrofitService retrofitService = retrofit.create(RetrofitService.class);

                Call<List<Post>> repos =  retrofitService.getPosts();

                repos.enqueue(new Callback<List<Post>>() {
                    @Override
                    public void onResponse(Response<List<Post>> response) {
                        ArrayList<Post> items = (ArrayList<Post>) response.body();

                        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(items);
                        twitterMessages.setAdapter(recyclerAdapter);
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }

                });
                //   Call<Post> call  = retrofitService.createPost(new Post("1","2", "Kate", "T"));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
