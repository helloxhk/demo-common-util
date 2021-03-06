package com.taiji.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author zhouy
 *
 */
public class StringUtils {
	
	/**
	 * 字符串前补0直到字符串长度为length
	 * @param str
	 * @param length
	 * @return
	 */
	public synchronized static String leftFillZero(String str, int length) {
		if (str == null || str == "") {
			return null;
		}
		int count = length - str.length();
		if(count>0){
			for (int i = 0; i < count; i++) {
				str = "0" + str;
			}
		}else{
			str = str.substring(0, length);	
		}
		return str;
	}
	
	/**
	 * 字符串前补空格直到字符串长度为length
	 * @param str
	 * @param length
	 * @return
	 */
	public synchronized static String leftFillSpace(String str, int length) {
		if (str == null || str == "") {
			return null;
		}
		int count = length - str.length();
		if(count>0){
			for (int i = 0; i < count; i++) {
				str = " " + str;
			}
		}else{
			str = str.substring(0, length);	
		}
		return str;
	}
	
	/**
	 * 右补指定字符方法
	 * @param str
	 * @param length
	 * @param fillMsg
	 * @return
	 */
	public static String rightFillTemp(String str, int length, String fillMsg) {
		if (str == null)
			return null;
		int count = length - str.length();
		for (int i = 0; i < count; i++) {
			str = str + fillMsg;
		}
		return str;
	}
	
	/**
	 * 左补指定字符方法
	 * @param str
	 * @param length
	 * @param fillMsg
	 * @return
	 */
	public static String leftFillTemp(String str, int length, String fillMsg) {
		if (str == null)
			return null;
		int count = length - str.length();
		for (int i = 0; i < count; i++) {
			str = fillMsg + str;
		}
		return str;
	}
	
