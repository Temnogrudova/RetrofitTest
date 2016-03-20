package temnogrudova.com.retrofittest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;
import java.util.Arrays;
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
    AccountHeader.Result  headerResult;
    Drawer.Result drawerResult;

    public static final String API_URL = "http://jsonplaceholder.typicode.com";
    RecyclerView twitterMessages;
    ArrayList<Post> items;

    private boolean mRotated;
    Bundle savedInstanceState = null;
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
        this.savedInstanceState = savedInstanceState;

        setContentView(R.layout.main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Bitmap bIcon  = BitmapFactory.decodeResource(getResources(), R.drawable.avatar_man);
        Drawable dIcon  = new BitmapDrawable(getResources(), bIcon);
        Bitmap fbIcon  = BitmapFactory.decodeResource(getResources(), R.drawable.ic_facebook_box);
        Drawable icon = new BitmapDrawable(getResources(), fbIcon);
        // Create a few sample profile
        // NOTE you have to define the loader logic too. See the CustomApplication for more details
        final IProfile profileMain = new ProfileDrawerItem().withName("User Name").withIcon(dIcon);
        headerResult = new AccountHeader()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .withProfileImagesClickable(false)
                .withProfileImagesVisible(true)
                .addProfiles(
                        profileMain,
                        //don't ask but google uses 14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)
                        new ProfileSettingDrawerItem().withName("Sign in").withIcon(icon).withIdentifier(100),
                        new ProfileSettingDrawerItem().withName("Exit").withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_exit_to_app).actionBarSize().paddingDp(5).colorRes(R.color.material_drawer_primary_text)).withIdentifier(101)
                        //new ProfileSettingDrawerItem().withName("Manage Account").withIcon(GoogleMaterial.Icon.gmd_settings)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        if (profile.getIdentifier() == 100) {
                            Toast.makeText(getApplicationContext(), "Facebook cliked",Toast.LENGTH_SHORT).show();
                        }
                        if (profile.getIdentifier() == 101) {
                            Toast.makeText(getApplicationContext(), "Exit cliked",Toast.LENGTH_SHORT).show();
                        }

                        //false if you have not consumed the event and it should close the drawer
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();

        drawerResult = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.drawer_header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Element1").withIcon(FontAwesome.Icon.faw_map_marker).withIdentifier(0),
                        new PrimaryDrawerItem().withName("Element2").withIcon(FontAwesome.Icon.faw_list).withIdentifier(1),
                        new PrimaryDrawerItem().withName("Element3").withIcon(FontAwesome.Icon.faw_check).withIdentifier(2),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("Element4").withIcon(FontAwesome.Icon.faw_cog).withIdentifier(3)
                )
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Скрываем клавиатуру при открытии Navigation Drawer
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                    }
                }).build();
        drawerResult.setOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                    if (drawerItem != null) {
                        switch (drawerItem.getIdentifier()){
                            case 0:
                                Toast.makeText(getApplicationContext(), "Element1 cliked",Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                Toast.makeText(getApplicationContext(), "Element2 cliked",Toast.LENGTH_SHORT).show();
                                break;
                            case 2:
                                Toast.makeText(getApplicationContext(), "Element3 cliked",Toast.LENGTH_SHORT).show();
                                break;
                            case 3:
                                Toast.makeText(getApplicationContext(), "Element4 cliked",Toast.LENGTH_SHORT).show();
                                break;
                        }


                }

            }
        });



        twitterMessages = (RecyclerView)findViewById(R.id.myList);
        twitterMessages.setLayoutManager(new LinearLayoutManager(this));

        //change orientation
        items = (ArrayList<Post>) getLastCustomNonConfigurationInstance();
        if (items!=null){
            RecyclerAdapter recyclerAdapter = new RecyclerAdapter(items);
            twitterMessages.setAdapter(recyclerAdapter);
        }

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
                       items = (ArrayList<Post>) response.body();

                        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(items);
                        twitterMessages.setAdapter(recyclerAdapter);
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }

                });

                //Call<Post> call  = retrofitService.createPost(new Post("1","2", "Kate", "T"));
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

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return items;
    }

}
