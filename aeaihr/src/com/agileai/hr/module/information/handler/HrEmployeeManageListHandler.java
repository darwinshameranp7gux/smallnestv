package com.agileai.hr.module.information.handler;

import java.util.List;

import com.agileai.common.AppConfig;
import com.agileai.domain.DataParam;
import com.agileai.domain.DataRow;
import com.agileai.hotweb.annotation.PageAction;
import com.agileai.hotweb.common.BeanFactory;
import com.agileai.hotweb.common.HttpClientHelper;
import com.agileai.hotweb.controller.core.MasterSubListHandler;
import com.agileai.hotweb.domain.FormSelect;
import com.agileai.hotweb.domain.FormSelectFactory;
import com.agileai.hotweb.renders.AjaxRenderer;
import com.agileai.hotweb.renders.DispatchRenderer;
import com.agileai.hotweb.renders.LocalRenderer;
import com.agileai.hotweb.renders.ViewRenderer;
import com.agileai.hr.module.information.service.HrEmployeeManage;

public class HrEmployeeManageListHandler
        extends MasterSubListHandler {
    public HrEmployeeManageListHandler() {
        super();
        this.editHandlerClazz = HrEmployeeManageEditHandler.class;
        this.serviceId = buildServiceId(HrEmployeeManage.class);
    }
    public ViewRenderer prepareDisplay(DataParam param) {
	
		mergeParam(param);
		initParameters(param);
		this.setAttributes(param);
		String empWorkState = param.get("empWorkState");
		if("01".equals(empWorkState)){
			param.put("dimission","Y");
		}else{
			param.put("undimission","Y");
		}
		List<DataRow> rsList = getService().findMasterRecords(param);
		this.setRsList(rsList);
		processPageAttributes(param);
		return new LocalRenderer(getPage());
	}

    protected void processPageAttributes(DataParam param) {
        setAttribute("empSex",
                     FormSelectFactory.create("USER_SEX")
                                      .addSelectedValue(param.get("empSex")));
        setAttribute("EMP_IS_SYNC",
                FormSelectFactory.create("EMP_IS_SYNC")
                                 .addSelectedValue(param.get("IS_SYNC")));
        initMappingItem("EMP_IS_SYNC",
                        FormSelectFactory.create("IS_SYNC").getContent());
        
        initMappingItem("EMP_SEX",
                FormSelectFactory.create("USER_SEX").getContent());
        
        initMappingItem("EMP_EDUCATION",
                        FormSelectFactory.create("EMP_EDUCATION").getContent());
        initMappingItem("EMP_STATE",
                FormSelectFactory.create("EMP_STATE").getContent());
        initMappingItem("EMP_WORK_STATE",
        		FormSelectFactory.create("EMP_WORK_STATE").getContent());
		setAttribute("EMP_STATE", FormSelectFactory.create("EMP_STATE")
				.addSelectedValue(param.get("EMP_STATE")));
		setAttribute("empWorkState", buildEmpWorkStateSelect().addSelectedValue(param.get("empWorkState","00")));
		
    }
    
	
    
    public ViewRenderer doSynchronizationAction(DataParam param){
		HttpClientHelper clientHelper = new HttpClientHelper();
		BeanFactory beanFactory = BeanFactory.instance();
		AppConfig appConfig = beanFactory.getAppConfig();
		String menuURLPrefix = appConfig.getConfig("GlobalConfig","PerURLPrefix");
		String responseBody = clientHelper.retrieveGetReponseText(menuURLPrefix+"?code="+param.getString("EMP_CODE")+"&isDisp="+param.getString("isDisp"));
		return new AjaxRenderer(responseBody);
	}
    
    public ViewRenderer doApproveRequestAction(DataParam param) {
		storeParam(param);
		return new DispatchRenderer(getHandlerURL(this.editHandlerClazz) + "&"
				+ OperaType.KEY + "=approve&comeFrome=approve");
	}
    
    
    protected void initParameters(DataParam param) {
    	initParamItem(param, "EMP_STATE", "");
        initParamItem(param, "empSex", "");
        initParamItem(param, "empName", "");
    }
    

    protected HrEmployeeManage getService() {
        return (HrEmployeeManage) this.lookupService(this.getServiceId());
    }
    @PageAction
	public ViewRenderer revokeApproval(DataParam param) {
		String empId = param.get("EMP_ID");
		getService().revokeApprovalRecords(empId);
		return prepareDisplay(param);
	}
    private FormSelect buildEmpWorkStateSelect(){
    	FormSelect formSelect = new FormSelect();
    	formSelect.addValue("00", "在职");
    	formSelect.addValue("01", "离职");
    	formSelect.addHasBlankValue(false);
    	return formSelect;
    }
}
