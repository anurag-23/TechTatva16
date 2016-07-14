package in.techtatva.techtatva.fragments;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.techtatva.techtatva.R;


public class CategoryInfoDialogFragment extends DialogFragment{

    public CategoryInfoDialogFragment(){

    }

    public static CategoryInfoDialogFragment newInstance() {
        CategoryInfoDialogFragment fragment = new CategoryInfoDialogFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_info_dialog, container, false);

        TextView categoryName = (TextView)view.findViewById(R.id.category_info_name_text_view);
        TextView categoryDescription = (TextView)view.findViewById(R.id.category_info_description_text_view);

        categoryName.setText(getArguments().getString("Category Name"));
        categoryDescription.setText(getArguments().getString("Description"));

        return view;
    }


}
