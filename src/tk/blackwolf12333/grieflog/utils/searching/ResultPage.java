package tk.blackwolf12333.grieflog.utils.searching;

import java.util.List;

import tk.blackwolf12333.grieflog.data.BaseData;

public class ResultPage {

	List<BaseData> page;
	int pagenumber;
	
	public ResultPage(List<BaseData> list, int pagenumber) {
		this.page = list;
		this.pagenumber = pagenumber;
	}
	
	public BaseData[] getPage() {
		BaseData[] page = new BaseData[9];
		page = this.page.toArray(page);
		return page;
	}
}
