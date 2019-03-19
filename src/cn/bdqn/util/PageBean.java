package cn.bdqn.util;

import java.util.List;

/**
 * 泛型的工具类
 * 
 * @author WindLin
 * 
 * @param <T>
 *            泛型以实际调用者的类型决定
 */
public class PageBean<T> {

	private int pageSize = 8; // 每页显示的数据数量
	private int pageNo = 1; // 当前页码 默认第一页
	private int totalPages; // 总页数 只读
	private int totalCount; // 总记录数,去决定 总页数
	private List<T> pageList; // 每页对应的数据集合

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	
	public int getPageNo() {
		return pageNo;
	}
	
	//当前页不能小于1也不能大于总页数
	public void setPageNo(int pageNo) {
		if(pageNo<1)
			this.pageNo = 1;
		else if( totalPages> 0 && pageNo > totalPages)
			this.pageNo = totalPages; //最后一页
		else
			this.pageNo = pageNo;
	}

	//只读 总页数
	public int getTotalPages() {
		return totalPages;
	}


	public int getTotalCount() {
		return totalCount;
	}

	//设置完总记录数后自动计算总页数
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		this.totalPages = (totalCount%pageSize==0)?
				totalCount/pageSize:totalCount/pageSize +1;
	}

	public List<T> getPageList() {
		return pageList;
	}

	public void setPageList(List<T> pageList) {
		this.pageList = pageList;
	}

}
