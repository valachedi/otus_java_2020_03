package hw08;

import com.google.gson.Gson;
import hw08.components.MyGson;
import hw08.models.Book;

public class Main {
  public static void main(String ...args) {
    Gson gson = new Gson();
    Book book = new Book(364);
    MyGson myGson = new MyGson();

    book
      .setTitle("Best book about the world")
      .setAuthorName("Sofia")
      .setMarkedPageNumbers(new int[]{25,34})
      .addThanksTo("Mike N.");

    String json = gson.toJson(book);
    String myJson = myGson.toJson(book);
    Book bookDeserialized = gson.fromJson(json, Book.class);

    System.out.println(book.equals(bookDeserialized));
  }
}
