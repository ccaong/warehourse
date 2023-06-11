package com.ccaong.warehousingmanager.ui.activity.main;

import android.app.Application;
import android.content.Context;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.ccaong.warehousingmanager.App;
import com.ccaong.warehousingmanager.R;
import com.ccaong.warehousingmanager.base.BaseActivity;
import com.ccaong.warehousingmanager.databinding.ActivityMainBinding;
import com.ccaong.warehousingmanager.rfid.other.power.OtherPower;
import com.ccaong.warehousingmanager.ui.activity.main.menu.MenuFragment;
import com.ccaong.warehousingmanager.ui.activity.main.mine.MineFragment;
import com.ccaong.warehousingmanager.util.AndroidWakeLock;
import com.ccaong.warehousingmanager.util.SPconfig;
import com.google.android.material.tabs.TabLayout;
import com.orhanobut.hawk.Hawk;
import com.pow.api.cls.RfidPower;
import com.uhf.api.cls.Reader;

import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author caocong
 * @date 2022/9/18
 */
public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {

    private static final String TAG = MainActivity.class.getSimpleName();
    private MenuFragment menuFragment;

    private MineFragment mineFragment;

    private App myapp;

//    AndroidWakeLock Awl;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    protected void bindViewModel() {

    }

    @Override
    protected boolean isNoActionBar() {
        return true;
    }

    @Override
    protected void init() {
        menuFragment = new MenuFragment();
        mineFragment = new MineFragment();

        // 具体代码
        FragmentTransaction transaction = getFragmentTransaction();
        // 加载当前显示的Fragment
        transaction.replace(R.id.frame_layout, menuFragment);
        transaction.commit();


        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FragmentTransaction transaction = getFragmentTransaction();

                if (tab.getPosition() == 0) {
                    transaction.replace(R.id.frame_layout, menuFragment);
                } else {
                    transaction.replace(R.id.frame_layout, mineFragment);
                }
                transaction.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        initRFID();
        onStartPreparation();
        connectRfid();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 配置更改， 判断是否需要重新连接RFID
        boolean a = Hawk.get("RE_CONNECT_RFID", false);
        if (a) {
            connectRfid();
        }
    }

    private FragmentTransaction getFragmentTransaction() {
        // 具体代码
        FragmentManager manager = getSupportFragmentManager();
        return manager.beginTransaction();
    }


    private void initRFID() {
        String apkRoot = "chmod 777 " + getPackageCodePath();
        // 设置权限
        runRootCommand(apkRoot);


        Application app = getApplication();
        myapp = (App) app;
        myapp.Mreader = new Reader();
        myapp.spf = new SPconfig(this);

        myapp.Rparams = myapp.new ReaderParams();

        RfidPower.PDATYPE PT = RfidPower.PDATYPE.valueOf(0);
        myapp.Rpower = new RfidPower(PT, getApplicationContext());
    }

    public void connectRfid() {
        Hawk.put("RE_CONNECT_RFID", false);

        new Thread(() -> {
            mViewModel.setRfidStatus("0");
            String ip = "/dev/ttyMSM1";
            int portc = 1;

            OtherPower opow = new OtherPower();
            opow.oPowerUp();

            Reader.GetDeviceVersion(ip, new Reader.deviceVersion());

            Reader.READER_ERR er = myapp.Mreader.InitReader_Notype(ip, portc);

            if (er == Reader.READER_ERR.MT_OK_ERR) {

                Reader.Inv_Potls_ST ipst = myapp.Mreader.new Inv_Potls_ST();
                ipst.potlcnt = 1;
                ipst.potls = new Reader.Inv_Potl[ipst.potlcnt];
                for (int i = 0; i < ipst.potlcnt; i++) {
                    Reader.Inv_Potl ipl = myapp.Mreader.new Inv_Potl();
                    ipl.weight = 30;
                    ipl.potl = Reader.SL_TagProtocol.SL_TAG_PROTOCOL_GEN2;
                    ipst.potls[i] = ipl;
                }
                er = myapp.Mreader.ParamSet(
                        Reader.Mtr_Param.MTR_PARAM_TAG_INVPOTL, ipst);
                int[] av = new int[1];
                myapp.Mreader.ParamGet(
                        Reader.Mtr_Param.MTR_PARAM_READER_AVAILABLE_ANTPORTS, av);
                ConnectHandleUI();
            } else {
                mViewModel.setRfidStatus("2");
            }
        }).start();


    }

    /**
     * 连接后读取配置文件配置读写器
     */
    private void ConnectHandleUI() {
        try {
            Reader.READER_ERR er;
            myapp.Rparams = myapp.spf.ReadReaderParams();
            if (myapp.Rparams.invpro.size() < 1) {
                myapp.Rparams.invpro.add("GEN2");
            }

            List<Reader.SL_TagProtocol> ltp = new ArrayList<Reader.SL_TagProtocol>();
            for (int i = 0; i < myapp.Rparams.invpro.size(); i++) {
                if (myapp.Rparams.invpro.get(i).equals("GEN2")) {
                    ltp.add(Reader.SL_TagProtocol.SL_TAG_PROTOCOL_GEN2);
                } else if (myapp.Rparams.invpro.get(i).equals("6B")) {
                    ltp.add(Reader.SL_TagProtocol.SL_TAG_PROTOCOL_ISO180006B);
                } else if (myapp.Rparams.invpro.get(i).equals("IPX64")) {
                    ltp.add(Reader.SL_TagProtocol.SL_TAG_PROTOCOL_IPX64);
                } else if (myapp.Rparams.invpro.get(i).equals("IPX256")) {
                    ltp.add(Reader.SL_TagProtocol.SL_TAG_PROTOCOL_IPX256);
                }
            }

            Reader.Inv_Potls_ST ipst = myapp.Mreader.new Inv_Potls_ST();
            ipst.potlcnt = ltp.size();
            ipst.potls = new Reader.Inv_Potl[ipst.potlcnt];
            Reader.SL_TagProtocol[] stp = ltp.toArray(new Reader.SL_TagProtocol[ipst.potlcnt]);
            for (int i = 0; i < ipst.potlcnt; i++) {
                Reader.Inv_Potl ipl = myapp.Mreader.new Inv_Potl();
                ipl.weight = 30;
                ipl.potl = stp[i];
                ipst.potls[i] = ipl;
            }

            er = myapp.Mreader.ParamSet(Reader.Mtr_Param.MTR_PARAM_TAG_INVPOTL, ipst);
            er = myapp.Mreader.ParamSet(Reader.Mtr_Param.MTR_PARAM_READER_IS_CHK_ANT, new int[]{myapp.Rparams.checkant});

            Reader.AntPowerConf apcf = myapp.Mreader.new AntPowerConf();
            apcf.antcnt = 1;
            for (int i = 0; i < apcf.antcnt; i++) {
                Reader.AntPower jaap = myapp.Mreader.new AntPower();
                jaap.antid = i + 1;
                jaap.readPower = (short) myapp.Rparams.rpow[i];
                jaap.writePower = (short) myapp.Rparams.wpow[i];
                apcf.Powers[i] = jaap;
            }
            myapp.Mreader.ParamSet(Reader.Mtr_Param.MTR_PARAM_RF_ANTPOWER, apcf);

            Reader.Region_Conf rre;
            switch (myapp.Rparams.region) {
                case 0:
                    rre = Reader.Region_Conf.RG_PRC;
                    break;
                case 1:
                    rre = Reader.Region_Conf.RG_NA;
                    break;
                case 2:
                    rre = Reader.Region_Conf.RG_NONE;
                    break;
                case 3:
                    rre = Reader.Region_Conf.RG_KR;
                    break;
                case 4:
                    rre = Reader.Region_Conf.RG_EU;
                    break;
                case 5:
                    rre = Reader.Region_Conf.RG_EU2;
                    break;
                case 6:
                    rre = Reader.Region_Conf.RG_EU3;
                    break;
                case 9:
                    rre = Reader.Region_Conf.RG_OPEN;
                    break;
                case 10:
                    rre = Reader.Region_Conf.RG_PRC2;
                    break;
                case 7:
                case 8:
                default:
                    rre = Reader.Region_Conf.RG_NONE;
                    break;
            }
            if (rre != Reader.Region_Conf.RG_NONE) {
                er = myapp.Mreader.ParamSet(
                        Reader.Mtr_Param.MTR_PARAM_FREQUENCY_REGION, rre);
            }

            if (myapp.Rparams.frelen > 0) {

                Reader.HoptableData_ST hdst = myapp.Mreader.new HoptableData_ST();
                hdst.lenhtb = myapp.Rparams.frelen;
                hdst.htb = myapp.Rparams.frecys;
                er = myapp.Mreader.ParamSet(
                        Reader.Mtr_Param.MTR_PARAM_FREQUENCY_HOPTABLE, hdst);
            }

            er = myapp.Mreader.ParamSet(Reader.Mtr_Param.MTR_PARAM_POTL_GEN2_SESSION,
                    new int[]{myapp.Rparams.session});
            er = myapp.Mreader.ParamSet(Reader.Mtr_Param.MTR_PARAM_POTL_GEN2_Q,
                    new int[]{myapp.Rparams.qv});
            er = myapp.Mreader.ParamSet(
                    Reader.Mtr_Param.MTR_PARAM_POTL_GEN2_WRITEMODE,
                    new int[]{myapp.Rparams.wmode});
            er = myapp.Mreader.ParamSet(
                    Reader.Mtr_Param.MTR_PARAM_POTL_GEN2_MAXEPCLEN,
                    new int[]{myapp.Rparams.maxlen});
            er = myapp.Mreader.ParamSet(Reader.Mtr_Param.MTR_PARAM_POTL_GEN2_TARGET,
                    new int[]{myapp.Rparams.target});

            if (myapp.Rparams.filenable == 1) {
                Reader.TagFilter_ST tfst = myapp.Mreader.new TagFilter_ST();
                tfst.bank = myapp.Rparams.filbank;
                int len = myapp.Rparams.fildata.length();
                len = len % 2 == 0 ? len : len + 1;
                tfst.fdata = new byte[len / 2];
                myapp.Mreader.Str2Hex(myapp.Rparams.fildata,
                        myapp.Rparams.fildata.length(), tfst.fdata);
                tfst.flen = myapp.Rparams.fildata.length() * 4;
                tfst.startaddr = myapp.Rparams.filadr;
                tfst.isInvert = myapp.Rparams.filisinver;

                myapp.Mreader.ParamSet(Reader.Mtr_Param.MTR_PARAM_TAG_FILTER, tfst);
            }

            if (myapp.Rparams.emdenable == 1) {
                Reader.EmbededData_ST edst = myapp.Mreader.new EmbededData_ST();

                edst.accesspwd = null;
                edst.bank = myapp.Rparams.emdbank;
                edst.startaddr = myapp.Rparams.emdadr;
                edst.bytecnt = myapp.Rparams.emdbytec;

                er = myapp.Mreader.ParamSet(
                        Reader.Mtr_Param.MTR_PARAM_TAG_EMBEDEDDATA, edst);
            }

            er = myapp.Mreader.ParamSet(
                    Reader.Mtr_Param.MTR_PARAM_TAGDATA_UNIQUEBYEMDDATA,
                    new int[]{myapp.Rparams.adataq});
            er = myapp.Mreader.ParamSet(
                    Reader.Mtr_Param.MTR_PARAM_TAGDATA_RECORDHIGHESTRSSI,
                    new int[]{myapp.Rparams.rhssi});
            er = myapp.Mreader.ParamSet(Reader.Mtr_Param.MTR_PARAM_TAG_SEARCH_MODE,
                    new int[]{myapp.Rparams.invw});

            Reader.AntPortsVSWR apvr = myapp.Mreader.new AntPortsVSWR();
            apvr.andid = 1;
            apvr.power = (short) myapp.Rparams.rpow[0];
            apvr.region = Reader.Region_Conf.RG_NA;
            er = myapp.Mreader.ParamGet(Reader.Mtr_Param.MTR_PARAM_RF_ANTPORTS_VSWR, apvr);

            Reader.HardwareDetails val = myapp.Mreader.new HardwareDetails();
            er = myapp.Mreader.GetHardwareDetails(val);


        } catch (Exception ex) {
            Log.d("MYINFO", ex.getMessage() + ex + ex.getStackTrace());
        }
        mViewModel.setRfidStatus("1");
    }

    @Override
    protected void onDestroy() {
        closeRfid();
        onStopDestruction();
//        Awl.ReleaseWakeLock();
        super.onDestroy();
    }


    private void closeRfid() {
        if (myapp.Mreader != null) {
            myapp.Mreader.CloseReader();
        }
        myapp.Rpower.PowerDown();
    }


    public static boolean runRootCommand(String command) {
        Process process = null;
        DataOutputStream os = null;

        try {
            process = Runtime.getRuntime().exec("sh");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(command + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            Log.d("Phone Link",
                    "su root - the device is not rooted,  error message： "
                            + e.getMessage());
            return false;
        } finally {
            try {
                if (null != os) {
                    os.close();
                }
                if (null != process) {
                    process.destroy();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}
