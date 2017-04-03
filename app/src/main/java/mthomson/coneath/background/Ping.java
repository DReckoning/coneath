package mthomson.coneath.background;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ping {
    public static double get() {
        Runtime runtime = Runtime.getRuntime();
        try
        {
            Process  process = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int mExitValue = process.waitFor();
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));

            if(mExitValue==0){
                Pattern pattern = Pattern.compile("time=(\\d.+)*\\sms");
                Matcher m;
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    m = pattern.matcher(inputLine);
                    if (m.find()) {
                        return Double.parseDouble(m.group(1));
                    }
                }
            }else{
                BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String error_str = error.readLine();
                System.out.println(" mExitValue "+ error_str);
                return -1.0;
            }
        }
        catch (InterruptedException | IOException ignore)
        {
            ignore.printStackTrace();
            System.out.println(" Exception:"+ignore);
        }
        return -1.0;
    }
}
