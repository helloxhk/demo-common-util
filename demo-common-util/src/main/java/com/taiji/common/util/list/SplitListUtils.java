package com.taiji.common.util.list;

import java.util.ArrayList;
import java.util.List;

/**
 * list拆分类
 * 
 * @author sas
 *
 */
public class SplitListUtils {
	/**
	 * 
	 * @param listSize
	 * 		单个list的条数
	 * @param listContent
	 * 		要拆分的list
	 * @return
	 */
	public <T> List<List<T>> splitList(int listSize, List<T> listContent) {
		
		List<List<T>> lst = new ArrayList<List<T>>();
		int listTotalPage = (listContent.size() + listSize - 1) / listSize;
		
		for (int i = 1; i <= listTotalPage; i++) {

			// 开始截取的list下标
			int formIndex = 0;
			// 结束截取的list下标
			int toIndex = 0;

			if (i <= listTotalPage && i != 0) {
				formIndex = (i - 1) * listSize;
			}

			if (i == listTotalPage) {
				toIndex = listContent.size();
			} else {
				toIndex = i * listSize;
			}
			lst.add(listContent.subList(formIndex, toIndex));
		}
		return lst;
	}
}
