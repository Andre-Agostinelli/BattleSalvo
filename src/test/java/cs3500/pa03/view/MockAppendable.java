package cs3500.pa03.view;

import java.io.IOException;

public class MockAppendable implements Appendable{

  private void throwInOut() throws IOException {
    throw new IOException("Mock throwing an error");
  }

  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throwInOut();
    return null;
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throwInOut();
    return null;
  }

  @Override
  public Appendable append(char c) throws IOException {
    throwInOut();
    return null;
  }
}
