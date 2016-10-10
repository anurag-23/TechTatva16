package in.techtatva.techtatva.models;

/**
 * Created by Naman on 10/10/2016.
 */
public class RatingsModel {
    private String categoryID;
    private String categoryName;
    private String rating ;

    public RatingsModel() {
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
