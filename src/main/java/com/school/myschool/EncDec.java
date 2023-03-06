package com.school.myschool;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.crypto.Cipher;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

public class EncDec {


    String privateKey = "<RSAKeyValue><Modulus>xTSiS4+I/x9awUXcF66Ffw7tracsQfGCn6g6k/hGkLquHYMFTCYk4mOB5NwLwqczwvl8HkQfDShGcvrm47XHKUzA8iadWdA5n4toBECzRxiCWCHm1KEg59LUD3fxTG5ogGiNxDj9wSguCIzFdUxBYq5ot2J4iLgGu0qShml5vwk=</Modulus><Exponent>AQAB</Exponent><P>+lXMCEwIN/7+eMpBrq87kQppxu3jJBTwztGTfXNaPUTx+A6uqRwug5oHBbSpYXKNDNCBzVm/0VxB3bo4FJx+ZQ==</P><Q>yasOGaJaE9xlF9T2xRuKeG9ZxCiyjhYaYB/mbtL+SIbtkRLi/AxaU4g2Il/UxhxhSXArKxIzV28zktispPJx1Q==</Q><DP>ueRgQIEFUV+fY979a1RgrVHIPpqEI1URhOMH3Q59oiXCcOumM5njyIHmWQxRAzXnG+7xlKXi1PrnRll0L4oOKQ==</DP><DQ>dfEMNgG1HJhwpxdtmqkYuoakwQvsIRzcIAuIAJh1DoWaupWJGk8/JEstHb1d+t7uJrzrAi2KyT/HscH2diE0YQ==</DQ><InverseQ>YoYF9PF6FiC0YngVeaC/eqt/ea8wMYNN3YO1LuzWpcy2exPRj2U0ZbWMvHXMUb4ea2qmhZGx1QlK4ULAuWKpXQ==</InverseQ><D>g1WAWI4pEK9TA7CA2Yyy/2FzzNiu0uQCuE2TZYRNiomo96KQXpxwqAzZLw+VDXfJMypwDMAVZe/SqzSJnFEtZxjdxaEo3VLcZ1mnbIL0vS7D6iFeYutF9kF231165qGd3k2tgymNMMpY7oYKjS11Y6JqWDU0WE5hjS2X35iG6mE=</D></RSAKeyValue>";

    String publicKey = "<RSAKeyValue><Modulus>xTSiS4+I/x9awUXcF66Ffw7tracsQfGCn6g6k/hGkLquHYMFTCYk4mOB5NwLwqczwvl8HkQfDShGcvrm47XHKUzA8iadWdA5n4toBECzRxiCWCHm1KEg59LUD3fxTG5ogGiNxDj9wSguCIzFdUxBYq5ot2J4iLgGu0qShml5vwk=</Modulus><Exponent>AQAB</Exponent></RSAKeyValue>";


    public static String isEncrypt="N";

