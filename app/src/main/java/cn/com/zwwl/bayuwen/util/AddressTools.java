package cn.com.zwwl.bayuwen.util;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.com.zwwl.bayuwen.model.Entry;

/**
 * 城市选择工具
 */
public class AddressTools {
    private Context mContext;
    public Map<String, ArrayList<CityModel>> mProvinceDatasMap = new HashMap<String,
            ArrayList<CityModel>>();
    public Map<String, ArrayList<DistModel>> mCityDatasMap = new HashMap<String,
            ArrayList<DistModel>>();

    public ArrayList<ProvinceModel> arrProvinces = new ArrayList<>();// 所有的省
    private ArrayList<CityModel> arrCitys = new ArrayList<>();// 所有的市

    public AddressTools(Context context) {
        this.mContext = context;
        initJsonData();
    }

    /**
     * 从文件中读取地址数据
     */
    public void initJsonData() {
        try {
            StringBuffer sb = new StringBuffer();
            InputStream is = mContext.getAssets().open("test.json");
            int len = -1;
            byte[] buf = new byte[1024];
            while ((len = is.read(buf)) != -1) {
                sb.append(new String(buf, 0, len, "utf-8"));
            }
            is.close();
            JSONArray array = new JSONArray(sb.toString());

            // 遍历第一次，筛选省
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                if (!jsonObject.has("parent")) {
                    ProvinceModel provinceModel = new ProvinceModel();
                    provinceModel.setPid(jsonObject.optString("value"));
                    provinceModel.setPtxt(jsonObject.optString("name"));
                    arrProvinces.add(provinceModel);
                }
            }
            // 遍历第二遍，初始化province map
            for (ProvinceModel p : arrProvinces) {
                ArrayList<CityModel> cityModels = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    if (jsonObject.has("parent") && jsonObject.optString("parent").equals(p
                            .getPid())) {
                        CityModel c = new CityModel();
                        c.setCid(jsonObject.optString("value"));
                        c.setCtxt(jsonObject.optString("name"));
                        cityModels.add(c);
                        arrCitys.add(c);
                    }
                }
                mProvinceDatasMap.put(p.getPid(), cityModels);
            }

            // 遍历第三遍，初始化city map
            for (CityModel c : arrCitys) {
                ArrayList<DistModel> distModels = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    if (jsonObject.has("parent") && jsonObject.optString("parent").equals(c
                            .getCid())) {
                        DistModel d = new DistModel();
                        d.setDid(jsonObject.optString("value"));
                        d.setDtxt(jsonObject.optString("name"));
                        distModels.add(d);
                    }
                }
                mCityDatasMap.put(c.getCid(), distModels);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前省份列表
     *
     * @return
     */
    public ArrayList<ProvinceModel> initProvinceList() {
        return arrProvinces;
    }

    /**
     * 获取当前省份的城市列表
     *
     * @param pid
     * @return
     */
    public ArrayList<CityModel> initCityList(String pid) {
        return mProvinceDatasMap.get(pid);
    }

    /**
     * 获取当前城市的区列表
     *
     * @param cid
     * @return
     */
    public ArrayList<DistModel> initDistList(String cid) {
        return mCityDatasMap.get(cid);
    }

    public class ProvinceModel extends Entry {
        private String ptxt;
        private String pid;

        public String getPtxt() {
            return ptxt;
        }

        public void setPtxt(String ptxt) {
            this.ptxt = ptxt;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }
    }

    public class CityModel extends Entry {
        private String ctxt;
        private String cid;

        public String getCtxt() {
            return ctxt;
        }

        public void setCtxt(String ctxt) {
            this.ctxt = ctxt;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }
    }

    public class DistModel extends Entry {
        private String dtxt;
        private String did;

        public String getDtxt() {
            return dtxt;
        }

        public void setDtxt(String dtxt) {
            this.dtxt = dtxt;
        }

        public String getDid() {
            return did;
        }

        public void setDid(String did) {
            this.did = did;
        }
    }

    /**
     * 通过城市名称获取id
     *
     * @param cityName
     */
    public void getAidByCityname(String cityName) {
        if (TextUtils.isEmpty(cityName)) return;


    }
}
