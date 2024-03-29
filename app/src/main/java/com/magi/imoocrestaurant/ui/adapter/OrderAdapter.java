package com.magi.imoocrestaurant.ui.adapter;

import android.annotation.SuppressLint;
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
import com.magi.imoocrestaurant.bean.Order;
import com.magi.imoocrestaurant.config.Config;
import com.magi.imoocrestaurant.ui.activity.OrderDetailActivity;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderItemViewHolder> {

    private List<Order> mList;
    private Context mContext;

    public OrderAdapter(Context mContext, List<Order> mList) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_order_list,parent,false);
        return new OrderItemViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder, int position) {
        Order order = mList.get(position);
        Glide.with(mContext)
                .load(Config.baseUrl+order.getRestaurant().getIcon())
                .placeholder(R.drawable.pictures_no)
                .into(holder.icon_imageView);
        holder.restaurant_name_textView.setText(order.getRestaurant().getName());
        holder.price_textView.setText("共消费"+order.getPrice()+"元");
        if (order.getPs().size()>0) {
            holder.description_textView
                    .setText(order.getPs().get(0).product.getName()+"等"+order.getCount()+"件商品");
        } else {
            holder.description_textView.setText("无消费");
        }
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    public class OrderItemViewHolder extends RecyclerView.ViewHolder{

        private ImageView icon_imageView;
        private TextView restaurant_name_textView, description_textView, price_textView;


        public OrderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, OrderDetailActivity.class);
                    intent.putExtra(OrderDetailActivity.KEY_ORDER,mList.get(getLayoutPosition()));
                    mContext.startActivity(intent);
                }
            });

            icon_imageView = itemView.findViewById(R.id.icon_imageView);
            restaurant_name_textView = itemView.findViewById(R.id.restaurant_name_textView);
            description_textView = itemView.findViewById(R.id.description_textView);
            price_textView = itemView.findViewById(R.id.price_textView);
        }
    }
}
