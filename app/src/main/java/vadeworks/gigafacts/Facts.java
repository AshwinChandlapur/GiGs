package vadeworks.gigafacts;

public class Facts {
    public String getFact() {
        return fact;
    }

    public void setFact(String fact) {
        this.fact = fact;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    String fact,imgUrl,uploadTime;

    public Facts(String fact, String imgUrl, String uploadTime) {
        this.fact = fact;
        this.imgUrl = imgUrl;
        this.uploadTime = uploadTime;
    }

}