	/**
	 * 验证str是否在array数组中
	 * @param array
	 * @param str
	 * @return
	 */
	public static boolean valiArgInArray(Object[] array,Object str){
		if(array!=null&&array.length>0){
			if(!array[0].getClass().equals(str.getClass())){
				return false;
			}
			for(Object obj:array){
				if(str.equals(obj)){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 判断String 值 是否为空
	 * @param str
	 * @return
	 */
	public static boolean isNotNull(String str) {
		if (org.apache.commons.lang.StringUtils.isNotEmpty(str) && org.apache.commons.lang.StringUtils.isNotBlank(str))
			return true;
		else
			return false;
	}
	
	/**
	 * 判断String 值 是否为空
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str) {
		if (org.apache.commons.lang.StringUtils.isNotEmpty(str) && org.apache.commons.lang.StringUtils.isNotBlank(str))
			return false;
		else
			return true;
	}
	
	/**
	 * 获得参数MAP
	 * 
	 * @param str
	 * @return
	 * @throws BusinessException
	 */
	public static Map<String, String> getKeyMap(String str){
		Map<String, String> map = new HashMap<String, String>();
		String keys[] = str.split("&");
		for(int i = 0;i<keys.length;i++){
			String[] strs = keys[i].split("=");
			if(strs.length!=2){
				continue;
			}
			if(!StringUtils.isNotNull(strs[0])){
				continue;
			}
			if(!StringUtils.isNotNull(strs[1])){
				continue;
			}
			map.put(strs[0], strs[1]);
		}
		return map;
	}
	
	/**
	 * 根据传入字符串分割（可有空值key1=null,key2=123）
	 * @param returnMsg
	 * @param propSpacer
	 * @return
	 */
	public static Map<String, String> analyzeResponse(String returnMsg,String propSpacer) {
		Map<String, String> paramMap = new HashMap<String, String>();
		String[] msgStr = returnMsg.split(propSpacer);
		if (returnMsg != null && msgStr.length > 0) {
			for(String msg : msgStr){
				if(!msg.endsWith("=")){
					paramMap.put(msg.split("=")[0], msg.split("=")[1]);
				}else{
					paramMap.put(msg.split("=")[0], null);
				}
			}
		}
		return paramMap;
	}
	
	public static void main(String[] args) {
		/*byte[] keybyte = { 0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF, 0x23, 0x45, 0x67,
				(byte) 0x89, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF, 0x01, 0x45, 0x67, (byte) 0x89, (byte) 0xAB,
				(byte) 0xCD, (byte) 0xEF, 0x01, 0x23 };
		
		System.out.println(new String(keybyte));
		String keyStr = StringUtils.bin2hex(keybyte);
		System.out.println(keyStr);
		System.out.println(new String(StringUtils.hex2bin(keyStr)));*/
		//returncode=0000&returninfo=null&etransid=37913017&pid=00122050&pnum=3&pname=77+977+977+977+977+9xrbvv73vv73vv73Wte+/ve+/vTUw1Kog&pinfo=NDU3NTU0MDczMjQ3NjM1IzExMTIyMjMzMzQ0NDU1NXw0NDQ2MTkyMjk1NzkyNzEjMTExMjIyMzMzNDQ0NTU1fDc4NTg2MDA5NTEwNjAwMyMxMTEyMjIzMzM0NDQ1NTV8QCAg
		//returncode=0000&returninfo=&pinfo=6bqS6bqf5LiA5Y2h6YCa77yI5oiQ5ZCJ5oCd5rGX77yJMTDlhYMjMDY5MTEwMTAjMTAjOS4xMCMxMDAwNzUjMTAwMDUyfOS4remdkuWunee9keS4gOWNoemAmjEwMOWFgyMwMzUxMTEwMCMxMDAjOTEuOTYjMTAwMDcxIzEwMDA0OXzkuK3pnZLlrp3nvZHkuIDljaHpgJo1MOWFgyMwMzUxMTA1MCM1MCM0NS45OCMxMDAwNzEjMTAwMDQ5fOS4remdkuWunee9keS4gOWNoemAmjIw5YWDIzAzNTExMDIwIzIwIzE4LjM5IzEwMDA2OSMxMDAwNDl85Lit6Z2S5a6d572R5LiA5Y2h6YCaMTDlhYMjMDM1MTEwMTAjMTAjOS4yMCMxMDAwNzQjMTAwMDQ5fOe9keWfn+S4gOWNoemAmjYwMOeCuSMwMzkxMTAzOCMzOCMzNC4xOCMxNjgxIzE4NjJ8572R5Z+f5LiA5Y2h6YCaNDUw54K5IzAzOTExMDMwIzMwIzI2Ljk4IzE2ODEjMTg2MnznvZHln5/kuIDljaHpgJoxNTDngrkjMDM5MTEwMTAjMTAjOC45OSMxNjgxIzE4NjJ86JOd5riv5LiA5Y2h6YCaMzDlhYMjMDgxMTEwMzAjMzAjMjcuNTkjMTY3MCMxODUwfOiTnea4r+S4gOWNoemAmjEw5YWDIzA4MTExMDEwIzEwIzkuMjAjMTY3MCMxODUwfOaYk+a3mOWuouS4k+eUqOa1i+ivleWNoSMyMDEwMDQxOSMxIzAjMTQwMSMxNjAzfOm6kum6n+S4gOWNoemAmu+8iOaIkOWQieaAneaxl++8iTEwMOWFgyMwNjkxMTEwMCMxMDAjOTAuOTUjMTAwMDc1IzEwMDA1MnzpupLpup/kuIDljaHpgJrvvIjmiJDlkInmgJ3msZfvvIk1MOWFgyMwNjkxMTA1MCM1MCM0NS40OCMxMDAwNzUjMTAwMDUyfOm6kum6n+S4gOWNoemAmu+8iOaIkOWQieaAneaxl++8iTMw5YWDIzA2OTExMDMwIzMwIzI3LjI5IzEwMDA3NSMxMDAwNTJ85Lit5Y2O572R5ri45oiP5LiA5Y2h6YCaMTAw5YWDIzAyOTExMTAwIzEwMCM5Mi4yMiMxMDAwNzAjMTAwMDQ1fOS4reWNjue9kea4uOaIj+S4gOWNoemAmjMw5YWDIzAyOTExMDMwIzMwIzI3LjQ2IzEwMDA3MCMxMDAwNDV85Lit5Y2O572R5ri45oiP5LiA5Y2h6YCaMTDlhYMjMDI5MTEwMTAjMTAjOS4yMiMxMDAwNzAjMTAwMDQ1fDkx5LiA5Y2h6YCaMzXlhYMjMDI1MTEwMzUjMzUjMjYuNTkjMTAwMDY5IzEwMDA0NHw5MeS4gOWNoemAmjEw5YWDIzAyNTExMDEwIzEwIzcuNjAjMTAwMDY5IzEwMDA0NHznm67moIfkuIDljaHpgJozMOWFgyMwMTcxMTAzMCMzMCMyNS45MyMxMDAwNjUjMTAwMDQwfOebruagh+S4gOWNoemAmjEw5YWDIzAxNzExMDEwIzEwIzguNjQjMTAwMDY1IzEwMDA0MHzpqo/nvZHkuIDljaHpgJoxMDDlhYMjMDQzMTExMDAjMTAwIzkyLjQ2IzEwMDA2NCMxMDAwMzl86aqP572R5LiA5Y2h6YCaNTDlhYMjMDQzMTEwNTAjNTAjNDYuMjMjMTAwMDY0IzEwMDAzOXzpqo/nvZHkuIDljaHpgJozMOWFgyMwNDMxMTAzMCMzMCMyNy43NCMxMDAwNjQjMTAwMDM5fOmqj+e9keS4gOWNoemAmjE15YWDIzA0MzExMDE1IzE1IzEzLjg3IzEwMDA2NCMxMDAwMzl86aqP572R5LiA5Y2h6YCaMTDlhYMjMDQzMTEwMTAjMTAjOS4yNSMxMDAwNjQjMTAwMDM5fOmHkeWxseS4gOWNoemAmjUw5YWDIzAwOTExMDUwIzUwIzQ2LjAjMTAwMDYyIzEwMDAzN3zph5HlsbHkuIDljaHpgJozMOWFgyMwMDkxMTAzMCMzMCMyNy42IzEwMDA2MiMxMDAwMzd86YeR5bGx5LiA5Y2h6YCaMTXlhYMjMDA5MTEwMTUjMTUjMTMuODAjMTAwMDYyIzEwMDAzN3znm5vlpKfkuIDljaHpgJogMTAw5YWDIzAwMzExMTAwIzEwMCM5NS4wIzEwMDA2MSMxMDAwMzZ855ub5aSn5LiA5Y2h6YCaIDUw5YWDIzAwMzExMDUwIzUwIzQ3LjUjMTAwMDYxIzEwMDAzNnznm5vlpKfkuIDljaHpgJogNDXlhYMjMDAzMTEwNDUjNDUjNDIuNzUjMTAwMDYxIzEwMDAzNnznm5vlpKfkuIDljaHpgJogMzXlhYMjMDAzMTEwMzUjMzUuMTU0IzMzLjI1IzEwMDA2MSMxMDAwMzZ855ub5aSn5LiA5Y2h6YCaIDMw5YWDIzAwMzExMDMwIzMwIzI4LjUwIzEwMDA2MSMxMDAwMzZ855ub5aSn5LiA5Y2h6YCaIDEw5YWDIzAwMzExMDEwIzEwIzkuNTAjMTAwMDYxIzEwMDAzNnzmkJzni5DnlYXmuLjkuIDljaHpgJoxMDDlhYMjMDA1MTExMDAjMTAwIzkyLjY2IzEwMDA2MCMxMDAwMzV85pCc54uQ55WF5ri45LiA5Y2h6YCaNDDlhYMjMDA1MTEwNDAjNDAjMzcuMDYjMTAwMDYwIzEwMDAzNXzmkJzni5DnlYXmuLjkuIDljaHpgJozMOWFgyMwMDUxMTAzMCMzMCMyNy44IzEwMDA2MCMxMDAwMzV85pCc54uQ55WF5ri45LiA5Y2h6YCaMTXlhYMjMDA1MTEwMTUjMTUjMTMuOTUjMTAwMDYwIzEwMDAzNXzmkJzni5DnlYXmuLjkuIDljaHpgJo15YWDIzAwNTExMDA1IzUjNC42MyMxMDAwNjAjMTAwMDM1fOWFieWuh+S4gOWNoemAmjEwMOWFgyMwMzcxMTEwMCMxMDAjOTEuMDUjMTAwMDQ2IzEwMDAzNHzlhYnlrofkuIDljaHpgJozMOWFgyMwMzcxMTAzMCMzMCMyNy4zMiMxMDAwNDYjMTAwMDM0fOWFieWuh+S4gOWNoemAmjEw5YWDIzAzNzExMDEwIzEwIzkuMTEjMTAwMDQ2IzEwMDAzNHzlt6jkurrkuIDljaHpgJo2MOWFgyMwMDcxMTA2MCM2MCM1Ny4wMCMxMDAwNDUjMTAwMDMzfOW3qOS6uuS4gOWNoemAmjMw5YWDIzAwNzExMDMwIzMwIzI4LjUjMTAwMDQ1IzEwMDAzM3zlt6jkurrkuIDljaHpgJoxMOWFgyMwMDcxMTAxMCMxMCM5LjUjMTAwMDQ1IzEwMDAzM3zlt6jkurrkuIDljaHpgJoxMDDlhYMjMDA3MTExMDAjMTAwIzk2IzEwMDA0NSMxMDAwMzN85beo5Lq65LiA5Y2h6YCaNTDlhYMjMDA3MTEwNTAjNTAjNDcuNTAjMTAwMDQ1IzEwMDAzM3zlt6jkurrkuIDljaHpgJoyMOWFgyMwMDcxMTAyMCMyMCMxOS4wIzEwMDA0NSMxMDAwMzN85a6M576O5LiA5Y2h6YCaMTAw5YWDIzAwODExMTAwIzEwMCM5MC42NSMxMDAwNDQjMTAwMDMyfOWujOe+juS4gOWNoemAmjUw5YWDIzAwODExMDUwIzUwIzQ1LjMzIzEwMDA0NCMxMDAwMzJ85a6M576O5LiA5Y2h6YCaMzDlhYMjMDA4MTEwMzAjMzAjMjcuMzIjMTAwMDQ0IzEwMDAzMnzlroznvo7kuIDljaHpgJoxNeWFgyMwMDgxMTAxNSMxNSMxMy42NiMxMDAwNDQjMTAwMDMyfOiFvuiur1HluIE2MOWFgyMwMjgxMTA2MCM2MCM1Ni43MiMxMDAwNDMjMTAwMDMxfOiFvuiur1HluIEzMOWFgyMwMjgxMTAzMCMzMCMyOC4zNTEjMTAwMDQzIzEwMDAzMXzkuYXmuLjkvJHpl7LljaE1MOWFgyMwNDAxMTA1MCM1MCM0My40NyMxMDAwNDAjMTAwMDI5fOS5hea4uOS8kemXsuWNoTMw5YWDIzA0MDExMDMwIzMwIzI2LjA4IzEwMDA0MCMxMDAwMjl85LmF5ri45LyR6Zey5Y2hMTDlhYMjMDQwMTEwMTAjMTAjOC42OSMxMDAwNDAjMTAwMDI5fOe9keaYk+S4gOWNoemAmjMw5YWDIzAwNDExMDMwIzMwIzI4LjUjMTAwMDIwIzEwMDAyOHznvZHmmJPkuIDljaHpgJoxNeWFgyMwMDQxMTAxNSMxNSMxNC4yNSMxMDAwMjAjMTAwMDI4fOmtlOWFveS4lueVjO+8iOaImOe9keS4gOWNoemAmu+8iTMwMOeCueWNoSMwMDExMTAxNSMxNSMxNC4yNSMxMDAwMDEjMTAwMDA1fOmtlOWFveS4lueVjO+8iOaImOe9keS4gOWNoemAmu+8iTYwMOeCueWNoSMwMDExMTAzMCMzMCMyOC41IzEwMDAwMSMxMDAwMDV85Luj5Yqe6aKE5a2Y6K+d6LS5NTDlhYMjMDEwMTIwNTAjNTAjMCMxOTIxIzIzMDN85LiJ56em5qOL54mMMTAwMOWFgyMwMDcyMTAwMCMxMDAwIzAjMTkyMiMyMzAxfOS4ieenpuaji+eJjDUwMOWFgyMwMDcyMzUwMCM1MDAjMCMxOTIyIzIzMDF85LiJ56em5qOL54mMMTAw5YWDIzAwNzIzMTAwIzEwMCMwIzE5MjIjMjMwMXzkuInnp6bmo4vniYw1MOWFgyMwMDcyMzA1MCM1MCMwIzE5MjIjMjMwMXzkuInnp6bmo4vniYwxMOWFgyMwMDcyMzAxMCMxMCMwIzE5MjIjMjMwMXzku6Plip7pooTlrZjor53otLkxMDDlhYMjMDEwMTIxMDAjMTAwIzAjMTkyMSMyMzAzfOS7o+WKnumihOWtmOivnei0uTEw5YWDIzAxMDEyMDEwIzEwIzAjMTkyMSMyMzAzfOS7o+WKnumihOWtmOivnei0uTXlhYMjMDEwMTIwMDUjNSMwIzE5MjEjMjMwM3zku6Plip7pooTlrZjor53otLkx5YWDIzAxMDEyMDAxIzEjMCMxOTIxIzIzMDN85Luj5Yqe5a695bim5pmu6YCa5bCP5Yy65paw6KOFMTk55YWDLTg5OeWFg+aho+Wll+mkkCMwMTIxMjM0NSMzNDUjMCMxOTIxIzIzMDN85Luj5Yqe5a695bim5pmu6YCa5bCP5Yy65paw6KOFMTU55YWD5qGj5aWX6aSQIzAxMTEyMzQ1IzM0NSMwIzE5MjEjMjMwM3zku6Plip7lrr3luKblhYnpgJ/lsI/ljLrmlrDoo4UxOTnlhYMtODk55YWD5qGj5aWX6aSQIzAxMDEyMzQ1IzM0NSMwIzE5MjEjMjMwM3zku6Plip7lrr3luKblhYnpgJ/lsI/ljLrmlrDoo4UxNTnlhYPmoaPlpZfppJAjMDEwMTI0NDUjNDQ1IzAjMTkyMSMyMzAzfOaYk+a3mOWuouaJi+acuuWIt+WNoSMwMTAxMDE2OCMxNjgjMTY4IzIwNjEjMjUyMXzkuK3lm73ogZTpgJojMDAyMjExMDAjMTAwIzk4LjYjMTAwMDgwIzEwMDAyMnzkuK3lm73ogZTpgJrkuIDljaHlhYU1MOWFgyMwMDIyMTA1MCM1MCM0OS4zMCMxMDAwODAjMTAwMDIyfOS4reWbveiBlOmAmuS4gOWNoeWFhTMw5YWDIzAwMjIxMDMwIzMwIzI5LjU4IzEwMDA4MCMxMDAwMjJ85Lit5Zu96IGU6YCa5LiA5Y2h5YWFMjDlhYMjMDAyMjEwMjAjMjAjMTkuNzUjMTAwMDgwIzEwMDAyMnzljJfkuqznlLXkv6HlpoLmhI8xMzMtMTUzQ0RNQeWFheWAvOWNoSMwMDIyMjEzMyMxMDEuMTE0IzEzMC44MyMxNTIyIzEwMDAyNHzljJfkuqznp7vliqjlhYXlgLzljaExMDDlhYMjMDAxMjIxMDAjMTAwIzk5LjMjMTAwMDgxIzEwMDAyMHzljJfkuqznp7vliqjlhYXlgLzljaE1MOWFgyMwMDEyMjA1MCM1MCM0OS42NSMxMDAwODEjMTAwMDIwfOWMl+S6rOenu+WKqOWFheWAvOWNoTMw5YWDIzAwMTIyMDMwIzMwIzMwLjMjMTAwMDgxIzEwMDAyMHzljJfkuqznp7vliqjlhYXlgLzljaEyMOWFgyMwMDEyMjAyMCMyMCMyMS4wIzEwMDA4MSMxMDAwMjB85YyX5Lqs56e75Yqo5YWF5YC85Y2hMTDlhYMjMDAxMjIwMTAjMTAjMTAuODIjMTAwMDgxIzEwMDAyMHzljJfkuqznp7vliqjlhYXlgLzljaEzMDDlhYMjMDAxMjIzMDAjMzAwIzI5OC41IzEwMDA4MSMxMDAwMjB85YWo5Zu955S15L+h5YWF5YC85Y2hMTAw5YWDIzAwMjIzMTAwIzEwMCM5OC42IzEwMDA4MiMyMzQxfOWFqOWbveeUteS/oeWFheWAvOWNoTUw5YWDIzAwMjIzMDUwIzUwIzQ5LjMjMTAwMDgyIzIzNDF85YWo5Zu955S15L+h5YWF5YC85Y2hMzDlhYMjMDAyMjMwMzAjMzAjMzAuNSMxMDAwODIjMjM0MXzlhajlm73nlLXkv6HlhYXlgLzljaEyMOWFgyMwMDIyMzAyMCMyMCMyMC41IzEwMDA4MiMyMzQxfOWFqOWbveeUteS/oeWFheWAvOWNoTEw5YWDIzAwMjIzMDEwIzEwIzEwLjUjMTAwMDgyIzIzNDF85paw6KOF5py66L+H5oi36LS5MjAw5YWDIzAxMDEyMjAwIzIwMCMyMDAjMTg2MSMyMzgxfOS7o+eQhuWVhue7iOerr+S/neivgemHkTEwMDAw5YWDIzc3NzcyMDAwIzEwMDAwIzEwMDAwIzE4NjEjMjQ2MXzku6PnkIbllYbnu4jnq6/kv53or4Hph5ExMDAw5YWDIzc3NzcxMDAwIzEwMDAjMTAwMCMxODYxIzI0NjF85aSp5LiL6YCaMTAw5YWDIzAwMDc1MTAwIzEwMCM5NCMyMDQxIzI1MDF85aSp5LiL6YCaNTDlhYMjMDAwNzUwNTAjNTAjNDcjMjA0MSMyNTAxfOWkqeS4i+mAmjMw5YWDIzAwMDc1MDMwIzMwIzI4LjIjMjA0MSMyNTAxfOWkqeS4i+mAmjE15YWDIzAwMDc1MDE1IzE1IzE0LjEjMjA0MSMyNTAxfOWkqeS4i+mAmjEw5YWDIzAwMDc1MDEwIzEwIzkuNCMyMDQxIzI1MDF86IGU6YCa5Yqg55uf5bqX5L+d6K+B6YeRIzc3NzczMDAwIzMwMDAjMzAwMCMyNTYxIzMxMjF855yL6LSt55S15b2x5Y2hR+exuzQz5YWDIzAxMDEyMDQyIzQzIzQwLjg1MCMxOTYxIzIzNjJ854Gr6L2m56Wo5qCH6YWN5Lia5Yqh5YyFIzAxMDEzMDUwIzUwIzUwIzIwMDEjMjQ0MnzlvKDpmLPnrb7liLDljaEjMDEyMzQ0OTcjMCMwIzE0MDEjMTYwM3znjovotLrlubPnrb7liLDljaEjMDEyMzQ0OTkjMCMwIzE0MDEjMTYwM3zpmYjpmLPnrb7liLDljaEjMDEyMzQ0OTgjMCMwIzE0MDEjMTYwMyAg
		//System.out.println(getKeyMap("returncode=0000&returninfo=&pinfo=6bqS6bqf5LiA5Y2h6YCa77yI5oiQ5ZCJ5oCd5rGX77yJMTDlhYMjMDY5MTEwMTAjMTAjOS4xMCMxMDAwNzUjMTAwMDUyfOS4remdkuWunee9keS4gOWNoemAmjEwMOWFgyMwMzUxMTEwMCMxMDAjOTEuOTYjMTAwMDcxIzEwMDA0OXzkuK3pnZLlrp3nvZHkuIDljaHpgJo1MOWFgyMwMzUxMTA1MCM1MCM0NS45OCMxMDAwNzEjMTAwMDQ5fOS4remdkuWunee9keS4gOWNoemAmjIw5YWDIzAzNTExMDIwIzIwIzE4LjM5IzEwMDA2OSMxMDAwNDl85Lit6Z2S5a6d572R5LiA5Y2h6YCaMTDlhYMjMDM1MTEwMTAjMTAjOS4yMCMxMDAwNzQjMTAwMDQ5fOe9keWfn+S4gOWNoemAmjYwMOeCuSMwMzkxMTAzOCMzOCMzNC4xOCMxNjgxIzE4NjJ8572R5Z+f5LiA5Y2h6YCaNDUw54K5IzAzOTExMDMwIzMwIzI2Ljk4IzE2ODEjMTg2MnznvZHln5/kuIDljaHpgJoxNTDngrkjMDM5MTEwMTAjMTAjOC45OSMxNjgxIzE4NjJ86JOd5riv5LiA5Y2h6YCaMzDlhYMjMDgxMTEwMzAjMzAjMjcuNTkjMTY3MCMxODUwfOiTnea4r+S4gOWNoemAmjEw5YWDIzA4MTExMDEwIzEwIzkuMjAjMTY3MCMxODUwfOaYk+a3mOWuouS4k+eUqOa1i+ivleWNoSMyMDEwMDQxOSMxIzAjMTQwMSMxNjAzfOm6kum6n+S4gOWNoemAmu+8iOaIkOWQieaAneaxl++8iTEwMOWFgyMwNjkxMTEwMCMxMDAjOTAuOTUjMTAwMDc1IzEwMDA1MnzpupLpup/kuIDljaHpgJrvvIjmiJDlkInmgJ3msZfvvIk1MOWFgyMwNjkxMTA1MCM1MCM0NS40OCMxMDAwNzUjMTAwMDUyfOm6kum6n+S4gOWNoemAmu+8iOaIkOWQieaAneaxl++8iTMw5YWDIzA2OTExMDMwIzMwIzI3LjI5IzEwMDA3NSMxMDAwNTJ85Lit5Y2O572R5ri45oiP5LiA5Y2h6YCaMTAw5YWDIzAyOTExMTAwIzEwMCM5Mi4yMiMxMDAwNzAjMTAwMDQ1fOS4reWNjue9kea4uOaIj+S4gOWNoemAmjMw5YWDIzAyOTExMDMwIzMwIzI3LjQ2IzEwMDA3MCMxMDAwNDV85Lit5Y2O572R5ri45oiP5LiA5Y2h6YCaMTDlhYMjMDI5MTEwMTAjMTAjOS4yMiMxMDAwNzAjMTAwMDQ1fDkx5LiA5Y2h6YCaMzXlhYMjMDI1MTEwMzUjMzUjMjYuNTkjMTAwMDY5IzEwMDA0NHw5MeS4gOWNoemAmjEw5YWDIzAyNTExMDEwIzEwIzcuNjAjMTAwMDY5IzEwMDA0NHznm67moIfkuIDljaHpgJozMOWFgyMwMTcxMTAzMCMzMCMyNS45MyMxMDAwNjUjMTAwMDQwfOebruagh+S4gOWNoemAmjEw5YWDIzAxNzExMDEwIzEwIzguNjQjMTAwMDY1IzEwMDA0MHzpqo/nvZHkuIDljaHpgJoxMDDlhYMjMDQzMTExMDAjMTAwIzkyLjQ2IzEwMDA2NCMxMDAwMzl86aqP572R5LiA5Y2h6YCaNTDlhYMjMDQzMTEwNTAjNTAjNDYuMjMjMTAwMDY0IzEwMDAzOXzpqo/nvZHkuIDljaHpgJozMOWFgyMwNDMxMTAzMCMzMCMyNy43NCMxMDAwNjQjMTAwMDM5fOmqj+e9keS4gOWNoemAmjE15YWDIzA0MzExMDE1IzE1IzEzLjg3IzEwMDA2NCMxMDAwMzl86aqP572R5LiA5Y2h6YCaMTDlhYMjMDQzMTEwMTAjMTAjOS4yNSMxMDAwNjQjMTAwMDM5fOmHkeWxseS4gOWNoemAmjUw5YWDIzAwOTExMDUwIzUwIzQ2LjAjMTAwMDYyIzEwMDAzN3zph5HlsbHkuIDljaHpgJozMOWFgyMwMDkxMTAzMCMzMCMyNy42IzEwMDA2MiMxMDAwMzd86YeR5bGx5LiA5Y2h6YCaMTXlhYMjMDA5MTEwMTUjMTUjMTMuODAjMTAwMDYyIzEwMDAzN3znm5vlpKfkuIDljaHpgJogMTAw5YWDIzAwMzExMTAwIzEwMCM5NS4wIzEwMDA2MSMxMDAwMzZ855ub5aSn5LiA5Y2h6YCaIDUw5YWDIzAwMzExMDUwIzUwIzQ3LjUjMTAwMDYxIzEwMDAzNnznm5vlpKfkuIDljaHpgJogNDXlhYMjMDAzMTEwNDUjNDUjNDIuNzUjMTAwMDYxIzEwMDAzNnznm5vlpKfkuIDljaHpgJogMzXlhYMjMDAzMTEwMzUjMzUuMTU0IzMzLjI1IzEwMDA2MSMxMDAwMzZ855ub5aSn5LiA5Y2h6YCaIDMw5YWDIzAwMzExMDMwIzMwIzI4LjUwIzEwMDA2MSMxMDAwMzZ855ub5aSn5LiA5Y2h6YCaIDEw5YWDIzAwMzExMDEwIzEwIzkuNTAjMTAwMDYxIzEwMDAzNnzmkJzni5DnlYXmuLjkuIDljaHpgJoxMDDlhYMjMDA1MTExMDAjMTAwIzkyLjY2IzEwMDA2MCMxMDAwMzV85pCc54uQ55WF5ri45LiA5Y2h6YCaNDDlhYMjMDA1MTEwNDAjNDAjMzcuMDYjMTAwMDYwIzEwMDAzNXzmkJzni5DnlYXmuLjkuIDljaHpgJozMOWFgyMwMDUxMTAzMCMzMCMyNy44IzEwMDA2MCMxMDAwMzV85pCc54uQ55WF5ri45LiA5Y2h6YCaMTXlhYMjMDA1MTEwMTUjMTUjMTMuOTUjMTAwMDYwIzEwMDAzNXzmkJzni5DnlYXmuLjkuIDljaHpgJo15YWDIzAwNTExMDA1IzUjNC42MyMxMDAwNjAjMTAwMDM1fOWFieWuh+S4gOWNoemAmjEwMOWFgyMwMzcxMTEwMCMxMDAjOTEuMDUjMTAwMDQ2IzEwMDAzNHzlhYnlrofkuIDljaHpgJozMOWFgyMwMzcxMTAzMCMzMCMyNy4zMiMxMDAwNDYjMTAwMDM0fOWFieWuh+S4gOWNoemAmjEw5YWDIzAzNzExMDEwIzEwIzkuMTEjMTAwMDQ2IzEwMDAzNHzlt6jkurrkuIDljaHpgJo2MOWFgyMwMDcxMTA2MCM2MCM1Ny4wMCMxMDAwNDUjMTAwMDMzfOW3qOS6uuS4gOWNoemAmjMw5YWDIzAwNzExMDMwIzMwIzI4LjUjMTAwMDQ1IzEwMDAzM3zlt6jkurrkuIDljaHpgJoxMOWFgyMwMDcxMTAxMCMxMCM5LjUjMTAwMDQ1IzEwMDAzM3zlt6jkurrkuIDljaHpgJoxMDDlhYMjMDA3MTExMDAjMTAwIzk2IzEwMDA0NSMxMDAwMzN85beo5Lq65LiA5Y2h6YCaNTDlhYMjMDA3MTEwNTAjNTAjNDcuNTAjMTAwMDQ1IzEwMDAzM3zlt6jkurrkuIDljaHpgJoyMOWFgyMwMDcxMTAyMCMyMCMxOS4wIzEwMDA0NSMxMDAwMzN85a6M576O5LiA5Y2h6YCaMTAw5YWDIzAwODExMTAwIzEwMCM5MC42NSMxMDAwNDQjMTAwMDMyfOWujOe+juS4gOWNoemAmjUw5YWDIzAwODExMDUwIzUwIzQ1LjMzIzEwMDA0NCMxMDAwMzJ85a6M576O5LiA5Y2h6YCaMzDlhYMjMDA4MTEwMzAjMzAjMjcuMzIjMTAwMDQ0IzEwMDAzMnzlroznvo7kuIDljaHpgJoxNeWFgyMwMDgxMTAxNSMxNSMxMy42NiMxMDAwNDQjMTAwMDMyfOiFvuiur1HluIE2MOWFgyMwMjgxMTA2MCM2MCM1Ni43MiMxMDAwNDMjMTAwMDMxfOiFvuiur1HluIEzMOWFgyMwMjgxMTAzMCMzMCMyOC4zNTEjMTAwMDQzIzEwMDAzMXzkuYXmuLjkvJHpl7LljaE1MOWFgyMwNDAxMTA1MCM1MCM0My40NyMxMDAwNDAjMTAwMDI5fOS5hea4uOS8kemXsuWNoTMw5YWDIzA0MDExMDMwIzMwIzI2LjA4IzEwMDA0MCMxMDAwMjl85LmF5ri45LyR6Zey5Y2hMTDlhYMjMDQwMTEwMTAjMTAjOC42OSMxMDAwNDAjMTAwMDI5fOe9keaYk+S4gOWNoemAmjMw5YWDIzAwNDExMDMwIzMwIzI4LjUjMTAwMDIwIzEwMDAyOHznvZHmmJPkuIDljaHpgJoxNeWFgyMwMDQxMTAxNSMxNSMxNC4yNSMxMDAwMjAjMTAwMDI4fOmtlOWFveS4lueVjO+8iOaImOe9keS4gOWNoemAmu+8iTMwMOeCueWNoSMwMDExMTAxNSMxNSMxNC4yNSMxMDAwMDEjMTAwMDA1fOmtlOWFveS4lueVjO+8iOaImOe9keS4gOWNoemAmu+8iTYwMOeCueWNoSMwMDExMTAzMCMzMCMyOC41IzEwMDAwMSMxMDAwMDV85Luj5Yqe6aKE5a2Y6K+d6LS5NTDlhYMjMDEwMTIwNTAjNTAjMCMxOTIxIzIzMDN85LiJ56em5qOL54mMMTAwMOWFgyMwMDcyMTAwMCMxMDAwIzAjMTkyMiMyMzAxfOS4ieenpuaji+eJjDUwMOWFgyMwMDcyMzUwMCM1MDAjMCMxOTIyIzIzMDF85LiJ56em5qOL54mMMTAw5YWDIzAwNzIzMTAwIzEwMCMwIzE5MjIjMjMwMXzkuInnp6bmo4vniYw1MOWFgyMwMDcyMzA1MCM1MCMwIzE5MjIjMjMwMXzkuInnp6bmo4vniYwxMOWFgyMwMDcyMzAxMCMxMCMwIzE5MjIjMjMwMXzku6Plip7pooTlrZjor53otLkxMDDlhYMjMDEwMTIxMDAjMTAwIzAjMTkyMSMyMzAzfOS7o+WKnumihOWtmOivnei0uTEw5YWDIzAxMDEyMDEwIzEwIzAjMTkyMSMyMzAzfOS7o+WKnumihOWtmOivnei0uTXlhYMjMDEwMTIwMDUjNSMwIzE5MjEjMjMwM3zku6Plip7pooTlrZjor53otLkx5YWDIzAxMDEyMDAxIzEjMCMxOTIxIzIzMDN85Luj5Yqe5a695bim5pmu6YCa5bCP5Yy65paw6KOFMTk55YWDLTg5OeWFg+aho+Wll+mkkCMwMTIxMjM0NSMzNDUjMCMxOTIxIzIzMDN85Luj5Yqe5a695bim5pmu6YCa5bCP5Yy65paw6KOFMTU55YWD5qGj5aWX6aSQIzAxMTEyMzQ1IzM0NSMwIzE5MjEjMjMwM3zku6Plip7lrr3luKblhYnpgJ/lsI/ljLrmlrDoo4UxOTnlhYMtODk55YWD5qGj5aWX6aSQIzAxMDEyMzQ1IzM0NSMwIzE5MjEjMjMwM3zku6Plip7lrr3luKblhYnpgJ/lsI/ljLrmlrDoo4UxNTnlhYPmoaPlpZfppJAjMDEwMTI0NDUjNDQ1IzAjMTkyMSMyMzAzfOaYk+a3mOWuouaJi+acuuWIt+WNoSMwMTAxMDE2OCMxNjgjMTY4IzIwNjEjMjUyMXzkuK3lm73ogZTpgJojMDAyMjExMDAjMTAwIzk4LjYjMTAwMDgwIzEwMDAyMnzkuK3lm73ogZTpgJrkuIDljaHlhYU1MOWFgyMwMDIyMTA1MCM1MCM0OS4zMCMxMDAwODAjMTAwMDIyfOS4reWbveiBlOmAmuS4gOWNoeWFhTMw5YWDIzAwMjIxMDMwIzMwIzI5LjU4IzEwMDA4MCMxMDAwMjJ85Lit5Zu96IGU6YCa5LiA5Y2h5YWFMjDlhYMjMDAyMjEwMjAjMjAjMTkuNzUjMTAwMDgwIzEwMDAyMnzljJfkuqznlLXkv6HlpoLmhI8xMzMtMTUzQ0RNQeWFheWAvOWNoSMwMDIyMjEzMyMxMDEuMTE0IzEzMC44MyMxNTIyIzEwMDAyNHzljJfkuqznp7vliqjlhYXlgLzljaExMDDlhYMjMDAxMjIxMDAjMTAwIzk5LjMjMTAwMDgxIzEwMDAyMHzljJfkuqznp7vliqjlhYXlgLzljaE1MOWFgyMwMDEyMjA1MCM1MCM0OS42NSMxMDAwODEjMTAwMDIwfOWMl+S6rOenu+WKqOWFheWAvOWNoTMw5YWDIzAwMTIyMDMwIzMwIzMwLjMjMTAwMDgxIzEwMDAyMHzljJfkuqznp7vliqjlhYXlgLzljaEyMOWFgyMwMDEyMjAyMCMyMCMyMS4wIzEwMDA4MSMxMDAwMjB85YyX5Lqs56e75Yqo5YWF5YC85Y2hMTDlhYMjMDAxMjIwMTAjMTAjMTAuODIjMTAwMDgxIzEwMDAyMHzljJfkuqznp7vliqjlhYXlgLzljaEzMDDlhYMjMDAxMjIzMDAjMzAwIzI5OC41IzEwMDA4MSMxMDAwMjB85YWo5Zu955S15L+h5YWF5YC85Y2hMTAw5YWDIzAwMjIzMTAwIzEwMCM5OC42IzEwMDA4MiMyMzQxfOWFqOWbveeUteS/oeWFheWAvOWNoTUw5YWDIzAwMjIzMDUwIzUwIzQ5LjMjMTAwMDgyIzIzNDF85YWo5Zu955S15L+h5YWF5YC85Y2hMzDlhYMjMDAyMjMwMzAjMzAjMzAuNSMxMDAwODIjMjM0MXzlhajlm73nlLXkv6HlhYXlgLzljaEyMOWFgyMwMDIyMzAyMCMyMCMyMC41IzEwMDA4MiMyMzQxfOWFqOWbveeUteS/oeWFheWAvOWNoTEw5YWDIzAwMjIzMDEwIzEwIzEwLjUjMTAwMDgyIzIzNDF85paw6KOF5py66L+H5oi36LS5MjAw5YWDIzAxMDEyMjAwIzIwMCMyMDAjMTg2MSMyMzgxfOS7o+eQhuWVhue7iOerr+S/neivgemHkTEwMDAw5YWDIzc3NzcyMDAwIzEwMDAwIzEwMDAwIzE4NjEjMjQ2MXzku6PnkIbllYbnu4jnq6/kv53or4Hph5ExMDAw5YWDIzc3NzcxMDAwIzEwMDAjMTAwMCMxODYxIzI0NjF85aSp5LiL6YCaMTAw5YWDIzAwMDc1MTAwIzEwMCM5NCMyMDQxIzI1MDF85aSp5LiL6YCaNTDlhYMjMDAwNzUwNTAjNTAjNDcjMjA0MSMyNTAxfOWkqeS4i+mAmjMw5YWDIzAwMDc1MDMwIzMwIzI4LjIjMjA0MSMyNTAxfOWkqeS4i+mAmjE15YWDIzAwMDc1MDE1IzE1IzE0LjEjMjA0MSMyNTAxfOWkqeS4i+mAmjEw5YWDIzAwMDc1MDEwIzEwIzkuNCMyMDQxIzI1MDF86IGU6YCa5Yqg55uf5bqX5L+d6K+B6YeRIzc3NzczMDAwIzMwMDAjMzAwMCMyNTYxIzMxMjF855yL6LSt55S15b2x5Y2hR+exuzQz5YWDIzAxMDEyMDQyIzQzIzQwLjg1MCMxOTYxIzIzNjJ854Gr6L2m56Wo5qCH6YWN5Lia5Yqh5YyFIzAxMDEzMDUwIzUwIzUwIzIwMDEjMjQ0MnzlvKDpmLPnrb7liLDljaEjMDEyMzQ0OTcjMCMwIzE0MDEjMTYwM3znjovotLrlubPnrb7liLDljaEjMDEyMzQ0OTkjMCMwIzE0MDEjMTYwM3zpmYjpmLPnrb7liLDljaEjMDEyMzQ0OTgjMCMwIzE0MDEjMTYwMyAg"));
		System.out.println(StringUtils.isNull(null));
	}
}
