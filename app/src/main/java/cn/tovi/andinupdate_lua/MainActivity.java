package cn.tovi.andinupdate_lua;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;

import cn.tovi.andinupdate_lua.R;

public class MainActivity extends Activity implements View.OnClickListener {

    private LuaState mLuaState;
    private EditText editMessage;

    boolean isToast1 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        editMessage = (EditText) findViewById(R.id.eidt_message);

        mLuaState = LuaStateFactory.newLuaState();
        mLuaState.openLibs();

        updateFile();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_update_file:
                updateFile();
                break;
            case R.id.btn_run:
                luaToast(editMessage.getText().toString());
                break;
        }

    }


    private void updateFile() {
        if (isToast1 = !isToast1)
            if (LuaUtil.moveLuaFile("toast1.lua", this))
                toast("更新Toast1成功");
            else
                toast("更新Toast1失败");
        else {
            if (LuaUtil.moveLuaFile("toast2.lua", this))
                toast("更新Toast2成功");
            else
                toast("更新Toast2失败");
        }
    }


    private void luaToast(String message) {
        String luaString = LuaUtil.readLuaFile(this);

        if (TextUtils.isEmpty(luaString)) {
            toast("get Lua error");
            return;
        }

        mLuaState.LdoString(luaString);
        mLuaState.getField(LuaState.LUA_GLOBALSINDEX, "toast");
        mLuaState.pushJavaObject(this);
        mLuaState.pushString(message);
        mLuaState.call(2, 0);
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
