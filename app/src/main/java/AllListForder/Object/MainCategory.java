package AllListForder.Object;

import java.util.List;

public class MainCategory {
    private int idMainCate;
    private String codeIdCategory, nameCategory, urlImgMainCategory;
    private List<SideCategory> sideCategoryList;

    public MainCategory(int idMainCate, String codeIdCategory, String nameCategory, String urlImgMainCategory, List<SideCategory> sideCategoryList) {
        this.idMainCate = idMainCate;
        this.codeIdCategory = codeIdCategory;
        this.nameCategory = nameCategory;
        this.urlImgMainCategory = urlImgMainCategory;
        this.sideCategoryList = sideCategoryList;
    }

    public int getIdMainCate() {
        return idMainCate;
    }

    public void setIdMainCate(int idMainCate) {
        this.idMainCate = idMainCate;
    }

    public String getCodeIdCategory() {
        return codeIdCategory;
    }

    public void setCodeIdCategory(String codeIdCategory) {
        this.codeIdCategory = codeIdCategory;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public String getUrlImgMainCategory() {
        return urlImgMainCategory;
    }

    public void setUrlImgMainCategory(String urlImgMainCategory) {
        this.urlImgMainCategory = urlImgMainCategory;
    }

    public List<SideCategory> getSideCategoryList() {
        return sideCategoryList;
    }

    public void setSideCategoryList(List<SideCategory> sideCategoryList) {
        this.sideCategoryList = sideCategoryList;
    }
}
