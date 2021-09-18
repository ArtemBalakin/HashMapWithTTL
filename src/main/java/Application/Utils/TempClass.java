package Application.Utils;

import java.io.IOException;

/**
 * Пример использования библиотеки
 */
public class TempClass {
    public static void main(String[] args) throws IOException {
        FakeClient fakeClient=new FakeClient("http://localhost",8080);
        fakeClient.postRequest("str","str",100000);
        System.out.println(fakeClient.getRequest("str"));
        fakeClient.deleteRequest("str");
        System.out.println(fakeClient.getRequest("str"));
        System.out.println(fakeClient.saveRequest());
        System.out.println(fakeClient.uploadRequest());
    }
}
