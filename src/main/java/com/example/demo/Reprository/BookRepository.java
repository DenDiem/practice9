package com.example.demo.Reprository;

import com.example.demo.Entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Integer>
{
   // List<BookEntity> findAllWhereIsbn(String contains);
    //Optional<BookEntity> findByIsbn(String isbn);
   // boolean existsByIsbn(String isbn);
    //List<BookEntity> findAllWhereIsbnLikeOrTitleLike(String isbn,String title);
   List<BookEntity> findAllByIsbnLikeOrTitleLike(String isbn, String title);
}