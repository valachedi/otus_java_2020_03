package hw08.models;

import java.util.ArrayList;
import java.util.Objects;

public class Book {
  private final static char DEFAULT_CATEGORY = 'c';

  private int numberOfPages;
  private String authorName = "anonymous";
  private String title = "unnamed";
  private ArrayList thanksTo = new ArrayList(0);
  private int[] markedPageNumbers;
  private char categoryCodeLetter = Book.DEFAULT_CATEGORY;
  private byte checksumByte = 0b110;

  public Book(int numberOfPages) {
    this.numberOfPages = numberOfPages;
  }

  public Book setTitle(String title) {
    this.title = title;
    return this;
  }

  public Book setAuthorName(String authorName) {
    this.authorName = authorName;
    return this;
  }

  public Book setMarkedPageNumbers(int[] markedPageNumbers) {
    this.markedPageNumbers = markedPageNumbers;
    return this;
  }

  public Book addThanksTo(String name) {
    thanksTo.add(name);
    return this;
  }

  @Override
  public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Book that = (Book) o;

      return numberOfPages == that.numberOfPages &&
              Objects.equals(authorName, that.authorName) &&
              Objects.equals(title, that.title);
  }

  @Override
  public int hashCode() {
    return Objects.hash(numberOfPages, authorName, title);
  }
}
