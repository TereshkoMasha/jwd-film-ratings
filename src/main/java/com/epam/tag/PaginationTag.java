package com.epam.tag;

import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * The tag is used to form pagination on the page
 * the default number of items per page is 3
 * when displaying movies - 1 item is displayed separately.
 */

public class PaginationTag extends SimpleTagSupport {

    private Integer reviewOnProfileListSize;

    private Integer movieListSize;

    private Integer movieReviewListSize;

    public void setReviewOnProfileListSize(int reviewOnProfileListSize) {
        this.reviewOnProfileListSize = reviewOnProfileListSize;
    }

    public void setMovieListSize(Integer movieListSize) {
        this.movieListSize = movieListSize;
    }

    public void setMovieReviewListSize(Integer movieReviewListSize) {
        this.movieReviewListSize = movieReviewListSize;
    }

    @Override
    public void doTag() {
        if (reviewOnProfileListSize != null) {
            try {
                int pages = reviewOnProfileListSize / 3;
                if (pages * 3 + 1 == reviewOnProfileListSize) {
                    pages++;
                }
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 1; i < pages + 1; i++) {
                    int k = (i - 1) * 3;
                    stringBuilder.append("<a href=\"/jwdFilms_war_exploded/controller?command=view-user-profile&page=")
                            .append(k)
                            .append("\">")
                            .append(i)
                            .append("</a>")
                            .append("\n");
                }

                getJspContext().getOut().write(stringBuilder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (movieReviewListSize != null) {
            try {
                int pages = movieReviewListSize / 3;
                if (pages * 3 + 1 <= movieReviewListSize) {
                    pages++;
                }
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 1; i < pages + 1; i++) {
                    int k = (i - 1) * 3;
                    stringBuilder.append("<a href=\"/jwdFilms_war_exploded/controller?command=movie-info&page=")
                            .append(k)
                            .append("\">")
                            .append(i)
                            .append("</a>")
                            .append("\n");
                }

                getJspContext().getOut().write(stringBuilder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (movieListSize != null) {
            try {
                int pages = (movieListSize - 1) / 3;
                if (pages * 3 + 1 <= (movieListSize - 1)) {
                    pages++;
                }
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 1; i < pages + 1; i++) {
                    int k = (i - 1) * 3;
                    stringBuilder.append("<a href=\"/jwdFilms_war_exploded/controller?command=main&page=")
                            .append(k)
                            .append("\">")
                            .append(i)
                            .append("</a>")
                            .append("\n");
                }

                getJspContext().getOut().write(stringBuilder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
