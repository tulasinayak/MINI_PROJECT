package com.project.jfe;

public class model {
    private String imageUri;
    public model(){

    }
    public model(String imageUri){
          this.imageUri = imageUri;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
