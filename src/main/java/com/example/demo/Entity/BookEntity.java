package com.example.demo.Entity;


import javax.persistence.*;

import lombok.*;
@Entity
/*@NamedQueries(
        {
                @NamedQuery(query = "SELECT u FROM BookEntity u WHERE u.id = :id", name = BookEntity.FIND_BY_ID)
        })*/
@Table(name = "books")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BookEntity
{
    //public static final String FIND_BY_ID = "BookEntity.FIND_BY_ID";
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "isbn")
    private String isbn;
    @Column(name = "author")
    private String author;


}