package com.ccaong.warehousingmanager.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.ccaong.warehousingmanager.App;
import com.ccaong.warehousingmanager.base.BaseActivity;


/**
 * 配置文件类
 *
 * @author Administrator
 */
public class SPconfig {
    SharedPreferences sp;

    public SPconfig(Context cth) {
        Context ctx = cth;
        sp = ctx.getSharedPreferences("SP",
                Context.MODE_PRIVATE);
    }

    public String GetString(String key) {
        return sp.getString(key, "");
    }

    public boolean SaveString(String key, String val) {
        try {
            Editor editor = sp.edit();
            editor.putString(key, val);
            editor.commit();
        } catch (Exception ex) {
            return false;
        }
        return true;
    }


    public App.ReaderParams ReadReaderParams() {
        App.ReaderParams RP = new App().new ReaderParams();
        if (GetString("OPANT") != null && !GetString("OPANT").equals(""))
            RP.opant = Integer.valueOf(GetString("OPANT"));

        if (GetString("INVPRO") != null && !GetString("INVPRO").equals("")) {
            RP.invpro.clear();
            String[] vals = GetString("INVPRO").split(",");

            for (int i = 0; i < vals.length; i++) {
                RP.invpro.add(vals[i]);
            }

        }
        if (GetString("OPRO") != null && !GetString("OPRO").equals(""))
            RP.opro = GetString("OPRO");

        if (GetString("UANTS") != null && !GetString("UANTS").equals("")) {
            String[] vals = GetString("UANTS").split(",");
            RP.uants = new int[vals.length];
            for (int i = 0; i < vals.length; i++) {
                RP.uants[i] = Integer.valueOf(vals[i]);
            }
        }
        if (GetString("READTIME") != null && !GetString("READTIME").equals(""))
            RP.readtime = Integer.valueOf(GetString("READTIME"));
        if (GetString("SLEEP") != null && !GetString("SLEEP").equals(""))
            RP.sleep = Integer.valueOf(GetString("SLEEP"));
        if (GetString("CHECKANT") != null && !GetString("CHECKANT").equals(""))
            RP.checkant = Integer.valueOf(GetString("CHECKANT"));

        if (GetString("RPOW") != null && !GetString("RPOW").equals("")) {
            String[] vals = GetString("RPOW").split(",");
            RP.rpow = new int[vals.length];
            for (int i = 0; i < vals.length; i++) {
                RP.rpow[i] = Integer.valueOf(vals[i]);
            }
        }
        if (GetString("WPOW") != null && !GetString("WPOW").equals("")) {
            String[] vals = GetString("WPOW").split(",");
            RP.wpow = new int[vals.length];
            for (int i = 0; i < vals.length; i++) {
                RP.wpow[i] = Integer.valueOf(vals[i]);
            }
        }
        if (GetString("REGION") != null && !GetString("REGION").equals(""))
            RP.region = Integer.valueOf(GetString("REGION"));
        else
            RP.region = 1;
        if (GetString("FRELEN") != null && !GetString("FRELEN").equals(""))
            RP.frelen = Integer.valueOf(GetString("FRELEN"));

        if (RP.frelen > 0) {
            if (GetString("FRECYS") != null && !GetString("FRECYS").equals("")) {
                String[] vals = GetString("FRECYS").split(",");
                RP.frecys = new int[RP.frelen];
                for (int i = 0; i < vals.length; i++) {
                    RP.frecys[i] = Integer.valueOf(vals[i]);
                }
            }
        }
        if (GetString("SESSION") != null && !GetString("SESSION").equals(""))
            RP.session = Integer.valueOf(GetString("SESSION"));
        if (GetString("QV") != null && !GetString("QV").equals(""))
            RP.qv = Integer.valueOf(GetString("QV"));
        if (GetString("WMODE") != null && !GetString("WMODE").equals(""))
            RP.wmode = Integer.valueOf(GetString("WMODE"));
        if (GetString("BLF") != null && !GetString("BLF").equals(""))
            RP.blf = Integer.valueOf(GetString("BLF"));
        if (GetString("MAXLEN") != null && !GetString("MAXLEN").equals(""))
            RP.maxlen = Integer.valueOf(GetString("MAXLEN"));
        if (GetString("TARGET") != null && !GetString("TARGET").equals(""))
            RP.target = Integer.valueOf(GetString("TARGET"));
        if (GetString("GEN2CODE") != null && !GetString("GEN2CODE").equals(""))
            RP.gen2code = Integer.valueOf(GetString("GEN2CODE"));
        if (GetString("GEN2TARI") != null && !GetString("GEN2TARI").equals(""))
            RP.gen2tari = Integer.valueOf(GetString("GEN2TARI"));

        if (GetString("FILDATA") != null && !GetString("FILDATA").equals(""))
            RP.fildata = GetString("FILDATA");
        if (GetString("FILADR") != null && !GetString("FILADR").equals(""))
            RP.filadr = Integer.valueOf(GetString("FILADR"));
        if (GetString("FILBANK") != null && !GetString("FILBANK").equals(""))
            RP.filbank = Integer.valueOf(GetString("FILBANK"));
        if (GetString("FILISINVER") != null
                && !GetString("FILISINVER").equals(""))
            RP.filisinver = Integer.valueOf(GetString("FILISINVER"));
        if (GetString("FILENABLE") != null
                && !GetString("FILENABLE").equals(""))
            RP.filenable = Integer.valueOf(GetString("FILENABLE"));

        if (GetString("FILENABLE") != null
                && !GetString("FILENABLE").equals(""))
            RP.emdadr = Integer.valueOf(GetString("EMDADR"));
        if (GetString("FILENABLE") != null
                && !GetString("FILENABLE").equals(""))
            RP.emdbytec = Integer.valueOf(GetString("EMDBYTEC"));
        if (GetString("FILENABLE") != null
                && !GetString("FILENABLE").equals(""))
            RP.emdbank = Integer.valueOf(GetString("EMDBANK"));
        if (GetString("FILENABLE") != null
                && !GetString("FILENABLE").equals(""))
            RP.emdenable = Integer.valueOf(GetString("EMDENABLE"));

        if (GetString("ADATAQ") != null && !GetString("ADATAQ").equals(""))
            RP.adataq = Integer.valueOf(GetString("ADATAQ"));
        if (GetString("RHSSI") != null && !GetString("RHSSI").equals(""))
            RP.rhssi = Integer.valueOf(GetString("RHSSI"));
        if (GetString("INVW") != null && !GetString("INVW").equals(""))
            RP.invw = Integer.valueOf(GetString("INVW"));
        if (GetString("ISO6BDEEP") != null
                && !GetString("ISO6BDEEP").equals(""))
            RP.iso6bdeep = Integer.valueOf(GetString("ISO6BDEEP"));
        if (GetString("ISO6BDEL") != null && !GetString("ISO6BDEL").equals(""))
            RP.iso6bdel = Integer.valueOf(GetString("ISO6BDEL"));
        if (GetString("ISO6BBLF") != null && !GetString("ISO6BBLF").equals(""))
            RP.iso6bblf = Integer.valueOf(GetString("ISO6BBLF"));

        return RP;

    }
}
