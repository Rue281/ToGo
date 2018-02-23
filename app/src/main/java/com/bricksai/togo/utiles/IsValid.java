package com.bricksai.togo.utiles;

import android.util.Log;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Remonda on 5/9/2017.
 */

public class IsValid
{
    public boolean isPhoneValid (String phone)
    {
        String[] arrPhone = phone.split("");
        boolean valid = true;
        Log.e("key", phone);
        if (arrPhone.length == 12)
        {
            if (arrPhone[1] == "0" && arrPhone[2] == "1")
            {
                if (arrPhone[3] == "0" || arrPhone[3] == "1" || arrPhone[3] == "2")
                {
                    valid = true;
                }
            }
        }else
        {
            valid = false;
        }
        return valid;
    }

    private boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        } else {
        }
        return isValid;
    }
}