    public static void main(String[] args) throws JsonProcessingException {

        ObjectMapper mapper=new ObjectMapper();
        ObjectNode headers=mapper.createObjectNode();
        ObjectNode customerInfo=mapper.createObjectNode();
        ObjectNode deviceInfo=mapper.createObjectNode();
        ObjectNode transactionInfo=mapper.createObjectNode();
        ObjectNode attributes=mapper.createObjectNode();
        ObjectNode mainRequest=mapper.createObjectNode();

        String mod = "xTSiS4+I/x9awUXcF66Ffw7tracsQfGCn6g6k/hGkLquHYMFTCYk4mOB5NwLwqczwvl8HkQfDShGcvrm47XHKUzA8iadWdA5n4toBECzRxiCWCHm1KEg59LUD3fxTG5ogGiNxDj9wSguCIzFdUxBYq5ot2J4iLgGu0qShml5vwk=";
        String exp = "AQAB";


        isEncrypt="N";


        headers.put("bankCode", EncryptString("159747", mod, exp));
        headers.put("channel", EncryptString("PSP", mod, exp));
        customerInfo.put("customerId", EncryptString("26441087", mod, exp));
        customerInfo.put("mobileNo", EncryptString("9671008646", mod, exp));
        customerInfo.put("upitoken", EncryptString("28b25c78-042a-4d30-a70d-6f7cc8846581", mod, exp));
        customerInfo.put("profileEncrypt", EncryptString("26441087", mod, exp));
        customerInfo.put("sessionId", "Fx7MJ6HNfe+LeEOLm4RtuAxGlhLfGsjrb6T//nlCrGSDdOjG25RvDbnhbw93zzQkLBSJ6PkMKXDpCy1LQa7cJnNbYxHSYWj49xkmnPkFVFZRKsyhJ2YF6kUgjESMq7qcIgsUY3pHdHe0/Sfo6WJ/OAhn4ZEsmQYzAVGn2ZNmNYM=");
        deviceInfo.put("geocode", EncryptString("9.0911,72.9208", mod, exp));
        deviceInfo.put("telecom","YEieazSq0b3JYUVU9a5Ux0o2GDA+W5Sp6NtVbrhyI3MqJPaRVZrFlehkS3V5k/7hCFu7isblobUSCvYVMDPVai3OlI4EmdLkWWpb3xDnhNd5wviV/Kv7ES0XA9i742N+EE4+sYT6ni6amFVS2CX7q2ScbhUdm44sMvU/hNXTJ+Y=");
        deviceInfo.put("location", EncryptString("Kharadi, pune", mod, exp));
        deviceInfo.put("ip", EncryptString("182.79.100.178", mod, exp));
        deviceInfo.put("type", EncryptString("MOB", mod, exp));
        deviceInfo.put("id", EncryptString("b86941e47ad65b40", mod, exp));
        deviceInfo.put("os", EncryptString("ANDROID - R", mod, exp));
        deviceInfo.put("app", EncryptString("com.EuroNet.upi", mod, exp));
        deviceInfo.put("capability", EncryptString("0", mod, exp));

        headers.set("customerInfo",customerInfo);
        headers.set("deviceInfo",deviceInfo);


        transactionInfo.put("transactionid", "212332838223");
        transactionInfo.put("transactiondatetime", "2019-09-21 14:22:22 002");
        attributes.put("profileId","26441087");
        attributes.put("vpa","9671008646@aubank");
        transactionInfo.set("attributes", attributes);
        mainRequest.set("transactionInfo",transactionInfo);
        mainRequest.set("header",headers);


        System.out.println(mapper.writeValueAsString(mainRequest));

        System.out.println("=======================================================");

        isEncrypt="Y";


        headers.put("bankCode", EncryptString("159765", mod, exp)); // environment variable
        headers.put("channel", EncryptString("PSP", mod, exp)); // environment variable
        customerInfo.put("customerId", EncryptString("26441087", mod, exp)); //request body cif id
        customerInfo.put("mobileNo", EncryptString("9671008646", mod, exp)); // req. id customer mobile no.
        customerInfo.put("upitoken", EncryptString("28b25c78-042a-4d30-a70d-6f7cc8846581", mod, exp)); // req. body logic
        customerInfo.put("profileEncrypt", EncryptString("26441087", mod, exp)); // cutomer id
        customerInfo.put("sessionId", "Fx7MJ6HNfe+LeEOLm4RtuAxGlhLfGsjrb6T//nlCrGSDdOjG25RvDbnhbw93zzQkLBSJ6PkMKXDpCy1LQa7cJnNbYxHSYWj49xkmnPkFVFZRKsyhJ2YF6kUgjESMq7qcIgsUY3pHdHe0/Sfo6WJ/OAhn4ZEsmQYzAVGn2ZNmNYM=");
        deviceInfo.put("geocode", EncryptString("9.0911,72.9208", mod, exp));
        deviceInfo.put("location", EncryptString("Kharadi, pune", mod, exp));
        deviceInfo.put("telecom","YEieazSq0b3JYUVU9a5Ux0o2GDA+W5Sp6NtVbrhyI3MqJPaRVZrFlehkS3V5k/7hCFu7isblobUSCvYVMDPVai3OlI4EmdLkWWpb3xDnhNd5wviV/Kv7ES0XA9i742N+EE4+sYT6ni6amFVS2CX7q2ScbhUdm44sMvU/hNXTJ+Y=");
        deviceInfo.put("ip", EncryptString("182.79.100.178", mod, exp));
        deviceInfo.put("type", EncryptString("MOB", mod, exp));
        deviceInfo.put("id", EncryptString("b86941e47ad65b40", mod, exp));
        deviceInfo.put("os", EncryptString("ANDROID - R", mod, exp));
        deviceInfo.put("app", EncryptString("com.EuroNet.upi", mod, exp));
        deviceInfo.put("capability", EncryptString("0", mod, exp));

        headers.set("customerInfo",customerInfo);
        headers.set("deviceInfo",deviceInfo);


        transactionInfo.put("transactionid", "2SEP32838223873287");
        transactionInfo.put("transactiondatetime", "2019-09-21 14:12:22 002");
        attributes.put("profileId","26441087");
        attributes.put("vpa","9671008646@aubank");
        transactionInfo.set("attributes", attributes);
        mainRequest.set("transactionInfo",transactionInfo);
        mainRequest.set("header",headers);


        System.out.println(mapper.writeValueAsString(mainRequest));


    }


    public static String EncryptString(String input, String mod, String exp) {

        if(isEncrypt.equalsIgnoreCase("N")){
            return input;
        }

        try {
            BigInteger modules = new BigInteger(1, Base64.getDecoder().decode(mod.getBytes("UTF-8")));
            BigInteger exponent = new BigInteger(1, Base64.getDecoder().decode(exp.getBytes("UTF-8")));
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
            PublicKey pubKey = KeyFactory.getInstance("RSA")
                    .generatePublic(new RSAPublicKeySpec(modules, exponent));
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(input.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void getPlainRequest(){

    }


}
