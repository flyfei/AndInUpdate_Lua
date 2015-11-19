package cn.tovi.andinupdate_lua;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.tovi.andinupdate_lua.FileUtil;

/**
 * @author <a href='mailto:zhaotengfei9@gmail.com'>Tengfei Zhao</a>
 */
public class LuaUtil {

    private static final String LUA_FILE = "toast.lua";

    public static boolean moveLuaFile(String fileName, Context context) {
        FileOutputStream outStream = null;
        try {
            outStream = context.openFileOutput(LUA_FILE, Context.MODE_PRIVATE);
            outStream.write(FileUtil.readFromAssets(context, fileName).getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (outStream != null) {
                try {
                    outStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String readLuaFile(Context context) {

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = context.openFileInput(LUA_FILE);

            byte[] buf = new byte[1024];
            int hasRead = 0;
            StringBuilder stringBuilder = new StringBuilder();
            while ((hasRead = fileInputStream.read(buf)) > 0) {
                stringBuilder.append(new String(buf, 0, hasRead));
            }
            fileInputStream.close();
            return stringBuilder.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
