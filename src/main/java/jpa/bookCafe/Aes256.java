package jpa.bookCafe;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

@Component
public class Aes256 {

    private String key;

    //string으로 된 key를 한번더 계산을 통해 꼬아줌 key가 유출되더라도 계산 알고리즘까지 알아야 쓸수있음.
    public Aes256() {
        String password = getKey();
        char[] array_pass = new char[password.length()];
        for (int i = 0; i < array_pass.length; i++)
        {
            if (i % 2 == 0 && i < 12)
            {
                array_pass[i] = password.charAt(array_pass.length-6+(i/2));
            }
            else
            {
                array_pass[i] = password.charAt(i);
            }
        }
        key = new String(array_pass);
    }

    public String getKey(){
        String password = KeyConst.KEY;
//        SimpleDateFormat format1 = new SimpleDateFormat("ddyyhhyy");
//        String year = format1.format(new Date());
        return password;
    }

    //복호화 하는 코드
    public String Decrypt(String text) throws Exception
    {

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        byte[] keyBytes= new byte[16];

        byte[] b= key.getBytes("UTF-8");

        int len= b.length;

        if (len > keyBytes.length) len = keyBytes.length;

        System.arraycopy(b, 0, keyBytes, 0, len);

        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

        IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);

        cipher.init(Cipher.DECRYPT_MODE,keySpec,ivSpec);



        Base64.Decoder decoder = Base64.getDecoder();
        byte [] results = cipher.doFinal(decoder.decode(text));

        return new String(results,"UTF-8");

    }


    //암호화 하는 코드
    public String Encrypt(String text) throws Exception

    {

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        byte[] keyBytes= new byte[16];

        byte[] b= key.getBytes("UTF-8");

        int len= b.length;

        if (len > keyBytes.length) len = keyBytes.length;

        System.arraycopy(b, 0, keyBytes, 0, len);

        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

        IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);

        cipher.init(Cipher.ENCRYPT_MODE,keySpec,ivSpec);



        byte[] results = cipher.doFinal(text.getBytes("UTF-8"));

        Base64.Encoder encoder = Base64.getEncoder();

        return  new String(encoder.encode(results));

    }

}
