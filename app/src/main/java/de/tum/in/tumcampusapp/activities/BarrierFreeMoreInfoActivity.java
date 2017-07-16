package de.tum.in.tumcampusapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import de.tum.in.tumcampusapp.R;
import de.tum.in.tumcampusapp.activities.generic.ActivityForLoadingInBackground;
import de.tum.in.tumcampusapp.adapters.BarrierfreeMoreInfoAdapter;
import de.tum.in.tumcampusapp.models.barrierfree.BarrierfreeMoreInfo;
import de.tum.in.tumcampusapp.tumonline.TUMBarrierFreeRequest;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class BarrierFreeMoreInfoActivity
        extends ActivityForLoadingInBackground<Void, List<BarrierfreeMoreInfo>>
        implements AdapterView.OnItemClickListener{

    public StickyListHeadersListView listview;
    public List<BarrierfreeMoreInfo> infos;
    public BarrierfreeMoreInfoAdapter adapter;
    private TUMBarrierFreeRequest request;

    public BarrierFreeMoreInfoActivity(){
        super(R.layout.activity_barrier_free_list_info);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        listview = (StickyListHeadersListView) findViewById(R.id.activity_barrier_info_list_view);

        request = new TUMBarrierFreeRequest(this);
        startLoading();
    }

    @Override
    protected void onLoadFinished(List<BarrierfreeMoreInfo> result) {
        infos = result;
        adapter = new BarrierfreeMoreInfoAdapter(this, infos);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
    }

    @Override
    protected List<BarrierfreeMoreInfo> onLoadInBackground(Void... arg) {
        return request.fetchMoreInfoList();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String url = infos.get(position).getUrl();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);

    }
}
