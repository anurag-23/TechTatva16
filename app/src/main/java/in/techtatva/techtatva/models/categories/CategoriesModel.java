package in.techtatva.techtatva.models.categories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anurag on 29/6/16.
 */
public class CategoriesModel {

    @SerializedName("data")
    @Expose
    private List<CategoryModel> categoriesList = new ArrayList<>();

    public CategoriesModel() {
    }

    public List<CategoryModel> getCategoriesList() {
        return categoriesList;
    }

    public void setCategoriesList(List<CategoryModel> categoriesList) {
        this.categoriesList = categoriesList;
    }
}
