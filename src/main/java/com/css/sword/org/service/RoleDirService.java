package com.css.sword.org.service;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.css.sword.core.kernel.base.annotation.Service;
import com.css.sword.core.kernel.base.annotation.ServiceContainer;
import com.css.sword.core.kernel.base.exception.SwordBaseCheckedException;
import com.css.sword.core.kernel.base.persistence.IPersistenceService;
import com.css.sword.core.kernel.utils.SwordPersistenceUtils;
import com.css.sword.org.entity.OrgRoleDir;
import com.css.sword.web.request.ISwordRequest;
import com.css.sword.web.response.ISwordResponse;
import com.css.sword.web.response.SwordResponseFactory;
import com.css.util.Page;

@ServiceContainer("org/roledir")
public class RoleDirService {
	
	//根据角色目录id查询出角色目录信息
	@Service("getRoleDirById")
	public ISwordResponse getRoleDirById(ISwordRequest iReq) throws SwordBaseCheckedException{
		
		IPersistenceService dao = SwordPersistenceUtils.getPersistenceService();
		ISwordResponse dRes = SwordResponseFactory.createSwordResponseInstance(iReq);
		
		try {
			String sql = "select * from org_role_dir where dir_code=?";
			List<Object> param = new ArrayList<Object>();
			param.add(iReq.getData("dir_code"));
			OrgRoleDir roleDir = dao.findOneBySql(sql, param, OrgRoleDir.class);
			dRes.setModel(roleDir);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dRes;
	}
		
	//角色目录导入
	@Service("importRoleDir")
	public ISwordResponse importRoleDir(ISwordRequest iReq,List<OrgRoleDir> list) throws SwordBaseCheckedException{
		
		IPersistenceService dao = SwordPersistenceUtils.getPersistenceService();
		ISwordResponse dRes = SwordResponseFactory.createSwordResponseInstance(iReq);

		try {
			dao.insertBatch(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dRes;
	}
	//获取角色目录列表
	@Service("getAllRoleDir")
	public ISwordResponse getAllRoleDir(ISwordRequest iReq) throws SwordBaseCheckedException{
		
		IPersistenceService dao = SwordPersistenceUtils.getPersistenceService();
		ISwordResponse dRes = SwordResponseFactory.createSwordResponseInstance(iReq);

		try {
			String sql = "select * from org_role_dir";
			List<OrgRoleDir> result = dao.findAllBySql(sql, null ,OrgRoleDir.class);
			dRes.setModel(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dRes;
	}
	//获取角色目录列表，带分页
	@Service("getAllRoleDirPage")
	public ISwordResponse getAllRoleDirPage(ISwordRequest iReq) throws SwordBaseCheckedException{
		
		ISwordResponse dRes = SwordResponseFactory.createSwordResponseInstance(iReq);

		try {
			String sql = "select * from org_role_dir";
			dRes.setModel(new Page(iReq).getData(sql, null, OrgRoleDir.class));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dRes;
	}
	
	//保存角色目录
	@Service("saveRoleDir")
	public ISwordResponse saveRoleDir(ISwordRequest iReq,OrgRoleDir roleDir) throws SwordBaseCheckedException{
		
		IPersistenceService dao = SwordPersistenceUtils.getPersistenceService();
		ISwordResponse dRes = SwordResponseFactory.createSwordResponseInstance(iReq);
		JSONObject json = new JSONObject();
		json.put("status", false);
		//String saveType = iReq.getData("saveType");
		try {
			//if(saveType.equals("insert")? dao.insert(role) :dao.update(role)){
			if(dao.saveOrUpdate(roleDir)){
				json.put("status", true);
				json.put("message", "保存成功.");
			}else{
				json.put("message", "保存失败.");
			}
			dRes.setModel(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			json.put("message", "保存失败.");
			dRes.setModel(json);
			e.printStackTrace();
		}
		
		return dRes;
	}
	//角色目录id验证
	@Service("validateRoleDirCode")
	public ISwordResponse validateRoleDirCode(ISwordRequest iReq) throws SwordBaseCheckedException{
		
		IPersistenceService dao = SwordPersistenceUtils.getPersistenceService();
		ISwordResponse dRes = SwordResponseFactory.createSwordResponseInstance(iReq);

		try {
			String sql = "select dir_code from org_role_dir where dir_code=?";
			List<Object> param = new ArrayList<Object>();
			param.add(iReq.getData("dir_code"));
			OrgRoleDir roleDir = dao.findOneBySql(sql, param, OrgRoleDir.class);
			dRes.setModel(roleDir==null?true:false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dRes;
	}
}
