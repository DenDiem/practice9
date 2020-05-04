package com.example.demo.Service;

import com.example.demo.Entity.BookEntity;
import com.example.demo.Reprository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    //private final EntityManager entityManager;
    @Transactional
    public BookEntity createBook(String title, String isbn, String author) {
        BookEntity book = new BookEntity();
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setAuthor(author);

        //return entityManager.merge(book);
        return bookRepository.saveAndFlush(book);

    }
    @Transactional
    public List<BookEntity> findAllBooks() {
        //return entityManager.createQuery("FROM  BookEntity ", BookEntity.class).getResultList();
        return bookRepository.findAll();
    }
    @Transactional
    public BookEntity findByID(int id) {
        //return entityManager.createNamedQuery(BookEntity.FIND_BY_ID, BookEntity.class).setParameter("id", id).getSingleResult();
        Optional<BookEntity> optionalUser = bookRepository.findById(id);
        return optionalUser.orElse(null);
    }
   /* @Transactional
    public List<BookEntity> serchByLetter(String letter) {
        List<BookEntity> temp = findAllBooks();
        List<BookEntity> result = new ArrayList<>();
        for (BookEntity bookEntity:temp) {
            if (bookEntity.getAuthor().contains(letter)||bookEntity.getIsbn().contains(letter)||bookEntity.getTitle().contains(letter))
                result.add(bookEntity);
        }
        return result;
    }*/
    public List<BookEntity> findAllWhereIsbnLikeOrTitleLike(String isbn, String title) {
        return  bookRepository.findAllByIsbnLikeOrTitleLike('%' + isbn + '%', '%' + title + '%');
    }
}