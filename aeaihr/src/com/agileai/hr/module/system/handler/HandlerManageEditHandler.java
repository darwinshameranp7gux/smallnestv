package com.agileai.hr.module.system.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.agileai.domain.DataParam;
import com.agileai.hotweb.controller.core.MasterSubEditMainHandler;
import com.agileai.hr.cxmodule.FunctionTreeManage;
import com.agileai.hotweb.domain.FormSelectFactory;
import com.agileai.hr.module.system.service.HandlerManage;
import com.agileai.hotweb.renders.ViewRenderer;

public class HandlerManageEditHandler
        extends MasterSubEditMainHandler {
    public HandlerManageEditHandler() {
        super();
        this.listHandlerClass = HandlerManageListHandler.class;
        this.serviceId = buildServiceId(HandlerManage.class);
        this.baseTablePK = "HANLER_ID";
        this.defaultTabId = "SysOperation";
    }

    protected void processPageAttributes(DataParam param) {
    	this.setAttribute("FUNC_ID", param.get("FUNC_ID"));
    	setAttribute("HANLER_TYPE",
                     FormSelectFactory.create("HANDLER_TYPE")
                                      .addSelectedValue(getOperaAttributeValue("HANLER_TYPE",
                                                                               "MAIN")));
    }
    public ViewRenderer doSaveMasterRecordAction(DataParam param){
    	getFunctionTreeManage().clearFuncTreeCache();
    	return super.doSaveMasterRecordAction(param);
    }
    public ViewRenderer doMoveUpAction(DataParam param){
    	getFunctionTreeManage().clearFuncTreeCache();
    	return super.doMoveUpAction(param);
    }
    public ViewRenderer doMoveDownAction(DataParam param){
    	getFunctionTreeManage().clearFuncTreeCache();
    	return super.doMoveDownAction(param);
    }
    public ViewRenderer doSaveEntryRecordsAction(DataParam param){
    	getFunctionTreeManage().clearFuncTreeCache();
    	return super.doSaveEntryRecordsAction(param);
    }
    public ViewRenderer doDeleteEntryRecordAction(DataParam param){
    	getFunctionTreeManage().clearFuncTreeCache();
    	return super.doDeleteEntryRecordAction(param);
    }
    protected String[] getEntryEditFields(String currentSubTableId) {
        List<String> temp = new ArrayList<String>();

        if ("SysOperation".equals(currentSubTableId)) {
            temp.add("OPER_ID");
            temp.add("HANLER_ID");
            temp.add("OPER_CODE");
            temp.add("OPER_ACTIONTPYE");
            temp.add("OPER_NAME");
            temp.add("OPER_SORT");
        }

        return temp.toArray(new String[] {  });
    }

    protected String getEntryEditTablePK(String currentSubTableId) {
        HashMap<String, String> primaryKeys = new HashMap<String, String>();
        primaryKeys.put("SysOperation", "OPER_ID");

        return primaryKeys.get(currentSubTableId);
    }

    protected String getEntryEditForeignKey(String currentSubTableId) {
        HashMap<String, String> foreignKeys = new HashMap<String, String>();
        foreignKeys.put("SysOperation", "HANLER_ID");

        return foreignKeys.get(currentSubTableId);
    }

    protected FunctionTreeManage getFunctionTreeManage(){
    	FunctionTreeManage functionTreeManage = this.lookupService(FunctionTreeManage.class);
    	String appName = request.getContextPath().substring(1);
    	functionTreeManage.setAppName(appName);
    	return functionTreeManage;
    }
    
    protected HandlerManage getService() {
        return (HandlerManage) this.lookupService(this.getServiceId());
    }
}
