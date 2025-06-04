package org.example.assignment_java3.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class News {
    private String id;
    private String title;
    private String content;
    private String image;
    private Date postDate;
    private String author;
    private int viewCount;
    private String categoryId;
    private boolean home;
}
