package com.example.athira.sahapaadi;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.willowtreeapps.spruce.Spruce;
import com.willowtreeapps.spruce.animation.DefaultAnimations;
import com.willowtreeapps.spruce.sort.DefaultSort;
import java.util.ArrayList;
import java.util.List;

public class FavActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

  private Animator spruceAnimator;

  private ProgressDialog pDialog;

  DBHelper mDBHelper;

  List<String> rid;
  List<String> name;
  List<String> desc;
  List<Integer> type;
  List<Integer> isd;

  String sub;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    pDialog = new ProgressDialog(this);
    pDialog.setCancelable(false);

    mDBHelper = new DBHelper(this);

    rid = new ArrayList<>();
    name = new ArrayList<>();
    desc = new ArrayList<>();
    type = new ArrayList<>();
    isd = new ArrayList<>();

    Intent intent = getIntent();
    sub = intent.getStringExtra("sub");
    Cursor cursor = mDBHelper.getFav(sub);
    while (cursor.moveToNext()) {
      rid.add(cursor.getString(0));
      name.add(cursor.getString(1));
      desc.add(cursor.getString(2));
      type.add(cursor.getInt(5));
      isd.add(cursor.getInt(4));
    }

    final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
    recyclerView.setHasFixedSize(true);

    LinearLayoutManager linearLayoutManager =
        new LinearLayoutManager(FavActivity.this) {
          @Override public void onLayoutChildren(RecyclerView.Recycler recycler,
              RecyclerView.State state) {
            super.onLayoutChildren(recycler, state);
            // Animate in the visible children
            spruceAnimator =
                new Spruce.SpruceBuilder(recyclerView).sortWith(new DefaultSort(100))
                    .animateWith(DefaultAnimations.shrinkAnimator(recyclerView, 800),
                        ObjectAnimator.ofFloat(recyclerView, "translationX",
                            -recyclerView.getWidth(), 0f).setDuration(800))
                    .start();
          }
        };
    recyclerView.setAdapter(
        new RecyclerAdapter(FavActivity.this, rid, name, desc, type, isd));
    recyclerView.setLayoutManager(linearLayoutManager);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show();
      }
    });

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle =
        new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);
  }

  @Override public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @SuppressWarnings("StatementWithEmptyBody") @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    if (id == R.id.nav_camera) {
      // Handle the camera action
    } else if (id == R.id.nav_gallery) {

    } else if (id == R.id.nav_slideshow) {

    }
    else if (id == R.id.nav_share) {
      startActivity(new Intent(FavActivity.this,ProfileUpdation.class));
    } else if (id == R.id.nav_send) {
      SessionManager session = new SessionManager(getApplicationContext());
      session.setLogin(false);
      DBHelper dbHelper = new DBHelper(this);
      dbHelper.revRegistration();
      dbHelper.revUser();
      dbHelper.revSub();
      dbHelper.revDown();
      dbHelper.revFav();
      startActivity(new Intent(this, SplashActivity.class));
    }
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

  public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private Context mContext;
    List<String> rid;
    List<String> name;
    List<String> desc;
    List<Integer> type;
    List<Integer> isd;

    public RecyclerAdapter(Context context, List<String> rid, List<String> name, List<String> desc,
        List<Integer> type, List<Integer> isd) {
      this.mContext = context;
      this.rid = rid;
      this.name = name;
      this.desc = desc;
      this.type = type;
      this.isd = isd;
    }

    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      MyViewHolder holder =
          new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_sub, parent, false));
      return holder;
    }

    @Override public void onBindViewHolder(final RecyclerAdapter.MyViewHolder holder, final int position) {
      if (position % 2 == 1) holder.mView.setBackgroundColor(Color.parseColor("#FF4081"));
      holder.sub.setText(name.get(position));
      holder.desc.setText(desc.get(position));
      holder.root.setOnClickListener(new OnClickListener() {
        @Override public void onClick(View view) {

          startActivity(new Intent(FavActivity.this,ReaderActivity.class));
        }
      });
      if (isd.get(position)==0){
        holder.image2.setVisibility(View.GONE);
      }
      holder.image1.setBackgroundResource(R.drawable.ic_star_black_24dp);
      holder.image2.setOnClickListener(new OnClickListener() {
        @Override public void onClick(View view) {
          mDBHelper.putDown(rid.get(position),name.get(position),desc.get(position),sub,isd.get(position),type.get(position));
          holder.image2.setBackgroundResource(R.drawable.ic_cloud_download_black_24dp);

        }
      });

      switch (type.get(position)){
        case 1 : holder.type.setText("PDF");
                break;
        case 2 : holder.type.setText("PPT");
          break;
        case 3 : holder.type.setText("Note");
          break;
        case 4 : holder.type.setText("Question Paper");
          break;
        case 5 :  holder.type.setText("Book");
          break;
      }

    }

    @Override public int getItemCount() {
      return rid.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

      LinearLayout root;
      View mView;
      TextView sub, type, desc;
      ImageView image1,image2;

      public MyViewHolder(View view) {
        super(view);
        root = (LinearLayout) view.findViewById(R.id.rootView);
        mView = view.findViewById(R.id.stroke);
        sub = (TextView) view.findViewById(R.id.textSub);
        type = (TextView) view.findViewById(R.id.textType);
        desc = (TextView) view.findViewById(R.id.textDesc);

        image1 = (ImageView) view.findViewById(R.id.image1);
        image2 = (ImageView) view.findViewById(R.id.image2);
      }
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.main1, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    // Handle item selection
    switch (item.getItemId()) {
      case R.id.action_favorite:
        return true;
      case R.id.action_download:
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
