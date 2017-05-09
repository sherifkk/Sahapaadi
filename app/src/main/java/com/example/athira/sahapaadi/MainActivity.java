package com.example.athira.sahapaadi;

import android.animation.Animator;
import android.animation.ObjectAnimator;
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
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.willowtreeapps.spruce.Spruce;
import com.willowtreeapps.spruce.animation.DefaultAnimations;
import com.willowtreeapps.spruce.sort.DefaultSort;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

  private Animator spruceAnimator;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    List<String> sid = new ArrayList<>();
    List<String> name = new ArrayList<>();
    List<String> pdf = new ArrayList<>();
    List<String> ppt = new ArrayList<>();
    List<String> note = new ArrayList<>();
    List<String> qps = new ArrayList<>();
    List<String> books = new ArrayList<>();
    sid.clear();
    name.clear();
    pdf.clear();
    ppt.clear();
    note.clear();
    qps.clear();
    books.clear();

    DBHelper DBHelper = new DBHelper(this);
    Cursor cursor = DBHelper.getSub();

    while (cursor.moveToNext()){
      sid.add(cursor.getString(0));
      name.add(cursor.getString(1));
      pdf.add(cursor.getString(2));
      ppt.add(cursor.getString(3));
      note.add(cursor.getString(4));
      qps.add(cursor.getString(5));
      books.add(cursor.getString(6));
    }



    final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
    recyclerView.setHasFixedSize(true);

    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this) {
      @Override
      public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        // Animate in the visible children
        spruceAnimator = new Spruce.SpruceBuilder(recyclerView)
            .sortWith(new DefaultSort(100))
            .animateWith(DefaultAnimations.shrinkAnimator(recyclerView, 800),
                ObjectAnimator.ofFloat(recyclerView, "translationX", -recyclerView.getWidth(), 0f).setDuration(800))
            .start();
      }
    };
    recyclerView.setAdapter(new RecyclerAdapter(this,sid,name,pdf,ppt,note,qps,books));
    recyclerView.setLayoutManager(linearLayoutManager);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show();

      }
    });

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle =
        new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
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

  @SuppressWarnings("StatementWithEmptyBody") @Override public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    if (id == R.id.nav_camera) {
//      startActivity(new Intent(MainActivity.this,ProfileUpdation.class));
      } else if (id == R.id.nav_gallery) {
      startActivity(new Intent(MainActivity.this,FavActivity.class));
    } else if (id == R.id.nav_slideshow) {
      startActivity(new Intent(MainActivity.this,DowActivity.class));
    }
    else if (id == R.id.nav_share) {
      startActivity(new Intent(MainActivity.this,ProfileUpdation.class));
    } else if (id == R.id.nav_send) {
      SessionManager session = new SessionManager(getApplicationContext());
      session.setLogin(false);
      DBHelper dbHelper= new DBHelper(this);
      dbHelper.revUser();
      dbHelper.revSub();
      dbHelper.revDown();
      dbHelper.revFav();
      startActivity(new Intent(this,SplashActivity.class));
    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

  public class RecyclerAdapter
      extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private Context mContext;
    List<String> sid;
    List<String> name;
    List<String> pdf;
    List<String> ppt;
    List<String> note;
    List<String> qps;
    List<String> books;

    public RecyclerAdapter(Context context,List<String> sid,List<String> name,List<String> pdf,List<String> ppt,List<String> note,List<String> qps,List<String> books){
      this.mContext = context;
      this.sid = sid;
      this.name = name;
      this.pdf = pdf;
      this.ppt = ppt;
      this.note = note;
      this.qps = qps;
      this.books = books;
    }

    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
          mContext).inflate(R.layout.item_recycler, parent, false));
      return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.MyViewHolder holder, final int position) {
      if(position%2==1)
        holder.mView.setBackgroundColor(Color.parseColor("#FF4081"));
      holder.sub.setText(name.get(position));
      holder.pdf.setText("PDF: "+pdf.get(position));
      holder.ppt.setText("PPT: "+ppt.get(position));
      holder.notes.setText("Notes: "+note.get(position));
      holder.qps.setText("Question Papers: "+qps.get(position));
      holder.books.setText("Books: "+books.get(position));

      holder.root.setOnClickListener(new OnClickListener() {
        @Override public void onClick(View view) {
          final Intent intent =  new Intent(MainActivity.this, SubjectActivity.class);
          intent.putExtra("sub",sid.get(position));
          intent.putExtra("type","0");
          startActivity(intent);
          finish();
        }
      });

    }

    @Override public int getItemCount() {
      return sid.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

      LinearLayout root;
      View mView;
      TextView sub,ppt,pdf,notes,qps,books;

      public MyViewHolder(View view) {
        super(view);
        root = (LinearLayout) view.findViewById(R.id.rootView);
        mView = view.findViewById(R.id.stroke);
        sub = (TextView) view.findViewById(R.id.textSub);
        pdf = (TextView) view.findViewById(R.id.textPdf);
        ppt = (TextView) view.findViewById(R.id.textPpt);
        notes = (TextView) view.findViewById(R.id.textnotes);
        qps = (TextView) view.findViewById(R.id.textQps);
        books = (TextView) view.findViewById(R.id.textBooks);
      }
    }
  }




}
