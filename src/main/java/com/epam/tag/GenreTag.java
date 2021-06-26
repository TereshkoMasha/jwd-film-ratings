package com.epam.tag;

import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Locale;

public class GenreTag extends SimpleTagSupport {
    private String genre;

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public void doTag() throws IOException {
        getJspContext().getOut().write(genre.charAt(0) + genre.substring(1).toLowerCase(Locale.ROOT));
    }
}
