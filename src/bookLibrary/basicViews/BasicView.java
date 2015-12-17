package bookLibrary.basicViews;

import javax.faces.bean.ManagedBean;

@ManagedBean (name = "basicView")
public class BasicView {
     
    private String text;
 
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
