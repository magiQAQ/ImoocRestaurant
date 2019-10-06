package com.magi.imoocrestaurant.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.magi.imoocrestaurant.R;
import com.magi.imoocrestaurant.bean.ProductModel;
import com.magi.imoocrestaurant.config.Config;
import com.magi.imoocrestaurant.ui.activity.ProductDetailActivity;
import com.magi.imoocrestaurant.utils.ToastUtils;

import java.io.File;
import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder> {

    private Context mContext;
    private List<ProductModel> mList;

    public ProductListAdapter(Context context, List<ProductModel> list){
        mContext = context;
        mList = list;
    }

    private OnProductListener listener;

    public interface OnProductListener{
        void onCountAdd(ProductModel model);
        void onCountSub(ProductModel model);
    }

    public void setOnProductListener(OnProductListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_product_list,parent,false);
        return new ProductListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListViewHolder holder, int position) {
        ProductModel model = mList.get(position);
        Glide.with(mContext)
                .load(Config.baseUrl+ File.separator + model.getIcon())
                .placeholder(R.drawable.pictures_no)
                .into(holder.icon_imageView);
        holder.product_name_textView.setText(model.getName());
        holder.count_textView.setText(String.valueOf(model.getCount()));
        holder.description_textView.setText(model.getLabel());
        holder.price_textView.setText(String.format("%s元/份", String.valueOf(model.getPrice())));
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    class ProductListViewHolder extends RecyclerView.ViewHolder{

        private ImageView icon_imageView, add_imageView, sub_imageView;
        private TextView product_name_textView, description_textView, price_textView, count_textView;

        public ProductListViewHolder(@NonNull View itemView) {
            super(itemView);
            icon_imageView = itemView.findViewById(R.id.icon_imageView);
            add_imageView = itemView.findViewById(R.id.add_imageView);
            sub_imageView = itemView.findViewById(R.id.sub_imageView);
            product_name_textView = itemView.findViewById(R.id.product_name_textView);
            description_textView = itemView.findViewById(R.id.description_textView);
            price_textView = itemView.findViewById(R.id.price_textView);
            count_textView = itemView.findViewById(R.id.count_textView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ProductDetailActivity.class);
                    intent.putExtra(ProductDetailActivity.KEY_PRODUCT,mList.get(getLayoutPosition()));
                    mContext.startActivity(intent);
                }
            });

            add_imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ProductModel model = mList.get(getLayoutPosition());
                    model.setCount(model.getCount()+1);
                    count_textView.setText(String.valueOf(model.getCount()));
                    if (listener!=null) {
                        listener.onCountAdd(model);
                    }
                }
            });

            sub_imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ProductModel model = mList.get(getLayoutPosition());
                    if (model.getCount()<=0){
                        ToastUtils.showToast("已经是0了,你想干嘛~");
                        return;
                    }
                    model.setCount(model.getCount()-1);
                    count_textView.setText(String.valueOf(model.getCount()));
                    if (listener!=null) {
                        listener.onCountSub(model);
                    }
                }
            });
        }
    }
}
