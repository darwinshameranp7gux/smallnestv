package com.agileai.hr.cxmodule;

import java.util.List;

import com.agileai.domain.DataParam;
import com.agileai.domain.DataRow;
import com.agileai.hotweb.bizmoduler.core.StandardService;

public interface HrSalaryManage
        extends StandardService {
	
	public DataRow retrieveValidDays(String year,String month);
	public DataRow getInductionAndCreateTime(String userId);
	public void createValidDayRecord(DataParam param);
	public void updateValidDayRecord(DataParam param);
	
	public void gatherData(String year,String month);
	public void computeTotalMoney(String masterRecordId);
	public void approveRecord(DataParam param);
	public void revokeApprovalRecords(String salId);
	public List<DataRow> findSalaryList(DataParam param);
	
	public DataRow getLastSalayInfo(DataParam param);
	public void recalculation(String year,String month,String userId);
	public List<DataRow> findFiveMonthsSalayInfos(DataParam param);
}

