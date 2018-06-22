package cn.com.zwwl.bayuwen.view;

import java.util.Comparator;

import cn.com.zwwl.bayuwen.model.CitySortModel;

/**
 * 
 * @author xiaanming
 *
 */
public class PinyinComparator implements Comparator<CitySortModel.CityBean> {

	public int compare(CitySortModel.CityBean o1, CitySortModel.CityBean o2) {
			if (o1.getInitial().equals("@") || o2.getInitial().equals("热")) {
				return -1;
			} else if (o1.getInitial().equals("热")
					|| o2.getInitial().equals("@")) {
				return 1;
			} else {
				return o1.getInitial().compareTo(o2.getInitial());
			}
		}


}
