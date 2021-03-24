package com.smt.dev.publish.data;

import java.util.List;
import java.util.Map;

import com.douglei.orm.context.SessionContext;
import com.douglei.orm.context.Transaction;
import com.douglei.orm.context.TransactionComponent;

/**
 * 固定数据查询服务
 * @author DougLei
 */
@TransactionComponent
public class FixedDataQueryService {
	
	/**
	 * 查询固定表名的所有数据
	 * @param tablename
	 * @return
	 */
	@Transaction
	public List<Map<String, Object>> query(String tablename) {
		return SessionContext.getSqlSession().query("select * from " + tablename);
	}
}
