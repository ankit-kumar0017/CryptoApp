package com.example.cryptoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {
    EditText key_text;
    EditText normal_text;
    EditText cipher_text;
    Button copy_normal;
    Button copy_cipher;
    Button encrypt;
    Button decrypt;
    Button delete_normal;
    Button delete_cipher;
    TextView char_count;
    TextView char_count2;
    Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window=this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#000000"));
        }
        c=MainActivity.this;
        normal_text= findViewById(R.id.normal_text);
        key_text=findViewById(R.id.key_text);
        cipher_text=findViewById(R.id.ciphertext);
        copy_normal=findViewById(R.id.copy_normal);
        copy_cipher=findViewById(R.id.copy_cipher);
        encrypt=findViewById(R.id.encrypt);
        decrypt=findViewById(R.id.decrypt);
        delete_cipher=findViewById(R.id.delete_cipher);
        delete_normal=findViewById(R.id.delete_normal);
        char_count=findViewById(R.id.char_count);
        char_count2=findViewById(R.id.char_count2);

        encrypt.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {
                if(normal_text.getText().toString().matches("")||key_text.getText().toString().matches(""))
                {
                    app.ToastMaker(c,"Enter Input Text and Key");
                }
                else if(key_text.getText().toString().length()!=8)
                {
                    app.ToastMaker(c,"Enter a key of 8 characters");
                }
                else
                {
                    cipher_text.setText(encrypt(normal_text.getText().toString(),c));
                }
            }
        });

        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cipher_text.getText().toString().matches("")||key_text.getText().toString().matches(""))
                {
                    app.ToastMaker(c,"Enter the Cipher Text");
                }
                else if(key_text.getText().toString().length()!=8)
                {
                    app.ToastMaker(c,"Enter the key of 8 characters");
                }
                else
                {
                    normal_text.setText(decrypt(cipher_text.getText().toString(),c));
                }
            }
        });

        copy_normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard= (ClipboardManager)  getSystemService(CLIPBOARD_SERVICE);
                ClipData clip=ClipData.newPlainText("normal text",normal_text.getText().toString());
                clipboard.setPrimaryClip(clip);
                app.ToastMaker(c,"Normal Text copied");
            }
        });

        copy_cipher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard= (ClipboardManager)  getSystemService(CLIPBOARD_SERVICE);
                ClipData clip=ClipData.newPlainText("cipher text",cipher_text.getText().toString());
                clipboard.setPrimaryClip(clip);
                app.ToastMaker(c,"Encrypted Text copied");
            }
        });

        delete_normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                normal_text.setText("");
            }
        });

        delete_cipher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cipher_text.setText("");
            }
        });

        normal_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                char_count.setText(normal_text.getText().toString().length()+"");
            }
        });

        cipher_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                char_count2.setText(cipher_text.getText().toString().length()+"");
            }
        });
    }

    public String encrypt(String value, Context c)
    {
        String encoded;
        try
        {
            byte[] cleartext= value.getBytes("UTF-8");
            SecretKeySpec key= new SecretKeySpec(key_text.getText().toString().getBytes(),"DES");
            Cipher cipher= Cipher.getInstance("DES/ECB/ZeroBytePadding");
            cipher.init(Cipher.ENCRYPT_MODE,key);
            encoded= Base64.encodeToString(cipher.doFinal(cleartext),Base64.DEFAULT);
            
        }
        catch(NoSuchAlgorithmException e)
        {
            e.printStackTrace();
            app.DialogMaker(c,"Encrypt Error","Error"+"\n"+e.getMessage());
            return "Encrypt Error";
        }
        catch(NoSuchPaddingException e)
        {
            e.printStackTrace();
            app.DialogMaker(c,"Encrypt Error","Error"+"\n"+e.getMessage());
            return "Encrypt Error";
        }
        catch(IllegalBlockSizeException e)
        {
            e.printStackTrace();
            app.DialogMaker(c,"Encrypt Error","Error"+"\n"+e.getMessage());
            return "Encrypt Error";
        }
        catch(BadPaddingException e)
        {
            e.printStackTrace();
            app.DialogMaker(c,"Encrypt Error","Error"+"\n"+e.getMessage());
            return "Encrypt Error";
        }
        catch(InvalidKeyException e)
        {
            e.printStackTrace();
            app.DialogMaker(c,"Encrypt Error","Error"+"\n"+e.getMessage());
            return "Encrypt Error";
        }
        catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
            app.DialogMaker(c,"Encrypt Error","Error"+"\n"+e.getMessage());
            return "Encrypt Error";
        }
        catch(Exception e)
        {
            e.printStackTrace();
            app.DialogMaker(c,"Encrypt Error","Error"+"\n"+e.getMessage());
            return "Encrypt Error";
        }
        return encoded;
    }
    
    public String decrypt(String value, Context c)
    {
        String coded;
        String result;
        if( value.startsWith("code=="))
        {
            coded=value.substring(6,value.length()).trim();
        }
        else
        {
            coded=value.trim();
        }
        try 
        {
            byte[] byteDecoded= Base64.decode(coded.getBytes("UTF-8"),Base64.DEFAULT);
            SecretKeySpec key= new SecretKeySpec(key_text.getText().toString().getBytes(),"DES");
            Cipher cipher= Cipher.getInstance("DES/ECB/ZeroBytePadding");
            cipher.init(Cipher.DECRYPT_MODE,key);
            byte[] textDecrypted= cipher.doFinal(byteDecoded);
            result = new String(textDecrypted);
        }
        catch(NoSuchAlgorithmException e)
        {
            e.printStackTrace();
            app.DialogMaker(c,"Decrypt Error","Error"+"\n"+e.getMessage());
            return "Decrypt Error";
        }
        catch(NoSuchPaddingException e)
        {
            e.printStackTrace();
            app.DialogMaker(c,"Decrypt Error","Error"+"\n"+e.getMessage());
            return "Decrypt Error";
        }
        catch(IllegalBlockSizeException e)
        {
            e.printStackTrace();
            app.DialogMaker(c,"Decrypt Error","Error"+"\n"+e.getMessage());
            return "Decrypt Error";
        }
        catch(BadPaddingException e)
        {
            e.printStackTrace();
            app.DialogMaker(c,"Decrypt Error","Error"+"\n"+e.getMessage());
            return "Decrypt Error";
        }
        catch(InvalidKeyException e)
        {
            e.printStackTrace();
            app.DialogMaker(c,"Decrypt Error","Error"+"\n"+e.getMessage());
            return "Decrypt Error";
        }
        catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
            app.DialogMaker(c,"Decrypt Error","Error"+"\n"+e.getMessage());
            return "Decrypt Error";
        }
        catch(Exception e)
        {
            e.printStackTrace();
            app.DialogMaker(c,"Decrypt Error","Error"+"\n"+e.getMessage());
            return "Decrypt Error";
        }
        return result;

    }

}