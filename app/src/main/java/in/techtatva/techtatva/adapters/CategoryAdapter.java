package in.techtatva.techtatva.adapters;

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
import in.techtatva.techtatva.fragments.CategoryInfoDialogFragment;
import in.techtatva.techtatva.models.categories.CategoryModel;
import io.realm.RealmResults;

/**
 * Created by AYUSH on 14-06-2016.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private FragmentManager fm;
    private List<CategoryModel> categories = new ArrayList<>();

    public CategoryAdapter(FragmentManager fm, List<CategoryModel> categories){
        this.fm = fm;
        setCategories(categories);
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
        holder.categoryDescription = category.getCategoryDescription();
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView categoryLogo;
        TextView categoryName;
        ImageButton categoryInfo;
        String categoryDescription;

        public ViewHolder(View itemView) {
            super(itemView);

            categoryLogo=(ImageView)itemView.findViewById(R.id.category_logo_image_view);
            categoryName=(TextView)itemView.findViewById(R.id.category_name_text_view);
            categoryInfo=(ImageButton)itemView.findViewById(R.id.category_info_imagebutton);
            categoryInfo.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {

            if (view.getId() == categoryInfo.getId()){
                DialogFragment fragment = CategoryInfoDialogFragment.newInstance();
                fragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);

                Bundle bundle = new Bundle();
                bundle.putString("Category Name", categoryName.getText().toString());
                bundle.putString("Description", categoryDescription);

                fragment.setArguments(bundle);
                fragment.show(fm, "fragment_category_info_dialog");
            }

        }
    }
}

