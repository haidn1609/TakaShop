package AllListForder.Object;

public class SideCategory {
    private int idSideCategory, idMainCategory;
    private String codeId, sideCategoryName, sideCategoryAvatar;

    public SideCategory(int idSideCategory, int idMainCategory, String codeId, String sideCategoryName, String sideCategoryAvatar) {
        this.idSideCategory = idSideCategory;
        this.idMainCategory = idMainCategory;
        this.codeId = codeId;
        this.sideCategoryName = sideCategoryName;
        this.sideCategoryAvatar = sideCategoryAvatar;
    }

    public int getIdSideCategory() {
        return idSideCategory;
    }

    public void setIdSideCategory(int idSideCategory) {
        this.idSideCategory = idSideCategory;
    }

    public int getIdMainCategory() {
        return idMainCategory;
    }

    public void setIdMainCategory(int idMainCategory) {
        this.idMainCategory = idMainCategory;
    }

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public String getSideCategoryName() {
        return sideCategoryName;
    }

    public void setSideCategoryName(String sideCategoryName) {
        this.sideCategoryName = sideCategoryName;
    }

    public String getSideCategoryAvatar() {
        return sideCategoryAvatar;
    }

    public void setSideCategoryAvatar(String sideCategoryAvatar) {
        this.sideCategoryAvatar = sideCategoryAvatar;
    }
}
