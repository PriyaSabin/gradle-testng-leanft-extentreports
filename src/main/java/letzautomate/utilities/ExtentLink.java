package letzautomate.utilities;

import com.aventstack.extentreports.markuputils.Markup;

public class ExtentLink implements Markup {

    private String urlLink;
    private String textLink;

    public void setLink(String textLink, String urlLink){
        this.urlLink = urlLink;
        this.textLink = textLink;
    }

    @Override
    public String getMarkup() {
        String lhs = "<a href=" + urlLink + ">";
        String rhs = "</a>";
        return lhs + textLink + rhs;
    }

    @Override
    public String toString() {
        return this.textLink;
    }


}
