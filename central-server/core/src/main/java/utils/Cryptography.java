package utils;

import com.password4j.BcryptFunction;
import com.password4j.Password;
import com.password4j.types.Bcrypt;

public class Cryptography {

  private static final String SECRET = "9Yq$_OZ%iPeP";
  private static final int ROUNDS = 12;

  public static String getHash(String text) {
    BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.B, ROUNDS);
    return Password.hash(text).addPepper(SECRET).with(bcrypt).getResult();
  }

  public static Boolean validateHash(String text, String hash) {
    BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.B, ROUNDS);
    return Password.check(text, hash).addPepper(SECRET).with(bcrypt);
  }
}
