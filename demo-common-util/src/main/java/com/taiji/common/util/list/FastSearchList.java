package com.taiji.common.util.list;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * 快速检索List，提供根据属性值查找对象的方法
 * @author waking
 *
 * @param <T>
 */
public class FastSearchList<T> implements Serializable, Collection<T> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8868897211103003085L;

	private Map<String, HashMap<Integer, HashSet<Integer>>> elementDataMap = new HashMap<>();

	private ArrayList<T> innerArrayList = new ArrayList<>();
	
	private Class<T> clazzT;
	
	private Map<String, String> fieldValueMethod_map = new HashMap<>();
	
	public FastSearchList(Class<T> clazz){
		this.clazzT = clazz;
		this.initializeElementDataMap(clazz);
	}
	
	public FastSearchList(Class<T> clazz, String... fieldNames){
		this.clazzT = clazz;
		this.initializeElementDataMap(fieldNames);
	}
	
	public FastSearchList(Class<T> clazz, List<T> list){
		this.clazzT = clazz;
		this.initializeElementDataMap(clazz);
		this.addAll(list);
	}
	
	public FastSearchList(Class<T> clazz, List<T> list, String... fieldNames){
		this.clazzT = clazz;
		this.initializeElementDataMap(fieldNames);
		this.addAll(list);
	}
	
	private void initializeElementDataMap(Class<T> clazz){
		Method[] methods = clazz.getMethods();
		for(Method m : methods){
			VariableAnnotation a = m.getAnnotation(VariableAnnotation.class);
			if(a != null){
				if(elementDataMap.get(a.variablename()) == null){
					elementDataMap.put(a.variablename(), new HashMap<Integer, HashSet<Integer>>());
				}
				fieldValueMethod_map.put(a.variablename(), m.getName());
			}
		}
	}
	
	private void initializeElementDataMap(String... fieldNames){
		for(String fieldName : fieldNames){
			elementDataMap.put(fieldName, new HashMap<Integer, HashSet<Integer>>());
			fieldValueMethod_map.put(fieldName, "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1,fieldName.length()));
		}
	}
	
	/**
	 * 查找满足属性值的对象集合
	 * @param searchTOList
	 * @return
	 */
	public FastSearchList<T> searchSubList(String fieldName,Object fieldValue){
		FastSearchList<T> result_list = null;
		HashSet<Integer> set = elementDataMap.get(fieldName).get(fieldValue.hashCode());
		if(set != null && set.size() != 0){
			result_list = (FastSearchList<T>)new FastSearchList<>(clazzT);
			Iterator<Integer> it = set.iterator();
			while(it.hasNext()){
				Integer a = it.next();
				result_list.add(innerArrayList.get(a));
			}
		}
		return result_list;
	}
	
	/**
	 * 查找同时满足多组属性值的对象集合
	 * @param searchTOList
	 * @return
	 */
	public FastSearchList<T> searchSubList(List<SearchTO> searchTOList){
		FastSearchList<T> result_list = null;
		Integer previousIndex = -1;
		int conditionCount = searchTOList.size();
		Map<Integer, Integer> groupmap = new HashMap<>();
		for(SearchTO to : searchTOList){
			String name = to.getFiledName();
			Object value = to.getFiledValue();
			if(elementDataMap.get(name) == null){
				continue;
			}
			HashSet<Integer> set = elementDataMap.get(name).get(value.hashCode());
			if(set != null && set.size() != 0){
				Iterator<Integer> it = set.iterator();
				while(it.hasNext()){
					previousIndex = it.next();
					if(groupmap.get(previousIndex) == null){
						groupmap.put(previousIndex, 1);
					}else{
						groupmap.put(previousIndex, groupmap.get(previousIndex) + 1);
					}
				}
			}
		}
		
		Iterator<Integer> it = groupmap.keySet().iterator();
		Integer key = null;
		while(it.hasNext()){
			key = it.next();
			if(groupmap.get(key) == conditionCount){
				result_list = result_list == null ? (FastSearchList<T>)new FastSearchList<>(clazzT) : result_list;
				result_list.add(innerArrayList.get(key));
			}
		}
		return result_list;
	}
	
	private void buildElementDataMap(T t, int index){
		String fieldName = null;
		Iterator<String> methodNames_it = fieldValueMethod_map.keySet().iterator();
		while(methodNames_it.hasNext()){
			fieldName = methodNames_it.next();
			try {
				Method m = t.getClass().getMethod(fieldValueMethod_map.get(fieldName), new Class[0]);
				Object fieldvalue = m.invoke(t);
				if(elementDataMap.get(fieldName).get(fieldvalue.hashCode()) == null){
					elementDataMap.get(fieldName).put(fieldvalue.hashCode(), new HashSet<Integer>());
				}
				elementDataMap.get(fieldName).get(fieldvalue.hashCode()).add(index);
			} catch (Exception e) {
				throw new RuntimeException("build elementDataMap error", e);
			}
		}
	}
	
	private void removeElementDataMapIndex(int index){
		Iterator<String> it = elementDataMap.keySet().iterator();
		while(it.hasNext()){
			String fieldName = it.next();
			Iterator<Integer> it2 = elementDataMap.get(fieldName).keySet().iterator();
			while(it2.hasNext()){
				Integer filedValue = it2.next();
				elementDataMap.get(fieldName).get(filedValue).remove(index);
			}
		}
	}
	
	public boolean add(T t) {
		boolean boo = innerArrayList.add(t);
		this.buildElementDataMap(t, innerArrayList.indexOf(t));
		return boo;
	}

	public boolean addAll(Collection<? extends T> c) {
		for(T t : c){
			if(!add(t)){
				return false;
			}
		}
		return true;
	}

	@Override
	public int size() {
		return innerArrayList.size();
	}

	@Override
	public boolean isEmpty() {
		return innerArrayList.size() == 0;
	}

	@Override
	public boolean contains(Object o) {
		return this.innerArrayList.contains(o);
	}

	@Override
	public Iterator<T> iterator() {
		return new Itr();
	}

	@Override
	public Object[] toArray() {
		return this.innerArrayList.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return this.innerArrayList.toArray(a);
	}

	@Override
	public boolean remove(Object o) {
		int index = this.innerArrayList.indexOf(o);
		if(index >= 0){
			this.removeElementDataMapIndex(index);
		}
		return index >= 0;
	}

	public T remove(int index) {
		T t = this.innerArrayList.remove(index);
		removeElementDataMapIndex(index);
		return t;
	}
	
	@Override
	public boolean containsAll(Collection<?> c) {
		return this.innerArrayList.containsAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		for(T t : (Collection<T>)c){
			int index = this.innerArrayList.indexOf(t);
			this.innerArrayList.remove(index);
			removeElementDataMapIndex(index);
		}
		return true;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean isretain = this.innerArrayList.retainAll(c);
		
		this.elementDataMap.clear();
		this.initializeElementDataMap(clazzT);
		Object[] obj = innerArrayList.toArray();
		for(int i = 0; i < obj.length; i++ ){
			this.buildElementDataMap((T)obj[i], i);
		}
		return isretain;
	}

	@Override
	public void clear() {
		this.innerArrayList.clear();
		this.elementDataMap.clear();
	}
	
	/**
	 * arraylist内部实现
	 * @author waking
	 *
	 */
    private class Itr implements Iterator<T> {
        int cursor;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such
        int expectedModCount = innerArrayList.size();

        public boolean hasNext() {
            return cursor != innerArrayList.size();
        }

        @SuppressWarnings("unchecked")
        public T next() {
            checkForComodification();
            int i = cursor;
            if (i >= innerArrayList.size())
                throw new NoSuchElementException();
            Object[] elementData = innerArrayList.toArray();
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            return (T) elementData[lastRet = i];
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
            	innerArrayList.remove(lastRet);
            	removeElementDataMapIndex(lastRet);
                cursor = lastRet;
                lastRet = -1;
                expectedModCount = innerArrayList.size();
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        final void checkForComodification() {
            if (innerArrayList.size() != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }
    
    public class SearchTO{
    	private String filedName;
    	private Object filedValue;
    	
    	public SearchTO(){}
    	public SearchTO(String filedName, Object filedValue){
    		this.filedName = filedName;
    		this.filedValue = filedValue;
    	}
    	
		public String getFiledName() {
			return filedName;
		}
		public void setFiledName(String filedName) {
			this.filedName = filedName;
		}
		public Object getFiledValue() {
			return filedValue;
		}
		public void setFiledValue(Object filedValue) {
			this.filedValue = filedValue;
		}
    }
}