package in.techtatva.techtatva.models.categories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by AYUSH on 14-06-2016.
 */
public class CategoryModel extends RealmObject{

    @SerializedName("categoryName")
    @Expose
    private String categoryName;

    @SerializedName("description")
    @Expose
    private String categoryDescription;

    @SerializedName("categoryType")
    @Expose
    private String categoryType;

    @SerializedName("categoryID")
    @Expose
    private String categoryID;

    public CategoryModel(){

    }
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }


    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }
}
