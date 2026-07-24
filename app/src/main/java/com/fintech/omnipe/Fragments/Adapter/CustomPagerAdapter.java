package com.fintech.omnipe.Fragments.Adapter;

import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.fintech.omnipe.Api.Object.RefferalImage;
import com.fintech.omnipe.R;

import java.util.List;


public class CustomPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    private List<RefferalImage> ImageList;
    private int type;
    private String appUrl;

    public CustomPagerAdapter(List<RefferalImage> ImageList, Context context,int type) {
        this.ImageList = ImageList;
        this.mContext = context;
        this.type=type;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return ImageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

        ImageView imageView = itemView.findViewById(R.id.imageView);
        if(type==1){
           appUrl=ImageList.get(position).getResourceUrl();
        }else{
            appUrl=ImageList.get(position).getSiteResourceUrl();
        }
        Glide.with(mContext)
                .load(appUrl)
                .into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}