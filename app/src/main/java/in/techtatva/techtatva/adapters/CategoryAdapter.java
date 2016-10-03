package in.techtatva.techtatva.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.techtatva.techtatva.R;
import in.techtatva.techtatva.activities.SelectedCategoryActivity;
import in.techtatva.techtatva.fragments.CategoryInfoDialogFragment;
import in.techtatva.techtatva.models.categories.CategoryModel;
import in.techtatva.techtatva.resources.IconCollection;

/**
 * Created by AYUSH on 14-06-2016.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private FragmentManager fm;
    private List<CategoryModel> categories = new ArrayList<>();
    private Context context;

    public CategoryAdapter(FragmentManager fm, List<CategoryModel> categories, Context context){
        this.fm = fm;
        setCategories(categories);
        this.context = context;
    }

    private void setCategories(List<CategoryModel> categories){
        this.categories = categories;
        notifyDataSetChanged();
    }

    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.ViewHolder holder, int position) {
        CategoryModel category = categories.get(position);
        holder.categoryName.setText(category.getCategoryName());

        IconCollection icons = new IconCollection();
        holder.categoryLogo.setImageResource(icons.getIconResource(context, category.getCategoryName()));

        holder.categoryDescription = category.getCategoryDescription();
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView categoryLogo;
        TextView categoryName;
        //ImageButton categoryInfo;
        String categoryDescription;

        public ViewHolder(View itemView) {
            super(itemView);

            categoryLogo=(ImageView)itemView.findViewById(R.id.category_logo_image_view);
            categoryName=(TextView)itemView.findViewById(R.id.category_name_text_view);
            itemView.setOnClickListener(this);
            /*categoryInfo=(ImageButton)itemView.findViewById(R.id.category_info_imagebutton);
            categoryInfo.setOnClickListener(this);*/
        }


        @Override
        public void onClick(View view) {

            if (view.getId() == itemView.getId()){
                Intent intent = new Intent(context, SelectedCategoryActivity.class);
                intent.putExtra("Category Name", categoryName.getText().toString());
                intent.putExtra("Category ID", categories.get(getLayoutPosition()).getCategoryID());
                intent.putExtra("Description", categoryDescription);
                context.startActivity(intent);
            }


        }
    }
}

