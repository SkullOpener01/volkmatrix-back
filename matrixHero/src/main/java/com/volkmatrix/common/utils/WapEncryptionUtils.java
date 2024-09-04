package com.volkmatrix.common.utils;

import com.volkmatrix.common.exception.ApiServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class WapEncryptionUtils {
  private static byte[] sharedvector = {1, 2, 3, 5, 7, 11, 13, 17};

  private static Logger LOGGER = LoggerFactory.getLogger(WapEncryptionUtils.class);

  public static void main(String args[]) {
    try {
//		  String encrpty=encryptPassword("sms@1234");

    } catch (Exception e) {

    }

  }

  public static String encrypt(String RawText, String key) {
    String EncText = "";
    byte[] keyArray = new byte[24];


    byte[] toEncryptArray = null;
    try {
      toEncryptArray = RawText.getBytes("UTF-8");
      MessageDigest m = MessageDigest.getInstance("MD5");
      byte[] temporaryKey = m.digest(key.getBytes("UTF-8"));
      if (temporaryKey.length < 24) {
        int index = 0;
        for (int i = temporaryKey.length; i < 24; i++) {
          keyArray[i] = temporaryKey[index];
        }
      }
      Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");
      c.init(1, new SecretKeySpec(keyArray, "DESede"), new IvParameterSpec(sharedvector));
      byte[] encrypted = c.doFinal(toEncryptArray);
      //  EncText = Base64.encodeBase64String(encrypted);
      EncText = new String(java.util.Base64.getMimeEncoder().encode(encrypted),
          StandardCharsets.UTF_8);
    } catch (NoSuchAlgorithmException | UnsupportedEncodingException | NoSuchPaddingException | InvalidKeyException |
             InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException NoEx) {
      JOptionPane.showMessageDialog(null, NoEx);
    }
    return EncText;
  }

  public static String decrypt(String EncText, String key) {
    String RawText = "";
    byte[] keyArray = new byte[24];


    try {
      MessageDigest m = MessageDigest.getInstance("MD5");
      byte[] temporaryKey = m.digest(key.getBytes("UTF-8"));
      if (temporaryKey.length < 24) {
        int index = 0;
        for (int i = temporaryKey.length; i < 24; i++) {
          keyArray[i] = temporaryKey[index];
        }
      }
      Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");
      c.init(2, new SecretKeySpec(keyArray, "DESede"), new IvParameterSpec(sharedvector));
      byte[] decrypted = c.doFinal(java.util.Base64.getMimeDecoder().decode(EncText));

      RawText = new String(decrypted, "UTF-8");
    } catch (NoSuchAlgorithmException | UnsupportedEncodingException | NoSuchPaddingException | InvalidKeyException |
             InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException NoEx) {
      JOptionPane.showMessageDialog(null, NoEx);
    }
    return RawText;
  }

  public static String getIdCode(String nm) {
    try {
      String res = "";
      char[] alph = nm.toCharArray();
      String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
      for (char c : alph) {
        int ind = alphabet.indexOf(c);
        int chr;

        if (ind < 25) {
          chr = c + ind + 1;
        } else {
          chr = 65;
        }
        if (chr > 90) {
          chr -= 90;
          chr += 65;
        }
        System.out.println((char) chr);
        res = res + (char) chr;
      }
      return res;
    } catch (Exception e) {
      throw new ApiServiceException(e.getMessage());
    }
  }

  public static String encryptPassword(String password)
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    try {
      SecureRandom rand = SecureRandom.getInstance("SHA1PRNG");
      byte[] salt = new byte[24];
      rand.nextBytes(salt);

      PBEKeySpec key = new PBEKeySpec(password.toCharArray(), salt, 1000, 512);
      SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//					  System.out.println("kkey///"+key);
      byte[] passBytes = keyFactory.generateSecret(key).getEncoded();

      String encrPass = new String(java.util.Base64.getMimeEncoder().encode(passBytes),
          StandardCharsets.UTF_8);
      // String encrPass = new Base64.getMimeEncoder().encode(passBytes);

      //    String saltString = new BASE64Encoder().encode(salt);

      String saltString = new String(java.util.Base64.getMimeEncoder().encode(salt),
          StandardCharsets.UTF_8);
      ;

      return encrPass + ":" + saltString;
    } catch (NoSuchAlgorithmException e) {

      throw new ApiServiceException(e.getMessage());
    }
  }

  public static boolean validatePassword(String password, String encrPassword) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {


    String[] tokens = encrPassword.split(":");

    //  byte[] storedPass = new BASE64Decoder().decodeBuffer(tokens[0]);

    byte[] storedPass = java.util.Base64.getMimeDecoder().decode(tokens[0]);

    //  byte[] salt = new BASE64Decoder().decodeBuffer(tokens[1]);

    byte[] salt = java.util.Base64.getMimeDecoder().decode(tokens[1]);

    PBEKeySpec key = new PBEKeySpec(password.toCharArray(), salt, 1000, storedPass.length * 8);

    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

    byte[] passBytes = keyFactory.generateSecret(key).getEncoded();

    int diff = storedPass.length ^ passBytes.length;

    for (int i = 0; (i < storedPass.length) && (i < passBytes.length); i++) {
      diff |= storedPass[i] ^ passBytes[i];
    }

    return diff == 0;
  }

  public static int varCount(String msg) {
    try {
      int i = 0;
      //Pattern p = Pattern.compile("<!" + "[1-9][0-9]{0,3}" + "!>");
//			Pattern p = Pattern.compile("<#" + "var" + "#>");
      Pattern p = Pattern.compile("\\{#" + "var" + "#}");

      Matcher m = p.matcher(msg);
      while (m.find()) {
        i++;
      }
      return i;
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      throw new ApiServiceException(e.getMessage());
    }
  }
}

